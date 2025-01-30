package com.samsung.android.sume.core.types;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public enum Status {
  OK(0);

  private final int value;

  Status(int value) {
    this.value = value;
  }

  public static Status from(final int value) {
    return (Status)
        Arrays.stream(values())
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.types.Status$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return Status.lambda$from$0(value, (Status) obj);
                  }
                })
            .findFirst()
            .orElseThrow(
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.types.Status$$ExternalSyntheticLambda1
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    return Status.lambda$from$1(value);
                  }
                });
  }

  static /* synthetic */ boolean lambda$from$0(int value, Status e) {
    return e.value == value;
  }

  static /* synthetic */ InvalidParameterException lambda$from$1(int value) {
    return new InvalidParameterException("invalid Status value: " + value);
  }
}
