package android.nfc;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.hardware.display.SemWifiDisplayParameter;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface INfcAdapter extends IInterface {
  void NetworkResetAtt() throws RemoteException;

  boolean SetWirelessChargeEnabled(boolean z) throws RemoteException;

  byte[] StartLedCover() throws RemoteException;

  boolean StopLedCover() throws RemoteException;

  byte[] TransceiveLedCover(byte[] bArr) throws RemoteException;

  void addNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler, int[] iArr) throws RemoteException;

  byte[] apcCommand(int i, byte[] bArr) throws RemoteException;

  void changeDiscoveryTech(IBinder iBinder, int i, int i2) throws RemoteException;

  void changeRoutingTable(IBinder iBinder, int i, String str, String str2, List<ComponentName> list)
      throws RemoteException;

  void copyUtilityFiles() throws RemoteException;

  boolean deviceSupportsNfcSecure() throws RemoteException;

  boolean deviceSupportsReaderOption() throws RemoteException;

  boolean disable(boolean z) throws RemoteException;

  void dispatch(Tag tag) throws RemoteException;

  boolean enable() throws RemoteException;

  void enableDisableSeTestMode(String str, boolean z) throws RemoteException;

  boolean enableReaderOption(boolean z) throws RemoteException;

  String getDefaultRoutingDestination() throws RemoteException;

  INfcAdapterExtras getNfcAdapterExtrasInterface(String str) throws RemoteException;

  NfcAntennaInfo getNfcAntennaInfo() throws RemoteException;

  INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException;

  INfcDta getNfcDtaInterface(String str) throws RemoteException;

  INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException;

  INfcTag getNfcTagInterface() throws RemoteException;

  String getPhoneNumber() throws RemoteException;

  int getSeSupportedTech() throws RemoteException;

  int getState() throws RemoteException;

  Map getTagIntentAppPreferenceForUser(int i) throws RemoteException;

  boolean ignore(int i, int i2, ITagRemovedCallback iTagRemovedCallback) throws RemoteException;

  boolean isControllerAlwaysOn() throws RemoteException;

  boolean isControllerAlwaysOnSupported() throws RemoteException;

  boolean isNFCAllowed(int i) throws RemoteException;

  boolean isNfcSecureEnabled() throws RemoteException;

  boolean isReaderOptionEnabled() throws RemoteException;

  boolean isTagIntentAppPreferenceSupported() throws RemoteException;

  void pausePolling(int i) throws RemoteException;

  boolean readerDisable() throws RemoteException;

  boolean readerEnable() throws RemoteException;

  void registerControllerAlwaysOnListener(
      INfcControllerAlwaysOnListener iNfcControllerAlwaysOnListener) throws RemoteException;

  void removeNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler) throws RemoteException;

  void resumePolling() throws RemoteException;

  void setAppCallback(IAppCallback iAppCallback) throws RemoteException;

  boolean setControllerAlwaysOn(boolean z) throws RemoteException;

  void setDefaultRoutingDestination(String str) throws RemoteException;

  void setForegroundDispatch(
      PendingIntent pendingIntent, IntentFilter[] intentFilterArr, TechListParcel techListParcel)
      throws RemoteException;

  boolean setNfcSecure(boolean z) throws RemoteException;

  void setReaderMode(IBinder iBinder, IAppCallback iAppCallback, int i, Bundle bundle)
      throws RemoteException;

  int setTagIntentAppPreferenceForUser(int i, String str, boolean z) throws RemoteException;

  byte[] startCoverAuth() throws RemoteException;

  boolean stopCoverAuth() throws RemoteException;

  void storeEnableHistory(String[] strArr) throws RemoteException;

  byte[] transceiveAuthData(byte[] bArr) throws RemoteException;

  void unregisterControllerAlwaysOnListener(
      INfcControllerAlwaysOnListener iNfcControllerAlwaysOnListener) throws RemoteException;

  void verifyNfcPermission() throws RemoteException;

  boolean writeFelicaLockState(String str, String str2, String str3) throws RemoteException;

  public static class Default implements INfcAdapter {
    @Override // android.nfc.INfcAdapter
    public INfcTag getNfcTagInterface() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public INfcAdapterExtras getNfcAdapterExtrasInterface(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public INfcDta getNfcDtaInterface(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public int getState() throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcAdapter
    public boolean disable(boolean saveState) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean enable() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public void pausePolling(int timeoutInMs) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void resumePolling() throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void storeEnableHistory(String[] log) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void setForegroundDispatch(
        PendingIntent intent, IntentFilter[] filters, TechListParcel techLists)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void setAppCallback(IAppCallback callback) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public boolean ignore(int nativeHandle, int debounceMs, ITagRemovedCallback callback)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public void dispatch(Tag tag) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void setReaderMode(IBinder b, IAppCallback callback, int flags, Bundle extras)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void changeDiscoveryTech(IBinder b, int pollTech, int listenTech)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void changeRoutingTable(
        IBinder b, int userHandle, String proto, String tech, List<ComponentName> services)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void addNfcUnlockHandler(INfcUnlockHandler unlockHandler, int[] techList)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void removeNfcUnlockHandler(INfcUnlockHandler unlockHandler) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void verifyNfcPermission() throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public boolean isNfcSecureEnabled() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean deviceSupportsNfcSecure() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean setNfcSecure(boolean enable) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public NfcAntennaInfo getNfcAntennaInfo() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public boolean setControllerAlwaysOn(boolean value) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean isControllerAlwaysOn() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean isControllerAlwaysOnSupported() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public void registerControllerAlwaysOnListener(INfcControllerAlwaysOnListener listener)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void unregisterControllerAlwaysOnListener(INfcControllerAlwaysOnListener listener)
        throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public boolean isTagIntentAppPreferenceSupported() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public Map getTagIntentAppPreferenceForUser(int userId) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public int setTagIntentAppPreferenceForUser(int userId, String pkg, boolean allow)
        throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcAdapter
    public void enableDisableSeTestMode(String SE, boolean enable) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public void setDefaultRoutingDestination(String SE) throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public String getDefaultRoutingDestination() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public boolean readerDisable() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean readerEnable() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public byte[] startCoverAuth() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public byte[] transceiveAuthData(byte[] data) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public boolean stopCoverAuth() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public byte[] TransceiveLedCover(byte[] data) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public byte[] StartLedCover() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public boolean StopLedCover() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean SetWirelessChargeEnabled(boolean enable) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public int getSeSupportedTech() throws RemoteException {
      return 0;
    }

    @Override // android.nfc.INfcAdapter
    public void NetworkResetAtt() throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public byte[] apcCommand(int cmd, byte[] param) throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public void copyUtilityFiles() throws RemoteException {}

    @Override // android.nfc.INfcAdapter
    public boolean isNFCAllowed(int userId) throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean writeFelicaLockState(String fileDirectory, String fileName, String value)
        throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public String getPhoneNumber() throws RemoteException {
      return null;
    }

    @Override // android.nfc.INfcAdapter
    public boolean isReaderOptionEnabled() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean deviceSupportsReaderOption() throws RemoteException {
      return false;
    }

    @Override // android.nfc.INfcAdapter
    public boolean enableReaderOption(boolean enable) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INfcAdapter {
    public static final String DESCRIPTOR = "android.nfc.INfcAdapter";
    static final int TRANSACTION_NetworkResetAtt = 47;
    static final int TRANSACTION_SetWirelessChargeEnabled = 45;
    static final int TRANSACTION_StartLedCover = 43;
    static final int TRANSACTION_StopLedCover = 44;
    static final int TRANSACTION_TransceiveLedCover = 42;
    static final int TRANSACTION_addNfcUnlockHandler = 19;
    static final int TRANSACTION_apcCommand = 48;
    static final int TRANSACTION_changeDiscoveryTech = 17;
    static final int TRANSACTION_changeRoutingTable = 18;
    static final int TRANSACTION_copyUtilityFiles = 49;
    static final int TRANSACTION_deviceSupportsNfcSecure = 23;
    static final int TRANSACTION_deviceSupportsReaderOption = 54;
    static final int TRANSACTION_disable = 7;
    static final int TRANSACTION_dispatch = 15;
    static final int TRANSACTION_enable = 8;
    static final int TRANSACTION_enableDisableSeTestMode = 34;
    static final int TRANSACTION_enableReaderOption = 55;
    static final int TRANSACTION_getDefaultRoutingDestination = 36;
    static final int TRANSACTION_getNfcAdapterExtrasInterface = 4;
    static final int TRANSACTION_getNfcAntennaInfo = 25;
    static final int TRANSACTION_getNfcCardEmulationInterface = 2;
    static final int TRANSACTION_getNfcDtaInterface = 5;
    static final int TRANSACTION_getNfcFCardEmulationInterface = 3;
    static final int TRANSACTION_getNfcTagInterface = 1;
    static final int TRANSACTION_getPhoneNumber = 52;
    static final int TRANSACTION_getSeSupportedTech = 46;
    static final int TRANSACTION_getState = 6;
    static final int TRANSACTION_getTagIntentAppPreferenceForUser = 32;
    static final int TRANSACTION_ignore = 14;
    static final int TRANSACTION_isControllerAlwaysOn = 27;
    static final int TRANSACTION_isControllerAlwaysOnSupported = 28;
    static final int TRANSACTION_isNFCAllowed = 50;
    static final int TRANSACTION_isNfcSecureEnabled = 22;
    static final int TRANSACTION_isReaderOptionEnabled = 53;
    static final int TRANSACTION_isTagIntentAppPreferenceSupported = 31;
    static final int TRANSACTION_pausePolling = 9;
    static final int TRANSACTION_readerDisable = 37;
    static final int TRANSACTION_readerEnable = 38;
    static final int TRANSACTION_registerControllerAlwaysOnListener = 29;
    static final int TRANSACTION_removeNfcUnlockHandler = 20;
    static final int TRANSACTION_resumePolling = 10;
    static final int TRANSACTION_setAppCallback = 13;
    static final int TRANSACTION_setControllerAlwaysOn = 26;
    static final int TRANSACTION_setDefaultRoutingDestination = 35;
    static final int TRANSACTION_setForegroundDispatch = 12;
    static final int TRANSACTION_setNfcSecure = 24;
    static final int TRANSACTION_setReaderMode = 16;
    static final int TRANSACTION_setTagIntentAppPreferenceForUser = 33;
    static final int TRANSACTION_startCoverAuth = 39;
    static final int TRANSACTION_stopCoverAuth = 41;
    static final int TRANSACTION_storeEnableHistory = 11;
    static final int TRANSACTION_transceiveAuthData = 40;
    static final int TRANSACTION_unregisterControllerAlwaysOnListener = 30;
    static final int TRANSACTION_verifyNfcPermission = 21;
    static final int TRANSACTION_writeFelicaLockState = 51;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static INfcAdapter asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INfcAdapter)) {
        return (INfcAdapter) iin;
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
          return "getNfcTagInterface";
        case 2:
          return "getNfcCardEmulationInterface";
        case 3:
          return "getNfcFCardEmulationInterface";
        case 4:
          return "getNfcAdapterExtrasInterface";
        case 5:
          return "getNfcDtaInterface";
        case 6:
          return "getState";
        case 7:
          return SemWifiDisplayParameter.VALUE_DISABLE;
        case 8:
          return "enable";
        case 9:
          return "pausePolling";
        case 10:
          return "resumePolling";
        case 11:
          return "storeEnableHistory";
        case 12:
          return "setForegroundDispatch";
        case 13:
          return "setAppCallback";
        case 14:
          return "ignore";
        case 15:
          return "dispatch";
        case 16:
          return "setReaderMode";
        case 17:
          return "changeDiscoveryTech";
        case 18:
          return "changeRoutingTable";
        case 19:
          return "addNfcUnlockHandler";
        case 20:
          return "removeNfcUnlockHandler";
        case 21:
          return "verifyNfcPermission";
        case 22:
          return "isNfcSecureEnabled";
        case 23:
          return "deviceSupportsNfcSecure";
        case 24:
          return "setNfcSecure";
        case 25:
          return "getNfcAntennaInfo";
        case 26:
          return "setControllerAlwaysOn";
        case 27:
          return "isControllerAlwaysOn";
        case 28:
          return "isControllerAlwaysOnSupported";
        case 29:
          return "registerControllerAlwaysOnListener";
        case 30:
          return "unregisterControllerAlwaysOnListener";
        case 31:
          return "isTagIntentAppPreferenceSupported";
        case 32:
          return "getTagIntentAppPreferenceForUser";
        case 33:
          return "setTagIntentAppPreferenceForUser";
        case 34:
          return "enableDisableSeTestMode";
        case 35:
          return "setDefaultRoutingDestination";
        case 36:
          return "getDefaultRoutingDestination";
        case 37:
          return "readerDisable";
        case 38:
          return "readerEnable";
        case 39:
          return "startCoverAuth";
        case 40:
          return "transceiveAuthData";
        case 41:
          return "stopCoverAuth";
        case 42:
          return "TransceiveLedCover";
        case 43:
          return "StartLedCover";
        case 44:
          return "StopLedCover";
        case 45:
          return "SetWirelessChargeEnabled";
        case 46:
          return "getSeSupportedTech";
        case 47:
          return "NetworkResetAtt";
        case 48:
          return "apcCommand";
        case 49:
          return "copyUtilityFiles";
        case 50:
          return "isNFCAllowed";
        case 51:
          return "writeFelicaLockState";
        case 52:
          return "getPhoneNumber";
        case 53:
          return "isReaderOptionEnabled";
        case 54:
          return "deviceSupportsReaderOption";
        case 55:
          return "enableReaderOption";
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
              INfcTag _result = getNfcTagInterface();
              reply.writeNoException();
              reply.writeStrongInterface(_result);
              return true;
            case 2:
              INfcCardEmulation _result2 = getNfcCardEmulationInterface();
              reply.writeNoException();
              reply.writeStrongInterface(_result2);
              return true;
            case 3:
              INfcFCardEmulation _result3 = getNfcFCardEmulationInterface();
              reply.writeNoException();
              reply.writeStrongInterface(_result3);
              return true;
            case 4:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              INfcAdapterExtras _result4 = getNfcAdapterExtrasInterface(_arg0);
              reply.writeNoException();
              reply.writeStrongInterface(_result4);
              return true;
            case 5:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              INfcDta _result5 = getNfcDtaInterface(_arg02);
              reply.writeNoException();
              reply.writeStrongInterface(_result5);
              return true;
            case 6:
              int _result6 = getState();
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 7:
              boolean _arg03 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result7 = disable(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 8:
              boolean _result8 = enable();
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 9:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              pausePolling(_arg04);
              reply.writeNoException();
              return true;
            case 10:
              resumePolling();
              reply.writeNoException();
              return true;
            case 11:
              String[] _arg05 = data.createStringArray();
              data.enforceNoDataAvail();
              storeEnableHistory(_arg05);
              reply.writeNoException();
              return true;
            case 12:
              PendingIntent _arg06 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              IntentFilter[] _arg1 = (IntentFilter[]) data.createTypedArray(IntentFilter.CREATOR);
              TechListParcel _arg2 = (TechListParcel) data.readTypedObject(TechListParcel.CREATOR);
              data.enforceNoDataAvail();
              setForegroundDispatch(_arg06, _arg1, _arg2);
              reply.writeNoException();
              return true;
            case 13:
              IAppCallback _arg07 = IAppCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setAppCallback(_arg07);
              reply.writeNoException();
              return true;
            case 14:
              int _arg08 = data.readInt();
              int _arg12 = data.readInt();
              ITagRemovedCallback _arg22 =
                  ITagRemovedCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result9 = ignore(_arg08, _arg12, _arg22);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 15:
              Tag _arg09 = (Tag) data.readTypedObject(Tag.CREATOR);
              data.enforceNoDataAvail();
              dispatch(_arg09);
              reply.writeNoException();
              return true;
            case 16:
              IBinder _arg010 = data.readStrongBinder();
              IAppCallback _arg13 = IAppCallback.Stub.asInterface(data.readStrongBinder());
              int _arg23 = data.readInt();
              Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              setReaderMode(_arg010, _arg13, _arg23, _arg3);
              reply.writeNoException();
              return true;
            case 17:
              IBinder _arg011 = data.readStrongBinder();
              int _arg14 = data.readInt();
              int _arg24 = data.readInt();
              data.enforceNoDataAvail();
              changeDiscoveryTech(_arg011, _arg14, _arg24);
              reply.writeNoException();
              return true;
            case 18:
              IBinder _arg012 = data.readStrongBinder();
              int _arg15 = data.readInt();
              String _arg25 = data.readString();
              String _arg32 = data.readString();
              List<ComponentName> _arg4 = data.createTypedArrayList(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              changeRoutingTable(_arg012, _arg15, _arg25, _arg32, _arg4);
              reply.writeNoException();
              return true;
            case 19:
              INfcUnlockHandler _arg013 =
                  INfcUnlockHandler.Stub.asInterface(data.readStrongBinder());
              int[] _arg16 = data.createIntArray();
              data.enforceNoDataAvail();
              addNfcUnlockHandler(_arg013, _arg16);
              reply.writeNoException();
              return true;
            case 20:
              INfcUnlockHandler _arg014 =
                  INfcUnlockHandler.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              removeNfcUnlockHandler(_arg014);
              reply.writeNoException();
              return true;
            case 21:
              verifyNfcPermission();
              reply.writeNoException();
              return true;
            case 22:
              boolean _result10 = isNfcSecureEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 23:
              boolean _result11 = deviceSupportsNfcSecure();
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 24:
              boolean _arg015 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result12 = setNfcSecure(_arg015);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 25:
              NfcAntennaInfo _result13 = getNfcAntennaInfo();
              reply.writeNoException();
              reply.writeTypedObject(_result13, 1);
              return true;
            case 26:
              boolean _arg016 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result14 = setControllerAlwaysOn(_arg016);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 27:
              boolean _result15 = isControllerAlwaysOn();
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 28:
              boolean _result16 = isControllerAlwaysOnSupported();
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 29:
              INfcControllerAlwaysOnListener _arg017 =
                  INfcControllerAlwaysOnListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerControllerAlwaysOnListener(_arg017);
              reply.writeNoException();
              return true;
            case 30:
              INfcControllerAlwaysOnListener _arg018 =
                  INfcControllerAlwaysOnListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterControllerAlwaysOnListener(_arg018);
              reply.writeNoException();
              return true;
            case 31:
              boolean _result17 = isTagIntentAppPreferenceSupported();
              reply.writeNoException();
              reply.writeBoolean(_result17);
              return true;
            case 32:
              int _arg019 = data.readInt();
              data.enforceNoDataAvail();
              Map _result18 = getTagIntentAppPreferenceForUser(_arg019);
              reply.writeNoException();
              reply.writeMap(_result18);
              return true;
            case 33:
              int _arg020 = data.readInt();
              String _arg17 = data.readString();
              boolean _arg26 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result19 = setTagIntentAppPreferenceForUser(_arg020, _arg17, _arg26);
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 34:
              String _arg021 = data.readString();
              boolean _arg18 = data.readBoolean();
              data.enforceNoDataAvail();
              enableDisableSeTestMode(_arg021, _arg18);
              reply.writeNoException();
              return true;
            case 35:
              String _arg022 = data.readString();
              data.enforceNoDataAvail();
              setDefaultRoutingDestination(_arg022);
              reply.writeNoException();
              return true;
            case 36:
              String _result20 = getDefaultRoutingDestination();
              reply.writeNoException();
              reply.writeString(_result20);
              return true;
            case 37:
              boolean _result21 = readerDisable();
              reply.writeNoException();
              reply.writeBoolean(_result21);
              return true;
            case 38:
              boolean _result22 = readerEnable();
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 39:
              byte[] _result23 = startCoverAuth();
              reply.writeNoException();
              reply.writeByteArray(_result23);
              return true;
            case 40:
              byte[] _arg023 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result24 = transceiveAuthData(_arg023);
              reply.writeNoException();
              reply.writeByteArray(_result24);
              return true;
            case 41:
              boolean _result25 = stopCoverAuth();
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 42:
              byte[] _arg024 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result26 = TransceiveLedCover(_arg024);
              reply.writeNoException();
              reply.writeByteArray(_result26);
              return true;
            case 43:
              byte[] _result27 = StartLedCover();
              reply.writeNoException();
              reply.writeByteArray(_result27);
              return true;
            case 44:
              boolean _result28 = StopLedCover();
              reply.writeNoException();
              reply.writeBoolean(_result28);
              return true;
            case 45:
              boolean _arg025 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result29 = SetWirelessChargeEnabled(_arg025);
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 46:
              int _result30 = getSeSupportedTech();
              reply.writeNoException();
              reply.writeInt(_result30);
              return true;
            case 47:
              NetworkResetAtt();
              reply.writeNoException();
              return true;
            case 48:
              int _arg026 = data.readInt();
              byte[] _arg19 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result31 = apcCommand(_arg026, _arg19);
              reply.writeNoException();
              reply.writeByteArray(_result31);
              return true;
            case 49:
              copyUtilityFiles();
              reply.writeNoException();
              return true;
            case 50:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result32 = isNFCAllowed(_arg027);
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            case 51:
              String _arg028 = data.readString();
              String _arg110 = data.readString();
              String _arg27 = data.readString();
              data.enforceNoDataAvail();
              boolean _result33 = writeFelicaLockState(_arg028, _arg110, _arg27);
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 52:
              String _result34 = getPhoneNumber();
              reply.writeNoException();
              reply.writeString(_result34);
              return true;
            case 53:
              boolean _result35 = isReaderOptionEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 54:
              boolean _result36 = deviceSupportsReaderOption();
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            case 55:
              boolean _arg029 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result37 = enableReaderOption(_arg029);
              reply.writeNoException();
              reply.writeBoolean(_result37);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INfcAdapter {
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

      @Override // android.nfc.INfcAdapter
      public INfcTag getNfcTagInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          INfcTag _result = INfcTag.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          INfcCardEmulation _result = INfcCardEmulation.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          INfcFCardEmulation _result =
              INfcFCardEmulation.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public INfcAdapterExtras getNfcAdapterExtrasInterface(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          INfcAdapterExtras _result = INfcAdapterExtras.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public INfcDta getNfcDtaInterface(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          INfcDta _result = INfcDta.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public int getState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean disable(boolean saveState) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(saveState);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean enable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void pausePolling(int timeoutInMs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(timeoutInMs);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void resumePolling() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void storeEnableHistory(String[] log) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStringArray(log);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void setForegroundDispatch(
          PendingIntent intent, IntentFilter[] filters, TechListParcel techLists)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeTypedArray(filters, 0);
          _data.writeTypedObject(techLists, 0);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void setAppCallback(IAppCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean ignore(int nativeHandle, int debounceMs, ITagRemovedCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(nativeHandle);
          _data.writeInt(debounceMs);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void dispatch(Tag tag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(tag, 0);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void setReaderMode(IBinder b, IAppCallback callback, int flags, Bundle extras)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(b);
          _data.writeStrongInterface(callback);
          _data.writeInt(flags);
          _data.writeTypedObject(extras, 0);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void changeDiscoveryTech(IBinder b, int pollTech, int listenTech)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(b);
          _data.writeInt(pollTech);
          _data.writeInt(listenTech);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void changeRoutingTable(
          IBinder b, int userHandle, String proto, String tech, List<ComponentName> services)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(b);
          _data.writeInt(userHandle);
          _data.writeString(proto);
          _data.writeString(tech);
          _data.writeTypedList(services, 0);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void addNfcUnlockHandler(INfcUnlockHandler unlockHandler, int[] techList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(unlockHandler);
          _data.writeIntArray(techList);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void removeNfcUnlockHandler(INfcUnlockHandler unlockHandler) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(unlockHandler);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void verifyNfcPermission() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean isNfcSecureEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean deviceSupportsNfcSecure() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean setNfcSecure(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public NfcAntennaInfo getNfcAntennaInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          NfcAntennaInfo _result = (NfcAntennaInfo) _reply.readTypedObject(NfcAntennaInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean setControllerAlwaysOn(boolean value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(value);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean isControllerAlwaysOn() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean isControllerAlwaysOnSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void registerControllerAlwaysOnListener(INfcControllerAlwaysOnListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void unregisterControllerAlwaysOnListener(INfcControllerAlwaysOnListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean isTagIntentAppPreferenceSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public Map getTagIntentAppPreferenceForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public int setTagIntentAppPreferenceForUser(int userId, String pkg, boolean allow)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(pkg);
          _data.writeBoolean(allow);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void enableDisableSeTestMode(String SE, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(SE);
          _data.writeBoolean(enable);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void setDefaultRoutingDestination(String SE) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(SE);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public String getDefaultRoutingDestination() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean readerDisable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean readerEnable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public byte[] startCoverAuth() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public byte[] transceiveAuthData(byte[] data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeByteArray(data);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean stopCoverAuth() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public byte[] TransceiveLedCover(byte[] data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeByteArray(data);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public byte[] StartLedCover() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean StopLedCover() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean SetWirelessChargeEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public int getSeSupportedTech() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void NetworkResetAtt() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public byte[] apcCommand(int cmd, byte[] param) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(cmd);
          _data.writeByteArray(param);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public void copyUtilityFiles() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean isNFCAllowed(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean writeFelicaLockState(String fileDirectory, String fileName, String value)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(fileDirectory);
          _data.writeString(fileName);
          _data.writeString(value);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public String getPhoneNumber() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean isReaderOptionEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean deviceSupportsReaderOption() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.nfc.INfcAdapter
      public boolean enableReaderOption(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 54;
    }
  }
}
