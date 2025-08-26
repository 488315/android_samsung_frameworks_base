package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class HybridGroupManager {
    public final Context mContext;
    public int mOverflowNumberColor;
    public int mOverflowNumberPadding;
    public float mOverflowNumberSize;

    public HybridGroupManager(Context context) {
        this.mContext = context;
        Resources resources = context.getResources();
        this.mOverflowNumberSize = resources.getDimensionPixelSize(R.dimen.group_overflow_number_size);
        this.mOverflowNumberPadding = resources.getDimensionPixelSize(R.dimen.group_overflow_number_padding);
    }

    public static CharSequence resolveText(Notification notification2) {
        CharSequence charSequence = notification2.extras.getCharSequence("android.text");
        return charSequence == null ? notification2.extras.getCharSequence("android.bigText") : charSequence;
    }

    public static CharSequence resolveTitle(Notification notification2) {
        CharSequence charSequence = notification2.extras.getCharSequence("android.title");
        return charSequence == null ? notification2.extras.getCharSequence("android.title.big") : charSequence;
    }
}
