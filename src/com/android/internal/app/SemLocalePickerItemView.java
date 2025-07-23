package com.android.internal.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class SemLocalePickerItemView extends LinearLayout {
    public static final int LOCALE_PICKER_TYPE_HEADER = 0;
    public static final int LOCALE_PICKER_TYPE_ITEM = 1;
    int mItemType;

    public SemLocalePickerItemView(Context context, int i, LayoutInflater layoutInflater) {
        super(context);
        this.mItemType = i;
        init(layoutInflater);
    }

    private void init(LayoutInflater layoutInflater) {
        View inflate;
        if (this.mItemType == 0) {
            inflate = layoutInflater.inflate(R.layout.sem_language_picker_section_header_category, (ViewGroup) this, false);
        } else {
            inflate = layoutInflater.inflate(R.layout.language_picker_item, (ViewGroup) this, false);
        }
        addView(inflate);
    }
}
