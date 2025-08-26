package com.android.systemui.inputdevice.tutorial.ui.view;

import com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$6$1 extends FunctionReferenceImpl implements Function1 {
    public KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$6$1(Object obj) {
        super(1, obj, KeyboardTouchpadTutorialViewModel.class, "onAutoProceed", "onAutoProceed(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((KeyboardTouchpadTutorialViewModel) this.receiver).onAutoProceed((Continuation) obj);
    }
}
