package com.android.systemui.ambient.statusbar.ui;

import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyType;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientStatusBarViewController$$ExternalSyntheticLambda11 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((PrivacyItem) obj).privacyType == PrivacyType.TYPE_LOCATION;
    }
}
