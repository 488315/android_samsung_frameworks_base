package com.android.systemui.common.ui.binder;

import android.widget.TextView;
import com.android.systemui.common.shared.model.Text;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
