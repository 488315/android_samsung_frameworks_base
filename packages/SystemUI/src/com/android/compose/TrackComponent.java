package com.android.compose;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
final class TrackComponent {
    public static final /* synthetic */ TrackComponent[] $VALUES;
    public static final TrackComponent Background;
    public static final TrackComponent Icon;
    public static final TrackComponent Label;
    private final float zIndex;

    static {
        TrackComponent trackComponent = new TrackComponent("Background", 0, 0.0f);
        Background = trackComponent;
        TrackComponent trackComponent2 = new TrackComponent("Icon", 1, 1.0f);
        Icon = trackComponent2;
        TrackComponent trackComponent3 = new TrackComponent("Label", 2, 1.0f);
        Label = trackComponent3;
        TrackComponent[] trackComponentArr = {trackComponent, trackComponent2, trackComponent3};
        $VALUES = trackComponentArr;
        EnumEntriesKt.enumEntries(trackComponentArr);
    }

    private TrackComponent(String str, int i, float f) {
        this.zIndex = f;
    }

    public static TrackComponent valueOf(String str) {
        return (TrackComponent) Enum.valueOf(TrackComponent.class, str);
    }

    public static TrackComponent[] values() {
        return (TrackComponent[]) $VALUES.clone();
    }

    public final float getZIndex() {
        return this.zIndex;
    }
}
