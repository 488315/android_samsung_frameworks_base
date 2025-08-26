package com.android.launcher3.icons;

import android.content.Context;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class IconFactory extends BaseIconFactory {
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
            try {
                IconFactory iconFactory = sPool;
                if (iconFactory == null) {
                    return new IconFactory(context, context.getResources().getConfiguration().densityDpi, context.getResources().getDimensionPixelSize(R.dimen.default_icon_bitmap_size), 0);
                }
                sPool = iconFactory.next;
                iconFactory.next = null;
                return iconFactory;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.launcher3.icons.BaseIconFactory, java.lang.AutoCloseable
    public final void close() {
        synchronized (sPoolSync) {
            try {
                if (this.mPoolId != 0) {
                    return;
                }
                this.mWrapperBackgroundColor = -1;
                this.next = sPool;
                sPool = this;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
