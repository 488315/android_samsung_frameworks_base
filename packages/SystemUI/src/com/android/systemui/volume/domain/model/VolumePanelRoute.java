package com.android.systemui.volume.domain.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelRoute {
    public static final /* synthetic */ VolumePanelRoute[] $VALUES;
    public static final VolumePanelRoute COMPOSE_VOLUME_PANEL;
    public static final VolumePanelRoute SETTINGS_VOLUME_PANEL;
    public static final VolumePanelRoute SYSTEM_UI_VOLUME_PANEL;

    static {
        VolumePanelRoute volumePanelRoute = new VolumePanelRoute("COMPOSE_VOLUME_PANEL", 0);
        COMPOSE_VOLUME_PANEL = volumePanelRoute;
        VolumePanelRoute volumePanelRoute2 = new VolumePanelRoute("SETTINGS_VOLUME_PANEL", 1);
        SETTINGS_VOLUME_PANEL = volumePanelRoute2;
        VolumePanelRoute volumePanelRoute3 = new VolumePanelRoute("SYSTEM_UI_VOLUME_PANEL", 2);
        SYSTEM_UI_VOLUME_PANEL = volumePanelRoute3;
        VolumePanelRoute[] volumePanelRouteArr = {volumePanelRoute, volumePanelRoute2, volumePanelRoute3};
        $VALUES = volumePanelRouteArr;
        EnumEntriesKt.enumEntries(volumePanelRouteArr);
    }

    private VolumePanelRoute(String str, int i) {
    }

    public static VolumePanelRoute valueOf(String str) {
        return (VolumePanelRoute) Enum.valueOf(VolumePanelRoute.class, str);
    }

    public static VolumePanelRoute[] values() {
        return (VolumePanelRoute[]) $VALUES.clone();
    }
}
