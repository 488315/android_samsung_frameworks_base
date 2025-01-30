package com.android.internal.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
public class SemLocalePickerItemView extends LinearLayout {
  public static final int LOCALE_PICKER_TYPE_HEADER = 0;
  public static final int LOCALE_PICKER_TYPE_ITEM = 1;
  int mItemType;

  public SemLocalePickerItemView(Context context, int defStyle, LayoutInflater li) {
    super(context);
    this.mItemType = defStyle;
    init(li);
  }

  private void init(LayoutInflater li) {
    View view;
    if (this.mItemType == 0) {
      view =
          li.inflate(
              C4337R.layout.sem_language_picker_section_header_category, (ViewGroup) this, false);
    } else {
      view = li.inflate(C4337R.layout.language_picker_item, (ViewGroup) this, false);
    }
    addView(view);
  }
}
