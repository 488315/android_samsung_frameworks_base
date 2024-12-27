package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda92 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SyncRtSurfaceTransactionApplier f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda92(Object obj, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, int i) {
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
                this.f$1.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(((RemoteAnimationTarget) this.f$0).leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
                break;
            default:
                KeyguardViewMediator.AnonymousClass9 anonymousClass9 = (KeyguardViewMediator.AnonymousClass9) this.f$0;
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.f$1;
                anonymousClass9.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float height = KeyguardViewMediator.this.mRemoteAnimationTarget.screenSpaceBounds.height();
                SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withAlpha = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(KeyguardViewMediator.this.mRemoteAnimationTarget.leash).withAlpha(floatValue);
                anonymousClass9.mUnoccludeMatrix.setTranslate(0.0f, (1.0f - floatValue) * height * 0.1f);
                withAlpha.withMatrix(anonymousClass9.mUnoccludeMatrix).withCornerRadius(KeyguardViewMediator.this.mWindowCornerRadius);
                syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{withAlpha.build()});
                break;
        }
    }
}
