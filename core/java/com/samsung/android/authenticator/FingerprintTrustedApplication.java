package com.samsung.android.authenticator;

import java.util.Arrays;

/* loaded from: classes5.dex */
final class FingerprintTrustedApplication implements TrustedApplication {
  private static final String TAG = "FTA";
  private final int mHandle;
  static final byte[] SET_AUTH_CHALLENGE_COMMAND = {1};
  static final byte[] GET_AUTH_RESULT_COMMAND = {2};
  private static final byte[] SUCCESS = {0};

  FingerprintTrustedApplication(int handle) {
    this.mHandle = handle;
  }

  @Override // com.samsung.android.authenticator.TrustedApplication
  public int load() {
    return this.mHandle;
  }

  @Override // com.samsung.android.authenticator.TrustedApplication
  public byte[] execute(byte[] command) {
    if (command == null || command.length != 1) {
      AuthenticatorLog.m250e(TAG, "command is invalid");
      return new byte[0];
    }
    byte[] result = new byte[0];
    if (Arrays.equals(SET_AUTH_CHALLENGE_COMMAND, command)) {
      if (AuthenticatorService.setChallenge(command)) {
        result = SUCCESS;
      }
    } else if (Arrays.equals(GET_AUTH_RESULT_COMMAND, command)) {
      result = AuthenticatorService.getWrappedObject(command);
    }
    AuthenticatorLog.m250e(TAG, "command is not supported");
    return result;
  }

  @Override // com.samsung.android.authenticator.TrustedApplication
  public int unload() {
    return 0;
  }

  @Override // com.samsung.android.authenticator.TrustedApplication
  public int getHandle() {
    return this.mHandle;
  }
}
