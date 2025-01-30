package com.android.server.location.nsflp;

import android.app.IUidObserver;
import android.location.LocationConstants;
import android.os.Bundle;
import android.util.Log;
import com.android.server.location.injector.UserInfoHelper;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class NSPermissionHelper {
  public final NSConnectionHelper mNSConnectionHelper;
  public final UidObserver mUidObserver;

  public NSPermissionHelper(UserInfoHelper userInfoHelper, NSConnectionHelper nSConnectionHelper) {
    Log.i("NSPermissionHelper", "constructor");
    this.mNSConnectionHelper = nSConnectionHelper;
    UidObserver uidObserver = new UidObserver();
    this.mUidObserver = uidObserver;
    userInfoHelper.registerUidObserver(uidObserver);
  }

  public void updateUidForegroundChanged(int i, boolean z) {
    Bundle bundle = new Bundle();
    bundle.putInt("uid", i);
    bundle.putBoolean("foreground", z);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.FOREGROUND_CHANGED, bundle);
  }

  public void sendOpChanged(String str) {
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.OP_CHANGED, bundle);
  }

  public void sendPermissionsChange(int i) {
    Bundle bundle = new Bundle();
    bundle.putInt("uid", i);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.PERMISSIONS_CHANGED, bundle);
  }

  public void sendFreezeStateChanged(boolean z, int i) {
    Bundle bundle = new Bundle();
    bundle.putBoolean("enabled", z);
    bundle.putInt("uid", i);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.FREEZE_STATE_CHANGED, bundle);
  }

  public UidState getUidState(int i) {
    return this.mUidObserver.getUidState(i);
  }

  public class UidObserver extends IUidObserver.Stub {
    public HashMap mUidState;

    public void onUidActive(int i) {}

    public void onUidCachedChanged(int i, boolean z) {}

    public void onUidIdle(int i, boolean z) {}

    public void onUidProcAdjChanged(int i, int i2) {}

    public UidObserver() {
      this.mUidState = new HashMap();
    }

    public final UidState getUidState(int i) {
      return (UidState) this.mUidState.get(Integer.valueOf(i));
    }

    public void onUidGone(int i, boolean z) {
      this.mUidState.remove(Integer.valueOf(i));
    }

    public void onUidStateChanged(int i, int i2, long j, int i3) {
      UidState uidState = (UidState) this.mUidState.get(Integer.valueOf(i));
      if (uidState == null) {
        this.mUidState.put(Integer.valueOf(i), new UidState(i2, i3));
      } else {
        uidState.state = i2;
        uidState.capability = i3;
      }
    }
  }

  public class UidState {
    public int capability;
    public int state;

    public UidState(int i, int i2) {
      this.state = i;
      this.capability = i2;
    }

    public int getProcState() {
      return this.state;
    }

    public boolean hasLocationCapability() {
      return (this.capability & 1) == 1;
    }
  }
}
