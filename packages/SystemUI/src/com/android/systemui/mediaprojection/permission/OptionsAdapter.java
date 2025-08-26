package com.android.systemui.mediaprojection.permission;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: classes2.dex */
public final class OptionsAdapter extends ArrayAdapter {
    public final List options;

    public OptionsAdapter(Context context, List<ScreenShareOption> list) {
        List<ScreenShareOption> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (ScreenShareOption screenShareOption : list2) {
            arrayList.add(context.getString(screenShareOption.spinnerText, screenShareOption.displayName));
        }
        super(context, R.layout.screen_share_dialog_spinner_text, arrayList);
        this.options = list;
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View viewM = KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.screen_share_dialog_spinner_item_text, viewGroup, false);
        TextView textView = (TextView) viewM.requireViewById(android.R.id.text1);
        TextView textView2 = (TextView) viewM.requireViewById(android.R.id.text2);
        textView.setText((CharSequence) getItem(i));
        textView2.setText(((ScreenShareOption) this.options.get(i)).spinnerDisabledText);
        if (isEnabled(i)) {
            textView2.setVisibility(8);
            textView.setEnabled(true);
            return viewM;
        }
        textView2.setVisibility(0);
        textView2.setEnabled(false);
        textView2.setTextColor(getContext().getColorStateList(R.color.menu_item_text));
        textView2.setText(R.string.media_projection_entry_app_permission_dialog_single_app_not_supported);
        textView.setEnabled(false);
        return viewM;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public final boolean isEnabled(int i) {
        return ((ScreenShareOption) this.options.get(i)).spinnerDisabledText == null;
    }
}
