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

/* loaded from: classes2.dex */
public final class TileNameConverter {
    public static final TileNameConverter INSTANCE = new TileNameConverter();
    public static final HashMap componentNameTable = new HashMap();
    public static final HashMap aliasNameTable = new HashMap();

    private TileNameConverter() {
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0059 A[LOOP:1: B:15:0x0057->B:16:0x0059, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String toTileLoggingName(Resources resources, String str) {
        Collection collectionTake;
        HashMap map = aliasNameTable;
        if (map.isEmpty()) {
            List listSplit = new Regex(",").split(resources.getString(R.string.quick_settings_custom_tile_component_names));
            if (listSplit.isEmpty()) {
                collectionTake = EmptyList.INSTANCE;
                while (i < r1) {
                }
                map = aliasNameTable;
            } else {
                ListIterator listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        collectionTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                collectionTake = EmptyList.INSTANCE;
                for (String str2 : (String[]) collectionTake.toArray(new String[0])) {
                    str2.getClass();
                    int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str2, ":", 0, false, 6);
                    aliasNameTable.put(str2.substring(iIndexOf$default + 1, str2.length()), str2.substring(0, iIndexOf$default));
                }
                map = aliasNameTable;
            }
        }
        return (String) map.get(CustomTile.getComponentFromSpec(str).flattenToShortString());
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0189 A[LOOP:1: B:85:0x0187->B:86:0x0189, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String toTileSpec(Resources resources, String str) {
        Collection collectionTake;
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
        HashMap map = componentNameTable;
        if (map.isEmpty()) {
            List listSplit = new Regex(",").split(resources.getString(R.string.quick_settings_custom_tile_component_names));
            if (listSplit.isEmpty()) {
                collectionTake = EmptyList.INSTANCE;
                while (i < r0) {
                }
                map = componentNameTable;
            } else {
                ListIterator listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (((String) listIterator.previous()).length() != 0) {
                        collectionTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                collectionTake = EmptyList.INSTANCE;
                for (String str2 : (String[]) collectionTake.toArray(new String[0])) {
                    str2.getClass();
                    int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str2, ":", 0, false, 6);
                    String strSubstring = str2.substring(0, iIndexOf$default);
                    String strSubstring2 = str2.substring(iIndexOf$default + 1, str2.length());
                    componentNameTable.put(strSubstring, strSubstring2);
                    Log.d("TileNameConverter", "make table : customTileName = " + strSubstring + ", componentName = " + strSubstring2);
                }
                map = componentNameTable;
            }
        }
        String str3 = (String) map.get(str);
        return str3 != null ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("custom(", str3, ")") : str;
    }
}
