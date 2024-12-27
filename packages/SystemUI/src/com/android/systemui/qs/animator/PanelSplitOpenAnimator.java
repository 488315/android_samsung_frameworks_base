package com.android.systemui.qs.animator;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.BottomLargeTileBar;
import com.android.systemui.qs.bar.BrightnessVolumeBar;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.qs.bar.QuickControlBar;
import com.android.systemui.qs.bar.SecurityFooterBar;
import com.android.systemui.qs.bar.SmartViewLargeTileBar;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PanelSplitOpenAnimator extends SecQSImplAnimatorBase {
    public final BarController barController;
    public final BarOrderInteractor barOrderInteractor;
    public View bottomLargeTileBarView;
    public View brightnessVolumeBarView;
    public TouchAnimator buttonAnimator;
    public View buttonContainer;
    public TouchAnimator clockDateAnimator;
    public View clockDateContainer;
    public TouchAnimator containerViewAnimator;
    public final Context context;
    public MotionLayout header;
    public TouchAnimator headerAnimator;
    public final ShadeHeaderController headerController;
    public View multiSimBarView;
    public float openPosition;
    public float overExpansionAmount;
    public final float overExpansionMaxAmount;
    public TouchAnimator panelBarAnimator;
    public View panelMediaPlayerBarView;
    public View panelQuickControlBarView;
    public View securityFooterBarView;
    public View smartViewLargeTileBarView;
    public View tileChunkLayoutBarView;
    public View topLargeTileBarView;
    public View videoCallMicModeBarView;
    public final ArrayList panelBarViews = new ArrayList();
    public final ArrayList overExpansionBarAnimators = new ArrayList();
    public final float barYDiff = 300.0f;
    public final float buttonYDiff = 50.0f;
    public final float xDiff = 100.0f;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public PanelSplitOpenAnimator(Context context, ShadeHeaderController shadeHeaderController, BarOrderInteractor barOrderInteractor, BarController barController) {
        this.context = context;
        this.headerController = shadeHeaderController;
        this.barOrderInteractor = barOrderInteractor;
        this.barController = barController;
        this.overExpansionMaxAmount = context.getResources().getDimension(R.dimen.panel_overshoot_amount) * 1.5f;
    }

    public static final View updateViews$lambda$1$toBarView(BarItemImpl barItemImpl) {
        View view = barItemImpl.mBarRootView;
        if (view != null) {
            return view.findViewWithTag("expand_anim");
        }
        return null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        View view;
        Log.d("PanelSplitOpenAnimator", "clearAnimationState");
        if (isThereNoView()) {
            return;
        }
        MotionLayout motionLayout = this.header;
        if (motionLayout != null) {
            motionLayout.setAlpha(QsAnimatorState.state == 1 ? 0.0f : 1.0f);
            motionLayout.setTranslationY(0.0f);
        }
        View view2 = this.clockDateContainer;
        if (view2 != null) {
            view2.setAlpha(1.0f);
            view2.setTranslationY(0.0f);
            view2.setScaleX(1.0f);
            view2.setScaleY(1.0f);
        }
        View view3 = this.buttonContainer;
        if (view3 != null) {
            view3.setAlpha(1.0f);
            view3.setTranslationX(0.0f);
            view3.setTranslationY(0.0f);
            view3.setScaleX(1.0f);
            view3.setScaleY(1.0f);
        }
        QS qs = this.mQs;
        if (qs != null && (view = qs.getView()) != null) {
            view.setAlpha(1.0f);
            view.setTranslationY(0.0f);
        }
        Iterator it = this.panelBarViews.iterator();
        while (it.hasNext()) {
            View view4 = (View) it.next();
            view4.setAlpha(1.0f);
            view4.setScaleX(1.0f);
            view4.setScaleY(1.0f);
        }
        int i = this.context.getResources().getConfiguration().orientation;
        BarOrderInteractor barOrderInteractor = this.barOrderInteractor;
        Iterator it2 = (2 == i ? barOrderInteractor.landscapeBars : barOrderInteractor.getBarViewsByOrder()).iterator();
        while (it2.hasNext()) {
            ((View) it2.next()).setTranslationY(0.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        if (!super.isThereNoView()) {
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && !QsAnimatorState.isDetailShowing) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed() {
        clearAnimationState();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        if (isThereNoView()) {
            return;
        }
        int i = QsAnimatorState.state;
        StringBuilder sb = new StringBuilder("onPanelExpansionChanged : ");
        float f = shadeExpansionChangeEvent.fraction;
        sb.append(f);
        sb.append(" state : ");
        sb.append(i);
        Log.d("PanelSplitOpenAnimator", sb.toString());
        if (QsAnimatorState.state != 1) {
            setPosition$2(f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        int i = this.mPanelState;
        int i2 = panelTransitionStateChangeEvent.state;
        if (i != i2) {
            this.mPanelState = i2;
        }
        boolean z = this.mPanelSplitEnabled;
        boolean z2 = panelTransitionStateChangeEvent.enabled;
        if (z != z2) {
            this.mPanelSplitEnabled = z2;
            if (z2) {
                updateAnimators();
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        QsAnimatorState.state = i;
        if (i == 1) {
            clearAnimationState();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        this.mUserChanged = true;
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            setPosition$2(1.0f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setOverScrollAmount(float f) {
        if (isThereNoView()) {
            return;
        }
        Log.d("PanelSplitOpenAnimator", "setOverScrollAmount : " + f);
        this.overExpansionAmount = f;
        float f2 = f / this.overExpansionMaxAmount;
        Iterator it = this.overExpansionBarAnimators.iterator();
        while (it.hasNext()) {
            ((TouchAnimator) it.next()).setPosition(f2);
        }
    }

    public final void setPosition$2(float f) {
        boolean z = this.mUserChanged;
        this.mUserChanged = false;
        boolean isThereNoView = isThereNoView();
        boolean z2 = QsAnimatorState.isSliding;
        float f2 = this.overExpansionAmount;
        StringBuilder sb = new StringBuilder("setPosition : ");
        sb.append(f);
        sb.append(" // ");
        sb.append(!z);
        sb.append(" ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, isThereNoView, " ", z2, " ");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, f2, "PanelSplitOpenAnimator");
        if ((!z && isThereNoView()) || QsAnimatorState.isSliding || QsAnimatorState.isDetailShowing) {
            return;
        }
        if (this.openPosition != 1.0f || this.overExpansionAmount <= 0.0f) {
            this.openPosition = f;
            TouchAnimator touchAnimator = this.headerAnimator;
            if (touchAnimator != null) {
                touchAnimator.setPosition(f);
            }
            TouchAnimator touchAnimator2 = this.clockDateAnimator;
            if (touchAnimator2 != null) {
                touchAnimator2.setPosition(f);
            }
            TouchAnimator touchAnimator3 = this.buttonAnimator;
            if (touchAnimator3 != null) {
                touchAnimator3.setPosition(f);
            }
            TouchAnimator touchAnimator4 = this.containerViewAnimator;
            if (touchAnimator4 != null) {
                touchAnimator4.setPosition(f);
            }
            TouchAnimator touchAnimator5 = this.panelBarAnimator;
            if (touchAnimator5 != null) {
                touchAnimator5.setPosition(f);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            return;
        }
        this.mQs = qs;
        this.header = this.headerController.header;
        View view = qs.getView();
        if (view != null) {
            this.clockDateContainer = view.findViewById(R.id.clock_parent);
            this.buttonContainer = view.findViewById(R.id.header_settings_container);
        }
        BarType barType = BarType.TOP_LARGE_TILE;
        BarController barController = this.barController;
        BarItemImpl barInExpanded = barController.getBarInExpanded(barType);
        TopLargeTileBar topLargeTileBar = barInExpanded instanceof TopLargeTileBar ? (TopLargeTileBar) barInExpanded : null;
        this.topLargeTileBarView = topLargeTileBar != null ? updateViews$lambda$1$toBarView(topLargeTileBar) : null;
        BarItemImpl barInExpanded2 = barController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
        TileChunkLayoutBar tileChunkLayoutBar = barInExpanded2 instanceof TileChunkLayoutBar ? (TileChunkLayoutBar) barInExpanded2 : null;
        this.tileChunkLayoutBarView = tileChunkLayoutBar != null ? updateViews$lambda$1$toBarView(tileChunkLayoutBar) : null;
        BarItemImpl barInExpanded3 = barController.getBarInExpanded(BarType.VIDEO_CALL_MIC_MODE);
        VideoCallMicModeBar videoCallMicModeBar = barInExpanded3 instanceof VideoCallMicModeBar ? (VideoCallMicModeBar) barInExpanded3 : null;
        this.videoCallMicModeBarView = videoCallMicModeBar != null ? updateViews$lambda$1$toBarView(videoCallMicModeBar) : null;
        BarItemImpl barInExpanded4 = barController.getBarInExpanded(BarType.MULTI_SIM_PREFERRED_SLOT);
        MultiSIMPreferredSlotBar multiSIMPreferredSlotBar = barInExpanded4 instanceof MultiSIMPreferredSlotBar ? (MultiSIMPreferredSlotBar) barInExpanded4 : null;
        this.multiSimBarView = multiSIMPreferredSlotBar != null ? updateViews$lambda$1$toBarView(multiSIMPreferredSlotBar) : null;
        BarItemImpl barInExpanded5 = barController.getBarInExpanded(BarType.BRIGHTNESS_VOLUME);
        BrightnessVolumeBar brightnessVolumeBar = barInExpanded5 instanceof BrightnessVolumeBar ? (BrightnessVolumeBar) barInExpanded5 : null;
        this.brightnessVolumeBarView = brightnessVolumeBar != null ? updateViews$lambda$1$toBarView(brightnessVolumeBar) : null;
        BarItemImpl barInExpanded6 = barController.getBarInExpanded(BarType.QS_MEDIA_PLAYER);
        QSMediaPlayerBar qSMediaPlayerBar = barInExpanded6 instanceof QSMediaPlayerBar ? (QSMediaPlayerBar) barInExpanded6 : null;
        this.panelMediaPlayerBarView = qSMediaPlayerBar != null ? updateViews$lambda$1$toBarView(qSMediaPlayerBar) : null;
        BarItemImpl barInExpanded7 = barController.getBarInExpanded(BarType.QUICK_CONTROL);
        QuickControlBar quickControlBar = barInExpanded7 instanceof QuickControlBar ? (QuickControlBar) barInExpanded7 : null;
        this.panelQuickControlBarView = quickControlBar != null ? updateViews$lambda$1$toBarView(quickControlBar) : null;
        BarItemImpl barInExpanded8 = barController.getBarInExpanded(BarType.BOTTOM_LARGE_TILE);
        BottomLargeTileBar bottomLargeTileBar = barInExpanded8 instanceof BottomLargeTileBar ? (BottomLargeTileBar) barInExpanded8 : null;
        this.bottomLargeTileBarView = bottomLargeTileBar != null ? updateViews$lambda$1$toBarView(bottomLargeTileBar) : null;
        BarItemImpl barInExpanded9 = barController.getBarInExpanded(BarType.SMARTVIEW_LARGE_TILE);
        SmartViewLargeTileBar smartViewLargeTileBar = barInExpanded9 instanceof SmartViewLargeTileBar ? (SmartViewLargeTileBar) barInExpanded9 : null;
        this.smartViewLargeTileBarView = smartViewLargeTileBar != null ? updateViews$lambda$1$toBarView(smartViewLargeTileBar) : null;
        BarItemImpl barInExpanded10 = barController.getBarInExpanded(BarType.SECURITY_FOOTER);
        SecurityFooterBar securityFooterBar = barInExpanded10 instanceof SecurityFooterBar ? (SecurityFooterBar) barInExpanded10 : null;
        this.securityFooterBarView = securityFooterBar != null ? updateViews$lambda$1$toBarView(securityFooterBar) : null;
        updateAnimators();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setTransitionToFullShadeAmount(float f) {
        if (!isThereNoView() && QsAnimatorState.state == 1) {
            Log.d("PanelSplitOpenAnimator", "setTransitionToFullShadeAmount : " + f);
            setPosition$2(f);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        float f;
        View view;
        if (isThereNoView()) {
            return;
        }
        Log.d("PanelSplitOpenAnimator", "updateAnimators");
        MotionLayout motionLayout = this.header;
        float f2 = this.buttonYDiff;
        if (motionLayout != null) {
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            builder.addFloat(motionLayout, "alpha", 0.0f, 1.0f);
            builder.addFloat(motionLayout, "translationY", -f2, 0.0f);
            this.headerAnimator = builder.build();
        }
        View view2 = this.clockDateContainer;
        float f3 = this.xDiff;
        float f4 = this.barYDiff;
        if (view2 != null) {
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(view2, "alpha", 0.0f, 1.0f);
            f = f3;
            builder2.addFloat(view2, "translationX", -f3, 0.0f);
            builder2.addFloat(view2, "translationY", f4 - f2, 0.0f);
            builder2.addFloat(view2, "scaleX", 0.8f, 1.0f);
            builder2.addFloat(view2, "scaleY", 0.8f, 1.0f);
            builder2.mStartDelay = 0.2f;
            this.clockDateAnimator = builder2.build();
        } else {
            f = f3;
        }
        View view3 = this.buttonContainer;
        if (view3 != null) {
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(view3, "alpha", 0.0f, 1.0f);
            builder3.addFloat(view3, "translationX", f, 0.0f);
            builder3.addFloat(view3, "translationY", f4 - f2, 0.0f);
            builder3.addFloat(view3, "scaleX", 0.8f, 1.0f);
            builder3.addFloat(view3, "scaleY", 0.8f, 1.0f);
            builder3.mStartDelay = 0.2f;
            this.buttonAnimator = builder3.build();
        }
        QS qs = this.mQs;
        if (qs != null && (view = qs.getView()) != null) {
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(view, "translationY", -f4, 0.0f);
            builder4.mStartDelay = 0.2f;
            this.containerViewAnimator = builder4.build();
        }
        ArrayList arrayList = this.panelBarViews;
        arrayList.clear();
        View view4 = this.topLargeTileBarView;
        if (view4 != null) {
            arrayList.add(view4);
        }
        View view5 = this.tileChunkLayoutBarView;
        if (view5 != null) {
            arrayList.add(view5);
        }
        View view6 = this.videoCallMicModeBarView;
        if (view6 != null) {
            arrayList.add(view6);
        }
        View view7 = this.multiSimBarView;
        if (view7 != null) {
            arrayList.add(view7);
        }
        View view8 = this.brightnessVolumeBarView;
        if (view8 != null) {
            arrayList.add(view8);
        }
        View view9 = this.panelMediaPlayerBarView;
        if (view9 != null) {
            arrayList.add(view9);
        }
        View view10 = this.panelQuickControlBarView;
        if (view10 != null) {
            arrayList.add(view10);
        }
        View view11 = this.bottomLargeTileBarView;
        if (view11 != null) {
            arrayList.add(view11);
        }
        View view12 = this.smartViewLargeTileBarView;
        if (view12 != null) {
            arrayList.add(view12);
        }
        View view13 = this.securityFooterBarView;
        if (view13 != null) {
            arrayList.add(view13);
        }
        TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
        Iterator it = this.panelBarViews.iterator();
        while (it.hasNext()) {
            View view14 = (View) it.next();
            builder5.addFloat(view14, "alpha", 0.0f, 1.0f);
            builder5.addFloat(view14, "scaleX", 0.93f, 1.0f);
            builder5.addFloat(view14, "scaleY", 0.93f, 1.0f);
            builder5.mStartDelay = 0.5f;
            builder5.build();
        }
        this.panelBarAnimator = builder5.build();
        this.overExpansionBarAnimators.clear();
        int i = this.context.getResources().getConfiguration().orientation;
        BarOrderInteractor barOrderInteractor = this.barOrderInteractor;
        int i2 = 0;
        for (Object obj : 2 == i ? barOrderInteractor.landscapeBars : barOrderInteractor.getBarViewsByOrder()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            View findViewWithTag = ((View) obj).findViewWithTag("expand_anim");
            if (findViewWithTag != null) {
                TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
                builder6.addFloat(findViewWithTag, "translationY", 0.0f, i3 * 20);
                this.overExpansionBarAnimators.add(builder6.build());
            }
            i2 = i3;
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
    }
}
