package android.drm;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.android.mms.util.DownloadDrmHelper;
import com.samsung.android.os.SemDvfsManager;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
/* loaded from: classes.dex */
public class DrmManagerClient implements AutoCloseable {
  private static final int ACTION_PROCESS_DRM_INFO = 1002;
  private static final int ACTION_REMOVE_ALL_RIGHTS = 1001;
  static final int DRM_SECURE_PLAY = 1400;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_UNKNOWN = -2000;
  public static final int INVALID_SESSION = -1;
  private static final String TAG = "DrmManagerClient";
  private static final boolean isLogEnabled = false;
  private String DRM_DISPLAYPORT_ENABLE;
  private boolean isAcquired;
  private final CloseGuard mCloseGuard;
  private final AtomicBoolean mClosed = new AtomicBoolean();
  private Context mContext;
  private SemDvfsManager mDvfsHelper;
  SemDvfsManager mDvfsHintManager;
  private EventHandler mEventHandler;
  HandlerThread mEventThread;
  private InfoHandler mInfoHandler;
  HandlerThread mInfoThread;
  private long mNativeContext;
  private OnErrorListener mOnErrorListener;
  private OnEventListener mOnEventListener;
  private OnInfoListener mOnInfoListener;
  private volatile boolean mReleased;
  private int mUniqueId;

  public interface OnErrorListener {
    void onError(DrmManagerClient drmManagerClient, DrmErrorEvent drmErrorEvent);
  }

  public interface OnEventListener {
    void onEvent(DrmManagerClient drmManagerClient, DrmEvent drmEvent);
  }

  public interface OnInfoListener {
    void onInfo(DrmManagerClient drmManagerClient, DrmInfoEvent drmInfoEvent);
  }

  private native DrmInfo _acquireDrmInfo(int i, DrmInfoRequest drmInfoRequest);

  private native boolean _canHandle(int i, String str, String str2);

  private native int _checkRightsStatus(int i, String str, int i2);

  private native DrmConvertedStatus _closeConvertSession(int i, int i2);

  private native DrmConvertedStatus _convertData(int i, int i2, byte[] bArr);

  private native DrmSupportInfo[] _getAllSupportInfo(int i);

  private native ContentValues _getConstraints(int i, String str, int i2);

  private native int _getDrmObjectType(int i, String str, String str2);

  private native ContentValues _getMetadata(int i, String str);

  private native String _getOriginalMimeType(int i, String str, FileDescriptor fileDescriptor);

  private native int _initialize();

  private native void _installDrmEngine(int i, String str);

  private native int _openConvertSession(int i, String str);

  /* JADX INFO: Access modifiers changed from: private */
  public native DrmInfoStatus _processDrmInfo(int i, DrmInfo drmInfo);

  private native void _release(int i);

  /* JADX INFO: Access modifiers changed from: private */
  public native int _removeAllRights(int i);

  private native int _removeRights(int i, String str);

  private native boolean _saveIMEI(String str);

  private native int _saveRights(int i, DrmRights drmRights, String str, String str2);

  private native boolean _saveSRL(String str);

  private native int _setFD(int i, FileDescriptor fileDescriptor);

  private native void _setListeners(int i, Object obj);

  static {
    System.loadLibrary("drmframework_jni");
  }

