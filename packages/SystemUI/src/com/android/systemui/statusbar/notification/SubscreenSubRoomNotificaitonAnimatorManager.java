package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.Property;
import android.view.HapticFeedbackConstants;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SubscreenSubRoomNotificaitonAnimatorManager {
    public final SubscreenNotificationInfoManager mNotificationInfoManager;
    public final Vibrator mVibrator;
    public final SubscreenNotificationController mSubscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
    public final VibrationEffect effect = VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(27), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);

    public SubscreenSubRoomNotificaitonAnimatorManager(SubscreenNotificationInfoManager subscreenNotificationInfoManager, Vibrator vibrator) {
        this.mNotificationInfoManager = subscreenNotificationInfoManager;
        this.mVibrator = vibrator;
    }

    public final Animator alphaAnimatedMainView(final long j, final View view, final Runnable runnable) {
        ObjectAnimator duration = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f, 0.0f).setDuration(j);
        duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonAnimatorManager.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                view.animate().alpha(1.0f).setDuration(j).setListener(null);
                SubscreenSubRoomNotificaitonAnimatorManager.this.mSubscreenNotificationController.mDeviceModel.mMainViewAnimator = null;
            }
        });
        duration.start();
        return duration;
    }

    public final Animator alphaViewAnimated(View view, final Runnable runnable, long j, float f, float f2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, f, f2);
        objectAnimatorOfFloat.setDuration(j);
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonAnimatorManager.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        objectAnimatorOfFloat.start();
        return objectAnimatorOfFloat;
    }

    public final void performDismissAllAnimations(Runnable runnable) {
        final Runnable runnable2;
        this.mSubscreenNotificationController.mDeviceModel.dismissImmediately(2);
        ArrayList arrayList = this.mNotificationInfoManager.mRecyclerViewItemHolderArray;
        int size = arrayList.size();
        Log.d("SubscreenSubRoomNotificaitonAnimatorManager", "performDismissAllAnimations() dismiss list size: " + size);
        int iMax = 140;
        int i = 180;
        boolean z = false;
        for (int i2 = size + (-1); i2 >= 0; i2--) {
            if (arrayList.size() <= i2) {
                Log.e("SubscreenSubRoomNotificaitonAnimatorManager", "Invalid dismiss position. size = " + arrayList.size() + ", index = " + i2);
            } else {
                SubscreenParentItemViewHolder subscreenParentItemViewHolder = (SubscreenParentItemViewHolder) arrayList.get(i2);
                if (i2 == 0 || i2 == size - 5) {
                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isHapticFeedbackEnabled()) {
                        this.mVibrator.vibrate(this.effect);
                    }
                    runnable2 = runnable;
                    z = true;
                } else {
                    runnable2 = null;
                }
                View view = subscreenParentItemViewHolder.itemView;
                float measuredWidth = view.getMeasuredWidth() + 100;
                if (!SubscreenNotificationInfoManager.canViewBeCleared(subscreenParentItemViewHolder.mInfo.mRow)) {
                    measuredWidth = 0.0f;
                }
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, measuredWidth);
                objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
                objectAnimatorOfFloat.setDuration(200L);
                if (i > 0) {
                    objectAnimatorOfFloat.setStartDelay(i);
                }
                objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonAnimatorManager.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            runnable3.run();
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                objectAnimatorOfFloat.start();
                iMax = Math.max(50, iMax - 10);
                i += iMax;
                if (z) {
                    return;
                }
            }
        }
    }

    public final void replyButtonAnimated(View view, final SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0 subscreenNotificationDetailAdapter$$ExternalSyntheticLambda0, float f, float f2, float f3, float f4) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, f, f2);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, f, f2);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, f3, f4);
        objectAnimatorOfFloat3.setInterpolator(Interpolators.LINEAR);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        animatorSet.setDuration(200L);
        animatorSet.setStartDelay(0L);
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonAnimatorManager.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Runnable runnable = subscreenNotificationDetailAdapter$$ExternalSyntheticLambda0;
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        animatorSet.start();
    }
}
