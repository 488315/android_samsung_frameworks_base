package com.samsung.android.knox.foresight;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.foresight.framework.system.IKFCommnadService;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KnoxForesight {
    public static final String ERROR_DOWNLOAD = "ERROR_DOWNLOAD";
    public static final String ERROR_INSTALL = "ERROR_INSTALL";
    public static final String ERROR_VERSION = "ERROR_VERSION";
    public static final String SUCCESS = "SUCCESS";
    public static ArrayList<String> commandList = new ArrayList<>();
    public String FS_APP_NAME;
    public String TAG;
    public ServiceConnection connection;
    public ComponentName eventReceiver;
    public BroadcastReceiver fsEventReceiver;
    public BroadcastReceiver fsReturnReceiver;
    public IKFCommnadService iBinder;
    public KnoxForesightCallback kfCallback;
    public Context mContext;
    public PackageManager packagemanager;

    public KnoxForesight(Context context, KnoxForesightCallback knoxForesightCallback) {
        this(context, knoxForesightCallback, null);
    }

    public void notifyCallbacks(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("notifyt : ", str, this.TAG);
        this.kfCallback.notify(str);
    }

    public String sendCommand(String str) {
        return sendCommand(str, false);
    }

    public boolean sendCommandAsync(String str) {
        try {
            String sendCommand = sendCommand(str, true);
            Log.d(this.TAG, "sendCommandAsync. msg : " + sendCommand + " / This message will be forwarded to the callback.");
            return true;
        } catch (Exception e) {
            Log.d(this.TAG, "sendCommandAsync. error");
            e.printStackTrace();
            return false;
        }
    }

    public final void sendLastCommand() {
        synchronized (commandList) {
            try {
                ArrayList<String> arrayList = commandList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    String str = arrayList.get(i);
                    i++;
                    String str2 = str;
                    Log.d(this.TAG, "sendLastCommand.... " + str2);
                    sendCommand(str2, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        commandList = new ArrayList<>();
    }

    public KnoxForesight(Context context, KnoxForesightCallback knoxForesightCallback, ComponentName componentName) {
        this.TAG = "KnoxForesight";
        this.FS_APP_NAME = "com.samsung.android.knox.foresight";
        this.connection = new ServiceConnection() { // from class: com.samsung.android.knox.foresight.KnoxForesight.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName2, IBinder iBinder) {
                Log.d(KnoxForesight.this.TAG, "Service connected!! : " + componentName2.toString());
                KnoxForesight.this.iBinder = IKFCommnadService.Stub.asInterface(iBinder);
                KnoxForesight.this.sendLastCommand();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName2) {
                Log.d(KnoxForesight.this.TAG, "Service disconnected!! : " + componentName2.toString());
            }
        };
        this.fsReturnReceiver = new BroadcastReceiver() { // from class: com.samsung.android.knox.foresight.KnoxForesight.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String stringExtra = intent.getStringExtra("error");
                if (stringExtra == null || !stringExtra.contains(KnoxForesight.SUCCESS)) {
                    Log.e(KnoxForesight.this.TAG, "Download failed. Reason : " + stringExtra);
                    KnoxForesight.this.notifyCallbacks(stringExtra);
                    return;
                }
                Intent intent2 = new Intent("com.samsung.android.knox.foresight.COMMAND");
                intent2.putExtra("eventReceiver", KnoxForesight.this.eventReceiver);
                intent2.setPackage("com.samsung.android.knox.foresight");
                KnoxForesight knoxForesight = KnoxForesight.this;
                knoxForesight.mContext.bindService(intent2, knoxForesight.connection, 1);
                KnoxForesight.this.notifyCallbacks(stringExtra);
            }
        };
        this.fsEventReceiver = new BroadcastReceiver() { // from class: com.samsung.android.knox.foresight.KnoxForesight.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Log.d(KnoxForesight.this.TAG, "Event intent comes");
                KnoxForesight.this.notifyCallbacks(intent.getStringExtra(IMSParameter.CALL.EVENT));
            }
        };
        this.mContext = context;
        this.kfCallback = knoxForesightCallback;
        this.eventReceiver = componentName;
        this.packagemanager = context.getPackageManager();
        this.mContext.registerReceiver(this.fsReturnReceiver, new IntentFilter("com.samsung.android.knox.containercore.action.FORESIGHT_RETURN"));
        this.mContext.registerReceiver(this.fsEventReceiver, new IntentFilter("com.samsung.android.knox.containercore.action.FORESIGHT_EVENT"));
        Intent intent = new Intent("com.samsung.android.knox.foresight.COMMAND");
        intent.putExtra("eventReceiver", componentName);
        intent.setPackage("com.samsung.android.knox.foresight");
        this.mContext.bindService(intent, this.connection, 1);
    }

    public String sendCommand(String str, boolean z) {
        Log.d(this.TAG, "sendCommand. received msg is : " + str + " callback? " + z);
        List<ApplicationInfo> installedApplications = this.packagemanager.getInstalledApplications(0);
        for (int i = 0; i < installedApplications.size(); i++) {
            if (this.FS_APP_NAME.equals(installedApplications.get(i).packageName)) {
                try {
                    Log.d(this.TAG, "send command.... " + str);
                    String SendCommand = this.iBinder.SendCommand(str);
                    if (z) {
                        Intent intent = new Intent("com.samsung.android.knox.containercore.action.FORESIGHT_RETURN");
                        intent.putExtra("error", SendCommand);
                        this.fsReturnReceiver.onReceive(null, intent);
                    }
                    return SendCommand;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (commandList) {
            commandList.add(str);
        }
        Log.d(this.TAG, "Requested app donwload");
        Intent intent2 = new Intent("com.samsung.android.knox.containercore.action.FORESIGHT_COMMAND");
        intent2.setClassName("com.samsung.android.knox.containercore", "com.samsung.android.knox.containercore.KnoxForesightCommandReceiver");
        ((SemPersonaManager) this.mContext.getSystemService("persona")).sendKnoxForesightBroadcast(intent2);
        return "app_download";
    }
}
