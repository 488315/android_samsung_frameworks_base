package com.android.keyguard.mediator;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class ScreenOnCoordinator$fullScreenLightRevealAnimations$1 extends FunctionReferenceImpl implements Function1 {
    public static final ScreenOnCoordinator$fullScreenLightRevealAnimations$1 INSTANCE = new ScreenOnCoordinator$fullScreenLightRevealAnimations$1();

    public ScreenOnCoordinator$fullScreenLightRevealAnimations$1() {
        super(1, SysUIUnfoldComponent.class, "getFullScreenLightRevealAnimations", "getFullScreenLightRevealAnimations()Ljava/util/Set;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((SysUIUnfoldComponent) obj).getFullScreenLightRevealAnimations();
    }
}
