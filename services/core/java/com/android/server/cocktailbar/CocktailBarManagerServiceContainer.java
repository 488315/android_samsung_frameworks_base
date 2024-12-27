package com.android.server.cocktailbar;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.RemoteViews;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.cocktailbar.CocktailBarManagerServiceContainer;
import com.android.server.cocktailbar.CocktailBarManagerServiceImpl;
import com.android.server.cocktailbar.CocktailBarManagerServiceImpl.CocktailHostInfo;
import com.android.server.cocktailbar.mode.CocktailBarMode;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.mode.NormalMode;
import com.android.server.cocktailbar.mode.PrivateEmergencyMode;
import com.android.server.cocktailbar.mode.PrivateKnoxMode;
import com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy;
import com.android.server.cocktailbar.policy.cocktail.CocktailContextualPolicy;
import com.android.server.cocktailbar.policy.cocktail.CocktailNativePolicy;
import com.android.server.cocktailbar.policy.cocktail.CocktailNormalPolicy;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicyManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailWhisperPolicy;
import com.android.server.cocktailbar.policy.state.CocktailBarStatePolicyController;
import com.android.server.cocktailbar.policy.state.CocktailBarStatePolicyController.CocktailBarStateListenerInfo;
import com.android.server.cocktailbar.policy.state.OverlayCocktailBarStatePolicy;
import com.android.server.cocktailbar.policy.systemui.SystemUiVisibilityPolicyController;
import com.android.server.cocktailbar.policy.systemui.SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.android.server.cocktailbar.settings.CocktailOrderManager;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.android.server.cocktailbar.utils.CocktailBarHistory;
import com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailBarStateInfo;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.cocktailbar.ICocktailHost;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import libcore.io.IoUtils;

public final class CocktailBarManagerServiceContainer extends ICocktailBarService.Stub implements CocktailBarManagerServiceListener, CocktailPolicyManager.OnCocktailBarPolicyListener, CocktailBarUsageStateWatcher.OnCocktailBarWatcherListener, DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener {
    public static final boolean SUPPORT_EDGE_MUM;
    public final AppOpsManager mAppOpsManager;
    public final AnonymousClass1 mBroadcastReceiver;
    public final CocktailBarHandler mCocktailBarHandler;
    public final SparseArray mCocktailBarServices;
    public final CocktailOrderManager mCocktailOrderManager;
    public final CocktailPolicyManager mCocktailPolicyManager;
    public final Context mContext;
    public final AnonymousClass2 mLauncherAppsCallback;
    public final CocktailBarModeManager mModeManager;
    public SemPersonaManager mPersonalManager;
    public final SecurityPolicy mSecurityPolicy;
    public final CocktailBarStatePolicyController mStatePolicyController;
    public int mSystemBarAppearance;
    public final SystemUiVisibilityPolicyController mSystemUiVisibilityPolicyController;
    public AnonymousClass3 mTaskSystemBarsVisibilityListener;
    public final UserManager mUserManager;
    public CocktailBarUsageStateWatcher mWatcher;
    public Intent mIntent = null;
    public final HashMap mHost = new HashMap();
    public final HashMap mFilterCategory = new HashMap();
    public final SparseIntArray mLoadedUserIds = new SparseIntArray();
    public int mCurrentUserId = -10;

