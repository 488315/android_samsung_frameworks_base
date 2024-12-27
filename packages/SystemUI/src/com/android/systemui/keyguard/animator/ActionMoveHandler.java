package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlinx.coroutines.Job;

public final class ActionMoveHandler extends ActionHandlerType {
    public ActionMoveHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        KeyguardTouchAnimator keyguardTouchAnimator;
        final KeyguardTouchAnimator keyguardTouchAnimator2 = this.parent;
        if (!keyguardTouchAnimator2.isTouching) {
            return false;
        }
        if (keyguardTouchAnimator2.hasDozeAmount) {
            return true;
        }
        keyguardTouchAnimator2.userActivityForMove(new Runnable() { // from class: com.android.systemui.keyguard.animator.ActionMoveHandler$handleMotionEvent$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = KeyguardTouchAnimator.this.callback;
                if (keyguardTouchSwipeCallback == null) {
                    keyguardTouchSwipeCallback = null;
                }
                keyguardTouchSwipeCallback.callUserActivity();
            }
        });
        FullScreenViewController fullScreenViewController = keyguardTouchAnimator2.fullScreenViewController;
        keyguardTouchAnimator2.updateDistance(motionEvent, fullScreenViewController.isFullscreenModeEnabled);
        if (fullScreenViewController.isFullScreenModeShown) {
            Log.d("KeyguardTouchAnimator", "FullScreenView is started");
            keyguardTouchAnimator2.lockscreenShadeTransitionController.isKeyguardAnimatorStarted = true;
            return true;
        }
        float f = keyguardTouchAnimator2.distance;
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = keyguardTouchAnimator2.editModeAnimatorController;
        Log.d("KeyguardEditModeAnimatorController", "actionMove " + keyguardEditModeAnimatorController.isEditMode());
        if (!keyguardEditModeAnimatorController.isEditMode()) {
            boolean isNotSupportedAnimation = keyguardEditModeAnimatorController.isNotSupportedAnimation();
            KeyguardTouchAnimator keyguardTouchAnimator3 = keyguardEditModeAnimatorController.keyguardTouchAnimator;
            if (isNotSupportedAnimation) {
                int i = keyguardTouchAnimator3.lockEditorTouchSlop;
                if (i < f) {
                    Log.d("KeyguardEditModeAnimatorController", "cancel edit mode touchSlop=" + i + ", distance=" + f);
                    Job job = keyguardEditModeAnimatorController.longPressJob;
                    if (job != null && job.isActive()) {
                        Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
                        Job job2 = keyguardEditModeAnimatorController.longPressJob;
                        if (job2 != null) {
                            job2.cancel(null);
                        }
                    }
                }
            } else if (keyguardTouchAnimator3.lockEditorTouchSlop < f && !keyguardEditModeAnimatorController.isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                Log.d("KeyguardEditModeAnimatorController", "cancel edit mode touchSlop=" + keyguardTouchAnimator3.lockEditorTouchSlop + ", distance=" + f);
                keyguardEditModeAnimatorController.cancel$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            }
            if (keyguardTouchAnimator2.touchSlop < keyguardTouchAnimator2.distance) {
                KeyguardTouchBase.Companion.getClass();
                if (KeyguardTouchBase.DEBUG) {
                    Log.d("KeyguardTouchAnimator", "removeCallback touchSlop=" + keyguardTouchAnimator2.touchSlop + " distance=" + keyguardTouchAnimator2.distance);
                }
                fullScreenViewController.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().removeCallbacks(fullScreenViewController.longPressCallback);
            }
            KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = keyguardTouchAnimator2.dymLockInjector;
            if (keyguardTouchDymLockInjector.mIsDynamicLockEnabled) {
                int i2 = keyguardTouchAnimator2.swipeUnlockRadius;
                PointF pointF = keyguardTouchAnimator2.touchDownPos;
                keyguardTouchDymLockInjector.updateDirection(i2, pointF.x, pointF.y, motionEvent);
            }
            if (!keyguardTouchAnimator2.isAnimationRunning$frameworks__base__packages__SystemUI__android_common__SystemUI_core() && !keyguardTouchAnimator2.isUnlockExecuted && keyguardTouchAnimator2.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getAlpha() >= 1.0f && keyguardTouchAnimator2.distance > keyguardTouchAnimator2.touchSlop) {
                if (((KeyguardEditModeControllerImpl) keyguardTouchAnimator2.keyguardEditModeController).getVIRunning()) {
                    Log.d("KeyguardTouchAnimator", "updateChildViewVI skip : edit vi running");
                } else {
                    float f2 = keyguardTouchAnimator2.swipeUnlockRadius * 0.8f * 0.8f;
                    float max = Math.max(0.0f, Math.min(1.0f, (((f2 - keyguardTouchAnimator2.distance) / f2) * 1.0f) + 0.0f));
                    DragViewController dragViewController = keyguardTouchAnimator2.dragViewController;
                    List list = dragViewController.dragViews;
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (true) {
                        boolean hasNext = it.hasNext();
                        keyguardTouchAnimator = dragViewController.keyguardTouchAnimator;
                        if (!hasNext) {
                            break;
                        }
                        Object next = it.next();
                        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                            arrayList.add(next);
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it2.next()).intValue()));
                    }
                    ArrayList arrayList3 = new ArrayList();
                    Iterator it3 = arrayList2.iterator();
                    while (it3.hasNext()) {
                        Object next2 = it3.next();
                        if (((View) next2).getVisibility() == 0) {
                            arrayList3.add(next2);
                        }
                    }
                    Iterator it4 = arrayList3.iterator();
                    while (it4.hasNext()) {
                        ((View) it4.next()).setAlpha(max);
                    }
                    dragViewController.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(max);
                    float f3 = keyguardTouchAnimator2.swipeUnlockRadius * 0.1f;
                    float max2 = Math.max(0.0f, Math.min(1.0f, (((f3 - keyguardTouchAnimator2.distance) / f3) * 1.0f) + 0.0f));
                    List list2 = dragViewController.onlyAlphaDragViews;
                    ArrayList arrayList4 = new ArrayList();
                    for (Object obj : list2) {
                        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue())) {
                            arrayList4.add(obj);
                        }
                    }
                    ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
                    Iterator it5 = arrayList4.iterator();
                    while (it5.hasNext()) {
                        arrayList5.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it5.next()).intValue()));
                    }
                    ArrayList arrayList6 = new ArrayList();
                    Iterator it6 = arrayList5.iterator();
                    while (it6.hasNext()) {
                        Object next3 = it6.next();
                        if (((View) next3).getVisibility() == 0) {
                            arrayList6.add(next3);
                        }
                    }
                    Iterator it7 = arrayList6.iterator();
                    while (it7.hasNext()) {
                        ((View) it7.next()).setAlpha(max2);
                    }
                    View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0) ? keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0) : null;
                    if (!dragViewController.unlockViewHideAnimatorSet.isRunning() && view$frameworks__base__packages__SystemUI__android_common__SystemUI_core != null && view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getAlpha() != 0.0f) {
                        AnimatorSet createAnimatorSet$default = DragViewController.createAnimatorSet$default(dragViewController, 0);
                        dragViewController.setViewAnimation(createAnimatorSet$default, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, -1.0f, 0.0f);
                        createAnimatorSet$default.start();
                    }
                    float max3 = Math.max(1.0f, Math.min(1.07f, ((keyguardTouchAnimator2.distance / (keyguardTouchAnimator2.swipeUnlockRadius * 0.8f)) * 0.07000005f) + 1.0f));
                    if (Float.isNaN(max3)) {
                        Log.d("KeyguardTouchAnimator", "scale is NaN, distance = " + keyguardTouchAnimator2.distance + ", swipeUnlockRadius " + keyguardTouchAnimator2.swipeUnlockRadius);
                    } else {
                        keyguardTouchAnimator2.notiScale = max3;
                        List list3 = dragViewController.dragViews;
                        ArrayList arrayList7 = new ArrayList();
                        for (Object obj2 : list3) {
                            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue())) {
                                arrayList7.add(obj2);
                            }
                        }
                        ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList7, 10));
                        Iterator it8 = arrayList7.iterator();
                        while (it8.hasNext()) {
                            arrayList8.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it8.next()).intValue()));
                        }
                        ArrayList arrayList9 = new ArrayList();
                        Iterator it9 = arrayList8.iterator();
                        while (it9.hasNext()) {
                            Object next4 = it9.next();
                            if (((View) next4).getVisibility() == 0) {
                                arrayList9.add(next4);
                            }
                        }
                        Iterator it10 = arrayList9.iterator();
                        while (it10.hasNext()) {
                            View view = (View) it10.next();
                            view.setScaleX(max3);
                            view.setScaleY(max3);
                        }
                    }
                }
            }
        }
        return true;
    }
}
