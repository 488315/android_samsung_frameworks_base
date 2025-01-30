package android.os;

/* loaded from: classes.dex */
public interface IVoldListener extends IInterface {

  public class Default implements IVoldListener {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.os.IVoldListener
    public void onDiskCreated(String str, int i) {}

    @Override // android.os.IVoldListener
    public void onDiskDestroyed(String str) {}

    @Override // android.os.IVoldListener
    public void onDiskMetadataChanged(String str, long j, String str2, String str3) {}

    @Override // android.os.IVoldListener
    public void onDiskScanned(String str) {}

    @Override // android.os.IVoldListener
    public void onEncryptionStateChanged(String str, String str2, String str3) {}

    @Override // android.os.IVoldListener
    public void onVolumeCreated(String str, int i, String str2, String str3, int i2) {}

    @Override // android.os.IVoldListener
    public void onVolumeDestroyed(String str) {}

    @Override // android.os.IVoldListener
    public void onVolumeInternalPathChanged(String str, String str2) {}

    @Override // android.os.IVoldListener
    public void onVolumeMetadataChanged(String str, String str2, String str3, String str4) {}

    @Override // android.os.IVoldListener
    public void onVolumePathChanged(String str, String str2) {}

    @Override // android.os.IVoldListener
    public void onVolumeStateChanged(String str, int i) {}

    @Override // android.os.IVoldListener
    public void sendVoldMessage(String str) {}
  }

  void onDiskCreated(String str, int i);

  void onDiskDestroyed(String str);

  void onDiskMetadataChanged(String str, long j, String str2, String str3);

  void onDiskScanned(String str);

  void onEncryptionStateChanged(String str, String str2, String str3);

  void onVolumeCreated(String str, int i, String str2, String str3, int i2);

  void onVolumeDestroyed(String str);

  void onVolumeInternalPathChanged(String str, String str2);

  void onVolumeMetadataChanged(String str, String str2, String str3, String str4);

  void onVolumePathChanged(String str, String str2);

  void onVolumeStateChanged(String str, int i);

  void sendVoldMessage(String str);

  public abstract class Stub extends Binder implements IVoldListener {
    public static final String DESCRIPTOR = "android.os.IVoldListener";
    public static final int TRANSACTION_onDiskCreated = 1;
    public static final int TRANSACTION_onDiskDestroyed = 4;
    public static final int TRANSACTION_onDiskMetadataChanged = 3;
    public static final int TRANSACTION_onDiskScanned = 2;
    public static final int TRANSACTION_onEncryptionStateChanged = 12;
    public static final int TRANSACTION_onVolumeCreated = 5;
    public static final int TRANSACTION_onVolumeDestroyed = 10;
    public static final int TRANSACTION_onVolumeInternalPathChanged = 9;
    public static final int TRANSACTION_onVolumeMetadataChanged = 7;
    public static final int TRANSACTION_onVolumePathChanged = 8;
    public static final int TRANSACTION_onVolumeStateChanged = 6;
    public static final int TRANSACTION_sendVoldMessage = 11;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IVoldListener asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IVoldListener)) {
        return (IVoldListener) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(DESCRIPTOR);
      }
      if (i == 1598968902) {
        parcel2.writeString(DESCRIPTOR);
        return true;
      }
      switch (i) {
        case 1:
          String readString = parcel.readString();
          int readInt = parcel.readInt();
          parcel.enforceNoDataAvail();
          onDiskCreated(readString, readInt);
          return true;
        case 2:
          String readString2 = parcel.readString();
          parcel.enforceNoDataAvail();
          onDiskScanned(readString2);
          return true;
        case 3:
          String readString3 = parcel.readString();
          long readLong = parcel.readLong();
          String readString4 = parcel.readString();
          String readString5 = parcel.readString();
          parcel.enforceNoDataAvail();
          onDiskMetadataChanged(readString3, readLong, readString4, readString5);
          return true;
        case 4:
          String readString6 = parcel.readString();
          parcel.enforceNoDataAvail();
          onDiskDestroyed(readString6);
          return true;
        case 5:
          String readString7 = parcel.readString();
          int readInt2 = parcel.readInt();
          String readString8 = parcel.readString();
          String readString9 = parcel.readString();
          int readInt3 = parcel.readInt();
          parcel.enforceNoDataAvail();
          onVolumeCreated(readString7, readInt2, readString8, readString9, readInt3);
          return true;
        case 6:
          String readString10 = parcel.readString();
          int readInt4 = parcel.readInt();
          parcel.enforceNoDataAvail();
          onVolumeStateChanged(readString10, readInt4);
          return true;
        case 7:
          String readString11 = parcel.readString();
          String readString12 = parcel.readString();
          String readString13 = parcel.readString();
          String readString14 = parcel.readString();
          parcel.enforceNoDataAvail();
          onVolumeMetadataChanged(readString11, readString12, readString13, readString14);
          return true;
        case 8:
          String readString15 = parcel.readString();
          String readString16 = parcel.readString();
          parcel.enforceNoDataAvail();
          onVolumePathChanged(readString15, readString16);
          return true;
        case 9:
          String readString17 = parcel.readString();
          String readString18 = parcel.readString();
          parcel.enforceNoDataAvail();
          onVolumeInternalPathChanged(readString17, readString18);
          return true;
        case 10:
          String readString19 = parcel.readString();
          parcel.enforceNoDataAvail();
          onVolumeDestroyed(readString19);
          return true;
        case 11:
          String readString20 = parcel.readString();
          parcel.enforceNoDataAvail();
          sendVoldMessage(readString20);
          return true;
        case 12:
          String readString21 = parcel.readString();
          String readString22 = parcel.readString();
          String readString23 = parcel.readString();
          parcel.enforceNoDataAvail();
          onEncryptionStateChanged(readString21, readString22, readString23);
          return true;
        default:
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IVoldListener {
      public IBinder mRemote;

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // android.os.IVoldListener
      public void onDiskCreated(String str, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          this.mRemote.transact(1, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onDiskScanned(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(2, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onDiskMetadataChanged(String str, long j, String str2, String str3) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeLong(j);
          obtain.writeString(str2);
          obtain.writeString(str3);
          this.mRemote.transact(3, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onDiskDestroyed(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(4, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onVolumeCreated(String str, int i, String str2, String str3, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeString(str2);
          obtain.writeString(str3);
          obtain.writeInt(i2);
          this.mRemote.transact(5, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onVolumeStateChanged(String str, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          this.mRemote.transact(6, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onVolumeMetadataChanged(String str, String str2, String str3, String str4) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeString(str3);
          obtain.writeString(str4);
          this.mRemote.transact(7, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onVolumePathChanged(String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(8, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onVolumeInternalPathChanged(String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(9, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onVolumeDestroyed(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(10, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void sendVoldMessage(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(11, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.os.IVoldListener
      public void onEncryptionStateChanged(String str, String str2, String str3) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeString(str3);
          this.mRemote.transact(12, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }
    }
  }
}
