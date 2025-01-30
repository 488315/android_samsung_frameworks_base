package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda21 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SyncRtSurfaceTransactionApplier f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda21(Object obj, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = syncRtSurfaceTransactionApplier;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                this.f$1.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(((RemoteAnimationTarget) this.f$0).leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
                break;
            case 1:
                RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) this.f$0;
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.f$1;
                int i = KeyguardViewMediator.C14887.$r8$clinit;
                syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
                break;
            default:
                KeyguardViewMediator.C14898 c14898 = (KeyguardViewMediator.C14898) this.f$0;
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = this.f$1;
                int i2 = KeyguardViewMediator.C14898.$r8$clinit;
                c14898.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float height = KeyguardViewMediator.this.mRemoteAnimationTarget.screenSpaceBounds.height();
                SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withAlpha = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(KeyguardViewMediator.this.mRemoteAnimationTarget.leash).withAlpha(floatValue);
                c14898.mUnoccludeMatrix.setTranslate(0.0f, (1.0f - floatValue) * height * 0.1f);
                withAlpha.withMatrix(c14898.mUnoccludeMatrix).withCornerRadius(KeyguardViewMediator.this.mWindowCornerRadius);
                syncRtSurfaceTransactionApplier2.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{withAlpha.build()});
                break;
        }
    }
}
