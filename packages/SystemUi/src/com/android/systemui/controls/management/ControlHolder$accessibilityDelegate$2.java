package com.android.systemui.controls.management;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class ControlHolder$accessibilityDelegate$2 extends FunctionReferenceImpl implements Function0 {
    public ControlHolder$accessibilityDelegate$2(Object obj) {
        super(0, obj, ControlHolder.class, "getLayoutPosition", "getLayoutPosition()I", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return Integer.valueOf(((ControlHolder) this.receiver).getLayoutPosition());
    }
}
