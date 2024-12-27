package com.android.keyguard;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.smartspace.SmartspaceSession;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardSliceView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.KeyguardStatusViewAlphaChangeControllerWrapper;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.collections.EmptySet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardStatusViewController extends ViewController implements Dumpable {
    public static final AnimationProperties CLOCK_ANIMATION_PROPERTIES;
    static final String TAG = "KeyguardStatusViewController";
    public final Rect mClipBounds;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass4 mConfigurationListener;
    public final DozeParameters mDozeParameters;
    public final DumpManager mDumpManager;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public Supplier mIsDLSViewEnabledSupplier;
    public final KeyguardClockSwitchController mKeyguardClockSwitchController;
    public final KeyguardSliceViewController mKeyguardSliceViewController;
    public final AnonymousClass1 mKeyguardStatusAlignmentTransitionListener;
    public FaceWidgetContainerWrapper mKeyguardStatusBase;
    public final KeyguardStatusViewAlphaChangeControllerWrapper mKeyguardStatusViewAlphaChangeControllerWrapper;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    public final AnonymousClass3 mLockscreenShadeTransitonCallback;
    public final DcmMascotViewContainer mMascotViewContainer;
    public Lazy mPluginAODManagerLazy;
    public Boolean mSplitShadeEnabled;
    public View mStatusArea;
    public ValueAnimator mStatusAreaHeightAnimator;
    public final AnonymousClass2 mStatusAreaLayoutChangeListener;
    public Boolean mStatusViewCentered;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                    ClockController clock = splitShadeTransitionAdapter.mController.getClock();
                    if (clock == null) {
                        return;
                    }
                    clock.getLargeClock().getAnimations().onPositionUpdated(i2, i3, valueAnimator.getAnimatedFraction());
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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardStatusViewController$3] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.KeyguardStatusViewController$4] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.KeyguardStatusViewController$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardStatusViewController$2] */
    public KeyguardStatusViewController(DcmMascotViewContainer dcmMascotViewContainer, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper, KeyguardStatusView keyguardStatusView, KeyguardSliceViewController keyguardSliceViewController, KeyguardClockSwitchController keyguardClockSwitchController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, KeyguardLogger keyguardLogger, InteractionJankMonitor interactionJankMonitor, KeyguardInteractor keyguardInteractor, DumpManager dumpManager, PowerInteractor powerInteractor) {
        super(keyguardStatusView);
        this.mClipBounds = new Rect();
        this.mStatusArea = null;
        this.mStatusAreaHeightAnimator = null;
        this.mSplitShadeEnabled = Boolean.FALSE;
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
        this.mStatusAreaLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.keyguard.KeyguardStatusViewController.2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (KeyguardStatusViewController.this.mDozeParameters.getAlwaysOn()) {
                    int height = view.getHeight() - (i8 - i6);
                    if (height == 0) {
                        return;
                    }
                    int i9 = height * (-1);
                    ValueAnimator valueAnimator = KeyguardStatusViewController.this.mStatusAreaHeightAnimator;
                    long j = 133;
                    if (valueAnimator != null && valueAnimator.isRunning()) {
                        j = 133 + (KeyguardStatusViewController.this.mStatusAreaHeightAnimator.getDuration() - KeyguardStatusViewController.this.mStatusAreaHeightAnimator.getCurrentPlayTime());
                        i9 += ((Integer) KeyguardStatusViewController.this.mStatusAreaHeightAnimator.getAnimatedValue()).intValue();
                        KeyguardStatusViewController.this.mStatusAreaHeightAnimator.cancel();
                        KeyguardStatusViewController.this.mStatusAreaHeightAnimator = null;
                    }
                    KeyguardStatusViewController.this.mStatusAreaHeightAnimator = ValueAnimator.ofInt(i9, 0);
                    KeyguardStatusViewController.this.mStatusAreaHeightAnimator.setDuration(j);
                    KeyguardStatusViewController keyguardStatusViewController = KeyguardStatusViewController.this;
                    final NotificationIconContainer notificationIconContainer = keyguardStatusViewController.mKeyguardClockSwitchController.mAodIconContainer;
                    if (notificationIconContainer != null) {
                        keyguardStatusViewController.mStatusAreaHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardStatusViewController$2$$ExternalSyntheticLambda0
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                notificationIconContainer.setTranslationY(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                            }
                        });
                    }
                    KeyguardStatusViewController.this.mStatusAreaHeightAnimator.start();
                }
            }
        };
        this.mIsDLSViewEnabledSupplier = null;
        this.mLockscreenShadeTransitonCallback = new LockscreenShadeTransitionController.Callback() { // from class: com.android.keyguard.KeyguardStatusViewController.3
            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void setTransitionToFullShadeAmount(float f) {
                View childAt;
                Slog.d(KeyguardStatusViewController.TAG, "setTransitionToFullShadeAmount  " + f);
                AnimationProperties animationProperties = KeyguardStatusViewController.CLOCK_ANIMATION_PROPERTIES;
                View clockView = KeyguardStatusViewController.this.getClockView();
                if (!(clockView instanceof ViewGroup) || (childAt = ((ViewGroup) clockView).getChildAt(0)) == null) {
                    return;
                }
                childAt.setTranslationY(clockView.getResources().getDisplayMetrics().heightPixels * 0.325f * f);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardStatusViewController.4
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardStatusViewController.this.mKeyguardClockSwitchController.onConfigChanged();
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
                keyguardStatusViewController.refreshTime();
                KeyguardClockSwitchController keyguardClockSwitchController2 = keyguardStatusViewController.mKeyguardClockSwitchController;
                LockscreenSmartspaceController lockscreenSmartspaceController = keyguardClockSwitchController2.mSmartspaceController;
                if (lockscreenSmartspaceController.isEnabled()) {
                    keyguardClockSwitchController2.removeViewsFromStatusArea();
                    keyguardClockSwitchController2.addSmartspaceView();
                    if (lockscreenSmartspaceController.isDateWeatherDecoupled()) {
                        keyguardClockSwitchController2.mDateWeatherView.removeView(keyguardClockSwitchController2.mWeatherView);
                        keyguardClockSwitchController2.addDateWeatherView();
                        keyguardClockSwitchController2.setDateWeatherVisibility();
                        if (keyguardClockSwitchController2.mWeatherView != null) {
                            keyguardClockSwitchController2.mUiExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(keyguardClockSwitchController2, 4));
                        }
                    }
                }
            }
        };
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardStatusViewController.5
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                if (z) {
                    AnimationProperties animationProperties = KeyguardStatusViewController.CLOCK_ANIMATION_PROPERTIES;
                    KeyguardStatusViewController.this.refreshTime();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTimeChanged() {
                AnimationProperties animationProperties = KeyguardStatusViewController.CLOCK_ANIMATION_PROPERTIES;
                KeyguardStatusViewController.this.refreshTime();
            }
        };
        this.mKeyguardSliceViewController = keyguardSliceViewController;
        this.mKeyguardClockSwitchController = keyguardClockSwitchController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardVisibilityHelper = new KeyguardVisibilityHelper(this.mView, keyguardStateController, dozeParameters, screenOffAnimationController, true, keyguardLogger.buffer);
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mDumpManager = dumpManager;
        this.mMascotViewContainer = dcmMascotViewContainer;
        this.mKeyguardStatusViewAlphaChangeControllerWrapper = keyguardStatusViewAlphaChangeControllerWrapper;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Object valueOf;
        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) this.mView;
        keyguardStatusView.getClass();
        printWriter.println("KeyguardStatusView:");
        printWriter.println("  mDarkAmount: 0.0");
        printWriter.println("  visibility: " + keyguardStatusView.getVisibility());
        KeyguardClockSwitch keyguardClockSwitch = keyguardStatusView.mClockView;
        if (keyguardClockSwitch != null) {
            keyguardClockSwitch.dump(printWriter);
        }
        KeyguardSliceView keyguardSliceView = keyguardStatusView.mKeyguardSlice;
        if (keyguardSliceView != null) {
            StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardSliceView:", "  mTitle: ");
            TextView textView = keyguardSliceView.mTitle;
            Object obj = "null";
            if (textView == null) {
                valueOf = "null";
            } else {
                valueOf = Boolean.valueOf(textView.getVisibility() == 0);
            }
            m.append(valueOf);
            printWriter.println(m.toString());
            StringBuilder sb = new StringBuilder("  mRow: ");
            KeyguardSliceView.Row row = keyguardSliceView.mRow;
            if (row != null) {
                obj = Boolean.valueOf(row.getVisibility() == 0);
            }
            sb.append(obj);
            printWriter.println(sb.toString());
            printWriter.println("  mTextColor: " + Integer.toHexString(keyguardSliceView.mTextColor));
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(new StringBuilder("  mDarkAmount: "), keyguardSliceView.mDarkAmount, printWriter, "  mHasHeader: "), keyguardSliceView.mHasHeader, printWriter);
        }
    }

    public final View getClockView() {
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        if (faceWidgetContainerWrapper == null) {
            return null;
        }
        View view = faceWidgetContainerWrapper.mClockContainer;
        return view != null ? view : faceWidgetContainerWrapper.mFaceWidgetContainer;
    }

    public final int getHeight() {
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        if (faceWidgetContainerWrapper == null) {
            return ((KeyguardStatusView) this.mView).getHeight();
        }
        List<View> list = faceWidgetContainerWrapper.mContentsContainerList;
        int i = 0;
        if (list == null) {
            View view = faceWidgetContainerWrapper.mFaceWidgetContainer;
            if (view != null) {
                return view.getHeight();
            }
            return 0;
        }
        int i2 = Integer.MAX_VALUE;
        for (View view2 : list) {
            i2 = Math.min(i2, (int) view2.getY());
            i = Math.max(i, (int) view2.getY());
        }
        return i - i2;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        KeyguardClockSwitchController keyguardClockSwitchController = this.mKeyguardClockSwitchController;
        keyguardClockSwitchController.init();
        final View findViewById = ((KeyguardStatusView) this.mView).findViewById(R.id.status_view_media_container);
        if (findViewById != null) {
            keyguardClockSwitchController.getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    KeyguardStatusViewController keyguardStatusViewController = KeyguardStatusViewController.this;
                    View view2 = findViewById;
                    if (keyguardStatusViewController.mSplitShadeEnabled.booleanValue() && !keyguardStatusViewController.mKeyguardClockSwitchController.getView().mSplitShadeCentered && keyguardStatusViewController.mKeyguardUpdateMonitor.isKeyguardVisible()) {
                        if (view.getHeight() == i8 - i6 || view2.getVisibility() != 0 || view2.getHeight() == 0) {
                            return;
                        }
                        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                        Interpolator interpolator = Interpolators.STANDARD;
                        companion.getClass();
                        if ((16 & 2) != 0) {
                            interpolator = ViewHierarchyAnimator.DEFAULT_INTERPOLATOR;
                        }
                        boolean z = (16 & 8) != 0;
                        EmptySet emptySet = (16 & 16) != 0 ? EmptySet.INSTANCE : null;
                        companion.getClass();
                        if (ViewHierarchyAnimator.Companion.occupiesSpace(view2.getVisibility(), view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom())) {
                            ViewHierarchyAnimator.Companion.addListener(view2, new ViewHierarchyAnimator$Companion$createListener$1(null, false, interpolator, 500L, true, null), true, z, emptySet);
                        }
                    }
                }
            });
        }
        DumpManager dumpManager = this.mDumpManager;
        String str = "KeyguardStatusViewController#" + hashCode();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, str, this);
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mStatusArea = ((KeyguardStatusView) this.mView).findViewById(R.id.keyguard_status_area);
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        this.mStatusArea.addOnLayoutChangeListener(this.mStatusAreaLayoutChangeListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        this.mStatusArea.removeOnLayoutChangeListener(this.mStatusAreaLayoutChangeListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    public final void refreshTime() {
        SmartspaceSession smartspaceSession;
        KeyguardClockSwitchController keyguardClockSwitchController = this.mKeyguardClockSwitchController;
        keyguardClockSwitchController.getClass();
        keyguardClockSwitchController.mLogBuffer.log("KeyguardClockSwitchController", LogLevel.INFO, "refresh", null);
        LockscreenSmartspaceController lockscreenSmartspaceController = keyguardClockSwitchController.mSmartspaceController;
        if (lockscreenSmartspaceController != null && (smartspaceSession = lockscreenSmartspaceController.session) != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
        ClockController clock = keyguardClockSwitchController.getClock();
        if (clock != null) {
            clock.getSmallClock().getEvents().onTimeTick();
            clock.getLargeClock().getEvents().onTimeTick();
        }
    }

    public final void setAlpha(float f) {
        View view;
        if (this.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
            return;
        }
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        if (faceWidgetContainerWrapper != null && (view = faceWidgetContainerWrapper.mFaceWidgetContainer) != null) {
            view.setAlpha(f);
        }
        KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper = this.mKeyguardStatusViewAlphaChangeControllerWrapper;
        if (keyguardStatusViewAlphaChangeControllerWrapper != null) {
            keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(f);
        }
    }

    public final void setClipBounds(Rect rect) {
        if (rect == null) {
            ((KeyguardStatusView) this.mView).setClipBounds(null);
        } else {
            this.mClipBounds.set(rect.left, (int) (rect.top - ((KeyguardStatusView) this.mView).getY()), rect.right, (int) (rect.bottom - ((KeyguardStatusView) this.mView).getY()));
            ((KeyguardStatusView) this.mView).setClipBounds(this.mClipBounds);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper$$ExternalSyntheticLambda0] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setKeyguardStatusViewVisibility(int r17, int r18, boolean r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardStatusViewController.setKeyguardStatusViewVisibility(int, int, boolean, boolean):void");
    }

    public void setProperty(AnimatableProperty animatableProperty, float f, boolean z) {
        PropertyAnimator.setProperty((KeyguardStatusView) this.mView, animatableProperty, f, CLOCK_ANIMATION_PROPERTIES, z);
    }

    public final void setStatusAccessibilityImportance(int i) {
        ((KeyguardStatusView) this.mView).setImportantForAccessibility(i);
    }

    public final void setTranslationY$1(float f) {
        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) this.mView;
        keyguardStatusView.getClass();
        Set emptySet = Collections.emptySet();
        for (int i = 0; i < keyguardStatusView.mStatusViewContainer.getChildCount(); i++) {
            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i);
            if (!emptySet.contains(childAt)) {
                childAt.setTranslationY(f);
            }
        }
    }

    public final void updateAlignment(ConstraintLayout constraintLayout, boolean z, boolean z2, boolean z3) {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        boolean z4 = z && z2;
        KeyguardClockSwitchController keyguardClockSwitchController = this.mKeyguardClockSwitchController;
        keyguardClockSwitchController.setSplitShadeCentered(z4);
        if (this.mStatusViewCentered.booleanValue() == z2) {
            return;
        }
        this.mStatusViewCentered = Boolean.valueOf(z2);
        if (constraintLayout == null) {
            return;
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        Flags.migrateClocksToBlueprint();
        constraintSet.connect(R.id.keyguard_status_view, 7, z2 ? 0 : R.id.qs_edge_guideline, 7);
        if (!z3) {
            constraintSet.applyTo(constraintLayout);
            return;
        }
        this.mInteractionJankMonitor.begin(this.mView, 70);
        ChangeBounds changeBounds = new ChangeBounds();
        if (z) {
            changeBounds.excludeTarget(R.id.status_view_media_container, true);
            changeBounds.excludeTarget(ViewPager.class, true);
            changeBounds.excludeChildren(ViewPager.class, true);
        }
        changeBounds.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        changeBounds.setDuration(360L);
        ClockController clock = keyguardClockSwitchController.getClock();
        AnonymousClass1 anonymousClass1 = this.mKeyguardStatusAlignmentTransitionListener;
        if (clock == null || !clock.getLargeClock().getConfig().getHasCustomPositionUpdatedAnimation()) {
            changeBounds.addListener(anonymousClass1);
            TransitionManager.beginDelayedTransition(constraintLayout, changeBounds);
        } else {
            Flags.migrateClocksToBlueprint();
            FrameLayout frameLayout = (FrameLayout) ((KeyguardStatusView) this.mView).findViewById(R.id.lockscreen_clock_view_large);
            if (frameLayout == null || frameLayout.getChildCount() == 0) {
                changeBounds.addListener(anonymousClass1);
                TransitionManager.beginDelayedTransition(constraintLayout, changeBounds);
            } else {
                View childAt = frameLayout.getChildAt(0);
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(changeBounds);
                SplitShadeTransitionAdapter splitShadeTransitionAdapter = new SplitShadeTransitionAdapter(keyguardClockSwitchController);
                splitShadeTransitionAdapter.setInterpolator(Interpolators.LINEAR);
                splitShadeTransitionAdapter.setDuration(1000L);
                splitShadeTransitionAdapter.addTarget(childAt);
                transitionSet.addTransition(splitShadeTransitionAdapter);
                if (z) {
                    transitionSet.excludeTarget(ViewPager.class, true);
                    transitionSet.excludeChildren(ViewPager.class, true);
                }
                transitionSet.addListener((Transition.TransitionListener) anonymousClass1);
                TransitionManager.beginDelayedTransition(constraintLayout, transitionSet);
            }
        }
        constraintSet.applyTo(constraintLayout);
    }

    public final void updatePosition(int i, int i2, float f, boolean z, List list) {
        float f2 = i2;
        setProperty(AnimatableProperty.Y, f2, z);
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
                float f3 = i;
                if (view.getX() != f3 || view.getY() != f2) {
                    view.setX(f3);
                    view.setY(f2);
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
            if (pluginAODManager.mAODPlugin != null) {
                Log.d("PluginAODManager", "onFaceWidgetPositionChanged");
                pluginAODManager.mAODPlugin.onFaceWidgetPositionChanged();
            }
        }
        KeyguardClockSwitchController keyguardClockSwitchController = this.mKeyguardClockSwitchController;
        ClockController clock = keyguardClockSwitchController.getClock();
        AnimationProperties animationProperties = CLOCK_ANIMATION_PROPERTIES;
        if (clock == null || !clock.getConfig().getUseAlternateSmartspaceAODTransition()) {
            keyguardClockSwitchController.updatePosition(i, f, animationProperties, z);
            setProperty(AnimatableProperty.SCALE_X, 1.0f, z);
            setProperty(AnimatableProperty.SCALE_Y, 1.0f, z);
        } else {
            keyguardClockSwitchController.updatePosition(i, 1.0f, animationProperties, z);
            setProperty(AnimatableProperty.SCALE_X, f, z);
            setProperty(AnimatableProperty.SCALE_Y, f, z);
        }
    }
}
