package com.android.server.permission.access;

public final class MutateStateScope extends GetStateScope {
    public final MutableAccessState newState;
    public final AccessState oldState;

    public MutateStateScope(AccessState accessState, MutableAccessState mutableAccessState) {
        super(mutableAccessState);
        this.oldState = accessState;
        this.newState = mutableAccessState;
    }
}
