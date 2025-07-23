package android.inputmethodservice;

import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.NavigationBarController;
import android.inputmethodservice.navigationbar.NavigationBarFrame;
import android.inputmethodservice.navigationbar.NavigationBarView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.util.Objects;

/* loaded from: classes2.dex */
final class NavigationBarController {
    private final Callback mImpl;

    private interface Callback {
        public static final Callback NOOP = new Callback() { // from class: android.inputmethodservice.NavigationBarController.Callback.1
        };

        default boolean isShown() {
            return false;
        }

        default void onDestroy() {
        }

        default void onNavButtonFlagsChanged(int i) {
        }

        default void onSoftInputWindowCreated(SoftInputWindow softInputWindow) {
        }

        default void onViewInitialized() {
        }

        default void onWindowShown() {
        }

        default void updateInsets(InputMethodService.Insets insets) {
        }

        default void updateTouchableInsets(InputMethodService.Insets insets, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        }

        default String toDebugString() {
            return "No-op implementation";
        }
    }

    NavigationBarController(InputMethodService inputMethodService) {
        this.mImpl = InputMethodService.canImeRenderGesturalNavButtons() ? new Impl(inputMethodService) : Callback.NOOP;
    }

    void updateInsets(InputMethodService.Insets insets) {
        this.mImpl.updateInsets(insets);
    }

    void updateTouchableInsets(InputMethodService.Insets insets, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        this.mImpl.updateTouchableInsets(insets, internalInsetsInfo);
    }

    void onSoftInputWindowCreated(SoftInputWindow softInputWindow) {
        this.mImpl.onSoftInputWindowCreated(softInputWindow);
    }

    void onViewInitialized() {
        this.mImpl.onViewInitialized();
    }

    void onWindowShown() {
        this.mImpl.onWindowShown();
    }

    void onDestroy() {
        this.mImpl.onDestroy();
    }

    void onNavButtonFlagsChanged(int i) {
        this.mImpl.onNavButtonFlagsChanged(i);
    }

    boolean isShown() {
        return this.mImpl.isShown();
    }

    String toDebugString() {
        return this.mImpl.toDebugString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Impl implements Callback, Window.DecorCallback, NavigationBarView.ButtonClickListener {
        private static final int DEFAULT_COLOR_ADAPT_TRANSITION_TIME = 1700;
        private static final Interpolator LEGACY_DECELERATE = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        private int mAppearance;
        private boolean mCustomImeSwitcherButtonRequestedVisible;
        private float mDarkIntensity;
        private boolean mDrawLegacyNavigationBarBackground;
        private boolean mImeDrawsImeNavBar;
        Insets mLastInsets;
        private NavigationBarFrame mNavigationBarFrame;
        private final InputMethodService mService;
        private boolean mShouldShowImeSwitcherWhenImeIsShown;
        private ValueAnimator mTintAnimator;
        private boolean mDestroyed = false;
        private final Rect mTempRect = new Rect();
        private final int[] mTempPos = new int[2];

        private static float calculateTargetDarkIntensity(int i, boolean z) {
            return (z || (i & 16) == 0) ? 0.0f : 1.0f;
        }

        Impl(InputMethodService inputMethodService) {
            this.mService = inputMethodService;
        }

        private Insets getSystemInsets() {
            View decorView;
            WindowInsets rootWindowInsets;
            if (this.mService.mWindow == null || (decorView = this.mService.mWindow.getWindow().getDecorView()) == null || (rootWindowInsets = decorView.getRootWindowInsets()) == null) {
                return null;
            }
            return Insets.min(rootWindowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()), rootWindowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()));
        }

