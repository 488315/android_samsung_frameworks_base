package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface IImageService extends IInterface {
    public static final int CREATE_IMAGE_DEFAULT = 0;
    public static final int CREATE_IMAGE_READONLY = 1;
    public static final int CREATE_IMAGE_ZERO_FILL = 2;
    public static final String DESCRIPTOR = "android.gsi.IImageService";
    public static final int IMAGE_ERROR = 1;
    public static final int IMAGE_OK = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IImageService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.gsi.IImageService
        public boolean backingImageExists(String str) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public void createBackingImage(
                String str, long j, int i, IProgressCallback iProgressCallback)
                throws RemoteException {}

        @Override // android.gsi.IImageService
        public void deleteBackingImage(String str) throws RemoteException {}

        @Override // android.gsi.IImageService
        public void disableImage(String str) throws RemoteException {}

        @Override // android.gsi.IImageService
        public List getAllBackingImages() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IImageService
        public int getAvbPublicKey(String str, AvbPublicKey avbPublicKey) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IImageService
        public String getMappedImageDevice(String str) throws RemoteException {
            return null;
        }

        @Override // android.gsi.IImageService
        public boolean isImageDisabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public boolean isImageMapped(String str) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public void mapImageDevice(String str, int i, MappedImage mappedImage)
                throws RemoteException {}

        @Override // android.gsi.IImageService
        public void removeAllImages() throws RemoteException {}

        @Override // android.gsi.IImageService
        public void removeDisabledImages() throws RemoteException {}

        @Override // android.gsi.IImageService
        public void unmapImageDevice(String str) throws RemoteException {}

        @Override // android.gsi.IImageService
        public void zeroFillNewImage(String str, long j) throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IImageService {
        static final int TRANSACTION_backingImageExists = 5;
        static final int TRANSACTION_createBackingImage = 1;
        static final int TRANSACTION_deleteBackingImage = 2;
        static final int TRANSACTION_disableImage = 11;
        static final int TRANSACTION_getAllBackingImages = 8;
        static final int TRANSACTION_getAvbPublicKey = 7;
        static final int TRANSACTION_getMappedImageDevice = 14;
        static final int TRANSACTION_isImageDisabled = 13;
        static final int TRANSACTION_isImageMapped = 6;
        static final int TRANSACTION_mapImageDevice = 3;
        static final int TRANSACTION_removeAllImages = 10;
        static final int TRANSACTION_removeDisabledImages = 12;
        static final int TRANSACTION_unmapImageDevice = 4;
        static final int TRANSACTION_zeroFillNewImage = 9;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IImageService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.gsi.IImageService
            public final boolean backingImageExists(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void createBackingImage(
                    String str, long j, int i, IProgressCallback iProgressCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void deleteBackingImage(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void disableImage(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final List getAllBackingImages() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final int getAvbPublicKey(String str, AvbPublicKey avbPublicKey) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final String getMappedImageDevice(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final boolean isImageDisabled(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final boolean isImageMapped(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void mapImageDevice(String str, int i, MappedImage mappedImage) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        mappedImage.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void removeAllImages() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void removeDisabledImages() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void unmapImageDevice(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public final void zeroFillNewImage(String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImageService.DESCRIPTOR);
        }

        public static IImageService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImageService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImageService)) {
                return (IImageService) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImageService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    IProgressCallback asInterface =
                            IProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createBackingImage(readString, readLong, readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteBackingImage(readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    MappedImage mappedImage = new MappedImage();
                    parcel.enforceNoDataAvail();
                    mapImageDevice(readString3, readInt2, mappedImage);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mappedImage, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmapImageDevice(readString4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean backingImageExists = backingImageExists(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backingImageExists);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isImageMapped = isImageMapped(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImageMapped);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    AvbPublicKey avbPublicKey = new AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    int avbPublicKey2 = getAvbPublicKey(readString7, avbPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeInt(avbPublicKey2);
                    parcel2.writeTypedObject(avbPublicKey, 1);
                    return true;
                case 8:
                    List<String> allBackingImages = getAllBackingImages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allBackingImages);
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    zeroFillNewImage(readString8, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    removeAllImages();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableImage(readString9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    removeDisabledImages();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isImageDisabled = isImageDisabled(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImageDisabled);
                    return true;
                case 14:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mappedImageDevice = getMappedImageDevice(readString11);
                    parcel2.writeNoException();
                    parcel2.writeString(mappedImageDevice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    boolean backingImageExists(String str) throws RemoteException;

    void createBackingImage(String str, long j, int i, IProgressCallback iProgressCallback)
            throws RemoteException;

    void deleteBackingImage(String str) throws RemoteException;

    void disableImage(String str) throws RemoteException;

    List getAllBackingImages() throws RemoteException;

    int getAvbPublicKey(String str, AvbPublicKey avbPublicKey) throws RemoteException;

    String getMappedImageDevice(String str) throws RemoteException;

    boolean isImageDisabled(String str) throws RemoteException;

    boolean isImageMapped(String str) throws RemoteException;

    void mapImageDevice(String str, int i, MappedImage mappedImage) throws RemoteException;

    void removeAllImages() throws RemoteException;

    void removeDisabledImages() throws RemoteException;

    void unmapImageDevice(String str) throws RemoteException;

    void zeroFillNewImage(String str, long j) throws RemoteException;
}
