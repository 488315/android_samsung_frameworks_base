package com.android.systemui.common.p004ui.binder;

import android.widget.TextView;
import com.android.systemui.common.shared.model.Text;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
