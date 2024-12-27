package com.android.server.gpu;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.updatabledriver.UpdatableDriverProto;
import android.util.Base64;

import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.server.SystemService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class GpuService extends SystemService {
    public ContentResolver mContentResolver;
    public final Context mContext;
    public UpdatableDriverProto.Denylists mDenylists;
    public final String mDevDriverPackageName;
    public final Object mDeviceConfigLock;
    public final boolean mHasDevDriver;
    public final boolean mHasProdDriver;
    public final Object mLock;
    public final PackageManager mPackageManager;
    public final String mProdDriverPackageName;
    public long mProdDriverVersionCode;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public DeviceConfigListener() {
            DeviceConfig.addOnPropertiesChangedListener(
                    "game_driver", GpuService.this.mContext.getMainExecutor(), this);
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            synchronized (GpuService.this.mDeviceConfigLock) {
                try {
                    if (properties.getKeyset().contains("updatable_driver_production_denylists")) {
                        GpuService.this.parseDenylists(
                                properties.getString("updatable_driver_production_denylists", ""));
                        GpuService.this.setDenylist();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageReceiver extends BroadcastReceiver {
        public PackageReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            boolean equals = schemeSpecificPart.equals(GpuService.this.mProdDriverPackageName);
            boolean equals2 = schemeSpecificPart.equals(GpuService.this.mDevDriverPackageName);
            if (equals || equals2) {
                String action = intent.getAction();
                action.getClass();
                switch (action) {
                    case "android.intent.action.PACKAGE_CHANGED":
                    case "android.intent.action.PACKAGE_REMOVED":
                    case "android.intent.action.PACKAGE_ADDED":
                        if (!equals) {
                            if (equals2) {
                                GpuService.this.fetchPrereleaseDriverPackageProperties();
                                break;
                            }
                        } else {
                            GpuService.this.fetchProductionDriverPackageProperties();
                            GpuService.this.setDenylist();
                            break;
                        }
                        break;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri mProdDriverDenylistsUri;

        public SettingsObserver() {
            super(new Handler());
            Uri uriFor = Settings.Global.getUriFor("updatable_driver_production_denylists");
            this.mProdDriverDenylistsUri = uriFor;
            GpuService.this.mContentResolver.registerContentObserver(uriFor, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri != null && this.mProdDriverDenylistsUri.equals(uri)) {
                GpuService gpuService = GpuService.this;
                gpuService.getClass();
                String property =
                        DeviceConfig.getProperty(
                                "game_driver", "updatable_driver_production_denylists");
                if (property == null) {
                    property =
                            Settings.Global.getString(
                                    gpuService.mContentResolver,
                                    "updatable_driver_production_denylists");
                }
                if (property == null) {
                    property = "";
                }
                gpuService.parseDenylists(property);
                GpuService.this.setDenylist();
            }
        }
    }

    public GpuService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mDeviceConfigLock = new Object();
        this.mContext = context;
        String str = SystemProperties.get("ro.gfx.driver.0");
        this.mProdDriverPackageName = str;
        this.mProdDriverVersionCode = -1L;
        String str2 = SystemProperties.get("ro.gfx.driver.1");
        this.mDevDriverPackageName = str2;
        this.mPackageManager = context.getPackageManager();
        boolean z = !TextUtils.isEmpty(str);
        this.mHasProdDriver = z;
        boolean z2 = !TextUtils.isEmpty(str2);
        this.mHasDevDriver = z2;
        if (z2 || z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            getContext()
                    .registerReceiverAsUser(
                            new PackageReceiver(), UserHandle.ALL, intentFilter, null, null);
        }
    }

    private static native void nSetUpdatableDriverPath(String str);

    public final void fetchPrereleaseDriverPackageProperties() {
        try {
            ApplicationInfo applicationInfo =
                    this.mPackageManager.getApplicationInfo(this.mDevDriverPackageName, 1048576);
            if (applicationInfo.targetSdkVersion < 26) {
                return;
            }
            if (applicationInfo.primaryCpuAbi == null) {
                nSetUpdatableDriverPath("");
            } else {
                nSetUpdatableDriverPath(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), applicationInfo.sourceDir, "!/lib/"));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final void fetchProductionDriverPackageProperties() {
        String str = this.mProdDriverPackageName;
        try {
            ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, 1048576);
            if (applicationInfo.targetSdkVersion < 26) {
                return;
            }
            Settings.Global.putString(
                    this.mContentResolver, "updatable_driver_production_allowlist", "");
            this.mProdDriverVersionCode = applicationInfo.longVersionCode;
            Context createPackageContext = this.mContext.createPackageContext(str, 4);
            Context context = this.mContext;
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    createPackageContext.getAssets().open("allowlist.txt")));
            ArrayList arrayList = new ArrayList();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    Settings.Global.putString(
                            context.getContentResolver(),
                            "updatable_driver_production_allowlist",
                            String.join(",", arrayList));
                    return;
                }
                arrayList.add(readLine);
            }
        } catch (PackageManager.NameNotFoundException | IOException unused) {
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            this.mContentResolver = this.mContext.getContentResolver();
            if (this.mHasProdDriver || this.mHasDevDriver) {
                new SettingsObserver();
                new DeviceConfigListener();
                fetchProductionDriverPackageProperties();
                String property =
                        DeviceConfig.getProperty(
                                "game_driver", "updatable_driver_production_denylists");
                if (property == null) {
                    property =
                            Settings.Global.getString(
                                    this.mContentResolver, "updatable_driver_production_denylists");
                }
                if (property == null) {
                    property = "";
                }
                parseDenylists(property);
                setDenylist();
                fetchPrereleaseDriverPackageProperties();
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {}

    public final void parseDenylists(String str) {
        synchronized (this.mLock) {
            this.mDenylists = null;
            try {
                this.mDenylists = UpdatableDriverProto.Denylists.parseFrom(Base64.decode(str, 3));
            } catch (IllegalArgumentException | InvalidProtocolBufferException unused) {
            }
        }
    }

    public final void setDenylist() {
        Settings.Global.putString(
                this.mContentResolver, "updatable_driver_production_denylist", "");
        synchronized (this.mLock) {
            try {
                UpdatableDriverProto.Denylists denylists = this.mDenylists;
                if (denylists == null) {
                    return;
                }
                for (UpdatableDriverProto.Denylist denylist : denylists.getDenylistsList()) {
                    if (denylist.getVersionCode() == this.mProdDriverVersionCode) {
                        Settings.Global.putString(
                                this.mContentResolver,
                                "updatable_driver_production_denylist",
                                String.join(",", denylist.getPackageNamesList()));
                        return;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
