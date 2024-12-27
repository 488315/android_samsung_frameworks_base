package com.android.systemui.statusbar.notification.collection;

public abstract class PipelineDumperKt {
    public static final String getBareClassName(Object obj) {
        String name;
        String name2 = obj.getClass().getName();
        Package r1 = obj.getClass().getPackage();
        return name2.substring((r1 == null || (name = r1.getName()) == null) ? 0 : name.length() + 1);
    }
}
