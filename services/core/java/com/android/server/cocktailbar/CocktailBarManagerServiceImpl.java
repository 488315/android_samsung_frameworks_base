package com.android.server.cocktailbar;

import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.widget.RemoteViews;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.IRemoteViewsFactory;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicyManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.android.server.cocktailbar.utils.CocktailBarHistory;
import com.android.server.cocktailbar.utils.CocktailBarUtils$CocktailBarWhiteList;
import com.android.server.cocktailbar.utils.ServiceImplCommandLogger;

import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import com.samsung.android.cocktailbar.ICocktailHost;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

public final class CocktailBarManagerServiceImpl {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final boolean EMERGENCY_MODE_ENABLED;
    public static final int MIN_UPDATE_PERIOD;
    public final AlarmManager mAlarmManager;
    public final SparseArray mCocktailArr;
    public final CocktailPolicyManager mCocktailPolicyManager;
    public CocktailBarSettingsObserver mCocktailSettingsObserver;
    public final ServiceImplCommandLogger mCommandLogger;
    public final CocktailBarConfig mConfig;
    public final Context mContext;
    public int mDefaultDisplayDensity;
    public final EdgeStartHandler mEdgeStartHandler;
    public final EnabledPackageMap mEnabledCocktailPackages;
    public final Handler mHandler;
    public HashMap mHost;
    public int mHostCategory;
    public boolean mInitialzed;
    public final IDeviceIdleController mLocalDeviceIdleController;
    public Locale mLocale;
    public final CocktailBarModeManager mModeManager;
    public int mNextCocktailId;
    public int mNextUserId;
    public final IPackageManager mPm;
    public final HashMap mRemoteViewsServicesCocktails;
    public final AnonymousClass1 mSaveStateRunnable;
    public CocktailBarSettings mSettings;
    public boolean mStateLoaded;
    public final int mUserId;
    public final UserManager mUserManager;

    /* renamed from: com.android.server.cocktailbar.CocktailBarManagerServiceImpl$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CocktailBarManagerServiceImpl this$0;
        public final /* synthetic */ ArrayList val$enabledCocktailList;

