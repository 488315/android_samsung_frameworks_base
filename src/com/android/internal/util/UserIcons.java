package com.android.internal.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class UserIcons {
    private static final int[] USER_ICON_COLORS = {R.color.user_icon_1, R.color.user_icon_2, R.color.user_icon_3, R.color.user_icon_4, R.color.user_icon_5, R.color.user_icon_6, R.color.user_icon_7, R.color.user_icon_8, R.color.user_icon_9};

    public static Bitmap convertToBitmap(Drawable drawable) {
        return convertToBitmapAtSize(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    public static Bitmap convertToBitmapAtUserIconSize(Resources resources, Drawable drawable) throws Resources.NotFoundException {
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.user_icon_size);
        return convertToBitmapAtSize(drawable, dimensionPixelSize, dimensionPixelSize);
    }

    private static Bitmap convertToBitmapAtSize(Drawable drawable, int i, int i2) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Drawable getDefaultUserIcon(Resources resources, int i, boolean z) {
        int i2 = z ? R.color.user_icon_default_white : R.color.user_icon_default_gray;
        if (i != -10000) {
            int[] iArr = USER_ICON_COLORS;
            i2 = iArr[i % iArr.length];
        }
        return getDefaultUserIconInColor(resources, resources.getColor(i2, null));
    }

    public static Drawable getDefaultUserIconInColor(Resources resources, int i) {
        Drawable drawableMutate = resources.getDrawable(R.drawable.mum_default, null).mutate();
        drawableMutate.setColorFilter(i, PorterDuff.Mode.SRC_IN);
        drawableMutate.setBounds(0, 0, drawableMutate.getIntrinsicWidth(), drawableMutate.getIntrinsicHeight());
        return drawableMutate;
    }

    public static int[] getUserIconColors(Resources resources) {
        int length = USER_ICON_COLORS.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = resources.getColor(USER_ICON_COLORS[i], null);
        }
        return iArr;
    }
}
