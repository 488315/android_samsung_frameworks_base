package android.gsi;

import android.gsi.IProgressCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IImageService extends IInterface {
    public static final int CREATE_IMAGE_DEFAULT = 0;
    public static final int CREATE_IMAGE_READONLY = 1;
    public static final int CREATE_IMAGE_ZERO_FILL = 2;
    public static final String DESCRIPTOR = "android.gsi.IImageService";
    public static final int IMAGE_ERROR = 1;
    public static final int IMAGE_OK = 0;

    public static class Default implements IImageService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.gsi.IImageService
        public boolean backingImageExists(String str) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public void createBackingImage(String str, long j, int i, IProgressCallback iProgressCallback) throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public void deleteBackingImage(String str) throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public void disableImage(String str) throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public List<String> getAllBackingImages() throws RemoteException {
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
        public void mapImageDevice(String str, int i, MappedImage mappedImage) throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public void removeAllImages() throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public void removeDisabledImages() throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public void unmapImageDevice(String str) throws RemoteException {
        }

        @Override // android.gsi.IImageService
        public void zeroFillNewImage(String str, long j) throws RemoteException {
        }
    }

    boolean backingImageExists(String str) throws RemoteException;

    void createBackingImage(String str, long j, int i, IProgressCallback iProgressCallback) throws RemoteException;

    void deleteBackingImage(String str) throws RemoteException;

    void disableImage(String str) throws RemoteException;

    List<String> getAllBackingImages() throws RemoteException;

    int getAvbPublicKey(String str, AvbPublicKey avbPublicKey) throws RemoteException;

    String getMappedImageDevice(String str) throws RemoteException;

    boolean isImageDisabled(String str) throws RemoteException;

    boolean isImageMapped(String str) throws RemoteException;

    void mapImageDevice(String str, int i, MappedImage mappedImage) throws RemoteException;

    void removeAllImages() throws RemoteException;

    void removeDisabledImages() throws RemoteException;

    void unmapImageDevice(String str) throws RemoteException;

    void zeroFillNewImage(String str, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IImageService {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, IImageService.DESCRIPTOR);
        }

        public static IImageService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImageService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImageService)) {
                return (IImageService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createBackingImage";
                case 2:
                    return "deleteBackingImage";
                case 3:
                    return "mapImageDevice";
                case 4:
                    return "unmapImageDevice";
                case 5:
                    return "backingImageExists";
                case 6:
                    return "isImageMapped";
                case 7:
                    return "getAvbPublicKey";
                case 8:
                    return "getAllBackingImages";
                case 9:
                    return "zeroFillNewImage";
                case 10:
                    return "removeAllImages";
                case 11:
                    return "disableImage";
                case 12:
                    return "removeDisabledImages";
                case 13:
                    return "isImageDisabled";
                case 14:
                    return "getMappedImageDevice";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImageService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    IProgressCallback iProgressCallbackAsInterface = IProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createBackingImage(string, j, i3, iProgressCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteBackingImage(string2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    MappedImage mappedImage = new MappedImage();
                    parcel.enforceNoDataAvail();
                    mapImageDevice(string3, i4, mappedImage);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mappedImage, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmapImageDevice(string4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zBackingImageExists = backingImageExists(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBackingImageExists);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsImageMapped = isImageMapped(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImageMapped);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    AvbPublicKey avbPublicKey = new AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    int avbPublicKey2 = getAvbPublicKey(string7, avbPublicKey);
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
                    String string8 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    zeroFillNewImage(string8, j2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    removeAllImages();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableImage(string9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    removeDisabledImages();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsImageDisabled = isImageDisabled(string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImageDisabled);
                    return true;
                case 14:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mappedImageDevice = getMappedImageDevice(string11);
                    parcel2.writeNoException();
                    parcel2.writeString(mappedImageDevice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImageService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImageService.DESCRIPTOR;
            }

            @Override // android.gsi.IImageService
            public void createBackingImage(String str, long j, int i, IProgressCallback iProgressCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iProgressCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void deleteBackingImage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void mapImageDevice(String str, int i, MappedImage mappedImage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        mappedImage.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void unmapImageDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public boolean backingImageExists(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public boolean isImageMapped(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public int getAvbPublicKey(String str, AvbPublicKey avbPublicKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(parcelObtain2);
                    }
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public List<String> getAllBackingImages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void zeroFillNewImage(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void removeAllImages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void disableImage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void removeDisabledImages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public boolean isImageDisabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public String getMappedImageDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
