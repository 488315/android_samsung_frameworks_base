package com.android.systemui.util.animation;

import android.view.View;
import com.android.systemui.R;

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
