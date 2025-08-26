package com.android.systemui.camera.data.repository;

import android.hardware.SensorPrivacyManager;
import android.os.UserHandle;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

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
        Integer numValueOf = Integer.valueOf(userHandle.getIdentifier());
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object objStateIn = linkedHashMap.get(numValueOf);
        if (objStateIn == null) {
            SensorPrivacyManager sensorPrivacyManager = this.privacyManager;
            objStateIn = FlowKt.stateIn(FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CameraSensorPrivacyRepositoryKt$isEnabled$2(sensorPrivacyManager, null), FlowConflatedKt.conflatedCallbackFlow(new CameraSensorPrivacyRepositoryKt$isEnabled$1(sensorPrivacyManager, userHandle, null)))), this.bgCoroutineContext), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
            linkedHashMap.put(numValueOf, objStateIn);
        }
        return (StateFlow) objStateIn;
    }
}
