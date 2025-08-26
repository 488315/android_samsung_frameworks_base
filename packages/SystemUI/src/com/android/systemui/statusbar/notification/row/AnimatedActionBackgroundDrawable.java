package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import androidx.core.content.ContextCompat;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class AnimatedActionBackgroundDrawable extends RippleDrawable {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AnimatedActionBackgroundDrawable(Context context) {
        ColorStateList colorStateList = ContextCompat.getColorStateList(R.color.notification_ripple_untinted_color, context);
        colorStateList = colorStateList == null ? ColorStateList.valueOf(0) : colorStateList;
        Companion.getClass();
        super(colorStateList, new BaseBackgroundDrawable(context), null);
    }
}
