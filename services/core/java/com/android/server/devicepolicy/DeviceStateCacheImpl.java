package com.android.server.devicepolicy;

import android.app.admin.DeviceStateCache;
import android.util.IndentingPrintWriter;

import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceStateCacheImpl extends DeviceStateCache {
    public final Object mLock = new Object();
    public final AtomicInteger mDeviceOwnerType = new AtomicInteger(-1);
    public final Map mHasProfileOwner = new ConcurrentHashMap();
    public final Map mAffiliationWithDevice = new ConcurrentHashMap();
    public boolean mIsDeviceProvisioned = false;

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Device state cache:");
        indentingPrintWriter.increaseIndent();
        StringBuilder m =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Device provisioned: "),
                        this.mIsDeviceProvisioned,
                        indentingPrintWriter,
                        "Device Owner Type: ");
        m.append(this.mDeviceOwnerType.get());
        indentingPrintWriter.println(m.toString());
        indentingPrintWriter.println("Has PO:");
        for (Integer num : ((ConcurrentHashMap) this.mHasProfileOwner).keySet()) {
            indentingPrintWriter.println(
                    "User " + num + ": " + ((ConcurrentHashMap) this.mHasProfileOwner).get(num));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final boolean hasAffiliationWithDevice(int i) {
        return ((Boolean)
                        ((ConcurrentHashMap) this.mAffiliationWithDevice)
                                .getOrDefault(Integer.valueOf(i), Boolean.FALSE))
                .booleanValue();
    }

    public final boolean isDeviceProvisioned() {
        return this.mIsDeviceProvisioned;
    }

    public final boolean isUserOrganizationManaged(int i) {
        return ((Boolean)
                                ((ConcurrentHashMap) this.mHasProfileOwner)
                                        .getOrDefault(Integer.valueOf(i), Boolean.FALSE))
                        .booleanValue()
                || this.mDeviceOwnerType.get() == 0;
    }

    public final void setHasAffiliationWithDevice(int i, Boolean bool) {
        if (bool.booleanValue()) {
            ((ConcurrentHashMap) this.mAffiliationWithDevice).put(Integer.valueOf(i), Boolean.TRUE);
        } else {
            ((ConcurrentHashMap) this.mAffiliationWithDevice).remove(Integer.valueOf(i));
        }
    }

    public final void setHasProfileOwner(int i, boolean z) {
        if (z) {
            ((ConcurrentHashMap) this.mHasProfileOwner).put(Integer.valueOf(i), Boolean.TRUE);
        } else {
            ((ConcurrentHashMap) this.mHasProfileOwner).remove(Integer.valueOf(i));
        }
    }
}
