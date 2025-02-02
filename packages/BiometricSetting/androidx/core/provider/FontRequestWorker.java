package androidx.core.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
final class FontRequestWorker {
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE;
    static final Object LOCK;
    static final SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> PENDING_REPLIES;
    static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new ThreadFactory() { // from class: androidx.core.provider.RequestExecutor$DefaultThreadFactory
            private String mThreadName = "fonts-androidx";
            private int mPriority = 10;

            private static class ProcessPriorityThread extends Thread {
                private final int mPriority;

                ProcessPriorityThread(Runnable runnable, String str, int i) {
                    super(runnable, str);
                    this.mPriority = i;
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    Process.setThreadPriority(this.mPriority);
                    super.run();
                }
            }

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new ProcessPriorityThread(runnable, this.mThreadName, this.mPriority);
            }
        });
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        DEFAULT_EXECUTOR_SERVICE = threadPoolExecutor;
        LOCK = new Object();
        PENDING_REPLIES = new SimpleArrayMap<>();
    }

    static TypefaceResult getFontSync(String str, Context context, FontRequest fontRequest, int i) {
        int i2;
        LruCache<String, Typeface> lruCache = sTypefaceCache;
        Typeface typeface = lruCache.get(str);
        if (typeface != null) {
            return new TypefaceResult(typeface);
        }
        try {
            FontsContractCompat.FontFamilyResult fontFamilyResult = FontProvider.getFontFamilyResult(context, fontRequest);
            int i3 = 1;
            if (fontFamilyResult.getStatusCode() != 0) {
                if (fontFamilyResult.getStatusCode() == 1) {
                    i2 = -2;
                }
                i2 = -3;
            } else {
                FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
                if (fonts != null && fonts.length != 0) {
                    for (FontsContractCompat.FontInfo fontInfo : fonts) {
                        int resultCode = fontInfo.getResultCode();
                        if (resultCode != 0) {
                            if (resultCode >= 0) {
                                i2 = resultCode;
                            }
                            i2 = -3;
                        }
                    }
                    i3 = 0;
                }
                i2 = i3;
            }
            if (i2 != 0) {
                return new TypefaceResult(i2);
            }
            Typeface createFromFontInfo = TypefaceCompat.createFromFontInfo(context, fontFamilyResult.getFonts(), i);
            if (createFromFontInfo == null) {
                return new TypefaceResult(-3);
            }
            lruCache.put(str, createFromFontInfo);
            return new TypefaceResult(createFromFontInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            return new TypefaceResult(-1);
        }
    }

    static Typeface requestFontAsync(final Context context, final FontRequest fontRequest, final int i, final CallbackWithHandler callbackWithHandler) {
        final String str = fontRequest.getId() + "-" + i;
        Typeface typeface = sTypefaceCache.get(str);
        if (typeface != null) {
            callbackWithHandler.onTypefaceResult(new TypefaceResult(typeface));
            return typeface;
        }
        Consumer<TypefaceResult> consumer = new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.2
            @Override // androidx.core.util.Consumer
            public final void accept(TypefaceResult typefaceResult) {
                TypefaceResult typefaceResult2 = typefaceResult;
                if (typefaceResult2 == null) {
                    typefaceResult2 = new TypefaceResult(-3);
                }
                CallbackWithHandler.this.onTypefaceResult(typefaceResult2);
            }
        };
        synchronized (LOCK) {
            SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> simpleArrayMap = PENDING_REPLIES;
            ArrayList<Consumer<TypefaceResult>> arrayList = simpleArrayMap.get(str);
            if (arrayList != null) {
                arrayList.add(consumer);
                return null;
            }
            ArrayList<Consumer<TypefaceResult>> arrayList2 = new ArrayList<>();
            arrayList2.add(consumer);
            simpleArrayMap.put(str, arrayList2);
            final Callable<TypefaceResult> callable = new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.3
                @Override // java.util.concurrent.Callable
                public final TypefaceResult call() throws Exception {
                    try {
                        return FontRequestWorker.getFontSync(str, context, fontRequest, i);
                    } catch (Throwable unused) {
                        return new TypefaceResult(-3);
                    }
                }
            };
            ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;
            final Consumer<TypefaceResult> consumer2 = new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.4
                @Override // androidx.core.util.Consumer
                public final void accept(TypefaceResult typefaceResult) {
                    TypefaceResult typefaceResult2 = typefaceResult;
                    synchronized (FontRequestWorker.LOCK) {
                        SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> simpleArrayMap2 = FontRequestWorker.PENDING_REPLIES;
                        ArrayList<Consumer<TypefaceResult>> arrayList3 = simpleArrayMap2.get(str);
                        if (arrayList3 == null) {
                            return;
                        }
                        simpleArrayMap2.remove(str);
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            arrayList3.get(i2).accept(typefaceResult2);
                        }
                    }
                }
            };
            final Handler handler = Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : new Handler();
            executorService.execute(new Runnable(handler, callable, consumer2) { // from class: androidx.core.provider.RequestExecutor$ReplyRunnable
                private Callable<T> mCallable;
                private Consumer<T> mConsumer;
                private Handler mHandler;

                {
                    this.mCallable = callable;
                    this.mConsumer = consumer2;
                    this.mHandler = handler;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    final T t;
                    try {
                        t = this.mCallable.call();
                    } catch (Exception unused) {
                        t = null;
                    }
                    final Consumer<T> consumer3 = this.mConsumer;
                    this.mHandler.post(new Runnable() { // from class: androidx.core.provider.RequestExecutor$ReplyRunnable.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public final void run() {
                            Consumer.this.accept(t);
                        }
                    });
                }
            });
            return null;
        }
    }

    static Typeface requestFontSync(final Context context, final FontRequest fontRequest, CallbackWithHandler callbackWithHandler, final int i, int i2) {
        final String str = fontRequest.getId() + "-" + i;
        Typeface typeface = sTypefaceCache.get(str);
        if (typeface != null) {
            callbackWithHandler.onTypefaceResult(new TypefaceResult(typeface));
            return typeface;
        }
        if (i2 == -1) {
            TypefaceResult fontSync = getFontSync(str, context, fontRequest, i);
            callbackWithHandler.onTypefaceResult(fontSync);
            return fontSync.mTypeface;
        }
        try {
            try {
                try {
                    try {
                        TypefaceResult typefaceResult = (TypefaceResult) DEFAULT_EXECUTOR_SERVICE.submit(new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.1
                            @Override // java.util.concurrent.Callable
                            public final TypefaceResult call() throws Exception {
                                return FontRequestWorker.getFontSync(str, context, fontRequest, i);
                            }
                        }).get(i2, TimeUnit.MILLISECONDS);
                        callbackWithHandler.onTypefaceResult(typefaceResult);
                        return typefaceResult.mTypeface;
                    } catch (TimeoutException unused) {
                        throw new InterruptedException("timeout");
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            } catch (InterruptedException e2) {
                throw e2;
            }
        } catch (InterruptedException unused2) {
            callbackWithHandler.onTypefaceResult(new TypefaceResult(-3));
            return null;
        }
    }

    static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(int i) {
            this.mTypeface = null;
            this.mResult = i;
        }

        @SuppressLint({"WrongConstant"})
        TypefaceResult(Typeface typeface) {
            this.mTypeface = typeface;
            this.mResult = 0;
        }
    }
}
