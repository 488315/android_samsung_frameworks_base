package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Process;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontRequestWorker {
    public static final ExecutorService DEFAULT_EXECUTOR_SERVICE;
    public static final Object LOCK;
    public static final SimpleArrayMap PENDING_REPLIES;
    public static final LruCache sTypefaceCache = new LruCache(16);

    static {
        final String str = "fonts-androidx";
        final int i = 10;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new ThreadFactory(str, i) { // from class: androidx.core.provider.RequestExecutor$DefaultThreadFactory
            public final int mPriority;
            public final String mThreadName;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public final class ProcessPriorityThread extends Thread {
                public final int mPriority;

                public ProcessPriorityThread(Runnable runnable, String str, int i) {
                    super(runnable, str);
                    this.mPriority = i;
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    Process.setThreadPriority(this.mPriority);
                    super.run();
                }
            }

            {
                this.mThreadName = str;
                this.mPriority = i;
            }

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new ProcessPriorityThread(runnable, this.mThreadName, this.mPriority);
            }
        });
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        DEFAULT_EXECUTOR_SERVICE = threadPoolExecutor;
        LOCK = new Object();
        PENDING_REPLIES = new SimpleArrayMap();
    }

    private FontRequestWorker() {
    }

    public static TypefaceResult getFontSync(String str, Context context, FontRequest fontRequest, int i) {
        int i2;
        LruCache lruCache = sTypefaceCache;
        Typeface typeface = (Typeface) lruCache.get(str);
        if (typeface != null) {
            return new TypefaceResult(typeface);
        }
        try {
            FontsContractCompat$FontFamilyResult fontFamilyResult = FontProvider.getFontFamilyResult(context, fontRequest);
            int i3 = 1;
            FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr = fontFamilyResult.mFonts;
            int i4 = fontFamilyResult.mStatusCode;
            if (i4 != 0) {
                if (i4 == 1) {
                    i2 = -2;
                }
                i2 = -3;
            } else {
                if (fontsContractCompat$FontInfoArr != null && fontsContractCompat$FontInfoArr.length != 0) {
                    i3 = 0;
                    for (FontsContractCompat$FontInfo fontsContractCompat$FontInfo : fontsContractCompat$FontInfoArr) {
                        int i5 = fontsContractCompat$FontInfo.mResultCode;
                        if (i5 != 0) {
                            if (i5 >= 0) {
                                i2 = i5;
                            }
                            i2 = -3;
                        }
                    }
                }
                i2 = i3;
            }
            if (i2 != 0) {
                return new TypefaceResult(i2);
            }
            Typeface createFromFontInfo = TypefaceCompat.createFromFontInfo(context, fontsContractCompat$FontInfoArr, i);
            if (createFromFontInfo == null) {
                return new TypefaceResult(-3);
            }
            lruCache.put(str, createFromFontInfo);
            return new TypefaceResult(createFromFontInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            return new TypefaceResult(-1);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TypefaceResult {
        public final int mResult;
        public final Typeface mTypeface;

        public TypefaceResult(int i) {
            this.mTypeface = null;
            this.mResult = i;
        }

        public TypefaceResult(Typeface typeface) {
            this.mTypeface = typeface;
            this.mResult = 0;
        }
    }
}
