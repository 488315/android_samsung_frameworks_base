package com.samsung.android.camera.scpm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.ICameraService;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.util.Slog;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.clipboard.ClipboardService;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.camera.scpm.ScpmHelper;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ScpmReceiver extends BroadcastReceiver implements Handler.Callback {
    public static final boolean DEBUG;
    public CameraServiceWorker mCameraServiceWorker;
    public final Context mContext;
    public final Handler mHandler;
    public ScpmHelper mScpmHelper;
    public boolean mIsUnihalSupport = SemSystemProperties.getBoolean("vendor.camera.unihal.enable", false);
    public ScpmHelper.scpmCallback mScpmCallback = new ScpmHelper.scpmCallback() { // from class: com.samsung.android.camera.scpm.ScpmReceiver.1
        @Override // com.samsung.android.camera.scpm.ScpmHelper.scpmCallback
        public void onListReceived(String str, List list) {
            Message message = new Message();
            message.what = 4;
            message.arg1 = Integer.valueOf(str).intValue();
            message.obj = list;
            ScpmReceiver.this.mHandler.sendMessage(message);
            ScpmReceiver.this.mHandler.sendEmptyMessage(1);
        }

        @Override // com.samsung.android.camera.scpm.ScpmHelper.scpmCallback
        public void onRegistered(int i) {
            if (i == 1) {
                ScpmReceiver.this.mHandler.sendEmptyMessage(3);
                return;
            }
            if (i == 2) {
                Slog.v("CameraService/ScpmReceiver", "MSG_SCPM_UNAVAILABLE - retry after 5 min");
                ScpmReceiver.this.mHandler.removeMessages(2);
                ScpmReceiver.this.mHandler.sendEmptyMessageDelayed(2, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
            } else {
                if (i == 3) {
                    Slog.e("CameraService/ScpmReceiver", "MSG_REGISTER_FAILED - retry after 1 hour");
                    ScpmReceiver.this.mHandler.removeMessages(2);
                    ScpmReceiver.this.mHandler.sendEmptyMessageDelayed(2, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                    return;
                }
                Slog.e("CameraService/ScpmReceiver", "onRegistered - not acceptable result.");
            }
        }
    };
    public ScpmListManager mScpmListManager = new ScpmListManager();

    static {
        DEBUG = !Build.TYPE.equals("user") || Debug.semIsProductDev();
    }

    public ScpmReceiver(CameraServiceWorker cameraServiceWorker, Context context, Looper looper) {
        this.mCameraServiceWorker = cameraServiceWorker;
        this.mContext = context;
        this.mHandler = new Handler(looper, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.camera3rdpartylist-1857");
        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        context.registerReceiver(this, intentFilter);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            notifyParamChangeRetryLocked(message.arg1);
        } else if (i == 2) {
            ScpmHelper scpmHelper = this.mScpmHelper;
            if (scpmHelper != null) {
                scpmHelper.registerScpm();
            }
        } else if (i == 3) {
            ScpmHelper scpmHelper2 = this.mScpmHelper;
            if (scpmHelper2 != null) {
                scpmHelper2.getSCPMParameters();
            }
        } else if (i == 4) {
            ScpmListManager scpmListManager = this.mScpmListManager;
            if (scpmListManager != null) {
                scpmListManager.saveDataToFile(message.arg1, (List) message.obj);
            }
        } else {
            Slog.e("CameraService/ScpmReceiver", "SCPMReceiver error, invalid message: " + message.what);
        }
        return true;
    }

    public void initialize() {
        Slog.d("CameraService/ScpmReceiver", "initialize");
        this.mScpmListManager.loadListFromFile();
        notifyParamChangeRetry();
        this.mScpmHelper = new ScpmHelper(this.mContext, this.mScpmCallback);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.samsung.android.scpm.policy.UPDATE.camera3rdpartylist-1857".equals(action)) {
            Slog.d("CameraService/ScpmReceiver", "SCPM update broadcast received!");
            this.mHandler.sendEmptyMessage(3);
        } else if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
            Slog.d("CameraService/ScpmReceiver", "SCPM clear broadcast received, policy updated 1 min later!");
            this.mScpmHelper = new ScpmHelper(this.mContext, this.mScpmCallback);
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageDelayed(2, 60000L);
        }
    }

    public final void notifyParamChangeRetry() {
        notifyParamChangeRetryLocked(30);
    }

    public final synchronized void notifyParamChangeRetryLocked(int i) {
        if (notifyParamChange()) {
            i = 0;
        }
        if (i <= 0) {
            return;
        }
        Slog.i("CameraService/ScpmReceiver", "Could not notify camera service of device state change, retrying...");
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1, i - 1, 0, null), 20L);
    }

    public final boolean notifyParamChange() {
        ICameraService cameraService = this.mCameraServiceWorker.getCameraService();
        if (cameraService == null) {
            return false;
        }
        Slog.i("CameraService/ScpmReceiver", "mIsUnihalSupport = " + this.mIsUnihalSupport);
        List scpmList = this.mScpmListManager.getScpmList();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            for (PolicyListVO policyListVO : this.mScpmListManager.getScpmList()) {
                arrayList.add(policyListVO.packageName);
                StringBuilder sb = new StringBuilder("pkgNameHint=" + policyListVO.value + KnoxVpnFirewallHelper.DELIMITER);
                if (this.mIsUnihalSupport) {
                    if (!policyListVO.disallowUnihalVersion.equals("0")) {
                        sb.append("disallowUnihalVersion=" + policyListVO.disallowUnihalVersion + KnoxVpnFirewallHelper.DELIMITER);
                    }
                    if (!this.mScpmListManager.getVersion().equals(this.mScpmListManager.getDefaultVersion())) {
                        sb.append("downloaded=true;");
                    }
                }
                arrayList2.add(sb.toString());
                if (DEBUG) {
                    Slog.i("CameraService/ScpmReceiver", "notifyParamChange : " + sb.toString());
                }
            }
            cameraService.notifyPkgListParamChange((String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]));
            Slog.i("CameraService/ScpmReceiver", "notifyParamChange : size is " + scpmList.size());
            return true;
        } catch (RemoteException e) {
            Slog.w("CameraService/ScpmReceiver", "Could not notify vision param change, remote exception: " + e);
            return false;
        } catch (IllegalArgumentException e2) {
            Slog.e("CameraService/ScpmReceiver", "Could not parse package name " + e2);
            return false;
        }
    }

    public void tryRegisterCameraOpenListener() {
        ((CameraManager) this.mContext.getSystemService("camera")).registerSemCameraDeviceStateCallback(new CameraManager.SemCameraDeviceStateCallback() { // from class: com.samsung.android.camera.scpm.ScpmReceiver.2
            public void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
                Long l = 0L;
                Iterator it = ScpmReceiver.this.mScpmListManager.getScpmList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PolicyListVO policyListVO = (PolicyListVO) it.next();
                    if (policyListVO.decodedName.equals(str2)) {
                        l = Long.valueOf(Long.parseLong(policyListVO.value));
                        break;
                    }
                }
                if (l.longValue() == 0) {
                    return;
                }
                if (i2 == 0) {
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.android.camera.action.camera_open");
                    intent.setPackage("com.sec.android.sdhms");
                    intent.putExtra("package_name", str2);
                    intent.putExtra("package_hint", l);
                    intent.putExtra("facing", i);
                    intent.putExtra("cameraId", str);
                    ScpmReceiver.this.mContext.sendBroadcast(intent);
                    if (ScpmReceiver.DEBUG) {
                        Slog.i("CameraService/ScpmReceiver", "Send Open Message to SDMHS");
                        return;
                    }
                    return;
                }
                if (i2 != 3) {
                    return;
                }
                Intent intent2 = new Intent();
                intent2.setAction("com.samsung.android.camera.action.camera_close");
                intent2.setPackage("com.sec.android.sdhms");
                intent2.putExtra("package_name", str2);
                intent2.putExtra("package_hint", l);
                intent2.putExtra("facing", i);
                intent2.putExtra("cameraId", str);
                ScpmReceiver.this.mContext.sendBroadcast(intent2);
                if (ScpmReceiver.DEBUG) {
                    Slog.i("CameraService/ScpmReceiver", "Send Close Message to SDMHS");
                }
            }
        }, this.mHandler);
    }

    public void tryRegisterSCPMServer() {
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessage(2);
    }

    public List getCoverFlexRotatePkgList() {
        return Collections.unmodifiableList(this.mScpmListManager.getCoverFlexRotatePkgList());
    }

    public synchronized void dump(PrintWriter printWriter) {
        printWriter.println("\n\tDump of ScpmReceiver list");
        printWriter.println("\n\tOriginal list : version - " + this.mScpmListManager.getDefaultVersion());
        for (PolicyListVO policyListVO : this.mScpmListManager.getDefaultScpmList()) {
            printWriter.println("\t\t" + policyListVO.packageName + " arg1: " + policyListVO.value + " arg2: " + policyListVO.disallowUnihalVersion);
        }
        printWriter.println("\n\tReceived list : version - " + this.mScpmListManager.getVersion());
        for (PolicyListVO policyListVO2 : this.mScpmListManager.getScpmList()) {
            printWriter.println("\t\t" + policyListVO2.packageName + " arg1: " + policyListVO2.value + " arg2: " + policyListVO2.disallowUnihalVersion);
        }
    }
}
