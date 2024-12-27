package com.android.server.biometrics.sensors.face.aidl;

import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFaceServiceExImpl$2$$ExternalSyntheticLambda0 implements Runnable {
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
                SemFaceServiceExImpl semFaceServiceExImpl = ((SemFaceServiceExImpl.AnonymousClass2) obj).this$0;
                semFaceServiceExImpl.daemonSetRotation(semFaceServiceExImpl.mLastRotation);
                break;
            default:
                ((SemFaceServiceExImpl.AnonymousClass4) obj).this$0.daemonCancelInternal();
                break;
        }
    }
}
