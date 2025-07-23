package com.android.systemui.volume.dialog.ringer.ui.binder;

import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.volume.dialog.ringer.ui.util.VolumeDialogRingerDrawerTransitionListener;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonUiModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModelState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.properties.ReadWriteProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogRingerViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ReadWriteProperty $backgroundAnimationProgress$delegate;
    final /* synthetic */ float[] $bottomCornerRadii;
    final /* synthetic */ MotionLayout $drawerContainer;
    final /* synthetic */ View $ringerBackgroundView;
    final /* synthetic */ VolumeDialogRingerDrawerTransitionListener $ringerDrawerTransitionListener;
    final /* synthetic */ RingerButtonUiModel $selectedButtonUiModel;
    final /* synthetic */ RingerButtonUiModel $unselectedButtonUiModel;
    final /* synthetic */ View $view;
    final /* synthetic */ View $volumeDialogBackgroundView;
    int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogRingerViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogRingerViewBinder$bind$2(View view, MotionLayout motionLayout, View view2, float[] fArr, VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, RingerButtonUiModel ringerButtonUiModel, RingerButtonUiModel ringerButtonUiModel2, VolumeDialogRingerDrawerTransitionListener volumeDialogRingerDrawerTransitionListener, View view3, ReadWriteProperty readWriteProperty, Continuation continuation) {
        super(2, continuation);
        this.$view = view;
        this.$drawerContainer = motionLayout;
        this.$volumeDialogBackgroundView = view2;
        this.$bottomCornerRadii = fArr;
        this.this$0 = volumeDialogRingerViewBinder;
        this.$selectedButtonUiModel = ringerButtonUiModel;
        this.$unselectedButtonUiModel = ringerButtonUiModel2;
        this.$ringerDrawerTransitionListener = volumeDialogRingerDrawerTransitionListener;
        this.$ringerBackgroundView = view3;
        this.$backgroundAnimationProgress$delegate = readWriteProperty;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogRingerViewBinder$bind$2 volumeDialogRingerViewBinder$bind$2 = new VolumeDialogRingerViewBinder$bind$2(this.$view, this.$drawerContainer, this.$volumeDialogBackgroundView, this.$bottomCornerRadii, this.this$0, this.$selectedButtonUiModel, this.$unselectedButtonUiModel, this.$ringerDrawerTransitionListener, this.$ringerBackgroundView, this.$backgroundAnimationProgress$delegate, continuation);
        volumeDialogRingerViewBinder$bind$2.L$0 = obj;
        return volumeDialogRingerViewBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogRingerViewBinder$bind$2) create((RingerViewModelState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x00e0, code lost:
    
        if (r0.animateAndBindDrawerButtons(r1, r9, r3, r10, r12, r15, r7, r16) == r11) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0111  */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$2$2] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r17) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$bind$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
