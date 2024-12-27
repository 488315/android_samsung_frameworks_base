package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.util.Slog;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpCallbackDispatcher;
import com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFpAidlResponseHandler$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpAidlResponseHandler f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SemFpAidlResponseHandler$$ExternalSyntheticLambda11(SemFpAidlResponseHandler semFpAidlResponseHandler, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = semFpAidlResponseHandler;
        this.f$1 = i;
        this.f$2 = i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SemFpAidlResponseHandler semFpAidlResponseHandler = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                AcquisitionClient acquisitionClient = (AcquisitionClient) obj;
                semFpAidlResponseHandler.getClass();
                acquisitionClient.onAcquired(i, i2);
                SemFpCallbackDispatcher semFpCallbackDispatcher = semFpAidlResponseHandler.mHalCallbackEx;
                int i3 = semFpAidlResponseHandler.mUserId;
                int protoEnum = acquisitionClient.getProtoEnum();
                if (protoEnum != 3) {
                    if (protoEnum != 2) {
                        semFpCallbackDispatcher.getClass();
                        break;
                    } else {
                        Iterator it = ((ArrayList) semFpCallbackDispatcher.mEnrollListeners).iterator();
                        while (it.hasNext()) {
                            ((SemFpEnrollmentListener) it.next()).onEnrollAcquire(i, i2);
                        }
                        break;
                    }
                } else {
                    Iterator it2 = ((ArrayList) semFpCallbackDispatcher.mAuthenticationListeners).iterator();
                    while (it2.hasNext()) {
                        ((SemFpAuthenticationListener) it2.next()).onAuthenticationAcquire(i3, i, i2);
                    }
                    break;
                }
            default:
                SemFpAidlResponseHandler semFpAidlResponseHandler2 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                ErrorConsumer errorConsumer = (ErrorConsumer) obj;
                semFpAidlResponseHandler2.getClass();
                Slog.i("FingerprintService", "handleError: error=" + i4 + ", vendor=" + i5);
                errorConsumer.onError(i4, i5);
                if (i4 == 1) {
                    semFpAidlResponseHandler2.mAidlResponseHandlerCallback.onHardwareUnavailable();
                }
                SemFpCallbackDispatcher semFpCallbackDispatcher2 = semFpAidlResponseHandler2.mHalCallbackEx;
                int protoEnum2 = ((BaseClientMonitor) errorConsumer).getProtoEnum();
                if (protoEnum2 == 3) {
                    Iterator it3 = ((ArrayList) semFpCallbackDispatcher2.mAuthenticationListeners).iterator();
                    while (it3.hasNext()) {
                        ((SemFpAuthenticationListener) it3.next()).getClass();
                    }
                } else if (protoEnum2 == 2) {
                    Iterator it4 = ((ArrayList) semFpCallbackDispatcher2.mEnrollListeners).iterator();
                    while (it4.hasNext()) {
                        ((SemFpEnrollmentListener) it4.next()).getClass();
                    }
                }
                Iterator it5 = ((ArrayList) semFpCallbackDispatcher2.mEventListeners).iterator();
                while (it5.hasNext()) {
                    ((SemFpEventListener) it5.next()).getClass();
                }
                break;
        }
    }
}