        private void installNavigationBarFrameIfNecessary() {
            if (this.mImeDrawsImeNavBar && this.mNavigationBarFrame == null) {
                View decorView = this.mService.mWindow.getWindow().getDecorView();
                if (decorView instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) decorView;
                    this.mNavigationBarFrame = (NavigationBarFrame) viewGroup.findViewByPredicate(new NavigationBarController$Impl$$ExternalSyntheticLambda1(NavigationBarFrame.class));
                    Insets systemInsets = getSystemInsets();
                    NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                    if (navigationBarFrame == null) {
                        this.mNavigationBarFrame = new NavigationBarFrame(this.mService);
                        LayoutInflater.from(this.mService).inflate(R.layout.input_method_navigation_bar, this.mNavigationBarFrame);
                        if (systemInsets != null) {
                            viewGroup.addView(this.mNavigationBarFrame, new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                            this.mLastInsets = systemInsets;
                        } else {
                            viewGroup.addView(this.mNavigationBarFrame, new FrameLayout.LayoutParams(-1, getImeCaptionBarHeight(true), 80));
                        }
                        NavigationBarView navigationBarView = (NavigationBarView) this.mNavigationBarFrame.findViewByPredicate(new NavigationBarController$Impl$$ExternalSyntheticLambda1(NavigationBarView.class));
                        if (navigationBarView != null) {
                            navigationBarView.setNavbarFlags((this.mShouldShowImeSwitcherWhenImeIsShown ? 4 : 0) | 3);
                            navigationBarView.prepareNavButtons(this);
                        }
                    } else {
                        navigationBarFrame.setLayoutParams(new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                        this.mLastInsets = systemInsets;
                    }
                    if (this.mDrawLegacyNavigationBarBackground) {
                        this.mNavigationBarFrame.setBackgroundColor(-16777216);
                    } else {
                        this.mNavigationBarFrame.setBackground(null);
                    }
                    setIconTintInternal(calculateTargetDarkIntensity(this.mAppearance, this.mDrawLegacyNavigationBarBackground));
                    this.mNavigationBarFrame.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnApplyWindowInsetsListener
                        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                            WindowInsets lambda$installNavigationBarFrameIfNecessary$0;
                            lambda$installNavigationBarFrameIfNecessary$0 = NavigationBarController.Impl.this.lambda$installNavigationBarFrameIfNecessary$0(view, windowInsets);
                            return lambda$installNavigationBarFrameIfNecessary$0;
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ WindowInsets lambda$installNavigationBarFrameIfNecessary$0(View view, WindowInsets windowInsets) {
            if (this.mNavigationBarFrame != null) {
                boolean isVisible = windowInsets.isVisible(WindowInsets.Type.captionBar());
                this.mNavigationBarFrame.setVisibility(isVisible ? 0 : 8);
                checkCustomImeSwitcherButtonRequestedVisible(this.mShouldShowImeSwitcherWhenImeIsShown, this.mImeDrawsImeNavBar, !isVisible);
            }
            return view.onApplyWindowInsets(windowInsets);
        }

        private void uninstallNavigationBarFrameIfNecessary() {
            NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            if (navigationBarFrame == null) {
                return;
            }
            ViewParent parent = navigationBarFrame.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mNavigationBarFrame);
            }
            this.mNavigationBarFrame.setOnApplyWindowInsetsListener(null);
            this.mNavigationBarFrame = null;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void updateInsets(InputMethodService.Insets insets) {
            NavigationBarFrame navigationBarFrame;
            if (!this.mImeDrawsImeNavBar || (navigationBarFrame = this.mNavigationBarFrame) == null || navigationBarFrame.getVisibility() != 0 || this.mService.isFullscreenMode()) {
                return;
            }
            int[] iArr = new int[2];
            this.mNavigationBarFrame.getLocationInWindow(iArr);
            int i = insets.contentTopInsets;
            int i2 = iArr[1];
            if (i > i2) {
                insets.contentTopInsets = i2;
            }
            int i3 = insets.visibleTopInsets;
            int i4 = iArr[1];
            if (i3 > i4) {
                insets.visibleTopInsets = i4;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x00eb  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x00f3  */
        @Override // android.inputmethodservice.NavigationBarController.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void updateTouchableInsets(android.inputmethodservice.InputMethodService.Insets r12, android.view.ViewTreeObserver.InternalInsetsInfo r13) {
            /*
                Method dump skipped, instructions count: 297
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.NavigationBarController.Impl.updateTouchableInsets(android.inputmethodservice.InputMethodService$Insets, android.view.ViewTreeObserver$InternalInsetsInfo):void");
        }

        private void scheduleRelayout() {
            final NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            navigationBarFrame.post(new Runnable() { // from class: android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBarController.Impl.this.lambda$scheduleRelayout$1(navigationBarFrame);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleRelayout$1(NavigationBarFrame navigationBarFrame) {
            Window window;
            View peekDecorView;
            if (this.mDestroyed || !navigationBarFrame.isAttachedToWindow() || (window = this.mService.mWindow.getWindow()) == null || (peekDecorView = window.peekDecorView()) == null || !(peekDecorView instanceof ViewGroup)) {
                return;
            }
            ViewGroup viewGroup = (ViewGroup) peekDecorView;
            Insets systemInsets = getSystemInsets();
            if (!Objects.equals(systemInsets, this.mLastInsets)) {
                navigationBarFrame.setLayoutParams(new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                this.mLastInsets = systemInsets;
            }
            View navigationBarBackgroundView = window.getNavigationBarBackgroundView();
            if (navigationBarBackgroundView == null || viewGroup.indexOfChild(navigationBarBackgroundView) <= viewGroup.indexOfChild(navigationBarFrame)) {
                return;
            }
            viewGroup.bringChildToFront(navigationBarFrame);
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onSoftInputWindowCreated(SoftInputWindow softInputWindow) {
            Window window = softInputWindow.getWindow();
            this.mAppearance = window.getSystemBarAppearance();
            window.setDecorCallback(this);
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onViewInitialized() {
            if (this.mDestroyed) {
                return;
            }
            installNavigationBarFrameIfNecessary();
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onDestroy() {
            if (this.mDestroyed) {
                return;
            }
            ValueAnimator valueAnimator = this.mTintAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.mTintAnimator = null;
            }
            this.mDestroyed = true;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onWindowShown() {
            Insets systemInsets;
            if (this.mDestroyed || !this.mImeDrawsImeNavBar || this.mNavigationBarFrame == null || (systemInsets = getSystemInsets()) == null) {
                return;
            }
            if (!Objects.equals(systemInsets, this.mLastInsets)) {
                this.mNavigationBarFrame.setLayoutParams(new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                this.mLastInsets = systemInsets;
            }
            Window window = this.mService.mWindow.getWindow();
            View decorView = window.getDecorView();
            if (decorView instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) decorView;
                View navigationBarBackgroundView = window.getNavigationBarBackgroundView();
                if (navigationBarBackgroundView == null || viewGroup.indexOfChild(navigationBarBackgroundView) <= viewGroup.indexOfChild(this.mNavigationBarFrame)) {
                    return;
                }
                viewGroup.bringChildToFront(this.mNavigationBarFrame);
            }
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onNavButtonFlagsChanged(int i) {
            NavigationBarView navigationBarView;
            if (this.mDestroyed) {
                return;
            }
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 2) != 0;
            this.mImeDrawsImeNavBar = z;
            boolean z3 = this.mShouldShowImeSwitcherWhenImeIsShown;
            this.mShouldShowImeSwitcherWhenImeIsShown = z2;
            this.mService.mWindow.getWindow().getDecorView().getWindowInsetsController().setImeCaptionBarInsetsHeight(getImeCaptionBarHeight(z));
            if (z) {
                installNavigationBarFrameIfNecessary();
                NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                if (navigationBarFrame != null && this.mShouldShowImeSwitcherWhenImeIsShown != z3 && (navigationBarView = (NavigationBarView) navigationBarFrame.findViewByPredicate(new NavigationBarController$Impl$$ExternalSyntheticLambda1(NavigationBarView.class))) != null) {
                    navigationBarView.setNavbarFlags((this.mShouldShowImeSwitcherWhenImeIsShown ? 4 : 0) | 3);
                }
            } else {
                uninstallNavigationBarFrameIfNecessary();
            }
            checkCustomImeSwitcherButtonRequestedVisible(z2, z, !isShown());
        }

        @Override // android.view.Window.DecorCallback
        public void onSystemBarAppearanceChanged(int i) {
            if (this.mDestroyed) {
                return;
            }
            this.mAppearance = i;
            if (this.mNavigationBarFrame == null) {
                return;
            }
            float calculateTargetDarkIntensity = calculateTargetDarkIntensity(i, this.mDrawLegacyNavigationBarBackground);
            ValueAnimator valueAnimator = this.mTintAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mDarkIntensity, calculateTargetDarkIntensity);
            this.mTintAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    NavigationBarController.Impl.this.lambda$onSystemBarAppearanceChanged$2(valueAnimator2);
                }
            });
            this.mTintAnimator.setDuration(1700L);
            this.mTintAnimator.setStartDelay(0L);
            this.mTintAnimator.setInterpolator(LEGACY_DECELERATE);
            this.mTintAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemBarAppearanceChanged$2(ValueAnimator valueAnimator) {
            setIconTintInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        private void setIconTintInternal(float f) {
            NavigationBarView navigationBarView;
            this.mDarkIntensity = f;
            NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            if (navigationBarFrame == null || (navigationBarView = (NavigationBarView) navigationBarFrame.findViewByPredicate(new NavigationBarController$Impl$$ExternalSyntheticLambda1(NavigationBarView.class))) == null) {
                return;
            }
            navigationBarView.setDarkIntensity(f);
        }

        @Override // android.view.Window.DecorCallback
        public boolean onDrawLegacyNavigationBarBackgroundChanged(boolean z) {
            if (this.mDestroyed) {
                return false;
            }
            if (z != this.mDrawLegacyNavigationBarBackground) {
                this.mDrawLegacyNavigationBarBackground = z;
                NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                if (navigationBarFrame != null) {
                    if (z) {
                        navigationBarFrame.setBackgroundColor(-16777216);
                    } else {
                        navigationBarFrame.setBackground(null);
                    }
                    scheduleRelayout();
                }
                onSystemBarAppearanceChanged(this.mAppearance);
            }
            return z;
        }

        @Override // android.inputmethodservice.navigationbar.NavigationBarView.ButtonClickListener
        public void onImeSwitchButtonClick(View view) {
            this.mService.onImeSwitchButtonClickFromClient();
        }

        @Override // android.inputmethodservice.navigationbar.NavigationBarView.ButtonClickListener
        public boolean onImeSwitchButtonLongClick(View view) {
            ((InputMethodManager) view.getContext().getSystemService(InputMethodManager.class)).showInputMethodPicker();
            return true;
        }

        private int getImeCaptionBarHeight(boolean z) {
            if (z) {
                return this.mService.getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
            }
            return 0;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public boolean isShown() {
            NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            return navigationBarFrame != null && navigationBarFrame.getVisibility() == 0;
        }

        private void checkCustomImeSwitcherButtonRequestedVisible(boolean z, boolean z2, boolean z3) {
            if (Flags.imeSwitcherRevampApi()) {
                if (!z2) {
                    z3 = this.mService.getResources().getBoolean(R.bool.config_hideNavBarForKeyboard);
                }
                boolean z4 = z && z3;
                if (z4 != this.mCustomImeSwitcherButtonRequestedVisible) {
                    this.mCustomImeSwitcherButtonRequestedVisible = z4;
                    this.mService.onCustomImeSwitcherButtonRequestedVisible(z4);
                }
            }
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public String toDebugString() {
            return "{mImeDrawsImeNavBar=" + this.mImeDrawsImeNavBar + " mNavigationBarFrame=" + this.mNavigationBarFrame + " mShouldShowImeSwitcherWhenImeIsShown=" + this.mShouldShowImeSwitcherWhenImeIsShown + " mCustomImeSwitcherButtonRequestedVisible=" + this.mCustomImeSwitcherButtonRequestedVisible + " mAppearance=0x" + Integer.toHexString(this.mAppearance) + " mDarkIntensity=" + this.mDarkIntensity + " mDrawLegacyNavigationBarBackground=" + this.mDrawLegacyNavigationBarBackground + "}";
        }
    }
}
