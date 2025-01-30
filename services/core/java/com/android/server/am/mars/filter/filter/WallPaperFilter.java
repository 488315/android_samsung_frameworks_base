package com.android.server.am.mars.filter.filter;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.wallpaper.Rune;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class WallPaperFilter implements IFilter {
  public final String TAG;
  public Context mContext;
  public ArrayMap mRunningWallPapers;
  public Integer userId;

  public abstract class WallPaperFilterHolder {
    public static final WallPaperFilter INSTANCE = new WallPaperFilter();
  }

  public WallPaperFilter() {
    this.TAG = "MARs:" + WallPaperFilter.class.getSimpleName();
    this.mRunningWallPapers = new ArrayMap();
  }

  public static WallPaperFilter getInstance() {
    return WallPaperFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    this.mContext = context;
    this.userId = Integer.valueOf(context.getUserId());
    initWallpaperPackage();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    this.mRunningWallPapers.clear();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    Context context = this.mContext;
    if (context == null || i != context.getUserId()) {
      return 0;
    }
    return ((i3 != 15 || FreecessController.getInstance().getScreenOnState())
            && isWallPaperPackage(str, i))
        ? 10
        : 0;
  }

  public final void initWallpaperPackage() {
    ArrayList arrayList = new ArrayList();
    try {
      arrayList.add(
          WallpaperManager.getInstance(this.mContext).getWallpaperInfo(5, this.userId.intValue()));
      arrayList.add(
          WallpaperManager.getInstance(this.mContext).getWallpaperInfo(6, this.userId.intValue()));
      if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
        arrayList.add(
            WallpaperManager.getInstance(this.mContext)
                .getWallpaperInfo(17, this.userId.intValue()));
        arrayList.add(
            WallpaperManager.getInstance(this.mContext)
                .getWallpaperInfo(18, this.userId.intValue()));
      }
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        WallpaperInfo wallpaperInfo = (WallpaperInfo) it.next();
        synchronized (this.mRunningWallPapers) {
          WallPaperPackages wallPaperPackages =
              (WallPaperPackages) this.mRunningWallPapers.get(this.userId);
          if (wallPaperPackages == null) {
            wallPaperPackages = new WallPaperPackages();
          }
          if (wallpaperInfo != null) {
            wallPaperPackages.add(wallpaperInfo.getPackageName());
            MARsPolicyManager.getInstance()
                .addFilterDebugInfoToHistory("FILTER 10 default", wallpaperInfo.getPackageName());
            this.mRunningWallPapers.put(this.userId, wallPaperPackages);
          }
        }
      }
    } catch (Exception e) {
      Slog.e(this.TAG, "getWallpaperPackage() exception " + e);
    }
  }

  public void onWallPaperPkgBinded(String str, Integer num) {
    synchronized (this.mRunningWallPapers) {
      WallPaperPackages wallPaperPackages = (WallPaperPackages) this.mRunningWallPapers.get(num);
      if (wallPaperPackages == null) {
        wallPaperPackages = new WallPaperPackages();
      }
      wallPaperPackages.add(str);
      MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 10", str);
      this.mRunningWallPapers.put(num, wallPaperPackages);
    }
  }

  public void onWallPaperPkgUnBinded(String str, Integer num) {
    synchronized (this.mRunningWallPapers) {
      WallPaperPackages wallPaperPackages = (WallPaperPackages) this.mRunningWallPapers.get(num);
      if (wallPaperPackages != null && wallPaperPackages.contains(str)) {
        wallPaperPackages.remove(str);
        MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 10 remove", str);
        this.mRunningWallPapers.put(num, wallPaperPackages);
      }
    }
  }

  public class WallPaperPackages {
    public ArrayMap mMap;

    public WallPaperPackages() {
      this.mMap = new ArrayMap();
    }

    public boolean contains(String str) {
      return this.mMap.get(str) != null;
    }

    public void add(String str) {
      Integer num = (Integer) this.mMap.get(str);
      if (num == null) {
        num = 0;
      }
      this.mMap.put(str, Integer.valueOf(num.intValue() + 1));
    }

    public void remove(String str) {
      if (((Integer) this.mMap.get(str)) == null) {
        return;
      }
      Integer valueOf = Integer.valueOf(r0.intValue() - 1);
      if (valueOf.intValue() <= 0) {
        this.mMap.remove(str);
      } else {
        this.mMap.put(str, valueOf);
      }
    }

    public ArrayList getPackages() {
      ArrayList arrayList = new ArrayList();
      for (int i = 0; i < this.mMap.size(); i++) {
        arrayList.add((String) this.mMap.keyAt(i));
      }
      return arrayList;
    }
  }

  public final boolean isWallPaperPackage(String str, int i) {
    synchronized (this.mRunningWallPapers) {
      WallPaperPackages wallPaperPackages =
          (WallPaperPackages) this.mRunningWallPapers.get(Integer.valueOf(i));
      return wallPaperPackages != null && wallPaperPackages.contains(str);
    }
  }

  public ArrayList getWallPaperPackages(int i) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mRunningWallPapers) {
      WallPaperPackages wallPaperPackages =
          (WallPaperPackages) this.mRunningWallPapers.get(Integer.valueOf(i));
      if (wallPaperPackages != null) {
        arrayList = wallPaperPackages.getPackages();
      }
    }
    return arrayList;
  }
}
