package com.android.systemui.globalactions.util;

import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.UtilFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemUIConditionChecker implements ConditionChecker {
    public final ConditionChecker mDefaultSystemCondition;
    public final LogWrapper mLogWrapper;
    public final UtilFactory mUtilFactory;

    public SystemUIConditionChecker(UtilFactory utilFactory, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mUtilFactory = utilFactory;
        this.mDefaultSystemCondition = conditionChecker;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x023a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEnabled(java.lang.Object r7) {
        /*
            Method dump skipped, instructions count: 604
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.globalactions.util.SystemUIConditionChecker.isEnabled(java.lang.Object):boolean");
    }
}
