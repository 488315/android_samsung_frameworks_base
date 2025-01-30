package com.android.wm.shell.windowdecor;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;
import android.widget.TextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WindowMenuDexItemView extends LinearLayout {
    public final TextView mTextView;

    public WindowMenuDexItemView(Context context, AttributeSet attributeSet) {
        super(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight), attributeSet);
        Resources resources = ((LinearLayout) this).mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sec_dex_decor_more_menu_button_padding_horizontal);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sec_dex_decor_more_menu_button_padding_vertical);
        TextView textView = new TextView(((LinearLayout) this).mContext);
        this.mTextView = textView;
        textView.setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
        textView.setGravity(16);
        addView(textView, -1, -2);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
        setTooltipNull(true);
        setClickable(true);
    }

    @Override // android.view.View
    public final void setContentDescription(CharSequence charSequence) {
        super.setContentDescription(charSequence);
    }

    public final void setTextView(int i, boolean z) {
        Resources resources = ((LinearLayout) this).mContext.getResources();
        this.mTextView.setText(i != -1 ? resources.getText(i) : getContentDescription());
        this.mTextView.setTextColor(resources.getColorStateList(z ? com.android.systemui.R.color.sec_decor_new_dex_more_icon_color_dark : com.android.systemui.R.color.sec_decor_new_dex_more_icon_color_light, null));
        this.mTextView.setTextSize(0, resources.getDimensionPixelSize(com.android.systemui.R.dimen.sec_dex_decor_menu_text_size));
    }

    public final void setVerticalPadding(boolean z) {
        int dimensionPixelSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_dex_decor_more_menu_window_padding);
        int paddingTop = z ? this.mTextView.getPaddingTop() + dimensionPixelSize : this.mTextView.getPaddingTop();
        int paddingBottom = z ? this.mTextView.getPaddingBottom() : this.mTextView.getPaddingBottom() + dimensionPixelSize;
        TextView textView = this.mTextView;
        textView.setPadding(textView.getPaddingLeft(), paddingTop, this.mTextView.getPaddingRight(), paddingBottom);
    }
}
