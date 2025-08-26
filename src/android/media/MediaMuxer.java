package android.media;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Map;

/* loaded from: classes2.dex */
public final class MediaMuxer {
    private static final int MUXER_STATE_INITIALIZED = 0;
    private static final int MUXER_STATE_STARTED = 1;
    private static final int MUXER_STATE_STOPPED = 2;
    private static final int MUXER_STATE_UNINITIALIZED = -1;
    private long mNativeObject;
    private int mState = -1;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private int mLastTrackIndex = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Format {
    }

    private static native int nativeAddTrack(long j, String[] strArr, Object[] objArr);

    private static native void nativeRelease(long j);

    private static native void nativeSetLocation(long j, int i, int i2);

    private static native void nativeSetOrientationHint(long j, int i);

    private static native long nativeSetup(FileDescriptor fileDescriptor, int i) throws IOException, IllegalArgumentException;

    private static native void nativeStart(long j);

    private static native void nativeStop(long j);

    private static native void nativeWriteSampleData(long j, int i, ByteBuffer byteBuffer, int i2, int i3, long j2, int i4);

    static {
        System.loadLibrary("media_jni");
    }

    public static final class OutputFormat {
        public static final int MUXER_OUTPUT_3GPP = 2;
        public static final int MUXER_OUTPUT_FIRST = 0;
        public static final int MUXER_OUTPUT_HEIF = 3;
        public static final int MUXER_OUTPUT_LAST = 4;
        public static final int MUXER_OUTPUT_MPEG_4 = 0;
        public static final int MUXER_OUTPUT_OGG = 4;
        public static final int MUXER_OUTPUT_WEBM = 1;

        private OutputFormat() {
        }
    }

    private String convertMuxerStateCodeToString(int i) {
        if (i == -1) {
            return "UNINITIALIZED";
        }
        if (i == 0) {
            return "INITIALIZED";
        }
        if (i == 1) {
            return "STARTED";
        }
        if (i == 2) {
            return "STOPPED";
        }
        return "UNKNOWN";
    }

    public MediaMuxer(String str, int i) throws Throwable {
        if (str == null) {
            throw new IllegalArgumentException("path must not be null");
        }
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(str, "rws");
            try {
                randomAccessFile2.setLength(0L);
                setUpMediaMuxer(randomAccessFile2.getFD(), i);
                randomAccessFile2.close();
            } catch (Throwable th) {
                th = th;
                randomAccessFile = randomAccessFile2;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public MediaMuxer(FileDescriptor fileDescriptor, int i) throws IOException {
        setUpMediaMuxer(fileDescriptor, i);
    }

    private void setUpMediaMuxer(FileDescriptor fileDescriptor, int i) throws IOException {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("format: " + i + " is invalid");
        }
        this.mNativeObject = nativeSetup(fileDescriptor, i);
        this.mState = 0;
        this.mCloseGuard.open("release");
    }

    public void setOrientationHint(int i) {
        if (i != 0 && i != 90 && i != 180 && i != 270) {
            throw new IllegalArgumentException("Unsupported angle: " + i);
        }
        if (this.mState == 0) {
            nativeSetOrientationHint(this.mNativeObject, i);
            return;
        }
        throw new IllegalStateException("Can't set rotation degrees due to wrong state(" + convertMuxerStateCodeToString(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
    }

    public void setLocation(float f, float f2) {
        int iRound = Math.round(f * 10000.0f);
        int iRound2 = Math.round(10000.0f * f2);
        if (iRound > 900000 || iRound < -900000) {
            throw new IllegalArgumentException("Latitude: " + f + " out of range.");
        }
        if (iRound2 > 1800000 || iRound2 < -1800000) {
            throw new IllegalArgumentException("Longitude: " + f2 + " out of range");
        }
        if (this.mState == 0) {
            long j = this.mNativeObject;
            if (j != 0) {
                nativeSetLocation(j, iRound, iRound2);
                return;
            }
        }
        throw new IllegalStateException("Can't set location due to wrong state(" + convertMuxerStateCodeToString(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
    }

    public void start() {
        long j = this.mNativeObject;
        if (j == 0) {
            throw new IllegalStateException("Muxer has been released!");
        }
        if (this.mState == 0) {
            nativeStart(j);
            this.mState = 1;
        } else {
            throw new IllegalStateException("Can't start due to wrong state(" + convertMuxerStateCodeToString(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void stop() {
        if (this.mState == 1) {
            try {
                try {
                    nativeStop(this.mNativeObject);
                    return;
                } catch (Exception e) {
                    throw e;
                }
            } finally {
                this.mState = 2;
            }
        }
        throw new IllegalStateException("Can't stop due to wrong state(" + convertMuxerStateCodeToString(this.mState) + NavigationBarInflaterView.KEY_CODE_END);
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            long j = this.mNativeObject;
            if (j != 0) {
                nativeRelease(j);
                this.mNativeObject = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public int addTrack(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            throw new IllegalArgumentException("format must not be null.");
        }
        if (this.mState != 0) {
            throw new IllegalStateException("Muxer is not initialized.");
        }
        if (this.mNativeObject == 0) {
            throw new IllegalStateException("Muxer has been released!");
        }
        Map<String, Object> map = mediaFormat.getMap();
        int size = map.size();
        if (size > 0) {
            String[] strArr = new String[size];
            Object[] objArr = new Object[size];
            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                objArr[i] = entry.getValue();
                i++;
            }
            int iNativeAddTrack = nativeAddTrack(this.mNativeObject, strArr, objArr);
            if (this.mLastTrackIndex >= iNativeAddTrack) {
                throw new IllegalArgumentException("Invalid format.");
            }
            this.mLastTrackIndex = iNativeAddTrack;
            return iNativeAddTrack;
        }
        throw new IllegalArgumentException("format must not be empty.");
    }

    public void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (i < 0 || i > this.mLastTrackIndex) {
            throw new IllegalArgumentException("trackIndex is invalid");
        }
        if (byteBuffer == null) {
            throw new IllegalArgumentException("byteBuffer must not be null");
        }
        if (bufferInfo == null) {
            throw new IllegalArgumentException("bufferInfo must not be null");
        }
        if (bufferInfo.size < 0 || bufferInfo.offset < 0 || bufferInfo.offset + bufferInfo.size > byteBuffer.capacity()) {
            throw new IllegalArgumentException("bufferInfo must specify a valid buffer offset and size");
        }
        long j = this.mNativeObject;
        if (j == 0) {
            throw new IllegalStateException("Muxer has been released!");
        }
        if (this.mState != 1) {
            throw new IllegalStateException("Can't write, muxer is not started");
        }
        nativeWriteSampleData(j, i, byteBuffer, bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
    }

    public void release() {
        if (this.mState == 1) {
            stop();
        }
        long j = this.mNativeObject;
        if (j != 0) {
            nativeRelease(j);
            this.mNativeObject = 0L;
            this.mCloseGuard.close();
        }
        this.mState = -1;
    }
}
