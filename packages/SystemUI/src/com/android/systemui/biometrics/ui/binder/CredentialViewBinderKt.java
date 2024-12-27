package com.android.systemui.biometrics.ui.binder;

import android.widget.TextView;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class CredentialViewBinderKt {
    public static final void access$setTextOrHide(TextView textView, String str) {
        boolean z = str == null || StringsKt__StringsJVMKt.isBlank(str);
        textView.setVisibility(z ? 8 : 0);
        if (z) {
            str = "";
        }
        textView.setText(str);
    }
}
