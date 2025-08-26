package android.app;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.graphics.Rect;
import android.hardware.usb.UsbManager;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.InputEvent;
import android.view.SurfaceControl;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.window.ScreenCapture;
import java.util.List;

/* loaded from: classes.dex */
public interface IUiAutomationConnection extends IInterface {

    public static class Default implements IUiAutomationConnection {
        @Override // android.app.IUiAutomationConnection
        public void addOverridePermissionState(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void adoptShellPermissionIdentity(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUiAutomationConnection
        public void clearAllOverridePermissionStates() throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void clearOverridePermissionStates(int i) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void clearWindowAnimationFrameStats() throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public boolean clearWindowContentFrameStats(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void disconnect() throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void dropShellPermissionIdentity() throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void executeShellCommand(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void executeShellCommandArrayWithStderr(String[] strArr, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void executeShellCommandWithStderr(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public List<String> getAdoptedShellPermissions() throws RemoteException {
            return null;
        }

        @Override // android.app.IUiAutomationConnection
        public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException {
            return null;
        }

        @Override // android.app.IUiAutomationConnection
        public WindowContentFrameStats getWindowContentFrameStats(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IUiAutomationConnection
        public void grantRuntimePermission(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public boolean injectInputEvent(InputEvent inputEvent, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void removeOverridePermissionState(int i, String str) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void revokeRuntimePermission(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public boolean setRotation(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public void shutdown() throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public void syncInputTransactions(boolean z) throws RemoteException {
        }

        @Override // android.app.IUiAutomationConnection
        public boolean takeScreenshot(Rect rect, ScreenCapture.ScreenCaptureListener screenCaptureListener, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiAutomationConnection
        public boolean takeSurfaceControlScreenshot(SurfaceControl surfaceControl, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException {
            return false;
        }
    }

    void addOverridePermissionState(int i, String str, int i2) throws RemoteException;

    void adoptShellPermissionIdentity(int i, String[] strArr) throws RemoteException;

    void clearAllOverridePermissionStates() throws RemoteException;

    void clearOverridePermissionStates(int i) throws RemoteException;

    void clearWindowAnimationFrameStats() throws RemoteException;

    boolean clearWindowContentFrameStats(int i) throws RemoteException;

    void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException;

    void disconnect() throws RemoteException;

    void dropShellPermissionIdentity() throws RemoteException;

    void executeShellCommand(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException;

    void executeShellCommandArrayWithStderr(String[] strArr, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException;

    void executeShellCommandWithStderr(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException;

    List<String> getAdoptedShellPermissions() throws RemoteException;

    WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException;

    WindowContentFrameStats getWindowContentFrameStats(int i) throws RemoteException;

    void grantRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean injectInputEvent(InputEvent inputEvent, boolean z, boolean z2) throws RemoteException;

    void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException;

    void removeOverridePermissionState(int i, String str) throws RemoteException;

    void revokeRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean setRotation(int i) throws RemoteException;

    void shutdown() throws RemoteException;

    void syncInputTransactions(boolean z) throws RemoteException;

    boolean takeScreenshot(Rect rect, ScreenCapture.ScreenCaptureListener screenCaptureListener, int i) throws RemoteException;

    boolean takeSurfaceControlScreenshot(SurfaceControl surfaceControl, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IUiAutomationConnection {
        public static final String DESCRIPTOR = "android.app.IUiAutomationConnection";
        static final int TRANSACTION_addOverridePermissionState = 22;
        static final int TRANSACTION_adoptShellPermissionIdentity = 16;
        static final int TRANSACTION_clearAllOverridePermissionStates = 25;
        static final int TRANSACTION_clearOverridePermissionStates = 24;
        static final int TRANSACTION_clearWindowAnimationFrameStats = 11;
        static final int TRANSACTION_clearWindowContentFrameStats = 9;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_dropShellPermissionIdentity = 17;
        static final int TRANSACTION_executeShellCommand = 13;
        static final int TRANSACTION_executeShellCommandArrayWithStderr = 20;
        static final int TRANSACTION_executeShellCommandWithStderr = 19;
        static final int TRANSACTION_getAdoptedShellPermissions = 21;
        static final int TRANSACTION_getWindowAnimationFrameStats = 12;
        static final int TRANSACTION_getWindowContentFrameStats = 10;
        static final int TRANSACTION_grantRuntimePermission = 14;
        static final int TRANSACTION_injectInputEvent = 3;
        static final int TRANSACTION_injectInputEventToInputFilter = 4;
        static final int TRANSACTION_removeOverridePermissionState = 23;
        static final int TRANSACTION_revokeRuntimePermission = 15;
        static final int TRANSACTION_setRotation = 6;
        static final int TRANSACTION_shutdown = 18;
        static final int TRANSACTION_syncInputTransactions = 5;
        static final int TRANSACTION_takeScreenshot = 7;
        static final int TRANSACTION_takeSurfaceControlScreenshot = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUiAutomationConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUiAutomationConnection)) {
                return (IUiAutomationConnection) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return MediaMetrics.Value.CONNECT;
                case 2:
                    return MediaMetrics.Value.DISCONNECT;
                case 3:
                    return "injectInputEvent";
                case 4:
                    return "injectInputEventToInputFilter";
                case 5:
                    return "syncInputTransactions";
                case 6:
                    return "setRotation";
                case 7:
                    return "takeScreenshot";
                case 8:
                    return "takeSurfaceControlScreenshot";
                case 9:
                    return "clearWindowContentFrameStats";
                case 10:
                    return "getWindowContentFrameStats";
                case 11:
                    return "clearWindowAnimationFrameStats";
                case 12:
                    return "getWindowAnimationFrameStats";
                case 13:
                    return "executeShellCommand";
                case 14:
                    return "grantRuntimePermission";
                case 15:
                    return "revokeRuntimePermission";
                case 16:
                    return "adoptShellPermissionIdentity";
                case 17:
                    return "dropShellPermissionIdentity";
                case 18:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 19:
                    return "executeShellCommandWithStderr";
                case 20:
                    return "executeShellCommandArrayWithStderr";
                case 21:
                    return "getAdoptedShellPermissions";
                case 22:
                    return "addOverridePermissionState";
                case 23:
                    return "removeOverridePermissionState";
                case 24:
                    return "clearOverridePermissionStates";
                case 25:
                    return "clearAllOverridePermissionStates";
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
                    IAccessibilityServiceClient iAccessibilityServiceClientAsInterface = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    connect(iAccessibilityServiceClientAsInterface, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    disconnect();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    InputEvent inputEvent = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zInjectInputEvent = injectInputEvent(inputEvent, z, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInjectInputEvent);
                    return true;
                case 4:
                    InputEvent inputEvent2 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectInputEventToInputFilter(inputEvent2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    syncInputTransactions(z3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean rotation = setRotation(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rotation);
                    return true;
                case 7:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zTakeScreenshot = takeScreenshot(rect, screenCaptureListener, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTakeScreenshot);
                    return true;
                case 8:
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    ScreenCapture.ScreenCaptureListener screenCaptureListener2 = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zTakeSurfaceControlScreenshot = takeSurfaceControlScreenshot(surfaceControl, screenCaptureListener2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTakeSurfaceControlScreenshot);
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearWindowContentFrameStats = clearWindowContentFrameStats(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearWindowContentFrameStats);
                    return true;
                case 10:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowContentFrameStats windowContentFrameStats = getWindowContentFrameStats(i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContentFrameStats, 1);
                    return true;
                case 11:
                    clearWindowAnimationFrameStats();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    WindowAnimationFrameStats windowAnimationFrameStats = getWindowAnimationFrameStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowAnimationFrameStats, 1);
                    return true;
                case 13:
                    String string = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeShellCommand(string, parcelFileDescriptor, parcelFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(string2, string3, i8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(string4, string5, i9);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i10 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    adoptShellPermissionIdentity(i10, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    dropShellPermissionIdentity();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    shutdown();
                    return true;
                case 19:
                    String string6 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor5 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeShellCommandWithStderr(string6, parcelFileDescriptor3, parcelFileDescriptor4, parcelFileDescriptor5);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    ParcelFileDescriptor parcelFileDescriptor6 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor7 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor8 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeShellCommandArrayWithStderr(strArrCreateStringArray2, parcelFileDescriptor6, parcelFileDescriptor7, parcelFileDescriptor8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    List<String> adoptedShellPermissions = getAdoptedShellPermissions();
                    parcel2.writeNoException();
                    parcel2.writeStringList(adoptedShellPermissions);
                    return true;
                case 22:
                    int i11 = parcel.readInt();
                    String string7 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOverridePermissionState(i11, string7, i12);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i13 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeOverridePermissionState(i13, string8);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearOverridePermissionStates(i14);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    clearAllOverridePermissionStates();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUiAutomationConnection {
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

            @Override // android.app.IUiAutomationConnection
            public void connect(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccessibilityServiceClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void disconnect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean injectInputEvent(InputEvent inputEvent, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void syncInputTransactions(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean setRotation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean takeScreenshot(Rect rect, ScreenCapture.ScreenCaptureListener screenCaptureListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeTypedObject(screenCaptureListener, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean takeSurfaceControlScreenshot(SurfaceControl surfaceControl, ScreenCapture.ScreenCaptureListener screenCaptureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeTypedObject(screenCaptureListener, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public boolean clearWindowContentFrameStats(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public WindowContentFrameStats getWindowContentFrameStats(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowContentFrameStats) parcelObtain2.readTypedObject(WindowContentFrameStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void clearWindowAnimationFrameStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public WindowAnimationFrameStats getWindowAnimationFrameStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowAnimationFrameStats) parcelObtain2.readTypedObject(WindowAnimationFrameStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void executeShellCommand(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void grantRuntimePermission(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void revokeRuntimePermission(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void adoptShellPermissionIdentity(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void dropShellPermissionIdentity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void shutdown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void executeShellCommandWithStderr(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor3, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void executeShellCommandArrayWithStderr(String[] strArr, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor3, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public List<String> getAdoptedShellPermissions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void addOverridePermissionState(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void removeOverridePermissionState(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void clearOverridePermissionStates(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiAutomationConnection
            public void clearAllOverridePermissionStates() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
