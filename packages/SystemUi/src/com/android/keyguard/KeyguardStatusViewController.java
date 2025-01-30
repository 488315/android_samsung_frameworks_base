package com.android.keyguard;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Trace;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.slice.Slice;
import androidx.slice.SliceConvert;
import androidx.slice.SliceViewManagerWrapper;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import dagger.Lazy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardStatusViewController extends ViewController {
    public static final AnimationProperties CLOCK_ANIMATION_PROPERTIES;
    public final Rect mClipBounds;
    public final ConfigurationController mConfigurationController;
    public final C07852 mConfigurationListener;
    public KeyguardUpdateMonitorCallback mInfoCallback;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public Supplier mIsDLSViewEnabledSupplier;
    public final KeyguardClockSwitchController mKeyguardClockSwitchController;
    public final KeyguardSliceViewController mKeyguardSliceViewController;
    public final C07841 mKeyguardStatusAlignmentTransitionListener;
    public FaceWidgetContainerWrapper mKeyguardStatusBase;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    public final DcmMascotViewContainer mMascotViewContainer;
    public Lazy mPluginAODManagerLazy;
    public Boolean mStatusViewCentered;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class SplitShadeTransitionAdapter extends Transition {
        public static final String[] TRANSITION_PROPERTIES = {"splitShadeTransitionAdapter:boundsLeft", "splitShadeTransitionAdapter:boundsRight", "splitShadeTransitionAdapter:xInWindow"};
        public final KeyguardClockSwitchController mController;

        public SplitShadeTransitionAdapter(KeyguardClockSwitchController keyguardClockSwitchController) {
            this.mController = keyguardClockSwitchController;
        }

        public static void captureValues(TransitionValues transitionValues) {
            transitionValues.values.put("splitShadeTransitionAdapter:boundsLeft", Integer.valueOf(transitionValues.view.getLeft()));
            transitionValues.values.put("splitShadeTransitionAdapter:boundsRight", Integer.valueOf(transitionValues.view.getRight()));
            int[] iArr = new int[2];
            transitionValues.view.getLocationInWindow(iArr);
            transitionValues.values.put("splitShadeTransitionAdapter:xInWindow", Integer.valueOf(iArr[0]));
        }

        @Override // android.transition.Transition
        public final void captureEndValues(TransitionValues transitionValues) {
            captureValues(transitionValues);
        }

        @Override // android.transition.Transition
        public final void captureStartValues(TransitionValues transitionValues) {
            captureValues(transitionValues);
        }

        @Override // android.transition.Transition
        public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            if (transitionValues == null || transitionValues2 == null) {
                return null;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            final int intValue = ((Integer) transitionValues.values.get("splitShadeTransitionAdapter:boundsLeft")).intValue();
            final int i = ((Integer) transitionValues2.values.get("splitShadeTransitionAdapter:xInWindow")).intValue() - ((Integer) transitionValues.values.get("splitShadeTransitionAdapter:xInWindow")).intValue() > 0 ? 1 : -1;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardStatusViewController$SplitShadeTransitionAdapter$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardStatusViewController.SplitShadeTransitionAdapter splitShadeTransitionAdapter = KeyguardStatusViewController.SplitShadeTransitionAdapter.this;
                    int i2 = intValue;
                    int i3 = i;
                    ClockController clockController = splitShadeTransitionAdapter.mController.mClockEventController.clock;
                    if (clockController == null) {
                        return;
                    }
                    clockController.getLargeClock().getAnimations().onPositionUpdated(i2, i3, valueAnimator.getAnimatedFraction());
                }
            });
            return ofFloat;
        }

        @Override // android.transition.Transition
        public final String[] getTransitionProperties() {
            return TRANSITION_PROPERTIES;
        }
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360L;
        CLOCK_ANIMATION_PROPERTIES = animationProperties;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardStatusViewController$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.KeyguardStatusViewController$2] */
    public KeyguardStatusViewController(DcmMascotViewContainer dcmMascotViewContainer, KeyguardStatusView keyguardStatusView, KeyguardSliceViewController keyguardSliceViewController, KeyguardClockSwitchController keyguardClockSwitchController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, KeyguardLogger keyguardLogger, FeatureFlags featureFlags, InteractionJankMonitor interactionJankMonitor) {
        super(keyguardStatusView);
        this.mClipBounds = new Rect();
        this.mStatusViewCentered = Boolean.TRUE;
        this.mKeyguardStatusAlignmentTransitionListener = new TransitionListenerAdapter() { // from class: com.android.keyguard.KeyguardStatusViewController.1
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
                KeyguardStatusViewController.this.mInteractionJankMonitor.cancel(70);
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                KeyguardStatusViewController.this.mInteractionJankMonitor.end(70);
            }
        };
        this.mIsDLSViewEnabledSupplier = null;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardStatusViewController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardClockSwitchController keyguardClockSwitchController2 = KeyguardStatusViewController.this.mKeyguardClockSwitchController;
                ((KeyguardClockSwitch) keyguardClockSwitchController2.mView).onConfigChanged();
                ((KeyguardClockSwitch) keyguardClockSwitchController2.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
                ((KeyguardClockSwitch) keyguardClockSwitchController2.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_large_clock_top_margin);
                keyguardClockSwitchController2.mKeyguardDateWeatherViewInvisibility = ((KeyguardClockSwitch) keyguardClockSwitchController2.mView).getResources().getInteger(R.integer.keyguard_date_weather_view_invisibility);
                ((KeyguardClockSwitch) keyguardClockSwitchController2.mView).updateClockTargetRegions();
                keyguardClockSwitchController2.setDateWeatherVisibility();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                PluginKeyguardStatusView pluginKeyguardStatusView;
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = KeyguardStatusViewController.this.mKeyguardStatusBase;
                if (faceWidgetContainerWrapper == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) == null) {
                    return;
                }
                pluginKeyguardStatusView.onDensityOrFontScaleChanged();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLocaleListChanged() {
                AnimationProperties animationProperties = KeyguardStatusViewController.CLOCK_ANIMATION_PROPERTIES;
                KeyguardStatusViewController keyguardStatusViewController = KeyguardStatusViewController.this;
                keyguardStatusViewController.mKeyguardClockSwitchController.refresh();
                KeyguardClockSwitchController keyguardClockSwitchController2 = keyguardStatusViewController.mKeyguardClockSwitchController;
                LockscreenSmartspaceController lockscreenSmartspaceController = keyguardClockSwitchController2.mSmartspaceController;
                if (lockscreenSmartspaceController.isEnabled()) {
                    if (lockscreenSmartspaceController.isDateWeatherDecoupled()) {
                        keyguardClockSwitchController2.mDateWeatherView.removeView(keyguardClockSwitchController2.mWeatherView);
                        int indexOfChild = keyguardClockSwitchController2.mStatusArea.indexOfChild(keyguardClockSwitchController2.mDateWeatherView);
                        if (indexOfChild >= 0) {
                            keyguardClockSwitchController2.mStatusArea.removeView(keyguardClockSwitchController2.mDateWeatherView);
                            keyguardClockSwitchController2.addDateWeatherView(indexOfChild);
                        }
                        keyguardClockSwitchController2.setDateWeatherVisibility();
                        keyguardClockSwitchController2.setWeatherVisibility();
                    }
                    int indexOfChild2 = keyguardClockSwitchController2.mStatusArea.indexOfChild(keyguardClockSwitchController2.mSmartspaceView);
                    if (indexOfChild2 >= 0) {
                        keyguardClockSwitchController2.mStatusArea.removeView(keyguardClockSwitchController2.mSmartspaceView);
                        keyguardClockSwitchController2.addSmartspaceView(indexOfChild2);
                    }
                }
            }
        };
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardStatusViewController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                if (z) {
                    AnimationProperties animationProperties = KeyguardStatusViewController.CLOCK_ANIMATION_PROPERTIES;
                    KeyguardStatusViewController.this.mKeyguardClockSwitchController.refresh();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTimeChanged() {
                AnimationProperties animationProperties = KeyguardStatusViewController.CLOCK_ANIMATION_PROPERTIES;
                KeyguardStatusViewController.this.mKeyguardClockSwitchController.refresh();
            }
        };
        this.mKeyguardSliceViewController = keyguardSliceViewController;
        this.mKeyguardClockSwitchController = keyguardClockSwitchController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        this.mKeyguardVisibilityHelper = new KeyguardVisibilityHelper(this.mView, keyguardStateController, dozeParameters, screenOffAnimationController, true, keyguardLogger.buffer);
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mMascotViewContainer = dcmMascotViewContainer;
    }

    public final void dozeTimeTick() {
        this.mKeyguardClockSwitchController.refresh();
        KeyguardSliceViewController keyguardSliceViewController = this.mKeyguardSliceViewController;
        keyguardSliceViewController.getClass();
        Trace.beginSection("KeyguardSliceViewController#refresh");
        Slice slice = null;
        if ("content://com.android.systemui.keyguard/main".equals(keyguardSliceViewController.mKeyguardSliceUri.toString())) {
            KeyguardSliceProvider keyguardSliceProvider = KeyguardSliceProvider.sInstance;
            if (keyguardSliceProvider != null) {
                if (!keyguardSliceProvider.getPinnedSlices().contains(keyguardSliceViewController.mKeyguardSliceUri)) {
                    new SliceViewManagerWrapper(keyguardSliceViewController.getContext()).pinSlice(keyguardSliceViewController.mKeyguardSliceUri);
                }
                slice = keyguardSliceProvider.onBindSlice();
            } else {
                Log.w("KeyguardSliceViewCtrl", "Keyguard slice not bound yet?");
            }
        } else {
            SliceViewManagerWrapper sliceViewManagerWrapper = new SliceViewManagerWrapper(((KeyguardSliceView) keyguardSliceViewController.mView).getContext());
            Uri uri = keyguardSliceViewController.mKeyguardSliceUri;
            if (!sliceViewManagerWrapper.isAuthoritySuspended(uri.getAuthority())) {
                slice = SliceConvert.wrap(sliceViewManagerWrapper.mManager.bindSlice(uri, sliceViewManagerWrapper.mSpecs), sliceViewManagerWrapper.mContext);
            }
        }
        keyguardSliceViewController.mObserver.onChanged(slice);
        Trace.endSection();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mKeyguardClockSwitchController.init();
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        View view;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        if (faceWidgetContainerWrapper == null || (view = faceWidgetContainerWrapper.mFaceWidgetContainer) == null || !(view instanceof ViewGroup)) {
            return false;
        }
        return ((ViewGroup) view).onInterceptTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper$$ExternalSyntheticLambda0] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setKeyguardStatusViewVisibility(int i, int i2, boolean z, boolean z2) {
        int i3;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        if (faceWidgetContainerWrapper == null) {
            return;
        }
        View view = faceWidgetContainerWrapper.mFaceWidgetContainer;
        if ((view != null ? view.animate() : null) == null) {
            return;
        }
        FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = this.mKeyguardStatusBase;
        View view2 = faceWidgetContainerWrapper2.mFaceWidgetContainer;
        if (view2 == null) {
            view2 = new View(faceWidgetContainerWrapper2.mContext);
        }
        view2.setTranslationY(0.0f);
        final FaceWidgetContainerWrapper faceWidgetContainerWrapper3 = this.mKeyguardStatusBase;
        faceWidgetContainerWrapper3.getClass();
        final ?? r5 = new IntConsumer() { // from class: com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i4) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper4 = FaceWidgetContainerWrapper.this;
                faceWidgetContainerWrapper4.getClass();
                try {
                    PluginKeyguardStatusView pluginKeyguardStatusView = faceWidgetContainerWrapper4.mPluginKeyguardStatusView;
                    if (pluginKeyguardStatusView != null) {
                        pluginKeyguardStatusView.onKeyguardVisibilityHelperChanged(i4);
                    }
                } catch (UndeclaredThrowableException e) {
                    e.printStackTrace();
                }
            }
        };
        FaceWidgetContainerWrapper faceWidgetContainerWrapper4 = this.mKeyguardStatusBase;
        final View view3 = faceWidgetContainerWrapper4.mFaceWidgetContainer;
        if (view3 == null) {
            view3 = new View(faceWidgetContainerWrapper4.mContext);
        }
        Supplier supplier = this.mIsDLSViewEnabledSupplier;
        final int i4 = 1;
        final int i5 = 0;
        boolean z3 = supplier != null && ((Boolean) supplier.get()).booleanValue();
        final KeyguardVisibilityHelper keyguardVisibilityHelper = this.mKeyguardVisibilityHelper;
        DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
        keyguardVisibilityHelper.mMascotViewContainer = dcmMascotViewContainer;
        view3.animate().cancel();
        KeyguardStateController keyguardStateController = keyguardVisibilityHelper.mKeyguardStateController;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        boolean z4 = keyguardStateControllerImpl.mOccluded;
        keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
        if ((!z && i2 == 1 && i != 1) || z2) {
            keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = true;
            view3.animate().alpha(0.0f).setStartDelay(0L).setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i5) {
                        case 0:
                            KeyguardVisibilityHelper keyguardVisibilityHelper2 = keyguardVisibilityHelper;
                            View view4 = view3;
                            IntConsumer intConsumer = r5;
                            keyguardVisibilityHelper2.mKeyguardViewVisibilityAnimating = false;
                            view4.setVisibility(8);
                            if (intConsumer != null) {
                                intConsumer.accept(8);
                                break;
                            }
                            break;
                        default:
                            KeyguardVisibilityHelper keyguardVisibilityHelper3 = keyguardVisibilityHelper;
                            View view5 = view3;
                            IntConsumer intConsumer2 = r5;
                            keyguardVisibilityHelper3.mKeyguardViewVisibilityAnimating = false;
                            view5.setVisibility(4);
                            view5.setTranslationY(0.0f);
                            if (intConsumer2 != null) {
                                intConsumer2.accept(4);
                                break;
                            }
                            break;
                    }
                }
            });
            if (z) {
                ViewPropertyAnimator startDelay = view3.animate().setStartDelay(keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
                keyguardStateController.getClass();
                startDelay.setDuration(((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDuration / 2).start();
            }
        } else {
            if (i2 != 2 || i != 1) {
                if (i != 1) {
                    i3 = 8;
                    view3.setVisibility(8);
                    view3.setAlpha(1.0f);
                    r5.accept(8);
                } else if (z) {
                    keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = true;
                    ViewPropertyAnimator withEndAction = view3.animate().alpha(0.0f).translationYBy((-view3.getHeight()) * 0.05f).setInterpolator(Interpolators.FAST_OUT_LINEAR_IN).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i4) {
                                case 0:
                                    KeyguardVisibilityHelper keyguardVisibilityHelper2 = keyguardVisibilityHelper;
                                    View view4 = view3;
                                    IntConsumer intConsumer = r5;
                                    keyguardVisibilityHelper2.mKeyguardViewVisibilityAnimating = false;
                                    view4.setVisibility(8);
                                    if (intConsumer != null) {
                                        intConsumer.accept(8);
                                        break;
                                    }
                                    break;
                                default:
                                    KeyguardVisibilityHelper keyguardVisibilityHelper3 = keyguardVisibilityHelper;
                                    View view5 = view3;
                                    IntConsumer intConsumer2 = r5;
                                    keyguardVisibilityHelper3.mKeyguardViewVisibilityAnimating = false;
                                    view5.setVisibility(4);
                                    view5.setTranslationY(0.0f);
                                    if (intConsumer2 != null) {
                                        intConsumer2.accept(4);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
                    if (keyguardVisibilityHelper.mAnimateYPos) {
                        float y = view3.getY() - (view3.getHeight() * 0.05f);
                        AnimationProperties animationProperties = keyguardVisibilityHelper.mAnimationProperties;
                        long j = 125;
                        animationProperties.duration = j;
                        long j2 = 0;
                        animationProperties.delay = j2;
                        AnimatableProperty.C27067 c27067 = AnimatableProperty.f351Y;
                        PropertyAnimator.cancelAnimation(view3, c27067);
                        PropertyAnimator.setProperty(view3, c27067, y, animationProperties, true);
                        withEndAction.setDuration(j).setStartDelay(j2);
                    }
                    withEndAction.start();
                } else if (!keyguardVisibilityHelper.mLastOccludedState || z4) {
                    ScreenOffAnimationController screenOffAnimationController = keyguardVisibilityHelper.mScreenOffAnimationController;
                    if (screenOffAnimationController.shouldAnimateInKeyguard()) {
                        keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = true;
                        screenOffAnimationController.animateInKeyguard$1(keyguardVisibilityHelper.mView, keyguardVisibilityHelper.mSetVisibleEndRunnable);
                    } else {
                        view3.setVisibility(0);
                        view3.setAlpha(1.0f);
                        r5.accept(0);
                    }
                } else if (z3) {
                    i3 = 8;
                    view3.setVisibility(8);
                    r5.accept(8);
                } else {
                    view3.setVisibility(0);
                    r5.accept(0);
                }
                keyguardVisibilityHelper.mLastOccludedState = z4;
                if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                    return;
                }
                if (z || i == 0) {
                    dcmMascotViewContainer.setMascotViewVisible(4);
                    return;
                } else {
                    View view4 = this.mKeyguardStatusBase.mFaceWidgetContainer;
                    dcmMascotViewContainer.setMascotViewVisible(view4 != null ? view4.getVisibility() : i3);
                    return;
                }
            }
            view3.setVisibility(0);
            r5.accept(0);
            keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = true;
            view3.setAlpha(0.0f);
            view3.animate().alpha(1.0f).setStartDelay(0L).setDuration(320L).setInterpolator(Interpolators.ALPHA_IN).withEndAction(new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(keyguardVisibilityHelper, 3));
        }
        i3 = 8;
        keyguardVisibilityHelper.mLastOccludedState = z4;
        if (LsRune.KEYGUARD_DCM_LIVE_UX) {
        }
    }

    public void setProperty(AnimatableProperty animatableProperty, float f, boolean z) {
        PropertyAnimator.setProperty((KeyguardStatusView) this.mView, animatableProperty, f, CLOCK_ANIMATION_PROPERTIES, z);
    }

    public final void setSplitShadeEnabled(boolean z) {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.mKeyguardClockSwitchController.mSmartspaceController;
        lockscreenSmartspaceController.mSplitShadeEnabled = z;
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setSplitShadeEnabled(z);
        }
    }

    public final void updateAlignment(NotificationsQuickSettingsContainer notificationsQuickSettingsContainer, boolean z, boolean z2, boolean z3) {
        boolean z4 = z && z2;
        KeyguardClockSwitchController keyguardClockSwitchController = this.mKeyguardClockSwitchController;
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) keyguardClockSwitchController.mView;
        if (keyguardClockSwitch.mSplitShadeCentered != z4) {
            keyguardClockSwitch.mSplitShadeCentered = z4;
            keyguardClockSwitch.updateStatusArea(true);
        }
        if (this.mStatusViewCentered.booleanValue() == z2) {
            return;
        }
        this.mStatusViewCentered = Boolean.valueOf(z2);
        if (notificationsQuickSettingsContainer == null) {
            return;
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(notificationsQuickSettingsContainer);
        constraintSet.connect(R.id.keyguard_status_view, 7, z2 ? 0 : R.id.qs_edge_guideline, 7);
        if (!z3) {
            constraintSet.applyTo(notificationsQuickSettingsContainer);
            return;
        }
        this.mInteractionJankMonitor.begin(this.mView, 70);
        ChangeBounds changeBounds = new ChangeBounds();
        if (z) {
            changeBounds.excludeTarget(R.id.status_view_media_container, true);
        }
        changeBounds.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        changeBounds.setDuration(360L);
        ClockController clockController = keyguardClockSwitchController.mClockEventController.clock;
        boolean z5 = clockController != null && clockController.getLargeClock().getConfig().getHasCustomPositionUpdatedAnimation();
        C07841 c07841 = this.mKeyguardStatusAlignmentTransitionListener;
        if (z5) {
            FrameLayout frameLayout = (FrameLayout) ((KeyguardStatusView) this.mView).findViewById(R.id.lockscreen_clock_view_large);
            if (frameLayout == null || frameLayout.getChildCount() == 0) {
                changeBounds.addListener(c07841);
                TransitionManager.beginDelayedTransition(notificationsQuickSettingsContainer, changeBounds);
            } else {
                View childAt = frameLayout.getChildAt(0);
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(changeBounds);
                SplitShadeTransitionAdapter splitShadeTransitionAdapter = new SplitShadeTransitionAdapter(keyguardClockSwitchController);
                splitShadeTransitionAdapter.setInterpolator(Interpolators.LINEAR);
                splitShadeTransitionAdapter.setDuration(1000L);
                splitShadeTransitionAdapter.addTarget(childAt);
                transitionSet.addTransition(splitShadeTransitionAdapter);
                transitionSet.addListener((Transition.TransitionListener) c07841);
                TransitionManager.beginDelayedTransition(notificationsQuickSettingsContainer, transitionSet);
            }
        } else {
            changeBounds.addListener(c07841);
            TransitionManager.beginDelayedTransition(notificationsQuickSettingsContainer, changeBounds);
        }
        constraintSet.applyTo(notificationsQuickSettingsContainer);
    }

    public final void updatePosition(int i, int i2, boolean z, List list) {
        float f = i2;
        setProperty(AnimatableProperty.f351Y, f, z);
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        List list2 = faceWidgetContainerWrapper == null ? null : faceWidgetContainerWrapper.mContentsContainerList;
        boolean z2 = true;
        if (list2 == null || list2.size() <= 1 || list == null || list2.size() != list.size()) {
            FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = this.mKeyguardStatusBase;
            if (faceWidgetContainerWrapper2 != null) {
                View view = faceWidgetContainerWrapper2.mFaceWidgetContainer;
                if (view == null) {
                    view = new View(faceWidgetContainerWrapper2.mContext);
                }
                float f2 = i;
                if (view.getX() != f2 || view.getY() != f) {
                    view.setX(f2);
                    view.setY(f);
                }
            }
            z2 = false;
        } else {
            boolean z3 = false;
            for (int i3 = 0; i3 < list2.size(); i3++) {
                View view2 = (View) list2.get(i3);
                Point point = (Point) list.get(i3);
                if (view2 != null && (view2.getX() != point.x || view2.getY() != point.y)) {
                    view2.setX(point.x);
                    view2.setY(point.y);
                    z3 = true;
                }
            }
            z2 = z3;
        }
        if (z2) {
            PluginAODManager pluginAODManager = (PluginAODManager) this.mPluginAODManagerLazy.get();
            if (pluginAODManager.mAODPlugin == null) {
                return;
            }
            Log.d("PluginAODManager", "onFaceWidgetPositionChanged");
            pluginAODManager.mAODPlugin.onFaceWidgetPositionChanged();
        }
    }
}
