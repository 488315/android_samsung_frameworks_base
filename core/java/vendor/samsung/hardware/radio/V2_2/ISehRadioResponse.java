package vendor.samsung.hardware.radio.V2_2;

import android.hardware.radio.V1_0.RadioResponseInfo;
import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import vendor.samsung.hardware.radio.V2_0.SehCall;
import vendor.samsung.hardware.radio.V2_0.SehCardStatus;
import vendor.samsung.hardware.radio.V2_0.SehCbConfigArgs;
import vendor.samsung.hardware.radio.V2_0.SehCsgInfo;
import vendor.samsung.hardware.radio.V2_0.SehOperatorInfo;
import vendor.samsung.hardware.radio.V2_0.SehPhonebookInfo;
import vendor.samsung.hardware.radio.V2_0.SehPreferredNetworkInfo;
import vendor.samsung.hardware.radio.V2_0.SehSendSmsResult;
import vendor.samsung.hardware.radio.V2_0.SehSimLockInfo;
import vendor.samsung.hardware.radio.V2_0.SehSimMsgArgs;
import vendor.samsung.hardware.radio.V2_0.SehSimPhonebookResponse;
import vendor.samsung.hardware.radio.V2_0.SehStoredMsgCount;

/* loaded from: classes6.dex */
public interface ISehRadioResponse extends vendor.samsung.hardware.radio.V2_1.ISehRadioResponse {
  public static final String kInterfaceName =
      "vendor.samsung.hardware.radio@2.2::ISehRadioResponse";

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
  IHwBinder asBinder();

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  DebugInfo getDebugInfo() throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  ArrayList<byte[]> getHashChain() throws RemoteException;

  void getVendorSpecificConfigurationResponse(
      RadioResponseInfo radioResponseInfo, ArrayList<SehVendorConfiguration> arrayList)
      throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  ArrayList<String> interfaceChain() throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  String interfaceDescriptor() throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  void notifySyspropsChanged() throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  void ping() throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  void setHALInstrumentation() throws RemoteException;

  void setNrModeResponse_2_2(RadioResponseInfo radioResponseInfo) throws RemoteException;

  void setVendorSpecificConfigurationResponse(RadioResponseInfo radioResponseInfo)
      throws RemoteException;

  @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
            // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
            // android.internal.hidl.base.V1_0.IBase
  boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

  static ISehRadioResponse asInterface(IHwBinder binder) {
    if (binder == null) {
      return null;
    }
    IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
    if (iface != null && (iface instanceof ISehRadioResponse)) {
      return (ISehRadioResponse) iface;
    }
    ISehRadioResponse proxy = new Proxy(binder);
    try {
      Iterator<String> it = proxy.interfaceChain().iterator();
      while (it.hasNext()) {
        String descriptor = it.next();
        if (descriptor.equals(kInterfaceName)) {
          return proxy;
        }
      }
    } catch (RemoteException e) {
    }
    return null;
  }

  static ISehRadioResponse castFrom(IHwInterface iface) {
    if (iface == null) {
      return null;
    }
    return asInterface(iface.asBinder());
  }

  static ISehRadioResponse getService(String serviceName, boolean retry) throws RemoteException {
    return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
  }

  static ISehRadioResponse getService(boolean retry) throws RemoteException {
    return getService("default", retry);
  }

  @Deprecated
  static ISehRadioResponse getService(String serviceName) throws RemoteException {
    return asInterface(HwBinder.getService(kInterfaceName, serviceName));
  }

  @Deprecated
  static ISehRadioResponse getService() throws RemoteException {
    return getService("default");
  }

  public static final class Proxy implements ISehRadioResponse {
    private IHwBinder mRemote;

