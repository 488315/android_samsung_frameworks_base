package com.android.systemui.qs;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Operator;
import com.android.systemui.QpRune;
import com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class TileFeatureChecker {
    public final Context context;
    public final DefaultTilesRepository defaultTilesRepository;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TileFeatureChecker(Context context, UserTracker userTracker, DefaultTilesRepository defaultTilesRepository) {
        this.context = context;
        this.userTracker = userTracker;
        this.defaultTilesRepository = defaultTilesRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x024a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isAvailableCustomTile(TileSpec tileSpec) {
        boolean z;
        if (tileSpec instanceof TileSpec.PlatformTileSpec) {
            return true;
        }
        if (!(tileSpec instanceof TileSpec.CustomTileSpec)) {
            Log.e("TileFeatureChecker", "Invalid TileSpec " + tileSpec);
            return false;
        }
        String strFlattenToShortString = ((TileSpec.CustomTileSpec) tileSpec).componentName.flattenToShortString();
        Log.i("TileFeatureChecker", "isAvailableCustomTile " + tileSpec + "  " + strFlattenToShortString);
        boolean zEquals = "com.sec.android.app.soundalive/.DolbyTile".equals(strFlattenToShortString);
        UserTracker userTracker = this.userTracker;
        if (zEquals) {
            if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_DOLBY_AUDIO", false) || ((UserTrackerImpl) userTracker).getUserId() != 0) {
                Log.d("TileFeatureChecker", "isAvailableCustomTile : DolbyTile is removed ");
                return false;
            }
        } else if ("com.samsung.android.app.aodservice/.settings.AODTileService".equals(strFlattenToShortString)) {
            if (!StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", ""), "aodversion", false)) {
                Log.d("TileFeatureChecker", "isAvailableCustomTile : AodTile is removed ");
                return false;
            }
        } else if ("com.samsung.android.smartmirroring/.tile.SmartMirroringTile".equals(strFlattenToShortString)) {
            int iSemCheckScreenSharingSupported = ((DisplayManager) this.context.getSystemService("display")).semCheckScreenSharingSupported();
            if (iSemCheckScreenSharingSupported != 1 && iSemCheckScreenSharingSupported != 0) {
                Log.d("TileFeatureChecker", "isAvailableCustomTile : AllShareCastTile is removed ");
                return false;
            }
        } else if ("com.samsung.android.nfc/.quicktile.NfcTile".equals(strFlattenToShortString)) {
            if (!this.context.getPackageManager().hasSystemFeature("android.hardware.nfc")) {
                Log.d("TileFeatureChecker", "isAvailableCustomTile : NfcTile is removed ");
                return false;
            }
        } else if ("com.samsung.knox.securefolder/.switcher.SecureFolderTile".equals(strFlattenToShortString)) {
            if (!((SemPersonaManager) this.context.getSystemService("persona")).isUserManaged() || ((UserTrackerImpl) userTracker).getUserId() != 0) {
                return false;
            }
        } else if ("com.android.settings/com.samsung.android.settings.qstile.SecAccountTiles".equals(strFlattenToShortString)) {
            if (((UserManager) this.context.getSystemService("user")).getUserInfo(((UserTrackerImpl) userTracker).getUserId()).isRestricted()) {
                Log.d("TileFeatureChecker", "isAvailableCustomTile : Sync is removed ");
                return false;
            }
        } else {
            if ("com.samsung.android.homemode/.external.service.HomeModeTileService".equals(strFlattenToShortString)) {
                if (!"".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DAILYBOARD", ""))) {
                    if (((UserTrackerImpl) userTracker).getUserId() == 0 ? true : isPackageAvailable("com.samsung.android.homemode")) {
                    }
                }
                return false;
            }
            if ("com.samsung.android.lool/com.samsung.android.sm.battery.ui.mode.BatteryModeTile".equals(strFlattenToShortString)) {
                return isPackageAvailable(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
            }
            if ("com.sec.unifiedwfc/.ux.quicksettings.WFCQSTileService".equals(strFlattenToShortString)) {
                if (DeviceState.getVoWifiEnableState(this.context) != 0) {
                }
            } else {
                if (!"com.samsung.android.service.aircommand/.remotespen.RemoteSpenTileService".equals(strFlattenToShortString)) {
                    if ("com.samsung.android.lool/com.samsung.android.sm.powershare.PowerShareTileService".equals(strFlattenToShortString)) {
                        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_TX");
                    }
                    if ("com.samsung.android.bixby.service/com.samsung.android.bixby.settings.powerkey.PowerKeySettingTileService".equals(strFlattenToShortString)) {
                        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_BIXBY_CONFIG_HWKEY");
                        if (!TextUtils.isEmpty(string)) {
                            return StringsKt__StringsKt.contains(string, "pwrkey", false);
                        }
                    } else {
                        if ("com.sec.android.app.camera/.service.QrTileService".equals(strFlattenToShortString)) {
                            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_QRCODE");
                        }
                        if ("com.samsung.android.app.smartcapture/com.samsung.android.app.screenrecorder.view.RecordScreenTile".equals(strFlattenToShortString)) {
                            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_SCREEN_RECORDER");
                        }
                        if ("com.android.settings/com.samsung.android.settings.bluetooth.BluetoothCastTile".equals(strFlattenToShortString)) {
                            return QpRune.QUICK_BLUETOOTH_MUSIC_SHARE;
                        }
                        if (!"com.samsung.android.secondscreen/.tile.ScreenSharingTile".equals(strFlattenToShortString)) {
                            if ("com.samsung.android.app.telephonyui/.carrierui.tile.TurnOn5gTileService".equals(strFlattenToShortString)) {
                                if (DeviceType.isSupport5G()) {
                                    if (!Operator.isChinaQsTileBranding()) {
                                        if (!(Operator.QUICK_IS_BRI_BRANDING || Operator.QUICK_IS_TGY_BRANDING)) {
                                            z = true;
                                        }
                                        if (!z) {
                                        }
                                    }
                                    z = false;
                                    if (!z) {
                                    }
                                }
                            } else {
                                if ("com.samsung.android.app.telephonyui/.carrierui.tile.VoLteTileService".equals(strFlattenToShortString)) {
                                    return ((ArrayList) this.defaultTilesRepository.getDefaultTiles()).contains(tileSpec);
                                }
                                if ("com.samsung.android.homehub/.external.service.HomeHubTileService".equals(strFlattenToShortString)) {
                                    return !TextUtils.isEmpty(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB"));
                                }
                                if (!"com.sec.android.app.launcher/com.honeyspace.dexservice.DesktopModeTile".equals(strFlattenToShortString)) {
                                    if ("com.google.android.gms/.nearby.sharing.SharingTileService".equals(strFlattenToShortString)) {
                                        if (Build.VERSION.SEM_PLATFORM_INT < 150100) {
                                        }
                                    } else if ("com.samsung.android.app.interpreter/.interpretation.view.InterpreterQuickTileService".equals(strFlattenToShortString)) {
                                        return !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI");
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BLE_SPEN") || DeviceType.isSupportUnbundledBleSPen()) {
                }
            }
        }
        return true;
    }

    public final boolean isPackageAvailable(String str) {
        try {
            this.context.getPackageManager().getPackageInfoAsUser(str, 0, ((UserTrackerImpl) this.userTracker).getUserId());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("TileFeatureChecker", "Package not available: " + str, e);
            return false;
        }
    }
}
