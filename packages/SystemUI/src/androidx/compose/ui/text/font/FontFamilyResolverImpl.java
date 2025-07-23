package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.SynchronizedObject;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontFamilyResolverImpl implements FontFamily.Resolver {
    public final Function1 createDefaultTypeface;
    public final FontListFontFamilyTypefaceAdapter fontListFontFamilyTypefaceAdapter;
    public final PlatformFontFamilyTypefaceAdapter platformFamilyTypefaceAdapter;
    public final PlatformFontLoader platformFontLoader;
    public final PlatformResolveInterceptor platformResolveInterceptor;
    public final TypefaceRequestCache typefaceRequestCache;

    public FontFamilyResolverImpl(PlatformFontLoader platformFontLoader, PlatformResolveInterceptor platformResolveInterceptor, TypefaceRequestCache typefaceRequestCache, FontListFontFamilyTypefaceAdapter fontListFontFamilyTypefaceAdapter, PlatformFontFamilyTypefaceAdapter platformFontFamilyTypefaceAdapter) {
        this.platformFontLoader = platformFontLoader;
        this.platformResolveInterceptor = platformResolveInterceptor;
        this.typefaceRequestCache = typefaceRequestCache;
        this.fontListFontFamilyTypefaceAdapter = fontListFontFamilyTypefaceAdapter;
        this.platformFamilyTypefaceAdapter = platformFontFamilyTypefaceAdapter;
        this.createDefaultTypeface = new FontFamilyResolverImpl$createDefaultTypeface$1(this);
    }

    public final TypefaceResult resolve(final TypefaceRequest typefaceRequest) {
        final TypefaceRequestCache typefaceRequestCache = this.typefaceRequestCache;
        Function1 function1 = new Function1() { // from class: androidx.compose.ui.text.font.FontFamilyResolverImpl$resolve$result$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code restructure failed: missing block: B:61:0x03f1, code lost:
            
                r4 = (java.util.List) r2.component1();
                r5 = r2.component2();
             */
            /* JADX WARN: Code restructure failed: missing block: B:62:0x03fc, code lost:
            
                if (r4 != null) goto L221;
             */
            /* JADX WARN: Code restructure failed: missing block: B:63:0x03fe, code lost:
            
                r0 = new androidx.compose.ui.text.font.TypefaceResult.Immutable(r5, false, 2, null);
                r6 = true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:64:0x0409, code lost:
            
                r3 = new androidx.compose.ui.text.font.AsyncFontListLoader(r4, r5, r6, r10.asyncTypefaceCache, r8, r9);
                r6 = true;
                kotlinx.coroutines.BuildersKt.launch$default(r10.asyncLoadScope, null, kotlinx.coroutines.CoroutineStart.UNDISPATCHED, new androidx.compose.ui.text.font.FontListFontFamilyTypefaceAdapter$resolve$1(r3, null), 1);
                r0 = new androidx.compose.ui.text.font.TypefaceResult.Async(r3);
             */
            /* JADX WARN: Removed duplicated region for block: B:131:0x03e4 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:13:0x046c  */
            /* JADX WARN: Removed duplicated region for block: B:16:0x046e  */
            /* JADX WARN: Removed duplicated region for block: B:45:0x02b4  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object mo779invoke(java.lang.Object r20) {
                /*
                    Method dump skipped, instructions count: 1150
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.FontFamilyResolverImpl$resolve$result$1.mo779invoke(java.lang.Object):java.lang.Object");
            }
        };
        synchronized (typefaceRequestCache.lock) {
            TypefaceResult typefaceResult = (TypefaceResult) typefaceRequestCache.resultCache.get(typefaceRequest);
            if (typefaceResult != null) {
                if (typefaceResult.getCacheable()) {
                    return typefaceResult;
                }
            }
            try {
                TypefaceResult typefaceResult2 = (TypefaceResult) function1.mo779invoke(new Function1() { // from class: androidx.compose.ui.text.font.TypefaceRequestCache$runCached$currentTypefaceResult$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        TypefaceResult typefaceResult3 = (TypefaceResult) obj;
                        TypefaceRequestCache typefaceRequestCache2 = TypefaceRequestCache.this;
                        SynchronizedObject synchronizedObject = typefaceRequestCache2.lock;
                        TypefaceRequest typefaceRequest2 = typefaceRequest;
                        synchronized (synchronizedObject) {
                            try {
                                if (typefaceResult3.getCacheable()) {
                                    typefaceRequestCache2.resultCache.put(typefaceRequest2, typefaceResult3);
                                } else {
                                    typefaceRequestCache2.resultCache.remove(typefaceRequest2);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
                synchronized (typefaceRequestCache.lock) {
                    try {
                        if (typefaceRequestCache.resultCache.get(typefaceRequest) == null && typefaceResult2.getCacheable()) {
                            typefaceRequestCache.resultCache.put(typefaceRequest, typefaceResult2);
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return typefaceResult2;
            } catch (Exception e) {
                throw new IllegalStateException("Could not load font", e);
            }
        }
    }

    /* renamed from: resolve-DPcqOEQ, reason: not valid java name */
    public final TypefaceResult m762resolveDPcqOEQ(FontFamily fontFamily, FontWeight fontWeight, int i, int i2) {
        PlatformResolveInterceptor platformResolveInterceptor = this.platformResolveInterceptor;
        platformResolveInterceptor.getClass();
        return resolve(new TypefaceRequest(fontFamily, platformResolveInterceptor.interceptFontWeight(fontWeight), i, i2, this.platformFontLoader.getCacheKey(), null));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FontFamilyResolverImpl(androidx.compose.ui.text.font.PlatformFontLoader r7, androidx.compose.ui.text.font.PlatformResolveInterceptor r8, androidx.compose.ui.text.font.TypefaceRequestCache r9, androidx.compose.ui.text.font.FontListFontFamilyTypefaceAdapter r10, androidx.compose.ui.text.font.PlatformFontFamilyTypefaceAdapter r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 2
            if (r13 == 0) goto Lb
            androidx.compose.ui.text.font.PlatformResolveInterceptor$Companion r8 = androidx.compose.ui.text.font.PlatformResolveInterceptor.Companion
            r8.getClass()
            androidx.compose.ui.text.font.PlatformResolveInterceptor$Companion$Default$1 r8 = androidx.compose.ui.text.font.PlatformResolveInterceptor.Companion.Default
        Lb:
            r2 = r8
            r8 = r12 & 4
            if (r8 == 0) goto L12
            androidx.compose.ui.text.font.TypefaceRequestCache r9 = androidx.compose.ui.text.font.FontFamilyResolverKt.GlobalTypefaceRequestCache
        L12:
            r3 = r9
            r8 = r12 & 8
            if (r8 == 0) goto L20
            androidx.compose.ui.text.font.FontListFontFamilyTypefaceAdapter r10 = new androidx.compose.ui.text.font.FontListFontFamilyTypefaceAdapter
            androidx.compose.ui.text.font.AsyncTypefaceCache r8 = androidx.compose.ui.text.font.FontFamilyResolverKt.GlobalAsyncTypefaceCache
            r9 = 0
            r13 = 2
            r10.<init>(r8, r9, r13, r9)
        L20:
            r4 = r10
            r8 = r12 & 16
            if (r8 == 0) goto L2a
            androidx.compose.ui.text.font.PlatformFontFamilyTypefaceAdapter r11 = new androidx.compose.ui.text.font.PlatformFontFamilyTypefaceAdapter
            r11.<init>()
        L2a:
            r0 = r6
            r1 = r7
            r5 = r11
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.FontFamilyResolverImpl.<init>(androidx.compose.ui.text.font.PlatformFontLoader, androidx.compose.ui.text.font.PlatformResolveInterceptor, androidx.compose.ui.text.font.TypefaceRequestCache, androidx.compose.ui.text.font.FontListFontFamilyTypefaceAdapter, androidx.compose.ui.text.font.PlatformFontFamilyTypefaceAdapter, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
