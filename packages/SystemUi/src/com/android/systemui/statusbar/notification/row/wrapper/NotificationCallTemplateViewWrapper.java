package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.CallLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationCallTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public View appName;
    public final CallLayout callLayout;
    public View conversationBadgeBg;
    public View conversationIconContainer;
    public CachingIconView conversationIconView;
    public View conversationTitleView;
    public View expandBtn;
    public final int minHeightWithActions;

    public NotificationCallTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.minHeightWithActions = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_height, context);
        this.callLayout = (CallLayout) view;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getMinLayoutHeight() {
        return this.minHeightWithActions;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        CallLayout callLayout = this.callLayout;
        this.conversationIconContainer = callLayout.requireViewById(android.R.id.current_scene);
        this.conversationIconView = callLayout.requireViewById(android.R.id.costsMoney);
        this.conversationBadgeBg = callLayout.requireViewById(android.R.id.crossfade);
        this.expandBtn = callLayout.requireViewById(android.R.id.feedbackAudible);
        this.appName = callLayout.requireViewById(android.R.id.audio);
        this.conversationTitleView = callLayout.requireViewById(android.R.id.cycle);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setNotificationFaded(boolean z) {
        View view = this.expandBtn;
        if (view == null) {
            view = null;
        }
        NotificationFadeAware.setLayerTypeForFaded(view, z);
        View view2 = this.conversationIconContainer;
        NotificationFadeAware.setLayerTypeForFaded(view2 != null ? view2 : null, z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        View[] viewArr = new View[2];
        View view = this.appName;
        if (view == null) {
            view = null;
        }
        viewArr[0] = view;
        View view2 = this.conversationTitleView;
        if (view2 == null) {
            view2 = null;
        }
        viewArr[1] = view2;
        addTransformedViews(viewArr);
        View[] viewArr2 = new View[3];
        CachingIconView cachingIconView = this.conversationIconView;
        if (cachingIconView == null) {
            cachingIconView = null;
        }
        viewArr2[0] = cachingIconView;
        View view3 = this.conversationBadgeBg;
        if (view3 == null) {
            view3 = null;
        }
        viewArr2[1] = view3;
        View view4 = this.expandBtn;
        viewArr2[2] = view4 != null ? view4 : null;
        addViewsTransformingToSimilar(viewArr2);
    }
}
