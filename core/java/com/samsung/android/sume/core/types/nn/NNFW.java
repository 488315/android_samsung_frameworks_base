package com.samsung.android.sume.core.types.nn;

import com.samsung.android.sume.core.types.NumericEnum;
import com.samsung.android.sume.core.types.Vendor;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public enum NNFW implements NumericEnum {
  NONE(0, new Vendor[] {Vendor.NONE}),
  SNPE(1, new Vendor[] {Vendor.QCOM}),
  EDEN(2, new Vendor[] {Vendor.SLSI}),
  TFLITE(3, new Vendor[] {Vendor.SLSI, Vendor.QCOM}),
  SNAP(4, new Vendor[] {Vendor.SLSI, Vendor.QCOM});

  private final int value;

  /* renamed from: vendor, reason: collision with root package name */
  private final Vendor[] f3144vendor;

  NNFW(int value, Vendor[] vendor2) {
    this.value = value;
    this.f3144vendor = vendor2;
  }

  @Override // com.samsung.android.sume.core.types.NumericEnum
  public int getValue() {
    return this.value;
  }

  public Vendor[] getSupportedVendors() {
    return this.f3144vendor;
  }

  @Override // com.samsung.android.sume.core.types.NumericEnum
  public String stringfy() {
    return name() + ":" + this.value;
  }

  public static NNFW fromExtension(final String ext) {
    return (NNFW)
        Collections.unmodifiableMap(
                new HashMap<
                    String, NNFW>() { // from class: com.samsung.android.sume.core.types.nn.NNFW.1
                  {
                    put("dlc", NNFW.SNPE);
                    put("tflite", NNFW.TFLITE);
                    put("tf", NNFW.SNAP);
                    put("pb", NNFW.SNAP);
                  }
                })
            .entrySet()
            .stream()
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.types.nn.NNFW$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean equals;
                    equals = ((String) ((Map.Entry) obj).getKey()).equals(ext);
                    return equals;
                  }
                })
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.types.nn.NNFW$$ExternalSyntheticLambda1
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return (NNFW) ((Map.Entry) obj).getValue();
                  }
                })
            .findFirst()
            .orElseThrow(
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.types.nn.NNFW$$ExternalSyntheticLambda2
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    return NNFW.lambda$fromExtension$1(ext);
                  }
                });
  }

  static /* synthetic */ InvalidParameterException lambda$fromExtension$1(String ext) {
    return new InvalidParameterException("not supported model file type: " + ext);
  }
}
