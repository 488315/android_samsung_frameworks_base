package com.android.systemui.biometrics.ui.binder;

import android.widget.TextView;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
