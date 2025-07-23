package com.android.systemui.mediaprojection.permission;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OptionsAdapter extends ArrayAdapter {
    public final List options;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OptionsAdapter(android.content.Context r5, java.util.List<com.android.systemui.mediaprojection.permission.ScreenShareOption> r6) {
        /*
            r4 = this;
            r0 = r6
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r0, r2)
            r1.<init>(r2)
            java.util.Iterator r0 = r0.iterator()
        L12:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L2e
            java.lang.Object r2 = r0.next()
            com.android.systemui.mediaprojection.permission.ScreenShareOption r2 = (com.android.systemui.mediaprojection.permission.ScreenShareOption) r2
            int r3 = r2.spinnerText
            java.lang.String r2 = r2.displayName
            java.lang.Object[] r2 = new java.lang.Object[]{r2}
            java.lang.String r2 = r5.getString(r3, r2)
            r1.add(r2)
            goto L12
        L2e:
            r0 = 2131559314(0x7f0d0392, float:1.8743969E38)
            r4.<init>(r5, r0, r1)
            r4.options = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.permission.OptionsAdapter.<init>(android.content.Context, java.util.List):void");
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View m = KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.screen_share_dialog_spinner_item_text, viewGroup, false);
        TextView textView = (TextView) m.requireViewById(android.R.id.text1);
        TextView textView2 = (TextView) m.requireViewById(android.R.id.text2);
        textView.setText((CharSequence) getItem(i));
        textView2.setText(((ScreenShareOption) this.options.get(i)).spinnerDisabledText);
        if (isEnabled(i)) {
            textView2.setVisibility(8);
            textView.setEnabled(true);
            return m;
        }
        textView2.setVisibility(0);
        textView2.setEnabled(false);
        textView2.setTextColor(getContext().getColorStateList(R.color.menu_item_text));
        textView2.setText(R.string.media_projection_entry_app_permission_dialog_single_app_not_supported);
        textView.setEnabled(false);
        return m;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public final boolean isEnabled(int i) {
        return ((ScreenShareOption) this.options.get(i)).spinnerDisabledText == null;
    }
}
