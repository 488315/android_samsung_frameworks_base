package com.android.systemui.statusbar.policy;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AvalancheController implements Dumpable {
    public final Set debugDropSet;
    public final Map debugRunnableLabelMap;
    public final BaseHeadsUpManager.HeadsUpEntry headsUpEntryShowing;
    public final List nextList;
    public final Map nextMap;
    public final String previousHunKey = "";
    public final UiEventLogger uiEventLogger;

    public AvalancheController(DumpManager dumpManager, UiEventLogger uiEventLogger) {
        new ArrayList();
        this.nextList = new ArrayList();
        this.nextMap = new HashMap();
        new HashMap();
        this.debugDropSet = new HashSet();
        dumpManager.registerNormalDumpable("AvalancheController", this);
    }

    public static String getKey(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        if (headsUpEntry == null) {
            return "HeadsUpEntry null";
        }
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        return notificationEntry == null ? "HeadsUpEntry.mEntry null" : notificationEntry.mKey;
    }

    public static void update(Runnable runnable) {
        Flags.notificationAvalancheThrottleHun();
        runnable.run();
    }

    public final void addToNext(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, Runnable runnable) {
        ((HashMap) this.nextMap).put(headsUpEntry, CollectionsKt__CollectionsKt.arrayListOf(runnable));
        ((ArrayList) this.nextList).add(headsUpEntry);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String key = getKey(this.headsUpEntryShowing);
        String str = this.previousHunKey;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.nextList).iterator();
        while (it.hasNext()) {
            arrayList.add("[" + getKey((BaseHeadsUpManager.HeadsUpEntry) it.next()) + "]");
        }
        String join = String.join("\n", arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = ((HashMap) this.nextMap).keySet().iterator();
        while (it2.hasNext()) {
            arrayList2.add("[" + getKey((BaseHeadsUpManager.HeadsUpEntry) it2.next()) + "]");
        }
        String join2 = String.join("\n", arrayList2);
        ArrayList arrayList3 = new ArrayList();
        Iterator it3 = ((HashSet) this.debugDropSet).iterator();
        while (it3.hasNext()) {
            arrayList3.add("[" + getKey((BaseHeadsUpManager.HeadsUpEntry) it3.next()) + "]");
        }
        String join3 = String.join("\n", arrayList3);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SHOWING: [", key, "]\nPREVIOUS: [", str, "]\nNEXT LIST: ");
        ConstraintWidget$$ExternalSyntheticOutline0.m(m, join, "\nNEXT MAP: ", join2, "\nDROPPED: ");
        m.append(join3);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "AvalancheController: ", m.toString());
    }

    public static /* synthetic */ void getDebugDropSet$annotations() {
    }

    public static /* synthetic */ void getHeadsUpEntryShowing$annotations() {
    }

    public static /* synthetic */ void getNextMap$annotations() {
    }
}
