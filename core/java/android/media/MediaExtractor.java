package android.media;

import android.content.res.AssetFileDescriptor;
import android.media.metrics.LogSessionId;
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
    public @interface SampleFlag {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekMode {}

    private native Map<String, Object> getFileFormatNative();

    private native Map<String, Object> getTrackFormatNative(int i);

    private final native void nativeSetDataSource(
            IBinder iBinder, String str, String[] strArr, String[] strArr2) throws IOException;

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

    public final native void setDataSource(FileDescriptor fileDescriptor, long j, long j2)
            throws IOException;

    public native void unselectTrack(int i);

    public MediaExtractor() {
        native_setup();
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:

       if (0 == 0) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:

       r1.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:

       setDataSource(r11.toString(), r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0065, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005b, code lost:

       if (0 == 0) goto L31;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDataSource(
            android.content.Context r10,
            android.net.Uri r11,
            java.util.Map<java.lang.String, java.lang.String> r12)
            throws java.io.IOException {
        /*
            r9 = this;
            java.lang.String r0 = r11.getScheme()
            if (r0 == 0) goto L66
            java.lang.String r1 = "file"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto Lf
            goto L66
        Lf:
            r1 = 0
            android.content.ContentResolver r2 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            java.lang.String r3 = "r"
            android.content.res.AssetFileDescriptor r3 = r2.openAssetFileDescriptor(r11, r3)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            r1 = r3
            if (r1 != 0) goto L23
            if (r1 == 0) goto L22
            r1.close()
        L22:
            return
        L23:
            long r3 = r1.getDeclaredLength()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L35
            java.io.FileDescriptor r3 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            r9.setDataSource(r3)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            goto L46
        L35:
            java.io.FileDescriptor r4 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            long r5 = r1.getStartOffset()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            long r7 = r1.getDeclaredLength()     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
            r3 = r9
            r3.setDataSource(r4, r5, r7)     // Catch: java.lang.Throwable -> L4c java.io.IOException -> L53 java.lang.SecurityException -> L5a
        L46:
            if (r1 == 0) goto L4b
            r1.close()
        L4b:
            return
        L4c:
            r2 = move-exception
            if (r1 == 0) goto L52
            r1.close()
        L52:
            throw r2
        L53:
            r2 = move-exception
            if (r1 == 0) goto L5e
        L56:
            r1.close()
            goto L5e
        L5a:
            r2 = move-exception
            if (r1 == 0) goto L5e
            goto L56
        L5e:
            java.lang.String r2 = r11.toString()
            r9.setDataSource(r2, r12)
            return
        L66:
            java.lang.String r1 = r11.getPath()
            r9.setDataSource(r1)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.media.MediaExtractor.setDataSource(android.content.Context,"
                        + " android.net.Uri, java.util.Map):void");
    }

    public final void setDataSource(String path, Map<String, String> headers) throws IOException {
        String[] keys = null;
        String[] values = null;
        if (headers != null) {
            keys = new String[headers.size()];
            values = new String[headers.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                keys[i] = entry.getKey();
                values[i] = entry.getValue();
                i++;
            }
        }
        nativeSetDataSource(
                MediaHTTPService.createHttpServiceBinderIfNecessary(path), path, keys, values);
    }

    public final void setDataSource(String path) throws IOException {
        nativeSetDataSource(
                MediaHTTPService.createHttpServiceBinderIfNecessary(path), path, null, null);
    }

    public final void setDataSource(AssetFileDescriptor afd)
            throws IOException, IllegalArgumentException, IllegalStateException {
        Preconditions.checkNotNull(afd);
        if (afd.getDeclaredLength() < 0) {
            setDataSource(afd.getFileDescriptor());
        } else {
            setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
        }
    }

    public final void setDataSource(FileDescriptor fd) throws IOException {
        setDataSource(fd, 0L, 576460752303423487L);
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

        CasInfo(int systemId, MediaCas.Session session, byte[] privateData) {
            this.mSystemId = systemId;
            this.mSession = session;
            this.mPrivateData = privateData;
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

    public CasInfo getCasInfo(int index) {
        Map<String, Object> formatMap = getTrackFormatNative(index);
        if (formatMap.containsKey(MediaFormat.KEY_CA_SYSTEM_ID)) {
            int systemId = ((Integer) formatMap.get(MediaFormat.KEY_CA_SYSTEM_ID)).intValue();
            MediaCas.Session session = null;
            byte[] privateData = null;
            if (formatMap.containsKey(MediaFormat.KEY_CA_PRIVATE_DATA)) {
                ByteBuffer buf = (ByteBuffer) formatMap.get(MediaFormat.KEY_CA_PRIVATE_DATA);
                buf.rewind();
                privateData = new byte[buf.remaining()];
                buf.get(privateData);
            }
            if (this.mMediaCas != null && formatMap.containsKey(MediaFormat.KEY_CA_SESSION_ID)) {
                ByteBuffer buf2 = (ByteBuffer) formatMap.get(MediaFormat.KEY_CA_SESSION_ID);
                buf2.rewind();
                byte[] sessionId = new byte[buf2.remaining()];
                buf2.get(sessionId);
                session = this.mMediaCas.createFromSessionId(sessionId);
            }
            return new CasInfo(systemId, session, privateData);
        }
        return null;
    }

    protected void finalize() {
        native_finalize();
    }

    public DrmInitData getDrmInitData() {
        Map<String, Object> formatMap = getFileFormatNative();
        if (formatMap == null) {
            return null;
        }
        if (formatMap.containsKey("pssh")) {
            Map<UUID, byte[]> psshMap = getPsshInfo();
            final DrmInitData.SchemeInitData[] schemeInitDatas =
                    (DrmInitData.SchemeInitData[])
                            psshMap.entrySet().stream()
                                    .map(
                                            new Function() { // from class:
                                                // android.media.MediaExtractor$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return MediaExtractor.lambda$getDrmInitData$0(
                                                            (Map.Entry) obj);
                                                }
                                            })
                                    .toArray(
                                            new IntFunction() { // from class:
                                                // android.media.MediaExtractor$$ExternalSyntheticLambda1
                                                @Override // java.util.function.IntFunction
                                                public final Object apply(int i) {
                                                    return MediaExtractor.lambda$getDrmInitData$1(
                                                            i);
                                                }
                                            });
            final Map<UUID, DrmInitData.SchemeInitData> initDataMap =
                    (Map)
                            Arrays.stream(schemeInitDatas)
                                    .collect(
                                            Collectors.toMap(
                                                    new Function() { // from class:
                                                        // android.media.MediaExtractor$$ExternalSyntheticLambda2
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            UUID uuid;
                                                            uuid =
                                                                    ((DrmInitData.SchemeInitData)
                                                                                    obj)
                                                                            .uuid;
                                                            return uuid;
                                                        }
                                                    },
                                                    new Function() { // from class:
                                                        // android.media.MediaExtractor$$ExternalSyntheticLambda3
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            return MediaExtractor
                                                                    .lambda$getDrmInitData$3(
                                                                            (DrmInitData
                                                                                            .SchemeInitData)
                                                                                    obj);
                                                        }
                                                    }));
            return new DrmInitData() { // from class: android.media.MediaExtractor.1
                @Override // android.media.DrmInitData
                public DrmInitData.SchemeInitData get(UUID schemeUuid) {
                    return (DrmInitData.SchemeInitData) initDataMap.get(schemeUuid);
                }

                @Override // android.media.DrmInitData
                public int getSchemeInitDataCount() {
                    return schemeInitDatas.length;
                }

                @Override // android.media.DrmInitData
                public DrmInitData.SchemeInitData getSchemeInitDataAt(int index) {
                    return schemeInitDatas[index];
                }
            };
        }
        int numTracks = getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            Map<String, Object> trackFormatMap = getTrackFormatNative(i);
            if (trackFormatMap.containsKey("crypto-key")) {
                ByteBuffer buf = (ByteBuffer) trackFormatMap.get("crypto-key");
                buf.rewind();
                byte[] data = new byte[buf.remaining()];
                buf.get(data);
                final DrmInitData.SchemeInitData webmSchemeInitData =
                        new DrmInitData.SchemeInitData(
                                DrmInitData.SchemeInitData.UUID_NIL, "webm", data);
                return new DrmInitData() { // from class: android.media.MediaExtractor.2
                    @Override // android.media.DrmInitData
                    public DrmInitData.SchemeInitData get(UUID schemeUuid) {
                        return webmSchemeInitData;
                    }

                    @Override // android.media.DrmInitData
                    public int getSchemeInitDataCount() {
                        return 1;
                    }

                    @Override // android.media.DrmInitData
                    public DrmInitData.SchemeInitData getSchemeInitDataAt(int index) {
                        return webmSchemeInitData;
                    }
                };
            }
        }
        return null;
    }

    static /* synthetic */ DrmInitData.SchemeInitData lambda$getDrmInitData$0(Map.Entry entry) {
        return new DrmInitData.SchemeInitData(
                (UUID) entry.getKey(), "cenc", (byte[]) entry.getValue());
    }

    static /* synthetic */ DrmInitData.SchemeInitData[] lambda$getDrmInitData$1(int x$0) {
        return new DrmInitData.SchemeInitData[x$0];
    }

    static /* synthetic */ DrmInitData.SchemeInitData lambda$getDrmInitData$3(
            DrmInitData.SchemeInitData initData) {
        return initData;
    }

    public List<AudioPresentation> getAudioPresentations(int trackIndex) {
        return native_getAudioPresentations(trackIndex);
    }

    public Map<UUID, byte[]> getPsshInfo() {
        Map<UUID, byte[]> psshMap = null;
        Map<String, Object> formatMap = getFileFormatNative();
        if (formatMap != null && formatMap.containsKey("pssh")) {
            ByteBuffer rawpssh = (ByteBuffer) formatMap.get("pssh");
            rawpssh.order(ByteOrder.nativeOrder());
            rawpssh.rewind();
            formatMap.remove("pssh");
            psshMap = new HashMap<>();
            while (rawpssh.remaining() > 0) {
                rawpssh.order(ByteOrder.BIG_ENDIAN);
                long msb = rawpssh.getLong();
                long lsb = rawpssh.getLong();
                UUID uuid = new UUID(msb, lsb);
                rawpssh.order(ByteOrder.nativeOrder());
                int datalen = rawpssh.getInt();
                byte[] psshdata = new byte[datalen];
                rawpssh.get(psshdata);
                psshMap.put(uuid, psshdata);
            }
        }
        return psshMap;
    }

    public MediaFormat getTrackFormat(int index) {
        return new MediaFormat(getTrackFormatNative(index));
    }

    public void setLogSessionId(LogSessionId logSessionId) {
        this.mLogSessionId = (LogSessionId) Objects.requireNonNull(logSessionId);
        native_setLogSessionId(logSessionId.getStringId());
    }

    public LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    public PersistableBundle getMetrics() {
        PersistableBundle bundle = native_getMetrics();
        return bundle;
    }

    public void semSetRunningMode(int runningMode) {
        setRunningMode(runningMode);
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public static final class MetricsConstants {
        public static final String FORMAT = "android.media.mediaextractor.fmt";
        public static final String MIME_TYPE = "android.media.mediaextractor.mime";
        public static final String TRACKS = "android.media.mediaextractor.ntrk";

        private MetricsConstants() {}
    }
}
