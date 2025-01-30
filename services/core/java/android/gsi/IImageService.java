package android.gsi;

import android.gsi.IProgressCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

/* loaded from: classes.dex */
public interface IImageService extends IInterface {

    public class Default implements IImageService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    boolean backingImageExists(String str);

    void createBackingImage(String str, long j, int i, IProgressCallback iProgressCallback);

    void deleteBackingImage(String str);

    void disableImage(String str);

    List getAllBackingImages();

    int getAvbPublicKey(String str, AvbPublicKey avbPublicKey);

    String getMappedImageDevice(String str);

    boolean isImageDisabled(String str);

    boolean isImageMapped(String str);

    void mapImageDevice(String str, int i, MappedImage mappedImage);

    void removeAllImages();

    void removeDisabledImages();

    void unmapImageDevice(String str);

    void zeroFillNewImage(String str, long j);

    public abstract class Stub extends Binder implements IImageService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "android.gsi.IImageService");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.gsi.IImageService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.gsi.IImageService");
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    IProgressCallback asInterface = IProgressCallback.Stub.asInterface(parcel.readStrongBinder());
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

        public class Proxy implements IImageService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
