package android.hardware.boot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBootControl extends IInterface {
  public static final String DESCRIPTOR = "android$hardware$boot$IBootControl".replace('$', '.');

  public class Default implements IBootControl {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.boot.IBootControl
    public int getActiveBootSlot() {
      return 0;
    }

    @Override // android.hardware.boot.IBootControl
    public int getCurrentSlot() {
      return 0;
    }
  }

  int getActiveBootSlot();

  int getCurrentSlot();

  String getInterfaceHash();

  int getInterfaceVersion();

  int getNumberSlots();

  int getSnapshotMergeStatus();

  String getSuffix(int i);

  boolean isSlotBootable(int i);

  boolean isSlotMarkedSuccessful(int i);

  void markBootSuccessful();

  void setActiveBootSlot(int i);

  void setSlotAsUnbootable(int i);

  void setSnapshotMergeStatus(int i);

  public abstract class Stub extends Binder implements IBootControl {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IBootControl.DESCRIPTOR);
    }

    public static IBootControl asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IBootControl.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IBootControl)) {
        return (IBootControl) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IBootControl.DESCRIPTOR;
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case 16777214:
          parcel2.writeNoException();
          parcel2.writeString(getInterfaceHash());
          return true;
        case 16777215:
          parcel2.writeNoException();
          parcel2.writeInt(getInterfaceVersion());
          return true;
        case 1598968902:
          parcel2.writeString(str);
          return true;
        default:
          switch (i) {
            case 1:
              int activeBootSlot = getActiveBootSlot();
              parcel2.writeNoException();
              parcel2.writeInt(activeBootSlot);
              return true;
            case 2:
              int currentSlot = getCurrentSlot();
              parcel2.writeNoException();
              parcel2.writeInt(currentSlot);
              return true;
            case 3:
              int numberSlots = getNumberSlots();
              parcel2.writeNoException();
              parcel2.writeInt(numberSlots);
              return true;
            case 4:
              int snapshotMergeStatus = getSnapshotMergeStatus();
              parcel2.writeNoException();
              parcel2.writeInt(snapshotMergeStatus);
              return true;
            case 5:
              int readInt = parcel.readInt();
              parcel.enforceNoDataAvail();
              String suffix = getSuffix(readInt);
              parcel2.writeNoException();
              parcel2.writeString(suffix);
              return true;
            case 6:
              int readInt2 = parcel.readInt();
              parcel.enforceNoDataAvail();
              boolean isSlotBootable = isSlotBootable(readInt2);
              parcel2.writeNoException();
              parcel2.writeBoolean(isSlotBootable);
              return true;
            case 7:
              int readInt3 = parcel.readInt();
              parcel.enforceNoDataAvail();
              boolean isSlotMarkedSuccessful = isSlotMarkedSuccessful(readInt3);
              parcel2.writeNoException();
              parcel2.writeBoolean(isSlotMarkedSuccessful);
              return true;
            case 8:
              markBootSuccessful();
              parcel2.writeNoException();
              return true;
            case 9:
              int readInt4 = parcel.readInt();
              parcel.enforceNoDataAvail();
              setActiveBootSlot(readInt4);
              parcel2.writeNoException();
              return true;
            case 10:
              int readInt5 = parcel.readInt();
              parcel.enforceNoDataAvail();
              setSlotAsUnbootable(readInt5);
              parcel2.writeNoException();
              return true;
            case 11:
              int readInt6 = parcel.readInt();
              parcel.enforceNoDataAvail();
              setSnapshotMergeStatus(readInt6);
              parcel2.writeNoException();
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    public class Proxy implements IBootControl {
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

      @Override // android.hardware.boot.IBootControl
      public int getActiveBootSlot() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IBootControl.DESCRIPTOR);
          if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
            throw new RemoteException("Method getActiveBootSlot is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.boot.IBootControl
      public int getCurrentSlot() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IBootControl.DESCRIPTOR);
          if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
            throw new RemoteException("Method getCurrentSlot is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }
    }
  }
}
