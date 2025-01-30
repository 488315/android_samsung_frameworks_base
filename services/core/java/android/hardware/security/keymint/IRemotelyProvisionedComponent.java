package android.hardware.security.keymint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRemotelyProvisionedComponent extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$security$keymint$IRemotelyProvisionedComponent".replace('$', '.');

  public class Default implements IRemotelyProvisionedComponent {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
    public byte[] generateCertificateRequest(
        boolean z,
        MacedPublicKey[] macedPublicKeyArr,
        byte[] bArr,
        byte[] bArr2,
        DeviceInfo deviceInfo,
        ProtectedData protectedData) {
      return null;
    }

    @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
    public byte[] generateCertificateRequestV2(MacedPublicKey[] macedPublicKeyArr, byte[] bArr) {
      return null;
    }

    @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
    public RpcHardwareInfo getHardwareInfo() {
      return null;
    }
  }

  byte[] generateCertificateRequest(
      boolean z,
      MacedPublicKey[] macedPublicKeyArr,
      byte[] bArr,
      byte[] bArr2,
      DeviceInfo deviceInfo,
      ProtectedData protectedData);

  byte[] generateCertificateRequestV2(MacedPublicKey[] macedPublicKeyArr, byte[] bArr);

  byte[] generateEcdsaP256KeyPair(boolean z, MacedPublicKey macedPublicKey);

  RpcHardwareInfo getHardwareInfo();

  String getInterfaceHash();

  int getInterfaceVersion();

  public abstract class Stub extends Binder implements IRemotelyProvisionedComponent {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IRemotelyProvisionedComponent.DESCRIPTOR);
    }

    public static IRemotelyProvisionedComponent asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface(IRemotelyProvisionedComponent.DESCRIPTOR);
      if (queryLocalInterface != null
          && (queryLocalInterface instanceof IRemotelyProvisionedComponent)) {
        return (IRemotelyProvisionedComponent) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IRemotelyProvisionedComponent.DESCRIPTOR;
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
            RpcHardwareInfo hardwareInfo = getHardwareInfo();
            parcel2.writeNoException();
            parcel2.writeTypedObject(hardwareInfo, 1);
            break;
          } else if (i == 2) {
            boolean readBoolean = parcel.readBoolean();
            MacedPublicKey macedPublicKey = new MacedPublicKey();
            parcel.enforceNoDataAvail();
            byte[] generateEcdsaP256KeyPair = generateEcdsaP256KeyPair(readBoolean, macedPublicKey);
            parcel2.writeNoException();
            parcel2.writeByteArray(generateEcdsaP256KeyPair);
            parcel2.writeTypedObject(macedPublicKey, 1);
            break;
          } else if (i == 3) {
            boolean readBoolean2 = parcel.readBoolean();
            MacedPublicKey[] macedPublicKeyArr =
                (MacedPublicKey[]) parcel.createTypedArray(MacedPublicKey.CREATOR);
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            DeviceInfo deviceInfo = new DeviceInfo();
            ProtectedData protectedData = new ProtectedData();
            parcel.enforceNoDataAvail();
            byte[] generateCertificateRequest =
                generateCertificateRequest(
                    readBoolean2,
                    macedPublicKeyArr,
                    createByteArray,
                    createByteArray2,
                    deviceInfo,
                    protectedData);
            parcel2.writeNoException();
            parcel2.writeByteArray(generateCertificateRequest);
            parcel2.writeTypedObject(deviceInfo, 1);
            parcel2.writeTypedObject(protectedData, 1);
            break;
          } else if (i == 4) {
            MacedPublicKey[] macedPublicKeyArr2 =
                (MacedPublicKey[]) parcel.createTypedArray(MacedPublicKey.CREATOR);
            byte[] createByteArray3 = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            byte[] generateCertificateRequestV2 =
                generateCertificateRequestV2(macedPublicKeyArr2, createByteArray3);
            parcel2.writeNoException();
            parcel2.writeByteArray(generateCertificateRequestV2);
            break;
          } else {
            break;
          }
      }
      return true;
    }

    public class Proxy implements IRemotelyProvisionedComponent {
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

      @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
      public RpcHardwareInfo getHardwareInfo() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IRemotelyProvisionedComponent.DESCRIPTOR);
          if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
            throw new RemoteException("Method getHardwareInfo is unimplemented.");
          }
          obtain2.readException();
          return (RpcHardwareInfo) obtain2.readTypedObject(RpcHardwareInfo.CREATOR);
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
      public byte[] generateCertificateRequest(
          boolean z,
          MacedPublicKey[] macedPublicKeyArr,
          byte[] bArr,
          byte[] bArr2,
          DeviceInfo deviceInfo,
          ProtectedData protectedData) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IRemotelyProvisionedComponent.DESCRIPTOR);
          obtain.writeBoolean(z);
          obtain.writeTypedArray(macedPublicKeyArr, 0);
          obtain.writeByteArray(bArr);
          obtain.writeByteArray(bArr2);
          if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
            throw new RemoteException("Method generateCertificateRequest is unimplemented.");
          }
          obtain2.readException();
          byte[] createByteArray = obtain2.createByteArray();
          if (obtain2.readInt() != 0) {
            deviceInfo.readFromParcel(obtain2);
          }
          if (obtain2.readInt() != 0) {
            protectedData.readFromParcel(obtain2);
          }
          return createByteArray;
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
      public byte[] generateCertificateRequestV2(MacedPublicKey[] macedPublicKeyArr, byte[] bArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IRemotelyProvisionedComponent.DESCRIPTOR);
          obtain.writeTypedArray(macedPublicKeyArr, 0);
          obtain.writeByteArray(bArr);
          if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
            throw new RemoteException("Method generateCertificateRequestV2 is unimplemented.");
          }
          obtain2.readException();
          return obtain2.createByteArray();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }
    }
  }
}
