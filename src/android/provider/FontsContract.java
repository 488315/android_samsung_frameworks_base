package android.provider;

import android.app.job.JobInfo;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelFileDescriptor;
import android.provider.FontsContract;
import android.util.Log;
import android.util.LruCache;
import com.android.internal.util.Preconditions;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Deprecated
/* loaded from: classes3.dex */
public class FontsContract {
    private static final long SYNC_FONT_FETCH_TIMEOUT_MS = 500;
    private static final String TAG = "FontsContract";
    private static final int THREAD_RENEWAL_THRESHOLD_MS = 10000;
    private static volatile Context sContext;
    private static Handler sHandler;
    private static Set<String> sInQueueSet;
    private static HandlerThread sThread;
    private static final Object sLock = new Object();
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    private static final Runnable sReplaceDispatcherThreadRunnable = new Runnable() { // from class: android.provider.FontsContract.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (FontsContract.sLock) {
                if (FontsContract.sThread != null) {
                    FontsContract.sThread.quitSafely();
                    FontsContract.sThread = null;
                    FontsContract.sHandler = null;
                }
            }
        }
    };
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda12
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return FontsContract.lambda$static$13((byte[]) obj, (byte[]) obj2);
        }
    };

    @Deprecated
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @Retention(RetentionPolicy.SOURCE)
        @interface FontRequestFailReason {
        }

        public void onTypefaceRequestFailed(int i) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }
    }

    @Deprecated
    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";

        private Columns() {
        }
    }

    private FontsContract() {
    }

    public static void setApplicationContextForResources(Context context) {
        sContext = context.getApplicationContext();
    }

    @Deprecated
    public static class FontInfo {
        private final FontVariationAxis[] mAxes;
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int i, FontVariationAxis[] fontVariationAxisArr, int i2, boolean z, int i3) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = i;
            this.mAxes = fontVariationAxisArr;
            this.mWeight = i2;
            this.mItalic = z;
            this.mResultCode = i3;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public FontVariationAxis[] getAxes() {
            return this.mAxes;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    @Deprecated
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_REJECTED = 3;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @Retention(RetentionPolicy.SOURCE)
        @interface FontResultStatus {
        }

        public FontFamilyResult(int i, FontInfo[] fontInfoArr) {
            this.mStatusCode = i;
            this.mFonts = fontInfoArr;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    public static Typeface getFontSync(final FontRequest fontRequest) {
        final String identifier = fontRequest.getIdentifier();
        LruCache<String, Typeface> lruCache = sTypefaceCache;
        Typeface typeface = lruCache.get(identifier);
        if (typeface != null) {
            return typeface;
        }
        Log.w(TAG, "Platform version of downloadable fonts is deprecated. Please use androidx version instead.");
        synchronized (sLock) {
            Typeface typeface2 = lruCache.get(identifier);
            if (typeface2 != null) {
                return typeface2;
            }
            if (sHandler == null) {
                HandlerThread handlerThread = new HandlerThread("fonts", -2);
                sThread = handlerThread;
                handlerThread.start();
                sHandler = new Handler(sThread.getLooper());
            }
            final ReentrantLock reentrantLock = new ReentrantLock();
            final Condition newCondition = reentrantLock.newCondition();
            final AtomicReference atomicReference = new AtomicReference();
            final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            final AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
            sHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.lambda$getFontSync$0(FontRequest.this, identifier, atomicReference, reentrantLock, atomicBoolean2, atomicBoolean, newCondition);
                }
            });
            Handler handler = sHandler;
            Runnable runnable = sReplaceDispatcherThreadRunnable;
            handler.removeCallbacks(runnable);
            sHandler.postDelayed(runnable, JobInfo.MIN_BACKOFF_MILLIS);
            long nanos = TimeUnit.MILLISECONDS.toNanos(SYNC_FONT_FETCH_TIMEOUT_MS);
            reentrantLock.lock();
            try {
                if (!atomicBoolean.get()) {
                    return (Typeface) atomicReference.get();
                }
                do {
                    try {
                        nanos = newCondition.awaitNanos(nanos);
                    } catch (InterruptedException unused) {
                    }
                    if (!atomicBoolean.get()) {
                        return (Typeface) atomicReference.get();
                    }
                } while (nanos > 0);
                atomicBoolean2.set(true);
                Log.w(TAG, "Remote font fetch timed out: " + fontRequest.getProviderAuthority() + "/" + fontRequest.getQuery());
                return null;
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    static /* synthetic */ void lambda$getFontSync$0(FontRequest fontRequest, String str, AtomicReference atomicReference, Lock lock, AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2, Condition condition) {
        try {
            FontFamilyResult fetchFonts = fetchFonts(sContext, null, fontRequest);
            if (fetchFonts.getStatusCode() == 0) {
                Typeface buildTypeface = buildTypeface(sContext, null, fetchFonts.getFonts());
                if (buildTypeface != null) {
                    sTypefaceCache.put(str, buildTypeface);
                }
                atomicReference.set(buildTypeface);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        lock.lock();
        try {
            if (!atomicBoolean.get()) {
                atomicBoolean2.set(false);
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void requestFonts(final Context context, final FontRequest fontRequest, Handler handler, final CancellationSignal cancellationSignal, final FontRequestCallback fontRequestCallback) {
        final Handler handler2 = new Handler();
        final Typeface typeface = sTypefaceCache.get(fontRequest.getIdentifier());
        if (typeface != null) {
            handler2.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.FontRequestCallback.this.onTypefaceRetrieved(typeface);
                }
            });
        } else {
            handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.lambda$requestFonts$12(Context.this, cancellationSignal, fontRequest, handler2, fontRequestCallback);
                }
            });
        }
    }

    static /* synthetic */ void lambda$requestFonts$12(Context context, CancellationSignal cancellationSignal, FontRequest fontRequest, Handler handler, final FontRequestCallback fontRequestCallback) {
        try {
            FontFamilyResult fetchFonts = fetchFonts(context, cancellationSignal, fontRequest);
            final Typeface typeface = sTypefaceCache.get(fontRequest.getIdentifier());
            if (typeface != null) {
                handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRetrieved(typeface);
                    }
                });
                return;
            }
            if (fetchFonts.getStatusCode() != 0) {
                int statusCode = fetchFonts.getStatusCode();
                if (statusCode == 1) {
                    handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-2);
                        }
                    });
                    return;
                } else if (statusCode == 2) {
                    handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                        }
                    });
                    return;
                } else {
                    handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                        }
                    });
                    return;
                }
            }
            FontInfo[] fonts = fetchFonts.getFonts();
            if (fonts == null || fonts.length == 0) {
                handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(1);
                    }
                });
                return;
            }
            for (FontInfo fontInfo : fonts) {
                if (fontInfo.getResultCode() != 0) {
                    final int resultCode = fontInfo.getResultCode();
                    if (resultCode < 0) {
                        handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        return;
                    } else {
                        handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(resultCode);
                            }
                        });
                        return;
                    }
                }
            }
            final Typeface buildTypeface = buildTypeface(context, cancellationSignal, fonts);
            if (buildTypeface == null) {
                handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                    }
                });
            } else {
                sTypefaceCache.put(fontRequest.getIdentifier(), buildTypeface);
                handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRetrieved(buildTypeface);
                    }
                });
            }
        } catch (PackageManager.NameNotFoundException unused) {
            handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-1);
                }
            });
        }
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        if (context.isRestricted()) {
            return new FontFamilyResult(3, null);
        }
        ProviderInfo provider = getProvider(context.getPackageManager(), fontRequest);
        if (provider == null) {
            return new FontFamilyResult(1, null);
        }
        try {
            return new FontFamilyResult(0, getFontFromProvider(context, fontRequest, provider.authority, cancellationSignal));
        } catch (IllegalArgumentException unused) {
            return new FontFamilyResult(2, null);
        }
    }

    public static Typeface buildTypeface(Context context, CancellationSignal cancellationSignal, FontInfo[] fontInfoArr) {
        int i;
        if (context.isRestricted()) {
            return null;
        }
        Map<Uri, ByteBuffer> prepareFontData = prepareFontData(context, fontInfoArr, cancellationSignal);
        if (prepareFontData.isEmpty()) {
            return null;
        }
        int length = fontInfoArr.length;
        int i2 = 0;
        FontFamily.Builder builder = null;
        while (true) {
            if (i2 >= length) {
                break;
            }
            FontInfo fontInfo = fontInfoArr[i2];
            ByteBuffer byteBuffer = prepareFontData.get(fontInfo.getUri());
            if (byteBuffer != null) {
                try {
                    Font build = new Font.Builder(byteBuffer).setWeight(fontInfo.getWeight()).setSlant(fontInfo.isItalic() ? 1 : 0).setTtcIndex(fontInfo.getTtcIndex()).setFontVariationSettings(fontInfo.getAxes()).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(build);
                    } else {
                        builder.addFont(build);
                    }
                } catch (IOException unused) {
                } catch (IllegalArgumentException unused2) {
                    return null;
                }
            }
            i2++;
        }
        if (builder == null) {
            return null;
        }
        FontFamily build2 = builder.build();
        FontStyle fontStyle = new FontStyle(400, 0);
        Font font = build2.getFont(0);
        int matchScore = fontStyle.getMatchScore(font.getStyle());
        for (i = 1; i < build2.getSize(); i++) {
            Font font2 = build2.getFont(i);
            int matchScore2 = fontStyle.getMatchScore(font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
        }
        return new Typeface.CustomFallbackBuilder(build2).setStyle(font.getStyle()).build();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fontInfoArr, CancellationSignal cancellationSignal) {
        HashMap hashMap = new HashMap();
        ContentResolver contentResolver = context.getContentResolver();
        for (FontInfo fontInfo : fontInfoArr) {
            if (fontInfo.getResultCode() == 0) {
                Uri uri = fontInfo.getUri();
                if (!hashMap.containsKey(uri)) {
                    MappedByteBuffer mappedByteBuffer = null;
                    try {
                        ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r", cancellationSignal);
                        if (openFileDescriptor != null) {
                            try {
                                FileInputStream fileInputStream = new FileInputStream(openFileDescriptor.getFileDescriptor());
                                try {
                                    FileChannel channel = fileInputStream.getChannel();
                                    mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                                    fileInputStream.close();
                                } finally {
                                }
                            } catch (IOException unused) {
                            } catch (Throwable th) {
                                try {
                                    if (openFileDescriptor != null) {
                                        try {
                                            openFileDescriptor.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    }
                                    throw th;
                                } catch (IOException unused2) {
                                    mappedByteBuffer = null;
                                }
                            }
                        }
                        if (openFileDescriptor != null) {
                            openFileDescriptor.close();
                        }
                    } catch (IOException unused3) {
                    }
                    hashMap.put(uri, mappedByteBuffer);
                }
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        String providerAuthority = fontRequest.getProviderAuthority();
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!resolveContentProvider.packageName.equals(fontRequest.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + fontRequest.getProviderPackage());
        }
        if (!resolveContentProvider.applicationInfo.isSystemApp()) {
            List<byte[]> convertToByteArrayList = convertToByteArrayList(packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures);
            Collections.sort(convertToByteArrayList, sByteArrayComparator);
            List<List<byte[]>> certificates = fontRequest.getCertificates();
            for (int i = 0; i < certificates.size(); i++) {
                ArrayList arrayList = new ArrayList(certificates.get(i));
                Collections.sort(arrayList, sByteArrayComparator);
                if (!equalsByteArrayList(convertToByteArrayList, arrayList)) {
                }
            }
            return null;
        }
        return resolveContentProvider;
    }

    static /* synthetic */ int lambda$static$13(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return bArr.length - bArr2.length;
        }
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            byte b2 = bArr2[i];
            if (b != b2) {
                return b - b2;
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatureArr) {
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        return arrayList;
    }

    public static FontInfo[] getFontFromProvider(Context context, FontRequest fontRequest, String str, CancellationSignal cancellationSignal) {
        ArrayList arrayList;
        Uri withAppendedId;
        int i;
        boolean z;
        ArrayList arrayList2 = new ArrayList();
        Uri build = new Uri.Builder().scheme("content").authority(str).build();
        Uri build2 = new Uri.Builder().scheme("content").authority(str).appendPath("file").build();
        Cursor query = context.getContentResolver().query(build, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{fontRequest.getQuery()}, null, cancellationSignal);
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    int columnIndex = query.getColumnIndex(Columns.RESULT_CODE);
                    ArrayList arrayList3 = new ArrayList();
                    int columnIndexOrThrow = query.getColumnIndexOrThrow("_id");
                    int columnIndex2 = query.getColumnIndex(Columns.FILE_ID);
                    int columnIndex3 = query.getColumnIndex(Columns.TTC_INDEX);
                    int columnIndex4 = query.getColumnIndex(Columns.VARIATION_SETTINGS);
                    int columnIndex5 = query.getColumnIndex(Columns.WEIGHT);
                    int columnIndex6 = query.getColumnIndex(Columns.ITALIC);
                    while (query.moveToNext()) {
                        int i2 = columnIndex != -1 ? query.getInt(columnIndex) : 0;
                        int i3 = columnIndex3 != -1 ? query.getInt(columnIndex3) : 0;
                        String string = columnIndex4 != -1 ? query.getString(columnIndex4) : null;
                        if (columnIndex2 == -1) {
                            arrayList = arrayList3;
                            withAppendedId = ContentUris.withAppendedId(build, query.getLong(columnIndexOrThrow));
                        } else {
                            arrayList = arrayList3;
                            withAppendedId = ContentUris.withAppendedId(build2, query.getLong(columnIndex2));
                        }
                        if (columnIndex5 == -1 || columnIndex6 == -1) {
                            i = 400;
                            z = false;
                        } else {
                            i = query.getInt(columnIndex5);
                            boolean z2 = true;
                            if (query.getInt(columnIndex6) != 1) {
                                z2 = false;
                            }
                            z = z2;
                        }
                        FontVariationAxis[] fromFontVariationSettings = FontVariationAxis.fromFontVariationSettings(string);
                        Uri uri = withAppendedId;
                        ArrayList arrayList4 = arrayList;
                        arrayList4.add(new FontInfo(uri, i3, fromFontVariationSettings, i, z, i2));
                        arrayList3 = arrayList4;
                    }
                    arrayList2 = arrayList3;
                }
            } finally {
            }
        }
        if (query != null) {
            query.close();
        }
        return (FontInfo[]) arrayList2.toArray(new FontInfo[0]);
    }
}
