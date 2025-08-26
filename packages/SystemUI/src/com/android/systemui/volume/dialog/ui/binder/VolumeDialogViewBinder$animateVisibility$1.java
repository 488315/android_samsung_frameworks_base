package com.android.systemui.volume.dialog.ui.binder;

import android.app.Dialog;
import android.os.Trace;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.ui.utils.JankListenerFactory;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;

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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x007c, code lost:
    
        if (com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt.suspendAnimate$default(r10, 1.0f, null, r9, 2) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00d3, code lost:
    
        if (com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt.suspendAnimate$default(r10, 0.0f, null, r9, 2) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d5, code lost:
    
        return r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener, com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1] */
    /* JADX WARN: Type inference failed for: r7v1, types: [T, androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener, com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        ArrayList arrayList;
        int iIndexOf;
        ArrayList arrayList2;
        int iIndexOf2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            VolumeDialogVisibilityModel volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) this.L$0;
            if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Visible) {
                ((VolumeTracerImpl) this.this$0.tracer).getClass();
                Trace.endAsyncSection(VolumeTracerImpl.getMethodName(volumeDialogVisibilityModel), volumeDialogVisibilityModel.hashCode());
                DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener = this.$junkListener.element;
                if (onAnimationUpdateListener != null && (iIndexOf2 = (arrayList2 = this.$animation.mUpdateListeners).indexOf(onAnimationUpdateListener)) >= 0) {
                    arrayList2.set(iIndexOf2, null);
                }
                Ref$ObjectRef<DynamicAnimation.OnAnimationUpdateListener> ref$ObjectRef = this.$junkListener;
                final JankListenerFactory jankListenerFactory = this.this$0.jankListenerFactory;
                final View view = this.$view;
                jankListenerFactory.getClass();
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                final String str = "show";
                ?? r7 = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                        Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                        if (ref$BooleanRef2.element) {
                            return;
                        }
                        ref$BooleanRef2.element = true;
                        final JankListenerFactory jankListenerFactory2 = jankListenerFactory;
                        jankListenerFactory2.interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(55, view).setTag(str));
                        dynamicAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1.1
                            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                            public final void onAnimationEnd(DynamicAnimation dynamicAnimation2, boolean z, float f3, float f4) {
                                JankListenerFactory jankListenerFactory3 = jankListenerFactory2;
                                if (z) {
                                    jankListenerFactory3.interactionJankMonitor.cancel(55);
                                } else {
                                    jankListenerFactory3.interactionJankMonitor.end(55);
                                }
                            }
                        });
                    }
                };
                this.$animation.addUpdateListener(r7);
                ref$ObjectRef.element = r7;
                SpringAnimation springAnimation = this.$animation;
                this.label = 1;
            } else if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Dismissed) {
                ((VolumeTracerImpl) this.this$0.tracer).getClass();
                Trace.endAsyncSection(VolumeTracerImpl.getMethodName(volumeDialogVisibilityModel), volumeDialogVisibilityModel.hashCode());
                DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener2 = this.$junkListener.element;
                if (onAnimationUpdateListener2 != null && (iIndexOf = (arrayList = this.$animation.mUpdateListeners).indexOf(onAnimationUpdateListener2)) >= 0) {
                    arrayList.set(iIndexOf, null);
                }
                Ref$ObjectRef<DynamicAnimation.OnAnimationUpdateListener> ref$ObjectRef2 = this.$junkListener;
                final JankListenerFactory jankListenerFactory2 = this.this$0.jankListenerFactory;
                final View view2 = this.$view;
                jankListenerFactory2.getClass();
                final Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                final String str2 = PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS;
                ?? r6 = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                        Ref$BooleanRef ref$BooleanRef22 = ref$BooleanRef2;
                        if (ref$BooleanRef22.element) {
                            return;
                        }
                        ref$BooleanRef22.element = true;
                        final JankListenerFactory jankListenerFactory22 = jankListenerFactory2;
                        jankListenerFactory22.interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(55, view2).setTag(str2));
                        dynamicAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.volume.dialog.ui.utils.JankListenerFactory$createJunkListener$1.1
                            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                            public final void onAnimationEnd(DynamicAnimation dynamicAnimation2, boolean z, float f3, float f4) {
                                JankListenerFactory jankListenerFactory3 = jankListenerFactory22;
                                if (z) {
                                    jankListenerFactory3.interactionJankMonitor.cancel(55);
                                } else {
                                    jankListenerFactory3.interactionJankMonitor.end(55);
                                }
                            }
                        });
                    }
                };
                this.$animation.addUpdateListener(r6);
                ref$ObjectRef2.element = r6;
                SpringAnimation springAnimation2 = this.$animation;
                this.label = 2;
            } else if (!(volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Invisible)) {
                throw new NoWhenBranchMatchedException();
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$dialog.dismiss();
        }
        return Unit.INSTANCE;
    }
}
