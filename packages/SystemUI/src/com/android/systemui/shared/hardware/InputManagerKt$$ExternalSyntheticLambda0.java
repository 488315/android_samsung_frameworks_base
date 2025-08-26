package com.android.systemui.shared.hardware;

import android.hardware.input.InputManager;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class InputManagerKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ InputManager f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return this.f$0.getInputDevice(((Integer) obj).intValue());
    }
}
