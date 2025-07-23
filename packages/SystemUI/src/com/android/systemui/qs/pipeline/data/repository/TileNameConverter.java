package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.qs.external.CustomTile;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileNameConverter {
    public static final TileNameConverter INSTANCE = new TileNameConverter();
    public static final HashMap componentNameTable = new HashMap();
    public static final HashMap aliasNameTable = new HashMap();

    private TileNameConverter() {
    }

    public static String toTileLoggingName(Resources resources, String str) {
        Collection collection;
        HashMap hashMap = aliasNameTable;
        if (hashMap.isEmpty()) {
            List split = new Regex(",").split(resources.getString(R.string.quick_settings_custom_tile_component_names));
            if (!split.isEmpty()) {
                ListIterator listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        collection = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            collection = EmptyList.INSTANCE;
            for (String str2 : (String[]) collection.toArray(new String[0])) {
                str2.getClass();
                int indexOf$default = StringsKt__StringsKt.indexOf$default(str2, ":", 0, false, 6);
                aliasNameTable.put(str2.substring(indexOf$default + 1, str2.length()), str2.substring(0, indexOf$default));
            }
            hashMap = aliasNameTable;
        }
        return (String) hashMap.get(CustomTile.getComponentFromSpec(str).flattenToShortString());
    }

    public static String toTileSpec(Resources resources, String str) {
        Collection collection;
        if (Intrinsics.areEqual(str, "BatteryMode")) {
            if ("com.samsung.android.sm_cn".equals(Operator.smartManagerPackageName)) {
                str = "BatteryModeCHN";
            }
        } else if (Intrinsics.areEqual(str, "PowerShare") && "com.samsung.android.sm_cn".equals(Operator.smartManagerPackageName)) {
            str = "PowerShareCHN";
        }
        Locale locale = Locale.US;
        if ("WIFIHOTSPOT".equals(str.toUpperCase(locale))) {
            str = "Hotspot";
        } else if ("AUTOROTATE".equals(str.toUpperCase(locale))) {
            str = "RotationLock";
        } else if ("TORCHLIGHT".equals(str.toUpperCase(locale))) {
            str = PluginLockShortcutTask.FLASH_LIGHT_TASK;
        } else if ("SILENTMODE".equals(str.toUpperCase(locale)) || "SOUNDMODE".equals(str.toUpperCase(locale))) {
            str = "SoundMode";
        } else if ("DND".equals(str.toUpperCase(locale)) || "DORMANTMODE".equals(str.toUpperCase(locale))) {
            str = PluginLockShortcutTask.DO_NOT_DISTURB_TASK;
        } else if ("WORK".equals(str.toUpperCase(locale))) {
            str = "WorkMode";
        } else if ("NIGHTMODE".equals(str.toUpperCase(locale)) || str.equals(componentNameTable.get("NightMode"))) {
            str = "UiModeNight";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.accessibility/.vision.viewclear.extradim.ReduceBrightnessTileService", false)) {
            str = "ReduceBrightColors";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.accessibility/.vision.viewclear.HighContrastFontTileService", false)) {
            str = "HighContrastFont";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.accessibility/.vision.viewclear.ColorInversionTileService", false)) {
            str = "ColorInversion";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.accessibility/.vision.color.ColorLensTileService", false)) {
            str = "ColorLens";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.accessibility/.vision.color.ColorAdjustmentTileService", false)) {
            str = "ColorAdjustment";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.accessibility/.vision.color.AccessibilityColorCorrectionTileService", false)) {
            str = "ColorCorrection";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.android.bixby.interpreter/.interpretation.view.InterpreterQuickTileService", false)) {
            str = "custom(com.samsung.android.app.interpreter/.interpretation.view.InterpreterQuickTileService)";
        } else if (StringsKt__StringsKt.contains(str, "com.android.nfc/com.samsung.android.nfc.quicktile.NfcTile", false)) {
            str = "custom(com.samsung.android.nfc/.quicktile.NfcTile)";
        } else if (StringsKt__StringsKt.contains(str, "com.sec.android.desktopmode.uiservice/.DesktopModeTile", false)) {
            str = "custom(com.sec.android.app.launcher/com.honeyspace.dexservice.DesktopModeTile)";
        } else if (StringsKt__StringsKt.contains(str, "com.samsung.android.smartmirroring/.tile.ScreenSharingTile", false)) {
            str = "custom(com.samsung.android.secondscreen/.tile.ScreenSharingTile)";
        }
        HashMap hashMap = componentNameTable;
        if (hashMap.isEmpty()) {
            List split = new Regex(",").split(resources.getString(R.string.quick_settings_custom_tile_component_names));
            if (!split.isEmpty()) {
                ListIterator listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        collection = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            collection = EmptyList.INSTANCE;
            for (String str2 : (String[]) collection.toArray(new String[0])) {
                str2.getClass();
                int indexOf$default = StringsKt__StringsKt.indexOf$default(str2, ":", 0, false, 6);
                String substring = str2.substring(0, indexOf$default);
                String substring2 = str2.substring(indexOf$default + 1, str2.length());
                componentNameTable.put(substring, substring2);
                Log.d("TileNameConverter", "make table : customTileName = " + substring + ", componentName = " + substring2);
            }
            hashMap = componentNameTable;
        }
        String str3 = (String) hashMap.get(str);
        return str3 != null ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("custom(", str3, ")") : str;
    }
}
