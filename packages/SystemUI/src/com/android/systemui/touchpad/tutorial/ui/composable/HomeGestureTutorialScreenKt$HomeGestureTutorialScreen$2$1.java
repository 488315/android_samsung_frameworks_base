package com.android.systemui.touchpad.tutorial.ui.composable;

import com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.channels.BufferedChannel;

/* loaded from: classes3.dex */
final /* synthetic */ class HomeGestureTutorialScreenKt$HomeGestureTutorialScreen$2$1 extends FunctionReferenceImpl implements Function0 {
    public HomeGestureTutorialScreenKt$HomeGestureTutorialScreen$2$1(Object obj) {
        super(0, obj, EasterEggGestureViewModel.class, "onEasterEggFinished", "onEasterEggFinished()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BufferedChannel bufferedChannel = ((EasterEggGestureViewModel) this.receiver).easterEggFinished;
        Unit unit = Unit.INSTANCE;
        bufferedChannel.mo3475trySendJP2dKIU(unit);
        return unit;
    }
}
