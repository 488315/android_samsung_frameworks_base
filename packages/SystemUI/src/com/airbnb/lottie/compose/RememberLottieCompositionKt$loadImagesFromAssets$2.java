package com.airbnb.lottie.compose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
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
final class RememberLottieCompositionKt$loadImagesFromAssets$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LottieComposition $composition;
    final /* synthetic */ Context $context;
    final /* synthetic */ String $imageAssetsFolder;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RememberLottieCompositionKt$loadImagesFromAssets$2(LottieComposition lottieComposition, Context context, String str, Continuation continuation) {
        super(2, continuation);
        this.$composition = lottieComposition;
        this.$context = context;
        this.$imageAssetsFolder = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RememberLottieCompositionKt$loadImagesFromAssets$2(this.$composition, this.$context, this.$imageAssetsFolder, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RememberLottieCompositionKt$loadImagesFromAssets$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        for (LottieImageAsset lottieImageAsset : ((HashMap) this.$composition.images).values()) {
            Bitmap bitmap = lottieImageAsset.bitmap;
            String str = lottieImageAsset.fileName;
            if (bitmap == null && str.startsWith("data:") && StringsKt__StringsKt.indexOf$default(str, "base64,", 0, false, 6) > 0) {
                try {
                    byte[] decode = Base64.decode(str.substring(StringsKt__StringsKt.indexOf$default(str, ',', 0, 6) + 1), 0);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inDensity = 160;
                    lottieImageAsset.bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length, options);
                } catch (IllegalArgumentException e) {
                    Logger.warning("data URL did not have correct base64 format.", e);
                }
            }
            Context context = this.$context;
            String str2 = this.$imageAssetsFolder;
            if (lottieImageAsset.bitmap == null && str2 != null) {
                try {
                    InputStream open = context.getAssets().open(str2 + ((Object) str));
                    try {
                        BitmapFactory.Options options2 = new BitmapFactory.Options();
                        options2.inScaled = true;
                        options2.inDensity = 160;
                        lottieImageAsset.bitmap = Utils.resizeBitmapIfNeeded(BitmapFactory.decodeStream(open, null, options2), lottieImageAsset.width, lottieImageAsset.height);
                    } catch (IllegalArgumentException e2) {
                        Logger.warning("Unable to decode image.", e2);
                    }
                } catch (IOException e3) {
                    Logger.warning("Unable to open asset.", e3);
                }
            }
        }
        return Unit.INSTANCE;
    }
}
