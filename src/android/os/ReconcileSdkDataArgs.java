package android.os;

import android.os.Parcelable;
import java.util.List;

/* loaded from: classes3.dex */
public class ReconcileSdkDataArgs implements Parcelable {
    public static final Parcelable.Creator<ReconcileSdkDataArgs> CREATOR = new Parcelable.Creator<ReconcileSdkDataArgs>() { // from class: android.os.ReconcileSdkDataArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReconcileSdkDataArgs createFromParcel(Parcel parcel) {
            ReconcileSdkDataArgs reconcileSdkDataArgs = new ReconcileSdkDataArgs();
            reconcileSdkDataArgs.readFromParcel(parcel);
            return reconcileSdkDataArgs;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReconcileSdkDataArgs[] newArray(int i) {
            return new ReconcileSdkDataArgs[i];
        }
    };
    public String packageName;
    public String seInfo;
    public List<String> subDirNames;
    public String uuid;
    public int userId = 0;
    public int appId = 0;
    public int previousAppId = 0;
    public int flags = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.uuid);
        parcel.writeString(this.packageName);
        parcel.writeStringList(this.subDirNames);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.appId);
        parcel.writeInt(this.previousAppId);
        parcel.writeString(this.seInfo);
        parcel.writeInt(this.flags);
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
                this.uuid = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.subDirNames = parcel.createStringArrayList();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.userId = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.appId = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.previousAppId = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.seInfo = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.flags = parcel.readInt();
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
