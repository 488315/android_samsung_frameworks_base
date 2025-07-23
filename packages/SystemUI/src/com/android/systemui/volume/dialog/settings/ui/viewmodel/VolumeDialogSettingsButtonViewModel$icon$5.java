package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import android.graphics.drawable.Drawable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewModel$icon$5 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public VolumeDialogSettingsButtonViewModel$icon$5(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSettingsButtonViewModel$icon$5 volumeDialogSettingsButtonViewModel$icon$5 = new VolumeDialogSettingsButtonViewModel$icon$5((Continuation) obj3);
        volumeDialogSettingsButtonViewModel$icon$5.L$0 = (Drawable) obj;
        volumeDialogSettingsButtonViewModel$icon$5.L$1 = (Drawable) obj2;
        return volumeDialogSettingsButtonViewModel$icon$5.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0075  */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.animation.Animator$AnimatorListener, com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L1a
            if (r1 != r3) goto L12
            java.lang.Object r5 = r5.L$0
            android.graphics.drawable.Drawable r5 = (android.graphics.drawable.Drawable) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L70
        L12:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L1a:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            android.graphics.drawable.Drawable r6 = (android.graphics.drawable.Drawable) r6
            java.lang.Object r1 = r5.L$1
            android.graphics.drawable.Drawable r1 = (android.graphics.drawable.Drawable) r1
            boolean r4 = r6 instanceof com.airbnb.lottie.LottieDrawable
            if (r4 == 0) goto L2c
            com.airbnb.lottie.LottieDrawable r6 = (com.airbnb.lottie.LottieDrawable) r6
            goto L2d
        L2c:
            r6 = r2
        L2d:
            if (r6 == 0) goto L71
            r5.L$0 = r1
            r5.label = r3
            kotlinx.coroutines.CancellableContinuationImpl r4 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r5 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r5)
            r4.<init>(r5, r3)
            r4.initCancellability()
            com.airbnb.lottie.utils.LottieValueAnimator r5 = r6.animator
            if (r5 != 0) goto L45
            r5 = 0
            goto L47
        L45:
            boolean r5 = r5.running
        L47:
            if (r5 != 0) goto L51
            int r5 = kotlin.Result.$r8$clinit
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            r4.resumeWith(r5)
            goto L63
        L51:
            com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$listener$1 r5 = new com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$listener$1
            r5.<init>()
            com.airbnb.lottie.utils.LottieValueAnimator r3 = r6.animator
            r3.addListener(r5)
            com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$1 r3 = new com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModelKt$awaitFinish$2$1
            r3.<init>()
            r4.invokeOnCancellation(r3)
        L63:
            java.lang.Object r5 = r4.getResult()
            if (r5 != r0) goto L6a
            goto L6c
        L6a:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
        L6c:
            if (r5 != r0) goto L6f
            return r0
        L6f:
            r5 = r1
        L70:
            r1 = r5
        L71:
            boolean r5 = r1 instanceof com.airbnb.lottie.LottieDrawable
            if (r5 == 0) goto L78
            r2 = r1
            com.airbnb.lottie.LottieDrawable r2 = (com.airbnb.lottie.LottieDrawable) r2
        L78:
            if (r2 == 0) goto L7d
            r2.start()
        L7d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel$icon$5.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
