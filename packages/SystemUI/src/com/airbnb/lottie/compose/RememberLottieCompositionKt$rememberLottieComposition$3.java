package com.airbnb.lottie.compose;

import android.content.Context;
import androidx.compose.runtime.MutableState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class RememberLottieCompositionKt$rememberLottieComposition$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $cacheKey;
    final /* synthetic */ Context $context;
    final /* synthetic */ String $fontAssetsFolder;
    final /* synthetic */ String $fontFileExtension;
    final /* synthetic */ String $imageAssetsFolder;
    final /* synthetic */ Function3 $onRetry;
    final /* synthetic */ MutableState<LottieCompositionResultImpl> $result$delegate;
    final /* synthetic */ LottieCompositionSpec $spec;
    int I$0;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RememberLottieCompositionKt$rememberLottieComposition$3(Function3 function3, Context context, LottieCompositionSpec lottieCompositionSpec, String str, String str2, String str3, String str4, MutableState<LottieCompositionResultImpl> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$onRetry = function3;
        this.$context = context;
        this.$spec = lottieCompositionSpec;
        this.$imageAssetsFolder = str;
        this.$fontAssetsFolder = str2;
        this.$fontFileExtension = str3;
        this.$cacheKey = str4;
        this.$result$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RememberLottieCompositionKt$rememberLottieComposition$3(this.$onRetry, this.$context, this.$spec, this.$imageAssetsFolder, this.$fontAssetsFolder, this.$fontFileExtension, this.$cacheKey, this.$result$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RememberLottieCompositionKt$rememberLottieComposition$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:(1:25)|31|32|33|35|36|(1:52)|39|40|41|42|43) */
    /* JADX WARN: Can't wrap try/catch for region: R(19:5|(3:6|7|8)|(1:9)|10|11|12|14|c3|17|(1:19)(2:78|79)|20|21|22|(12:(1:25)|31|32|33|35|36|(1:52)|39|40|41|42|43)|59|60|(2:63|124)|76|77) */
    /* JADX WARN: Can't wrap try/catch for region: R(21:5|6|7|8|9|10|11|12|14|c3|17|(1:19)(2:78|79)|20|21|22|(12:(1:25)|31|32|33|35|36|(1:52)|39|40|41|42|43)|59|60|(2:63|124)|76|77) */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0064, code lost:
    
        if (r14 == r1) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006d, code lost:
    
        if (((java.lang.Boolean) r14).booleanValue() != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b8, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b6, code lost:
    
        if (r14 == r1) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00f8, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00fd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00fe, code lost:
    
        r11 = r13;
        r13 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00eb, code lost:
    
        r14 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00fa, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00fb, code lost:
    
        r11 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00ee, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00ef, code lost:
    
        r13 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00f1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00f2, code lost:
    
        r13 = r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x011a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0125 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x00b6 -> B:9:0x00b9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x00f5 -> B:48:0x001b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x00fe -> B:56:0x00eb). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
