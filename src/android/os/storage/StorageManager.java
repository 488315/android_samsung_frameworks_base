package android.os.storage;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.PendingIntent;
import android.app.PropertyInvalidatedCache;
import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.IPackageMoveObserver;
import android.content.res.ObbInfo;
import android.content.res.ObbScanner;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IVoldTaskListener;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.ProxyFileDescriptorCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.provider.DeviceConfig;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.DataUnit;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.sysfwutil.Slog;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.AppFuseMount;
import com.android.internal.os.FuseAppLoop;
import com.android.internal.os.FuseUnavailableMountException;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.Preconditions;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.share.SemShareConstants;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class StorageManager {
    public static final String ACTION_CLEAR_APP_CACHE = "android.os.storage.action.CLEAR_APP_CACHE";
    public static final String ACTION_MANAGE_STORAGE = "android.os.storage.action.MANAGE_STORAGE";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int APP_IO_BLOCKED_REASON_TRANSCODING = 1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int APP_IO_BLOCKED_REASON_UNKNOWN = 0;
    public static final String CACHE_RESERVE_PERCENT_HIGH_KEY = "cache_reserve_percent_high";
    public static final String CACHE_RESERVE_PERCENT_LOW_KEY = "cache_reserve_percent_low";
    public static final int CRYPT_TYPE_DEFAULT = 1;
    public static final int CRYPT_TYPE_PASSWORD = 0;
    public static final int DEBUG_ADOPTABLE_FORCE_OFF = 2;
    public static final int DEBUG_ADOPTABLE_FORCE_ON = 1;
    public static final int DEBUG_SDCARDFS_FORCE_OFF = 8;
    public static final int DEBUG_SDCARDFS_FORCE_ON = 4;
    public static final int DEBUG_VIRTUAL_DISK = 16;
    public static final int DEFAULT_CACHE_RESERVE_PERCENT_HIGH = 10;
    public static final int DEFAULT_CACHE_RESERVE_PERCENT_LOW = 2;
    private static final long DEFAULT_EXHAUSTION_THRESHOLD_BYTES;
    private static final long DEFAULT_FULL_THRESHOLD_BYTES;
    public static final int DEFAULT_STORAGE_THRESHOLD_PERCENT_HIGH = 20;
    public static final int DEFAULT_STORAGE_THRESHOLD_PERCENT_LOW = 5;
    private static final long DEFAULT_THRESHOLD_MAX_BYTES;
    public static final int ENCRYPTION_STATE_NONE = 1;
    public static final String EXTRA_REQUESTED_BYTES = "android.os.storage.extra.REQUESTED_BYTES";
    public static final String EXTRA_UUID = "android.os.storage.extra.UUID";
    private static final String FAT_UUID_PREFIX = "fafafafa-fafa-5afa-8afa-fafa";

    @SystemApi
    public static final int FLAG_ALLOCATE_AGGRESSIVE = 1;
    public static final int FLAG_ALLOCATE_CACHE_ONLY = 16;
    public static final int FLAG_ALLOCATE_DEFY_ALL_RESERVED = 2;
    public static final int FLAG_ALLOCATE_DEFY_HALF_RESERVED = 4;
    public static final int FLAG_ALLOCATE_NON_CACHE_ONLY = 8;
    public static final int FLAG_FOR_WRITE = 256;
    public static final int FLAG_INCLUDE_INVISIBLE = 1024;
    public static final int FLAG_INCLUDE_RECENT = 2048;
    public static final int FLAG_INCLUDE_SHARED_PROFILE = 4096;
    public static final int FLAG_REAL_STATE = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FLAG_STORAGE_SDK = 8;
    public static final int FSTRIM_FLAG_DEEP = 1;

    @SystemApi
    public static final int MOUNT_MODE_EXTERNAL_ANDROID_WRITABLE = 4;

    @SystemApi
    public static final int MOUNT_MODE_EXTERNAL_DEFAULT = 1;

    @SystemApi
    public static final int MOUNT_MODE_EXTERNAL_INSTALLER = 2;

    @SystemApi
    public static final int MOUNT_MODE_EXTERNAL_NONE = 0;

    @SystemApi
    public static final int MOUNT_MODE_EXTERNAL_PASS_THROUGH = 3;
    private static final Pattern PATTERN_USER_ID;
    public static final int PROJECT_ID_EXT_DEFAULT = 1000;
    public static final int PROJECT_ID_EXT_MEDIA_AUDIO = 1001;
    public static final int PROJECT_ID_EXT_MEDIA_IMAGE = 1003;
    public static final int PROJECT_ID_EXT_MEDIA_VIDEO = 1002;
    public static final String PROP_ADOPTABLE = "persist.sys.adoptable";
    public static final String PROP_FORCED_SCOPED_STORAGE_WHITELIST = "forced_scoped_storage_whitelist";
    public static final String PROP_HAS_ADOPTABLE = "vold.has_adoptable";
    public static final String PROP_HAS_RESERVED = "vold.has_reserved";
    public static final String PROP_PRIMARY_PHYSICAL = "ro.vold.primary_physical";
    public static final String PROP_SDCARDFS = "persist.sys.sdcardfs";
    public static final String PROP_VIRTUAL_DISK = "persist.sys.virtual_disk";

    @SystemApi
    public static final int QUOTA_TYPE_MEDIA_AUDIO = 2;

    @SystemApi
    public static final int QUOTA_TYPE_MEDIA_IMAGE = 1;

    @SystemApi
    public static final int QUOTA_TYPE_MEDIA_NONE = 0;

    @SystemApi
    public static final int QUOTA_TYPE_MEDIA_VIDEO = 3;
    public static final int SEM_EXTERNAL_SD_CARD_HEALTH_STATE_BAD = 1;
    public static final int SEM_EXTERNAL_SD_CARD_HEALTH_STATE_GOOD = 0;
    public static final int SEM_EXTERNAL_SD_CARD_HEALTH_STATE_UNKNOWN = -1;
    public static final int SEM_EXTERNAL_STORAGE_FORMAT = 2;
    public static final int SEM_EXTERNAL_STORAGE_MOUNT = 0;
    public static final int SEM_EXTERNAL_STORAGE_UNMOUNT = 1;
    public static final String STORAGE_THRESHOLD_PERCENT_HIGH_KEY = "storage_threshold_percent_high";
    public static final String UUID_PRIMARY_PHYSICAL = "primary_physical";
    public static final String UUID_SYSTEM = "system";
    private static final int VOLUME_LIST_CACHE_MAX = 16;
    private static final String XATTR_CACHE_GROUP = "user.cache_group";
    private static final String XATTR_CACHE_TOMBSTONE = "user.cache_tombstone";
    private static final PropertyInvalidatedCache<VolumeListQuery, StorageVolume[]> sVolumeListCache;
    private static final PropertyInvalidatedCache.QueryHandler<VolumeListQuery, StorageVolume[]> sVolumeListQuery;
    private final AppOpsManager mAppOps;
    private final Context mContext;
    private final Looper mLooper;
    private final ContentResolver mResolver;
    private static final String TAG = "StorageManager";
    private static final boolean LOCAL_LOGV = Log.isLoggable(TAG, 2);
    public static final String UUID_PRIVATE_INTERNAL = null;
    public static final UUID UUID_DEFAULT = UUID.fromString("41217664-9172-527a-b3d5-edabb50a7d69");
    public static final UUID UUID_PRIMARY_PHYSICAL_ = UUID.fromString("0f95a519-dae7-5abf-9519-fbd6209e05fd");
    public static final UUID UUID_SYSTEM_ = UUID.fromString("5d258386-e60d-59e3-826d-0089cdd42cc0");
    private static volatile IStorageManager sStorageManager = null;
    private final AtomicInteger mNextNonce = new AtomicInteger(0);
    private final String[] mAllowedPackagesForDataMvCp = {"com.sec.android.app.vepreload", "com.samsung.app.newtrim", "com.samsung.android.aware.service", "com.samsung.android.allshare.service.fileshare", SemShareConstants.GALLERY_PACKAGE, "com.sec.android.mimage.photoretouching", "com.sec.android.app.camera", "com.samsung.android.app.smartcapture", "com.sec.android.easyMover", "com.samsung.android.scloud", SemShareConstants.SHARE_LIVE_PKG};
    private final ArrayList<StorageEventListenerDelegate> mDelegates = new ArrayList<>();
    private final ObbActionListener mObbActionListener = new ObbActionListener();
    private final Object mFuseAppLoopLock = new Object();
    private FuseAppLoop mFuseAppLoop = null;
    private final String DATA_MEDIA_PATH = "/data/media";
    private final String DATA_SEC_PATH = "/data/sec";
    private final IStorageManager mStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getServiceOrThrow(AudioParameter.VALUE_MOUNT));
    private IActivityManager mActivityManager = ActivityManager.getService();

    @Retention(RetentionPolicy.SOURCE)
    public @interface AllocateFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppIoBlockedReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtStorageManageMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MountMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface QuotaType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StorageFlags {
    }

    public static class StorageVolumeCallback {
        public void onStateChanged(StorageVolume storageVolume) {
        }
    }

    private static long getProjectIdForUser(int i, int i2) {
        return (i * 100000) + i2;
    }

    public static boolean hasAdoptable() {
        return false;
    }

    @SystemApi
    public static boolean hasIsolatedStorage() {
        return false;
    }

    @Deprecated
    public static File maybeTranslateEmulatedPathToInternal(File file) {
        return file;
    }

    private static native boolean setQuotaProjectId(String str, long j);

    @Deprecated
    public void disableUsbMassStorage() {
    }

    @Deprecated
    public void enableUsbMassStorage() {
    }

    @Deprecated
    public boolean isUsbMassStorageConnected() {
        return false;
    }

    @Deprecated
    public boolean isUsbMassStorageEnabled() {
        return false;
    }

    public File translateAppToSystem(File file, int i, int i2) {
        return file;
    }

    public File translateSystemToApp(File file, int i, int i2) {
        return file;
    }

    static {
        PropertyInvalidatedCache.QueryHandler<VolumeListQuery, StorageVolume[]> queryHandler = new PropertyInvalidatedCache.QueryHandler<VolumeListQuery, StorageVolume[]>() { // from class: android.os.storage.StorageManager.1
            @Override // android.app.PropertyInvalidatedCache.QueryHandler
            public StorageVolume[] apply(VolumeListQuery volumeListQuery) {
                IStorageManager asInterface = IStorageManager.Stub.asInterface(ServiceManager.getService(AudioParameter.VALUE_MOUNT));
                if (asInterface == null) {
                    return null;
                }
                try {
                    return asInterface.getVolumeList(volumeListQuery.mUserId, volumeListQuery.mPackageName, volumeListQuery.mFlags);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        sVolumeListQuery = queryHandler;
        sVolumeListCache = new PropertyInvalidatedCache<>(new PropertyInvalidatedCache.Args("system_server").cacheNulls(false).api("getVolumeList").maxEntries(16), "getVolumeList", queryHandler);
        DEFAULT_THRESHOLD_MAX_BYTES = DataUnit.MEBIBYTES.toBytes(500L);
        DEFAULT_FULL_THRESHOLD_BYTES = DataUnit.MEBIBYTES.toBytes(1L);
        DEFAULT_EXHAUSTION_THRESHOLD_BYTES = DataUnit.GIBIBYTES.toBytes(1L);
        PATTERN_USER_ID = Pattern.compile("(?i)^/storage/emulated/([0-9]+)");
    }

    static final class VolumeListQuery extends Record {
        private final int mFlags;
        private final String mPackageName;
        private final int mUserId;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof VolumeListQuery)) {
                return false;
            }
            VolumeListQuery volumeListQuery = (VolumeListQuery) obj;
            return this.mUserId == volumeListQuery.mUserId && this.mFlags == volumeListQuery.mFlags && Objects.equals(this.mPackageName, volumeListQuery.mPackageName);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.mUserId), this.mPackageName, Integer.valueOf(this.mFlags)};
        }

        VolumeListQuery(int mUserId, String mPackageName, int mFlags) {
            this.mUserId = mUserId;
            this.mPackageName = mPackageName;
            this.mFlags = mFlags;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.mUserId, this.mFlags, this.mPackageName);
        }

        public int mFlags() {
            return this.mFlags;
        }

        public String mPackageName() {
            return this.mPackageName;
        }

        public int mUserId() {
            return this.mUserId;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), VolumeListQuery.class, "mUserId;mPackageName;mFlags");
        }
    }

    public static void invalidateVolumeListCache() {
        sVolumeListCache.invalidateCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StorageEventListenerDelegate extends IStorageEventListener.Stub {
        final StorageVolumeCallback mCallback;
        final Executor mExecutor;
        final StorageEventListener mListener;

        public StorageEventListenerDelegate(Executor executor, StorageEventListener storageEventListener, StorageVolumeCallback storageVolumeCallback) {
            this.mExecutor = executor;
            this.mListener = storageEventListener;
            this.mCallback = storageVolumeCallback;
        }

        @Override // android.os.storage.IStorageEventListener
        public void onUsbMassStorageConnectionChanged(final boolean z) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onUsbMassStorageConnectionChanged$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUsbMassStorageConnectionChanged$0(boolean z) {
            this.mListener.onUsbMassStorageConnectionChanged(z);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onStorageStateChanged(final String str, final String str2, final String str3) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onStorageStateChanged$1(str, str2, str3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStorageStateChanged$1(String str, String str2, String str3) {
            this.mListener.onStorageStateChanged(str, str2, str3);
            if (str != null) {
                for (StorageVolume storageVolume : StorageManager.this.getStorageVolumes()) {
                    if (Objects.equals(str, storageVolume.getPath())) {
                        this.mCallback.onStateChanged(storageVolume);
                    }
                }
            }
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeStateChanged(final VolumeInfo volumeInfo, final int i, final int i2) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onVolumeStateChanged$2(volumeInfo, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVolumeStateChanged$2(VolumeInfo volumeInfo, int i, int i2) {
            this.mListener.onVolumeStateChanged(volumeInfo, i, i2);
            File pathForUser = volumeInfo.getPathForUser(UserHandle.myUserId());
            if (pathForUser != null) {
                for (StorageVolume storageVolume : StorageManager.this.getStorageVolumes()) {
                    if (Objects.equals(pathForUser.getAbsolutePath(), storageVolume.getPath())) {
                        this.mCallback.onStateChanged(storageVolume);
                    }
                }
            }
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeRecordChanged(final VolumeRecord volumeRecord) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onVolumeRecordChanged$3(volumeRecord);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVolumeRecordChanged$3(VolumeRecord volumeRecord) {
            this.mListener.onVolumeRecordChanged(volumeRecord);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeForgotten(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onVolumeForgotten$4(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVolumeForgotten$4(String str) {
            this.mListener.onVolumeForgotten(str);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskScanned(final DiskInfo diskInfo, final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onDiskScanned$5(diskInfo, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDiskScanned$5(DiskInfo diskInfo, int i) {
            this.mListener.onDiskScanned(diskInfo, i);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskDestroyed(final DiskInfo diskInfo) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManager.StorageEventListenerDelegate.this.lambda$onDiskDestroyed$6(diskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDiskDestroyed$6(DiskInfo diskInfo) {
            this.mListener.onDiskDestroyed(diskInfo);
        }
    }

    private class ObbActionListener extends IObbActionListener.Stub {
        private SparseArray<ObbListenerDelegate> mListeners;

        private ObbActionListener() {
            this.mListeners = new SparseArray<>();
        }

        @Override // android.os.storage.IObbActionListener
        public void onObbResult(String str, int i, int i2) {
            ObbListenerDelegate obbListenerDelegate;
            synchronized (this.mListeners) {
                obbListenerDelegate = this.mListeners.get(i);
                if (obbListenerDelegate != null) {
                    this.mListeners.remove(i);
                }
            }
            if (obbListenerDelegate != null) {
                obbListenerDelegate.sendObbStateChanged(str, i2);
            }
        }

        public int addListener(OnObbStateChangeListener onObbStateChangeListener) {
            ObbListenerDelegate obbListenerDelegate = new ObbListenerDelegate(StorageManager.this, onObbStateChangeListener);
            synchronized (this.mListeners) {
                this.mListeners.put(obbListenerDelegate.nonce, obbListenerDelegate);
            }
            return obbListenerDelegate.nonce;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextNonce() {
        return this.mNextNonce.getAndIncrement();
    }

    private class ObbListenerDelegate {
        private final Handler mHandler;
        private final WeakReference<OnObbStateChangeListener> mObbEventListenerRef;
        private final int nonce;

        ObbListenerDelegate(final StorageManager storageManager, OnObbStateChangeListener onObbStateChangeListener) {
            this.nonce = storageManager.getNextNonce();
            this.mObbEventListenerRef = new WeakReference<>(onObbStateChangeListener);
            this.mHandler = new Handler(storageManager.mLooper) { // from class: android.os.storage.StorageManager.ObbListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    OnObbStateChangeListener listener = ObbListenerDelegate.this.getListener();
                    if (listener == null) {
                        return;
                    }
                    listener.onObbStateChange((String) message.obj, message.arg1);
                }
            };
        }

        OnObbStateChangeListener getListener() {
            WeakReference<OnObbStateChangeListener> weakReference = this.mObbEventListenerRef;
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }

        void sendObbStateChanged(String str, int i) {
            this.mHandler.obtainMessage(0, i, 0, str).sendToTarget();
        }
    }

    @Deprecated
    public static StorageManager from(Context context) {
        return (StorageManager) context.getSystemService(StorageManager.class);
    }

    public StorageManager(Context context, Looper looper) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mLooper = looper;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    }

    public void registerListener(StorageEventListener storageEventListener) {
        synchronized (this.mDelegates) {
            StorageEventListenerDelegate storageEventListenerDelegate = new StorageEventListenerDelegate(this.mContext.getMainExecutor(), storageEventListener, new StorageVolumeCallback());
            try {
                this.mStorageManager.registerListener(storageEventListenerDelegate);
                this.mDelegates.add(storageEventListenerDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterListener(StorageEventListener storageEventListener) {
        synchronized (this.mDelegates) {
            Iterator<StorageEventListenerDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                StorageEventListenerDelegate next = it.next();
                if (next.mListener == storageEventListener) {
                    try {
                        this.mStorageManager.unregisterListener(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public void registerStorageVolumeCallback(Executor executor, StorageVolumeCallback storageVolumeCallback) {
        synchronized (this.mDelegates) {
            StorageEventListenerDelegate storageEventListenerDelegate = new StorageEventListenerDelegate(executor, new StorageEventListener(), storageVolumeCallback);
            try {
                this.mStorageManager.registerListener(storageEventListenerDelegate);
                this.mDelegates.add(storageEventListenerDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterStorageVolumeCallback(StorageVolumeCallback storageVolumeCallback) {
        synchronized (this.mDelegates) {
            Iterator<StorageEventListenerDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                StorageEventListenerDelegate next = it.next();
                if (next.mCallback == storageVolumeCallback) {
                    try {
                        this.mStorageManager.unregisterListener(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public boolean mountObb(String str, String str2, OnObbStateChangeListener onObbStateChangeListener) {
        String str3;
        String canonicalPath;
        Preconditions.checkNotNull(str, "rawPath cannot be null");
        Preconditions.checkArgument(str2 == null, "mounting encrypted OBBs is no longer supported");
        Preconditions.checkNotNull(onObbStateChangeListener, "listener cannot be null");
        try {
            try {
                canonicalPath = new File(str).getCanonicalPath();
                str3 = str;
            } catch (IOException e) {
                e = e;
                str3 = str;
            }
            try {
                this.mStorageManager.mountObb(str3, canonicalPath, this.mObbActionListener, this.mObbActionListener.addListener(onObbStateChangeListener), getObbInfo(canonicalPath));
                return true;
            } catch (IOException e2) {
                e = e2;
                throw new IllegalArgumentException("Failed to resolve path: " + str3, e);
            }
        } catch (RemoteException e3) {
            throw e3.rethrowFromSystemServer();
        }
    }

    public PendingIntent getManageSpaceActivityIntent(String str, int i) {
        try {
            return this.mStorageManager.getManageSpaceActivityIntent(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private ObbInfo getObbInfo(String str) {
        try {
            return ObbScanner.getObbInfo(str);
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't get OBB info for " + str, e);
        }
    }

    public boolean unmountObb(String str, boolean z, OnObbStateChangeListener onObbStateChangeListener) {
        Preconditions.checkNotNull(str, "rawPath cannot be null");
        Preconditions.checkNotNull(onObbStateChangeListener, "listener cannot be null");
        try {
            this.mStorageManager.unmountObb(str, z, this.mObbActionListener, this.mObbActionListener.addListener(onObbStateChangeListener));
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isObbMounted(String str) {
        Preconditions.checkNotNull(str, "rawPath cannot be null");
        try {
            return this.mStorageManager.isObbMounted(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getMountedObbPath(String str) {
        Preconditions.checkNotNull(str, "rawPath cannot be null");
        try {
            return this.mStorageManager.getMountedObbPath(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<DiskInfo> getDisks() {
        try {
            return Arrays.asList(this.mStorageManager.getDisks());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public DiskInfo findDiskById(String str) {
        Preconditions.checkNotNull(str);
        for (DiskInfo diskInfo : getDisks()) {
            if (Objects.equals(diskInfo.id, str)) {
                return diskInfo;
            }
        }
        return null;
    }

    public VolumeInfo findVolumeById(String str) {
        Preconditions.checkNotNull(str);
        for (VolumeInfo volumeInfo : getVolumes()) {
            if (Objects.equals(volumeInfo.id, str)) {
                return volumeInfo;
            }
        }
        return null;
    }

    public VolumeInfo findVolumeByUuid(String str) {
        Preconditions.checkNotNull(str);
        for (VolumeInfo volumeInfo : getVolumes()) {
            if (Objects.equals(volumeInfo.fsUuid, str)) {
                return volumeInfo;
            }
        }
        return null;
    }

    public VolumeRecord findRecordByUuid(String str) {
        Preconditions.checkNotNull(str);
        for (VolumeRecord volumeRecord : getVolumeRecords()) {
            if (Objects.equals(volumeRecord.fsUuid, str)) {
                return volumeRecord;
            }
        }
        return null;
    }

    public VolumeInfo findPrivateForEmulated(VolumeInfo volumeInfo) {
        if (volumeInfo == null) {
            return null;
        }
        String id = volumeInfo.getId();
        int indexOf = id.indexOf(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        if (indexOf != -1) {
            id = id.substring(0, indexOf);
        }
        return findVolumeById(id.replace(VolumeInfo.ID_EMULATED_INTERNAL, VolumeInfo.ID_PRIVATE_INTERNAL));
    }

    public VolumeInfo findEmulatedForPrivate(VolumeInfo volumeInfo) {
        if (volumeInfo == null) {
            return null;
        }
        return findVolumeById(volumeInfo.getId().replace(VolumeInfo.ID_PRIVATE_INTERNAL, VolumeInfo.ID_EMULATED_INTERNAL) + NavigationBarInflaterView.GRAVITY_SEPARATOR + this.mContext.getUserId());
    }

    public VolumeInfo findVolumeByQualifiedUuid(String str) {
        if (Objects.equals(UUID_PRIVATE_INTERNAL, str)) {
            return findVolumeById(VolumeInfo.ID_PRIVATE_INTERNAL);
        }
        if (UUID_PRIMARY_PHYSICAL.equals(str)) {
            return getPrimaryPhysicalVolume();
        }
        return findVolumeByUuid(str);
    }

    public UUID getUuidForPath(File file) throws IOException {
        Preconditions.checkNotNull(file);
        String canonicalPath = file.getCanonicalPath();
        if (FileUtils.contains(Environment.getDataDirectory().getAbsolutePath(), canonicalPath)) {
            return UUID_DEFAULT;
        }
        try {
            VolumeInfo[] volumes = this.mStorageManager.getVolumes(0);
            int length = volumes.length;
            for (int i = 0; i < length; i++) {
                VolumeInfo volumeInfo = volumes[i];
                if (volumeInfo.path != null && FileUtils.contains(volumeInfo.path, canonicalPath) && volumeInfo.type != 0 && volumeInfo.type != 5) {
                    try {
                        return convert(volumeInfo.fsUuid);
                    } catch (IllegalArgumentException unused) {
                        continue;
                    }
                }
            }
            throw new FileNotFoundException("Failed to find a storage device for " + file);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public File findPathForUuid(String str) throws FileNotFoundException {
        VolumeInfo findVolumeByQualifiedUuid = findVolumeByQualifiedUuid(str);
        if (findVolumeByQualifiedUuid != null) {
            return findVolumeByQualifiedUuid.getPath();
        }
        throw new FileNotFoundException("Failed to find a storage device for " + str);
    }

    public boolean isAllocationSupported(FileDescriptor fileDescriptor) {
        try {
            getUuidForPath(ParcelFileDescriptor.getFile(fileDescriptor));
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public List<VolumeInfo> getVolumes() {
        try {
            return Arrays.asList(this.mStorageManager.getVolumes(0));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<VolumeInfo> getWritablePrivateVolumes() {
        try {
            ArrayList arrayList = new ArrayList();
            for (VolumeInfo volumeInfo : this.mStorageManager.getVolumes(0)) {
                if (volumeInfo.getType() == 1 && volumeInfo.isMountedWritable()) {
                    arrayList.add(volumeInfo);
                }
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<VolumeRecord> getVolumeRecords() {
        try {
            return Arrays.asList(this.mStorageManager.getVolumeRecords(0));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getBestVolumeDescription(VolumeInfo volumeInfo) {
        VolumeRecord findRecordByUuid;
        if (volumeInfo == null) {
            return null;
        }
        if (!TextUtils.isEmpty(volumeInfo.fsUuid) && (findRecordByUuid = findRecordByUuid(volumeInfo.fsUuid)) != null && !TextUtils.isEmpty(findRecordByUuid.nickname)) {
            return findRecordByUuid.nickname;
        }
        if (!TextUtils.isEmpty(volumeInfo.getDescription())) {
            return volumeInfo.getDescription();
        }
        if (volumeInfo.disk != null) {
            return volumeInfo.disk.getDescription();
        }
        return null;
    }

    public VolumeInfo getPrimaryPhysicalVolume() {
        for (VolumeInfo volumeInfo : getVolumes()) {
            if (volumeInfo.isPrimaryPhysical()) {
                return volumeInfo;
            }
        }
        return null;
    }

    public void mount(String str) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, AudioParameter.VALUE_MOUNT, new Exception("who's calling?"));
        } else {
            Log.d(TAG, AudioParameter.VALUE_MOUNT, new Exception("who's calling?"));
        }
        try {
            this.mStorageManager.mount(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unmount(String str) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, AudioParameter.VALUE_UNMOUNT, new Exception("who's calling?"));
        } else {
            Log.d(TAG, AudioParameter.VALUE_UNMOUNT, new Exception("who's calling?"));
        }
        try {
            this.mStorageManager.unmount(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void format(String str) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, Telephony.CellBroadcasts.MESSAGE_FORMAT, new Exception("who's calling?"));
        } else {
            Log.d(TAG, Telephony.CellBroadcasts.MESSAGE_FORMAT, new Exception("who's calling?"));
        }
        try {
            this.mStorageManager.format(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public long benchmark(String str) {
        final CompletableFuture completableFuture = new CompletableFuture();
        benchmark(str, new IVoldTaskListener.Stub(this) { // from class: android.os.storage.StorageManager.2
            @Override // android.os.IVoldTaskListener
            public void onStatus(int i, PersistableBundle persistableBundle) {
            }

            @Override // android.os.IVoldTaskListener
            public void onFinished(int i, PersistableBundle persistableBundle) {
                completableFuture.complete(persistableBundle);
            }
        });
        try {
            return ((PersistableBundle) completableFuture.get(3L, TimeUnit.MINUTES)).getLong("run", Long.MAX_VALUE) * 1000000;
        } catch (Exception unused) {
            return Long.MAX_VALUE;
        }
    }

    public void benchmark(String str, IVoldTaskListener iVoldTaskListener) {
        try {
            this.mStorageManager.benchmark(str, iVoldTaskListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partitionPublic(String str) {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "partitionPublic", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "partitionPublic", new Exception("who's calling?"));
        }
        try {
            this.mStorageManager.partitionPublic(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partitionPrivate(String str) {
        try {
            this.mStorageManager.partitionPrivate(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partitionMixed(String str, int i) {
        try {
            this.mStorageManager.partitionMixed(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wipeAdoptableDisks() {
        for (DiskInfo diskInfo : getDisks()) {
            String id = diskInfo.getId();
            if (diskInfo.isAdoptable() || diskInfo.isSd()) {
                Slog.d(TAG, "Found adoptable " + id + "; wiping");
                try {
                    this.mStorageManager.partitionPublic(id);
                } catch (Exception e) {
                    Slog.w(TAG, "Failed to wipe " + id + ", but soldiering onward", e);
                }
            } else {
                Slog.d(TAG, "Ignorning non-adoptable disk " + diskInfo.getId());
            }
        }
    }

    public void setVolumeNickname(String str, String str2) {
        try {
            this.mStorageManager.setVolumeNickname(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumeInited(String str, boolean z) {
        try {
            this.mStorageManager.setVolumeUserFlags(str, z ? 1 : 0, 1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumeSnoozed(String str, boolean z) {
        try {
            this.mStorageManager.setVolumeUserFlags(str, z ? 2 : 0, 2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forgetVolume(String str) {
        try {
            this.mStorageManager.forgetVolume(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getPrimaryStorageUuid() {
        try {
            return this.mStorageManager.getPrimaryStorageUuid();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) {
        try {
            this.mStorageManager.setPrimaryStorageUuid(str, iPackageMoveObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public StorageVolume getStorageVolume(File file) {
        return getStorageVolume(getVolumeList(), file);
    }

    public StorageVolume getStorageVolume(Uri uri) {
        String volumeName = MediaStore.getVolumeName(uri);
        if (Objects.equals(volumeName, "external")) {
            Cursor query = this.mContext.getContentResolver().query(uri, new String[]{"volume_name"}, null, null);
            try {
                if (query.moveToFirst()) {
                    volumeName = query.getString(0);
                }
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        try {
            String currentOpPackageName = ActivityThread.currentOpPackageName();
            if (currentOpPackageName == null) {
                String[] packagesForUid = ActivityThread.getPackageManager().getPackagesForUid(Process.myUid());
                if (packagesForUid == null || packagesForUid.length <= 0) {
                    Log.d(TAG, "No proper package name to use");
                }
                currentOpPackageName = packagesForUid[0];
            }
            int packageUid = ActivityThread.getPackageManager().getPackageUid(currentOpPackageName, 268435456L, this.mContext.getUserId());
            volumeName.hashCode();
            if (volumeName.equals("external_primary")) {
                return getPrimaryStorageVolume();
            }
            if (SemDualAppManager.isDualAppId(this.mContext.getUserId())) {
                for (StorageVolume storageVolume : getStorageVolumesIncludingSharedProfiles()) {
                    if (Objects.equals(storageVolume.getMediaStoreVolumeName(), volumeName)) {
                        return storageVolume;
                    }
                }
            } else {
                for (StorageVolume storageVolume2 : getStorageVolumes()) {
                    if (Objects.equals(storageVolume2.getMediaStoreVolumeName(), volumeName)) {
                        return storageVolume2;
                    }
                }
            }
            throw new IllegalStateException("Unknown volume for " + uri + " -> VOL_NAME[" + volumeName + "], UserId[" + this.mContext.getUserId() + "], PackageName[" + currentOpPackageName + "], CallerUID[" + packageUid + NavigationBarInflaterView.SIZE_MOD_END);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static StorageVolume getStorageVolume(File file, int i) {
        return getStorageVolume(getVolumeList(i, 0), file);
    }

    private static StorageVolume getStorageVolume(StorageVolume[] storageVolumeArr, File file) {
        if (file == null) {
            return null;
        }
        String absolutePath = file.getAbsolutePath();
        if (absolutePath.startsWith(ContentResolver.DEPRECATE_DATA_PREFIX)) {
            return ((StorageManager) AppGlobals.getInitialApplication().getSystemService(StorageManager.class)).getStorageVolume(ContentResolver.translateDeprecatedDataPath(absolutePath));
        }
        try {
            File canonicalFile = file.getCanonicalFile();
            for (StorageVolume storageVolume : storageVolumeArr) {
                if (FileUtils.contains(storageVolume.getPathFile().getCanonicalFile(), canonicalFile)) {
                    return storageVolume;
                }
            }
            return null;
        } catch (IOException unused) {
            Slog.d(TAG, "Could not get canonical path for " + file);
            return null;
        }
    }

    @Deprecated
    public String getVolumeState(String str) {
        StorageVolume storageVolume = getStorageVolume(new File(str));
        if (storageVolume != null) {
            return storageVolume.getState();
        }
        return "unknown";
    }

    public List<StorageVolume> getStorageVolumes() {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), 1536));
        return arrayList;
    }

    public List<StorageVolume> getStorageVolumesIncludingSharedProfiles() {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), 5632));
        return arrayList;
    }

    public List<StorageVolume> getRecentStorageVolumes() {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), 3584));
        return arrayList;
    }

    public StorageVolume getPrimaryStorageVolume() {
        return getVolumeList(this.mContext.getUserId(), 1536)[0];
    }

    public static Pair<String, Long> getPrimaryStoragePathAndSize() {
        return Pair.create(null, Long.valueOf(FileUtils.roundStorageSize(Environment.getDataDirectory().getTotalSpace() + Environment.getRootDirectory().getTotalSpace())));
    }

    public long getPrimaryStorageSize() {
        return FileUtils.roundStorageSize(Environment.getDataDirectory().getTotalSpace() + Environment.getRootDirectory().getTotalSpace());
    }

    public long getInternalStorageBlockDeviceSize() {
        try {
            return this.mStorageManager.getInternalStorageBlockDeviceSize();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void mkdirs(File file) {
        BlockGuard.getVmPolicy().onPathAccess(file.getAbsolutePath());
        try {
            this.mStorageManager.mkdirs(this.mContext.getOpPackageName(), file.getAbsolutePath());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public StorageVolume[] getVolumeList() {
        return getVolumeList(this.mContext.getUserId(), 0);
    }

    public static StorageVolume[] getVolumeList(int i, int i2) {
        try {
            String currentOpPackageName = ActivityThread.currentOpPackageName();
            if (currentOpPackageName == null) {
                String[] packagesForUid = ActivityThread.getPackageManager().getPackagesForUid(Process.myUid());
                if (packagesForUid != null && packagesForUid.length > 0) {
                    currentOpPackageName = packagesForUid[0];
                }
                Log.w(TAG, "Missing package names; no storage volumes available");
                return new StorageVolume[0];
            }
            return sVolumeListCache.query(new VolumeListQuery(i, currentOpPackageName, i2));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public String[] getVolumePaths() {
        StorageVolume[] volumeList = getVolumeList();
        int length = volumeList.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = volumeList[i].getPath();
        }
        return strArr;
    }

    public StorageVolume getPrimaryVolume() {
        return getPrimaryVolume(getVolumeList());
    }

    public static StorageVolume getPrimaryVolume(StorageVolume[] storageVolumeArr) {
        for (StorageVolume storageVolume : storageVolumeArr) {
            if (storageVolume.isPrimary()) {
                return storageVolume;
            }
        }
        throw new IllegalStateException("Missing primary storage");
    }

    public long getStorageBytesUntilLow(File file) {
        return file.getUsableSpace() - getStorageFullBytes(file);
    }

    public long getStorageLowBytes(File file) {
        return Math.min((file.getTotalSpace() * Settings.Global.getInt(this.mResolver, Settings.Global.SYS_STORAGE_THRESHOLD_PERCENTAGE, 5)) / 100, Settings.Global.getLong(this.mResolver, Settings.Global.SYS_STORAGE_THRESHOLD_MAX_BYTES, DEFAULT_THRESHOLD_MAX_BYTES));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public long computeStorageCacheBytes(File file) {
        int i = DeviceConfig.getInt("storage_native_boot", STORAGE_THRESHOLD_PERCENT_HIGH_KEY, 20);
        int i2 = DeviceConfig.getInt("storage_native_boot", CACHE_RESERVE_PERCENT_HIGH_KEY, 10);
        int i3 = DeviceConfig.getInt("storage_native_boot", CACHE_RESERVE_PERCENT_LOW_KEY, 2);
        long totalSpace = file.getTotalSpace();
        long usableSpace = file.getUsableSpace();
        long j = (i * totalSpace) / 100;
        long storageLowBytes = getStorageLowBytes(file);
        if (usableSpace > j) {
            return (totalSpace * i2) / 100;
        }
        if (usableSpace < storageLowBytes) {
            return (totalSpace * i3) / 100;
        }
        double d = ((i2 - i3) * totalSpace) / ((j - storageLowBytes) * 100.0d);
        return Math.round((d * usableSpace) + (((totalSpace * i3) / 100.0d) - (storageLowBytes * d)));
    }

    public long getStorageCacheBytes(File file, int i) {
        if ((i & 1) != 0 || (i & 2) != 0) {
            return 0L;
        }
        if ((i & 4) != 0) {
            return computeStorageCacheBytes(file) / 2;
        }
        return computeStorageCacheBytes(file);
    }

    public long getStorageFullBytes(File file) {
        return Settings.Global.getLong(this.mResolver, Settings.Global.SYS_STORAGE_FULL_THRESHOLD_BYTES, DEFAULT_FULL_THRESHOLD_BYTES);
    }

    public void createUserStorageKeys(int i, boolean z) {
        try {
            this.mStorageManager.createUserStorageKeys(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void destroyUserStorageKeys(int i) {
        try {
            this.mStorageManager.destroyUserStorageKeys(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void lockCeStorage(int i) {
        try {
            this.mStorageManager.lockCeStorage(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void prepareUserStorage(String str, int i, int i2) {
        try {
            this.mStorageManager.prepareUserStorage(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void destroyUserStorage(String str, int i, int i2) {
        try {
            this.mStorageManager.destroyUserStorage(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isCeStorageUnlocked(int i) {
        if (sStorageManager == null) {
            sStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getService(AudioParameter.VALUE_MOUNT));
        }
        if (sStorageManager == null) {
            Slog.w(TAG, "Early during boot, assuming CE storage is locked");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return sStorageManager.isCeStorageUnlocked(i);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isEncrypted(File file) {
        if (FileUtils.contains(Environment.getDataDirectory(), file)) {
            return isEncrypted();
        }
        return FileUtils.contains(Environment.getExpandDirectory(), file);
    }

    public static boolean isEncrypted() {
        return RoSystemProperties.CRYPTO_ENCRYPTED;
    }

    public static boolean isFileEncrypted() {
        if (isEncrypted()) {
            return RoSystemProperties.CRYPTO_FILE_ENCRYPTED;
        }
        return false;
    }

    public static boolean checkPermissionAndAppOp(Context context, boolean z, int i, int i2, String str, String str2, String str3, int i3) {
        return checkPermissionAndAppOp(context, z, i, i2, str, str2, str3, i3, true);
    }

    public static boolean checkPermissionAndCheckOp(Context context, boolean z, int i, int i2, String str, String str2, int i3) {
        return checkPermissionAndAppOp(context, z, i, i2, str, null, str2, i3, false);
    }

    private static boolean checkPermissionAndAppOp(Context context, boolean z, int i, int i2, String str, String str2, String str3, int i3, boolean z2) {
        String str4;
        int i4;
        int checkOpNoThrow;
        if (context.checkPermission(str3, i, i2) != 0) {
            if (!z) {
                return false;
            }
            throw new SecurityException("Permission " + str3 + " denied for package " + str);
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        if (z2) {
            str4 = str;
            i4 = i3;
            checkOpNoThrow = appOpsManager.noteOpNoThrow(i4, i2, str4, str2, (String) null);
        } else {
            str4 = str;
            i4 = i3;
            try {
                appOpsManager.checkPackage(i2, str4);
                checkOpNoThrow = appOpsManager.checkOpNoThrow(i4, i2, str4);
            } catch (SecurityException e) {
                if (z) {
                    throw e;
                }
                return false;
            }
        }
        if (checkOpNoThrow == 0) {
            return true;
        }
        if (checkOpNoThrow != 1 && checkOpNoThrow != 2 && checkOpNoThrow != 3) {
            throw new IllegalStateException(AppOpsManager.opToName(i4) + " has unknown mode " + AppOpsManager.modeToName(checkOpNoThrow));
        }
        if (!z) {
            return false;
        }
        throw new SecurityException("Op " + AppOpsManager.opToName(i4) + " " + AppOpsManager.modeToName(checkOpNoThrow) + " for package " + str4);
    }

    private boolean checkPermissionAndAppOp(boolean z, int i, int i2, String str, String str2, String str3, int i3) {
        return checkPermissionAndAppOp(this.mContext, z, i, i2, str, str2, str3, i3);
    }

    private boolean noteAppOpAllowingLegacy(boolean z, int i, int i2, String str, String str2, int i3) {
        int noteOpNoThrow = this.mAppOps.noteOpNoThrow(i3, i2, str, str2, (String) null);
        if (noteOpNoThrow == 0) {
            return true;
        }
        if (noteOpNoThrow == 1 || noteOpNoThrow == 2 || noteOpNoThrow == 3) {
            if (this.mAppOps.checkOpNoThrow(87, i2, str) == 0) {
                return true;
            }
            if (!z) {
                return false;
            }
            throw new SecurityException("Op " + AppOpsManager.opToName(i3) + " " + AppOpsManager.modeToName(noteOpNoThrow) + " for package " + str);
        }
        throw new IllegalStateException(AppOpsManager.opToName(i3) + " has unknown mode " + AppOpsManager.modeToName(noteOpNoThrow));
    }

    @Deprecated
    public boolean checkPermissionReadImages(boolean z, int i, int i2, String str, String str2) {
        if (checkExternalStoragePermissionAndAppOp(z, i, i2, str, str2, Manifest.permission.READ_EXTERNAL_STORAGE, 59)) {
            return noteAppOpAllowingLegacy(z, i, i2, str, str2, 85);
        }
        return false;
    }

    private boolean checkExternalStoragePermissionAndAppOp(boolean z, int i, int i2, String str, String str2, String str3, int i3) {
        int noteOpNoThrow = this.mAppOps.noteOpNoThrow(92, i2, str, str2, (String) null);
        if (noteOpNoThrow == 0) {
            return true;
        }
        if (noteOpNoThrow == 3 && this.mContext.checkPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE, i, i2) == 0) {
            return true;
        }
        return checkPermissionAndAppOp(z, i, i2, str, str2, str3, i3);
    }

    public ParcelFileDescriptor openProxyFileDescriptor(int i, ProxyFileDescriptorCallback proxyFileDescriptorCallback, Handler handler, ThreadFactory threadFactory) throws IOException {
        boolean z;
        ParcelFileDescriptor openProxyFileDescriptor;
        Preconditions.checkNotNull(proxyFileDescriptorCallback);
        MetricsLogger.count(this.mContext, "storage_open_proxy_file_descriptor", 1);
        while (true) {
            try {
                synchronized (this.mFuseAppLoopLock) {
                    if (this.mFuseAppLoop == null) {
                        AppFuseMount mountProxyFileDescriptorBridge = this.mStorageManager.mountProxyFileDescriptorBridge();
                        if (mountProxyFileDescriptorBridge == null) {
                            throw new IOException("Failed to mount proxy bridge");
                        }
                        this.mFuseAppLoop = new FuseAppLoop(mountProxyFileDescriptorBridge.mountPointId, mountProxyFileDescriptorBridge.fd, threadFactory);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (handler == null) {
                        handler = new Handler(Looper.getMainLooper());
                    }
                    try {
                        int registerCallback = this.mFuseAppLoop.registerCallback(proxyFileDescriptorCallback, handler);
                        openProxyFileDescriptor = this.mStorageManager.openProxyFileDescriptor(this.mFuseAppLoop.getMountPointId(), registerCallback, i);
                        if (openProxyFileDescriptor == null) {
                            this.mFuseAppLoop.unregisterCallback(registerCallback);
                            throw new FuseUnavailableMountException(this.mFuseAppLoop.getMountPointId());
                        }
                    } catch (FuseUnavailableMountException e) {
                        if (z) {
                            throw new IOException(e);
                        }
                        this.mFuseAppLoop = null;
                    }
                }
                return openProxyFileDescriptor;
            } catch (RemoteException e2) {
                throw new IOException(e2);
            }
        }
    }

    public ParcelFileDescriptor openProxyFileDescriptor(int i, ProxyFileDescriptorCallback proxyFileDescriptorCallback) throws IOException {
        return openProxyFileDescriptor(i, proxyFileDescriptorCallback, null, null);
    }

    public ParcelFileDescriptor openProxyFileDescriptor(int i, ProxyFileDescriptorCallback proxyFileDescriptorCallback, Handler handler) throws IOException {
        Preconditions.checkNotNull(handler);
        return openProxyFileDescriptor(i, proxyFileDescriptorCallback, handler, null);
    }

    public int getProxyFileDescriptorMountPointId() {
        int mountPointId;
        synchronized (this.mFuseAppLoopLock) {
            FuseAppLoop fuseAppLoop = this.mFuseAppLoop;
            mountPointId = fuseAppLoop != null ? fuseAppLoop.getMountPointId() : -1;
        }
        return mountPointId;
    }

    public long getCacheQuotaBytes(UUID uuid) throws IOException {
        try {
            return this.mStorageManager.getCacheQuotaBytes(convert(uuid), this.mContext.getApplicationInfo().uid);
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public long getCacheSizeBytes(UUID uuid) throws IOException {
        try {
            return this.mStorageManager.getCacheSizeBytes(convert(uuid), this.mContext.getApplicationInfo().uid);
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public long getAllocatableBytes(UUID uuid) throws IOException {
        return getAllocatableBytes(uuid, 0);
    }

    @SystemApi
    public long getAllocatableBytes(UUID uuid, int i) throws IOException {
        try {
            return this.mStorageManager.getAllocatableBytes(convert(uuid), i, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void allocateBytes(UUID uuid, long j) throws IOException {
        allocateBytes(uuid, j, 0);
    }

    @SystemApi
    public void allocateBytes(UUID uuid, long j, int i) throws IOException {
        try {
            this.mStorageManager.allocateBytes(convert(uuid), j, i, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getExternalStorageMountMode(int i, String str) {
        try {
            return this.mStorageManager.getExternalStorageMountMode(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void allocateBytes(FileDescriptor fileDescriptor, long j) throws IOException {
        allocateBytes(fileDescriptor, j, 0);
    }

    @SystemApi
    public void allocateBytes(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        File file = ParcelFileDescriptor.getFile(fileDescriptor);
        UUID uuidForPath = getUuidForPath(file);
        for (int i2 = 0; i2 < 3; i2++) {
            try {
                long j2 = j - (Os.fstat(fileDescriptor).st_blocks * 512);
                if (j2 > 0) {
                    allocateBytes(uuidForPath, j2, i);
                }
                try {
                    Os.posix_fallocate(fileDescriptor, 0L, j);
                    return;
                } catch (ErrnoException e) {
                    if (e.errno != OsConstants.ENOSYS && e.errno != OsConstants.ENOTSUP) {
                        throw e;
                    }
                    Log.w(TAG, "fallocate() not supported; falling back to ftruncate()");
                    Os.ftruncate(fileDescriptor, j);
                    return;
                }
            } catch (ErrnoException e2) {
                if (e2.errno == OsConstants.ENOSPC) {
                    Log.w(TAG, "Odd, not enough space; let's try again?");
                } else {
                    throw e2.rethrowAsIOException();
                }
            }
        }
        throw new IOException("Well this is embarassing; we can't allocate " + j + " for " + file);
    }

    @SystemApi
    public void updateExternalStorageFileQuotaType(File file, int i) throws IOException {
        int identifier;
        long projectIdForUser;
        if (file.exists()) {
            String canonicalPath = file.getCanonicalPath();
            Matcher matcher = PATTERN_USER_ID.matcher(canonicalPath);
            if (matcher.find()) {
                identifier = Integer.parseInt(matcher.group(1));
            } else {
                StorageVolume storageVolume = getStorageVolume(getVolumeList(this.mContext.getUserId(), this.mContext.checkSelfPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE) == 0 ? 5632 : 1536), file);
                if (storageVolume == null) {
                    Log.w(TAG, "Failed to update quota type for " + canonicalPath);
                    return;
                } else if (!storageVolume.isEmulated()) {
                    return;
                } else {
                    identifier = storageVolume.getOwner().getIdentifier();
                }
            }
            if (identifier < 0) {
                throw new IllegalStateException("Failed to update quota type for " + canonicalPath);
            }
            if (i == 0) {
                projectIdForUser = getProjectIdForUser(identifier, 1000);
            } else if (i == 1) {
                projectIdForUser = getProjectIdForUser(identifier, 1003);
            } else if (i == 2) {
                projectIdForUser = getProjectIdForUser(identifier, 1001);
            } else if (i == 3) {
                projectIdForUser = getProjectIdForUser(identifier, 1002);
            } else {
                throw new IllegalArgumentException("Invalid quota type: " + i);
            }
            if (setQuotaProjectId(canonicalPath, projectIdForUser)) {
                return;
            }
            throw new IOException("Failed to update quota type for " + canonicalPath);
        }
    }

    public void fixupAppDir(File file) {
        try {
            this.mStorageManager.fixupAppDir(file.getCanonicalPath());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IOException e2) {
            Log.e(TAG, "Failed to get canonical path for " + file.getPath(), e2);
        }
    }

    private static void setCacheBehavior(File file, String str, boolean z) throws IOException {
        if (!file.isDirectory()) {
            throw new IOException("Cache behavior can only be set on directories");
        }
        if (z) {
            try {
                Os.setxattr(file.getAbsolutePath(), str, "1".getBytes(StandardCharsets.UTF_8), 0);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            try {
                Os.removexattr(file.getAbsolutePath(), str);
            } catch (ErrnoException e2) {
                if (e2.errno != OsConstants.ENODATA) {
                    throw e2.rethrowAsIOException();
                }
            }
        }
    }

    private static boolean isCacheBehavior(File file, String str) throws IOException {
        try {
            Os.getxattr(file.getAbsolutePath(), str);
            return true;
        } catch (ErrnoException e) {
            if (e.errno == OsConstants.ENODATA) {
                return false;
            }
            throw e.rethrowAsIOException();
        }
    }

    public void setCacheBehaviorGroup(File file, boolean z) throws IOException {
        setCacheBehavior(file, XATTR_CACHE_GROUP, z);
    }

    public boolean isCacheBehaviorGroup(File file) throws IOException {
        return isCacheBehavior(file, XATTR_CACHE_GROUP);
    }

    public void setCacheBehaviorTombstone(File file, boolean z) throws IOException {
        setCacheBehavior(file, XATTR_CACHE_TOMBSTONE, z);
    }

    public boolean isCacheBehaviorTombstone(File file) throws IOException {
        return isCacheBehavior(file, XATTR_CACHE_TOMBSTONE);
    }

    private static boolean isFatVolumeIdentifier(String str) {
        return str.length() == 9 && str.charAt(4) == '-';
    }

    public static UUID convert(String str) {
        if (Objects.equals(str, UUID_PRIVATE_INTERNAL)) {
            return UUID_DEFAULT;
        }
        if (Objects.equals(str, UUID_PRIMARY_PHYSICAL)) {
            return UUID_PRIMARY_PHYSICAL_;
        }
        if (Objects.equals(str, "system")) {
            return UUID_SYSTEM_;
        }
        if (isFatVolumeIdentifier(str)) {
            return UUID.fromString(FAT_UUID_PREFIX + str.replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ""));
        }
        return UUID.fromString(str);
    }

    public static String convert(UUID uuid) {
        if (UUID_DEFAULT.equals(uuid)) {
            return UUID_PRIVATE_INTERNAL;
        }
        if (UUID_PRIMARY_PHYSICAL_.equals(uuid)) {
            return UUID_PRIMARY_PHYSICAL;
        }
        if (UUID_SYSTEM_.equals(uuid)) {
            return "system";
        }
        String uuid2 = uuid.toString();
        if (uuid2.startsWith(FAT_UUID_PREFIX)) {
            String upperCase = uuid2.substring(28).toUpperCase(Locale.US);
            return upperCase.substring(0, 4) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + upperCase.substring(4);
        }
        return uuid.toString();
    }

    public boolean isCheckpointSupported() {
        try {
            return this.mStorageManager.supportsCheckpoint();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void notifyAppIoBlocked(UUID uuid, int i, int i2, int i3) {
        Objects.requireNonNull(uuid);
        try {
            this.mStorageManager.notifyAppIoBlocked(convert(uuid), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void notifyAppIoResumed(UUID uuid, int i, int i2, int i3) {
        Objects.requireNonNull(uuid);
        try {
            this.mStorageManager.notifyAppIoResumed(convert(uuid), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAppIoBlocked(UUID uuid, int i, int i2, int i3) {
        Objects.requireNonNull(uuid);
        try {
            return this.mStorageManager.isAppIoBlocked(convert(uuid), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setCloudMediaProvider(String str) {
        try {
            this.mStorageManager.setCloudMediaProvider(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public String getCloudMediaProvider() {
        try {
            return this.mStorageManager.getCloudMediaProvider();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getInternalStorageRemainingLifetime() {
        try {
            return this.mStorageManager.getInternalStorageRemainingLifetime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getStorageExhaustionBytes() {
        return DEFAULT_EXHAUSTION_THRESHOLD_BYTES;
    }

    public int semGetExternalSdCardHealthState() {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semGetExternalSdCardHealthState", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "semGetExternalSdCardHealthState", new Exception("who's calling?"));
        }
        try {
            return this.mStorageManager.semGetExternalSdCardHealthState();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in StorageManager.semGetExternalSdCardHealthState", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public String semGetExternalSdCardId() {
        if (Process.myUid() == 1000) {
            Slog.who(TAG, "semGetExternalSdCardId", new Exception("who's calling?"));
        } else {
            Log.d(TAG, "semGetExternalSdCardId", new Exception("who's calling?"));
        }
        try {
            return this.mStorageManager.semGetExternalSdCardId();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in StorageManager.semGetExternalSdCardId", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public long getUsedF2fsFileNode() {
        if (Process.myUid() != 1000) {
            Log.d(TAG, "Getting Used FileNode Number is not allowed", new Exception("who's calling?"));
            return -1L;
        }
        try {
            return this.mStorageManager.getUsedF2fsFileNode();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in StorageManager.getUsedF2fsFileNode", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shrinkDataDdp(long j) {
        if (Process.myUid() != 1000) {
            Log.e(TAG, "Setting shrinkDataDdp is not allowed", new Exception("who's calling?"));
            return false;
        }
        try {
            return this.mStorageManager.shrinkDataDdp(j);
        } catch (Exception e) {
            Log.e(TAG, "Exception in StorageManager.shrinkDataDdp", e);
            return false;
        }
    }

    public int reserveDataBlocks(long j) {
        if (Process.myUid() != 1000) {
            Log.e(TAG, "Setting reserveDataBlock is not allowed", new Exception("who's calling?"));
            return -1;
        }
        try {
            return this.mStorageManager.reserveDataBlocks(j);
        } catch (Exception e) {
            Log.e(TAG, "Exception in StorageManager.reserveDataBlock", e);
            return -1;
        }
    }

    private boolean isValidPath(String str) {
        try {
            String canonicalPath = new File(str).getCanonicalPath();
            if (canonicalPath == null) {
                return false;
            }
            if (!canonicalPath.startsWith("/data/media") && !canonicalPath.startsWith("/data/sec")) {
                Slog.d(TAG, "input path is not supported");
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mvFileAtData(String str, String str2) {
        try {
            this.mContext.enforceCallingOrSelfPermission(Manifest.permission.SEM_VOLD_DATA_MOVE, null);
            if (!isAllowedPackageForDataMvCp(getPackageNameByContext())) {
                Log.d(TAG, "Move file at data path is not allowed", new Exception("who's calling?"));
                return false;
            }
            Log.d(TAG, "!@[Move File at data]", new Exception("who's calling?"));
            if (isValidPath(str) && isValidPath(str2)) {
                return this.mStorageManager.mvFileAtData(str, str2);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cpFileAtData(String str, String str2) {
        try {
            this.mContext.enforceCallingOrSelfPermission(Manifest.permission.SEM_VOLD_DATA_MOVE, null);
            if (!isAllowedPackageForDataMvCp(getPackageNameByContext())) {
                Log.d(TAG, "Copy file at data path is not allowed", new Exception("who's calling?"));
                return false;
            }
            Log.d(TAG, "!@[Copy file at data]", new Exception("who's calling?"));
            if (isValidPath(str) && isValidPath(str2)) {
                return this.mStorageManager.cpFileAtData(str, str2);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isAllowedPackageForDataMvCp(String str) {
        if (str != null) {
            for (String str2 : this.mAllowedPackagesForDataMvCp) {
                if (str2.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getPackageNameByContext() {
        String str;
        try {
            str = this.mContext.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        StringBuilder sb = new StringBuilder("getPackageNameByContext : Package name : ");
        sb.append(str == null ? "NULL" : str);
        Log.d(TAG, sb.toString());
        return str;
    }

    public void semManageExternalStorage(String str, int i) {
        Log.d(TAG, "External Storage Manage call by SecApp", new Exception("who's calling?"));
        String packageNameByContext = getPackageNameByContext();
        try {
            if (i == 0) {
                this.mStorageManager.mountBySecApp(str, packageNameByContext);
            } else if (i == 1) {
                this.mStorageManager.unmountBySecApp(str, packageNameByContext);
            } else if (i == 2) {
                this.mStorageManager.formatBySecApp(str, packageNameByContext);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semMoveFileAtData(String str, String str2) {
        try {
            this.mContext.enforceCallingOrSelfPermission(Manifest.permission.SEM_VOLD_DATA_MOVE, null);
            if (!isAllowedPackageForDataMvCp(getPackageNameByContext())) {
                Log.d(TAG, "Move file at data path is not allowed", new Exception("who's calling?"));
                return false;
            }
            Log.d(TAG, "!@[Move File at data]", new Exception("who's calling?"));
            if (isValidPath(str) && isValidPath(str2)) {
                return this.mStorageManager.mvFileAtData(str, str2);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean semCopyFileAtData(String str, String str2) {
        try {
            this.mContext.enforceCallingOrSelfPermission(Manifest.permission.SEM_VOLD_DATA_MOVE, null);
            if (!isAllowedPackageForDataMvCp(getPackageNameByContext())) {
                Log.d(TAG, "Copy file at data path is not allowed", new Exception("who's calling?"));
                return false;
            }
            Log.d(TAG, "!@[Copy file at data]", new Exception("who's calling?"));
            if (isValidPath(str) && isValidPath(str2)) {
                return this.mStorageManager.cpFileAtData(str, str2);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setSensitive(int i, String str) {
        try {
            return this.mStorageManager.setSensitive(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSensitive(String str) {
        try {
            return this.mStorageManager.isSensitive(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean mountSdpMediaStorage(int i) {
        try {
            return this.mStorageManager.mountSdpMediaStorageCmd(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setSdpPolicy(int i) {
        try {
            return this.mStorageManager.setSdpPolicyCmd(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setSdpPolicyToPath(int i, String str) {
        try {
            return this.mStorageManager.setSdpPolicyToPathCmd(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setDualDARPolicy(int i, int i2) {
        try {
            return this.mStorageManager.setDualDARPolicyCmd(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
