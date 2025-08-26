package com.android.systemui.communal.posturing.domain.interactor;

import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: classes2.dex */
public final /* synthetic */ class PosturingInteractorKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Float f = (Float) obj;
        f.getClass();
        int i = StringCompanionObject.$r8$clinit;
        return String.format("%.2f", Arrays.copyOf(new Object[]{f}, 1));
    }
}
