package com.samsung.android.sdk.routines.v3.data;

import android.app.PendingIntent;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.sdk.routines.v3.internal.ExtraKey;

/* loaded from: classes4.dex */
public class ErrorContents {
    public final String a;
    public final String b;
    public final DialogButton c;

    public final class Builder {
        public String a;
        public final String b;

        public Builder(String str) {
            this.b = str;
        }
    }

    public class DialogButton {
        public final String a;
        public final PendingIntent b;

        public DialogButton(String str, PendingIntent pendingIntent) {
            this.a = str;
            this.b = pendingIntent;
        }
    }

    public ErrorContents(String str, String str2, DialogButton dialogButton) {
        this.a = str;
        this.b = str2;
        this.c = dialogButton;
    }

    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(ExtraKey.ERROR_DIALOG_MESSAGE.a, this.b);
        String str = this.a;
        if (!TextUtils.isEmpty(str)) {
            bundle.putString(ExtraKey.ERROR_DIALOG_TITLE.a, str);
        }
        DialogButton dialogButton = this.c;
        if (dialogButton != null) {
            bundle.putString(ExtraKey.ERROR_DIALOG_BUTTON_TEXT.a, dialogButton.a);
            bundle.putParcelable(ExtraKey.ERROR_DIALOG_BUTTON_INTENT.a, dialogButton.b);
        }
        return bundle;
    }
}
