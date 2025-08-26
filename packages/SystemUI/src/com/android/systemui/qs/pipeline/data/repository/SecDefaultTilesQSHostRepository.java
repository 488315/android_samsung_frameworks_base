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
import java.io.IOException;
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

/* loaded from: classes2.dex */
public final class SecDefaultTilesQSHostRepository implements DefaultTilesRepository {
    public static final boolean DEBUG;
    public final Resources resources;

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

    /* JADX WARN: Removed duplicated region for block: B:69:0x01f2 A[LOOP:6: B:68:0x01f0->B:69:0x01f2, LOOP_END] */
    @Override // com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List getDefaultTiles() throws Resources.NotFoundException, IOException, NumberFormatException {
        String string;
        Collection collectionTake;
        Collection collectionTake2;
        Collection collectionTake3;
        Resources resources = this.resources;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(resources.getString(R.string.sec_quick_settings_tiles_default).split(",")));
        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, null, 62);
        boolean z = Operator.QUICK_IS_VZW_BRANDING;
        int i = 0;
        if ("true".equals(SemSystemProperties.get("mdc.singlesku")) && "true".equals(SemSystemProperties.get("mdc.unified"))) {
            int phoneCount = TelephonyManager.getDefault().getPhoneCount();
            String str = SystemProperties.get("gsm.sim.state", "");
            KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(phoneCount, "getSimSlotLoaded() : simSlotCount = ", ", simStates = ", str, "SystemUI-Operator");
            if (str != null) {
                String[] strArrSplit = str.split(",");
                int i2 = 0;
                while (i2 < phoneCount && strArrSplit.length > i2) {
                    if (strArrSplit[i2].equalsIgnoreCase("LOADED")) {
                        break;
                    }
                    i2++;
                }
                i2 = 0;
                string = SemCarrierFeature.getInstance().getString(i2, "CarrierFeature_SystemUI_ConfigDefQuickSettingItem", (String) null, false);
            } else {
                i2 = 0;
                string = SemCarrierFeature.getInstance().getString(i2, "CarrierFeature_SystemUI_ConfigDefQuickSettingItem", (String) null, false);
            }
        } else {
            string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefQuickSettingItem", (String) null);
        }
        String string2 = this.resources.getString(R.string.quick_settings_auto_adding_tiles);
        String str2 = string == null ? strJoinToString$default : string;
        if (DEBUG) {
            Log.i("DefaultTilesRepository", "xmlTiles=" + strJoinToString$default);
            Log.i("DefaultTilesRepository", "cscTiles=" + string);
            Log.i("DefaultTilesRepository", "autoAddingTiles=" + string2);
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("defaultTiles=", str2, "DefaultTilesRepository");
        }
        ArrayList arrayList2 = new ArrayList();
        List listSplit = new Regex(",").split(str2);
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit, 10));
        Iterator it = listSplit.iterator();
        while (it.hasNext()) {
            arrayList3.add(StringsKt__StringsKt.trim((String) it.next()).toString());
        }
        if (arrayList3.isEmpty()) {
            collectionTake = EmptyList.INSTANCE;
        } else {
            ListIterator listIterator = arrayList3.listIterator(arrayList3.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    collectionTake = CollectionsKt___CollectionsKt.take(arrayList3, listIterator.nextIndex() + 1);
                    break;
                }
            }
            collectionTake = EmptyList.INSTANCE;
        }
        for (String str3 : (String[]) collectionTake.toArray(new String[0])) {
            str3.getClass();
            if ("Bluetooth".equals(str3) && arrayList2.contains("SoundMode")) {
                arrayList2.add(arrayList2.indexOf("SoundMode"), str3);
            } else {
                arrayList2.add(str3);
            }
            if (PluginLockShortcutTask.DO_NOT_DISTURB_TASK.equals(str3) && DeviceType.isTablet()) {
                List listSplit2 = new Regex(",").split(this.resources.getString(R.string.quick_settings_additional_default_tiles_tablet));
                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit2, 10));
                Iterator it2 = listSplit2.iterator();
                while (it2.hasNext()) {
                    arrayList4.add(StringsKt__StringsKt.trim((String) it2.next()).toString());
                }
                if (arrayList4.isEmpty()) {
                    collectionTake3 = EmptyList.INSTANCE;
                    while (i < r10) {
                    }
                } else {
                    ListIterator listIterator2 = arrayList4.listIterator(arrayList4.size());
                    while (listIterator2.hasPrevious()) {
                        if (((String) listIterator2.previous()).length() != 0) {
                            collectionTake3 = CollectionsKt___CollectionsKt.take(arrayList4, listIterator2.nextIndex() + 1);
                            break;
                        }
                    }
                    collectionTake3 = EmptyList.INSTANCE;
                    for (String str4 : (String[]) collectionTake3.toArray(new String[0])) {
                        str4.getClass();
                        arrayList2.add(str4);
                    }
                }
            }
        }
        List listSplit3 = new Regex(",").split(string2);
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit3, 10));
        Iterator it3 = listSplit3.iterator();
        while (it3.hasNext()) {
            arrayList5.add(StringsKt__StringsKt.trim((String) it3.next()).toString());
        }
        if (arrayList5.isEmpty()) {
            collectionTake2 = EmptyList.INSTANCE;
        } else {
            ListIterator listIterator3 = arrayList5.listIterator(arrayList5.size());
            while (listIterator3.hasPrevious()) {
                if (((String) listIterator3.previous()).length() != 0) {
                    collectionTake2 = CollectionsKt___CollectionsKt.take(arrayList5, listIterator3.nextIndex() + 1);
                    break;
                }
            }
            collectionTake2 = EmptyList.INSTANCE;
        }
        for (String str5 : (String[]) collectionTake2.toArray(new String[0])) {
            str5.getClass();
            int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str5, ":", 0, false, 6);
            String strSubstring = str5.substring(0, iIndexOf$default);
            int i3 = Integer.parseInt(str5.substring(iIndexOf$default + 1, str5.length()));
            if (!arrayList2.contains(strSubstring) && (!"CameraSharing".equals(strSubstring) || DeviceType.isTablet())) {
                if (i3 < 0 || i3 > arrayList2.size()) {
                    arrayList2.add(strSubstring);
                } else {
                    arrayList2.add(i3, strSubstring);
                }
                SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i3, "getSupportedAllTileList : tileName = ", strSubstring, ", tileIndex = ", "DefaultTilesRepository");
            }
        }
        ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        int size = arrayList2.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList2.get(i4);
            i4++;
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
        while (i < size2) {
            Object obj2 = arrayList6.get(i);
            i++;
            if (!Intrinsics.areEqual((TileSpec) obj2, TileSpec.Invalid.INSTANCE)) {
                arrayList7.add(obj2);
            }
        }
        Log.d("DefaultTilesRepository", "ret=" + arrayList7);
        return arrayList7;
    }
}
