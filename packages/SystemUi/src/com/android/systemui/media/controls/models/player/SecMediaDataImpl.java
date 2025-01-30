package com.android.systemui.media.controls.models.player;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecMediaDataImpl {
    public final int uid;

    public SecMediaDataImpl(int i) {
        this.uid = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SecMediaDataImpl) {
            return this.uid == ((SecMediaDataImpl) obj).uid;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.uid);
    }

    public final String toString() {
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("SecMediaDataImpl(uid="), this.uid, ")");
    }
}
