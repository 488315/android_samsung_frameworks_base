package com.android.systemui.qs.tileimpl;

import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import java.util.Arrays;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class QSTileViewImplKt {
    public static final PropertyValuesHolder access$colorValuesHolder(String str, int... iArr) {
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, Arrays.copyOf(iArr, iArr.length));
        ofInt.setEvaluator(ArgbEvaluator.getInstance());
        return ofInt;
    }
}
