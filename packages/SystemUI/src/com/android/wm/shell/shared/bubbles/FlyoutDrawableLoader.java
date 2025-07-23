package com.android.wm.shell.shared.bubbles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            if (icon.getType() != 4) {
                if (icon.getType() == 6) {
                }
                return icon.loadDrawable(context);
            }
            context.grantUriPermission(context.getPackageName(), icon.getUri(), 1);
            return icon.loadDrawable(context);
        } catch (Exception e) {
            MotionLayout$$ExternalSyntheticOutline0.m("loadFlyoutDrawable failed: ", e.getMessage(), "FlyoutDrawableLoader");
            return null;
        }
    }
}
