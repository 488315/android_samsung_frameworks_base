package com.android.wm.shell.compatui;

import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda18 implements Predicate {
    public final /* synthetic */ int f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((CompatUIWindowManagerAbstract) obj).mDisplayId == this.f$0;
    }
}
