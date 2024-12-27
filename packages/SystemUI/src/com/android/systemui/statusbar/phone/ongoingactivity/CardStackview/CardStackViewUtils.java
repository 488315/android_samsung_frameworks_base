package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.android.systemui.R;
import com.android.systemui.volume.util.ContextUtils;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CardStackViewUtils {
    public static final CardStackViewUtils INSTANCE = new CardStackViewUtils();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Alpha {
        public static final Alpha INSTANCE = new Alpha();

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class MonochromeAlpha extends CommonAlpha {
            public static final MonochromeAlpha INSTANCE = new MonochromeAlpha();

            private MonochromeAlpha() {
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils.Alpha.CommonAlpha
            public final List getBaseColors() {
                Float valueOf = Float.valueOf(1.0f);
                Float valueOf2 = Float.valueOf(0.0f);
                return CollectionsKt__CollectionsKt.listOf(valueOf, valueOf2, valueOf2);
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils.Alpha.CommonAlpha
            public final List getKeyColors() {
                Float valueOf = Float.valueOf(0.0f);
                return CollectionsKt__CollectionsKt.listOf(valueOf, valueOf, valueOf);
            }
        }

        private Alpha() {
        }
    }

    private CardStackViewUtils() {
    }

    public static void addGradientBackground(Context context, int i, int i2, int i3, Drawable drawable, boolean z, boolean z2) {
        float dimenInt = ContextUtils.getDimenInt(R.dimen.oa_expanded_gradient_offset, context);
        float dimenInt2 = ContextUtils.getDimenInt(R.dimen.oa_expanded_gradient_radius, context);
        float dimenInt3 = ContextUtils.getDimenInt(R.dimen.ongoing_activity_card_bg_radius, context);
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
                gradientDrawable.setCornerRadius(dimenInt3);
            }
            gradientDrawable.setGradientRadius(dimenInt2);
            gradientDrawable.setGradientType(1);
            gradientDrawable.setGradientCenter(dimenInt / i, dimenInt / i2);
        }
    }
}
