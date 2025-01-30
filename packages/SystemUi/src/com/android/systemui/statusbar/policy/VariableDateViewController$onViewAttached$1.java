package com.android.systemui.statusbar.policy;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class VariableDateViewController$onViewAttached$1 extends FunctionReferenceImpl implements Function0 {
    public VariableDateViewController$onViewAttached$1(Object obj) {
        super(0, obj, VariableDateViewController.class, "updateClock", "updateClock()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        VariableDateViewController.access$updateClock((VariableDateViewController) this.receiver);
        return Unit.INSTANCE;
    }
}
