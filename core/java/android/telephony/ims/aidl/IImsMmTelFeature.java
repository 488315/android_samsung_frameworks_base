package android.telephony.ims.aidl;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Message;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.ims.MediaThreshold;
import android.telephony.ims.RtpHeaderExtensionType;
import android.telephony.ims.feature.CapabilityChangeRequest;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;
import com.android.internal.telephony.PublishDialog;
import java.util.List;

/* loaded from: classes3.dex */
public interface IImsMmTelFeature extends IInterface {
  public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelFeature";

  void acknowledgeSms(int i, int i2, int i3) throws RemoteException;

  void acknowledgeSmsReport(int i, int i2, int i3) throws RemoteException;

  void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException;

  void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

  void changeAudioPath(int i, int i2) throws RemoteException;

  void changeCapabilitiesConfiguration(
      CapabilityChangeRequest capabilityChangeRequest,
      IImsCapabilityCallback iImsCapabilityCallback)
      throws RemoteException;

  void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> list)
      throws RemoteException;

  ImsCallProfile createCallProfile(int i, int i2) throws RemoteException;

  IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException;

  IImsEcbm getEcbmInterface() throws RemoteException;

  int getFeatureState() throws RemoteException;

  int getInitialCallNetworkType(int i) throws RemoteException;

  IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

  String getSmsFormat() throws RemoteException;

  String getTrn(String str, String str2) throws RemoteException;

  IImsUt getUtInterface() throws RemoteException;

  void initImsSmsImplAdapter() throws RemoteException;

  boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

  void notifyEpsFallbackResult(int i, int i2) throws RemoteException;

  void notifySrvccCanceled() throws RemoteException;

  void notifySrvccCompleted() throws RemoteException;

  void notifySrvccFailed() throws RemoteException;

  void notifySrvccStarted(ISrvccStartedCallback iSrvccStartedCallback) throws RemoteException;

  void onMemoryAvailable(int i) throws RemoteException;

  void onSmsReady() throws RemoteException;

  void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback)
      throws RemoteException;

  int queryCapabilityStatus() throws RemoteException;

  MediaQualityStatus queryMediaQualityStatus(int i) throws RemoteException;

  void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback)
      throws RemoteException;

  void sendDtmfEvent(int i, String str) throws RemoteException;

  void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException;

  void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr)
      throws RemoteException;

  void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException;

  void setMediaQualityThreshold(int i, MediaThreshold mediaThreshold) throws RemoteException;

  void setRetryCount(int i, int i2) throws RemoteException;

  void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException;

  void setSmsc(String str) throws RemoteException;

  void setTerminalBasedCallWaitingStatus(boolean z) throws RemoteException;

  void setTtyMode(int i) throws RemoteException;

  void setUiTtyMode(int i, Message message) throws RemoteException;

  void setVideoCrtAudio(int i, boolean z) throws RemoteException;

  int shouldProcessCall(String[] strArr) throws RemoteException;

  int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

  int stopLocalRingBackTone() throws RemoteException;

  void triggerAutoConfigurationForApp(int i) throws RemoteException;

  public static class Default implements IImsMmTelFeature {
    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setListener(IImsMmTelListener l) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public int getFeatureState() throws RemoteException {
      return 0;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public ImsCallProfile createCallProfile(int callSessionType, int callType)
        throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> types)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public IImsCallSession createCallSession(ImsCallProfile profile) throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public int shouldProcessCall(String[] uris) throws RemoteException {
      return 0;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public IImsUt getUtInterface() throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public IImsEcbm getEcbmInterface() throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setUiTtyMode(int uiTtyMode, Message onCompleteMessage) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public int queryCapabilityStatus() throws RemoteException {
      return 0;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setTerminalBasedCallWaitingStatus(boolean enabled) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void addCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void removeCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void changeCapabilitiesConfiguration(
        CapabilityChangeRequest request, IImsCapabilityCallback c) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void queryCapabilityConfiguration(
        int capability, int radioTech, IImsCapabilityCallback c) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void notifySrvccStarted(ISrvccStartedCallback cb) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void notifySrvccCompleted() throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void notifySrvccFailed() throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void notifySrvccCanceled() throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setMediaQualityThreshold(int mediaSessionType, MediaThreshold threshold)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public MediaQualityStatus queryMediaQualityStatus(int mediaSessionType) throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setSmsListener(IImsSmsListener l) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void sendSms(
        int token, int messageRef, String format, String smsc, boolean retry, byte[] pdu)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void onMemoryAvailable(int token) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void acknowledgeSms(int token, int messageRef, int result) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void acknowledgeSmsWithPdu(int token, int messageRef, int result, byte[] pdu)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void acknowledgeSmsReport(int token, int messageRef, int result)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public String getSmsFormat() throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void onSmsReady() throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setRetryCount(int token, int retryCount) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setSmsc(String smsc) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void initImsSmsImplAdapter() throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void changeAudioPath(int phoneId, int direction) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public int startLocalRingBackTone(int streamType, int volume, int toneType)
        throws RemoteException {
      return 0;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public int stopLocalRingBackTone() throws RemoteException {
      return 0;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
      return null;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
      return false;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void setTtyMode(int ttyMode) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public int getInitialCallNetworkType(int phoneId) throws RemoteException {
      return 0;
    }

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void triggerAutoConfigurationForApp(int phoneId) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsMmTelFeature
    public void notifyEpsFallbackResult(int phoneId, int result) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IImsMmTelFeature {
    static final int TRANSACTION_acknowledgeSms = 26;
    static final int TRANSACTION_acknowledgeSmsReport = 28;
    static final int TRANSACTION_acknowledgeSmsWithPdu = 27;
    static final int TRANSACTION_addCapabilityCallback = 13;
    static final int TRANSACTION_changeAudioPath = 34;
    static final int TRANSACTION_changeCapabilitiesConfiguration = 15;
    static final int TRANSACTION_changeOfferedRtpHeaderExtensionTypes = 4;
    static final int TRANSACTION_createCallProfile = 3;
    static final int TRANSACTION_createCallSession = 5;
    static final int TRANSACTION_getEcbmInterface = 8;
    static final int TRANSACTION_getFeatureState = 2;
    static final int TRANSACTION_getInitialCallNetworkType = 43;
    static final int TRANSACTION_getMultiEndpointInterface = 10;
    static final int TRANSACTION_getSmsFormat = 29;
    static final int TRANSACTION_getTrn = 39;
    static final int TRANSACTION_getUtInterface = 7;
    static final int TRANSACTION_initImsSmsImplAdapter = 33;
    static final int TRANSACTION_isCmcEmergencyCallSupported = 41;
    static final int TRANSACTION_notifyEpsFallbackResult = 45;
    static final int TRANSACTION_notifySrvccCanceled = 20;
    static final int TRANSACTION_notifySrvccCompleted = 18;
    static final int TRANSACTION_notifySrvccFailed = 19;
    static final int TRANSACTION_notifySrvccStarted = 17;
    static final int TRANSACTION_onMemoryAvailable = 25;
    static final int TRANSACTION_onSmsReady = 30;
    static final int TRANSACTION_queryCapabilityConfiguration = 16;
    static final int TRANSACTION_queryCapabilityStatus = 11;
    static final int TRANSACTION_queryMediaQualityStatus = 22;
    static final int TRANSACTION_removeCapabilityCallback = 14;
    static final int TRANSACTION_sendDtmfEvent = 38;
    static final int TRANSACTION_sendPublishDialog = 40;
    static final int TRANSACTION_sendSms = 24;
    static final int TRANSACTION_setListener = 1;
    static final int TRANSACTION_setMediaQualityThreshold = 21;
    static final int TRANSACTION_setRetryCount = 31;
    static final int TRANSACTION_setSmsListener = 23;
    static final int TRANSACTION_setSmsc = 32;
    static final int TRANSACTION_setTerminalBasedCallWaitingStatus = 12;
    static final int TRANSACTION_setTtyMode = 42;
    static final int TRANSACTION_setUiTtyMode = 9;
    static final int TRANSACTION_setVideoCrtAudio = 37;
    static final int TRANSACTION_shouldProcessCall = 6;
    static final int TRANSACTION_startLocalRingBackTone = 35;
    static final int TRANSACTION_stopLocalRingBackTone = 36;
    static final int TRANSACTION_triggerAutoConfigurationForApp = 44;

    public Stub() {
      attachInterface(this, IImsMmTelFeature.DESCRIPTOR);
    }

    public static IImsMmTelFeature asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IImsMmTelFeature.DESCRIPTOR);
      if (iin != null && (iin instanceof IImsMmTelFeature)) {
        return (IImsMmTelFeature) iin;
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
          return "setListener";
        case 2:
          return "getFeatureState";
        case 3:
          return "createCallProfile";
        case 4:
          return "changeOfferedRtpHeaderExtensionTypes";
        case 5:
          return "createCallSession";
        case 6:
          return "shouldProcessCall";
        case 7:
          return "getUtInterface";
        case 8:
          return "getEcbmInterface";
        case 9:
          return "setUiTtyMode";
        case 10:
          return "getMultiEndpointInterface";
        case 11:
          return "queryCapabilityStatus";
        case 12:
          return "setTerminalBasedCallWaitingStatus";
        case 13:
          return "addCapabilityCallback";
        case 14:
          return "removeCapabilityCallback";
        case 15:
          return "changeCapabilitiesConfiguration";
        case 16:
          return "queryCapabilityConfiguration";
        case 17:
          return "notifySrvccStarted";
        case 18:
          return "notifySrvccCompleted";
        case 19:
          return "notifySrvccFailed";
        case 20:
          return "notifySrvccCanceled";
        case 21:
          return "setMediaQualityThreshold";
        case 22:
          return "queryMediaQualityStatus";
        case 23:
          return "setSmsListener";
        case 24:
          return "sendSms";
        case 25:
          return "onMemoryAvailable";
        case 26:
          return "acknowledgeSms";
        case 27:
          return "acknowledgeSmsWithPdu";
        case 28:
          return "acknowledgeSmsReport";
        case 29:
          return "getSmsFormat";
        case 30:
          return "onSmsReady";
        case 31:
          return "setRetryCount";
        case 32:
          return "setSmsc";
        case 33:
          return "initImsSmsImplAdapter";
        case 34:
          return "changeAudioPath";
        case 35:
          return "startLocalRingBackTone";
        case 36:
          return "stopLocalRingBackTone";
        case 37:
          return "setVideoCrtAudio";
        case 38:
          return "sendDtmfEvent";
        case 39:
          return "getTrn";
        case 40:
          return "sendPublishDialog";
        case 41:
          return "isCmcEmergencyCallSupported";
        case 42:
          return "setTtyMode";
        case 43:
          return "getInitialCallNetworkType";
        case 44:
          return "triggerAutoConfigurationForApp";
        case 45:
          return "notifyEpsFallbackResult";
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
        data.enforceInterface(IImsMmTelFeature.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IImsMmTelFeature.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IImsMmTelListener _arg0 = IImsMmTelListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setListener(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              int _result = getFeatureState();
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 3:
              int _arg02 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              ImsCallProfile _result2 = createCallProfile(_arg02, _arg1);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 4:
              List<RtpHeaderExtensionType> _arg03 =
                  data.createTypedArrayList(RtpHeaderExtensionType.CREATOR);
              data.enforceNoDataAvail();
              changeOfferedRtpHeaderExtensionTypes(_arg03);
              reply.writeNoException();
              return true;
            case 5:
              ImsCallProfile _arg04 = (ImsCallProfile) data.readTypedObject(ImsCallProfile.CREATOR);
              data.enforceNoDataAvail();
              IImsCallSession _result3 = createCallSession(_arg04);
              reply.writeNoException();
              reply.writeStrongInterface(_result3);
              return true;
            case 6:
              String[] _arg05 = data.createStringArray();
              data.enforceNoDataAvail();
              int _result4 = shouldProcessCall(_arg05);
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            case 7:
              IImsUt _result5 = getUtInterface();
              reply.writeNoException();
              reply.writeStrongInterface(_result5);
              return true;
            case 8:
              IImsEcbm _result6 = getEcbmInterface();
              reply.writeNoException();
              reply.writeStrongInterface(_result6);
              return true;
            case 9:
              int _arg06 = data.readInt();
              Message _arg12 = (Message) data.readTypedObject(Message.CREATOR);
              data.enforceNoDataAvail();
              setUiTtyMode(_arg06, _arg12);
              reply.writeNoException();
              return true;
            case 10:
              IImsMultiEndpoint _result7 = getMultiEndpointInterface();
              reply.writeNoException();
              reply.writeStrongInterface(_result7);
              return true;
            case 11:
              int _result8 = queryCapabilityStatus();
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 12:
              boolean _arg07 = data.readBoolean();
              data.enforceNoDataAvail();
              setTerminalBasedCallWaitingStatus(_arg07);
              reply.writeNoException();
              return true;
            case 13:
              IImsCapabilityCallback _arg08 =
                  IImsCapabilityCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              addCapabilityCallback(_arg08);
              return true;
            case 14:
              IImsCapabilityCallback _arg09 =
                  IImsCapabilityCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              removeCapabilityCallback(_arg09);
              return true;
            case 15:
              CapabilityChangeRequest _arg010 =
                  (CapabilityChangeRequest) data.readTypedObject(CapabilityChangeRequest.CREATOR);
              IImsCapabilityCallback _arg13 =
                  IImsCapabilityCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              changeCapabilitiesConfiguration(_arg010, _arg13);
              return true;
            case 16:
              int _arg011 = data.readInt();
              int _arg14 = data.readInt();
              IImsCapabilityCallback _arg2 =
                  IImsCapabilityCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              queryCapabilityConfiguration(_arg011, _arg14, _arg2);
              return true;
            case 17:
              ISrvccStartedCallback _arg012 =
                  ISrvccStartedCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              notifySrvccStarted(_arg012);
              return true;
            case 18:
              notifySrvccCompleted();
              return true;
            case 19:
              notifySrvccFailed();
              return true;
            case 20:
              notifySrvccCanceled();
              return true;
            case 21:
              int _arg013 = data.readInt();
              MediaThreshold _arg15 = (MediaThreshold) data.readTypedObject(MediaThreshold.CREATOR);
              data.enforceNoDataAvail();
              setMediaQualityThreshold(_arg013, _arg15);
              return true;
            case 22:
              int _arg014 = data.readInt();
              data.enforceNoDataAvail();
              MediaQualityStatus _result9 = queryMediaQualityStatus(_arg014);
              reply.writeNoException();
              reply.writeTypedObject(_result9, 1);
              return true;
            case 23:
              IImsSmsListener _arg015 = IImsSmsListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSmsListener(_arg015);
              reply.writeNoException();
              return true;
            case 24:
              int _arg016 = data.readInt();
              int _arg16 = data.readInt();
              String _arg22 = data.readString();
              String _arg3 = data.readString();
              boolean _arg4 = data.readBoolean();
              byte[] _arg5 = data.createByteArray();
              data.enforceNoDataAvail();
              sendSms(_arg016, _arg16, _arg22, _arg3, _arg4, _arg5);
              return true;
            case 25:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              onMemoryAvailable(_arg017);
              return true;
            case 26:
              int _arg018 = data.readInt();
              int _arg17 = data.readInt();
              int _arg23 = data.readInt();
              data.enforceNoDataAvail();
              acknowledgeSms(_arg018, _arg17, _arg23);
              return true;
            case 27:
              int _arg019 = data.readInt();
              int _arg18 = data.readInt();
              int _arg24 = data.readInt();
              byte[] _arg32 = data.createByteArray();
              data.enforceNoDataAvail();
              acknowledgeSmsWithPdu(_arg019, _arg18, _arg24, _arg32);
              return true;
            case 28:
              int _arg020 = data.readInt();
              int _arg19 = data.readInt();
              int _arg25 = data.readInt();
              data.enforceNoDataAvail();
              acknowledgeSmsReport(_arg020, _arg19, _arg25);
              return true;
            case 29:
              String _result10 = getSmsFormat();
              reply.writeNoException();
              reply.writeString(_result10);
              return true;
            case 30:
              onSmsReady();
              return true;
            case 31:
              int _arg021 = data.readInt();
              int _arg110 = data.readInt();
              data.enforceNoDataAvail();
              setRetryCount(_arg021, _arg110);
              return true;
            case 32:
              String _arg022 = data.readString();
              data.enforceNoDataAvail();
              setSmsc(_arg022);
              reply.writeNoException();
              return true;
            case 33:
              initImsSmsImplAdapter();
              reply.writeNoException();
              return true;
            case 34:
              int _arg023 = data.readInt();
              int _arg111 = data.readInt();
              data.enforceNoDataAvail();
              changeAudioPath(_arg023, _arg111);
              reply.writeNoException();
              return true;
            case 35:
              int _arg024 = data.readInt();
              int _arg112 = data.readInt();
              int _arg26 = data.readInt();
              data.enforceNoDataAvail();
              int _result11 = startLocalRingBackTone(_arg024, _arg112, _arg26);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 36:
              int _result12 = stopLocalRingBackTone();
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 37:
              int _arg025 = data.readInt();
              boolean _arg113 = data.readBoolean();
              data.enforceNoDataAvail();
              setVideoCrtAudio(_arg025, _arg113);
              reply.writeNoException();
              return true;
            case 38:
              int _arg026 = data.readInt();
              String _arg114 = data.readString();
              data.enforceNoDataAvail();
              sendDtmfEvent(_arg026, _arg114);
              reply.writeNoException();
              return true;
            case 39:
              String _arg027 = data.readString();
              String _arg115 = data.readString();
              data.enforceNoDataAvail();
              String _result13 = getTrn(_arg027, _arg115);
              reply.writeNoException();
              reply.writeString(_result13);
              return true;
            case 40:
              int _arg028 = data.readInt();
              PublishDialog _arg116 = (PublishDialog) data.readTypedObject(PublishDialog.CREATOR);
              data.enforceNoDataAvail();
              sendPublishDialog(_arg028, _arg116);
              reply.writeNoException();
              return true;
            case 41:
              int _arg029 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result14 = isCmcEmergencyCallSupported(_arg029);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 42:
              int _arg030 = data.readInt();
              data.enforceNoDataAvail();
              setTtyMode(_arg030);
              reply.writeNoException();
              return true;
            case 43:
              int _arg031 = data.readInt();
              data.enforceNoDataAvail();
              int _result15 = getInitialCallNetworkType(_arg031);
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 44:
              int _arg032 = data.readInt();
              data.enforceNoDataAvail();
              triggerAutoConfigurationForApp(_arg032);
              reply.writeNoException();
              return true;
            case 45:
              int _arg033 = data.readInt();
              int _arg117 = data.readInt();
              data.enforceNoDataAvail();
              notifyEpsFallbackResult(_arg033, _arg117);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IImsMmTelFeature {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IImsMmTelFeature.DESCRIPTOR;
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setListener(IImsMmTelListener l) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeStrongInterface(l);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public int getFeatureState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public ImsCallProfile createCallProfile(int callSessionType, int callType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(callSessionType);
          _data.writeInt(callType);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          ImsCallProfile _result = (ImsCallProfile) _reply.readTypedObject(ImsCallProfile.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> types)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeTypedList(types, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public IImsCallSession createCallSession(ImsCallProfile profile) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeTypedObject(profile, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          IImsCallSession _result = IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public int shouldProcessCall(String[] uris) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeStringArray(uris);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public IImsUt getUtInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          IImsUt _result = IImsUt.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public IImsEcbm getEcbmInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          IImsEcbm _result = IImsEcbm.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setUiTtyMode(int uiTtyMode, Message onCompleteMessage) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(uiTtyMode);
          _data.writeTypedObject(onCompleteMessage, 0);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          IImsMultiEndpoint _result = IImsMultiEndpoint.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public int queryCapabilityStatus() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setTerminalBasedCallWaitingStatus(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void addCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeStrongInterface(c);
          this.mRemote.transact(13, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void removeCapabilityCallback(IImsCapabilityCallback c) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeStrongInterface(c);
          this.mRemote.transact(14, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void changeCapabilitiesConfiguration(
          CapabilityChangeRequest request, IImsCapabilityCallback c) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeTypedObject(request, 0);
          _data.writeStrongInterface(c);
          this.mRemote.transact(15, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void queryCapabilityConfiguration(
          int capability, int radioTech, IImsCapabilityCallback c) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(capability);
          _data.writeInt(radioTech);
          _data.writeStrongInterface(c);
          this.mRemote.transact(16, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void notifySrvccStarted(ISrvccStartedCallback cb) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeStrongInterface(cb);
          this.mRemote.transact(17, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void notifySrvccCompleted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(18, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void notifySrvccFailed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(19, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void notifySrvccCanceled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(20, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setMediaQualityThreshold(int mediaSessionType, MediaThreshold threshold)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(mediaSessionType);
          _data.writeTypedObject(threshold, 0);
          this.mRemote.transact(21, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public MediaQualityStatus queryMediaQualityStatus(int mediaSessionType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(mediaSessionType);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          MediaQualityStatus _result =
              (MediaQualityStatus) _reply.readTypedObject(MediaQualityStatus.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setSmsListener(IImsSmsListener l) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeStrongInterface(l);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void sendSms(
          int token, int messageRef, String format, String smsc, boolean retry, byte[] pdu)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeString(format);
          _data.writeString(smsc);
          _data.writeBoolean(retry);
          _data.writeByteArray(pdu);
          this.mRemote.transact(24, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void onMemoryAvailable(int token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(token);
          this.mRemote.transact(25, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void acknowledgeSms(int token, int messageRef, int result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(result);
          this.mRemote.transact(26, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void acknowledgeSmsWithPdu(int token, int messageRef, int result, byte[] pdu)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(result);
          _data.writeByteArray(pdu);
          this.mRemote.transact(27, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void acknowledgeSmsReport(int token, int messageRef, int result)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(result);
          this.mRemote.transact(28, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public String getSmsFormat() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void onSmsReady() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(30, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setRetryCount(int token, int retryCount) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(retryCount);
          this.mRemote.transact(31, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setSmsc(String smsc) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeString(smsc);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void initImsSmsImplAdapter() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void changeAudioPath(int phoneId, int direction) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(direction);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public int startLocalRingBackTone(int streamType, int volume, int toneType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(streamType);
          _data.writeInt(volume);
          _data.writeInt(toneType);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public int stopLocalRingBackTone() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeBoolean(on);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeString(dtmfEvent);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeString(srcMsisdn);
          _data.writeString(dstMsisdn);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeTypedObject(publishDialog, 0);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void setTtyMode(int ttyMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(ttyMode);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public int getInitialCallNetworkType(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void triggerAutoConfigurationForApp(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsMmTelFeature
      public void notifyEpsFallbackResult(int phoneId, int result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(result);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 44;
    }
  }
}
