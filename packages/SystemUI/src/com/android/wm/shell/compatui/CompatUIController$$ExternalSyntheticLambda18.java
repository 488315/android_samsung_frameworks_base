package com.android.wm.shell.compatui;

import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda18 implements Predicate {
    public final /* synthetic */ int f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((CompatUIWindowManagerAbstract) obj).mDisplayId == this.f$0;
    }
}
