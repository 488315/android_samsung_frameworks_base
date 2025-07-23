package com.android.systemui.statusbar.phone.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CollapsedStatusBarFragmentExt implements KeyguardStateController.Callback, BootAnimationFinishedCache.BootAnimationFinishedListener {
    public final ActivityManager activityManager;
    public final DelayableExecutor bgExecutor;
    public final CoroutineScope bgScope;
    public final CollapsedStatusBarFragmentExt$cameraListener$1 cameraListener;
    public final CameraManager cameraManager;
    public final Context context;
    public final Lazy displayLifecycleLazy;
    public final Lazy keyguardStateControllerLazy;
    public boolean lastFullscreenFlagState;
    public final Handler mainHandler;
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;
    public boolean shouldHideIconsForNextAppWindow;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public final StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager;
    public final StatusBarModeRepositoryStore statusBarModeRepository;
    public CollapsedStatusBarFragment$$ExternalSyntheticLambda1 updateRunnable;
    public String lastFullscreenPkgName = "";
    public String updateStatusBarVisibilitiesCallers = "empty";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$cameraListener$1] */
    public CollapsedStatusBarFragmentExt(Context context, CoroutineScope coroutineScope, Handler handler, DelayableExecutor delayableExecutor, BootAnimationFinishedCache bootAnimationFinishedCache, Lazy lazy, Lazy lazy2, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, SlimIndicatorViewMediator slimIndicatorViewMediator, CameraManager cameraManager, StatusBarModeRepositoryStore statusBarModeRepositoryStore, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, ActivityManager activityManager) {
        this.context = context;
        this.bgScope = coroutineScope;
        this.mainHandler = handler;
        this.bgExecutor = delayableExecutor;
        this.keyguardStateControllerLazy = lazy;
        this.displayLifecycleLazy = lazy2;
        this.statusBarHideIconsForBouncerManager = statusBarHideIconsForBouncerManager;
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.cameraManager = cameraManager;
        this.statusBarModeRepository = statusBarModeRepositoryStore;
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.activityManager = activityManager;
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(this);
        this.cameraListener = new CameraManager.AvailabilityCallback() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$cameraListener$1
            public final void onCameraOpened(String str, String str2) {
                boolean z = ((KeyguardStateControllerImpl) ((KeyguardStateController) CollapsedStatusBarFragmentExt.this.keyguardStateControllerLazy.get())).mKeyguardFadingAway || ((KeyguardStateControllerImpl) ((KeyguardStateController) CollapsedStatusBarFragmentExt.this.keyguardStateControllerLazy.get())).mKeyguardGoingAway || ((KeyguardStateControllerImpl) ((KeyguardStateController) CollapsedStatusBarFragmentExt.this.keyguardStateControllerLazy.get())).mOccluded || ((KeyguardStateControllerImpl) ((KeyguardStateController) CollapsedStatusBarFragmentExt.this.keyguardStateControllerLazy.get())).mShowing;
                CollapsedStatusBarFragmentExt.this.printStatusBarInfoLog(MotionLayout$$ExternalSyntheticOutline0.m("onCameraOpened(cId:", str, ", pId:", str2, ")"));
                if (z) {
                    CollapsedStatusBarFragmentExt.access$updateHideIconsForNextAppWindow(CollapsedStatusBarFragmentExt.this, "CameraManager.AvailabilityCallback");
                }
            }

            public final void onCameraClosed(String str) {
            }
        };
    }

    public static final boolean access$isShouldHidePackage(CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt, String str) {
        collapsedStatusBarFragmentExt.getClass();
        if (str != null && str.length() != 0) {
            if (StringsKt__StringsKt.contains(str, "com.sec.android.app.camera", false) || StringsKt__StringsKt.contains(str, "com.samsung.android.app.dressroom", false)) {
                return true;
            }
            if (StringsKt__StringsKt.contains(str, "com.sec.android.app.launcher", false) || StringsKt__StringsKt.contains(str, "com.samsung.android.dialer", false)) {
                if (collapsedStatusBarFragmentExt.lastFullscreenFlagState && collapsedStatusBarFragmentExt.lastFullscreenPkgName.equals(str)) {
                    return true;
                }
                if (!DeviceType.isTablet() && ((!BasicRune.BASIC_FOLDABLE_TYPE_FOLD || collapsedStatusBarFragmentExt.context.getResources().getConfiguration().semDisplayDeviceType != 0) && collapsedStatusBarFragmentExt.context.getResources().getConfiguration().orientation == 2)) {
                    return true;
                }
            } else if (collapsedStatusBarFragmentExt.lastFullscreenFlagState && collapsedStatusBarFragmentExt.lastFullscreenPkgName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static final void access$updateHideIconsForNextAppWindow(final CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt, String str) {
        collapsedStatusBarFragmentExt.getClass();
        collapsedStatusBarFragmentExt.printStatusBarInfoLog("updateHideIconsForNextAppWindow(" + str + ") shouldHideIconsForNextAppWindow will be true");
        collapsedStatusBarFragmentExt.shouldHideIconsForNextAppWindow = true;
        collapsedStatusBarFragmentExt.bgExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$updateHideIconsForNextAppWindow$1
            @Override // java.lang.Runnable
            public final void run() {
                CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt2 = CollapsedStatusBarFragmentExt.this;
                collapsedStatusBarFragmentExt2.shouldHideIconsForNextAppWindow = false;
                collapsedStatusBarFragmentExt2.postUpdateStatusBarVisibility();
            }
        }, 300L);
    }

    @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
    public final void onBootAnimationFinished() {
        this.lastFullscreenFlagState = ((Boolean) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) this.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode.$$delegate_0.getValue()).booleanValue();
        BuildersKt.launch$default(this.bgScope, null, null, new CollapsedStatusBarFragmentExt$onBootAnimationFinished$1(this, null), 3);
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardShowingChanged() {
        printStatusBarInfoLog("onKeyguardShowingChanged()");
        CollapsedStatusBarFragment$$ExternalSyntheticLambda1 collapsedStatusBarFragment$$ExternalSyntheticLambda1 = this.updateRunnable;
        if (collapsedStatusBarFragment$$ExternalSyntheticLambda1 != null) {
            collapsedStatusBarFragment$$ExternalSyntheticLambda1.run();
        }
    }

    public final void postUpdateStatusBarVisibility() {
        CollapsedStatusBarFragment$$ExternalSyntheticLambda1 collapsedStatusBarFragment$$ExternalSyntheticLambda1 = this.updateRunnable;
        if (collapsedStatusBarFragment$$ExternalSyntheticLambda1 != null) {
            Handler handler = this.mainHandler;
            if (handler.hasCallbacks(collapsedStatusBarFragment$$ExternalSyntheticLambda1)) {
                handler.removeCallbacks(collapsedStatusBarFragment$$ExternalSyntheticLambda1);
            }
            handler.postDelayed(collapsedStatusBarFragment$$ExternalSyntheticLambda1, 200L);
        }
    }

    public final void printStatusBarInfoLog(String str) {
        Lazy lazy = this.keyguardStateControllerLazy;
        boolean z = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mKeyguardFadingAway;
        boolean z2 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mKeyguardGoingAway;
        boolean z3 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mOccluded;
        boolean z4 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mShowing;
        StringBuilder sb = new StringBuilder(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  "));
        sb.append("  shouldHideIconsForNextAppWindow:" + this.shouldHideIconsForNextAppWindow);
        sb.append(", lastFullscreen(pkg:".concat(this.lastFullscreenPkgName));
        sb.append(", flag:" + this.lastFullscreenFlagState + ")");
        StringBuilder sb2 = new StringBuilder(",    keyguard(fa:");
        sb2.append(z);
        sb.append(sb2.toString());
        sb.append(", ga:" + z2);
        sb.append(", oc:" + z3);
        sb.append(", show:" + z4 + ")");
        Log.d("CollapsedStatusBarFragmentExt", sb.toString());
    }
}
