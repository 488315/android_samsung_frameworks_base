package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.android.systemui.R;
import com.android.systemui.volume.util.ContextUtils;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CardStackViewUtils {
    public static final CardStackViewUtils INSTANCE = new CardStackViewUtils();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Alpha {
        public static final Alpha INSTANCE = new Alpha();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class CommonAlpha {
            public abstract List getBaseColors();

            public abstract List getKeyColors();

            public final float getUnderlayBaseColor(int i) {
                if (((Number) getKeyColors().get(i)).floatValue() >= 1.0f) {
                    return 0.0f;
                }
                return ((Number) getBaseColors().get(i)).floatValue() / (1.0f - ((Number) getKeyColors().get(i)).floatValue());
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class MonochromeAlpha extends CommonAlpha {
            public static final MonochromeAlpha INSTANCE = new MonochromeAlpha();

            private MonochromeAlpha() {
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils.Alpha.CommonAlpha
            public final List getBaseColors() {
                Float valueOf = Float.valueOf(1.0f);
                Float valueOf2 = Float.valueOf(0.0f);
                return Arrays.asList(valueOf, valueOf2, valueOf2);
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils.Alpha.CommonAlpha
            public final List getKeyColors() {
                Float valueOf = Float.valueOf(0.0f);
                return Arrays.asList(valueOf, valueOf, valueOf);
            }
        }

        private Alpha() {
        }
    }

    private CardStackViewUtils() {
    }

    public static void addGradientBackground(Context context, int i, int i2, int i3, Drawable drawable, boolean z, boolean z2) {
        float dimenInt = ContextUtils.getDimenInt(R.dimen.oa_expanded_gradient_offset, context);
        float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.oa_expanded_gradient_radius);
        float dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_bg_radius);
        Drawable mutate = drawable != null ? drawable.mutate() : null;
        if (mutate instanceof GradientDrawable) {
            int argb = Color.argb(76, Color.red(i3), Color.green(i3), Color.blue(i3));
            GradientDrawable gradientDrawable = (GradientDrawable) mutate;
            if (!z2) {
                argb = 0;
            }
            gradientDrawable.setColors(new int[]{argb, 0});
            gradientDrawable.setShape(0);
            if (z) {
                gradientDrawable.setCornerRadius(dimensionPixelSize2);
            }
            gradientDrawable.setGradientRadius(dimensionPixelSize);
            gradientDrawable.setGradientType(1);
            float f = dimenInt / i;
            float f2 = dimenInt / i2;
            if (f > 1.0f) {
                f = context.getResources().getFloat(R.dimen.ongoing_activity_gradient_offset_default_x);
            }
            if (f2 > 1.0f) {
                f2 = context.getResources().getFloat(R.dimen.ongoing_activity_gradient_offset_default_y);
            }
            gradientDrawable.setGradientCenter(f, f2);
        }
    }
}
