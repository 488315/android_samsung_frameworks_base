package com.android.systemui.display.data.repository;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class DeviceStateRepositoryImpl implements DeviceStateRepository {
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final ReadonlyStateFlow state;

    public final class IdsPerDeviceState {
        public final DeviceStateRepository.DeviceState deviceState;
        public final Set ids;

        public IdsPerDeviceState(Set<Integer> set, DeviceStateRepository.DeviceState deviceState) {
            this.ids = set;
            this.deviceState = deviceState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IdsPerDeviceState)) {
                return false;
            }
            IdsPerDeviceState idsPerDeviceState = (IdsPerDeviceState) obj;
            return Intrinsics.areEqual(this.ids, idsPerDeviceState.ids) && this.deviceState == idsPerDeviceState.deviceState;
        }

        public final int hashCode() {
            return this.deviceState.hashCode() + (this.ids.hashCode() * 31);
        }

        public final String toString() {
            return "IdsPerDeviceState(ids=" + this.ids + ", deviceState=" + this.deviceState + ")";
        }
    }

    public DeviceStateRepositoryImpl(Context context, DeviceStateManager deviceStateManager, CoroutineScope coroutineScope, Executor executor) {
        this.context = context;
        this.deviceStateManager = deviceStateManager;
        this.state = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new DeviceStateRepositoryImpl$state$1(this, executor, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), DeviceStateRepository.DeviceState.UNKNOWN);
        List<Pair> listAsList = Arrays.asList(new Pair(Integer.valueOf(R.array.special_locale_names), DeviceStateRepository.DeviceState.FOLDED), new Pair(Integer.valueOf(R.array.vendor_policy_exempt_apps), DeviceStateRepository.DeviceState.HALF_FOLDED), new Pair(17236291, DeviceStateRepository.DeviceState.UNFOLDED), new Pair(17236299, DeviceStateRepository.DeviceState.REAR_DISPLAY), new Pair(Integer.valueOf(R.array.config_smallAreaDetectionAllowlist), DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAsList, 10));
        for (Pair pair : listAsList) {
            arrayList.add(new IdsPerDeviceState(ArraysKt___ArraysKt.toSet(this.context.getResources().getIntArray(((Number) pair.getFirst()).intValue())), (DeviceStateRepository.DeviceState) pair.getSecond()));
        }
    }
}
