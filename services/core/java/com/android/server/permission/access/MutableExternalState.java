package com.android.server.permission.access;

import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedListSet;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IntMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableIntSet;
import com.android.server.permission.access.immutable.MutableReference;
import com.android.server.pm.permission.PermissionAllowlist;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MutableExternalState implements Immutable {
    public final MutableReference appIdPackageNamesReference;
    public Map configPermissions;
    public Map disabledSystemPackageStates;
    public IndexedMap implicitToSourcePermissions;
    public boolean isLeanback;
    public boolean isSystemReady;
    public IntMap knownPackages;
    public Map packageStates;
    public PermissionAllowlist permissionAllowlist;
    public IndexedListSet privilegedPermissionAllowlistPackages;
    public final MutableReference userIdsReference;

    public MutableExternalState(
            MutableReference mutableReference,
            Map map,
            Map map2,
            MutableReference mutableReference2,
            IntMap intMap,
            boolean z,
            Map map3,
            IndexedListSet indexedListSet,
            PermissionAllowlist permissionAllowlist,
            IndexedMap indexedMap,
            boolean z2) {
        this.userIdsReference = mutableReference;
        this.appIdPackageNamesReference = mutableReference2;
        this.packageStates = map;
        this.disabledSystemPackageStates = map2;
        this.knownPackages = intMap;
        this.isLeanback = z;
        this.configPermissions = map3;
        this.privilegedPermissionAllowlistPackages = indexedListSet;
        this.permissionAllowlist = permissionAllowlist;
        this.implicitToSourcePermissions = indexedMap;
        this.isSystemReady = z2;
    }

    public final MutableIntReferenceMap getAppIdPackageNames() {
        return (MutableIntReferenceMap) this.appIdPackageNamesReference.immutable;
    }

    public final MutableIntSet getUserIds() {
        return (MutableIntSet) this.userIdsReference.immutable;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableExternalState(
                this.userIdsReference.toImmutable(),
                this.packageStates,
                this.disabledSystemPackageStates,
                this.appIdPackageNamesReference.toImmutable(),
                this.knownPackages,
                this.isLeanback,
                this.configPermissions,
                this.privilegedPermissionAllowlistPackages,
                this.permissionAllowlist,
                this.implicitToSourcePermissions,
                this.isSystemReady);
    }
}
