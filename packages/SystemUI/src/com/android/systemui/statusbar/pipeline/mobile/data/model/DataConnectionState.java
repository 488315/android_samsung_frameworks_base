package com.android.systemui.statusbar.pipeline.mobile.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.sec.ims.IMSParameter;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class DataConnectionState implements Diffable {
    public static final /* synthetic */ DataConnectionState[] $VALUES;
    public static final DataConnectionState Connected;
    public static final DataConnectionState Connecting;
    public static final DataConnectionState Disconnected;
    public static final DataConnectionState Disconnecting;
    public static final DataConnectionState HandoverInProgress;
    public static final DataConnectionState Invalid;
    public static final DataConnectionState Suspended;
    public static final DataConnectionState Unknown;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        DataConnectionState dataConnectionState = new DataConnectionState("Connected", 0);
        Connected = dataConnectionState;
        DataConnectionState dataConnectionState2 = new DataConnectionState("Connecting", 1);
        Connecting = dataConnectionState2;
        DataConnectionState dataConnectionState3 = new DataConnectionState("Disconnected", 2);
        Disconnected = dataConnectionState3;
        DataConnectionState dataConnectionState4 = new DataConnectionState("Disconnecting", 3);
        Disconnecting = dataConnectionState4;
        DataConnectionState dataConnectionState5 = new DataConnectionState("Suspended", 4);
        Suspended = dataConnectionState5;
        DataConnectionState dataConnectionState6 = new DataConnectionState("HandoverInProgress", 5);
        HandoverInProgress = dataConnectionState6;
        DataConnectionState dataConnectionState7 = new DataConnectionState("Unknown", 6);
        Unknown = dataConnectionState7;
        DataConnectionState dataConnectionState8 = new DataConnectionState("Invalid", 7);
        Invalid = dataConnectionState8;
        DataConnectionState[] dataConnectionStateArr = {dataConnectionState, dataConnectionState2, dataConnectionState3, dataConnectionState4, dataConnectionState5, dataConnectionState6, dataConnectionState7, dataConnectionState8};
        $VALUES = dataConnectionStateArr;
        EnumEntriesKt.enumEntries(dataConnectionStateArr);
        new Companion(null);
    }

    private DataConnectionState(String str, int i) {
    }

    public static DataConnectionState valueOf(String str) {
        return (DataConnectionState) Enum.valueOf(DataConnectionState.class, str);
    }

    public static DataConnectionState[] values() {
        return (DataConnectionState[]) $VALUES.clone();
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        if (((DataConnectionState) obj) != this) {
            tableRowLoggerImpl.logChange(IMSParameter.GENERAL.CONNECTION_STATE, name());
        }
    }
}
