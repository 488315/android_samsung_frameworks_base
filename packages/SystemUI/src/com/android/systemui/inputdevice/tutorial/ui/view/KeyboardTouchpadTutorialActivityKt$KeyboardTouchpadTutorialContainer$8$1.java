package com.android.systemui.inputdevice.tutorial.ui.view;

import com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$8$1 extends FunctionReferenceImpl implements Function0 {
    public KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$8$1(Object obj) {
        super(0, obj, KeyboardTouchpadTutorialViewModel.class, "onBack", "onBack()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((KeyboardTouchpadTutorialViewModel) this.receiver).onBack();
        return Unit.INSTANCE;
    }
}
