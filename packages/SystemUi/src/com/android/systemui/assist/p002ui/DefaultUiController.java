package com.android.systemui.assist.p002ui;

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
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.assist.p002ui.PerimeterPathGuide;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultUiController {
    public final AssistLogger mAssistLogger;
    public final Lazy mAssistManagerLazy;
    public final Context mContext;
    public InvocationLightsView mInvocationLightsView;
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
        if (str.toLowerCase(locale).contains("debug")) {
            return;
        }
        str.toLowerCase(locale).equals("eng");
    }

    public DefaultUiController(Context context, AssistLogger assistLogger, WindowManager windowManager, MetricsLogger metricsLogger, Lazy lazy) {
        this.mContext = context;
        this.mAssistLogger = assistLogger;
        FrameLayout frameLayout = new FrameLayout(context);
        this.mRoot = frameLayout;
        this.mWindowManager = windowManager;
        this.mMetricsLogger = metricsLogger;
        this.mAssistManagerLazy = lazy;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 0, 0, 2024, 824, -3);
        this.mLayoutParams = layoutParams;
        layoutParams.privateFlags = 64;
        layoutParams.gravity = 80;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Assist");
        if (BasicRune.ASSIST_INVOCATION_SWITCH) {
            return;
        }
        InvocationLightsView invocationLightsView = (InvocationLightsView) LayoutInflater.from(context).inflate(R.layout.invocation_lights, (ViewGroup) frameLayout, false);
        this.mInvocationLightsView = invocationLightsView;
        frameLayout.addView(invocationLightsView);
    }

    public final void animateInvocationCompletion() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mLastInvocationProgress, 1.0f);
        this.mInvocationAnimator = ofFloat;
        ofFloat.setStartDelay(1L);
        this.mInvocationAnimator.setDuration(200L);
        this.mInvocationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.assist.ui.DefaultUiController$$ExternalSyntheticLambda0
            public final /* synthetic */ int f$1 = 1;

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
        if (invocationLightsView != null) {
            invocationLightsView.setVisibility(8);
            Iterator it = invocationLightsView.mAssistInvocationLights.iterator();
            while (it.hasNext()) {
                ((EdgeLight) it.next()).setEndpoints(0.0f, 0.0f);
            }
            if (invocationLightsView.mRegistered && (navigationBarController = (NavigationBarController) Dependency.get(NavigationBarController.class)) != null && (defaultNavigationBar = navigationBarController.getDefaultNavigationBar()) != null) {
                ((ArrayList) defaultNavigationBar.mNavigationBarTransitions.mDarkIntensityListeners).remove(invocationLightsView);
                invocationLightsView.mRegistered = false;
            }
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
        NavigationBarController navigationBarController;
        InvocationLightsView invocationLightsView = this.mInvocationLightsView;
        if (invocationLightsView != null) {
            float interpolation = this.mProgressInterpolator.getInterpolation(f);
            if (interpolation == 0.0f) {
                invocationLightsView.setVisibility(8);
            } else {
                if (!invocationLightsView.mRegistered && (navigationBarController = (NavigationBarController) Dependency.get(NavigationBarController.class)) != null) {
                    if (BasicRune.NAVBAR_ENABLED) {
                        NavBarStateManager navStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.get(NavBarStore.class))).getNavStateManager(invocationLightsView.getContext().getDisplayId());
                        if (BasicRune.NAVBAR_SUPPORT_TASKBAR && navStateManager.isTaskBarEnabled(false)) {
                            LightBarTransitionsController lightBarTransitionsController = ((TaskbarDelegate) Dependency.get(TaskbarDelegate.class)).mLightBarTransitionsController;
                            if (lightBarTransitionsController != null) {
                                invocationLightsView.updateDarkness(lightBarTransitionsController.mDarkIntensity);
                            }
                        }
                    }
                    NavigationBar defaultNavigationBar = navigationBarController.getDefaultNavigationBar();
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
                float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f2, f3, f4, invocationLightsView.mGuide.mRegions[region.ordinal()].normalizedLength);
                float f6 = f5 + lerp;
                invocationLightsView.setLight(f5, f6, 0);
                float f7 = 2.0f * lerp;
                invocationLightsView.setLight(f6, f5 + f7, 1);
                float f8 = m20m - lerp;
                invocationLightsView.setLight(m20m - f7, f8, 2);
                invocationLightsView.setLight(f8, m20m, 3);
                invocationLightsView.setVisibility(0);
            }
            invocationLightsView.invalidate();
        }
    }
}
