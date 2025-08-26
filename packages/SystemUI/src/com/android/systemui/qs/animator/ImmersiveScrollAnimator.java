package com.android.systemui.qs.animator;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.Collections;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ImmersiveScrollAnimator extends SecQSImplAnimatorBase {
    public float animDistance;
    public QSAnimView bigClock;
    public TouchAnimator bigClockAnimator;
    public QSAnimView bigDate;
    public TouchAnimator bigDateAnimator;
    public final Context context;
    public final HeadsUpManager headsUpManager;
    public QSAnimView plmn;
    public TouchAnimator plmnAlphaAnimator;
    public float preXDiff;
    public float preYDiff;
    public float qqsHeight;
    public float scrolledPosition;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final QSAnimViewProvider viewProvider;
    public final int[] bigClockLoc = new int[2];
    public final int[] plmnLoc = new int[2];
    public final ConfigurationState lastConfigurationState = new ConfigurationState(Collections.singletonList(ConfigurationState.ConfigurationField.ORIENTATION));

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

    public ImmersiveScrollAnimator(Context context, QSAnimViewProvider qSAnimViewProvider, HeadsUpManager headsUpManager, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.context = context;
        this.viewProvider = qSAnimViewProvider;
        this.headsUpManager = headsUpManager;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
    }

    public static void getRelativePosition$1(View view, int[] iArr) {
        if (view instanceof NotificationsQuickSettingsContainer) {
            return;
        }
        iArr[0] = view.getLeft() + iArr[0];
        iArr[1] = view.getTop() + iArr[1];
        Object parent = view.getParent();
        View view2 = parent instanceof View ? (View) parent : null;
        if (view2 != null) {
            getRelativePosition$1(view2, iArr);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        if (((HeadsUpManagerImpl) this.headsUpManager).mHasPinnedNotification || super.isThereNoView() || this.secQsUiDisplayModeInteractor.isTablet()) {
            return;
        }
        Log.d("ImmersiveScrollAnimator", "clearAnimators");
        QSAnimView qSAnimView = this.plmn;
        if (qSAnimView != null) {
            qSAnimView.setAlpha(1.0f);
        }
        QSAnimView qSAnimView2 = this.bigClock;
        if (qSAnimView2 != null) {
            qSAnimView2.setAlpha(1.0f);
            qSAnimView2.setTranslationX(0.0f);
            qSAnimView2.setTranslationY(0.0f);
            qSAnimView2.setScaleX(1.0f);
            qSAnimView2.setScaleY(1.0f);
        }
        QSAnimView qSAnimView3 = this.bigDate;
        if (qSAnimView3 != null) {
            qSAnimView3.setAlpha(1.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        this.mQs = null;
        this.bigClock = null;
        this.bigDate = null;
        this.plmn = null;
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
            view.post(new Runnable() { // from class: com.android.systemui.qs.animator.ImmersiveScrollAnimator$onConfigurationChanged$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.updateAnimators();
                }
            });
        }
        configurationState.update(configuration);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onNotificationScrolled(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onNotificationScrolled : ", "ImmersiveScrollAnimator");
        QsAnimatorState.isNotificationImmersiceScrolling = i != 0;
        float f = this.animDistance;
        float f2 = i - this.qqsHeight;
        if (0.0f >= f2) {
            f2 = 0.0f;
        }
        if (f <= f2) {
            f2 = f;
        }
        float f3 = f2 / f;
        float f4 = 0.0f < f3 ? f3 : 0.0f;
        if (1.0f <= f4) {
            f4 = 1.0f;
        }
        setPosition(f4);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        float f = shadeExpansionChangeEvent.fraction;
        if (f == 1.0f) {
            updateViews();
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
        Log.d("ImmersiveScrollAnimator", "mPanelSplitEnabled changed");
        updateViews();
        updateAnimators();
    }

    public final void setPosition(float f) {
        Log.d("ImmersiveScrollAnimator", "setPosition fraction = " + f);
        TouchAnimator touchAnimator = this.plmnAlphaAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
        TouchAnimator touchAnimator2 = this.bigClockAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f);
        }
        TouchAnimator touchAnimator3 = this.bigDateAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f);
        }
        this.scrolledPosition = f;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = (QSImpl) qs;
        updateViews();
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        View view;
        View view2;
        View view3;
        View view4;
        View view5;
        if (super.isThereNoView() || this.secQsUiDisplayModeInteractor.isTablet()) {
            return;
        }
        int[] iArr = this.bigClockLoc;
        iArr[0] = 0;
        iArr[1] = 0;
        int[] iArr2 = this.plmnLoc;
        iArr2[0] = 0;
        iArr2[1] = 0;
        QSAnimView qSAnimView = this.bigClock;
        if (qSAnimView != null && (view5 = qSAnimView.getView()) != null) {
            getRelativePosition$1(view5, iArr);
        }
        QSAnimView qSAnimView2 = this.plmn;
        if (qSAnimView2 != null && (view4 = qSAnimView2.getView()) != null) {
            getRelativePosition$1(view4, iArr2);
        }
        float dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) / this.context.getResources().getDimensionPixelSize(R.dimen.clock_text_size);
        QSAnimView qSAnimView3 = this.bigClock;
        float width = (qSAnimView3 == null || (view3 = qSAnimView3.getView()) == null) ? 0 : view3.getWidth();
        float f = width - (width * dimensionPixelSize);
        QSAnimView qSAnimView4 = this.bigClock;
        float height = (qSAnimView4 == null || (view2 = qSAnimView4.getView()) == null) ? 0 : view2.getHeight();
        float f2 = height * dimensionPixelSize;
        QSAnimView qSAnimView5 = this.plmn;
        float measuredHeight = (height - f2) - ((((qSAnimView5 == null || (view = qSAnimView5.getView()) == null) ? 0 : view.getMeasuredHeight()) - f2) * 0.5f);
        float f3 = (iArr2[0] - iArr[0]) - (f / 2.0f);
        float f4 = (iArr2[1] - iArr[1]) - (measuredHeight / 2.0f);
        QSAnimView qSAnimView6 = this.plmn;
        if (qSAnimView6 != null) {
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            builder.addFloat(qSAnimView6, "alpha", 1.0f, 0.0f);
            builder.mEndDelay = 0.8f;
            this.plmnAlphaAnimator = builder.build();
        }
        QSAnimView qSAnimView7 = this.bigClock;
        if (qSAnimView7 != null) {
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(qSAnimView7, "translationY", 0.0f, f4);
            builder2.addFloat(qSAnimView7, "scaleX", 1.0f, dimensionPixelSize);
            builder2.addFloat(qSAnimView7, "scaleY", 1.0f, dimensionPixelSize);
            this.bigClockAnimator = builder2.build();
        }
        QSAnimView qSAnimView8 = this.bigDate;
        if (qSAnimView8 != null) {
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(qSAnimView8, "alpha", 1.0f, 0.0f);
            builder3.mEndDelay = 0.8f;
            this.bigDateAnimator = builder3.build();
        }
        if (this.preXDiff == f3 && this.preYDiff == f4) {
            return;
        }
        this.preXDiff = f3;
        this.preYDiff = f4;
        float f5 = this.scrolledPosition;
        if (f5 == 0.0f) {
            return;
        }
        setPosition(f5);
    }

    public final void updateViews() {
        View view;
        View view2;
        QSAnimViewProvider.ViewType viewType = QSAnimViewProvider.ViewType.QS_HEADER;
        QSAnimViewProvider qSAnimViewProvider = this.viewProvider;
        QSAnimView qSAnimView = qSAnimViewProvider.get(viewType);
        QSAnimView qSAnimView2 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_QQS);
        this.bigClock = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_CLOCK);
        this.bigDate = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_DATE);
        this.plmn = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PLMN);
        SecPanelSplitHelper.Companion.getClass();
        int height = 0;
        this.qqsHeight = (SecPanelSplitHelper.isEnabled || qSAnimView2 == null || (view = qSAnimView2.getView()) == null) ? 0 : view.getHeight();
        if (qSAnimView != null && (view2 = qSAnimView.getView()) != null) {
            height = view2.getHeight();
        }
        this.animDistance = height - this.qqsHeight;
    }
}
