package com.android.server.am.mars.filter.filter;

import android.content.Context;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import java.com.android.server.am.mars.database.MARsListManager;
import java.util.Set;

/* loaded from: classes.dex */
public class RunningLocationFilter implements IFilter {
  public final Set mapAppPackages;

  public abstract class RunningLocationFilterHolder {
    public static final RunningLocationFilter INSTANCE = new RunningLocationFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {}

  public RunningLocationFilter() {
    this.mapAppPackages = MARsListManager.getInstance().getLocationAppPackages();
  }

  public static RunningLocationFilter getInstance() {
    return RunningLocationFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    boolean isForegroundServicePkg = MARsPolicyManager.getInstance().isForegroundServicePkg(i2);
    if (!FreecessController.getInstance().isInGPSAllowList(i2)) {
      return 0;
    }
    if (!isForegroundServicePkg
        || (MARsPolicyManager.getInstance().isChinaPolicyEnabled()
            && !this.mapAppPackages.contains(str))) {
      return (MARsPolicyManager.getInstance().isChinaPolicyEnabled()
              && MARsPolicyManager.getInstance().isAutoRunOn(str, i))
          ? 15
          : 0;
    }
    return 15;
  }
}
