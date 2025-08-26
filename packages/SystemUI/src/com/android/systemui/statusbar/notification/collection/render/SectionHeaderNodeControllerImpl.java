package com.android.systemui.statusbar.notification.collection.render;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* loaded from: classes3.dex */
public final class SectionHeaderNodeControllerImpl implements NodeController, SectionHeaderController {
    public SectionHeaderView _view;
    public final ActivityStarter activityStarter;
    public boolean clearAllButtonEnabled;
    public View.OnClickListener clearAllClickListener;
    public final String clickIntentAction;
    public final int headerTextResId;
    public final LayoutInflater layoutInflater;
    public final String nodeLabel;

    public SectionHeaderNodeControllerImpl(String str, LayoutInflater layoutInflater, int i, ActivityStarter activityStarter, String str2) {
        this.nodeLabel = str;
        this.layoutInflater = layoutInflater;
        this.headerTextResId = i;
        this.activityStarter = activityStarter;
        this.clickIntentAction = str2;
        new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl$onHeaderClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.activityStarter.startActivity(new Intent(this.this$0.clickIntentAction), true, true, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getView() {
        SectionHeaderView sectionHeaderView = this._view;
        sectionHeaderView.getClass();
        return sectionHeaderView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewAdded() {
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.setContentVisibleAnimated(true);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewRemoved() {
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.setContentVisible(false, false, null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reinflateView(ViewGroup viewGroup) {
        int iIndexOfChild;
        SectionHeaderView sectionHeaderView = this._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.removeFromTransientContainer();
            if (sectionHeaderView.getParent() == viewGroup) {
                iIndexOfChild = viewGroup.indexOfChild(sectionHeaderView);
                viewGroup.removeView(sectionHeaderView);
            } else {
                iIndexOfChild = -1;
            }
        }
        SectionHeaderView sectionHeaderView2 = (SectionHeaderView) this.layoutInflater.inflate(R.layout.status_bar_notification_section_header, viewGroup, false);
        int i = this.headerTextResId;
        sectionHeaderView2.mLabelTextId = Integer.valueOf(i);
        sectionHeaderView2.mLabelView.setText(i);
        View.OnClickListener onClickListener = this.clearAllClickListener;
        if (onClickListener != null) {
            sectionHeaderView2.mOnClearClickListener = onClickListener;
            sectionHeaderView2.mClearAllButton.setOnClickListener(onClickListener);
        }
        if (iIndexOfChild != -1) {
            viewGroup.addView(sectionHeaderView2, iIndexOfChild);
        }
        this._view = sectionHeaderView2;
        sectionHeaderView2.mClearAllButton.setVisibility(this.clearAllButtonEnabled ? 0 : 8);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void resetKeepInParentForAnimation() {
    }
}
