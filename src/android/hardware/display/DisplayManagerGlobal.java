package android.hardware.display;

import android.app.ActivityThread;
import android.app.PropertyInvalidatedCache;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.hardware.OverlayProperties;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.IDisplayManager;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IHbmBrightnessCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.display.VirtualDisplay;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Trace;
import android.sec.clipboard.data.ClipboardConstants;
import android.sysprop.DisplayProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.DisplayInfo;
import android.view.Surface;
import com.android.server.LocalServices;
import com.android.server.display.feature.flags.Flags;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final class DisplayManagerGlobal {
    private static final String CACHE_KEY_DISPLAY_INFO_API = "display_info";
    private static final boolean DEBUG;
    public static final int EVENT_CONNECTIONSTATUS_CHANGED = 8;
    public static final int EVENT_DISPLAY_ADDED = 1;
    public static final int EVENT_DISPLAY_BASIC_CHANGED = 2;
    public static final int EVENT_DISPLAY_BRIGHTNESS_CHANGED = 4;
    public static final int EVENT_DISPLAY_COMMITTED_STATE_CHANGED = 10;
    public static final int EVENT_DISPLAY_CONNECTED = 6;
    public static final int EVENT_DISPLAY_DISCONNECTED = 7;
    public static final int EVENT_DISPLAY_HDR_SDR_RATIO_CHANGED = 5;
    public static final int EVENT_DISPLAY_REFRESH_RATE_CHANGED = 8;
    public static final int EVENT_DISPLAY_REMOVED = 3;
    public static final int EVENT_DISPLAY_STATE_CHANGED = 9;
    public static final int EVENT_REMOTE_DISPLAY_ROTATION_CHANGED = 10;
    public static final int EVENT_REMOTE_DISPLAY_STATE_CHANGED = 9;
    public static final int EVENT_VOLUME_KEY_DOWN = 12;
    public static final int EVENT_VOLUME_KEY_UP = 13;
    public static final int EVENT_VOLUME_LEVEL_CHANGED = 11;
    public static final int EVENT_VOLUME_MUTE = 14;
    public static final int EVENT_VOLUME_UNMUTE = 15;
    public static final int EVENT_WIFIDISPLAY_PARAMETERS_CHANGED = 16;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_ADDED = 1;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_BASIC_CHANGED = 2;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_BRIGHTNESS_CHANGED = 8;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_COMMITTED_STATE_CHANGED = 512;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_CONNECTION_CHANGED = 32;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_HDR_SDR_RATIO_CHANGED = 16;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_REFRESH_RATE = 64;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_REMOVED = 4;
    public static final long INTERNAL_EVENT_FLAG_DISPLAY_STATE = 128;
    public static final long INTERNAL_EVENT_FLAG_TOPOLOGY_UPDATED = 256;
    private static final String TAG = "DisplayManager";
    private static final boolean USE_CACHE = false;
    private static DisplayManagerGlobal sInstance;
    private DisplayManagerCallback mCallback;
    private PropertyInvalidatedCache<Integer, DisplayInfo> mDisplayCache;
    private int[] mDisplayIdCache;
    private final IDisplayManager mDm;
    private HbmBrightnessCallback mHbmBrightnessCallback;
    private float mNativeCallbackReportedRefreshRate;
    private final OverlayProperties mOverlayProperties;
    private final ColorSpace mWideColorSpace;
    private WifiDisplayConnectionCallback mWifiDisplayConnectionCallback;
    private int mWifiDisplayScanNestCount;
    private static final String EXTRA_LOGGING_PACKAGE_NAME = DisplayProperties.debug_vri_package().orElse(null);
    private static String sCurrentPackageName = ActivityThread.currentPackageName();
    private static boolean sExtraDisplayListenerLogging = initExtraLogging();
    private boolean mDispatchNativeCallbacks = false;
    private final Object mLock = new Object();
    private long mRegisteredInternalEventFlag = 0;
    private final CopyOnWriteArrayList<DisplayListenerDelegate> mDisplayListeners = new CopyOnWriteArrayList<>();
    private final ArrayList<DisplayVolumeListenerDelegate> mDisplayVolumeListeners = new ArrayList<>();
    private final ArrayList<DisplayVolumeKeyListenerDelegate> mDisplayVolumeKeyListeners = new ArrayList<>();
    private final ArrayList<WifiDisplayParameterListenerDelegate> mWifiDisplayParameterListeners = new ArrayList<>();
    private final ArrayList<DeviceListenerDelegate> mDeviceListeners = new ArrayList<>();
    private final CopyOnWriteArrayList<DisplayTopologyListenerDelegate> mTopologyListeners = new CopyOnWriteArrayList<>();
    private final SparseArray<DisplayInfo> mDisplayInfoCache = new SparseArray<>();
    private final Binder mToken = new Binder();
    private boolean mShouldImplicitlyRegisterRrChanges = false;
    private boolean mHbmBrightnessCallbackRegistered = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InternalEventFlag {
    }

    private static native void nSignalNativeCallbacks(float f);

    static {
        DEBUG = DisplayManager.DEBUG || sExtraDisplayListenerLogging;
    }

    public DisplayManagerGlobal(IDisplayManager iDisplayManager) {
        PropertyInvalidatedCache.Args maxEntries = new PropertyInvalidatedCache.Args("system_server").maxEntries(8);
        this.mDisplayCache = new PropertyInvalidatedCache<Integer, DisplayInfo>(maxEntries.api(CACHE_KEY_DISPLAY_INFO_API).isolateUids(false), CACHE_KEY_DISPLAY_INFO_API, null) { // from class: android.hardware.display.DisplayManagerGlobal.1
            @Override // android.app.PropertyInvalidatedCache
            public DisplayInfo recompute(Integer num) {
                try {
                    return DisplayManagerGlobal.this.mDm.getDisplayInfo(num.intValue());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mDm = iDisplayManager;
        initExtraLogging();
        try {
            this.mWideColorSpace = ColorSpace.get(ColorSpace.Named.values()[iDisplayManager.getPreferredWideGamutColorSpaceId()]);
            this.mOverlayProperties = iDisplayManager.getOverlaySupport();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static DisplayManagerGlobal getInstance() {
        DisplayManagerGlobal displayManagerGlobal;
        IBinder service;
        synchronized (DisplayManagerGlobal.class) {
            if (sInstance == null && (service = ServiceManager.getService(Context.DISPLAY_SERVICE)) != null) {
                sInstance = new DisplayManagerGlobal(IDisplayManager.Stub.asInterface(service));
            }
            displayManagerGlobal = sInstance;
        }
        return displayManagerGlobal;
    }

    public DisplayInfo getDisplayInfo(int i) {
        DisplayInfo displayInfoLocked;
        synchronized (this.mLock) {
            displayInfoLocked = getDisplayInfoLocked(i);
        }
        return displayInfoLocked;
    }

    private DisplayInfo getDisplayInfoLocked(int i) {
        DisplayInfo displayInfo;
        PropertyInvalidatedCache<Integer, DisplayInfo> propertyInvalidatedCache = this.mDisplayCache;
        if (propertyInvalidatedCache != null) {
            displayInfo = propertyInvalidatedCache.query(Integer.valueOf(i));
        } else {
            try {
                displayInfo = this.mDm.getDisplayInfo(i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                displayInfo = null;
            }
        }
        if (displayInfo == null) {
            return null;
        }
        CompatSandbox.applyDisplaySandboxingIfNeeded(displayInfo);
        registerCallbackIfNeededLocked();
        if (DEBUG) {
            Log.d(TAG, "getDisplayInfo: displayId=" + i + ", info=" + displayInfo);
        }
        return displayInfo;
    }

    public int[] getDisplayIds() {
        return getDisplayIds(false);
    }

    public int[] getDisplayIds(boolean z) {
        int[] displayIds;
        try {
            synchronized (this.mLock) {
                displayIds = this.mDm.getDisplayIds(z);
                registerCallbackIfNeededLocked();
            }
            return displayIds;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUidPresentOnDisplay(int i, int i2) {
        try {
            return this.mDm.isUidPresentOnDisplay(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Display getCompatibleDisplay(int i, DisplayAdjustments displayAdjustments) {
        DisplayInfo displayInfo = getDisplayInfo(i);
        if (displayInfo == null) {
            return null;
        }
        return new Display(this, i, displayInfo, displayAdjustments);
    }

    public Display getCompatibleDisplay(int i, Resources resources) {
        DisplayInfo displayInfo = getDisplayInfo(i);
        if (displayInfo == null) {
            return null;
        }
        return new Display(this, i, displayInfo, resources);
    }

    public Display getRealDisplay(int i) {
        return getCompatibleDisplay(i, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    public void registerDisplayListener(DisplayManager.DisplayListener displayListener, Handler handler, long j, String str, boolean z) {
        registerDisplayListener(displayListener, new HandlerExecutor(new Handler(getLooperForHandler(handler))), j, str, z);
    }

    public void registerDisplayListener(DisplayManager.DisplayListener displayListener, Handler handler, long j, String str) {
        registerDisplayListener(displayListener, handler, j, str, true);
    }

    public void registerDisplayListener(DisplayManager.DisplayListener displayListener, Executor executor, long j, String str, boolean z) {
        if (displayListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        if (j == 0) {
            throw new IllegalArgumentException("The set of events to listen to must not be empty.");
        }
        if (extraLogging()) {
            Slog.i(TAG, "Registering Display Listener: " + Long.toBinaryString(j) + ", packageName: " + str);
        }
        synchronized (this.mLock) {
            int findDisplayListenerLocked = findDisplayListenerLocked(displayListener);
            if (findDisplayListenerLocked < 0) {
                this.mDisplayListeners.add(new DisplayListenerDelegate(displayListener, executor, j, str, z));
                registerCallbackIfNeededLocked();
            } else {
                this.mDisplayListeners.get(findDisplayListenerLocked).setEventsMask(j);
            }
            updateCallbackIfNeededLocked();
            maybeLogAllDisplayListeners();
        }
    }

    public void registerForRefreshRateChanges() {
        if (Flags.delayImplicitRrRegistrationUntilRrAccessed()) {
            synchronized (this.mLock) {
                if (!this.mShouldImplicitlyRegisterRrChanges) {
                    this.mShouldImplicitlyRegisterRrChanges = true;
                    Slog.i(TAG, "Implicitly registering for refresh rate");
                    updateCallbackIfNeededLocked();
                }
            }
        }
    }

    public void unregisterDisplayListener(DisplayManager.DisplayListener displayListener) {
        if (displayListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        if (extraLogging()) {
            Slog.i(TAG, "Unregistering Display Listener: " + displayListener);
        }
        synchronized (this.mLock) {
            int findDisplayListenerLocked = findDisplayListenerLocked(displayListener);
            if (findDisplayListenerLocked >= 0) {
                this.mDisplayListeners.get(findDisplayListenerLocked).clearEvents();
                this.mDisplayListeners.remove(findDisplayListenerLocked);
                updateCallbackIfNeededLocked();
            }
        }
        maybeLogAllDisplayListeners();
    }

    private void maybeLogAllDisplayListeners() {
        if (extraLogging()) {
            Slog.i(TAG, "Currently Registered Display Listeners:");
            for (int i = 0; i < this.mDisplayListeners.size(); i++) {
                Slog.i(TAG, i + ": " + this.mDisplayListeners.get(i));
            }
        }
    }

    private void maybeLogAllTopologyListeners() {
        if (extraLogging()) {
            Slog.i(TAG, "Currently registered display topology listeners:");
            Iterator<DisplayTopologyListenerDelegate> it = this.mTopologyListeners.iterator();
            int i = 0;
            while (it.hasNext()) {
                Slog.i(TAG, i + ": " + it.next());
                i++;
            }
        }
    }

    public void handleDisplayChangeFromWindowManager(int i) {
        handleDisplayEvent(i, 2, true);
    }

    private static Looper getLooperForHandler(Handler handler) {
        Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        if (looper != null) {
            return looper;
        }
        throw new RuntimeException("Could not get Looper for the UI thread.");
    }

    private int findDisplayListenerLocked(DisplayManager.DisplayListener displayListener) {
        int size = this.mDisplayListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mDisplayListeners.get(i).mListener == displayListener) {
                return i;
            }
        }
        return -1;
    }

    private long calculateEventsMaskLocked() {
        int size = this.mDisplayListeners.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            DisplayListenerDelegate displayListenerDelegate = this.mDisplayListeners.get(i);
            if (!Flags.delayImplicitRrRegistrationUntilRrAccessed() || this.mShouldImplicitlyRegisterRrChanges) {
                displayListenerDelegate.implicitlyRegisterForRRChanges();
            }
            j |= displayListenerDelegate.mInternalEventFlagsMask;
        }
        if (this.mDispatchNativeCallbacks) {
            j |= 71;
        }
        return !this.mTopologyListeners.isEmpty() ? j | 256 : j;
    }

    private DisplayTopologyListenerDelegate findTopologyListenerLocked(Consumer<DisplayTopology> consumer) {
        Iterator<DisplayTopologyListenerDelegate> it = this.mTopologyListeners.iterator();
        while (it.hasNext()) {
            DisplayTopologyListenerDelegate next = it.next();
            if (next.mListener == consumer) {
                return next;
            }
        }
        return null;
    }

    private void registerCallbackIfNeededLocked() {
        if (this.mCallback == null) {
            this.mCallback = new DisplayManagerCallback();
            updateCallbackIfNeededLocked();
        }
    }

    private void updateCallbackIfNeededLocked() {
        long calculateEventsMaskLocked = calculateEventsMaskLocked();
        if (DEBUG) {
            Log.d(TAG, "Mask for listener: " + calculateEventsMaskLocked);
        }
        if (calculateEventsMaskLocked != this.mRegisteredInternalEventFlag) {
            try {
                this.mDm.registerCallbackWithEventMask(this.mCallback, calculateEventsMaskLocked);
                this.mRegisteredInternalEventFlag = calculateEventsMaskLocked;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayEvent(int i, int i2, boolean z) {
        DisplayInfo displayInfoLocked;
        DisplayInfo displayInfoLocked2;
        synchronized (this.mLock) {
            displayInfoLocked = getDisplayInfoLocked(i);
            if ((i2 == 2 || i2 == 8) && this.mDispatchNativeCallbacks && ((i == 0 || (CoreRune.FW_VRR_MULTI_DISPLAY && i == 1)) && (displayInfoLocked2 = getDisplayInfoLocked(i)) != null && this.mNativeCallbackReportedRefreshRate != displayInfoLocked2.getRefreshRate())) {
                float refreshRate = displayInfoLocked2.getRefreshRate();
                this.mNativeCallbackReportedRefreshRate = refreshRate;
                nSignalNativeCallbacks(refreshRate);
            }
        }
        Iterator<DisplayListenerDelegate> it = this.mDisplayListeners.iterator();
        while (it.hasNext()) {
            it.next().sendDisplayEvent(i, i2, displayInfoLocked, z);
        }
    }

    public void enableConnectedDisplay(int i) {
        try {
            this.mDm.enableConnectedDisplay(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Error trying to enable external display", e);
        }
    }

    public void disableConnectedDisplay(int i) {
        try {
            this.mDm.disableConnectedDisplay(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Error trying to enable external display", e);
        }
    }

    public boolean requestDisplayPower(int i, int i2) {
        try {
            return this.mDm.requestDisplayPower(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Error trying to request display power: state=" + i2, e);
            return false;
        }
    }

    public void startWifiDisplayScan() {
        synchronized (this.mLock) {
            registerCallbackIfNeededLocked();
            try {
                this.mDm.startWifiDisplayScan();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void stopWifiDisplayScan() {
        synchronized (this.mLock) {
            try {
                try {
                    this.mDm.stopWifiDisplayScan();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void connectWifiDisplay(String str) {
        if (str == null) {
            throw new IllegalArgumentException("deviceAddress must not be null");
        }
        try {
            this.mDm.connectWifiDisplay(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void pauseWifiDisplay() {
        try {
            this.mDm.pauseWifiDisplay();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resumeWifiDisplay() {
        try {
            this.mDm.resumeWifiDisplay();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disconnectWifiDisplay() {
        try {
            this.mDm.disconnectWifiDisplay();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void renameWifiDisplay(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("deviceAddress must not be null");
        }
        try {
            this.mDm.renameWifiDisplay(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forgetWifiDisplay(String str) {
        if (str == null) {
            throw new IllegalArgumentException("deviceAddress must not be null");
        }
        try {
            this.mDm.forgetWifiDisplay(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WifiDisplayStatus getWifiDisplayStatus() {
        try {
            return this.mDm.getWifiDisplayStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserDisabledHdrTypes(int[] iArr) {
        try {
            this.mDm.setUserDisabledHdrTypes(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAreUserDisabledHdrTypesAllowed(boolean z) {
        try {
            this.mDm.setAreUserDisabledHdrTypesAllowed(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areUserDisabledHdrTypesAllowed() {
        try {
            return this.mDm.areUserDisabledHdrTypesAllowed();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getUserDisabledHdrTypes() {
        try {
            return this.mDm.getUserDisabledHdrTypes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetImplicitRefreshRateCallbackStatus() {
        if (Flags.delayImplicitRrRegistrationUntilRrAccessed()) {
            synchronized (this.mLock) {
                this.mShouldImplicitlyRegisterRrChanges = false;
            }
        }
    }

    public void overrideHdrTypes(int i, int[] iArr) {
        try {
            this.mDm.overrideHdrTypes(i, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestColorMode(int i, int i2) {
        try {
            this.mDm.requestColorMode(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public VirtualDisplay createSpegVirtualDisplay(String str, int i) {
        if (!CoreRune.SYSFW_APP_SPEG) {
            return null;
        }
        VirtualDisplayCallback virtualDisplayCallback = new VirtualDisplayCallback(null, null);
        int createSpegVirtualDisplay = ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).createSpegVirtualDisplay(str, i, virtualDisplayCallback);
        if (createSpegVirtualDisplay < 0) {
            Log.e(DisplayManager.TAG_SPEG, "Could not create speg display for " + str);
            return null;
        }
        Display realDisplay = getRealDisplay(createSpegVirtualDisplay);
        if (realDisplay == null) {
            Log.wtf(DisplayManager.TAG_SPEG, "Could not obtain display info for created displayId: " + createSpegVirtualDisplay);
            try {
                this.mDm.releaseVirtualDisplay(virtualDisplayCallback);
                return null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new VirtualDisplay(this, realDisplay, virtualDisplayCallback, null);
    }

    public VirtualDisplay createVirtualDisplay(Context context, MediaProjection mediaProjection, VirtualDisplayConfig virtualDisplayConfig, VirtualDisplay.Callback callback, Executor executor) {
        VirtualDisplayCallback virtualDisplayCallback = new VirtualDisplayCallback(callback, executor);
        try {
            return createVirtualDisplayWrapper(virtualDisplayConfig, virtualDisplayCallback, this.mDm.createVirtualDisplay(virtualDisplayConfig, virtualDisplayCallback, mediaProjection != null ? mediaProjection.getProjection() : null, context.getPackageName()));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public VirtualDisplay createVirtualDisplayWrapper(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, int i) {
        if (i < 0) {
            Log.e(TAG, "Could not create virtual display: " + virtualDisplayConfig.getName());
            return null;
        }
        Display realDisplay = getRealDisplay(i);
        if (realDisplay == null) {
            Log.wtf(TAG, "Could not obtain display info for newly created virtual display: " + virtualDisplayConfig.getName());
            try {
                this.mDm.releaseVirtualDisplay(iVirtualDisplayCallback);
                return null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new VirtualDisplay(this, realDisplay, iVirtualDisplayCallback, virtualDisplayConfig.getSurface());
    }

    public void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) {
        try {
            this.mDm.setVirtualDisplaySurface(iVirtualDisplayCallback, surface);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) {
        try {
            this.mDm.resizeVirtualDisplay(iVirtualDisplayCallback, i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) {
        try {
            this.mDm.releaseVirtualDisplay(iVirtualDisplayCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setVirtualDisplayRotation(IVirtualDisplayCallback iVirtualDisplayCallback, int i) {
        try {
            this.mDm.setVirtualDisplayRotation(iVirtualDisplayCallback, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Point getStableDisplaySize() {
        try {
            return this.mDm.getStableDisplaySize();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<BrightnessChangeEvent> getBrightnessEvents(String str) {
        try {
            ParceledListSlice brightnessEvents = this.mDm.getBrightnessEvents(str);
            if (brightnessEvents == null) {
                return Collections.EMPTY_LIST;
            }
            return brightnessEvents.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BrightnessInfo getBrightnessInfo(int i) {
        try {
            return this.mDm.getBrightnessInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ColorSpace getPreferredWideGamutColorSpace() {
        return this.mWideColorSpace;
    }

    public OverlayProperties getOverlaySupport() {
        return this.mOverlayProperties;
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) {
        try {
            this.mDm.setBrightnessConfigurationForUser(brightnessConfiguration, i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) {
        try {
            this.mDm.setBrightnessConfigurationForDisplay(brightnessConfiguration, str, i, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) {
        try {
            return this.mDm.getBrightnessConfigurationForDisplay(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
        try {
            return this.mDm.getBrightnessConfigurationForUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
        try {
            return this.mDm.getDefaultBrightnessConfiguration();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMinimalPostProcessingRequested(int i) {
        try {
            return this.mDm.isMinimalPostProcessingRequested(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTemporaryBrightness(int i, float f) {
        try {
            this.mDm.setTemporaryBrightness(i, f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBrightness(int i, float f) {
        try {
            this.mDm.setBrightness(i, f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public DisplayDecorationSupport getDisplayDecorationSupport(int i) {
        try {
            return this.mDm.getDisplayDecorationSupport(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getBrightness(int i) {
        try {
            return this.mDm.getBrightness(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        try {
            this.mDm.setTemporaryAutoBrightnessAdjustment(f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Pair<float[], float[]> getMinimumBrightnessCurve() {
        try {
            Curve minimumBrightnessCurve = this.mDm.getMinimumBrightnessCurve();
            return Pair.create(minimumBrightnessCurve.getX(), minimumBrightnessCurve.getY());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AmbientBrightnessDayStats> getAmbientBrightnessStats() {
        try {
            ParceledListSlice ambientBrightnessStats = this.mDm.getAmbientBrightnessStats();
            if (ambientBrightnessStats == null) {
                return Collections.EMPTY_LIST;
            }
            return ambientBrightnessStats.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserPreferredDisplayMode(int i, Display.Mode mode) {
        try {
            this.mDm.setUserPreferredDisplayMode(i, mode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Display.Mode getUserPreferredDisplayMode(int i) {
        try {
            return this.mDm.getUserPreferredDisplayMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Display.Mode getSystemPreferredDisplayMode(int i) {
        try {
            return this.mDm.getSystemPreferredDisplayMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHdrConversionMode(HdrConversionMode hdrConversionMode) {
        try {
            this.mDm.setHdrConversionMode(hdrConversionMode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public HdrConversionMode getHdrConversionModeSetting() {
        try {
            return this.mDm.getHdrConversionModeSetting();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public HdrConversionMode getHdrConversionMode() {
        try {
            return this.mDm.getHdrConversionMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getSupportedHdrOutputTypes() {
        try {
            return this.mDm.getSupportedHdrOutputTypes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
        try {
            this.mDm.setShouldAlwaysRespectAppRequestedMode(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
        try {
            return this.mDm.shouldAlwaysRespectAppRequestedMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRefreshRateSwitchingType(int i) {
        try {
            this.mDm.setRefreshRateSwitchingType(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRefreshRateSwitchingType() {
        try {
            return this.mDm.getRefreshRateSwitchingType();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestDisplayModes(int i, int[] iArr) {
        try {
            this.mDm.requestDisplayModes(this.mToken, i, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getHighestHdrSdrRatio(int i) {
        try {
            return this.mDm.getHighestHdrSdrRatio(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float[] getDozeBrightnessSensorValueToBrightness(int i) {
        try {
            return this.mDm.getDozeBrightnessSensorValueToBrightness(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getDefaultDozeBrightness(int i) {
        try {
            return this.mDm.getDefaultDozeBrightness(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public DisplayTopology getDisplayTopology() {
        try {
            return this.mDm.getDisplayTopology();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDisplayTopology(DisplayTopology displayTopology) {
        if (displayTopology == null) {
            throw new IllegalArgumentException("Topology must not be null");
        }
        try {
            this.mDm.setDisplayTopology(displayTopology);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTopologyListener(Executor executor, Consumer<DisplayTopology> consumer, String str) {
        if (Flags.displayTopology()) {
            if (consumer == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            if (extraLogging()) {
                Slog.i(TAG, "Registering display topology listener: packageName=" + str);
            }
            synchronized (this.mLock) {
                if (findTopologyListenerLocked(consumer) == null) {
                    this.mTopologyListeners.add(new DisplayTopologyListenerDelegate(consumer, executor, str));
                    registerCallbackIfNeededLocked();
                    updateCallbackIfNeededLocked();
                }
                maybeLogAllTopologyListeners();
            }
        }
    }

    public void unregisterTopologyListener(Consumer<DisplayTopology> consumer) {
        if (Flags.displayTopology()) {
            if (consumer == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            if (extraLogging()) {
                Slog.i(TAG, "Unregistering display topology listener: " + consumer);
            }
            synchronized (this.mLock) {
                DisplayTopologyListenerDelegate findTopologyListenerLocked = findTopologyListenerLocked(consumer);
                if (findTopologyListenerLocked != null) {
                    this.mTopologyListeners.remove(findTopologyListenerLocked);
                    updateCallbackIfNeededLocked();
                }
            }
            maybeLogAllTopologyListeners();
        }
    }

    public void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) {
        try {
            this.mDm.rotateVirtualDisplay(iVirtualDisplayCallback, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void connectWifiDisplay(SemWifiDisplayConfig semWifiDisplayConfig, DisplayManager.SemWifiDisplayConnectionCallback semWifiDisplayConnectionCallback, Handler handler) {
        WifiDisplayConnectionCallback wifiDisplayConnectionCallback;
        if (semWifiDisplayConnectionCallback == null) {
            wifiDisplayConnectionCallback = null;
        } else {
            try {
                wifiDisplayConnectionCallback = new WifiDisplayConnectionCallback(this, semWifiDisplayConnectionCallback, handler);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mWifiDisplayConnectionCallback = wifiDisplayConnectionCallback;
        this.mDm.connectWifiDisplayWithConfig(semWifiDisplayConfig, wifiDisplayConnectionCallback);
    }

    public void startWifiDisplayScan(int i) {
        try {
            this.mDm.startWifiDisplayChannelScan(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed startWifiDisplayChannelScan ", e);
        }
    }

    public void startWifiDisplayScan(int i, int i2) {
        try {
            this.mDm.startWifiDisplayChannelScanAndInterval(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed startWifiDisplayChannelScan ", e);
        }
    }

    public int getScreenSharingStatus() {
        try {
            return this.mDm.getScreenSharingStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed getScreenSharingStatus ", e);
            return -1;
        }
    }

    public void setScreenSharingStatus(int i) {
        try {
            this.mDm.setScreenSharingStatus(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed setScreenSharingStatus ", e);
        }
    }

    public void setDlnaDevice(SemDlnaDevice semDlnaDevice) {
        try {
            this.mDm.setDlnaDevice(semDlnaDevice, new Binder());
        } catch (RemoteException e) {
            Log.e(TAG, "Failed setDlnaDevice ", e);
        }
    }

    public SemDlnaDevice getDlnaDevice() {
        try {
            return this.mDm.getDlnaDevice();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed getDlnaDevice ", e);
            return new SemDlnaDevice();
        }
    }

    public void setDeviceVolume(int i) {
        try {
            this.mDm.setDeviceVolume(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed setDeviceVolume ", e);
        }
    }

    public void setDeviceVolumeMuted(boolean z) {
        try {
            this.mDm.setDeviceVolumeMuted(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed setDeviceVolumeMuted ", e);
        }
    }

    public void setVolumeKeyEvent(int i) {
        try {
            this.mDm.setVolumeKeyEvent(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed setVolumeKeyEvent ", e);
        }
    }

    public int getDeviceMaxVolume() {
        try {
            return this.mDm.getDeviceMaxVolume();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed getDeviceMaxVolume ", e);
            return -1;
        }
    }

    public int getDeviceMinVolume() {
        try {
            return this.mDm.getDeviceMinVolume();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed getDeviceMinVolume ", e);
            return -1;
        }
    }

    public boolean isDeviceVolumeMuted() {
        try {
            return this.mDm.isDeviceVolumeMuted();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed isDeviceVolumeMuted ", e);
            return false;
        }
    }

    public boolean requestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) {
        try {
            return this.mDm.requestSetWifiDisplayParameters(list);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed requestSetWifiDisplayParameters ", e);
            return false;
        }
    }

    public boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
        try {
            return this.mDm.requestWifiDisplayParameter(str, semWifiDisplayParameter);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed requestSetWifiDisplayParameters ", e);
            return false;
        }
    }

    public void registerDisplayVolumeListener(SemDisplayVolumeListener semDisplayVolumeListener, Handler handler) {
        if (this.mDisplayVolumeListeners != null) {
            Log.d(TAG, "registerDisplayVolumeListener");
            if (semDisplayVolumeListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                if (findDisplayVolumeListnerLocked(semDisplayVolumeListener) < 0) {
                    Log.d(TAG, "registerDisplayVolumeListener index < 0");
                    this.mDisplayVolumeListeners.add(new DisplayVolumeListenerDelegate(semDisplayVolumeListener, handler));
                    registerCallbackIfNeededLocked();
                }
            }
        }
    }

    public void unregisterDisplayVolumeListener(SemDisplayVolumeListener semDisplayVolumeListener) {
        if (this.mDisplayVolumeListeners != null) {
            Log.d(TAG, "unregisterDisplayVolumeListener");
            if (semDisplayVolumeListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                int findDisplayVolumeListnerLocked = findDisplayVolumeListnerLocked(semDisplayVolumeListener);
                if (findDisplayVolumeListnerLocked >= 0) {
                    Log.d(TAG, "unregisterDisplayVolumeListener index >= 0");
                    this.mDisplayVolumeListeners.get(findDisplayVolumeListnerLocked).clearEvents();
                    this.mDisplayVolumeListeners.remove(findDisplayVolumeListnerLocked);
                }
            }
        }
    }

    private int findDisplayVolumeListnerLocked(SemDisplayVolumeListener semDisplayVolumeListener) {
        ArrayList<DisplayVolumeListenerDelegate> arrayList = this.mDisplayVolumeListeners;
        if (arrayList == null) {
            return -1;
        }
        int size = arrayList.size();
        Log.d(TAG, "findDisplayVolumeListnerLocked numListeners: " + size);
        for (int i = 0; i < size; i++) {
            if (this.mDisplayVolumeListeners.get(i).mListener == semDisplayVolumeListener) {
                return i;
            }
        }
        return -1;
    }

    public void registerDisplayVolumeKeyListener(SemDisplayVolumeKeyListener semDisplayVolumeKeyListener, Handler handler) {
        if (this.mDisplayVolumeKeyListeners != null) {
            Log.d(TAG, "registerDisplayVolumeKeyListener");
            if (semDisplayVolumeKeyListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                if (findDisplayVolumeKeyListnerLocked(semDisplayVolumeKeyListener) < 0) {
                    Log.d(TAG, "registerDisplayVolumeKeyListener index < 0");
                    this.mDisplayVolumeKeyListeners.add(new DisplayVolumeKeyListenerDelegate(semDisplayVolumeKeyListener, handler));
                    registerCallbackIfNeededLocked();
                }
            }
        }
    }

    public void unregisterDisplayVolumeKeyListener(SemDisplayVolumeKeyListener semDisplayVolumeKeyListener) {
        if (this.mDisplayVolumeKeyListeners != null) {
            Log.d(TAG, "unregisterDisplayVolumeKeyListener");
            if (semDisplayVolumeKeyListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                int findDisplayVolumeKeyListnerLocked = findDisplayVolumeKeyListnerLocked(semDisplayVolumeKeyListener);
                if (findDisplayVolumeKeyListnerLocked >= 0) {
                    Log.d(TAG, "unregisterDisplayVolumeKeyListener index >= 0");
                    this.mDisplayVolumeKeyListeners.get(findDisplayVolumeKeyListnerLocked).clearEvents();
                    this.mDisplayVolumeKeyListeners.remove(findDisplayVolumeKeyListnerLocked);
                }
            }
        }
    }

    private int findDisplayVolumeKeyListnerLocked(SemDisplayVolumeKeyListener semDisplayVolumeKeyListener) {
        ArrayList<DisplayVolumeKeyListenerDelegate> arrayList = this.mDisplayVolumeKeyListeners;
        if (arrayList == null) {
            return -1;
        }
        int size = arrayList.size();
        Log.d(TAG, "findDisplayVolumeKeyListnerLocked numListeners: " + size);
        for (int i = 0; i < size; i++) {
            if (this.mDisplayVolumeKeyListeners.get(i).mListener == semDisplayVolumeKeyListener) {
                return i;
            }
        }
        return -1;
    }

    public void registerWifiDisplayParameterListener(SemWifiDisplayParameterListener semWifiDisplayParameterListener, Handler handler) {
        if (this.mWifiDisplayParameterListeners != null) {
            Log.d(TAG, "registerWifiDisplayParameterListener");
            if (semWifiDisplayParameterListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                if (findWifiDisplayParameterListnerLocked(semWifiDisplayParameterListener) < 0) {
                    Log.d(TAG, "registerWifiDisplayParameterListener index < 0");
                    this.mWifiDisplayParameterListeners.add(new WifiDisplayParameterListenerDelegate(semWifiDisplayParameterListener, handler));
                    registerCallbackIfNeededLocked();
                }
            }
        }
    }

    public void unregisterWifiDisplayParameterListener(SemWifiDisplayParameterListener semWifiDisplayParameterListener) {
        if (this.mWifiDisplayParameterListeners != null) {
            Log.d(TAG, "unregisterWifiDisplayParameterListener");
            if (semWifiDisplayParameterListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                int findWifiDisplayParameterListnerLocked = findWifiDisplayParameterListnerLocked(semWifiDisplayParameterListener);
                if (findWifiDisplayParameterListnerLocked >= 0) {
                    Log.d(TAG, "unregisterWifiDisplayParameterListener index >= 0");
                    this.mWifiDisplayParameterListeners.get(findWifiDisplayParameterListnerLocked).clearEvents();
                    this.mWifiDisplayParameterListeners.remove(findWifiDisplayParameterListnerLocked);
                }
            }
        }
    }

    private int findWifiDisplayParameterListnerLocked(SemWifiDisplayParameterListener semWifiDisplayParameterListener) {
        ArrayList<WifiDisplayParameterListenerDelegate> arrayList = this.mWifiDisplayParameterListeners;
        if (arrayList == null) {
            return -1;
        }
        int size = arrayList.size();
        Log.d(TAG, "findWifiDisplayParameterListnerLocked numListeners: " + size);
        for (int i = 0; i < size; i++) {
            if (this.mWifiDisplayParameterListeners.get(i).mListener == semWifiDisplayParameterListener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayVolumeEvent(int i, Bundle bundle) {
        synchronized (this.mLock) {
            ArrayList<DisplayVolumeListenerDelegate> arrayList = this.mDisplayVolumeListeners;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mDisplayVolumeListeners.get(i2).sendDisplayVolumeEvent(i, bundle);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayVolumeKeyEvent(int i) {
        synchronized (this.mLock) {
            ArrayList<DisplayVolumeKeyListenerDelegate> arrayList = this.mDisplayVolumeKeyListeners;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mDisplayVolumeKeyListeners.get(i2).sendDisplayVolumeKeyEvent(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list) {
        synchronized (this.mLock) {
            ArrayList<WifiDisplayParameterListenerDelegate> arrayList = this.mWifiDisplayParameterListeners;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mWifiDisplayParameterListeners.get(i2).sendWifiDisplayParameterEvent(i, list);
                }
            }
        }
    }

    public boolean isWifiDisplayWithPinSupported(String str) {
        try {
            return this.mDm.isWifiDisplayWithPinSupported(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get dongle pin supported feature Wifi display", e);
            return false;
        }
    }

    public void fitToActiveDisplay(boolean z) {
        try {
            this.mDm.fitToActiveDisplay(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to fit/unfit to active display", e);
        }
    }

    public boolean isFitToActiveDisplay() {
        try {
            return this.mDm.isFitToActiveDisplay();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get active display's fit status", e);
            return false;
        }
    }

    public String getPresentationOwner(int i) {
        try {
            return this.mDm.getPresentationOwner(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Fail to get PresentationOwner.", e);
            return "";
        }
    }

    public void setWifiDisplayParam(String str, String str2) {
        try {
            this.mDm.setWifiDisplayParam(str, str2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setWifiDisplayParam", e);
        }
    }

    public void registerDeviceListener(SemDeviceStatusListener semDeviceStatusListener, Handler handler) {
        if (this.mDeviceListeners != null) {
            Log.d(TAG, "registerDeviceListener");
            if (semDeviceStatusListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                if (findDeviceListnerLocked(semDeviceStatusListener) < 0) {
                    Log.d(TAG, "registerDeviceListener index < 0");
                    this.mDeviceListeners.add(new DeviceListenerDelegate(semDeviceStatusListener, handler));
                    registerCallbackIfNeededLocked();
                }
            }
        }
    }

    public void unregisterDeviceListener(SemDeviceStatusListener semDeviceStatusListener) {
        if (this.mDeviceListeners != null) {
            if (semDeviceStatusListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized (this.mLock) {
                int findDeviceListnerLocked = findDeviceListnerLocked(semDeviceStatusListener);
                if (findDeviceListnerLocked >= 0) {
                    this.mDeviceListeners.get(findDeviceListnerLocked).clearEvents();
                    this.mDeviceListeners.remove(findDeviceListnerLocked);
                }
            }
        }
    }

    private int findDeviceListnerLocked(SemDeviceStatusListener semDeviceStatusListener) {
        ArrayList<DeviceListenerDelegate> arrayList = this.mDeviceListeners;
        if (arrayList == null) {
            return -1;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (this.mDeviceListeners.get(i).mListener == semDeviceStatusListener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceEvent(Bundle bundle, int i) {
        synchronized (this.mLock) {
            ArrayList<DeviceListenerDelegate> arrayList = this.mDeviceListeners;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mDeviceListeners.get(i2).sendDeviceEvent(bundle, i);
                }
            }
        }
    }

    public class WifiDisplayConnectionCallback extends IWifiDisplayConnectionCallback.Stub {
        private Handler mHandler;
        private DisplayManager.SemWifiDisplayConnectionCallback mUserCallback;

        WifiDisplayConnectionCallback(DisplayManagerGlobal displayManagerGlobal, DisplayManager.SemWifiDisplayConnectionCallback semWifiDisplayConnectionCallback, Handler handler) {
            this.mUserCallback = semWifiDisplayConnectionCallback;
            this.mHandler = new Handler(handler != null ? handler.getLooper() : Looper.myLooper());
        }

        @Override // android.hardware.display.IWifiDisplayConnectionCallback
        public void onSuccess(final List<SemWifiDisplayParameter> list) {
            this.mHandler.post(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal.WifiDisplayConnectionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    if (WifiDisplayConnectionCallback.this.mUserCallback != null) {
                        WifiDisplayConnectionCallback.this.mUserCallback.onSuccess(list);
                    }
                }
            });
        }

        @Override // android.hardware.display.IWifiDisplayConnectionCallback
        public void onFailure(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal.WifiDisplayConnectionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    if (WifiDisplayConnectionCallback.this.mUserCallback != null) {
                        WifiDisplayConnectionCallback.this.mUserCallback.onFailure(i);
                    }
                }
            });
        }
    }

    private final class DisplayManagerCallback extends IDisplayManagerCallback.Stub {
        private DisplayManagerCallback() {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayEvent(int i, int i2) {
            if (DisplayManagerGlobal.DEBUG) {
                Log.d(DisplayManagerGlobal.TAG, "onDisplayEvent: displayId=" + i + ", event=" + DisplayManagerGlobal.eventToString(i2));
            }
            DisplayManagerGlobal.this.handleDisplayEvent(i, i2, false);
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onTopologyChanged(DisplayTopology displayTopology) {
            if (DisplayManagerGlobal.DEBUG) {
                Log.d(DisplayManagerGlobal.TAG, "onTopologyChanged: " + displayTopology);
            }
            Iterator it = DisplayManagerGlobal.this.mTopologyListeners.iterator();
            while (it.hasNext()) {
                ((DisplayTopologyListenerDelegate) it.next()).onTopologyChanged(displayTopology);
            }
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayVolumeEvent(int i, Bundle bundle) {
            if (DisplayManagerGlobal.DEBUG) {
                Log.d(DisplayManagerGlobal.TAG, "onDisplayVolumeEvent");
            }
            DisplayManagerGlobal.this.handleDisplayVolumeEvent(i, bundle);
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayVolumeKeyEvent(int i) {
            if (DisplayManagerGlobal.DEBUG) {
                Log.d(DisplayManagerGlobal.TAG, "onDisplayVolumeKeyEvent");
            }
            DisplayManagerGlobal.this.handleDisplayVolumeKeyEvent(i);
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list) {
            if (DisplayManagerGlobal.DEBUG) {
                Log.d(DisplayManagerGlobal.TAG, "onWifiDisplayParameterEvent");
            }
            DisplayManagerGlobal.this.handleWifiDisplayParameterEvent(i, list);
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDeviceEvent(Bundle bundle, int i) {
            if (DisplayManagerGlobal.DEBUG) {
                Log.d(DisplayManagerGlobal.TAG, "onDeviceEvent: msg = " + bundle + ", event = " + i);
            }
            DisplayManagerGlobal.this.handleDeviceEvent(bundle, i);
        }
    }

    public static final class DisplayListenerDelegate {
        private final Executor mExecutor;
        public volatile long mInternalEventFlagsMask;
        private final boolean mIsEventFilterExplicit;
        public final DisplayManager.DisplayListener mListener;
        private final String mPackageName;
        private final DisplayInfo mDisplayInfo = new DisplayInfo();
        private AtomicLong mGenerationId = new AtomicLong(1);

        DisplayListenerDelegate(DisplayManager.DisplayListener displayListener, Executor executor, long j, String str, boolean z) {
            this.mExecutor = executor;
            this.mListener = displayListener;
            this.mInternalEventFlagsMask = j;
            this.mPackageName = str;
            this.mIsEventFilterExplicit = z;
        }

        void sendDisplayEvent(final int i, final int i2, final DisplayInfo displayInfo, final boolean z) {
            if (DisplayManagerGlobal.extraLogging()) {
                Slog.i(DisplayManagerGlobal.TAG, "Sending Display Event: " + DisplayManagerGlobal.eventToString(i2));
            }
            final long j = this.mGenerationId.get();
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$DisplayListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayManagerGlobal.DisplayListenerDelegate.this.lambda$sendDisplayEvent$0(j, i, i2, displayInfo, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendDisplayEvent$0(long j, int i, int i2, DisplayInfo displayInfo, boolean z) {
            if (j == this.mGenerationId.get()) {
                handleDisplayEventInner(i, i2, displayInfo, z);
            }
        }

        public boolean isEventFilterExplicit() {
            return this.mIsEventFilterExplicit;
        }

        void clearEvents() {
            this.mGenerationId.incrementAndGet();
        }

        void setEventsMask(long j) {
            this.mInternalEventFlagsMask = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void implicitlyRegisterForRRChanges() {
            if ((this.mInternalEventFlagsMask & 2) == 0 || this.mIsEventFilterExplicit) {
                return;
            }
            setEventsMask(this.mInternalEventFlagsMask | 64);
        }

        private void handleDisplayEventInner(int i, int i2, DisplayInfo displayInfo, boolean z) {
            if (DisplayManagerGlobal.extraLogging()) {
                Slog.i(DisplayManagerGlobal.TAG, "DLD(" + DisplayManagerGlobal.eventToString(i2) + ", display=" + i + ", mEventsMask=" + Long.toBinaryString(this.mInternalEventFlagsMask) + ", mPackageName=" + this.mPackageName + ", displayInfo=" + displayInfo + ", listener=" + this.mListener.getClass() + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (DisplayManagerGlobal.DEBUG) {
                Trace.beginSection((String) TextUtils.trimToSize("DLD(" + DisplayManagerGlobal.eventToString(i2) + ", display=" + i + ", listener=" + this.mListener.getClass() + NavigationBarInflaterView.KEY_CODE_END, 127));
            }
            switch (i2) {
                case 1:
                    if ((this.mInternalEventFlagsMask & 1) != 0) {
                        this.mListener.onDisplayAdded(i);
                        break;
                    }
                    break;
                case 2:
                    if ((this.mInternalEventFlagsMask & 2) != 0 && displayInfo != null && (z || !displayInfo.equals(this.mDisplayInfo))) {
                        if (DisplayManagerGlobal.extraLogging()) {
                            Slog.i(DisplayManagerGlobal.TAG, "Sending onDisplayChanged: Display Changed. Info: " + displayInfo);
                        }
                        this.mDisplayInfo.copyFrom(displayInfo);
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 3:
                    if ((this.mInternalEventFlagsMask & 4) != 0) {
                        this.mListener.onDisplayRemoved(i);
                        break;
                    }
                    break;
                case 4:
                    if ((this.mInternalEventFlagsMask & 8) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 5:
                    if ((this.mInternalEventFlagsMask & 16) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 6:
                    if ((this.mInternalEventFlagsMask & 32) != 0) {
                        this.mListener.onDisplayConnected(i);
                        break;
                    }
                    break;
                case 7:
                    if ((this.mInternalEventFlagsMask & 32) != 0) {
                        this.mListener.onDisplayDisconnected(i);
                        break;
                    }
                    break;
                case 8:
                    if ((this.mInternalEventFlagsMask & 64) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 9:
                    if ((this.mInternalEventFlagsMask & 128) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 10:
                    if ((this.mInternalEventFlagsMask & 512) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
            }
            if (DisplayManagerGlobal.DEBUG) {
                Trace.endSection();
            }
        }

        public String toString() {
            return "flag: {" + this.mInternalEventFlagsMask + "}, for " + this.mListener.getClass();
        }
    }

    public static final class VirtualDisplayCallback extends IVirtualDisplayCallback.Stub {
        private final VirtualDisplay.Callback mCallback;
        private final Executor mExecutor;

        public VirtualDisplayCallback(VirtualDisplay.Callback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = callback != null ? (Executor) Objects.requireNonNull(executor) : null;
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onPaused() {
            final VirtualDisplay.Callback callback = this.mCallback;
            if (callback != null) {
                Executor executor = this.mExecutor;
                Objects.requireNonNull(callback);
                executor.execute(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDisplay.Callback.this.onPaused();
                    }
                });
            }
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onResumed() {
            final VirtualDisplay.Callback callback = this.mCallback;
            if (callback != null) {
                Executor executor = this.mExecutor;
                Objects.requireNonNull(callback);
                executor.execute(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDisplay.Callback.this.onResumed();
                    }
                });
            }
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onStopped() {
            final VirtualDisplay.Callback callback = this.mCallback;
            if (callback != null) {
                Executor executor = this.mExecutor;
                Objects.requireNonNull(callback);
                executor.execute(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDisplay.Callback.this.onStopped();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DisplayTopologyListenerDelegate {
        private final Executor mExecutor;
        private final Consumer<DisplayTopology> mListener;
        private final String mPackageName;

        DisplayTopologyListenerDelegate(Consumer<DisplayTopology> consumer, Executor executor, String str) {
            this.mExecutor = executor;
            this.mListener = consumer;
            this.mPackageName = str;
        }

        public String toString() {
            return "DisplayTopologyListener {packageName=" + this.mPackageName + "}";
        }

        void onTopologyChanged(final DisplayTopology displayTopology) {
            if (DisplayManagerGlobal.extraLogging()) {
                Slog.i(DisplayManagerGlobal.TAG, "Sending topology update: " + displayTopology);
            }
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$DisplayTopologyListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayManagerGlobal.DisplayTopologyListenerDelegate.this.lambda$onTopologyChanged$0(displayTopology);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopologyChanged$0(DisplayTopology displayTopology) {
            this.mListener.accept(displayTopology);
        }
    }

    public static void invalidateLocalDisplayInfoCaches() {
        PropertyInvalidatedCache.invalidateCache("system_server", CACHE_KEY_DISPLAY_INFO_API);
    }

    public void disableLocalDisplayInfoCaches() {
        this.mDisplayCache = null;
    }

    public void registerNativeChoreographerForRefreshRateCallbacks() {
        synchronized (this.mLock) {
            this.mDispatchNativeCallbacks = true;
            if (Flags.delayImplicitRrRegistrationUntilRrAccessed()) {
                if (!this.mShouldImplicitlyRegisterRrChanges) {
                    Slog.i(TAG, "Choreographer implicitly registered for the refresh rate.");
                }
                this.mShouldImplicitlyRegisterRrChanges = true;
            }
            registerCallbackIfNeededLocked();
            updateCallbackIfNeededLocked();
            DisplayInfo displayInfoLocked = getDisplayInfoLocked(0);
            if (displayInfoLocked != null) {
                float refreshRate = displayInfoLocked.getRefreshRate();
                this.mNativeCallbackReportedRefreshRate = refreshRate;
                nSignalNativeCallbacks(refreshRate);
            }
        }
    }

    public void unregisterNativeChoreographerForRefreshRateCallbacks() {
        synchronized (this.mLock) {
            this.mDispatchNativeCallbacks = false;
            updateCallbackIfNeededLocked();
        }
    }

    public long getPrimaryPhysicalDisplayId() {
        try {
            return this.mDm.getPrimaryPhysicalDisplayId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String eventToString(int i) {
        switch (i) {
            case 1:
                return ClipboardConstants.USER_ADDED;
            case 2:
                return "BASIC_CHANGED";
            case 3:
                return ClipboardConstants.USER_REMOVED;
            case 4:
                return "BRIGHTNESS_CHANGED";
            case 5:
                return "HDR_SDR_RATIO_CHANGED";
            case 6:
                return "EVENT_DISPLAY_CONNECTED";
            case 7:
                return "EVENT_DISPLAY_DISCONNECTED";
            case 8:
                return "EVENT_DISPLAY_REFRESH_RATE_CHANGED";
            case 9:
                return "EVENT_DISPLAY_STATE_CHANGED";
            case 10:
                return "EVENT_DISPLAY_COMMITTED_STATE_CHANGED";
            default:
                return "UNKNOWN";
        }
    }

    private static boolean initExtraLogging() {
        if (sCurrentPackageName == null) {
            sCurrentPackageName = ActivityThread.currentPackageName();
            String str = EXTRA_LOGGING_PACKAGE_NAME;
            sExtraDisplayListenerLogging = !TextUtils.isEmpty(str) && str.equals(sCurrentPackageName);
        }
        return sExtraDisplayListenerLogging;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean extraLogging() {
        return sExtraDisplayListenerLogging;
    }

    public long mapFiltersToInternalEventFlag(long j, long j2) {
        return mapPublicEventFlags(j) | mapPrivateEventFlags(j2);
    }

    private long mapPrivateEventFlags(long j) {
        long j2 = (1 & j) != 0 ? 8L : 0L;
        if ((2 & j) != 0) {
            j2 |= 16;
        }
        if ((4 & j) != 0) {
            j2 |= 32;
        }
        return (!Flags.committedStateSeparateEvent() || (j & 8) == 0) ? j2 : 512 | j2;
    }

    private long mapPublicEventFlags(long j) {
        long j2 = (j & 1) == 0 ? 0L : 1L;
        if ((j & 4) != 0) {
            j2 |= 2;
        }
        if ((2 & j) != 0) {
            j2 |= 4;
        }
        if ((8 & j) != 0) {
            j2 |= 64;
        }
        return (!Flags.displayListenerPerformanceImprovements() || (j & 16) == 0) ? j2 : 128 | j2;
    }

    public CopyOnWriteArrayList<DisplayListenerDelegate> getDisplayListeners() {
        return this.mDisplayListeners;
    }

    private static final class DisplayVolumeListenerDelegate extends Handler {
        public final SemDisplayVolumeListener mListener;

        public DisplayVolumeListenerDelegate(SemDisplayVolumeListener semDisplayVolumeListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
            this.mListener = semDisplayVolumeListener;
        }

        public void sendDisplayVolumeEvent(int i, Bundle bundle) {
            Message obtain = Message.obtain(this, i);
            obtain.setData(bundle);
            sendMessage(obtain);
        }

        public void clearEvents() {
            removeCallbacksAndMessages(null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            if (message.what != 11) {
                return;
            }
            int i = data.getInt("minVol");
            int i2 = data.getInt("maxVol");
            int i3 = data.getInt("curVol");
            boolean z = data.getBoolean("isMute", false);
            Log.d(DisplayManagerGlobal.TAG, "handleMessage EVENT_VOLUME_LEVEL_CHANGED= curVol: " + i3);
            this.mListener.onVolumeChanged(i, i2, i3, z);
        }
    }

    private static final class DisplayVolumeKeyListenerDelegate extends Handler {
        public final SemDisplayVolumeKeyListener mListener;

        public DisplayVolumeKeyListenerDelegate(SemDisplayVolumeKeyListener semDisplayVolumeKeyListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
            this.mListener = semDisplayVolumeKeyListener;
        }

        public void sendDisplayVolumeKeyEvent(int i) {
            sendMessage(Message.obtain(this, i));
        }

        public void clearEvents() {
            removeCallbacksAndMessages(null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            message.getData();
            switch (message.what) {
                case 12:
                    Log.d(DisplayManagerGlobal.TAG, "onVolumeKeyDown");
                    this.mListener.onVolumeKeyDown();
                    break;
                case 13:
                    Log.d(DisplayManagerGlobal.TAG, "onVolumeKeyUp");
                    this.mListener.onVolumeKeyUp();
                    break;
                case 14:
                    Log.d(DisplayManagerGlobal.TAG, "onMuteKeyStateChanged [MUTE]");
                    this.mListener.onMuteKeyStateChanged(true);
                    break;
                case 15:
                    Log.d(DisplayManagerGlobal.TAG, "onMuteKeyStateChanged [UNMUTE]");
                    this.mListener.onMuteKeyStateChanged(false);
                    break;
            }
        }
    }

    private static final class WifiDisplayParameterListenerDelegate extends Handler {
        private final SemWifiDisplayParameterListener mListener;

        public WifiDisplayParameterListenerDelegate(SemWifiDisplayParameterListener semWifiDisplayParameterListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
            this.mListener = semWifiDisplayParameterListener;
        }

        public void sendWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list) {
            Message obtain = Message.obtain(this, i);
            obtain.obj = list;
            sendMessage(obtain);
        }

        public void clearEvents() {
            removeCallbacksAndMessages(null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 16) {
                return;
            }
            Log.d(DisplayManagerGlobal.TAG, "onParametersChanged");
            this.mListener.onParametersChanged((List) message.obj);
        }
    }

    private static final class DeviceListenerDelegate extends Handler {
        public final SemDeviceStatusListener mListener;

        public DeviceListenerDelegate(SemDeviceStatusListener semDeviceStatusListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper(), null, true);
            this.mListener = semDeviceStatusListener;
        }

        public void sendDeviceEvent(Bundle bundle, int i) {
            Message obtain = Message.obtain(this, i);
            obtain.setData(bundle);
            sendMessage(obtain);
        }

        public void clearEvents() {
            removeCallbacksAndMessages(null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 8:
                    int i = data.getInt("status", 0);
                    Log.d(DisplayManagerGlobal.TAG, "handleMessage EVENT_CONNECTIONSTATUS_CHANGED = " + i);
                    this.mListener.onConnectionStatusChanged(i);
                    break;
                case 9:
                    int i2 = data.getInt("status", 6);
                    Log.d(DisplayManagerGlobal.TAG, "handleMessage EVENT_REMOTE_DISPLAY_STATE_CHANGED = " + i2);
                    this.mListener.onScreenSharingStatusChanged(i2);
                    break;
                case 10:
                    int i3 = data.getInt("status", 0);
                    Log.d(DisplayManagerGlobal.TAG, "handleMessage EVENT_REMOTE_DISPLAY_ROTATION_CHANGED = " + i3);
                    this.mListener.onScreenSharingStatusChanged(i3);
                    break;
            }
        }
    }

    public void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) {
        try {
            this.mDm.setTemporaryBrightnessForSlowChange(i, f, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) {
        try {
            this.mDm.setBrightnessConfigurationForUserWithStats(brightnessConfiguration, i, str, list, list2, list3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2, List<String> list, List<String> list2, List<String> list3) {
        try {
            this.mDm.setBrightnessConfigurationForDisplayWithStats(brightnessConfiguration, str, i, str2, list, list2, list3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetBrightnessConfigurationForUser(int i, String str) {
        try {
            this.mDm.resetBrightnessConfigurationForUser(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str, int i2) {
        try {
            this.mDm.setBackupBrightnessConfiguration(brightnessConfiguration, i, str, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BrightnessConfiguration getBackupBrightnessConfiguration(int i, int i2) {
        try {
            return this.mDm.getBackupBrightnessConfiguration(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int convertToBrightness(float f) {
        try {
            return this.mDm.convertToBrightness(f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getAdaptiveBrightness(int i, float f) {
        try {
            return this.mDm.getAdaptiveBrightness(i, f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerHbmBrightnessListener(DisplayManager.DisplayHbmBrightnessListener displayHbmBrightnessListener) {
        synchronized (this.mLock) {
            if (this.mHbmBrightnessCallback == null) {
                this.mHbmBrightnessCallback = new HbmBrightnessCallback();
                registerHbmBrightnessCallback();
            }
            this.mHbmBrightnessCallback.addDisplayHbmBrightnessListener(displayHbmBrightnessListener);
        }
    }

    public void unregisterHbmBrightnessListener(DisplayManager.DisplayHbmBrightnessListener displayHbmBrightnessListener) {
        synchronized (this.mLock) {
            HbmBrightnessCallback hbmBrightnessCallback = this.mHbmBrightnessCallback;
            if (hbmBrightnessCallback == null) {
                return;
            }
            hbmBrightnessCallback.removeDisplayHbmBrightnessListener(displayHbmBrightnessListener);
        }
    }

    private void registerHbmBrightnessCallback() {
        if (this.mHbmBrightnessCallbackRegistered) {
            return;
        }
        try {
            this.mDm.registerHbmBrightnessCallback(this.mHbmBrightnessCallback);
            this.mHbmBrightnessCallbackRegistered = true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private final class HbmBrightnessCallback extends IHbmBrightnessCallback.Stub {
        CopyOnWriteArrayList<DisplayManager.DisplayHbmBrightnessListener> mListeners;

        private HbmBrightnessCallback(DisplayManagerGlobal displayManagerGlobal) {
            this.mListeners = new CopyOnWriteArrayList<>();
        }

        void addDisplayHbmBrightnessListener(DisplayManager.DisplayHbmBrightnessListener displayHbmBrightnessListener) {
            this.mListeners.add(displayHbmBrightnessListener);
        }

        void removeDisplayHbmBrightnessListener(DisplayManager.DisplayHbmBrightnessListener displayHbmBrightnessListener) {
            this.mListeners.remove(displayHbmBrightnessListener);
        }

        @Override // android.hardware.display.IHbmBrightnessCallback
        public void onChanged(int i, boolean z) {
            Iterator<DisplayManager.DisplayHbmBrightnessListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onChanged(i, z);
            }
        }
    }
}
