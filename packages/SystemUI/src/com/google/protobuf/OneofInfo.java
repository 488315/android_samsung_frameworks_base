package com.google.protobuf;

import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public final class OneofInfo {
    public final Field caseField;
    public final Field valueField;

    public OneofInfo(int i, Field field, Field field2) {
        this.caseField = field;
        this.valueField = field2;
    }
}
