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
import com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver;
import com.samsung.android.wifi.ISemSharedPasswordCallback;
import com.samsung.android.wifi.ISemWifiApClientListUpdateCallback;
import com.samsung.android.wifi.ISemWifiApClientUpdateCallback;
import com.samsung.android.wifi.ISemWifiApDataUsageCallback;
import com.samsung.android.wifi.ISemWifiApSmartCallback;
import com.samsung.android.wifi.ISemWifiManager;
import com.samsung.android.wifi.SemTasPolicyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes6.dex */
public interface ISemWifiManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiManager";

    public static class Default implements ISemWifiManager {
        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean addOrUpdateNetwork(SemWifiConfiguration semWifiConfiguration) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void addOrUpdateWifiControlHistory(String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void allowAutojoinPasspoint(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int autohotspotWifiScanConnect(String str, String str2, String str3, int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void blockFccChannelBackoff(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean canAutoHotspotBeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int canSmartMHSLocked() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> checkAndGetUnauthorizedRro() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> checkAndGetUnauthorizedRroWithoutToast() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void checkAppForWiFiOffloading(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean checkUnauthorizedRro() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean checkUnauthorizedRroWithoutToast() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void clearAutoHotspotLists() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean connectToSmartD2DClient(String str, String str2, ISemWifiApSmartCallback iSemWifiApSmartCallback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean connectToSmartMHS(String str, int i, int i2, int i3, String str2, String str3, int i4, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void disableRandomMac() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean disconnectApBlockAutojoin(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void enableHotspotTsfInfo(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void enableTxPowerLogging(boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void externalTwtInterface(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void factoryReset() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemAbTestConfiguration> getAbTestConfigs() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public SemAbTestConfiguration getAbTestConfiguredModule(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getAdvancedAutohotspotConnectSettings() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getAdvancedAutohotspotLCDSettings() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getAntInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getAutoShareDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getAutoWifiDefaultValue() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getAutoWifiDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCandidateNetworkScores() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getChannelUtilization() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getChannelUtilizationExtended() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getConfiguredNetworkLocations() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public ParceledListSlice getConfiguredNetworks() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getConnectivityLog(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCountryCode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCountryRev() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getCtlFeatureState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getCurrentL2TransitionMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getCurrentStateAndEnterTime() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getCurrentStatusMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Bundle getCurrentWifiRouterInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getDailyUsageInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long[] getDataConsumedValues() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getDcxoCalibrationData() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getDiagnosisResults() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getDynamicFeatureStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map<String, SemEasySetupWifiScanSettings> getEasySetupScanSettings() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getFactoryMacAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getFrameburstInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getHotspotAntMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIWCQTables() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIccState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getIndoorStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getIsPacketCaptureSupportedByDriver() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIssueDetectorDump(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getIwhState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getL2TransitionLog() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getLastSelectedNetworkIdForSilentRoaming() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long getLastSelectedTimeStampForSilentRoaming() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getMHSClientTrafficDetails() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getMHSConfig(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getMHSMacFromInterface() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getMaxTdlsSession() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getMcfConnectedStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getMcfConnectedStatusFromScanResult(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApBleScanResult> getMcfScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getMonthlyDataUsage() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getNRTTrafficbandwidth() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getNetworkLastUpdatedTimeMap() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long[] getNetworkUsageInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getNumOfTdlsSession() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getNumOfWifiAnt() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getNumberOfDataInEachRssiLevel() throws RemoteException {
            return null;
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
        public List getPasspointConfigurations() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getProfileShareDump() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getProvisionSuccess() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getPsmInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getQoSScores(List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRVFModeStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamBand() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamDelta() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamScanPeriod() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRoamTrigger() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getRssi(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getSamsungIwhCtrl() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getSamsungMloCtrl() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getServiceDetectionResult() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getSilentRoamingDump(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartApConnectedStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartApConnectedStatusFromScanResult(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartD2DClientConnectedStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSmartMHSLockStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getSoftApBands() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public SoftApConfiguration getSoftApConfiguration() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSoftApFreq() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSoftApSecurityType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getSoftApUpStreamNetworkType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getStationInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int[] getTWTParams() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map getTasAverage() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTasMode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTcpMonitorAllSocketHistory(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTcpMonitorDnsHistory(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTcpMonitorSocketForegroundHistory(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApClientDetails> getTopHotspotClientsToday(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTopHotspotClientsTodayAsString(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(long j, long j2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getTxPower() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getValidState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getVendorWlanDriverProp(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWcmEverQualityTested() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifi7DisabledCountry() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApBleScanResult> getWifiApBleD2DScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<SemWifiApBleScanResult> getWifiApBleScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApChannel() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public SemWifiApClientDetails getWifiApClientDetails(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApConnectedStationCount() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long getWifiApDailyDataLimit() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApFreq() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApGuestPassword() throws RemoteException {
            return null;
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
        public String getWifiApInterfaceName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getWifiApInterfaceNames() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getWifiApIsolate() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApLOHSState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApMacAclMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApMaxClient() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApMaxClientFromFramework() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiApStaList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> getWifiApStaListDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public long getWifiApTodaysTotalDataUsage() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiApWarningActivityRunningState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean getWifiApWpsPbc() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiCid() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiEnableHistory() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiFirmwareVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int getWifiIconVisibility() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiMACAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Bundle getWifiRouterInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoBestEffort(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoBestEffortByBssid(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Bundle getWifiRouterInfoByBssid(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoPresentable(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiRouterInfoPresentableByBssid(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiStaInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiSupportedFeatureSet() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiUsabilityStatsEntry(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String getWifiVersions() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean hasConfiguredNetworkLocations(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isAvailableAutoWifiScan() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isAvailableTdls() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isCaptureRunning() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void isClientAcceptedWifiProfileSharing(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isDataSaverEnabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isGripSensorMonitorEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isIndividualAppSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isMCFClientAutohotspotSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isNCHOModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isNeededToShowWifiApDatalimitReachedDialog() throws RemoteException {
            return false;
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
        public boolean isP2pConnected() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSAFamilySupportedBasedOnCountry() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isScanningEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isSoftAp6ENetwork() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int isSoftap11axEnabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSupportedAutoWifi() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSupportedProfileRequest() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSupportedQoSProvider() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isSwitchToMobileDataDefaultOff() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isThisSoftApFeatureSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isUploadModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isUsingNonTerrestrialNetwork() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWesModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWiderBandwidthTdlsSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApEnabledWithDualBand() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApGuestClient(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApGuestModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApGuestModeIsolationEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApMacAclEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiApWpa3Supported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiDeveloperModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiSharingEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiSharingLiteSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean isWifiSharingSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean iwhIntendedDisconnection() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void launchWifiApWarningForMcfMHS(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean linkQosQuery(long j, long j2, long j3, int i, long j4) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void logWifiAp(String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int manageWifiApMacAclList(String str, String str2, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void notifyConnect(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void notifyReachabilityLost() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public List<String> readWifiApMacAclList(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerAbTestConfigUpdateObserver(ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerClientDataUsageCallback(IBinder iBinder, ISemWifiApClientUpdateCallback iSemWifiApClientUpdateCallback, int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerClientListDataUsageCallback(IBinder iBinder, ISemWifiApClientListUpdateCallback iSemWifiApClientListUpdateCallback, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerPasswordCallback(String str, ISemSharedPasswordCallback iSemSharedPasswordCallback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerWifiApDataUsageCallback(IBinder iBinder, ISemWifiApDataUsageCallback iSemWifiApDataUsageCallback, int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void registerWifiApSmartCallback(IBinder iBinder, ISemWifiApSmartCallback iSemWifiApSmartCallback, int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void removeExcludedNetwork(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean removeFactoryMacAddress() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean removeNetwork(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean removePktlogFilter(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportAbTestResult(String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportBigData(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportHotspotDumpLogs(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void reportIssue(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void requestPassword(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void requestStopAutohotspotAdvertisement(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetCallbackCondition(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetComebackCondition() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetDeveloperOptionsSettings() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetSoftAp(Message message) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void resetTotalPriorityDataConsumedValues() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void restoreIWCSettingsValue(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void restoreSemConfigurationsBackupData(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String retrieveSemWifiConfigsBackupData() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void runAutoShareForCurrent(List<String> list) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String runIptablesRulesCommand(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean saveFwDump() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean sendReassociationFrequencyRequestFrame(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean sendReassociationRequestFrame(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean sendVendorSpecificActionFrame(String str, int i, int i2, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void set5GmmWaveSarBackoffEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAdvancedAutohotspotConnectSettings(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAdvancedAutohotspotLCDSettings(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAllowWifiScan(boolean z, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setAntInfo(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAntMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setArdkPowerSaveMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setAutohotspotToastMessage(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setBtmOptionUserDisabled(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setBtmOptionUserEnabled(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setConnectionAttemptInfo(int i, boolean z, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setConnectivityCheckDisabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setCountryRev(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setDcxoCalibrationData(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setDtimInSuspendMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setEasySetupScanSettings(String str, SemEasySetupWifiScanSettings semEasySetupWifiScanSettings) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setFactoryMacAddress(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setFccChannelBackoffEnabled(String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setFrameburstInfo(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setGripSensorMonitorEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setHotspotAntMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setIWCMockAction(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setIWCQTables(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setIlaTrainingResult(double d, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setImsCallEstablished(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setInsInferenceResult(int i, float f, float f2, float f3, float f4, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setKeepConnection(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setKeepConnectionAlways(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setKeepConnectionBigData(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setLastSelectedNetworkIdForSilentRoaming(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setLastSelectedTimeStampForSilentRoaming() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setLatencyCritical(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setLocalOnlyHotspotEnabled(boolean z, String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String setMHSConfig(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMaxDtimInSuspendMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMcfMultiControlMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMhsAiServiceNsdResult(int[] iArr, String[] strArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setMhsAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setNCHOModeEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setOptimizerForceControlMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setPktlogFilter(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setPowerSavingTime(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setProvisionSuccess(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setPsmInfo(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setRVFmodeStatus(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamBand(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamDelta(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamScanChannels(String[] strArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamScanEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamScanPeriod(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setRoamTrigger(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setSamsungIwhCtrl(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setSamsungMloCtrl(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int setSmartMHSLocked(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setSoftApConfiguration(SoftApConfiguration softApConfiguration) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTCRule(boolean z, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public Map setTasPolicy(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setTdlsEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTestMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTestSettings(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setTrafficPatternTestSettings(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setUploadModeEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setUserConfirmForSharingPassword(boolean z, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setVendorWlanDriverProp(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setVerboseLoggingEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWesModeEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIccInferenceConfidence(float[] fArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIccInferenceResult(boolean[] zArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIccInferenceResult2(float[] fArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIccTrainingResult(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIwhInferenceResult(boolean[] zArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiIwhTrainingResult(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiServiceNsdResult(int[] iArr, int[] iArr2, int[] iArr3, String[] strArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientDataPaused(String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientEditedName(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientMobileDataLimit(String str, long j) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApClientTimeLimit(String str, long j) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApConfigurationToDefault() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApDailyDataLimit(long j) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWifiApEnabled(SoftApConfiguration softApConfiguration, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApGuestModeEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApGuestModeIsolationEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApGuestPassword(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApIsolate(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMacAclEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMacAclMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMaxClient(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApMaxClientToFramework(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApWarningActivityRunning(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiApWpsPbc(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiDeveloperModeEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void setWifiSettingsForegroundState(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWifiSharingEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean setWifiSharingMenuState(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int setWifiUwbCoexEnabled(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean shouldShowAutoWifiBubbleTip() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int startCapture(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void startIssueMonitoring(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int startMcfClientMHSDiscovery(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int startMcfMHSAdvertisement(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean startScan(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void startTimerForWifiOffload() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public int stopCapture() throws RemoteException {
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
        public void triggerBackoffRoutine(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unRegisterWifiApDataUsageCallback(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterAbTestConfigUpdateObserver(ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterClientDataUsageCallback(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterClientListDataUsageCallback(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterPasswordCallback(ISemSharedPasswordCallback iSemSharedPasswordCallback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void unregisterWifiApSmartCallback(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void updateGuiderFeature(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void updateHostapdMacList(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void updateIWCHintCard(long j) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public String wifiApBackUpClientDataUsageSettingsInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleClientRole(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleD2DClientRole(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleD2DMhsRole(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public boolean wifiApBleMhsRole(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void wifiApDisassocSta(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void wifiApRestoreClientDataUsageSettingsInfo(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiManager
        public void wifiApRestoreDailyHotspotDataLimit(long j) throws RemoteException {
        }
    }

    boolean addOrUpdateNetwork(SemWifiConfiguration semWifiConfiguration) throws RemoteException;

    void addOrUpdateWifiControlHistory(String str, boolean z) throws RemoteException;

    void allowAutojoinPasspoint(String str, boolean z) throws RemoteException;

    int autohotspotWifiScanConnect(String str, String str2, String str3, int i, int i2, int i3) throws RemoteException;

    void blockFccChannelBackoff(boolean z) throws RemoteException;

    boolean canAutoHotspotBeEnabled() throws RemoteException;

    int canSmartMHSLocked() throws RemoteException;

    List<String> checkAndGetUnauthorizedRro() throws RemoteException;

    List<String> checkAndGetUnauthorizedRroWithoutToast() throws RemoteException;

    void checkAppForWiFiOffloading(String str) throws RemoteException;

    boolean checkUnauthorizedRro() throws RemoteException;

    boolean checkUnauthorizedRroWithoutToast() throws RemoteException;

    void clearAutoHotspotLists() throws RemoteException;

    int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException;

    boolean connectToSmartD2DClient(String str, String str2, ISemWifiApSmartCallback iSemWifiApSmartCallback) throws RemoteException;

    boolean connectToSmartMHS(String str, int i, int i2, int i3, String str2, String str3, int i4, boolean z) throws RemoteException;

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

    String getCandidateNetworkScores() throws RemoteException;

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

    String getIccState() throws RemoteException;

    int getIndoorStatus() throws RemoteException;

    boolean getIsPacketCaptureSupportedByDriver() throws RemoteException;

    String getIssueDetectorDump(int i) throws RemoteException;

    String getIwhState() throws RemoteException;

    String getL2TransitionLog() throws RemoteException;

    int getLastSelectedNetworkIdForSilentRoaming() throws RemoteException;

    long getLastSelectedTimeStampForSilentRoaming() throws RemoteException;

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

    String getTasMode() throws RemoteException;

    String getTcpMonitorAllSocketHistory(int i) throws RemoteException;

    String getTcpMonitorDnsHistory(int i) throws RemoteException;

    String getTcpMonitorSocketForegroundHistory(int i) throws RemoteException;

    List<SemWifiApClientDetails> getTopHotspotClientsToday(int i, int i2) throws RemoteException;

    String getTopHotspotClientsTodayAsString(int i, int i2) throws RemoteException;

    List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(long j, long j2) throws RemoteException;

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

    boolean isThisSoftApFeatureSupported(int i) throws RemoteException;

    boolean isUploadModeEnabled() throws RemoteException;

    boolean isUsingNonTerrestrialNetwork() throws RemoteException;

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

    void logWifiAp(String str, String str2, String str3) throws RemoteException;

    int manageWifiApMacAclList(String str, String str2, int i, int i2) throws RemoteException;

    void notifyConnect(int i, String str) throws RemoteException;

    void notifyReachabilityLost() throws RemoteException;

    List<String> readWifiApMacAclList(int i) throws RemoteException;

    void registerAbTestConfigUpdateObserver(ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver, String str) throws RemoteException;

    void registerClientDataUsageCallback(IBinder iBinder, ISemWifiApClientUpdateCallback iSemWifiApClientUpdateCallback, int i, String str) throws RemoteException;

    void registerClientListDataUsageCallback(IBinder iBinder, ISemWifiApClientListUpdateCallback iSemWifiApClientListUpdateCallback, int i, int i2, int i3) throws RemoteException;

    void registerPasswordCallback(String str, ISemSharedPasswordCallback iSemSharedPasswordCallback) throws RemoteException;

    void registerTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener) throws RemoteException;

    void registerWifiApDataUsageCallback(IBinder iBinder, ISemWifiApDataUsageCallback iSemWifiApDataUsageCallback, int i) throws RemoteException;

    void registerWifiApSmartCallback(IBinder iBinder, ISemWifiApSmartCallback iSemWifiApSmartCallback, int i) throws RemoteException;

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

    boolean sendVendorSpecificActionFrame(String str, int i, int i2, String str2) throws RemoteException;

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

    void setEasySetupScanSettings(String str, SemEasySetupWifiScanSettings semEasySetupWifiScanSettings) throws RemoteException;

    boolean setFactoryMacAddress(String str) throws RemoteException;

    void setFccChannelBackoffEnabled(String str, boolean z) throws RemoteException;

    boolean setFrameburstInfo(String str) throws RemoteException;

    void setGripSensorMonitorEnabled(boolean z) throws RemoteException;

    void setHotspotAntMode(int i) throws RemoteException;

    void setIWCMockAction(int i) throws RemoteException;

    void setIWCQTables(String str) throws RemoteException;

    void setIlaTrainingResult(double d, String str) throws RemoteException;

    void setImsCallEstablished(boolean z) throws RemoteException;

    void setInsInferenceResult(int i, float f, float f2, float f3, float f4, String str) throws RemoteException;

    void setKeepConnection(boolean z, boolean z2) throws RemoteException;

    void setKeepConnectionAlways(boolean z) throws RemoteException;

    void setKeepConnectionBigData(int i) throws RemoteException;

    void setLastSelectedNetworkIdForSilentRoaming(int i) throws RemoteException;

    void setLastSelectedTimeStampForSilentRoaming() throws RemoteException;

    boolean setLatencyCritical(String str, int i) throws RemoteException;

    boolean setLocalOnlyHotspotEnabled(boolean z, String str, String str2, int i) throws RemoteException;

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

    void setWifiAiIccInferenceConfidence(float[] fArr) throws RemoteException;

    void setWifiAiIccInferenceResult(boolean[] zArr) throws RemoteException;

    void setWifiAiIccInferenceResult2(float[] fArr) throws RemoteException;

    void setWifiAiIccTrainingResult(String str, int i, int i2, int i3) throws RemoteException;

    void setWifiAiIwhInferenceResult(boolean[] zArr) throws RemoteException;

    void setWifiAiIwhTrainingResult(String str, int i, int i2, int i3) throws RemoteException;

    void setWifiAiServiceNsdResult(int[] iArr, int[] iArr2, int[] iArr3, String[] strArr) throws RemoteException;

    void setWifiAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException;

    void setWifiApClientDataPaused(String str, boolean z) throws RemoteException;

    void setWifiApClientEditedName(String str, String str2) throws RemoteException;

    void setWifiApClientMobileDataLimit(String str, long j) throws RemoteException;

    void setWifiApClientTimeLimit(String str, long j) throws RemoteException;

    void setWifiApConfigurationToDefault() throws RemoteException;

    void setWifiApDailyDataLimit(long j) throws RemoteException;

    boolean setWifiApEnabled(SoftApConfiguration softApConfiguration, boolean z) throws RemoteException;

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

    boolean setWifiSharingMenuState(boolean z) throws RemoteException;

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

    void unregisterAbTestConfigUpdateObserver(ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver) throws RemoteException;

    void unregisterClientDataUsageCallback(int i) throws RemoteException;

    void unregisterClientListDataUsageCallback(int i) throws RemoteException;

    void unregisterPasswordCallback(ISemSharedPasswordCallback iSemSharedPasswordCallback) throws RemoteException;

    void unregisterTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener) throws RemoteException;

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

    public static abstract class Stub extends Binder implements ISemWifiManager {
        static final int TRANSACTION_addOrUpdateNetwork = 210;
        static final int TRANSACTION_addOrUpdateWifiControlHistory = 208;
        static final int TRANSACTION_allowAutojoinPasspoint = 215;
        static final int TRANSACTION_autohotspotWifiScanConnect = 167;
        static final int TRANSACTION_blockFccChannelBackoff = 4;
        static final int TRANSACTION_canAutoHotspotBeEnabled = 154;
        static final int TRANSACTION_canSmartMHSLocked = 73;
        static final int TRANSACTION_checkAndGetUnauthorizedRro = 334;
        static final int TRANSACTION_checkAndGetUnauthorizedRroWithoutToast = 336;
        static final int TRANSACTION_checkAppForWiFiOffloading = 285;
        static final int TRANSACTION_checkUnauthorizedRro = 333;
        static final int TRANSACTION_checkUnauthorizedRroWithoutToast = 335;
        static final int TRANSACTION_clearAutoHotspotLists = 48;
        static final int TRANSACTION_connectToMcfMHS = 174;
        static final int TRANSACTION_connectToSmartD2DClient = 91;
        static final int TRANSACTION_connectToSmartMHS = 80;
        static final int TRANSACTION_disableRandomMac = 249;
        static final int TRANSACTION_disconnectApBlockAutojoin = 269;
        static final int TRANSACTION_enableHotspotTsfInfo = 151;
        static final int TRANSACTION_enableTxPowerLogging = 331;
        static final int TRANSACTION_externalTwtInterface = 287;
        static final int TRANSACTION_factoryReset = 212;
        static final int TRANSACTION_getAbTestConfigs = 348;
        static final int TRANSACTION_getAbTestConfiguredModule = 349;
        static final int TRANSACTION_getAdvancedAutohotspotConnectSettings = 42;
        static final int TRANSACTION_getAdvancedAutohotspotLCDSettings = 44;
        static final int TRANSACTION_getAntInfo = 11;
        static final int TRANSACTION_getAutoShareDump = 234;
        static final int TRANSACTION_getAutoWifiDefaultValue = 237;
        static final int TRANSACTION_getAutoWifiDump = 240;
        static final int TRANSACTION_getCandidateNetworkScores = 352;
        static final int TRANSACTION_getChannelUtilization = 50;
        static final int TRANSACTION_getChannelUtilizationExtended = 51;
        static final int TRANSACTION_getConfiguredNetworkLocations = 241;
        static final int TRANSACTION_getConfiguredNetworks = 214;
        static final int TRANSACTION_getConnectivityLog = 223;
        static final int TRANSACTION_getCountryCode = 62;
        static final int TRANSACTION_getCountryRev = 61;
        static final int TRANSACTION_getCtlFeatureState = 289;
        static final int TRANSACTION_getCurrentL2TransitionMode = 292;
        static final int TRANSACTION_getCurrentStateAndEnterTime = 33;
        static final int TRANSACTION_getCurrentStatusMode = 253;
        static final int TRANSACTION_getCurrentWifiRouterInfo = 25;
        static final int TRANSACTION_getDailyUsageInfo = 35;
        static final int TRANSACTION_getDataConsumedValues = 325;
        static final int TRANSACTION_getDcxoCalibrationData = 24;
        static final int TRANSACTION_getDiagnosisResults = 220;
        static final int TRANSACTION_getDynamicFeatureStatus = 332;
        static final int TRANSACTION_getEasySetupScanSettings = 248;
        static final int TRANSACTION_getFactoryMacAddress = 10;
        static final int TRANSACTION_getFrameburstInfo = 12;
        static final int TRANSACTION_getHotspotAntMode = 102;
        static final int TRANSACTION_getIWCQTables = 265;
        static final int TRANSACTION_getIccState = 296;
        static final int TRANSACTION_getIndoorStatus = 127;
        static final int TRANSACTION_getIsPacketCaptureSupportedByDriver = 343;
        static final int TRANSACTION_getIssueDetectorDump = 217;
        static final int TRANSACTION_getIwhState = 295;
        static final int TRANSACTION_getL2TransitionLog = 293;
        static final int TRANSACTION_getLastSelectedNetworkIdForSilentRoaming = 355;
        static final int TRANSACTION_getLastSelectedTimeStampForSilentRoaming = 353;
        static final int TRANSACTION_getMHSClientTrafficDetails = 323;
        static final int TRANSACTION_getMHSConfig = 105;
        static final int TRANSACTION_getMHSMacFromInterface = 162;
        static final int TRANSACTION_getMaxTdlsSession = 321;
        static final int TRANSACTION_getMcfConnectedStatus = 175;
        static final int TRANSACTION_getMcfConnectedStatusFromScanResult = 176;
        static final int TRANSACTION_getMcfScanDetail = 171;
        static final int TRANSACTION_getMonthlyDataUsage = 188;
        static final int TRANSACTION_getNRTTrafficbandwidth = 324;
        static final int TRANSACTION_getNetworkLastUpdatedTimeMap = 32;
        static final int TRANSACTION_getNetworkUsageInfo = 34;
        static final int TRANSACTION_getNumOfTdlsSession = 322;
        static final int TRANSACTION_getNumOfWifiAnt = 282;
        static final int TRANSACTION_getNumberOfDataInEachRssiLevel = 294;
        static final int TRANSACTION_getOptimizerForceControlMode = 271;
        static final int TRANSACTION_getOptimizerState = 272;
        static final int TRANSACTION_getPasspointConfigurations = 216;
        static final int TRANSACTION_getProfileShareDump = 233;
        static final int TRANSACTION_getProvisionSuccess = 122;
        static final int TRANSACTION_getPsmInfo = 13;
        static final int TRANSACTION_getQoSScores = 224;
        static final int TRANSACTION_getRVFModeStatus = 128;
        static final int TRANSACTION_getRoamBand = 59;
        static final int TRANSACTION_getRoamDelta = 55;
        static final int TRANSACTION_getRoamScanPeriod = 57;
        static final int TRANSACTION_getRoamTrigger = 53;
        static final int TRANSACTION_getRssi = 280;
        static final int TRANSACTION_getSamsungIwhCtrl = 300;
        static final int TRANSACTION_getSamsungMloCtrl = 299;
        static final int TRANSACTION_getServiceDetectionResult = 273;
        static final int TRANSACTION_getSilentRoamingDump = 222;
        static final int TRANSACTION_getSmartApConnectedStatus = 82;
        static final int TRANSACTION_getSmartApConnectedStatusFromScanResult = 87;
        static final int TRANSACTION_getSmartD2DClientConnectedStatus = 92;
        static final int TRANSACTION_getSmartMHSLockStatus = 72;
        static final int TRANSACTION_getSoftApBands = 153;
        static final int TRANSACTION_getSoftApConfiguration = 96;
        static final int TRANSACTION_getSoftApFreq = 163;
        static final int TRANSACTION_getSoftApSecurityType = 157;
        static final int TRANSACTION_getSoftApUpStreamNetworkType = 161;
        static final int TRANSACTION_getStationInfo = 98;
        static final int TRANSACTION_getTWTParams = 288;
        static final int TRANSACTION_getTasAverage = 327;
        static final int TRANSACTION_getTasMode = 283;
        static final int TRANSACTION_getTcpMonitorAllSocketHistory = 314;
        static final int TRANSACTION_getTcpMonitorDnsHistory = 315;
        static final int TRANSACTION_getTcpMonitorSocketForegroundHistory = 313;
        static final int TRANSACTION_getTopHotspotClientsToday = 183;
        static final int TRANSACTION_getTopHotspotClientsTodayAsString = 184;
        static final int TRANSACTION_getTotalAndTop3ClientsDataUsageBetweenGivenDates = 187;
        static final int TRANSACTION_getTxPower = 99;
        static final int TRANSACTION_getValidState = 254;
        static final int TRANSACTION_getVendorWlanDriverProp = 15;
        static final int TRANSACTION_getWcmEverQualityTested = 251;
        static final int TRANSACTION_getWifi7DisabledCountry = 63;
        static final int TRANSACTION_getWifiApBleD2DScanDetail = 88;
        static final int TRANSACTION_getWifiApBleScanDetail = 77;
        static final int TRANSACTION_getWifiApChannel = 107;
        static final int TRANSACTION_getWifiApClientDetails = 182;
        static final int TRANSACTION_getWifiApConnectedStationCount = 125;
        static final int TRANSACTION_getWifiApDailyDataLimit = 186;
        static final int TRANSACTION_getWifiApFreq = 100;
        static final int TRANSACTION_getWifiApGuestPassword = 195;
        static final int TRANSACTION_getWifiApHostapdFreq = 168;
        static final int TRANSACTION_getWifiApHostapdSecurtiy = 169;
        static final int TRANSACTION_getWifiApInterfaceName = 118;
        static final int TRANSACTION_getWifiApInterfaceNames = 117;
        static final int TRANSACTION_getWifiApIsolate = 138;
        static final int TRANSACTION_getWifiApLOHSState = 126;
        static final int TRANSACTION_getWifiApMacAclMode = 142;
        static final int TRANSACTION_getWifiApMaxClient = 108;
        static final int TRANSACTION_getWifiApMaxClientFromFramework = 134;
        static final int TRANSACTION_getWifiApStaList = 111;
        static final int TRANSACTION_getWifiApStaListDetail = 115;
        static final int TRANSACTION_getWifiApState = 148;
        static final int TRANSACTION_getWifiApTodaysTotalDataUsage = 185;
        static final int TRANSACTION_getWifiApWarningActivityRunningState = 47;
        static final int TRANSACTION_getWifiApWpsPbc = 136;
        static final int TRANSACTION_getWifiCid = 8;
        static final int TRANSACTION_getWifiEnableHistory = 209;
        static final int TRANSACTION_getWifiFirmwareVersion = 7;
        static final int TRANSACTION_getWifiIconVisibility = 252;
        static final int TRANSACTION_getWifiMACAddress = 166;
        static final int TRANSACTION_getWifiRouterInfo = 26;
        static final int TRANSACTION_getWifiRouterInfoBestEffort = 27;
        static final int TRANSACTION_getWifiRouterInfoBestEffortByBssid = 30;
        static final int TRANSACTION_getWifiRouterInfoByBssid = 29;
        static final int TRANSACTION_getWifiRouterInfoPresentable = 28;
        static final int TRANSACTION_getWifiRouterInfoPresentableByBssid = 31;
        static final int TRANSACTION_getWifiStaInfo = 281;
        static final int TRANSACTION_getWifiSupportedFeatureSet = 14;
        static final int TRANSACTION_getWifiUsabilityStatsEntry = 317;
        static final int TRANSACTION_getWifiVersions = 9;
        static final int TRANSACTION_hasConfiguredNetworkLocations = 242;
        static final int TRANSACTION_isAvailableAutoWifiScan = 239;
        static final int TRANSACTION_isAvailableTdls = 318;
        static final int TRANSACTION_isCaptureRunning = 342;
        static final int TRANSACTION_isClientAcceptedWifiProfileSharing = 76;
        static final int TRANSACTION_isDataSaverEnabled = 158;
        static final int TRANSACTION_isGripSensorMonitorEnabled = 37;
        static final int TRANSACTION_isIndividualAppSupported = 316;
        static final int TRANSACTION_isMCFClientAutohotspotSupported = 170;
        static final int TRANSACTION_isNCHOModeEnabled = 64;
        static final int TRANSACTION_isNeededToShowWifiApDatalimitReachedDialog = 165;
        static final int TRANSACTION_isOverAllMhsDataLimitReached = 189;
        static final int TRANSACTION_isOverAllMhsDataLimitSet = 190;
        static final int TRANSACTION_isP2pConnected = 155;
        static final int TRANSACTION_isSAFamilySupportedBasedOnCountry = 201;
        static final int TRANSACTION_isScanningEnabled = 245;
        static final int TRANSACTION_isSoftAp6ENetwork = 160;
        static final int TRANSACTION_isSoftap11axEnabled = 159;
        static final int TRANSACTION_isSupportedAutoWifi = 236;
        static final int TRANSACTION_isSupportedProfileRequest = 232;
        static final int TRANSACTION_isSupportedQoSProvider = 231;
        static final int TRANSACTION_isSwitchToMobileDataDefaultOff = 337;
        static final int TRANSACTION_isThisSoftApFeatureSupported = 114;
        static final int TRANSACTION_isUploadModeEnabled = 41;
        static final int TRANSACTION_isUsingNonTerrestrialNetwork = 147;
        static final int TRANSACTION_isWesModeEnabled = 68;
        static final int TRANSACTION_isWiderBandwidthTdlsSupported = 319;
        static final int TRANSACTION_isWifiApEnabled = 124;
        static final int TRANSACTION_isWifiApEnabledWithDualBand = 149;
        static final int TRANSACTION_isWifiApGuestClient = 200;
        static final int TRANSACTION_isWifiApGuestModeEnabled = 196;
        static final int TRANSACTION_isWifiApGuestModeIsolationEnabled = 198;
        static final int TRANSACTION_isWifiApMacAclEnabled = 144;
        static final int TRANSACTION_isWifiApWpa3Supported = 93;
        static final int TRANSACTION_isWifiDeveloperModeEnabled = 6;
        static final int TRANSACTION_isWifiSharingEnabled = 123;
        static final int TRANSACTION_isWifiSharingLiteSupported = 113;
        static final int TRANSACTION_isWifiSharingSupported = 112;
        static final int TRANSACTION_iwhIntendedDisconnection = 302;
        static final int TRANSACTION_launchWifiApWarningForMcfMHS = 164;
        static final int TRANSACTION_linkQosQuery = 303;
        static final int TRANSACTION_logWifiAp = 206;
        static final int TRANSACTION_manageWifiApMacAclList = 140;
        static final int TRANSACTION_notifyConnect = 152;
        static final int TRANSACTION_notifyReachabilityLost = 255;
        static final int TRANSACTION_readWifiApMacAclList = 141;
        static final int TRANSACTION_registerAbTestConfigUpdateObserver = 345;
        static final int TRANSACTION_registerClientDataUsageCallback = 204;
        static final int TRANSACTION_registerClientListDataUsageCallback = 202;
        static final int TRANSACTION_registerPasswordCallback = 227;
        static final int TRANSACTION_registerTasPolicyChangedListener = 329;
        static final int TRANSACTION_registerWifiApDataUsageCallback = 85;
        static final int TRANSACTION_registerWifiApSmartCallback = 83;
        static final int TRANSACTION_removeExcludedNetwork = 260;
        static final int TRANSACTION_removeFactoryMacAddress = 17;
        static final int TRANSACTION_removeNetwork = 211;
        static final int TRANSACTION_removePktlogFilter = 278;
        static final int TRANSACTION_reportAbTestResult = 347;
        static final int TRANSACTION_reportBigData = 207;
        static final int TRANSACTION_reportHotspotDumpLogs = 146;
        static final int TRANSACTION_reportIssue = 218;
        static final int TRANSACTION_requestPassword = 229;
        static final int TRANSACTION_requestStopAutohotspotAdvertisement = 81;
        static final int TRANSACTION_resetCallbackCondition = 290;
        static final int TRANSACTION_resetComebackCondition = 291;
        static final int TRANSACTION_resetDeveloperOptionsSettings = 213;
        static final int TRANSACTION_resetSoftAp = 132;
        static final int TRANSACTION_resetTotalPriorityDataConsumedValues = 326;
        static final int TRANSACTION_restoreIWCSettingsValue = 264;
        static final int TRANSACTION_restoreSemConfigurationsBackupData = 262;
        static final int TRANSACTION_retrieveSemWifiConfigsBackupData = 261;
        static final int TRANSACTION_runAutoShareForCurrent = 235;
        static final int TRANSACTION_runIptablesRulesCommand = 119;
        static final int TRANSACTION_saveFwDump = 279;
        static final int TRANSACTION_sendReassociationFrequencyRequestFrame = 350;
        static final int TRANSACTION_sendReassociationRequestFrame = 71;
        static final int TRANSACTION_sendVendorSpecificActionFrame = 70;
        static final int TRANSACTION_set5GmmWaveSarBackoffEnabled = 39;
        static final int TRANSACTION_setAdvancedAutohotspotConnectSettings = 43;
        static final int TRANSACTION_setAdvancedAutohotspotLCDSettings = 49;
        static final int TRANSACTION_setAllowWifiScan = 244;
        static final int TRANSACTION_setAntInfo = 21;
        static final int TRANSACTION_setAntMode = 103;
        static final int TRANSACTION_setArdkPowerSaveMode = 150;
        static final int TRANSACTION_setAutohotspotToastMessage = 156;
        static final int TRANSACTION_setBtmOptionUserDisabled = 226;
        static final int TRANSACTION_setBtmOptionUserEnabled = 225;
        static final int TRANSACTION_setConnectionAttemptInfo = 263;
        static final int TRANSACTION_setConnectivityCheckDisabled = 256;
        static final int TRANSACTION_setCountryRev = 60;
        static final int TRANSACTION_setDcxoCalibrationData = 23;
        static final int TRANSACTION_setDtimInSuspendMode = 2;
        static final int TRANSACTION_setEasySetupScanSettings = 247;
        static final int TRANSACTION_setFactoryMacAddress = 18;
        static final int TRANSACTION_setFccChannelBackoffEnabled = 19;
        static final int TRANSACTION_setFrameburstInfo = 22;
        static final int TRANSACTION_setGripSensorMonitorEnabled = 36;
        static final int TRANSACTION_setHotspotAntMode = 101;
        static final int TRANSACTION_setIWCMockAction = 268;
        static final int TRANSACTION_setIWCQTables = 266;
        static final int TRANSACTION_setIlaTrainingResult = 308;
        static final int TRANSACTION_setImsCallEstablished = 250;
        static final int TRANSACTION_setInsInferenceResult = 351;
        static final int TRANSACTION_setKeepConnection = 258;
        static final int TRANSACTION_setKeepConnectionAlways = 257;
        static final int TRANSACTION_setKeepConnectionBigData = 259;
        static final int TRANSACTION_setLastSelectedNetworkIdForSilentRoaming = 356;
        static final int TRANSACTION_setLastSelectedTimeStampForSilentRoaming = 354;
        static final int TRANSACTION_setLatencyCritical = 276;
        static final int TRANSACTION_setLocalOnlyHotspotEnabled = 95;
        static final int TRANSACTION_setMHSConfig = 106;
        static final int TRANSACTION_setMaxDtimInSuspendMode = 1;
        static final int TRANSACTION_setMcfMultiControlMode = 344;
        static final int TRANSACTION_setMhsAiServiceNsdResult = 339;
        static final int TRANSACTION_setMhsAiServiceState = 338;
        static final int TRANSACTION_setNCHOModeEnabled = 65;
        static final int TRANSACTION_setOptimizerForceControlMode = 270;
        static final int TRANSACTION_setPktlogFilter = 277;
        static final int TRANSACTION_setPowerSavingTime = 104;
        static final int TRANSACTION_setProvisionSuccess = 121;
        static final int TRANSACTION_setPsmInfo = 20;
        static final int TRANSACTION_setRVFmodeStatus = 129;
        static final int TRANSACTION_setRoamBand = 58;
        static final int TRANSACTION_setRoamDelta = 54;
        static final int TRANSACTION_setRoamScanChannels = 67;
        static final int TRANSACTION_setRoamScanEnabled = 66;
        static final int TRANSACTION_setRoamScanPeriod = 56;
        static final int TRANSACTION_setRoamTrigger = 52;
        static final int TRANSACTION_setSamsungIwhCtrl = 298;
        static final int TRANSACTION_setSamsungMloCtrl = 297;
        static final int TRANSACTION_setSmartMHSLocked = 74;
        static final int TRANSACTION_setSoftApConfiguration = 97;
        static final int TRANSACTION_setTCRule = 286;
        static final int TRANSACTION_setTasPolicy = 328;
        static final int TRANSACTION_setTdlsEnabled = 320;
        static final int TRANSACTION_setTestMode = 301;
        static final int TRANSACTION_setTestSettings = 243;
        static final int TRANSACTION_setTrafficPatternTestSettings = 274;
        static final int TRANSACTION_setUploadModeEnabled = 40;
        static final int TRANSACTION_setUserConfirmForSharingPassword = 230;
        static final int TRANSACTION_setVendorWlanDriverProp = 16;
        static final int TRANSACTION_setVerboseLoggingEnabled = 3;
        static final int TRANSACTION_setWesModeEnabled = 69;
        static final int TRANSACTION_setWifiAiIccInferenceConfidence = 312;
        static final int TRANSACTION_setWifiAiIccInferenceResult = 310;
        static final int TRANSACTION_setWifiAiIccInferenceResult2 = 311;
        static final int TRANSACTION_setWifiAiIccTrainingResult = 309;
        static final int TRANSACTION_setWifiAiIwhInferenceResult = 307;
        static final int TRANSACTION_setWifiAiIwhTrainingResult = 306;
        static final int TRANSACTION_setWifiAiServiceNsdResult = 305;
        static final int TRANSACTION_setWifiAiServiceState = 304;
        static final int TRANSACTION_setWifiApClientDataPaused = 179;
        static final int TRANSACTION_setWifiApClientEditedName = 180;
        static final int TRANSACTION_setWifiApClientMobileDataLimit = 177;
        static final int TRANSACTION_setWifiApClientTimeLimit = 178;
        static final int TRANSACTION_setWifiApConfigurationToDefault = 116;
        static final int TRANSACTION_setWifiApDailyDataLimit = 181;
        static final int TRANSACTION_setWifiApEnabled = 94;
        static final int TRANSACTION_setWifiApGuestModeEnabled = 197;
        static final int TRANSACTION_setWifiApGuestModeIsolationEnabled = 199;
        static final int TRANSACTION_setWifiApGuestPassword = 194;
        static final int TRANSACTION_setWifiApIsolate = 137;
        static final int TRANSACTION_setWifiApMacAclEnable = 145;
        static final int TRANSACTION_setWifiApMacAclMode = 143;
        static final int TRANSACTION_setWifiApMaxClient = 131;
        static final int TRANSACTION_setWifiApMaxClientToFramework = 133;
        static final int TRANSACTION_setWifiApWarningActivityRunning = 46;
        static final int TRANSACTION_setWifiApWpsPbc = 135;
        static final int TRANSACTION_setWifiDeveloperModeEnabled = 5;
        static final int TRANSACTION_setWifiSettingsForegroundState = 45;
        static final int TRANSACTION_setWifiSharingEnabled = 120;
        static final int TRANSACTION_setWifiSharingMenuState = 75;
        static final int TRANSACTION_setWifiUwbCoexEnabled = 275;
        static final int TRANSACTION_shouldShowAutoWifiBubbleTip = 238;
        static final int TRANSACTION_startCapture = 340;
        static final int TRANSACTION_startIssueMonitoring = 221;
        static final int TRANSACTION_startMcfClientMHSDiscovery = 172;
        static final int TRANSACTION_startMcfMHSAdvertisement = 173;
        static final int TRANSACTION_startScan = 246;
        static final int TRANSACTION_startTimerForWifiOffload = 284;
        static final int TRANSACTION_stopCapture = 341;
        static final int TRANSACTION_supportWifiAp5GBasedOnCountry = 109;
        static final int TRANSACTION_supportWifiAp6GBasedOnCountry = 110;
        static final int TRANSACTION_triggerBackoffRoutine = 38;
        static final int TRANSACTION_unRegisterWifiApDataUsageCallback = 86;
        static final int TRANSACTION_unregisterAbTestConfigUpdateObserver = 346;
        static final int TRANSACTION_unregisterClientDataUsageCallback = 205;
        static final int TRANSACTION_unregisterClientListDataUsageCallback = 203;
        static final int TRANSACTION_unregisterPasswordCallback = 228;
        static final int TRANSACTION_unregisterTasPolicyChangedListener = 330;
        static final int TRANSACTION_unregisterWifiApSmartCallback = 84;
        static final int TRANSACTION_updateGuiderFeature = 219;
        static final int TRANSACTION_updateHostapdMacList = 139;
        static final int TRANSACTION_updateIWCHintCard = 267;
        static final int TRANSACTION_wifiApBackUpClientDataUsageSettingsInfo = 191;
        static final int TRANSACTION_wifiApBleClientRole = 78;
        static final int TRANSACTION_wifiApBleD2DClientRole = 89;
        static final int TRANSACTION_wifiApBleD2DMhsRole = 90;
        static final int TRANSACTION_wifiApBleMhsRole = 79;
        static final int TRANSACTION_wifiApDisassocSta = 130;
        static final int TRANSACTION_wifiApRestoreClientDataUsageSettingsInfo = 192;
        static final int TRANSACTION_wifiApRestoreDailyHotspotDataLimit = 193;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 355;
        }

        public Stub() {
            attachInterface(this, ISemWifiManager.DESCRIPTOR);
        }

        public static ISemWifiManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemWifiManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemWifiManager)) {
                return (ISemWifiManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "setWifiSharingMenuState";
                case 76:
                    return "isClientAcceptedWifiProfileSharing";
                case 77:
                    return "getWifiApBleScanDetail";
                case 78:
                    return "wifiApBleClientRole";
                case 79:
                    return "wifiApBleMhsRole";
                case 80:
                    return "connectToSmartMHS";
                case 81:
                    return "requestStopAutohotspotAdvertisement";
                case 82:
                    return "getSmartApConnectedStatus";
                case 83:
                    return "registerWifiApSmartCallback";
                case 84:
                    return "unregisterWifiApSmartCallback";
                case 85:
                    return "registerWifiApDataUsageCallback";
                case 86:
                    return "unRegisterWifiApDataUsageCallback";
                case 87:
                    return "getSmartApConnectedStatusFromScanResult";
                case 88:
                    return "getWifiApBleD2DScanDetail";
                case 89:
                    return "wifiApBleD2DClientRole";
                case 90:
                    return "wifiApBleD2DMhsRole";
                case 91:
                    return "connectToSmartD2DClient";
                case 92:
                    return "getSmartD2DClientConnectedStatus";
                case 93:
                    return "isWifiApWpa3Supported";
                case 94:
                    return "setWifiApEnabled";
                case 95:
                    return "setLocalOnlyHotspotEnabled";
                case 96:
                    return "getSoftApConfiguration";
                case 97:
                    return "setSoftApConfiguration";
                case 98:
                    return "getStationInfo";
                case 99:
                    return "getTxPower";
                case 100:
                    return "getWifiApFreq";
                case 101:
                    return "setHotspotAntMode";
                case 102:
                    return "getHotspotAntMode";
                case 103:
                    return "setAntMode";
                case 104:
                    return "setPowerSavingTime";
                case 105:
                    return "getMHSConfig";
                case 106:
                    return "setMHSConfig";
                case 107:
                    return "getWifiApChannel";
                case 108:
                    return "getWifiApMaxClient";
                case 109:
                    return "supportWifiAp5GBasedOnCountry";
                case 110:
                    return "supportWifiAp6GBasedOnCountry";
                case 111:
                    return "getWifiApStaList";
                case 112:
                    return "isWifiSharingSupported";
                case 113:
                    return "isWifiSharingLiteSupported";
                case 114:
                    return "isThisSoftApFeatureSupported";
                case 115:
                    return "getWifiApStaListDetail";
                case 116:
                    return "setWifiApConfigurationToDefault";
                case 117:
                    return "getWifiApInterfaceNames";
                case 118:
                    return "getWifiApInterfaceName";
                case 119:
                    return "runIptablesRulesCommand";
                case 120:
                    return "setWifiSharingEnabled";
                case 121:
                    return "setProvisionSuccess";
                case 122:
                    return "getProvisionSuccess";
                case 123:
                    return "isWifiSharingEnabled";
                case 124:
                    return "isWifiApEnabled";
                case 125:
                    return "getWifiApConnectedStationCount";
                case 126:
                    return "getWifiApLOHSState";
                case 127:
                    return "getIndoorStatus";
                case 128:
                    return "getRVFModeStatus";
                case 129:
                    return "setRVFmodeStatus";
                case 130:
                    return "wifiApDisassocSta";
                case 131:
                    return "setWifiApMaxClient";
                case 132:
                    return "resetSoftAp";
                case 133:
                    return "setWifiApMaxClientToFramework";
                case 134:
                    return "getWifiApMaxClientFromFramework";
                case 135:
                    return "setWifiApWpsPbc";
                case 136:
                    return "getWifiApWpsPbc";
                case 137:
                    return "setWifiApIsolate";
                case 138:
                    return "getWifiApIsolate";
                case 139:
                    return "updateHostapdMacList";
                case 140:
                    return "manageWifiApMacAclList";
                case 141:
                    return "readWifiApMacAclList";
                case 142:
                    return "getWifiApMacAclMode";
                case 143:
                    return "setWifiApMacAclMode";
                case 144:
                    return "isWifiApMacAclEnabled";
                case 145:
                    return "setWifiApMacAclEnable";
                case 146:
                    return "reportHotspotDumpLogs";
                case 147:
                    return "isUsingNonTerrestrialNetwork";
                case 148:
                    return "getWifiApState";
                case 149:
                    return "isWifiApEnabledWithDualBand";
                case 150:
                    return "setArdkPowerSaveMode";
                case 151:
                    return "enableHotspotTsfInfo";
                case 152:
                    return "notifyConnect";
                case 153:
                    return "getSoftApBands";
                case 154:
                    return "canAutoHotspotBeEnabled";
                case 155:
                    return "isP2pConnected";
                case 156:
                    return "setAutohotspotToastMessage";
                case 157:
                    return "getSoftApSecurityType";
                case 158:
                    return "isDataSaverEnabled";
                case 159:
                    return "isSoftap11axEnabled";
                case 160:
                    return "isSoftAp6ENetwork";
                case 161:
                    return "getSoftApUpStreamNetworkType";
                case 162:
                    return "getMHSMacFromInterface";
                case 163:
                    return "getSoftApFreq";
                case 164:
                    return "launchWifiApWarningForMcfMHS";
                case 165:
                    return "isNeededToShowWifiApDatalimitReachedDialog";
                case 166:
                    return "getWifiMACAddress";
                case 167:
                    return "autohotspotWifiScanConnect";
                case 168:
                    return "getWifiApHostapdFreq";
                case 169:
                    return "getWifiApHostapdSecurtiy";
                case 170:
                    return "isMCFClientAutohotspotSupported";
                case 171:
                    return "getMcfScanDetail";
                case 172:
                    return "startMcfClientMHSDiscovery";
                case 173:
                    return "startMcfMHSAdvertisement";
                case 174:
                    return "connectToMcfMHS";
                case 175:
                    return "getMcfConnectedStatus";
                case 176:
                    return "getMcfConnectedStatusFromScanResult";
                case 177:
                    return "setWifiApClientMobileDataLimit";
                case 178:
                    return "setWifiApClientTimeLimit";
                case 179:
                    return "setWifiApClientDataPaused";
                case 180:
                    return "setWifiApClientEditedName";
                case 181:
                    return "setWifiApDailyDataLimit";
                case 182:
                    return "getWifiApClientDetails";
                case 183:
                    return "getTopHotspotClientsToday";
                case 184:
                    return "getTopHotspotClientsTodayAsString";
                case 185:
                    return "getWifiApTodaysTotalDataUsage";
                case 186:
                    return "getWifiApDailyDataLimit";
                case 187:
                    return "getTotalAndTop3ClientsDataUsageBetweenGivenDates";
                case 188:
                    return "getMonthlyDataUsage";
                case 189:
                    return "isOverAllMhsDataLimitReached";
                case 190:
                    return "isOverAllMhsDataLimitSet";
                case 191:
                    return "wifiApBackUpClientDataUsageSettingsInfo";
                case 192:
                    return "wifiApRestoreClientDataUsageSettingsInfo";
                case 193:
                    return "wifiApRestoreDailyHotspotDataLimit";
                case 194:
                    return "setWifiApGuestPassword";
                case 195:
                    return "getWifiApGuestPassword";
                case 196:
                    return "isWifiApGuestModeEnabled";
                case 197:
                    return "setWifiApGuestModeEnabled";
                case 198:
                    return "isWifiApGuestModeIsolationEnabled";
                case 199:
                    return "setWifiApGuestModeIsolationEnabled";
                case 200:
                    return "isWifiApGuestClient";
                case 201:
                    return "isSAFamilySupportedBasedOnCountry";
                case 202:
                    return "registerClientListDataUsageCallback";
                case 203:
                    return "unregisterClientListDataUsageCallback";
                case 204:
                    return "registerClientDataUsageCallback";
                case 205:
                    return "unregisterClientDataUsageCallback";
                case 206:
                    return "logWifiAp";
                case 207:
                    return "reportBigData";
                case 208:
                    return "addOrUpdateWifiControlHistory";
                case 209:
                    return "getWifiEnableHistory";
                case 210:
                    return "addOrUpdateNetwork";
                case 211:
                    return "removeNetwork";
                case 212:
                    return "factoryReset";
                case 213:
                    return "resetDeveloperOptionsSettings";
                case 214:
                    return "getConfiguredNetworks";
                case 215:
                    return "allowAutojoinPasspoint";
                case 216:
                    return "getPasspointConfigurations";
                case 217:
                    return "getIssueDetectorDump";
                case 218:
                    return "reportIssue";
                case 219:
                    return "updateGuiderFeature";
                case 220:
                    return "getDiagnosisResults";
                case 221:
                    return "startIssueMonitoring";
                case 222:
                    return "getSilentRoamingDump";
                case 223:
                    return "getConnectivityLog";
                case 224:
                    return "getQoSScores";
                case 225:
                    return "setBtmOptionUserEnabled";
                case 226:
                    return "setBtmOptionUserDisabled";
                case 227:
                    return "registerPasswordCallback";
                case 228:
                    return "unregisterPasswordCallback";
                case 229:
                    return "requestPassword";
                case 230:
                    return "setUserConfirmForSharingPassword";
                case 231:
                    return "isSupportedQoSProvider";
                case 232:
                    return "isSupportedProfileRequest";
                case 233:
                    return "getProfileShareDump";
                case 234:
                    return "getAutoShareDump";
                case 235:
                    return "runAutoShareForCurrent";
                case 236:
                    return "isSupportedAutoWifi";
                case 237:
                    return "getAutoWifiDefaultValue";
                case 238:
                    return "shouldShowAutoWifiBubbleTip";
                case 239:
                    return "isAvailableAutoWifiScan";
                case 240:
                    return "getAutoWifiDump";
                case 241:
                    return "getConfiguredNetworkLocations";
                case 242:
                    return "hasConfiguredNetworkLocations";
                case 243:
                    return "setTestSettings";
                case 244:
                    return "setAllowWifiScan";
                case 245:
                    return "isScanningEnabled";
                case 246:
                    return "startScan";
                case 247:
                    return "setEasySetupScanSettings";
                case 248:
                    return "getEasySetupScanSettings";
                case 249:
                    return "disableRandomMac";
                case 250:
                    return "setImsCallEstablished";
                case 251:
                    return "getWcmEverQualityTested";
                case 252:
                    return "getWifiIconVisibility";
                case 253:
                    return "getCurrentStatusMode";
                case 254:
                    return "getValidState";
                case 255:
                    return "notifyReachabilityLost";
                case 256:
                    return "setConnectivityCheckDisabled";
                case 257:
                    return "setKeepConnectionAlways";
                case 258:
                    return "setKeepConnection";
                case 259:
                    return "setKeepConnectionBigData";
                case 260:
                    return "removeExcludedNetwork";
                case 261:
                    return "retrieveSemWifiConfigsBackupData";
                case 262:
                    return "restoreSemConfigurationsBackupData";
                case 263:
                    return "setConnectionAttemptInfo";
                case 264:
                    return "restoreIWCSettingsValue";
                case 265:
                    return "getIWCQTables";
                case 266:
                    return "setIWCQTables";
                case 267:
                    return "updateIWCHintCard";
                case 268:
                    return "setIWCMockAction";
                case 269:
                    return "disconnectApBlockAutojoin";
                case 270:
                    return "setOptimizerForceControlMode";
                case 271:
                    return "getOptimizerForceControlMode";
                case 272:
                    return "getOptimizerState";
                case 273:
                    return "getServiceDetectionResult";
                case 274:
                    return "setTrafficPatternTestSettings";
                case 275:
                    return "setWifiUwbCoexEnabled";
                case 276:
                    return "setLatencyCritical";
                case 277:
                    return "setPktlogFilter";
                case 278:
                    return "removePktlogFilter";
                case 279:
                    return "saveFwDump";
                case 280:
                    return "getRssi";
                case 281:
                    return "getWifiStaInfo";
                case 282:
                    return "getNumOfWifiAnt";
                case 283:
                    return "getTasMode";
                case 284:
                    return "startTimerForWifiOffload";
                case 285:
                    return "checkAppForWiFiOffloading";
                case 286:
                    return "setTCRule";
                case 287:
                    return "externalTwtInterface";
                case 288:
                    return "getTWTParams";
                case 289:
                    return "getCtlFeatureState";
                case 290:
                    return "resetCallbackCondition";
                case 291:
                    return "resetComebackCondition";
                case 292:
                    return "getCurrentL2TransitionMode";
                case 293:
                    return "getL2TransitionLog";
                case 294:
                    return "getNumberOfDataInEachRssiLevel";
                case 295:
                    return "getIwhState";
                case 296:
                    return "getIccState";
                case 297:
                    return "setSamsungMloCtrl";
                case 298:
                    return "setSamsungIwhCtrl";
                case 299:
                    return "getSamsungMloCtrl";
                case 300:
                    return "getSamsungIwhCtrl";
                case 301:
                    return "setTestMode";
                case 302:
                    return "iwhIntendedDisconnection";
                case 303:
                    return "linkQosQuery";
                case 304:
                    return "setWifiAiServiceState";
                case 305:
                    return "setWifiAiServiceNsdResult";
                case 306:
                    return "setWifiAiIwhTrainingResult";
                case 307:
                    return "setWifiAiIwhInferenceResult";
                case 308:
                    return "setIlaTrainingResult";
                case 309:
                    return "setWifiAiIccTrainingResult";
                case 310:
                    return "setWifiAiIccInferenceResult";
                case 311:
                    return "setWifiAiIccInferenceResult2";
                case 312:
                    return "setWifiAiIccInferenceConfidence";
                case 313:
                    return "getTcpMonitorSocketForegroundHistory";
                case 314:
                    return "getTcpMonitorAllSocketHistory";
                case 315:
                    return "getTcpMonitorDnsHistory";
                case 316:
                    return "isIndividualAppSupported";
                case 317:
                    return "getWifiUsabilityStatsEntry";
                case 318:
                    return "isAvailableTdls";
                case 319:
                    return "isWiderBandwidthTdlsSupported";
                case 320:
                    return "setTdlsEnabled";
                case 321:
                    return "getMaxTdlsSession";
                case 322:
                    return "getNumOfTdlsSession";
                case 323:
                    return "getMHSClientTrafficDetails";
                case 324:
                    return "getNRTTrafficbandwidth";
                case 325:
                    return "getDataConsumedValues";
                case 326:
                    return "resetTotalPriorityDataConsumedValues";
                case 327:
                    return "getTasAverage";
                case 328:
                    return "setTasPolicy";
                case 329:
                    return "registerTasPolicyChangedListener";
                case 330:
                    return "unregisterTasPolicyChangedListener";
                case 331:
                    return "enableTxPowerLogging";
                case 332:
                    return "getDynamicFeatureStatus";
                case 333:
                    return "checkUnauthorizedRro";
                case 334:
                    return "checkAndGetUnauthorizedRro";
                case 335:
                    return "checkUnauthorizedRroWithoutToast";
                case 336:
                    return "checkAndGetUnauthorizedRroWithoutToast";
                case 337:
                    return "isSwitchToMobileDataDefaultOff";
                case 338:
                    return "setMhsAiServiceState";
                case 339:
                    return "setMhsAiServiceNsdResult";
                case 340:
                    return "startCapture";
                case 341:
                    return "stopCapture";
                case 342:
                    return "isCaptureRunning";
                case 343:
                    return "getIsPacketCaptureSupportedByDriver";
                case 344:
                    return "setMcfMultiControlMode";
                case 345:
                    return "registerAbTestConfigUpdateObserver";
                case 346:
                    return "unregisterAbTestConfigUpdateObserver";
                case 347:
                    return "reportAbTestResult";
                case 348:
                    return "getAbTestConfigs";
                case 349:
                    return "getAbTestConfiguredModule";
                case 350:
                    return "sendReassociationFrequencyRequestFrame";
                case 351:
                    return "setInsInferenceResult";
                case 352:
                    return "getCandidateNetworkScores";
                case 353:
                    return "getLastSelectedTimeStampForSilentRoaming";
                case 354:
                    return "setLastSelectedTimeStampForSilentRoaming";
                case 355:
                    return "getLastSelectedNetworkIdForSilentRoaming";
                case 356:
                    return "setLastSelectedNetworkIdForSilentRoaming";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMaxDtimInSuspendMode(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDtimInSuspendMode(readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVerboseLoggingEnabled(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    blockFccChannelBackoff(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiDeveloperModeEnabled(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean isWifiDeveloperModeEnabled = isWifiDeveloperModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiDeveloperModeEnabled);
                    return true;
                case 7:
                    String wifiFirmwareVersion = getWifiFirmwareVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiFirmwareVersion);
                    return true;
                case 8:
                    String wifiCid = getWifiCid();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiCid);
                    return true;
                case 9:
                    String wifiVersions = getWifiVersions();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiVersions);
                    return true;
                case 10:
                    String factoryMacAddress = getFactoryMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(factoryMacAddress);
                    return true;
                case 11:
                    String antInfo = getAntInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(antInfo);
                    return true;
                case 12:
                    String frameburstInfo = getFrameburstInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(frameburstInfo);
                    return true;
                case 13:
                    String psmInfo = getPsmInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(psmInfo);
                    return true;
                case 14:
                    String wifiSupportedFeatureSet = getWifiSupportedFeatureSet();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiSupportedFeatureSet);
                    return true;
                case 15:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String vendorWlanDriverProp = getVendorWlanDriverProp(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(vendorWlanDriverProp);
                    return true;
                case 16:
                    return onTransact$setVendorWlanDriverProp$(parcel, parcel2);
                case 17:
                    boolean removeFactoryMacAddress = removeFactoryMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeFactoryMacAddress);
                    return true;
                case 18:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean factoryMacAddress2 = setFactoryMacAddress(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(factoryMacAddress2);
                    return true;
                case 19:
                    return onTransact$setFccChannelBackoffEnabled$(parcel, parcel2);
                case 20:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean psmInfo2 = setPsmInfo(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(psmInfo2);
                    return true;
                case 21:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean antInfo2 = setAntInfo(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(antInfo2);
                    return true;
                case 22:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean frameburstInfo2 = setFrameburstInfo(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(frameburstInfo2);
                    return true;
                case 23:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dcxoCalibrationData = setDcxoCalibrationData(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dcxoCalibrationData);
                    return true;
                case 24:
                    String dcxoCalibrationData2 = getDcxoCalibrationData();
                    parcel2.writeNoException();
                    parcel2.writeString(dcxoCalibrationData2);
                    return true;
                case 25:
                    Bundle currentWifiRouterInfo = getCurrentWifiRouterInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentWifiRouterInfo, 1);
                    return true;
                case 26:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle wifiRouterInfo = getWifiRouterInfo(readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiRouterInfo, 1);
                    return true;
                case 27:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiRouterInfoBestEffort = getWifiRouterInfoBestEffort(readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiRouterInfoBestEffort);
                    return true;
                case 28:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiRouterInfoPresentable = getWifiRouterInfoPresentable(readString9);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiRouterInfoPresentable);
                    return true;
                case 29:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle wifiRouterInfoByBssid = getWifiRouterInfoByBssid(readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiRouterInfoByBssid, 1);
                    return true;
                case 30:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiRouterInfoBestEffortByBssid = getWifiRouterInfoBestEffortByBssid(readString11);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiRouterInfoBestEffortByBssid);
                    return true;
                case 31:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String wifiRouterInfoPresentableByBssid = getWifiRouterInfoPresentableByBssid(readString12);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiRouterInfoPresentableByBssid);
                    return true;
                case 32:
                    Map networkLastUpdatedTimeMap = getNetworkLastUpdatedTimeMap();
                    parcel2.writeNoException();
                    parcel2.writeMap(networkLastUpdatedTimeMap);
                    return true;
                case 33:
                    String currentStateAndEnterTime = getCurrentStateAndEnterTime();
                    parcel2.writeNoException();
                    parcel2.writeString(currentStateAndEnterTime);
                    return true;
                case 34:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long[] networkUsageInfo = getNetworkUsageInfo(readString13);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(networkUsageInfo);
                    return true;
                case 35:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String dailyUsageInfo = getDailyUsageInfo(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(dailyUsageInfo);
                    return true;
                case 36:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setGripSensorMonitorEnabled(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    boolean isGripSensorMonitorEnabled = isGripSensorMonitorEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGripSensorMonitorEnabled);
                    return true;
                case 38:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    triggerBackoffRoutine(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    set5GmmWaveSarBackoffEnabled(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean uploadModeEnabled = setUploadModeEnabled(readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uploadModeEnabled);
                    return true;
                case 41:
                    boolean isUploadModeEnabled = isUploadModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUploadModeEnabled);
                    return true;
                case 42:
                    int advancedAutohotspotConnectSettings = getAdvancedAutohotspotConnectSettings();
                    parcel2.writeNoException();
                    parcel2.writeInt(advancedAutohotspotConnectSettings);
                    return true;
                case 43:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdvancedAutohotspotConnectSettings(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int advancedAutohotspotLCDSettings = getAdvancedAutohotspotLCDSettings();
                    parcel2.writeNoException();
                    parcel2.writeInt(advancedAutohotspotLCDSettings);
                    return true;
                case 45:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiSettingsForegroundState(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiApWarningActivityRunning(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int wifiApWarningActivityRunningState = getWifiApWarningActivityRunningState();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApWarningActivityRunningState);
                    return true;
                case 48:
                    clearAutoHotspotLists();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdvancedAutohotspotLCDSettings(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int channelUtilization = getChannelUtilization();
                    parcel2.writeNoException();
                    parcel2.writeInt(channelUtilization);
                    return true;
                case 51:
                    Map channelUtilizationExtended = getChannelUtilizationExtended();
                    parcel2.writeNoException();
                    parcel2.writeMap(channelUtilizationExtended);
                    return true;
                case 52:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean roamTrigger = setRoamTrigger(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamTrigger);
                    return true;
                case 53:
                    int roamTrigger2 = getRoamTrigger();
                    parcel2.writeNoException();
                    parcel2.writeInt(roamTrigger2);
                    return true;
                case 54:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean roamDelta = setRoamDelta(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamDelta);
                    return true;
                case 55:
                    int roamDelta2 = getRoamDelta();
                    parcel2.writeNoException();
                    parcel2.writeInt(roamDelta2);
                    return true;
                case 56:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean roamScanPeriod = setRoamScanPeriod(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamScanPeriod);
                    return true;
                case 57:
                    int roamScanPeriod2 = getRoamScanPeriod();
                    parcel2.writeNoException();
                    parcel2.writeInt(roamScanPeriod2);
                    return true;
                case 58:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean roamBand = setRoamBand(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamBand);
                    return true;
                case 59:
                    int roamBand2 = getRoamBand();
                    parcel2.writeNoException();
                    parcel2.writeInt(roamBand2);
                    return true;
                case 60:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean countryRev = setCountryRev(readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(countryRev);
                    return true;
                case 61:
                    String countryRev2 = getCountryRev();
                    parcel2.writeNoException();
                    parcel2.writeString(countryRev2);
                    return true;
                case 62:
                    String countryCode = getCountryCode();
                    parcel2.writeNoException();
                    parcel2.writeString(countryCode);
                    return true;
                case 63:
                    String wifi7DisabledCountry = getWifi7DisabledCountry();
                    parcel2.writeNoException();
                    parcel2.writeString(wifi7DisabledCountry);
                    return true;
                case 64:
                    boolean isNCHOModeEnabled = isNCHOModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNCHOModeEnabled);
                    return true;
                case 65:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nCHOModeEnabled = setNCHOModeEnabled(readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nCHOModeEnabled);
                    return true;
                case 66:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean roamScanEnabled = setRoamScanEnabled(readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamScanEnabled);
                    return true;
                case 67:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean roamScanChannels = setRoamScanChannels(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(roamScanChannels);
                    return true;
                case 68:
                    boolean isWesModeEnabled = isWesModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWesModeEnabled);
                    return true;
                case 69:
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wesModeEnabled = setWesModeEnabled(readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wesModeEnabled);
                    return true;
                case 70:
                    return onTransact$sendVendorSpecificActionFrame$(parcel, parcel2);
                case 71:
                    return onTransact$sendReassociationRequestFrame$(parcel, parcel2);
                case 72:
                    int smartMHSLockStatus = getSmartMHSLockStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(smartMHSLockStatus);
                    return true;
                case 73:
                    int canSmartMHSLocked = canSmartMHSLocked();
                    parcel2.writeNoException();
                    parcel2.writeInt(canSmartMHSLocked);
                    return true;
                case 74:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int smartMHSLocked = setSmartMHSLocked(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(smartMHSLocked);
                    return true;
                case 75:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiSharingMenuState = setWifiSharingMenuState(readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiSharingMenuState);
                    return true;
                case 76:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isClientAcceptedWifiProfileSharing(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    List<SemWifiApBleScanResult> wifiApBleScanDetail = getWifiApBleScanDetail();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(wifiApBleScanDetail, 1);
                    return true;
                case 78:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiApBleClientRole = wifiApBleClientRole(readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApBleClientRole);
                    return true;
                case 79:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiApBleMhsRole = wifiApBleMhsRole(readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApBleMhsRole);
                    return true;
                case 80:
                    return onTransact$connectToSmartMHS$(parcel, parcel2);
                case 81:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestStopAutohotspotAdvertisement(readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int smartApConnectedStatus = getSmartApConnectedStatus(readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(smartApConnectedStatus);
                    return true;
                case 83:
                    return onTransact$registerWifiApSmartCallback$(parcel, parcel2);
                case 84:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWifiApSmartCallback(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    return onTransact$registerWifiApDataUsageCallback$(parcel, parcel2);
                case 86:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unRegisterWifiApDataUsageCallback(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int smartApConnectedStatusFromScanResult = getSmartApConnectedStatusFromScanResult(readString16);
                    parcel2.writeNoException();
                    parcel2.writeInt(smartApConnectedStatusFromScanResult);
                    return true;
                case 88:
                    List<SemWifiApBleScanResult> wifiApBleD2DScanDetail = getWifiApBleD2DScanDetail();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(wifiApBleD2DScanDetail, 1);
                    return true;
                case 89:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiApBleD2DClientRole = wifiApBleD2DClientRole(readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApBleD2DClientRole);
                    return true;
                case 90:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiApBleD2DMhsRole = wifiApBleD2DMhsRole(readBoolean18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApBleD2DMhsRole);
                    return true;
                case 91:
                    return onTransact$connectToSmartD2DClient$(parcel, parcel2);
                case 92:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int smartD2DClientConnectedStatus = getSmartD2DClientConnectedStatus(readString17);
                    parcel2.writeNoException();
                    parcel2.writeInt(smartD2DClientConnectedStatus);
                    return true;
                case 93:
                    boolean isWifiApWpa3Supported = isWifiApWpa3Supported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApWpa3Supported);
                    return true;
                case 94:
                    return onTransact$setWifiApEnabled$(parcel, parcel2);
                case 95:
                    return onTransact$setLocalOnlyHotspotEnabled$(parcel, parcel2);
                case 96:
                    SoftApConfiguration softApConfiguration = getSoftApConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(softApConfiguration, 1);
                    return true;
                case 97:
                    SoftApConfiguration softApConfiguration2 = (SoftApConfiguration) parcel.readTypedObject(SoftApConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSoftApConfiguration(softApConfiguration2);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String stationInfo = getStationInfo(readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(stationInfo);
                    return true;
                case 99:
                    String txPower = getTxPower();
                    parcel2.writeNoException();
                    parcel2.writeString(txPower);
                    return true;
                case 100:
                    int wifiApFreq = getWifiApFreq();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApFreq);
                    return true;
                case 101:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHotspotAntMode(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int hotspotAntMode = getHotspotAntMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(hotspotAntMode);
                    return true;
                case 103:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAntMode(readInt15);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPowerSavingTime(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mHSConfig = getMHSConfig(readString19);
                    parcel2.writeNoException();
                    parcel2.writeString(mHSConfig);
                    return true;
                case 106:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mHSConfig2 = setMHSConfig(readString20);
                    parcel2.writeNoException();
                    parcel2.writeString(mHSConfig2);
                    return true;
                case 107:
                    int wifiApChannel = getWifiApChannel();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApChannel);
                    return true;
                case 108:
                    int wifiApMaxClient = getWifiApMaxClient();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApMaxClient);
                    return true;
                case 109:
                    boolean supportWifiAp5GBasedOnCountry = supportWifiAp5GBasedOnCountry();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportWifiAp5GBasedOnCountry);
                    return true;
                case 110:
                    boolean supportWifiAp6GBasedOnCountry = supportWifiAp6GBasedOnCountry();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportWifiAp6GBasedOnCountry);
                    return true;
                case 111:
                    String wifiApStaList = getWifiApStaList();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiApStaList);
                    return true;
                case 112:
                    boolean isWifiSharingSupported = isWifiSharingSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiSharingSupported);
                    return true;
                case 113:
                    boolean isWifiSharingLiteSupported = isWifiSharingLiteSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiSharingLiteSupported);
                    return true;
                case 114:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isThisSoftApFeatureSupported = isThisSoftApFeatureSupported(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isThisSoftApFeatureSupported);
                    return true;
                case 115:
                    List<String> wifiApStaListDetail = getWifiApStaListDetail();
                    parcel2.writeNoException();
                    parcel2.writeStringList(wifiApStaListDetail);
                    return true;
                case 116:
                    setWifiApConfigurationToDefault();
                    parcel2.writeNoException();
                    return true;
                case 117:
                    List<String> wifiApInterfaceNames = getWifiApInterfaceNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(wifiApInterfaceNames);
                    return true;
                case 118:
                    String wifiApInterfaceName = getWifiApInterfaceName();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiApInterfaceName);
                    return true;
                case 119:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String runIptablesRulesCommand = runIptablesRulesCommand(readString21);
                    parcel2.writeNoException();
                    parcel2.writeString(runIptablesRulesCommand);
                    return true;
                case 120:
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiSharingEnabled = setWifiSharingEnabled(readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiSharingEnabled);
                    return true;
                case 121:
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean provisionSuccess = setProvisionSuccess(readBoolean20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(provisionSuccess);
                    return true;
                case 122:
                    int provisionSuccess2 = getProvisionSuccess();
                    parcel2.writeNoException();
                    parcel2.writeInt(provisionSuccess2);
                    return true;
                case 123:
                    boolean isWifiSharingEnabled = isWifiSharingEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiSharingEnabled);
                    return true;
                case 124:
                    boolean isWifiApEnabled = isWifiApEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApEnabled);
                    return true;
                case 125:
                    int wifiApConnectedStationCount = getWifiApConnectedStationCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApConnectedStationCount);
                    return true;
                case 126:
                    int wifiApLOHSState = getWifiApLOHSState();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApLOHSState);
                    return true;
                case 127:
                    int indoorStatus = getIndoorStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(indoorStatus);
                    return true;
                case 128:
                    int rVFModeStatus = getRVFModeStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(rVFModeStatus);
                    return true;
                case 129:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRVFmodeStatus(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wifiApDisassocSta(readString22);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiApMaxClient(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetSoftAp(message);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiApMaxClientToFramework(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    int wifiApMaxClientFromFramework = getWifiApMaxClientFromFramework();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApMaxClientFromFramework);
                    return true;
                case 135:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiApWpsPbc(readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    boolean wifiApWpsPbc = getWifiApWpsPbc();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApWpsPbc);
                    return true;
                case 137:
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiApIsolate(readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    boolean wifiApIsolate = getWifiApIsolate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiApIsolate);
                    return true;
                case 139:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateHostapdMacList(readInt21);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    return onTransact$manageWifiApMacAclList$(parcel, parcel2);
                case 141:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> readWifiApMacAclList = readWifiApMacAclList(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeStringList(readWifiApMacAclList);
                    return true;
                case 142:
                    int wifiApMacAclMode = getWifiApMacAclMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApMacAclMode);
                    return true;
                case 143:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiApMacAclMode(readInt23);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    boolean isWifiApMacAclEnabled = isWifiApMacAclEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApMacAclEnabled);
                    return true;
                case 145:
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiApMacAclEnable(readBoolean23);
                    parcel2.writeNoException();
                    return true;
                case 146:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportHotspotDumpLogs(readString23);
                    parcel2.writeNoException();
                    return true;
                case 147:
                    boolean isUsingNonTerrestrialNetwork = isUsingNonTerrestrialNetwork();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsingNonTerrestrialNetwork);
                    return true;
                case 148:
                    int wifiApState = getWifiApState();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiApState);
                    return true;
                case 149:
                    boolean isWifiApEnabledWithDualBand = isWifiApEnabledWithDualBand();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApEnabledWithDualBand);
                    return true;
                case 150:
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setArdkPowerSaveMode(readBoolean24);
                    parcel2.writeNoException();
                    return true;
                case 151:
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableHotspotTsfInfo(readBoolean25);
                    parcel2.writeNoException();
                    return true;
                case 152:
                    return onTransact$notifyConnect$(parcel, parcel2);
                case 153:
                    int[] softApBands = getSoftApBands();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(softApBands);
                    return true;
                case 154:
                    boolean canAutoHotspotBeEnabled = canAutoHotspotBeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAutoHotspotBeEnabled);
                    return true;
                case 155:
                    boolean isP2pConnected = isP2pConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isP2pConnected);
                    return true;
                case 156:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutohotspotToastMessage(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    int softApSecurityType = getSoftApSecurityType();
                    parcel2.writeNoException();
                    parcel2.writeInt(softApSecurityType);
                    return true;
                case 158:
                    int isDataSaverEnabled = isDataSaverEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isDataSaverEnabled);
                    return true;
                case 159:
                    int isSoftap11axEnabled = isSoftap11axEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isSoftap11axEnabled);
                    return true;
                case 160:
                    int isSoftAp6ENetwork = isSoftAp6ENetwork();
                    parcel2.writeNoException();
                    parcel2.writeInt(isSoftAp6ENetwork);
                    return true;
                case 161:
                    int softApUpStreamNetworkType = getSoftApUpStreamNetworkType();
                    parcel2.writeNoException();
                    parcel2.writeInt(softApUpStreamNetworkType);
                    return true;
                case 162:
                    String mHSMacFromInterface = getMHSMacFromInterface();
                    parcel2.writeNoException();
                    parcel2.writeString(mHSMacFromInterface);
                    return true;
                case 163:
                    int softApFreq = getSoftApFreq();
                    parcel2.writeNoException();
                    parcel2.writeInt(softApFreq);
                    return true;
                case 164:
                    return onTransact$launchWifiApWarningForMcfMHS$(parcel, parcel2);
                case 165:
                    boolean isNeededToShowWifiApDatalimitReachedDialog = isNeededToShowWifiApDatalimitReachedDialog();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNeededToShowWifiApDatalimitReachedDialog);
                    return true;
                case 166:
                    String wifiMACAddress = getWifiMACAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiMACAddress);
                    return true;
                case 167:
                    return onTransact$autohotspotWifiScanConnect$(parcel, parcel2);
                case 168:
                    String wifiApHostapdFreq = getWifiApHostapdFreq();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiApHostapdFreq);
                    return true;
                case 169:
                    String wifiApHostapdSecurtiy = getWifiApHostapdSecurtiy();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiApHostapdSecurtiy);
                    return true;
                case 170:
                    boolean isMCFClientAutohotspotSupported = isMCFClientAutohotspotSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMCFClientAutohotspotSupported);
                    return true;
                case 171:
                    List<SemWifiApBleScanResult> mcfScanDetail = getMcfScanDetail();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(mcfScanDetail, 1);
                    return true;
                case 172:
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startMcfClientMHSDiscovery = startMcfClientMHSDiscovery(readBoolean26);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMcfClientMHSDiscovery);
                    return true;
                case 173:
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startMcfMHSAdvertisement = startMcfMHSAdvertisement(readBoolean27);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMcfMHSAdvertisement);
                    return true;
                case 174:
                    return onTransact$connectToMcfMHS$(parcel, parcel2);
                case 175:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mcfConnectedStatus = getMcfConnectedStatus(readString24);
                    parcel2.writeNoException();
                    parcel2.writeInt(mcfConnectedStatus);
                    return true;
                case 176:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mcfConnectedStatusFromScanResult = getMcfConnectedStatusFromScanResult(readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(mcfConnectedStatusFromScanResult);
                    return true;
                case 177:
                    return onTransact$setWifiApClientMobileDataLimit$(parcel, parcel2);
                case 178:
                    return onTransact$setWifiApClientTimeLimit$(parcel, parcel2);
                case 179:
                    return onTransact$setWifiApClientDataPaused$(parcel, parcel2);
                case 180:
                    return onTransact$setWifiApClientEditedName$(parcel, parcel2);
                case 181:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setWifiApDailyDataLimit(readLong);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemWifiApClientDetails wifiApClientDetails = getWifiApClientDetails(readString26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiApClientDetails, 1);
                    return true;
                case 183:
                    return onTransact$getTopHotspotClientsToday$(parcel, parcel2);
                case 184:
                    return onTransact$getTopHotspotClientsTodayAsString$(parcel, parcel2);
                case 185:
                    long wifiApTodaysTotalDataUsage = getWifiApTodaysTotalDataUsage();
                    parcel2.writeNoException();
                    parcel2.writeLong(wifiApTodaysTotalDataUsage);
                    return true;
                case 186:
                    long wifiApDailyDataLimit = getWifiApDailyDataLimit();
                    parcel2.writeNoException();
                    parcel2.writeLong(wifiApDailyDataLimit);
                    return true;
                case 187:
                    return onTransact$getTotalAndTop3ClientsDataUsageBetweenGivenDates$(parcel, parcel2);
                case 188:
                    List<String> monthlyDataUsage = getMonthlyDataUsage();
                    parcel2.writeNoException();
                    parcel2.writeStringList(monthlyDataUsage);
                    return true;
                case 189:
                    boolean isOverAllMhsDataLimitReached = isOverAllMhsDataLimitReached();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOverAllMhsDataLimitReached);
                    return true;
                case 190:
                    boolean isOverAllMhsDataLimitSet = isOverAllMhsDataLimitSet();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOverAllMhsDataLimitSet);
                    return true;
                case 191:
                    String wifiApBackUpClientDataUsageSettingsInfo = wifiApBackUpClientDataUsageSettingsInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiApBackUpClientDataUsageSettingsInfo);
                    return true;
                case 192:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wifiApRestoreClientDataUsageSettingsInfo(readString27);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    wifiApRestoreDailyHotspotDataLimit(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 194:
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setWifiApGuestPassword(readString28);
                    parcel2.writeNoException();
                    return true;
                case 195:
                    String wifiApGuestPassword = getWifiApGuestPassword();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiApGuestPassword);
                    return true;
                case 196:
                    boolean isWifiApGuestModeEnabled = isWifiApGuestModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApGuestModeEnabled);
                    return true;
                case 197:
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiApGuestModeEnabled(readBoolean28);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    boolean isWifiApGuestModeIsolationEnabled = isWifiApGuestModeIsolationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApGuestModeIsolationEnabled);
                    return true;
                case 199:
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setWifiApGuestModeIsolationEnabled(readBoolean29);
                    parcel2.writeNoException();
                    return true;
                case 200:
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isWifiApGuestClient = isWifiApGuestClient(readString29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiApGuestClient);
                    return true;
                case 201:
                    boolean isSAFamilySupportedBasedOnCountry = isSAFamilySupportedBasedOnCountry();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSAFamilySupportedBasedOnCountry);
                    return true;
                case 202:
                    return onTransact$registerClientListDataUsageCallback$(parcel, parcel2);
                case 203:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterClientListDataUsageCallback(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 204:
                    return onTransact$registerClientDataUsageCallback$(parcel, parcel2);
                case 205:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterClientDataUsageCallback(readInt26);
                    parcel2.writeNoException();
                    return true;
                case 206:
                    return onTransact$logWifiAp$(parcel, parcel2);
                case 207:
                    return onTransact$reportBigData$(parcel, parcel2);
                case 208:
                    return onTransact$addOrUpdateWifiControlHistory$(parcel, parcel2);
                case 209:
                    String wifiEnableHistory = getWifiEnableHistory();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiEnableHistory);
                    return true;
                case 210:
                    SemWifiConfiguration semWifiConfiguration = (SemWifiConfiguration) parcel.readTypedObject(SemWifiConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addOrUpdateNetwork = addOrUpdateNetwork(semWifiConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addOrUpdateNetwork);
                    return true;
                case 211:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeNetwork = removeNetwork(readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeNetwork);
                    return true;
                case 212:
                    factoryReset();
                    return true;
                case 213:
                    resetDeveloperOptionsSettings();
                    return true;
                case 214:
                    ParceledListSlice configuredNetworks = getConfiguredNetworks();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configuredNetworks, 1);
                    return true;
                case 215:
                    return onTransact$allowAutojoinPasspoint$(parcel, parcel2);
                case 216:
                    List passpointConfigurations = getPasspointConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeList(passpointConfigurations);
                    return true;
                case 217:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String issueDetectorDump = getIssueDetectorDump(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeString(issueDetectorDump);
                    return true;
                case 218:
                    return onTransact$reportIssue$(parcel, parcel2);
                case 219:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateGuiderFeature(bundle);
                    return true;
                case 220:
                    List<String> diagnosisResults = getDiagnosisResults();
                    parcel2.writeNoException();
                    parcel2.writeStringList(diagnosisResults);
                    return true;
                case 221:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startIssueMonitoring(bundle2);
                    return true;
                case 222:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String silentRoamingDump = getSilentRoamingDump(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeString(silentRoamingDump);
                    return true;
                case 223:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String connectivityLog = getConnectivityLog(readString31);
                    parcel2.writeNoException();
                    parcel2.writeString(connectivityLog);
                    return true;
                case 224:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    Map qoSScores = getQoSScores(createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeMap(qoSScores);
                    return true;
                case 225:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBtmOptionUserEnabled(readString32);
                    return true;
                case 226:
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBtmOptionUserDisabled(readString33);
                    return true;
                case 227:
                    return onTransact$registerPasswordCallback$(parcel, parcel2);
                case 228:
                    ISemSharedPasswordCallback asInterface = ISemSharedPasswordCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPasswordCallback(asInterface);
                    return true;
                case 229:
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestPassword(readBoolean30);
                    return true;
                case 230:
                    return onTransact$setUserConfirmForSharingPassword$(parcel, parcel2);
                case 231:
                    boolean isSupportedQoSProvider = isSupportedQoSProvider();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportedQoSProvider);
                    return true;
                case 232:
                    boolean isSupportedProfileRequest = isSupportedProfileRequest();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportedProfileRequest);
                    return true;
                case 233:
                    String profileShareDump = getProfileShareDump();
                    parcel2.writeNoException();
                    parcel2.writeString(profileShareDump);
                    return true;
                case 234:
                    String autoShareDump = getAutoShareDump();
                    parcel2.writeNoException();
                    parcel2.writeString(autoShareDump);
                    return true;
                case 235:
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    runAutoShareForCurrent(createStringArrayList2);
                    return true;
                case 236:
                    boolean isSupportedAutoWifi = isSupportedAutoWifi();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportedAutoWifi);
                    return true;
                case 237:
                    boolean autoWifiDefaultValue = getAutoWifiDefaultValue();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoWifiDefaultValue);
                    return true;
                case 238:
                    boolean shouldShowAutoWifiBubbleTip = shouldShowAutoWifiBubbleTip();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowAutoWifiBubbleTip);
                    return true;
                case 239:
                    boolean isAvailableAutoWifiScan = isAvailableAutoWifiScan();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailableAutoWifiScan);
                    return true;
                case 240:
                    String autoWifiDump = getAutoWifiDump();
                    parcel2.writeNoException();
                    parcel2.writeString(autoWifiDump);
                    return true;
                case 241:
                    Map configuredNetworkLocations = getConfiguredNetworkLocations();
                    parcel2.writeNoException();
                    parcel2.writeMap(configuredNetworkLocations);
                    return true;
                case 242:
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasConfiguredNetworkLocations = hasConfiguredNetworkLocations(readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasConfiguredNetworkLocations);
                    return true;
                case 243:
                    return onTransact$setTestSettings$(parcel, parcel2);
                case 244:
                    return onTransact$setAllowWifiScan$(parcel, parcel2);
                case 245:
                    boolean isScanningEnabled = isScanningEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScanningEnabled);
                    return true;
                case 246:
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startScan = startScan(readString35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startScan);
                    return true;
                case 247:
                    return onTransact$setEasySetupScanSettings$(parcel, parcel2);
                case 248:
                    Map<String, SemEasySetupWifiScanSettings> easySetupScanSettings = getEasySetupScanSettings();
                    parcel2.writeNoException();
                    if (easySetupScanSettings == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(easySetupScanSettings.size());
                        easySetupScanSettings.forEach(new BiConsumer() { // from class: com.samsung.android.wifi.ISemWifiManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ISemWifiManager.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (SemEasySetupWifiScanSettings) obj2);
                            }
                        });
                    }
                    return true;
                case 249:
                    disableRandomMac();
                    return true;
                case 250:
                    boolean readBoolean31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImsCallEstablished(readBoolean31);
                    return true;
                case 251:
                    int wcmEverQualityTested = getWcmEverQualityTested();
                    parcel2.writeNoException();
                    parcel2.writeInt(wcmEverQualityTested);
                    return true;
                case 252:
                    int wifiIconVisibility = getWifiIconVisibility();
                    parcel2.writeNoException();
                    parcel2.writeInt(wifiIconVisibility);
                    return true;
                case 253:
                    int currentStatusMode = getCurrentStatusMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentStatusMode);
                    return true;
                case 254:
                    int validState = getValidState();
                    parcel2.writeNoException();
                    parcel2.writeInt(validState);
                    return true;
                case 255:
                    notifyReachabilityLost();
                    parcel2.writeNoException();
                    return true;
                case 256:
                    boolean readBoolean32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setConnectivityCheckDisabled(readBoolean32);
                    parcel2.writeNoException();
                    return true;
                case 257:
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeepConnectionAlways(readBoolean33);
                    return true;
                case 258:
                    return onTransact$setKeepConnection$(parcel, parcel2);
                case 259:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setKeepConnectionBigData(readInt29);
                    return true;
                case 260:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeExcludedNetwork(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 261:
                    String retrieveSemWifiConfigsBackupData = retrieveSemWifiConfigsBackupData();
                    parcel2.writeNoException();
                    parcel2.writeString(retrieveSemWifiConfigsBackupData);
                    return true;
                case 262:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreSemConfigurationsBackupData(readString36);
                    parcel2.writeNoException();
                    return true;
                case 263:
                    return onTransact$setConnectionAttemptInfo$(parcel, parcel2);
                case 264:
                    return onTransact$restoreIWCSettingsValue$(parcel, parcel2);
                case 265:
                    String iWCQTables = getIWCQTables();
                    parcel2.writeNoException();
                    parcel2.writeString(iWCQTables);
                    return true;
                case 266:
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIWCQTables(readString37);
                    parcel2.writeNoException();
                    return true;
                case 267:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateIWCHintCard(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 268:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIWCMockAction(readInt31);
                    parcel2.writeNoException();
                    return true;
                case 269:
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean disconnectApBlockAutojoin = disconnectApBlockAutojoin(readBoolean34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disconnectApBlockAutojoin);
                    return true;
                case 270:
                    return onTransact$setOptimizerForceControlMode$(parcel, parcel2);
                case 271:
                    int optimizerForceControlMode = getOptimizerForceControlMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(optimizerForceControlMode);
                    return true;
                case 272:
                    int[] optimizerState = getOptimizerState();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(optimizerState);
                    return true;
                case 273:
                    int[] serviceDetectionResult = getServiceDetectionResult();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(serviceDetectionResult);
                    return true;
                case 274:
                    return onTransact$setTrafficPatternTestSettings$(parcel, parcel2);
                case 275:
                    return onTransact$setWifiUwbCoexEnabled$(parcel, parcel2);
                case 276:
                    return onTransact$setLatencyCritical$(parcel, parcel2);
                case 277:
                    return onTransact$setPktlogFilter$(parcel, parcel2);
                case 278:
                    return onTransact$removePktlogFilter$(parcel, parcel2);
                case 279:
                    boolean saveFwDump = saveFwDump();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(saveFwDump);
                    return true;
                case 280:
                    return onTransact$getRssi$(parcel, parcel2);
                case 281:
                    String wifiStaInfo = getWifiStaInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(wifiStaInfo);
                    return true;
                case 282:
                    int numOfWifiAnt = getNumOfWifiAnt();
                    parcel2.writeNoException();
                    parcel2.writeInt(numOfWifiAnt);
                    return true;
                case 283:
                    String tasMode = getTasMode();
                    parcel2.writeNoException();
                    parcel2.writeString(tasMode);
                    return true;
                case 284:
                    startTimerForWifiOffload();
                    parcel2.writeNoException();
                    return true;
                case 285:
                    return onTransact$checkAppForWiFiOffloading$(parcel, parcel2);
                case 286:
                    return onTransact$setTCRule$(parcel, parcel2);
                case 287:
                    return onTransact$externalTwtInterface$(parcel, parcel2);
                case 288:
                    int[] tWTParams = getTWTParams();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(tWTParams);
                    return true;
                case 289:
                    Map ctlFeatureState = getCtlFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeMap(ctlFeatureState);
                    return true;
                case 290:
                    return onTransact$resetCallbackCondition$(parcel, parcel2);
                case 291:
                    resetComebackCondition();
                    parcel2.writeNoException();
                    return true;
                case 292:
                    int currentL2TransitionMode = getCurrentL2TransitionMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentL2TransitionMode);
                    return true;
                case 293:
                    String l2TransitionLog = getL2TransitionLog();
                    parcel2.writeNoException();
                    parcel2.writeString(l2TransitionLog);
                    return true;
                case 294:
                    String numberOfDataInEachRssiLevel = getNumberOfDataInEachRssiLevel();
                    parcel2.writeNoException();
                    parcel2.writeString(numberOfDataInEachRssiLevel);
                    return true;
                case 295:
                    String iwhState = getIwhState();
                    parcel2.writeNoException();
                    parcel2.writeString(iwhState);
                    return true;
                case 296:
                    String iccState = getIccState();
                    parcel2.writeNoException();
                    parcel2.writeString(iccState);
                    return true;
                case 297:
                    return onTransact$setSamsungMloCtrl$(parcel, parcel2);
                case 298:
                    return onTransact$setSamsungIwhCtrl$(parcel, parcel2);
                case 299:
                    boolean samsungMloCtrl = getSamsungMloCtrl();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(samsungMloCtrl);
                    return true;
                case 300:
                    boolean samsungIwhCtrl = getSamsungIwhCtrl();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(samsungIwhCtrl);
                    return true;
                case 301:
                    return onTransact$setTestMode$(parcel, parcel2);
                case 302:
                    boolean iwhIntendedDisconnection = iwhIntendedDisconnection();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iwhIntendedDisconnection);
                    return true;
                case 303:
                    return onTransact$linkQosQuery$(parcel, parcel2);
                case 304:
                    return onTransact$setWifiAiServiceState$(parcel, parcel2);
                case 305:
                    return onTransact$setWifiAiServiceNsdResult$(parcel, parcel2);
                case 306:
                    return onTransact$setWifiAiIwhTrainingResult$(parcel, parcel2);
                case 307:
                    return onTransact$setWifiAiIwhInferenceResult$(parcel, parcel2);
                case 308:
                    return onTransact$setIlaTrainingResult$(parcel, parcel2);
                case 309:
                    return onTransact$setWifiAiIccTrainingResult$(parcel, parcel2);
                case 310:
                    return onTransact$setWifiAiIccInferenceResult$(parcel, parcel2);
                case 311:
                    return onTransact$setWifiAiIccInferenceResult2$(parcel, parcel2);
                case 312:
                    return onTransact$setWifiAiIccInferenceConfidence$(parcel, parcel2);
                case 313:
                    return onTransact$getTcpMonitorSocketForegroundHistory$(parcel, parcel2);
                case 314:
                    return onTransact$getTcpMonitorAllSocketHistory$(parcel, parcel2);
                case 315:
                    return onTransact$getTcpMonitorDnsHistory$(parcel, parcel2);
                case 316:
                    boolean isIndividualAppSupported = isIndividualAppSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIndividualAppSupported);
                    return true;
                case 317:
                    return onTransact$getWifiUsabilityStatsEntry$(parcel, parcel2);
                case 318:
                    boolean isAvailableTdls = isAvailableTdls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailableTdls);
                    return true;
                case 319:
                    boolean isWiderBandwidthTdlsSupported = isWiderBandwidthTdlsSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWiderBandwidthTdlsSupported);
                    return true;
                case 320:
                    return onTransact$setTdlsEnabled$(parcel, parcel2);
                case 321:
                    int maxTdlsSession = getMaxTdlsSession();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxTdlsSession);
                    return true;
                case 322:
                    int numOfTdlsSession = getNumOfTdlsSession();
                    parcel2.writeNoException();
                    parcel2.writeInt(numOfTdlsSession);
                    return true;
                case 323:
                    List<String> mHSClientTrafficDetails = getMHSClientTrafficDetails();
                    parcel2.writeNoException();
                    parcel2.writeStringList(mHSClientTrafficDetails);
                    return true;
                case 324:
                    int nRTTrafficbandwidth = getNRTTrafficbandwidth();
                    parcel2.writeNoException();
                    parcel2.writeInt(nRTTrafficbandwidth);
                    return true;
                case 325:
                    long[] dataConsumedValues = getDataConsumedValues();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(dataConsumedValues);
                    return true;
                case 326:
                    resetTotalPriorityDataConsumedValues();
                    parcel2.writeNoException();
                    return true;
                case 327:
                    Map tasAverage = getTasAverage();
                    parcel2.writeNoException();
                    parcel2.writeMap(tasAverage);
                    return true;
                case 328:
                    return onTransact$setTasPolicy$(parcel, parcel2);
                case 329:
                    return onTransact$registerTasPolicyChangedListener$(parcel, parcel2);
                case 330:
                    return onTransact$unregisterTasPolicyChangedListener$(parcel, parcel2);
                case 331:
                    return onTransact$enableTxPowerLogging$(parcel, parcel2);
                case 332:
                    String dynamicFeatureStatus = getDynamicFeatureStatus();
                    parcel2.writeNoException();
                    parcel2.writeString(dynamicFeatureStatus);
                    return true;
                case 333:
                    boolean checkUnauthorizedRro = checkUnauthorizedRro();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkUnauthorizedRro);
                    return true;
                case 334:
                    List<String> checkAndGetUnauthorizedRro = checkAndGetUnauthorizedRro();
                    parcel2.writeNoException();
                    parcel2.writeStringList(checkAndGetUnauthorizedRro);
                    return true;
                case 335:
                    boolean checkUnauthorizedRroWithoutToast = checkUnauthorizedRroWithoutToast();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkUnauthorizedRroWithoutToast);
                    return true;
                case 336:
                    List<String> checkAndGetUnauthorizedRroWithoutToast = checkAndGetUnauthorizedRroWithoutToast();
                    parcel2.writeNoException();
                    parcel2.writeStringList(checkAndGetUnauthorizedRroWithoutToast);
                    return true;
                case 337:
                    boolean isSwitchToMobileDataDefaultOff = isSwitchToMobileDataDefaultOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSwitchToMobileDataDefaultOff);
                    return true;
                case 338:
                    return onTransact$setMhsAiServiceState$(parcel, parcel2);
                case 339:
                    return onTransact$setMhsAiServiceNsdResult$(parcel, parcel2);
                case 340:
                    return onTransact$startCapture$(parcel, parcel2);
                case 341:
                    int stopCapture = stopCapture();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopCapture);
                    return true;
                case 342:
                    int isCaptureRunning = isCaptureRunning();
                    parcel2.writeNoException();
                    parcel2.writeInt(isCaptureRunning);
                    return true;
                case 343:
                    boolean isPacketCaptureSupportedByDriver = getIsPacketCaptureSupportedByDriver();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPacketCaptureSupportedByDriver);
                    return true;
                case 344:
                    return onTransact$setMcfMultiControlMode$(parcel, parcel2);
                case 345:
                    return onTransact$registerAbTestConfigUpdateObserver$(parcel, parcel2);
                case 346:
                    return onTransact$unregisterAbTestConfigUpdateObserver$(parcel, parcel2);
                case 347:
                    return onTransact$reportAbTestResult$(parcel, parcel2);
                case 348:
                    List<SemAbTestConfiguration> abTestConfigs = getAbTestConfigs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(abTestConfigs, 1);
                    return true;
                case 349:
                    return onTransact$getAbTestConfiguredModule$(parcel, parcel2);
                case 350:
                    return onTransact$sendReassociationFrequencyRequestFrame$(parcel, parcel2);
                case 351:
                    return onTransact$setInsInferenceResult$(parcel, parcel2);
                case 352:
                    String candidateNetworkScores = getCandidateNetworkScores();
                    parcel2.writeNoException();
                    parcel2.writeString(candidateNetworkScores);
                    return true;
                case 353:
                    long lastSelectedTimeStampForSilentRoaming = getLastSelectedTimeStampForSilentRoaming();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastSelectedTimeStampForSilentRoaming);
                    return true;
                case 354:
                    setLastSelectedTimeStampForSilentRoaming();
                    parcel2.writeNoException();
                    return true;
                case 355:
                    int lastSelectedNetworkIdForSilentRoaming = getLastSelectedNetworkIdForSilentRoaming();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastSelectedNetworkIdForSilentRoaming);
                    return true;
                case 356:
                    return onTransact$setLastSelectedNetworkIdForSilentRoaming$(parcel, parcel2);
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, SemEasySetupWifiScanSettings semEasySetupWifiScanSettings) {
            parcel.writeString(str);
            parcel.writeTypedObject(semEasySetupWifiScanSettings, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements ISemWifiManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMaxDtimInSuspendMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setDtimInSuspendMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setVerboseLoggingEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void blockFccChannelBackoff(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiDeveloperModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiDeveloperModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiFirmwareVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiCid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiVersions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getFactoryMacAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getAntInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getFrameburstInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getPsmInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiSupportedFeatureSet() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getVendorWlanDriverProp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setVendorWlanDriverProp(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean removeFactoryMacAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setFactoryMacAddress(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setFccChannelBackoffEnabled(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setPsmInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setAntInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setFrameburstInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setDcxoCalibrationData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getDcxoCalibrationData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Bundle getCurrentWifiRouterInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Bundle getWifiRouterInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoBestEffort(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoPresentable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Bundle getWifiRouterInfoByBssid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoBestEffortByBssid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiRouterInfoPresentableByBssid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getNetworkLastUpdatedTimeMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCurrentStateAndEnterTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long[] getNetworkUsageInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getDailyUsageInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setGripSensorMonitorEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isGripSensorMonitorEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void triggerBackoffRoutine(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void set5GmmWaveSarBackoffEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setUploadModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isUploadModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getAdvancedAutohotspotConnectSettings() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAdvancedAutohotspotConnectSettings(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getAdvancedAutohotspotLCDSettings() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiSettingsForegroundState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApWarningActivityRunning(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApWarningActivityRunningState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void clearAutoHotspotLists() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAdvancedAutohotspotLCDSettings(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getChannelUtilization() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getChannelUtilizationExtended() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamTrigger(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamTrigger() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamDelta(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamDelta() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamScanPeriod(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamScanPeriod() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamBand(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRoamBand() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setCountryRev(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCountryRev() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCountryCode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifi7DisabledCountry() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isNCHOModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setNCHOModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamScanEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setRoamScanChannels(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWesModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWesModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean sendVendorSpecificActionFrame(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean sendReassociationRequestFrame(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartMHSLockStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int canSmartMHSLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int setSmartMHSLocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWifiSharingMenuState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void isClientAcceptedWifiProfileSharing(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApBleScanResult> getWifiApBleScanDetail() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemWifiApBleScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleClientRole(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleMhsRole(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean connectToSmartMHS(String str, int i, int i2, int i3, String str2, String str3, int i4, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void requestStopAutohotspotAdvertisement(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartApConnectedStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerWifiApSmartCallback(IBinder iBinder, ISemWifiApSmartCallback iSemWifiApSmartCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iSemWifiApSmartCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterWifiApSmartCallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerWifiApDataUsageCallback(IBinder iBinder, ISemWifiApDataUsageCallback iSemWifiApDataUsageCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iSemWifiApDataUsageCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unRegisterWifiApDataUsageCallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartApConnectedStatusFromScanResult(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApBleScanResult> getWifiApBleD2DScanDetail() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemWifiApBleScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleD2DClientRole(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean wifiApBleD2DMhsRole(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean connectToSmartD2DClient(String str, String str2, ISemWifiApSmartCallback iSemWifiApSmartCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iSemWifiApSmartCallback);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSmartD2DClientConnectedStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApWpa3Supported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWifiApEnabled(SoftApConfiguration softApConfiguration, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(softApConfiguration, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setLocalOnlyHotspotEnabled(boolean z, String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public SoftApConfiguration getSoftApConfiguration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SoftApConfiguration) obtain2.readTypedObject(SoftApConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setSoftApConfiguration(SoftApConfiguration softApConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(softApConfiguration, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getStationInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTxPower() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApFreq() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setHotspotAntMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getHotspotAntMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAntMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setPowerSavingTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getMHSConfig(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String setMHSConfig(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApChannel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApMaxClient() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean supportWifiAp5GBasedOnCountry() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean supportWifiAp6GBasedOnCountry() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApStaList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiSharingSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiSharingLiteSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isThisSoftApFeatureSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getWifiApStaListDetail() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApConfigurationToDefault() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getWifiApInterfaceNames() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApInterfaceName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String runIptablesRulesCommand(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setWifiSharingEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setProvisionSuccess(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getProvisionSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiSharingEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApConnectedStationCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApLOHSState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getIndoorStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRVFModeStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setRVFmodeStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void wifiApDisassocSta(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMaxClient(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetSoftAp(Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMaxClientToFramework(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApMaxClientFromFramework() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApWpsPbc(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getWifiApWpsPbc() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApIsolate(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getWifiApIsolate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void updateHostapdMacList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int manageWifiApMacAclList(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> readWifiApMacAclList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApMacAclMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMacAclMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApMacAclEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApMacAclEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportHotspotDumpLogs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isUsingNonTerrestrialNetwork() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiApState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApEnabledWithDualBand() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setArdkPowerSaveMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void enableHotspotTsfInfo(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void notifyConnect(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getSoftApBands() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean canAutoHotspotBeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isP2pConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAutohotspotToastMessage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSoftApSecurityType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isDataSaverEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isSoftap11axEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isSoftAp6ENetwork() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSoftApUpStreamNetworkType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getMHSMacFromInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getSoftApFreq() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void launchWifiApWarningForMcfMHS(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isNeededToShowWifiApDatalimitReachedDialog() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiMACAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int autohotspotWifiScanConnect(String str, String str2, String str3, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApHostapdFreq() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApHostapdSecurtiy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isMCFClientAutohotspotSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApBleScanResult> getMcfScanDetail() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemWifiApBleScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int startMcfClientMHSDiscovery(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int startMcfMHSAdvertisement(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getMcfConnectedStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getMcfConnectedStatusFromScanResult(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientMobileDataLimit(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientTimeLimit(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientDataPaused(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApClientEditedName(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApDailyDataLimit(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public SemWifiApClientDetails getWifiApClientDetails(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWifiApClientDetails) obtain2.readTypedObject(SemWifiApClientDetails.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemWifiApClientDetails> getTopHotspotClientsToday(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemWifiApClientDetails.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTopHotspotClientsTodayAsString(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long getWifiApTodaysTotalDataUsage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long getWifiApDailyDataLimit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getMonthlyDataUsage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isOverAllMhsDataLimitReached() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isOverAllMhsDataLimitSet() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String wifiApBackUpClientDataUsageSettingsInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void wifiApRestoreClientDataUsageSettingsInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void wifiApRestoreDailyHotspotDataLimit(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApGuestPassword(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiApGuestPassword() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApGuestModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApGuestModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApGuestModeIsolationEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiApGuestModeIsolationEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWifiApGuestClient(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSAFamilySupportedBasedOnCountry() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerClientListDataUsageCallback(IBinder iBinder, ISemWifiApClientListUpdateCallback iSemWifiApClientListUpdateCallback, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iSemWifiApClientListUpdateCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterClientListDataUsageCallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerClientDataUsageCallback(IBinder iBinder, ISemWifiApClientUpdateCallback iSemWifiApClientUpdateCallback, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iSemWifiApClientUpdateCallback);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterClientDataUsageCallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void logWifiAp(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportBigData(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(207, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void addOrUpdateWifiControlHistory(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(208, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiEnableHistory() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean addOrUpdateNetwork(SemWifiConfiguration semWifiConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(semWifiConfiguration, 0);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean removeNetwork(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void factoryReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(212, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetDeveloperOptionsSettings() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(213, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public ParceledListSlice getConfiguredNetworks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void allowAutojoinPasspoint(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(215, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List getPasspointConfigurations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIssueDetectorDump(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportIssue(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(218, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void updateGuiderFeature(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(219, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getDiagnosisResults() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void startIssueMonitoring(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(221, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getSilentRoamingDump(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getConnectivityLog(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getQoSScores(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setBtmOptionUserEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(225, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setBtmOptionUserDisabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(226, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerPasswordCallback(String str, ISemSharedPasswordCallback iSemSharedPasswordCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSemSharedPasswordCallback);
                    this.mRemote.transact(227, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterPasswordCallback(ISemSharedPasswordCallback iSemSharedPasswordCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemSharedPasswordCallback);
                    this.mRemote.transact(228, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void requestPassword(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(229, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setUserConfirmForSharingPassword(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(230, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSupportedQoSProvider() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSupportedProfileRequest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getProfileShareDump() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getAutoShareDump() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void runAutoShareForCurrent(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(235, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSupportedAutoWifi() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getAutoWifiDefaultValue() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean shouldShowAutoWifiBubbleTip() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isAvailableAutoWifiScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getAutoWifiDump() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getConfiguredNetworkLocations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean hasConfiguredNetworkLocations(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTestSettings(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(243, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setAllowWifiScan(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(244, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isScanningEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean startScan(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setEasySetupScanSettings(String str, SemEasySetupWifiScanSettings semEasySetupWifiScanSettings) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(semEasySetupWifiScanSettings, 0);
                    this.mRemote.transact(247, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map<String, SemEasySetupWifiScanSettings> getEasySetupScanSettings() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: com.samsung.android.wifi.ISemWifiManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), (SemEasySetupWifiScanSettings) Parcel.this.readTypedObject(SemEasySetupWifiScanSettings.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void disableRandomMac() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(249, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setImsCallEstablished(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(250, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWcmEverQualityTested() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getWifiIconVisibility() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getCurrentStatusMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getValidState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void notifyReachabilityLost() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(255, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setConnectivityCheckDisabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(256, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setKeepConnectionAlways(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(257, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setKeepConnection(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(258, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setKeepConnectionBigData(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(259, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void removeExcludedNetwork(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(260, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String retrieveSemWifiConfigsBackupData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void restoreSemConfigurationsBackupData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setConnectionAttemptInfo(int i, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(263, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void restoreIWCSettingsValue(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(264, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIWCQTables() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(265, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setIWCQTables(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(266, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void updateIWCHintCard(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(267, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setIWCMockAction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(268, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean disconnectApBlockAutojoin(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(269, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setOptimizerForceControlMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(270, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getOptimizerForceControlMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(271, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getOptimizerState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(272, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getServiceDetectionResult() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(273, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTrafficPatternTestSettings(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(274, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int setWifiUwbCoexEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(275, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setLatencyCritical(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(276, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setPktlogFilter(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(277, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean removePktlogFilter(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(278, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean saveFwDump() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(279, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getRssi(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(280, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiStaInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(281, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getNumOfWifiAnt() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(282, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTasMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(283, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void startTimerForWifiOffload() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(284, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void checkAppForWiFiOffloading(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(285, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTCRule(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(286, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void externalTwtInterface(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(287, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int[] getTWTParams() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(288, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getCtlFeatureState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(289, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetCallbackCondition(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(290, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetComebackCondition() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(291, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getCurrentL2TransitionMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(292, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getL2TransitionLog() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(293, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getNumberOfDataInEachRssiLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(294, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIwhState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(295, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getIccState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(296, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setSamsungMloCtrl(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(297, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setSamsungIwhCtrl(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(298, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getSamsungMloCtrl() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(299, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getSamsungIwhCtrl() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(300, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setTestMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(301, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean iwhIntendedDisconnection() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(302, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean linkQosQuery(long j, long j2, long j3, int i, long j4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i);
                    obtain.writeLong(j4);
                    this.mRemote.transact(303, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(304, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiServiceNsdResult(int[] iArr, int[] iArr2, int[] iArr3, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeIntArray(iArr3);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(305, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIwhTrainingResult(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(306, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIwhInferenceResult(boolean[] zArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBooleanArray(zArr);
                    this.mRemote.transact(307, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setIlaTrainingResult(double d, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeDouble(d);
                    obtain.writeString(str);
                    this.mRemote.transact(308, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIccTrainingResult(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(309, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIccInferenceResult(boolean[] zArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBooleanArray(zArr);
                    this.mRemote.transact(310, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIccInferenceResult2(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(311, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setWifiAiIccInferenceConfidence(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(312, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTcpMonitorSocketForegroundHistory(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(313, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTcpMonitorAllSocketHistory(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(314, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getTcpMonitorDnsHistory(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(315, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isIndividualAppSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(316, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getWifiUsabilityStatsEntry(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(317, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isAvailableTdls() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(318, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isWiderBandwidthTdlsSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(319, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean setTdlsEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(320, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getMaxTdlsSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(321, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getNumOfTdlsSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(322, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> getMHSClientTrafficDetails() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(323, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getNRTTrafficbandwidth() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(324, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long[] getDataConsumedValues() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(325, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void resetTotalPriorityDataConsumedValues() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(326, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map getTasAverage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(327, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public Map setTasPolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(328, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongInterface(semTasPolicyListener);
                    this.mRemote.transact(329, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterTasPolicyChangedListener(SemTasPolicyListener semTasPolicyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongInterface(semTasPolicyListener);
                    this.mRemote.transact(330, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void enableTxPowerLogging(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(331, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getDynamicFeatureStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(332, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean checkUnauthorizedRro() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(333, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> checkAndGetUnauthorizedRro() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(334, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean checkUnauthorizedRroWithoutToast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(335, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<String> checkAndGetUnauthorizedRroWithoutToast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(336, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean isSwitchToMobileDataDefaultOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(337, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMhsAiServiceState(boolean z, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(338, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMhsAiServiceNsdResult(int[] iArr, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(339, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int startCapture(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(340, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int stopCapture() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(341, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int isCaptureRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(342, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean getIsPacketCaptureSupportedByDriver() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(343, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setMcfMultiControlMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(344, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void registerAbTestConfigUpdateObserver(ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemAbTestConfigurationUpdateObserver);
                    obtain.writeString(str);
                    this.mRemote.transact(345, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void unregisterAbTestConfigUpdateObserver(ISemAbTestConfigurationUpdateObserver iSemAbTestConfigurationUpdateObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemAbTestConfigurationUpdateObserver);
                    this.mRemote.transact(346, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void reportAbTestResult(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(347, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public List<SemAbTestConfiguration> getAbTestConfigs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(348, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemAbTestConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public SemAbTestConfiguration getAbTestConfiguredModule(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(349, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemAbTestConfiguration) obtain2.readTypedObject(SemAbTestConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public boolean sendReassociationFrequencyRequestFrame(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(350, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setInsInferenceResult(int i, float f, float f2, float f3, float f4, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    obtain.writeFloat(f4);
                    obtain.writeString(str);
                    this.mRemote.transact(351, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public String getCandidateNetworkScores() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(352, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public long getLastSelectedTimeStampForSilentRoaming() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(353, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setLastSelectedTimeStampForSilentRoaming() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(354, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public int getLastSelectedNetworkIdForSilentRoaming() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    this.mRemote.transact(355, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiManager
            public void setLastSelectedNetworkIdForSilentRoaming(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(356, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        private boolean onTransact$setVendorWlanDriverProp$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean vendorWlanDriverProp = setVendorWlanDriverProp(readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(vendorWlanDriverProp);
            return true;
        }

        private boolean onTransact$setFccChannelBackoffEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setFccChannelBackoffEnabled(readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$sendVendorSpecificActionFrame$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean sendVendorSpecificActionFrame = sendVendorSpecificActionFrame(readString, readInt, readInt2, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(sendVendorSpecificActionFrame);
            return true;
        }

        private boolean onTransact$sendReassociationRequestFrame$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean sendReassociationRequestFrame = sendReassociationRequestFrame(readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(sendReassociationRequestFrame);
            return true;
        }

        private boolean onTransact$connectToSmartMHS$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt4 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean connectToSmartMHS = connectToSmartMHS(readString, readInt, readInt2, readInt3, readString2, readString3, readInt4, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(connectToSmartMHS);
            return true;
        }

        private boolean onTransact$registerWifiApSmartCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            ISemWifiApSmartCallback asInterface = ISemWifiApSmartCallback.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            registerWifiApSmartCallback(readStrongBinder, asInterface, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerWifiApDataUsageCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            ISemWifiApDataUsageCallback asInterface = ISemWifiApDataUsageCallback.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            registerWifiApDataUsageCallback(readStrongBinder, asInterface, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$connectToSmartD2DClient$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            ISemWifiApSmartCallback asInterface = ISemWifiApSmartCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            boolean connectToSmartD2DClient = connectToSmartD2DClient(readString, readString2, asInterface);
            parcel2.writeNoException();
            parcel2.writeBoolean(connectToSmartD2DClient);
            return true;
        }

        private boolean onTransact$setWifiApEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            SoftApConfiguration softApConfiguration = (SoftApConfiguration) parcel.readTypedObject(SoftApConfiguration.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean wifiApEnabled = setWifiApEnabled(softApConfiguration, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(wifiApEnabled);
            return true;
        }

        private boolean onTransact$setLocalOnlyHotspotEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean localOnlyHotspotEnabled = setLocalOnlyHotspotEnabled(readBoolean, readString, readString2, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(localOnlyHotspotEnabled);
            return true;
        }

        private boolean onTransact$manageWifiApMacAclList$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int manageWifiApMacAclList = manageWifiApMacAclList(readString, readString2, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeInt(manageWifiApMacAclList);
            return true;
        }

        private boolean onTransact$notifyConnect$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            notifyConnect(readInt, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$launchWifiApWarningForMcfMHS$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            launchWifiApWarningForMcfMHS(readInt, readInt2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$autohotspotWifiScanConnect$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int autohotspotWifiScanConnect = autohotspotWifiScanConnect(readString, readString2, readString3, readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeInt(autohotspotWifiScanConnect);
            return true;
        }

        private boolean onTransact$connectToMcfMHS$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int connectToMcfMHS = connectToMcfMHS(readString, readInt, readInt2, readInt3, readString2, readString3, readInt4);
            parcel2.writeNoException();
            parcel2.writeInt(connectToMcfMHS);
            return true;
        }

        private boolean onTransact$setWifiApClientMobileDataLimit$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            setWifiApClientMobileDataLimit(readString, readLong);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiApClientTimeLimit$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            setWifiApClientTimeLimit(readString, readLong);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiApClientDataPaused$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setWifiApClientDataPaused(readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setWifiApClientEditedName$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setWifiApClientEditedName(readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getTopHotspotClientsToday$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            List<SemWifiApClientDetails> topHotspotClientsToday = getTopHotspotClientsToday(readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeTypedList(topHotspotClientsToday, 1);
            return true;
        }

        private boolean onTransact$getTopHotspotClientsTodayAsString$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            String topHotspotClientsTodayAsString = getTopHotspotClientsTodayAsString(readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeString(topHotspotClientsTodayAsString);
            return true;
        }

        private boolean onTransact$getTotalAndTop3ClientsDataUsageBetweenGivenDates$(Parcel parcel, Parcel parcel2) throws RemoteException {
            long readLong = parcel.readLong();
            long readLong2 = parcel.readLong();
            parcel.enforceNoDataAvail();
            List<String> totalAndTop3ClientsDataUsageBetweenGivenDates = getTotalAndTop3ClientsDataUsageBetweenGivenDates(readLong, readLong2);
            parcel2.writeNoException();
            parcel2.writeStringList(totalAndTop3ClientsDataUsageBetweenGivenDates);
            return true;
        }

        private boolean onTransact$registerClientListDataUsageCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            ISemWifiApClientListUpdateCallback asInterface = ISemWifiApClientListUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            registerClientListDataUsageCallback(readStrongBinder, asInterface, readInt, readInt2, readInt3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerClientDataUsageCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            ISemWifiApClientUpdateCallback asInterface = ISemWifiApClientUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            registerClientDataUsageCallback(readStrongBinder, asInterface, readInt, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$logWifiAp$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            parcel.enforceNoDataAvail();
            logWifiAp(readString, readString2, readString3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$reportBigData$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            reportBigData(readString, readString2);
            return true;
        }

        private boolean onTransact$addOrUpdateWifiControlHistory$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            addOrUpdateWifiControlHistory(readString, readBoolean);
            return true;
        }

        private boolean onTransact$allowAutojoinPasspoint$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            allowAutojoinPasspoint(readString, readBoolean);
            return true;
        }

        private boolean onTransact$reportIssue$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            reportIssue(readInt, bundle);
            return true;
        }

        private boolean onTransact$registerPasswordCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            ISemSharedPasswordCallback asInterface = ISemSharedPasswordCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerPasswordCallback(readString, asInterface);
            return true;
        }

        private boolean onTransact$setUserConfirmForSharingPassword$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setUserConfirmForSharingPassword(readBoolean, readString);
            return true;
        }

        private boolean onTransact$setTestSettings$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            setTestSettings(readInt, bundle);
            return true;
        }

        private boolean onTransact$setAllowWifiScan$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setAllowWifiScan(readBoolean, readString);
            return true;
        }

        private boolean onTransact$setEasySetupScanSettings$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            SemEasySetupWifiScanSettings semEasySetupWifiScanSettings = (SemEasySetupWifiScanSettings) parcel.readTypedObject(SemEasySetupWifiScanSettings.CREATOR);
            parcel.enforceNoDataAvail();
            setEasySetupScanSettings(readString, semEasySetupWifiScanSettings);
            return true;
        }

        private boolean onTransact$setKeepConnection$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setKeepConnection(readBoolean, readBoolean2);
            return true;
        }

        private boolean onTransact$setConnectionAttemptInfo$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setConnectionAttemptInfo(readInt, readBoolean, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$restoreIWCSettingsValue$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            restoreIWCSettingsValue(readInt, readInt2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOptimizerForceControlMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean optimizerForceControlMode = setOptimizerForceControlMode(readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(optimizerForceControlMode);
            return true;
        }

        private boolean onTransact$setTrafficPatternTestSettings$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            setTrafficPatternTestSettings(bundle);
            return true;
        }

        private boolean onTransact$setWifiUwbCoexEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int wifiUwbCoexEnabled = setWifiUwbCoexEnabled(readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(wifiUwbCoexEnabled);
            return true;
        }

        private boolean onTransact$setLatencyCritical$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean latencyCritical = setLatencyCritical(readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(latencyCritical);
            return true;
        }

        private boolean onTransact$setPktlogFilter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean pktlogFilter = setPktlogFilter(readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(pktlogFilter);
            return true;
        }

        private boolean onTransact$removePktlogFilter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean removePktlogFilter = removePktlogFilter(readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(removePktlogFilter);
            return true;
        }

        private boolean onTransact$getRssi$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            int rssi = getRssi(readString);
            parcel2.writeNoException();
            parcel2.writeInt(rssi);
            return true;
        }

        private boolean onTransact$checkAppForWiFiOffloading$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            checkAppForWiFiOffloading(readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTCRule$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setTCRule(readBoolean, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$externalTwtInterface$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            externalTwtInterface(readInt, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$resetCallbackCondition$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            resetCallbackCondition(readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSamsungMloCtrl$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSamsungMloCtrl(readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSamsungIwhCtrl$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSamsungIwhCtrl(readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTestMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setTestMode(readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$linkQosQuery$(Parcel parcel, Parcel parcel2) throws RemoteException {
            long readLong = parcel.readLong();
            long readLong2 = parcel.readLong();
            long readLong3 = parcel.readLong();
            int readInt = parcel.readInt();
            long readLong4 = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean linkQosQuery = linkQosQuery(readLong, readLong2, readLong3, readInt, readLong4);
            parcel2.writeNoException();
            parcel2.writeBoolean(linkQosQuery);
            return true;
        }

        private boolean onTransact$setWifiAiServiceState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            int[] createIntArray = parcel.createIntArray();
            int[] createIntArray2 = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            setWifiAiServiceState(readBoolean, createIntArray, createIntArray2);
            return true;
        }

        private boolean onTransact$setWifiAiServiceNsdResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int[] createIntArray = parcel.createIntArray();
            int[] createIntArray2 = parcel.createIntArray();
            int[] createIntArray3 = parcel.createIntArray();
            String[] createStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            setWifiAiServiceNsdResult(createIntArray, createIntArray2, createIntArray3, createStringArray);
            return true;
        }

        private boolean onTransact$setWifiAiIwhTrainingResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setWifiAiIwhTrainingResult(readString, readInt, readInt2, readInt3);
            return true;
        }

        private boolean onTransact$setWifiAiIwhInferenceResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean[] createBooleanArray = parcel.createBooleanArray();
            parcel.enforceNoDataAvail();
            setWifiAiIwhInferenceResult(createBooleanArray);
            return true;
        }

        private boolean onTransact$setIlaTrainingResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            double readDouble = parcel.readDouble();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setIlaTrainingResult(readDouble, readString);
            return true;
        }

        private boolean onTransact$setWifiAiIccTrainingResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setWifiAiIccTrainingResult(readString, readInt, readInt2, readInt3);
            return true;
        }

        private boolean onTransact$setWifiAiIccInferenceResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean[] createBooleanArray = parcel.createBooleanArray();
            parcel.enforceNoDataAvail();
            setWifiAiIccInferenceResult(createBooleanArray);
            return true;
        }

        private boolean onTransact$setWifiAiIccInferenceResult2$(Parcel parcel, Parcel parcel2) throws RemoteException {
            float[] createFloatArray = parcel.createFloatArray();
            parcel.enforceNoDataAvail();
            setWifiAiIccInferenceResult2(createFloatArray);
            return true;
        }

        private boolean onTransact$setWifiAiIccInferenceConfidence$(Parcel parcel, Parcel parcel2) throws RemoteException {
            float[] createFloatArray = parcel.createFloatArray();
            parcel.enforceNoDataAvail();
            setWifiAiIccInferenceConfidence(createFloatArray);
            return true;
        }

        private boolean onTransact$getTcpMonitorSocketForegroundHistory$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            String tcpMonitorSocketForegroundHistory = getTcpMonitorSocketForegroundHistory(readInt);
            parcel2.writeNoException();
            parcel2.writeString(tcpMonitorSocketForegroundHistory);
            return true;
        }

        private boolean onTransact$getTcpMonitorAllSocketHistory$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            String tcpMonitorAllSocketHistory = getTcpMonitorAllSocketHistory(readInt);
            parcel2.writeNoException();
            parcel2.writeString(tcpMonitorAllSocketHistory);
            return true;
        }

        private boolean onTransact$getTcpMonitorDnsHistory$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            String tcpMonitorDnsHistory = getTcpMonitorDnsHistory(readInt);
            parcel2.writeNoException();
            parcel2.writeString(tcpMonitorDnsHistory);
            return true;
        }

        private boolean onTransact$getWifiUsabilityStatsEntry$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            String wifiUsabilityStatsEntry = getWifiUsabilityStatsEntry(readInt);
            parcel2.writeNoException();
            parcel2.writeString(wifiUsabilityStatsEntry);
            return true;
        }

        private boolean onTransact$setTdlsEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean tdlsEnabled = setTdlsEnabled(readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(tdlsEnabled);
            return true;
        }

        private boolean onTransact$setTasPolicy$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            Map tasPolicy = setTasPolicy(readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeMap(tasPolicy);
            return true;
        }

        private boolean onTransact$registerTasPolicyChangedListener$(Parcel parcel, Parcel parcel2) throws RemoteException {
            SemTasPolicyListener asInterface = SemTasPolicyListener.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerTasPolicyChangedListener(asInterface);
            return true;
        }

        private boolean onTransact$unregisterTasPolicyChangedListener$(Parcel parcel, Parcel parcel2) throws RemoteException {
            SemTasPolicyListener asInterface = SemTasPolicyListener.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterTasPolicyChangedListener(asInterface);
            return true;
        }

        private boolean onTransact$enableTxPowerLogging$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            enableTxPowerLogging(readBoolean, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setMhsAiServiceState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            int[] createIntArray = parcel.createIntArray();
            int[] createIntArray2 = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            setMhsAiServiceState(readBoolean, createIntArray, createIntArray2);
            return true;
        }

        private boolean onTransact$setMhsAiServiceNsdResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int[] createIntArray = parcel.createIntArray();
            String[] createStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            setMhsAiServiceNsdResult(createIntArray, createStringArray);
            return true;
        }

        private boolean onTransact$startCapture$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int startCapture = startCapture(readInt);
            parcel2.writeNoException();
            parcel2.writeInt(startCapture);
            return true;
        }

        private boolean onTransact$setMcfMultiControlMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMcfMultiControlMode(readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerAbTestConfigUpdateObserver$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ISemAbTestConfigurationUpdateObserver asInterface = ISemAbTestConfigurationUpdateObserver.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            registerAbTestConfigUpdateObserver(asInterface, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterAbTestConfigUpdateObserver$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ISemAbTestConfigurationUpdateObserver asInterface = ISemAbTestConfigurationUpdateObserver.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterAbTestConfigUpdateObserver(asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$reportAbTestResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            parcel.enforceNoDataAvail();
            reportAbTestResult(readString, readString2, readString3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getAbTestConfiguredModule$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            SemAbTestConfiguration abTestConfiguredModule = getAbTestConfiguredModule(readString);
            parcel2.writeNoException();
            parcel2.writeTypedObject(abTestConfiguredModule, 1);
            return true;
        }

        private boolean onTransact$sendReassociationFrequencyRequestFrame$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean sendReassociationFrequencyRequestFrame = sendReassociationFrequencyRequestFrame(readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(sendReassociationFrequencyRequestFrame);
            return true;
        }

        private boolean onTransact$setInsInferenceResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            float readFloat = parcel.readFloat();
            float readFloat2 = parcel.readFloat();
            float readFloat3 = parcel.readFloat();
            float readFloat4 = parcel.readFloat();
            String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setInsInferenceResult(readInt, readFloat, readFloat2, readFloat3, readFloat4, readString);
            return true;
        }

        private boolean onTransact$setLastSelectedNetworkIdForSilentRoaming$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setLastSelectedNetworkIdForSilentRoaming(readInt);
            parcel2.writeNoException();
            return true;
        }
    }
}
