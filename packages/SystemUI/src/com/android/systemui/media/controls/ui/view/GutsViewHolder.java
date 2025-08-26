package com.android.systemui.media.controls.ui.view;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.monet.ColorScheme;
import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class GutsViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set ids = ArraysKt___ArraysKt.toSet(new Integer[]{Integer.valueOf(R.id.remove_text), Integer.valueOf(R.id.cancel), Integer.valueOf(R.id.dismiss), Integer.valueOf(R.id.settings)});
    public final View cancel;
    public final TextView cancelText;
    public ColorScheme colorScheme;
    public final ViewGroup dismiss;
    public final TextView dismissText;
    public final TextView gutsText;
    public boolean isDismissible = true;
    public final ImageButton settings;
    public Integer textColorFixed;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public GutsViewHolder(View view) {
        this.gutsText = (TextView) view.requireViewById(R.id.remove_text);
        this.cancel = view.requireViewById(R.id.cancel);
        this.cancelText = (TextView) view.requireViewById(R.id.cancel_text);
        this.dismiss = (ViewGroup) view.requireViewById(R.id.dismiss);
        this.dismissText = (TextView) view.requireViewById(R.id.dismiss_text);
        this.settings = (ImageButton) view.requireViewById(R.id.settings);
    }

    public final void setColors(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
        Integer num = this.textColorFixed;
        if (num != null) {
            setTextColor(num.intValue());
        }
        DynamicScheme dynamicScheme = colorScheme.mMaterialScheme;
        dynamicScheme.getClass();
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(new MaterialDynamicColors().primaryFixed().getArgb(dynamicScheme));
        this.dismissText.setBackgroundTintList(colorStateListValueOf);
        this.cancelText.setBackgroundTintList(colorStateListValueOf);
        DynamicScheme dynamicScheme2 = colorScheme.mMaterialScheme;
        dynamicScheme2.getClass();
        int argb = new MaterialDynamicColors().onPrimaryFixed().getArgb(dynamicScheme2);
        this.dismissText.setTextColor(argb);
        if (this.isDismissible) {
            return;
        }
        this.cancelText.setTextColor(argb);
    }

    public final void setTextColor(int i) {
        this.textColorFixed = Integer.valueOf(i);
        this.gutsText.setTextColor(i);
        this.settings.setImageTintList(ColorStateList.valueOf(i));
        if (this.isDismissible) {
            this.cancelText.setTextColor(i);
        }
    }
}
