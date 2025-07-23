package com.samsung.android.jdsms;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import com.samsung.android.dsms.aidl.IDsmsService;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class DsmsService extends IDsmsService.Stub {
    private static final long APK_TIMEOUT = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES);
    public static final String DSMS_SERVICE = "dsms";
    private static final String SUBTAG = "[Service] ";
    private final Context mContext;
    private Timer mTimerApkTimeout = new Timer();

    public DsmsService(Context context) {
        this.mContext = context;
        if (DsmsLog.isDebuggable()) {
            DsmsLog.d("[Service] Created for context: " + context);
            int callingUid = Binder.getCallingUid();
            DsmsLog.d("[Service] Binder.callingUid=[" + callingUid + NavigationBarInflaterView.SIZE_MOD_END);
            DsmsLog.d("[Service] Binder.callingPid=[" + Binder.getCallingPid() + NavigationBarInflaterView.SIZE_MOD_END);
            if (context != null) {
                DsmsLog.d("[Service] context.packageName=[" + context.getPackageName() + NavigationBarInflaterView.SIZE_MOD_END);
                DsmsLog.d("[Service] context.packageManager.nameForUid=[" + context.getPackageManager().getNameForUid(callingUid) + NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        DsmsInfoCache.getInstance().setContext(context);
        this.mTimerApkTimeout.schedule(new TimerTask(this) { // from class: com.samsung.android.jdsms.DsmsService.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                DsmsInfoCache.getInstance().updateCommercializedDeviceCache();
                DsmsThreadPoolExecutor.getInstance().resume();
            }
        }, APK_TIMEOUT);
    }

    @Override // com.samsung.android.dsms.aidl.IDsmsService
    public void sendMessage(String str, String str2, long j) {
        DsmsLog.d("[Service] Sending message featureCode=[" + str + "] detail=[" + str2 + "] value=[" + j + NavigationBarInflaterView.SIZE_MOD_END);
        if (!PolicyEnforcer.isAValidUser(this.mContext)) {
            DsmsLog.e("[Service] Unauthorized call");
            return;
        }
        try {
            if (new Sender(this.mContext).send(str, str2, j) < 0) {
                DsmsLog.e("[Service] Failed to send message");
            }
        } catch (IllegalArgumentException e) {
            DsmsLog.e(SUBTAG + e.getMessage());
        }
    }
}
