package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationCompactMessagingTemplateViewWrapper extends NotificationCompactHeadsUpTemplateViewWrapper {
    public final ViewGroup compactMessagingView;
    public CachingIconView conversationIconView;
    public View expandBtn;
    public View facePileBottom;
    public View facePileBottomBg;
    public View facePileTop;
    public View headerTextSecondary;
    public View subText;
    public View titleView;

    public NotificationCompactMessagingTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
        if (viewGroup == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        this.compactMessagingView = viewGroup;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        this.conversationIconView = this.compactMessagingView.requireViewById(R.id.dangerous);
        this.titleView = this.compactMessagingView.findViewById(R.id.title);
        this.headerTextSecondary = this.compactMessagingView.findViewById(R.id.inbox_text2);
        this.subText = this.compactMessagingView.findViewById(R.id.inbox_text0);
        this.facePileTop = this.compactMessagingView.findViewById(R.id.customPanel);
        this.facePileBottom = this.compactMessagingView.findViewById(R.id.crosshair);
        this.facePileBottomBg = this.compactMessagingView.findViewById(R.id.current_scene);
        this.expandBtn = this.compactMessagingView.requireViewById(R.id.fill);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        addViewsTransformingToSimilar(this.conversationIconView, this.titleView, this.headerTextSecondary, this.subText, this.facePileTop, this.facePileBottom, this.facePileBottomBg, this.expandBtn);
    }
}
