package com.android.systemui.statusbar.notification.collection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class PipelineDumperKt {
    public static final String getBareClassName(Object obj) {
        String name;
        String name2 = obj.getClass().getName();
        Package r1 = obj.getClass().getPackage();
        return name2.substring((r1 == null || (name = r1.getName()) == null) ? 0 : name.length() + 1);
    }
}
