package com.airbnb.lottie.compose;

import android.content.Context;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda0;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda1;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda2;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.compose.LottieCompositionSpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RememberLottieCompositionKt {
    public static final String access$ensureTrailingSlash(String str) {
        if (str == null || StringsKt__StringsKt.isBlank(str)) {
            return null;
        }
        return (str.length() <= 0 || !CharsKt__CharKt.equals(str.charAt(str.length() + (-1)), '/', false)) ? str.concat("/") : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0112 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$lottieComposition(android.content.Context r13, com.airbnb.lottie.compose.LottieCompositionSpec r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, kotlin.coroutines.jvm.internal.ContinuationImpl r19) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.compose.RememberLottieCompositionKt.access$lottieComposition(android.content.Context, com.airbnb.lottie.compose.LottieCompositionSpec, java.lang.String, java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final LottieTask lottieTask(Context context, LottieCompositionSpec lottieCompositionSpec, String str, boolean z) {
        if (lottieCompositionSpec instanceof LottieCompositionSpec.RawRes) {
            return Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__") ? LottieCompositionFactory.fromRawRes(((LottieCompositionSpec.RawRes) lottieCompositionSpec).resId, context) : LottieCompositionFactory.fromRawRes(context, str, ((LottieCompositionSpec.RawRes) lottieCompositionSpec).resId);
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.Url) {
            if (!Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda0(context, ((LottieCompositionSpec.Url) lottieCompositionSpec).url, str, 0), null);
            }
            String str2 = ((LottieCompositionSpec.Url) lottieCompositionSpec).url;
            Map map = LottieCompositionFactory.taskCache;
            String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("url_", str2);
            return LottieCompositionFactory.cache(m, new LottieCompositionFactory$$ExternalSyntheticLambda0(context, str2, m, 0), null);
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.File) {
            if (z) {
                return null;
            }
            LottieCompositionSpec.File file = (LottieCompositionSpec.File) lottieCompositionSpec;
            FileInputStream fileInputStream = new FileInputStream(file.fileName);
            String str3 = file.fileName;
            if (!str3.endsWith("zip")) {
                if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                    str = str3;
                }
                return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(fileInputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(fileInputStream));
            }
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                str = str3;
            }
            return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(zipInputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(zipInputStream));
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.Asset) {
            if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                return LottieCompositionFactory.fromAsset(context, ((LottieCompositionSpec.Asset) lottieCompositionSpec).assetName);
            }
            String str4 = ((LottieCompositionSpec.Asset) lottieCompositionSpec).assetName;
            Map map2 = LottieCompositionFactory.taskCache;
            return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda0(context.getApplicationContext(), str4, str, 1), null);
        }
        if (lottieCompositionSpec instanceof LottieCompositionSpec.JsonString) {
            if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
                str = String.valueOf(((LottieCompositionSpec.JsonString) lottieCompositionSpec).jsonString.hashCode());
            }
            return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(((LottieCompositionSpec.JsonString) lottieCompositionSpec).jsonString, str), null);
        }
        if (!(lottieCompositionSpec instanceof LottieCompositionSpec.ContentProvider)) {
            throw new NoWhenBranchMatchedException();
        }
        LottieCompositionSpec.ContentProvider contentProvider = (LottieCompositionSpec.ContentProvider) lottieCompositionSpec;
        InputStream openInputStream = context.getContentResolver().openInputStream(contentProvider.uri);
        if (Intrinsics.areEqual(str, "__LottieInternalDefaultCacheKey__")) {
            str = contentProvider.uri.toString();
        }
        return LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(openInputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(openInputStream));
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x002e, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x005f, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.airbnb.lottie.compose.LottieCompositionResultImpl rememberLottieComposition(com.airbnb.lottie.compose.LottieCompositionSpec.RawRes r11, androidx.compose.runtime.Composer r12) {
        /*
            androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
            r0 = 1388713460(0x52c615f4, float:4.2538592E11)
            r12.startReplaceableGroup(r0)
            com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$1 r2 = new com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$1
            r0 = 0
            r2.<init>(r0)
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalContext
            java.lang.Object r0 = r12.consume(r0)
            r3 = r0
            android.content.Context r3 = (android.content.Context) r3
            r0 = -3686930(0xffffffffffc7bdee, float:NaN)
            r12.startReplaceableGroup(r0)
            boolean r0 = r12.changed(r11)
            java.lang.Object r1 = r12.rememberedValue()
            androidx.compose.runtime.Composer$Companion r4 = androidx.compose.runtime.Composer.Companion
            if (r0 != 0) goto L30
            r4.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L3c
        L30:
            com.airbnb.lottie.compose.LottieCompositionResultImpl r0 = new com.airbnb.lottie.compose.LottieCompositionResultImpl
            r0.<init>()
            androidx.compose.runtime.MutableState r1 = androidx.compose.runtime.SnapshotStateKt.mutableStateOf$default(r0)
            r12.updateRememberedValue(r1)
        L3c:
            r0 = 0
            r12.end(r0)
            r9 = r1
            androidx.compose.runtime.MutableState r9 = (androidx.compose.runtime.MutableState) r9
            r1 = -3686552(0xffffffffffc7bf68, float:NaN)
            r12.startReplaceableGroup(r1)
            boolean r1 = r12.changed(r11)
            java.lang.String r8 = "__LottieInternalDefaultCacheKey__"
            boolean r5 = r12.changed(r8)
            r1 = r1 | r5
            java.lang.Object r5 = r12.rememberedValue()
            if (r1 != 0) goto L61
            r4.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r1) goto L69
        L61:
            r1 = 1
            com.airbnb.lottie.LottieTask r1 = lottieTask(r3, r11, r8, r1)
            r12.updateRememberedValue(r1)
        L69:
            r12.end(r0)
            com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$3 r1 = new com.airbnb.lottie.compose.RememberLottieCompositionKt$rememberLottieComposition$3
            r10 = 0
            r5 = 0
            java.lang.String r6 = "fonts/"
            java.lang.String r7 = ".ttf"
            r4 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r4, r8, r1, r12)
            java.lang.Object r11 = r9.getValue()
            com.airbnb.lottie.compose.LottieCompositionResultImpl r11 = (com.airbnb.lottie.compose.LottieCompositionResultImpl) r11
            r12.end(r0)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.compose.RememberLottieCompositionKt.rememberLottieComposition(com.airbnb.lottie.compose.LottieCompositionSpec$RawRes, androidx.compose.runtime.Composer):com.airbnb.lottie.compose.LottieCompositionResultImpl");
    }
}
