package com.android.server.companion.virtual;

import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.sensor.VirtualSensor;
import android.os.LocaleList;
import android.util.ArraySet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class VirtualDeviceManagerInternal {
    public abstract Set getAllPersistentDeviceIds();

    public abstract int getBaseVirtualDisplayFlags(IVirtualDevice iVirtualDevice);

    public abstract ArraySet getDeviceIdsForUid(int i);

    public abstract int getDeviceOwnerUid(int i);

    public abstract ArraySet getDisplayIdsForDevice(int i);

    public abstract String getPersistentIdForDevice(int i);

    public abstract LocaleList getPreferredLocaleListForUid(int i);

    public abstract VirtualSensor getVirtualSensor(int i, int i2);

    public abstract boolean isAppRunningOnAnyVirtualDevice(int i);

    public abstract boolean isInputDeviceOwnedByVirtualDevice(int i);

    public abstract void onAuthenticationPrompt(int i);

    public abstract void onVirtualDisplayRemoved(IVirtualDevice iVirtualDevice, int i);
}
