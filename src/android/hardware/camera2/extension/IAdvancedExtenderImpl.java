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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAdvancedExtenderImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAdvancedExtenderImpl)) {
                return (IAdvancedExtenderImpl) queryLocalInterface;
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
            final HashMap hashMap;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAdvancedExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdvancedExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    hashMap = readInt >= 0 ? new HashMap() : null;
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), (CameraMetadataNative) Parcel.this.readTypedObject(CameraMetadataNative.CREATOR));
                        }
                    });
                    parcel.enforceNoDataAvail();
                    boolean isExtensionAvailable = isExtensionAvailable(readString, hashMap);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExtensionAvailable);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    hashMap = readInt2 >= 0 ? new HashMap() : null;
                    IntStream.range(0, readInt2).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), (CameraMetadataNative) Parcel.this.readTypedObject(CameraMetadataNative.CREATOR));
                        }
                    });
                    parcel.enforceNoDataAvail();
                    init(readString2, hashMap);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    Size size = (Size) parcel.readTypedObject(Size.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LatencyRange estimatedCaptureLatencyRange = getEstimatedCaptureLatencyRange(readString3, size, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(estimatedCaptureLatencyRange, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SizeList> supportedPreviewOutputResolutions = getSupportedPreviewOutputResolutions(readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPreviewOutputResolutions, 1);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SizeList> supportedCaptureOutputResolutions = getSupportedCaptureOutputResolutions(readString5);
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
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative availableCaptureRequestKeys = getAvailableCaptureRequestKeys(readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureRequestKeys, 1);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative availableCaptureResultKeys = getAvailableCaptureResultKeys(readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureResultKeys, 1);
                    return true;
                case 10:
                    boolean isCaptureProcessProgressAvailable = isCaptureProcessProgressAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaptureProcessProgressAvailable);
                    return true;
                case 11:
                    boolean isPostviewAvailable = isPostviewAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPostviewAvailable);
                    return true;
                case 12:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative availableCharacteristicsKeyValues = getAvailableCharacteristicsKeyValues(readString8);
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
                final Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAdvancedExtenderImpl.Stub.Proxy.lambda$isExtensionAvailable$0(Parcel.this, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$isExtensionAvailable$0(Parcel parcel, String str, CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public void init(String str, Map<String, CameraMetadataNative> map) throws RemoteException {
                final Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IAdvancedExtenderImpl.Stub.Proxy.lambda$init$1(Parcel.this, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$init$1(Parcel parcel, String str, CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public LatencyRange getEstimatedCaptureLatencyRange(String str, Size size, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(size, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LatencyRange) obtain2.readTypedObject(LatencyRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedPreviewOutputResolutions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedCaptureOutputResolutions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public ISessionProcessorImpl getSessionProcessor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISessionProcessorImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCaptureRequestKeys(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCaptureResultKeys(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isPostviewAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public CameraMetadataNative getAvailableCharacteristicsKeyValues(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
