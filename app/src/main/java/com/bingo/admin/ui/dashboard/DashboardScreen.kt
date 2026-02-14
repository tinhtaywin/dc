package com.bingo.admin.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bingo.admin.data.model.GameItem
import com.bingo.admin.data.model.SpecialItem
import com.bingo.admin.ui.theme.NeonCyan
import com.bingo.admin.ui.theme.NeonPink
import java.text.NumberFormat
import java.util.Locale

/**
 * Format number with commas (e.g., 114800 -> "114,800")
 */
fun formatWithCommas(value: String): String {
    return try {
        val number = value.toLongOrNull() ?: return value
        NumberFormat.getNumberInstance(Locale.US).format(number)
    } catch (e: Exception) {
        value
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showEditSheet by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<Any?>(null) } // Can be GameItem or SpecialItem
    var editingCategory by remember { mutableStateOf("") }
    
    // Load data on first composition
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BINGO ADMIN",
                    color = NeonCyan,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                
                Row {
                    IconButton(onClick = { viewModel.loadData() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = NeonCyan
                        )
                    }
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Logout",
                            tint = NeonPink
                        )
                    }
                }
            }
            
            // Special Item Card
            uiState.specialItem?.let { special ->
                SpecialItemCard(
                    special = special,
                    onEdit = {
                        editingItem = special
                        editingCategory = "special"
                        showEditSheet = true
                    }
                )
            }
            
            // Tabs
            TabRow(
                selectedTabIndex = uiState.selectedTab,
                containerColor = Color.Black,
                contentColor = NeonCyan,
                indicator = { tabPositions ->
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[uiState.selectedTab])
                            .height(3.dp)
                            .background(NeonCyan)
                    )
                }
            ) {
                Tab(
                    selected = uiState.selectedTab == 0,
                    onClick = { viewModel.setSelectedTab(0) },
                    text = { Text("MLBB", color = if (uiState.selectedTab == 0) NeonCyan else Color.Gray) }
                )
                Tab(
                    selected = uiState.selectedTab == 1,
                    onClick = { viewModel.setSelectedTab(1) },
                    text = { Text("PUBG", color = if (uiState.selectedTab == 1) NeonCyan else Color.Gray) }
                )
            }
            
            // Grid
            val items = if (uiState.selectedTab == 0) uiState.mlbbItems else uiState.pubgItems
            val category = if (uiState.selectedTab == 0) "mlbb" else "pubg"
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    GameItemCard(
                        item = item,
                        category = category,
                        onEdit = {
                            editingItem = item
                            editingCategory = category
                            showEditSheet = true
                        }
                    )
                }
            }
        }
        
        // Loading overlay
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = NeonCyan)
            }
        }
        
        // Error message
        if (uiState.error.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
                    .background(NeonPink.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = uiState.error,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        // Edit Bottom Sheet
        if (showEditSheet && editingItem != null) {
            val sheetState = rememberModalBottomSheetState()
            
            ModalBottomSheet(
                onDismissRequest = {
                    showEditSheet = false
                    editingItem = null
                },
                sheetState = sheetState,
                containerColor = Color(0xFF1A1A1A)
            ) {
                EditBottomSheet(
                    item = editingItem,
                    category = editingCategory,
                    onSave = { newKs, newTag ->
                        when (editingItem) {
                            is SpecialItem -> {
                                viewModel.updateSpecialItem(newKs)
                            }
                            is GameItem -> {
                                val gameItem = editingItem as GameItem
                                viewModel.updateItem(gameItem.id, editingCategory, newKs, newTag)
                            }
                        }
                        showEditSheet = false
                        editingItem = null
                    },
                    onCancel = {
                        showEditSheet = false
                        editingItem = null
                    }
                )
            }
        }
    }
}

