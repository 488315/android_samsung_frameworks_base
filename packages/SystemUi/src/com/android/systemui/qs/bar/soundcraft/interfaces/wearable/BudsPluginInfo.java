package com.android.systemui.qs.bar.soundcraft.interfaces.wearable;

import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum BudsPluginInfo {
    /* JADX INFO: Fake field, exist only in values array */
    Buds3Pro("paran", true, null, 4, null),
    /* JADX INFO: Fake field, exist only in values array */
    Buds3("jelly", true, null, 4, null),
    /* JADX INFO: Fake field, exist only in values array */
    BudsFE("pearl", false, null, 4, null),
    /* JADX INFO: Fake field, exist only in values array */
    Buds2Pro("zenith", false, null, 4, null),
    /* JADX INFO: Fake field, exist only in values array */
    Buds2("berry", false, null, 4, null);

    public static final Companion Companion = new Companion(null);
    private final boolean isSupport;
    private final String packageName;
    private final String projectName;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String findProjectName(String str) {
            BudsPluginInfo budsPluginInfo;
            BudsPluginInfo[] values = BudsPluginInfo.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    budsPluginInfo = null;
                    break;
                }
                budsPluginInfo = values[i];
                if (Intrinsics.areEqual(budsPluginInfo.getPackageName(), str)) {
                    break;
                }
                i++;
            }
            if (budsPluginInfo != null) {
                return budsPluginInfo.getProjectName();
            }
            return null;
        }
    }

    /* synthetic */ BudsPluginInfo(String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, (i & 4) != 0 ? PathParser$$ExternalSyntheticOutline0.m29m("com.samsung.accessory.", str, "mgr") : str2);
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getProjectName() {
        return this.projectName;
    }

    public final boolean isSupport() {
        return this.isSupport;
    }

    BudsPluginInfo(String str, boolean z, String str2) {
        this.projectName = str;
        this.isSupport = z;
        this.packageName = str2;
    }
}
