package com.android.server;

import android.app.BroadcastOptions;
import android.os.Bundle;

/* loaded from: classes.dex */
public abstract class PendingIntentUtils {
  public static Bundle createDontSendToRestrictedAppsBundle(Bundle bundle) {
    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
    makeBasic.setDontSendToRestrictedApps(true);
    makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
    if (bundle == null) {
      return makeBasic.toBundle();
    }
    bundle.putAll(makeBasic.toBundle());
    return bundle;
  }
}