@Composable
fun SpecialItemCard(
    special: SpecialItem,
    onEdit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        NeonCyan.copy(alpha = 0.2f),
                        Color.Transparent
                    )
                )
            )
            .border(
                2.dp,
                Brush.linearGradient(listOf(NeonCyan, NeonPink)),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐ SPECIAL: ${special.name}",
                    color = NeonCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                IconButton(onClick = onEdit) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = NeonPink
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatWithCommas(special.ks),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp
                )
                Text(
                    text = " Ks",
                    color = NeonCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                
                if (special.tag.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(16.dp))
                    TagChip(tag = special.tag)
                }
            }
        }
    }
}

@Composable
fun GameItemCard(
    item: GameItem,
    category: String,
    onEdit: () -> Unit
) {
    val isNaOrSo = item.tag == "NA" || item.tag == "SO"
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A2A2A),
                        Color(0xFF1A1A1A)
                    )
                )
            )
            .border(
                1.dp,
                if (item.tag == "HOT") NeonPink.copy(alpha = 0.7f) else Color.Gray.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onEdit)
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tag
            if (item.tag.isNotEmpty()) {
                TagChip(tag = item.tag)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Quantity (Diamond or UC)
            val quantityText = if (category == "mlbb") "${item.dia} 💎" else "${item.uc} UC"
            Text(
                text = quantityText,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Price or Status
            if (isNaOrSo) {
                // Show grayed out price
                Text(
                    text = formatWithCommas(item.ks),
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (item.tag == "NA") "Not Available" else "Sold Out",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            } else {
                // Show price with glow
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatWithCommas(item.ks),
                        color = NeonCyan,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = " Ks",
                        color = NeonCyan.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Edit icon
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit",
                tint = NeonPink.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun TagChip(tag: String) {
    val (bgColor, textColor) = when (tag) {
        "HOT" -> NeonPink to Color.White
        "NA" -> Color.Gray to Color.White
        "SO" -> Color(0xFFFF6600) to Color.White
        else -> NeonCyan to Color.Black
    }
    
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = tag,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
    }
}

@Composable
fun EditBottomSheet(
    item: Any?,
    category: String,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var priceText by remember {
        mutableStateOf(
            when (item) {
                is SpecialItem -> item.ks
                is GameItem -> item.ks
                else -> ""
            }
        )
    }
    var selectedTag by remember {
        mutableStateOf(
            when (item) {
                is SpecialItem -> item.tag.ifEmpty { "None" }
                is GameItem -> item.tag.ifEmpty { "None" }
                else -> "None"
            }
        )
    }
    var showTagDropdown by remember { mutableStateOf(false) }
    
    val tags = listOf("None", "HOT", "NA", "SO")
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Price & Tag",
            color = NeonCyan,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Item info - Show quantity as READ-ONLY display (fixed quantity)
        val itemInfo = when (item) {
            is SpecialItem -> item.name
            is GameItem -> if (category == "mlbb") "${item.dia} Diamonds (Fixed)" else "${item.uc} UC (Fixed)"
            else -> ""
        }
        
        // Display fixed quantity as read-only
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF2A2A2A),
                    RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = itemInfo,
                color = NeonCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Note: Quantities are fixed - only price and tag are editable
        Text(
            text = "💡 Quantities (Dia/UC) are fixed - only Price and Tag are editable",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Price Input
        OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it.filter { char -> char.isDigit() } },
            label = { Text("Price (Ks)", color = NeonCyan) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = Color.Gray,
                cursorColor = NeonCyan,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tag Dropdown (only for regular items, not special)
        if (item is GameItem) {
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedTag,
                    onValueChange = {},
                    label = { Text("Status Tag", color = NeonCyan) },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = NeonCyan,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTagDropdown = true }
                )
                
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showTagDropdown = true }
                )
                
                DropdownMenu(
                    expanded = showTagDropdown,
                    onDismissRequest = { showTagDropdown = false }
                ) {
                    tags.forEach { tag ->
                        DropdownMenuItem(
                            text = { Text(tag, color = Color.White) },
                            onClick = {
                                selectedTag = tag
                                showTagDropdown = false
                            }
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            
            Button(
                onClick = {
                    val tagToSave = if (selectedTag == "None") "" else selectedTag
                    onSave(priceText, tagToSave)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonCyan
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Save", color = Color.Black)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
