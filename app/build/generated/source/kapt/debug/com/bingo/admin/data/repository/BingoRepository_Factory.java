package com.bingo.admin.data.repository;

import com.bingo.admin.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class BingoRepository_Factory implements Factory<BingoRepository> {
  private final Provider<ApiService> apiServiceProvider;

  public BingoRepository_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public BingoRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static BingoRepository_Factory create(Provider<ApiService> apiServiceProvider) {
    return new BingoRepository_Factory(apiServiceProvider);
  }

  public static BingoRepository newInstance(ApiService apiService) {
    return new BingoRepository(apiService);
  }
}
