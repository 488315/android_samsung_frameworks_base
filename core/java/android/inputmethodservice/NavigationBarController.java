package android.inputmethodservice;

import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
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
import android.widget.FrameLayout;

import com.android.internal.R;

import java.util.Objects;

/* loaded from: classes2.dex */
final class NavigationBarController {
    private final Callback mImpl;

    private interface Callback {
        public static final Callback NOOP = new Callback() { // from class:
                    // android.inputmethodservice.NavigationBarController.Callback.1
                };

        default void updateInsets(InputMethodService.Insets originalInsets) {}

        default void updateTouchableInsets(
                InputMethodService.Insets originalInsets,
                ViewTreeObserver.InternalInsetsInfo dest) {}

        default void onSoftInputWindowCreated(SoftInputWindow softInputWindow) {}

        default void onViewInitialized() {}

        default void onWindowShown() {}

        default void onDestroy() {}

        default void onNavButtonFlagsChanged(int navButtonFlags) {}

        default boolean isShown() {
            return false;
        }

        default String toDebugString() {
            return "No-op implementation";
        }
    }

    NavigationBarController(InputMethodService inputMethodService) {
        this.mImpl =
                InputMethodService.canImeRenderGesturalNavButtons()
                        ? new Impl(inputMethodService)
                        : Callback.NOOP;
    }

    void updateInsets(InputMethodService.Insets originalInsets) {
        this.mImpl.updateInsets(originalInsets);
    }

