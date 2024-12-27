package com.android.systemui.flags;

import java.util.LinkedHashMap;
import java.util.Map;

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
