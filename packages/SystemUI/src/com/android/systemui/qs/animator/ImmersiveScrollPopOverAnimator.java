package com.android.systemui.qs.animator;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.samsung.android.view.animation.SineOut60;
import java.util.Collections;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ImmersiveScrollPopOverAnimator extends SecQSImplAnimatorBase {
    public ValueAnimator bigClockAnimatorAlpha;
    public SpringAnimation bigClockAnimatorScaleX;
    public SpringAnimation bigClockAnimatorScaleY;
    public QSAnimView bigClockParent;
    public QSAnimView clock;
    public ValueAnimator clockAnimator;
    public final HeadsUpManager headsUpManager;
    public final SpringForce immersiveScaleForce;
    public boolean isClockAppearAnimationTriggered = true;
    public boolean isClockDisappearAnimationTriggered;
    public final ConfigurationState lastConfigurationState;
    public int lastPosition;
    public QSAnimView plmn;
    public ValueAnimator plmnAnimator;
    public float qqsHeight;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final QSAnimViewProvider viewProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ImmersiveScrollPopOverAnimator(QSAnimViewProvider qSAnimViewProvider, HeadsUpManager headsUpManager, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.viewProvider = qSAnimViewProvider;
        this.headsUpManager = headsUpManager;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(0.85f);
        springForce.setStiffness(150.0f);
        this.immersiveScaleForce = springForce;
        this.lastConfigurationState = new ConfigurationState(Collections.singletonList(ConfigurationState.ConfigurationField.ORIENTATION));
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        if (((HeadsUpManagerImpl) this.headsUpManager).mHasPinnedNotification || isThereNoView()) {
            return;
        }
        Log.d("ImmersiveScrollPopOverAnimator", "clearAnimators");
        QSAnimView qSAnimView = this.plmn;
        if (qSAnimView != null) {
            qSAnimView.setAlpha(1.0f);
        }
        QSAnimView qSAnimView2 = this.clock;
        if (qSAnimView2 != null) {
            qSAnimView2.setAlpha(0.0f);
        }
        QSAnimView qSAnimView3 = this.bigClockParent;
        if (qSAnimView3 != null) {
            qSAnimView3.setAlpha(1.0f);
            qSAnimView3.setScaleX(1.0f);
            qSAnimView3.setScaleY(1.0f);
        }
        this.isClockDisappearAnimationTriggered = false;
        this.isClockAppearAnimationTriggered = true;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        this.mQs = null;
        this.bigClockParent = null;
        this.plmn = null;
        this.clock = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        return super.isThereNoView() || !this.secQsUiDisplayModeInteractor.isTablet();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        View view;
        ConfigurationState configurationState = this.lastConfigurationState;
        if (!configurationState.needToUpdate(configuration) || configuration == null) {
            return;
        }
        QSImpl qSImpl = this.mQs;
        if (qSImpl != null && (view = qSImpl.getView()) != null) {
            view.post(new Runnable() { // from class: com.android.systemui.qs.animator.ImmersiveScrollPopOverAnimator$onConfigurationChanged$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    ImmersiveScrollPopOverAnimator.this.updateAnimators();
                }
            });
        }
        configurationState.update(configuration);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onNotificationScrolled(int i) {
        if (isThereNoView()) {
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onNotificationScrolled : ", "ImmersiveScrollPopOverAnimator");
        QsAnimatorState.isNotificationImmersiceScrolling = i != 0;
        int i2 = this.lastPosition;
        SpringForce springForce = this.immersiveScaleForce;
        if (i2 < i && i > this.qqsHeight && !this.isClockDisappearAnimationTriggered) {
            springForce.mFinalPosition = 0.85f;
            SpringAnimation springAnimation = this.bigClockAnimatorScaleX;
            if (springAnimation != null) {
                springAnimation.mSpring = springForce;
            }
            SpringAnimation springAnimation2 = this.bigClockAnimatorScaleY;
            if (springAnimation2 != null) {
                springAnimation2.mSpring = springForce;
            }
            ValueAnimator valueAnimator = this.bigClockAnimatorAlpha;
            if (valueAnimator != null) {
                valueAnimator.setDuration(150L);
            }
            ValueAnimator valueAnimator2 = this.plmnAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.setDuration(100L);
            }
            ValueAnimator valueAnimator3 = this.plmnAnimator;
            if (valueAnimator3 != null) {
                valueAnimator3.setInterpolator(new LinearInterpolator());
            }
            ValueAnimator valueAnimator4 = this.clockAnimator;
            if (valueAnimator4 != null) {
                valueAnimator4.setDuration(250L);
            }
            ValueAnimator valueAnimator5 = this.clockAnimator;
            if (valueAnimator5 != null) {
                valueAnimator5.setInterpolator(new SineOut60());
            }
            SpringAnimation springAnimation3 = this.bigClockAnimatorScaleX;
            if (springAnimation3 != null) {
                springAnimation3.start();
            }
            SpringAnimation springAnimation4 = this.bigClockAnimatorScaleY;
            if (springAnimation4 != null) {
                springAnimation4.start();
            }
            ValueAnimator valueAnimator6 = this.bigClockAnimatorAlpha;
            if (valueAnimator6 != null) {
                valueAnimator6.start();
            }
            ValueAnimator valueAnimator7 = this.plmnAnimator;
            if (valueAnimator7 != null) {
                valueAnimator7.start();
            }
            ValueAnimator valueAnimator8 = this.clockAnimator;
            if (valueAnimator8 != null) {
                valueAnimator8.start();
            }
            this.isClockDisappearAnimationTriggered = true;
            this.isClockAppearAnimationTriggered = false;
        } else if (i2 > i && i < this.qqsHeight && !this.isClockAppearAnimationTriggered) {
            springForce.mFinalPosition = 1.0f;
            SpringAnimation springAnimation5 = this.bigClockAnimatorScaleX;
            if (springAnimation5 != null) {
                springAnimation5.mSpring = springForce;
            }
            SpringAnimation springAnimation6 = this.bigClockAnimatorScaleY;
            if (springAnimation6 != null) {
                springAnimation6.mSpring = springForce;
            }
            ValueAnimator valueAnimator9 = this.bigClockAnimatorAlpha;
            if (valueAnimator9 != null) {
                valueAnimator9.setDuration(250L);
            }
            ValueAnimator valueAnimator10 = this.plmnAnimator;
            if (valueAnimator10 != null) {
                valueAnimator10.setDuration(250L);
            }
            ValueAnimator valueAnimator11 = this.plmnAnimator;
            if (valueAnimator11 != null) {
                valueAnimator11.setInterpolator(new SineOut60());
            }
            ValueAnimator valueAnimator12 = this.clockAnimator;
            if (valueAnimator12 != null) {
                valueAnimator12.setDuration(100L);
            }
            ValueAnimator valueAnimator13 = this.clockAnimator;
            if (valueAnimator13 != null) {
                valueAnimator13.setInterpolator(new LinearInterpolator());
            }
            SpringAnimation springAnimation7 = this.bigClockAnimatorScaleX;
            if (springAnimation7 != null) {
                springAnimation7.start();
            }
            SpringAnimation springAnimation8 = this.bigClockAnimatorScaleY;
            if (springAnimation8 != null) {
                springAnimation8.start();
            }
            ValueAnimator valueAnimator14 = this.bigClockAnimatorAlpha;
            if (valueAnimator14 != null) {
                valueAnimator14.reverse();
            }
            ValueAnimator valueAnimator15 = this.plmnAnimator;
            if (valueAnimator15 != null) {
                valueAnimator15.reverse();
            }
            ValueAnimator valueAnimator16 = this.clockAnimator;
            if (valueAnimator16 != null) {
                valueAnimator16.reverse();
            }
            this.isClockDisappearAnimationTriggered = false;
            this.isClockAppearAnimationTriggered = true;
        }
        this.lastPosition = i;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        float f = shadeExpansionChangeEvent.fraction;
        if (f == 1.0f) {
            updateViews$1();
            updateAnimators();
        } else if (f == 0.0f) {
            clearAnimationState();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        super.onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        if (this.mPanelSplitEnabled == panelTransitionStateChangeEvent.enabled) {
            return;
        }
        Log.d("ImmersiveScrollPopOverAnimator", "mPanelSplitEnabled changed");
        updateViews$1();
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = (QSImpl) qs;
        updateViews$1();
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        if (isThereNoView()) {
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.plmnAnimator = ofFloat;
        if (ofFloat != null) {
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.animator.ImmersiveScrollPopOverAnimator$updateAnimators$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    QSAnimView qSAnimView = ImmersiveScrollPopOverAnimator.this.plmn;
                    if (qSAnimView != null) {
                        qSAnimView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                }
            });
        }
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.clockAnimator = ofFloat2;
        if (ofFloat2 != null) {
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.animator.ImmersiveScrollPopOverAnimator$updateAnimators$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    QSAnimView qSAnimView = ImmersiveScrollPopOverAnimator.this.clock;
                    if (qSAnimView != null) {
                        qSAnimView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                }
            });
        }
        final QSAnimView qSAnimView = this.bigClockParent;
        if (qSAnimView != null) {
            this.bigClockAnimatorScaleX = new SpringAnimation(qSAnimView.getView(), DynamicAnimation.SCALE_X);
            this.bigClockAnimatorScaleY = new SpringAnimation(qSAnimView.getView(), DynamicAnimation.SCALE_Y);
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
            this.bigClockAnimatorAlpha = ofFloat3;
            if (ofFloat3 != null) {
                ofFloat3.setInterpolator(new SineOut60());
            }
            ValueAnimator valueAnimator = this.bigClockAnimatorAlpha;
            if (valueAnimator != null) {
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.animator.ImmersiveScrollPopOverAnimator$updateAnimators$3$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        QSAnimView.this.setAlpha(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                    }
                });
            }
        }
    }

    public final void updateViews$1() {
        View view;
        QSAnimViewProvider.ViewType viewType = QSAnimViewProvider.ViewType.QS_HEADER_QQS;
        QSAnimViewProvider qSAnimViewProvider = this.viewProvider;
        QSAnimView qSAnimView = qSAnimViewProvider.get(viewType);
        this.bigClockParent = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_CLOCK_DATE_PARENT);
        this.plmn = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PLMN);
        this.clock = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.POP_OVER_IMMERSIVE_CLOCK);
        SecPanelSplitHelper.Companion.getClass();
        int i = 0;
        if (!SecPanelSplitHelper.isEnabled && qSAnimView != null && (view = qSAnimView.getView()) != null) {
            i = view.getHeight();
        }
        this.qqsHeight = i;
    }
}
