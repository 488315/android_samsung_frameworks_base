package com.android.keyguard;

import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUnfoldTransition$$ExternalSyntheticLambda4 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((View) obj).setScrollX(-((int) ((Float) obj2).floatValue()));
        return Unit.INSTANCE;
    }
}
