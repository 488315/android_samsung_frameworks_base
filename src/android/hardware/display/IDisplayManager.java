package android.hardware.display;

import android.Manifest;
import android.app.ActivityThread;
import android.content.pm.ParceledListSlice;
import android.graphics.Point;
import android.hardware.OverlayProperties;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IHbmBrightnessCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.media.projection.IMediaProjection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.Surface;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IDisplayManager extends IInterface {

    public static class Default implements IDisplayManager {
        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean areUserDisabledHdrTypesAllowed() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void connectWifiDisplay(String str) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void connectWifiDisplayWithConfig(SemWifiDisplayConfig semWifiDisplayConfig, IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int convertToBrightness(float f) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void disableConnectedDisplay(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void disconnectWifiDisplay() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void enableConnectedDisplay(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void fitToActiveDisplay(boolean z) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void forgetWifiDisplay(String str) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public float getAdaptiveBrightness(int i, float f) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getBackupBrightnessConfiguration(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public float getBrightness(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public ParceledListSlice getBrightnessEvents(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessInfo getBrightnessInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public float getDefaultDozeBrightness(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getDeviceMaxVolume() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getDeviceMinVolume() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public DisplayDecorationSupport getDisplayDecorationSupport(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getDisplayIds(boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public DisplayInfo getDisplayInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public DisplayTopology getDisplayTopology() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public SemDlnaDevice getDlnaDevice() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public float[] getDozeBrightnessSensorValueToBrightness(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public HdrConversionMode getHdrConversionMode() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public HdrConversionMode getHdrConversionModeSetting() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public float getHighestHdrSdrRatio(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public Curve getMinimumBrightnessCurve() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public OverlayProperties getOverlaySupport() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getPreferredWideGamutColorSpaceId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public String getPresentationOwner(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public long getPrimaryPhysicalDisplayId() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getRefreshRateSwitchingType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getScreenSharingStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public Point getStableDisplaySize() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getSupportedHdrOutputTypes() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public Display.Mode getSystemPreferredDisplayMode(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getUserDisabledHdrTypes() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public Display.Mode getUserPreferredDisplayMode(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isDeviceVolumeMuted() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isFitToActiveDisplay() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isMinimalPostProcessingRequested(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isUidPresentOnDisplay(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isWifiDisplayWithPinSupported(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void overrideHdrTypes(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void pauseWifiDisplay() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerCallbackWithEventMask(IDisplayManagerCallback iDisplayManagerCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerHbmBrightnessCallback(IHbmBrightnessCallback iHbmBrightnessCallback) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void renameWifiDisplay(String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void requestColorMode(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void requestDisplayModes(IBinder iBinder, int i, int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean requestDisplayPower(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void resetBrightnessConfigurationForUser(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void resumeWifiDisplay() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setAreUserDisabledHdrTypesAllowed(boolean z) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str, int i2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightness(int i, float f) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForDisplayWithStats(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDeviceVolume(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDeviceVolumeMuted(boolean z) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayIdToMirror(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayStateOverride(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayStateOverrideWithDisplayId(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayTopology(DisplayTopology displayTopology) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setEnableConnectedDisplay(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setHdrConversionMode(HdrConversionMode hdrConversionMode) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setRefreshRateSwitchingType(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setScreenSharingStatus(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setShouldAlwaysRespectAppRequestedMode(boolean z) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryBrightness(int i, float f) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setUserDisabledHdrTypes(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setUserPreferredDisplayMode(int i, Display.Mode mode) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVirtualDisplayRotation(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVolumeKeyEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setWifiDisplayParam(String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayChannelScan(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayChannelScanAndInterval(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayScan() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void stopWifiDisplayScan() throws RemoteException {
        }
    }

    IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) throws RemoteException;

    IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) throws RemoteException;

    IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) throws RemoteException;

    IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) throws RemoteException;

    boolean areUserDisabledHdrTypesAllowed() throws RemoteException;

    void connectWifiDisplay(String str) throws RemoteException;

    void connectWifiDisplayWithConfig(SemWifiDisplayConfig semWifiDisplayConfig, IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) throws RemoteException;

    int convertToBrightness(float f) throws RemoteException;

    int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str) throws RemoteException;

    void disableConnectedDisplay(int i) throws RemoteException;

    void disconnectWifiDisplay() throws RemoteException;

    void enableConnectedDisplay(int i) throws RemoteException;

    void fitToActiveDisplay(boolean z) throws RemoteException;

    void forgetWifiDisplay(String str) throws RemoteException;

    float getAdaptiveBrightness(int i, float f) throws RemoteException;

    ParceledListSlice getAmbientBrightnessStats() throws RemoteException;

    BrightnessConfiguration getBackupBrightnessConfiguration(int i, int i2) throws RemoteException;

    float getBrightness(int i) throws RemoteException;

    BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) throws RemoteException;

    BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws RemoteException;

    ParceledListSlice getBrightnessEvents(String str) throws RemoteException;

    BrightnessInfo getBrightnessInfo(int i) throws RemoteException;

    BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException;

    float getDefaultDozeBrightness(int i) throws RemoteException;

    int getDeviceMaxVolume() throws RemoteException;

    int getDeviceMinVolume() throws RemoteException;

    DisplayDecorationSupport getDisplayDecorationSupport(int i) throws RemoteException;

    int[] getDisplayIds(boolean z) throws RemoteException;

    DisplayInfo getDisplayInfo(int i) throws RemoteException;

    DisplayTopology getDisplayTopology() throws RemoteException;

    SemDlnaDevice getDlnaDevice() throws RemoteException;

    float[] getDozeBrightnessSensorValueToBrightness(int i) throws RemoteException;

    HdrConversionMode getHdrConversionMode() throws RemoteException;

    HdrConversionMode getHdrConversionModeSetting() throws RemoteException;

    float getHighestHdrSdrRatio(int i) throws RemoteException;

    Curve getMinimumBrightnessCurve() throws RemoteException;

    OverlayProperties getOverlaySupport() throws RemoteException;

    int getPreferredWideGamutColorSpaceId() throws RemoteException;

    String getPresentationOwner(int i) throws RemoteException;

    long getPrimaryPhysicalDisplayId() throws RemoteException;

    int getRefreshRateSwitchingType() throws RemoteException;

    int getScreenSharingStatus() throws RemoteException;

    Point getStableDisplaySize() throws RemoteException;

    int[] getSupportedHdrOutputTypes() throws RemoteException;

    Display.Mode getSystemPreferredDisplayMode(int i) throws RemoteException;

    int[] getUserDisabledHdrTypes() throws RemoteException;

    Display.Mode getUserPreferredDisplayMode(int i) throws RemoteException;

    WifiDisplayStatus getWifiDisplayStatus() throws RemoteException;

    boolean isDeviceVolumeMuted() throws RemoteException;

    boolean isFitToActiveDisplay() throws RemoteException;

    boolean isMinimalPostProcessingRequested(int i) throws RemoteException;

    boolean isUidPresentOnDisplay(int i, int i2) throws RemoteException;

    boolean isWifiDisplayWithPinSupported(String str) throws RemoteException;

    void overrideHdrTypes(int i, int[] iArr) throws RemoteException;

    void pauseWifiDisplay() throws RemoteException;

    void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException;

    void registerCallbackWithEventMask(IDisplayManagerCallback iDisplayManagerCallback, long j) throws RemoteException;

    void registerHbmBrightnessCallback(IHbmBrightnessCallback iHbmBrightnessCallback) throws RemoteException;

    void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException;

    void renameWifiDisplay(String str, String str2) throws RemoteException;

    void requestColorMode(int i, int i2) throws RemoteException;

    void requestDisplayModes(IBinder iBinder, int i, int[] iArr) throws RemoteException;

    boolean requestDisplayPower(int i, int i2) throws RemoteException;

    boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) throws RemoteException;

    boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) throws RemoteException;

    void resetBrightnessConfigurationForUser(int i, String str) throws RemoteException;

    void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws RemoteException;

    void resumeWifiDisplay() throws RemoteException;

    void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException;

    void setAreUserDisabledHdrTypesAllowed(boolean z) throws RemoteException;

    void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str, int i2) throws RemoteException;

    void setBrightness(int i, float f) throws RemoteException;

    void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) throws RemoteException;

    void setBrightnessConfigurationForDisplayWithStats(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2, List<String> list, List<String> list2, List<String> list3) throws RemoteException;

    void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException;

    void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException;

    void setDeviceVolume(int i) throws RemoteException;

    void setDeviceVolumeMuted(boolean z) throws RemoteException;

    void setDisplayIdToMirror(IBinder iBinder, int i) throws RemoteException;

    void setDisplayStateOverride(IBinder iBinder, int i, int i2) throws RemoteException;

    void setDisplayStateOverrideWithDisplayId(IBinder iBinder, int i, int i2, int i3) throws RemoteException;

    void setDisplayTopology(DisplayTopology displayTopology) throws RemoteException;

    void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) throws RemoteException;

    void setEnableConnectedDisplay(int i, boolean z) throws RemoteException;

    void setHdrConversionMode(HdrConversionMode hdrConversionMode) throws RemoteException;

    void setRefreshRateSwitchingType(int i) throws RemoteException;

    void setScreenSharingStatus(int i) throws RemoteException;

    void setShouldAlwaysRespectAppRequestedMode(boolean z) throws RemoteException;

    void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException;

    void setTemporaryBrightness(int i, float f) throws RemoteException;

    void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) throws RemoteException;

    void setUserDisabledHdrTypes(int[] iArr) throws RemoteException;

    void setUserPreferredDisplayMode(int i, Display.Mode mode) throws RemoteException;

    void setVirtualDisplayRotation(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException;

    void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException;

    void setVolumeKeyEvent(int i) throws RemoteException;

    void setWifiDisplayParam(String str, String str2) throws RemoteException;

    boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException;

    void startWifiDisplayChannelScan(int i) throws RemoteException;

    void startWifiDisplayChannelScanAndInterval(int i, int i2) throws RemoteException;

    void startWifiDisplayScan() throws RemoteException;

    void stopWifiDisplayScan() throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayManager {
        public static final String DESCRIPTOR = "android.hardware.display.IDisplayManager";
        static final int TRANSACTION_acquireLowRefreshRateToken = 98;
        static final int TRANSACTION_acquirePassiveModeToken = 97;
        static final int TRANSACTION_acquireRefreshRateMaxLimitToken = 99;
        static final int TRANSACTION_acquireRefreshRateMinLimitToken = 100;
        static final int TRANSACTION_areUserDisabledHdrTypesAllowed = 17;
        static final int TRANSACTION_connectWifiDisplay = 8;
        static final int TRANSACTION_connectWifiDisplayWithConfig = 27;
        static final int TRANSACTION_convertToBrightness = 83;
        static final int TRANSACTION_createVirtualDisplay = 21;
        static final int TRANSACTION_disableConnectedDisplay = 89;
        static final int TRANSACTION_disconnectWifiDisplay = 9;
        static final int TRANSACTION_enableConnectedDisplay = 88;
        static final int TRANSACTION_fitToActiveDisplay = 41;
        static final int TRANSACTION_forgetWifiDisplay = 11;
        static final int TRANSACTION_getAdaptiveBrightness = 86;
        static final int TRANSACTION_getAmbientBrightnessStats = 49;
        static final int TRANSACTION_getBackupBrightnessConfiguration = 82;
        static final int TRANSACTION_getBrightness = 58;
        static final int TRANSACTION_getBrightnessConfigurationForDisplay = 52;
        static final int TRANSACTION_getBrightnessConfigurationForUser = 53;
        static final int TRANSACTION_getBrightnessEvents = 48;
        static final int TRANSACTION_getBrightnessInfo = 61;
        static final int TRANSACTION_getDefaultBrightnessConfiguration = 54;
        static final int TRANSACTION_getDefaultDozeBrightness = 94;
        static final int TRANSACTION_getDeviceMaxVolume = 39;
        static final int TRANSACTION_getDeviceMinVolume = 37;
        static final int TRANSACTION_getDisplayDecorationSupport = 74;
        static final int TRANSACTION_getDisplayIds = 2;
        static final int TRANSACTION_getDisplayInfo = 1;
        static final int TRANSACTION_getDisplayTopology = 95;
        static final int TRANSACTION_getDlnaDevice = 33;
        static final int TRANSACTION_getDozeBrightnessSensorValueToBrightness = 93;
        static final int TRANSACTION_getHdrConversionMode = 68;
        static final int TRANSACTION_getHdrConversionModeSetting = 67;
        static final int TRANSACTION_getHighestHdrSdrRatio = 92;
        static final int TRANSACTION_getMinimumBrightnessCurve = 60;
        static final int TRANSACTION_getOverlaySupport = 76;
        static final int TRANSACTION_getPreferredWideGamutColorSpaceId = 62;
        static final int TRANSACTION_getPresentationOwner = 43;
        static final int TRANSACTION_getPrimaryPhysicalDisplayId = 101;
        static final int TRANSACTION_getRefreshRateSwitchingType = 73;
        static final int TRANSACTION_getScreenSharingStatus = 30;
        static final int TRANSACTION_getStableDisplaySize = 47;
        static final int TRANSACTION_getSupportedHdrOutputTypes = 69;
        static final int TRANSACTION_getSystemPreferredDisplayMode = 65;
        static final int TRANSACTION_getUserDisabledHdrTypes = 18;
        static final int TRANSACTION_getUserPreferredDisplayMode = 64;
        static final int TRANSACTION_getWifiDisplayStatus = 14;
        static final int TRANSACTION_isDeviceVolumeMuted = 38;
        static final int TRANSACTION_isFitToActiveDisplay = 42;
        static final int TRANSACTION_isMinimalPostProcessingRequested = 55;
        static final int TRANSACTION_isUidPresentOnDisplay = 3;
        static final int TRANSACTION_isWifiDisplayWithPinSupported = 40;
        static final int TRANSACTION_overrideHdrTypes = 19;
        static final int TRANSACTION_pauseWifiDisplay = 12;
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_registerCallbackWithEventMask = 5;
        static final int TRANSACTION_registerHbmBrightnessCallback = 102;
        static final int TRANSACTION_releaseVirtualDisplay = 24;
        static final int TRANSACTION_renameWifiDisplay = 10;
        static final int TRANSACTION_requestColorMode = 20;
        static final int TRANSACTION_requestDisplayModes = 91;
        static final int TRANSACTION_requestDisplayPower = 90;
        static final int TRANSACTION_requestSetWifiDisplayParameters = 45;
        static final int TRANSACTION_requestWifiDisplayParameter = 46;
        static final int TRANSACTION_resetBrightnessConfigurationForUser = 79;
        static final int TRANSACTION_resizeVirtualDisplay = 22;
        static final int TRANSACTION_resumeWifiDisplay = 13;
        static final int TRANSACTION_rotateVirtualDisplay = 26;
        static final int TRANSACTION_setAreUserDisabledHdrTypesAllowed = 16;
        static final int TRANSACTION_setBackupBrightnessConfiguration = 81;
        static final int TRANSACTION_setBrightness = 57;
        static final int TRANSACTION_setBrightnessConfigurationForDisplay = 51;
        static final int TRANSACTION_setBrightnessConfigurationForDisplayWithStats = 78;
        static final int TRANSACTION_setBrightnessConfigurationForUser = 50;
        static final int TRANSACTION_setBrightnessConfigurationForUserWithStats = 77;
        static final int TRANSACTION_setDeviceVolume = 34;
        static final int TRANSACTION_setDeviceVolumeMuted = 35;
        static final int TRANSACTION_setDisplayIdToMirror = 75;
        static final int TRANSACTION_setDisplayStateOverride = 84;
        static final int TRANSACTION_setDisplayStateOverrideWithDisplayId = 85;
        static final int TRANSACTION_setDisplayTopology = 96;
        static final int TRANSACTION_setDlnaDevice = 32;
        static final int TRANSACTION_setEnableConnectedDisplay = 87;
        static final int TRANSACTION_setHdrConversionMode = 66;
        static final int TRANSACTION_setRefreshRateSwitchingType = 72;
        static final int TRANSACTION_setScreenSharingStatus = 31;
        static final int TRANSACTION_setShouldAlwaysRespectAppRequestedMode = 70;
        static final int TRANSACTION_setTemporaryAutoBrightnessAdjustment = 59;
        static final int TRANSACTION_setTemporaryBrightness = 56;
        static final int TRANSACTION_setTemporaryBrightnessForSlowChange = 80;
        static final int TRANSACTION_setUserDisabledHdrTypes = 15;
        static final int TRANSACTION_setUserPreferredDisplayMode = 63;
        static final int TRANSACTION_setVirtualDisplayRotation = 25;
        static final int TRANSACTION_setVirtualDisplaySurface = 23;
        static final int TRANSACTION_setVolumeKeyEvent = 36;
        static final int TRANSACTION_setWifiDisplayParam = 44;
        static final int TRANSACTION_shouldAlwaysRespectAppRequestedMode = 71;
        static final int TRANSACTION_startWifiDisplayChannelScan = 28;
        static final int TRANSACTION_startWifiDisplayChannelScanAndInterval = 29;
        static final int TRANSACTION_startWifiDisplayScan = 6;
        static final int TRANSACTION_stopWifiDisplayScan = 7;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 101;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IDisplayManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayManager)) {
                return (IDisplayManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDisplayInfo";
                case 2:
                    return "getDisplayIds";
                case 3:
                    return "isUidPresentOnDisplay";
                case 4:
                    return "registerCallback";
                case 5:
                    return "registerCallbackWithEventMask";
                case 6:
                    return "startWifiDisplayScan";
                case 7:
                    return "stopWifiDisplayScan";
                case 8:
                    return "connectWifiDisplay";
                case 9:
                    return "disconnectWifiDisplay";
                case 10:
                    return "renameWifiDisplay";
                case 11:
                    return "forgetWifiDisplay";
                case 12:
                    return "pauseWifiDisplay";
                case 13:
                    return "resumeWifiDisplay";
                case 14:
                    return "getWifiDisplayStatus";
                case 15:
                    return "setUserDisabledHdrTypes";
                case 16:
                    return "setAreUserDisabledHdrTypesAllowed";
                case 17:
                    return "areUserDisabledHdrTypesAllowed";
                case 18:
                    return "getUserDisabledHdrTypes";
                case 19:
                    return "overrideHdrTypes";
                case 20:
                    return "requestColorMode";
                case 21:
                    return "createVirtualDisplay";
                case 22:
                    return "resizeVirtualDisplay";
                case 23:
                    return "setVirtualDisplaySurface";
                case 24:
                    return "releaseVirtualDisplay";
                case 25:
                    return "setVirtualDisplayRotation";
                case 26:
                    return "rotateVirtualDisplay";
                case 27:
                    return "connectWifiDisplayWithConfig";
                case 28:
                    return "startWifiDisplayChannelScan";
                case 29:
                    return "startWifiDisplayChannelScanAndInterval";
                case 30:
                    return "getScreenSharingStatus";
                case 31:
                    return "setScreenSharingStatus";
                case 32:
                    return "setDlnaDevice";
                case 33:
                    return "getDlnaDevice";
                case 34:
                    return "setDeviceVolume";
                case 35:
                    return "setDeviceVolumeMuted";
                case 36:
                    return "setVolumeKeyEvent";
                case 37:
                    return "getDeviceMinVolume";
                case 38:
                    return "isDeviceVolumeMuted";
                case 39:
                    return "getDeviceMaxVolume";
                case 40:
                    return "isWifiDisplayWithPinSupported";
                case 41:
                    return "fitToActiveDisplay";
                case 42:
                    return "isFitToActiveDisplay";
                case 43:
                    return "getPresentationOwner";
                case 44:
                    return "setWifiDisplayParam";
                case 45:
                    return "requestSetWifiDisplayParameters";
                case 46:
                    return "requestWifiDisplayParameter";
                case 47:
                    return "getStableDisplaySize";
                case 48:
                    return "getBrightnessEvents";
                case 49:
                    return "getAmbientBrightnessStats";
                case 50:
                    return "setBrightnessConfigurationForUser";
                case 51:
                    return "setBrightnessConfigurationForDisplay";
                case 52:
                    return "getBrightnessConfigurationForDisplay";
                case 53:
                    return "getBrightnessConfigurationForUser";
                case 54:
                    return "getDefaultBrightnessConfiguration";
                case 55:
                    return "isMinimalPostProcessingRequested";
                case 56:
                    return "setTemporaryBrightness";
                case 57:
                    return "setBrightness";
                case 58:
                    return "getBrightness";
                case 59:
                    return "setTemporaryAutoBrightnessAdjustment";
                case 60:
                    return "getMinimumBrightnessCurve";
                case 61:
                    return "getBrightnessInfo";
                case 62:
                    return "getPreferredWideGamutColorSpaceId";
                case 63:
                    return "setUserPreferredDisplayMode";
                case 64:
                    return "getUserPreferredDisplayMode";
                case 65:
                    return "getSystemPreferredDisplayMode";
                case 66:
                    return "setHdrConversionMode";
                case 67:
                    return "getHdrConversionModeSetting";
                case 68:
                    return "getHdrConversionMode";
                case 69:
                    return "getSupportedHdrOutputTypes";
                case 70:
                    return "setShouldAlwaysRespectAppRequestedMode";
                case 71:
                    return "shouldAlwaysRespectAppRequestedMode";
                case 72:
                    return "setRefreshRateSwitchingType";
                case 73:
                    return "getRefreshRateSwitchingType";
                case 74:
                    return "getDisplayDecorationSupport";
                case 75:
                    return "setDisplayIdToMirror";
                case 76:
                    return "getOverlaySupport";
                case 77:
                    return "setBrightnessConfigurationForUserWithStats";
                case 78:
                    return "setBrightnessConfigurationForDisplayWithStats";
                case 79:
                    return "resetBrightnessConfigurationForUser";
                case 80:
                    return "setTemporaryBrightnessForSlowChange";
                case 81:
                    return "setBackupBrightnessConfiguration";
                case 82:
                    return "getBackupBrightnessConfiguration";
                case 83:
                    return "convertToBrightness";
                case 84:
                    return "setDisplayStateOverride";
                case 85:
                    return "setDisplayStateOverrideWithDisplayId";
                case 86:
                    return "getAdaptiveBrightness";
                case 87:
                    return "setEnableConnectedDisplay";
                case 88:
                    return "enableConnectedDisplay";
                case 89:
                    return "disableConnectedDisplay";
                case 90:
                    return "requestDisplayPower";
                case 91:
                    return "requestDisplayModes";
                case 92:
                    return "getHighestHdrSdrRatio";
                case 93:
                    return "getDozeBrightnessSensorValueToBrightness";
                case 94:
                    return "getDefaultDozeBrightness";
                case 95:
                    return "getDisplayTopology";
                case 96:
                    return "setDisplayTopology";
                case 97:
                    return "acquirePassiveModeToken";
                case 98:
                    return "acquireLowRefreshRateToken";
                case 99:
                    return "acquireRefreshRateMaxLimitToken";
                case 100:
                    return "acquireRefreshRateMinLimitToken";
                case 101:
                    return "getPrimaryPhysicalDisplayId";
                case 102:
                    return "registerHbmBrightnessCallback";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DisplayInfo displayInfo = getDisplayInfo(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayInfo, 1);
                    return true;
                case 2:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] displayIds = getDisplayIds(z);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIds);
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidPresentOnDisplay = isUidPresentOnDisplay(i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidPresentOnDisplay);
                    return true;
                case 4:
                    IDisplayManagerCallback iDisplayManagerCallbackAsInterface = IDisplayManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(iDisplayManagerCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IDisplayManagerCallback iDisplayManagerCallbackAsInterface2 = IDisplayManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    registerCallbackWithEventMask(iDisplayManagerCallbackAsInterface2, j);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    startWifiDisplayScan();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    stopWifiDisplayScan();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    connectWifiDisplay(string);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    disconnectWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    renameWifiDisplay(string2, string3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetWifiDisplay(string4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    pauseWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    resumeWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    WifiDisplayStatus wifiDisplayStatus = getWifiDisplayStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiDisplayStatus, 1);
                    return true;
                case 15:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setUserDisabledHdrTypes(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAreUserDisabledHdrTypesAllowed(z2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean zAreUserDisabledHdrTypesAllowed = areUserDisabledHdrTypesAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreUserDisabledHdrTypesAllowed);
                    return true;
                case 18:
                    int[] userDisabledHdrTypes = getUserDisabledHdrTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(userDisabledHdrTypes);
                    return true;
                case 19:
                    int i6 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    overrideHdrTypes(i6, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestColorMode(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    VirtualDisplayConfig virtualDisplayConfig = (VirtualDisplayConfig) parcel.readTypedObject(VirtualDisplayConfig.CREATOR);
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    IMediaProjection iMediaProjectionAsInterface = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCreateVirtualDisplay = createVirtualDisplay(virtualDisplayConfig, iVirtualDisplayCallbackAsInterface, iMediaProjectionAsInterface, string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateVirtualDisplay);
                    return true;
                case 22:
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface2 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeVirtualDisplay(iVirtualDisplayCallbackAsInterface2, i9, i10, i11);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface3 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVirtualDisplaySurface(iVirtualDisplayCallbackAsInterface3, surface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface4 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    releaseVirtualDisplay(iVirtualDisplayCallbackAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface5 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVirtualDisplayRotation(iVirtualDisplayCallbackAsInterface5, i12);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface6 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rotateVirtualDisplay(iVirtualDisplayCallbackAsInterface6, i13);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    SemWifiDisplayConfig semWifiDisplayConfig = (SemWifiDisplayConfig) parcel.readTypedObject(SemWifiDisplayConfig.CREATOR);
                    IWifiDisplayConnectionCallback iWifiDisplayConnectionCallbackAsInterface = IWifiDisplayConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectWifiDisplayWithConfig(semWifiDisplayConfig, iWifiDisplayConnectionCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startWifiDisplayChannelScan(i14);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startWifiDisplayChannelScanAndInterval(i15, i16);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int screenSharingStatus = getScreenSharingStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(screenSharingStatus);
                    return true;
                case 31:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenSharingStatus(i17);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    SemDlnaDevice semDlnaDevice = (SemDlnaDevice) parcel.readTypedObject(SemDlnaDevice.CREATOR);
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setDlnaDevice(semDlnaDevice, strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    SemDlnaDevice dlnaDevice = getDlnaDevice();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dlnaDevice, 1);
                    return true;
                case 34:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceVolume(i18);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceVolumeMuted(z3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolumeKeyEvent(i19);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int deviceMinVolume = getDeviceMinVolume();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceMinVolume);
                    return true;
                case 38:
                    boolean zIsDeviceVolumeMuted = isDeviceVolumeMuted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceVolumeMuted);
                    return true;
                case 39:
                    int deviceMaxVolume = getDeviceMaxVolume();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceMaxVolume);
                    return true;
                case 40:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiDisplayWithPinSupported = isWifiDisplayWithPinSupported(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiDisplayWithPinSupported);
                    return true;
                case 41:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    fitToActiveDisplay(z4);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean zIsFitToActiveDisplay = isFitToActiveDisplay();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFitToActiveDisplay);
                    return true;
                case 43:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String presentationOwner = getPresentationOwner(i20);
                    parcel2.writeNoException();
                    parcel2.writeString(presentationOwner);
                    return true;
                case 44:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setWifiDisplayParam(string7, string8);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestSetWifiDisplayParameters = requestSetWifiDisplayParameters(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestSetWifiDisplayParameters);
                    return true;
                case 46:
                    String string9 = parcel.readString();
                    SemWifiDisplayParameter semWifiDisplayParameter = (SemWifiDisplayParameter) parcel.readTypedObject(SemWifiDisplayParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestWifiDisplayParameter = requestWifiDisplayParameter(string9, semWifiDisplayParameter);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestWifiDisplayParameter);
                    return true;
                case 47:
                    Point stableDisplaySize = getStableDisplaySize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stableDisplaySize, 1);
                    return true;
                case 48:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice brightnessEvents = getBrightnessEvents(string10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessEvents, 1);
                    return true;
                case 49:
                    ParceledListSlice ambientBrightnessStats = getAmbientBrightnessStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ambientBrightnessStats, 1);
                    return true;
                case 50:
                    BrightnessConfiguration brightnessConfiguration = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    int i21 = parcel.readInt();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForUser(brightnessConfiguration, i21, string11);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    BrightnessConfiguration brightnessConfiguration2 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    String string12 = parcel.readString();
                    int i22 = parcel.readInt();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForDisplay(brightnessConfiguration2, string12, i22, string13);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    String string14 = parcel.readString();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessConfiguration brightnessConfigurationForDisplay = getBrightnessConfigurationForDisplay(string14, i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessConfigurationForDisplay, 1);
                    return true;
                case 53:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessConfiguration brightnessConfigurationForUser = getBrightnessConfigurationForUser(i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessConfigurationForUser, 1);
                    return true;
                case 54:
                    BrightnessConfiguration defaultBrightnessConfiguration = getDefaultBrightnessConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultBrightnessConfiguration, 1);
                    return true;
                case 55:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMinimalPostProcessingRequested = isMinimalPostProcessingRequested(i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMinimalPostProcessingRequested);
                    return true;
                case 56:
                    int i26 = parcel.readInt();
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setTemporaryBrightness(i26, f);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int i27 = parcel.readInt();
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setBrightness(i27, f2);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float brightness = getBrightness(i28);
                    parcel2.writeNoException();
                    parcel2.writeFloat(brightness);
                    return true;
                case 59:
                    float f3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setTemporaryAutoBrightnessAdjustment(f3);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    Curve minimumBrightnessCurve = getMinimumBrightnessCurve();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(minimumBrightnessCurve, 1);
                    return true;
                case 61:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessInfo brightnessInfo = getBrightnessInfo(i29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessInfo, 1);
                    return true;
                case 62:
                    int preferredWideGamutColorSpaceId = getPreferredWideGamutColorSpaceId();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredWideGamutColorSpaceId);
                    return true;
                case 63:
                    int i30 = parcel.readInt();
                    Display.Mode mode = (Display.Mode) parcel.readTypedObject(Display.Mode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserPreferredDisplayMode(i30, mode);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Display.Mode userPreferredDisplayMode = getUserPreferredDisplayMode(i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userPreferredDisplayMode, 1);
                    return true;
                case 65:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Display.Mode systemPreferredDisplayMode = getSystemPreferredDisplayMode(i32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemPreferredDisplayMode, 1);
                    return true;
                case 66:
                    HdrConversionMode hdrConversionMode = (HdrConversionMode) parcel.readTypedObject(HdrConversionMode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setHdrConversionMode(hdrConversionMode);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    HdrConversionMode hdrConversionModeSetting = getHdrConversionModeSetting();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hdrConversionModeSetting, 1);
                    return true;
                case 68:
                    HdrConversionMode hdrConversionMode2 = getHdrConversionMode();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hdrConversionMode2, 1);
                    return true;
                case 69:
                    int[] supportedHdrOutputTypes = getSupportedHdrOutputTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedHdrOutputTypes);
                    return true;
                case 70:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldAlwaysRespectAppRequestedMode(z5);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    boolean zShouldAlwaysRespectAppRequestedMode = shouldAlwaysRespectAppRequestedMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldAlwaysRespectAppRequestedMode);
                    return true;
                case 72:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRefreshRateSwitchingType(i33);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int refreshRateSwitchingType = getRefreshRateSwitchingType();
                    parcel2.writeNoException();
                    parcel2.writeInt(refreshRateSwitchingType);
                    return true;
                case 74:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DisplayDecorationSupport displayDecorationSupport = getDisplayDecorationSupport(i34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayDecorationSupport, 1);
                    return true;
                case 75:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayIdToMirror(strongBinder2, i35);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    OverlayProperties overlaySupport = getOverlaySupport();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlaySupport, 1);
                    return true;
                case 77:
                    BrightnessConfiguration brightnessConfiguration3 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    int i36 = parcel.readInt();
                    String string15 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForUserWithStats(brightnessConfiguration3, i36, string15, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    BrightnessConfiguration brightnessConfiguration4 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    String string16 = parcel.readString();
                    int i37 = parcel.readInt();
                    String string17 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForDisplayWithStats(brightnessConfiguration4, string16, i37, string17, arrayListCreateStringArrayList4, arrayListCreateStringArrayList5, arrayListCreateStringArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int i38 = parcel.readInt();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetBrightnessConfigurationForUser(i38, string18);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int i39 = parcel.readInt();
                    float f4 = parcel.readFloat();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTemporaryBrightnessForSlowChange(i39, f4, z6);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    BrightnessConfiguration brightnessConfiguration5 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    int i40 = parcel.readInt();
                    String string19 = parcel.readString();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBackupBrightnessConfiguration(brightnessConfiguration5, i40, string19, i41);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessConfiguration backupBrightnessConfiguration = getBackupBrightnessConfiguration(i42, i43);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(backupBrightnessConfiguration, 1);
                    return true;
                case 83:
                    float f5 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    int iConvertToBrightness = convertToBrightness(f5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConvertToBrightness);
                    return true;
                case 84:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayStateOverride(strongBinder3, i44, i45);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayStateOverrideWithDisplayId(strongBinder4, i46, i47, i48);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int i49 = parcel.readInt();
                    float f6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    float adaptiveBrightness = getAdaptiveBrightness(i49, f6);
                    parcel2.writeNoException();
                    parcel2.writeFloat(adaptiveBrightness);
                    return true;
                case 87:
                    int i50 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnableConnectedDisplay(i50, z7);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableConnectedDisplay(i51);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableConnectedDisplay(i52);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestDisplayPower = requestDisplayPower(i53, i54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestDisplayPower);
                    return true;
                case 91:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i55 = parcel.readInt();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    requestDisplayModes(strongBinder5, i55, iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float highestHdrSdrRatio = getHighestHdrSdrRatio(i56);
                    parcel2.writeNoException();
                    parcel2.writeFloat(highestHdrSdrRatio);
                    return true;
                case 93:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float[] dozeBrightnessSensorValueToBrightness = getDozeBrightnessSensorValueToBrightness(i57);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(dozeBrightnessSensorValueToBrightness);
                    return true;
                case 94:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float defaultDozeBrightness = getDefaultDozeBrightness(i58);
                    parcel2.writeNoException();
                    parcel2.writeFloat(defaultDozeBrightness);
                    return true;
                case 95:
                    DisplayTopology displayTopology = getDisplayTopology();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayTopology, 1);
                    return true;
                case 96:
                    DisplayTopology displayTopology2 = (DisplayTopology) parcel.readTypedObject(DisplayTopology.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplayTopology(displayTopology2);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken iRefreshRateTokenAcquirePassiveModeToken = acquirePassiveModeToken(strongBinder6, string20);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iRefreshRateTokenAcquirePassiveModeToken);
                    return true;
                case 98:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken iRefreshRateTokenAcquireLowRefreshRateToken = acquireLowRefreshRateToken(strongBinder7, string21);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iRefreshRateTokenAcquireLowRefreshRateToken);
                    return true;
                case 99:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    int i59 = parcel.readInt();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken iRefreshRateTokenAcquireRefreshRateMaxLimitToken = acquireRefreshRateMaxLimitToken(strongBinder8, i59, string22);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iRefreshRateTokenAcquireRefreshRateMaxLimitToken);
                    return true;
                case 100:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i60 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken iRefreshRateTokenAcquireRefreshRateMinLimitToken = acquireRefreshRateMinLimitToken(strongBinder9, i60, string23);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iRefreshRateTokenAcquireRefreshRateMinLimitToken);
                    return true;
                case 101:
                    long primaryPhysicalDisplayId = getPrimaryPhysicalDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeLong(primaryPhysicalDisplayId);
                    return true;
                case 102:
                    IHbmBrightnessCallback iHbmBrightnessCallbackAsInterface = IHbmBrightnessCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHbmBrightnessCallback(iHbmBrightnessCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDisplayManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.display.IDisplayManager
            public DisplayInfo getDisplayInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DisplayInfo) parcelObtain2.readTypedObject(DisplayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getDisplayIds(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isUidPresentOnDisplay(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayManagerCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallbackWithEventMask(IDisplayManagerCallback iDisplayManagerCallback, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayManagerCallback);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void stopWifiDisplayScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplay(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disconnectWifiDisplay() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void renameWifiDisplay(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void forgetWifiDisplay(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void pauseWifiDisplay() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resumeWifiDisplay() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WifiDisplayStatus) parcelObtain2.readTypedObject(WifiDisplayStatus.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserDisabledHdrTypes(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setAreUserDisabledHdrTypesAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean areUserDisabledHdrTypesAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getUserDisabledHdrTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void overrideHdrTypes(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void requestColorMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualDisplayConfig, 0);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    parcelObtain.writeStrongInterface(iMediaProjection);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplayRotation(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplayWithConfig(SemWifiDisplayConfig semWifiDisplayConfig, IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semWifiDisplayConfig, 0);
                    parcelObtain.writeStrongInterface(iWifiDisplayConnectionCallback);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayChannelScan(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayChannelScanAndInterval(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getScreenSharingStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setScreenSharingStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semDlnaDevice, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public SemDlnaDevice getDlnaDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemDlnaDevice) parcelObtain2.readTypedObject(SemDlnaDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDeviceVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDeviceVolumeMuted(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVolumeKeyEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getDeviceMinVolume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isDeviceVolumeMuted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getDeviceMaxVolume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isWifiDisplayWithPinSupported(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void fitToActiveDisplay(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isFitToActiveDisplay() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public String getPresentationOwner(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setWifiDisplayParam(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(semWifiDisplayParameter, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Point getStableDisplaySize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Point) parcelObtain2.readTypedObject(Point.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public ParceledListSlice getBrightnessEvents(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(brightnessConfiguration, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(brightnessConfiguration, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BrightnessConfiguration) parcelObtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BrightnessConfiguration) parcelObtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BrightnessConfiguration) parcelObtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isMinimalPostProcessingRequested(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightness(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightness(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getBrightness(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Curve getMinimumBrightnessCurve() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Curve) parcelObtain2.readTypedObject(Curve.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessInfo getBrightnessInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BrightnessInfo) parcelObtain2.readTypedObject(BrightnessInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getPreferredWideGamutColorSpaceId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserPreferredDisplayMode(int i, Display.Mode mode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(mode, 0);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Display.Mode getUserPreferredDisplayMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Display.Mode) parcelObtain2.readTypedObject(Display.Mode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Display.Mode getSystemPreferredDisplayMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Display.Mode) parcelObtain2.readTypedObject(Display.Mode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setHdrConversionMode(HdrConversionMode hdrConversionMode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hdrConversionMode, 0);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public HdrConversionMode getHdrConversionModeSetting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (HdrConversionMode) parcelObtain2.readTypedObject(HdrConversionMode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public HdrConversionMode getHdrConversionMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (HdrConversionMode) parcelObtain2.readTypedObject(HdrConversionMode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getSupportedHdrOutputTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setShouldAlwaysRespectAppRequestedMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setRefreshRateSwitchingType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getRefreshRateSwitchingType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public DisplayDecorationSupport getDisplayDecorationSupport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DisplayDecorationSupport) parcelObtain2.readTypedObject(DisplayDecorationSupport.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayIdToMirror(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public OverlayProperties getOverlaySupport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverlayProperties) parcelObtain2.readTypedObject(OverlayProperties.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(brightnessConfiguration, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStringList(list3);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForDisplayWithStats(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(brightnessConfiguration, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStringList(list3);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resetBrightnessConfigurationForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(brightnessConfiguration, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBackupBrightnessConfiguration(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BrightnessConfiguration) parcelObtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int convertToBrightness(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayStateOverride(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayStateOverrideWithDisplayId(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getAdaptiveBrightness(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setEnableConnectedDisplay(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void enableConnectedDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disableConnectedDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestDisplayPower(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void requestDisplayModes(IBinder iBinder, int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getHighestHdrSdrRatio(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float[] getDozeBrightnessSensorValueToBrightness(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getDefaultDozeBrightness(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public DisplayTopology getDisplayTopology() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DisplayTopology) parcelObtain2.readTypedObject(DisplayTopology.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayTopology(DisplayTopology displayTopology) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(displayTopology, 0);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public long getPrimaryPhysicalDisplayId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerHbmBrightnessCallback(IHbmBrightnessCallback iHbmBrightnessCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHbmBrightnessCallback);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void startWifiDisplayScan_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void stopWifiDisplayScan_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void connectWifiDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void renameWifiDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void forgetWifiDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void pauseWifiDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void resumeWifiDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, getCallingPid(), getCallingUid());
        }

        protected void setUserDisabledHdrTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setAreUserDisabledHdrTypesAllowed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void requestColorMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_DISPLAY_COLOR_MODE, getCallingPid(), getCallingUid());
        }

        protected void getBrightnessEvents_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BRIGHTNESS_SLIDER_USAGE, getCallingPid(), getCallingUid());
        }

        protected void getAmbientBrightnessStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_AMBIENT_LIGHT_STATS, getCallingPid(), getCallingUid());
        }

        protected void setBrightnessConfigurationForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setBrightnessConfigurationForDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getBrightnessConfigurationForDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getDefaultBrightnessConfiguration_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONFIGURE_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setTemporaryBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setTemporaryAutoBrightnessAdjustment_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getBrightnessInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void setUserPreferredDisplayMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_USER_PREFERRED_DISPLAY_MODE, getCallingPid(), getCallingUid());
        }

        protected void setHdrConversionMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_HDR_CONVERSION_MODE, getCallingPid(), getCallingUid());
        }

        protected void setShouldAlwaysRespectAppRequestedMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_DISPLAY_MODE_REQUESTS, getCallingPid(), getCallingUid());
        }

        protected void shouldAlwaysRespectAppRequestedMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_DISPLAY_MODE_REQUESTS, getCallingPid(), getCallingUid());
        }

        protected void setRefreshRateSwitchingType_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_REFRESH_RATE_SWITCHING_TYPE, getCallingPid(), getCallingUid());
        }

        protected void setEnableConnectedDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        protected void enableConnectedDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        protected void disableConnectedDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        protected void requestDisplayPower_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        protected void requestDisplayModes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RESTRICT_DISPLAY_MODES, getCallingPid(), getCallingUid());
        }

        protected void getDozeBrightnessSensorValueToBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getDefaultDozeBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_BRIGHTNESS, getCallingPid(), getCallingUid());
        }

        protected void getDisplayTopology_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }

        protected void setDisplayTopology_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DISPLAYS, getCallingPid(), getCallingUid());
        }
    }
}
