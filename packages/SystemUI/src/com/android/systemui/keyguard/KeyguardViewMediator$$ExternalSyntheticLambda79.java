package com.android.systemui.keyguard;

import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.keyguard.KeyguardViewController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda79 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda79(KeyguardViewMediator keyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        switch (i) {
            case 0:
                keyguardViewMediator.getClass();
                keyguardViewMediator.mShowCommunalWhenUnoccluding = ((Boolean) obj).booleanValue();
                break;
            case 1:
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = keyguardViewMediator.mUnoccludeFinishedCallback;
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        keyguardViewMediator.mUnoccludeFinishedCallback = null;
                    } catch (RemoteException e) {
                        android.util.Log.e("KeyguardViewMediator", "Wasn't able to callback", e);
                    }
                    keyguardViewMediator.mInteractionJankMonitor.end(64);
                    break;
                }
                break;
            case 2:
                keyguardViewMediator.mWallpaperSupportsAmbientMode = ((Boolean) obj).booleanValue();
                break;
            default:
                Float f = (Float) obj;
                if (keyguardViewMediator.mRemoteAnimationTarget != null) {
                    new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(keyguardViewMediator.mRemoteAnimationTarget.leash).withAlpha(f.floatValue()).build()});
                    break;
                } else {
                    android.util.Log.e("KeyguardViewMediator", "Attempting to set alpha on null animation target");
                    break;
                }
        }
    }
}
