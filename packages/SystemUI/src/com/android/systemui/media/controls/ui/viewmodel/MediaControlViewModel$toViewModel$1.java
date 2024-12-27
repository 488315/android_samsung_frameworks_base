package com.android.systemui.media.controls.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class MediaControlViewModel$toViewModel$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaControlViewModel this$0;

    public MediaControlViewModel$toViewModel$1(MediaControlViewModel mediaControlViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaControlViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MediaControlViewModel.access$toViewModel(this.this$0, null, this);
    }
}
