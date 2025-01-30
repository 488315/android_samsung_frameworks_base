package android.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ProviderInfo;
import android.content.p002pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.p009os.CancellationSignal;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.ParcelFileDescriptor;
import android.provider.FontsContract;
import android.util.LruCache;
import com.android.internal.util.Preconditions;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

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
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return FontsContract.lambda$static$13((byte[]) obj, (byte[]) obj2);
        }
    };

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

        public FontInfo(Uri uri, int ttcIndex, FontVariationAxis[] axes, int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mAxes = axes;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
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

        public FontFamilyResult(int statusCode, FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:53:0x00f5
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public static android.graphics.Typeface getFontSync(final android.provider.FontRequest r17) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.provider.FontsContract.getFontSync(android.provider.FontRequest):android.graphics.Typeface");
    }

    static /* synthetic */ void lambda$getFontSync$0(FontRequest request, String id, AtomicReference holder, Lock lock, AtomicBoolean timeout, AtomicBoolean waiting, Condition cond) {
        try {
            FontFamilyResult result = fetchFonts(sContext, null, request);
            if (result.getStatusCode() == 0) {
                Typeface typeface = buildTypeface(sContext, null, result.getFonts());
                if (typeface != null) {
                    sTypefaceCache.put(id, typeface);
                }
                holder.set(typeface);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        lock.lock();
        try {
            if (!timeout.get()) {
                waiting.set(false);
                cond.signal();
            }
        } finally {
            lock.unlock();
        }
    }

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

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int reason) {
        }
    }

    public static void requestFonts(final Context context, final FontRequest request, Handler handler, final CancellationSignal cancellationSignal, final FontRequestCallback callback) {
        final Handler callerThreadHandler = new Handler();
        final Typeface cachedTypeface = sTypefaceCache.get(request.getIdentifier());
        if (cachedTypeface != null) {
            callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.FontRequestCallback.this.onTypefaceRetrieved(cachedTypeface);
                }
            });
        } else {
            handler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.lambda$requestFonts$12(Context.this, cancellationSignal, request, callerThreadHandler, callback);
                }
            });
        }
    }

    static /* synthetic */ void lambda$requestFonts$12(Context context, CancellationSignal cancellationSignal, FontRequest request, Handler callerThreadHandler, final FontRequestCallback callback) {
        try {
            FontFamilyResult result = fetchFonts(context, cancellationSignal, request);
            final Typeface anotherCachedTypeface = sTypefaceCache.get(request.getIdentifier());
            if (anotherCachedTypeface != null) {
                callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRetrieved(anotherCachedTypeface);
                    }
                });
                return;
            }
            if (result.getStatusCode() != 0) {
                switch (result.getStatusCode()) {
                    case 1:
                        callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-2);
                            }
                        });
                        break;
                    case 2:
                        callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        break;
                    default:
                        callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        break;
                }
                return;
            }
            FontInfo[] fonts = result.getFonts();
            if (fonts == null || fonts.length == 0) {
                callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(1);
                    }
                });
                return;
            }
            for (FontInfo font : fonts) {
                if (font.getResultCode() != 0) {
                    final int resultCode = font.getResultCode();
                    if (resultCode < 0) {
                        callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                            }
                        });
                        return;
                    } else {
                        callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(resultCode);
                            }
                        });
                        return;
                    }
                }
            }
            final Typeface typeface = buildTypeface(context, cancellationSignal, fonts);
            if (typeface == null) {
                callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-3);
                    }
                });
            } else {
                sTypefaceCache.put(request.getIdentifier(), typeface);
                callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontsContract.FontRequestCallback.this.onTypefaceRetrieved(typeface);
                    }
                });
            }
        } catch (PackageManager.NameNotFoundException e) {
            callerThreadHandler.post(new Runnable() { // from class: android.provider.FontsContract$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContract.FontRequestCallback.this.onTypefaceRequestFailed(-1);
                }
            });
        }
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest request) throws PackageManager.NameNotFoundException {
        if (context.isRestricted()) {
            return new FontFamilyResult(3, null);
        }
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request);
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        try {
            FontInfo[] fonts = getFontFromProvider(context, request, providerInfo.authority, cancellationSignal);
            return new FontFamilyResult(0, fonts);
        } catch (IllegalArgumentException e) {
            return new FontFamilyResult(2, null);
        }
    }

    public static Typeface buildTypeface(Context context, CancellationSignal cancellationSignal, FontInfo[] fonts) {
        if (context.isRestricted()) {
            return null;
        }
        Map<Uri, ByteBuffer> uriBuffer = prepareFontData(context, fonts, cancellationSignal);
        if (uriBuffer.isEmpty()) {
            return null;
        }
        FontFamily.Builder familyBuilder = null;
        for (FontInfo fontInfo : fonts) {
            ByteBuffer buffer = uriBuffer.get(fontInfo.getUri());
            if (buffer != null) {
                try {
                    Font font = new Font.Builder(buffer).setWeight(fontInfo.getWeight()).setSlant(fontInfo.isItalic() ? 1 : 0).setTtcIndex(fontInfo.getTtcIndex()).setFontVariationSettings(fontInfo.getAxes()).build();
                    if (familyBuilder == null) {
                        familyBuilder = new FontFamily.Builder(font);
                    } else {
                        familyBuilder.addFont(font);
                    }
                } catch (IOException e) {
                } catch (IllegalArgumentException e2) {
                    return null;
                }
            }
        }
        if (familyBuilder == null) {
            return null;
        }
        FontFamily family = familyBuilder.build();
        FontStyle normal = new FontStyle(400, 0);
        Font bestFont = family.getFont(0);
        int bestScore = normal.getMatchScore(bestFont.getStyle());
        for (int i = 1; i < family.getSize(); i++) {
            Font candidate = family.getFont(i);
            int score = normal.getMatchScore(candidate.getStyle());
            if (score < bestScore) {
                bestFont = candidate;
                bestScore = score;
            }
        }
        return new Typeface.CustomFallbackBuilder(family).setStyle(bestFont.getStyle()).build();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        ContentResolver resolver = context.getContentResolver();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    ByteBuffer buffer = null;
                    try {
                        ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r", cancellationSignal);
                        if (pfd != null) {
                            try {
                                FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                                try {
                                    FileChannel fileChannel = fis.getChannel();
                                    long size = fileChannel.size();
                                    buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                                    fis.close();
                                } finally {
                                }
                            } catch (IOException e) {
                            } catch (Throwable th) {
                                if (pfd == null) {
                                    throw th;
                                }
                                try {
                                    pfd.close();
                                    throw th;
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                    throw th;
                                }
                            }
                        }
                        if (pfd != null) {
                            pfd.close();
                        }
                    } catch (IOException e2) {
                    }
                    out.put(uri, buffer);
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest request) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!info.packageName.equals(request.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
        if (info.applicationInfo.isSystemApp()) {
            return info;
        }
        PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, 64);
        List<byte[]> signatures = convertToByteArrayList(packageInfo.signatures);
        Collections.sort(signatures, sByteArrayComparator);
        List<List<byte[]>> requestCertificatesList = request.getCertificates();
        for (int i = 0; i < requestCertificatesList.size(); i++) {
            List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
            Collections.sort(requestSignatures, sByteArrayComparator);
            if (equalsByteArrayList(signatures, requestSignatures)) {
                return info;
            }
        }
        return null;
    }

    static /* synthetic */ int lambda$static$13(byte[] l, byte[] r) {
        if (l.length != r.length) {
            return l.length - r.length;
        }
        for (int i = 0; i < l.length; i++) {
            if (l[i] != r[i]) {
                return l[i] - r[i];
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList<>();
        for (Signature signature : signatures) {
            shas.add(signature.toByteArray());
        }
        return shas;
    }

    public static FontInfo[] getFontFromProvider(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) {
        Uri fileUri;
        int weight;
        boolean italic;
        ArrayList<FontInfo> result = new ArrayList<>();
        Uri uri = new Uri.Builder().scheme("content").authority(authority).build();
        Uri fileBaseUri = new Uri.Builder().scheme("content").authority(authority).appendPath("file").build();
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{request.getQuery()}, null, cancellationSignal);
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    int resultCodeColumnIndex = cursor.getColumnIndex(Columns.RESULT_CODE);
                    result = new ArrayList<>();
                    int idColumnIndex = cursor.getColumnIndexOrThrow("_id");
                    int fileIdColumnIndex = cursor.getColumnIndex(Columns.FILE_ID);
                    int ttcIndexColumnIndex = cursor.getColumnIndex(Columns.TTC_INDEX);
                    int vsColumnIndex = cursor.getColumnIndex(Columns.VARIATION_SETTINGS);
                    int weightColumnIndex = cursor.getColumnIndex(Columns.WEIGHT);
                    int italicColumnIndex = cursor.getColumnIndex(Columns.ITALIC);
                    while (cursor.moveToNext()) {
                        int resultCode = resultCodeColumnIndex != -1 ? cursor.getInt(resultCodeColumnIndex) : 0;
                        int ttcIndex = ttcIndexColumnIndex != -1 ? cursor.getInt(ttcIndexColumnIndex) : 0;
                        String variationSettings = vsColumnIndex != -1 ? cursor.getString(vsColumnIndex) : null;
                        if (fileIdColumnIndex == -1) {
                            long id = cursor.getLong(idColumnIndex);
                            fileUri = ContentUris.withAppendedId(uri, id);
                        } else {
                            long id2 = cursor.getLong(fileIdColumnIndex);
                            fileUri = ContentUris.withAppendedId(fileBaseUri, id2);
                        }
                        if (weightColumnIndex != -1 && italicColumnIndex != -1) {
                            weight = cursor.getInt(weightColumnIndex);
                            boolean z = true;
                            if (cursor.getInt(italicColumnIndex) != 1) {
                                z = false;
                            }
                            italic = z;
                        } else {
                            weight = 400;
                            italic = false;
                        }
                        FontVariationAxis[] axes = FontVariationAxis.fromFontVariationSettings(variationSettings);
                        result.add(new FontInfo(fileUri, ttcIndex, axes, weight, italic, resultCode));
                    }
                }
            } finally {
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return (FontInfo[]) result.toArray(new FontInfo[0]);
    }
}
