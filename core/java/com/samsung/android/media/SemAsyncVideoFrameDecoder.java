package com.samsung.android.media;

import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.Parcel;
import android.p009os.Process;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
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
    void onVideoFrame(
        SemAsyncVideoFrameDecoder semAsyncVideoFrameDecoder, Bitmap bitmap, int i, int i2);
  }

  private native void _init(
      IBinder iBinder, String str, String[] strArr, String[] strArr2, String str2)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

  private native void _init(FileDescriptor fileDescriptor, long j, long j2)
      throws IOException, IllegalArgumentException, IllegalStateException;

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
    Looper looper = Looper.myLooper();
    if (looper != null) {
      this.mEventHandler = new EventHandler(this, looper);
    } else {
      Looper looper2 = Looper.getMainLooper();
      if (looper2 != null) {
        this.mEventHandler = new EventHandler(this, looper2);
      } else {
        this.mEventHandler = null;
      }
    }
    this.mSemMediaResourceHelper = SemMediaResourceHelper.createInstance(2, false);
    native_setup(new WeakReference(this));
  }

  public void init(FileDescriptor fd)
      throws IOException, IllegalArgumentException, IllegalStateException {
    init(fd, 0L, 576460752303423487L);
  }

  public void init(FileDescriptor fd, long offset, long length)
      throws IOException, IllegalArgumentException, IllegalStateException {
    _init(fd, offset, length);
  }

  public void init(String path)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    init(path, null, null, null);
  }

  private void init(
      String path, Map<String, String> headers, List<HttpCookie> cookies, String cacheDir)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
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
    init(path, keys, values, cookies, cacheDir);
  }

  private void init(
      String path, String[] keys, String[] values, List<HttpCookie> cookies, String cacheDir)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    Uri uri = Uri.parse(path);
    String scheme = uri.getScheme();
    if (!"file".equals(scheme)) {
      _init(null, path, keys, values, cacheDir);
      return;
    }
    File file = new File(uri.getPath());
    if (!file.exists()) {
      throw new IOException("init failed with file scheme");
    }
    FileInputStream is = null;
    try {
      is = new FileInputStream(file);
      FileDescriptor fd = is.getFD();
      try {
        init(fd);
        is.close();
      } catch (Throwable th) {
        th = th;
        if (is != null) {
          is.close();
        }
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
    }
  }

  public void setSeekOption(int option) throws IllegalStateException {
    _setSeekOption(option);
  }

  public void setOutputImageSize(int dstWidth, int dstHeight, boolean keepAspectRatio)
      throws IllegalStateException {
    _setOutputImageSize(dstWidth, dstHeight, keepAspectRatio);
  }

  public void setOutputColorFormat(Bitmap.Config config) throws IllegalStateException {
    _setOutputColorFormat(config);
  }

  public void setTargetFrameTimeList(List<Integer> timeMsList) throws IllegalArgumentException {
    Parcel request = Parcel.obtain();
    try {
      int listSize = timeMsList.size();
      if (listSize <= 0) {
        throw new IllegalArgumentException("there's no time request");
      }
      request.writeInt(listSize);
      for (int i = 0; i < listSize; i++) {
        Integer timeMs = timeMsList.get(i);
        if (timeMs.intValue() < 0) {
          throw new IllegalArgumentException(
              "abnormal frame time. timeMsList[" + i + "] = " + timeMs);
        }
        request.writeInt(timeMs.intValue());
      }
      _setFrameTime(request);
    } finally {
      request.recycle();
    }
  }

  public void setPreferredCodec(int option) throws IllegalArgumentException {
    if (option == 1 || option == 2) {
      _setPreferredCodec(option);
    } else {
      String msg = "Illegal option for setPreferredCodec :" + option;
      throw new IllegalArgumentException(msg);
    }
  }

  private int getCurrentVideoCodecUsage() {
    if (this.mSemMediaResourceHelper == null) {
      return 0;
    }
    int currentUsage = 0;
    int myPid = Process.myPid();
    if (myPid > 0) {
      ArrayList<SemMediaResourceHelper.MediaResourceInfo> videoResourceInfo =
          this.mSemMediaResourceHelper.getMediaResourceInfo(2);
      Iterator<SemMediaResourceHelper.MediaResourceInfo> it = videoResourceInfo.iterator();
      while (it.hasNext()) {
        SemMediaResourceHelper.MediaResourceInfo info = it.next();
        if (info.getPid() == myPid) {
          float scale = 1.0f;
          int frameRate = info.getVideoFrameRate();
          if (frameRate >= 120) {
            scale = 4.0f;
          } else if (frameRate >= 60) {
            scale = 2.0f;
          } else if (frameRate <= 15) {
            scale = 0.5f;
          }
          currentUsage =
              (int) (currentUsage + (info.getVideoWidth() * info.getVideoHeight() * scale));
        }
      }
    }
    return currentUsage;
  }

  public void start() throws IllegalStateException {
    int maxVideoCapacity = 0;
    SemMediaResourceHelper semMediaResourceHelper = this.mSemMediaResourceHelper;
    if (semMediaResourceHelper != null) {
      maxVideoCapacity = semMediaResourceHelper.getMaxVideoCapacity();
    }
    _start(maxVideoCapacity, getCurrentVideoCodecUsage());
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

    public EventHandler(SemAsyncVideoFrameDecoder vfd, Looper looper) {
      super(looper);
      this.mVideoFrameDecoder = vfd;
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      if (this.mVideoFrameDecoder.mNativeContext == 0) {
        Log.m102w(
            SemAsyncVideoFrameDecoder.TAG, "VideoFrameDecoder went away with unhandled events");
      }
      switch (msg.what) {
        case 1:
          Log.m98i(SemAsyncVideoFrameDecoder.TAG, "VIDEO_FRAME");
          if (SemAsyncVideoFrameDecoder.this.mOnVideoFrameListener != null) {
            Bitmap outBitmap = (Bitmap) msg.obj;
            SemAsyncVideoFrameDecoder.this.mOnVideoFrameListener.onVideoFrame(
                this.mVideoFrameDecoder, outBitmap, msg.arg1, msg.arg2);
            break;
          }
          break;
        case 100:
          Log.m96e(
              SemAsyncVideoFrameDecoder.TAG,
              "Error (" + msg.arg1 + "," + msg.arg2 + NavigationBarInflaterView.KEY_CODE_END);
          boolean error_was_handled = false;
          if (SemAsyncVideoFrameDecoder.this.mOnErrorListener != null) {
            error_was_handled =
                SemAsyncVideoFrameDecoder.this.mOnErrorListener.onError(
                    this.mVideoFrameDecoder, msg.arg1, msg.arg2);
          }
          if (!error_was_handled) {
            Log.m98i(
                SemAsyncVideoFrameDecoder.TAG,
                "Error is not handled("
                    + msg.arg1
                    + ","
                    + msg.arg2
                    + NavigationBarInflaterView.KEY_CODE_END);
            break;
          }
          break;
        case 200:
          if (msg.arg1 == 201) {
            Log.m98i(SemAsyncVideoFrameDecoder.TAG, "INIT_COMPLETED");
            if (SemAsyncVideoFrameDecoder.this.mOnInitCompleteListener != null) {
              SemAsyncVideoFrameDecoder.this.mOnInitCompleteListener.onInitCompleted(
                  this.mVideoFrameDecoder);
              break;
            }
          } else if (msg.arg1 == 202) {
            Log.m98i(SemAsyncVideoFrameDecoder.TAG, "DECODING_COMPLETED");
            if (SemAsyncVideoFrameDecoder.this.mOnDecodingCompleteListener != null) {
              SemAsyncVideoFrameDecoder.this.mOnDecodingCompleteListener.onDecodingCompleted(
                  this.mVideoFrameDecoder, msg.arg2);
              break;
            }
          }
          break;
        default:
          Log.m96e(SemAsyncVideoFrameDecoder.TAG, "Unknown message type " + msg.what);
          break;
      }
    }
  }

  private static void postEventFromNative(Object ref, int what, int arg1, int arg2, Object obj) {
    SemAsyncVideoFrameDecoder vfd = (SemAsyncVideoFrameDecoder) ((WeakReference) ref).get();
    if (vfd == null) {
      Log.m102w(TAG, "vfd is null");
      return;
    }
    EventHandler eventHandler = vfd.mEventHandler;
    if (eventHandler == null) {
      Log.m102w(TAG, "vfd.mEventHandler is null");
    } else {
      Message m = eventHandler.obtainMessage(what, arg1, arg2, obj);
      vfd.mEventHandler.sendMessage(m);
    }
  }

  public void setOnInitCompleteListener(OnInitCompleteListener listener) {
    this.mOnInitCompleteListener = listener;
  }

  public void setOnVideoFrameListener(OnVideoFrameListener listener) {
    this.mOnVideoFrameListener = listener;
  }

  public void setOnDecodingCompleteListener(OnDecodingCompleteListener listener) {
    this.mOnDecodingCompleteListener = listener;
  }

  public void setOnErrorListener(OnErrorListener listener) {
    this.mOnErrorListener = listener;
  }
}
