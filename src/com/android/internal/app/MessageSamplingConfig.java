package com.android.internal.app;

import android.annotation.IntRange;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes5.dex */
public final class MessageSamplingConfig implements Parcelable {
    public static final Parcelable.Creator<MessageSamplingConfig> CREATOR = new Parcelable.Creator<MessageSamplingConfig>() { // from class: com.android.internal.app.MessageSamplingConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MessageSamplingConfig[] newArray(int i) {
            return new MessageSamplingConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MessageSamplingConfig createFromParcel(Parcel parcel) {
            return new MessageSamplingConfig(parcel);
        }
    };
    private final int mAcceptableLeftDistance;
    private final long mExpirationTimeSinceBootMillis;
    private final int mSampledOpCode;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MessageSamplingConfig(int i, int i2, long j) {
        this.mSampledOpCode = i;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", -1L, "to", 162L);
        this.mAcceptableLeftDistance = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 0L, "to", 162L);
        this.mExpirationTimeSinceBootMillis = j;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, j, "from", 0L);
    }

    public int getSampledOpCode() {
        return this.mSampledOpCode;
    }

    public int getAcceptableLeftDistance() {
        return this.mAcceptableLeftDistance;
    }

    public long getExpirationTimeSinceBootMillis() {
        return this.mExpirationTimeSinceBootMillis;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSampledOpCode);
        parcel.writeInt(this.mAcceptableLeftDistance);
        parcel.writeLong(this.mExpirationTimeSinceBootMillis);
    }

    MessageSamplingConfig(Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        long readLong = parcel.readLong();
        this.mSampledOpCode = readInt;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, readInt, "from", -1L, "to", 162L);
        this.mAcceptableLeftDistance = readInt2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, readInt2, "from", 0L, "to", 162L);
        this.mExpirationTimeSinceBootMillis = readLong;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, readLong, "from", 0L);
    }
}
