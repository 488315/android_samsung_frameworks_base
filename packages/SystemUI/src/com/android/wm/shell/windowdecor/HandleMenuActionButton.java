package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HandleMenuActionButton extends LinearLayout {
    public final ImageView iconView;
    public final MarqueedTextView textView;

    public HandleMenuActionButton(Context context) {
        this(context, null, 0, 6, null);
    }

    public HandleMenuActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ HandleMenuActionButton(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public HandleMenuActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.desktop_mode_window_decor_handle_menu_action_button, (ViewGroup) this, true);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        this.iconView = imageView;
        MarqueedTextView marqueedTextView = (MarqueedTextView) findViewById(R.id.label);
        this.textView = marqueedTextView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.wm.shell.R.styleable.HandleMenuActionButton, 0, 0);
        setContentDescription(obtainStyledAttributes.getString(2));
        marqueedTextView.setText(obtainStyledAttributes.getString(2));
        marqueedTextView.setTextColor(obtainStyledAttributes.getColor(0, 0));
        imageView.setImageResource(obtainStyledAttributes.getResourceId(1, 0));
        imageView.setImageTintList(obtainStyledAttributes.getColorStateList(3));
        obtainStyledAttributes.recycle();
    }
}
