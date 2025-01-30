package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.samsung.android.knox.p045ex.knoxAI.KnoxAiManagerInternal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActionMoveHandler extends ActionHandlerType {
    public ActionMoveHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c8  */
    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        boolean z;
        final KeyguardTouchAnimator keyguardTouchAnimator = this.parent;
        if (!keyguardTouchAnimator.isTouching) {
            return false;
        }
        if (keyguardTouchAnimator.hasDozeAmount) {
            return true;
        }
        Runnable runnable = new Runnable() { // from class: com.android.systemui.keyguard.animator.ActionMoveHandler$handleMotionEvent$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = KeyguardTouchAnimator.this.callback;
                if (keyguardTouchSwipeCallback == null) {
                    keyguardTouchSwipeCallback = null;
                }
                keyguardTouchSwipeCallback.callUserActivity();
            }
        };
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - KnoxAiManagerInternal.CONN_MAX_WAIT_TIME > keyguardTouchAnimator.userActivityInvokedTime) {
            runnable.run();
            keyguardTouchAnimator.userActivityInvokedTime = currentTimeMillis;
        }
        FullScreenViewController fullScreenViewController = keyguardTouchAnimator.fullScreenViewController;
        keyguardTouchAnimator.updateDistance(motionEvent, fullScreenViewController.isFullscreenModeEnabled);
        if (fullScreenViewController.isFullScreenModeShown) {
            return true;
        }
        float f = keyguardTouchAnimator.distance;
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = keyguardTouchAnimator.editModeAnimatorController;
        Log.d("KeyguardEditModeAnimatorController", "actionMove " + keyguardEditModeAnimatorController.isEditMode());
        if (!keyguardEditModeAnimatorController.isEditMode()) {
            boolean isNotSupportedAnimation = keyguardEditModeAnimatorController.isNotSupportedAnimation();
            KeyguardTouchAnimator keyguardTouchAnimator2 = keyguardEditModeAnimatorController.keyguardTouchAnimator;
            if (isNotSupportedAnimation) {
                int i = keyguardTouchAnimator2.lockEditorTouchSlop;
                if (i < f) {
                    Log.d("KeyguardEditModeAnimatorController", "cancel edit mode touchSlop=" + i + ", distance=" + f);
                    StandaloneCoroutine standaloneCoroutine = keyguardEditModeAnimatorController.longPressJob;
                    if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
                        Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
                        StandaloneCoroutine standaloneCoroutine2 = keyguardEditModeAnimatorController.longPressJob;
                        if (standaloneCoroutine2 != null) {
                            standaloneCoroutine2.cancel(null);
                        }
                    }
                }
            } else if (keyguardTouchAnimator2.lockEditorTouchSlop < f && !keyguardEditModeAnimatorController.m148x7abb39c2()) {
                Log.d("KeyguardEditModeAnimatorController", "cancel edit mode touchSlop=" + keyguardTouchAnimator2.lockEditorTouchSlop + ", distance=" + f);
                keyguardEditModeAnimatorController.m147xc9fd32c0();
            }
            z = false;
            if (!z) {
                return true;
            }
            if (keyguardTouchAnimator.touchSlop < keyguardTouchAnimator.distance) {
                KeyguardTouchBase.Companion.getClass();
                if (KeyguardTouchBase.DEBUG) {
                    Log.d("KeyguardTouchAnimator", "removeCallback touchSlop=" + keyguardTouchAnimator.touchSlop + " distance=" + keyguardTouchAnimator.distance);
                }
                fullScreenViewController.getParentView().removeCallbacks(fullScreenViewController.longPressCallback);
            }
            KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = keyguardTouchAnimator.dymLockInjector;
            if (keyguardTouchDymLockInjector.mIsDynamicLockEnabled) {
                int i2 = keyguardTouchAnimator.swipeUnlockRadius;
                PointF pointF = keyguardTouchAnimator.touchDownPos;
                keyguardTouchDymLockInjector.updateDirection(i2, pointF.x, pointF.y, motionEvent);
            }
            if (!keyguardTouchAnimator.m150x6c4411ab() && !keyguardTouchAnimator.isUnlockExecuted && ((View) keyguardTouchAnimator.parentView$delegate.getValue()).getAlpha() >= 1.0f && keyguardTouchAnimator.distance > keyguardTouchAnimator.touchSlop) {
                if (((KeyguardEditModeControllerImpl) keyguardTouchAnimator.keyguardEditModeController).getVIRunning()) {
                    Log.d("KeyguardTouchAnimator", "updateChildViewVI skip : edit vi running");
                } else {
                    float f2 = keyguardTouchAnimator.swipeUnlockRadius * 0.8f;
                    float max = Math.max(0.0f, Math.min(1.0f, (((f2 - keyguardTouchAnimator.distance) / f2) * 1.0f) + 0.0f));
                    DragViewController dragViewController = keyguardTouchAnimator.dragViewController;
                    dragViewController.getClass();
                    ArrayList arrayList = new ArrayList();
                    List list = dragViewController.dragViews;
                    for (Object obj : list) {
                        if (dragViewController.hasView(((Number) obj).intValue())) {
                            arrayList.add(obj);
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(dragViewController.getView(((Number) it.next()).intValue()));
                    }
                    ArrayList arrayList3 = new ArrayList();
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        Object next = it2.next();
                        if (((View) next).getVisibility() == 0) {
                            arrayList3.add(next);
                        }
                    }
                    Iterator it3 = arrayList3.iterator();
                    while (it3.hasNext()) {
                        ((View) it3.next()).setAlpha(max);
                    }
                    float f3 = keyguardTouchAnimator.swipeUnlockRadius * 0.1f;
                    float max2 = Math.max(0.0f, Math.min(1.0f, (((f3 - keyguardTouchAnimator.distance) / f3) * 1.0f) + 0.0f));
                    ArrayList arrayList4 = new ArrayList();
                    for (Object obj2 : dragViewController.onlyAlphaDragViews) {
                        if (dragViewController.hasView(((Number) obj2).intValue())) {
                            arrayList4.add(obj2);
                        }
                    }
                    ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
                    Iterator it4 = arrayList4.iterator();
                    while (it4.hasNext()) {
                        arrayList5.add(dragViewController.getView(((Number) it4.next()).intValue()));
                    }
                    ArrayList arrayList6 = new ArrayList();
                    Iterator it5 = arrayList5.iterator();
                    while (it5.hasNext()) {
                        Object next2 = it5.next();
                        if (((View) next2).getVisibility() == 0) {
                            arrayList6.add(next2);
                        }
                    }
                    Iterator it6 = arrayList6.iterator();
                    while (it6.hasNext()) {
                        ((View) it6.next()).setAlpha(max2);
                    }
                    View view = dragViewController.hasView(0) ? dragViewController.getView(0) : null;
                    if (!dragViewController.unlockViewHideAnimatorSet.isRunning() && view != null) {
                        if (!(view.getAlpha() == 0.0f)) {
                            AnimatorSet createAnimatorSet$default = DragViewController.createAnimatorSet$default(dragViewController, 0);
                            ViewAnimationController.setViewAnimation(createAnimatorSet$default, view, -1.0f, 0.0f);
                            createAnimatorSet$default.start();
                        }
                    }
                    float max3 = Math.max(1.0f, Math.min(1.2f, ((keyguardTouchAnimator.distance / f2) * 0.20000005f) + 1.0f));
                    if (Float.isNaN(max3)) {
                        Log.d("KeyguardTouchAnimator", "scale is NaN, distance = " + keyguardTouchAnimator.distance + ", swipeUnlockRadius " + keyguardTouchAnimator.swipeUnlockRadius);
                    } else {
                        keyguardTouchAnimator.notiScale = max3;
                        ArrayList arrayList7 = new ArrayList();
                        for (Object obj3 : list) {
                            if (dragViewController.hasView(((Number) obj3).intValue())) {
                                arrayList7.add(obj3);
                            }
                        }
                        ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList7, 10));
                        Iterator it7 = arrayList7.iterator();
                        while (it7.hasNext()) {
                            arrayList8.add(dragViewController.getView(((Number) it7.next()).intValue()));
                        }
                        ArrayList arrayList9 = new ArrayList();
                        Iterator it8 = arrayList8.iterator();
                        while (it8.hasNext()) {
                            Object next3 = it8.next();
                            if (((View) next3).getVisibility() == 0) {
                                arrayList9.add(next3);
                            }
                        }
                        Iterator it9 = arrayList9.iterator();
                        while (it9.hasNext()) {
                            View view2 = (View) it9.next();
                            view2.setScaleX(max3);
                            view2.setScaleY(max3);
                        }
                    }
                }
            }
            return true;
        }
        z = true;
        if (!z) {
        }
    }
}
