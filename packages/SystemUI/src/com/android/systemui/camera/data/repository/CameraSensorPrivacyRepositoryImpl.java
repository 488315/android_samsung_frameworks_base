package com.android.systemui.camera.data.repository;

import android.hardware.SensorPrivacyManager;
import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CameraSensorPrivacyRepositoryImpl implements CameraSensorPrivacyRepository {
    public final CoroutineContext bgCoroutineContext;
    public final SensorPrivacyManager privacyManager;
    public final CoroutineScope scope;
    public final Map userMap = new LinkedHashMap();

    public CameraSensorPrivacyRepositoryImpl(CoroutineContext coroutineContext, CoroutineScope coroutineScope, SensorPrivacyManager sensorPrivacyManager) {
        this.bgCoroutineContext = coroutineContext;
        this.scope = coroutineScope;
        this.privacyManager = sensorPrivacyManager;
    }

    public final StateFlow isEnabled(UserHandle userHandle) {
        Map map = this.userMap;
        Integer valueOf = Integer.valueOf(userHandle.getIdentifier());
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object obj = linkedHashMap.get(valueOf);
        if (obj == null) {
            SensorPrivacyManager sensorPrivacyManager = this.privacyManager;
            ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
            CameraSensorPrivacyRepositoryKt$isEnabled$1 cameraSensorPrivacyRepositoryKt$isEnabled$1 = new CameraSensorPrivacyRepositoryKt$isEnabled$1(sensorPrivacyManager, userHandle, null);
            conflatedCallbackFlow.getClass();
            obj = FlowKt.stateIn(FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CameraSensorPrivacyRepositoryKt$isEnabled$2(sensorPrivacyManager, null), FlowConflatedKt.conflatedCallbackFlow(cameraSensorPrivacyRepositoryKt$isEnabled$1))), this.bgCoroutineContext), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
            linkedHashMap.put(valueOf, obj);
        }
        return (StateFlow) obj;
    }
}
