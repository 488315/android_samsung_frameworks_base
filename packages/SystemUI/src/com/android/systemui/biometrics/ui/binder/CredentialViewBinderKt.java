package com.android.systemui.biometrics.ui.binder;

import android.widget.TextView;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public abstract class CredentialViewBinderKt {
    public static final void access$setTextOrHide(TextView textView, String str) {
        boolean z = str == null || StringsKt__StringsKt.isBlank(str);
        textView.setVisibility(z ? 8 : 0);
        if (z) {
            str = "";
        }
        textView.setText(str);
    }
}
