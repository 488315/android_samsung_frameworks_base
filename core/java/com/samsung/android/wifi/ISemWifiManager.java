package com.samsung.android.wifi;

import android.content.pm.ParceledListSlice;
import android.net.wifi.SoftApConfiguration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public interface ISemWifiManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiManager";

    boolean addOrUpdateNetwork(SemWifiConfiguration semWifiConfiguration) throws RemoteException;

    void addOrUpdateWifiControlHistory(String str, boolean z) throws RemoteException;

    void allowAutojoinPasspoint(String str, boolean z) throws RemoteException;

    int autohotspotWifiScanConnect(String str, String str2, String str3, int i, int i2, int i3)
            throws RemoteException;

    void blockFccChannelBackoff(boolean z) throws RemoteException;

    boolean canAutoHotspotBeEnabled() throws RemoteException;

    int canSmartMHSLocked() throws RemoteException;

    void checkAppForWiFiOffloading(String str) throws RemoteException;

    void clearAutoHotspotLists() throws RemoteException;

    int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4)
            throws RemoteException;

    boolean connectToSmartD2DClient(
            String str, String str2, ISemWifiApSmartCallback iSemWifiApSmartCallback)
            throws RemoteException;

    boolean connectToSmartMHS(
            String str, int i, int i2, int i3, String str2, String str3, int i4, boolean z)
            throws RemoteException;

    void disableRandomMac() throws RemoteException;

    boolean disconnectApBlockAutojoin(boolean z) throws RemoteException;

    void enableHotspotTsfInfo(boolean z) throws RemoteException;

    void enableTxPowerLogging(boolean z, int i) throws RemoteException;

    void externalTwtInterface(int i, String str) throws RemoteException;

    void factoryReset() throws RemoteException;

    List<SemAbTestConfiguration> getAbTestConfigs() throws RemoteException;

    SemAbTestConfiguration getAbTestConfiguredModule(String str) throws RemoteException;

    int getAdvancedAutohotspotConnectSettings() throws RemoteException;

    int getAdvancedAutohotspotLCDSettings() throws RemoteException;

    String getAntInfo() throws RemoteException;

    String getAutoShareDump() throws RemoteException;

    boolean getAutoWifiDefaultValue() throws RemoteException;

    String getAutoWifiDump() throws RemoteException;

    int getChannelUtilization() throws RemoteException;

    Map getChannelUtilizationExtended() throws RemoteException;

    Map getConfiguredNetworkLocations() throws RemoteException;

    ParceledListSlice getConfiguredNetworks() throws RemoteException;

    String getConnectivityLog(String str) throws RemoteException;

    String getCountryCode() throws RemoteException;

    String getCountryRev() throws RemoteException;

    Map getCtlFeatureState() throws RemoteException;

    int getCurrentL2TransitionMode() throws RemoteException;

    String getCurrentStateAndEnterTime() throws RemoteException;

    int getCurrentStatusMode() throws RemoteException;

    Bundle getCurrentWifiRouterInfo() throws RemoteException;

    String getDailyUsageInfo(int i) throws RemoteException;

    long[] getDataConsumedValues() throws RemoteException;

    String getDcxoCalibrationData() throws RemoteException;

    List<String> getDiagnosisResults() throws RemoteException;

    String getDynamicFeatureStatus() throws RemoteException;

    Map<String, SemEasySetupWifiScanSettings> getEasySetupScanSettings() throws RemoteException;

    String getFactoryMacAddress() throws RemoteException;

    String getFrameburstInfo() throws RemoteException;

    int getHotspotAntMode() throws RemoteException;

    String getIWCQTables() throws RemoteException;

    int getIndoorStatus() throws RemoteException;

    boolean getIsPacketCaptureSupportedByDriver() throws RemoteException;

    String getIssueDetectorDump(int i) throws RemoteException;

    String getIwhState() throws RemoteException;

    String getL2TransitionLog() throws RemoteException;

    List<String> getMHSClientTrafficDetails() throws RemoteException;

    String getMHSConfig(String str) throws RemoteException;

    String getMHSMacFromInterface() throws RemoteException;

    int getMaxTdlsSession() throws RemoteException;

    int getMcfConnectedStatus(String str) throws RemoteException;

    int getMcfConnectedStatusFromScanResult(String str) throws RemoteException;

    List<SemWifiApBleScanResult> getMcfScanDetail() throws RemoteException;

    List<String> getMonthlyDataUsage() throws RemoteException;

    int getNRTTrafficbandwidth() throws RemoteException;

    Map getNetworkLastUpdatedTimeMap() throws RemoteException;

    long[] getNetworkUsageInfo(String str) throws RemoteException;

    int getNumOfTdlsSession() throws RemoteException;

    int getNumOfWifiAnt() throws RemoteException;

    String getNumberOfDataInEachRssiLevel() throws RemoteException;

    int getOptimizerForceControlMode() throws RemoteException;

    int[] getOptimizerState() throws RemoteException;

    List getPasspointConfigurations() throws RemoteException;

    String getProfileShareDump() throws RemoteException;

    int getProvisionSuccess() throws RemoteException;

    String getPsmInfo() throws RemoteException;

    Map getQoSScores(List<String> list) throws RemoteException;

    int getRVFModeStatus() throws RemoteException;

    int getRoamBand() throws RemoteException;

    int getRoamDelta() throws RemoteException;

    int getRoamScanPeriod() throws RemoteException;

    int getRoamTrigger() throws RemoteException;

    int getRssi(String str) throws RemoteException;

    boolean getSamsungIwhCtrl() throws RemoteException;

    boolean getSamsungMloCtrl() throws RemoteException;

    int[] getServiceDetectionResult() throws RemoteException;

    String getSilentRoamingDump(int i) throws RemoteException;

    int getSmartApConnectedStatus(String str) throws RemoteException;

    int getSmartApConnectedStatusFromScanResult(String str) throws RemoteException;

    int getSmartD2DClientConnectedStatus(String str) throws RemoteException;

    int getSmartMHSLockStatus() throws RemoteException;

    int[] getSoftApBands() throws RemoteException;

    SoftApConfiguration getSoftApConfiguration() throws RemoteException;

    int getSoftApFreq() throws RemoteException;

    int getSoftApSecurityType() throws RemoteException;

    int getSoftApUpStreamNetworkType() throws RemoteException;

    String getStationInfo(String str) throws RemoteException;

    int[] getTWTParams() throws RemoteException;

    Map getTasAverage() throws RemoteException;

    String getTcpMonitorAllSocketHistory(int i) throws RemoteException;

    String getTcpMonitorDnsHistory(int i) throws RemoteException;

    String getTcpMonitorSocketForegroundHistory(int i) throws RemoteException;

    List<SemWifiApClientDetails> getTopHotspotClientsToday(int i, int i2) throws RemoteException;

    String getTopHotspotClientsTodayAsString(int i, int i2) throws RemoteException;

    List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(long j, long j2)
            throws RemoteException;

    String getTxPower() throws RemoteException;

    int getValidState() throws RemoteException;

    String getVendorWlanDriverProp(String str) throws RemoteException;

    int getWcmEverQualityTested() throws RemoteException;

    String getWifi7DisabledCountry() throws RemoteException;

    List<SemWifiApBleScanResult> getWifiApBleD2DScanDetail() throws RemoteException;

    List<SemWifiApBleScanResult> getWifiApBleScanDetail() throws RemoteException;

    int getWifiApChannel() throws RemoteException;

    SemWifiApClientDetails getWifiApClientDetails(String str) throws RemoteException;

    int getWifiApConnectedStationCount() throws RemoteException;

    long getWifiApDailyDataLimit() throws RemoteException;

    int getWifiApFreq() throws RemoteException;

    String getWifiApGuestPassword() throws RemoteException;

    String getWifiApHostapdFreq() throws RemoteException;

    String getWifiApHostapdSecurtiy() throws RemoteException;

    String getWifiApInterfaceName() throws RemoteException;

    List<String> getWifiApInterfaceNames() throws RemoteException;

    boolean getWifiApIsolate() throws RemoteException;

    int getWifiApLOHSState() throws RemoteException;

    int getWifiApMacAclMode() throws RemoteException;

    int getWifiApMaxClient() throws RemoteException;

    int getWifiApMaxClientFromFramework() throws RemoteException;

    String getWifiApStaList() throws RemoteException;

    List<String> getWifiApStaListDetail() throws RemoteException;

    int getWifiApState() throws RemoteException;

    long getWifiApTodaysTotalDataUsage() throws RemoteException;

    int getWifiApWarningActivityRunningState() throws RemoteException;

    boolean getWifiApWpsPbc() throws RemoteException;

    String getWifiCid() throws RemoteException;

    String getWifiEnableHistory() throws RemoteException;

    String getWifiFirmwareVersion() throws RemoteException;

    int getWifiIconVisibility() throws RemoteException;

    String getWifiMACAddress() throws RemoteException;

    Bundle getWifiRouterInfo(String str) throws RemoteException;

    String getWifiRouterInfoBestEffort(String str) throws RemoteException;

    String getWifiRouterInfoBestEffortByBssid(String str) throws RemoteException;

    Bundle getWifiRouterInfoByBssid(String str) throws RemoteException;

    String getWifiRouterInfoPresentable(String str) throws RemoteException;

    String getWifiRouterInfoPresentableByBssid(String str) throws RemoteException;

    String getWifiStaInfo() throws RemoteException;

    String getWifiSupportedFeatureSet() throws RemoteException;

    String getWifiUsabilityStatsEntry(int i) throws RemoteException;

    String getWifiVersions() throws RemoteException;

    boolean hasConfiguredNetworkLocations(String str) throws RemoteException;

    boolean isAvailableAutoWifiScan() throws RemoteException;

    boolean isAvailableTdls() throws RemoteException;

    int isCaptureRunning() throws RemoteException;

    void isClientAcceptedWifiProfileSharing(boolean z) throws RemoteException;

    int isDataSaverEnabled() throws RemoteException;

    boolean isGripSensorMonitorEnabled() throws RemoteException;

    boolean isIndividualAppSupported() throws RemoteException;

    boolean isMCFClientAutohotspotSupported() throws RemoteException;

    boolean isNCHOModeEnabled() throws RemoteException;

    boolean isNeededToShowWifiApDatalimitReachedDialog() throws RemoteException;

    boolean isOverAllMhsDataLimitReached() throws RemoteException;

    boolean isOverAllMhsDataLimitSet() throws RemoteException;

    boolean isP2pConnected() throws RemoteException;

    boolean isSAFamilySupportedBasedOnCountry() throws RemoteException;

    boolean isScanningEnabled() throws RemoteException;

    int isSoftAp6ENetwork() throws RemoteException;

    int isSoftap11axEnabled() throws RemoteException;

    boolean isSupportedAutoWifi() throws RemoteException;

    boolean isSupportedProfileRequest() throws RemoteException;

    boolean isSupportedQoSProvider() throws RemoteException;

    boolean isSwitchToMobileDataDefaultOff() throws RemoteException;

    boolean isUploadModeEnabled() throws RemoteException;

    boolean isWesModeEnabled() throws RemoteException;

    boolean isWiderBandwidthTdlsSupported() throws RemoteException;

    boolean isWifiApEnabled() throws RemoteException;

    boolean isWifiApEnabledWithDualBand() throws RemoteException;

    boolean isWifiApGuestClient(String str) throws RemoteException;

    boolean isWifiApGuestModeEnabled() throws RemoteException;

    boolean isWifiApGuestModeIsolationEnabled() throws RemoteException;

    boolean isWifiApMacAclEnabled() throws RemoteException;

    boolean isWifiApWpa3Supported() throws RemoteException;

    boolean isWifiDeveloperModeEnabled() throws RemoteException;

    boolean isWifiSharingEnabled() throws RemoteException;

    boolean isWifiSharingLiteSupported() throws RemoteException;

    boolean isWifiSharingSupported() throws RemoteException;

    boolean iwhIntendedDisconnection() throws RemoteException;

    void launchWifiApWarningForMcfMHS(int i, int i2, boolean z) throws RemoteException;

    boolean linkQosQuery(long j, long j2, long j3, int i, long j4) throws RemoteException;

    int manageWifiApMacAclList(String str, String str2, int i, int i2) throws RemoteException;

    void notifyConnect(int i, String str) throws RemoteException;

    void notifyReachabilityLost() throws RemoteException;

    List<String> readWifiApMacAclList(int i) throws RemoteException;

    void registerAbTestConfigUpdateObserver(
            ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver, String str)
            throws RemoteException;

    void registerClientDataUsageCallback(
            IBinder iBinder,
            ISemWifiApClientUpdateCallback iSemWifiApClientUpdateCallback,
            int i,
            String str)
            throws RemoteException;

    void registerClientListDataUsageCallback(
            IBinder iBinder,
            ISemWifiApClientListUpdateCallback iSemWifiApClientListUpdateCallback,
            int i,
            int i2,
            int i3)
            throws RemoteException;

    void registerPasswordCallback(String str, ISemSharedPasswordCallback iSemSharedPasswordCallback)
            throws RemoteException;

    void registerTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener)
            throws RemoteException;

    void registerWifiApDataUsageCallback(
            IBinder iBinder, ISemWifiApDataUsageCallback iSemWifiApDataUsageCallback, int i)
            throws RemoteException;

    void registerWifiApSmartCallback(
            IBinder iBinder, ISemWifiApSmartCallback iSemWifiApSmartCallback, int i)
            throws RemoteException;

    void removeExcludedNetwork(int i) throws RemoteException;

    boolean removeFactoryMacAddress() throws RemoteException;

    boolean removeNetwork(String str) throws RemoteException;

    boolean removePktlogFilter(String str, String str2) throws RemoteException;

    void reportAbTestResult(String str, String str2, String str3) throws RemoteException;

    void reportBigData(String str, String str2) throws RemoteException;

    void reportHotspotDumpLogs(String str) throws RemoteException;

    void reportIssue(int i, Bundle bundle) throws RemoteException;

    void requestPassword(boolean z) throws RemoteException;

    void requestStopAutohotspotAdvertisement(boolean z) throws RemoteException;

    void resetCallbackCondition(int i) throws RemoteException;

    void resetComebackCondition() throws RemoteException;

    void resetDeveloperOptionsSettings() throws RemoteException;

    void resetSoftAp(Message message) throws RemoteException;

    void resetTotalPriorityDataConsumedValues() throws RemoteException;

    void restoreIWCSettingsValue(int i, int i2) throws RemoteException;

    void restoreSemConfigurationsBackupData(String str) throws RemoteException;

    String retrieveSemWifiConfigsBackupData() throws RemoteException;

    void runAutoShareForCurrent(List<String> list) throws RemoteException;

    String runIptablesRulesCommand(String str) throws RemoteException;

    boolean saveFwDump() throws RemoteException;

    boolean sendReassociationFrequencyRequestFrame(String str, int i) throws RemoteException;

    boolean sendReassociationRequestFrame(String str, int i) throws RemoteException;

    boolean sendVendorSpecificActionFrame(String str, int i, int i2, String str2)
            throws RemoteException;

    void set5GmmWaveSarBackoffEnabled(boolean z) throws RemoteException;

    void setAdvancedAutohotspotConnectSettings(int i) throws RemoteException;

    void setAdvancedAutohotspotLCDSettings(int i) throws RemoteException;

    void setAllowWifiScan(boolean z, String str) throws RemoteException;

    boolean setAntInfo(String str) throws RemoteException;

    void setAntMode(int i) throws RemoteException;

    void setArdkPowerSaveMode(boolean z) throws RemoteException;

    void setAutohotspotToastMessage(int i) throws RemoteException;

    void setBtmOptionUserDisabled(String str) throws RemoteException;

    void setBtmOptionUserEnabled(String str) throws RemoteException;

    void setConnectionAttemptInfo(int i, boolean z, String str) throws RemoteException;

    void setConnectivityCheckDisabled(boolean z) throws RemoteException;

    boolean setCountryRev(String str) throws RemoteException;

    boolean setDcxoCalibrationData(String str) throws RemoteException;

    void setDtimInSuspendMode(int i) throws RemoteException;

    void setEasySetupScanSettings(
            String str, SemEasySetupWifiScanSettings semEasySetupWifiScanSettings)
            throws RemoteException;

    boolean setFactoryMacAddress(String str) throws RemoteException;

    void setFccChannelBackoffEnabled(String str, boolean z) throws RemoteException;

    boolean setFrameburstInfo(String str) throws RemoteException;

    void setGripSensorMonitorEnabled(boolean z) throws RemoteException;

    void setHotspotAntMode(int i) throws RemoteException;

    void setIWCMockAction(int i) throws RemoteException;

    void setIWCQTables(String str) throws RemoteException;

    void setIlaTrainingResult(double d, String str) throws RemoteException;

    void setImsCallEstablished(boolean z) throws RemoteException;

    void setKeepConnection(boolean z, boolean z2) throws RemoteException;

    void setKeepConnectionAlways(boolean z) throws RemoteException;

    void setKeepConnectionBigData(int i) throws RemoteException;

    boolean setLatencyCritical(String str, int i) throws RemoteException;

    boolean setLocalOnlyHotspotEnabled(boolean z, String str, String str2, int i)
            throws RemoteException;

    String setMHSConfig(String str) throws RemoteException;

    void setMaxDtimInSuspendMode(boolean z) throws RemoteException;

    void setMcfMultiControlMode(boolean z) throws RemoteException;

    void setMhsAiServiceNsdResult(int[] iArr, String[] strArr) throws RemoteException;

    void setMhsAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException;

    boolean setNCHOModeEnabled(boolean z) throws RemoteException;

    boolean setOptimizerForceControlMode(int i) throws RemoteException;

    boolean setPktlogFilter(String str, String str2) throws RemoteException;

    void setPowerSavingTime(int i) throws RemoteException;

    boolean setProvisionSuccess(boolean z) throws RemoteException;

    boolean setPsmInfo(String str) throws RemoteException;

    void setRVFmodeStatus(int i) throws RemoteException;

    boolean setRoamBand(int i) throws RemoteException;

    boolean setRoamDelta(int i) throws RemoteException;

    boolean setRoamScanChannels(String[] strArr) throws RemoteException;

    boolean setRoamScanEnabled(boolean z) throws RemoteException;

    boolean setRoamScanPeriod(int i) throws RemoteException;

    boolean setRoamTrigger(int i) throws RemoteException;

    void setSamsungIwhCtrl(boolean z) throws RemoteException;

    void setSamsungMloCtrl(boolean z) throws RemoteException;

    int setSmartMHSLocked(int i) throws RemoteException;

    void setSoftApConfiguration(SoftApConfiguration softApConfiguration) throws RemoteException;

    void setTCRule(boolean z, String str, int i) throws RemoteException;

    Map setTasPolicy(int i, int i2) throws RemoteException;

    boolean setTdlsEnabled(boolean z) throws RemoteException;

    void setTestMode(boolean z) throws RemoteException;

    void setTestSettings(int i, Bundle bundle) throws RemoteException;

    void setTrafficPatternTestSettings(Bundle bundle) throws RemoteException;

    boolean setUploadModeEnabled(boolean z) throws RemoteException;

    void setUserConfirmForSharingPassword(boolean z, String str) throws RemoteException;

    boolean setVendorWlanDriverProp(String str, String str2) throws RemoteException;

    void setVerboseLoggingEnabled(boolean z) throws RemoteException;

    boolean setWesModeEnabled(boolean z) throws RemoteException;

    void setWifiAiIwhInferenceResult(boolean[] zArr) throws RemoteException;

    void setWifiAiIwhTrainingResult(String str, int i, int i2, int i3) throws RemoteException;

    void setWifiAiServiceNsdResult(int[] iArr, int[] iArr2, int[] iArr3, String[] strArr)
            throws RemoteException;

    void setWifiAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException;

    void setWifiApClientDataPaused(String str, boolean z) throws RemoteException;

    void setWifiApClientEditedName(String str, String str2) throws RemoteException;

    void setWifiApClientMobileDataLimit(String str, long j) throws RemoteException;

    void setWifiApClientTimeLimit(String str, long j) throws RemoteException;

    void setWifiApConfigurationToDefault() throws RemoteException;

    void setWifiApDailyDataLimit(long j) throws RemoteException;

    boolean setWifiApEnabled(SoftApConfiguration softApConfiguration, boolean z)
            throws RemoteException;

    void setWifiApGuestModeEnabled(boolean z) throws RemoteException;

    void setWifiApGuestModeIsolationEnabled(boolean z) throws RemoteException;

    void setWifiApGuestPassword(String str) throws RemoteException;

    void setWifiApIsolate(boolean z) throws RemoteException;

    void setWifiApMacAclEnable(boolean z) throws RemoteException;

    void setWifiApMacAclMode(int i) throws RemoteException;

    void setWifiApMaxClient(int i) throws RemoteException;

    void setWifiApMaxClientToFramework(int i) throws RemoteException;

    void setWifiApWarningActivityRunning(int i) throws RemoteException;

    void setWifiApWpsPbc(boolean z) throws RemoteException;

    void setWifiDeveloperModeEnabled(boolean z) throws RemoteException;

    void setWifiSettingsForegroundState(int i) throws RemoteException;

    boolean setWifiSharingEnabled(boolean z) throws RemoteException;

    int setWifiUwbCoexEnabled(int i, boolean z) throws RemoteException;

    boolean shouldShowAutoWifiBubbleTip() throws RemoteException;

    int startCapture(int i) throws RemoteException;

    void startIssueMonitoring(Bundle bundle) throws RemoteException;

    int startMcfClientMHSDiscovery(boolean z) throws RemoteException;

    int startMcfMHSAdvertisement(boolean z) throws RemoteException;

    boolean startScan(String str) throws RemoteException;

    void startTimerForWifiOffload() throws RemoteException;

    int stopCapture() throws RemoteException;

    boolean supportWifiAp5GBasedOnCountry() throws RemoteException;

    boolean supportWifiAp6GBasedOnCountry() throws RemoteException;

    void triggerBackoffRoutine(boolean z) throws RemoteException;

    void unRegisterWifiApDataUsageCallback(int i) throws RemoteException;

    void unregisterAbTestConfigUpdateObserver(
            ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver)
            throws RemoteException;

    void unregisterClientDataUsageCallback(int i) throws RemoteException;

    void unregisterClientListDataUsageCallback(int i) throws RemoteException;

    void unregisterPasswordCallback(ISemSharedPasswordCallback iSemSharedPasswordCallback)
            throws RemoteException;

    void unregisterTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener)
            throws RemoteException;

    void unregisterWifiApSmartCallback(int i) throws RemoteException;

    void updateGuiderFeature(Bundle bundle) throws RemoteException;

    void updateHostapdMacList(int i) throws RemoteException;

    void updateIWCHintCard(long j) throws RemoteException;

    String wifiApBackUpClientDataUsageSettingsInfo() throws RemoteException;

    boolean wifiApBleClientRole(boolean z) throws RemoteException;

    boolean wifiApBleD2DClientRole(boolean z) throws RemoteException;

    boolean wifiApBleD2DMhsRole(boolean z) throws RemoteException;

    boolean wifiApBleMhsRole(boolean z) throws RemoteException;

    void wifiApDisassocSta(String str) throws RemoteException;

    void wifiApRestoreClientDataUsageSettingsInfo(String str) throws RemoteException;

    void wifiApRestoreDailyHotspotDataLimit(long j) throws RemoteException;

    public static class Default implements ISemWifiManager {
        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMaxDtimInSuspendMode(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setDtimInSuspendMode(int interval) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setVerboseLoggingEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void blockFccChannelBackoff(boolean choice) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiDeveloperModeEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiDeveloperModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiFirmwareVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiCid() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiVersions() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getFactoryMacAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getAntInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getFrameburstInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getPsmInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiSupportedFeatureSet() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getVendorWlanDriverProp(String propName) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setVendorWlanDriverProp(String propName, String value)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean removeFactoryMacAddress() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setFactoryMacAddress(String data) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setFccChannelBackoffEnabled(String interfaceName, boolean enable)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setPsmInfo(String data) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setAntInfo(String data) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setFrameburstInfo(String data) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setDcxoCalibrationData(String data) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getDcxoCalibrationData() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Bundle getCurrentWifiRouterInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Bundle getWifiRouterInfo(String configKey) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoBestEffort(String configKey) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoPresentable(String configKey) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Bundle getWifiRouterInfoByBssid(String bssid) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoBestEffortByBssid(String bssid) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoPresentableByBssid(String bssid) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getNetworkLastUpdatedTimeMap() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCurrentStateAndEnterTime() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long[] getNetworkUsageInfo(String configKey) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getDailyUsageInfo(int daysAgo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setGripSensorMonitorEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isGripSensorMonitorEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void triggerBackoffRoutine(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void set5GmmWaveSarBackoffEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setUploadModeEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isUploadModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getAdvancedAutohotspotConnectSettings() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAdvancedAutohotspotConnectSettings(int val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getAdvancedAutohotspotLCDSettings() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiSettingsForegroundState(int val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApWarningActivityRunning(int val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApWarningActivityRunningState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void clearAutoHotspotLists() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAdvancedAutohotspotLCDSettings(int val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getChannelUtilization() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getChannelUtilizationExtended() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamTrigger(int roamTrigger) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamTrigger() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamDelta(int roamDelta) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamDelta() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamScanPeriod(int roamScanPeriod) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamScanPeriod() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamBand(int band) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamBand() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setCountryRev(String countryRev) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCountryRev() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCountryCode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifi7DisabledCountry() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isNCHOModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setNCHOModeEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamScanEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamScanChannels(String[] channels) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWesModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWesModeEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean sendVendorSpecificActionFrame(
                String bssid, int channel, int dwellTime, String frameBody) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean sendReassociationRequestFrame(String bssid, int channel)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartMHSLockStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int canSmartMHSLocked() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int setSmartMHSLocked(int state) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void isClientAcceptedWifiProfileSharing(boolean val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApBleScanResult> getWifiApBleScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleClientRole(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleMhsRole(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean connectToSmartMHS(
                String addr,
                int type,
                int mhidden,
                int mSecurity,
                String mhs_mac,
                String Username,
                int ver,
                boolean wifiprofileshare)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void requestStopAutohotspotAdvertisement(boolean val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartApConnectedStatus(String mhs_mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerWifiApSmartCallback(
                IBinder binder, ISemWifiApSmartCallback callback, int callbackIdentifier)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterWifiApSmartCallback(int callbackIdentifier) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerWifiApDataUsageCallback(
                IBinder binder, ISemWifiApDataUsageCallback callback, int callbackIdentifier)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unRegisterWifiApDataUsageCallback(int callbackIdentifier)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartApConnectedStatusFromScanResult(String mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApBleScanResult> getWifiApBleD2DScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleD2DClientRole(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleD2DMhsRole(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean connectToSmartD2DClient(
                String bleaddr, String client_mac, ISemWifiApSmartCallback callback)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartD2DClientConnectedStatus(String mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApWpa3Supported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWifiApEnabled(SoftApConfiguration mSoftApConfig, boolean enable)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setLocalOnlyHotspotEnabled(
                boolean enabled, String ssid, String password, int band) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public SoftApConfiguration getSoftApConfiguration() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setSoftApConfiguration(SoftApConfiguration mConfig) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getStationInfo(String mac) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTxPower() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApFreq() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setHotspotAntMode(int mode) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getHotspotAntMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAntMode(int mode) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setPowerSavingTime(int min) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getMHSConfig(String key) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String setMHSConfig(String jsonMIFI) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApChannel() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApMaxClient() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean supportWifiAp5GBasedOnCountry() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean supportWifiAp6GBasedOnCountry() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApStaList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiSharingSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiSharingLiteSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getWifiApStaListDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApConfigurationToDefault() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getWifiApInterfaceNames() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApInterfaceName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String runIptablesRulesCommand(String cmd) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWifiSharingEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setProvisionSuccess(boolean set) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getProvisionSuccess() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiSharingEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApConnectedStationCount() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApLOHSState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getIndoorStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRVFModeStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setRVFmodeStatus(int mode) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void wifiApDisassocSta(String mac) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMaxClient(int num) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetSoftAp(Message msg) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMaxClientToFramework(int num) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApMaxClientFromFramework() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApWpsPbc(boolean value) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getWifiApWpsPbc() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApIsolate(boolean value) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getWifiApIsolate() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void updateHostapdMacList(int value) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int manageWifiApMacAclList(
                String name, String mac, int add_or_delete, int allow_or_deny)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> readWifiApMacAclList(int allow_or_deny) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApMacAclMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMacAclMode(int mode) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApMacAclEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMacAclEnable(boolean val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportHotspotDumpLogs(String logs) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApEnabledWithDualBand() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setArdkPowerSaveMode(boolean value) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void enableHotspotTsfInfo(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void notifyConnect(int netId, String key) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getSoftApBands() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean canAutoHotspotBeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isP2pConnected() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAutohotspotToastMessage(int noti) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSoftApSecurityType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isDataSaverEnabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isSoftap11axEnabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isSoftAp6ENetwork() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSoftApUpStreamNetworkType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getMHSMacFromInterface() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSoftApFreq() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void launchWifiApWarningForMcfMHS(
                int wifiap_band, int wifiap_set_security, boolean wifiap_security)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isNeededToShowWifiApDatalimitReachedDialog() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiMACAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int autohotspotWifiScanConnect(
                String ssid, String password, String bssid, int hideSSID, int mhsFreq, int security)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApHostapdFreq() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApHostapdSecurtiy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isMCFClientAutohotspotSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApBleScanResult> getMcfScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int startMcfClientMHSDiscovery(boolean enable) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int startMcfMHSAdvertisement(boolean enable) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int connectToMcfMHS(
                String addr,
                int type,
                int mhidden,
                int mSecurity,
                String mhs_mac,
                String Username,
                int ver)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getMcfConnectedStatus(String mhs_mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getMcfConnectedStatusFromScanResult(String mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientMobileDataLimit(String mac, long val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientTimeLimit(String mac, long val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientDataPaused(String mac, boolean val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientEditedName(String mac, String val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApDailyDataLimit(long bytes) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public SemWifiApClientDetails getWifiApClientDetails(String mac) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApClientDetails> getTopHotspotClientsToday(
                int topConnectedAndDisconnected, int maxListLength) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTopHotspotClientsTodayAsString(
                int topConnectedAndDisconnected, int maxListLength) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long getWifiApTodaysTotalDataUsage() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long getWifiApDailyDataLimit() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(
                long timestampInMilliSecsDate1, long timestampInMilliSecsDate2)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getMonthlyDataUsage() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isOverAllMhsDataLimitReached() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isOverAllMhsDataLimitSet() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String wifiApBackUpClientDataUsageSettingsInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void wifiApRestoreClientDataUsageSettingsInfo(String jsonString)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void wifiApRestoreDailyHotspotDataLimit(long bytes) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApGuestPassword(String pwd) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApGuestPassword() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApGuestModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApGuestModeEnabled(boolean val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApGuestModeIsolationEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApGuestModeIsolationEnabled(boolean val) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApGuestClient(String mac) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSAFamilySupportedBasedOnCountry() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerClientListDataUsageCallback(
                IBinder binder,
                ISemWifiApClientListUpdateCallback callbackToRegister,
                int callbackIdentifier,
                int topConnectedAndDisconnected,
                int maxListLength)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterClientListDataUsageCallback(int callbackIdentifier)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerClientDataUsageCallback(
                IBinder binder,
                ISemWifiApClientUpdateCallback callbackToRegister,
                int callbackIdentifier,
                String clientMac)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterClientDataUsageCallback(int callbackIdentifier)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportBigData(String featureName, String parameters) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void addOrUpdateWifiControlHistory(String packageName, boolean enable)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiEnableHistory() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean addOrUpdateNetwork(SemWifiConfiguration config) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean removeNetwork(String configKey) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void factoryReset() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetDeveloperOptionsSettings() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public ParceledListSlice getConfiguredNetworks() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void allowAutojoinPasspoint(String fqdn, boolean allowAutojoin)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List getPasspointConfigurations() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIssueDetectorDump(int maxCount) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportIssue(int reportId, Bundle data) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void updateGuiderFeature(Bundle bundle) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getDiagnosisResults() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void startIssueMonitoring(Bundle bundle) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getSilentRoamingDump(int maxCount) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getConnectivityLog(String category) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getQoSScores(List<String> bssids) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setBtmOptionUserEnabled(String configKey) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setBtmOptionUserDisabled(String configKey) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerPasswordCallback(String configKey, ISemSharedPasswordCallback callback)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterPasswordCallback(ISemSharedPasswordCallback callback)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void requestPassword(boolean showConfirm) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setUserConfirmForSharingPassword(boolean isAccept, String userData)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSupportedQoSProvider() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSupportedProfileRequest() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getProfileShareDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getAutoShareDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void runAutoShareForCurrent(List<String> target) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSupportedAutoWifi() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getAutoWifiDefaultValue() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean shouldShowAutoWifiBubbleTip() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isAvailableAutoWifiScan() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getAutoWifiDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getConfiguredNetworkLocations() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean hasConfiguredNetworkLocations(String wifiConfigKey) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTestSettings(int moduleId, Bundle settings) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAllowWifiScan(boolean enable, String packageName) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isScanningEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean startScan(String packageName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setEasySetupScanSettings(
                String packageName, SemEasySetupWifiScanSettings settings) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map<String, SemEasySetupWifiScanSettings> getEasySetupScanSettings()
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void disableRandomMac() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setImsCallEstablished(boolean isEstablished) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWcmEverQualityTested() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiIconVisibility() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getCurrentStatusMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getValidState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void notifyReachabilityLost() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setConnectivityCheckDisabled(boolean disabled) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setKeepConnectionAlways(boolean keepConnection) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setKeepConnection(boolean keepConnection, boolean always)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setKeepConnectionBigData(int reason) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void removeExcludedNetwork(int networkId) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String retrieveSemWifiConfigsBackupData() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void restoreSemConfigurationsBackupData(String semconfigs) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setConnectionAttemptInfo(int netId, boolean byUser, String configKey)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void restoreIWCSettingsValue(int opType, int value) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIWCQTables() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setIWCQTables(String qTables) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void updateIWCHintCard(long timestamp) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setIWCMockAction(int action) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean disconnectApBlockAutojoin(boolean block) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setOptimizerForceControlMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getOptimizerForceControlMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getOptimizerState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getServiceDetectionResult() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTrafficPatternTestSettings(Bundle settings) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int setWifiUwbCoexEnabled(int uwbCh, boolean enable) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setLatencyCritical(String ifaceName, int enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setPktlogFilter(String ifaceName, String filter) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean removePktlogFilter(String ifaceName, String filter) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean saveFwDump() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRssi(String ifaceName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiStaInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getNumOfWifiAnt() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void startTimerForWifiOffload() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void checkAppForWiFiOffloading(String packageName) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTCRule(boolean enabled, String iface, int limit) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void externalTwtInterface(int cmdId, String cmdLine) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getTWTParams() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getCtlFeatureState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetCallbackCondition(int mode) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetComebackCondition() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getCurrentL2TransitionMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getL2TransitionLog() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getNumberOfDataInEachRssiLevel() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIwhState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setSamsungMloCtrl(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setSamsungIwhCtrl(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getSamsungMloCtrl() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getSamsungIwhCtrl() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTestMode(boolean mode) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean iwhIntendedDisconnection() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean linkQosQuery(
                long payloadBytes,
                long desiredLatencyMs,
                long desiredThroughputMbps,
                int queryType,
                long timeWindowMs)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiServiceState(boolean enabled, int[] numClass, int[] numTimeStep)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiServiceNsdResult(
                int[] nsdResult, int[] l1ConvSerPredArr, int[] l2ConvSerPredArr, String[] convArr)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIwhTrainingResult(String gKey, int trScore, int numBssids, int mode)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIwhInferenceResult(boolean[] ret) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setIlaTrainingResult(double RssiResult, String bssidE) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTcpMonitorSocketForegroundHistory(int count) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTcpMonitorAllSocketHistory(int count) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTcpMonitorDnsHistory(int count) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isIndividualAppSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiUsabilityStatsEntry(int size) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isAvailableTdls() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWiderBandwidthTdlsSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setTdlsEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getMaxTdlsSession() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getNumOfTdlsSession() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getMHSClientTrafficDetails() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getNRTTrafficbandwidth() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long[] getDataConsumedValues() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetTotalPriorityDataConsumedValues() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getTasAverage() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map setTasPolicy(int newTasPolicy, int windowSize) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerTasPolicyChangedListener(SemTasPolicyListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterTasPolicyChangedListener(SemTasPolicyListener listener)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void enableTxPowerLogging(boolean enable, int index) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getDynamicFeatureStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSwitchToMobileDataDefaultOff() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMhsAiServiceState(boolean enabled, int[] numClass, int[] numTimeStep)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMhsAiServiceNsdResult(int[] predArr, String[] convoStrArr)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int startCapture(int captureFrameTypes) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int stopCapture() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isCaptureRunning() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getIsPacketCaptureSupportedByDriver() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMcfMultiControlMode(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerAbTestConfigUpdateObserver(
                ISemAbTestConfigurationUpdateObserver observer, String module)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterAbTestConfigUpdateObserver(
                ISemAbTestConfigurationUpdateObserver observer) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportAbTestResult(String module, String outputDim, String output)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemAbTestConfiguration> getAbTestConfigs() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public SemAbTestConfiguration getAbTestConfiguredModule(String module)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean sendReassociationFrequencyRequestFrame(String bssid, int channel)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemWifiManager {
        static final int TRANSACTION_addOrUpdateNetwork = 206;
        static final int TRANSACTION_addOrUpdateWifiControlHistory = 204;
        static final int TRANSACTION_allowAutojoinPasspoint = 211;
        static final int TRANSACTION_autohotspotWifiScanConnect = 164;
        static final int TRANSACTION_blockFccChannelBackoff = 4;
        static final int TRANSACTION_canAutoHotspotBeEnabled = 151;
        static final int TRANSACTION_canSmartMHSLocked = 73;
        static final int TRANSACTION_checkAppForWiFiOffloading = 280;
        static final int TRANSACTION_clearAutoHotspotLists = 48;
        static final int TRANSACTION_connectToMcfMHS = 171;
        static final int TRANSACTION_connectToSmartD2DClient = 90;
        static final int TRANSACTION_connectToSmartMHS = 79;
        static final int TRANSACTION_disableRandomMac = 245;
        static final int TRANSACTION_disconnectApBlockAutojoin = 265;
        static final int TRANSACTION_enableHotspotTsfInfo = 148;
        static final int TRANSACTION_enableTxPowerLogging = 321;
        static final int TRANSACTION_externalTwtInterface = 282;
        static final int TRANSACTION_factoryReset = 208;
        static final int TRANSACTION_getAbTestConfigs = 334;
        static final int TRANSACTION_getAbTestConfiguredModule = 335;
        static final int TRANSACTION_getAdvancedAutohotspotConnectSettings = 42;
        static final int TRANSACTION_getAdvancedAutohotspotLCDSettings = 44;
        static final int TRANSACTION_getAntInfo = 11;
        static final int TRANSACTION_getAutoShareDump = 230;
        static final int TRANSACTION_getAutoWifiDefaultValue = 233;
        static final int TRANSACTION_getAutoWifiDump = 236;
        static final int TRANSACTION_getChannelUtilization = 50;
        static final int TRANSACTION_getChannelUtilizationExtended = 51;
        static final int TRANSACTION_getConfiguredNetworkLocations = 237;
        static final int TRANSACTION_getConfiguredNetworks = 210;
        static final int TRANSACTION_getConnectivityLog = 219;
        static final int TRANSACTION_getCountryCode = 62;
        static final int TRANSACTION_getCountryRev = 61;
        static final int TRANSACTION_getCtlFeatureState = 284;
        static final int TRANSACTION_getCurrentL2TransitionMode = 287;
        static final int TRANSACTION_getCurrentStateAndEnterTime = 33;
        static final int TRANSACTION_getCurrentStatusMode = 249;
        static final int TRANSACTION_getCurrentWifiRouterInfo = 25;
        static final int TRANSACTION_getDailyUsageInfo = 35;
        static final int TRANSACTION_getDataConsumedValues = 315;
        static final int TRANSACTION_getDcxoCalibrationData = 24;
        static final int TRANSACTION_getDiagnosisResults = 216;
        static final int TRANSACTION_getDynamicFeatureStatus = 322;
        static final int TRANSACTION_getEasySetupScanSettings = 244;
        static final int TRANSACTION_getFactoryMacAddress = 10;
        static final int TRANSACTION_getFrameburstInfo = 12;
        static final int TRANSACTION_getHotspotAntMode = 101;
        static final int TRANSACTION_getIWCQTables = 261;
        static final int TRANSACTION_getIndoorStatus = 125;
        static final int TRANSACTION_getIsPacketCaptureSupportedByDriver = 329;
        static final int TRANSACTION_getIssueDetectorDump = 213;
        static final int TRANSACTION_getIwhState = 290;
        static final int TRANSACTION_getL2TransitionLog = 288;
        static final int TRANSACTION_getMHSClientTrafficDetails = 313;
        static final int TRANSACTION_getMHSConfig = 104;
        static final int TRANSACTION_getMHSMacFromInterface = 159;
        static final int TRANSACTION_getMaxTdlsSession = 311;
        static final int TRANSACTION_getMcfConnectedStatus = 172;
        static final int TRANSACTION_getMcfConnectedStatusFromScanResult = 173;
        static final int TRANSACTION_getMcfScanDetail = 168;
        static final int TRANSACTION_getMonthlyDataUsage = 185;
        static final int TRANSACTION_getNRTTrafficbandwidth = 314;
        static final int TRANSACTION_getNetworkLastUpdatedTimeMap = 32;
        static final int TRANSACTION_getNetworkUsageInfo = 34;
        static final int TRANSACTION_getNumOfTdlsSession = 312;
        static final int TRANSACTION_getNumOfWifiAnt = 278;
        static final int TRANSACTION_getNumberOfDataInEachRssiLevel = 289;
        static final int TRANSACTION_getOptimizerForceControlMode = 267;
        static final int TRANSACTION_getOptimizerState = 268;
        static final int TRANSACTION_getPasspointConfigurations = 212;
        static final int TRANSACTION_getProfileShareDump = 229;
        static final int TRANSACTION_getProvisionSuccess = 120;
        static final int TRANSACTION_getPsmInfo = 13;
        static final int TRANSACTION_getQoSScores = 220;
        static final int TRANSACTION_getRVFModeStatus = 126;
        static final int TRANSACTION_getRoamBand = 59;
        static final int TRANSACTION_getRoamDelta = 55;
        static final int TRANSACTION_getRoamScanPeriod = 57;
        static final int TRANSACTION_getRoamTrigger = 53;
        static final int TRANSACTION_getRssi = 276;
        static final int TRANSACTION_getSamsungIwhCtrl = 294;
        static final int TRANSACTION_getSamsungMloCtrl = 293;
        static final int TRANSACTION_getServiceDetectionResult = 269;
        static final int TRANSACTION_getSilentRoamingDump = 218;
        static final int TRANSACTION_getSmartApConnectedStatus = 81;
        static final int TRANSACTION_getSmartApConnectedStatusFromScanResult = 86;
        static final int TRANSACTION_getSmartD2DClientConnectedStatus = 91;
        static final int TRANSACTION_getSmartMHSLockStatus = 72;
        static final int TRANSACTION_getSoftApBands = 150;
        static final int TRANSACTION_getSoftApConfiguration = 95;
        static final int TRANSACTION_getSoftApFreq = 160;
        static final int TRANSACTION_getSoftApSecurityType = 154;
        static final int TRANSACTION_getSoftApUpStreamNetworkType = 158;
        static final int TRANSACTION_getStationInfo = 97;
        static final int TRANSACTION_getTWTParams = 283;
        static final int TRANSACTION_getTasAverage = 317;
        static final int TRANSACTION_getTcpMonitorAllSocketHistory = 304;
        static final int TRANSACTION_getTcpMonitorDnsHistory = 305;
        static final int TRANSACTION_getTcpMonitorSocketForegroundHistory = 303;
        static final int TRANSACTION_getTopHotspotClientsToday = 180;
        static final int TRANSACTION_getTopHotspotClientsTodayAsString = 181;
        static final int TRANSACTION_getTotalAndTop3ClientsDataUsageBetweenGivenDates = 184;
        static final int TRANSACTION_getTxPower = 98;
        static final int TRANSACTION_getValidState = 250;
        static final int TRANSACTION_getVendorWlanDriverProp = 15;
        static final int TRANSACTION_getWcmEverQualityTested = 247;
        static final int TRANSACTION_getWifi7DisabledCountry = 63;
        static final int TRANSACTION_getWifiApBleD2DScanDetail = 87;
        static final int TRANSACTION_getWifiApBleScanDetail = 76;
        static final int TRANSACTION_getWifiApChannel = 106;
        static final int TRANSACTION_getWifiApClientDetails = 179;
        static final int TRANSACTION_getWifiApConnectedStationCount = 123;
        static final int TRANSACTION_getWifiApDailyDataLimit = 183;
        static final int TRANSACTION_getWifiApFreq = 99;
        static final int TRANSACTION_getWifiApGuestPassword = 192;
        static final int TRANSACTION_getWifiApHostapdFreq = 165;
        static final int TRANSACTION_getWifiApHostapdSecurtiy = 166;
        static final int TRANSACTION_getWifiApInterfaceName = 116;
        static final int TRANSACTION_getWifiApInterfaceNames = 115;
        static final int TRANSACTION_getWifiApIsolate = 136;
        static final int TRANSACTION_getWifiApLOHSState = 124;
        static final int TRANSACTION_getWifiApMacAclMode = 140;
        static final int TRANSACTION_getWifiApMaxClient = 107;
        static final int TRANSACTION_getWifiApMaxClientFromFramework = 132;
        static final int TRANSACTION_getWifiApStaList = 110;
        static final int TRANSACTION_getWifiApStaListDetail = 113;
        static final int TRANSACTION_getWifiApState = 145;
        static final int TRANSACTION_getWifiApTodaysTotalDataUsage = 182;
        static final int TRANSACTION_getWifiApWarningActivityRunningState = 47;
        static final int TRANSACTION_getWifiApWpsPbc = 134;
        static final int TRANSACTION_getWifiCid = 8;
        static final int TRANSACTION_getWifiEnableHistory = 205;
        static final int TRANSACTION_getWifiFirmwareVersion = 7;
        static final int TRANSACTION_getWifiIconVisibility = 248;
        static final int TRANSACTION_getWifiMACAddress = 163;
        static final int TRANSACTION_getWifiRouterInfo = 26;
        static final int TRANSACTION_getWifiRouterInfoBestEffort = 27;
        static final int TRANSACTION_getWifiRouterInfoBestEffortByBssid = 30;
        static final int TRANSACTION_getWifiRouterInfoByBssid = 29;
        static final int TRANSACTION_getWifiRouterInfoPresentable = 28;
        static final int TRANSACTION_getWifiRouterInfoPresentableByBssid = 31;
        static final int TRANSACTION_getWifiStaInfo = 277;
        static final int TRANSACTION_getWifiSupportedFeatureSet = 14;
        static final int TRANSACTION_getWifiUsabilityStatsEntry = 307;
        static final int TRANSACTION_getWifiVersions = 9;
        static final int TRANSACTION_hasConfiguredNetworkLocations = 238;
        static final int TRANSACTION_isAvailableAutoWifiScan = 235;
        static final int TRANSACTION_isAvailableTdls = 308;
        static final int TRANSACTION_isCaptureRunning = 328;
        static final int TRANSACTION_isClientAcceptedWifiProfileSharing = 75;
        static final int TRANSACTION_isDataSaverEnabled = 155;
        static final int TRANSACTION_isGripSensorMonitorEnabled = 37;
        static final int TRANSACTION_isIndividualAppSupported = 306;
        static final int TRANSACTION_isMCFClientAutohotspotSupported = 167;
        static final int TRANSACTION_isNCHOModeEnabled = 64;
        static final int TRANSACTION_isNeededToShowWifiApDatalimitReachedDialog = 162;
        static final int TRANSACTION_isOverAllMhsDataLimitReached = 186;
        static final int TRANSACTION_isOverAllMhsDataLimitSet = 187;
        static final int TRANSACTION_isP2pConnected = 152;
        static final int TRANSACTION_isSAFamilySupportedBasedOnCountry = 198;
        static final int TRANSACTION_isScanningEnabled = 241;
        static final int TRANSACTION_isSoftAp6ENetwork = 157;
        static final int TRANSACTION_isSoftap11axEnabled = 156;
        static final int TRANSACTION_isSupportedAutoWifi = 232;
        static final int TRANSACTION_isSupportedProfileRequest = 228;
        static final int TRANSACTION_isSupportedQoSProvider = 227;
        static final int TRANSACTION_isSwitchToMobileDataDefaultOff = 323;
        static final int TRANSACTION_isUploadModeEnabled = 41;
        static final int TRANSACTION_isWesModeEnabled = 68;
        static final int TRANSACTION_isWiderBandwidthTdlsSupported = 309;
        static final int TRANSACTION_isWifiApEnabled = 122;
        static final int TRANSACTION_isWifiApEnabledWithDualBand = 146;
        static final int TRANSACTION_isWifiApGuestClient = 197;
        static final int TRANSACTION_isWifiApGuestModeEnabled = 193;
        static final int TRANSACTION_isWifiApGuestModeIsolationEnabled = 195;
        static final int TRANSACTION_isWifiApMacAclEnabled = 142;
        static final int TRANSACTION_isWifiApWpa3Supported = 92;
        static final int TRANSACTION_isWifiDeveloperModeEnabled = 6;
        static final int TRANSACTION_isWifiSharingEnabled = 121;
        static final int TRANSACTION_isWifiSharingLiteSupported = 112;
        static final int TRANSACTION_isWifiSharingSupported = 111;
        static final int TRANSACTION_iwhIntendedDisconnection = 296;
        static final int TRANSACTION_launchWifiApWarningForMcfMHS = 161;
        static final int TRANSACTION_linkQosQuery = 297;
        static final int TRANSACTION_manageWifiApMacAclList = 138;
        static final int TRANSACTION_notifyConnect = 149;
        static final int TRANSACTION_notifyReachabilityLost = 251;
        static final int TRANSACTION_readWifiApMacAclList = 139;
        static final int TRANSACTION_registerAbTestConfigUpdateObserver = 331;
        static final int TRANSACTION_registerClientDataUsageCallback = 201;
        static final int TRANSACTION_registerClientListDataUsageCallback = 199;
        static final int TRANSACTION_registerPasswordCallback = 223;
        static final int TRANSACTION_registerTasPolicyChangedListener = 319;
        static final int TRANSACTION_registerWifiApDataUsageCallback = 84;
        static final int TRANSACTION_registerWifiApSmartCallback = 82;
        static final int TRANSACTION_removeExcludedNetwork = 256;
        static final int TRANSACTION_removeFactoryMacAddress = 17;
        static final int TRANSACTION_removeNetwork = 207;
        static final int TRANSACTION_removePktlogFilter = 274;
        static final int TRANSACTION_reportAbTestResult = 333;
        static final int TRANSACTION_reportBigData = 203;
        static final int TRANSACTION_reportHotspotDumpLogs = 144;
        static final int TRANSACTION_reportIssue = 214;
        static final int TRANSACTION_requestPassword = 225;
        static final int TRANSACTION_requestStopAutohotspotAdvertisement = 80;
        static final int TRANSACTION_resetCallbackCondition = 285;
        static final int TRANSACTION_resetComebackCondition = 286;
        static final int TRANSACTION_resetDeveloperOptionsSettings = 209;
        static final int TRANSACTION_resetSoftAp = 130;
        static final int TRANSACTION_resetTotalPriorityDataConsumedValues = 316;
        static final int TRANSACTION_restoreIWCSettingsValue = 260;
        static final int TRANSACTION_restoreSemConfigurationsBackupData = 258;
        static final int TRANSACTION_retrieveSemWifiConfigsBackupData = 257;
        static final int TRANSACTION_runAutoShareForCurrent = 231;
        static final int TRANSACTION_runIptablesRulesCommand = 117;
        static final int TRANSACTION_saveFwDump = 275;
        static final int TRANSACTION_sendReassociationFrequencyRequestFrame = 336;
        static final int TRANSACTION_sendReassociationRequestFrame = 71;
        static final int TRANSACTION_sendVendorSpecificActionFrame = 70;
        static final int TRANSACTION_set5GmmWaveSarBackoffEnabled = 39;
        static final int TRANSACTION_setAdvancedAutohotspotConnectSettings = 43;
        static final int TRANSACTION_setAdvancedAutohotspotLCDSettings = 49;
        static final int TRANSACTION_setAllowWifiScan = 240;
        static final int TRANSACTION_setAntInfo = 21;
        static final int TRANSACTION_setAntMode = 102;
        static final int TRANSACTION_setArdkPowerSaveMode = 147;
        static final int TRANSACTION_setAutohotspotToastMessage = 153;
        static final int TRANSACTION_setBtmOptionUserDisabled = 222;
        static final int TRANSACTION_setBtmOptionUserEnabled = 221;
        static final int TRANSACTION_setConnectionAttemptInfo = 259;
        static final int TRANSACTION_setConnectivityCheckDisabled = 252;
        static final int TRANSACTION_setCountryRev = 60;
        static final int TRANSACTION_setDcxoCalibrationData = 23;
        static final int TRANSACTION_setDtimInSuspendMode = 2;
        static final int TRANSACTION_setEasySetupScanSettings = 243;
        static final int TRANSACTION_setFactoryMacAddress = 18;
        static final int TRANSACTION_setFccChannelBackoffEnabled = 19;
        static final int TRANSACTION_setFrameburstInfo = 22;
        static final int TRANSACTION_setGripSensorMonitorEnabled = 36;
        static final int TRANSACTION_setHotspotAntMode = 100;
        static final int TRANSACTION_setIWCMockAction = 264;
        static final int TRANSACTION_setIWCQTables = 262;
        static final int TRANSACTION_setIlaTrainingResult = 302;
        static final int TRANSACTION_setImsCallEstablished = 246;
        static final int TRANSACTION_setKeepConnection = 254;
        static final int TRANSACTION_setKeepConnectionAlways = 253;
        static final int TRANSACTION_setKeepConnectionBigData = 255;
        static final int TRANSACTION_setLatencyCritical = 272;
        static final int TRANSACTION_setLocalOnlyHotspotEnabled = 94;
        static final int TRANSACTION_setMHSConfig = 105;
        static final int TRANSACTION_setMaxDtimInSuspendMode = 1;
        static final int TRANSACTION_setMcfMultiControlMode = 330;
        static final int TRANSACTION_setMhsAiServiceNsdResult = 325;
        static final int TRANSACTION_setMhsAiServiceState = 324;
        static final int TRANSACTION_setNCHOModeEnabled = 65;
        static final int TRANSACTION_setOptimizerForceControlMode = 266;
        static final int TRANSACTION_setPktlogFilter = 273;
        static final int TRANSACTION_setPowerSavingTime = 103;
        static final int TRANSACTION_setProvisionSuccess = 119;
        static final int TRANSACTION_setPsmInfo = 20;
        static final int TRANSACTION_setRVFmodeStatus = 127;
        static final int TRANSACTION_setRoamBand = 58;
        static final int TRANSACTION_setRoamDelta = 54;
        static final int TRANSACTION_setRoamScanChannels = 67;
        static final int TRANSACTION_setRoamScanEnabled = 66;
        static final int TRANSACTION_setRoamScanPeriod = 56;
        static final int TRANSACTION_setRoamTrigger = 52;
        static final int TRANSACTION_setSamsungIwhCtrl = 292;
        static final int TRANSACTION_setSamsungMloCtrl = 291;
        static final int TRANSACTION_setSmartMHSLocked = 74;
        static final int TRANSACTION_setSoftApConfiguration = 96;
        static final int TRANSACTION_setTCRule = 281;
        static final int TRANSACTION_setTasPolicy = 318;
        static final int TRANSACTION_setTdlsEnabled = 310;
        static final int TRANSACTION_setTestMode = 295;
        static final int TRANSACTION_setTestSettings = 239;
        static final int TRANSACTION_setTrafficPatternTestSettings = 270;
        static final int TRANSACTION_setUploadModeEnabled = 40;
        static final int TRANSACTION_setUserConfirmForSharingPassword = 226;
        static final int TRANSACTION_setVendorWlanDriverProp = 16;
        static final int TRANSACTION_setVerboseLoggingEnabled = 3;
        static final int TRANSACTION_setWesModeEnabled = 69;
        static final int TRANSACTION_setWifiAiIwhInferenceResult = 301;
        static final int TRANSACTION_setWifiAiIwhTrainingResult = 300;
        static final int TRANSACTION_setWifiAiServiceNsdResult = 299;
        static final int TRANSACTION_setWifiAiServiceState = 298;
        static final int TRANSACTION_setWifiApClientDataPaused = 176;
        static final int TRANSACTION_setWifiApClientEditedName = 177;
        static final int TRANSACTION_setWifiApClientMobileDataLimit = 174;
        static final int TRANSACTION_setWifiApClientTimeLimit = 175;
        static final int TRANSACTION_setWifiApConfigurationToDefault = 114;
        static final int TRANSACTION_setWifiApDailyDataLimit = 178;
        static final int TRANSACTION_setWifiApEnabled = 93;
        static final int TRANSACTION_setWifiApGuestModeEnabled = 194;
        static final int TRANSACTION_setWifiApGuestModeIsolationEnabled = 196;
        static final int TRANSACTION_setWifiApGuestPassword = 191;
        static final int TRANSACTION_setWifiApIsolate = 135;
        static final int TRANSACTION_setWifiApMacAclEnable = 143;
        static final int TRANSACTION_setWifiApMacAclMode = 141;
        static final int TRANSACTION_setWifiApMaxClient = 129;
        static final int TRANSACTION_setWifiApMaxClientToFramework = 131;
        static final int TRANSACTION_setWifiApWarningActivityRunning = 46;
        static final int TRANSACTION_setWifiApWpsPbc = 133;
        static final int TRANSACTION_setWifiDeveloperModeEnabled = 5;
        static final int TRANSACTION_setWifiSettingsForegroundState = 45;
        static final int TRANSACTION_setWifiSharingEnabled = 118;
        static final int TRANSACTION_setWifiUwbCoexEnabled = 271;
        static final int TRANSACTION_shouldShowAutoWifiBubbleTip = 234;
        static final int TRANSACTION_startCapture = 326;
        static final int TRANSACTION_startIssueMonitoring = 217;
        static final int TRANSACTION_startMcfClientMHSDiscovery = 169;
        static final int TRANSACTION_startMcfMHSAdvertisement = 170;
        static final int TRANSACTION_startScan = 242;
        static final int TRANSACTION_startTimerForWifiOffload = 279;
        static final int TRANSACTION_stopCapture = 327;
        static final int TRANSACTION_supportWifiAp5GBasedOnCountry = 108;
        static final int TRANSACTION_supportWifiAp6GBasedOnCountry = 109;
        static final int TRANSACTION_triggerBackoffRoutine = 38;
        static final int TRANSACTION_unRegisterWifiApDataUsageCallback = 85;
        static final int TRANSACTION_unregisterAbTestConfigUpdateObserver = 332;
        static final int TRANSACTION_unregisterClientDataUsageCallback = 202;
        static final int TRANSACTION_unregisterClientListDataUsageCallback = 200;
        static final int TRANSACTION_unregisterPasswordCallback = 224;
        static final int TRANSACTION_unregisterTasPolicyChangedListener = 320;
        static final int TRANSACTION_unregisterWifiApSmartCallback = 83;
        static final int TRANSACTION_updateGuiderFeature = 215;
        static final int TRANSACTION_updateHostapdMacList = 137;
        static final int TRANSACTION_updateIWCHintCard = 263;
        static final int TRANSACTION_wifiApBackUpClientDataUsageSettingsInfo = 188;
        static final int TRANSACTION_wifiApBleClientRole = 77;
        static final int TRANSACTION_wifiApBleD2DClientRole = 88;
        static final int TRANSACTION_wifiApBleD2DMhsRole = 89;
        static final int TRANSACTION_wifiApBleMhsRole = 78;
        static final int TRANSACTION_wifiApDisassocSta = 128;
        static final int TRANSACTION_wifiApRestoreClientDataUsageSettingsInfo = 189;
        static final int TRANSACTION_wifiApRestoreDailyHotspotDataLimit = 190;

        public Stub() {
            attachInterface(this, ISemWifiManager.DESCRIPTOR);
        }

        public static ISemWifiManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiManager)) {
                return (ISemWifiManager) iin;
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
                    return "setMaxDtimInSuspendMode";
                case 2:
                    return "setDtimInSuspendMode";
                case 3:
                    return "setVerboseLoggingEnabled";
                case 4:
                    return "blockFccChannelBackoff";
                case 5:
                    return "setWifiDeveloperModeEnabled";
                case 6:
                    return "isWifiDeveloperModeEnabled";
                case 7:
                    return "getWifiFirmwareVersion";
                case 8:
                    return "getWifiCid";
                case 9:
                    return "getWifiVersions";
                case 10:
                    return "getFactoryMacAddress";
                case 11:
                    return "getAntInfo";
                case 12:
                    return "getFrameburstInfo";
                case 13:
                    return "getPsmInfo";
                case 14:
                    return "getWifiSupportedFeatureSet";
                case 15:
                    return "getVendorWlanDriverProp";
                case 16:
                    return "setVendorWlanDriverProp";
                case 17:
                    return "removeFactoryMacAddress";
                case 18:
                    return "setFactoryMacAddress";
                case 19:
                    return "setFccChannelBackoffEnabled";
                case 20:
                    return "setPsmInfo";
                case 21:
                    return "setAntInfo";
                case 22:
                    return "setFrameburstInfo";
                case 23:
                    return "setDcxoCalibrationData";
                case 24:
                    return "getDcxoCalibrationData";
                case 25:
                    return "getCurrentWifiRouterInfo";
                case 26:
                    return "getWifiRouterInfo";
                case 27:
                    return "getWifiRouterInfoBestEffort";
                case 28:
                    return "getWifiRouterInfoPresentable";
                case 29:
                    return "getWifiRouterInfoByBssid";
                case 30:
                    return "getWifiRouterInfoBestEffortByBssid";
                case 31:
                    return "getWifiRouterInfoPresentableByBssid";
                case 32:
                    return "getNetworkLastUpdatedTimeMap";
                case 33:
                    return "getCurrentStateAndEnterTime";
                case 34:
                    return "getNetworkUsageInfo";
                case 35:
                    return "getDailyUsageInfo";
                case 36:
                    return "setGripSensorMonitorEnabled";
                case 37:
                    return "isGripSensorMonitorEnabled";
                case 38:
                    return "triggerBackoffRoutine";
                case 39:
                    return "set5GmmWaveSarBackoffEnabled";
                case 40:
                    return "setUploadModeEnabled";
                case 41:
                    return "isUploadModeEnabled";
                case 42:
                    return "getAdvancedAutohotspotConnectSettings";
                case 43:
                    return "setAdvancedAutohotspotConnectSettings";
                case 44:
                    return "getAdvancedAutohotspotLCDSettings";
                case 45:
                    return "setWifiSettingsForegroundState";
                case 46:
                    return "setWifiApWarningActivityRunning";
                case 47:
                    return "getWifiApWarningActivityRunningState";
                case 48:
                    return "clearAutoHotspotLists";
                case 49:
                    return "setAdvancedAutohotspotLCDSettings";
                case 50:
                    return "getChannelUtilization";
                case 51:
                    return "getChannelUtilizationExtended";
                case 52:
                    return "setRoamTrigger";
                case 53:
                    return "getRoamTrigger";
                case 54:
                    return "setRoamDelta";
                case 55:
                    return "getRoamDelta";
                case 56:
                    return "setRoamScanPeriod";
                case 57:
                    return "getRoamScanPeriod";
                case 58:
                    return "setRoamBand";
                case 59:
                    return "getRoamBand";
                case 60:
                    return "setCountryRev";
                case 61:
                    return "getCountryRev";
                case 62:
                    return "getCountryCode";
                case 63:
                    return "getWifi7DisabledCountry";
                case 64:
                    return "isNCHOModeEnabled";
                case 65:
                    return "setNCHOModeEnabled";
                case 66:
                    return "setRoamScanEnabled";
                case 67:
                    return "setRoamScanChannels";
                case 68:
                    return "isWesModeEnabled";
                case 69:
                    return "setWesModeEnabled";
                case 70:
                    return "sendVendorSpecificActionFrame";
                case 71:
                    return "sendReassociationRequestFrame";
                case 72:
                    return "getSmartMHSLockStatus";
                case 73:
                    return "canSmartMHSLocked";
                case 74:
                    return "setSmartMHSLocked";
                case 75:
                    return "isClientAcceptedWifiProfileSharing";
                case 76:
                    return "getWifiApBleScanDetail";
                case 77:
                    return "wifiApBleClientRole";
                case 78:
                    return "wifiApBleMhsRole";
                case 79:
                    return "connectToSmartMHS";
                case 80:
                    return "requestStopAutohotspotAdvertisement";
                case 81:
                    return "getSmartApConnectedStatus";
                case 82:
                    return "registerWifiApSmartCallback";
                case 83:
                    return "unregisterWifiApSmartCallback";
                case 84:
                    return "registerWifiApDataUsageCallback";
                case 85:
                    return "unRegisterWifiApDataUsageCallback";
                case 86:
                    return "getSmartApConnectedStatusFromScanResult";
                case 87:
                    return "getWifiApBleD2DScanDetail";
                case 88:
                    return "wifiApBleD2DClientRole";
                case 89:
                    return "wifiApBleD2DMhsRole";
                case 90:
                    return "connectToSmartD2DClient";
                case 91:
                    return "getSmartD2DClientConnectedStatus";
                case 92:
                    return "isWifiApWpa3Supported";
                case 93:
                    return "setWifiApEnabled";
                case 94:
                    return "setLocalOnlyHotspotEnabled";
                case 95:
                    return "getSoftApConfiguration";
                case 96:
                    return "setSoftApConfiguration";
                case 97:
                    return "getStationInfo";
                case 98:
                    return "getTxPower";
                case 99:
                    return "getWifiApFreq";
                case 100:
                    return "setHotspotAntMode";
                case 101:
                    return "getHotspotAntMode";
                case 102:
                    return "setAntMode";
                case 103:
                    return "setPowerSavingTime";
                case 104:
                    return "getMHSConfig";
                case 105:
                    return "setMHSConfig";
                case 106:
                    return "getWifiApChannel";
                case 107:
                    return "getWifiApMaxClient";
                case 108:
                    return "supportWifiAp5GBasedOnCountry";
                case 109:
                    return "supportWifiAp6GBasedOnCountry";
                case 110:
                    return "getWifiApStaList";
                case 111:
                    return "isWifiSharingSupported";
                case 112:
                    return "isWifiSharingLiteSupported";
                case 113:
                    return "getWifiApStaListDetail";
                case 114:
                    return "setWifiApConfigurationToDefault";
                case 115:
                    return "getWifiApInterfaceNames";
                case 116:
                    return "getWifiApInterfaceName";
                case 117:
                    return "runIptablesRulesCommand";
                case 118:
                    return "setWifiSharingEnabled";
                case 119:
                    return "setProvisionSuccess";
                case 120:
                    return "getProvisionSuccess";
                case 121:
                    return "isWifiSharingEnabled";
                case 122:
                    return "isWifiApEnabled";
                case 123:
                    return "getWifiApConnectedStationCount";
                case 124:
                    return "getWifiApLOHSState";
                case 125:
                    return "getIndoorStatus";
                case 126:
                    return "getRVFModeStatus";
                case 127:
                    return "setRVFmodeStatus";
                case 128:
                    return "wifiApDisassocSta";
                case 129:
                    return "setWifiApMaxClient";
                case 130:
                    return "resetSoftAp";
                case 131:
                    return "setWifiApMaxClientToFramework";
                case 132:
                    return "getWifiApMaxClientFromFramework";
                case 133:
                    return "setWifiApWpsPbc";
                case 134:
                    return "getWifiApWpsPbc";
                case 135:
                    return "setWifiApIsolate";
                case 136:
                    return "getWifiApIsolate";
                case 137:
                    return "updateHostapdMacList";
                case 138:
                    return "manageWifiApMacAclList";
                case 139:
                    return "readWifiApMacAclList";
                case 140:
                    return "getWifiApMacAclMode";
                case 141:
                    return "setWifiApMacAclMode";
                case 142:
                    return "isWifiApMacAclEnabled";
                case 143:
                    return "setWifiApMacAclEnable";
                case 144:
                    return "reportHotspotDumpLogs";
                case 145:
                    return "getWifiApState";
                case 146:
                    return "isWifiApEnabledWithDualBand";
                case 147:
                    return "setArdkPowerSaveMode";
                case 148:
                    return "enableHotspotTsfInfo";
                case 149:
                    return "notifyConnect";
                case 150:
                    return "getSoftApBands";
                case 151:
                    return "canAutoHotspotBeEnabled";
                case 152:
                    return "isP2pConnected";
                case 153:
                    return "setAutohotspotToastMessage";
                case 154:
                    return "getSoftApSecurityType";
                case 155:
                    return "isDataSaverEnabled";
                case 156:
                    return "isSoftap11axEnabled";
                case 157:
                    return "isSoftAp6ENetwork";
                case 158:
                    return "getSoftApUpStreamNetworkType";
                case 159:
                    return "getMHSMacFromInterface";
                case 160:
                    return "getSoftApFreq";
                case 161:
                    return "launchWifiApWarningForMcfMHS";
                case 162:
                    return "isNeededToShowWifiApDatalimitReachedDialog";
                case 163:
                    return "getWifiMACAddress";
                case 164:
                    return "autohotspotWifiScanConnect";
                case 165:
                    return "getWifiApHostapdFreq";
                case 166:
                    return "getWifiApHostapdSecurtiy";
                case 167:
                    return "isMCFClientAutohotspotSupported";
                case 168:
                    return "getMcfScanDetail";
                case 169:
                    return "startMcfClientMHSDiscovery";
                case 170:
                    return "startMcfMHSAdvertisement";
                case 171:
                    return "connectToMcfMHS";
                case 172:
                    return "getMcfConnectedStatus";
                case 173:
                    return "getMcfConnectedStatusFromScanResult";
                case 174:
                    return "setWifiApClientMobileDataLimit";
                case 175:
                    return "setWifiApClientTimeLimit";
                case 176:
                    return "setWifiApClientDataPaused";
                case 177:
                    return "setWifiApClientEditedName";
                case 178:
                    return "setWifiApDailyDataLimit";
                case 179:
                    return "getWifiApClientDetails";
                case 180:
                    return "getTopHotspotClientsToday";
                case 181:
                    return "getTopHotspotClientsTodayAsString";
                case 182:
                    return "getWifiApTodaysTotalDataUsage";
                case 183:
                    return "getWifiApDailyDataLimit";
                case 184:
                    return "getTotalAndTop3ClientsDataUsageBetweenGivenDates";
                case 185:
                    return "getMonthlyDataUsage";
                case 186:
                    return "isOverAllMhsDataLimitReached";
                case 187:
                    return "isOverAllMhsDataLimitSet";
                case 188:
                    return "wifiApBackUpClientDataUsageSettingsInfo";
                case 189:
                    return "wifiApRestoreClientDataUsageSettingsInfo";
                case 190:
                    return "wifiApRestoreDailyHotspotDataLimit";
                case 191:
                    return "setWifiApGuestPassword";
                case 192:
                    return "getWifiApGuestPassword";
                case 193:
                    return "isWifiApGuestModeEnabled";
                case 194:
                    return "setWifiApGuestModeEnabled";
                case 195:
                    return "isWifiApGuestModeIsolationEnabled";
                case 196:
                    return "setWifiApGuestModeIsolationEnabled";
                case 197:
                    return "isWifiApGuestClient";
                case 198:
                    return "isSAFamilySupportedBasedOnCountry";
                case 199:
                    return "registerClientListDataUsageCallback";
                case 200:
                    return "unregisterClientListDataUsageCallback";
                case 201:
                    return "registerClientDataUsageCallback";
                case 202:
                    return "unregisterClientDataUsageCallback";
                case 203:
                    return "reportBigData";
                case 204:
                    return "addOrUpdateWifiControlHistory";
                case 205:
                    return "getWifiEnableHistory";
                case 206:
                    return "addOrUpdateNetwork";
                case 207:
                    return "removeNetwork";
                case 208:
                    return "factoryReset";
                case 209:
                    return "resetDeveloperOptionsSettings";
                case 210:
                    return "getConfiguredNetworks";
                case 211:
                    return "allowAutojoinPasspoint";
                case 212:
                    return "getPasspointConfigurations";
                case 213:
                    return "getIssueDetectorDump";
                case 214:
                    return "reportIssue";
                case 215:
                    return "updateGuiderFeature";
                case 216:
                    return "getDiagnosisResults";
                case 217:
                    return "startIssueMonitoring";
                case 218:
                    return "getSilentRoamingDump";
                case 219:
                    return "getConnectivityLog";
                case 220:
                    return "getQoSScores";
                case 221:
                    return "setBtmOptionUserEnabled";
                case 222:
                    return "setBtmOptionUserDisabled";
                case 223:
                    return "registerPasswordCallback";
                case 224:
                    return "unregisterPasswordCallback";
                case 225:
                    return "requestPassword";
                case 226:
                    return "setUserConfirmForSharingPassword";
                case 227:
                    return "isSupportedQoSProvider";
                case 228:
                    return "isSupportedProfileRequest";
                case 229:
                    return "getProfileShareDump";
                case 230:
                    return "getAutoShareDump";
                case 231:
                    return "runAutoShareForCurrent";
                case 232:
                    return "isSupportedAutoWifi";
                case 233:
                    return "getAutoWifiDefaultValue";
                case 234:
                    return "shouldShowAutoWifiBubbleTip";
                case 235:
                    return "isAvailableAutoWifiScan";
                case 236:
                    return "getAutoWifiDump";
                case 237:
                    return "getConfiguredNetworkLocations";
                case 238:
                    return "hasConfiguredNetworkLocations";
                case 239:
                    return "setTestSettings";
                case 240:
                    return "setAllowWifiScan";
                case 241:
                    return "isScanningEnabled";
                case 242:
                    return "startScan";
                case 243:
                    return "setEasySetupScanSettings";
                case 244:
                    return "getEasySetupScanSettings";
                case 245:
                    return "disableRandomMac";
                case 246:
                    return "setImsCallEstablished";
                case 247:
                    return "getWcmEverQualityTested";
                case 248:
                    return "getWifiIconVisibility";
                case 249:
                    return "getCurrentStatusMode";
                case 250:
                    return "getValidState";
                case 251:
                    return "notifyReachabilityLost";
                case 252:
                    return "setConnectivityCheckDisabled";
                case 253:
                    return "setKeepConnectionAlways";
                case 254:
                    return "setKeepConnection";
                case 255:
                    return "setKeepConnectionBigData";
                case 256:
                    return "removeExcludedNetwork";
                case 257:
                    return "retrieveSemWifiConfigsBackupData";
                case 258:
                    return "restoreSemConfigurationsBackupData";
                case 259:
                    return "setConnectionAttemptInfo";
                case 260:
                    return "restoreIWCSettingsValue";
                case 261:
                    return "getIWCQTables";
                case 262:
                    return "setIWCQTables";
                case 263:
                    return "updateIWCHintCard";
                case 264:
                    return "setIWCMockAction";
                case 265:
                    return "disconnectApBlockAutojoin";
                case 266:
                    return "setOptimizerForceControlMode";
                case 267:
                    return "getOptimizerForceControlMode";
                case 268:
                    return "getOptimizerState";
                case 269:
                    return "getServiceDetectionResult";
                case 270:
                    return "setTrafficPatternTestSettings";
                case 271:
                    return "setWifiUwbCoexEnabled";
                case 272:
                    return "setLatencyCritical";
                case 273:
                    return "setPktlogFilter";
                case 274:
                    return "removePktlogFilter";
                case 275:
                    return "saveFwDump";
                case 276:
                    return "getRssi";
                case 277:
                    return "getWifiStaInfo";
                case 278:
                    return "getNumOfWifiAnt";
                case 279:
                    return "startTimerForWifiOffload";
                case 280:
                    return "checkAppForWiFiOffloading";
                case 281:
                    return "setTCRule";
                case 282:
                    return "externalTwtInterface";
                case 283:
                    return "getTWTParams";
                case 284:
                    return "getCtlFeatureState";
                case 285:
                    return "resetCallbackCondition";
                case 286:
                    return "resetComebackCondition";
                case 287:
                    return "getCurrentL2TransitionMode";
                case 288:
                    return "getL2TransitionLog";
                case 289:
                    return "getNumberOfDataInEachRssiLevel";
                case 290:
                    return "getIwhState";
                case 291:
                    return "setSamsungMloCtrl";
                case 292:
                    return "setSamsungIwhCtrl";
                case 293:
                    return "getSamsungMloCtrl";
                case 294:
                    return "getSamsungIwhCtrl";
                case 295:
                    return "setTestMode";
                case 296:
                    return "iwhIntendedDisconnection";
                case 297:
                    return "linkQosQuery";
                case 298:
                    return "setWifiAiServiceState";
                case 299:
                    return "setWifiAiServiceNsdResult";
                case 300:
                    return "setWifiAiIwhTrainingResult";
                case 301:
                    return "setWifiAiIwhInferenceResult";
                case 302:
                    return "setIlaTrainingResult";
                case 303:
                    return "getTcpMonitorSocketForegroundHistory";
                case 304:
                    return "getTcpMonitorAllSocketHistory";
                case 305:
                    return "getTcpMonitorDnsHistory";
                case 306:
                    return "isIndividualAppSupported";
                case 307:
                    return "getWifiUsabilityStatsEntry";
                case 308:
                    return "isAvailableTdls";
                case 309:
                    return "isWiderBandwidthTdlsSupported";
                case 310:
                    return "setTdlsEnabled";
                case 311:
                    return "getMaxTdlsSession";
                case 312:
                    return "getNumOfTdlsSession";
                case 313:
                    return "getMHSClientTrafficDetails";
                case 314:
                    return "getNRTTrafficbandwidth";
                case 315:
                    return "getDataConsumedValues";
                case 316:
                    return "resetTotalPriorityDataConsumedValues";
                case 317:
                    return "getTasAverage";
                case 318:
                    return "setTasPolicy";
                case 319:
                    return "registerTasPolicyChangedListener";
                case 320:
                    return "unregisterTasPolicyChangedListener";
                case 321:
                    return "enableTxPowerLogging";
                case 322:
                    return "getDynamicFeatureStatus";
                case 323:
                    return "isSwitchToMobileDataDefaultOff";
                case 324:
                    return "setMhsAiServiceState";
                case 325:
                    return "setMhsAiServiceNsdResult";
                case 326:
                    return "startCapture";
                case 327:
                    return "stopCapture";
                case 328:
                    return "isCaptureRunning";
                case 329:
                    return "getIsPacketCaptureSupportedByDriver";
                case 330:
                    return "setMcfMultiControlMode";
                case 331:
                    return "registerAbTestConfigUpdateObserver";
                case 332:
                    return "unregisterAbTestConfigUpdateObserver";
                case 333:
                    return "reportAbTestResult";
                case 334:
                    return "getAbTestConfigs";
                case 335:
                    return "getAbTestConfiguredModule";
                case 336:
                    return "sendReassociationFrequencyRequestFrame";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, final Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemWifiManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemWifiManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMaxDtimInSuspendMode(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    setDtimInSuspendMode(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setVerboseLoggingEnabled(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    blockFccChannelBackoff(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _arg05 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWifiDeveloperModeEnabled(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    boolean _result = isWifiDeveloperModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 7:
                    String _result2 = getWifiFirmwareVersion();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 8:
                    String _result3 = getWifiCid();
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 9:
                    String _result4 = getWifiVersions();
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 10:
                    String _result5 = getFactoryMacAddress();
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                case 11:
                    String _result6 = getAntInfo();
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 12:
                    String _result7 = getFrameburstInfo();
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 13:
                    String _result8 = getPsmInfo();
                    reply.writeNoException();
                    reply.writeString(_result8);
                    return true;
                case 14:
                    String _result9 = getWifiSupportedFeatureSet();
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 15:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    String _result10 = getVendorWlanDriverProp(_arg06);
                    reply.writeNoException();
                    reply.writeString(_result10);
                    return true;
                case 16:
                    return onTransact$setVendorWlanDriverProp$(data, reply);
                case 17:
                    boolean _result11 = removeFactoryMacAddress();
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 18:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result12 = setFactoryMacAddress(_arg07);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 19:
                    return onTransact$setFccChannelBackoffEnabled$(data, reply);
                case 20:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result13 = setPsmInfo(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 21:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result14 = setAntInfo(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 22:
                    String _arg010 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result15 = setFrameburstInfo(_arg010);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 23:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result16 = setDcxoCalibrationData(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 24:
                    String _result17 = getDcxoCalibrationData();
                    reply.writeNoException();
                    reply.writeString(_result17);
                    return true;
                case 25:
                    Bundle _result18 = getCurrentWifiRouterInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result18, 1);
                    return true;
                case 26:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    Bundle _result19 = getWifiRouterInfo(_arg012);
                    reply.writeNoException();
                    reply.writeTypedObject(_result19, 1);
                    return true;
                case 27:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    String _result20 = getWifiRouterInfoBestEffort(_arg013);
                    reply.writeNoException();
                    reply.writeString(_result20);
                    return true;
                case 28:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    String _result21 = getWifiRouterInfoPresentable(_arg014);
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 29:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    Bundle _result22 = getWifiRouterInfoByBssid(_arg015);
                    reply.writeNoException();
                    reply.writeTypedObject(_result22, 1);
                    return true;
                case 30:
                    String _arg016 = data.readString();
                    data.enforceNoDataAvail();
                    String _result23 = getWifiRouterInfoBestEffortByBssid(_arg016);
                    reply.writeNoException();
                    reply.writeString(_result23);
                    return true;
                case 31:
                    String _arg017 = data.readString();
                    data.enforceNoDataAvail();
                    String _result24 = getWifiRouterInfoPresentableByBssid(_arg017);
                    reply.writeNoException();
                    reply.writeString(_result24);
                    return true;
                case 32:
                    Map _result25 = getNetworkLastUpdatedTimeMap();
                    reply.writeNoException();
                    reply.writeMap(_result25);
                    return true;
                case 33:
                    String _result26 = getCurrentStateAndEnterTime();
                    reply.writeNoException();
                    reply.writeString(_result26);
                    return true;
                case 34:
                    String _arg018 = data.readString();
                    data.enforceNoDataAvail();
                    long[] _result27 = getNetworkUsageInfo(_arg018);
                    reply.writeNoException();
                    reply.writeLongArray(_result27);
                    return true;
                case 35:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result28 = getDailyUsageInfo(_arg019);
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case 36:
                    boolean _arg020 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setGripSensorMonitorEnabled(_arg020);
                    reply.writeNoException();
                    return true;
                case 37:
                    boolean _result29 = isGripSensorMonitorEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 38:
                    boolean _arg021 = data.readBoolean();
                    data.enforceNoDataAvail();
                    triggerBackoffRoutine(_arg021);
                    reply.writeNoException();
                    return true;
                case 39:
                    boolean _arg022 = data.readBoolean();
                    data.enforceNoDataAvail();
                    set5GmmWaveSarBackoffEnabled(_arg022);
                    reply.writeNoException();
                    return true;
                case 40:
                    boolean _arg023 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result30 = setUploadModeEnabled(_arg023);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 41:
                    boolean _result31 = isUploadModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 42:
                    int _result32 = getAdvancedAutohotspotConnectSettings();
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 43:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    setAdvancedAutohotspotConnectSettings(_arg024);
                    reply.writeNoException();
                    return true;
                case 44:
                    int _result33 = getAdvancedAutohotspotLCDSettings();
                    reply.writeNoException();
                    reply.writeInt(_result33);
                    return true;
                case 45:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    setWifiSettingsForegroundState(_arg025);
                    reply.writeNoException();
                    return true;
                case 46:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    setWifiApWarningActivityRunning(_arg026);
                    reply.writeNoException();
                    return true;
                case 47:
                    int _result34 = getWifiApWarningActivityRunningState();
                    reply.writeNoException();
                    reply.writeInt(_result34);
                    return true;
                case 48:
                    clearAutoHotspotLists();
                    reply.writeNoException();
                    return true;
                case 49:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    setAdvancedAutohotspotLCDSettings(_arg027);
                    reply.writeNoException();
                    return true;
                case 50:
                    int _result35 = getChannelUtilization();
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 51:
                    Map _result36 = getChannelUtilizationExtended();
                    reply.writeNoException();
                    reply.writeMap(_result36);
                    return true;
                case 52:
                    int _arg028 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result37 = setRoamTrigger(_arg028);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 53:
                    int _result38 = getRoamTrigger();
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                case 54:
                    int _arg029 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result39 = setRoamDelta(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 55:
                    int _result40 = getRoamDelta();
                    reply.writeNoException();
                    reply.writeInt(_result40);
                    return true;
                case 56:
                    int _arg030 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result41 = setRoamScanPeriod(_arg030);
                    reply.writeNoException();
                    reply.writeBoolean(_result41);
                    return true;
                case 57:
                    int _result42 = getRoamScanPeriod();
                    reply.writeNoException();
                    reply.writeInt(_result42);
                    return true;
                case 58:
                    int _arg031 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result43 = setRoamBand(_arg031);
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 59:
                    int _result44 = getRoamBand();
                    reply.writeNoException();
                    reply.writeInt(_result44);
                    return true;
                case 60:
                    String _arg032 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result45 = setCountryRev(_arg032);
                    reply.writeNoException();
                    reply.writeBoolean(_result45);
                    return true;
                case 61:
                    String _result46 = getCountryRev();
                    reply.writeNoException();
                    reply.writeString(_result46);
                    return true;
                case 62:
                    String _result47 = getCountryCode();
                    reply.writeNoException();
                    reply.writeString(_result47);
                    return true;
                case 63:
                    String _result48 = getWifi7DisabledCountry();
                    reply.writeNoException();
                    reply.writeString(_result48);
                    return true;
                case 64:
                    boolean _result49 = isNCHOModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result49);
                    return true;
                case 65:
                    boolean _arg033 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result50 = setNCHOModeEnabled(_arg033);
                    reply.writeNoException();
                    reply.writeBoolean(_result50);
                    return true;
                case 66:
                    boolean _arg034 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result51 = setRoamScanEnabled(_arg034);
                    reply.writeNoException();
                    reply.writeBoolean(_result51);
                    return true;
                case 67:
                    String[] _arg035 = data.createStringArray();
                    data.enforceNoDataAvail();
                    boolean _result52 = setRoamScanChannels(_arg035);
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 68:
                    boolean _result53 = isWesModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result53);
                    return true;
                case 69:
                    boolean _arg036 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result54 = setWesModeEnabled(_arg036);
                    reply.writeNoException();
                    reply.writeBoolean(_result54);
                    return true;
                case 70:
                    return onTransact$sendVendorSpecificActionFrame$(data, reply);
                case 71:
                    return onTransact$sendReassociationRequestFrame$(data, reply);
                case 72:
                    int _result55 = getSmartMHSLockStatus();
                    reply.writeNoException();
                    reply.writeInt(_result55);
                    return true;
                case 73:
                    int _result56 = canSmartMHSLocked();
                    reply.writeNoException();
                    reply.writeInt(_result56);
                    return true;
                case 74:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result57 = setSmartMHSLocked(_arg037);
                    reply.writeNoException();
                    reply.writeInt(_result57);
                    return true;
                case 75:
                    boolean _arg038 = data.readBoolean();
                    data.enforceNoDataAvail();
                    isClientAcceptedWifiProfileSharing(_arg038);
                    reply.writeNoException();
                    return true;
                case 76:
                    List<SemWifiApBleScanResult> _result58 = getWifiApBleScanDetail();
                    reply.writeNoException();
                    reply.writeTypedList(_result58, 1);
                    return true;
                case 77:
                    boolean _arg039 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result59 = wifiApBleClientRole(_arg039);
                    reply.writeNoException();
                    reply.writeBoolean(_result59);
                    return true;
                case 78:
                    boolean _arg040 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result60 = wifiApBleMhsRole(_arg040);
                    reply.writeNoException();
                    reply.writeBoolean(_result60);
                    return true;
                case 79:
                    return onTransact$connectToSmartMHS$(data, reply);
                case 80:
                    boolean _arg041 = data.readBoolean();
                    data.enforceNoDataAvail();
                    requestStopAutohotspotAdvertisement(_arg041);
                    reply.writeNoException();
                    return true;
                case 81:
                    String _arg042 = data.readString();
                    data.enforceNoDataAvail();
                    int _result61 = getSmartApConnectedStatus(_arg042);
                    reply.writeNoException();
                    reply.writeInt(_result61);
                    return true;
                case 82:
                    return onTransact$registerWifiApSmartCallback$(data, reply);
                case 83:
                    int _arg043 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterWifiApSmartCallback(_arg043);
                    reply.writeNoException();
                    return true;
                case 84:
                    return onTransact$registerWifiApDataUsageCallback$(data, reply);
                case 85:
                    int _arg044 = data.readInt();
                    data.enforceNoDataAvail();
                    unRegisterWifiApDataUsageCallback(_arg044);
                    reply.writeNoException();
                    return true;
                case 86:
                    String _arg045 = data.readString();
                    data.enforceNoDataAvail();
                    int _result62 = getSmartApConnectedStatusFromScanResult(_arg045);
                    reply.writeNoException();
                    reply.writeInt(_result62);
                    return true;
                case 87:
                    List<SemWifiApBleScanResult> _result63 = getWifiApBleD2DScanDetail();
                    reply.writeNoException();
                    reply.writeTypedList(_result63, 1);
                    return true;
                case 88:
                    boolean _arg046 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result64 = wifiApBleD2DClientRole(_arg046);
                    reply.writeNoException();
                    reply.writeBoolean(_result64);
                    return true;
                case 89:
                    boolean _arg047 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result65 = wifiApBleD2DMhsRole(_arg047);
                    reply.writeNoException();
                    reply.writeBoolean(_result65);
                    return true;
                case 90:
                    return onTransact$connectToSmartD2DClient$(data, reply);
                case 91:
                    String _arg048 = data.readString();
                    data.enforceNoDataAvail();
                    int _result66 = getSmartD2DClientConnectedStatus(_arg048);
                    reply.writeNoException();
                    reply.writeInt(_result66);
                    return true;
                case 92:
                    boolean _result67 = isWifiApWpa3Supported();
                    reply.writeNoException();
                    reply.writeBoolean(_result67);
                    return true;
                case 93:
                    return onTransact$setWifiApEnabled$(data, reply);
                case 94:
                    return onTransact$setLocalOnlyHotspotEnabled$(data, reply);
                case 95:
                    SoftApConfiguration _result68 = getSoftApConfiguration();
                    reply.writeNoException();
                    reply.writeTypedObject(_result68, 1);
                    return true;
                case 96:
                    SoftApConfiguration _arg049 =
                            (SoftApConfiguration) data.readTypedObject(SoftApConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    setSoftApConfiguration(_arg049);
                    reply.writeNoException();
                    return true;
                case 97:
                    String _arg050 = data.readString();
                    data.enforceNoDataAvail();
                    String _result69 = getStationInfo(_arg050);
                    reply.writeNoException();
                    reply.writeString(_result69);
                    return true;
                case 98:
                    String _result70 = getTxPower();
                    reply.writeNoException();
                    reply.writeString(_result70);
                    return true;
                case 99:
                    int _result71 = getWifiApFreq();
                    reply.writeNoException();
                    reply.writeInt(_result71);
                    return true;
                case 100:
                    int _arg051 = data.readInt();
                    data.enforceNoDataAvail();
                    setHotspotAntMode(_arg051);
                    reply.writeNoException();
                    return true;
                case 101:
                    int _result72 = getHotspotAntMode();
                    reply.writeNoException();
                    reply.writeInt(_result72);
                    return true;
                case 102:
                    int _arg052 = data.readInt();
                    data.enforceNoDataAvail();
                    setAntMode(_arg052);
                    reply.writeNoException();
                    return true;
                case 103:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    setPowerSavingTime(_arg053);
                    reply.writeNoException();
                    return true;
                case 104:
                    String _arg054 = data.readString();
                    data.enforceNoDataAvail();
                    String _result73 = getMHSConfig(_arg054);
                    reply.writeNoException();
                    reply.writeString(_result73);
                    return true;
                case 105:
                    String _arg055 = data.readString();
                    data.enforceNoDataAvail();
                    String _result74 = setMHSConfig(_arg055);
                    reply.writeNoException();
                    reply.writeString(_result74);
                    return true;
                case 106:
                    int _result75 = getWifiApChannel();
                    reply.writeNoException();
                    reply.writeInt(_result75);
                    return true;
                case 107:
                    int _result76 = getWifiApMaxClient();
                    reply.writeNoException();
                    reply.writeInt(_result76);
                    return true;
                case 108:
                    boolean _result77 = supportWifiAp5GBasedOnCountry();
                    reply.writeNoException();
                    reply.writeBoolean(_result77);
                    return true;
                case 109:
                    boolean _result78 = supportWifiAp6GBasedOnCountry();
                    reply.writeNoException();
                    reply.writeBoolean(_result78);
                    return true;
                case 110:
                    String _result79 = getWifiApStaList();
                    reply.writeNoException();
                    reply.writeString(_result79);
                    return true;
                case 111:
                    boolean _result80 = isWifiSharingSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result80);
                    return true;
                case 112:
                    boolean _result81 = isWifiSharingLiteSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result81);
                    return true;
                case 113:
                    List<String> _result82 = getWifiApStaListDetail();
                    reply.writeNoException();
                    reply.writeStringList(_result82);
                    return true;
                case 114:
                    setWifiApConfigurationToDefault();
                    reply.writeNoException();
                    return true;
                case 115:
                    List<String> _result83 = getWifiApInterfaceNames();
                    reply.writeNoException();
                    reply.writeStringList(_result83);
                    return true;
                case 116:
                    String _result84 = getWifiApInterfaceName();
                    reply.writeNoException();
                    reply.writeString(_result84);
                    return true;
                case 117:
                    String _arg056 = data.readString();
                    data.enforceNoDataAvail();
                    String _result85 = runIptablesRulesCommand(_arg056);
                    reply.writeNoException();
                    reply.writeString(_result85);
                    return true;
                case 118:
                    boolean _arg057 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result86 = setWifiSharingEnabled(_arg057);
                    reply.writeNoException();
                    reply.writeBoolean(_result86);
                    return true;
                case 119:
                    boolean _arg058 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result87 = setProvisionSuccess(_arg058);
                    reply.writeNoException();
                    reply.writeBoolean(_result87);
                    return true;
                case 120:
                    int _result88 = getProvisionSuccess();
                    reply.writeNoException();
                    reply.writeInt(_result88);
                    return true;
                case 121:
                    boolean _result89 = isWifiSharingEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result89);
                    return true;
                case 122:
                    boolean _result90 = isWifiApEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result90);
                    return true;
                case 123:
                    int _result91 = getWifiApConnectedStationCount();
                    reply.writeNoException();
                    reply.writeInt(_result91);
                    return true;
                case 124:
                    int _result92 = getWifiApLOHSState();
                    reply.writeNoException();
                    reply.writeInt(_result92);
                    return true;
                case 125:
                    int _result93 = getIndoorStatus();
                    reply.writeNoException();
                    reply.writeInt(_result93);
                    return true;
                case 126:
                    int _result94 = getRVFModeStatus();
                    reply.writeNoException();
                    reply.writeInt(_result94);
                    return true;
                case 127:
                    int _arg059 = data.readInt();
                    data.enforceNoDataAvail();
                    setRVFmodeStatus(_arg059);
                    reply.writeNoException();
                    return true;
                case 128:
                    String _arg060 = data.readString();
                    data.enforceNoDataAvail();
                    wifiApDisassocSta(_arg060);
                    reply.writeNoException();
                    return true;
                case 129:
                    int _arg061 = data.readInt();
                    data.enforceNoDataAvail();
                    setWifiApMaxClient(_arg061);
                    reply.writeNoException();
                    return true;
                case 130:
                    Message _arg062 = (Message) data.readTypedObject(Message.CREATOR);
                    data.enforceNoDataAvail();
                    resetSoftAp(_arg062);
                    reply.writeNoException();
                    return true;
                case 131:
                    int _arg063 = data.readInt();
                    data.enforceNoDataAvail();
                    setWifiApMaxClientToFramework(_arg063);
                    reply.writeNoException();
                    return true;
                case 132:
                    int _result95 = getWifiApMaxClientFromFramework();
                    reply.writeNoException();
                    reply.writeInt(_result95);
                    return true;
                case 133:
                    boolean _arg064 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWifiApWpsPbc(_arg064);
                    reply.writeNoException();
                    return true;
                case 134:
                    boolean _result96 = getWifiApWpsPbc();
                    reply.writeNoException();
                    reply.writeBoolean(_result96);
                    return true;
                case 135:
                    boolean _arg065 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWifiApIsolate(_arg065);
                    reply.writeNoException();
                    return true;
                case 136:
                    boolean _result97 = getWifiApIsolate();
                    reply.writeNoException();
                    reply.writeBoolean(_result97);
                    return true;
                case 137:
                    int _arg066 = data.readInt();
                    data.enforceNoDataAvail();
                    updateHostapdMacList(_arg066);
                    reply.writeNoException();
                    return true;
                case 138:
                    return onTransact$manageWifiApMacAclList$(data, reply);
                case 139:
                    int _arg067 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result98 = readWifiApMacAclList(_arg067);
                    reply.writeNoException();
                    reply.writeStringList(_result98);
                    return true;
                case 140:
                    int _result99 = getWifiApMacAclMode();
                    reply.writeNoException();
                    reply.writeInt(_result99);
                    return true;
                case 141:
                    int _arg068 = data.readInt();
                    data.enforceNoDataAvail();
                    setWifiApMacAclMode(_arg068);
                    reply.writeNoException();
                    return true;
                case 142:
                    boolean _result100 = isWifiApMacAclEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result100);
                    return true;
                case 143:
                    boolean _arg069 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWifiApMacAclEnable(_arg069);
                    reply.writeNoException();
                    return true;
                case 144:
                    String _arg070 = data.readString();
                    data.enforceNoDataAvail();
                    reportHotspotDumpLogs(_arg070);
                    reply.writeNoException();
                    return true;
                case 145:
                    int _result101 = getWifiApState();
                    reply.writeNoException();
                    reply.writeInt(_result101);
                    return true;
                case 146:
                    boolean _result102 = isWifiApEnabledWithDualBand();
                    reply.writeNoException();
                    reply.writeBoolean(_result102);
                    return true;
                case 147:
                    boolean _arg071 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setArdkPowerSaveMode(_arg071);
                    reply.writeNoException();
                    return true;
                case 148:
                    boolean _arg072 = data.readBoolean();
                    data.enforceNoDataAvail();
                    enableHotspotTsfInfo(_arg072);
                    reply.writeNoException();
                    return true;
                case 149:
                    return onTransact$notifyConnect$(data, reply);
                case 150:
                    int[] _result103 = getSoftApBands();
                    reply.writeNoException();
                    reply.writeIntArray(_result103);
                    return true;
                case 151:
                    boolean _result104 = canAutoHotspotBeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result104);
                    return true;
                case 152:
                    boolean _result105 = isP2pConnected();
                    reply.writeNoException();
                    reply.writeBoolean(_result105);
                    return true;
                case 153:
                    int _arg073 = data.readInt();
                    data.enforceNoDataAvail();
                    setAutohotspotToastMessage(_arg073);
                    reply.writeNoException();
                    return true;
                case 154:
                    int _result106 = getSoftApSecurityType();
                    reply.writeNoException();
                    reply.writeInt(_result106);
                    return true;
                case 155:
                    int _result107 = isDataSaverEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result107);
                    return true;
                case 156:
                    int _result108 = isSoftap11axEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result108);
                    return true;
                case 157:
                    int _result109 = isSoftAp6ENetwork();
                    reply.writeNoException();
                    reply.writeInt(_result109);
                    return true;
                case 158:
                    int _result110 = getSoftApUpStreamNetworkType();
                    reply.writeNoException();
                    reply.writeInt(_result110);
                    return true;
                case 159:
                    String _result111 = getMHSMacFromInterface();
                    reply.writeNoException();
                    reply.writeString(_result111);
                    return true;
                case 160:
                    int _result112 = getSoftApFreq();
                    reply.writeNoException();
                    reply.writeInt(_result112);
                    return true;
                case 161:
                    return onTransact$launchWifiApWarningForMcfMHS$(data, reply);
                case 162:
                    boolean _result113 = isNeededToShowWifiApDatalimitReachedDialog();
                    reply.writeNoException();
                    reply.writeBoolean(_result113);
                    return true;
                case 163:
                    String _result114 = getWifiMACAddress();
                    reply.writeNoException();
                    reply.writeString(_result114);
                    return true;
                case 164:
                    return onTransact$autohotspotWifiScanConnect$(data, reply);
                case 165:
                    String _result115 = getWifiApHostapdFreq();
                    reply.writeNoException();
                    reply.writeString(_result115);
                    return true;
                case 166:
                    String _result116 = getWifiApHostapdSecurtiy();
                    reply.writeNoException();
                    reply.writeString(_result116);
                    return true;
                case 167:
                    boolean _result117 = isMCFClientAutohotspotSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result117);
                    return true;
                case 168:
                    List<SemWifiApBleScanResult> _result118 = getMcfScanDetail();
                    reply.writeNoException();
                    reply.writeTypedList(_result118, 1);
                    return true;
                case 169:
                    boolean _arg074 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result119 = startMcfClientMHSDiscovery(_arg074);
                    reply.writeNoException();
                    reply.writeInt(_result119);
                    return true;
                case 170:
                    boolean _arg075 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result120 = startMcfMHSAdvertisement(_arg075);
                    reply.writeNoException();
                    reply.writeInt(_result120);
                    return true;
                case 171:
                    return onTransact$connectToMcfMHS$(data, reply);
                case 172:
                    String _arg076 = data.readString();
                    data.enforceNoDataAvail();
                    int _result121 = getMcfConnectedStatus(_arg076);
                    reply.writeNoException();
                    reply.writeInt(_result121);
                    return true;
                case 173:
                    String _arg077 = data.readString();
                    data.enforceNoDataAvail();
                    int _result122 = getMcfConnectedStatusFromScanResult(_arg077);
                    reply.writeNoException();
                    reply.writeInt(_result122);
                    return true;
                case 174:
                    return onTransact$setWifiApClientMobileDataLimit$(data, reply);
                case 175:
                    return onTransact$setWifiApClientTimeLimit$(data, reply);
                case 176:
                    return onTransact$setWifiApClientDataPaused$(data, reply);
                case 177:
                    return onTransact$setWifiApClientEditedName$(data, reply);
                case 178:
                    long _arg078 = data.readLong();
                    data.enforceNoDataAvail();
                    setWifiApDailyDataLimit(_arg078);
                    reply.writeNoException();
                    return true;
                case 179:
                    String _arg079 = data.readString();
                    data.enforceNoDataAvail();
                    SemWifiApClientDetails _result123 = getWifiApClientDetails(_arg079);
                    reply.writeNoException();
                    reply.writeTypedObject(_result123, 1);
                    return true;
                case 180:
                    return onTransact$getTopHotspotClientsToday$(data, reply);
                case 181:
                    return onTransact$getTopHotspotClientsTodayAsString$(data, reply);
                case 182:
                    long _result124 = getWifiApTodaysTotalDataUsage();
                    reply.writeNoException();
                    reply.writeLong(_result124);
                    return true;
                case 183:
                    long _result125 = getWifiApDailyDataLimit();
                    reply.writeNoException();
                    reply.writeLong(_result125);
                    return true;
                case 184:
                    return onTransact$getTotalAndTop3ClientsDataUsageBetweenGivenDates$(
                            data, reply);
                case 185:
                    List<String> _result126 = getMonthlyDataUsage();
                    reply.writeNoException();
                    reply.writeStringList(_result126);
                    return true;
                case 186:
                    boolean _result127 = isOverAllMhsDataLimitReached();
                    reply.writeNoException();
                    reply.writeBoolean(_result127);
                    return true;
                case 187:
                    boolean _result128 = isOverAllMhsDataLimitSet();
                    reply.writeNoException();
                    reply.writeBoolean(_result128);
                    return true;
                case 188:
                    String _result129 = wifiApBackUpClientDataUsageSettingsInfo();
                    reply.writeNoException();
                    reply.writeString(_result129);
                    return true;
                case 189:
                    String _arg080 = data.readString();
                    data.enforceNoDataAvail();
                    wifiApRestoreClientDataUsageSettingsInfo(_arg080);
                    reply.writeNoException();
                    return true;
                case 190:
                    long _arg081 = data.readLong();
                    data.enforceNoDataAvail();
                    wifiApRestoreDailyHotspotDataLimit(_arg081);
                    reply.writeNoException();
                    return true;
                case 191:
                    String _arg082 = data.readString();
                    data.enforceNoDataAvail();
                    setWifiApGuestPassword(_arg082);
                    reply.writeNoException();
                    return true;
                case 192:
                    String _result130 = getWifiApGuestPassword();
                    reply.writeNoException();
                    reply.writeString(_result130);
                    return true;
                case 193:
                    boolean _result131 = isWifiApGuestModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result131);
                    return true;
                case 194:
                    boolean _arg083 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWifiApGuestModeEnabled(_arg083);
                    reply.writeNoException();
                    return true;
                case 195:
                    boolean _result132 = isWifiApGuestModeIsolationEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result132);
                    return true;
                case 196:
                    boolean _arg084 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setWifiApGuestModeIsolationEnabled(_arg084);
                    reply.writeNoException();
                    return true;
                case 197:
                    String _arg085 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result133 = isWifiApGuestClient(_arg085);
                    reply.writeNoException();
                    reply.writeBoolean(_result133);
                    return true;
                case 198:
                    boolean _result134 = isSAFamilySupportedBasedOnCountry();
                    reply.writeNoException();
                    reply.writeBoolean(_result134);
                    return true;
                case 199:
                    return onTransact$registerClientListDataUsageCallback$(data, reply);
                case 200:
                    int _arg086 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterClientListDataUsageCallback(_arg086);
                    reply.writeNoException();
                    return true;
                case 201:
                    return onTransact$registerClientDataUsageCallback$(data, reply);
                case 202:
                    int _arg087 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterClientDataUsageCallback(_arg087);
                    reply.writeNoException();
                    return true;
                case 203:
                    return onTransact$reportBigData$(data, reply);
                case 204:
                    return onTransact$addOrUpdateWifiControlHistory$(data, reply);
                case 205:
                    String _result135 = getWifiEnableHistory();
                    reply.writeNoException();
                    reply.writeString(_result135);
                    return true;
                case 206:
                    SemWifiConfiguration _arg088 =
                            (SemWifiConfiguration)
                                    data.readTypedObject(SemWifiConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result136 = addOrUpdateNetwork(_arg088);
                    reply.writeNoException();
                    reply.writeBoolean(_result136);
                    return true;
                case 207:
                    String _arg089 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result137 = removeNetwork(_arg089);
                    reply.writeNoException();
                    reply.writeBoolean(_result137);
                    return true;
                case 208:
                    factoryReset();
                    return true;
                case 209:
                    resetDeveloperOptionsSettings();
                    return true;
                case 210:
                    ParceledListSlice _result138 = getConfiguredNetworks();
                    reply.writeNoException();
                    reply.writeTypedObject(_result138, 1);
                    return true;
                case 211:
                    return onTransact$allowAutojoinPasspoint$(data, reply);
                case 212:
                    List _result139 = getPasspointConfigurations();
                    reply.writeNoException();
                    reply.writeList(_result139);
                    return true;
                case 213:
                    int _arg090 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result140 = getIssueDetectorDump(_arg090);
                    reply.writeNoException();
                    reply.writeString(_result140);
                    return true;
                case 214:
                    return onTransact$reportIssue$(data, reply);
                case 215:
                    Bundle _arg091 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    updateGuiderFeature(_arg091);
                    return true;
                case 216:
                    List<String> _result141 = getDiagnosisResults();
                    reply.writeNoException();
                    reply.writeStringList(_result141);
                    return true;
                case 217:
                    Bundle _arg092 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    startIssueMonitoring(_arg092);
                    return true;
                case 218:
                    int _arg093 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result142 = getSilentRoamingDump(_arg093);
                    reply.writeNoException();
                    reply.writeString(_result142);
                    return true;
                case 219:
                    String _arg094 = data.readString();
                    data.enforceNoDataAvail();
                    String _result143 = getConnectivityLog(_arg094);
                    reply.writeNoException();
                    reply.writeString(_result143);
                    return true;
                case 220:
                    List<String> _arg095 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    Map _result144 = getQoSScores(_arg095);
                    reply.writeNoException();
                    reply.writeMap(_result144);
                    return true;
                case 221:
                    String _arg096 = data.readString();
                    data.enforceNoDataAvail();
                    setBtmOptionUserEnabled(_arg096);
                    return true;
                case 222:
                    String _arg097 = data.readString();
                    data.enforceNoDataAvail();
                    setBtmOptionUserDisabled(_arg097);
                    return true;
                case 223:
                    return onTransact$registerPasswordCallback$(data, reply);
                case 224:
                    ISemSharedPasswordCallback _arg098 =
                            ISemSharedPasswordCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterPasswordCallback(_arg098);
                    return true;
                case 225:
                    boolean _arg099 = data.readBoolean();
                    data.enforceNoDataAvail();
                    requestPassword(_arg099);
                    return true;
                case 226:
                    return onTransact$setUserConfirmForSharingPassword$(data, reply);
                case 227:
                    boolean _result145 = isSupportedQoSProvider();
                    reply.writeNoException();
                    reply.writeBoolean(_result145);
                    return true;
                case 228:
                    boolean _result146 = isSupportedProfileRequest();
                    reply.writeNoException();
                    reply.writeBoolean(_result146);
                    return true;
                case 229:
                    String _result147 = getProfileShareDump();
                    reply.writeNoException();
                    reply.writeString(_result147);
                    return true;
                case 230:
                    String _result148 = getAutoShareDump();
                    reply.writeNoException();
                    reply.writeString(_result148);
                    return true;
                case 231:
                    List<String> _arg0100 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    runAutoShareForCurrent(_arg0100);
                    return true;
                case 232:
                    boolean _result149 = isSupportedAutoWifi();
                    reply.writeNoException();
                    reply.writeBoolean(_result149);
                    return true;
                case 233:
                    boolean _result150 = getAutoWifiDefaultValue();
                    reply.writeNoException();
                    reply.writeBoolean(_result150);
                    return true;
                case 234:
                    boolean _result151 = shouldShowAutoWifiBubbleTip();
                    reply.writeNoException();
                    reply.writeBoolean(_result151);
                    return true;
                case 235:
                    boolean _result152 = isAvailableAutoWifiScan();
                    reply.writeNoException();
                    reply.writeBoolean(_result152);
                    return true;
                case 236:
                    String _result153 = getAutoWifiDump();
                    reply.writeNoException();
                    reply.writeString(_result153);
                    return true;
                case 237:
                    Map _result154 = getConfiguredNetworkLocations();
                    reply.writeNoException();
                    reply.writeMap(_result154);
                    return true;
                case 238:
                    String _arg0101 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result155 = hasConfiguredNetworkLocations(_arg0101);
                    reply.writeNoException();
                    reply.writeBoolean(_result155);
                    return true;
                case 239:
                    return onTransact$setTestSettings$(data, reply);
                case 240:
                    return onTransact$setAllowWifiScan$(data, reply);
                case 241:
                    boolean _result156 = isScanningEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result156);
                    return true;
                case 242:
                    String _arg0102 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result157 = startScan(_arg0102);
                    reply.writeNoException();
                    reply.writeBoolean(_result157);
                    return true;
                case 243:
                    return onTransact$setEasySetupScanSettings$(data, reply);
                case 244:
                    Map<String, SemEasySetupWifiScanSettings> _result158 =
                            getEasySetupScanSettings();
                    reply.writeNoException();
                    if (_result158 == null) {
                        reply.writeInt(-1);
                    } else {
                        reply.writeInt(_result158.size());
                        _result158.forEach(
                                new BiConsumer() { // from class:
                                                   // com.samsung.android.wifi.ISemWifiManager$Stub$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        ISemWifiManager.Stub.lambda$onTransact$0(
                                                Parcel.this,
                                                (String) obj,
                                                (SemEasySetupWifiScanSettings) obj2);
                                    }
                                });
                    }
                    return true;
                case 245:
                    disableRandomMac();
                    return true;
                case 246:
                    boolean _arg0103 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setImsCallEstablished(_arg0103);
                    return true;
                case 247:
                    int _result159 = getWcmEverQualityTested();
                    reply.writeNoException();
                    reply.writeInt(_result159);
                    return true;
                case 248:
                    int _result160 = getWifiIconVisibility();
                    reply.writeNoException();
                    reply.writeInt(_result160);
                    return true;
                case 249:
                    int _result161 = getCurrentStatusMode();
                    reply.writeNoException();
                    reply.writeInt(_result161);
                    return true;
                case 250:
                    int _result162 = getValidState();
                    reply.writeNoException();
                    reply.writeInt(_result162);
                    return true;
                case 251:
                    notifyReachabilityLost();
                    reply.writeNoException();
                    return true;
                case 252:
                    boolean _arg0104 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setConnectivityCheckDisabled(_arg0104);
                    reply.writeNoException();
                    return true;
                case 253:
                    boolean _arg0105 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setKeepConnectionAlways(_arg0105);
                    return true;
                case 254:
                    return onTransact$setKeepConnection$(data, reply);
                case 255:
                    int _arg0106 = data.readInt();
                    data.enforceNoDataAvail();
                    setKeepConnectionBigData(_arg0106);
                    return true;
                case 256:
                    int _arg0107 = data.readInt();
                    data.enforceNoDataAvail();
                    removeExcludedNetwork(_arg0107);
                    reply.writeNoException();
                    return true;
                case 257:
                    String _result163 = retrieveSemWifiConfigsBackupData();
                    reply.writeNoException();
                    reply.writeString(_result163);
                    return true;
                case 258:
                    String _arg0108 = data.readString();
                    data.enforceNoDataAvail();
                    restoreSemConfigurationsBackupData(_arg0108);
                    reply.writeNoException();
                    return true;
                case 259:
                    return onTransact$setConnectionAttemptInfo$(data, reply);
                case 260:
                    return onTransact$restoreIWCSettingsValue$(data, reply);
                case 261:
                    String _result164 = getIWCQTables();
                    reply.writeNoException();
                    reply.writeString(_result164);
                    return true;
                case 262:
                    String _arg0109 = data.readString();
                    data.enforceNoDataAvail();
                    setIWCQTables(_arg0109);
                    reply.writeNoException();
                    return true;
                case 263:
                    long _arg0110 = data.readLong();
                    data.enforceNoDataAvail();
                    updateIWCHintCard(_arg0110);
                    reply.writeNoException();
                    return true;
                case 264:
                    int _arg0111 = data.readInt();
                    data.enforceNoDataAvail();
                    setIWCMockAction(_arg0111);
                    reply.writeNoException();
                    return true;
                case 265:
                    boolean _arg0112 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result165 = disconnectApBlockAutojoin(_arg0112);
                    reply.writeNoException();
                    reply.writeBoolean(_result165);
                    return true;
                case 266:
                    int _arg0113 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result166 = setOptimizerForceControlMode(_arg0113);
                    reply.writeNoException();
                    reply.writeBoolean(_result166);
                    return true;
                case 267:
                    int _result167 = getOptimizerForceControlMode();
                    reply.writeNoException();
                    reply.writeInt(_result167);
                    return true;
                case 268:
                    int[] _result168 = getOptimizerState();
                    reply.writeNoException();
                    reply.writeIntArray(_result168);
                    return true;
                case 269:
                    int[] _result169 = getServiceDetectionResult();
                    reply.writeNoException();
                    reply.writeIntArray(_result169);
                    return true;
                case 270:
                    Bundle _arg0114 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    setTrafficPatternTestSettings(_arg0114);
                    return true;
                case 271:
                    return onTransact$setWifiUwbCoexEnabled$(data, reply);
                case 272:
                    return onTransact$setLatencyCritical$(data, reply);
                case 273:
                    return onTransact$setPktlogFilter$(data, reply);
                case 274:
                    return onTransact$removePktlogFilter$(data, reply);
                case 275:
                    boolean _result170 = saveFwDump();
                    reply.writeNoException();
                    reply.writeBoolean(_result170);
                    return true;
                case 276:
                    String _arg0115 = data.readString();
                    data.enforceNoDataAvail();
                    int _result171 = getRssi(_arg0115);
                    reply.writeNoException();
                    reply.writeInt(_result171);
                    return true;
                case 277:
                    String _result172 = getWifiStaInfo();
                    reply.writeNoException();
                    reply.writeString(_result172);
                    return true;
                case 278:
                    int _result173 = getNumOfWifiAnt();
                    reply.writeNoException();
                    reply.writeInt(_result173);
                    return true;
                case 279:
                    startTimerForWifiOffload();
                    reply.writeNoException();
                    return true;
                case 280:
                    String _arg0116 = data.readString();
                    data.enforceNoDataAvail();
                    checkAppForWiFiOffloading(_arg0116);
                    reply.writeNoException();
                    return true;
                case 281:
                    return onTransact$setTCRule$(data, reply);
                case 282:
                    return onTransact$externalTwtInterface$(data, reply);
                case 283:
                    int[] _result174 = getTWTParams();
                    reply.writeNoException();
                    reply.writeIntArray(_result174);
                    return true;
                case 284:
                    Map _result175 = getCtlFeatureState();
                    reply.writeNoException();
                    reply.writeMap(_result175);
                    return true;
                case 285:
                    int _arg0117 = data.readInt();
                    data.enforceNoDataAvail();
                    resetCallbackCondition(_arg0117);
                    reply.writeNoException();
                    return true;
                case 286:
                    resetComebackCondition();
                    reply.writeNoException();
                    return true;
                case 287:
                    int _result176 = getCurrentL2TransitionMode();
                    reply.writeNoException();
                    reply.writeInt(_result176);
                    return true;
                case 288:
                    String _result177 = getL2TransitionLog();
                    reply.writeNoException();
                    reply.writeString(_result177);
                    return true;
                case 289:
                    String _result178 = getNumberOfDataInEachRssiLevel();
                    reply.writeNoException();
                    reply.writeString(_result178);
                    return true;
                case 290:
                    String _result179 = getIwhState();
                    reply.writeNoException();
                    reply.writeString(_result179);
                    return true;
                case 291:
                    boolean _arg0118 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSamsungMloCtrl(_arg0118);
                    reply.writeNoException();
                    return true;
                case 292:
                    boolean _arg0119 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSamsungIwhCtrl(_arg0119);
                    reply.writeNoException();
                    return true;
                case 293:
                    boolean _result180 = getSamsungMloCtrl();
                    reply.writeNoException();
                    reply.writeBoolean(_result180);
                    return true;
                case 294:
                    boolean _result181 = getSamsungIwhCtrl();
                    reply.writeNoException();
                    reply.writeBoolean(_result181);
                    return true;
                case 295:
                    boolean _arg0120 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setTestMode(_arg0120);
                    reply.writeNoException();
                    return true;
                case 296:
                    boolean _result182 = iwhIntendedDisconnection();
                    reply.writeNoException();
                    reply.writeBoolean(_result182);
                    return true;
                case 297:
                    return onTransact$linkQosQuery$(data, reply);
                case 298:
                    return onTransact$setWifiAiServiceState$(data, reply);
                case 299:
                    return onTransact$setWifiAiServiceNsdResult$(data, reply);
                case 300:
                    return onTransact$setWifiAiIwhTrainingResult$(data, reply);
                case 301:
                    boolean[] _arg0121 = data.createBooleanArray();
                    data.enforceNoDataAvail();
                    setWifiAiIwhInferenceResult(_arg0121);
                    return true;
                case 302:
                    return onTransact$setIlaTrainingResult$(data, reply);
                case 303:
                    int _arg0122 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result183 = getTcpMonitorSocketForegroundHistory(_arg0122);
                    reply.writeNoException();
                    reply.writeString(_result183);
                    return true;
                case 304:
                    int _arg0123 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result184 = getTcpMonitorAllSocketHistory(_arg0123);
                    reply.writeNoException();
                    reply.writeString(_result184);
                    return true;
                case 305:
                    int _arg0124 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result185 = getTcpMonitorDnsHistory(_arg0124);
                    reply.writeNoException();
                    reply.writeString(_result185);
                    return true;
                case 306:
                    boolean _result186 = isIndividualAppSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result186);
                    return true;
                case 307:
                    int _arg0125 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result187 = getWifiUsabilityStatsEntry(_arg0125);
                    reply.writeNoException();
                    reply.writeString(_result187);
                    return true;
                case 308:
                    boolean _result188 = isAvailableTdls();
                    reply.writeNoException();
                    reply.writeBoolean(_result188);
                    return true;
                case 309:
                    boolean _result189 = isWiderBandwidthTdlsSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result189);
                    return true;
                case 310:
                    return onTransact$setTdlsEnabled$(data, reply);
                case 311:
                    int _result190 = getMaxTdlsSession();
                    reply.writeNoException();
                    reply.writeInt(_result190);
                    return true;
                case 312:
                    int _result191 = getNumOfTdlsSession();
                    reply.writeNoException();
                    reply.writeInt(_result191);
                    return true;
                case 313:
                    List<String> _result192 = getMHSClientTrafficDetails();
                    reply.writeNoException();
                    reply.writeStringList(_result192);
                    return true;
                case 314:
                    int _result193 = getNRTTrafficbandwidth();
                    reply.writeNoException();
                    reply.writeInt(_result193);
                    return true;
                case 315:
                    long[] _result194 = getDataConsumedValues();
                    reply.writeNoException();
                    reply.writeLongArray(_result194);
                    return true;
                case 316:
                    resetTotalPriorityDataConsumedValues();
                    reply.writeNoException();
                    return true;
                case 317:
                    Map _result195 = getTasAverage();
                    reply.writeNoException();
                    reply.writeMap(_result195);
                    return true;
                case 318:
                    return onTransact$setTasPolicy$(data, reply);
                case 319:
                    return onTransact$registerTasPolicyChangedListener$(data, reply);
                case 320:
                    return onTransact$unregisterTasPolicyChangedListener$(data, reply);
                case 321:
                    return onTransact$enableTxPowerLogging$(data, reply);
                case 322:
                    String _result196 = getDynamicFeatureStatus();
                    reply.writeNoException();
                    reply.writeString(_result196);
                    return true;
                case 323:
                    boolean _result197 = isSwitchToMobileDataDefaultOff();
                    reply.writeNoException();
                    reply.writeBoolean(_result197);
                    return true;
                case 324:
                    return onTransact$setMhsAiServiceState$(data, reply);
                case 325:
                    return onTransact$setMhsAiServiceNsdResult$(data, reply);
                case 326:
                    return onTransact$startCapture$(data, reply);
                case 327:
                    int _result198 = stopCapture();
                    reply.writeNoException();
                    reply.writeInt(_result198);
                    return true;
                case 328:
                    int _result199 = isCaptureRunning();
                    reply.writeNoException();
                    reply.writeInt(_result199);
                    return true;
                case 329:
                    boolean _result200 = getIsPacketCaptureSupportedByDriver();
                    reply.writeNoException();
                    reply.writeBoolean(_result200);
                    return true;
                case 330:
                    return onTransact$setMcfMultiControlMode$(data, reply);
                case 331:
                    return onTransact$registerAbTestConfigUpdateObserver$(data, reply);
                case 332:
                    return onTransact$unregisterAbTestConfigUpdateObserver$(data, reply);
                case 333:
                    return onTransact$reportAbTestResult$(data, reply);
                case 334:
                    List<SemAbTestConfiguration> _result201 = getAbTestConfigs();
                    reply.writeNoException();
                    reply.writeTypedList(_result201, 1);
                    return true;
                case 335:
                    return onTransact$getAbTestConfiguredModule$(data, reply);
                case 336:
                    return onTransact$sendReassociationFrequencyRequestFrame$(data, reply);
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(
                Parcel reply, String k, SemEasySetupWifiScanSettings v) {
            reply.writeString(k);
            reply.writeTypedObject(v, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements ISemWifiManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMaxDtimInSuspendMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setDtimInSuspendMode(int interval) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(interval);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setVerboseLoggingEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void blockFccChannelBackoff(boolean choice) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(choice);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiDeveloperModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiDeveloperModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiFirmwareVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiCid() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiVersions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getFactoryMacAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getAntInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getFrameburstInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getPsmInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiSupportedFeatureSet() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getVendorWlanDriverProp(String propName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(propName);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setVendorWlanDriverProp(String propName, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(propName);
                    _data.writeString(value);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean removeFactoryMacAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setFactoryMacAddress(String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(data);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setFccChannelBackoffEnabled(String interfaceName, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(interfaceName);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setPsmInfo(String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(data);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setAntInfo(String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(data);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setFrameburstInfo(String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(data);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setDcxoCalibrationData(String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(data);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getDcxoCalibrationData() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Bundle getCurrentWifiRouterInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Bundle getWifiRouterInfo(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoBestEffort(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoPresentable(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Bundle getWifiRouterInfoByBssid(String bssid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bssid);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoBestEffortByBssid(String bssid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bssid);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoPresentableByBssid(String bssid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bssid);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getNetworkLastUpdatedTimeMap() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
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

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCurrentStateAndEnterTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long[] getNetworkUsageInfo(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getDailyUsageInfo(int daysAgo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(daysAgo);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setGripSensorMonitorEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isGripSensorMonitorEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void triggerBackoffRoutine(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void set5GmmWaveSarBackoffEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setUploadModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isUploadModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getAdvancedAutohotspotConnectSettings() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAdvancedAutohotspotConnectSettings(int val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(val);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getAdvancedAutohotspotLCDSettings() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiSettingsForegroundState(int val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(val);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApWarningActivityRunning(int val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(val);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApWarningActivityRunningState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void clearAutoHotspotLists() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAdvancedAutohotspotLCDSettings(int val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(val);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getChannelUtilization() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getChannelUtilizationExtended() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamTrigger(int roamTrigger) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(roamTrigger);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamDelta(int roamDelta) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(roamDelta);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamDelta() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamScanPeriod(int roamScanPeriod) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(roamScanPeriod);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamScanPeriod() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamBand(int band) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(band);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamBand() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setCountryRev(String countryRev) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(countryRev);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCountryRev() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCountryCode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifi7DisabledCountry() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isNCHOModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setNCHOModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamScanEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamScanChannels(String[] channels) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStringArray(channels);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWesModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWesModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean sendVendorSpecificActionFrame(
                    String bssid, int channel, int dwellTime, String frameBody)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bssid);
                    _data.writeInt(channel);
                    _data.writeInt(dwellTime);
                    _data.writeString(frameBody);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean sendReassociationRequestFrame(String bssid, int channel)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bssid);
                    _data.writeInt(channel);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartMHSLockStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int canSmartMHSLocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int setSmartMHSLocked(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void isClientAcceptedWifiProfileSharing(boolean val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(val);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApBleScanResult> getWifiApBleScanDetail() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    List<SemWifiApBleScanResult> _result =
                            _reply.createTypedArrayList(SemWifiApBleScanResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleClientRole(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleMhsRole(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean connectToSmartMHS(
                    String addr,
                    int type,
                    int mhidden,
                    int mSecurity,
                    String mhs_mac,
                    String Username,
                    int ver,
                    boolean wifiprofileshare)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(addr);
                    _data.writeInt(type);
                    _data.writeInt(mhidden);
                    _data.writeInt(mSecurity);
                    _data.writeString(mhs_mac);
                    _data.writeString(Username);
                    _data.writeInt(ver);
                    _data.writeBoolean(wifiprofileshare);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void requestStopAutohotspotAdvertisement(boolean val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(val);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartApConnectedStatus(String mhs_mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mhs_mac);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerWifiApSmartCallback(
                    IBinder binder, ISemWifiApSmartCallback callback, int callbackIdentifier)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(callbackIdentifier);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterWifiApSmartCallback(int callbackIdentifier)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerWifiApDataUsageCallback(
                    IBinder binder, ISemWifiApDataUsageCallback callback, int callbackIdentifier)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(callbackIdentifier);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unRegisterWifiApDataUsageCallback(int callbackIdentifier)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartApConnectedStatusFromScanResult(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApBleScanResult> getWifiApBleD2DScanDetail() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    List<SemWifiApBleScanResult> _result =
                            _reply.createTypedArrayList(SemWifiApBleScanResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleD2DClientRole(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleD2DMhsRole(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean connectToSmartD2DClient(
                    String bleaddr, String client_mac, ISemWifiApSmartCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bleaddr);
                    _data.writeString(client_mac);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartD2DClientConnectedStatus(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApWpa3Supported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWifiApEnabled(SoftApConfiguration mSoftApConfig, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(mSoftApConfig, 0);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setLocalOnlyHotspotEnabled(
                    boolean enabled, String ssid, String password, int band)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeString(ssid);
                    _data.writeString(password);
                    _data.writeInt(band);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public SoftApConfiguration getSoftApConfiguration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    SoftApConfiguration _result =
                            (SoftApConfiguration)
                                    _reply.readTypedObject(SoftApConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setSoftApConfiguration(SoftApConfiguration mConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(mConfig, 0);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getStationInfo(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTxPower() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApFreq() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setHotspotAntMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getHotspotAntMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAntMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setPowerSavingTime(int min) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(min);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getMHSConfig(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String setMHSConfig(String jsonMIFI) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(jsonMIFI);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApChannel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApMaxClient() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean supportWifiAp5GBasedOnCountry() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean supportWifiAp6GBasedOnCountry() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApStaList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiSharingSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiSharingLiteSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getWifiApStaListDetail() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApConfigurationToDefault() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getWifiApInterfaceNames() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApInterfaceName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String runIptablesRulesCommand(String cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(cmd);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWifiSharingEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setProvisionSuccess(boolean set) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(set);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getProvisionSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiSharingEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApConnectedStationCount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApLOHSState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getIndoorStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(125, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRVFModeStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setRVFmodeStatus(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void wifiApDisassocSta(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMaxClient(int num) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(num);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetSoftAp(Message msg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(msg, 0);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMaxClientToFramework(int num) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(num);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApMaxClientFromFramework() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApWpsPbc(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(133, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getWifiApWpsPbc() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApIsolate(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(135, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getWifiApIsolate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void updateHostapdMacList(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(137, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int manageWifiApMacAclList(
                    String name, String mac, int add_or_delete, int allow_or_deny)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(mac);
                    _data.writeInt(add_or_delete);
                    _data.writeInt(allow_or_deny);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> readWifiApMacAclList(int allow_or_deny) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(allow_or_deny);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApMacAclMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMacAclMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(141, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApMacAclEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(142, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMacAclEnable(boolean val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(val);
                    this.mRemote.transact(143, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportHotspotDumpLogs(String logs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(logs);
                    this.mRemote.transact(144, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(145, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApEnabledWithDualBand() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(146, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setArdkPowerSaveMode(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(147, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void enableHotspotTsfInfo(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(148, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void notifyConnect(int netId, String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(key);
                    this.mRemote.transact(149, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getSoftApBands() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(150, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean canAutoHotspotBeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isP2pConnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAutohotspotToastMessage(int noti) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(noti);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSoftApSecurityType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(154, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isDataSaverEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isSoftap11axEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isSoftAp6ENetwork() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSoftApUpStreamNetworkType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(158, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getMHSMacFromInterface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSoftApFreq() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(160, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void launchWifiApWarningForMcfMHS(
                    int wifiap_band, int wifiap_set_security, boolean wifiap_security)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(wifiap_band);
                    _data.writeInt(wifiap_set_security);
                    _data.writeBoolean(wifiap_security);
                    this.mRemote.transact(161, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isNeededToShowWifiApDatalimitReachedDialog() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(162, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiMACAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(163, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int autohotspotWifiScanConnect(
                    String ssid,
                    String password,
                    String bssid,
                    int hideSSID,
                    int mhsFreq,
                    int security)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(ssid);
                    _data.writeString(password);
                    _data.writeString(bssid);
                    _data.writeInt(hideSSID);
                    _data.writeInt(mhsFreq);
                    _data.writeInt(security);
                    this.mRemote.transact(164, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApHostapdFreq() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(165, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApHostapdSecurtiy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(166, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isMCFClientAutohotspotSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(167, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApBleScanResult> getMcfScanDetail() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(168, _data, _reply, 0);
                    _reply.readException();
                    List<SemWifiApBleScanResult> _result =
                            _reply.createTypedArrayList(SemWifiApBleScanResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int startMcfClientMHSDiscovery(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(169, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int startMcfMHSAdvertisement(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(170, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int connectToMcfMHS(
                    String addr,
                    int type,
                    int mhidden,
                    int mSecurity,
                    String mhs_mac,
                    String Username,
                    int ver)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(addr);
                    _data.writeInt(type);
                    _data.writeInt(mhidden);
                    _data.writeInt(mSecurity);
                    _data.writeString(mhs_mac);
                    _data.writeString(Username);
                    _data.writeInt(ver);
                    this.mRemote.transact(171, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getMcfConnectedStatus(String mhs_mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mhs_mac);
                    this.mRemote.transact(172, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getMcfConnectedStatusFromScanResult(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(173, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientMobileDataLimit(String mac, long val)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    _data.writeLong(val);
                    this.mRemote.transact(174, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientTimeLimit(String mac, long val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    _data.writeLong(val);
                    this.mRemote.transact(175, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientDataPaused(String mac, boolean val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    _data.writeBoolean(val);
                    this.mRemote.transact(176, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientEditedName(String mac, String val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    _data.writeString(val);
                    this.mRemote.transact(177, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApDailyDataLimit(long bytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeLong(bytes);
                    this.mRemote.transact(178, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public SemWifiApClientDetails getWifiApClientDetails(String mac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(179, _data, _reply, 0);
                    _reply.readException();
                    SemWifiApClientDetails _result =
                            (SemWifiApClientDetails)
                                    _reply.readTypedObject(SemWifiApClientDetails.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApClientDetails> getTopHotspotClientsToday(
                    int topConnectedAndDisconnected, int maxListLength) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(topConnectedAndDisconnected);
                    _data.writeInt(maxListLength);
                    this.mRemote.transact(180, _data, _reply, 0);
                    _reply.readException();
                    List<SemWifiApClientDetails> _result =
                            _reply.createTypedArrayList(SemWifiApClientDetails.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTopHotspotClientsTodayAsString(
                    int topConnectedAndDisconnected, int maxListLength) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(topConnectedAndDisconnected);
                    _data.writeInt(maxListLength);
                    this.mRemote.transact(181, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long getWifiApTodaysTotalDataUsage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(182, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long getWifiApDailyDataLimit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(183, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(
                    long timestampInMilliSecsDate1, long timestampInMilliSecsDate2)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeLong(timestampInMilliSecsDate1);
                    _data.writeLong(timestampInMilliSecsDate2);
                    this.mRemote.transact(184, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getMonthlyDataUsage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(185, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isOverAllMhsDataLimitReached() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(186, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isOverAllMhsDataLimitSet() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(187, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String wifiApBackUpClientDataUsageSettingsInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(188, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void wifiApRestoreClientDataUsageSettingsInfo(String jsonString)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(jsonString);
                    this.mRemote.transact(189, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void wifiApRestoreDailyHotspotDataLimit(long bytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeLong(bytes);
                    this.mRemote.transact(190, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApGuestPassword(String pwd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(pwd);
                    this.mRemote.transact(191, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApGuestPassword() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(192, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApGuestModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(193, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApGuestModeEnabled(boolean val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(val);
                    this.mRemote.transact(194, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApGuestModeIsolationEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(195, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApGuestModeIsolationEnabled(boolean val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(val);
                    this.mRemote.transact(196, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApGuestClient(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(197, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSAFamilySupportedBasedOnCountry() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(198, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerClientListDataUsageCallback(
                    IBinder binder,
                    ISemWifiApClientListUpdateCallback callbackToRegister,
                    int callbackIdentifier,
                    int topConnectedAndDisconnected,
                    int maxListLength)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongInterface(callbackToRegister);
                    _data.writeInt(callbackIdentifier);
                    _data.writeInt(topConnectedAndDisconnected);
                    _data.writeInt(maxListLength);
                    this.mRemote.transact(199, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterClientListDataUsageCallback(int callbackIdentifier)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    this.mRemote.transact(200, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerClientDataUsageCallback(
                    IBinder binder,
                    ISemWifiApClientUpdateCallback callbackToRegister,
                    int callbackIdentifier,
                    String clientMac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeStrongInterface(callbackToRegister);
                    _data.writeInt(callbackIdentifier);
                    _data.writeString(clientMac);
                    this.mRemote.transact(201, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterClientDataUsageCallback(int callbackIdentifier)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(callbackIdentifier);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportBigData(String featureName, String parameters)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(featureName);
                    _data.writeString(parameters);
                    this.mRemote.transact(203, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void addOrUpdateWifiControlHistory(String packageName, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(204, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiEnableHistory() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean addOrUpdateNetwork(SemWifiConfiguration config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean removeNetwork(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void factoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(208, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetDeveloperOptionsSettings() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(209, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public ParceledListSlice getConfiguredNetworks() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void allowAutojoinPasspoint(String fqdn, boolean allowAutojoin)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(fqdn);
                    _data.writeBoolean(allowAutojoin);
                    this.mRemote.transact(211, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List getPasspointConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(212, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIssueDetectorDump(int maxCount) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(maxCount);
                    this.mRemote.transact(213, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportIssue(int reportId, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(reportId);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(214, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void updateGuiderFeature(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(215, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getDiagnosisResults() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(216, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void startIssueMonitoring(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(217, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getSilentRoamingDump(int maxCount) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(maxCount);
                    this.mRemote.transact(218, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getConnectivityLog(String category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(category);
                    this.mRemote.transact(219, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getQoSScores(List<String> bssids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStringList(bssids);
                    this.mRemote.transact(220, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setBtmOptionUserEnabled(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(221, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setBtmOptionUserDisabled(String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    this.mRemote.transact(222, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerPasswordCallback(
                    String configKey, ISemSharedPasswordCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(configKey);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(223, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterPasswordCallback(ISemSharedPasswordCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(224, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void requestPassword(boolean showConfirm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(showConfirm);
                    this.mRemote.transact(225, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setUserConfirmForSharingPassword(boolean isAccept, String userData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(isAccept);
                    _data.writeString(userData);
                    this.mRemote.transact(226, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSupportedQoSProvider() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(227, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSupportedProfileRequest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(228, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getProfileShareDump() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(229, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getAutoShareDump() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(230, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void runAutoShareForCurrent(List<String> target) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStringList(target);
                    this.mRemote.transact(231, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSupportedAutoWifi() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(232, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getAutoWifiDefaultValue() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(233, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean shouldShowAutoWifiBubbleTip() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(234, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isAvailableAutoWifiScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(235, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getAutoWifiDump() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(236, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getConfiguredNetworkLocations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(237, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean hasConfiguredNetworkLocations(String wifiConfigKey)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(wifiConfigKey);
                    this.mRemote.transact(238, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTestSettings(int moduleId, Bundle settings) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(moduleId);
                    _data.writeTypedObject(settings, 0);
                    this.mRemote.transact(239, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAllowWifiScan(boolean enable, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeString(packageName);
                    this.mRemote.transact(240, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isScanningEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(241, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean startScan(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(242, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setEasySetupScanSettings(
                    String packageName, SemEasySetupWifiScanSettings settings)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(settings, 0);
                    this.mRemote.transact(243, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map<String, SemEasySetupWifiScanSettings> getEasySetupScanSettings()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(244, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, SemEasySetupWifiScanSettings> _result =
                            N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N)
                            .forEach(
                                    new IntConsumer() { // from class:
                                                        // com.samsung.android.wifi.ISemWifiManager$Stub$Proxy$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntConsumer
                                        public final void accept(int i) {
                                            ISemWifiManager.Stub.Proxy
                                                    .lambda$getEasySetupScanSettings$0(
                                                            Parcel.this, _result, i);
                                        }
                                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getEasySetupScanSettings$0(
                    Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                SemEasySetupWifiScanSettings v =
                        (SemEasySetupWifiScanSettings)
                                _reply.readTypedObject(SemEasySetupWifiScanSettings.CREATOR);
                _result.put(k, v);
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void disableRandomMac() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(245, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setImsCallEstablished(boolean isEstablished) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(isEstablished);
                    this.mRemote.transact(246, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWcmEverQualityTested() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(247, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiIconVisibility() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(248, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getCurrentStatusMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(249, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getValidState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(250, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void notifyReachabilityLost() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(251, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setConnectivityCheckDisabled(boolean disabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(disabled);
                    this.mRemote.transact(252, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setKeepConnectionAlways(boolean keepConnection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(keepConnection);
                    this.mRemote.transact(253, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setKeepConnection(boolean keepConnection, boolean always)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(keepConnection);
                    _data.writeBoolean(always);
                    this.mRemote.transact(254, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setKeepConnectionBigData(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(255, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void removeExcludedNetwork(int networkId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(networkId);
                    this.mRemote.transact(256, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String retrieveSemWifiConfigsBackupData() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(257, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void restoreSemConfigurationsBackupData(String semconfigs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(semconfigs);
                    this.mRemote.transact(258, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setConnectionAttemptInfo(int netId, boolean byUser, String configKey)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeBoolean(byUser);
                    _data.writeString(configKey);
                    this.mRemote.transact(259, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void restoreIWCSettingsValue(int opType, int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(opType);
                    _data.writeInt(value);
                    this.mRemote.transact(260, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIWCQTables() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(261, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setIWCQTables(String qTables) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(qTables);
                    this.mRemote.transact(262, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void updateIWCHintCard(long timestamp) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    this.mRemote.transact(263, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setIWCMockAction(int action) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(action);
                    this.mRemote.transact(264, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean disconnectApBlockAutojoin(boolean block) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(block);
                    this.mRemote.transact(265, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setOptimizerForceControlMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(266, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getOptimizerForceControlMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(267, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getOptimizerState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(268, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getServiceDetectionResult() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(269, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTrafficPatternTestSettings(Bundle settings) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(settings, 0);
                    this.mRemote.transact(270, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int setWifiUwbCoexEnabled(int uwbCh, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(uwbCh);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(271, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setLatencyCritical(String ifaceName, int enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    _data.writeInt(enable);
                    this.mRemote.transact(272, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setPktlogFilter(String ifaceName, String filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    _data.writeString(filter);
                    this.mRemote.transact(273, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean removePktlogFilter(String ifaceName, String filter)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    _data.writeString(filter);
                    this.mRemote.transact(274, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean saveFwDump() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(275, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRssi(String ifaceName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    this.mRemote.transact(276, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiStaInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(277, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getNumOfWifiAnt() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(278, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void startTimerForWifiOffload() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(279, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void checkAppForWiFiOffloading(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(280, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTCRule(boolean enabled, String iface, int limit) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeString(iface);
                    _data.writeInt(limit);
                    this.mRemote.transact(281, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void externalTwtInterface(int cmdId, String cmdLine) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(cmdId);
                    _data.writeString(cmdLine);
                    this.mRemote.transact(282, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getTWTParams() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(283, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getCtlFeatureState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(284, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetCallbackCondition(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(285, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetComebackCondition() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(286, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getCurrentL2TransitionMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(287, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getL2TransitionLog() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(288, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getNumberOfDataInEachRssiLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(289, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIwhState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(290, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setSamsungMloCtrl(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(291, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setSamsungIwhCtrl(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(292, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getSamsungMloCtrl() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(293, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getSamsungIwhCtrl() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(294, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTestMode(boolean mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(mode);
                    this.mRemote.transact(295, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean iwhIntendedDisconnection() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(296, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean linkQosQuery(
                    long payloadBytes,
                    long desiredLatencyMs,
                    long desiredThroughputMbps,
                    int queryType,
                    long timeWindowMs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeLong(payloadBytes);
                    _data.writeLong(desiredLatencyMs);
                    _data.writeLong(desiredThroughputMbps);
                    _data.writeInt(queryType);
                    _data.writeLong(timeWindowMs);
                    this.mRemote.transact(297, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiServiceState(boolean enabled, int[] numClass, int[] numTimeStep)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeIntArray(numClass);
                    _data.writeIntArray(numTimeStep);
                    this.mRemote.transact(298, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiServiceNsdResult(
                    int[] nsdResult,
                    int[] l1ConvSerPredArr,
                    int[] l2ConvSerPredArr,
                    String[] convArr)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeIntArray(nsdResult);
                    _data.writeIntArray(l1ConvSerPredArr);
                    _data.writeIntArray(l2ConvSerPredArr);
                    _data.writeStringArray(convArr);
                    this.mRemote.transact(299, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIwhTrainingResult(
                    String gKey, int trScore, int numBssids, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(gKey);
                    _data.writeInt(trScore);
                    _data.writeInt(numBssids);
                    _data.writeInt(mode);
                    this.mRemote.transact(300, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIwhInferenceResult(boolean[] ret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBooleanArray(ret);
                    this.mRemote.transact(301, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setIlaTrainingResult(double RssiResult, String bssidE)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeDouble(RssiResult);
                    _data.writeString(bssidE);
                    this.mRemote.transact(302, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTcpMonitorSocketForegroundHistory(int count) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(count);
                    this.mRemote.transact(303, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTcpMonitorAllSocketHistory(int count) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(count);
                    this.mRemote.transact(304, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTcpMonitorDnsHistory(int count) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(count);
                    this.mRemote.transact(305, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isIndividualAppSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(306, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiUsabilityStatsEntry(int size) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(size);
                    this.mRemote.transact(307, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isAvailableTdls() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(308, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWiderBandwidthTdlsSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(309, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setTdlsEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(310, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getMaxTdlsSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(311, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getNumOfTdlsSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(312, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getMHSClientTrafficDetails() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(313, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getNRTTrafficbandwidth() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(314, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long[] getDataConsumedValues() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(315, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetTotalPriorityDataConsumedValues() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(316, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getTasAverage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(317, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map setTasPolicy(int newTasPolicy, int windowSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(newTasPolicy);
                    _data.writeInt(windowSize);
                    this.mRemote.transact(318, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerTasPolicyChangedListener(SemTasPolicyListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(319, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterTasPolicyChangedListener(SemTasPolicyListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(320, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void enableTxPowerLogging(boolean enable, int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(index);
                    this.mRemote.transact(321, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getDynamicFeatureStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(322, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSwitchToMobileDataDefaultOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(323, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMhsAiServiceState(boolean enabled, int[] numClass, int[] numTimeStep)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeIntArray(numClass);
                    _data.writeIntArray(numTimeStep);
                    this.mRemote.transact(324, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMhsAiServiceNsdResult(int[] predArr, String[] convoStrArr)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeIntArray(predArr);
                    _data.writeStringArray(convoStrArr);
                    this.mRemote.transact(325, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int startCapture(int captureFrameTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeInt(captureFrameTypes);
                    this.mRemote.transact(326, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int stopCapture() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(327, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isCaptureRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(328, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getIsPacketCaptureSupportedByDriver() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(329, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMcfMultiControlMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(330, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerAbTestConfigUpdateObserver(
                    ISemAbTestConfigurationUpdateObserver observer, String module)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    _data.writeString(module);
                    this.mRemote.transact(331, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterAbTestConfigUpdateObserver(
                    ISemAbTestConfigurationUpdateObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(332, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportAbTestResult(String module, String outputDim, String output)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(module);
                    _data.writeString(outputDim);
                    _data.writeString(output);
                    this.mRemote.transact(333, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemAbTestConfiguration> getAbTestConfigs() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(334, _data, _reply, 0);
                    _reply.readException();
                    List<SemAbTestConfiguration> _result =
                            _reply.createTypedArrayList(SemAbTestConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public SemAbTestConfiguration getAbTestConfiguredModule(String module)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(module);
                    this.mRemote.transact(335, _data, _reply, 0);
                    _reply.readException();
                    SemAbTestConfiguration _result =
                            (SemAbTestConfiguration)
                                    _reply.readTypedObject(SemAbTestConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean sendReassociationFrequencyRequestFrame(String bssid, int channel)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    _data.writeString(bssid);
                    _data.writeInt(channel);
                    this.mRemote.transact(336, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        private boolean onTransact$setVendorWlanDriverProp$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = setVendorWlanDriverProp(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setFccChannelBackoffEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            setFccChannelBackoffEnabled(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$sendVendorSpecificActionFrame$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = sendVendorSpecificActionFrame(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$sendReassociationRequestFrame$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = sendReassociationRequestFrame(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$connectToSmartMHS$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            boolean _arg7 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result =
                    connectToSmartMHS(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$registerWifiApSmartCallback$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            ISemWifiApSmartCallback _arg1 =
                    ISemWifiApSmartCallback.Stub.asInterface(data.readStrongBinder());
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            registerWifiApSmartCallback(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$registerWifiApDataUsageCallback$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            ISemWifiApDataUsageCallback _arg1 =
                    ISemWifiApDataUsageCallback.Stub.asInterface(data.readStrongBinder());
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            registerWifiApDataUsageCallback(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$connectToSmartD2DClient$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            ISemWifiApSmartCallback _arg2 =
                    ISemWifiApSmartCallback.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            boolean _result = connectToSmartD2DClient(_arg0, _arg1, _arg2);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setWifiApEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            SoftApConfiguration _arg0 =
                    (SoftApConfiguration) data.readTypedObject(SoftApConfiguration.CREATOR);
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setWifiApEnabled(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setLocalOnlyHotspotEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            int _arg3 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = setLocalOnlyHotspotEnabled(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$manageWifiApMacAclList$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            data.enforceNoDataAvail();
            int _result = manageWifiApMacAclList(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$notifyConnect$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            notifyConnect(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$launchWifiApWarningForMcfMHS$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            data.enforceNoDataAvail();
            launchWifiApWarningForMcfMHS(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$autohotspotWifiScanConnect$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            int _arg5 = data.readInt();
            data.enforceNoDataAvail();
            int _result = autohotspotWifiScanConnect(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$connectToMcfMHS$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            data.enforceNoDataAvail();
            int _result = connectToMcfMHS(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setWifiApClientMobileDataLimit$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            long _arg1 = data.readLong();
            data.enforceNoDataAvail();
            setWifiApClientMobileDataLimit(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiApClientTimeLimit$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            long _arg1 = data.readLong();
            data.enforceNoDataAvail();
            setWifiApClientTimeLimit(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiApClientDataPaused$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            setWifiApClientDataPaused(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiApClientEditedName$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            setWifiApClientEditedName(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getTopHotspotClientsToday$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            List<SemWifiApClientDetails> _result = getTopHotspotClientsToday(_arg0, _arg1);
            reply.writeNoException();
            reply.writeTypedList(_result, 1);
            return true;
        }

        private boolean onTransact$getTopHotspotClientsTodayAsString$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            String _result = getTopHotspotClientsTodayAsString(_arg0, _arg1);
            reply.writeNoException();
            reply.writeString(_result);
            return true;
        }

        private boolean onTransact$getTotalAndTop3ClientsDataUsageBetweenGivenDates$(
                Parcel data, Parcel reply) throws RemoteException {
            long _arg0 = data.readLong();
            long _arg1 = data.readLong();
            data.enforceNoDataAvail();
            List<String> _result = getTotalAndTop3ClientsDataUsageBetweenGivenDates(_arg0, _arg1);
            reply.writeNoException();
            reply.writeStringList(_result);
            return true;
        }

        private boolean onTransact$registerClientListDataUsageCallback$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            ISemWifiApClientListUpdateCallback _arg1 =
                    ISemWifiApClientListUpdateCallback.Stub.asInterface(data.readStrongBinder());
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            registerClientListDataUsageCallback(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$registerClientDataUsageCallback$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            ISemWifiApClientUpdateCallback _arg1 =
                    ISemWifiApClientUpdateCallback.Stub.asInterface(data.readStrongBinder());
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            data.enforceNoDataAvail();
            registerClientDataUsageCallback(_arg0, _arg1, _arg2, _arg3);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$reportBigData$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            reportBigData(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$addOrUpdateWifiControlHistory$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            addOrUpdateWifiControlHistory(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$allowAutojoinPasspoint$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            allowAutojoinPasspoint(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$reportIssue$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            data.enforceNoDataAvail();
            reportIssue(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$registerPasswordCallback$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            ISemSharedPasswordCallback _arg1 =
                    ISemSharedPasswordCallback.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            registerPasswordCallback(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setUserConfirmForSharingPassword$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            setUserConfirmForSharingPassword(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setTestSettings$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            data.enforceNoDataAvail();
            setTestSettings(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setAllowWifiScan$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            setAllowWifiScan(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setEasySetupScanSettings$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            SemEasySetupWifiScanSettings _arg1 =
                    (SemEasySetupWifiScanSettings)
                            data.readTypedObject(SemEasySetupWifiScanSettings.CREATOR);
            data.enforceNoDataAvail();
            setEasySetupScanSettings(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setKeepConnection$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            setKeepConnection(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setConnectionAttemptInfo$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            boolean _arg1 = data.readBoolean();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            setConnectionAttemptInfo(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$restoreIWCSettingsValue$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            restoreIWCSettingsValue(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiUwbCoexEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            boolean _arg1 = data.readBoolean();
            data.enforceNoDataAvail();
            int _result = setWifiUwbCoexEnabled(_arg0, _arg1);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setLatencyCritical$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = setLatencyCritical(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setPktlogFilter$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = setPktlogFilter(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$removePktlogFilter$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            boolean _result = removePktlogFilter(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setTCRule$(Parcel data, Parcel reply) throws RemoteException {
            boolean _arg0 = data.readBoolean();
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            data.enforceNoDataAvail();
            setTCRule(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$externalTwtInterface$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            externalTwtInterface(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$linkQosQuery$(Parcel data, Parcel reply) throws RemoteException {
            long _arg0 = data.readLong();
            long _arg1 = data.readLong();
            long _arg2 = data.readLong();
            int _arg3 = data.readInt();
            long _arg4 = data.readLong();
            data.enforceNoDataAvail();
            boolean _result = linkQosQuery(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setWifiAiServiceState$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            int[] _arg1 = data.createIntArray();
            int[] _arg2 = data.createIntArray();
            data.enforceNoDataAvail();
            setWifiAiServiceState(_arg0, _arg1, _arg2);
            return true;
        }

        private boolean onTransact$setWifiAiServiceNsdResult$(Parcel data, Parcel reply)
                throws RemoteException {
            int[] _arg0 = data.createIntArray();
            int[] _arg1 = data.createIntArray();
            int[] _arg2 = data.createIntArray();
            String[] _arg3 = data.createStringArray();
            data.enforceNoDataAvail();
            setWifiAiServiceNsdResult(_arg0, _arg1, _arg2, _arg3);
            return true;
        }

        private boolean onTransact$setWifiAiIwhTrainingResult$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            data.enforceNoDataAvail();
            setWifiAiIwhTrainingResult(_arg0, _arg1, _arg2, _arg3);
            return true;
        }

        private boolean onTransact$setIlaTrainingResult$(Parcel data, Parcel reply)
                throws RemoteException {
            double _arg0 = data.readDouble();
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            setIlaTrainingResult(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$setTdlsEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            data.enforceNoDataAvail();
            boolean _result = setTdlsEnabled(_arg0);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$setTasPolicy$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            Map _result = setTasPolicy(_arg0, _arg1);
            reply.writeNoException();
            reply.writeMap(_result);
            return true;
        }

        private boolean onTransact$registerTasPolicyChangedListener$(Parcel data, Parcel reply)
                throws RemoteException {
            SemTasPolicyListener _arg0 =
                    SemTasPolicyListener.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            registerTasPolicyChangedListener(_arg0);
            return true;
        }

        private boolean onTransact$unregisterTasPolicyChangedListener$(Parcel data, Parcel reply)
                throws RemoteException {
            SemTasPolicyListener _arg0 =
                    SemTasPolicyListener.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            unregisterTasPolicyChangedListener(_arg0);
            return true;
        }

        private boolean onTransact$enableTxPowerLogging$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            enableTxPowerLogging(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setMhsAiServiceState$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            int[] _arg1 = data.createIntArray();
            int[] _arg2 = data.createIntArray();
            data.enforceNoDataAvail();
            setMhsAiServiceState(_arg0, _arg1, _arg2);
            return true;
        }

        private boolean onTransact$setMhsAiServiceNsdResult$(Parcel data, Parcel reply)
                throws RemoteException {
            int[] _arg0 = data.createIntArray();
            String[] _arg1 = data.createStringArray();
            data.enforceNoDataAvail();
            setMhsAiServiceNsdResult(_arg0, _arg1);
            return true;
        }

        private boolean onTransact$startCapture$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            data.enforceNoDataAvail();
            int _result = startCapture(_arg0);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$setMcfMultiControlMode$(Parcel data, Parcel reply)
                throws RemoteException {
            boolean _arg0 = data.readBoolean();
            data.enforceNoDataAvail();
            setMcfMultiControlMode(_arg0);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$registerAbTestConfigUpdateObserver$(Parcel data, Parcel reply)
                throws RemoteException {
            ISemAbTestConfigurationUpdateObserver _arg0 =
                    ISemAbTestConfigurationUpdateObserver.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            data.enforceNoDataAvail();
            registerAbTestConfigUpdateObserver(_arg0, _arg1);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterAbTestConfigUpdateObserver$(Parcel data, Parcel reply)
                throws RemoteException {
            ISemAbTestConfigurationUpdateObserver _arg0 =
                    ISemAbTestConfigurationUpdateObserver.Stub.asInterface(data.readStrongBinder());
            data.enforceNoDataAvail();
            unregisterAbTestConfigUpdateObserver(_arg0);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$reportAbTestResult$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            data.enforceNoDataAvail();
            reportAbTestResult(_arg0, _arg1, _arg2);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getAbTestConfiguredModule$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            data.enforceNoDataAvail();
            SemAbTestConfiguration _result = getAbTestConfiguredModule(_arg0);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$sendReassociationFrequencyRequestFrame$(
                Parcel data, Parcel reply) throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = sendReassociationFrequencyRequestFrame(_arg0, _arg1);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 335;
        }
    }
}