    public Proxy(IHwBinder remote) {
      this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    public IHwBinder asBinder() {
      return this.mRemote;
    }

    public String toString() {
      try {
        return interfaceDescriptor() + "@Proxy";
      } catch (RemoteException e) {
        return "[class or subclass of vendor.samsung.hardware.radio@2.2::ISehRadioResponse]@Proxy";
      }
    }

    public final boolean equals(Object other) {
      return HidlSupport.interfacesEqual(this, other);
    }

    public final int hashCode() {
      return asBinder().hashCode();
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getIccCardStatusResponse(RadioResponseInfo info, SehCardStatus cardStatus)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      cardStatus.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void supplyNetworkDepersonalizationResponse(RadioResponseInfo info, int remainingRetries)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(remainingRetries);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void dialResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getCurrentCallsResponse(RadioResponseInfo info, ArrayList<SehCall> calls)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      SehCall.writeVectorToParcel(_hidl_request, calls);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getImsRegistrationStateResponse(RadioResponseInfo info, ArrayList<Integer> regState)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32Vector(regState);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getAvailableNetworksResponse(
        RadioResponseInfo info, ArrayList<SehOperatorInfo> networkInfos) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      SehOperatorInfo.writeVectorToParcel(_hidl_request, networkInfos);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setImsCallListResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getPreferredNetworkListResponse(
        RadioResponseInfo info, ArrayList<SehPreferredNetworkInfo> infos) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      SehPreferredNetworkInfo.writeVectorToParcel(_hidl_request, infos);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setPreferredNetworkListResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendEncodedUssdResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getDisable2gResponse(RadioResponseInfo info, int isDisable) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(isDisable);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setDisable2gResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getCnapResponse(RadioResponseInfo info, int m) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(m);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getPhonebookStorageInfoResponse(
        RadioResponseInfo info, SehPhonebookInfo phonebookInfo) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      phonebookInfo.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getUsimPhonebookCapabilityResponse(
        RadioResponseInfo info, ArrayList<Integer> phonebookCapability) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32Vector(phonebookCapability);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setSimOnOffResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setSimInitEventResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getSimLockInfoResponse(RadioResponseInfo info, SehSimLockInfo simLockInfo)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      simLockInfo.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void supplyIccPersonalizationResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void changeIccPersonalizationResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getPhonebookEntryResponse(
        RadioResponseInfo info, SehSimPhonebookResponse simPhonebookResponseInfo)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      simPhonebookResponseInfo.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void accessPhonebookEntryResponse(RadioResponseInfo info, int SimPhonebookAccessResp)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(SimPhonebookAccessResp);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getCellBroadcastConfigResponse(RadioResponseInfo info, SehCbConfigArgs configs)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      configs.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void emergencySearchResponse(RadioResponseInfo info, int respEmergencySearch)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(respEmergencySearch);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void emergencyControlResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getAtrResponse(RadioResponseInfo info, String atr) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeString(atr);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendCdmaSmsExpectMoreResponse(RadioResponseInfo info, SehSendSmsResult sms)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      sms.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendSmsResponse(RadioResponseInfo info, SehSendSmsResult sms)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      sms.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendSMSExpectMoreResponse(RadioResponseInfo info, SehSendSmsResult sms)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      sms.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendCdmaSmsResponse(RadioResponseInfo info, SehSendSmsResult sms)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      sms.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendImsSmsResponse(RadioResponseInfo info, SehSendSmsResult sms)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      sms.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getStoredMsgCountFromSimResponse(
        RadioResponseInfo info, SehStoredMsgCount storedMsgInfo) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      storedMsgInfo.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void readSmsFromSimResponse(RadioResponseInfo info, SehSimMsgArgs simMsg)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      simMsg.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void writeSmsToSimResponse(RadioResponseInfo info, int index) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(index);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setDataAllowedResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void getCsgListResponse(RadioResponseInfo info, ArrayList<SehCsgInfo> csgInfos)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      SehCsgInfo.writeVectorToParcel(_hidl_request, csgInfos);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void selectCsgManualResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void setMobileDataSettingResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendRequestRawResponse(RadioResponseInfo info, ArrayList<Byte> data)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt8Vector(data);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
    public void sendRequestStringsResponse(RadioResponseInfo info, ArrayList<String> data)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeStringVector(data);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
    public void setNrModeResponse(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
    public void getNrModeResponse(RadioResponseInfo info, int nrMode) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(nrMode);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
    public void getNrIconTypeResponse(RadioResponseInfo info, int nrIconType)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      _hidl_request.writeInt32(nrIconType);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
    public void getIccCardStatusResponse_2_1(
        RadioResponseInfo info, vendor.samsung.hardware.radio.V2_1.SehCardStatus cardStatus)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(
          vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      cardStatus.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse
    public void setNrModeResponse_2_2(RadioResponseInfo info) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse
    public void getVendorSpecificConfigurationResponse(
        RadioResponseInfo info, ArrayList<SehVendorConfiguration> configurations)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      SehVendorConfiguration.writeVectorToParcel(_hidl_request, configurations);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse
    public void setVendorSpecificConfigurationResponse(RadioResponseInfo info)
        throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
      info.writeToParcel(_hidl_request);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public ArrayList<String> interfaceChain() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
        _hidl_reply.verifySuccess();
        _hidl_request.releaseTemporaryStorage();
        ArrayList<String> _hidl_out_descriptors = _hidl_reply.readStringVector();
        return _hidl_out_descriptors;
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      _hidl_request.writeNativeHandle(fd);
      _hidl_request.writeStringVector(options);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
        _hidl_reply.verifySuccess();
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public String interfaceDescriptor() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
        _hidl_reply.verifySuccess();
        _hidl_request.releaseTemporaryStorage();
        String _hidl_out_descriptor = _hidl_reply.readString();
        return _hidl_out_descriptor;
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public ArrayList<byte[]> getHashChain() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
        _hidl_reply.verifySuccess();
        _hidl_request.releaseTemporaryStorage();
        ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
        HwBlob _hidl_blob = _hidl_reply.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob =
            _hidl_reply.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_out_hashchain.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
          byte[] _hidl_vec_element = new byte[32];
          long _hidl_array_offset_1 = _hidl_index_0 * 32;
          childBlob.copyToInt8Array(_hidl_array_offset_1, _hidl_vec_element, 32);
          _hidl_out_hashchain.add(_hidl_vec_element);
        }
        return _hidl_out_hashchain;
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public void setHALInstrumentation() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie)
        throws RemoteException {
      return this.mRemote.linkToDeath(recipient, cookie);
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public void ping() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
        _hidl_reply.verifySuccess();
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public DebugInfo getDebugInfo() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
        _hidl_reply.verifySuccess();
        _hidl_request.releaseTemporaryStorage();
        DebugInfo _hidl_out_info = new DebugInfo();
        _hidl_out_info.readFromParcel(_hidl_reply);
        return _hidl_out_info;
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public void notifySyspropsChanged() throws RemoteException {
      HwParcel _hidl_request = new HwParcel();
      _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
      HwParcel _hidl_reply = new HwParcel();
      try {
        this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
        _hidl_request.releaseTemporaryStorage();
      } finally {
        _hidl_reply.release();
      }
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
      return this.mRemote.unlinkToDeath(recipient);
    }
  }

  public abstract static class Stub extends HwBinder implements ISehRadioResponse {
    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    public IHwBinder asBinder() {
      return this;
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final ArrayList<String> interfaceChain() {
      return new ArrayList<>(
          Arrays.asList(
              ISehRadioResponse.kInterfaceName,
              vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName,
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName,
              IBase.kInterfaceName));
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public void debug(NativeHandle fd, ArrayList<String> options) {}

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
      return ISehRadioResponse.kInterfaceName;
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final ArrayList<byte[]> getHashChain() {
      return new ArrayList<>(
          Arrays.asList(
              new byte[] {
                -93,
                91,
                5,
                SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT,
                5,
                SprAttributeBase.TYPE_SHADOW,
                88,
                35,
                -86,
                109,
                32,
                88,
                -90,
                -4,
                5,
                -122,
                -22,
                8,
                -5,
                8,
                -105,
                79,
                117,
                SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
                -26,
                -17,
                -65,
                -63,
                -61,
                -46,
                12,
                -115
              },
              new byte[] {
                82,
                -116,
                16,
                -3,
                -40,
                72,
                MidiConstants.STATUS_SONG_SELECT,
                -31,
                -2,
                55,
                -90,
                -118,
                -3,
                0,
                87,
                85,
                SprAttributeBase.TYPE_ANIMATOR_SET,
                MidiConstants.STATUS_SONG_SELECT,
                80,
                -107,
                1,
                106,
                38,
                78,
                -73,
                -49,
                20,
                -123,
                101,
                -108,
                -69,
                -117
              },
              new byte[] {
                -109,
                -98,
                SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT,
                -18,
                -101,
                SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
                -71,
                MidiConstants.STATUS_CHANNEL_PRESSURE,
                68,
                MidiConstants.STATUS_SONG_SELECT,
                66,
                95,
                109,
                -67,
                -27,
                -119,
                107,
                -59,
                SprAttributeBase.TYPE_ANIMATOR_SET,
                SprAnimatorBase.INTERPOLATOR_TYPE_CUBICEASEINOUT,
                81,
                38,
                85,
                SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN,
                37,
                122,
                14,
                6,
                -34,
                1,
                MidiConstants.STATUS_PITCH_BEND,
                -110
              },
              new byte[] {
                -20,
                Byte.MAX_VALUE,
                -41,
                -98,
                MidiConstants.STATUS_CHANNEL_PRESSURE,
                SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
                -6,
                -123,
                -68,
                73,
                -108,
                38,
                -83,
                -82,
                62,
                -66,
                35,
                -17,
                5,
                SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT,
                MidiConstants.STATUS_SONG_SELECT,
                -51,
                105,
                87,
                SprAnimatorBase.INTERPOLATOR_TYPE_CUBICEASEIN,
                -109,
                SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT,
                -72,
                59,
                SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT,
                -54,
                76
              }));
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {}

    @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas,
              // android.internal.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
      return true;
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final void ping() {}

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final DebugInfo getDebugInfo() {
      DebugInfo info = new DebugInfo();
      info.pid = HidlSupport.getPidIfSharable();
      info.ptr = 0L;
      info.arch = 0;
      return info;
    }

    @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse,
              // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse,
              // android.internal.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
      HwBinder.enableInstrumentation();
    }

    @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas,
              // android.internal.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
      return true;
    }

    @Override // android.os.IHwBinder
    public IHwInterface queryLocalInterface(String descriptor) {
      if (ISehRadioResponse.kInterfaceName.equals(descriptor)) {
        return this;
      }
      return null;
    }

    public void registerAsService(String serviceName) throws RemoteException {
      registerService(serviceName);
    }

    public String toString() {
      return interfaceDescriptor() + "@Stub";
    }

    @Override // android.os.HwBinder
    public void onTransact(
        int _hidl_code, HwParcel _hidl_request, HwParcel _hidl_reply, int _hidl_flags)
        throws RemoteException {
      switch (_hidl_code) {
        case 1:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info = new RadioResponseInfo();
          info.readFromParcel(_hidl_request);
          SehCardStatus cardStatus = new SehCardStatus();
          cardStatus.readFromParcel(_hidl_request);
          getIccCardStatusResponse(info, cardStatus);
          return;
        case 2:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info2 = new RadioResponseInfo();
          info2.readFromParcel(_hidl_request);
          int remainingRetries = _hidl_request.readInt32();
          supplyNetworkDepersonalizationResponse(info2, remainingRetries);
          return;
        case 3:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info3 = new RadioResponseInfo();
          info3.readFromParcel(_hidl_request);
          dialResponse(info3);
          return;
        case 4:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info4 = new RadioResponseInfo();
          info4.readFromParcel(_hidl_request);
          ArrayList<SehCall> calls = SehCall.readVectorFromParcel(_hidl_request);
          getCurrentCallsResponse(info4, calls);
          return;
        case 5:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info5 = new RadioResponseInfo();
          info5.readFromParcel(_hidl_request);
          ArrayList<Integer> regState = _hidl_request.readInt32Vector();
          getImsRegistrationStateResponse(info5, regState);
          return;
        case 6:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info6 = new RadioResponseInfo();
          info6.readFromParcel(_hidl_request);
          ArrayList<SehOperatorInfo> networkInfos =
              SehOperatorInfo.readVectorFromParcel(_hidl_request);
          getAvailableNetworksResponse(info6, networkInfos);
          return;
        case 7:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info7 = new RadioResponseInfo();
          info7.readFromParcel(_hidl_request);
          setImsCallListResponse(info7);
          return;
        case 8:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info8 = new RadioResponseInfo();
          info8.readFromParcel(_hidl_request);
          ArrayList<SehPreferredNetworkInfo> infos =
              SehPreferredNetworkInfo.readVectorFromParcel(_hidl_request);
          getPreferredNetworkListResponse(info8, infos);
          return;
        case 9:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info9 = new RadioResponseInfo();
          info9.readFromParcel(_hidl_request);
          setPreferredNetworkListResponse(info9);
          return;
        case 10:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info10 = new RadioResponseInfo();
          info10.readFromParcel(_hidl_request);
          sendEncodedUssdResponse(info10);
          return;
        case 11:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info11 = new RadioResponseInfo();
          info11.readFromParcel(_hidl_request);
          int isDisable = _hidl_request.readInt32();
          getDisable2gResponse(info11, isDisable);
          return;
        case 12:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info12 = new RadioResponseInfo();
          info12.readFromParcel(_hidl_request);
          setDisable2gResponse(info12);
          return;
        case 13:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info13 = new RadioResponseInfo();
          info13.readFromParcel(_hidl_request);
          int m = _hidl_request.readInt32();
          getCnapResponse(info13, m);
          return;
        case 14:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info14 = new RadioResponseInfo();
          info14.readFromParcel(_hidl_request);
          SehPhonebookInfo phonebookInfo = new SehPhonebookInfo();
          phonebookInfo.readFromParcel(_hidl_request);
          getPhonebookStorageInfoResponse(info14, phonebookInfo);
          return;
        case 15:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info15 = new RadioResponseInfo();
          info15.readFromParcel(_hidl_request);
          ArrayList<Integer> phonebookCapability = _hidl_request.readInt32Vector();
          getUsimPhonebookCapabilityResponse(info15, phonebookCapability);
          return;
        case 16:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info16 = new RadioResponseInfo();
          info16.readFromParcel(_hidl_request);
          setSimOnOffResponse(info16);
          return;
        case 17:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info17 = new RadioResponseInfo();
          info17.readFromParcel(_hidl_request);
          setSimInitEventResponse(info17);
          return;
        case 18:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info18 = new RadioResponseInfo();
          info18.readFromParcel(_hidl_request);
          SehSimLockInfo simLockInfo = new SehSimLockInfo();
          simLockInfo.readFromParcel(_hidl_request);
          getSimLockInfoResponse(info18, simLockInfo);
          return;
        case 19:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info19 = new RadioResponseInfo();
          info19.readFromParcel(_hidl_request);
          supplyIccPersonalizationResponse(info19);
          return;
        case 20:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info20 = new RadioResponseInfo();
          info20.readFromParcel(_hidl_request);
          changeIccPersonalizationResponse(info20);
          return;
        case 21:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info21 = new RadioResponseInfo();
          info21.readFromParcel(_hidl_request);
          SehSimPhonebookResponse simPhonebookResponseInfo = new SehSimPhonebookResponse();
          simPhonebookResponseInfo.readFromParcel(_hidl_request);
          getPhonebookEntryResponse(info21, simPhonebookResponseInfo);
          return;
        case 22:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info22 = new RadioResponseInfo();
          info22.readFromParcel(_hidl_request);
          int SimPhonebookAccessResp = _hidl_request.readInt32();
          accessPhonebookEntryResponse(info22, SimPhonebookAccessResp);
          return;
        case 23:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info23 = new RadioResponseInfo();
          info23.readFromParcel(_hidl_request);
          SehCbConfigArgs configs = new SehCbConfigArgs();
          configs.readFromParcel(_hidl_request);
          getCellBroadcastConfigResponse(info23, configs);
          return;
        case 24:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info24 = new RadioResponseInfo();
          info24.readFromParcel(_hidl_request);
          int respEmergencySearch = _hidl_request.readInt32();
          emergencySearchResponse(info24, respEmergencySearch);
          return;
        case 25:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info25 = new RadioResponseInfo();
          info25.readFromParcel(_hidl_request);
          emergencyControlResponse(info25);
          return;
        case 26:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info26 = new RadioResponseInfo();
          info26.readFromParcel(_hidl_request);
          String atr = _hidl_request.readString();
          getAtrResponse(info26, atr);
          return;
        case 27:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info27 = new RadioResponseInfo();
          info27.readFromParcel(_hidl_request);
          SehSendSmsResult sms = new SehSendSmsResult();
          sms.readFromParcel(_hidl_request);
          sendCdmaSmsExpectMoreResponse(info27, sms);
          return;
        case 28:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info28 = new RadioResponseInfo();
          info28.readFromParcel(_hidl_request);
          SehSendSmsResult sms2 = new SehSendSmsResult();
          sms2.readFromParcel(_hidl_request);
          sendSmsResponse(info28, sms2);
          return;
        case 29:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info29 = new RadioResponseInfo();
          info29.readFromParcel(_hidl_request);
          SehSendSmsResult sms3 = new SehSendSmsResult();
          sms3.readFromParcel(_hidl_request);
          sendSMSExpectMoreResponse(info29, sms3);
          return;
        case 30:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info30 = new RadioResponseInfo();
          info30.readFromParcel(_hidl_request);
          SehSendSmsResult sms4 = new SehSendSmsResult();
          sms4.readFromParcel(_hidl_request);
          sendCdmaSmsResponse(info30, sms4);
          return;
        case 31:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info31 = new RadioResponseInfo();
          info31.readFromParcel(_hidl_request);
          SehSendSmsResult sms5 = new SehSendSmsResult();
          sms5.readFromParcel(_hidl_request);
          sendImsSmsResponse(info31, sms5);
          return;
        case 32:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info32 = new RadioResponseInfo();
          info32.readFromParcel(_hidl_request);
          SehStoredMsgCount storedMsgInfo = new SehStoredMsgCount();
          storedMsgInfo.readFromParcel(_hidl_request);
          getStoredMsgCountFromSimResponse(info32, storedMsgInfo);
          return;
        case 33:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info33 = new RadioResponseInfo();
          info33.readFromParcel(_hidl_request);
          SehSimMsgArgs simMsg = new SehSimMsgArgs();
          simMsg.readFromParcel(_hidl_request);
          readSmsFromSimResponse(info33, simMsg);
          return;
        case 34:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info34 = new RadioResponseInfo();
          info34.readFromParcel(_hidl_request);
          int index = _hidl_request.readInt32();
          writeSmsToSimResponse(info34, index);
          return;
        case 35:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info35 = new RadioResponseInfo();
          info35.readFromParcel(_hidl_request);
          setDataAllowedResponse(info35);
          return;
        case 36:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info36 = new RadioResponseInfo();
          info36.readFromParcel(_hidl_request);
          ArrayList<SehCsgInfo> csgInfos = SehCsgInfo.readVectorFromParcel(_hidl_request);
          getCsgListResponse(info36, csgInfos);
          return;
        case 37:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info37 = new RadioResponseInfo();
          info37.readFromParcel(_hidl_request);
          selectCsgManualResponse(info37);
          return;
        case 38:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info38 = new RadioResponseInfo();
          info38.readFromParcel(_hidl_request);
          setMobileDataSettingResponse(info38);
          return;
        case 39:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info39 = new RadioResponseInfo();
          info39.readFromParcel(_hidl_request);
          ArrayList<Byte> data = _hidl_request.readInt8Vector();
          sendRequestRawResponse(info39, data);
          return;
        case 40:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info40 = new RadioResponseInfo();
          info40.readFromParcel(_hidl_request);
          ArrayList<String> data2 = _hidl_request.readStringVector();
          sendRequestStringsResponse(info40, data2);
          return;
        case 41:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info41 = new RadioResponseInfo();
          info41.readFromParcel(_hidl_request);
          setNrModeResponse(info41);
          return;
        case 42:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info42 = new RadioResponseInfo();
          info42.readFromParcel(_hidl_request);
          int nrMode = _hidl_request.readInt32();
          getNrModeResponse(info42, nrMode);
          return;
        case 43:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info43 = new RadioResponseInfo();
          info43.readFromParcel(_hidl_request);
          int nrIconType = _hidl_request.readInt32();
          getNrIconTypeResponse(info43, nrIconType);
          return;
        case 44:
          _hidl_request.enforceInterface(
              vendor.samsung.hardware.radio.V2_1.ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info44 = new RadioResponseInfo();
          info44.readFromParcel(_hidl_request);
          vendor.samsung.hardware.radio.V2_1.SehCardStatus cardStatus2 =
              new vendor.samsung.hardware.radio.V2_1.SehCardStatus();
          cardStatus2.readFromParcel(_hidl_request);
          getIccCardStatusResponse_2_1(info44, cardStatus2);
          return;
        case 45:
          _hidl_request.enforceInterface(ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info45 = new RadioResponseInfo();
          info45.readFromParcel(_hidl_request);
          setNrModeResponse_2_2(info45);
          return;
        case 46:
          _hidl_request.enforceInterface(ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info46 = new RadioResponseInfo();
          info46.readFromParcel(_hidl_request);
          ArrayList<SehVendorConfiguration> configurations =
              SehVendorConfiguration.readVectorFromParcel(_hidl_request);
          getVendorSpecificConfigurationResponse(info46, configurations);
          return;
        case 47:
          _hidl_request.enforceInterface(ISehRadioResponse.kInterfaceName);
          RadioResponseInfo info47 = new RadioResponseInfo();
          info47.readFromParcel(_hidl_request);
          setVendorSpecificConfigurationResponse(info47);
          return;
        case 256067662:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          ArrayList<String> _hidl_out_descriptors = interfaceChain();
          _hidl_reply.writeStatus(0);
          _hidl_reply.writeStringVector(_hidl_out_descriptors);
          _hidl_reply.send();
          return;
        case 256131655:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          NativeHandle fd = _hidl_request.readNativeHandle();
          ArrayList<String> options = _hidl_request.readStringVector();
          debug(fd, options);
          _hidl_reply.writeStatus(0);
          _hidl_reply.send();
          return;
        case 256136003:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          String _hidl_out_descriptor = interfaceDescriptor();
          _hidl_reply.writeStatus(0);
          _hidl_reply.writeString(_hidl_out_descriptor);
          _hidl_reply.send();
          return;
        case 256398152:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
          _hidl_reply.writeStatus(0);
          HwBlob _hidl_blob = new HwBlob(16);
          int _hidl_vec_size = _hidl_out_hashchain.size();
          _hidl_blob.putInt32(8L, _hidl_vec_size);
          _hidl_blob.putBool(12L, false);
          HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
          for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            long _hidl_array_offset_1 = _hidl_index_0 * 32;
            byte[] _hidl_array_item_1 = _hidl_out_hashchain.get(_hidl_index_0);
            if (_hidl_array_item_1 == null || _hidl_array_item_1.length != 32) {
              throw new IllegalArgumentException("Array element is not of the expected length");
            }
            childBlob.putInt8Array(_hidl_array_offset_1, _hidl_array_item_1);
          }
          _hidl_blob.putBlob(0L, childBlob);
          _hidl_reply.writeBuffer(_hidl_blob);
          _hidl_reply.send();
          return;
        case 256462420:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          setHALInstrumentation();
          return;
        case 256660548:
        default:
          return;
        case 256921159:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          ping();
          _hidl_reply.writeStatus(0);
          _hidl_reply.send();
          return;
        case 257049926:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          DebugInfo _hidl_out_info = getDebugInfo();
          _hidl_reply.writeStatus(0);
          _hidl_out_info.writeToParcel(_hidl_reply);
          _hidl_reply.send();
          return;
        case 257120595:
          _hidl_request.enforceInterface(IBase.kInterfaceName);
          notifySyspropsChanged();
          return;
      }
    }
  }
}
