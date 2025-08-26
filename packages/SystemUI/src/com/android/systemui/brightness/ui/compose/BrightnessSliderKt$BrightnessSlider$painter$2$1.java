package com.android.systemui.brightness.ui.compose;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.compose.runtime.ProduceStateScope;
import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.painter.BitmapPainter;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.common.shared.model.Icon;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
final class BrightnessSliderKt$BrightnessSlider$painter$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ State<Integer> $iconRes$delegate;
    final /* synthetic */ Function3 $imageLoader;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrightnessSliderKt$BrightnessSlider$painter$2$1(Function3 function3, Context context, State<Integer> state, Continuation continuation) {
        super(2, continuation);
        this.$imageLoader = function3;
        this.$context = context;
        this.$iconRes$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BrightnessSliderKt$BrightnessSlider$painter$2$1 brightnessSliderKt$BrightnessSlider$painter$2$1 = new BrightnessSliderKt$BrightnessSlider$painter$2$1(this.$imageLoader, this.$context, this.$iconRes$delegate, continuation);
        brightnessSliderKt$BrightnessSlider$painter$2$1.L$0 = obj;
        return brightnessSliderKt$BrightnessSlider$painter$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BrightnessSliderKt$BrightnessSlider$painter$2$1) create((ProduceStateScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ProduceStateScope produceStateScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProduceStateScope produceStateScope2 = (ProduceStateScope) this.L$0;
            Function3 function3 = this.$imageLoader;
            Integer num = new Integer(((Number) this.$iconRes$delegate.getValue()).intValue());
            Context context = this.$context;
            this.L$0 = produceStateScope2;
            this.label = 1;
            Object objInvoke = function3.invoke(num, context, this);
            if (objInvoke == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objInvoke;
            produceStateScope = produceStateScope2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            produceStateScope = (ProduceStateScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Bitmap bitmap = Utils.toBitmap(((Icon.Loaded) obj).drawable);
        bitmap.getClass();
        produceStateScope.setValue(new BitmapPainter(new AndroidImageBitmap(bitmap), 0L, 0L, 6, null));
        return Unit.INSTANCE;
    }
}
