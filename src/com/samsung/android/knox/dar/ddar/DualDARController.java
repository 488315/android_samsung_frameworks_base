package com.samsung.android.knox.dar.ddar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;

/* loaded from: classes6.dex */
public class DualDARController {
    private static final boolean DEBUG = false;
    public static final String DUALDAR_AGENT = "KNOXCORE_PROXY_AGENT";
    public static final String DUALDAR_CONTROLLER_SERVICE = "DUALDAR_CONTROLLER_SERVICE";
    private static final int FEATURE_RESET_PASSWORD = 1000;
    private static final String TAG = "DualDAR::DualDARController";
    private static volatile DualDARController mInstance;
    private Context mContext;

    public static DualDARController getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DualDARController.class) {
                if (mInstance == null) {
                    mInstance = new DualDARController(context);
                }
            }
        }
        return mInstance;
    }

    private DualDARController(Context context) {
        this.mContext = context;
    }

    public boolean handleDeviceOwnerProvisioning() {
        Log.d(TAG, "handleDeviceOwnerProvisioning");
        Bundle bundle = new Bundle();
        boolean z = false;
        bundle.putInt("user_id", 0);
        Bundle processCommand = processCommand("ON_DEVICE_OWNER_PROVISIONING", bundle);
        if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        Log.d(TAG, "handleDeviceOwnerProvisioning - result : " + z);
        return z;
    }

    public boolean handleWorkspaceCreation(int i) {
        Log.d(TAG, "handleWorkspaceCreation");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommand = processCommand("ON_WORKSPACE_CREATION", bundle);
        if (processCommand == null || !processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            Log.e(TAG, "handleWorkspaceCreation failed");
            return false;
        }
        Log.e(TAG, "handleWorkspaceCreation succeeded");
        return true;
    }

    public boolean handleBeforeUnlockUser(int i) {
        Log.d(TAG, "handleBeforeUnlockUser");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommand = processCommand("ON_BEFORE_UNLOCK_USER", bundle);
        if (processCommand == null || !processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            Log.e(TAG, "handleBeforeUnlockUser failed");
            return false;
        }
        Log.e(TAG, "handleBeforeUnlockUser succeeded");
        return true;
    }

    public boolean onPassword2Change(int i, byte[] bArr, byte[] bArr2) {
        byte[] encryptStream;
        byte[] encryptStream2;
        Log.d(TAG, "onPassword2Change");
        Bundle bundle = new Bundle();
        if (bArr2 != null && bArr2.length == 0) {
            bArr2 = null;
        }
        if (bArr != null && bArr.length == 0) {
            bArr = null;
        }
        if (bArr != null) {
            if (SemPersonaManager.isDualDARNativeCrypto(i) && (encryptStream2 = StreamCipher.encryptStream(bArr)) != null) {
                bArr = encryptStream2;
            }
            bundle.putByteArray("EXISTING_PASSWORD", bArr);
        }
        bundle.putInt("user_id", i);
        if (bArr2 != null) {
            if (SemPersonaManager.isDualDARNativeCrypto(i) && (encryptStream = StreamCipher.encryptStream(bArr2)) != null) {
                bArr2 = encryptStream;
            }
            bundle.putByteArray("NEW_PASSWORD", bArr2);
        }
        Bundle processCommand = processCommand("ON_PASSWORD2_CHANGE", bundle);
        Wiper.wipe(bundle.getByteArray("EXISTING_PASSWORD"));
        Wiper.wipe(bundle.getByteArray("NEW_PASSWORD"));
        if (processCommand == null || !processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            Log.e(TAG, "Authentication Change Failure by dual dar client");
            return false;
        }
        Log.e(TAG, "Authentication Change to DualDAR Client Successful");
        return true;
    }

    public boolean onPassword1Change(int i, boolean z) {
        Log.d(TAG, "onPassword1Change");
        Bundle bundle = new Bundle();
        bundle.putBoolean("NEW_PASSWORD", z);
        Bundle processCommand = processCommand("ON_PASSWORD1_CHANGE", bundle);
        if (processCommand == null || !processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            Log.e(TAG, "Failed to handle user 0 password change");
            return false;
        }
        Log.d(TAG, "Successfully handled user 0 password change");
        return true;
    }

    public boolean onPassword2Auth(int i, byte[] bArr) {
        byte[] encryptStream;
        Log.d(TAG, "onPassword2Auth()");
        Bundle bundle = new Bundle();
        if (bArr != null) {
            if (SemPersonaManager.isDualDARNativeCrypto(i) && (encryptStream = StreamCipher.encryptStream(bArr)) != null) {
                bArr = encryptStream;
            }
            bundle.putByteArray("EXISTING_PASSWORD", bArr);
        }
        bundle.putInt("user_id", i);
        Bundle processCommand = processCommand("ON_PASSWORD2_AUTH", bundle);
        boolean z = processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false);
        Wiper.wipe(bundle.getByteArray("EXISTING_PASSWORD"));
        if (!z) {
            Log.e(TAG, "Authentication Failure by dual dar client");
            return false;
        }
        Log.d(TAG, "onPassword2Auth completed sucessfully");
        return true;
    }

    public byte[] fetchOuterLayerKey(int i) {
        Log.d(TAG, "fetchOuterLayerKey()");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommand = processCommand("FETCH_OUTERLAYER_KEY", bundle);
        byte[] byteArray = processCommand != null ? processCommand.getByteArray("OUTER_LAYER_SECRET") : null;
        if (byteArray == null) {
            Log.e(TAG, "fetchOuterLayerKey failed");
            return null;
        }
        Log.e(TAG, "fetchOuterLayerKey Successful");
        return byteArray;
    }

    public void onUserStopped(int i) {
        Log.d(TAG, "onUserStopped()");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommandAsync = processCommandAsync("ON_USER_STOPPED", bundle);
        if (processCommandAsync == null || !processCommandAsync.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
            Log.e(TAG, "handling onUserStopped failed by KnoxCore");
        }
        Log.e(TAG, "handling onUserStopped succeeded by KnoxCore");
    }

    public void onUserStart(int i) {
        Log.d(TAG, "onUserStart()");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommandAsync = processCommandAsync("ON_USER_START", bundle);
        if (processCommandAsync == null || !processCommandAsync.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
            Log.e(TAG, "handling onUserStart failed by KnoxCore");
        }
        Log.e(TAG, "handling onUserStart succeeded by KnoxCore");
    }

    public boolean isReady(int i) {
        Log.d(TAG, "isReady()");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommand = processCommand("IS_READY", bundle);
        boolean z = processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
        if (!z) {
            Log.e(TAG, "handling isReady failed by KnoxCore");
        }
        return z;
    }

    public void onUserRemoved(int i) {
        Log.d(TAG, "onUserRemoved()");
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        Bundle processCommandAsync = processCommandAsync("ON_USER_REMOVED", bundle);
        if (processCommandAsync == null || !processCommandAsync.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
            Log.e(TAG, "handling onUserRemoved failed by KnoxCore");
        }
        Log.e(TAG, "handling onUserRemoved succeeded by KnoxCore");
    }

    public boolean onDualDarStateChanged(State state, State state2, Event event, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        bundle.putString("PREVIOUS_STATE", state.name());
        bundle.putString("CURRENT_STATE", state2.name());
        bundle.putString("ON_EVENT", event.name());
        Bundle processCommandAsync = processCommandAsync("ON_DDAR_STATE_CHANGED", bundle);
        boolean z = false;
        if (processCommandAsync != null && processCommandAsync.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        if (!z) {
            Log.e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
        }
        return z;
    }

    public boolean setResetPasswordToken(int i, byte[] bArr, long j, byte[] bArr2) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        if (bArr != null) {
            bundle.putByteArray("EXISTING_PASSWORD", bArr);
        }
        bundle.putLong("RESET_PASSWORD_TOKEN_HANDLE", j);
        bundle.putByteArray("RESET_PASSWORD_TOKEN", bArr2);
        Bundle processCommand = processCommand("SET_RESET_PASSWORD_TOKEN", bundle);
        Wiper.wipe(bundle.getByteArray("EXISTING_PASSWORD"));
        boolean z = false;
        if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        if (!z) {
            Log.e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
        }
        return z;
    }

    public void clearResetPasswordToken(int i, long j) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        bundle.putLong("RESET_PASSWORD_TOKEN_HANDLE", j);
        Bundle processCommand = processCommand("CLEAR_RESET_PASSWORD_TOKEN", bundle);
        if (processCommand == null || !processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            Log.e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
        }
    }

    public boolean resetPasswordWithToken(int i, byte[] bArr, long j, byte[] bArr2) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        if (bArr != null && bArr.length > 0) {
            bundle.putByteArray("NEW_PASSWORD", bArr);
        }
        bundle.putLong("RESET_PASSWORD_TOKEN_HANDLE", j);
        bundle.putByteArray("RESET_PASSWORD_TOKEN", bArr2);
        Bundle processCommand = processCommand("RESET_PASSWORD_WITH_TOKEN", bundle);
        Wiper.wipe(bundle.getByteArray("NEW_PASSWORD"));
        boolean z = false;
        if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        if (!z) {
            Log.e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
        }
        return z;
    }

    public boolean isResetPasswordSupported(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", i);
        bundle.putInt("FEATURE", 1000);
        Bundle processCommand = processCommand("IS_SUPPORTED", bundle);
        boolean z = false;
        if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        if (!z) {
            Log.e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
        }
        return z;
    }

    private Bundle processCommand(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessage("KNOXCORE_PROXY_AGENT", DUALDAR_CONTROLLER_SERVICE, str, bundle);
    }

    private Bundle processCommandAsync(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessageAsync("KNOXCORE_PROXY_AGENT", DUALDAR_CONTROLLER_SERVICE, str, bundle);
    }
}
