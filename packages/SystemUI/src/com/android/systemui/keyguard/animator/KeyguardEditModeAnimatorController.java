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
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.keyguard.KeyguardClickController;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardEditModeAnimatorController extends ViewAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List alphaViews;
    public AnimatorSet animatorSet;
    public AnimatorSet cancelAnimatorSet;
    public final KeyguardClickController keyguardClickController;
    public final KeyguardEditModeController keyguardEditModeController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardWallpaper keyguardWallpaper;
    public Job longPressJob;
    public final List noScaleViews;
    public final List scaleViews;
    public final SelectedUserInteractor selectedUserInteractor;
    private final SettingsHelper settingsHelper;
    public Job startActivityJob;
    public AnimatorSet touchDownAnimatorSet;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public KeyguardEditModeAnimatorController(KeyguardTouchAnimator keyguardTouchAnimator, SelectedUserInteractor selectedUserInteractor, KeyguardEditModeController keyguardEditModeController, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor, SecRotationWatcher secRotationWatcher, SettingsHelper settingsHelper, KeyguardClickController keyguardClickController) {
        super(keyguardTouchAnimator);
        this.selectedUserInteractor = selectedUserInteractor;
        this.keyguardEditModeController = keyguardEditModeController;
        this.keyguardWallpaper = keyguardWallpaper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.keyguardClickController = keyguardClickController;
        this.animatorSet = new AnimatorSet();
        this.touchDownAnimatorSet = new AnimatorSet();
        this.alphaViews = CollectionsKt__CollectionsKt.listOf(7, 8, 9, 10, 3, 6, 2, 12);
        this.scaleViews = CollectionsKt__CollectionsKt.listOf(7, 1, 8, 9, 10, 3, 6, 2, 4, 5, 12);
        this.noScaleViews = CollectionsKt__CollectionsKt.listOf(0, 11);
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
            boolean isEmpty = secRotationWatcher.mListeners.isEmpty();
            secRotationWatcher.mListeners.add(intConsumer);
            intConsumer.accept(secRotationWatcher.mCurrentRotation);
            if (isEmpty) {
                try {
                    SecurityLog.d("SecRotationWatcher", "enable watchRotation");
                    WindowManagerGlobal.getWindowManagerService().watchRotation(secRotationWatcher.mWatcher, secRotationWatcher.mContext.getDisplayId());
                } catch (RemoteException e) {
                    Log.w("SecRotationWatcher", "Failed to set rotation watcher", e);
                }
            }
        }
        ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).isAnimationRunning = new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(KeyguardEditModeAnimatorController.this.animatorSet.isRunning());
            }
        };
        KeyguardEditModeController keyguardEditModeController2 = this.keyguardEditModeController;
        new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(KeyguardEditModeAnimatorController.this.touchDownAnimatorSet.isRunning());
            }
        };
        keyguardEditModeController2.getClass();
        ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).startCancelAnimationFunction = new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardEditModeAnimatorController.this.startCancelAnimation();
                return Unit.INSTANCE;
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
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = KeyguardEditModeAnimatorController.this;
                int i = KeyguardEditModeAnimatorController.$r8$clinit;
                if (keyguardEditModeAnimatorController.isEditMode()) {
                    ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).isEditMode = false;
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = KeyguardEditModeAnimatorController.this;
                int i = KeyguardEditModeAnimatorController.$r8$clinit;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onAnimationEnd EM=", "KeyguardEditModeAnimatorController", keyguardEditModeAnimatorController.isEditMode());
                if (KeyguardEditModeAnimatorController.this.isEditMode()) {
                    KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = KeyguardEditModeAnimatorController.this;
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    keyguardEditModeAnimatorController2.startActivityJob = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1(KeyguardEditModeAnimatorController.this, null), 3);
                } else {
                    KeyguardEditModeAnimatorController.this.keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_STARTED_LOCK_EDIT_MODE_FINISHED);
                    ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).cancel();
                    KeyguardEditModeAnimatorController.this.resetViews();
                    Log.d("KeyguardTouchAnimator", "LOCKUI_EDIT_MODE is finished");
                    KeyguardEditModeAnimatorController.this.keyguardTouchAnimator.lockscreenShadeTransitionController.isKeyguardAnimatorStarted = false;
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
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it.next()).intValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setPivotX(((Number) getEditModePivot().getFirst()).floatValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setPivotY(((Number) getEditModePivot().getSecond()).floatValue());
            if (z) {
                setViewScaleAnimation(this.animatorSet, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale, 500L, 0L);
            } else {
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleX(1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleY(1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
                setViewScaleAnimation(this.animatorSet, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, 1.0f, 500L, 100L);
            }
        }
        List list2 = this.alphaViews;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            int intValue = ((Number) it2.next()).intValue();
            if (z) {
                setViewAlphaAnimation(this.animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(intValue), 0.0f, 150L, 0L);
            } else {
                View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(intValue);
                view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.setAlpha(0.0f);
                setViewAlphaAnimation(this.animatorSet, view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2, 1.0f, 300L, 300L);
            }
        }
        if (z) {
            List list3 = this.scaleViews;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : list3) {
                if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj3).intValue())) {
                    arrayList3.add(obj3);
                }
            }
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                setViewScaleAnimation(this.animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it3.next()).intValue()), 1.0f, 500L, 0L);
            }
        }
        this.animatorSet.start();
        ((KeyguardEditModeControllerImpl) keyguardEditModeController).startAnimation(z);
        if (z) {
            keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().performHapticFeedback(0);
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_GO_TO_EDIT_MODE, z ? "1" : "2");
    }

    public final void cancel$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        Job job;
        Job job2;
        Log.d("KeyguardEditModeAnimatorController", "cancel()");
        Job job3 = this.longPressJob;
        if (job3 != null && job3.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
            Job job4 = this.longPressJob;
            if (job4 != null) {
                job4.cancel(null);
            }
        }
        if (this.touchDownAnimatorSet.isRunning()) {
            this.touchDownAnimatorSet.cancel();
        }
        if (this.animatorSet.isRunning()) {
            Log.d("KeyguardEditModeAnimatorController", "cancel : isAnimationRunning");
            this.animatorSet.cancel();
        }
        if (isEditMode() && ((job2 = this.startActivityJob) == null || !job2.isActive())) {
            startCancelAnimation();
        }
        if (!isKeyguardState() && (job = this.startActivityJob) != null && job.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "startActivityJob?.cancel");
            Job job5 = this.startActivityJob;
            if (job5 != null) {
                job5.cancel(null);
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
        return (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(this.selectedUserInteractor.getSelectedUserId(false))) || this.settingsHelper.isRemoveAnimation();
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
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it.next()).intValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleX(1.0f);
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleY(1.0f);
        }
        List list2 = this.alphaViews;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it2.next()).intValue()).setAlpha(1.0f);
        }
        List list3 = this.scaleViews;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : list3) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj3).intValue())) {
                arrayList3.add(obj3);
            }
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it3.next()).intValue());
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.setScaleX(1.0f);
            view$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.setScaleY(1.0f);
        }
        ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).cancel();
        Job job = this.startActivityJob;
        if (job == null || !job.isActive()) {
            return;
        }
        Log.d("KeyguardEditModeAnimatorController", "startActivityJob?.cancel");
        Job job2 = this.startActivityJob;
        if (job2 != null) {
            job2.cancel(null);
        }
    }

    public final void startCancelAnimation() {
        Log.d("KeyguardEditModeAnimatorController", "startCancelAnimation");
        if (!isKeyguardState()) {
            Log.d("KeyguardEditModeAnimatorController", "startCancelAnimation : is not keyguard state");
            resetViews();
            if (this.touchDownAnimatorSet.isRunning()) {
                this.touchDownAnimatorSet.cancel();
                return;
            }
            return;
        }
        KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
        if (keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getScaleX() == 1.0f) {
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
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it.next()).intValue()), (Property<View, Float>) View.ALPHA, 1.0f);
            ofFloat.setInterpolator(this.alphaPathInterpolator);
            Unit unit = Unit.INSTANCE;
            animatorSet.playTogether(ofFloat);
        }
        List list2 = this.noScaleViews;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            setViewAnimation(animatorSet, keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it2.next()).intValue()), 1.0f, 1.0f);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$startCancelAnimation$2$5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).cancel();
                ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).isEditMode = false;
            }
        });
        animatorSet.setDuration(200L);
        animatorSet.start();
        this.cancelAnimatorSet = animatorSet;
    }
}
