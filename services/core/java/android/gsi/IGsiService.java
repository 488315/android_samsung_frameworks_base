package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import java.util.List;

/* loaded from: classes.dex */
public interface IGsiService extends IInterface {

  public class Default implements IGsiService {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.gsi.IGsiService
    public boolean cancelGsiInstall() {
      return false;
    }

    @Override // android.gsi.IGsiService
    public int closeInstall() {
      return 0;
    }

    @Override // android.gsi.IGsiService
    public int closePartition() {
      return 0;
    }

    @Override // android.gsi.IGsiService
    public boolean commitGsiChunkFromAshmem(long j) {
      return false;
    }

    @Override // android.gsi.IGsiService
    public int createPartition(String str, long j, boolean z) {
      return 0;
    }

    @Override // android.gsi.IGsiService
    public boolean disableGsi() {
      return false;
    }

    @Override // android.gsi.IGsiService
    public void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback) {}

    @Override // android.gsi.IGsiService
    public String getActiveDsuSlot() {
      return null;
    }

    @Override // android.gsi.IGsiService
    public int getAvbPublicKey(AvbPublicKey avbPublicKey) {
      return 0;
    }

    @Override // android.gsi.IGsiService
    public GsiProgress getInstallProgress() {
      return null;
    }

    @Override // android.gsi.IGsiService
    public boolean isGsiEnabled() {
      return false;
    }

    @Override // android.gsi.IGsiService
    public int openInstall(String str) {
      return 0;
    }

