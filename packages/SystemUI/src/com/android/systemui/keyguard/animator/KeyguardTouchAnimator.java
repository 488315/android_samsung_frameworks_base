package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.PointF;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.DisplayInfo;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.systemui.CscRune;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.KeyguardStatusViewAlphaChangeControllerWrapper;
import com.android.systemui.keyguard.KeyguardClickController;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.animator.ActionHandlerType;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class KeyguardTouchAnimator extends KeyguardTouchBase implements Gefingerpoken, Dumpable {
    public final Map actionHandlerTypes;
    public KeyguardTouchSwipeCallback callback;
    public MotionEvent doubleTapDownEvent;
    public final int doubleTapSlop;
    public float dozeAmount;
    public final DragViewController dragViewController;
    public final KeyguardTouchDymLockInjector dymLockInjector;
    public final KeyguardEditModeAnimatorController editModeAnimatorController;
    public final FullScreenViewController fullScreenViewController;
    public final GestureDetector gestureDetector;
    public final KeyguardClickController keyguardClickController;
    public final KeyguardEditModeController keyguardEditModeController;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper;
    public final KeyguardWallpaper keyguardWallpaper;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final KeyguardTouchLoggingInjector loggingInjector;
    public float notiScale;
    public final Lazy parentView$delegate;
    public final PivotViewController pivotViewController;
    public final StatusBarStateController sbStateController;
    public final ScreenOnAffordanceViewController screenOnViewController;
    public final KeyguardTouchSecurityInjector securityInjector;
    private final SettingsHelper settingsHelper;
    public final TapAffordanceViewController tapAffordanceViewController;
    public KeyguardTouchViewInjector viewInjector;
    public final SparseArray views;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardTouchAnimator(Context context, final KeyguardUpdateMonitor keyguardUpdateMonitor, final PowerManager powerManager, KeyguardStateController keyguardStateController, KeyguardTouchDymLockInjector keyguardTouchDymLockInjector, KeyguardTouchLoggingInjector keyguardTouchLoggingInjector, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, KeyguardEditModeController keyguardEditModeController, KeyguardWallpaper keyguardWallpaper, SecRotationWatcher secRotationWatcher, LockscreenShadeTransitionController lockscreenShadeTransitionController, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper, KeyguardClickController keyguardClickController, CoroutineScope coroutineScope, PowerInteractor powerInteractor, DozeParameters dozeParameters, SelectedUserInteractor selectedUserInteractor) {
        super(context, keyguardUpdateMonitor);
        this.keyguardStateController = keyguardStateController;
        this.dymLockInjector = keyguardTouchDymLockInjector;
        this.loggingInjector = keyguardTouchLoggingInjector;
        this.securityInjector = keyguardTouchSecurityInjector;
        this.sbStateController = statusBarStateController;
        this.settingsHelper = settingsHelper;
        this.keyguardEditModeController = keyguardEditModeController;
        this.keyguardWallpaper = keyguardWallpaper;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.keyguardStatusViewAlphaChangeControllerWrapper = keyguardStatusViewAlphaChangeControllerWrapper;
        this.keyguardClickController = keyguardClickController;
        this.parentView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator$parentView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardTouchViewInjector keyguardTouchViewInjector = KeyguardTouchAnimator.this.viewInjector;
                if (keyguardTouchViewInjector == null) {
                    keyguardTouchViewInjector = null;
                }
                return ((NotificationPanelViewController) keyguardTouchViewInjector).mView;
            }
        });
        this.views = new SparseArray();
        this.actionHandlerTypes = new LinkedHashMap();
        this.tapAffordanceViewController = new TapAffordanceViewController(this);
        this.pivotViewController = new PivotViewController(this);
        this.screenOnViewController = new ScreenOnAffordanceViewController(this, powerInteractor, coroutineScope, dozeParameters);
        this.fullScreenViewController = new FullScreenViewController(this);
        this.editModeAnimatorController = new KeyguardEditModeAnimatorController(this, selectedUserInteractor, keyguardEditModeController, keyguardWallpaper, keyguardUpdateMonitor, secRotationWatcher, settingsHelper, keyguardClickController);
        this.dragViewController = new DragViewController(this, keyguardStatusViewAlphaChangeControllerWrapper);
        initDimens$5();
        statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                KeyguardTouchAnimator.this.dozeAmount = f;
                if (f == 0.0f) {
                    Log.d("KeyguardTouchAnimator", "dozeAmount cleared");
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                if (i != 1) {
                    KeyguardTouchAnimator.this.editModeAnimatorController.startCancelAnimation();
                }
            }
        });
        this.doubleTapSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                Log.d("KeyguardTouchAnimator", "onDoubleTap");
                KeyguardTouchAnimator.this.doubleTapDownEvent = motionEvent;
                return true;
            }

            /* JADX WARN: Removed duplicated region for block: B:23:0x008d  */
            /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onDoubleTapEvent(android.view.MotionEvent r10) {
                /*
                    r9 = this;
                    int r0 = r10.getAction()
                    r1 = 0
                    r2 = 1
                    if (r0 != r2) goto La5
                    com.android.systemui.keyguard.animator.KeyguardTouchAnimator r0 = com.android.systemui.keyguard.animator.KeyguardTouchAnimator.this
                    android.view.MotionEvent r3 = r0.doubleTapDownEvent
                    java.lang.String r4 = "KeyguardTouchAnimator"
                    if (r3 == 0) goto L85
                    long r5 = r10.getEventTime()
                    long r7 = r3.getEventTime()
                    long r5 = r5 - r7
                    r7 = 300(0x12c, double:1.48E-321)
                    int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                    if (r7 <= 0) goto L31
                    java.lang.StringBuilder r10 = new java.lang.StringBuilder
                    java.lang.String r0 = "isConsideredDoubleTap: time out deltaTime="
                    r10.<init>(r0)
                    r10.append(r5)
                    java.lang.String r10 = r10.toString()
                    android.util.Log.d(r4, r10)
                    goto L85
                L31:
                    float r5 = r3.getX()
                    int r5 = (int) r5
                    float r6 = r10.getX()
                    int r6 = (int) r6
                    int r5 = r5 - r6
                    float r6 = r3.getY()
                    int r6 = (int) r6
                    float r7 = r10.getY()
                    int r7 = (int) r7
                    int r6 = r6 - r7
                    int r3 = r3.getFlags()
                    r3 = r3 & 8
                    if (r3 == 0) goto L51
                    r0 = r1
                    goto L54
                L51:
                    int r0 = r0.doubleTapSlop
                    int r0 = r0 * r0
                L54:
                    int r3 = r5 * r5
                    int r7 = r6 * r6
                    int r7 = r7 + r3
                    if (r7 < r0) goto L69
                    java.lang.String r10 = "isConsideredDoubleTap: over slop="
                    java.lang.String r0 = ", deltaX="
                    java.lang.String r3 = ", deltaY="
                    java.lang.StringBuilder r10 = androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(r7, r5, r10, r0, r3)
                    androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0.m(r6, r4, r10)
                    goto L85
                L69:
                    com.android.systemui.keyguard.animator.KeyguardTouchAnimator r0 = com.android.systemui.keyguard.animator.KeyguardTouchAnimator.this
                    com.android.systemui.statusbar.policy.KeyguardStateController r3 = r0.keyguardStateController
                    com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r3 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r3
                    boolean r3 = r3.mKeyguardGoingAway
                    if (r3 != 0) goto L85
                    com.android.systemui.keyguard.animator.KeyguardTouchSecurityInjector r0 = r0.securityInjector
                    boolean r10 = r0.isFingerprintArea(r10)
                    if (r10 == 0) goto L83
                    com.android.keyguard.KeyguardUpdateMonitor r10 = r2
                    boolean r10 = r10.isFingerprintDetectionRunning()
                    if (r10 != 0) goto L85
                L83:
                    r10 = r2
                    goto L86
                L85:
                    r10 = r1
                L86:
                    java.lang.String r0 = "onDoubleTapEvent executeDoubleTap="
                    com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m(r0, r4, r10)
                    if (r10 == 0) goto La5
                    android.os.PowerManager r10 = r3
                    long r3 = android.os.SystemClock.uptimeMillis()
                    r0 = 23
                    r10.goToSleep(r3, r0, r1)
                    com.android.systemui.keyguard.animator.KeyguardTouchAnimator r9 = com.android.systemui.keyguard.animator.KeyguardTouchAnimator.this
                    r9.getClass()
                    java.lang.String r9 = "101"
                    java.lang.String r10 = "LSE1012"
                    com.android.systemui.util.SystemUIAnalytics.sendEventLog(r9, r10)
                    r1 = r2
                La5:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.animator.KeyguardTouchAnimator.AnonymousClass2.onDoubleTapEvent(android.view.MotionEvent):boolean");
            }
        });
        lockscreenShadeTransitionController.addCallback(new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator.3
            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void onExpansionReset() {
                Log.d("KeyguardTouchAnimator", "onExpansionReset");
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardUpdateMonitor;
                if (keyguardUpdateMonitor2.isFingerprintOptionEnabled()) {
                    keyguardUpdateMonitor2.setPanelExpandingStarted(false);
                }
                KeyguardTouchAnimator.this.reset(false);
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void onExpansionStarted() {
                Log.d("KeyguardTouchAnimator", "onExpansionStarted()");
                KeyguardTouchAnimator keyguardTouchAnimator = KeyguardTouchAnimator.this;
                keyguardTouchAnimator.setDraggingDownStarted(true);
                keyguardTouchAnimator.resetChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1.0f);
            }

            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void setTransitionToFullShadeAmount(float f) {
                final KeyguardTouchAnimator keyguardTouchAnimator = KeyguardTouchAnimator.this;
                keyguardTouchAnimator.userActivityForMove(new Runnable() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator$3$setTransitionToFullShadeAmount$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = KeyguardTouchAnimator.this.callback;
                        if (keyguardTouchSwipeCallback == null) {
                            keyguardTouchSwipeCallback = null;
                        }
                        keyguardTouchSwipeCallback.callUserActivity();
                    }
                });
            }
        });
    }

    public static void showViewState(View view) {
        Log.d("KeyguardTouchAnimator", "v=" + view + " alpha=" + view.getAlpha() + " scale=" + view.getScaleX() + "," + view.getScaleY());
    }

    public final boolean canLongPressArea(MotionEvent motionEvent) {
        return !this.securityInjector.isFingerprintArea(motionEvent) && this.touchDownPos.y < ((float) (getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getHeight() - this.longPressAllowHeight));
    }

    public final View getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return (View) this.parentView$delegate.getValue();
    }

    public final View getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i) {
        return (View) this.views.get(i);
    }

    public final boolean hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i) {
        if (this.views.indexOfKey(i) >= 0) {
            return true;
        }
        switch (i) {
            case 0:
                Object obj = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj != null ? obj : null)).mKeyguardStatusBar;
                break;
            case 1:
                Object obj2 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj2 != null ? obj2 : null)).mKeyguardStatusViewController.getClockView();
                break;
            case 2:
                Object obj3 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj3 != null ? obj3 : null)).mNotificationStackScrollLayoutController.mView;
                break;
            case 3:
                KeyguardTouchViewInjector keyguardTouchViewInjector = this.viewInjector;
                if (keyguardTouchViewInjector == null) {
                    keyguardTouchViewInjector = null;
                }
                PluginNotificationController pluginNotificationController = ((NotificationPanelViewController) keyguardTouchViewInjector).mLockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                if (pluginNotificationController != null) {
                    r0 = pluginNotificationController.getIconContainer();
                    break;
                }
                break;
            case 4:
                Object obj4 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj4 != null ? obj4 : null)).mKeyguardBottomArea.getLeftView();
                break;
            case 5:
                Object obj5 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj5 != null ? obj5 : null)).mKeyguardBottomArea.getRightView();
                break;
            case 6:
                Object obj6 = this.viewInjector;
                r0 = (ViewGroup) ((NotificationPanelViewController) (obj6 != null ? obj6 : null)).mKeyguardBottomArea.indicationArea$delegate.getValue();
                break;
            case 7:
                Object obj7 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj7 != null ? obj7 : null)).mStatusBarKeyguardViewManager.getLockIconContainer();
                break;
            case 8:
                KeyguardTouchViewInjector keyguardTouchViewInjector2 = this.viewInjector;
                if (keyguardTouchViewInjector2 == null) {
                    keyguardTouchViewInjector2 = null;
                }
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = ((NotificationPanelViewController) keyguardTouchViewInjector2).mKeyguardStatusViewController.mKeyguardStatusBase;
                List list = faceWidgetContainerWrapper == null ? null : faceWidgetContainerWrapper.mContentsContainerList;
                if (list != null && !list.isEmpty()) {
                    r0 = (View) list.get(1);
                    break;
                }
                break;
            case 9:
                KeyguardTouchViewInjector keyguardTouchViewInjector3 = this.viewInjector;
                if (keyguardTouchViewInjector3 == null) {
                    keyguardTouchViewInjector3 = null;
                }
                KeyguardUserSwitcherController keyguardUserSwitcherController = ((NotificationPanelViewController) keyguardTouchViewInjector3).mKeyguardUserSwitcherController;
                if (keyguardUserSwitcherController != null) {
                    r0 = keyguardUserSwitcherController.mListView;
                    break;
                }
                break;
            case 10:
                Object obj8 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj8 != null ? obj8 : null)).mPluginLockStarContainer;
                break;
            case 11:
                Object obj9 = this.viewInjector;
                r0 = ((NotificationPanelViewController) (obj9 != null ? obj9 : null)).mView.findViewById(R.id.keyguard_edit_mode_blur_effect);
                break;
            case 12:
                KeyguardTouchViewInjector keyguardTouchViewInjector4 = this.viewInjector;
                if (keyguardTouchViewInjector4 == null) {
                    keyguardTouchViewInjector4 = null;
                }
                Object obj10 = ((NotificationPanelViewController) keyguardTouchViewInjector4).mNowBarContainer;
                if (obj10 != null) {
                    r0 = obj10;
                    break;
                } else {
                    Log.e("NotificationPanelView", "provideFaceWidgetNowBarContainer nowbarRootView is null");
                    break;
                }
        }
        if (r0 == null) {
            return false;
        }
        this.views.put(i, r0);
        return true;
    }

    public final boolean isAnimationRunning$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.tapAffordanceViewController.isTapAnimationRunning || this.dragViewController.restoreAnimatorSet.isRunning() || this.fullScreenViewController.fullScreenAnimatorSet.isRunning();
    }

    public final boolean isViRunning() {
        return (this.isTouching && !this.hasDozeAmount) || isAnimationRunning$frameworks__base__packages__SystemUI__android_common__SystemUI_core() || this.isUnlockExecuted;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.intercepting;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.intercepting) {
            return false;
        }
        KeyguardTouchSecurityInjector keyguardTouchSecurityInjector = this.securityInjector;
        keyguardTouchSecurityInjector.getClass();
        if (CscRune.SECURITY_SIM_PERM_DISABLED && keyguardTouchSecurityInjector.mKeyguardUpdateMonitor.isIccBlockedPermanently()) {
            return true;
        }
        if (this.settingsHelper.isDoubleTapToSleepAllowed() && (motionEvent.getSource() & 12290) == 4098 && motionEvent.getToolType(0) == 1) {
            GestureDetector gestureDetector = this.gestureDetector;
            if (gestureDetector == null) {
                gestureDetector = null;
            }
            gestureDetector.onTouchEvent(motionEvent);
        }
        if (WallpaperUtils.isLiveWallpaper()) {
            this.keyguardWallpaper.getClass();
        }
        Map map = this.actionHandlerTypes;
        Integer valueOf = Integer.valueOf(motionEvent.getActionMasked());
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object obj = linkedHashMap.get(valueOf);
        if (obj == null) {
            ActionHandlerType.Companion companion = ActionHandlerType.Companion;
            int actionMasked = motionEvent.getActionMasked();
            companion.getClass();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        obj = new ActionMoveHandler(this);
                    } else if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            obj = new ActionPointerDownHandler(this);
                        } else if (actionMasked != 6) {
                            obj = new ActionDefaultHandler(this);
                        }
                    }
                }
                obj = new ActionUpOrCancelHandler(this);
            } else {
                obj = new ActionDownHandler(this);
            }
            linkedHashMap.put(valueOf, obj);
        }
        return ((ActionHandlerType) obj).handleMotionEvent(motionEvent);
    }

    public final void reset(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("reset unlockExecuted=", "KeyguardTouchAnimator", this.isUnlockExecuted);
        ScreenOnAffordanceViewController screenOnAffordanceViewController = this.screenOnViewController;
        ArrayList arrayList = (ArrayList) screenOnAffordanceViewController.screenOnSpringAnimationList;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((SpringAnimation) it.next()).cancel();
        }
        arrayList.clear();
        ArrayList arrayList2 = (ArrayList) screenOnAffordanceViewController.restoreSpringAnimationList;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            ((SpringAnimation) it2.next()).cancel();
        }
        arrayList2.clear();
        TapAffordanceViewController tapAffordanceViewController = this.tapAffordanceViewController;
        ArrayList arrayList3 = (ArrayList) tapAffordanceViewController.tapSpringAnimationList;
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            ((SpringAnimation) it3.next()).cancel();
        }
        arrayList3.clear();
        ArrayList arrayList4 = (ArrayList) tapAffordanceViewController.restoreSpringAnimationList;
        Iterator it4 = arrayList4.iterator();
        while (it4.hasNext()) {
            ((SpringAnimation) it4.next()).cancel();
        }
        arrayList4.clear();
        tapAffordanceViewController.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().removeCallbacks(tapAffordanceViewController.restoreSpringAnimRunnable);
        tapAffordanceViewController.isTapAnimationRunning = false;
        FullScreenViewController fullScreenViewController = this.fullScreenViewController;
        fullScreenViewController.fullScreenAnimatorSet.cancel();
        if (fullScreenViewController.isFullscreenModeEnabled) {
            Log.d("KeyguardTouchAnimator", "reset mFullScreenModeEnabled true");
            fullScreenViewController.isFullscreenModeEnabled = false;
        }
        fullScreenViewController.isFullScreenModeShown = false;
        fullScreenViewController.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().removeCallbacks(fullScreenViewController.longPressCallback);
        DragViewController dragViewController = this.dragViewController;
        dragViewController.unlockViewHideAnimatorSet.cancel();
        dragViewController.restoreAnimatorSet.cancel();
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.editModeAnimatorController;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("reset ", "KeyguardEditModeAnimatorController", keyguardEditModeAnimatorController.isKeyguardState());
        Job job = keyguardEditModeAnimatorController.longPressJob;
        if (job != null && job.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
            Job job2 = keyguardEditModeAnimatorController.longPressJob;
            if (job2 != null) {
                job2.cancel(null);
            }
        }
        if (keyguardEditModeAnimatorController.animatorSet.isRunning()) {
            keyguardEditModeAnimatorController.animatorSet.cancel();
        }
        if (!keyguardEditModeAnimatorController.isKeyguardState() && !keyguardEditModeAnimatorController.isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            keyguardEditModeAnimatorController.resetViews();
        }
        if (this.isUnlockExecuted) {
            this.isUnlockExecuted = false;
        }
        resetChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core(z ? 0.95f : 1.0f);
        setTouch(false);
        this.hasDozeAmount = false;
        this.distance = 0.0f;
        this.updateDistanceCount = 0;
        PointF pointF = this.touchDownPos;
        pointF.x = -1.0f;
        pointF.y = -1.0f;
    }

    public final void resetChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core(float f) {
        Log.d("KeyguardTouchAnimator", "resetChildViewVI(): isUnlockExecuted=" + this.isUnlockExecuted + " scale=" + f);
        KeyguardTouchBase.Companion.getClass();
        boolean z = KeyguardTouchBase.DEBUG;
        if (z) {
            showViewState(getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
        }
        if (((KeyguardEditModeControllerImpl) this.keyguardEditModeController).getVIRunning()) {
            Log.d("KeyguardTouchAnimator", "resetChildViewVI vIRunning");
            return;
        }
        this.notiScale = f;
        int size = this.views.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.views.valueAt(i);
            if (z) {
                Intrinsics.checkNotNull(view);
                showViewState(view);
            }
            if (f == 0.95f) {
                int keyAt = this.views.keyAt(i);
                this.screenOnViewController.getClass();
                if (keyAt != 0) {
                    if (keyAt == 3) {
                    }
                }
            }
            view.setScaleY(f);
            view.setScaleX(f);
            view.setAlpha(1.0f);
        }
        this.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(1.0f);
    }

    public final void restoreChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        KeyguardTouchAnimator keyguardTouchAnimator;
        boolean z = this.isUnlockExecuted;
        if (z || this.tapAffordanceViewController.isTapAnimationRunning) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("restoreChildViewVI(): ", "KeyguardTouchAnimator", z);
            return;
        }
        final DragViewController dragViewController = this.dragViewController;
        dragViewController.getClass();
        AnimatorSet createAnimatorSet$default = DragViewController.createAnimatorSet$default(dragViewController, 1);
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
            View view = (View) it4.next();
            if (view != null) {
                SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(250.0f, 0.7f);
                springAnimation.animateToFinalPosition(1.0f);
                SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(250.0f, 0.7f);
                springAnimation2.animateToFinalPosition(1.0f);
                SpringAnimation springAnimation3 = new SpringAnimation(view, DynamicAnimation.ALPHA);
                springAnimation3.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(550.0f, 0.96f);
                if (keyguardTouchAnimator != null && keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1) && view.equals(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1))) {
                    springAnimation3.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.keyguard.animator.ViewAnimationController$setViewAnimationRestoreDrag$1
                        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                        public final void onAnimationUpdate(float f, float f2) {
                            ViewAnimationController.this.keyguardTouchAnimator.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(f);
                        }
                    });
                }
                springAnimation3.animateToFinalPosition(1.0f);
            }
        }
        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0)) {
            dragViewController.setViewAnimation(createAnimatorSet$default, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0), -1.0f, 1.0f);
        }
        createAnimatorSet$default.start();
    }

    @Override // com.android.systemui.keyguard.animator.KeyguardTouchBase
    public final void updateAffordace(DisplayInfo displayInfo) {
        PivotViewController pivotViewController = this.pivotViewController;
        pivotViewController.getClass();
        pivotViewController.affordancePivotX = displayInfo.logicalWidth / 2;
        pivotViewController.affordancePivotY = displayInfo.logicalHeight / 2;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
