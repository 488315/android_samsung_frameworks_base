package android.hardware.ir;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IConsumerIr extends IInterface {
  public static final String DESCRIPTOR = "android$hardware$ir$IConsumerIr".replace('$', '.');

  public class Default implements IConsumerIr {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.ir.IConsumerIr
    public ConsumerIrFreqRange[] getCarrierFreqs() {
      return null;
    }

    @Override // android.hardware.ir.IConsumerIr
    public void transmit(int i, int[] iArr) {}
  }

  ConsumerIrFreqRange[] getCarrierFreqs();

  String getInterfaceHash();

  int getInterfaceVersion();

  void transmit(int i, int[] iArr);

  public abstract class Stub extends Binder implements IConsumerIr {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IConsumerIr.DESCRIPTOR);
    }

    public static IConsumerIr asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IConsumerIr.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IConsumerIr)) {
        return (IConsumerIr) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IConsumerIr.DESCRIPTOR;
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
            ConsumerIrFreqRange[] carrierFreqs = getCarrierFreqs();
            parcel2.writeNoException();
            parcel2.writeTypedArray(carrierFreqs, 1);
            break;
          } else if (i == 2) {
            int readInt = parcel.readInt();
            int[] createIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            transmit(readInt, createIntArray);
            parcel2.writeNoException();
            break;
          } else {
            break;
          }
      }
      return true;
    }

    public class Proxy implements IConsumerIr {
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

      @Override // android.hardware.ir.IConsumerIr
      public ConsumerIrFreqRange[] getCarrierFreqs() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IConsumerIr.DESCRIPTOR);
          if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
            throw new RemoteException("Method getCarrierFreqs is unimplemented.");
          }
          obtain2.readException();
          return (ConsumerIrFreqRange[]) obtain2.createTypedArray(ConsumerIrFreqRange.CREATOR);
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.ir.IConsumerIr
      public void transmit(int i, int[] iArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IConsumerIr.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeIntArray(iArr);
          if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
            throw new RemoteException("Method transmit is unimplemented.");
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
