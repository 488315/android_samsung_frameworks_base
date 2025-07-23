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
        int dataPosition = parcel.dataPosition();
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
                this.moduleName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.modulePath = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.preinstalledModulePath = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.versionCode = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.versionName = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    boolean z = true;
                                    this.isFactory = parcel.readInt() != 0;
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.isActive = parcel.readInt() != 0;
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.hasClassPathJars = parcel.readInt() != 0;
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                if (parcel.readInt() == 0) {
                                                    z = false;
                                                }
                                                this.activeApexChanged = z;
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.partition = parcel.readByte();
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
