package com.android.systemui.common.ui.view;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class TouchHandlingView$interactionHandler$2$2 extends FunctionReferenceImpl implements Function0 {
    public TouchHandlingView$interactionHandler$2$2(Object obj) {
        super(0, obj, TouchHandlingView.class, "isAttachedToWindow", "isAttachedToWindow()Z", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return Boolean.valueOf(((TouchHandlingView) this.receiver).isAttachedToWindow());
    }
}
