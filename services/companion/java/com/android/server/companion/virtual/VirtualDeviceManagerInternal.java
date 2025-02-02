package com.android.server.companion.virtual;

import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.sensor.VirtualSensor;
import android.os.LocaleList;
import android.util.ArraySet;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class VirtualDeviceManagerInternal {

  public interface AppsOnVirtualDeviceListener {
    void onAppsOnAnyVirtualDeviceChanged(Set set);
  }

  public interface VirtualDisplayListener {
    void onVirtualDisplayCreated(int i);

    void onVirtualDisplayRemoved(int i);
  }

  public abstract int getBaseVirtualDisplayFlags(IVirtualDevice iVirtualDevice);

  public abstract ArraySet getDeviceIdsForUid(int i);

  public abstract int getDeviceOwnerUid(int i);

  public abstract ArraySet getDisplayIdsForDevice(int i);

  public abstract LocaleList getPreferredLocaleListForUid(int i);

  public abstract VirtualSensor getVirtualSensor(int i, int i2);

  public abstract boolean isAppRunningOnAnyVirtualDevice(int i);

  public abstract boolean isDisplayOwnedByAnyVirtualDevice(int i);

  public abstract void onAppsOnVirtualDeviceChanged();

  public abstract void onAuthenticationPrompt(int i);

  public abstract void onVirtualDisplayCreated(int i);

  public abstract void onVirtualDisplayRemoved(IVirtualDevice iVirtualDevice, int i);

  public abstract void registerAppsOnVirtualDeviceListener(
      AppsOnVirtualDeviceListener appsOnVirtualDeviceListener);

  public abstract void registerVirtualDisplayListener(
      VirtualDisplayListener virtualDisplayListener);
}
