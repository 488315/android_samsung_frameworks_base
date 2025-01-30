package com.android.systemui.log.table;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt___StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TableChange {
    public boolean bool;
    public String columnName;
    public String columnPrefix;

    /* renamed from: int, reason: not valid java name */
    public Integer f831int;
    public boolean isInitial;
    public String str;
    public long timestamp;
    public DataType type;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum DataType {
        STRING,
        BOOLEAN,
        INT,
        EMPTY
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        return this.timestamp == tableChange.timestamp && Intrinsics.areEqual(this.columnPrefix, tableChange.columnPrefix) && Intrinsics.areEqual(this.columnName, tableChange.columnName) && this.isInitial == tableChange.isInitial && this.type == tableChange.type && this.bool == tableChange.bool && Intrinsics.areEqual(this.f831int, tableChange.f831int) && Intrinsics.areEqual(this.str, tableChange.str);
    }

    public final String getName() {
        return StringsKt__StringsJVMKt.isBlank(this.columnPrefix) ^ true ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(this.columnPrefix, ".", this.columnName) : this.columnName;
    }

    public final String getVal() {
        Object obj;
        int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i == 1) {
            obj = this.str;
        } else if (i == 2) {
            obj = this.f831int;
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
        return (StringsKt__StringsJVMKt.isBlank(this.columnName) ^ true) && this.type != DataType.EMPTY;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.columnName, AppInfo$$ExternalSyntheticOutline0.m41m(this.columnPrefix, Long.hashCode(this.timestamp) * 31, 31), 31);
        boolean z = this.isInitial;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int hashCode = (this.type.hashCode() + ((m41m + i) * 31)) * 31;
        boolean z2 = this.bool;
        int i2 = (hashCode + (z2 ? 1 : z2 ? 1 : 0)) * 31;
        Integer num = this.f831int;
        int hashCode2 = (i2 + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.str;
        return hashCode2 + (str != null ? str.hashCode() : 0);
    }

    public final void reset(long j, String str, String str2, boolean z) {
        this.timestamp = j;
        this.columnPrefix = StringsKt___StringsKt.take(str);
        this.columnName = StringsKt___StringsKt.take(str2);
        this.isInitial = z;
        this.type = DataType.EMPTY;
        this.bool = false;
        this.f831int = 0;
        this.str = null;
    }

    public final String toString() {
        return "TableChange(timestamp=" + this.timestamp + ", columnPrefix=" + this.columnPrefix + ", columnName=" + this.columnName + ", isInitial=" + this.isInitial + ", type=" + this.type + ", bool=" + this.bool + ", int=" + this.f831int + ", str=" + this.str + ")";
    }

    public TableChange(long j, String str, String str2, boolean z, DataType dataType, boolean z2, Integer num, String str3) {
        this.timestamp = j;
        this.columnPrefix = str;
        this.columnName = str2;
        this.isInitial = z;
        this.type = dataType;
        this.bool = z2;
        this.f831int = num;
        this.str = str3;
        this.columnPrefix = StringsKt___StringsKt.take(str);
        this.columnName = StringsKt___StringsKt.take(this.columnName);
        String str4 = this.str;
        this.str = str4 != null ? StringsKt___StringsKt.take(str4) : null;
    }

    public /* synthetic */ TableChange(long j, String str, String str2, boolean z, DataType dataType, boolean z2, Integer num, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j, (i & 2) != 0 ? "" : str, (i & 4) == 0 ? str2 : "", (i & 8) != 0 ? false : z, (i & 16) != 0 ? DataType.EMPTY : dataType, (i & 32) == 0 ? z2 : false, (i & 64) != 0 ? null : num, (i & 128) == 0 ? str3 : null);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getIS_INITIAL_PREFIX$annotations() {
        }

        public static /* synthetic */ void getMAX_STRING_LENGTH$annotations() {
        }
    }
}
