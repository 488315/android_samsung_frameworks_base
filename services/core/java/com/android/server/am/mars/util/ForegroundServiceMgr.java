package com.android.server.am.mars.util;

import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IProcessObserver;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.app.IAppOpsService;
import com.android.server.LocalServices;
import com.android.server.am.MARsHandler;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.ForegroundServiceRecord;
import com.android.server.am.mars.util.ForegroundServiceMgr;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ForegroundServiceMgr implements ActivityManagerInternal.ForegroundServiceStateListener {
    public static final String TAG = ForegroundServiceMgr.class.getSimpleName();
    public GetAccesesTimeHelper getAccesesTimeHelper;
    public ActivityManagerInternal mActivityManagerInternal;
    public Runnable mGetUsingFGSTypeRunnable;
    public ConcurrentHashMap mMapFGSRecord;
    public final IProcessObserver.Stub mProcessObserver;

    public abstract class ForegroundServiceMgrHolder {
        public static final ForegroundServiceMgr INSTANCE = new ForegroundServiceMgr();
    }

    public void onForegroundServiceNotificationUpdated(String str, int i, int i2, boolean z) {
    }

    public ForegroundServiceMgr() {
        this.mMapFGSRecord = new ConcurrentHashMap();
        this.mProcessObserver = new IProcessObserver.Stub() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr.1
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            }

            public void onProcessDied(int i, int i2) {
            }

            public void onForegroundServicesChanged(int i, int i2, int i3) {
                String packageNameByUid = MARsPolicyManager.getInstance().getPackageNameByUid(i2);
                if (packageNameByUid == null || !ForegroundServiceMgr.this.isFGSTarget(packageNameByUid)) {
                    return;
                }
                ForegroundServiceRecord foregroundServiceRecord = (ForegroundServiceRecord) ForegroundServiceMgr.this.mMapFGSRecord.get(Integer.valueOf(i2));
                if (foregroundServiceRecord == null) {
                    foregroundServiceRecord = new ForegroundServiceRecord(packageNameByUid, i2);
                    ForegroundServiceMgr.this.mMapFGSRecord.put(Integer.valueOf(i2), foregroundServiceRecord);
                }
                foregroundServiceRecord.setFGSType(i3);
            }
        };
        this.getAccesesTimeHelper = new GetAccesesTimeHelper();
        this.mGetUsingFGSTypeRunnable = new Runnable() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ForegroundServiceMgr.this.lambda$new$0();
            }
        };
    }

    public static ForegroundServiceMgr getInstance() {
        return ForegroundServiceMgrHolder.INSTANCE;
    }

    public void init() {
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mActivityManagerInternal = activityManagerInternal;
        if (activityManagerInternal != null) {
            activityManagerInternal.addForegroundServiceStateListener(this);
            this.mActivityManagerInternal.registerProcessObserver(this.mProcessObserver);
        } else {
            Slog.i(TAG, "AMI is null");
        }
    }

    public ConcurrentHashMap getMap() {
        return this.mMapFGSRecord;
    }

    public void clearMap() {
        this.mMapFGSRecord.clear();
    }

    public void onForegroundServiceStateChanged(String str, int i, int i2, boolean z) {
        if (isFGSTarget(str)) {
            ForegroundServiceRecord foregroundServiceRecord = (ForegroundServiceRecord) this.mMapFGSRecord.get(Integer.valueOf(i));
            if (foregroundServiceRecord == null) {
                foregroundServiceRecord = new ForegroundServiceRecord(str, i);
                this.mMapFGSRecord.put(Integer.valueOf(i), foregroundServiceRecord);
            }
            if (z) {
                foregroundServiceRecord.setFGSStartTime(System.currentTimeMillis());
            } else {
                foregroundServiceRecord.setFGSEndTime(System.currentTimeMillis());
                this.getAccesesTimeHelper.getAllAccessTimeOfPackage(i, str);
            }
        }
    }

    public class GetAccesesTimeHelper {
        public IAppOpsService mAppOpsService;
        public CustomVisitor visitor = new CustomVisitor();

        public GetAccesesTimeHelper() {
        }

        public void getAllAccessTimeOfPackage(int i, String str) {
            this.mAppOpsService = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
            this.visitor.setTarget(i, str);
            IAppOpsService iAppOpsService = this.mAppOpsService;
            if (iAppOpsService != null) {
                try {
                    iAppOpsService.getHistoricalOps(i, str, (String) null, (List) null, 3, 3, 0L, Long.MAX_VALUE, 31, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr$GetAccesesTimeHelper$$ExternalSyntheticLambda0
                        public final void onResult(Bundle bundle) {
                            ForegroundServiceMgr.GetAccesesTimeHelper.this.lambda$getAllAccessTimeOfPackage$0(bundle);
                        }
                    }));
                    MARsHandler.getInstance().getHandler().postDelayed(ForegroundServiceMgr.this.mGetUsingFGSTypeRunnable, 1000L);
                } catch (Exception e) {
                    Slog.d(ForegroundServiceMgr.TAG, "exception", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getAllAccessTimeOfPackage$0(Bundle bundle) {
            try {
                ((AppOpsManager.HistoricalOps) bundle.getParcelable("historical_ops", AppOpsManager.HistoricalOps.class)).accept(this.visitor);
            } catch (Exception e) {
                Slog.d(ForegroundServiceMgr.TAG, "exception", e);
            }
        }

        public void checkAllOp(CustomVisitor customVisitor) {
            try {
                for (String str : customVisitor.accessTimePerOp.keySet()) {
                    Slog.d(ForegroundServiceMgr.TAG, "op [" + str + "] access time");
                    getAccessTimeOfOp(str, customVisitor.filterUid);
                }
            } catch (Exception e) {
                Slog.d(ForegroundServiceMgr.TAG, "exception", e);
            }
        }

        public void getAccessTimeOfOp(String str, int i) {
            ForegroundServiceRecord foregroundServiceRecord = (ForegroundServiceRecord) ForegroundServiceMgr.this.mMapFGSRecord.get(Integer.valueOf(i));
            ArrayList arrayList = (ArrayList) this.visitor.accessTimePerOp.get(str);
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            long longValue = ((Long) arrayList.get(arrayList.size() - 1)).longValue();
            Slog.d(ForegroundServiceMgr.TAG, "access at " + longValue + ", " + ForegroundServiceMgr.this.formatDateTime(longValue));
            if (foregroundServiceRecord == null || !ForegroundServiceMgr.this.isInDuration(longValue, foregroundServiceRecord.getFGSStartTime(), foregroundServiceRecord.getFGSEndTime())) {
                return;
            }
            foregroundServiceRecord.setUsingFGSType(ForegroundServiceMgr.this.convertFGSType(str));
        }

        public class CustomVisitor implements AppOpsManager.HistoricalOpsVisitor {
            public String curPackageName;
            public String filterPackageName;
            public int filterUid = 0;
            public int curUid = 0;
            public ArrayMap accessTimePerOp = new ArrayMap();

            public void visitHistoricalOps(AppOpsManager.HistoricalOps historicalOps) {
            }

            public CustomVisitor() {
            }

            public void setTarget(int i, String str) {
                this.filterUid = i;
                this.filterPackageName = str;
                this.accessTimePerOp.clear();
            }

            public void visitHistoricalUidOps(AppOpsManager.HistoricalUidOps historicalUidOps) {
                this.curUid = historicalUidOps.getUid();
            }

            public void visitHistoricalPackageOps(AppOpsManager.HistoricalPackageOps historicalPackageOps) {
                if (this.curUid != this.filterUid) {
                    return;
                }
                this.curPackageName = historicalPackageOps.getPackageName();
            }

            public void visitHistoricalAttributionOps(AppOpsManager.AttributedHistoricalOps attributedHistoricalOps) {
                this.curPackageName.equals(this.filterPackageName);
            }

            public void visitHistoricalOp(AppOpsManager.HistoricalOp historicalOp) {
                ArrayList arrayList = (ArrayList) this.accessTimePerOp.computeIfAbsent(historicalOp.getOpName(), new Function() { // from class: com.android.server.am.mars.util.ForegroundServiceMgr$GetAccesesTimeHelper$CustomVisitor$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        ArrayList lambda$visitHistoricalOp$0;
                        lambda$visitHistoricalOp$0 = ForegroundServiceMgr.GetAccesesTimeHelper.CustomVisitor.lambda$visitHistoricalOp$0((String) obj);
                        return lambda$visitHistoricalOp$0;
                    }
                });
                int discreteAccessCount = historicalOp.getDiscreteAccessCount();
                if (discreteAccessCount > 0) {
                    arrayList.add(Long.valueOf(historicalOp.getDiscreteAccessAt(discreteAccessCount - 1).getLastAccessTime(31)));
                }
            }

            public static /* synthetic */ ArrayList lambda$visitHistoricalOp$0(String str) {
                return new ArrayList();
            }
        }
    }

    public final String formatDateTime(long j) {
        if (j == 0) {
            return String.format("%16s", "null");
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(j));
    }

    public final int convertFGSType(String str) {
        if (str == null) {
            return 0;
        }
        if (str.contains("location")) {
            return 8;
        }
        if (str.contains("record")) {
            return 128;
        }
        if (str.contains("audio")) {
            return 2;
        }
        return str.contains("camera") ? 64 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        GetAccesesTimeHelper getAccesesTimeHelper = this.getAccesesTimeHelper;
        getAccesesTimeHelper.checkAllOp(getAccesesTimeHelper.visitor);
    }

    public final boolean isFGSTarget(String str) {
        int i;
        try {
            i = AppGlobals.getPackageManager().getTargetSdkVersion(str);
        } catch (RemoteException e) {
            Slog.e(TAG, "getPackageManager() failed " + e);
            i = 0;
        }
        return i >= 34;
    }

    public final boolean isInDuration(long j, long j2, long j3) {
        long roundDownToMinute = roundDownToMinute(j);
        long roundDownToMinute2 = roundDownToMinute(j2);
        long roundDownToMinute3 = roundDownToMinute(j3);
        return roundDownToMinute2 > 0 && roundDownToMinute3 >= roundDownToMinute2 && roundDownToMinute2 <= roundDownToMinute && roundDownToMinute <= roundDownToMinute3;
    }

    public final long roundDownToMinute(long j) {
        if (j != 0) {
            return (j / 1000) / 60;
        }
        return 0L;
    }
}
