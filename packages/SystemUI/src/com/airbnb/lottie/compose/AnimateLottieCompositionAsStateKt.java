package com.airbnb.lottie.compose;

import android.content.Context;
import android.provider.Settings;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.android.systemui.util.SettingsHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class AnimateLottieCompositionAsStateKt {

    /* renamed from: com.airbnb.lottie.compose.AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ float $actualSpeed;
        final /* synthetic */ LottieAnimatable $animatable;
        final /* synthetic */ LottieCancellationBehavior $cancellationBehavior;
        final /* synthetic */ LottieClipSpec $clipSpec;
        final /* synthetic */ LottieComposition $composition;
        final /* synthetic */ boolean $isPlaying;
        final /* synthetic */ int $iterations;
        final /* synthetic */ boolean $restartOnPlay;
        final /* synthetic */ MutableState<Boolean> $wasPlaying$delegate;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(boolean z, boolean z2, LottieAnimatable lottieAnimatable, LottieComposition lottieComposition, int i, float f, LottieClipSpec lottieClipSpec, LottieCancellationBehavior lottieCancellationBehavior, MutableState<Boolean> mutableState, Continuation continuation) {
            super(2, continuation);
            this.$isPlaying = z;
            this.$restartOnPlay = z2;
            this.$animatable = lottieAnimatable;
            this.$composition = lottieComposition;
            this.$iterations = i;
            this.$actualSpeed = f;
            this.$clipSpec = lottieClipSpec;
            this.$cancellationBehavior = lottieCancellationBehavior;
            this.$wasPlaying$delegate = mutableState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$isPlaying, this.$restartOnPlay, this.$animatable, this.$composition, this.$iterations, this.$actualSpeed, this.$clipSpec, this.$cancellationBehavior, this.$wasPlaying$delegate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:51:0x00f6, code lost:
        
            if (r14 == r0) goto L52;
         */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x008b  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00a0  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00a5  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Object objMutate;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$isPlaying && !((Boolean) this.$wasPlaying$delegate.getValue()).booleanValue() && this.$restartOnPlay) {
                    LottieAnimatable lottieAnimatable = this.$animatable;
                    this.label = 1;
                    LottieAnimatableImpl lottieAnimatableImpl = (LottieAnimatableImpl) lottieAnimatable;
                    LottieComposition lottieComposition = (LottieComposition) ((SnapshotMutableStateImpl) lottieAnimatableImpl.composition$delegate).getValue();
                    LottieClipSpec lottieClipSpec = (LottieClipSpec) ((SnapshotMutableStateImpl) lottieAnimatableImpl.clipSpec$delegate).getValue();
                    float speed = lottieAnimatableImpl.getSpeed();
                    float minProgress$lottie_compose_release = 0.0f;
                    if (speed >= 0.0f || lottieComposition != null) {
                        if (lottieComposition != null) {
                            if (speed < 0.0f) {
                                if (lottieClipSpec != null) {
                                    minProgress$lottie_compose_release = lottieClipSpec.getMaxProgress$lottie_compose_release();
                                }
                            } else if (lottieClipSpec != null) {
                                minProgress$lottie_compose_release = lottieClipSpec.getMinProgress$lottie_compose_release();
                            }
                        }
                        float f = minProgress$lottie_compose_release;
                        objMutate = lottieAnimatableImpl.mutex.mutate(MutatePriority.Default, new LottieAnimatableImpl$snapTo$2(lottieAnimatableImpl, (LottieComposition) ((SnapshotMutableStateImpl) lottieAnimatableImpl.composition$delegate).getValue(), f, 1, !(f != lottieAnimatableImpl.getProgress()), null), this);
                        if (objMutate != coroutineSingletons) {
                            objMutate = Unit.INSTANCE;
                        }
                        if (objMutate != coroutineSingletons) {
                            objMutate = Unit.INSTANCE;
                        }
                        if (objMutate != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    minProgress$lottie_compose_release = 1.0f;
                    float f2 = minProgress$lottie_compose_release;
                    objMutate = lottieAnimatableImpl.mutex.mutate(MutatePriority.Default, new LottieAnimatableImpl$snapTo$2(lottieAnimatableImpl, (LottieComposition) ((SnapshotMutableStateImpl) lottieAnimatableImpl.composition$delegate).getValue(), f2, 1, !(f2 != lottieAnimatableImpl.getProgress()), null), this);
                    if (objMutate != coroutineSingletons) {
                    }
                    if (objMutate != coroutineSingletons) {
                    }
                    if (objMutate != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$wasPlaying$delegate.setValue(Boolean.valueOf(this.$isPlaying));
            if (!this.$isPlaying) {
                return Unit.INSTANCE;
            }
            LottieAnimatable lottieAnimatable2 = this.$animatable;
            LottieComposition lottieComposition2 = this.$composition;
            int i2 = this.$iterations;
            float f3 = this.$actualSpeed;
            LottieClipSpec lottieClipSpec2 = this.$clipSpec;
            LottieAnimatableImpl lottieAnimatableImpl2 = (LottieAnimatableImpl) lottieAnimatable2;
            float progress = lottieAnimatableImpl2.getProgress();
            LottieCancellationBehavior lottieCancellationBehavior = this.$cancellationBehavior;
            this.label = 2;
            int iIntValue = ((Number) ((SnapshotMutableStateImpl) lottieAnimatableImpl2.iteration$delegate).getValue()).intValue();
            lottieAnimatableImpl2.getClass();
            Object objMutate2 = lottieAnimatableImpl2.mutex.mutate(MutatePriority.Default, new LottieAnimatableImpl$animate$2(lottieAnimatableImpl2, iIntValue, i2, f3, lottieClipSpec2, lottieComposition2, progress, false, lottieCancellationBehavior, null), this);
            if (objMutate2 != coroutineSingletons) {
                objMutate2 = Unit.INSTANCE;
            }
        }
    }

    public static final LottieAnimatable animateLottieCompositionAsState(LottieComposition lottieComposition, boolean z, int i, Composer composer, int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(-180607952);
        if ((i2 & 2) != 0) {
            z = true;
        }
        boolean z2 = (i2 & 4) != 0;
        LottieCancellationBehavior lottieCancellationBehavior = LottieCancellationBehavior.Immediately;
        if (i <= 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Iterations must be a positive number (", ").").toString());
        }
        if (Float.isInfinite(1.0f) || Float.isNaN(1.0f)) {
            throw new IllegalArgumentException(("Speed must be a finite number. It is 1.0.").toString());
        }
        composerImpl.startReplaceableGroup(-610207901);
        composerImpl.startReplaceableGroup(-3687241);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = new LottieAnimatableImpl();
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        composerImpl.end(false);
        LottieAnimatable lottieAnimatable = (LottieAnimatable) objRememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceableGroup(-3687241);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        composerImpl.end(false);
        MutableState mutableState = (MutableState) objRememberedValue2;
        composerImpl.startReplaceableGroup(-180607189);
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
        float f = 1.0f / Settings.Global.getFloat(context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f);
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(new Object[]{lottieComposition, Boolean.valueOf(z), null, Float.valueOf(f), Integer.valueOf(i)}, new AnonymousClass3(z, z2, lottieAnimatable, lottieComposition, i, f, null, lottieCancellationBehavior, mutableState, null), composerImpl);
        composerImpl.end(false);
        return lottieAnimatable;
    }
}
