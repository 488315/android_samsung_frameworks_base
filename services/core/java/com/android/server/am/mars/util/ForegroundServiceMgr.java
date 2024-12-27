package com.android.server.am.mars.util;

import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IProcessObserver;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.app.IAppOpsService;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.MARsHandler;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.ForegroundServiceRecord;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.util.ForegroundServiceMgr;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ForegroundServiceMgr implements ActivityManagerInternal.ForegroundServiceStateListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ActivityManagerInternal mActivityManagerInternal;
    public final ConcurrentHashMap mMapFGSRecord = new ConcurrentHashMap();
    public final AnonymousClass1 mProcessObserver = new IProcessObserver.Stub() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr.1
        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
        }

        public final void onForegroundServicesChanged(int i, int i2, int i3) {
            String[] strArr;
            String str;
            boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
            boolean z2 = MARsPolicyManager.MARs_ENABLE;
            MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
            mARsPolicyManager.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    strArr = mARsPolicyManager.mContext.getPackageManager().getPackagesForUid(i2);
                } catch (Exception e) {
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    strArr = null;
                }
                if (strArr == null) {
                    Slog.e("MARsPolicyManager", "package name not found for uid " + i2);
                    str = Integer.toString(i2);
                } else {
                    str = strArr[0];
                }
                if (str != null) {
                    ForegroundServiceMgr.this.getClass();
                    if (ForegroundServiceMgr.isFGSTarget(str)) {
                        ForegroundServiceRecord foregroundServiceRecord = (ForegroundServiceRecord) ForegroundServiceMgr.this.mMapFGSRecord.get(Integer.valueOf(i2));
                        if (foregroundServiceRecord == null) {
                            foregroundServiceRecord = new ForegroundServiceRecord(str);
                            ForegroundServiceMgr.this.mMapFGSRecord.put(Integer.valueOf(i2), foregroundServiceRecord);
                        }
                        foregroundServiceRecord.mForegroundType |= i3;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void onProcessDied(int i, int i2) {
        }

        public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {
        }
    };
    public final GetAccesesTimeHelper getAccesesTimeHelper = new GetAccesesTimeHelper();
    public final ForegroundServiceMgr$$ExternalSyntheticLambda0 mGetUsingFGSTypeRunnable = new Runnable() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ForegroundServiceMgr.GetAccesesTimeHelper getAccesesTimeHelper = ForegroundServiceMgr.this.getAccesesTimeHelper;
            ForegroundServiceMgr.GetAccesesTimeHelper.CustomVisitor customVisitor = getAccesesTimeHelper.visitor;
            try {
                for (String str : customVisitor.accessTimePerOp.keySet()) {
                    Slog.d("ForegroundServiceMgr", "op [" + str + "] access time");
                    getAccesesTimeHelper.getAccessTimeOfOp(customVisitor.filterUid, str);
                }
            } catch (Exception e) {
                Slog.d("ForegroundServiceMgr", "exception", e);
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ForegroundServiceMgrHolder {
        public static final ForegroundServiceMgr INSTANCE = new ForegroundServiceMgr();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GetAccesesTimeHelper {
        public IAppOpsService mAppOpsService;
        public final CustomVisitor visitor;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class CustomVisitor implements AppOpsManager.HistoricalOpsVisitor {
            public ArrayMap accessTimePerOp;
            public String curPackageName;
            public int curUid;
            public String filterPackageName;
            public int filterUid;

            public final void visitHistoricalAttributionOps(AppOpsManager.AttributedHistoricalOps attributedHistoricalOps) {
                this.curPackageName.equals(this.filterPackageName);
            }

            public final void visitHistoricalOp(AppOpsManager.HistoricalOp historicalOp) {
                ArrayList arrayList = (ArrayList) this.accessTimePerOp.computeIfAbsent(historicalOp.getOpName(), new ForegroundServiceMgr$GetAccesesTimeHelper$CustomVisitor$$ExternalSyntheticLambda0());
                int discreteAccessCount = historicalOp.getDiscreteAccessCount();
                if (discreteAccessCount > 0) {
                    arrayList.add(Long.valueOf(historicalOp.getDiscreteAccessAt(discreteAccessCount - 1).getLastAccessTime(31)));
                }
            }

            public final void visitHistoricalOps(AppOpsManager.HistoricalOps historicalOps) {
            }

            public final void visitHistoricalPackageOps(AppOpsManager.HistoricalPackageOps historicalPackageOps) {
                if (this.curUid != this.filterUid) {
                    return;
                }
                this.curPackageName = historicalPackageOps.getPackageName();
            }

            public final void visitHistoricalUidOps(AppOpsManager.HistoricalUidOps historicalUidOps) {
                this.curUid = historicalUidOps.getUid();
            }
        }

        public GetAccesesTimeHelper() {
            CustomVisitor customVisitor = new CustomVisitor();
            customVisitor.filterUid = 0;
            customVisitor.curUid = 0;
            customVisitor.accessTimePerOp = new ArrayMap();
            this.visitor = customVisitor;
        }

        public final void getAccessTimeOfOp(int i, String str) {
            ForegroundServiceRecord foregroundServiceRecord = (ForegroundServiceRecord) ForegroundServiceMgr.this.mMapFGSRecord.get(Integer.valueOf(i));
            ArrayList arrayList = (ArrayList) this.visitor.accessTimePerOp.get(str);
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            long longValue = ((Long) arrayList.get(arrayList.size() - 1)).longValue();
            int i2 = ForegroundServiceMgr.$r8$clinit;
            BootReceiver$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("access at ", longValue, ", "), longValue == 0 ? String.format("%16s", "null") : new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(longValue)), "ForegroundServiceMgr");
            if (foregroundServiceRecord != null) {
                long j = foregroundServiceRecord.mFGSStartTime;
                long j2 = foregroundServiceRecord.mFGSEndTime;
                long roundDownToMinute = ForegroundServiceMgr.roundDownToMinute(longValue);
                long roundDownToMinute2 = ForegroundServiceMgr.roundDownToMinute(j);
                long roundDownToMinute3 = ForegroundServiceMgr.roundDownToMinute(j2);
                if (roundDownToMinute2 <= 0 || roundDownToMinute3 < roundDownToMinute2 || roundDownToMinute2 > roundDownToMinute || roundDownToMinute > roundDownToMinute3) {
                    return;
                }
                int i3 = 0;
                if (str != null) {
                    if (str.contains("location")) {
                        i3 = 8;
                    } else if (str.contains("record")) {
                        i3 = 128;
                    } else if (str.contains("audio")) {
                        i3 = 2;
                    } else if (str.contains("camera")) {
                        i3 = 64;
                    }
                }
                foregroundServiceRecord.mUsingForegroundType = i3 | foregroundServiceRecord.mUsingForegroundType;
            }
        }
    }

    public static boolean isFGSTarget(String str) {
        int i;
        try {
            i = AppGlobals.getPackageManager().getTargetSdkVersion(str);
        } catch (RemoteException e) {
            Slog.e("ForegroundServiceMgr", "getPackageManager() failed " + e);
            i = 0;
        }
        return i >= 34;
    }

    public static long roundDownToMinute(long j) {
        if (j != 0) {
            return (j / 1000) / 60;
        }
        return 0L;
    }

    public final void onForegroundServiceNotificationUpdated(String str, int i, int i2, boolean z) {
    }

    public final void onForegroundServiceStateChanged(final String str, final int i, int i2, boolean z) {
        if (isFGSTarget(str)) {
            ForegroundServiceRecord foregroundServiceRecord = (ForegroundServiceRecord) this.mMapFGSRecord.get(Integer.valueOf(i));
            if (foregroundServiceRecord == null) {
                foregroundServiceRecord = new ForegroundServiceRecord(str);
                this.mMapFGSRecord.put(Integer.valueOf(i), foregroundServiceRecord);
            }
            if (z) {
                foregroundServiceRecord.mFGSStartTime = System.currentTimeMillis();
                return;
            }
            foregroundServiceRecord.mFGSEndTime = System.currentTimeMillis();
            final GetAccesesTimeHelper getAccesesTimeHelper = this.getAccesesTimeHelper;
            getAccesesTimeHelper.getClass();
            getAccesesTimeHelper.mAppOpsService = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
            GetAccesesTimeHelper.CustomVisitor customVisitor = getAccesesTimeHelper.visitor;
            customVisitor.filterUid = i;
            customVisitor.filterPackageName = str;
            customVisitor.accessTimePerOp.clear();
            IAppOpsService iAppOpsService = getAccesesTimeHelper.mAppOpsService;
            MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
            if (iAppOpsService != null) {
                Runnable runnable = new Runnable() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr$GetAccesesTimeHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final ForegroundServiceMgr.GetAccesesTimeHelper getAccesesTimeHelper2 = ForegroundServiceMgr.GetAccesesTimeHelper.this;
                        int i3 = i;
                        String str2 = str;
                        getAccesesTimeHelper2.getClass();
                        try {
                            getAccesesTimeHelper2.mAppOpsService.getHistoricalOps(i3, str2, (String) null, (List) null, 3, 3, 0L, Long.MAX_VALUE, 31, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr$GetAccesesTimeHelper$$ExternalSyntheticLambda1
                                public final void onResult(Bundle bundle) {
                                    ForegroundServiceMgr.GetAccesesTimeHelper getAccesesTimeHelper3 = ForegroundServiceMgr.GetAccesesTimeHelper.this;
                                    getAccesesTimeHelper3.getClass();
                                    try {
                                        ((AppOpsManager.HistoricalOps) bundle.getParcelable("historical_ops", AppOpsManager.HistoricalOps.class)).accept(getAccesesTimeHelper3.visitor);
                                    } catch (Exception e) {
                                        int i4 = ForegroundServiceMgr.$r8$clinit;
                                        Slog.d("ForegroundServiceMgr", "exception", e);
                                    }
                                }
                            }));
                        } catch (Exception e) {
                            int i4 = ForegroundServiceMgr.$r8$clinit;
                            Slog.d("ForegroundServiceMgr", "exception", e);
                        }
                    }
                };
                boolean z2 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                mARsHandler.mMainHandler.postDelayed(runnable, 0L);
            }
            ForegroundServiceMgr$$ExternalSyntheticLambda0 foregroundServiceMgr$$ExternalSyntheticLambda0 = ForegroundServiceMgr.this.mGetUsingFGSTypeRunnable;
            boolean z3 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
            mARsHandler.mMainHandler.postDelayed(foregroundServiceMgr$$ExternalSyntheticLambda0, 1000L);
        }
    }
}
