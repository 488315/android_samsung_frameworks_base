package android.p005os;

import android.os.Parcelable;
import java.util.List;

/* loaded from: classes.dex */
public class ReconcileSdkDataArgs implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.os.ReconcileSdkDataArgs.1
        @Override // android.os.Parcelable.Creator
        public ReconcileSdkDataArgs createFromParcel(Parcel parcel) {
            ReconcileSdkDataArgs reconcileSdkDataArgs = new ReconcileSdkDataArgs();
            reconcileSdkDataArgs.readFromParcel(parcel);
            return reconcileSdkDataArgs;
        }

        @Override // android.os.Parcelable.Creator
        public ReconcileSdkDataArgs[] newArray(int i) {
            return new ReconcileSdkDataArgs[i];
        }
    };
    public String packageName;
    public String seInfo;
    public List subDirNames;
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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.uuid);
        parcel.writeString(this.packageName);
        parcel.writeStringList(this.subDirNames);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.appId);
        parcel.writeInt(this.previousAppId);
        parcel.writeString(this.seInfo);
        parcel.writeInt(this.flags);
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
                this.uuid = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.subDirNames = parcel.createStringArrayList();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.userId = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.appId = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.previousAppId = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.seInfo = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.flags = parcel.readInt();
                                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                            parcel.setDataPosition(dataPosition + readInt);
                                            return;
                                        }
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
