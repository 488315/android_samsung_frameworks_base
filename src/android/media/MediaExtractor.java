package android.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.DrmInitData;
import android.media.MediaCas;
import android.media.MediaCodec;
import android.media.metrics.LogSessionId;
import android.net.Uri;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.PersistableBundle;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public final class MediaExtractor {
    public static final int SAMPLE_FLAG_ENCRYPTED = 2;
    public static final int SAMPLE_FLAG_PARTIAL_FRAME = 4;
    public static final int SAMPLE_FLAG_SYNC = 1;
    public static final int SEEK_TO_CLOSEST_SYNC = 2;
    public static final int SEEK_TO_NEXT_SYNC = 1;
    public static final int SEEK_TO_PREVIOUS_SYNC = 0;
    public static final int SEM_MODE_RUNNING_ON_CALLING_PROCESS = 1;
    public static final int SEM_MODE_RUNNING_ON_EXTRACTOR_SERVICE = 0;
    private LogSessionId mLogSessionId = LogSessionId.LOG_SESSION_ID_NONE;
    private MediaCas mMediaCas;
    private long mNativeContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SampleFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekMode {
    }

    private native Map<String, Object> getFileFormatNative();

    private native Map<String, Object> getTrackFormatNative(int i);

    static /* synthetic */ DrmInitData.SchemeInitData lambda$getDrmInitData$3(DrmInitData.SchemeInitData schemeInitData) {
        return schemeInitData;
    }

    private final native void nativeSetDataSource(IBinder iBinder, String str, String[] strArr, String[] strArr2) throws IOException;

    private final native void nativeSetMediaCas(IHwBinder iHwBinder);

    private final native void native_finalize();

    private native List<AudioPresentation> native_getAudioPresentations(int i);

    private native PersistableBundle native_getMetrics();

    private static final native void native_init();

    private native void native_setLogSessionId(String str);

    private final native void native_setup();

    private native void setRunningMode(int i);

    public native boolean advance();

    public native long getCachedDuration();

    public native boolean getSampleCryptoInfo(MediaCodec.CryptoInfo cryptoInfo);

    public native int getSampleFlags();

    public native long getSampleSize();

    public native long getSampleTime();

    public native int getSampleTrackIndex();

    public final native int getTrackCount();

    public native boolean hasCacheReachedEndOfStream();

    public native int readSampleData(ByteBuffer byteBuffer, int i);

    public final native void release();

    public native void seekTo(long j, int i);

    public native void selectTrack(int i);

    public final native void setDataSource(MediaDataSource mediaDataSource) throws IOException;

    public final native void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException;

    public native void unselectTrack(int i);

    public MediaExtractor() {
        native_setup();
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0059 A[PHI: r1 r2
      0x0059: PHI (r1v3 android.content.res.AssetFileDescriptor) = (r1v2 android.content.res.AssetFileDescriptor), (r1v4 android.content.res.AssetFileDescriptor) binds: [B:31:0x0057, B:28:0x0053] A[DONT_GENERATE, DONT_INLINE]
      0x0059: PHI (r2v4 android.media.MediaExtractor) = (r2v3 android.media.MediaExtractor), (r2v6 android.media.MediaExtractor) binds: [B:31:0x0057, B:28:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException {
        MediaExtractor mediaExtractor;
        String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("file")) {
            setDataSource(uri.getPath());
            return;
        }
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = null;
        try {
            try {
                assetFileDescriptorOpenAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
                if (assetFileDescriptorOpenAssetFileDescriptor == null) {
                    if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                        assetFileDescriptorOpenAssetFileDescriptor.close();
                        return;
                    }
                    return;
                }
                if (assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength() < 0) {
                    setDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor());
                } else {
                    mediaExtractor = this;
                    try {
                        mediaExtractor.setDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor(), assetFileDescriptorOpenAssetFileDescriptor.getStartOffset(), assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength());
                    } catch (IOException unused) {
                        if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                            assetFileDescriptorOpenAssetFileDescriptor.close();
                        }
                        mediaExtractor.setDataSource(uri.toString(), map);
                        return;
                    } catch (SecurityException unused2) {
                        if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                        }
                        mediaExtractor.setDataSource(uri.toString(), map);
                        return;
                    }
                }
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                }
            } finally {
            }
        } catch (IOException unused3) {
            mediaExtractor = this;
        } catch (SecurityException unused4) {
            mediaExtractor = this;
        }
    }

    public final void setDataSource(String str, Map<String, String> map) throws IOException {
        String[] strArr;
        String[] strArr2;
        if (map != null) {
            strArr = new String[map.size()];
            strArr2 = new String[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                strArr2[i] = entry.getValue();
                i++;
            }
        } else {
            strArr = null;
            strArr2 = null;
        }
        nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(str), str, strArr, strArr2);
    }

    public final void setDataSource(String str) throws IOException {
        nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(str), str, null, null);
    }

    public final void setDataSource(AssetFileDescriptor assetFileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        Preconditions.checkNotNull(assetFileDescriptor);
        if (assetFileDescriptor.getDeclaredLength() < 0) {
            setDataSource(assetFileDescriptor.getFileDescriptor());
        } else {
            setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    public final void setDataSource(FileDescriptor fileDescriptor) throws IOException {
        setDataSource(fileDescriptor, 0L, 576460752303423487L);
    }

    @Deprecated
    public final void setMediaCas(MediaCas mediaCas) {
        this.mMediaCas = mediaCas;
        nativeSetMediaCas(mediaCas.getBinder());
    }

    public static final class CasInfo {
        private final byte[] mPrivateData;
        private final MediaCas.Session mSession;
        private final int mSystemId;

        CasInfo(int i, MediaCas.Session session, byte[] bArr) {
            this.mSystemId = i;
            this.mSession = session;
            this.mPrivateData = bArr;
        }

        public int getSystemId() {
            return this.mSystemId;
        }

        public byte[] getPrivateData() {
            return this.mPrivateData;
        }

        public MediaCas.Session getSession() {
            return this.mSession;
        }
    }

    public CasInfo getCasInfo(int i) {
        byte[] bArr;
        Map<String, Object> trackFormatNative = getTrackFormatNative(i);
        MediaCas.Session sessionCreateFromSessionId = null;
        if (!trackFormatNative.containsKey(MediaFormat.KEY_CA_SYSTEM_ID)) {
            return null;
        }
        int iIntValue = ((Integer) trackFormatNative.get(MediaFormat.KEY_CA_SYSTEM_ID)).intValue();
        if (trackFormatNative.containsKey(MediaFormat.KEY_CA_PRIVATE_DATA)) {
            ByteBuffer byteBuffer = (ByteBuffer) trackFormatNative.get(MediaFormat.KEY_CA_PRIVATE_DATA);
            byteBuffer.rewind();
            bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
        } else {
            bArr = null;
        }
        if (this.mMediaCas != null && trackFormatNative.containsKey(MediaFormat.KEY_CA_SESSION_ID)) {
            ByteBuffer byteBuffer2 = (ByteBuffer) trackFormatNative.get(MediaFormat.KEY_CA_SESSION_ID);
            byteBuffer2.rewind();
            byte[] bArr2 = new byte[byteBuffer2.remaining()];
            byteBuffer2.get(bArr2);
            sessionCreateFromSessionId = this.mMediaCas.createFromSessionId(bArr2);
        }
        return new CasInfo(iIntValue, sessionCreateFromSessionId, bArr);
    }

    protected void finalize() {
        native_finalize();
    }

    public DrmInitData getDrmInitData() {
        Map<String, Object> fileFormatNative = getFileFormatNative();
        if (fileFormatNative == null) {
            return null;
        }
        if (fileFormatNative.containsKey("pssh")) {
            final DrmInitData.SchemeInitData[] schemeInitDataArr = (DrmInitData.SchemeInitData[]) getPsshInfo().entrySet().stream().map(new Function() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MediaExtractor.lambda$getDrmInitData$0((Map.Entry) obj);
                }
            }).toArray(new IntFunction() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return MediaExtractor.lambda$getDrmInitData$1(i);
                }
            });
            final Map map = (Map) Arrays.stream(schemeInitDataArr).collect(Collectors.toMap(new Function() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((DrmInitData.SchemeInitData) obj).uuid;
                }
            }, new Function() { // from class: android.media.MediaExtractor$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MediaExtractor.lambda$getDrmInitData$3((DrmInitData.SchemeInitData) obj);
                }
            }));
            return new DrmInitData(this) { // from class: android.media.MediaExtractor.1
                @Override // android.media.DrmInitData
                public DrmInitData.SchemeInitData get(UUID uuid) {
                    return (DrmInitData.SchemeInitData) map.get(uuid);
                }

                @Override // android.media.DrmInitData
                public int getSchemeInitDataCount() {
                    return schemeInitDataArr.length;
                }

                @Override // android.media.DrmInitData
                public DrmInitData.SchemeInitData getSchemeInitDataAt(int i) {
                    return schemeInitDataArr[i];
                }
            };
        }
        int trackCount = getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            Map<String, Object> trackFormatNative = getTrackFormatNative(i);
            if (trackFormatNative.containsKey("crypto-key")) {
                ByteBuffer byteBuffer = (ByteBuffer) trackFormatNative.get("crypto-key");
                byteBuffer.rewind();
                byte[] bArr = new byte[byteBuffer.remaining()];
                byteBuffer.get(bArr);
                final DrmInitData.SchemeInitData schemeInitData = new DrmInitData.SchemeInitData(DrmInitData.SchemeInitData.UUID_NIL, "webm", bArr);
                return new DrmInitData(this) { // from class: android.media.MediaExtractor.2
                    @Override // android.media.DrmInitData
                    public int getSchemeInitDataCount() {
                        return 1;
                    }

                    @Override // android.media.DrmInitData
                    public DrmInitData.SchemeInitData get(UUID uuid) {
                        return schemeInitData;
                    }

                    @Override // android.media.DrmInitData
                    public DrmInitData.SchemeInitData getSchemeInitDataAt(int i2) {
                        return schemeInitData;
                    }
                };
            }
        }
        return null;
    }

    static /* synthetic */ DrmInitData.SchemeInitData lambda$getDrmInitData$0(Map.Entry entry) {
        return new DrmInitData.SchemeInitData((UUID) entry.getKey(), "cenc", (byte[]) entry.getValue());
    }

    static /* synthetic */ DrmInitData.SchemeInitData[] lambda$getDrmInitData$1(int i) {
        return new DrmInitData.SchemeInitData[i];
    }

    public List<AudioPresentation> getAudioPresentations(int i) {
        return native_getAudioPresentations(i);
    }

    public Map<UUID, byte[]> getPsshInfo() {
        Map<String, Object> fileFormatNative = getFileFormatNative();
        if (fileFormatNative == null || !fileFormatNative.containsKey("pssh")) {
            return null;
        }
        ByteBuffer byteBuffer = (ByteBuffer) fileFormatNative.get("pssh");
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.rewind();
        fileFormatNative.remove("pssh");
        HashMap map = new HashMap();
        while (byteBuffer.remaining() > 0) {
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
            UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
            byteBuffer.order(ByteOrder.nativeOrder());
            byte[] bArr = new byte[byteBuffer.getInt()];
            byteBuffer.get(bArr);
            map.put(uuid, bArr);
        }
        return map;
    }

    public MediaFormat getTrackFormat(int i) {
        return new MediaFormat(getTrackFormatNative(i));
    }

    public void setLogSessionId(LogSessionId logSessionId) {
        this.mLogSessionId = (LogSessionId) Objects.requireNonNull(logSessionId);
        native_setLogSessionId(logSessionId.getStringId());
    }

    public LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    public PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public void semSetRunningMode(int i) {
        setRunningMode(i);
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public static final class MetricsConstants {
        public static final String FORMAT = "android.media.mediaextractor.fmt";
        public static final String MIME_TYPE = "android.media.mediaextractor.mime";
        public static final String TRACKS = "android.media.mediaextractor.ntrk";

        private MetricsConstants() {
        }
    }
}
