package com.android.systemui.util.animation;

import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
