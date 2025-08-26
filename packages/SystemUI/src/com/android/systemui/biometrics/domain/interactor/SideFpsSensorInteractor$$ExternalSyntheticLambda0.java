package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class SideFpsSensorInteractor$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        KeyguardState keyguardState = (KeyguardState) obj;
        return Boolean.valueOf(keyguardState == KeyguardState.OFF || keyguardState == KeyguardState.DOZING);
    }
}
