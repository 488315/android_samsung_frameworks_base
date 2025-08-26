package android.media;

import android.annotation.SystemApi;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.Image;
import android.media.MediaCodec;
import android.media.codec.Flags;
import android.media.quality.PictureProfile;
import android.media.quality.PictureProfileHandle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Trace;
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
import java.util.Iterator;
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
    private static final int CB_METRICS_FLUSHED = 8;
    private static final int CB_OUTPUT_AVAILABLE = 2;
    private static final int CB_OUTPUT_FORMAT_CHANGE = 4;
    private static final int CB_REQUIRED_RESOURCES_CHANGE = 9;
    public static final int CONFIGURE_FLAG_DETACHED_SURFACE = 8;
    public static final int CONFIGURE_FLAG_ENCODE = 1;
    public static final int CONFIGURE_FLAG_USE_BLOCK_MODEL = 2;
    public static final int CONFIGURE_FLAG_USE_CRYPTO_ASYNC = 4;
    public static final int CRYPTO_MODE_AES_CBC = 2;
    public static final int CRYPTO_MODE_AES_CTR = 1;
    public static final int CRYPTO_MODE_UNENCRYPTED = 0;
    private static final String EOS_AND_DECODE_ONLY_ERROR_MESSAGE = "An input buffer cannot have both BUFFER_FLAG_END_OF_STREAM and BUFFER_FLAG_DECODE_ONLY flags";
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
    private static final String PARAMETER_KEY_PICTURE_PROFILE_HANDLE = "picture-profile-handle";
    public static final String PARAMETER_KEY_QP_OFFSET_MAP = "qp-offset-map";
    public static final String PARAMETER_KEY_QP_OFFSET_RECTS = "qp-offset-rects";
    public static final String PARAMETER_KEY_REQUEST_SYNC_FRAME = "request-sync";
    public static final String PARAMETER_KEY_SUSPEND = "drop-input-frames";
    public static final String PARAMETER_KEY_SUSPEND_TIME = "drop-start-time-us";
    public static final String PARAMETER_KEY_TUNNEL_PEEK = "tunnel-peek";
    public static final String PARAMETER_KEY_VIDEO_BITRATE = "video-bitrate";
    private static final String TAG = "MediaCodec";
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
    public @interface BufferFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigureFlag {
    }

    public interface OnFirstTunnelFrameReadyListener {
        void onFirstTunnelFrameReady(MediaCodec mediaCodec);
    }

    public interface OnFrameRenderedListener {
        void onFrameRendered(MediaCodec mediaCodec, long j, long j2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OutputBufferInfo {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoScalingMode {
    }

    private final native ByteBuffer getBuffer(boolean z, int i);

    private final native ByteBuffer[] getBuffers(boolean z);

    private final native Map<String, Object> getFormatNative(boolean z);

    private final native Image getImage(boolean z, int i);

    private final native Map<String, Object> getOutputFormatNative(int i);

    private final native MediaCodecInfo getOwnCodecInfo();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_closeMediaImage(long j);

    private final native void native_configure(String[] strArr, Object[] objArr, Surface surface, MediaCrypto mediaCrypto, IHwBinder iHwBinder, int i);

    private static final native PersistentSurface native_createPersistentInputSurface();

    private final native int native_dequeueInputBuffer(long j);

    private final native int native_dequeueOutputBuffer(BufferInfo bufferInfo, long j);

    private native void native_detachOutputSurface();

    private native void native_enableOnFirstTunnelFrameReadyListener(boolean z);

    private native void native_enableOnFrameRenderedListener(boolean z);

    private final native void native_finalize();

    private final native void native_flush();

    private static native List<GlobalResourceInfo> native_getGloballyAvailableResources();

    private native PersistableBundle native_getMetrics();

    private native void native_getOutputFrame(OutputFrame outputFrame, int i);

    private native ParameterDescriptor native_getParameterDescriptor(String str);

    private native List<InstanceResourceInfo> native_getRequiredResources();

    private native List<String> native_getSupportedVendorParameters();

    private static final native void native_init();

    private static native Image native_mapHardwareBuffer(HardwareBuffer hardwareBuffer);

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_queueHardwareBuffer(int i, HardwareBuffer hardwareBuffer, long j, int i2, ArrayList<String> arrayList, ArrayList<Object> arrayList2);

    private final native void native_queueInputBuffer(int i, int i2, int i3, long j, int i4) throws CryptoException;

    private final native void native_queueInputBuffers(int i, Object[] objArr) throws CryptoException, CodecException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_queueLinearBlock(int i, LinearBlock linearBlock, Object[] objArr, Object[] objArr2, ArrayList<String> arrayList, ArrayList<Object> arrayList2);

    private final native void native_queueSecureInputBuffer(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) throws CryptoException;

    private final native void native_queueSecureInputBuffers(int i, Object[] objArr, Object[] objArr2) throws CryptoException, CodecException;

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

        public void set(int i, int i2, long j, int i3) {
            this.offset = i;
            this.size = i2;
            this.presentationTimeUs = j;
            this.flags = i3;
        }

        public BufferInfo dup() {
            BufferInfo bufferInfo = new BufferInfo();
            bufferInfo.set(this.offset, this.size, this.presentationTimeUs, this.flags);
            return bufferInfo;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EventHandler extends Handler {
        private MediaCodec mCodec;

        public EventHandler(MediaCodec mediaCodec, Looper looper) {
            super(looper);
            this.mCodec = mediaCodec;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            OnFrameRenderedListener onFrameRenderedListener;
            OnFirstTunnelFrameReadyListener onFirstTunnelFrameReadyListener;
            int i = message.what;
            if (i == 1) {
                handleCallback(message);
                return;
            }
            if (i == 2) {
                MediaCodec.this.mCallback = (Callback) message.obj;
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                synchronized (MediaCodec.this.mListenerLock) {
                    onFirstTunnelFrameReadyListener = MediaCodec.this.mOnFirstTunnelFrameReadyListener;
                }
                if (onFirstTunnelFrameReadyListener == null) {
                    return;
                }
                onFirstTunnelFrameReadyListener.onFirstTunnelFrameReady(this.mCodec);
                return;
            }
            Map map = (Map) message.obj;
            int i2 = 0;
            while (true) {
                Object obj = map.get(i2 + "-media-time-us");
                Object obj2 = map.get(i2 + "-system-nano");
                synchronized (MediaCodec.this.mListenerLock) {
                    onFrameRenderedListener = MediaCodec.this.mOnFrameRenderedListener;
                }
                if (obj == null || obj2 == null || onFrameRenderedListener == null) {
                    return;
                }
                onFrameRenderedListener.onFrameRendered(this.mCodec, ((Long) obj).longValue(), ((Long) obj2).longValue());
                i2++;
            }
        }

        private void handleCallback(Message message) {
            if (MediaCodec.this.mCallback == null) {
                return;
            }
            switch (message.arg1) {
                case 1:
                    int i = message.arg2;
                    synchronized (MediaCodec.this.mBufferLock) {
                        int i2 = MediaCodec.this.mBufferMode;
                        if (i2 == 0) {
                            MediaCodec mediaCodec = MediaCodec.this;
                            mediaCodec.validateInputByteBufferLocked(mediaCodec.mCachedInputBuffers, i);
                        } else if (i2 == 1) {
                            while (MediaCodec.this.mQueueRequests.size() <= i) {
                                MediaCodec.this.mQueueRequests.add(null);
                            }
                            QueueRequest queueRequest = (QueueRequest) MediaCodec.this.mQueueRequests.get(i);
                            if (queueRequest == null) {
                                queueRequest = new QueueRequest(this.mCodec, i);
                                MediaCodec.this.mQueueRequests.set(i, queueRequest);
                            }
                            queueRequest.setAccessible(true);
                        } else {
                            throw new IllegalStateException("Unrecognized buffer mode: " + MediaCodec.this.mBufferMode);
                        }
                    }
                    MediaCodec.this.mCallback.onInputBufferAvailable(this.mCodec, i);
                    return;
                case 2:
                    int i3 = message.arg2;
                    BufferInfo bufferInfo = (BufferInfo) message.obj;
                    synchronized (MediaCodec.this.mBufferLock) {
                        int i4 = MediaCodec.this.mBufferMode;
                        if (i4 == 0) {
                            MediaCodec mediaCodec2 = MediaCodec.this;
                            mediaCodec2.validateOutputByteBufferLocked(mediaCodec2.mCachedOutputBuffers, i3, bufferInfo);
                        } else if (i4 == 1) {
                            while (MediaCodec.this.mOutputFrames.size() <= i3) {
                                MediaCodec.this.mOutputFrames.add(null);
                            }
                            OutputFrame outputFrame = (OutputFrame) MediaCodec.this.mOutputFrames.get(i3);
                            if (outputFrame == null) {
                                outputFrame = new OutputFrame(i3);
                                MediaCodec.this.mOutputFrames.set(i3, outputFrame);
                            }
                            outputFrame.setBufferInfo(bufferInfo);
                            outputFrame.setAccessible(true);
                        } else {
                            throw new IllegalStateException("Unrecognized buffer mode: " + MediaCodec.this.mBufferMode);
                        }
                    }
                    MediaCodec.this.mCallback.onOutputBufferAvailable(this.mCodec, i3, bufferInfo);
                    return;
                case 3:
                    MediaCodec.this.mCallback.onError(this.mCodec, (CodecException) message.obj);
                    return;
                case 4:
                    MediaCodec.this.mCallback.onOutputFormatChanged(this.mCodec, new MediaFormat((Map<String, Object>) message.obj));
                    return;
                case 5:
                default:
                    return;
                case 6:
                    MediaCodec.this.mCallback.onCryptoError(this.mCodec, (CryptoException) message.obj);
                    return;
                case 7:
                    int i5 = message.arg2;
                    ArrayDeque<BufferInfo> arrayDeque = (ArrayDeque) message.obj;
                    synchronized (MediaCodec.this.mBufferLock) {
                        int i6 = MediaCodec.this.mBufferMode;
                        if (i6 == 0) {
                            MediaCodec mediaCodec3 = MediaCodec.this;
                            mediaCodec3.validateOutputByteBuffersLocked(mediaCodec3.mCachedOutputBuffers, i5, arrayDeque);
                        } else if (i6 == 1) {
                            while (MediaCodec.this.mOutputFrames.size() <= i5) {
                                MediaCodec.this.mOutputFrames.add(null);
                            }
                            OutputFrame outputFrame2 = (OutputFrame) MediaCodec.this.mOutputFrames.get(i5);
                            if (outputFrame2 == null) {
                                outputFrame2 = new OutputFrame(i5);
                                MediaCodec.this.mOutputFrames.set(i5, outputFrame2);
                            }
                            outputFrame2.setBufferInfos(arrayDeque);
                            outputFrame2.setAccessible(true);
                        } else {
                            throw new IllegalArgumentException("Unrecognized buffer mode: for large frame output");
                        }
                    }
                    MediaCodec.this.mCallback.onOutputBuffersAvailable(this.mCodec, i5, arrayDeque);
                    return;
                case 8:
                    if (MediaCodec.GetFlag(new Supplier() { // from class: android.media.MediaCodec$EventHandler$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return Boolean.valueOf(Flags.subsessionMetrics());
                        }
                    })) {
                        MediaCodec.this.mCallback.onMetricsFlushed(this.mCodec, (PersistableBundle) message.obj);
                        return;
                    }
                    return;
                case 9:
                    if (Flags.codecAvailability()) {
                        MediaCodec.this.mCallback.onRequiredResourcesChanged(this.mCodec);
                        return;
                    }
                    return;
            }
        }
    }

    static boolean GetFlag(Supplier<Boolean> supplier) {
        return GetFlag(supplier, false);
    }

    static boolean GetFlag(Supplier<Boolean> supplier, boolean z) {
        try {
            return supplier.get().booleanValue();
        } catch (RuntimeException unused) {
            return z;
        }
    }

    public static MediaCodec createDecoderByType(String str) throws IOException {
        return new MediaCodec(str, true, false);
    }

    public static MediaCodec createEncoderByType(String str) throws IOException {
        return new MediaCodec(str, true, true);
    }

    public static MediaCodec createByCodecName(String str) throws IOException {
        return new MediaCodec(str, false, false);
    }

    @SystemApi
    public static MediaCodec createByCodecNameForClient(String str, int i, int i2) throws IOException {
        return new MediaCodec(str, false, false, i, i2);
    }

    private MediaCodec(String str, boolean z, boolean z2) {
        this(str, z, z2, -1, -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
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
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new EventHandler(this, looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        EventHandler eventHandler = this.mEventHandler;
        this.mCallbackHandler = eventHandler;
        this.mOnFirstTunnelFrameReadyHandler = eventHandler;
        this.mOnFrameRenderedHandler = eventHandler;
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
        IncompatibleWithBlockModelException() {
        }

        IncompatibleWithBlockModelException(String str) {
            super(str);
        }

        IncompatibleWithBlockModelException(String str, Throwable th) {
            super(str, th);
        }

        IncompatibleWithBlockModelException(Throwable th) {
            super(th);
        }
    }

    public class InvalidBufferFlagsException extends RuntimeException {
        InvalidBufferFlagsException(String str) {
            super(str);
        }
    }

    public static final class GlobalResourceInfo {
        long mAvailable;
        long mCapacity;
        String mName;

        public String getName() {
            return this.mName;
        }

        public long getCapacity() {
            return this.mCapacity;
        }

        public long getAvailable() {
            return this.mAvailable;
        }
    }

    public static List<GlobalResourceInfo> getGloballyAvailableResources() {
        return native_getGloballyAvailableResources();
    }

    public void configure(MediaFormat mediaFormat, Surface surface, MediaCrypto mediaCrypto, int i) {
        configure(mediaFormat, surface, mediaCrypto, null, i);
    }

    public void configure(MediaFormat mediaFormat, Surface surface, int i, MediaDescrambler mediaDescrambler) {
        configure(mediaFormat, surface, null, mediaDescrambler != null ? mediaDescrambler.getBinder() : null, i);
    }

    private void configure(MediaFormat mediaFormat, Surface surface, MediaCrypto mediaCrypto, IHwBinder iHwBinder, int i) {
        String[] strArr;
        Object[] objArr;
        if (mediaCrypto != null && iHwBinder != null) {
            throw new IllegalArgumentException("Can't use crypto and descrambler together!");
        }
        boolean zGetFlag = GetFlag(new Supplier() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(Flags.nullOutputSurfaceSupport());
            }
        });
        if (!GetFlag(new Supplier() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(Flags.nullOutputSurface());
            }
        })) {
            zGetFlag = false;
        } else if (surface == null && (i & 8) != 0 && !zGetFlag) {
            throw new IllegalArgumentException("Codec does not support detached surface");
        }
        if (mediaFormat != null) {
            Map<String, Object> map = mediaFormat.getMap();
            String[] strArr2 = new String[map.size()];
            Object[] objArr2 = new Object[map.size()];
            int i2 = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals(MediaFormat.KEY_AUDIO_SESSION_ID)) {
                    try {
                        int iIntValue = ((Integer) entry.getValue()).intValue();
                        strArr2[i2] = MediaFormat.KEY_AUDIO_HW_SYNC;
                        objArr2[i2] = Integer.valueOf(AudioSystem.getAudioHwSyncForSession(iIntValue));
                    } catch (Exception unused) {
                        throw new IllegalArgumentException("Wrong Session ID Parameter!");
                    }
                } else if (com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.applyPictureProfiles() && com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.mediaQualityFw() && entry.getKey().equals(MediaFormat.KEY_PICTURE_PROFILE_INSTANCE)) {
                    try {
                        PictureProfile pictureProfile = (PictureProfile) entry.getValue();
                        if (pictureProfile == null) {
                            throw new IllegalArgumentException("Picture profile instance parameter is null!");
                        }
                        PictureProfileHandle handle = pictureProfile.getHandle();
                        if (handle != PictureProfileHandle.NONE) {
                            strArr2[i2] = PARAMETER_KEY_PICTURE_PROFILE_HANDLE;
                            objArr2[i2] = Long.valueOf(handle.getId());
                        }
                    } catch (ClassCastException unused2) {
                        throw new IllegalArgumentException("Cannot cast the instance parameter to PictureProfile!");
                    } catch (Exception e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                        throw new IllegalArgumentException("Unexpected exception when casting the instance parameter to PictureProfile!");
                    }
                } else if (com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.applyPictureProfiles() && com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.mediaQualityFw() && entry.getKey().equals(MediaFormat.KEY_PICTURE_PROFILE_ID)) {
                    try {
                        String str = (String) entry.getValue();
                        if (str == null) {
                            throw new IllegalArgumentException("KEY_PICTURE_PROFILE_ID parameter is null!");
                        }
                        if (!str.isEmpty()) {
                            strArr2[i2] = MediaFormat.KEY_PICTURE_PROFILE_ID;
                            objArr2[i2] = str;
                        }
                    } catch (ClassCastException unused3) {
                        throw new IllegalArgumentException("Cannot cast the KEY_PICTURE_PROFILE_ID parameter to String!");
                    } catch (Exception e2) {
                        Log.e(TAG, Log.getStackTraceString(e2));
                        throw new IllegalArgumentException("Unexpected exception when casting the KEY_PICTURE_PROFILE_ID parameter!");
                    }
                } else {
                    strArr2[i2] = entry.getKey();
                    objArr2[i2] = entry.getValue();
                }
                i2++;
            }
            strArr = strArr2;
            objArr = objArr2;
        } else {
            strArr = null;
            objArr = null;
        }
        this.mHasSurface = surface != null;
        this.mCrypto = mediaCrypto;
        synchronized (this.mBufferLock) {
            if ((i & 2) != 0) {
                this.mBufferMode = 1;
            } else {
                this.mBufferMode = 0;
            }
        }
        native_configure(strArr, objArr, surface, mediaCrypto, iHwBinder, i);
        if (zGetFlag && surface == null && (i & 8) != 0) {
            this.mHasSurface = true;
        }
    }

    public static final class InstanceResourceInfo {
        String mName;
        long mPerFrameCount;
        long mStaticCount;

        public String getName() {
            return this.mName;
        }

        public long getStaticCount() {
            return this.mStaticCount;
        }

        public long getPerFrameCount() {
            return this.mPerFrameCount;
        }
    }

    public List<InstanceResourceInfo> getRequiredResources() {
        return native_getRequiredResources();
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
        if (GetFlag(new Supplier() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(Flags.nullOutputSurfaceSupport());
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

        PersistentSurface() {
        }

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
            EventHandler eventHandler = this.mCallbackHandler;
            if (eventHandler != null) {
                eventHandler.removeMessages(2);
                this.mCallbackHandler.removeMessages(1);
            }
            EventHandler eventHandler2 = this.mOnFirstTunnelFrameReadyHandler;
            if (eventHandler2 != null) {
                eventHandler2.removeMessages(4);
            }
            EventHandler eventHandler3 = this.mOnFrameRenderedHandler;
            if (eventHandler3 != null) {
                eventHandler3.removeMessages(3);
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
        public @interface ReasonCode {
        }

        CodecException(int i, int i2, String str) {
            super(str);
            this.mErrorCode = i;
            this.mActionCode = i2;
            this.mDiagnosticInfo = "android.media.MediaCodec.error_" + (i < 0 ? "neg_" : "") + Math.abs(i);
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

    public static final class CryptoException extends RuntimeException implements MediaDrmThrowable {
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
        public @interface CryptoErrorCode {
        }

        public CryptoException(int i, String str) {
            this(str, i, 0, 0, 0, null);
        }

        public CryptoException(String str, int i, int i2, int i3, int i4, CryptoInfo cryptoInfo) {
            super(str);
            this.mErrorCode = i;
            this.mVendorError = i2;
            this.mOemError = i3;
            this.mErrorContext = i4;
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

    public final void queueInputBuffer(int i, int i2, int i3, long j, int i4) throws CryptoException {
        Trace.traceBegin(512L, "MediaCodec::queueInputBuffer#java");
        if ((i4 & 32) != 0 && (i4 & 4) != 0) {
            throw new InvalidBufferFlagsException(EOS_AND_DECODE_ONLY_ERROR_MESSAGE);
        }
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("queueInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            try {
                native_queueInputBuffer(i, i2, i3, j, i4);
                Trace.traceEnd(512L);
            } catch (CryptoException | IllegalStateException e) {
                revalidateByteBuffer(this.mCachedInputBuffers, i, true);
                throw e;
            }
        } catch (Throwable th) {
            Trace.traceEnd(512L);
            throw th;
        }
    }

    public final void queueInputBuffers(int i, ArrayDeque<BufferInfo> arrayDeque) {
        Trace.traceBegin(512L, "MediaCodec::queueInputBuffers#java");
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("queueInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            try {
                native_queueInputBuffers(i, arrayDeque.toArray());
            } catch (CryptoException | IllegalArgumentException | IllegalStateException e) {
                revalidateByteBuffer(this.mCachedInputBuffers, i, true);
                throw e;
            }
        } finally {
            Trace.traceEnd(512L);
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

            public Pattern(int i, int i2) {
                set(i, i2);
            }

            public void set(int i, int i2) {
                this.mEncryptBlocks = i;
                this.mSkipBlocks = i2;
            }

            public int getSkipBlocks() {
                return this.mSkipBlocks;
            }

            public int getEncryptBlocks() {
                return this.mEncryptBlocks;
            }
        }

        public void set(int i, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i2) {
            this.numSubSamples = i;
            this.numBytesOfClearData = iArr;
            this.numBytesOfEncryptedData = iArr2;
            this.key = bArr;
            this.iv = bArr2;
            this.mode = i2;
            this.mPattern = ZERO_PATTERN;
        }

        public Pattern getPattern() {
            return new Pattern(this.mPattern.getEncryptBlocks(), this.mPattern.getSkipBlocks());
        }

        public void setPattern(Pattern pattern) {
            if (pattern == null) {
                pattern = ZERO_PATTERN;
            }
            setPattern(pattern.getEncryptBlocks(), pattern.getSkipBlocks());
        }

        private void setPattern(int i, int i2) {
            this.mPattern = new Pattern(i, i2);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.numSubSamples + " subsamples, key [");
            int i = 0;
            int i2 = 0;
            while (true) {
                byte[] bArr = this.key;
                if (i2 >= bArr.length) {
                    break;
                }
                sb.append("0123456789abcdef".charAt((bArr[i2] & 240) >> 4));
                sb.append("0123456789abcdef".charAt(this.key[i2] & 15));
                i2++;
            }
            sb.append("], iv [");
            while (true) {
                byte[] bArr2 = this.iv;
                if (i < bArr2.length) {
                    sb.append("0123456789abcdef".charAt((bArr2[i] & 240) >> 4));
                    sb.append("0123456789abcdef".charAt(this.iv[i] & 15));
                    i++;
                } else {
                    sb.append("], clear ");
                    sb.append(Arrays.toString(this.numBytesOfClearData));
                    sb.append(", encrypted ");
                    sb.append(Arrays.toString(this.numBytesOfEncryptedData));
                    sb.append(", pattern (encrypt: ");
                    sb.append(this.mPattern.mEncryptBlocks);
                    sb.append(", skip: ");
                    sb.append(this.mPattern.mSkipBlocks);
                    sb.append(NavigationBarInflaterView.KEY_CODE_END);
                    return sb.toString();
                }
            }
        }
    }

    public final void queueSecureInputBuffer(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) throws CryptoException {
        Trace.traceBegin(512L, "MediaCodec::queueSecureInputBuffer#java");
        if ((i3 & 32) != 0 && (i3 & 4) != 0) {
            throw new InvalidBufferFlagsException(EOS_AND_DECODE_ONLY_ERROR_MESSAGE);
        }
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("queueSecureInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            try {
                native_queueSecureInputBuffer(i, i2, cryptoInfo, j, i3);
                Trace.traceEnd(512L);
            } catch (CryptoException | IllegalStateException e) {
                revalidateByteBuffer(this.mCachedInputBuffers, i, true);
                throw e;
            }
        } catch (Throwable th) {
            Trace.traceEnd(512L);
            throw th;
        }
    }

    public final void queueSecureInputBuffers(int i, ArrayDeque<BufferInfo> arrayDeque, ArrayDeque<CryptoInfo> arrayDeque2) {
        Trace.traceBegin(512L, "MediaCodec::queueSecureInputBuffers#java");
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("queueSecureInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getQueueRequest() to queue buffers");
            }
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.remove(i);
        }
        try {
            try {
                native_queueSecureInputBuffers(i, arrayDeque.toArray(), arrayDeque2.toArray());
            } catch (CryptoException | IllegalArgumentException | IllegalStateException e) {
                revalidateByteBuffer(this.mCachedInputBuffers, i, true);
                throw e;
            }
        } finally {
            Trace.traceEnd(512L);
        }
    }

    public final int dequeueInputBuffer(long j) {
        Trace.traceBegin(512L, "MediaCodec::dequeueInputBuffer#java");
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("dequeueInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use MediaCodec.Callback objectes to get input buffer slots.");
            }
        }
        int iNative_dequeueInputBuffer = native_dequeueInputBuffer(j);
        if (iNative_dequeueInputBuffer >= 0) {
            synchronized (this.mBufferLock) {
                validateInputByteBufferLocked(this.mCachedInputBuffers, iNative_dequeueInputBuffer);
            }
        }
        Trace.traceEnd(512L);
        return iNative_dequeueInputBuffer;
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

        private LinearBlock() {
        }

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
                ByteBuffer byteBuffer = this.mMapped;
                if (byteBuffer != null) {
                    byteBuffer.setAccessible(false);
                    this.mMapped = null;
                }
                native_recycle();
                this.mValid = false;
                this.mNativeContext = 0L;
            }
            if (this.mInternal) {
                return;
            }
            sPool.offer(this);
        }

        protected void finalize() {
            native_recycle();
        }

        public static boolean isCodecCopyFreeCompatible(String[] strArr) {
            return native_checkCompatible(strArr);
        }

        public static LinearBlock obtain(int i, String[] strArr) {
            LinearBlock linearBlockPoll = sPool.poll();
            if (linearBlockPoll == null) {
                linearBlockPoll = new LinearBlock();
            }
            synchronized (linearBlockPoll.mLock) {
                linearBlockPoll.native_obtain(i, strArr);
            }
            return linearBlockPoll;
        }

        private void setInternalStateLocked(long j, boolean z) {
            this.mNativeContext = j;
            this.mMappable = z;
            this.mValid = j != 0;
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

        private QueueRequest(MediaCodec mediaCodec, MediaCodec mediaCodec2, int i) {
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
            this.mCodec = mediaCodec2;
            this.mIndex = i;
        }

        public QueueRequest setLinearBlock(LinearBlock linearBlock, int i, int i2) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mOffset = i;
            this.mSize = i2;
            this.mCryptoInfos.clear();
            return this;
        }

        public QueueRequest setMultiFrameLinearBlock(LinearBlock linearBlock, ArrayDeque<BufferInfo> arrayDeque) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(arrayDeque);
            this.mCryptoInfos.clear();
            return this;
        }

        public QueueRequest setEncryptedLinearBlock(LinearBlock linearBlock, int i, int i2, CryptoInfo cryptoInfo) {
            Objects.requireNonNull(cryptoInfo);
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mOffset = i;
            this.mSize = i2;
            this.mCryptoInfos.clear();
            this.mCryptoInfos.add(cryptoInfo);
            return this;
        }

        public QueueRequest setMultiFrameEncryptedLinearBlock(LinearBlock linearBlock, ArrayDeque<BufferInfo> arrayDeque, ArrayDeque<CryptoInfo> arrayDeque2) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mLinearBlock = linearBlock;
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(arrayDeque);
            this.mCryptoInfos.clear();
            this.mCryptoInfos.addAll(arrayDeque2);
            return this;
        }

        public QueueRequest setHardwareBuffer(HardwareBuffer hardwareBuffer) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock != null || this.mHardwareBuffer != null) {
                throw new IllegalStateException("Cannot set block twice");
            }
            this.mHardwareBuffer = hardwareBuffer;
            return this;
        }

        public QueueRequest setPresentationTimeUs(long j) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mPresentationTimeUs = j;
            return this;
        }

        public QueueRequest setFlags(int i) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mFlags = i;
            return this;
        }

        public QueueRequest setIntegerParameter(String str, int i) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(Integer.valueOf(i));
            return this;
        }

        public QueueRequest setLongParameter(String str, long j) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(Long.valueOf(j));
            return this;
        }

        public QueueRequest setFloatParameter(String str, float f) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(Float.valueOf(f));
            return this;
        }

        public QueueRequest setByteBufferParameter(String str, ByteBuffer byteBuffer) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(byteBuffer);
            return this;
        }

        public QueueRequest setStringParameter(String str, String str2) {
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            this.mTuningKeys.add(str);
            this.mTuningValues.add(str2);
            return this;
        }

        public void queue() {
            Trace.traceBegin(512L, "MediaCodec::queueRequest-queue#java");
            if (!isAccessible()) {
                throw new IllegalStateException("The request is stale");
            }
            if (this.mLinearBlock == null && this.mHardwareBuffer == null) {
                throw new IllegalStateException("No block is set");
            }
            setAccessible(false);
            if (this.mBufferInfos.isEmpty()) {
                BufferInfo bufferInfo = new BufferInfo();
                bufferInfo.size = this.mSize;
                bufferInfo.offset = this.mOffset;
                bufferInfo.presentationTimeUs = this.mPresentationTimeUs;
                bufferInfo.flags = this.mFlags;
                this.mBufferInfos.add(bufferInfo);
            }
            LinearBlock linearBlock = this.mLinearBlock;
            if (linearBlock != null) {
                this.mCodec.native_queueLinearBlock(this.mIndex, linearBlock, this.mCryptoInfos.isEmpty() ? null : this.mCryptoInfos.toArray(), this.mBufferInfos.toArray(), this.mTuningKeys, this.mTuningValues);
            } else {
                HardwareBuffer hardwareBuffer = this.mHardwareBuffer;
                if (hardwareBuffer != null) {
                    this.mCodec.native_queueHardwareBuffer(this.mIndex, hardwareBuffer, this.mPresentationTimeUs, this.mFlags, this.mTuningKeys, this.mTuningValues);
                }
            }
            clear();
            Trace.traceEnd(512L);
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

        QueueRequest setAccessible(boolean z) {
            this.mAccessible = z;
            return this;
        }
    }

    public QueueRequest getQueueRequest(int i) {
        QueueRequest queueRequestClear;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode != 1) {
                throw new IllegalStateException("The codec is not configured for block model");
            }
            if (i < 0 || i >= this.mQueueRequests.size()) {
                throw new IndexOutOfBoundsException("Expected range of index: [0," + (this.mQueueRequests.size() - 1) + "]; actual: " + i);
            }
            QueueRequest queueRequest = this.mQueueRequests.get(i);
            if (queueRequest == null) {
                throw new IllegalArgumentException("Unavailable index: " + i);
            }
            if (!queueRequest.isAccessible()) {
                throw new IllegalArgumentException("The request is stale at index " + i);
            }
            queueRequestClear = queueRequest.clear();
        }
        return queueRequestClear;
    }

    public final int dequeueOutputBuffer(BufferInfo bufferInfo, long j) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("dequeueOutputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use MediaCodec.Callback objects to get output buffer slots.");
            }
        }
        int iNative_dequeueOutputBuffer = native_dequeueOutputBuffer(bufferInfo, j);
        synchronized (this.mBufferLock) {
            try {
                if (iNative_dequeueOutputBuffer == -3) {
                    cacheBuffersLocked(false);
                } else if (iNative_dequeueOutputBuffer >= 0) {
                    validateOutputByteBufferLocked(this.mCachedOutputBuffers, iNative_dequeueOutputBuffer, bufferInfo);
                    if (this.mHasSurface || this.mCachedOutputBuffers == null) {
                        this.mDequeuedOutputInfos.put(Integer.valueOf(iNative_dequeueOutputBuffer), bufferInfo.dup());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iNative_dequeueOutputBuffer;
    }

    public final void releaseOutputBuffer(int i, boolean z) {
        releaseOutputBufferInternal(i, z, false, 0L);
    }

    public final void releaseOutputBuffer(int i, long j) {
        releaseOutputBufferInternal(i, true, true, j);
    }

    private void releaseOutputBufferInternal(int i, boolean z, boolean z2, long j) {
        synchronized (this.mBufferLock) {
            int i2 = this.mBufferMode;
            if (i2 == 0) {
                invalidateByteBufferLocked(this.mCachedOutputBuffers, i, false);
                this.mDequeuedOutputBuffers.remove(i);
                if (this.mHasSurface || this.mCachedOutputBuffers == null) {
                    this.mDequeuedOutputInfos.remove(Integer.valueOf(i));
                }
            } else if (i2 == 1) {
                OutputFrame outputFrame = this.mOutputFrames.get(i);
                outputFrame.setAccessible(false);
                outputFrame.clear();
            } else {
                throw new IllegalStateException("Unrecognized buffer mode: " + this.mBufferMode);
            }
        }
        releaseOutputBuffer(i, z, z2, j);
    }

    public final MediaFormat getOutputFormat() {
        return new MediaFormat(getFormatNative(false));
    }

    public final MediaFormat getInputFormat() {
        return new MediaFormat(getFormatNative(true));
    }

    public final MediaFormat getOutputFormat(int i) {
        return new MediaFormat(getOutputFormatNative(i));
    }

    private static class BufferMap {
        private final Map<Integer, CodecBuffer> mMap;

        private BufferMap() {
            this.mMap = new HashMap();
        }

        private static class CodecBuffer {
            private ByteBuffer mByteBuffer;
            private Image mImage;

            private CodecBuffer() {
            }

            public void free() {
                ByteBuffer byteBuffer = this.mByteBuffer;
                if (byteBuffer != null) {
                    NioUtils.freeDirectBuffer(byteBuffer);
                    this.mByteBuffer = null;
                }
                Image image = this.mImage;
                if (image != null) {
                    image.close();
                    this.mImage = null;
                }
            }

            public void setImage(Image image) {
                free();
                this.mImage = image;
            }

            public void setByteBuffer(ByteBuffer byteBuffer) {
                free();
                this.mByteBuffer = byteBuffer;
            }
        }

        public void remove(int i) {
            CodecBuffer codecBuffer = this.mMap.get(Integer.valueOf(i));
            if (codecBuffer != null) {
                codecBuffer.free();
                this.mMap.remove(Integer.valueOf(i));
            }
        }

        public void put(int i, ByteBuffer byteBuffer) {
            CodecBuffer codecBuffer = this.mMap.get(Integer.valueOf(i));
            if (codecBuffer == null) {
                codecBuffer = new CodecBuffer();
                this.mMap.put(Integer.valueOf(i), codecBuffer);
            }
            codecBuffer.setByteBuffer(byteBuffer);
        }

        public void put(int i, Image image) {
            CodecBuffer codecBuffer = this.mMap.get(Integer.valueOf(i));
            if (codecBuffer == null) {
                codecBuffer = new CodecBuffer();
                this.mMap.put(Integer.valueOf(i), codecBuffer);
            }
            codecBuffer.setImage(image);
        }

        public void clear() {
            Iterator<CodecBuffer> it = this.mMap.values().iterator();
            while (it.hasNext()) {
                it.next().free();
            }
            this.mMap.clear();
        }
    }

    private void invalidateByteBufferLocked(ByteBuffer[] byteBufferArr, int i, boolean z) {
        ByteBuffer byteBuffer;
        if (byteBufferArr == null) {
            if (i >= 0) {
                (z ? this.mValidInputIndices : this.mValidOutputIndices).clear(i);
            }
        } else {
            if (i < 0 || i >= byteBufferArr.length || (byteBuffer = byteBufferArr[i]) == null) {
                return;
            }
            byteBuffer.setAccessible(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateInputByteBufferLocked(ByteBuffer[] byteBufferArr, int i) {
        ByteBuffer byteBuffer;
        if (byteBufferArr == null) {
            if (i >= 0) {
                this.mValidInputIndices.set(i);
            }
        } else {
            if (i < 0 || i >= byteBufferArr.length || (byteBuffer = byteBufferArr[i]) == null) {
                return;
            }
            byteBuffer.setAccessible(true);
            byteBuffer.clear();
        }
    }

    private void revalidateByteBuffer(ByteBuffer[] byteBufferArr, int i, boolean z) {
        ByteBuffer byteBuffer;
        synchronized (this.mBufferLock) {
            if (byteBufferArr == null) {
                if (i >= 0) {
                    (z ? this.mValidInputIndices : this.mValidOutputIndices).set(i);
                }
            } else if (i >= 0 && i < byteBufferArr.length && (byteBuffer = byteBufferArr[i]) != null) {
                byteBuffer.setAccessible(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateOutputByteBuffersLocked(ByteBuffer[] byteBufferArr, int i, ArrayDeque<BufferInfo> arrayDeque) {
        ByteBuffer byteBuffer;
        Optional optionalMin = arrayDeque.stream().min(new Comparator() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((MediaCodec.BufferInfo) obj).offset, ((MediaCodec.BufferInfo) obj2).offset);
            }
        });
        Optional optionalMax = arrayDeque.stream().max(new Comparator() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((MediaCodec.BufferInfo) obj).offset, ((MediaCodec.BufferInfo) obj2).offset);
            }
        });
        if (byteBufferArr == null) {
            if (i >= 0) {
                this.mValidOutputIndices.set(i);
            }
        } else {
            if (i < 0 || i >= byteBufferArr.length || (byteBuffer = byteBufferArr[i]) == null || !optionalMin.isPresent() || !optionalMax.isPresent()) {
                return;
            }
            byteBuffer.setAccessible(true);
            byteBuffer.limit(((BufferInfo) optionalMax.get()).offset + ((BufferInfo) optionalMax.get()).size);
            byteBuffer.position(((BufferInfo) optionalMin.get()).offset);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateOutputByteBufferLocked(ByteBuffer[] byteBufferArr, int i, BufferInfo bufferInfo) {
        ByteBuffer byteBuffer;
        if (byteBufferArr == null) {
            if (i >= 0) {
                this.mValidOutputIndices.set(i);
            }
        } else {
            if (i < 0 || i >= byteBufferArr.length || (byteBuffer = byteBufferArr[i]) == null) {
                return;
            }
            byteBuffer.setAccessible(true);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size).position(bufferInfo.offset);
        }
    }

    private void invalidateByteBuffersLocked(ByteBuffer[] byteBufferArr) {
        if (byteBufferArr != null) {
            for (ByteBuffer byteBuffer : byteBufferArr) {
                if (byteBuffer != null) {
                    byteBuffer.setAccessible(false);
                }
            }
        }
    }

    private void freeByteBufferLocked(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            NioUtils.freeDirectBuffer(byteBuffer);
        }
    }

    private void freeByteBuffersLocked(ByteBuffer[] byteBufferArr) {
        if (byteBufferArr != null) {
            for (ByteBuffer byteBuffer : byteBufferArr) {
                freeByteBufferLocked(byteBuffer);
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

    private void cacheBuffersLocked(boolean z) {
        ByteBuffer[] buffers;
        BufferInfo bufferInfo;
        try {
            buffers = getBuffers(z);
            try {
                invalidateByteBuffersLocked(buffers);
            } catch (IllegalStateException unused) {
            }
        } catch (IllegalStateException unused2) {
            buffers = null;
        }
        if (buffers != null) {
            BitSet bitSet = z ? this.mValidInputIndices : this.mValidOutputIndices;
            for (int i = 0; i < buffers.length; i++) {
                ByteBuffer byteBuffer = buffers[i];
                if (byteBuffer != null && bitSet.get(i)) {
                    byteBuffer.setAccessible(true);
                    if (!z && (bufferInfo = this.mDequeuedOutputInfos.get(Integer.valueOf(i))) != null) {
                        byteBuffer.limit(bufferInfo.offset + bufferInfo.size).position(bufferInfo.offset);
                    }
                }
            }
            bitSet.clear();
        }
        if (z) {
            this.mCachedInputBuffers = buffers;
        } else {
            this.mCachedOutputBuffers = buffers;
        }
    }

    public ByteBuffer[] getInputBuffers() {
        ByteBuffer[] byteBufferArr;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("getInputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and attach to QueueRequest objects.");
            }
            if (this.mCachedInputBuffers == null) {
                cacheBuffersLocked(true);
            }
            byteBufferArr = this.mCachedInputBuffers;
            if (byteBufferArr == null) {
                throw new IllegalStateException();
            }
        }
        return byteBufferArr;
    }

    public ByteBuffer[] getOutputBuffers() {
        ByteBuffer[] byteBufferArr;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("getOutputBuffers() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getOutputFrame to get output frames.");
            }
            if (this.mCachedOutputBuffers == null) {
                cacheBuffersLocked(false);
            }
            byteBufferArr = this.mCachedOutputBuffers;
            if (byteBufferArr == null) {
                throw new IllegalStateException();
            }
        }
        return byteBufferArr;
    }

    public ByteBuffer getInputBuffer(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("getInputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and attach to QueueRequest objects.");
            }
        }
        ByteBuffer buffer = getBuffer(true, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.put(i, buffer);
        }
        return buffer;
    }

    public Image getInputImage(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("getInputImage() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please obtain MediaCodec.LinearBlock or HardwareBuffer objects and attach to QueueRequest objects.");
            }
        }
        Image image = getImage(true, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedInputBuffers, i, true);
            this.mDequeuedInputBuffers.put(i, image);
        }
        return image;
    }

    public ByteBuffer getOutputBuffer(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("getOutputBuffer() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getOutputFrame() to get output frames.");
            }
        }
        ByteBuffer buffer = getBuffer(false, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedOutputBuffers, i, false);
            this.mDequeuedOutputBuffers.put(i, buffer);
        }
        return buffer;
    }

    public Image getOutputImage(int i) {
        synchronized (this.mBufferLock) {
            if (this.mBufferMode == 1) {
                throw new IncompatibleWithBlockModelException("getOutputImage() is not compatible with CONFIGURE_FLAG_USE_BLOCK_MODEL. Please use getOutputFrame() to get output frames.");
            }
        }
        Image image = getImage(false, i);
        synchronized (this.mBufferLock) {
            invalidateByteBufferLocked(this.mCachedOutputBuffers, i, false);
            this.mDequeuedOutputBuffers.put(i, image);
        }
        return image;
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

        OutputFrame(int i) {
            this.mIndex = i;
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

        void setAccessible(boolean z) {
            this.mAccessible = z;
        }

        void setBufferInfo(BufferInfo bufferInfo) {
            this.mBufferInfos.clear();
            this.mPresentationTimeUs = bufferInfo.presentationTimeUs;
            this.mFlags = bufferInfo.flags;
        }

        void setBufferInfos(ArrayDeque<BufferInfo> arrayDeque) {
            this.mBufferInfos.clear();
            this.mBufferInfos.addAll(arrayDeque);
        }

        boolean isLoaded() {
            return this.mLoaded;
        }

        void setLoaded(boolean z) {
            this.mLoaded = z;
        }
    }

    public OutputFrame getOutputFrame(int i) {
        OutputFrame outputFrame;
        synchronized (this.mBufferLock) {
            if (this.mBufferMode != 1) {
                throw new IllegalStateException("The codec is not configured for block model");
            }
            if (i < 0 || i >= this.mOutputFrames.size()) {
                throw new IndexOutOfBoundsException("Expected range of index: [0," + (this.mQueueRequests.size() - 1) + "]; actual: " + i);
            }
            outputFrame = this.mOutputFrames.get(i);
            if (outputFrame == null) {
                throw new IllegalArgumentException("Unavailable index: " + i);
            }
            if (!outputFrame.isAccessible()) {
                throw new IllegalArgumentException("The output frame is stale at index " + i);
            }
            if (!outputFrame.isLoaded()) {
                native_getOutputFrame(outputFrame, i);
                outputFrame.setLoaded(true);
            }
        }
        return outputFrame;
    }

    public void setAudioPresentation(AudioPresentation audioPresentation) {
        if (audioPresentation == null) {
            throw new NullPointerException("audio presentation is null");
        }
        native_setAudioPresentation(audioPresentation.getPresentationId(), audioPresentation.getProgramId());
    }

    public final String getName() {
        String canonicalName = getCanonicalName();
        String str = this.mNameAtCreation;
        return str != null ? str : canonicalName;
    }

    public PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public final void setParameters(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String[] strArr = new String[bundle.size()];
        Object[] objArr = new Object[bundle.size()];
        int i = 0;
        for (String str : bundle.keySet()) {
            if (str.equals(MediaFormat.KEY_AUDIO_SESSION_ID)) {
                try {
                    int iIntValue = ((Integer) bundle.get(str)).intValue();
                    strArr[i] = MediaFormat.KEY_AUDIO_HW_SYNC;
                    objArr[i] = Integer.valueOf(AudioSystem.getAudioHwSyncForSession(iIntValue));
                } catch (Exception unused) {
                    throw new IllegalArgumentException("Wrong Session ID Parameter!");
                }
            } else if (com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.applyPictureProfiles() && com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.mediaQualityFw() && str.equals(MediaFormat.KEY_PICTURE_PROFILE_INSTANCE)) {
                try {
                    PictureProfile pictureProfile = (PictureProfile) bundle.get(str);
                    if (pictureProfile == null) {
                        throw new IllegalArgumentException("Picture profile instance parameter is null!");
                    }
                    PictureProfileHandle handle = pictureProfile.getHandle();
                    if (handle != PictureProfileHandle.NONE) {
                        strArr[i] = PARAMETER_KEY_PICTURE_PROFILE_HANDLE;
                        objArr[i] = Long.valueOf(handle.getId());
                    }
                } catch (ClassCastException unused2) {
                    throw new IllegalArgumentException("Cannot cast the instance parameter to PictureProfile!");
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                    throw new IllegalArgumentException("Unexpected exception when casting the instance parameter to PictureProfile!");
                }
            } else if (com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.applyPictureProfiles() && com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags.mediaQualityFw() && str.equals(MediaFormat.KEY_PICTURE_PROFILE_ID)) {
                try {
                    String str2 = (String) bundle.get(str);
                    if (str2 == null) {
                        throw new IllegalArgumentException("KEY_PICTURE_PROFILE_ID parameter is null!");
                    }
                    if (!str2.isEmpty()) {
                        strArr[i] = MediaFormat.KEY_PICTURE_PROFILE_ID;
                        objArr[i] = str2;
                    }
                } catch (ClassCastException unused3) {
                    throw new IllegalArgumentException("Cannot cast the KEY_PICTURE_PROFILE_ID parameter to String!");
                } catch (Exception e2) {
                    Log.e(TAG, Log.getStackTraceString(e2));
                    throw new IllegalArgumentException("Unexpected exception when casting the KEY_PICTURE_PROFILE_ID parameter!");
                }
            } else {
                strArr[i] = str;
                Object obj = bundle.get(str);
                if (obj instanceof byte[]) {
                    objArr[i] = ByteBuffer.wrap((byte[]) obj);
                } else {
                    objArr[i] = obj;
                }
            }
            i++;
        }
        setParameters(strArr, objArr);
    }

    private void logAndRun(String str, Runnable runnable) {
        Log.d(TAG, "enter: " + str);
        runnable.run();
        Log.d(TAG, "exit : " + str);
    }

    public void setCallback(Callback callback, Handler handler) {
        boolean zGetFlag = GetFlag(new Supplier() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(Flags.setCallbackStall());
            }
        });
        if (callback != null) {
            synchronized (this.mListenerLock) {
                EventHandler eventHandlerOn = getEventHandlerOn(handler, this.mCallbackHandler);
                EventHandler eventHandler = this.mCallbackHandler;
                if (eventHandlerOn != eventHandler) {
                    if (zGetFlag) {
                        logAndRun("[new handler] removeMessages(SET_CALLBACK)", new Runnable() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$setCallback$6();
                            }
                        });
                        logAndRun("[new handler] removeMessages(CALLBACK)", new Runnable() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$setCallback$7();
                            }
                        });
                    } else {
                        eventHandler.removeMessages(2);
                        this.mCallbackHandler.removeMessages(1);
                    }
                    this.mCallbackHandler = eventHandlerOn;
                }
            }
        } else {
            EventHandler eventHandler2 = this.mCallbackHandler;
            if (eventHandler2 != null) {
                if (zGetFlag) {
                    logAndRun("[null handler] removeMessages(SET_CALLBACK)", new Runnable() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$setCallback$8();
                        }
                    });
                    logAndRun("[null handler] removeMessages(CALLBACK)", new Runnable() { // from class: android.media.MediaCodec$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$setCallback$9();
                        }
                    });
                } else {
                    eventHandler2.removeMessages(2);
                    this.mCallbackHandler.removeMessages(1);
                }
            }
        }
        EventHandler eventHandler3 = this.mCallbackHandler;
        if (eventHandler3 != null) {
            this.mCallbackHandler.sendMessage(eventHandler3.obtainMessage(2, 0, 0, callback));
            native_setCallback(callback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$6() {
        this.mCallbackHandler.removeMessages(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$7() {
        this.mCallbackHandler.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$8() {
        this.mCallbackHandler.removeMessages(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$9() {
        this.mCallbackHandler.removeMessages(1);
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setOnFirstTunnelFrameReadyListener(Handler handler, OnFirstTunnelFrameReadyListener onFirstTunnelFrameReadyListener) {
        synchronized (this.mListenerLock) {
            this.mOnFirstTunnelFrameReadyListener = onFirstTunnelFrameReadyListener;
            if (onFirstTunnelFrameReadyListener != null) {
                EventHandler eventHandlerOn = getEventHandlerOn(handler, this.mOnFirstTunnelFrameReadyHandler);
                EventHandler eventHandler = this.mOnFirstTunnelFrameReadyHandler;
                if (eventHandlerOn != eventHandler) {
                    eventHandler.removeMessages(4);
                }
                this.mOnFirstTunnelFrameReadyHandler = eventHandlerOn;
            } else {
                EventHandler eventHandler2 = this.mOnFirstTunnelFrameReadyHandler;
                if (eventHandler2 != null) {
                    eventHandler2.removeMessages(4);
                }
            }
            native_enableOnFirstTunnelFrameReadyListener(onFirstTunnelFrameReadyListener != null);
        }
    }

    public void setOnFrameRenderedListener(OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        synchronized (this.mListenerLock) {
            this.mOnFrameRenderedListener = onFrameRenderedListener;
            if (onFrameRenderedListener != null) {
                EventHandler eventHandlerOn = getEventHandlerOn(handler, this.mOnFrameRenderedHandler);
                EventHandler eventHandler = this.mOnFrameRenderedHandler;
                if (eventHandlerOn != eventHandler) {
                    eventHandler.removeMessages(3);
                }
                this.mOnFrameRenderedHandler = eventHandlerOn;
            } else {
                EventHandler eventHandler2 = this.mOnFrameRenderedHandler;
                if (eventHandler2 != null) {
                    eventHandler2.removeMessages(3);
                }
            }
            native_enableOnFrameRenderedListener(onFrameRenderedListener != null);
        }
    }

    public List<String> getSupportedVendorParameters() {
        return native_getSupportedVendorParameters();
    }

    public static class ParameterDescriptor {
        private String mName;
        private int mType;

        private ParameterDescriptor() {
        }

        public String getName() {
            return this.mName;
        }

        public int getType() {
            return this.mType;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof ParameterDescriptor)) {
                return false;
            }
            ParameterDescriptor parameterDescriptor = (ParameterDescriptor) obj;
            return this.mName.equals(parameterDescriptor.mName) && this.mType == parameterDescriptor.mType;
        }

        public int hashCode() {
            return Arrays.asList(this.mName, Integer.valueOf(this.mType)).hashCode();
        }
    }

    public ParameterDescriptor getParameterDescriptor(String str) {
        return native_getParameterDescriptor(str);
    }

    public void subscribeToVendorParameters(List<String> list) {
        native_subscribeToVendorParameters(list);
    }

    public void unsubscribeFromVendorParameters(List<String> list) {
        native_unsubscribeFromVendorParameters(list);
    }

    private EventHandler getEventHandlerOn(Handler handler, EventHandler eventHandler) {
        if (handler == null) {
            return this.mEventHandler;
        }
        Looper looper = handler.getLooper();
        return eventHandler.getLooper() == looper ? eventHandler : new EventHandler(this, looper);
    }

    public static abstract class Callback {
        public abstract void onError(MediaCodec mediaCodec, CodecException codecException);

        public abstract void onInputBufferAvailable(MediaCodec mediaCodec, int i);

        public void onMetricsFlushed(MediaCodec mediaCodec, PersistableBundle persistableBundle) {
        }

        public abstract void onOutputBufferAvailable(MediaCodec mediaCodec, int i, BufferInfo bufferInfo);

        public abstract void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat);

        public void onRequiredResourcesChanged(MediaCodec mediaCodec) {
        }

        public void onOutputBuffersAvailable(MediaCodec mediaCodec, int i, ArrayDeque<BufferInfo> arrayDeque) {
            throw new IllegalStateException("Client must override onOutputBuffersAvailable when codec is configured to operate with multiple access units");
        }

        public void onCryptoError(MediaCodec mediaCodec, CryptoException cryptoException) {
            throw new IllegalStateException("Client must override onCryptoError when the codec is configured with CONFIGURE_FLAG_USE_CRYPTO_ASYNC.", cryptoException);
        }
    }

    private void postEventFromNative(int i, int i2, int i3, Object obj) {
        synchronized (this.mListenerLock) {
            EventHandler eventHandler = this.mEventHandler;
            if (i == 1) {
                eventHandler = this.mCallbackHandler;
            } else if (i == 4) {
                eventHandler = this.mOnFirstTunnelFrameReadyHandler;
            } else if (i == 3) {
                eventHandler = this.mOnFrameRenderedHandler;
            }
            if (eventHandler != null) {
                eventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj));
            }
        }
    }

    public MediaCodecInfo getCodecInfo() {
        MediaCodecInfo mediaCodecInfo;
        String name = getName();
        synchronized (this.mCodecInfoLock) {
            if (this.mCodecInfo == null) {
                MediaCodecInfo ownCodecInfo = getOwnCodecInfo();
                this.mCodecInfo = ownCodecInfo;
                if (ownCodecInfo == null) {
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

    private final void setAndUnlockContext(long j) {
        this.mNativeContext = j;
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
            Image.Plane[] planeArr = this.mPlanes;
            return (Image.Plane[]) Arrays.copyOf(planeArr, planeArr.length);
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            if (this.mIsImageValid) {
                ByteBuffer byteBuffer = this.mBuffer;
                if (byteBuffer != null) {
                    NioUtils.freeDirectBuffer(byteBuffer);
                }
                long j = this.mBufferContext;
                if (j != 0) {
                    MediaCodec.native_closeMediaImage(j);
                }
                this.mIsImageValid = false;
            }
        }

        @Override // android.media.Image
        public void setCropRect(Rect rect) {
            if (this.mIsReadOnly) {
                throw new ReadOnlyBufferException();
            }
            super.setCropRect(rect);
        }

        public MediaImage(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z, long j, int i, int i2, Rect rect) {
            int i3;
            int i4;
            this.mTimestamp = j;
            this.mIsImageValid = true;
            this.mIsReadOnly = byteBuffer.isReadOnly();
            this.mBuffer = byteBuffer.duplicate();
            this.mXOffset = i;
            this.mYOffset = i2;
            ByteBuffer byteBuffer3 = byteBuffer2;
            this.mInfo = byteBuffer3;
            this.mBufferContext = 0L;
            if (byteBuffer3.remaining() == 104) {
                int i5 = byteBuffer3.getInt();
                if (i5 != 1) {
                    throw new UnsupportedOperationException("unsupported type: " + i5);
                }
                int i6 = byteBuffer3.getInt();
                if (i6 != 3) {
                    throw new RuntimeException("unexpected number of planes: " + i6);
                }
                int i7 = byteBuffer3.getInt();
                this.mWidth = i7;
                int i8 = byteBuffer3.getInt();
                this.mHeight = i8;
                if (i7 < 1 || i8 < 1) {
                    throw new UnsupportedOperationException("unsupported size: " + i7 + "x" + i8);
                }
                int i9 = byteBuffer3.getInt();
                if (i9 != 8 && i9 != 10) {
                    throw new UnsupportedOperationException("unsupported bit depth: " + i9);
                }
                int i10 = byteBuffer3.getInt();
                if (i10 != 8 && i10 != 16) {
                    throw new UnsupportedOperationException("unsupported allocated bit depth: " + i10);
                }
                if (i9 == 8 && i10 == 8) {
                    this.mFormat = 35;
                    i4 = 1;
                    i3 = 2;
                } else if (i9 == 10 && i10 == 16) {
                    this.mFormat = 54;
                    i3 = 4;
                    i4 = 2;
                } else {
                    throw new UnsupportedOperationException("couldn't infer ImageFormat bitDepth: " + i9 + " bitDepthAllocated: " + i10);
                }
                this.mPlanes = new MediaPlane[i6];
                int i11 = -1;
                int i12 = -1;
                int i13 = 0;
                while (i13 < i6) {
                    int i14 = byteBuffer3.getInt();
                    int i15 = byteBuffer3.getInt();
                    int i16 = byteBuffer3.getInt();
                    int i17 = byteBuffer3.getInt();
                    int i18 = byteBuffer3.getInt();
                    if (i17 == i18) {
                        if (i17 == (i13 == 0 ? 1 : 2)) {
                            if (i15 < 1 || i16 < 1) {
                                throw new UnsupportedOperationException("unexpected strides: " + i15 + " pixel, " + i16 + " row on plane " + i13);
                            }
                            byteBuffer.clear();
                            byteBuffer.position(this.mBuffer.position() + i14 + ((i / i17) * i15) + ((i2 / i18) * i16));
                            byteBuffer.limit(byteBuffer.position() + Utils.divUp(i9, 8) + (((this.mHeight / i18) - 1) * i16) + (((this.mWidth / i17) - 1) * i15));
                            this.mPlanes[i13] = new MediaPlane(byteBuffer.slice(), i16, i15);
                            int i19 = this.mFormat;
                            if ((i19 == 35 || i19 == 54 || i19 == 60) && i13 == 1) {
                                i11 = i14;
                            } else if ((i19 == 35 || i19 == 54 || i19 == 60) && i13 == 2) {
                                i12 = i14;
                            }
                            i13++;
                            byteBuffer3 = byteBuffer2;
                        }
                    }
                    throw new UnsupportedOperationException("unexpected subsampling: " + i17 + "x" + i18 + " on plane " + i13);
                }
                int i20 = this.mFormat;
                if (i20 == 54 || i20 == 60) {
                    int i21 = i12;
                    if (i21 != i4 + i11) {
                        throw new UnsupportedOperationException("Invalid plane offsets cbPlaneOffset: " + i11 + " crPlaneOffset: " + i21);
                    }
                    if (this.mPlanes[1].getPixelStride() != i3 || this.mPlanes[2].getPixelStride() != i3) {
                        throw new UnsupportedOperationException("Invalid pixelStride");
                    }
                }
                Rect rect2 = rect == null ? new Rect(0, 0, this.mWidth, this.mHeight) : rect;
                rect2.offset(-i, -i2);
                super.setCropRect(rect2);
                return;
            }
            throw new UnsupportedOperationException("unsupported info length: " + byteBuffer2.remaining());
        }

        public MediaImage(ByteBuffer[] byteBufferArr, int[] iArr, int[] iArr2, int i, int i2, int i3, boolean z, long j, int i4, int i5, Rect rect, long j2) {
            if (byteBufferArr.length != iArr.length || byteBufferArr.length != iArr2.length) {
                throw new IllegalArgumentException("buffers, rowStrides and pixelStrides should have the same length");
            }
            this.mWidth = i;
            this.mHeight = i2;
            this.mFormat = i3;
            this.mTimestamp = j;
            this.mIsImageValid = true;
            this.mIsReadOnly = z;
            this.mBuffer = null;
            this.mInfo = null;
            this.mPlanes = new MediaPlane[byteBufferArr.length];
            for (int i6 = 0; i6 < byteBufferArr.length; i6++) {
                this.mPlanes[i6] = new MediaPlane(byteBufferArr[i6], iArr[i6], iArr2[i6]);
            }
            this.mXOffset = i4;
            this.mYOffset = i5;
            Rect rect2 = rect == null ? new Rect(0, 0, this.mWidth, this.mHeight) : rect;
            rect2.offset(-i4, -i5);
            super.setCropRect(rect2);
            this.mBufferContext = j2;
        }

        private class MediaPlane extends Image.Plane {
            private final int mColInc;
            private final ByteBuffer mData;
            private final int mRowInc;

            public MediaPlane(ByteBuffer byteBuffer, int i, int i2) {
                this.mData = byteBuffer;
                this.mRowInc = i;
                this.mColInc = i2;
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

        private MetricsConstants() {
        }
    }
}
