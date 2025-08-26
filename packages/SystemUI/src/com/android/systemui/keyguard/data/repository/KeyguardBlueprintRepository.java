package com.android.systemui.keyguard.data.repository;

import android.os.Handler;
import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.util.ThreadAssert;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardBlueprintRepository {

    /* renamed from: assert, reason: not valid java name */
    public final ThreadAssert f48assert;
    public final StateFlowImpl blueprint;
    public final TreeMap blueprintIdMap;
    public final Handler handler;
    public final Logger logger;
    public final SharedFlowImpl refreshTransition;
    public IntraBlueprintTransition.Config targetTransitionConfig;

    public KeyguardBlueprintRepository(Set<KeyguardBlueprint> set, Handler handler, ThreadAssert threadAssert, LogBuffer logBuffer) {
        this.handler = handler;
        this.f48assert = threadAssert;
        this.logger = new Logger(logBuffer, "KeyguardBlueprintRepository");
        TreeMap treeMap = new TreeMap();
        Set<KeyguardBlueprint> set2 = set;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity < 16 ? 16 : iMapCapacity);
        for (Object obj : set2) {
            linkedHashMap.put(((KeyguardBlueprint) obj).getId(), obj);
        }
        treeMap.putAll(linkedHashMap);
        this.blueprintIdMap = treeMap;
        Object obj2 = treeMap.get("default");
        obj2.getClass();
        this.blueprint = StateFlowKt.MutableStateFlow(obj2);
        this.refreshTransition = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
    }

    public final boolean applyBlueprint(String str) {
        KeyguardBlueprint keyguardBlueprint = (KeyguardBlueprint) this.blueprintIdMap.get(str);
        if (keyguardBlueprint != null) {
            StateFlowImpl stateFlowImpl = this.blueprint;
            if (keyguardBlueprint.equals(stateFlowImpl.getValue())) {
                return true;
            }
            stateFlowImpl.updateState(null, keyguardBlueprint);
            return true;
        }
        Logger logger = this.logger;
        KeyguardBlueprintRepository$$ExternalSyntheticLambda0 keyguardBlueprintRepository$$ExternalSyntheticLambda0 = new KeyguardBlueprintRepository$$ExternalSyntheticLambda0(0);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, keyguardBlueprintRepository$$ExternalSyntheticLambda0, null);
        logMessageObtain.setStr1(str);
        logger.getBuffer().commit(logMessageObtain);
        return false;
    }

    public static /* synthetic */ void getTargetTransitionConfig$annotations() {
    }
}
