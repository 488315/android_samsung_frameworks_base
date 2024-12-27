package com.android.server.am;

import java.util.function.ToIntFunction;

public final /* synthetic */
class AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda1
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((AppRestrictionController.RestrictionSettings.PkgSettings) obj).mUid;
    }
}
