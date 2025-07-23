package com.android.settingslib.widget;

import android.R;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ZeroStatePreference extends Preference {
    public final Drawable tintedIcon;

    public ZeroStatePreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setFocusable(false);
        preferenceViewHolder.itemView.setClickable(false);
        View findViewById = preferenceViewHolder.findViewById(R.id.icon);
        ImageView imageView = findViewById instanceof ImageView ? (ImageView) findViewById : null;
        if (imageView != null) {
            Drawable drawable = this.tintedIcon;
            if (drawable == null) {
                drawable = getIcon();
            }
            imageView.setImageDrawable(drawable);
        }
    }

    public ZeroStatePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public ZeroStatePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ ZeroStatePreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public ZeroStatePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int color = context.getColor(com.android.systemui.R.color.settingslib_materialColorOnSecondaryContainer);
        setSelectable(false);
        this.mLayoutResId = com.android.systemui.R.layout.settingslib_expressive_preference_zerostate;
        Drawable icon = getIcon();
        if (icon != null) {
            Drawable mutate = icon.mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            this.tintedIcon = mutate;
        }
    }
}
