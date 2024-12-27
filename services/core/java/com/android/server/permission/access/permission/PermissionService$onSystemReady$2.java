package com.android.server.permission.access.permission;

import com.android.server.permission.access.AccessCheckingService;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PermissionService$onSystemReady$2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object this$0;

    public /* synthetic */ PermissionService$onSystemReady$2(int i, Object obj) {
        this.$r8$classId = i;
        this.this$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = (String) obj;
                PermissionService permissionService = (PermissionService) this.this$0;
                AccessCheckingService accessCheckingService = permissionService.service;
                synchronized (accessCheckingService.stateLock) {
                    AccessState accessState = accessCheckingService.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    MutableAccessState mutable = accessState.toMutable();
                    MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                    permissionService.devicePolicy.getClass();
                    DevicePermissionPolicy.onDeviceIdRemoved(mutateStateScope, str);
                    accessCheckingService.persistence.write(mutable);
                    accessCheckingService.state = mutable;
                    IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                    int size = indexedMap.map.size();
                    for (int i = 0; i < size; i++) {
                        Object keyAt = indexedMap.map.keyAt(i);
                        IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i);
                        int size2 = indexedMap2.map.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            ((SchemePolicy) indexedMap2.map.valueAt(i2)).onStateMutated();
                        }
                    }
                }
                return;
            default:
                ((CompletableFuture) this.this$0).complete((byte[]) obj);
                return;
        }
    }
}
