package com.android.server.display;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayDeviceRepository;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.layout.Layout;
import com.android.server.display.mode.SyntheticModeManager;
import com.android.server.display.utils.DebugUtils;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.utils.FoldSettingProvider;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LogicalDisplayMapper implements DisplayDeviceRepository.Listener {
    public static final boolean DEBUG = DebugUtils.isDebuggable("LogicalDisplayMapper");
    public static int sNextNonDefaultDisplayId = 5;
    public boolean mBootCompleted;
    public Layout mCurrentLayout;
    public final SparseIntArray mDeviceDisplayGroupIds;
    public int mDeviceState;
    public int mDeviceStateToBeAppliedAfterBoot;
    public final DeviceStateToLayoutMap mDeviceStateToLayoutMap;
    public final SparseBooleanArray mDeviceStatesOnWhichToSelectiveSleep;
    public final SparseBooleanArray mDeviceStatesOnWhichToWakeUp;
    public final DisplayDeviceRepository mDisplayDeviceRepo;
    public final ArrayMap mDisplayGroupIdsByName;
    public final SparseArray mDisplayGroups;
    public final SparseIntArray mDisplayGroupsToUpdate;
    public final SparseBooleanArray mDisplaysEnabledCache;
    public final DisplayManagerFlags mFlags;
    public final FoldGracePeriodProvider mFoldGracePeriodProvider;
    public final FoldSettingProvider mFoldSettingProvider;
    public final LogicalDisplayMapperHandler mHandler;
    public LogicalDisplay mHiddenSpaceDisplay;
    public final LogicalDisplayMapper$$ExternalSyntheticLambda2 mIdProducer;
    public boolean mInteractive;
    public final DisplayManagerService.AnonymousClass1 mListener;
    public final SparseArray mLogicalDisplays;
    public final SparseIntArray mLogicalDisplaysToUpdate;
    public int mNextNonDefaultGroupId;
    public int mPendingDeviceState;
    public final PowerManager mPowerManager;
    public final boolean mSingleDisplayDemoMode;
    public final DisplayManagerService.SyncRoot mSyncRoot;
    public final SyntheticModeManager mSyntheticModeManager;
    public final DisplayInfo mTempDisplayInfo;
    public final DisplayInfo mTempNonOverrideDisplayInfo;
    public final SparseIntArray mUpdatedDisplayGroups;
    public final SparseIntArray mUpdatedLogicalDisplays;
    public final ArrayMap mVirtualDeviceDisplayMapping;
    public WindowManagerPolicy mWindowManagerPolicy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogicalDisplayMapperHandler extends Handler {
        public LogicalDisplayMapperHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            synchronized (LogicalDisplayMapper.this.mSyncRoot) {
                LogicalDisplayMapper.this.finishStateTransitionLocked(true);
            }
        }
    }

    public LogicalDisplayMapper(Context context, FoldSettingProvider foldSettingProvider, FoldGracePeriodProvider foldGracePeriodProvider, DisplayDeviceRepository displayDeviceRepository, DisplayManagerService.AnonymousClass1 anonymousClass1, DisplayManagerService.SyncRoot syncRoot, DisplayManagerService.DisplayManagerHandler displayManagerHandler, DisplayManagerFlags displayManagerFlags) {
        DeviceStateToLayoutMap deviceStateToLayoutMap = new DeviceStateToLayoutMap(new LogicalDisplayMapper$$ExternalSyntheticLambda2(0), displayManagerFlags);
        SyntheticModeManager syntheticModeManager = new SyntheticModeManager(displayManagerFlags);
        this.mTempDisplayInfo = new DisplayInfo();
        this.mTempNonOverrideDisplayInfo = new DisplayInfo();
        this.mLogicalDisplays = new SparseArray();
        this.mDisplaysEnabledCache = new SparseBooleanArray();
        this.mDisplayGroups = new SparseArray();
        this.mDeviceDisplayGroupIds = new SparseIntArray();
        this.mDisplayGroupIdsByName = new ArrayMap();
        this.mHiddenSpaceDisplay = null;
        this.mUpdatedLogicalDisplays = new SparseIntArray();
        this.mUpdatedDisplayGroups = new SparseIntArray();
        this.mLogicalDisplaysToUpdate = new SparseIntArray();
        this.mDisplayGroupsToUpdate = new SparseIntArray();
        this.mVirtualDeviceDisplayMapping = new ArrayMap();
        this.mNextNonDefaultGroupId = 5;
        this.mIdProducer = new LogicalDisplayMapper$$ExternalSyntheticLambda2(1);
        this.mCurrentLayout = null;
        this.mDeviceState = -1;
        this.mPendingDeviceState = -1;
        this.mDeviceStateToBeAppliedAfterBoot = -1;
        this.mBootCompleted = false;
        this.mSyncRoot = syncRoot;
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mPowerManager = powerManager;
        this.mInteractive = powerManager.isInteractive();
        this.mHandler = new LogicalDisplayMapperHandler(displayManagerHandler.getLooper());
        this.mDisplayDeviceRepo = displayDeviceRepository;
        this.mListener = anonymousClass1;
        this.mFoldSettingProvider = foldSettingProvider;
        this.mFoldGracePeriodProvider = foldGracePeriodProvider;
        this.mSingleDisplayDemoMode = SystemProperties.getBoolean("persist.demo.singledisplay", false);
        context.getResources().getBoolean(R.bool.config_systemCaptionsServiceCallsEnabled);
        int[] intArray = context.getResources().getIntArray(R.array.config_telephonyEuiccDeviceCapabilities);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(2);
        for (int i = 0; intArray != null && i < intArray.length; i++) {
            sparseBooleanArray.put(intArray[i], true);
        }
        this.mDeviceStatesOnWhichToWakeUp = sparseBooleanArray;
        int[] intArray2 = context.getResources().getIntArray(R.array.config_system_condition_providers);
        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray(2);
        for (int i2 = 0; intArray2 != null && i2 < intArray2.length; i2++) {
            sparseBooleanArray2.put(intArray2[i2], true);
        }
        this.mDeviceStatesOnWhichToSelectiveSleep = sparseBooleanArray2;
        ((ArrayList) displayDeviceRepository.mListeners).add(this);
        this.mDeviceStateToLayoutMap = deviceStateToLayoutMap;
        this.mFlags = displayManagerFlags;
        this.mSyntheticModeManager = syntheticModeManager;
    }

    public final void applyLayoutLocked() {
        boolean z;
        WindowManagerPolicy windowManagerPolicy;
        Layout layout = this.mCurrentLayout;
        this.mCurrentLayout = this.mDeviceStateToLayoutMap.get(this.mDeviceState);
        Slog.i("LogicalDisplayMapper", "Applying layout: " + this.mCurrentLayout + ", Previous layout: " + layout);
        int size = ((ArrayList) this.mCurrentLayout.mDisplays).size();
        for (int i = 0; i < size; i++) {
            Layout.Display display = (Layout.Display) ((ArrayList) this.mCurrentLayout.mDisplays).get(i);
            DisplayAddress displayAddress = display.mAddress;
            DisplayDevice byAddressLocked = this.mDisplayDeviceRepo.getByAddressLocked(displayAddress);
            if (byAddressLocked == null) {
                StringBuilder sb = new StringBuilder("applyLayoutLocked: The display device (");
                sb.append(displayAddress);
                sb.append("), is not available for the display state ");
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, this.mDeviceState, "LogicalDisplayMapper");
            } else {
                int i2 = display.mLogicalDisplayId;
                LogicalDisplay displayLocked = getDisplayLocked(i2, true);
                if (displayLocked == null) {
                    displayLocked = createNewLogicalDisplayLocked(null, i2);
                    z = true;
                } else {
                    z = false;
                }
                LogicalDisplay displayLocked2 = getDisplayLocked(byAddressLocked);
                int i3 = displayLocked.mDisplayId;
                if (displayLocked != displayLocked2) {
                    if (!z && (windowManagerPolicy = this.mWindowManagerPolicy) != null) {
                        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) windowManagerPolicy;
                        if (i3 == 0) {
                            phoneWindowManager.mDefaultDisplayPolicy.mDisplayContent.mDisplayUpdater.onDisplaySwitching(true);
                        }
                    }
                    displayLocked.setPrimaryDisplayDeviceLocked(displayLocked2.setPrimaryDisplayDeviceLocked(displayLocked.mPrimaryDisplayDevice));
                }
                DisplayDeviceConfig displayDeviceConfig = byAddressLocked.getDisplayDeviceConfig();
                int i4 = displayLocked.mDevicePosition;
                int i5 = display.mPosition;
                if (i4 != i5) {
                    displayLocked.mDevicePosition = i5;
                    displayLocked.mDirty = true;
                }
                int i6 = display.mLeadDisplayId;
                if (i3 != displayLocked.mLeadDisplayId && i3 != i6) {
                    displayLocked.mLeadDisplayId = i6;
                }
                displayDeviceConfig.getClass();
                String str = display.mRefreshRateZoneId;
                SurfaceControl.RefreshRateRange refreshRateRange = TextUtils.isEmpty(str) ? null : (SurfaceControl.RefreshRateRange) ((HashMap) displayDeviceConfig.mRefreshRateZoneProfiles).get(str);
                if (!Objects.equals(refreshRateRange, displayLocked.mLayoutLimitedRefreshRate)) {
                    displayLocked.mLayoutLimitedRefreshRate = refreshRateRange;
                    displayLocked.mDirty = true;
                }
                String str2 = display.mThermalRefreshRateThrottlingMapId;
                if (str2 == null) {
                    str2 = "default";
                }
                SparseArray<?> sparseArray = (SparseArray) ((HashMap) displayDeviceConfig.mRefreshRateThrottlingMap).get(str2);
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                if (!displayLocked.mThermalRefreshRateThrottling.contentEquals(sparseArray)) {
                    displayLocked.mThermalRefreshRateThrottling = sparseArray;
                    displayLocked.mDirty = true;
                }
                setEnabledLocked(displayLocked, display.mIsEnabled);
                String str3 = display.mThermalBrightnessThrottlingMapId;
                if (str3 == null) {
                    str3 = "default";
                }
                if (!str3.equals(displayLocked.mThermalBrightnessThrottlingDataId)) {
                    displayLocked.mThermalBrightnessThrottlingDataId = str3;
                    displayLocked.mDirty = true;
                }
                String str4 = display.mPowerThrottlingMapId;
                String str5 = str4 != null ? str4 : "default";
                if (!str5.equals(displayLocked.mPowerThrottlingDataId)) {
                    displayLocked.mPowerThrottlingDataId = str5;
                    displayLocked.mDirty = true;
                }
                displayLocked.mDisplayGroupName = display.mDisplayGroupName;
            }
        }
    }

    public final boolean areAllTransitioningDisplaysOffLocked() {
        DisplayDevice displayDevice;
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            if (logicalDisplay.mIsInTransition && (displayDevice = logicalDisplay.mPrimaryDisplayDevice) != null && displayDevice.getDisplayDeviceInfoLocked().state != 1) {
                return false;
            }
        }
        return true;
    }

    public final void assignDisplayGroupLocked(LogicalDisplay logicalDisplay) {
        int intValue;
        DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        if (displayDevice != null) {
            Integer num = (Integer) this.mVirtualDeviceDisplayMapping.get(displayDevice.mUniqueId);
            int i = logicalDisplay.mDisplayId;
            int displayGroupIdFromDisplayIdLocked = getDisplayGroupIdFromDisplayIdLocked(i);
            Integer valueOf = (num == null || this.mDeviceDisplayGroupIds.indexOfKey(num.intValue()) <= 0) ? null : Integer.valueOf(this.mDeviceDisplayGroupIds.get(num.intValue()));
            DisplayGroup displayGroupLocked = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
            String str = logicalDisplay.mDisplayGroupName;
            DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
            boolean z = ((displayDeviceInfoLocked.flags & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) == 0 && TextUtils.isEmpty(str) && displayGroupIdFromDisplayIdLocked != 2) ? false : true;
            boolean z2 = displayGroupIdFromDisplayIdLocked != 0;
            boolean z3 = (z || num == null) ? false : true;
            boolean z4 = valueOf != null && displayGroupIdFromDisplayIdLocked == valueOf.intValue();
            if (displayGroupIdFromDisplayIdLocked == -1 || z2 != z || z4 != z3) {
                if (i == 2) {
                    displayGroupIdFromDisplayIdLocked = 2;
                } else if (!CoreRune.CARLIFE_DISPLAY_GROUP || (displayDeviceInfoLocked.flags & Integer.MIN_VALUE) == 0) {
                    String str2 = logicalDisplay.mDisplayGroupName;
                    if (z3 && num != null) {
                        intValue = this.mDeviceDisplayGroupIds.get(num.intValue());
                        if (intValue == 0) {
                            intValue = this.mNextNonDefaultGroupId;
                            this.mNextNonDefaultGroupId = intValue + 1;
                            this.mDeviceDisplayGroupIds.put(num.intValue(), intValue);
                        }
                    } else if (z) {
                        Integer num2 = (Integer) this.mDisplayGroupIdsByName.get(str2);
                        if (num2 == null) {
                            int i2 = this.mNextNonDefaultGroupId;
                            this.mNextNonDefaultGroupId = i2 + 1;
                            num2 = Integer.valueOf(i2);
                            this.mDisplayGroupIdsByName.put(str2, num2);
                        }
                        intValue = num2.intValue();
                    } else {
                        displayGroupIdFromDisplayIdLocked = 0;
                    }
                    displayGroupIdFromDisplayIdLocked = intValue;
                } else {
                    displayGroupIdFromDisplayIdLocked = 4;
                }
            }
            DisplayGroup displayGroupLocked2 = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
            if (displayGroupLocked2 == null) {
                displayGroupLocked2 = new DisplayGroup(displayGroupIdFromDisplayIdLocked);
                this.mDisplayGroups.append(displayGroupIdFromDisplayIdLocked, displayGroupLocked2);
            }
            if (displayGroupLocked != displayGroupLocked2) {
                if (displayGroupLocked != null) {
                    displayGroupLocked.mChangeCount++;
                    ((ArrayList) displayGroupLocked.mDisplays).remove(logicalDisplay);
                }
                if (!((ArrayList) displayGroupLocked2.mDisplays).contains(logicalDisplay)) {
                    displayGroupLocked2.mChangeCount++;
                    ((ArrayList) displayGroupLocked2.mDisplays).add(logicalDisplay);
                }
                if (displayGroupIdFromDisplayIdLocked != logicalDisplay.mDisplayGroupId) {
                    logicalDisplay.mDisplayGroupId = displayGroupIdFromDisplayIdLocked;
                    logicalDisplay.mDirty = true;
                }
                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(displayGroupIdFromDisplayIdLocked, i, "Setting new display group ", " for display ", ", from previous group: ");
                m.append(displayGroupLocked != null ? Integer.valueOf(displayGroupLocked.mGroupId) : "null");
                Slog.i("LogicalDisplayMapper", m.toString());
            }
        }
    }

    public final LogicalDisplay createNewLogicalDisplayLocked(DisplayDevice displayDevice, int i) {
        DisplayManagerFlags displayManagerFlags = this.mFlags;
        LogicalDisplay logicalDisplay = new LogicalDisplay(i, i, displayDevice, displayManagerFlags.mPixelAnisotropyCorrectionEnabled.isEnabled(), displayManagerFlags.mAlwaysRotateDisplayDevice.isEnabled());
        logicalDisplay.updateLocked(this.mDisplayDeviceRepo, this.mSyntheticModeManager);
        if (logicalDisplay.getDisplayInfoLocked().type == 1 && this.mDeviceStateToLayoutMap.mLayoutMap.size() > 1 && logicalDisplay.mIsEnabled) {
            logicalDisplay.mDirty = true;
            logicalDisplay.mIsEnabled = false;
        }
        this.mLogicalDisplays.put(i, logicalDisplay);
        return logicalDisplay;
    }

    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("LogicalDisplayMapper:");
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        StringBuilder m = DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mSingleDisplayDemoMode="), this.mSingleDisplayDemoMode, (IndentingPrintWriter) indentingPrintWriter, "mCurrentLayout=");
        m.append(this.mCurrentLayout);
        indentingPrintWriter.println(m.toString());
        indentingPrintWriter.println("mDeviceStatesOnWhichToWakeUp=" + this.mDeviceStatesOnWhichToWakeUp);
        indentingPrintWriter.println("mDeviceStatesOnWhichSelectiveSleep=" + this.mDeviceStatesOnWhichToSelectiveSleep);
        StringBuilder m2 = DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mInteractive="), this.mInteractive, (IndentingPrintWriter) indentingPrintWriter, "mBootCompleted=");
        m2.append(this.mBootCompleted);
        indentingPrintWriter.println(m2.toString());
        indentingPrintWriter.println();
        indentingPrintWriter.println("mDeviceState=" + this.mDeviceState);
        indentingPrintWriter.println("mPendingDeviceState=" + this.mPendingDeviceState);
        indentingPrintWriter.println("mDeviceStateToBeAppliedAfterBoot=" + this.mDeviceStateToBeAppliedAfterBoot);
        int size = this.mLogicalDisplays.size();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Logical Displays: size=" + size);
        for (int i = 0; i < size; i++) {
            int keyAt = this.mLogicalDisplays.keyAt(i);
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            indentingPrintWriter.println("Display " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            logicalDisplay.dumpLocked(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
        DeviceStateToLayoutMap deviceStateToLayoutMap = this.mDeviceStateToLayoutMap;
        deviceStateToLayoutMap.getClass();
        indentingPrintWriter.println("DeviceStateToLayoutMap:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mIsPortInDisplayLayoutEnabled=" + deviceStateToLayoutMap.mIsPortInDisplayLayoutEnabled);
        indentingPrintWriter.println("Registered Layouts:");
        for (int i2 = 0; i2 < deviceStateToLayoutMap.mLayoutMap.size(); i2++) {
            indentingPrintWriter.println("state(" + deviceStateToLayoutMap.mLayoutMap.keyAt(i2) + "): " + deviceStateToLayoutMap.mLayoutMap.valueAt(i2));
        }
    }

    public final void finishStateTransitionLocked(boolean z) {
        boolean z2;
        boolean z3;
        int i = this.mPendingDeviceState;
        if (i == -1) {
            return;
        }
        boolean z4 = this.mDeviceStatesOnWhichToWakeUp.get(i) && !this.mDeviceStatesOnWhichToWakeUp.get(this.mDeviceState) && !this.mInteractive && this.mBootCompleted;
        if (this.mDeviceStatesOnWhichToSelectiveSleep.get(this.mPendingDeviceState) && !this.mDeviceStatesOnWhichToSelectiveSleep.get(this.mDeviceState) && this.mInteractive && this.mBootCompleted) {
            FoldSettingProvider foldSettingProvider = this.mFoldSettingProvider;
            if (!foldSettingProvider.getFoldSettingValue().equals("stay_awake_on_fold_key") && (!foldSettingProvider.getFoldSettingValue().equals("selective_stay_awake_key") || !this.mFoldGracePeriodProvider.isEnabled())) {
                z2 = true;
                boolean areAllTransitioningDisplaysOffLocked = areAllTransitioningDisplaysOffLocked();
                z3 = (areAllTransitioningDisplaysOffLocked || z4 || z2) ? false : true;
                if (!z3 || z) {
                    resetLayoutLocked(this.mDeviceState, this.mPendingDeviceState, false);
                    this.mDeviceState = this.mPendingDeviceState;
                    this.mPendingDeviceState = -1;
                    applyLayoutLocked();
                    updateLogicalDisplaysLocked$1();
                    this.mHandler.removeMessages(1);
                }
                if (DEBUG) {
                    Slog.d("LogicalDisplayMapper", "Not yet ready to transition to state=" + this.mPendingDeviceState + " with displays-off=" + areAllTransitioningDisplaysOffLocked + ", force=" + z + ", mInteractive=" + this.mInteractive + ", isReady=" + z3);
                    return;
                }
                return;
            }
        }
        z2 = false;
        boolean areAllTransitioningDisplaysOffLocked2 = areAllTransitioningDisplaysOffLocked();
        if (areAllTransitioningDisplaysOffLocked2) {
        }
        if (z3) {
        }
        resetLayoutLocked(this.mDeviceState, this.mPendingDeviceState, false);
        this.mDeviceState = this.mPendingDeviceState;
        this.mPendingDeviceState = -1;
        applyLayoutLocked();
        updateLogicalDisplaysLocked$1();
        this.mHandler.removeMessages(1);
    }

    public final void forEachLocked(Consumer consumer, boolean z) {
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            if (logicalDisplay.mIsEnabled || z || logicalDisplay.mDisplayId == 0) {
                consumer.accept(logicalDisplay);
            }
        }
    }

    public int getDisplayGroupIdFromDisplayIdLocked(int i) {
        LogicalDisplay displayLocked = getDisplayLocked(i, true);
        if (displayLocked == null) {
            return -1;
        }
        int size = this.mDisplayGroups.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((ArrayList) ((DisplayGroup) this.mDisplayGroups.valueAt(i2)).mDisplays).contains(displayLocked)) {
                return this.mDisplayGroups.keyAt(i2);
            }
        }
        return -1;
    }

    public final DisplayGroup getDisplayGroupLocked(int i) {
        return (DisplayGroup) this.mDisplayGroups.get(i);
    }

    public final DisplayInfo getDisplayInfoForStateLocked(int i, int i2) {
        Layout layout = this.mDeviceStateToLayoutMap.get(i);
        if (layout == null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Cannot get layout for given state:", "LogicalDisplayMapper");
            return null;
        }
        Layout.Display byId = layout.getById(i2);
        if (byId == null) {
            Slog.d("LogicalDisplayMapper", "Cannot get display for given layout:" + layout);
            return null;
        }
        DisplayDevice byAddressLocked = this.mDisplayDeviceRepo.getByAddressLocked(byId.mAddress);
        if (byAddressLocked == null) {
            StringBuilder sb = new StringBuilder("The display device (");
            sb.append(byId.mAddress);
            sb.append("), is not available for the display state ");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, this.mDeviceState, "LogicalDisplayMapper");
            return null;
        }
        LogicalDisplay displayLocked = getDisplayLocked(byAddressLocked);
        if (displayLocked != null) {
            DisplayInfo displayInfo = new DisplayInfo(displayLocked.getDisplayInfoLocked());
            displayInfo.displayId = i2;
            return displayInfo;
        }
        StringBuilder sb2 = new StringBuilder("The logical display associated with address (");
        sb2.append(byId.mAddress);
        sb2.append("), is not available for the display state ");
        HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb2, this.mDeviceState, "LogicalDisplayMapper");
        return null;
    }

    public final LogicalDisplay getDisplayLocked(int i, boolean z) {
        LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.get(i);
        if (logicalDisplay == null || logicalDisplay.mIsEnabled || z || i == 0) {
            return logicalDisplay;
        }
        return null;
    }

    public final LogicalDisplay getDisplayLocked(DisplayDevice displayDevice) {
        if (displayDevice == null) {
            return null;
        }
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            if (logicalDisplay.mPrimaryDisplayDevice == displayDevice) {
                return logicalDisplay;
            }
        }
        return null;
    }

    public final void resetLayoutLocked(int i, int i2, boolean z) {
        DeviceStateToLayoutMap deviceStateToLayoutMap = this.mDeviceStateToLayoutMap;
        Layout layout = deviceStateToLayoutMap.get(i);
        Layout layout2 = deviceStateToLayoutMap.get(i2);
        int size = this.mLogicalDisplays.size();
        for (int i3 = 0; i3 < size; i3++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i3);
            int i4 = logicalDisplay.mDisplayId;
            DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
            if (displayDevice != null) {
                DisplayAddress displayAddress = displayDevice.getDisplayDeviceInfoLocked().address;
                Layout.Display byAddress = displayAddress != null ? layout.getByAddress(displayAddress) : null;
                Layout.Display byAddress2 = displayAddress != null ? layout2.getByAddress(displayAddress) : null;
                boolean z2 = (byAddress == null) != (byAddress2 == null);
                boolean z3 = byAddress == null || byAddress.mIsEnabled;
                boolean z4 = byAddress2 == null || byAddress2.mIsEnabled;
                boolean z5 = (byAddress == null || byAddress2 == null || byAddress.mLogicalDisplayId == byAddress2.mLogicalDisplayId) ? false : true;
                boolean z6 = logicalDisplay.mIsInTransition;
                if (z6 || z3 != z4 || z5 || z2) {
                    if (z != z6) {
                        Slog.i("LogicalDisplayMapper", "Set isInTransition on display " + i4 + ": " + z);
                    }
                    logicalDisplay.mIsInTransition = z;
                    this.mUpdatedLogicalDisplays.put(i4, 1);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x03cd  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x03d7  */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v27 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendUpdatesForDisplaysLocked(int r17) {
        /*
            Method dump skipped, instructions count: 1054
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.LogicalDisplayMapper.sendUpdatesForDisplaysLocked(int):void");
    }

    public final void sendUpdatesForGroupsLocked(int i) {
        for (int size = this.mDisplayGroupsToUpdate.size() - 1; size >= 0; size--) {
            if (this.mDisplayGroupsToUpdate.valueAt(size) == i) {
                int keyAt = this.mDisplayGroupsToUpdate.keyAt(size);
                DisplayManagerService.DisplayManagerHandler displayManagerHandler = DisplayManagerService.this.mHandler;
                displayManagerHandler.sendMessage(displayManagerHandler.obtainMessage(8, keyAt, i));
                if (i == 3) {
                    this.mDisplayGroups.delete(keyAt);
                    int indexOfValue = this.mDeviceDisplayGroupIds.indexOfValue(keyAt);
                    if (indexOfValue >= 0) {
                        this.mDeviceDisplayGroupIds.removeAt(indexOfValue);
                    }
                }
            }
        }
    }

    public final void setDeviceStateLocked(int i) {
        boolean z = this.mBootCompleted;
        boolean z2 = DEBUG;
        if (!z) {
            if (z2) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Postponing transition to state: "), this.mPendingDeviceState, " until boot is completed", "LogicalDisplayMapper");
            }
            this.mDeviceStateToBeAppliedAfterBoot = i;
            return;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Requesting Transition to state: ", ", from state=");
        m.append(this.mDeviceState);
        m.append(", interactive=");
        m.append(this.mInteractive);
        m.append(", mBootCompleted=");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("LogicalDisplayMapper", m, this.mBootCompleted);
        resetLayoutLocked(this.mDeviceState, i, true);
        this.mPendingDeviceState = i;
        this.mDeviceStateToBeAppliedAfterBoot = -1;
        boolean shouldDeviceBeWoken = shouldDeviceBeWoken(i, this.mDeviceState, this.mInteractive, this.mBootCompleted);
        boolean shouldDeviceBePutToSleep = shouldDeviceBePutToSleep(this.mPendingDeviceState, this.mDeviceState, this.mInteractive, this.mBootCompleted);
        if (areAllTransitioningDisplaysOffLocked() && !shouldDeviceBeWoken && !shouldDeviceBePutToSleep) {
            resetLayoutLocked(this.mDeviceState, this.mPendingDeviceState, false);
            this.mDeviceState = this.mPendingDeviceState;
            this.mPendingDeviceState = -1;
            applyLayoutLocked();
            updateLogicalDisplaysLocked$1();
            return;
        }
        if (z2) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Postponing transition to state: "), this.mPendingDeviceState, "LogicalDisplayMapper");
        }
        updateLogicalDisplaysLocked$1();
        LogicalDisplayMapperHandler logicalDisplayMapperHandler = this.mHandler;
        if (shouldDeviceBeWoken || shouldDeviceBePutToSleep) {
            if (shouldDeviceBeWoken) {
                logicalDisplayMapperHandler.post(new Runnable() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicalDisplayMapper.this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 12, "server.display:unfold");
                    }
                });
            } else if (shouldDeviceBePutToSleep) {
                final int i2 = this.mFoldSettingProvider.getFoldSettingValue().equals("sleep_on_fold_key") ? 0 : 2;
                logicalDisplayMapperHandler.post(new Runnable() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicalDisplayMapper logicalDisplayMapper = LogicalDisplayMapper.this;
                        logicalDisplayMapper.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 13, i2);
                    }
                });
            }
        }
        logicalDisplayMapperHandler.sendEmptyMessageDelayed(1, 3000L);
    }

    public final void setDisplayEnabledLocked(LogicalDisplay logicalDisplay, boolean z) {
        boolean z2 = logicalDisplay.mIsEnabled;
        if (z2 != z) {
            setEnabledLocked(logicalDisplay, z);
            updateLogicalDisplaysLocked$1();
        } else {
            StringBuilder sb = new StringBuilder("Display is already ");
            sb.append(z2 ? "enabled" : "disabled");
            sb.append(": ");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, logicalDisplay.mDisplayId, "LogicalDisplayMapper");
        }
    }

    public final void setEnabledLocked(LogicalDisplay logicalDisplay, boolean z) {
        int i = logicalDisplay.mDisplayId;
        boolean z2 = this.mSingleDisplayDemoMode && logicalDisplay.getDisplayInfoLocked().type != 1;
        if (z && z2) {
            Slog.i("LogicalDisplayMapper", "Not creating a logical display for a secondary display because single display demo mode is enabled: " + logicalDisplay.getDisplayInfoLocked());
            z = false;
        }
        if (logicalDisplay.mIsEnabled != z) {
            Slog.i("LogicalDisplayMapper", "SetEnabled on display " + i + ": " + z);
            if (z != logicalDisplay.mIsEnabled) {
                logicalDisplay.mDirty = true;
                logicalDisplay.mIsEnabled = z;
            }
        }
    }

    public boolean shouldDeviceBePutToSleep(int i, int i2, boolean z, boolean z2) {
        return i2 != -1 && this.mDeviceStatesOnWhichToSelectiveSleep.get(i) && !this.mDeviceStatesOnWhichToSelectiveSleep.get(i2) && z && z2 && !this.mFoldSettingProvider.getFoldSettingValue().equals("stay_awake_on_fold_key");
    }

    public boolean shouldDeviceBeWoken(int i, int i2, boolean z, boolean z2) {
        return this.mDeviceStatesOnWhichToWakeUp.get(i) && !this.mDeviceStatesOnWhichToWakeUp.get(i2) && !z && z2;
    }

    public void updateLogicalDisplays() {
        synchronized (this.mSyncRoot) {
            updateLogicalDisplaysLocked$1();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x014d, code lost:
    
        if (com.android.server.display.DisplayDeviceRepository.isExternalDisplayDeviceForDexLocked(r8) != false) goto L68;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0112 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLogicalDisplaysLocked(int r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 752
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.LogicalDisplayMapper.updateLogicalDisplaysLocked(int, boolean):void");
    }

    public final void updateLogicalDisplaysLocked$1() {
        updateLogicalDisplaysLocked(-1, false);
    }
}
