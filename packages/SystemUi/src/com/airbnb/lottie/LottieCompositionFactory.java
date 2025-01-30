package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.airbnb.lottie.parser.LottieCompositionMoshiParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.sec.ims.presence.ServiceTuple;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okio.InputStreamSource;
import okio.RealBufferedSource;
import okio.Timeout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LottieCompositionFactory {
    public static final Map taskCache = new HashMap();
    public static final Set taskIdleListeners = new HashSet();
    public static final byte[] MAGIC = {80, 75, 3, 4};

    private LottieCompositionFactory() {
    }

    public static LottieTask cache(final String str, Callable callable, LottieCompositionFactory$$ExternalSyntheticLambda2 lottieCompositionFactory$$ExternalSyntheticLambda2) {
        final LottieComposition lottieComposition = str == null ? null : (LottieComposition) LottieCompositionCache.INSTANCE.cache.get(str);
        LottieTask lottieTask = lottieComposition != null ? new LottieTask(new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return new LottieResult(LottieComposition.this);
            }
        }) : null;
        Map map = taskCache;
        if (str != null) {
            HashMap hashMap = (HashMap) map;
            if (hashMap.containsKey(str)) {
                lottieTask = (LottieTask) hashMap.get(str);
            }
        }
        if (lottieTask != null) {
            if (lottieCompositionFactory$$ExternalSyntheticLambda2 != null) {
                lottieCompositionFactory$$ExternalSyntheticLambda2.run();
            }
            return lottieTask;
        }
        LottieTask lottieTask2 = new LottieTask(callable);
        if (str != null) {
            final int i = 0;
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            lottieTask2.addListener(new LottieListener() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda4
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    int i2 = i;
                    AtomicBoolean atomicBoolean2 = atomicBoolean;
                    String str2 = str;
                    switch (i2) {
                        case 0:
                            HashMap hashMap2 = (HashMap) LottieCompositionFactory.taskCache;
                            hashMap2.remove(str2);
                            atomicBoolean2.set(true);
                            if (hashMap2.size() == 0) {
                                LottieCompositionFactory.notifyTaskCacheIdleListeners();
                                break;
                            }
                            break;
                        default:
                            HashMap hashMap3 = (HashMap) LottieCompositionFactory.taskCache;
                            hashMap3.remove(str2);
                            atomicBoolean2.set(true);
                            if (hashMap3.size() == 0) {
                                LottieCompositionFactory.notifyTaskCacheIdleListeners();
                                break;
                            }
                            break;
                    }
                }
            });
            final int i2 = 1;
            lottieTask2.addFailureListener(new LottieListener() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda4
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    int i22 = i2;
                    AtomicBoolean atomicBoolean2 = atomicBoolean;
                    String str2 = str;
                    switch (i22) {
                        case 0:
                            HashMap hashMap2 = (HashMap) LottieCompositionFactory.taskCache;
                            hashMap2.remove(str2);
                            atomicBoolean2.set(true);
                            if (hashMap2.size() == 0) {
                                LottieCompositionFactory.notifyTaskCacheIdleListeners();
                                break;
                            }
                            break;
                        default:
                            HashMap hashMap3 = (HashMap) LottieCompositionFactory.taskCache;
                            hashMap3.remove(str2);
                            atomicBoolean2.set(true);
                            if (hashMap3.size() == 0) {
                                LottieCompositionFactory.notifyTaskCacheIdleListeners();
                                break;
                            }
                            break;
                    }
                }
            });
            if (!atomicBoolean.get()) {
                HashMap hashMap2 = (HashMap) map;
                hashMap2.put(str, lottieTask2);
                if (hashMap2.size() == 1) {
                    notifyTaskCacheIdleListeners();
                }
            }
        }
        return lottieTask2;
    }

    public static LottieTask fromAsset(Context context, String str) {
        String m21m = KeyAttributes$$ExternalSyntheticOutline0.m21m("asset_", str);
        return cache(m21m, new LottieCompositionFactory$$ExternalSyntheticLambda0(context.getApplicationContext(), str, m21m, 1), null);
    }

    public static LottieResult fromAssetSync(Context context, String str, String str2) {
        try {
            if (!str.endsWith(".zip") && !str.endsWith(".lottie")) {
                return fromJsonInputStreamSync(context.getAssets().open(str), str2);
            }
            return fromZipStreamSync(context, new ZipInputStream(context.getAssets().open(str)), str2);
        } catch (IOException e) {
            return new LottieResult((Throwable) e);
        }
    }

    public static LottieResult fromJsonInputStreamSync(InputStream inputStream, String str) {
        try {
            RealBufferedSource realBufferedSource = new RealBufferedSource(new InputStreamSource(inputStream, new Timeout()));
            String[] strArr = JsonReader.REPLACEMENT_CHARS;
            return fromJsonReaderSyncInternal(new JsonUtf8Reader(realBufferedSource), str, true);
        } finally {
            Utils.closeQuietly(inputStream);
        }
    }

    public static LottieResult fromJsonReaderSyncInternal(JsonUtf8Reader jsonUtf8Reader, String str, boolean z) {
        try {
            try {
                LottieComposition parse = LottieCompositionMoshiParser.parse(jsonUtf8Reader);
                if (str != null) {
                    LottieCompositionCache.INSTANCE.cache.put(str, parse);
                }
                LottieResult lottieResult = new LottieResult(parse);
                if (z) {
                    Utils.closeQuietly(jsonUtf8Reader);
                }
                return lottieResult;
            } catch (Exception e) {
                LottieResult lottieResult2 = new LottieResult((Throwable) e);
                if (z) {
                    Utils.closeQuietly(jsonUtf8Reader);
                }
                return lottieResult2;
            }
        } catch (Throwable th) {
            if (z) {
                Utils.closeQuietly(jsonUtf8Reader);
            }
            throw th;
        }
    }

    public static LottieTask fromRawRes(Context context, final String str, final int i) {
        final WeakReference weakReference = new WeakReference(context);
        final Context applicationContext = context.getApplicationContext();
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda5
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Context context2 = (Context) weakReference.get();
                if (context2 == null) {
                    context2 = applicationContext;
                }
                return LottieCompositionFactory.fromRawResSync(context2, str, i);
            }
        }, null);
    }

    public static LottieResult fromRawResSync(Context context, String str, int i) {
        Boolean bool;
        try {
            final RealBufferedSource realBufferedSource = new RealBufferedSource(new InputStreamSource(context.getResources().openRawResource(i), new Timeout()));
            try {
                try {
                    RealBufferedSource peek = realBufferedSource.peek();
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
                    Logger.INSTANCE.getClass();
                    bool = Boolean.FALSE;
                }
            } catch (NoSuchMethodError unused2) {
                bool = Boolean.FALSE;
            }
            return bool.booleanValue() ? fromZipStreamSync(context, new ZipInputStream(new InputStream() { // from class: okio.RealBufferedSource$inputStream$1
                @Override // java.io.InputStream
                public final int available() {
                    RealBufferedSource realBufferedSource2 = RealBufferedSource.this;
                    if (realBufferedSource2.closed) {
                        throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                    }
                    return (int) Math.min(realBufferedSource2.bufferField.size, Integer.MAX_VALUE);
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public final void close() {
                    RealBufferedSource.this.close();
                }

                @Override // java.io.InputStream
                public final int read() {
                    RealBufferedSource realBufferedSource2 = RealBufferedSource.this;
                    if (realBufferedSource2.closed) {
                        throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                    }
                    Buffer buffer = realBufferedSource2.bufferField;
                    if (buffer.size == 0 && realBufferedSource2.source.read(buffer, 8192) == -1) {
                        return -1;
                    }
                    return RealBufferedSource.this.bufferField.readByte() & 255;
                }

                public final String toString() {
                    return RealBufferedSource.this + ".inputStream()";
                }

                @Override // java.io.InputStream
                public final int read(byte[] bArr2, int i3, int i4) {
                    if (!RealBufferedSource.this.closed) {
                        Util.checkOffsetAndCount(bArr2.length, i3, i4);
                        RealBufferedSource realBufferedSource2 = RealBufferedSource.this;
                        Buffer buffer = realBufferedSource2.bufferField;
                        if (buffer.size == 0 && realBufferedSource2.source.read(buffer, 8192) == -1) {
                            return -1;
                        }
                        return RealBufferedSource.this.bufferField.read(bArr2, i3, i4);
                    }
                    throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                }
            }), str) : fromJsonInputStreamSync(new InputStream() { // from class: okio.RealBufferedSource$inputStream$1
                @Override // java.io.InputStream
                public final int available() {
                    RealBufferedSource realBufferedSource2 = RealBufferedSource.this;
                    if (realBufferedSource2.closed) {
                        throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                    }
                    return (int) Math.min(realBufferedSource2.bufferField.size, Integer.MAX_VALUE);
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public final void close() {
                    RealBufferedSource.this.close();
                }

                @Override // java.io.InputStream
                public final int read() {
                    RealBufferedSource realBufferedSource2 = RealBufferedSource.this;
                    if (realBufferedSource2.closed) {
                        throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                    }
                    Buffer buffer = realBufferedSource2.bufferField;
                    if (buffer.size == 0 && realBufferedSource2.source.read(buffer, 8192) == -1) {
                        return -1;
                    }
                    return RealBufferedSource.this.bufferField.readByte() & 255;
                }

                public final String toString() {
                    return RealBufferedSource.this + ".inputStream()";
                }

                @Override // java.io.InputStream
                public final int read(byte[] bArr2, int i3, int i4) {
                    if (!RealBufferedSource.this.closed) {
                        Util.checkOffsetAndCount(bArr2.length, i3, i4);
                        RealBufferedSource realBufferedSource2 = RealBufferedSource.this;
                        Buffer buffer = realBufferedSource2.bufferField;
                        if (buffer.size == 0 && realBufferedSource2.source.read(buffer, 8192) == -1) {
                            return -1;
                        }
                        return RealBufferedSource.this.bufferField.read(bArr2, i3, i4);
                    }
                    throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                }
            }, str);
        } catch (Resources.NotFoundException e) {
            return new LottieResult((Throwable) e);
        }
    }

    public static LottieResult fromZipStreamSync(Context context, ZipInputStream zipInputStream, String str) {
        try {
            return fromZipStreamSyncInternal(context, zipInputStream, str);
        } finally {
            Utils.closeQuietly(zipInputStream);
        }
    }

    public static LottieResult fromZipStreamSyncInternal(Context context, ZipInputStream zipInputStream, String str) {
        LottieImageAsset lottieImageAsset;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
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
                    RealBufferedSource realBufferedSource = new RealBufferedSource(new InputStreamSource(zipInputStream, new Timeout()));
                    String[] strArr = JsonReader.REPLACEMENT_CHARS;
                    lottieComposition = (LottieComposition) fromJsonReaderSyncInternal(new JsonUtf8Reader(realBufferedSource), null, false).value;
                } else {
                    if (!name.contains(".png") && !name.contains(".webp") && !name.contains(".jpg") && !name.contains(".jpeg")) {
                        if (!name.contains(".ttf") && !name.contains(".otf")) {
                            zipInputStream.closeEntry();
                        }
                        String[] split = name.split("/");
                        String str2 = split[split.length - 1];
                        String str3 = str2.split("\\.")[0];
                        File file = new File(context.getCacheDir(), str2);
                        new FileOutputStream(file);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            try {
                                byte[] bArr = new byte[4096];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Throwable th) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            Logger.warning("Unable to save font " + str3 + " to the temporary file: " + str2 + ". ", th3);
                        }
                        Typeface createFromFile = Typeface.createFromFile(file);
                        if (!file.delete()) {
                            Logger.warning("Failed to delete temp font file " + file.getAbsolutePath() + ".");
                        }
                        hashMap2.put(str3, createFromFile);
                    }
                    String[] split2 = name.split("/");
                    hashMap.put(split2[split2.length - 1], BitmapFactory.decodeStream(zipInputStream));
                }
                nextEntry = zipInputStream.getNextEntry();
            }
            if (lottieComposition == null) {
                return new LottieResult((Throwable) new IllegalArgumentException("Unable to parse composition"));
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                String str4 = (String) entry.getKey();
                Iterator it = lottieComposition.images.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        lottieImageAsset = null;
                        break;
                    }
                    lottieImageAsset = (LottieImageAsset) it.next();
                    if (lottieImageAsset.fileName.equals(str4)) {
                        break;
                    }
                }
                if (lottieImageAsset != null) {
                    Bitmap bitmap = (Bitmap) entry.getValue();
                    Utils.C06051 c06051 = Utils.threadLocalPathMeasure;
                    int width = bitmap.getWidth();
                    int i = lottieImageAsset.width;
                    int i2 = lottieImageAsset.height;
                    if (width != i || bitmap.getHeight() != i2) {
                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
                        bitmap.recycle();
                        bitmap = createScaledBitmap;
                    }
                    lottieImageAsset.bitmap = bitmap;
                }
            }
            for (Map.Entry entry2 : hashMap2.entrySet()) {
                boolean z = false;
                for (Font font : lottieComposition.fonts.values()) {
                    if (font.family.equals(entry2.getKey())) {
                        font.typeface = (Typeface) entry2.getValue();
                        z = true;
                    }
                }
                if (!z) {
                    Logger.warning("Parsed font for " + ((String) entry2.getKey()) + " however it was not found in the animation.");
                }
            }
            if (hashMap.isEmpty()) {
                Iterator it2 = lottieComposition.images.entrySet().iterator();
                while (it2.hasNext()) {
                    LottieImageAsset lottieImageAsset2 = (LottieImageAsset) ((Map.Entry) it2.next()).getValue();
                    if (lottieImageAsset2 == null) {
                        return null;
                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inDensity = 160;
                    String str5 = lottieImageAsset2.fileName;
                    if (str5.startsWith("data:") && str5.indexOf("base64,") > 0) {
                        try {
                            byte[] decode = Base64.decode(str5.substring(str5.indexOf(44) + 1), 0);
                            lottieImageAsset2.bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length, options);
                        } catch (IllegalArgumentException e) {
                            Logger.warning("data URL did not have correct base64 format.", e);
                            return null;
                        }
                    }
                }
            }
            if (str != null) {
                LottieCompositionCache.INSTANCE.cache.put(str, lottieComposition);
            }
            return new LottieResult(lottieComposition);
        } catch (IOException e2) {
            return new LottieResult((Throwable) e2);
        }
    }

    public static void notifyTaskCacheIdleListeners() {
        ArrayList arrayList = new ArrayList(taskIdleListeners);
        if (arrayList.size() <= 0) {
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(arrayList.get(0));
        throw null;
    }

    public static String rawResCacheKey(int i, Context context) {
        StringBuilder sb = new StringBuilder("rawRes");
        sb.append((context.getResources().getConfiguration().uiMode & 48) == 32 ? "_night_" : "_day_");
        sb.append(i);
        return sb.toString();
    }
}
