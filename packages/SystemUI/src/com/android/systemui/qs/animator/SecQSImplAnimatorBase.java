package com.android.systemui.qs.animator;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.ViewUtil;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SecQSImplAnimatorBase implements ShadeExpansionListener, PanelScreenShotLogger.LogProvider {
    public NotificationPanelViewController mPanelViewController;
    public QS mQs;
    public ShadeRepository mShadeRepository;
    public NotificationStackScrollLayoutController mStackScrollerController;
    public boolean mPanelSplitEnabled = SecPanelSplitHelper.isEnabled();
    public boolean mAnimatorsInitialiezed = false;
    public int mPanelState = 1;
    public boolean mUserChanged = false;
    public float mOverScrollAmount = 0.0f;

    public static void gatherStateOfViews(ArrayList arrayList, String str, ArrayList arrayList2, boolean z) {
        String str2;
        if (!str.isEmpty()) {
            arrayList.add(str);
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            StringBuilder sb = new StringBuilder("  ");
            sb.append(ViewUtil.INSTANCE.toIdSting(view));
            sb.append("  ");
            sb.append(view.getClass().getSimpleName());
            sb.append(" : alpha = ");
            sb.append(view.getAlpha());
            if (z) {
                str2 = ", translationY = " + view.getTranslationY();
            } else {
                str2 = "";
            }
            sb.append(str2);
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

    public final SecQuickStatusBarHeader getHeader() {
        if (this.mQs.getView() instanceof ViewGroup) {
            return ViewUtil.getSecQuickStatusBarHeader((ViewGroup) this.mQs.getView());
        }
        return null;
    }

    public boolean isThereNoView() {
        SecQSPanel secQSPanel;
        QS qs = this.mQs;
        return qs == null || qs.getView() == null || (secQSPanel = (SecQSPanel) this.mQs.getView().findViewById(R.id.quick_settings_panel)) == null || !secQSPanel.isAttachedToWindow();
    }

    public void onPanelClosed() {
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

    public void setPanelViewController(NotificationPanelViewController notificationPanelViewController) {
        this.mPanelViewController = notificationPanelViewController;
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

    public void clearAnimationState() {
    }

    public void onRtlChanged() {
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

    public void setQsExpansionPosition(float f) {
    }

    public void setTransitionToFullShadeAmount(float f) {
    }

    public void slide(float f, float f2, PanelSlideEventHandler.Direction direction, int i) {
    }

    public void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
    }
}
