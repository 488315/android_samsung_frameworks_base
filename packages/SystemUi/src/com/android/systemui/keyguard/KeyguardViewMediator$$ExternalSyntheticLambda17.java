package com.android.systemui.keyguard;

import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.log.LogLevel;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda17 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda17(KeyguardViewMediator keyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = keyguardViewMediator.mUnoccludeFromDreamFinishedCallback;
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        keyguardViewMediator.mUnoccludeFromDreamFinishedCallback = null;
                    } catch (RemoteException e) {
                        android.util.Log.e("KeyguardViewMediator", "Wasn't able to callback", e);
                        KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "Wasn't able to callback", e);
                    }
                    keyguardViewMediator.mInteractionJankMonitor.end(64);
                    break;
                }
                break;
            default:
                KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                Float f = (Float) obj;
                if (keyguardViewMediator2.mRemoteAnimationTarget != null) {
                    new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(keyguardViewMediator2.mRemoteAnimationTarget.leash).withAlpha(f.floatValue()).build()});
                    break;
                }
                break;
        }
    }
}
