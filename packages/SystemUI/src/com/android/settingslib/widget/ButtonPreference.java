package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ButtonPreference extends Preference {
    public Button mButton;
    public int mGravity;
    public Drawable mIcon;
    public CharSequence mTitle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum ButtonStyle {
        /* JADX INFO: Fake field, exist only in values array */
        FILLED_NORMAL(0, 0, R.layout.settingslib_expressive_button_filled),
        /* JADX INFO: Fake field, exist only in values array */
        FILLED_LARGE(0, 1, R.layout.settingslib_expressive_button_filled_large),
        /* JADX INFO: Fake field, exist only in values array */
        FILLED_EXTRA(0, 2, R.layout.settingslib_expressive_button_filled_extra),
        /* JADX INFO: Fake field, exist only in values array */
        TONAL_NORMAL(1, 0, R.layout.settingslib_expressive_button_tonal),
        /* JADX INFO: Fake field, exist only in values array */
        TONAL_LARGE(1, 1, R.layout.settingslib_expressive_button_tonal_large),
        /* JADX INFO: Fake field, exist only in values array */
        TONAL_EXTRA(1, 2, R.layout.settingslib_expressive_button_tonal_extra),
        /* JADX INFO: Fake field, exist only in values array */
        OUTLINE_NORMAL(2, 0, R.layout.settingslib_expressive_button_outline),
        /* JADX INFO: Fake field, exist only in values array */
        OUTLINE_LARGE(2, 1, R.layout.settingslib_expressive_button_outline_large),
        /* JADX INFO: Fake field, exist only in values array */
        OUTLINE_EXTRA(2, 2, R.layout.settingslib_expressive_button_outline_extra);

        private final int mLayoutId;
        private final int mSize;
        private final int mType;

        ButtonStyle(int i, int i2, int i3) {
            this.mType = i;
            this.mSize = i2;
            this.mLayoutId = i3;
        }

        public static int getLayoutId(int i, int i2) {
            for (ButtonStyle buttonStyle : values()) {
                if (buttonStyle.mType == i && buttonStyle.mSize == i2) {
                    return buttonStyle.mLayoutId;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public ButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2 = R.layout.settingslib_button_layout;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, 0);
            this.mTitle = obtainStyledAttributes.getText(4);
            this.mIcon = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.settingslib.widget.preference.button.R$styleable.ButtonPreference, i, 0);
            this.mGravity = obtainStyledAttributes2.getInt(0, 8388611);
            i2 = SettingsThemeHelper.isExpressiveTheme(context) ? ButtonStyle.getLayoutId(obtainStyledAttributes2.getInt(2, 0), obtainStyledAttributes2.getInt(1, 0)) : i2;
            obtainStyledAttributes2.recycle();
        }
        this.mLayoutResId = i2;
    }

    @Override // androidx.preference.Preference
    public final CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        this.mButton = (Button) preferenceViewHolder.findViewById(R.id.settingslib_button);
        setTitle(this.mTitle);
        setIcon(this.mIcon);
        int i = this.mGravity;
        if (i == 1 || i == 16 || i == 17) {
            this.mGravity = 1;
        } else {
            this.mGravity = 8388611;
        }
        Button button = this.mButton;
        if (button != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.gravity = this.mGravity;
            this.mButton.setLayoutParams(layoutParams);
        }
        Button button2 = this.mButton;
        if (button2 != null) {
            button2.setOnClickListener(null);
        }
        Button button3 = this.mButton;
        if (button3 != null) {
            boolean z = this.mSelectable;
            button3.setFocusable(z);
            this.mButton.setClickable(z);
            this.mButton.setEnabled(isEnabled());
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        Button button = this.mButton;
        if (button != null) {
            button.setEnabled(z);
        }
    }

    @Override // androidx.preference.Preference
    public final void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        Button button = this.mButton;
        if (button == null || drawable == null) {
            return;
        }
        if (button instanceof MaterialButton) {
            ((MaterialButton) button).setIcon(drawable);
            return;
        }
        int m = (int) ActionRow$$ExternalSyntheticOutline0.m(this.mContext, 1, 24.0f);
        drawable.setBounds(0, 0, m, m);
        this.mButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    @Override // androidx.preference.Preference
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        Button button = this.mButton;
        if (button != null) {
            button.setText(charSequence);
        }
    }

    public ButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ButtonPreference(Context context) {
        this(context, null);
    }
}
