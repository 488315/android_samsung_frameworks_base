package com.android.systemui.statusbar.window;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Binder;
import android.os.RemoteException;
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
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.JankMonitorTransitionProgressListener;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarWindowController {
    public int mBarHeight;
    public final StatusBarContentInsetsProvider mContentInsetsProvider;
    public final Context mContext;
    public final State mCurrentState;
    public final C34541 mDesktopManagerCallback;
    public final FragmentService mFragmentService;
    public final GardenHeight mGardenHeight;
    public final IWindowManager mIWindowManager;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final Binder mInsetsSourceOwner = new Binder();
    public boolean mIsAttached;
    public final ViewGroup mLaunchAnimationContainer;
    public WindowManager.LayoutParams mLp;
    public final WindowManager.LayoutParams mLpChanged;
    public final Executor mMainExecutor;
    public final StatusBarWindowView mStatusBarWindowView;
    public final WindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.window.StatusBarWindowController$1 */
    public final class C34541 implements DesktopManager.Callback {
        public C34541() {
        }

        @Override // com.android.systemui.util.DesktopManager.Callback
        public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
            if (semDesktopModeState == null) {
                return;
            }
            int state = semDesktopModeState.getState();
            int enabled = semDesktopModeState.getEnabled();
            int displayType = semDesktopModeState.getDisplayType();
            final int i = 2;
            StatusBarWindowController statusBarWindowController = StatusBarWindowController.this;
            if (state != 50) {
                if (state == 0 && enabled == 4 && displayType == 101) {
                    statusBarWindowController.mMainExecutor.execute(new Runnable(this) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ StatusBarWindowController.C34541 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i) {
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
                    public final /* synthetic */ StatusBarWindowController.C34541 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
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
            } else if (enabled == 2 && displayType == 101) {
                final int i3 = 1;
                statusBarWindowController.mMainExecutor.execute(new Runnable(this) { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$1$$ExternalSyntheticLambda0
                    public final /* synthetic */ StatusBarWindowController.C34541 f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GardenHeight {
        public final int[] heights;

        public /* synthetic */ GardenHeight(int i) {
            this();
        }

        private GardenHeight() {
            this.heights = new int[]{-1, -1, -1, -1};
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class State {
        public boolean mChangeStatusBarHeight;
        public boolean mForceStatusBarVisible;
        public boolean mIsAODAmbientWallpaperWakingUp;
        public boolean mIsHideInformationMirroring;
        public boolean mIsLaunchAnimationRunning;

        public /* synthetic */ State(int i) {
            this();
        }

        private State() {
            this.mIsAODAmbientWallpaperWakingUp = true;
        }
    }

    public StatusBarWindowController(Context context, StatusBarWindowView statusBarWindowView, WindowManager windowManager, IWindowManager iWindowManager, StatusBarContentInsetsProvider statusBarContentInsetsProvider, FragmentService fragmentService, Resources resources, Optional<UnfoldTransitionProgressProvider> optional, Executor executor, DesktopManager desktopManager, IndicatorCutoutUtil indicatorCutoutUtil) {
        this.mBarHeight = -1;
        int i = 0;
        this.mCurrentState = new State(i);
        C34541 c34541 = new C34541();
        this.mDesktopManagerCallback = c34541;
        this.mGardenHeight = new GardenHeight(i);
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
        DesktopManagerImpl desktopManagerImpl = (DesktopManagerImpl) desktopManager;
        desktopManagerImpl.registerCallback(c34541);
        c34541.onDesktopModeStateChanged(desktopManagerImpl.getSemDesktopModeState());
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
    }

    public final void apply(State state, boolean z) {
        if (this.mIsAttached) {
            boolean z2 = state.mForceStatusBarVisible;
            WindowManager.LayoutParams layoutParams = this.mLpChanged;
            if (z2 || state.mIsLaunchAnimationRunning) {
                layoutParams.privateFlags |= 2048;
            } else {
                layoutParams.privateFlags &= -2049;
            }
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
                WindowManager.LayoutParams layoutParams2 = layoutParams.paramsForRotation[i];
                layoutParams2.height = state.mIsLaunchAnimationRunning ? -1 : statusBarHeightForRotation;
                InsetsFrameProvider[] insetsFrameProviderArr = layoutParams2.providedInsets;
                if (insetsFrameProviderArr != null) {
                    for (InsetsFrameProvider insetsFrameProvider : insetsFrameProviderArr) {
                        insetsFrameProvider.setInsetsSize(Insets.of(0, statusBarHeightForRotation, 0, 0));
                    }
                }
            }
            if (state.mIsHideInformationMirroring) {
                layoutParams.semAddExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
            } else {
                layoutParams.semClearExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
            }
            WindowManager.LayoutParams layoutParams3 = this.mLp;
            if (layoutParams3 != null) {
                if (layoutParams3.copyFrom(layoutParams) != 0 || z) {
                    this.mWindowManager.updateViewLayout(this.mStatusBarWindowView, this.mLp);
                }
            }
        }
    }

    public final void calculateStatusBarLocationsForAllRotations() {
        Context context = this.mContext;
        DisplayCutout cutout = context.getDisplay().getCutout();
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = this.mContentInsetsProvider;
        try {
            this.mIWindowManager.updateStaticPrivacyIndicatorBounds(context.getDisplayId(), new Rect[]{statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(0, cutout), statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(1, cutout), statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(2, cutout), statusBarContentInsetsProvider.getBoundingRectForPrivacyChipForRotation(3, cutout)});
        } catch (RemoteException unused) {
        }
    }

    public final WindowManager.LayoutParams getBarLayoutParamsForRotation(int i) {
        Context context = this.mContext;
        int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(context, i);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, statusBarHeightForRotation, 2000, -2147483640, -3);
        layoutParams.privateFlags |= 16777216;
        layoutParams.token = new Binder();
        layoutParams.gravity = 48;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("StatusBar");
        layoutParams.packageName = context.getPackageName();
        layoutParams.layoutInDisplayCutoutMode = 3;
        int mandatorySystemGestures = WindowInsets.Type.mandatorySystemGestures();
        Binder binder = this.mInsetsSourceOwner;
        InsetsFrameProvider insetsFrameProvider = new InsetsFrameProvider(binder, 0, mandatorySystemGestures);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(android.R.dimen.docked_stack_minimize_thickness);
        if (dimensionPixelSize > 0) {
            insetsFrameProvider.setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, dimensionPixelSize, 0, 0));
        }
        layoutParams.providedInsets = new InsetsFrameProvider[]{new InsetsFrameProvider(binder, 0, WindowInsets.Type.statusBars()).setInsetsSize(Insets.of(0, statusBarHeightForRotation, 0, 0)), new InsetsFrameProvider(binder, 0, WindowInsets.Type.tappableElement()).setInsetsSize(Insets.of(0, statusBarHeightForRotation, 0, 0)), insetsFrameProvider};
        if (this.mIndicatorCutoutUtil.isUDCModel) {
            layoutParams.semAddExtensionFlags(8192);
        }
        return layoutParams;
    }

    public final void setForceStatusBarVisible(boolean z) {
        State state = this.mCurrentState;
        state.mForceStatusBarVisible = z;
        apply(state, false);
    }
}
