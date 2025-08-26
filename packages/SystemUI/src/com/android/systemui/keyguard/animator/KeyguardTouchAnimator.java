package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.PointF;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.view.DisplayInfo;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
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
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.animator.ActionHandlerType;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class KeyguardTouchAnimator extends KeyguardTouchBase implements Gefingerpoken, Dumpable {
    public final Map actionHandlerTypes;
    public NotificationPanelViewController.AnonymousClass4 callback;
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
    public NotificationPanelViewController viewInjector;
    public final SparseArray views;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardTouchAnimator(Context context, final KeyguardUpdateMonitor keyguardUpdateMonitor, final PowerManager powerManager, KeyguardStateController keyguardStateController, KeyguardTouchDymLockInjector keyguardTouchDymLockInjector, KeyguardTouchLoggingInjector keyguardTouchLoggingInjector, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, KeyguardEditModeController keyguardEditModeController, KeyguardWallpaper keyguardWallpaper, SecRotationWatcher secRotationWatcher, LockscreenShadeTransitionController lockscreenShadeTransitionController, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper, KeyguardClickController keyguardClickController, CoroutineScope coroutineScope, PowerInteractor powerInteractor, DozeParameters dozeParameters, SelectedUserInteractor selectedUserInteractor, KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl) {
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
        this.parentView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NotificationPanelViewController notificationPanelViewController = this.f$0.viewInjector;
                if (notificationPanelViewController == null) {
                    notificationPanelViewController = null;
                }
                return notificationPanelViewController.mView;
            }
        });
        this.views = new SparseArray();
        this.actionHandlerTypes = new LinkedHashMap();
        this.tapAffordanceViewController = new TapAffordanceViewController(this);
        this.pivotViewController = new PivotViewController(this);
        this.screenOnViewController = new ScreenOnAffordanceViewController(this, powerInteractor, coroutineScope, dozeParameters);
        this.fullScreenViewController = new FullScreenViewController(this);
        this.editModeAnimatorController = new KeyguardEditModeAnimatorController(this, selectedUserInteractor, keyguardEditModeController, keyguardWallpaper, keyguardUpdateMonitor, secRotationWatcher, settingsHelper, keyguardClickController, keyguardViewMediatorHelperImpl);
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

            /* JADX WARN: Removed duplicated region for block: B:26:0x008d  */
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
                boolean z;
                int i;
                if (motionEvent.getAction() == 1) {
                    KeyguardTouchAnimator keyguardTouchAnimator = KeyguardTouchAnimator.this;
                    MotionEvent motionEvent2 = keyguardTouchAnimator.doubleTapDownEvent;
                    if (motionEvent2 != null) {
                        long eventTime = motionEvent.getEventTime() - motionEvent2.getEventTime();
                        if (eventTime > 300) {
                            Log.d("KeyguardTouchAnimator", "isConsideredDoubleTap: time out deltaTime=" + eventTime);
                        } else {
                            int x = ((int) motionEvent2.getX()) - ((int) motionEvent.getX());
                            int y = ((int) motionEvent2.getY()) - ((int) motionEvent.getY());
                            if ((motionEvent2.getFlags() & 8) != 0) {
                                i = 0;
                            } else {
                                int i2 = keyguardTouchAnimator.doubleTapSlop;
                                i = i2 * i2;
                            }
                            int i3 = (y * y) + (x * x);
                            if (i3 >= i) {
                                RecyclerView$$ExternalSyntheticOutline0.m(y, "KeyguardTouchAnimator", MutableObjectList$$ExternalSyntheticOutline0.m(i3, x, "isConsideredDoubleTap: over slop=", ", deltaX=", ", deltaY="));
                            } else {
                                KeyguardTouchAnimator keyguardTouchAnimator2 = KeyguardTouchAnimator.this;
                                if (!((KeyguardStateControllerImpl) keyguardTouchAnimator2.keyguardStateController).mKeyguardGoingAway && (!keyguardTouchAnimator2.securityInjector.isFingerprintArea(motionEvent) || !keyguardUpdateMonitor.isFingerprintDetectionRunning())) {
                                    z = true;
                                }
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("onDoubleTapEvent executeDoubleTap=", "KeyguardTouchAnimator", z);
                                if (z) {
                                    powerManager.goToSleep(SystemClock.uptimeMillis(), 23, 0);
                                    KeyguardTouchAnimator.this.getClass();
                                    SystemUIAnalytics.sendEventLog("101", SystemUIAnalytics.EID_DOUBLE_TAP_TO_SLEEP);
                                    return true;
                                }
                            }
                        }
                        z = false;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onDoubleTapEvent executeDoubleTap=", "KeyguardTouchAnimator", z);
                        if (z) {
                        }
                    } else {
                        z = false;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onDoubleTapEvent executeDoubleTap=", "KeyguardTouchAnimator", z);
                        if (z) {
                        }
                    }
                }
                return false;
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
                        NotificationPanelViewController.AnonymousClass4 anonymousClass4 = keyguardTouchAnimator.callback;
                        if (anonymousClass4 == null) {
                            anonymousClass4 = null;
                        }
                        anonymousClass4.callUserActivity();
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x0038 A[PHI: r2
      0x0038: PHI (r2v20 android.view.View) = (r2v2 android.view.View), (r2v23 android.view.View) binds: [B:83:0x00d6, B:22:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i) {
        View view;
        if (this.views.indexOfKey(i) >= 0) {
            return true;
        }
        View iconContainer = null;
        iconContainer = null;
        iconContainer = null;
        iconContainer = null;
        iconContainer = null;
        switch (i) {
            case 0:
                NotificationPanelViewController notificationPanelViewController = this.viewInjector;
                iconContainer = (notificationPanelViewController != null ? notificationPanelViewController : null).mKeyguardStatusBar;
                break;
            case 1:
                NotificationPanelViewController notificationPanelViewController2 = this.viewInjector;
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = (notificationPanelViewController2 != null ? notificationPanelViewController2 : null).mKeyguardStatusBase;
                view = faceWidgetContainerWrapper.mClockContainer;
                if (view == null) {
                    iconContainer = faceWidgetContainerWrapper.mFaceWidgetContainer;
                    break;
                } else {
                    iconContainer = view;
                    break;
                }
            case 2:
                NotificationPanelViewController notificationPanelViewController3 = this.viewInjector;
                iconContainer = (notificationPanelViewController3 != null ? notificationPanelViewController3 : null).mNotificationStackScrollLayoutController.mView;
                break;
            case 3:
                NotificationPanelViewController notificationPanelViewController4 = this.viewInjector;
                iconContainer = (notificationPanelViewController4 != null ? notificationPanelViewController4 : null).mLockscreenNotificationIconsOnlyController.getIconContainer();
                break;
            case 4:
                NotificationPanelViewController notificationPanelViewController5 = this.viewInjector;
                iconContainer = (notificationPanelViewController5 != null ? notificationPanelViewController5 : null).mKeyguardSecBottomArea.getLeftView();
                break;
            case 5:
                NotificationPanelViewController notificationPanelViewController6 = this.viewInjector;
                iconContainer = (notificationPanelViewController6 != null ? notificationPanelViewController6 : null).mKeyguardSecBottomArea.getRightView();
                break;
            case 6:
                NotificationPanelViewController notificationPanelViewController7 = this.viewInjector;
                iconContainer = (ViewGroup) (notificationPanelViewController7 != null ? notificationPanelViewController7 : null).mKeyguardSecBottomArea.indicationArea$delegate.getValue();
                break;
            case 7:
                NotificationPanelViewController notificationPanelViewController8 = this.viewInjector;
                iconContainer = (notificationPanelViewController8 != null ? notificationPanelViewController8 : null).mStatusBarKeyguardViewManager.getLockIconContainer();
                break;
            case 8:
                NotificationPanelViewController notificationPanelViewController9 = this.viewInjector;
                if (notificationPanelViewController9 == null) {
                    notificationPanelViewController9 = null;
                }
                List list = notificationPanelViewController9.mKeyguardStatusBase.mContentsContainerList;
                if (list != null && !list.isEmpty()) {
                    iconContainer = (View) list.get(1);
                    break;
                }
                break;
            case 9:
                NotificationPanelViewController notificationPanelViewController10 = this.viewInjector;
                if (notificationPanelViewController10 == null) {
                    notificationPanelViewController10 = null;
                }
                notificationPanelViewController10.getClass();
                break;
            case 10:
                NotificationPanelViewController notificationPanelViewController11 = this.viewInjector;
                iconContainer = (notificationPanelViewController11 != null ? notificationPanelViewController11 : null).mPluginLockStarContainer;
                break;
            case 11:
                NotificationPanelViewController notificationPanelViewController12 = this.viewInjector;
                iconContainer = (notificationPanelViewController12 != null ? notificationPanelViewController12 : null).mView.findViewById(R.id.keyguard_edit_mode_blur_effect);
                break;
            case 12:
                NotificationPanelViewController notificationPanelViewController13 = this.viewInjector;
                if (notificationPanelViewController13 == null) {
                    notificationPanelViewController13 = null;
                }
                view = notificationPanelViewController13.mNowBarContainer;
                if (view == null) {
                    Log.e("NotificationPanelView", "provideFaceWidgetNowBarContainer nowbarRootView is null");
                    break;
                }
                break;
            case 13:
                NotificationPanelViewController notificationPanelViewController14 = this.viewInjector;
                iconContainer = (notificationPanelViewController14 != null ? notificationPanelViewController14 : null).mContinuityLockScreenContainer;
                break;
            case 14:
                NotificationPanelViewController notificationPanelViewController15 = this.viewInjector;
                iconContainer = (notificationPanelViewController15 != null ? notificationPanelViewController15 : null).provideComplication();
                break;
        }
        if (iconContainer == null) {
            return false;
        }
        this.views.put(i, iconContainer);
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

    /* JADX WARN: Removed duplicated region for block: B:42:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
        if (WallpaperUtils.sWallpaperType == 7) {
            this.keyguardWallpaper.getClass();
        }
        Map map = this.actionHandlerTypes;
        Integer numValueOf = Integer.valueOf(motionEvent.getActionMasked());
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object actionDownHandler = linkedHashMap.get(numValueOf);
        if (actionDownHandler == null) {
            ActionHandlerType.Companion companion = ActionHandlerType.Companion;
            int actionMasked = motionEvent.getActionMasked();
            companion.getClass();
            if (actionMasked == 0) {
                actionDownHandler = new ActionDownHandler(this);
            } else if (actionMasked == 1) {
                actionDownHandler = new ActionUpOrCancelHandler(this);
            } else if (actionMasked == 2) {
                actionDownHandler = new ActionMoveHandler(this);
            } else if (actionMasked != 3) {
                if (actionMasked == 5) {
                    actionDownHandler = new ActionPointerDownHandler(this);
                } else if (actionMasked != 6) {
                    actionDownHandler = new ActionDefaultHandler(this);
                }
            }
            linkedHashMap.put(numValueOf, actionDownHandler);
        }
        return ((ActionHandlerType) actionDownHandler).handleMotionEvent(motionEvent);
    }

    public final void reset(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("reset unlockExecuted=", "KeyguardTouchAnimator", this.isUnlockExecuted);
        ScreenOnAffordanceViewController screenOnAffordanceViewController = this.screenOnViewController;
        ArrayList arrayList = (ArrayList) screenOnAffordanceViewController.screenOnSpringAnimationList;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SpringAnimation) obj).cancel();
        }
        arrayList.clear();
        ArrayList arrayList2 = (ArrayList) screenOnAffordanceViewController.restoreSpringAnimationList;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            ((SpringAnimation) obj2).cancel();
        }
        arrayList2.clear();
        TapAffordanceViewController tapAffordanceViewController = this.tapAffordanceViewController;
        ArrayList arrayList3 = (ArrayList) tapAffordanceViewController.tapSpringAnimationList;
        int size3 = arrayList3.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList3.get(i3);
            i3++;
            ((SpringAnimation) obj3).cancel();
        }
        arrayList3.clear();
        ArrayList arrayList4 = (ArrayList) tapAffordanceViewController.restoreSpringAnimationList;
        int size4 = arrayList4.size();
        int i4 = 0;
        while (i4 < size4) {
            Object obj4 = arrayList4.get(i4);
            i4++;
            ((SpringAnimation) obj4).cancel();
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("reset ", "KeyguardEditModeAnimatorController", keyguardEditModeAnimatorController.isKeyguardState());
        StandaloneCoroutine standaloneCoroutine = keyguardEditModeAnimatorController.longPressJob;
        if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
            StandaloneCoroutine standaloneCoroutine2 = keyguardEditModeAnimatorController.longPressJob;
            if (standaloneCoroutine2 != null) {
                standaloneCoroutine2.cancel(null);
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                view.getClass();
                showViewState(view);
            }
            if (f == 0.95f) {
                int iKeyAt = this.views.keyAt(i);
                this.screenOnViewController.getClass();
                if (iKeyAt != 0 && iKeyAt != 3) {
                    view.setScaleY(f);
                    view.setScaleX(f);
                    view.setAlpha(1.0f);
                }
            }
        }
        this.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(1.0f);
    }

    public final void restoreChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        KeyguardTouchAnimator keyguardTouchAnimator;
        boolean z = this.isUnlockExecuted;
        if (z || this.tapAffordanceViewController.isTapAnimationRunning) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("restoreChildViewVI(): ", "KeyguardTouchAnimator", z);
            return;
        }
        final DragViewController dragViewController = this.dragViewController;
        dragViewController.getClass();
        AnimatorSet animatorSetCreateAnimatorSet$default = DragViewController.createAnimatorSet$default(dragViewController, 1);
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
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            arrayList2.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue()));
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            if (((View) obj2).getVisibility() == 0) {
                arrayList3.add(obj2);
            }
        }
        int size3 = arrayList3.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList3.get(i3);
            i3++;
            View view = (View) obj3;
            if (view != null) {
                SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(250.0f, 0.7f);
                springAnimation.animateToFinalPosition(1.0f);
                SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(250.0f, 0.7f);
                springAnimation2.animateToFinalPosition(1.0f);
                SpringAnimation springAnimation3 = new SpringAnimation(view, DynamicAnimation.ALPHA);
                springAnimation3.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(550.0f, 0.96f);
                if (keyguardTouchAnimator != null && keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1) && view.equals(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1))) {
                    springAnimation3.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.keyguard.animator.ViewAnimationController$setViewAnimationRestoreDrag$1
                        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                        public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                            dragViewController.keyguardTouchAnimator.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(f);
                        }
                    });
                }
                springAnimation3.animateToFinalPosition(1.0f);
            }
        }
        if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0)) {
            dragViewController.setViewAnimation(animatorSetCreateAnimatorSet$default, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0), -1.0f, 1.0f);
        }
        animatorSetCreateAnimatorSet$default.start();
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
