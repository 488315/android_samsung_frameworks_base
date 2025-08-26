package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.android.systemui.R;
import com.android.systemui.volume.util.ContextUtils;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public final class CardStackViewUtils {
    public static final CardStackViewUtils INSTANCE = new CardStackViewUtils();

    public final class Alpha {
        public static final Alpha INSTANCE = new Alpha();

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

        public final class MonochromeAlpha extends CommonAlpha {
            public static final MonochromeAlpha INSTANCE = new MonochromeAlpha();

            private MonochromeAlpha() {
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils.Alpha.CommonAlpha
            public final List getBaseColors() {
                Float fValueOf = Float.valueOf(1.0f);
                Float fValueOf2 = Float.valueOf(0.0f);
                return Arrays.asList(fValueOf, fValueOf2, fValueOf2);
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils.Alpha.CommonAlpha
            public final List getKeyColors() {
                Float fValueOf = Float.valueOf(0.0f);
                return Arrays.asList(fValueOf, fValueOf, fValueOf);
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
        Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
        if (drawableMutate instanceof GradientDrawable) {
            int iArgb = Color.argb(76, Color.red(i3), Color.green(i3), Color.blue(i3));
            GradientDrawable gradientDrawable = (GradientDrawable) drawableMutate;
            if (!z2) {
                iArgb = 0;
            }
            gradientDrawable.setColors(new int[]{iArgb, 0});
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
