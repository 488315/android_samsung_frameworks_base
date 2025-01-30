package com.android.server.am.mars.filter;

import android.content.Context;
import com.android.server.am.mars.filter.filter.AODClockFilter;
import com.android.server.am.mars.filter.filter.AccessibilityAppFilter;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.ActiveSensorFilter;
import com.android.server.am.mars.filter.filter.ActiveTrafficFilter;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.android.server.am.mars.filter.filter.AppCastFilter;
import com.android.server.am.mars.filter.filter.BackupServiceFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.android.server.am.mars.filter.filter.CameraInFgsFilter;
import com.android.server.am.mars.filter.filter.CarConnectedFilter;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.am.mars.filter.filter.DeviceAdminPackageFilter;
import com.android.server.am.mars.filter.filter.DisableForceStopFilter;
import com.android.server.am.mars.filter.filter.EdgeAppFilter;
import com.android.server.am.mars.filter.filter.ImportantRoleFilter;
import com.android.server.am.mars.filter.filter.JobSchedulerPackageFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import com.android.server.am.mars.filter.filter.LockScreenFilter;
import com.android.server.am.mars.filter.filter.NoAppIconFilter;
import com.android.server.am.mars.filter.filter.OngoingNotiPackageFilter;
import com.android.server.am.mars.filter.filter.PredownloadFilter;
import com.android.server.am.mars.filter.filter.ProtectedPackagesFilter;
import com.android.server.am.mars.filter.filter.QuickTilePackageFilter;
import com.android.server.am.mars.filter.filter.RecentUsedPackageFilter;
import com.android.server.am.mars.filter.filter.RunningLocationFilter;
import com.android.server.am.mars.filter.filter.SystemFilter;
import com.android.server.am.mars.filter.filter.TopPackageFilter;
import com.android.server.am.mars.filter.filter.VPNPackageFilter;
import com.android.server.am.mars.filter.filter.WallPaperFilter;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FilterFactory {
  public HashMap filterHashMap;
  public Context mContext;

  public abstract class FilterFactoryHolder {
    public static final FilterFactory INSTANCE = new FilterFactory();
  }

  public FilterFactory() {
    this.filterHashMap = new HashMap();
  }

  public static FilterFactory getInstance() {
    return FilterFactoryHolder.INSTANCE;
  }

  public void init(Context context) {
    setContext(context);
    this.filterHashMap.put(1, RecentUsedPackageFilter.getInstance());
    this.filterHashMap.put(2, LatestProtectedPackageFilter.getInstance());
    this.filterHashMap.put(3, OngoingNotiPackageFilter.getInstance());
    this.filterHashMap.put(4, WidgetPkgFilter.getInstance());
    this.filterHashMap.put(5, NoAppIconFilter.getInstance());
    this.filterHashMap.put(6, VPNPackageFilter.getInstance());
    this.filterHashMap.put(7, ActiveMusicRecordFilter.getInstance());
    this.filterHashMap.put(8, ActiveTrafficFilter.getInstance());
    this.filterHashMap.put(9, DeviceAdminPackageFilter.getInstance());
    this.filterHashMap.put(10, WallPaperFilter.getInstance());
    this.filterHashMap.put(11, DefaultAppFilter.getInstance());
    this.filterHashMap.put(12, TopPackageFilter.getInstance());
    this.filterHashMap.put(13, LockScreenFilter.getInstance());
    this.filterHashMap.put(14, SystemFilter.getInstance());
    this.filterHashMap.put(15, RunningLocationFilter.getInstance());
    this.filterHashMap.put(16, DisableForceStopFilter.getInstance());
    this.filterHashMap.put(17, EdgeAppFilter.getInstance());
    this.filterHashMap.put(18, JobSchedulerPackageFilter.getInstance());
    this.filterHashMap.put(19, AccessibilityAppFilter.getInstance());
    this.filterHashMap.put(20, AllowListFilter.getInstance());
    this.filterHashMap.put(21, QuickTilePackageFilter.getInstance());
    this.filterHashMap.put(22, ImportantRoleFilter.getInstance());
    this.filterHashMap.put(23, ActiveSensorFilter.getInstance());
    this.filterHashMap.put(24, AppCastFilter.getInstance());
    this.filterHashMap.put(25, AODClockFilter.getInstance());
    this.filterHashMap.put(26, BackupServiceFilter.getInstance());
    this.filterHashMap.put(27, BlueToothConnectedFilter.getInstance());
    this.filterHashMap.put(28, PredownloadFilter.getInstance());
    this.filterHashMap.put(29, CameraInFgsFilter.getInstance());
    this.filterHashMap.put(30, ProtectedPackagesFilter.getInstance());
    this.filterHashMap.put(31, CarConnectedFilter.getInstance());
    for (int i = 1; i < 32; i++) {
      ((IFilter) this.filterHashMap.get(Integer.valueOf(i))).init(this.mContext);
    }
  }

  public void setContext(Context context) {
    this.mContext = context;
  }

  public void deInit() {
    for (int i = 1; i < 32; i++) {
      ((IFilter) this.filterHashMap.get(Integer.valueOf(i))).deInit();
    }
    this.filterHashMap.clear();
  }

  public IFilter getFilter(int i) {
    return (IFilter) this.filterHashMap.get(Integer.valueOf(i));
  }
}
