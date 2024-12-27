package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public final class NotificationCustomViewWrapper extends NotificationViewWrapper {
    public boolean mIsLegacy;
    public final int mLegacyColor;

    public NotificationCustomViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mLegacyColor = expandableNotificationRow.getContext().getColor(R.color.notification_legacy_background_color);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getCustomBackgroundColor() {
        int customBackgroundColor = super.getCustomBackgroundColor();
        return (customBackgroundColor == 0 && this.mIsLegacy) ? this.mLegacyColor : customBackgroundColor;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        if (needsInversion(this.mView, this.mBackgroundColor)) {
            NotificationViewWrapper.invertViewLuminosity(this.mView);
            float[] fArr = {0.0f, 0.0f, 0.0f};
            ColorUtils.colorToHSL(this.mBackgroundColor, fArr);
            if (this.mBackgroundColor != 0) {
                float f = fArr[2];
                if (f > 0.5d) {
                    fArr[2] = 1.0f - f;
                    this.mBackgroundColor = ColorUtils.HSLToColor(fArr);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setLegacy(boolean z) {
        this.mIsLegacy = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setNotificationFaded(boolean z) {
        super.setNotificationFaded(z);
        NotificationFadeAware.setLayerTypeForFaded(this.mView, z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final void setVisible(boolean z) {
        super.setVisible(z);
        this.mView.setAlpha(z ? 1.0f : 0.0f);
    }
}
