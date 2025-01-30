package com.android.server.cocktailbar.policy.state;

import android.content.Context;
import android.os.Debug;
import android.os.IBinder;
import android.util.Slog;
import com.android.server.location.gnss.hal.GnssNative;
import com.samsung.android.cocktailbar.CocktailBarStateInfo;

/* loaded from: classes.dex */
public class OverlayCocktailBarStatePolicy extends AbsCocktailBarStatePolicy {
  public static final boolean DEBUG = Debug.semIsProductDev();
  public static final String TAG = "OverlayCocktailBarStatePolicy";

  public OverlayCocktailBarStatePolicy(
      Context context,
      CocktailBarStatePolicy.OnCocktailBarStateListener onCocktailBarStateListener) {
    super(context, onCocktailBarStateListener);
  }

  @Override // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy
  public void handleUpdateVisibility(int i) {
    if (DEBUG) {
      Slog.i(
          TAG,
          "handleUpdateVisibility: mVisibility = "
              + this.mStateInfo.visibility
              + " visibility = "
              + i);
    }
    if (this.mStateInfo.visibility != i) {
      CocktailBarStateInfo cocktailBarStateInfo = new CocktailBarStateInfo(i);
      cocktailBarStateInfo.changeFlag |= 1;
      this.mListener.notifyCocktailBarState(cocktailBarStateInfo);
      this.mStateInfo.visibility = i;
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy
  public void handleUpdatePosition(int i) {
    if (DEBUG) {
      Slog.i(
          TAG,
          "handleUpdatePosition: mPosition = " + this.mStateInfo.position + " position = " + i);
    }
    if (this.mStateInfo.position != i) {
      CocktailBarStateInfo cocktailBarStateInfo =
          new CocktailBarStateInfo(this.mStateInfo.visibility);
      cocktailBarStateInfo.position = i;
      cocktailBarStateInfo.changeFlag |= 4;
      this.mListener.notifyCocktailBarState(cocktailBarStateInfo);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy
  public void handleUpdateActivate(boolean z) {
    if (this.mStateInfo.activate != z) {
      CocktailBarStateInfo cocktailBarStateInfo =
          new CocktailBarStateInfo(this.mStateInfo.visibility);
      cocktailBarStateInfo.activate = z;
      cocktailBarStateInfo.changeFlag |= 64;
      this.mListener.notifyCocktailBarState(cocktailBarStateInfo);
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy
  public void handleUpdateCocktailBarWindowType(int i, String str) {
    CocktailBarStateInfo cocktailBarStateInfo =
        new CocktailBarStateInfo(this.mStateInfo.visibility);
    cocktailBarStateInfo.windowType = i;
    cocktailBarStateInfo.changeFlag |= 128;
    this.mListener.notifyCocktailBarStateExceptCallingPackage(cocktailBarStateInfo, str);
  }

  @Override // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy
  public void handleNotifyCurrentStateToBinder(IBinder iBinder) {
    if (DEBUG) {
      Slog.i(TAG, "handleNotifyCurrentStateToBinder");
    }
    CocktailBarStateInfo clone = this.mStateInfo.clone();
    clone.lockState &= GnssNative.GNSS_AIDING_TYPE_ALL;
    clone.changeFlag = 77;
    this.mListener.notifyCocktailBarStateToBinder(iBinder, clone);
  }

  @Override // com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy,
            // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy
  public String dump() {
    return super.dump();
  }
}
