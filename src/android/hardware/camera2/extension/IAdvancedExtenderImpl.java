package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.ISessionProcessorImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes2.dex */
public interface IAdvancedExtenderImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IAdvancedExtenderImpl";

    public static class Default implements IAdvancedExtenderImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureRequestKeys(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCaptureResultKeys(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public CameraMetadataNative getAvailableCharacteristicsKeyValues(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public LatencyRange getEstimatedCaptureLatencyRange(String str, Size size, int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public ISessionProcessorImpl getSessionProcessor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedCaptureOutputResolutions(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public List<SizeList> getSupportedPreviewOutputResolutions(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public void init(String str, Map<String, CameraMetadataNative> map) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isCaptureProcessProgressAvailable() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isExtensionAvailable(String str, Map<String, CameraMetadataNative> map) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isPostviewAvailable() throws RemoteException {
            return false;
        }
    }

    CameraMetadataNative getAvailableCaptureRequestKeys(String str) throws RemoteException;

    CameraMetadataNative getAvailableCaptureResultKeys(String str) throws RemoteException;

    CameraMetadataNative getAvailableCharacteristicsKeyValues(String str) throws RemoteException;

    LatencyRange getEstimatedCaptureLatencyRange(String str, Size size, int i) throws RemoteException;

    ISessionProcessorImpl getSessionProcessor() throws RemoteException;

    List<SizeList> getSupportedCaptureOutputResolutions(String str) throws RemoteException;

    List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException;

    List<SizeList> getSupportedPreviewOutputResolutions(String str) throws RemoteException;

    void init(String str, Map<String, CameraMetadataNative> map) throws RemoteException;

    boolean isCaptureProcessProgressAvailable() throws RemoteException;

    boolean isExtensionAvailable(String str, Map<String, CameraMetadataNative> map) throws RemoteException;

    boolean isPostviewAvailable() throws RemoteException;

    public static abstract class Stub extends Binder implements IAdvancedExtenderImpl {
        static final int TRANSACTION_getAvailableCaptureRequestKeys = 8;
        static final int TRANSACTION_getAvailableCaptureResultKeys = 9;
        static final int TRANSACTION_getAvailableCharacteristicsKeyValues = 12;
        static final int TRANSACTION_getEstimatedCaptureLatencyRange = 3;
        static final int TRANSACTION_getSessionProcessor = 7;
        static final int TRANSACTION_getSupportedCaptureOutputResolutions = 5;
        static final int TRANSACTION_getSupportedPostviewResolutions = 6;
        static final int TRANSACTION_getSupportedPreviewOutputResolutions = 4;
        static final int TRANSACTION_init = 2;
        static final int TRANSACTION_isCaptureProcessProgressAvailable = 10;
        static final int TRANSACTION_isExtensionAvailable = 1;
        static final int TRANSACTION_isPostviewAvailable = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IAdvancedExtenderImpl.DESCRIPTOR);
        }

        public static IAdvancedExtenderImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAdvancedExtenderImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAdvancedExtenderImpl)) {
                return (IAdvancedExtenderImpl) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isExtensionAvailable";
                case 2:
                    return "init";
                case 3:
                    return "getEstimatedCaptureLatencyRange";
                case 4:
                    return "getSupportedPreviewOutputResolutions";
                case 5:
                    return "getSupportedCaptureOutputResolutions";
                case 6:
                    return "getSupportedPostviewResolutions";
                case 7:
                    return "getSessionProcessor";
                case 8:
                    return "getAvailableCaptureRequestKeys";
                case 9:
                    return "getAvailableCaptureResultKeys";
                case 10:
                    return "isCaptureProcessProgressAvailable";
                case 11:
                    return "isPostviewAvailable";
                case 12:
                    return "getAvailableCharacteristicsKeyValues";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            final HashMap map;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAdvancedExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdvancedExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    map = i3 >= 0 ? new HashMap() : null;
                    IntStream.range(0, i3).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i4) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), (CameraMetadataNative) parcel3.readTypedObject(CameraMetadataNative.CREATOR));
                        }
                    });
                    parcel.enforceNoDataAvail();
                    boolean zIsExtensionAvailable = isExtensionAvailable(string, map);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsExtensionAvailable);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    map = i4 >= 0 ? new HashMap() : null;
                    IntStream.range(0, i4).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i5) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), (CameraMetadataNative) parcel3.readTypedObject(CameraMetadataNative.CREATOR));
                        }
                    });
                    parcel.enforceNoDataAvail();
                    init(string2, map);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    Size size = (Size) parcel.readTypedObject(Size.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LatencyRange estimatedCaptureLatencyRange = getEstimatedCaptureLatencyRange(string3, size, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(estimatedCaptureLatencyRange, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SizeList> supportedPreviewOutputResolutions = getSupportedPreviewOutputResolutions(string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPreviewOutputResolutions, 1);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SizeList> supportedCaptureOutputResolutions = getSupportedCaptureOutputResolutions(string5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedCaptureOutputResolutions, 1);
                    return true;
                case 6:
                    Size size2 = (Size) parcel.readTypedObject(Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SizeList> supportedPostviewResolutions = getSupportedPostviewResolutions(size2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPostviewResolutions, 1);
                    return true;
                case 7:
                    ISessionProcessorImpl sessionProcessor = getSessionProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sessionProcessor);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative availableCaptureRequestKeys = getAvailableCaptureRequestKeys(string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureRequestKeys, 1);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative availableCaptureResultKeys = getAvailableCaptureResultKeys(string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureResultKeys, 1);
                    return true;
                case 10:
                    boolean zIsCaptureProcessProgressAvailable = isCaptureProcessProgressAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCaptureProcessProgressAvailable);
                    return true;
                case 11:
                    boolean zIsPostviewAvailable = isPostviewAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPostviewAvailable);
                    return true;
                case 12:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative availableCharacteristicsKeyValues = getAvailableCharacteristicsKeyValues(string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCharacteristicsKeyValues, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IAdvancedExtenderImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdvancedExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isExtensionAvailable(String str, Map<String, CameraMetadataNative> map) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAdvancedExtenderImpl.Stub.Proxy.lambda$isExtensionAvailable$0(parcelObtain, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$isExtensionAvailable$0(Parcel parcel, String str, CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public void init(String str, Map<String, CameraMetadataNative> map) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAdvancedExtenderImpl.Stub.Proxy.lambda$init$1(parcelObtain, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$init$1(Parcel parcel, String str, CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public LatencyRange getEstimatedCaptureLatencyRange(String str, Size size, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(size, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LatencyRange) parcelObtain2.readTypedObject(LatencyRange.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedPreviewOutputResolutions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedCaptureOutputResolutions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(size, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public ISessionProcessorImpl getSessionProcessor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISessionProcessorImpl.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCaptureRequestKeys(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCaptureResultKeys(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isPostviewAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCharacteristicsKeyValues(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
