package android.app.admin;

public interface DevicePolicyManagerLiteInternal {
    void notifyUnsafeOperationStateChanged(
            DevicePolicySafetyChecker devicePolicySafetyChecker, int i, boolean z);
}
