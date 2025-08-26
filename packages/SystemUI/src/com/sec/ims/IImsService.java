package com.sec.ims;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.IAutoConfigurationListener;
import com.sec.ims.ICentralMsgStoreService;
import com.sec.ims.IDialogEventListener;
import com.sec.ims.IEpdgListener;
import com.sec.ims.IImsDmConfigListener;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.IRttEventListener;
import com.sec.ims.ISimMobilityStatusListener;
import com.sec.ims.ImsEventListener;
import com.sec.ims.cmc.CmcCallCmdInfo;
import com.sec.ims.cmc.CmcCallCmdResult;
import com.sec.ims.cmc.CmcCallInfo;
import com.sec.ims.cmc.CmcRecordingInfo;
import com.sec.ims.cmc.ICmcCallEventListener;
import com.sec.ims.cmc.ICmcDialogListener;
import com.sec.ims.cmc.ICmcRecordingListener;
import com.sec.ims.ft.IImsOngoingFtEventListener;
import com.sec.ims.im.IImSessionListener;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IImsService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IImsService";

    void changeAudioPath(int i) throws RemoteException;

    void changeAudioPathForSlot(int i, int i2) throws RemoteException;

    CmcCallCmdResult cmcAnswerCall(int i, int i2) throws RemoteException;

    CmcCallCmdResult cmcEndCall(int i, int i2, int i3) throws RemoteException;

    CmcCallCmdResult cmcHoldCall(int i, int i2) throws RemoteException;

    CmcCallCmdResult cmcMakeCall(int i, String str, CmcCallCmdInfo cmcCallCmdInfo) throws RemoteException;

    CmcCallCmdResult cmcPullCall(int i, String str) throws RemoteException;

    CmcCallCmdResult cmcRejectCall(int i, int i2, int i3) throws RemoteException;

    CmcCallCmdResult cmcResumeCall(int i, int i2) throws RemoteException;

    CmcCallCmdResult cmcSendDtmf(int i, int i2, char c) throws RemoteException;

    CmcCallCmdResult cmcStartDtmf(int i, int i2, char c) throws RemoteException;

    CmcCallCmdResult cmcStopDtmf(int i, int i2) throws RemoteException;

    void deregisterAdhocProfile(int i) throws RemoteException;

    void deregisterAdhocProfileByPhoneId(int i, int i2) throws RemoteException;

    void deregisterProfile(List list, boolean z) throws RemoteException;

    void deregisterProfileByPhoneId(List list, boolean z, int i) throws RemoteException;

    void dump() throws RemoteException;

    void enableRcs(boolean z) throws RemoteException;

    void enableRcsByPhoneId(boolean z, int i) throws RemoteException;

    void enableService(String str, boolean z) throws RemoteException;

    void enableServiceByPhoneId(String str, boolean z, int i) throws RemoteException;

    void enableVoLte(boolean z) throws RemoteException;

    void enableVoLteByPhoneId(boolean z, int i) throws RemoteException;

    void finishDmConfig(int i, int i2) throws RemoteException;

    void forcedUpdateRegistration(ImsProfile imsProfile) throws RemoteException;

    void forcedUpdateRegistrationByPhoneId(ImsProfile imsProfile, int i) throws RemoteException;

    String getAvailableNetworkType(String str) throws RemoteException;

    int[] getCallCount(int i) throws RemoteException;

    CmcCallInfo getCmcCallInfo() throws RemoteException;

    ContentValues getConfigValues(String[] strArr, int i) throws RemoteException;

    ImsProfile[] getCurrentProfile() throws RemoteException;

    ImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException;

    int getEpsFbCallCount(int i) throws RemoteException;

    boolean getGlobalSettingsValueToBoolean(String str, int i, boolean z) throws RemoteException;

    int getGlobalSettingsValueToInteger(String str, int i, int i2) throws RemoteException;

    String getGlobalSettingsValueToString(String str, int i, String str2) throws RemoteException;

    DialogEvent getLastDialogEvent(int i) throws RemoteException;

    LastEndedImsCallInfo getLastEndedImsCallInfo(int i) throws RemoteException;

    String getMasterStringValue(int i) throws RemoteException;

    int getMasterValue(int i) throws RemoteException;

    int getNetworkType(int i) throws RemoteException;

    int getNrSaCallCount(int i) throws RemoteException;

    int getPhoneCount() throws RemoteException;

    String getRcsProfileType(int i) throws RemoteException;

    ImsRegistration[] getRegistrationInfo() throws RemoteException;

    ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException;

    ImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException;

    int getRttMode(int i) throws RemoteException;

    int getVideocallType() throws RemoteException;

    boolean hasCrossSimImsService(int i) throws RemoteException;

    boolean hasVoLteSim() throws RemoteException;

    boolean hasVoLteSimByPhoneId(int i) throws RemoteException;

    boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

    boolean isCmcEmergencyNumber(String str, int i) throws RemoteException;

    boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException;

    boolean isCrossSimCallingRegistered(int i) throws RemoteException;

    boolean isCrossSimCallingSupported() throws RemoteException;

    boolean isCrossSimCallingSupportedByPhoneId(int i) throws RemoteException;

    boolean isCrossSimPermanentBlocked(int i) throws RemoteException;

    boolean isForbidden() throws RemoteException;

    boolean isForbiddenByPhoneId(int i) throws RemoteException;

    boolean isImsEnabled() throws RemoteException;

    boolean isImsEnabledByPhoneId(int i) throws RemoteException;

    boolean isNonVerifiedMno(int i) throws RemoteException;

    boolean isQSSSuccessAuthAndLogin(int i) throws RemoteException;

    boolean isRcsEnabled() throws RemoteException;

    boolean isRegistered() throws RemoteException;

    boolean isRttCall(int i) throws RemoteException;

    boolean isServiceAvailable(String str, int i, int i2) throws RemoteException;

    boolean isServiceEnabled(String str) throws RemoteException;

    boolean isServiceEnabledByPhoneId(String str, int i) throws RemoteException;

    boolean isSupportVoWiFiDisable5GSA(int i) throws RemoteException;

    boolean isVoLteEnabled() throws RemoteException;

    boolean isVoLteEnabledByPhoneId(int i) throws RemoteException;

    boolean isVolteEnabledFromNetwork(int i) throws RemoteException;

    boolean isVolteSupportECT() throws RemoteException;

    boolean isVolteSupportEctByPhoneId(int i) throws RemoteException;

    int registerAdhocProfile(ImsProfile imsProfile) throws RemoteException;

    int registerAdhocProfileByPhoneId(ImsProfile imsProfile, int i) throws RemoteException;

    String registerAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener, int i) throws RemoteException;

    void registerCallback(ImsEventListener imsEventListener, String str) throws RemoteException;

    String registerCmcCallEventListenerForSlot(int i, ICmcCallEventListener iCmcCallEventListener) throws RemoteException;

    String registerCmcDialogListenerByToken(int i, ICmcDialogListener iCmcDialogListener) throws RemoteException;

    void registerCmcRecordingListener(int i, ICmcRecordingListener iCmcRecordingListener) throws RemoteException;

    String registerCmcRegistrationListenerForSlot(IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException;

    String registerCmsRegistrationListenerByPhoneId(ICentralMsgStoreService iCentralMsgStoreService, int i) throws RemoteException;

    void registerDialogEventListener(int i, IDialogEventListener iDialogEventListener) throws RemoteException;

    String registerDialogEventListenerByToken(int i, IDialogEventListener iDialogEventListener) throws RemoteException;

    void registerDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException;

    String registerEpdgListener(IEpdgListener iEpdgListener) throws RemoteException;

    String registerImSessionListener(IImSessionListener iImSessionListener) throws RemoteException;

    String registerImSessionListenerByPhoneId(IImSessionListener iImSessionListener, int i) throws RemoteException;

    String registerImsOngoingFtListener(IImsOngoingFtEventListener iImsOngoingFtEventListener) throws RemoteException;

    String registerImsOngoingFtListenerByPhoneId(IImsOngoingFtEventListener iImsOngoingFtEventListener, int i) throws RemoteException;

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    String registerImsRegistrationListenerForSlot(IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException;

    void registerProfile(List list) throws RemoteException;

    void registerProfileByPhoneId(List list, int i) throws RemoteException;

    String registerRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException;

    String registerSimMobilityStatusListenerByPhoneId(ISimMobilityStatusListener iSimMobilityStatusListener, int i) throws RemoteException;

    void sendCmcRecordingEvent(int i, int i2, CmcRecordingInfo cmcRecordingInfo) throws RemoteException;

    void sendDeregister(int i, int i2) throws RemoteException;

    void sendIidToken(String str, int i) throws RemoteException;

    void sendMsisdnNumber(String str, int i) throws RemoteException;

    void sendRttMessage(String str) throws RemoteException;

    void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException;

    void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException;

    void sendTryRegister() throws RemoteException;

    void sendTryRegisterByPhoneId(int i) throws RemoteException;

    void sendTryRegisterCms(int i) throws RemoteException;

    void sendVerificationCode(String str, int i) throws RemoteException;

    int setActiveImpu(int i, String str, String str2) throws RemoteException;

    int setActiveMsisdn(int i, String str, String str2) throws RemoteException;

    void setAutomaticMode(int i, boolean z) throws RemoteException;

    void setCrossSimPermanentBlocked(int i, boolean z) throws RemoteException;

    void setEmergencyPdnInfo(String str, String[] strArr, String str2, int i) throws RemoteException;

    void setIsimLoaded() throws RemoteException;

    void setNrInterworkingMode(int i, int i2) throws RemoteException;

    void setProvisionedStringValue(int i, String str) throws RemoteException;

    void setProvisionedValue(int i, int i2) throws RemoteException;

    void setRttMode(int i, int i2) throws RemoteException;

    void setSimRefreshed() throws RemoteException;

    boolean setVideocallType(int i) throws RemoteException;

    int startDmConfig(int i) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void suspendRegister(boolean z, int i) throws RemoteException;

    void transferCall(String str, String str2) throws RemoteException;

    void triggerAutoConfigurationForApp(int i) throws RemoteException;

    void unRegisterEpdgListener(String str) throws RemoteException;

    void unregisterAutoConfigurationListener(String str, int i) throws RemoteException;

    void unregisterCallback(ImsEventListener imsEventListener) throws RemoteException;

    void unregisterCmcCallEventListenerForSlot(int i, String str) throws RemoteException;

    void unregisterCmcDialogListenerByToken(int i, String str) throws RemoteException;

    void unregisterCmcRegistrationListenerForSlot(String str, int i) throws RemoteException;

    void unregisterCmsRegistrationListenerByPhoneId(String str, int i) throws RemoteException;

    void unregisterDialogEventListener(int i, IDialogEventListener iDialogEventListener) throws RemoteException;

    void unregisterDialogEventListenerByToken(int i, String str) throws RemoteException;

    void unregisterDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException;

    void unregisterImSessionListener(String str) throws RemoteException;

    void unregisterImSessionListenerByPhoneId(String str, int i) throws RemoteException;

    void unregisterImsOngoingFtListener(String str) throws RemoteException;

    void unregisterImsOngoingFtListenerByPhoneId(String str, int i) throws RemoteException;

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException;

    void unregisterRttEventListener(int i, String str) throws RemoteException;

    void unregisterSimMobilityStatusListenerByPhoneId(String str, int i) throws RemoteException;

    boolean updateConfigValues(ContentValues contentValues, int i, int i2) throws RemoteException;

    int updateRegistration(ImsProfile imsProfile, int i) throws RemoteException;

    public class Default implements IImsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcAnswerCall(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcEndCall(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcHoldCall(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcMakeCall(int i, String str, CmcCallCmdInfo cmcCallCmdInfo) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcPullCall(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcRejectCall(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcResumeCall(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcSendDtmf(int i, int i2, char c) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcStartDtmf(int i, int i2, char c) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallCmdResult cmcStopDtmf(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String getAvailableNetworkType(String str) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int[] getCallCount(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public CmcCallInfo getCmcCallInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsProfile[] getCurrentProfile() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int getEpsFbCallCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean getGlobalSettingsValueToBoolean(String str, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int getGlobalSettingsValueToInteger(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public String getGlobalSettingsValueToString(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public DialogEvent getLastDialogEvent(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public LastEndedImsCallInfo getLastEndedImsCallInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String getMasterStringValue(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int getMasterValue(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getNetworkType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getNrSaCallCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getPhoneCount() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public String getRcsProfileType(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsRegistration[] getRegistrationInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public ImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int getRttMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int getVideocallType() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean hasCrossSimImsService(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean hasVoLteSim() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean hasVoLteSimByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCmcEmergencyNumber(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimCallingSupported() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimCallingSupportedByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isCrossSimPermanentBlocked(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isForbidden() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isForbiddenByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isImsEnabled() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isImsEnabledByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isNonVerifiedMno(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isQSSSuccessAuthAndLogin(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isRcsEnabled() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isRegistered() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isRttCall(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isServiceEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isServiceEnabledByPhoneId(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isSupportVoWiFiDisable5GSA(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVoLteEnabled() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVoLteEnabledByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVolteEnabledFromNetwork(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVolteSupportECT() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public boolean isVolteSupportEctByPhoneId(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int registerAdhocProfile(ImsProfile imsProfile) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int registerAdhocProfileByPhoneId(ImsProfile imsProfile, int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public String registerAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerCmcCallEventListenerForSlot(int i, ICmcCallEventListener iCmcCallEventListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerCmcDialogListenerByToken(int i, ICmcDialogListener iCmcDialogListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerCmcRegistrationListenerForSlot(IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerCmsRegistrationListenerByPhoneId(ICentralMsgStoreService iCentralMsgStoreService, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerDialogEventListenerByToken(int i, IDialogEventListener iDialogEventListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerEpdgListener(IEpdgListener iEpdgListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImSessionListener(IImSessionListener iImSessionListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImSessionListenerByPhoneId(IImSessionListener iImSessionListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImsOngoingFtListener(IImsOngoingFtEventListener iImsOngoingFtEventListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImsOngoingFtListenerByPhoneId(IImsOngoingFtEventListener iImsOngoingFtEventListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerImsRegistrationListenerForSlot(IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public String registerSimMobilityStatusListenerByPhoneId(ISimMobilityStatusListener iSimMobilityStatusListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.IImsService
        public int setActiveImpu(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int setActiveMsisdn(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean setVideocallType(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int startDmConfig(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public boolean updateConfigValues(ContentValues contentValues, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.IImsService
        public int updateRegistration(ImsProfile imsProfile, int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.IImsService
        public void dump() throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendTryRegister() throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setIsimLoaded() throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setSimRefreshed() throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void changeAudioPath(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void deregisterAdhocProfile(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void enableRcs(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void enableVoLte(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void forcedUpdateRegistration(ImsProfile imsProfile) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerProfile(List list) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendRttMessage(String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendTryRegisterByPhoneId(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendTryRegisterCms(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void triggerAutoConfigurationForApp(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unRegisterEpdgListener(String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterCallback(ImsEventListener imsEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterImSessionListener(String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterImsOngoingFtListener(String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void changeAudioPathForSlot(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void deregisterAdhocProfileByPhoneId(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void deregisterProfile(List list, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void enableService(String str, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void enableVoLteByPhoneId(boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void finishDmConfig(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void forcedUpdateRegistrationByPhoneId(ImsProfile imsProfile, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerCallback(ImsEventListener imsEventListener, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerCmcRecordingListener(int i, ICmcRecordingListener iCmcRecordingListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerDialogEventListener(int i, IDialogEventListener iDialogEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void registerProfileByPhoneId(List list, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendDeregister(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendIidToken(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendMsisdnNumber(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendVerificationCode(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setAutomaticMode(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setCrossSimPermanentBlocked(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setNrInterworkingMode(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setProvisionedStringValue(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setProvisionedValue(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setRttMode(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void suspendRegister(boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void transferCall(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterAutoConfigurationListener(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterCmcCallEventListenerForSlot(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterCmcDialogListenerByToken(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterCmcRegistrationListenerForSlot(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterCmsRegistrationListenerByPhoneId(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterDialogEventListener(int i, IDialogEventListener iDialogEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterDialogEventListenerByToken(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterImSessionListenerByPhoneId(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterImsOngoingFtListenerByPhoneId(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterRttEventListener(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void unregisterSimMobilityStatusListenerByPhoneId(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void deregisterProfileByPhoneId(List list, boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void enableServiceByPhoneId(String str, boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void sendCmcRecordingEvent(int i, int i2, CmcRecordingInfo cmcRecordingInfo) throws RemoteException {
        }

        @Override // com.sec.ims.IImsService
        public void setEmergencyPdnInfo(String str, String[] strArr, String str2, int i) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IImsService {
        static final int TRANSACTION_changeAudioPath = 98;
        static final int TRANSACTION_changeAudioPathForSlot = 99;
        static final int TRANSACTION_cmcAnswerCall = 106;
        static final int TRANSACTION_cmcEndCall = 107;
        static final int TRANSACTION_cmcHoldCall = 110;
        static final int TRANSACTION_cmcMakeCall = 105;
        static final int TRANSACTION_cmcPullCall = 109;
        static final int TRANSACTION_cmcRejectCall = 108;
        static final int TRANSACTION_cmcResumeCall = 111;
        static final int TRANSACTION_cmcSendDtmf = 112;
        static final int TRANSACTION_cmcStartDtmf = 113;
        static final int TRANSACTION_cmcStopDtmf = 114;
        static final int TRANSACTION_deregisterAdhocProfile = 36;
        static final int TRANSACTION_deregisterAdhocProfileByPhoneId = 37;
        static final int TRANSACTION_deregisterProfile = 40;
        static final int TRANSACTION_deregisterProfileByPhoneId = 41;
        static final int TRANSACTION_dump = 134;
        static final int TRANSACTION_enableRcs = 88;
        static final int TRANSACTION_enableRcsByPhoneId = 89;
        static final int TRANSACTION_enableService = 84;
        static final int TRANSACTION_enableServiceByPhoneId = 85;
        static final int TRANSACTION_enableVoLte = 86;
        static final int TRANSACTION_enableVoLteByPhoneId = 87;
        static final int TRANSACTION_finishDmConfig = 120;
        static final int TRANSACTION_forcedUpdateRegistration = 45;
        static final int TRANSACTION_forcedUpdateRegistrationByPhoneId = 46;
        static final int TRANSACTION_getAvailableNetworkType = 12;
        static final int TRANSACTION_getCallCount = 90;
        static final int TRANSACTION_getCmcCallInfo = 102;
        static final int TRANSACTION_getConfigValues = 117;
        static final int TRANSACTION_getCurrentProfile = 31;
        static final int TRANSACTION_getCurrentProfileForSlot = 32;
        static final int TRANSACTION_getEpsFbCallCount = 91;
        static final int TRANSACTION_getGlobalSettingsValueToBoolean = 133;
        static final int TRANSACTION_getGlobalSettingsValueToInteger = 132;
        static final int TRANSACTION_getGlobalSettingsValueToString = 131;
        static final int TRANSACTION_getLastDialogEvent = 62;
        static final int TRANSACTION_getLastEndedImsCallInfo = 63;
        static final int TRANSACTION_getMasterStringValue = 67;
        static final int TRANSACTION_getMasterValue = 66;
        static final int TRANSACTION_getNetworkType = 11;
        static final int TRANSACTION_getNrSaCallCount = 92;
        static final int TRANSACTION_getPhoneCount = 3;
        static final int TRANSACTION_getRcsProfileType = 33;
        static final int TRANSACTION_getRegistrationInfo = 28;
        static final int TRANSACTION_getRegistrationInfoByPhoneId = 29;
        static final int TRANSACTION_getRegistrationInfoByServiceType = 30;
        static final int TRANSACTION_getRttMode = 124;
        static final int TRANSACTION_getVideocallType = 101;
        static final int TRANSACTION_hasCrossSimImsService = 144;
        static final int TRANSACTION_hasVoLteSim = 82;
        static final int TRANSACTION_hasVoLteSimByPhoneId = 83;
        static final int TRANSACTION_isCmcEmergencyCallSupported = 137;
        static final int TRANSACTION_isCmcEmergencyNumber = 138;
        static final int TRANSACTION_isCmcPotentialEmergencyNumber = 139;
        static final int TRANSACTION_isCrossSimCallingRegistered = 143;
        static final int TRANSACTION_isCrossSimCallingSupported = 146;
        static final int TRANSACTION_isCrossSimCallingSupportedByPhoneId = 145;
        static final int TRANSACTION_isCrossSimPermanentBlocked = 148;
        static final int TRANSACTION_isForbidden = 93;
        static final int TRANSACTION_isForbiddenByPhoneId = 94;
        static final int TRANSACTION_isImsEnabled = 70;
        static final int TRANSACTION_isImsEnabledByPhoneId = 71;
        static final int TRANSACTION_isNonVerifiedMno = 80;
        static final int TRANSACTION_isQSSSuccessAuthAndLogin = 50;
        static final int TRANSACTION_isRcsEnabled = 77;
        static final int TRANSACTION_isRegistered = 27;
        static final int TRANSACTION_isRttCall = 121;
        static final int TRANSACTION_isServiceAvailable = 79;
        static final int TRANSACTION_isServiceEnabled = 78;
        static final int TRANSACTION_isServiceEnabledByPhoneId = 81;
        static final int TRANSACTION_isSupportVoWiFiDisable5GSA = 142;
        static final int TRANSACTION_isVoLteEnabled = 72;
        static final int TRANSACTION_isVoLteEnabledByPhoneId = 73;
        static final int TRANSACTION_isVolteEnabledFromNetwork = 74;
        static final int TRANSACTION_isVolteSupportECT = 75;
        static final int TRANSACTION_isVolteSupportEctByPhoneId = 76;
        static final int TRANSACTION_registerAdhocProfile = 34;
        static final int TRANSACTION_registerAdhocProfileByPhoneId = 35;
        static final int TRANSACTION_registerAutoConfigurationListener = 21;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_registerCmcCallEventListenerForSlot = 103;
        static final int TRANSACTION_registerCmcDialogListenerByToken = 64;
        static final int TRANSACTION_registerCmcRecordingListener = 141;
        static final int TRANSACTION_registerCmcRegistrationListenerForSlot = 135;
        static final int TRANSACTION_registerCmsRegistrationListenerByPhoneId = 25;
        static final int TRANSACTION_registerDialogEventListener = 58;
        static final int TRANSACTION_registerDialogEventListenerByToken = 60;
        static final int TRANSACTION_registerDmValueListener = 115;
        static final int TRANSACTION_registerEpdgListener = 52;
        static final int TRANSACTION_registerImSessionListener = 13;
        static final int TRANSACTION_registerImSessionListenerByPhoneId = 14;
        static final int TRANSACTION_registerImsOngoingFtListener = 17;
        static final int TRANSACTION_registerImsOngoingFtListenerByPhoneId = 18;
        static final int TRANSACTION_registerImsRegistrationListener = 54;
        static final int TRANSACTION_registerImsRegistrationListenerForSlot = 56;
        static final int TRANSACTION_registerProfile = 38;
        static final int TRANSACTION_registerProfileByPhoneId = 39;
        static final int TRANSACTION_registerRttEventListener = 128;
        static final int TRANSACTION_registerSimMobilityStatusListenerByPhoneId = 23;
        static final int TRANSACTION_sendCmcRecordingEvent = 140;
        static final int TRANSACTION_sendDeregister = 47;
        static final int TRANSACTION_sendIidToken = 10;
        static final int TRANSACTION_sendMsisdnNumber = 9;
        static final int TRANSACTION_sendRttMessage = 125;
        static final int TRANSACTION_sendRttSessionModifyRequest = 127;
        static final int TRANSACTION_sendRttSessionModifyResponse = 126;
        static final int TRANSACTION_sendTryRegister = 42;
        static final int TRANSACTION_sendTryRegisterByPhoneId = 44;
        static final int TRANSACTION_sendTryRegisterCms = 43;
        static final int TRANSACTION_sendVerificationCode = 8;
        static final int TRANSACTION_setActiveImpu = 6;
        static final int TRANSACTION_setActiveMsisdn = 7;
        static final int TRANSACTION_setAutomaticMode = 122;
        static final int TRANSACTION_setCrossSimPermanentBlocked = 147;
        static final int TRANSACTION_setEmergencyPdnInfo = 51;
        static final int TRANSACTION_setIsimLoaded = 4;
        static final int TRANSACTION_setNrInterworkingMode = 149;
        static final int TRANSACTION_setProvisionedStringValue = 69;
        static final int TRANSACTION_setProvisionedValue = 68;
        static final int TRANSACTION_setRttMode = 123;
        static final int TRANSACTION_setSimRefreshed = 5;
        static final int TRANSACTION_setVideocallType = 100;
        static final int TRANSACTION_startDmConfig = 119;
        static final int TRANSACTION_startLocalRingBackTone = 96;
        static final int TRANSACTION_stopLocalRingBackTone = 97;
        static final int TRANSACTION_suspendRegister = 48;
        static final int TRANSACTION_transferCall = 95;
        static final int TRANSACTION_triggerAutoConfigurationForApp = 130;
        static final int TRANSACTION_unRegisterEpdgListener = 53;
        static final int TRANSACTION_unregisterAutoConfigurationListener = 22;
        static final int TRANSACTION_unregisterCallback = 2;
        static final int TRANSACTION_unregisterCmcCallEventListenerForSlot = 104;
        static final int TRANSACTION_unregisterCmcDialogListenerByToken = 65;
        static final int TRANSACTION_unregisterCmcRegistrationListenerForSlot = 136;
        static final int TRANSACTION_unregisterCmsRegistrationListenerByPhoneId = 26;
        static final int TRANSACTION_unregisterDialogEventListener = 59;
        static final int TRANSACTION_unregisterDialogEventListenerByToken = 61;
        static final int TRANSACTION_unregisterDmValueListener = 116;
        static final int TRANSACTION_unregisterImSessionListener = 15;
        static final int TRANSACTION_unregisterImSessionListenerByPhoneId = 16;
        static final int TRANSACTION_unregisterImsOngoingFtListener = 19;
        static final int TRANSACTION_unregisterImsOngoingFtListenerByPhoneId = 20;
        static final int TRANSACTION_unregisterImsRegistrationListener = 55;
        static final int TRANSACTION_unregisterImsRegistrationListenerForSlot = 57;
        static final int TRANSACTION_unregisterRttEventListener = 129;
        static final int TRANSACTION_unregisterSimMobilityStatusListenerByPhoneId = 24;
        static final int TRANSACTION_updateConfigValues = 118;
        static final int TRANSACTION_updateRegistration = 49;

        class Proxy implements IImsService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.IImsService
            public void changeAudioPath(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void changeAudioPathForSlot(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcAnswerCall(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcEndCall(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcHoldCall(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcMakeCall(int i, String str, CmcCallCmdInfo cmcCallCmdInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cmcCallCmdInfo, 0);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcPullCall(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcRejectCall(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcResumeCall(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcSendDtmf(int i, int i2, char c) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(c);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcStartDtmf(int i, int i2, char c) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(c);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallCmdResult cmcStopDtmf(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallCmdResult) parcelObtain2.readTypedObject(CmcCallCmdResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterAdhocProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterAdhocProfileByPhoneId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterProfile(List list, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeList(list);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void deregisterProfileByPhoneId(List list, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeList(list);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void dump() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableRcs(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableRcsByPhoneId(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableService(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableServiceByPhoneId(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableVoLte(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void enableVoLteByPhoneId(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void finishDmConfig(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void forcedUpdateRegistration(ImsProfile imsProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsProfile, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void forcedUpdateRegistrationByPhoneId(ImsProfile imsProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getAvailableNetworkType(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int[] getCallCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public CmcCallInfo getCmcCallInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CmcCallInfo) parcelObtain2.readTypedObject(CmcCallInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ContentValues getConfigValues(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContentValues) parcelObtain2.readTypedObject(ContentValues.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsProfile[] getCurrentProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsProfile[]) parcelObtain2.createTypedArray(ImsProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsProfile[] getCurrentProfileForSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsProfile[]) parcelObtain2.createTypedArray(ImsProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getEpsFbCallCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean getGlobalSettingsValueToBoolean(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getGlobalSettingsValueToInteger(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getGlobalSettingsValueToString(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsService.DESCRIPTOR;
            }

            @Override // com.sec.ims.IImsService
            public DialogEvent getLastDialogEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DialogEvent) parcelObtain2.readTypedObject(DialogEvent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public LastEndedImsCallInfo getLastEndedImsCallInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LastEndedImsCallInfo) parcelObtain2.readTypedObject(LastEndedImsCallInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getMasterStringValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getMasterValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getNetworkType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getNrSaCallCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getPhoneCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String getRcsProfileType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsRegistration[] getRegistrationInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsRegistration[]) parcelObtain2.createTypedArray(ImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsRegistration[]) parcelObtain2.createTypedArray(ImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public ImsRegistration getRegistrationInfoByServiceType(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsRegistration) parcelObtain2.readTypedObject(ImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getRttMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int getVideocallType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean hasCrossSimImsService(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean hasVoLteSim() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean hasVoLteSimByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCmcEmergencyNumber(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCmcPotentialEmergencyNumber(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimCallingRegistered(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimCallingSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimCallingSupportedByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isCrossSimPermanentBlocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isForbidden() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isForbiddenByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isImsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isImsEnabledByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isNonVerifiedMno(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isQSSSuccessAuthAndLogin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isRcsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isRegistered() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isRttCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isServiceAvailable(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isServiceEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isServiceEnabledByPhoneId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isSupportVoWiFiDisable5GSA(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVoLteEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVoLteEnabledByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVolteEnabledFromNetwork(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVolteSupportECT() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean isVolteSupportEctByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int registerAdhocProfile(ImsProfile imsProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsProfile, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int registerAdhocProfileByPhoneId(ImsProfile imsProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAutoConfigurationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerCallback(ImsEventListener imsEventListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(imsEventListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerCmcCallEventListenerForSlot(int i, ICmcCallEventListener iCmcCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCmcCallEventListener);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerCmcDialogListenerByToken(int i, ICmcDialogListener iCmcDialogListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCmcDialogListener);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerCmcRecordingListener(int i, ICmcRecordingListener iCmcRecordingListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCmcRecordingListener);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerCmcRegistrationListenerForSlot(IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerCmsRegistrationListenerByPhoneId(ICentralMsgStoreService iCentralMsgStoreService, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCentralMsgStoreService);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerDialogEventListener(int i, IDialogEventListener iDialogEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerDialogEventListenerByToken(int i, IDialogEventListener iDialogEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsDmConfigListener);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerEpdgListener(IEpdgListener iEpdgListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iEpdgListener);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImSessionListener(IImSessionListener iImSessionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImSessionListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImSessionListenerByPhoneId(IImSessionListener iImSessionListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImSessionListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImsOngoingFtListener(IImsOngoingFtEventListener iImsOngoingFtEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsOngoingFtEventListener);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImsOngoingFtListenerByPhoneId(IImsOngoingFtEventListener iImsOngoingFtEventListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsOngoingFtEventListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerImsRegistrationListenerForSlot(IImsRegistrationListener iImsRegistrationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerProfile(List list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeList(list);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void registerProfileByPhoneId(List list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRttEventListener);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public String registerSimMobilityStatusListenerByPhoneId(ISimMobilityStatusListener iSimMobilityStatusListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSimMobilityStatusListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendCmcRecordingEvent(int i, int i2, CmcRecordingInfo cmcRecordingInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(cmcRecordingInfo, 0);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendDeregister(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendIidToken(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendMsisdnNumber(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendRttMessage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendTryRegister() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendTryRegisterByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendTryRegisterCms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void sendVerificationCode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int setActiveImpu(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int setActiveMsisdn(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setAutomaticMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setCrossSimPermanentBlocked(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setEmergencyPdnInfo(String str, String[] strArr, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setIsimLoaded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setNrInterworkingMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setProvisionedStringValue(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setProvisionedValue(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setRttMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void setSimRefreshed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean setVideocallType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int startDmConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void suspendRegister(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void transferCall(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void triggerAutoConfigurationForApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unRegisterEpdgListener(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterAutoConfigurationListener(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCallback(ImsEventListener imsEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(imsEventListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCmcCallEventListenerForSlot(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCmcDialogListenerByToken(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCmcRegistrationListenerForSlot(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterCmsRegistrationListenerByPhoneId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterDialogEventListener(int i, IDialogEventListener iDialogEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterDialogEventListenerByToken(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterDmValueListener(IImsDmConfigListener iImsDmConfigListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsDmConfigListener);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImSessionListener(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImSessionListenerByPhoneId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsOngoingFtListener(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsOngoingFtListenerByPhoneId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterImsRegistrationListenerForSlot(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterRttEventListener(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public void unregisterSimMobilityStatusListenerByPhoneId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public boolean updateConfigValues(ContentValues contentValues, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contentValues, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsService
            public int updateRegistration(ImsProfile imsProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsService.DESCRIPTOR);
        }

        public static IImsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsService)) ? new Proxy(iBinder) : (IImsService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ImsEventListener imsEventListenerAsInterface = ImsEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCallback(imsEventListenerAsInterface, string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ImsEventListener imsEventListenerAsInterface2 = ImsEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(imsEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int phoneCount = getPhoneCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneCount);
                    return true;
                case 4:
                    setIsimLoaded();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    setSimRefreshed();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int activeImpu = setActiveImpu(i3, string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeImpu);
                    return true;
                case 7:
                    int i4 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int activeMsisdn = setActiveMsisdn(i4, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeMsisdn);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendVerificationCode(string6, i5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMsisdnNumber(string7, i6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string8 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendIidToken(string8, i7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int networkType = getNetworkType(i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkType);
                    return true;
                case 12:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String availableNetworkType = getAvailableNetworkType(string9);
                    parcel2.writeNoException();
                    parcel2.writeString(availableNetworkType);
                    return true;
                case 13:
                    IImSessionListener iImSessionListenerAsInterface = IImSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterImSessionListener = registerImSessionListener(iImSessionListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterImSessionListener);
                    return true;
                case 14:
                    IImSessionListener iImSessionListenerAsInterface2 = IImSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterImSessionListenerByPhoneId = registerImSessionListenerByPhoneId(iImSessionListenerAsInterface2, i9);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterImSessionListenerByPhoneId);
                    return true;
                case 15:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterImSessionListener(string10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string11 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImSessionListenerByPhoneId(string11, i10);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IImsOngoingFtEventListener iImsOngoingFtEventListenerAsInterface = IImsOngoingFtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterImsOngoingFtListener = registerImsOngoingFtListener(iImsOngoingFtEventListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterImsOngoingFtListener);
                    return true;
                case 18:
                    IImsOngoingFtEventListener iImsOngoingFtEventListenerAsInterface2 = IImsOngoingFtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterImsOngoingFtListenerByPhoneId = registerImsOngoingFtListenerByPhoneId(iImsOngoingFtEventListenerAsInterface2, i11);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterImsOngoingFtListenerByPhoneId);
                    return true;
                case 19:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterImsOngoingFtListener(string12);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string13 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImsOngoingFtListenerByPhoneId(string13, i12);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IAutoConfigurationListener iAutoConfigurationListenerAsInterface = IAutoConfigurationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterAutoConfigurationListener = registerAutoConfigurationListener(iAutoConfigurationListenerAsInterface, i13);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterAutoConfigurationListener);
                    return true;
                case 22:
                    String string14 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterAutoConfigurationListener(string14, i14);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    ISimMobilityStatusListener iSimMobilityStatusListenerAsInterface = ISimMobilityStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterSimMobilityStatusListenerByPhoneId = registerSimMobilityStatusListenerByPhoneId(iSimMobilityStatusListenerAsInterface, i15);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterSimMobilityStatusListenerByPhoneId);
                    return true;
                case 24:
                    String string15 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSimMobilityStatusListenerByPhoneId(string15, i16);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ICentralMsgStoreService iCentralMsgStoreServiceAsInterface = ICentralMsgStoreService.Stub.asInterface(parcel.readStrongBinder());
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterCmsRegistrationListenerByPhoneId = registerCmsRegistrationListenerByPhoneId(iCentralMsgStoreServiceAsInterface, i17);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterCmsRegistrationListenerByPhoneId);
                    return true;
                case 26:
                    String string16 = parcel.readString();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCmsRegistrationListenerByPhoneId(string16, i18);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    boolean zIsRegistered = isRegistered();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRegistered);
                    return true;
                case 28:
                    ImsRegistration[] registrationInfo = getRegistrationInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfo, 1);
                    return true;
                case 29:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsRegistration[] registrationInfoByPhoneId = getRegistrationInfoByPhoneId(i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfoByPhoneId, 1);
                    return true;
                case 30:
                    String string17 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsRegistration registrationInfoByServiceType = getRegistrationInfoByServiceType(string17, i20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registrationInfoByServiceType, 1);
                    return true;
                case 31:
                    ImsProfile[] currentProfile = getCurrentProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentProfile, 1);
                    return true;
                case 32:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsProfile[] currentProfileForSlot = getCurrentProfileForSlot(i21);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentProfileForSlot, 1);
                    return true;
                case 33:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String rcsProfileType = getRcsProfileType(i22);
                    parcel2.writeNoException();
                    parcel2.writeString(rcsProfileType);
                    return true;
                case 34:
                    ImsProfile imsProfile = (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRegisterAdhocProfile = registerAdhocProfile(imsProfile);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterAdhocProfile);
                    return true;
                case 35:
                    ImsProfile imsProfile2 = (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRegisterAdhocProfileByPhoneId = registerAdhocProfileByPhoneId(imsProfile2, i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterAdhocProfileByPhoneId);
                    return true;
                case 36:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deregisterAdhocProfile(i24);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deregisterAdhocProfileByPhoneId(i25, i26);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    registerProfile(arrayList);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    ArrayList arrayList2 = parcel.readArrayList(getClass().getClassLoader());
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerProfileByPhoneId(arrayList2, i27);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    ArrayList arrayList3 = parcel.readArrayList(getClass().getClassLoader());
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    deregisterProfile(arrayList3, z);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    ArrayList arrayList4 = parcel.readArrayList(getClass().getClassLoader());
                    boolean z2 = parcel.readBoolean();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deregisterProfileByPhoneId(arrayList4, z2, i28);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    sendTryRegister();
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterCms(i29);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterByPhoneId(i30);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ImsProfile imsProfile3 = (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    forcedUpdateRegistration(imsProfile3);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ImsProfile imsProfile4 = (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forcedUpdateRegistrationByPhoneId(imsProfile4, i31);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendDeregister(i32, i33);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean z3 = parcel.readBoolean();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    suspendRegister(z3, i34);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    ImsProfile imsProfile5 = (ImsProfile) parcel.readTypedObject(ImsProfile.CREATOR);
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUpdateRegistration = updateRegistration(imsProfile5, i35);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateRegistration);
                    return true;
                case 50:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsQSSSuccessAuthAndLogin = isQSSSuccessAuthAndLogin(i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsQSSSuccessAuthAndLogin);
                    return true;
                case 51:
                    String string18 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String string19 = parcel.readString();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEmergencyPdnInfo(string18, strArrCreateStringArray, string19, i37);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    IEpdgListener iEpdgListenerAsInterface = IEpdgListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterEpdgListener = registerEpdgListener(iEpdgListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterEpdgListener);
                    return true;
                case 53:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unRegisterEpdgListener(string20);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationListener(iImsRegistrationListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListener(iImsRegistrationListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface3 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterImsRegistrationListenerForSlot = registerImsRegistrationListenerForSlot(iImsRegistrationListenerAsInterface3, i38);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterImsRegistrationListenerForSlot);
                    return true;
                case 57:
                    String string21 = parcel.readString();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListenerForSlot(string21, i39);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int i40 = parcel.readInt();
                    IDialogEventListener iDialogEventListenerAsInterface = IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDialogEventListener(i40, iDialogEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int i41 = parcel.readInt();
                    IDialogEventListener iDialogEventListenerAsInterface2 = IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDialogEventListener(i41, iDialogEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i42 = parcel.readInt();
                    IDialogEventListener iDialogEventListenerAsInterface3 = IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterDialogEventListenerByToken = registerDialogEventListenerByToken(i42, iDialogEventListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterDialogEventListenerByToken);
                    return true;
                case 61:
                    int i43 = parcel.readInt();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterDialogEventListenerByToken(i43, string22);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DialogEvent lastDialogEvent = getLastDialogEvent(i44);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastDialogEvent, 1);
                    return true;
                case 63:
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LastEndedImsCallInfo lastEndedImsCallInfo = getLastEndedImsCallInfo(i45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastEndedImsCallInfo, 1);
                    return true;
                case 64:
                    int i46 = parcel.readInt();
                    ICmcDialogListener iCmcDialogListenerAsInterface = ICmcDialogListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterCmcDialogListenerByToken = registerCmcDialogListenerByToken(i46, iCmcDialogListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterCmcDialogListenerByToken);
                    return true;
                case 65:
                    int i47 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterCmcDialogListenerByToken(i47, string23);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int masterValue = getMasterValue(i48);
                    parcel2.writeNoException();
                    parcel2.writeInt(masterValue);
                    return true;
                case 67:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String masterStringValue = getMasterStringValue(i49);
                    parcel2.writeNoException();
                    parcel2.writeString(masterStringValue);
                    return true;
                case 68:
                    int i50 = parcel.readInt();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProvisionedValue(i50, i51);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int i52 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProvisionedStringValue(i52, string24);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    boolean zIsImsEnabled = isImsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImsEnabled);
                    return true;
                case 71:
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsImsEnabledByPhoneId = isImsEnabledByPhoneId(i53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImsEnabledByPhoneId);
                    return true;
                case 72:
                    boolean zIsVoLteEnabled = isVoLteEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoLteEnabled);
                    return true;
                case 73:
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVoLteEnabledByPhoneId = isVoLteEnabledByPhoneId(i54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoLteEnabledByPhoneId);
                    return true;
                case 74:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVolteEnabledFromNetwork = isVolteEnabledFromNetwork(i55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVolteEnabledFromNetwork);
                    return true;
                case 75:
                    boolean zIsVolteSupportECT = isVolteSupportECT();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVolteSupportECT);
                    return true;
                case 76:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVolteSupportEctByPhoneId = isVolteSupportEctByPhoneId(i56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVolteSupportEctByPhoneId);
                    return true;
                case 77:
                    boolean zIsRcsEnabled = isRcsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRcsEnabled);
                    return true;
                case 78:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsServiceEnabled = isServiceEnabled(string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsServiceEnabled);
                    return true;
                case 79:
                    String string26 = parcel.readString();
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsServiceAvailable = isServiceAvailable(string26, i57, i58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsServiceAvailable);
                    return true;
                case 80:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNonVerifiedMno = isNonVerifiedMno(i59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNonVerifiedMno);
                    return true;
                case 81:
                    String string27 = parcel.readString();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsServiceEnabledByPhoneId = isServiceEnabledByPhoneId(string27, i60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsServiceEnabledByPhoneId);
                    return true;
                case 82:
                    boolean zHasVoLteSim = hasVoLteSim();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasVoLteSim);
                    return true;
                case 83:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasVoLteSimByPhoneId = hasVoLteSimByPhoneId(i61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasVoLteSimByPhoneId);
                    return true;
                case 84:
                    String string28 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableService(string28, z4);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String string29 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableServiceByPhoneId(string29, z5, i62);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableVoLte(z6);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    boolean z7 = parcel.readBoolean();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableVoLteByPhoneId(z7, i63);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableRcs(z8);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    boolean z9 = parcel.readBoolean();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableRcsByPhoneId(z9, i64);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] callCount = getCallCount(i65);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(callCount);
                    return true;
                case 91:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int epsFbCallCount = getEpsFbCallCount(i66);
                    parcel2.writeNoException();
                    parcel2.writeInt(epsFbCallCount);
                    return true;
                case 92:
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nrSaCallCount = getNrSaCallCount(i67);
                    parcel2.writeNoException();
                    parcel2.writeInt(nrSaCallCount);
                    return true;
                case 93:
                    boolean zIsForbidden = isForbidden();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsForbidden);
                    return true;
                case 94:
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsForbiddenByPhoneId = isForbiddenByPhoneId(i68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsForbiddenByPhoneId);
                    return true;
                case 95:
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferCall(string30, string31);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    int i69 = parcel.readInt();
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartLocalRingBackTone = startLocalRingBackTone(i69, i70, i71);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartLocalRingBackTone);
                    return true;
                case 97:
                    int iStopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopLocalRingBackTone);
                    return true;
                case 98:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(i72);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    int i73 = parcel.readInt();
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPathForSlot(i73, i74);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean videocallType = setVideocallType(i75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(videocallType);
                    return true;
                case 101:
                    int videocallType2 = getVideocallType();
                    parcel2.writeNoException();
                    parcel2.writeInt(videocallType2);
                    return true;
                case 102:
                    CmcCallInfo cmcCallInfo = getCmcCallInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallInfo, 1);
                    return true;
                case 103:
                    int i76 = parcel.readInt();
                    ICmcCallEventListener iCmcCallEventListenerAsInterface = ICmcCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterCmcCallEventListenerForSlot = registerCmcCallEventListenerForSlot(i76, iCmcCallEventListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterCmcCallEventListenerForSlot);
                    return true;
                case 104:
                    int i77 = parcel.readInt();
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterCmcCallEventListenerForSlot(i77, string32);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    int i78 = parcel.readInt();
                    String string33 = parcel.readString();
                    CmcCallCmdInfo cmcCallCmdInfo = (CmcCallCmdInfo) parcel.readTypedObject(CmcCallCmdInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcMakeCall = cmcMakeCall(i78, string33, cmcCallCmdInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcMakeCall, 1);
                    return true;
                case 106:
                    int i79 = parcel.readInt();
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcAnswerCall = cmcAnswerCall(i79, i80);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcAnswerCall, 1);
                    return true;
                case 107:
                    int i81 = parcel.readInt();
                    int i82 = parcel.readInt();
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcEndCall = cmcEndCall(i81, i82, i83);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcEndCall, 1);
                    return true;
                case 108:
                    int i84 = parcel.readInt();
                    int i85 = parcel.readInt();
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcRejectCall = cmcRejectCall(i84, i85, i86);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcRejectCall, 1);
                    return true;
                case 109:
                    int i87 = parcel.readInt();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcPullCall = cmcPullCall(i87, string34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcPullCall, 1);
                    return true;
                case 110:
                    int i88 = parcel.readInt();
                    int i89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcHoldCall = cmcHoldCall(i88, i89);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcHoldCall, 1);
                    return true;
                case 111:
                    int i90 = parcel.readInt();
                    int i91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcResumeCall = cmcResumeCall(i90, i91);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcResumeCall, 1);
                    return true;
                case 112:
                    int i92 = parcel.readInt();
                    int i93 = parcel.readInt();
                    char c = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcSendDtmf = cmcSendDtmf(i92, i93, c);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcSendDtmf, 1);
                    return true;
                case 113:
                    int i94 = parcel.readInt();
                    int i95 = parcel.readInt();
                    char c2 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcStartDtmf = cmcStartDtmf(i94, i95, c2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcStartDtmf, 1);
                    return true;
                case 114:
                    int i96 = parcel.readInt();
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CmcCallCmdResult cmcCallCmdResultCmcStopDtmf = cmcStopDtmf(i96, i97);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cmcCallCmdResultCmcStopDtmf, 1);
                    return true;
                case 115:
                    IImsDmConfigListener iImsDmConfigListenerAsInterface = IImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDmValueListener(iImsDmConfigListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    IImsDmConfigListener iImsDmConfigListenerAsInterface2 = IImsDmConfigListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDmValueListener(iImsDmConfigListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    int i98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContentValues configValues = getConfigValues(strArrCreateStringArray2, i98);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configValues, 1);
                    return true;
                case 118:
                    ContentValues contentValues = (ContentValues) parcel.readTypedObject(ContentValues.CREATOR);
                    int i99 = parcel.readInt();
                    int i100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateConfigValues = updateConfigValues(contentValues, i99, i100);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateConfigValues);
                    return true;
                case 119:
                    int i101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartDmConfig = startDmConfig(i101);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartDmConfig);
                    return true;
                case 120:
                    int i102 = parcel.readInt();
                    int i103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishDmConfig(i102, i103);
                    parcel2.writeNoException();
                    return true;
                case 121:
                    int i104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRttCall = isRttCall(i104);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRttCall);
                    return true;
                case 122:
                    int i105 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutomaticMode(i105, z10);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    int i106 = parcel.readInt();
                    int i107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRttMode(i106, i107);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    int i108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rttMode = getRttMode(i108);
                    parcel2.writeNoException();
                    parcel2.writeInt(rttMode);
                    return true;
                case 125:
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRttMessage(string35);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    int i109 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyResponse(i109, z11);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    int i110 = parcel.readInt();
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyRequest(i110, z12);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    int i111 = parcel.readInt();
                    IRttEventListener iRttEventListenerAsInterface = IRttEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    String strRegisterRttEventListener = registerRttEventListener(i111, iRttEventListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterRttEventListener);
                    return true;
                case 129:
                    int i112 = parcel.readInt();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterRttEventListener(i112, string36);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    int i113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerAutoConfigurationForApp(i113);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    String string37 = parcel.readString();
                    int i114 = parcel.readInt();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String globalSettingsValueToString = getGlobalSettingsValueToString(string37, i114, string38);
                    parcel2.writeNoException();
                    parcel2.writeString(globalSettingsValueToString);
                    return true;
                case 132:
                    String string39 = parcel.readString();
                    int i115 = parcel.readInt();
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int globalSettingsValueToInteger = getGlobalSettingsValueToInteger(string39, i115, i116);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalSettingsValueToInteger);
                    return true;
                case 133:
                    String string40 = parcel.readString();
                    int i117 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean globalSettingsValueToBoolean = getGlobalSettingsValueToBoolean(string40, i117, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(globalSettingsValueToBoolean);
                    return true;
                case 134:
                    dump();
                    parcel2.writeNoException();
                    return true;
                case 135:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface4 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterCmcRegistrationListenerForSlot = registerCmcRegistrationListenerForSlot(iImsRegistrationListenerAsInterface4, i118);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterCmcRegistrationListenerForSlot);
                    return true;
                case 136:
                    String string41 = parcel.readString();
                    int i119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCmcRegistrationListenerForSlot(string41, i119);
                    parcel2.writeNoException();
                    return true;
                case 137:
                    int i120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCmcEmergencyCallSupported = isCmcEmergencyCallSupported(i120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCmcEmergencyCallSupported);
                    return true;
                case 138:
                    String string42 = parcel.readString();
                    int i121 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCmcEmergencyNumber = isCmcEmergencyNumber(string42, i121);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCmcEmergencyNumber);
                    return true;
                case 139:
                    String string43 = parcel.readString();
                    int i122 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCmcPotentialEmergencyNumber = isCmcPotentialEmergencyNumber(string43, i122);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCmcPotentialEmergencyNumber);
                    return true;
                case 140:
                    int i123 = parcel.readInt();
                    int i124 = parcel.readInt();
                    CmcRecordingInfo cmcRecordingInfo = (CmcRecordingInfo) parcel.readTypedObject(CmcRecordingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCmcRecordingEvent(i123, i124, cmcRecordingInfo);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    int i125 = parcel.readInt();
                    ICmcRecordingListener iCmcRecordingListenerAsInterface = ICmcRecordingListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCmcRecordingListener(i125, iCmcRecordingListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    int i126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSupportVoWiFiDisable5GSA = isSupportVoWiFiDisable5GSA(i126);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportVoWiFiDisable5GSA);
                    return true;
                case 143:
                    int i127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCrossSimCallingRegistered = isCrossSimCallingRegistered(i127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCrossSimCallingRegistered);
                    return true;
                case 144:
                    int i128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasCrossSimImsService = hasCrossSimImsService(i128);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCrossSimImsService);
                    return true;
                case 145:
                    int i129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCrossSimCallingSupportedByPhoneId = isCrossSimCallingSupportedByPhoneId(i129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCrossSimCallingSupportedByPhoneId);
                    return true;
                case 146:
                    boolean zIsCrossSimCallingSupported = isCrossSimCallingSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCrossSimCallingSupported);
                    return true;
                case 147:
                    int i130 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossSimPermanentBlocked(i130, z14);
                    parcel2.writeNoException();
                    return true;
                case 148:
                    int i131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCrossSimPermanentBlocked = isCrossSimPermanentBlocked(i131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCrossSimPermanentBlocked);
                    return true;
                case 149:
                    int i132 = parcel.readInt();
                    int i133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNrInterworkingMode(i132, i133);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
