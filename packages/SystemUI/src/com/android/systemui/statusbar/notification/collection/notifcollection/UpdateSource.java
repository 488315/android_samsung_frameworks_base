package com.android.systemui.statusbar.notification.collection.notifcollection;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UpdateSource {
    public static final /* synthetic */ UpdateSource[] $VALUES;
    public static final UpdateSource App;
    public static final UpdateSource SystemServer;
    public static final UpdateSource SystemUi;

    static {
        UpdateSource updateSource = new UpdateSource("App", 0);
        App = updateSource;
        UpdateSource updateSource2 = new UpdateSource("SystemServer", 1);
        SystemServer = updateSource2;
        UpdateSource updateSource3 = new UpdateSource("SystemUi", 2);
        SystemUi = updateSource3;
        UpdateSource[] updateSourceArr = {updateSource, updateSource2, updateSource3};
        $VALUES = updateSourceArr;
        EnumEntriesKt.enumEntries(updateSourceArr);
    }

    private UpdateSource(String str, int i) {
    }

    public static UpdateSource valueOf(String str) {
        return (UpdateSource) Enum.valueOf(UpdateSource.class, str);
    }

    public static UpdateSource[] values() {
        return (UpdateSource[]) $VALUES.clone();
    }
}
