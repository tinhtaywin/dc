package com.bingo.admin.ui.login;

import com.bingo.admin.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<ApiService> apiServiceProvider;

  public LoginViewModel_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(apiServiceProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<ApiService> apiServiceProvider) {
    return new LoginViewModel_Factory(apiServiceProvider);
  }

  public static LoginViewModel newInstance(ApiService apiService) {
    return new LoginViewModel(apiService);
  }
}
