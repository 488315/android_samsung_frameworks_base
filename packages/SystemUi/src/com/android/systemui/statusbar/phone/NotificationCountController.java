package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationCountController implements DarkIconDispatcher.DarkReceiver {
    public final Context mContext;
    public TextView mCountIcon;
    public int mCountIconSize;
    public int mCountTextSize;
    public float mDarkIntensity;
    public int mDarkModeIconColorSingleTone;
    public final NotificationIconAreaController mIconController;
    public int mLightModeIconColorSingleTone;
    public final ArrayList mTintAreas = new ArrayList();
    public List mEntries = List.of();

    public NotificationCountController(Context context, NotificationIconAreaController notificationIconAreaController) {
        this.mContext = context;
        this.mIconController = notificationIconAreaController;
    }

    public final void applyNotificationCountTint() {
        boolean z = false;
        boolean z2 = DarkIconDispatcher.isInAreas(this.mTintAreas, this.mCountIcon) || this.mCountIcon.getWidth() == 0;
        int intValue = ((Integer) ArgbEvaluator.sInstance.evaluate(this.mDarkIntensity, Integer.valueOf(this.mLightModeIconColorSingleTone), Integer.valueOf(this.mDarkModeIconColorSingleTone))).intValue();
        boolean z3 = this.mDarkModeIconColorSingleTone - intValue <= intValue - this.mLightModeIconColorSingleTone;
        if (z2) {
            z = z3;
        } else if (!z3) {
            z = true;
        }
        TextView textView = this.mCountIcon;
        int i = z ? R.color.notification_count_text_color_dark : R.color.notification_count_text_color_light;
        Context context = this.mContext;
        textView.setTextColor(context.getColor(i));
        this.mCountIcon.setBackground(context.getDrawable(z ? R.drawable.notification_count_background_dark : R.drawable.notification_count_background_light));
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        ArrayList arrayList2 = this.mTintAreas;
        arrayList2.clear();
        if (arrayList != null) {
            arrayList2.addAll(arrayList);
        }
        this.mDarkIntensity = f;
        if (this.mCountIcon != null) {
            applyNotificationCountTint();
        }
    }

    public final void updateNotificationCountLayoutParams() {
        int i;
        this.mCountIcon.setTextSize(0, this.mCountTextSize);
        if (this.mCountIcon.getText().length() >= 3) {
            int i2 = this.mCountIconSize;
            i = (i2 / 2) + i2;
        } else {
            i = this.mCountIconSize;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, this.mCountIconSize);
        layoutParams.gravity = 16;
        layoutParams.leftMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_count_icon_start_margin);
        this.mCountIcon.setLayoutParams(layoutParams);
    }
}
