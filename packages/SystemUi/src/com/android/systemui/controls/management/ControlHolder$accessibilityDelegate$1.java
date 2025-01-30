package com.android.systemui.controls.management;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class ControlHolder$accessibilityDelegate$1 extends FunctionReferenceImpl implements Function1 {
    public ControlHolder$accessibilityDelegate$1(Object obj) {
        super(1, obj, ControlHolder.class, "stateDescription", "stateDescription(Z)Ljava/lang/CharSequence;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ControlHolder controlHolder = (ControlHolder) this.receiver;
        int i = ControlHolder.$r8$clinit;
        return controlHolder.stateDescription(booleanValue);
    }
}
