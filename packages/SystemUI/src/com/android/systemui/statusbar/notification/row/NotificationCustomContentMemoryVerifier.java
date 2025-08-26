package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public final class NotificationCustomContentMemoryVerifier {
    public static final NotificationCustomContentMemoryVerifier INSTANCE = new NotificationCustomContentMemoryVerifier();

    private NotificationCustomContentMemoryVerifier() {
    }

    public static int computeDrawableSize(Drawable drawable) {
        if (drawable == null) {
            return 0;
        }
        if (drawable instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            return computeDrawableSize(adaptiveIconDrawable.getMonochrome()) + computeDrawableSize(adaptiveIconDrawable.getBackground()) + computeDrawableSize(adaptiveIconDrawable.getForeground());
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap().getAllocationByteCount();
        }
        return drawable.getIntrinsicHeight() * drawable.getIntrinsicWidth() * 4;
    }

    public static final int computeViewHierarchyImageViewSize(View view) {
        if (!(view instanceof ViewGroup)) {
            if (!(view instanceof ImageView)) {
                return 0;
            }
            INSTANCE.getClass();
            return computeDrawableSize(((ImageView) view).getDrawable());
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        int iComputeViewHierarchyImageViewSize = 0;
        for (int i = 0; i < childCount; i++) {
            iComputeViewHierarchyImageViewSize += computeViewHierarchyImageViewSize(viewGroup.getChildAt(i));
        }
        return iComputeViewHierarchyImageViewSize;
    }

    public final int getStripViewSizeLimit(Context context) {
        return context.getResources().getInteger(R.integer.config_shutdownBatteryTemperature);
    }

    public final int getWarnViewSizeLimit(Context context) {
        return context.getResources().getInteger(R.integer.config_sideFpsToastTimeout);
    }
}
