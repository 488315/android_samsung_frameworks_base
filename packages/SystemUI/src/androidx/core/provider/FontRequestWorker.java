package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Process;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FontRequestWorker {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public class ProcessPriorityThread extends Thread {
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

    public static String createCacheId(int i, List list) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            sb.append(((FontRequest) list.get(i2)).mIdentifier);
            sb.append("-");
            sb.append(i);
            if (i2 < list.size() - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004d A[Catch: all -> 0x00a3, TRY_LEAVE, TryCatch #2 {all -> 0x00a3, all -> 0x0071, NameNotFoundException -> 0x0099, blocks: (B:3:0x0008, B:5:0x0010, B:10:0x0019, B:11:0x001d, B:16:0x004d, B:19:0x0056, B:21:0x005e, B:24:0x006d, B:26:0x0084, B:29:0x0090, B:34:0x0072, B:35:0x0075, B:36:0x0076, B:38:0x0029, B:40:0x0033, B:43:0x0037, B:45:0x003b, B:47:0x0046, B:56:0x0099, B:23:0x0067), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0056 A[Catch: all -> 0x00a3, TRY_ENTER, TryCatch #2 {all -> 0x00a3, all -> 0x0071, NameNotFoundException -> 0x0099, blocks: (B:3:0x0008, B:5:0x0010, B:10:0x0019, B:11:0x001d, B:16:0x004d, B:19:0x0056, B:21:0x005e, B:24:0x006d, B:26:0x0084, B:29:0x0090, B:34:0x0072, B:35:0x0075, B:36:0x0076, B:38:0x0029, B:40:0x0033, B:43:0x0037, B:45:0x003b, B:47:0x0046, B:56:0x0099, B:23:0x0067), top: B:2:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.core.provider.FontRequestWorker.TypefaceResult getFontSync(java.lang.String r8, android.content.Context r9, java.util.List r10, int r11) {
        /*
            r0 = 1
            java.lang.String r1 = "getFontSync"
            android.os.Trace.beginSection(r1)
            androidx.collection.LruCache r1 = androidx.core.provider.FontRequestWorker.sTypefaceCache
            java.lang.Object r2 = r1.get(r8)     // Catch: java.lang.Throwable -> La3
            android.graphics.Typeface r2 = (android.graphics.Typeface) r2     // Catch: java.lang.Throwable -> La3
            if (r2 == 0) goto L19
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = new androidx.core.provider.FontRequestWorker$TypefaceResult     // Catch: java.lang.Throwable -> La3
            r8.<init>(r2)     // Catch: java.lang.Throwable -> La3
            android.os.Trace.endSection()
            return r8
        L19:
            androidx.core.provider.FontsContractCompat$FontFamilyResult r10 = androidx.core.provider.FontProvider.getFontFamilyResult(r9, r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L99 java.lang.Throwable -> La3
            int r2 = r10.mStatusCode     // Catch: java.lang.Throwable -> La3
            r3 = 0
            r4 = -3
            if (r2 == 0) goto L29
            if (r2 == r0) goto L27
        L25:
            r2 = r4
            goto L4b
        L27:
            r2 = -2
            goto L4b
        L29:
            java.util.List r2 = r10.mFonts     // Catch: java.lang.Throwable -> La3
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> La3
            androidx.core.provider.FontsContractCompat$FontInfo[] r2 = (androidx.core.provider.FontsContractCompat$FontInfo[]) r2     // Catch: java.lang.Throwable -> La3
            if (r2 == 0) goto L4a
            int r5 = r2.length     // Catch: java.lang.Throwable -> La3
            if (r5 != 0) goto L37
            goto L4a
        L37:
            int r5 = r2.length     // Catch: java.lang.Throwable -> La3
            r6 = r3
        L39:
            if (r6 >= r5) goto L48
            r7 = r2[r6]     // Catch: java.lang.Throwable -> La3
            int r7 = r7.mResultCode     // Catch: java.lang.Throwable -> La3
            if (r7 == 0) goto L46
            if (r7 >= 0) goto L44
            goto L25
        L44:
            r2 = r7
            goto L4b
        L46:
            int r6 = r6 + r0
            goto L39
        L48:
            r2 = r3
            goto L4b
        L4a:
            r2 = r0
        L4b:
            if (r2 == 0) goto L56
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = new androidx.core.provider.FontRequestWorker$TypefaceResult     // Catch: java.lang.Throwable -> La3
            r8.<init>(r2)     // Catch: java.lang.Throwable -> La3
            android.os.Trace.endSection()
            return r8
        L56:
            java.util.List r2 = r10.mFonts     // Catch: java.lang.Throwable -> La3
            int r2 = r2.size()     // Catch: java.lang.Throwable -> La3
            if (r2 <= r0) goto L76
            java.util.List r10 = r10.mFonts     // Catch: java.lang.Throwable -> La3
            androidx.core.graphics.TypefaceCompatApi29Impl r0 = androidx.core.graphics.TypefaceCompat.sTypefaceCompatImpl     // Catch: java.lang.Throwable -> La3
            java.lang.String r0 = "TypefaceCompat.createFromFontInfoWithFallback"
            android.os.Trace.beginSection(r0)     // Catch: java.lang.Throwable -> La3
            androidx.core.graphics.TypefaceCompatApi29Impl r0 = androidx.core.graphics.TypefaceCompat.sTypefaceCompatImpl     // Catch: java.lang.Throwable -> L71
            android.graphics.Typeface r9 = r0.createFromFontInfoWithFallback(r9, r11, r10)     // Catch: java.lang.Throwable -> L71
            android.os.Trace.endSection()     // Catch: java.lang.Throwable -> La3
            goto L82
        L71:
            r8 = move-exception
            android.os.Trace.endSection()     // Catch: java.lang.Throwable -> La3
            throw r8     // Catch: java.lang.Throwable -> La3
        L76:
            java.util.List r10 = r10.mFonts     // Catch: java.lang.Throwable -> La3
            java.lang.Object r10 = r10.get(r3)     // Catch: java.lang.Throwable -> La3
            androidx.core.provider.FontsContractCompat$FontInfo[] r10 = (androidx.core.provider.FontsContractCompat$FontInfo[]) r10     // Catch: java.lang.Throwable -> La3
            android.graphics.Typeface r9 = androidx.core.graphics.TypefaceCompat.createFromFontInfo(r9, r10, r11)     // Catch: java.lang.Throwable -> La3
        L82:
            if (r9 == 0) goto L90
            r1.put(r8, r9)     // Catch: java.lang.Throwable -> La3
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = new androidx.core.provider.FontRequestWorker$TypefaceResult     // Catch: java.lang.Throwable -> La3
            r8.<init>(r9)     // Catch: java.lang.Throwable -> La3
            android.os.Trace.endSection()
            return r8
        L90:
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = new androidx.core.provider.FontRequestWorker$TypefaceResult     // Catch: java.lang.Throwable -> La3
            r8.<init>(r4)     // Catch: java.lang.Throwable -> La3
            android.os.Trace.endSection()
            return r8
        L99:
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = new androidx.core.provider.FontRequestWorker$TypefaceResult     // Catch: java.lang.Throwable -> La3
            r9 = -1
            r8.<init>(r9)     // Catch: java.lang.Throwable -> La3
            android.os.Trace.endSection()
            return r8
        La3:
            r8 = move-exception
            android.os.Trace.endSection()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontRequestWorker.getFontSync(java.lang.String, android.content.Context, java.util.List, int):androidx.core.provider.FontRequestWorker$TypefaceResult");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
