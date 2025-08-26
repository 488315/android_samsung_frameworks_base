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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.wm.shell.R.styleable.HandleMenuActionButton, 0, 0);
        setContentDescription(typedArrayObtainStyledAttributes.getString(2));
        marqueedTextView.setText(typedArrayObtainStyledAttributes.getString(2));
        marqueedTextView.setTextColor(typedArrayObtainStyledAttributes.getColor(0, 0));
        imageView.setImageResource(typedArrayObtainStyledAttributes.getResourceId(1, 0));
        imageView.setImageTintList(typedArrayObtainStyledAttributes.getColorStateList(3));
        typedArrayObtainStyledAttributes.recycle();
    }
}
