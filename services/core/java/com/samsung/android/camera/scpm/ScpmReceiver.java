package com.samsung.android.camera.scpm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.ICameraService;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.util.Base64;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;

import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ScpmReceiver extends BroadcastReceiver implements Handler.Callback {
    public static final boolean DEBUG;
    public final CameraServiceWorker mCameraServiceWorker;
    public final Context mContext;
    public final Handler mHandler;
    public final boolean mIsUnihalSupport =
            SemSystemProperties.getBoolean("vendor.camera.unihal.enable", false);
    public final AnonymousClass1 mScpmCallback;
    public ScpmHelper mScpmHelper;
    public final ScpmListManager mScpmListManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.camera.scpm.ScpmReceiver$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}

        public final void onListReceived(
                ScpmList.PolicyType policyType, ParcelFileDescriptor parcelFileDescriptor) {
            Message message = new Message();
            message.what = 4;
            message.arg1 = policyType.ordinal();
            message.obj = parcelFileDescriptor;
            ScpmReceiver scpmReceiver = ScpmReceiver.this;
            scpmReceiver.mHandler.sendMessage(message);
            Message message2 = new Message();
            message2.what = 1;
            message2.arg1 = 30;
            message2.obj = policyType;
            scpmReceiver.mHandler.sendMessage(message2);
        }

        public final void onRegistered(int i) {
            ScpmReceiver scpmReceiver = ScpmReceiver.this;
            if (i == 1) {
                scpmReceiver.mHandler.sendEmptyMessage(3);
                return;
            }
            if (i == 2) {
                Slog.v("CameraService/ScpmReceiver", "MSG_SCPM_UNAVAILABLE - retry after 5 min");
                scpmReceiver.mHandler.removeMessages(2);
                scpmReceiver.mHandler.sendEmptyMessageDelayed(2, 300000L);
            } else {
                if (i != 3) {
                    Slog.e("CameraService/ScpmReceiver", "onRegistered - not acceptable result.");
                    return;
                }
                Slog.e("CameraService/ScpmReceiver", "MSG_REGISTER_FAILED - retry after 1 hour");
                scpmReceiver.mHandler.removeMessages(2);
                scpmReceiver.mHandler.sendEmptyMessageDelayed(
                        2, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
            }
        }
    }

    static {
        String str = Build.TYPE;
        DEBUG = str.equals("eng") || str.equals("userdebug");
    }

    public ScpmReceiver(CameraServiceWorker cameraServiceWorker, Context context, Looper looper) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mScpmCallback = anonymousClass1;
        this.mCameraServiceWorker = cameraServiceWorker;
        this.mContext = context;
        Handler handler = new Handler(looper, this);
        this.mHandler = handler;
        this.mScpmListManager = new ScpmListManager();
        this.mScpmHelper = new ScpmHelper(context, anonymousClass1);
        context.registerReceiver(
                this,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.scpm.policy.UPDATE.camera3rdpartylist-1857",
                        KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA),
                null,
                handler,
                2);
    }

    public final synchronized void dump(PrintWriter printWriter) {
        String str;
        try {
            printWriter.println("\n\tDump of ScpmReceiver list");
            for (ScpmList.PolicyType policyType : ScpmList.PolicyType.values()) {
                printWriter.println("\n\tPolicy Type : " + policyType.name());
                StringBuilder sb = new StringBuilder();
                sb.append("\n\tCurrent list : version - ");
                ScpmListManager scpmListManager = this.mScpmListManager;
                synchronized (scpmListManager) {
                    str = scpmListManager.getCurrentPolicy(policyType).mVersion;
                }
                sb.append(str);
                printWriter.println(sb.toString());
                Iterator it =
                        ((CopyOnWriteArrayList)
                                        this.mScpmListManager.getCurrentPolicyList(policyType))
                                .iterator();
                while (it.hasNext()) {
                    PolicyListVO policyListVO = (PolicyListVO) it.next();
                    printWriter.println(
                            "\t\t"
                                    + Base64.encodeToString(
                                            policyListVO.packageName.getBytes(
                                                    StandardCharsets.UTF_8),
                                            2)
                                    + " arg1: "
                                    + policyListVO.value
                                    + " arg2: "
                                    + policyListVO.extra);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i != 1) {
            if (i == 2) {
                ScpmHelper scpmHelper = this.mScpmHelper;
                if (scpmHelper != null) {
                    Slog.i("CameraService/ScpmHelper", "registerScpm");
                    boolean z =
                            scpmHelper
                                            .mContext
                                            .getPackageManager()
                                            .resolveContentProvider(
                                                    KnoxCustomManagerService
                                                            .SPCM_PROVIDER_AUTHORITY,
                                                    PackageManager.ComponentInfoFlags.of(8L))
                                    != null;
                    AnonymousClass1 anonymousClass1 = scpmHelper.mScpmCallback;
                    if (z) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("packageName", "android");
                            bundle.putString("appId", "k0fpqpbykt");
                            bundle.putString("version", ScpmHelper.APP_VERSION);
                            bundle.putString("receiverPackageName", "android");
                            Bundle call =
                                    scpmHelper
                                            .mContext
                                            .getContentResolver()
                                            .call(
                                                    Uri.parse(
                                                            "content://com.samsung.android.scpm.policy/"),
                                                    "register",
                                                    "android",
                                                    bundle);
                            if (call != null) {
                                int i2 = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, -1);
                                scpmHelper.mToken =
                                        call.getString(
                                                KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                                int i3 =
                                        call.getInt(
                                                KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                                String string =
                                        call.getString(
                                                KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE,
                                                "");
                                if (i2 == 1) {
                                    Slog.v(
                                            "CameraService/ScpmHelper",
                                            "register success : rcode = "
                                                    + i3
                                                    + ", rmsg = "
                                                    + string);
                                    anonymousClass1.onRegistered(1);
                                } else {
                                    Slog.e(
                                            "CameraService/ScpmHelper",
                                            "failed to register: rcode = "
                                                    + i3
                                                    + ", rmsg = "
                                                    + string);
                                    anonymousClass1.onRegistered(3);
                                }
                            }
                        } catch (Exception e) {
                            Slog.e(
                                    "CameraService/ScpmHelper",
                                    "cannot register package : " + e.getMessage());
                            anonymousClass1.onRegistered(3);
                        }
                    } else {
                        Slog.i("CameraService/ScpmHelper", "registerScpm - SCPM is not available");
                        anonymousClass1.onRegistered(2);
                    }
                }
            } else if (i != 3) {
                if (i != 4) {
                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("SCPMReceiver error, invalid message: "),
                            message.what,
                            "CameraService/ScpmReceiver");
                } else if (this.mScpmListManager != null) {
                    Object obj = message.obj;
                    if (obj instanceof ParcelFileDescriptor) {
                        ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obj;
                        if (message.arg1 < ScpmList.PolicyType.values().length) {
                            ScpmListManager scpmListManager = this.mScpmListManager;
                            ScpmList.PolicyType policyType =
                                    ScpmList.PolicyType.values()[message.arg1];
                            synchronized (scpmListManager) {
                                Slog.v(
                                        "CameraService/ScpmListManager",
                                        "updatePolicy " + policyType);
                                try {
                                    scpmListManager.parseAndUpdateData(
                                            ScpmListManager.getJsonObject(parcelFileDescriptor),
                                            scpmListManager.getCurrentPolicy(policyType));
                                } catch (Exception e2) {
                                    Slog.e("CameraService/ScpmListManager", "updatePolicy " + e2);
                                    scpmListManager.loadDefaultScpmList(policyType);
                                }
                            }
                        }
                    }
                }
            } else if (this.mScpmHelper != null) {
                Iterator it = this.mScpmListManager.mCurrentPolicyList.iterator();
                while (it.hasNext()) {
                    ScpmList scpmList = (ScpmList) it.next();
                    ScpmHelper scpmHelper2 = this.mScpmHelper;
                    if (scpmHelper2
                                    .mContext
                                    .getPackageManager()
                                    .resolveContentProvider(
                                            KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY,
                                            PackageManager.ComponentInfoFlags.of(8L))
                            != null) {
                        Uri parse =
                                Uri.parse(
                                        "content://com.samsung.android.scpm.policy/"
                                                + scpmHelper2.mToken
                                                + "/"
                                                + scpmList.mConfigurationName);
                        if (ScpmHelper.DEBUG) {
                            Slog.v("CameraService/ScpmHelper", "uri: " + parse);
                        }
                        try {
                            ParcelFileDescriptor openFileDescriptor =
                                    scpmHelper2
                                            .mContext
                                            .getContentResolver()
                                            .openFileDescriptor(parse, "r");
                            if (openFileDescriptor == null) {
                                try {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString(
                                            KnoxCustomManagerService.SPCM_KEY_TOKEN,
                                            scpmHelper2.mToken);
                                    Bundle call2 =
                                            scpmHelper2
                                                    .mContext
                                                    .getContentResolver()
                                                    .call(
                                                            parse,
                                                            "getLastError",
                                                            "android",
                                                            bundle2);
                                    Slog.d(
                                            "CameraService/ScpmHelper",
                                            "cannot get new policy : "
                                                    + call2.getInt(
                                                            KnoxCustomManagerService
                                                                    .SPCM_KEY_RESULT_CODE)
                                                    + ", "
                                                    + call2.getString(
                                                            KnoxCustomManagerService
                                                                    .SPCM_KEY_RESULT_MESSAGE));
                                } catch (Throwable th) {
                                    if (openFileDescriptor != null) {
                                        try {
                                            openFileDescriptor.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    }
                                    throw th;
                                }
                            } else {
                                scpmHelper2.mScpmCallback.onListReceived(
                                        scpmList.mType, openFileDescriptor.dup());
                            }
                            if (openFileDescriptor != null) {
                                openFileDescriptor.close();
                            }
                        } catch (FileNotFoundException e3) {
                            Slog.e("CameraService/ScpmHelper", "File not found!");
                            e3.printStackTrace();
                        } catch (Exception e4) {
                            Slog.e(
                                    "CameraService/ScpmHelper",
                                    "getSCPMPolicy fail because of exception! " + e4);
                            e4.printStackTrace();
                        }
                    } else {
                        Slog.i("CameraService/ScpmHelper", "getSCPMPolicy - SCPM is not available");
                    }
                }
            }
        } else {
            Object obj2 = message.obj;
            if (obj2 instanceof ScpmList.PolicyType) {
                notifyParamChangeRetryLocked(message.arg1, (ScpmList.PolicyType) obj2);
            }
        }
        return true;
    }

    public final boolean notifyParamChange(ScpmList.PolicyType policyType) {
        String str;
        ICameraService cameraService = this.mCameraServiceWorker.getCameraService();
        if (cameraService == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("notifyParamChange : ");
        sb.append(policyType);
        sb.append(", mIsUnihalSupport = ");
        HeimdAllFsService$$ExternalSyntheticOutline0.m(
                "CameraService/ScpmReceiver", sb, this.mIsUnihalSupport);
        List currentPolicyList = this.mScpmListManager.getCurrentPolicyList(policyType);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) currentPolicyList;
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                PolicyListVO policyListVO = (PolicyListVO) it.next();
                String str2 = policyListVO.packageName;
                String str3 = policyListVO.extra;
                arrayList.add(str2);
                StringBuilder sb2 = new StringBuilder("pkgNameHint=" + policyListVO.value + ";");
                if (this.mIsUnihalSupport && ScpmList.PolicyType.CAMERA_3RD_PARTY == policyType) {
                    ScpmListManager scpmListManager = this.mScpmListManager;
                    synchronized (scpmListManager) {
                        str = scpmListManager.getCurrentPolicy(policyType).mVersion;
                    }
                    if (!str.equals(this.mScpmListManager.getDefaultVersion(policyType))) {
                        sb2.append("downloaded=true;");
                    }
                }
                if (!str3.isEmpty()) {
                    sb2.append("extra=" + str3 + ";");
                }
                arrayList2.add(sb2.toString());
                if (DEBUG) {
                    Slog.i("CameraService/ScpmReceiver", "notifyParamChange : " + ((Object) sb2));
                }
            }
            cameraService.notifyPkgListParamChange(
                    policyType.ordinal(),
                    (String[]) arrayList.toArray(new String[0]),
                    (String[]) arrayList2.toArray(new String[0]));
            Slog.i(
                    "CameraService/ScpmReceiver",
                    "notifyParamChange : size is " + copyOnWriteArrayList.size());
            return true;
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m(
                    "Could not notify vision param change, remote exception: ",
                    e,
                    "CameraService/ScpmReceiver");
            return false;
        } catch (IllegalArgumentException e2) {
            Slog.e("CameraService/ScpmReceiver", "Could not parse package name " + e2);
            return false;
        } catch (Exception e3) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e3, "Unknown exception occur ", "CameraService/ScpmReceiver");
            return false;
        }
    }

    public final synchronized void notifyParamChangeRetryLocked(
            int i, ScpmList.PolicyType policyType) {
        if (notifyParamChange(policyType)) {
            i = 0;
        }
        if (i <= 0) {
            return;
        }
        Slog.i(
                "CameraService/ScpmReceiver",
                "Could not notify camera service of device state change, retrying...");
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1, i - 1, 0, policyType), 20L);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.samsung.android.scpm.policy.UPDATE.camera3rdpartylist-1857".equals(action)) {
            Slog.d("CameraService/ScpmReceiver", "SCPM update broadcast received!");
            this.mHandler.sendEmptyMessage(3);
        } else if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
            Slog.d(
                    "CameraService/ScpmReceiver",
                    "SCPM clear broadcast received, policy updated 1 min later!");
            this.mScpmHelper = new ScpmHelper(this.mContext, this.mScpmCallback);
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageDelayed(2, 60000L);
        }
    }
}
