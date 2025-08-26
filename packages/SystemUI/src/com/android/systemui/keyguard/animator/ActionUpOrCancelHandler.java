package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardClickController;
import com.android.systemui.keyguard.KeyguardClickControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.shade.NotificationPanelViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: classes2.dex */
public final class ActionUpOrCancelHandler extends ActionHandlerType {
    public ActionUpOrCancelHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController;
        NotificationPanelViewController.AnonymousClass4 anonymousClass4;
        int i;
        KeyguardTouchAnimator keyguardTouchAnimator;
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2;
        KeyguardTouchAnimator keyguardTouchAnimator2;
        int actionMasked = motionEvent.getActionMasked();
        final KeyguardTouchAnimator keyguardTouchAnimator3 = this.parent;
        Log.d("KeyguardTouchAnimator", "onTouchEvent event=" + actionMasked + " distance=" + keyguardTouchAnimator3.distance);
        FullScreenViewController fullScreenViewController = keyguardTouchAnimator3.fullScreenViewController;
        fullScreenViewController.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().removeCallbacks(fullScreenViewController.longPressCallback);
        if (fullScreenViewController.isFullscreenModeEnabled) {
            fullScreenViewController.setFullScreenMode$frameworks__base__packages__SystemUI__android_common__SystemUI_core(false);
        }
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController3 = keyguardTouchAnimator3.editModeAnimatorController;
        if (!keyguardEditModeAnimatorController3.isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            Log.d("KeyguardEditModeAnimatorController", "actionUpOrCancel");
            if (keyguardEditModeAnimatorController3.touchDownAnimatorSet.isRunning()) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                KeyguardClickControllerImpl keyguardClickControllerImpl = (KeyguardClickControllerImpl) keyguardEditModeAnimatorController3.keyguardClickController;
                keyguardClickControllerImpl.getClass();
                com.android.systemui.keyguard.Log.d("KeyguardClickControllerImpl", "onNPVClicked ");
                PluginKeyguardStatusView pluginKeyguardStatusView = keyguardClickControllerImpl.manager.mFaceWidgetPlugin;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.onClockClicked(x, y);
                }
                keyguardEditModeAnimatorController3.touchDownAnimatorSet.cancel();
                AnimatorSet animatorSet = new AnimatorSet();
                List list = keyguardEditModeAnimatorController3.scaleViews;
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    keyguardTouchAnimator2 = keyguardEditModeAnimatorController3.keyguardTouchAnimator;
                    if (!zHasNext) {
                        break;
                    }
                    Object next = it.next();
                    if (keyguardTouchAnimator2.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                        arrayList.add(next);
                    }
                }
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    keyguardEditModeAnimatorController3.setViewScaleAnimation(animatorSet, keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) arrayList.get(i2)).intValue()), 1.0f, 500L, 0L);
                    i2++;
                    keyguardTouchAnimator2 = keyguardTouchAnimator2;
                }
                animatorSet.start();
            }
            keyguardEditModeAnimatorController3.cancel$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        }
        boolean z = true;
        if (motionEvent.getPointerCount() <= 1) {
            keyguardTouchAnimator3.isMultiTouch = false;
        }
        KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = keyguardTouchAnimator3.dymLockInjector;
        KeyguardTouchDymLockInjector.Direction direction = keyguardTouchDymLockInjector.mIsDynamicLockEnabled ? keyguardTouchDymLockInjector.mDirection : null;
        if (actionMasked == 3 && keyguardTouchAnimator3.isDraggingDownStarted) {
            Log.d("KeyguardTouchAnimator", "cancel swipe by onExpansionStarted");
            keyguardTouchAnimator3.setTouch(false);
            keyguardTouchAnimator3.hasDozeAmount = false;
            keyguardTouchAnimator3.setDraggingDownStarted(false);
            return true;
        }
        if (keyguardTouchAnimator3.isAnimationRunning$frameworks__base__packages__SystemUI__android_common__SystemUI_core() || keyguardTouchAnimator3.isUnlockExecuted || keyguardTouchAnimator3.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getAlpha() != 1.0f) {
            keyguardEditModeAnimatorController = keyguardEditModeAnimatorController3;
            anonymousClass4 = null;
            i = 1;
        } else if (keyguardTouchAnimator3.isMultiTouch || direction == null || direction == KeyguardTouchDymLockInjector.Direction.SWIPE || direction == KeyguardTouchDymLockInjector.Direction.TAP) {
            float f = keyguardTouchAnimator3.distance;
            float f2 = keyguardTouchAnimator3.touchSlop;
            KeyguardClickController keyguardClickController = keyguardTouchAnimator3.keyguardClickController;
            if (f < f2) {
                keyguardTouchAnimator3.resetChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1.0f);
                if (actionMasked == 1) {
                    KeyguardClickControllerImpl keyguardClickControllerImpl2 = (KeyguardClickControllerImpl) keyguardClickController;
                    if (((Boolean) keyguardClickControllerImpl2.isClickContainerArea.invoke(Integer.valueOf((int) motionEvent.getX()), Integer.valueOf((int) motionEvent.getY()))).booleanValue()) {
                        int x2 = (int) motionEvent.getX();
                        int y2 = (int) motionEvent.getY();
                        keyguardClickControllerImpl2.getClass();
                        com.android.systemui.keyguard.Log.d("KeyguardClickControllerImpl", "onNPVClicked ");
                        PluginKeyguardStatusView pluginKeyguardStatusView2 = keyguardClickControllerImpl2.manager.mFaceWidgetPlugin;
                        if (pluginKeyguardStatusView2 != null) {
                            pluginKeyguardStatusView2.onClockClicked(x2, y2);
                        }
                        keyguardEditModeAnimatorController = keyguardEditModeAnimatorController3;
                        i = 1;
                        anonymousClass4 = null;
                    }
                }
                PluginKeyguardStatusView pluginKeyguardStatusView3 = ((KeyguardClickControllerImpl) keyguardClickController).manager.mFaceWidgetPlugin;
                if (pluginKeyguardStatusView3 != null) {
                    pluginKeyguardStatusView3.onEmptyAreaClicked();
                }
                TapAffordanceViewController tapAffordanceViewController = keyguardTouchAnimator3.tapAffordanceViewController;
                tapAffordanceViewController.getClass();
                Log.d("KeyguardTouchAnimator", "showTapAffordanceAnimation");
                List list2 = tapAffordanceViewController.tapAffordanceViews;
                ArrayList arrayList2 = new ArrayList();
                Iterator it2 = list2.iterator();
                while (true) {
                    boolean zHasNext2 = it2.hasNext();
                    keyguardTouchAnimator = tapAffordanceViewController.keyguardTouchAnimator;
                    if (!zHasNext2) {
                        break;
                    }
                    Object next2 = it2.next();
                    if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next2).intValue())) {
                        arrayList2.add(next2);
                    }
                }
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                int size2 = arrayList2.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj = arrayList2.get(i3);
                    i3++;
                    arrayList3.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue()));
                }
                ArrayList arrayList4 = new ArrayList();
                int size3 = arrayList3.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object obj2 = arrayList3.get(i4);
                    i4++;
                    if (((View) obj2).getVisibility() == 0) {
                        arrayList4.add(obj2);
                    }
                }
                int size4 = arrayList4.size();
                int i5 = 0;
                int i6 = 0;
                while (i6 < size4) {
                    Object obj3 = arrayList4.get(i6);
                    i6++;
                    int i7 = i5 + 1;
                    if (i5 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    View view = (View) obj3;
                    if (i5 == 0) {
                        tapAffordanceViewController.isTapAnimationRunning = z;
                        keyguardEditModeAnimatorController2 = keyguardEditModeAnimatorController3;
                        keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().postDelayed(tapAffordanceViewController.restoreSpringAnimRunnable, 150L);
                    } else {
                        keyguardEditModeAnimatorController2 = keyguardEditModeAnimatorController3;
                    }
                    SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                    springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(500.0f, 0.78f);
                    springAnimation.animateToFinalPosition(1.1f);
                    ((ArrayList) tapAffordanceViewController.tapSpringAnimationList).add(springAnimation);
                    SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                    springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(500.0f, 0.78f);
                    springAnimation2.animateToFinalPosition(1.1f);
                    ((ArrayList) tapAffordanceViewController.tapSpringAnimationList).add(springAnimation2);
                    i5 = i7;
                    keyguardEditModeAnimatorController3 = keyguardEditModeAnimatorController2;
                    z = true;
                }
                keyguardEditModeAnimatorController = keyguardEditModeAnimatorController3;
                anonymousClass4 = null;
                KeyguardTouchSecurityInjector keyguardTouchSecurityInjector = keyguardTouchAnimator3.securityInjector;
                keyguardTouchSecurityInjector.getClass();
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && keyguardTouchSecurityInjector.mFingerprintManager != null && keyguardTouchSecurityInjector.mKeyguardUpdateMonitor.isFingerprintOptionEnabled()) {
                    keyguardTouchSecurityInjector.mFingerprintManager.semShowUdfpsIcon();
                }
            } else {
                keyguardEditModeAnimatorController = keyguardEditModeAnimatorController3;
                anonymousClass4 = null;
                if (f <= keyguardTouchAnimator3.swipeUnlockRadius || actionMasked == 6 || actionMasked == 3) {
                    if (actionMasked == 1 && keyguardEditModeAnimatorController.isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                        Log.d("KeyguardTouchAnimator", "skip because of EM");
                    } else {
                        PluginKeyguardStatusView pluginKeyguardStatusView4 = ((KeyguardClickControllerImpl) keyguardClickController).manager.mFaceWidgetPlugin;
                        if (pluginKeyguardStatusView4 != null) {
                            pluginKeyguardStatusView4.onEmptyAreaClicked();
                        }
                        keyguardTouchAnimator3.restoreChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                    }
                }
            }
            i = 1;
        } else {
            keyguardEditModeAnimatorController = keyguardEditModeAnimatorController3;
            i = 1;
            anonymousClass4 = null;
        }
        if (actionMasked == i) {
            String str = "onTouchEvent T=" + keyguardTouchAnimator3.touchSlop + ", D=" + keyguardTouchAnimator3.distance + ", R=" + keyguardTouchAnimator3.swipeUnlockRadius + ", W=" + direction + ", M=" + keyguardTouchAnimator3.updateDistanceCount;
            keyguardTouchAnimator3.loggingInjector.getClass();
            com.android.systemui.keyguard.Log.d("KeyguardTouchAnimator", str);
            NotificationPanelViewController.AnonymousClass4 anonymousClass42 = keyguardTouchAnimator3.callback;
            if (anonymousClass42 == null) {
                anonymousClass42 = anonymousClass4;
            }
            anonymousClass42.callUserActivity();
            if (direction == null || direction == KeyguardTouchDymLockInjector.Direction.SWIPE) {
                float f3 = keyguardTouchAnimator3.distance;
                if (f3 < keyguardTouchAnimator3.touchSlop) {
                    NotificationPanelViewController.AnonymousClass4 anonymousClass43 = keyguardTouchAnimator3.callback;
                    (anonymousClass43 != null ? anonymousClass43 : anonymousClass4).getClass();
                } else if (keyguardTouchAnimator3.swipeUnlockRadius >= f3) {
                    PointF pointF = keyguardTouchAnimator3.touchDownPos;
                    float f4 = pointF.x;
                    float f5 = pointF.y;
                    PointF pointF2 = keyguardTouchAnimator3.lastMovePos;
                    float f6 = pointF2.x;
                    float f7 = pointF2.y;
                    StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("no operation: (", f4, ",", f5, ") - (");
                    sbM.append(f6);
                    sbM.append(", ");
                    sbM.append(f7);
                    sbM.append(")");
                    Log.d("KeyguardTouchAnimator", sbM.toString());
                } else if (keyguardEditModeAnimatorController.isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                    Log.d("KeyguardTouchAnimator", "skip swipe because of edit mode");
                } else if (keyguardTouchAnimator3.getCanBeUnlock()) {
                    KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SWIPE);
                    Runnable runnable = new Runnable() { // from class: com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$handleMotionEvent$1$3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationPanelViewController.AnonymousClass4 anonymousClass44 = keyguardTouchAnimator3.callback;
                            if (anonymousClass44 == null) {
                                anonymousClass44 = null;
                            }
                            anonymousClass44.onUnlockExecuted();
                        }
                    };
                    com.android.systemui.keyguard.Log.i("KeyguardTouchBase", "unlockExecute()");
                    keyguardTouchAnimator3.isUnlockExecuted = true;
                    runnable.run();
                } else {
                    keyguardTouchAnimator3.restoreChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                }
            }
            keyguardTouchAnimator3.isMultiTouch = motionEvent.getPointerCount() >= 2;
        }
        keyguardTouchAnimator3.setTouch(false);
        keyguardTouchAnimator3.hasDozeAmount = false;
        keyguardTouchAnimator3.setDraggingDownStarted(false);
        keyguardTouchDymLockInjector.resetDynamicLock();
        return true;
    }
}
