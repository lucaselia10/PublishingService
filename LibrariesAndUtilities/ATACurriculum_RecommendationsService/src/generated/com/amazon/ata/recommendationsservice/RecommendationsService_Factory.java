package com.amazon.ata.recommendationsservice;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RecommendationsService_Factory implements Factory<RecommendationsService> {
  private static final RecommendationsService_Factory INSTANCE =
      new RecommendationsService_Factory();

  @Override
  public RecommendationsService get() {
    return new RecommendationsService();
  }

  public static RecommendationsService_Factory create() {
    return INSTANCE;
  }
}
