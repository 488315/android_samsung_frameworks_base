package com.android.systemui.common.ui.binder;

import android.widget.TextView;
import com.android.systemui.common.shared.model.Text;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextViewBinder {
    public static final TextViewBinder INSTANCE = new TextViewBinder();

    private TextViewBinder() {
    }

    public static void bind(TextView textView, Text text) {
        String str;
        if (text instanceof Text.Resource) {
            str = textView.getContext().getString(((Text.Resource) text).res);
        } else {
            if (!(text instanceof Text.Loaded)) {
                throw new NoWhenBranchMatchedException();
            }
            str = ((Text.Loaded) text).text;
        }
        textView.setText(str);
    }
}
