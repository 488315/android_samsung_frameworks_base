package com.google.protobuf;

import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneofInfo {
    public final Field caseField;
    public final Field valueField;

    public OneofInfo(int i, Field field, Field field2) {
        this.caseField = field;
        this.valueField = field2;
    }
}
