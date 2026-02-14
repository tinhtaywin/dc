import threading
import telebot
from flask import Flask, render_template
from flask_socketio import SocketIO

# ================= CONFIGURATION =================
BOT_TOKEN = "xxx"
# PASTE YOUR ID HERE (e.g., 123456789)
ADMIN_ID = xxxx  

bot = telebot.TeleBot(BOT_TOKEN)
app = Flask(__name__)
socketio = SocketIO(app, cors_allowed_origins="*")

# ================= DATA STORE =================
# This acts as our "live" database
prices = {
    "mlbb": [
        {"dia": "11", "ks": "1000", "tag": "NA"},
        {"dia": "22", "ks": "2000", "tag": "NA"},
        {"dia": "275", "ks": "14800", "tag": "HOT"},
        {"dia": "565", "ks": "30400", "tag": "HOT"},
        {"dia": "Weekly Pass", "ks": "6000", "tag": "SPECIAL"}
    ],
    "pubg": [
        {"uc": "60", "ks": "3800", "tag": "HOT"},
        {"uc": "325", "ks": "19000", "tag": "HOT"},
        {"uc": "8100", "ks": "380000", "tag": ""}
    ]
}

# ================= BOT LOGIC =================

# Security Wrapper: Only allows the specific ADMIN_ID
def is_admin(message):
    return message.from_user.id == ADMIN_ID

@bot.message_handler(commands=['start'])
def send_welcome(message):
    if is_admin(message):
        bot.reply_to(message, "✅ Welcome Admin! You can now update prices.\n\nUse: /update [game] [index] [price]\nExample: /update mlbb 0 1500")
    else:
        bot.reply_to(message, f"❌ Access Denied. Your ID is: {message.from_user.id}")

@bot.message_handler(commands=['update'])
def update_price(message):
    # Security Check
    if not is_admin(message):
        bot.reply_to(message, "🚫 You are not authorized to change prices.")
        return

    try:
        # Format: /update mlbb 0 1200
        parts = message.text.split()
        game = parts[1].lower()  
        idx = int(parts[2])      
        new_ks = parts[3]        

        if game in prices and idx < len(prices[game]):
            prices[game][idx]['ks'] = new_ks
            bot.reply_to(message, f"✅ Price Updated!\nGame: {game.upper()}\nItem: {prices[game][idx].get('dia') or prices[game][idx].get('uc')}\nNew Price: {new_ks} Ks")
        else:
            bot.reply_to(message, "❌ Game not found or index out of range.")
            
    except (IndexError, ValueError):
        bot.reply_to(message, "⚠️ Error! Please use correct format:\n`/update mlbb 0 1200`", parse_mode="Markdown")

# ================= WEB ROUTES =================

@app.route('/')
def index():
    # Renders your HTML template using the current live prices
    return render_template('index.html', mlbb=prices['mlbb'], pubg=prices['pubg'])

# ================= RUNNER =================

def run_bot():
    print("--- Telegram Bot is starting... ---")
    bot.infinity_polling()

if __name__ == '__main__':
    # Start Bot Thread
    bot_thread = threading.Thread(target=run_bot)
    bot_thread.daemon = True
    bot_thread.start()

    # Start Flask App
    print("--- Web Server is starting on port 5000... ---")
    socketio.run(app, host='0.0.0.0', port=5000, allow_unsafe_werkzeug=True)