package org.apache.commons.lang3.builder;

import java.lang.reflect.Field;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final /* synthetic */ class HashCodeBuilder$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Field) obj).getName();
    }
}
