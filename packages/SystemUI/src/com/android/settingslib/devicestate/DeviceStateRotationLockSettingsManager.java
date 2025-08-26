package com.android.settingslib.devicestate;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes.dex */
public final class DeviceStateRotationLockSettingsManager implements DeviceStateAutoRotateSettingManager {
    public final Set mListeners;
    public SparseIntArray mPostureDefaultRotationLockSettings;
    public String[] mPostureRotationLockDefaults;
    public SparseIntArray mPostureRotationLockFallbackSettings;
    public SparseIntArray mPostureRotationLockSettings;
    public final PosturesHelper mPosturesHelper;
    public final SecureSettings mSecureSettings;
    public List mSettableDeviceStates;

    public DeviceStateRotationLockSettingsManager(Context context, SecureSettings secureSettings) throws NumberFormatException {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mListeners = new HashSet();
        this.mSecureSettings = secureSettings;
        this.mPosturesHelper = new PosturesHelper(context, (DeviceStateManager) context.getSystemService(DeviceStateManager.class));
        this.mPostureRotationLockDefaults = context.getResources().getStringArray(17236293);
        loadDefaults();
        initializeInMemoryMap();
        ((AndroidSecureSettings) secureSettings).mContentResolver.registerContentObserver(Settings.Secure.getUriFor("device_state_rotation_lock"), false, new ContentObserver(handler) { // from class: com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) throws NumberFormatException {
                DeviceStateRotationLockSettingsManager.this.onPersistedSettingsChanged();
            }
        }, -2);
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("DeviceStateRotationLockSettingsManager");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mPostureRotationLockDefaults: " + Arrays.toString(this.mPostureRotationLockDefaults));
        indentingPrintWriter.println("mPostureDefaultRotationLockSettings: " + this.mPostureDefaultRotationLockSettings);
        indentingPrintWriter.println("mDeviceStateRotationLockSettings: " + this.mPostureRotationLockSettings);
        indentingPrintWriter.println("mPostureRotationLockFallbackSettings: " + this.mPostureRotationLockFallbackSettings);
        indentingPrintWriter.println("mSettableDeviceStates: " + this.mSettableDeviceStates);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.settingslib.devicestate.DeviceStateAutoRotateSettingManager
    public final int getRotationLockSetting(int i) {
        int iDeviceStateToPosture = this.mPosturesHelper.deviceStateToPosture(i);
        int i2 = this.mPostureRotationLockSettings.get(iDeviceStateToPosture, 0);
        if (i2 != 0) {
            return i2;
        }
        int iIndexOfKey = this.mPostureRotationLockFallbackSettings.indexOfKey(iDeviceStateToPosture);
        if (iIndexOfKey < 0) {
            Log.w("DSRotLockSettingsMngr", "Setting is ignored, but no fallback was specified.");
            return 0;
        }
        return this.mPostureRotationLockSettings.get(this.mPostureRotationLockFallbackSettings.valueAt(iIndexOfKey), 0);
    }

