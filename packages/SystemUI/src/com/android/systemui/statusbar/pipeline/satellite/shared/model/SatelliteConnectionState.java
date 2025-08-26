package com.android.systemui.statusbar.pipeline.satellite.shared.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class SatelliteConnectionState implements Diffable {
    public static final /* synthetic */ SatelliteConnectionState[] $VALUES;
    public static final Companion Companion;
    public static final SatelliteConnectionState Connected;
    public static final SatelliteConnectionState Off;
    public static final SatelliteConnectionState On;
    public static final SatelliteConnectionState Unknown;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        SatelliteConnectionState satelliteConnectionState = new SatelliteConnectionState(C2paManifestList.UNKNOWN_VALUE, 0);
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

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        if (((SatelliteConnectionState) diffable) != this) {
            tableRowLoggerImpl.logChange("connState", name());
        }
    }
}
