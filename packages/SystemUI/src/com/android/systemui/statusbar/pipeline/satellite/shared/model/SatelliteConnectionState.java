package com.android.systemui.statusbar.pipeline.satellite.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SatelliteConnectionState {
    public static final /* synthetic */ SatelliteConnectionState[] $VALUES;
    public static final Companion Companion;
    public static final SatelliteConnectionState Connected;
    public static final SatelliteConnectionState Off;
    public static final SatelliteConnectionState On;
    public static final SatelliteConnectionState Unknown;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        SatelliteConnectionState satelliteConnectionState = new SatelliteConnectionState("Unknown", 0);
        Unknown = satelliteConnectionState;
        SatelliteConnectionState satelliteConnectionState2 = new SatelliteConnectionState("Off", 1);
        Off = satelliteConnectionState2;
        SatelliteConnectionState satelliteConnectionState3 = new SatelliteConnectionState("On", 2);
        On = satelliteConnectionState3;
        SatelliteConnectionState satelliteConnectionState4 = new SatelliteConnectionState("Connected", 3);
        Connected = satelliteConnectionState4;
        SatelliteConnectionState[] satelliteConnectionStateArr = {satelliteConnectionState, satelliteConnectionState2, satelliteConnectionState3, satelliteConnectionState4};
        $VALUES = satelliteConnectionStateArr;
        EnumEntriesKt.enumEntries(satelliteConnectionStateArr);
        Companion = new Companion(null);
    }

    private SatelliteConnectionState(String str, int i) {
    }

    public static SatelliteConnectionState valueOf(String str) {
        return (SatelliteConnectionState) Enum.valueOf(SatelliteConnectionState.class, str);
    }

    public static SatelliteConnectionState[] values() {
        return (SatelliteConnectionState[]) $VALUES.clone();
    }
}
