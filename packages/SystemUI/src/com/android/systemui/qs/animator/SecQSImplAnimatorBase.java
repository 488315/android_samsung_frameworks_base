package com.android.systemui.qs.animator;

import android.content.res.Configuration;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewUtil;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class SecQSImplAnimatorBase implements ShadeExpansionListener, PanelScreenShotLogger.LogProvider {
    public final float SCALE_DOWN_RATIO;
    public boolean mAnimatorsInitialiezed;
    public float mOverScrollAmount;
    public boolean mPanelSplitEnabled;
    public int mPanelState;
    public QSImpl mQs;
    public ShadeRepository mShadeRepository;
    public NotificationStackScrollLayoutController mStackScrollerController;
    public boolean mUserChanged;

    public SecQSImplAnimatorBase() {
        this.SCALE_DOWN_RATIO = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() ? 0.9f : 0.93f;
        this.mPanelSplitEnabled = SecPanelSplitHelper.isEnabled();
        this.mAnimatorsInitialiezed = false;
        this.mPanelState = 1;
        this.mUserChanged = false;
        this.mOverScrollAmount = 0.0f;
    }

    public static void gatherStateOfAnimViews(ArrayList arrayList, ArrayList arrayList2, String str) {
        if (!str.isEmpty()) {
            arrayList.add(str);
        }
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            View view = ((QSAnimView) obj).getView();
            arrayList.add("  " + ViewUtil.INSTANCE.toIdSting(view) + "  " + view.getClass().getSimpleName() + " : alpha = " + view.getAlpha() + ", visibility = " + view.getVisibility() + ", translationY = " + view.getTranslationY());
        }
    }

    public static void gatherStateOfViews(ArrayList arrayList, ArrayList arrayList2) {
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            View view = (View) obj;
            StringBuilder sb = new StringBuilder("  ");
            sb.append(ViewUtil.INSTANCE.toIdSting(view));
            sb.append("  ");
            sb.append(view.getClass().getSimpleName());
            sb.append(" : alpha = ");
            sb.append(view.getAlpha());
            sb.append(", translationY = " + view.getTranslationY());
            sb.append(", visibility = ");
            sb.append(view.getVisibility());
            arrayList.add(sb.toString());
        }
    }

    public static boolean isDetailVisible() {
        return QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing;
    }

    public abstract void destroyQSViews();

    public ArrayList gatherState() {
        return new ArrayList();
    }

    public boolean isThereNoView() {
        SecQSPanel secQSPanel;
        QSImpl qSImpl = this.mQs;
        return qSImpl == null || qSImpl.getView() == null || (secQSPanel = (SecQSPanel) this.mQs.getView().findViewById(R.id.quick_settings_panel)) == null || !secQSPanel.isAttachedToWindow();
    }

    public void onPanelClosed$1() {
        QsAnimatorState.panelExpanded = false;
    }

    public void onPanelOpened() {
        QsAnimatorState.panelExpanded = true;
    }

    public void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        int i = this.mPanelState;
        int i2 = panelTransitionStateChangeEvent.state;
        if (i != i2) {
            this.mPanelState = i2;
        }
        boolean z = this.mPanelSplitEnabled;
        boolean z2 = panelTransitionStateChangeEvent.enabled;
        if (z != z2) {
            this.mPanelSplitEnabled = z2;
        }
    }

    public void onStateChanged(int i) {
        QsAnimatorState.state = i;
    }

    public void setNotificationStackScrollerController(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.mStackScrollerController = notificationStackScrollLayoutController;
    }

    public void setOverScrollAmount(float f) {
        this.mOverScrollAmount = f;
    }

    public abstract void setQs(QS qs);

    public void setQsExpanded(boolean z) {
        QsAnimatorState.qsExpanded = z;
    }

    public void setShadeRepository(ShadeRepository shadeRepository) {
        this.mShadeRepository = shadeRepository;
    }

    public void setStackScrollerOverscrolling(boolean z) {
        QsAnimatorState.expandedByNotiOverScroll = z;
    }

    public abstract void updateAnimators();

    public void updatePanelExpanded(boolean z) {
        QsAnimatorState.panelExpanded = z;
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onNotificationScrolled(int i) {
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
    }

    public void onUserSwitched(int i) {
    }

    public void setOverDragAmount(float f) {
    }

    public void setQsExpansionPosition(float f) {
    }

    public void setTransitionToFullShadeAmount(float f) {
    }

    public void clearAnimationState() {
    }

    public void onRtlChanged() {
    }

    public void slide(float f, float f2, PanelSlideEventHandler.Direction direction, int i) {
    }

    public void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
    }
}