  private class EventHandler extends Handler {
    public EventHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      DrmEvent event = null;
      DrmErrorEvent error = null;
      HashMap<String, Object> attributes = new HashMap<>();
      switch (msg.what) {
        case 1001:
          DrmManagerClient drmManagerClient = DrmManagerClient.this;
          if (drmManagerClient._removeAllRights(drmManagerClient.mUniqueId) == 0) {
            event = new DrmEvent(DrmManagerClient.this.mUniqueId, 1001, null);
            break;
          } else {
            error = new DrmErrorEvent(DrmManagerClient.this.mUniqueId, 2007, null);
            break;
          }
        case 1002:
          DrmInfo drmInfo = (DrmInfo) msg.obj;
          String filepath = (String) drmInfo.get(DrmInfoRequest.SEM_DRM_PATH);
          FileInputStream fis = null;
          if (filepath != null && !filepath.isEmpty()) {
            try {
              fis = new FileInputStream(filepath);
              FileDescriptor fd = fis.getFD();
              String FD = DrmManagerClient.this.setFD(fd);
              drmInfo.put("FileDescriptorKey", FD);
            } catch (IOException e) {
              Log.m96e(DrmManagerClient.TAG, "Exception the file " + e.toString());
            }
          }
          DrmManagerClient drmManagerClient2 = DrmManagerClient.this;
          DrmInfoStatus status =
              drmManagerClient2._processDrmInfo(drmManagerClient2.mUniqueId, drmInfo);
          attributes.put(DrmEvent.DRM_INFO_STATUS_OBJECT, status);
          attributes.put(DrmEvent.DRM_INFO_OBJECT, drmInfo);
          if (fis != null) {
            try {
              fis.close();
            } catch (IOException e2) {
            }
          }
          if (status != null && 1 == status.statusCode) {
            event =
                new DrmEvent(
                    DrmManagerClient.this.mUniqueId,
                    DrmManagerClient.this.getEventType(status.infoType),
                    null,
                    attributes);
            break;
          } else {
            int infoType = status != null ? status.infoType : drmInfo.getInfoType();
            if (status != null && status.data.getData() != null) {
              byte[] bytes = status.data.getData();
              String url = new String(bytes, StandardCharsets.UTF_8);
              error =
                  new DrmErrorEvent(
                      DrmManagerClient.this.mUniqueId,
                      DrmManagerClient.this.getErrorType(infoType, status),
                      url,
                      attributes);
              break;
            } else {
              error =
                  new DrmErrorEvent(
                      DrmManagerClient.this.mUniqueId,
                      DrmManagerClient.this.getErrorType(infoType, status),
                      null,
                      attributes);
              break;
            }
          }
          break;
        default:
          Log.m96e(DrmManagerClient.TAG, "Unknown message type " + msg.what);
          return;
      }
      if (DrmManagerClient.this.mOnEventListener != null && event != null) {
        DrmManagerClient.this.mOnEventListener.onEvent(DrmManagerClient.this, event);
      }
      if (DrmManagerClient.this.mOnErrorListener != null && error != null) {
        DrmManagerClient.this.mOnErrorListener.onError(DrmManagerClient.this, error);
      }
    }
  }

  public static void notify(Object thisReference, int uniqueId, int infoType, String message) {
    InfoHandler infoHandler;
    DrmManagerClient instance = (DrmManagerClient) ((WeakReference) thisReference).get();
    if (instance != null && (infoHandler = instance.mInfoHandler) != null) {
      Message m = infoHandler.obtainMessage(1, uniqueId, infoType, message);
      instance.mInfoHandler.sendMessage(m);
    }
  }

  private class InfoHandler extends Handler {
    public static final int INFO_EVENT_TYPE = 1;

    public InfoHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      DrmInfoEvent info = null;
      DrmErrorEvent error = null;
      switch (msg.what) {
        case 1:
          int uniqueId = msg.arg1;
          int infoType = msg.arg2;
          String message = msg.obj.toString();
          switch (infoType) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
              info = new DrmInfoEvent(uniqueId, infoType, message);
              break;
            case 2:
              try {
                DrmUtils.removeFile(message);
              } catch (IOException e) {
                e.printStackTrace();
              }
              info = new DrmInfoEvent(uniqueId, infoType, message);
              break;
            default:
              error = new DrmErrorEvent(uniqueId, infoType, message);
              break;
          }
          if (DrmManagerClient.this.mOnInfoListener != null && info != null) {
            DrmManagerClient.this.mOnInfoListener.onInfo(DrmManagerClient.this, info);
          }
          if (DrmManagerClient.this.mOnErrorListener != null && error != null) {
            DrmManagerClient.this.mOnErrorListener.onError(DrmManagerClient.this, error);
            break;
          }
          break;
        default:
          Log.m96e(DrmManagerClient.TAG, "Unknown message type " + msg.what);
          break;
      }
    }
  }

  private int _checkFDSupporting(String path) {
    String[] OmaExtensions = {".dcf"};
    String[] PlayReadyExtensions = {
      ".pyv", ".pya", ".wmv", ".wma", ".asf", ".eny", ".pye", ".ismv", ".isma", ".mp4", ".fdsa"
    };
    String[] DivxExtensions = {".avi", "divx"};
    if (path == null) {
      return 0;
    }
    for (String str : OmaExtensions) {
      if (path.toLowerCase().endsWith(str)) {
        return 1;
      }
    }
    for (String str2 : PlayReadyExtensions) {
      if (path.toLowerCase().endsWith(str2)) {
        return 2;
      }
    }
    for (String str3 : DivxExtensions) {
      if (path.toLowerCase().endsWith(str3)) {
        return 3;
      }
    }
    return 0;
  }

  public DrmManagerClient(Context context) {
    CloseGuard closeGuard = CloseGuard.get();
    this.mCloseGuard = closeGuard;
    this.DRM_DISPLAYPORT_ENABLE = "/sys/class/dp_sec/dp_drm";
    this.mDvfsHelper = null;
    this.isAcquired = false;
    this.mContext = context;
    createEventThreads();
    this.mUniqueId = _initialize();
    closeGuard.open("release");
  }

  protected void finalize() throws Throwable {
    try {
      CloseGuard closeGuard = this.mCloseGuard;
      if (closeGuard != null) {
        closeGuard.warnIfOpen();
      }
      close();
    } finally {
      super.finalize();
    }
  }

  @Override // java.lang.AutoCloseable
  public void close() {
    this.mCloseGuard.close();
    if (this.mClosed.compareAndSet(false, true)) {
      if (this.mEventHandler != null) {
        this.mEventThread.quit();
        this.mEventThread = null;
      }
      if (this.mInfoHandler != null) {
        this.mInfoThread.quit();
        this.mInfoThread = null;
      }
      this.mEventHandler = null;
      this.mInfoHandler = null;
      this.mOnEventListener = null;
      this.mOnInfoListener = null;
      this.mOnErrorListener = null;
      _release(this.mUniqueId);
    }
  }

  @Deprecated
  public void release() {
    close();
  }

  public synchronized void setOnInfoListener(OnInfoListener infoListener) {
    this.mOnInfoListener = infoListener;
    if (infoListener != null) {
      createListeners();
    }
  }

  public synchronized void setOnEventListener(OnEventListener eventListener) {
    this.mOnEventListener = eventListener;
    if (eventListener != null) {
      createListeners();
    }
  }

  public synchronized void setOnErrorListener(OnErrorListener errorListener) {
    this.mOnErrorListener = errorListener;
    if (errorListener != null) {
      createListeners();
    }
  }

  public String[] getAvailableDrmEngines() {
    DrmSupportInfo[] supportInfos = _getAllSupportInfo(this.mUniqueId);
    ArrayList<String> descriptions = new ArrayList<>();
    for (DrmSupportInfo drmSupportInfo : supportInfos) {
      descriptions.add(drmSupportInfo.getDescriprition());
    }
    int i = descriptions.size();
    String[] drmEngines = new String[i];
    return (String[]) descriptions.toArray(drmEngines);
  }

  public Collection<DrmSupportInfo> getAvailableDrmSupportInfo() {
    return Arrays.asList(_getAllSupportInfo(this.mUniqueId));
  }

  public ContentValues getConstraints(String path, int action) {
    if (path == null || path.equals("") || !DrmStore.Action.isValid(action)) {
      throw new IllegalArgumentException("Given usage or path is invalid/null");
    }
    if (_checkFDSupporting(path) == 0) {
      return _getConstraints(this.mUniqueId, path, action);
    }
    ContentValues contentValues = null;
    FileInputStream fis = null;
    try {
      try {
        try {
          fis = new FileInputStream(path);
          FileDescriptor fd = fis.getFD();
          String FD = setFD(fd);
          contentValues = _getConstraints(this.mUniqueId, FD, action);
          fis.close();
        } catch (IOException e) {
          Log.m96e(TAG, "Exception the file " + e.toString());
          if (fis != null) {
            fis.close();
          }
        }
      } catch (Throwable th) {
        if (fis != null) {
          try {
            fis.close();
          } catch (IOException e2) {
          }
        }
        throw th;
      }
    } catch (IOException e3) {
    }
    return contentValues;
  }

  public ContentValues getMetadata(String path) {
    if (path == null || path.equals("")) {
      throw new IllegalArgumentException("Given path is invalid/null");
    }
    return _getMetadata(this.mUniqueId, path);
  }

  public ContentValues getConstraints(Uri uri, int action) {
    if (uri == null || Uri.EMPTY == uri) {
      throw new IllegalArgumentException("Uri should be non null");
    }
    return getConstraints(convertUriToPath(uri), action);
  }

  public ContentValues getMetadata(Uri uri) {
    if (uri == null || Uri.EMPTY == uri) {
      throw new IllegalArgumentException("Uri should be non null");
    }
    return getMetadata(convertUriToPath(uri));
  }

  public int saveRights(DrmRights drmRights, String rightsPath, String contentPath)
      throws IOException {
    if (drmRights == null || !drmRights.isValid()) {
      throw new IllegalArgumentException("Given drmRights or contentPath is not valid");
    }
    if (rightsPath != null && !rightsPath.equals("")) {
      DrmUtils.writeToFile(rightsPath, drmRights.getData());
    }
    return _saveRights(this.mUniqueId, drmRights, rightsPath, contentPath);
  }

  public void installDrmEngine(String engineFilePath) {
    if (engineFilePath == null || engineFilePath.equals("")) {
      throw new IllegalArgumentException(
          "Given engineFilePath: " + engineFilePath + "is not valid");
    }
    _installDrmEngine(this.mUniqueId, engineFilePath);
  }

  public String setFD(FileDescriptor fd) {
    int FD = _setFD(this.mUniqueId, fd);
    return "FileDescriptor[" + FD + NavigationBarInflaterView.SIZE_MOD_END;
  }

  private void setDvfsBooster(boolean state) {
    if (state && !this.isAcquired) {
      int[] iArr = {0};
      if (this.mDvfsHelper == null) {
        Log.m98i(TAG, "mDvfsHelper initialize");
        SemDvfsManager createInstance =
            SemDvfsManager.createInstance(this.mContext, "DRM_SECURE_PLAY", 21);
        this.mDvfsHintManager = createInstance;
        if (createInstance != null) {
          createInstance.setHint(1400);
        }
      }
      SemDvfsManager semDvfsManager = this.mDvfsHintManager;
      if (semDvfsManager != null) {
        semDvfsManager.acquire();
        Log.m98i(TAG, "mDvfsHintManager acquired ");
        this.isAcquired = true;
      }
    }
  }

  private void releaseDvfsBooster() {
    SemDvfsManager semDvfsManager = this.mDvfsHintManager;
    if (semDvfsManager != null) {
      semDvfsManager.release();
      this.mDvfsHintManager = null;
      this.isAcquired = false;
      Log.m100v(TAG, "releaseDRMDVFS: done:");
    }
  }

  public void saveDevID() {
    Log.m96e(TAG, "saveDevID : Checking ");
    String roSerial = Build.getSerial();
    if (!_saveSRL(roSerial)) {
      Log.m96e(TAG, "SRL Write save failed");
    }
    Context context = this.mContext;
    if (context != null && context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == 0) {
      TelephonyManager tmgr = (TelephonyManager) this.mContext.getSystemService("phone");
      String deviceID = tmgr.getDeviceId();
      if (!_saveIMEI(deviceID)) {
        Log.m96e(TAG, "devID save failed");
        return;
      }
      return;
    }
    Log.m96e(TAG, "Permission denied:You do not have READ_PHONE_STATE permission .");
  }

  private static boolean sysfsWrite(String sysfs, int value) {
    FileOutputStream out = null;
    File myfile = new File(sysfs);
    if (!myfile.exists()) {
      return false;
    }
    try {
      try {
        FileOutputStream out2 = new FileOutputStream(myfile);
        out2.write(Integer.toString(value).getBytes(Charset.forName("UTF-8")));
        out2.close();
        return true;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
    } catch (IOException e2) {
      e2.printStackTrace();
      try {
        out.close();
      } catch (Exception err) {
        err.printStackTrace();
      }
      return false;
    }
  }

  public boolean toggleCPUBoost(int pid, boolean boostON) {
    if (pid <= 0) {
      return false;
    }
    if (boostON) {
      Log.m96e(TAG, "SECURE_PLAYBACK_START");
      setDvfsBooster(true);
      return false;
    }
    Log.m96e(TAG, "SECURE_PLAYBACK_STOP");
    releaseDvfsBooster();
    return false;
  }

  public void dpDRM(int value) {
    if (value == 1) {
      if (sysfsWrite(this.DRM_DISPLAYPORT_ENABLE, 1)) {
        Log.m98i(TAG, "DRM_DISPLAYPORT_ENABLE");
        return;
      } else {
        Log.m98i(TAG, "DRM_DISPLAYPORT_ENABLE failed. Error can be ignored");
        return;
      }
    }
    if (sysfsWrite(this.DRM_DISPLAYPORT_ENABLE, 0)) {
      Log.m98i(TAG, "DRM_DISPLAYPORT_DISABLE");
    } else {
      Log.m98i(TAG, "DRM_DISPLAYPORT_DISABLE failed. Error can be ignored");
    }
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x003a -> B:14:0x0062). Please report as a decompilation issue!!! */
  public boolean canHandle(String path, String mimeType) {
    if ((path == null || path.equals("")) && (mimeType == null || mimeType.equals(""))) {
      throw new IllegalArgumentException("Path or the mimetype should be non null");
    }
    if (path == null || _checkFDSupporting(path) == 0) {
      return _canHandle(this.mUniqueId, path, mimeType);
    }
    boolean result = false;
    FileInputStream fis = null;
    try {
      try {
        try {
          fis = new FileInputStream(path);
          FileDescriptor fd = fis.getFD();
          String FD = setFD(fd);
          result = _canHandle(this.mUniqueId, FD, mimeType);
          fis.close();
        } catch (IOException e) {
        }
      } catch (IOException e2) {
        Log.m96e(TAG, "Exception the file " + e2.toString());
        if (fis != null) {
          fis.close();
        }
      }
      return result;
    } catch (Throwable th) {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e3) {
        }
      }
      throw th;
    }
  }

  public boolean canHandle(Uri uri, String mimeType) {
    if ((uri == null || Uri.EMPTY == uri) && (mimeType == null || mimeType.equals(""))) {
      throw new IllegalArgumentException("Uri or the mimetype should be non null");
    }
    return canHandle(convertUriToPath(uri), mimeType);
  }

  public int processDrmInfo(DrmInfo drmInfo) {
    if (drmInfo == null || !drmInfo.isValid()) {
      throw new IllegalArgumentException("Given drmInfo is invalid/null");
    }
    EventHandler eventHandler = this.mEventHandler;
    if (eventHandler == null) {
      return ERROR_UNKNOWN;
    }
    Message msg = eventHandler.obtainMessage(1002, drmInfo);
    int result = this.mEventHandler.sendMessage(msg) ? 0 : -2000;
    return result;
  }

  /* JADX WARN: Removed duplicated region for block: B:39:0x0163  */
  /* JADX WARN: Removed duplicated region for block: B:83:0x024c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public DrmInfo acquireDrmInfo(DrmInfoRequest drmInfoRequest) {
    FileInputStream fis;
    FileInputStream fis2;
    if (drmInfoRequest == null || !drmInfoRequest.isValid()) {
      throw new IllegalArgumentException("Given drmInfoRequest is invalid/null");
    }
    FileOutputStream fos = null;
    String filepath = (String) drmInfoRequest.get(DrmInfoRequest.SEM_DRM_PATH);
    FileInputStream fis3 = null;
    if (drmInfoRequest.getInfoType() == 7) {
      if (filepath != null && !filepath.isEmpty()) {
        if (filepath.toLowerCase().endsWith(DownloadDrmHelper.EXTENSION_DRM_MESSAGE)) {
          try {
            fis3 = new FileInputStream(filepath);
            FileDescriptor DmFd = fis3.getFD();
            String FD = setFD(DmFd);
            drmInfoRequest.put("FileDescriptorKey", FD);
            fos =
                new FileOutputStream(
                    filepath.replace(DownloadDrmHelper.EXTENSION_DRM_MESSAGE, ".dcf"));
            FileDescriptor DcfFd = fos.getFD();
            String FD2 = setFD(DcfFd);
            drmInfoRequest.put("OutFileDescriptorKey", FD2);
          } catch (IOException e) {
            Log.m96e(TAG, "Exception the file " + e.toString());
          }
        } else if (filepath.toLowerCase().endsWith(DownloadDrmHelper.EXTENSION_INTERNAL_FWDL)) {
          try {
            fis3 = new FileInputStream(filepath);
            FileDescriptor DmFd2 = fis3.getFD();
            String FD3 = setFD(DmFd2);
            drmInfoRequest.put("FileDescriptorKey", FD3);
            fos =
                new FileOutputStream(
                    filepath.replace(DownloadDrmHelper.EXTENSION_INTERNAL_FWDL, ".dcf"));
            FileDescriptor DcfFd2 = fos.getFD();
            String FD4 = setFD(DcfFd2);
            drmInfoRequest.put("OutFileDescriptorKey", FD4);
          } catch (IOException e2) {
            Log.m96e(TAG, "Exception the file " + e2.toString());
          }
        } else {
          Log.m98i(TAG, "file extention is not dm or fl");
          try {
            fis2 = new FileInputStream(filepath);
            try {
              FileDescriptor Fd = fis2.getFD();
              String FD5 = setFD(Fd);
              drmInfoRequest.put("FileDescriptorKey", FD5);
            } catch (IOException e3) {
              e = e3;
              Log.m96e(TAG, "Exception the file " + e.toString());
              fis3 = fis2;
              DrmInfo lDrmInfo = _acquireDrmInfo(this.mUniqueId, drmInfoRequest);
              if (drmInfoRequest.getInfoType() == 7) {}
              return lDrmInfo;
            }
          } catch (IOException e4) {
            e = e4;
            fis2 = null;
          }
          fis3 = fis2;
        }
      }
    } else if (filepath != null && !filepath.isEmpty()) {
      try {
        fis = new FileInputStream(filepath);
      } catch (IOException e5) {
        e = e5;
        fis = null;
      }
      try {
        FileDescriptor Fd2 = fis.getFD();
        String FD6 = setFD(Fd2);
        drmInfoRequest.put("FileDescriptorKey", FD6);
        fis3 = fis;
      } catch (IOException e6) {
        e = e6;
        Log.m96e(TAG, "Exception the file " + e.toString());
        fis3 = fis;
        DrmInfo lDrmInfo2 = _acquireDrmInfo(this.mUniqueId, drmInfoRequest);
        if (drmInfoRequest.getInfoType() == 7) {}
        return lDrmInfo2;
      }
    }
    DrmInfo lDrmInfo22 = _acquireDrmInfo(this.mUniqueId, drmInfoRequest);
    if (drmInfoRequest.getInfoType() == 7) {
      Log.m96e(TAG, "_acquireDrmInfo TYPE_CONVERT_DRM_FILE");
      if (fis3 != null) {
        try {
          fis3.close();
        } catch (IOException e7) {
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e8) {
        }
      }
      Log.m96e(TAG, "_acquireDrmInfo TYPE_CONVERT_DRM_FILE " + lDrmInfo22.get("status"));
      if (lDrmInfo22.get("status").equals("success")) {
        Log.m96e(TAG, "DM file delete DrmInfoRequest.SEM_SUCCESS");
        if (filepath != null && !filepath.isEmpty()) {
          File uf = new File(filepath);
          if (uf.exists()) {
            boolean delFlag = uf.delete();
            if (!delFlag) {
              Log.m96e(TAG, "DM file delete fail");
            }
          }
        }
      }
      if (lDrmInfo22.get("status").equals(DrmInfoRequest.SEM_FAIL)) {
        Log.m96e(TAG, "dcf file delete DrmInfoRequest.SEM_FAIL");
        if (filepath != null && !filepath.isEmpty()) {
          if (filepath.toLowerCase().endsWith(DownloadDrmHelper.EXTENSION_DRM_MESSAGE)) {
            String Filename = filepath.replace(DownloadDrmHelper.EXTENSION_DRM_MESSAGE, ".dcf");
            Log.m96e(TAG, "dcf file delete DrmInfoRequest.SEM_FAIL" + Filename);
            File uf2 = new File(Filename);
            if (uf2.exists()) {
              boolean delFlag2 = uf2.delete();
              if (!delFlag2) {
                Log.m96e(TAG, "DM file delete fail");
              }
            }
          } else if (filepath.toLowerCase().endsWith(DownloadDrmHelper.EXTENSION_INTERNAL_FWDL)) {
            String Filename2 = filepath.replace(DownloadDrmHelper.EXTENSION_INTERNAL_FWDL, ".dcf");
            Log.m96e(TAG, "dcf file delete DrmInfoRequest.SEM_FAIL" + Filename2);
            File uf3 = new File(Filename2);
            if (uf3.exists()) {
              boolean delFlag3 = uf3.delete();
              if (!delFlag3) {
                Log.m96e(TAG, "DM file delete fail");
              }
            }
          }
        }
      }
    } else if (fis3 != null) {
      try {
        fis3.close();
      } catch (IOException e9) {
      }
    }
    return lDrmInfo22;
  }

  public int acquireRights(DrmInfoRequest drmInfoRequest) {
    DrmInfo drmInfo = acquireDrmInfo(drmInfoRequest);
    if (drmInfo == null) {
      return ERROR_UNKNOWN;
    }
    return processDrmInfo(drmInfo);
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x003a -> B:14:0x0062). Please report as a decompilation issue!!! */
  public int getDrmObjectType(String path, String mimeType) {
    if ((path == null || path.equals("")) && (mimeType == null || mimeType.equals(""))) {
      throw new IllegalArgumentException("Path or the mimetype should be non null");
    }
    if (path == null || _checkFDSupporting(path) == 0) {
      return _getDrmObjectType(this.mUniqueId, path, mimeType);
    }
    int result = 0;
    FileInputStream fis = null;
    try {
      try {
        try {
          fis = new FileInputStream(path);
          FileDescriptor fd = fis.getFD();
          String FD = setFD(fd);
          result = _getDrmObjectType(this.mUniqueId, FD, mimeType);
          fis.close();
        } catch (IOException e) {
        }
      } catch (IOException e2) {
        Log.m96e(TAG, "Exception the file " + e2.toString());
        if (fis != null) {
          fis.close();
        }
      }
      return result;
    } catch (Throwable th) {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e3) {
        }
      }
      throw th;
    }
  }

  public int getDrmObjectType(Uri uri, String mimeType) {
    if ((uri == null || Uri.EMPTY == uri) && (mimeType == null || mimeType.equals(""))) {
      throw new IllegalArgumentException("Uri or the mimetype should be non null");
    }
    String path = "";
    try {
      path = convertUriToPath(uri);
    } catch (Exception e) {
      Log.m102w(TAG, "Given Uri could not be found in media store");
    }
    return getDrmObjectType(path, mimeType);
  }

  public String getOriginalMimeType(String path) {
    if (path == null || path.equals("")) {
      throw new IllegalArgumentException("Given path should be non null");
    }
    String mime = null;
    FileInputStream is = null;
    FileDescriptor fd = null;
    try {
      try {
        File file = new File(path);
        if (file.exists()) {
          is = new FileInputStream(file);
          fd = is.getFD();
        }
        mime = _getOriginalMimeType(this.mUniqueId, path, fd);
        if (mime.equals("")
            && path.toLowerCase().endsWith(DownloadDrmHelper.EXTENSION_INTERNAL_FWDL)) {
          mime = "unsupported/drm.mimetype";
          Log.m98i(TAG, "No supported OMA Plugin found. Update mime as : " + mime);
        }
        if (is != null) {
          is.close();
        }
      } catch (IOException e) {
        if (is != null) {
          is.close();
        }
      } catch (Throwable th) {
        if (is != null) {
          try {
            is.close();
          } catch (IOException e2) {
          }
        }
        throw th;
      }
    } catch (IOException e3) {
    }
    return mime;
  }

  public String getOriginalMimeType(Uri uri) {
    if (uri == null || Uri.EMPTY == uri) {
      throw new IllegalArgumentException("Given uri is not valid");
    }
    return getOriginalMimeType(convertUriToPath(uri));
  }

  public int checkRightsStatus(String path) {
    return checkRightsStatus(path, 0);
  }

  public int checkRightsStatus(Uri uri) {
    if (uri == null || Uri.EMPTY == uri) {
      throw new IllegalArgumentException("Given uri is not valid");
    }
    return checkRightsStatus(convertUriToPath(uri));
  }

  public int checkRightsStatus(String path, int action) {
    if (path == null || path.equals("") || !DrmStore.Action.isValid(action)) {
      throw new IllegalArgumentException("Given path or action is not valid");
    }
    if (_checkFDSupporting(path) == 0) {
      return _checkRightsStatus(this.mUniqueId, path, action);
    }
    int result = 0;
    FileInputStream fis = null;
    try {
      try {
        try {
          fis = new FileInputStream(path);
          FileDescriptor fd = fis.getFD();
          String FD = setFD(fd);
          result = _checkRightsStatus(this.mUniqueId, FD, action);
          fis.close();
        } catch (IOException e) {
          Log.m96e(TAG, "Exception the file " + e.toString());
          if (fis != null) {
            fis.close();
          }
        }
      } catch (Throwable th) {
        if (fis != null) {
          try {
            fis.close();
          } catch (IOException e2) {
          }
        }
        throw th;
      }
    } catch (IOException e3) {
    }
    return result;
  }

  public int checkRightsStatus(Uri uri, int action) {
    if (uri == null || Uri.EMPTY == uri) {
      throw new IllegalArgumentException("Given uri is not valid");
    }
    return checkRightsStatus(convertUriToPath(uri), action);
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0036 -> B:14:0x005e). Please report as a decompilation issue!!! */
  public int removeRights(String path) {
    if (path == null || path.equals("")) {
      throw new IllegalArgumentException("Given path should be non null");
    }
    if (_checkFDSupporting(path) == 0) {
      return _removeRights(this.mUniqueId, path);
    }
    int result = 0;
    FileInputStream fis = null;
    try {
      try {
        try {
          fis = new FileInputStream(path);
          FileDescriptor fd = fis.getFD();
          String FD = setFD(fd);
          result = _removeRights(this.mUniqueId, FD);
          fis.close();
        } catch (IOException e) {
          Log.m96e(TAG, "Exception the file " + e.toString());
          if (fis != null) {
            fis.close();
          }
        }
      } catch (IOException e2) {
      }
      return result;
    } catch (Throwable th) {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e3) {
        }
      }
      throw th;
    }
  }

  public int removeRights(Uri uri) {
    if (uri == null || Uri.EMPTY == uri) {
      throw new IllegalArgumentException("Given uri is not valid");
    }
    return removeRights(convertUriToPath(uri));
  }

  public int removeAllRights() {
    EventHandler eventHandler = this.mEventHandler;
    if (eventHandler == null) {
      return ERROR_UNKNOWN;
    }
    Message msg = eventHandler.obtainMessage(1001);
    int result = this.mEventHandler.sendMessage(msg) ? 0 : -2000;
    return result;
  }

  public int openConvertSession(String mimeType) {
    if (mimeType == null || mimeType.equals("")) {
      throw new IllegalArgumentException("Path or the mimeType should be non null");
    }
    return _openConvertSession(this.mUniqueId, mimeType);
  }

  public DrmConvertedStatus convertData(int convertId, byte[] inputData) {
    if (inputData == null || inputData.length <= 0) {
      throw new IllegalArgumentException("Given inputData should be non null");
    }
    return _convertData(this.mUniqueId, convertId, inputData);
  }

  public DrmConvertedStatus closeConvertSession(int convertId) {
    return _closeConvertSession(this.mUniqueId, convertId);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getEventType(int infoType) {
    switch (infoType) {
      case 1:
      case 2:
      case 3:
        return 1002;
      default:
        return -1;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getErrorType(int infoType, DrmInfoStatus infoStatus) {
    switch (infoType) {
      case 1:
      case 2:
      case 3:
        if (infoStatus == null
            || infoStatus.mimeType.contains("video/wvm")
            || infoStatus.statusCode == 2) {
          Log.m98i(
              TAG,
              "getErrorType return TYPE_PROCESS_DRM_INFO_FAILED becauseof widevine or"
                  + " STATUS_ERROR");
          return 2006;
        }
        Log.m98i(TAG, "getErrorType infoStatus.statusCode: " + infoStatus.statusCode);
        int error = infoStatus.statusCode;
        return error;
      default:
        return -1;
    }
  }

  private String convertUriToPath(Uri uri) {
    if (uri == null) {
      return null;
    }
    String scheme = uri.getScheme();
    if (scheme == null || scheme.equals("") || scheme.equals("file")) {
      String path = uri.getPath();
      return path;
    }
    if (scheme.equals(IntentFilter.SCHEME_HTTP) || scheme.equals(IntentFilter.SCHEME_HTTPS)) {
      String path2 = uri.toString();
      return path2;
    }
    if (scheme.equals("content")) {
      String[] projection = {"_data"};
      Cursor cursor = null;
      try {
        try {
          cursor = this.mContext.getContentResolver().query(uri, projection, null, null, null);
          if (cursor == null || cursor.getCount() == 0 || !cursor.moveToFirst()) {
            throw new IllegalArgumentException("Given Uri could not be found in media store");
          }
          int pathIndex = cursor.getColumnIndexOrThrow("_data");
          String path3 = cursor.getString(pathIndex);
        } catch (SQLiteException e) {
          throw new IllegalArgumentException(
              "Given Uri is not formatted in a way so that it can be found in media store.");
        }
      } finally {
        if (cursor != null) {
          cursor.close();
        }
      }
    }
    throw new IllegalArgumentException("Given Uri scheme is not supported");
  }

  private void createEventThreads() {
    if (this.mEventHandler == null && this.mInfoHandler == null) {
      HandlerThread handlerThread = new HandlerThread("DrmManagerClient.InfoHandler");
      this.mInfoThread = handlerThread;
      handlerThread.start();
      this.mInfoHandler = new InfoHandler(this.mInfoThread.getLooper());
      HandlerThread handlerThread2 = new HandlerThread("DrmManagerClient.EventHandler");
      this.mEventThread = handlerThread2;
      handlerThread2.start();
      this.mEventHandler = new EventHandler(this.mEventThread.getLooper());
    }
  }

  private void createListeners() {
    _setListeners(this.mUniqueId, new WeakReference(this));
  }
}
