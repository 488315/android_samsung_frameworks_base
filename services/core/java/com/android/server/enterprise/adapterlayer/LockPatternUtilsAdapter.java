package com.android.server.enterprise.adapterlayer;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;

/* loaded from: classes2.dex */
public class LockPatternUtilsAdapter {
  public Context mContext;
  public LockPatternUtils mLockPatternUtils;

  public LockPatternUtilsAdapter(Context context) {
    this.mContext = context;
    this.mLockPatternUtils = new LockPatternUtils(this.mContext);
  }

  public int getActivePasswordQuality(int i) {
    return this.mLockPatternUtils.getActivePasswordQuality(i);
  }
}
