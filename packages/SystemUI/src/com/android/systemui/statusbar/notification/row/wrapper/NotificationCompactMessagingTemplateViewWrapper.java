package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* loaded from: classes3.dex */
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
            throw new IllegalArgumentException("Required value was null.");
        }
        this.compactMessagingView = viewGroup;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        this.conversationIconView = this.compactMessagingView.requireViewById(R.id.dvorak);
        this.titleView = this.compactMessagingView.findViewById(R.id.title);
        this.headerTextSecondary = this.compactMessagingView.findViewById(R.id.internalEmpty);
        this.subText = this.compactMessagingView.findViewById(R.id.inter_word);
        this.facePileTop = this.compactMessagingView.findViewById(R.id.drag);
        this.facePileBottom = this.compactMessagingView.findViewById(R.id.divider);
        this.facePileBottomBg = this.compactMessagingView.findViewById(R.id.dpad);
        this.expandBtn = this.compactMessagingView.requireViewById(R.id.flagRetrieveInteractiveWindows);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        addViewsTransformingToSimilar(this.conversationIconView, this.titleView, this.headerTextSecondary, this.subText, this.facePileTop, this.facePileBottom, this.facePileBottomBg, this.expandBtn);
    }
}
