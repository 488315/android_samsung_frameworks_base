package com.android.ims.internal;

import android.app.PendingIntent;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Message;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.RcsContactPresenceTuple;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.aidl.ISipTransport;
import com.android.internal.telephony.PublishDialog;

/* loaded from: classes4.dex */
public interface IImsService extends IInterface {
  void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException;

  void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException;

  void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException;

  void addRegistrationListener(int i, int i2, IImsRegistrationListener iImsRegistrationListener)
      throws RemoteException;

  void changeAudioPath(int i, int i2) throws RemoteException;

  void close(int i) throws RemoteException;

  ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException;

  IImsCallSession createCallSession(
      int i, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener)
      throws RemoteException;

  android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i) throws RemoteException;

  android.telephony.ims.aidl.IImsConfig getConfig(int i) throws RemoteException;

  int getE911CallCount(int i) throws RemoteException;

  IImsEcbm getEcbmInterface(int i) throws RemoteException;

  IImsMultiEndpoint getMultiEndpointInterface(int i) throws RemoteException;

  IImsCallSession getPendingCallSession(int i, String str) throws RemoteException;

  IImsRegistration getRegistration(int i) throws RemoteException;

  ISipTransport getSipTransport(int i) throws RemoteException;

  String getSmsFormat(int i) throws RemoteException;

  String getTrn(String str, String str2) throws RemoteException;

  IImsUt getUtInterface(int i) throws RemoteException;

  boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

  boolean isConnected(int i, int i2, int i3) throws RemoteException;

  boolean isOpened(int i) throws RemoteException;

  void notifyEpsFallbackResult(int i, int i2) throws RemoteException;

  void onMemoryAvailable(int i, int i2) throws RemoteException;

  void onSmsReady(int i) throws RemoteException;

  int open(
      int i, int i2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener)
      throws RemoteException;

  boolean queryCapabilityConfiguration(int i, int i2, int i3) throws RemoteException;

  void removeImsFeature(int i, int i2) throws RemoteException;

  void sendDtmfEvent(int i, String str) throws RemoteException;

  void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException;

  void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr)
      throws RemoteException;

  void setRegistrationListener(int i, IImsRegistrationListener iImsRegistrationListener)
      throws RemoteException;

  void setRetryCount(int i, int i2, int i3) throws RemoteException;

  void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener)
      throws RemoteException;

  void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException;

  void setSmsc(int i, String str) throws RemoteException;

  void setTtyMode(int i, int i2) throws RemoteException;

  void setUiTTYMode(int i, int i2, Message message) throws RemoteException;

  void setVideoCrtAudio(int i, boolean z) throws RemoteException;

  int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

  int stopLocalRingBackTone() throws RemoteException;

  void triggerAutoConfigurationForApp(int i) throws RemoteException;

  void turnOffIms(int i) throws RemoteException;

  void turnOnIms(int i) throws RemoteException;

  public static class Default implements IImsService {
    @Override // com.android.ims.internal.IImsService
    public int open(
        int phoneId,
        int serviceClass,
        PendingIntent incomingCallIntent,
        IImsRegistrationListener listener)
        throws RemoteException {
      return 0;
    }

    @Override // com.android.ims.internal.IImsService
    public void close(int serviceId) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public boolean isConnected(int serviceId, int serviceType, int callType)
        throws RemoteException {
      return false;
    }

    @Override // com.android.ims.internal.IImsService
    public boolean isOpened(int serviceId) throws RemoteException {
      return false;
    }

    @Override // com.android.ims.internal.IImsService
    public void setRegistrationListener(int serviceId, IImsRegistrationListener listener)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void addRegistrationListener(
        int phoneId, int serviceClass, IImsRegistrationListener listener) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public ImsCallProfile createCallProfile(int serviceId, int serviceType, int callType)
        throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public IImsCallSession createCallSession(
        int serviceId, ImsCallProfile profile, IImsCallSessionListener listener)
        throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public IImsCallSession getPendingCallSession(int serviceId, String callId)
        throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public IImsUt getUtInterface(int serviceId) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public android.telephony.ims.aidl.IImsConfig getConfig(int slotId) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public void turnOnIms(int phoneId) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void turnOffIms(int phoneId) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public IImsEcbm getEcbmInterface(int serviceId) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public void setUiTTYMode(int serviceId, int uiTtyMode, Message onComplete)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public IImsMultiEndpoint getMultiEndpointInterface(int serviceId) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public IImsRegistration getRegistration(int slotId) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public void changeAudioPath(int phoneId, int direction) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public int startLocalRingBackTone(int streamType, int volume, int toneType)
        throws RemoteException {
      return 0;
    }

    @Override // com.android.ims.internal.IImsService
    public int stopLocalRingBackTone() throws RemoteException {
      return 0;
    }

    @Override // com.android.ims.internal.IImsService
    public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
      return false;
    }

    @Override // com.android.ims.internal.IImsService
    public void triggerAutoConfigurationForApp(int phoneId) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void setTtyMode(int phoneId, int mode) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void notifyEpsFallbackResult(int phoneId, int result) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public int getE911CallCount(int phoneId) throws RemoteException {
      return 0;
    }

    @Override // com.android.ims.internal.IImsService
    public void setSecImsMmTelEventListener(int phoneId, ISecImsMmTelEventListener listener)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void sendSms(
        int phoneId,
        int token,
        int messageRef,
        String format,
        String smsc,
        boolean isRetry,
        byte[] pdu)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void setRetryCount(int phoneId, int token, int retryCount) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void onMemoryAvailable(int phoneId, int token) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void setSmsc(int phoneId, String smsc) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void acknowledgeSms(int phoneId, int token, int messageRef, int result)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void acknowledgeSmsReport(int phoneId, int token, int messageRef, int result)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void setSmsListener(int phoneId, IImsSmsListener listener) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public void onSmsReady(int phoneId) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public String getSmsFormat(int phoneId) throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public void acknowledgeSmsWithPdu(int phoneId, int token, int messageRef, byte[] data)
        throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public boolean queryCapabilityConfiguration(int capability, int radioTech, int slotId)
        throws RemoteException {
      return false;
    }

    @Override // com.android.ims.internal.IImsService
    public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int slotId)
        throws RemoteException {
      return null;
    }

    @Override // com.android.ims.internal.IImsService
    public void removeImsFeature(int slotId, int featureType) throws RemoteException {}

    @Override // com.android.ims.internal.IImsService
    public ISipTransport getSipTransport(int phoneId) throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IImsService {
    public static final String DESCRIPTOR = "com.android.ims.internal.IImsService";
    static final int TRANSACTION_acknowledgeSms = 35;
    static final int TRANSACTION_acknowledgeSmsReport = 36;
    static final int TRANSACTION_acknowledgeSmsWithPdu = 40;
    static final int TRANSACTION_addRegistrationListener = 6;
    static final int TRANSACTION_changeAudioPath = 18;
    static final int TRANSACTION_close = 2;
    static final int TRANSACTION_createCallProfile = 7;
    static final int TRANSACTION_createCallSession = 8;
    static final int TRANSACTION_createRcsFeature = 42;
    static final int TRANSACTION_getConfig = 11;
    static final int TRANSACTION_getE911CallCount = 29;
    static final int TRANSACTION_getEcbmInterface = 14;
    static final int TRANSACTION_getMultiEndpointInterface = 16;
    static final int TRANSACTION_getPendingCallSession = 9;
    static final int TRANSACTION_getRegistration = 17;
    static final int TRANSACTION_getSipTransport = 44;
    static final int TRANSACTION_getSmsFormat = 39;
    static final int TRANSACTION_getTrn = 23;
    static final int TRANSACTION_getUtInterface = 10;
    static final int TRANSACTION_isCmcEmergencyCallSupported = 25;
    static final int TRANSACTION_isConnected = 3;
    static final int TRANSACTION_isOpened = 4;
    static final int TRANSACTION_notifyEpsFallbackResult = 28;
    static final int TRANSACTION_onMemoryAvailable = 33;
    static final int TRANSACTION_onSmsReady = 38;
    static final int TRANSACTION_open = 1;
    static final int TRANSACTION_queryCapabilityConfiguration = 41;
    static final int TRANSACTION_removeImsFeature = 43;
    static final int TRANSACTION_sendDtmfEvent = 22;
    static final int TRANSACTION_sendPublishDialog = 24;
    static final int TRANSACTION_sendSms = 31;
    static final int TRANSACTION_setRegistrationListener = 5;
    static final int TRANSACTION_setRetryCount = 32;
    static final int TRANSACTION_setSecImsMmTelEventListener = 30;
    static final int TRANSACTION_setSmsListener = 37;
    static final int TRANSACTION_setSmsc = 34;
    static final int TRANSACTION_setTtyMode = 27;
    static final int TRANSACTION_setUiTTYMode = 15;
    static final int TRANSACTION_setVideoCrtAudio = 21;
    static final int TRANSACTION_startLocalRingBackTone = 19;
    static final int TRANSACTION_stopLocalRingBackTone = 20;
    static final int TRANSACTION_triggerAutoConfigurationForApp = 26;
    static final int TRANSACTION_turnOffIms = 13;
    static final int TRANSACTION_turnOnIms = 12;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IImsService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IImsService)) {
        return (IImsService) iin;
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
          return RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN;
        case 2:
          return "close";
        case 3:
          return "isConnected";
        case 4:
          return "isOpened";
        case 5:
          return "setRegistrationListener";
        case 6:
          return "addRegistrationListener";
        case 7:
          return "createCallProfile";
        case 8:
          return "createCallSession";
        case 9:
          return "getPendingCallSession";
        case 10:
          return "getUtInterface";
        case 11:
          return "getConfig";
        case 12:
          return "turnOnIms";
        case 13:
          return "turnOffIms";
        case 14:
          return "getEcbmInterface";
        case 15:
          return "setUiTTYMode";
        case 16:
          return "getMultiEndpointInterface";
        case 17:
          return "getRegistration";
        case 18:
          return "changeAudioPath";
        case 19:
          return "startLocalRingBackTone";
        case 20:
          return "stopLocalRingBackTone";
        case 21:
          return "setVideoCrtAudio";
        case 22:
          return "sendDtmfEvent";
        case 23:
          return "getTrn";
        case 24:
          return "sendPublishDialog";
        case 25:
          return "isCmcEmergencyCallSupported";
        case 26:
          return "triggerAutoConfigurationForApp";
        case 27:
          return "setTtyMode";
        case 28:
          return "notifyEpsFallbackResult";
        case 29:
          return "getE911CallCount";
        case 30:
          return "setSecImsMmTelEventListener";
        case 31:
          return "sendSms";
        case 32:
          return "setRetryCount";
        case 33:
          return "onMemoryAvailable";
        case 34:
          return "setSmsc";
        case 35:
          return "acknowledgeSms";
        case 36:
          return "acknowledgeSmsReport";
        case 37:
          return "setSmsListener";
        case 38:
          return "onSmsReady";
        case 39:
          return "getSmsFormat";
        case 40:
          return "acknowledgeSmsWithPdu";
        case 41:
          return "queryCapabilityConfiguration";
        case 42:
          return "createRcsFeature";
        case 43:
          return "removeImsFeature";
        case 44:
          return "getSipTransport";
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
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              PendingIntent _arg2 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              IImsRegistrationListener _arg3 =
                  IImsRegistrationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result = open(_arg0, _arg1, _arg2, _arg3);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              close(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              int _arg12 = data.readInt();
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = isConnected(_arg03, _arg12, _arg22);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 4:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = isOpened(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 5:
              int _arg05 = data.readInt();
              IImsRegistrationListener _arg13 =
                  IImsRegistrationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setRegistrationListener(_arg05, _arg13);
              reply.writeNoException();
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg14 = data.readInt();
              IImsRegistrationListener _arg23 =
                  IImsRegistrationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              addRegistrationListener(_arg06, _arg14, _arg23);
              reply.writeNoException();
              return true;
            case 7:
              int _arg07 = data.readInt();
              int _arg15 = data.readInt();
              int _arg24 = data.readInt();
              data.enforceNoDataAvail();
              ImsCallProfile _result4 = createCallProfile(_arg07, _arg15, _arg24);
              reply.writeNoException();
              reply.writeTypedObject(_result4, 1);
              return true;
            case 8:
              int _arg08 = data.readInt();
              ImsCallProfile _arg16 = (ImsCallProfile) data.readTypedObject(ImsCallProfile.CREATOR);
              IImsCallSessionListener _arg25 =
                  IImsCallSessionListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              IImsCallSession _result5 = createCallSession(_arg08, _arg16, _arg25);
              reply.writeNoException();
              reply.writeStrongInterface(_result5);
              return true;
            case 9:
              int _arg09 = data.readInt();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              IImsCallSession _result6 = getPendingCallSession(_arg09, _arg17);
              reply.writeNoException();
              reply.writeStrongInterface(_result6);
              return true;
            case 10:
              int _arg010 = data.readInt();
              data.enforceNoDataAvail();
              IImsUt _result7 = getUtInterface(_arg010);
              reply.writeNoException();
              reply.writeStrongInterface(_result7);
              return true;
            case 11:
              int _arg011 = data.readInt();
              data.enforceNoDataAvail();
              android.telephony.ims.aidl.IImsConfig _result8 = getConfig(_arg011);
              reply.writeNoException();
              reply.writeStrongInterface(_result8);
              return true;
            case 12:
              int _arg012 = data.readInt();
              data.enforceNoDataAvail();
              turnOnIms(_arg012);
              reply.writeNoException();
              return true;
            case 13:
              int _arg013 = data.readInt();
              data.enforceNoDataAvail();
              turnOffIms(_arg013);
              reply.writeNoException();
              return true;
            case 14:
              int _arg014 = data.readInt();
              data.enforceNoDataAvail();
              IImsEcbm _result9 = getEcbmInterface(_arg014);
              reply.writeNoException();
              reply.writeStrongInterface(_result9);
              return true;
            case 15:
              int _arg015 = data.readInt();
              int _arg18 = data.readInt();
              Message _arg26 = (Message) data.readTypedObject(Message.CREATOR);
              data.enforceNoDataAvail();
              setUiTTYMode(_arg015, _arg18, _arg26);
              reply.writeNoException();
              return true;
            case 16:
              int _arg016 = data.readInt();
              data.enforceNoDataAvail();
              IImsMultiEndpoint _result10 = getMultiEndpointInterface(_arg016);
              reply.writeNoException();
              reply.writeStrongInterface(_result10);
              return true;
            case 17:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              IImsRegistration _result11 = getRegistration(_arg017);
              reply.writeNoException();
              reply.writeStrongInterface(_result11);
              return true;
            case 18:
              int _arg018 = data.readInt();
              int _arg19 = data.readInt();
              data.enforceNoDataAvail();
              changeAudioPath(_arg018, _arg19);
              reply.writeNoException();
              return true;
            case 19:
              int _arg019 = data.readInt();
              int _arg110 = data.readInt();
              int _arg27 = data.readInt();
              data.enforceNoDataAvail();
              int _result12 = startLocalRingBackTone(_arg019, _arg110, _arg27);
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 20:
              int _result13 = stopLocalRingBackTone();
              reply.writeNoException();
              reply.writeInt(_result13);
              return true;
            case 21:
              int _arg020 = data.readInt();
              boolean _arg111 = data.readBoolean();
              data.enforceNoDataAvail();
              setVideoCrtAudio(_arg020, _arg111);
              reply.writeNoException();
              return true;
            case 22:
              int _arg021 = data.readInt();
              String _arg112 = data.readString();
              data.enforceNoDataAvail();
              sendDtmfEvent(_arg021, _arg112);
              reply.writeNoException();
              return true;
            case 23:
              String _arg022 = data.readString();
              String _arg113 = data.readString();
              data.enforceNoDataAvail();
              String _result14 = getTrn(_arg022, _arg113);
              reply.writeNoException();
              reply.writeString(_result14);
              return true;
            case 24:
              int _arg023 = data.readInt();
              PublishDialog _arg114 = (PublishDialog) data.readTypedObject(PublishDialog.CREATOR);
              data.enforceNoDataAvail();
              sendPublishDialog(_arg023, _arg114);
              reply.writeNoException();
              return true;
            case 25:
              int _arg024 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result15 = isCmcEmergencyCallSupported(_arg024);
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 26:
              int _arg025 = data.readInt();
              data.enforceNoDataAvail();
              triggerAutoConfigurationForApp(_arg025);
              reply.writeNoException();
              return true;
            case 27:
              int _arg026 = data.readInt();
              int _arg115 = data.readInt();
              data.enforceNoDataAvail();
              setTtyMode(_arg026, _arg115);
              reply.writeNoException();
              return true;
            case 28:
              int _arg027 = data.readInt();
              int _arg116 = data.readInt();
              data.enforceNoDataAvail();
              notifyEpsFallbackResult(_arg027, _arg116);
              reply.writeNoException();
              return true;
            case 29:
              int _arg028 = data.readInt();
              data.enforceNoDataAvail();
              int _result16 = getE911CallCount(_arg028);
              reply.writeNoException();
              reply.writeInt(_result16);
              return true;
            case 30:
              int _arg029 = data.readInt();
              ISecImsMmTelEventListener _arg117 =
                  ISecImsMmTelEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSecImsMmTelEventListener(_arg029, _arg117);
              reply.writeNoException();
              return true;
            case 31:
              int _arg030 = data.readInt();
              int _arg118 = data.readInt();
              int _arg28 = data.readInt();
              String _arg32 = data.readString();
              String _arg4 = data.readString();
              boolean _arg5 = data.readBoolean();
              byte[] _arg6 = data.createByteArray();
              data.enforceNoDataAvail();
              sendSms(_arg030, _arg118, _arg28, _arg32, _arg4, _arg5, _arg6);
              reply.writeNoException();
              return true;
            case 32:
              int _arg031 = data.readInt();
              int _arg119 = data.readInt();
              int _arg29 = data.readInt();
              data.enforceNoDataAvail();
              setRetryCount(_arg031, _arg119, _arg29);
              reply.writeNoException();
              return true;
            case 33:
              int _arg032 = data.readInt();
              int _arg120 = data.readInt();
              data.enforceNoDataAvail();
              onMemoryAvailable(_arg032, _arg120);
              reply.writeNoException();
              return true;
            case 34:
              int _arg033 = data.readInt();
              String _arg121 = data.readString();
              data.enforceNoDataAvail();
              setSmsc(_arg033, _arg121);
              reply.writeNoException();
              return true;
            case 35:
              int _arg034 = data.readInt();
              int _arg122 = data.readInt();
              int _arg210 = data.readInt();
              int _arg33 = data.readInt();
              data.enforceNoDataAvail();
              acknowledgeSms(_arg034, _arg122, _arg210, _arg33);
              reply.writeNoException();
              return true;
            case 36:
              int _arg035 = data.readInt();
              int _arg123 = data.readInt();
              int _arg211 = data.readInt();
              int _arg34 = data.readInt();
              data.enforceNoDataAvail();
              acknowledgeSmsReport(_arg035, _arg123, _arg211, _arg34);
              reply.writeNoException();
              return true;
            case 37:
              int _arg036 = data.readInt();
              IImsSmsListener _arg124 = IImsSmsListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSmsListener(_arg036, _arg124);
              reply.writeNoException();
              return true;
            case 38:
              int _arg037 = data.readInt();
              data.enforceNoDataAvail();
              onSmsReady(_arg037);
              reply.writeNoException();
              return true;
            case 39:
              int _arg038 = data.readInt();
              data.enforceNoDataAvail();
              String _result17 = getSmsFormat(_arg038);
              reply.writeNoException();
              reply.writeString(_result17);
              return true;
            case 40:
              int _arg039 = data.readInt();
              int _arg125 = data.readInt();
              int _arg212 = data.readInt();
              byte[] _arg35 = data.createByteArray();
              data.enforceNoDataAvail();
              acknowledgeSmsWithPdu(_arg039, _arg125, _arg212, _arg35);
              reply.writeNoException();
              return true;
            case 41:
              int _arg040 = data.readInt();
              int _arg126 = data.readInt();
              int _arg213 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result18 = queryCapabilityConfiguration(_arg040, _arg126, _arg213);
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 42:
              int _arg041 = data.readInt();
              data.enforceNoDataAvail();
              android.telephony.ims.aidl.IImsRcsFeature _result19 = createRcsFeature(_arg041);
              reply.writeNoException();
              reply.writeStrongInterface(_result19);
              return true;
            case 43:
              int _arg042 = data.readInt();
              int _arg127 = data.readInt();
              data.enforceNoDataAvail();
              removeImsFeature(_arg042, _arg127);
              reply.writeNoException();
              return true;
            case 44:
              int _arg043 = data.readInt();
              data.enforceNoDataAvail();
              ISipTransport _result20 = getSipTransport(_arg043);
              reply.writeNoException();
              reply.writeStrongInterface(_result20);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IImsService {
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

      @Override // com.android.ims.internal.IImsService
      public int open(
          int phoneId,
          int serviceClass,
          PendingIntent incomingCallIntent,
          IImsRegistrationListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(serviceClass);
          _data.writeTypedObject(incomingCallIntent, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void close(int serviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public boolean isConnected(int serviceId, int serviceType, int callType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          _data.writeInt(serviceType);
          _data.writeInt(callType);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public boolean isOpened(int serviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setRegistrationListener(int serviceId, IImsRegistrationListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void addRegistrationListener(
          int phoneId, int serviceClass, IImsRegistrationListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(serviceClass);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public ImsCallProfile createCallProfile(int serviceId, int serviceType, int callType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          _data.writeInt(serviceType);
          _data.writeInt(callType);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          ImsCallProfile _result = (ImsCallProfile) _reply.readTypedObject(ImsCallProfile.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public IImsCallSession createCallSession(
          int serviceId, ImsCallProfile profile, IImsCallSessionListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          _data.writeTypedObject(profile, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          IImsCallSession _result = IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public IImsCallSession getPendingCallSession(int serviceId, String callId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          _data.writeString(callId);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          IImsCallSession _result = IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public IImsUt getUtInterface(int serviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          IImsUt _result = IImsUt.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public android.telephony.ims.aidl.IImsConfig getConfig(int slotId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(slotId);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          android.telephony.ims.aidl.IImsConfig _result =
              IImsConfig.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void turnOnIms(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void turnOffIms(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public IImsEcbm getEcbmInterface(int serviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          IImsEcbm _result = IImsEcbm.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setUiTTYMode(int serviceId, int uiTtyMode, Message onComplete)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          _data.writeInt(uiTtyMode);
          _data.writeTypedObject(onComplete, 0);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public IImsMultiEndpoint getMultiEndpointInterface(int serviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(serviceId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          IImsMultiEndpoint _result = IImsMultiEndpoint.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public IImsRegistration getRegistration(int slotId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(slotId);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          IImsRegistration _result = IImsRegistration.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void changeAudioPath(int phoneId, int direction) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(direction);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public int startLocalRingBackTone(int streamType, int volume, int toneType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(streamType);
          _data.writeInt(volume);
          _data.writeInt(toneType);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public int stopLocalRingBackTone() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeBoolean(on);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeString(dtmfEvent);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(srcMsisdn);
          _data.writeString(dstMsisdn);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeTypedObject(publishDialog, 0);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void triggerAutoConfigurationForApp(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setTtyMode(int phoneId, int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(mode);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void notifyEpsFallbackResult(int phoneId, int result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(result);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public int getE911CallCount(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setSecImsMmTelEventListener(int phoneId, ISecImsMmTelEventListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void sendSms(
          int phoneId,
          int token,
          int messageRef,
          String format,
          String smsc,
          boolean isRetry,
          byte[] pdu)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeString(format);
          _data.writeString(smsc);
          _data.writeBoolean(isRetry);
          _data.writeByteArray(pdu);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setRetryCount(int phoneId, int token, int retryCount) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(token);
          _data.writeInt(retryCount);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void onMemoryAvailable(int phoneId, int token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(token);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setSmsc(int phoneId, String smsc) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeString(smsc);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void acknowledgeSms(int phoneId, int token, int messageRef, int result)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(result);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void acknowledgeSmsReport(int phoneId, int token, int messageRef, int result)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(result);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void setSmsListener(int phoneId, IImsSmsListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void onSmsReady(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public String getSmsFormat(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void acknowledgeSmsWithPdu(int phoneId, int token, int messageRef, byte[] data)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeByteArray(data);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public boolean queryCapabilityConfiguration(int capability, int radioTech, int slotId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(capability);
          _data.writeInt(radioTech);
          _data.writeInt(slotId);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int slotId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(slotId);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          android.telephony.ims.aidl.IImsRcsFeature _result =
              IImsRcsFeature.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public void removeImsFeature(int slotId, int featureType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(slotId);
          _data.writeInt(featureType);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsService
      public ISipTransport getSipTransport(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          ISipTransport _result = ISipTransport.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 43;
    }
  }
}
