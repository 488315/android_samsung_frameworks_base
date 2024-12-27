package com.android.keyguard;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.util.Property;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardSecSecurityContainer;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUIWidgetCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardArrowViewController extends ViewController implements SystemUIWidgetCallback {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public int mCurrentPosition;
    public final AnonymousClass1 mDisplayLifecycleObserver;
    public final Interpolator mInterpolator;
    public boolean mIsFolderOpened;
    public boolean mIsTableArrowState;
    public boolean mIsTimerRunning;
    public final KeyguardArrowViewCallback mKeyguardArrowViewCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mLastOrientation;
    public final SystemUIImageView mLeftArrow;
    public final FrameLayout mLeftArrowContainer;
    public GestureDetector mLeftArrowGD;
    public final SystemUIImageView mRightArrow;
    public final FrameLayout mRightArrowContainer;
    public GestureDetector mRightArrowGD;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final ViewMediatorCallback mViewMediatorCallback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final KeyguardArrowView mView;
        public final ViewMediatorCallback mViewMediatorCallback;

        public Factory(KeyguardArrowView keyguardArrowView, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, ViewMediatorCallback viewMediatorCallback) {
            this.mView = keyguardArrowView;
            this.mConfigurationController = configurationController;
            this.mViewMediatorCallback = viewMediatorCallback;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }
    }

    /* renamed from: -$$Nest$mannounceForArrowAccessibility, reason: not valid java name */
    public static void m830$$Nest$mannounceForArrowAccessibility(KeyguardArrowViewController keyguardArrowViewController, boolean z) {
        boolean isPatternView = keyguardArrowViewController.isPatternView();
        ((KeyguardArrowView) keyguardArrowViewController.mView).announceForAccessibility(keyguardArrowViewController.getContext().getString(z ? isPatternView ? R.string.kg_arrow_move_pattern_area_left : R.string.kg_arrow_move_pin_area_left : isPatternView ? R.string.kg_arrow_move_pattern_area_right : R.string.kg_arrow_move_pin_area_right));
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.keyguard.KeyguardArrowViewController$1] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.keyguard.KeyguardArrowViewController$2] */
    public KeyguardArrowViewController(KeyguardArrowView keyguardArrowView, KeyguardArrowViewCallback keyguardArrowViewCallback, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, ViewMediatorCallback viewMediatorCallback) {
        super(keyguardArrowView);
        this.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        this.mCurrentPosition = 1;
        this.mIsTableArrowState = false;
        this.mIsFolderOpened = true;
        this.mDisplayLifecycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.KeyguardArrowViewController.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                keyguardArrowViewController.mIsFolderOpened = z;
                int bouncerOneHandPosition = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getBouncerOneHandPosition();
                if (keyguardArrowViewController.mCurrentPosition != bouncerOneHandPosition) {
                    keyguardArrowViewController.mCurrentPosition = bouncerOneHandPosition;
                }
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardArrowViewController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                if (z) {
                    SecurityUtils.initMainDisplaySize(keyguardArrowViewController.getContext());
                }
                int i = keyguardArrowViewController.mLastOrientation;
                boolean z2 = i == 0;
                int i2 = configuration.orientation;
                if (i != i2) {
                    keyguardArrowViewController.mLastOrientation = i2;
                    keyguardArrowViewController.updateArrowMargin();
                    if (z2) {
                        return;
                    }
                    keyguardArrowViewController.updateSecurityViewPosition(false, true);
                }
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardArrowViewController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
                SystemUIImageView systemUIImageView;
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                if (keyguardArrowViewController.mLeftArrow == null || (systemUIImageView = keyguardArrowViewController.mRightArrow) == null) {
                    return;
                }
                if (SecurityUtils.isArrowViewSupported(keyguardArrowViewController.mKeyguardUpdateMonitor.getCurrentSecurityMode())) {
                    keyguardArrowViewController.updateArrowView();
                } else {
                    keyguardArrowViewController.mLeftArrow.setVisibility(8);
                    systemUIImageView.setVisibility(8);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                if (z) {
                    KeyguardArrowViewController.this.updateArrowMargin();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                View view;
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                long lockoutAttemptDeadline = keyguardArrowViewController.mKeyguardUpdateMonitor.getLockoutAttemptDeadline();
                Log.d("KeyguardArrowViewController", "onLockModeChanged - deadline " + lockoutAttemptDeadline);
                if (lockoutAttemptDeadline <= 0) {
                    keyguardArrowViewController.mIsTimerRunning = false;
                    if (keyguardArrowViewController.checkArrowVisibility()) {
                        keyguardArrowViewController.initArrowView();
                        return;
                    }
                    return;
                }
                keyguardArrowViewController.mIsTimerRunning = true;
                keyguardArrowViewController.updateArrowVisibility(false);
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
                if (keyguardSecSecurityContainerController.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                    view = ((ViewController) keyguardSecSecurityContainerController).mView;
                    KeyguardSecSecurityContainer keyguardSecSecurityContainer = (KeyguardSecSecurityContainer) view;
                    if (keyguardSecSecurityContainer.mCurrentMode == 3) {
                        ((KeyguardSecSecurityContainer.SecViewMode) keyguardSecSecurityContainer.mViewMode).updateSecurityViewPosition(1, false);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                SystemUIImageView systemUIImageView;
                View view;
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                if (keyguardArrowViewController.mLeftArrow == null || (systemUIImageView = keyguardArrowViewController.mRightArrow) == null) {
                    return;
                }
                if (SecurityUtils.isArrowViewSupported(securityMode)) {
                    keyguardArrowViewController.updateArrowView();
                    return;
                }
                keyguardArrowViewController.mLeftArrow.setVisibility(8);
                systemUIImageView.setVisibility(8);
                KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
                if (keyguardSecSecurityContainerController.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                    view = ((ViewController) keyguardSecSecurityContainerController).mView;
                    KeyguardSecSecurityContainer keyguardSecSecurityContainer = (KeyguardSecSecurityContainer) view;
                    if (keyguardSecSecurityContainer.mCurrentMode == 3) {
                        ((KeyguardSecSecurityContainer.SecViewMode) keyguardSecSecurityContainer.mViewMode).updateSecurityViewPosition(1, false);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTableModeChanged(boolean z) {
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                if (keyguardArrowViewController.mLastOrientation == 1) {
                    return;
                }
                keyguardArrowViewController.mIsTableArrowState = z;
                if (keyguardArrowViewController.checkArrowVisibility() && !z) {
                    keyguardArrowViewController.initArrowView();
                }
                keyguardArrowViewController.updateArrowMargin();
            }
        };
        this.mKeyguardArrowViewCallback = keyguardArrowViewCallback;
        this.mConfigurationController = configurationController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mLeftArrowContainer = (FrameLayout) ((KeyguardArrowView) this.mView).findViewById(R.id.left_arrow_container);
        this.mLeftArrow = (SystemUIImageView) ((KeyguardArrowView) this.mView).findViewById(R.id.left_arrow);
        this.mRightArrowContainer = (FrameLayout) ((KeyguardArrowView) this.mView).findViewById(R.id.right_arrow_container);
        this.mRightArrow = (SystemUIImageView) ((KeyguardArrowView) this.mView).findViewById(R.id.right_arrow);
    }

    public static void hideArrow(View view) {
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(Interpolators.LINEAR);
        animatorSet.play(ofFloat);
        animatorSet.setDuration(100L);
        animatorSet.start();
        view.setVisibility(8);
    }

    public final void arrowTouchDownAnimation(View view) {
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, 1.0f, 0.8f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, 1.0f, 0.8f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setInterpolator(this.mInterpolator);
        animatorSet.setDuration(300L);
        animatorSet.start();
    }

    public final void arrowTouchUpAnimation(View view) {
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, 0.8f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, 0.8f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setInterpolator(this.mInterpolator);
        animatorSet.setDuration(300L);
        animatorSet.start();
    }

    public final boolean checkArrowVisibility() {
        if (this.mKeyguardUpdateMonitor.getLockoutAttemptDeadline() > 0 || DeviceState.isSmartViewFitToActiveDisplay() || !this.mIsFolderOpened) {
            return false;
        }
        KeyguardSecSecurityContainerController.AnonymousClass3 anonymousClass3 = (KeyguardSecSecurityContainerController.AnonymousClass3) this.mKeyguardArrowViewCallback;
        anonymousClass3.getClass();
        return (LsRune.SECURITY_SUB_DISPLAY_LOCK && KeyguardSecSecurityContainerController.this.mIsDisappearAnimation) ? false : true;
    }

    public final void initArrowView() {
        this.mCurrentPosition = checkArrowVisibility() ? ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getBouncerOneHandPosition() : 1;
        if (this.mIsTableArrowState && !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLockScreenRotationAllowed()) {
            Log.d("KeyguardArrowViewController", "Set force left security view position");
            this.mCurrentPosition = 0;
        }
        updateArrowView();
    }

    public final boolean isInvalidArrowView() {
        return !SecurityUtils.isArrowViewSupported(this.mKeyguardUpdateMonitor.getCurrentSecurityMode()) || this.mLeftArrow == null || this.mRightArrow == null;
    }

    public final boolean isPatternView() {
        return this.mKeyguardUpdateMonitor.getCurrentSecurityMode() == KeyguardSecurityModel.SecurityMode.Pattern;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            SecurityUtils.initMainDisplaySize(getContext());
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(this.mDisplayLifecycleObserver);
        WallpaperUtils.registerSystemUIWidgetCallback(this, 512L);
        this.mIsFolderOpened = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
        initArrowView();
        this.mLeftArrowGD = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.android.keyguard.KeyguardArrowViewController.4
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                KeyguardArrowViewController.this.mLeftArrow.setPressed(false);
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                keyguardArrowViewController.mCurrentPosition = 0;
                keyguardArrowViewController.mViewMediatorCallback.userActivity();
                KeyguardArrowViewController keyguardArrowViewController2 = KeyguardArrowViewController.this;
                keyguardArrowViewController2.mIsTableArrowState = false;
                keyguardArrowViewController2.updateArrowVisibility(true);
                KeyguardArrowViewController.this.updateSecurityViewPosition(true, false);
                KeyguardArrowViewController.m830$$Nest$mannounceForArrowAccessibility(KeyguardArrowViewController.this, true);
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                KeyguardArrowViewController.this.mLeftArrow.setPressed(false);
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                int i = keyguardArrowViewController.mCurrentPosition;
                if (i == 2) {
                    keyguardArrowViewController.mCurrentPosition = 1;
                } else if (i == 1) {
                    keyguardArrowViewController.mCurrentPosition = 0;
                }
                keyguardArrowViewController.mViewMediatorCallback.userActivity();
                KeyguardArrowViewController keyguardArrowViewController2 = KeyguardArrowViewController.this;
                keyguardArrowViewController2.mIsTableArrowState = false;
                keyguardArrowViewController2.updateArrowVisibility(true);
                KeyguardArrowViewController.this.updateSecurityViewPosition(true, false);
                KeyguardArrowViewController.m830$$Nest$mannounceForArrowAccessibility(KeyguardArrowViewController.this, true);
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onSingleTapUp(MotionEvent motionEvent) {
                KeyguardArrowViewController.this.mLeftArrow.setPressed(true);
                return false;
            }
        });
        final int i = 0;
        this.mLeftArrow.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.android.keyguard.KeyguardArrowViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardArrowViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i2 = i;
                KeyguardArrowViewController keyguardArrowViewController = this.f$0;
                switch (i2) {
                    case 0:
                        keyguardArrowViewController.mLeftArrowGD.onTouchEvent(motionEvent);
                        int action = motionEvent.getAction();
                        SystemUIImageView systemUIImageView = keyguardArrowViewController.mLeftArrow;
                        if (action == 0) {
                            keyguardArrowViewController.arrowTouchDownAnimation(systemUIImageView);
                            break;
                        } else if (action == 1) {
                            keyguardArrowViewController.arrowTouchUpAnimation(systemUIImageView);
                            break;
                        }
                        break;
                    default:
                        keyguardArrowViewController.mRightArrowGD.onTouchEvent(motionEvent);
                        int action2 = motionEvent.getAction();
                        SystemUIImageView systemUIImageView2 = keyguardArrowViewController.mRightArrow;
                        if (action2 == 0) {
                            keyguardArrowViewController.arrowTouchDownAnimation(systemUIImageView2);
                            break;
                        } else if (action2 == 1) {
                            keyguardArrowViewController.arrowTouchUpAnimation(systemUIImageView2);
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        this.mRightArrowGD = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.android.keyguard.KeyguardArrowViewController.5
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                KeyguardArrowViewController.this.mRightArrow.setPressed(false);
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                keyguardArrowViewController.mCurrentPosition = 2;
                keyguardArrowViewController.mViewMediatorCallback.userActivity();
                KeyguardArrowViewController keyguardArrowViewController2 = KeyguardArrowViewController.this;
                keyguardArrowViewController2.mIsTableArrowState = false;
                keyguardArrowViewController2.updateArrowVisibility(true);
                KeyguardArrowViewController.this.updateSecurityViewPosition(true, false);
                KeyguardArrowViewController.m830$$Nest$mannounceForArrowAccessibility(KeyguardArrowViewController.this, false);
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                KeyguardArrowViewController.this.mRightArrow.setPressed(false);
                KeyguardArrowViewController keyguardArrowViewController = KeyguardArrowViewController.this;
                int i2 = keyguardArrowViewController.mCurrentPosition;
                if (i2 == 0) {
                    keyguardArrowViewController.mCurrentPosition = 1;
                } else if (i2 == 1) {
                    keyguardArrowViewController.mCurrentPosition = 2;
                }
                keyguardArrowViewController.mViewMediatorCallback.userActivity();
                KeyguardArrowViewController keyguardArrowViewController2 = KeyguardArrowViewController.this;
                keyguardArrowViewController2.mIsTableArrowState = false;
                keyguardArrowViewController2.updateArrowVisibility(true);
                KeyguardArrowViewController.this.updateSecurityViewPosition(true, false);
                KeyguardArrowViewController.m830$$Nest$mannounceForArrowAccessibility(KeyguardArrowViewController.this, false);
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onSingleTapUp(MotionEvent motionEvent) {
                KeyguardArrowViewController.this.mRightArrow.setPressed(true);
                return false;
            }
        });
        final int i2 = 1;
        this.mRightArrow.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.android.keyguard.KeyguardArrowViewController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardArrowViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i22 = i2;
                KeyguardArrowViewController keyguardArrowViewController = this.f$0;
                switch (i22) {
                    case 0:
                        keyguardArrowViewController.mLeftArrowGD.onTouchEvent(motionEvent);
                        int action = motionEvent.getAction();
                        SystemUIImageView systemUIImageView = keyguardArrowViewController.mLeftArrow;
                        if (action == 0) {
                            keyguardArrowViewController.arrowTouchDownAnimation(systemUIImageView);
                            break;
                        } else if (action == 1) {
                            keyguardArrowViewController.arrowTouchUpAnimation(systemUIImageView);
                            break;
                        }
                        break;
                    default:
                        keyguardArrowViewController.mRightArrowGD.onTouchEvent(motionEvent);
                        int action2 = motionEvent.getAction();
                        SystemUIImageView systemUIImageView2 = keyguardArrowViewController.mRightArrow;
                        if (action2 == 0) {
                            keyguardArrowViewController.arrowTouchDownAnimation(systemUIImageView2);
                            break;
                        } else if (action2 == 1) {
                            keyguardArrowViewController.arrowTouchUpAnimation(systemUIImageView2);
                            break;
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).removeObserver(this.mDisplayLifecycleObserver);
        WallpaperUtils.removeSystemUIWidgetCallback(this);
    }

    public final void startArrowViewAnimation(View view) {
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, 0.7f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, 0.7f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f);
        ofFloat3.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        animatorSet.setInterpolator(this.mInterpolator);
        animatorSet.setDuration(300L);
        animatorSet.setStartDelay(0L);
        animatorSet.start();
    }

    public final void updateArrowMargin() {
        float f;
        float f2;
        View view;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        if (isInvalidArrowView()) {
            return;
        }
        Resources resources = getResources();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mLeftArrowContainer.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mRightArrowContainer.getLayoutParams();
        if (!this.mIsFolderOpened || DeviceState.isSmartViewFitToActiveDisplay()) {
            layoutParams.leftMargin = 0;
            layoutParams.bottomMargin = 0;
            layoutParams2.rightMargin = 0;
            layoutParams2.bottomMargin = 0;
        } else {
            isPatternView();
            if (DeviceType.isTablet()) {
                f = DeviceState.getDisplayWidth(getContext());
                f2 = getResources().getFloat(R.dimen.kg_arrow_view_side_margin_tablet_ratio);
            } else {
                Context context = getContext();
                int i = SecurityUtils.sPINContainerBottomMargin;
                int rotation = DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
                f = (rotation == 1 || rotation == 3) ? SecurityUtils.sMainDisplayHeight : SecurityUtils.sMainDisplayWidth;
                f2 = getResources().getFloat(R.dimen.kg_arrow_view_side_margin_ratio);
            }
            int i2 = (int) (f2 * f);
            Resources resources2 = getResources();
            boolean isTablet = DeviceType.isTablet();
            boolean isPatternView = isPatternView();
            view = KeyguardSecSecurityContainerController.this.mView;
            EmergencyButton emergencyButton = (EmergencyButton) ((KeyguardSecSecurityContainer) view).findViewById(R.id.emergency_call_button);
            boolean z = emergencyButton != null && emergencyButton.getVisibility() == 0;
            int dimensionPixelSize3 = LsRune.SECURITY_NAVBAR_ENABLED ? resources2.getDimensionPixelSize(R.dimen.navigation_bar_frame_height) : 0;
            int i3 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height;
            if (isTablet) {
                if (isPatternView) {
                    dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet) + resources2.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom_tablet);
                    dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom_tablet);
                } else {
                    dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet) + resources2.getDimensionPixelSize(R.dimen.kg_pin_container_margin_bottom_tablet);
                    dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.kg_pin_eca_margin_bottom_tablet);
                }
            } else if (isPatternView) {
                dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height) + resources2.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom);
                dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom);
            } else {
                dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height) + SecurityUtils.sPINContainerBottomMargin;
                dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.kg_pin_eca_margin_bottom);
            }
            int i4 = dimensionPixelSize2 + dimensionPixelSize + dimensionPixelSize3;
            if (!z) {
                if (isTablet) {
                    i3 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                }
                i4 -= resources2.getDimensionPixelSize(i3);
            }
            if (this.mKeyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && resources2.getConfiguration().windowConfiguration.getRotation() == 0) {
                i4 += (resources2.getDimensionPixelSize(R.dimen.kg_emergency_button_margin_bottom_for_tablet_fingerprint) + DeviceState.getInDisplayFingerprintHeight()) - dimensionPixelSize3;
            }
            Resources resources3 = getResources();
            boolean isPatternView2 = isPatternView();
            int dimensionPixelSize4 = DeviceType.isTablet() ? isPatternView2 ? resources3.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_height_tablet) : SecurityUtils.getTabletPINContainerHeight(getContext()) : isPatternView2 ? resources3.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_height) : SecurityUtils.getFoldPINContainerHeight(getContext());
            resources.getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
            layoutParams.height = dimensionPixelSize4;
            layoutParams2.height = dimensionPixelSize4;
            layoutParams.leftMargin = i2;
            layoutParams2.rightMargin = i2;
            layoutParams.bottomMargin = i4;
            layoutParams2.bottomMargin = i4;
        }
        this.mLeftArrowContainer.setLayoutParams(layoutParams);
        this.mRightArrowContainer.setLayoutParams(layoutParams2);
    }

    public final void updateArrowStyle(boolean z) {
        if (isInvalidArrowView()) {
            return;
        }
        this.mLeftArrow.setImageResource(z ? R.drawable.lock_bouncer_whitebg_btn_left_mtrl : R.drawable.lock_bouncer_btn_left_mtrl);
        this.mRightArrow.setImageResource(z ? R.drawable.lock_bouncer_whitebg_btn_right_mtrl : R.drawable.lock_bouncer_btn_right_mtrl);
    }

    public final void updateArrowView() {
        if (isInvalidArrowView()) {
            return;
        }
        updateArrowStyle(WallpaperUtils.isWhiteKeyguardWallpaper("background"));
        updateArrowVisibility(true);
        updateArrowMargin();
        updateSecurityViewPosition(false, false);
    }

    public final void updateArrowVisibility(boolean z) {
        if (isInvalidArrowView()) {
            return;
        }
        boolean checkArrowVisibility = checkArrowVisibility();
        SystemUIImageView systemUIImageView = this.mRightArrow;
        SystemUIImageView systemUIImageView2 = this.mLeftArrow;
        if (!checkArrowVisibility || !z) {
            systemUIImageView2.setVisibility(8);
            systemUIImageView.setVisibility(8);
            return;
        }
        int i = this.mCurrentPosition;
        if (i != 0 && i != 2) {
            if (systemUIImageView2 != null) {
                systemUIImageView2.setAlpha(1.0f);
                systemUIImageView2.setVisibility(0);
            }
            if (systemUIImageView != null) {
                systemUIImageView.setAlpha(1.0f);
                systemUIImageView.setVisibility(0);
                return;
            }
            return;
        }
        if (i == 0) {
            hideArrow(systemUIImageView2);
        } else if (systemUIImageView2 != null) {
            systemUIImageView2.setAlpha(1.0f);
            systemUIImageView2.setVisibility(0);
        }
        if (this.mCurrentPosition == 2) {
            hideArrow(systemUIImageView);
        } else if (systemUIImageView != null) {
            systemUIImageView.setAlpha(1.0f);
            systemUIImageView.setVisibility(0);
        }
    }

    public final void updateSecurityViewPosition(boolean z, boolean z2) {
        View view;
        View view2;
        boolean z3 = this.mIsTimerRunning;
        KeyguardArrowViewCallback keyguardArrowViewCallback = this.mKeyguardArrowViewCallback;
        if (z3 || !checkArrowVisibility() || !SecurityUtils.isArrowViewSupported(this.mKeyguardUpdateMonitor.getCurrentSecurityMode())) {
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = KeyguardSecSecurityContainerController.this;
            if (keyguardSecSecurityContainerController.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                view = keyguardSecSecurityContainerController.mView;
                KeyguardSecSecurityContainer keyguardSecSecurityContainer = (KeyguardSecSecurityContainer) view;
                if (keyguardSecSecurityContainer.mCurrentMode == 3) {
                    ((KeyguardSecSecurityContainer.SecViewMode) keyguardSecSecurityContainer.mViewMode).updateSecurityViewPosition(1, false);
                    return;
                }
                return;
            }
            return;
        }
        int i = this.mCurrentPosition;
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = KeyguardSecSecurityContainerController.this;
        if (keyguardSecSecurityContainerController2.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            view2 = keyguardSecSecurityContainerController2.mView;
            KeyguardSecSecurityContainer keyguardSecSecurityContainer2 = (KeyguardSecSecurityContainer) view2;
            if (keyguardSecSecurityContainer2.mCurrentMode == 3) {
                ((KeyguardSecSecurityContainer.SecViewMode) keyguardSecSecurityContainer2.mViewMode).updateSecurityViewPosition(i, z);
            }
        }
        if (!this.mIsTableArrowState || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLockScreenRotationAllowed()) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setBouncerOneHandPosition(this.mCurrentPosition);
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        updateArrowStyle(WallpaperUtils.isWhiteKeyguardWallpaper("background"));
    }
}
