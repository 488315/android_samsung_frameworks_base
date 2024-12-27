package com.android.systemui.qs;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class TileStateToProtoKt {
    /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.android.systemui.qs.nano.QsTileState toProto(com.android.systemui.plugins.qs.QSTile.State r5) {
        /*
            java.lang.String r0 = r5.spec
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto La
            r5 = 0
            return r5
        La:
            com.android.systemui.qs.nano.QsTileState r0 = new com.android.systemui.qs.nano.QsTileState
            r0.<init>()
            java.lang.String r1 = r5.spec
            java.lang.String r2 = "custom("
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L34
            com.android.systemui.util.nano.ComponentNameProto r1 = new com.android.systemui.util.nano.ComponentNameProto
            r1.<init>()
            java.lang.String r2 = r5.spec
            android.content.ComponentName r2 = com.android.systemui.qs.external.CustomTile.getComponentFromSpec(r2)
            java.lang.String r3 = r2.getPackageName()
            r1.packageName = r3
            java.lang.String r2 = r2.getClassName()
            r1.className = r2
            r0.setComponentName(r1)
            goto L39
        L34:
            java.lang.String r1 = r5.spec
            r0.setSpec(r1)
        L39:
            int r1 = r5.state
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L44
            if (r1 == r4) goto L48
            if (r1 == r3) goto L46
        L44:
            r1 = r2
            goto L49
        L46:
            r1 = r3
            goto L49
        L48:
            r1 = r4
        L49:
            r0.state = r1
            java.lang.CharSequence r1 = r5.label
            if (r1 == 0) goto L56
            java.lang.String r1 = r1.toString()
            r0.setLabel(r1)
        L56:
            java.lang.CharSequence r1 = r5.secondaryLabel
            if (r1 == 0) goto L61
            java.lang.String r1 = r1.toString()
            r0.setSecondaryLabel(r1)
        L61:
            java.lang.String r5 = r5.expandedAccessibilityClassName
            java.lang.Class<android.widget.Switch> r1 = android.widget.Switch.class
            java.lang.String r1 = r1.getName()
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r1)
            if (r5 == 0) goto L77
            int r5 = r0.state
            if (r5 != r3) goto L74
            r2 = r4
        L74:
            r0.setBooleanState(r2)
        L77:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.TileStateToProtoKt.toProto(com.android.systemui.plugins.qs.QSTile$State):com.android.systemui.qs.nano.QsTileState");
    }
}
