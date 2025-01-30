package com.android.internal.textservice;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISpellCheckerServiceCallback extends IInterface {
  void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) throws RemoteException;

  public static class Default implements ISpellCheckerServiceCallback {
    @Override // com.android.internal.textservice.ISpellCheckerServiceCallback
    public void onSessionCreated(ISpellCheckerSession newSession) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISpellCheckerServiceCallback {
    public static final String DESCRIPTOR =
        "com.android.internal.textservice.ISpellCheckerServiceCallback";
    static final int TRANSACTION_onSessionCreated = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ISpellCheckerServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISpellCheckerServiceCallback)) {
        return (ISpellCheckerServiceCallback) iin;
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
          return "onSessionCreated";
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
              ISpellCheckerSession _arg0 =
                  ISpellCheckerSession.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onSessionCreated(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISpellCheckerServiceCallback {
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

      @Override // com.android.internal.textservice.ISpellCheckerServiceCallback
      public void onSessionCreated(ISpellCheckerSession newSession) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(newSession);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
