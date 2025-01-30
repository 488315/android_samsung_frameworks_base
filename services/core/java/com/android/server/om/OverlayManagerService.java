package com.android.server.om;

import android.R;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.om.OverlayManagerTransaction;
import android.content.om.OverlayableInfo;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.content.pm.overlay.OverlayPaths;
import android.content.res.ApkAssets;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FabricatedOverlayInternal;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.UiModeManagerInternal;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.om.OverlayManagerService;
import com.android.server.om.OverlayManagerServiceImpl;
import com.android.server.om.wallpapertheme.ISemWallpaperThemeManager;
import com.android.server.om.wallpapertheme.SemWallpaperThemeManager;
import com.android.server.om.wallpapertheme.SemWallpaperThemeManagerWrapper;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import libcore.util.EmptyArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class OverlayManagerService extends SystemService {
    public final OverlayActorEnforcer mActorEnforcer;
    public final OverlayManagerServiceImpl mImpl;
    public final Object mLock;
    public IOverlayManagerExt mOverlayManagerServiceExt;
    public final PackageManagerHelperImpl mPackageManager;
    public final IBinder mService;
    public final OverlayManagerSettings mSettings;
    public final AtomicFile mSettingsFile;
    public final UserManagerService mUserManager;
    public BroadcastReceiver mUserUnlockerReceiver;
    public ISemWallpaperThemeManager mWallpaperThemeManager;

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public OverlayManagerService(Context context) {
        super(context);
        this.mLock = new Object();
        Object[] objArr = 0;
        this.mOverlayManagerServiceExt = null;
        this.mUserUnlockerReceiver = new BroadcastReceiver() { // from class: com.android.server.om.OverlayManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                HandlerThread handlerThread = new HandlerThread("OverlayManager");
                handlerThread.start();
                context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("contrast_level"), false, new ContentObserver(handlerThread.getThreadHandler()) { // from class: com.android.server.om.OverlayManagerService.1.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        if (Settings.Secure.getFloatForUser(OverlayManagerService.this.getContext().getContentResolver(), "contrast_level", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -2) == -1.0f) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                synchronized (OverlayManagerService.this.mLock) {
                                    OverlayManagerService overlayManagerService = OverlayManagerService.this;
                                    overlayManagerService.updateTargetPackagesLocked(overlayManagerService.unregisterColorThemeGG(false), true);
                                }
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                });
            }
        };
        IOverlayManager.Stub c20512 = new C20512();
        this.mService = c20512;
        try {
            Trace.traceBegin(67108864L, "OMS#OverlayManagerService");
            this.mSettingsFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "overlays.xml"), "overlays");
            PackageManagerHelperImpl packageManagerHelperImpl = new PackageManagerHelperImpl(context);
            this.mPackageManager = packageManagerHelperImpl;
            this.mUserManager = UserManagerService.getInstance();
            IdmapManager idmapManager = new IdmapManager(IdmapDaemon.getInstance(), packageManagerHelperImpl);
            OverlayManagerSettings overlayManagerSettings = new OverlayManagerSettings();
            this.mSettings = overlayManagerSettings;
            OverlayManagerServiceImpl overlayManagerServiceImpl = new OverlayManagerServiceImpl(packageManagerHelperImpl, idmapManager, overlayManagerSettings, OverlayConfig.getSystemInstance(), getDefaultOverlayPackages());
            this.mImpl = overlayManagerServiceImpl;
            this.mActorEnforcer = new OverlayActorEnforcer(packageManagerHelperImpl);
            HandlerThread handlerThread = new HandlerThread("OverlayManager");
            handlerThread.start();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            getContext().registerReceiverAsUser(new PackageReceiver(), UserHandle.ALL, intentFilter, null, handlerThread.getThreadHandler());
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.USER_ADDED");
            intentFilter2.addAction("android.intent.action.USER_REMOVED");
            getContext().registerReceiverAsUser(new UserReceiver(), UserHandle.ALL, intentFilter2, null, null);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("android.intent.action.USER_UNLOCKED");
            getContext().registerReceiverAsUser(this.mUserUnlockerReceiver, UserHandle.SYSTEM, intentFilter3, null, null);
            restoreSettings();
            OverlayManagerServiceExt overlayManagerServiceExt = new OverlayManagerServiceExt(context, packageManagerHelperImpl, overlayManagerSettings, idmapManager, new PackageUpdateHelper());
            this.mOverlayManagerServiceExt = overlayManagerServiceExt;
            overlayManagerServiceImpl.setOverlayManagerExt(overlayManagerServiceExt);
            this.mWallpaperThemeManager = SemWallpaperThemeManagerWrapper.getInstance(context, new OverlayManagerSettingsHelper(overlayManagerSettings));
            final String emptyIfNull = TextUtils.emptyIfNull(getContext().getString(R.string.config_systemShell));
            overlayManagerSettings.removeIf(new Predicate() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$new$0;
                    lambda$new$0 = OverlayManagerService.lambda$new$0(emptyIfNull, (OverlayInfo) obj);
                    return lambda$new$0;
                }
            });
            initIfNeeded();
            onStartUser(0, true);
            publishBinderService("overlay", c20512);
            publishLocalService(OverlayManagerService.class, this);
            if (!this.mWallpaperThemeManager.isColoThemeApplied()) {
                unregisterUnusedPaletteOverlays();
            }
            this.mWallpaperThemeManager.initWallpaperTheme();
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    public static /* synthetic */ boolean lambda$new$0(String str, OverlayInfo overlayInfo) {
        return overlayInfo.isFabricated && str.equals(overlayInfo.packageName);
    }

    public final void initIfNeeded() {
        List aliveUsers = ((UserManager) getContext().getSystemService(UserManager.class)).getAliveUsers();
        synchronized (this.mLock) {
            int size = aliveUsers.size();
            for (int i = 0; i < size; i++) {
                UserInfo userInfo = (UserInfo) aliveUsers.get(i);
                if (!userInfo.supportsSwitchTo() && userInfo.id != 0) {
                    updatePackageManagerLocked(this.mImpl.updateOverlaysForUser(((UserInfo) aliveUsers.get(i)).id));
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
        onStartUser(targetUser.getUserIdentifier());
    }

    public final void onStartUser(int i) {
        onStartUser(i, false);
    }

    public final void onStartUser(int i, boolean z) {
        try {
            Trace.traceBegin(67108864L, "OMS#onStartUser " + i);
            this.mImpl.createLocaleOverlayPreferenceDir(i);
            if (!z) {
                try {
                    LocaleOverlayManagerWrapper.getInstance(getContext()).checkSanityOfOverlays(i);
                } catch (Exception e) {
                    Slog.e("OverlayManager", "onStartUser - Error in starting localeoverlaymanager: " + e);
                }
            }
            synchronized (this.mLock) {
                Slog.e("OMS_DEBUG", "[onStartUser] newUserId : " + i);
                IOverlayManagerExt iOverlayManagerExt = this.mOverlayManagerServiceExt;
                if (iOverlayManagerExt != null) {
                    iOverlayManagerExt.setIsInitonBoot(z);
                }
                updateTargetPackagesLocked(this.mImpl.updateOverlaysForUser(i));
                IOverlayManagerExt iOverlayManagerExt2 = this.mOverlayManagerServiceExt;
                if (iOverlayManagerExt2 != null) {
                    iOverlayManagerExt2.setIsInitonBoot(false);
                }
            }
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        UiModeManagerInternal uiModeManagerInternal = (UiModeManagerInternal) LocalServices.getService(UiModeManagerInternal.class);
        if (uiModeManagerInternal != null) {
            uiModeManagerInternal.onEarlySwitchUser(targetUser2.getUserIdentifier());
        }
    }

    public static String[] getDefaultOverlayPackages() {
        String str = SystemProperties.get("ro.boot.vendor.overlay.theme");
        if (TextUtils.isEmpty(str)) {
            return EmptyArray.STRING;
        }
        ArraySet arraySet = new ArraySet();
        for (String str2 : str.split(KnoxVpnFirewallHelper.DELIMITER)) {
            if (!TextUtils.isEmpty(str2)) {
                arraySet.add(str2);
            }
        }
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }

    public final class PackageReceiver extends BroadcastReceiver {
        public PackageReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0074, code lost:
        
            if (r9.equals("android.intent.action.PACKAGE_CHANGED") == false) goto L16;
         */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onReceive(Context context, Intent intent) {
            int[] iArr;
            String action = intent.getAction();
            if (action == null) {
                Slog.e("OverlayManager", "Cannot handle package broadcast with null action");
            }
            Uri data = intent.getData();
            if (data == null) {
                Slog.e("OverlayManager", "Cannot handle package broadcast with null data");
                return;
            }
            String schemeSpecificPart = data.getSchemeSpecificPart();
            char c = 0;
            boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
            boolean booleanExtra2 = intent.getBooleanExtra("android.intent.extra.SYSTEM_UPDATE_UNINSTALL", false);
            boolean booleanExtra3 = intent.getBooleanExtra("android.intent.extra.HIDDEN", false);
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -10000);
            if (intExtra == -10000) {
                iArr = OverlayManagerService.this.mUserManager.getUserIds();
            } else {
                iArr = new int[]{UserHandle.getUserId(intExtra)};
            }
            switch (action.hashCode()) {
                case 172491798:
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1544582882:
                    if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (!"android.intent.action.OVERLAY_CHANGED".equals(intent.getStringExtra("android.intent.extra.REASON"))) {
                        onPackageChanged(schemeSpecificPart, iArr);
                        break;
                    }
                    break;
                case 1:
                    if (booleanExtra) {
                        onPackageReplacing(schemeSpecificPart, booleanExtra2, iArr);
                        break;
                    } else {
                        onPackageRemoved(schemeSpecificPart, iArr, booleanExtra3);
                        break;
                    }
                case 2:
                    if (booleanExtra) {
                        onPackageReplaced(schemeSpecificPart, iArr);
                    } else {
                        onPackageAdded(schemeSpecificPart, iArr);
                    }
                    OverlayManagerService.this.mWallpaperThemeManager.onPackageAdded(schemeSpecificPart);
                    break;
            }
        }

        public final void onPackageAdded(String str, int[] iArr) {
            try {
                Trace.traceBegin(67108864L, "OMS#onPackageAdded " + str);
                for (int i : iArr) {
                    synchronized (OverlayManagerService.this.mLock) {
                        if (OverlayManagerService.this.mPackageManager.onPackageAdded(str, i) != null && !OverlayManagerService.this.mPackageManager.isInstantApp(str, i)) {
                            try {
                                OverlayManagerService overlayManagerService = OverlayManagerService.this;
                                overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.onPackageAdded(str, i));
                            } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                                Slog.e("OverlayManager", "onPackageAdded internal error", e);
                            }
                        }
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final void onPackageChanged(String str, int[] iArr) {
            try {
                Trace.traceBegin(67108864L, "OMS#onPackageChanged " + str);
                for (int i : iArr) {
                    synchronized (OverlayManagerService.this.mLock) {
                        if (OverlayManagerService.this.mPackageManager.onPackageUpdated(str, i) != null && !OverlayManagerService.this.mPackageManager.isInstantApp(str, i)) {
                            try {
                                OverlayManagerService overlayManagerService = OverlayManagerService.this;
                                overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.onPackageChanged(str, i));
                            } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                                Slog.e("OverlayManager", "onPackageChanged internal error", e);
                            }
                        }
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final void onPackageReplacing(String str, boolean z, int[] iArr) {
            boolean z2;
            try {
                Trace.traceBegin(67108864L, "OMS#onPackageReplacing " + str);
                int length = iArr.length;
                for (int i = 0; i < length; i++) {
                    int i2 = iArr[i];
                    synchronized (OverlayManagerService.this.mLock) {
                        if (OverlayManagerService.this.mPackageManager.onPackageUpdated(str, i2) != null && !OverlayManagerService.this.mPackageManager.isInstantApp(str, i2)) {
                            try {
                                OverlayManagerService overlayManagerService = OverlayManagerService.this;
                                overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.onPackageReplacing(str, z, i2));
                                if (z) {
                                    try {
                                        OverlayPaths enabledOverlayPaths = OverlayManagerService.this.mImpl.getEnabledOverlayPaths("android", i2, false);
                                        for (String str2 : enabledOverlayPaths.getOverlayPaths()) {
                                            if (str2 != null && (str2.startsWith("/data/overlays/currentstyle") || str2.startsWith("/data/resource-cache/android-SemWT"))) {
                                                z2 = false;
                                                break;
                                            }
                                        }
                                        z2 = true;
                                        if (z2) {
                                            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                                            ArrayMap arrayMap = new ArrayMap(1);
                                            arrayMap.put(str, enabledOverlayPaths);
                                            packageManagerInternal.setEnabledOverlayPackages(i2, arrayMap, new HashSet(), new HashSet());
                                            Slog.d("OverlayManager", "OM_BUG_FIX_LOST_OVERLAY_WHEN_UPDATE_UNINSTALL : " + str);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (OverlayManagerServiceImpl.OperationFailedException e2) {
                                Slog.e("OverlayManager", "onPackageReplacing internal error", e2);
                            }
                        }
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final void onPackageReplaced(String str, int[] iArr) {
            try {
                Trace.traceBegin(67108864L, "OMS#onPackageReplaced " + str);
                for (int i : iArr) {
                    synchronized (OverlayManagerService.this.mLock) {
                        if (OverlayManagerService.this.mPackageManager.onPackageUpdated(str, i) != null && !OverlayManagerService.this.mPackageManager.isInstantApp(str, i)) {
                            try {
                                OverlayManagerService overlayManagerService = OverlayManagerService.this;
                                overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.onPackageReplaced(str, i));
                            } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                                Slog.e("OverlayManager", "onPackageReplaced internal error", e);
                            }
                        }
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final void onPackageRemoved(String str, int[] iArr, boolean z) {
            try {
                Trace.traceBegin(67108864L, "OMS#onPackageRemoved " + str);
                for (int i : iArr) {
                    synchronized (OverlayManagerService.this.mLock) {
                        OverlayManagerService.this.mPackageManager.onPackageRemoved(str, i);
                        OverlayManagerService overlayManagerService = OverlayManagerService.this;
                        overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.onPackageRemoved(str, i, z));
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }
    }

    public final class UserReceiver extends BroadcastReceiver {
        public UserReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("android.intent.action.USER_REMOVED")) {
                if (intExtra != -10000) {
                    try {
                        Trace.traceBegin(67108864L, "OMS ACTION_USER_REMOVED");
                        synchronized (OverlayManagerService.this.mLock) {
                            OverlayManagerService.this.mImpl.onUserRemoved(intExtra);
                            OverlayManagerService.this.mPackageManager.forgetAllPackageInfos(intExtra);
                        }
                        return;
                    } finally {
                    }
                }
                return;
            }
            if (action.equals("android.intent.action.USER_ADDED") && intExtra != -10000) {
                try {
                    Trace.traceBegin(67108864L, "OMS ACTION_USER_ADDED");
                    synchronized (OverlayManagerService.this.mLock) {
                        Slog.e("OMS_DEBUG", "[ACTION_USER_ADDED] userId : " + intExtra);
                        OverlayManagerService overlayManagerService = OverlayManagerService.this;
                        overlayManagerService.updatePackageManagerLocked(overlayManagerService.mImpl.updateOverlaysForUser(intExtra));
                        if (OverlayManagerService.this.mOverlayManagerServiceExt != null) {
                            OverlayManagerService.this.mOverlayManagerServiceExt.handleUserSwitch(intExtra);
                        }
                        OverlayManagerService.this.mWallpaperThemeManager.onUserAdded(intExtra);
                    }
                } finally {
                }
            }
        }
    }

    @SystemApi
    public void updatePackageCache(String str, int i) {
        Slog.i("OverlayManager", "updatePackageCache() called with: packageName = [" + str + "], userId = [" + i + "]");
        if (str != null) {
            synchronized (this.mLock) {
                this.mPackageManager.addPackageUser(str, i);
            }
        }
    }

    public Map getAllOverlays(int i) {
        try {
            return this.mService.getAllOverlays(i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public List getOverlayInfosForTarget(String str, int i) {
        try {
            return this.mService.getOverlayInfosForTarget(str, i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public String getTargetPath(String str) {
        synchronized (this.mLock) {
            IOverlayManagerExt iOverlayManagerExt = this.mOverlayManagerServiceExt;
            if (iOverlayManagerExt == null) {
                return null;
            }
            return iOverlayManagerExt.getTargetPath(str);
        }
    }

    /* renamed from: com.android.server.om.OverlayManagerService$2 */
    public class C20512 extends IOverlayManager.Stub {
        public C20512() {
        }

        public Map getAllOverlays(int i) {
            Map overlaysForUser;
            try {
                Trace.traceBegin(67108864L, "OMS#getAllOverlays " + i);
                int handleIncomingUser = handleIncomingUser(i, "getAllOverlays");
                synchronized (OverlayManagerService.this.mLock) {
                    overlaysForUser = OverlayManagerService.this.mImpl.getOverlaysForUser(handleIncomingUser);
                }
                return overlaysForUser;
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public List getOverlayInfosForTarget(String str, int i) {
            List overlayInfosForTarget;
            if (str == null) {
                return Collections.emptyList();
            }
            try {
                Trace.traceBegin(67108864L, "OMS#getOverlayInfosForTarget " + str);
                int handleIncomingUser = handleIncomingUser(i, "getOverlayInfosForTarget");
                synchronized (OverlayManagerService.this.mLock) {
                    overlayInfosForTarget = OverlayManagerService.this.mImpl.getOverlayInfosForTarget(str, handleIncomingUser);
                }
                return overlayInfosForTarget;
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public OverlayInfo getOverlayInfo(String str, int i) {
            return getOverlayInfoByIdentifier(new OverlayIdentifier(str), i);
        }

        public OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier overlayIdentifier, int i) {
            OverlayInfo overlayInfo;
            if (overlayIdentifier == null || overlayIdentifier.getPackageName() == null) {
                return null;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#getOverlayInfo " + overlayIdentifier);
                int handleIncomingUser = handleIncomingUser(i, "getOverlayInfo");
                synchronized (OverlayManagerService.this.mLock) {
                    overlayInfo = OverlayManagerService.this.mImpl.getOverlayInfo(overlayIdentifier, handleIncomingUser);
                }
                return overlayInfo;
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public boolean setEnabled(String str, boolean z, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setEnabled " + str + " " + z);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabled");
                enforceActor(overlayIdentifier, "setEnabled", handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService overlayManagerService = OverlayManagerService.this;
                            overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.setEnabled(overlayIdentifier, z, handleIncomingUser));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public boolean setEnabledExclusive(String str, boolean z, int i) {
            if (str == null || !z) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setEnabledExclusive " + str + " " + z);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabledExclusive");
                enforceActor(overlayIdentifier, "setEnabledExclusive", handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setEnabledExclusive(overlayIdentifier, false, handleIncomingUser).ifPresent(new OverlayManagerService$2$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public boolean setEnabledExclusiveInCategory(String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setEnabledExclusiveInCategory " + str);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabledExclusiveInCategory");
                enforceActor(overlayIdentifier, "setEnabledExclusiveInCategory", handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setEnabledExclusive(overlayIdentifier, true, handleIncomingUser).ifPresent(new OverlayManagerService$2$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public boolean setPriority(String str, String str2, int i) {
            if (str == null || str2 == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setPriority " + str + " " + str2);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                OverlayIdentifier overlayIdentifier2 = new OverlayIdentifier(str2);
                int handleIncomingUser = handleIncomingUser(i, "setPriority");
                enforceActor(overlayIdentifier, "setPriority", handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setPriority(overlayIdentifier, overlayIdentifier2, handleIncomingUser).ifPresent(new OverlayManagerService$2$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public boolean setHighestPriority(String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setHighestPriority " + str);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setHighestPriority");
                enforceActor(overlayIdentifier, "setHighestPriority", handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService overlayManagerService = OverlayManagerService.this;
                            overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.setHighestPriority(overlayIdentifier, handleIncomingUser));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public boolean setLowestPriority(String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setLowestPriority " + str);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setLowestPriority");
                enforceActor(overlayIdentifier, "setLowestPriority", handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setLowestPriority(overlayIdentifier, handleIncomingUser).ifPresent(new OverlayManagerService$2$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public String[] getDefaultOverlayPackages() {
            String[] defaultOverlayPackages;
            try {
                Trace.traceBegin(67108864L, "OMS#getDefaultOverlayPackages");
                OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.MODIFY_THEME_OVERLAY", null);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        defaultOverlayPackages = OverlayManagerService.this.mImpl.getDefaultOverlayPackages();
                    }
                    return defaultOverlayPackages;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public void invalidateCachesForOverlay(String str, int i) {
            if (str == null) {
                return;
            }
            OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
            int handleIncomingUser = handleIncomingUser(i, "invalidateCachesForOverlay");
            enforceActor(overlayIdentifier, "invalidateCachesForOverlay", handleIncomingUser);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (OverlayManagerService.this.mLock) {
                    try {
                        OverlayManagerService.this.mImpl.removeIdmapForOverlay(overlayIdentifier, handleIncomingUser);
                    } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                        Slog.w("OverlayManager", "invalidate caches for overlay '" + overlayIdentifier + "' failed", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void commit(OverlayManagerTransaction overlayManagerTransaction) {
            try {
                Trace.traceBegin(67108864L, "OMS#commit " + overlayManagerTransaction);
                try {
                    executeAllRequests(overlayManagerTransaction);
                } catch (Exception e) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        OverlayManagerService.this.restoreSettings();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Slog.d("OverlayManager", "commit failed: " + e.getMessage(), e);
                        throw new SecurityException("commit failed: " + e.getMessage());
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final Set executeRequest(OverlayManagerTransaction.Request request, boolean z) {
            int i;
            Objects.requireNonNull(request, "Transaction contains a null request");
            Objects.requireNonNull(request.overlay, "Transaction overlay identifier must be non-null");
            int callingUid = Binder.getCallingUid();
            int i2 = request.type;
            if (i2 != 2 && i2 != 3) {
                i = handleIncomingUser(request.userId, request.typeToString());
                if (z && OverlayManagerService.this.mImpl.getOverlayInfo(request.overlay, i) == null) {
                    SemWallpaperThemeManager.saveSWTLog("SWT_OverlayManager", "OverlayInfo is not founded, skip request " + request);
                    return Set.of();
                }
                enforceActor(request.overlay, request.typeToString(), i);
            } else {
                if (request.userId != -1) {
                    throw new IllegalArgumentException(request.typeToString() + " unsupported for user " + request.userId);
                }
                if (callingUid == 2000) {
                    EventLog.writeEvent(1397638484, "202768292", -1, "");
                    throw new IllegalArgumentException("Non-root shell cannot fabricate overlays");
                }
                String packageName = request.overlay.getPackageName();
                if (callingUid != 0 && !ArrayUtils.contains(OverlayManagerService.this.mPackageManager.getPackagesForUid(callingUid), packageName)) {
                    throw new IllegalArgumentException("UID " + callingUid + " does own packagename " + packageName);
                }
                i = -1;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int i3 = request.type;
                if (i3 == 0) {
                    return CollectionUtils.emptyIfNull(CollectionUtils.addAll(CollectionUtils.addAll((Set) null, OverlayManagerService.this.mImpl.setEnabled(request.overlay, true, i)), OverlayManagerService.this.mImpl.setHighestPriority(request.overlay, i)));
                }
                if (i3 == 1) {
                    return OverlayManagerService.this.mImpl.setEnabled(request.overlay, false, i);
                }
                if (i3 == 2) {
                    FabricatedOverlayInternal fabricatedOverlayInternal = (FabricatedOverlayInternal) request.extras.getParcelable("fabricated_overlay", FabricatedOverlayInternal.class);
                    Objects.requireNonNull(fabricatedOverlayInternal, "no fabricated overlay attached to request");
                    return OverlayManagerService.this.mImpl.registerFabricatedOverlay(fabricatedOverlayInternal);
                }
                if (i3 == 3) {
                    return OverlayManagerService.this.mImpl.unregisterFabricatedOverlay(request.overlay);
                }
                throw new IllegalArgumentException("unsupported request: " + request);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void executeAllRequests(OverlayManagerTransaction overlayManagerTransaction) {
            Set set;
            Slog.d("OverlayManager", "commit " + overlayManagerTransaction);
            if (overlayManagerTransaction == null) {
                throw new IllegalArgumentException("null transaction");
            }
            synchronized (OverlayManagerService.this.mLock) {
                boolean isRequestForColorTheme = OverlayManagerService.this.mWallpaperThemeManager.isRequestForColorTheme(overlayManagerTransaction);
                Set set2 = null;
                if (isRequestForColorTheme) {
                    OverlayManagerTransaction.Request request = (OverlayManagerTransaction.Request) overlayManagerTransaction.getRequests().next();
                    set = OverlayManagerService.this.unregisterColorThemeGG(request != null && request.type == 2);
                    if (request != null && request.type == 1) {
                        OverlayManagerService.this.disableDynamicContrast();
                    }
                } else {
                    set = null;
                }
                Iterator requests = overlayManagerTransaction.getRequests();
                while (requests.hasNext()) {
                    set2 = CollectionUtils.addAll(set2, executeRequest((OverlayManagerTransaction.Request) requests.next(), isRequestForColorTheme));
                }
                Set addAll = CollectionUtils.addAll(set2, CollectionUtils.emptyIfNull(set));
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    OverlayManagerService.this.updateTargetPackagesLocked(addAll, isRequestForColorTheme);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new OverlayManagerShellCommand(OverlayManagerService.this.getContext(), this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            String str;
            String str2;
            DumpState dumpState = new DumpState();
            dumpState.setUserId(-1);
            int i = 0;
            while (i < strArr.length && (str2 = strArr[i]) != null && str2.length() > 0 && str2.charAt(0) == '-') {
                i++;
                if (!"-a".equals(str2)) {
                    if ("-h".equals(str2)) {
                        printWriter.println("dump [-h] [--verbose] [--user USER_ID] [[FIELD] PACKAGE]");
                        printWriter.println("  Print debugging information about the overlay manager.");
                        printWriter.println("  With optional parameter PACKAGE, limit output to the specified");
                        printWriter.println("  package. With optional parameter FIELD, limit output to");
                        printWriter.println("  the value of that SettingsItem field. Field names are");
                        printWriter.println("  case insensitive and out.println the m prefix can be omitted,");
                        printWriter.println("  so the following are equivalent: mState, mstate, State, state.");
                        return;
                    }
                    if ("--user".equals(str2)) {
                        if (i >= strArr.length) {
                            printWriter.println("Error: user missing argument");
                            return;
                        }
                        try {
                            dumpState.setUserId(Integer.parseInt(strArr[i]));
                            i++;
                        } catch (NumberFormatException unused) {
                            printWriter.println("Error: user argument is not a number: " + strArr[i]);
                            return;
                        }
                    } else if ("--verbose".equals(str2)) {
                        dumpState.setVerbose(true);
                    } else {
                        printWriter.println("Unknown argument: " + str2 + "; use -h for help");
                    }
                }
            }
            if (i < strArr.length) {
                str = strArr[i];
                i++;
                str.hashCode();
                switch (str) {
                    case "targetoverlayablename":
                    case "targetpackagename":
                    case "priority":
                    case "userid":
                    case "ismutable":
                    case "category":
                    case "state":
                    case "isenabled":
                    case "packagename":
                    case "basecodepath":
                        dumpState.setField(str);
                        break;
                    default:
                        dumpState.setOverlyIdentifier(str);
                        break;
                }
            }
            if (dumpState.getPackageName() == null && i < strArr.length) {
                dumpState.setOverlyIdentifier(strArr[i]);
            }
            enforceDumpPermission("dump");
            synchronized (OverlayManagerService.this.mLock) {
                OverlayManagerService.this.mImpl.dump(printWriter, dumpState);
                if (dumpState.getPackageName() == null) {
                    OverlayManagerService.this.mPackageManager.dump(printWriter, dumpState);
                }
            }
            OverlayManagerService.this.mWallpaperThemeManager.dump(printWriter);
            ContentResolver contentResolver = OverlayManagerService.this.getContext().getContentResolver();
            printWriter.println("ThemeFramework Information:");
            String string = Settings.System.getString(contentResolver, "current_sec_active_themepackage");
            String string2 = Settings.System.getString(contentResolver, "current_sec_appicon_theme_package");
            String string3 = Settings.System.getString(contentResolver, "wallpapertheme_state");
            String string4 = Settings.System.getString(contentResolver, "wallpapertheme_color_isgray");
            String string5 = Settings.System.getString(contentResolver, "wallpapertheme_color");
            int i2 = Settings.Global.getInt(contentResolver, "colortheme_app_icon", 0);
            printWriter.println("current_theme_package : " + string);
            printWriter.println("current_app_icon_package : " + string2);
            printWriter.println("wallpapertheme_state : " + string3);
            printWriter.println("wallpapertheme_color_isgray : " + string4);
            printWriter.println("wallpapertheme_color : " + string5);
            printWriter.println("colortheme_app_icon : " + i2);
            try {
                if (string != null) {
                    printWriter.println("available theme content at /data/overlays/main_packages : ");
                    dumpFilesInDir(new File("/data/overlays/main_packages"), printWriter);
                    String checkUserOrTrialPackageJson = checkUserOrTrialPackageJson("/data/overlays/jsonfiles", string, printWriter);
                    if (checkUserOrTrialPackageJson != null) {
                        printJsonInfo(checkUserOrTrialPackageJson, printWriter);
                    }
                } else {
                    printWriter.println("available theme content at /data/overlays/main_packages : " + ((Object) null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void dumpFilesInDir(File file, PrintWriter printWriter) {
            File[] listFiles;
            if (!file.exists() || (listFiles = file.listFiles()) == null) {
                return;
            }
            for (File file2 : listFiles) {
                PackageInfo packageArchiveInfo = OverlayManagerService.this.getContext().getPackageManager().getPackageArchiveInfo(file2.getPath(), PackageManager.PackageInfoFlags.of(4096L));
                String charSequence = packageArchiveInfo.applicationInfo.loadLabel(OverlayManagerService.this.getContext().getPackageManager()).toString();
                if (charSequence != null) {
                    printWriter.println("   " + packageArchiveInfo.packageName + "(" + charSequence + ")");
                } else {
                    printWriter.println("   " + packageArchiveInfo.packageName + "()");
                }
            }
        }

        public final String checkUserOrTrialPackageJson(String str, String str2, PrintWriter printWriter) {
            File file = new File(str + "/userjson");
            if (file.exists() && file.list() != null) {
                for (String str3 : file.list()) {
                    if (str3.startsWith(str2)) {
                        return file.getPath() + "/" + str3;
                    }
                }
            }
            File file2 = new File(str + "/trialjson");
            if (!file2.exists() || file2.list() == null) {
                return null;
            }
            for (String str4 : file2.list()) {
                if (str4.startsWith(str2)) {
                    return file2.getPath() + "/" + str4;
                }
            }
            return null;
        }

        public final void printJsonInfo(String str, PrintWriter printWriter) {
            printWriter.println("current theme Json: " + str);
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                sb.append(readLine);
                            } else {
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                                printWriter.println(new JSONObject(sb.toString()).toString(3) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                                return;
                            }
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        public final int handleIncomingUser(int i, String str) {
            return ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, true, str, null);
        }

        public final void enforceDumpPermission(String str) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DUMP", str);
        }

        public final void enforceActor(OverlayIdentifier overlayIdentifier, String str, int i) {
            OverlayInfo overlayInfo = OverlayManagerService.this.mImpl.getOverlayInfo(overlayIdentifier, i);
            if (overlayInfo == null) {
                throw new IllegalArgumentException("Unable to retrieve overlay information for " + overlayIdentifier);
            }
            OverlayManagerService.this.mActorEnforcer.enforceActor(overlayInfo, str, Binder.getCallingUid(), i);
        }

        public void replaceOverlays(List list, List list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt != null) {
                    OverlayManagerService.this.mOverlayManagerServiceExt.replaceOverlays(list, list2, iSamsungOverlayCallback, i);
                }
            }
        }

        public void addOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt != null) {
                    OverlayManagerService.this.mOverlayManagerServiceExt.addOverlays(list, iSamsungOverlayCallback, i);
                }
            }
        }

        public void removeOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt != null) {
                    OverlayManagerService.this.mOverlayManagerServiceExt.removeOverlays(list, iSamsungOverlayCallback, i);
                }
            }
        }

        public boolean changeOverlayState(String str, int i, boolean z) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt == null) {
                    return false;
                }
                return OverlayManagerService.this.mOverlayManagerServiceExt.changeOverlayState(str, i, z);
            }
        }

        public OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt == null) {
                    return new OverlayInfoExt[0];
                }
                return OverlayManagerService.this.mOverlayManagerServiceExt.getAllOverlaysInCategory(i, i2);
            }
        }

        public OverlayInfoExt getOverlayForPath(String str, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt == null) {
                    return null;
                }
                return OverlayManagerService.this.mOverlayManagerServiceExt.getOverlayForPath(str, i);
            }
        }

        public OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                if (OverlayManagerService.this.mOverlayManagerServiceExt == null) {
                    return new OverlayInfoExt[0];
                }
                return OverlayManagerService.this.mOverlayManagerServiceExt.getOverlaysForTarget(str, i, i2);
            }
        }

        public void applyWallpaperColors(List list, int i, int i2) {
            OverlayManagerService.this.mWallpaperThemeManager.applyWallpaperColors(list, i, i2);
        }

        public void applyWallpaperColor(List list, List list2, boolean z) {
            OverlayManagerService.this.mWallpaperThemeManager.applyWallpaperColor(list, list2, z);
        }

        public List getWallpaperColors() {
            return OverlayManagerService.this.mWallpaperThemeManager.getWallpaperColors();
        }

        public List readLastPalette() {
            return OverlayManagerService.this.mWallpaperThemeManager.readLastPalette();
        }

        public boolean getLastPalette(List list, List list2) {
            return OverlayManagerService.this.mWallpaperThemeManager.getLastPalette(list, list2);
        }

        public void applyThemeParkWallpaperColor(Uri uri) {
            OverlayManagerService.this.mWallpaperThemeManager.applyThemeParkWallpaperColor(uri);
        }

        public List getThemeParkOverlayNames(final String str) {
            return (List) OverlayManagerService.this.mSettings.getOverlaysForUser(handleIncomingUser(0, "getAllOverlays")).values().stream().flatMap(new OverlayManagerService$2$$ExternalSyntheticLambda1()).map(new Function() { // from class: com.android.server.om.OverlayManagerService$2$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((OverlayInfo) obj).getOverlayName();
                }
            }).filter(new Predicate() { // from class: com.android.server.om.OverlayManagerService$2$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getThemeParkOverlayNames$0;
                    lambda$getThemeParkOverlayNames$0 = OverlayManagerService.C20512.lambda$getThemeParkOverlayNames$0(str, (String) obj);
                    return lambda$getThemeParkOverlayNames$0;
                }
            }).collect(Collectors.toList());
        }

        public static /* synthetic */ boolean lambda$getThemeParkOverlayNames$0(String str, String str2) {
            return str2 != null && str2.startsWith(str);
        }
    }

    public final class PackageManagerHelperImpl implements PackageManagerHelper {
        public final Context mContext;
        public final ArrayMap mCache = new ArrayMap();
        public final Set mInitializedUsers = new ArraySet();
        public final IPackageManager mPackageManager = AppGlobals.getPackageManager();
        public final PackageManagerInternal mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);

        public class PackageStateUsers {
            public final Set mInstalledUsers;
            public PackageState mPackageState;

            public PackageStateUsers(PackageState packageState) {
                this.mInstalledUsers = new ArraySet();
                this.mPackageState = packageState;
            }
        }

        public PackageManagerHelperImpl(Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.om.PackageManagerHelper
        public ArrayMap initializeForUser(final int i) {
            ArrayMap arrayMap;
            synchronized (this.mCache) {
                if (!this.mInitializedUsers.contains(Integer.valueOf(i))) {
                    this.mInitializedUsers.add(Integer.valueOf(i));
                    this.mPackageManagerInternal.forEachPackageState(new Consumer() { // from class: com.android.server.om.OverlayManagerService$PackageManagerHelperImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            OverlayManagerService.PackageManagerHelperImpl.this.lambda$initializeForUser$0(i, (PackageStateInternal) obj);
                        }
                    });
                }
                arrayMap = new ArrayMap();
                int size = this.mCache.size();
                for (int i2 = 0; i2 < size; i2++) {
                    PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.valueAt(i2);
                    if (packageStateUsers.mInstalledUsers.contains(Integer.valueOf(i))) {
                        arrayMap.put((String) this.mCache.keyAt(i2), packageStateUsers.mPackageState);
                    }
                }
            }
            return arrayMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initializeForUser$0(int i, PackageStateInternal packageStateInternal) {
            if (packageStateInternal.getPkg() == null || !packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                return;
            }
            addPackageUser(packageStateInternal, i);
        }

        @Override // com.android.server.om.PackageManagerHelper
        public PackageState getPackageStateForUser(String str, int i) {
            synchronized (this.mCache) {
                PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.get(str);
                if (packageStateUsers != null && packageStateUsers.mInstalledUsers.contains(Integer.valueOf(i))) {
                    return packageStateUsers.mPackageState;
                }
                try {
                    if (!this.mPackageManager.isPackageAvailable(str, i)) {
                        return null;
                    }
                    return addPackageUser(str, i);
                } catch (RemoteException e) {
                    Slog.w("OverlayManager", "Failed to check availability of package '" + str + "' for user " + i, e);
                    return null;
                }
            }
        }

        public final PackageState addPackageUser(String str, int i) {
            PackageStateInternal packageStateInternal = this.mPackageManagerInternal.getPackageStateInternal(str);
            if (packageStateInternal == null) {
                Slog.w("OverlayManager", "Android package for '" + str + "' could not be found; continuing as if package was never added", new Throwable());
                return null;
            }
            return addPackageUser(packageStateInternal, i);
        }

        public final PackageState addPackageUser(PackageState packageState, int i) {
            PackageState packageState2;
            synchronized (this.mCache) {
                PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.get(packageState.getPackageName());
                if (packageStateUsers == null) {
                    packageStateUsers = new PackageStateUsers(packageState);
                    this.mCache.put(packageState.getPackageName(), packageStateUsers);
                } else {
                    packageStateUsers.mPackageState = packageState;
                }
                packageStateUsers.mInstalledUsers.add(Integer.valueOf(i));
                packageState2 = packageStateUsers.mPackageState;
            }
            return packageState2;
        }

        public final void removePackageUser(String str, int i) {
            synchronized (this.mCache) {
                PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.get(str);
                if (packageStateUsers == null) {
                    return;
                }
                removePackageUser(packageStateUsers, i);
            }
        }

        public final void removePackageUser(PackageStateUsers packageStateUsers, int i) {
            synchronized (this.mCache) {
                packageStateUsers.mInstalledUsers.remove(Integer.valueOf(i));
                if (packageStateUsers.mInstalledUsers.isEmpty()) {
                    this.mCache.remove(packageStateUsers.mPackageState.getPackageName());
                }
            }
        }

        public PackageState onPackageAdded(String str, int i) {
            return addPackageUser(str, i);
        }

        public PackageState onPackageUpdated(String str, int i) {
            return addPackageUser(str, i);
        }

        public void onPackageRemoved(String str, int i) {
            removePackageUser(str, i);
        }

        public boolean isInstantApp(String str, int i) {
            return this.mPackageManagerInternal.isInstantApp(str, i);
        }

        @Override // com.android.server.om.PackageManagerHelper
        public Map getNamedActors() {
            return SystemConfig.getInstance().getNamedActors();
        }

        @Override // com.android.server.om.PackageManagerHelper
        public boolean signaturesMatching(String str, String str2, int i) {
            try {
                return this.mPackageManager.checkSignatures(str, str2, i) == 0;
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public String getConfigSignaturePackage() {
            String[] knownPackageNames = this.mPackageManagerInternal.getKnownPackageNames(13, 0);
            if (knownPackageNames.length == 0) {
                return null;
            }
            return knownPackageNames[0];
        }

        @Override // com.android.server.om.PackageManagerHelper
        public OverlayableInfo getOverlayableForTarget(String str, String str2, int i) {
            PackageState packageStateForUser = getPackageStateForUser(str, i);
            ApkAssets apkAssets = null;
            AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
            if (androidPackage == null) {
                throw new IOException("Unable to get target package");
            }
            try {
                apkAssets = ApkAssets.loadFromPath(((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath());
                OverlayableInfo overlayableInfo = apkAssets.getOverlayableInfo(str2);
                try {
                    apkAssets.close();
                } catch (Throwable unused) {
                }
                return overlayableInfo;
            } catch (Throwable th) {
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused2) {
                    }
                }
                throw th;
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public boolean doesTargetDefineOverlayable(String str, int i) {
            PackageState packageStateForUser = getPackageStateForUser(str, i);
            ApkAssets apkAssets = null;
            AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
            if (androidPackage == null) {
                throw new IOException("Unable to get target package");
            }
            try {
                apkAssets = ApkAssets.loadFromPath(((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath());
                boolean definesOverlayable = apkAssets.definesOverlayable();
                try {
                    apkAssets.close();
                } catch (Throwable unused) {
                }
                return definesOverlayable;
            } catch (Throwable th) {
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused2) {
                    }
                }
                throw th;
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public void enforcePermission(String str, String str2) {
            this.mContext.enforceCallingOrSelfPermission(str, str2);
        }

        public void forgetAllPackageInfos(int i) {
            synchronized (this.mCache) {
                for (int size = this.mCache.size() - 1; size >= 0; size--) {
                    removePackageUser((PackageStateUsers) this.mCache.valueAt(size), i);
                }
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public String[] getPackagesForUid(int i) {
            try {
                return this.mPackageManager.getPackagesForUid(i);
            } catch (RemoteException unused) {
                return null;
            }
        }

        public void dump(PrintWriter printWriter, DumpState dumpState) {
            synchronized (this.mCache) {
                printWriter.println("AndroidPackage cache");
                if (!dumpState.isVerbose()) {
                    printWriter.println("    " + this.mCache.size() + " package(s)");
                    return;
                }
                if (this.mCache.size() == 0) {
                    printWriter.println("    <empty>");
                    return;
                }
                int size = this.mCache.size();
                for (int i = 0; i < size; i++) {
                    String str = (String) this.mCache.keyAt(i);
                    PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.valueAt(i);
                    printWriter.print("    " + str + ": " + packageStateUsers.mPackageState + " users=");
                    printWriter.println(TextUtils.join(", ", packageStateUsers.mInstalledUsers));
                }
            }
        }
    }

    public final void updateTargetPackagesLocked(UserPackage userPackage) {
        if (userPackage != null) {
            updateTargetPackagesLocked(Set.of(userPackage));
        }
    }

    public final void updateTargetPackagesLocked(Set set) {
        updateTargetPackagesLocked(set, false);
    }

    public final void updateTargetPackagesLocked(Set set, final boolean z) {
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        persistSettingsLocked();
        SparseArray groupTargetsByUserId = groupTargetsByUserId(set);
        int size = groupTargetsByUserId.size();
        for (int i = 0; i < size; i++) {
            final ArraySet arraySet = (ArraySet) groupTargetsByUserId.valueAt(i);
            final int keyAt = groupTargetsByUserId.keyAt(i);
            final List updatePackageManagerLocked = updatePackageManagerLocked(arraySet, keyAt);
            if (!updatePackageManagerLocked.isEmpty()) {
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OverlayManagerService.this.lambda$updateTargetPackagesLocked$1(updatePackageManagerLocked, keyAt, z, arraySet);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTargetPackagesLocked$1(List list, int i, boolean z, ArraySet arraySet) {
        updateActivityManager(list, i);
        if (z) {
            Slog.i("SWT_OverlayManager", "overlay changed broadcast to system for color theme");
            broadcastActionOverlayChanged(new ArraySet(Arrays.asList("android")), i);
        } else {
            broadcastActionOverlayChanged(arraySet, i);
        }
    }

    public static SparseArray groupTargetsByUserId(Set set) {
        final SparseArray sparseArray = new SparseArray();
        CollectionUtils.forEach(set, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda4
            public final void acceptOrThrow(Object obj) {
                OverlayManagerService.lambda$groupTargetsByUserId$2(sparseArray, (UserPackage) obj);
            }
        });
        return sparseArray;
    }

    public static /* synthetic */ void lambda$groupTargetsByUserId$2(SparseArray sparseArray, UserPackage userPackage) {
        ArraySet arraySet = (ArraySet) sparseArray.get(userPackage.userId);
        if (arraySet == null) {
            arraySet = new ArraySet();
            sparseArray.put(userPackage.userId, arraySet);
        }
        arraySet.add(userPackage.packageName);
    }

    public static void broadcastActionOverlayChanged(Set set, final int i) {
        final ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        CollectionUtils.forEach(set, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda1
            public final void acceptOrThrow(Object obj) {
                OverlayManagerService.lambda$broadcastActionOverlayChanged$3(i, activityManagerInternal, (String) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$broadcastActionOverlayChanged$3(int i, ActivityManagerInternal activityManagerInternal, String str) {
        Intent intent = new Intent("android.intent.action.OVERLAY_CHANGED", Uri.fromParts("package", str, null));
        intent.setFlags(67108864);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.USER_ID", i);
        activityManagerInternal.broadcastIntent(intent, (IIntentReceiver) null, (String[]) null, false, i, (int[]) null, new BiFunction() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Bundle filterReceiverAccess;
                filterReceiverAccess = OverlayManagerService.filterReceiverAccess(((Integer) obj).intValue(), (Bundle) obj2);
                return filterReceiverAccess;
            }
        }, (Bundle) null);
    }

    @SystemApi
    public static void broadcastActionOverlayChangedPublic(Set set, int i) {
        broadcastActionOverlayChanged(set, i);
    }

    public static Bundle filterReceiverAccess(int i, Bundle bundle) {
        if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).filterAppAccess(bundle.getString("android.intent.extra.PACKAGE_NAME"), i, bundle.getInt("android.intent.extra.USER_ID"), false)) {
            return null;
        }
        return bundle;
    }

    public final void updateActivityManager(List list, int i) {
        try {
            ActivityManager.getService().scheduleApplicationInfoChanged(list, i);
        } catch (RemoteException e) {
            Slog.e("OverlayManager", "updateActivityManager remote exception", e);
        }
    }

    public final SparseArray updatePackageManagerLocked(Set set) {
        if (CollectionUtils.isEmpty(set)) {
            return new SparseArray();
        }
        SparseArray sparseArray = new SparseArray();
        SparseArray groupTargetsByUserId = groupTargetsByUserId(set);
        int size = groupTargetsByUserId.size();
        for (int i = 0; i < size; i++) {
            int keyAt = groupTargetsByUserId.keyAt(i);
            sparseArray.put(keyAt, updatePackageManagerLocked((Collection) groupTargetsByUserId.valueAt(i), keyAt));
        }
        return sparseArray;
    }

    public final List updatePackageManagerLocked(Collection collection, int i) {
        return updatePackageManagerLocked(collection, i, false);
    }

    public final List updatePackageManagerLocked(Collection collection, int i, boolean z) {
        try {
            Trace.traceBegin(67108864L, "OMS#updatePackageManagerLocked " + collection);
            Slog.d("OverlayManager", "Update package manager about changed overlays");
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            if (collection.contains("android") && !z) {
                collection = packageManagerInternal.getTargetPackageNames(i);
            }
            ArrayMap arrayMap = new ArrayMap(collection.size());
            synchronized (this.mLock) {
                OverlayPaths enabledOverlayPaths = this.mImpl.getEnabledOverlayPaths("android", i, false);
                for (String str : collection) {
                    OverlayPaths.Builder builder = new OverlayPaths.Builder();
                    OverlayPaths filterByPolicy = !"android".equals(str) ? OverlayPolicyManager.filterByPolicy(this.mImpl.getEnabledOverlayPaths(str, i, true), str, i) : null;
                    builder.addAll(OverlayPolicyManager.filterByPolicy(enabledOverlayPaths, filterByPolicy, str, i));
                    builder.addAll(filterByPolicy);
                    arrayMap.put(str, builder.build());
                }
            }
            Set hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            packageManagerInternal.setEnabledOverlayPackages(i, arrayMap, hashSet, hashSet2);
            for (String str2 : collection) {
                Slog.d("OverlayManager", "-> Updating overlay: target=" + str2 + " overlays=[" + arrayMap.get(str2) + "] userId=" + i);
                if (hashSet2.contains(str2)) {
                    Slog.e("OverlayManager", TextUtils.formatSimple("Failed to change enabled overlays for %s user %d", new Object[]{str2, Integer.valueOf(i)}));
                }
            }
            return new ArrayList(hashSet);
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    public final void persistSettingsLocked() {
        Slog.d("OverlayManager", "Writing overlay settings");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mSettingsFile.startWrite();
            this.mSettings.persist(fileOutputStream);
            this.mSettingsFile.finishWrite(fileOutputStream);
        } catch (IOException | XmlPullParserException e) {
            this.mSettingsFile.failWrite(fileOutputStream);
            Slog.e("OverlayManager", "failed to persist overlay state", e);
        }
    }

    public final void restoreSettings() {
        try {
            Trace.traceBegin(67108864L, "OMS#restoreSettings");
            synchronized (this.mLock) {
                if (this.mSettingsFile.getBaseFile().exists()) {
                    try {
                        FileInputStream openRead = this.mSettingsFile.openRead();
                        try {
                            this.mSettings.restore(openRead);
                            List users = this.mUserManager.getUsers(true);
                            int[] iArr = new int[users.size()];
                            for (int i = 0; i < users.size(); i++) {
                                iArr[i] = ((UserInfo) users.get(i)).getUserHandle().getIdentifier();
                            }
                            Arrays.sort(iArr);
                            for (int i2 : this.mSettings.getUsers()) {
                                if (Arrays.binarySearch(iArr, i2) < 0) {
                                    this.mSettings.removeUser(i2);
                                }
                            }
                            if (openRead != null) {
                                openRead.close();
                            }
                        } catch (Throwable th) {
                            if (openRead != null) {
                                try {
                                    openRead.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException | XmlPullParserException e) {
                        Slog.e("OverlayManager", "failed to restore overlay state", e);
                    }
                }
            }
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    public final void disableDynamicContrast() {
        try {
            ContentResolver contentResolver = getContext().getContentResolver();
            for (int i : this.mUserManager.getUserIds()) {
                Settings.Secure.putFloatForUser(contentResolver, "contrast_level", -1.0f, i);
                Settings.Secure.putStringForUser(contentResolver, "theme_customization_overlay_packages", "", i);
            }
        } catch (Exception e) {
            Slog.i("OverlayManager", "disableDynamicContrast posted delay, due: " + e);
            FgThread.getHandler().postDelayed(new Runnable() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    OverlayManagerService.this.lambda$disableDynamicContrast$4();
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disableDynamicContrast$4() {
        disableDynamicContrast();
        Slog.i("OverlayManager", "disableDynamicContrast done");
    }

    public final Set unregisterColorThemeGG(boolean z) {
        Set set;
        synchronized (this.mLock) {
            set = null;
            for (Pair pair : this.mSettings.getAllIdentifiersAndBaseCodePaths()) {
                String overlayName = ((OverlayIdentifier) pair.first).getOverlayName();
                if ("com.android.systemui".equals(((OverlayIdentifier) pair.first).getPackageName()) && (overlayName.contains("neutral") || overlayName.contains("accent") || (!z && overlayName.contains("dynamic")))) {
                    set = CollectionUtils.addAll(set, this.mImpl.unregisterFabricatedOverlay((OverlayIdentifier) pair.first));
                }
            }
        }
        return set;
    }

    public final void unregisterUnusedPaletteOverlays() {
        synchronized (this.mLock) {
            for (Pair pair : this.mSettings.getAllIdentifiersAndBaseCodePaths()) {
                String overlayName = ((OverlayIdentifier) pair.first).getOverlayName();
                if (overlayName != null && overlayName.startsWith("SemWT_")) {
                    this.mImpl.unregisterFabricatedOverlay((OverlayIdentifier) pair.first);
                }
            }
        }
    }

    public final class PackageUpdateHelper {
        public PackageUpdateHelper() {
        }

        public void persistSettings() {
            synchronized (OverlayManagerService.this.mLock) {
                OverlayManagerService.this.persistSettingsLocked();
            }
        }

        public List updatePackageManager(Collection collection, int i, boolean z) {
            List updatePackageManagerLocked;
            synchronized (OverlayManagerService.this.mLock) {
                updatePackageManagerLocked = OverlayManagerService.this.updatePackageManagerLocked(collection, i, z);
            }
            return updatePackageManagerLocked;
        }
    }
}
