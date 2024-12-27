package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.os.CancellationSignal;
import android.os.Handler;

import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemTpaTestHal$1$$ExternalSyntheticLambda3
        implements CancellationSignal.OnCancelListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemTpaTestHal.AnonymousClass1 f$0;
    public final /* synthetic */ Handler f$1;
    public final /* synthetic */ ISessionCallback f$2;

    public /* synthetic */ SemTpaTestHal$1$$ExternalSyntheticLambda3(
            SemTpaTestHal.AnonymousClass1 anonymousClass1,
            Handler handler,
            ISessionCallback iSessionCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = handler;
        this.f$2 = iSessionCallback;
    }

    @Override // android.os.CancellationSignal.OnCancelListener
    public final void onCancel() {
        switch (this.$r8$classId) {
            case 0:
                SemTpaTestHal.AnonymousClass1 anonymousClass1 = this.f$0;
                Handler handler = this.f$1;
                ISessionCallback iSessionCallback = this.f$2;
                CountDownLatch countDownLatch = SemTpaTestHal.this.mActionDelayLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                CountDownLatch countDownLatch2 = SemTpaTestHal.this.mTspDelayLatch;
                if (countDownLatch2 != null) {
                    countDownLatch2.countDown();
                }
                handler.post(new SemTpaTestHal$$ExternalSyntheticLambda0(iSessionCallback, 3));
                break;
            default:
                SemTpaTestHal.AnonymousClass1 anonymousClass12 = this.f$0;
                Handler handler2 = this.f$1;
                ISessionCallback iSessionCallback2 = this.f$2;
                CountDownLatch countDownLatch3 = SemTpaTestHal.this.mActionDelayLatch;
                if (countDownLatch3 != null) {
                    countDownLatch3.countDown();
                }
                CountDownLatch countDownLatch4 = SemTpaTestHal.this.mTspDelayLatch;
                if (countDownLatch4 != null) {
                    countDownLatch4.countDown();
                }
                handler2.post(new SemTpaTestHal$$ExternalSyntheticLambda0(iSessionCallback2, 1));
                break;
        }
    }
}
