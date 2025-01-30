package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HybridGroupManager {
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
}
