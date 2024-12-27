package com.android.sdksandbox.flags;

public interface FeatureFlags {
    boolean firstAndLastSdkSandboxUidPublic();

    boolean getEffectiveTargetSdkVersionApi();

    boolean sandboxActivitySdkBasedContext();

    boolean sandboxClientImportanceListener();

    boolean sdkSandboxDexVerifier();

    boolean sdkSandboxInstrumentationInfo();

    boolean sdkSandboxUidToAppUidApi();

    boolean selinuxInputSelector();

    boolean selinuxSdkSandboxAudit();
}
