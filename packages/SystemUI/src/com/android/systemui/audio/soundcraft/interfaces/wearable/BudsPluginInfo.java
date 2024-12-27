package com.android.systemui.audio.soundcraft.interfaces.wearable;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

public final class BudsPluginInfo {
    public static final /* synthetic */ BudsPluginInfo[] $VALUES;
    public static final Companion Companion;
    private final boolean isSupport;
    private final String packageName;
    private final String projectName;

    public final class Companion {
        private Companion() {
        }

        public static String findProjectName(String str) {
            String str2;
            List split$default = StringsKt__StringsKt.split$default(str, new String[]{"."}, 0, 6);
            if (split$default.isEmpty()) {
                return null;
            }
            try {
                int i = Result.$r8$clinit;
                str2 = (String) split$default.get(split$default.size() - 1);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                new Result.Failure(th);
            }
            if (str2.length() > 0) {
                return str2.substring(0, str2.length() - 3);
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        String str = null;
        int i = 4;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        String str2 = null;
        int i2 = 4;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        BudsPluginInfo[] budsPluginInfoArr = {new BudsPluginInfo("Buds3Pro", 0, "paran", true, null, 4, null), new BudsPluginInfo("Buds3", 1, "jelly", true, str, i, defaultConstructorMarker), new BudsPluginInfo("BudsFE", 2, "pearl", z, str2, i2, defaultConstructorMarker2), new BudsPluginInfo("Buds2Pro", 3, "zenith", false, str, i, defaultConstructorMarker), new BudsPluginInfo("Buds2", 4, "berry", z, str2, i2, defaultConstructorMarker2)};
        $VALUES = budsPluginInfoArr;
        EnumEntriesKt.enumEntries(budsPluginInfoArr);
        Companion = new Companion(null);
    }

    private BudsPluginInfo(String str, int i, String str2, boolean z, String str3) {
        this.projectName = str2;
        this.isSupport = z;
        this.packageName = str3;
    }

    public static BudsPluginInfo valueOf(String str) {
        return (BudsPluginInfo) Enum.valueOf(BudsPluginInfo.class, str);
    }

    public static BudsPluginInfo[] values() {
        return (BudsPluginInfo[]) $VALUES.clone();
    }

    public /* synthetic */ BudsPluginInfo(String str, int i, String str2, boolean z, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, str2, z, (i2 & 4) != 0 ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("com.samsung.accessory.", str2, "mgr") : str3);
    }
}
