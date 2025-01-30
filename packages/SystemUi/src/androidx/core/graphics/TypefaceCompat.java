package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.CallbackWithHandler;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat$FontInfo;
import androidx.core.provider.FontsContractCompat$FontRequestCallback;
import androidx.core.util.Consumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TypefaceCompat {
    public static final TypefaceCompatApi29Impl sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
    public static final LruCache sTypefaceCache = new LruCache(16);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResourcesCallbackAdapter extends FontsContractCompat$FontRequestCallback {
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

    private TypefaceCompat() {
    }

    public static Typeface createFromFontInfo(Context context, FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr, int i) {
        ParcelFileDescriptor openFileDescriptor;
        sTypefaceCompatImpl.getClass();
        ContentResolver contentResolver = context.getContentResolver();
        try {
            FontFamily.Builder builder = null;
            for (FontsContractCompat$FontInfo fontsContractCompat$FontInfo : fontsContractCompat$FontInfoArr) {
                try {
                    openFileDescriptor = contentResolver.openFileDescriptor(fontsContractCompat$FontInfo.mUri, "r", null);
                } catch (IOException unused) {
                }
                if (openFileDescriptor != null) {
                    try {
                        Font build = new Font.Builder(openFileDescriptor).setWeight(fontsContractCompat$FontInfo.mWeight).setSlant(fontsContractCompat$FontInfo.mItalic ? 1 : 0).setTtcIndex(fontsContractCompat$FontInfo.mTtcIndex).build();
                        if (builder == null) {
                            builder = new FontFamily.Builder(build);
                        } else {
                            builder.addFont(build);
                        }
                    } catch (Throwable th) {
                        try {
                            openFileDescriptor.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } else if (openFileDescriptor == null) {
                }
                openFileDescriptor.close();
            }
            if (builder == null) {
                return null;
            }
            FontFamily build2 = builder.build();
            return new Typeface.CustomFallbackBuilder(build2).setStyle(TypefaceCompatApi29Impl.findBaseFont(build2, i).getStyle()).build();
        } catch (Exception unused2) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002b, code lost:
    
        if (r4.equals(r9) == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Typeface createFromResourcesFamilyXml(final Context context, FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, Resources resources, int i, String str, int i2, final int i3, ResourcesCompat.FontCallback fontCallback, boolean z) {
        Typeface typeface;
        boolean z2 = false;
        Typeface typeface2 = null;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            String str2 = providerResourceEntry.mSystemFontFamilyName;
            if (str2 != null && !str2.isEmpty()) {
                typeface = Typeface.create(str2, 0);
                Typeface create = Typeface.create(Typeface.DEFAULT, 0);
                if (typeface != null) {
                }
            }
            typeface = null;
            if (typeface != null) {
                if (fontCallback != null) {
                    fontCallback.callbackSuccessAsync(typeface);
                }
                return typeface;
            }
            if (!z ? fontCallback == null : providerResourceEntry.mStrategy == 0) {
                z2 = true;
            }
            int i4 = z ? providerResourceEntry.mTimeoutMs : -1;
            Handler handler = new Handler(Looper.getMainLooper());
            ResourcesCallbackAdapter resourcesCallbackAdapter = new ResourcesCallbackAdapter(fontCallback);
            final FontRequest fontRequest = providerResourceEntry.mRequest;
            final CallbackWithHandler callbackWithHandler = new CallbackWithHandler(resourcesCallbackAdapter, handler);
            if (z2) {
                LruCache lruCache = FontRequestWorker.sTypefaceCache;
                final String str3 = fontRequest.mIdentifier + "-" + i3;
                Typeface typeface3 = (Typeface) FontRequestWorker.sTypefaceCache.get(str3);
                if (typeface3 != null) {
                    callbackWithHandler.onTypefaceResult(new FontRequestWorker.TypefaceResult(typeface3));
                    typeface2 = typeface3;
                } else if (i4 == -1) {
                    FontRequestWorker.TypefaceResult fontSync = FontRequestWorker.getFontSync(str3, context, fontRequest, i3);
                    callbackWithHandler.onTypefaceResult(fontSync);
                    typeface2 = fontSync.mTypeface;
                } else {
                    try {
                        try {
                            try {
                                FontRequestWorker.TypefaceResult typefaceResult = (FontRequestWorker.TypefaceResult) FontRequestWorker.DEFAULT_EXECUTOR_SERVICE.submit(new Callable() { // from class: androidx.core.provider.FontRequestWorker.1
                                    public final /* synthetic */ Context val$context;
                                    public final /* synthetic */ String val$id;
                                    public final /* synthetic */ FontRequest val$request;
                                    public final /* synthetic */ int val$style;

                                    public CallableC01501(final String str32, final Context context2, final FontRequest fontRequest2, final int i32) {
                                        r1 = str32;
                                        r2 = context2;
                                        r3 = fontRequest2;
                                        r4 = i32;
                                    }

                                    @Override // java.util.concurrent.Callable
                                    public final Object call() {
                                        return FontRequestWorker.getFontSync(r1, r2, r3, r4);
                                    }
                                }).get(i4, TimeUnit.MILLISECONDS);
                                callbackWithHandler.onTypefaceResult(typefaceResult);
                                typeface2 = typefaceResult.mTypeface;
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (InterruptedException e2) {
                            throw e2;
                        } catch (TimeoutException unused) {
                            throw new InterruptedException("timeout");
                        }
                    } catch (InterruptedException unused2) {
                        callbackWithHandler.onTypefaceResult(new FontRequestWorker.TypefaceResult(-3));
                    }
                }
            } else {
                LruCache lruCache2 = FontRequestWorker.sTypefaceCache;
                final String str4 = fontRequest2.mIdentifier + "-" + i32;
                Typeface typeface4 = (Typeface) FontRequestWorker.sTypefaceCache.get(str4);
                if (typeface4 != null) {
                    callbackWithHandler.onTypefaceResult(new FontRequestWorker.TypefaceResult(typeface4));
                    typeface2 = typeface4;
                } else {
                    Consumer c01512 = new Consumer() { // from class: androidx.core.provider.FontRequestWorker.2
                        public C01512() {
                        }

                        @Override // androidx.core.util.Consumer
                        public final void accept(Object obj) {
                            TypefaceResult typefaceResult2 = (TypefaceResult) obj;
                            if (typefaceResult2 == null) {
                                typefaceResult2 = new TypefaceResult(-3);
                            }
                            CallbackWithHandler.this.onTypefaceResult(typefaceResult2);
                        }
                    };
                    synchronized (FontRequestWorker.LOCK) {
                        SimpleArrayMap simpleArrayMap = FontRequestWorker.PENDING_REPLIES;
                        ArrayList arrayList = (ArrayList) simpleArrayMap.get(str4);
                        if (arrayList != null) {
                            arrayList.add(c01512);
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(c01512);
                            simpleArrayMap.put(str4, arrayList2);
                            final Callable callableC01523 = new Callable() { // from class: androidx.core.provider.FontRequestWorker.3
                                public final /* synthetic */ Context val$context;
                                public final /* synthetic */ String val$id;
                                public final /* synthetic */ FontRequest val$request;
                                public final /* synthetic */ int val$style;

                                public CallableC01523(final String str42, final Context context2, final FontRequest fontRequest2, final int i32) {
                                    r1 = str42;
                                    r2 = context2;
                                    r3 = fontRequest2;
                                    r4 = i32;
                                }

                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    try {
                                        return FontRequestWorker.getFontSync(r1, r2, r3, r4);
                                    } catch (Throwable unused3) {
                                        return new TypefaceResult(-3);
                                    }
                                }
                            };
                            ExecutorService executorService = FontRequestWorker.DEFAULT_EXECUTOR_SERVICE;
                            final Consumer c01534 = new Consumer() { // from class: androidx.core.provider.FontRequestWorker.4
                                public final /* synthetic */ String val$id;

                                public C01534(final String str42) {
                                    r1 = str42;
                                }

                                @Override // androidx.core.util.Consumer
                                public final void accept(Object obj) {
                                    TypefaceResult typefaceResult2 = (TypefaceResult) obj;
                                    synchronized (FontRequestWorker.LOCK) {
                                        SimpleArrayMap simpleArrayMap2 = FontRequestWorker.PENDING_REPLIES;
                                        ArrayList arrayList3 = (ArrayList) simpleArrayMap2.get(r1);
                                        if (arrayList3 == null) {
                                            return;
                                        }
                                        simpleArrayMap2.remove(r1);
                                        for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                                            ((Consumer) arrayList3.get(i5)).accept(typefaceResult2);
                                        }
                                    }
                                }
                            };
                            final Handler handler2 = Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : new Handler();
                            ((ThreadPoolExecutor) executorService).execute(new Runnable(handler2, callableC01523, c01534) { // from class: androidx.core.provider.RequestExecutor$ReplyRunnable
                                public final Callable mCallable;
                                public final Consumer mConsumer;
                                public final Handler mHandler;

                                {
                                    this.mCallable = callableC01523;
                                    this.mConsumer = c01534;
                                    this.mHandler = handler2;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    final Object obj;
                                    try {
                                        obj = this.mCallable.call();
                                    } catch (Exception unused3) {
                                        obj = null;
                                    }
                                    final Consumer consumer = this.mConsumer;
                                    this.mHandler.post(new Runnable(this) { // from class: androidx.core.provider.RequestExecutor$ReplyRunnable.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            consumer.accept(obj);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            }
        } else {
            FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry = (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry;
            sTypefaceCompatImpl.getClass();
            try {
                FontFamily.Builder builder = null;
                for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.mEntries) {
                    try {
                        try {
                            Font build = new Font.Builder(resources, fontFileResourceEntry.mResourceId).setWeight(fontFileResourceEntry.mWeight).setSlant(fontFileResourceEntry.mItalic ? 1 : 0).setTtcIndex(fontFileResourceEntry.mTtcIndex).setFontVariationSettings(fontFileResourceEntry.mVariationSettings).build();
                            if (builder == null) {
                                builder = new FontFamily.Builder(build);
                            } else {
                                builder.addFont(build);
                            }
                        } catch (IOException unused3) {
                        }
                    } catch (Exception unused4) {
                    }
                }
                if (builder != null) {
                    FontFamily build2 = builder.build();
                    typeface2 = new Typeface.CustomFallbackBuilder(build2).setStyle(TypefaceCompatApi29Impl.findBaseFont(build2, i32).getStyle()).build();
                }
            } catch (Exception unused5) {
            }
            if (fontCallback != null) {
                if (typeface2 != null) {
                    fontCallback.callbackSuccessAsync(typeface2);
                } else {
                    fontCallback.callbackFailAsync(-3);
                }
            }
        }
        if (typeface2 != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i32), typeface2);
        }
        return typeface2;
    }

    public static Typeface createFromResourcesFontFile(Resources resources, int i, String str, int i2, int i3) {
        Typeface typeface;
        sTypefaceCompatImpl.getClass();
        try {
            Font build = new Font.Builder(resources, i).build();
            typeface = new Typeface.CustomFallbackBuilder(new FontFamily.Builder(build).build()).setStyle(build.getStyle()).build();
        } catch (Exception unused) {
            typeface = null;
        }
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typeface);
        }
        return typeface;
    }

    public static String createResourceUid(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + '-' + str + '-' + i2 + '-' + i + '-' + i3;
    }
}
