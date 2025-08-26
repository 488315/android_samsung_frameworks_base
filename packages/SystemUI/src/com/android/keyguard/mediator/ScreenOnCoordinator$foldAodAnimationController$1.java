package com.android.keyguard.mediator;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class ScreenOnCoordinator$foldAodAnimationController$1 extends FunctionReferenceImpl implements Function1 {
    public static final ScreenOnCoordinator$foldAodAnimationController$1 INSTANCE = new ScreenOnCoordinator$foldAodAnimationController$1();

    public ScreenOnCoordinator$foldAodAnimationController$1() {
        super(1, SysUIUnfoldComponent.class, "getFoldAodAnimationController", "getFoldAodAnimationController()Lcom/android/systemui/unfold/FoldAodAnimationController;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((SysUIUnfoldComponent) obj).getFoldAodAnimationController();
    }
}
