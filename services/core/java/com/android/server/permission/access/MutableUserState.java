package com.android.server.permission.access;

import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedReferenceMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableReference;

public final class MutableUserState implements WritableState, Immutable {
    public final MutableReference appIdAppOpModesReference;
    public final MutableReference appIdDevicePermissionFlagsReference;
    public final MutableReference appIdPermissionFlagsReference;
    public String defaultPermissionGrantFingerprint;
    public final MutableReference packageAppOpModesReference;
    public final MutableReference packageVersionsReference;
    public int writeMode;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MutableUserState() {
        /*
            r7 = this;
            com.android.server.permission.access.immutable.MutableReference r1 = new com.android.server.permission.access.immutable.MutableReference
            com.android.server.permission.access.immutable.MutableIndexedMap r0 = new com.android.server.permission.access.immutable.MutableIndexedMap
            r0.<init>()
            r1.<init>(r0, r0)
            com.android.server.permission.access.immutable.MutableReference r2 = new com.android.server.permission.access.immutable.MutableReference
            com.android.server.permission.access.immutable.MutableIntReferenceMap r0 = new com.android.server.permission.access.immutable.MutableIntReferenceMap
            r0.<init>()
            r2.<init>(r0, r0)
            com.android.server.permission.access.immutable.MutableReference r3 = new com.android.server.permission.access.immutable.MutableReference
            com.android.server.permission.access.immutable.MutableIntReferenceMap r0 = new com.android.server.permission.access.immutable.MutableIntReferenceMap
            r0.<init>()
            r3.<init>(r0, r0)
            com.android.server.permission.access.immutable.MutableReference r4 = new com.android.server.permission.access.immutable.MutableReference
            com.android.server.permission.access.immutable.MutableIntReferenceMap r0 = new com.android.server.permission.access.immutable.MutableIntReferenceMap
            r0.<init>()
            r4.<init>(r0, r0)
            com.android.server.permission.access.immutable.MutableReference r5 = new com.android.server.permission.access.immutable.MutableReference
            com.android.server.permission.access.immutable.MutableIndexedReferenceMap r0 = new com.android.server.permission.access.immutable.MutableIndexedReferenceMap
            r0.<init>()
            r5.<init>(r0, r0)
            r6 = 0
            r0 = r7
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.MutableUserState.<init>():void");
    }

    public MutableUserState(
            MutableReference mutableReference,
            MutableReference mutableReference2,
            MutableReference mutableReference3,
            MutableReference mutableReference4,
            MutableReference mutableReference5,
            String str) {
        this.packageVersionsReference = mutableReference;
        this.appIdPermissionFlagsReference = mutableReference2;
        this.appIdDevicePermissionFlagsReference = mutableReference3;
        this.appIdAppOpModesReference = mutableReference4;
        this.packageAppOpModesReference = mutableReference5;
        this.defaultPermissionGrantFingerprint = str;
        this.writeMode = 0;
    }

    public final MutableIntReferenceMap getAppIdAppOpModes() {
        return (MutableIntReferenceMap) this.appIdAppOpModesReference.immutable;
    }

    public final MutableIntReferenceMap getAppIdDevicePermissionFlags() {
        return (MutableIntReferenceMap) this.appIdDevicePermissionFlagsReference.immutable;
    }

    public final MutableIntReferenceMap getAppIdPermissionFlags() {
        return (MutableIntReferenceMap) this.appIdPermissionFlagsReference.immutable;
    }

    public final MutableIndexedReferenceMap getPackageAppOpModes() {
        return (MutableIndexedReferenceMap) this.packageAppOpModesReference.immutable;
    }

    @Override // com.android.server.permission.access.WritableState
    public final int getWriteMode() {
        return this.writeMode;
    }

    public final MutableIndexedMap mutatePackageVersions() {
        return (MutableIndexedMap) this.packageVersionsReference.mutate();
    }

    public final void requestWriteMode(int i) {
        this.writeMode = Math.max(this.writeMode, i);
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableUserState(
                this.packageVersionsReference.toImmutable(),
                this.appIdPermissionFlagsReference.toImmutable(),
                this.appIdDevicePermissionFlagsReference.toImmutable(),
                this.appIdAppOpModesReference.toImmutable(),
                this.packageAppOpModesReference.toImmutable(),
                this.defaultPermissionGrantFingerprint);
    }
}
