package com.android.internal.protolog;

import com.android.internal.protolog.common.IProtoLogGroup;

/* loaded from: classes3.dex */
public class ProtoLogGroup implements IProtoLogGroup {
    private final boolean mEnabled;
    private boolean mLogToLogcat;
    private boolean mLogToProto;
    private final String mName;
    private final String mTag;

    public ProtoLogGroup(String str) {
        this(str, str);
    }

    public ProtoLogGroup(String str, String str2) {
        this(str, str2, true);
    }

    public ProtoLogGroup(String str, String str2, boolean z) {
        this.mName = str;
        this.mTag = str2;
        this.mEnabled = z;
        this.mLogToProto = z;
        this.mLogToLogcat = z;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    @Deprecated
    public boolean isLogToProto() {
        return this.mLogToProto;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isLogToLogcat() {
        return this.mLogToLogcat;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public String getTag() {
        return this.mTag;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    @Deprecated
    public void setLogToProto(boolean z) {
        this.mLogToProto = z;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public void setLogToLogcat(boolean z) {
        this.mLogToLogcat = z;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public String name() {
        return this.mName;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public int getId() {
        return this.mName.hashCode();
    }
}
