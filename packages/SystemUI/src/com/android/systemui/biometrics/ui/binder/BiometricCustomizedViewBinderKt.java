package com.android.systemui.biometrics.ui.binder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.biometrics.Utils;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* loaded from: classes.dex */
public abstract class BiometricCustomizedViewBinderKt {
    public static final LinearLayout inflateContentView(LayoutInflater layoutInflater, int i, String str) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(i, (ViewGroup) null);
        TextView textView = (TextView) linearLayout.requireViewById(R.id.customized_view_description);
        if (str == null || str.length() == 0) {
            textView.setVisibility(8);
            return linearLayout;
        }
        textView.setText(Utils.ellipsize(IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType, str));
        return linearLayout;
    }
}
