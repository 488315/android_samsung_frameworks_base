package com.android.server.statusbar;

import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.Notification;
import android.app.StatusBarManager;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ServiceInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.drawable.Icon;
import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.WindowInsets;
import android.view.accessibility.Flags;
import android.widget.RemoteViews;
import com.android.internal.carlife.IStatusBarCarLife;
import com.android.internal.logging.InstanceId;
import com.android.internal.os.TransferPipe;
import com.android.internal.statusbar.ISessionListener;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.DumpUtils;
import com.android.internal.view.AppearanceRegion;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.notification.NotificationAttentionHelper;
import com.android.server.notification.NotificationDelegate;
import com.android.server.notification.NotificationManagerService;
import com.android.server.notification.NotificationRecord;
import com.android.server.pm.UserManagerInternal;
import com.android.server.policy.GlobalActions;
import com.android.server.power.ShutdownCheckPoints;
import com.android.server.power.ShutdownThread;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.edge.EdgeManagerInternal;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StatusBarManagerService extends IStatusBarService.Stub implements DisplayManager.DisplayListener {
    public final ActivityManagerInternal mActivityManagerInternal;
    public final ActivityTaskManagerInternal mActivityTaskManager;
    public volatile IStatusBar mBar;
    public final Object mBarLock;
    public volatile ArrayMap mBarMap;
    public IBiometricContextListener mBiometricContextListener;
    public volatile IStatusBarCarLife mCarLifeBar;
    public final Context mContext;
    public final ArrayMap mCurrentRequestAddTilePackages;
    public int mCurrentUserId;
    public final AnonymousClass1 mDesktopModeListener;
    public final ArrayList mDisableHistoryList;
    public final ArrayList mDisableRecords;
    public final SparseArray mDisplayUiState;
    public final SparseArray mDisplayUiStateDex;
    public EdgeManagerInternal mEdgeInternal;
    public GlobalActions mGlobalActionListener;
    public final AnonymousClass3 mGlobalActionsProvider;
    public final Handler mHandler;
    public final ArrayMap mIcons;
    public final AnonymousClass2 mInternalService;
    public final ConcurrentHashMap mIsSecCustomTileMap;
    public int mLastSystemKey;
    public final Object mLock;
    public NotificationDelegate mNotificationDelegate;
    public IOverlayManager mOverlayManager;
    public final PackageManagerInternal mPackageManagerInternal;
    public final ArrayMap mPanelExpandStateMap;
    public final ArrayMap mQsPanelExpandStateMap;
    public final SessionMonitor mSessionMonitor;
    public final ArrayList mStatusBarHistoryList;
    public boolean mSysUiSafeMode;
    public final IBinder mSysUiVisToken;
    public final TileRequestTracker mTileRequestTracker;
    public boolean mTracingEnabled;
    public IUdfpsRefreshRateRequestCallback mUdfpsRefreshRateRequestCallback;
    public final UserManagerInternal mUserManagerInternal;
    public static final long REQUEST_TIME_OUT = TimeUnit.MINUTES.toNanos(5);
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static final boolean DEBUG_SAFEMODE = Integer.toString(1).equals(SystemProperties.get("debug.sysui.safemode", "0"));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.statusbar.StatusBarManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 implements StatusBarManagerInternal {
        public AnonymousClass2() {
        }

        public final void abortTransientToType(int i, int i2, int i3) {
            StatusBarManagerService.this.getUiState(i, i3).mTransientBarTypes &= ~i2;
            synchronized (StatusBarManagerService.this.mBarLock) {
                if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3)) != null) {
                    try {
                        ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3))).abortTransient(i, i2);
                    } catch (RemoteException unused) {
                        Slog.i("StatusBarManagerService", "RemoteException for abortTransient");
                    }
                } else if (CoreRune.CARLIFE_NAVBAR && i3 == 2 && StatusBarManagerService.this.mCarLifeBar != null) {
                    try {
                        StatusBarManagerService.this.mCarLifeBar.abortTransient(i, i2);
                    } catch (RemoteException unused2) {
                        Slog.i("StatusBarManagerService", "RemoteException for abortTransient");
                    }
                }
            }
        }

        public final void sendKeyEventToDesktopTaskbarToType(KeyEvent keyEvent, int i) {
            StatusBarManagerService.this.enforceStatusBarService();
            StatusBarManagerService statusBarManagerService = StatusBarManagerService.this;
            statusBarManagerService.getClass();
            try {
                if (statusBarManagerService.mBarMap.get(Integer.valueOf(i)) != null) {
                    try {
                        ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i))).sendKeyEventToDesktopTaskbar(keyEvent);
                    } catch (RemoteException unused) {
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Slog.w("StatusBarManagerService", "checkBarMapState barType =" + i, e);
            } catch (ConcurrentModificationException e2) {
                Slog.w("StatusBarManagerService", "checkBarMapState barType =" + i, e2);
            }
        }

        public final void showTransientToType(int i, int i2, int i3, boolean z) {
            StatusBarManagerService.this.getUiState(i, i3).mTransientBarTypes |= i2;
            synchronized (StatusBarManagerService.this.mBarLock) {
                if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3)) != null) {
                    try {
                        ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i3))).showTransient(i, i2, z);
                    } catch (RemoteException unused) {
                        Slog.i("StatusBarManagerService", "RemoteException for showTransient");
                    }
                } else if (CoreRune.CARLIFE_NAVBAR && i3 == 2 && StatusBarManagerService.this.mCarLifeBar != null) {
                    try {
                        StatusBarManagerService.this.mCarLifeBar.showTransient(i, i2, z);
                    } catch (RemoteException unused2) {
                        Slog.i("StatusBarManagerService", "RemoteException for showTransient");
                    }
                }
            }
        }

        public final void toggleRecentApps() {
            IStatusBar iStatusBar;
            IStatusBar iStatusBar2 = StatusBarManagerService.this.mBar;
            if (iStatusBar2 != null) {
                try {
                    iStatusBar2.toggleRecentApps();
                    for (Map.Entry entry : StatusBarManagerService.this.mBarMap.entrySet()) {
                        if (((Integer) entry.getKey()).intValue() != 0 && (iStatusBar = (IStatusBar) entry.getValue()) != null) {
                            iStatusBar.hideRecentApps(false, true);
                        }
                    }
                } catch (RemoteException unused) {
                }
            }
        }

        public final void toggleRecentAppsToType(int i) {
            IStatusBar iStatusBar;
            synchronized (StatusBarManagerService.this.mBarLock) {
                if (StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i)) != null) {
                    try {
                        ((IStatusBar) StatusBarManagerService.this.mBarMap.get(Integer.valueOf(i))).toggleRecentApps();
                        for (Map.Entry entry : StatusBarManagerService.this.mBarMap.entrySet()) {
                            if (((Integer) entry.getKey()).intValue() != i && (iStatusBar = (IStatusBar) entry.getValue()) != null) {
                                iStatusBar.hideRecentApps(false, true);
                            }
                        }
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.statusbar.StatusBarManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.statusbar.StatusBarManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ StatusBarManagerService this$0;

        public /* synthetic */ AnonymousClass5(StatusBarManagerService statusBarManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = statusBarManagerService;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mBar = null;
                    synchronized (this.this$0.mBarLock) {
                        this.this$0.mBarMap.remove(0);
                    }
                    this.this$0.notifyBarAttachChanged();
                    return;
                default:
                    this.this$0.mCarLifeBar = null;
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisableRecord implements IBinder.DeathRecipient {
        public final int barType;
        public final int displayId;
        public String pkg;
        public final IBinder token;
        public final int userId;
        public int what1;
        public int what2;

        public DisableRecord(int i, IBinder iBinder, int i2, int i3) {
            this.userId = i;
            this.token = iBinder;
            this.barType = i2;
            this.displayId = i3;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("binder died for pkg="), this.pkg, "StatusBarManagerService");
            StatusBarManagerService.this.disableForUserToType(0, this.token, this.pkg, this.userId, this.barType);
            StatusBarManagerService.this.disable2ForUserToType(0, this.token, this.pkg, this.userId, this.barType);
            this.token.unlinkToDeath(this, 0);
        }

        public final int getFlags(int i) {
            if (i == 1) {
                return this.what1;
            }
            if (i == 2) {
                return this.what2;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Can't get unsupported disable flag ", "StatusBarManagerService");
            return 0;
        }

        public final void setFlags(int i, int i2, String str) {
            if (i2 == 1) {
                this.what1 = i;
            } else if (i2 != 2) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "Can't set unsupported disable flag ", ": 0x");
                m.append(Integer.toHexString(i));
                Slog.w("StatusBarManagerService", m.toString());
            } else {
                this.what2 = i;
            }
            this.pkg = str;
        }

        public final String toString() {
            return String.format("barType=%d userId=%d what1=0x%08X what2=0x%08X pkg=%s token=%s", Integer.valueOf(this.barType), Integer.valueOf(this.userId), Integer.valueOf(this.what1), Integer.valueOf(this.what2), this.pkg, this.token);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UiState {
        public int mBehavior;
        public int mTransientBarTypes;
        public int mAppearance = 0;
        public AppearanceRegion[] mAppearanceRegions = new AppearanceRegion[0];
        public boolean mNavbarColorManagedByIme = false;
        public int mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
        public String mPackageName = "none";
        public int mDisabled1 = 0;
        public int mDisabled2 = 0;
        public int mImeWindowVis = 0;
        public int mImeBackDisposition = 0;
        public boolean mShowImeSwitcher = false;
        public IBinder mImeToken = null;
        public LetterboxDetails[] mLetterboxDetails = new LetterboxDetails[0];
    }

    /* renamed from: -$$Nest$msetDisableFlags, reason: not valid java name */
    public static void m960$$Nest$msetDisableFlags(StatusBarManagerService statusBarManagerService, int i, int i2, String str, int i3) {
        statusBarManagerService.enforceStatusBarService();
        int i4 = (-134152193) & i2;
        if (i4 != 0) {
            Slog.e("StatusBarManagerService", AudioChannelMask$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown disable flags: 0x"), i4), new RuntimeException());
        }
        synchronized (statusBarManagerService.mLock) {
            statusBarManagerService.disableLocked(i, statusBarManagerService.mCurrentUserId, i2, 1, i3, statusBarManagerService.mSysUiVisToken, str);
        }
    }

    public StatusBarManagerService(Context context) {
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mIcons = new ArrayMap();
        this.mDisableRecords = new ArrayList();
        this.mSysUiVisToken = new Binder();
        this.mLock = new Object();
        this.mLastSystemKey = -1;
        SparseArray sparseArray = new SparseArray();
        this.mDisplayUiState = sparseArray;
        this.mCurrentRequestAddTilePackages = new ArrayMap();
        this.mEdgeInternal = null;
        this.mBarLock = new Object();
        this.mBarMap = new ArrayMap();
        SparseArray sparseArray2 = new SparseArray();
        this.mDisplayUiStateDex = sparseArray2;
        this.mPanelExpandStateMap = new ArrayMap();
        this.mQsPanelExpandStateMap = new ArrayMap();
        SemDesktopModeManager.DesktopModeListener desktopModeListener = new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.server.statusbar.StatusBarManagerService.1
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                Slog.d("StatusBarManagerService", "onDesktopModeStateChanged state = " + semDesktopModeState);
                if (semDesktopModeState.getState() == 20 && semDesktopModeState.getEnabled() == 1) {
                    synchronized (StatusBarManagerService.this.mLock) {
                        try {
                            for (int size = StatusBarManagerService.this.mDisableRecords.size() - 1; -1 < size; size--) {
                                DisableRecord disableRecord = (DisableRecord) StatusBarManagerService.this.mDisableRecords.get(size);
                                if (disableRecord.barType == 1) {
                                    Slog.d("StatusBarManagerService", "remove record of STATUS_BAR_DEX r = " + disableRecord);
                                    StatusBarManagerService.this.mDisableRecords.remove(size);
                                    disableRecord.token.unlinkToDeath(disableRecord, 0);
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        };
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mGlobalActionsProvider = new AnonymousClass3();
        this.mIsSecCustomTileMap = new ConcurrentHashMap();
        this.mDisableHistoryList = new ArrayList();
        this.mStatusBarHistoryList = new ArrayList();
        this.mSysUiSafeMode = false;
        this.mContext = context;
        LocalServices.addService(StatusBarManagerInternal.class, anonymousClass2);
        sparseArray.put(0, new UiState());
        sparseArray2.put(0, new UiState());
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(desktopModeListener);
        }
        ((DisplayManager) context.getSystemService("display")).registerDisplayListener(this, handler);
        this.mActivityTaskManager = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mTileRequestTracker = new TileRequestTracker(context);
        this.mSessionMonitor = new SessionMonitor(context);
        this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    public static final Context getUiContext() {
        return ActivityThread.currentActivityThread().getSystemUiContext();
    }

    public final void addTile(ComponentName componentName) {
        try {
            if (Flags.a11yQsShortcut()) {
                enforceStatusBarOrShell();
                if (this.mBar == null) {
                } else {
                    this.mBar.addQsTileToFrontOrEnd(componentName, false);
                }
            } else {
                enforceStatusBarOrShell();
                if (this.mBar == null) {
                } else {
                    this.mBar.addQsTile(componentName);
                }
            }
        } catch (RemoteException unused) {
        }
    }

    public final void cancelRequestAddTile(String str) {
        enforceStatusBar();
        clearTileAddRequest(str);
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.cancelRequestAddTile(str);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "requestAddTile", e);
            }
        }
    }

    public final void checkCallingUidPackage(int i, int i2, String str) {
        if (UserHandle.getAppId(i) != UserHandle.getAppId(this.mPackageManagerInternal.getPackageUid(str, 0L, i2))) {
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Package ", str, " does not belong to the calling uid "));
        }
    }

    public final boolean checkCanCollapseStatusBar(String str) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (CompatChanges.isChangeEnabled(173031413L, callingUid)) {
            enforceStatusBar();
            return true;
        }
        if (this.mContext.checkPermission("android.permission.STATUS_BAR", callingPid, callingUid) == 0) {
            return true;
        }
        enforceExpandStatusBar();
        if (ActivityTaskManagerService.this.canCloseSystemDialogs(callingPid, callingUid)) {
            return true;
        }
        Slog.e("StatusBarManagerService", "Permission Denial: Method " + str + "() requires permission android.permission.STATUS_BAR, ignoring call.");
        return false;
    }

    public final void clearInlineReplyUriPermissions(String str) {
        enforceStatusBarService();
        Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).clearInlineReplyUriPermissions(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearNotificationEffects() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).clearEffects();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean clearTileAddRequest(String str) {
        boolean z;
        synchronized (this.mCurrentRequestAddTilePackages) {
            z = this.mCurrentRequestAddTilePackages.remove(str) != null;
        }
        return z;
    }

    public final void clickTile(ComponentName componentName) {
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.clickQsTile(componentName);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void collapsePanels() {
        if (checkCanCollapseStatusBar("collapsePanels") && this.mBar != null) {
            try {
                this.mBar.animateCollapsePanels();
            } catch (RemoteException unused) {
            }
        }
    }

    public final void collapsePanelsToType(int i) {
        if (checkCanCollapseStatusBar("collapsePanelsToType")) {
            synchronized (this.mBarLock) {
                if (this.mBarMap.get(Integer.valueOf(i)) != null) {
                    try {
                        ((IStatusBar) this.mBarMap.get(Integer.valueOf(i))).animateCollapsePanels();
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    public final void disable(int i, IBinder iBinder, String str) {
        disableForUser(i, iBinder, str, this.mCurrentUserId);
    }

    public final void disable2(int i, IBinder iBinder, String str) {
        disable2ForUser(i, iBinder, str, this.mCurrentUserId);
    }

    public final void disable2ForUser(int i, IBinder iBinder, String str, int i2) {
        enforceStatusBar();
        synchronized (this.mLock) {
            disableLocked(0, i2, i, 2, 0, iBinder, str);
        }
    }

    public final void disable2ForUserToType(int i, IBinder iBinder, String str, int i2, int i3) {
        enforceStatusBar();
        synchronized (this.mLock) {
            disableLocked(0, i2, i, 2, i3, iBinder, str);
        }
    }

    public final void disable2ToType(int i, IBinder iBinder, String str, int i2) {
        disable2ForUserToType(i, iBinder, str, this.mCurrentUserId, i2);
    }

    public final void disableForUser(int i, IBinder iBinder, String str, int i2) {
        enforceStatusBar();
        synchronized (this.mLock) {
            disableLocked(0, i2, i, 1, 0, iBinder, str);
        }
    }

    public final void disableForUserToType(int i, IBinder iBinder, String str, int i2, int i3) {
        enforceStatusBar();
        synchronized (this.mLock) {
            disableLocked(0, i2, i, 1, i3, iBinder, str);
        }
    }

    public final void disableLocked(int i, int i2, int i3, int i4, int i5, IBinder iBinder, String str) {
        int i6;
        IBinder iBinder2;
        String str2;
        String str3;
        DisableRecord disableRecord;
        int i7;
        String str4;
        DisableRecord disableRecord2;
        if (str == null || !str.contains(";")) {
            i6 = i2;
            iBinder2 = iBinder;
            str2 = str;
            str3 = null;
        } else {
            String[] split = str.split(";");
            String str5 = split[0];
            i6 = i2;
            iBinder2 = iBinder;
            str3 = split[1];
            str2 = str5;
        }
        Pair findMatchingRecordLocked = findMatchingRecordLocked(i6, iBinder2, i5);
        int intValue = ((Integer) findMatchingRecordLocked.first).intValue();
        DisableRecord disableRecord3 = (DisableRecord) findMatchingRecordLocked.second;
        if (iBinder.isBinderAlive()) {
            if (disableRecord3 != null) {
                if (disableRecord3.getFlags(i4) != i3) {
                    i7 = intValue;
                    disableRecord = disableRecord3;
                    str4 = str2;
                    makeDisableHistory(i2, i3, iBinder, str2, str3, i4, i5, i);
                } else {
                    disableRecord = disableRecord3;
                    i7 = intValue;
                    str4 = str2;
                }
                if (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN") && i4 == 1 && (23068672 & i3) != 0) {
                    disableRecord2 = disableRecord;
                    if (disableRecord2.displayId == i) {
                        disableRecord2.setFlags(i3, i4, str4);
                    } else {
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i3, "Ignored flags(", "), displayId is mismatched. record.display: ");
                        m.append(disableRecord2.displayId);
                        m.append(" and displayId: ");
                        m.append(i);
                        Slog.i("StatusBarManagerService", m.toString());
                    }
                } else {
                    disableRecord2 = disableRecord;
                    disableRecord2.setFlags(i3, i4, str4);
                    Slog.d("StatusBarManagerService", "update existing record: " + disableRecord2);
                }
                if (disableRecord2.what1 == 0 && disableRecord2.what2 == 0) {
                    this.mDisableRecords.remove(i7);
                    disableRecord2.token.unlinkToDeath(disableRecord2, 0);
                }
            } else {
                DisableRecord disableRecord4 = new DisableRecord(i2, iBinder, i5, i);
                disableRecord4.setFlags(i3, i4, str2);
                this.mDisableRecords.add(disableRecord4);
                if (i3 != 0) {
                    makeDisableHistory(i2, i3, iBinder, str2, str3, i4, i5, i);
                }
            }
        } else if (disableRecord3 != null) {
            this.mDisableRecords.remove(intValue);
            disableRecord3.token.unlinkToDeath(disableRecord3, 0);
        }
        final int gatherDisableActionsLocked = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN") ? gatherDisableActionsLocked(this.mCurrentUserId, 1, i5, i) : gatherDisableActionsLocked(this.mCurrentUserId, 1, i5);
        int gatherDisableActionsLocked2 = gatherDisableActionsLocked(this.mCurrentUserId, 2, i5);
        UiState uiState = getUiState(i, i5);
        if (uiState.mDisabled1 != gatherDisableActionsLocked || uiState.mDisabled2 != gatherDisableActionsLocked2) {
            uiState.mDisabled1 = gatherDisableActionsLocked;
            uiState.mDisabled2 = gatherDisableActionsLocked2;
            this.mHandler.post(new Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarManagerService statusBarManagerService = StatusBarManagerService.this;
                    int i8 = gatherDisableActionsLocked;
                    NotificationManagerService.AnonymousClass2 anonymousClass2 = (NotificationManagerService.AnonymousClass2) statusBarManagerService.mNotificationDelegate;
                    synchronized (NotificationManagerService.this.mNotificationLock) {
                        NotificationAttentionHelper notificationAttentionHelper = NotificationManagerService.this.mAttentionHelper;
                        notificationAttentionHelper.getClass();
                        boolean z = (i8 & 262144) != 0;
                        notificationAttentionHelper.mDisableNotificationEffects = z;
                        if (z) {
                            notificationAttentionHelper.clearSoundLocked();
                            notificationAttentionHelper.clearVibrateLocked();
                            notificationAttentionHelper.mLights.clear();
                            notificationAttentionHelper.updateLightsLocked();
                        }
                    }
                }
            });
            synchronized (this.mBarLock) {
                IStatusBar iStatusBar = (IStatusBar) this.mBarMap.get(Integer.valueOf(i5));
                if (iStatusBar != null) {
                    try {
                        iStatusBar.disable(i, gatherDisableActionsLocked, gatherDisableActionsLocked2);
                    } catch (RemoteException e) {
                        Slog.e("StatusBarManagerService", "disable Exception = " + e);
                    }
                }
            }
        }
        if (this.mEdgeInternal == null) {
            this.mEdgeInternal = (EdgeManagerInternal) LocalServices.getService(EdgeManagerInternal.class);
        }
        EdgeManagerInternal edgeManagerInternal = this.mEdgeInternal;
        if (edgeManagerInternal != null) {
            edgeManagerInternal.statusBarDisabled(gatherDisableActionsLocked, gatherDisableActionsLocked2);
        }
    }

    public final void disableToType(int i, IBinder iBinder, String str, int i2) {
        disableForUserToType(i, iBinder, str, this.mCurrentUserId, i2);
    }

    public final void dismissInattentiveSleepWarning(boolean z) {
        enforceStatusBarService();
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.dismissInattentiveSleepWarning(z);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ArrayList arrayList;
        if (DumpUtils.checkDumpPermission(this.mContext, "StatusBarManagerService", printWriter)) {
            boolean z = false;
            for (String str : strArr) {
                if ("--proto".equals(str)) {
                    z = true;
                }
            }
            if (z) {
                if (this.mBar == null) {
                    return;
                }
                try {
                    TransferPipe transferPipe = new TransferPipe();
                    try {
                        this.mBar.dumpProto(strArr, transferPipe.getWriteFd());
                        transferPipe.go(fileDescriptor);
                        transferPipe.close();
                        return;
                    } finally {
                    }
                } catch (Throwable th) {
                    Slog.e("StatusBarManagerService", "Error sending command to IStatusBar", th);
                    return;
                }
            }
            synchronized (this.mLock) {
                for (int i = 0; i < this.mDisplayUiState.size(); i++) {
                    try {
                        int keyAt = this.mDisplayUiState.keyAt(i);
                        UiState uiState = (UiState) this.mDisplayUiState.get(keyAt);
                        printWriter.println("  displayId=" + keyAt);
                        printWriter.println("    mDisabled1=0x" + Integer.toHexString(uiState.mDisabled1));
                        printWriter.println("    mDisabled2=0x" + Integer.toHexString(uiState.mDisabled2));
                    } finally {
                    }
                }
                for (int i2 = 0; i2 < this.mDisplayUiStateDex.size(); i2++) {
                    int keyAt2 = this.mDisplayUiStateDex.keyAt(i2);
                    UiState uiState2 = (UiState) this.mDisplayUiStateDex.get(keyAt2);
                    printWriter.println("  DexdisplayId=" + keyAt2);
                    printWriter.println("    mDexDisabled1=0x" + Integer.toHexString(uiState2.mDisabled1));
                    printWriter.println("    mDexDisabled2=0x" + Integer.toHexString(uiState2.mDisabled2));
                }
                int size = this.mDisableRecords.size();
                printWriter.println("  mDisableRecords.size=" + size);
                for (int i3 = 0; i3 < size; i3++) {
                    printWriter.println("    [" + i3 + "] " + ((DisableRecord) this.mDisableRecords.get(i3)));
                }
                printWriter.println("  mCurrentUserId=" + this.mCurrentUserId);
                printWriter.println("  mIcons=");
                for (String str2 : this.mIcons.keySet()) {
                    printWriter.println("    ");
                    printWriter.print(str2);
                    printWriter.print(" -> ");
                    StatusBarIcon statusBarIcon = (StatusBarIcon) this.mIcons.get(str2);
                    printWriter.print(statusBarIcon);
                    if (!TextUtils.isEmpty(statusBarIcon.contentDescription)) {
                        printWriter.print(" \"");
                        printWriter.print(statusBarIcon.contentDescription);
                        printWriter.print("\"");
                    }
                    printWriter.println();
                }
                synchronized (this.mCurrentRequestAddTilePackages) {
                    arrayList = new ArrayList(this.mCurrentRequestAddTilePackages.keySet());
                }
                printWriter.println("  mCurrentRequestAddTilePackages=[");
                int size2 = arrayList.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    printWriter.println("    " + ((String) arrayList.get(i4)) + ",");
                }
                printWriter.println("  ]");
                this.mTileRequestTracker.dump(new IndentingPrintWriter(printWriter, "  ").increaseIndent());
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mSysUiSafeMode="), this.mSysUiSafeMode, printWriter);
            synchronized (this.mDisableHistoryList) {
                try {
                    int size3 = this.mDisableHistoryList.size();
                    printWriter.println("  mDisableHistoryList.size=" + size3);
                    for (int i5 = 0; i5 < size3; i5++) {
                        printWriter.println("    [" + i5 + "] " + ((String) this.mDisableHistoryList.get(i5)));
                    }
                } finally {
                }
            }
            synchronized (this.mStatusBarHistoryList) {
                try {
                    int size4 = this.mStatusBarHistoryList.size();
                    printWriter.println("  mStatusBarHistoryList.size=" + size4);
                    for (int i6 = 0; i6 < size4; i6++) {
                        printWriter.println("    [" + i6 + "] " + ((String) this.mStatusBarHistoryList.get(i6)));
                    }
                } finally {
                }
            }
            if (this.mIsSecCustomTileMap.isEmpty()) {
                return;
            }
            printWriter.println(" mIsSecCustomTileMap=" + this.mIsSecCustomTileMap);
        }
    }

    public final void enforceExpandStatusBar() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.EXPAND_STATUS_BAR", "StatusBarManagerService");
        makeStatusBarHistory(1);
    }

    public final void enforceStatusBar() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR", "StatusBarManagerService");
        makeStatusBarHistory(0);
    }

    public final void enforceStatusBarOrShell() {
        if (Binder.getCallingUid() == 2000) {
            return;
        }
        enforceStatusBar();
    }

    public final void enforceStatusBarService() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", "StatusBarManagerService");
        makeStatusBarHistory(2);
    }

    public final void expandNotificationsPanel() {
        enforceExpandStatusBar();
        if ((((UiState) this.mDisplayUiState.get(0)).mDisabled2 & 4) == 4 || this.mBar == null) {
            return;
        }
        try {
            this.mBar.animateExpandNotificationsPanel();
        } catch (RemoteException unused) {
        }
    }

    public final void expandNotificationsPanelToType(int i) {
        enforceExpandStatusBar();
        synchronized (this.mBarLock) {
            if (this.mBarMap.get(Integer.valueOf(i)) != null) {
                try {
                    ((IStatusBar) this.mBarMap.get(Integer.valueOf(i))).animateExpandNotificationsPanel();
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void expandSettingsPanel(String str) {
        enforceExpandStatusBar();
        if (this.mBar != null) {
            try {
                this.mBar.animateExpandSettingsPanel(str);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void expandSettingsPanelToType(String str, int i) {
        enforceExpandStatusBar();
        synchronized (this.mBarLock) {
            if (this.mBarMap.get(Integer.valueOf(i)) != null) {
                try {
                    ((IStatusBar) this.mBarMap.get(Integer.valueOf(i))).animateExpandSettingsPanel(str);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final Pair findMatchingRecordLocked(int i, IBinder iBinder, int i2) {
        DisableRecord disableRecord;
        int size = this.mDisableRecords.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                disableRecord = null;
                break;
            }
            disableRecord = (DisableRecord) this.mDisableRecords.get(i3);
            if (disableRecord.token == iBinder && disableRecord.userId == i && disableRecord.barType == i2) {
                break;
            }
            i3++;
        }
        return new Pair(Integer.valueOf(i3), disableRecord);
    }

    public final int gatherDisableActionsLocked(int i, int i2, int i3) {
        if (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN") && i2 == 1) {
            return gatherDisableActionsLocked(i, i2, i3, 0);
        }
        int size = this.mDisableRecords.size();
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            DisableRecord disableRecord = (DisableRecord) this.mDisableRecords.get(i5);
            if (disableRecord.userId == i && disableRecord.barType == i3) {
                i4 |= disableRecord.getFlags(i2);
            }
        }
        return i4;
    }

    public final int gatherDisableActionsLocked(int i, int i2, int i3, int i4) {
        int size = this.mDisableRecords.size();
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            DisableRecord disableRecord = (DisableRecord) this.mDisableRecords.get(i6);
            if (disableRecord.userId == i && disableRecord.barType == i3) {
                int flags = disableRecord.getFlags(i2);
                if (!(i4 == 0 || i4 == 1) || (23068672 & flags) == 0) {
                    i5 |= disableRecord.getFlags(i2);
                } else if (i4 == 0) {
                    if (disableRecord.displayId == 1) {
                    }
                    i5 |= flags;
                } else {
                    if (disableRecord.displayId != i4) {
                    }
                    i5 |= flags;
                }
            }
        }
        return i5;
    }

    public final int[] getDisableFlags(IBinder iBinder, int i) {
        int i2;
        int i3;
        enforceStatusBar();
        synchronized (this.mLock) {
            i2 = 0;
            try {
                if (iBinder == null && i == -1) {
                    i2 = gatherDisableActionsLocked(this.mCurrentUserId, 1, 0);
                    i3 = 0;
                } else {
                    DisableRecord disableRecord = (DisableRecord) findMatchingRecordLocked(i, iBinder, 0).second;
                    if (disableRecord != null) {
                        i2 = disableRecord.what1;
                        i3 = disableRecord.what2;
                    } else {
                        i3 = 0;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return new int[]{i2, i3};
    }

    public final int[] getDisableFlagsToType(IBinder iBinder, int i, int i2) {
        int i3;
        int i4;
        enforceStatusBar();
        synchronized (this.mLock) {
            i3 = 0;
            try {
                if (iBinder == null && i == -1) {
                    i3 = gatherDisableActionsLocked(this.mCurrentUserId, 1, i2);
                    i4 = 0;
                } else {
                    DisableRecord disableRecord = (DisableRecord) findMatchingRecordLocked(i, iBinder, i2).second;
                    if (disableRecord != null) {
                        i3 = disableRecord.what1;
                        i4 = disableRecord.what2;
                    } else {
                        i4 = 0;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return new int[]{i3, i4};
    }

    public final int getLastSystemKey() {
        enforceStatusBar();
        return this.mLastSystemKey;
    }

    public final int getNavBarMode() {
        enforceStatusBar();
        int i = this.mCurrentUserId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "nav_bar_kids_mode", i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return intForUser;
        } catch (Settings.SettingNotFoundException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final IOverlayManager getOverlayManager() {
        if (this.mOverlayManager == null) {
            IOverlayManager asInterface = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
            this.mOverlayManager = asInterface;
            if (asInterface == null) {
                Slog.w("StatusBarManager", "warning: no OVERLAY_SERVICE");
            }
        }
        return this.mOverlayManager;
    }

    public final boolean getPanelExpandStateToType(int i) {
        if (this.mPanelExpandStateMap.get(Integer.valueOf(i)) != null) {
            return ((Boolean) this.mPanelExpandStateMap.get(Integer.valueOf(i))).booleanValue();
        }
        return false;
    }

    public final boolean getQuickSettingPanelExpandStateToType(int i) {
        if (this.mQsPanelExpandStateMap.get(Integer.valueOf(i)) != null) {
            return ((Boolean) this.mQsPanelExpandStateMap.get(Integer.valueOf(i))).booleanValue();
        }
        return false;
    }

    public final UiState getUiState(int i, int i2) {
        if (i2 == 1) {
            UiState uiState = (UiState) this.mDisplayUiStateDex.get(i);
            if (uiState != null) {
                return uiState;
            }
            UiState uiState2 = new UiState();
            this.mDisplayUiStateDex.put(i, uiState2);
            return uiState2;
        }
        UiState uiState3 = (UiState) this.mDisplayUiState.get(i);
        if (uiState3 != null) {
            return uiState3;
        }
        UiState uiState4 = new UiState();
        this.mDisplayUiState.put(i, uiState4);
        return uiState4;
    }

    public final void grantInlineReplyUriPermission(String str, Uri uri, UserHandle userHandle, String str2) {
        enforceStatusBarService();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).grantInlineReplyUriPermission(str, uri, userHandle, str2, callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void handleSystemKey(KeyEvent keyEvent) {
        if (checkCanCollapseStatusBar("handleSystemKey")) {
            this.mLastSystemKey = keyEvent.getKeyCode();
            if (this.mBar != null) {
                try {
                    this.mBar.handleSystemKey(keyEvent);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void hideAuthenticationDialog(long j) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC_DIALOG", "StatusBarManagerService");
        if (this.mBar != null) {
            try {
                this.mBar.hideAuthenticationDialog(j);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void hideCurrentInputMethodForBubbles(int i) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            InputMethodManagerInternal.get().hideAllInputMethods(20);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isFOTAAvailableForGlobalActions() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Context context = this.mContext;
            Object obj = ShutdownThread.sIsStartedGuard;
            boolean z = false;
            if (context != null && Settings.System.getInt(context.getContentResolver(), "attfota_forceinstall_FN_sim", 0) == 1) {
                Slog.d("StatusBarManagerService", "FOTA update available when asking recovery system");
                z = true;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSysUiSafeModeEnabled() {
        return this.mSysUiSafeMode || DEBUG_SAFEMODE;
    }

    public final boolean isTracing() {
        return this.mTracingEnabled;
    }

    public final void makeDisableHistory(int i, int i2, IBinder iBinder, String str, String str2, int i3, int i4, int i5) {
        StringBuilder sb = new StringBuilder();
        if (i3 == 1) {
            if (i2 == 0) {
                sb.append("CLEAR");
            } else {
                sb.append((65536 & i2) != 0 ? "EXPAND " : "");
                sb.append((131072 & i2) != 0 ? "ICONS " : "");
                sb.append((262144 & i2) != 0 ? "ALERTS " : "");
                sb.append((524288 & i2) != 0 ? "TICKER " : "");
                sb.append((1048576 & i2) != 0 ? "SYSTEM_INFO " : "");
                sb.append((4194304 & i2) != 0 ? "BACK " : "");
                sb.append((2097152 & i2) != 0 ? "HOME " : "");
                sb.append((16777216 & i2) != 0 ? "RECENT " : "");
                sb.append((8388608 & i2) != 0 ? "CLOCK " : "");
                sb.append((33554432 & i2) != 0 ? "SEARCH " : "");
                sb.append((268435456 & i2) != 0 ? "EXPAND_ON_KEYGUARD " : "");
                sb.append((i2 & 536870912) != 0 ? "EXPAND_AND_TOUCH " : "");
            }
        } else {
            if (i3 != 2) {
                return;
            }
            if (i2 == 0) {
                sb.append("CLEAR2");
            } else {
                sb.append((i2 & 1) != 0 ? "QS " : "");
                sb.append((i2 & 2) != 0 ? "SYSTEM_ICONS " : "");
                sb.append((i2 & 4) != 0 ? "SHADE " : "");
                sb.append((i2 & 8) != 0 ? "GA " : "");
                sb.append((i2 & 16) != 0 ? "ROTATE " : "");
            }
        }
        ArrayList arrayList = this.mDisableHistoryList;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FORMAT.format(new Date(System.currentTimeMillis())));
        sb2.append(" barType=");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i4, i5, " displayId=", " pkg=", sb2);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb2, str, " tag=", str2, " userId=");
        sb2.append(i);
        sb2.append(" what=");
        sb2.append(sb.toString());
        sb2.append(" token=");
        sb2.append(iBinder);
        arrayList.add(sb2.toString());
        while (this.mDisableHistoryList.size() > 100) {
            this.mDisableHistoryList.remove(0);
        }
    }

    public final void makeStatusBarHistory(int i) {
        String str;
        String callers = Debug.getCallers(3);
        String str2 = "collapsePanels";
        if (i == 0) {
            if (!callers.contains("collapsePanels")) {
                return;
            } else {
                str = "STATUS_BAR";
            }
        } else {
            if (i != 1) {
                return;
            }
            String str3 = "expandSettingsPanel";
            if (!callers.contains("expandSettingsPanel")) {
                str3 = "togglePanel";
                if (!callers.contains("togglePanel")) {
                    str3 = "expandNotificationsPanel";
                    if (!callers.contains("expandNotificationsPanel")) {
                        if (!callers.contains("collapsePanels")) {
                            return;
                        }
                        str = "EXPAND_STATUS_BAR";
                    }
                }
            }
            str2 = str3;
            str = "EXPAND_STATUS_BAR";
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        ArrayList arrayList = this.mStatusBarHistoryList;
        StringBuilder sb = new StringBuilder();
        sb.append(FORMAT.format(new Date(System.currentTimeMillis())));
        sb.append(" category = ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str, ", API = ", str2, ", pkg = ");
        sb.append(nameForUid);
        sb.append(", tag = null");
        arrayList.add(sb.toString());
        while (this.mStatusBarHistoryList.size() > 100) {
            this.mStatusBarHistoryList.remove(0);
        }
    }

    public final void notifyBarAttachChanged() {
        UiThread.getHandler().post(new StatusBarManagerService$$ExternalSyntheticLambda5(this, 1));
        this.mHandler.post(new StatusBarManagerService$$ExternalSyntheticLambda5(this, 2));
    }

    public final void onBiometricAuthenticated(int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC_DIALOG", "StatusBarManagerService");
        if (this.mBar != null) {
            try {
                this.mBar.onBiometricAuthenticated(i);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void onBiometricError(int i, int i2, int i3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC_DIALOG", "StatusBarManagerService");
        if (this.mBar != null) {
            try {
                this.mBar.onBiometricError(i, i2, i3);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void onBiometricHelp(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC_DIALOG", "StatusBarManagerService");
        if (this.mBar != null) {
            try {
                this.mBar.onBiometricHelp(i, str);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void onBubbleMetadataFlagChanged(String str, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onBubbleMetadataFlagChanged(str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onClearAllNotifications(int i) {
        enforceStatusBarService();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            NotificationManagerService.AnonymousClass2 anonymousClass2 = (NotificationManagerService.AnonymousClass2) this.mNotificationDelegate;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationManagerService.this.cancelAllLocked(callingUid, callingPid, i, 3, null, true, 8192);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            this.mDisplayUiState.remove(i);
        }
    }

    public final void onGlobalActionsHidden() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            GlobalActions globalActions = this.mGlobalActionListener;
            if (globalActions == null) {
                return;
            }
            globalActions.mShowing = false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onGlobalActionsShown() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            GlobalActions globalActions = this.mGlobalActionListener;
            if (globalActions == null) {
                return;
            }
            globalActions.getClass();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) {
        enforceStatusBarService();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationActionClick(callingUid, callingPid, str, i, action, notificationVisibility, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationBubbleChanged(String str, boolean z, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationBubbleChanged(str, z, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationClear(String str, int i, String str2, int i2, int i3, NotificationVisibility notificationVisibility) {
        enforceStatusBarService();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationClear(callingUid, callingPid, str, i, str2, i2, i3, notificationVisibility);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationClick(String str, NotificationVisibility notificationVisibility) {
        enforceStatusBarService();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationClick(callingUid, callingPid, str, notificationVisibility);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationDataUpdateFromPDC(List list) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationDataUpdateFromPDC(list);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationDirectReplied(String str) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationDirectReplied(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationError(String str, String str2, int i, int i2, int i3, String str3, int i4) {
        enforceStatusBarService();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationError(callingUid, callingPid, i, i2, i3, i4, str, str2, str3);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationExpansionChanged(str, z, z2, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationFeedbackReceived(String str, Bundle bundle) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationFeedbackReceived(str, bundle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationSettingsViewed(String str) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            NotificationManagerService.AnonymousClass2 anonymousClass2 = (NotificationManagerService.AnonymousClass2) this.mNotificationDelegate;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    notificationRecord.mStats.setViewedSettings();
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationSmartReplySent(str, i, charSequence, i2, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            NotificationManagerService.AnonymousClass2 anonymousClass2 = (NotificationManagerService.AnonymousClass2) this.mNotificationDelegate;
            synchronized (NotificationManagerService.this.mNotificationLock) {
                NotificationRecord notificationRecord = (NotificationRecord) NotificationManagerService.this.mNotificationsByKey.get(str);
                if (notificationRecord != null) {
                    notificationRecord.mNumberOfSmartRepliesAdded = i;
                    notificationRecord.mNumberOfSmartActionsAdded = i2;
                    notificationRecord.mSuggestionsGeneratedByAssistant = z;
                    notificationRecord.mEditChoicesBeforeSending = z2;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onNotificationVisibilityChanged(notificationVisibilityArr, notificationVisibilityArr2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onPanelHidden() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onPanelHidden();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onPanelRevealed(boolean z, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).onPanelRevealed(z, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onSessionEnded(int i, InstanceId instanceId) {
        SessionMonitor sessionMonitor = this.mSessionMonitor;
        sessionMonitor.requireSetterPermissions(i);
        if (!StatusBarManager.ALL_SESSIONS.contains(Integer.valueOf(i))) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "invalid onSessionEnded sessionType=", "SessionMonitor");
            return;
        }
        synchronized (sessionMonitor.mSessionToListeners) {
            for (ISessionListener iSessionListener : (Set) ((HashMap) sessionMonitor.mSessionToListeners).get(Integer.valueOf(i))) {
                try {
                    iSessionListener.onSessionEnded(i, instanceId);
                } catch (RemoteException e) {
                    Log.e("SessionMonitor", "unable to send session end to listener=" + iSessionListener, e);
                }
            }
        }
    }

    public final void onSessionStarted(int i, InstanceId instanceId) {
        SessionMonitor sessionMonitor = this.mSessionMonitor;
        sessionMonitor.requireSetterPermissions(i);
        if (!StatusBarManager.ALL_SESSIONS.contains(Integer.valueOf(i))) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "invalid onSessionStarted sessionType=", "SessionMonitor");
            return;
        }
        synchronized (sessionMonitor.mSessionToListeners) {
            for (ISessionListener iSessionListener : (Set) ((HashMap) sessionMonitor.mSessionToListeners).get(Integer.valueOf(i))) {
                try {
                    iSessionListener.onSessionStarted(i, instanceId);
                } catch (RemoteException e) {
                    Log.e("SessionMonitor", "unable to send session start to listener=" + iSessionListener, e);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new StatusBarShellCommand(this, this.mContext).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void passThroughShellCommand(String[] strArr, FileDescriptor fileDescriptor) {
        enforceStatusBarOrShell();
        if (this.mBar == null) {
            return;
        }
        try {
            TransferPipe transferPipe = new TransferPipe();
            try {
                transferPipe.setBufferPrefix("  ");
                this.mBar.passThroughShellCommand(strArr, transferPipe.getWriteFd());
                transferPipe.go(fileDescriptor);
                transferPipe.close();
            } finally {
            }
        } catch (Throwable th) {
            Slog.e("StatusBarManagerService", "Error sending command to IStatusBar", th);
        }
    }

    public final void publishGlobalActionsProvider() {
        if (LocalServices.getService(AnonymousClass3.class) == null) {
            LocalServices.addService(AnonymousClass3.class, this.mGlobalActionsProvider);
        }
    }

    public final void reboot(final boolean z) {
        enforceStatusBarService();
        final String str = z ? "safemode" : "userrequested";
        ShutdownCheckPoints.INSTANCE.recordCheckPointInternal(Binder.getCallingPid(), str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).prepareForPossibleShutdown();
            this.mHandler.post(new Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z2 = z;
                    String str2 = str;
                    if (z2) {
                        ShutdownThread.rebootSafeMode(StatusBarManagerService.getUiContext());
                    } else {
                        ShutdownThread.reboot(StatusBarManagerService.getUiContext(), str2, false);
                    }
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void rebootByBixby(final boolean z) {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mHandler.post(new Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    if (z) {
                        ShutdownThread.rebootSafeMode(StatusBarManagerService.getUiContext());
                    } else {
                        ShutdownThread.reboot(StatusBarManagerService.getUiContext(), "bixbyrequest", false);
                    }
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MEDIA_CONTENT_CONTROL", "StatusBarManagerService");
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "registerNearbyMediaDevicesProvider", e);
            }
        }
    }

    public void registerOverlayManager(IOverlayManager iOverlayManager) {
        this.mOverlayManager = iOverlayManager;
    }

    public final void registerSessionListener(int i, ISessionListener iSessionListener) {
        SessionMonitor sessionMonitor = this.mSessionMonitor;
        if ((i & 1) != 0) {
            sessionMonitor.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "StatusBarManagerService.SessionMonitor");
        }
        if ((i & 2) != 0) {
            sessionMonitor.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "StatusBarManagerService.SessionMonitor");
        }
        synchronized (sessionMonitor.mSessionToListeners) {
            try {
                for (Integer num : StatusBarManager.ALL_SESSIONS) {
                    if ((num.intValue() & i) != 0) {
                        ((Set) ((HashMap) sessionMonitor.mSessionToListeners).get(num)).add(iSessionListener);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) {
        ArrayMap arrayMap;
        RegisterStatusBarResult registerStatusBarResult;
        enforceStatusBarService();
        Slog.i("StatusBarManagerService", "registerStatusBar bar=" + iStatusBar);
        this.mBar = iStatusBar;
        this.mBarMap.put(0, iStatusBar);
        try {
            this.mBar.asBinder().linkToDeath(new AnonymousClass5(this, 0), 0);
        } catch (RemoteException e) {
            Slog.e("StatusBarManagerService", "Unable to register Death Recipient for status bar", e);
        }
        notifyBarAttachChanged();
        synchronized (this.mIcons) {
            arrayMap = new ArrayMap(this.mIcons);
        }
        synchronized (this.mLock) {
            UiState uiState = (UiState) this.mDisplayUiState.get(0);
            registerStatusBarResult = new RegisterStatusBarResult(arrayMap, gatherDisableActionsLocked(this.mCurrentUserId, 1, 0), uiState.mAppearance, uiState.mAppearanceRegions, uiState.mImeWindowVis, uiState.mImeBackDisposition, uiState.mShowImeSwitcher, gatherDisableActionsLocked(this.mCurrentUserId, 2, 0), uiState.mImeToken, uiState.mNavbarColorManagedByIme, uiState.mBehavior, uiState.mRequestedVisibleTypes, uiState.mPackageName, uiState.mTransientBarTypes, uiState.mLetterboxDetails);
        }
        return registerStatusBarResult;
    }

    public final RegisterStatusBarResult registerStatusBarAsType(IStatusBar iStatusBar, final int i) {
        ArrayMap arrayMap;
        RegisterStatusBarResult registerStatusBarResult;
        enforceStatusBarService();
        Slog.i("StatusBarManagerService", "start!! registerStatusBarAsType bar=" + iStatusBar);
        this.mBarMap.put(Integer.valueOf(i), iStatusBar);
        try {
            iStatusBar.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.statusbar.StatusBarManagerService.4
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    synchronized (StatusBarManagerService.this.mBarLock) {
                        StatusBarManagerService.this.mBarMap.remove(Integer.valueOf(i));
                    }
                    StatusBarManagerService.this.notifyBarAttachChanged();
                }
            }, 0);
        } catch (RemoteException e) {
            Slog.e("StatusBarManagerService", "Unable to register Death Recipient for DEX status bar", e);
        }
        notifyBarAttachChanged();
        synchronized (this.mIcons) {
            arrayMap = new ArrayMap(this.mIcons);
        }
        synchronized (this.mLock) {
            UiState uiState = (UiState) this.mDisplayUiStateDex.get(0);
            registerStatusBarResult = new RegisterStatusBarResult(arrayMap, gatherDisableActionsLocked(this.mCurrentUserId, 1, 1), uiState.mAppearance, uiState.mAppearanceRegions, uiState.mImeWindowVis, uiState.mImeBackDisposition, uiState.mShowImeSwitcher, gatherDisableActionsLocked(this.mCurrentUserId, 2, 1), uiState.mImeToken, uiState.mNavbarColorManagedByIme, uiState.mBehavior, uiState.mRequestedVisibleTypes, uiState.mPackageName, uiState.mTransientBarTypes, uiState.mLetterboxDetails);
        }
        return registerStatusBarResult;
    }

    public final void registerStatusBarForCarLife(IStatusBarCarLife iStatusBarCarLife) {
        if (CoreRune.CARLIFE_NAVBAR) {
            enforceStatusBarService();
            Slog.i("StatusBarManagerService", "registerStatusBarForCarLife bar=" + iStatusBarCarLife);
            this.mCarLifeBar = iStatusBarCarLife;
            if (this.mCarLifeBar != null) {
                try {
                    this.mCarLifeBar.asBinder().linkToDeath(new AnonymousClass5(this, 1), 0);
                } catch (RemoteException e) {
                    Slog.e("StatusBarManagerService", "Unable to register Death Recipient for status bar", e);
                }
            }
        }
    }

    public final void remTile(ComponentName componentName) {
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.remQsTile(componentName);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void removeIcon(String str) {
        enforceStatusBar();
        synchronized (this.mIcons) {
            this.mIcons.remove(str);
            Iterator it = this.mBarMap.entrySet().iterator();
            while (it.hasNext()) {
                IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
                if (iStatusBar != null) {
                    try {
                        iStatusBar.removeIcon(str);
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a0, code lost:
    
        if ("android.permission.BIND_QUICK_SETTINGS_TILE".equals(r5.permission) != false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void requestAddTile(final android.content.ComponentName r22, java.lang.CharSequence r23, android.graphics.drawable.Icon r24, final int r25, final com.android.internal.statusbar.IAddTileResultCallback r26) {
        /*
            Method dump skipped, instructions count: 443
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.statusbar.StatusBarManagerService.requestAddTile(android.content.ComponentName, java.lang.CharSequence, android.graphics.drawable.Icon, int, com.android.internal.statusbar.IAddTileResultCallback):void");
    }

    public final void requestTileServiceListeningState(ComponentName componentName, int i) {
        boolean z;
        ServiceInfo serviceInfo;
        Bundle bundle;
        int callingUid = Binder.getCallingUid();
        String packageName = componentName.getPackageName();
        boolean isChangeEnabled = CompatChanges.isChangeEnabled(172251878L, callingUid);
        Boolean bool = (Boolean) this.mIsSecCustomTileMap.get(componentName);
        if (bool != null) {
            z = bool.booleanValue();
        } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                serviceInfo = this.mContext.getPackageManager().getServiceInfo(componentName, 787072);
            } catch (PackageManager.NameNotFoundException unused) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            if (serviceInfo != null && (bundle = serviceInfo.metaData) != null) {
                if (!"".equals(bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_NAME", ""))) {
                    z = true;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    this.mIsSecCustomTileMap.put(componentName, Boolean.valueOf(z));
                }
            }
            z = false;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mIsSecCustomTileMap.put(componentName, Boolean.valueOf(z));
        }
        if (z) {
            Slog.d("StatusBarManagerService", "isSecCustomTile : componentName =" + componentName);
            isChangeEnabled = false;
        }
        if (isChangeEnabled) {
            int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, 0, "requestTileServiceListeningState", packageName);
            checkCallingUidPackage(callingUid, handleIncomingUser, packageName);
            int currentUserId = this.mActivityManagerInternal.getCurrentUserId();
            int profileParentId = this.mUserManagerInternal.getProfileParentId(handleIncomingUser);
            if (handleIncomingUser != currentUserId && profileParentId != currentUserId) {
                if (!CompatChanges.isChangeEnabled(242194868L, callingUid)) {
                    throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(handleIncomingUser, "User ", " is not the current user."));
                }
                return;
            }
        }
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.requestTileServiceListeningState(componentName);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "requestTileServiceListeningState", e);
            }
        }
    }

    public final void resetScheduleAutoHide() {
        if (this.mBar != null) {
            try {
                this.mBar.resetScheduleAutoHide();
            } catch (RemoteException unused) {
            }
        }
    }

    public final void restart() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mHandler.post(new StatusBarManagerService$$ExternalSyntheticLambda5(this, 0));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) {
        enforceStatusBarService();
        if (this.mBar != null) {
            try {
                this.mBar.sendKeyEventToDesktopTaskbar(keyEvent);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) {
        enforceStatusBarService();
        synchronized (this.mLock) {
            this.mBiometricContextListener = iBiometricContextListener;
        }
        if (this.mBar != null) {
            try {
                this.mBar.setBiometicContextListener(iBiometricContextListener);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void setBlueLightFilter(boolean z, int i) {
        enforceStatusBarService();
        Iterator it = this.mBarMap.entrySet().iterator();
        while (it.hasNext()) {
            IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
            if (iStatusBar != null) {
                try {
                    iStatusBar.setBlueLightFilter(z, i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void setIcon(String str, String str2, int i, int i2, String str3) {
        enforceStatusBar();
        synchronized (this.mIcons) {
            StatusBarIcon statusBarIcon = new StatusBarIcon(str2, UserHandle.SYSTEM, i, i2, 0, str3, StatusBarIcon.Type.SystemIcon);
            this.mIcons.put(str, statusBarIcon);
            Iterator it = this.mBarMap.entrySet().iterator();
            while (it.hasNext()) {
                IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
                if (iStatusBar != null) {
                    try {
                        iStatusBar.setIcon(str, statusBarIcon);
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    public final void setIconVisibility(String str, boolean z) {
        enforceStatusBar();
        synchronized (this.mIcons) {
            try {
                StatusBarIcon statusBarIcon = (StatusBarIcon) this.mIcons.get(str);
                if (statusBarIcon == null) {
                    return;
                }
                if (statusBarIcon.visible != z) {
                    statusBarIcon.visible = z;
                    Iterator it = this.mBarMap.entrySet().iterator();
                    while (it.hasNext()) {
                        IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
                        if (iStatusBar != null) {
                            try {
                                iStatusBar.setIcon(str, statusBarIcon);
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setImeWindowStatus(final int i, final IBinder iBinder, final int i2, final int i3, final boolean z) {
        enforceStatusBar();
        synchronized (this.mLock) {
            UiState uiState = getUiState(i, 0);
            uiState.mImeWindowVis = i2;
            uiState.mImeBackDisposition = i3;
            uiState.mShowImeSwitcher = z;
            uiState.mImeToken = iBinder;
            this.mHandler.post(new Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarManagerService statusBarManagerService = StatusBarManagerService.this;
                    int i4 = i;
                    IBinder iBinder2 = iBinder;
                    int i5 = i2;
                    int i6 = i3;
                    boolean z2 = z;
                    if (statusBarManagerService.mBar == null) {
                        return;
                    }
                    try {
                        statusBarManagerService.mBar.setImeWindowStatus(i4, iBinder2, i5, i6, z2);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }

    public final void setIndicatorBgColor(int i) {
    }

    public final void setNavBarMode(int i) {
        enforceStatusBar();
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Supplied navBarMode not supported: "));
        }
        int i2 = this.mCurrentUserId;
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        if (this.mCurrentUserId != userId && this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0 && this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
            throw new SecurityException(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mCurrentUserId, BatteryService$$ExternalSyntheticOutline0.m(userId, "Calling user id: ", ", cannot call on behalf of current user id: "), "."));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "nav_bar_kids_mode", i, i2);
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "nav_bar_force_visible", i, i2);
                IOverlayManager overlayManager = getOverlayManager();
                if (overlayManager != null && i == 1) {
                    try {
                        if (this.mContext.getPackageManager().getPackageInfo(KnoxCustomManagerService.NAV_BAR_MODE_3BUTTON_OVERLAY, PackageManager.PackageInfoFlags.of(0L)) != null) {
                            overlayManager.setEnabledExclusiveInCategory(KnoxCustomManagerService.NAV_BAR_MODE_3BUTTON_OVERLAY, i2);
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        if (this.mBar != null) {
            try {
                this.mBar.setNavigationBarShortcut(str, remoteViews, i, i2);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void setPanelExpandStateToType(boolean z, int i) {
        Slog.d("StatusBarManagerService", "  setPanelExpandStateToType : state = " + z + ", barType = " + i);
        this.mPanelExpandStateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
        this.mQsPanelExpandStateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    }

    public final void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
        enforceStatusBarService();
        if (this.mBar != null) {
            try {
                this.mBar.setUdfpsRefreshRateCallback(iUdfpsRefreshRateRequestCallback);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC_DIALOG", "StatusBarManagerService");
        if (this.mBar != null) {
            try {
                this.mBar.showAuthenticationDialog(promptInfo, iBiometricSysuiReceiver, iArr, z, z2, i, j, str, j2);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void showInattentiveSleepWarning() {
        enforceStatusBarService();
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.showInattentiveSleepWarning();
            } catch (RemoteException unused) {
            }
        }
    }

    public final void showPinningEnterExitToast(boolean z) {
        if (this.mBar != null) {
            try {
                this.mBar.showPinningEnterExitToast(z);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void showPinningEscapeToast() {
        if (this.mBar != null) {
            try {
                this.mBar.showPinningEscapeToast();
            } catch (RemoteException unused) {
            }
        }
    }

    public final void showRearDisplayDialog(int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE", "StatusBarManagerService");
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.showRearDisplayDialog(i);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "showRearDisplayDialog", e);
            }
        }
    }

    public final void shutdown() {
        enforceStatusBarService();
        ShutdownCheckPoints.INSTANCE.recordCheckPointInternal(Binder.getCallingPid(), "userrequested");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((NotificationManagerService.AnonymousClass2) this.mNotificationDelegate).prepareForPossibleShutdown();
            this.mHandler.post(new StatusBarManagerService$$ExternalSyntheticLambda0(0));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void shutdownByBixby() {
        enforceStatusBarService();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mHandler.post(new StatusBarManagerService$$ExternalSyntheticLambda0(1));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startTracing() {
        if (this.mBar != null) {
            try {
                this.mBar.startTracing();
                this.mTracingEnabled = true;
            } catch (RemoteException unused) {
            }
        }
    }

    public final void stopTracing() {
        if (this.mBar != null) {
            try {
                this.mTracingEnabled = false;
                this.mBar.stopTracing();
            } catch (RemoteException unused) {
            }
        }
    }

    public final void suppressAmbientDisplay(boolean z) {
        enforceStatusBarService();
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.suppressAmbientDisplay(z);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void togglePanel() {
        if (!checkCanCollapseStatusBar("togglePanel") || (((UiState) this.mDisplayUiState.get(0)).mDisabled2 & 4) == 4 || this.mBar == null) {
            return;
        }
        try {
            this.mBar.toggleNotificationsPanel();
        } catch (RemoteException unused) {
        }
    }

    public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MEDIA_CONTENT_CONTROL", "StatusBarManagerService");
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "unregisterNearbyMediaDevicesProvider", e);
            }
        }
    }

    public final void unregisterSessionListener(int i, ISessionListener iSessionListener) {
        SessionMonitor sessionMonitor = this.mSessionMonitor;
        synchronized (sessionMonitor.mSessionToListeners) {
            try {
                for (Integer num : StatusBarManager.ALL_SESSIONS) {
                    if ((num.intValue() & i) != 0) {
                        ((Set) ((HashMap) sessionMonitor.mSessionToListeners).get(num)).remove(iSessionListener);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MEDIA_CONTENT_CONTROL", "StatusBarManagerService");
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.updateMediaTapToTransferReceiverDisplay(i, mediaRoute2Info, icon, charSequence);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "updateMediaTapToTransferReceiverDisplay", e);
            }
        }
    }

    public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MEDIA_CONTENT_CONTROL", "StatusBarManagerService");
        IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.updateMediaTapToTransferSenderDisplay(i, mediaRoute2Info, iUndoMediaTransferCallback);
            } catch (RemoteException e) {
                Slog.e("StatusBarManagerService", "updateMediaTapToTransferSenderDisplay", e);
            }
        }
    }
}
