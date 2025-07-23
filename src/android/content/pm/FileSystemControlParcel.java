package android.content.pm;

import android.content.pm.IPackageInstallerSessionFileSystemConnector;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.incremental.IIncrementalServiceConnector;
import android.os.incremental.IncrementalFileSystemControlParcel;

/* loaded from: classes.dex */
public class FileSystemControlParcel implements Parcelable {
    public static final Parcelable.Creator<FileSystemControlParcel> CREATOR = new Parcelable.Creator<FileSystemControlParcel>() { // from class: android.content.pm.FileSystemControlParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FileSystemControlParcel createFromParcel(Parcel parcel) {
            FileSystemControlParcel fileSystemControlParcel = new FileSystemControlParcel();
            fileSystemControlParcel.readFromParcel(parcel);
            return fileSystemControlParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FileSystemControlParcel[] newArray(int i) {
            return new FileSystemControlParcel[i];
        }
    };
    public IPackageInstallerSessionFileSystemConnector callback;
    public IncrementalFileSystemControlParcel incremental;
    public IIncrementalServiceConnector service;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.incremental, i);
        parcel.writeStrongInterface(this.service);
        parcel.writeStrongInterface(this.callback);
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
                this.incremental = (IncrementalFileSystemControlParcel) parcel.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.service = IIncrementalServiceConnector.Stub.asInterface(parcel.readStrongBinder());
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.callback = IPackageInstallerSessionFileSystemConnector.Stub.asInterface(parcel.readStrongBinder());
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
        return describeContents(this.incremental);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
