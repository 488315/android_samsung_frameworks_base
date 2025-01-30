package com.android.internal.view.inline;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IInlineContentProvider extends IInterface {
  public static final String DESCRIPTOR = "com.android.internal.view.inline.IInlineContentProvider";

  void onSurfacePackageReleased() throws RemoteException;

  void provideContent(int i, int i2, IInlineContentCallback iInlineContentCallback)
      throws RemoteException;

  void requestSurfacePackage() throws RemoteException;

  public static class Default implements IInlineContentProvider {
    @Override // com.android.internal.view.inline.IInlineContentProvider
    public void provideContent(int width, int height, IInlineContentCallback callback)
        throws RemoteException {}

    @Override // com.android.internal.view.inline.IInlineContentProvider
    public void requestSurfacePackage() throws RemoteException {}

    @Override // com.android.internal.view.inline.IInlineContentProvider
    public void onSurfacePackageReleased() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IInlineContentProvider {
    static final int TRANSACTION_onSurfacePackageReleased = 3;
    static final int TRANSACTION_provideContent = 1;
    static final int TRANSACTION_requestSurfacePackage = 2;

    public Stub() {
      attachInterface(this, IInlineContentProvider.DESCRIPTOR);
    }

    public static IInlineContentProvider asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IInlineContentProvider.DESCRIPTOR);
      if (iin != null && (iin instanceof IInlineContentProvider)) {
        return (IInlineContentProvider) iin;
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
          return "provideContent";
        case 2:
          return "requestSurfacePackage";
        case 3:
          return "onSurfacePackageReleased";
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
        data.enforceInterface(IInlineContentProvider.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IInlineContentProvider.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              IInlineContentCallback _arg2 =
                  IInlineContentCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              provideContent(_arg0, _arg1, _arg2);
              return true;
            case 2:
              requestSurfacePackage();
              return true;
            case 3:
              onSurfacePackageReleased();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IInlineContentProvider {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IInlineContentProvider.DESCRIPTOR;
      }

      @Override // com.android.internal.view.inline.IInlineContentProvider
      public void provideContent(int width, int height, IInlineContentCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInlineContentProvider.DESCRIPTOR);
          _data.writeInt(width);
          _data.writeInt(height);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.view.inline.IInlineContentProvider
      public void requestSurfacePackage() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInlineContentProvider.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.view.inline.IInlineContentProvider
      public void onSurfacePackageReleased() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInlineContentProvider.DESCRIPTOR);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
