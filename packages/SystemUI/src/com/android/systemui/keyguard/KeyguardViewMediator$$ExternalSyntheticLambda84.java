package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda84 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SyncRtSurfaceTransactionApplier f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda84(Object obj, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = syncRtSurfaceTransactionApplier;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) this.f$0;
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.f$1;
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
                break;
            case 1:
                RemoteAnimationTarget remoteAnimationTarget2 = (RemoteAnimationTarget) this.f$0;
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = this.f$1;
                int i = KeyguardViewMediator.AnonymousClass9.$r8$clinit;
                syncRtSurfaceTransactionApplier2.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget2.leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
                break;
            default:
                KeyguardViewMediator.AnonymousClass10 anonymousClass10 = (KeyguardViewMediator.AnonymousClass10) this.f$0;
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier3 = this.f$1;
                int i2 = KeyguardViewMediator.AnonymousClass10.$r8$clinit;
                anonymousClass10.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float height = KeyguardViewMediator.this.mRemoteAnimationTarget.screenSpaceBounds.height();
                SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withAlpha = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(KeyguardViewMediator.this.mRemoteAnimationTarget.leash).withAlpha(floatValue);
                anonymousClass10.mUnoccludeMatrix.setTranslate(0.0f, (1.0f - floatValue) * height * 0.1f);
                withAlpha.withMatrix(anonymousClass10.mUnoccludeMatrix).withCornerRadius(KeyguardViewMediator.this.mWindowCornerRadius);
                syncRtSurfaceTransactionApplier3.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{withAlpha.build()});
                break;
        }
    }
}
