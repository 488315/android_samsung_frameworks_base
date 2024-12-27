package com.android.server.devicepolicy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UCMProvisioningHelper {
    public static Bundle ucmProfile;
    public final AnonymousClass3 connection = new AnonymousClass3();
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.devicepolicy.UCMProvisioningHelper$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UCMProvisioningHelper this$0;
        public final /* synthetic */ int[] val$errorCode;
        public final /* synthetic */ boolean[] val$finished;
        public final /* synthetic */ Object val$mutex;
        public final /* synthetic */ boolean[] val$success;

        public /* synthetic */ AnonymousClass1(
                UCMProvisioningHelper uCMProvisioningHelper,
                boolean[] zArr,
                int[] iArr,
                Object obj,
                boolean[] zArr2,
                int i) {
            this.$r8$classId = i;
            this.this$0 = uCMProvisioningHelper;
            this.val$success = zArr;
            this.val$errorCode = iArr;
            this.val$mutex = obj;
            this.val$finished = zArr2;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "UCM Managed Profile Started Service onReceived is called: ",
                            action,
                            "UCMProvisioningHelper");
                    this.this$0.mContext.unregisterReceiver(this);
                    UCMProvisioningHelper uCMProvisioningHelper = this.this$0;
                    uCMProvisioningHelper.mContext.unbindService(uCMProvisioningHelper.connection);
                    if ("com.samsung.android.knox.ucm.action.UCM_STARTED_PROVISIONING_SUCCESS"
                            .equals(action)) {
                        this.val$success[0] = true;
                    } else {
                        this.val$success[0] = false;
                        this.val$errorCode[0] = intent.getIntExtra("ERROR_CODE", 5);
                    }
                    synchronized (this.val$mutex) {
                        this.val$finished[0] = true;
                        this.val$mutex.notify();
                    }
                    return;
                default:
                    String action2 = intent.getAction();
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "UCM Managed Profile Completed Service onReceived is called: ",
                            action2,
                            "UCMProvisioningHelper");
                    this.this$0.mContext.unregisterReceiver(this);
                    UCMProvisioningHelper uCMProvisioningHelper2 = this.this$0;
                    uCMProvisioningHelper2.mContext.unbindService(
                            uCMProvisioningHelper2.connection);
                    if ("com.samsung.android.knox.ucm.action.UCM_COMPLETED_PROVISIONING_SUCCESS"
                            .equals(action2)) {
                        this.val$success[0] = true;
                        Bundle bundle = UCMProvisioningHelper.ucmProfile;
                        if (bundle == null || !bundle.getBoolean("ucm-config")) {
                            StringBuilder sb = new StringBuilder("resetUCMProfile: ");
                            SemPersonaManager semPersonaManager =
                                    (SemPersonaManager) context.getSystemService("persona");
                            sb.append(
                                    (semPersonaManager != null
                                                    ? Integer.valueOf(
                                                            semPersonaManager.resetUCMProfile())
                                                    : null)
                                            .intValue());
                            Log.i("UCMProvisioningHelper", sb.toString());
                        }
                    } else {
                        this.val$success[0] = false;
                        this.val$errorCode[0] = intent.getIntExtra("ERROR_CODE", 5);
                    }
                    synchronized (this.val$mutex) {
                        this.val$finished[0] = true;
                        this.val$mutex.notify();
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.devicepolicy.UCMProvisioningHelper$3, reason: invalid class name */
    public final class AnonymousClass3 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("UCMProvisioningHelper", "Service connected!! : " + componentName.toString());
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("UCMProvisioningHelper", "Service disconnected!! : " + componentName.toString());
        }
    }

    public UCMProvisioningHelper(Context context) {
        this.mContext = context;
    }

    public static Bundle getUCMConfigParams(PersistableBundle persistableBundle) {
        String str;
        if (persistableBundle != null && persistableBundle.getBoolean("ucm-config")) {
            return new Bundle(persistableBundle).deepCopy();
        }
        if (ucmProfile == null) {
            str = "getUCMProfile received null";
        } else {
            str = "getUCMProfile received non-null: " + ucmProfile.toString();
        }
        Log.d("UCMProvisioningHelper", str);
        Bundle bundle = ucmProfile;
        if (bundle == null || !bundle.getBoolean("ucm-config")) {
            return null;
        }
        String string = ucmProfile.getString("ucm-config-client-package");
        String string2 = ucmProfile.getString("ucm-config-client-signature");
        String string3 = ucmProfile.getString("ucm-config-client-location");
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("ucm-config", true);
        bundle2.putString("ucm-config-client-package", string);
        bundle2.putString("ucm-config-client-signature", string2);
        bundle2.putString("ucm-config-client-location", string3);
        return bundle2;
    }

    public static boolean isUCMConfigured(Context context, PersistableBundle persistableBundle) {
        if (persistableBundle != null && persistableBundle.getBoolean("ucm-config")) {
            Log.d("UCMProvisioningHelper", "isUCMConfigured from provisioning params");
            return true;
        }
        SemPersonaManager semPersonaManager =
                (SemPersonaManager) context.getSystemService("persona");
        Bundle uCMProfile = semPersonaManager != null ? semPersonaManager.getUCMProfile() : null;
        ucmProfile = uCMProfile;
        if (uCMProfile == null || !uCMProfile.getBoolean("ucm-config")) {
            return false;
        }
        Log.d("UCMProvisioningHelper", "isUCMConfigured from preset params");
        return true;
    }
}
