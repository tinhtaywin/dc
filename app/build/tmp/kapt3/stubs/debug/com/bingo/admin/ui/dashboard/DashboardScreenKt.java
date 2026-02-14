package com.bingo.admin.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a \u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aB\u0010\u0006\u001a\u00020\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a&\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\nH\u0007\u001a\u000e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n\u00a8\u0006\u0018"}, d2 = {"DashboardScreen", "", "viewModel", "Lcom/bingo/admin/ui/dashboard/DashboardViewModel;", "onLogout", "Lkotlin/Function0;", "EditBottomSheet", "item", "", "category", "", "onSave", "Lkotlin/Function2;", "onCancel", "GameItemCard", "Lcom/bingo/admin/data/model/GameItem;", "onEdit", "SpecialItemCard", "special", "Lcom/bingo/admin/data/model/SpecialItem;", "TagChip", "tag", "formatWithCommas", "value", "app_debug"})
public final class DashboardScreenKt {
    
    /**
     * Format number with commas (e.g., 114800 -> "114,800")
     */
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String formatWithCommas(@org.jetbrains.annotations.NotNull
    java.lang.String value) {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull
    com.bingo.admin.ui.dashboard.DashboardViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SpecialItemCard(@org.jetbrains.annotations.NotNull
    com.bingo.admin.data.model.SpecialItem special, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void GameItemCard(@org.jetbrains.annotations.NotNull
    com.bingo.admin.data.model.GameItem item, @org.jetbrains.annotations.NotNull
    java.lang.String category, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TagChip(@org.jetbrains.annotations.NotNull
    java.lang.String tag) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EditBottomSheet(@org.jetbrains.annotations.Nullable
    java.lang.Object item, @org.jetbrains.annotations.NotNull
    java.lang.String category, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
}