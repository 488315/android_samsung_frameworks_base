package com.android.internal.flags;

import android.flags.BooleanFlag;
import android.flags.DynamicBooleanFlag;
import android.flags.FeatureFlags;
import android.flags.FusedOffFlag;
import android.flags.FusedOnFlag;
import android.flags.SyncableFlag;
import com.samsung.android.knox.ContainerProxy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class CoreFlags {
    private static final List<SyncableFlag> sKnownFlags = new ArrayList();
    public static BooleanFlag BOOL_FLAG = booleanFlag(ContainerProxy.CATEGORY_CORE, "bool_flag", false);
    public static FusedOffFlag OFF_FLAG = fusedOffFlag(ContainerProxy.CATEGORY_CORE, "off_flag");
    public static FusedOnFlag ON_FLAG = fusedOnFlag(ContainerProxy.CATEGORY_CORE, "on_flag");
    public static DynamicBooleanFlag DYN_FLAG = dynamicBooleanFlag(ContainerProxy.CATEGORY_CORE, "dyn_flag", true);

    public static boolean isCoreFlag(SyncableFlag flag) {
        for (SyncableFlag knownFlag : sKnownFlags) {
            if (knownFlag.getName().equals(flag.getName()) && knownFlag.getNamespace().equals(flag.getNamespace())) {
                return true;
            }
        }
        return false;
    }

    public static List<SyncableFlag> getCoreFlags() {
        return sKnownFlags;
    }

    private static BooleanFlag booleanFlag(String namespace, String name, boolean defaultValue) {
        BooleanFlag f = FeatureFlags.booleanFlag(namespace, name, defaultValue);
        sKnownFlags.add(new SyncableFlag(namespace, name, Boolean.toString(defaultValue), false));
        return f;
    }

    private static FusedOffFlag fusedOffFlag(String namespace, String name) {
        FusedOffFlag f = FeatureFlags.fusedOffFlag(namespace, name);
        sKnownFlags.add(new SyncableFlag(namespace, name, "false", false));
        return f;
    }

    private static FusedOnFlag fusedOnFlag(String namespace, String name) {
        FusedOnFlag f = FeatureFlags.fusedOnFlag(namespace, name);
        sKnownFlags.add(new SyncableFlag(namespace, name, "true", false));
        return f;
    }

    private static DynamicBooleanFlag dynamicBooleanFlag(String namespace, String name, boolean defaultValue) {
        DynamicBooleanFlag f = FeatureFlags.dynamicBooleanFlag(namespace, name, defaultValue);
        sKnownFlags.add(new SyncableFlag(namespace, name, Boolean.toString(defaultValue), true));
        return f;
    }
}
