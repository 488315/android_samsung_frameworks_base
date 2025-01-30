package com.android.systemui.keyguard.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.shade.SecPanelExpansionStateNotifier;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
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
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardEditModeAnimatorController extends ViewAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List alphaViews;
    public AnimatorSet animatorSet;
    public AnimatorSet cancelAnimatorSet;
    public final KeyguardEditModeController keyguardEditModeController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardWallpaper keyguardWallpaper;
    public StandaloneCoroutine longPressJob;
    public final List noScaleViews;
    public final List scaleViews;
    public final SettingsHelper settingsHelper;
    public Job startActivityJob;
    public AnimatorSet touchDownAnimatorSet;

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

    public KeyguardEditModeAnimatorController(KeyguardTouchAnimator keyguardTouchAnimator, KeyguardEditModeController keyguardEditModeController, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor, SecRotationWatcher secRotationWatcher, SettingsHelper settingsHelper) {
        super(keyguardTouchAnimator);
        this.keyguardEditModeController = keyguardEditModeController;
        this.keyguardWallpaper = keyguardWallpaper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.animatorSet = new AnimatorSet();
        this.touchDownAnimatorSet = new AnimatorSet();
        this.alphaViews = CollectionsKt__CollectionsKt.listOf(7, 8, 9, 10, 3, 6, 2);
        this.scaleViews = CollectionsKt__CollectionsKt.listOf(7, 1, 8, 9, 10, 3, 6, 2, 4, 5);
        this.noScaleViews = CollectionsKt__CollectionsKt.listOf(0, 11);
        secRotationWatcher.addCallback(new IntConsumer() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = KeyguardEditModeAnimatorController.this;
                int i2 = KeyguardEditModeAnimatorController.$r8$clinit;
                AbstractC0731x5bb8a836.m72m("rotation ", i, " ", keyguardEditModeAnimatorController.isEditMode(), "KeyguardEditModeAnimatorController");
                if (((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).getVIRunning()) {
                    ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).cancel();
                    KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = KeyguardEditModeAnimatorController.this;
                    ((KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController2.keyguardEditModeController).isEditMode = false;
                    keyguardEditModeAnimatorController2.resetViews();
                }
            }
        });
        ((KeyguardEditModeControllerImpl) keyguardEditModeController).isAnimationRunning = new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(KeyguardEditModeAnimatorController.this.animatorSet.isRunning());
            }
        };
        ((KeyguardEditModeControllerImpl) keyguardEditModeController).isTouchDownAnimationRunning = new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(KeyguardEditModeAnimatorController.this.touchDownAnimatorSet.isRunning());
            }
        };
        ((KeyguardEditModeControllerImpl) keyguardEditModeController).startCancelAnimationFunction = new Function0() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = KeyguardEditModeAnimatorController.this;
                int i = KeyguardEditModeAnimatorController.$r8$clinit;
                keyguardEditModeAnimatorController.startCancelAnimation();
                return Unit.INSTANCE;
            }
        };
        ((SecPanelExpansionStateNotifier) Dependency.get(SecPanelExpansionStateNotifier.class)).registerTicket(new SecPanelExpansionStateNotifier.SecPanelExpansionStateTicket() { // from class: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController.5
            @Override // com.android.systemui.shade.SecPanelExpansionStateNotifier.SecPanelExpansionStateTicket
            public final String getName() {
                return "KeyguardEditModeAnimatorController";
            }

            @Override // com.android.systemui.shade.SecPanelExpansionStateNotifier.SecPanelExpansionStateTicket
            public final void onSecPanelExpansionStateChanged(int i, boolean z) {
                if (z) {
                    int i2 = KeyguardEditModeAnimatorController.$r8$clinit;
                    KeyguardEditModeAnimatorController.this.startCancelAnimation();
                }
            }
        });
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
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onAnimationEnd EM=", keyguardEditModeAnimatorController.isEditMode(), "KeyguardEditModeAnimatorController");
                if (KeyguardEditModeAnimatorController.this.isEditMode()) {
                    KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = KeyguardEditModeAnimatorController.this;
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    keyguardEditModeAnimatorController2.startActivityJob = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new C1526x3f2deed0(KeyguardEditModeAnimatorController.this, null), 3);
                } else {
                    KeyguardEditModeAnimatorController.this.keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_STARTED_LOCK_EDIT_MODE_FINISHED);
                    ((KeyguardEditModeControllerImpl) KeyguardEditModeAnimatorController.this.keyguardEditModeController).cancel();
                    KeyguardEditModeAnimatorController.this.resetViews();
                }
            }
        });
        this.animatorSet = animatorSet;
        View parentView = getParentView();
        ViewGroup viewGroup = (ViewGroup) parentView;
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
            setViewScaleAnimation(this.animatorSet, parentView, ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale, 500L, 0L);
        } else {
            viewGroup.setScaleX(((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
            viewGroup.setScaleY(((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
            setViewScaleAnimation(this.animatorSet, parentView, 1.0f, 500L, 100L);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : this.noScaleViews) {
            if (hasView(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            View view = getView(((Number) it.next()).intValue());
            view.setPivotX(((Number) getEditModePivot().getFirst()).floatValue());
            view.setPivotY(((Number) getEditModePivot().getSecond()).floatValue());
            if (z) {
                setViewScaleAnimation(this.animatorSet, view, 1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale, 500L, 0L);
            } else {
                view.setScaleX(1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
                view.setScaleY(1.0f / ((KeyguardEditModeControllerImpl) keyguardEditModeController).previewScale);
                setViewScaleAnimation(this.animatorSet, view, 1.0f, 500L, 100L);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : this.alphaViews) {
            if (hasView(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            int intValue = ((Number) it2.next()).intValue();
            if (z) {
                setViewAlphaAnimation(this.animatorSet, getView(intValue), 0.0f, 150L, 0L);
            } else {
                View view2 = getView(intValue);
                view2.setAlpha(0.0f);
                setViewAlphaAnimation(this.animatorSet, view2, 1.0f, 300L, 300L);
            }
        }
        if (z) {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : this.scaleViews) {
                if (hasView(((Number) obj3).intValue())) {
                    arrayList3.add(obj3);
                }
            }
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                setViewScaleAnimation(this.animatorSet, getView(((Number) it3.next()).intValue()), 1.0f, 500L, 0L);
            }
        }
        this.animatorSet.start();
        ((KeyguardEditModeControllerImpl) keyguardEditModeController).startAnimation(z);
        if (z) {
            getParentView().performHapticFeedback(0);
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "1011E", z ? "1" : "2");
    }

    /* renamed from: cancel$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m147xc9fd32c0() {
        Log.d("KeyguardEditModeAnimatorController", "cancel()");
        KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) this.keyguardWallpaper;
        keyguardWallpaperController.setThumbnailVisibility(4);
        StandaloneCoroutine standaloneCoroutine = this.longPressJob;
        boolean z = false;
        if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
            Log.d("KeyguardEditModeAnimatorController", "longPressJob?.cancel");
            StandaloneCoroutine standaloneCoroutine2 = this.longPressJob;
            if (standaloneCoroutine2 != null) {
                standaloneCoroutine2.cancel(null);
            }
        }
        if (this.touchDownAnimatorSet.isRunning()) {
            this.touchDownAnimatorSet.cancel();
        }
        if (this.animatorSet.isRunning()) {
            Log.d("KeyguardEditModeAnimatorController", "cancel : isAnimationRunning");
            this.animatorSet.cancel();
        }
        if (isEditMode()) {
            Job job = this.startActivityJob;
            if (!(job != null && job.isActive())) {
                startCancelAnimation();
            }
        }
        if (!isKeyguardState()) {
            Job job2 = this.startActivityJob;
            if (job2 != null && job2.isActive()) {
                z = true;
            }
            if (z) {
                Log.d("KeyguardEditModeAnimatorController", "startActivityJob?.cancel");
                Job job3 = this.startActivityJob;
                if (job3 != null) {
                    job3.cancel(null);
                }
            }
        }
        SystemUIWallpaperBase systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            systemUIWallpaperBase.updateDrawState(true);
        }
    }

    public final Pair getEditModePivot() {
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) this.keyguardEditModeController;
        Rect bounds = keyguardEditModeControllerImpl.windowManager.getCurrentWindowMetrics().getBounds();
        return new Pair(Float.valueOf(bounds.right / 2), Float.valueOf((bounds.bottom * keyguardEditModeControllerImpl.previewTopMargin) / (1.0f - keyguardEditModeControllerImpl.previewScale)));
    }

    public final boolean isEditMode() {
        return ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).isEditMode;
    }

    /* renamed from: isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final boolean m148x7abb39c2() {
        if (isEditMode() || this.animatorSet.isRunning()) {
            Log.d("KeyguardEditModeAnimatorController", "long pressed");
            return true;
        }
        Log.d("KeyguardEditModeAnimatorController", "long pressed false");
        return false;
    }

    public final boolean isNotSupportedAnimation() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser())) {
            return true;
        }
        return this.settingsHelper.mItemLists.get("remove_animations").getIntValue() == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f0, code lost:
    
        if (r1.isActive() == true) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resetViews() {
        Log.d("KeyguardEditModeAnimatorController", "resetViews");
        View parentView = getParentView();
        parentView.setScaleX(1.0f);
        parentView.setScaleY(1.0f);
        ArrayList arrayList = new ArrayList();
        for (Object obj : this.noScaleViews) {
            if (hasView(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            View view = getView(((Number) it.next()).intValue());
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : this.alphaViews) {
            if (hasView(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            getView(((Number) it2.next()).intValue()).setAlpha(1.0f);
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : this.scaleViews) {
            if (hasView(((Number) obj3).intValue())) {
                arrayList3.add(obj3);
            }
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            View view2 = getView(((Number) it3.next()).intValue());
            view2.setScaleX(1.0f);
            view2.setScaleY(1.0f);
        }
        ((KeyguardEditModeControllerImpl) this.keyguardEditModeController).cancel();
        Job job = this.startActivityJob;
        boolean z = job != null;
        if (z) {
            Log.d("KeyguardEditModeAnimatorController", "startActivityJob?.cancel");
            Job job2 = this.startActivityJob;
            if (job2 != null) {
                job2.cancel(null);
            }
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
        if (getParentView().getScaleX() == 1.0f) {
            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) this.keyguardEditModeController;
            keyguardEditModeControllerImpl.cancel();
            keyguardEditModeControllerImpl.isEditMode = false;
            return;
        }
        if (this.cancelAnimatorSet.isRunning()) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ViewAnimationController.setViewAnimation(animatorSet, getParentView(), 1.0f, 1.0f);
        ArrayList arrayList = new ArrayList();
        for (Object obj : this.alphaViews) {
            if (hasView(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(getView(((Number) it.next()).intValue()), (Property<View, Float>) View.ALPHA, 1.0f);
            ofFloat.setInterpolator(this.alphaPathInterpolator);
            Unit unit = Unit.INSTANCE;
            animatorSet.playTogether(ofFloat);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : this.noScaleViews) {
            if (hasView(((Number) obj2).intValue())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            ViewAnimationController.setViewAnimation(animatorSet, getView(((Number) it2.next()).intValue()), 1.0f, 1.0f);
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
