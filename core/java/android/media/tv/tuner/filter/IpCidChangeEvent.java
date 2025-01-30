package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public final class IpCidChangeEvent extends FilterEvent {
  private final int mCid;

  private IpCidChangeEvent(int cid) {
    this.mCid = cid;
  }

  public int getIpCid() {
    return this.mCid;
  }
}
