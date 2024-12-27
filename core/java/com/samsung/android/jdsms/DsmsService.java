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
            DsmsLog.d("[Service] Created for context: " + this.mContext);
            int uid = Binder.getCallingUid();
            DsmsLog.d(
                    "[Service] Binder.callingUid=[" + uid + NavigationBarInflaterView.SIZE_MOD_END);
            DsmsLog.d(
                    "[Service] Binder.callingPid=["
                            + Binder.getCallingPid()
                            + NavigationBarInflaterView.SIZE_MOD_END);
            if (this.mContext != null) {
                DsmsLog.d(
                        "[Service] context.packageName=["
                                + this.mContext.getPackageName()
                                + NavigationBarInflaterView.SIZE_MOD_END);
                DsmsLog.d(
                        "[Service] context.packageManager.nameForUid=["
                                + this.mContext.getPackageManager().getNameForUid(uid)
                                + NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        DsmsInfoCache.getInstance().setContext(this.mContext);
        this.mTimerApkTimeout.schedule(
                new TimerTask() { // from class: com.samsung.android.jdsms.DsmsService.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        DsmsInfoCache.getInstance().updateCommercializedDeviceCache();
                        DsmsThreadPoolExecutor.getInstance().resume();
                    }
                },
                APK_TIMEOUT);
    }

    @Override // com.samsung.android.dsms.aidl.IDsmsService
    public void sendMessage(String featureCode, String detail, long value) {
        DsmsLog.d(
                "[Service] Sending message featureCode=["
                        + featureCode
                        + "] detail=["
                        + detail
                        + "] value=["
                        + value
                        + NavigationBarInflaterView.SIZE_MOD_END);
        if (!PolicyEnforcer.isAValidUser(this.mContext)) {
            DsmsLog.e("[Service] Unauthorized call");
            return;
        }
        try {
            Sender sender = new Sender(this.mContext);
            if (sender.send(featureCode, detail, value) < 0) {
                DsmsLog.e("[Service] Failed to send message");
            }
        } catch (IllegalArgumentException exception) {
            DsmsLog.e(SUBTAG + exception.getMessage());
        }
    }
}
