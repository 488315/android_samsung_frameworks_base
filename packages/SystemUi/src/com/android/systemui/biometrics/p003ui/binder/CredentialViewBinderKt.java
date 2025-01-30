package com.android.systemui.biometrics.p003ui.binder;

import android.widget.TextView;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
