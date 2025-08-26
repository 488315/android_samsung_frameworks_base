package com.android.systemui.qs.tileimpl;

import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import java.util.Arrays;

/* loaded from: classes2.dex */
public abstract class QSTileViewImplKt {
    public static final PropertyValuesHolder access$colorValuesHolder(String str, int... iArr) {
        PropertyValuesHolder propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, Arrays.copyOf(iArr, iArr.length));
        propertyValuesHolderOfInt.setEvaluator(ArgbEvaluator.getInstance());
        return propertyValuesHolderOfInt;
    }
}
