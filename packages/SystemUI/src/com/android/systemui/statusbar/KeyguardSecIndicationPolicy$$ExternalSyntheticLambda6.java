package com.android.systemui.statusbar;

import android.text.TextUtils;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSecIndicationPolicy$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !TextUtils.isEmpty(((IndicationItem) obj).mText);
    }
}
