package com.android.server.display;

import android.R;
import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.sidekick.SidekickInternal;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.p005os.IInstalld;
import android.util.DisplayUtils;
import android.util.EventLog;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayCutout;
import android.view.DisplayEventReceiver;
import android.view.DisplayShape;
import android.view.RoundedCorners;
import android.view.SurfaceControl;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.display.DisplayAdapter;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.LocalDisplayAdapter;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LogicalLight;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final class LocalDisplayAdapter extends DisplayAdapter {
    public final LongSparseArray mDevices;
    public final Injector mInjector;
    public final boolean mIsBootDisplayModeSupported;
    public Context mOverlayContext;
    public final SurfaceControlProxy mSurfaceControlProxy;

    public interface DisplayEventListener {
        void onFrameRateOverridesChanged(long j, long j2, DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr);

        void onHotplug(long j, long j2, boolean z);

        void onModeChanged(long j, long j2, int i, long j3);
    }

    public LocalDisplayAdapter(DisplayManagerService.SyncRoot syncRoot, Context context, Handler handler, DisplayAdapter.Listener listener) {
        this(syncRoot, context, handler, listener, new Injector());
    }

    public LocalDisplayAdapter(DisplayManagerService.SyncRoot syncRoot, Context context, Handler handler, DisplayAdapter.Listener listener, Injector injector) {
        super(syncRoot, context, handler, listener, "LocalDisplayAdapter");
        this.mDevices = new LongSparseArray();
        this.mInjector = injector;
        SurfaceControlProxy surfaceControlProxy = injector.getSurfaceControlProxy();
        this.mSurfaceControlProxy = surfaceControlProxy;
        this.mIsBootDisplayModeSupported = surfaceControlProxy.getBootDisplayModeSupport();
    }

    @Override // com.android.server.display.DisplayAdapter
    public void registerLocked() {
        super.registerLocked();
        this.mInjector.setDisplayEventListenerLocked(getHandler().getLooper(), new LocalDisplayEventListener());
        for (long j : this.mSurfaceControlProxy.getPhysicalDisplayIds()) {
            tryConnectDisplayLocked(j);
        }
    }

    public final void tryConnectDisplayLocked(long j) {
        IBinder physicalDisplayToken = this.mSurfaceControlProxy.getPhysicalDisplayToken(j);
        if (physicalDisplayToken != null) {
            SurfaceControl.StaticDisplayInfo staticDisplayInfo = this.mSurfaceControlProxy.getStaticDisplayInfo(j);
            if (staticDisplayInfo == null) {
                Slog.m79w("LocalDisplayAdapter", "No valid static info found for display device " + j);
                return;
            }
            SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo = this.mSurfaceControlProxy.getDynamicDisplayInfo(j);
            if (dynamicDisplayInfo == null) {
                Slog.m79w("LocalDisplayAdapter", "No valid dynamic info found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.supportedDisplayModes == null) {
                Slog.m79w("LocalDisplayAdapter", "No valid modes found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.activeDisplayModeId < 0) {
                Slog.m79w("LocalDisplayAdapter", "No valid active mode found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.activeColorMode < 0) {
                Slog.m79w("LocalDisplayAdapter", "No valid active color mode for display device " + j);
                dynamicDisplayInfo.activeColorMode = -1;
            }
            SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs = this.mSurfaceControlProxy.getDesiredDisplayModeSpecs(physicalDisplayToken);
            LocalDisplayDevice localDisplayDevice = (LocalDisplayDevice) this.mDevices.get(j);
            if (localDisplayDevice == null) {
                boolean z = this.mDevices.size() == 0;
                if (CoreRune.FW_VRR_RESOLUTION_POLICY && staticDisplayInfo.isInternal) {
                    dynamicDisplayInfo.activeDisplayModeId = getOptimizedDisplayMode(dynamicDisplayInfo.supportedDisplayModes);
                }
                LocalDisplayDevice localDisplayDevice2 = new LocalDisplayDevice(physicalDisplayToken, j, staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs, z);
                this.mDevices.put(j, localDisplayDevice2);
                sendDisplayDeviceEventLocked(localDisplayDevice2, 1);
                return;
            }
            if (localDisplayDevice.updateDisplayPropertiesLocked(staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs)) {
                sendDisplayDeviceEventLocked(localDisplayDevice, 2);
            }
        }
    }

    public final void tryDisconnectDisplayLocked(long j) {
        LocalDisplayDevice localDisplayDevice = (LocalDisplayDevice) this.mDevices.get(j);
        if (localDisplayDevice != null) {
            this.mDevices.remove(j);
            sendDisplayDeviceEventLocked(localDisplayDevice, 3);
        }
    }

    public static int getPowerModeForState(int i) {
        if (i == 1) {
            return 0;
        }
        if (i != 6) {
            return i != 3 ? (i == 4 && !PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI) ? 3 : 2 : PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI ? 2 : 1;
        }
        return 4;
    }

    public final int getOptimizedDisplayMode(SurfaceControl.DisplayMode[] displayModeArr) {
        int i = 0;
        int i2 = 0;
        float f = 0.0f;
        int i3 = 0;
        for (SurfaceControl.DisplayMode displayMode : displayModeArr) {
            int i4 = displayMode.width;
            int i5 = displayMode.height;
            int i6 = i * i2;
            if (i4 * i5 >= i6) {
                float f2 = displayMode.refreshRate;
                if (f2 >= 59.99f && (i4 * i5 > i6 || f2 < f)) {
                    i3 = Arrays.asList(displayModeArr).indexOf(displayMode);
                    i = i4;
                    i2 = i5;
                    f = f2;
                }
            }
        }
        return i3;
    }

    public final class LocalDisplayDevice extends DisplayDevice {
        public int mActiveColorMode;
        public int mActiveModeId;
        public float mActiveRenderFrameRate;
        public SurfaceControl.DisplayMode mActiveSfDisplayMode;
        public int mActiveSfDisplayModeAtStartId;
        public boolean mAllmRequested;
        public boolean mAllmSupported;
        public final BacklightAdapter mBacklightAdapter;
        public float mBrightnessState;
        public int mCommittedState;
        public float mCurrentHdrSdrRatio;
        public int mDefaultModeGroup;
        public int mDefaultModeId;
        public final DisplayModeDirector.DesiredDisplayModeSpecs mDisplayModeSpecs;
        public boolean mDisplayModeSpecsInvalid;
        public int mDisplayStateCount;
        public DisplayEventReceiver.FrameRateOverride[] mFrameRateOverrides;
        public boolean mGameContentTypeRequested;
        public boolean mGameContentTypeSupported;
        public boolean mHavePendingChanges;
        public Display.HdrCapabilities mHdrCapabilities;
        public DisplayDeviceInfo mInfo;
        public final boolean mIsFirstDisplay;
        public long mLastBrightnessRequestedTime;
        public int mLastResolution;
        public final long mPhysicalDisplayId;
        public int mRequestedState;
        public float mSdrBrightnessState;
        public SurfaceControl.DisplayMode[] mSfDisplayModes;
        public boolean mSidekickActive;
        public final SidekickInternal mSidekickInternal;
        public int mState;
        public final ArrayList mStateChangeCallbacks;
        public int mStateLimit;
        public SurfaceControl.StaticDisplayInfo mStaticDisplayInfo;
        public final ArrayList mSupportedColorModes;
        public final SparseArray mSupportedModes;
        public int mSystemPreferredModeId;
        public Display.Mode mUserPreferredMode;
        public int mUserPreferredModeId;

        @Override // com.android.server.display.DisplayDevice
        public boolean hasStableUniqueId() {
            return true;
        }

        public LocalDisplayDevice(IBinder iBinder, long j, SurfaceControl.StaticDisplayInfo staticDisplayInfo, SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo, SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs, boolean z) {
            super(LocalDisplayAdapter.this, iBinder, "local:" + j, LocalDisplayAdapter.this.getContext());
            this.mSupportedModes = new SparseArray();
            this.mSupportedColorModes = new ArrayList();
            this.mDisplayModeSpecs = new DisplayModeDirector.DesiredDisplayModeSpecs();
            this.mState = 0;
            this.mCommittedState = 0;
            this.mBrightnessState = Float.NaN;
            this.mSdrBrightnessState = Float.NaN;
            this.mCurrentHdrSdrRatio = Float.NaN;
            this.mDefaultModeId = -1;
            this.mSystemPreferredModeId = -1;
            this.mUserPreferredModeId = -1;
            this.mActiveSfDisplayModeAtStartId = -1;
            this.mActiveModeId = -1;
            this.mLastResolution = 0;
            this.mFrameRateOverrides = new DisplayEventReceiver.FrameRateOverride[0];
            this.mStateLimit = 0;
            this.mRequestedState = 0;
            this.mDisplayStateCount = 0;
            this.mLastBrightnessRequestedTime = -1L;
            this.mStateChangeCallbacks = new ArrayList();
            this.mPhysicalDisplayId = j;
            this.mIsFirstDisplay = z;
            updateDisplayPropertiesLocked(staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs);
            this.mSidekickInternal = (SidekickInternal) LocalServices.getService(SidekickInternal.class);
            this.mBacklightAdapter = new BacklightAdapter(iBinder, z, LocalDisplayAdapter.this.mSurfaceControlProxy, j);
            this.mActiveSfDisplayModeAtStartId = dynamicDisplayInfo.activeDisplayModeId;
        }

        @Override // com.android.server.display.DisplayDevice
        public Display.Mode getActiveDisplayModeAtStartLocked() {
            return findMode(findMatchingModeIdLocked(this.mActiveSfDisplayModeAtStartId));
        }

        public boolean updateDisplayPropertiesLocked(SurfaceControl.StaticDisplayInfo staticDisplayInfo, SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo, SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            boolean updateStaticInfo = updateStaticInfo(staticDisplayInfo) | updateDisplayModesLocked(dynamicDisplayInfo.supportedDisplayModes, dynamicDisplayInfo.preferredBootDisplayMode, dynamicDisplayInfo.activeDisplayModeId, dynamicDisplayInfo.renderFrameRate, desiredDisplayModeSpecs) | updateColorModesLocked(dynamicDisplayInfo.supportedColorModes, dynamicDisplayInfo.activeColorMode) | updateHdrCapabilitiesLocked(dynamicDisplayInfo.hdrCapabilities) | updateAllmSupport(dynamicDisplayInfo.autoLowLatencyModeSupported) | updateGameContentTypeSupport(dynamicDisplayInfo.gameContentTypeSupported);
            if (updateStaticInfo) {
                this.mHavePendingChanges = true;
            }
            return updateStaticInfo;
        }

        /* JADX WARN: Code restructure failed: missing block: B:94:0x0191, code lost:
        
            if (r16.mDisplayModeSpecs.appRequest.equals(r21.appRequestRanges) != false) goto L88;
         */
        /* JADX WARN: Removed duplicated region for block: B:101:0x01ae  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x01b6  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x016f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean updateDisplayModesLocked(SurfaceControl.DisplayMode[] displayModeArr, int i, int i2, float f, SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            DisplayModeRecord displayModeRecord;
            DisplayModeRecord displayModeRecord2;
            boolean z;
            int i3;
            boolean z2;
            float f2;
            boolean z3;
            boolean z4;
            this.mSfDisplayModes = (SurfaceControl.DisplayMode[]) Arrays.copyOf(displayModeArr, displayModeArr.length);
            this.mActiveSfDisplayMode = getModeById(displayModeArr, i2);
            SurfaceControl.DisplayMode modeById = getModeById(displayModeArr, i);
            ArrayList arrayList = new ArrayList();
            int i4 = 0;
            boolean z5 = false;
            while (i4 < displayModeArr.length) {
                SurfaceControl.DisplayMode displayMode = displayModeArr[i4];
                ArrayList arrayList2 = new ArrayList();
                int i5 = 0;
                while (i5 < displayModeArr.length) {
                    SurfaceControl.DisplayMode displayMode2 = displayModeArr[i5];
                    if (i5 != i4 && displayMode2.width == displayMode.width && displayMode2.height == displayMode.height && displayMode2.refreshRate != displayMode.refreshRate && displayMode2.group == displayMode.group) {
                        arrayList2.add(Float.valueOf(displayMode2.refreshRate));
                    }
                    i5++;
                }
                Collections.sort(arrayList2);
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z4 = false;
                        break;
                    }
                    DisplayModeRecord displayModeRecord3 = (DisplayModeRecord) it.next();
                    if (displayModeRecord3.hasMatchingMode(displayMode) && refreshRatesEquals(arrayList2, displayModeRecord3.mMode.getAlternativeRefreshRates())) {
                        z4 = true;
                        break;
                    }
                }
                if (!z4) {
                    DisplayModeRecord findDisplayModeRecord = findDisplayModeRecord(displayMode, arrayList2);
                    if (findDisplayModeRecord == null) {
                        int size = arrayList2.size();
                        float[] fArr = new float[size];
                        for (int i6 = 0; i6 < size; i6++) {
                            fArr[i6] = ((Float) arrayList2.get(i6)).floatValue();
                        }
                        findDisplayModeRecord = new DisplayModeRecord(displayMode, fArr);
                        z5 = true;
                    }
                    arrayList.add(findDisplayModeRecord);
                }
                i4++;
            }
            Iterator it2 = arrayList.iterator();
            while (true) {
                displayModeRecord = null;
                if (!it2.hasNext()) {
                    displayModeRecord2 = null;
                    break;
                }
                displayModeRecord2 = (DisplayModeRecord) it2.next();
                if (displayModeRecord2.hasMatchingMode(this.mActiveSfDisplayMode)) {
                    break;
                }
            }
            if (i != -1 && modeById != null) {
                Iterator it3 = arrayList.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    DisplayModeRecord displayModeRecord4 = (DisplayModeRecord) it3.next();
                    if (displayModeRecord4.hasMatchingMode(modeById)) {
                        displayModeRecord = displayModeRecord4;
                        break;
                    }
                }
                if (displayModeRecord != null) {
                    int modeId = displayModeRecord.mMode.getModeId();
                    if (LocalDisplayAdapter.this.mIsBootDisplayModeSupported && this.mSystemPreferredModeId != modeId) {
                        this.mSystemPreferredModeId = modeId;
                        z = true;
                        i3 = this.mActiveModeId;
                        if (i3 != -1 || i3 == displayModeRecord2.mMode.getModeId()) {
                            z2 = false;
                        } else {
                            Slog.m72d("LocalDisplayAdapter", "The active mode was changed from SurfaceFlinger or the display device to " + displayModeRecord2.mMode);
                            this.mActiveModeId = displayModeRecord2.mMode.getModeId();
                            LocalDisplayAdapter.this.sendTraversalRequestLocked();
                            z2 = true;
                        }
                        f2 = this.mActiveRenderFrameRate;
                        if (f2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f2 == f) {
                            z3 = false;
                        } else {
                            Slog.m72d("LocalDisplayAdapter", "The render frame rate was changed from SurfaceFlinger or the display device to " + f);
                            this.mActiveRenderFrameRate = f;
                            LocalDisplayAdapter.this.sendTraversalRequestLocked();
                            z3 = true;
                        }
                        if (this.mDisplayModeSpecs.baseModeId != -1) {
                            int findMatchingModeIdLocked = findMatchingModeIdLocked(desiredDisplayModeSpecs.defaultMode);
                            if (findMatchingModeIdLocked != -1) {
                                DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs2 = this.mDisplayModeSpecs;
                                if (desiredDisplayModeSpecs2.baseModeId == findMatchingModeIdLocked) {
                                    if (desiredDisplayModeSpecs2.primary.equals(desiredDisplayModeSpecs.primaryRanges)) {
                                    }
                                }
                            }
                            this.mDisplayModeSpecsInvalid = true;
                            LocalDisplayAdapter.this.sendTraversalRequestLocked();
                        }
                        if (arrayList.size() == this.mSupportedModes.size() || z5) {
                            return z2 || z || z3;
                        }
                        this.mSupportedModes.clear();
                        Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            DisplayModeRecord displayModeRecord5 = (DisplayModeRecord) it4.next();
                            this.mSupportedModes.put(displayModeRecord5.mMode.getModeId(), displayModeRecord5);
                        }
                        int i7 = this.mDefaultModeId;
                        if (i7 == -1) {
                            this.mDefaultModeId = displayModeRecord2.mMode.getModeId();
                            this.mDefaultModeGroup = this.mActiveSfDisplayMode.group;
                            this.mActiveRenderFrameRate = f;
                        } else if (z5 && z2) {
                            Slog.m72d("LocalDisplayAdapter", "New display modes are added and the active mode has changed, use active mode as default mode.");
                            this.mDefaultModeId = displayModeRecord2.mMode.getModeId();
                            this.mDefaultModeGroup = this.mActiveSfDisplayMode.group;
                            this.mActiveRenderFrameRate = f;
                        } else if (findSfDisplayModeIdLocked(i7, this.mDefaultModeGroup) < 0) {
                            Slog.m79w("LocalDisplayAdapter", "Default display mode no longer available, using currently active mode as default.");
                            this.mDefaultModeId = displayModeRecord2.mMode.getModeId();
                            this.mDefaultModeGroup = this.mActiveSfDisplayMode.group;
                            this.mActiveRenderFrameRate = f;
                        }
                        if (this.mSupportedModes.indexOfKey(this.mDisplayModeSpecs.baseModeId) < 0) {
                            if (this.mDisplayModeSpecs.baseModeId != -1) {
                                Slog.m79w("LocalDisplayAdapter", "DisplayModeSpecs base mode no longer available, using currently active mode.");
                            }
                            this.mDisplayModeSpecs.baseModeId = displayModeRecord2.mMode.getModeId();
                            this.mDisplayModeSpecsInvalid = true;
                        }
                        Display.Mode mode = this.mUserPreferredMode;
                        if (mode != null) {
                            this.mUserPreferredModeId = findUserPreferredModeIdLocked(mode);
                        }
                        if (this.mSupportedModes.indexOfKey(this.mActiveModeId) < 0) {
                            if (this.mActiveModeId != -1) {
                                Slog.m79w("LocalDisplayAdapter", "Active display mode no longer available, reverting to default mode.");
                            }
                            this.mActiveModeId = getPreferredModeId();
                        }
                        LocalDisplayAdapter.this.sendTraversalRequestLocked();
                        return true;
                    }
                }
            }
            z = false;
            i3 = this.mActiveModeId;
            if (i3 != -1) {
            }
            z2 = false;
            f2 = this.mActiveRenderFrameRate;
            if (f2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            }
            z3 = false;
            if (this.mDisplayModeSpecs.baseModeId != -1) {
            }
            if (arrayList.size() == this.mSupportedModes.size() || z5) {
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public DisplayDeviceConfig getDisplayDeviceConfig() {
            if (this.mDisplayDeviceConfig == null) {
                loadDisplayDeviceConfig();
            }
            return this.mDisplayDeviceConfig;
        }

        public final int getPreferredModeId() {
            int i = this.mUserPreferredModeId;
            return i != -1 ? i : this.mDefaultModeId;
        }

        public final int getLogicalDensity() {
            DensityMapping densityMapping = getDisplayDeviceConfig().getDensityMapping();
            if (densityMapping == null) {
                return (int) ((this.mStaticDisplayInfo.density * 160.0f) + 0.5d);
            }
            DisplayDeviceInfo displayDeviceInfo = this.mInfo;
            return densityMapping.getDensityForResolution(displayDeviceInfo.width, displayDeviceInfo.height);
        }

        public final void loadDisplayDeviceConfig() {
            DisplayDeviceConfig create = DisplayDeviceConfig.create(LocalDisplayAdapter.this.getOverlayContext(), this.mIsFirstDisplay, this.mStaticDisplayInfo.isInternal, this.mBacklightAdapter.mUseSurfaceControlBrightness);
            this.mDisplayDeviceConfig = create;
            this.mBacklightAdapter.setForceSurfaceControl(create.hasQuirk("canSetBrightnessViaHwc"));
        }

        public final boolean updateStaticInfo(SurfaceControl.StaticDisplayInfo staticDisplayInfo) {
            if (Objects.equals(this.mStaticDisplayInfo, staticDisplayInfo)) {
                return false;
            }
            this.mStaticDisplayInfo = staticDisplayInfo;
            return true;
        }

        public final boolean updateColorModesLocked(int[] iArr, int i) {
            if (iArr == null) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (int i2 : iArr) {
                if (!this.mSupportedColorModes.contains(Integer.valueOf(i2))) {
                    z = true;
                }
                arrayList.add(Integer.valueOf(i2));
            }
            if (!(arrayList.size() != this.mSupportedColorModes.size() || z)) {
                return false;
            }
            this.mSupportedColorModes.clear();
            this.mSupportedColorModes.addAll(arrayList);
            Collections.sort(this.mSupportedColorModes);
            if (!this.mSupportedColorModes.contains(Integer.valueOf(this.mActiveColorMode))) {
                if (this.mActiveColorMode != 0) {
                    Slog.m79w("LocalDisplayAdapter", "Active color mode no longer available, reverting to default mode.");
                    this.mActiveColorMode = 0;
                } else if (!this.mSupportedColorModes.isEmpty()) {
                    Slog.m74e("LocalDisplayAdapter", "Default and active color mode is no longer available! Reverting to first available mode.");
                    this.mActiveColorMode = ((Integer) this.mSupportedColorModes.get(0)).intValue();
                } else {
                    Slog.m74e("LocalDisplayAdapter", "No color modes available!");
                }
            }
            return true;
        }

        public final boolean updateHdrCapabilitiesLocked(Display.HdrCapabilities hdrCapabilities) {
            if (Objects.equals(this.mHdrCapabilities, hdrCapabilities)) {
                return false;
            }
            this.mHdrCapabilities = hdrCapabilities;
            return true;
        }

        public final boolean updateAllmSupport(boolean z) {
            if (this.mAllmSupported == z) {
                return false;
            }
            this.mAllmSupported = z;
            return true;
        }

        public final boolean updateGameContentTypeSupport(boolean z) {
            if (this.mGameContentTypeSupported == z) {
                return false;
            }
            this.mGameContentTypeSupported = z;
            return true;
        }

        public final SurfaceControl.DisplayMode getModeById(SurfaceControl.DisplayMode[] displayModeArr, int i) {
            for (SurfaceControl.DisplayMode displayMode : displayModeArr) {
                if (displayMode.id == i) {
                    return displayMode;
                }
            }
            Slog.m74e("LocalDisplayAdapter", "Can't find display mode with id " + i);
            return null;
        }

        public final DisplayModeRecord findDisplayModeRecord(SurfaceControl.DisplayMode displayMode, List list) {
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                DisplayModeRecord displayModeRecord = (DisplayModeRecord) this.mSupportedModes.valueAt(i);
                if (displayModeRecord.hasMatchingMode(displayMode) && refreshRatesEquals(list, displayModeRecord.mMode.getAlternativeRefreshRates()) && LocalDisplayAdapter.this.hdrTypesEqual(displayMode.supportedHdrTypes, displayModeRecord.mMode.getSupportedHdrTypes())) {
                    return displayModeRecord;
                }
            }
            return null;
        }

        public final boolean refreshRatesEquals(List list, float[] fArr) {
            if (list.size() != fArr.length) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                if (Float.floatToIntBits(((Float) list.get(i)).floatValue()) != Float.floatToIntBits(fArr[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.display.DisplayDevice
        public void applyPendingDisplayDeviceInfoChangesLocked() {
            if (this.mHavePendingChanges) {
                this.mInfo = null;
                this.mHavePendingChanges = false;
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            SurfaceControl.DisplayMode displayMode;
            if (this.mInfo == null) {
                if (CoreRune.FW_VRR_RESOLUTION_POLICY && this.mStaticDisplayInfo.isInternal) {
                    displayMode = getModeById(this.mSfDisplayModes, findSfDisplayModeIdLocked(this.mDefaultModeId, this.mDefaultModeGroup));
                } else {
                    displayMode = this.mActiveSfDisplayMode;
                }
                DisplayDeviceInfo displayDeviceInfo = new DisplayDeviceInfo();
                this.mInfo = displayDeviceInfo;
                displayDeviceInfo.width = displayMode.width;
                displayDeviceInfo.height = displayMode.height;
                displayDeviceInfo.modeId = this.mActiveModeId;
                displayDeviceInfo.renderFrameRate = this.mActiveRenderFrameRate;
                displayDeviceInfo.defaultModeId = getPreferredModeId();
                this.mInfo.supportedModes = getDisplayModes(this.mSupportedModes);
                DisplayDeviceInfo displayDeviceInfo2 = this.mInfo;
                displayDeviceInfo2.colorMode = this.mActiveColorMode;
                displayDeviceInfo2.allmSupported = this.mAllmSupported;
                displayDeviceInfo2.gameContentTypeSupported = this.mGameContentTypeSupported;
                displayDeviceInfo2.supportedColorModes = new int[this.mSupportedColorModes.size()];
                for (int i = 0; i < this.mSupportedColorModes.size(); i++) {
                    this.mInfo.supportedColorModes[i] = ((Integer) this.mSupportedColorModes.get(i)).intValue();
                }
                DisplayDeviceInfo displayDeviceInfo3 = this.mInfo;
                displayDeviceInfo3.hdrCapabilities = this.mHdrCapabilities;
                displayDeviceInfo3.appVsyncOffsetNanos = displayMode.appVsyncOffsetNanos;
                displayDeviceInfo3.presentationDeadlineNanos = displayMode.presentationDeadlineNanos;
                displayDeviceInfo3.state = this.mState;
                displayDeviceInfo3.committedState = this.mCommittedState;
                displayDeviceInfo3.uniqueId = getUniqueId();
                DisplayAddress.Physical fromPhysicalDisplayId = DisplayAddress.fromPhysicalDisplayId(this.mPhysicalDisplayId);
                DisplayDeviceInfo displayDeviceInfo4 = this.mInfo;
                displayDeviceInfo4.address = fromPhysicalDisplayId;
                displayDeviceInfo4.densityDpi = getLogicalDensity();
                DisplayDeviceInfo displayDeviceInfo5 = this.mInfo;
                displayDeviceInfo5.xDpi = displayMode.xDpi;
                displayDeviceInfo5.yDpi = displayMode.yDpi;
                SurfaceControl.StaticDisplayInfo staticDisplayInfo = this.mStaticDisplayInfo;
                displayDeviceInfo5.deviceProductInfo = staticDisplayInfo.deviceProductInfo;
                if (staticDisplayInfo.secure) {
                    displayDeviceInfo5.flags = 12;
                }
                Resources resources = LocalDisplayAdapter.this.getOverlayContext().getResources();
                boolean z = this.mIsFirstDisplay;
                if (z) {
                    this.mInfo.flags |= 1;
                }
                if (z) {
                    if (resources.getBoolean(R.bool.config_is_powerbutton_fps) || (Build.IS_EMULATOR && SystemProperties.getBoolean("ro.emulator.circular", false))) {
                        this.mInfo.flags |= 256;
                    }
                } else if (this.mStaticDisplayInfo.isInternal) {
                    this.mInfo.flags |= 16777408;
                } else {
                    if (!resources.getBoolean(R.bool.config_intrusiveNotificationLed)) {
                        this.mInfo.flags |= 128;
                    }
                    if (isDisplayPrivate(fromPhysicalDisplayId)) {
                        this.mInfo.flags |= 16;
                    }
                }
                if (DisplayCutout.getMaskBuiltInDisplayCutout(resources, this.mInfo.uniqueId)) {
                    this.mInfo.flags |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
                }
                Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(this.mInfo.supportedModes);
                int physicalWidth = maximumResolutionDisplayMode == null ? this.mInfo.width : maximumResolutionDisplayMode.getPhysicalWidth();
                int physicalHeight = maximumResolutionDisplayMode == null ? this.mInfo.height : maximumResolutionDisplayMode.getPhysicalHeight();
                if (this.mIsFirstDisplay) {
                    DisplayDeviceInfo displayDeviceInfo6 = this.mInfo;
                    displayDeviceInfo6.displayCutout = DisplayCutout.fromResourcesRectApproximation(resources, displayDeviceInfo6.uniqueId, physicalWidth, physicalHeight, displayDeviceInfo6.width, displayDeviceInfo6.height, displayDeviceInfo6.densityDpi, false);
                }
                if (this.mIsFirstDisplay) {
                    DisplayDeviceInfo displayDeviceInfo7 = this.mInfo;
                    displayDeviceInfo7.roundedCorners = RoundedCorners.fromResources(resources, displayDeviceInfo7.uniqueId, physicalWidth, physicalHeight, displayDeviceInfo7.width, displayDeviceInfo7.height);
                }
                DisplayDeviceInfo displayDeviceInfo8 = this.mInfo;
                displayDeviceInfo8.installOrientation = this.mStaticDisplayInfo.installOrientation;
                displayDeviceInfo8.displayShape = DisplayShape.fromResources(resources, displayDeviceInfo8.uniqueId, physicalWidth, physicalHeight, displayDeviceInfo8.width, displayDeviceInfo8.height);
                this.mInfo.name = getDisplayDeviceConfig().getName();
                if (this.mStaticDisplayInfo.isInternal) {
                    DisplayDeviceInfo displayDeviceInfo9 = this.mInfo;
                    displayDeviceInfo9.type = 1;
                    displayDeviceInfo9.touch = 1;
                    displayDeviceInfo9.flags = 2 | displayDeviceInfo9.flags;
                    if (displayDeviceInfo9.name == null) {
                        displayDeviceInfo9.name = resources.getString(R.string.granularity_label_character);
                    }
                } else {
                    DisplayDeviceInfo displayDeviceInfo10 = this.mInfo;
                    displayDeviceInfo10.type = 2;
                    displayDeviceInfo10.touch = 2;
                    displayDeviceInfo10.flags |= 64;
                    if (displayDeviceInfo10.name == null) {
                        displayDeviceInfo10.name = LocalDisplayAdapter.this.getContext().getResources().getString(R.string.granularity_label_line);
                    }
                }
                DisplayDeviceInfo displayDeviceInfo11 = this.mInfo;
                displayDeviceInfo11.frameRateOverrides = this.mFrameRateOverrides;
                displayDeviceInfo11.flags |= IInstalld.FLAG_FORCE;
                displayDeviceInfo11.brightnessMinimum = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                displayDeviceInfo11.brightnessMaximum = 1.0f;
                displayDeviceInfo11.brightnessDefault = getDisplayDeviceConfig().getBrightnessDefault();
                this.mInfo.hdrSdrRatio = this.mCurrentHdrSdrRatio;
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public Runnable requestDisplayStateLocked(int i, float f, float f2) {
            return requestDisplayStateLocked(i, f, f2, 0, null);
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0057  */
        @Override // com.android.server.display.DisplayDevice
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Runnable requestDisplayStateLocked(final int i, final float f, final float f2, final int i2, final ArrayList arrayList) {
            int i3;
            int i4;
            boolean z = this.mState != i;
            final boolean z2 = (this.mBrightnessState == f && this.mSdrBrightnessState == f2) ? false : true;
            final long uptimeMillis = SystemClock.uptimeMillis();
            boolean z3 = this.mStateLimit != i2;
            if (!z && !z2 && !z3) {
                return null;
            }
            final long j = this.mPhysicalDisplayId;
            final IBinder displayTokenLocked = getDisplayTokenLocked();
            final int i5 = this.mState;
            DisplayDeviceInfo displayDeviceInfo = this.mInfo;
            if (displayDeviceInfo.type != 1) {
                i3 = -1;
            } else {
                if ((displayDeviceInfo.flags & 16777216) == 0) {
                    i4 = 1;
                    if (z) {
                        this.mState = i;
                        updateDeviceInfoLocked();
                    }
                    if (z3) {
                        this.mStateLimit = i2;
                    }
                    final boolean z4 = z;
                    final boolean z5 = z3;
                    final int i6 = i4;
                    return new Runnable() { // from class: com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.1
                        /* JADX WARN: Removed duplicated region for block: B:36:0x010a  */
                        /* JADX WARN: Removed duplicated region for block: B:39:0x0123  */
                        /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public void run() {
                            int i7;
                            int i8;
                            int i9 = LocalDisplayDevice.this.mState;
                            if (z4 || z5) {
                                int i10 = i2;
                                if (i10 != 0) {
                                    i9 = i10;
                                }
                                PowerManagerUtil.ScreenOnProfiler screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
                                screenOnProfiler.noteListenerStart();
                                LocalDisplayDevice localDisplayDevice = LocalDisplayDevice.this;
                                localDisplayDevice.notifyStateChangeStart(arrayList, localDisplayDevice.mState, i9, i6);
                                screenOnProfiler.noteListenerEnd();
                            }
                            int i11 = 3;
                            if (z5) {
                                int i12 = i2;
                                if (i12 == 1 || i12 == 2 || i12 == 3 || i12 == 4 || i12 == 6) {
                                    setDisplayState(i12);
                                } else {
                                    setDisplayState(LocalDisplayDevice.this.mState);
                                }
                                LocalDisplayDevice localDisplayDevice2 = LocalDisplayDevice.this;
                                localDisplayDevice2.notifyStateChangeFinish(arrayList, localDisplayDevice2.mState, i9, i6);
                            }
                            if (i2 != 0) {
                                if (z4) {
                                    Slog.m72d("LocalDisplayAdapter", "display_state_limit: " + Display.stateToString(i2) + " (id:" + j + " ) is limited, pending current request. " + Display.stateToString(LocalDisplayDevice.this.mState));
                                    if (!z5) {
                                        LocalDisplayDevice localDisplayDevice3 = LocalDisplayDevice.this;
                                        localDisplayDevice3.notifyStateChangeFinish(arrayList, localDisplayDevice3.mState, i9, i6);
                                    }
                                }
                                if (z2) {
                                    setDisplayBrightness(f, f2);
                                    LocalDisplayDevice.this.mBrightnessState = f;
                                    LocalDisplayDevice.this.mSdrBrightnessState = f2;
                                    return;
                                }
                                return;
                            }
                            int i13 = i5;
                            if (Display.isSuspendedState(i13) || i5 == 0) {
                                if (!Display.isSuspendedState(i)) {
                                    setDisplayState(i);
                                    i11 = i;
                                    LocalDisplayDevice localDisplayDevice4 = LocalDisplayDevice.this;
                                    localDisplayDevice4.notifyStateChangeFinish(arrayList, localDisplayDevice4.mState, i9, i6);
                                } else {
                                    int i14 = i;
                                    if (i14 == 4 || (i7 = i5) == 4) {
                                        setDisplayState(3);
                                    } else if (i14 == 6 || i7 == 6) {
                                        setDisplayState(2);
                                        i11 = 2;
                                    } else if (i7 != 0) {
                                        return;
                                    }
                                }
                                if (z2) {
                                    setDisplayBrightness(f, f2);
                                    LocalDisplayDevice.this.mBrightnessState = f;
                                    LocalDisplayDevice.this.mSdrBrightnessState = f2;
                                }
                                i8 = i;
                                if (i8 == i11) {
                                    setDisplayState(i8);
                                    LocalDisplayDevice localDisplayDevice5 = LocalDisplayDevice.this;
                                    localDisplayDevice5.notifyStateChangeFinish(arrayList, localDisplayDevice5.mState, i9, i6);
                                    return;
                                }
                                return;
                            }
                            i11 = i13;
                            if (z2) {
                            }
                            i8 = i;
                            if (i8 == i11) {
                            }
                        }

                        public final void setDisplayState(int i7) {
                            String str;
                            if (LocalDisplayDevice.this.mSidekickActive) {
                                Trace.traceBegin(131072L, "SidekickInternal#endDisplayControl");
                                try {
                                    LocalDisplayDevice.this.mSidekickInternal.endDisplayControl();
                                    Trace.traceEnd(131072L);
                                    LocalDisplayDevice.this.mSidekickActive = false;
                                } finally {
                                }
                            }
                            int powerModeForState = LocalDisplayAdapter.getPowerModeForState(i7);
                            Trace.traceBegin(131072L, "setDisplayState(id=" + j + ", state=" + Display.stateToString(i7) + ")");
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append("display_state - ");
                                sb.append(LocalDisplayDevice.this.mDisplayStateCount);
                                sb.append(" : ");
                                sb.append(Display.stateToString(LocalDisplayDevice.this.mRequestedState));
                                sb.append(" -> ");
                                sb.append(Display.stateToString(i7));
                                sb.append(" (");
                                sb.append(PowerManagerUtil.displayTypeToString(i6));
                                if (z5) {
                                    str = ",L:" + i2;
                                } else {
                                    str = "";
                                }
                                sb.append(str);
                                sb.append(")");
                                Slog.m73dk("LocalDisplayAdapter", sb.toString());
                                PowerManagerUtil.StopwatchLogger start = PowerManagerUtil.StopwatchLogger.start();
                                if (i7 == 2) {
                                    PowerManagerUtil.sCurrentScreenOnProfiler.noteDisplayStart(LocalDisplayDevice.this.mRequestedState);
                                } else if (i7 == 1) {
                                    PowerManagerUtil.sCurrentScreenOffProfiler.noteDisplayStart();
                                }
                                LocalDisplayAdapter.this.mSurfaceControlProxy.setDisplayPowerMode(displayTokenLocked, powerModeForState);
                                if (i7 == 2) {
                                    PowerManagerUtil.ScreenOnProfiler screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
                                    screenOnProfiler.noteDisplayEnd();
                                    screenOnProfiler.noteFrameStart(i6);
                                } else if (i7 == 1) {
                                    PowerManagerUtil.sCurrentScreenOffProfiler.noteDisplayEnd();
                                }
                                start.m69dk("LocalDisplayAdapter", "display_state - " + LocalDisplayDevice.this.mDisplayStateCount + " : " + Display.stateToString(i7) + " (" + PowerManagerUtil.displayTypeToString(i6) + ")");
                                LocalDisplayDevice localDisplayDevice = LocalDisplayDevice.this;
                                int i8 = localDisplayDevice.mDisplayStateCount + 1;
                                localDisplayDevice.mDisplayStateCount = i8;
                                if (i8 > 10000) {
                                    LocalDisplayDevice.this.mDisplayStateCount = 0;
                                }
                                LocalDisplayDevice.this.mRequestedState = i7;
                                Trace.traceCounter(131072L, "DisplayPowerMode", powerModeForState);
                                Trace.traceEnd(131072L);
                                setCommittedState(i7);
                                if (!Display.isSuspendedState(i7) || i7 == 1 || LocalDisplayDevice.this.mSidekickInternal == null || LocalDisplayDevice.this.mSidekickActive) {
                                    return;
                                }
                                Trace.traceBegin(131072L, "SidekickInternal#startDisplayControl");
                                try {
                                    LocalDisplayDevice localDisplayDevice2 = LocalDisplayDevice.this;
                                    localDisplayDevice2.mSidekickActive = localDisplayDevice2.mSidekickInternal.startDisplayControl(i7);
                                } finally {
                                }
                            } finally {
                            }
                        }

                        public final void setCommittedState(int i7) {
                            synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                                LocalDisplayDevice.this.mCommittedState = i7;
                                if (CoreRune.FW_VRR_PERFORMANCE && !LocalDisplayDevice.this.mStateChangeCallbacks.isEmpty()) {
                                    Iterator it = LocalDisplayDevice.this.mStateChangeCallbacks.iterator();
                                    while (it.hasNext()) {
                                        ((Runnable) it.next()).run();
                                    }
                                    LocalDisplayDevice.this.mStateChangeCallbacks.clear();
                                }
                                LocalDisplayDevice.this.updateDeviceInfoLocked();
                            }
                        }

                        public final void setDisplayBrightness(float f3, float f4) {
                            if (Float.isNaN(f3) || Float.isNaN(f4)) {
                                return;
                            }
                            LocalDisplayDevice.this.mLastBrightnessRequestedTime = SystemClock.uptimeMillis();
                            Trace.traceBegin(131072L, "setDisplayBrightness(id=" + j + ", brightnessState=" + f3 + ", sdrBrightnessState=" + f4 + ")");
                            try {
                                float brightnessToBacklight = brightnessToBacklight(f3);
                                float brightnessToBacklight2 = brightnessToBacklight(f4);
                                float backlightToNits = backlightToNits(brightnessToBacklight);
                                float backlightToNits2 = backlightToNits(brightnessToBacklight2);
                                LocalDisplayDevice.this.mBacklightAdapter.setBacklight(brightnessToBacklight2, backlightToNits2, brightnessToBacklight, backlightToNits, BrightnessSynchronizer.brightnessFloatToInt(f3), BrightnessSynchronizer.brightnessFloatToInt(f4));
                                Trace.traceCounter(131072L, "ScreenBrightness", BrightnessSynchronizer.brightnessFloatToInt(f3));
                                Trace.traceCounter(131072L, "SdrScreenBrightness", BrightnessSynchronizer.brightnessFloatToInt(f4));
                                if (LocalDisplayDevice.this.getDisplayDeviceConfig().hasSdrToHdrRatioSpline()) {
                                    handleHdrSdrNitsChanged(backlightToNits, backlightToNits2);
                                }
                            } finally {
                                Trace.traceEnd(131072L);
                            }
                        }

                        public final float brightnessToBacklight(float f3) {
                            return f3 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : LocalDisplayDevice.this.getDisplayDeviceConfig().getBacklightFromBrightness(f3);
                        }

                        public final float backlightToNits(float f3) {
                            return LocalDisplayDevice.this.getDisplayDeviceConfig().getNitsFromBacklight(f3);
                        }

                        public void handleHdrSdrNitsChanged(float f3, float f4) {
                            float max = (f3 == -1.0f || f4 == -1.0f) ? Float.NaN : Math.max(1.0f, f3 / f4);
                            if (BrightnessSynchronizer.floatEquals(LocalDisplayDevice.this.mCurrentHdrSdrRatio, max)) {
                                return;
                            }
                            synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                                LocalDisplayDevice.this.mCurrentHdrSdrRatio = max;
                                LocalDisplayDevice.this.updateDeviceInfoLocked();
                            }
                        }
                    };
                }
                i3 = 2;
            }
            i4 = i3;
            if (z) {
            }
            if (z3) {
            }
            final boolean z42 = z;
            final boolean z52 = z3;
            final int i62 = i4;
            return new Runnable() { // from class: com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.1
                /* JADX WARN: Removed duplicated region for block: B:36:0x010a  */
                /* JADX WARN: Removed duplicated region for block: B:39:0x0123  */
                /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    int i7;
                    int i8;
                    int i9 = LocalDisplayDevice.this.mState;
                    if (z42 || z52) {
                        int i10 = i2;
                        if (i10 != 0) {
                            i9 = i10;
                        }
                        PowerManagerUtil.ScreenOnProfiler screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
                        screenOnProfiler.noteListenerStart();
                        LocalDisplayDevice localDisplayDevice = LocalDisplayDevice.this;
                        localDisplayDevice.notifyStateChangeStart(arrayList, localDisplayDevice.mState, i9, i62);
                        screenOnProfiler.noteListenerEnd();
                    }
                    int i11 = 3;
                    if (z52) {
                        int i12 = i2;
                        if (i12 == 1 || i12 == 2 || i12 == 3 || i12 == 4 || i12 == 6) {
                            setDisplayState(i12);
                        } else {
                            setDisplayState(LocalDisplayDevice.this.mState);
                        }
                        LocalDisplayDevice localDisplayDevice2 = LocalDisplayDevice.this;
                        localDisplayDevice2.notifyStateChangeFinish(arrayList, localDisplayDevice2.mState, i9, i62);
                    }
                    if (i2 != 0) {
                        if (z42) {
                            Slog.m72d("LocalDisplayAdapter", "display_state_limit: " + Display.stateToString(i2) + " (id:" + j + " ) is limited, pending current request. " + Display.stateToString(LocalDisplayDevice.this.mState));
                            if (!z52) {
                                LocalDisplayDevice localDisplayDevice3 = LocalDisplayDevice.this;
                                localDisplayDevice3.notifyStateChangeFinish(arrayList, localDisplayDevice3.mState, i9, i62);
                            }
                        }
                        if (z2) {
                            setDisplayBrightness(f, f2);
                            LocalDisplayDevice.this.mBrightnessState = f;
                            LocalDisplayDevice.this.mSdrBrightnessState = f2;
                            return;
                        }
                        return;
                    }
                    int i13 = i5;
                    if (Display.isSuspendedState(i13) || i5 == 0) {
                        if (!Display.isSuspendedState(i)) {
                            setDisplayState(i);
                            i11 = i;
                            LocalDisplayDevice localDisplayDevice4 = LocalDisplayDevice.this;
                            localDisplayDevice4.notifyStateChangeFinish(arrayList, localDisplayDevice4.mState, i9, i62);
                        } else {
                            int i14 = i;
                            if (i14 == 4 || (i7 = i5) == 4) {
                                setDisplayState(3);
                            } else if (i14 == 6 || i7 == 6) {
                                setDisplayState(2);
                                i11 = 2;
                            } else if (i7 != 0) {
                                return;
                            }
                        }
                        if (z2) {
                            setDisplayBrightness(f, f2);
                            LocalDisplayDevice.this.mBrightnessState = f;
                            LocalDisplayDevice.this.mSdrBrightnessState = f2;
                        }
                        i8 = i;
                        if (i8 == i11) {
                            setDisplayState(i8);
                            LocalDisplayDevice localDisplayDevice5 = LocalDisplayDevice.this;
                            localDisplayDevice5.notifyStateChangeFinish(arrayList, localDisplayDevice5.mState, i9, i62);
                            return;
                        }
                        return;
                    }
                    i11 = i13;
                    if (z2) {
                    }
                    i8 = i;
                    if (i8 == i11) {
                    }
                }

                public final void setDisplayState(int i7) {
                    String str;
                    if (LocalDisplayDevice.this.mSidekickActive) {
                        Trace.traceBegin(131072L, "SidekickInternal#endDisplayControl");
                        try {
                            LocalDisplayDevice.this.mSidekickInternal.endDisplayControl();
                            Trace.traceEnd(131072L);
                            LocalDisplayDevice.this.mSidekickActive = false;
                        } finally {
                        }
                    }
                    int powerModeForState = LocalDisplayAdapter.getPowerModeForState(i7);
                    Trace.traceBegin(131072L, "setDisplayState(id=" + j + ", state=" + Display.stateToString(i7) + ")");
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("display_state - ");
                        sb.append(LocalDisplayDevice.this.mDisplayStateCount);
                        sb.append(" : ");
                        sb.append(Display.stateToString(LocalDisplayDevice.this.mRequestedState));
                        sb.append(" -> ");
                        sb.append(Display.stateToString(i7));
                        sb.append(" (");
                        sb.append(PowerManagerUtil.displayTypeToString(i62));
                        if (z52) {
                            str = ",L:" + i2;
                        } else {
                            str = "";
                        }
                        sb.append(str);
                        sb.append(")");
                        Slog.m73dk("LocalDisplayAdapter", sb.toString());
                        PowerManagerUtil.StopwatchLogger start = PowerManagerUtil.StopwatchLogger.start();
                        if (i7 == 2) {
                            PowerManagerUtil.sCurrentScreenOnProfiler.noteDisplayStart(LocalDisplayDevice.this.mRequestedState);
                        } else if (i7 == 1) {
                            PowerManagerUtil.sCurrentScreenOffProfiler.noteDisplayStart();
                        }
                        LocalDisplayAdapter.this.mSurfaceControlProxy.setDisplayPowerMode(displayTokenLocked, powerModeForState);
                        if (i7 == 2) {
                            PowerManagerUtil.ScreenOnProfiler screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
                            screenOnProfiler.noteDisplayEnd();
                            screenOnProfiler.noteFrameStart(i62);
                        } else if (i7 == 1) {
                            PowerManagerUtil.sCurrentScreenOffProfiler.noteDisplayEnd();
                        }
                        start.m69dk("LocalDisplayAdapter", "display_state - " + LocalDisplayDevice.this.mDisplayStateCount + " : " + Display.stateToString(i7) + " (" + PowerManagerUtil.displayTypeToString(i62) + ")");
                        LocalDisplayDevice localDisplayDevice = LocalDisplayDevice.this;
                        int i8 = localDisplayDevice.mDisplayStateCount + 1;
                        localDisplayDevice.mDisplayStateCount = i8;
                        if (i8 > 10000) {
                            LocalDisplayDevice.this.mDisplayStateCount = 0;
                        }
                        LocalDisplayDevice.this.mRequestedState = i7;
                        Trace.traceCounter(131072L, "DisplayPowerMode", powerModeForState);
                        Trace.traceEnd(131072L);
                        setCommittedState(i7);
                        if (!Display.isSuspendedState(i7) || i7 == 1 || LocalDisplayDevice.this.mSidekickInternal == null || LocalDisplayDevice.this.mSidekickActive) {
                            return;
                        }
                        Trace.traceBegin(131072L, "SidekickInternal#startDisplayControl");
                        try {
                            LocalDisplayDevice localDisplayDevice2 = LocalDisplayDevice.this;
                            localDisplayDevice2.mSidekickActive = localDisplayDevice2.mSidekickInternal.startDisplayControl(i7);
                        } finally {
                        }
                    } finally {
                    }
                }

                public final void setCommittedState(int i7) {
                    synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                        LocalDisplayDevice.this.mCommittedState = i7;
                        if (CoreRune.FW_VRR_PERFORMANCE && !LocalDisplayDevice.this.mStateChangeCallbacks.isEmpty()) {
                            Iterator it = LocalDisplayDevice.this.mStateChangeCallbacks.iterator();
                            while (it.hasNext()) {
                                ((Runnable) it.next()).run();
                            }
                            LocalDisplayDevice.this.mStateChangeCallbacks.clear();
                        }
                        LocalDisplayDevice.this.updateDeviceInfoLocked();
                    }
                }

                public final void setDisplayBrightness(float f3, float f4) {
                    if (Float.isNaN(f3) || Float.isNaN(f4)) {
                        return;
                    }
                    LocalDisplayDevice.this.mLastBrightnessRequestedTime = SystemClock.uptimeMillis();
                    Trace.traceBegin(131072L, "setDisplayBrightness(id=" + j + ", brightnessState=" + f3 + ", sdrBrightnessState=" + f4 + ")");
                    try {
                        float brightnessToBacklight = brightnessToBacklight(f3);
                        float brightnessToBacklight2 = brightnessToBacklight(f4);
                        float backlightToNits = backlightToNits(brightnessToBacklight);
                        float backlightToNits2 = backlightToNits(brightnessToBacklight2);
                        LocalDisplayDevice.this.mBacklightAdapter.setBacklight(brightnessToBacklight2, backlightToNits2, brightnessToBacklight, backlightToNits, BrightnessSynchronizer.brightnessFloatToInt(f3), BrightnessSynchronizer.brightnessFloatToInt(f4));
                        Trace.traceCounter(131072L, "ScreenBrightness", BrightnessSynchronizer.brightnessFloatToInt(f3));
                        Trace.traceCounter(131072L, "SdrScreenBrightness", BrightnessSynchronizer.brightnessFloatToInt(f4));
                        if (LocalDisplayDevice.this.getDisplayDeviceConfig().hasSdrToHdrRatioSpline()) {
                            handleHdrSdrNitsChanged(backlightToNits, backlightToNits2);
                        }
                    } finally {
                        Trace.traceEnd(131072L);
                    }
                }

                public final float brightnessToBacklight(float f3) {
                    return f3 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : LocalDisplayDevice.this.getDisplayDeviceConfig().getBacklightFromBrightness(f3);
                }

                public final float backlightToNits(float f3) {
                    return LocalDisplayDevice.this.getDisplayDeviceConfig().getNitsFromBacklight(f3);
                }

                public void handleHdrSdrNitsChanged(float f3, float f4) {
                    float max = (f3 == -1.0f || f4 == -1.0f) ? Float.NaN : Math.max(1.0f, f3 / f4);
                    if (BrightnessSynchronizer.floatEquals(LocalDisplayDevice.this.mCurrentHdrSdrRatio, max)) {
                        return;
                    }
                    synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                        LocalDisplayDevice.this.mCurrentHdrSdrRatio = max;
                        LocalDisplayDevice.this.updateDeviceInfoLocked();
                    }
                }
            };
        }

        @Override // com.android.server.display.DisplayDevice
        public void setUserPreferredDisplayModeLocked(Display.Mode mode) {
            Display.Mode findMode;
            int i;
            int preferredModeId = getPreferredModeId();
            this.mUserPreferredMode = mode;
            if (mode == null && (i = this.mSystemPreferredModeId) != -1) {
                this.mDefaultModeId = i;
            }
            if (mode != null && ((mode.isRefreshRateSet() || mode.isResolutionSet()) && (findMode = findMode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate())) != null)) {
                this.mUserPreferredMode = findMode;
            }
            this.mUserPreferredModeId = findUserPreferredModeIdLocked(this.mUserPreferredMode);
            if (preferredModeId == getPreferredModeId()) {
                return;
            }
            updateDeviceInfoLocked();
            if (LocalDisplayAdapter.this.mIsBootDisplayModeSupported) {
                if (this.mUserPreferredModeId == -1) {
                    LocalDisplayAdapter.this.mSurfaceControlProxy.clearBootDisplayMode(getDisplayTokenLocked());
                } else {
                    LocalDisplayAdapter.this.mSurfaceControlProxy.setBootDisplayMode(getDisplayTokenLocked(), findSfDisplayModeIdLocked(this.mUserPreferredMode.getModeId(), this.mDefaultModeGroup));
                }
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public Display.Mode getUserPreferredDisplayModeLocked() {
            return this.mUserPreferredMode;
        }

        @Override // com.android.server.display.DisplayDevice
        public Display.Mode getSystemPreferredDisplayModeLocked() {
            return findMode(this.mSystemPreferredModeId);
        }

        @Override // com.android.server.display.DisplayDevice
        public void setRequestedColorModeLocked(int i) {
            requestColorModeLocked(i);
        }

        @Override // com.android.server.display.DisplayDevice
        public void setDesiredDisplayModeSpecsLocked(DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            int i = desiredDisplayModeSpecs.baseModeId;
            if (i == 0) {
                return;
            }
            int findSfDisplayModeIdLocked = findSfDisplayModeIdLocked(i, this.mDefaultModeGroup);
            if (findSfDisplayModeIdLocked < 0) {
                Slog.m79w("LocalDisplayAdapter", "Ignoring request for invalid base mode id " + desiredDisplayModeSpecs.baseModeId);
                if (getDisplayDeviceInfoLocked().type != 1) {
                    updateDeviceInfoLocked();
                    return;
                }
                return;
            }
            if (this.mDisplayModeSpecsInvalid || !desiredDisplayModeSpecs.equals(this.mDisplayModeSpecs)) {
                this.mDisplayModeSpecsInvalid = false;
                this.mDisplayModeSpecs.copyFrom(desiredDisplayModeSpecs);
                Handler handler = LocalDisplayAdapter.this.getHandler();
                TriConsumer triConsumer = new TriConsumer() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda1
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((LocalDisplayAdapter.LocalDisplayDevice) obj).setDesiredDisplayModeSpecsAsync((IBinder) obj2, (SurfaceControl.DesiredDisplayModeSpecs) obj3);
                    }
                };
                IBinder displayTokenLocked = getDisplayTokenLocked();
                DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs2 = this.mDisplayModeSpecs;
                handler.sendMessage(PooledLambda.obtainMessage(triConsumer, this, displayTokenLocked, new SurfaceControl.DesiredDisplayModeSpecs(findSfDisplayModeIdLocked, desiredDisplayModeSpecs2.allowGroupSwitching, desiredDisplayModeSpecs2.primary, desiredDisplayModeSpecs2.appRequest)));
            }
        }

        public final void setDesiredDisplayModeSpecsAsync(final IBinder iBinder, final SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            if (CoreRune.FW_VRR_PERFORMANCE) {
                synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                    for (int i = 0; i < LocalDisplayAdapter.this.mDevices.size(); i++) {
                        LocalDisplayDevice localDisplayDevice = (LocalDisplayDevice) LocalDisplayAdapter.this.mDevices.valueAt(i);
                        int i2 = localDisplayDevice.mStateLimit;
                        if (i2 != 0) {
                            if (i2 != localDisplayDevice.mCommittedState) {
                                localDisplayDevice.mStateChangeCallbacks.add(new Runnable() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LocalDisplayAdapter.LocalDisplayDevice.this.lambda$setDesiredDisplayModeSpecsAsync$0(desiredDisplayModeSpecs, iBinder);
                                    }
                                });
                                return;
                            }
                        } else {
                            if (localDisplayDevice.mState != localDisplayDevice.mCommittedState) {
                                localDisplayDevice.mStateChangeCallbacks.add(new Runnable() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LocalDisplayAdapter.LocalDisplayDevice.this.lambda$setDesiredDisplayModeSpecsAsync$0(desiredDisplayModeSpecs, iBinder);
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
            }
            if (CoreRune.FW_VRR_POLICY) {
                EventLog.writeEvent(1290000, desiredDisplayModeSpecs.toString());
                Slog.m72d("LocalDisplayAdapter", "setDesiredDisplayModeSpecsAsync(" + this.mPhysicalDisplayId + ") : " + desiredDisplayModeSpecs);
            }
            LocalDisplayAdapter.this.mSurfaceControlProxy.setDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setDesiredDisplayModeSpecsAsync$0(SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs, IBinder iBinder) {
            EventLog.writeEvent(1290000, desiredDisplayModeSpecs.toString());
            Slog.m72d("LocalDisplayAdapter", "Run! setDesiredDisplayModeSpecsAsync(" + this.mPhysicalDisplayId + ") : " + desiredDisplayModeSpecs);
            LocalDisplayAdapter.this.mSurfaceControlProxy.setDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
        }

        @Override // com.android.server.display.DisplayDevice
        public void onOverlayChangedLocked() {
            updateDeviceInfoLocked();
        }

        public void onActiveDisplayModeChangedLocked(int i, float f) {
            if (updateActiveModeLocked(i, f)) {
                updateDeviceInfoLocked();
            }
        }

        public void onFrameRateOverridesChanged(DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            if (updateFrameRateOverridesLocked(frameRateOverrideArr)) {
                updateDeviceInfoLocked();
            }
        }

        public boolean updateActiveModeLocked(int i, float f) {
            SurfaceControl.DisplayMode displayMode;
            int i2;
            if (this.mActiveSfDisplayMode.id == i && this.mActiveRenderFrameRate == f) {
                return false;
            }
            this.mActiveSfDisplayMode = getModeById(this.mSfDisplayModes, i);
            int findMatchingModeIdLocked = findMatchingModeIdLocked(i);
            this.mActiveModeId = findMatchingModeIdLocked;
            if (findMatchingModeIdLocked == -1) {
                Slog.m79w("LocalDisplayAdapter", "In unknown mode after setting allowed modes, activeModeId=" + i);
            } else if (CoreRune.FW_VRR_POLICY) {
                Slog.m72d("LocalDisplayAdapter", "updateActiveModeLocked for d=" + this.mPhysicalDisplayId + ", mActiveModeId=" + this.mActiveModeId + ", mActiveSfDisplayMode=" + this.mActiveSfDisplayMode);
            }
            if (CoreRune.FW_VRR_RESOLUTION_POLICY && this.mIsFirstDisplay && (displayMode = this.mActiveSfDisplayMode) != null && this.mLastResolution != (i2 = displayMode.width * displayMode.height)) {
                this.mLastResolution = i2;
                this.mDisplayModeSpecsInvalid = true;
                Slog.m72d("LocalDisplayAdapter", "Reset modespecs due to resolution change!");
            }
            this.mActiveRenderFrameRate = f;
            return true;
        }

        public boolean updateFrameRateOverridesLocked(DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            if (Arrays.equals(frameRateOverrideArr, this.mFrameRateOverrides)) {
                return false;
            }
            this.mFrameRateOverrides = frameRateOverrideArr;
            return true;
        }

        public void requestColorModeLocked(int i) {
            if (this.mActiveColorMode == i) {
                return;
            }
            if (!this.mSupportedColorModes.contains(Integer.valueOf(i))) {
                Slog.m79w("LocalDisplayAdapter", "Unable to find color mode " + i + ", ignoring request.");
                return;
            }
            this.mActiveColorMode = i;
            LocalDisplayAdapter.this.getHandler().sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda0
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((LocalDisplayAdapter.LocalDisplayDevice) obj).requestColorModeAsync((IBinder) obj2, ((Integer) obj3).intValue());
                }
            }, this, getDisplayTokenLocked(), Integer.valueOf(i)));
        }

        public final void requestColorModeAsync(IBinder iBinder, int i) {
            LocalDisplayAdapter.this.mSurfaceControlProxy.setActiveColorMode(iBinder, i);
            synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                updateDeviceInfoLocked();
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public void setAutoLowLatencyModeLocked(boolean z) {
            if (this.mAllmRequested == z) {
                return;
            }
            this.mAllmRequested = z;
            if (!this.mAllmSupported) {
                Slog.m72d("LocalDisplayAdapter", "Unable to set ALLM because the connected display does not support ALLM.");
            } else {
                LocalDisplayAdapter.this.mSurfaceControlProxy.setAutoLowLatencyMode(getDisplayTokenLocked(), z);
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public void setGameContentTypeLocked(boolean z) {
            if (this.mGameContentTypeRequested == z) {
                return;
            }
            this.mGameContentTypeRequested = z;
            LocalDisplayAdapter.this.mSurfaceControlProxy.setGameContentType(getDisplayTokenLocked(), z);
        }

        @Override // com.android.server.display.DisplayDevice
        public boolean isFirstDisplay() {
            return this.mIsFirstDisplay;
        }

        @Override // com.android.server.display.DisplayDevice
        public void dumpLocked(PrintWriter printWriter) {
            super.dumpLocked(printWriter);
            printWriter.println("mPhysicalDisplayId=" + this.mPhysicalDisplayId);
            printWriter.println("mDisplayModeSpecs={" + this.mDisplayModeSpecs + "}");
            StringBuilder sb = new StringBuilder();
            sb.append("mDisplayModeSpecsInvalid=");
            sb.append(this.mDisplayModeSpecsInvalid);
            printWriter.println(sb.toString());
            printWriter.println("mActiveModeId=" + this.mActiveModeId);
            printWriter.println("mActiveColorMode=" + this.mActiveColorMode);
            printWriter.println("mDefaultModeId=" + this.mDefaultModeId);
            printWriter.println("mUserPreferredModeId=" + this.mUserPreferredModeId);
            printWriter.println("mState=" + Display.stateToString(this.mState));
            printWriter.println("mCommittedState=" + Display.stateToString(this.mCommittedState));
            printWriter.println("mBrightnessState=" + this.mBrightnessState);
            printWriter.println("mBacklightAdapter=" + this.mBacklightAdapter);
            printWriter.println("mAllmSupported=" + this.mAllmSupported);
            printWriter.println("mAllmRequested=" + this.mAllmRequested);
            printWriter.println("mGameContentTypeSupported=" + this.mGameContentTypeSupported);
            printWriter.println("mGameContentTypeRequested=" + this.mGameContentTypeRequested);
            printWriter.println("mStaticDisplayInfo=" + this.mStaticDisplayInfo);
            printWriter.println("mSfDisplayModes=");
            for (SurfaceControl.DisplayMode displayMode : this.mSfDisplayModes) {
                printWriter.println("  " + displayMode);
            }
            printWriter.println("mActiveSfDisplayMode=" + this.mActiveSfDisplayMode);
            printWriter.println("mActiveRenderFrameRate=" + this.mActiveRenderFrameRate);
            printWriter.println("mSupportedModes=");
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                printWriter.println("  " + this.mSupportedModes.valueAt(i));
            }
            printWriter.println("mSupportedColorModes=" + this.mSupportedColorModes);
            printWriter.println("mDisplayDeviceConfig=" + this.mDisplayDeviceConfig);
        }

        public final int findSfDisplayModeIdLocked(int i, int i2) {
            DisplayModeRecord displayModeRecord = (DisplayModeRecord) this.mSupportedModes.get(i);
            if (displayModeRecord == null) {
                return -1;
            }
            int i3 = -1;
            for (SurfaceControl.DisplayMode displayMode : this.mSfDisplayModes) {
                if (displayModeRecord.hasMatchingMode(displayMode)) {
                    if (i3 == -1) {
                        i3 = displayMode.id;
                    }
                    if (displayMode.group == i2) {
                        return displayMode.id;
                    }
                }
            }
            return i3;
        }

        public final Display.Mode findMode(int i) {
            for (int i2 = 0; i2 < this.mSupportedModes.size(); i2++) {
                Display.Mode mode = ((DisplayModeRecord) this.mSupportedModes.valueAt(i2)).mMode;
                if (mode.getModeId() == i) {
                    return mode;
                }
            }
            return null;
        }

        public final Display.Mode findMode(int i, int i2, float f) {
            for (int i3 = 0; i3 < this.mSupportedModes.size(); i3++) {
                Display.Mode mode = ((DisplayModeRecord) this.mSupportedModes.valueAt(i3)).mMode;
                if (mode.matchesIfValid(i, i2, f)) {
                    return mode;
                }
            }
            return null;
        }

        public final int findUserPreferredModeIdLocked(Display.Mode mode) {
            if (mode == null) {
                return -1;
            }
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                Display.Mode mode2 = ((DisplayModeRecord) this.mSupportedModes.valueAt(i)).mMode;
                if (mode.matches(mode2.getPhysicalWidth(), mode2.getPhysicalHeight(), mode2.getRefreshRate())) {
                    return mode2.getModeId();
                }
            }
            return -1;
        }

        public final int findMatchingModeIdLocked(int i) {
            SurfaceControl.DisplayMode modeById = getModeById(this.mSfDisplayModes, i);
            if (modeById == null) {
                Slog.m74e("LocalDisplayAdapter", "Invalid display mode ID " + i);
                return -1;
            }
            for (int i2 = 0; i2 < this.mSupportedModes.size(); i2++) {
                DisplayModeRecord displayModeRecord = (DisplayModeRecord) this.mSupportedModes.valueAt(i2);
                if (displayModeRecord.hasMatchingMode(modeById)) {
                    return displayModeRecord.mMode.getModeId();
                }
            }
            return -1;
        }

        public final void updateDeviceInfoLocked() {
            this.mInfo = null;
            LocalDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
        }

        public final Display.Mode[] getDisplayModes(SparseArray sparseArray) {
            int size = sparseArray.size();
            Display.Mode[] modeArr = new Display.Mode[size];
            for (int i = 0; i < size; i++) {
                modeArr[i] = ((DisplayModeRecord) sparseArray.valueAt(i)).mMode;
            }
            return modeArr;
        }

        public final boolean isDisplayPrivate(DisplayAddress.Physical physical) {
            int[] intArray;
            if (physical != null && (intArray = LocalDisplayAdapter.this.getOverlayContext().getResources().getIntArray(R.array.supported_locales)) != null) {
                int port = physical.getPort();
                for (int i : intArray) {
                    if (i == port) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final void notifyStateChangeStart(ArrayList arrayList, final int i, final int i2, final int i3) {
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            final PowerManagerUtil.StopwatchLogger start = PowerManagerUtil.StopwatchLogger.start();
            arrayList.forEach(new Consumer() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LocalDisplayAdapter.LocalDisplayDevice.lambda$notifyStateChangeStart$1(PowerManagerUtil.StopwatchLogger.this, i, i2, i3, (DisplayManagerInternal.DisplayStateListener) obj);
                }
            });
        }

        public static /* synthetic */ void lambda$notifyStateChangeStart$1(PowerManagerUtil.StopwatchLogger stopwatchLogger, int i, int i2, int i3, DisplayManagerInternal.DisplayStateListener displayStateListener) {
            stopwatchLogger.resetStartTime();
            displayStateListener.onStart(i, i2, i3);
            stopwatchLogger.m68d("LocalDisplayAdapter", "notifyStateChangeStart: " + displayStateListener);
        }

        public final void notifyStateChangeFinish(ArrayList arrayList, final int i, final int i2, final int i3) {
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            final PowerManagerUtil.StopwatchLogger start = PowerManagerUtil.StopwatchLogger.start();
            arrayList.forEach(new Consumer() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LocalDisplayAdapter.LocalDisplayDevice.lambda$notifyStateChangeFinish$2(PowerManagerUtil.StopwatchLogger.this, i, i2, i3, (DisplayManagerInternal.DisplayStateListener) obj);
                }
            });
        }

        public static /* synthetic */ void lambda$notifyStateChangeFinish$2(PowerManagerUtil.StopwatchLogger stopwatchLogger, int i, int i2, int i3, DisplayManagerInternal.DisplayStateListener displayStateListener) {
            stopwatchLogger.resetStartTime();
            displayStateListener.onFinish(i, i2, i3);
            stopwatchLogger.m68d("LocalDisplayAdapter", "notifyStateChangeFinish: " + displayStateListener);
        }
    }

    public final boolean hdrTypesEqual(int[] iArr, int[] iArr2) {
        int[] copyOf = Arrays.copyOf(iArr, iArr.length);
        Arrays.sort(copyOf);
        return Arrays.equals(copyOf, iArr2);
    }

    public Context getOverlayContext() {
        if (this.mOverlayContext == null) {
            this.mOverlayContext = ActivityThread.currentActivityThread().getSystemUiContext();
        }
        return this.mOverlayContext;
    }

    public final class DisplayModeRecord {
        public final Display.Mode mMode;

        public DisplayModeRecord(SurfaceControl.DisplayMode displayMode, float[] fArr) {
            this.mMode = DisplayAdapter.createMode(displayMode.width, displayMode.height, displayMode.refreshRate, fArr, displayMode.supportedHdrTypes);
        }

        public boolean hasMatchingMode(SurfaceControl.DisplayMode displayMode) {
            return this.mMode.getPhysicalWidth() == displayMode.width && this.mMode.getPhysicalHeight() == displayMode.height && Float.floatToIntBits(this.mMode.getRefreshRate()) == Float.floatToIntBits(displayMode.refreshRate);
        }

        public String toString() {
            return "DisplayModeRecord{mMode=" + this.mMode + "}";
        }
    }

    public class Injector {
        public ProxyDisplayEventReceiver mReceiver;

        public void setDisplayEventListenerLocked(Looper looper, DisplayEventListener displayEventListener) {
            this.mReceiver = new ProxyDisplayEventReceiver(looper, displayEventListener);
        }

        public SurfaceControlProxy getSurfaceControlProxy() {
            return new SurfaceControlProxy();
        }
    }

    public final class ProxyDisplayEventReceiver extends DisplayEventReceiver {
        public final DisplayEventListener mListener;

        public ProxyDisplayEventReceiver(Looper looper, DisplayEventListener displayEventListener) {
            super(looper, 0, 3);
            this.mListener = displayEventListener;
        }

        public void onHotplug(long j, long j2, boolean z) {
            this.mListener.onHotplug(j, j2, z);
        }

        public void onModeChanged(long j, long j2, int i, long j3) {
            this.mListener.onModeChanged(j, j2, i, j3);
        }

        public void onFrameRateOverridesChanged(long j, long j2, DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            this.mListener.onFrameRateOverridesChanged(j, j2, frameRateOverrideArr);
        }
    }

    public final class LocalDisplayEventListener implements DisplayEventListener {
        public LocalDisplayEventListener() {
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onHotplug(long j, long j2, boolean z) {
            synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                if (z) {
                    LocalDisplayAdapter.this.tryConnectDisplayLocked(j2);
                } else {
                    LocalDisplayAdapter.this.tryDisconnectDisplayLocked(j2);
                }
            }
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onModeChanged(long j, long j2, int i, long j3) {
            synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                LocalDisplayDevice localDisplayDevice = (LocalDisplayDevice) LocalDisplayAdapter.this.mDevices.get(j2);
                if (localDisplayDevice == null) {
                    return;
                }
                localDisplayDevice.onActiveDisplayModeChangedLocked(i, 1.0E9f / j3);
            }
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onFrameRateOverridesChanged(long j, long j2, DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            synchronized (LocalDisplayAdapter.this.getSyncRoot()) {
                LocalDisplayDevice localDisplayDevice = (LocalDisplayDevice) LocalDisplayAdapter.this.mDevices.get(j2);
                if (localDisplayDevice == null) {
                    return;
                }
                localDisplayDevice.onFrameRateOverridesChanged(frameRateOverrideArr);
            }
        }
    }

    public class SurfaceControlProxy {
        public SurfaceControl.DynamicDisplayInfo getDynamicDisplayInfo(long j) {
            return SurfaceControl.getDynamicDisplayInfo(j);
        }

        public long[] getPhysicalDisplayIds() {
            return DisplayControl.getPhysicalDisplayIds();
        }

        public IBinder getPhysicalDisplayToken(long j) {
            return DisplayControl.getPhysicalDisplayToken(j);
        }

        public SurfaceControl.StaticDisplayInfo getStaticDisplayInfo(long j) {
            return SurfaceControl.getStaticDisplayInfo(j);
        }

        public SurfaceControl.DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(IBinder iBinder) {
            return SurfaceControl.getDesiredDisplayModeSpecs(iBinder);
        }

        public boolean setDesiredDisplayModeSpecs(IBinder iBinder, SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            return SurfaceControl.setDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
        }

        public void setDisplayPowerMode(IBinder iBinder, int i) {
            SurfaceControl.setDisplayPowerMode(iBinder, i);
        }

        public boolean setActiveColorMode(IBinder iBinder, int i) {
            return SurfaceControl.setActiveColorMode(iBinder, i);
        }

        public boolean getBootDisplayModeSupport() {
            Trace.traceBegin(32L, "getBootDisplayModeSupport");
            try {
                return SurfaceControl.getBootDisplayModeSupport();
            } finally {
                Trace.traceEnd(32L);
            }
        }

        public void setBootDisplayMode(IBinder iBinder, int i) {
            SurfaceControl.setBootDisplayMode(iBinder, i);
        }

        public void clearBootDisplayMode(IBinder iBinder) {
            SurfaceControl.clearBootDisplayMode(iBinder);
        }

        public void setAutoLowLatencyMode(IBinder iBinder, boolean z) {
            SurfaceControl.setAutoLowLatencyMode(iBinder, z);
        }

        public void setGameContentType(IBinder iBinder, boolean z) {
            SurfaceControl.setGameContentType(iBinder, z);
        }

        public boolean getDisplayBrightnessSupport(IBinder iBinder) {
            return SurfaceControl.getDisplayBrightnessSupport(iBinder);
        }

        public boolean setDisplayBrightness(IBinder iBinder, float f) {
            return SurfaceControl.setDisplayBrightness(iBinder, f);
        }

        public boolean setDisplayBrightness(IBinder iBinder, float f, float f2, float f3, float f4) {
            return SurfaceControl.setDisplayBrightness(iBinder, f, f2, f3, f4);
        }
    }

    public class BacklightAdapter {
        public final LogicalLight mBacklight;
        public final IBinder mDisplayToken;
        public boolean mForceSurfaceControl = false;
        public final boolean mIsFirstDisplay;
        public final SurfaceControlProxy mSurfaceControlProxy;
        public final boolean mUseSurfaceControlBrightness;

        public BacklightAdapter(IBinder iBinder, boolean z, SurfaceControlProxy surfaceControlProxy, long j) {
            this.mDisplayToken = iBinder;
            this.mSurfaceControlProxy = surfaceControlProxy;
            boolean displayBrightnessSupport = surfaceControlProxy.getDisplayBrightnessSupport(iBinder);
            this.mUseSurfaceControlBrightness = displayBrightnessSupport;
            this.mIsFirstDisplay = z;
            SurfaceControl.StaticDisplayInfo staticDisplayInfo = surfaceControlProxy.getStaticDisplayInfo(j);
            boolean z2 = staticDisplayInfo != null && staticDisplayInfo.isInternal;
            if (!displayBrightnessSupport && z) {
                this.mBacklight = ((LightsManager) LocalServices.getService(LightsManager.class)).getLight(0);
            } else if (!displayBrightnessSupport && z2) {
                this.mBacklight = ((LightsManager) LocalServices.getService(LightsManager.class)).getLight(9);
            } else {
                this.mBacklight = null;
            }
        }

        public void setBacklight(float f, float f2, float f3, float f4, int i, int i2) {
            if (this.mUseSurfaceControlBrightness || this.mForceSurfaceControl) {
                if (BrightnessSynchronizer.floatEquals(f, Float.NaN)) {
                    this.mSurfaceControlProxy.setDisplayBrightness(this.mDisplayToken, f3);
                    return;
                }
                Slog.m72d("LocalDisplayAdapter", "surface lcd : " + PowerManagerUtil.brightnessToString(i, f3) + ", " + PowerManagerUtil.brightnessToString(i2, f) + ", " + PowerManagerUtil.displayTypeToString(this.mIsFirstDisplay) + " +");
                this.mSurfaceControlProxy.setDisplayBrightness(this.mDisplayToken, f, f2, f3, f4);
                Slog.m72d("LocalDisplayAdapter", "surface lcd : " + PowerManagerUtil.brightnessToString(i, f3) + ", " + PowerManagerUtil.brightnessToString(i2, f) + ", " + PowerManagerUtil.displayTypeToString(this.mIsFirstDisplay) + " -");
                return;
            }
            LogicalLight logicalLight = this.mBacklight;
            if (logicalLight != null) {
                logicalLight.setBrightness(f3);
            }
        }

        public void setForceSurfaceControl(boolean z) {
            this.mForceSurfaceControl = z;
        }

        public String toString() {
            return "BacklightAdapter [useSurfaceControl=" + this.mUseSurfaceControlBrightness + " (force_anyway? " + this.mForceSurfaceControl + "), backlight=" + this.mBacklight + "]";
        }
    }
}
