package com.android.systemui.authentication.domain.interactor;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class AuthenticationResult {
    public static final /* synthetic */ AuthenticationResult[] $VALUES;
    public static final AuthenticationResult FAILED;
    public static final AuthenticationResult SKIPPED;
    public static final AuthenticationResult SUCCEEDED;

    static {
        AuthenticationResult authenticationResult = new AuthenticationResult("SUCCEEDED", 0);
        SUCCEEDED = authenticationResult;
        AuthenticationResult authenticationResult2 = new AuthenticationResult("FAILED", 1);
        FAILED = authenticationResult2;
        AuthenticationResult authenticationResult3 = new AuthenticationResult("SKIPPED", 2);
        SKIPPED = authenticationResult3;
        AuthenticationResult[] authenticationResultArr = {authenticationResult, authenticationResult2, authenticationResult3};
        $VALUES = authenticationResultArr;
        EnumEntriesKt.enumEntries(authenticationResultArr);
    }

    private AuthenticationResult(String str, int i) {
    }

    public static AuthenticationResult valueOf(String str) {
        return (AuthenticationResult) Enum.valueOf(AuthenticationResult.class, str);
    }

    public static AuthenticationResult[] values() {
        return (AuthenticationResult[]) $VALUES.clone();
    }
}
