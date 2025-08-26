package android.view;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.IScrollCaptureResponseListener;
import android.view.InsetsSourceControl;
import android.view.inputmethod.ImeTracker;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import com.android.internal.os.IResultReceiver;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;

/* loaded from: classes4.dex */
public interface IWindow extends IInterface {

    public static class Default implements IWindow {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IWindow
        public void closeSystemDialogs(String str) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchDragEvent(DragEvent dragEvent) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchDragEventUpdated(DragEvent dragEvent) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchLetterboxDirectionChanged(int i) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchSPenGestureEvent(InputEvent[] inputEventArr) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dispatchWindowShown() throws RemoteException {
        }

        @Override // android.view.IWindow
        public void dumpWindow(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void invalidateForScreenShot(boolean z) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void moved(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
        }

        @Override // android.view.IWindow
        public void windowFocusInTaskChanged(boolean z) throws RemoteException {
        }
    }

    void closeSystemDialogs(String str) throws RemoteException;

    void dispatchAppVisibility(boolean z) throws RemoteException;

    void dispatchDragEvent(DragEvent dragEvent) throws RemoteException;

    void dispatchDragEventUpdated(DragEvent dragEvent) throws RemoteException;

    void dispatchGetNewSurface() throws RemoteException;

    void dispatchLetterboxDirectionChanged(int i) throws RemoteException;

    void dispatchSPenGestureEvent(InputEvent[] inputEventArr) throws RemoteException;

    void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) throws RemoteException;

    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException;

