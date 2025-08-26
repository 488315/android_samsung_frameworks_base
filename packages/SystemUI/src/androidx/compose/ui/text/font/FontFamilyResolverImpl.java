package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.AsyncTypefaceCache;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.PlatformResolveInterceptor;
import androidx.compose.ui.text.font.TypefaceResult;
import androidx.compose.ui.text.platform.SynchronizedObject;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;

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

            /* JADX WARN: Removed duplicated region for block: B:143:0x02b4  */
            /* JADX WARN: Removed duplicated region for block: B:220:0x03fe  */
            /* JADX WARN: Removed duplicated region for block: B:221:0x0409  */
            /* JADX WARN: Removed duplicated region for block: B:238:0x046c  */
            /* JADX WARN: Removed duplicated region for block: B:239:0x046e  */
            /* JADX WARN: Removed duplicated region for block: B:258:0x03e4 A[SYNTHETIC] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) {
                Function1 function12;
                int size;
                int i;
                Pair pair;
                List list;
                boolean z;
                Object async;
                Object objMo781invoke;
                AsyncTypefaceCache.AsyncTypefaceResult asyncTypefaceResult;
                Object failure;
                TypefaceResult.Immutable immutable;
                android.graphics.Typeface typefaceM771createAndroidTypefaceApi28RetOiIg;
                Function1 function13 = (Function1) obj;
                FontFamilyResolverImpl fontFamilyResolverImpl = this.this$0;
                FontListFontFamilyTypefaceAdapter fontListFontFamilyTypefaceAdapter = fontFamilyResolverImpl.fontListFontFamilyTypefaceAdapter;
                TypefaceRequest typefaceRequest2 = typefaceRequest;
                PlatformFontLoader platformFontLoader = fontFamilyResolverImpl.platformFontLoader;
                Function1 function14 = fontFamilyResolverImpl.createDefaultTypeface;
                fontListFontFamilyTypefaceAdapter.getClass();
                FontFamily fontFamily = typefaceRequest2.fontFamily;
                if (fontFamily instanceof FontListFontFamily) {
                    FontMatcher fontMatcher = FontListFontFamilyTypefaceAdapter.fontMatcher;
                    List list2 = ((FontListFontFamily) fontFamily).fonts;
                    FontWeight fontWeight = typefaceRequest2.fontWeight;
                    int i2 = typefaceRequest2.fontStyle;
                    fontMatcher.getClass();
                    ArrayList arrayList = new ArrayList(list2.size());
                    List list3 = list2;
                    int size2 = list3.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        Object obj2 = list2.get(i3);
                        Font font = (Font) obj2;
                        if (Intrinsics.areEqual(font.getWeight(), fontWeight)) {
                            int iMo762getStyle_LCdwA = font.mo762getStyle_LCdwA();
                            FontStyle.Companion companion = FontStyle.Companion;
                            if (iMo762getStyle_LCdwA == i2) {
                                arrayList.add(obj2);
                            }
                        }
                    }
                    if (arrayList.isEmpty()) {
                        ArrayList arrayList2 = new ArrayList(list2.size());
                        int size3 = list3.size();
                        for (int i4 = 0; i4 < size3; i4++) {
                            Object obj3 = list2.get(i4);
                            int iMo762getStyle_LCdwA2 = ((Font) obj3).mo762getStyle_LCdwA();
                            FontStyle.Companion companion2 = FontStyle.Companion;
                            if (iMo762getStyle_LCdwA2 == i2) {
                                arrayList2.add(obj3);
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            list2 = arrayList2;
                        }
                        List list4 = list2;
                        FontWeight.Companion.getClass();
                        if (fontWeight.compareTo(FontWeight.W400) < 0) {
                            List list5 = list4;
                            int size4 = list5.size();
                            FontWeight fontWeight2 = null;
                            FontWeight fontWeight3 = null;
                            int i5 = 0;
                            while (true) {
                                if (i5 >= size4) {
                                    break;
                                }
                                FontWeight weight = ((Font) list4.get(i5)).getWeight();
                                if (Intrinsics.compare(weight.weight, fontWeight.weight) >= 0) {
                                    if (Intrinsics.compare(weight.weight, fontWeight.weight) <= 0) {
                                        fontWeight2 = weight;
                                        fontWeight3 = fontWeight2;
                                        break;
                                    }
                                    if (fontWeight3 == null || Intrinsics.compare(weight.weight, fontWeight3.weight) < 0) {
                                        fontWeight3 = weight;
                                    }
                                } else if (fontWeight2 == null || Intrinsics.compare(weight.weight, fontWeight2.weight) > 0) {
                                    fontWeight2 = weight;
                                }
                                i5++;
                            }
                            if (fontWeight2 == null) {
                                fontWeight2 = fontWeight3;
                            }
                            arrayList = new ArrayList(list4.size());
                            int size5 = list5.size();
                            for (int i6 = 0; i6 < size5; i6++) {
                                Object obj4 = list4.get(i6);
                                if (Intrinsics.areEqual(((Font) obj4).getWeight(), fontWeight2)) {
                                    arrayList.add(obj4);
                                }
                            }
                        } else {
                            FontWeight fontWeight4 = FontWeight.W500;
                            if (fontWeight.compareTo(fontWeight4) > 0) {
                                List list6 = list4;
                                int size6 = list6.size();
                                FontWeight fontWeight5 = null;
                                FontWeight fontWeight6 = null;
                                int i7 = 0;
                                while (true) {
                                    if (i7 >= size6) {
                                        break;
                                    }
                                    FontWeight weight2 = ((Font) list4.get(i7)).getWeight();
                                    if (Intrinsics.compare(weight2.weight, fontWeight.weight) >= 0) {
                                        if (Intrinsics.compare(weight2.weight, fontWeight.weight) <= 0) {
                                            fontWeight5 = weight2;
                                            fontWeight6 = fontWeight5;
                                            break;
                                        }
                                        if (fontWeight6 == null || Intrinsics.compare(weight2.weight, fontWeight6.weight) < 0) {
                                            fontWeight6 = weight2;
                                        }
                                    } else if (fontWeight5 == null || Intrinsics.compare(weight2.weight, fontWeight5.weight) > 0) {
                                        fontWeight5 = weight2;
                                    }
                                    i7++;
                                }
                                if (fontWeight6 != null) {
                                    fontWeight5 = fontWeight6;
                                }
                                arrayList = new ArrayList(list4.size());
                                int size7 = list6.size();
                                for (int i8 = 0; i8 < size7; i8++) {
                                    Object obj5 = list4.get(i8);
                                    if (Intrinsics.areEqual(((Font) obj5).getWeight(), fontWeight5)) {
                                        arrayList.add(obj5);
                                    }
                                }
                            } else {
                                List list7 = list4;
                                int size8 = list7.size();
                                FontWeight fontWeight7 = null;
                                FontWeight fontWeight8 = null;
                                int i9 = 0;
                                while (true) {
                                    if (i9 >= size8) {
                                        function12 = function14;
                                        break;
                                    }
                                    FontWeight weight3 = ((Font) list4.get(i9)).getWeight();
                                    function12 = function14;
                                    if (Intrinsics.compare(weight3.weight, fontWeight4.weight) <= 0) {
                                        if (Intrinsics.compare(weight3.weight, fontWeight.weight) >= 0) {
                                            if (Intrinsics.compare(weight3.weight, fontWeight.weight) <= 0) {
                                                fontWeight7 = weight3;
                                                fontWeight8 = fontWeight7;
                                                break;
                                            }
                                            if (fontWeight8 == null || Intrinsics.compare(weight3.weight, fontWeight8.weight) < 0) {
                                                fontWeight8 = weight3;
                                            }
                                        } else if (fontWeight7 == null || Intrinsics.compare(weight3.weight, fontWeight7.weight) > 0) {
                                            fontWeight7 = weight3;
                                        }
                                    }
                                    i9++;
                                    function14 = function12;
                                }
                                if (fontWeight8 != null) {
                                    fontWeight7 = fontWeight8;
                                }
                                ArrayList arrayList3 = new ArrayList(list4.size());
                                int size9 = list7.size();
                                for (int i10 = 0; i10 < size9; i10++) {
                                    Object obj6 = list4.get(i10);
                                    if (Intrinsics.areEqual(((Font) obj6).getWeight(), fontWeight7)) {
                                        arrayList3.add(obj6);
                                    }
                                }
                                if (arrayList3.isEmpty()) {
                                    FontWeight.Companion.getClass();
                                    FontWeight fontWeight9 = FontWeight.W500;
                                    int size10 = list7.size();
                                    FontWeight fontWeight10 = null;
                                    FontWeight fontWeight11 = null;
                                    int i11 = 0;
                                    while (true) {
                                        if (i11 >= size10) {
                                            break;
                                        }
                                        FontWeight weight4 = ((Font) list4.get(i11)).getWeight();
                                        if (fontWeight9 == null || Intrinsics.compare(weight4.weight, fontWeight9.weight) >= 0) {
                                            if (Intrinsics.compare(weight4.weight, fontWeight.weight) >= 0) {
                                                if (Intrinsics.compare(weight4.weight, fontWeight.weight) <= 0) {
                                                    fontWeight10 = weight4;
                                                    fontWeight11 = fontWeight10;
                                                    break;
                                                }
                                                if (fontWeight11 == null || Intrinsics.compare(weight4.weight, fontWeight11.weight) < 0) {
                                                    fontWeight11 = weight4;
                                                }
                                            } else if (fontWeight10 == null || Intrinsics.compare(weight4.weight, fontWeight10.weight) > 0) {
                                                fontWeight10 = weight4;
                                            }
                                        }
                                        i11++;
                                    }
                                    if (fontWeight11 != null) {
                                        fontWeight10 = fontWeight11;
                                    }
                                    arrayList3 = new ArrayList(list4.size());
                                    int size11 = list7.size();
                                    for (int i12 = 0; i12 < size11; i12++) {
                                        Object obj7 = list4.get(i12);
                                        if (Intrinsics.areEqual(((Font) obj7).getWeight(), fontWeight10)) {
                                            arrayList3.add(obj7);
                                        }
                                    }
                                }
                                arrayList = arrayList3;
                                AsyncTypefaceCache asyncTypefaceCache = fontListFontFamilyTypefaceAdapter.asyncTypefaceCache;
                                size = arrayList.size();
                                List listMutableListOf = null;
                                i = 0;
                                while (true) {
                                    if (i >= size) {
                                    }
                                    i++;
                                }
                                list = (List) pair.component1();
                                Object objComponent2 = pair.component2();
                                if (list == null) {
                                }
                            }
                        }
                        function12 = function14;
                        AsyncTypefaceCache asyncTypefaceCache2 = fontListFontFamilyTypefaceAdapter.asyncTypefaceCache;
                        size = arrayList.size();
                        List listMutableListOf2 = null;
                        i = 0;
                        while (true) {
                            if (i >= size) {
                            }
                            i++;
                        }
                        list = (List) pair.component1();
                        Object objComponent22 = pair.component2();
                        if (list == null) {
                        }
                    } else {
                        function12 = function14;
                        AsyncTypefaceCache asyncTypefaceCache22 = fontListFontFamilyTypefaceAdapter.asyncTypefaceCache;
                        size = arrayList.size();
                        List listMutableListOf22 = null;
                        i = 0;
                        while (true) {
                            if (i >= size) {
                                pair = new Pair(listMutableListOf22, ((FontFamilyResolverImpl$createDefaultTypeface$1) function12).mo781invoke(typefaceRequest2));
                                break;
                            }
                            Font font2 = (Font) arrayList.get(i);
                            int iMo759getLoadingStrategyPKNRLFQ = font2.mo759getLoadingStrategyPKNRLFQ();
                            FontLoadingStrategy.Companion.getClass();
                            if (iMo759getLoadingStrategyPKNRLFQ == 0) {
                                synchronized (asyncTypefaceCache22.cacheLock) {
                                    try {
                                        AsyncTypefaceCache.Key key = new AsyncTypefaceCache.Key(font2, platformFontLoader.getCacheKey());
                                        AsyncTypefaceCache.AsyncTypefaceResult asyncTypefaceResult2 = (AsyncTypefaceCache.AsyncTypefaceResult) asyncTypefaceCache22.resultCache.get(key);
                                        if (asyncTypefaceResult2 == null) {
                                            asyncTypefaceResult2 = (AsyncTypefaceCache.AsyncTypefaceResult) asyncTypefaceCache22.permanentCache.get(key);
                                        }
                                        if (asyncTypefaceResult2 != null) {
                                            objMo781invoke = asyncTypefaceResult2.result;
                                        } else {
                                            Unit unit = Unit.INSTANCE;
                                            try {
                                                objMo781invoke = platformFontLoader.loadBlocking(font2);
                                            } catch (Exception unused) {
                                                objMo781invoke = ((FontFamilyResolverImpl$createDefaultTypeface$1) function12).mo781invoke(typefaceRequest2);
                                            }
                                            AsyncTypefaceCache.put$default(asyncTypefaceCache22, font2, platformFontLoader, objMo781invoke);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                if (objMo781invoke == null) {
                                    objMo781invoke = ((FontFamilyResolverImpl$createDefaultTypeface$1) function12).mo781invoke(typefaceRequest2);
                                }
                                pair = new Pair(listMutableListOf22, FontSynthesis_androidKt.m770synthesizeTypefaceFxwP2eA(typefaceRequest2.fontSynthesis, objMo781invoke, font2, typefaceRequest2.fontWeight, typefaceRequest2.fontStyle));
                            } else if (iMo759getLoadingStrategyPKNRLFQ == FontLoadingStrategy.OptionalLocal) {
                                synchronized (asyncTypefaceCache22.cacheLock) {
                                    try {
                                        AsyncTypefaceCache.Key key2 = new AsyncTypefaceCache.Key(font2, platformFontLoader.getCacheKey());
                                        AsyncTypefaceCache.AsyncTypefaceResult asyncTypefaceResult3 = (AsyncTypefaceCache.AsyncTypefaceResult) asyncTypefaceCache22.resultCache.get(key2);
                                        if (asyncTypefaceResult3 == null) {
                                            asyncTypefaceResult3 = (AsyncTypefaceCache.AsyncTypefaceResult) asyncTypefaceCache22.permanentCache.get(key2);
                                        }
                                        if (asyncTypefaceResult3 != null) {
                                            failure = asyncTypefaceResult3.result;
                                        } else {
                                            Unit unit2 = Unit.INSTANCE;
                                            try {
                                                int i13 = Result.$r8$clinit;
                                                failure = platformFontLoader.loadBlocking(font2);
                                            } catch (Throwable th2) {
                                                int i14 = Result.$r8$clinit;
                                                failure = new Result.Failure(th2);
                                            }
                                            if (failure instanceof Result.Failure) {
                                                failure = null;
                                            }
                                            AsyncTypefaceCache.put$default(asyncTypefaceCache22, font2, platformFontLoader, failure);
                                        }
                                    } catch (Throwable th3) {
                                        throw th3;
                                    }
                                }
                                if (failure != null) {
                                    pair = new Pair(listMutableListOf22, FontSynthesis_androidKt.m770synthesizeTypefaceFxwP2eA(typefaceRequest2.fontSynthesis, failure, font2, typefaceRequest2.fontWeight, typefaceRequest2.fontStyle));
                                    break;
                                }
                                i++;
                            } else {
                                if (iMo759getLoadingStrategyPKNRLFQ != FontLoadingStrategy.Async) {
                                    throw new IllegalStateException("Unknown font type " + font2);
                                }
                                asyncTypefaceCache22.getClass();
                                AsyncTypefaceCache.Key key3 = new AsyncTypefaceCache.Key(font2, platformFontLoader.getCacheKey());
                                synchronized (asyncTypefaceCache22.cacheLock) {
                                    asyncTypefaceResult = (AsyncTypefaceCache.AsyncTypefaceResult) asyncTypefaceCache22.resultCache.get(key3);
                                    if (asyncTypefaceResult == null) {
                                        asyncTypefaceResult = (AsyncTypefaceCache.AsyncTypefaceResult) asyncTypefaceCache22.permanentCache.get(key3);
                                    }
                                }
                                if (asyncTypefaceResult != null) {
                                    Object obj8 = asyncTypefaceResult.result;
                                    if (obj8 != null) {
                                        pair = new Pair(listMutableListOf22, FontSynthesis_androidKt.m770synthesizeTypefaceFxwP2eA(typefaceRequest2.fontSynthesis, obj8, font2, typefaceRequest2.fontWeight, typefaceRequest2.fontStyle));
                                        break;
                                    }
                                } else if (listMutableListOf22 == null) {
                                    listMutableListOf22 = CollectionsKt__CollectionsKt.mutableListOf(font2);
                                } else {
                                    listMutableListOf22.add(font2);
                                }
                                i++;
                            }
                        }
                        list = (List) pair.component1();
                        Object objComponent222 = pair.component2();
                        if (list == null) {
                            async = new TypefaceResult.Immutable(objComponent222, false, 2, null);
                            z = true;
                        } else {
                            AsyncFontListLoader asyncFontListLoader = new AsyncFontListLoader(list, objComponent222, typefaceRequest2, fontListFontFamilyTypefaceAdapter.asyncTypefaceCache, function13, platformFontLoader);
                            z = true;
                            BuildersKt.launch$default(fontListFontFamilyTypefaceAdapter.asyncLoadScope, null, CoroutineStart.UNDISPATCHED, new FontListFontFamilyTypefaceAdapter$resolve$1(asyncFontListLoader, null), 1);
                            async = new TypefaceResult.Async(asyncFontListLoader);
                        }
                    }
                } else {
                    async = null;
                    z = true;
                }
                if (async != null) {
                    return async;
                }
                PlatformFontFamilyTypefaceAdapter platformFontFamilyTypefaceAdapter = this.this$0.platformFamilyTypefaceAdapter;
                TypefaceRequest typefaceRequest3 = typefaceRequest;
                platformFontFamilyTypefaceAdapter.getClass();
                FontFamily fontFamily2 = typefaceRequest3.fontFamily;
                boolean z2 = fontFamily2 == null ? z : fontFamily2 instanceof DefaultFontFamily;
                PlatformTypefaces platformTypefaces = platformFontFamilyTypefaceAdapter.platformTypefaceResolver;
                int i15 = typefaceRequest3.fontStyle;
                FontWeight fontWeight12 = typefaceRequest3.fontWeight;
                if (z2) {
                    ((PlatformTypefacesApi28) platformTypefaces).getClass();
                    typefaceM771createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(null, fontWeight12, i15);
                } else {
                    if (!(fontFamily2 instanceof GenericFontFamily)) {
                        if (fontFamily2 instanceof LoadedFontFamily) {
                            ((LoadedFontFamily) fontFamily2).getClass();
                            throw null;
                        }
                        immutable = null;
                        if (immutable == null) {
                            return immutable;
                        }
                        throw new IllegalStateException("Could not load font");
                    }
                    ((PlatformTypefacesApi28) platformTypefaces).getClass();
                    typefaceM771createAndroidTypefaceApi28RetOiIg = PlatformTypefacesApi28.m771createAndroidTypefaceApi28RetOiIg(((GenericFontFamily) fontFamily2).name, fontWeight12, i15);
                }
                immutable = new TypefaceResult.Immutable(typefaceM771createAndroidTypefaceApi28RetOiIg, false, 2, null);
                if (immutable == null) {
                }
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
                TypefaceResult typefaceResult2 = (TypefaceResult) function1.mo781invoke(new Function1() { // from class: androidx.compose.ui.text.font.TypefaceRequestCache$runCached$currentTypefaceResult$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        TypefaceResult typefaceResult3 = (TypefaceResult) obj;
                        TypefaceRequestCache typefaceRequestCache2 = typefaceRequestCache;
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
    public final TypefaceResult m764resolveDPcqOEQ(FontFamily fontFamily, FontWeight fontWeight, int i, int i2) {
        PlatformResolveInterceptor platformResolveInterceptor = this.platformResolveInterceptor;
        platformResolveInterceptor.getClass();
        return resolve(new TypefaceRequest(fontFamily, platformResolveInterceptor.interceptFontWeight(fontWeight), i, i2, this.platformFontLoader.getCacheKey(), null));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    public FontFamilyResolverImpl(PlatformFontLoader platformFontLoader, PlatformResolveInterceptor platformResolveInterceptor, TypefaceRequestCache typefaceRequestCache, FontListFontFamilyTypefaceAdapter fontListFontFamilyTypefaceAdapter, PlatformFontFamilyTypefaceAdapter platformFontFamilyTypefaceAdapter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            PlatformResolveInterceptor.Companion.getClass();
            platformResolveInterceptor = PlatformResolveInterceptor.Companion.Default;
        }
        this(platformFontLoader, platformResolveInterceptor, (i & 4) != 0 ? FontFamilyResolverKt.GlobalTypefaceRequestCache : typefaceRequestCache, (i & 8) != 0 ? new FontListFontFamilyTypefaceAdapter(FontFamilyResolverKt.GlobalAsyncTypefaceCache, null, 2, 0 == true ? 1 : 0) : fontListFontFamilyTypefaceAdapter, (i & 16) != 0 ? new PlatformFontFamilyTypefaceAdapter() : platformFontFamilyTypefaceAdapter);
    }
}
