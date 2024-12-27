package com.android.server.devicepolicy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DualDarProvisioningHelper {
    public final AnonymousClass5 connection = new AnonymousClass5();
    public final Context mContext;
    public final UserManagerInternal mUserManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.devicepolicy.DualDarProvisioningHelper$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DualDarProvisioningHelper this$0;
        public final /* synthetic */ int[] val$errorCode;
        public final /* synthetic */ boolean[] val$finished;
        public final /* synthetic */ Object val$mutex;
        public final /* synthetic */ boolean[] val$success;

        public /* synthetic */ AnonymousClass1(
                DualDarProvisioningHelper dualDarProvisioningHelper,
                boolean[] zArr,
                int[] iArr,
                Object obj,
                boolean[] zArr2,
                int i) {
            this.$r8$classId = i;
            this.this$0 = dualDarProvisioningHelper;
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
                            "DualDAR Managed Profile Started Service onReceived is called: ",
                            action,
                            "DualDarProvisioningHelper");
                    this.this$0.mContext.unregisterReceiver(this);
                    DualDarProvisioningHelper dualDarProvisioningHelper = this.this$0;
                    dualDarProvisioningHelper.mContext.unbindService(
                            dualDarProvisioningHelper.connection);
                    if ("com.android.dualdar.started.provisioning_success".equals(action)) {
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
                case 1:
                    String action2 = intent.getAction();
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "DualDAR Managed Profile Completed Service onReceived is called: ",
                            action2,
                            "DualDarProvisioningHelper");
                    this.this$0.mContext.unregisterReceiver(this);
                    DualDarProvisioningHelper dualDarProvisioningHelper2 = this.this$0;
                    dualDarProvisioningHelper2.mContext.unbindService(
                            dualDarProvisioningHelper2.connection);
                    if ("com.android.dualdar.completed.provisioning_success".equals(action2)) {
                        this.val$success[0] = true;
                    } else if ("com.android.dualdar.completed.cancelled".equals(action2)) {
                        this.val$success[0] = true;
                        Log.d(
                                "DualDarProvisioningHelper",
                                "DualDAR is not enabled. dualdar completed provisinoing cacelled.");
                    } else {
                        this.val$success[0] = false;
                        this.val$errorCode[0] = intent.getIntExtra("ERROR_CODE", 5);
                    }
                    synchronized (this.val$mutex) {
                        this.val$finished[0] = true;
                        this.val$mutex.notify();
                    }
                    return;
                case 2:
                    String action3 = intent.getAction();
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "DualDAR Managed Device Started Service onReceived is called: ",
                            action3,
                            "DualDarProvisioningHelper");
                    this.this$0.mContext.unregisterReceiver(this);
                    DualDarProvisioningHelper dualDarProvisioningHelper3 = this.this$0;
                    dualDarProvisioningHelper3.mContext.unbindService(
                            dualDarProvisioningHelper3.connection);
                    if ("com.android.dualdar.started.provisioning_success".equals(action3)) {
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
                    String action4 = intent.getAction();
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "DualDAR Managed Device Completed Service onReceived is called: ",
                            action4,
                            "DualDarProvisioningHelper");
                    this.this$0.mContext.unregisterReceiver(this);
                    DualDarProvisioningHelper dualDarProvisioningHelper4 = this.this$0;
                    dualDarProvisioningHelper4.mContext.unbindService(
                            dualDarProvisioningHelper4.connection);
                    if ("com.android.dualdar.completed.provisioning_success".equals(action4)) {
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
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.devicepolicy.DualDarProvisioningHelper$5, reason: invalid class name */
    public final class AnonymousClass5 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("DualDarProvisioningHelper", "Service connected!! : " + componentName.toString());
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d(
                    "DualDarProvisioningHelper",
                    "Service disconnected!! : " + componentName.toString());
        }
    }

    public DualDarProvisioningHelper(Context context, UserManagerInternal userManagerInternal) {
        this.mContext = context;
        this.mUserManagerInternal = userManagerInternal;
    }

    public static Bundle getDualDARConfigParams(
            Context context, PersistableBundle persistableBundle) {
        if (persistableBundle != null && isDualDARIntentProvisioned(persistableBundle)) {
            return new Bundle(persistableBundle).deepCopy();
        }
        Bundle dualDARProfile = getDualDARProfile(context);
        if (dualDARProfile == null) {
            return null;
        }
        String string = dualDARProfile.getString("dualdar-config-client-package");
        String string2 = dualDARProfile.getString("dualdar-config-client-signature");
        String string3 = dualDARProfile.getString("dualdar-config-client-location");
        Bundle bundle = new Bundle();
        bundle.putBoolean("dualdar-config", true);
        bundle.putString("dualdar-config-client-package", "default".equals(string) ? null : string);
        if ("default".equals(string)) {
            string2 = null;
        }
        bundle.putString("dualdar-config-client-signature", string2);
        bundle.putString(
                "dualdar-config-client-location", "default".equals(string3) ? null : string3);
        return bundle;
    }

    public static Bundle getDualDARProfile(Context context) {
        SemPersonaManager semPersonaManager =
                (SemPersonaManager) context.getSystemService("persona");
        if (semPersonaManager != null) {
            return semPersonaManager.getDualDARProfile();
        }
        return null;
    }

    public static boolean isDualDARConfigured(
            Context context, PersistableBundle persistableBundle) {
        if (persistableBundle != null && persistableBundle.getBoolean("dualdar-config")) {
            Slogf.d("DualDarProvisioningHelper", "isDualDARConfigured from provisioning params");
            return true;
        }
        if (getDualDARProfile(context) == null) {
            return false;
        }
        Slogf.d("DualDarProvisioningHelper", "isDualDARConfigured from preset params");
        return true;
    }

    public static boolean isDualDARIntentProvisioned(PersistableBundle persistableBundle) {
        if (persistableBundle == null || !persistableBundle.getBoolean("dualdar-config")) {
            return false;
        }
        Slogf.d("DualDarProvisioningHelper", "isDualDARIntentProvisioned from provisioning params");
        return true;
    }

    public static boolean isDualDARNativeCrypto(
            Context context, PersistableBundle persistableBundle) {
        if (persistableBundle != null
                && !TextUtils.isEmpty(
                        persistableBundle.getString("dualdar-config-client-package"))) {
            Log.d("DualDarProvisioningHelper", "Custom crypto from provisioning params");
            return false;
        }
        if (persistableBundle != null && persistableBundle.getBoolean("dualdar-config")) {
            Log.d("DualDarProvisioningHelper", "native crypto from provisioning params");
            return true;
        }
        if (getDualDARProfile(context) == null
                || !"default"
                        .equals(
                                getDualDARProfile(context)
                                        .getString("dualdar-config-client-package"))) {
            return false;
        }
        Log.d("DualDarProvisioningHelper", "isDualDARNativeCrypto from preset params");
        return true;
    }

    public final void startProvisionService(Intent intent) {
        if (!this.mContext.bindService(intent, this.connection, 65)) {
            throw new Exception("startProvisionService : failed to startService.");
        }
    }
}
