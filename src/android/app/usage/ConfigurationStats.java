package android.app.usage;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class ConfigurationStats implements Parcelable {
    public static final Parcelable.Creator<ConfigurationStats> CREATOR = new Parcelable.Creator<ConfigurationStats>() { // from class: android.app.usage.ConfigurationStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationStats createFromParcel(Parcel parcel) {
            ConfigurationStats configurationStats = new ConfigurationStats();
            if (parcel.readInt() != 0) {
                configurationStats.mConfiguration = Configuration.CREATOR.createFromParcel(parcel);
            }
            configurationStats.mBeginTimeStamp = parcel.readLong();
            configurationStats.mEndTimeStamp = parcel.readLong();
            configurationStats.mLastTimeActive = parcel.readLong();
            configurationStats.mTotalTimeActive = parcel.readLong();
            configurationStats.mActivationCount = parcel.readInt();
            return configurationStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationStats[] newArray(int i) {
            return new ConfigurationStats[i];
        }
    };
    public int mActivationCount;
    public long mBeginTimeStamp;
    public Configuration mConfiguration;
    public long mEndTimeStamp;
    public long mLastTimeActive;
    public long mTotalTimeActive;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConfigurationStats() {
    }

    public ConfigurationStats(ConfigurationStats configurationStats) {
        this.mConfiguration = configurationStats.mConfiguration;
        this.mBeginTimeStamp = configurationStats.mBeginTimeStamp;
        this.mEndTimeStamp = configurationStats.mEndTimeStamp;
        this.mLastTimeActive = configurationStats.mLastTimeActive;
        this.mTotalTimeActive = configurationStats.mTotalTimeActive;
        this.mActivationCount = configurationStats.mActivationCount;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastTimeActive() {
        return this.mLastTimeActive;
    }

    public long getTotalTimeActive() {
        return this.mTotalTimeActive;
    }

    public int getActivationCount() {
        return this.mActivationCount;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mConfiguration != null) {
            parcel.writeInt(1);
            this.mConfiguration.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastTimeActive);
        parcel.writeLong(this.mTotalTimeActive);
        parcel.writeInt(this.mActivationCount);
    }
}
