package com.android.systemui.statusbar.notification.headsup;

import android.os.Handler;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AvalancheController implements Dumpable {
    public final Handler bgHandler;
    public HeadsUpManagerImpl.HeadsUpEntry headsUpEntryShowing;
    public final HeadsUpManagerLogger headsUpManagerLogger;
    public final UiEventLogger uiEventLogger;
    public Function0 baseEntryMapStr = new AvalancheController$$ExternalSyntheticLambda0();
    public boolean enableAtRuntime = true;
    public String previousHunKey = "";
    public List headsUpEntryShowingRunnableList = new ArrayList();
    public final List nextList = new ArrayList();
    public final Map nextMap = new HashMap();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ThrottleEvent implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ ThrottleEvent[] $VALUES;
        public static final ThrottleEvent AVALANCHE_THROTTLING_HUN_DROPPED;
        public static final ThrottleEvent AVALANCHE_THROTTLING_HUN_REMOVED;
        public static final ThrottleEvent AVALANCHE_THROTTLING_HUN_SHOWN;
        private final int id;

        static {
            ThrottleEvent throttleEvent = new ThrottleEvent("AVALANCHE_THROTTLING_HUN_SHOWN", 0, 1821);
            AVALANCHE_THROTTLING_HUN_SHOWN = throttleEvent;
            ThrottleEvent throttleEvent2 = new ThrottleEvent("AVALANCHE_THROTTLING_HUN_DROPPED", 1, 1822);
            AVALANCHE_THROTTLING_HUN_DROPPED = throttleEvent2;
            ThrottleEvent throttleEvent3 = new ThrottleEvent("AVALANCHE_THROTTLING_HUN_REMOVED", 2, 1823);
            AVALANCHE_THROTTLING_HUN_REMOVED = throttleEvent3;
            ThrottleEvent[] throttleEventArr = {throttleEvent, throttleEvent2, throttleEvent3};
            $VALUES = throttleEventArr;
            EnumEntriesKt.enumEntries(throttleEventArr);
        }

        private ThrottleEvent(String str, int i, int i2) {
            this.id = i2;
        }

        public static ThrottleEvent valueOf(String str) {
            return (ThrottleEvent) Enum.valueOf(ThrottleEvent.class, str);
        }

        public static ThrottleEvent[] values() {
            return (ThrottleEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this.id;
        }
    }

    public AvalancheController(DumpManager dumpManager, UiEventLogger uiEventLogger, HeadsUpManagerLogger headsUpManagerLogger, Handler handler) {
        this.uiEventLogger = uiEventLogger;
        this.headsUpManagerLogger = headsUpManagerLogger;
        this.bgHandler = handler;
        new HashMap();
        dumpManager.registerNormalDumpable("AvalancheController", this);
    }

    public static String getKey(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry) {
        if (headsUpEntry == null) {
            return "HeadsUpEntry null";
        }
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        return notificationEntry == null ? "HeadsUpEntry.mEntry null" : notificationEntry.mKey;
    }

    public final void addToNext(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, Runnable runnable) {
        ((HashMap) this.nextMap).put(headsUpEntry, CollectionsKt__CollectionsKt.arrayListOf(runnable));
        ((ArrayList) this.nextList).add(headsUpEntry);
    }

    public final void delete(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, Runnable runnable, String str) {
        String m;
        boolean isEnabled = isEnabled();
        String key = getKey(headsUpEntry);
        HeadsUpManagerLogger headsUpManagerLogger = this.headsUpManagerLogger;
        if (!isEnabled) {
            runnable.run();
            headsUpManagerLogger.logAvalancheDelete(str, key, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("NOT ENABLED, run runnable. ", getStateStr()), false);
            return;
        }
        if (headsUpEntry == null) {
            runnable.run();
            headsUpManagerLogger.logAvalancheDelete(str, key, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Entry NULL, run runnable. ", getStateStr()), true);
            return;
        }
        if (((HashMap) this.nextMap).containsKey(headsUpEntry)) {
            if (((HashMap) this.nextMap).containsKey(headsUpEntry)) {
                ((HashMap) this.nextMap).remove(headsUpEntry);
            }
            if (((ArrayList) this.nextList).contains(headsUpEntry)) {
                ((ArrayList) this.nextList).remove(headsUpEntry);
            }
            this.uiEventLogger.log(ThrottleEvent.AVALANCHE_THROTTLING_HUN_REMOVED);
            m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("remove from next. ", getStateStr());
        } else if (isShowing(headsUpEntry)) {
            this.previousHunKey = getKey(this.headsUpEntryShowing);
            headsUpManagerLogger.logAvalancheStage("show next", "");
            this.headsUpEntryShowing = null;
            if (((ArrayList) this.nextList).isEmpty()) {
                headsUpManagerLogger.logAvalancheStage("no more", "");
                this.previousHunKey = "";
            } else {
                CollectionsKt__MutableCollectionsJVMKt.sort(this.nextList);
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = (HeadsUpManagerImpl.HeadsUpEntry) ((ArrayList) this.nextList).get(0);
                this.headsUpEntryShowing = headsUpEntry2;
                Object obj = ((HashMap) this.nextMap).get(headsUpEntry2);
                obj.getClass();
                this.headsUpEntryShowingRunnableList = (List) obj;
                ArrayList arrayList = (ArrayList) this.nextList;
                List subList = arrayList.subList(1, arrayList.size());
                this.bgHandler.post(new AvalancheController$logDroppedHunsInBackground$1(subList.size(), this));
                String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(subList, "\n ", null, null, new AvalancheController$$ExternalSyntheticLambda1(0, this), 30);
                LogLevel logLevel = LogLevel.VERBOSE;
                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(16);
                LogBuffer logBuffer = headsUpManagerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = joinToString$default;
                logBuffer.commit(obtain);
                ((ArrayList) this.nextList).clear();
                ((HashMap) this.nextMap).clear();
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry3 = this.headsUpEntryShowing;
                headsUpEntry3.getClass();
                showNow(headsUpEntry3, this.headsUpEntryShowingRunnableList);
            }
            runnable.run();
            m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("remove showing. ", getStateStr());
        } else {
            runnable.run();
            m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("run runnable for untracked HUN (was dropped or shown when AC was disabled). ", getStateStr());
        }
        headsUpManagerLogger.logAvalancheDelete(str, getKey(headsUpEntry), m, isEnabled());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "AvalancheController: ", getStateStr());
    }

    public final String getStateStr() {
        String sb;
        String key = getKey(this.headsUpEntryShowing);
        String str = this.previousHunKey;
        String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(this.nextList, "\n ", null, null, new AvalancheController$$ExternalSyntheticLambda1(1, this), 30);
        if (Intrinsics.areEqual(CollectionsKt___CollectionsKt.toSet(this.nextList), CollectionsKt___CollectionsKt.toSet(((HashMap) this.nextMap).keySet()))) {
            sb = BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(((ArrayList) this.nextList).size(), "next (", "):\n ", joinToString$default);
        } else {
            String joinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(((HashMap) this.nextMap).keySet(), "\n ", null, null, new AvalancheController$$ExternalSyntheticLambda1(2, this), 30);
            int size = ((ArrayList) this.nextList).size();
            int size2 = ((HashMap) this.nextMap).size();
            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(size, "next list (", "):\n ", joinToString$default, "\nnext map (");
            m.append(size2);
            m.append("):\n ");
            m.append(joinToString$default2);
            sb = m.toString();
        }
        Object invoke = this.baseEntryMapStr.invoke();
        StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("\n[AC state]\nshow: ", key, "\nprevious: ", str, "\n");
        m2.append(sb);
        m2.append("\n[HeadsUpManagerImpl.mHeadsUpEntryMap] ");
        m2.append(invoke);
        m2.append("\n");
        return m2.toString();
    }

    public final HeadsUpManagerImpl.HeadsUpEntry getWaitingEntry(String str) {
        if (isEnabled()) {
            for (HeadsUpManagerImpl.HeadsUpEntry headsUpEntry : ((HashMap) this.nextMap).keySet()) {
                NotificationEntry notificationEntry = headsUpEntry.mEntry;
                if (StringsKt__StringsJVMKt.equals(notificationEntry != null ? notificationEntry.mKey : null, str, false)) {
                    return headsUpEntry;
                }
            }
        }
        return null;
    }

    public final List getWaitingKeys() {
        if (!isEnabled()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((HashMap) this.nextMap).keySet().iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = ((HeadsUpManagerImpl.HeadsUpEntry) it.next()).mEntry;
            if (notificationEntry != null) {
                notificationEntry.getClass();
                arrayList.add(notificationEntry.mKey);
            }
        }
        return arrayList;
    }

    public final boolean isEnabled() {
        return this.enableAtRuntime;
    }

    public final boolean isShowing(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry) {
        NotificationEntry notificationEntry;
        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = this.headsUpEntryShowing;
        if (headsUpEntry2 == null) {
            return false;
        }
        NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
        String str = null;
        String str2 = notificationEntry2 != null ? notificationEntry2.mKey : null;
        if (headsUpEntry2 != null && (notificationEntry = headsUpEntry2.mEntry) != null) {
            str = notificationEntry.mKey;
        }
        return Intrinsics.areEqual(str2, str);
    }

    public final boolean isWaiting(String str) {
        if (isEnabled()) {
            Iterator it = ((HashMap) this.nextMap).keySet().iterator();
            while (it.hasNext()) {
                NotificationEntry notificationEntry = ((HeadsUpManagerImpl.HeadsUpEntry) it.next()).mEntry;
                if (StringsKt__StringsJVMKt.equals(notificationEntry != null ? notificationEntry.mKey : null, str, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void showNow(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, List list) {
        this.headsUpManagerLogger.logAvalancheStage("show", getKey(headsUpEntry));
        this.uiEventLogger.log(ThrottleEvent.AVALANCHE_THROTTLING_HUN_SHOWN);
        this.headsUpEntryShowing = headsUpEntry;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
    }

    public final void update(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, Runnable runnable, String str) {
        String str2;
        boolean isEnabled = isEnabled();
        String key = getKey(headsUpEntry);
        HeadsUpManagerLogger headsUpManagerLogger = this.headsUpManagerLogger;
        if (!isEnabled) {
            headsUpManagerLogger.logAvalancheUpdate(str, key, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("NOT ENABLED, run runnable. ", getStateStr()), isEnabled);
            runnable.run();
            return;
        }
        if (headsUpEntry == null) {
            headsUpManagerLogger.logAvalancheUpdate(str, key, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Entry NULL, stop. ", getStateStr()), isEnabled);
            return;
        }
        if (isShowing(headsUpEntry)) {
            runnable.run();
            str2 = "update showing";
        } else if (((HashMap) this.nextMap).containsKey(headsUpEntry)) {
            List list = (List) ((HashMap) this.nextMap).get(headsUpEntry);
            if (list != null) {
                list.add(runnable);
            }
            str2 = "update next";
        } else if (this.headsUpEntryShowing == null) {
            showNow(headsUpEntry, CollectionsKt__CollectionsKt.arrayListOf(runnable));
            str2 = "show now";
        } else {
            if (((HashMap) this.nextMap).containsKey(headsUpEntry)) {
                ((HashMap) this.nextMap).remove(headsUpEntry);
            }
            if (((ArrayList) this.nextList).contains(headsUpEntry)) {
                ((ArrayList) this.nextList).remove(headsUpEntry);
            }
            addToNext(headsUpEntry, runnable);
            if (((ArrayList) this.nextList).indexOf(headsUpEntry) == 0 && ((ArrayList) this.nextList).size() == 1) {
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = this.headsUpEntryShowing;
                headsUpEntry2.getClass();
                headsUpEntry2.updateEntry("shorten duration of previously-last HUN", false, false);
            }
            str2 = "add next";
        }
        headsUpManagerLogger.logAvalancheUpdate(str, key, ((Object) str2) + getStateStr(), isEnabled);
    }

    public static /* synthetic */ void getHeadsUpEntryShowing$annotations() {
    }

    public static /* synthetic */ void getNextMap$annotations() {
    }
}
