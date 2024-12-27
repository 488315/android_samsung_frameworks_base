package com.android.server.permission.access;

import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AccessState implements Immutable {
    public final MutableReference externalStateReference;
    public final MutableReference systemStateReference;
    public final MutableReference userStatesReference;

    public AccessState(MutableReference mutableReference, MutableReference mutableReference2, MutableReference mutableReference3) {
        this.externalStateReference = mutableReference;
        this.systemStateReference = mutableReference2;
        this.userStatesReference = mutableReference3;
    }

    public final MutableExternalState getExternalState() {
        return (MutableExternalState) this.externalStateReference.immutable;
    }

    public final MutableSystemState getSystemState() {
        return (MutableSystemState) this.systemStateReference.immutable;
    }

    public final MutableIntReferenceMap getUserStates() {
        return (MutableIntReferenceMap) this.userStatesReference.immutable;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final MutableAccessState toMutable() {
        return new MutableAccessState(this.externalStateReference.toImmutable(), this.systemStateReference.toImmutable(), this.userStatesReference.toImmutable());
    }
}
