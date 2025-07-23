package androidx.slice.widget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ActionRow extends FrameLayout {
    public final LinearLayout mActionsGroup;

    public ActionRow(Context context, boolean z) {
        super(context);
        ActionRow$$ExternalSyntheticOutline0.m(context, 1, 48.0f);
        ActionRow$$ExternalSyntheticOutline0.m(context, 1, 12.0f);
        LinearLayout linearLayout = new LinearLayout(context);
        this.mActionsGroup = linearLayout;
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        addView(linearLayout);
    }
}
