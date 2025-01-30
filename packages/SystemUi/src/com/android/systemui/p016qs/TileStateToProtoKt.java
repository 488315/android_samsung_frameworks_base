package com.android.systemui.p016qs;

import android.content.ComponentName;
import android.text.TextUtils;
import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.p016qs.nano.QsTileState;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.util.nano.ComponentNameProto;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TileStateToProtoKt {
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0041, code lost:
    
        if (r1 != 2) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final QsTileState toProto(QSTile.State state) {
        int i;
        CharSequence charSequence;
        CharSequence charSequence2;
        if (TextUtils.isEmpty(state.spec)) {
            return null;
        }
        QsTileState qsTileState = new QsTileState();
        if (state.spec.startsWith("custom(")) {
            ComponentNameProto componentNameProto = new ComponentNameProto();
            ComponentName componentFromSpec = CustomTile.getComponentFromSpec(state.spec);
            componentNameProto.packageName = componentFromSpec.getPackageName();
            componentNameProto.className = componentFromSpec.getClassName();
            qsTileState.setComponentName(componentNameProto);
        } else {
            qsTileState.setSpec(state.spec);
        }
        int i2 = state.state;
        if (i2 != 0) {
            i = 1;
            if (i2 != 1) {
                i = 2;
            }
            qsTileState.state = i;
            charSequence = state.label;
            if (charSequence != null) {
                qsTileState.setLabel(charSequence.toString());
            }
            charSequence2 = state.secondaryLabel;
            if (charSequence2 != null) {
                qsTileState.setSecondaryLabel(charSequence2.toString());
            }
            if (state instanceof QSTile.BooleanState) {
                qsTileState.setBooleanState(((QSTile.BooleanState) state).value);
            }
            return qsTileState;
        }
        i = 0;
        qsTileState.state = i;
        charSequence = state.label;
        if (charSequence != null) {
        }
        charSequence2 = state.secondaryLabel;
        if (charSequence2 != null) {
        }
        if (state instanceof QSTile.BooleanState) {
        }
        return qsTileState;
    }
}
