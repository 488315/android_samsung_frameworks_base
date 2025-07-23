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

    public static boolean isCoreFlag(SyncableFlag syncableFlag) {
        for (SyncableFlag syncableFlag2 : sKnownFlags) {
            if (syncableFlag2.getName().equals(syncableFlag.getName()) && syncableFlag2.getNamespace().equals(syncableFlag.getNamespace())) {
                return true;
            }
        }
        return false;
    }

    public static List<SyncableFlag> getCoreFlags() {
        return sKnownFlags;
    }

    private static BooleanFlag booleanFlag(String str, String str2, boolean z) {
        BooleanFlag booleanFlag = FeatureFlags.booleanFlag(str, str2, z);
        sKnownFlags.add(new SyncableFlag(str, str2, Boolean.toString(z), false));
        return booleanFlag;
    }

    private static FusedOffFlag fusedOffFlag(String str, String str2) {
        FusedOffFlag fusedOffFlag = FeatureFlags.fusedOffFlag(str, str2);
        sKnownFlags.add(new SyncableFlag(str, str2, "false", false));
        return fusedOffFlag;
    }

    private static FusedOnFlag fusedOnFlag(String str, String str2) {
        FusedOnFlag fusedOnFlag = FeatureFlags.fusedOnFlag(str, str2);
        sKnownFlags.add(new SyncableFlag(str, str2, "true", false));
        return fusedOnFlag;
    }

    private static DynamicBooleanFlag dynamicBooleanFlag(String str, String str2, boolean z) {
        DynamicBooleanFlag dynamicBooleanFlag = FeatureFlags.dynamicBooleanFlag(str, str2, z);
        sKnownFlags.add(new SyncableFlag(str, str2, Boolean.toString(z), true));
        return dynamicBooleanFlag;
    }
}
