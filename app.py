"""
BINGO Game Shop - Flask Backend
Provides API endpoints for admin login and price updates.
"""

import json
import os
from datetime import datetime, timedelta
from functools import wraps

from flask import Flask, jsonify, request, render_template
from flask_cors import CORS
import jwt

app = Flask(__name__)
CORS(app)

# Configuration
DATA_FILE = 'data.json'
SECRET_KEY = 'bingo_admin_secret_key_2024'  # Change in production
ADMIN_USERNAME = 'admin'
ADMIN_PASSWORD = 'bingo123'

# Load data from JSON file
def load_data():
    """Load data from data.json file."""
    try:
        with open(DATA_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    except FileNotFoundError:
        return {"special": {}, "mlbb": [], "pubg": []}
    except json.JSONDecodeError:
        return {"special": {}, "mlbb": [], "pubg": []}

def save_data(data):
    """Save data to data.json file."""
    with open(DATA_FILE, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=4, ensure_ascii=False)

# JWT Token decorator
def token_required(f):
    """Decorator to require valid JWT token."""
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None
        
        # Get token from header
        if 'Authorization' in request.headers:
            auth_header = request.headers['Authorization']
            try:
                token = auth_header.split(" ")[1]
            except IndexError:
                return jsonify({'message': 'Invalid token format'}), 401
        
        if not token:
            return jsonify({'message': 'Token is missing'}), 401
        
        try:
            jwt.decode(token, SECRET_KEY, algorithms=['HS256'])
        except jwt.ExpiredSignatureError:
            return jsonify({'message': 'Token has expired'}), 401
        except jwt.InvalidTokenError:
            return jsonify({'message': 'Invalid token'}), 401
        
        return f(*args, **kwargs)
    
    return decorated

# ==================== ROUTES ====================

@app.route('/')
def index():
    """Render the main index.html page with game data."""
    data = load_data()
    return render_template('index.html',
                         mlbb=data.get('mlbb', []),
                         pubg=data.get('pubg', []),
                         special=data.get('special', {}))

@app.route('/api/data')
def get_data():
    """Get all game data (for API consumers)."""
    data = load_data()
    return jsonify(data)

@app.route('/api/login', methods=['POST'])
def login():
    """Admin login endpoint. Returns JWT token."""
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({'message': 'No data provided'}), 400
        
        username = data.get('username')
        password = data.get('password')
        
        # Validate credentials
        if not username or not password:
            return jsonify({'message': 'Username and password required'}), 400
        
        if username != ADMIN_USERNAME or password != ADMIN_PASSWORD:
            return jsonify({'message': 'Invalid credentials'}), 401
        
        # Generate JWT token
        token = jwt.encode({
            'user': username,
            'exp': datetime.utcnow() + timedelta(hours=24)
        }, SECRET_KEY, algorithm='HS256')
        
        return jsonify({
            'token': token,
            'expiresIn': 86400  # 24 hours in seconds
        }), 200
        
    except Exception as e:
        return jsonify({'message': f'Server error: {str(e)}'}), 500

@app.route('/api/update-item', methods=['PUT'])
@token_required
def update_item():
    """
    Update an item's price and/or tag.
    Request body: {"id": "mlbb_1", "category": "mlbb", "new_ks": "800", "new_tag": "HOT"}
    """
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({'message': 'No data provided'}), 400
        
        item_id = data.get('id')
        category = data.get('category')
        new_ks = data.get('new_ks')
        new_tag = data.get('new_tag')
        
        # Validate required fields
        if not item_id or not category:
            return jsonify({'message': 'ID and category are required'}), 400
        
        if category not in ['mlbb', 'pubg', 'special']:
            return jsonify({'message': 'Invalid category'}), 400
        
        # Load current data
        game_data = load_data()
        
        # Update the item
        if category == 'special':
            if 'special' not in game_data:
                game_data['special'] = {}
            
            if new_ks is not None:
                game_data['special']['ks'] = str(new_ks)
            if new_tag is not None:
                game_data['special']['tag'] = str(new_tag)
        else:
            # Find and update item in the list
            items = game_data.get(category, [])
            item_found = False
            
            for item in items:
                if item.get('id') == item_id:
                    if new_ks is not None:
                        item['ks'] = str(new_ks)
                    if new_tag is not None:
                        item['tag'] = str(new_tag)
                    item_found = True
                    break
            
            if not item_found:
                return jsonify({'message': 'Item not found'}), 404
        
        # Save updated data
        save_data(game_data)
        
        return jsonify({
            'message': 'Item updated successfully',
            'item': {
                'id': item_id,
                'category': category,
                'new_ks': new_ks,
                'new_tag': new_tag
            }
        }), 200
        
    except Exception as e:
        return jsonify({'message': f'Server error: {str(e)}'}), 500

@app.route('/api/update-special', methods=['PUT'])
@token_required
def update_special():
    """
    Update the special item's price.
    Request body: {"new_ks": "4000"}
    """
    try:
        data = request.get_json()
        
        if not data:
            return jsonify({'message': 'No data provided'}), 400
        
        new_ks = data.get('new_ks')
        
        if new_ks is None:
            return jsonify({'message': 'new_ks is required'}), 400
        
        # Load current data
        game_data = load_data()
        
        if 'special' not in game_data:
            game_data['special'] = {}
        
        game_data['special']['ks'] = str(new_ks)
        
        # Save updated data
        save_data(game_data)
        
        return jsonify({
            'message': 'Special item updated successfully',
            'new_ks': new_ks
        }), 200
        
    except Exception as e:
        return jsonify({'message': f'Server error: {str(e)}'}), 500

# ==================== ERROR HANDLERS ====================

@app.errorhandler(404)
def not_found(error):
    return jsonify({'message': 'Endpoint not found'}), 404

@app.errorhandler(500)
def internal_error(error):
    return jsonify({'message': 'Internal server error'}), 500

# ==================== MAIN ====================

if __name__ == '__main__':
    # Create templates directory if it doesn't exist
    if not os.path.exists('templates'):
        os.makedirs('templates')
    
    # Copy index.html to templates folder
    if os.path.exists('index.html'):
        import shutil
        shutil.copy('index.html', 'templates/index.html')
    
    print("=" * 50)
    print("BINGO Game Shop Backend")
    print("=" * 50)
    print("Server running on http://0.0.0.0:5000")
    print("Endpoints:")
    print("  - GET  /              - Main website")
    print("  - GET  /api/data     - Get all data (JSON)")
    print("  - POST /api/login    - Admin login")
    print("  - PUT  /api/update-item  - Update item price/tag")
    print("  - PUT  /api/update-special - Update special item")
    print("=" * 50)
    
    app.run(host='0.0.0.0', port=5000, debug=True)
