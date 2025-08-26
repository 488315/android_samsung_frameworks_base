package android.apex;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ApexInfo implements Parcelable {
    public static final Parcelable.Creator<ApexInfo> CREATOR = new Parcelable.Creator<ApexInfo>() { // from class: android.apex.ApexInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApexInfo createFromParcel(Parcel parcel) {
            ApexInfo apexInfo = new ApexInfo();
            apexInfo.readFromParcel(parcel);
            return apexInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApexInfo[] newArray(int i) {
            return new ApexInfo[i];
        }
    };
    public String moduleName;
    public String modulePath;
    public byte partition;
    public String preinstalledModulePath;
    public String versionName;
    public long versionCode = 0;
    public boolean isFactory = false;
    public boolean isActive = false;
    public boolean hasClassPathJars = false;
    public boolean activeApexChanged = false;

    public @interface Partition {
        public static final byte ODM = 4;
        public static final byte PRODUCT = 2;
        public static final byte SYSTEM = 0;
        public static final byte SYSTEM_EXT = 1;
        public static final byte VENDOR = 3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.moduleName);
        parcel.writeString(this.modulePath);
        parcel.writeString(this.preinstalledModulePath);
        parcel.writeLong(this.versionCode);
        parcel.writeString(this.versionName);
        parcel.writeInt(this.isFactory ? 1 : 0);
        parcel.writeInt(this.isActive ? 1 : 0);
        parcel.writeInt(this.hasClassPathJars ? 1 : 0);
        parcel.writeInt(this.activeApexChanged ? 1 : 0);
        parcel.writeByte(this.partition);
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
                this.moduleName = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.modulePath = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.preinstalledModulePath = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.versionCode = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.versionName = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    boolean z = true;
                                    this.isFactory = parcel.readInt() != 0;
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.isActive = parcel.readInt() != 0;
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.hasClassPathJars = parcel.readInt() != 0;
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                if (parcel.readInt() == 0) {
                                                    z = false;
                                                }
                                                this.activeApexChanged = z;
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.partition = parcel.readByte();
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
