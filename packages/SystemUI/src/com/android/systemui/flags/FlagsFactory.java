package com.android.systemui.flags;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FlagsFactory {
    public static final FlagsFactory INSTANCE = new FlagsFactory();
    public static final Map flagMap = new LinkedHashMap();

    private FlagsFactory() {
    }

    public static ReleasedFlag releasedFlag(String str, String str2) {
        ReleasedFlag releasedFlag = new ReleasedFlag(str, str2, false, 4, null);
        flagMap.put(str, releasedFlag);
        return releasedFlag;
    }

    public static ResourceBooleanFlag resourceBooleanFlag$default(int i, FlagsFactory flagsFactory, String str) {
        flagsFactory.getClass();
        ResourceBooleanFlag resourceBooleanFlag = new ResourceBooleanFlag(str, "systemui", i);
        flagMap.put(str, resourceBooleanFlag);
        return resourceBooleanFlag;
    }

    public static SysPropBooleanFlag sysPropBooleanFlag$default(FlagsFactory flagsFactory, String str, boolean z) {
        flagsFactory.getClass();
        SysPropBooleanFlag sysPropBooleanFlag = new SysPropBooleanFlag(str, "systemui", z);
        flagMap.put(str, sysPropBooleanFlag);
        return sysPropBooleanFlag;
    }

    public static UnreleasedFlag unreleasedFlag$default(int i, FlagsFactory flagsFactory, String str) {
        flagsFactory.getClass();
        return new UnreleasedFlag(str, "systemui", false, false, 8, null);
    }
}
