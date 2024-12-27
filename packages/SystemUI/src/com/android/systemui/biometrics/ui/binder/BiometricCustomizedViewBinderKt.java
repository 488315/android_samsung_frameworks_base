package com.android.systemui.biometrics.ui.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class BiometricCustomizedViewBinderKt {
    public static final LinearLayout inflateContentView(LayoutInflater layoutInflater, int i, String str) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(i, (ViewGroup) null);
        TextView textView = (TextView) linearLayout.requireViewById(R.id.customized_view_description);
        if (str == null || str.length() == 0) {
            textView.setVisibility(8);
        } else {
            textView.setText(str);
        }
        return linearLayout;
    }
}