    public final void initializeInMemoryMap() throws NumberFormatException {
        String stringForUser = Settings.Secure.getStringForUser(((AndroidSecureSettings) this.mSecureSettings).mContentResolver, "device_state_rotation_lock", -2);
        if (TextUtils.isEmpty(stringForUser)) {
            loadDefaults();
            persistSettings();
            return;
        }
        String[] strArrSplit = stringForUser.split(":");
        if (strArrSplit.length % 2 != 0) {
            Log.wtf("DSRotLockSettingsMngr", "Can't deserialize saved settings, falling back on defaults");
            loadDefaults();
            persistSettings();
            return;
        }
        this.mPostureRotationLockSettings = new SparseIntArray(strArrSplit.length / 2);
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= strArrSplit.length - 1) {
                return;
            }
            int i2 = i + 1;
            try {
                int i3 = Integer.parseInt(strArrSplit[i]);
                i += 2;
                int i4 = Integer.parseInt(strArrSplit[i2]);
                boolean z2 = i4 == 0;
                if (this.mPostureDefaultRotationLockSettings.get(i3) != 0) {
                    z = false;
                }
                if (z2 != z) {
                    Log.w("DSRotLockSettingsMngr", "Conflict for ignored device state " + i3 + ". Falling back on defaults");
                    loadDefaults();
                    persistSettings();
                    return;
                }
                this.mPostureRotationLockSettings.put(i3, i4);
            } catch (NumberFormatException e) {
                Log.wtf("DSRotLockSettingsMngr", "Error deserializing one of the saved settings", e);
                loadDefaults();
                persistSettings();
                return;
            }
        }
    }

    @Override // com.android.settingslib.devicestate.DeviceStateAutoRotateSettingManager
    public final boolean isRotationLocked(int i) {
        return getRotationLockSetting(i) == 1;
    }

    public final void loadDefaults() throws NumberFormatException {
        this.mSettableDeviceStates = new ArrayList(this.mPostureRotationLockDefaults.length);
        this.mPostureDefaultRotationLockSettings = new SparseIntArray(this.mPostureRotationLockDefaults.length);
        this.mPostureRotationLockSettings = new SparseIntArray(this.mPostureRotationLockDefaults.length);
        this.mPostureRotationLockFallbackSettings = new SparseIntArray(1);
        for (String str : this.mPostureRotationLockDefaults) {
            String[] strArrSplit = str.split(":");
            try {
                int i = Integer.parseInt(strArrSplit[0]);
                int i2 = Integer.parseInt(strArrSplit[1]);
                if (i2 == 0) {
                    if (strArrSplit.length == 3) {
                        this.mPostureRotationLockFallbackSettings.put(i, Integer.parseInt(strArrSplit[2]));
                    } else {
                        Log.w("DSRotLockSettingsMngr", "Rotation lock setting is IGNORED, but values have unexpected size of " + strArrSplit.length);
                    }
                }
                boolean z = i2 != 0;
                List list = (List) this.mPosturesHelper.postures.get(Integer.valueOf(i));
                Integer num = list != null ? (Integer) CollectionsKt___CollectionsKt.firstOrNull(list) : null;
                if (num != null) {
                    this.mSettableDeviceStates.add(new SettableDeviceState(num.intValue(), z));
                } else {
                    Log.wtf("DSRotLockSettingsMngr", "No matching device state for posture: " + i);
                }
                this.mPostureRotationLockSettings.put(i, i2);
                this.mPostureDefaultRotationLockSettings.put(i, i2);
            } catch (NumberFormatException e) {
                Log.wtf("DSRotLockSettingsMngr", "Error parsing settings entry. Entry was: ".concat(str), e);
                return;
            }
        }
    }

    public void onPersistedSettingsChanged() throws NumberFormatException {
        initializeInMemoryMap();
        Iterator it = ((HashSet) this.mListeners).iterator();
        while (it.hasNext()) {
            DeviceStateRotationLockSettingController deviceStateRotationLockSettingController = ((DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1) it.next()).f$0;
            deviceStateRotationLockSettingController.readPersistedSetting(deviceStateRotationLockSettingController.mDeviceState, "deviceStateRotationLockChange");
        }
    }

    public final void persistSettings() {
        if (this.mPostureRotationLockSettings.size() == 0) {
            if (TextUtils.equals(Settings.Secure.getStringForUser(((AndroidSecureSettings) this.mSecureSettings).mContentResolver, "device_state_rotation_lock", -2), "")) {
                return;
            }
            Settings.Secure.putStringForUser(((AndroidSecureSettings) this.mSecureSettings).mContentResolver, "device_state_rotation_lock", "", -2);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mPostureRotationLockSettings.keyAt(0));
        sb.append(":");
        sb.append(this.mPostureRotationLockSettings.valueAt(0));
        for (int i = 1; i < this.mPostureRotationLockSettings.size(); i++) {
            sb.append(":");
            sb.append(this.mPostureRotationLockSettings.keyAt(i));
            sb.append(":");
            sb.append(this.mPostureRotationLockSettings.valueAt(i));
        }
        String string = sb.toString();
        if (TextUtils.equals(Settings.Secure.getStringForUser(((AndroidSecureSettings) this.mSecureSettings).mContentResolver, "device_state_rotation_lock", -2), string)) {
            return;
        }
        Settings.Secure.putStringForUser(((AndroidSecureSettings) this.mSecureSettings).mContentResolver, "device_state_rotation_lock", string, -2);
    }

    @Override // com.android.settingslib.devicestate.DeviceStateAutoRotateSettingManager
    public final void registerListener(DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 deviceStateRotationLockSettingController$$ExternalSyntheticLambda1) {
        ((HashSet) this.mListeners).add(deviceStateRotationLockSettingController$$ExternalSyntheticLambda1);
    }

    public void resetStateForTesting(Resources resources) throws NumberFormatException {
        this.mPostureRotationLockDefaults = resources.getStringArray(17236293);
        loadDefaults();
        persistSettings();
    }

    @Override // com.android.settingslib.devicestate.DeviceStateAutoRotateSettingManager
    public final void updateSetting(int i, boolean z) {
        int iDeviceStateToPosture = this.mPosturesHelper.deviceStateToPosture(i);
        if (this.mPostureRotationLockFallbackSettings.indexOfKey(iDeviceStateToPosture) >= 0) {
            iDeviceStateToPosture = this.mPostureRotationLockFallbackSettings.get(iDeviceStateToPosture);
        }
        this.mPostureRotationLockSettings.put(iDeviceStateToPosture, z ? 1 : 2);
        persistSettings();
    }
}
