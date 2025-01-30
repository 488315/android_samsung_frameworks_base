package android.service.wallpaper;

import android.app.WallpaperInfo;
import android.graphics.Rect;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IWallpaperService extends IInterface {
  void attach(
      IWallpaperConnection iWallpaperConnection,
      IBinder iBinder,
      int i,
      boolean z,
      int i2,
      int i3,
      Rect rect,
      int i4,
      int i5)
      throws RemoteException;

  void attachWithExtras(
      IWallpaperConnection iWallpaperConnection,
      IBinder iBinder,
      int i,
      boolean z,
      int i2,
      int i3,
      Rect rect,
      int i4,
      int i5,
      WallpaperInfo wallpaperInfo,
      Bundle bundle)
      throws RemoteException;

  void detach(IBinder iBinder) throws RemoteException;

  void setCurrentUserId(int i) throws RemoteException;

  public static class Default implements IWallpaperService {
    @Override // android.service.wallpaper.IWallpaperService
    public void attach(
        IWallpaperConnection connection,
        IBinder windowToken,
        int windowType,
        boolean isPreview,
        int reqWidth,
        int reqHeight,
        Rect padding,
        int displayId,
        int which)
        throws RemoteException {}

    @Override // android.service.wallpaper.IWallpaperService
    public void detach(IBinder windowToken) throws RemoteException {}

    @Override // android.service.wallpaper.IWallpaperService
    public void attachWithExtras(
        IWallpaperConnection connection,
        IBinder windowToken,
        int windowType,
        boolean isPreview,
        int reqWidth,
        int reqHeight,
        Rect padding,
        int displayId,
        int which,
        WallpaperInfo info,
        Bundle extras)
        throws RemoteException {}

    @Override // android.service.wallpaper.IWallpaperService
    public void setCurrentUserId(int userId) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IWallpaperService {
    public static final String DESCRIPTOR = "android.service.wallpaper.IWallpaperService";
    static final int TRANSACTION_attach = 1;
    static final int TRANSACTION_attachWithExtras = 3;
    static final int TRANSACTION_detach = 2;
    static final int TRANSACTION_setCurrentUserId = 4;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IWallpaperService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IWallpaperService)) {
        return (IWallpaperService) iin;
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
          return "attach";
        case 2:
          return "detach";
        case 3:
          return "attachWithExtras";
        case 4:
          return "setCurrentUserId";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
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
              IWallpaperConnection _arg0 =
                  IWallpaperConnection.Stub.asInterface(data.readStrongBinder());
              IBinder _arg1 = data.readStrongBinder();
              int _arg2 = data.readInt();
              boolean _arg3 = data.readBoolean();
              int _arg4 = data.readInt();
              int _arg5 = data.readInt();
              Rect _arg6 = (Rect) data.readTypedObject(Rect.CREATOR);
              int _arg7 = data.readInt();
              int _arg8 = data.readInt();
              data.enforceNoDataAvail();
              attach(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
              return true;
            case 2:
              IBinder _arg02 = data.readStrongBinder();
              data.enforceNoDataAvail();
              detach(_arg02);
              return true;
            case 3:
              IWallpaperConnection _arg03 =
                  IWallpaperConnection.Stub.asInterface(data.readStrongBinder());
              IBinder _arg12 = data.readStrongBinder();
              int _arg22 = data.readInt();
              boolean _arg32 = data.readBoolean();
              int _arg42 = data.readInt();
              int _arg52 = data.readInt();
              Rect _arg62 = (Rect) data.readTypedObject(Rect.CREATOR);
              int _arg72 = data.readInt();
              int _arg82 = data.readInt();
              WallpaperInfo _arg9 = (WallpaperInfo) data.readTypedObject(WallpaperInfo.CREATOR);
              Bundle _arg10 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              attachWithExtras(
                  _arg03, _arg12, _arg22, _arg32, _arg42, _arg52, _arg62, _arg72, _arg82, _arg9,
                  _arg10);
              return true;
            case 4:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              setCurrentUserId(_arg04);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IWallpaperService {
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

      @Override // android.service.wallpaper.IWallpaperService
      public void attach(
          IWallpaperConnection connection,
          IBinder windowToken,
          int windowType,
          boolean isPreview,
          int reqWidth,
          int reqHeight,
          Rect padding,
          int displayId,
          int which)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(connection);
          _data.writeStrongBinder(windowToken);
          _data.writeInt(windowType);
          _data.writeBoolean(isPreview);
          _data.writeInt(reqWidth);
          _data.writeInt(reqHeight);
          _data.writeTypedObject(padding, 0);
          _data.writeInt(displayId);
          _data.writeInt(which);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.wallpaper.IWallpaperService
      public void detach(IBinder windowToken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(windowToken);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.wallpaper.IWallpaperService
      public void attachWithExtras(
          IWallpaperConnection connection,
          IBinder windowToken,
          int windowType,
          boolean isPreview,
          int reqWidth,
          int reqHeight,
          Rect padding,
          int displayId,
          int which,
          WallpaperInfo info,
          Bundle extras)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(connection);
          try {
            _data.writeStrongBinder(windowToken);
            try {
              _data.writeInt(windowType);
              try {
                _data.writeBoolean(isPreview);
              } catch (Throwable th) {
                th = th;
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _data.recycle();
            throw th;
          }
          try {
            _data.writeInt(reqWidth);
            try {
              _data.writeInt(reqHeight);
              try {
                _data.writeTypedObject(padding, 0);
                try {
                  _data.writeInt(displayId);
                } catch (Throwable th4) {
                  th = th4;
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th5) {
                th = th5;
                _data.recycle();
                throw th;
              }
            } catch (Throwable th6) {
              th = th6;
              _data.recycle();
              throw th;
            }
            try {
              _data.writeInt(which);
              try {
                _data.writeTypedObject(info, 0);
                try {
                  _data.writeTypedObject(extras, 0);
                  try {
                    this.mRemote.transact(3, _data, null, 1);
                    _data.recycle();
                  } catch (Throwable th7) {
                    th = th7;
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th8) {
                  th = th8;
                }
              } catch (Throwable th9) {
                th = th9;
                _data.recycle();
                throw th;
              }
            } catch (Throwable th10) {
              th = th10;
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
        }
      }

      @Override // android.service.wallpaper.IWallpaperService
      public void setCurrentUserId(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
