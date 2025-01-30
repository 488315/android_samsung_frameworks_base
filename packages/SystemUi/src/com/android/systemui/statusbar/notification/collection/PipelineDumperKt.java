package com.android.systemui.statusbar.notification.collection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class PipelineDumperKt {
    public static final String getBareClassName(Object obj) {
        String name;
        String name2 = obj.getClass().getName();
        Package r1 = obj.getClass().getPackage();
        return name2.substring((r1 == null || (name = r1.getName()) == null) ? 0 : name.length() + 1);
    }
}
