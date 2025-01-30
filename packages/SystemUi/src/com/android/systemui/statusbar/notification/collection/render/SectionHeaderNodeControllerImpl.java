package com.android.systemui.statusbar.notification.collection.render;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SectionHeaderNodeControllerImpl implements NodeController, SectionHeaderController {
    public SectionHeaderView _view;
    public final ActivityStarter activityStarter;
    public boolean clearAllButtonEnabled;
    public View.OnClickListener clearAllClickListener;
    public final String clickIntentAction;
    public final int headerTextResId;
    public final LayoutInflater layoutInflater;
    public final String nodeLabel;
    public final SectionHeaderNodeControllerImpl$onHeaderClickListener$1 onHeaderClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl$onHeaderClickListener$1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SectionHeaderNodeControllerImpl.this.activityStarter.startActivity(new Intent(SectionHeaderNodeControllerImpl.this.clickIntentAction), true, true, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl$onHeaderClickListener$1] */
    public SectionHeaderNodeControllerImpl(String str, LayoutInflater layoutInflater, int i, ActivityStarter activityStarter, String str2) {
        this.nodeLabel = str;
        this.layoutInflater = layoutInflater;
        this.headerTextResId = i;
        this.activityStarter = activityStarter;
        this.clickIntentAction = str2;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void addChildAt(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getChildAt(int i) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final int getChildCount() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getView() {
        SectionHeaderView sectionHeaderView = this._view;
        Intrinsics.checkNotNull(sectionHeaderView);
        return sectionHeaderView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void moveChildTo(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewAdded() {
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView == null) {
            return;
        }
        sectionHeaderView.setContentVisible(null, true, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reinflateView(NotificationStackScrollLayout notificationStackScrollLayout) {
        int i;
        View.OnClickListener onClickListener;
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.removeFromTransientContainer();
            if (sectionHeaderView.getParent() == notificationStackScrollLayout) {
                i = notificationStackScrollLayout.indexOfChild(sectionHeaderView);
                notificationStackScrollLayout.removeView(sectionHeaderView);
                SectionHeaderView sectionHeaderView2 = (SectionHeaderView) this.layoutInflater.inflate(R.layout.status_bar_notification_section_header, (ViewGroup) notificationStackScrollLayout, false);
                int i2 = this.headerTextResId;
                sectionHeaderView2.mLabelTextId = Integer.valueOf(i2);
                sectionHeaderView2.mLabelView.setText(i2);
                SectionHeaderNodeControllerImpl$onHeaderClickListener$1 sectionHeaderNodeControllerImpl$onHeaderClickListener$1 = this.onHeaderClickListener;
                sectionHeaderView2.mLabelClickListener = sectionHeaderNodeControllerImpl$onHeaderClickListener$1;
                sectionHeaderView2.mLabelView.setOnClickListener(sectionHeaderNodeControllerImpl$onHeaderClickListener$1);
                onClickListener = this.clearAllClickListener;
                if (onClickListener != null) {
                    sectionHeaderView2.mOnClearClickListener = onClickListener;
                    sectionHeaderView2.mClearAllButton.setOnClickListener(onClickListener);
                }
                if (i != -1) {
                    notificationStackScrollLayout.addView(sectionHeaderView2, i);
                }
                this._view = sectionHeaderView2;
                sectionHeaderView2.mClearAllButton.setVisibility(this.clearAllButtonEnabled ? 0 : 8);
            }
        }
        i = -1;
        SectionHeaderView sectionHeaderView22 = (SectionHeaderView) this.layoutInflater.inflate(R.layout.status_bar_notification_section_header, (ViewGroup) notificationStackScrollLayout, false);
        int i22 = this.headerTextResId;
        sectionHeaderView22.mLabelTextId = Integer.valueOf(i22);
        sectionHeaderView22.mLabelView.setText(i22);
        SectionHeaderNodeControllerImpl$onHeaderClickListener$1 sectionHeaderNodeControllerImpl$onHeaderClickListener$12 = this.onHeaderClickListener;
        sectionHeaderView22.mLabelClickListener = sectionHeaderNodeControllerImpl$onHeaderClickListener$12;
        sectionHeaderView22.mLabelView.setOnClickListener(sectionHeaderNodeControllerImpl$onHeaderClickListener$12);
        onClickListener = this.clearAllClickListener;
        if (onClickListener != null) {
        }
        if (i != -1) {
        }
        this._view = sectionHeaderView22;
        sectionHeaderView22.mClearAllButton.setVisibility(this.clearAllButtonEnabled ? 0 : 8);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void removeChild(NodeController nodeController, boolean z) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewMoved() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewRemoved() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void resetKeepInParentForAnimation() {
    }
}
