package android.media;

import android.annotation.SystemApi;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.codec.Flags;
import android.os.Bundle;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.NioUtils;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public final class MediaCodec {
    public static final int BUFFER_FLAG_CODEC_CONFIG = 2;
    public static final int BUFFER_FLAG_DECODE_ONLY = 32;
    public static final int BUFFER_FLAG_END_OF_STREAM = 4;
    public static final int BUFFER_FLAG_KEY_FRAME = 1;
    public static final int BUFFER_FLAG_MUXER_DATA = 16;
    public static final int BUFFER_FLAG_PARTIAL_FRAME = 8;
    public static final int BUFFER_FLAG_SYNC_FRAME = 1;
    private static final int BUFFER_MODE_BLOCK = 1;
    private static final int BUFFER_MODE_INVALID = -1;
    private static final int BUFFER_MODE_LEGACY = 0;
    private static final int CB_CRYPTO_ERROR = 6;
    private static final int CB_ERROR = 3;
    private static final int CB_INPUT_AVAILABLE = 1;
    private static final int CB_LARGE_FRAME_OUTPUT_AVAILABLE = 7;
    private static final int CB_OUTPUT_AVAILABLE = 2;
    private static final int CB_OUTPUT_FORMAT_CHANGE = 4;
    public static final int CONFIGURE_FLAG_DETACHED_SURFACE = 8;
    public static final int CONFIGURE_FLAG_ENCODE = 1;
    public static final int CONFIGURE_FLAG_USE_BLOCK_MODEL = 2;
    public static final int CONFIGURE_FLAG_USE_CRYPTO_ASYNC = 4;
    public static final int CRYPTO_MODE_AES_CBC = 2;
    public static final int CRYPTO_MODE_AES_CTR = 1;
    public static final int CRYPTO_MODE_UNENCRYPTED = 0;
    private static final String EOS_AND_DECODE_ONLY_ERROR_MESSAGE =
            "An input buffer cannot have both BUFFER_FLAG_END_OF_STREAM and BUFFER_FLAG_DECODE_ONLY"
                    + " flags";
    private static final int EVENT_CALLBACK = 1;
    private static final int EVENT_FIRST_TUNNEL_FRAME_READY = 4;
    private static final int EVENT_FRAME_RENDERED = 3;
    private static final int EVENT_SET_CALLBACK = 2;
    public static final int INFO_OUTPUT_BUFFERS_CHANGED = -3;
    public static final int INFO_OUTPUT_FORMAT_CHANGED = -2;
    public static final int INFO_TRY_AGAIN_LATER = -1;
    public static final String PARAMETER_KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
    public static final String PARAMETER_KEY_LOW_LATENCY = "low-latency";
    public static final String PARAMETER_KEY_OFFSET_TIME = "time-offset-us";
    public static final String PARAMETER_KEY_QP_OFFSET_MAP = "qp-offset-map";
    public static final String PARAMETER_KEY_QP_OFFSET_RECTS = "qp-offset-rects";
    public static final String PARAMETER_KEY_REQUEST_SYNC_FRAME = "request-sync";
    public static final String PARAMETER_KEY_SUSPEND = "drop-input-frames";
    public static final String PARAMETER_KEY_SUSPEND_TIME = "drop-start-time-us";
    public static final String PARAMETER_KEY_TUNNEL_PEEK = "tunnel-peek";
    public static final String PARAMETER_KEY_VIDEO_BITRATE = "video-bitrate";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private final Object mBufferLock;
    private int mBufferMode;
    private ByteBuffer[] mCachedInputBuffers;
    private ByteBuffer[] mCachedOutputBuffers;
    private Callback mCallback;
    private EventHandler mCallbackHandler;
    private MediaCodecInfo mCodecInfo;
    private final Object mCodecInfoLock;
    private MediaCrypto mCrypto;
    private final BufferMap mDequeuedInputBuffers;
    private final BufferMap mDequeuedOutputBuffers;
    private final Map<Integer, BufferInfo> mDequeuedOutputInfos;
    private EventHandler mEventHandler;
    private boolean mHasSurface;
    private final Object mListenerLock;
    private String mNameAtCreation;
    private long mNativeContext;
    private final Lock mNativeContextLock;
    private EventHandler mOnFirstTunnelFrameReadyHandler;
    private OnFirstTunnelFrameReadyListener mOnFirstTunnelFrameReadyListener;
    private EventHandler mOnFrameRenderedHandler;
    private OnFrameRenderedListener mOnFrameRenderedListener;
    private final ArrayList<OutputFrame> mOutputFrames;
    private final ArrayList<QueueRequest> mQueueRequests;
    private BitSet mValidInputIndices;
    private BitSet mValidOutputIndices;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferFlag {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigureFlag {}

    public interface OnFirstTunnelFrameReadyListener {
        void onFirstTunnelFrameReady(MediaCodec mediaCodec);
    }

    public interface OnFrameRenderedListener {
        void onFrameRendered(MediaCodec mediaCodec, long j, long j2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OutputBufferInfo {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoScalingMode {}

    private final native ByteBuffer getBuffer(boolean z, int i);

    private final native ByteBuffer[] getBuffers(boolean z);

    private final native Map<String, Object> getFormatNative(boolean z);

    private final native Image getImage(boolean z, int i);

    private final native Map<String, Object> getOutputFormatNative(int i);

    private final native MediaCodecInfo getOwnCodecInfo();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_closeMediaImage(long j);

    private final native void native_configure(
            String[] strArr,
            Object[] objArr,
            Surface surface,
            MediaCrypto mediaCrypto,
            IHwBinder iHwBinder,
            int i);

    private static final native PersistentSurface native_createPersistentInputSurface();

    private final native int native_dequeueInputBuffer(long j);

    private final native int native_dequeueOutputBuffer(BufferInfo bufferInfo, long j);

    private native void native_detachOutputSurface();

    private native void native_enableOnFirstTunnelFrameReadyListener(boolean z);

    private native void native_enableOnFrameRenderedListener(boolean z);

    private final native void native_finalize();

    private final native void native_flush();

    private native PersistableBundle native_getMetrics();

    private native void native_getOutputFrame(OutputFrame outputFrame, int i);

    private native ParameterDescriptor native_getParameterDescriptor(String str);

    private native List<String> native_getSupportedVendorParameters();

    private static final native void native_init();

    private static native Image native_mapHardwareBuffer(HardwareBuffer hardwareBuffer);

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_queueHardwareBuffer(
            int i,
            HardwareBuffer hardwareBuffer,
            long j,
            int i2,
            ArrayList<String> arrayList,
            ArrayList<Object> arrayList2);

    private final native void native_queueInputBuffer(int i, int i2, int i3, long j, int i4)
            throws CryptoException;

    private final native void native_queueInputBuffers(int i, Object[] objArr)
            throws CryptoException, CodecException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_queueLinearBlock(
            int i,
            LinearBlock linearBlock,
            Object[] objArr,
            Object[] objArr2,
            ArrayList<String> arrayList,
            ArrayList<Object> arrayList2);

    private final native void native_queueSecureInputBuffer(
            int i, int i2, CryptoInfo cryptoInfo, long j, int i3) throws CryptoException;

    private final native void native_queueSecureInputBuffers(
            int i, Object[] objArr, Object[] objArr2) throws CryptoException, CodecException;

    private final native void native_release();

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void native_releasePersistentInputSurface(Surface surface);

    private final native void native_reset();

    private native void native_setAudioPresentation(int i, int i2);

    private final native void native_setCallback(Callback callback);

    private final native void native_setInputSurface(Surface surface);

    private native void native_setSurface(Surface surface);

    private final native void native_setup(String str, boolean z, boolean z2, int i, int i2);

    private final native void native_start();

    private final native void native_stop();

    private native void native_subscribeToVendorParameters(List<String> list);

    private native void native_unsubscribeFromVendorParameters(List<String> list);

    private final native void releaseOutputBuffer(int i, boolean z, boolean z2, long j);

    private final native void setParameters(String[] strArr, Object[] objArr);

    public final native Surface createInputSurface();

    public final native String getCanonicalName();

    public final native void setVideoScalingMode(int i);

    public final native void signalEndOfInputStream();

    public static final class BufferInfo {
        public int flags;
        public int offset;
        public long presentationTimeUs;
        public int size;

        public void set(int newOffset, int newSize, long newTimeUs, int newFlags) {
            this.offset = newOffset;
            this.size = newSize;
            this.presentationTimeUs = newTimeUs;
            this.flags = newFlags;
        }

        public BufferInfo dup() {
            BufferInfo copy = new BufferInfo();
            copy.set(this.offset, this.size, this.presentationTimeUs, this.flags);
            return copy;
        }
    }

    private class EventHandler extends Handler {
        private MediaCodec mCodec;

        public EventHandler(MediaCodec codec, Looper looper) {
            super(looper);
            this.mCodec = codec;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            OnFrameRenderedListener onFrameRenderedListener;
            OnFirstTunnelFrameReadyListener onFirstTunnelFrameReadyListener;
            switch (msg.what) {
                case 1:
                    handleCallback(msg);
                    return;
                case 2:
                    MediaCodec.this.mCallback = (Callback) msg.obj;
                    return;
                case 3:
                    Map<String, Object> map = (Map) msg.obj;
                    int i = 0;
                    while (true) {
                        Object mediaTimeUs = map.get(i + "-media-time-us");
                        Object systemNano = map.get(i + "-system-nano");
                        synchronized (MediaCodec.this.mListenerLock) {
                            onFrameRenderedListener = MediaCodec.this.mOnFrameRenderedListener;
                        }
                        if (mediaTimeUs != null
                                && systemNano != null
                                && onFrameRenderedListener != null) {
                            onFrameRenderedListener.onFrameRendered(
                                    this.mCodec,
                                    ((Long) mediaTimeUs).longValue(),
                                    ((Long) systemNano).longValue());
                            i++;
                        } else {
                            return;
                        }
                    }
                    break;
                case 4:
                    synchronized (MediaCodec.this.mListenerLock) {
                        onFirstTunnelFrameReadyListener =
                                MediaCodec.this.mOnFirstTunnelFrameReadyListener;
                    }
                    if (onFirstTunnelFrameReadyListener != null) {
                        onFirstTunnelFrameReadyListener.onFirstTunnelFrameReady(this.mCodec);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void handleCallback(Message msg) {
            if (MediaCodec.this.mCallback == null) {
                return;
            }
            switch (msg.arg1) {
                case 1:
                    int index = msg.arg2;
                    synchronized (MediaCodec.this.mBufferLock) {
                        switch (MediaCodec.this.mBufferMode) {
                            case 0:
                                MediaCodec.this.validateInputByteBufferLocked(
                                        MediaCodec.this.mCachedInputBuffers, index);
                                break;
                            case 1:
                                while (MediaCodec.this.mQueueRequests.size() <= index) {
                                    MediaCodec.this.mQueueRequests.add(null);
                                }
                                QueueRequest request =
                                        (QueueRequest) MediaCodec.this.mQueueRequests.get(index);
                                if (request == null) {
                                    request = new QueueRequest(this.mCodec, index);
                                    MediaCodec.this.mQueueRequests.set(index, request);
                                }
                                request.setAccessible(true);
                                break;
                            default:
                                throw new IllegalStateException(
                                        "Unrecognized buffer mode: " + MediaCodec.this.mBufferMode);
                        }
                    }
                    MediaCodec.this.mCallback.onInputBufferAvailable(this.mCodec, index);
                    return;
                case 2:
                    int index2 = msg.arg2;
                    BufferInfo info = (BufferInfo) msg.obj;
                    synchronized (MediaCodec.this.mBufferLock) {
                        switch (MediaCodec.this.mBufferMode) {
                            case 0:
                                MediaCodec.this.validateOutputByteBufferLocked(
                                        MediaCodec.this.mCachedOutputBuffers, index2, info);
                                break;
                            case 1:
                                while (MediaCodec.this.mOutputFrames.size() <= index2) {
                                    MediaCodec.this.mOutputFrames.add(null);
                                }
                                OutputFrame frame =
                                        (OutputFrame) MediaCodec.this.mOutputFrames.get(index2);
                                if (frame == null) {
                                    frame = new OutputFrame(index2);
                                    MediaCodec.this.mOutputFrames.set(index2, frame);
                                }
                                frame.setBufferInfo(info);
                                frame.setAccessible(true);
                                break;
                            default:
                                throw new IllegalStateException(
                                        "Unrecognized buffer mode: " + MediaCodec.this.mBufferMode);
                        }
                    }
                    MediaCodec.this.mCallback.onOutputBufferAvailable(this.mCodec, index2, info);
                    return;
                case 3:
                    MediaCodec.this.mCallback.onError(this.mCodec, (CodecException) msg.obj);
                    return;
                case 4:
                    MediaCodec.this.mCallback.onOutputFormatChanged(
                            this.mCodec, new MediaFormat((Map<String, Object>) msg.obj));
                    return;
                case 5:
                default:
                    return;
                case 6:
                    MediaCodec.this.mCallback.onCryptoError(this.mCodec, (CryptoException) msg.obj);
                    return;
                case 7:
                    int index3 = msg.arg2;
                    ArrayDeque<BufferInfo> infos = (ArrayDeque) msg.obj;
                    synchronized (MediaCodec.this.mBufferLock) {
                        switch (MediaCodec.this.mBufferMode) {
                            case 0:
                                MediaCodec.this.validateOutputByteBuffersLocked(
                                        MediaCodec.this.mCachedOutputBuffers, index3, infos);
                                break;
                            case 1:
                                while (MediaCodec.this.mOutputFrames.size() <= index3) {
                                    MediaCodec.this.mOutputFrames.add(null);
                                }
                                OutputFrame frame2 =
                                        (OutputFrame) MediaCodec.this.mOutputFrames.get(index3);
                                if (frame2 == null) {
                                    frame2 = new OutputFrame(index3);
                                    MediaCodec.this.mOutputFrames.set(index3, frame2);
                                }
                                frame2.setBufferInfos(infos);
                                frame2.setAccessible(true);
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Unrecognized buffer mode: for large frame output");
                        }
                    }
                    MediaCodec.this.mCallback.onOutputBuffersAvailable(this.mCodec, index3, infos);
                    return;
            }
        }
    }

    static boolean GetFlag(Supplier<Boolean> flagValueSupplier) {
        return GetFlag(flagValueSupplier, false);
    }

    static boolean GetFlag(Supplier<Boolean> flagValueSupplier, boolean defaultValue) {
        try {
            return flagValueSupplier.get().booleanValue();
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }

    public static MediaCodec createDecoderByType(String type) throws IOException {
        return new MediaCodec(type, true, false);
    }

    public static MediaCodec createEncoderByType(String type) throws IOException {
        return new MediaCodec(type, true, true);
    }

    public static MediaCodec createByCodecName(String name) throws IOException {
        return new MediaCodec(name, false, false);
    }

    @SystemApi
    public static MediaCodec createByCodecNameForClient(String name, int clientPid, int clientUid)
            throws IOException {
        return new MediaCodec(name, false, false, clientPid, clientUid);
    }

    private MediaCodec(String name, boolean nameIsType, boolean encoder) {
        this(name, nameIsType, encoder, -1, -1);
    }

    private MediaCodec(String str, boolean z, boolean z2, int i, int i2) {
        this.mListenerLock = new Object();
        this.mCodecInfoLock = new Object();
        this.mHasSurface = false;
        this.mBufferMode = -1;
        this.mQueueRequests = new ArrayList<>();
        this.mValidInputIndices = new BitSet();
        this.mValidOutputIndices = new BitSet();
        this.mDequeuedInputBuffers = new BufferMap();
        this.mDequeuedOutputBuffers = new BufferMap();
        this.mDequeuedOutputInfos = new HashMap();
        this.mOutputFrames = new ArrayList<>();
        this.mNativeContext = 0L;
        this.mNativeContextLock = new ReentrantLock();
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new EventHandler(this, myLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mCallbackHandler = this.mEventHandler;
        this.mOnFirstTunnelFrameReadyHandler = this.mEventHandler;
        this.mOnFrameRenderedHandler = this.mEventHandler;
        this.mBufferLock = new Object();
        this.mNameAtCreation = z ? null : str;
        native_setup(str, z, z2, i, i2);
    }

    protected void finalize() {
        native_finalize();
        this.mCrypto = null;
    }

    public final void reset() {
        freeAllTrackedBuffers();
        native_reset();
        this.mCrypto = null;
    }

    public final void release() {
        freeAllTrackedBuffers();
        native_release();
        this.mCrypto = null;
    }

    public class IncompatibleWithBlockModelException extends RuntimeException {
        IncompatibleWithBlockModelException() {}

        IncompatibleWithBlockModelException(String message) {
            super(message);
        }

        IncompatibleWithBlockModelException(String message, Throwable cause) {
            super(message, cause);
        }

        IncompatibleWithBlockModelException(Throwable cause) {
            super(cause);
        }
    }

    public class InvalidBufferFlagsException extends RuntimeException {
        InvalidBufferFlagsException(String message) {
            super(message);
        }
    }

    public void configure(MediaFormat format, Surface surface, MediaCrypto crypto, int flags) {
        configure(format, surface, crypto, null, flags);
    }

    public void configure(
            MediaFormat format, Surface surface, int flags, MediaDescrambler descrambler) {
        configure(
                format, surface, null, descrambler != null ? descrambler.getBinder() : null, flags);
    }

    private void configure(
            MediaFormat format,
            Surface surface,
            MediaCrypto crypto,
            IHwBinder descramblerBinder,
            int flags) {
        boolean canDetach;
        String[] keys;
        Object[] values;
        if (crypto != null && descramblerBinder != null) {
            throw new IllegalArgumentException("Can't use crypto and descrambler together!");
        }
        boolean canDetach2 =
                GetFlag(
                        new Supplier() { // from class:
                            // android.media.MediaCodec$$ExternalSyntheticLambda6
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                Boolean valueOf;
                                valueOf = Boolean.valueOf(Flags.nullOutputSurfaceSupport());
                                return valueOf;
                            }
                        });
        if (GetFlag(
                new Supplier() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda7
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(Flags.nullOutputSurface());
                        return valueOf;
                    }
                })) {
            if (surface == null && (flags & 8) != 0 && !canDetach2) {
                throw new IllegalArgumentException("Codec does not support detached surface");
            }
            canDetach = canDetach2;
        } else {
            canDetach = false;
        }
        if (format == null) {
            keys = null;
            values = null;
        } else {
            Map<String, Object> formatMap = format.getMap();
            String[] keys2 = new String[formatMap.size()];
            Object[] values2 = new Object[formatMap.size()];
            int i = 0;
            for (Map.Entry<String, Object> entry : formatMap.entrySet()) {
                if (entry.getKey().equals(MediaFormat.KEY_AUDIO_SESSION_ID)) {
                    try {
                        int sessionId = ((Integer) entry.getValue()).intValue();
                        keys2[i] = MediaFormat.KEY_AUDIO_HW_SYNC;
                        values2[i] =
                                Integer.valueOf(AudioSystem.getAudioHwSyncForSession(sessionId));
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Wrong Session ID Parameter!");
                    }
                } else {
                    keys2[i] = entry.getKey();
                    values2[i] = entry.getValue();
                }
                i++;
            }
            values = values2;
            keys = keys2;
        }
        this.mHasSurface = surface != null;
        this.mCrypto = crypto;
        synchronized (this.mBufferLock) {
            if ((flags & 2) != 0) {
                this.mBufferMode = 1;
            } else {
                this.mBufferMode = 0;
            }
        }
        native_configure(keys, values, surface, crypto, descramblerBinder, flags);
        if (canDetach && surface == null && (flags & 8) != 0) {
            this.mHasSurface = true;
        }
    }

    public void setOutputSurface(Surface surface) {
        if (!this.mHasSurface) {
            throw new IllegalStateException("codec was not configured for an output surface");
        }
        native_setSurface(surface);
    }

    public void detachOutputSurface() {
        if (!this.mHasSurface) {
            throw new IllegalStateException("codec was not configured for an output surface");
        }
        if (GetFlag(
                new Supplier() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda8
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(Flags.nullOutputSurfaceSupport());
                        return valueOf;
                    }
                })) {
            native_detachOutputSurface();
            return;
        }
        throw new IllegalStateException("codec does not support detaching output surface");
    }

    public static Surface createPersistentInputSurface() {
        return native_createPersistentInputSurface();
    }

    static class PersistentSurface extends Surface {
        private long mPersistentObject;

        PersistentSurface() {}

        @Override // android.view.Surface
        public void release() {
            MediaCodec.native_releasePersistentInputSurface(this);
            super.release();
        }
    }

    public void setInputSurface(Surface surface) {
        if (!(surface instanceof PersistentSurface)) {
            throw new IllegalArgumentException("not a PersistentSurface");
        }
        native_setInputSurface(surface);
    }

    public final void start() {
        native_start();
    }

    public final void stop() {
        native_stop();
        freeAllTrackedBuffers();
        synchronized (this.mListenerLock) {
            if (this.mCallbackHandler != null) {
                this.mCallbackHandler.removeMessages(2);
                this.mCallbackHandler.removeMessages(1);
            }
            if (this.mOnFirstTunnelFrameReadyHandler != null) {
                this.mOnFirstTunnelFrameReadyHandler.removeMessages(4);
            }
            if (this.mOnFrameRenderedHandler != null) {
                this.mOnFrameRenderedHandler.removeMessages(3);
            }
        }
    }

    public final void flush() {
        synchronized (this.mBufferLock) {
            invalidateByteBuffersLocked(this.mCachedInputBuffers);
            invalidateByteBuffersLocked(this.mCachedOutputBuffers);
            this.mValidInputIndices.clear();
            this.mValidOutputIndices.clear();
            this.mDequeuedInputBuffers.clear();
            this.mDequeuedOutputBuffers.clear();
        }
        native_flush();
    }

    public static final class CodecException extends IllegalStateException {
        private static final int ACTION_RECOVERABLE = 2;
        private static final int ACTION_TRANSIENT = 1;
        public static final int ERROR_INSUFFICIENT_RESOURCE = 1100;
        public static final int ERROR_RECLAIMED = 1101;
        private final int mActionCode;
        private final String mDiagnosticInfo;
        private final int mErrorCode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ReasonCode {}

        CodecException(int errorCode, int actionCode, String detailMessage) {
            super(detailMessage);
            this.mErrorCode = errorCode;
            this.mActionCode = actionCode;
            String sign = errorCode < 0 ? "neg_" : "";
            this.mDiagnosticInfo = "android.media.MediaCodec.error_" + sign + Math.abs(errorCode);
        }

        public boolean isTransient() {
            return this.mActionCode == 1;
        }

        public boolean isRecoverable() {
            return this.mActionCode == 2;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public String getDiagnosticInfo() {
            return this.mDiagnosticInfo;
        }
    }

    public static final class CryptoException extends RuntimeException
            implements MediaDrmThrowable {
        public static final int ERROR_FRAME_TOO_LARGE = 8;
        public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
        public static final int ERROR_INSUFFICIENT_SECURITY = 7;
        public static final int ERROR_KEY_EXPIRED = 2;
        public static final int ERROR_LOST_STATE = 9;
        public static final int ERROR_NO_KEY = 1;
        public static final int ERROR_RESOURCE_BUSY = 3;
        public static final int ERROR_SESSION_NOT_OPENED = 5;
        public static final int ERROR_UNSUPPORTED_OPERATION = 6;
        private CryptoInfo mCryptoInfo;
        private final int mErrorCode;
        private final int mErrorContext;
        private final int mOemError;
        private final int mVendorError;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CryptoErrorCode {}

        public CryptoException(int errorCode, String detailMessage) {
            this(detailMessage, errorCode, 0, 0, 0, null);
        }

        public CryptoException(
                String message,
                int errorCode,
                int vendorError,
                int oemError,
                int errorContext,
                CryptoInfo cryptoInfo) {
            super(message);
            this.mErrorCode = errorCode;
            this.mVendorError = vendorError;
            this.mOemError = oemError;
            this.mErrorContext = errorContext;
            this.mCryptoInfo = cryptoInfo;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public CryptoInfo getCryptoInfo() {
            return this.mCryptoInfo;
        }

        @Override // android.media.MediaDrmThrowable
        public int getVendorError() {
            return this.mVendorError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getOemError() {
            return this.mOemError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getErrorContext() {
            return this.mErrorContext;
        }
    }

    public final void queueInputBuffer(
            int index, int offset, int size, long presentationTimeUs, int flags)
            throws CryptoException {
        if ((flags & 32) != 0 && (flags & 4) != 0) {
            throw new InvalidBufferFlagsException(EOS_AND_DECODE_ONLY_ERROR_MESSAGE);
        }
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "queueInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                                + " Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, index, true);
            this.mDequeuedInputBuffers.remove(index);
        }
        try {
            native_queueInputBuffer(index, offset, size, presentationTimeUs, flags);
        } catch (CryptoException | IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, index, true);
            throw e;
        }
    }

    public final void queueInputBuffers(int index, ArrayDeque<BufferInfo> bufferInfos) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "queueInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                                + " Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, index, true);
            this.mDequeuedInputBuffers.remove(index);
        }
        try {
            native_queueInputBuffers(index, bufferInfos.toArray());
        } catch (CryptoException | IllegalArgumentException | IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, index, true);
            throw e;
        }
    }

    public static final class CryptoInfo {
        private static final Pattern ZERO_PATTERN = new Pattern(0, 0);
        public byte[] iv;
        public byte[] key;
        private Pattern mPattern = ZERO_PATTERN;
        public int mode;
        public int[] numBytesOfClearData;
        public int[] numBytesOfEncryptedData;
        public int numSubSamples;

        public static final class Pattern {
            private int mEncryptBlocks;
            private int mSkipBlocks;

            public Pattern(int blocksToEncrypt, int blocksToSkip) {
                set(blocksToEncrypt, blocksToSkip);
            }

            public void set(int blocksToEncrypt, int blocksToSkip) {
                this.mEncryptBlocks = blocksToEncrypt;
                this.mSkipBlocks = blocksToSkip;
            }

            public int getSkipBlocks() {
                return this.mSkipBlocks;
            }

            public int getEncryptBlocks() {
                return this.mEncryptBlocks;
            }
        }

        public void set(
                int newNumSubSamples,
                int[] newNumBytesOfClearData,
                int[] newNumBytesOfEncryptedData,
                byte[] newKey,
                byte[] newIV,
                int newMode) {
            this.numSubSamples = newNumSubSamples;
            this.numBytesOfClearData = newNumBytesOfClearData;
            this.numBytesOfEncryptedData = newNumBytesOfEncryptedData;
            this.key = newKey;
            this.iv = newIV;
            this.mode = newMode;
            this.mPattern = ZERO_PATTERN;
        }

        public Pattern getPattern() {
            return new Pattern(this.mPattern.getEncryptBlocks(), this.mPattern.getSkipBlocks());
        }

        public void setPattern(Pattern newPattern) {
            if (newPattern == null) {
                newPattern = ZERO_PATTERN;
            }
            setPattern(newPattern.getEncryptBlocks(), newPattern.getSkipBlocks());
        }

        private void setPattern(int blocksToEncrypt, int blocksToSkip) {
            this.mPattern = new Pattern(blocksToEncrypt, blocksToSkip);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.numSubSamples + " subsamples, key [");
            for (int i = 0; i < this.key.length; i++) {
                builder.append("0123456789abcdef".charAt((this.key[i] & 240) >> 4));
                builder.append("0123456789abcdef".charAt(this.key[i] & 15));
            }
            builder.append("], iv [");
            for (int i2 = 0; i2 < this.iv.length; i2++) {
                builder.append("0123456789abcdef".charAt((this.iv[i2] & 240) >> 4));
                builder.append("0123456789abcdef".charAt(this.iv[i2] & 15));
            }
            builder.append("], clear ");
            builder.append(Arrays.toString(this.numBytesOfClearData));
            builder.append(", encrypted ");
            builder.append(Arrays.toString(this.numBytesOfEncryptedData));
            builder.append(", pattern (encrypt: ");
            builder.append(this.mPattern.mEncryptBlocks);
            builder.append(", skip: ");
            builder.append(this.mPattern.mSkipBlocks);
            builder.append(NavigationBarInflaterView.KEY_CODE_END);
            return builder.toString();
        }
    }

    public final void queueSecureInputBuffer(
            int index, int offset, CryptoInfo info, long presentationTimeUs, int flags)
            throws CryptoException {
        if ((flags & 32) != 0 && (flags & 4) != 0) {
            throw new InvalidBufferFlagsException(EOS_AND_DECODE_ONLY_ERROR_MESSAGE);
        }
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "queueSecureInputBuffer() is not compatible with"
                                + " CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to"
                                + " queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, index, true);
            this.mDequeuedInputBuffers.remove(index);
        }
        try {
            native_queueSecureInputBuffer(index, offset, info, presentationTimeUs, flags);
        } catch (CryptoException | IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, index, true);
            throw e;
        }
    }

    public final void queueSecureInputBuffers(
            int index, ArrayDeque<BufferInfo> bufferInfos, ArrayDeque<CryptoInfo> cryptoInfos) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "queueSecureInputBuffers() is not compatible with"
                                + " CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to"
                                + " queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, index, true);
            this.mDequeuedInputBuffers.remove(index);
        }
        try {
            native_queueSecureInputBuffers(index, bufferInfos.toArray(), cryptoInfos.toArray());
        } catch (CryptoException | IllegalArgumentException | IllegalStateException e) {
            revalidateByteBuffer(this.mCachedInputBuffers, index, true);
            throw e;
        }
    }

    public final int dequeueInputBuffer(long timeoutUs) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "dequeueInputBuffer() is not compatible with"
                                + " CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use MediaCodec.Callback"
                                + " objectes to get input buffer slots.");
            }
        }
        int res = native_dequeueInputBuffer(timeoutUs);
        if (res >= 0) {
            synchronized (this.mBufferLock) {
                validateInputByteBufferLocked(this.mCachedInputBuffers, res);
            }
        }
        return res;
    }

    public static final class LinearBlock {
        private static final BlockingQueue<LinearBlock> sPool = new LinkedBlockingQueue();
        private final Object mLock = new Object();
        private boolean mValid = false;
        private boolean mMappable = false;
        private ByteBuffer mMapped = null;
        private long mNativeContext = 0;
        private boolean mInternal = false;

        private static native boolean native_checkCompatible(String[] strArr);

        private native ByteBuffer native_map();

        private native void native_obtain(int i, String[] strArr);

        private native void native_recycle();

        private LinearBlock() {}

        public boolean isMappable() {
            boolean z;
            synchronized (this.mLock) {
                if (!this.mValid) {
                    throw new IllegalStateException("The linear block is invalid");
                }
                z = this.mMappable;
            }
            return z;
        }

        public ByteBuffer map() {
            ByteBuffer byteBuffer;
            synchronized (this.mLock) {
                if (!this.mValid) {
                    throw new IllegalStateException("The linear block is invalid");
                }
                if (!this.mMappable) {
                    throw new IllegalStateException("The linear block is not mappable");
                }
                if (this.mMapped == null) {
                    this.mMapped = native_map();
                }
                byteBuffer = this.mMapped;
            }
            return byteBuffer;
        }

        public void recycle() {
            synchronized (this.mLock) {
                if (!this.mValid) {
                    throw new IllegalStateException("The linear block is invalid");
                }
                if (this.mMapped != null) {
                    this.mMapped.setAccessible(false);
                    this.mMapped = null;
                }
                native_recycle();
                this.mValid = false;
                this.mNativeContext = 0L;
            }
            if (!this.mInternal) {
                sPool.offer(this);
            }
        }

        protected void finalize() {
            native_recycle();
        }

        public static boolean isCodecCopyFreeCompatible(String[] codecNames) {
            return native_checkCompatible(codecNames);
        }

        public static LinearBlock obtain(int capacity, String[] codecNames) {
            LinearBlock buffer = sPool.poll();
            if (buffer == null) {
                buffer = new LinearBlock();
            }
            synchronized (buffer.mLock) {
                buffer.native_obtain(capacity, codecNames);
            }
            return buffer;
        }

        private void setInternalStateLocked(long context, boolean isMappable) {
            this.mNativeContext = context;
            this.mMappable = isMappable;
            this.mValid = context != 0;
            this.mInternal = true;
        }
    }

    public static Image mapHardwareBuffer(HardwareBuffer hardwareBuffer) {
        return native_mapHardwareBuffer(hardwareBuffer);
    }

    public final class QueueRequest {
        private boolean mAccessible;
        private final ArrayDeque<BufferInfo> mBufferInfos;
        private final MediaCodec mCodec;
        private final ArrayDeque<CryptoInfo> mCryptoInfos;
        private int mFlags;
        private HardwareBuffer mHardwareBuffer;
        private final int mIndex;
        private LinearBlock mLinearBlock;
        private int mOffset;
        private long mPresentationTimeUs;
        private int mSize;
        private final ArrayList<String> mTuningKeys;
        private final ArrayList<Object> mTuningValues;

        private QueueRequest(MediaCodec codec, int index) {
            this.mLinearBlock = null;
            this.mOffset = 0;
            this.mSize = 0;
            this.mHardwareBuffer = null;
            this.mPresentationTimeUs = 0L;
            this.mFlags = 0;
            this.mBufferInfos = new ArrayDeque<>();
            this.mCryptoInfos = new ArrayDeque<>();
            this.mTuningKeys = new ArrayList<>();
            this.mTuningValues = new ArrayList<>();
            this.mAccessible = false;
            this.mCodec = codec;
            this.mIndex = index;
        }

        public QueueRequest setLinearBlock(LinearBlock block, int offset, int size) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = block;
            this.mOffset = offset;
            this.mSize = size;
            this.mCryptoInfos.clear();
            return this;
        }

        public QueueRequest setMultiFrameLinearBlock(
                LinearBlock block, ArrayDeque<BufferInfo> infos) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = block;
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(infos);
            this.mCryptoInfos.clear();
            return this;
        }

        public QueueRequest setEncryptedLinearBlock(
                LinearBlock block, int offset, int size, CryptoInfo cryptoInfo) {
            Objects.requireNonNull(cryptoInfo);
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = block;
            this.mOffset = offset;
            this.mSize = size;
            this.mCryptoInfos.clear();
            this.mCryptoInfos.add(cryptoInfo);
            return this;
        }

        public QueueRequest setMultiFrameEncryptedLinearBlock(
                LinearBlock block,
                ArrayDeque<BufferInfo> bufferInfos,
                ArrayDeque<CryptoInfo> cryptoInfos) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = block;
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(bufferInfos);
            this.mCryptoInfos.clear();
            this.mCryptoInfos.addAll(cryptoInfos);
            return this;
        }

        public QueueRequest setHardwareBuffer(HardwareBuffer buffer) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mHardwareBuffer = buffer;
            return this;
        }

        public QueueRequest setPresentationTimeUs(long presentationTimeUs) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mPresentationTimeUs = presentationTimeUs;
            return this;
        }

        public QueueRequest setFlags(int flags) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mFlags = flags;
            return this;
        }

        public QueueRequest setIntegerParameter(String key, int value) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(key);
            this.mTuningValues.add(Integer.valueOf(value));
            return this;
        }

        public QueueRequest setLongParameter(String key, long value) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(key);
            this.mTuningValues.add(Long.valueOf(value));
            return this;
        }

        public QueueRequest setFloatParameter(String key, float value) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(key);
            this.mTuningValues.add(Float.valueOf(value));
            return this;
        }

        public QueueRequest setByteBufferParameter(String key, ByteBuffer value) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(key);
            this.mTuningValues.add(value);
            return this;
        }

        public QueueRequest setStringParameter(String key, String value) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(key);
            this.mTuningValues.add(value);
            return this;
        }

        public void queue() {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock == null && this.mHardwareBuffer == null) {
                throw new IllegalStateException("No block is set");
            }
            setAccessible(false);
            if (this.mBufferInfos.isEmpty()) {
                BufferInfo info = new BufferInfo();
                info.size = this.mSize;
                info.offset = this.mOffset;
                info.presentationTimeUs = this.mPresentationTimeUs;
                info.flags = this.mFlags;
                this.mBufferInfos.add(info);
            }
            if (this.mLinearBlock != null) {
                this.mCodec.native_queueLinearBlock(
                        this.mIndex,
                        this.mLinearBlock,
                        this.mCryptoInfos.isEmpty() ? null : this.mCryptoInfos.toArray(),
                        this.mBufferInfos.toArray(),
                        this.mTuningKeys,
                        this.mTuningValues);
            } else if (this.mHardwareBuffer != null) {
                this.mCodec.native_queueHardwareBuffer(
                        this.mIndex,
                        this.mHardwareBuffer,
                        this.mPresentationTimeUs,
                        this.mFlags,
                        this.mTuningKeys,
                        this.mTuningValues);
            }
            clear();
        }

        QueueRequest clear() {
            this.mLinearBlock = null;
            this.mOffset = 0;
            this.mSize = 0;
            this.mHardwareBuffer = null;
            this.mPresentationTimeUs = 0L;
            this.mFlags = 0;
            this.mBufferInfos.clear();
            this.mCryptoInfos.clear();
            this.mTuningKeys.clear();
            this.mTuningValues.clear();
            return this;
        }

        boolean isAccessible() {
            return this.mAccessible;
        }

        QueueRequest setAccessible(boolean accessible) {
            this.mAccessible = accessible;
            return this;
        }
    }

    public QueueRequest getQueueRequest(int index) {
        QueueRequest clear;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode != 1) {
                throw new IllegalStateException("The codec is not configured for block model");
            }
            if (index < 0 || index >= this.mQueueRequests.size()) {
                throw new IndexOutOfBoundsException(
                        "Expected range of index: [0,"
                                + (this.mQueueRequests.size() - 1)
                                + "]; actual: "
                                + index);
            }
            QueueRequest request = this.mQueueRequests.get(index);
            if (request == null) {
                throw new IllegalArgumentException("Unavailable index: " + index);
            }
            if (!request.isAccessible()) {
                throw new IllegalArgumentException("The request is stale at index " + index);
            }
            clear = request.clear();
        }
        return clear;
    }

    public final int dequeueOutputBuffer(BufferInfo info, long timeoutUs) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "dequeueOutputBuffer() is not compatible with"
                                + " CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use MediaCodec.Callback"
                                + " objects to get output buffer slots.");
            }
        }
        int res = native_dequeueOutputBuffer(info, timeoutUs);
        synchronized (this.mBufferLock) {
            try {
                if (res == -3) {
                    cacheBuffersLocked(false);
                } else if (res >= 0) {
                    validateOutputByteBufferLocked(this.mCachedOutputBuffers, res, info);
                    if (this.mHasSurface || this.mCachedOutputBuffers == null) {
                        this.mDequeuedOutputInfos.put(Integer.valueOf(res), info.dup());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return res;
    }

    public final void releaseOutputBuffer(int index, boolean render) {
        releaseOutputBufferInternal(index, render, false, 0L);
    }

    public final void releaseOutputBuffer(int index, long renderTimestampNs) {
        releaseOutputBufferInternal(index, true, true, renderTimestampNs);
    }

    private void releaseOutputBufferInternal(
            int index, boolean render, boolean updatePts, long renderTimestampNs) {
        synchronized (this.mBufferLock) {
            switch (this.mBufferMode) {
                case 0:
                    invalidateByteBufferLocked(this.mCachedOutputBuffers, index, false);
                    this.mDequeuedOutputBuffers.remove(index);
                    if (this.mHasSurface || this.mCachedOutputBuffers == null) {
                        this.mDequeuedOutputInfos.remove(Integer.valueOf(index));
                        break;
                    }
                    break;
                case 1:
                    OutputFrame frame = this.mOutputFrames.get(index);
                    frame.setAccessible(false);
                    frame.clear();
                    break;
                default:
                    throw new IllegalStateException(
                            "Unrecognized buffer mode: " + this.mBufferMode);
            }
        }
        releaseOutputBuffer(index, render, updatePts, renderTimestampNs);
    }

    public final MediaFormat getOutputFormat() {
        return new MediaFormat(getFormatNative(false));
    }

    public final MediaFormat getInputFormat() {
        return new MediaFormat(getFormatNative(true));
    }

    public final MediaFormat getOutputFormat(int index) {
        return new MediaFormat(getOutputFormatNative(index));
    }

    private static class BufferMap {
        private final Map<Integer, CodecBuffer> mMap;

        private BufferMap() {
            this.mMap = new HashMap();
        }

        private static class CodecBuffer {
            private ByteBuffer mByteBuffer;
            private Image mImage;

            private CodecBuffer() {}

            public void free() {
                if (this.mByteBuffer != null) {
                    NioUtils.freeDirectBuffer(this.mByteBuffer);
                    this.mByteBuffer = null;
                }
                if (this.mImage != null) {
                    this.mImage.close();
                    this.mImage = null;
                }
            }

            public void setImage(Image image) {
                free();
                this.mImage = image;
            }

            public void setByteBuffer(ByteBuffer buffer) {
                free();
                this.mByteBuffer = buffer;
            }
        }

        public void remove(int index) {
            CodecBuffer buffer = this.mMap.get(Integer.valueOf(index));
            if (buffer != null) {
                buffer.free();
                this.mMap.remove(Integer.valueOf(index));
            }
        }

        public void put(int index, ByteBuffer newBuffer) {
            CodecBuffer buffer = this.mMap.get(Integer.valueOf(index));
            if (buffer == null) {
                buffer = new CodecBuffer();
                this.mMap.put(Integer.valueOf(index), buffer);
            }
            buffer.setByteBuffer(newBuffer);
        }

        public void put(int index, Image newImage) {
            CodecBuffer buffer = this.mMap.get(Integer.valueOf(index));
            if (buffer == null) {
                buffer = new CodecBuffer();
                this.mMap.put(Integer.valueOf(index), buffer);
            }
            buffer.setImage(newImage);
        }

        public void clear() {
            for (CodecBuffer buffer : this.mMap.values()) {
                buffer.free();
            }
            this.mMap.clear();
        }
    }

    private void invalidateByteBufferLocked(ByteBuffer[] buffers, int index, boolean input) {
        ByteBuffer buffer;
        if (buffers == null) {
            if (index >= 0) {
                BitSet indices = input ? this.mValidInputIndices : this.mValidOutputIndices;
                indices.clear(index);
                return;
            }
            return;
        }
        if (index >= 0 && index < buffers.length && (buffer = buffers[index]) != null) {
            buffer.setAccessible(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateInputByteBufferLocked(ByteBuffer[] buffers, int index) {
        ByteBuffer buffer;
        if (buffers == null) {
            if (index >= 0) {
                this.mValidInputIndices.set(index);
            }
        } else if (index >= 0 && index < buffers.length && (buffer = buffers[index]) != null) {
            buffer.setAccessible(true);
            buffer.clear();
        }
    }

    private void revalidateByteBuffer(ByteBuffer[] buffers, int index, boolean input) {
        ByteBuffer buffer;
        synchronized (this.mBufferLock) {
            if (buffers == null) {
                if (index >= 0) {
                    BitSet indices = input ? this.mValidInputIndices : this.mValidOutputIndices;
                    indices.set(index);
                }
            } else if (index >= 0 && index < buffers.length && (buffer = buffers[index]) != null) {
                buffer.setAccessible(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateOutputByteBuffersLocked(
            ByteBuffer[] buffers, int index, ArrayDeque<BufferInfo> infoDeque) {
        ByteBuffer buffer;
        Optional<BufferInfo> minInfo =
                infoDeque.stream()
                        .min(
                                new Comparator() { // from class:
                                    // android.media.MediaCodec$$ExternalSyntheticLambda0
                                    @Override // java.util.Comparator
                                    public final int compare(Object obj, Object obj2) {
                                        int compare;
                                        compare =
                                                Integer.compare(
                                                        ((MediaCodec.BufferInfo) obj).offset,
                                                        ((MediaCodec.BufferInfo) obj2).offset);
                                        return compare;
                                    }
                                });
        Optional<BufferInfo> maxInfo =
                infoDeque.stream()
                        .max(
                                new Comparator() { // from class:
                                    // android.media.MediaCodec$$ExternalSyntheticLambda1
                                    @Override // java.util.Comparator
                                    public final int compare(Object obj, Object obj2) {
                                        int compare;
                                        compare =
                                                Integer.compare(
                                                        ((MediaCodec.BufferInfo) obj).offset,
                                                        ((MediaCodec.BufferInfo) obj2).offset);
                                        return compare;
                                    }
                                });
        if (buffers == null) {
            if (index >= 0) {
                this.mValidOutputIndices.set(index);
            }
        } else if (index >= 0
                && index < buffers.length
                && (buffer = buffers[index]) != null
                && minInfo.isPresent()
                && maxInfo.isPresent()) {
            buffer.setAccessible(true);
            buffer.limit(maxInfo.get().offset + maxInfo.get().size);
            buffer.position(minInfo.get().offset);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateOutputByteBufferLocked(ByteBuffer[] buffers, int index, BufferInfo info) {
        ByteBuffer buffer;
        if (buffers == null) {
            if (index >= 0) {
                this.mValidOutputIndices.set(index);
            }
        } else if (index >= 0 && index < buffers.length && (buffer = buffers[index]) != null) {
            buffer.setAccessible(true);
            buffer.limit(info.offset + info.size).position(info.offset);
        }
    }

    private void invalidateByteBuffersLocked(ByteBuffer[] buffers) {
        if (buffers != null) {
            for (ByteBuffer buffer : buffers) {
                if (buffer != null) {
                    buffer.setAccessible(false);
                }
            }
        }
    }

    private void freeByteBufferLocked(ByteBuffer buffer) {
        if (buffer != null) {
            NioUtils.freeDirectBuffer(buffer);
        }
    }

    private void freeByteBuffersLocked(ByteBuffer[] buffers) {
        if (buffers != null) {
            for (ByteBuffer buffer : buffers) {
                freeByteBufferLocked(buffer);
            }
        }
    }

    private void freeAllTrackedBuffers() {
        synchronized (this.mBufferLock) {
            freeByteBuffersLocked(this.mCachedInputBuffers);
            freeByteBuffersLocked(this.mCachedOutputBuffers);
            this.mCachedInputBuffers = null;
            this.mCachedOutputBuffers = null;
            this.mValidInputIndices.clear();
            this.mValidOutputIndices.clear();
            this.mDequeuedInputBuffers.clear();
            this.mDequeuedOutputBuffers.clear();
            this.mQueueRequests.clear();
            this.mOutputFrames.clear();
        }
    }

    private void cacheBuffersLocked(boolean input) {
        BufferInfo info;
        ByteBuffer[] buffers = null;
        try {
            buffers = getBuffers(input);
            invalidateByteBuffersLocked(buffers);
        } catch (IllegalStateException e) {
        }
        if (buffers != null) {
            BitSet indices = input ? this.mValidInputIndices : this.mValidOutputIndices;
            for (int i = 0; i < buffers.length; i++) {
                ByteBuffer buffer = buffers[i];
                if (buffer != null && indices.get(i)) {
                    buffer.setAccessible(true);
                    if (!input
                            && (info = this.mDequeuedOutputInfos.get(Integer.valueOf(i))) != null) {
                        buffer.limit(info.offset + info.size).position(info.offset);
                    }
                }
            }
            indices.clear();
        }
        if (input) {
            this.mCachedInputBuffers = buffers;
        } else {
            this.mCachedOutputBuffers = buffers;
        }
    }

    public ByteBuffer[] getInputBuffers() {
        ByteBuffer[] byteBufferArr;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "getInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                            + " Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and"
                            + " attach to QueueRequest objects.");
            }
            if (this.mCachedInputBuffers == null) {
                cacheBuffersLocked(true);
            }
            if (this.mCachedInputBuffers == null) {
                throw new IllegalStateException();
            }
            byteBufferArr = this.mCachedInputBuffers;
        }
        return byteBufferArr;
    }

    public ByteBuffer[] getOutputBuffers() {
        ByteBuffer[] byteBufferArr;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "getOutputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                                + " Please use getOutputFrame to get output frames.");
            }
            if (this.mCachedOutputBuffers == null) {
                cacheBuffersLocked(false);
            }
            if (this.mCachedOutputBuffers == null) {
                throw new IllegalStateException();
            }
            byteBufferArr = this.mCachedOutputBuffers;
        }
        return byteBufferArr;
    }

    public ByteBuffer getInputBuffer(int index) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "getInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                            + " Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and"
                            + " attach to QueueRequest objects.");
            }
        }
        ByteBuffer newBuffer = getBuffer(true, index);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedInputBuffers, index, true);
            this.mDequeuedInputBuffers.put(index, newBuffer);
        }
        return newBuffer;
    }

    public Image getInputImage(int index) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "getInputImage() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                            + " Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and"
                            + " attach to QueueRequest objects.");
            }
        }
        Image newImage = getImage(true, index);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedInputBuffers, index, true);
            this.mDequeuedInputBuffers.put(index, newImage);
        }
        return newImage;
    }

    public ByteBuffer getOutputBuffer(int index) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "getOutputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                                + " Please use getOutputFrame() to get output frames.");
            }
        }
        ByteBuffer newBuffer = getBuffer(false, index);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedOutputBuffers, index, false);
            this.mDequeuedOutputBuffers.put(index, newBuffer);
        }
        return newBuffer;
    }

    public Image getOutputImage(int index) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException(
                        "getOutputImage() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL."
                                + " Please use getOutputFrame() to get output frames.");
            }
        }
        Image newImage = getImage(false, index);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedOutputBuffers, index, false);
            this.mDequeuedOutputBuffers.put(index, newImage);
        }
        return newImage;
    }

    public static final class OutputFrame {
        private final int mIndex;
        private LinearBlock mLinearBlock = null;
        private HardwareBuffer mHardwareBuffer = null;
        private long mPresentationTimeUs = 0;
        private int mFlags = 0;
        private MediaFormat mFormat = null;
        private final ArrayDeque<BufferInfo> mBufferInfos = new ArrayDeque<>();
        private final ArrayList<String> mChangedKeys = new ArrayList<>();
        private final Set<String> mKeySet = new HashSet();
        private boolean mAccessible = false;
        private boolean mLoaded = false;

        OutputFrame(int index) {
            this.mIndex = index;
        }

        public LinearBlock getLinearBlock() {
            if (this.mHardwareBuffer != null) {
                throw new IllegalStateException("This output frame is not linear");
            }
            return this.mLinearBlock;
        }

        public HardwareBuffer getHardwareBuffer() {
            if (this.mLinearBlock != null) {
                throw new IllegalStateException("This output frame is not graphic");
            }
            return this.mHardwareBuffer;
        }

        public long getPresentationTimeUs() {
            return this.mPresentationTimeUs;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public ArrayDeque<BufferInfo> getBufferInfos() {
            if (this.mBufferInfos.isEmpty()) {
                BufferInfo bufferInfo = new BufferInfo();
                bufferInfo.set(0, 0, this.mPresentationTimeUs, this.mFlags);
                this.mBufferInfos.add(bufferInfo);
            }
            return this.mBufferInfos;
        }

        public MediaFormat getFormat() {
            return this.mFormat;
        }

        public Set<String> getChangedKeys() {
            if (this.mKeySet.isEmpty() && !this.mChangedKeys.isEmpty()) {
                this.mKeySet.addAll(this.mChangedKeys);
            }
            return Collections.unmodifiableSet(this.mKeySet);
        }

        void clear() {
            this.mLinearBlock = null;
            this.mHardwareBuffer = null;
            this.mFormat = null;
            this.mBufferInfos.clear();
            this.mChangedKeys.clear();
            this.mKeySet.clear();
            this.mLoaded = false;
        }

        boolean isAccessible() {
            return this.mAccessible;
        }

        void setAccessible(boolean accessible) {
            this.mAccessible = accessible;
        }

        void setBufferInfo(BufferInfo info) {
            this.mBufferInfos.clear();
            this.mPresentationTimeUs = info.presentationTimeUs;
            this.mFlags = info.flags;
        }

        void setBufferInfos(ArrayDeque<BufferInfo> infos) {
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(infos);
        }

        boolean isLoaded() {
            return this.mLoaded;
        }

        void setLoaded(boolean loaded) {
            this.mLoaded = loaded;
        }
    }

    public OutputFrame getOutputFrame(int index) {
        OutputFrame frame;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode != 1) {
                throw new IllegalStateException("The codec is not configured for block model");
            }
            if (index < 0 || index >= this.mOutputFrames.size()) {
                throw new IndexOutOfBoundsException(
                        "Expected range of index: [0,"
                                + (this.mQueueRequests.size() - 1)
                                + "]; actual: "
                                + index);
            }
            frame = this.mOutputFrames.get(index);
            if (frame == null) {
                throw new IllegalArgumentException("Unavailable index: " + index);
            }
            if (!frame.isAccessible()) {
                throw new IllegalArgumentException("The output frame is stale at index " + index);
            }
            if (!frame.isLoaded()) {
                native_getOutputFrame(frame, index);
                frame.setLoaded(true);
            }
        }
        return frame;
    }

    public void setAudioPresentation(AudioPresentation presentation) {
        if (presentation == null) {
            throw new NullPointerException("audio presentation is null");
        }
        native_setAudioPresentation(presentation.getPresentationId(), presentation.getProgramId());
    }

    public final String getName() {
        String canonicalName = getCanonicalName();
        return this.mNameAtCreation != null ? this.mNameAtCreation : canonicalName;
    }

    public PersistableBundle getMetrics() {
        PersistableBundle bundle = native_getMetrics();
        return bundle;
    }

    public final void setParameters(Bundle params) {
        if (params == null) {
            return;
        }
        String[] keys = new String[params.size()];
        Object[] values = new Object[params.size()];
        int i = 0;
        for (String key : params.keySet()) {
            if (key.equals(MediaFormat.KEY_AUDIO_SESSION_ID)) {
                try {
                    int sessionId = ((Integer) params.get(key)).intValue();
                    keys[i] = MediaFormat.KEY_AUDIO_HW_SYNC;
                    values[i] = Integer.valueOf(AudioSystem.getAudioHwSyncForSession(sessionId));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Wrong Session ID Parameter!");
                }
            } else {
                keys[i] = key;
                Object value = params.get(key);
                if (value instanceof byte[]) {
                    values[i] = ByteBuffer.wrap((byte[]) value);
                } else {
                    values[i] = value;
                }
            }
            i++;
        }
        setParameters(keys, values);
    }

    private void logAndRun(String message, Runnable r) {
        Log.d("MediaCodec", "enter: " + message);
        r.run();
        Log.d("MediaCodec", "exit : " + message);
    }

    public void setCallback(Callback cb, Handler handler) {
        if (cb != null) {
            synchronized (this.mListenerLock) {
                EventHandler newHandler = getEventHandlerOn(handler, this.mCallbackHandler);
                if (newHandler != this.mCallbackHandler) {
                    if (Flags.setCallbackStall()) {
                        logAndRun(
                                "[new handler] removeMessages(SET_CALLBACK)",
                                new Runnable() { // from class:
                                    // android.media.MediaCodec$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MediaCodec.this.lambda$setCallback$5();
                                    }
                                });
                        logAndRun(
                                "[new handler] removeMessages(CALLBACK)",
                                new Runnable() { // from class:
                                    // android.media.MediaCodec$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MediaCodec.this.lambda$setCallback$6();
                                    }
                                });
                    } else {
                        this.mCallbackHandler.removeMessages(2);
                        this.mCallbackHandler.removeMessages(1);
                    }
                    this.mCallbackHandler = newHandler;
                }
            }
        } else if (this.mCallbackHandler != null) {
            if (Flags.setCallbackStall()) {
                logAndRun(
                        "[null handler] removeMessages(SET_CALLBACK)",
                        new Runnable() { // from class:
                            // android.media.MediaCodec$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaCodec.this.lambda$setCallback$7();
                            }
                        });
                logAndRun(
                        "[null handler] removeMessages(CALLBACK)",
                        new Runnable() { // from class:
                            // android.media.MediaCodec$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaCodec.this.lambda$setCallback$8();
                            }
                        });
            } else {
                this.mCallbackHandler.removeMessages(2);
                this.mCallbackHandler.removeMessages(1);
            }
        }
        if (this.mCallbackHandler != null) {
            Message msg = this.mCallbackHandler.obtainMessage(2, 0, 0, cb);
            this.mCallbackHandler.sendMessage(msg);
            native_setCallback(cb);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$5() {
        this.mCallbackHandler.removeMessages(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$6() {
        this.mCallbackHandler.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$7() {
        this.mCallbackHandler.removeMessages(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$8() {
        this.mCallbackHandler.removeMessages(1);
    }

    public void setCallback(Callback cb) {
        setCallback(cb, null);
    }

    public void setOnFirstTunnelFrameReadyListener(
            Handler handler, OnFirstTunnelFrameReadyListener listener) {
        synchronized (this.mListenerLock) {
            this.mOnFirstTunnelFrameReadyListener = listener;
            if (listener != null) {
                EventHandler newHandler =
                        getEventHandlerOn(handler, this.mOnFirstTunnelFrameReadyHandler);
                if (newHandler != this.mOnFirstTunnelFrameReadyHandler) {
                    this.mOnFirstTunnelFrameReadyHandler.removeMessages(4);
                }
                this.mOnFirstTunnelFrameReadyHandler = newHandler;
            } else if (this.mOnFirstTunnelFrameReadyHandler != null) {
                this.mOnFirstTunnelFrameReadyHandler.removeMessages(4);
            }
            native_enableOnFirstTunnelFrameReadyListener(listener != null);
        }
    }

    public void setOnFrameRenderedListener(OnFrameRenderedListener listener, Handler handler) {
        synchronized (this.mListenerLock) {
            this.mOnFrameRenderedListener = listener;
            if (listener != null) {
                EventHandler newHandler = getEventHandlerOn(handler, this.mOnFrameRenderedHandler);
                if (newHandler != this.mOnFrameRenderedHandler) {
                    this.mOnFrameRenderedHandler.removeMessages(3);
                }
                this.mOnFrameRenderedHandler = newHandler;
            } else if (this.mOnFrameRenderedHandler != null) {
                this.mOnFrameRenderedHandler.removeMessages(3);
            }
            native_enableOnFrameRenderedListener(listener != null);
        }
    }

    public List<String> getSupportedVendorParameters() {
        return native_getSupportedVendorParameters();
    }

    public static class ParameterDescriptor {
        private String mName;
        private int mType;

        private ParameterDescriptor() {}

        public String getName() {
            return this.mName;
        }

        public int getType() {
            return this.mType;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof ParameterDescriptor)) {
                return false;
            }
            ParameterDescriptor other = (ParameterDescriptor) o;
            return this.mName.equals(other.mName) && this.mType == other.mType;
        }

        public int hashCode() {
            return Arrays.asList(this.mName, Integer.valueOf(this.mType)).hashCode();
        }
    }

    public ParameterDescriptor getParameterDescriptor(String name) {
        return native_getParameterDescriptor(name);
    }

    public void subscribeToVendorParameters(List<String> names) {
        native_subscribeToVendorParameters(names);
    }

    public void unsubscribeFromVendorParameters(List<String> names) {
        native_unsubscribeFromVendorParameters(names);
    }

    private EventHandler getEventHandlerOn(Handler handler, EventHandler lastHandler) {
        if (handler == null) {
            return this.mEventHandler;
        }
        Looper looper = handler.getLooper();
        if (lastHandler.getLooper() == looper) {
            return lastHandler;
        }
        return new EventHandler(this, looper);
    }

    public abstract static class Callback {
        public abstract void onError(MediaCodec mediaCodec, CodecException codecException);

        public abstract void onInputBufferAvailable(MediaCodec mediaCodec, int i);

        public abstract void onOutputBufferAvailable(
                MediaCodec mediaCodec, int i, BufferInfo bufferInfo);

        public abstract void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat);

        public void onOutputBuffersAvailable(
                MediaCodec codec, int index, ArrayDeque<BufferInfo> infos) {
            throw new IllegalStateException(
                    "Client must override onOutputBuffersAvailable when codec is configured to"
                            + " operate with multiple access units");
        }

        public void onCryptoError(MediaCodec codec, CryptoException e) {
            throw new IllegalStateException(
                    "Client must override onCryptoError when the codec is configured with"
                            + " CONFIGURE_FLAG_USE_CRYPTO_ASYNC.",
                    e);
        }
    }

    private void postEventFromNative(int what, int arg1, int arg2, Object obj) {
        synchronized (this.mListenerLock) {
            EventHandler handler = this.mEventHandler;
            if (what == 1) {
                handler = this.mCallbackHandler;
            } else if (what == 4) {
                handler = this.mOnFirstTunnelFrameReadyHandler;
            } else if (what == 3) {
                handler = this.mOnFrameRenderedHandler;
            }
            if (handler != null) {
                Message msg = handler.obtainMessage(what, arg1, arg2, obj);
                handler.sendMessage(msg);
            }
        }
    }

    public MediaCodecInfo getCodecInfo() {
        MediaCodecInfo mediaCodecInfo;
        String name = getName();
        synchronized (this.mCodecInfoLock) {
            if (this.mCodecInfo == null) {
                this.mCodecInfo = getOwnCodecInfo();
                if (this.mCodecInfo == null) {
                    this.mCodecInfo = MediaCodecList.getInfoFor(name);
                }
            }
            mediaCodecInfo = this.mCodecInfo;
        }
        return mediaCodecInfo;
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    private final long lockAndGetContext() {
        this.mNativeContextLock.lock();
        return this.mNativeContext;
    }

    private final void setAndUnlockContext(long context) {
        this.mNativeContext = context;
        this.mNativeContextLock.unlock();
    }

    public static class MediaImage extends Image {
        private static final int TYPE_YUV = 1;
        private final ByteBuffer mBuffer;
        private final long mBufferContext;
        private final int mFormat;
        private final int mHeight;
        private final ByteBuffer mInfo;
        private final boolean mIsReadOnly;
        private final Image.Plane[] mPlanes;
        private long mTimestamp;
        private final int mWidth;
        private final int mXOffset;
        private final int mYOffset;
        private final int mTransform = 0;
        private final int mScalingMode = 0;

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            return this.mFormat;
        }

        @Override // android.media.Image
        public int getHeight() {
            throwISEIfImageIsInvalid();
            return this.mHeight;
        }

        @Override // android.media.Image
        public int getWidth() {
            throwISEIfImageIsInvalid();
            return this.mWidth;
        }

        @Override // android.media.Image
        public int getTransform() {
            throwISEIfImageIsInvalid();
            return 0;
        }

        @Override // android.media.Image
        public int getScalingMode() {
            throwISEIfImageIsInvalid();
            return 0;
        }

        @Override // android.media.Image
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            return (Image.Plane[]) Arrays.copyOf(this.mPlanes, this.mPlanes.length);
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            if (this.mIsImageValid) {
                if (this.mBuffer != null) {
                    NioUtils.freeDirectBuffer(this.mBuffer);
                }
                if (this.mBufferContext != 0) {
                    MediaCodec.native_closeMediaImage(this.mBufferContext);
                }
                this.mIsImageValid = false;
            }
        }

        @Override // android.media.Image
        public void setCropRect(Rect cropRect) {
            if (this.mIsReadOnly) {
                throw new ReadOnlyBufferException();
            }
            super.setCropRect(cropRect);
        }

        public MediaImage(
                ByteBuffer buffer,
                ByteBuffer info,
                boolean readOnly,
                long timestamp,
                int xOffset,
                int yOffset,
                Rect cropRect) {
            int planeOffsetInc;
            int planeOffsetInc2;
            Rect cropRect2;
            char c;
            ByteBuffer byteBuffer = buffer;
            int i = yOffset;
            this.mTimestamp = timestamp;
            this.mIsImageValid = true;
            this.mIsReadOnly = buffer.isReadOnly();
            this.mBuffer = buffer.duplicate();
            this.mXOffset = xOffset;
            this.mYOffset = i;
            this.mInfo = info;
            this.mBufferContext = 0L;
            int cbPlaneOffset = -1;
            int crPlaneOffset = -1;
            if (info.remaining() == 104) {
                int type = info.getInt();
                if (type != 1) {
                    throw new UnsupportedOperationException("unsupported type: " + type);
                }
                int numPlanes = info.getInt();
                if (numPlanes != 3) {
                    throw new RuntimeException("unexpected number of planes: " + numPlanes);
                }
                this.mWidth = info.getInt();
                this.mHeight = info.getInt();
                if (this.mWidth < 1 || this.mHeight < 1) {
                    throw new UnsupportedOperationException(
                            "unsupported size: " + this.mWidth + "x" + this.mHeight);
                }
                int bitDepth = info.getInt();
                if (bitDepth != 8 && bitDepth != 10) {
                    throw new UnsupportedOperationException("unsupported bit depth: " + bitDepth);
                }
                int bitDepthAllocated = info.getInt();
                if (bitDepthAllocated == 8 || bitDepthAllocated == 16) {
                    if (bitDepth == 8 && bitDepthAllocated == 8) {
                        this.mFormat = 35;
                        planeOffsetInc = 1;
                        planeOffsetInc2 = 2;
                    } else if (bitDepth == 10 && bitDepthAllocated == 16) {
                        this.mFormat = 54;
                        planeOffsetInc = 2;
                        planeOffsetInc2 = 4;
                    } else {
                        throw new UnsupportedOperationException(
                                "couldn't infer ImageFormat bitDepth: "
                                        + bitDepth
                                        + " bitDepthAllocated: "
                                        + bitDepthAllocated);
                    }
                    this.mPlanes = new MediaPlane[numPlanes];
                    int ix = 0;
                    while (ix < numPlanes) {
                        int planeOffset = info.getInt();
                        int colInc = info.getInt();
                        int rowInc = info.getInt();
                        int type2 = type;
                        int horiz = info.getInt();
                        int numPlanes2 = numPlanes;
                        int vert = info.getInt();
                        if (horiz == vert) {
                            int bitDepthAllocated2 = bitDepthAllocated;
                            if (horiz == (ix == 0 ? 1 : 2)) {
                                if (colInc < 1 || rowInc < 1) {
                                    throw new UnsupportedOperationException(
                                            "unexpected strides: "
                                                    + colInc
                                                    + " pixel, "
                                                    + rowInc
                                                    + " row on plane "
                                                    + ix);
                                }
                                buffer.clear();
                                byteBuffer.position(
                                        this.mBuffer.position()
                                                + planeOffset
                                                + ((xOffset / horiz) * colInc)
                                                + ((i / vert) * rowInc));
                                byteBuffer.limit(
                                        buffer.position()
                                                + Utils.divUp(bitDepth, 8)
                                                + (((this.mHeight / vert) - 1) * rowInc)
                                                + (((this.mWidth / horiz) - 1) * colInc));
                                this.mPlanes[ix] = new MediaPlane(buffer.slice(), rowInc, colInc);
                                if ((this.mFormat == 35 || this.mFormat == 54) && ix == 1) {
                                    cbPlaneOffset = planeOffset;
                                    c = '#';
                                } else {
                                    int cbPlaneOffset2 = this.mFormat;
                                    c = '#';
                                    if ((cbPlaneOffset2 == 35 || this.mFormat == 54) && ix == 2) {
                                        crPlaneOffset = planeOffset;
                                    }
                                }
                                ix++;
                                byteBuffer = buffer;
                                type = type2;
                                numPlanes = numPlanes2;
                                bitDepthAllocated = bitDepthAllocated2;
                                i = yOffset;
                            }
                        }
                        throw new UnsupportedOperationException(
                                "unexpected subsampling: "
                                        + horiz
                                        + "x"
                                        + vert
                                        + " on plane "
                                        + ix);
                    }
                    if (this.mFormat == 54) {
                        if (crPlaneOffset != cbPlaneOffset + planeOffsetInc) {
                            throw new UnsupportedOperationException(
                                    "Invalid plane offsets cbPlaneOffset: "
                                            + cbPlaneOffset
                                            + " crPlaneOffset: "
                                            + crPlaneOffset);
                        }
                        if (this.mPlanes[1].getPixelStride() != planeOffsetInc2
                                || this.mPlanes[2].getPixelStride() != planeOffsetInc2) {
                            throw new UnsupportedOperationException("Invalid pixelStride");
                        }
                    }
                    if (cropRect != null) {
                        cropRect2 = cropRect;
                    } else {
                        cropRect2 = new Rect(0, 0, this.mWidth, this.mHeight);
                    }
                    cropRect2.offset(-xOffset, -yOffset);
                    super.setCropRect(cropRect2);
                    return;
                }
                throw new UnsupportedOperationException(
                        "unsupported allocated bit depth: " + bitDepthAllocated);
            }
            throw new UnsupportedOperationException("unsupported info length: " + info.remaining());
        }

        public MediaImage(
                ByteBuffer[] buffers,
                int[] rowStrides,
                int[] pixelStrides,
                int width,
                int height,
                int format,
                boolean readOnly,
                long timestamp,
                int xOffset,
                int yOffset,
                Rect cropRect,
                long context) {
            Rect cropRect2;
            ByteBuffer[] byteBufferArr = buffers;
            int[] iArr = rowStrides;
            if (byteBufferArr.length == iArr.length
                    && byteBufferArr.length == pixelStrides.length) {
                this.mWidth = width;
                this.mHeight = height;
                this.mFormat = format;
                this.mTimestamp = timestamp;
                this.mIsImageValid = true;
                this.mIsReadOnly = readOnly;
                this.mBuffer = null;
                this.mInfo = null;
                this.mPlanes = new MediaPlane[byteBufferArr.length];
                int i = 0;
                while (i < byteBufferArr.length) {
                    this.mPlanes[i] = new MediaPlane(byteBufferArr[i], iArr[i], pixelStrides[i]);
                    i++;
                    byteBufferArr = buffers;
                    iArr = rowStrides;
                }
                this.mXOffset = xOffset;
                this.mYOffset = yOffset;
                if (cropRect != null) {
                    cropRect2 = cropRect;
                } else {
                    cropRect2 = new Rect(0, 0, this.mWidth, this.mHeight);
                }
                cropRect2.offset(-xOffset, -yOffset);
                super.setCropRect(cropRect2);
                this.mBufferContext = context;
                return;
            }
            throw new IllegalArgumentException(
                    "buffers, rowStrides and pixelStrides should have the same length");
        }

        private class MediaPlane extends Image.Plane {
            private final int mColInc;
            private final ByteBuffer mData;
            private final int mRowInc;

            public MediaPlane(ByteBuffer buffer, int rowInc, int colInc) {
                this.mData = buffer;
                this.mRowInc = rowInc;
                this.mColInc = colInc;
            }

            @Override // android.media.Image.Plane
            public int getRowStride() {
                MediaImage.this.throwISEIfImageIsInvalid();
                return this.mRowInc;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                MediaImage.this.throwISEIfImageIsInvalid();
                return this.mColInc;
            }

            @Override // android.media.Image.Plane
            public ByteBuffer getBuffer() {
                MediaImage.this.throwISEIfImageIsInvalid();
                return this.mData;
            }
        }
    }

    public static final class MetricsConstants {
        public static final String CODEC = "android.media.mediacodec.codec";
        public static final String ENCODER = "android.media.mediacodec.encoder";
        public static final String HEIGHT = "android.media.mediacodec.height";
        public static final String MIME_TYPE = "android.media.mediacodec.mime";
        public static final String MODE = "android.media.mediacodec.mode";
        public static final String MODE_AUDIO = "audio";
        public static final String MODE_VIDEO = "video";
        public static final String ROTATION = "android.media.mediacodec.rotation";
        public static final String SECURE = "android.media.mediacodec.secure";
        public static final String WIDTH = "android.media.mediacodec.width";

        private MetricsConstants() {}
    }
}
