package android.service.wallpaper;

import android.graphics.Rect;
import android.graphics.RectF;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import java.util.List;

/* loaded from: classes3.dex */
public interface IWallpaperEngine extends IInterface {
    void addLocalColorsAreas(List<RectF> list) throws RemoteException;

    void applyDimming(float f) throws RemoteException;

    void destroy() throws RemoteException;

    void dispatchPointer(MotionEvent motionEvent) throws RemoteException;

    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) throws RemoteException;

    SurfaceControl mirrorSurfaceControl() throws RemoteException;

    void onScreenTurnedOn() throws RemoteException;

    void onScreenTurningOn() throws RemoteException;

    void removeLocalColorsAreas(List<RectF> list) throws RemoteException;

    void requestWallpaperColors() throws RemoteException;

    void resizePreview(Rect rect) throws RemoteException;

    void setDesiredSize(int i, int i2) throws RemoteException;

    void setDisplayPadding(Rect rect) throws RemoteException;

    void setInAmbientMode(boolean z, long j) throws RemoteException;

    void setSurfaceAlpha(float f) throws RemoteException;

    void setVisibility(boolean z) throws RemoteException;

    void setWallpaperFlags(int i) throws RemoteException;

    void setZoomOut(float f) throws RemoteException;

    public static class Default implements IWallpaperEngine {
        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDesiredSize(int width, int height) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDisplayPadding(Rect padding) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean visible) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurningOn() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurnedOn() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setInAmbientMode(boolean inAmbientDisplay, long animationDuration) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchPointer(MotionEvent event) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setSurfaceAlpha(float alpha) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void requestWallpaperColors() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setZoomOut(float scale) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void resizePreview(Rect positionInWindow) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void removeLocalColorsAreas(List<RectF> regions) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void addLocalColorsAreas(List<RectF> regions) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public SurfaceControl mirrorSurfaceControl() throws RemoteException {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void applyDimming(float dimAmount) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setWallpaperFlags(int which) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWallpaperEngine {
        public static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperEngine";
        static final int TRANSACTION_addLocalColorsAreas = 15;
        static final int TRANSACTION_applyDimming = 17;
        static final int TRANSACTION_destroy = 11;
        static final int TRANSACTION_dispatchPointer = 7;
        static final int TRANSACTION_dispatchWallpaperCommand = 8;
        static final int TRANSACTION_mirrorSurfaceControl = 16;
        static final int TRANSACTION_onScreenTurnedOn = 5;
        static final int TRANSACTION_onScreenTurningOn = 4;
        static final int TRANSACTION_removeLocalColorsAreas = 14;
        static final int TRANSACTION_requestWallpaperColors = 10;
        static final int TRANSACTION_resizePreview = 13;
        static final int TRANSACTION_setDesiredSize = 1;
        static final int TRANSACTION_setDisplayPadding = 2;
        static final int TRANSACTION_setInAmbientMode = 6;
        static final int TRANSACTION_setSurfaceAlpha = 9;
        static final int TRANSACTION_setVisibility = 3;
        static final int TRANSACTION_setWallpaperFlags = 18;
        static final int TRANSACTION_setZoomOut = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperEngine asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWallpaperEngine)) {
                return (IWallpaperEngine) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setDesiredSize";
                case 2:
                    return "setDisplayPadding";
                case 3:
                    return "setVisibility";
                case 4:
                    return "onScreenTurningOn";
                case 5:
                    return "onScreenTurnedOn";
                case 6:
                    return "setInAmbientMode";
                case 7:
                    return "dispatchPointer";
                case 8:
                    return "dispatchWallpaperCommand";
                case 9:
                    return "setSurfaceAlpha";
                case 10:
                    return "requestWallpaperColors";
                case 11:
                    return "destroy";
                case 12:
                    return "setZoomOut";
                case 13:
                    return "resizePreview";
                case 14:
                    return "removeLocalColorsAreas";
                case 15:
                    return "addLocalColorsAreas";
                case 16:
                    return "mirrorSurfaceControl";
                case 17:
                    return "applyDimming";
                case 18:
                    return "setWallpaperFlags";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            setDesiredSize(_arg0, _arg1);
                            return true;
                        case 2:
                            Rect _arg02 = (Rect) data.readTypedObject(Rect.CREATOR);
                            data.enforceNoDataAvail();
                            setDisplayPadding(_arg02);
                            return true;
                        case 3:
                            boolean _arg03 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setVisibility(_arg03);
                            return true;
                        case 4:
                            onScreenTurningOn();
                            return true;
                        case 5:
                            onScreenTurnedOn();
                            return true;
                        case 6:
                            boolean _arg04 = data.readBoolean();
                            long _arg12 = data.readLong();
                            data.enforceNoDataAvail();
                            setInAmbientMode(_arg04, _arg12);
                            return true;
                        case 7:
                            MotionEvent _arg05 = (MotionEvent) data.readTypedObject(MotionEvent.CREATOR);
                            data.enforceNoDataAvail();
                            dispatchPointer(_arg05);
                            return true;
                        case 8:
                            String _arg06 = data.readString();
                            int _arg13 = data.readInt();
                            int _arg2 = data.readInt();
                            int _arg3 = data.readInt();
                            Bundle _arg4 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            dispatchWallpaperCommand(_arg06, _arg13, _arg2, _arg3, _arg4);
                            return true;
                        case 9:
                            float _arg07 = data.readFloat();
                            data.enforceNoDataAvail();
                            setSurfaceAlpha(_arg07);
                            return true;
                        case 10:
                            requestWallpaperColors();
                            return true;
                        case 11:
                            destroy();
                            return true;
                        case 12:
                            float _arg08 = data.readFloat();
                            data.enforceNoDataAvail();
                            setZoomOut(_arg08);
                            return true;
                        case 13:
                            Rect _arg09 = (Rect) data.readTypedObject(Rect.CREATOR);
                            data.enforceNoDataAvail();
                            resizePreview(_arg09);
                            return true;
                        case 14:
                            List<RectF> _arg010 = data.createTypedArrayList(RectF.CREATOR);
                            data.enforceNoDataAvail();
                            removeLocalColorsAreas(_arg010);
                            return true;
                        case 15:
                            List<RectF> _arg011 = data.createTypedArrayList(RectF.CREATOR);
                            data.enforceNoDataAvail();
                            addLocalColorsAreas(_arg011);
                            return true;
                        case 16:
                            SurfaceControl _result = mirrorSurfaceControl();
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 17:
                            float _arg012 = data.readFloat();
                            data.enforceNoDataAvail();
                            applyDimming(_arg012);
                            return true;
                        case 18:
                            int _arg013 = data.readInt();
                            data.enforceNoDataAvail();
                            setWallpaperFlags(_arg013);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IWallpaperEngine {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setDesiredSize(int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setDisplayPadding(Rect padding) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(padding, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void onScreenTurningOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void onScreenTurnedOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setInAmbientMode(boolean inAmbientDisplay, long animationDuration) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(inAmbientDisplay);
                    _data.writeLong(animationDuration);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchPointer(MotionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeInt(z);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setSurfaceAlpha(float alpha) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(alpha);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void requestWallpaperColors() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void destroy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setZoomOut(float scale) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(scale);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void resizePreview(Rect positionInWindow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(positionInWindow, 0);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void removeLocalColorsAreas(List<RectF> regions) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(regions, 0);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void addLocalColorsAreas(List<RectF> regions) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(regions, 0);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public SurfaceControl mirrorSurfaceControl() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    SurfaceControl _result = (SurfaceControl) _reply.readTypedObject(SurfaceControl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void applyDimming(float dimAmount) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(dimAmount);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setWallpaperFlags(int which) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(which);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
