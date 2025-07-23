package org.apache.commons.lang3.builder;

import java.lang.reflect.Field;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class HashCodeBuilder$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Field) obj).getName();
    }
}
