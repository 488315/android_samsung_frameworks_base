package com.airbnb.lottie.compose;

import android.content.Context;
import android.graphics.Typeface;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.utils.Logger;
import java.util.HashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class RememberLottieCompositionKt$loadFontsFromAssets$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LottieComposition $composition;
    final /* synthetic */ Context $context;
    final /* synthetic */ String $fontAssetsFolder;
    final /* synthetic */ String $fontFileExtension;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RememberLottieCompositionKt$loadFontsFromAssets$2(LottieComposition lottieComposition, Context context, String str, String str2, Continuation continuation) {
        super(2, continuation);
        this.$composition = lottieComposition;
        this.$context = context;
        this.$fontAssetsFolder = str;
        this.$fontFileExtension = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RememberLottieCompositionKt$loadFontsFromAssets$2(this.$composition, this.$context, this.$fontAssetsFolder, this.$fontFileExtension, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RememberLottieCompositionKt$loadFontsFromAssets$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        for (Font font : ((HashMap) this.$composition.fonts).values()) {
            Context context = this.$context;
            String str = this.$fontAssetsFolder;
            String str2 = this.$fontFileExtension;
            StringBuilder sb = new StringBuilder();
            sb.append((Object) str);
            String str3 = font.family;
            String str4 = font.style;
            sb.append((Object) str3);
            sb.append(str2);
            try {
                Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), sb.toString());
                try {
                    int i = 0;
                    boolean contains = StringsKt__StringsKt.contains(str4, "Italic", false);
                    boolean contains2 = StringsKt__StringsKt.contains(str4, "Bold", false);
                    if (contains && contains2) {
                        i = 3;
                    } else if (contains) {
                        i = 2;
                    } else if (contains2) {
                        i = 1;
                    }
                    if (createFromAsset.getStyle() != i) {
                        createFromAsset = Typeface.create(createFromAsset, i);
                    }
                    font.typeface = createFromAsset;
                } catch (Exception unused) {
                    Logger.INSTANCE.getClass();
                }
            } catch (Exception unused2) {
                Logger.INSTANCE.getClass();
            }
        }
        return Unit.INSTANCE;
    }
}
