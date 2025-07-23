package com.android.keyguard.mediator;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class ScreenOnCoordinator$foldAodAnimationController$1 extends FunctionReferenceImpl implements Function1 {
    public static final ScreenOnCoordinator$foldAodAnimationController$1 INSTANCE = new ScreenOnCoordinator$foldAodAnimationController$1();

    public ScreenOnCoordinator$foldAodAnimationController$1() {
        super(1, SysUIUnfoldComponent.class, "getFoldAodAnimationController", "getFoldAodAnimationController()Lcom/android/systemui/unfold/FoldAodAnimationController;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((SysUIUnfoldComponent) obj).getFoldAodAnimationController();
    }
}
