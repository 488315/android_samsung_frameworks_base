package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyDrawableResource;
import android.app.admin.DevicePolicyStringResource;
import android.app.admin.ParcelableResource;

import com.android.internal.util.FunctionalUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda25
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda25(
            DevicePolicyManagerService devicePolicyManagerService, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = list;
    }

    private final void
            runOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda121() {
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        List list = this.f$1;
        DeviceManagementResourcesProvider deviceManagementResourcesProvider =
                devicePolicyManagerService.mDeviceManagementResourcesProvider;
        deviceManagementResourcesProvider.getClass();
        int i = 0;
        boolean z = false;
        while (true) {
            boolean z2 = true;
            if (i >= list.size()) {
                break;
            }
            String drawableId = ((DevicePolicyDrawableResource) list.get(i)).getDrawableId();
            String drawableStyle = ((DevicePolicyDrawableResource) list.get(i)).getDrawableStyle();
            String drawableSource =
                    ((DevicePolicyDrawableResource) list.get(i)).getDrawableSource();
            ParcelableResource resource =
                    ((DevicePolicyDrawableResource) list.get(i)).getResource();
            Objects.requireNonNull(drawableId, "drawableId must be provided.");
            Objects.requireNonNull(drawableStyle, "drawableStyle must be provided.");
            Objects.requireNonNull(drawableSource, "drawableSource must be provided.");
            Objects.requireNonNull(resource, "ParcelableResource must be provided.");
            if ("UNDEFINED".equals(drawableSource)) {
                synchronized (deviceManagementResourcesProvider.mLock) {
                    try {
                        if (!((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForStyle)
                                .containsKey(drawableId)) {
                            ((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForStyle)
                                    .put(drawableId, new HashMap());
                        }
                        if (resource.equals(
                                (ParcelableResource)
                                        ((Map)
                                                        ((HashMap)
                                                                        deviceManagementResourcesProvider
                                                                                .mUpdatedDrawablesForStyle)
                                                                .get(drawableId))
                                                .get(drawableStyle))) {
                            z2 = false;
                        } else {
                            ((Map)
                                            ((HashMap)
                                                            deviceManagementResourcesProvider
                                                                    .mUpdatedDrawablesForStyle)
                                                    .get(drawableId))
                                    .put(drawableStyle, resource);
                        }
                    } finally {
                    }
                }
                z |= z2;
                try {
                    i++;
                } finally {
                }
            } else {
                synchronized (deviceManagementResourcesProvider.mLock) {
                    if (!((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForSource)
                            .containsKey(drawableId)) {
                        ((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForSource)
                                .put(drawableId, new HashMap());
                    }
                    Map map =
                            (Map)
                                    ((HashMap)
                                                    deviceManagementResourcesProvider
                                                            .mUpdatedDrawablesForSource)
                                            .get(drawableId);
                    if (!map.containsKey(drawableSource)) {
                        ((Map)
                                        ((HashMap)
                                                        deviceManagementResourcesProvider
                                                                .mUpdatedDrawablesForSource)
                                                .get(drawableId))
                                .put(drawableSource, new HashMap());
                    }
                    if (resource.equals(
                            (ParcelableResource)
                                    ((Map) map.get(drawableSource)).get(drawableStyle))) {
                        z2 = false;
                    } else {
                        ((Map) map.get(drawableSource)).put(drawableStyle, resource);
                    }
                }
                z |= z2;
                i++;
            }
        }
        if (z) {
            synchronized (deviceManagementResourcesProvider.mLock) {
                deviceManagementResourcesProvider.write();
            }
            devicePolicyManagerService.sendResourceUpdatedBroadcast(
                    1,
                    (List)
                            list.stream()
                                    .map(
                                            new DevicePolicyManagerService$$ExternalSyntheticLambda15(
                                                    9))
                                    .collect(Collectors.toList()));
        }
    }

    public final void runOrThrow() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                List list = this.f$1;
                DeviceManagementResourcesProvider deviceManagementResourcesProvider =
                        devicePolicyManagerService.mDeviceManagementResourcesProvider;
                synchronized (deviceManagementResourcesProvider.mLock) {
                    int i = 0;
                    boolean z2 = false;
                    while (true) {
                        try {
                            boolean z3 = true;
                            if (i >= list.size()) {
                                if (z2) {
                                    deviceManagementResourcesProvider.write();
                                    devicePolicyManagerService.sendResourceUpdatedBroadcast(
                                            1, list);
                                    return;
                                }
                                return;
                            }
                            String str = (String) list.get(i);
                            if (((HashMap)
                                                            deviceManagementResourcesProvider
                                                                    .mUpdatedDrawablesForStyle)
                                                    .remove(str)
                                            == null
                                    && ((HashMap)
                                                            deviceManagementResourcesProvider
                                                                    .mUpdatedDrawablesForSource)
                                                    .remove(str)
                                            == null) {
                                z3 = false;
                            }
                            z2 |= z3;
                            i++;
                        } finally {
                        }
                    }
                }
                break;
            case 1:
                runOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda121();
                return;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                List list2 = this.f$1;
                DeviceManagementResourcesProvider deviceManagementResourcesProvider2 =
                        devicePolicyManagerService2.mDeviceManagementResourcesProvider;
                synchronized (deviceManagementResourcesProvider2.mLock) {
                    boolean z4 = false;
                    for (int i2 = 0; i2 < list2.size(); i2++) {
                        try {
                            z4 |=
                                    ((HashMap) deviceManagementResourcesProvider2.mUpdatedStrings)
                                                    .remove((String) list2.get(i2))
                                            != null;
                        } finally {
                        }
                    }
                    if (z4) {
                        deviceManagementResourcesProvider2.write();
                        devicePolicyManagerService2.sendResourceUpdatedBroadcast(2, list2);
                        return;
                    }
                    return;
                }
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                List list3 = this.f$1;
                DeviceManagementResourcesProvider deviceManagementResourcesProvider3 =
                        devicePolicyManagerService3.mDeviceManagementResourcesProvider;
                deviceManagementResourcesProvider3.getClass();
                boolean z5 = false;
                for (int i3 = 0; i3 < list3.size(); i3++) {
                    String stringId = ((DevicePolicyStringResource) list3.get(i3)).getStringId();
                    ParcelableResource resource =
                            ((DevicePolicyStringResource) list3.get(i3)).getResource();
                    Objects.requireNonNull(stringId, "stringId must be provided.");
                    Objects.requireNonNull(resource, "ParcelableResource must be provided.");
                    synchronized (deviceManagementResourcesProvider3.mLock) {
                        try {
                            if (resource.equals(
                                    (ParcelableResource)
                                            ((HashMap)
                                                            deviceManagementResourcesProvider3
                                                                    .mUpdatedStrings)
                                                    .get(stringId))) {
                                z = false;
                            } else {
                                ((HashMap) deviceManagementResourcesProvider3.mUpdatedStrings)
                                        .put(stringId, resource);
                                z = true;
                            }
                        } finally {
                        }
                    }
                    z5 |= z;
                }
                if (z5) {
                    synchronized (deviceManagementResourcesProvider3.mLock) {
                        deviceManagementResourcesProvider3.write();
                    }
                    devicePolicyManagerService3.sendResourceUpdatedBroadcast(
                            2,
                            (List)
                                    list3.stream()
                                            .map(
                                                    new DevicePolicyManagerService$$ExternalSyntheticLambda15(
                                                            10))
                                            .collect(Collectors.toList()));
                    return;
                }
                return;
        }
    }
}
