package com.samsung.android.media;

import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Process;
import android.util.Log;
import com.samsung.android.media.SemMediaResourceHelper;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpCookie;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemAsyncVideoFrameDecoder {
    private static final int DECODING_COMPLETED = 202;
    private static final int ERROR = 100;
    public static final int HW_CODEC = 1;
    private static final int INFO = 200;
    private static final int INIT_COMPLETED = 201;
    public static final int MEDIA_ERROR_CODEC_DIED = 101;
    public static final int MEDIA_ERROR_EXTRACTOR_DIED = 100;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_RESOURCE_OVERSPEC = -5001;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    public static final int OPTION_CLOSEST = 3;
    public static final int OPTION_CLOSEST_SYNC = 2;
    public static final int OPTION_NEXT_SYNC = 1;
    public static final int OPTION_PREVIOUS_SYNC = 0;
    public static final int SW_CODEC = 2;
    private static final String TAG = "SemAsyncVideoFrameDecoder";
    private static final int VIDEO_FRAME = 1;
    private EventHandler mEventHandler;
    private long mNativeContext;
    private OnDecodingCompleteListener mOnDecodingCompleteListener;
    private OnErrorListener mOnErrorListener;
    private OnInitCompleteListener mOnInitCompleteListener;
    private OnVideoFrameListener mOnVideoFrameListener;
    private SemMediaResourceHelper mSemMediaResourceHelper;

    public interface OnDecodingCompleteListener {
        void onDecodingCompleted(SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder, int i);
    }

    public interface OnErrorListener {
        boolean onError(SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder, int i, int i2);
    }

    public interface OnInitCompleteListener {
        void onInitCompleted(SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder);
    }

    public interface OnVideoFrameListener {
        void onVideoFrame(SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder, Bitmap bitmap, int i, int i2);
    }

    private native void _init(IBinder iBinder, String str, String[] strArr, String[] strArr2, String str2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    private native void _init(FileDescriptor fileDescriptor, long j, long j2) throws IllegalStateException, IOException, IllegalArgumentException;

    private native void _release();

    private native void _reset() throws IllegalStateException;

    private native void _setFrameTime(Parcel parcel);

    private native void _setOutputColorFormat(Bitmap.Config config);

    private native void _setOutputImageSize(int i, int i2, boolean z);

    private native void _setPreferredCodec(int i);

    private native void _setSeekOption(int i) throws IllegalStateException;

    private native void _start(int i, int i2) throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup(Object obj);

    static {
        System.loadLibrary("videoframedec_jni");
        native_init();
    }

    public SemAsyncVideoFrameDecoder() {
        this.mEventHandler = null;
        this.mSemMediaResourceHelper = null;
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
        this.mSemMediaResourceHelper = SemMediaResourceHelper.createInstance(2, false);
        native_setup(new WeakReference(this));
    }

    public void init(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        init(fileDescriptor, 0L, 576460752303423487L);
    }

    public void init(FileDescriptor fileDescriptor, long j, long j2) throws IllegalStateException, IOException, IllegalArgumentException {
        _init(fileDescriptor, j, j2);
    }

    public void init(String str) throws Throwable {
        init(str, null, null, null);
    }

    private void init(String str, Map<String, String> map, List<HttpCookie> list, String str2) throws Throwable {
        String[] strArr;
        String[] strArr2;
        SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder;
        String str3;
        List<HttpCookie> list2;
        String str4;
        if (map != null) {
            String[] strArr3 = new String[map.size()];
            String[] strArr4 = new String[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                strArr3[i] = entry.getKey();
                strArr4[i] = entry.getValue();
                i++;
            }
            strArr = strArr3;
            strArr2 = strArr4;
            str3 = str;
            list2 = list;
            str4 = str2;
            semAsyncVideoFrameDecoder = this;
        } else {
            strArr = null;
            strArr2 = null;
            semAsyncVideoFrameDecoder = this;
            str3 = str;
            list2 = list;
            str4 = str2;
        }
        semAsyncVideoFrameDecoder.init(str3, strArr, strArr2, list2, str4);
    }

    private void init(String str, String[] strArr, String[] strArr2, List<HttpCookie> list, String str2) throws Throwable {
        Throwable th;
        Uri uri = Uri.parse(str);
        if (!"file".equals(uri.getScheme())) {
            _init(null, str, strArr, strArr2, str2);
            return;
        }
        File file = new File(uri.getPath());
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    init(fileInputStream2.getFD());
                    fileInputStream2.close();
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            throw new IOException("init failed with file scheme");
        }
    }

    public void setSeekOption(int i) throws IllegalStateException {
        _setSeekOption(i);
    }

    public void setOutputImageSize(int i, int i2, boolean z) throws IllegalStateException {
        _setOutputImageSize(i, i2, z);
    }

    public void setOutputColorFormat(Bitmap.Config config) throws IllegalStateException {
        _setOutputColorFormat(config);
    }

    public void setTargetFrameTimeList(List<Integer> list) throws IllegalArgumentException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            int size = list.size();
            if (size <= 0) {
                throw new IllegalArgumentException("there's no time request");
            }
            parcelObtain.writeInt(size);
            for (int i = 0; i < size; i++) {
                Integer num = list.get(i);
                if (num.intValue() < 0) {
                    throw new IllegalArgumentException("abnormal frame time. timeMsList[" + i + "] = " + num);
                }
                parcelObtain.writeInt(num.intValue());
            }
            _setFrameTime(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }

    public void setPreferredCodec(int i) throws IllegalArgumentException {
        if (i == 1 || i == 2) {
            _setPreferredCodec(i);
        } else {
            throw new IllegalArgumentException("Illegal option for setPreferredCodec :" + i);
        }
    }

    private int getCurrentVideoCodecUsage() {
        int videoWidth = 0;
        if (this.mSemMediaResourceHelper == null) {
            return 0;
        }
        int iMyPid = Process.myPid();
        if (iMyPid > 0) {
            Iterator<SemMediaResourceHelper.MediaResourceInfo> it = this.mSemMediaResourceHelper.getMediaResourceInfo(2).iterator();
            while (it.hasNext()) {
                SemMediaResourceHelper.MediaResourceInfo next = it.next();
                if (next.getPid() == iMyPid) {
                    int videoFrameRate = next.getVideoFrameRate();
                    videoWidth = (int) (videoWidth + (next.getVideoWidth() * next.getVideoHeight() * (videoFrameRate >= 120 ? 4.0f : videoFrameRate >= 60 ? 2.0f : videoFrameRate <= 15 ? 0.5f : 1.0f)));
                }
            }
        }
        return videoWidth;
    }

    public void start() throws IllegalStateException {
        SemMediaResourceHelper semMediaResourceHelper = this.mSemMediaResourceHelper;
        _start(semMediaResourceHelper != null ? semMediaResourceHelper.getMaxVideoCapacity() : 0, getCurrentVideoCodecUsage());
    }

    public void stop() throws IllegalStateException {
        _stop();
    }

    public void reset() throws IllegalStateException {
        _reset();
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler != null) {
            eventHandler.removeCallbacksAndMessages(null);
        }
    }

    public void release() {
        this.mOnInitCompleteListener = null;
        this.mOnVideoFrameListener = null;
        this.mOnDecodingCompleteListener = null;
        this.mOnErrorListener = null;
        this.mSemMediaResourceHelper.release();
        _release();
    }

    protected void finalize() {
        native_finalize();
    }

    private class EventHandler extends Handler {
        private SemAsyncVideoFrameDecoder mVideoFrameDecoder;

        public EventHandler(SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder, Looper looper) {
            super(looper);
            this.mVideoFrameDecoder = semAsyncVideoFrameDecoder;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mVideoFrameDecoder.mNativeContext == 0) {
                Log.w(SemAsyncVideoFrameDecoder.TAG, "VideoFrameDecoder went away with unhandled events");
                return;
            }
            int i = message.what;
            if (i == 1) {
                Log.i(SemAsyncVideoFrameDecoder.TAG, "VIDEO_FRAME");
                if (SemAsyncVideoFrameDecoder.this.mOnVideoFrameListener != null) {
                    SemAsyncVideoFrameDecoder.this.mOnVideoFrameListener.onVideoFrame(this.mVideoFrameDecoder, (Bitmap) message.obj, message.arg1, message.arg2);
                    return;
                }
                return;
            }
            if (i == 100) {
                Log.e(SemAsyncVideoFrameDecoder.TAG, "Error (" + message.arg1 + "," + message.arg2 + NavigationBarInflaterView.KEY_CODE_END);
                if (SemAsyncVideoFrameDecoder.this.mOnErrorListener != null ? SemAsyncVideoFrameDecoder.this.mOnErrorListener.onError(this.mVideoFrameDecoder, message.arg1, message.arg2) : false) {
                    return;
                }
                Log.i(SemAsyncVideoFrameDecoder.TAG, "Error is not handled(" + message.arg1 + "," + message.arg2 + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (i == 200) {
                if (message.arg1 == 201) {
                    Log.i(SemAsyncVideoFrameDecoder.TAG, "INIT_COMPLETED");
                    if (SemAsyncVideoFrameDecoder.this.mOnInitCompleteListener != null) {
                        SemAsyncVideoFrameDecoder.this.mOnInitCompleteListener.onInitCompleted(this.mVideoFrameDecoder);
                        return;
                    }
                    return;
                }
                if (message.arg1 == 202) {
                    Log.i(SemAsyncVideoFrameDecoder.TAG, "DECODING_COMPLETED");
                    if (SemAsyncVideoFrameDecoder.this.mOnDecodingCompleteListener != null) {
                        SemAsyncVideoFrameDecoder.this.mOnDecodingCompleteListener.onDecodingCompleted(this.mVideoFrameDecoder, message.arg2);
                        return;
                    }
                    return;
                }
                return;
            }
            Log.e(SemAsyncVideoFrameDecoder.TAG, "Unknown message type " + message.what);
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder = (SemAsyncVideoFrameDecoder) ((WeakReference) obj).get();
        if (semAsyncVideoFrameDecoder == null) {
            Log.w(TAG, "vfd is null");
            return;
        }
        EventHandler eventHandler = semAsyncVideoFrameDecoder.mEventHandler;
        if (eventHandler == null) {
            Log.w(TAG, "vfd.mEventHandler is null");
        } else {
            semAsyncVideoFrameDecoder.mEventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public void setOnInitCompleteListener(OnInitCompleteListener onInitCompleteListener) {
        this.mOnInitCompleteListener = onInitCompleteListener;
    }

    public void setOnVideoFrameListener(OnVideoFrameListener onVideoFrameListener) {
        this.mOnVideoFrameListener = onVideoFrameListener;
    }

    public void setOnDecodingCompleteListener(OnDecodingCompleteListener onDecodingCompleteListener) {
        this.mOnDecodingCompleteListener = onDecodingCompleteListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }
}
