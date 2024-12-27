package com.android.systemui.shade.data.repository;

import android.content.IntentFilter;
import android.os.UserHandle;
import android.safetycenter.SafetyCenterManager;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

public final class PrivacyChipRepositoryImpl implements PrivacyChipRepository {
    public final ReadonlyStateFlow isLocationIndicationEnabled;
    public final ReadonlyStateFlow isMicCameraIndicationEnabled;
    public final ReadonlyStateFlow isSafetyCenterEnabled;
    public final PrivacyConfig privacyConfig;
    public final PrivacyItemController privacyItemController;
    public final ReadonlyStateFlow privacyItems;
    public final SafetyCenterManager safetyCenterManager;

    public PrivacyChipRepositoryImpl(CoroutineScope coroutineScope, PrivacyConfig privacyConfig, PrivacyItemController privacyItemController, CoroutineDispatcher coroutineDispatcher, BroadcastDispatcher broadcastDispatcher, SafetyCenterManager safetyCenterManager) {
        this.privacyConfig = privacyConfig;
        this.privacyItemController = privacyItemController;
        this.safetyCenterManager = safetyCenterManager;
        IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED");
        Unit unit = Unit.INSTANCE;
        FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new PrivacyChipRepositoryImpl$isSafetyCenterEnabled$3(this, null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, m, UserHandle.SYSTEM, new Function2() { // from class: com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl$isSafetyCenterEnabled$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(PrivacyChipRepositoryImpl.this.safetyCenterManager.isSafetyCenterEnabled());
            }
        }, 12)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        PrivacyChipRepositoryImpl$privacyItems$1 privacyChipRepositoryImpl$privacyItems$1 = new PrivacyChipRepositoryImpl$privacyItems$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(privacyChipRepositoryImpl$privacyItems$1);
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.privacyItems = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, startedEagerly, EmptyList.INSTANCE);
        Flow conflatedCallbackFlow3 = FlowConflatedKt.conflatedCallbackFlow(new PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1(this, null));
        PrivacyConfig privacyConfig2 = privacyItemController.privacyConfig;
        this.isMicCameraIndicationEnabled = FlowKt.stateIn(conflatedCallbackFlow3, coroutineScope, startedEagerly, Boolean.valueOf(privacyConfig2.micCameraAvailable));
        this.isLocationIndicationEnabled = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new PrivacyChipRepositoryImpl$isLocationIndicationEnabled$1(this, null)), coroutineScope, startedEagerly, Boolean.valueOf(privacyConfig2.locationAvailable));
    }
}