    public final class CocktailBarHandler extends Handler {
        public CocktailBarHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z = CocktailBarManagerServiceContainer.SUPPORT_EDGE_MUM;
            SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("handleMessage: entry what = "), message.what, "CocktailBarManagerServiceContainer");
            switch (message.what) {
                case 1:
                    CocktailBarManagerServiceContainer.m343$$Nest$mhandleChangeVisibleEdgeService(CocktailBarManagerServiceContainer.this, message.arg1 == 1);
                    return;
                case 2:
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = CocktailBarManagerServiceContainer.this;
                    String str = (String) message.obj;
                    AbsCocktailPolicy findCocktailPolicy = cocktailBarManagerServiceContainer.mCocktailPolicyManager.findCocktailPolicy();
                    if (findCocktailPolicy != null) {
                        findCocktailPolicy.changeResumePackage(str);
                        return;
                    }
                    return;
                case 3:
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer2 = CocktailBarManagerServiceContainer.this;
                    cocktailBarManagerServiceContainer2.getClass();
                    CocktailBarHistory.getInstance().recordServiceProcess("startCocktailBarServiceWithCurrentUser");
                    Intent intent = new Intent("android.intent.action.MAIN");
                    cocktailBarManagerServiceContainer2.mIntent = intent;
                    intent.setClassName(KnoxCustomManagerService.LAUNCHER_PACKAGE, "com.samsung.app.honeyspace.edge.CocktailBarService");
                    try {
                        cocktailBarManagerServiceContainer2.mContext.startServiceAsUser(cocktailBarManagerServiceContainer2.mIntent, new UserHandle(ActivityManager.getCurrentUser()));
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        CocktailBarHistory.getInstance().recordServiceProcess(e.toString());
                        return;
                    }
                case 4:
                    final CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer3 = CocktailBarManagerServiceContainer.this;
                    final int i = message.arg1;
                    synchronized (cocktailBarManagerServiceContainer3.mCocktailBarServices) {
                        try {
                            int size = cocktailBarManagerServiceContainer3.mCocktailBarServices.size();
                            CocktailBarHistory.getInstance().recordServiceProcess("onUnlockUserImpl CocktailBarServices - " + size + ", userId : " + i);
                            StringBuilder sb = new StringBuilder("onUnlockUser : CocktailBarServices=");
                            sb.append(size);
                            Slog.d("CocktailBarManagerServiceContainer", sb.toString());
                            for (int i2 = 0; i2 < size; i2++) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) cocktailBarManagerServiceContainer3.mCocktailBarServices.valueAt(i2);
                                if (cocktailBarManagerServiceImpl != null) {
                                    synchronized (cocktailBarManagerServiceImpl.mCocktailArr) {
                                        cocktailBarManagerServiceImpl.ensureStateLoadedLocked();
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                    if (cocktailBarManagerServiceContainer3.mWatcher == null) {
                        Context context = cocktailBarManagerServiceContainer3.mContext;
                        final CocktailBarUsageStateWatcher cocktailBarUsageStateWatcher = new CocktailBarUsageStateWatcher();
                        cocktailBarUsageStateWatcher.mPackageHideEdgeServiceList = new HashSet();
                        cocktailBarUsageStateWatcher.mLevel = 0;
                        cocktailBarUsageStateWatcher.mLock = new Object();
                        cocktailBarUsageStateWatcher.mComponentsToHideEdge = new ArrayList();
                        cocktailBarUsageStateWatcher.mContext = context;
                        cocktailBarUsageStateWatcher.mListener = cocktailBarManagerServiceContainer3;
                        cocktailBarUsageStateWatcher.mActivityManager = (ActivityManager) context.getSystemService("activity");
                        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(UsageStatsManager.class);
                        HashSet hashSet = CocktailBarConfig.getInstance(context).mPackageHideEdgeServiceList;
                        cocktailBarUsageStateWatcher.mPackageHideEdgeServiceList = hashSet;
                        String str2 = CocktailBarConfig.getInstance(context).mMetaDataHideEdgeService;
                        cocktailBarUsageStateWatcher.mMetaDataHideEdgeService = str2;
                        CocktailProviderInfo.getCategoryIds(CocktailBarConfig.getInstance(context).getCategoryFilter());
                        if (hashSet != null && hashSet.size() > 0) {
                            cocktailBarUsageStateWatcher.mLevel = 1;
                        }
                        if (str2 != null && str2.length() > 0) {
                            cocktailBarUsageStateWatcher.mLevel |= 2;
                        }
                        if (cocktailBarUsageStateWatcher.mLevel > 0 && cocktailBarUsageStateWatcher.mUsageStatsWatcher == null) {
                            IUsageStatsWatcher iUsageStatsWatcher = new IUsageStatsWatcher.Stub() { // from class: com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher.1
                                public final HashSet mHideEdgeServiceComponentCache = new HashSet();
                                public boolean mVisible = true;

                                public final void notePauseComponent(ComponentName componentName, Intent intent2, int i3, int i4) {
                                    synchronized (CocktailBarUsageStateWatcher.this.mLock) {
                                        try {
                                            if (componentName == null) {
                                                Slog.e("CocktailBarUsageStateWatcher", "pauseComponentName is null");
                                                return;
                                            }
                                            String flattenToShortString = componentName.flattenToShortString();
                                            if (componentName.getPackageName() == null) {
                                                Slog.e("CocktailBarUsageStateWatcher", "pausePackageName is null");
                                                return;
                                            }
                                            CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer4 = (CocktailBarManagerServiceContainer) CocktailBarUsageStateWatcher.this.mListener;
                                            cocktailBarManagerServiceContainer4.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer4.mCocktailBarHandler, 6, componentName));
                                            String str3 = flattenToShortString + ":" + i3;
                                            if (CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str3)) {
                                                Slog.d("CocktailBarUsageStateWatcher", "notePauseComponent: " + str3);
                                                CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.remove(str3);
                                            }
                                            if (!this.mVisible && CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.isEmpty()) {
                                                CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer5 = (CocktailBarManagerServiceContainer) CocktailBarUsageStateWatcher.this.mListener;
                                                cocktailBarManagerServiceContainer5.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer5.mCocktailBarHandler, 1, 1, 0));
                                                this.mVisible = true;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }

                                public final void noteResumeComponent(ComponentName componentName, Intent intent2, int i3, int i4) {
                                    boolean z2;
                                    boolean z3;
                                    List<ActivityManager.RecentTaskInfo> recentTasks;
                                    ActivityInfo activityInfo;
                                    synchronized (CocktailBarUsageStateWatcher.this.mLock) {
                                        try {
                                            if (componentName == null) {
                                                Slog.e("CocktailBarUsageStateWatcher", "resumeComponentName is null");
                                                return;
                                            }
                                            String packageName = componentName.getPackageName();
                                            if (packageName == null) {
                                                Slog.e("CocktailBarUsageStateWatcher", "resumePackageName is null");
                                                return;
                                            }
                                            String flattenToShortString = componentName.flattenToShortString();
                                            CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer4 = (CocktailBarManagerServiceContainer) CocktailBarUsageStateWatcher.this.mListener;
                                            cocktailBarManagerServiceContainer4.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer4.mCocktailBarHandler, 5, componentName));
                                            Intent intent3 = new Intent("android.intent.action.MAIN");
                                            intent3.addCategory("android.intent.category.HOME");
                                            PackageManager packageManager = CocktailBarUsageStateWatcher.this.mContext.getPackageManager();
                                            ResolveInfo resolveActivity = packageManager.resolveActivity(intent3, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                                            String str3 = (resolveActivity == null || (activityInfo = resolveActivity.activityInfo) == null) ? null : activityInfo.packageName;
                                            CocktailBarUsageStateWatcher cocktailBarUsageStateWatcher2 = CocktailBarUsageStateWatcher.this;
                                            if ((cocktailBarUsageStateWatcher2.mLevel & 1) != 0) {
                                                if (cocktailBarUsageStateWatcher2.mPackageHideEdgeServiceList.contains(packageName)) {
                                                    String str4 = flattenToShortString + ":" + i3;
                                                    if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str4)) {
                                                        Slog.d("CocktailBarUsageStateWatcher", "noteResumeComponent: " + str4);
                                                        CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str4);
                                                    }
                                                    z2 = false;
                                                } else {
                                                    z2 = true;
                                                }
                                                if (z2 && !packageName.equals(str3) && (recentTasks = CocktailBarUsageStateWatcher.this.mActivityManager.getRecentTasks(1, 1)) != null && recentTasks.size() > 0) {
                                                    ComponentName component = recentTasks.get(0).baseIntent.getComponent();
                                                    if (component == null) {
                                                        Slog.e("CocktailBarUsageStateWatcher", "noteResumeComponent top task ComponentName is NULL");
                                                    } else if (CocktailBarUsageStateWatcher.this.mPackageHideEdgeServiceList.contains(component.getPackageName())) {
                                                        String str5 = flattenToShortString + ":" + i3;
                                                        if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str5)) {
                                                            Slog.d("CocktailBarUsageStateWatcher", "noteResumeComponent: " + str5);
                                                            CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str5);
                                                        }
                                                        z2 = false;
                                                    }
                                                }
                                            } else {
                                                z2 = true;
                                            }
                                            if (z2 && (CocktailBarUsageStateWatcher.this.mLevel & 2) != 0) {
                                                if (this.mHideEdgeServiceComponentCache.contains(componentName.getClassName())) {
                                                    String str6 = flattenToShortString + ":" + i3;
                                                    if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str6)) {
                                                        Slog.d("CocktailBarUsageStateWatcher", "noteResumeComponent: " + str6);
                                                        CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str6);
                                                    }
                                                } else {
                                                    try {
                                                        Bundle bundle = packageManager.getActivityInfo(componentName, 128).metaData;
                                                        if (bundle != null ? bundle.getBoolean(CocktailBarUsageStateWatcher.this.mMetaDataHideEdgeService, false) : false) {
                                                            this.mHideEdgeServiceComponentCache.add(componentName.getClassName());
                                                            String str7 = flattenToShortString + ":" + i3;
                                                            if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str7)) {
                                                                Slog.d("CocktailBarUsageStateWatcher", "noteResumeComponent: " + str7);
                                                                CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str7);
                                                            }
                                                        }
                                                    } catch (PackageManager.NameNotFoundException e2) {
                                                        Slog.d("CocktailBarUsageStateWatcher", "noteResumeComponent: getActivityInfo not found. " + componentName);
                                                        e2.printStackTrace();
                                                    }
                                                }
                                                z3 = false;
                                                if (this.mVisible && !z3 && !CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.isEmpty()) {
                                                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer5 = (CocktailBarManagerServiceContainer) CocktailBarUsageStateWatcher.this.mListener;
                                                    cocktailBarManagerServiceContainer5.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer5.mCocktailBarHandler, 1, 0, 0));
                                                    this.mVisible = false;
                                                }
                                            }
                                            z3 = z2;
                                            if (this.mVisible) {
                                                CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer52 = (CocktailBarManagerServiceContainer) CocktailBarUsageStateWatcher.this.mListener;
                                                cocktailBarManagerServiceContainer52.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer52.mCocktailBarHandler, 1, 0, 0));
                                                this.mVisible = false;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }

                                public final void noteStopComponent(ComponentName componentName, Intent intent2, int i3, int i4) {
                                    synchronized (CocktailBarUsageStateWatcher.this.mLock) {
                                        try {
                                            if (componentName == null) {
                                                Slog.e("CocktailBarUsageStateWatcher", "stopComponentName is null");
                                                return;
                                            }
                                            String flattenToShortString = componentName.flattenToShortString();
                                            if (componentName.getPackageName() == null) {
                                                Slog.e("CocktailBarUsageStateWatcher", "stopPackageName is null");
                                                return;
                                            }
                                            String str3 = flattenToShortString + ":" + i3;
                                            if (CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str3)) {
                                                Slog.d("CocktailBarUsageStateWatcher", "noteStopComponent: " + str3);
                                                CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.remove(str3);
                                            }
                                            if (!this.mVisible && CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.isEmpty()) {
                                                CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer4 = (CocktailBarManagerServiceContainer) CocktailBarUsageStateWatcher.this.mListener;
                                                cocktailBarManagerServiceContainer4.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer4.mCocktailBarHandler, 1, 1, 0));
                                                this.mVisible = true;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            };
                            cocktailBarUsageStateWatcher.mUsageStatsWatcher = iUsageStatsWatcher;
                            if (usageStatsManager != null) {
                                try {
                                    usageStatsManager.registerUsageStatsWatcher(iUsageStatsWatcher);
                                } catch (Exception unused) {
                                }
                            }
                        }
                        cocktailBarManagerServiceContainer3.mWatcher = cocktailBarUsageStateWatcher;
                    }
                    cocktailBarManagerServiceContainer3.bootCompleted();
                    new Handler().postDelayed(new Runnable() { // from class: com.android.server.cocktailbar.CocktailBarManagerServiceContainer$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CocktailBarManagerServiceContainer.this.sendEdgeAppStartBr(i, 0);
                        }
                    }, 10000L);
                    for (final int i3 = 1; i3 <= 3; i3++) {
                        new Handler().postDelayed(new Runnable() { // from class: com.android.server.cocktailbar.CocktailBarManagerServiceContainer$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer4 = CocktailBarManagerServiceContainer.this;
                                int i4 = i;
                                int i5 = i3;
                                synchronized (cocktailBarManagerServiceContainer4.mHost) {
                                    try {
                                        if (cocktailBarManagerServiceContainer4.mHost.isEmpty()) {
                                            cocktailBarManagerServiceContainer4.sendEdgeAppStartBr(i4, i5);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }, (i3 * 60000) + 10000);
                    }
                    return;
                case 5:
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer4 = CocktailBarManagerServiceContainer.this;
                    ComponentName componentName = (ComponentName) message.obj;
                    if (!cocktailBarManagerServiceContainer4.enforceCocktailBarService() || CocktailBarManagerServiceContainer.isNotEdgeRunnableId(cocktailBarManagerServiceContainer4.mCurrentUserId)) {
                        return;
                    }
                    CocktailBarManagerServiceImpl implForUser = cocktailBarManagerServiceContainer4.getImplForUser(cocktailBarManagerServiceContainer4.mCurrentUserId);
                    HashMap hashMap = implForUser.mHost;
                    if (hashMap == null || hashMap.isEmpty()) {
                        Slog.i("CocktailBarManagerServiceImpl", "noteResumeComponent : no active host");
                        return;
                    }
                    synchronized (implForUser.mHost) {
                        Iterator it = implForUser.mHost.entrySet().iterator();
                        while (it.hasNext()) {
                            try {
                                ((CocktailBarManagerServiceImpl.CocktailHostInfo) ((Map.Entry) it.next()).getValue()).callbackHost.noteResumeComponent(componentName);
                            } catch (RemoteException unused2) {
                                it.remove();
                            }
                        }
                    }
                    return;
                case 6:
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer5 = CocktailBarManagerServiceContainer.this;
                    ComponentName componentName2 = (ComponentName) message.obj;
                    if (!cocktailBarManagerServiceContainer5.enforceCocktailBarService() || CocktailBarManagerServiceContainer.isNotEdgeRunnableId(cocktailBarManagerServiceContainer5.mCurrentUserId)) {
                        return;
                    }
                    CocktailBarManagerServiceImpl implForUser2 = cocktailBarManagerServiceContainer5.getImplForUser(cocktailBarManagerServiceContainer5.mCurrentUserId);
                    HashMap hashMap2 = implForUser2.mHost;
                    if (hashMap2 == null || hashMap2.isEmpty()) {
                        Slog.i("CocktailBarManagerServiceImpl", "notePauseComponent : no active host");
                        return;
                    }
                    synchronized (implForUser2.mHost) {
                        Iterator it2 = implForUser2.mHost.entrySet().iterator();
                        while (it2.hasNext()) {
                            try {
                                ((CocktailBarManagerServiceImpl.CocktailHostInfo) ((Map.Entry) it2.next()).getValue()).callbackHost.notePauseComponent(componentName2);
                            } catch (RemoteException unused3) {
                                it2.remove();
                            }
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public final class SecurityPolicy {
        public SecurityPolicy() {
        }

        public final void enforceCallFromPackage(String str) {
            CocktailBarManagerServiceContainer.this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
        }

        public final int[] getEnabledGroupProfileIds(int i) {
            int i2;
            CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = CocktailBarManagerServiceContainer.this;
            SecurityPolicy securityPolicy = cocktailBarManagerServiceContainer.mSecurityPolicy;
            securityPolicy.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UserInfo profileParent = CocktailBarManagerServiceContainer.this.mUserManager.getProfileParent(i);
                if (profileParent != null) {
                    i2 = profileParent.getUserHandle().getIdentifier();
                } else {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    i2 = -10;
                }
                if (i2 != -10) {
                    i = i2;
                }
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List profiles = cocktailBarManagerServiceContainer.mUserManager.getProfiles(i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    int size = profiles.size();
                    int i3 = 0;
                    for (int i4 = 0; i4 < size; i4++) {
                        if (((UserInfo) profiles.get(i4)).isEnabled()) {
                            i3++;
                        }
                    }
                    int[] iArr = new int[i3];
                    int i5 = 0;
                    for (int i6 = 0; i6 < size; i6++) {
                        UserInfo userInfo = (UserInfo) profiles.get(i6);
                        if (userInfo.isEnabled()) {
                            iArr[i5] = userInfo.getUserHandle().getIdentifier();
                            i5++;
                        }
                    }
                    return iArr;
                } finally {
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mhandleChangeVisibleEdgeService, reason: not valid java name */
    public static void m343$$Nest$mhandleChangeVisibleEdgeService(CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer, boolean z) {
        if (!cocktailBarManagerServiceContainer.enforceCocktailBarService() || isNotEdgeRunnableId(cocktailBarManagerServiceContainer.mCurrentUserId)) {
            return;
        }
        CocktailBarManagerServiceImpl implForUser = cocktailBarManagerServiceContainer.getImplForUser(cocktailBarManagerServiceContainer.mCurrentUserId);
        HashMap hashMap = implForUser.mHost;
        if (hashMap == null || hashMap.isEmpty()) {
            Slog.i("CocktailBarManagerServiceImpl", "changeVisibleEdgeService : no active host");
            return;
        }
        synchronized (implForUser.mHost) {
            Iterator it = implForUser.mHost.entrySet().iterator();
            while (it.hasNext()) {
                CocktailBarManagerServiceImpl.CocktailHostInfo cocktailHostInfo = (CocktailBarManagerServiceImpl.CocktailHostInfo) ((Map.Entry) it.next()).getValue();
                implForUser.mCommandLogger.recordHostCommand(implForUser.mUserId, cocktailHostInfo.mPackageName, "changeVisibleEdgeService uid=");
                try {
                    cocktailHostInfo.callbackHost.changeVisibleEdgeService(z, implForUser.mUserId);
                } catch (RemoteException unused) {
                    it.remove();
                }
            }
        }
    }

    static {
        SUPPORT_EDGE_MUM = Build.VERSION.SEM_PLATFORM_INT >= 140500;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.cocktailbar.CocktailBarManagerServiceContainer$2] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.android.server.cocktailbar.CocktailBarManagerServiceContainer$1] */
    public CocktailBarManagerServiceContainer(Context context) {
        SystemUiVisibilityPolicyController systemUiVisibilityPolicyController;
        this.mContext = null;
        ?? r2 = new BroadcastReceiver() { // from class: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                int i = 0;
                if ("android.intent.action.CONFIGURATION_CHANGED".equals(action)) {
                    synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
                        try {
                            int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
                            while (i < size) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                                if (cocktailBarManagerServiceImpl != null) {
                                    cocktailBarManagerServiceImpl.onConfigurationChanged();
                                }
                                i++;
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if ("android.intent.action.USER_STARTED".equals(action)) {
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = CocktailBarManagerServiceContainer.this;
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    cocktailBarManagerServiceContainer.getClass();
                    Slog.i("CocktailBarManagerServiceContainer", "onUserStarted : userId = " + intExtra);
                    CocktailBarHistory.getInstance().recordServiceProcess("onUserStarted : userId = " + intExtra);
                    if (CocktailBarManagerServiceContainer.isNotEdgeRunnableId(intExtra)) {
                        return;
                    }
                    cocktailBarManagerServiceContainer.ensureGroupStateLoaded(intExtra);
                    return;
                }
                if ("android.intent.action.USER_STOPPED".equals(action)) {
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer2 = CocktailBarManagerServiceContainer.this;
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    cocktailBarManagerServiceContainer2.getClass();
                    Slog.i("CocktailBarManagerServiceContainer", "onUserStopped : userId = " + intExtra2);
                    CocktailBarHistory.getInstance().recordServiceProcess("onUserStopped : userId = " + intExtra2);
                    if (cocktailBarManagerServiceContainer2.enforceCocktailBarService()) {
                        synchronized (cocktailBarManagerServiceContainer2.mCocktailBarServices) {
                            try {
                                int indexOfKey = cocktailBarManagerServiceContainer2.mLoadedUserIds.indexOfKey(intExtra2);
                                if (indexOfKey >= 0) {
                                    cocktailBarManagerServiceContainer2.mLoadedUserIds.removeAt(indexOfKey);
                                }
                                int indexOfKey2 = cocktailBarManagerServiceContainer2.mCocktailBarServices.indexOfKey(intExtra2);
                                if (indexOfKey2 >= 0) {
                                    ((CocktailBarManagerServiceImpl) cocktailBarManagerServiceContainer2.mCocktailBarServices.valueAt(indexOfKey2)).systemDestroy();
                                    cocktailBarManagerServiceContainer2.mCocktailBarServices.removeAt(indexOfKey2);
                                    if (intExtra2 >= 100 && cocktailBarManagerServiceContainer2.mModeManager.mCocktailBarModeId == 2) {
                                        if (cocktailBarManagerServiceContainer2.mPersonalManager == null) {
                                            cocktailBarManagerServiceContainer2.mPersonalManager = (SemPersonaManager) cocktailBarManagerServiceContainer2.mContext.getSystemService("persona");
                                        }
                                        CocktailBarModeManager cocktailBarModeManager = cocktailBarManagerServiceContainer2.mModeManager;
                                        cocktailBarModeManager.onSetModeForcely(cocktailBarModeManager.getCocktailBarMode(1));
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                }
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer3 = CocktailBarManagerServiceContainer.this;
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    cocktailBarManagerServiceContainer3.getClass();
                    Slog.i("CocktailBarManagerServiceContainer", "onUserSwitched : userId = " + intExtra3);
                    CocktailBarHistory.getInstance().recordServiceProcess("onUserSwitched : userId = " + intExtra3);
                    if (cocktailBarManagerServiceContainer3.enforceCocktailBarService() && !CocktailBarManagerServiceContainer.isNotEdgeRunnableId(intExtra3) && CocktailBarManagerServiceContainer.SUPPORT_EDGE_MUM) {
                        synchronized (cocktailBarManagerServiceContainer3.mCocktailBarServices) {
                            try {
                                int size2 = cocktailBarManagerServiceContainer3.mCocktailBarServices.size();
                                while (i < size2) {
                                    CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 = (CocktailBarManagerServiceImpl) cocktailBarManagerServiceContainer3.mCocktailBarServices.valueAt(i);
                                    if (cocktailBarManagerServiceImpl2 != null) {
                                        cocktailBarManagerServiceImpl2.mNextUserId = intExtra3;
                                    }
                                    i++;
                                }
                            } finally {
                            }
                        }
                        if (cocktailBarManagerServiceContainer3.mCurrentUserId != -10) {
                            Intent intent2 = new Intent("com.samsung.android.cocktailbar.intent.action.USER_SWITCHED");
                            intent2.addFlags(285212704);
                            intent2.putExtra("user_id", intExtra3);
                            cocktailBarManagerServiceContainer3.mContext.sendBroadcastAsUser(intent2, new UserHandle(cocktailBarManagerServiceContainer3.mCurrentUserId));
                        }
                        cocktailBarManagerServiceContainer3.mCurrentUserId = intExtra3;
                        return;
                    }
                    return;
                }
                if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
                    if (intent.getData() != null && intent.getData().toString().contains(KnoxCustomManagerService.LAUNCHER_PACKAGE)) {
                        CocktailBarManagerServiceContainer.this.mCocktailBarHandler.sendEmptyMessage(3);
                        return;
                    }
                    return;
                }
                boolean z = CocktailBarManagerServiceContainer.SUPPORT_EDGE_MUM;
                BinaryTransparencyService$$ExternalSyntheticOutline0.m("onReceive : ", action, "CocktailBarManagerServiceContainer");
                CocktailBarModeManager cocktailBarModeManager2 = CocktailBarManagerServiceContainer.this.mModeManager;
                Iterator it = cocktailBarModeManager2.mPrivateModes.iterator();
                while (it.hasNext()) {
                    CocktailBarMode cocktailBarMode = (CocktailBarMode) it.next();
                    if (cocktailBarMode.getRegistrationType() == 1) {
                        int onBroadcastReceived = cocktailBarMode.onBroadcastReceived(intent);
                        if (onBroadcastReceived == 1) {
                            return;
                        }
                        if (onBroadcastReceived == 2) {
                            cocktailBarModeManager2.onSetMode(UserHandle.getCallingUserId(), cocktailBarMode);
                            return;
                        }
                        if (onBroadcastReceived == 3) {
                            int callingUserId = UserHandle.getCallingUserId();
                            if (cocktailBarModeManager2.mCocktailBarModeId == cocktailBarMode.getModeId()) {
                                CocktailBarModeManager cocktailBarModeManager3 = ((CocktailBarManagerServiceContainer) cocktailBarModeManager2.mListener).mModeManager;
                                cocktailBarModeManager3.onSetMode(callingUserId, cocktailBarModeManager3.getCocktailBarMode(1));
                                cocktailBarModeManager2.mCocktailBarModeId = 1;
                                if (CocktailBarModeManager.DEBUG) {
                                    StringBuffer stringBuffer = new StringBuffer("CocktailBarModeManageronResetMode: ");
                                    stringBuffer.append(cocktailBarMode.getModeName());
                                    Log.d("CocktailBarModeManager", stringBuffer.toString());
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    }
                }
                synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
                    try {
                        int sendingUserId = getSendingUserId();
                        if (sendingUserId == -1) {
                            int size3 = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
                            while (i < size3) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl3 = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                                if (cocktailBarManagerServiceImpl3 != null) {
                                    cocktailBarManagerServiceImpl3.onBroadcastReceived(intent);
                                }
                                i++;
                            }
                        } else {
                            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl4 = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(sendingUserId);
                            if (cocktailBarManagerServiceImpl4 != null) {
                                cocktailBarManagerServiceImpl4.onBroadcastReceived(intent);
                            }
                        }
                    } finally {
                    }
                }
            }
        };
        this.mBroadcastReceiver = r2;
        this.mLauncherAppsCallback = new LauncherApps.Callback() { // from class: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.2
            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackageAdded(String str, UserHandle userHandle) {
                synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
                    try {
                        int semGetIdentifier = userHandle.semGetIdentifier();
                        if (semGetIdentifier == -1) {
                            int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
                            for (int i = 0; i < size; i++) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                                if (cocktailBarManagerServiceImpl != null) {
                                    cocktailBarManagerServiceImpl.onPackageAdded(str);
                                }
                            }
                        } else {
                            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(semGetIdentifier);
                            if (cocktailBarManagerServiceImpl2 != null) {
                                cocktailBarManagerServiceImpl2.onPackageAdded(str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackageChanged(String str, UserHandle userHandle) {
                synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
                    try {
                        int semGetIdentifier = userHandle.semGetIdentifier();
                        if (semGetIdentifier == -1) {
                            int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
                            for (int i = 0; i < size; i++) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                                if (cocktailBarManagerServiceImpl != null) {
                                    cocktailBarManagerServiceImpl.onPackageChanged(str);
                                }
                            }
                        } else {
                            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(semGetIdentifier);
                            if (cocktailBarManagerServiceImpl2 != null) {
                                cocktailBarManagerServiceImpl2.onPackageChanged(str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackageRemoved(String str, UserHandle userHandle) {
                synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
                    try {
                        int semGetIdentifier = userHandle.semGetIdentifier();
                        if (semGetIdentifier == -1) {
                            int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
                            for (int i = 0; i < size; i++) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                                if (cocktailBarManagerServiceImpl != null) {
                                    cocktailBarManagerServiceImpl.onPackageRemoved(str);
                                }
                            }
                        } else {
                            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(semGetIdentifier);
                            if (cocktailBarManagerServiceImpl2 != null) {
                                cocktailBarManagerServiceImpl2.onPackageRemoved(str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z) {
            }

            public final void onPackagesSuspendChanged(String[] strArr, UserHandle userHandle, boolean z) {
                synchronized (CocktailBarManagerServiceContainer.this.mCocktailBarServices) {
                    try {
                        int semGetIdentifier = userHandle.semGetIdentifier();
                        if (semGetIdentifier == -1) {
                            int size = CocktailBarManagerServiceContainer.this.mCocktailBarServices.size();
                            for (int i = 0; i < size; i++) {
                                CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.valueAt(i);
                                if (cocktailBarManagerServiceImpl != null) {
                                    cocktailBarManagerServiceImpl.onPackagesSuspendChanged(strArr, z);
                                }
                            }
                        } else {
                            CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl2 = (CocktailBarManagerServiceImpl) CocktailBarManagerServiceContainer.this.mCocktailBarServices.get(semGetIdentifier);
                            if (cocktailBarManagerServiceImpl2 != null) {
                                cocktailBarManagerServiceImpl2.onPackagesSuspendChanged(strArr, z);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackagesSuspended(String[] strArr, UserHandle userHandle) {
                onPackagesSuspendChanged(strArr, userHandle, true);
            }

            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z) {
            }

            @Override // android.content.pm.LauncherApps.Callback
            public final void onPackagesUnsuspended(String[] strArr, UserHandle userHandle) {
                onPackagesSuspendChanged(strArr, userHandle, false);
            }
        };
        this.mContext = context;
        this.mCocktailBarHandler = new CocktailBarHandler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("CocktailBarManagerServiceContainer").getLooper());
        CocktailBarModeManager cocktailBarModeManager = new CocktailBarModeManager();
        cocktailBarModeManager.mCocktailBarModeId = 1;
        cocktailBarModeManager.mPrivateModes = new ArrayList();
        cocktailBarModeManager.mPrivateModeMap = new HashMap();
        cocktailBarModeManager.mContext = context;
        cocktailBarModeManager.mListener = this;
        cocktailBarModeManager.mNormalMode = new NormalMode();
        PrivateKnoxMode privateKnoxMode = new PrivateKnoxMode(context, 2);
        cocktailBarModeManager.mKnoxMode = privateKnoxMode;
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")) {
            PrivateEmergencyMode privateEmergencyMode = new PrivateEmergencyMode(context, 65537);
            context.registerReceiverAsUser(r2, UserHandle.ALL, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), null, null);
            cocktailBarModeManager.setupPrivateMode(privateEmergencyMode);
        }
        cocktailBarModeManager.setupPrivateMode(privateKnoxMode);
        this.mModeManager = cocktailBarModeManager;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mSecurityPolicy = new SecurityPolicy();
        this.mStatePolicyController = CocktailBarStatePolicyController.getInstance();
        boolean z = SystemUiVisibilityPolicyController.DEBUG;
        synchronized (SystemUiVisibilityPolicyController.class) {
            try {
                if (SystemUiVisibilityPolicyController.mInstance == null) {
                    SystemUiVisibilityPolicyController.mInstance = new SystemUiVisibilityPolicyController();
                }
                systemUiVisibilityPolicyController = SystemUiVisibilityPolicyController.mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mSystemUiVisibilityPolicyController = systemUiVisibilityPolicyController;
        CocktailPolicyManager cocktailPolicyManager = new CocktailPolicyManager();
        cocktailPolicyManager.mCocktailPolicys = new ArrayList();
        cocktailPolicyManager.mUpdatableCocktailMap = new SparseArray();
        cocktailPolicyManager.mListener = this;
        int categoryIds = CocktailProviderInfo.getCategoryIds(CocktailBarConfig.getInstance(context).getCategoryFilter());
        if (categoryIds == 0 || (65536 & categoryIds) != 0) {
            cocktailPolicyManager.mCocktailPolicys.add(new CocktailContextualPolicy(cocktailPolicyManager));
        }
        if ((categoryIds & 512) != 0) {
            CocktailWhisperPolicy cocktailWhisperPolicy = new CocktailWhisperPolicy(cocktailPolicyManager);
            cocktailWhisperPolicy.mWhisperInfoList = new SparseArray();
            cocktailWhisperPolicy.mCurrentWhisperInfo = new SparseArray();
            cocktailWhisperPolicy.mLock = new Object();
            cocktailWhisperPolicy.mContext = context;
            cocktailPolicyManager.mCocktailPolicys.add(cocktailWhisperPolicy);
        }
        if (categoryIds == 0 || (categoryIds & FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED) != 0) {
            cocktailPolicyManager.mCocktailPolicys.add(new CocktailNativePolicy(cocktailPolicyManager));
        }
        cocktailPolicyManager.mCocktailPolicys.add(new CocktailNormalPolicy(cocktailPolicyManager));
        this.mCocktailPolicyManager = cocktailPolicyManager;
        this.mCocktailBarServices = new SparseArray(5);
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null) {
            devicePolicyManagerInternal.addOnCrossProfileWidgetProvidersChangeListener(this);
        }
        CocktailOrderManager cocktailOrderManager = new CocktailOrderManager();
        cocktailOrderManager.mLock = new Object();
        cocktailOrderManager.mContext = context;
        this.mCocktailOrderManager = cocktailOrderManager;
    }

    public static boolean isNotEdgeRunnableId(int i) {
        return SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isKnoxId(i) || SemDualAppManager.isDualAppId(i) || i == 77;
    }

    public final void activateCocktailBar() {
        if (Binder.getCallingUid() != 1000) {
            Slog.w("CocktailBarManagerServiceContainer", "activateCocktailBar : Calling uid is not system uid (" + Binder.getCallingUid() + ")");
            return;
        }
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        if (CocktailBarStatePolicyController.DEBUG) {
            cocktailBarStatePolicyController.getClass();
            Slog.i("CocktailBarStatePolicyController", "activateCocktailBar");
        }
        cocktailBarStatePolicyController.mPolicy.updateActivate(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x02e1 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:13:0x005e, B:15:0x0064, B:16:0x0083, B:20:0x0094, B:22:0x0098, B:24:0x009e, B:26:0x00b0, B:28:0x00bc, B:29:0x00d7, B:75:0x0110, B:76:0x0112, B:86:0x0133, B:88:0x0144, B:90:0x014c, B:91:0x0153, B:93:0x0156, B:95:0x0160, B:99:0x0163, B:100:0x0175, B:105:0x019e, B:106:0x019f, B:107:0x01a2, B:38:0x01ab, B:39:0x01ad, B:49:0x01ce, B:51:0x01df, B:53:0x01e7, B:54:0x01ee, B:56:0x01f1, B:58:0x01fb, B:62:0x01fe, B:63:0x0210, B:68:0x0239, B:69:0x023a, B:70:0x0377, B:150:0x024c, B:151:0x024e, B:161:0x026f, B:163:0x0280, B:165:0x0288, B:166:0x028f, B:168:0x0292, B:170:0x029a, B:174:0x029f, B:175:0x02b1, B:180:0x02da, B:181:0x02db, B:182:0x02de, B:113:0x02e1, B:114:0x02e3, B:124:0x0305, B:126:0x0316, B:128:0x031e, B:129:0x0325, B:131:0x0328, B:137:0x0337, B:133:0x0331, B:141:0x034c, B:146:0x0376, B:192:0x037a, B:193:0x038b, B:194:0x038c, B:195:0x0393, B:196:0x0394, B:197:0x03a5, B:198:0x03a6, B:199:0x03ae, B:41:0x01ae, B:43:0x01b8, B:45:0x01be, B:47:0x01cb, B:153:0x024f, B:155:0x0259, B:157:0x025f, B:159:0x026c, B:78:0x0113, B:80:0x011d, B:82:0x0123, B:84:0x0130, B:116:0x02e4, B:118:0x02ee, B:120:0x02f4, B:122:0x0302), top: B:12:0x005e, inners: #5, #7, #8, #9, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x024c A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:13:0x005e, B:15:0x0064, B:16:0x0083, B:20:0x0094, B:22:0x0098, B:24:0x009e, B:26:0x00b0, B:28:0x00bc, B:29:0x00d7, B:75:0x0110, B:76:0x0112, B:86:0x0133, B:88:0x0144, B:90:0x014c, B:91:0x0153, B:93:0x0156, B:95:0x0160, B:99:0x0163, B:100:0x0175, B:105:0x019e, B:106:0x019f, B:107:0x01a2, B:38:0x01ab, B:39:0x01ad, B:49:0x01ce, B:51:0x01df, B:53:0x01e7, B:54:0x01ee, B:56:0x01f1, B:58:0x01fb, B:62:0x01fe, B:63:0x0210, B:68:0x0239, B:69:0x023a, B:70:0x0377, B:150:0x024c, B:151:0x024e, B:161:0x026f, B:163:0x0280, B:165:0x0288, B:166:0x028f, B:168:0x0292, B:170:0x029a, B:174:0x029f, B:175:0x02b1, B:180:0x02da, B:181:0x02db, B:182:0x02de, B:113:0x02e1, B:114:0x02e3, B:124:0x0305, B:126:0x0316, B:128:0x031e, B:129:0x0325, B:131:0x0328, B:137:0x0337, B:133:0x0331, B:141:0x034c, B:146:0x0376, B:192:0x037a, B:193:0x038b, B:194:0x038c, B:195:0x0393, B:196:0x0394, B:197:0x03a5, B:198:0x03a6, B:199:0x03ae, B:41:0x01ae, B:43:0x01b8, B:45:0x01be, B:47:0x01cb, B:153:0x024f, B:155:0x0259, B:157:0x025f, B:159:0x026c, B:78:0x0113, B:80:0x011d, B:82:0x0123, B:84:0x0130, B:116:0x02e4, B:118:0x02ee, B:120:0x02f4, B:122:0x0302), top: B:12:0x005e, inners: #5, #7, #8, #9, #10 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean bindRemoteViewsService(java.lang.String r21, int r22, android.content.Intent r23, android.app.IApplicationThread r24, android.os.IBinder r25, android.app.IServiceConnection r26, int r27) {
        /*
            Method dump skipped, instructions count: 946
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.bindRemoteViewsService(java.lang.String, int, android.content.Intent, android.app.IApplicationThread, android.os.IBinder, android.app.IServiceConnection, int):boolean");
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.cocktailbar.CocktailBarManagerServiceContainer$3] */
    public final void bootCompleted() {
        CocktailBarHistory.getInstance().recordServiceProcess("bootCompleted");
        int userId = SUPPORT_EDGE_MUM ? this.mContext.getUserId() : 0;
        synchronized (this.mCocktailBarServices) {
            try {
                if (((CocktailBarManagerServiceImpl) this.mCocktailBarServices.get(userId)) == null) {
                    createCocktailBarManagerServiceImplLocked(userId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        IntentFilter m = BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.CONFIGURATION_CHANGED");
        Context context = this.mContext;
        AnonymousClass1 anonymousClass1 = this.mBroadcastReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass1, userHandle, m, null, null);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(KnoxCustomManagerService.LAUNCHER_PACKAGE, 0);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, userHandle, intentFilter, null, null);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, userHandle, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE", "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"), null, null);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, userHandle, GmsAlarmManager$$ExternalSyntheticOutline0.m("android.intent.action.USER_STARTED", "android.intent.action.USER_STOPPED", "android.intent.action.USER_SWITCHED"), null, null);
        if (this.mTaskSystemBarsVisibilityListener == null) {
            this.mTaskSystemBarsVisibilityListener = new WindowManagerInternal.TaskSystemBarsListener() { // from class: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.3
                @Override // com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener
                public final void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2) {
                    SystemUiVisibilityPolicyController systemUiVisibilityPolicyController = CocktailBarManagerServiceContainer.this.mSystemUiVisibilityPolicyController;
                    int i2 = systemUiVisibilityPolicyController.mSystemUiVisibility;
                    systemUiVisibilityPolicyController.setState(4, 4, z);
                    if (i2 != systemUiVisibilityPolicyController.mSystemUiVisibility) {
                        systemUiVisibilityPolicyController.systemUiVisibilityChanged();
                    }
                }
            };
        }
        WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        if (windowManagerInternal != null) {
            windowManagerInternal.registerTaskSystemBarsListener(this.mTaskSystemBarsVisibilityListener);
        }
    }

    public final void checkPermission() {
        if (this.mContext.checkCallingOrSelfPermission("com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL") == 0) {
            return;
        }
        throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL");
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x008b, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0187, code lost:
    
        throw r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void closeCocktail(java.lang.String r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 393
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.closeCocktail(java.lang.String, int, int):void");
    }

    public final void createCocktailBarManagerServiceImplLocked(int i) {
        if (isNotEdgeRunnableId(i)) {
            return;
        }
        CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl = new CocktailBarManagerServiceImpl(this.mContext, this.mCocktailBarHandler, this.mModeManager, this.mCocktailPolicyManager, i);
        HashMap hashMap = this.mHost;
        if (hashMap != null && !hashMap.isEmpty()) {
            cocktailBarManagerServiceImpl.setCocktailHostCallbacks(this.mHost, this.mFilterCategory, false);
        }
        this.mCocktailBarServices.append(i, cocktailBarManagerServiceImpl);
        this.mLoadedUserIds.put(i, i);
    }

    public final void deactivateCocktailBar() {
        if (Binder.getCallingUid() != 1000) {
            Slog.w("CocktailBarManagerServiceContainer", "deactivateCocktailBar : Calling uid is not system uid (" + Binder.getCallingUid() + ")");
            return;
        }
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        if (CocktailBarStatePolicyController.DEBUG) {
            cocktailBarStatePolicyController.getClass();
            Slog.i("CocktailBarStatePolicyController", "deactivateCocktailBar");
        }
        cocktailBarStatePolicyController.mPolicy.updateActivate(false);
    }

    public final void disableCocktail(String str, ComponentName componentName) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (componentName == null) {
            throw new IllegalArgumentException("invalid provider");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.i("CocktailBarManagerServiceImpl", "disableCocktail : not loaded" + implForUser.mUserId);
                    return;
                }
                Cocktail lookupProviderLocked = implForUser.lookupProviderLocked(componentName);
                if (lookupProviderLocked != null) {
                    ArrayList enableCocktailIds = implForUser.mSettings.getEnableCocktailIds();
                    ArrayList arrayList = new ArrayList();
                    int size = enableCocktailIds.size();
                    boolean z = false;
                    for (int i = 0; i < size; i++) {
                        Cocktail cocktail = (Cocktail) implForUser.mCocktailArr.get(((Integer) enableCocktailIds.get(i)).intValue());
                        if (cocktail != null && cocktail.getProvider() != null) {
                            if (cocktail.getCocktailId() == lookupProviderLocked.getCocktailId()) {
                                z = true;
                            } else {
                                arrayList.add(cocktail.getProvider().getClassName());
                            }
                        }
                    }
                    if (z) {
                        implForUser.removeCocktailLocked(lookupProviderLocked);
                        implForUser.mHandler.post(new CocktailBarManagerServiceImpl.AnonymousClass4(implForUser, arrayList, 0));
                    }
                }
            } finally {
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "CocktailBarManagerServiceContainer");
        synchronized (this.mCocktailBarServices) {
            try {
                PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, " ");
                printWriter.println(CocktailBarHistory.getInstance().toString());
                int size = this.mCocktailBarServices.size();
                for (int i = 0; i < size; i++) {
                    printWriter.println("User: " + this.mCocktailBarServices.keyAt(i));
                    indentingPrintWriter.increaseIndent();
                    ((CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(i)).dump(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                    printWriter.println("\n");
                }
                printWriter.println(this.mCocktailOrderManager.dump());
                printWriter.println(this.mStatePolicyController.dump());
                printWriter.println(CocktailBarConfig.getInstance(this.mContext).dump());
                CocktailBarUsageStateWatcher cocktailBarUsageStateWatcher = this.mWatcher;
                if (cocktailBarUsageStateWatcher != null) {
                    printWriter.println(cocktailBarUsageStateWatcher.dump());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean enforceCocktailBarService() {
        synchronized (this.mCocktailBarServices) {
            try {
                return this.mCocktailBarServices.size() >= 1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void ensureGroupStateLoaded(int i) {
        if (isNotEdgeRunnableId(i)) {
            return;
        }
        synchronized (this.mCocktailBarServices) {
            try {
                int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
                int length = enabledGroupProfileIds.length;
                int i2 = 0;
                for (int i3 = 0; i3 < length; i3++) {
                    if (this.mLoadedUserIds.indexOfKey(enabledGroupProfileIds[i3]) >= 0) {
                        enabledGroupProfileIds[i3] = -1;
                    } else {
                        i2++;
                    }
                }
                if (i2 <= 0) {
                    return;
                }
                for (int i4 : enabledGroupProfileIds) {
                    if (i4 != -1) {
                        createCocktailBarManagerServiceImplLocked(i4);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int[] getAllCocktailIds() {
        int callingUserId = UserHandle.getCallingUserId();
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
            return null;
        }
        ensureGroupStateLoaded(callingUserId);
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId);
        ArrayList arrayList = new ArrayList();
        for (int i : enabledGroupProfileIds) {
            CocktailBarManagerServiceImpl implForUser = getImplForUser(i);
            synchronized (implForUser.mCocktailArr) {
                try {
                    int size = implForUser.mCocktailArr.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.add(Integer.valueOf(((Cocktail) implForUser.mCocktailArr.valueAt(i2)).getCocktailId()));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        int size2 = arrayList.size();
        int[] iArr = new int[size2];
        for (int i3 = 0; i3 < size2; i3++) {
            iArr[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
        return iArr;
    }

    public final String getCategoryFilterStr() {
        return CocktailBarConfig.getInstance(this.mContext).mCategoryFilterStr;
    }

    public final boolean getCocktaiBarWakeUpState() {
        return false;
    }

    public final Cocktail getCocktail(int i) {
        Cocktail cocktail;
        int callingUserId = UserHandle.getCallingUserId();
        if (enforceCocktailBarService() && !isNotEdgeRunnableId(callingUserId)) {
            ensureGroupStateLoaded(callingUserId);
            for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId)) {
                CocktailBarManagerServiceImpl implForUser = getImplForUser(i2);
                synchronized (implForUser.mCocktailArr) {
                    cocktail = (Cocktail) implForUser.mCocktailArr.get(i);
                    if (cocktail == null) {
                        cocktail = null;
                    }
                }
                if (cocktail != null) {
                    return cocktail;
                }
            }
        }
        return null;
    }

    public final CocktailBarStateInfo getCocktailBarStateInfo() {
        return this.mStatePolicyController.mPolicy.mStateInfo;
    }

    public final int getCocktailBarVisibility() {
        return this.mStatePolicyController.mPolicy.mStateInfo.visibility;
    }

    public final int getCocktailId(String str, ComponentName componentName) {
        int i = 0;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return 0;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (componentName == null) {
            throw new IllegalArgumentException("invalid provider");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                Cocktail lookupProviderLocked = implForUser.lookupProviderLocked(componentName);
                if (lookupProviderLocked != null) {
                    i = lookupProviderLocked.getCocktailId();
                }
            } finally {
            }
        }
        return i;
    }

    public final int[] getCocktailIds(String str, ComponentName componentName) {
        int[] iArr;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return new int[0];
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (componentName == null) {
            throw new IllegalArgumentException("invalid provider");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                Cocktail lookupProviderLocked = implForUser.lookupProviderLocked(componentName);
                iArr = (lookupProviderLocked == null || Binder.getCallingUid() != lookupProviderLocked.getUid()) ? new int[0] : new int[]{lookupProviderLocked.getCocktailId()};
            } finally {
            }
        }
        return iArr;
    }

    public final int getConfigVersion() {
        return CocktailBarConfig.getInstance(this.mContext).mVersion;
    }

    public final int[] getEnabledCocktailIds() {
        FileInputStream fileInputStream;
        ArrayList enableCocktailIds;
        ComponentName provider;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return new int[0];
        }
        int callingUserId = UserHandle.getCallingUserId();
        ensureGroupStateLoaded(callingUserId);
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId);
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray = new SparseArray();
        int i = 0;
        while (true) {
            fileInputStream = null;
            if (i >= enabledGroupProfileIds.length) {
                break;
            }
            CocktailBarManagerServiceImpl implForUser = getImplForUser(enabledGroupProfileIds[i]);
            synchronized (implForUser.mCocktailArr) {
                try {
                    if (implForUser.ensureStateLoadedLocked()) {
                        enableCocktailIds = implForUser.mSettings.getEnableCocktailIds();
                    } else {
                        Slog.i("CocktailBarManagerServiceImpl", "getEnabledCocktailIds : not loaded" + implForUser.mUserId);
                        enableCocktailIds = new ArrayList();
                    }
                } finally {
                }
            }
            arrayList.addAll(enableCocktailIds);
            int size = enableCocktailIds.size();
            for (int i2 = 0; i2 < size; i2++) {
                CocktailBarManagerServiceImpl implForUser2 = getImplForUser(enabledGroupProfileIds[i]);
                Integer num = (Integer) arrayList.get(i2);
                synchronized (implForUser2.mCocktailArr) {
                    try {
                        Cocktail cocktail = (Cocktail) implForUser2.mCocktailArr.get(num.intValue());
                        provider = cocktail != null ? cocktail.getProvider() : null;
                    } finally {
                    }
                }
                if (provider != null) {
                    sparseArray.put(((Integer) enableCocktailIds.get(i2)).intValue(), provider.getClassName() + "_" + enabledGroupProfileIds[i]);
                }
            }
            i++;
        }
        CocktailOrderManager cocktailOrderManager = this.mCocktailOrderManager;
        ArrayList arrayList2 = cocktailOrderManager.mCocktailOrderedList;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            synchronized (cocktailOrderManager.mLock) {
                try {
                    try {
                        try {
                            fileInputStream = CocktailOrderManager.savedStateFile().openRead();
                            cocktailOrderManager.readStateFromFileLocked(fileInputStream);
                        } finally {
                            IoUtils.closeQuietly(fileInputStream);
                        }
                    } catch (FileNotFoundException e) {
                        Slog.w("CocktailOrderManager", "Failed to read state: " + e);
                    }
                    StringBuilder sb = new StringBuilder("getMatchedSortIds: loadedorder=");
                    ArrayList arrayList3 = cocktailOrderManager.mCocktailOrderedList;
                    String str = arrayList3;
                    if (arrayList3 != null) {
                        str = arrayList3.toString();
                    }
                    sb.append((Object) str);
                    Slog.d("CocktailOrderManager", sb.toString());
                } finally {
                }
            }
        }
        ArrayList arrayList4 = cocktailOrderManager.mCocktailOrderedList;
        if (arrayList4 != null && !arrayList4.isEmpty()) {
            ArrayList arrayList5 = cocktailOrderManager.mCocktailOrderedList;
            CocktailOrderManager.CocktailOrderComparator cocktailOrderComparator = new CocktailOrderManager.CocktailOrderComparator();
            synchronized (cocktailOrderManager.mLock) {
                try {
                    cocktailOrderComparator.mOrderInfoMap = new HashMap();
                    cocktailOrderComparator.mEnabledPanelInfoArray = sparseArray;
                    int size2 = arrayList5.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        CocktailOrderManager.CocktailOrderInfo cocktailOrderInfo = (CocktailOrderManager.CocktailOrderInfo) arrayList5.get(i3);
                        if (cocktailOrderInfo != null && cocktailOrderInfo.mComponentName != null) {
                            String str2 = (String) CocktailBarConfig.getInstance(cocktailOrderManager.mContext).mReplacedComponent.get(cocktailOrderInfo.mComponentName.getClassName());
                            cocktailOrderComparator.mOrderInfoMap.put(str2 != null ? str2 + "_" + cocktailOrderInfo.mUserId : cocktailOrderInfo.mComponentName.getClassName() + "_" + cocktailOrderInfo.mUserId, Integer.valueOf(cocktailOrderInfo.mOrder));
                        }
                    }
                } finally {
                }
            }
            Collections.sort(arrayList, cocktailOrderComparator);
        }
        int size3 = arrayList.size();
        int[] iArr = new int[size3];
        for (int i4 = 0; i4 < size3; i4++) {
            iArr[i4] = ((Integer) arrayList.get(i4)).intValue();
        }
        return iArr;
    }

    public final String getHideEdgeListStr() {
        HashSet hashSet = CocktailBarConfig.getInstance(this.mContext).mPackageHideEdgeServiceList;
        return hashSet != null ? hashSet.toString() : "";
    }

    public final CocktailBarManagerServiceImpl getImplForUser(int i) {
        CocktailBarManagerServiceImpl cocktailBarManagerServiceImpl;
        boolean z;
        synchronized (this.mCocktailBarServices) {
            try {
                cocktailBarManagerServiceImpl = (CocktailBarManagerServiceImpl) this.mCocktailBarServices.get(i);
                z = false;
                if (cocktailBarManagerServiceImpl == null) {
                    Slog.i("CocktailBarManagerServiceContainer", "Unable to find CocktailBarManagerService for user " + i + ", adding");
                    cocktailBarManagerServiceImpl = new CocktailBarManagerServiceImpl(this.mContext, this.mCocktailBarHandler, this.mModeManager, this.mCocktailPolicyManager, i);
                    this.mCocktailBarServices.append(i, cocktailBarManagerServiceImpl);
                    HashMap hashMap = this.mHost;
                    if (hashMap != null && !hashMap.isEmpty()) {
                        cocktailBarManagerServiceImpl.setCocktailHostCallbacks(this.mHost, this.mFilterCategory, false);
                    }
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z && this.mHost != null) {
            if (CocktailBarManagerServiceImpl.DEBUG) {
                Slog.i("CocktailBarManagerServiceImpl", "initialize");
            }
            synchronized (cocktailBarManagerServiceImpl.mCocktailArr) {
                cocktailBarManagerServiceImpl.ensureStateLoadedLocked();
            }
            CocktailBarModeManager cocktailBarModeManager = cocktailBarManagerServiceImpl.mModeManager;
            int i2 = cocktailBarModeManager.mCocktailBarModeId;
            Iterator it = cocktailBarModeManager.mPrivateModes.iterator();
            int i3 = i2;
            while (it.hasNext()) {
                i3 = ((CocktailBarMode) it.next()).renewMode(i3);
            }
            if (i2 != i3) {
                CocktailBarModeManager cocktailBarModeManager2 = cocktailBarManagerServiceImpl.mModeManager;
                cocktailBarModeManager2.onSetMode(cocktailBarManagerServiceImpl.mUserId, cocktailBarModeManager2.getCocktailBarMode(i3));
            } else if (i3 == 1) {
                cocktailBarManagerServiceImpl.sendInitialBroadcasts();
            }
            cocktailBarManagerServiceImpl.mInitialzed = true;
        }
        return cocktailBarManagerServiceImpl;
    }

    public final int getPreferWidth() {
        return CocktailBarConfig.getInstance(this.mContext).mPreferredWidth;
    }

    public final int getSystemBarAppearance() {
        return this.mSystemBarAppearance;
    }

    public final int getUserIdFromCocktailId(int i) {
        int i2 = i >> 16;
        synchronized (this.mCocktailBarServices) {
            try {
                if (((CocktailBarManagerServiceImpl) this.mCocktailBarServices.get(i2)) != null) {
                    return i2;
                }
                return -10000;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getWindowType() {
        return this.mStatePolicyController.mPolicy.mWindowType;
    }

    public final boolean isBoundCocktailPackage(String str, int i) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(i)) {
            return false;
        }
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Only the system process can call this");
        }
        CocktailBarManagerServiceImpl implForUser = getImplForUser(i);
        CocktailBarSettings cocktailBarSettings = implForUser.mSettings;
        if (cocktailBarSettings == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("isBoundCocktailPackage: user settings not loaded "), implForUser.mUserId, str, "CocktailBarManagerServiceImpl");
            return false;
        }
        synchronized (cocktailBarSettings.mCocktailMap) {
            try {
                Iterator it = cocktailBarSettings.mEnabledCocktailListCache.iterator();
                StringBuffer stringBuffer = null;
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    CocktailBarSettings.CocktailInfo cocktailInfo = (CocktailBarSettings.CocktailInfo) cocktailBarSettings.mCocktailMap.get(str2);
                    if (cocktailInfo == null) {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer("isEnabledCocktail: invalid ");
                            stringBuffer.append(" for pName=");
                            stringBuffer.append(str);
                            stringBuffer.append(" uid=");
                            stringBuffer.append(cocktailBarSettings.mCurrentUserId);
                        }
                        stringBuffer.append(" [");
                        stringBuffer.append(str2);
                        stringBuffer.append("]");
                    } else if (cocktailInfo.packageName.equals(str)) {
                        return true;
                    }
                }
                if (stringBuffer != null) {
                    Slog.d("CocktailBarSettings", stringBuffer.toString());
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isCocktailEnabled(String str, ComponentName componentName) {
        boolean z = false;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return false;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (componentName == null) {
            throw new IllegalArgumentException("invalid provider");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.d("CocktailBarManagerServiceImpl", "isCocktailEnabled: uset not loaded " + implForUser.mUserId + componentName);
                }
                Cocktail lookupProviderLocked = implForUser.lookupProviderLocked(componentName);
                if (lookupProviderLocked != null) {
                    z = implForUser.mSettings.isEnabledCocktail(lookupProviderLocked.getCocktailId());
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean isEnabledCocktail(String str, ComponentName componentName) {
        boolean z = false;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return false;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (componentName == null) {
            throw new IllegalArgumentException("invalid provider");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.d("CocktailBarManagerServiceImpl", "isEnabledCocktail: uset not loaded " + implForUser.mUserId + componentName);
                }
                Cocktail lookupProviderLocked = implForUser.lookupProviderLocked(componentName);
                if (lookupProviderLocked != null && Binder.getCallingUid() == lookupProviderLocked.getUid()) {
                    z = implForUser.mSettings.isEnabledCocktail(lookupProviderLocked.getCocktailId());
                }
            } finally {
            }
        }
        return z;
    }

    public final void notifyCocktailViewDataChanged(String str, int i, int i2) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        CocktailBarHistory cocktailBarHistory = CocktailBarHistory.getInstance();
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "notifyCocktailViewDataChanged callingPackage:", str, ", id:", ", viewId:");
        m.append(i2);
        cocktailBarHistory.recordCocktailBarManagerCommand(m.toString());
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        synchronized (implForUser.mCocktailArr) {
            try {
                Cocktail lookupCocktailLocked = implForUser.lookupCocktailLocked(i, Binder.getCallingUid(), str);
                if (lookupCocktailLocked != null) {
                    implForUser.notifyCocktailViewDataChangedInstanceLocked(lookupCocktailLocked, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyCocktailVisibiltyChanged(int i, int i2) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        if (getUserIdFromCocktailId(i) == -10000) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "notifyCocktailVisibiltyChanged: invalid user id ", "CocktailBarManagerServiceContainer");
            return;
        }
        CocktailBarManagerServiceImpl implForUser = getImplForUser(getUserIdFromCocktailId(i));
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "notifyCocktailVisibiltyChanged : cocktailId = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                Cocktail cocktail = (Cocktail) implForUser.mCocktailArr.get(i);
                if (cocktail != null) {
                    implForUser.sendCocktailChangedVisibilityIntentLocked(cocktail, i2);
                }
            } finally {
            }
        }
    }

    public final void notifyKeyguardState(boolean z) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        CocktailBarManagerServiceImpl implForUser = getImplForUser(this.mCurrentUserId);
        HashMap hashMap = implForUser.mHost;
        if (hashMap == null || hashMap.isEmpty()) {
            Slog.i("CocktailBarManagerServiceImpl", "removeCocktailInHostLocked : no active host");
            return;
        }
        synchronized (implForUser.mHost) {
            Iterator it = implForUser.mHost.entrySet().iterator();
            while (it.hasNext()) {
                CocktailBarManagerServiceImpl.CocktailHostInfo cocktailHostInfo = (CocktailBarManagerServiceImpl.CocktailHostInfo) ((Map.Entry) it.next()).getValue();
                implForUser.mCommandLogger.recordHostCommand(implForUser.mUserId, cocktailHostInfo.mPackageName, "notifyKeyguardState uid=");
                try {
                    cocktailHostInfo.callbackHost.notifyKeyguardState(z, implForUser.mUserId);
                } catch (RemoteException unused) {
                    it.remove();
                }
            }
        }
    }

    public final void onCrossProfileWidgetProvidersChanged(int i, List list) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "onCrossProfileWidgetProvidersChanged : userId = ", "CocktailBarManagerServiceContainer");
        if (list != null) {
            Slog.d("CocktailBarManagerServiceContainer", "onCrossProfileWidgetProvidersChanged : packages = " + list.toString());
        }
    }

    public final void onSetMode(int i, int i2, int i3, String str) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(i)) {
            return;
        }
        ensureGroupStateLoaded(i);
        for (int i4 : this.mSecurityPolicy.getEnabledGroupProfileIds(i)) {
            CocktailBarManagerServiceImpl implForUser = getImplForUser(i4);
            synchronized (implForUser.mCocktailArr) {
                implForUser.setModeLocked(i2, i3, str);
            }
        }
    }

    public final void onUnsetMode(int i, int i2, String str) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(i)) {
            return;
        }
        ensureGroupStateLoaded(i);
        for (int i3 : this.mSecurityPolicy.getEnabledGroupProfileIds(i)) {
            CocktailBarManagerServiceImpl implForUser = getImplForUser(i3);
            synchronized (implForUser.mCocktailArr) {
                if (i2 != 1) {
                    Iterator it = implForUser.findCocktailsByPrivateModeLocked(str).iterator();
                    while (it.hasNext()) {
                        implForUser.removeCocktailLocked((Cocktail) it.next());
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0084, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0173, code lost:
    
        throw r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void partiallyUpdateCocktail(java.lang.String r9, android.widget.RemoteViews r10, int r11) {
        /*
            Method dump skipped, instructions count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.partiallyUpdateCocktail(java.lang.String, android.widget.RemoteViews, int):void");
    }

    public final void partiallyUpdateHelpView(String str, RemoteViews remoteViews, int i) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        if (remoteViews == null) {
            Slog.e("CocktailBarManagerServiceContainer", "partiallyUpdateHelpView : helpView is null");
            return;
        }
        CocktailBarHistory.getInstance().recordCocktailBarManagerCommand("partiallyUpdateHelpView callingPackage:" + str + ", id:" + i);
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "partiallyUpdateHelpView : cocktailId = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.d("CocktailBarManagerServiceImpl", "partiallyUpdateHelpView: user not loaded u=" + implForUser.mUserId + " cocktail=" + i);
                    return;
                }
                Cocktail lookupCocktailLocked = implForUser.lookupCocktailLocked(i, Binder.getCallingUid(), str);
                if (lookupCocktailLocked == null) {
                    Slog.i("CocktailBarManagerServiceImpl", "partiallyUpdateHelpView : invalid cocktailId=" + i);
                    return;
                }
                HashMap hashMap = implForUser.mHost;
                if (hashMap != null && !hashMap.isEmpty()) {
                    lookupCocktailLocked.updateCocktailHelpView(remoteViews, true);
                    if (implForUser.mCocktailPolicyManager.canUpdateCocktail(lookupCocktailLocked, implForUser.mSettings, implForUser.mUserId, implForUser.mModeManager)) {
                        synchronized (implForUser.mHost) {
                            Iterator it = implForUser.mHost.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry entry = (Map.Entry) it.next();
                                CocktailBarManagerServiceImpl.CocktailHostInfo cocktailHostInfo = (CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue();
                                try {
                                    if ((cocktailHostInfo.category & lookupCocktailLocked.getProviderInfo().category) != 0) {
                                        implForUser.mCommandLogger.recordHostCommand(i, cocktailHostInfo.mPackageName, "partiallyUpdateHelpView id=");
                                        ((CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue()).callbackHost.partiallyUpdateHelpView(i, remoteViews, implForUser.mUserId);
                                    } else {
                                        Slog.d("CocktailBarManagerServiceImpl", "partiallyUpdateHelpView: category not matched " + ((String) entry.getKey()) + " cat=" + cocktailHostInfo.category + " requestedCat=" + lookupCocktailLocked.getProviderInfo().category);
                                    }
                                } catch (RemoteException unused) {
                                    it.remove();
                                }
                            }
                        }
                    } else {
                        Slog.i("CocktailBarManagerServiceImpl", "partiallyUpdateHelpView : " + i + " reject");
                    }
                    return;
                }
                Slog.i("CocktailBarManagerServiceImpl", "partiallyUpdateHelpView : no active host");
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        }
    }

    public final void registerCocktailBarStateListenerCallback(IBinder iBinder, ComponentName componentName) {
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        synchronized (cocktailBarStatePolicyController.mStateListeners) {
            try {
                Iterator it = cocktailBarStatePolicyController.mStateListeners.iterator();
                while (it.hasNext()) {
                    CocktailBarStatePolicyController.CocktailBarStateListenerInfo cocktailBarStateListenerInfo = (CocktailBarStatePolicyController.CocktailBarStateListenerInfo) it.next();
                    if (cocktailBarStateListenerInfo != null && iBinder.equals(cocktailBarStateListenerInfo.token)) {
                        Slog.e("CocktailBarStatePolicyController", "registerListenerCallback : already registered");
                        return;
                    }
                }
                CocktailBarStatePolicyController.CocktailBarStateListenerInfo cocktailBarStateListenerInfo2 = cocktailBarStatePolicyController.new CocktailBarStateListenerInfo(iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid());
                try {
                    iBinder.linkToDeath(cocktailBarStateListenerInfo2, 0);
                } catch (RemoteException e) {
                    Slog.e("CocktailBarStatePolicyController", "registerListenerCallback : exception in linkToDeath " + e);
                }
                cocktailBarStatePolicyController.mStateListeners.add(cocktailBarStateListenerInfo2);
                OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy = cocktailBarStatePolicyController.mPolicy;
                IBinder iBinder2 = cocktailBarStateListenerInfo2.token;
                synchronized (overlayCocktailBarStatePolicy.mLock) {
                    Message obtain = Message.obtain();
                    obtain.what = 51;
                    obtain.obj = iBinder2;
                    overlayCocktailBarStatePolicy.enqueueMessageLocked(obtain, false);
                }
            } finally {
            }
        }
    }

    public final void registerSystemUiVisibilityListenerCallback(IBinder iBinder, ComponentName componentName) {
        SystemUiVisibilityPolicyController systemUiVisibilityPolicyController = this.mSystemUiVisibilityPolicyController;
        synchronized (systemUiVisibilityPolicyController.mStateListeners) {
            try {
                Iterator it = systemUiVisibilityPolicyController.mStateListeners.iterator();
                while (it.hasNext()) {
                    SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo = (SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo) it.next();
                    if (systemUiVisibilityListenerInfo != null && iBinder.equals(systemUiVisibilityListenerInfo.token)) {
                        Slog.e("SystemUiVisibilityPolicyController", "registerListenerCallback : already registered");
                        return;
                    }
                }
                Binder.getCallingPid();
                Binder.getCallingUid();
                SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo2 = systemUiVisibilityPolicyController.new SystemUiVisibilityListenerInfo(iBinder);
                try {
                    iBinder.linkToDeath(systemUiVisibilityListenerInfo2, 0);
                } catch (RemoteException e) {
                    Slog.e("SystemUiVisibilityPolicyController", "registerListenerCallback : exception in linkToDeath " + e);
                }
                systemUiVisibilityPolicyController.mStateListeners.add(systemUiVisibilityListenerInfo2);
                systemUiVisibilityPolicyController.notifyStateToBinder(systemUiVisibilityListenerInfo2.token);
            } finally {
            }
        }
    }

    public final void removeCocktailUIService() {
        if (Binder.getCallingUid() == 1000) {
            this.mContext.stopService(this.mIntent);
        }
    }

    public final boolean requestToDisableCocktail(int i) {
        boolean z = false;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return false;
        }
        CocktailBarHistory.getInstance().recordCocktailBarManagerCommand("requestToDisableCocktail id:" + i);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "requestToDisableCocktail : cocktailId = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (implForUser.ensureStateLoadedLocked()) {
                    z = implForUser.requestToDisableCocktailLocked((Cocktail) implForUser.mCocktailArr.get(i));
                } else {
                    Slog.i("CocktailBarManagerServiceImpl", "requestToDisableCocktail : not loaded u=" + implForUser.mUserId + " cocktail=" + i);
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean requestToDisableCocktailByCategory(int i) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return false;
        }
        CocktailBarHistory.getInstance().recordCocktailBarManagerCommand("requestToDisableCocktailByCategory category:" + i);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "requestToDisableCocktailByCategory : category = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.i("CocktailBarManagerServiceImpl", "requestToDisableCocktailByCategory : not loaded u=" + implForUser.mUserId + " category=" + i);
                    return false;
                }
                int size = implForUser.mCocktailArr.size();
                boolean z = false;
                for (int i2 = 0; i2 < size; i2++) {
                    Cocktail cocktail = (Cocktail) implForUser.mCocktailArr.valueAt(i2);
                    if (cocktail.getProviderInfo().category == i) {
                        z |= implForUser.requestToDisableCocktailLocked(cocktail);
                    }
                }
                return z;
            } finally {
            }
        }
    }

    public final boolean requestToUpdateCocktail(int i) {
        boolean z = false;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return false;
        }
        CocktailBarHistory.getInstance().recordCocktailBarManagerCommand("requestToUpdateCocktail id:" + i);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "requestToUpdateCocktail : cocktailId = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (implForUser.ensureStateLoadedLocked()) {
                    z = implForUser.requestToUpdateCocktailLocked((Cocktail) implForUser.mCocktailArr.get(i));
                } else {
                    Slog.i("CocktailBarManagerServiceImpl", "requestToUpdateCocktail : not loaded u=" + implForUser.mUserId + " cocktail=" + i);
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean requestToUpdateCocktailByCategory(int i) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return false;
        }
        CocktailBarHistory.getInstance().recordCocktailBarManagerCommand("requestToUpdateCocktailByCategory category:" + i);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "requestToUpdateCocktailByCategory : category = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.i("CocktailBarManagerServiceImpl", "requestToUpdateCocktailByCategory : not loaded u=" + implForUser.mUserId + " category=" + i);
                    return false;
                }
                int size = implForUser.mCocktailArr.size();
                boolean z = false;
                for (int i2 = 0; i2 < size; i2++) {
                    Cocktail cocktail = (Cocktail) implForUser.mCocktailArr.valueAt(i2);
                    if (cocktail.getProviderInfo().category == i) {
                        z |= implForUser.requestToUpdateCocktailLocked(cocktail);
                    }
                }
                return z;
            } finally {
            }
        }
    }

    public final void sendEdgeAppStartBr(int i, int i2) {
        CocktailBarHistory.getInstance().recordServiceProcess("sendEdgeAppStartBr " + i2);
        Intent intent = new Intent("com.samsung.android.cocktailbar.intent.action.EDGE_APP_START");
        intent.addFlags(285212704);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
    }

    public final void sendExtraDataToCocktailBar(Bundle bundle) {
        CocktailBarManagerServiceImpl implForUser;
        HashMap hashMap;
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId()) || (hashMap = (implForUser = getImplForUser(UserHandle.getCallingUserId())).mHost) == null) {
            return;
        }
        try {
            synchronized (hashMap) {
                try {
                    for (Map.Entry entry : implForUser.mHost.entrySet()) {
                        implForUser.mCommandLogger.recordHostCommand(implForUser.mUserId, ((CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue()).mPackageName, "removeCocktailInHostLocked uid=");
                        ((CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue()).callbackHost.sendExtraData(implForUser.mUserId, bundle);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void setCocktailBarWakeUpState(boolean z) {
    }

    public final void setCocktailHostCallbacks(ICocktailHost iCocktailHost, String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
            return;
        }
        checkPermission();
        CocktailBarHistory.getInstance().recordServiceProcess("setCocktailHostCallbacks packageName - " + str + ", category - " + i);
        this.mHost.put(str, iCocktailHost);
        this.mFilterCategory.put(str, Integer.valueOf(i));
        ensureGroupStateLoaded(callingUserId);
        for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId)) {
            getImplForUser(i2).setCocktailHostCallbacks(this.mHost, this.mFilterCategory, true);
        }
        if (SemPersonaManager.isKioskModeEnabled(this.mContext)) {
            int callingUserId2 = UserHandle.getCallingUserId();
            int i3 = this.mCurrentUserId;
            if (callingUserId2 != i3) {
                getImplForUser(i3).setCocktailHostCallbacks(this.mHost, this.mFilterCategory, true);
            }
        }
    }

    public final void setEnabledCocktailIds(int[] iArr) {
        Cocktail cocktail;
        int callingUserId = UserHandle.getCallingUserId();
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
            return;
        }
        checkPermission();
        ensureGroupStateLoaded(callingUserId);
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId);
        for (int i : enabledGroupProfileIds) {
            CocktailBarManagerServiceImpl implForUser = getImplForUser(i);
            synchronized (implForUser.mCocktailArr) {
                try {
                    if (implForUser.ensureStateLoadedLocked()) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 : iArr) {
                            Cocktail cocktail2 = (Cocktail) implForUser.mCocktailArr.get(i2);
                            if (cocktail2 != null && cocktail2.getProvider() != null) {
                                arrayList.add(cocktail2.getProvider().getClassName());
                            }
                        }
                        implForUser.mHandler.post(new CocktailBarManagerServiceImpl.AnonymousClass4(implForUser, arrayList, 1));
                    } else {
                        Slog.d("CocktailBarManagerServiceImpl", "setEnabledCocktailIds: settings not loaded yet" + implForUser.mUserId + implForUser.mStateLoaded);
                    }
                } finally {
                }
            }
        }
        int length = iArr.length;
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < length; i3++) {
            for (int i4 = 0; i4 < enabledGroupProfileIds.length; i4++) {
                CocktailBarManagerServiceImpl implForUser2 = getImplForUser(enabledGroupProfileIds[i4]);
                int i5 = iArr[i3];
                synchronized (implForUser2.mCocktailArr) {
                    cocktail = (Cocktail) implForUser2.mCocktailArr.get(i5);
                    if (cocktail == null) {
                        cocktail = null;
                    }
                }
                if (cocktail != null) {
                    int i6 = enabledGroupProfileIds[i4];
                    CocktailOrderManager.CocktailOrderInfo cocktailOrderInfo = new CocktailOrderManager.CocktailOrderInfo();
                    cocktailOrderInfo.mCocktailId = cocktail.getCocktailId();
                    if (cocktail.getProvider() != null) {
                        cocktailOrderInfo.mComponentName = cocktail.getProvider();
                    }
                    cocktailOrderInfo.mOrder = i3;
                    cocktailOrderInfo.mUserId = i6;
                    arrayList2.add(cocktailOrderInfo);
                }
            }
        }
        CocktailOrderManager cocktailOrderManager = this.mCocktailOrderManager;
        synchronized (cocktailOrderManager.mLock) {
            cocktailOrderManager.mCocktailOrderedList = arrayList2;
            AtomicFile savedStateFile = CocktailOrderManager.savedStateFile();
            try {
                FileOutputStream startWrite = savedStateFile.startWrite();
                if (cocktailOrderManager.writeStateToFileLocked(startWrite)) {
                    savedStateFile.finishWrite(startWrite);
                } else {
                    savedStateFile.failWrite(startWrite);
                    Slog.w("CocktailOrderManager", "Failed to save state, restoring backup.");
                }
            } catch (IOException e) {
                Slog.w("CocktailOrderManager", "Failed open state file for write: " + e);
            }
        }
    }

    public final void setOnPullPendingIntent(String str, int i, int i2, PendingIntent pendingIntent) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        CocktailBarHistory cocktailBarHistory = CocktailBarHistory.getInstance();
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "setOnPullPendingIntent callingPackage:", str, ", id:", ", viewId:");
        m.append(i2);
        cocktailBarHistory.recordCocktailBarManagerCommand(m.toString());
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.d("CocktailBarManagerServiceImpl", "setOnPullPendingIntent: u=" + implForUser.mUserId + " cocktail=" + i);
                    return;
                }
                Cocktail lookupCocktailLocked = implForUser.lookupCocktailLocked(i, Binder.getCallingUid(), str);
                if (lookupCocktailLocked != null && lookupCocktailLocked.getCocktailInfo() != null) {
                    if (lookupCocktailLocked.getProviderInfo() != null && (lookupCocktailLocked.getProviderInfo().category & 256) == 0) {
                        HashMap hashMap = implForUser.mHost;
                        if (hashMap != null && !hashMap.isEmpty()) {
                            if (implForUser.mCocktailPolicyManager.canUpdateCocktail(lookupCocktailLocked, implForUser.mSettings, implForUser.mUserId, implForUser.mModeManager)) {
                                synchronized (implForUser.mHost) {
                                    Iterator it = implForUser.mHost.entrySet().iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = (Map.Entry) it.next();
                                        CocktailBarManagerServiceImpl.CocktailHostInfo cocktailHostInfo = (CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue();
                                        try {
                                            if ((cocktailHostInfo.category & lookupCocktailLocked.getProviderInfo().category) != 0) {
                                                implForUser.mCommandLogger.recordHostCommand(i, cocktailHostInfo.mPackageName, "setPullToRefresh id=");
                                                ((CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue()).callbackHost.setPullToRefresh(i, i2, pendingIntent, implForUser.mUserId);
                                            } else {
                                                Slog.d("CocktailBarManagerServiceImpl", "setOnPullPendingIntent: category not matched " + ((String) entry.getKey()) + " cat=" + cocktailHostInfo.category + " requestedCat=" + lookupCocktailLocked.getProviderInfo().category);
                                            }
                                        } catch (RemoteException unused) {
                                            it.remove();
                                        }
                                    }
                                }
                            } else {
                                Slog.i("CocktailBarManagerServiceImpl", "setOnPullPendingIntent : " + i + " reject");
                            }
                            return;
                        }
                        Slog.i("CocktailBarManagerServiceImpl", "setOnPullPendingIntent : no active host");
                        return;
                    }
                    Slog.i("CocktailBarManagerServiceImpl", "setOnPullPendingIntent: not supported provider  " + i);
                    return;
                }
                Slog.i("CocktailBarManagerServiceImpl", "setOnPullPendingIntent : invalid cocktailId=" + i);
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x007c, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0171, code lost:
    
        throw r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showCocktail(java.lang.String r9, int r10) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.cocktailbar.CocktailBarManagerServiceContainer.showCocktail(java.lang.String, int):void");
    }

    public final void startListening(ICocktailHost iCocktailHost, String str, int i) {
        int callingUserId = UserHandle.getCallingUserId();
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(callingUserId)) {
            return;
        }
        checkPermission();
        CocktailBarHistory.getInstance().recordServiceProcess("startListening packageName - " + str + ", category - " + i);
        HermesService$3$$ExternalSyntheticOutline0.m(i, "startListening() ", "CocktailBarManagerServiceContainer");
        this.mHost.put(str, iCocktailHost);
        this.mFilterCategory.put(str, Integer.valueOf(i));
        ensureGroupStateLoaded(callingUserId);
        for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(callingUserId)) {
            CocktailBarManagerServiceImpl implForUser = getImplForUser(i2);
            synchronized (implForUser.mCocktailArr) {
                Slog.d("CocktailBarManagerServiceImpl", "startListening: " + implForUser.ensureStateLoadedLocked() + implForUser.mUserId + " init=" + implForUser.mInitialzed);
            }
            if (implForUser.mHost == null) {
                implForUser.mHost = new HashMap();
            }
            synchronized (implForUser.mHost) {
                try {
                    CocktailBarManagerServiceImpl.CocktailHostInfo cocktailHostInfo = (CocktailBarManagerServiceImpl.CocktailHostInfo) implForUser.mHost.get(str);
                    if (cocktailHostInfo == null) {
                        implForUser.mHost.put(str, implForUser.new CocktailHostInfo(str, iCocktailHost, i));
                    } else if (cocktailHostInfo.token.equals(iCocktailHost.asBinder())) {
                        cocktailHostInfo.category = i;
                    } else {
                        cocktailHostInfo.unlinkBinder();
                        implForUser.mHost.put(str, implForUser.new CocktailHostInfo(str, iCocktailHost, i));
                    }
                    implForUser.mHostCategory = 0;
                    Iterator it = implForUser.mHost.entrySet().iterator();
                    while (it.hasNext()) {
                        implForUser.mHostCategory = ((CocktailBarManagerServiceImpl.CocktailHostInfo) ((Map.Entry) it.next()).getValue()).category | implForUser.mHostCategory;
                    }
                } finally {
                }
            }
            if (implForUser.mHostCategory != 0) {
                if (implForUser.mInitialzed) {
                    synchronized (implForUser.mCocktailArr) {
                        try {
                            if (implForUser.ensureStateLoadedLocked()) {
                                int size = implForUser.mCocktailArr.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    Cocktail cocktail = (Cocktail) implForUser.mCocktailArr.valueAt(i3);
                                    CocktailBarManagerServiceImpl.checkCocktailAttributeLocked(cocktail, implForUser.mHostCategory);
                                    if (cocktail.getState() != 2) {
                                        if (!implForUser.mCocktailPolicyManager.isUpdatedCocktail(cocktail.getCocktailId(), implForUser.mUserId) && implForUser.mSettings.isEnabledCocktail(cocktail.getCocktailId())) {
                                            implForUser.sendEnableAndUpdateBroadcastLocked(cocktail);
                                        }
                                    } else if (implForUser.mCocktailPolicyManager.isUpdatedCocktail(cocktail.getCocktailId(), implForUser.mUserId)) {
                                        implForUser.removeCocktailLocked(cocktail);
                                    }
                                }
                            } else {
                                Slog.i("CocktailBarManagerServiceImpl", "updateCocktailAttribute : not loaded u=" + implForUser.mUserId);
                            }
                        } finally {
                        }
                    }
                } else {
                    CocktailBarModeManager cocktailBarModeManager = implForUser.mModeManager;
                    int i4 = cocktailBarModeManager.mCocktailBarModeId;
                    Iterator it2 = cocktailBarModeManager.mPrivateModes.iterator();
                    int i5 = i4;
                    while (it2.hasNext()) {
                        i5 = ((CocktailBarMode) it2.next()).renewMode(i5);
                    }
                    if (i4 != i5) {
                        CocktailBarModeManager cocktailBarModeManager2 = implForUser.mModeManager;
                        cocktailBarModeManager2.onSetMode(implForUser.mUserId, cocktailBarModeManager2.getCocktailBarMode(i5));
                    } else if (i5 == 1) {
                        implForUser.sendInitialBroadcasts();
                    }
                    implForUser.mInitialzed = true;
                }
            }
        }
    }

    public final void stopListening(String str) {
        int callingUserId = UserHandle.getCallingUserId();
        if (isNotEdgeRunnableId(callingUserId)) {
            return;
        }
        CocktailBarHistory.getInstance().recordServiceProcess("stopListening callingPackage - " + str);
        Slog.i("CocktailBarManagerServiceContainer", "stopListening() " + callingUserId);
        this.mSecurityPolicy.enforceCallFromPackage(str);
        ensureGroupStateLoaded(callingUserId);
        HashMap hashMap = this.mHost;
        if (hashMap == null || !hashMap.containsKey(str)) {
            return;
        }
        this.mHost.remove(str);
        synchronized (this.mCocktailBarServices) {
            try {
                int indexOfKey = this.mCocktailBarServices.indexOfKey(callingUserId);
                if (indexOfKey >= 0) {
                    ((CocktailBarManagerServiceImpl) this.mCocktailBarServices.valueAt(indexOfKey)).deleteHost(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        CocktailBarStatePolicyController cocktailBarStatePolicyController = CocktailBarStatePolicyController.getInstance();
        OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy = cocktailBarStatePolicyController.mPolicy;
        int i = overlayCocktailBarStatePolicy.mStateInfo.windowType;
        if (CocktailBarStatePolicyController.DEBUG) {
            Slog.i("CocktailBarStatePolicyController", "clearCocktailWindowType: currentWindowType = " + i + ", callingPkg=" + str);
        }
        String str2 = cocktailBarStatePolicyController.mPackageName;
        if (str2 == null || !str2.equals(str)) {
            return;
        }
        cocktailBarStatePolicyController.mPackageName = null;
        overlayCocktailBarStatePolicy.updateCocktailBarWindowType(1, null);
    }

    public final void unbindRemoteViewsService(String str, int i, Intent intent) {
        if (enforceCocktailBarService()) {
            isNotEdgeRunnableId(UserHandle.getCallingUserId());
        }
    }

    public final void unregisterCocktailBarStateListenerCallback(IBinder iBinder) {
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        synchronized (cocktailBarStatePolicyController.mStateListeners) {
            try {
                Iterator it = cocktailBarStatePolicyController.mStateListeners.iterator();
                CocktailBarStatePolicyController.CocktailBarStateListenerInfo cocktailBarStateListenerInfo = null;
                while (it.hasNext()) {
                    CocktailBarStatePolicyController.CocktailBarStateListenerInfo cocktailBarStateListenerInfo2 = (CocktailBarStatePolicyController.CocktailBarStateListenerInfo) it.next();
                    if (cocktailBarStateListenerInfo2 != null && iBinder.equals(cocktailBarStateListenerInfo2.token)) {
                        cocktailBarStateListenerInfo = cocktailBarStateListenerInfo2;
                    }
                }
                if (cocktailBarStateListenerInfo == null) {
                    Slog.e("CocktailBarStatePolicyController", "registerListenerCallback : cannot find the matched listener");
                    return;
                }
                if (!cocktailBarStatePolicyController.mStateListeners.isEmpty()) {
                    cocktailBarStatePolicyController.mStateListeners.remove(cocktailBarStateListenerInfo);
                }
                iBinder.unlinkToDeath(cocktailBarStateListenerInfo, 0);
                cocktailBarStatePolicyController.mStateListeners.notify();
            } finally {
            }
        }
    }

    public final void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) {
        SystemUiVisibilityPolicyController systemUiVisibilityPolicyController = this.mSystemUiVisibilityPolicyController;
        synchronized (systemUiVisibilityPolicyController.mStateListeners) {
            try {
                Iterator it = systemUiVisibilityPolicyController.mStateListeners.iterator();
                SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo = null;
                while (it.hasNext()) {
                    SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo2 = (SystemUiVisibilityPolicyController.SystemUiVisibilityListenerInfo) it.next();
                    if (systemUiVisibilityListenerInfo2 != null && iBinder.equals(systemUiVisibilityListenerInfo2.token)) {
                        systemUiVisibilityListenerInfo = systemUiVisibilityListenerInfo2;
                    }
                }
                if (systemUiVisibilityListenerInfo == null) {
                    Slog.e("SystemUiVisibilityPolicyController", "registerListenerCallback : cannot find the matched listener");
                    return;
                }
                if (!systemUiVisibilityPolicyController.mStateListeners.isEmpty()) {
                    systemUiVisibilityPolicyController.mStateListeners.remove(systemUiVisibilityListenerInfo);
                }
                iBinder.unlinkToDeath(systemUiVisibilityListenerInfo, 0);
                systemUiVisibilityPolicyController.mStateListeners.notify();
            } finally {
            }
        }
    }

    public final void updateCocktail(String str, CocktailInfo cocktailInfo, int i) {
        if (!enforceCocktailBarService() || isNotEdgeRunnableId(UserHandle.getCallingUserId())) {
            return;
        }
        if (cocktailInfo == null) {
            Slog.e("CocktailBarManagerServiceContainer", "updateCocktail : cocktailInfo is null");
            return;
        }
        CocktailBarHistory.getInstance().recordCocktailBarManagerCommand("updateCocktail callingPackage:" + str + ", id:" + i);
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarManagerServiceImpl implForUser = getImplForUser(UserHandle.getCallingUserId());
        if (CocktailBarManagerServiceImpl.DEBUG) {
            HermesService$3$$ExternalSyntheticOutline0.m(i, "updateCocktail : cocktailId = ", "CocktailBarManagerServiceImpl");
        }
        synchronized (implForUser.mCocktailArr) {
            try {
                if (!implForUser.ensureStateLoadedLocked()) {
                    Slog.d("CocktailBarManagerServiceImpl", "updateCocktail: u=" + implForUser.mUserId + " cocktail=" + i);
                    return;
                }
                Cocktail lookupCocktailLocked = implForUser.lookupCocktailLocked(i, Binder.getCallingUid(), str);
                if (lookupCocktailLocked == null) {
                    Slog.i("CocktailBarManagerServiceImpl", "updateCocktail : invalid cocktailId=" + i);
                    return;
                }
                if (!CocktailBarManagerServiceImpl.checkSize(cocktailInfo.getContentView())) {
                    Slog.i("CocktailBarManagerServiceImpl", "updateCocktail : content's view is too large.");
                    return;
                }
                if (!CocktailBarManagerServiceImpl.checkSize(cocktailInfo.getHelpView())) {
                    Slog.i("CocktailBarManagerServiceImpl", "updateCocktail : helpcontent's view is too large.");
                    return;
                }
                HashMap hashMap = implForUser.mHost;
                if (hashMap != null && !hashMap.isEmpty()) {
                    if (implForUser.mCocktailPolicyManager.canUpdateCocktail(lookupCocktailLocked, implForUser.mSettings, implForUser.mUserId, implForUser.mModeManager)) {
                        synchronized (implForUser.mHost) {
                            Iterator it = implForUser.mHost.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry entry = (Map.Entry) it.next();
                                CocktailBarManagerServiceImpl.CocktailHostInfo cocktailHostInfo = (CocktailBarManagerServiceImpl.CocktailHostInfo) entry.getValue();
                                try {
                                    try {
                                        if ((cocktailHostInfo.category & lookupCocktailLocked.getProviderInfo().category) != 0) {
                                            CocktailBarHistory.getInstance().recordPanelUpdateHistory(i, "updateCocktail");
                                            implForUser.mCommandLogger.recordHostCommand(i, cocktailHostInfo.mPackageName, "updateCocktail id=");
                                            lookupCocktailLocked.updateCocktailInfo(cocktailInfo);
                                            lookupCocktailLocked.setPackageSuspended(implForUser.mPm.isPackageSuspendedForUser(CocktailBarManagerServiceImpl.getPackageNameFromCocktail(lookupCocktailLocked), implForUser.mUserId));
                                            cocktailHostInfo.callbackHost.updateCocktail(i, lookupCocktailLocked, implForUser.mUserId);
                                            lookupCocktailLocked.setPackageUpdated(false);
                                        } else {
                                            Slog.d("CocktailBarManagerServiceImpl", "updateCocktail: category not matched " + i + ((String) entry.getKey()) + " cat=" + cocktailHostInfo.category + " requestedCat=" + lookupCocktailLocked.getProviderInfo().category);
                                        }
                                    } catch (RemoteException unused) {
                                        it.remove();
                                    }
                                } catch (IllegalArgumentException unused2) {
                                    Slog.e("CocktailBarManagerServiceImpl", "Package is not founded");
                                }
                            }
                        }
                    } else {
                        Slog.i("CocktailBarManagerServiceImpl", "updateCocktail : " + i + " reject");
                    }
                    return;
                }
                Slog.i("CocktailBarManagerServiceImpl", "updateCocktail : no active host");
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        }
    }

    public final void updateCocktailBarPosition(int i) {
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        if (CocktailBarStatePolicyController.DEBUG) {
            cocktailBarStatePolicyController.getClass();
            Slog.i("CocktailBarStatePolicyController", "updatePosition: position = " + i);
        }
        OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy = cocktailBarStatePolicyController.mPolicy;
        synchronized (overlayCocktailBarStatePolicy.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 4;
            obtain.arg1 = i;
            overlayCocktailBarStatePolicy.enqueueMessageLocked(obtain, true);
        }
    }

    public final void updateCocktailBarVisibility(int i) {
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        if (CocktailBarStatePolicyController.DEBUG) {
            cocktailBarStatePolicyController.getClass();
            Slog.i("CocktailBarStatePolicyController", "updateVisibility: visibility = " + i);
        }
        OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy = cocktailBarStatePolicyController.mPolicy;
        synchronized (overlayCocktailBarStatePolicy.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.arg1 = i;
            overlayCocktailBarStatePolicy.enqueueMessageLocked(obtain, true);
        }
    }

    public final void updateCocktailBarWindowType(String str, int i) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        CocktailBarStatePolicyController cocktailBarStatePolicyController = this.mStatePolicyController;
        OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy = cocktailBarStatePolicyController.mPolicy;
        int i2 = overlayCocktailBarStatePolicy.mStateInfo.windowType;
        if (CocktailBarStatePolicyController.DEBUG) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i, "updateWindowType: currentWindowType = ", " windowType = ", " CP=");
            m.append(str);
            m.append(" P=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(m, cocktailBarStatePolicyController.mPackageName, "CocktailBarStatePolicyController");
        }
        if (str != null) {
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                String str2 = cocktailBarStatePolicyController.mPackageName;
                if (str2 == null || !str2.equals(str)) {
                    cocktailBarStatePolicyController.mPackageName = str;
                    overlayCocktailBarStatePolicy.updateCocktailBarWindowType(i, str);
                    return;
                }
                return;
            }
            if (i2 == i) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "updateWindowType: current window type is requested window type(", ")", "CocktailBarStatePolicyController");
                return;
            }
            String str3 = cocktailBarStatePolicyController.mPackageName;
            if (str3 == null || !str3.equals(str)) {
                return;
            }
            overlayCocktailBarStatePolicy.updateCocktailBarWindowType(i, str);
            cocktailBarStatePolicyController.mPackageName = null;
        }
    }

    public final void updateWakeupArea(int i) {
    }

    public final void updateWakeupGesture(int i, boolean z) {
    }
}
