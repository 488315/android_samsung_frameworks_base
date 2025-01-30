package android.service.selectiontoolbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.selectiontoolbar.ISelectionToolbarCallback;
import android.view.selectiontoolbar.ShowInfo;

/* loaded from: classes3.dex */
public interface ISelectionToolbarRenderService extends IInterface {
  public static final String DESCRIPTOR =
      "android.service.selectiontoolbar.ISelectionToolbarRenderService";

  void onConnected(IBinder iBinder) throws RemoteException;

  void onDismiss(int i, long j) throws RemoteException;

  void onHide(long j) throws RemoteException;

  void onShow(int i, ShowInfo showInfo, ISelectionToolbarCallback iSelectionToolbarCallback)
      throws RemoteException;

  public static class Default implements ISelectionToolbarRenderService {
    @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
    public void onConnected(IBinder callback) throws RemoteException {}

    @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
    public void onShow(int callingUid, ShowInfo showInfo, ISelectionToolbarCallback callback)
        throws RemoteException {}

    @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
    public void onHide(long widgetToken) throws RemoteException {}

    @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
    public void onDismiss(int callingUid, long widgetToken) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISelectionToolbarRenderService {
    static final int TRANSACTION_onConnected = 1;
    static final int TRANSACTION_onDismiss = 4;
    static final int TRANSACTION_onHide = 3;
    static final int TRANSACTION_onShow = 2;

    public Stub() {
      attachInterface(this, ISelectionToolbarRenderService.DESCRIPTOR);
    }

    public static ISelectionToolbarRenderService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISelectionToolbarRenderService.DESCRIPTOR);
      if (iin != null && (iin instanceof ISelectionToolbarRenderService)) {
        return (ISelectionToolbarRenderService) iin;
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
          return "onConnected";
        case 2:
          return "onShow";
        case 3:
          return "onHide";
        case 4:
          return "onDismiss";
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
        data.enforceInterface(ISelectionToolbarRenderService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISelectionToolbarRenderService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IBinder _arg0 = data.readStrongBinder();
              data.enforceNoDataAvail();
              onConnected(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              ShowInfo _arg1 = (ShowInfo) data.readTypedObject(ShowInfo.CREATOR);
              ISelectionToolbarCallback _arg2 =
                  ISelectionToolbarCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onShow(_arg02, _arg1, _arg2);
              return true;
            case 3:
              long _arg03 = data.readLong();
              data.enforceNoDataAvail();
              onHide(_arg03);
              return true;
            case 4:
              int _arg04 = data.readInt();
              long _arg12 = data.readLong();
              data.enforceNoDataAvail();
              onDismiss(_arg04, _arg12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISelectionToolbarRenderService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISelectionToolbarRenderService.DESCRIPTOR;
      }

      @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
      public void onConnected(IBinder callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISelectionToolbarRenderService.DESCRIPTOR);
          _data.writeStrongBinder(callback);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
      public void onShow(int callingUid, ShowInfo showInfo, ISelectionToolbarCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISelectionToolbarRenderService.DESCRIPTOR);
          _data.writeInt(callingUid);
          _data.writeTypedObject(showInfo, 0);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
      public void onHide(long widgetToken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISelectionToolbarRenderService.DESCRIPTOR);
          _data.writeLong(widgetToken);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.selectiontoolbar.ISelectionToolbarRenderService
      public void onDismiss(int callingUid, long widgetToken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISelectionToolbarRenderService.DESCRIPTOR);
          _data.writeInt(callingUid);
          _data.writeLong(widgetToken);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
