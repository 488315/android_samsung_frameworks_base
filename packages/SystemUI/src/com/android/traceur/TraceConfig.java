package com.android.traceur;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.traceur.PresetTraceConfigs;
import java.util.Set;

/* loaded from: classes3.dex */
public class TraceConfig implements Parcelable {
    public static final Parcelable.Creator<TraceConfig> CREATOR = new Parcelable.Creator() { // from class: com.android.traceur.TraceConfig.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new TraceConfig(parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), Set.of((Object[]) parcel.readStringArray()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TraceConfig[i];
        }
    };
    public final boolean apps;
    public final boolean attachToBugreport;
    public final int bufferSizeKb;
    public final boolean longTrace;
    public final int maxLongTraceDurationMinutes;
    public final int maxLongTraceSizeMb;
    public final Set tags;
    public final boolean winscope;

    public TraceConfig(int i, boolean z, boolean z2, boolean z3, boolean z4, int i2, int i3, Set<String> set) {
        this.bufferSizeKb = i;
        this.winscope = z;
        this.apps = z2;
        this.longTrace = z3;
        this.attachToBugreport = z4;
        this.maxLongTraceSizeMb = i2;
        this.maxLongTraceDurationMinutes = i3;
        this.tags = set;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.bufferSizeKb);
        parcel.writeBoolean(this.winscope);
        parcel.writeBoolean(this.apps);
        parcel.writeBoolean(this.longTrace);
        parcel.writeBoolean(this.attachToBugreport);
        parcel.writeInt(this.maxLongTraceSizeMb);
        parcel.writeInt(this.maxLongTraceDurationMinutes);
        parcel.writeStringArray((String[]) this.tags.toArray(new TraceConfig$$ExternalSyntheticLambda0()));
    }

    public class Builder {
        public boolean apps;
        public boolean attachToBugreport;
        public int bufferSizeKb;
        public boolean longTrace;
        public int maxLongTraceDurationMinutes;
        public int maxLongTraceSizeMb;
        public Set tags;
        public boolean winscope;

        public Builder(TraceConfig traceConfig) {
            this(traceConfig.bufferSizeKb, traceConfig.winscope, traceConfig.apps, traceConfig.longTrace, traceConfig.attachToBugreport, traceConfig.maxLongTraceSizeMb, traceConfig.maxLongTraceDurationMinutes, traceConfig.tags);
        }

        private Builder(int i, boolean z, boolean z2, boolean z3, boolean z4, int i2, int i3, Set<String> set) {
            this.bufferSizeKb = i;
            this.winscope = z;
            this.apps = z2;
            this.longTrace = z3;
            this.attachToBugreport = z4;
            this.maxLongTraceSizeMb = i2;
            this.maxLongTraceDurationMinutes = i3;
            this.tags = set;
        }
    }

    public TraceConfig(PresetTraceConfigs.TraceOptions traceOptions, Set<String> set) {
        this(traceOptions.bufferSizeKb, traceOptions.winscope, traceOptions.apps, traceOptions.longTrace, traceOptions.attachToBugreport, traceOptions.maxLongTraceSizeMb, traceOptions.maxLongTraceDurationMinutes, set);
    }
}
