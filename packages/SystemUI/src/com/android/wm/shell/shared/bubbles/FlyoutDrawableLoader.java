package com.android.wm.shell.shared.bubbles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public final class FlyoutDrawableLoader {
    static {
        new FlyoutDrawableLoader();
    }

    private FlyoutDrawableLoader() {
    }

    public static final Drawable loadFlyoutDrawable(Context context, Icon icon) {
        if (icon == null) {
            return null;
        }
        try {
            if (icon.getType() == 4 || icon.getType() == 6) {
                context.grantUriPermission(context.getPackageName(), icon.getUri(), 1);
            }
            return icon.loadDrawable(context);
        } catch (Exception e) {
            MotionLayout$$ExternalSyntheticOutline0.m("loadFlyoutDrawable failed: ", e.getMessage(), "FlyoutDrawableLoader");
            return null;
        }
    }
}
