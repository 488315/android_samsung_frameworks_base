package com.android.systemui.audio.soundcraft.interfaces.wearable;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class BudsPluginInfo {
    public static final /* synthetic */ BudsPluginInfo[] $VALUES;
    public static final Companion Companion;
    private final boolean isSupport;
    private final String packageName;
    private final String projectName;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String findProjectName(String str) {
            List listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{"."}, 0, 6);
            if (!listSplit$default.isEmpty()) {
                try {
                    int i = Result.$r8$clinit;
                    String str2 = (String) listSplit$default.get(listSplit$default.size() - 1);
                    if (str2.length() > 0) {
                        return str2.substring(0, str2.length() - 3);
                    }
                    Unit unit = Unit.INSTANCE;
                    return null;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    new Result.Failure(th);
                }
            }
            return null;
        }

        private Companion() {
        }
    }

    static {
        BudsPluginInfo[] budsPluginInfoArr = {new BudsPluginInfo("Buds3Pro", 0, "paran", true, null, 4, null), new BudsPluginInfo("Buds3", 1, "jelly", true, null, 4, null), new BudsPluginInfo("BudsFE", 2, "pearl", false, null, 4, null), new BudsPluginInfo("Buds2Pro", 3, "zenith", false, null, 4, null), new BudsPluginInfo("Buds2", 4, "berry", false, null, 4, null)};
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
