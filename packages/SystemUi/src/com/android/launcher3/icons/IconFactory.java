package com.android.launcher3.icons;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IconFactory extends BaseIconFactory {
    public static IconFactory sPool;
    public static final Object sPoolSync = new Object();
    public final int mPoolId;
    public IconFactory next;

    private IconFactory(Context context, int i, int i2, int i3) {
        super(context, i, i2);
        this.mPoolId = i3;
    }

    public static IconFactory obtain(Context context) {
        synchronized (sPoolSync) {
            IconFactory iconFactory = sPool;
            if (iconFactory == null) {
                return new IconFactory(context, context.getResources().getConfiguration().densityDpi, context.getResources().getDimensionPixelSize(R.dimen.default_icon_bitmap_size), 0);
            }
            sPool = iconFactory.next;
            iconFactory.next = null;
            return iconFactory;
        }
    }

    @Override // com.android.launcher3.icons.BaseIconFactory, java.lang.AutoCloseable
    public final void close() {
        synchronized (sPoolSync) {
            if (this.mPoolId != 0) {
                return;
            }
            this.mWrapperBackgroundColor = -1;
            this.next = sPool;
            sPool = this;
        }
    }
}
