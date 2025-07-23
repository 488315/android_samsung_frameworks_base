package android.content.pm;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ArchivedPackageParcel implements Parcelable {
    public static final Parcelable.Creator<ArchivedPackageParcel> CREATOR = new Parcelable.Creator<ArchivedPackageParcel>() { // from class: android.content.pm.ArchivedPackageParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ArchivedPackageParcel createFromParcel(Parcel parcel) {
            ArchivedPackageParcel archivedPackageParcel = new ArchivedPackageParcel();
            archivedPackageParcel.readFromParcel(parcel);
            return archivedPackageParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ArchivedPackageParcel[] newArray(int i) {
            return new ArchivedPackageParcel[i];
        }
    };
    public ArchivedActivityParcel[] archivedActivities;
    public String defaultToDeviceProtectedStorage;
    public String packageName;
    public String requestLegacyExternalStorage;
    public SigningDetails signingDetails;
    public String userDataFragile;
    public int versionCode = 0;
    public int versionCodeMajor = 0;
    public int targetSdkVersion = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.packageName);
        parcel.writeTypedObject(this.signingDetails, i);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeString(this.defaultToDeviceProtectedStorage);
        parcel.writeString(this.requestLegacyExternalStorage);
        parcel.writeString(this.userDataFragile);
        parcel.writeTypedArray(this.archivedActivities, i);
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
                this.packageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.signingDetails = (SigningDetails) parcel.readTypedObject(SigningDetails.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.versionCode = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.versionCodeMajor = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.targetSdkVersion = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.defaultToDeviceProtectedStorage = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.requestLegacyExternalStorage = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.userDataFragile = parcel.readString();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.archivedActivities = (ArchivedActivityParcel[]) parcel.createTypedArray(ArchivedActivityParcel.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.archivedActivities) | describeContents(this.signingDetails);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
