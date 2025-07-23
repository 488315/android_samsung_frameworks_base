package com.android.systemui.qs.animator;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.tileimpl.LargeTileView;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.RecoilEffectUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QsDetailPopupAnimator extends SecQSImplAnimatorBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Rect anchorRegion;
    public View anchorView;
    public SecQSImplAnimatorManager.AnonymousClass2 animStateCallback;
    public final QSAnimViewProvider animViewProvider;
    public QSAnimView buttonContainer;
    public final ColoredBGHelper coloredBGHelper;
    public final Context context;
    public QSAnimView detail;
    public QsTransitionAnimator.DetailCallback detailCallback;
    public View detailContainer;
    public View detailContent;
    public View detailHeaderText;
    public View detailSummary;
    public float expandedFraction;
    public float fromScaleX;
    public float fromScaleY;
    public int heightDiff;
    public QsDetailPopupAnimator$updateAnimators$3$1 hideAnimUpdateListener;
    public SpringAnimation hideDetailSpringAnimator;
    public boolean isAnimating;
    public boolean isQuicklyDetailPopupClosing;
    public TouchAnimator panelClosingAnchorHidingAnimator;
    public TouchAnimator panelClosingDetailHidingAnimator;
    public TouchAnimator panelClosingIndicatorHidingAnimator;
    public QSAnimView privacyContainer;
    public QSAnimView qsPanel;
    public final QsTransitionAnimator qsTransitionAnimator;
    public float scaleXDiff;
    public float scaleYDiff;
    public ScrollView scrollView;
    public final ShadeHeaderController shadeHeaderController;
    public QsDetailPopupAnimator$updateAnimators$1$1 showAnimUpdateListener;
    public SpringAnimation showDetailSpringAnimator;
    public QSAnimView systemIconContainer;
    public View targetView;
    public float textXDiff;
    public float textYDiff;
    public int widthDiff;
    public float xDiff;
    public float yDiff;
    public final ConfigurationState lastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.UI_MODE));
    public final ArrayList anchorChildren = new ArrayList();
    public final ArrayList targetChildren = new ArrayList();
    public final ArrayList panelViewList = new ArrayList();
    public final ArrayList panelAnimViewList = new ArrayList();
    public final ArrayList springAnimList = new ArrayList();
    public final QsDetailPopupAnimator$anchorLayoutChangedListener$1 anchorLayoutChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$anchorLayoutChangedListener$1
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
            Rect rect = qsDetailPopupAnimator.anchorRegion;
            if (rect == null || !QsAnimatorState.isDetailShowing) {
                return;
            }
            if (i == i5 && i3 == i7 && i2 == i6 && i4 == i8) {
                return;
            }
            view.setLeft(rect.left - (qsDetailPopupAnimator.widthDiff / 2));
            view.setRight((qsDetailPopupAnimator.widthDiff / 2) + rect.right);
            view.setTop(rect.top - (qsDetailPopupAnimator.heightDiff / 2));
            view.setBottom((qsDetailPopupAnimator.heightDiff / 2) + rect.bottom);
        }
    };
    public final QsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1 mediaAnchorLayoutChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (!(i == i5 && i3 == i7 && i2 == i6 && i4 == i8) && QsAnimatorState.isDetailShowing) {
                QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
                int i9 = QsDetailPopupAnimator.$r8$clinit;
                qsDetailPopupAnimator.aimingTarget();
                QsDetailPopupAnimator.this.updateAnimators();
            }
        }
    };
    public final QsDetailPopupAnimator$targetLayoutChangedListener$1 targetLayoutChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$targetLayoutChangedListener$1
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (i == i5 && i3 == i7 && i2 == i6 && i4 == i8) {
                return;
            }
            QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
            if (qsDetailPopupAnimator.isAnimating) {
                qsDetailPopupAnimator.cancelAnimators();
                boolean z = QsAnimatorState.isDetailPopupShowing;
                if (z) {
                    QsDetailPopupAnimator qsDetailPopupAnimator2 = QsDetailPopupAnimator.this;
                    qsDetailPopupAnimator2.transitionDetail(qsDetailPopupAnimator2.anchorView, z);
                }
            }
        }
    };
    public final QsDetailPopupAnimator$showAnimEndListener$1 showAnimEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$showAnimEndListener$1
        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            QsTransitionAnimator.DetailCallback detailCallback = QsDetailPopupAnimator.this.detailCallback;
            if (detailCallback != null) {
                detailCallback.showDetailAnimEnd();
            }
            if (QsDetailPopupAnimator.this.animStateCallback != null) {
                SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(false);
            }
            if (!z && QsDetailPopupAnimator.this.animStateCallback != null) {
                QsAnimatorState.setDetailShowing(true);
            }
            QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
            qsDetailPopupAnimator.isAnimating = false;
            SpringAnimation springAnimation = qsDetailPopupAnimator.showDetailSpringAnimator;
            if (springAnimation != null) {
                springAnimation.removeUpdateListener(qsDetailPopupAnimator.showAnimUpdateListener);
            }
            QsDetailPopupAnimator qsDetailPopupAnimator2 = QsDetailPopupAnimator.this;
            qsDetailPopupAnimator2.showAnimUpdateListener = null;
            qsDetailPopupAnimator2.showDetailSpringAnimator = null;
        }
    };
    public final QsDetailPopupAnimator$hideAnimEndListener$1 hideAnimEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$hideAnimEndListener$1
        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            QsDetailPopupAnimator.this.clearAnimationState();
            QsTransitionAnimator.DetailCallback detailCallback = QsDetailPopupAnimator.this.detailCallback;
            if (detailCallback != null) {
                detailCallback.hideDetailAnimEnd();
            }
            if (QsDetailPopupAnimator.this.animStateCallback != null) {
                SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
            }
            QsDetailPopupAnimator qsDetailPopupAnimator = QsDetailPopupAnimator.this;
            if (qsDetailPopupAnimator.animStateCallback != null) {
                QsAnimatorState.isDetailPopupShowing = false;
            }
            QSAnimView qSAnimView = qsDetailPopupAnimator.detail;
            if (qSAnimView != null) {
                qSAnimView.setVisibility(4);
            }
            QsDetailPopupAnimator qsDetailPopupAnimator2 = QsDetailPopupAnimator.this;
            qsDetailPopupAnimator2.isAnimating = false;
            QSAnimView qSAnimView2 = qsDetailPopupAnimator2.buttonContainer;
            if (qSAnimView2 != null) {
                QsDetailPopupAnimator.setFocusability(qSAnimView2, 131072);
            }
            QSAnimView qSAnimView3 = QsDetailPopupAnimator.this.qsPanel;
            if (qSAnimView3 != null) {
                QsDetailPopupAnimator.setFocusability(qSAnimView3, 131072);
            }
            QsDetailPopupAnimator qsDetailPopupAnimator3 = QsDetailPopupAnimator.this;
            SpringAnimation springAnimation = qsDetailPopupAnimator3.hideDetailSpringAnimator;
            if (springAnimation != null) {
                springAnimation.removeUpdateListener(qsDetailPopupAnimator3.hideAnimUpdateListener);
            }
            QsDetailPopupAnimator qsDetailPopupAnimator4 = QsDetailPopupAnimator.this;
            qsDetailPopupAnimator4.hideAnimUpdateListener = null;
            qsDetailPopupAnimator4.hideDetailSpringAnimator = null;
            QsAnimatorState.isDetailPopupClosing = false;
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$showAnimEndListener$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$hideAnimEndListener$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$anchorLayoutChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.animator.QsDetailPopupAnimator$targetLayoutChangedListener$1] */
    public QsDetailPopupAnimator(Context context, ColoredBGHelper coloredBGHelper, ShadeHeaderController shadeHeaderController, QsTransitionAnimator qsTransitionAnimator, QSAnimViewProvider qSAnimViewProvider) {
        this.context = context;
        this.coloredBGHelper = coloredBGHelper;
        this.shadeHeaderController = shadeHeaderController;
        this.qsTransitionAnimator = qsTransitionAnimator;
        this.animViewProvider = qSAnimViewProvider;
    }

    public static int[] getCenterPositionOnScreen(View view) {
        view.getLocationOnScreen(new int[2]);
        float f = 2;
        return new int[]{(int) (((view.getScaleX() * view.getWidth()) / f) + r1[0]), (int) (((view.getScaleY() * view.getHeight()) / f) + r1[1])};
    }

    public static void setFocusability(QSAnimView qSAnimView, int i) {
        View view = qSAnimView.getView();
        ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
        if (viewGroup != null) {
            viewGroup.setDescendantFocusability(i);
        }
    }

    public final void aimingTarget() {
        int displayWidth;
        int displayHeight;
        int[] iArr = new int[2];
        View view = this.anchorView;
        if (view != null) {
            iArr = getCenterPositionOnScreen(view);
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(CubicBezierEasing$$ExternalSyntheticOutline0.m("aimingTarget anchor = ", view.getScaleX(), ", ", view.getScaleY(), ", w,h = "), view.getWidth(), ",", view.getHeight(), "QsDetailPopupAnimator");
        }
        View view2 = this.anchorView;
        int width = view2 != null ? view2.getWidth() : 0;
        View view3 = this.anchorView;
        int height = view3 != null ? view3.getHeight() : 0;
        View view4 = this.anchorView;
        int left = view4 != null ? view4.getLeft() : 0;
        View view5 = this.anchorView;
        int top = view5 != null ? view5.getTop() : 0;
        View view6 = this.anchorView;
        int right = view6 != null ? view6.getRight() : 0;
        View view7 = this.anchorView;
        Rect rect = new Rect(left, top, right, view7 != null ? view7.getBottom() : 0);
        int[] iArr2 = new int[2];
        if (this.anchorView instanceof LargeTileView) {
            View view8 = this.targetView;
            if (view8 != null) {
                iArr2 = getCenterPositionOnScreen(view8);
            }
            View view9 = this.targetView;
            displayWidth = view9 != null ? view9.getWidth() : 0;
            View view10 = this.targetView;
            displayHeight = view10 != null ? view10.getHeight() : 0;
        } else {
            displayWidth = (int) (DeviceState.getDisplayWidth(this.context) * 0.917f);
            displayHeight = (int) (DeviceState.getDisplayHeight(this.context) * 0.65f);
            iArr2[0] = (displayWidth / 2) + ((int) (DeviceState.getDisplayWidth(this.context) * 0.0415f));
            iArr2[1] = (displayHeight / 2) + ((int) (DeviceState.getDisplayHeight(this.context) * 0.175f));
        }
        int i = displayWidth - width;
        int i2 = displayHeight - height;
        if (i != 0 && i2 != 0) {
            int[] iArr3 = new int[2];
            View view11 = this.detailContainer;
            if (view11 != null) {
                view11.getLocationOnScreen(iArr3);
            }
            float measuredHeight = (this.shadeHeaderController.header.getMeasuredHeight() - iArr3[1]) / 2.0f;
            float f = iArr2[0] - iArr[0];
            this.xDiff = f;
            float f2 = iArr2[1] + measuredHeight;
            float f3 = iArr[1];
            this.yDiff = f2 - f3;
            this.textXDiff = f;
            this.textYDiff = (f2 - (displayHeight / 2.0f)) - f3;
            this.anchorRegion = rect;
            this.widthDiff = i;
            this.heightDiff = i2;
            View view12 = this.anchorView;
            this.fromScaleX = view12 != null ? view12.getScaleX() : 1.0f;
            View view13 = this.anchorView;
            float scaleY = view13 != null ? view13.getScaleY() : 1.0f;
            this.fromScaleY = scaleY;
            this.scaleXDiff = 1.0f - this.fromScaleX;
            this.scaleYDiff = 1.0f - scaleY;
            int i3 = iArr2[0];
            int i4 = iArr2[1];
            int i5 = iArr[0];
            int i6 = iArr[1];
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i4, "aimingTarget targetPt = ", ", ", ", targetOffsetY = ");
            m.append(measuredHeight);
            m.append(", anchorPt = ");
            m.append(i5);
            m.append(",");
            RecyclerView$$ExternalSyntheticOutline0.m(i6, "QsDetailPopupAnimator", m);
        }
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(CubicBezierEasing$$ExternalSyntheticOutline0.m("aimingTarget x,y diff = ", this.xDiff, ", ", this.yDiff, ", w,h diff = "), i, ", ", i2, "QsDetailPopupAnimator");
    }

    public final void cancelAnimators() {
        Log.d("QsDetailPopupAnimator", "cancelAnimators");
        ArrayList arrayList = this.springAnimList;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            SpringAnimation springAnimation = (SpringAnimation) obj;
            if (springAnimation != null) {
                springAnimation.cancel();
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        Log.d("QsDetailPopupAnimator", "clearAnimationState");
        View view = this.anchorView;
        if (view != null) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            Rect rect = this.anchorRegion;
            if (rect != null) {
                view.setLeft(rect.left);
                view.setRight(rect.right);
                view.setTop(rect.top);
                view.setBottom(rect.bottom);
            }
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            View view2 = this.anchorView;
            if ((view2 instanceof LargeTileView) && view2 != null) {
                view2.setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(view.getContext()));
            }
        }
        ArrayList arrayList = this.anchorChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((View) obj).setAlpha(1.0f);
        }
        ArrayList arrayList2 = this.panelViewList;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            View view3 = (View) obj2;
            view3.setAlpha(1.0f);
            view3.setScaleX(1.0f);
            view3.setScaleY(1.0f);
        }
        ArrayList arrayList3 = this.panelAnimViewList;
        int size3 = arrayList3.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList3.get(i3);
            i3++;
            QSAnimView qSAnimView = (QSAnimView) obj3;
            qSAnimView.setAlpha(1.0f);
            qSAnimView.setScaleX(1.0f);
            qSAnimView.setScaleY(1.0f);
        }
        this.panelViewList.clear();
        this.panelAnimViewList.clear();
        QSAnimView qSAnimView2 = this.qsPanel;
        if (qSAnimView2 != null) {
            qSAnimView2.setAlpha(1.0f);
        }
        QSAnimView qSAnimView3 = this.detail;
        if (qSAnimView3 != null) {
            qSAnimView3.setAlpha(1.0f);
        }
        View view4 = this.targetView;
        if (view4 != null) {
            view4.setTranslationX(0.0f);
            view4.setTranslationY(0.0f);
        }
        ArrayList arrayList4 = this.targetChildren;
        int size4 = arrayList4.size();
        int i4 = 0;
        while (i4 < size4) {
            Object obj4 = arrayList4.get(i4);
            i4++;
            View view5 = (View) obj4;
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
        QSAnimView qSAnimView4 = this.systemIconContainer;
        if (qSAnimView4 != null) {
            qSAnimView4.setAlpha(1.0f);
        }
        QSAnimView qSAnimView5 = this.privacyContainer;
        if (qSAnimView5 != null) {
            qSAnimView5.setAlpha(1.0f);
        }
        this.anchorView = null;
        this.targetView = null;
        this.anchorRegion = null;
        resetSpringAnimator();
        this.panelClosingAnchorHidingAnimator = null;
        this.panelClosingIndicatorHidingAnimator = null;
        this.panelClosingDetailHidingAnimator = null;
        QsAnimatorState.isDetailPopupShowing = false;
        this.isQuicklyDetailPopupClosing = false;
        ScrollView scrollView = this.scrollView;
        if (scrollView != null) {
            scrollView.setTouchscreenBlocksFocus(false);
        }
        View view8 = this.detailContent;
        ColoredBGHelper coloredBGHelper = this.coloredBGHelper;
        coloredBGHelper.setBackGroundDrawable(view8, coloredBGHelper.getBGColor());
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        View view = this.anchorView;
        if (view != null) {
            view.removeOnLayoutChangeListener(this.anchorLayoutChangedListener);
        }
        View view2 = this.anchorView;
        if (view2 != null) {
            view2.removeOnLayoutChangeListener(this.mediaAnchorLayoutChangedListener);
        }
        View view3 = this.detailContent;
        if (view3 != null) {
            view3.removeOnLayoutChangeListener(this.targetLayoutChangedListener);
        }
        this.detailCallback = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("QsDetailPopupAnimator ============================================= ");
        SecQSImplAnimatorBase.gatherStateOfViews(arrayList, this.panelViewList);
        SecQSImplAnimatorBase.gatherStateOfAnimViews(arrayList, this.panelAnimViewList, "");
        arrayList.add("============================================================== ");
        return arrayList;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationState configurationState = this.lastConfigurationState;
        boolean needToUpdate = configurationState.needToUpdate(configuration);
        ActionBarContextView$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("onConfigurationChanged needToUpdate = ", ", isDetailPopupShowing = ", ", isDetailPopupClosing = ", needToUpdate, QsAnimatorState.isDetailPopupShowing), QsAnimatorState.isDetailPopupClosing, "QsDetailPopupAnimator");
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE && needToUpdate) {
            clearAnimationState();
        }
        if (QsAnimatorState.isDetailPopupShowing && needToUpdate && !QsAnimatorState.isDetailPopupClosing) {
            QsAnimatorState.isDetailPopupShowing = false;
            View view = this.detailContent;
            ColoredBGHelper coloredBGHelper = this.coloredBGHelper;
            coloredBGHelper.setBackGroundDrawable(view, coloredBGHelper.getBGColor());
            aimingTarget();
            clearAnimationState();
            QSAnimView qSAnimView = this.detail;
            if (qSAnimView != null) {
                qSAnimView.setVisibility(0);
            }
            QsTransitionAnimator qsTransitionAnimator = this.qsTransitionAnimator;
            AnimatorSet animatorSet = qsTransitionAnimator.panelHideAnimSet;
            if (animatorSet != null) {
                animatorSet.start();
            }
            AnimatorSet animatorSet2 = qsTransitionAnimator.detailShowAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.start();
            }
            QSAnimView qSAnimView2 = qsTransitionAnimator.headerView;
            View view2 = qSAnimView2 != null ? qSAnimView2.getView() : null;
            ViewGroup viewGroup = view2 instanceof ViewGroup ? (ViewGroup) view2 : null;
            if (viewGroup != null) {
                viewGroup.setDescendantFocusability(262144);
            }
        }
        if (configuration != null) {
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        Log.d("QsDetailPopupAnimator", "onPanelClosed");
        QSAnimView qSAnimView = this.buttonContainer;
        if (qSAnimView != null) {
            setFocusability(qSAnimView, 131072);
        }
        QSAnimView qSAnimView2 = this.qsPanel;
        if (qSAnimView2 != null) {
            setFocusability(qSAnimView2, 131072);
        }
        cancelAnimators();
        clearAnimationState();
        if (this.animStateCallback != null) {
            SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(false);
            QsAnimatorState.setDetailShowing(false);
            SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:
    
        if (r3.isRunning() == false) goto L26;
     */
    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase, com.android.systemui.shade.ShadeExpansionListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPanelExpansionChanged(com.android.systemui.shade.ShadeExpansionChangeEvent r3) {
        /*
            r2 = this;
            float r0 = r2.expandedFraction
            float r3 = r3.fraction
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 != 0) goto L9
            goto L65
        L9:
            r2.expandedFraction = r3
            boolean r0 = com.android.systemui.qs.animator.QsAnimatorState.isDetailShowing
            boolean r1 = com.android.systemui.qs.animator.QsAnimatorState.isDetailPopupShowing
            r0 = r0 | r1
            if (r0 != 0) goto L13
            goto L65
        L13:
            boolean r0 = r2.isQuicklyDetailPopupClosing
            if (r0 != 0) goto L3e
            r0 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r3 >= 0) goto L3b
            boolean r3 = com.android.systemui.qs.animator.QsAnimatorState.isDetailShowing
            if (r3 != 0) goto L3b
            boolean r3 = com.android.systemui.qs.animator.QsAnimatorState.isDetailPopupShowing
            if (r3 == 0) goto L3b
            com.android.internal.dynamicanimation.animation.SpringAnimation r3 = r2.showDetailSpringAnimator
            if (r3 == 0) goto L3b
            boolean r3 = r3.isRunning()
            r0 = 1
            if (r3 != r0) goto L3b
            com.android.internal.dynamicanimation.animation.SpringAnimation r3 = r2.hideDetailSpringAnimator
            if (r3 == 0) goto L3b
            boolean r3 = r3.isRunning()
            if (r3 != 0) goto L3b
            goto L3c
        L3b:
            r0 = 0
        L3c:
            r2.isQuicklyDetailPopupClosing = r0
        L3e:
            boolean r3 = com.android.systemui.qs.animator.QsAnimatorState.isDetailShowing
            if (r3 == 0) goto L4b
            com.android.systemui.qs.TouchAnimator r3 = r2.panelClosingIndicatorHidingAnimator
            if (r3 == 0) goto L4b
            float r0 = r2.expandedFraction
            r3.setPosition(r0)
        L4b:
            boolean r3 = com.android.systemui.qs.animator.QsAnimatorState.isDetailPopupShowing
            if (r3 == 0) goto L58
            com.android.systemui.qs.TouchAnimator r3 = r2.panelClosingAnchorHidingAnimator
            if (r3 == 0) goto L58
            float r0 = r2.expandedFraction
            r3.setPosition(r0)
        L58:
            boolean r3 = r2.isQuicklyDetailPopupClosing
            if (r3 == 0) goto L65
            com.android.systemui.qs.TouchAnimator r3 = r2.panelClosingDetailHidingAnimator
            if (r3 == 0) goto L65
            float r2 = r2.expandedFraction
            r3.setPosition(r2)
        L65:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.animator.QsDetailPopupAnimator.onPanelExpansionChanged(com.android.systemui.shade.ShadeExpansionChangeEvent):void");
    }

    public final void resetSpringAnimator() {
        Log.d("QsDetailPopupAnimator", "resetSpringAnimator");
        SpringAnimation springAnimation = this.showDetailSpringAnimator;
        if (springAnimation != null) {
            springAnimation.removeUpdateListener(this.showAnimUpdateListener);
            springAnimation.removeEndListener(this.showAnimEndListener);
        }
        SpringAnimation springAnimation2 = this.hideDetailSpringAnimator;
        if (springAnimation2 != null) {
            springAnimation2.removeUpdateListener(this.hideAnimUpdateListener);
            springAnimation2.removeEndListener(this.hideAnimEndListener);
        }
        this.showAnimUpdateListener = null;
        this.hideAnimUpdateListener = null;
        this.showDetailSpringAnimator = null;
        this.hideDetailSpringAnimator = null;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
        } else {
            this.mQs = (QSImpl) qs;
            updateViews$5();
        }
    }

    public final void startPopupAnimation(boolean z) {
        this.isAnimating = true;
        if (z) {
            if (this.animStateCallback != null) {
                SecQSImplAnimatorManager.AnonymousClass2.setDetailOpening(true);
            }
            QsAnimatorState.isDetailPopupShowing = true;
            SpringAnimation springAnimation = this.hideDetailSpringAnimator;
            if (springAnimation != null) {
                springAnimation.cancel();
            }
            SpringAnimation springAnimation2 = this.showDetailSpringAnimator;
            if (springAnimation2 != null) {
                springAnimation2.start();
                return;
            }
            return;
        }
        if (this.animStateCallback != null) {
            QsAnimatorState.setDetailShowing(false);
        }
        if (this.animStateCallback != null) {
            SecQSImplAnimatorManager.AnonymousClass2.setDetailClosing(true);
        }
        SpringAnimation springAnimation3 = this.showDetailSpringAnimator;
        if (springAnimation3 != null) {
            springAnimation3.cancel();
        }
        SpringAnimation springAnimation4 = this.hideDetailSpringAnimator;
        if (springAnimation4 != null) {
            springAnimation4.start();
        }
    }

    public final void transitionDetail(View view, boolean z) {
        View view2;
        boolean z2 = QsAnimatorState.isDetailShowing;
        boolean z3 = QsAnimatorState.isDetailPopupClosing;
        String callers = Debug.getCallers(3, " ");
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("transitionDetail showDetail = ", ", isDetailShowing = ", ", isDetailPopupClosing = ", z, z2);
        m.append(z3);
        m.append("\n");
        m.append(callers);
        Log.d("QsDetailPopupAnimator", m.toString());
        if (z && !QsAnimatorState.isDetailPopupClosing && !Intrinsics.areEqual(view, this.anchorView)) {
            clearAnimationState();
            View view3 = this.detailContent;
            if (view3 != null) {
                view3.setBackground(null);
            }
        }
        QsAnimatorState.isDetailPopupClosing = !z;
        QSAnimView qSAnimView = this.detail;
        if (qSAnimView != null) {
            if (!z) {
                qSAnimView = null;
            }
            if (qSAnimView != null) {
                qSAnimView.setVisibility(0);
                qSAnimView.setAlpha(0.0f);
                qSAnimView.setScaleX(1.0f);
                qSAnimView.setScaleY(1.0f);
                QSAnimView qSAnimView2 = this.buttonContainer;
                if (qSAnimView2 != null) {
                    setFocusability(qSAnimView2, 393216);
                }
                QSAnimView qSAnimView3 = this.qsPanel;
                if (qSAnimView3 != null) {
                    setFocusability(qSAnimView3, 393216);
                }
            }
        }
        QsDetailPopupAnimator$targetLayoutChangedListener$1 qsDetailPopupAnimator$targetLayoutChangedListener$1 = this.targetLayoutChangedListener;
        if (!z) {
            if (z) {
                throw new NoWhenBranchMatchedException();
            }
            View view4 = this.targetView;
            if (view4 != null) {
                view4.removeOnLayoutChangeListener(qsDetailPopupAnimator$targetLayoutChangedListener$1);
            }
            startPopupAnimation(false);
            return;
        }
        updateViews$5();
        View view5 = this.anchorView;
        if (view5 != null) {
            view5.setAlpha(1.0f);
            view5.setTranslationX(0.0f);
            view5.setTranslationY(0.0f);
        }
        ArrayList arrayList = this.anchorChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((View) obj).setAlpha(1.0f);
        }
        View view6 = this.targetView;
        if (view6 != null) {
            view6.setAlpha(1.0f);
            view6.setTranslationX(0.0f);
            view6.setTranslationY(0.0f);
        }
        QSAnimView qSAnimView4 = this.detail;
        if (qSAnimView4 != null) {
            qSAnimView4.setTranslationX(0.0f);
            qSAnimView4.setTranslationY(0.0f);
        }
        ScrollView scrollView = this.scrollView;
        if (scrollView != null) {
            scrollView.setTouchscreenBlocksFocus(true);
        }
        if (view != null) {
            View view7 = this.anchorView;
            QsDetailPopupAnimator$anchorLayoutChangedListener$1 qsDetailPopupAnimator$anchorLayoutChangedListener$1 = this.anchorLayoutChangedListener;
            if (view7 != null) {
                view7.removeOnLayoutChangeListener(qsDetailPopupAnimator$anchorLayoutChangedListener$1);
            }
            View view8 = this.anchorView;
            QsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1 qsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1 = this.mediaAnchorLayoutChangedListener;
            if (view8 != null) {
                view8.removeOnLayoutChangeListener(qsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1);
            }
            this.anchorView = view;
            if (view instanceof LargeTileView) {
                view.addOnLayoutChangeListener(qsDetailPopupAnimator$anchorLayoutChangedListener$1);
                View view9 = this.anchorView;
                if (view9 != null) {
                    view9.setStateListAnimator(null);
                }
            } else {
                view.addOnLayoutChangeListener(qsDetailPopupAnimator$mediaAnchorLayoutChangedListener$1);
            }
            this.anchorChildren.clear();
            View view10 = this.anchorView;
            ViewGroup viewGroup = view10 instanceof ViewGroup ? (ViewGroup) view10 : null;
            if (viewGroup != null) {
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    this.anchorChildren.add(viewGroup.getChildAt(i2));
                }
            }
            View view11 = this.targetView;
            if (view11 != null) {
                view11.removeOnLayoutChangeListener(qsDetailPopupAnimator$targetLayoutChangedListener$1);
            }
            QSAnimView qSAnimView5 = this.detail;
            View findViewWithTag = (qSAnimView5 == null || (view2 = qSAnimView5.getView()) == null) ? null : view2.findViewWithTag("target");
            this.targetView = findViewWithTag;
            if (findViewWithTag != null) {
                findViewWithTag.addOnLayoutChangeListener(qsDetailPopupAnimator$targetLayoutChangedListener$1);
            }
            this.targetChildren.clear();
            View view12 = this.targetView;
            ViewGroup viewGroup2 = view12 instanceof ViewGroup ? (ViewGroup) view12 : null;
            if (viewGroup2 != null) {
                int childCount2 = viewGroup2.getChildCount();
                for (int i3 = 0; i3 < childCount2; i3++) {
                    this.targetChildren.add(viewGroup2.getChildAt(i3));
                }
            }
        }
        aimingTarget();
        updateAnimators(true);
        startPopupAnimation(true);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        SpringAnimation springAnimation;
        SpringAnimation springAnimation2 = this.showDetailSpringAnimator;
        if ((springAnimation2 == null || !springAnimation2.isRunning()) && ((springAnimation = this.hideDetailSpringAnimator) == null || !springAnimation.isRunning())) {
            updateAnimators(false);
            return;
        }
        SpringAnimation springAnimation3 = this.showDetailSpringAnimator;
        Boolean valueOf = springAnimation3 != null ? Boolean.valueOf(springAnimation3.isRunning()) : null;
        SpringAnimation springAnimation4 = this.hideDetailSpringAnimator;
        Log.d("QsDetailPopupAnimator", "updateAnimators SpringAnimator is Running (" + valueOf + ", " + (springAnimation4 != null ? Boolean.valueOf(springAnimation4.isRunning()) : null) + ") > skip updateAnimator");
    }

    public final void updateViews$5() {
        View view;
        View view2;
        View view3;
        this.panelViewList.clear();
        this.panelAnimViewList.clear();
        QSAnimViewProvider qSAnimViewProvider = this.animViewProvider;
        for (QSAnimView qSAnimView : qSAnimViewProvider.getBars()) {
            if (qSAnimView != null && qSAnimView.getViewType() != QSAnimViewProvider.ViewType.QS_BAR_TOP) {
                this.panelAnimViewList.add(qSAnimView);
            }
        }
        QSAnimView qSAnimView2 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_BAR_TOP);
        QSAnimView qSAnimView3 = null;
        ViewGroup viewGroup = (qSAnimView2 == null || (view3 = qSAnimView2.getView()) == null) ? null : (ViewGroup) view3.findViewById(R.id.large_tile_container);
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.panelViewList.add(viewGroup.getChildAt(i));
            }
        }
        QSImpl qSImpl = this.mQs;
        this.scrollView = (qSImpl == null || (view2 = qSImpl.getView()) == null) ? null : (ScrollView) view2.findViewById(R.id.expanded_qs_scroll_view);
        this.qsPanel = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_PANEL);
        QSAnimView qSAnimView4 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_BUTTON_CONTAINER);
        if (qSAnimView4 != null) {
            this.panelAnimViewList.add(qSAnimView4);
            qSAnimView3 = qSAnimView4;
        }
        this.buttonContainer = qSAnimView3;
        QSAnimView qSAnimView5 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_NETWORK_SPEED);
        if (qSAnimView5 != null) {
            this.panelAnimViewList.add(qSAnimView5);
        }
        this.systemIconContainer = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_SYSTEM_ICONS);
        this.privacyContainer = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PRIVACY_CONTAINER);
        QSAnimView qSAnimView6 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PLMN);
        if (qSAnimView6 != null) {
            this.panelAnimViewList.add(qSAnimView6);
        }
        QSAnimView qSAnimView7 = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.DETAIL);
        this.detail = qSAnimView7;
        if (qSAnimView7 == null || (view = qSAnimView7.getView()) == null) {
            return;
        }
        this.detailContainer = view.findViewById(R.id.panel_adjusted_detail);
        this.detailHeaderText = view.findViewById(R.id.qs_detail_extended_text);
        this.detailSummary = view.findViewById(R.id.qs_detail_extended_summary_container);
        this.detailContent = view.findViewById(R.id.qs_detail_parent);
    }

    /* JADX WARN: Type inference failed for: r3v15, types: [com.android.internal.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener, com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$3$1] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.internal.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener, com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$1$1] */
    public final void updateAnimators(boolean z) {
        Log.d("QsDetailPopupAnimator", "updateAnimators");
        if (!z) {
            updateViews$5();
        }
        if (this.showDetailSpringAnimator != null && this.hideDetailSpringAnimator != null) {
            resetSpringAnimator();
        }
        this.springAnimList.clear();
        final boolean z2 = this.anchorView instanceof LargeTileView;
        final float hypot = (float) Math.hypot(this.xDiff, this.yDiff);
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder(0.0f));
        springAnimation.setSpring(new SpringForce().setStiffness(300.0f).setDampingRatio(0.80829036f).setFinalPosition(hypot));
        ?? r3 = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$1$1
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                float f3;
                float f4;
                float f5 = f / hypot;
                if (Float.isNaN(f5)) {
                    Log.e("QsDetailPopupAnimator", "animatedValue for showDetailSpringAnimator is NaN, so sets as 0f");
                    f5 = 0.0f;
                }
                QsDetailPopupAnimator qsDetailPopupAnimator = this;
                View view = qsDetailPopupAnimator.anchorView;
                if (view != null) {
                    if (!z2) {
                        view = null;
                    }
                    if (view != null) {
                        view.setTranslationX(qsDetailPopupAnimator.xDiff * f5);
                        view.setTranslationY(qsDetailPopupAnimator.yDiff * f5);
                        float f6 = f5 / 2.0f;
                        if (qsDetailPopupAnimator.anchorRegion != null) {
                            view.setLeft((int) (r3.left - (qsDetailPopupAnimator.widthDiff * f6)));
                            view.setRight((int) ((qsDetailPopupAnimator.widthDiff * f6) + r3.right));
                            view.setTop((int) (r3.top - (qsDetailPopupAnimator.heightDiff * f6)));
                            view.setBottom((int) ((qsDetailPopupAnimator.heightDiff * f6) + r3.bottom));
                        }
                        view.setScaleX((qsDetailPopupAnimator.scaleXDiff * f5) + qsDetailPopupAnimator.fromScaleX);
                        view.setScaleY((qsDetailPopupAnimator.scaleYDiff * f5) + qsDetailPopupAnimator.fromScaleY);
                    }
                }
                float f7 = 1.0f - (2.0f * f5);
                float f8 = 0.0f < f7 ? f7 : 0.0f;
                Iterator it = this.anchorChildren.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(f8);
                }
                if (0.0f >= f7) {
                    f7 = 0.0f;
                }
                float f9 = 1.0f - (0.1f * f5);
                View view2 = this.anchorView;
                int hashCode = view2 != null ? view2.hashCode() : 0;
                ArrayList arrayList = this.panelViewList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    View view3 = (View) obj;
                    if (hashCode != view3.hashCode()) {
                        view3.setAlpha(f7);
                        view3.setScaleX(f9);
                        view3.setScaleY(f9);
                    }
                }
                ArrayList arrayList2 = this.panelAnimViewList;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    QSAnimView qSAnimView = (QSAnimView) obj2;
                    View view4 = qSAnimView.getView();
                    if (hashCode != (view4 != null ? view4.hashCode() : 0)) {
                        qSAnimView.setAlpha(f7);
                        qSAnimView.setScaleX(f9);
                        qSAnimView.setScaleY(f9);
                    }
                }
                QSAnimView qSAnimView2 = this.detail;
                if (qSAnimView2 != null) {
                    if (z2) {
                        float f10 = f5 - 0.75f;
                        f3 = 0.0f < f10 ? f10 : 0.0f;
                        f4 = 4.0f;
                    } else {
                        float f11 = f5 - 0.25f;
                        f3 = 0.0f < f11 ? f11 : 0.0f;
                        f4 = 1.3333334f;
                    }
                    qSAnimView2.setAlpha(f3 * f4);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator2 = this;
                float f12 = f5 - 1;
                float f13 = qsDetailPopupAnimator2.xDiff * f12;
                float f14 = qsDetailPopupAnimator2.yDiff * f12;
                View view5 = qsDetailPopupAnimator2.targetView;
                if (view5 != null) {
                    view5.setTranslationX(f13);
                    view5.setTranslationY(f14);
                }
                float f15 = (f5 * 0.5f) + 0.5f;
                Iterator it2 = this.targetChildren.iterator();
                while (it2.hasNext()) {
                    View view6 = (View) it2.next();
                    view6.setScaleX(f15);
                    view6.setScaleY(f15);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator3 = this;
                float f16 = qsDetailPopupAnimator3.textXDiff * f12;
                float f17 = qsDetailPopupAnimator3.textYDiff * f12;
                View view7 = qsDetailPopupAnimator3.detailHeaderText;
                if (view7 != null) {
                    view7.setTranslationX(f16);
                    view7.setTranslationY(f17);
                    view7.setScaleX(f15);
                    view7.setScaleY(f15);
                }
                View view8 = this.detailSummary;
                if (view8 != null) {
                    view8.setTranslationX(f16);
                    view8.setTranslationY(f17);
                    view8.setScaleX(f15);
                    view8.setScaleY(f15);
                }
            }
        };
        this.showAnimUpdateListener = r3;
        springAnimation.addUpdateListener((DynamicAnimation.OnAnimationUpdateListener) r3);
        springAnimation.addEndListener(this.showAnimEndListener);
        this.springAnimList.add(springAnimation);
        this.showDetailSpringAnimator = springAnimation;
        SpringAnimation springAnimation2 = new SpringAnimation(new FloatValueHolder(hypot));
        springAnimation2.setSpring(new SpringForce().setStiffness(400.0f).setDampingRatio(0.9f).setFinalPosition(0.0f));
        ?? r32 = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.qs.animator.QsDetailPopupAnimator$updateAnimators$3$1
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                float f3 = f / hypot;
                if (Float.isNaN(f3)) {
                    Log.e("QsDetailPopupAnimator", "animatedValue for hideDetailSpringAnimator is NaN, so sets as 1f");
                    f3 = 1.0f;
                }
                QsDetailPopupAnimator qsDetailPopupAnimator = this;
                View view = qsDetailPopupAnimator.anchorView;
                if (view != null) {
                    if (!z2) {
                        view = null;
                    }
                    if (view != null) {
                        view.setTranslationX(qsDetailPopupAnimator.xDiff * f3);
                        view.setTranslationY(qsDetailPopupAnimator.yDiff * f3);
                        float f4 = f3 / 2.0f;
                        if (qsDetailPopupAnimator.anchorRegion != null) {
                            view.setLeft((int) (r3.left - (qsDetailPopupAnimator.widthDiff * f4)));
                            view.setRight((int) ((qsDetailPopupAnimator.widthDiff * f4) + r3.right));
                            view.setTop((int) (r3.top - (qsDetailPopupAnimator.heightDiff * f4)));
                            view.setBottom((int) ((qsDetailPopupAnimator.heightDiff * f4) + r3.bottom));
                        }
                    }
                }
                float f5 = 1.0f - (f3 * 2.0f);
                float f6 = 0.0f;
                float f7 = 0.0f < f5 ? f5 : 0.0f;
                Iterator it = this.anchorChildren.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(f7);
                }
                if (0.0f >= f5) {
                    f5 = 0.0f;
                }
                float f8 = 1.0f - (0.1f * f3);
                View view2 = this.anchorView;
                int hashCode = view2 != null ? view2.hashCode() : 0;
                ArrayList arrayList = this.panelViewList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    View view3 = (View) obj;
                    if (hashCode != view3.hashCode()) {
                        view3.setAlpha(f5);
                        view3.setScaleX(f8);
                        view3.setScaleY(f8);
                    }
                }
                ArrayList arrayList2 = this.panelAnimViewList;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    QSAnimView qSAnimView = (QSAnimView) obj2;
                    View view4 = qSAnimView.getView();
                    if (hashCode != (view4 != null ? view4.hashCode() : 0)) {
                        qSAnimView.setAlpha(f5);
                        qSAnimView.setScaleX(f8);
                        qSAnimView.setScaleY(f8);
                    }
                }
                QSAnimView qSAnimView2 = this.detail;
                if (qSAnimView2 != null) {
                    if (z2) {
                        float f9 = 1;
                        float f10 = f9 - ((f9 - f3) * 4.0f);
                        if (0.0f < f10) {
                            f6 = f10;
                        }
                    } else {
                        float f11 = 1;
                        float f12 = f11 - ((f11 - f3) * 2.0f);
                        if (0.0f < f12) {
                            f6 = f12;
                        }
                    }
                    qSAnimView2.setAlpha(f6);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator2 = this;
                float f13 = 1;
                float f14 = f3 - f13;
                float f15 = qsDetailPopupAnimator2.xDiff * f14;
                float f16 = qsDetailPopupAnimator2.yDiff * f14;
                View view5 = qsDetailPopupAnimator2.targetView;
                if (view5 != null) {
                    view5.setTranslationX(f15);
                    view5.setTranslationY(f16);
                }
                float f17 = f13 - ((f13 - f3) * 0.5f);
                Iterator it2 = this.targetChildren.iterator();
                while (it2.hasNext()) {
                    View view6 = (View) it2.next();
                    view6.setScaleX(f17);
                    view6.setScaleY(f17);
                }
                QsDetailPopupAnimator qsDetailPopupAnimator3 = this;
                float f18 = qsDetailPopupAnimator3.textXDiff * f14;
                float f19 = qsDetailPopupAnimator3.textYDiff * f14;
                View view7 = qsDetailPopupAnimator3.detailHeaderText;
                if (view7 != null) {
                    view7.setTranslationX(f18);
                    view7.setTranslationY(f19);
                    view7.setScaleX(f17);
                    view7.setScaleY(f17);
                }
                View view8 = this.detailSummary;
                if (view8 != null) {
                    view8.setTranslationX(f18);
                    view8.setTranslationY(f19);
                    view8.setScaleX(f17);
                    view8.setScaleY(f17);
                }
            }
        };
        this.hideAnimUpdateListener = r32;
        springAnimation2.addUpdateListener((DynamicAnimation.OnAnimationUpdateListener) r32);
        springAnimation2.addEndListener(this.hideAnimEndListener);
        this.springAnimList.add(springAnimation2);
        this.hideDetailSpringAnimator = springAnimation2;
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        View view = this.anchorView;
        if (view != null) {
            builder.addFloat(view, "alpha", 0.0f, 1.0f);
        }
        this.panelClosingAnchorHidingAnimator = builder.build();
        TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
        QSAnimView qSAnimView = this.systemIconContainer;
        if (qSAnimView != null) {
            builder2.addFloat(qSAnimView, "alpha", 0.0f, 1.0f);
        }
        QSAnimView qSAnimView2 = this.privacyContainer;
        if (qSAnimView2 != null) {
            builder2.addFloat(qSAnimView2, "alpha", 0.0f, 1.0f);
        }
        this.panelClosingIndicatorHidingAnimator = builder2.build();
        TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
        QSAnimView qSAnimView3 = this.detail;
        if (qSAnimView3 != null) {
            builder3.addFloat(qSAnimView3, "alpha", 0.0f, 1.0f);
        }
        this.panelClosingDetailHidingAnimator = builder3.build();
    }
}
