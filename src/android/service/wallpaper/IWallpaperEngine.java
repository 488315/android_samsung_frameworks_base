package android.service.wallpaper;

import android.app.wallpaper.WallpaperDescription;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IWallpaperEngine extends IInterface {

    public static class Default implements IWallpaperEngine {
        @Override // android.service.wallpaper.IWallpaperEngine
        public void addLocalColorsAreas(List<RectF> list) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void applyDimming(float f) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchPointer(MotionEvent motionEvent) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public SurfaceControl mirrorSurfaceControl() throws RemoteException {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public WallpaperDescription onApplyWallpaper(int i) throws RemoteException {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurnedOn() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurningOn() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void removeLocalColorsAreas(List<RectF> list) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void requestWallpaperColors() throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void resizePreview(Rect rect) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDesiredSize(int i, int i2) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDisplayPadding(Rect rect) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setInAmbientMode(boolean z, long j) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setSurfaceAlpha(float f) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean z) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setWallpaperFlags(int i) throws RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setZoomOut(float f) throws RemoteException {
        }
    }

    void addLocalColorsAreas(List<RectF> list) throws RemoteException;

    void applyDimming(float f) throws RemoteException;

    void destroy() throws RemoteException;

    void dispatchPointer(MotionEvent motionEvent) throws RemoteException;

    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) throws RemoteException;

    SurfaceControl mirrorSurfaceControl() throws RemoteException;

    WallpaperDescription onApplyWallpaper(int i) throws RemoteException;

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

    public static abstract class Stub extends Binder implements IWallpaperEngine {
        public static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperEngine";
        static final int TRANSACTION_addLocalColorsAreas = 15;
        static final int TRANSACTION_applyDimming = 17;
        static final int TRANSACTION_destroy = 11;
        static final int TRANSACTION_dispatchPointer = 7;
        static final int TRANSACTION_dispatchWallpaperCommand = 8;
        static final int TRANSACTION_mirrorSurfaceControl = 16;
        static final int TRANSACTION_onApplyWallpaper = 19;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 18;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWallpaperEngine asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWallpaperEngine)) {
                return (IWallpaperEngine) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 19:
                    return "onApplyWallpaper";
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDesiredSize(i3, i4);
                    return true;
                case 2:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplayPadding(rect);
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVisibility(z);
                    return true;
                case 4:
                    onScreenTurningOn();
                    return true;
                case 5:
                    onScreenTurnedOn();
                    return true;
                case 6:
                    boolean z2 = parcel.readBoolean();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInAmbientMode(z2, j);
                    return true;
                case 7:
                    MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchPointer(motionEvent);
                    return true;
                case 8:
                    String string = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchWallpaperCommand(string, i5, i6, i7, bundle);
                    return true;
                case 9:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setSurfaceAlpha(f);
                    return true;
                case 10:
                    requestWallpaperColors();
                    return true;
                case 11:
                    destroy();
                    return true;
                case 12:
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoomOut(f2);
                    return true;
                case 13:
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    resizePreview(rect2);
                    return true;
                case 14:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(RectF.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeLocalColorsAreas(arrayListCreateTypedArrayList);
                    return true;
                case 15:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(RectF.CREATOR);
                    parcel.enforceNoDataAvail();
                    addLocalColorsAreas(arrayListCreateTypedArrayList2);
                    return true;
                case 16:
                    SurfaceControl surfaceControlMirrorSurfaceControl = mirrorSurfaceControl();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(surfaceControlMirrorSurfaceControl, 1);
                    return true;
                case 17:
                    float f3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    applyDimming(f3);
                    return true;
                case 18:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWallpaperFlags(i8);
                    return true;
                case 19:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WallpaperDescription wallpaperDescriptionOnApplyWallpaper = onApplyWallpaper(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperDescriptionOnApplyWallpaper, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWallpaperEngine {
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

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setDesiredSize(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setDisplayPadding(Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setVisibility(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void onScreenTurningOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void onScreenTurnedOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setInAmbientMode(boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchPointer(MotionEvent motionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setSurfaceAlpha(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void requestWallpaperColors() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setZoomOut(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void resizePreview(Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void removeLocalColorsAreas(List<RectF> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void addLocalColorsAreas(List<RectF> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public SurfaceControl mirrorSurfaceControl() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SurfaceControl) parcelObtain2.readTypedObject(SurfaceControl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void applyDimming(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setWallpaperFlags(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public WallpaperDescription onApplyWallpaper(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WallpaperDescription) parcelObtain2.readTypedObject(WallpaperDescription.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
