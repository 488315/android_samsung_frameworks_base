package com.android.systemui.keyguard.animator;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceType;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActionDownHandler extends ActionHandlerType {
    public ActionDownHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x017c  */
    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        boolean z;
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController;
        StandaloneCoroutine standaloneCoroutine;
        boolean z2;
        Pair pair;
        KeyguardTouchAnimator keyguardTouchAnimator = this.parent;
        keyguardTouchAnimator.setTouch(true);
        boolean z3 = false;
        keyguardTouchAnimator.hasDozeAmount = !(keyguardTouchAnimator.dozeAmount == 0.0f);
        keyguardTouchAnimator.isMultiTouch = false;
        FullScreenViewController fullScreenViewController = keyguardTouchAnimator.fullScreenViewController;
        fullScreenViewController.isFullScreenModeShown = false;
        keyguardTouchAnimator.distance = 0.0f;
        keyguardTouchAnimator.updateDistanceCount = 0;
        PointF pointF = keyguardTouchAnimator.touchDownPos;
        pointF.x = motionEvent.getRawX();
        pointF.y = motionEvent.getRawY();
        ((ArrayList) keyguardTouchAnimator.tapAffordanceViewController.restoreSpringAnimationList).clear();
        PivotViewController pivotViewController = keyguardTouchAnimator.pivotViewController;
        pivotViewController.getClass();
        ArrayList arrayList = new ArrayList();
        List list = pivotViewController.pivotViews;
        for (Object obj : list) {
            if (!pivotViewController.hasView(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Log.d("KeyguardTouchAnimator", ((Number) it.next()).intValue() + " is null");
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            int intValue = ((Number) it2.next()).intValue();
            if (pivotViewController.hasView(intValue)) {
                View view = pivotViewController.getView(intValue);
                if (view.getVisibility() == 0 && (pair = (Pair) pivotViewController.pivot.get(intValue)) != null) {
                    view.setPivotX(((Number) ((Function) pair.first).apply(view)).floatValue());
                    view.setPivotY(((Number) ((Function) pair.second).apply(view)).floatValue());
                }
            }
        }
        if (keyguardTouchAnimator.hasDozeAmount) {
            return true;
        }
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardTouchAnimator.keyguardEditModeController;
        if (!keyguardEditModeControllerImpl.settingsHelper.isSupportTouchAndHoldToEdit() || keyguardEditModeControllerImpl.isEditMode) {
            com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "can not be EM=" + keyguardEditModeControllerImpl.isEditMode);
        } else if (keyguardEditModeControllerImpl.keyguardUpdateMonitor.isUserUnlocked()) {
            boolean isTablet = DeviceType.isTablet();
            Context context = keyguardTouchAnimator.context;
            if (isTablet) {
                if (context.getResources().getConfiguration().semDesktopModeEnabled == 1) {
                    com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "can not be : New Dex");
                } else if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
                    com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "can not be : Dex Standalone");
                }
            }
            if (CustomDeviceManager.getInstance().getProKioskManager().getProKioskState()) {
                com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "isKioskMode : proKiosk mode");
            } else {
                try {
                    if (EnterpriseDeviceManager.getInstance(context).getKioskMode().isKioskModeEnabled()) {
                        com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "isKioskMode : Kiosk mode");
                    }
                } catch (SecurityException e) {
                    com.android.systemui.keyguard.Log.m142w("KeyguardEditModeController", "SecurityException: " + e);
                }
                z2 = false;
                if (!z2) {
                    com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "can not be : Kiosk Modee");
                } else if ((DeviceType.isTablet() || (LsRune.LOCKUI_SUB_DISPLAY_LOCK && context.getResources().getConfiguration().semDisplayDeviceType == 0)) || context.getResources().getConfiguration().orientation == 1) {
                    z = true;
                    keyguardEditModeAnimatorController = keyguardTouchAnimator.editModeAnimatorController;
                    if ((z || !keyguardTouchAnimator.canLongPressArea(motionEvent) || keyguardEditModeAnimatorController.animatorSet.isRunning()) ? false : true) {
                        if (keyguardTouchAnimator.canLongPressArea(motionEvent) && !fullScreenViewController.isFullscreenModeEnabled && !fullScreenViewController.fullScreenAnimatorSet.isRunning() && !keyguardEditModeAnimatorController.m148x7abb39c2()) {
                            z3 = true;
                        }
                        if (z3) {
                            fullScreenViewController.getParentView().postDelayed(fullScreenViewController.longPressCallback, 500L);
                        }
                    } else {
                        Log.d("KeyguardEditModeAnimatorController", "actionDown editMode(" + keyguardEditModeAnimatorController.isEditMode() + ")");
                        if (!keyguardEditModeAnimatorController.isEditMode()) {
                            StandaloneCoroutine standaloneCoroutine2 = keyguardEditModeAnimatorController.longPressJob;
                            if (standaloneCoroutine2 != null && standaloneCoroutine2.isActive()) {
                                z3 = true;
                            }
                            if (z3 && (standaloneCoroutine = keyguardEditModeAnimatorController.longPressJob) != null) {
                                standaloneCoroutine.cancel(null);
                            }
                            DefaultScheduler defaultScheduler = Dispatchers.Default;
                            keyguardEditModeAnimatorController.longPressJob = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new KeyguardEditModeAnimatorController$actionDown$1(keyguardEditModeAnimatorController, null), 3);
                        }
                    }
                    return true;
                }
            }
            z2 = true;
            if (!z2) {
            }
        } else {
            com.android.systemui.keyguard.Log.m138d("KeyguardEditModeController", "can not be FBE");
        }
        z = false;
        keyguardEditModeAnimatorController = keyguardTouchAnimator.editModeAnimatorController;
        if ((z || !keyguardTouchAnimator.canLongPressArea(motionEvent) || keyguardEditModeAnimatorController.animatorSet.isRunning()) ? false : true) {
        }
        return true;
    }
}
