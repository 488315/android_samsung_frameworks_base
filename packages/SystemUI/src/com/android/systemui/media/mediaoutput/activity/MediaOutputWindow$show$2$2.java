package com.android.systemui.media.mediaoutput.activity;

import android.content.Context;
import android.widget.PopupWindow;
import com.android.systemui.media.mediaoutput.activity.MediaOutputWindow;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class MediaOutputWindow$show$2$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MediaOutputWindow this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputWindow$show$2$2(MediaOutputWindow mediaOutputWindow, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaOutputWindow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaOutputWindow$show$2$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputWindow$show$2$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MediaOutputWindow.Companion companion = MediaOutputWindow.Companion;
            Context context = this.this$0.context;
            companion.getClass();
            Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MediaOutputWindow$Companion$dismissCallback$1(context, null)), -1, 2);
            final MediaOutputWindow mediaOutputWindow = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$show$2$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ((Number) obj2).longValue();
                    PopupWindow popupWindow = mediaOutputWindow.popupWindow;
                    if (popupWindow == null) {
                        popupWindow = null;
                    }
                    popupWindow.dismiss();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowBuffer$default.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
