package com.android.systemui.statusbar.window;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.IWindowManager;
import android.view.InsetsFrameProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DelegateTransitionAnimatorController;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.JankMonitorTransitionProgressListener;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarWindowControllerImpl implements StatusBarWindowController {
    public int mBarHeight;
    public final StatusBarContentInsetsProvider mContentInsetsProvider;
    public final Context mContext;
    public final StatusBarWindowControllerExt mExt;
    public final FragmentService mFragmentService;
    public final IWindowManager mIWindowManager;
    public boolean mIsAttached;
    public final ViewGroup mLaunchAnimationContainer;
    public WindowManager.LayoutParams mLp;
    public final WindowManager.LayoutParams mLpChanged;
    public final Executor mMainExecutor;
    public final StatusBarWindowView mStatusBarWindowView;
    public final WindowManager mWindowManager;
    public final State mCurrentState = new State(0);
    public final Binder mInsetsSourceOwner = new Binder();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory extends StatusBarWindowController.Factory {
        StatusBarWindowControllerImpl create(Context context, WindowManager windowManager, StatusBarConfigurationController statusBarConfigurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class State {
        public boolean mChangeStatusBarHeight;
        public boolean mForceStatusBarVisible;
        public boolean mIsAODAmbientWallpaperWakingUp;
        public boolean mIsHideInformationMirroring;
        public boolean mIsLaunchAnimationRunning;
        public boolean mOngoingProcessRequiresStatusBarVisible;

        public /* synthetic */ State(int i) {
            this();
        }

        private State() {
            this.mIsAODAmbientWallpaperWakingUp = true;
        }
    }

    public StatusBarWindowControllerImpl(Context context, StatusBarWindowViewInflater statusBarWindowViewInflater, WindowManager windowManager, StatusBarConfigurationController statusBarConfigurationController, IWindowManager iWindowManager, StatusBarContentInsetsProvider statusBarContentInsetsProvider, FragmentService fragmentService, Optional<UnfoldTransitionProgressProvider> optional, Executor executor, StatusBarWindowControllerExt statusBarWindowControllerExt) {
        this.mBarHeight = -1;
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mIWindowManager = iWindowManager;
        this.mContentInsetsProvider = statusBarContentInsetsProvider;
        this.mMainExecutor = executor;
        ((StatusBarWindowViewInflaterImpl) statusBarWindowViewInflater).getClass();
        StatusBarWindowView statusBarWindowView = (StatusBarWindowView) LayoutInflater.from(context).inflate(R.layout.super_status_bar, (ViewGroup) null);
        if (statusBarWindowView == null) {
            throw new IllegalStateException("R.layout.super_status_bar could not be properly inflated");
        }
        this.mStatusBarWindowView = statusBarWindowView;
        this.mFragmentService = fragmentService;
        this.mLaunchAnimationContainer = (ViewGroup) statusBarWindowView.findViewById(R.id.status_bar_launch_animation_container);
        this.mLpChanged = new WindowManager.LayoutParams();
        if (this.mBarHeight < 0) {
            this.mBarHeight = SystemBarUtils.getStatusBarHeight(context);
        }
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.window.StatusBarWindowControllerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final StatusBarWindowControllerImpl statusBarWindowControllerImpl = StatusBarWindowControllerImpl.this;
                ((UnfoldTransitionProgressProvider) obj).addCallback(new JankMonitorTransitionProgressListener(new Supplier() { // from class: com.android.systemui.statusbar.window.StatusBarWindowControllerImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return StatusBarWindowControllerImpl.this.mStatusBarWindowView;
                    }
                }));
            }
        });
        this.mExt = statusBarWindowControllerExt;
    }

    public final void apply(State state) {
        if (this.mIsAttached) {
            if (state.mForceStatusBarVisible || state.mIsLaunchAnimationRunning || state.mOngoingProcessRequiresStatusBarVisible) {
                this.mLpChanged.forciblyShownTypes |= WindowInsets.Type.statusBars();
            } else {
                this.mLpChanged.forciblyShownTypes &= ~WindowInsets.Type.statusBars();
            }
            WindowManager.LayoutParams layoutParams = this.mLpChanged;
            layoutParams.height = state.mIsLaunchAnimationRunning ? -1 : this.mBarHeight;
            if (LsRune.COVER_SUPPORTED && state.mChangeStatusBarHeight) {
                layoutParams.height = 1;
            }
            if (state.mIsAODAmbientWallpaperWakingUp) {
                layoutParams.samsungFlags &= -262145;
            } else {
                layoutParams.height = 1;
                layoutParams.samsungFlags |= 262144;
            }
            for (int i = 0; i <= 3; i++) {
                int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(this.mContext, i);
                WindowManager.LayoutParams layoutParams2 = this.mLpChanged.paramsForRotation[i];
                layoutParams2.height = state.mIsLaunchAnimationRunning ? -1 : statusBarHeightForRotation;
                InsetsFrameProvider[] insetsFrameProviderArr = layoutParams2.providedInsets;
                if (insetsFrameProviderArr != null) {
                    for (InsetsFrameProvider insetsFrameProvider : insetsFrameProviderArr) {
                        insetsFrameProvider.setInsetsSize(Insets.of(0, statusBarHeightForRotation, 0, 0));
                    }
                }
            }
            if (state.mIsHideInformationMirroring) {
                this.mLpChanged.semAddExtensionFlags(Integer.MIN_VALUE);
            } else {
                this.mLpChanged.semClearExtensionFlags(Integer.MIN_VALUE);
            }
            WindowManager.LayoutParams layoutParams3 = this.mLp;
            if (layoutParams3 == null || layoutParams3.copyFrom(this.mLpChanged) == 0) {
                return;
            }
            this.mWindowManager.updateViewLayout(this.mStatusBarWindowView, this.mLp);
        }
    }

    public final void calculateStatusBarLocationsForAllRotations() {
        DisplayCutout cutout = this.mContext.getDisplay().getCutout();
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = this.mContentInsetsProvider;
        try {
            this.mIWindowManager.updateStaticPrivacyIndicatorBounds(this.mContext.getDisplayId(), new Rect[]{((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider).getBoundingRectForPrivacyChipForRotation(0, cutout), ((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider).getBoundingRectForPrivacyChipForRotation(1, cutout), ((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider).getBoundingRectForPrivacyChipForRotation(2, cutout), ((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider).getBoundingRectForPrivacyChipForRotation(3, cutout)});
        } catch (RemoteException unused) {
        }
    }

    public final WindowManager.LayoutParams getBarLayoutParamsForRotation(int i) {
        int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(this.mContext, i);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, statusBarHeightForRotation, 2000, -2147483640, -3);
        layoutParams.privateFlags |= 16777216;
        layoutParams.token = new Binder();
        layoutParams.gravity = 48;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle(PluginLockStar.STATUS_BAR_TYPE);
        layoutParams.packageName = this.mContext.getPackageName();
        layoutParams.layoutInDisplayCutoutMode = 3;
        InsetsFrameProvider insetsFrameProvider = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.mandatorySystemGestures());
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_22);
        if (dimensionPixelSize > 0) {
            insetsFrameProvider.setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, dimensionPixelSize, 0, 0));
        }
        layoutParams.providedInsets = new InsetsFrameProvider[]{new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.statusBars()).setInsetsSize(Insets.of(0, statusBarHeightForRotation, 0, 0)), new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.tappableElement()).setInsetsSize(Insets.of(0, statusBarHeightForRotation, 0, 0)), insetsFrameProvider};
        if (this.mExt.mIndicatorCutoutUtil.isUDCModel) {
            layoutParams.semAddExtensionFlags(8192);
        }
        return layoutParams;
    }

    public final void setAODAmbientWallpaperState(boolean z) {
        State state = this.mCurrentState;
        if (state.mIsAODAmbientWallpaperWakingUp != z) {
            state.mIsAODAmbientWallpaperWakingUp = z;
            Log.d("StatusBarWindowController", "setAODAmbientWallpaperState: wakingUp=" + z);
            apply(state);
        }
    }

    public final void setForceStatusBarVisible(boolean z) {
        State state = this.mCurrentState;
        state.mForceStatusBarVisible = z;
        apply(state);
    }

    public final Optional wrapAnimationControllerIfInStatusBar(View view, ActivityTransitionAnimator.Controller controller) {
        if (view != this.mStatusBarWindowView) {
            return Optional.empty();
        }
        controller.setTransitionContainer(this.mLaunchAnimationContainer);
        return Optional.of(new DelegateTransitionAnimatorController(controller) { // from class: com.android.systemui.statusbar.window.StatusBarWindowControllerImpl.1
            @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(boolean z) {
                this.delegate.onTransitionAnimationEnd(z);
                StatusBarWindowControllerImpl statusBarWindowControllerImpl = StatusBarWindowControllerImpl.this;
                State state = statusBarWindowControllerImpl.mCurrentState;
                if (state.mIsLaunchAnimationRunning) {
                    state.mIsLaunchAnimationRunning = false;
                    statusBarWindowControllerImpl.apply(state);
                }
            }

            @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationStart(boolean z) {
                this.delegate.onTransitionAnimationStart(z);
                StatusBarWindowControllerImpl statusBarWindowControllerImpl = StatusBarWindowControllerImpl.this;
                State state = statusBarWindowControllerImpl.mCurrentState;
                if (true == state.mIsLaunchAnimationRunning) {
                    return;
                }
                state.mIsLaunchAnimationRunning = true;
                statusBarWindowControllerImpl.apply(state);
            }
        });
    }
}
