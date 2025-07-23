package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecDefaultTilesQSHostRepository implements DefaultTilesRepository {
    public static final boolean DEBUG;
    public final Resources resources;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DEBUG = !DeviceType.isShipBuild();
    }

    public SecDefaultTilesQSHostRepository(Resources resources) {
        this.resources = resources;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository
    public final List getDefaultTiles() {
        String string;
        Collection collection;
        Collection collection2;
        Collection collection3;
        int i;
        Resources resources = this.resources;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(resources.getString(R.string.sec_quick_settings_tiles_default).split(",")));
        String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, null, 62);
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        int i2 = 0;
        if ("true".equals(SemSystemProperties.get("mdc.singlesku")) && "true".equals(SemSystemProperties.get("mdc.unified"))) {
            int phoneCount = TelephonyManager.getDefault().getPhoneCount();
            String str = SystemProperties.get("gsm.sim.state", "");
            KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(phoneCount, "getSimSlotLoaded() : simSlotCount = ", ", simStates = ", str, "SystemUI-Operator");
            if (str != null) {
                String[] split = str.split(",");
                i = 0;
                while (i < phoneCount && split.length > i) {
                    if (split[i].equalsIgnoreCase("LOADED")) {
                        break;
                    }
                    i++;
                }
            }
            i = 0;
            string = SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigDefQuickSettingItem", (String) null, false);
        } else {
            string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefQuickSettingItem", (String) null);
        }
        String string2 = this.resources.getString(R.string.quick_settings_auto_adding_tiles);
        String str2 = string == null ? joinToString$default : string;
        if (DEBUG) {
            Log.i("DefaultTilesRepository", "xmlTiles=" + joinToString$default);
            Log.i("DefaultTilesRepository", "cscTiles=" + string);
            Log.i("DefaultTilesRepository", "autoAddingTiles=" + string2);
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("defaultTiles=", str2, "DefaultTilesRepository");
        }
        ArrayList arrayList2 = new ArrayList();
        List split2 = new Regex(",").split(str2);
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split2, 10));
        Iterator it = split2.iterator();
        while (it.hasNext()) {
            arrayList3.add(StringsKt__StringsKt.trim((String) it.next()).toString());
        }
        if (!arrayList3.isEmpty()) {
            ListIterator listIterator = arrayList3.listIterator(arrayList3.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    collection = CollectionsKt___CollectionsKt.take(arrayList3, listIterator.nextIndex() + 1);
                    break;
                }
            }
        }
        collection = EmptyList.INSTANCE;
        for (String str3 : (String[]) collection.toArray(new String[0])) {
            str3.getClass();
            if ("Bluetooth".equals(str3) && arrayList2.contains("SoundMode")) {
                arrayList2.add(arrayList2.indexOf("SoundMode"), str3);
            } else {
                arrayList2.add(str3);
            }
            if (PluginLockShortcutTask.DO_NOT_DISTURB_TASK.equals(str3) && DeviceType.isTablet()) {
                List split3 = new Regex(",").split(this.resources.getString(R.string.quick_settings_additional_default_tiles_tablet));
                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split3, 10));
                Iterator it2 = split3.iterator();
                while (it2.hasNext()) {
                    arrayList4.add(StringsKt__StringsKt.trim((String) it2.next()).toString());
                }
                if (!arrayList4.isEmpty()) {
                    ListIterator listIterator2 = arrayList4.listIterator(arrayList4.size());
                    while (listIterator2.hasPrevious()) {
                        if (((String) listIterator2.previous()).length() != 0) {
                            collection3 = CollectionsKt___CollectionsKt.take(arrayList4, listIterator2.nextIndex() + 1);
                            break;
                        }
                    }
                }
                collection3 = EmptyList.INSTANCE;
                for (String str4 : (String[]) collection3.toArray(new String[0])) {
                    str4.getClass();
                    arrayList2.add(str4);
                }
            }
        }
        List split4 = new Regex(",").split(string2);
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split4, 10));
        Iterator it3 = split4.iterator();
        while (it3.hasNext()) {
            arrayList5.add(StringsKt__StringsKt.trim((String) it3.next()).toString());
        }
        if (!arrayList5.isEmpty()) {
            ListIterator listIterator3 = arrayList5.listIterator(arrayList5.size());
            while (listIterator3.hasPrevious()) {
                if (((String) listIterator3.previous()).length() != 0) {
                    collection2 = CollectionsKt___CollectionsKt.take(arrayList5, listIterator3.nextIndex() + 1);
                    break;
                }
            }
        }
        collection2 = EmptyList.INSTANCE;
        for (String str5 : (String[]) collection2.toArray(new String[0])) {
            str5.getClass();
            int indexOf$default = StringsKt__StringsKt.indexOf$default(str5, ":", 0, false, 6);
            String substring = str5.substring(0, indexOf$default);
            int parseInt = Integer.parseInt(str5.substring(indexOf$default + 1, str5.length()));
            if (!arrayList2.contains(substring) && (!"CameraSharing".equals(substring) || DeviceType.isTablet())) {
                if (parseInt < 0 || parseInt > arrayList2.size()) {
                    arrayList2.add(substring);
                } else {
                    arrayList2.add(parseInt, substring);
                }
                SecNotificationBlockManager$$ExternalSyntheticOutline0.m(parseInt, "getSupportedAllTileList : tileName = ", substring, ", tileIndex = ", "DefaultTilesRepository");
            }
        }
        ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        int size = arrayList2.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList2.get(i3);
            i3++;
            TileSpec.Companion companion = TileSpec.Companion;
            TileNameConverter tileNameConverter = TileNameConverter.INSTANCE;
            Resources resources2 = this.resources;
            tileNameConverter.getClass();
            String tileSpec = TileNameConverter.toTileSpec(resources2, (String) obj);
            companion.getClass();
            arrayList6.add(TileSpec.Companion.create(tileSpec));
        }
        ArrayList arrayList7 = new ArrayList();
        int size2 = arrayList6.size();
        while (i2 < size2) {
            Object obj2 = arrayList6.get(i2);
            i2++;
            if (!Intrinsics.areEqual((TileSpec) obj2, TileSpec.Invalid.INSTANCE)) {
                arrayList7.add(obj2);
            }
        }
        Log.d("DefaultTilesRepository", "ret=" + arrayList7);
        return arrayList7;
    }
}
