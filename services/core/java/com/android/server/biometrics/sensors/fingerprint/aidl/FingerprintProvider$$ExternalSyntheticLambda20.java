package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.IBinder;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpSensorTestClient;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda20 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda20(FingerprintProvider fingerprintProvider, int i, Object obj, Object obj2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
        this.f$2 = obj;
        this.f$3 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintProvider fingerprintProvider = this.f$0;
                int i = this.f$1;
                SemFpBaseRequestClient semFpBaseRequestClient = (SemFpBaseRequestClient) this.f$2;
                final CountDownLatch countDownLatch = (CountDownLatch) this.f$3;
                fingerprintProvider.getClass();
                final int i2 = 0;
                fingerprintProvider.scheduleForSensor$1(i, semFpBaseRequestClient, new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.8
                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                        switch (i2) {
                            case 0:
                                countDownLatch.countDown();
                                break;
                            default:
                                countDownLatch.countDown();
                                break;
                        }
                    }
                });
                break;
            case 1:
                FingerprintProvider fingerprintProvider2 = this.f$0;
                int i3 = this.f$1;
                SemFpBaseRequestClient semFpBaseRequestClient2 = (SemFpBaseRequestClient) this.f$2;
                final CountDownLatch countDownLatch2 = (CountDownLatch) this.f$3;
                fingerprintProvider2.getClass();
                final int i4 = 1;
                fingerprintProvider2.scheduleForSensor$1(i3, semFpBaseRequestClient2, new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.8
                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
                        switch (i4) {
                            case 0:
                                countDownLatch2.countDown();
                                break;
                            default:
                                countDownLatch2.countDown();
                                break;
                        }
                    }
                });
                break;
            default:
                FingerprintProvider fingerprintProvider3 = this.f$0;
                int i5 = this.f$1;
                IBinder iBinder = (IBinder) this.f$2;
                ClientMonitorCallbackConverter clientMonitorCallbackConverter = (ClientMonitorCallbackConverter) this.f$3;
                fingerprintProvider3.getClass();
                SemFpSensorTestClient semFpSensorTestClient = new SemFpSensorTestClient(fingerprintProvider3.mContext, fingerprintProvider3.mBiometricContext, ((Sensor) fingerprintProvider3.mFingerprintSensors.mSensors.get(i5)).mLazySession, iBinder, clientMonitorCallbackConverter, i5, 0, "FingerprintSensorTestClient", true, 3, 0, null, null);
                if (((Sensor) fingerprintProvider3.mFingerprintSensors.mSensors.get(i5)) != null) {
                    ((Sensor) fingerprintProvider3.mFingerprintSensors.mSensors.get(i5)).mScheduler.scheduleClientMonitor(semFpSensorTestClient, null);
                    break;
                }
                break;
        }
    }
}
