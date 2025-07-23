package com.android.systemui.log.table;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsSettings;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TableChange {
    public boolean bool;
    public String columnName;
    public String columnPrefix;

    /* renamed from: int, reason: not valid java name */
    public Integer f49int;
    public boolean isInitial;
    public String str;
    public long timestamp;
    public DataType type;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getIS_INITIAL_PREFIX$annotations() {
        }

        public static /* synthetic */ void getMAX_STRING_LENGTH$annotations() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DataType {
        public static final /* synthetic */ DataType[] $VALUES;
        public static final DataType BOOLEAN;
        public static final DataType EMPTY;
        public static final DataType INT;
        public static final DataType STRING;

        static {
            DataType dataType = new DataType("STRING", 0);
            STRING = dataType;
            DataType dataType2 = new DataType("BOOLEAN", 1);
            BOOLEAN = dataType2;
            DataType dataType3 = new DataType(ImsSettings.TYPE_INT, 2);
            INT = dataType3;
            DataType dataType4 = new DataType("EMPTY", 3);
            EMPTY = dataType4;
            DataType[] dataTypeArr = {dataType, dataType2, dataType3, dataType4};
            $VALUES = dataTypeArr;
            EnumEntriesKt.enumEntries(dataTypeArr);
        }

        private DataType(String str, int i) {
        }

        public static DataType valueOf(String str) {
            return (DataType) Enum.valueOf(DataType.class, str);
        }

        public static DataType[] values() {
            return (DataType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DataType.values().length];
            try {
                iArr[DataType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DataType.INT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DataType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DataType.EMPTY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public TableChange() {
        this(0L, null, null, false, null, false, null, null, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TableChange)) {
            return false;
        }
        TableChange tableChange = (TableChange) obj;
        return this.timestamp == tableChange.timestamp && Intrinsics.areEqual(this.columnPrefix, tableChange.columnPrefix) && Intrinsics.areEqual(this.columnName, tableChange.columnName) && this.isInitial == tableChange.isInitial && this.type == tableChange.type && this.bool == tableChange.bool && Intrinsics.areEqual(this.f49int, tableChange.f49int) && Intrinsics.areEqual(this.str, tableChange.str);
    }

    public final String getName() {
        return !StringsKt__StringsKt.isBlank(this.columnPrefix) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.columnPrefix, ".", this.columnName) : this.columnName;
    }

    public final String getVal() {
        Object obj;
        int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i == 1) {
            obj = this.str;
        } else if (i == 2) {
            obj = this.f49int;
        } else if (i == 3) {
            obj = Boolean.valueOf(this.bool);
        } else {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            obj = null;
        }
        return (this.isInitial ? "**" : "").concat(String.valueOf(obj));
    }

    public final boolean hasData() {
        return (StringsKt__StringsKt.isBlank(this.columnName) || this.type == DataType.EMPTY) ? false : true;
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m((this.type.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Long.hashCode(this.timestamp) * 31, 31, this.columnPrefix), 31, this.columnName), 31, this.isInitial)) * 31, 31, this.bool);
        Integer num = this.f49int;
        int hashCode = (m + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.str;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    public final void reset(long j, String str, String str2, boolean z) {
        this.timestamp = j;
        this.columnPrefix = StringsKt___StringsKt.take(500, str);
        this.columnName = StringsKt___StringsKt.take(500, str2);
        this.isInitial = z;
        this.type = DataType.EMPTY;
        this.bool = false;
        this.f49int = 0;
        this.str = null;
    }

    public final String toString() {
        return "TableChange(timestamp=" + this.timestamp + ", columnPrefix=" + this.columnPrefix + ", columnName=" + this.columnName + ", isInitial=" + this.isInitial + ", type=" + this.type + ", bool=" + this.bool + ", int=" + this.f49int + ", str=" + this.str + ")";
    }

    public TableChange(long j, String str, String str2, boolean z, DataType dataType, boolean z2, Integer num, String str3) {
        this.timestamp = j;
        this.columnPrefix = str;
        this.columnName = str2;
        this.isInitial = z;
        this.type = dataType;
        this.bool = z2;
        this.f49int = num;
        this.str = str3;
        this.columnPrefix = StringsKt___StringsKt.take(500, str);
        this.columnName = StringsKt___StringsKt.take(500, this.columnName);
        String str4 = this.str;
        this.str = str4 != null ? StringsKt___StringsKt.take(500, str4) : null;
    }

    public /* synthetic */ TableChange(long j, String str, String str2, boolean z, DataType dataType, boolean z2, Integer num, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j, (i & 2) != 0 ? "" : str, (i & 4) != 0 ? "" : str2, (i & 8) != 0 ? false : z, (i & 16) != 0 ? DataType.EMPTY : dataType, (i & 32) != 0 ? false : z2, (i & 64) != 0 ? null : num, (i & 128) != 0 ? null : str3);
    }
}
