package com.android.server.rollback;

import android.content.Intent;
import android.content.rollback.RollbackInfo;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RollbackPackageHealthObserver$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RollbackPackageHealthObserver$$ExternalSyntheticLambda0(
            RollbackPackageHealthObserver$$ExternalSyntheticLambda6
                    rollbackPackageHealthObserver$$ExternalSyntheticLambda6,
            Intent intent) {
        this.f$0 = rollbackPackageHealthObserver$$ExternalSyntheticLambda6;
        this.f$1 = intent;
    }

    public /* synthetic */ RollbackPackageHealthObserver$$ExternalSyntheticLambda0(
            RollbackPackageHealthObserver rollbackPackageHealthObserver,
            RollbackInfo rollbackInfo) {
        this.f$0 = rollbackPackageHealthObserver;
        this.f$1 = rollbackInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RollbackPackageHealthObserver rollbackPackageHealthObserver =
                        (RollbackPackageHealthObserver) this.f$0;
                RollbackInfo rollbackInfo = (RollbackInfo) this.f$1;
                rollbackPackageHealthObserver.getClass();
                if (RollbackPackageHealthObserver.isRebootlessApex(rollbackInfo)) {
                    rollbackPackageHealthObserver.mTwoPhaseRollbackEnabled = true;
                    RollbackPackageHealthObserver.writeBoolean(
                            rollbackPackageHealthObserver.mTwoPhaseRollbackEnabledFile, true);
                    break;
                }
                break;
            default:
                ((Consumer) this.f$0).accept((Intent) this.f$1);
                break;
        }
    }
}
