package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.fingerprint.FingerprintEnrollOptions;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.IBinder;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda25 implements Runnable {
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ IFingerprintServiceReceiver f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ byte[] f$6;
    public final /* synthetic */ String f$7;
    public final /* synthetic */ int f$8;
    public final /* synthetic */ FingerprintEnrollOptions f$9;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda25(FingerprintProvider fingerprintProvider, int i, IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, byte[] bArr, String str, int i3, FingerprintEnrollOptions fingerprintEnrollOptions) {
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
        this.f$2 = iBinder;
        this.f$3 = j;
        this.f$4 = iFingerprintServiceReceiver;
        this.f$5 = i2;
        this.f$6 = bArr;
        this.f$7 = str;
        this.f$8 = i3;
        this.f$9 = fingerprintEnrollOptions;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FingerprintProvider fingerprintProvider = this.f$0;
        int i = this.f$1;
        IBinder iBinder = this.f$2;
        long j = this.f$3;
        IFingerprintServiceReceiver iFingerprintServiceReceiver = this.f$4;
        int i2 = this.f$5;
        byte[] bArr = this.f$6;
        String str = this.f$7;
        int i3 = this.f$8;
        FingerprintEnrollOptions fingerprintEnrollOptions = this.f$9;
        fingerprintProvider.scheduleForSensor$1(i, new SemFingerprintEnrollClient(fingerprintProvider.mContext, ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i)).mLazySession, iBinder, j, new ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i2, bArr, str, FingerprintUtils.getInstance(i), i, fingerprintProvider.createLogger$1(1, 0, fingerprintProvider.mAuthenticationStatsCollector), fingerprintProvider.mBiometricContext, ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i)).mSensorProperties, fingerprintProvider.mAuthenticationStateListeners, ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i)).mSensorProperties.maxEnrollmentsPerUser, i3, fingerprintEnrollOptions), new ClientMonitorCompositeCallback(fingerprintProvider.mBiometricStateCallback, new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.5
            public AnonymousClass5() {
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                FingerprintProvider.this.mCallbackDispatcher.onClientFinished(baseClientMonitor, z);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
                FingerprintProvider.this.mCallbackDispatcher.onClientStarted(baseClientMonitor);
            }
        }));
    }
}
