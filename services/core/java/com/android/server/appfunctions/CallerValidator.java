package com.android.server.appfunctions;

import android.os.UserHandle;

import com.android.internal.infra.AndroidFuture;
import com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager;

public interface CallerValidator {
    boolean isUserOrganizationManaged(UserHandle userHandle);

    String validateCallingPackage(String str);

    AndroidFuture verifyCallerCanExecuteAppFunction(
            int i,
            int i2,
            UserHandle userHandle,
            String str,
            String str2,
            String str3,
            AppFunctionAgentPolicyManager appFunctionAgentPolicyManager);

    UserHandle verifyTargetUserHandle(String str, UserHandle userHandle);
}
