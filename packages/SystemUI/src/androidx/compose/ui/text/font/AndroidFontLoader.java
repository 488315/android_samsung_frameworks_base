package androidx.compose.ui.text.font;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import androidx.compose.ui.text.font.AndroidFont;
import androidx.core.content.res.ResourcesCompat;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes.dex */
public final class AndroidFontLoader implements PlatformFontLoader {
    public final Context context;

    /* renamed from: androidx.compose.ui.text.font.AndroidFontLoader$awaitLoad$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AndroidFontLoader.this.awaitLoad(null, this);
        }
    }

    public AndroidFontLoader(Context context) {
        this.context = context.getApplicationContext();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object awaitLoad(Font font, Continuation continuation) throws Resources.NotFoundException {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object result = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(result);
            if (font instanceof AndroidFont) {
                AndroidFont.TypefaceLoader typefaceLoader = ((AndroidFont) font).typefaceLoader;
                anonymousClass1.label = 1;
                ((NamedFontLoader) typefaceLoader).getClass();
                throw new UnsupportedOperationException("All preloaded fonts are optional local.");
            }
            if (!(font instanceof ResourceFont)) {
                throw new IllegalArgumentException("Unknown font type: " + font);
            }
            final ResourceFont resourceFont = (ResourceFont) font;
            Context context = this.context;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = font;
            anonymousClass1.label = 2;
            final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass1), 1);
            cancellableContinuationImpl.initCancellability();
            int i3 = resourceFont.resId;
            ResourcesCompat.FontCallback fontCallback = new ResourcesCompat.FontCallback() { // from class: androidx.compose.ui.text.font.AndroidFontLoader_androidKt$loadAsync$2$1
                @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                public final void onFontRetrievalFailed(int i4) {
                    cancellableContinuationImpl.cancel(new IllegalStateException("Unable to load font " + resourceFont + " (reason=" + i4 + ')'));
                }

                @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                public final void onFontRetrieved(android.graphics.Typeface typeface) {
                    int i4 = Result.$r8$clinit;
                    cancellableContinuationImpl.resumeWith(typeface);
                }
            };
            ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
            if (context.isRestricted()) {
                fontCallback.callbackFailAsync(-4);
            } else {
                ResourcesCompat.loadFont(context, i3, new TypedValue(), 0, fontCallback, false, false);
            }
            result = cancellableContinuationImpl.getResult();
            if (result == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 == 1) {
                ResultKt.throwOnFailure(result);
                return result;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            font = (Font) anonymousClass1.L$1;
            this = (AndroidFontLoader) anonymousClass1.L$0;
            ResultKt.throwOnFailure(result);
        }
        return PlatformTypefaces_androidKt.setFontVariationSettings((android.graphics.Typeface) result, ((ResourceFont) font).variationSettings, this.context);
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
        android.graphics.Typeface typefaceM771createAndroidTypefaceApi28RetOiIg;
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
                boolean zAreEqual = Intrinsics.areEqual(str2, str);
                FontWeight fontWeight = deviceFontFamilyNameFont.weight;
                int i = deviceFontFamilyNameFont.style;
                if (zAreEqual) {
                    typefaceM771createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(genericFontFamily.name, fontWeight, i);
                } else {
                    GenericFontFamily genericFontFamily2 = FontFamily.Serif;
                    if (Intrinsics.areEqual(str2, genericFontFamily2.name)) {
                        typefaceM771createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(genericFontFamily2.name, fontWeight, i);
                    } else {
                        GenericFontFamily genericFontFamily3 = FontFamily.Monospace;
                        if (Intrinsics.areEqual(str2, genericFontFamily3.name)) {
                            typefaceM771createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(genericFontFamily3.name, fontWeight, i);
                        } else {
                            GenericFontFamily genericFontFamily4 = FontFamily.Cursive;
                            if (Intrinsics.areEqual(str2, genericFontFamily4.name)) {
                                typefaceM771createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(genericFontFamily4.name, fontWeight, i);
                            } else {
                                if (str2.length() != 0) {
                                    android.graphics.Typeface typefaceM771createAndroidTypefaceApi28RetOiIg2 = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(str2, fontWeight, i);
                                    FontStyle.Companion.getClass();
                                    boolean z = i == FontStyle.Italic;
                                    TypefaceHelperMethodsApi28 typefaceHelperMethodsApi28 = TypefaceHelperMethodsApi28.INSTANCE;
                                    android.graphics.Typeface typeface = android.graphics.Typeface.DEFAULT;
                                    int i2 = fontWeight.weight;
                                    typefaceHelperMethodsApi28.getClass();
                                    if (!Intrinsics.areEqual(typefaceM771createAndroidTypefaceApi28RetOiIg2, android.graphics.Typeface.create(typeface, i2, z)) && !Intrinsics.areEqual(typefaceM771createAndroidTypefaceApi28RetOiIg2, PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(null, fontWeight, i))) {
                                        typefaceLoadFont = typefaceM771createAndroidTypefaceApi28RetOiIg2;
                                    }
                                }
                                typefaceM771createAndroidTypefaceApi28RetOiIg = typefaceLoadFont;
                            }
                        }
                    }
                }
                return PlatformTypefaces_androidKt.setFontVariationSettings(typefaceM771createAndroidTypefaceApi28RetOiIg, deviceFontFamilyNameFont.variationSettings, context);
            }
        } else if (font instanceof ResourceFont) {
            FontLoadingStrategy.Companion.getClass();
            int i3 = ((ResourceFont) font).loadingStrategy;
            if (i3 == 0) {
                ResourceFont resourceFont = (ResourceFont) font;
                Context context2 = this.context;
                ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
                typefaceLoadFont = context2.isRestricted() ? null : ResourcesCompat.loadFont(context2, resourceFont.resId, new TypedValue(), 0, null, false, false);
                typefaceLoadFont.getClass();
            } else {
                if (i3 != FontLoadingStrategy.OptionalLocal) {
                    if (i3 == FontLoadingStrategy.Async) {
                        throw new UnsupportedOperationException("Unsupported Async font load path");
                    }
                    throw new IllegalArgumentException("Unknown loading type " + ((Object) FontLoadingStrategy.m765toStringimpl(i3)));
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
                typefaceLoadFont = failure instanceof Result.Failure ? null : failure;
            }
            return PlatformTypefaces_androidKt.setFontVariationSettings(typefaceLoadFont, ((ResourceFont) font).variationSettings, this.context);
        }
        return null;
    }
}
