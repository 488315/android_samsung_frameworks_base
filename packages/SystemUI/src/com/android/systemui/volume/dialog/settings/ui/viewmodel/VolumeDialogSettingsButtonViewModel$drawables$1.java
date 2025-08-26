package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import android.R;
import android.graphics.drawable.Drawable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.systemui.lottie.LottieTaskExtKt;
import com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewModel$drawables$1 extends SuspendLambda implements Function2 {
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ VolumeDialogSettingsButtonViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSettingsButtonViewModel$drawables$1(VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogSettingsButtonViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogSettingsButtonViewModel$drawables$1 volumeDialogSettingsButtonViewModel$drawables$1 = new VolumeDialogSettingsButtonViewModel$drawables$1(this.this$0, continuation);
        volumeDialogSettingsButtonViewModel$drawables$1.L$0 = obj;
        return volumeDialogSettingsButtonViewModel$drawables$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSettingsButtonViewModel$drawables$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0107, code lost:
    
        if (r5.emit(r1, r9) != r0) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d6  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        int color;
        LottieDrawable lottieDrawable;
        FlowCollector flowCollector2;
        LottieDrawable lottieDrawable2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            color = this.this$0.context.getColor(R.color.secondary_text_inverse_when_activated_material);
            LottieTask lottieTaskFromRawRes = LottieCompositionFactory.fromRawRes(com.android.systemui.R.raw.audio_bars_in, this.this$0.context);
            this.L$0 = flowCollector;
            this.I$0 = color;
            this.label = 1;
            obj = LottieTaskExtKt.await(lottieTaskFromRawRes, this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            color = this.I$0;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i == 2) {
                color = this.I$0;
                lottieDrawable = (LottieDrawable) this.L$1;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                LottieDrawable lottieDrawable3 = new LottieDrawable();
                lottieDrawable3.setComposition((LottieComposition) obj);
                lottieDrawable3.animator.setRepeatCount(-1);
                lottieDrawable3.animator.setRepeatMode(1);
                lottieDrawable3.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(color)));
                Unit unit = Unit.INSTANCE;
                LottieTask lottieTaskFromRawRes2 = LottieCompositionFactory.fromRawRes(com.android.systemui.R.raw.audio_bars_out, this.this$0.context);
                this.L$0 = flowCollector;
                this.L$1 = lottieDrawable;
                this.L$2 = lottieDrawable3;
                this.I$0 = color;
                this.label = 3;
                obj = LottieTaskExtKt.await(lottieTaskFromRawRes2, this);
                if (obj != coroutineSingletons) {
                    flowCollector2 = flowCollector;
                    lottieDrawable2 = lottieDrawable3;
                    LottieDrawable lottieDrawable4 = new LottieDrawable();
                    lottieDrawable4.setComposition((LottieComposition) obj);
                    lottieDrawable4.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(color)));
                    Unit unit2 = Unit.INSTANCE;
                    Drawable drawable = this.this$0.context.getDrawable(com.android.systemui.R.drawable.audio_bars_idle);
                    drawable.getClass();
                    VolumeDialogSettingsButtonViewModel.Drawables drawables = new VolumeDialogSettingsButtonViewModel.Drawables(lottieDrawable, lottieDrawable2, lottieDrawable4, drawable);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.L$2 = null;
                    this.label = 4;
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            color = this.I$0;
            lottieDrawable2 = (LottieDrawable) this.L$2;
            lottieDrawable = (LottieDrawable) this.L$1;
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            LottieDrawable lottieDrawable42 = new LottieDrawable();
            lottieDrawable42.setComposition((LottieComposition) obj);
            lottieDrawable42.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(color)));
            Unit unit22 = Unit.INSTANCE;
            Drawable drawable2 = this.this$0.context.getDrawable(com.android.systemui.R.drawable.audio_bars_idle);
            drawable2.getClass();
            VolumeDialogSettingsButtonViewModel.Drawables drawables2 = new VolumeDialogSettingsButtonViewModel.Drawables(lottieDrawable, lottieDrawable2, lottieDrawable42, drawable2);
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 4;
        }
        LottieDrawable lottieDrawable5 = new LottieDrawable();
        lottieDrawable5.setComposition((LottieComposition) obj);
        lottieDrawable5.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(color)));
        Unit unit3 = Unit.INSTANCE;
        LottieTask lottieTaskFromRawRes3 = LottieCompositionFactory.fromRawRes(com.android.systemui.R.raw.audio_bars_playing, this.this$0.context);
        this.L$0 = flowCollector;
        this.L$1 = lottieDrawable5;
        this.I$0 = color;
        this.label = 2;
        obj = LottieTaskExtKt.await(lottieTaskFromRawRes3, this);
        if (obj != coroutineSingletons) {
            lottieDrawable = lottieDrawable5;
            LottieDrawable lottieDrawable32 = new LottieDrawable();
            lottieDrawable32.setComposition((LottieComposition) obj);
            lottieDrawable32.animator.setRepeatCount(-1);
            lottieDrawable32.animator.setRepeatMode(1);
            lottieDrawable32.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(color)));
            Unit unit4 = Unit.INSTANCE;
            LottieTask lottieTaskFromRawRes22 = LottieCompositionFactory.fromRawRes(com.android.systemui.R.raw.audio_bars_out, this.this$0.context);
            this.L$0 = flowCollector;
            this.L$1 = lottieDrawable;
            this.L$2 = lottieDrawable32;
            this.I$0 = color;
            this.label = 3;
            obj = LottieTaskExtKt.await(lottieTaskFromRawRes22, this);
            if (obj != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
