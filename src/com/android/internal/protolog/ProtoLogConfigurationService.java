package com.android.internal.protolog;

import java.io.PrintWriter;

/* loaded from: classes3.dex */
public interface ProtoLogConfigurationService extends IProtoLogConfigurationService {
    void disableProtoLogToLogcat(PrintWriter printWriter, String... strArr);

    void enableProtoLogToLogcat(PrintWriter printWriter, String... strArr);

    String[] getGroups();

    boolean isLoggingToLogcat(String str);
}
