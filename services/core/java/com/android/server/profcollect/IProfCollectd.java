package com.android.server.profcollect;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes3.dex */
public interface IProfCollectd extends IInterface {

  public class Default implements IProfCollectd {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // com.android.server.profcollect.IProfCollectd
    public String get_supported_provider() {
      return null;
    }

    @Override // com.android.server.profcollect.IProfCollectd
    public void process() {}

    @Override // com.android.server.profcollect.IProfCollectd
    public void registerProviderStatusCallback(IProviderStatusCallback iProviderStatusCallback) {}

    @Override // com.android.server.profcollect.IProfCollectd
    public String report(int i) {
      return null;
    }

    @Override // com.android.server.profcollect.IProfCollectd
    public void trace_once(String str) {}
  }

  String get_supported_provider();

  void process();

  void registerProviderStatusCallback(IProviderStatusCallback iProviderStatusCallback);

  String report(int i);

  void schedule();

  void terminate();

  void trace_once(String str);

  public abstract class Stub extends Binder implements IProfCollectd {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, "com.android.server.profcollect.IProfCollectd");
    }

    public static IProfCollectd asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface("com.android.server.profcollect.IProfCollectd");
      if (queryLocalInterface != null && (queryLocalInterface instanceof IProfCollectd)) {
        return (IProfCollectd) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface("com.android.server.profcollect.IProfCollectd");
      }
      if (i == 1598968902) {
        parcel2.writeString("com.android.server.profcollect.IProfCollectd");
        return true;
      }
      switch (i) {
        case 1:
          schedule();
          parcel2.writeNoException();
          return true;
        case 2:
          terminate();
          parcel2.writeNoException();
          return true;
        case 3:
          String readString = parcel.readString();
          parcel.enforceNoDataAvail();
          trace_once(readString);
          parcel2.writeNoException();
          return true;
        case 4:
          process();
          parcel2.writeNoException();
          return true;
        case 5:
          int readInt = parcel.readInt();
          parcel.enforceNoDataAvail();
          String report = report(readInt);
          parcel2.writeNoException();
          parcel2.writeString(report);
          return true;
        case 6:
          String str = get_supported_provider();
          parcel2.writeNoException();
          parcel2.writeString(str);
          return true;
        case 7:
          IProviderStatusCallback asInterface =
              IProviderStatusCallback.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          registerProviderStatusCallback(asInterface);
          parcel2.writeNoException();
          return true;
        default:
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IProfCollectd {
      public IBinder mRemote;

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // com.android.server.profcollect.IProfCollectd
      public void trace_once(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.android.server.profcollect.IProfCollectd");
          obtain.writeString(str);
          this.mRemote.transact(3, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // com.android.server.profcollect.IProfCollectd
      public void process() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.android.server.profcollect.IProfCollectd");
          this.mRemote.transact(4, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // com.android.server.profcollect.IProfCollectd
      public String report(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.android.server.profcollect.IProfCollectd");
          obtain.writeInt(i);
          this.mRemote.transact(5, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // com.android.server.profcollect.IProfCollectd
      public String get_supported_provider() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.android.server.profcollect.IProfCollectd");
          this.mRemote.transact(6, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // com.android.server.profcollect.IProfCollectd
      public void registerProviderStatusCallback(IProviderStatusCallback iProviderStatusCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.android.server.profcollect.IProfCollectd");
          obtain.writeStrongInterface(iProviderStatusCallback);
          this.mRemote.transact(7, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }
    }
  }
}
