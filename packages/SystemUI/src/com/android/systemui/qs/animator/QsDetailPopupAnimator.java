package com.android.systemui.qs.animator;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSDetail;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.BottomLargeTileBar;
import com.android.systemui.qs.bar.BrightnessVolumeBar;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.qs.bar.QuickControlBar;
import com.android.systemui.qs.bar.SecurityFooterBar;
import com.android.systemui.qs.bar.SmartViewLargeTileBar;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.tileimpl.LargeTileView;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

public final class QsDetailPopupAnimator extends SecQSImplAnimatorBase {
    public int anchorBottomOnStart;
    public int anchorHeightOnStart;
    public final QsDetailPopupAnimator$anchorLayoutChangedListener$1 anchorLayoutChangedListener;
    public int anchorLeftOnStart;
    public int anchorRightOnStart;
    public int anchorTopOnStart;
    public View anchorView;
    public int anchorWidthOnStart;
    public SecQSImplAnimatorManager.AnonymousClass2 animStateCallback;
    public final BarController barController;
    public BottomLargeTileBar bottomLargeTileBar;
    public View bottomLargeTileBarView;
    public BrightnessVolumeBar brightnessVolumeBar;
    public View brightnessVolumeBarView;
    public QSTileView btTileView;
    public View buttonContainer;
    public View chunkTileBarView;
    public final ColoredBGHelper coloredBGHelper;
    public final Context context;
    public SecQSDetail detail;
    public QsTransitionAnimator.DetailCallback detailCallback;
    public View detailContent;
    public View detailHeaderText;
    public View detailSummary;
    public final SecQSDetailController detailcontroller;
    public float expandedFraction;
    public QSMediaPlayerBar expandedMediaPlayerBar;
    public int heightDiff;
    public SpringAnimation hideDetailSpringAnimator;
    public boolean isAnimating;
    public View mediaPlayerBarView;
    public MultiSIMPreferredSlotBar multiSIMBar;
    public View multiSIMBarView;
    public View networkSpeedContainer;
    public TouchAnimator panelClosingAnchorHidingAnimator;
    public View plmn;
    public View privacyContainer;
    public View qsPanel;
    public final QsTransitionAnimator qsTransitionAnimator;
    public QuickControlBar quickControlBar;
    public View quickControlBarView;
    public View quickHeader;
    public SecurityFooterBar securityFooterBar;
    public View securityFooterBarView;
    public final ShadeHeaderController shadeHeaderController;
    public SpringAnimation showDetailSpringAnimator;
    public View smartLargeTileBarView;
    public SmartViewLargeTileBar smartViewLargeTileBar;
    public final ArrayList springAnimList;
    public View systemIconContainer;
    public int targetHeightOnStart;
    public final QsDetailPopupAnimator$targetLayoutChangedListener$1 targetLayoutChangedListener;
    public int targetLeftOnStart;
    public View targetView;
    public int targetWidthOnStart;
    public float textXDiff;
    public float textYDiff;
    public TileChunkLayoutBar tileChunkLayoutBar;
    public TopLargeTileBar topLargeTileBar;
    public VideoCallMicModeBar videoCallMicModeBar;
    public View videoCallMicModeBarView;
    public int widthDiff;
    public QSTileView wifiTileView;
    public float xDiff;
    public float yDiff;
    public final ConfigurationState lastConfigurationState = new ConfigurationState(Collections.singletonList(ConfigurationState.ConfigurationField.ORIENTATION));
    public int[] anchorLocationOnStart = new int[2];
    public final ArrayList anchorChildren = new ArrayList();
    public int[] targetLocation = new int[2];
    public final ArrayList targetChildren = new ArrayList();
    public final ArrayList panelViewList = new ArrayList();

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

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$anchorLayoutChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$targetLayoutChangedListener$1] */
    public QsDetailPopupAnimator(Context context, SecQSDetailController secQSDetailController, BarController barController, ColoredBGHelper coloredBGHelper, ShadeHeaderController shadeHeaderController, QsTransitionAnimator qsTransitionAnimator) {
        this.context = context;
        this.detailcontroller = secQSDetailController;
        this.barController = barController;
        this.coloredBGHelper = coloredBGHelper;
        this.shadeHeaderController = shadeHeaderController;
        this.qsTransitionAnimator = qsTransitionAnimator;
        new ArrayList();
        this.springAnimList = new ArrayList();
        this.anchorLayoutChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$anchorLayoutChangedListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (!(i == i5 && i3 == i7 && i2 == i6 && i4 == i8) && QsAnimatorState.isDetailShowing) {
                    QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
                    view.setLeft(qsDetailPopupAnimator.anchorLeftOnStart - (qsDetailPopupAnimator.widthDiff / 2));
                    QsDetailPopupAnimator qsDetailPopupAnimator2 = QsDetailPopupAnimator.this;
                    view.setRight((qsDetailPopupAnimator2.widthDiff / 2) + qsDetailPopupAnimator2.anchorRightOnStart);
                    QsDetailPopupAnimator qsDetailPopupAnimator3 = QsDetailPopupAnimator.this;
                    view.setTop(qsDetailPopupAnimator3.anchorTopOnStart - (qsDetailPopupAnimator3.heightDiff / 2));
                    QsDetailPopupAnimator qsDetailPopupAnimator4 = QsDetailPopupAnimator.this;
                    view.setBottom((qsDetailPopupAnimator4.heightDiff / 2) + qsDetailPopupAnimator4.anchorBottomOnStart);
                }
            }
        };
        this.targetLayoutChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$targetLayoutChangedListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i == i5 && i3 == i7 && i2 == i6 && i4 == i8) {
                    return;
                }
                QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
                if (qsDetailPopupAnimator.isAnimating) {
                    Iterator it = qsDetailPopupAnimator.springAnimList.iterator();
                    while (it.hasNext()) {
                        SpringAnimation springAnimation = (SpringAnimation) it.next();
                        if (springAnimation != null) {
                            springAnimation.cancel();
                        }
                    }
                    QsDetailPopupAnimator qsDetailPopupAnimator2 = QsDetailPopupAnimator.this;
                    qsDetailPopupAnimator2.transitionDetail(qsDetailPopupAnimator2.anchorView, QsAnimatorState.isDetailPopupShowing);
                }
            }
        };
    }

    public static int[] getCenterPositionOnScreen(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return new int[]{(view.getWidth() / 2) + iArr[0], (view.getHeight() / 2) + iArr[1]};
    }

    public final void aimingTarget() {
        View view = this.anchorView;
        if (view != null) {
            this.anchorLocationOnStart = getCenterPositionOnScreen(view);
        }
        View view2 = this.anchorView;
        this.anchorWidthOnStart = view2 != null ? view2.getWidth() : 0;
        View view3 = this.anchorView;
        this.anchorHeightOnStart = view3 != null ? view3.getHeight() : 0;
        View view4 = this.anchorView;
        this.anchorLeftOnStart = view4 != null ? view4.getLeft() : 0;
        View view5 = this.anchorView;
        this.anchorTopOnStart = view5 != null ? view5.getTop() : 0;
        View view6 = this.anchorView;
        this.anchorRightOnStart = view6 != null ? view6.getRight() : 0;
        View view7 = this.anchorView;
        this.anchorBottomOnStart = view7 != null ? view7.getBottom() : 0;
        if (this.anchorView instanceof LargeTileView) {
            View view8 = this.targetView;
            if (view8 != null) {
                this.targetLocation = getCenterPositionOnScreen(view8);
            }
            View view9 = this.targetView;
            this.targetWidthOnStart = view9 != null ? view9.getWidth() : 0;
            View view10 = this.targetView;
            this.targetHeightOnStart = view10 != null ? view10.getHeight() : 0;
            View view11 = this.targetView;
            this.targetLeftOnStart = view11 != null ? view11.getLeft() : 0;
            View view12 = this.targetView;
            if (view12 != null) {
                view12.getTop();
            }
            View view13 = this.targetView;
            if (view13 != null) {
                view13.getRight();
            }
            View view14 = this.targetView;
            if (view14 != null) {
                view14.getBottom();
            }
        } else {
            this.targetWidthOnStart = (int) (DeviceState.getDisplayWidth(this.context) * 0.917f);
            this.targetHeightOnStart = (int) (DeviceState.getDisplayHeight(this.context) * 0.65f);
            this.targetLeftOnStart = (int) (DeviceState.getDisplayWidth(this.context) * 0.0415f);
            int i = this.targetLeftOnStart;
            int i2 = this.targetWidthOnStart;
            int i3 = this.targetHeightOnStart;
            int[] iArr = this.targetLocation;
            iArr[0] = (i2 / 2) + i;
            iArr[1] = (i3 / 2) + ((int) (DeviceState.getDisplayHeight(this.context) * 0.175f));
        }
        int[] iArr2 = this.targetLocation;
        int i4 = iArr2[0];
        int[] iArr3 = this.anchorLocationOnStart;
        float f = i4 - iArr3[0];
        this.xDiff = f;
        int i5 = iArr2[1];
        int i6 = iArr3[1];
        this.yDiff = i5 - i6;
        this.textXDiff = f;
        this.textYDiff = (i5 - (this.targetHeightOnStart / 2.0f)) - i6;
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i4, i5, "targetLocation[0] ", "   targetLocation[1] ", "QsPopupAnimator");
        int i7 = this.targetWidthOnStart - this.anchorWidthOnStart;
        this.widthDiff = i7;
        int i8 = this.targetHeightOnStart - this.anchorHeightOnStart;
        this.heightDiff = i8;
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CubicBezierEasing$$ExternalSyntheticOutline0.m("aimingTarget ", this.xDiff, " ", this.yDiff, " "), i7, " ", i8, "QsPopupAnimator");
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        View view = this.anchorView;
        if (view != null) {
            view.setAlpha(1.0f);
            view.setLeft(this.anchorLeftOnStart);
            view.setRight(this.anchorRightOnStart);
            view.setTop(this.anchorTopOnStart);
            view.setBottom(this.anchorBottomOnStart);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
        }
        Iterator it = this.anchorChildren.iterator();
        while (it.hasNext()) {
            ((View) it.next()).setAlpha(1.0f);
        }
        Iterator it2 = this.panelViewList.iterator();
        while (it2.hasNext()) {
            View view2 = (View) it2.next();
            view2.setAlpha(1.0f);
            view2.setScaleX(1.0f);
            view2.setScaleY(1.0f);
        }
        SecQSDetail secQSDetail = this.detail;
        if (secQSDetail != null) {
            secQSDetail.setAlpha(1.0f);
        }
        View view3 = this.qsPanel;
        if (view3 != null) {
            view3.setAlpha(1.0f);
        }
        View view4 = this.targetView;
        if (view4 != null) {
            view4.setTranslationX(0.0f);
            view4.setTranslationY(0.0f);
        }
        Iterator it3 = this.targetChildren.iterator();
        while (it3.hasNext()) {
            View view5 = (View) it3.next();
            view5.setScaleX(1.0f);
            view5.setScaleY(1.0f);
        }
        View view6 = this.detailHeaderText;
        if (view6 != null) {
            view6.setScaleX(1.0f);
            view6.setScaleY(1.0f);
            view6.setTranslationX(0.0f);
            view6.setTranslationY(0.0f);
        }
        View view7 = this.detailSummary;
        if (view7 != null) {
            view7.setScaleX(1.0f);
            view7.setScaleY(1.0f);
            view7.setTranslationX(0.0f);
            view7.setTranslationY(0.0f);
        }
        View view8 = this.systemIconContainer;
        if (view8 != null) {
            view8.setAlpha(1.0f);
        }
        this.anchorView = null;
        this.targetView = null;
        this.showDetailSpringAnimator = null;
        this.hideDetailSpringAnimator = null;
        this.panelClosingAnchorHidingAnimator = null;
        QsAnimatorState.isDetailPopupShowing = false;
        View view9 = this.detailContent;
        ColoredBGHelper coloredBGHelper = this.coloredBGHelper;
        coloredBGHelper.setBackGroundDrawable(view9, coloredBGHelper.getBGColor());
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        View view = this.anchorView;
        if (view != null) {
            view.removeOnLayoutChangeListener(this.anchorLayoutChangedListener);
        }
        View view2 = this.detailContent;
        if (view2 != null) {
            view2.removeOnLayoutChangeListener(this.targetLayoutChangedListener);
        }
        this.detailCallback = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationState configurationState = this.lastConfigurationState;
        boolean needToUpdate = configurationState.needToUpdate(configuration);
        if (configuration != null && QsAnimatorState.isDetailPopupShowing && needToUpdate) {
            View view = this.detailContent;
            ColoredBGHelper coloredBGHelper = this.coloredBGHelper;
            coloredBGHelper.setBackGroundDrawable(view, coloredBGHelper.getBGColor());
            aimingTarget();
            clearAnimationState();
            SecQSDetail secQSDetail = this.detail;
            if (secQSDetail != null) {
                secQSDetail.setVisibility(0);
            }
            QsTransitionAnimator qsTransitionAnimator = this.qsTransitionAnimator;
            qsTransitionAnimator.mPanelHideAnimSet.start();
            qsTransitionAnimator.mDetailShowAnimSet.start();
            qsTransitionAnimator.mHeader.setDescendantFocusability(262144);
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed() {
        Iterator it = this.springAnimList.iterator();
        while (it.hasNext()) {
            SpringAnimation springAnimation = (SpringAnimation) it.next();
            if (springAnimation != null) {
                springAnimation.cancel();
            }
        }
        clearAnimationState();
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = this.animStateCallback;
        if (anonymousClass2 != null) {
            anonymousClass2.setDetailOpening(false);
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass22 = this.animStateCallback;
        if (anonymousClass22 != null) {
            anonymousClass22.setDetailShowing(false);
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass23 = this.animStateCallback;
        if (anonymousClass23 != null) {
            anonymousClass23.setDetailClosing(false);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        TouchAnimator touchAnimator;
        float f = this.expandedFraction;
        float f2 = shadeExpansionChangeEvent.fraction;
        if (f == f2) {
            return;
        }
        this.expandedFraction = f2;
        if (!QsAnimatorState.isDetailPopupShowing || (touchAnimator = this.panelClosingAnchorHidingAnimator) == null) {
            return;
        }
        touchAnimator.setPosition(f2);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = qs;
        BarType barType = BarType.BOTTOM_LARGE_TILE;
        BarController barController = this.barController;
        BarItemImpl barInExpanded = barController.getBarInExpanded(barType);
        this.bottomLargeTileBar = barInExpanded instanceof BottomLargeTileBar ? (BottomLargeTileBar) barInExpanded : null;
        BarItemImpl barInExpanded2 = barController.getBarInExpanded(BarType.BRIGHTNESS_VOLUME);
        this.brightnessVolumeBar = barInExpanded2 instanceof BrightnessVolumeBar ? (BrightnessVolumeBar) barInExpanded2 : null;
        BarItemImpl barInExpanded3 = barController.getBarInExpanded(BarType.QS_MEDIA_PLAYER);
        this.expandedMediaPlayerBar = barInExpanded3 instanceof QSMediaPlayerBar ? (QSMediaPlayerBar) barInExpanded3 : null;
        BarItemImpl barInExpanded4 = barController.getBarInExpanded(BarType.MULTI_SIM_PREFERRED_SLOT);
        this.multiSIMBar = barInExpanded4 instanceof MultiSIMPreferredSlotBar ? (MultiSIMPreferredSlotBar) barInExpanded4 : null;
        BarItemImpl barInExpanded5 = barController.getBarInExpanded(BarType.QUICK_CONTROL);
        this.quickControlBar = barInExpanded5 instanceof QuickControlBar ? (QuickControlBar) barInExpanded5 : null;
        BarItemImpl barInExpanded6 = barController.getBarInExpanded(BarType.SECURITY_FOOTER);
        this.securityFooterBar = barInExpanded6 instanceof SecurityFooterBar ? (SecurityFooterBar) barInExpanded6 : null;
        BarItemImpl barInExpanded7 = barController.getBarInExpanded(BarType.SMARTVIEW_LARGE_TILE);
        this.smartViewLargeTileBar = barInExpanded7 instanceof SmartViewLargeTileBar ? (SmartViewLargeTileBar) barInExpanded7 : null;
        BarItemImpl barInExpanded8 = barController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
        this.tileChunkLayoutBar = barInExpanded8 instanceof TileChunkLayoutBar ? (TileChunkLayoutBar) barInExpanded8 : null;
        BarItemImpl barInExpanded9 = barController.getBarInExpanded(BarType.TOP_LARGE_TILE);
        this.topLargeTileBar = barInExpanded9 instanceof TopLargeTileBar ? (TopLargeTileBar) barInExpanded9 : null;
        BarItemImpl barInExpanded10 = barController.getBarInExpanded(BarType.VIDEO_CALL_MIC_MODE);
        this.videoCallMicModeBar = barInExpanded10 instanceof VideoCallMicModeBar ? (VideoCallMicModeBar) barInExpanded10 : null;
        updateViews$3();
    }

    public final void startPopupAnimation(boolean z) {
        Math.sqrt(Math.pow(this.yDiff, 2.0d) + Math.pow(this.xDiff, 2.0d));
        if (z) {
            SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = this.animStateCallback;
            if (anonymousClass2 != null) {
                anonymousClass2.setDetailOpening(true);
            }
            QsAnimatorState.isDetailPopupShowing = true;
            this.isAnimating = true;
            SpringAnimation springAnimation = this.showDetailSpringAnimator;
            if (springAnimation != null) {
                springAnimation.start();
                return;
            }
            return;
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass22 = this.animStateCallback;
        if (anonymousClass22 != null) {
            anonymousClass22.setDetailShowing(false);
        }
        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass23 = this.animStateCallback;
        if (anonymousClass23 != null) {
            anonymousClass23.setDetailClosing(true);
        }
        this.isAnimating = true;
        SpringAnimation springAnimation2 = this.hideDetailSpringAnimator;
        if (springAnimation2 != null) {
            springAnimation2.start();
        }
    }

    public final void transitionDetail(View view, boolean z) {
        Log.d("QsPopupAnimator", "transitionDetail( " + z + " )");
        Log.d("QsPopupAnimator", Debug.getCallers(3, " "));
        if (z) {
            SecQSDetail secQSDetail = this.detail;
            if (secQSDetail != null) {
                secQSDetail.setVisibility(0);
            }
            SecQSDetail secQSDetail2 = this.detail;
            if (secQSDetail2 != null) {
                secQSDetail2.setAlpha(0.0f);
            }
            SecQSDetail secQSDetail3 = this.detail;
            if (secQSDetail3 != null) {
                secQSDetail3.setScaleX(1.0f);
            }
            SecQSDetail secQSDetail4 = this.detail;
            if (secQSDetail4 != null) {
                secQSDetail4.setScaleY(1.0f);
            }
        }
        if (!z) {
            if (z) {
                return;
            }
            startPopupAnimation(false);
            return;
        }
        View view2 = this.anchorView;
        if (view2 != null) {
            view2.setAlpha(1.0f);
            view2.setTranslationX(0.0f);
            view2.setTranslationY(0.0f);
        }
        Iterator it = this.anchorChildren.iterator();
        while (it.hasNext()) {
            ((View) it.next()).setAlpha(1.0f);
        }
        View view3 = this.targetView;
        if (view3 != null) {
            view3.setAlpha(1.0f);
            view3.setTranslationX(0.0f);
            view3.setTranslationY(0.0f);
        }
        SecQSDetail secQSDetail5 = this.detail;
        if (secQSDetail5 != null) {
            secQSDetail5.setTranslationX(0.0f);
            secQSDetail5.setTranslationY(0.0f);
        }
        if (view != null) {
            View view4 = this.anchorView;
            QsDetailPopupAnimator$anchorLayoutChangedListener$1 qsDetailPopupAnimator$anchorLayoutChangedListener$1 = this.anchorLayoutChangedListener;
            if (view4 != null) {
                view4.removeOnLayoutChangeListener(qsDetailPopupAnimator$anchorLayoutChangedListener$1);
            }
            this.anchorView = view;
            if (view instanceof LargeTileView) {
                view.addOnLayoutChangeListener(qsDetailPopupAnimator$anchorLayoutChangedListener$1);
            }
            this.anchorChildren.clear();
            View view5 = this.anchorView;
            ViewGroup viewGroup = view5 instanceof ViewGroup ? (ViewGroup) view5 : null;
            if (viewGroup != null) {
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    this.anchorChildren.add(viewGroup.getChildAt(i));
                }
            }
            View view6 = this.targetView;
            QsDetailPopupAnimator$targetLayoutChangedListener$1 qsDetailPopupAnimator$targetLayoutChangedListener$1 = this.targetLayoutChangedListener;
            if (view6 != null) {
                view6.removeOnLayoutChangeListener(qsDetailPopupAnimator$targetLayoutChangedListener$1);
            }
            SecQSDetail secQSDetail6 = this.detail;
            View findViewWithTag = secQSDetail6 != null ? secQSDetail6.findViewWithTag("target") : null;
            this.targetView = findViewWithTag;
            if (findViewWithTag != null) {
                findViewWithTag.addOnLayoutChangeListener(qsDetailPopupAnimator$targetLayoutChangedListener$1);
            }
            this.targetChildren.clear();
            View view7 = this.targetView;
            ViewGroup viewGroup2 = view7 instanceof ViewGroup ? (ViewGroup) view7 : null;
            if (viewGroup2 != null) {
                int childCount2 = viewGroup2.getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    this.targetChildren.add(viewGroup2.getChildAt(i2));
                }
            }
        }
        aimingTarget();
        updateAnimators();
        startPopupAnimation(true);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        Log.d("QsPopupAnimator", "updateAnimators");
        updateViews$3();
        this.springAnimList.clear();
        final boolean z = this.anchorView instanceof LargeTileView;
        final double hypot = Math.hypot(this.xDiff, this.yDiff);
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder(0.0f));
        this.showDetailSpringAnimator = springAnimation;
        float f = (float) hypot;
        springAnimation.setSpring(new SpringForce().setStiffness(300.0f).setDampingRatio(0.80829036f).setFinalPosition(f));
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$1$1
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                float coerceAtLeast;
                float f4;
                QsDetailPopupAnimator qsDetailPopupAnimator;
                View view;
                float f5 = f2 / ((float) hypot);
                if (z && (view = (qsDetailPopupAnimator = this).anchorView) != null) {
                    view.setTranslationX(qsDetailPopupAnimator.xDiff * f5);
                    view.setTranslationY(qsDetailPopupAnimator.yDiff * f5);
                    view.setLeft((int) (qsDetailPopupAnimator.anchorLeftOnStart - ((qsDetailPopupAnimator.widthDiff / 2) * f5)));
                    view.setRight((int) (((qsDetailPopupAnimator.widthDiff / 2) * f5) + qsDetailPopupAnimator.anchorRightOnStart));
                    view.setTop((int) (qsDetailPopupAnimator.anchorTopOnStart - ((qsDetailPopupAnimator.heightDiff / 2) * f5)));
                    view.setBottom((int) (((qsDetailPopupAnimator.heightDiff / 2) * f5) + qsDetailPopupAnimator.anchorBottomOnStart));
                }
                float f6 = 1.0f - (2.0f * f5);
                float coerceAtLeast2 = RangesKt___RangesKt.coerceAtLeast(0.0f, f6);
                Iterator it = this.anchorChildren.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(coerceAtLeast2);
                }
                float coerceAtLeast3 = RangesKt___RangesKt.coerceAtLeast(0.0f, f6);
                float f7 = 1.0f - (0.1f * f5);
                QsDetailPopupAnimator qsDetailPopupAnimator2 = this;
                Iterator it2 = qsDetailPopupAnimator2.panelViewList.iterator();
                while (it2.hasNext()) {
                    View view2 = (View) it2.next();
                    View view3 = qsDetailPopupAnimator2.anchorView;
                    if ((view3 != null ? view3.hashCode() : 0) != view2.hashCode()) {
                        view2.setAlpha(coerceAtLeast3);
                        view2.setScaleX(f7);
                        view2.setScaleY(f7);
                    }
                }
                if (z) {
                    coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(0.0f, f5 - 0.75f);
                    f4 = 4.0f;
                } else {
                    coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(0.0f, f5 - 0.25f);
                    f4 = 1.3333334f;
                }
                float f8 = coerceAtLeast * f4;
                SecQSDetail secQSDetail = this.detail;
                if (secQSDetail != null) {
                    secQSDetail.setAlpha(f8);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator3 = this;
                float f9 = qsDetailPopupAnimator3.xDiff;
                float f10 = (f9 * f5) + (-f9);
                float f11 = qsDetailPopupAnimator3.yDiff;
                float f12 = (f11 * f5) + (-f11);
                View view4 = qsDetailPopupAnimator3.targetView;
                if (view4 != null) {
                    view4.setTranslationX(f10);
                    view4.setTranslationY(f12);
                }
                float f13 = (f5 * 0.5f) + 0.5f;
                Iterator it3 = this.targetChildren.iterator();
                while (it3.hasNext()) {
                    View view5 = (View) it3.next();
                    view5.setScaleX(f13);
                    view5.setScaleY(f13);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator4 = this;
                float f14 = qsDetailPopupAnimator4.textXDiff;
                float f15 = (f14 * f5) + (-f14);
                float f16 = qsDetailPopupAnimator4.textYDiff;
                float f17 = (f5 * f16) + (-f16);
                View view6 = qsDetailPopupAnimator4.detailHeaderText;
                if (view6 != null) {
                    view6.setTranslationX(f15);
                    view6.setTranslationY(f17);
                    view6.setScaleX(f13);
                    view6.setScaleY(f13);
                }
                View view7 = this.detailSummary;
                if (view7 != null) {
                    view7.setTranslationX(f15);
                    view7.setTranslationY(f17);
                    view7.setScaleX(f13);
                    view7.setScaleY(f13);
                }
            }
        });
        this.springAnimList.add(springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$1$2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f2, float f3) {
                QsTransitionAnimator.DetailCallback detailCallback = QsDetailPopupAnimator.this.detailCallback;
                if (detailCallback != null) {
                    detailCallback.showDetailAnimEnd();
                }
                SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = QsDetailPopupAnimator.this.animStateCallback;
                if (anonymousClass2 != null) {
                    anonymousClass2.setDetailOpening(false);
                }
                SecQSImplAnimatorManager.AnonymousClass2 anonymousClass22 = QsDetailPopupAnimator.this.animStateCallback;
                if (anonymousClass22 != null) {
                    anonymousClass22.setDetailShowing(true);
                }
                QsDetailPopupAnimator.this.isAnimating = false;
            }
        }));
        final SpringAnimation springAnimation2 = new SpringAnimation(new FloatValueHolder(f));
        this.hideDetailSpringAnimator = springAnimation2;
        springAnimation2.setSpring(new SpringForce().setStiffness(400.0f).setDampingRatio(0.9f).setFinalPosition(0.0f));
        this.springAnimList.add(springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$3$1
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f2, float f3) {
                float coerceAtLeast;
                float f4 = f2 / ((float) hypot);
                QsDetailPopupAnimator qsDetailPopupAnimator = this;
                View view = qsDetailPopupAnimator.anchorView;
                if ((view instanceof LargeTileView) && view != null) {
                    view.setTranslationX(qsDetailPopupAnimator.xDiff * f4);
                    view.setTranslationY(qsDetailPopupAnimator.yDiff * f4);
                    view.setLeft((int) (qsDetailPopupAnimator.anchorLeftOnStart - ((qsDetailPopupAnimator.widthDiff / 2) * f4)));
                    view.setRight((int) (((qsDetailPopupAnimator.widthDiff / 2) * f4) + qsDetailPopupAnimator.anchorRightOnStart));
                    view.setTop((int) (qsDetailPopupAnimator.anchorTopOnStart - ((qsDetailPopupAnimator.heightDiff / 2) * f4)));
                    view.setBottom((int) (((qsDetailPopupAnimator.heightDiff / 2) * f4) + qsDetailPopupAnimator.anchorBottomOnStart));
                }
                float f5 = 1.0f - (f4 * 2.0f);
                float coerceAtLeast2 = RangesKt___RangesKt.coerceAtLeast(0.0f, f5);
                Iterator it = this.anchorChildren.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(coerceAtLeast2);
                }
                float coerceAtLeast3 = RangesKt___RangesKt.coerceAtLeast(0.0f, f5);
                float f6 = 1.0f - (0.1f * f4);
                QsDetailPopupAnimator qsDetailPopupAnimator2 = this;
                Iterator it2 = qsDetailPopupAnimator2.panelViewList.iterator();
                while (it2.hasNext()) {
                    View view2 = (View) it2.next();
                    View view3 = qsDetailPopupAnimator2.anchorView;
                    if ((view3 != null ? view3.hashCode() : 0) != view2.hashCode()) {
                        view2.setAlpha(coerceAtLeast3);
                        view2.setScaleX(f6);
                        view2.setScaleY(f6);
                    }
                }
                if (z) {
                    float f7 = 1;
                    coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(0.0f, f7 - ((f7 - f4) * 4.0f));
                } else {
                    float f8 = 1;
                    coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(0.0f, f8 - ((f8 - f4) * 2.0f));
                }
                SecQSDetail secQSDetail = this.detail;
                if (secQSDetail != null) {
                    secQSDetail.setAlpha(coerceAtLeast);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator3 = this;
                float f9 = 1;
                float f10 = f9 - f4;
                float f11 = (-qsDetailPopupAnimator3.xDiff) * f10;
                float f12 = (-qsDetailPopupAnimator3.yDiff) * f10;
                View view4 = qsDetailPopupAnimator3.targetView;
                if (view4 != null) {
                    view4.setTranslationX(f11);
                    view4.setTranslationY(f12);
                }
                float f13 = f9 - (0.5f * f10);
                Iterator it3 = this.targetChildren.iterator();
                while (it3.hasNext()) {
                    View view5 = (View) it3.next();
                    view5.setScaleX(f13);
                    view5.setScaleY(f13);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator4 = this;
                float f14 = (-qsDetailPopupAnimator4.textXDiff) * f10;
                float f15 = (-qsDetailPopupAnimator4.textYDiff) * f10;
                View view6 = qsDetailPopupAnimator4.detailHeaderText;
                if (view6 != null) {
                    view6.setTranslationX(f14);
                    view6.setTranslationY(f15);
                    view6.setScaleX(f13);
                    view6.setScaleY(f13);
                }
                View view7 = this.detailSummary;
                if (view7 != null) {
                    view7.setTranslationX(f14);
                    view7.setTranslationY(f15);
                    view7.setScaleX(f13);
                    view7.setScaleY(f13);
                }
                SpringAnimation springAnimation3 = springAnimation2;
                final QsDetailPopupAnimator qsDetailPopupAnimator5 = this;
                springAnimation3.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$3$1.6
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation2, boolean z2, float f16, float f17) {
                        QsDetailPopupAnimator.this.clearAnimationState();
                        QsTransitionAnimator.DetailCallback detailCallback = QsDetailPopupAnimator.this.detailCallback;
                        if (detailCallback != null) {
                            detailCallback.hideDetailAnimEnd();
                        }
                        SecQSImplAnimatorManager.AnonymousClass2 anonymousClass2 = QsDetailPopupAnimator.this.animStateCallback;
                        if (anonymousClass2 != null) {
                            anonymousClass2.setDetailClosing(false);
                        }
                        QsAnimatorState.isDetailPopupShowing = false;
                        SecQSDetail secQSDetail2 = QsDetailPopupAnimator.this.detail;
                        if (secQSDetail2 != null) {
                            secQSDetail2.setVisibility(4);
                        }
                        QsDetailPopupAnimator.this.isAnimating = false;
                    }
                });
            }
        }));
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        View view = this.anchorView;
        if (view != null) {
            builder.addFloat(view, "alpha", 0.0f, 1.0f);
        }
        View view2 = this.systemIconContainer;
        if (view2 != null) {
            builder.addFloat(view2, "alpha", 0.0f, 1.0f);
        }
        this.panelClosingAnchorHidingAnimator = builder.build();
    }

    public final void updateViews$3() {
        View view;
        View view2;
        View view3;
        View view4;
        View view5;
        View view6;
        View view7;
        View view8;
        View view9;
        View view10;
        View view11;
        View view12;
        View view13;
        ArrayList arrayList;
        QSTileView qSTileView;
        QSTileView qSTileView2;
        this.panelViewList.clear();
        BottomLargeTileBar bottomLargeTileBar = this.bottomLargeTileBar;
        if (bottomLargeTileBar == null || (view = bottomLargeTileBar.mBarRootView) == null) {
            view = null;
        } else {
            this.panelViewList.add(view);
        }
        this.bottomLargeTileBarView = view;
        BrightnessVolumeBar brightnessVolumeBar = this.brightnessVolumeBar;
        if (brightnessVolumeBar == null || (view2 = brightnessVolumeBar.mBarRootView) == null) {
            view2 = null;
        } else {
            this.panelViewList.add(view2);
        }
        this.brightnessVolumeBarView = view2;
        QSMediaPlayerBar qSMediaPlayerBar = this.expandedMediaPlayerBar;
        if (qSMediaPlayerBar == null || (view3 = qSMediaPlayerBar.mBarRootView) == null) {
            view3 = null;
        } else {
            this.panelViewList.add(view3);
        }
        this.mediaPlayerBarView = view3;
        MultiSIMPreferredSlotBar multiSIMPreferredSlotBar = this.multiSIMBar;
        if (multiSIMPreferredSlotBar == null || (view4 = multiSIMPreferredSlotBar.mBarRootView) == null) {
            view4 = null;
        } else {
            this.panelViewList.add(view4);
        }
        this.multiSIMBarView = view4;
        QuickControlBar quickControlBar = this.quickControlBar;
        if (quickControlBar == null || (view5 = quickControlBar.mBarRootView) == null) {
            view5 = null;
        } else {
            this.panelViewList.add(view5);
        }
        this.quickControlBarView = view5;
        SecurityFooterBar securityFooterBar = this.securityFooterBar;
        if (securityFooterBar == null || (view6 = securityFooterBar.mBarRootView) == null) {
            view6 = null;
        } else {
            this.panelViewList.add(view6);
        }
        this.securityFooterBarView = view6;
        SmartViewLargeTileBar smartViewLargeTileBar = this.smartViewLargeTileBar;
        if (smartViewLargeTileBar == null || (view7 = smartViewLargeTileBar.mBarRootView) == null) {
            view7 = null;
        } else {
            this.panelViewList.add(view7);
        }
        this.smartLargeTileBarView = view7;
        TileChunkLayoutBar tileChunkLayoutBar = this.tileChunkLayoutBar;
        if (tileChunkLayoutBar == null || (view8 = tileChunkLayoutBar.mBarRootView) == null) {
            view8 = null;
        } else {
            this.panelViewList.add(view8);
        }
        this.chunkTileBarView = view8;
        TopLargeTileBar topLargeTileBar = this.topLargeTileBar;
        if (topLargeTileBar != null && (qSTileView2 = topLargeTileBar.mWifiTileView) != null) {
            this.panelViewList.add(qSTileView2);
        }
        TopLargeTileBar topLargeTileBar2 = this.topLargeTileBar;
        if (topLargeTileBar2 != null && (qSTileView = topLargeTileBar2.mBluetoothTileView) != null) {
            this.panelViewList.add(qSTileView);
        }
        TopLargeTileBar topLargeTileBar3 = this.topLargeTileBar;
        if (topLargeTileBar3 != null && (arrayList = topLargeTileBar3.mOtherTileViews) != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.panelViewList.add((View) it.next());
            }
        }
        VideoCallMicModeBar videoCallMicModeBar = this.videoCallMicModeBar;
        if (videoCallMicModeBar == null || (view9 = videoCallMicModeBar.mBarRootView) == null) {
            view9 = null;
        } else {
            this.panelViewList.add(view9);
        }
        this.videoCallMicModeBarView = view9;
        QS qs = this.mQs;
        this.qsPanel = (qs == null || (view13 = qs.getView()) == null) ? null : view13.findViewById(R.id.quick_settings_panel);
        QS qs2 = this.mQs;
        this.quickHeader = (qs2 == null || (view12 = qs2.getView()) == null) ? null : view12.findViewById(R.id.header);
        QS qs3 = this.mQs;
        if (qs3 == null || (view11 = qs3.getView()) == null || (view10 = view11.findViewById(R.id.header_settings_container)) == null) {
            view10 = null;
        } else {
            this.panelViewList.add(view10);
        }
        this.buttonContainer = view10;
        MotionLayout motionLayout = this.shadeHeaderController.header;
        View findViewById = motionLayout.findViewById(R.id.quick_qs_network_speed_container);
        if (findViewById != null) {
            this.panelViewList.add(findViewById);
        } else {
            findViewById = null;
        }
        this.networkSpeedContainer = findViewById;
        this.systemIconContainer = motionLayout.findViewById(R.id.shade_header_system_icons);
        View findViewById2 = motionLayout.findViewById(R.id.privacy_container);
        if (findViewById2 != null) {
            this.panelViewList.add(findViewById2);
        } else {
            findViewById2 = null;
        }
        this.privacyContainer = findViewById2;
        View findViewById3 = motionLayout.findViewById(R.id.anim_view);
        if (findViewById3 != null) {
            this.panelViewList.add(findViewById3);
        } else {
            findViewById3 = null;
        }
        this.plmn = findViewById3;
        SecQSDetail secQSDetail = this.detailcontroller.view;
        this.detail = secQSDetail;
        this.detailHeaderText = secQSDetail != null ? secQSDetail.findViewById(R.id.qs_detail_extended_text) : null;
        SecQSDetail secQSDetail2 = this.detail;
        this.detailSummary = secQSDetail2 != null ? secQSDetail2.findViewById(R.id.qs_detail_extended_summary_container) : null;
        SecQSDetail secQSDetail3 = this.detail;
        this.detailContent = secQSDetail3 != null ? secQSDetail3.findViewById(R.id.qs_detail_parent) : null;
    }
}
