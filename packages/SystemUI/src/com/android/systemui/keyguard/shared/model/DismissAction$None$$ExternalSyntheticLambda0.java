package com.android.systemui.keyguard.shared.model;

import com.android.systemui.keyguard.shared.model.DismissAction;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class DismissAction$None$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        DismissAction.None none = DismissAction.None.INSTANCE;
        return KeyguardDone.IMMEDIATE;
    }
}
