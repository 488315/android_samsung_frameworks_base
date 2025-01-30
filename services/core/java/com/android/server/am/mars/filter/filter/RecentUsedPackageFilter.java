package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.os.SystemClock;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import com.android.server.backup.BackupAgentTimeoutParameters;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class RecentUsedPackageFilter implements IFilter {
  public static String TAG = "MARs:" + RecentUsedPackageFilter.class.getSimpleName();
  public ArrayList RogueApp;
  public Context mContext;
  public long mUnUsedTime;

  public abstract class RecentUsedPackageFilterHolder {
    public static final RecentUsedPackageFilter INSTANCE = new RecentUsedPackageFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  public RecentUsedPackageFilter() {
    this.mContext = null;
    this.mUnUsedTime = 900000L;
    this.RogueApp = new ArrayList(Arrays.asList("com.codoon.gps", "com.traffic.panda"));
  }

  public static RecentUsedPackageFilter getInstance() {
    return RecentUsedPackageFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    if (i3 == 4) {
      this.mUnUsedTime = 60000L;
    } else if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()
        && this.RogueApp.contains(str)) {
      this.mUnUsedTime = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
    } else {
      this.mUnUsedTime = 900000L;
    }
    return !(((SystemClock.elapsedRealtime() - this.mUnUsedTime)
                    > MARsPolicyManager.getInstance().getLastUsedTime(str, i)
                ? 1
                : ((SystemClock.elapsedRealtime() - this.mUnUsedTime)
                        == MARsPolicyManager.getInstance().getLastUsedTime(str, i)
                    ? 0
                    : -1))
            > 0)
        ? 1
        : 0;
  }
}
