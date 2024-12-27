package com.android.systemui.statusbar.window;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.IWindowManager;
import android.view.InsetsFrameProvider;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.JankMonitorTransitionProgressListener;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarWindowController {
    public int mBarHeight;
    public final StatusBarContentInsetsProvider mContentInsetsProvider;
    public final Context mContext;
    public final AnonymousClass1 mDesktopManagerCallback;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.window.StatusBarWindowController$1, reason: invalid class name */
    public final class AnonymousClass1 implements DesktopManager.Callback {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.util.DesktopManager.Callback
        public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
            if (semDesktopModeState == null) {
                return;
            }
            int state = semDesktopModeState.getState();
            int enabled = semDesktopModeState.getEnabled();
            int displayType = semDesktopModeState.getDisplayType();
            StatusBarWindowController statusBarWindowController = StatusBarWindowController.this;
            if (state != 50) {
                if (state == 0 && enabled == 4 && displayType == 101) {
                    final int i = 2;
                    statusBarWindowController.mMainExecutor.execute(new Runnable(this) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ StatusBarWindowController.AnonymousClass1 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            StatusBarWindowController.AnonymousClass1 anonymousClass1 = this.f$0;
                            switch (i2) {
                                case 0:
                                    StatusBarWindowController.this.mStatusBarWindowView.setVisibility(8);
                                    break;
                                case 1:
                                    StatusBarWindowController.this.mStatusBarWindowView.setVisibility(0);
                                    break;
                                default:
                                    StatusBarWindowController.this.mStatusBarWindowView.setVisibility(8);
                                    break;
                            }
                        }
                    });
                    return;
                }
                return;
            }
            if (enabled == 4 && displayType == 101) {
                final int i2 = 0;
                statusBarWindowController.mMainExecutor.execute(new Runnable(this) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ StatusBarWindowController.AnonymousClass1 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        StatusBarWindowController.AnonymousClass1 anonymousClass1 = this.f$0;
                        switch (i22) {
                            case 0:
                                StatusBarWindowController.this.mStatusBarWindowView.setVisibility(8);
                                break;
                            case 1:
                                StatusBarWindowController.this.mStatusBarWindowView.setVisibility(0);
                                break;
                            default:
                                StatusBarWindowController.this.mStatusBarWindowView.setVisibility(8);
                                break;
                        }
                    }
                });
            } else if (enabled == 2 && displayType == 101) {
                final int i3 = 1;
                statusBarWindowController.mMainExecutor.execute(new Runnable(this) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ StatusBarWindowController.AnonymousClass1 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i3;
                        StatusBarWindowController.AnonymousClass1 anonymousClass1 = this.f$0;
                        switch (i22) {
                            case 0:
                                StatusBarWindowController.this.mStatusBarWindowView.setVisibility(8);
                                break;
                            case 1:
                                StatusBarWindowController.this.mStatusBarWindowView.setVisibility(0);
                                break;
                            default:
                                StatusBarWindowController.this.mStatusBarWindowView.setVisibility(8);
                                break;
                        }
                    }
                });
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class State {
        public boolean mChangeStatusBarHeight;
        public boolean mForceStatusBarVisible;
        public boolean mIsAODAmbientWallpaperWakingUp;
        public boolean mIsLaunchAnimationRunning;
        public boolean mShouldHideNotificationShadeInMirror;

        public /* synthetic */ State(int i) {
            this();
        }

        private State() {
            this.mIsAODAmbientWallpaperWakingUp = true;
            this.mShouldHideNotificationShadeInMirror = false;
        }
    }

    public StatusBarWindowController(Context context, StatusBarWindowView statusBarWindowView, WindowManager windowManager, IWindowManager iWindowManager, StatusBarContentInsetsProvider statusBarContentInsetsProvider, FragmentService fragmentService, Resources resources, Optional<UnfoldTransitionProgressProvider> optional, Executor executor, DesktopManager desktopManager, StatusBarWindowControllerExt statusBarWindowControllerExt) {
        this.mBarHeight = -1;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mIWindowManager = iWindowManager;
        this.mContentInsetsProvider = statusBarContentInsetsProvider;
        this.mStatusBarWindowView = statusBarWindowView;
        this.mFragmentService = fragmentService;
        this.mLaunchAnimationContainer = (ViewGroup) statusBarWindowView.findViewById(R.id.status_bar_launch_animation_container);
        this.mLpChanged = new WindowManager.LayoutParams();
        if (this.mBarHeight < 0) {
            this.mBarHeight = SystemBarUtils.getStatusBarHeight(context);
        }
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final StatusBarWindowController statusBarWindowController = StatusBarWindowController.this;
                statusBarWindowController.getClass();
                ((UnfoldTransitionProgressProvider) obj).addCallback(new JankMonitorTransitionProgressListener(new Supplier() { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return StatusBarWindowController.this.mStatusBarWindowView;
                    }
                }));
            }
        });
        this.mMainExecutor = executor;
        desktopManager.registerCallback(anonymousClass1);
        anonymousClass1.onDesktopModeStateChanged(desktopManager.getSemDesktopModeState());
        this.mExt = statusBarWindowControllerExt;
    }

    public final void apply(State state) {
        if (this.mIsAttached) {
            if (state.mForceStatusBarVisible || state.mIsLaunchAnimationRunning) {
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
            if (state.mShouldHideNotificationShadeInMirror) {
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
            this.mIWindowManager.updateStaticPrivacyIndicatorBounds(this.mContext.getDisplayId(), new Rect[]{statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(0, cutout), statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(1, cutout), statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(2, cutout), statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(3, cutout)});
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
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.harmful_app_name_padding_right);
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
}
