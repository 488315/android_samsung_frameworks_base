package com.android.systemui.qs.customize;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSBlurPopUpMenu extends ListPopupWindow {
    public ListAdapter adapter;
    public PopupWindow.OnDismissListener dismissListener;
    public boolean onceShowed;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PopUpContent {
        public boolean checked;
        public final String text;

        public PopUpContent(String str, boolean z) {
            this.text = str;
            this.checked = z;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PopupListAdapter extends ArrayAdapter {
        public final int FONT_WEIGHT_REGULAR;
        public final int FONT_WEIGHT_SEMIBOLD;
        public final List popupList;

        public PopupListAdapter(Context context, List<? extends PopUpContent> list) {
            super(context, 0, list);
            this.popupList = list;
            this.FONT_WEIGHT_REGULAR = 400;
            this.FONT_WEIGHT_SEMIBOLD = VolteConstants.ErrorCode.BUSY_EVERYWHERE;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i) {
            return -2;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Object item = getItem(i);
            Intrinsics.checkNotNull(item);
            PopUpContent popUpContent = (PopUpContent) item;
            final boolean z = popUpContent.checked;
            if (view != null) {
                return view;
            }
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.qs_edit_popup_paddingVertical);
            getContext().getResources().getDimensionPixelSize(R.dimen.qs_edit_popup_paddingStart);
            int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.qs_edit_popup_minHeight);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.qs_setting_edit_popup_item, viewGroup, false);
            if (this.popupList.size() <= 1) {
                inflate.setPadding(inflate.getPaddingLeft(), dimensionPixelSize, inflate.getPaddingRight(), dimensionPixelSize);
            } else if (i == 0) {
                inflate.setMinimumHeight(dimensionPixelSize2);
                inflate.setPadding(inflate.getPaddingLeft(), dimensionPixelSize, inflate.getPaddingRight(), 0);
            } else if (i == this.popupList.size() - 1) {
                inflate.setMinimumHeight(dimensionPixelSize2);
                inflate.setPadding(inflate.getPaddingLeft(), 0, inflate.getPaddingRight(), dimensionPixelSize);
            } else {
                inflate.setMinimumHeight(dimensionPixelSize2 - dimensionPixelSize);
                inflate.setPadding(inflate.getPaddingLeft(), 0, inflate.getPaddingRight(), 0);
            }
            TextView textView = (TextView) inflate.requireViewById(R.id.textView);
            TextView textView2 = (TextView) inflate.requireViewById(R.id.textView);
            textView2.setText(popUpContent.text);
            textView2.setTextColor(textView2.getResources().getColor(R.color.qs_edit_content_text_color));
            Typeface create = Typeface.create("sec", 0);
            if (z) {
                textView.setTypeface(Typeface.create(create, this.FONT_WEIGHT_SEMIBOLD, false));
            } else {
                textView.setTypeface(Typeface.create(create, this.FONT_WEIGHT_REGULAR, false));
            }
            ((ImageView) inflate.requireViewById(R.id.checked)).setVisibility(z ? 0 : 4);
            ViewCompat.setAccessibilityDelegate(inflate, new AccessibilityDelegateCompat() { // from class: com.android.systemui.qs.customize.QSBlurPopUpMenu$PopupListAdapter$initializeAccessibilityNodeInfoForItem$1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                    accessibilityNodeInfoCompat.mInfo.setSelected(z);
                }
            });
            return inflate;
        }
    }

    public QSBlurPopUpMenu(Context context) {
        super(context);
        Resources resources = context.getResources();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(0);
        gradientDrawable.setCornerRadius(resources.getDimensionPixelSize(R.dimen.qs_setting_edit_popup_corner_radius));
        setBackgroundDrawable(gradientDrawable);
        setInputMethodMode(2);
        setModal(true);
        super.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.qs.customize.QSBlurPopUpMenu.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                PopupWindow.OnDismissListener onDismissListener = QSBlurPopUpMenu.this.dismissListener;
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
    public final void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.dismissListener = onDismissListener;
    }

    @Override // android.widget.ListPopupWindow
    public final void show() {
        int i;
        if (this.onceShowed) {
            return;
        }
        this.onceShowed = true;
        super.show();
        ListView listView = getListView();
        Intrinsics.checkNotNull(listView);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
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
            i = 0;
            for (int i2 = 0; i2 < count; i2++) {
                View view = listAdapter.getView(i2, null, listView2);
                view.measure(0, 0);
                int measuredWidth = view.getMeasuredWidth();
                if (measuredWidth >= i) {
                    i = measuredWidth;
                }
            }
        } else {
            i = 0;
        }
        View anchorView = getAnchorView();
        if (i > (anchorView != null ? anchorView.getWidth() : 0)) {
            View anchorView2 = getAnchorView();
            if (anchorView2 != null) {
                setContentWidth(anchorView2.getWidth());
            }
        } else {
            setContentWidth(i);
        }
        super.show();
    }
}
