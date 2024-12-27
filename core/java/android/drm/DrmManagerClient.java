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

import dalvik.system.CloseGuard;

import com.samsung.android.os.SemDvfsManager;

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
public class DrmManagerClient implements AutoCloseable {
    private static final int ACTION_PROCESS_DRM_INFO = 1002;
    private static final int ACTION_REMOVE_ALL_RIGHTS = 1001;
    static final int DRM_SECURE_PLAY = 1400;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_UNKNOWN = -2000;
    public static final int INVALID_SESSION = -1;
    private static final String TAG = "DrmManagerClient";
    private static final boolean isLogEnabled = false;
    private Context mContext;
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
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private String DRM_DISPLAYPORT_ENABLE = "/sys/class/dp_sec/dp_drm";
    private SemDvfsManager mDvfsHelper = null;
    private boolean isAcquired = false;

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
                    if (DrmManagerClient.this._removeAllRights(DrmManagerClient.this.mUniqueId)
                            == 0) {
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
                            Log.e(DrmManagerClient.TAG, "Exception the file " + e.toString());
                        }
                    }
                    DrmInfoStatus status =
                            DrmManagerClient.this._processDrmInfo(
                                    DrmManagerClient.this.mUniqueId, drmInfo);
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
                    Log.e(DrmManagerClient.TAG, "Unknown message type " + msg.what);
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
        DrmManagerClient instance = (DrmManagerClient) ((WeakReference) thisReference).get();
        if (instance != null && instance.mInfoHandler != null) {
            Message m = instance.mInfoHandler.obtainMessage(1, uniqueId, infoType, message);
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
                        DrmManagerClient.this.mOnErrorListener.onError(
                                DrmManagerClient.this, error);
                        break;
                    }
                    break;
                default:
                    Log.e(DrmManagerClient.TAG, "Unknown message type " + msg.what);
                    break;
            }
        }
    }

    private int _checkFDSupporting(String path) {
        String[] OmaExtensions = {".dcf"};
        String[] PlayReadyExtensions = {
            ".pyv", ".pya", ".wmv", ".wma", ".asf", ".eny", ".pye", ".ismv", ".isma", ".mp4",
            ".fdsa"
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
        this.mContext = context;
        createEventThreads();
        this.mUniqueId = _initialize();
        this.mCloseGuard.open("release");
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
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
                } catch (Throwable th) {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                Log.e(TAG, "Exception the file " + e2.toString());
                if (fis != null) {
                    fis.close();
                }
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
                Log.i(TAG, "mDvfsHelper initialize");
                this.mDvfsHintManager =
                        SemDvfsManager.createInstance(this.mContext, "DRM_SECURE_PLAY", 21);
                if (this.mDvfsHintManager != null) {
                    this.mDvfsHintManager.setHint(1400);
                }
            }
            if (this.mDvfsHintManager != null) {
                this.mDvfsHintManager.acquire();
                Log.i(TAG, "mDvfsHintManager acquired ");
                this.isAcquired = true;
            }
        }
    }

    private void releaseDvfsBooster() {
        if (this.mDvfsHintManager != null) {
            this.mDvfsHintManager.release();
            this.mDvfsHintManager = null;
            this.isAcquired = false;
            Log.v(TAG, "releaseDRMDVFS: done:");
        }
    }

    public void saveDevID() {
        Log.e(TAG, "saveDevID : Checking ");
        String roSerial = Build.getSerial();
        if (!_saveSRL(roSerial)) {
            Log.e(TAG, "SRL Write save failed");
        }
        if (this.mContext != null
                && this.mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == 0) {
            TelephonyManager tmgr = (TelephonyManager) this.mContext.getSystemService("phone");
            String deviceID = tmgr.getDeviceId();
            if (!_saveIMEI(deviceID)) {
                Log.e(TAG, "devID save failed");
                return;
            }
            return;
        }
        Log.e(TAG, "Permission denied:You do not have READ_PHONE_STATE permission .");
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
            Log.e(TAG, "SECURE_PLAYBACK_START");
            setDvfsBooster(true);
            return false;
        }
        Log.e(TAG, "SECURE_PLAYBACK_STOP");
        releaseDvfsBooster();
        return false;
    }

    public void dpDRM(int value) {
        if (value == 1) {
            if (sysfsWrite(this.DRM_DISPLAYPORT_ENABLE, 1)) {
                Log.i(TAG, "DRM_DISPLAYPORT_ENABLE");
                return;
            } else {
                Log.i(TAG, "DRM_DISPLAYPORT_ENABLE failed. Error can be ignored");
                return;
            }
        }
        if (sysfsWrite(this.DRM_DISPLAYPORT_ENABLE, 0)) {
            Log.i(TAG, "DRM_DISPLAYPORT_DISABLE");
        } else {
            Log.i(TAG, "DRM_DISPLAYPORT_DISABLE failed. Error can be ignored");
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
                    Log.e(TAG, "Exception the file " + e.toString());
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
        if (this.mEventHandler == null) {
            return ERROR_UNKNOWN;
        }
        Message msg = this.mEventHandler.obtainMessage(1002, drmInfo);
        int result = this.mEventHandler.sendMessage(msg) ? 0 : -2000;
        return result;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x024c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.drm.DrmInfo acquireDrmInfo(android.drm.DrmInfoRequest r19) {
        /*
            Method dump skipped, instructions count: 605
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.drm.DrmManagerClient.acquireDrmInfo(android.drm.DrmInfoRequest):android.drm.DrmInfo");
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
                    Log.e(TAG, "Exception the file " + e.toString());
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

    public int getDrmObjectType(Uri uri, String mimeType) {
        if ((uri == null || Uri.EMPTY == uri) && (mimeType == null || mimeType.equals(""))) {
            throw new IllegalArgumentException("Uri or the mimetype should be non null");
        }
        String path = "";
        try {
            path = convertUriToPath(uri);
        } catch (Exception e) {
            Log.w(TAG, "Given Uri could not be found in media store");
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
                } catch (Throwable th) {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                Log.e(TAG, "Exception the file " + e2.toString());
                if (fis != null) {
                    fis.close();
                }
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
                } catch (Throwable th) {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
            }
        } catch (IOException e3) {
            Log.e(TAG, "Exception the file " + e3.toString());
            if (fis != null) {
                fis.close();
            }
        }
        return result;
    }

    public int removeRights(Uri uri) {
        if (uri == null || Uri.EMPTY == uri) {
            throw new IllegalArgumentException("Given uri is not valid");
        }
        return removeRights(convertUriToPath(uri));
    }

    public int removeAllRights() {
        if (this.mEventHandler == null) {
            return ERROR_UNKNOWN;
        }
        Message msg = this.mEventHandler.obtainMessage(1001);
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
                    Log.i(
                            TAG,
                            "getErrorType return TYPE_PROCESS_DRM_INFO_FAILED becauseof widevine or"
                                    + " STATUS_ERROR");
                    return 2006;
                }
                Log.i(TAG, "getErrorType infoStatus.statusCode: " + infoStatus.statusCode);
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
                    cursor =
                            this.mContext
                                    .getContentResolver()
                                    .query(uri, projection, null, null, null);
                    if (cursor == null || cursor.getCount() == 0 || !cursor.moveToFirst()) {
                        throw new IllegalArgumentException(
                                "Given Uri could not be found in media store");
                    }
                    int pathIndex = cursor.getColumnIndexOrThrow("_data");
                    String path3 = cursor.getString(pathIndex);
                } catch (SQLiteException e) {
                    throw new IllegalArgumentException(
                            "Given Uri is not formatted in a way so that it can be found in media"
                                    + " store.");
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
            this.mInfoThread = new HandlerThread("DrmManagerClient.InfoHandler");
            this.mInfoThread.start();
            this.mInfoHandler = new InfoHandler(this.mInfoThread.getLooper());
            this.mEventThread = new HandlerThread("DrmManagerClient.EventHandler");
            this.mEventThread.start();
            this.mEventHandler = new EventHandler(this.mEventThread.getLooper());
        }
    }

    private void createListeners() {
        _setListeners(this.mUniqueId, new WeakReference(this));
    }
}
