package com.android.server.clipboard;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IUriGrantsManager;
import android.app.KeyguardManager;
import android.app.UriGrantsManager;
import android.companion.virtual.VirtualDeviceManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.IClipboard;
import android.content.IOnPrimaryClipChangedListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IUserManager;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.sec.clipboard.IClipboardService;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.ClipboardPolicyObserver;
import android.sec.clipboard.util.SemClipboardPolicy;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SafetyProtectionUtils;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.SparseBooleanArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.autofill.AutofillManagerInternal;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.contentcapture.ContentCaptureManagerInternal;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.semclipboard.SemClipboardService;
import com.android.server.uri.UriGrantsManagerInternal;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ClipboardService extends SystemService {
    public static final long DEFAULT_CLIPBOARD_TIMEOUT_MILLIS = 3600000;
    public static final boolean IS_EMULATOR = SystemProperties.getBoolean("ro.boot.qemu", false);
    public boolean mAllowVirtualDeviceSilos;
    public final ActivityManagerInternal mAmInternal;
    public final AppOpsManager mAppOps;
    public final AutofillManagerInternal mAutofillInternal;
    public final SparseArrayMap mClipboards;
    public int mContainerID;
    public final ContentCaptureManagerInternal mContentCaptureInternal;
    public Context mContext;
    public final Consumer mEmulatorClipboardMonitor;
    public final Object mLock;
    public int mMaxClassificationLength;
    public final IBinder mPermissionOwner;
    public SemPersonaManager mPersonaManager;
    public final PackageManager mPm;
    public SemClipboardService mSemClipboardService;
    public boolean mShowAccessNotifications;
    public final IUriGrantsManager mUgm;
    public final UriGrantsManagerInternal mUgmInternal;
    public final IUserManager mUm;
    public final VirtualDeviceManager mVdm;
    public final VirtualDeviceManagerInternal mVdmInternal;
    public BroadcastReceiver mVirtualDeviceRemovedReceiver;
    public final WindowManagerInternal mWm;
    public final Handler mWorkerHandler;

    public static /* synthetic */ void lambda$new$1(ClipData clipData) {
    }

    public ClipboardService(Context context) {
        super(context);
        this.mClipboards = new SparseArrayMap();
        this.mContext = null;
        this.mPersonaManager = null;
        this.mShowAccessNotifications = true;
        this.mAllowVirtualDeviceSilos = true;
        this.mMaxClassificationLength = 400;
        this.mLock = new Object();
        this.mSemClipboardService = null;
        this.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mUgm = UriGrantsManager.getService();
        UriGrantsManagerInternal uriGrantsManagerInternal = (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mUgmInternal = uriGrantsManagerInternal;
        this.mWm = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        VirtualDeviceManagerInternal virtualDeviceManagerInternal = (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);
        this.mVdmInternal = virtualDeviceManagerInternal;
        this.mVdm = virtualDeviceManagerInternal != null ? (VirtualDeviceManager) getContext().getSystemService(VirtualDeviceManager.class) : null;
        this.mPm = getContext().getPackageManager();
        this.mUm = ServiceManager.getService("user");
        this.mAppOps = (AppOpsManager) getContext().getSystemService("appops");
        this.mContentCaptureInternal = (ContentCaptureManagerInternal) LocalServices.getService(ContentCaptureManagerInternal.class);
        this.mAutofillInternal = (AutofillManagerInternal) LocalServices.getService(AutofillManagerInternal.class);
        IBinder newUriPermissionOwner = uriGrantsManagerInternal.newUriPermissionOwner("clipboard");
        this.mContext = context;
        this.mContainerID = 0;
        context.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_ALLOWED_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_SHARED_ALLOWED_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_ALLOWED_DENYLIST_APP_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mPermissionOwner = newUriPermissionOwner;
        if (IS_EMULATOR) {
            this.mEmulatorClipboardMonitor = new EmulatorClipboardMonitor(new Consumer() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ClipboardService.this.lambda$new$0((ClipData) obj);
                }
            });
        } else {
            this.mEmulatorClipboardMonitor = new Consumer() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ClipboardService.lambda$new$1((ClipData) obj);
                }
            };
        }
        updateConfig();
        DeviceConfig.addOnPropertiesChangedListener("clipboard", getContext().getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda2
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                ClipboardService.this.lambda$new$2(properties);
            }
        });
        HandlerThread handlerThread = new HandlerThread("ClipboardService");
        handlerThread.start();
        this.mWorkerHandler = handlerThread.getThreadHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ClipData clipData) {
        synchronized (this.mLock) {
            setPrimaryClipInternalLocked(getClipboardLocked(0, 0), clipData, 1000, (String) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(DeviceConfig.Properties properties) {
        updateConfig();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("clipboard", new ClipboardImpl());
        if (this.mVdmInternal != null) {
            registerVirtualDeviceRemovedListener();
        }
    }

    public final void registerVirtualDeviceRemovedListener() {
        if (this.mVirtualDeviceRemovedReceiver != null) {
            return;
        }
        this.mVirtualDeviceRemovedReceiver = new BroadcastReceiver() { // from class: com.android.server.clipboard.ClipboardService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED")) {
                    int intExtra = intent.getIntExtra("android.companion.virtual.extra.VIRTUAL_DEVICE_ID", -1);
                    synchronized (ClipboardService.this.mLock) {
                        for (int numMaps = ClipboardService.this.mClipboards.numMaps() - 1; numMaps >= 0; numMaps--) {
                            ClipboardService.this.mClipboards.delete(ClipboardService.this.mClipboards.keyAt(numMaps), Integer.valueOf(intExtra));
                        }
                    }
                }
            }
        };
        getContext().registerReceiver(this.mVirtualDeviceRemovedReceiver, new IntentFilter("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED"), 4);
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mClipboards.delete(targetUser.getUserIdentifier());
        }
    }

    public final void updateConfig() {
        synchronized (this.mLock) {
            this.mShowAccessNotifications = DeviceConfig.getBoolean("clipboard", "show_access_notifications", true);
            this.mAllowVirtualDeviceSilos = DeviceConfig.getBoolean("clipboard", "allow_virtualdevice_silos", true);
            this.mMaxClassificationLength = DeviceConfig.getInt("clipboard", "max_classification_length", 400);
        }
    }

    public class ListenerInfo {
        public final String mAttributionTag;
        public final String mPackageName;
        public final int mUid;

        public ListenerInfo(int i, String str, String str2) {
            this.mUid = i;
            this.mPackageName = str;
            this.mAttributionTag = str2;
        }
    }

    public class Clipboard {
        public final int deviceId;
        public String mPrimaryClipPackage;
        public TextClassifier mTextClassifier;
        public ClipData primaryClip;
        public ClipData primaryClipForKNOX;
        public final int userId;
        public final RemoteCallbackList primaryClipListeners = new RemoteCallbackList();
        public final RemoteCallbackList primaryClipListenersForKNOX = new RemoteCallbackList();
        public Map primaryClipByUid = new HashMap();
        public int primaryClipUid = 9999;
        public final SparseBooleanArray mNotifiedUids = new SparseBooleanArray();
        public final SparseBooleanArray mNotifiedTextClassifierUids = new SparseBooleanArray();
        public final HashSet activePermissionOwners = new HashSet();

        public Clipboard(int i, int i2) {
            this.userId = i;
            this.deviceId = i2;
        }
    }

    public final boolean isInternalSysWindowAppWithWindowFocus(String str) {
        return this.mPm.checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW", str) == 0 && this.mWm.isUidFocused(Binder.getCallingUid());
    }

    public final int getIntendingUserId(String str, int i) {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        return (!UserManager.supportsMultipleUsers() || userId == i) ? userId : this.mAmInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 2, "checkClipboardServiceCallingUser", str);
    }

    public final int getIntendingUid(String str, int i) {
        return UserHandle.getUid(getIntendingUserId(str, i), UserHandle.getAppId(Binder.getCallingUid()));
    }

    public final int getIntendingDeviceId(int i, int i2) {
        VirtualDeviceManagerInternal virtualDeviceManagerInternal = this.mVdmInternal;
        if (virtualDeviceManagerInternal == null) {
            return 0;
        }
        ArraySet deviceIdsForUid = virtualDeviceManagerInternal.getDeviceIdsForUid(i2);
        synchronized (this.mLock) {
            if (!this.mAllowVirtualDeviceSilos && (!deviceIdsForUid.isEmpty() || i != 0)) {
                return -1;
            }
            if (i != 0) {
                if (this.mVdmInternal.getDeviceOwnerUid(i) == i2 || deviceIdsForUid.contains(Integer.valueOf(i))) {
                    return i;
                }
                return -1;
            }
            if (deviceIdsForUid.isEmpty()) {
                return 0;
            }
            if (deviceIdsForUid.size() > 1) {
                return -1;
            }
            return ((Integer) deviceIdsForUid.valueAt(0)).intValue();
        }
    }

    public class ClipboardImpl extends IClipboard.Stub {
        public final Handler mClipboardClearHandler;

        public ClipboardImpl() {
            this.mClipboardClearHandler = new ClipboardClearHandler(ClipboardService.this.mWorkerHandler.getLooper());
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (RuntimeException e) {
                if (!(e instanceof SecurityException)) {
                    Slog.wtf("clipboard", "Exception: ", e);
                }
                throw e;
            }
        }

        public void setPrimaryClip(ClipData clipData, String str, String str2, int i, int i2) {
            checkAndSetPrimaryClip(clipData, str, str2, i, i2, str);
        }

        public void setPrimaryClipAsPackage(ClipData clipData, String str, String str2, int i, int i2, String str3) {
            ClipboardService.this.getContext().enforceCallingOrSelfPermission("android.permission.SET_CLIP_SOURCE", "Requires SET_CLIP_SOURCE permission");
            checkAndSetPrimaryClip(clipData, str, str2, i, i2, str3);
        }

        public boolean areClipboardAccessNotificationsEnabledForUser(int i) {
            if (ClipboardService.this.getContext().checkCallingOrSelfPermission("android.permission.MANAGE_CLIPBOARD_ACCESS_NOTIFICATION") != 0) {
                throw new SecurityException("areClipboardAccessNotificationsEnable requires permission MANAGE_CLIPBOARD_ACCESS_NOTIFICATION");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.Secure.getIntForUser(ClipboardService.this.getContext().getContentResolver(), "clipboard_show_access_notifications", getDefaultClipboardAccessNotificationsSetting(), i) != 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) {
            if (ClipboardService.this.getContext().checkCallingOrSelfPermission("android.permission.MANAGE_CLIPBOARD_ACCESS_NOTIFICATION") != 0) {
                throw new SecurityException("areClipboardAccessNotificationsEnable requires permission MANAGE_CLIPBOARD_ACCESS_NOTIFICATION");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Settings.Secure.putInt(ClipboardService.this.getContext().createContextAsUser(UserHandle.of(i), 0).getContentResolver(), "clipboard_show_access_notifications", z ? 1 : 0);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getDefaultClipboardAccessNotificationsSetting() {
            return DeviceConfig.getBoolean("clipboard", "show_access_notifications", true) ? 1 : 0;
        }

        public final void checkAndSetPrimaryClip(ClipData clipData, String str, String str2, int i, int i2, String str3) {
            if (clipData == null || clipData.getItemCount() <= 0) {
                throw new IllegalArgumentException("No items");
            }
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (ClipboardService.this.clipboardAccessAllowed(30, str, str2, intendingUid, userId, intendingDeviceId)) {
                if (ClipboardService.this.getSemClipboardService() != null && clipData.getDescription().getExtras() != null && clipData.getDescription().getExtras().getBoolean("direct_clip") && SemClipboardPolicy.getInstance().canAccessSemClipboard(ClipboardService.this.mContext, str3, i)) {
                    ClipboardService.this.getSemClipboardService().setPrimaryClip(clipData, intendingUid);
                    return;
                }
                synchronized (ClipboardService.this.mLock) {
                    Clipboard clipboardLocked = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    if (isClipboardAllowed(false, getPersonaId()) == 0) {
                        clipboardLocked.primaryClip = null;
                        clipboardLocked.primaryClipForKNOX = null;
                        clipboardLocked.primaryClipByUid.clear();
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.server.clipboard.ClipboardService.ClipboardImpl.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(new ContextThemeWrapper(ClipboardService.this.mContext, R.style.Theme.DeviceDefault.Light), R.string.config_sharedConnectivityServicePackage, 0).show();
                            }
                        });
                        return;
                    }
                    if (isClipboardShareAllowed(getPersonaId()) == 0) {
                        clipboardLocked.primaryClip = null;
                        clipboardLocked.primaryClipForKNOX = null;
                    } else {
                        clipboardLocked.primaryClipByUid.clear();
                    }
                    ClipboardService.this.checkDataOwner(clipData, intendingUid);
                    synchronized (ClipboardService.this.mLock) {
                        Clipboard clipboardLocked2 = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                        if (isClipboardShareAllowed(getPersonaId()) == 0) {
                            clipboardLocked2.primaryClipByUid.put(Integer.valueOf(Binder.getCallingUid()), clipData);
                            return;
                        }
                        if (ClipboardService.this.mContainerID != 0) {
                            clipboardLocked2.primaryClipForKNOX = clipData;
                        } else {
                            clipboardLocked2.primaryClip = clipData;
                            clipboardLocked2.primaryClipForKNOX = clipData;
                        }
                        synchronized (ClipboardService.this.mLock) {
                            scheduleAutoClear(i, intendingUid, intendingDeviceId);
                            ClipboardService.this.setPrimaryClipInternalLocked(clipData, intendingUid, intendingDeviceId, str3);
                            if (ClipboardService.this.getSemClipboardService() != null) {
                                ClipboardService.this.getSemClipboardService().setPrimaryClip(clipData, intendingUid);
                            }
                        }
                    }
                }
            }
        }

        public final void scheduleAutoClear(int i, int i2, int i3) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (DeviceConfig.getBoolean("clipboard", "auto_clear_enabled", true)) {
                    Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i3));
                    this.mClipboardClearHandler.removeEqualMessages(101, pair);
                    this.mClipboardClearHandler.sendMessageDelayed(Message.obtain(this.mClipboardClearHandler, 101, i, i2, pair), getTimeoutForAutoClear());
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final long getTimeoutForAutoClear() {
            return DeviceConfig.getLong("clipboard", "auto_clear_timeout", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
        }

        public void clearPrimaryClip(String str, String str2, int i, int i2) {
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (ClipboardService.this.clipboardAccessAllowed(30, str, str2, intendingUid, userId, intendingDeviceId)) {
                synchronized (ClipboardService.this.mLock) {
                    this.mClipboardClearHandler.removeEqualMessages(101, new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
                    ClipboardService.this.setPrimaryClipInternalLocked((ClipData) null, intendingUid, intendingDeviceId, str);
                }
            }
        }

        public ClipData getPrimaryClip(String str, String str2, int i, int i2) {
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId) || ClipboardService.this.isDeviceLocked(userId)) {
                return null;
            }
            synchronized (ClipboardService.this.mLock) {
                if (isClipboardAllowed(true, getPersonaId()) == 0) {
                    ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClip = null;
                    ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipForKNOX = null;
                    ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipByUid.clear();
                    return null;
                }
                if (isClipboardShareAllowed(getPersonaId()) != 0) {
                    ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipByUid.clear();
                } else {
                    ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClip = null;
                    ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipForKNOX = null;
                }
                try {
                    ClipboardService.this.addActiveOwnerLocked(intendingUid, intendingDeviceId, str);
                    if (isClipboardShareAllowed(getPersonaId()) == 0) {
                        return (ClipData) ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipByUid.get(Integer.valueOf(Binder.getCallingUid()));
                    }
                    if (ClipboardService.this.mContainerID != 0) {
                        Slog.d("ClipboardService", "getPrimaryClip knox return!!!");
                        return ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipForKNOX;
                    }
                    Clipboard clipboardLocked = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    ClipboardService.this.showAccessNotificationLocked(str, intendingUid, userId, clipboardLocked);
                    ClipboardService.this.notifyTextClassifierLocked(clipboardLocked, str, intendingUid);
                    if (clipboardLocked.primaryClip != null) {
                        scheduleAutoClear(i, intendingUid, intendingDeviceId);
                    }
                    return clipboardLocked.primaryClip;
                } catch (SecurityException unused) {
                    Slog.i("ClipboardService", "Could not grant permission to primary clip. Clearing clipboard.");
                    ClipboardService.this.setPrimaryClipInternalLocked((ClipData) null, intendingUid, intendingDeviceId, str);
                    return null;
                }
            }
        }

        public ClipDescription getPrimaryClipDescription(String str, String str2, int i, int i2) {
            ClipDescription description;
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false) || ClipboardService.this.isDeviceLocked(userId)) {
                return null;
            }
            synchronized (ClipboardService.this.mLock) {
                ClipData clipData = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClip;
                description = clipData != null ? clipData.getDescription() : null;
            }
            return description;
        }

        public boolean hasPrimaryClip(String str, String str2, int i, int i2) {
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false) || ClipboardService.this.isDeviceLocked(userId)) {
                return false;
            }
            synchronized (ClipboardService.this.mLock) {
                Clipboard clipboardLocked = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                int isClipboardAllowed = isClipboardAllowed(false, getPersonaId());
                int isClipboardShareAllowed = isClipboardShareAllowed(getPersonaId());
                if (isClipboardAllowed == 0) {
                    clipboardLocked.primaryClip = null;
                    clipboardLocked.primaryClipForKNOX = null;
                    clipboardLocked.primaryClipByUid.clear();
                    return false;
                }
                if (isClipboardShareAllowed != 0) {
                    clipboardLocked.primaryClipByUid.clear();
                    return ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClip != null;
                }
                clipboardLocked.primaryClip = null;
                clipboardLocked.primaryClipForKNOX = null;
                return clipboardLocked.primaryClipByUid.get(Integer.valueOf(Binder.getCallingUid())) != null;
            }
        }

        public void addPrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) {
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (intendingDeviceId == -1) {
                Slog.i("ClipboardService", "addPrimaryClipChangedListener invalid deviceId for userId:" + i + " uid:" + intendingUid + " callingPackage:" + str + " requestedDeviceId:" + i2);
                return;
            }
            synchronized (ClipboardService.this.mLock) {
                ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClipListeners.register(iOnPrimaryClipChangedListener, ClipboardService.this.new ListenerInfo(intendingUid, str, str2));
            }
        }

        public void removePrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) {
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int intendingUserId = ClipboardService.this.getIntendingUserId(str, i);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (intendingDeviceId == -1) {
                Slog.i("ClipboardService", "removePrimaryClipChangedListener invalid deviceId for userId:" + i + " uid:" + intendingUid + " callingPackage:" + str);
                return;
            }
            synchronized (ClipboardService.this.mLock) {
                ClipboardService.this.getClipboardLocked(intendingUserId, intendingDeviceId).primaryClipListeners.unregister(iOnPrimaryClipChangedListener);
            }
        }

        public boolean hasClipboardText(String str, String str2, int i, int i2) {
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            boolean clipboardAccessAllowed = ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false);
            boolean z = false;
            if (!clipboardAccessAllowed || ClipboardService.this.isDeviceLocked(userId)) {
                return false;
            }
            synchronized (ClipboardService.this.mLock) {
                ClipData clipData = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId).primaryClip;
                if (clipData == null) {
                    return false;
                }
                CharSequence text = clipData.getItemAt(0).getText();
                if (text != null && text.length() > 0) {
                    z = true;
                }
                return z;
            }
        }

        public String getPrimaryClipSource(String str, String str2, int i, int i2) {
            ClipboardService.this.getContext().enforceCallingOrSelfPermission("android.permission.SET_CLIP_SOURCE", "Requires SET_CLIP_SOURCE permission");
            int intendingUid = ClipboardService.this.getIntendingUid(str, i);
            int userId = UserHandle.getUserId(intendingUid);
            int intendingDeviceId = ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false) || ClipboardService.this.isDeviceLocked(userId)) {
                return null;
            }
            synchronized (ClipboardService.this.mLock) {
                Clipboard clipboardLocked = ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                if (clipboardLocked.primaryClip == null) {
                    return null;
                }
                return clipboardLocked.mPrimaryClipPackage;
            }
        }

        public final int isClipboardAllowed(boolean z, int i) {
            if (ClipboardService.this.mContext == null) {
                return -1;
            }
            return (ClipboardPolicyObserver.getInstance(ClipboardService.this.mContext).isClipboardAllowed(i) && ClipboardPolicyObserver.getInstance(ClipboardService.this.mContext).isPackageAllowed(i)) ? 1 : 0;
        }

        public final int isClipboardShareAllowed(int i) {
            if (ClipboardService.this.mContext == null) {
                return -1;
            }
            return ClipboardPolicyObserver.getInstance(ClipboardService.this.mContext).isClipboardSharedAllowed(i) ? 1 : 0;
        }

        public final int getUserId() {
            return UserHandle.getCallingUserId();
        }

        public int getPersonaId() {
            if (ClipboardConstants.KNOX_V2_ENABLED && getPersonaManager()) {
                int focusedKnoxId = ClipboardService.this.mPersonaManager.getFocusedKnoxId();
                return focusedKnoxId == 0 ? getUserId() : focusedKnoxId;
            }
            return getUserId();
        }

        public final boolean getPersonaManager() {
            if (ClipboardService.this.mContext != null && ClipboardService.this.mPersonaManager == null) {
                ClipboardService clipboardService = ClipboardService.this;
                clipboardService.mPersonaManager = (SemPersonaManager) clipboardService.mContext.getSystemService("persona");
            }
            if (ClipboardService.this.mPersonaManager != null) {
                return true;
            }
            Slog.e("ClipboardService", "personaManager is null!");
            return false;
        }

        public class ClipboardClearHandler extends Handler {
            public ClipboardClearHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 101) {
                    int i = message.arg1;
                    int i2 = message.arg2;
                    int intValue = ((Integer) ((Pair) message.obj).second).intValue();
                    synchronized (ClipboardService.this.mLock) {
                        if (ClipboardService.this.getClipboardLocked(i, intValue).primaryClip != null) {
                            FrameworkStatsLog.write(FrameworkStatsLog.CLIPBOARD_CLEARED, 1);
                            ClipboardService.this.setPrimaryClipInternalLocked((ClipData) null, i2, intValue, (String) null);
                        }
                    }
                    return;
                }
                Slog.wtf("ClipboardService", "ClipboardClearHandler received unknown message " + message.what);
            }
        }
    }

    public final Clipboard getClipboardLocked(int i, int i2) {
        Clipboard clipboard = (Clipboard) this.mClipboards.get(i, Integer.valueOf(i2));
        if (clipboard != null) {
            return clipboard;
        }
        Clipboard clipboard2 = new Clipboard(i, i2);
        this.mClipboards.add(i, Integer.valueOf(i2), clipboard2);
        return clipboard2;
    }

    public List getRelatedProfiles(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mUm.getProfiles(i, true);
            } catch (RemoteException e) {
                Slog.e("ClipboardService", "Remote Exception calling UserManager: " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasRestriction(String str, int i) {
        try {
            return this.mUm.hasUserRestriction(str, i);
        } catch (RemoteException e) {
            Slog.e("ClipboardService", "Remote Exception calling UserManager.getUserRestrictions: ", e);
            return true;
        }
    }

    public final void setPrimaryClipInternalLocked(ClipData clipData, int i, int i2, String str) {
        int size;
        if (i2 == 0) {
            this.mEmulatorClipboardMonitor.accept(clipData);
        }
        int userId = UserHandle.getUserId(i);
        setPrimaryClipInternalLocked(getClipboardLocked(userId, i2), clipData, i, str);
        List relatedProfiles = getRelatedProfiles(userId);
        if (relatedProfiles == null || (size = relatedProfiles.size()) <= 1) {
            return;
        }
        if (!(!hasRestriction("no_cross_profile_copy_paste", userId))) {
            clipData = null;
        } else if (clipData != null) {
            ClipData clipData2 = new ClipData(clipData);
            for (int itemCount = clipData2.getItemCount() - 1; itemCount >= 0; itemCount--) {
                clipData2.setItemAt(itemCount, new ClipData.Item(clipData2.getItemAt(itemCount)));
            }
            if (!SemImageClipData.class.getSimpleName().equals(clipData2.getDescription().getLabel())) {
                clipData2.fixUrisLight(userId);
            }
            clipData = clipData2;
        }
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = ((UserInfo) relatedProfiles.get(i3)).id;
            if (i4 != userId && (!hasRestriction("no_sharing_into_profile", i4))) {
                setPrimaryClipInternalNoClassifyLocked(getClipboardLocked(i4, i2), clipData, i, str);
            }
        }
    }

    public final void setPrimaryClipInternalLocked(Clipboard clipboard, ClipData clipData, int i, String str) {
        int userId = UserHandle.getUserId(i);
        if (clipData != null) {
            startClassificationLocked(clipData, userId, clipboard.deviceId);
        }
        setPrimaryClipInternalNoClassifyLocked(clipboard, clipData, i, str);
    }

    public final void setPrimaryClipInternalNoClassifyLocked(Clipboard clipboard, ClipData clipData, int i, String str) {
        ClipDescription description;
        revokeUris(clipboard);
        clipboard.activePermissionOwners.clear();
        if (clipData == null && clipboard.primaryClip == null) {
            return;
        }
        clipboard.primaryClip = clipData;
        clipboard.mNotifiedUids.clear();
        clipboard.mNotifiedTextClassifierUids.clear();
        if (clipData != null) {
            clipboard.primaryClipUid = i;
            clipboard.mPrimaryClipPackage = str;
        } else {
            clipboard.primaryClipUid = 9999;
            clipboard.mPrimaryClipPackage = null;
        }
        if (clipData != null && (description = clipData.getDescription()) != null) {
            description.setTimestamp(System.currentTimeMillis());
        }
        sendClipChangedBroadcast(clipboard);
    }

    public final void sendClipChangedBroadcast(Clipboard clipboard) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int beginBroadcast = clipboard.primaryClipListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                ListenerInfo listenerInfo = (ListenerInfo) clipboard.primaryClipListeners.getBroadcastCookie(i);
                String str = listenerInfo.mPackageName;
                String str2 = listenerInfo.mAttributionTag;
                int i2 = listenerInfo.mUid;
                if (clipboardAccessAllowed(29, str, str2, i2, UserHandle.getUserId(i2), clipboard.deviceId)) {
                    clipboard.primaryClipListeners.getBroadcastItem(i).dispatchPrimaryClipChanged();
                }
            } catch (RemoteException | SecurityException unused) {
            } catch (Throwable th) {
                clipboard.primaryClipListeners.finishBroadcast();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        clipboard.primaryClipListeners.finishBroadcast();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void startClassificationLocked(final ClipData clipData, final int i, final int i2) {
        final CharSequence text = clipData.getItemCount() == 0 ? null : clipData.getItemAt(0).getText();
        if (TextUtils.isEmpty(text) || text.length() > this.mMaxClassificationLength) {
            clipData.getDescription().setClassificationStatus(2);
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final TextClassifier createTextClassificationSession = createTextClassificationManagerAsUser(i).createTextClassificationSession(new TextClassificationContext.Builder(getContext().getPackageName(), "clipboard").build());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (text.length() > createTextClassificationSession.getMaxGenerateLinksTextLength()) {
                clipData.getDescription().setClassificationStatus(2);
            } else {
                this.mWorkerHandler.post(new Runnable() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ClipboardService.this.lambda$startClassificationLocked$3(text, clipData, createTextClassificationSession, i, i2);
                    }
                });
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: doClassification, reason: merged with bridge method [inline-methods] */
    public final void lambda$startClassificationLocked$3(CharSequence charSequence, ClipData clipData, TextClassifier textClassifier, int i, int i2) {
        int i3;
        TextLinks generateLinks = textClassifier.generateLinks(new TextLinks.Request.Builder(charSequence).build());
        ArrayMap arrayMap = new ArrayMap();
        Iterator<TextLinks.TextLink> it = generateLinks.getLinks().iterator();
        while (true) {
            i3 = 0;
            if (!it.hasNext()) {
                break;
            }
            TextLinks.TextLink next = it.next();
            while (i3 < next.getEntityCount()) {
                String entity = next.getEntity(i3);
                float confidenceScore = next.getConfidenceScore(entity);
                if (confidenceScore > ((Float) arrayMap.getOrDefault(entity, Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON))).floatValue()) {
                    arrayMap.put(entity, Float.valueOf(confidenceScore));
                }
                i3++;
            }
        }
        synchronized (this.mLock) {
            Clipboard clipboardLocked = getClipboardLocked(i, i2);
            if (clipboardLocked.primaryClip == clipData) {
                applyClassificationAndSendBroadcastLocked(clipboardLocked, arrayMap, generateLinks, textClassifier);
                List relatedProfiles = getRelatedProfiles(i);
                if (relatedProfiles != null) {
                    int size = relatedProfiles.size();
                    while (i3 < size) {
                        int i4 = ((UserInfo) relatedProfiles.get(i3)).id;
                        if (i4 != i && (!hasRestriction("no_sharing_into_profile", i4))) {
                            Clipboard clipboardLocked2 = getClipboardLocked(i4, i2);
                            if (hasTextLocked(clipboardLocked2, charSequence)) {
                                applyClassificationAndSendBroadcastLocked(clipboardLocked2, arrayMap, generateLinks, textClassifier);
                            }
                        }
                        i3++;
                    }
                }
            }
        }
    }

    public final void applyClassificationAndSendBroadcastLocked(Clipboard clipboard, ArrayMap arrayMap, TextLinks textLinks, TextClassifier textClassifier) {
        clipboard.mTextClassifier = textClassifier;
        clipboard.primaryClip.getDescription().setConfidenceScores(arrayMap);
        if (!textLinks.getLinks().isEmpty()) {
            clipboard.primaryClip.getItemAt(0).setTextLinks(textLinks);
        }
        sendClipChangedBroadcast(clipboard);
    }

    public final boolean hasTextLocked(Clipboard clipboard, CharSequence charSequence) {
        ClipData clipData = clipboard.primaryClip;
        return clipData != null && clipData.getItemCount() > 0 && charSequence.equals(clipboard.primaryClip.getItemAt(0).getText());
    }

    public final boolean isDeviceLocked(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            KeyguardManager keyguardManager = (KeyguardManager) getContext().getSystemService(KeyguardManager.class);
            if (keyguardManager != null) {
                if (keyguardManager.isDeviceLocked(i)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void checkUriOwner(Uri uri, int i) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgmInternal.checkGrantUriPermission(i, null, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void checkItemOwner(ClipData.Item item, int i) {
        if (item.getUri() != null) {
            checkUriOwner(item.getUri(), i);
        }
        Intent intent = item.getIntent();
        if (intent == null || intent.getData() == null) {
            return;
        }
        checkUriOwner(intent.getData(), i);
    }

    public final void checkDataOwner(ClipData clipData, int i) {
        int itemCount = clipData.getItemCount();
        for (int i2 = 0; i2 < itemCount; i2++) {
            checkItemOwner(clipData.getItemAt(i2), i);
        }
    }

    public final void grantUriPermission(Uri uri, int i, String str, int i2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgm.grantUriPermissionFromOwner(this.mPermissionOwner, i, str, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)), i2);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void grantItemPermission(ClipData.Item item, int i, String str, int i2) {
        if (item.getUri() != null) {
            grantUriPermission(item.getUri(), i, str, i2);
        }
        Intent intent = item.getIntent();
        if (intent == null || intent.getData() == null) {
            return;
        }
        grantUriPermission(intent.getData(), i, str, i2);
    }

    public final void addActiveOwnerLocked(int i, int i2, String str) {
        PackageInfo packageInfo;
        IPackageManager packageManager = AppGlobals.getPackageManager();
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            packageInfo = packageManager.getPackageInfo(str, 0L, callingUserId);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (packageInfo == null) {
            throw new IllegalArgumentException("Unknown package " + str);
        }
        if (!UserHandle.isSameApp(packageInfo.applicationInfo.uid, i)) {
            throw new SecurityException("Calling uid " + i + " does not own package " + str);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Clipboard clipboardLocked = getClipboardLocked(UserHandle.getUserId(i), i2);
        if (clipboardLocked.primaryClip == null || clipboardLocked.activePermissionOwners.contains(str)) {
            return;
        }
        int itemCount = clipboardLocked.primaryClip.getItemCount();
        for (int i3 = 0; i3 < itemCount; i3++) {
            grantItemPermission(clipboardLocked.primaryClip.getItemAt(i3), clipboardLocked.primaryClipUid, str, UserHandle.getUserId(i));
            if (KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(str)) {
                Slog.i("ClipboardService", "adding mcfds");
                grantItemPermission(clipboardLocked.primaryClip.getItemAt(i3), clipboardLocked.primaryClipUid, "com.samsung.android.mcfds", UserHandle.getUserId(i));
            }
        }
        clipboardLocked.activePermissionOwners.add(str);
    }

    public final void revokeUriPermission(Uri uri, int i) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgmInternal.revokeUriPermissionFromOwner(this.mPermissionOwner, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void revokeItemPermission(ClipData.Item item, int i) {
        if (item.getUri() != null) {
            revokeUriPermission(item.getUri(), i);
        }
        Intent intent = item.getIntent();
        if (intent == null || intent.getData() == null) {
            return;
        }
        revokeUriPermission(intent.getData(), i);
    }

    public final void revokeUris(Clipboard clipboard) {
        ClipData clipData = clipboard.primaryClip;
        if (clipData == null) {
            return;
        }
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            revokeItemPermission(clipboard.primaryClip.getItemAt(i), clipboard.primaryClipUid);
        }
    }

    public final boolean clipboardAccessAllowed(int i, String str, String str2, int i2, int i3, int i4) {
        return clipboardAccessAllowed(i, str, str2, i2, i3, i4, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean clipboardAccessAllowed(int i, String str, String str2, int i2, int i3, int i4, boolean z) {
        AutofillManagerInternal autofillManagerInternal;
        ContentCaptureManagerInternal contentCaptureManagerInternal;
        int checkOp;
        this.mAppOps.checkPackage(i2, str);
        if (i4 == -1) {
            Slog.w("ClipboardService", "Clipboard access denied to " + i2 + "/" + str + " due to invalid device id");
            return false;
        }
        boolean isDefaultIme = this.mPm.checkPermission("android.permission.READ_CLIPBOARD_IN_BACKGROUND", str) == 0 ? true : isDefaultIme(i3, str);
        if (i == 29) {
            if (!isDefaultIme) {
                isDefaultIme = isDefaultDeviceAndUidFocused(i4, i2) || isVirtualDeviceAndUidFocused(i4, i2) || isInternalSysWindowAppWithWindowFocus(str);
            }
            if (!isDefaultIme && (contentCaptureManagerInternal = this.mContentCaptureInternal) != null) {
                isDefaultIme = contentCaptureManagerInternal.isContentCaptureServiceForUser(i2, i3);
            }
            if (!isDefaultIme && (autofillManagerInternal = this.mAutofillInternal) != null) {
                isDefaultIme = autofillManagerInternal.isAugmentedAutofillServiceForUser(i2, i3);
            }
            if (!isDefaultIme && i4 != 0) {
                VirtualDeviceManagerInternal virtualDeviceManagerInternal = this.mVdmInternal;
                if (virtualDeviceManagerInternal == null || virtualDeviceManagerInternal.getDeviceOwnerUid(i4) != i2) {
                    isDefaultIme = false;
                }
            }
            if (!isDefaultIme) {
                if (z) {
                    checkOp = this.mAppOps.noteOp(i, i2, str, str2, (String) null);
                } else {
                    checkOp = this.mAppOps.checkOp(i, i2, str);
                }
                return checkOp == 0;
            }
            Slog.e("ClipboardService", "Denying clipboard access to " + str + ", application is not in focus nor is it a system service for user " + i3);
            return false;
        }
        if (i != 30) {
            throw new IllegalArgumentException("Unknown clipboard appop " + i);
        }
        isDefaultIme = true;
        if (!isDefaultIme) {
        }
    }

    public final boolean isDefaultDeviceAndUidFocused(int i, int i2) {
        return i == 0 && this.mWm.isUidFocused(i2);
    }

    public final boolean isVirtualDeviceAndUidFocused(int i, int i2) {
        if (i == 0 || this.mVdm == null) {
            return false;
        }
        return this.mVdm.getDeviceIdForDisplayId(this.mWm.getTopFocusedDisplayId()) == i && this.mWm.isUidFocused(i2);
    }

    public final boolean isDefaultIme(int i, String str) {
        String stringForUser = Settings.Secure.getStringForUser(getContext().getContentResolver(), "default_input_method", i);
        if (TextUtils.isEmpty(stringForUser)) {
            return false;
        }
        return ComponentName.unflattenFromString(stringForUser).getPackageName().equals(str);
    }

    public final void showAccessNotificationLocked(final String str, int i, final int i2, Clipboard clipboard) {
        if (clipboard.primaryClip == null || Settings.Secure.getInt(getContext().getContentResolver(), "clipboard_show_access_notifications", this.mShowAccessNotifications ? 1 : 0) == 0 || UserHandle.isSameApp(i, clipboard.primaryClipUid) || isDefaultIme(i2, str)) {
            return;
        }
        ContentCaptureManagerInternal contentCaptureManagerInternal = this.mContentCaptureInternal;
        if (contentCaptureManagerInternal == null || !contentCaptureManagerInternal.isContentCaptureServiceForUser(i, i2)) {
            AutofillManagerInternal autofillManagerInternal = this.mAutofillInternal;
            if ((autofillManagerInternal == null || !autofillManagerInternal.isAugmentedAutofillServiceForUser(i, i2)) && this.mPm.checkPermission("android.permission.SUPPRESS_CLIPBOARD_ACCESS_NOTIFICATION", str) != 0) {
                int i3 = clipboard.deviceId;
                if ((i3 == 0 || this.mVdmInternal.getDeviceOwnerUid(i3) != i) && !clipboard.mNotifiedUids.get(i)) {
                    final ArraySet toastContexts = getToastContexts(clipboard);
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda5
                        public final void runOrThrow() {
                            ClipboardService.this.lambda$showAccessNotificationLocked$4(str, i2, toastContexts);
                        }
                    });
                    clipboard.mNotifiedUids.put(i, true);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAccessNotificationLocked$4(String str, int i, ArraySet arraySet) {
        Toast makeText;
        try {
            PackageManager packageManager = this.mPm;
            String string = getContext().getString(17043049, packageManager.getApplicationLabel(packageManager.getApplicationInfoAsUser(str, 0, i)));
            Slog.i("ClipboardService", string);
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                Context context = (Context) arraySet.valueAt(i2);
                if (SafetyProtectionUtils.shouldShowSafetyProtectionResources(getContext())) {
                    makeText = Toast.makeCustomToastWithIcon(context, UiThread.get().getLooper(), string, 1, getContext().getDrawable(R.drawable.ic_safety_protection));
                } else {
                    makeText = Toast.makeText(context, UiThread.get().getLooper(), string, 1);
                }
                makeText.show();
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final ArraySet getToastContexts(Clipboard clipboard) {
        Display display;
        ArraySet arraySet = new ArraySet();
        if (clipboard.deviceId != 0) {
            DisplayManager displayManager = (DisplayManager) getContext().getSystemService(DisplayManager.class);
            int topFocusedDisplayId = this.mWm.getTopFocusedDisplayId();
            ArraySet displayIdsForDevice = this.mVdmInternal.getDisplayIdsForDevice(clipboard.deviceId);
            if (displayIdsForDevice.contains(Integer.valueOf(topFocusedDisplayId)) && (display = displayManager.getDisplay(topFocusedDisplayId)) != null) {
                arraySet.add(getContext().createDisplayContext(display));
                return arraySet;
            }
            for (int i = 0; i < displayIdsForDevice.size(); i++) {
                Display display2 = displayManager.getDisplay(((Integer) displayIdsForDevice.valueAt(i)).intValue());
                if (display2 != null) {
                    arraySet.add(getContext().createDisplayContext(display2));
                }
            }
            if (!arraySet.isEmpty()) {
                return arraySet;
            }
            Slog.e("ClipboardService", "getToastContexts Couldn't find any VirtualDisplays for VirtualDevice " + clipboard.deviceId);
        }
        arraySet.add(getContext());
        return arraySet;
    }

    public static boolean isText(ClipData clipData) {
        if (clipData.getItemCount() > 1) {
            return false;
        }
        ClipData.Item itemAt = clipData.getItemAt(0);
        return !TextUtils.isEmpty(itemAt.getText()) && itemAt.getUri() == null && itemAt.getIntent() == null;
    }

    public final SemClipboardService getSemClipboardService() {
        SemClipboardService semClipboardService = this.mSemClipboardService;
        if (semClipboardService != null) {
            return semClipboardService;
        }
        SemClipboardService asInterface = IClipboardService.Stub.asInterface(ServiceManager.getService("semclipboard"));
        this.mSemClipboardService = asInterface;
        return asInterface;
    }

    public final void notifyTextClassifierLocked(final Clipboard clipboard, final String str, int i) {
        final TextClassifier textClassifier;
        ClipData clipData = clipboard.primaryClip;
        if (clipData == null || clipData.getItemAt(0) == null || !isText(clipboard.primaryClip) || (textClassifier = clipboard.mTextClassifier) == null || !this.mWm.isUidFocused(i) || clipboard.mNotifiedTextClassifierUids.get(i)) {
            return;
        }
        clipboard.mNotifiedTextClassifierUids.put(i, true);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                ClipboardService.lambda$notifyTextClassifierLocked$5(str, clipboard, textClassifier);
            }
        });
    }

    public static /* synthetic */ void lambda$notifyTextClassifierLocked$5(String str, Clipboard clipboard, TextClassifier textClassifier) {
        textClassifier.onTextClassifierEvent(((TextClassifierEvent.TextLinkifyEvent.Builder) ((TextClassifierEvent.TextLinkifyEvent.Builder) new TextClassifierEvent.TextLinkifyEvent.Builder(22).setEventContext(new TextClassificationContext.Builder(str, "clipboard").build())).setExtras(Bundle.forPair("source_package", clipboard.mPrimaryClipPackage))).build());
    }

    public final TextClassificationManager createTextClassificationManagerAsUser(int i) {
        return (TextClassificationManager) getContext().createContextAsUser(UserHandle.of(i), 0).getSystemService(TextClassificationManager.class);
    }
}
