package com.android.systemui.common.ui.binder;

import android.widget.TextView;
import com.android.systemui.common.shared.model.Text;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public final class TextViewBinder {
    public static final TextViewBinder INSTANCE = new TextViewBinder();

    private TextViewBinder() {
    }

    public static void bind(TextView textView, Text text) {
        String string;
        if (text instanceof Text.Resource) {
            string = textView.getContext().getString(((Text.Resource) text).res);
        } else {
            if (!(text instanceof Text.Loaded)) {
                throw new NoWhenBranchMatchedException();
            }
            string = ((Text.Loaded) text).text;
        }
        textView.setText(string);
    }
}