    void updateTouchableInsets(
            InputMethodService.Insets originalInsets, ViewTreeObserver.InternalInsetsInfo dest) {
        this.mImpl.updateTouchableInsets(originalInsets, dest);
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

    void onNavButtonFlagsChanged(int navButtonFlags) {
        this.mImpl.onNavButtonFlagsChanged(navButtonFlags);
    }

    boolean isShown() {
        return this.mImpl.isShown();
    }

    String toDebugString() {
        return this.mImpl.toDebugString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Impl implements Callback, Window.DecorCallback {
        private static final int DEFAULT_COLOR_ADAPT_TRANSITION_TIME = 1700;
        private static final Interpolator LEGACY_DECELERATE =
                new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        private int mAppearance;
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

        Impl(InputMethodService inputMethodService) {
            this.mService = inputMethodService;
        }

        private Insets getSystemInsets() {
            View decorView;
            WindowInsets windowInsets;
            if (this.mService.mWindow == null
                    || (decorView = this.mService.mWindow.getWindow().getDecorView()) == null
                    || (windowInsets = decorView.getRootWindowInsets()) == null) {
                return null;
            }
            Insets stableBarInsets =
                    windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return Insets.min(
                    windowInsets.getInsets(
                            WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()),
                    stableBarInsets);
        }

        private void installNavigationBarFrameIfNecessary() {
            int i;
            if (!this.mImeDrawsImeNavBar || this.mNavigationBarFrame != null) {
                return;
            }
            View rawDecorView = this.mService.mWindow.getWindow().getDecorView();
            if (!(rawDecorView instanceof ViewGroup)) {
                return;
            }
            ViewGroup decorView = (ViewGroup) rawDecorView;
            Objects.requireNonNull(NavigationBarFrame.class);
            this.mNavigationBarFrame =
                    (NavigationBarFrame)
                            decorView.findViewByPredicate(
                                    new NavigationBarController$Impl$$ExternalSyntheticLambda1(
                                            NavigationBarFrame.class));
            Insets systemInsets = getSystemInsets();
            if (this.mNavigationBarFrame == null) {
                this.mNavigationBarFrame = new NavigationBarFrame(this.mService);
                LayoutInflater.from(this.mService)
                        .inflate(R.layout.input_method_navigation_bar, this.mNavigationBarFrame);
                if (systemInsets != null) {
                    decorView.addView(
                            this.mNavigationBarFrame,
                            new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                    this.mLastInsets = systemInsets;
                } else {
                    decorView.addView(this.mNavigationBarFrame);
                }
                NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                Objects.requireNonNull(NavigationBarView.class);
                NavigationBarView navigationBarView =
                        (NavigationBarView)
                                navigationBarFrame.findViewByPredicate(
                                        new NavigationBarController$Impl$$ExternalSyntheticLambda1(
                                                NavigationBarView.class));
                if (navigationBarView != null) {
                    if (this.mShouldShowImeSwitcherWhenImeIsShown) {
                        i = 4;
                    } else {
                        i = 0;
                    }
                    int hints = i | 1;
                    navigationBarView.setNavigationIconHints(hints);
                }
            } else {
                this.mNavigationBarFrame.setLayoutParams(
                        new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                this.mLastInsets = systemInsets;
            }
            if (this.mDrawLegacyNavigationBarBackground) {
                this.mNavigationBarFrame.setBackgroundColor(-16777216);
            } else {
                this.mNavigationBarFrame.setBackground(null);
            }
            setIconTintInternal(
                    calculateTargetDarkIntensity(
                            this.mAppearance, this.mDrawLegacyNavigationBarBackground));
            this.mNavigationBarFrame.setOnApplyWindowInsetsListener(
                    new View.OnApplyWindowInsetsListener() { // from class:
                        // android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnApplyWindowInsetsListener
                        public final WindowInsets onApplyWindowInsets(
                                View view, WindowInsets windowInsets) {
                            WindowInsets lambda$installNavigationBarFrameIfNecessary$0;
                            lambda$installNavigationBarFrameIfNecessary$0 =
                                    NavigationBarController.Impl.this
                                            .lambda$installNavigationBarFrameIfNecessary$0(
                                                    view, windowInsets);
                            return lambda$installNavigationBarFrameIfNecessary$0;
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ WindowInsets lambda$installNavigationBarFrameIfNecessary$0(
                View view, WindowInsets insets) {
            if (this.mNavigationBarFrame != null) {
                boolean visible = insets.isVisible(WindowInsets.Type.captionBar());
                this.mNavigationBarFrame.setVisibility(visible ? 0 : 8);
            }
            return view.onApplyWindowInsets(insets);
        }

        private void uninstallNavigationBarFrameIfNecessary() {
            if (this.mNavigationBarFrame == null) {
                return;
            }
            ViewParent parent = this.mNavigationBarFrame.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mNavigationBarFrame);
            }
            this.mNavigationBarFrame.setOnApplyWindowInsetsListener(null);
            this.mNavigationBarFrame = null;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void updateInsets(InputMethodService.Insets originalInsets) {
            if (!this.mImeDrawsImeNavBar
                    || this.mNavigationBarFrame == null
                    || this.mNavigationBarFrame.getVisibility() != 0
                    || this.mService.isFullscreenMode()) {
                return;
            }
            int[] loc = new int[2];
            this.mNavigationBarFrame.getLocationInWindow(loc);
            if (originalInsets.contentTopInsets > loc[1]) {
                originalInsets.contentTopInsets = loc[1];
            }
            if (originalInsets.visibleTopInsets > loc[1]) {
                originalInsets.visibleTopInsets = loc[1];
            }
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void updateTouchableInsets(
                InputMethodService.Insets originalInsets,
                ViewTreeObserver.InternalInsetsInfo dest) {
            Insets systemInsets;
            if (this.mImeDrawsImeNavBar
                    && this.mNavigationBarFrame != null
                    && (systemInsets = getSystemInsets()) != null) {
                Window window = this.mService.mWindow.getWindow();
                View decor = window.getDecorView();
                Region region = null;
                if (!this.mService.isExtractViewShown()) {
                    Region touchableRegion = null;
                    View inputFrame = this.mService.mInputFrame;
                    switch (originalInsets.touchableInsets) {
                        case 0:
                            if (inputFrame.getVisibility() == 0) {
                                inputFrame.getLocationInWindow(this.mTempPos);
                                this.mTempRect.set(
                                        this.mTempPos[0],
                                        this.mTempPos[1],
                                        this.mTempPos[0] + inputFrame.getWidth(),
                                        this.mTempPos[1] + inputFrame.getHeight());
                                touchableRegion = new Region(this.mTempRect);
                                break;
                            }
                            break;
                        case 1:
                            if (inputFrame.getVisibility() == 0) {
                                inputFrame.getLocationInWindow(this.mTempPos);
                                this.mTempRect.set(
                                        this.mTempPos[0],
                                        originalInsets.contentTopInsets,
                                        this.mTempPos[0] + inputFrame.getWidth(),
                                        this.mTempPos[1] + inputFrame.getHeight());
                                touchableRegion = new Region(this.mTempRect);
                                break;
                            }
                            break;
                        case 2:
                            if (inputFrame.getVisibility() == 0) {
                                inputFrame.getLocationInWindow(this.mTempPos);
                                this.mTempRect.set(
                                        this.mTempPos[0],
                                        originalInsets.visibleTopInsets,
                                        this.mTempPos[0] + inputFrame.getWidth(),
                                        this.mTempPos[1] + inputFrame.getHeight());
                                touchableRegion = new Region(this.mTempRect);
                                break;
                            }
                            break;
                        case 3:
                            touchableRegion = new Region();
                            touchableRegion.set(originalInsets.touchableRegion);
                            break;
                    }
                    this.mTempRect.set(
                            decor.getLeft(),
                            decor.getBottom() - systemInsets.bottom,
                            decor.getRight(),
                            decor.getBottom());
                    if (touchableRegion == null) {
                        touchableRegion = new Region(this.mTempRect);
                    } else {
                        touchableRegion.union(this.mTempRect);
                    }
                    dest.touchableRegion.set(touchableRegion);
                    dest.setTouchableInsets(3);
                }
                Region touchableRegion2 = null;
                if (decor instanceof ViewGroup) {
                    ViewGroup decorGroup = (ViewGroup) decor;
                    View navbarBackgroundView = window.getNavigationBarBackgroundView();
                    if (navbarBackgroundView != null
                            && decorGroup.indexOfChild(navbarBackgroundView)
                                    > decorGroup.indexOfChild(this.mNavigationBarFrame)) {
                        region = 1;
                    }
                    touchableRegion2 = region;
                }
                boolean insetChanged = true ^ Objects.equals(systemInsets, this.mLastInsets);
                if (touchableRegion2 != null || insetChanged) {
                    scheduleRelayout();
                }
            }
        }

        private void scheduleRelayout() {
            final NavigationBarFrame frame = this.mNavigationBarFrame;
            frame.post(
                    new Runnable() { // from class:
                        // android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NavigationBarController.Impl.this.lambda$scheduleRelayout$1(frame);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleRelayout$1(NavigationBarFrame frame) {
            Window window;
            View decor;
            if (this.mDestroyed
                    || !frame.isAttachedToWindow()
                    || (window = this.mService.mWindow.getWindow()) == null
                    || (decor = window.peekDecorView()) == null
                    || !(decor instanceof ViewGroup)) {
                return;
            }
            ViewGroup decorGroup = (ViewGroup) decor;
            Insets currentSystemInsets = getSystemInsets();
            if (!Objects.equals(currentSystemInsets, this.mLastInsets)) {
                frame.setLayoutParams(
                        new FrameLayout.LayoutParams(-1, currentSystemInsets.bottom, 80));
                this.mLastInsets = currentSystemInsets;
            }
            View navbarBackgroundView = window.getNavigationBarBackgroundView();
            if (navbarBackgroundView != null
                    && decorGroup.indexOfChild(navbarBackgroundView)
                            > decorGroup.indexOfChild(frame)) {
                decorGroup.bringChildToFront(frame);
            }
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
            if (this.mTintAnimator != null) {
                this.mTintAnimator.cancel();
                this.mTintAnimator = null;
            }
            this.mDestroyed = true;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onWindowShown() {
            Insets systemInsets;
            if (!this.mDestroyed
                    && this.mImeDrawsImeNavBar
                    && this.mNavigationBarFrame != null
                    && (systemInsets = getSystemInsets()) != null) {
                if (!Objects.equals(systemInsets, this.mLastInsets)) {
                    this.mNavigationBarFrame.setLayoutParams(
                            new FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                    this.mLastInsets = systemInsets;
                }
                Window window = this.mService.mWindow.getWindow();
                View rawDecorView = window.getDecorView();
                if (rawDecorView instanceof ViewGroup) {
                    ViewGroup decor = (ViewGroup) rawDecorView;
                    View navbarBackgroundView = window.getNavigationBarBackgroundView();
                    if (navbarBackgroundView != null
                            && decor.indexOfChild(navbarBackgroundView)
                                    > decor.indexOfChild(this.mNavigationBarFrame)) {
                        decor.bringChildToFront(this.mNavigationBarFrame);
                    }
                }
            }
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onNavButtonFlagsChanged(int navButtonFlags) {
            if (this.mDestroyed) {
                return;
            }
            boolean imeDrawsImeNavBar = (navButtonFlags & 1) != 0;
            boolean shouldShowImeSwitcherWhenImeIsShown = (navButtonFlags & 2) != 0;
            this.mImeDrawsImeNavBar = imeDrawsImeNavBar;
            boolean prevShouldShowImeSwitcherWhenImeIsShown =
                    this.mShouldShowImeSwitcherWhenImeIsShown;
            this.mShouldShowImeSwitcherWhenImeIsShown = shouldShowImeSwitcherWhenImeIsShown;
            this.mService
                    .mWindow
                    .getWindow()
                    .getDecorView()
                    .getWindowInsetsController()
                    .setImeCaptionBarInsetsHeight(getImeCaptionBarHeight());
            if (imeDrawsImeNavBar) {
                installNavigationBarFrameIfNecessary();
                if (this.mNavigationBarFrame == null
                        || this.mShouldShowImeSwitcherWhenImeIsShown
                                == prevShouldShowImeSwitcherWhenImeIsShown) {
                    return;
                }
                NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                Objects.requireNonNull(NavigationBarView.class);
                NavigationBarView navigationBarView =
                        (NavigationBarView)
                                navigationBarFrame.findViewByPredicate(
                                        new NavigationBarController$Impl$$ExternalSyntheticLambda1(
                                                NavigationBarView.class));
                if (navigationBarView == null) {
                    return;
                }
                int hints = (shouldShowImeSwitcherWhenImeIsShown ? 4 : 0) | 1;
                navigationBarView.setNavigationIconHints(hints);
                return;
            }
            uninstallNavigationBarFrameIfNecessary();
        }

        @Override // android.view.Window.DecorCallback
        public void onSystemBarAppearanceChanged(int appearance) {
            if (this.mDestroyed) {
                return;
            }
            this.mAppearance = appearance;
            if (this.mNavigationBarFrame == null) {
                return;
            }
            float targetDarkIntensity =
                    calculateTargetDarkIntensity(
                            this.mAppearance, this.mDrawLegacyNavigationBarBackground);
            if (this.mTintAnimator != null) {
                this.mTintAnimator.cancel();
            }
            this.mTintAnimator = ValueAnimator.ofFloat(this.mDarkIntensity, targetDarkIntensity);
            this.mTintAnimator.addUpdateListener(
                    new ValueAnimator.AnimatorUpdateListener() { // from class:
                        // android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            NavigationBarController.Impl.this.lambda$onSystemBarAppearanceChanged$2(
                                    valueAnimator);
                        }
                    });
            this.mTintAnimator.setDuration(1700L);
            this.mTintAnimator.setStartDelay(0L);
            this.mTintAnimator.setInterpolator(LEGACY_DECELERATE);
            this.mTintAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemBarAppearanceChanged$2(ValueAnimator animation) {
            setIconTintInternal(((Float) animation.getAnimatedValue()).floatValue());
        }

        private void setIconTintInternal(float darkIntensity) {
            this.mDarkIntensity = darkIntensity;
            if (this.mNavigationBarFrame == null) {
                return;
            }
            NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            Objects.requireNonNull(NavigationBarView.class);
            NavigationBarView navigationBarView =
                    (NavigationBarView)
                            navigationBarFrame.findViewByPredicate(
                                    new NavigationBarController$Impl$$ExternalSyntheticLambda1(
                                            NavigationBarView.class));
            if (navigationBarView == null) {
                return;
            }
            navigationBarView.setDarkIntensity(darkIntensity);
        }

        private static float calculateTargetDarkIntensity(
                int appearance, boolean drawLegacyNavigationBarBackground) {
            boolean lightNavBar =
                    (drawLegacyNavigationBarBackground || (appearance & 16) == 0) ? false : true;
            return lightNavBar ? 1.0f : 0.0f;
        }

        @Override // android.view.Window.DecorCallback
        public boolean onDrawLegacyNavigationBarBackgroundChanged(
                boolean drawLegacyNavigationBarBackground) {
            if (this.mDestroyed) {
                return false;
            }
            if (drawLegacyNavigationBarBackground != this.mDrawLegacyNavigationBarBackground) {
                this.mDrawLegacyNavigationBarBackground = drawLegacyNavigationBarBackground;
                if (this.mNavigationBarFrame != null) {
                    if (this.mDrawLegacyNavigationBarBackground) {
                        this.mNavigationBarFrame.setBackgroundColor(-16777216);
                    } else {
                        this.mNavigationBarFrame.setBackground(null);
                    }
                    scheduleRelayout();
                }
                onSystemBarAppearanceChanged(this.mAppearance);
            }
            return drawLegacyNavigationBarBackground;
        }

        private int getImeCaptionBarHeight() {
            if (this.mImeDrawsImeNavBar) {
                return this.mService
                        .getResources()
                        .getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
            }
            return 0;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public boolean isShown() {
            return this.mNavigationBarFrame != null
                    && this.mNavigationBarFrame.getVisibility() == 0;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public String toDebugString() {
            return "{mImeDrawsImeNavBar="
                    + this.mImeDrawsImeNavBar
                    + " mNavigationBarFrame="
                    + this.mNavigationBarFrame
                    + " mShouldShowImeSwitcherWhenImeIsShown="
                    + this.mShouldShowImeSwitcherWhenImeIsShown
                    + " mAppearance=0x"
                    + Integer.toHexString(this.mAppearance)
                    + " mDarkIntensity="
                    + this.mDarkIntensity
                    + " mDrawLegacyNavigationBarBackground="
                    + this.mDrawLegacyNavigationBarBackground
                    + "}";
        }
    }
}
