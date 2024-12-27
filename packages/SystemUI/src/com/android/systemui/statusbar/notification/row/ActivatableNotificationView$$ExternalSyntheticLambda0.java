package com.android.systemui.statusbar.notification.row;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.util.MathUtils;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationHeadsUpCycling;
import com.android.systemui.statusbar.notification.shared.NotificationsImprovedHunAnimation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ActivatableNotificationView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ActivatableNotificationView f$0;

    public /* synthetic */ ActivatableNotificationView$$ExternalSyntheticLambda0(ActivatableNotificationView activatableNotificationView) {
        this.f$0 = activatableNotificationView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        ActivatableNotificationView activatableNotificationView = this.f$0;
        switch (i) {
            case 0:
                activatableNotificationView.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                activatableNotificationView.mAppearAnimationFraction = floatValue;
                float interpolation = ((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation((MathUtils.constrain(floatValue, 0.7f, 1.0f) - 0.7f) / 0.3f);
                View contentView = activatableNotificationView.getContentView();
                if (contentView.hasOverlappingRendering()) {
                    contentView.setLayerType((interpolation == 0.0f || interpolation == 1.0f) ? 0 : 2, null);
                }
                contentView.setAlpha(interpolation);
                if (interpolation == 1.0f) {
                    activatableNotificationView.resetAllContentAlphas();
                }
                int i2 = NotificationHeadsUpCycling.$r8$clinit;
                Flags.notificationAvalancheThrottleHun();
                ExpandableView.ClipSide clipSide = ExpandableView.ClipSide.BOTTOM;
                int i3 = NotificationsImprovedHunAnimation.$r8$clinit;
                float f = activatableNotificationView.mAppearAnimationFraction;
                float f2 = (1.0f - f) * activatableNotificationView.mAnimationTranslationY;
                activatableNotificationView.mAppearAnimationTranslation = f2;
                float f3 = activatableNotificationView.mActualHeight;
                float f4 = f * f3;
                if (activatableNotificationView.mTargetPoint != null) {
                    int width = activatableNotificationView.getWidth();
                    float f5 = 1.0f - activatableNotificationView.mAppearAnimationFraction;
                    Point point = activatableNotificationView.mTargetPoint;
                    int i4 = point.x;
                    float f6 = activatableNotificationView.mAnimationTranslationY;
                    activatableNotificationView.setOutlineRect(i4 * f5, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f6, point.y, f5, f6), width - ((width - i4) * f5), f3 - ((r4 - r0) * f5));
                } else if (clipSide == ExpandableView.ClipSide.TOP) {
                    activatableNotificationView.setOutlineRect(0.0f, f3 - f4, activatableNotificationView.getWidth(), f3);
                } else {
                    activatableNotificationView.setOutlineRect(0.0f, f2, activatableNotificationView.getWidth(), f4 + activatableNotificationView.mAppearAnimationTranslation);
                }
                activatableNotificationView.invalidate();
                break;
            default:
                activatableNotificationView.setBackgroundTintColor(NotificationUtils.interpolateColors(valueAnimator.getAnimatedFraction(), activatableNotificationView.mStartTint, activatableNotificationView.mTargetTint));
                break;
        }
    }

    public /* synthetic */ ActivatableNotificationView$$ExternalSyntheticLambda0(ActivatableNotificationView activatableNotificationView, ExpandableView.ClipSide clipSide) {
        this.f$0 = activatableNotificationView;
    }
}
