package com.android.systemui.plugins.clocks;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ZenData {
    private final String descriptionId;
    private final ZenMode zenMode;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ZenMode {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ZenMode[] $VALUES;
        public static final Companion Companion;
        private final int zenMode;
        public static final ZenMode OFF = new ZenMode("OFF", 0, 0);
        public static final ZenMode IMPORTANT_INTERRUPTIONS = new ZenMode("IMPORTANT_INTERRUPTIONS", 1, 1);
        public static final ZenMode NO_INTERRUPTIONS = new ZenMode("NO_INTERRUPTIONS", 2, 2);
        public static final ZenMode ALARMS = new ZenMode("ALARMS", 3, 3);

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            private Companion() {
            }

            public final ZenMode fromInt(int i) {
                for (ZenMode zenMode : ZenMode.values()) {
                    if (zenMode.getZenMode() == i) {
                        return zenMode;
                    }
                }
                return null;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private static final /* synthetic */ ZenMode[] $values() {
            return new ZenMode[]{OFF, IMPORTANT_INTERRUPTIONS, NO_INTERRUPTIONS, ALARMS};
        }

        static {
            ZenMode[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
            Companion = new Companion(null);
        }

        private ZenMode(String str, int i, int i2) {
            this.zenMode = i2;
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static ZenMode valueOf(String str) {
            return (ZenMode) Enum.valueOf(ZenMode.class, str);
        }

        public static ZenMode[] values() {
            return (ZenMode[]) $VALUES.clone();
        }

        public final int getZenMode() {
            return this.zenMode;
        }
    }

    public ZenData(ZenMode zenMode, String str) {
        this.zenMode = zenMode;
        this.descriptionId = str;
    }

    public static /* synthetic */ ZenData copy$default(ZenData zenData, ZenMode zenMode, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            zenMode = zenData.zenMode;
        }
        if ((i & 2) != 0) {
            str = zenData.descriptionId;
        }
        return zenData.copy(zenMode, str);
    }

    public final ZenMode component1() {
        return this.zenMode;
    }

    public final String component2() {
        return this.descriptionId;
    }

    public final ZenData copy(ZenMode zenMode, String str) {
        return new ZenData(zenMode, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZenData)) {
            return false;
        }
        ZenData zenData = (ZenData) obj;
        return this.zenMode == zenData.zenMode && Intrinsics.areEqual(this.descriptionId, zenData.descriptionId);
    }

    public final String getDescriptionId() {
        return this.descriptionId;
    }

    public final ZenMode getZenMode() {
        return this.zenMode;
    }

    public int hashCode() {
        int hashCode = this.zenMode.hashCode() * 31;
        String str = this.descriptionId;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "ZenData(zenMode=" + this.zenMode + ", descriptionId=" + this.descriptionId + ")";
    }
}
