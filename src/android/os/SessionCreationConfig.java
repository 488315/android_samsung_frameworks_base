package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class SessionCreationConfig implements Parcelable {
    public static final Parcelable.Creator<SessionCreationConfig> CREATOR = new Parcelable.Creator<SessionCreationConfig>() { // from class: android.os.SessionCreationConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SessionCreationConfig createFromParcel(Parcel parcel) {
            SessionCreationConfig sessionCreationConfig = new SessionCreationConfig();
            sessionCreationConfig.readFromParcel(parcel);
            return sessionCreationConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SessionCreationConfig[] newArray(int i) {
            return new SessionCreationConfig[i];
        }
    };
    public IBinder[] layerTokens;
    public int[] modesToEnable;
    public long targetWorkDurationNanos = 0;
    public int[] tids;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.tids);
        parcel.writeLong(this.targetWorkDurationNanos);
        parcel.writeIntArray(this.modesToEnable);
        parcel.writeBinderArray(this.layerTokens);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.tids = parcel.createIntArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.targetWorkDurationNanos = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.modesToEnable = parcel.createIntArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.layerTokens = parcel.createBinderArray();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
