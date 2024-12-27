package com.android.systemui.monet;

import android.graphics.Color;
import com.google.ux.material.libmonet.hct.Hct;
import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ColorScheme$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ColorScheme$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return (Integer) ((Map.Entry) obj).getKey();
            case 1:
                return ColorScheme.stringForColor(((Integer) obj).intValue());
            case 2:
                return (Hct) ((Map.Entry) obj).getValue();
            case 3:
                return Integer.valueOf(((Color) obj).toArgb());
            default:
                return Hct.fromInt(((Integer) ((Map.Entry) obj).getKey()).intValue());
        }
    }
}
