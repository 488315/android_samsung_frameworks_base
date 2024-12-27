package com.android.systemui.statusbar;

import android.text.TextUtils;
import java.util.function.Predicate;

public final /* synthetic */ class KeyguardSecIndicationPolicy$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !TextUtils.isEmpty(((IndicationItem) obj).mText);
    }
}
