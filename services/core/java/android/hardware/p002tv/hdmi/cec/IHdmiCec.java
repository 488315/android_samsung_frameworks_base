package android.hardware.p002tv.hdmi.cec;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IHdmiCec extends IInterface {
  public static final String DESCRIPTOR = "android$hardware$tv$hdmi$cec$IHdmiCec".replace('$', '.');

  public class Default implements IHdmiCec {
    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public byte addLogicalAddress(byte b) {
      return (byte) 0;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void clearLogicalAddress() {}

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void enableAudioReturnChannel(int i, boolean z) {}

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void enableCec(boolean z) {}

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void enableSystemCecControl(boolean z) {}

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void enableWakeupByOtp(boolean z) {}

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public int getCecVersion() {
      return 0;
    }

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public int getPhysicalAddress() {
      return 0;
    }

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public int getVendorId() {
      return 0;
    }

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public byte sendMessage(CecMessage cecMessage) {
      return (byte) 0;
    }

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void setCallback(IHdmiCecCallback iHdmiCecCallback) {}

    @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
    public void setLanguage(String str) {}
  }

  byte addLogicalAddress(byte b);

  void clearLogicalAddress();

  void enableAudioReturnChannel(int i, boolean z);

  void enableCec(boolean z);

  void enableSystemCecControl(boolean z);

  void enableWakeupByOtp(boolean z);

  int getCecVersion();

  String getInterfaceHash();

  int getInterfaceVersion();

  int getPhysicalAddress();

  int getVendorId();

  byte sendMessage(CecMessage cecMessage);

  void setCallback(IHdmiCecCallback iHdmiCecCallback);

  void setLanguage(String str);

  public abstract class Stub extends Binder implements IHdmiCec {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IHdmiCec.DESCRIPTOR);
    }

    public static IHdmiCec asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IHdmiCec.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiCec)) {
        return (IHdmiCec) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IHdmiCec.DESCRIPTOR;
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
              byte readByte = parcel.readByte();
              parcel.enforceNoDataAvail();
              byte addLogicalAddress = addLogicalAddress(readByte);
              parcel2.writeNoException();
              parcel2.writeByte(addLogicalAddress);
              return true;
            case 2:
              clearLogicalAddress();
              parcel2.writeNoException();
              return true;
            case 3:
              int readInt = parcel.readInt();
              boolean readBoolean = parcel.readBoolean();
              parcel.enforceNoDataAvail();
              enableAudioReturnChannel(readInt, readBoolean);
              parcel2.writeNoException();
              return true;
            case 4:
              int cecVersion = getCecVersion();
              parcel2.writeNoException();
              parcel2.writeInt(cecVersion);
              return true;
            case 5:
              int physicalAddress = getPhysicalAddress();
              parcel2.writeNoException();
              parcel2.writeInt(physicalAddress);
              return true;
            case 6:
              int vendorId = getVendorId();
              parcel2.writeNoException();
              parcel2.writeInt(vendorId);
              return true;
            case 7:
              CecMessage cecMessage = (CecMessage) parcel.readTypedObject(CecMessage.CREATOR);
              parcel.enforceNoDataAvail();
              byte sendMessage = sendMessage(cecMessage);
              parcel2.writeNoException();
              parcel2.writeByte(sendMessage);
              return true;
            case 8:
              IHdmiCecCallback asInterface =
                  IHdmiCecCallback.Stub.asInterface(parcel.readStrongBinder());
              parcel.enforceNoDataAvail();
              setCallback(asInterface);
              parcel2.writeNoException();
              return true;
            case 9:
              String readString = parcel.readString();
              parcel.enforceNoDataAvail();
              setLanguage(readString);
              parcel2.writeNoException();
              return true;
            case 10:
              boolean readBoolean2 = parcel.readBoolean();
              parcel.enforceNoDataAvail();
              enableWakeupByOtp(readBoolean2);
              parcel2.writeNoException();
              return true;
            case 11:
              boolean readBoolean3 = parcel.readBoolean();
              parcel.enforceNoDataAvail();
              enableCec(readBoolean3);
              parcel2.writeNoException();
              return true;
            case 12:
              boolean readBoolean4 = parcel.readBoolean();
              parcel.enforceNoDataAvail();
              enableSystemCecControl(readBoolean4);
              parcel2.writeNoException();
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    public class Proxy implements IHdmiCec {
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

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public byte addLogicalAddress(byte b) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeByte(b);
          if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
            throw new RemoteException("Method addLogicalAddress is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readByte();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void clearLogicalAddress() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
            throw new RemoteException("Method clearLogicalAddress is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void enableAudioReturnChannel(int i, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeBoolean(z);
          if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
            throw new RemoteException("Method enableAudioReturnChannel is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public int getCecVersion() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
            throw new RemoteException("Method getCecVersion is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public int getPhysicalAddress() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
            throw new RemoteException("Method getPhysicalAddress is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public int getVendorId() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
            throw new RemoteException("Method getVendorId is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public byte sendMessage(CecMessage cecMessage) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeTypedObject(cecMessage, 0);
          if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
            throw new RemoteException("Method sendMessage is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readByte();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void setCallback(IHdmiCecCallback iHdmiCecCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeStrongInterface(iHdmiCecCallback);
          if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
            throw new RemoteException("Method setCallback is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void setLanguage(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeString(str);
          if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
            throw new RemoteException("Method setLanguage is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void enableWakeupByOtp(boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeBoolean(z);
          if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
            throw new RemoteException("Method enableWakeupByOtp is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void enableCec(boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeBoolean(z);
          if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
            throw new RemoteException("Method enableCec is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.cec.IHdmiCec
      public void enableSystemCecControl(boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IHdmiCec.DESCRIPTOR);
          obtain.writeBoolean(z);
          if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
            throw new RemoteException("Method enableSystemCecControl is unimplemented.");
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
