package com.android.server.biometrics.sensors.face.aidl;


public final /* synthetic */ class SemFaceServiceExImpl$2$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SemFaceServiceExImpl$2$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SemFaceServiceExImpl semFaceServiceExImpl =
                        ((SemFaceServiceExImpl.AnonymousClass2) obj).this$0;
                semFaceServiceExImpl.daemonSetRotation(semFaceServiceExImpl.mLastRotation);
                break;
            default:
                ((SemFaceServiceExImpl.AnonymousClass4) obj).this$0.daemonCancelInternal();
                break;
        }
    }
}
