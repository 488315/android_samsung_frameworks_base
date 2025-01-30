package android.hardware.oemlock;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IOemLock extends IInterface {
  public static final String DESCRIPTOR = "android$hardware$oemlock$IOemLock".replace('$', '.');

  public class Default implements IOemLock {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.oemlock.IOemLock
    public String getName() {
      return null;
    }

    @Override // android.hardware.oemlock.IOemLock
    public boolean isOemUnlockAllowedByCarrier() {
      return false;
    }

    @Override // android.hardware.oemlock.IOemLock
    public boolean isOemUnlockAllowedByDevice() {
      return false;
    }

    @Override // android.hardware.oemlock.IOemLock
    public int setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
      return 0;
    }

    @Override // android.hardware.oemlock.IOemLock
    public void setOemUnlockAllowedByDevice(boolean z) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  String getName();

  boolean isOemUnlockAllowedByCarrier();

  boolean isOemUnlockAllowedByDevice();

  int setOemUnlockAllowedByCarrier(boolean z, byte[] bArr);

  void setOemUnlockAllowedByDevice(boolean z);

  public abstract class Stub extends Binder implements IOemLock {
    public static String getDefaultTransactionName(int i) {
      if (i == 1) {
        return "getName";
      }
      if (i == 2) {
        return "isOemUnlockAllowedByCarrier";
      }
      if (i == 3) {
        return "isOemUnlockAllowedByDevice";
      }
      if (i == 4) {
        return "setOemUnlockAllowedByCarrier";
      }
      if (i == 5) {
        return "setOemUnlockAllowedByDevice";
      }
      switch (i) {
        case 16777214:
          return "getInterfaceHash";
        case 16777215:
          return "getInterfaceVersion";
        default:
          return null;
      }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public int getMaxTransactionId() {
      return 16777214;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IOemLock.DESCRIPTOR);
    }

    public static IOemLock asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IOemLock.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IOemLock)) {
        return (IOemLock) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    public String getTransactionName(int i) {
      return getDefaultTransactionName(i);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IOemLock.DESCRIPTOR;
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case 16777214:
          parcel2.writeNoException();
          parcel2.writeString(getInterfaceHash());
          break;
        case 16777215:
          parcel2.writeNoException();
          parcel2.writeInt(getInterfaceVersion());
          break;
        case 1598968902:
          parcel2.writeString(str);
          break;
        default:
          if (i == 1) {
            String name = getName();
            parcel2.writeNoException();
            parcel2.writeString(name);
            break;
          } else if (i == 2) {
            boolean isOemUnlockAllowedByCarrier = isOemUnlockAllowedByCarrier();
            parcel2.writeNoException();
            parcel2.writeBoolean(isOemUnlockAllowedByCarrier);
            break;
          } else if (i == 3) {
            boolean isOemUnlockAllowedByDevice = isOemUnlockAllowedByDevice();
            parcel2.writeNoException();
            parcel2.writeBoolean(isOemUnlockAllowedByDevice);
            break;
          } else if (i == 4) {
            boolean readBoolean = parcel.readBoolean();
            byte[] createByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            int oemUnlockAllowedByCarrier =
                setOemUnlockAllowedByCarrier(readBoolean, createByteArray);
            parcel2.writeNoException();
            parcel2.writeInt(oemUnlockAllowedByCarrier);
            break;
          } else if (i == 5) {
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setOemUnlockAllowedByDevice(readBoolean2);
            parcel2.writeNoException();
            break;
          } else {
            break;
          }
      }
      return true;
    }

    public class Proxy implements IOemLock {
      public IBinder mRemote;
      public int mCachedVersion = -1;
      public String mCachedHash = "-1";

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // android.hardware.oemlock.IOemLock
      public String getName() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IOemLock.DESCRIPTOR);
          if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
            throw new RemoteException("Method getName is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.oemlock.IOemLock
      public boolean isOemUnlockAllowedByCarrier() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IOemLock.DESCRIPTOR);
          if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
            throw new RemoteException("Method isOemUnlockAllowedByCarrier is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.oemlock.IOemLock
      public boolean isOemUnlockAllowedByDevice() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IOemLock.DESCRIPTOR);
          if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
            throw new RemoteException("Method isOemUnlockAllowedByDevice is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.oemlock.IOemLock
      public int setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IOemLock.DESCRIPTOR);
          obtain.writeBoolean(z);
          obtain.writeByteArray(bArr);
          if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
            throw new RemoteException("Method setOemUnlockAllowedByCarrier is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.oemlock.IOemLock
      public void setOemUnlockAllowedByDevice(boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IOemLock.DESCRIPTOR);
          obtain.writeBoolean(z);
          if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
            throw new RemoteException("Method setOemUnlockAllowedByDevice is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }
    }
  }
}
