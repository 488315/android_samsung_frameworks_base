package android.nfc;

import android.content.ComponentName;
import android.nfc.cardemulation.AidGroup;
import android.nfc.cardemulation.ApduServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface INfcCardEmulation extends IInterface {
  boolean disableAutoRouting() throws RemoteException;

  boolean enableAutoRouting() throws RemoteException;

  AidGroup getAidGroupForService(int i, ComponentName componentName, String str)
      throws RemoteException;

  int getAidSizeForServiceInPercent(int i, ComponentName componentName) throws RemoteException;

  ApduServiceInfo getPaymentDefaultServiceInfo(int i) throws RemoteException;

  ComponentName getPaymentPriority(int i) throws RemoteException;

  ApduServiceInfo getPreferredPaymentService(int i) throws RemoteException;

  List<ApduServiceInfo> getServices(int i, String str) throws RemoteException;

  int getUsedAidTableSizeInPercent(int i, String str) throws RemoteException;

  void initializePaymentDefault(int i, int i2) throws RemoteException;

  boolean isDefaultPaymentRegistered() throws RemoteException;

  boolean isDefaultServiceForAid(int i, ComponentName componentName, String str)
      throws RemoteException;

  boolean isDefaultServiceForCategory(int i, ComponentName componentName, String str)
      throws RemoteException;

  boolean isRegisteredService(int i, ComponentName componentName, String str)
      throws RemoteException;

  boolean registerAidGroupForService(int i, ComponentName componentName, AidGroup aidGroup)
      throws RemoteException;

  int registerService(int i, ComponentName componentName, String str, int i2)
      throws RemoteException;

  boolean removeAidGroupForService(int i, ComponentName componentName, String str)
      throws RemoteException;

  boolean setDefaultForNextTap(int i, ComponentName componentName) throws RemoteException;

  boolean setDefaultServiceForCategory(int i, ComponentName componentName, String str)
      throws RemoteException;

  boolean setLockPassword(String str) throws RemoteException;

  boolean setOffHostForService(int i, ComponentName componentName, String str)
      throws RemoteException;

  boolean setOtherService(int i, ComponentName componentName) throws RemoteException;

  boolean setPreferredService(ComponentName componentName) throws RemoteException;

  boolean supportsAidPrefixRegistration() throws RemoteException;

  boolean supportsAutoRouting() throws RemoteException;

  int unregisterOtherService(int i, ComponentName componentName) throws RemoteException;

  boolean unsetOffHostForService(int i, ComponentName componentName) throws RemoteException;

  boolean unsetOtherService(int i, ComponentName componentName) throws RemoteException;

  boolean unsetPreferredService() throws RemoteException;

  public static class Default implements INfcCardEmulation {
    @Override // android.nfc.INfcCardEmulation
    public boolean isDefaultServiceForCategory(
        int userHandle, ComponentName service, String category) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean isDefaultServiceForAid(int userHandle, ComponentName service, String aid)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean setDefaultServiceForCategory(
        int userHandle, ComponentName service, String category) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean setDefaultForNextTap(int userHandle, ComponentName service)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean registerAidGroupForService(
        int userHandle, ComponentName service, AidGroup aidGroup) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean setOffHostForService(
        int userHandle, ComponentName service, String offHostSecureElement) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean unsetOffHostForService(int userHandle, ComponentName service)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public AidGroup getAidGroupForService(int userHandle, ComponentName service, String category)
        throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean removeAidGroupForService(int userHandle, ComponentName service, String category)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public List<ApduServiceInfo> getServices(int userHandle, String category)
        throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean setPreferredService(ComponentName service) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean unsetPreferredService() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean supportsAidPrefixRegistration() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public ApduServiceInfo getPreferredPaymentService(int userHandle) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean supportsAutoRouting() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean enableAutoRouting() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean disableAutoRouting() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean setOtherService(int userHandle, ComponentName app) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean unsetOtherService(int userHandle, ComponentName app) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public int registerService(int userHandle, ComponentName app, String category, int requester)
        throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcCardEmulation
    public int unregisterOtherService(int userHandle, ComponentName app) throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean isRegisteredService(int userHandle, ComponentName app, String category)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public int getUsedAidTableSizeInPercent(int userHandle, String category)
        throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcCardEmulation
    public int getAidSizeForServiceInPercent(int userHandle, ComponentName app)
        throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcCardEmulation
    public void initializePaymentDefault(int userHandle, int necessity) throws RemoteException {}

    @Override // android.nfc.INfcCardEmulation
    public ComponentName getPaymentPriority(int userHandle) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcCardEmulation
    public ApduServiceInfo getPaymentDefaultServiceInfo(int userHandle) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean setLockPassword(String data) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcCardEmulation
    public boolean isDefaultPaymentRegistered() throws RemoteException {
      return false;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INfcCardEmulation {
    public static final String DESCRIPTOR = "android.nfc.INfcCardEmulation";
    static final int TRANSACTION_disableAutoRouting = 17;
    static final int TRANSACTION_enableAutoRouting = 16;
    static final int TRANSACTION_getAidGroupForService = 8;
    static final int TRANSACTION_getAidSizeForServiceInPercent = 24;
    static final int TRANSACTION_getPaymentDefaultServiceInfo = 27;
    static final int TRANSACTION_getPaymentPriority = 26;
    static final int TRANSACTION_getPreferredPaymentService = 14;
    static final int TRANSACTION_getServices = 10;
    static final int TRANSACTION_getUsedAidTableSizeInPercent = 23;
    static final int TRANSACTION_initializePaymentDefault = 25;
    static final int TRANSACTION_isDefaultPaymentRegistered = 29;
    static final int TRANSACTION_isDefaultServiceForAid = 2;
    static final int TRANSACTION_isDefaultServiceForCategory = 1;
    static final int TRANSACTION_isRegisteredService = 22;
    static final int TRANSACTION_registerAidGroupForService = 5;
    static final int TRANSACTION_registerService = 20;
    static final int TRANSACTION_removeAidGroupForService = 9;
    static final int TRANSACTION_setDefaultForNextTap = 4;
    static final int TRANSACTION_setDefaultServiceForCategory = 3;
    static final int TRANSACTION_setLockPassword = 28;
    static final int TRANSACTION_setOffHostForService = 6;
    static final int TRANSACTION_setOtherService = 18;
    static final int TRANSACTION_setPreferredService = 11;
    static final int TRANSACTION_supportsAidPrefixRegistration = 13;
    static final int TRANSACTION_supportsAutoRouting = 15;
    static final int TRANSACTION_unregisterOtherService = 21;
    static final int TRANSACTION_unsetOffHostForService = 7;
    static final int TRANSACTION_unsetOtherService = 19;
    static final int TRANSACTION_unsetPreferredService = 12;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static INfcCardEmulation asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INfcCardEmulation)) {
        return (INfcCardEmulation) iin;
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
          return "isDefaultServiceForCategory";
        case 2:
          return "isDefaultServiceForAid";
        case 3:
          return "setDefaultServiceForCategory";
        case 4:
          return "setDefaultForNextTap";
        case 5:
          return "registerAidGroupForService";
        case 6:
          return "setOffHostForService";
        case 7:
          return "unsetOffHostForService";
        case 8:
          return "getAidGroupForService";
        case 9:
          return "removeAidGroupForService";
        case 10:
          return "getServices";
        case 11:
          return "setPreferredService";
        case 12:
          return "unsetPreferredService";
        case 13:
          return "supportsAidPrefixRegistration";
        case 14:
          return "getPreferredPaymentService";
        case 15:
          return "supportsAutoRouting";
        case 16:
          return "enableAutoRouting";
        case 17:
          return "disableAutoRouting";
        case 18:
          return "setOtherService";
        case 19:
          return "unsetOtherService";
        case 20:
          return "registerService";
        case 21:
          return "unregisterOtherService";
        case 22:
          return "isRegisteredService";
        case 23:
          return "getUsedAidTableSizeInPercent";
        case 24:
          return "getAidSizeForServiceInPercent";
        case 25:
          return "initializePaymentDefault";
        case 26:
          return "getPaymentPriority";
        case 27:
          return "getPaymentDefaultServiceInfo";
        case 28:
          return "setLockPassword";
        case 29:
          return "isDefaultPaymentRegistered";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              ComponentName _arg1 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              boolean _result = isDefaultServiceForCategory(_arg0, _arg1, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              ComponentName _arg12 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg22 = data.readString();
              data.enforceNoDataAvail();
              boolean _result2 = isDefaultServiceForAid(_arg02, _arg12, _arg22);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              ComponentName _arg13 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = setDefaultServiceForCategory(_arg03, _arg13, _arg23);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              int _arg04 = data.readInt();
              ComponentName _arg14 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result4 = setDefaultForNextTap(_arg04, _arg14);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              int _arg05 = data.readInt();
              ComponentName _arg15 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              AidGroup _arg24 = (AidGroup) data.readTypedObject(AidGroup.CREATOR);
              data.enforceNoDataAvail();
              boolean _result5 = registerAidGroupForService(_arg05, _arg15, _arg24);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 6:
              int _arg06 = data.readInt();
              ComponentName _arg16 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg25 = data.readString();
              data.enforceNoDataAvail();
              boolean _result6 = setOffHostForService(_arg06, _arg16, _arg25);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 7:
              int _arg07 = data.readInt();
              ComponentName _arg17 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result7 = unsetOffHostForService(_arg07, _arg17);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 8:
              int _arg08 = data.readInt();
              ComponentName _arg18 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg26 = data.readString();
              data.enforceNoDataAvail();
              AidGroup _result8 = getAidGroupForService(_arg08, _arg18, _arg26);
              reply.writeNoException();
              reply.writeTypedObject(_result8, 1);
              return true;
            case 9:
              int _arg09 = data.readInt();
              ComponentName _arg19 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg27 = data.readString();
              data.enforceNoDataAvail();
              boolean _result9 = removeAidGroupForService(_arg09, _arg19, _arg27);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 10:
              int _arg010 = data.readInt();
              String _arg110 = data.readString();
              data.enforceNoDataAvail();
              List<ApduServiceInfo> _result10 = getServices(_arg010, _arg110);
              reply.writeNoException();
              reply.writeTypedList(_result10, 1);
              return true;
            case 11:
              ComponentName _arg011 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result11 = setPreferredService(_arg011);
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 12:
              boolean _result12 = unsetPreferredService();
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 13:
              boolean _result13 = supportsAidPrefixRegistration();
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 14:
              int _arg012 = data.readInt();
              data.enforceNoDataAvail();
              ApduServiceInfo _result14 = getPreferredPaymentService(_arg012);
              reply.writeNoException();
              reply.writeTypedObject(_result14, 1);
              return true;
            case 15:
              boolean _result15 = supportsAutoRouting();
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 16:
              boolean _result16 = enableAutoRouting();
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 17:
              boolean _result17 = disableAutoRouting();
              reply.writeNoException();
              reply.writeBoolean(_result17);
              return true;
            case 18:
              int _arg013 = data.readInt();
              ComponentName _arg111 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result18 = setOtherService(_arg013, _arg111);
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 19:
              int _arg014 = data.readInt();
              ComponentName _arg112 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result19 = unsetOtherService(_arg014, _arg112);
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 20:
              int _arg015 = data.readInt();
              ComponentName _arg113 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg28 = data.readString();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              int _result20 = registerService(_arg015, _arg113, _arg28, _arg3);
              reply.writeNoException();
              reply.writeInt(_result20);
              return true;
            case 21:
              int _arg016 = data.readInt();
              ComponentName _arg114 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              int _result21 = unregisterOtherService(_arg016, _arg114);
              reply.writeNoException();
              reply.writeInt(_result21);
              return true;
            case 22:
              int _arg017 = data.readInt();
              ComponentName _arg115 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg29 = data.readString();
              data.enforceNoDataAvail();
              boolean _result22 = isRegisteredService(_arg017, _arg115, _arg29);
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 23:
              int _arg018 = data.readInt();
              String _arg116 = data.readString();
              data.enforceNoDataAvail();
              int _result23 = getUsedAidTableSizeInPercent(_arg018, _arg116);
              reply.writeNoException();
              reply.writeInt(_result23);
              return true;
            case 24:
              int _arg019 = data.readInt();
              ComponentName _arg117 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              int _result24 = getAidSizeForServiceInPercent(_arg019, _arg117);
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 25:
              int _arg020 = data.readInt();
              int _arg118 = data.readInt();
              data.enforceNoDataAvail();
              initializePaymentDefault(_arg020, _arg118);
              reply.writeNoException();
              return true;
            case 26:
              int _arg021 = data.readInt();
              data.enforceNoDataAvail();
              ComponentName _result25 = getPaymentPriority(_arg021);
              reply.writeNoException();
              reply.writeTypedObject(_result25, 1);
              return true;
            case 27:
              int _arg022 = data.readInt();
              data.enforceNoDataAvail();
              ApduServiceInfo _result26 = getPaymentDefaultServiceInfo(_arg022);
              reply.writeNoException();
              reply.writeTypedObject(_result26, 1);
              return true;
            case 28:
              String _arg023 = data.readString();
              data.enforceNoDataAvail();
              boolean _result27 = setLockPassword(_arg023);
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 29:
              boolean _result28 = isDefaultPaymentRegistered();
              reply.writeNoException();
              reply.writeBoolean(_result28);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INfcCardEmulation {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean isDefaultServiceForCategory(
          int userHandle, ComponentName service, String category) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeString(category);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean isDefaultServiceForAid(int userHandle, ComponentName service, String aid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeString(aid);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean setDefaultServiceForCategory(
          int userHandle, ComponentName service, String category) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeString(category);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean setDefaultForNextTap(int userHandle, ComponentName service)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean registerAidGroupForService(
          int userHandle, ComponentName service, AidGroup aidGroup) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeTypedObject(aidGroup, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean setOffHostForService(
          int userHandle, ComponentName service, String offHostSecureElement)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeString(offHostSecureElement);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean unsetOffHostForService(int userHandle, ComponentName service)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public AidGroup getAidGroupForService(int userHandle, ComponentName service, String category)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeString(category);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          AidGroup _result = (AidGroup) _reply.readTypedObject(AidGroup.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean removeAidGroupForService(
          int userHandle, ComponentName service, String category) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(service, 0);
          _data.writeString(category);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public List<ApduServiceInfo> getServices(int userHandle, String category)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeString(category);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          List<ApduServiceInfo> _result = _reply.createTypedArrayList(ApduServiceInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean setPreferredService(ComponentName service) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(service, 0);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean unsetPreferredService() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean supportsAidPrefixRegistration() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public ApduServiceInfo getPreferredPaymentService(int userHandle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          ApduServiceInfo _result =
              (ApduServiceInfo) _reply.readTypedObject(ApduServiceInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean supportsAutoRouting() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean enableAutoRouting() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean disableAutoRouting() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean setOtherService(int userHandle, ComponentName app) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(app, 0);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean unsetOtherService(int userHandle, ComponentName app) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(app, 0);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public int registerService(int userHandle, ComponentName app, String category, int requester)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(app, 0);
          _data.writeString(category);
          _data.writeInt(requester);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public int unregisterOtherService(int userHandle, ComponentName app) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(app, 0);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean isRegisteredService(int userHandle, ComponentName app, String category)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(app, 0);
          _data.writeString(category);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public int getUsedAidTableSizeInPercent(int userHandle, String category)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeString(category);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public int getAidSizeForServiceInPercent(int userHandle, ComponentName app)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeTypedObject(app, 0);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public void initializePaymentDefault(int userHandle, int necessity) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          _data.writeInt(necessity);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public ComponentName getPaymentPriority(int userHandle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public ApduServiceInfo getPaymentDefaultServiceInfo(int userHandle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userHandle);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          ApduServiceInfo _result =
              (ApduServiceInfo) _reply.readTypedObject(ApduServiceInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean setLockPassword(String data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(data);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcCardEmulation
      public boolean isDefaultPaymentRegistered() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 28;
    }
  }
}
