package com.android.server.am;

import android.R;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextFlags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public final class CoreSettingsObserver extends ContentObserver {
    public static volatile boolean sDeviceConfigContextEntriesLoaded;
    public static final List sDeviceConfigEntries;
    static final Map sGlobalSettingToTypeMap;
    static final Map sSecureSettingToTypeMap;
    static final Map sSystemSettingToTypeMap;
    public final ActivityManagerService mActivityManagerService;
    public final Bundle mCoreSettings;

    public final class DeviceConfigEntry {
        public final String coreSettingKey;
        public final Object defaultValue;
        public final String flag;
        public final String namespace;
        public final Class type;

        public DeviceConfigEntry(String str, String str2, String str3, Class cls, Object obj) {
            this.namespace = str;
            this.flag = str2;
            this.coreSettingKey = str3;
            this.type = cls;
            this.defaultValue = obj;
        }
    }

    static {
        HashMap hashMap = new HashMap();
        sSecureSettingToTypeMap = hashMap;
        HashMap hashMap2 = new HashMap();
        sSystemSettingToTypeMap = hashMap2;
        HashMap hashMap3 = new HashMap();
        sGlobalSettingToTypeMap = hashMap3;
        ArrayList arrayList = new ArrayList();
        sDeviceConfigEntries = arrayList;
        Class cls = Integer.TYPE;
        hashMap.put("long_press_timeout", cls);
        hashMap.put("multi_press_timeout", cls);
        hashMap.put("key_repeat_timeout", cls);
        hashMap.put("key_repeat_delay", cls);
        hashMap.put("stylus_pointer_icon_enabled", cls);
        hashMap2.put("time_12_24", String.class);
        hashMap3.put("debug_view_attributes", cls);
        hashMap3.put("debug_view_attributes_application_package", String.class);
        hashMap3.put("angle_debug_package", String.class);
        hashMap3.put("angle_gl_driver_all_angle", cls);
        hashMap3.put("angle_gl_driver_selection_pkgs", String.class);
        hashMap3.put("angle_gl_driver_selection_values", String.class);
        hashMap3.put("angle_egl_features", String.class);
        hashMap3.put("show_angle_in_use_dialog_box", String.class);
        hashMap3.put("enable_gpu_debug_layers", cls);
        hashMap3.put("gpu_debug_app", String.class);
        hashMap3.put("gpu_debug_layers", String.class);
        hashMap3.put("gpu_debug_layers_gles", String.class);
        hashMap3.put("gpu_debug_layer_app", String.class);
        hashMap3.put("gpu_control_layer_apps", String.class);
        hashMap3.put("updatable_driver_all_apps", cls);
        hashMap3.put("updatable_driver_production_opt_in_apps", String.class);
        hashMap3.put("updatable_driver_prerelease_opt_in_apps", String.class);
        hashMap3.put("updatable_driver_production_opt_out_apps", String.class);
        hashMap3.put("updatable_driver_production_denylist", String.class);
        hashMap3.put("updatable_driver_production_allowlist", String.class);
        hashMap3.put("updatable_driver_production_denylists", String.class);
        hashMap3.put("updatable_driver_sphal_libraries", String.class);
        hashMap3.put("navigationbar_current_color", cls);
        Class cls2 = Boolean.TYPE;
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__enable_cursor_drag_from_anywhere",
                        "widget__enable_cursor_drag_from_anywhere",
                        cls2,
                        Boolean.TRUE));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__min_angle_from_vertical_to_start_cursor_drag",
                        "widget__min_angle_from_vertical_to_start_cursor_drag",
                        cls,
                        45));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__finger_to_cursor_distance",
                        "widget__finger_to_cursor_distance",
                        cls,
                        -1));
        Boolean bool = Boolean.FALSE;
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__enable_insertion_handle_gestures",
                        "widget__enable_insertion_handle_gestures",
                        cls2,
                        bool));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__insertion_handle_delta_height",
                        "widget__insertion_handle_delta_height",
                        cls,
                        25));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__insertion_handle_opacity",
                        "widget__insertion_handle_opacity",
                        cls,
                        50));
        Class cls3 = Float.TYPE;
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__line_slop_ratio",
                        "widget__line_slop_ratio",
                        cls3,
                        Float.valueOf(0.5f)));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__enable_new_magnifier",
                        "widget__enable_new_magnifier",
                        cls2,
                        bool));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__magnifier_zoom_factor",
                        "widget__magnifier_zoom_factor",
                        cls3,
                        Float.valueOf(1.5f)));
        arrayList.add(
                new DeviceConfigEntry(
                        "widget",
                        "CursorControlFeature__magnifier_aspect_ratio",
                        "widget__magnifier_aspect_ratio",
                        cls3,
                        Float.valueOf(5.5f)));
        arrayList.add(
                new DeviceConfigEntry(
                        "text",
                        "TextEditing__enable_new_context_menu",
                        "text__enable_new_context_menu",
                        cls2,
                        bool));
        int i = 0;
        while (true) {
            String[] strArr = TextFlags.TEXT_ACONFIGS_FLAGS;
            if (i >= strArr.length) {
                sDeviceConfigContextEntriesLoaded = false;
                return;
            }
            String str = strArr[i];
            sDeviceConfigEntries.add(
                    new DeviceConfigEntry(
                            "text",
                            str,
                            TextFlags.getKeyForFlag(str),
                            Boolean.TYPE,
                            Boolean.valueOf(TextFlags.TEXT_ACONFIG_DEFAULT_VALUE[i])));
            i++;
        }
    }

    public CoreSettingsObserver(ActivityManagerService activityManagerService) {
        super(activityManagerService.mHandler);
        this.mCoreSettings = new Bundle();
        if (!sDeviceConfigContextEntriesLoaded) {
            List list = sDeviceConfigEntries;
            synchronized (list) {
                try {
                    if (!sDeviceConfigContextEntriesLoaded) {
                        ((ArrayList) list)
                                .add(
                                        new DeviceConfigEntry(
                                                "widget",
                                                "AnalogClockFeature__analog_clock_seconds_hand_fps",
                                                "widget__analog_clock_seconds_hand_fps",
                                                Integer.TYPE,
                                                Integer.valueOf(
                                                        activityManagerService
                                                                .mContext
                                                                .getResources()
                                                                .getInteger(
                                                                        R.integer
                                                                                .config_displayWhiteBalanceBrightnessFilterHorizon))));
                        sDeviceConfigContextEntriesLoaded = true;
                    }
                } finally {
                }
            }
        }
        this.mActivityManagerService = activityManagerService;
        Iterator it = sSecureSettingToTypeMap.keySet().iterator();
        while (it.hasNext()) {
            this.mActivityManagerService
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor((String) it.next()), false, this);
        }
        Iterator it2 = sSystemSettingToTypeMap.keySet().iterator();
        while (it2.hasNext()) {
            this.mActivityManagerService
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor((String) it2.next()), false, this);
        }
        Iterator it3 = sGlobalSettingToTypeMap.keySet().iterator();
        while (it3.hasNext()) {
            this.mActivityManagerService
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor((String) it3.next()), false, this);
        }
        HashSet hashSet = new HashSet();
        Iterator it4 = ((ArrayList) sDeviceConfigEntries).iterator();
        while (it4.hasNext()) {
            DeviceConfigEntry deviceConfigEntry = (DeviceConfigEntry) it4.next();
            if (!hashSet.contains(deviceConfigEntry.namespace)) {
                Executor mainExecutor = ActivityThread.currentApplication().getMainExecutor();
                DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener =
                        new DeviceConfig
                                .OnPropertiesChangedListener() { // from class:
                                                                 // com.android.server.am.CoreSettingsObserver$$ExternalSyntheticLambda0
                            public final void onPropertiesChanged(
                                    DeviceConfig.Properties properties) {
                                CoreSettingsObserver.this.onChange(false);
                            }
                        };
                String str = deviceConfigEntry.namespace;
                DeviceConfig.addOnPropertiesChangedListener(
                        str, mainExecutor, onPropertiesChangedListener);
                hashSet.add(str);
            }
        }
        sendCoreSettings();
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        ActivityManagerService activityManagerService = this.mActivityManagerService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                sendCoreSettings();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void populateSettings(Bundle bundle, Map map) {
        ContentResolver contentResolver =
                this.mActivityManagerService.mContext.getContentResolver();
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String stringForUser =
                    map == sSecureSettingToTypeMap
                            ? Settings.Secure.getStringForUser(
                                    contentResolver, str, contentResolver.getUserId())
                            : map == sSystemSettingToTypeMap
                                    ? Settings.System.getStringForUser(
                                            contentResolver, str, contentResolver.getUserId())
                                    : Settings.Global.getString(contentResolver, str);
            if (stringForUser == null) {
                bundle.remove(str);
            } else {
                Class cls = (Class) entry.getValue();
                if (cls == String.class) {
                    bundle.putString(str, stringForUser);
                } else if (cls == Integer.TYPE) {
                    bundle.putInt(str, Integer.parseInt(stringForUser));
                } else if (cls == Float.TYPE) {
                    bundle.putFloat(str, Float.parseFloat(stringForUser));
                } else if (cls == Long.TYPE) {
                    bundle.putLong(str, Long.parseLong(stringForUser));
                }
            }
        }
    }

    public final void sendCoreSettings() {
        populateSettings(this.mCoreSettings, sSecureSettingToTypeMap);
        populateSettings(this.mCoreSettings, sSystemSettingToTypeMap);
        populateSettings(this.mCoreSettings, sGlobalSettingToTypeMap);
        Iterator it = ((ArrayList) sDeviceConfigEntries).iterator();
        while (it.hasNext()) {
            DeviceConfigEntry deviceConfigEntry = (DeviceConfigEntry) it.next();
            Class cls = deviceConfigEntry.type;
            String str = deviceConfigEntry.flag;
            String str2 = deviceConfigEntry.namespace;
            String str3 = deviceConfigEntry.coreSettingKey;
            Object obj = deviceConfigEntry.defaultValue;
            if (cls == String.class) {
                this.mCoreSettings.putString(str3, DeviceConfig.getString(str2, str, (String) obj));
            } else if (cls == Integer.TYPE) {
                this.mCoreSettings.putInt(
                        str3, DeviceConfig.getInt(str2, str, ((Integer) obj).intValue()));
            } else if (cls == Float.TYPE) {
                this.mCoreSettings.putFloat(
                        str3, DeviceConfig.getFloat(str2, str, ((Float) obj).floatValue()));
            } else if (cls == Long.TYPE) {
                this.mCoreSettings.putLong(
                        str3, DeviceConfig.getLong(str2, str, ((Long) obj).longValue()));
            } else if (cls == Boolean.TYPE) {
                this.mCoreSettings.putInt(
                        str3,
                        DeviceConfig.getBoolean(str2, str, ((Boolean) obj).booleanValue()) ? 1 : 0);
            }
        }
        ActivityManagerService activityManagerService = this.mActivityManagerService;
        Bundle bundle = this.mCoreSettings;
        ActivityManagerProcLock activityManagerProcLock = activityManagerService.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerProcLock) {
            try {
                ProcessList processList = activityManagerService.mProcessList;
                for (int size = processList.mLruProcesses.size() - 1; size >= 0; size--) {
                    IApplicationThread iApplicationThread =
                            ((ProcessRecord) processList.mLruProcesses.get(size)).mThread;
                    if (iApplicationThread != null) {
                        try {
                            iApplicationThread.setCoreSettings(bundle);
                        } catch (RemoteException unused) {
                        }
                    }
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }
}
