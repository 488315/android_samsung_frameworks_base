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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.incremental, i);
        parcel.writeStrongInterface(this.service);
        parcel.writeStrongInterface(this.callback);
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
                this.incremental = (IncrementalFileSystemControlParcel) parcel.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.service = IIncrementalServiceConnector.Stub.asInterface(parcel.readStrongBinder());
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.callback = IPackageInstallerSessionFileSystemConnector.Stub.asInterface(parcel.readStrongBinder());
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
