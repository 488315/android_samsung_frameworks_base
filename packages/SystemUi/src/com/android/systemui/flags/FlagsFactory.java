package com.android.systemui.flags;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FlagsFactory {
    public static final FlagsFactory INSTANCE = new FlagsFactory();
    public static final Map flagMap = new LinkedHashMap();

    private FlagsFactory() {
    }

    public static ReleasedFlag releasedFlag$default(FlagsFactory flagsFactory, int i, String str) {
        flagsFactory.getClass();
        ReleasedFlag releasedFlag = new ReleasedFlag(i, str, "systemui", false, false, 16, null);
        flagMap.put(str, releasedFlag);
        return releasedFlag;
    }

    public static ResourceBooleanFlag resourceBooleanFlag$default(FlagsFactory flagsFactory, int i, int i2, String str) {
        flagsFactory.getClass();
        ResourceBooleanFlag resourceBooleanFlag = new ResourceBooleanFlag(i, str, "systemui", i2, false);
        flagMap.put(str, resourceBooleanFlag);
        return resourceBooleanFlag;
    }

    public static SysPropBooleanFlag sysPropBooleanFlag$default(FlagsFactory flagsFactory, int i, String str, boolean z) {
        flagsFactory.getClass();
        SysPropBooleanFlag sysPropBooleanFlag = new SysPropBooleanFlag(i, str, "systemui", z);
        flagMap.put(str, sysPropBooleanFlag);
        return sysPropBooleanFlag;
    }

    public static UnreleasedFlag unreleasedFlag(int i) {
        return new UnreleasedFlag(i, "", "", false, false, 16, null);
    }

    public static /* synthetic */ UnreleasedFlag unreleasedFlag$default(FlagsFactory flagsFactory, int i, boolean z, int i2) {
        flagsFactory.getClass();
        return unreleasedFlag(i);
    }
}
