package com.android.server.permission.access;

import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MutableAccessState extends AccessState {
    public static MutableSystemState mutateSystemState$default(
            MutableAccessState mutableAccessState) {
        MutableSystemState mutableSystemState =
                (MutableSystemState) mutableAccessState.systemStateReference.mutate();
        mutableSystemState.writeMode = Math.max(mutableSystemState.writeMode, 1);
        return mutableSystemState;
    }

    public static MutableUserState mutateUserStateAt$default(
            MutableAccessState mutableAccessState, int i) {
        MutableUserState mutableUserState =
                (MutableUserState)
                        ((MutableReference)
                                        ((MutableIntReferenceMap)
                                                        mutableAccessState.userStatesReference
                                                                .mutate())
                                                .array.valueAt(i))
                                .mutate();
        mutableUserState.requestWriteMode(1);
        return mutableUserState;
    }

    public final MutableExternalState mutateExternalState() {
        return (MutableExternalState) this.externalStateReference.mutate();
    }

    public final MutableUserState mutateUserState(int i, int i2) {
        MutableUserState mutableUserState =
                (MutableUserState)
                        ((MutableIntReferenceMap) this.userStatesReference.mutate()).mutate(i);
        if (mutableUserState == null) {
            return null;
        }
        mutableUserState.requestWriteMode(i2);
        return mutableUserState;
    }
}
