package com.android.systemui.volume.dialog.ringer.ui.binder;

import android.animation.ArgbEvaluator;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageButton;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerButtonUiModel;
import com.android.systemui.volume.dialog.ui.utils.SuspendAnimatorsKt;
import java.util.NoSuchElementException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class VolumeDialogRingerViewBinder$animateTo$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ SpringAnimation $colorAnimation;
    final /* synthetic */ float $cornerRadiusDiff;
    final /* synthetic */ Function2 $onProgressChanged;
    final /* synthetic */ float $radius;
    final /* synthetic */ RingerButtonUiModel $ringerButtonUiModel;
    final /* synthetic */ SpringAnimation $roundnessAnimation;
    final /* synthetic */ ImageButton $this_animateTo;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogRingerViewBinder this$0;

    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateTo$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SpringAnimation $colorAnimation;
        final /* synthetic */ RingerButtonUiModel $ringerButtonUiModel;
        final /* synthetic */ ImageButton $this_animateTo;
        int label;
        final /* synthetic */ VolumeDialogRingerViewBinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SpringAnimation springAnimation, VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, Continuation continuation) {
            super(2, continuation);
            this.$colorAnimation = springAnimation;
            this.this$0 = volumeDialogRingerViewBinder;
            this.$this_animateTo = imageButton;
            this.$ringerButtonUiModel = ringerButtonUiModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$colorAnimation, this.this$0, this.$this_animateTo, this.$ringerButtonUiModel, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SpringAnimation springAnimation = this.$colorAnimation;
                springAnimation.getClass();
                final VolumeDialogRingerViewBinder volumeDialogRingerViewBinder = this.this$0;
                final ImageButton imageButton = this.$this_animateTo;
                final RingerButtonUiModel ringerButtonUiModel = this.$ringerButtonUiModel;
                Function1 function1 = new Function1() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateTo$3$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Integer numValueOf;
                        int[] colors;
                        int[] colors2;
                        ImageButton imageButton2 = imageButton;
                        float fFloatValue = ((Float) obj2).floatValue();
                        VolumeDialogRingerViewBinder volumeDialogRingerViewBinder2 = volumeDialogRingerViewBinder;
                        ArgbEvaluator argbEvaluator = volumeDialogRingerViewBinder2.rgbEvaluator;
                        float fCoerceIn = RangesKt___RangesKt.coerceIn(fFloatValue, 0.0f, 1.0f);
                        ColorStateList imageTintList = imageButton2.getImageTintList();
                        Integer numValueOf2 = null;
                        if (imageTintList == null || (colors2 = imageTintList.getColors()) == null) {
                            numValueOf = null;
                        } else {
                            if (colors2.length == 0) {
                                throw new NoSuchElementException("Array is empty.");
                            }
                            numValueOf = Integer.valueOf(colors2[0]);
                        }
                        RingerButtonUiModel ringerButtonUiModel2 = ringerButtonUiModel;
                        int iIntValue = ((Integer) argbEvaluator.evaluate(fCoerceIn, numValueOf, Integer.valueOf(ringerButtonUiModel2.tintColor))).intValue();
                        ArgbEvaluator argbEvaluator2 = volumeDialogRingerViewBinder2.rgbEvaluator;
                        float fCoerceIn2 = RangesKt___RangesKt.coerceIn(fFloatValue, 0.0f, 1.0f);
                        ColorStateList color = ((GradientDrawable) imageButton2.getBackground()).getColor();
                        if (color != null && (colors = color.getColors()) != null) {
                            numValueOf2 = Integer.valueOf(colors[0]);
                        }
                        ((GradientDrawable) imageButton2.getBackground()).setColor(((Integer) argbEvaluator2.evaluate(fCoerceIn2, numValueOf2, Integer.valueOf(ringerButtonUiModel2.backgroundColor))).intValue());
                        imageButton2.getBackground().invalidateSelf();
                        imageButton2.setColorFilter(iIntValue);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (SuspendAnimatorsKt.suspendAnimate$default(springAnimation, 0.0f, function1, this, 1) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogRingerViewBinder$animateTo$3(SpringAnimation springAnimation, SpringAnimation springAnimation2, VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, ImageButton imageButton, RingerButtonUiModel ringerButtonUiModel, Function2 function2, float f, float f2, Continuation continuation) {
        super(2, continuation);
        this.$roundnessAnimation = springAnimation;
        this.$colorAnimation = springAnimation2;
        this.this$0 = volumeDialogRingerViewBinder;
        this.$this_animateTo = imageButton;
        this.$ringerButtonUiModel = ringerButtonUiModel;
        this.$onProgressChanged = function2;
        this.$cornerRadiusDiff = f;
        this.$radius = f2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogRingerViewBinder$animateTo$3 volumeDialogRingerViewBinder$animateTo$3 = new VolumeDialogRingerViewBinder$animateTo$3(this.$roundnessAnimation, this.$colorAnimation, this.this$0, this.$this_animateTo, this.$ringerButtonUiModel, this.$onProgressChanged, this.$cornerRadiusDiff, this.$radius, continuation);
        volumeDialogRingerViewBinder$animateTo$3.L$0 = obj;
        return volumeDialogRingerViewBinder$animateTo$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogRingerViewBinder$animateTo$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$colorAnimation, this.this$0, this.$this_animateTo, this.$ringerButtonUiModel, null), 6);
            SpringAnimation springAnimation = this.$roundnessAnimation;
            springAnimation.getClass();
            final Function2 function2 = this.$onProgressChanged;
            final float f = this.$cornerRadiusDiff;
            final ImageButton imageButton = this.$this_animateTo;
            final float f2 = this.$radius;
            Function1 function1 = new Function1() { // from class: com.android.systemui.volume.dialog.ringer.ui.binder.VolumeDialogRingerViewBinder$animateTo$3$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    ImageButton imageButton2 = imageButton;
                    Float f3 = (Float) obj2;
                    float fFloatValue = f3.floatValue();
                    float f4 = f;
                    function2.invoke(f3, Boolean.valueOf(f4 > 0.0f));
                    ((GradientDrawable) imageButton2.getBackground()).setCornerRadius((fFloatValue * f4) + f2);
                    imageButton2.getBackground().invalidateSelf();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (SuspendAnimatorsKt.suspendAnimate$default(springAnimation, 0.0f, function1, this, 1) == coroutineSingletons) {
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
