package android.drm;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.samsung.android.os.SemDvfsManager;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    private native int _saveRights(int i, DrmRights drmRights, String str, String str2);

    private native void _setListeners(int i, Object obj);

    /* JADX INFO: Access modifiers changed from: private */
    public int getErrorType(int i) {
        return (i == 1 || i == 2 || i == 3) ? 2006 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getEventType(int i) {
        return (i == 1 || i == 2 || i == 3) ? 1002 : -1;
    }

    static {
        System.loadLibrary("drmframework_jni");
    }

    private class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x00a5 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x00ba A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:24:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r7) {
            /*
                r6 = this;
                java.util.HashMap r0 = new java.util.HashMap
                r0.<init>()
                int r1 = r7.what
                r2 = 1001(0x3e9, float:1.403E-42)
                r3 = 0
                if (r1 == r2) goto L75
                r2 = 1002(0x3ea, float:1.404E-42)
                if (r1 == r2) goto L26
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                java.lang.String r0 = "Unknown message type "
                r6.<init>(r0)
                int r7 = r7.what
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                java.lang.String r7 = "DrmManagerClient"
                android.util.Log.e(r7, r6)
                return
            L26:
                java.lang.Object r7 = r7.obj
                android.drm.DrmInfo r7 = (android.drm.DrmInfo) r7
                android.drm.DrmManagerClient r1 = android.drm.DrmManagerClient.this
                int r2 = android.drm.DrmManagerClient.m1209$$Nest$fgetmUniqueId(r1)
                android.drm.DrmInfoStatus r1 = android.drm.DrmManagerClient.m1210$$Nest$m_processDrmInfo(r1, r2, r7)
                java.lang.String r2 = "drm_info_status_object"
                r0.put(r2, r1)
                java.lang.String r2 = "drm_info_object"
                r0.put(r2, r7)
                if (r1 == 0) goto L59
                r2 = 1
                int r4 = r1.statusCode
                if (r2 != r4) goto L59
                android.drm.DrmEvent r7 = new android.drm.DrmEvent
                android.drm.DrmManagerClient r2 = android.drm.DrmManagerClient.this
                int r2 = android.drm.DrmManagerClient.m1209$$Nest$fgetmUniqueId(r2)
                android.drm.DrmManagerClient r4 = android.drm.DrmManagerClient.this
                int r1 = r1.infoType
                int r1 = android.drm.DrmManagerClient.m1213$$Nest$mgetEventType(r4, r1)
                r7.<init>(r2, r1, r3, r0)
                goto L8c
            L59:
                if (r1 == 0) goto L5e
                int r7 = r1.infoType
                goto L62
            L5e:
                int r7 = r7.getInfoType()
            L62:
                android.drm.DrmErrorEvent r1 = new android.drm.DrmErrorEvent
                android.drm.DrmManagerClient r2 = android.drm.DrmManagerClient.this
                int r2 = android.drm.DrmManagerClient.m1209$$Nest$fgetmUniqueId(r2)
                android.drm.DrmManagerClient r4 = android.drm.DrmManagerClient.this
                int r7 = android.drm.DrmManagerClient.m1212$$Nest$mgetErrorType(r4, r7)
                r1.<init>(r2, r7, r3, r0)
                r7 = r1
                goto L9d
            L75:
                android.drm.DrmManagerClient r7 = android.drm.DrmManagerClient.this
                int r0 = android.drm.DrmManagerClient.m1209$$Nest$fgetmUniqueId(r7)
                int r7 = android.drm.DrmManagerClient.m1211$$Nest$m_removeAllRights(r7, r0)
                if (r7 != 0) goto L90
                android.drm.DrmEvent r7 = new android.drm.DrmEvent
                android.drm.DrmManagerClient r0 = android.drm.DrmManagerClient.this
                int r0 = android.drm.DrmManagerClient.m1209$$Nest$fgetmUniqueId(r0)
                r7.<init>(r0, r2, r3)
            L8c:
                r5 = r3
                r3 = r7
                r7 = r5
                goto L9d
            L90:
                android.drm.DrmErrorEvent r7 = new android.drm.DrmErrorEvent
                android.drm.DrmManagerClient r0 = android.drm.DrmManagerClient.this
                int r0 = android.drm.DrmManagerClient.m1209$$Nest$fgetmUniqueId(r0)
                r1 = 2007(0x7d7, float:2.812E-42)
                r7.<init>(r0, r1, r3)
            L9d:
                android.drm.DrmManagerClient r0 = android.drm.DrmManagerClient.this
                android.drm.DrmManagerClient$OnEventListener r0 = android.drm.DrmManagerClient.m1207$$Nest$fgetmOnEventListener(r0)
                if (r0 == 0) goto Lb2
                if (r3 == 0) goto Lb2
                android.drm.DrmManagerClient r0 = android.drm.DrmManagerClient.this
                android.drm.DrmManagerClient$OnEventListener r0 = android.drm.DrmManagerClient.m1207$$Nest$fgetmOnEventListener(r0)
                android.drm.DrmManagerClient r1 = android.drm.DrmManagerClient.this
                r0.onEvent(r1, r3)
            Lb2:
                android.drm.DrmManagerClient r0 = android.drm.DrmManagerClient.this
                android.drm.DrmManagerClient$OnErrorListener r0 = android.drm.DrmManagerClient.m1206$$Nest$fgetmOnErrorListener(r0)
                if (r0 == 0) goto Lc7
                if (r7 == 0) goto Lc7
                android.drm.DrmManagerClient r0 = android.drm.DrmManagerClient.this
                android.drm.DrmManagerClient$OnErrorListener r0 = android.drm.DrmManagerClient.m1206$$Nest$fgetmOnErrorListener(r0)
                android.drm.DrmManagerClient r6 = android.drm.DrmManagerClient.this
                r0.onError(r6, r7)
            Lc7:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.drm.DrmManagerClient.EventHandler.handleMessage(android.os.Message):void");
        }
    }

    public static void notify(Object obj, int i, int i2, String str) {
        InfoHandler infoHandler;
        DrmManagerClient drmManagerClient = (DrmManagerClient) ((WeakReference) obj).get();
        if (drmManagerClient == null || (infoHandler = drmManagerClient.mInfoHandler) == null) {
            return;
        }
        drmManagerClient.mInfoHandler.sendMessage(infoHandler.obtainMessage(1, i, i2, str));
    }

    private class InfoHandler extends Handler {
        public static final int INFO_EVENT_TYPE = 1;

        public InfoHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            DrmInfoEvent drmInfoEvent;
            DrmErrorEvent drmErrorEvent;
            if (message.what == 1) {
                int i = message.arg1;
                int i2 = message.arg2;
                String obj = message.obj.toString();
                DrmInfoEvent drmInfoEvent2 = null;
                switch (i2) {
                    case 1:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        drmInfoEvent = new DrmInfoEvent(i, i2, obj);
                        DrmInfoEvent drmInfoEvent3 = drmInfoEvent;
                        drmErrorEvent = null;
                        drmInfoEvent2 = drmInfoEvent3;
                        break;
                    case 2:
                        try {
                            DrmUtils.removeFile(obj);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        drmInfoEvent = new DrmInfoEvent(i, i2, obj);
                        DrmInfoEvent drmInfoEvent32 = drmInfoEvent;
                        drmErrorEvent = null;
                        drmInfoEvent2 = drmInfoEvent32;
                        break;
                    default:
                        drmErrorEvent = new DrmErrorEvent(i, i2, obj);
                        break;
                }
                if (DrmManagerClient.this.mOnInfoListener != null && drmInfoEvent2 != null) {
                    DrmManagerClient.this.mOnInfoListener.onInfo(DrmManagerClient.this, drmInfoEvent2);
                }
                if (DrmManagerClient.this.mOnErrorListener == null || drmErrorEvent == null) {
                    return;
                }
                DrmManagerClient.this.mOnErrorListener.onError(DrmManagerClient.this, drmErrorEvent);
                return;
            }
            Log.e(DrmManagerClient.TAG, "Unknown message type " + message.what);
        }
    }

    public DrmManagerClient(Context context) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
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

    public synchronized void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
        if (onInfoListener != null) {
            createListeners();
        }
    }

    public synchronized void setOnEventListener(OnEventListener onEventListener) {
        this.mOnEventListener = onEventListener;
        if (onEventListener != null) {
            createListeners();
        }
    }

    public synchronized void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
        if (onErrorListener != null) {
            createListeners();
        }
    }

    public String[] getAvailableDrmEngines() {
        DrmSupportInfo[] _getAllSupportInfo = _getAllSupportInfo(this.mUniqueId);
        ArrayList arrayList = new ArrayList();
        for (DrmSupportInfo drmSupportInfo : _getAllSupportInfo) {
            arrayList.add(drmSupportInfo.getDescriprition());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public Collection<DrmSupportInfo> getAvailableDrmSupportInfo() {
        return Arrays.asList(_getAllSupportInfo(this.mUniqueId));
    }

    public ContentValues getConstraints(String str, int i) {
        if (str == null || str.equals("") || !DrmStore.Action.isValid(i)) {
            throw new IllegalArgumentException("Given usage or path is invalid/null");
        }
        return _getConstraints(this.mUniqueId, str, i);
    }

    public ContentValues getMetadata(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("Given path is invalid/null");
        }
        return _getMetadata(this.mUniqueId, str);
    }

    public ContentValues getConstraints(Uri uri, int i) {
        if (uri == null || Uri.EMPTY == uri) {
            throw new IllegalArgumentException("Uri should be non null");
        }
        return getConstraints(convertUriToPath(uri), i);
    }

    public ContentValues getMetadata(Uri uri) {
        if (uri == null || Uri.EMPTY == uri) {
            throw new IllegalArgumentException("Uri should be non null");
        }
        return getMetadata(convertUriToPath(uri));
    }

    public int saveRights(DrmRights drmRights, String str, String str2) throws IOException {
        if (drmRights == null || !drmRights.isValid()) {
            throw new IllegalArgumentException("Given drmRights or contentPath is not valid");
        }
        if (str != null && !str.equals("")) {
            DrmUtils.writeToFile(str, drmRights.getData());
        }
        return _saveRights(this.mUniqueId, drmRights, str, str2);
    }

    public void installDrmEngine(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("Given engineFilePath: " + str + "is not valid");
        }
        _installDrmEngine(this.mUniqueId, str);
    }

    private void setDvfsBooster(boolean z) {
        if (!z || this.isAcquired) {
            return;
        }
        if (this.mDvfsHelper == null) {
            Log.i(TAG, "mDvfsHelper initialize");
            SemDvfsManager createInstance = SemDvfsManager.createInstance(this.mContext, "DRM_SECURE_PLAY", 21);
            this.mDvfsHintManager = createInstance;
            if (createInstance != null) {
                createInstance.setHint(1400);
            }
        }
        SemDvfsManager semDvfsManager = this.mDvfsHintManager;
        if (semDvfsManager != null) {
            semDvfsManager.acquire();
            Log.i(TAG, "mDvfsHintManager acquired ");
            this.isAcquired = true;
        }
    }

    private void releaseDvfsBooster() {
        SemDvfsManager semDvfsManager = this.mDvfsHintManager;
        if (semDvfsManager != null) {
            semDvfsManager.release();
            this.mDvfsHintManager = null;
            this.isAcquired = false;
            Log.v(TAG, "releaseDRMDVFS: done:");
        }
    }

    public boolean toggleCPUBoost(int i, boolean z) {
        if (i <= 0) {
            return false;
        }
        if (z) {
            Log.e(TAG, "SECURE_PLAYBACK_START");
            setDvfsBooster(true);
            return false;
        }
        Log.e(TAG, "SECURE_PLAYBACK_STOP");
        releaseDvfsBooster();
        return false;
    }

    public boolean canHandle(String str, String str2) {
        if ((str == null || str.equals("")) && (str2 == null || str2.equals(""))) {
            throw new IllegalArgumentException("Path or the mimetype should be non null");
        }
        return _canHandle(this.mUniqueId, str, str2);
    }

    public boolean canHandle(Uri uri, String str) {
        if ((uri == null || Uri.EMPTY == uri) && (str == null || str.equals(""))) {
            throw new IllegalArgumentException("Uri or the mimetype should be non null");
        }
        return canHandle(convertUriToPath(uri), str);
    }

    public int processDrmInfo(DrmInfo drmInfo) {
        if (drmInfo == null || !drmInfo.isValid()) {
            throw new IllegalArgumentException("Given drmInfo is invalid/null");
        }
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler == null) {
            return ERROR_UNKNOWN;
        }
        if (this.mEventHandler.sendMessage(eventHandler.obtainMessage(1002, drmInfo))) {
            return 0;
        }
        return ERROR_UNKNOWN;
    }

    public DrmInfo acquireDrmInfo(DrmInfoRequest drmInfoRequest) {
        if (drmInfoRequest == null || !drmInfoRequest.isValid()) {
            throw new IllegalArgumentException("Given drmInfoRequest is invalid/null");
        }
        return _acquireDrmInfo(this.mUniqueId, drmInfoRequest);
    }

    public int acquireRights(DrmInfoRequest drmInfoRequest) {
        DrmInfo acquireDrmInfo = acquireDrmInfo(drmInfoRequest);
        return acquireDrmInfo == null ? ERROR_UNKNOWN : processDrmInfo(acquireDrmInfo);
    }

    public int getDrmObjectType(String str, String str2) {
        if ((str == null || str.equals("")) && (str2 == null || str2.equals(""))) {
            throw new IllegalArgumentException("Path or the mimetype should be non null");
        }
        return _getDrmObjectType(this.mUniqueId, str, str2);
    }

    public int getDrmObjectType(Uri uri, String str) {
        String str2 = "";
        if ((uri == null || Uri.EMPTY == uri) && (str == null || str.equals(""))) {
            throw new IllegalArgumentException("Uri or the mimetype should be non null");
        }
        try {
            str2 = convertUriToPath(uri);
        } catch (Exception unused) {
            Log.w(TAG, "Given Uri could not be found in media store");
        }
        return getDrmObjectType(str2, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        if (r2 != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002a, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0039, code lost:
    
        if (r2 == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getOriginalMimeType(java.lang.String r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L3d
            java.lang.String r0 = ""
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L3d
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L38
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L38
            boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L38
            if (r2 == 0) goto L20
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L38
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L38
            java.io.FileDescriptor r1 = r2.getFD()     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L39
            goto L22
        L20:
            r1 = r0
            r2 = r1
        L22:
            int r3 = r4.mUniqueId     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L39
            java.lang.String r0 = r4._getOriginalMimeType(r3, r5, r1)     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L39
            if (r2 == 0) goto L3c
        L2a:
            r2.close()     // Catch: java.io.IOException -> L3c
            goto L3c
        L2e:
            r4 = move-exception
            r0 = r2
            goto L32
        L31:
            r4 = move-exception
        L32:
            if (r0 == 0) goto L37
            r0.close()     // Catch: java.io.IOException -> L37
        L37:
            throw r4
        L38:
            r2 = r0
        L39:
            if (r2 == 0) goto L3c
            goto L2a
        L3c:
            return r0
        L3d:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Given path should be non null"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.drm.DrmManagerClient.getOriginalMimeType(java.lang.String):java.lang.String");
    }

    public String getOriginalMimeType(Uri uri) {
        if (uri == null || Uri.EMPTY == uri) {
            throw new IllegalArgumentException("Given uri is not valid");
        }
        return getOriginalMimeType(convertUriToPath(uri));
    }

    public int checkRightsStatus(String str) {
        return checkRightsStatus(str, 0);
    }

    public int checkRightsStatus(Uri uri) {
        if (uri == null || Uri.EMPTY == uri) {
            throw new IllegalArgumentException("Given uri is not valid");
        }
        return checkRightsStatus(convertUriToPath(uri));
    }

    public int checkRightsStatus(String str, int i) {
        if (str == null || str.equals("") || !DrmStore.Action.isValid(i)) {
            throw new IllegalArgumentException("Given path or action is not valid");
        }
        return _checkRightsStatus(this.mUniqueId, str, i);
    }

    public int checkRightsStatus(Uri uri, int i) {
        if (uri == null || Uri.EMPTY == uri) {
            throw new IllegalArgumentException("Given uri is not valid");
        }
        return checkRightsStatus(convertUriToPath(uri), i);
    }

    public int removeRights(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("Given path should be non null");
        }
        return _removeRights(this.mUniqueId, str);
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
        if (this.mEventHandler.sendMessage(eventHandler.obtainMessage(1001))) {
            return 0;
        }
        return ERROR_UNKNOWN;
    }

    public int openConvertSession(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("Path or the mimeType should be non null");
        }
        return _openConvertSession(this.mUniqueId, str);
    }

    public DrmConvertedStatus convertData(int i, byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            throw new IllegalArgumentException("Given inputData should be non null");
        }
        return _convertData(this.mUniqueId, i, bArr);
    }

    public DrmConvertedStatus closeConvertSession(int i) {
        return _closeConvertSession(this.mUniqueId, i);
    }

    private String convertUriToPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("") || scheme.equals("file")) {
            return uri.getPath();
        }
        if (scheme.equals(IntentFilter.SCHEME_HTTP) || scheme.equals(IntentFilter.SCHEME_HTTPS)) {
            return uri.toString();
        }
        if (scheme.equals("content")) {
            try {
                try {
                    Cursor query = this.mContext.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
                    if (query == null || query.getCount() == 0 || !query.moveToFirst()) {
                        throw new IllegalArgumentException("Given Uri could not be found in media store");
                    }
                    String string = query.getString(query.getColumnIndexOrThrow("_data"));
                    if (query != null) {
                        query.close();
                    }
                    return string;
                } catch (SQLiteException unused) {
                    throw new IllegalArgumentException("Given Uri is not formatted in a way so that it can be found in media store.");
                }
            } finally {
            }
        } else {
            throw new IllegalArgumentException("Given Uri scheme is not supported");
        }
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
