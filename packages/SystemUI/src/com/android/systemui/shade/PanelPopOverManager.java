package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.SecQSBlurShadowView;
import com.android.systemui.blur.SecQSNewBlurView;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PanelPopOverManager implements LockscreenShadeTransitionController.Callback, PanelScreenShotLogger.LogProvider {
    public static final Interpolator INTERPOLATOR;
    public final BarOrderInteractor barOrderInteractor;
    public SecQSNewBlurView blur;
    public View blurView;
    public int blurViewTopMargin;
    public NotificationPanelViewController$$ExternalSyntheticLambda18 collapseRunnable;
    public final Context context;
    public int currentPanelState;
    public View customizerView;
    public View detailView;
    public boolean isAnimating;
    public boolean isPopOverAreaListenerAdded;
    public SecQSBlurShadowView largeShadowView;
    public int largeShadowViewDistanceFromBlur;
    public Rect leftGestureArea;
    public NotificationPanelView mView;
    public int navBarHeight;
    public NotificationShelf notificationShelf;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public NotificationStackScrollLayout nssl;
    public int nsslStartingHeight;
    public int overExpansionAmount;
    public final SecPanelSplitHelper panelSplitHelper;
    public NotificationPanelViewController panelViewController;
    public View qqsPanelView;
    public View qsPanelView;
    public final QSCMainViewController qscMainViewController;
    public Rect rightGestureArea;
    public final SecQSPanelResourcePicker secQSPanelResourcePicker;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final ShadeRepository shadeRepository;
    public ValueAnimator showDetailAnimator;
    public ValueAnimator showPanelAnimator;
    public SecQSBlurShadowView smallShadowView;
    public int smallShadowViewDistanceFromBlur;
    public final PanelPopOverManager$popOverInsetsListener$1 popOverInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.shade.PanelPopOverManager$popOverInsetsListener$1
        public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) throws Resources.NotFoundException {
            NotificationPanelViewController notificationPanelViewController;
            PanelPopOverManager panelPopOverManager = this.$tmp0;
            if (panelPopOverManager.getNeedToPopOver() && (notificationPanelViewController = panelPopOverManager.panelViewController) != null && notificationPanelViewController.isExpanded()) {
                NotificationPanelViewController notificationPanelViewController2 = panelPopOverManager.panelViewController;
                if ((notificationPanelViewController2 != null ? notificationPanelViewController2.mExpandedHeight : 0.0f) > 0.0f && panelPopOverManager.currentPanelState != 0) {
                    View view = panelPopOverManager.qsPanelView;
                    int width = view != null ? view.getWidth() : 0;
                    int popOverHeight = panelPopOverManager.getPopOverHeight();
                    panelPopOverManager.setTouchableArea(internalInsetsInfo, width, popOverHeight);
                    panelPopOverManager.setBlurArea(width, popOverHeight);
                    return;
                }
            }
            boolean z = !panelPopOverManager.getNeedToPopOver();
            NotificationPanelViewController notificationPanelViewController3 = panelPopOverManager.panelViewController;
            boolean z2 = !(notificationPanelViewController3 != null && notificationPanelViewController3.isExpanded());
            NotificationPanelViewController notificationPanelViewController4 = panelPopOverManager.panelViewController;
            CarrierTextManager$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("setPopoverArea: remove listener. ", " || ", " || ", z, z2), (notificationPanelViewController4 != null ? notificationPanelViewController4.mExpandedHeight : 0.0f) <= 0.0f, " || ", panelPopOverManager.currentPanelState == 0, "PanelPopOverManager");
            panelPopOverManager.isAnimating = false;
            panelPopOverManager.setTouchableArea(internalInsetsInfo, 0, 0);
            panelPopOverManager.setBlurArea(0, 0);
            SecQSNewBlurView secQSNewBlurView = panelPopOverManager.blur;
            if (secQSNewBlurView != null) {
                secQSNewBlurView.setAlpha(0.0f);
            }
            SecQSBlurShadowView secQSBlurShadowView = panelPopOverManager.largeShadowView;
            if (secQSBlurShadowView != null) {
                secQSBlurShadowView.setAlpha(0.0f);
            }
            SecQSBlurShadowView secQSBlurShadowView2 = panelPopOverManager.smallShadowView;
            if (secQSBlurShadowView2 != null) {
                secQSBlurShadowView2.setAlpha(0.0f);
            }
            panelPopOverManager.removePopOverAreaListener();
        }
    };
    public final int[] blurViewLocation = {0, 0};
    public final int[] shelfLocation = {0, 0};
    public final int[] panelLocation = {0, 0};

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        INTERPOLATOR = new PathInterpolator(0.37f, 0.3f, 0.14f, 1.34f);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.PanelPopOverManager$popOverInsetsListener$1] */
    public PanelPopOverManager(Context context, SecPanelSplitHelper secPanelSplitHelper, NotificationStackScrollLayoutController notificationStackScrollLayoutController, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, ShadeExpansionStateManager shadeExpansionStateManager, ShadeRepository shadeRepository, BarOrderInteractor barOrderInteractor, SecQSPanelResourcePicker secQSPanelResourcePicker, QSCMainViewController qSCMainViewController) {
        this.context = context;
        this.panelSplitHelper = secPanelSplitHelper;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.shadeRepository = shadeRepository;
        this.barOrderInteractor = barOrderInteractor;
        this.secQSPanelResourcePicker = secQSPanelResourcePicker;
        this.qscMainViewController = qSCMainViewController;
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        View view = this.blurView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (view != null ? view.getLayoutParams() : null);
        ArrayList arrayList = new ArrayList();
        if (layoutParams != null) {
            arrayList.add("PanelPopOverManager =================================================================================== ");
            int i = layoutParams.height;
            int i2 = layoutParams.width;
            int i3 = layoutParams.topMargin;
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "  blurHeight = ", " width = ", " topMargin = ");
            sbM.append(i3);
            sbM.append(" ");
            arrayList.add(sbM.toString());
            arrayList.add("  translationX = " + this.secQSPanelResourcePicker.getQsFrameX() + " blurTop = " + this.blurViewLocation[1]);
            arrayList.add("======================================================================================================= ");
        }
        Log.d("PanelPopOverManager", String.valueOf(arrayList));
        return arrayList;
    }

    public final int getBlurMaxHeight() {
        return StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.qs_pop_over_blur_spare, (DeviceState.getScreenHeight(this.context) - this.navBarHeight) - this.blurViewTopMargin);
    }

    public final boolean getNeedToPopOver() {
        return QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && this.secQsUiDisplayModeInteractor.isTablet();
    }

    public final int getPopOverHeight() throws Resources.NotFoundException {
        int i;
        int targetViewHeight;
        SecPanelSplitHelper.Companion.getClass();
        boolean z = SecPanelSplitHelper.isEnabled;
        int[] iArr = this.panelLocation;
        int[] iArr2 = this.shelfLocation;
        int[] iArr3 = this.blurViewLocation;
        if (z) {
            SecPanelSplitHelper secPanelSplitHelper = this.panelSplitHelper;
            if (secPanelSplitHelper.enabled) {
                int i2 = secPanelSplitHelper.currentState;
                i = i2 != 2 ? i2 : secPanelSplitHelper.draggedFraction < 0.5f ? secPanelSplitHelper.stateOnDown : secPanelSplitHelper.stateToChange;
            } else {
                i = 3;
            }
            if (i == 0) {
                View view = this.blurView;
                if (view != null) {
                    view.getLocationOnScreen(iArr3);
                }
                View popOverTargetView = getPopOverTargetView();
                if (popOverTargetView != null) {
                    popOverTargetView.getLocationOnScreen(iArr);
                }
                targetViewHeight = getTargetViewHeight(popOverTargetView);
                if (popOverTargetView == this.qsPanelView) {
                    targetViewHeight = ((iArr[1] + targetViewHeight) + this.overExpansionAmount) - iArr3[1];
                }
            } else if (i != 1) {
                targetViewHeight = 0;
            } else {
                View view2 = this.blurView;
                if (view2 != null) {
                    view2.getLocationOnScreen(iArr3);
                }
                NotificationShelf notificationShelf = this.notificationShelf;
                if (notificationShelf != null) {
                    notificationShelf.getLocationOnScreen(iArr2);
                }
                targetViewHeight = ((iArr2[1] + ((NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class)).getPanelShelfHeight()) + this.context.getResources().getDimensionPixelSize(R.dimen.qs_icon_size)) - iArr3[1];
            }
            int blurMaxHeight = getBlurMaxHeight();
            if (targetViewHeight > blurMaxHeight) {
                targetViewHeight = blurMaxHeight;
            }
            if (targetViewHeight >= 0) {
                return targetViewHeight;
            }
        } else {
            View view3 = this.blurView;
            if (view3 != null) {
                view3.getLocationOnScreen(iArr3);
            }
            NotificationShelf notificationShelf2 = this.notificationShelf;
            if (notificationShelf2 != null) {
                notificationShelf2.getLocationOnScreen(iArr2);
            }
            View popOverTargetView2 = getPopOverTargetView();
            if (popOverTargetView2 != null) {
                popOverTargetView2.getLocationOnScreen(iArr);
            }
            NotificationStackScrollLayout notificationStackScrollLayout = this.nssl;
            if (notificationStackScrollLayout != null && notificationStackScrollLayout.mAmbientState != null) {
                notificationStackScrollLayout.getContentHeight();
                notificationStackScrollLayout.mAmbientState.getTopPadding();
            }
            int panelShelfHeight = ((NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class)).getPanelShelfHeight();
            View view4 = this.qqsPanelView;
            if (view4 != null) {
                view4.getHeight();
            }
            int targetViewHeight2 = getTargetViewHeight(popOverTargetView2);
            int dimensionPixelSize = iArr2[1] + panelShelfHeight + this.context.getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
            int i3 = iArr3[1];
            int i4 = dimensionPixelSize - i3;
            int i5 = (iArr[1] + targetViewHeight2) - i3;
            int blurMaxHeight2 = getBlurMaxHeight();
            if (i5 > blurMaxHeight2) {
                i5 = blurMaxHeight2;
            }
            if (popOverTargetView2 != this.qsPanelView) {
                int blurMaxHeight3 = getBlurMaxHeight();
                if (targetViewHeight2 > blurMaxHeight3) {
                    targetViewHeight2 = blurMaxHeight3;
                }
            } else {
                ShadeRepository shadeRepository = this.shadeRepository;
                if (((Number) ((ShadeRepositoryImpl) shadeRepository).qsExpansion.$$delegate_0.getValue()).floatValue() == 0.0f) {
                    this.nsslStartingHeight = i4;
                    AmbientState ambientState = (AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class);
                    targetViewHeight2 = this.nsslStartingHeight;
                    ambientState.mQsPanelHeight = targetViewHeight2;
                } else {
                    targetViewHeight2 = (int) MathUtils.lerp(this.nsslStartingHeight, i5, ((Number) ((ShadeRepositoryImpl) shadeRepository).qsExpansion.$$delegate_0.getValue()).floatValue());
                    ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).mQsPanelHeight = targetViewHeight2;
                }
            }
            if (targetViewHeight2 >= 0) {
                return targetViewHeight2;
            }
        }
        return 0;
    }

    public final View getPopOverTargetView() {
        return this.isAnimating ? this.qsPanelView : QsAnimatorState.isCustomizerShowing ? this.customizerView : (!QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing) ? this.qsPanelView : this.detailView;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getTargetViewHeight(View view) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int height = view != null ? view.getHeight() : 0;
        if (Intrinsics.areEqual(view, this.detailView)) {
            int dimensionPixelSize2 = this.context.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_blur_detail_height);
            int availableDisplayHeight = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getAvailableDisplayHeight(this.context);
            return dimensionPixelSize2 > availableDisplayHeight ? availableDisplayHeight : dimensionPixelSize2;
        }
        if (view != this.customizerView) {
            return height;
        }
        if (height > StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.qs_pop_over_layout_edit_items_top_margin, ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(this.context))) {
            return height;
        }
        QSCMainViewController qSCMainViewController = this.qscMainViewController;
        int navBarHeight = qSCMainViewController.resourcePicker.getNavBarHeight(qSCMainViewController.getContext());
        int dimensionPixelSize3 = qSCMainViewController.getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_items_top_margin);
        int dimensionPixelSize4 = qSCMainViewController.getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin) + ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileIconSize(qSCMainViewController.getContext());
        BarOrderInteractor barOrderInteractor = qSCMainViewController.barOrderInteractor;
        int iMax = (dimensionPixelSize4 * Math.max(barOrderInteractor.repository.collapsedBarRow, 1)) + qSCMainViewController.getContext().getResources().getDimensionPixelSize(R.dimen.qs_customize_tile_layout_padding_top) + qSCMainViewController.getContext().getResources().getDimensionPixelSize(R.dimen.qs_customize_tile_layout_handler);
        int dimensionPixelSize5 = qSCMainViewController.getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_unknown_edit_margin);
        QSPanelHost qSPanelHost = qSCMainViewController.panelHost;
        if (qSPanelHost == null) {
            qSPanelHost = null;
        }
        ArrayList barItems = qSPanelHost.getBarItems();
        int size = barItems.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = barItems.get(i2);
            i2++;
            BarItemImpl barItemImpl = (BarItemImpl) obj;
            if (!barOrderInteractor.repository.nonEditableBars.contains(barItemImpl.getClass().getSimpleName())) {
                if (barItemImpl instanceof TileChunkLayoutBar) {
                    dimensionPixelSize = qSCMainViewController.getContext().getResources().getDimensionPixelSize(R.dimen.qs_customize_sumOf_margin_and_border_size);
                } else {
                    View view2 = barItemImpl.mBarRootView;
                    if (view2 != null) {
                        dimensionPixelSize = (barItemImpl.mBarRootView.getHeight() != 0 ? qSCMainViewController.getContext().getResources().getDimensionPixelSize(R.dimen.qs_customize_sumOf_margin_and_border_size) : 0) + view2.getHeight();
                    } else {
                        dimensionPixelSize = 0;
                    }
                }
            }
            i += dimensionPixelSize;
        }
        return dimensionPixelSize3 + iMax + i + navBarHeight + dimensionPixelSize5;
    }

    public final void removePopOverAreaListener() {
        ViewTreeObserver viewTreeObserver;
        if (this.isPopOverAreaListenerAdded) {
            Log.d("PanelPopOverManager", "removePopoverAreaListener");
            NotificationPanelView notificationPanelView = this.mView;
            if (notificationPanelView != null && (viewTreeObserver = notificationPanelView.getViewTreeObserver()) != null) {
                viewTreeObserver.removeOnComputeInternalInsetsListener(this.popOverInsetsListener);
            }
            this.isPopOverAreaListenerAdded = false;
            this.isAnimating = false;
            View view = this.blurView;
            if (view != null) {
                view.setVisibility(8);
            }
            SecQSBlurShadowView secQSBlurShadowView = this.largeShadowView;
            if (secQSBlurShadowView != null) {
                secQSBlurShadowView.setVisibility(8);
            }
            SecQSBlurShadowView secQSBlurShadowView2 = this.smallShadowView;
            if (secQSBlurShadowView2 != null) {
                secQSBlurShadowView2.setVisibility(8);
            }
            this.blurView = null;
            this.blur = null;
            this.largeShadowView = null;
            this.smallShadowView = null;
            this.qsPanelView = null;
            this.qqsPanelView = null;
            this.customizerView = null;
            this.notificationShelf = null;
            this.nssl = null;
        }
    }

    public final void setBlurArea(int i, int i2) {
        SecQSNewBlurView secQSNewBlurView;
        SecQSNewBlurView secQSNewBlurView2;
        if (this.isAnimating) {
            return;
        }
        View view = this.blurView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (view != null ? view.getLayoutParams() : null);
        float qsFrameX = this.secQSPanelResourcePicker.getQsFrameX();
        View view2 = this.blurView;
        Integer numValueOf = (view2 == null || (secQSNewBlurView2 = (SecQSNewBlurView) view2.findViewById(R.id.qs_new_blur)) == null) ? null : Integer.valueOf(secQSNewBlurView2.newPos[1]);
        if (layoutParams != null && layoutParams.width == i && layoutParams.height == i2 && layoutParams.topMargin == this.blurViewTopMargin) {
            View view3 = this.blurView;
            if (Intrinsics.areEqual(view3 != null ? Float.valueOf(view3.getTranslationX()) : null, qsFrameX) || Float.isNaN(qsFrameX)) {
                int i3 = this.blurViewLocation[1];
                if (numValueOf != null && i3 == numValueOf.intValue()) {
                    return;
                }
            }
        }
        int i4 = this.blurViewTopMargin;
        float f = this.context.getResources().getDisplayMetrics().density;
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "setBlurArea - width : ", " height : ", " topMargin : ");
        sbM.append(i4);
        sbM.append(" translationX : ");
        sbM.append(qsFrameX);
        sbM.append(" blurTop : ");
        sbM.append(numValueOf);
        sbM.append(" density : ");
        sbM.append(f);
        Log.d("PanelPopOverManager", sbM.toString());
        setShadowArea(i, i2);
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        if (layoutParams != null) {
            layoutParams.topMargin = this.blurViewTopMargin;
        }
        View view4 = this.blurView;
        if (view4 != null) {
            view4.setLayoutParams(layoutParams);
        }
        View view5 = this.blurView;
        if (view5 != null) {
            view5.setTranslationX(qsFrameX);
        }
        View view6 = this.blurView;
        if (view6 == null || (secQSNewBlurView = (SecQSNewBlurView) view6.findViewById(R.id.qs_new_blur)) == null) {
            return;
        }
        secQSNewBlurView.getLocationOnScreen(secQSNewBlurView.newPos);
    }

    @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
    public final void setOverDragAmount(float f) {
        setOverScrollAmount((int) f);
    }

    public final void setOverScrollAmount(int i) {
        int i2;
        if (this.isPopOverAreaListenerAdded) {
            float dimension = i / (this.context.getResources().getDimension(R.dimen.panel_overshoot_amount) * 1.5f);
            boolean zIsTablet = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
            BarOrderInteractor barOrderInteractor = this.barOrderInteractor;
            ArrayList barViewsByOrder = (!zIsTablet && this.context.getResources().getConfiguration().orientation == 2) ? barOrderInteractor.landscapeBars : barOrderInteractor.getBarViewsByOrder();
            this.overExpansionAmount = (int) (dimension * barViewsByOrder.size() * 20);
            SecPanelSplitHelper secPanelSplitHelper = this.panelSplitHelper;
            if (secPanelSplitHelper.enabled) {
                int i3 = secPanelSplitHelper.currentState;
                i2 = i3 != 2 ? i3 : secPanelSplitHelper.draggedFraction < 0.5f ? secPanelSplitHelper.stateOnDown : secPanelSplitHelper.stateToChange;
            } else {
                i2 = 3;
            }
            if (i2 == 0) {
                View view = this.qsPanelView;
                setBlurArea(view != null ? view.getWidth() : 0, getPopOverHeight());
            }
        }
    }

    public final void setShadowArea(int i, int i2) {
        SecQSBlurShadowView secQSBlurShadowView = this.largeShadowView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (secQSBlurShadowView != null ? secQSBlurShadowView.getLayoutParams() : null);
        SecQSBlurShadowView secQSBlurShadowView2 = this.smallShadowView;
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) (secQSBlurShadowView2 != null ? secQSBlurShadowView2.getLayoutParams() : null);
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.secQSPanelResourcePicker;
        float qsFrameX = secQSPanelResourcePicker.getQsFrameX();
        Context context = this.context;
        SecQSPanelResourceNormalPicker targetPicker = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker();
        targetPicker.getClass();
        int screenWidth = DeviceState.getScreenWidth(context) - targetPicker.getPanelWidth(context);
        float f = screenWidth == 0 ? 0.0f : (targetPicker.qsTransitionX * (-4.0f)) / screenWidth;
        int i3 = this.largeShadowViewDistanceFromBlur;
        int i4 = i3 * 2;
        int i5 = i4 + i;
        int i6 = this.smallShadowViewDistanceFromBlur;
        int i7 = i6 * 2;
        int i8 = i7 + i;
        int i9 = this.blurViewTopMargin;
        int i10 = i9 - i3;
        int i11 = i9 - i6;
        if (layoutParams != null) {
            layoutParams.width = i5;
        }
        if (layoutParams != null) {
            layoutParams.height = i4 + i2;
        }
        if (layoutParams != null) {
            layoutParams.topMargin = i10;
        }
        if (layoutParams2 != null) {
            layoutParams2.width = i8;
        }
        if (layoutParams2 != null) {
            layoutParams2.height = i7 + i2;
        }
        if (layoutParams2 != null) {
            layoutParams2.topMargin = i11;
        }
        SecQSBlurShadowView secQSBlurShadowView3 = this.largeShadowView;
        if (secQSBlurShadowView3 != null) {
            secQSBlurShadowView3.setLayoutParams(layoutParams);
        }
        SecQSBlurShadowView secQSBlurShadowView4 = this.largeShadowView;
        if (secQSBlurShadowView4 != null) {
            secQSBlurShadowView4.setTranslationX(qsFrameX);
        }
        SecQSBlurShadowView secQSBlurShadowView5 = this.largeShadowView;
        if (secQSBlurShadowView5 != null) {
            int color = this.context.getColor(R.color.qs_large_shadow_color);
            secQSBlurShadowView5.radius = 60.0f;
            secQSBlurShadowView5.dx = f;
            secQSBlurShadowView5.dy = 15.0f;
            secQSBlurShadowView5.color = color;
        }
        SecQSBlurShadowView secQSBlurShadowView6 = this.largeShadowView;
        if (secQSBlurShadowView6 != null) {
            float f2 = this.largeShadowViewDistanceFromBlur;
            secQSBlurShadowView6.left = f2;
            secQSBlurShadowView6.top = f2;
        }
        if (secQSBlurShadowView6 != null) {
            secQSBlurShadowView6.width = i;
            secQSBlurShadowView6.height = i2;
        }
        SecQSBlurShadowView secQSBlurShadowView7 = this.smallShadowView;
        if (secQSBlurShadowView7 != null) {
            secQSBlurShadowView7.setLayoutParams(layoutParams2);
        }
        SecQSBlurShadowView secQSBlurShadowView8 = this.smallShadowView;
        if (secQSBlurShadowView8 != null) {
            secQSBlurShadowView8.setTranslationX(qsFrameX);
        }
        SecQSBlurShadowView secQSBlurShadowView9 = this.smallShadowView;
        if (secQSBlurShadowView9 != null) {
            int color2 = this.context.getColor(R.color.qs_small_shadow_color);
            secQSBlurShadowView9.radius = 10.0f;
            secQSBlurShadowView9.dx = 0.0f;
            secQSBlurShadowView9.dy = 2.0f;
            secQSBlurShadowView9.color = color2;
        }
        SecQSBlurShadowView secQSBlurShadowView10 = this.smallShadowView;
        if (secQSBlurShadowView10 != null) {
            float f3 = this.smallShadowViewDistanceFromBlur;
            secQSBlurShadowView10.left = f3;
            secQSBlurShadowView10.top = f3;
        }
        if (secQSBlurShadowView10 != null) {
            secQSBlurShadowView10.width = i;
            secQSBlurShadowView10.height = i2;
        }
    }

    public final void setTouchableArea(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo, int i, int i2) {
        NotificationPanelViewController notificationPanelViewController;
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER_NOT_SET_TOUCHABLE_AREA || (notificationPanelViewController = this.panelViewController) == null || notificationPanelViewController.mBarState != 0) {
            return;
        }
        int[] iArr = this.blurViewLocation;
        int i3 = iArr[1] + i2;
        int i4 = iArr[0];
        Region region = new Region(i4, iArr[1], i + i4, i3);
        if (region.isEmpty()) {
            NotificationPanelView notificationPanelView = this.mView;
            if (notificationPanelView != null) {
                region.set(0, 0, notificationPanelView.getWidth(), notificationPanelView.getHeight());
            }
        } else {
            Rect rect = new Rect();
            NotificationPanelView notificationPanelView2 = this.mView;
            rect.set(0, 0, notificationPanelView2 != null ? notificationPanelView2.getWidth() : 0, this.blurViewTopMargin);
            region.union(rect);
            Rect rect2 = this.leftGestureArea;
            if (rect2 == null) {
                rect2 = new Rect();
            }
            region.union(rect2);
            Rect rect3 = this.rightGestureArea;
            if (rect3 == null) {
                rect3 = new Rect();
            }
            region.union(rect3);
        }
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(region);
        region.recycle();
    }

    public final void transitionBlurAnim(boolean z) {
        if (getNeedToPopOver()) {
            View view = this.blurView;
            int height = view != null ? view.getHeight() : 0;
            View view2 = this.blurView;
            final int width = view2 != null ? view2.getWidth() : 0;
            int targetViewHeight = getTargetViewHeight(QsAnimatorState.isCustomizerShowing ? this.customizerView : this.detailView);
            int blurMaxHeight = getBlurMaxHeight();
            if (targetViewHeight > blurMaxHeight) {
                targetViewHeight = blurMaxHeight;
            }
            if (z) {
                ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(height, targetViewHeight);
                valueAnimatorOfInt.setDuration(480L);
                valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$1$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        PanelPopOverManager panelPopOverManager = this.this$0;
                        panelPopOverManager.showDetailAnimator = null;
                        panelPopOverManager.isAnimating = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        this.this$0.isAnimating = true;
                    }
                });
                valueAnimatorOfInt.setInterpolator(INTERPOLATOR);
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$1$2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        View view3 = this.this$0.blurView;
                        ViewGroup.LayoutParams layoutParams = view3 != null ? view3.getLayoutParams() : null;
                        if (layoutParams != null) {
                            layoutParams.height = iIntValue;
                        }
                        View view4 = this.this$0.blurView;
                        if (view4 != null) {
                            view4.setLayoutParams(layoutParams);
                        }
                        this.this$0.setShadowArea(width, iIntValue);
                    }
                });
                this.showDetailAnimator = valueAnimatorOfInt;
                ValueAnimator valueAnimator = this.showPanelAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                ValueAnimator valueAnimator2 = this.showDetailAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.start();
                    return;
                }
                return;
            }
            ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(height, getPopOverHeight());
            valueAnimatorOfInt2.setDuration(300L);
            valueAnimatorOfInt2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$2$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    PanelPopOverManager panelPopOverManager = this.this$0;
                    panelPopOverManager.showPanelAnimator = null;
                    panelPopOverManager.isAnimating = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    this.this$0.isAnimating = true;
                }
            });
            valueAnimatorOfInt2.setInterpolator(INTERPOLATOR);
            valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$2$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                    int iIntValue = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                    View view3 = this.this$0.blurView;
                    ViewGroup.LayoutParams layoutParams = view3 != null ? view3.getLayoutParams() : null;
                    if (layoutParams != null) {
                        layoutParams.height = iIntValue;
                    }
                    View view4 = this.this$0.blurView;
                    if (view4 != null) {
                        view4.setLayoutParams(layoutParams);
                    }
                    this.this$0.setShadowArea(width, iIntValue);
                }
            });
            this.showPanelAnimator = valueAnimatorOfInt2;
            ValueAnimator valueAnimator3 = this.showDetailAnimator;
            if (valueAnimator3 != null) {
                valueAnimator3.cancel();
            }
            ValueAnimator valueAnimator4 = this.showPanelAnimator;
            if (valueAnimator4 != null) {
                valueAnimator4.start();
            }
        }
    }
}
