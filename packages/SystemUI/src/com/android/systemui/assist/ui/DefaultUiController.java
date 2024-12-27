package com.android.systemui.assist.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.metrics.LogMaker;
import android.os.Build;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.assist.ui.PerimeterPathGuide;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.samsung.android.knox.zt.config.BuildConfig;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DefaultUiController {
    public final AssistLogger mAssistLogger;
    public final Lazy mAssistManagerLazy;
    public final InvocationLightsView mInvocationLightsView;
    public final WindowManager.LayoutParams mLayoutParams;
    public final MetricsLogger mMetricsLogger;
    public final FrameLayout mRoot;
    public final WindowManager mWindowManager;
    public final PathInterpolator mProgressInterpolator = new PathInterpolator(0.83f, 0.0f, 0.84f, 1.0f);
    public boolean mAttached = false;
    public boolean mInvocationInProgress = false;
    public float mLastInvocationProgress = 0.0f;
    public ValueAnimator mInvocationAnimator = new ValueAnimator();

    static {
        String str = Build.TYPE;
        Locale locale = Locale.ROOT;
        if (str.toLowerCase(locale).contains(BuildConfig.BUILD_TYPE)) {
            return;
        }
        str.toLowerCase(locale).equals("eng");
    }

    public DefaultUiController(Context context, AssistLogger assistLogger, WindowManager windowManager, MetricsLogger metricsLogger, Lazy lazy, NavigationBarController navigationBarController) {
        this.mAssistLogger = assistLogger;
        FrameLayout frameLayout = new FrameLayout(context);
        this.mRoot = frameLayout;
        this.mWindowManager = windowManager;
        this.mMetricsLogger = metricsLogger;
        this.mAssistManagerLazy = lazy;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 0, 0, 2024, 808, -3);
        this.mLayoutParams = layoutParams;
        layoutParams.privateFlags = 64;
        layoutParams.gravity = 80;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Assist");
        InvocationLightsView invocationLightsView = (InvocationLightsView) LayoutInflater.from(context).inflate(R.layout.invocation_lights, (ViewGroup) frameLayout, false);
        this.mInvocationLightsView = invocationLightsView;
        invocationLightsView.mNavigationBarController = navigationBarController;
        frameLayout.addView(invocationLightsView);
    }

    public final void animateInvocationCompletion() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mLastInvocationProgress, 1.0f);
        this.mInvocationAnimator = ofFloat;
        ofFloat.setStartDelay(1L);
        this.mInvocationAnimator.setDuration(200L);
        this.mInvocationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.assist.ui.DefaultUiController$$ExternalSyntheticLambda0
            public final /* synthetic */ int f$1;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DefaultUiController defaultUiController = DefaultUiController.this;
                defaultUiController.getClass();
                defaultUiController.setProgressInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.mInvocationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.assist.ui.DefaultUiController.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DefaultUiController defaultUiController = DefaultUiController.this;
                defaultUiController.mInvocationInProgress = false;
                defaultUiController.mLastInvocationProgress = 0.0f;
                defaultUiController.hide();
            }
        });
        this.mInvocationAnimator.start();
    }

    public final void hide() {
        NavigationBarController navigationBarController;
        NavigationBar defaultNavigationBar;
        if (this.mAttached) {
            this.mWindowManager.removeViewImmediate(this.mRoot);
            this.mAttached = false;
        }
        if (this.mInvocationAnimator.isRunning()) {
            this.mInvocationAnimator.cancel();
        }
        InvocationLightsView invocationLightsView = this.mInvocationLightsView;
        invocationLightsView.setVisibility(8);
        Iterator it = invocationLightsView.mAssistInvocationLights.iterator();
        while (it.hasNext()) {
            ((EdgeLight) it.next()).setEndpoints(0.0f, 0.0f);
        }
        if (invocationLightsView.mRegistered && (navigationBarController = invocationLightsView.mNavigationBarController) != null && (defaultNavigationBar = ((NavigationBarControllerImpl) navigationBarController).getDefaultNavigationBar()) != null) {
            ((ArrayList) defaultNavigationBar.mNavigationBarTransitions.mDarkIntensityListeners).remove(invocationLightsView);
            invocationLightsView.mRegistered = false;
        }
        this.mInvocationInProgress = false;
    }

    public final void logInvocationProgressMetrics(float f, boolean z) {
        AssistLogger assistLogger = this.mAssistLogger;
        if (!z && f > 0.0f) {
            assistLogger.reportAssistantInvocationEventFromLegacy(1, false, null, null);
            this.mMetricsLogger.write(new LogMaker(1716).setType(4).setSubtype((((AssistManager) this.mAssistManagerLazy.get()).mPhoneStateMonitor.getPhoneState() << 4) | 2));
        }
        ValueAnimator valueAnimator = this.mInvocationAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && z && f == 0.0f) {
            assistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED);
            MetricsLogger.action(new LogMaker(1716).setType(5).setSubtype(1));
        }
    }

    public final void setProgressInternal(float f) {
        float interpolation = this.mProgressInterpolator.getInterpolation(f);
        InvocationLightsView invocationLightsView = this.mInvocationLightsView;
        if (interpolation == 0.0f) {
            invocationLightsView.setVisibility(8);
        } else {
            if (!invocationLightsView.mRegistered && invocationLightsView.mNavigationBarController != null) {
                if (BasicRune.NAVBAR_ENABLED) {
                    NavBarStateManager navStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(invocationLightsView.getContext().getDisplayId());
                    if (BasicRune.NAVBAR_TASKBAR && ((NavBarStateManagerImpl) navStateManager).isTaskBarEnabled(false)) {
                        LightBarTransitionsController lightBarTransitionsController = ((TaskbarDelegate) Dependency.sDependency.getDependencyInner(TaskbarDelegate.class)).getLightBarTransitionsController();
                        if (lightBarTransitionsController != null) {
                            invocationLightsView.updateDarkness(lightBarTransitionsController.mDarkIntensity);
                        }
                    }
                }
                NavigationBar defaultNavigationBar = ((NavigationBarControllerImpl) invocationLightsView.mNavigationBarController).getDefaultNavigationBar();
                if (defaultNavigationBar != null) {
                    NavigationBarTransitions navigationBarTransitions = defaultNavigationBar.mNavigationBarTransitions;
                    ((ArrayList) navigationBarTransitions.mDarkIntensityListeners).add(invocationLightsView);
                    invocationLightsView.updateDarkness(navigationBarTransitions.mLightTransitionsController.mDarkIntensity);
                    invocationLightsView.mRegistered = true;
                }
            }
            float f2 = invocationLightsView.mGuide.mRegions[PerimeterPathGuide.Region.BOTTOM_LEFT.ordinal()].normalizedLength;
            float f3 = (f2 - (0.6f * f2)) / 2.0f;
            PerimeterPathGuide perimeterPathGuide = invocationLightsView.mGuide;
            PerimeterPathGuide.Region region = PerimeterPathGuide.Region.BOTTOM;
            float lerp = MathUtils.lerp(0.0f, perimeterPathGuide.mRegions[region.ordinal()].normalizedLength / 4.0f, interpolation);
            float f4 = 1.0f - interpolation;
            float f5 = ((-f2) + f3) * f4;
            float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f3, f4, invocationLightsView.mGuide.mRegions[region.ordinal()].normalizedLength);
            float f6 = f5 + lerp;
            invocationLightsView.setLight(f5, f6, 0);
            float f7 = 2.0f * lerp;
            invocationLightsView.setLight(f6, f5 + f7, 1);
            float f8 = m$1 - lerp;
            invocationLightsView.setLight(m$1 - f7, f8, 2);
            invocationLightsView.setLight(f8, m$1, 3);
            invocationLightsView.setVisibility(0);
        }
        invocationLightsView.invalidate();
    }
}
