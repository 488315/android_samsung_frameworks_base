package com.samsung.android.knox.dar.ddar;

import android.content.Context;
import android.p009os.Bundle;
import android.util.Log;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;

/* loaded from: classes5.dex */
public class DualDARController {
  private static final boolean DEBUG = false;
  public static final String DUALDAR_AGENT = "KNOXCORE_PROXY_AGENT";
  public static final String DUALDAR_CONTROLLER_SERVICE = "DUALDAR_CONTROLLER_SERVICE";
  private static final int FEATURE_RESET_PASSWORD = 1000;
  private static final String TAG = "DualDAR::DualDARController";
  private static volatile DualDARController mInstance = null;
  private Context mContext;

  public static DualDARController getInstance(Context c) {
    if (mInstance == null) {
      synchronized (DualDARController.class) {
        if (mInstance == null) {
          mInstance = new DualDARController(c);
        }
      }
    }
    return mInstance;
  }

  private DualDARController(Context c) {
    this.mContext = null;
    this.mContext = c;
  }

  public boolean handleDeviceOwnerProvisioning() {
    Log.m94d(TAG, "handleDeviceOwnerProvisioning");
    Bundle params = new Bundle();
    boolean result = false;
    params.putInt("user_id", 0);
    Bundle response = processCommand(DualDarConstants.ON_DEVICE_OWNER_PROVISIONING, params);
    if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
      result = true;
    }
    Log.m94d(TAG, "handleDeviceOwnerProvisioning - result : " + result);
    return result;
  }

  public boolean handleWorkspaceCreation(int userId) {
    Log.m94d(TAG, "handleWorkspaceCreation");
    Bundle params = new Bundle();
    params.putInt("user_id", userId);
    Bundle response = processCommand(DualDarConstants.ON_WORKSPACE_CREATION, params);
    boolean ret =
        response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false);
    if (!ret) {
      Log.m96e(TAG, "handleWorkspaceCreation failed");
      return false;
    }
    Log.m96e(TAG, "handleWorkspaceCreation succeeded");
    return true;
  }

  public boolean handleBeforeUnlockUser(int userId) {
    Log.m94d(TAG, "handleBeforeUnlockUser");
    Bundle params = new Bundle();
    params.putInt("user_id", userId);
    Bundle response = processCommand(DualDarConstants.ON_BEFORE_UNLOCK_USER, params);
    boolean ret =
        response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false);
    if (!ret) {
      Log.m96e(TAG, "handleBeforeUnlockUser failed");
      return false;
    }
    Log.m96e(TAG, "handleBeforeUnlockUser succeeded");
    return true;
  }

  public boolean onPassword2Change(int userId, byte[] savedCredential, byte[] credential) {
    byte[] encryptedCred;
    byte[] encryptedSavedCred;
    Log.m94d(TAG, "onPassword2Change");
    Bundle request = new Bundle();
    if (credential != null && credential.length == 0) {
      credential = null;
    }
    if (savedCredential != null && savedCredential.length == 0) {
      savedCredential = null;
    }
    if (savedCredential != null) {
      if (SemPersonaManager.isDualDARNativeCrypto(userId)
          && (encryptedSavedCred = StreamCipher.encryptStream(savedCredential)) != null) {
        savedCredential = encryptedSavedCred;
      }
      request.putByteArray(DualDarConstants.EXISTING_PASSWORD, savedCredential);
    }
    request.putInt("user_id", userId);
    if (credential != null) {
      if (SemPersonaManager.isDualDARNativeCrypto(userId)
          && (encryptedCred = StreamCipher.encryptStream(credential)) != null) {
        credential = encryptedCred;
      }
      request.putByteArray(DualDarConstants.NEW_PASSWORD, credential);
    }
    Bundle response = processCommand(DualDarConstants.ON_PASSWORD2_CHANGE, request);
    Wiper.wipe(request.getByteArray(DualDarConstants.EXISTING_PASSWORD));
    Wiper.wipe(request.getByteArray(DualDarConstants.NEW_PASSWORD));
    boolean ret =
        response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false);
    if (!ret) {
      Log.m96e(TAG, "Authentication Change Failure by dual dar client");
      return false;
    }
    Log.m96e(TAG, "Authentication Change to DualDAR Client Successful");
    return true;
  }

  public boolean onPassword1Change(int userId, boolean isCredential) {
    Log.m94d(TAG, "onPassword1Change");
    Bundle request = new Bundle();
    request.putBoolean(DualDarConstants.NEW_PASSWORD, isCredential);
    Bundle response = processCommand(DualDarConstants.ON_PASSWORD1_CHANGE, request);
    boolean ret =
        response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false);
    if (!ret) {
      Log.m96e(TAG, "Failed to handle user 0 password change");
      return false;
    }
    Log.m94d(TAG, "Successfully handled user 0 password change");
    return true;
  }

  public boolean onPassword2Auth(int userId, byte[] credential) {
    byte[] encryptedCred;
    Log.m94d(TAG, "onPassword2Auth()");
    Bundle request = new Bundle();
    if (credential != null) {
      if (SemPersonaManager.isDualDARNativeCrypto(userId)
          && (encryptedCred = StreamCipher.encryptStream(credential)) != null) {
        credential = encryptedCred;
      }
      request.putByteArray(DualDarConstants.EXISTING_PASSWORD, credential);
    }
    request.putInt("user_id", userId);
    Bundle response = processCommand(DualDarConstants.ON_PASSWORD2_AUTH, request);
    boolean ret =
        response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false);
    Wiper.wipe(request.getByteArray(DualDarConstants.EXISTING_PASSWORD));
    if (!ret) {
      Log.m96e(TAG, "Authentication Failure by dual dar client");
      return false;
    }
    Log.m94d(TAG, "onPassword2Auth completed sucessfully");
    return true;
  }

  public byte[] fetchOuterLayerKey(int userId) {
    Log.m94d(TAG, "fetchOuterLayerKey()");
    Bundle request = new Bundle();
    request.putInt("user_id", userId);
    Bundle response = processCommand(DualDarConstants.FETCH_OUTERLAYER_KEY, request);
    byte[] key =
        response != null ? response.getByteArray(DualDarConstants.OUTER_LAYER_SECRET) : null;
    if (key == null) {
      Log.m96e(TAG, "fetchOuterLayerKey failed");
      return null;
    }
    Log.m96e(TAG, "fetchOuterLayerKey Successful");
    return key;
  }

  public void onUserStopped(int userId) {
    Log.m94d(TAG, "onUserStopped()");
    Bundle request = new Bundle();
    request.putInt("user_id", userId);
    Bundle response = processCommandAsync(DualDarConstants.ON_USER_STOPPED, request);
    boolean ret = response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
    if (!ret) {
      Log.m96e(TAG, "handling onUserStopped failed by KnoxCore");
    }
    Log.m96e(TAG, "handling onUserStopped succeeded by KnoxCore");
  }

  public void onUserStart(int userId) {
    Log.m94d(TAG, "onUserStart()");
    Bundle request = new Bundle();
    request.putInt("user_id", userId);
    Bundle response = processCommandAsync(DualDarConstants.ON_USER_START, request);
    boolean ret = response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
    if (!ret) {
      Log.m96e(TAG, "handling onUserStart failed by KnoxCore");
    }
    Log.m96e(TAG, "handling onUserStart succeeded by KnoxCore");
  }

  public boolean isReady(int userId) {
    Log.m94d(TAG, "isReady()");
    Bundle request = new Bundle();
    request.putInt("user_id", userId);
    Bundle response = processCommand(DualDarConstants.IS_READY, request);
    boolean ret = response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
    if (!ret) {
      Log.m96e(TAG, "handling isReady failed by KnoxCore");
    }
    return ret;
  }

  public void onUserRemoved(int userId) {
    Log.m94d(TAG, "onUserRemoved()");
    Bundle request = new Bundle();
    request.putInt("user_id", userId);
    Bundle response = processCommandAsync(DualDarConstants.ON_USER_REMOVED, request);
    boolean ret = response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
    if (!ret) {
      Log.m96e(TAG, "handling onUserRemoved failed by KnoxCore");
    }
    Log.m96e(TAG, "handling onUserRemoved succeeded by KnoxCore");
  }

  public boolean onDualDarStateChanged(
      State prevState, State currentState, Event event, int dualDarUserId) {
    Bundle request = new Bundle();
    request.putInt("user_id", dualDarUserId);
    request.putString(DualDarConstants.PREVIOUS_STATE, prevState.name());
    request.putString(DualDarConstants.CURRENT_STATE, currentState.name());
    request.putString(DualDarConstants.ON_EVENT, event.name());
    Bundle response = processCommandAsync(DualDarConstants.ON_DDAR_STATE_CHANGED, request);
    boolean ret = false;
    if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
      ret = true;
    }
    if (!ret) {
      Log.m96e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
    }
    return ret;
  }

  public boolean setResetPasswordToken(
      int dualDarUserId, byte[] password, long tokenHandle, byte[] token) {
    Bundle request = new Bundle();
    request.putInt("user_id", dualDarUserId);
    if (password != null) {
      request.putByteArray(DualDarConstants.EXISTING_PASSWORD, password);
    }
    request.putLong(DualDarConstants.RESET_PASSWORD_TOKEN_HANDLE, tokenHandle);
    request.putByteArray(DualDarConstants.RESET_PASSWORD_TOKEN, token);
    Bundle response = processCommand(DualDarConstants.SET_RESET_PASSWORD_TOKEN, request);
    Wiper.wipe(request.getByteArray(DualDarConstants.EXISTING_PASSWORD));
    boolean ret = false;
    if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
      ret = true;
    }
    if (!ret) {
      Log.m96e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
    }
    return ret;
  }

  public void clearResetPasswordToken(int dualDarUserId, long tokenHandle) {
    Bundle request = new Bundle();
    request.putInt("user_id", dualDarUserId);
    request.putLong(DualDarConstants.RESET_PASSWORD_TOKEN_HANDLE, tokenHandle);
    Bundle response = processCommand(DualDarConstants.CLEAR_RESET_PASSWORD_TOKEN, request);
    boolean ret = false;
    if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
      ret = true;
    }
    if (!ret) {
      Log.m96e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
    }
  }

  public boolean resetPasswordWithToken(
      int dualDarUserId, byte[] newPassword, long tokenHandle, byte[] token) {
    Bundle request = new Bundle();
    request.putInt("user_id", dualDarUserId);
    if (newPassword != null && newPassword.length > 0) {
      request.putByteArray(DualDarConstants.NEW_PASSWORD, newPassword);
    }
    request.putLong(DualDarConstants.RESET_PASSWORD_TOKEN_HANDLE, tokenHandle);
    request.putByteArray(DualDarConstants.RESET_PASSWORD_TOKEN, token);
    Bundle response = processCommand(DualDarConstants.RESET_PASSWORD_WITH_TOKEN, request);
    Wiper.wipe(request.getByteArray(DualDarConstants.NEW_PASSWORD));
    boolean ret = false;
    if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
      ret = true;
    }
    if (!ret) {
      Log.m96e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
    }
    return ret;
  }

  public boolean isResetPasswordSupported(int dualDarUserId) {
    Bundle request = new Bundle();
    request.putInt("user_id", dualDarUserId);
    request.putInt(DualDarConstants.FEATURE, 1000);
    Bundle response = processCommand(DualDarConstants.IS_SUPPORTED, request);
    boolean ret = false;
    if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
      ret = true;
    }
    if (!ret) {
      Log.m96e(TAG, "Some operation on DualDAR platform failed on DualDAR state changed");
    }
    return ret;
  }

  private Bundle processCommand(String command, Bundle params) {
    return KnoxProxyManager.getInstance(this.mContext)
        .relayMessage("KNOXCORE_PROXY_AGENT", DUALDAR_CONTROLLER_SERVICE, command, params);
  }

  private Bundle processCommandAsync(String command, Bundle params) {
    return KnoxProxyManager.getInstance(this.mContext)
        .relayMessageAsync("KNOXCORE_PROXY_AGENT", DUALDAR_CONTROLLER_SERVICE, command, params);
  }
}
