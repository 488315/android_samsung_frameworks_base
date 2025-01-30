package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.IBiometricsFace;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.HalClientMonitor;
import java.io.File;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class FaceUpdateActiveUserClient extends HalClientMonitor {
    public final Map mAuthenticatorIds;
    public final boolean mHasEnrolledBiometrics;

    public abstract int daemonSetActiveUser(int i);

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 1;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    public FaceUpdateActiveUserClient(Context context, Supplier supplier, int i, String str, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext, boolean z, Map map) {
        super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mHasEnrolledBiometrics = z;
        this.mAuthenticatorIds = map;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        String str;
        if (!new File(Environment.getDataVendorDeDirectory(getTargetUserId()), "facedata").exists()) {
            Slog.e("FaceUpdateActiveUserClient", "vold has not created the directory?");
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            daemonSetActiveUser(getTargetUserId());
            IBiometricsFace iBiometricsFace = (IBiometricsFace) getFreshDaemon();
            long currentTimeMillis = System.currentTimeMillis();
            long j = iBiometricsFace.getAuthenticatorId().value;
            this.mAuthenticatorIds.put(Integer.valueOf(getTargetUserId()), Long.valueOf(this.mHasEnrolledBiometrics ? j : 0L));
            StringBuilder sb = new StringBuilder();
            sb.append("getAuthenticatorId FINISH (");
            sb.append(System.currentTimeMillis() - currentTimeMillis);
            sb.append("ms)  user = ");
            sb.append(getTargetUserId());
            if (Utils.DEBUG) {
                str = ", id = " + j;
            } else {
                str = "";
            }
            sb.append(str);
            Slog.w("FaceUpdateActiveUserClient", sb.toString());
            this.mCallback.onClientFinished(this, true);
        } catch (RemoteException e) {
            Slog.e("FaceUpdateActiveUserClient", "Failed to setActiveUser: " + e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
