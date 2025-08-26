package android.telephony;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.DownloadStatusListener;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.InternalDownloadProgressListener;
import android.telephony.mbms.InternalDownloadSessionCallback;
import android.telephony.mbms.InternalDownloadStatusListener;
import android.telephony.mbms.MbmsDownloadReceiver;
import android.telephony.mbms.MbmsDownloadSessionCallback;
import android.telephony.mbms.MbmsTempFileProvider;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.vendor.IMbmsDownloadService;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class MbmsDownloadSession implements AutoCloseable {
    public static final String DEFAULT_TOP_LEVEL_TEMP_DIRECTORY = "androidMbmsTempFileRoot";
    private static final String DESTINATION_SANITY_CHECK_FILE_NAME = "destinationSanityCheckFile";
    public static final String EXTRA_MBMS_COMPLETED_FILE_URI = "android.telephony.extra.MBMS_COMPLETED_FILE_URI";
    public static final String EXTRA_MBMS_DOWNLOAD_REQUEST = "android.telephony.extra.MBMS_DOWNLOAD_REQUEST";
    public static final String EXTRA_MBMS_DOWNLOAD_RESULT = "android.telephony.extra.MBMS_DOWNLOAD_RESULT";
    public static final String EXTRA_MBMS_FILE_INFO = "android.telephony.extra.MBMS_FILE_INFO";
    private static final String LOG_TAG = "MbmsDownloadSession";
    private static final int MAX_SERVICE_ANNOUNCEMENT_SIZE = 10240;

    @SystemApi
    public static final String MBMS_DOWNLOAD_SERVICE_ACTION = "android.telephony.action.EmbmsDownload";
    public static final String MBMS_DOWNLOAD_SERVICE_OVERRIDE_METADATA = "mbms-download-service-override";
    public static final int RESULT_CANCELLED = 2;
    public static final int RESULT_DOWNLOAD_FAILURE = 6;
    public static final int RESULT_EXPIRED = 3;
    public static final int RESULT_FILE_ROOT_UNREACHABLE = 8;
    public static final int RESULT_IO_ERROR = 4;
    public static final int RESULT_OUT_OF_STORAGE = 7;
    public static final int RESULT_SERVICE_ID_NOT_DEFINED = 5;
    public static final int RESULT_SUCCESSFUL = 1;
    public static final int STATUS_ACTIVELY_DOWNLOADING = 1;
    public static final int STATUS_PENDING_DOWNLOAD = 2;
    public static final int STATUS_PENDING_DOWNLOAD_WINDOW = 4;
    public static final int STATUS_PENDING_REPAIR = 3;
    public static final int STATUS_UNKNOWN = 0;
    private static AtomicBoolean sIsInitialized = new AtomicBoolean(false);
    private final Context mContext;
    private final InternalDownloadSessionCallback mInternalCallback;
    private ServiceConnection mServiceConnection;
    private int mSubscriptionId;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.MbmsDownloadSession.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MbmsDownloadSession.this.sendErrorToApp(3, "Received death notification");
        }
    };
    private AtomicReference<IMbmsDownloadService> mService = new AtomicReference<>(null);
    private final Map<DownloadStatusListener, InternalDownloadStatusListener> mInternalDownloadStatusListeners = new HashMap();
    private final Map<DownloadProgressListener, InternalDownloadProgressListener> mInternalDownloadProgressListeners = new HashMap();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadResultCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadStatus {
    }

    public static int getMaximumServiceAnnouncementSize() {
        return 10240;
    }

    private MbmsDownloadSession(Context context, Executor executor, int i, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        this.mSubscriptionId = -1;
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mInternalCallback = new InternalDownloadSessionCallback(mbmsDownloadSessionCallback, executor);
    }

    public static MbmsDownloadSession create(Context context, Executor executor, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        return create(context, executor, SubscriptionManager.getDefaultSubscriptionId(), mbmsDownloadSessionCallback);
    }

    public static MbmsDownloadSession create(Context context, Executor executor, int i, final MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        if (!sIsInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("Cannot have two active instances");
        }
        MbmsDownloadSession mbmsDownloadSession = new MbmsDownloadSession(context, executor, i, mbmsDownloadSessionCallback);
        final int iBindAndInitialize = mbmsDownloadSession.bindAndInitialize();
        if (iBindAndInitialize == 0) {
            return mbmsDownloadSession;
        }
        sIsInitialized.set(false);
        executor.execute(new Runnable() { // from class: android.telephony.MbmsDownloadSession.2
            @Override // java.lang.Runnable
            public void run() {
                mbmsDownloadSessionCallback.onError(iBindAndInitialize, null);
            }
        });
        return null;
    }

    private int bindAndInitialize() {
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: android.telephony.MbmsDownloadSession.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IMbmsDownloadService iMbmsDownloadServiceAsInterface = IMbmsDownloadService.Stub.asInterface(iBinder);
                try {
                    int iInitialize = iMbmsDownloadServiceAsInterface.initialize(MbmsDownloadSession.this.mSubscriptionId, MbmsDownloadSession.this.mInternalCallback);
                    if (iInitialize == -1) {
                        MbmsDownloadSession.this.close();
                        throw new IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (iInitialize != 0) {
                        MbmsDownloadSession.this.sendErrorToApp(iInitialize, "Error returned during initialization");
                        MbmsDownloadSession.sIsInitialized.set(false);
                        return;
                    }
                    try {
                        iMbmsDownloadServiceAsInterface.asBinder().linkToDeath(MbmsDownloadSession.this.mDeathRecipient, 0);
                        MbmsDownloadSession.this.mService.set(iMbmsDownloadServiceAsInterface);
                    } catch (RemoteException unused) {
                        MbmsDownloadSession.this.sendErrorToApp(3, "Middleware lost during initialization");
                        MbmsDownloadSession.sIsInitialized.set(false);
                    }
                } catch (RemoteException unused2) {
                    Log.e(MbmsDownloadSession.LOG_TAG, "Service died before initialization");
                    MbmsDownloadSession.sIsInitialized.set(false);
                } catch (RuntimeException e) {
                    Log.e(MbmsDownloadSession.LOG_TAG, "Runtime exception during initialization");
                    MbmsDownloadSession.this.sendErrorToApp(103, e.toString());
                    MbmsDownloadSession.sIsInitialized.set(false);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.w(MbmsDownloadSession.LOG_TAG, "bindAndInitialize: Remote service disconnected");
                MbmsDownloadSession.sIsInitialized.set(false);
                MbmsDownloadSession.this.mService.set(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Log.w(MbmsDownloadSession.LOG_TAG, "bindAndInitialize: Remote service returned null");
                MbmsDownloadSession.this.sendErrorToApp(3, "Middleware service binding returned null");
                MbmsDownloadSession.sIsInitialized.set(false);
                MbmsDownloadSession.this.mService.set(null);
                MbmsDownloadSession.this.mContext.unbindService(this);
            }
        };
        this.mServiceConnection = serviceConnection;
        return MbmsUtils.startBinding(this.mContext, MBMS_DOWNLOAD_SERVICE_ACTION, serviceConnection);
    }

    public void requestUpdateFileServices(List<String> list) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            int iRequestUpdateFileServices = iMbmsDownloadService.requestUpdateFileServices(this.mSubscriptionId, list);
            if (iRequestUpdateFileServices == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iRequestUpdateFileServices != 0) {
                sendErrorToApp(iRequestUpdateFileServices, null);
            }
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void addServiceAnnouncement(byte[] bArr) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        if (bArr.length > 10240) {
            throw new IllegalArgumentException("File too large");
        }
        try {
            int iAddServiceAnnouncement = iMbmsDownloadService.addServiceAnnouncement(this.mSubscriptionId, bArr);
            if (iAddServiceAnnouncement == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iAddServiceAnnouncement != 0) {
                sendErrorToApp(iAddServiceAnnouncement, null);
            }
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void setTempFileRootDirectory(File file) throws IOException {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            validateTempFileRootSanity(file);
            try {
                String canonicalPath = file.getCanonicalPath();
                try {
                    int tempFileRootDirectory = iMbmsDownloadService.setTempFileRootDirectory(this.mSubscriptionId, canonicalPath);
                    if (tempFileRootDirectory == -1) {
                        close();
                        throw new IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (tempFileRootDirectory != 0) {
                        sendErrorToApp(tempFileRootDirectory, null);
                    } else {
                        this.mContext.getSharedPreferences(MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_FILE_NAME, 0).edit().putString(MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_NAME, canonicalPath).apply();
                    }
                } catch (RemoteException unused) {
                    this.mService.set(null);
                    sIsInitialized.set(false);
                    sendErrorToApp(3, null);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to canonicalize the provided path: " + e);
            }
        } catch (IOException unused2) {
            throw new IllegalStateException("Got IOException checking directory sanity");
        }
    }

    private void validateTempFileRootSanity(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException("Provided directory does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Provided File is not a directory");
        }
        String canonicalPath = file.getCanonicalPath();
        if (this.mContext.getDataDir().getCanonicalPath().equals(canonicalPath)) {
            throw new IllegalArgumentException("Temp file root cannot be your data dir");
        }
        if (this.mContext.getCacheDir().getCanonicalPath().equals(canonicalPath)) {
            throw new IllegalArgumentException("Temp file root cannot be your cache dir");
        }
        if (this.mContext.getFilesDir().getCanonicalPath().equals(canonicalPath)) {
            throw new IllegalArgumentException("Temp file root cannot be your files dir");
        }
    }

    public File getTempFileRootDirectory() {
        String string = this.mContext.getSharedPreferences(MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_FILE_NAME, 0).getString(MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_NAME, null);
        if (string != null) {
            return new File(string);
        }
        return null;
    }

    public void download(DownloadRequest downloadRequest) throws IOException {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        if (this.mContext.getSharedPreferences(MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_FILE_NAME, 0).getString(MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_NAME, null) == null) {
            File file = new File(this.mContext.getFilesDir(), DEFAULT_TOP_LEVEL_TEMP_DIRECTORY);
            file.mkdirs();
            setTempFileRootDirectory(file);
        }
        checkDownloadRequestDestination(downloadRequest);
        try {
            int iDownload = iMbmsDownloadService.download(downloadRequest);
            if (iDownload == 0) {
                writeDownloadRequestToken(downloadRequest);
            } else {
                if (iDownload == -1) {
                    close();
                    throw new IllegalStateException("Middleware must not return an unknown error code");
                }
                sendErrorToApp(iDownload, null);
            }
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public List<DownloadRequest> listPendingDownloads() {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            return iMbmsDownloadService.listPendingDownloads(this.mSubscriptionId);
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
            return Collections.EMPTY_LIST;
        }
    }

    public void addStatusListener(DownloadRequest downloadRequest, Executor executor, DownloadStatusListener downloadStatusListener) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        InternalDownloadStatusListener internalDownloadStatusListener = new InternalDownloadStatusListener(downloadStatusListener, executor);
        try {
            int iAddStatusListener = iMbmsDownloadService.addStatusListener(downloadRequest, internalDownloadStatusListener);
            if (iAddStatusListener == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iAddStatusListener == 0) {
                this.mInternalDownloadStatusListeners.put(downloadStatusListener, internalDownloadStatusListener);
            } else {
                if (iAddStatusListener == 402) {
                    throw new IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(iAddStatusListener, null);
            }
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void removeStatusListener(DownloadRequest downloadRequest, DownloadStatusListener downloadStatusListener) {
        try {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null) {
                throw new IllegalStateException("Middleware not yet bound");
            }
            InternalDownloadStatusListener internalDownloadStatusListener = this.mInternalDownloadStatusListeners.get(downloadStatusListener);
            if (internalDownloadStatusListener == null) {
                throw new IllegalArgumentException("Provided listener was never registered");
            }
            try {
                int iRemoveStatusListener = iMbmsDownloadService.removeStatusListener(downloadRequest, internalDownloadStatusListener);
                if (iRemoveStatusListener == -1) {
                    close();
                    throw new IllegalStateException("Middleware must not return an unknown error code");
                }
                if (iRemoveStatusListener == 0) {
                    InternalDownloadStatusListener internalDownloadStatusListenerRemove = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
                    if (internalDownloadStatusListenerRemove != null) {
                        internalDownloadStatusListenerRemove.stop();
                        return;
                    }
                    return;
                }
                if (iRemoveStatusListener == 402) {
                    throw new IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(iRemoveStatusListener, null);
                InternalDownloadStatusListener internalDownloadStatusListenerRemove2 = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
                if (internalDownloadStatusListenerRemove2 != null) {
                    internalDownloadStatusListenerRemove2.stop();
                }
            } catch (RemoteException unused) {
                this.mService.set(null);
                sIsInitialized.set(false);
                sendErrorToApp(3, null);
                InternalDownloadStatusListener internalDownloadStatusListenerRemove3 = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
                if (internalDownloadStatusListenerRemove3 != null) {
                    internalDownloadStatusListenerRemove3.stop();
                }
            }
        } catch (Throwable th) {
            InternalDownloadStatusListener internalDownloadStatusListenerRemove4 = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
            if (internalDownloadStatusListenerRemove4 != null) {
                internalDownloadStatusListenerRemove4.stop();
            }
            throw th;
        }
    }

    public void addProgressListener(DownloadRequest downloadRequest, Executor executor, DownloadProgressListener downloadProgressListener) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        InternalDownloadProgressListener internalDownloadProgressListener = new InternalDownloadProgressListener(downloadProgressListener, executor);
        try {
            int iAddProgressListener = iMbmsDownloadService.addProgressListener(downloadRequest, internalDownloadProgressListener);
            if (iAddProgressListener == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iAddProgressListener == 0) {
                this.mInternalDownloadProgressListeners.put(downloadProgressListener, internalDownloadProgressListener);
            } else {
                if (iAddProgressListener == 402) {
                    throw new IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(iAddProgressListener, null);
            }
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void removeProgressListener(DownloadRequest downloadRequest, DownloadProgressListener downloadProgressListener) {
        try {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null) {
                throw new IllegalStateException("Middleware not yet bound");
            }
            InternalDownloadProgressListener internalDownloadProgressListener = this.mInternalDownloadProgressListeners.get(downloadProgressListener);
            if (internalDownloadProgressListener == null) {
                throw new IllegalArgumentException("Provided listener was never registered");
            }
            try {
                int iRemoveProgressListener = iMbmsDownloadService.removeProgressListener(downloadRequest, internalDownloadProgressListener);
                if (iRemoveProgressListener == -1) {
                    close();
                    throw new IllegalStateException("Middleware must not return an unknown error code");
                }
                if (iRemoveProgressListener == 0) {
                    InternalDownloadProgressListener internalDownloadProgressListenerRemove = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
                    if (internalDownloadProgressListenerRemove != null) {
                        internalDownloadProgressListenerRemove.stop();
                        return;
                    }
                    return;
                }
                if (iRemoveProgressListener == 402) {
                    throw new IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(iRemoveProgressListener, null);
                InternalDownloadProgressListener internalDownloadProgressListenerRemove2 = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
                if (internalDownloadProgressListenerRemove2 != null) {
                    internalDownloadProgressListenerRemove2.stop();
                }
            } catch (RemoteException unused) {
                this.mService.set(null);
                sIsInitialized.set(false);
                sendErrorToApp(3, null);
                InternalDownloadProgressListener internalDownloadProgressListenerRemove3 = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
                if (internalDownloadProgressListenerRemove3 != null) {
                    internalDownloadProgressListenerRemove3.stop();
                }
            }
        } catch (Throwable th) {
            InternalDownloadProgressListener internalDownloadProgressListenerRemove4 = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
            if (internalDownloadProgressListenerRemove4 != null) {
                internalDownloadProgressListenerRemove4.stop();
            }
            throw th;
        }
    }

    public void cancelDownload(DownloadRequest downloadRequest) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            int iCancelDownload = iMbmsDownloadService.cancelDownload(downloadRequest);
            if (iCancelDownload == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iCancelDownload != 0) {
                sendErrorToApp(iCancelDownload, null);
            } else {
                deleteDownloadRequestToken(downloadRequest);
            }
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            int iRequestDownloadState = iMbmsDownloadService.requestDownloadState(downloadRequest, fileInfo);
            if (iRequestDownloadState == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iRequestDownloadState != 0) {
                if (iRequestDownloadState == 402) {
                    throw new IllegalArgumentException("Unknown download request.");
                }
                if (iRequestDownloadState == 403) {
                    throw new IllegalArgumentException("Unknown file.");
                }
                sendErrorToApp(iRequestDownloadState, null);
            }
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void resetDownloadKnowledge(DownloadRequest downloadRequest) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            int iResetDownloadKnowledge = iMbmsDownloadService.resetDownloadKnowledge(downloadRequest);
            if (iResetDownloadKnowledge == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iResetDownloadKnowledge != 0) {
                if (iResetDownloadKnowledge == 402) {
                    throw new IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(iResetDownloadKnowledge, null);
            }
        } catch (RemoteException unused) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null || this.mServiceConnection == null) {
                Log.i(LOG_TAG, "Service already dead");
            } else {
                iMbmsDownloadService.dispose(this.mSubscriptionId);
                this.mContext.unbindService(this.mServiceConnection);
            }
        } catch (RemoteException unused) {
            Log.i(LOG_TAG, "Remote exception while disposing of service");
        } finally {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mServiceConnection = null;
            this.mInternalCallback.stop();
        }
    }

    private void writeDownloadRequestToken(DownloadRequest downloadRequest) {
        File downloadRequestTokenPath = getDownloadRequestTokenPath(downloadRequest);
        if (!downloadRequestTokenPath.getParentFile().exists()) {
            downloadRequestTokenPath.getParentFile().mkdirs();
        }
        if (downloadRequestTokenPath.exists()) {
            Log.w(LOG_TAG, "Download token " + downloadRequestTokenPath.getName() + " already exists");
            return;
        }
        try {
            if (downloadRequestTokenPath.createNewFile()) {
                return;
            }
            throw new RuntimeException("Failed to create download token for request " + downloadRequest + ". Token location is " + downloadRequestTokenPath.getPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create download token for request " + downloadRequest + " due to IOException " + e + ". Attempted to write to " + downloadRequestTokenPath.getPath());
        }
    }

    private void deleteDownloadRequestToken(DownloadRequest downloadRequest) {
        File downloadRequestTokenPath = getDownloadRequestTokenPath(downloadRequest);
        if (!downloadRequestTokenPath.isFile()) {
            Log.w(LOG_TAG, "Attempting to delete non-existent download token at " + downloadRequestTokenPath);
            return;
        }
        if (downloadRequestTokenPath.delete()) {
            return;
        }
        Log.w(LOG_TAG, "Couldn't delete download token at " + downloadRequestTokenPath);
    }

    private void checkDownloadRequestDestination(DownloadRequest downloadRequest) {
        File file = new File(downloadRequest.getDestinationUri().getPath());
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("The destination path must be a directory");
        }
        File file2 = new File(MbmsTempFileProvider.getEmbmsTempFileDir(this.mContext), DESTINATION_SANITY_CHECK_FILE_NAME);
        File file3 = new File(file, DESTINATION_SANITY_CHECK_FILE_NAME);
        try {
            try {
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                if (file2.renameTo(file3)) {
                } else {
                    throw new IllegalArgumentException("Destination provided in the download request is invalid -- files in the temp file directory cannot be directly moved there.");
                }
            } catch (IOException e) {
                throw new IllegalStateException("Got IOException while testing out the destination: " + e);
            }
        } finally {
            file2.delete();
            file3.delete();
        }
    }

    private File getDownloadRequestTokenPath(DownloadRequest downloadRequest) {
        return new File(MbmsUtils.getEmbmsTempFileDirForService(this.mContext, downloadRequest.getFileServiceId()), downloadRequest.getHash() + MbmsDownloadReceiver.DOWNLOAD_TOKEN_SUFFIX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorToApp(int i, String str) {
        this.mInternalCallback.onError(i, str);
    }
}
