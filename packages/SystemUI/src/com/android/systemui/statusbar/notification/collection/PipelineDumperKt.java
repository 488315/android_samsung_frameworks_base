package com.android.systemui.statusbar.notification.collection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PipelineDumperKt {
    public static final String getBareClassName(Object obj) {
        String name;
        String name2 = obj.getClass().getName();
        Package r1 = obj.getClass().getPackage();
        return name2.substring((r1 == null || (name = r1.getName()) == null) ? 0 : name.length() + 1);
    }
}
