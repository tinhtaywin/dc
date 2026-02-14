package com.bingo.admin.data.repository;

/**
 * Repository for handling all data operations.
 * Acts as a single source of truth for the app.
 */
@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0006J\u0015\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00f8\u0001\u0000J%\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\n0\t2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006\u00f8\u0001\u0000J\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0006J9\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\n0\t2\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u00f8\u0001\u0000J\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\n0\t2\u0006\u0010\u0018\u001a\u00020\u0006\u00f8\u0001\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2 = {"Lcom/bingo/admin/data/repository/BingoRepository;", "", "apiService", "Lcom/bingo/admin/data/remote/ApiService;", "(Lcom/bingo/admin/data/remote/ApiService;)V", "authToken", "", "getAuthToken", "getGameData", "Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Result;", "Lcom/bingo/admin/data/model/PricesResponse;", "login", "Lcom/bingo/admin/data/model/LoginResponse;", "username", "password", "logout", "", "setAuthToken", "token", "updateItem", "Lcom/bingo/admin/data/model/UpdateResponse;", "id", "category", "newKs", "newTag", "updateSpecialPrice", "app_debug"})
public final class BingoRepository {
    @org.jetbrains.annotations.NotNull
    private final com.bingo.admin.data.remote.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private java.lang.String authToken = "";
    
    @javax.inject.Inject
    public BingoRepository(@org.jetbrains.annotations.NotNull
    com.bingo.admin.data.remote.ApiService apiService) {
        super();
    }
    
    public final void setAuthToken(@org.jetbrains.annotations.NotNull
    java.lang.String token) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAuthToken() {
        return null;
    }
    
    /**
     * Login with username and password
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<com.bingo.admin.data.model.LoginResponse>> login(@org.jetbrains.annotations.NotNull
    java.lang.String username, @org.jetbrains.annotations.NotNull
    java.lang.String password) {
        return null;
    }
    
    /**
     * Fetch all game data (prices, tags)
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<com.bingo.admin.data.model.PricesResponse>> getGameData() {
        return null;
    }
    
    /**
     * Update item price and/or tag
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<com.bingo.admin.data.model.UpdateResponse>> updateItem(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String category, @org.jetbrains.annotations.Nullable
    java.lang.String newKs, @org.jetbrains.annotations.Nullable
    java.lang.String newTag) {
        return null;
    }
    
    /**
     * Update special item price
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<kotlin.Result<com.bingo.admin.data.model.UpdateResponse>> updateSpecialPrice(@org.jetbrains.annotations.NotNull
    java.lang.String newKs) {
        return null;
    }
    
    /**
     * Clear stored token (logout)
     */
    public final void logout() {
    }
}