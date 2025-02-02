package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.airbnb.lottie.network.NetworkFetcher;
import com.airbnb.lottie.parser.LottieCompositionMoshiParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okio.BufferedSource;
import okio.Okio;

/* loaded from: classes.dex */
public final class LottieCompositionFactory {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final Map<String, LottieTask<LottieComposition>> taskCache = new HashMap();
    private static final byte[] MAGIC = {80, 75, 3, 4};

    private static LottieTask<LottieComposition> cache(final String str, Callable<LottieResult<LottieComposition>> callable) {
        final LottieComposition lottieComposition = str == null ? null : LottieCompositionCache.getInstance().get(str);
        if (lottieComposition != null) {
            return new LottieTask<>(new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.9
                @Override // java.util.concurrent.Callable
                public final LottieResult<LottieComposition> call() throws Exception {
                    return new LottieResult<>(LottieComposition.this);
                }
            }, false);
        }
        Map<String, LottieTask<LottieComposition>> map = taskCache;
        if (str != null) {
            HashMap hashMap = (HashMap) map;
            if (hashMap.containsKey(str)) {
                return (LottieTask) hashMap.get(str);
            }
        }
        LottieTask<LottieComposition> lottieTask = new LottieTask<>(callable, false);
        if (str != null) {
            lottieTask.addListener(new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieCompositionFactory.10
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(LottieComposition lottieComposition2) {
                    ((HashMap) LottieCompositionFactory.taskCache).remove(str);
                }
            });
            lottieTask.addFailureListener(new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieCompositionFactory.11
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Throwable th) {
                    ((HashMap) LottieCompositionFactory.taskCache).remove(str);
                }
            });
            ((HashMap) map).put(str, lottieTask);
        }
        return lottieTask;
    }

    public static LottieTask<LottieComposition> fromAsset(Context context, final String str, final String str2) {
        final Context applicationContext = context.getApplicationContext();
        return cache(str2, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.2
            @Override // java.util.concurrent.Callable
            public final LottieResult<LottieComposition> call() throws Exception {
                return LottieCompositionFactory.fromAssetSync(applicationContext, str, str2);
            }
        });
    }

    public static LottieResult<LottieComposition> fromAssetSync(Context context, String str, String str2) {
        try {
            if (!str.endsWith(".zip") && !str.endsWith(".lottie")) {
                return fromJsonInputStreamSync(context.getAssets().open(str), str2);
            }
            return fromZipStreamSync(new ZipInputStream(context.getAssets().open(str)), str2);
        } catch (IOException e) {
            return new LottieResult<>(e);
        }
    }

    public static LottieTask fromJsonInputStream(final InputStream inputStream) {
        return cache(null, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.4
            final /* synthetic */ String val$cacheKey = null;

            @Override // java.util.concurrent.Callable
            public final LottieResult<LottieComposition> call() throws Exception {
                return LottieCompositionFactory.fromJsonInputStreamSync(inputStream, this.val$cacheKey);
            }
        });
    }

    public static LottieResult<LottieComposition> fromJsonInputStreamSync(InputStream inputStream, String str) {
        try {
            return fromJsonReaderSyncInternal(JsonReader.m7of(Okio.buffer(Okio.source(inputStream))), str, true);
        } finally {
            Utils.closeQuietly(inputStream);
        }
    }

    private static LottieResult<LottieComposition> fromJsonReaderSyncInternal(JsonReader jsonReader, String str, boolean z) {
        try {
            try {
                LottieComposition parse = LottieCompositionMoshiParser.parse(jsonReader);
                if (str != null) {
                    LottieCompositionCache.getInstance().put(parse, str);
                }
                LottieResult<LottieComposition> lottieResult = new LottieResult<>(parse);
                if (z) {
                    Utils.closeQuietly(jsonReader);
                }
                return lottieResult;
            } catch (Exception e) {
                LottieResult<LottieComposition> lottieResult2 = new LottieResult<>(e);
                if (z) {
                    Utils.closeQuietly(jsonReader);
                }
                return lottieResult2;
            }
        } catch (Throwable th) {
            if (z) {
                Utils.closeQuietly(jsonReader);
            }
            throw th;
        }
    }

    public static LottieTask<LottieComposition> fromRawRes(Context context, int i) {
        return fromRawRes(context, i, rawResCacheKey(context, i));
    }

    public static LottieResult<LottieComposition> fromRawResSync(Context context, int i) {
        return fromRawResSync(context, i, rawResCacheKey(context, i));
    }

    public static LottieTask<LottieComposition> fromUrl(final Context context, final String str, final String str2) {
        return cache(str2, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.1
            @Override // java.util.concurrent.Callable
            public final LottieResult<LottieComposition> call() throws Exception {
                NetworkFetcher networkFetcher = C0179L.networkFetcher(context);
                String str3 = str;
                String str4 = str2;
                LottieResult<LottieComposition> fetchSync = networkFetcher.fetchSync(str3, str4);
                if (str4 != null && fetchSync.getValue() != null) {
                    LottieCompositionCache.getInstance().put(fetchSync.getValue(), str4);
                }
                return fetchSync;
            }
        });
    }

    public static LottieResult<LottieComposition> fromZipStreamSync(ZipInputStream zipInputStream, String str) {
        try {
            return fromZipStreamSyncInternal(zipInputStream, str);
        } finally {
            Utils.closeQuietly(zipInputStream);
        }
    }

    private static LottieResult<LottieComposition> fromZipStreamSyncInternal(ZipInputStream zipInputStream, String str) {
        LottieImageAsset lottieImageAsset;
        HashMap hashMap = new HashMap();
        try {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            LottieComposition lottieComposition = null;
            while (nextEntry != null) {
                String name = nextEntry.getName();
                if (name.contains("__MACOSX")) {
                    zipInputStream.closeEntry();
                } else if (nextEntry.getName().equalsIgnoreCase("manifest.json")) {
                    zipInputStream.closeEntry();
                } else if (nextEntry.getName().contains(".json")) {
                    lottieComposition = fromJsonReaderSyncInternal(JsonReader.m7of(Okio.buffer(Okio.source(zipInputStream))), null, false).getValue();
                } else {
                    if (!name.contains(".png") && !name.contains(".webp")) {
                        zipInputStream.closeEntry();
                    }
                    hashMap.put(name.split("/")[r1.length - 1], BitmapFactory.decodeStream(zipInputStream));
                }
                nextEntry = zipInputStream.getNextEntry();
            }
            if (lottieComposition == null) {
                return new LottieResult<>(new IllegalArgumentException("Unable to parse composition"));
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                String str2 = (String) entry.getKey();
                Iterator<LottieImageAsset> it = lottieComposition.getImages().values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        lottieImageAsset = null;
                        break;
                    }
                    lottieImageAsset = it.next();
                    if (lottieImageAsset.getFileName().equals(str2)) {
                        break;
                    }
                }
                if (lottieImageAsset != null) {
                    Bitmap bitmap = (Bitmap) entry.getValue();
                    int width = lottieImageAsset.getWidth();
                    int height = lottieImageAsset.getHeight();
                    int i = Utils.$r8$clinit;
                    if (bitmap.getWidth() != width || bitmap.getHeight() != height) {
                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                        bitmap.recycle();
                        bitmap = createScaledBitmap;
                    }
                    lottieImageAsset.setBitmap(bitmap);
                }
            }
            for (Map.Entry<String, LottieImageAsset> entry2 : lottieComposition.getImages().entrySet()) {
                if (entry2.getValue().getBitmap() == null) {
                    return new LottieResult<>(new IllegalStateException("There is no image for " + entry2.getValue().getFileName()));
                }
            }
            if (str != null) {
                LottieCompositionCache.getInstance().put(lottieComposition, str);
            }
            return new LottieResult<>(lottieComposition);
        } catch (IOException e) {
            return new LottieResult<>(e);
        }
    }

    private static String rawResCacheKey(Context context, int i) {
        StringBuilder sb = new StringBuilder("rawRes");
        sb.append((context.getResources().getConfiguration().uiMode & 48) == 32 ? "_night_" : "_day_");
        sb.append(i);
        return sb.toString();
    }

    public static LottieTask<LottieComposition> fromRawRes(Context context, final int i, final String str) {
        final WeakReference weakReference = new WeakReference(context);
        final Context applicationContext = context.getApplicationContext();
        return cache(str, new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieCompositionFactory.3
            @Override // java.util.concurrent.Callable
            public final LottieResult<LottieComposition> call() throws Exception {
                Context context2 = (Context) weakReference.get();
                if (context2 == null) {
                    context2 = applicationContext;
                }
                return LottieCompositionFactory.fromRawResSync(context2, i, str);
            }
        });
    }

    public static LottieResult<LottieComposition> fromRawResSync(Context context, int i, String str) {
        Boolean bool;
        try {
            BufferedSource buffer = Okio.buffer(Okio.source(context.getResources().openRawResource(i)));
            try {
                BufferedSource peek = buffer.peek();
                byte[] bArr = MAGIC;
                int length = bArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        peek.close();
                        bool = Boolean.TRUE;
                        break;
                    }
                    if (peek.readByte() != bArr[i2]) {
                        bool = Boolean.FALSE;
                        break;
                    }
                    i2++;
                }
            } catch (Exception unused) {
                Logger.error();
                bool = Boolean.FALSE;
            }
            return bool.booleanValue() ? fromZipStreamSync(new ZipInputStream(buffer.inputStream()), str) : fromJsonInputStreamSync(buffer.inputStream(), str);
        } catch (Resources.NotFoundException e) {
            return new LottieResult<>(e);
        }
    }
}
