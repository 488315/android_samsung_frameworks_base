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
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.animator.ActionHandlerType;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.sec.ims.configuration.DATA;
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
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final KeyguardEditModeController keyguardEditModeController;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardWallpaper keyguardWallpaper;
    public final KeyguardTouchLoggingInjector loggingInjector;
    public float notiScale;
    public final Lazy parentView$delegate;
    public final PivotViewController pivotViewController;
    public final StatusBarStateController sbStateController;
    public final KeyguardTouchSecurityInjector securityInjector;
    public final SettingsHelper settingsHelper;
    public final TapAffordanceViewController tapAffordanceViewController;
    public KeyguardTouchViewInjector viewInjector;
    public final SparseArray views;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public KeyguardTouchAnimator(Context context, final KeyguardUpdateMonitor keyguardUpdateMonitor, final PowerManager powerManager, KeyguardStateController keyguardStateController, KeyguardTouchDymLockInjector keyguardTouchDymLockInjector, KeyguardTouchLoggingInjector keyguardTouchLoggingInjector, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, KeyguardEditModeController keyguardEditModeController, KeyguardWallpaper keyguardWallpaper, SecRotationWatcher secRotationWatcher) {
        super(context, keyguardUpdateMonitor);
        this.keyguardStateController = keyguardStateController;
        this.dymLockInjector = keyguardTouchDymLockInjector;
        this.loggingInjector = keyguardTouchLoggingInjector;
        this.securityInjector = keyguardTouchSecurityInjector;
        this.sbStateController = statusBarStateController;
        this.settingsHelper = settingsHelper;
        this.keyguardEditModeController = keyguardEditModeController;
        this.keyguardWallpaper = keyguardWallpaper;
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
        this.fullScreenViewController = new FullScreenViewController(this);
        this.editModeAnimatorController = new KeyguardEditModeAnimatorController(this, keyguardEditModeController, keyguardWallpaper, keyguardUpdateMonitor, secRotationWatcher, settingsHelper);
        this.dragViewController = new DragViewController(this);
        initDimens();
        statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchAnimator.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                KeyguardTouchAnimator.this.dozeAmount = f;
                if (f == 0.0f) {
                    Log.d("KeyguardTouchAnimator", "dozeAmount cleared");
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

            /* JADX WARN: Removed duplicated region for block: B:18:0x0073  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x0098  */
            /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
                boolean z;
                boolean z2;
                int i;
                if (motionEvent.getAction() != 1) {
                    return false;
                }
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
                            RecyclerView$$ExternalSyntheticOutline0.m46m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("isConsideredDoubleTap: over slop=", i3, ", deltaX=", x, ", deltaY="), y, "KeyguardTouchAnimator");
                        } else {
                            z = true;
                            if (z) {
                                KeyguardTouchAnimator keyguardTouchAnimator2 = KeyguardTouchAnimator.this;
                                if (!((KeyguardStateControllerImpl) keyguardTouchAnimator2.keyguardStateController).mKeyguardGoingAway && (!keyguardTouchAnimator2.securityInjector.isFingerprintArea(motionEvent) || !keyguardUpdateMonitor.isFingerprintDetectionRunning())) {
                                    z2 = true;
                                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onDoubleTapEvent executeDoubleTap=", z2, "KeyguardTouchAnimator");
                                    if (!z2) {
                                        return false;
                                    }
                                    powerManager.goToSleep(SystemClock.uptimeMillis(), 23, 0);
                                    KeyguardTouchAnimator.this.getClass();
                                    SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.UT_APN_NAME, "1012");
                                    return true;
                                }
                            }
                            z2 = false;
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onDoubleTapEvent executeDoubleTap=", z2, "KeyguardTouchAnimator");
                            if (!z2) {
                            }
                        }
                    }
                }
                z = false;
                if (z) {
                }
                z2 = false;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onDoubleTapEvent executeDoubleTap=", z2, "KeyguardTouchAnimator");
                if (!z2) {
                }
            }
        });
    }

    public static void showViewState(View view) {
        Log.d("KeyguardTouchAnimator", "v=" + view + " alpha=" + view.getAlpha() + " scale=" + view.getScaleX() + "," + view.getScaleY());
    }

    public final boolean canLongPressArea(MotionEvent motionEvent) {
        return !this.securityInjector.isFingerprintArea(motionEvent) && this.touchDownPos.y < ((float) (((View) this.parentView$delegate.getValue()).getHeight() - this.longPressAllowHeight));
    }

    /* renamed from: hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final boolean m149xd394625(int i) {
        Object iconContainer;
        SparseArray sparseArray = this.views;
        if (sparseArray.indexOfKey(i) >= 0) {
            return true;
        }
        switch (i) {
            case 0:
                Object obj = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj != null ? obj : null)).mKeyguardStatusBar;
                break;
            case 1:
                KeyguardTouchViewInjector keyguardTouchViewInjector = this.viewInjector;
                if (keyguardTouchViewInjector == null) {
                    keyguardTouchViewInjector = null;
                }
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = ((NotificationPanelViewController) keyguardTouchViewInjector).mKeyguardStatusViewController.mKeyguardStatusBase;
                if (faceWidgetContainerWrapper != null && (r1 = faceWidgetContainerWrapper.mClockContainer) == null) {
                    r1 = faceWidgetContainerWrapper.mFaceWidgetContainer;
                    break;
                }
                break;
            case 2:
                Object obj2 = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj2 != null ? obj2 : null)).mNotificationStackScrollLayoutController.mView;
                break;
            case 3:
                KeyguardTouchViewInjector keyguardTouchViewInjector2 = this.viewInjector;
                if (keyguardTouchViewInjector2 == null) {
                    keyguardTouchViewInjector2 = null;
                }
                PluginNotificationController pluginNotificationController = ((NotificationPanelViewController) keyguardTouchViewInjector2).mLockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                if (pluginNotificationController != null) {
                    iconContainer = pluginNotificationController.getIconContainer();
                    r1 = iconContainer;
                    break;
                }
                break;
            case 4:
                Object obj3 = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj3 != null ? obj3 : null)).mKeyguardBottomArea.getLeftView();
                break;
            case 5:
                Object obj4 = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj4 != null ? obj4 : null)).mKeyguardBottomArea.getRightView();
                break;
            case 6:
                Object obj5 = this.viewInjector;
                r1 = (ViewGroup) ((NotificationPanelViewController) (obj5 != null ? obj5 : null)).mKeyguardBottomArea.indicationArea$delegate.getValue();
                break;
            case 7:
                Object obj6 = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj6 != null ? obj6 : null)).mStatusBarKeyguardViewManager.getLockIconContainer();
                break;
            case 8:
                KeyguardTouchViewInjector keyguardTouchViewInjector3 = this.viewInjector;
                if (keyguardTouchViewInjector3 == null) {
                    keyguardTouchViewInjector3 = null;
                }
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = ((NotificationPanelViewController) keyguardTouchViewInjector3).mKeyguardStatusViewController.mKeyguardStatusBase;
                List list = faceWidgetContainerWrapper2 == null ? null : faceWidgetContainerWrapper2.mContentsContainerList;
                if (list != null && !list.isEmpty()) {
                    iconContainer = (View) list.get(1);
                    r1 = iconContainer;
                    break;
                }
                break;
            case 9:
                KeyguardTouchViewInjector keyguardTouchViewInjector4 = this.viewInjector;
                if (keyguardTouchViewInjector4 == null) {
                    keyguardTouchViewInjector4 = null;
                }
                KeyguardUserSwitcherController keyguardUserSwitcherController = ((NotificationPanelViewController) keyguardTouchViewInjector4).mKeyguardUserSwitcherController;
                if (keyguardUserSwitcherController != null) {
                    iconContainer = keyguardUserSwitcherController.mListView;
                    r1 = iconContainer;
                    break;
                }
                break;
            case 10:
                Object obj7 = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj7 != null ? obj7 : null)).mPluginLockStarContainer;
                break;
            case 11:
                Object obj8 = this.viewInjector;
                r1 = ((NotificationPanelViewController) (obj8 != null ? obj8 : null)).mView.findViewById(R.id.keyguard_edit_mode_blur_effect);
                break;
        }
        if (r1 == null) {
            return false;
        }
        sparseArray.put(i, r1);
        return true;
    }

    /* renamed from: isAnimationRunning$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final boolean m150x6c4411ab() {
        return this.tapAffordanceViewController.isTapAnimationRunning || this.dragViewController.restoreAnimatorSet.isRunning() || this.fullScreenViewController.fullScreenAnimatorSet.isRunning();
    }

    public final boolean isViRunning() {
        return (this.isTouching && !this.hasDozeAmount) || m150x6c4411ab() || this.isUnlockExecuted;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.intercepting;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        SystemUIWallpaperBase systemUIWallpaperBase;
        if (!this.intercepting) {
            return false;
        }
        KeyguardTouchSecurityInjector keyguardTouchSecurityInjector = this.securityInjector;
        keyguardTouchSecurityInjector.getClass();
        if ((LsRune.SECURITY_SIM_PERM_DISABLED && keyguardTouchSecurityInjector.mKeyguardUpdateMonitor.isIccBlockedPermanently()) == true) {
            return true;
        }
        if ((this.settingsHelper.mItemLists.get("double_tap_to_sleep").getIntValue() == 1) != false) {
            if (((motionEvent.getSource() & 12290) == 4098 && motionEvent.getToolType(0) == 1) != false) {
                GestureDetector gestureDetector = this.gestureDetector;
                if (gestureDetector == null) {
                    gestureDetector = null;
                }
                gestureDetector.onTouchEvent(motionEvent);
            }
        }
        WallpaperUtils.isSubDisplay();
        if ((WallpaperUtils.sWallpaperType[WallpaperUtils.isSubDisplay() ? 1 : 0] == 7) && (systemUIWallpaperBase = ((KeyguardWallpaperController) this.keyguardWallpaper).mWallpaperView) != null) {
            systemUIWallpaperBase.handleTouchEvent(motionEvent);
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0097, code lost:
    
        if (r2.isActive() == true) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reset() {
        boolean z;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("reset unlockExecuted=", this.isUnlockExecuted, "KeyguardTouchAnimator");
        TapAffordanceViewController tapAffordanceViewController = this.tapAffordanceViewController;
        ArrayList arrayList = (ArrayList) tapAffordanceViewController.tapSpringAnimationList;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((SpringAnimation) it.next()).cancel();
        }
        arrayList.clear();
        ArrayList arrayList2 = (ArrayList) tapAffordanceViewController.restoreSpringAnimationList;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            ((SpringAnimation) it2.next()).cancel();
        }
        arrayList2.clear();
        tapAffordanceViewController.getParentView().removeCallbacks(tapAffordanceViewController.restoreSpringAnimRunnable);
        tapAffordanceViewController.isTapAnimationRunning = false;
        FullScreenViewController fullScreenViewController = this.fullScreenViewController;
        fullScreenViewController.fullScreenAnimatorSet.cancel();
        if (fullScreenViewController.isFullscreenModeEnabled) {
            Log.d("KeyguardTouchAnimator", "reset mFullScreenModeEnabled true");
            fullScreenViewController.isFullscreenModeEnabled = false;
        }
        fullScreenViewController.isFullScreenModeShown = false;
        fullScreenViewController.getParentView().removeCallbacks(fullScreenViewController.longPressCallback);
        DragViewController dragViewController = this.dragViewController;
        dragViewController.unlockViewHideAnimatorSet.cancel();
        dragViewController.restoreAnimatorSet.cancel();
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.editModeAnimatorController;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("reset ", keyguardEditModeAnimatorController.isKeyguardState(), "KeyguardEditModeAnimatorController");
        ((KeyguardWallpaperController) keyguardEditModeAnimatorController.keyguardWallpaper).setThumbnailVisibility(4);
        StandaloneCoroutine standaloneCoroutine = keyguardEditModeAnimatorController.longPressJob;
        if (standaloneCoroutine != null) {
            z = true;
        }
        z = false;
        if (z) {
            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
            StandaloneCoroutine standaloneCoroutine2 = keyguardEditModeAnimatorController.longPressJob;
            if (standaloneCoroutine2 != null) {
                standaloneCoroutine2.cancel(null);
            }
        }
        if (keyguardEditModeAnimatorController.animatorSet.isRunning()) {
            keyguardEditModeAnimatorController.animatorSet.cancel();
        }
        if (!keyguardEditModeAnimatorController.isKeyguardState() && !keyguardEditModeAnimatorController.m148x7abb39c2()) {
            keyguardEditModeAnimatorController.resetViews();
        }
        if (this.isUnlockExecuted) {
            this.isUnlockExecuted = false;
        }
        m151x304bee4b();
        setTouch(false);
        this.hasDozeAmount = false;
        this.distance = 0.0f;
        this.updateDistanceCount = 0;
        PointF pointF = this.touchDownPos;
        pointF.x = -1.0f;
        pointF.y = -1.0f;
    }

    /* renamed from: resetChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m151x304bee4b() {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("resetChildViewVI(): ", this.isUnlockExecuted, "KeyguardTouchAnimator");
        KeyguardTouchBase.Companion.getClass();
        boolean z = KeyguardTouchBase.DEBUG;
        if (z) {
            showViewState((View) this.parentView$delegate.getValue());
        }
        if (((KeyguardEditModeControllerImpl) this.keyguardEditModeController).getVIRunning()) {
            Log.d("KeyguardTouchAnimator", "resetChildViewVI vIRunning");
            return;
        }
        this.notiScale = 1.0f;
        SparseArray sparseArray = this.views;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            View view = (View) sparseArray.valueAt(i);
            if (z) {
                showViewState(view);
            }
            view.setScaleY(1.0f);
            view.setScaleX(1.0f);
            view.setAlpha(1.0f);
        }
    }

    /* renamed from: restoreChildViewVI$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m152x84c4ab2c() {
        boolean z = this.isUnlockExecuted;
        if (z || this.tapAffordanceViewController.isTapAnimationRunning) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("restoreChildViewVI(): ", z, "KeyguardTouchAnimator");
            return;
        }
        DragViewController dragViewController = this.dragViewController;
        dragViewController.getClass();
        AnimatorSet createAnimatorSet$default = DragViewController.createAnimatorSet$default(dragViewController, 1);
        ArrayList arrayList = new ArrayList();
        for (Object obj : dragViewController.dragViews) {
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
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            if (((View) next).getVisibility() == 0) {
                arrayList3.add(next);
            }
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            ViewAnimationController.setViewAnimation(createAnimatorSet$default, (View) it3.next(), 1.0f, 1.0f);
        }
        if (dragViewController.hasView(0)) {
            ViewAnimationController.setViewAnimation(createAnimatorSet$default, dragViewController.getView(0), -1.0f, 1.0f);
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
