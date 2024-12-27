package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationDecoratedCustomViewWrapper extends NotificationTemplateViewWrapper {
    public View mWrappedView;

    public NotificationDecoratedCustomViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mWrappedView = null;
    }

    public static View getWrappedCustomView(View view) {
        ViewGroup viewGroup;
        Integer num;
        if (view == null || (viewGroup = (ViewGroup) view.findViewById(R.id.popup_submenu_presenter)) == null || (num = (Integer) viewGroup.getTag(R.id.pin_text)) == null || num.intValue() == -1) {
            return null;
        }
        return viewGroup.getChildAt(num.intValue());
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        this.mWrappedView = getWrappedCustomView(this.mView);
        if (needsInversion(this.mWrappedView, resolveBackgroundColor())) {
            NotificationViewWrapper.invertViewLuminosity(this.mWrappedView);
        }
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setNotificationFaded(boolean z) {
        super.setNotificationFaded(z);
        NotificationFadeAware.setLayerTypeForFaded(this.mWrappedView, z);
    }
}
