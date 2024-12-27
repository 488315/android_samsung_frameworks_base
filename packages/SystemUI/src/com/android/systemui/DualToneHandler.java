package com.android.systemui;

import android.content.Context;
import android.view.ContextThemeWrapper;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class DualToneHandler {
    public Color darkColor;
    public Color lightColor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Color {
        public final int background;
        public final int fill;
        public final int single;

        public Color(int i, int i2, int i3) {
            this.single = i;
            this.background = i2;
            this.fill = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Color)) {
                return false;
            }
            Color color = (Color) obj;
            return this.single == color.single && this.background == color.background && this.fill == color.fill;
        }

        public final int hashCode() {
            return Integer.hashCode(this.fill) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.background, Integer.hashCode(this.single) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Color(single=");
            sb.append(this.single);
            sb.append(", background=");
            sb.append(this.background);
            sb.append(", fill=");
            return Anchor$$ExternalSyntheticOutline0.m(this.fill, ")", sb);
        }
    }

    public DualToneHandler(Context context) {
        setColorsFromContext(context);
    }

    public final void setColorsFromContext(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.darkIconTheme, context));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.lightIconTheme, context));
        com.android.settingslib.flags.Flags.newStatusBarIcons();
        this.darkColor = new Color(Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.singleToneColor, 0), Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.iconBackgroundColor, 0), Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.fillColor, 0));
        this.lightColor = new Color(Utils.getColorAttrDefaultColor(contextThemeWrapper2, R.attr.singleToneColor, 0), Utils.getColorAttrDefaultColor(contextThemeWrapper2, R.attr.iconBackgroundColor, 0), Utils.getColorAttrDefaultColor(contextThemeWrapper2, R.attr.fillColor, 0));
    }
}
