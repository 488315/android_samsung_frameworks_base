package com.android.systemui.globalactions.util;

import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.UtilFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SystemUIConditionChecker implements ConditionChecker {
    public final ConditionChecker mDefaultSystemCondition;
    public final LogWrapper mLogWrapper;
    public final UtilFactory mUtilFactory;

    public SystemUIConditionChecker(UtilFactory utilFactory, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mUtilFactory = utilFactory;
        this.mDefaultSystemCondition = conditionChecker;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d9, code lost:
    
        if (r7.contains("mini_open") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0108, code lost:
    
        if (r7.getPhoneRestrictionPolicy().checkEnableUseOfPacketData(true) != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0227, code lost:
    
        if (r7.getBundle("function_key_setting").getBoolean("grayout") != false) goto L42;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0233  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEnabled(java.lang.Object r7) {
        /*
            Method dump skipped, instructions count: 597
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.globalactions.util.SystemUIConditionChecker.isEnabled(java.lang.Object):boolean");
    }
}
