package android.view.accessibility;

import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.accessibility.IWindowSurfaceInfoCallback;
import android.window.ScreenCapture;

/* loaded from: classes4.dex */
public interface IAccessibilityInteractionConnection extends IInterface {

    public static class Default implements IAccessibilityInteractionConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void clearAccessibilityFocus() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr, Bundle bundle) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void getWindowSurfaceInfo(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void takeScreenshotOfWindow(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
        }
    }

    void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException;

    void clearAccessibilityFocus() throws RemoteException;

    void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr, Bundle bundle) throws RemoteException;

    void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException;

    void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException;

    void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException;

    void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException;

    void getWindowSurfaceInfo(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) throws RemoteException;

    void notifyOutsideTouch() throws RemoteException;

    void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws RemoteException;

    void takeScreenshotOfWindow(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityInteractionConnection {
        public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnection";
        static final int TRANSACTION_attachAccessibilityOverlayToWindow = 11;
        static final int TRANSACTION_clearAccessibilityFocus = 7;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 1;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 2;
        static final int TRANSACTION_findFocus = 4;
        static final int TRANSACTION_focusSearch = 5;
        static final int TRANSACTION_getWindowSurfaceInfo = 10;
        static final int TRANSACTION_notifyOutsideTouch = 8;
        static final int TRANSACTION_performAccessibilityAction = 6;
        static final int TRANSACTION_takeScreenshotOfWindow = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityInteractionConnection)) {
                return (IAccessibilityInteractionConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "findAccessibilityNodeInfoByAccessibilityId";
                case 2:
                    return "findAccessibilityNodeInfosByViewId";
                case 3:
                    return "findAccessibilityNodeInfosByText";
                case 4:
                    return "findFocus";
                case 5:
                    return "focusSearch";
                case 6:
                    return "performAccessibilityAction";
                case 7:
                    return "clearAccessibilityFocus";
                case 8:
                    return "notifyOutsideTouch";
                case 9:
                    return "takeScreenshotOfWindow";
                case 10:
                    return "getWindowSurfaceInfo";
                case 11:
                    return "attachAccessibilityOverlayToWindow";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    int readInt = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    MagnificationSpec magnificationSpec = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] createFloatArray = parcel.createFloatArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfoByAccessibilityId(readLong, region, readInt, asInterface, readInt2, readInt3, readLong2, magnificationSpec, createFloatArray, bundle);
                    return true;
                case 2:
                    long readLong3 = parcel.readLong();
                    String readString = parcel.readString();
                    Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int readInt4 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface2 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    MagnificationSpec magnificationSpec2 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] createFloatArray2 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfosByViewId(readLong3, readString, region2, readInt4, asInterface2, readInt5, readInt6, readLong4, magnificationSpec2, createFloatArray2);
                    return true;
                case 3:
                    long readLong5 = parcel.readLong();
                    String readString2 = parcel.readString();
                    Region region3 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int readInt7 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface3 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    MagnificationSpec magnificationSpec3 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] createFloatArray3 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfosByText(readLong5, readString2, region3, readInt7, asInterface3, readInt8, readInt9, readLong6, magnificationSpec3, createFloatArray3);
                    return true;
                case 4:
                    long readLong7 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    Region region4 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int readInt11 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface4 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    long readLong8 = parcel.readLong();
                    MagnificationSpec magnificationSpec4 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] createFloatArray4 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findFocus(readLong7, readInt10, region4, readInt11, asInterface4, readInt12, readInt13, readLong8, magnificationSpec4, createFloatArray4);
                    return true;
                case 5:
                    long readLong9 = parcel.readLong();
                    int readInt14 = parcel.readInt();
                    Region region5 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int readInt15 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface5 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    long readLong10 = parcel.readLong();
                    MagnificationSpec magnificationSpec5 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] createFloatArray5 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    focusSearch(readLong9, readInt14, region5, readInt15, asInterface5, readInt16, readInt17, readLong10, magnificationSpec5, createFloatArray5);
                    return true;
                case 6:
                    long readLong11 = parcel.readLong();
                    int readInt18 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface6 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    performAccessibilityAction(readLong11, readInt18, bundle2, readInt19, asInterface6, readInt20, readInt21, readLong12);
                    return true;
                case 7:
                    clearAccessibilityFocus();
                    return true;
                case 8:
                    notifyOutsideTouch();
                    return true;
                case 9:
                    int readInt22 = parcel.readInt();
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    IAccessibilityInteractionConnectionCallback asInterface7 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    takeScreenshotOfWindow(readInt22, screenCaptureListener, asInterface7);
                    return true;
                case 10:
                    IWindowSurfaceInfoCallback asInterface8 = IWindowSurfaceInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getWindowSurfaceInfo(asInterface8);
                    return true;
                case 11:
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int readInt23 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface9 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToWindow(surfaceControl, readInt23, asInterface9);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccessibilityInteractionConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(magnificationSpec, 0);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void clearAccessibilityFocus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void notifyOutsideTouch() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void takeScreenshotOfWindow(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void getWindowSurfaceInfo(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowSurfaceInfoCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
