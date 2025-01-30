package com.android.systemui.flags;

import java.util.Map;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FlagsCommonModule_ProvidesAllFlagsFactory implements Provider {
    public static Map providesAllFlags() {
        FlagsCommonModule.Companion.getClass();
        FlagsFactory.INSTANCE.getClass();
        Map map = FlagsFactory.flagMap;
        map.containsKey(Flags.TEAMFOOD.name);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesAllFlags();
    }
}
