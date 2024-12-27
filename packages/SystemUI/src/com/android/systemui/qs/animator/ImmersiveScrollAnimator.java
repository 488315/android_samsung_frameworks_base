package com.android.systemui.qs.animator;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.ConfigurationState;
import java.util.Collections;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

public final class ImmersiveScrollAnimator extends SecQSImplAnimatorBase {
    public float animDistance;
    public View bigClock;
    public TouchAnimator bigClockAnimator;
    public View bigDate;
    public TouchAnimator bigDateAnimator;
    public final Context context;
    public final ShadeHeaderController headerController;
    public View plmn;
    public TouchAnimator plmnAlphaAnimator;
    public float preXDiff;
    public float preYDiff;
    public float qqsHeight;
    public float scrolledPosition;
    public View smallClock;
    public final int[] bigClockLoc = new int[2];
    public final int[] smallClockLoc = new int[2];
    public final ConfigurationState lastConfigurationState = new ConfigurationState(Collections.singletonList(ConfigurationState.ConfigurationField.ORIENTATION));

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ImmersiveScrollAnimator(Context context, ShadeHeaderController shadeHeaderController) {
        this.context = context;
        this.headerController = shadeHeaderController;
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
        if (isThereNoView()) {
            return;
        }
        Log.d("ImmersiveScrollAnimator", "clearAnimators");
        View view = this.plmn;
        if (view != null) {
            view.setAlpha(1.0f);
        }
        View view2 = this.bigClock;
        if (view2 != null) {
            view2.setAlpha(1.0f);
            view2.setTranslationX(0.0f);
            view2.setTranslationY(0.0f);
            view2.setScaleX(1.0f);
            view2.setScaleY(1.0f);
        }
        View view3 = this.bigDate;
        if (view3 == null) {
            return;
        }
        view3.setAlpha(1.0f);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        this.mQs = null;
        this.bigClock = null;
        this.bigDate = null;
        this.smallClock = null;
        this.plmn = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        View view;
        ConfigurationState configurationState = this.lastConfigurationState;
        if (!configurationState.needToUpdate(configuration) || configuration == null) {
            return;
        }
        QS qs = this.mQs;
        if (qs != null && (view = qs.getView()) != null) {
            view.post(new Runnable() { // from class: com.android.systemui.qs.animator.ImmersiveScrollAnimator$onConfigurationChanged$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    ImmersiveScrollAnimator.this.updateAnimators();
                }
            });
        }
        configurationState.update(configuration);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onNotificationScrolled(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onNotificationScrolled : ", "ImmersiveScrollAnimator");
        QsAnimatorState.isNotificationImmersiceScrolling = i != 0;
        setPosition$1(RangesKt___RangesKt.coerceAtMost(1.0f, RangesKt___RangesKt.coerceAtLeast(0.0f, RangesKt___RangesKt.coerceAtMost(this.animDistance, RangesKt___RangesKt.coerceAtLeast(0.0f, i - this.qqsHeight)) / this.animDistance)));
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
        Log.d("ImmersiveScrollAnimator", "mPanelSplitEnabled changed");
        updateViews$1();
        updateAnimators();
    }

    public final void setPosition$1(float f) {
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
        this.mQs = qs;
        updateViews$1();
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        if (isThereNoView()) {
            return;
        }
        int[] iArr = this.bigClockLoc;
        iArr[0] = 0;
        iArr[1] = 0;
        int[] iArr2 = this.smallClockLoc;
        iArr2[0] = 0;
        iArr2[1] = 0;
        View view = this.bigClock;
        if (view != null) {
            getRelativePosition$1(view, iArr);
        }
        View view2 = this.smallClock;
        if (view2 != null) {
            getRelativePosition$1(view2, iArr2);
        }
        float dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) / this.context.getResources().getDimensionPixelSize(R.dimen.clock_text_size);
        View view3 = this.bigClock;
        float width = view3 != null ? view3.getWidth() : 0;
        float f = width - (width * dimensionPixelSize);
        View view4 = this.bigClock;
        float height = view4 != null ? view4.getHeight() : 0;
        float f2 = (iArr2[0] - iArr[0]) - (f / 2.0f);
        float f3 = (iArr2[1] - iArr[1]) - ((height - (height * dimensionPixelSize)) / 2.0f);
        View view5 = this.plmn;
        if (view5 != null) {
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            builder.addFloat(view5, "alpha", 1.0f, 0.0f);
            builder.mEndDelay = 0.8f;
            this.plmnAlphaAnimator = builder.build();
        }
        View view6 = this.bigClock;
        if (view6 != null) {
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(view6, "translationY", 0.0f, f3);
            builder2.addFloat(view6, "scaleX", 1.0f, dimensionPixelSize);
            builder2.addFloat(view6, "scaleY", 1.0f, dimensionPixelSize);
            this.bigClockAnimator = builder2.build();
        }
        View view7 = this.bigDate;
        if (view7 != null) {
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(view7, "alpha", 1.0f, 0.0f);
            builder3.mEndDelay = 0.8f;
            this.bigDateAnimator = builder3.build();
        }
        if (this.preXDiff == f2 && this.preYDiff == f3) {
            return;
        }
        this.preXDiff = f2;
        this.preYDiff = f3;
        float f4 = this.scrolledPosition;
        if (f4 == 0.0f) {
            return;
        }
        setPosition$1(f4);
    }

    public final void updateViews$1() {
        QS qs = this.mQs;
        View view = null;
        View header = qs != null ? qs.getHeader() : null;
        if (header != null) {
            this.bigClock = header.findViewById(R.id.header_clock);
            this.bigDate = header.findViewById(R.id.header_date);
            view = header.findViewById(R.id.quick_qs_panel);
        }
        ShadeHeaderController shadeHeaderController = this.headerController;
        this.smallClock = shadeHeaderController.header.findViewById(R.id.carrier_group);
        this.plmn = shadeHeaderController.header.findViewById(R.id.anim_view);
        SecPanelSplitHelper.Companion.getClass();
        this.qqsHeight = (SecPanelSplitHelper.isEnabled || view == null) ? 0 : view.getHeight();
        this.animDistance = (header != null ? header.getHeight() : 0) - this.qqsHeight;
    }
}
