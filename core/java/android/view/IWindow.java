package android.view;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.inputmethod.ImeTracker;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;

import com.android.internal.os.IResultReceiver;

import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;

/* loaded from: classes4.dex */
public interface IWindow extends IInterface {
    void closeSystemDialogs(String str) throws RemoteException;

    void dispatchAppVisibility(boolean z) throws RemoteException;

    void dispatchDragEvent(DragEvent dragEvent) throws RemoteException;

    void dispatchDragEventUpdated(DragEvent dragEvent) throws RemoteException;

    void dispatchGetNewSurface() throws RemoteException;

    void dispatchLetterboxDirectionChanged(int i) throws RemoteException;

    void dispatchSPenGestureEvent(InputEvent[] inputEventArr) throws RemoteException;

    void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo)
            throws RemoteException;

    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z)
            throws RemoteException;

    void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z)
            throws RemoteException;

    void dispatchWindowShown() throws RemoteException;

    void dumpWindow(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor)
            throws RemoteException;

    void hideInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException;

    void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array)
            throws RemoteException;

    void invalidateForScreenShot(boolean z) throws RemoteException;

    void moved(int i, int i2) throws RemoteException;

    void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener)
            throws RemoteException;

    void resized(
            ClientWindowFrames clientWindowFrames,
            boolean z,
            MergedConfiguration mergedConfiguration,
            InsetsState insetsState,
            boolean z2,
            boolean z3,
            int i,
            int i2,
            boolean z4,
            ActivityWindowInfo activityWindowInfo)
            throws RemoteException;

    void showInsets(int i, boolean z, ImeTracker.Token token) throws RemoteException;

    void windowFocusInTaskChanged(boolean z) throws RemoteException;

    public static class Default implements IWindow {
        @Override // android.view.IWindow
        public void executeCommand(
                String command, String parameters, ParcelFileDescriptor descriptor)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void resized(
                ClientWindowFrames frames,
                boolean reportDraw,
                MergedConfiguration newMergedConfiguration,
                InsetsState insetsState,
                boolean forceLayout,
                boolean alwaysConsumeSystemBars,
                int displayId,
                int syncSeqId,
                boolean dragResizing,
                ActivityWindowInfo activityWindowInfo)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void insetsControlChanged(
                InsetsState insetsState, InsetsSourceControl.Array activeControls)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void showInsets(int types, boolean fromIme, ImeTracker.Token statsToken)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void hideInsets(int types, boolean fromIme, ImeTracker.Token statsToken)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void moved(int newX, int newY) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean visible) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() throws RemoteException {}

        @Override // android.view.IWindow
        public void closeSystemDialogs(String reason) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(
                float x, float y, float xStep, float yStep, float zoom, boolean sync)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(
                String action, int x, int y, int z, Bundle extras, boolean sync)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchDragEvent(DragEvent event) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchWindowShown() throws RemoteException {}

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void requestScrollCapture(IScrollCaptureResponseListener callbacks)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void dumpWindow(ParcelFileDescriptor pfd) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo request)
                throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchLetterboxDirectionChanged(int direction) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchSPenGestureEvent(InputEvent[] events) throws RemoteException {}

        @Override // android.view.IWindow
        public void dispatchDragEventUpdated(DragEvent event) throws RemoteException {}

        @Override // android.view.IWindow
        public void invalidateForScreenShot(boolean forceMode) throws RemoteException {}

        @Override // android.view.IWindow
        public void windowFocusInTaskChanged(boolean hasFocus) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWindow {
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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindow asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWindow)) {
                return (IWindow) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    ParcelFileDescriptor _arg2 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    executeCommand(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    ClientWindowFrames _arg02 =
                            (ClientWindowFrames) data.readTypedObject(ClientWindowFrames.CREATOR);
                    boolean _arg12 = data.readBoolean();
                    MergedConfiguration _arg22 =
                            (MergedConfiguration) data.readTypedObject(MergedConfiguration.CREATOR);
                    InsetsState _arg3 = (InsetsState) data.readTypedObject(InsetsState.CREATOR);
                    boolean _arg4 = data.readBoolean();
                    boolean _arg5 = data.readBoolean();
                    int _arg6 = data.readInt();
                    int _arg7 = data.readInt();
                    boolean _arg8 = data.readBoolean();
                    ActivityWindowInfo _arg9 =
                            (ActivityWindowInfo) data.readTypedObject(ActivityWindowInfo.CREATOR);
                    data.enforceNoDataAvail();
                    resized(
                            _arg02, _arg12, _arg22, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8,
                            _arg9);
                    return true;
                case 3:
                    InsetsState _arg03 = (InsetsState) data.readTypedObject(InsetsState.CREATOR);
                    InsetsSourceControl.Array _arg13 =
                            (InsetsSourceControl.Array)
                                    data.readTypedObject(InsetsSourceControl.Array.CREATOR);
                    data.enforceNoDataAvail();
                    insetsControlChanged(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    boolean _arg14 = data.readBoolean();
                    ImeTracker.Token _arg23 =
                            (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    data.enforceNoDataAvail();
                    showInsets(_arg04, _arg14, _arg23);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    boolean _arg15 = data.readBoolean();
                    ImeTracker.Token _arg24 =
                            (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    data.enforceNoDataAvail();
                    hideInsets(_arg05, _arg15, _arg24);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    moved(_arg06, _arg16);
                    return true;
                case 7:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dispatchAppVisibility(_arg07);
                    return true;
                case 8:
                    dispatchGetNewSurface();
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    closeSystemDialogs(_arg08);
                    return true;
                case 10:
                    float _arg09 = data.readFloat();
                    float _arg17 = data.readFloat();
                    float _arg25 = data.readFloat();
                    float _arg32 = data.readFloat();
                    float _arg42 = data.readFloat();
                    boolean _arg52 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dispatchWallpaperOffsets(_arg09, _arg17, _arg25, _arg32, _arg42, _arg52);
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    int _arg18 = data.readInt();
                    int _arg26 = data.readInt();
                    int _arg33 = data.readInt();
                    Bundle _arg43 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    boolean _arg53 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dispatchWallpaperCommand(_arg010, _arg18, _arg26, _arg33, _arg43, _arg53);
                    return true;
                case 12:
                    DragEvent _arg011 = (DragEvent) data.readTypedObject(DragEvent.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchDragEvent(_arg011);
                    return true;
                case 13:
                    dispatchWindowShown();
                    return true;
                case 14:
                    IResultReceiver _arg012 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(_arg012, _arg19);
                    return true;
                case 15:
                    IScrollCaptureResponseListener _arg013 =
                            IScrollCaptureResponseListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestScrollCapture(_arg013);
                    return true;
                case 16:
                    ParcelFileDescriptor _arg014 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    dumpWindow(_arg014);
                    return true;
                case 17:
                    SmartClipRemoteRequestInfo _arg015 =
                            (SmartClipRemoteRequestInfo)
                                    data.readTypedObject(SmartClipRemoteRequestInfo.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchSmartClipRemoteRequest(_arg015);
                    return true;
                case 18:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    dispatchLetterboxDirectionChanged(_arg016);
                    return true;
                case 19:
                    InputEvent[] _arg017 = (InputEvent[]) data.createTypedArray(InputEvent.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchSPenGestureEvent(_arg017);
                    return true;
                case 20:
                    DragEvent _arg018 = (DragEvent) data.readTypedObject(DragEvent.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchDragEventUpdated(_arg018);
                    return true;
                case 21:
                    boolean _arg019 = data.readBoolean();
                    data.enforceNoDataAvail();
                    invalidateForScreenShot(_arg019);
                    return true;
                case 22:
                    boolean _arg020 = data.readBoolean();
                    data.enforceNoDataAvail();
                    windowFocusInTaskChanged(_arg020);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWindow {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindow
            public void executeCommand(
                    String command, String parameters, ParcelFileDescriptor descriptor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    _data.writeString(parameters);
                    _data.writeTypedObject(descriptor, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void resized(
                    ClientWindowFrames frames,
                    boolean reportDraw,
                    MergedConfiguration newMergedConfiguration,
                    InsetsState insetsState,
                    boolean forceLayout,
                    boolean alwaysConsumeSystemBars,
                    int displayId,
                    int syncSeqId,
                    boolean dragResizing,
                    ActivityWindowInfo activityWindowInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(frames, 0);
                    _data.writeBoolean(reportDraw);
                    _data.writeTypedObject(newMergedConfiguration, 0);
                    _data.writeTypedObject(insetsState, 0);
                    _data.writeBoolean(forceLayout);
                    _data.writeBoolean(alwaysConsumeSystemBars);
                    _data.writeInt(displayId);
                    _data.writeInt(syncSeqId);
                    _data.writeBoolean(dragResizing);
                    _data.writeTypedObject(activityWindowInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void insetsControlChanged(
                    InsetsState insetsState, InsetsSourceControl.Array activeControls)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(insetsState, 0);
                    _data.writeTypedObject(activeControls, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void showInsets(int types, boolean fromIme, ImeTracker.Token statsToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(types);
                    _data.writeBoolean(fromIme);
                    _data.writeTypedObject(statsToken, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void hideInsets(int types, boolean fromIme, ImeTracker.Token statsToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(types);
                    _data.writeBoolean(fromIme);
                    _data.writeTypedObject(statsToken, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void moved(int newX, int newY) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newX);
                    _data.writeInt(newY);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchAppVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchGetNewSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWallpaperOffsets(
                    float x, float y, float xStep, float yStep, float zoom, boolean sync)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(x);
                    _data.writeFloat(y);
                    _data.writeFloat(xStep);
                    _data.writeFloat(yStep);
                    _data.writeFloat(zoom);
                    _data.writeBoolean(sync);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWallpaperCommand(
                    String action, int x, int y, int z, Bundle extras, boolean sync)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeInt(z);
                    _data.writeTypedObject(extras, 0);
                    _data.writeBoolean(sync);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchDragEvent(DragEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchWindowShown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void requestScrollCapture(IScrollCaptureResponseListener callbacks)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callbacks);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dumpWindow(ParcelFileDescriptor pfd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(pfd, 0);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo request)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchLetterboxDirectionChanged(int direction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchSPenGestureEvent(InputEvent[] events) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(events, 0);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void dispatchDragEventUpdated(DragEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void invalidateForScreenShot(boolean forceMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(forceMode);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.IWindow
            public void windowFocusInTaskChanged(boolean hasFocus) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(hasFocus);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }
    }
}
