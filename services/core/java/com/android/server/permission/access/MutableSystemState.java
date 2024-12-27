package com.android.server.permission.access;

import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MutableSystemState implements WritableState, Immutable {
    public final MutableReference permissionGroupsReference;
    public final MutableReference permissionTreesReference;
    public final MutableReference permissionsReference;
    public int writeMode = 0;

    public MutableSystemState(MutableReference mutableReference, MutableReference mutableReference2, MutableReference mutableReference3) {
        this.permissionGroupsReference = mutableReference;
        this.permissionTreesReference = mutableReference2;
        this.permissionsReference = mutableReference3;
    }

    public final IndexedMap getPermissionGroups() {
        return (IndexedMap) this.permissionGroupsReference.immutable;
    }

    public final IndexedMap getPermissionTrees() {
        return (IndexedMap) this.permissionTreesReference.immutable;
    }

    public final IndexedMap getPermissions() {
        return (IndexedMap) this.permissionsReference.immutable;
    }

    @Override // com.android.server.permission.access.WritableState
    public final int getWriteMode() {
        return this.writeMode;
    }

    public final MutableIndexedMap mutatePermissions() {
        return (MutableIndexedMap) this.permissionsReference.mutate();
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableSystemState(this.permissionGroupsReference.toImmutable(), this.permissionTreesReference.toImmutable(), this.permissionsReference.toImmutable());
    }
}
