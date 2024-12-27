package com.android.server.am;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SettingsToPropertiesMapper {
    public final ContentResolver mContentResolver;
    public final String[] mDeviceConfigAconfigScopes;
    public final String[] mDeviceConfigScopes;
    public final String[] mGlobalSettings;
    static final String[] sGlobalSettings = {"native_flags_health_check_enabled"};
    static final String[] sDeviceConfigScopes = {
        "activity_manager_native_boot",
        "camera_native",
        "configuration",
        "connectivity",
        "edgetpu_native",
        "input_native_boot",
        "intelligence_content_suggestions",
        "lmkd_native",
        "media_native",
        "mglru_native",
        "netd_native",
        "nnapi_native",
        "profcollect_native_boot",
        "remote_key_provisioning_native",
        "runtime_native",
        "runtime_native_boot",
        "statsd_native",
        "statsd_native_boot",
        "storage_native_boot",
        "surface_flinger_native_boot",
        "swcodec_native",
        "vendor_system_native",
        "vendor_system_native_boot",
        "virtualization_framework_native",
        "window_manager_native_boot",
        "memory_safety_native_boot",
        "memory_safety_native",
        "hdmi_control",
        "tethering_u_or_later_native"
    };
    static final String[] sDeviceConfigAconfigScopes = {
        "accessibility",
        "android_core_networking",
        "android_stylus",
        "aoc",
        "app_widgets",
        "arc_next",
        "art_mainline",
        "art_performance",
        "attack_tools",
        "avic",
        "biometrics",
        "biometrics_framework",
        "biometrics_integration",
        "bluetooth",
        "brownout_mitigation_audio",
        "brownout_mitigation_modem",
        "build",
        "camera_hal",
        "camera_platform",
        "car_framework",
        "car_perception",
        "car_security",
        "car_telemetry",
        "codec_fwk",
        "companion",
        "com_android_adbd",
        "content_protection",
        "context_hub",
        "core_experiments_team_internal",
        "core_graphics",
        "core_libraries",
        "crumpet",
        "dck_framework",
        "devoptions_settings",
        "game",
        "gpu",
        "haptics",
        "hardware_backed_security_mainline",
        "input",
        "llvm_and_toolchains",
        "lse_desktop_experience",
        "machine_learning",
        "mainline_modularization",
        "mainline_sdk",
        "make_pixel_haptics",
        "media_audio",
        "media_drm",
        "media_reliability",
        "media_solutions",
        "media_tv",
        "nearby",
        "nfc",
        "pdf_viewer",
        "perfetto",
        "pixel_audio_android",
        "pixel_biometrics_face",
        "pixel_bluetooth",
        "pixel_connectivity_gps",
        "pixel_continuity",
        "pixel_sensors",
        "pixel_system_sw_video",
        "pixel_watch",
        "platform_compat",
        "platform_security",
        "pmw",
        "power",
        "preload_safety",
        "printing",
        "privacy_infra_policy",
        "resource_manager",
        "responsible_apis",
        "rust",
        "safety_center",
        "sensors",
        "spoon",
        "statsd",
        "system_performance",
        "system_sw_touch",
        "system_sw_usb",
        "test_suites",
        "text",
        "threadnetwork",
        "treble",
        "tv_system_ui",
        "usb",
        "vibrator",
        "virtual_devices",
        "virtualization",
        "wallet_integration",
        "wear_calling_messaging",
        "wear_connectivity",
        "wear_esim_carriers",
        "wear_frameworks",
        "wear_health_services",
        "wear_media",
        "wear_offload",
        "wear_security",
        "wear_system_health",
        "wear_systems",
        "wear_sysui",
        "window_surfaces",
        "windowing_frontend"
    };

    public SettingsToPropertiesMapper(
            ContentResolver contentResolver, String[] strArr, String[] strArr2, String[] strArr3) {
        this.mContentResolver = contentResolver;
        this.mGlobalSettings = strArr;
        this.mDeviceConfigScopes = strArr2;
        this.mDeviceConfigAconfigScopes = strArr3;
    }

    public static String getResetFlagsFileContent() {
        String str = null;
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new FileReader(
                                    new File("/data/server_configurable_flags/reset_flags")));
            str = bufferedReader.readLine();
            bufferedReader.close();
            return str;
        } catch (IOException e) {
            logErr(e, "failed to read file /data/server_configurable_flags/reset_flags");
            return str;
        }
    }

    public static void logErr(Exception exc, String str) {
        if (Build.IS_DEBUGGABLE) {
            Slog.wtf("SettingsToPropertiesMapper", str, exc);
        } else {
            Slog.e("SettingsToPropertiesMapper", str, exc);
        }
    }

    public static void logErr(String str) {
        if (Build.IS_DEBUGGABLE) {
            Slog.wtf("SettingsToPropertiesMapper", str);
        } else {
            Slog.e("SettingsToPropertiesMapper", str);
        }
    }

    public static String makeAconfigFlagPropertyName(String str, String str2) {
        String m =
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "persist.device_config.aconfig_flags.", str, ".", str2);
        if (!m.matches("^[\\w\\.\\-@:]*$") || m.contains("..")) {
            return null;
        }
        return m;
    }

    public static String makePropertyName(String str, String str2) {
        String m =
                BootReceiver$$ExternalSyntheticOutline0.m("persist.device_config.", str, ".", str2);
        if (!m.matches("^[\\w\\.\\-@:]*$") || m.contains("..")) {
            return null;
        }
        return m;
    }

    public static void setProperty(String str, String str2) {
        if (str2 == null) {
            if (TextUtils.isEmpty(SystemProperties.get(str))) {
                return;
            } else {
                str2 = "";
            }
        } else if (str2.length() > 92) {
            logErr("key=" + str + " value=" + str2 + " exceeds system property max length.");
            return;
        }
        try {
            SystemProperties.set(str, str2);
        } catch (Exception e) {
            logErr(
                    e,
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Unable to set property ", str, " value '", str2, "'"));
        }
    }

    public static void stageFlagsInNewStorage(DeviceConfig.Properties properties) {
        ProtoInputStream protoInputStream;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        Iterator it = properties.getKeyset().iterator();
        int i = 0;
        while (true) {
            protoInputStream = null;
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            String string = properties.getString(str, (String) null);
            if (str != null && string != null) {
                int indexOf = str.indexOf("*");
                if (indexOf == -1 || indexOf == str.length() - 1 || indexOf == 0) {
                    logErr("invalid local flag override: ".concat(str));
                } else {
                    str.substring(0, indexOf);
                    String substring = str.substring(indexOf + 1);
                    int lastIndexOf = substring.lastIndexOf(".");
                    if (lastIndexOf == -1) {
                        logErr("invalid flag name: ".concat(substring));
                    } else {
                        String substring2 = substring.substring(0, lastIndexOf);
                        String substring3 = substring.substring(lastIndexOf + 1);
                        long start = protoOutputStream.start(2246267895809L);
                        long start2 = protoOutputStream.start(1146756268034L);
                        protoOutputStream.write(1138166333441L, substring2);
                        protoOutputStream.write(1138166333442L, substring3);
                        protoOutputStream.write(1138166333443L, string);
                        protoOutputStream.write(1133871366148L, false);
                        protoOutputStream.end(start2);
                        protoOutputStream.end(start);
                        i++;
                    }
                }
            }
        }
        if (i == 0) {
            return;
        }
        LocalSocket localSocket = new LocalSocket();
        try {
            localSocket.connect(
                    new LocalSocketAddress("aconfigd", LocalSocketAddress.Namespace.RESERVED));
            Slog.d("SettingsToPropertiesMapper", "connected to aconfigd socket");
            try {
                DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
                DataOutputStream dataOutputStream =
                        new DataOutputStream(localSocket.getOutputStream());
                try {
                    byte[] bytes = protoOutputStream.getBytes();
                    dataOutputStream.writeInt(bytes.length);
                    dataOutputStream.write(bytes, 0, bytes.length);
                    Slog.d("SettingsToPropertiesMapper", "flag override requests sent to aconfigd");
                    try {
                        int readInt = dataInputStream.readInt();
                        ProtoInputStream protoInputStream2 = new ProtoInputStream(dataInputStream);
                        Slog.d(
                                "SettingsToPropertiesMapper",
                                "received " + readInt + " bytes back from aconfigd");
                        protoInputStream = protoInputStream2;
                    } catch (IOException e) {
                        logErr(e, "failed to read requests return from aconfigd");
                    }
                } catch (IOException e2) {
                    logErr(e2, "failed to send requests to aconfigd");
                }
            } catch (IOException e3) {
                logErr(e3, "failed to get local socket iostreams");
            }
        } catch (IOException e4) {
            logErr(e4, "failed to connect to aconfigd socket");
        }
        while (true) {
            try {
                int nextField = protoInputStream.nextField();
                if (nextField == -1) {
                    return;
                }
                if (nextField != 1) {
                    logErr("invalid message type, expect storage return message");
                } else {
                    long start3 = protoInputStream.start(2246267895809L);
                    int nextField2 = protoInputStream.nextField();
                    if (nextField2 != -1) {
                        if (nextField2 == 2) {
                            Slog.d(
                                    "SettingsToPropertiesMapper",
                                    "successfully handled override requests");
                            protoInputStream.end(protoInputStream.start(1146756268034L));
                        } else if (nextField2 != 8) {
                            logErr(
                                    "invalid message type, expecting only flag override return or"
                                        + " error message");
                        } else {
                            Slog.d(
                                    "SettingsToPropertiesMapper",
                                    "override request failed: "
                                            + protoInputStream.readString(1138166333448L));
                        }
                    }
                    protoInputStream.end(start3);
                }
            } catch (IOException e5) {
                logErr(e5, "failed to parse aconfigd return");
                return;
            }
        }
    }

    public void updatePropertiesFromSettings() {
        for (final String str : this.mGlobalSettings) {
            Uri uriFor = Settings.Global.getUriFor(str);
            final String makePropertyName = makePropertyName("global_settings", str);
            if (uriFor == null) {
                logErr("setting uri is null for globalSetting " + str);
            } else if (makePropertyName == null) {
                logErr("invalid prop name for globalSetting " + str);
            } else {
                ContentObserver contentObserver =
                        new ContentObserver() { // from class:
                                                // com.android.server.am.SettingsToPropertiesMapper.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(null);
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                SettingsToPropertiesMapper.this.updatePropertyFromSetting(
                                        str, makePropertyName);
                            }
                        };
                if (!"true".equals(SystemProperties.get("device_config.reset_performed"))) {
                    updatePropertyFromSetting(str, makePropertyName);
                }
                this.mContentResolver.registerContentObserver(uriFor, false, contentObserver);
            }
        }
        for (String str2 : this.mDeviceConfigScopes) {
            final int i = 0;
            DeviceConfig.addOnPropertiesChangedListener(
                    str2,
                    AsyncTask.THREAD_POOL_EXECUTOR,
                    new DeviceConfig.OnPropertiesChangedListener(
                            this) { // from class:
                                    // com.android.server.am.SettingsToPropertiesMapper$$ExternalSyntheticLambda0
                        public final /* synthetic */ SettingsToPropertiesMapper f$0;

                        {
                            this.f$0 = this;
                        }

                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            int i2 = i;
                            this.f$0.getClass();
                            switch (i2) {
                                case 0:
                                    String namespace = properties.getNamespace();
                                    for (String str3 : properties.getKeyset()) {
                                        String makePropertyName2 =
                                                SettingsToPropertiesMapper.makePropertyName(
                                                        namespace, str3);
                                        if (makePropertyName2 == null) {
                                            SettingsToPropertiesMapper.logErr(
                                                    "unable to construct system property for "
                                                            + namespace
                                                            + "/"
                                                            + str3);
                                            break;
                                        } else {
                                            SettingsToPropertiesMapper.setProperty(
                                                    makePropertyName2,
                                                    properties.getString(str3, (String) null));
                                            String makeAconfigFlagPropertyName =
                                                    SettingsToPropertiesMapper
                                                            .makeAconfigFlagPropertyName(
                                                                    namespace, str3);
                                            if (makeAconfigFlagPropertyName == null) {
                                                SettingsToPropertiesMapper.logErr(
                                                        "unable to construct system property for "
                                                                + namespace
                                                                + "/"
                                                                + str3);
                                                break;
                                            } else {
                                                SettingsToPropertiesMapper.setProperty(
                                                        makeAconfigFlagPropertyName,
                                                        properties.getString(str3, (String) null));
                                            }
                                        }
                                    }
                                    break;
                                case 1:
                                    String namespace2 = properties.getNamespace();
                                    for (String str4 : properties.getKeyset()) {
                                        String makeAconfigFlagPropertyName2 =
                                                SettingsToPropertiesMapper
                                                        .makeAconfigFlagPropertyName(
                                                                namespace2, str4);
                                        if (makeAconfigFlagPropertyName2 == null) {
                                            SettingsToPropertiesMapper.logErr(
                                                    "unable to construct system property for "
                                                            + namespace2
                                                            + "/"
                                                            + str4);
                                            break;
                                        } else {
                                            SettingsToPropertiesMapper.setProperty(
                                                    makeAconfigFlagPropertyName2,
                                                    properties.getString(str4, (String) null));
                                        }
                                    }
                                    break;
                                default:
                                    for (String str5 : properties.getKeyset()) {
                                        String string = properties.getString(str5, (String) null);
                                        if (str5 != null && string != null) {
                                            int indexOf = str5.indexOf("*");
                                            if (indexOf == -1
                                                    || indexOf == str5.length() - 1
                                                    || indexOf == 0) {
                                                SettingsToPropertiesMapper.logErr(
                                                        "invalid staged flag: ".concat(str5));
                                            } else {
                                                SettingsToPropertiesMapper.setProperty(
                                                        "next_boot."
                                                                + SettingsToPropertiesMapper
                                                                        .makeAconfigFlagPropertyName(
                                                                                str5.substring(
                                                                                        0, indexOf),
                                                                                str5.substring(
                                                                                        indexOf
                                                                                                + 1)),
                                                        string);
                                            }
                                        }
                                    }
                                    com.android.aconfig_new_storage.Flags
                                            .enableAconfigStorageDaemon();
                                    break;
                            }
                        }
                    });
        }
        for (String str3 : this.mDeviceConfigAconfigScopes) {
            final int i2 = 1;
            DeviceConfig.addOnPropertiesChangedListener(
                    str3,
                    AsyncTask.THREAD_POOL_EXECUTOR,
                    new DeviceConfig.OnPropertiesChangedListener(
                            this) { // from class:
                                    // com.android.server.am.SettingsToPropertiesMapper$$ExternalSyntheticLambda0
                        public final /* synthetic */ SettingsToPropertiesMapper f$0;

                        {
                            this.f$0 = this;
                        }

                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            int i22 = i2;
                            this.f$0.getClass();
                            switch (i22) {
                                case 0:
                                    String namespace = properties.getNamespace();
                                    for (String str32 : properties.getKeyset()) {
                                        String makePropertyName2 =
                                                SettingsToPropertiesMapper.makePropertyName(
                                                        namespace, str32);
                                        if (makePropertyName2 == null) {
                                            SettingsToPropertiesMapper.logErr(
                                                    "unable to construct system property for "
                                                            + namespace
                                                            + "/"
                                                            + str32);
                                            break;
                                        } else {
                                            SettingsToPropertiesMapper.setProperty(
                                                    makePropertyName2,
                                                    properties.getString(str32, (String) null));
                                            String makeAconfigFlagPropertyName =
                                                    SettingsToPropertiesMapper
                                                            .makeAconfigFlagPropertyName(
                                                                    namespace, str32);
                                            if (makeAconfigFlagPropertyName == null) {
                                                SettingsToPropertiesMapper.logErr(
                                                        "unable to construct system property for "
                                                                + namespace
                                                                + "/"
                                                                + str32);
                                                break;
                                            } else {
                                                SettingsToPropertiesMapper.setProperty(
                                                        makeAconfigFlagPropertyName,
                                                        properties.getString(str32, (String) null));
                                            }
                                        }
                                    }
                                    break;
                                case 1:
                                    String namespace2 = properties.getNamespace();
                                    for (String str4 : properties.getKeyset()) {
                                        String makeAconfigFlagPropertyName2 =
                                                SettingsToPropertiesMapper
                                                        .makeAconfigFlagPropertyName(
                                                                namespace2, str4);
                                        if (makeAconfigFlagPropertyName2 == null) {
                                            SettingsToPropertiesMapper.logErr(
                                                    "unable to construct system property for "
                                                            + namespace2
                                                            + "/"
                                                            + str4);
                                            break;
                                        } else {
                                            SettingsToPropertiesMapper.setProperty(
                                                    makeAconfigFlagPropertyName2,
                                                    properties.getString(str4, (String) null));
                                        }
                                    }
                                    break;
                                default:
                                    for (String str5 : properties.getKeyset()) {
                                        String string = properties.getString(str5, (String) null);
                                        if (str5 != null && string != null) {
                                            int indexOf = str5.indexOf("*");
                                            if (indexOf == -1
                                                    || indexOf == str5.length() - 1
                                                    || indexOf == 0) {
                                                SettingsToPropertiesMapper.logErr(
                                                        "invalid staged flag: ".concat(str5));
                                            } else {
                                                SettingsToPropertiesMapper.setProperty(
                                                        "next_boot."
                                                                + SettingsToPropertiesMapper
                                                                        .makeAconfigFlagPropertyName(
                                                                                str5.substring(
                                                                                        0, indexOf),
                                                                                str5.substring(
                                                                                        indexOf
                                                                                                + 1)),
                                                        string);
                                            }
                                        }
                                    }
                                    com.android.aconfig_new_storage.Flags
                                            .enableAconfigStorageDaemon();
                                    break;
                            }
                        }
                    });
        }
        Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
        final int i3 = 2;
        DeviceConfig.addOnPropertiesChangedListener(
                "staged",
                executor,
                new DeviceConfig.OnPropertiesChangedListener(
                        this) { // from class:
                                // com.android.server.am.SettingsToPropertiesMapper$$ExternalSyntheticLambda0
                    public final /* synthetic */ SettingsToPropertiesMapper f$0;

                    {
                        this.f$0 = this;
                    }

                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        int i22 = i3;
                        this.f$0.getClass();
                        switch (i22) {
                            case 0:
                                String namespace = properties.getNamespace();
                                for (String str32 : properties.getKeyset()) {
                                    String makePropertyName2 =
                                            SettingsToPropertiesMapper.makePropertyName(
                                                    namespace, str32);
                                    if (makePropertyName2 == null) {
                                        SettingsToPropertiesMapper.logErr(
                                                "unable to construct system property for "
                                                        + namespace
                                                        + "/"
                                                        + str32);
                                        break;
                                    } else {
                                        SettingsToPropertiesMapper.setProperty(
                                                makePropertyName2,
                                                properties.getString(str32, (String) null));
                                        String makeAconfigFlagPropertyName =
                                                SettingsToPropertiesMapper
                                                        .makeAconfigFlagPropertyName(
                                                                namespace, str32);
                                        if (makeAconfigFlagPropertyName == null) {
                                            SettingsToPropertiesMapper.logErr(
                                                    "unable to construct system property for "
                                                            + namespace
                                                            + "/"
                                                            + str32);
                                            break;
                                        } else {
                                            SettingsToPropertiesMapper.setProperty(
                                                    makeAconfigFlagPropertyName,
                                                    properties.getString(str32, (String) null));
                                        }
                                    }
                                }
                                break;
                            case 1:
                                String namespace2 = properties.getNamespace();
                                for (String str4 : properties.getKeyset()) {
                                    String makeAconfigFlagPropertyName2 =
                                            SettingsToPropertiesMapper.makeAconfigFlagPropertyName(
                                                    namespace2, str4);
                                    if (makeAconfigFlagPropertyName2 == null) {
                                        SettingsToPropertiesMapper.logErr(
                                                "unable to construct system property for "
                                                        + namespace2
                                                        + "/"
                                                        + str4);
                                        break;
                                    } else {
                                        SettingsToPropertiesMapper.setProperty(
                                                makeAconfigFlagPropertyName2,
                                                properties.getString(str4, (String) null));
                                    }
                                }
                                break;
                            default:
                                for (String str5 : properties.getKeyset()) {
                                    String string = properties.getString(str5, (String) null);
                                    if (str5 != null && string != null) {
                                        int indexOf = str5.indexOf("*");
                                        if (indexOf == -1
                                                || indexOf == str5.length() - 1
                                                || indexOf == 0) {
                                            SettingsToPropertiesMapper.logErr(
                                                    "invalid staged flag: ".concat(str5));
                                        } else {
                                            SettingsToPropertiesMapper.setProperty(
                                                    "next_boot."
                                                            + SettingsToPropertiesMapper
                                                                    .makeAconfigFlagPropertyName(
                                                                            str5.substring(
                                                                                    0, indexOf),
                                                                            str5.substring(
                                                                                    indexOf + 1)),
                                                    string);
                                        }
                                    }
                                }
                                com.android.aconfig_new_storage.Flags.enableAconfigStorageDaemon();
                                break;
                        }
                    }
                });
        DeviceConfig.addOnPropertiesChangedListener(
                "device_config_overrides",
                executor,
                new SettingsToPropertiesMapper$$ExternalSyntheticLambda3());
    }

    public void updatePropertyFromSetting(String str, String str2) {
        setProperty(str2, Settings.Global.getString(this.mContentResolver, str));
    }
}
