package com.android.systemui.p016qs.customize.setting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSSettingEditPopUpMenu extends ListPopupWindow {
    public ListAdapter adapter;
    public PopupWindow.OnDismissListener dismissListener;
    public boolean onceShowed;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EditPopUpContent {
        public boolean checked;
        public final String text;

        public EditPopUpContent(String str, boolean z) {
            this.text = str;
            this.checked = z;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopupListAdapter extends ArrayAdapter {
        public PopupListAdapter(Context context, List<? extends EditPopUpContent> list) {
            super(context, 0, list);
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i) {
            return -2;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Object item = getItem(i);
            Intrinsics.checkNotNull(item);
            EditPopUpContent editPopUpContent = (EditPopUpContent) item;
            final boolean z = editPopUpContent.checked;
            if (view != null) {
                return view;
            }
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qs_setting_edit_popup_item, viewGroup, false);
            TextView textView = (TextView) inflate.requireViewById(R.id.textView);
            TextView textView2 = (TextView) inflate.requireViewById(R.id.textView);
            textView2.setText(editPopUpContent.text);
            textView2.setTextColor(textView2.getResources().getColor(R.color.qs_edit_content_text_color));
            Typeface create = Typeface.create("sec", 0);
            if (z) {
                textView.setTypeface(Typeface.create(create, VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
            } else {
                textView.setTypeface(Typeface.create(create, 400, false));
            }
            ((ImageView) inflate.requireViewById(R.id.checked)).setVisibility(z ? 0 : 4);
            ViewCompat.setAccessibilityDelegate(inflate, new AccessibilityDelegateCompat() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditPopUpMenu$PopupListAdapter$initializeAccessibilityNodeInfoForItem$1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                    accessibilityNodeInfoCompat.setSelected(z);
                }
            });
            return inflate;
        }
    }

    public SecQSSettingEditPopUpMenu(Context context) {
        super(context);
        Resources resources = context.getResources();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(0);
        gradientDrawable.setCornerRadius(resources.getDimensionPixelSize(R.dimen.qs_setting_edit_popup_corner_radius));
        setBackgroundDrawable(gradientDrawable);
        setInputMethodMode(2);
        setModal(true);
        super.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditPopUpMenu.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                PopupWindow.OnDismissListener onDismissListener = SecQSSettingEditPopUpMenu.this.dismissListener;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
        setOverlapAnchor(true);
    }

    @Override // android.widget.ListPopupWindow
    public final void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.adapter = listAdapter;
    }

    @Override // android.widget.ListPopupWindow
    public final void setDropDownGravity(int i) {
        super.setDropDownGravity(i);
    }

    @Override // android.widget.ListPopupWindow
    public final void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.dismissListener = onDismissListener;
    }

    @Override // android.widget.ListPopupWindow
    public final void show() {
        if (this.onceShowed) {
            return;
        }
        this.onceShowed = true;
        super.show();
        ListView listView = getListView();
        Intrinsics.checkNotNull(listView);
        int i = 0;
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE || ((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(listView.getResources().getColor(R.color.qs_edit_popup_background_color));
            gradientDrawable.setCornerRadius(listView.getResources().getDimensionPixelSize(R.dimen.qs_setting_edit_popup_corner_radius));
            gradientDrawable.setStroke(1, listView.getResources().getColor(R.color.qs_edit_popup_background_color));
            listView.setBackgroundDrawable(gradientDrawable);
        } else if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            builder.setBackgroundColor(listView.getResources().getColor(R.color.qs_edit_ripple_bg_color));
            builder.setBackgroundCornerRadius(listView.getResources().getDimensionPixelSize(R.dimen.qs_setting_edit_popup_corner_radius));
            listView.semSetBlurInfo(builder.build());
        }
        ListView listView2 = getListView();
        Intrinsics.checkNotNull(listView2);
        ListAdapter listAdapter = this.adapter;
        if (listAdapter != null) {
            int count = listAdapter.getCount();
            int i2 = 0;
            for (int i3 = 0; i3 < count; i3++) {
                View view = listAdapter.getView(i3, null, listView2);
                view.measure(0, 0);
                int measuredWidth = view.getMeasuredWidth();
                if (measuredWidth >= i2) {
                    i2 = measuredWidth;
                }
            }
            i = i2;
        }
        if (i > getAnchorView().getWidth()) {
            setContentWidth(getAnchorView().getWidth());
        } else {
            setContentWidth(i);
        }
        super.show();
    }
}
