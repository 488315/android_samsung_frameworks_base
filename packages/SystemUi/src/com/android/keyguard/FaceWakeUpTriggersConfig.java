package com.android.keyguard;

import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWakeUpTriggersConfig implements Dumpable {
    public final Set triggerFaceAuthOnWakeUpFrom;

    public FaceWakeUpTriggersConfig(Resources resources, GlobalSettings globalSettings, DumpManager dumpManager) {
        Set set = ArraysKt___ArraysKt.toSet(resources.getIntArray(R.array.config_face_auth_wake_up_triggers));
        if (Build.IS_DEBUGGABLE) {
            String stringForUser = ((GlobalSettingsImpl) globalSettings).getStringForUser(globalSettings.getUserId(), "face_wake_triggers");
            Set set2 = stringForUser != null ? (Set) StringsKt__StringsKt.split$default(stringForUser, new String[]{"|"}, 0, 6).stream().map(new Function() { // from class: com.android.keyguard.FaceWakeUpTriggersConfig$processStringArray$1$1
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
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("FaceWakeUpTriggers:");
        Iterator it = this.triggerFaceAuthOnWakeUpFrom.iterator();
        while (it.hasNext()) {
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("    ", PowerManager.wakeReasonToString(((Number) it.next()).intValue()), printWriter);
        }
    }
}
