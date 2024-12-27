package com.android.server.chimera.genie;

import android.os.SystemProperties;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GenieConfigurations {
    public static final int MODEL_COUNT =
            Integer.parseInt(SystemProperties.get("ro.slmk.genie.model_count", "1"));
    public static final int GENAI_UNLOAD_THRESHOLD =
            Integer.parseInt(SystemProperties.get("ro.slmk.genie.unload.threshold", "2097152"));
    public static final int GENAI_UNLOAD_OOMADJ_THRESHOLD =
            Integer.parseInt(SystemProperties.get("ro.slmk.genie.oomadj.threshold", "850"));
    public static final int GENAI_UNLOAD_MEMORY_PSI_LEVEL =
            Integer.parseInt(SystemProperties.get("ro.slmk.genie.oomadj.psilevel", "1"));
    public static final int GENAI_UNLOAD_MODEL_TIMEOUT =
            Integer.parseInt(SystemProperties.get("ro.slmk.genie.unload.timeout", "7200")) * 1000;
    public static final int sAIVersion =
            SemFloatingFeature.getInstance()
                    .getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION", -1);
    public static final List sAllowedPackages =
            new ArrayList(
                    Arrays.asList(
                            "com.sec.android.app.camera",
                            KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME,
                            "com.samsung.android.offline.languagemodel.test_client",
                            "com.samsung.android.offline.languagemodel",
                            "com.samsung.android.wallpaper.magician",
                            "com.samsung.android.wallpaper.live",
                            "com.sec.android.mimage.photoretouching",
                            "com.sec.android.gallery3d",
                            "com.samsung.android.app.notes",
                            "com.sec.android.app.voicenote",
                            "com.sec.android.app.shealth",
                            "com.samsung.android.app.moments",
                            "com.samsung.android.oneconnect",
                            KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME,
                            "com.samsung.android.dialer",
                            "com.samsung.android.smartsuggestions",
                            "com.samsung.android.smartsuggestions:moneta",
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            "com.google.android.apps.messaging",
                            "com.samsung.android.accessibility.talkback"));
    public static final List sAllowedBroadcastActions =
            new ArrayList(
                    Arrays.asList(
                            "com.samsung.GEN_AI_RECLAIM", "AICORE_BROADCAST_ACTION_MODEL_LOADING"));
    public static final Map sAIVersionSepModelSize =
            new HashMap() { // from class: com.android.server.chimera.genie.GenieConfigurations.1
                {
                    put(20241, 1048576);
                    put(20242, 1048576);
                    put(20251, 2728960);
                    put(20252, 2728960);
                }
            };
    public static final Map sAIVersionGoogleModelSize =
            new HashMap() { // from class: com.android.server.chimera.genie.GenieConfigurations.2
                {
                    put(20241, 1048576);
                    put(20242, 1048576);
                    put(20251, 2728960);
                    put(20252, 2728960);
                }
            };
    public static final Map sGenAIPackageMap =
            new HashMap() { // from class: com.android.server.chimera.genie.GenieConfigurations.3
                {
                    put(
                            KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME,
                            "com.samsung.android.offline.languagemodel");
                    put(
                            "com.samsung.android.app.notes",
                            "com.samsung.android.offline.languagemodel");
                    put("com.sec.android.app.shealth", "com.samsung.android.offline.languagemodel");
                    put("com.samsung.GEN_AI_RECLAIM", "com.samsung.android.offline.languagemodel");
                    put(
                            "com.sec.android.app.voicenote",
                            "com.samsung.android.offline.languagemodel");
                    put(
                            "com.samsung.android.oneconnect",
                            "com.samsung.android.offline.languagemodel");
                    put(
                            "com.samsung.android.app.moments",
                            "com.samsung.android.offline.languagemodel");
                    put(
                            KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME,
                            "com.samsung.android.offline.languagemodel");
                    put("com.samsung.android.dialer", "com.samsung.android.offline.languagemodel");
                    put(
                            "com.samsung.android.smartsuggestions",
                            "com.samsung.android.offline.languagemodel");
                    put(
                            "com.samsung.android.smartsuggestions:moneta",
                            "com.samsung.android.offline.languagemodel");
                    put(
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            "com.samsung.android.offline.languagemodel");
                }
            };
}
