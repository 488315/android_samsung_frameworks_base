package android.hardware.display;

import android.content.p002pm.ParceledListSlice;
import android.graphics.Point;
import android.hardware.OverlayProperties;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.media.projection.IMediaProjection;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.Surface;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.util.List;

/* loaded from: classes2.dex */
public interface IDisplayManager extends IInterface {
    IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) throws RemoteException;

    IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) throws RemoteException;

    IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) throws RemoteException;

    IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) throws RemoteException;

    boolean areUserDisabledHdrTypesAllowed() throws RemoteException;

    void connectWifiDisplay(String str) throws RemoteException;

    void connectWifiDisplayWithConfig(SemWifiDisplayConfig semWifiDisplayConfig, IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) throws RemoteException;

    int convertToBrightness(float f) throws RemoteException;

    int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str) throws RemoteException;

    void disconnectWifiDisplay() throws RemoteException;

    void fitToActiveDisplay(boolean z) throws RemoteException;

    void forgetWifiDisplay(String str) throws RemoteException;

    float getAdaptiveBrightness(int i, float f) throws RemoteException;

    ParceledListSlice getAmbientBrightnessStats() throws RemoteException;

    BrightnessConfiguration getBackupBrightnessConfiguration(int i) throws RemoteException;

    float getBrightness(int i) throws RemoteException;

    BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) throws RemoteException;

    BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws RemoteException;

    ParceledListSlice getBrightnessEvents(String str) throws RemoteException;

    BrightnessInfo getBrightnessInfo(int i) throws RemoteException;

    BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException;

    int getDeviceMaxVolume() throws RemoteException;

    int getDeviceMinVolume() throws RemoteException;

    DisplayDecorationSupport getDisplayDecorationSupport(int i) throws RemoteException;

    int[] getDisplayIds(boolean z) throws RemoteException;

    DisplayInfo getDisplayInfo(int i) throws RemoteException;

    SemDlnaDevice getDlnaDevice() throws RemoteException;

    HdrConversionMode getHdrConversionMode() throws RemoteException;

    HdrConversionMode getHdrConversionModeSetting() throws RemoteException;

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

    void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException;

    void renameWifiDisplay(String str, String str2) throws RemoteException;

    void requestColorMode(int i, int i2) throws RemoteException;

    boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) throws RemoteException;

    boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) throws RemoteException;

    void resetBrightnessConfigurationForUser(int i, String str) throws RemoteException;

    void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws RemoteException;

    void resumeWifiDisplay() throws RemoteException;

    void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) throws RemoteException;

    void setAreUserDisabledHdrTypesAllowed(boolean z) throws RemoteException;

    void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException;

    void setBrightness(int i, float f) throws RemoteException;

    void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) throws RemoteException;

    void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException;

    void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException;

    void setDeviceVolume(int i) throws RemoteException;

    void setDeviceVolumeMuted(boolean z) throws RemoteException;

    void setDisplayIdToMirror(IBinder iBinder, int i) throws RemoteException;

    void setDisplayStateLimit(IBinder iBinder, int i) throws RemoteException;

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

    void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) throws RemoteException;

    void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException;

    void setVolumeKeyEvent(int i) throws RemoteException;

    void setWifiDisplayParam(String str, String str2) throws RemoteException;

    boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException;

    void startWifiDisplayChannelScan(int i) throws RemoteException;

    void startWifiDisplayChannelScanAndInterval(int i, int i2) throws RemoteException;

    void startWifiDisplayScan() throws RemoteException;

    void startWifiDisplayScanAutoP2P() throws RemoteException;

    void stopWifiDisplayScan() throws RemoteException;

    public static class Default implements IDisplayManager {
        @Override // android.hardware.display.IDisplayManager
        public DisplayInfo getDisplayInfo(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getDisplayIds(boolean includeDisabled) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isUidPresentOnDisplay(int uid, int displayId) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerCallback(IDisplayManagerCallback callback) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void registerCallbackWithEventMask(IDisplayManagerCallback callback, long eventsMask) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayScan() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void stopWifiDisplayScan() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void connectWifiDisplay(String address) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void disconnectWifiDisplay() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void renameWifiDisplay(String address, String alias) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void forgetWifiDisplay(String address) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void pauseWifiDisplay() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void resumeWifiDisplay() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setUserDisabledHdrTypes(int[] userDisabledTypes) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setAreUserDisabledHdrTypesAllowed(boolean areUserDisabledHdrTypesAllowed) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean areUserDisabledHdrTypesAllowed() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getUserDisabledHdrTypes() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void overrideHdrTypes(int displayId, int[] modes) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void requestColorMode(int displayId, int colorMode) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback callback, IMediaProjection projectionToken, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void resizeVirtualDisplay(IVirtualDisplayCallback token, int width, int height, int densityDpi) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVirtualDisplaySurface(IVirtualDisplayCallback token, Surface surface) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void releaseVirtualDisplay(IVirtualDisplayCallback token) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVirtualDisplayState(IVirtualDisplayCallback token, boolean isOn) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void rotateVirtualDisplay(IVirtualDisplayCallback token, int rotation) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void connectWifiDisplayWithConfig(SemWifiDisplayConfig wifidisplayConfig, IWifiDisplayConnectionCallback callback) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayChannelScan(int scanChannel) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayChannelScanAndInterval(int scanChannel, int interval) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int getScreenSharingStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setScreenSharingStatus(int status) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDlnaDevice(SemDlnaDevice dlnaDevice, IBinder appToken) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public SemDlnaDevice getDlnaDevice() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDeviceVolume(int volume) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDeviceVolumeMuted(boolean muted) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setVolumeKeyEvent(int event) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int getDeviceMinVolume() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isDeviceVolumeMuted() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getDeviceMaxVolume() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isWifiDisplayWithPinSupported(String address) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void fitToActiveDisplay(boolean status) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isFitToActiveDisplay() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public String getPresentationOwner(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setWifiDisplayParam(String key, String value) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> parameters) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean requestWifiDisplayParameter(String parameterGroup, SemWifiDisplayParameter parameter) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void startWifiDisplayScanAutoP2P() throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public Point getStableDisplaySize() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public ParceledListSlice getBrightnessEvents(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForUser(BrightnessConfiguration c, int userId, String packageName) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForDisplay(BrightnessConfiguration c, String uniqueDisplayId, int userId, String packageName) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getBrightnessConfigurationForDisplay(String uniqueDisplayId, int userId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getBrightnessConfigurationForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean isMinimalPostProcessingRequested(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryBrightness(int displayId, float brightness) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightness(int displayId, float brightness) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public float getBrightness(int displayId) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryAutoBrightnessAdjustment(float adjustment) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public Curve getMinimumBrightnessCurve() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessInfo getBrightnessInfo(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int getPreferredWideGamutColorSpaceId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setUserPreferredDisplayMode(int displayId, Display.Mode mode) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public Display.Mode getUserPreferredDisplayMode(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public Display.Mode getSystemPreferredDisplayMode(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setHdrConversionMode(HdrConversionMode hdrConversionMode) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public HdrConversionMode getHdrConversionModeSetting() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public HdrConversionMode getHdrConversionMode() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int[] getSupportedHdrOutputTypes() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setShouldAlwaysRespectAppRequestedMode(boolean enabled) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setRefreshRateSwitchingType(int newValue) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public int getRefreshRateSwitchingType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public DisplayDecorationSupport getDisplayDecorationSupport(int displayId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayIdToMirror(IBinder token, int displayId) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public OverlayProperties getOverlaySupport() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration c, int userId, String packageName, List<String> weights, List<String> timeWeights, List<String> continuityWeights) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void resetBrightnessConfigurationForUser(int userId, String packageName) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setTemporaryBrightnessForSlowChange(int displayId, float brightness, boolean slowChange) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public void setBackupBrightnessConfiguration(BrightnessConfiguration config, int userId, String packageName) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public BrightnessConfiguration getBackupBrightnessConfiguration(int userId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public int convertToBrightness(float nits) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IDisplayManager
        public void setDisplayStateLimit(IBinder lock, int stateLimit) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManager
        public float getAdaptiveBrightness(int displayId, float lux) throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquirePassiveModeToken(IBinder token, String tag) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquireLowRefreshRateToken(IBinder token, String tag) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder token, int refreshRate, String tag) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder token, int refreshRate, String tag) throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IDisplayManager
        public long getPrimaryPhysicalDisplayId() throws RemoteException {
            return 0L;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDisplayManager {
        public static final String DESCRIPTOR = "android.hardware.display.IDisplayManager";
        static final int TRANSACTION_acquireLowRefreshRateToken = 87;
        static final int TRANSACTION_acquirePassiveModeToken = 86;
        static final int TRANSACTION_acquireRefreshRateMaxLimitToken = 88;
        static final int TRANSACTION_acquireRefreshRateMinLimitToken = 89;
        static final int TRANSACTION_areUserDisabledHdrTypesAllowed = 17;
        static final int TRANSACTION_connectWifiDisplay = 8;
        static final int TRANSACTION_connectWifiDisplayWithConfig = 27;
        static final int TRANSACTION_convertToBrightness = 83;
        static final int TRANSACTION_createVirtualDisplay = 21;
        static final int TRANSACTION_disconnectWifiDisplay = 9;
        static final int TRANSACTION_fitToActiveDisplay = 41;
        static final int TRANSACTION_forgetWifiDisplay = 11;
        static final int TRANSACTION_getAdaptiveBrightness = 85;
        static final int TRANSACTION_getAmbientBrightnessStats = 50;
        static final int TRANSACTION_getBackupBrightnessConfiguration = 82;
        static final int TRANSACTION_getBrightness = 59;
        static final int TRANSACTION_getBrightnessConfigurationForDisplay = 53;
        static final int TRANSACTION_getBrightnessConfigurationForUser = 54;
        static final int TRANSACTION_getBrightnessEvents = 49;
        static final int TRANSACTION_getBrightnessInfo = 62;
        static final int TRANSACTION_getDefaultBrightnessConfiguration = 55;
        static final int TRANSACTION_getDeviceMaxVolume = 39;
        static final int TRANSACTION_getDeviceMinVolume = 37;
        static final int TRANSACTION_getDisplayDecorationSupport = 75;
        static final int TRANSACTION_getDisplayIds = 2;
        static final int TRANSACTION_getDisplayInfo = 1;
        static final int TRANSACTION_getDlnaDevice = 33;
        static final int TRANSACTION_getHdrConversionMode = 69;
        static final int TRANSACTION_getHdrConversionModeSetting = 68;
        static final int TRANSACTION_getMinimumBrightnessCurve = 61;
        static final int TRANSACTION_getOverlaySupport = 77;
        static final int TRANSACTION_getPreferredWideGamutColorSpaceId = 63;
        static final int TRANSACTION_getPresentationOwner = 43;
        static final int TRANSACTION_getPrimaryPhysicalDisplayId = 90;
        static final int TRANSACTION_getRefreshRateSwitchingType = 74;
        static final int TRANSACTION_getScreenSharingStatus = 30;
        static final int TRANSACTION_getStableDisplaySize = 48;
        static final int TRANSACTION_getSupportedHdrOutputTypes = 70;
        static final int TRANSACTION_getSystemPreferredDisplayMode = 66;
        static final int TRANSACTION_getUserDisabledHdrTypes = 18;
        static final int TRANSACTION_getUserPreferredDisplayMode = 65;
        static final int TRANSACTION_getWifiDisplayStatus = 14;
        static final int TRANSACTION_isDeviceVolumeMuted = 38;
        static final int TRANSACTION_isFitToActiveDisplay = 42;
        static final int TRANSACTION_isMinimalPostProcessingRequested = 56;
        static final int TRANSACTION_isUidPresentOnDisplay = 3;
        static final int TRANSACTION_isWifiDisplayWithPinSupported = 40;
        static final int TRANSACTION_overrideHdrTypes = 19;
        static final int TRANSACTION_pauseWifiDisplay = 12;
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_registerCallbackWithEventMask = 5;
        static final int TRANSACTION_releaseVirtualDisplay = 24;
        static final int TRANSACTION_renameWifiDisplay = 10;
        static final int TRANSACTION_requestColorMode = 20;
        static final int TRANSACTION_requestSetWifiDisplayParameters = 45;
        static final int TRANSACTION_requestWifiDisplayParameter = 46;
        static final int TRANSACTION_resetBrightnessConfigurationForUser = 79;
        static final int TRANSACTION_resizeVirtualDisplay = 22;
        static final int TRANSACTION_resumeWifiDisplay = 13;
        static final int TRANSACTION_rotateVirtualDisplay = 26;
        static final int TRANSACTION_setAreUserDisabledHdrTypesAllowed = 16;
        static final int TRANSACTION_setBackupBrightnessConfiguration = 81;
        static final int TRANSACTION_setBrightness = 58;
        static final int TRANSACTION_setBrightnessConfigurationForDisplay = 52;
        static final int TRANSACTION_setBrightnessConfigurationForUser = 51;
        static final int TRANSACTION_setBrightnessConfigurationForUserWithStats = 78;
        static final int TRANSACTION_setDeviceVolume = 34;
        static final int TRANSACTION_setDeviceVolumeMuted = 35;
        static final int TRANSACTION_setDisplayIdToMirror = 76;
        static final int TRANSACTION_setDisplayStateLimit = 84;
        static final int TRANSACTION_setDlnaDevice = 32;
        static final int TRANSACTION_setHdrConversionMode = 67;
        static final int TRANSACTION_setRefreshRateSwitchingType = 73;
        static final int TRANSACTION_setScreenSharingStatus = 31;
        static final int TRANSACTION_setShouldAlwaysRespectAppRequestedMode = 71;
        static final int TRANSACTION_setTemporaryAutoBrightnessAdjustment = 60;
        static final int TRANSACTION_setTemporaryBrightness = 57;
        static final int TRANSACTION_setTemporaryBrightnessForSlowChange = 80;
        static final int TRANSACTION_setUserDisabledHdrTypes = 15;
        static final int TRANSACTION_setUserPreferredDisplayMode = 64;
        static final int TRANSACTION_setVirtualDisplayState = 25;
        static final int TRANSACTION_setVirtualDisplaySurface = 23;
        static final int TRANSACTION_setVolumeKeyEvent = 36;
        static final int TRANSACTION_setWifiDisplayParam = 44;
        static final int TRANSACTION_shouldAlwaysRespectAppRequestedMode = 72;
        static final int TRANSACTION_startWifiDisplayChannelScan = 28;
        static final int TRANSACTION_startWifiDisplayChannelScanAndInterval = 29;
        static final int TRANSACTION_startWifiDisplayScan = 6;
        static final int TRANSACTION_startWifiDisplayScanAutoP2P = 47;
        static final int TRANSACTION_stopWifiDisplayScan = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDisplayManager)) {
                return (IDisplayManager) iin;
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
                    return "setVirtualDisplayState";
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
                    return "startWifiDisplayScanAutoP2P";
                case 48:
                    return "getStableDisplaySize";
                case 49:
                    return "getBrightnessEvents";
                case 50:
                    return "getAmbientBrightnessStats";
                case 51:
                    return "setBrightnessConfigurationForUser";
                case 52:
                    return "setBrightnessConfigurationForDisplay";
                case 53:
                    return "getBrightnessConfigurationForDisplay";
                case 54:
                    return "getBrightnessConfigurationForUser";
                case 55:
                    return "getDefaultBrightnessConfiguration";
                case 56:
                    return "isMinimalPostProcessingRequested";
                case 57:
                    return "setTemporaryBrightness";
                case 58:
                    return "setBrightness";
                case 59:
                    return "getBrightness";
                case 60:
                    return "setTemporaryAutoBrightnessAdjustment";
                case 61:
                    return "getMinimumBrightnessCurve";
                case 62:
                    return "getBrightnessInfo";
                case 63:
                    return "getPreferredWideGamutColorSpaceId";
                case 64:
                    return "setUserPreferredDisplayMode";
                case 65:
                    return "getUserPreferredDisplayMode";
                case 66:
                    return "getSystemPreferredDisplayMode";
                case 67:
                    return "setHdrConversionMode";
                case 68:
                    return "getHdrConversionModeSetting";
                case 69:
                    return "getHdrConversionMode";
                case 70:
                    return "getSupportedHdrOutputTypes";
                case 71:
                    return "setShouldAlwaysRespectAppRequestedMode";
                case 72:
                    return "shouldAlwaysRespectAppRequestedMode";
                case 73:
                    return "setRefreshRateSwitchingType";
                case 74:
                    return "getRefreshRateSwitchingType";
                case 75:
                    return "getDisplayDecorationSupport";
                case 76:
                    return "setDisplayIdToMirror";
                case 77:
                    return "getOverlaySupport";
                case 78:
                    return "setBrightnessConfigurationForUserWithStats";
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
                    return "setDisplayStateLimit";
                case 85:
                    return "getAdaptiveBrightness";
                case 86:
                    return "acquirePassiveModeToken";
                case 87:
                    return "acquireLowRefreshRateToken";
                case 88:
                    return "acquireRefreshRateMaxLimitToken";
                case 89:
                    return "acquireRefreshRateMinLimitToken";
                case 90:
                    return "getPrimaryPhysicalDisplayId";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
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
                            data.enforceNoDataAvail();
                            DisplayInfo _result = getDisplayInfo(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 2:
                            boolean _arg02 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int[] _result2 = getDisplayIds(_arg02);
                            reply.writeNoException();
                            reply.writeIntArray(_result2);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result3 = isUidPresentOnDisplay(_arg03, _arg1);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            IDisplayManagerCallback _arg04 = IDisplayManagerCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            registerCallback(_arg04);
                            reply.writeNoException();
                            return true;
                        case 5:
                            IDisplayManagerCallback _arg05 = IDisplayManagerCallback.Stub.asInterface(data.readStrongBinder());
                            long _arg12 = data.readLong();
                            data.enforceNoDataAvail();
                            registerCallbackWithEventMask(_arg05, _arg12);
                            reply.writeNoException();
                            return true;
                        case 6:
                            startWifiDisplayScan();
                            reply.writeNoException();
                            return true;
                        case 7:
                            stopWifiDisplayScan();
                            reply.writeNoException();
                            return true;
                        case 8:
                            String _arg06 = data.readString();
                            data.enforceNoDataAvail();
                            connectWifiDisplay(_arg06);
                            reply.writeNoException();
                            return true;
                        case 9:
                            disconnectWifiDisplay();
                            reply.writeNoException();
                            return true;
                        case 10:
                            String _arg07 = data.readString();
                            String _arg13 = data.readString();
                            data.enforceNoDataAvail();
                            renameWifiDisplay(_arg07, _arg13);
                            reply.writeNoException();
                            return true;
                        case 11:
                            String _arg08 = data.readString();
                            data.enforceNoDataAvail();
                            forgetWifiDisplay(_arg08);
                            reply.writeNoException();
                            return true;
                        case 12:
                            pauseWifiDisplay();
                            reply.writeNoException();
                            return true;
                        case 13:
                            resumeWifiDisplay();
                            reply.writeNoException();
                            return true;
                        case 14:
                            WifiDisplayStatus _result4 = getWifiDisplayStatus();
                            reply.writeNoException();
                            reply.writeTypedObject(_result4, 1);
                            return true;
                        case 15:
                            int[] _arg09 = data.createIntArray();
                            data.enforceNoDataAvail();
                            setUserDisabledHdrTypes(_arg09);
                            reply.writeNoException();
                            return true;
                        case 16:
                            boolean _arg010 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setAreUserDisabledHdrTypesAllowed(_arg010);
                            reply.writeNoException();
                            return true;
                        case 17:
                            boolean _result5 = areUserDisabledHdrTypesAllowed();
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        case 18:
                            int[] _result6 = getUserDisabledHdrTypes();
                            reply.writeNoException();
                            reply.writeIntArray(_result6);
                            return true;
                        case 19:
                            int _arg011 = data.readInt();
                            int[] _arg14 = data.createIntArray();
                            data.enforceNoDataAvail();
                            overrideHdrTypes(_arg011, _arg14);
                            reply.writeNoException();
                            return true;
                        case 20:
                            int _arg012 = data.readInt();
                            int _arg15 = data.readInt();
                            data.enforceNoDataAvail();
                            requestColorMode(_arg012, _arg15);
                            reply.writeNoException();
                            return true;
                        case 21:
                            VirtualDisplayConfig _arg013 = (VirtualDisplayConfig) data.readTypedObject(VirtualDisplayConfig.CREATOR);
                            IVirtualDisplayCallback _arg16 = IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                            IMediaProjection _arg2 = IMediaProjection.Stub.asInterface(data.readStrongBinder());
                            String _arg3 = data.readString();
                            data.enforceNoDataAvail();
                            int _result7 = createVirtualDisplay(_arg013, _arg16, _arg2, _arg3);
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 22:
                            IVirtualDisplayCallback _arg014 = IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                            int _arg17 = data.readInt();
                            int _arg22 = data.readInt();
                            int _arg32 = data.readInt();
                            data.enforceNoDataAvail();
                            resizeVirtualDisplay(_arg014, _arg17, _arg22, _arg32);
                            reply.writeNoException();
                            return true;
                        case 23:
                            IVirtualDisplayCallback _arg015 = IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                            Surface _arg18 = (Surface) data.readTypedObject(Surface.CREATOR);
                            data.enforceNoDataAvail();
                            setVirtualDisplaySurface(_arg015, _arg18);
                            reply.writeNoException();
                            return true;
                        case 24:
                            IVirtualDisplayCallback _arg016 = IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            releaseVirtualDisplay(_arg016);
                            reply.writeNoException();
                            return true;
                        case 25:
                            IVirtualDisplayCallback _arg017 = IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                            boolean _arg19 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setVirtualDisplayState(_arg017, _arg19);
                            reply.writeNoException();
                            return true;
                        case 26:
                            IVirtualDisplayCallback _arg018 = IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                            int _arg110 = data.readInt();
                            data.enforceNoDataAvail();
                            rotateVirtualDisplay(_arg018, _arg110);
                            reply.writeNoException();
                            return true;
                        case 27:
                            SemWifiDisplayConfig _arg019 = (SemWifiDisplayConfig) data.readTypedObject(SemWifiDisplayConfig.CREATOR);
                            IWifiDisplayConnectionCallback _arg111 = IWifiDisplayConnectionCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            connectWifiDisplayWithConfig(_arg019, _arg111);
                            reply.writeNoException();
                            return true;
                        case 28:
                            int _arg020 = data.readInt();
                            data.enforceNoDataAvail();
                            startWifiDisplayChannelScan(_arg020);
                            reply.writeNoException();
                            return true;
                        case 29:
                            int _arg021 = data.readInt();
                            int _arg112 = data.readInt();
                            data.enforceNoDataAvail();
                            startWifiDisplayChannelScanAndInterval(_arg021, _arg112);
                            reply.writeNoException();
                            return true;
                        case 30:
                            int _result8 = getScreenSharingStatus();
                            reply.writeNoException();
                            reply.writeInt(_result8);
                            return true;
                        case 31:
                            int _arg022 = data.readInt();
                            data.enforceNoDataAvail();
                            setScreenSharingStatus(_arg022);
                            reply.writeNoException();
                            return true;
                        case 32:
                            SemDlnaDevice _arg023 = (SemDlnaDevice) data.readTypedObject(SemDlnaDevice.CREATOR);
                            IBinder _arg113 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            setDlnaDevice(_arg023, _arg113);
                            reply.writeNoException();
                            return true;
                        case 33:
                            SemDlnaDevice _result9 = getDlnaDevice();
                            reply.writeNoException();
                            reply.writeTypedObject(_result9, 1);
                            return true;
                        case 34:
                            int _arg024 = data.readInt();
                            data.enforceNoDataAvail();
                            setDeviceVolume(_arg024);
                            reply.writeNoException();
                            return true;
                        case 35:
                            boolean _arg025 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setDeviceVolumeMuted(_arg025);
                            reply.writeNoException();
                            return true;
                        case 36:
                            int _arg026 = data.readInt();
                            data.enforceNoDataAvail();
                            setVolumeKeyEvent(_arg026);
                            reply.writeNoException();
                            return true;
                        case 37:
                            int _result10 = getDeviceMinVolume();
                            reply.writeNoException();
                            reply.writeInt(_result10);
                            return true;
                        case 38:
                            boolean _result11 = isDeviceVolumeMuted();
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 39:
                            int _result12 = getDeviceMaxVolume();
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            return true;
                        case 40:
                            String _arg027 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result13 = isWifiDisplayWithPinSupported(_arg027);
                            reply.writeNoException();
                            reply.writeBoolean(_result13);
                            return true;
                        case 41:
                            boolean _arg028 = data.readBoolean();
                            data.enforceNoDataAvail();
                            fitToActiveDisplay(_arg028);
                            reply.writeNoException();
                            return true;
                        case 42:
                            boolean _result14 = isFitToActiveDisplay();
                            reply.writeNoException();
                            reply.writeBoolean(_result14);
                            return true;
                        case 43:
                            int _arg029 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result15 = getPresentationOwner(_arg029);
                            reply.writeNoException();
                            reply.writeString(_result15);
                            return true;
                        case 44:
                            String _arg030 = data.readString();
                            String _arg114 = data.readString();
                            data.enforceNoDataAvail();
                            setWifiDisplayParam(_arg030, _arg114);
                            reply.writeNoException();
                            return true;
                        case 45:
                            List<SemWifiDisplayParameter> _arg031 = data.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result16 = requestSetWifiDisplayParameters(_arg031);
                            reply.writeNoException();
                            reply.writeBoolean(_result16);
                            return true;
                        case 46:
                            String _arg032 = data.readString();
                            SemWifiDisplayParameter _arg115 = (SemWifiDisplayParameter) data.readTypedObject(SemWifiDisplayParameter.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result17 = requestWifiDisplayParameter(_arg032, _arg115);
                            reply.writeNoException();
                            reply.writeBoolean(_result17);
                            return true;
                        case 47:
                            startWifiDisplayScanAutoP2P();
                            reply.writeNoException();
                            return true;
                        case 48:
                            Point _result18 = getStableDisplaySize();
                            reply.writeNoException();
                            reply.writeTypedObject(_result18, 1);
                            return true;
                        case 49:
                            String _arg033 = data.readString();
                            data.enforceNoDataAvail();
                            ParceledListSlice _result19 = getBrightnessEvents(_arg033);
                            reply.writeNoException();
                            reply.writeTypedObject(_result19, 1);
                            return true;
                        case 50:
                            ParceledListSlice _result20 = getAmbientBrightnessStats();
                            reply.writeNoException();
                            reply.writeTypedObject(_result20, 1);
                            return true;
                        case 51:
                            BrightnessConfiguration _arg034 = (BrightnessConfiguration) data.readTypedObject(BrightnessConfiguration.CREATOR);
                            int _arg116 = data.readInt();
                            String _arg23 = data.readString();
                            data.enforceNoDataAvail();
                            setBrightnessConfigurationForUser(_arg034, _arg116, _arg23);
                            reply.writeNoException();
                            return true;
                        case 52:
                            BrightnessConfiguration _arg035 = (BrightnessConfiguration) data.readTypedObject(BrightnessConfiguration.CREATOR);
                            String _arg117 = data.readString();
                            int _arg24 = data.readInt();
                            String _arg33 = data.readString();
                            data.enforceNoDataAvail();
                            setBrightnessConfigurationForDisplay(_arg035, _arg117, _arg24, _arg33);
                            reply.writeNoException();
                            return true;
                        case 53:
                            String _arg036 = data.readString();
                            int _arg118 = data.readInt();
                            data.enforceNoDataAvail();
                            BrightnessConfiguration _result21 = getBrightnessConfigurationForDisplay(_arg036, _arg118);
                            reply.writeNoException();
                            reply.writeTypedObject(_result21, 1);
                            return true;
                        case 54:
                            int _arg037 = data.readInt();
                            data.enforceNoDataAvail();
                            BrightnessConfiguration _result22 = getBrightnessConfigurationForUser(_arg037);
                            reply.writeNoException();
                            reply.writeTypedObject(_result22, 1);
                            return true;
                        case 55:
                            BrightnessConfiguration _result23 = getDefaultBrightnessConfiguration();
                            reply.writeNoException();
                            reply.writeTypedObject(_result23, 1);
                            return true;
                        case 56:
                            int _arg038 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result24 = isMinimalPostProcessingRequested(_arg038);
                            reply.writeNoException();
                            reply.writeBoolean(_result24);
                            return true;
                        case 57:
                            int _arg039 = data.readInt();
                            float _arg119 = data.readFloat();
                            data.enforceNoDataAvail();
                            setTemporaryBrightness(_arg039, _arg119);
                            reply.writeNoException();
                            return true;
                        case 58:
                            int _arg040 = data.readInt();
                            float _arg120 = data.readFloat();
                            data.enforceNoDataAvail();
                            setBrightness(_arg040, _arg120);
                            reply.writeNoException();
                            return true;
                        case 59:
                            int _arg041 = data.readInt();
                            data.enforceNoDataAvail();
                            float _result25 = getBrightness(_arg041);
                            reply.writeNoException();
                            reply.writeFloat(_result25);
                            return true;
                        case 60:
                            float _arg042 = data.readFloat();
                            data.enforceNoDataAvail();
                            setTemporaryAutoBrightnessAdjustment(_arg042);
                            reply.writeNoException();
                            return true;
                        case 61:
                            Curve _result26 = getMinimumBrightnessCurve();
                            reply.writeNoException();
                            reply.writeTypedObject(_result26, 1);
                            return true;
                        case 62:
                            int _arg043 = data.readInt();
                            data.enforceNoDataAvail();
                            BrightnessInfo _result27 = getBrightnessInfo(_arg043);
                            reply.writeNoException();
                            reply.writeTypedObject(_result27, 1);
                            return true;
                        case 63:
                            int _result28 = getPreferredWideGamutColorSpaceId();
                            reply.writeNoException();
                            reply.writeInt(_result28);
                            return true;
                        case 64:
                            int _arg044 = data.readInt();
                            Display.Mode _arg121 = (Display.Mode) data.readTypedObject(Display.Mode.CREATOR);
                            data.enforceNoDataAvail();
                            setUserPreferredDisplayMode(_arg044, _arg121);
                            reply.writeNoException();
                            return true;
                        case 65:
                            int _arg045 = data.readInt();
                            data.enforceNoDataAvail();
                            Display.Mode _result29 = getUserPreferredDisplayMode(_arg045);
                            reply.writeNoException();
                            reply.writeTypedObject(_result29, 1);
                            return true;
                        case 66:
                            int _arg046 = data.readInt();
                            data.enforceNoDataAvail();
                            Display.Mode _result30 = getSystemPreferredDisplayMode(_arg046);
                            reply.writeNoException();
                            reply.writeTypedObject(_result30, 1);
                            return true;
                        case 67:
                            HdrConversionMode _arg047 = (HdrConversionMode) data.readTypedObject(HdrConversionMode.CREATOR);
                            data.enforceNoDataAvail();
                            setHdrConversionMode(_arg047);
                            reply.writeNoException();
                            return true;
                        case 68:
                            HdrConversionMode _result31 = getHdrConversionModeSetting();
                            reply.writeNoException();
                            reply.writeTypedObject(_result31, 1);
                            return true;
                        case 69:
                            HdrConversionMode _result32 = getHdrConversionMode();
                            reply.writeNoException();
                            reply.writeTypedObject(_result32, 1);
                            return true;
                        case 70:
                            int[] _result33 = getSupportedHdrOutputTypes();
                            reply.writeNoException();
                            reply.writeIntArray(_result33);
                            return true;
                        case 71:
                            boolean _arg048 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setShouldAlwaysRespectAppRequestedMode(_arg048);
                            reply.writeNoException();
                            return true;
                        case 72:
                            boolean _result34 = shouldAlwaysRespectAppRequestedMode();
                            reply.writeNoException();
                            reply.writeBoolean(_result34);
                            return true;
                        case 73:
                            int _arg049 = data.readInt();
                            data.enforceNoDataAvail();
                            setRefreshRateSwitchingType(_arg049);
                            reply.writeNoException();
                            return true;
                        case 74:
                            int _result35 = getRefreshRateSwitchingType();
                            reply.writeNoException();
                            reply.writeInt(_result35);
                            return true;
                        case 75:
                            int _arg050 = data.readInt();
                            data.enforceNoDataAvail();
                            DisplayDecorationSupport _result36 = getDisplayDecorationSupport(_arg050);
                            reply.writeNoException();
                            reply.writeTypedObject(_result36, 1);
                            return true;
                        case 76:
                            IBinder _arg051 = data.readStrongBinder();
                            int _arg122 = data.readInt();
                            data.enforceNoDataAvail();
                            setDisplayIdToMirror(_arg051, _arg122);
                            reply.writeNoException();
                            return true;
                        case 77:
                            OverlayProperties _result37 = getOverlaySupport();
                            reply.writeNoException();
                            reply.writeTypedObject(_result37, 1);
                            return true;
                        case 78:
                            BrightnessConfiguration _arg052 = (BrightnessConfiguration) data.readTypedObject(BrightnessConfiguration.CREATOR);
                            int _arg123 = data.readInt();
                            String _arg25 = data.readString();
                            List<String> _arg34 = data.createStringArrayList();
                            List<String> _arg4 = data.createStringArrayList();
                            List<String> _arg5 = data.createStringArrayList();
                            data.enforceNoDataAvail();
                            setBrightnessConfigurationForUserWithStats(_arg052, _arg123, _arg25, _arg34, _arg4, _arg5);
                            reply.writeNoException();
                            return true;
                        case 79:
                            int _arg053 = data.readInt();
                            String _arg124 = data.readString();
                            data.enforceNoDataAvail();
                            resetBrightnessConfigurationForUser(_arg053, _arg124);
                            reply.writeNoException();
                            return true;
                        case 80:
                            int _arg054 = data.readInt();
                            float _arg125 = data.readFloat();
                            boolean _arg26 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setTemporaryBrightnessForSlowChange(_arg054, _arg125, _arg26);
                            reply.writeNoException();
                            return true;
                        case 81:
                            BrightnessConfiguration _arg055 = (BrightnessConfiguration) data.readTypedObject(BrightnessConfiguration.CREATOR);
                            int _arg126 = data.readInt();
                            String _arg27 = data.readString();
                            data.enforceNoDataAvail();
                            setBackupBrightnessConfiguration(_arg055, _arg126, _arg27);
                            reply.writeNoException();
                            return true;
                        case 82:
                            int _arg056 = data.readInt();
                            data.enforceNoDataAvail();
                            BrightnessConfiguration _result38 = getBackupBrightnessConfiguration(_arg056);
                            reply.writeNoException();
                            reply.writeTypedObject(_result38, 1);
                            return true;
                        case 83:
                            float _arg057 = data.readFloat();
                            data.enforceNoDataAvail();
                            int _result39 = convertToBrightness(_arg057);
                            reply.writeNoException();
                            reply.writeInt(_result39);
                            return true;
                        case 84:
                            IBinder _arg058 = data.readStrongBinder();
                            int _arg127 = data.readInt();
                            data.enforceNoDataAvail();
                            setDisplayStateLimit(_arg058, _arg127);
                            reply.writeNoException();
                            return true;
                        case 85:
                            int _arg059 = data.readInt();
                            float _arg128 = data.readFloat();
                            data.enforceNoDataAvail();
                            float _result40 = getAdaptiveBrightness(_arg059, _arg128);
                            reply.writeNoException();
                            reply.writeFloat(_result40);
                            return true;
                        case 86:
                            IBinder _arg060 = data.readStrongBinder();
                            String _arg129 = data.readString();
                            data.enforceNoDataAvail();
                            IRefreshRateToken _result41 = acquirePassiveModeToken(_arg060, _arg129);
                            reply.writeNoException();
                            reply.writeStrongInterface(_result41);
                            return true;
                        case 87:
                            IBinder _arg061 = data.readStrongBinder();
                            String _arg130 = data.readString();
                            data.enforceNoDataAvail();
                            IRefreshRateToken _result42 = acquireLowRefreshRateToken(_arg061, _arg130);
                            reply.writeNoException();
                            reply.writeStrongInterface(_result42);
                            return true;
                        case 88:
                            IBinder _arg062 = data.readStrongBinder();
                            int _arg131 = data.readInt();
                            String _arg28 = data.readString();
                            data.enforceNoDataAvail();
                            IRefreshRateToken _result43 = acquireRefreshRateMaxLimitToken(_arg062, _arg131, _arg28);
                            reply.writeNoException();
                            reply.writeStrongInterface(_result43);
                            return true;
                        case 89:
                            IBinder _arg063 = data.readStrongBinder();
                            int _arg132 = data.readInt();
                            String _arg29 = data.readString();
                            data.enforceNoDataAvail();
                            IRefreshRateToken _result44 = acquireRefreshRateMinLimitToken(_arg063, _arg132, _arg29);
                            reply.writeNoException();
                            reply.writeStrongInterface(_result44);
                            return true;
                        case 90:
                            long _result45 = getPrimaryPhysicalDisplayId();
                            reply.writeNoException();
                            reply.writeLong(_result45);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDisplayManager {
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

            @Override // android.hardware.display.IDisplayManager
            public DisplayInfo getDisplayInfo(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    DisplayInfo _result = (DisplayInfo) _reply.readTypedObject(DisplayInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getDisplayIds(boolean includeDisabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(includeDisabled);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isUidPresentOnDisplay(int uid, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(displayId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallback(IDisplayManagerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void registerCallbackWithEventMask(IDisplayManagerCallback callback, long eventsMask) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeLong(eventsMask);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void stopWifiDisplayScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplay(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void disconnectWifiDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void renameWifiDisplay(String address, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(alias);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void forgetWifiDisplay(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void pauseWifiDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resumeWifiDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    WifiDisplayStatus _result = (WifiDisplayStatus) _reply.readTypedObject(WifiDisplayStatus.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserDisabledHdrTypes(int[] userDisabledTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(userDisabledTypes);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setAreUserDisabledHdrTypesAllowed(boolean areUserDisabledHdrTypesAllowed) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(areUserDisabledHdrTypesAllowed);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean areUserDisabledHdrTypesAllowed() throws RemoteException {
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

            @Override // android.hardware.display.IDisplayManager
            public int[] getUserDisabledHdrTypes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void overrideHdrTypes(int displayId, int[] modes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeIntArray(modes);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void requestColorMode(int displayId, int colorMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(colorMode);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback callback, IMediaProjection projectionToken, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(virtualDisplayConfig, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeStrongInterface(projectionToken);
                    _data.writeString(packageName);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resizeVirtualDisplay(IVirtualDisplayCallback token, int width, int height, int densityDpi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(token);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(densityDpi);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplaySurface(IVirtualDisplayCallback token, Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(token);
                    _data.writeTypedObject(surface, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void releaseVirtualDisplay(IVirtualDisplayCallback token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(token);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVirtualDisplayState(IVirtualDisplayCallback token, boolean isOn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(token);
                    _data.writeBoolean(isOn);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void rotateVirtualDisplay(IVirtualDisplayCallback token, int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(token);
                    _data.writeInt(rotation);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void connectWifiDisplayWithConfig(SemWifiDisplayConfig wifidisplayConfig, IWifiDisplayConnectionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(wifidisplayConfig, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayChannelScan(int scanChannel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scanChannel);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayChannelScanAndInterval(int scanChannel, int interval) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scanChannel);
                    _data.writeInt(interval);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getScreenSharingStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setScreenSharingStatus(int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDlnaDevice(SemDlnaDevice dlnaDevice, IBinder appToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(dlnaDevice, 0);
                    _data.writeStrongBinder(appToken);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public SemDlnaDevice getDlnaDevice() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    SemDlnaDevice _result = (SemDlnaDevice) _reply.readTypedObject(SemDlnaDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDeviceVolume(int volume) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(volume);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDeviceVolumeMuted(boolean muted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(muted);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setVolumeKeyEvent(int event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(event);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getDeviceMinVolume() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isDeviceVolumeMuted() throws RemoteException {
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

            @Override // android.hardware.display.IDisplayManager
            public int getDeviceMaxVolume() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isWifiDisplayWithPinSupported(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void fitToActiveDisplay(boolean status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(status);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isFitToActiveDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public String getPresentationOwner(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setWifiDisplayParam(String key, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(value);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> parameters) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(parameters, 0);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean requestWifiDisplayParameter(String parameterGroup, SemWifiDisplayParameter parameter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(parameterGroup);
                    _data.writeTypedObject(parameter, 0);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void startWifiDisplayScanAutoP2P() throws RemoteException {
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

            @Override // android.hardware.display.IDisplayManager
            public Point getStableDisplaySize() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    Point _result = (Point) _reply.readTypedObject(Point.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public ParceledListSlice getBrightnessEvents(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUser(BrightnessConfiguration c, int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(c, 0);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForDisplay(BrightnessConfiguration c, String uniqueDisplayId, int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(c, 0);
                    _data.writeString(uniqueDisplayId);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBrightnessConfigurationForDisplay(String uniqueDisplayId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uniqueDisplayId);
                    _data.writeInt(userId);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    BrightnessConfiguration _result = (BrightnessConfiguration) _reply.readTypedObject(BrightnessConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBrightnessConfigurationForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    BrightnessConfiguration _result = (BrightnessConfiguration) _reply.readTypedObject(BrightnessConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    BrightnessConfiguration _result = (BrightnessConfiguration) _reply.readTypedObject(BrightnessConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean isMinimalPostProcessingRequested(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightness(int displayId, float brightness) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(brightness);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightness(int displayId, float brightness) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(brightness);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getBrightness(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryAutoBrightnessAdjustment(float adjustment) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(adjustment);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Curve getMinimumBrightnessCurve() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    Curve _result = (Curve) _reply.readTypedObject(Curve.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessInfo getBrightnessInfo(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    BrightnessInfo _result = (BrightnessInfo) _reply.readTypedObject(BrightnessInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getPreferredWideGamutColorSpaceId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setUserPreferredDisplayMode(int displayId, Display.Mode mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(mode, 0);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Display.Mode getUserPreferredDisplayMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    Display.Mode _result = (Display.Mode) _reply.readTypedObject(Display.Mode.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public Display.Mode getSystemPreferredDisplayMode(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    Display.Mode _result = (Display.Mode) _reply.readTypedObject(Display.Mode.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setHdrConversionMode(HdrConversionMode hdrConversionMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(hdrConversionMode, 0);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public HdrConversionMode getHdrConversionModeSetting() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    HdrConversionMode _result = (HdrConversionMode) _reply.readTypedObject(HdrConversionMode.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public HdrConversionMode getHdrConversionMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    HdrConversionMode _result = (HdrConversionMode) _reply.readTypedObject(HdrConversionMode.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int[] getSupportedHdrOutputTypes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setShouldAlwaysRespectAppRequestedMode(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public boolean shouldAlwaysRespectAppRequestedMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setRefreshRateSwitchingType(int newValue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newValue);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int getRefreshRateSwitchingType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public DisplayDecorationSupport getDisplayDecorationSupport(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    DisplayDecorationSupport _result = (DisplayDecorationSupport) _reply.readTypedObject(DisplayDecorationSupport.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayIdToMirror(IBinder token, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(displayId);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public OverlayProperties getOverlaySupport() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    OverlayProperties _result = (OverlayProperties) _reply.readTypedObject(OverlayProperties.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration c, int userId, String packageName, List<String> weights, List<String> timeWeights, List<String> continuityWeights) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(c, 0);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeStringList(weights);
                    _data.writeStringList(timeWeights);
                    _data.writeStringList(continuityWeights);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void resetBrightnessConfigurationForUser(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setTemporaryBrightnessForSlowChange(int displayId, float brightness, boolean slowChange) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(brightness);
                    _data.writeBoolean(slowChange);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setBackupBrightnessConfiguration(BrightnessConfiguration config, int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public BrightnessConfiguration getBackupBrightnessConfiguration(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    BrightnessConfiguration _result = (BrightnessConfiguration) _reply.readTypedObject(BrightnessConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public int convertToBrightness(float nits) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(nits);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public void setDisplayStateLimit(IBinder lock, int stateLimit) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(stateLimit);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public float getAdaptiveBrightness(int displayId, float lux) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(lux);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquirePassiveModeToken(IBinder token, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(tag);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                    IRefreshRateToken _result = IRefreshRateToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireLowRefreshRateToken(IBinder token, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(tag);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    IRefreshRateToken _result = IRefreshRateToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder token, int refreshRate, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(refreshRate);
                    _data.writeString(tag);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    IRefreshRateToken _result = IRefreshRateToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder token, int refreshRate, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(refreshRate);
                    _data.writeString(tag);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    IRefreshRateToken _result = IRefreshRateToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManager
            public long getPrimaryPhysicalDisplayId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 89;
        }
    }
}