    void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) throws RemoteException;

    void dispatchWindowShown() throws RemoteException;

    void dumpWindow(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException;

    void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) throws RemoteException;

    void invalidateForScreenShot(boolean z) throws RemoteException;

    void moved(int i, int i2) throws RemoteException;

    void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException;

    void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) throws RemoteException;

    void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException;

    void windowFocusInTaskChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindow {
        public static final String DESCRIPTOR = "android.view.IWindow";
        static final int TRANSACTION_closeSystemDialogs = 9;
        static final int TRANSACTION_dispatchAppVisibility = 7;
        static final int TRANSACTION_dispatchDragEvent = 12;
        static final int TRANSACTION_dispatchDragEventUpdated = 20;
        static final int TRANSACTION_dispatchGetNewSurface = 8;
        static final int TRANSACTION_dispatchLetterboxDirectionChanged = 18;
        static final int TRANSACTION_dispatchSPenGestureEvent = 19;
        static final int TRANSACTION_dispatchSmartClipRemoteRequest = 17;
        static final int TRANSACTION_dispatchWallpaperCommand = 11;
        static final int TRANSACTION_dispatchWallpaperOffsets = 10;
        static final int TRANSACTION_dispatchWindowShown = 13;
        static final int TRANSACTION_dumpWindow = 16;
        static final int TRANSACTION_executeCommand = 1;
        static final int TRANSACTION_hideInsets = 5;
        static final int TRANSACTION_insetsControlChanged = 3;
        static final int TRANSACTION_invalidateForScreenShot = 21;
        static final int TRANSACTION_moved = 6;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 14;
        static final int TRANSACTION_requestScrollCapture = 15;
        static final int TRANSACTION_resized = 2;
        static final int TRANSACTION_showInsets = 4;
        static final int TRANSACTION_windowFocusInTaskChanged = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindow asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWindow)) {
                return (IWindow) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "executeCommand";
                case 2:
                    return "resized";
                case 3:
                    return "insetsControlChanged";
                case 4:
                    return "showInsets";
                case 5:
                    return "hideInsets";
                case 6:
                    return "moved";
                case 7:
                    return "dispatchAppVisibility";
                case 8:
                    return "dispatchGetNewSurface";
                case 9:
                    return "closeSystemDialogs";
                case 10:
                    return "dispatchWallpaperOffsets";
                case 11:
                    return "dispatchWallpaperCommand";
                case 12:
                    return "dispatchDragEvent";
                case 13:
                    return "dispatchWindowShown";
                case 14:
                    return "requestAppKeyboardShortcuts";
                case 15:
                    return "requestScrollCapture";
                case 16:
                    return "dumpWindow";
                case 17:
                    return "dispatchSmartClipRemoteRequest";
                case 18:
                    return "dispatchLetterboxDirectionChanged";
                case 19:
                    return "dispatchSPenGestureEvent";
                case 20:
                    return "dispatchDragEventUpdated";
                case 21:
                    return "invalidateForScreenShot";
                case 22:
                    return "windowFocusInTaskChanged";
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    executeCommand(string, string2, parcelFileDescriptor);
                    return true;
                case 2:
                    ClientWindowFrames clientWindowFrames = (ClientWindowFrames) parcel.readTypedObject(ClientWindowFrames.CREATOR);
                    boolean z = parcel.readBoolean();
                    MergedConfiguration mergedConfiguration = (MergedConfiguration) parcel.readTypedObject(MergedConfiguration.CREATOR);
                    InsetsState insetsState = (InsetsState) parcel.readTypedObject(InsetsState.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    ActivityWindowInfo activityWindowInfo = (ActivityWindowInfo) parcel.readTypedObject(ActivityWindowInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    resized(clientWindowFrames, z, mergedConfiguration, insetsState, z2, z3, i3, i4, z4, activityWindowInfo);
                    return true;
                case 3:
                    InsetsState insetsState2 = (InsetsState) parcel.readTypedObject(InsetsState.CREATOR);
                    InsetsSourceControl.Array array = (InsetsSourceControl.Array) parcel.readTypedObject(InsetsSourceControl.Array.CREATOR);
                    parcel.enforceNoDataAvail();
                    insetsControlChanged(insetsState2, array);
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    showInsets(i5, z5, token);
                    return true;
                case 5:
                    int i6 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideInsets(i6, z6, token2);
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moved(i7, i8);
                    return true;
                case 7:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchAppVisibility(z7);
                    return true;
                case 8:
                    dispatchGetNewSurface();
                    return true;
                case 9:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(string3);
                    return true;
                case 10:
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    float f3 = parcel.readFloat();
                    float f4 = parcel.readFloat();
                    float f5 = parcel.readFloat();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchWallpaperOffsets(f, f2, f3, f4, f5, z8);
                    return true;
                case 11:
                    String string4 = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchWallpaperCommand(string4, i9, i10, i11, bundle, z9);
                    return true;
                case 12:
                    DragEvent dragEvent = (DragEvent) parcel.readTypedObject(DragEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchDragEvent(dragEvent);
                    return true;
                case 13:
                    dispatchWindowShown();
                    return true;
                case 14:
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(iResultReceiverAsInterface, i12);
                    return true;
                case 15:
                    IScrollCaptureResponseListener iScrollCaptureResponseListenerAsInterface = IScrollCaptureResponseListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestScrollCapture(iScrollCaptureResponseListenerAsInterface);
                    return true;
                case 16:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpWindow(parcelFileDescriptor2);
                    return true;
                case 17:
                    SmartClipRemoteRequestInfo smartClipRemoteRequestInfo = (SmartClipRemoteRequestInfo) parcel.readTypedObject(SmartClipRemoteRequestInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchSmartClipRemoteRequest(smartClipRemoteRequestInfo);
                    return true;
                case 18:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchLetterboxDirectionChanged(i13);
                    return true;
                case 19:
                    InputEvent[] inputEventArr = (InputEvent[]) parcel.createTypedArray(InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchSPenGestureEvent(inputEventArr);
                    return true;
                case 20:
                    DragEvent dragEvent2 = (DragEvent) parcel.readTypedObject(DragEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchDragEventUpdated(dragEvent2);
                    return true;
                case 21:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    invalidateForScreenShot(z10);
                    return true;
                case 22:
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    windowFocusInTaskChanged(z11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWindow {
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

            @Override // android.view.IWindow
            public void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientWindowFrames, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(mergedConfiguration, 0);
                    parcelObtain.writeTypedObject(insetsState, 0);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeTypedObject(activityWindowInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(insetsState, 0);
                    parcelObtain.writeTypedObject(array, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void moved(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchAppVisibility(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchGetNewSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void closeSystemDialogs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    parcelObtain.writeFloat(f4);
                    parcelObtain.writeFloat(f5);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchDragEvent(DragEvent dragEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dragEvent, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWindowShown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iScrollCaptureResponseListener);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dumpWindow(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartClipRemoteRequestInfo, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchLetterboxDirectionChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchSPenGestureEvent(InputEvent[] inputEventArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(inputEventArr, 0);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchDragEventUpdated(DragEvent dragEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dragEvent, 0);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void invalidateForScreenShot(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindow
            public void windowFocusInTaskChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
