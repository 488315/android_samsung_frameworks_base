package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.util.Slog;
import android.util.SparseArray;

import java.util.List;
import java.util.stream.Collectors;

public final class PolicyVersionUpgrader {
    public final PolicyPathProvider mPathProvider;
    public final PolicyUpgraderDataProvider mProvider;

    public PolicyVersionUpgrader(
            PolicyUpgraderDataProvider policyUpgraderDataProvider,
            PolicyPathProvider policyPathProvider) {
        this.mProvider = policyUpgraderDataProvider;
        this.mPathProvider = policyPathProvider;
    }

    public final void saveSuspendedPackages(
            SparseArray sparseArray, int i, ComponentName componentName) {
        DevicePolicyData devicePolicyData = (DevicePolicyData) sparseArray.get(i);
        if (devicePolicyData == null) {
            Slog.e(
                    "DevicePolicyManager",
                    "No policy data for owner user, cannot migrate suspended packages");
            return;
        }
        ActiveAdmin activeAdmin = (ActiveAdmin) devicePolicyData.mAdminMap.get(componentName);
        if (activeAdmin == null) {
            Slog.e("DevicePolicyManager", "No admin for owner, cannot migrate suspended packages");
            return;
        }
        DevicePolicyManagerService devicePolicyManagerService = DevicePolicyManagerService.this;
        devicePolicyManagerService.mInjector.getClass();
        List list =
                (List)
                        devicePolicyManagerService
                                .mInjector
                                .getPackageManager(i)
                                .getInstalledPackages(PackageManager.PackageInfoFlags.of(786432L))
                                .stream()
                                .map(new DevicePolicyManagerService$$ExternalSyntheticLambda15(13))
                                .filter(
                                        new DevicePolicyManagerService$$ExternalSyntheticLambda179(
                                                i,
                                                2,
                                                DevicePolicyManagerService.Injector
                                                        .getPackageManagerInternal()))
                                .collect(Collectors.toList());
        activeAdmin.suspendedPackages = list;
        Slog.i(
                "DevicePolicyManager",
                String.format(
                        "Saved %d packages suspended by %s in user %d",
                        Integer.valueOf(list.size()), componentName, Integer.valueOf(i)));
    }
}
