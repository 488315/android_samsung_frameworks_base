package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda0;
import androidx.core.provider.CallbackWrapper;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat$FontInfo;
import androidx.core.provider.FontsContractCompat$FontRequestCallback;
import androidx.core.provider.RequestExecutor$HandlerExecutor;
import androidx.core.util.Consumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class TypefaceCompat {
    public static final LruCache sTypefaceCache;
    public static final TypefaceCompatApi29Impl sTypefaceCompatImpl;

    public class ResourcesCallbackAdapter extends FontsContractCompat$FontRequestCallback {
        public final ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }

        @Override // androidx.core.provider.FontsContractCompat$FontRequestCallback
        public final void onTypefaceRequestFailed(int i) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.onFontRetrievalFailed(i);
            }
        }

        @Override // androidx.core.provider.FontsContractCompat$FontRequestCallback
        public final void onTypefaceRetrieved(Typeface typeface) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.onFontRetrieved(typeface);
            }
        }
    }

    static {
        Trace.beginSection("TypefaceCompat static init");
        sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
        sTypefaceCache = new LruCache(16);
        Trace.endSection();
    }

    private TypefaceCompat() {
    }

    public static Typeface createFromFontInfo(Context context, FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr, int i) {
        Trace.beginSection("TypefaceCompat.createFromFontInfo");
        try {
            sTypefaceCompatImpl.getClass();
            Typeface typefaceBuild = null;
            try {
                FontFamily fontFamily = TypefaceCompatApi29Impl.getFontFamily(fontsContractCompat$FontInfoArr, context.getContentResolver());
                if (fontFamily != null) {
                    typefaceBuild = new Typeface.CustomFallbackBuilder(fontFamily).setStyle(TypefaceCompatApi29Impl.findBaseFont(fontFamily, i).getStyle()).build();
                }
            } catch (Exception e) {
                Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            }
            return typefaceBuild;
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Typeface createFromResourcesFamilyXml(final Context context, FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, Resources resources, int i, String str, int i2, final int i3, ResourcesCompat.FontCallback fontCallback, boolean z) {
        Typeface typefaceCreate;
        Typeface typeface;
        Typeface typefaceBuild = null;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            String str2 = providerResourceEntry.mSystemFontFamilyName;
            if (str2 == null || str2.isEmpty()) {
                typefaceCreate = null;
                if (typefaceCreate == null) {
                    if (fontCallback != null) {
                        new Handler(Looper.getMainLooper()).post(new ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(fontCallback, typefaceCreate));
                    }
                    return typefaceCreate;
                }
                Object[] objArr = !z ? fontCallback != null : providerResourceEntry.mStrategy != 0;
                int i4 = z ? providerResourceEntry.mTimeoutMs : -1;
                Handler handler = new Handler(Looper.getMainLooper());
                ResourcesCallbackAdapter resourcesCallbackAdapter = new ResourcesCallbackAdapter(fontCallback);
                FontRequest fontRequest = providerResourceEntry.mFallbackRequest;
                final List listOf = fontRequest != null ? List.of(providerResourceEntry.mRequest, fontRequest) : List.of(providerResourceEntry.mRequest);
                final CallbackWrapper callbackWrapper = new CallbackWrapper(resourcesCallbackAdapter, new RequestExecutor$HandlerExecutor(handler));
                if (objArr != true) {
                    final String strCreateCacheId = FontRequestWorker.createCacheId(i3, listOf);
                    typeface = (Typeface) FontRequestWorker.sTypefaceCache.get(strCreateCacheId);
                    if (typeface != null) {
                        callbackWrapper.onTypefaceResult(new FontRequestWorker.TypefaceResult(typeface));
                        typefaceBuild = typeface;
                    } else {
                        Consumer consumer = new Consumer() { // from class: androidx.core.provider.FontRequestWorker.2
                            public AnonymousClass2() {
                            }

                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj) {
                                TypefaceResult typefaceResult = (TypefaceResult) obj;
                                if (typefaceResult == null) {
                                    typefaceResult = new TypefaceResult(-3);
                                }
                                callbackWrapper.onTypefaceResult(typefaceResult);
                            }
                        };
                        synchronized (FontRequestWorker.LOCK) {
                            try {
                                SimpleArrayMap simpleArrayMap = FontRequestWorker.PENDING_REPLIES;
                                ArrayList arrayList = (ArrayList) simpleArrayMap.get(strCreateCacheId);
                                if (arrayList != null) {
                                    arrayList.add(consumer);
                                } else {
                                    ArrayList arrayList2 = new ArrayList();
                                    arrayList2.add(consumer);
                                    simpleArrayMap.put(strCreateCacheId, arrayList2);
                                    final Callable callable = new Callable() { // from class: androidx.core.provider.FontRequestWorker.3
                                        public final /* synthetic */ Context val$context;
                                        public final /* synthetic */ String val$id;
                                        public final /* synthetic */ List val$requests;
                                        public final /* synthetic */ int val$style;

                                        public AnonymousClass3(final String strCreateCacheId2, final Context context2, final List listOf2, final int i32) {
                                            str = strCreateCacheId2;
                                            context = context2;
                                            list = listOf2;
                                            i = i32;
                                        }

                                        @Override // java.util.concurrent.Callable
                                        public final Object call() {
                                            try {
                                                return FontRequestWorker.getFontSync(str, context, list, i);
                                            } catch (Throwable unused) {
                                                return new TypefaceResult(-3);
                                            }
                                        }
                                    };
                                    ExecutorService executorService = FontRequestWorker.DEFAULT_EXECUTOR_SERVICE;
                                    final Consumer consumer2 = new Consumer() { // from class: androidx.core.provider.FontRequestWorker.4
                                        public final /* synthetic */ String val$id;

                                        public AnonymousClass4(final String strCreateCacheId2) {
                                            str = strCreateCacheId2;
                                        }

                                        @Override // androidx.core.util.Consumer
                                        public final void accept(Object obj) {
                                            TypefaceResult typefaceResult = (TypefaceResult) obj;
                                            synchronized (FontRequestWorker.LOCK) {
                                                try {
                                                    SimpleArrayMap simpleArrayMap2 = FontRequestWorker.PENDING_REPLIES;
                                                    ArrayList arrayList3 = (ArrayList) simpleArrayMap2.get(str);
                                                    if (arrayList3 == null) {
                                                        return;
                                                    }
                                                    simpleArrayMap2.remove(str);
                                                    for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                                                        ((Consumer) arrayList3.get(i5)).accept(typefaceResult);
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                        }
                                    };
                                    final Handler handler2 = Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : new Handler();
                                    ((ThreadPoolExecutor) executorService).execute(new Runnable(handler2, callable, consumer2) { // from class: androidx.core.provider.RequestExecutor$ReplyRunnable
                                        public final Callable mCallable;
                                        public final Consumer mConsumer;
                                        public final Handler mHandler;

                                        {
                                            this.mCallable = callable;
                                            this.mConsumer = consumer2;
                                            this.mHandler = handler2;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() throws Exception {
                                            final Object objCall;
                                            try {
                                                objCall = this.mCallable.call();
                                            } catch (Exception unused) {
                                                objCall = null;
                                            }
                                            final Consumer consumer3 = this.mConsumer;
                                            this.mHandler.post(new Runnable(this) { // from class: androidx.core.provider.RequestExecutor$ReplyRunnable.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    consumer3.accept(objCall);
                                                }
                                            });
                                        }
                                    });
                                }
                            } finally {
                            }
                        }
                    }
                } else {
                    if (listOf2.size() > 1) {
                        throw new IllegalArgumentException("Fallbacks with blocking fetches are not supported for performance reasons");
                    }
                    final FontRequest fontRequest2 = (FontRequest) listOf2.get(0);
                    LruCache lruCache = FontRequestWorker.sTypefaceCache;
                    final String strCreateCacheId2 = FontRequestWorker.createCacheId(i32, List.of(fontRequest2));
                    typeface = (Typeface) FontRequestWorker.sTypefaceCache.get(strCreateCacheId2);
                    if (typeface != null) {
                        callbackWrapper.onTypefaceResult(new FontRequestWorker.TypefaceResult(typeface));
                        typefaceBuild = typeface;
                    } else if (i4 == -1) {
                        FontRequestWorker.TypefaceResult fontSync = FontRequestWorker.getFontSync(strCreateCacheId2, context2, List.of(fontRequest2), i32);
                        callbackWrapper.onTypefaceResult(fontSync);
                        typefaceBuild = fontSync.mTypeface;
                    } else {
                        try {
                            try {
                                try {
                                    try {
                                        FontRequestWorker.TypefaceResult typefaceResult = (FontRequestWorker.TypefaceResult) FontRequestWorker.DEFAULT_EXECUTOR_SERVICE.submit(new Callable() { // from class: androidx.core.provider.FontRequestWorker.1
                                            public final /* synthetic */ Context val$context;
                                            public final /* synthetic */ String val$id;
                                            public final /* synthetic */ FontRequest val$request;
                                            public final /* synthetic */ int val$style;

                                            public AnonymousClass1(final String strCreateCacheId22, final Context context2, final FontRequest fontRequest22, final int i32) {
                                                str = strCreateCacheId22;
                                                context = context2;
                                                fontRequest = fontRequest22;
                                                i = i32;
                                            }

                                            @Override // java.util.concurrent.Callable
                                            public final Object call() {
                                                return FontRequestWorker.getFontSync(str, context, List.of(fontRequest), i);
                                            }
                                        }).get(i4, TimeUnit.MILLISECONDS);
                                        callbackWrapper.onTypefaceResult(typefaceResult);
                                        typefaceBuild = typefaceResult.mTypeface;
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    }
                                } catch (TimeoutException unused) {
                                    throw new InterruptedException("timeout");
                                }
                            } catch (InterruptedException e2) {
                                throw e2;
                            }
                        } catch (InterruptedException unused2) {
                            callbackWrapper.onTypefaceResult(new FontRequestWorker.TypefaceResult(-3));
                        }
                    }
                }
            } else {
                typefaceCreate = Typeface.create(str2, 0);
                Typeface typefaceCreate2 = Typeface.create(Typeface.DEFAULT, 0);
                if (typefaceCreate == null || typefaceCreate.equals(typefaceCreate2)) {
                }
                if (typefaceCreate == null) {
                }
            }
        } else {
            FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry = (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry;
            sTypefaceCompatImpl.getClass();
            try {
                FontFamily.Builder builder = null;
                for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.mEntries) {
                    try {
                        Font fontBuild = new Font.Builder(resources, fontFileResourceEntry.mResourceId).setWeight(fontFileResourceEntry.mWeight).setSlant(fontFileResourceEntry.mItalic ? 1 : 0).setTtcIndex(fontFileResourceEntry.mTtcIndex).setFontVariationSettings(fontFileResourceEntry.mVariationSettings).build();
                        if (builder == null) {
                            builder = new FontFamily.Builder(fontBuild);
                        } else {
                            builder.addFont(fontBuild);
                        }
                    } catch (IOException unused3) {
                    }
                }
                if (builder != null) {
                    FontFamily fontFamilyBuild = builder.build();
                    typefaceBuild = new Typeface.CustomFallbackBuilder(fontFamilyBuild).setStyle(TypefaceCompatApi29Impl.findBaseFont(fontFamilyBuild, i32).getStyle()).build();
                }
            } catch (Exception e3) {
                Log.w("TypefaceCompatApi29Impl", "Font load failed", e3);
            }
            if (fontCallback != null) {
                if (typefaceBuild != null) {
                    new Handler(Looper.getMainLooper()).post(new ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(fontCallback, typefaceBuild));
                } else {
                    fontCallback.callbackFailAsync(-3);
                }
            }
        }
        if (typefaceBuild != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i32), typefaceBuild);
        }
        return typefaceBuild;
    }

    public static Typeface createFromResourcesFontFile(Resources resources, int i, String str, int i2, int i3) {
        Typeface typefaceBuild;
        sTypefaceCompatImpl.getClass();
        try {
            Font fontBuild = new Font.Builder(resources, i).build();
            typefaceBuild = new Typeface.CustomFallbackBuilder(new FontFamily.Builder(fontBuild).build()).setStyle(fontBuild.getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            typefaceBuild = null;
        }
        if (typefaceBuild != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typefaceBuild);
        }
        return typefaceBuild;
    }

    public static String createResourceUid(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + '-' + str + '-' + i2 + '-' + i + '-' + i3;
    }
}