        public /* synthetic */ AnonymousClass4(
                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl,
                ArrayList arrayList,
                int i) {
            this.$r8$classId = i;
            this.this$0 = cocktailBarManagerServiceImpl;
            this.val$enabledCocktailList = arrayList;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mSettings.setEnabledCocktailsLocked(this.val$enabledCocktailList);
                    break;
                default:
                    this.this$0.mSettings.setEnabledCocktailsLocked(this.val$enabledCocktailList);
                    break;
            }
        }
    }

    public final class CocktailBarSettingsObserver extends ContentObserver {
        public String mLastEnabled;

        public CocktailBarSettingsObserver(Handler handler) {
            super(handler);
            this.mLastEnabled = "";
            CocktailBarManagerServiceImpl.this
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("cocktail_bar_enabled_cocktails"),
                            false,
                            this,
                            CocktailBarManagerServiceImpl.this.mUserId);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            String str;
            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    CocktailBarManagerServiceImpl.this;
            String str2 = this.mLastEnabled;
            synchronized (cocktailBarManagerServiceImpl.mCocktailArr) {
                try {
                    if (cocktailBarManagerServiceImpl.ensureStateLoadedLocked()) {
                        String enabledCocktailsStr =
                                cocktailBarManagerServiceImpl.mSettings.getEnabledCocktailsStr();
                        if (enabledCocktailsStr != null && !str2.equals(enabledCocktailsStr)) {
                            cocktailBarManagerServiceImpl.updateFromSettingsLocked();
                            str2 = enabledCocktailsStr;
                        }
                        str = str2;
                    } else {
                        Slog.i(
                                "CocktailBarManagerServiceImpl",
                                "updateCocktailBarSetting : not loaded u="
                                        + cocktailBarManagerServiceImpl.mUserId
                                        + " lastEnabled="
                                        + str2);
                        str = "";
                    }
                } finally {
                }
            }
            this.mLastEnabled = str;
        }
    }

    public final class CocktailHostInfo implements IBinder.DeathRecipient {
        public final ICocktailHost callbackHost;
        public int category;
        public final String mPackageName;
        public final IBinder token;

        public CocktailHostInfo(String str, ICocktailHost iCocktailHost, int i) {
            this.callbackHost = iCocktailHost;
            this.token = iCocktailHost.asBinder();
            this.category = i;
            this.mPackageName = str;
            ServiceImplCommandLogger serviceImplCommandLogger =
                    CocktailBarManagerServiceImpl.this.mCommandLogger;
            serviceImplCommandLogger.getClass();
            ServiceImplCommandLogger.HostDumpInfo hostDumpInfo =
                    new ServiceImplCommandLogger.HostDumpInfo();
            try {
                hostDumpInfo.mStratTime =
                        new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
            } catch (Exception e) {
                e.printStackTrace();
                hostDumpInfo.mStratTime = String.valueOf(System.currentTimeMillis());
            }
            synchronized (serviceImplCommandLogger.mHostDumpInfoCache) {
                serviceImplCommandLogger.mHostDumpInfoCache.put(str, hostDumpInfo);
            }
            try {
                this.token.linkToDeath(this, 0);
            } catch (RemoteException unused) {
                boolean z = CocktailBarManagerServiceImpl.DEBUG;
                Slog.e("CocktailBarManagerServiceImpl", "CocktailHostInfo : linkToDeath error");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = CocktailBarManagerServiceImpl.DEBUG;
            Slog.v("CocktailBarManagerServiceImpl", "binderDied : binder = " + this.token);
            if (Settings.Secure.getInt(
                            CocktailBarManagerServiceImpl.this.mContext.getContentResolver(),
                            "edge_enable",
                            1)
                    == 1) {
                CocktailBarManagerServiceImpl.this.mEdgeStartHandler.removeMessages(1);
                CocktailBarManagerServiceImpl.this.mEdgeStartHandler.sendEmptyMessageDelayed(
                        1, 5000L);
            }
            synchronized (CocktailBarManagerServiceImpl.this.mHost) {
                try {
                    CocktailBarManagerServiceImpl.this.mCommandLogger.recordHostEnd(
                            this.mPackageName);
                    if (equals(CocktailBarManagerServiceImpl.this.mHost.get(this.mPackageName))) {
                        CocktailBarManagerServiceImpl.this.mHost.remove(this.mPackageName);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            try {
                this.token.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }

        public final void unlinkBinder() {
            try {
                CocktailBarManagerServiceImpl.this.mCommandLogger.recordHostEnd(this.mPackageName);
                this.token.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    public final class EdgeStartHandler extends Handler {
        public EdgeStartHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                    CocktailBarManagerServiceImpl.this;
            int i = cocktailBarManagerServiceImpl.mUserId;
            int i2 = cocktailBarManagerServiceImpl.mNextUserId;
            if (i2 != -10) {
                i = i2;
            }
            boolean z = CocktailBarManagerServiceImpl.DEBUG;
            StringBuilder sb = new StringBuilder("EdgeStartHandler userId : ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    cocktailBarManagerServiceImpl.mUserId,
                    i,
                    ", currentUserId : ",
                    ", nextUserId : ",
                    sb);
            sb.append(cocktailBarManagerServiceImpl.mNextUserId);
            Slog.i("CocktailBarManagerServiceImpl", sb.toString());
            Intent intent =
                    new Intent("com.samsung.android.cocktailbar.intent.action.EDGE_APP_START");
            intent.addFlags(16777248);
            cocktailBarManagerServiceImpl.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
            cocktailBarManagerServiceImpl.mNextUserId = cocktailBarManagerServiceImpl.mUserId;
        }
    }

    public final class EnabledPackageMap {
        public ArrayMap mEnabledPackage;

        public final int getEnabledCount(String str) {
            ArrayList arrayList = (ArrayList) this.mEnabledPackage.get(str);
            if (arrayList == null) {
                return 0;
            }
            return arrayList.size();
        }

        public final void putEnabledProvider(ComponentName componentName) {
            if (componentName == null) {
                return;
            }
            String packageName = componentName.getPackageName();
            String className = componentName.getClassName();
            ArrayList arrayList = (ArrayList) this.mEnabledPackage.get(packageName);
            if (arrayList == null) {
                arrayList = PortStatus_1_1$$ExternalSyntheticOutline0.m(className);
            } else if (!arrayList.contains(className)) {
                arrayList.add(className);
            }
            this.mEnabledPackage.put(packageName, arrayList);
        }

        public final void removeEnabledProvider(ComponentName componentName) {
            if (componentName == null) {
                return;
            }
            String packageName = componentName.getPackageName();
            String className = componentName.getClassName();
            ArrayList arrayList = (ArrayList) this.mEnabledPackage.get(packageName);
            if (arrayList == null) {
                return;
            }
            if (arrayList.contains(className)) {
                arrayList.remove(className);
            }
            this.mEnabledPackage.put(packageName, arrayList);
        }
    }

    static {
        MIN_UPDATE_PERIOD = Debug.semIsProductDev() ? 1800000 : 0;
        EMERGENCY_MODE_ENABLED =
                SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING")
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING");
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.cocktailbar.CocktailBarManagerServiceImpl$1] */
    public CocktailBarManagerServiceImpl(
            Context context,
            Handler handler,
            CocktailBarModeManager cocktailBarModeManager,
            CocktailPolicyManager cocktailPolicyManager,
            int i) {
        SparseArray sparseArray = new SparseArray();
        this.mCocktailArr = sparseArray;
        this.mRemoteViewsServicesCocktails = new HashMap();
        this.mNextUserId = -10;
        this.mHost = new HashMap();
        this.mNextCocktailId = 0;
        this.mInitialzed = false;
        EnabledPackageMap enabledPackageMap = new EnabledPackageMap();
        enabledPackageMap.mEnabledPackage = new ArrayMap();
        this.mEnabledCocktailPackages = enabledPackageMap;
        ServiceImplCommandLogger serviceImplCommandLogger = new ServiceImplCommandLogger();
        serviceImplCommandLogger.mHostDumpInfoCache =
                new ServiceImplCommandLogger.AnonymousClass1(10);
        this.mCommandLogger = serviceImplCommandLogger;
        final int i2 = 1;
        this.mSaveStateRunnable =
                new Runnable(
                        this) { // from class:
                                // com.android.server.cocktailbar.CocktailBarManagerServiceImpl.1
                    public final /* synthetic */ CocktailBarManagerServiceImpl this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                                        this.this$0;
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                                        this.this$0;
                                cocktailBarManagerServiceImpl.mCocktailSettingsObserver =
                                        cocktailBarManagerServiceImpl2
                                        .new CocktailBarSettingsObserver(
                                                cocktailBarManagerServiceImpl2.mHandler);
                                return;
                            default:
                                synchronized (this.this$0.mCocktailArr) {
                                    this.this$0.ensureStateLoadedLocked();
                                    CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl3 =
                                            this.this$0;
                                    AtomicFile savedStateFile =
                                            cocktailBarManagerServiceImpl3.savedStateFile();
                                    try {
                                        FileOutputStream startWrite = savedStateFile.startWrite();
                                        if (cocktailBarManagerServiceImpl3.writeStateToFileLocked(
                                                startWrite)) {
                                            savedStateFile.finishWrite(startWrite);
                                        } else {
                                            savedStateFile.failWrite(startWrite);
                                            Slog.w(
                                                    "CocktailBarManagerServiceImpl",
                                                    "Failed to save state, restoring backup.");
                                        }
                                    } catch (IOException e) {
                                        Slog.w(
                                                "CocktailBarManagerServiceImpl",
                                                "Failed open state file for write: " + e);
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mContext = context;
        this.mUserId = i;
        this.mNextCocktailId = (i << 16) | this.mNextCocktailId;
        this.mPm = AppGlobals.getPackageManager();
        this.mConfig = CocktailBarConfig.getInstance(context);
        this.mModeManager = cocktailBarModeManager;
        this.mCocktailPolicyManager = cocktailPolicyManager;
        this.mHandler = handler;
        this.mEdgeStartHandler = new EdgeStartHandler(Looper.getMainLooper());
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mLocalDeviceIdleController =
                IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
        this.mLocale = Locale.getDefault();
        if (Process.myPid() == Binder.getCallingPid()) {
            this.mCocktailSettingsObserver = new CocktailBarSettingsObserver(handler);
        } else {
            final int i3 = 0;
            handler.post(
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.cocktailbar.CocktailBarManagerServiceImpl.1
                        public final /* synthetic */ CocktailBarManagerServiceImpl this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl =
                                            this.this$0;
                                    CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 =
                                            this.this$0;
                                    cocktailBarManagerServiceImpl.mCocktailSettingsObserver =
                                            cocktailBarManagerServiceImpl2
                                            .new CocktailBarSettingsObserver(
                                                    cocktailBarManagerServiceImpl2.mHandler);
                                    return;
                                default:
                                    synchronized (this.this$0.mCocktailArr) {
                                        this.this$0.ensureStateLoadedLocked();
                                        CocktailBarManagerServiceImpl
                                                cocktailBarManagerServiceImpl3 = this.this$0;
                                        AtomicFile savedStateFile =
                                                cocktailBarManagerServiceImpl3.savedStateFile();
                                        try {
                                            FileOutputStream startWrite =
                                                    savedStateFile.startWrite();
                                            if (cocktailBarManagerServiceImpl3
                                                    .writeStateToFileLocked(startWrite)) {
                                                savedStateFile.finishWrite(startWrite);
                                            } else {
                                                savedStateFile.failWrite(startWrite);
                                                Slog.w(
                                                        "CocktailBarManagerServiceImpl",
                                                        "Failed to save state, restoring backup.");
                                            }
                                        } catch (IOException e) {
                                            Slog.w(
                                                    "CocktailBarManagerServiceImpl",
                                                    "Failed open state file for write: " + e);
                                        }
                                    }
                                    return;
                            }
                        }
                    });
        }
        this.mDefaultDisplayDensity = context.getResources().getConfiguration().densityDpi;
        synchronized (sparseArray) {
            ensureStateLoadedLocked();
        }
    }

    public static void checkCocktailAttributeLocked(Cocktail cocktail, int i) {
        cocktail.setState((i == 0 || (i & cocktail.getProviderInfo().category) != 0) ? 0 : 2);
    }

    public static boolean checkSize(RemoteViews remoteViews) {
        if (remoteViews == null || remoteViews.estimateMemoryUsage() <= 10000000) {
            return true;
        }
        Slog.w(
                "CocktailBarManagerServiceImpl",
                "checkSize : size =" + remoteViews.estimateMemoryUsage());
        return false;
    }

    public static void dumpCocktail(Cocktail cocktail, PrintWriter printWriter) {
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        printWriter.print("  [");
        printWriter.print(cocktail.getCocktailId());
        printWriter.print("] provider ");
        printWriter.print(providerInfo.provider.flattenToShortString());
        printWriter.println(':');
        printWriter.print(" (label=");
        printWriter.print(providerInfo.label);
        printWriter.print(") (description=");
        printWriter.print(providerInfo.description);
        printWriter.print(") (icon=");
        printWriter.print(providerInfo.icon);
        printWriter.print(") (previewImage=");
        printWriter.print(providerInfo.previewImage);
        printWriter.print(") (updatePeriodMillis=");
        printWriter.print(providerInfo.updatePeriodMillis);
        printWriter.print(") (category=");
        printWriter.print(providerInfo.category);
        printWriter.print(") (permitVisibilityChanged=");
        printWriter.print(providerInfo.permitVisibilityChanged);
        printWriter.print(") (configure=");
        printWriter.print(providerInfo.configure);
        printWriter.print(") (privateMode=");
        printWriter.print(providerInfo.privateMode);
        printWriter.print(") (cscPreviewImage=");
        printWriter.print(providerInfo.cscPreviewImage);
        printWriter.print(" (uid=");
        printWriter.print(cocktail.getUid());
        printWriter.print(") ");
        printWriter.print(cocktail.dump());
        printWriter.println("\n");
    }

    public static String getPackageNameFromCocktail(Cocktail cocktail) {
        if (cocktail.getProvider() != null) {
            return cocktail.getProvider().getPackageName();
        }
        return null;
    }

    public final Cocktail addProviderLocked(ResolveInfo resolveInfo, int i, int i2) {
        int i3 = resolveInfo.activityInfo.applicationInfo.flags & 262144;
        boolean z = DEBUG;
        if (i3 != 0) {
            if (z) {
                Slog.i(
                        "CocktailBarManagerServiceImpl",
                        "addProviderLocked : Application FLAG_EXTERNAL_STORAGE");
            }
            return null;
        }
        if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                || SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING")
                || SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING")) {
            if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
                if (z) {
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "addProviderLocked : even if the package is disable in emergency mode,"
                                + " allow creating cocktail");
                }
            } else if (!resolveInfo.activityInfo.isEnabled()) {
                if (z) {
                    Slog.i("CocktailBarManagerServiceImpl", "addProviderLocked : disable");
                }
                return null;
            }
        } else if (!resolveInfo.activityInfo.isEnabled()) {
            if (z) {
                Slog.i("CocktailBarManagerServiceImpl", "addProviderLocked : disable");
            }
            return null;
        }
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ComponentName componentName =
                new ComponentName(activityInfo.packageName, activityInfo.name);
        if (lookupProviderLocked(componentName) != null) {
            Slog.e(
                    "CocktailBarManagerServiceImpl",
                    "addProviderLocked : already existed(" + componentName.toString() + ")");
            return null;
        }
        int i4 = this.mNextCocktailId + 1;
        this.mNextCocktailId = i4;
        Cocktail cocktail = new Cocktail(i4);
        if (parseAndUpdateProviderInfoXml(cocktail, componentName, resolveInfo, i, i2)) {
            checkCocktailAttributeLocked(cocktail, this.mHostCategory);
        } else {
            this.mNextCocktailId--;
            cocktail = null;
        }
        if (cocktail == null) {
            Slog.e(
                    "CocktailBarManagerServiceImpl",
                    "addProviderLocked : parseProviderInfoXmlLocked cocktail is null"
                            + componentName.toString());
            return null;
        }
        cocktail.setVersion(i2);
        if (cocktail.getProviderInfo().category == 512) {
            this.mCocktailPolicyManager.establishPolicy(cocktail, 1);
        }
        this.mCocktailArr.put(cocktail.getCocktailId(), cocktail);
        if (z) {
            Slog.i("CocktailBarManagerServiceImpl", "addProviderLocked : success");
        }
        return cocktail;
    }

    public final boolean addProvidersForPackageLocked(int i, String str) {
        boolean z = DEBUG;
        if (z) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "addProvidersForPackageLocked : pkgName = " + str + " v=" + i);
        }
        String updateIntentName = Cocktail.getUpdateIntentName(i);
        List queryIntentReceivers =
                queryIntentReceivers(
                        this.mUserId,
                        ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                updateIntentName, str));
        int size = queryIntentReceivers == null ? 0 : queryIntentReceivers.size();
        if (z) {
            HermesService$3$$ExternalSyntheticOutline0.m(
                    size,
                    "addProvidersForPackageLocked : queryIntentReceivers=",
                    "CocktailBarManagerServiceImpl");
        }
        int categoryIds = CocktailProviderInfo.getCategoryIds(this.mConfig.getCategoryFilter());
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentReceivers.get(i2);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (z) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "addProvidersForPackageLocked : ", str, " ai="),
                        activityInfo.packageName,
                        "CocktailBarManagerServiceImpl");
            }
            if ((activityInfo.applicationInfo.flags & 262144) != 0) {
                if (z) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "addProvidersForPackageLocked : application"
                                        + " FLAG_EXTERNAL_STORAGE"),
                            activityInfo.packageName,
                            "CocktailBarManagerServiceImpl");
                }
            } else if (TextUtils.equals(str, activityInfo.packageName)) {
                Cocktail addProviderLocked = addProviderLocked(resolveInfo, categoryIds, i);
                if (addProviderLocked != null) {
                    if (addProviderLocked.getProviderInfo().category == 4) {
                        z3 = true;
                    }
                    sendUpdateIntentLocked(addProviderLocked, updateIntentName, false);
                    z2 = true;
                } else if (z) {
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "addProvidersForPackageLocked : Cocktail is null for " + str);
                }
            }
        }
        if (z2 && z3) {
            updateToolLauncher();
        }
        return z2;
    }

    public final void clearCocktailInfoLocked(String str) {
        if (DEBUG) {
            Slog.i("CocktailBarManagerServiceImpl", "clearCocktailInfoLocked : packageName=" + str);
        }
        for (int size = this.mCocktailArr.size() - 1; size >= 0; size--) {
            Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(size);
            if (TextUtils.equals(str, getPackageNameFromCocktail(cocktail))) {
                cocktail.updateCocktailInfo((CocktailInfo) null);
            }
        }
    }

    public final void decrementCocktailServiceRefCount(Cocktail cocktail) {
        Iterator it = this.mRemoteViewsServicesCocktails.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Intent.FilterComparison filterComparison = (Intent.FilterComparison) entry.getKey();
            HashSet hashSet = (HashSet) entry.getValue();
            if (hashSet.remove(Integer.valueOf(cocktail.getCocktailId())) && hashSet.isEmpty()) {
                final Intent intent = filterComparison.getIntent();
                ServiceConnection serviceConnection = new ServiceConnection() { // from class:
                            // com.android.server.cocktailbar.CocktailBarManagerServiceImpl.6
                            @Override // android.content.ServiceConnection
                            public final void onServiceConnected(
                                    ComponentName componentName, IBinder iBinder) {
                                IRemoteViewsFactory asInterface =
                                        IRemoteViewsFactory.Stub.asInterface(iBinder);
                                try {
                                    if (asInterface != null) {
                                        asInterface.onDestroy(intent);
                                    } else {
                                        boolean z = CocktailBarManagerServiceImpl.DEBUG;
                                        Slog.d(
                                                "CocktailBarManagerServiceImpl",
                                                "destroyRemoteViewsService: IRemoteViewsFactory is"
                                                    + " null for "
                                                        + componentName);
                                    }
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                } catch (RuntimeException e2) {
                                    e2.printStackTrace();
                                }
                                CocktailBarManagerServiceImpl.this.mContext.unbindService(this);
                            }

                            @Override // android.content.ServiceConnection
                            public final void onServiceDisconnected(ComponentName componentName) {}
                        };
                int userId = UserHandle.getUserId(cocktail.getUid());
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mContext.bindServiceAsUser(
                            intent, serviceConnection, 1, new UserHandle(userId));
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    it.remove();
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
    }

    public final void deleteHost(String str) {
        HashMap hashMap = this.mHost;
        if (hashMap == null) {
            Slog.d("CocktailBarManagerServiceImpl", "unlinkHost: no registered host");
        } else {
            synchronized (hashMap) {
                try {
                    if (this.mHost.containsKey(str)) {
                        CocktailHostInfo cocktailHostInfo = (CocktailHostInfo) this.mHost.get(str);
                        if (cocktailHostInfo != null) {
                            cocktailHostInfo.unlinkBinder();
                        }
                        this.mHost.remove(str);
                    } else {
                        Slog.d(
                                "CocktailBarManagerServiceImpl",
                                "unlinkHost: no registered host for " + str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        HashMap hashMap2 = this.mHost;
        if (hashMap2 == null || hashMap2.isEmpty()) {
            synchronized (this.mCocktailArr) {
                removeAllUpdatedCocktailsLocked();
            }
        } else {
            StringBuilder sb = new StringBuilder("deleteHost: host remain ");
            HashMap hashMap3 = this.mHost;
            sb.append(hashMap3 == null ? "null" : Integer.valueOf(hashMap3.size()));
            Slog.d("CocktailBarManagerServiceImpl", sb.toString());
        }
    }

    public final void dump(PrintWriter printWriter) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println(
                    "Permission Denial: can't dump from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid());
            return;
        }
        synchronized (this.mCocktailArr) {
            try {
                int size = this.mCocktailArr.size();
                for (int i = 0; i < size; i++) {
                    dumpCocktail((Cocktail) this.mCocktailArr.valueAt(i), printWriter);
                }
            } finally {
            }
        }
        CocktailBarSettings cocktailBarSettings = this.mSettings;
        if (cocktailBarSettings != null) {
            printWriter.println(
                    "EnabledCocktails : " + cocktailBarSettings.getEnableCocktailIds().toString());
            printWriter.println("");
        }
        HashMap hashMap = this.mHost;
        if (hashMap != null) {
            synchronized (hashMap) {
                try {
                    StringBuffer stringBuffer = new StringBuffer("CocktailHost: ");
                    for (Map.Entry entry : this.mHost.entrySet()) {
                        stringBuffer.append((String) entry.getKey());
                        stringBuffer.append(" category=");
                        stringBuffer.append(
                                Integer.toHexString(
                                        ((CocktailHostInfo) entry.getValue()).category));
                    }
                    printWriter.println(stringBuffer.toString());
                    ServiceImplCommandLogger serviceImplCommandLogger = this.mCommandLogger;
                    if (serviceImplCommandLogger != null) {
                        printWriter.println(serviceImplCommandLogger.toString());
                    }
                } finally {
                }
            }
        }
        printWriter.println("");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0057, code lost:

       if (android.os.storage.StorageManager.isCeStorageUnlocked(r0) != false) goto L31;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean ensureStateLoadedLocked() {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.cocktailbar.CocktailBarManagerServiceImpl.ensureStateLoadedLocked():boolean");
    }

    public final ArrayList findCocktailsByPrivateModeLocked(String str) {
        ArrayList arrayList = new ArrayList();
        int size = this.mCocktailArr.size();
        for (int i = 0; i < size; i++) {
            Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(i);
            String str2 = cocktail.getProviderInfo().privateMode;
            if (str2 != null && str2.equals(str)) {
                arrayList.add(cocktail);
            }
        }
        return arrayList;
    }

    public final int getUidForPackage(String str) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        try {
            packageInfo = this.mPm.getPackageInfo(str, 0L, this.mUserId);
        } catch (RemoteException unused) {
            packageInfo = null;
        }
        if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
            throw new PackageManager.NameNotFoundException();
        }
        return applicationInfo.uid;
    }

    public final void incrementCocktailServiceRefCount(
            int i, Intent.FilterComparison filterComparison) {
        HashSet hashSet;
        if (this.mRemoteViewsServicesCocktails.containsKey(filterComparison)) {
            hashSet = (HashSet) this.mRemoteViewsServicesCocktails.get(filterComparison);
        } else {
            HashSet hashSet2 = new HashSet();
            this.mRemoteViewsServicesCocktails.put(filterComparison, hashSet2);
            hashSet = hashSet2;
        }
        hashSet.add(Integer.valueOf(i));
    }

    public final boolean isProfileWithUnlockedParent(int i) {
        UserInfo profileParent;
        UserInfo userInfo = this.mUserManager.getUserInfo(i);
        return userInfo != null
                && userInfo.isManagedProfile()
                && (profileParent = this.mUserManager.getProfileParent(i)) != null
                && this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle());
    }

    public final Cocktail lookupCocktailLocked(int i, int i2, String str) {
        Cocktail cocktail;
        if (str != null
                && (cocktail = (Cocktail) this.mCocktailArr.get(i)) != null
                && cocktail.getUid() == i2
                && TextUtils.equals(str, getPackageNameFromCocktail(cocktail))) {
            return cocktail;
        }
        return null;
    }

    public final Cocktail lookupProviderLocked(ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        int size = this.mCocktailArr.size();
        for (int i = 0; i < size; i++) {
            Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(i);
            if (componentName.equals(
                    cocktail.getProvider() != null ? cocktail.getProvider() : null)) {
                return cocktail;
            }
        }
        return null;
    }

    public final void notifyCocktailViewDataChangedInstanceLocked(Cocktail cocktail, int i) {
        HashMap hashMap = this.mHost;
        if (hashMap != null && !hashMap.isEmpty()) {
            try {
                synchronized (this.mHost) {
                    try {
                        for (Map.Entry entry : this.mHost.entrySet()) {
                            this.mCommandLogger.recordHostCommand(
                                    cocktail.getCocktailId(),
                                    ((CocktailHostInfo) entry.getValue()).mPackageName,
                                    "notifyCocktailViewDataChangedInstanceLocked id=");
                            ((CocktailHostInfo) entry.getValue())
                                    .callbackHost.viewDataChanged(
                                            cocktail.getCocktailId(), i, this.mUserId);
                        }
                    } finally {
                    }
                }
            } catch (RemoteException unused) {
            }
        }
        if (this.mHost == null) {
            for (Map.Entry entry2 : this.mRemoteViewsServicesCocktails.entrySet()) {
                if (((HashSet) entry2.getValue())
                        .contains(Integer.valueOf(cocktail.getCocktailId()))) {
                    Intent intent = ((Intent.FilterComparison) entry2.getKey()).getIntent();
                    ServiceConnection serviceConnection = new ServiceConnection() { // from class:
                                // com.android.server.cocktailbar.CocktailBarManagerServiceImpl.3
                                @Override // android.content.ServiceConnection
                                public final void onServiceConnected(
                                        ComponentName componentName, IBinder iBinder) {
                                    IRemoteViewsFactory asInterface =
                                            IRemoteViewsFactory.Stub.asInterface(iBinder);
                                    try {
                                        if (asInterface != null) {
                                            asInterface.onDataSetChangedAsync();
                                        } else {
                                            boolean z = CocktailBarManagerServiceImpl.DEBUG;
                                            Slog.d(
                                                    "CocktailBarManagerServiceImpl",
                                                    "notifyCocktailViewDataChangedInstanceLocked:"
                                                        + " IRemoteViewsFactory is null for "
                                                            + componentName);
                                        }
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    } catch (RuntimeException e2) {
                                        e2.printStackTrace();
                                    }
                                    CocktailBarManagerServiceImpl.this.mContext.unbindService(this);
                                }

                                @Override // android.content.ServiceConnection
                                public final void onServiceDisconnected(
                                        ComponentName componentName) {}
                            };
                    int userId = UserHandle.getUserId(cocktail.getUid());
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mContext.bindServiceAsUser(
                                intent, serviceConnection, 1, new UserHandle(userId));
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBroadcastReceived(android.content.Intent r11) {
        /*
            Method dump skipped, instructions count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.cocktailbar.CocktailBarManagerServiceImpl.onBroadcastReceived(android.content.Intent):void");
    }

    public final void onConfigurationChanged() {
        Locale locale;
        Locale locale2 = Locale.getDefault();
        int i = this.mContext.getResources().getConfiguration().densityDpi;
        if (locale2 == null
                || (locale = this.mLocale) == null
                || !locale2.equals(locale)
                || i != this.mDefaultDisplayDensity) {
            this.mLocale = locale2;
            this.mDefaultDisplayDensity = i;
            synchronized (this.mCocktailArr) {
                try {
                    if (!ensureStateLoadedLocked()) {
                        Slog.i(
                                "CocktailBarManagerServiceImpl",
                                "onConfigurationChanged : not loaded u=" + this.mUserId);
                        return;
                    }
                    for (int size = this.mCocktailArr.size() - 1; size >= 0; size--) {
                        Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(size);
                        ComponentName provider = cocktail.getProvider();
                        updateProvidersInfoForPackageLocked(
                                cocktail.getVersion(),
                                provider != null ? provider.getPackageName() : null);
                    }
                    BackgroundThread.getHandler().post(this.mSaveStateRunnable);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void onPackageAdded(String str) {
        synchronized (this.mCocktailArr) {
            try {
                if (!ensureStateLoadedLocked()) {
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "onPackageAdded : not loaded u = "
                                    + this.mUserId
                                    + " pkgName = "
                                    + str);
                    return;
                }
                this.mConfig.getClass();
                boolean z = false;
                for (int i = 1; i <= this.mConfig.mVersion; i++) {
                    z |= addProvidersForPackageLocked(i, str);
                }
                BackgroundThread.getHandler().post(this.mSaveStateRunnable);
                if (z) {
                    CocktailBarSettings cocktailBarSettings = this.mSettings;
                    cocktailBarSettings.initCocktailMap(this.mCocktailArr);
                    cocktailBarSettings.updateEnabledCocktailList();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageChanged(String str) {
        synchronized (this.mCocktailArr) {
            try {
                if (!ensureStateLoadedLocked()) {
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "onPackageChanged : not loaded u=" + this.mUserId + " pkgName=" + str);
                    return;
                }
                clearCocktailInfoLocked(str);
                this.mConfig.getClass();
                boolean z = false;
                for (int i = 1; i <= this.mConfig.mVersion; i++) {
                    z |= updateProvidersForPackageLocked(i, str);
                }
                if (z) {
                    CocktailBarSettings cocktailBarSettings = this.mSettings;
                    cocktailBarSettings.initCocktailMap(this.mCocktailArr);
                    cocktailBarSettings.updateEnabledCocktailList();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageRemoved(String str) {
        synchronized (this.mCocktailArr) {
            try {
                if (!ensureStateLoadedLocked()) {
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "onPackageRemoved : not loaded u = "
                                    + this.mUserId
                                    + " pkgName = "
                                    + str);
                    return;
                }
                boolean removeProvidersForPackageLocked = removeProvidersForPackageLocked(str);
                BackgroundThread.getHandler().post(this.mSaveStateRunnable);
                if (removeProvidersForPackageLocked) {
                    CocktailBarSettings cocktailBarSettings = this.mSettings;
                    cocktailBarSettings.initCocktailMap(this.mCocktailArr);
                    cocktailBarSettings.updateEnabledCocktailList();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackagesSuspendChanged(String[] strArr, boolean z) {
        synchronized (this.mCocktailArr) {
            try {
                if (!ensureStateLoadedLocked()) {
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "onPackagesSuspended : "
                                    + z
                                    + "not loaded u="
                                    + this.mUserId
                                    + " pkgName="
                                    + Arrays.toString(strArr));
                    return;
                }
                HashMap hashMap = this.mHost;
                if (hashMap != null && !hashMap.isEmpty()) {
                    processPackageSuspended(strArr, z);
                    return;
                }
                Slog.i(
                        "CocktailBarManagerServiceImpl",
                        "onPackagesSuspended : " + z + " no active host");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean parseAndUpdateProviderInfoXml(
            Cocktail cocktail,
            ComponentName componentName,
            ResolveInfo resolveInfo,
            int i,
            int i2) {
        int next;
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        boolean z = false;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData =
                        activityInfo.loadXmlMetaData(
                                this.mContext.getPackageManager(),
                                "com.samsung.android.cocktail.provider");
                try {
                    if (loadXmlMetaData == null) {
                        Slog.w(
                                "CocktailBarManagerServiceImpl",
                                "No com.samsung.android.cocktail.provider meta-data for CocktailBar"
                                    + " provider '"
                                        + componentName
                                        + '\'');
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return false;
                    }
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!"cocktail-provider".equals(loadXmlMetaData.getName())) {
                        Slog.w(
                                "CocktailBarManagerServiceImpl",
                                "Meta-data does not start with cocktail-provider tag for"
                                    + " CocktailBar provider '"
                                        + componentName
                                        + '\'');
                        loadXmlMetaData.close();
                        return false;
                    }
                    CocktailProviderInfo create =
                            CocktailProviderInfo.create(
                                    this.mContext,
                                    resolveInfo,
                                    componentName,
                                    loadXmlMetaData,
                                    i,
                                    i2);
                    if (create != null) {
                        int i3 = this.mUserId;
                        int i4 = create.category;
                        if ((i4 == 32 || i4 == 128)
                                ? CocktailBarUtils$CocktailBarWhiteList.isSystemApplication(
                                        i3, create.provider.getPackageName())
                                : true) {
                            cocktail.setProviderInfo(create);
                            cocktail.setUid(activityInfo.applicationInfo.uid);
                            z = true;
                        }
                    }
                    loadXmlMetaData.close();
                    return z;
                } catch (Exception e) {
                    e = e;
                    xmlResourceParser = loadXmlMetaData;
                    Slog.w(
                            "CocktailBarManagerServiceImpl",
                            "XML parsing failed for CocktailBar provider '" + componentName + '\'',
                            e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final void processPackageSuspended(String[] strArr, boolean z) {
        for (String str : strArr) {
            for (int i = 0; i < this.mCocktailArr.size(); i++) {
                Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(i);
                if (TextUtils.equals(str, getPackageNameFromCocktail(cocktail))
                        && this.mCocktailPolicyManager.canUpdateCocktail(
                                cocktail, this.mSettings, this.mUserId, this.mModeManager)) {
                    synchronized (this.mHost) {
                        Iterator it = this.mHost.entrySet().iterator();
                        while (it.hasNext()) {
                            CocktailHostInfo cocktailHostInfo =
                                    (CocktailHostInfo) ((Map.Entry) it.next()).getValue();
                            try {
                                if ((cocktailHostInfo.category
                                                & cocktail.getProviderInfo().category)
                                        != 0) {
                                    CocktailBarHistory.getInstance()
                                            .recordPanelUpdateHistory(
                                                    cocktail.getCocktailId(),
                                                    "packageSuspended " + z);
                                    this.mCommandLogger.recordHostCommand(
                                            cocktail.getCocktailId(),
                                            cocktailHostInfo.mPackageName,
                                            "packageSuspended " + z + " id=");
                                    cocktail.setPackageSuspended(z);
                                    cocktailHostInfo.callbackHost.packageSuspendChanged(cocktail);
                                }
                            } catch (RemoteException unused) {
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
    }

    public final List queryIntentReceivers(int i, Intent intent) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (EMERGENCY_MODE_ENABLED && SemEmergencyManager.isEmergencyMode(this.mContext))
                    ? this.mPm
                            .queryIntentReceivers(
                                    intent,
                                    intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                                    640L,
                                    i)
                            .getList()
                    : this.mPm
                            .queryIntentReceivers(
                                    intent,
                                    intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                                    (isProfileWithUnlockedParent(i) ? 269222016 : 268435584) | 1024,
                                    i)
                            .getList();
        } catch (RemoteException unused) {
            return Collections.emptyList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x038f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:113:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01db A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x03ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:187:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x02af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:227:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0171 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:297:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00f8 A[Catch: all -> 0x003f, Exception -> 0x0043, IndexOutOfBoundsException -> 0x0047, IOException -> 0x004b, XmlPullParserException -> 0x004f, NullPointerException -> 0x0053, NumberFormatException -> 0x0057, TRY_LEAVE, TryCatch #13 {NumberFormatException -> 0x0057, blocks: (B:4:0x000f, B:5:0x001f, B:7:0x0026, B:9:0x0033, B:14:0x005b, B:16:0x0064, B:37:0x0078, B:23:0x00ad, B:26:0x00c4, B:28:0x00cc, B:30:0x00f8, B:18:0x008e, B:21:0x0097, B:35:0x009c), top: B:3:0x000f, outer: #14 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0107 A[LOOP:0: B:5:0x001f->B:42:0x0107, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x038c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0319 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v63 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readStateFromFileLocked(java.io.FileInputStream r18) {
        /*
            Method dump skipped, instructions count: 1017
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.cocktailbar.CocktailBarManagerServiceImpl.readStateFromFileLocked(java.io.FileInputStream):void");
    }

    public final void removeAllUpdatedCocktailsLocked() {
        int size = this.mCocktailArr.size();
        for (int i = 0; i < size; i++) {
            Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(i);
            if (this.mCocktailPolicyManager.isUpdatedCocktail(
                    cocktail.getCocktailId(), this.mUserId)) {
                removeCocktailLocked(cocktail);
            }
        }
    }

    public final void removeCocktailInHostLocked(int i) {
        HashMap hashMap = this.mHost;
        if (hashMap == null || hashMap.isEmpty()) {
            Slog.i("CocktailBarManagerServiceImpl", "removeCocktailInHostLocked : no active host");
            return;
        }
        synchronized (this.mHost) {
            Iterator it = this.mHost.entrySet().iterator();
            while (it.hasNext()) {
                CocktailHostInfo cocktailHostInfo =
                        (CocktailHostInfo) ((Map.Entry) it.next()).getValue();
                this.mCommandLogger.recordHostCommand(
                        i, cocktailHostInfo.mPackageName, "removeCocktailInHostLocked id=");
                try {
                    cocktailHostInfo.callbackHost.removeCocktail(i, this.mUserId);
                } catch (RemoteException unused) {
                    it.remove();
                }
            }
        }
    }

    public final void removeCocktailLocked(int i) {
        if (DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(
                    i, "removeCocktailLocked : cocktailId = ", "CocktailBarManagerServiceImpl");
        }
        Cocktail cocktail = (Cocktail) this.mCocktailArr.get(i);
        if (cocktail != null) {
            this.mCocktailPolicyManager.disableUpdatableCocktail(
                    cocktail.getCocktailId(), this.mUserId);
            decrementCocktailServiceRefCount(cocktail);
            cocktail.updateCocktailInfo((CocktailInfo) null);
            removeCocktailInHostLocked(i);
        }
    }

    public final void removeCocktailLocked(Cocktail cocktail) {
        long clearCallingIdentity;
        boolean z = DEBUG;
        if (z) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "removeCocktailLocked : cocktailId = " + cocktail.getCocktailId());
        }
        int cocktailId = cocktail.getCocktailId();
        CocktailPolicyManager cocktailPolicyManager = this.mCocktailPolicyManager;
        int i = this.mUserId;
        cocktailPolicyManager.disableUpdatableCocktail(cocktailId, i);
        decrementCocktailServiceRefCount(cocktail);
        cocktail.updateCocktailInfo((CocktailInfo) null);
        removeCocktailInHostLocked(cocktail.getCocktailId());
        EnabledPackageMap enabledPackageMap = this.mEnabledCocktailPackages;
        if (z) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "sendDisableIntentLocked : cocktailId = " + cocktail.getCocktailId());
        }
        if (cocktail.getProvider() == null) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "sendDisableIntentLocked : invalied provider info cocktailId = "
                            + cocktail.getCocktailId());
            return;
        }
        PendingIntent broadcast = cocktail.getBroadcast();
        if (broadcast != null) {
            this.mAlarmManager.cancel(broadcast);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                broadcast.cancel();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                cocktail.setBroadcast((PendingIntent) null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Intent m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        268435456, "com.samsung.android.cocktail.action.COCKTAIL_DISABLED");
        m.setComponent(cocktail.getProvider());
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(m, new UserHandle(i));
            if (cocktail.getState() != 2) {
                cocktail.setState(0);
            }
            String packageName = cocktail.getProvider().getPackageName();
            CocktailBarHistory.getInstance()
                    .recordSemCocktailProviderBr(
                            "sendDisableIntentLocked : PackageName - "
                                    + packageName
                                    + ", "
                                    + cocktail.dump());
            int enabledCount = enabledPackageMap.getEnabledCount(packageName);
            enabledPackageMap.removeEnabledProvider(cocktail.getProvider());
            int enabledCount2 = enabledPackageMap.getEnabledCount(packageName);
            if (enabledCount >= 1 && enabledCount2 == 0) {
                if (z) {
                    try {
                        Slog.i(
                                "CocktailBarManagerServiceImpl",
                                "sendDisableIntentLocked : removePowerSaveWhitelistApp cocktailId ="
                                    + " "
                                        + cocktail.getCocktailId()
                                        + packageName);
                    } catch (RemoteException | UnsupportedOperationException e) {
                        Slog.d(
                                "CocktailBarManagerServiceImpl",
                                "sendDisableIntentLocked: fail to remove pm white list "
                                        + packageName);
                        e.printStackTrace();
                    }
                }
                CocktailBarHistory.getInstance()
                        .recordPowerWhitelistHistory(
                                cocktail.getCocktailId(),
                                "sendDisableIntentLocked removePowerSaveWhitelistApp");
                this.mLocalDeviceIdleController.removePowerSaveWhitelistApp(packageName);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean removeProviderLocked(Cocktail cocktail) {
        removeCocktailLocked(cocktail.getCocktailId());
        if (cocktail.getProviderInfo().category == 512) {
            this.mCocktailPolicyManager.establishPolicy(cocktail, 3);
        }
        if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                && SemEmergencyManager.isEmergencyMode(this.mContext)) {
            return false;
        }
        this.mEnabledCocktailPackages.removeEnabledProvider(cocktail.getProvider());
        this.mCocktailArr.remove(cocktail.getCocktailId());
        return true;
    }

    public final boolean removeProvidersForPackageLocked(String str) {
        if (DEBUG) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "removeProvidersForPackageLocked : pkgName = " + str);
        }
        boolean z = false;
        boolean z2 = false;
        for (int size = this.mCocktailArr.size() - 1; size >= 0; size--) {
            Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(size);
            if (TextUtils.equals(str, getPackageNameFromCocktail(cocktail))) {
                z = removeProviderLocked(cocktail);
                if (cocktail.getProviderInfo().category == 4) {
                    z2 = true;
                }
            }
        }
        if (z) {
            if (z2) {
                updateToolLauncher();
            }
            Intent intent =
                    new Intent(
                            "com.samsung.android.app.cocktailbarservice.action.COCKTAIL_BAR_COCKTAIL_UNINSTALLED");
            intent.addFlags(268435456);
            intent.setPackage(KnoxCustomManagerService.LAUNCHER_PACKAGE);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mUserId));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z;
    }

    public final boolean requestToDisableCocktailLocked(Cocktail cocktail) {
        int i;
        if (cocktail == null) {
            return false;
        }
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        if (providerInfo != null && ((i = providerInfo.category) == 4 || i == 32 || i == 128)) {
            if (!this.mCocktailPolicyManager.isUpdatedCocktail(
                    cocktail.getCocktailId(), this.mUserId)) {
                return false;
            }
        }
        removeCocktailLocked(cocktail);
        return true;
    }

    public final boolean requestToUpdateCocktailLocked(Cocktail cocktail) {
        int i;
        if (cocktail == null) {
            return false;
        }
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        if (providerInfo != null && ((i = providerInfo.category) == 4 || i == 32 || i == 128)) {
            this.mCocktailPolicyManager.enableUpdatableCocktail(
                    cocktail.getCocktailId(), this.mUserId);
        }
        sendEnableAndUpdateBroadcastLocked(cocktail);
        return true;
    }

    public final AtomicFile savedStateFile() {
        int i = this.mUserId;
        File userSystemDirectory = Environment.getUserSystemDirectory(i);
        File file = new File(Environment.getUserSystemDirectory(i), "cocktails.xml");
        if (!file.exists() && i == 0) {
            if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
                Slog.w("CocktailBarManagerServiceImpl", "savedStateFile Failed mkdirs");
            }
            if (!new File("/data/system/cocktails.xml").renameTo(file)) {
                Slog.w(
                        "CocktailBarManagerServiceImpl",
                        "savedStateFile Failed rename to setting file.");
            }
        }
        return new AtomicFile(file);
    }

    public final void sendCocktailChangedVisibilityIntentLocked(Cocktail cocktail, int i) {
        if (DEBUG) {
            Slog.i("CocktailBarManagerServiceImpl", "sendCocktailChangedVisibilityIntentLocked");
        }
        Intent intent =
                new Intent("com.samsung.android.cocktail.action.COCKTAIL_VISIBILITY_CHANGED");
        intent.putExtra("cocktailId", cocktail.getCocktailId());
        intent.putExtra("cocktailVisibility", i);
        intent.addFlags(268435456);
        intent.setComponent(cocktail.getProvider());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(this.mUserId));
            CocktailBarHistory cocktailBarHistory = CocktailBarHistory.getInstance();
            StringBuilder sb =
                    new StringBuilder("sendCocktailChangedVisibilityIntentLocked : PackageName - ");
            sb.append(
                    cocktail.getProvider() != null ? cocktail.getProvider().getClassName() : null);
            sb.append(", ");
            sb.append(cocktail.dump());
            cocktailBarHistory.recordSemCocktailProviderBr(sb.toString());
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendEnableAndUpdateBroadcastLocked(Cocktail cocktail) {
        String str;
        String str2;
        long clearCallingIdentity;
        String str3;
        boolean ensureStateLoadedLocked = ensureStateLoadedLocked();
        int i = this.mUserId;
        if (!ensureStateLoadedLocked) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "sendEnableAndUpdateBroadcastLocked : not loaded u="
                            + i
                            + " cocktail="
                            + cocktail);
            return;
        }
        if (cocktail != null) {
            if (cocktail.getState() == 2) {
                Slog.i(
                        "CocktailBarManagerServiceImpl",
                        "sendEnableAndUpdateBroadcastLocked : cocktail("
                                + cocktail.getCocktailId()
                                + ") is disabled");
                return;
            }
            EnabledPackageMap enabledPackageMap = this.mEnabledCocktailPackages;
            boolean z = DEBUG;
            if (z) {
                Slog.i(
                        "CocktailBarManagerServiceImpl",
                        "sendEnableIntentLocked : cocktailId = " + cocktail.getCocktailId());
            }
            if (cocktail.getState() != 0) {
                Slog.i(
                        "CocktailBarManagerServiceImpl",
                        "sendEnableIntentLocked : cocktail("
                                + cocktail.getCocktailId()
                                + ") has state("
                                + cocktail.getState()
                                + ")");
            } else if (cocktail.getProvider() == null) {
                Slog.i(
                        "CocktailBarManagerServiceImpl",
                        "sendEnableIntentLocked : invalid provider info cocktailId = "
                                + cocktail.getCocktailId());
            } else {
                if (z) {
                    Slog.i("CocktailBarManagerServiceImpl", "registerForBroadcastsLocked");
                }
                CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
                if (providerInfo == null || providerInfo.updatePeriodMillis <= 0) {
                    str = "CocktailBarManagerServiceImpl";
                    str2 = "sendEnableIntentLocked: fail to add power save whitelist ";
                } else {
                    boolean z2 = cocktail.getBroadcast() != null;
                    Intent intent = new Intent(cocktail.getUpdateIntentName());
                    intent.putExtra("cocktailIds", new int[] {cocktail.getCocktailId()});
                    intent.setComponent(providerInfo.provider);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        str2 = "sendEnableIntentLocked: fail to add power save whitelist ";
                        str = "CocktailBarManagerServiceImpl";
                        PendingIntent broadcastAsUser =
                                PendingIntent.getBroadcastAsUser(
                                        this.mContext, 1, intent, 201326592, new UserHandle(i));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (!z2) {
                            cocktail.setBroadcast(broadcastAsUser);
                            long j = providerInfo.updatePeriodMillis;
                            long j2 = MIN_UPDATE_PERIOD;
                            long j3 = j < j2 ? j2 : j;
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                this.mAlarmManager.setInexactRepeating(
                                        2, SystemClock.elapsedRealtime() + j3, j3, broadcastAsUser);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } finally {
                            }
                        }
                    } finally {
                    }
                }
                Intent m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                268435456, "com.samsung.android.cocktail.action.COCKTAIL_ENABLED");
                m.setComponent(cocktail.getProvider());
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mContext.sendBroadcastAsUser(m, new UserHandle(i));
                    cocktail.setState(1);
                    String packageName = cocktail.getProvider().getPackageName();
                    CocktailBarHistory.getInstance()
                            .recordSemCocktailProviderBr(
                                    "sendEnableIntentLocked : PackageName - "
                                            + packageName
                                            + ", "
                                            + cocktail.dump());
                    if (enabledPackageMap.getEnabledCount(packageName) == 0) {
                        enabledPackageMap.putEnabledProvider(cocktail.getProvider());
                        if (z) {
                            try {
                                str3 = str;
                                try {
                                    Slog.i(
                                            str3,
                                            "sendEnableIntentLocked : addPowerSaveWhitelistApp"
                                                + " cocktailId = "
                                                    + cocktail.getCocktailId()
                                                    + packageName);
                                } catch (RemoteException e) {
                                    e = e;
                                    Slog.d(str3, str2 + packageName);
                                    e.printStackTrace();
                                    sendUpdateIntentLocked(
                                            cocktail, cocktail.getUpdateIntentName(), true);
                                }
                            } catch (RemoteException e2) {
                                e = e2;
                                str3 = str;
                                Slog.d(str3, str2 + packageName);
                                e.printStackTrace();
                                sendUpdateIntentLocked(
                                        cocktail, cocktail.getUpdateIntentName(), true);
                            }
                        } else {
                            str3 = str;
                        }
                        CocktailBarHistory.getInstance()
                                .recordPowerWhitelistHistory(
                                        cocktail.getCocktailId(),
                                        "sendEnableIntentLocked addPowerSaveWhitelistApp");
                        this.mLocalDeviceIdleController.addPowerSaveWhitelistApp(packageName);
                    } else {
                        enabledPackageMap.putEnabledProvider(cocktail.getProvider());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            sendUpdateIntentLocked(cocktail, cocktail.getUpdateIntentName(), true);
        }
    }

    public final void sendInitialBroadcasts() {
        synchronized (this.mCocktailArr) {
            try {
                int size = this.mCocktailArr.size();
                for (int i = 0; i < size; i++) {
                    checkCocktailAttributeLocked(
                            (Cocktail) this.mCocktailArr.valueAt(i), this.mHostCategory);
                }
                sendInitialBroadcastsLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendInitialBroadcastsLocked() {
        if (!ensureStateLoadedLocked()) {
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("sendInitialBroadcastsLocked : not loaded u="),
                    this.mUserId,
                    "CocktailBarManagerServiceImpl");
            return;
        }
        ArrayList enableCocktailIds = this.mSettings.getEnableCocktailIds();
        ArrayList arrayList = new ArrayList();
        Iterator it = enableCocktailIds.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            Cocktail cocktail = (Cocktail) this.mCocktailArr.get(intValue);
            if (cocktail == null || cocktail.getState() != 0 || cocktail.getProvider() == null) {
                StringBuffer stringBuffer =
                        new StringBuffer("sendInitialBroadcastsLocked : invalid cocktail ");
                stringBuffer.append(intValue);
                stringBuffer.append(" c=");
                stringBuffer.append(cocktail);
                if (cocktail != null) {
                    stringBuffer.append(" state=");
                    stringBuffer.append(cocktail.getState());
                    stringBuffer.append(" p=");
                    stringBuffer.append(cocktail.getProvider());
                }
                Slog.d("CocktailBarManagerServiceImpl", stringBuffer.toString());
            } else {
                String packageName = cocktail.getProvider().getPackageName();
                EnabledPackageMap enabledPackageMap = this.mEnabledCocktailPackages;
                if (enabledPackageMap.getEnabledCount(packageName) == 0) {
                    enabledPackageMap.putEnabledProvider(cocktail.getProvider());
                    arrayList.add(cocktail.getProvider().getPackageName());
                    try {
                        if (DEBUG) {
                            Slog.i(
                                    "CocktailBarManagerServiceImpl",
                                    "sendInitialBroadcastsLocked : addPowerSaveWhitelistApp"
                                        + " cocktailId = "
                                            + intValue
                                            + packageName);
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            CocktailBarHistory.getInstance()
                                    .recordPowerWhitelistHistory(
                                            cocktail.getCocktailId(),
                                            "sendInitialBroadcastsLocked addPowerSaveWhitelistApp");
                            this.mLocalDeviceIdleController.addPowerSaveWhitelistApp(packageName);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } catch (RemoteException e) {
                        Slog.d(
                                "CocktailBarManagerServiceImpl",
                                "sendInitialBroadcastsLocked: fail to add pm white list "
                                        + packageName);
                        e.printStackTrace();
                    }
                } else {
                    enabledPackageMap.putEnabledProvider(cocktail.getProvider());
                }
            }
            sendEnableAndUpdateBroadcastLocked(cocktail);
        }
    }

    public final void sendUpdateIntentLocked(Cocktail cocktail, String str, boolean z) {
        int i = this.mUserId;
        if (cocktail.getState() == 2) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "sendUpdateIntentLocked : cocktail("
                            + cocktail.getCocktailId()
                            + ") is disabled");
            return;
        }
        CocktailPolicyManager cocktailPolicyManager = this.mCocktailPolicyManager;
        if (!z) {
            CocktailBarSettings cocktailBarSettings = this.mSettings;
            cocktailPolicyManager.getClass();
            if (!cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId())) {
                return;
            }
        }
        if (DEBUG) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "sendUpdateIntentLocked : cocktailId = " + cocktail.getCocktailId());
        }
        Intent m = BatteryService$$ExternalSyntheticOutline0.m(268435456, str);
        m.putExtra("cocktailIds", new int[] {cocktail.getCocktailId()});
        m.setComponent(cocktail.getProvider());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(m, new UserHandle(i));
            CocktailBarHistory cocktailBarHistory = CocktailBarHistory.getInstance();
            StringBuilder sb = new StringBuilder("sendUpdateIntentLocked : PackageName - ");
            sb.append(
                    cocktail.getProvider() != null ? cocktail.getProvider().getClassName() : null);
            sb.append(", ");
            sb.append(cocktail.dump());
            cocktailBarHistory.recordSemCocktailProviderBr(sb.toString());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            cocktailPolicyManager.enableUpdatableCocktail(cocktail.getCocktailId(), i);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setCocktailHostCallbacks(HashMap hashMap, HashMap hashMap2, boolean z) {
        if (this.mHost == null) {
            this.mHost = new HashMap();
        }
        synchronized (this.mHost) {
            try {
                Iterator it = this.mHost.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String str = (String) entry.getKey();
                    ICocktailHost iCocktailHost = (ICocktailHost) hashMap.get(entry.getKey());
                    if (iCocktailHost != null) {
                        CocktailHostInfo cocktailHostInfo = (CocktailHostInfo) this.mHost.get(str);
                        if (cocktailHostInfo != null
                                && !cocktailHostInfo.token.equals(iCocktailHost.asBinder())) {
                            cocktailHostInfo.unlinkBinder();
                            this.mHost.put(
                                    str,
                                    new CocktailHostInfo(
                                            str,
                                            iCocktailHost,
                                            ((Integer) hashMap2.get(str)).intValue()));
                        }
                    } else {
                        CocktailHostInfo cocktailHostInfo2 = (CocktailHostInfo) this.mHost.get(str);
                        if (cocktailHostInfo2 != null) {
                            cocktailHostInfo2.unlinkBinder();
                        }
                        it.remove();
                    }
                }
                for (Map.Entry entry2 : hashMap.entrySet()) {
                    if (this.mHost.get(entry2.getKey()) == null) {
                        this.mHost.put(
                                (String) entry2.getKey(),
                                new CocktailHostInfo(
                                        (String) entry2.getKey(),
                                        (ICocktailHost) entry2.getValue(),
                                        ((Integer) hashMap2.get(entry2.getKey())).intValue()));
                    }
                }
                boolean z2 = false;
                this.mHostCategory = 0;
                for (Map.Entry entry3 : this.mHost.entrySet()) {
                    this.mHostCategory =
                            ((CocktailHostInfo) entry3.getValue()).category | this.mHostCategory;
                }
                if (this.mInitialzed && !z) {
                    z2 = true;
                }
                this.mInitialzed = z2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setModeLocked(int i, int i2, String str) {
        if (i == 1) {
            if (ensureStateLoadedLocked()) {
                sendInitialBroadcastsLocked();
                return;
            }
            return;
        }
        int i3 = this.mUserId;
        CocktailPolicyManager cocktailPolicyManager = this.mCocktailPolicyManager;
        if (i2 == 2) {
            removeAllUpdatedCocktailsLocked();
        } else {
            ArrayList arrayList = new ArrayList();
            int size = this.mCocktailArr.size();
            for (int i4 = 0; i4 < size; i4++) {
                Cocktail cocktail = (Cocktail) this.mCocktailArr.valueAt(i4);
                if ((cocktail.getProviderInfo().category & 4) != 0) {
                    arrayList.add(cocktail);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Cocktail cocktail2 = (Cocktail) it.next();
                if (cocktailPolicyManager.isUpdatedCocktail(cocktail2.getCocktailId(), i3)) {
                    removeCocktailLocked(cocktail2);
                }
            }
        }
        Iterator it2 = findCocktailsByPrivateModeLocked(str).iterator();
        while (it2.hasNext()) {
            Cocktail cocktail3 = (Cocktail) it2.next();
            cocktailPolicyManager.enableUpdatableCocktail(cocktail3.getCocktailId(), i3);
            sendEnableAndUpdateBroadcastLocked(cocktail3);
        }
    }

    public final void systemDestroy() {
        CocktailBarSettingsObserver cocktailBarSettingsObserver = this.mCocktailSettingsObserver;
        CocktailBarManagerServiceImpl.this
                .mContext
                .getContentResolver()
                .unregisterContentObserver(cocktailBarSettingsObserver);
        this.mCocktailSettingsObserver = null;
        synchronized (this.mCocktailArr) {
            removeAllUpdatedCocktailsLocked();
        }
        HashMap hashMap = this.mHost;
        if (hashMap == null) {
            Slog.d("CocktailBarManagerServiceImpl", "unlinkAllHost: no registered host");
        } else {
            synchronized (hashMap) {
                try {
                    if (this.mHost.isEmpty()) {
                        Slog.d(
                                "CocktailBarManagerServiceImpl",
                                "unlinkAllHost: no registered host");
                    } else {
                        Iterator it = this.mHost.entrySet().iterator();
                        while (it.hasNext()) {
                            ((CocktailHostInfo) ((Map.Entry) it.next()).getValue()).unlinkBinder();
                        }
                        this.mHost = null;
                    }
                } finally {
                }
            }
        }
        synchronized (this.mCocktailArr) {
            this.mCocktailArr.clear();
            this.mNextCocktailId = this.mUserId << 16;
        }
    }

    public final void updateFromSettingsLocked() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        CocktailBarSettings cocktailBarSettings = this.mSettings;
        ArrayList arrayList3 = cocktailBarSettings.mEnabledCocktailListCache;
        ArrayList arrayList4 = new ArrayList();
        String enabledCocktailsStr = cocktailBarSettings.getEnabledCocktailsStr();
        cocktailBarSettings.mEnabledCocktailsStrCache = enabledCocktailsStr;
        if (!TextUtils.isEmpty(enabledCocktailsStr)) {
            cocktailBarSettings.mEnabledCocktailsSplitter.setString(
                    cocktailBarSettings.mEnabledCocktailsStrCache);
            while (cocktailBarSettings.mEnabledCocktailsSplitter.hasNext()) {
                String next = cocktailBarSettings.mEnabledCocktailsSplitter.next();
                CocktailBarSettings.CocktailInfo cocktailInfo =
                        (CocktailBarSettings.CocktailInfo)
                                cocktailBarSettings.mCocktailMap.get(next);
                if (cocktailInfo != null) {
                    arrayList4.add(next);
                    if (!arrayList3.contains(next)) {
                        arrayList.add(Integer.valueOf(cocktailInfo.cocktailId));
                    }
                }
            }
        }
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            CocktailBarSettings.CocktailInfo cocktailInfo2 =
                    (CocktailBarSettings.CocktailInfo) cocktailBarSettings.mCocktailMap.get(str);
            if (cocktailInfo2 != null && !arrayList4.contains(str)) {
                arrayList2.add(Integer.valueOf(cocktailInfo2.cocktailId));
            }
        }
        cocktailBarSettings.mEnabledCocktailListCache = arrayList4;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            Cocktail cocktail = (Cocktail) this.mCocktailArr.get(((Integer) it2.next()).intValue());
            if (cocktail != null) {
                removeCocktailLocked(cocktail);
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            Cocktail cocktail2 =
                    (Cocktail) this.mCocktailArr.get(((Integer) it3.next()).intValue());
            if (cocktail2 != null) {
                sendEnableAndUpdateBroadcastLocked(cocktail2);
            }
        }
    }

    public final boolean updateProvidersForPackageLocked(int i, String str) {
        boolean z;
        int i2;
        String str2;
        boolean z2;
        int i3;
        int i4;
        Cocktail cocktail;
        String str3 = "updateProvidersForPackageLocked : ";
        boolean z3 = DEBUG;
        if (z3) {
            Slog.i("CocktailBarManagerServiceImpl", "updateProvidersForPackageLocked : " + str);
        }
        if (str == null) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "updateProvidersForPackageLocked : invalide packageName");
            return false;
        }
        HashSet hashSet = new HashSet();
        String updateIntentName = Cocktail.getUpdateIntentName(i);
        List queryIntentReceivers =
                queryIntentReceivers(
                        this.mUserId,
                        ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                updateIntentName, str));
        int size = queryIntentReceivers == null ? 0 : queryIntentReceivers.size();
        if (z3) {
            HermesService$3$$ExternalSyntheticOutline0.m(
                    size,
                    "updateProvidersForPackageLocked : queryIntentReceivers=",
                    "CocktailBarManagerServiceImpl");
        }
        int categoryIds = CocktailProviderInfo.getCategoryIds(this.mConfig.getCategoryFilter());
        boolean z4 = false;
        int i5 = 0;
        boolean z5 = false;
        while (i5 < size) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentReceivers.get(i5);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (z3) {
                z = z4;
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m(str3, str, " ai="),
                        activityInfo.packageName,
                        "CocktailBarManagerServiceImpl");
            } else {
                z = z4;
            }
            if ((activityInfo.applicationInfo.flags & 262144) == 0
                    && TextUtils.equals(str, activityInfo.packageName)) {
                int i6 = i5;
                ComponentName componentName =
                        new ComponentName(activityInfo.packageName, activityInfo.name);
                Cocktail lookupProviderLocked = lookupProviderLocked(componentName);
                if (lookupProviderLocked == null) {
                    Cocktail addProviderLocked = addProviderLocked(resolveInfo, categoryIds, i);
                    if (addProviderLocked != null) {
                        hashSet.add(activityInfo.name);
                        if (addProviderLocked.getProviderInfo().category == 4) {
                            z5 = true;
                        }
                        if (this.mSettings.isEnabledCocktail(addProviderLocked.getCocktailId())) {
                            sendEnableAndUpdateBroadcastLocked(addProviderLocked);
                        } else {
                            sendUpdateIntentLocked(addProviderLocked, updateIntentName, false);
                        }
                        i2 = categoryIds;
                        str2 = str3;
                        z2 = z3;
                        i3 = i6;
                        z4 = true;
                        i4 = size;
                        i5 = i3 + 1;
                        categoryIds = i2;
                        size = i4;
                        str3 = str2;
                        z3 = z2;
                    } else {
                        i2 = categoryIds;
                        str2 = str3;
                        z2 = z3;
                        i3 = i6;
                        i4 = size;
                    }
                } else if (lookupProviderLocked.getVersion() == i) {
                    str2 = str3;
                    z2 = z3;
                    i3 = i6;
                    i2 = categoryIds;
                    i4 = size;
                    parseAndUpdateProviderInfoXml(
                            lookupProviderLocked, componentName, resolveInfo, categoryIds, i);
                    hashSet.add(activityInfo.name);
                    if (lookupProviderLocked.getProviderInfo().category == 4) {
                        z5 = true;
                    } else if (lookupProviderLocked.getProviderInfo().category == 512) {
                        cocktail = lookupProviderLocked;
                        this.mCocktailPolicyManager.establishPolicy(cocktail, 2);
                        cocktail.setPackageUpdated(true);
                        sendUpdateIntentLocked(cocktail, updateIntentName, false);
                        z4 = true;
                        i5 = i3 + 1;
                        categoryIds = i2;
                        size = i4;
                        str3 = str2;
                        z3 = z2;
                    }
                    cocktail = lookupProviderLocked;
                    cocktail.setPackageUpdated(true);
                    sendUpdateIntentLocked(cocktail, updateIntentName, false);
                    z4 = true;
                    i5 = i3 + 1;
                    categoryIds = i2;
                    size = i4;
                    str3 = str2;
                    z3 = z2;
                } else {
                    i2 = categoryIds;
                    str2 = str3;
                    z2 = z3;
                    i3 = i6;
                    i4 = size;
                    Slog.i(
                            "CocktailBarManagerServiceImpl",
                            "updateProvidersForPackageLocked : can not get right cocktail");
                }
            } else {
                i3 = i5;
                i2 = categoryIds;
                i4 = size;
                str2 = str3;
                z2 = z3;
            }
            z4 = z;
            i5 = i3 + 1;
            categoryIds = i2;
            size = i4;
            str3 = str2;
            z3 = z2;
        }
        boolean z6 = z4;
        for (int size2 = this.mCocktailArr.size() - 1; size2 >= 0; size2--) {
            Cocktail cocktail2 = (Cocktail) this.mCocktailArr.valueAt(size2);
            if (cocktail2.getVersion() == i
                    && TextUtils.equals(str, getPackageNameFromCocktail(cocktail2))) {
                if (!hashSet.contains(
                        cocktail2.getProvider() != null
                                ? cocktail2.getProvider().getClassName()
                                : null)) {
                    boolean z7 = cocktail2.getProviderInfo().category == 4 ? true : z5;
                    z6 = removeProviderLocked(cocktail2);
                    z5 = z7;
                }
            }
        }
        if (z6) {
            if (z5) {
                updateToolLauncher();
            }
            if (SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                    && SemEmergencyManager.isEmergencyMode(this.mContext)) {
                return false;
            }
        }
        return z6;
    }

    public final void updateProvidersInfoForPackageLocked(int i, String str) {
        int i2;
        int i3;
        int i4;
        int i5 = i;
        String str2 = str;
        boolean z = DEBUG;
        if (z) {
            Slog.i(
                    "CocktailBarManagerServiceImpl",
                    "updateProvidersInfoForPackageLocked : " + str2 + " version=" + i5);
        }
        if (str2 == null) {
            HermesService$3$$ExternalSyntheticOutline0.m(
                    i5,
                    "updateProvidersInfoForPackageLocked invalid packageName version=",
                    "CocktailBarManagerServiceImpl");
            return;
        }
        HashSet hashSet = new HashSet();
        String updateIntentName = Cocktail.getUpdateIntentName(i);
        List queryIntentReceivers =
                queryIntentReceivers(
                        this.mUserId,
                        ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                updateIntentName, str2));
        int size = queryIntentReceivers == null ? 0 : queryIntentReceivers.size();
        int categoryIds = CocktailProviderInfo.getCategoryIds(this.mConfig.getCategoryFilter());
        boolean z2 = false;
        int i6 = 0;
        boolean z3 = false;
        while (i6 < size) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentReceivers.get(i6);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (z) {
                i2 = i6;
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "updateProvidersInfoForPackageLocked : ", str2, " ai="),
                        activityInfo.packageName,
                        "CocktailBarManagerServiceImpl");
            } else {
                i2 = i6;
            }
            if ((activityInfo.applicationInfo.flags & 262144) == 0
                    && TextUtils.equals(str2, activityInfo.packageName)) {
                i3 = size;
                ComponentName componentName =
                        new ComponentName(activityInfo.packageName, activityInfo.name);
                Cocktail lookupProviderLocked = lookupProviderLocked(componentName);
                if (lookupProviderLocked == null) {
                    Cocktail addProviderLocked = addProviderLocked(resolveInfo, categoryIds, i5);
                    if (addProviderLocked != null) {
                        hashSet.add(activityInfo.name);
                        if (addProviderLocked.getProviderInfo().category == 4) {
                            z3 = true;
                        }
                        if (this.mSettings.isEnabledCocktail(addProviderLocked.getCocktailId())) {
                            sendEnableAndUpdateBroadcastLocked(addProviderLocked);
                        } else {
                            sendUpdateIntentLocked(addProviderLocked, updateIntentName, false);
                        }
                        i4 = categoryIds;
                        z2 = true;
                    } else {
                        i4 = categoryIds;
                    }
                } else {
                    if (lookupProviderLocked.getVersion() == i5) {
                        i4 = categoryIds;
                        parseAndUpdateProviderInfoXml(
                                lookupProviderLocked, componentName, resolveInfo, categoryIds, i);
                        hashSet.add(activityInfo.name);
                        if (lookupProviderLocked.getProviderInfo().category == 4) {
                            z3 = true;
                        } else if (lookupProviderLocked.getProviderInfo().category == 512) {
                            this.mCocktailPolicyManager.establishPolicy(lookupProviderLocked, 2);
                        }
                        sendUpdateIntentLocked(lookupProviderLocked, updateIntentName, false);
                        z2 = true;
                    } else {
                        i4 = categoryIds;
                        Slog.i(
                                "CocktailBarManagerServiceImpl",
                                "updateProvidersInfoForPackageLocked : can not get right cocktail");
                    }
                    i6 = i2 + 1;
                    i5 = i;
                    str2 = str;
                    size = i3;
                    categoryIds = i4;
                }
            } else {
                i4 = categoryIds;
                i3 = size;
            }
            i6 = i2 + 1;
            i5 = i;
            str2 = str;
            size = i3;
            categoryIds = i4;
        }
        if (z2) {
            if (z3) {
                updateToolLauncher();
            }
            if (SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")) {
                SemEmergencyManager.isEmergencyMode(this.mContext);
            }
        }
    }

    public final void updateToolLauncher() {
        HashMap hashMap = this.mHost;
        if (hashMap == null || hashMap.isEmpty()) {
            Slog.i("CocktailBarManagerServiceImpl", "updateToolLauncher : no active host");
            return;
        }
        synchronized (this.mHost) {
            Iterator it = this.mHost.entrySet().iterator();
            while (it.hasNext()) {
                CocktailHostInfo cocktailHostInfo =
                        (CocktailHostInfo) ((Map.Entry) it.next()).getValue();
                this.mCommandLogger.recordHostCommand(
                        this.mUserId, cocktailHostInfo.mPackageName, "updateToolLauncher uid=");
                try {
                    cocktailHostInfo.callbackHost.updateToolLauncher(this.mUserId);
                } catch (RemoteException unused) {
                    it.remove();
                }
            }
        }
    }

    public final boolean writeStateToFileLocked(FileOutputStream fileOutputStream) {
        try {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(fileOutputStream, "utf-8");
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.startTag((String) null, "gs");
            int size = this.mCocktailArr.size();
            for (int i = 0; i < size; i++) {
                CocktailProviderInfo providerInfo =
                        ((Cocktail) this.mCocktailArr.valueAt(i)).getProviderInfo();
                if (providerInfo != null) {
                    fastXmlSerializer.startTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
                    fastXmlSerializer.attribute(
                            (String) null, "pkg", providerInfo.provider.getPackageName());
                    fastXmlSerializer.attribute(
                            (String) null, "cl", providerInfo.provider.getClassName());
                    fastXmlSerializer.endTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
                }
            }
            fastXmlSerializer.endTag((String) null, "gs");
            fastXmlSerializer.endDocument();
            return true;
        } catch (IOException e) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "Failed to write state: ", e, "CocktailBarManagerServiceImpl");
            return false;
        }
    }
}
