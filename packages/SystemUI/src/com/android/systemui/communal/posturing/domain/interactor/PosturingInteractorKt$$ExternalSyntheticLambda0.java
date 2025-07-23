package com.android.systemui.communal.posturing.domain.interactor;

import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PosturingInteractorKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Float f = (Float) obj;
        f.getClass();
        int i = StringCompanionObject.$r8$clinit;
        return String.format("%.2f", Arrays.copyOf(new Object[]{f}, 1));
    }
}
