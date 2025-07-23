package com.android.systemui.util.animation;

import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UniqueObjectHostViewKt {
    public static final boolean getRequiresRemeasuring(View view) {
        Object tag = view.getTag(R.id.requires_remeasuring);
        if (tag != null) {
            return tag.equals(Boolean.TRUE);
        }
        return false;
    }

    public static final void setRequiresRemeasuring(View view, boolean z) {
        view.setTag(R.id.requires_remeasuring, Boolean.valueOf(z));
    }
}
