package com.samsung.android.content.clipboard;

import android.content.ClipData;
import android.content.Context;
import android.hardware.graphics.common.Dataspace;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.sec.clipboard.IClipboardDataPasteEvent;
import android.sec.clipboard.IClipboardService;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public class SemClipboardManager {
  public static final String ACTION_ADD_CLIP =
      "com.samsung.android.content.clipboard.action.ADD_CLIP";

  @Deprecated(forRemoval = true, since = "13.0")
  public static final String ACTION_CLIPBOARD_CLOSED =
      "com.samsung.android.content.clipboard.action.CLIPBOARD_CLOSED";

  @Deprecated(forRemoval = true, since = "13.0")
  public static final String ACTION_CLIPBOARD_OPENED =
      "com.samsung.android.content.clipboard.action.CLIPBOARD_OPENED";

  @Deprecated(forRemoval = true, since = "13.0")
  public static final String ACTION_DISMISS_CLIPBOARD =
      "com.samsung.android.content.clipboard.action.DISMISS_CLIPBOARD";

  public static final String ACTION_INTRODUCE_EDGE =
      "com.samsung.android.content.clipboard.action.INTRODUCE_EDGE";
  public static final String ACTION_REMOVE_CLIP =
      "com.samsung.android.content.clipboard.action.REMOVE_CLIP";
  public static final int CLIPBOARD_TYPE_FILTER = 255;
  public static final String EXTRA_DARK_THEME = "darkTheme";
  public static final String EXTRA_EXTRA_PATH = "extra_path";
  public static final String EXTRA_NO_TOAST = "noToast";
  public static final String EXTRA_PATH = "path";
  public static final String EXTRA_TYPE = "type";
  private static final String KEY_DATA = "data";
  private static final String KEY_FILTER = "filter";
  private static final String TAG = "SemClipboardManager";
  private static IClipboardService mSemService = null;
  private Context mContext;
  private Handler mHandler;
  private final InnerOnClipboardEventListener mInnerOnClipboardEventServiceListener;
  private final int SUCCESS_SET_DATA = 0;
  private final int FAIL_SET_DATA = 1;
  private final int SUCCESS_AND_SAVE_BITMAP = 2;
  private final int PROTECTED_DATA_MAX = 3;
  private boolean mIsMaximumSize = false;
  private OnPasteListener mPasteListener = null;
  private final IClipboardDataPasteEvent.Stub mOnPasteServiceListener =
      new IClipboardDataPasteEvent
          .Stub() { // from class: com.samsung.android.content.clipboard.SemClipboardManager.1
        @Override // android.sec.clipboard.IClipboardDataPasteEvent
        public void onPaste(SemClipData data) {
          SemClipboardManager.this.requestPaste(data);
        }
      };
  private ArrayList<SemClipboardEventListener> mOnClipboardEventServiceListeners =
      new ArrayList<>();

  public interface OnPasteListener {
    void onPaste(SemClipData semClipData);
  }

  private static class InnerOnClipboardEventListener extends IOnClipboardEventListener.Stub {
    private final WeakReference<Handler> mWeakHandler;

    public InnerOnClipboardEventListener(Handler handler) {
      this.mWeakHandler = new WeakReference<>(handler);
    }

    @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
    public void onClipboardEvent(int event, SemClipData data) {
      WeakReference<Handler> weakReference = this.mWeakHandler;
      if (weakReference == null || weakReference.get() == null) {
        Log.secD(
            SemClipboardManager.TAG,
            "onClipboardEvent mWeakHandler is null. mWeakHandler=" + this.mWeakHandler);
        return;
      }
      Handler handler = this.mWeakHandler.get();
      Message message = new Message();
      Bundle bundle = new Bundle();
      bundle.putParcelable("data", data);
      message.what = event;
      message.setData(bundle);
      handler.sendMessage(message);
    }

    @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
    public void onUpdateFilter(int filter) {
      WeakReference<Handler> weakReference = this.mWeakHandler;
      if (weakReference == null || weakReference.get() == null) {
        Log.secD(
            SemClipboardManager.TAG,
            "onUpdateFilter mWeakHandler is null. mWeakHandler=" + this.mWeakHandler);
        return;
      }
      Handler handler = this.mWeakHandler.get();
      Message message = new Message();
      Bundle bundle = new Bundle();
      bundle.putInt(SemClipboardManager.KEY_FILTER, filter);
      message.what = 5;
      message.setData(bundle);
      handler.sendMessage(message);
    }
  }

  public class ClipboardEvent {
    public static final int CLIPS_REFRESH = 7;
    public static final int CLIP_ADDED = 1;
    public static final int CLIP_DUPLICATED = 8;
    public static final int CLIP_UPDATE_LOCK = 16;
    public static final int CLIP_UPDATE_TIMESTAMP = 256;
    public static final int FILTER_UPDATED = 5;

    private ClipboardEvent() {}
  }

  public static class Type {
    public static final int ALL = -1;
    public static final int HTML = 4;
    public static final int IMAGE = 2;
    public static final int INTENT = 8;
    public static final int NONE = 0;
    public static final int TEXT = 1;
    public static final int URI = 16;
    public static final int URI_LIST = 32;

    private Type() {}
  }

  public interface OnAddClipResultListener {
    void onFailure(int i);

    void onSuccess();

    public static class Error {
      public static final int REASON_DUPLICATED = 2;
      public static final int REASON_EMPTY_DATA = 3;
      public static final int REASON_NOT_ALLOWED_TO_USE = 4;
      public static final int REASON_UNKNOWN = 1;

      private Error() {}
    }
  }

  public SemClipboardManager(Context context) {
    this.mContext = context;
    this.mHandler =
        new Handler(
            this.mContext
                .getMainLooper()) { // from class:
                                    // com.samsung.android.content.clipboard.SemClipboardManager.2
          @Override // android.os.Handler
          public void handleMessage(Message msg) {
            SemClipboardManager.this.notifyEvent(msg);
          }
        };
    this.mInnerOnClipboardEventServiceListener = new InnerOnClipboardEventListener(this.mHandler);
  }

  private static IClipboardService getSemService() {
    IClipboardService iClipboardService = mSemService;
    if (iClipboardService != null) {
      return iClipboardService;
    }
    IClipboardService asInterface =
        IClipboardService.Stub.asInterface(
            ServiceManager.getService(Context.SEM_CLIPBOARD_SERVICE));
    mSemService = asInterface;
    if (asInterface == null) {
      Log.m60e(TAG, "Failed to get semclipboard service.");
    }
    return mSemService;
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public boolean isShowing() {
    Log.m59d(TAG, "deprecated isShowing, calling package : " + this.mContext.getOpPackageName());
    return false;
  }

  public void addClip(Context context, SemClipData data, OnAddClipResultListener listener) {
    if (!isEnabled("addClip")) {
      return;
    }
    setPrimarySemClip(data);
  }

  public int setDataWithoutNoti(Context context, SemClipData data) {
    setPrimarySemClip(data);
    return 0;
  }

  public int setDataWithoutSendingOrginalClipboard(Context context, SemClipData data) {
    setPrimarySemClip(data);
    return 0;
  }

  public void setPrimarySemClip(SemClipData semClip) {
    if (getSemService() != null) {
      Objects.requireNonNull(semClip);
      if (!makeFileDescriptor(semClip)) {
        Log.secE(TAG, "failed making file descriptor!");
        return;
      }
      try {
        getSemService()
            .setPrimarySemClip(
                semClip, this.mContext.getOpPackageName(), this.mContext.getUserId());
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in setPrimarySemClip, " + e.getMessage());
      }
      semClip.closeParcelFileDescriptor();
    }
  }

  public SemClipData getLatestClip(int type) {
    if (isEnabled("getLatestClip") && getSemService() != null) {
      try {
        return getSemService()
            .getPrimarySemClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in getLatestClip, " + e.getMessage());
      }
    }
    return null;
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public int getCount() {
    if (isEnabled("getCount") && getSemService() != null) {
      try {
        return getSemService()
                .hasPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getUserId())
            ? 1
            : 0;
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in getCount, " + e.getMessage());
      }
    }
    return -1;
  }

  public void registerClipboardEventListener(SemClipboardEventListener listener) {
    if (!isEnabled("registerClipboardEventListener")) {
      return;
    }
    if (getSemService() == null) {
      Log.secW(TAG, "registerClipboardUIInterface: Service is null.");
      return;
    }
    synchronized (this.mOnClipboardEventServiceListeners) {
      if (this.mOnClipboardEventServiceListeners.size() == 0) {
        try {
          getSemService()
              .addClipboardEventListener(
                  this.mInnerOnClipboardEventServiceListener, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
          throw e.rethrowFromSystemServer();
        }
      }
      if (listener != null && !this.mOnClipboardEventServiceListeners.contains(listener)) {
        this.mOnClipboardEventServiceListeners.add(listener);
      }
    }
  }

  public void unregisterClipboardEventListener(SemClipboardEventListener listener) {
    synchronized (this.mOnClipboardEventServiceListeners) {
      boolean removed = this.mOnClipboardEventServiceListeners.remove(listener);
      if (removed && this.mOnClipboardEventServiceListeners.size() == 0) {
        try {
          getSemService().removeClipboardEventListener(this.mInnerOnClipboardEventServiceListener);
        } catch (RemoteException e) {
          throw e.rethrowFromSystemServer();
        }
      }
    }
  }

  public void filterClip(int type, OnPasteListener listener) {
    if (isEnabled("filterClip") && getSemService() != null) {
      try {
        getSemService().updateFilter(type, this.mOnPasteServiceListener);
        Log.m61i(
            TAG,
            "updateFilter - Format("
                + type
                + "), "
                + listener
                + " by "
                + this.mContext.getOpPackageName());
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in filterClip, " + e.getMessage());
      }
      this.mPasteListener = listener;
    }
  }

  public boolean paste(ClipData data) {
    if (isEnabled("paste(ClipData)") && getSemService() != null) {
      try {
        return getSemService()
            .pasteClipData(data, this.mContext.getOpPackageName(), this.mContext.getUserId());
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in paste(ClipData), " + e.getMessage());
      }
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void requestPaste(SemClipData data) {
    OnPasteListener onPasteListener = this.mPasteListener;
    if (onPasteListener != null) {
      if (data != null) {
        onPasteListener.onPaste(data);
        return;
      } else {
        Log.secE(TAG, "clipdata is null");
        return;
      }
    }
    Log.secE(TAG, "no app clipboard listener!");
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public void showDialog() {
    Log.m59d(TAG, "deprecated showDialog, calling package : " + this.mContext.getOpPackageName());
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public void dismissDialog() {
    Log.m59d(
        TAG, "deprecated dismissDialog, calling package : " + this.mContext.getOpPackageName());
  }

  public boolean isEnabled() {
    if (getSemService() != null) {
      try {
        return getSemService().isEnabled(this.mContext.getUserId());
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in isEnabled, " + e.getMessage());
        return false;
      }
    }
    return false;
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public boolean paste(String id) {
    Log.m59d(
        TAG, "deprecated paste(String id), calling package : " + this.mContext.getOpPackageName());
    return false;
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public List<SemClipData> getClips() {
    if (!isEnabled("getClips")) {
      return null;
    }
    ArrayList<SemClipData> clipList = new ArrayList<>();
    if (getSemService() != null) {
      try {
        SemClipData data =
            getSemService()
                .getPrimarySemClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
        if (data != null) {
          clipList.add(data);
        }
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in getClips, " + e.getMessage());
      }
    }
    return clipList;
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public SemClipData getClip(String id) throws RemoteException {
    if (isEnabled("getClip") && getSemService() != null) {
      try {
        return getSemService()
            .getPrimarySemClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
      } catch (RemoteException e) {
        Log.m60e(TAG, "RemoteException in getClip, " + e.getMessage());
      }
    }
    return null;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  private boolean makeFileDescriptor(SemClipData data) {
    FileHelper fh = FileHelper.getInstance();
    switch (data.getClipType()) {
      case 2:
        SemImageClipData target = (SemImageClipData) data;
        String imgPath = target.getBitmapPath();
        if (!TextUtils.isEmpty(imgPath)) {
          File f = new File(imgPath);
          if (f.exists() && !f.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
            if (fh.checkFile(f)) {
              try {
                ParcelFileDescriptor pfd = ParcelFileDescriptor.open(f, Dataspace.RANGE_MASK);
                target.setParcelFileDescriptor(pfd);
              } catch (FileNotFoundException e) {
                Log.m60e(TAG, "makeFileDescriptor(1) Exception : " + e.getMessage());
                return false;
              }
            } else {
              Log.secD(TAG, "it's not file.");
              return false;
            }
          } else {
            Log.secD(TAG, "it's /data/semclipdata/.. path file");
            return true;
          }
        } else {
          Log.secD(TAG, "no bitmap file");
        }
        if (target.hasExtraData()) {
          String extraPath = target.getExtraDataPath();
          if (!TextUtils.isEmpty(extraPath)) {
            File f2 = new File(extraPath);
            if (fh.checkFile(f2)) {
              try {
                ParcelFileDescriptor pfd2 = ParcelFileDescriptor.open(f2, Dataspace.RANGE_MASK);
                target.setExtraParcelFileDescriptor(pfd2);
              } catch (FileNotFoundException e2) {
                Log.m60e(TAG, "makeFileDescriptor(2) Exception : " + e2.getMessage());
                return false;
              }
            } else {
              Log.secD(TAG, "it's not file. : " + f2.getAbsolutePath());
              return false;
            }
          }
        } else {
          Log.secD(TAG, "no extra bitmap file");
        }
        return true;
      case 4:
        SemHtmlClipData target2 = (SemHtmlClipData) data;
        String imgPath2 = target2.getThumbnailImagePath();
        if (TextUtils.isEmpty(imgPath2)) {
          boolean result = fh.setFirstImagePathFromHtmlData(target2);
          if (result) {
            imgPath2 = target2.getThumbnailImagePath();
          }
        }
        boolean result2 = TextUtils.isEmpty(imgPath2);
        if (!result2) {
          File f3 = new File(imgPath2);
          if (f3.exists()
              && !f3.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
            if (fh.checkFile(f3)) {
              try {
                ParcelFileDescriptor pfd3 = ParcelFileDescriptor.open(f3, Dataspace.RANGE_MASK);
                target2.setParcelFileDescriptor(pfd3);
              } catch (FileNotFoundException e3) {
                Log.m60e(TAG, "makeFileDescriptor(3) Exception : " + e3.getMessage());
                return false;
              }
            } else {
              Log.secD(TAG, "it's not file.");
              return false;
            }
          } else {
            Log.secD(TAG, "it's /data/semclipdata/.. path file");
            return true;
          }
        } else {
          Log.secD(TAG, "no first image file");
        }
        return true;
      case 16:
        SemUriClipData target3 = (SemUriClipData) data;
        String imgPath3 = target3.getThumbnailPath();
        if (TextUtils.isEmpty(imgPath3)) {
          boolean result3 = fh.setThumbnailImagePathFromUriData(target3);
          if (result3) {
            imgPath3 = target3.getThumbnailPath();
          }
        }
        boolean result4 = TextUtils.isEmpty(imgPath3);
        if (!result4) {
          File f4 = new File(imgPath3);
          if (f4.exists()
              && !f4.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
            if (fh.checkFile(f4)) {
              try {
                ParcelFileDescriptor pfd4 = ParcelFileDescriptor.open(f4, Dataspace.RANGE_MASK);
                target3.setParcelFileDescriptor(pfd4);
              } catch (FileNotFoundException e4) {
                Log.m60e(TAG, "makeFileDescriptor(4) Exception : " + e4.getMessage());
                return false;
              }
            } else {
              Log.secD(TAG, "it's not file.");
              return false;
            }
          } else {
            Log.secD(TAG, "it's /data/semclipdata/.. path file");
            return true;
          }
        } else {
          Log.secD(TAG, "no preview image file");
        }
        return true;
      default:
        return true;
    }
  }

  private boolean isEnabled(String func) {
    if (!isEnabled()) {
      Log.m61i(TAG, "not enabled! from " + func);
      return false;
    }
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void notifyEvent(Message msg) {
    switch (msg.what) {
      case 1:
      case 7:
      case 8:
      case 16:
      case 256:
        SemClipData data = null;
        Bundle bundle = msg.getData();
        if (bundle != null) {
          data = (SemClipData) bundle.getParcelable("data");
        }
        synchronized (this.mOnClipboardEventServiceListeners) {
          int N = this.mOnClipboardEventServiceListeners.size();
          if (N <= 0) {
            return;
          }
          for (Object obj : this.mOnClipboardEventServiceListeners.toArray()) {
            ((SemClipboardEventListener) obj).onClipboardUpdated(msg.what, data);
          }
          return;
        }
      case 5:
        int filter = 0;
        Bundle bundle2 = msg.getData();
        if (bundle2 != null) {
          filter = bundle2.getInt(KEY_FILTER);
        }
        synchronized (this.mOnClipboardEventServiceListeners) {
          int N2 = this.mOnClipboardEventServiceListeners.size();
          if (N2 <= 0) {
            return;
          }
          Object[] listeners = this.mOnClipboardEventServiceListeners.toArray();
          for (Object obj2 : listeners) {
            ((SemClipboardEventListener) obj2).onFilterUpdated(filter);
          }
          return;
        }
      default:
        return;
    }
  }
}
