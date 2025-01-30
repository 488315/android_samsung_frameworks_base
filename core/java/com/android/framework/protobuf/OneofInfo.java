package com.android.framework.protobuf;

import java.lang.reflect.Field;

@CheckReturnValue
/* loaded from: classes4.dex */
final class OneofInfo {
  private final Field caseField;

  /* renamed from: id */
  private final int f627id;
  private final Field valueField;

  public OneofInfo(int id, Field caseField, Field valueField) {
    this.f627id = id;
    this.caseField = caseField;
    this.valueField = valueField;
  }

  public int getId() {
    return this.f627id;
  }

  public Field getCaseField() {
    return this.caseField;
  }

  public Field getValueField() {
    return this.valueField;
  }
}
