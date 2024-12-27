package com.android.server.appfunctions;

import android.os.UserHandle;
import com.android.internal.infra.AndroidFuture;
import com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface CallerValidator {
    boolean isUserOrganizationManaged(UserHandle userHandle);

    String validateCallingPackage(String str);

    AndroidFuture verifyCallerCanExecuteAppFunction(int i, int i2, UserHandle userHandle, String str, String str2, String str3, AppFunctionAgentPolicyManager appFunctionAgentPolicyManager);

    UserHandle verifyTargetUserHandle(String str, UserHandle userHandle);
}
