package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.face.Face;
import android.os.IBinder;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.InternalCleanupClient;
import com.android.server.biometrics.sensors.InternalEnumerateClient;
import com.android.server.biometrics.sensors.RemovalClient;
import com.android.server.biometrics.sensors.face.FaceUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceInternalCleanupClient extends InternalCleanupClient {
  public FaceInternalCleanupClient(
      Context context,
      Supplier supplier,
      int i,
      String str,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      BiometricUtils biometricUtils,
      Map map) {
    super(context, supplier, i, str, i2, biometricLogger, biometricContext, biometricUtils, map);
  }

  @Override // com.android.server.biometrics.sensors.InternalCleanupClient
  public InternalEnumerateClient getEnumerateClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      int i,
      String str,
      List list,
      BiometricUtils biometricUtils,
      int i2,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext) {
    return new FaceInternalEnumerateClient(
        context,
        supplier,
        iBinder,
        i,
        str,
        list,
        biometricUtils,
        i2,
        biometricLogger,
        biometricContext);
  }

  @Override // com.android.server.biometrics.sensors.InternalCleanupClient
  public RemovalClient getRemovalClient(
      Context context,
      Supplier supplier,
      IBinder iBinder,
      int i,
      int i2,
      String str,
      BiometricUtils biometricUtils,
      int i3,
      BiometricLogger biometricLogger,
      BiometricContext biometricContext,
      Map map) {
    return new FaceRemovalClient(
        context,
        supplier,
        iBinder,
        null,
        new int[] {i},
        i2,
        str,
        biometricUtils,
        i3,
        biometricLogger,
        biometricContext,
        map);
  }

  @Override // com.android.server.biometrics.sensors.InternalCleanupClient
  public void onAddUnknownTemplate(int i, BiometricAuthenticator.Identifier identifier) {
    FaceUtils.getInstance(getSensorId())
        .addBiometricForUser(getContext(), getTargetUserId(), (Face) identifier);
  }

  public void onEnumerationError() {
    Slog.d("FaceInternalCleanupClient", "onEnumerationError");
    this.mCallback.onClientFinished(this, false);
  }
}
