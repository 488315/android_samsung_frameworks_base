package com.android.systemui.statusbar.pipeline.shared.ui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SignalIcon extends QSTile.Icon {
    public final int state;

    public SignalIcon(int i) {
        this.state = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SignalIcon) && this.state == ((SignalIcon) obj).state;
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Icon
    public final Drawable getDrawable(Context context) {
        SignalDrawable signalDrawable = new SignalDrawable(context);
        signalDrawable.setLevel(this.state);
        return signalDrawable;
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Icon
    public final int hashCode() {
        return Integer.hashCode(this.state);
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Icon
    public final String toString() {
        int i = StringCompanionObject.$r8$clinit;
        return String.format("SignalIcon[mState=0x%08x]", Arrays.copyOf(new Object[]{Integer.valueOf(this.state)}, 1));
    }
}
