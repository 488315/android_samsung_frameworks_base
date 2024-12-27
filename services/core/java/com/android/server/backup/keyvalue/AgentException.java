package com.android.server.backup.keyvalue;

class AgentException extends BackupException {
    private final boolean mTransitory;

    public AgentException(boolean z) {
        this.mTransitory = z;
    }

    public AgentException(boolean z, Exception exc) {
        super(exc);
        this.mTransitory = z;
    }

    public static AgentException permanent() {
        return new AgentException(false);
    }

    public final boolean isTransitory() {
        return this.mTransitory;
    }
}
