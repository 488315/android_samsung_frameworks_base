package android.view.contentcapture;

import android.annotation.SystemApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Dumpable;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.IDataShareWriteAdapter;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.SyncResultReceiver;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class ContentCaptureManager {
    public static final int DATA_SHARE_ERROR_CONCURRENT_REQUEST = 2;
    public static final int DATA_SHARE_ERROR_TIMEOUT_INTERRUPTED = 3;
    public static final int DATA_SHARE_ERROR_UNKNOWN = 1;
    public static final boolean DEBUG = false;
    public static final long DEFAULT_CONTENT_PROTECTION_ALLOWLIST_DELAY_MS = 30000;
    public static final long DEFAULT_CONTENT_PROTECTION_ALLOWLIST_TIMEOUT_MS = 250;
    public static final long DEFAULT_CONTENT_PROTECTION_AUTO_DISCONNECT_TIMEOUT_MS = 3000;
    public static final int DEFAULT_CONTENT_PROTECTION_BUFFER_SIZE = 150;
    public static final String DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS_CONFIG = "";
    public static final int DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS_THRESHOLD = 0;
    public static final String DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS_CONFIG = "";
    public static final boolean DEFAULT_DISABLE_FLUSH_FOR_VIEW_TREE_APPEARING = false;
    public static final boolean DEFAULT_ENABLE_CONTENT_CAPTURE_RECEIVER = true;
    public static final boolean DEFAULT_ENABLE_CONTENT_PROTECTION_RECEIVER = false;
    public static final int DEFAULT_IDLE_FLUSHING_FREQUENCY_MS = 5000;
    public static final int DEFAULT_LOG_HISTORY_SIZE = 10;
    public static final int DEFAULT_MAX_BUFFER_SIZE = 500;
    public static final int DEFAULT_TEXT_CHANGE_FLUSHING_FREQUENCY_MS = 1000;
    public static final String DEVICE_CONFIG_ENABLE_ACTIVITY_START_ASSIST_CONTENT = "enable_activity_start_assist_content";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_ALLOWLIST_DELAY_MS = "content_protection_allowlist_delay_ms";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_ALLOWLIST_TIMEOUT_MS = "content_protection_allowlist_timeout_ms";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_AUTO_DISCONNECT_TIMEOUT = "content_protection_auto_disconnect_timeout_ms";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_BUFFER_SIZE = "content_protection_buffer_size";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_OPTIONAL_GROUPS_CONFIG = "content_protection_optional_groups_config";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_OPTIONAL_GROUPS_THRESHOLD = "content_protection_optional_groups_threshold";
    public static final String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_REQUIRED_GROUPS_CONFIG = "content_protection_required_groups_config";
    public static final String DEVICE_CONFIG_PROPERTY_DISABLE_FLUSH_FOR_VIEW_TREE_APPEARING = "disable_flush_for_view_tree_appearing";
    public static final String DEVICE_CONFIG_PROPERTY_ENABLE_CONTENT_PROTECTION_RECEIVER = "enable_content_protection_receiver";
    public static final String DEVICE_CONFIG_PROPERTY_IDLE_FLUSH_FREQUENCY = "idle_flush_frequency";
    public static final String DEVICE_CONFIG_PROPERTY_IDLE_UNBIND_TIMEOUT = "idle_unbind_timeout";
    public static final String DEVICE_CONFIG_PROPERTY_LOGGING_LEVEL = "logging_level";
    public static final String DEVICE_CONFIG_PROPERTY_LOG_HISTORY_SIZE = "log_history_size";
    public static final String DEVICE_CONFIG_PROPERTY_MAX_BUFFER_SIZE = "max_buffer_size";
    public static final String DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN = "report_list_view_children";
    public static final String DEVICE_CONFIG_PROPERTY_SERVICE_EXPLICITLY_ENABLED = "service_explicitly_enabled";
    public static final String DEVICE_CONFIG_PROPERTY_TEXT_CHANGE_FLUSH_FREQUENCY = "text_change_flush_frequency";
    public static final String DUMPABLE_NAME = "ContentCaptureManager";
    public static final int LOGGING_LEVEL_DEBUG = 1;
    public static final int LOGGING_LEVEL_OFF = 0;
    public static final int LOGGING_LEVEL_VERBOSE = 2;

    @SystemApi
    public static final int NO_SESSION_ID = 0;
    public static final int RESULT_CODE_FALSE = 2;
    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_SECURITY_EXCEPTION = -1;
    public static final int RESULT_CODE_TRUE = 1;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = "ContentCaptureManager";
    private Handler mContentCaptureHandler;
    private final RingBuffer<ContentCaptureEvent> mContentProtectionEventBuffer;
    private final StrippedContext mContext;
    private final LocalDataShareAdapterResourceManager mDataShareAdapterResourceManager;
    private Dumper mDumpable;
    private int mFlags;
    private final Object mLock = new Object();
    private ContentCaptureSession mMainSession;
    final ContentCaptureOptions mOptions;
    private final IContentCaptureManager mService;
    private Handler mUiHandler;
    public static final List<List<String>> DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS = Collections.EMPTY_LIST;
    public static final List<List<String>> DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS = Collections.EMPTY_LIST;

    public interface ContentCaptureClient {
        ComponentName contentCaptureClientGetComponentName();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataShareError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LoggingLevel {
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface MyRunnable {
        void run(SyncResultReceiver syncResultReceiver) throws RemoteException;
    }

    public static class StrippedContext {
        final String mContext;
        final String mPackageName;
        final int mUserId;

        public StrippedContext(Context context) {
            this.mPackageName = context.getPackageName();
            this.mContext = context.toString();
            this.mUserId = context.getUserId();
        }

        public String toString() {
            return this.mContext;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    public ContentCaptureManager(Context context, IContentCaptureManager iContentCaptureManager, ContentCaptureOptions contentCaptureOptions) {
        Objects.requireNonNull(context, "context cannot be null");
        this.mContext = new StrippedContext(context);
        this.mService = (IContentCaptureManager) Objects.requireNonNull(iContentCaptureManager, "service cannot be null");
        ContentCaptureOptions contentCaptureOptions2 = (ContentCaptureOptions) Objects.requireNonNull(contentCaptureOptions, "options cannot be null");
        this.mOptions = contentCaptureOptions2;
        ContentCaptureHelper.setLoggingLevel(contentCaptureOptions2.loggingLevel);
        setFlushViewTreeAppearingEventDisabled(contentCaptureOptions2.disableFlushForViewTreeAppearing);
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "Constructor for " + context.getPackageName());
        }
        this.mDataShareAdapterResourceManager = new LocalDataShareAdapterResourceManager();
        if (contentCaptureOptions2.contentProtectionOptions.enableReceiver && contentCaptureOptions2.contentProtectionOptions.bufferSize > 0) {
            this.mContentProtectionEventBuffer = new RingBuffer<>(ContentCaptureEvent.class, contentCaptureOptions2.contentProtectionOptions.bufferSize);
        } else {
            this.mContentProtectionEventBuffer = null;
        }
    }

    public ContentCaptureSession getMainContentCaptureSession() {
        ContentCaptureManager contentCaptureManager;
        ContentCaptureSession contentCaptureSession;
        synchronized (this.mLock) {
            if (this.mMainSession == null) {
                contentCaptureManager = this;
                contentCaptureManager.mMainSession = new MainContentCaptureSession(this.mContext, contentCaptureManager, prepareUiHandler(), prepareContentCaptureHandler(), this.mService);
                if (ContentCaptureHelper.sVerbose) {
                    Log.v(TAG, "getMainContentCaptureSession(): created " + contentCaptureManager.mMainSession);
                }
            } else {
                contentCaptureManager = this;
            }
            contentCaptureSession = contentCaptureManager.mMainSession;
        }
        return contentCaptureSession;
    }

    private Handler prepareContentCaptureHandler() {
        if (this.mContentCaptureHandler == null) {
            this.mContentCaptureHandler = BackgroundThread.getHandler();
        }
        return this.mContentCaptureHandler;
    }

    private Handler prepareUiHandler() {
        if (this.mUiHandler == null) {
            this.mUiHandler = Handler.createAsync(Looper.getMainLooper());
        }
        return this.mUiHandler;
    }

    public void onActivityCreated(IBinder iBinder, IBinder iBinder2, ComponentName componentName) {
        if (this.mOptions.lite) {
            return;
        }
        synchronized (this.mLock) {
            getMainContentCaptureSession().start(iBinder, iBinder2, componentName, this.mFlags);
        }
    }

    public void onActivityResumed() {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().notifySessionResumed();
    }

    public void onActivityPaused() {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().notifySessionPaused();
    }

    public void onActivityDestroyed() {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().destroy();
    }

    public void flush(int i) {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().flush(i);
    }

    public ComponentName getServiceComponentName() {
        if (!isContentCaptureEnabled() && !this.mOptions.lite) {
            return null;
        }
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.getServiceComponentName(syncResultReceiver);
            return (ComponentName) syncResultReceiver.getParcelableResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException unused) {
            throw new RuntimeException("Fail to get service componentName.");
        }
    }

    public static ComponentName getServiceSettingsComponentName() {
        IBinder iBinderCheckService = ServiceManager.checkService(Context.CONTENT_CAPTURE_MANAGER_SERVICE);
        if (iBinderCheckService == null) {
            return null;
        }
        IContentCaptureManager iContentCaptureManagerAsInterface = IContentCaptureManager.Stub.asInterface(iBinderCheckService);
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            iContentCaptureManagerAsInterface.getServiceSettingsActivity(syncResultReceiver);
            if (syncResultReceiver.getIntResult() == -1) {
                throw new SecurityException(syncResultReceiver.getStringResult());
            }
            return (ComponentName) syncResultReceiver.getParcelableResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException e2) {
            Log.e(TAG, "Fail to get service settings componentName: " + e2);
            return null;
        }
    }

    public boolean isContentCaptureEnabled() {
        ContentCaptureSession contentCaptureSession;
        if (this.mOptions.lite) {
            return false;
        }
        synchronized (this.mLock) {
            contentCaptureSession = this.mMainSession;
        }
        return contentCaptureSession == null || !contentCaptureSession.isDisabled();
    }

    public Set<ContentCaptureCondition> getContentCaptureConditions() {
        if (!isContentCaptureEnabled() && !this.mOptions.lite) {
            return null;
        }
        try {
            return ContentCaptureHelper.toSet(syncRun(new MyRunnable() { // from class: android.view.contentcapture.ContentCaptureManager$$ExternalSyntheticLambda0
                @Override // android.view.contentcapture.ContentCaptureManager.MyRunnable
                public final void run(SyncResultReceiver syncResultReceiver) throws RemoteException {
                    this.f$0.lambda$getContentCaptureConditions$0(syncResultReceiver);
                }
            }).getParcelableListResult());
        } catch (SyncResultReceiver.TimeoutException unused) {
            throw new RuntimeException("Fail to get content capture conditions.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getContentCaptureConditions$0(SyncResultReceiver syncResultReceiver) throws RemoteException {
        this.mService.getContentCaptureConditions(this.mContext.getPackageName(), syncResultReceiver);
    }

    public void setContentCaptureEnabled(boolean z) {
        ContentCaptureSession contentCaptureSession;
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "setContentCaptureEnabled(): setting to " + z + " for " + this.mContext);
        }
        synchronized (this.mLock) {
            if (z) {
                this.mFlags &= -2;
            } else {
                this.mFlags |= 1;
            }
            contentCaptureSession = this.mMainSession;
        }
        if (contentCaptureSession != null) {
            contentCaptureSession.setDisabled(!z);
        }
    }

    public void updateWindowAttributes(WindowManager.LayoutParams layoutParams) {
        boolean z;
        ContentCaptureSession contentCaptureSession;
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "updateWindowAttributes(): window flags=" + layoutParams.flags);
        }
        boolean z2 = (layoutParams.flags & 8192) != 0;
        synchronized (this.mLock) {
            int i = this.mFlags;
            z = (i & 1) != 0;
            if (z2) {
                this.mFlags = i | 2;
            } else {
                this.mFlags = i & (-3);
            }
            contentCaptureSession = this.mMainSession;
        }
        if (contentCaptureSession == null || z) {
            return;
        }
        contentCaptureSession.setDisabled(z2);
    }

    public void setFlushViewTreeAppearingEventDisabled(boolean z) {
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "setFlushViewTreeAppearingEventDisabled(): setting to " + z);
        }
        synchronized (this.mLock) {
            if (z) {
                this.mFlags |= 8;
            } else {
                this.mFlags &= -9;
            }
        }
    }

    public boolean getFlushViewTreeAppearingEventDisabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mFlags & 8) != 0;
        }
        return z;
    }

    @SystemApi
    public boolean isContentCaptureFeatureEnabled() {
        try {
            int intResult = syncRun(new MyRunnable() { // from class: android.view.contentcapture.ContentCaptureManager$$ExternalSyntheticLambda1
                @Override // android.view.contentcapture.ContentCaptureManager.MyRunnable
                public final void run(SyncResultReceiver syncResultReceiver) throws RemoteException {
                    this.f$0.lambda$isContentCaptureFeatureEnabled$1(syncResultReceiver);
                }
            }).getIntResult();
            if (intResult == 1) {
                return true;
            }
            if (intResult != 2) {
                Log.wtf(TAG, "received invalid result: " + intResult);
            }
            return false;
        } catch (SyncResultReceiver.TimeoutException e) {
            Log.e(TAG, "Fail to get content capture feature enable status: " + e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isContentCaptureFeatureEnabled$1(SyncResultReceiver syncResultReceiver) throws RemoteException {
        this.mService.isContentCaptureFeatureEnabled(syncResultReceiver);
    }

    public void removeData(DataRemovalRequest dataRemovalRequest) {
        Objects.requireNonNull(dataRemovalRequest);
        try {
            this.mService.removeData(dataRemovalRequest);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void shareData(DataShareRequest dataShareRequest, Executor executor, DataShareWriteAdapter dataShareWriteAdapter) {
        Objects.requireNonNull(dataShareRequest);
        Objects.requireNonNull(dataShareWriteAdapter);
        Objects.requireNonNull(executor);
        try {
            this.mService.shareData(dataShareRequest, new DataShareAdapterDelegate(executor, dataShareWriteAdapter, this.mDataShareAdapterResourceManager));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private SyncResultReceiver syncRun(MyRunnable myRunnable) {
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            myRunnable.run(syncResultReceiver);
            if (syncResultReceiver.getIntResult() != -1) {
                return syncResultReceiver;
            }
            throw new SecurityException(syncResultReceiver.getStringResult());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException unused) {
            throw new RuntimeException("Fail to get syn run result from SyncResultReceiver.");
        }
    }

    public void addDumpable(Activity activity) {
        if (this.mDumpable == null) {
            this.mDumpable = new Dumper();
        }
        activity.addDumpable(this.mDumpable);
    }

    public RingBuffer<ContentCaptureEvent> getContentProtectionEventBuffer() {
        return this.mContentProtectionEventBuffer;
    }

    private final class Dumper implements Dumpable {
        private Dumper() {
        }

        @Override // android.util.Dumpable
        public void dump(PrintWriter printWriter, String[] strArr) {
            printWriter.print("");
            printWriter.println(ContentCaptureManager.DUMPABLE_NAME);
            synchronized (ContentCaptureManager.this.mLock) {
                printWriter.print("  ");
                printWriter.print("isContentCaptureEnabled(): ");
                printWriter.println(ContentCaptureManager.this.isContentCaptureEnabled());
                printWriter.print("  ");
                printWriter.print("Debug: ");
                printWriter.print(ContentCaptureHelper.sDebug);
                printWriter.print(" Verbose: ");
                printWriter.println(ContentCaptureHelper.sVerbose);
                printWriter.print("  ");
                printWriter.print("Context: ");
                printWriter.println(ContentCaptureManager.this.mContext);
                printWriter.print("  ");
                printWriter.print("User: ");
                printWriter.println(ContentCaptureManager.this.mContext.getUserId());
                printWriter.print("  ");
                printWriter.print("Service: ");
                printWriter.println(ContentCaptureManager.this.mService);
                printWriter.print("  ");
                printWriter.print("Flags: ");
                printWriter.println(ContentCaptureManager.this.mFlags);
                printWriter.print("  ");
                printWriter.print("Options: ");
                ContentCaptureManager.this.mOptions.dumpShort(printWriter);
                printWriter.println();
                if (ContentCaptureManager.this.mMainSession != null) {
                    printWriter.print("  ");
                    printWriter.println("Main session:");
                    ContentCaptureManager.this.mMainSession.dump("    ", printWriter);
                } else {
                    printWriter.print("  ");
                    printWriter.println("No sessions");
                }
            }
        }

        @Override // android.util.Dumpable
        public String getDumpableName() {
            return ContentCaptureManager.DUMPABLE_NAME;
        }
    }

    public static void resetTemporaryService(int i) {
        IContentCaptureManager service = getService();
        if (service == null) {
            Log.e(TAG, "IContentCaptureManager is null");
        }
        try {
            service.resetTemporaryService(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setTemporaryService(int i, String str, int i2) {
        IContentCaptureManager service = getService();
        if (service == null) {
            Log.e(TAG, "IContentCaptureManager is null");
        }
        try {
            service.setTemporaryService(i, str, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setDefaultServiceEnabled(int i, boolean z) {
        IContentCaptureManager service = getService();
        if (service == null) {
            Log.e(TAG, "IContentCaptureManager is null");
        }
        try {
            service.setDefaultServiceEnabled(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static IContentCaptureManager getService() {
        return IContentCaptureManager.Stub.asInterface(ServiceManager.getService(Context.CONTENT_CAPTURE_MANAGER_SERVICE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataShareAdapterDelegate extends IDataShareWriteAdapter.Stub {
        private final WeakReference<LocalDataShareAdapterResourceManager> mResourceManagerReference;

        private DataShareAdapterDelegate(Executor executor, DataShareWriteAdapter dataShareWriteAdapter, LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager) {
            Objects.requireNonNull(executor);
            Objects.requireNonNull(dataShareWriteAdapter);
            Objects.requireNonNull(localDataShareAdapterResourceManager);
            localDataShareAdapterResourceManager.initializeForDelegate(this, dataShareWriteAdapter, executor);
            this.mResourceManagerReference = new WeakReference<>(localDataShareAdapterResourceManager);
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void write(final ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            executeAdapterMethodLocked(new Consumer() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DataShareWriteAdapter) obj).onWrite(parcelFileDescriptor);
                }
            }, "onWrite");
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void error(final int i) throws RemoteException {
            executeAdapterMethodLocked(new Consumer() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DataShareWriteAdapter) obj).onError(i);
                }
            }, "onError");
            clearHardReferences();
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void rejected() throws RemoteException {
            executeAdapterMethodLocked(new Consumer() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DataShareWriteAdapter) obj).onRejected();
                }
            }, "onRejected");
            clearHardReferences();
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void finish() throws RemoteException {
            clearHardReferences();
        }

        private void executeAdapterMethodLocked(final Consumer<DataShareWriteAdapter> consumer, String str) {
            LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                Slog.w(ContentCaptureManager.TAG, "Can't execute " + str + "(), resource manager has been GC'ed");
                return;
            }
            final DataShareWriteAdapter adapter = localDataShareAdapterResourceManager.getAdapter(this);
            Executor executor = localDataShareAdapterResourceManager.getExecutor(this);
            if (adapter == null || executor == null) {
                Slog.w(ContentCaptureManager.TAG, "Can't execute " + str + "(), references are null");
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(adapter);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void clearHardReferences() {
            LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                Slog.w(ContentCaptureManager.TAG, "Can't clear references, resource manager has been GC'ed");
            } else {
                localDataShareAdapterResourceManager.clearHardReferences(this);
            }
        }
    }

    private static class LocalDataShareAdapterResourceManager {
        private Map<DataShareAdapterDelegate, Executor> mExecutorHardReferences;
        private Map<DataShareAdapterDelegate, DataShareWriteAdapter> mWriteAdapterHardReferences;

        private LocalDataShareAdapterResourceManager() {
            this.mWriteAdapterHardReferences = new HashMap();
            this.mExecutorHardReferences = new HashMap();
        }

        void initializeForDelegate(DataShareAdapterDelegate dataShareAdapterDelegate, DataShareWriteAdapter dataShareWriteAdapter, Executor executor) {
            this.mWriteAdapterHardReferences.put(dataShareAdapterDelegate, dataShareWriteAdapter);
            this.mExecutorHardReferences.put(dataShareAdapterDelegate, executor);
        }

        Executor getExecutor(DataShareAdapterDelegate dataShareAdapterDelegate) {
            return this.mExecutorHardReferences.get(dataShareAdapterDelegate);
        }

        DataShareWriteAdapter getAdapter(DataShareAdapterDelegate dataShareAdapterDelegate) {
            return this.mWriteAdapterHardReferences.get(dataShareAdapterDelegate);
        }

        void clearHardReferences(DataShareAdapterDelegate dataShareAdapterDelegate) {
            this.mWriteAdapterHardReferences.remove(dataShareAdapterDelegate);
            this.mExecutorHardReferences.remove(dataShareAdapterDelegate);
        }
    }
}
