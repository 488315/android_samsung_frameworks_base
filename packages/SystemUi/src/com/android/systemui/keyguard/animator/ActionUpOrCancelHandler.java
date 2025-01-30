package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActionUpOrCancelHandler extends ActionHandlerType {
    public ActionUpOrCancelHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01f6  */
    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback;
        int actionMasked = motionEvent.getActionMasked();
        final KeyguardTouchAnimator keyguardTouchAnimator = this.parent;
        Log.d("KeyguardTouchAnimator", "onTouchEvent event=" + actionMasked + " distance=" + keyguardTouchAnimator.distance);
        FullScreenViewController fullScreenViewController = keyguardTouchAnimator.fullScreenViewController;
        fullScreenViewController.getParentView().removeCallbacks(fullScreenViewController.longPressCallback);
        int i = 0;
        if (fullScreenViewController.isFullscreenModeEnabled) {
            fullScreenViewController.m146x4f14f666(false);
        }
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = keyguardTouchAnimator.editModeAnimatorController;
        if (!keyguardEditModeAnimatorController.m148x7abb39c2()) {
            Log.d("KeyguardEditModeAnimatorController", "actionUpOrCancel");
            if (keyguardEditModeAnimatorController.touchDownAnimatorSet.isRunning()) {
                keyguardEditModeAnimatorController.touchDownAnimatorSet.cancel();
                AnimatorSet animatorSet = new AnimatorSet();
                ArrayList arrayList = new ArrayList();
                for (Object obj : keyguardEditModeAnimatorController.scaleViews) {
                    if (keyguardEditModeAnimatorController.hasView(((Number) obj).intValue())) {
                        arrayList.add(obj);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    keyguardEditModeAnimatorController.setViewScaleAnimation(animatorSet, keyguardEditModeAnimatorController.getView(((Number) it.next()).intValue()), 1.0f, 500L, 0L);
                }
                animatorSet.start();
            }
            keyguardEditModeAnimatorController.m147xc9fd32c0();
        }
        boolean z = true;
        if (motionEvent.getPointerCount() <= 1) {
            keyguardTouchAnimator.isMultiTouch = false;
        }
        KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = keyguardTouchAnimator.dymLockInjector;
        KeyguardTouchDymLockInjector.Direction direction = keyguardTouchDymLockInjector.mIsDynamicLockEnabled ? keyguardTouchDymLockInjector.mDirection : null;
        if (!keyguardTouchAnimator.m150x6c4411ab() && !keyguardTouchAnimator.isUnlockExecuted) {
            if ((((View) keyguardTouchAnimator.parentView$delegate.getValue()).getAlpha() == 1.0f) && (keyguardTouchAnimator.isMultiTouch || direction == null || direction == KeyguardTouchDymLockInjector.Direction.SWIPE || direction == KeyguardTouchDymLockInjector.Direction.TAP)) {
                float f = keyguardTouchAnimator.distance;
                if (f >= keyguardTouchAnimator.touchSlop) {
                    keyguardTouchSwipeCallback = null;
                    if (f <= keyguardTouchAnimator.swipeUnlockRadius || actionMasked == 6 || actionMasked == 3) {
                        if (keyguardEditModeAnimatorController.m148x7abb39c2()) {
                            Log.d("KeyguardTouchAnimator", "skip because of EM");
                        } else {
                            keyguardTouchAnimator.m152x84c4ab2c();
                        }
                    }
                    if (actionMasked == 1) {
                        String str = "onTouchEvent T=" + keyguardTouchAnimator.touchSlop + ", D=" + keyguardTouchAnimator.distance + ", R=" + keyguardTouchAnimator.swipeUnlockRadius + ", W=" + direction + ", M=" + keyguardTouchAnimator.updateDistanceCount;
                        keyguardTouchAnimator.loggingInjector.getClass();
                        com.android.systemui.keyguard.Log.m138d("KeyguardTouchAnimator", str);
                        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback2 = keyguardTouchAnimator.callback;
                        if (keyguardTouchSwipeCallback2 == null) {
                            keyguardTouchSwipeCallback2 = keyguardTouchSwipeCallback;
                        }
                        keyguardTouchSwipeCallback2.callUserActivity();
                        if (direction == null || direction == KeyguardTouchDymLockInjector.Direction.SWIPE) {
                            float f2 = keyguardTouchAnimator.distance;
                            if (f2 < keyguardTouchAnimator.touchSlop) {
                                KeyguardTouchSwipeCallback keyguardTouchSwipeCallback3 = keyguardTouchAnimator.callback;
                                if (keyguardTouchSwipeCallback3 != null) {
                                    keyguardTouchSwipeCallback = keyguardTouchSwipeCallback3;
                                }
                                keyguardTouchSwipeCallback.onAffordanceTap();
                            } else if (keyguardTouchAnimator.swipeUnlockRadius >= f2) {
                                PointF pointF = keyguardTouchAnimator.touchDownPos;
                                float f3 = pointF.x;
                                float f4 = pointF.y;
                                PointF pointF2 = keyguardTouchAnimator.lastMovePos;
                                float f5 = pointF2.x;
                                float f6 = pointF2.y;
                                StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("no operation: (", f3, ",", f4, ") - (");
                                m88m.append(f5);
                                m88m.append(", ");
                                m88m.append(f6);
                                m88m.append(")");
                                Log.d("KeyguardTouchAnimator", m88m.toString());
                            } else if (keyguardEditModeAnimatorController.m148x7abb39c2()) {
                                Log.d("KeyguardTouchAnimator", "skip swipe because of edit mode");
                            } else if (keyguardTouchAnimator.getCanBeUnlock()) {
                                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SWIPE);
                                Runnable runnable = new Runnable() { // from class: com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$handleMotionEvent$1$3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback4 = KeyguardTouchAnimator.this.callback;
                                        if (keyguardTouchSwipeCallback4 == null) {
                                            keyguardTouchSwipeCallback4 = null;
                                        }
                                        keyguardTouchSwipeCallback4.onUnlockExecuted();
                                    }
                                };
                                com.android.systemui.keyguard.Log.m141i("KeyguardTouchBase", "unlockExecute()");
                                keyguardTouchAnimator.isUnlockExecuted = true;
                                runnable.run();
                            } else {
                                keyguardTouchAnimator.m152x84c4ab2c();
                            }
                        }
                        keyguardTouchAnimator.isMultiTouch = motionEvent.getPointerCount() >= 2;
                    }
                    keyguardTouchAnimator.setTouch(false);
                    keyguardTouchAnimator.hasDozeAmount = false;
                    keyguardTouchDymLockInjector.resetDynamicLock();
                    return true;
                }
                keyguardTouchAnimator.m151x304bee4b();
                TapAffordanceViewController tapAffordanceViewController = keyguardTouchAnimator.tapAffordanceViewController;
                tapAffordanceViewController.getClass();
                Log.d("KeyguardTouchAnimator", "showTapAffordanceAnimation");
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : tapAffordanceViewController.tapAffordanceViews) {
                    if (tapAffordanceViewController.hasView(((Number) obj2).intValue())) {
                        arrayList2.add(obj2);
                    }
                }
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(tapAffordanceViewController.getView(((Number) it2.next()).intValue()));
                }
                ArrayList arrayList4 = new ArrayList();
                Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    Object next = it3.next();
                    if (((View) next).getVisibility() == 0) {
                        arrayList4.add(next);
                    }
                }
                Iterator it4 = arrayList4.iterator();
                while (it4.hasNext()) {
                    Object next2 = it4.next();
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    View view = (View) next2;
                    if (i == 0) {
                        tapAffordanceViewController.isTapAnimationRunning = z;
                        tapAffordanceViewController.getParentView().postDelayed(tapAffordanceViewController.restoreSpringAnimRunnable, 150L);
                    }
                    SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                    springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(500.0f, 0.78f);
                    springAnimation.animateToFinalPosition(1.1f);
                    ArrayList arrayList5 = (ArrayList) tapAffordanceViewController.tapSpringAnimationList;
                    arrayList5.add(springAnimation);
                    SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                    springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(500.0f, 0.78f);
                    springAnimation2.animateToFinalPosition(1.1f);
                    arrayList5.add(springAnimation2);
                    z = true;
                    i = i2;
                }
            }
        }
        keyguardTouchSwipeCallback = null;
        if (actionMasked == 1) {
        }
        keyguardTouchAnimator.setTouch(false);
        keyguardTouchAnimator.hasDozeAmount = false;
        keyguardTouchDymLockInjector.resetDynamicLock();
        return true;
    }
}
