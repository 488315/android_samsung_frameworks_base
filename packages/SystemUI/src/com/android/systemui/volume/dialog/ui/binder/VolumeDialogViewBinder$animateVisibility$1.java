package com.android.systemui.volume.dialog.ui.binder;

import android.app.Dialog;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogViewBinder$animateVisibility$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SpringAnimation $animation;
    final /* synthetic */ Dialog $dialog;
    final /* synthetic */ Ref$ObjectRef<DynamicAnimation.OnAnimationUpdateListener> $junkListener;
    final /* synthetic */ View $view;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewBinder$animateVisibility$1(VolumeDialogViewBinder volumeDialogViewBinder, Ref$ObjectRef<DynamicAnimation.OnAnimationUpdateListener> ref$ObjectRef, SpringAnimation springAnimation, View view, Dialog dialog, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogViewBinder;
        this.$junkListener = ref$ObjectRef;
        this.$animation = springAnimation;
        this.$view = view;
        this.$dialog = dialog;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogViewBinder$animateVisibility$1 volumeDialogViewBinder$animateVisibility$1 = new VolumeDialogViewBinder$animateVisibility$1(this.this$0, this.$junkListener, this.$animation, this.$view, this.$dialog, continuation);
        volumeDialogViewBinder$animateVisibility$1.L$0 = obj;
        return volumeDialogViewBinder$animateVisibility$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogViewBinder$animateVisibility$1) create((VolumeDialogVisibilityModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x007c, code lost:
    
        if (com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt.suspendAnimate$default(r10, 1.0f, null, r9, 2) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00d5, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d3, code lost:
    
        if (com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt.suspendAnimate$default(r10, 0.0f, null, r9, 2) == r0) goto L29;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener, com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1] */
    /* JADX WARN: Type inference failed for: r7v1, types: [T, androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener, com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$animateVisibility$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
