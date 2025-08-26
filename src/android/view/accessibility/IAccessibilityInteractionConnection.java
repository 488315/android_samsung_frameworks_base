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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccessibilityInteractionConnection)) {
                return (IAccessibilityInteractionConnection) iInterfaceQueryLocalInterface;
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
                    long j = parcel.readLong();
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    int i3 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    long j2 = parcel.readLong();
                    MagnificationSpec magnificationSpec = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfoByAccessibilityId(j, region, i3, iAccessibilityInteractionConnectionCallbackAsInterface, i4, i5, j2, magnificationSpec, fArrCreateFloatArray, bundle);
                    return true;
                case 2:
                    long j3 = parcel.readLong();
                    String string = parcel.readString();
                    Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int i6 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface2 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    long j4 = parcel.readLong();
                    MagnificationSpec magnificationSpec2 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] fArrCreateFloatArray2 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfosByViewId(j3, string, region2, i6, iAccessibilityInteractionConnectionCallbackAsInterface2, i7, i8, j4, magnificationSpec2, fArrCreateFloatArray2);
                    return true;
                case 3:
                    long j5 = parcel.readLong();
                    String string2 = parcel.readString();
                    Region region3 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int i9 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface3 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    long j6 = parcel.readLong();
                    MagnificationSpec magnificationSpec3 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] fArrCreateFloatArray3 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findAccessibilityNodeInfosByText(j5, string2, region3, i9, iAccessibilityInteractionConnectionCallbackAsInterface3, i10, i11, j6, magnificationSpec3, fArrCreateFloatArray3);
                    return true;
                case 4:
                    long j7 = parcel.readLong();
                    int i12 = parcel.readInt();
                    Region region4 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int i13 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface4 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    long j8 = parcel.readLong();
                    MagnificationSpec magnificationSpec4 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] fArrCreateFloatArray4 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    findFocus(j7, i12, region4, i13, iAccessibilityInteractionConnectionCallbackAsInterface4, i14, i15, j8, magnificationSpec4, fArrCreateFloatArray4);
                    return true;
                case 5:
                    long j9 = parcel.readLong();
                    int i16 = parcel.readInt();
                    Region region5 = (Region) parcel.readTypedObject(Region.CREATOR);
                    int i17 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface5 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    long j10 = parcel.readLong();
                    MagnificationSpec magnificationSpec5 = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                    float[] fArrCreateFloatArray5 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    focusSearch(j9, i16, region5, i17, iAccessibilityInteractionConnectionCallbackAsInterface5, i18, i19, j10, magnificationSpec5, fArrCreateFloatArray5);
                    return true;
                case 6:
                    long j11 = parcel.readLong();
                    int i20 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i21 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface6 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    long j12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    performAccessibilityAction(j11, i20, bundle2, i21, iAccessibilityInteractionConnectionCallbackAsInterface6, i22, i23, j12);
                    return true;
                case 7:
                    clearAccessibilityFocus();
                    return true;
                case 8:
                    notifyOutsideTouch();
                    return true;
                case 9:
                    int i24 = parcel.readInt();
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface7 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    takeScreenshotOfWindow(i24, screenCaptureListener, iAccessibilityInteractionConnectionCallbackAsInterface7);
                    return true;
                case 10:
                    IWindowSurfaceInfoCallback iWindowSurfaceInfoCallbackAsInterface = IWindowSurfaceInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getWindowSurfaceInfo(iWindowSurfaceInfoCallbackAsInterface);
                    return true;
                case 11:
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    int i25 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface8 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToWindow(surfaceControl, i25, iAccessibilityInteractionConnectionCallbackAsInterface8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(magnificationSpec, 0);
                    parcelObtain.writeFloatArray(fArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(magnificationSpec, 0);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(magnificationSpec, 0);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(magnificationSpec, 0);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(magnificationSpec, 0);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void clearAccessibilityFocus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void notifyOutsideTouch() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void takeScreenshotOfWindow(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(screenCaptureListener, 0);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void getWindowSurfaceInfo(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindowSurfaceInfoCallback);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnection
            public void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
