package com.android.systemui.p016qs.tileimpl;

import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class QSTileViewImplKt {
    public static final PropertyValuesHolder access$colorValuesHolder(String str, int... iArr) {
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, Arrays.copyOf(iArr, iArr.length));
        ofInt.setEvaluator(ArgbEvaluator.getInstance());
        return ofInt;
    }
}
