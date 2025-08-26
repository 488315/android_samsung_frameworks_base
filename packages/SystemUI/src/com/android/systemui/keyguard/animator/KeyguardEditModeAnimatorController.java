package com.android.systemui.keyguard.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManagerGlobal;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.KeyguardClickController;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class KeyguardEditModeAnimatorController extends ViewAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List alphaViews;
    public AnimatorSet animatorSet;
    public AnimatorSet cancelAnimatorSet;
    public final KeyguardClickController keyguardClickController;
    public final KeyguardEditModeController keyguardEditModeController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl;
    public final KeyguardWallpaper keyguardWallpaper;
    public StandaloneCoroutine longPressJob;
    public final List noScaleViews;
    public final List scaleViews;
    public final SelectedUserInteractor selectedUserInteractor;
    private final SettingsHelper settingsHelper;
    public StandaloneCoroutine startActivityJob;
    public AnimatorSet touchDownAnimatorSet;

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

    public KeyguardEditModeAnimatorController(KeyguardTouchAnimator keyguardTouchAnimator, SelectedUserInteractor selectedUserInteractor, KeyguardEditModeController keyguardEditModeController, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor, SecRotationWatcher secRotationWatcher, SettingsHelper settingsHelper, KeyguardClickController keyguardClickController, KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl) {
        super(keyguardTouchAnimator);
        this.selectedUserInteractor = selectedUserInteractor;
        this.keyguardEditModeController = keyguardEditModeController;
        this.keyguardWallpaper = keyguardWallpaper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.keyguardClickController = keyguardClickController;
        this.keyguardViewMediatorHelperImpl = keyguardViewMediatorHelperImpl;
        this.animatorSet = new AnimatorSet();
        this.touchDownAnimatorSet = new AnimatorSet();
        this.alphaViews = Arrays.asList(7, 8, 9, 10, 6, 2, 12);
        this.scaleViews = Arrays.asList(7, 1, 8, 9, 10, 6, 2, 4, 5, 12, 14);
        this.noScaleViews = Arrays.asList(0, 3, 11);
        IntConsumer intConsumer = new IntConsumer() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = KeyguardEditModeAnimatorController.this;
                int i2 = KeyguardEditModeAnimatorController.$r8$clinit;
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("rotation ", i, " ", keyguardEditModeAnimatorController.isEditMode(), "KeyguardEditModeAnimatorController");
                if (((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).getVIRunning()) {
                    ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).cancel();
                    KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = KeyguardEditModeAnimatorController.this;
                    ((KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController2.keyguardEditModeController).isEditMode = false;
                    keyguardEditModeAnimatorController2.resetViews();
                }
            }
        };
        if (!secRotationWatcher.mListeners.contains(intConsumer)) {
            boolean zIsEmpty = secRotationWatcher.mListeners.isEmpty();
            secRotationWatcher.mListeners.add(intConsumer);
            intConsumer.accept(secRotationWatcher.mCurrentRotation);
            if (zIsEmpty) {
                try {
                    SecurityLog.d("SecRotationWatcher", "enable watchRotation");
                    WindowManagerGlobal.getWindowManagerService().watchRotation(secRotationWatcher.mWatcher, secRotationWatcher.mContext.getDisplayId());
                } catch (RemoteException e) {
                    Log.w("SecRotationWatcher", "Failed to set rotation watcher", e);
                }
            }
        }
        KeyguardEditModeController keyguardEditModeController2 = this.keyguardEditModeController;
        final int i = 0;
        ((KeyguardEditModeControllerImpl) keyguardEditModeController2).isAnimationRunning = new Function0(this) { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardEditModeAnimatorController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.f$0;
                switch (i) {
                    case 0:
                        return Boolean.valueOf(keyguardEditModeAnimatorController.animatorSet.isRunning());
                    default:
                        int i2 = KeyguardEditModeAnimatorController.$r8$clinit;
                        keyguardEditModeAnimatorController.startCancelAnimation();
                        return Unit.INSTANCE;
                }
            }
        };
        keyguardEditModeController2.getClass();
        final int i2 = 1;
        ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).startCancelAnimationFunction = new Function0(this) { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardEditModeAnimatorController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.f$0;
                switch (i2) {
                    case 0:
                        return Boolean.valueOf(keyguardEditModeAnimatorController.animatorSet.isRunning());
                    default:
                        int i22 = KeyguardEditModeAnimatorController.$r8$clinit;
                        keyguardEditModeAnimatorController.startCancelAnimation();
                        return Unit.INSTANCE;
                }
            }
        };
        this.cancelAnimatorSet = new AnimatorSet();
    }

    public final void animate(boolean z) {
        if (this.animatorSet.isRunning()) {
            this.animatorSet.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$initAnimatorSet$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                Log.d("KeyguardEditModeAnimatorController", "onAnimationCancel ");
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.this$0;
                int i = KeyguardEditModeAnimatorController.$r8$clinit;
                if (keyguardEditModeAnimatorController.isEditMode()) {
                    ((KeyguardEditModeControllerImpl) this.this$0.keyguardEditModeController).isEditMode = false;
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.this$0;
                int i = KeyguardEditModeAnimatorController.$r8$clinit;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onAnimationEnd EM=", "KeyguardEditModeAnimatorController", keyguardEditModeAnimatorController.isEditMode());
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0.keyguardViewMediatorHelperImpl;
                if (keyguardViewMediatorHelperImpl.lockShownJob != null) {
                    com.android.systemui.keyguard.Log.d("KeyguardViewMediator", "setLockScreenShownFromEditMode: needKeyguardAppearAnimation=" + keyguardViewMediatorHelperImpl.needKeyguardAppearAnimation);
                    keyguardViewMediatorHelperImpl.needKeyguardAppearAnimation = false;
                    StandaloneCoroutine standaloneCoroutine = keyguardViewMediatorHelperImpl.lockShownJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    keyguardViewMediatorHelperImpl.setLockScreenShownRunnable.run();
                }
                if (this.this$0.isEditMode()) {
                    KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = this.this$0;
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    keyguardEditModeAnimatorController2.startActivityJob = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1(this.this$0, null), 3);
                } else {
                    this.this$0.keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_STARTED_LOCK_EDIT_MODE_FINISHED);
                    ((KeyguardEditModeControllerImpl) this.this$0.keyguardEditModeController).cancel();
                    this.this$0.resetViews();
                    Log.d("KeyguardTouchAnimator", "LOCKUI_EDIT_MODE is finished");
                    this.this$0.keyguardTouchAnimator.lockscreenShadeTransitionController.isKeyguardAnimatorStarted = false;
                }
            }
        });
        this.animatorSet = animatorSet;
        KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
        View parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        ViewGroup viewGroup = (ViewGroup) parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core;
        viewGroup.setClipToPadding(false);
        viewGroup.setClipChildren(false);
        ViewParent parent = viewGroup.getParent();
        ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup2 != null) {
            viewGroup2.setClipToPadding(false);
            viewGroup2.setClipChildren(false);
        }
        viewGroup.setPivotX(((Number) getEditModePivot().getFirst()).floatValue());
        viewGroup.setPivotY(((Number) getEditModePivot().getSecond()).floatValue());
        KeyguardEditModeController keyguardEditModeController = this.keyguardEditModeController;
        if (z) {
            setViewScaleAnimation(this.animatorSet, parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core, ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale, 500L, 0L);
        } else {
            viewGroup.setScaleX(((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
            viewGroup.setScaleY(((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
            setViewScaleAnimation(this.animatorSet, parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 1.0f, 500L, 100L);
        }
        List list = this.noScaleViews;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) arrayList.get(i)).intValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setPivotX(((Number) getEditModePivot().getFirst()).floatValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setPivotY(((Number) getEditModePivot().getSecond()).floatValue());
            if (z) {
                setViewScaleAnimation(this.animatorSet, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale, 500L, 0L);
            } else {
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleX(1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleY(1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
                setViewScaleAnimation(this.animatorSet, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 1.0f, 500L, 100L);
            }
            i = i2;
        }
        List list2 = this.alphaViews;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            int i4 = i3 + 1;
            int iIntValue = ((Number) arrayList2.get(i3)).intValue();
            if (z) {
                setViewAlphaAnimation(this.animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(iIntValue), 0.0f, 150L, 0L);
            } else {
                View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(iIntValue);
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.setAlpha(0.0f);
                setViewAlphaAnimation(this.animatorSet, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2, 1.0f, 300L, 300L);
            }
            i3 = i4;
        }
        if (z) {
            List list3 = this.scaleViews;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : list3) {
                if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj3).intValue())) {
                    arrayList3.add(obj3);
                }
            }
            int size3 = arrayList3.size();
            for (int i5 = 0; i5 < size3; i5++) {
                setViewScaleAnimation(this.animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) arrayList3.get(i5)).intValue()), 1.0f, 500L, 0L);
            }
        }
        this.animatorSet.start();
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeController;
        keyguardEditModeControllerImpl.getClass();
        com.android.systemui.keyguard.Log.d("KeyguardEditModeController", "startAnimation e=" + z);
        keyguardEditModeControllerImpl.isEditMode = z;
        keyguardEditModeControllerImpl.isCanceled = false;
        KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2 keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 = keyguardEditModeControllerImpl.updateViewsFunction;
        (keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 != null ? keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 : null).invoke(Boolean.TRUE, Boolean.valueOf(z));
        ArrayList arrayList4 = (ArrayList) keyguardEditModeControllerImpl.listeners;
        int size4 = arrayList4.size();
        int i6 = 0;
        while (i6 < size4) {
            Object obj4 = arrayList4.get(i6);
            i6++;
            ((KeyguardEditModeController.Listener) obj4).onAnimationStarted(z);
        }
        if (z) {
            keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().performHapticFeedback(0);
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_GO_TO_EDIT_MODE, z ? "1" : "2");
    }

    public final void cancel$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        StandaloneCoroutine standaloneCoroutine;
        StandaloneCoroutine standaloneCoroutine2;
        Log.d("KeyguardEditModeAnimatorController", "cancel()");
        StandaloneCoroutine standaloneCoroutine3 = this.longPressJob;
        if (standaloneCoroutine3 != null && standaloneCoroutine3.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
            StandaloneCoroutine standaloneCoroutine4 = this.longPressJob;
            if (standaloneCoroutine4 != null) {
                standaloneCoroutine4.cancel(null);
            }
        }
        if (this.touchDownAnimatorSet.isRunning()) {
            this.touchDownAnimatorSet.cancel();
        }
        if (this.animatorSet.isRunning()) {
            Log.d("KeyguardEditModeAnimatorController", "cancel : isAnimationRunning");
            this.animatorSet.cancel();
        }
        if (isEditMode() && ((standaloneCoroutine2 = this.startActivityJob) == null || !standaloneCoroutine2.isActive())) {
            startCancelAnimation();
        }
        if (!isKeyguardState() && (standaloneCoroutine = this.startActivityJob) != null && standaloneCoroutine.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "startActivityJob?.cancel");
            StandaloneCoroutine standaloneCoroutine5 = this.startActivityJob;
            if (standaloneCoroutine5 != null) {
                standaloneCoroutine5.cancel(null);
            }
        }
        this.keyguardWallpaper.getClass();
    }

    public final Pair getEditModePivot() {
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) this.keyguardEditModeController;
        Rect bounds = keyguardEditModeControllerImpl.windowManager.getCurrentWindowMetrics().getBounds();
        return new Pair(Float.valueOf(bounds.right / 2), Float.valueOf((bounds.bottom * keyguardEditModeControllerImpl.previewTopMargin) / (1.0f - keyguardEditModeControllerImpl.previewScale)));
    }

    public final boolean isEditMode() {
        return ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).isEditMode;
    }

    public final boolean isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        if (isEditMode() || this.animatorSet.isRunning()) {
            Log.d("KeyguardEditModeAnimatorController", "long pressed");
            return true;
        }
        Log.d("KeyguardEditModeAnimatorController", "long pressed false");
        return false;
    }

    public final boolean isNotSupportedAnimation() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        return (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(this.selectedUserInteractor.getSelectedUserId())) || this.settingsHelper.isRemoveAnimation();
    }

    public final void resetViews() {
        Log.d("KeyguardEditModeAnimatorController", "resetViews");
        KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
        View parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleX(1.0f);
        parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleY(1.0f);
        List list = this.noScaleViews;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleX(1.0f);
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleY(1.0f);
        }
        List list2 = this.alphaViews;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj3 : list2) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj3).intValue())) {
                arrayList2.add(obj3);
            }
        }
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj4 = arrayList2.get(i3);
            i3++;
            keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj4).intValue()).setAlpha(1.0f);
        }
        List list3 = this.scaleViews;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj5 : list3) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj5).intValue())) {
                arrayList3.add(obj5);
            }
        }
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj6 = arrayList3.get(i);
            i++;
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj6).intValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.setScaleX(1.0f);
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.setScaleY(1.0f);
        }
        ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).cancel();
        StandaloneCoroutine standaloneCoroutine = this.startActivityJob;
        if (standaloneCoroutine == null || !standaloneCoroutine.isActive()) {
            return;
        }
        Log.d("KeyguardEditModeAnimatorController", "startActivityJob?.cancel");
        StandaloneCoroutine standaloneCoroutine2 = this.startActivityJob;
        if (standaloneCoroutine2 != null) {
            standaloneCoroutine2.cancel(null);
        }
    }

    public final void startCancelAnimation() {
        Log.d("KeyguardEditModeAnimatorController", "startCancelAnimation");
        boolean zIsKeyguardState = isKeyguardState();
        int i = 0;
        KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
        if (!zIsKeyguardState) {
            Log.d("KeyguardEditModeAnimatorController", "startCancelAnimation : is not keyguard state");
            keyguardTouchAnimator.lockscreenShadeTransitionController.isKeyguardAnimatorStarted = false;
            resetViews();
            if (this.touchDownAnimatorSet.isRunning()) {
                this.touchDownAnimatorSet.cancel();
                return;
            }
            return;
        }
        View parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        if (parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core != null && parentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getScaleX() == 1.0f) {
            KeyguardEditModeController keyguardEditModeController = this.keyguardEditModeController;
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).cancel();
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode = false;
            return;
        }
        if (this.cancelAnimatorSet.isRunning()) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        setViewAnimation(animatorSet, keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), 1.0f, 1.0f);
        List list = this.alphaViews;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue()), (Property<View, Float>) View.ALPHA, 1.0f);
            objectAnimatorOfFloat.setInterpolator(this.alphaPathInterpolator);
            Unit unit = Unit.INSTANCE;
            animatorSet.playTogether(objectAnimatorOfFloat);
        }
        List list2 = this.noScaleViews;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj3 : list2) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj3).intValue())) {
                arrayList2.add(obj3);
            }
        }
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj4 = arrayList2.get(i);
            i++;
            setViewAnimation(animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj4).intValue()), 1.0f, 1.0f);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$startCancelAnimation$2$5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ((KeyguardEditModeControllerImpl) this.this$0.keyguardEditModeController).cancel();
                ((KeyguardEditModeControllerImpl) this.this$0.keyguardEditModeController).isEditMode = false;
            }
        });
        animatorSet.setDuration(200L);
        animatorSet.start();
        this.cancelAnimatorSet = animatorSet;
    }
}
