package com.android.systemui.deviceentry.data.repository;

import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.text.StringsKt__StringsKt;

public final class FaceWakeUpTriggersConfigImpl implements Dumpable, FaceWakeUpTriggersConfig {
    public final Set defaultTriggerFaceAuthOnWakeUpFrom;
    public final Set triggerFaceAuthOnWakeUpFrom;
    public final Set wakeSleepReasonsToTriggerFaceAuth;

    public FaceWakeUpTriggersConfigImpl(Resources resources, GlobalSettings globalSettings, DumpManager dumpManager) {
        Set set = ArraysKt___ArraysKt.toSet(resources.getIntArray(R.array.config_face_auth_wake_up_triggers));
        this.defaultTriggerFaceAuthOnWakeUpFrom = set;
        if (Build.IS_DEBUGGABLE) {
            String string = globalSettings.getString("face_wake_triggers");
            Set set2 = string != null ? (Set) StringsKt__StringsKt.split$default(string, new String[]{"|"}, 0, 6).stream().map(new Function() { // from class: com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfigImpl$processStringArray$1$1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(Integer.parseInt((String) obj));
                }
            }).collect(Collectors.toSet()) : null;
            if (set2 != null) {
                set = set2;
            }
        }
        this.triggerFaceAuthOnWakeUpFrom = set;
        Set set3 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set3, 10));
        Iterator it = set3.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            WakeSleepReason.Companion.getClass();
            arrayList.add(WakeSleepReason.Companion.fromPowerManagerWakeReason(intValue));
        }
        this.wakeSleepReasonsToTriggerFaceAuth = CollectionsKt___CollectionsKt.toSet(arrayList);
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("FaceWakeUpTriggers:");
        Iterator it = this.triggerFaceAuthOnWakeUpFrom.iterator();
        while (it.hasNext()) {
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "    ", PowerManager.wakeReasonToString(((Number) it.next()).intValue()));
        }
    }
}
