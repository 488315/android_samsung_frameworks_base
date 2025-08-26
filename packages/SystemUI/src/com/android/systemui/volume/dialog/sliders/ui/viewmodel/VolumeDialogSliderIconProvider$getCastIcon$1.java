package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.graphics.drawable.Drawable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class VolumeDialogSliderIconProvider$getCastIcon$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isMuted;
    int I$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogSliderIconProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderIconProvider$getCastIcon$1(boolean z, VolumeDialogSliderIconProvider volumeDialogSliderIconProvider, Continuation continuation) {
        super(2, continuation);
        this.$isMuted = z;
        this.this$0 = volumeDialogSliderIconProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogSliderIconProvider$getCastIcon$1 volumeDialogSliderIconProvider$getCastIcon$1 = new VolumeDialogSliderIconProvider$getCastIcon$1(this.$isMuted, this.this$0, continuation);
        volumeDialogSliderIconProvider$getCastIcon$1.L$0 = obj;
        return volumeDialogSliderIconProvider$getCastIcon$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSliderIconProvider$getCastIcon$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0062, code lost:
    
        if (r3.emit(r5, r9) == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            i = this.$isMuted ? R.drawable.ic_volume_remote_mute : R.drawable.ic_volume_remote;
            VolumeDialogSliderIconProvider volumeDialogSliderIconProvider = this.this$0;
            CoroutineContext coroutineContext = volumeDialogSliderIconProvider.uiBackgroundContext;
            VolumeDialogSliderIconProvider$getCastIcon$1$drawable$1 volumeDialogSliderIconProvider$getCastIcon$1$drawable$1 = new VolumeDialogSliderIconProvider$getCastIcon$1$drawable$1(volumeDialogSliderIconProvider, i, null);
            this.L$0 = flowCollector2;
            this.I$0 = i;
            this.label = 1;
            Object objWithContext = BuildersKt.withContext(coroutineContext, volumeDialogSliderIconProvider$getCastIcon$1$drawable$1, this);
            if (objWithContext != coroutineSingletons) {
                flowCollector = flowCollector2;
                obj = objWithContext;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        i = this.I$0;
        flowCollector = (FlowCollector) this.L$0;
        ResultKt.throwOnFailure(obj);
        Icon.Loaded loaded = new Icon.Loaded((Drawable) obj, null, new Integer(i));
        this.L$0 = null;
        this.label = 2;
    }
}
