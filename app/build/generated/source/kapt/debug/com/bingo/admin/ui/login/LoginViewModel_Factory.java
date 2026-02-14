package com.bingo.admin.ui.login;

import com.bingo.admin.data.repository.BingoRepository;
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
  private final Provider<BingoRepository> repositoryProvider;

  public LoginViewModel_Factory(Provider<BingoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<BingoRepository> repositoryProvider) {
    return new LoginViewModel_Factory(repositoryProvider);
  }

  public static LoginViewModel newInstance(BingoRepository repository) {
    return new LoginViewModel(repository);
  }
}
