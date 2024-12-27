package com.android.server.biometrics.sensors.fingerprint;

import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFpAodController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpAodController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SemFpAodController$$ExternalSyntheticLambda2(
            SemFpAodController semFpAodController,
            SemFpAodController$$ExternalSyntheticLambda0
                    semFpAodController$$ExternalSyntheticLambda0) {
        this.$r8$classId = 1;
        this.f$0 = semFpAodController;
        this.f$1 = semFpAodController$$ExternalSyntheticLambda0;
    }

    public /* synthetic */ SemFpAodController$$ExternalSyntheticLambda2(
            SemFpAodController semFpAodController,
            SemFpAodController$$ExternalSyntheticLambda0
                    semFpAodController$$ExternalSyntheticLambda0,
            byte b) {
        this.$r8$classId = 2;
        this.f$0 = semFpAodController;
        this.f$1 = semFpAodController$$ExternalSyntheticLambda0;
    }

    public /* synthetic */ SemFpAodController$$ExternalSyntheticLambda2(
            SemFpAodController semFpAodController,
            SemFpAodController$$ExternalSyntheticLambda0
                    semFpAodController$$ExternalSyntheticLambda0,
            char c) {
        this.$r8$classId = 3;
        this.f$0 = semFpAodController;
        this.f$1 = semFpAodController$$ExternalSyntheticLambda0;
    }

    public /* synthetic */ SemFpAodController$$ExternalSyntheticLambda2(
            SemFpAodController semFpAodController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = semFpAodController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemFpAodController semFpAodController = this.f$0;
                Runnable runnable = (Runnable) this.f$1;
                if (semFpAodController.mAodController != null) {
                    runnable.run();
                    break;
                } else {
                    semFpAodController.mPendingRequestBeforeListener.add(runnable);
                    break;
                }
            case 1:
                SemFpAodController semFpAodController2 = this.f$0;
                Runnable runnable2 = (Runnable) this.f$1;
                if (semFpAodController2.mAodController != null) {
                    runnable2.run();
                    break;
                } else {
                    semFpAodController2.mPendingRequestBeforeListener.add(runnable2);
                    break;
                }
            case 2:
                SemFpAodController semFpAodController3 = this.f$0;
                Runnable runnable3 = (Runnable) this.f$1;
                if (semFpAodController3.mAodController != null) {
                    runnable3.run();
                    break;
                } else {
                    semFpAodController3.mPendingRequestBeforeListener.add(runnable3);
                    break;
                }
            case 3:
                SemFpAodController semFpAodController4 = this.f$0;
                Runnable runnable4 = (Runnable) this.f$1;
                if (semFpAodController4.mAodController != null) {
                    runnable4.run();
                    break;
                } else {
                    semFpAodController4.mPendingRequestBeforeListener.add(runnable4);
                    break;
                }
            default:
                SemFpAodController semFpAodController5 = this.f$0;
                semFpAodController5.mAodController = (ISemFingerprintAodController) this.f$1;
                Iterator it = semFpAodController5.mPendingRequestBeforeListener.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                semFpAodController5.mPendingRequestBeforeListener.clear();
                break;
        }
    }
}
