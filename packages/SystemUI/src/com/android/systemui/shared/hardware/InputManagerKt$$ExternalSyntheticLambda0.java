package com.android.systemui.shared.hardware;

import android.hardware.input.InputManager;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class InputManagerKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ InputManager f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return this.f$0.getInputDevice(((Integer) obj).intValue());
    }
}
