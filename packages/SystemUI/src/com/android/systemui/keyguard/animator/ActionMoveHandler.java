package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class ActionMoveHandler extends ActionHandlerType {
    public ActionMoveHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        KeyguardTouchAnimator keyguardTouchAnimator;
        final KeyguardTouchAnimator keyguardTouchAnimator2 = this.parent;
        int i = 0;
        if (!keyguardTouchAnimator2.isTouching) {
            return false;
        }
        boolean z = true;
        if (!keyguardTouchAnimator2.hasDozeAmount) {
            keyguardTouchAnimator2.userActivityForMove(new Runnable() { // from class: com.android.systemui.keyguard.animator.ActionMoveHandler$handleMotionEvent$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationPanelViewController.AnonymousClass4 anonymousClass4 = keyguardTouchAnimator2.callback;
                    if (anonymousClass4 == null) {
                        anonymousClass4 = null;
                    }
                    anonymousClass4.callUserActivity();
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
                boolean zIsNotSupportedAnimation = keyguardEditModeAnimatorController.isNotSupportedAnimation();
                KeyguardTouchAnimator keyguardTouchAnimator3 = keyguardEditModeAnimatorController.keyguardTouchAnimator;
                if (zIsNotSupportedAnimation) {
                    int i2 = keyguardTouchAnimator3.lockEditorTouchSlop;
                    if (i2 < f) {
                        Log.d("KeyguardEditModeAnimatorController", "cancel edit mode touchSlop=" + i2 + ", distance=" + f);
                        StandaloneCoroutine standaloneCoroutine = keyguardEditModeAnimatorController.longPressJob;
                        if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
                            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
                            StandaloneCoroutine standaloneCoroutine2 = keyguardEditModeAnimatorController.longPressJob;
                            if (standaloneCoroutine2 != null) {
                                standaloneCoroutine2.cancel(null);
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
                    int i3 = keyguardTouchAnimator2.swipeUnlockRadius;
                    PointF pointF = keyguardTouchAnimator2.touchDownPos;
                    keyguardTouchDymLockInjector.updateDirection(i3, pointF.x, pointF.y, motionEvent);
                }
                if (!keyguardTouchAnimator2.isAnimationRunning$frameworks__base__packages__SystemUI__android_common__SystemUI_core() && !keyguardTouchAnimator2.isUnlockExecuted && keyguardTouchAnimator2.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getAlpha() >= 1.0f && keyguardTouchAnimator2.distance > keyguardTouchAnimator2.touchSlop) {
                    if (((KeyguardEditModeControllerImpl) keyguardTouchAnimator2.keyguardEditModeController).getVIRunning()) {
                        Log.d("KeyguardTouchAnimator", "updateChildViewVI skip : edit vi running");
                        return true;
                    }
                    float f2 = keyguardTouchAnimator2.swipeUnlockRadius * 0.8f * 0.8f;
                    float fMax = Math.max(0.0f, Math.min(1.0f, (((f2 - keyguardTouchAnimator2.distance) / f2) * 1.0f) + 0.0f));
                    DragViewController dragViewController = keyguardTouchAnimator2.dragViewController;
                    List list = dragViewController.dragViews;
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (true) {
                        boolean zHasNext = it.hasNext();
                        keyguardTouchAnimator = dragViewController.keyguardTouchAnimator;
                        if (!zHasNext) {
                            break;
                        }
                        Object next = it.next();
                        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                            arrayList.add(next);
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    int size = arrayList.size();
                    int i4 = 0;
                    while (i4 < size) {
                        Object obj = arrayList.get(i4);
                        i4++;
                        arrayList2.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue()));
                        z = z;
                    }
                    boolean z2 = z;
                    ArrayList arrayList3 = new ArrayList();
                    int size2 = arrayList2.size();
                    int i5 = 0;
                    while (i5 < size2) {
                        Object obj2 = arrayList2.get(i5);
                        i5++;
                        if (((View) obj2).getVisibility() == 0) {
                            arrayList3.add(obj2);
                        }
                    }
                    int size3 = arrayList3.size();
                    int i6 = 0;
                    while (i6 < size3) {
                        Object obj3 = arrayList3.get(i6);
                        i6++;
                        ((View) obj3).setAlpha(fMax);
                    }
                    dragViewController.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(fMax);
                    float f3 = keyguardTouchAnimator2.swipeUnlockRadius * 0.1f;
                    float fMax2 = Math.max(0.0f, Math.min(1.0f, (((f3 - keyguardTouchAnimator2.distance) / f3) * 1.0f) + 0.0f));
                    List list2 = dragViewController.onlyAlphaDragViews;
                    ArrayList arrayList4 = new ArrayList();
                    for (Object obj4 : list2) {
                        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj4).intValue())) {
                            arrayList4.add(obj4);
                        }
                    }
                    ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
                    int size4 = arrayList4.size();
                    int i7 = 0;
                    while (i7 < size4) {
                        Object obj5 = arrayList4.get(i7);
                        i7++;
                        arrayList5.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj5).intValue()));
                    }
                    ArrayList arrayList6 = new ArrayList();
                    int size5 = arrayList5.size();
                    int i8 = 0;
                    while (i8 < size5) {
                        Object obj6 = arrayList5.get(i8);
                        i8++;
                        if (((View) obj6).getVisibility() == 0) {
                            arrayList6.add(obj6);
                        }
                    }
                    int size6 = arrayList6.size();
                    int i9 = 0;
                    while (i9 < size6) {
                        Object obj7 = arrayList6.get(i9);
                        i9++;
                        ((View) obj7).setAlpha(fMax2);
                    }
                    View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0) ? keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0) : null;
                    if (!dragViewController.unlockViewHideAnimatorSet.isRunning() && view$frameworks__base__packages__SystemUI__android_common__SystemUI_core != null && view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getAlpha() != 0.0f) {
                        AnimatorSet animatorSetCreateAnimatorSet$default = DragViewController.createAnimatorSet$default(dragViewController, 0);
                        dragViewController.setViewAnimation(animatorSetCreateAnimatorSet$default, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, -1.0f, 0.0f);
                        animatorSetCreateAnimatorSet$default.start();
                    }
                    float fMax3 = Math.max(1.0f, Math.min(1.07f, ((keyguardTouchAnimator2.distance / (keyguardTouchAnimator2.swipeUnlockRadius * 0.8f)) * 0.07000005f) + 1.0f));
                    if (Float.isNaN(fMax3)) {
                        Log.d("KeyguardTouchAnimator", "scale is NaN, distance = " + keyguardTouchAnimator2.distance + ", swipeUnlockRadius " + keyguardTouchAnimator2.swipeUnlockRadius);
                        return z2;
                    }
                    keyguardTouchAnimator2.notiScale = fMax3;
                    List list3 = dragViewController.dragViews;
                    ArrayList arrayList7 = new ArrayList();
                    for (Object obj8 : list3) {
                        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj8).intValue())) {
                            arrayList7.add(obj8);
                        }
                    }
                    ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList7, 10));
                    int size7 = arrayList7.size();
                    int i10 = 0;
                    while (i10 < size7) {
                        Object obj9 = arrayList7.get(i10);
                        i10++;
                        arrayList8.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj9).intValue()));
                    }
                    ArrayList arrayList9 = new ArrayList();
                    int size8 = arrayList8.size();
                    int i11 = 0;
                    while (i11 < size8) {
                        Object obj10 = arrayList8.get(i11);
                        i11++;
                        if (((View) obj10).getVisibility() == 0) {
                            arrayList9.add(obj10);
                        }
                    }
                    int size9 = arrayList9.size();
                    while (i < size9) {
                        Object obj11 = arrayList9.get(i);
                        i++;
                        View view = (View) obj11;
                        view.setScaleX(fMax3);
                        view.setScaleY(fMax3);
                    }
                    return z2;
                }
            }
        }
        return true;
    }
}
