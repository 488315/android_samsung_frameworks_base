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
        static final int TRANSACTION_acquireLowRefreshRateToken = 97;
        static final int TRANSACTION_acquirePassiveModeToken = 96;
        static final int TRANSACTION_acquireRefreshRateMaxLimitToken = 98;
        static final int TRANSACTION_acquireRefreshRateMinLimitToken = 99;
        static final int TRANSACTION_areUserDisabledHdrTypesAllowed = 17;
        static final int TRANSACTION_connectWifiDisplay = 8;
        static final int TRANSACTION_connectWifiDisplayWithConfig = 27;
        static final int TRANSACTION_convertToBrightness = 83;
        static final int TRANSACTION_createVirtualDisplay = 21;
        static final int TRANSACTION_disableConnectedDisplay = 88;
        static final int TRANSACTION_disconnectWifiDisplay = 9;
        static final int TRANSACTION_enableConnectedDisplay = 87;
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
        static final int TRANSACTION_getDefaultDozeBrightness = 93;
        static final int TRANSACTION_getDeviceMaxVolume = 39;
        static final int TRANSACTION_getDeviceMinVolume = 37;
        static final int TRANSACTION_getDisplayDecorationSupport = 74;
        static final int TRANSACTION_getDisplayIds = 2;
        static final int TRANSACTION_getDisplayInfo = 1;
        static final int TRANSACTION_getDisplayTopology = 94;
        static final int TRANSACTION_getDlnaDevice = 33;
        static final int TRANSACTION_getDozeBrightnessSensorValueToBrightness = 92;
        static final int TRANSACTION_getHdrConversionMode = 68;
        static final int TRANSACTION_getHdrConversionModeSetting = 67;
        static final int TRANSACTION_getHighestHdrSdrRatio = 91;
        static final int TRANSACTION_getMinimumBrightnessCurve = 60;
        static final int TRANSACTION_getOverlaySupport = 76;
        static final int TRANSACTION_getPreferredWideGamutColorSpaceId = 62;
        static final int TRANSACTION_getPresentationOwner = 43;
        static final int TRANSACTION_getPrimaryPhysicalDisplayId = 100;
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
        static final int TRANSACTION_registerHbmBrightnessCallback = 101;
        static final int TRANSACTION_releaseVirtualDisplay = 24;
        static final int TRANSACTION_renameWifiDisplay = 10;
        static final int TRANSACTION_requestColorMode = 20;
        static final int TRANSACTION_requestDisplayModes = 90;
        static final int TRANSACTION_requestDisplayPower = 89;
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
        static final int TRANSACTION_setDisplayTopology = 95;
        static final int TRANSACTION_setDlnaDevice = 32;
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
            return 100;
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDisplayManager)) {
                return (IDisplayManager) queryLocalInterface;
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
                    return "enableConnectedDisplay";
                case 88:
                    return "disableConnectedDisplay";
                case 89:
                    return "requestDisplayPower";
                case 90:
                    return "requestDisplayModes";
                case 91:
                    return "getHighestHdrSdrRatio";
                case 92:
                    return "getDozeBrightnessSensorValueToBrightness";
                case 93:
                    return "getDefaultDozeBrightness";
                case 94:
                    return "getDisplayTopology";
                case 95:
                    return "setDisplayTopology";
                case 96:
                    return "acquirePassiveModeToken";
                case 97:
                    return "acquireLowRefreshRateToken";
                case 98:
                    return "acquireRefreshRateMaxLimitToken";
                case 99:
                    return "acquireRefreshRateMinLimitToken";
                case 100:
                    return "getPrimaryPhysicalDisplayId";
                case 101:
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DisplayInfo displayInfo = getDisplayInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayInfo, 1);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] displayIds = getDisplayIds(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIds);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidPresentOnDisplay = isUidPresentOnDisplay(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidPresentOnDisplay);
                    return true;
                case 4:
                    IDisplayManagerCallback asInterface = IDisplayManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IDisplayManagerCallback asInterface2 = IDisplayManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    registerCallbackWithEventMask(asInterface2, readLong);
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    connectWifiDisplay(readString);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    disconnectWifiDisplay();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    renameWifiDisplay(readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetWifiDisplay(readString4);
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
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setUserDisabledHdrTypes(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAreUserDisabledHdrTypesAllowed(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean areUserDisabledHdrTypesAllowed = areUserDisabledHdrTypesAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areUserDisabledHdrTypesAllowed);
                    return true;
                case 18:
                    int[] userDisabledHdrTypes = getUserDisabledHdrTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(userDisabledHdrTypes);
                    return true;
                case 19:
                    int readInt4 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    overrideHdrTypes(readInt4, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestColorMode(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    VirtualDisplayConfig virtualDisplayConfig = (VirtualDisplayConfig) parcel.readTypedObject(VirtualDisplayConfig.CREATOR);
                    IVirtualDisplayCallback asInterface3 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    IMediaProjection asInterface4 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createVirtualDisplay = createVirtualDisplay(virtualDisplayConfig, asInterface3, asInterface4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(createVirtualDisplay);
                    return true;
                case 22:
                    IVirtualDisplayCallback asInterface5 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeVirtualDisplay(asInterface5, readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IVirtualDisplayCallback asInterface6 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVirtualDisplaySurface(asInterface6, surface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IVirtualDisplayCallback asInterface7 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    releaseVirtualDisplay(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IVirtualDisplayCallback asInterface8 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVirtualDisplayRotation(asInterface8, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IVirtualDisplayCallback asInterface9 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rotateVirtualDisplay(asInterface9, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    SemWifiDisplayConfig semWifiDisplayConfig = (SemWifiDisplayConfig) parcel.readTypedObject(SemWifiDisplayConfig.CREATOR);
                    IWifiDisplayConnectionCallback asInterface10 = IWifiDisplayConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectWifiDisplayWithConfig(semWifiDisplayConfig, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startWifiDisplayChannelScan(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startWifiDisplayChannelScanAndInterval(readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int screenSharingStatus = getScreenSharingStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(screenSharingStatus);
                    return true;
                case 31:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenSharingStatus(readInt15);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    SemDlnaDevice semDlnaDevice = (SemDlnaDevice) parcel.readTypedObject(SemDlnaDevice.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setDlnaDevice(semDlnaDevice, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    SemDlnaDevice dlnaDevice = getDlnaDevice();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dlnaDevice, 1);
                    return true;
                case 34:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceVolume(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceVolumeMuted(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolumeKeyEvent(readInt17);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int deviceMinVolume = getDeviceMinVolume();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceMinVolume);
                    return true;
                case 38:
                    boolean isDeviceVolumeMuted = isDeviceVolumeMuted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceVolumeMuted);
                    return true;
                case 39:
                    int deviceMaxVolume = getDeviceMaxVolume();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceMaxVolume);
                    return true;
                case 40:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isWifiDisplayWithPinSupported = isWifiDisplayWithPinSupported(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiDisplayWithPinSupported);
                    return true;
                case 41:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    fitToActiveDisplay(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean isFitToActiveDisplay = isFitToActiveDisplay();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFitToActiveDisplay);
                    return true;
                case 43:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String presentationOwner = getPresentationOwner(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeString(presentationOwner);
                    return true;
                case 44:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setWifiDisplayParam(readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestSetWifiDisplayParameters = requestSetWifiDisplayParameters(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestSetWifiDisplayParameters);
                    return true;
                case 46:
                    String readString9 = parcel.readString();
                    SemWifiDisplayParameter semWifiDisplayParameter = (SemWifiDisplayParameter) parcel.readTypedObject(SemWifiDisplayParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestWifiDisplayParameter = requestWifiDisplayParameter(readString9, semWifiDisplayParameter);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestWifiDisplayParameter);
                    return true;
                case 47:
                    Point stableDisplaySize = getStableDisplaySize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(stableDisplaySize, 1);
                    return true;
                case 48:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice brightnessEvents = getBrightnessEvents(readString10);
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
                    int readInt19 = parcel.readInt();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForUser(brightnessConfiguration, readInt19, readString11);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    BrightnessConfiguration brightnessConfiguration2 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    String readString12 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForDisplay(brightnessConfiguration2, readString12, readInt20, readString13);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    String readString14 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessConfiguration brightnessConfigurationForDisplay = getBrightnessConfigurationForDisplay(readString14, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessConfigurationForDisplay, 1);
                    return true;
                case 53:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessConfiguration brightnessConfigurationForUser = getBrightnessConfigurationForUser(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessConfigurationForUser, 1);
                    return true;
                case 54:
                    BrightnessConfiguration defaultBrightnessConfiguration = getDefaultBrightnessConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultBrightnessConfiguration, 1);
                    return true;
                case 55:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMinimalPostProcessingRequested = isMinimalPostProcessingRequested(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMinimalPostProcessingRequested);
                    return true;
                case 56:
                    int readInt24 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setTemporaryBrightness(readInt24, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt25 = parcel.readInt();
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setBrightness(readInt25, readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float brightness = getBrightness(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeFloat(brightness);
                    return true;
                case 59:
                    float readFloat3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setTemporaryAutoBrightnessAdjustment(readFloat3);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    Curve minimumBrightnessCurve = getMinimumBrightnessCurve();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(minimumBrightnessCurve, 1);
                    return true;
                case 61:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessInfo brightnessInfo = getBrightnessInfo(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(brightnessInfo, 1);
                    return true;
                case 62:
                    int preferredWideGamutColorSpaceId = getPreferredWideGamutColorSpaceId();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredWideGamutColorSpaceId);
                    return true;
                case 63:
                    int readInt28 = parcel.readInt();
                    Display.Mode mode = (Display.Mode) parcel.readTypedObject(Display.Mode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserPreferredDisplayMode(readInt28, mode);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Display.Mode userPreferredDisplayMode = getUserPreferredDisplayMode(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userPreferredDisplayMode, 1);
                    return true;
                case 65:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Display.Mode systemPreferredDisplayMode = getSystemPreferredDisplayMode(readInt30);
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
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldAlwaysRespectAppRequestedMode(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    boolean shouldAlwaysRespectAppRequestedMode = shouldAlwaysRespectAppRequestedMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldAlwaysRespectAppRequestedMode);
                    return true;
                case 72:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRefreshRateSwitchingType(readInt31);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int refreshRateSwitchingType = getRefreshRateSwitchingType();
                    parcel2.writeNoException();
                    parcel2.writeInt(refreshRateSwitchingType);
                    return true;
                case 74:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DisplayDecorationSupport displayDecorationSupport = getDisplayDecorationSupport(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayDecorationSupport, 1);
                    return true;
                case 75:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayIdToMirror(readStrongBinder2, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    OverlayProperties overlaySupport = getOverlaySupport();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(overlaySupport, 1);
                    return true;
                case 77:
                    BrightnessConfiguration brightnessConfiguration3 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    int readInt34 = parcel.readInt();
                    String readString15 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForUserWithStats(brightnessConfiguration3, readInt34, readString15, createStringArrayList, createStringArrayList2, createStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    BrightnessConfiguration brightnessConfiguration4 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    String readString16 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    String readString17 = parcel.readString();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setBrightnessConfigurationForDisplayWithStats(brightnessConfiguration4, readString16, readInt35, readString17, createStringArrayList4, createStringArrayList5, createStringArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt36 = parcel.readInt();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetBrightnessConfigurationForUser(readInt36, readString18);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int readInt37 = parcel.readInt();
                    float readFloat4 = parcel.readFloat();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTemporaryBrightnessForSlowChange(readInt37, readFloat4, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    BrightnessConfiguration brightnessConfiguration5 = (BrightnessConfiguration) parcel.readTypedObject(BrightnessConfiguration.CREATOR);
                    int readInt38 = parcel.readInt();
                    String readString19 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBackupBrightnessConfiguration(brightnessConfiguration5, readInt38, readString19, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BrightnessConfiguration backupBrightnessConfiguration = getBackupBrightnessConfiguration(readInt40, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(backupBrightnessConfiguration, 1);
                    return true;
                case 83:
                    float readFloat5 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    int convertToBrightness = convertToBrightness(readFloat5);
                    parcel2.writeNoException();
                    parcel2.writeInt(convertToBrightness);
                    return true;
                case 84:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayStateOverride(readStrongBinder3, readInt42, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayStateOverrideWithDisplayId(readStrongBinder4, readInt44, readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int readInt47 = parcel.readInt();
                    float readFloat6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    float adaptiveBrightness = getAdaptiveBrightness(readInt47, readFloat6);
                    parcel2.writeNoException();
                    parcel2.writeFloat(adaptiveBrightness);
                    return true;
                case 87:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableConnectedDisplay(readInt48);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableConnectedDisplay(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestDisplayPower = requestDisplayPower(readInt50, readInt51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestDisplayPower);
                    return true;
                case 90:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt52 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    requestDisplayModes(readStrongBinder5, readInt52, createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float highestHdrSdrRatio = getHighestHdrSdrRatio(readInt53);
                    parcel2.writeNoException();
                    parcel2.writeFloat(highestHdrSdrRatio);
                    return true;
                case 92:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float[] dozeBrightnessSensorValueToBrightness = getDozeBrightnessSensorValueToBrightness(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(dozeBrightnessSensorValueToBrightness);
                    return true;
                case 93:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float defaultDozeBrightness = getDefaultDozeBrightness(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeFloat(defaultDozeBrightness);
                    return true;
                case 94:
                    DisplayTopology displayTopology = getDisplayTopology();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displayTopology, 1);
                    return true;
                case 95:
                    DisplayTopology displayTopology2 = (DisplayTopology) parcel.readTypedObject(DisplayTopology.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplayTopology(displayTopology2);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken acquirePassiveModeToken = acquirePassiveModeToken(readStrongBinder6, readString20);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(acquirePassiveModeToken);
                    return true;
                case 97:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken acquireLowRefreshRateToken = acquireLowRefreshRateToken(readStrongBinder7, readString21);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(acquireLowRefreshRateToken);
                    return true;
                case 98:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    int readInt56 = parcel.readInt();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken acquireRefreshRateMaxLimitToken = acquireRefreshRateMaxLimitToken(readStrongBinder8, readInt56, readString22);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(acquireRefreshRateMaxLimitToken);
                    return true;
                case 99:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt57 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRefreshRateToken acquireRefreshRateMinLimitToken = acquireRefreshRateMinLimitToken(readStrongBinder9, readInt57, readString23);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(acquireRefreshRateMinLimitToken);
                    return true;
                case 100:
                    long primaryPhysicalDisplayId = getPrimaryPhysicalDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeLong(primaryPhysicalDisplayId);
                    return true;
                case 101:
                    IHbmBrightnessCallback asInterface11 = IHbmBrightnessCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHbmBrightnessCallback(asInterface11);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DisplayInfo) obtain2.readTypedObject(DisplayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getDisplayIds(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isUidPresentOnDisplay(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayManagerCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallbackWithEventMask(IDisplayManagerCallback iDisplayManagerCallback, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayManagerCallback);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void stopWifiDisplayScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplay(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disconnectWifiDisplay() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void renameWifiDisplay(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void forgetWifiDisplay(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void pauseWifiDisplay() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resumeWifiDisplay() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WifiDisplayStatus) obtain2.readTypedObject(WifiDisplayStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserDisabledHdrTypes(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setAreUserDisabledHdrTypesAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean areUserDisabledHdrTypesAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getUserDisabledHdrTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void overrideHdrTypes(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void requestColorMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(virtualDisplayConfig, 0);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeStrongInterface(iMediaProjection);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplayRotation(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplayWithConfig(SemWifiDisplayConfig semWifiDisplayConfig, IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(semWifiDisplayConfig, 0);
                    obtain.writeStrongInterface(iWifiDisplayConnectionCallback);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayChannelScan(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayChannelScanAndInterval(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getScreenSharingStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setScreenSharingStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(semDlnaDevice, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public SemDlnaDevice getDlnaDevice() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemDlnaDevice) obtain2.readTypedObject(SemDlnaDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDeviceVolume(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDeviceVolumeMuted(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVolumeKeyEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getDeviceMinVolume() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isDeviceVolumeMuted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getDeviceMaxVolume() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isWifiDisplayWithPinSupported(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void fitToActiveDisplay(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isFitToActiveDisplay() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public String getPresentationOwner(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setWifiDisplayParam(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(semWifiDisplayParameter, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Point getStableDisplaySize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Point) obtain2.readTypedObject(Point.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public ParceledListSlice getBrightnessEvents(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BrightnessConfiguration) obtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BrightnessConfiguration) obtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BrightnessConfiguration) obtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isMinimalPostProcessingRequested(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightness(int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightness(int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getBrightness(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Curve getMinimumBrightnessCurve() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Curve) obtain2.readTypedObject(Curve.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessInfo getBrightnessInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BrightnessInfo) obtain2.readTypedObject(BrightnessInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getPreferredWideGamutColorSpaceId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserPreferredDisplayMode(int i, Display.Mode mode) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mode, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Display.Mode getUserPreferredDisplayMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Display.Mode) obtain2.readTypedObject(Display.Mode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Display.Mode getSystemPreferredDisplayMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Display.Mode) obtain2.readTypedObject(Display.Mode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setHdrConversionMode(HdrConversionMode hdrConversionMode) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdrConversionMode, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public HdrConversionMode getHdrConversionModeSetting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HdrConversionMode) obtain2.readTypedObject(HdrConversionMode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public HdrConversionMode getHdrConversionMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HdrConversionMode) obtain2.readTypedObject(HdrConversionMode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getSupportedHdrOutputTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setShouldAlwaysRespectAppRequestedMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setRefreshRateSwitchingType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getRefreshRateSwitchingType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public DisplayDecorationSupport getDisplayDecorationSupport(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DisplayDecorationSupport) obtain2.readTypedObject(DisplayDecorationSupport.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayIdToMirror(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public OverlayProperties getOverlaySupport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return (OverlayProperties) obtain2.readTypedObject(OverlayProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForDisplayWithStats(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resetBrightnessConfigurationForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(brightnessConfiguration, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBackupBrightnessConfiguration(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BrightnessConfiguration) obtain2.readTypedObject(BrightnessConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int convertToBrightness(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayStateOverride(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayStateOverrideWithDisplayId(IBinder iBinder, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getAdaptiveBrightness(int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void enableConnectedDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disableConnectedDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestDisplayPower(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void requestDisplayModes(IBinder iBinder, int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getHighestHdrSdrRatio(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float[] getDozeBrightnessSensorValueToBrightness(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getDefaultDozeBrightness(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public DisplayTopology getDisplayTopology() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DisplayTopology) obtain2.readTypedObject(DisplayTopology.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayTopology(DisplayTopology displayTopology) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(displayTopology, 0);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRefreshRateToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public long getPrimaryPhysicalDisplayId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerHbmBrightnessCallback(IHbmBrightnessCallback iHbmBrightnessCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iHbmBrightnessCallback);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
