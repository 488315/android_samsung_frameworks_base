package com.android.server.backup.keyvalue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
