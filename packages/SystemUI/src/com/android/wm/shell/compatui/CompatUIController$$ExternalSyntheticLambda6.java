package com.android.wm.shell.compatui;

import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CompatUIWindowManagerAbstract compatUIWindowManagerAbstract = (CompatUIWindowManagerAbstract) obj;
        switch (this.$r8$classId) {
            case 0:
                return compatUIWindowManagerAbstract.mDisplayId != 0;
            case 1:
                return compatUIWindowManagerAbstract.mDisplayId == 0;
            default:
                return true;
        }
    }
}