    @Override // android.gsi.IGsiService
    public void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback) {}

    @Override // android.gsi.IGsiService
    public boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) {
      return false;
    }

    @Override // android.gsi.IGsiService
    public long suggestScratchSize() {
      return 0L;
    }
  }

  boolean cancelGsiInstall();

  int closeInstall();

  int closePartition();

  boolean commitGsiChunkFromAshmem(long j);

  boolean commitGsiChunkFromStream(ParcelFileDescriptor parcelFileDescriptor, long j);

  int createPartition(String str, long j, boolean z);

  boolean disableGsi();

  String dumpDeviceMapperDevices();

  int enableGsi(boolean z, String str);

  void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback);

  String getActiveDsuSlot();

  int getAvbPublicKey(AvbPublicKey avbPublicKey);

  GsiProgress getInstallProgress();

  List getInstalledDsuSlots();

  String getInstalledGsiImageDir();

  boolean isGsiEnabled();

  boolean isGsiInstallInProgress();

  boolean isGsiInstalled();

  boolean isGsiRunning();

  IImageService openImageService(String str);

  int openInstall(String str);

  boolean removeGsi();

  void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback);

  boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j);

  long suggestScratchSize();

  int zeroPartition(String str);

  public abstract class Stub extends Binder implements IGsiService {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, "android.gsi.IGsiService");
    }

    public static IGsiService asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface("android.gsi.IGsiService");
      if (queryLocalInterface != null && (queryLocalInterface instanceof IGsiService)) {
        return (IGsiService) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface("android.gsi.IGsiService");
      }
      if (i == 1598968902) {
        parcel2.writeString("android.gsi.IGsiService");
        return true;
      }
      switch (i) {
        case 1:
          ParcelFileDescriptor parcelFileDescriptor =
              (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
          long readLong = parcel.readLong();
          parcel.enforceNoDataAvail();
          boolean commitGsiChunkFromStream =
              commitGsiChunkFromStream(parcelFileDescriptor, readLong);
          parcel2.writeNoException();
          parcel2.writeBoolean(commitGsiChunkFromStream);
          return true;
        case 2:
          GsiProgress installProgress = getInstallProgress();
          parcel2.writeNoException();
          parcel2.writeTypedObject(installProgress, 1);
          return true;
        case 3:
          ParcelFileDescriptor parcelFileDescriptor2 =
              (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
          long readLong2 = parcel.readLong();
          parcel.enforceNoDataAvail();
          boolean gsiAshmem = setGsiAshmem(parcelFileDescriptor2, readLong2);
          parcel2.writeNoException();
          parcel2.writeBoolean(gsiAshmem);
          return true;
        case 4:
          long readLong3 = parcel.readLong();
          parcel.enforceNoDataAvail();
          boolean commitGsiChunkFromAshmem = commitGsiChunkFromAshmem(readLong3);
          parcel2.writeNoException();
          parcel2.writeBoolean(commitGsiChunkFromAshmem);
          return true;
        case 5:
          boolean readBoolean = parcel.readBoolean();
          String readString = parcel.readString();
          parcel.enforceNoDataAvail();
          int enableGsi = enableGsi(readBoolean, readString);
          parcel2.writeNoException();
          parcel2.writeInt(enableGsi);
          return true;
        case 6:
          boolean readBoolean2 = parcel.readBoolean();
          String readString2 = parcel.readString();
          IGsiServiceCallback asInterface =
              IGsiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          enableGsiAsync(readBoolean2, readString2, asInterface);
          return true;
        case 7:
          boolean isGsiEnabled = isGsiEnabled();
          parcel2.writeNoException();
          parcel2.writeBoolean(isGsiEnabled);
          return true;
        case 8:
          boolean cancelGsiInstall = cancelGsiInstall();
          parcel2.writeNoException();
          parcel2.writeBoolean(cancelGsiInstall);
          return true;
        case 9:
          boolean isGsiInstallInProgress = isGsiInstallInProgress();
          parcel2.writeNoException();
          parcel2.writeBoolean(isGsiInstallInProgress);
          return true;
        case 10:
          boolean removeGsi = removeGsi();
          parcel2.writeNoException();
          parcel2.writeBoolean(removeGsi);
          return true;
        case 11:
          IGsiServiceCallback asInterface2 =
              IGsiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          removeGsiAsync(asInterface2);
          return true;
        case 12:
          boolean disableGsi = disableGsi();
          parcel2.writeNoException();
          parcel2.writeBoolean(disableGsi);
          return true;
        case 13:
          boolean isGsiInstalled = isGsiInstalled();
          parcel2.writeNoException();
          parcel2.writeBoolean(isGsiInstalled);
          return true;
        case 14:
          boolean isGsiRunning = isGsiRunning();
          parcel2.writeNoException();
          parcel2.writeBoolean(isGsiRunning);
          return true;
        case 15:
          String activeDsuSlot = getActiveDsuSlot();
          parcel2.writeNoException();
          parcel2.writeString(activeDsuSlot);
          return true;
        case 16:
          String installedGsiImageDir = getInstalledGsiImageDir();
          parcel2.writeNoException();
          parcel2.writeString(installedGsiImageDir);
          return true;
        case 17:
          List<String> installedDsuSlots = getInstalledDsuSlots();
          parcel2.writeNoException();
          parcel2.writeStringList(installedDsuSlots);
          return true;
        case 18:
          String readString3 = parcel.readString();
          parcel.enforceNoDataAvail();
          int openInstall = openInstall(readString3);
          parcel2.writeNoException();
          parcel2.writeInt(openInstall);
          return true;
        case 19:
          int closeInstall = closeInstall();
          parcel2.writeNoException();
          parcel2.writeInt(closeInstall);
          return true;
        case 20:
          String readString4 = parcel.readString();
          long readLong4 = parcel.readLong();
          boolean readBoolean3 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          int createPartition = createPartition(readString4, readLong4, readBoolean3);
          parcel2.writeNoException();
          parcel2.writeInt(createPartition);
          return true;
        case 21:
          int closePartition = closePartition();
          parcel2.writeNoException();
          parcel2.writeInt(closePartition);
          return true;
        case 22:
          String readString5 = parcel.readString();
          parcel.enforceNoDataAvail();
          int zeroPartition = zeroPartition(readString5);
          parcel2.writeNoException();
          parcel2.writeInt(zeroPartition);
          return true;
        case 23:
          String readString6 = parcel.readString();
          parcel.enforceNoDataAvail();
          IImageService openImageService = openImageService(readString6);
          parcel2.writeNoException();
          parcel2.writeStrongInterface(openImageService);
          return true;
        case 24:
          String dumpDeviceMapperDevices = dumpDeviceMapperDevices();
          parcel2.writeNoException();
          parcel2.writeString(dumpDeviceMapperDevices);
          return true;
        case 25:
          AvbPublicKey avbPublicKey = new AvbPublicKey();
          parcel.enforceNoDataAvail();
          int avbPublicKey2 = getAvbPublicKey(avbPublicKey);
          parcel2.writeNoException();
          parcel2.writeInt(avbPublicKey2);
          parcel2.writeTypedObject(avbPublicKey, 1);
          return true;
        case 26:
          long suggestScratchSize = suggestScratchSize();
          parcel2.writeNoException();
          parcel2.writeLong(suggestScratchSize);
          return true;
        default:
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IGsiService {
      public IBinder mRemote;

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // android.gsi.IGsiService
      public GsiProgress getInstallProgress() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(2, obtain, obtain2, 0);
          obtain2.readException();
          return (GsiProgress) obtain2.readTypedObject(GsiProgress.CREATOR);
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          obtain.writeTypedObject(parcelFileDescriptor, 0);
          obtain.writeLong(j);
          this.mRemote.transact(3, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public boolean commitGsiChunkFromAshmem(long j) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          obtain.writeLong(j);
          this.mRemote.transact(4, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          obtain.writeBoolean(z);
          obtain.writeString(str);
          obtain.writeStrongInterface(iGsiServiceCallback);
          this.mRemote.transact(6, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public boolean isGsiEnabled() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(7, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public boolean cancelGsiInstall() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(8, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          obtain.writeStrongInterface(iGsiServiceCallback);
          this.mRemote.transact(11, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public boolean disableGsi() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(12, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public String getActiveDsuSlot() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(15, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public int openInstall(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          obtain.writeString(str);
          this.mRemote.transact(18, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public int closeInstall() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(19, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public int createPartition(String str, long j, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          obtain.writeString(str);
          obtain.writeLong(j);
          obtain.writeBoolean(z);
          this.mRemote.transact(20, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public int closePartition() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(21, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public int getAvbPublicKey(AvbPublicKey avbPublicKey) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(25, obtain, obtain2, 0);
          obtain2.readException();
          int readInt = obtain2.readInt();
          if (obtain2.readInt() != 0) {
            avbPublicKey.readFromParcel(obtain2);
          }
          return readInt;
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.gsi.IGsiService
      public long suggestScratchSize() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("android.gsi.IGsiService");
          this.mRemote.transact(26, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readLong();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }
    }
  }
}
