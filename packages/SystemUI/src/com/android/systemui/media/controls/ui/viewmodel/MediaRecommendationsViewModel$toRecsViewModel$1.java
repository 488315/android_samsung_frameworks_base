package com.android.systemui.media.controls.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class MediaRecommendationsViewModel$toRecsViewModel$1 extends ContinuationImpl {
    int I$0;
    int I$1;
    int I$2;
    int I$3;
    int I$4;
    int I$5;
    int I$6;
    int I$7;
    int I$8;
    Object L$0;
    Object L$1;
    Object L$10;
    Object L$11;
    Object L$12;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    Object L$8;
    Object L$9;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaRecommendationsViewModel this$0;

    public MediaRecommendationsViewModel$toRecsViewModel$1(MediaRecommendationsViewModel mediaRecommendationsViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaRecommendationsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MediaRecommendationsViewModel.access$toRecsViewModel(this.this$0, null, this);
    }
}
