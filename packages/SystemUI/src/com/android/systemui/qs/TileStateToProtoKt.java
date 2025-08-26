package com.android.systemui.qs;

import android.content.ComponentName;
import android.text.TextUtils;
import android.widget.Switch;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.util.nano.ComponentNameProto;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class TileStateToProtoKt {
    /* JADX WARN: Removed duplicated region for block: B:14:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final QsTileState toProto(QSTile.State state) {
        int i;
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
        if (i2 == 0) {
            i = 0;
        } else if (i2 == 1) {
            i = 1;
        } else if (i2 == 2) {
            i = 2;
        }
        qsTileState.state = i;
        CharSequence charSequence = state.label;
        if (charSequence != null) {
            qsTileState.setLabel(charSequence.toString());
        }
        CharSequence charSequence2 = state.secondaryLabel;
        if (charSequence2 != null) {
            qsTileState.setSecondaryLabel(charSequence2.toString());
        }
        if (Intrinsics.areEqual(state.expandedAccessibilityClassName, Switch.class.getName())) {
            qsTileState.setBooleanState(qsTileState.state == 2);
        }
        return qsTileState;
    }
}
