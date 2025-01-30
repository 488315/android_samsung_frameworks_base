package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        int i = this.mBackgroundColor;
        View view = this.mView;
        if (needsInversion(view, i)) {
            NotificationViewWrapper.invertViewLuminosity(view);
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
