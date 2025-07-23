package androidx.compose.ui.text.font;

import android.content.Context;
import android.util.TypedValue;
import androidx.core.content.res.ResourcesCompat;
import kotlin.Result;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidFontLoader implements PlatformFontLoader {
    public final Context context;

    public AndroidFontLoader(Context context) {
        this.context = context.getApplicationContext();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object awaitLoad(androidx.compose.ui.text.font.Font r13, kotlin.coroutines.Continuation r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof androidx.compose.ui.text.font.AndroidFontLoader$awaitLoad$1
            if (r0 == 0) goto L13
            r0 = r14
            androidx.compose.ui.text.font.AndroidFontLoader$awaitLoad$1 r0 = (androidx.compose.ui.text.font.AndroidFontLoader$awaitLoad$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.text.font.AndroidFontLoader$awaitLoad$1 r0 = new androidx.compose.ui.text.font.AndroidFontLoader$awaitLoad$1
            r0.<init>(r12, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r12 = r0.L$1
            r13 = r12
            androidx.compose.ui.text.font.Font r13 = (androidx.compose.ui.text.font.Font) r13
            java.lang.Object r12 = r0.L$0
            androidx.compose.ui.text.font.AndroidFontLoader r12 = (androidx.compose.ui.text.font.AndroidFontLoader) r12
            kotlin.ResultKt.throwOnFailure(r14)
            goto L87
        L33:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L3b:
            kotlin.ResultKt.throwOnFailure(r14)
            return r14
        L3f:
            kotlin.ResultKt.throwOnFailure(r14)
            boolean r14 = r13 instanceof androidx.compose.ui.text.font.AndroidFont
            if (r14 != 0) goto La8
            boolean r14 = r13 instanceof androidx.compose.ui.text.font.ResourceFont
            if (r14 == 0) goto L94
            r14 = r13
            androidx.compose.ui.text.font.ResourceFont r14 = (androidx.compose.ui.text.font.ResourceFont) r14
            android.content.Context r5 = r12.context
            r0.L$0 = r12
            r0.L$1 = r13
            r0.label = r3
            kotlinx.coroutines.CancellableContinuationImpl r2 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r2.<init>(r0, r4)
            r2.initCancellability()
            int r6 = r14.resId
            androidx.compose.ui.text.font.AndroidFontLoader_androidKt$loadAsync$2$1 r9 = new androidx.compose.ui.text.font.AndroidFontLoader_androidKt$loadAsync$2$1
            r9.<init>()
            java.lang.ThreadLocal r14 = androidx.core.content.res.ResourcesCompat.sTempTypedValue
            boolean r14 = r5.isRestricted()
            if (r14 == 0) goto L75
            r14 = -4
            r9.callbackFailAsync(r14)
            goto L80
        L75:
            android.util.TypedValue r7 = new android.util.TypedValue
            r7.<init>()
            r8 = 0
            r10 = 0
            r11 = 0
            androidx.core.content.res.ResourcesCompat.loadFont(r5, r6, r7, r8, r9, r10, r11)
        L80:
            java.lang.Object r14 = r2.getResult()
            if (r14 != r1) goto L87
            return r1
        L87:
            android.graphics.Typeface r14 = (android.graphics.Typeface) r14
            androidx.compose.ui.text.font.ResourceFont r13 = (androidx.compose.ui.text.font.ResourceFont) r13
            androidx.compose.ui.text.font.FontVariation$Settings r13 = r13.variationSettings
            android.content.Context r12 = r12.context
            android.graphics.Typeface r12 = androidx.compose.ui.text.font.PlatformTypefaces_androidKt.setFontVariationSettings(r14, r13, r12)
            return r12
        L94:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r0 = "Unknown font type: "
            r14.<init>(r0)
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        La8:
            androidx.compose.ui.text.font.AndroidFont r13 = (androidx.compose.ui.text.font.AndroidFont) r13
            androidx.compose.ui.text.font.AndroidFont$TypefaceLoader r12 = r13.typefaceLoader
            r0.label = r4
            androidx.compose.ui.text.font.NamedFontLoader r12 = (androidx.compose.ui.text.font.NamedFontLoader) r12
            r12.getClass()
            java.lang.UnsupportedOperationException r12 = new java.lang.UnsupportedOperationException
            java.lang.String r13 = "All preloaded fonts are optional local."
            r12.<init>(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.AndroidFontLoader.awaitLoad(androidx.compose.ui.text.font.Font, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    public final Object getCacheKey() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [kotlin.Result$Failure] */
    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    public final Object loadBlocking(Font font) {
        android.graphics.Typeface failure;
        android.graphics.Typeface typeface;
        if (font instanceof AndroidFont) {
            AndroidFont androidFont = (AndroidFont) font;
            Context context = this.context;
            ((NamedFontLoader) androidFont.typefaceLoader).getClass();
            DeviceFontFamilyNameFont deviceFontFamilyNameFont = androidFont instanceof DeviceFontFamilyNameFont ? (DeviceFontFamilyNameFont) androidFont : null;
            if (deviceFontFamilyNameFont != null) {
                new PlatformTypefacesApi28();
                FontFamily.Companion.getClass();
                GenericFontFamily genericFontFamily = FontFamily.SansSerif;
                String str = genericFontFamily.name;
                String str2 = deviceFontFamilyNameFont.familyName;
                boolean areEqual = Intrinsics.areEqual(str2, str);
                FontWeight fontWeight = deviceFontFamilyNameFont.weight;
                int i = deviceFontFamilyNameFont.style;
                if (areEqual) {
                    typeface = PlatformTypefacesApi28.m769createAndroidTypefaceApi28RetOiIg(genericFontFamily.name, fontWeight, i);
                } else {
                    GenericFontFamily genericFontFamily2 = FontFamily.Serif;
                    if (Intrinsics.areEqual(str2, genericFontFamily2.name)) {
                        typeface = PlatformTypefacesApi28.m769createAndroidTypefaceApi28RetOiIg(genericFontFamily2.name, fontWeight, i);
                    } else {
                        GenericFontFamily genericFontFamily3 = FontFamily.Monospace;
                        if (Intrinsics.areEqual(str2, genericFontFamily3.name)) {
                            typeface = PlatformTypefacesApi28.m769createAndroidTypefaceApi28RetOiIg(genericFontFamily3.name, fontWeight, i);
                        } else {
                            GenericFontFamily genericFontFamily4 = FontFamily.Cursive;
                            if (Intrinsics.areEqual(str2, genericFontFamily4.name)) {
                                typeface = PlatformTypefacesApi28.m769createAndroidTypefaceApi28RetOiIg(genericFontFamily4.name, fontWeight, i);
                            } else {
                                if (str2.length() != 0) {
                                    android.graphics.Typeface m769createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m769createAndroidTypefaceApi28RetOiIg(str2, fontWeight, i);
                                    FontStyle.Companion.getClass();
                                    boolean z = i == FontStyle.Italic;
                                    TypefaceHelperMethodsApi28 typefaceHelperMethodsApi28 = TypefaceHelperMethodsApi28.INSTANCE;
                                    android.graphics.Typeface typeface2 = android.graphics.Typeface.DEFAULT;
                                    int i2 = fontWeight.weight;
                                    typefaceHelperMethodsApi28.getClass();
                                    if (!Intrinsics.areEqual(m769createAndroidTypefaceApi28RetOiIg, android.graphics.Typeface.create(typeface2, i2, z)) && !Intrinsics.areEqual(m769createAndroidTypefaceApi28RetOiIg, PlatformTypefacesApi28.m769createAndroidTypefaceApi28RetOiIg(null, fontWeight, i))) {
                                        r1 = m769createAndroidTypefaceApi28RetOiIg;
                                    }
                                }
                                typeface = r1;
                            }
                        }
                    }
                }
                return PlatformTypefaces_androidKt.setFontVariationSettings(typeface, deviceFontFamilyNameFont.variationSettings, context);
            }
        } else if (font instanceof ResourceFont) {
            FontLoadingStrategy.Companion.getClass();
            int i3 = ((ResourceFont) font).loadingStrategy;
            if (i3 == 0) {
                ResourceFont resourceFont = (ResourceFont) font;
                Context context2 = this.context;
                ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
                r1 = context2.isRestricted() ? null : ResourcesCompat.loadFont(context2, resourceFont.resId, new TypedValue(), 0, null, false, false);
                r1.getClass();
            } else {
                if (i3 != FontLoadingStrategy.OptionalLocal) {
                    if (i3 == FontLoadingStrategy.Async) {
                        throw new UnsupportedOperationException("Unsupported Async font load path");
                    }
                    throw new IllegalArgumentException("Unknown loading type " + ((Object) FontLoadingStrategy.m763toStringimpl(i3)));
                }
                try {
                    int i4 = Result.$r8$clinit;
                    ResourceFont resourceFont2 = (ResourceFont) font;
                    Context context3 = this.context;
                    ThreadLocal threadLocal2 = ResourcesCompat.sTempTypedValue;
                    failure = context3.isRestricted() ? null : ResourcesCompat.loadFont(context3, resourceFont2.resId, new TypedValue(), 0, null, false, false);
                    failure.getClass();
                } catch (Throwable th) {
                    int i5 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                r1 = failure instanceof Result.Failure ? null : failure;
            }
            return PlatformTypefaces_androidKt.setFontVariationSettings(r1, ((ResourceFont) font).variationSettings, this.context);
        }
        return null;
    }
}
