package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0062, code lost:
    
        if (r3.emit(r5, r9) == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 2
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L23
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r10)
            goto L65
        L11:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L19:
            int r1 = r9.I$0
            java.lang.Object r3 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r10)
            goto L4e
        L23:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            boolean r1 = r9.$isMuted
            if (r1 == 0) goto L32
            r1 = 2131233704(0x7f080ba8, float:1.8083553E38)
            goto L35
        L32:
            r1 = 2131233703(0x7f080ba7, float:1.808355E38)
        L35:
            com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderIconProvider r5 = r9.this$0
            kotlin.coroutines.CoroutineContext r6 = r5.uiBackgroundContext
            com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderIconProvider$getCastIcon$1$drawable$1 r7 = new com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderIconProvider$getCastIcon$1$drawable$1
            r7.<init>(r5, r1, r4)
            r9.L$0 = r10
            r9.I$0 = r1
            r9.label = r3
            java.lang.Object r3 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r9)
            if (r3 != r0) goto L4b
            goto L64
        L4b:
            r8 = r3
            r3 = r10
            r10 = r8
        L4e:
            android.graphics.drawable.Drawable r10 = (android.graphics.drawable.Drawable) r10
            com.android.systemui.common.shared.model.Icon$Loaded r5 = new com.android.systemui.common.shared.model.Icon$Loaded
            java.lang.Integer r6 = new java.lang.Integer
            r6.<init>(r1)
            r5.<init>(r10, r4, r6)
            r9.L$0 = r4
            r9.label = r2
            java.lang.Object r9 = r3.emit(r5, r9)
            if (r9 != r0) goto L65
        L64:
            return r0
        L65:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderIconProvider$getCastIcon$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
