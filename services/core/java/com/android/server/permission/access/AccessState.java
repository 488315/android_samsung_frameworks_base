package com.android.server.permission.access;

import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableReference;

public abstract class AccessState implements Immutable {
    public final MutableReference externalStateReference;
    public final MutableReference systemStateReference;
    public final MutableReference userStatesReference;

    public AccessState(
            MutableReference mutableReference,
            MutableReference mutableReference2,
            MutableReference mutableReference3) {
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
        return new MutableAccessState(
                this.externalStateReference.toImmutable(),
                this.systemStateReference.toImmutable(),
                this.userStatesReference.toImmutable());
    }
}
