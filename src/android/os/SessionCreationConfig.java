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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.tids);
        parcel.writeLong(this.targetWorkDurationNanos);
        parcel.writeIntArray(this.modesToEnable);
        parcel.writeBinderArray(this.layerTokens);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.tids = parcel.createIntArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.targetWorkDurationNanos = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.modesToEnable = parcel.createIntArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.layerTokens = parcel.createBinderArray();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
