package com.google.protobuf;

import java.lang.reflect.Field;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class OneofInfo {
    public final Field caseField;
    public final Field valueField;

    public OneofInfo(int i, Field field, Field field2) {
        this.caseField = field;
        this.valueField = field2;
    }
}
