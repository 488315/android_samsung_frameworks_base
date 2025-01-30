package com.android.server.cocktailbar.watcher;

import android.app.ActivityManager;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes.dex */
public class CocktailBarUsageStateWatcher {
  public static final String TAG = "CocktailBarUsageStateWatcher";
  public ActivityManager mActivityManager;
  public int mCategoryFilter;
  public Context mContext;
  public OnCocktailBarWatcherListener mListener;
  public String mMetaDataHideEdgeService;
  public HashSet mPackageHideEdgeServiceList;
  public UsageStatsManager mUsageStatsManager;
  public IUsageStatsWatcher.Stub mUsageStatsWatcher;
  public String mCurrentPackageName = null;
  public int mLevel = 0;
  public Object mLock = new Object();
  public ArrayList mComponentsToHideEdge = new ArrayList();

  public interface OnCocktailBarWatcherListener {
    void onChangeVisibleEdgeService(boolean z);

    void onNotePauseComponent(ComponentName componentName);

    void onNoteResumeComponent(ComponentName componentName);
  }

  public CocktailBarUsageStateWatcher(
      Context context, OnCocktailBarWatcherListener onCocktailBarWatcherListener) {
    this.mPackageHideEdgeServiceList = new HashSet();
    this.mContext = context;
    this.mListener = onCocktailBarWatcherListener;
    this.mActivityManager = (ActivityManager) context.getSystemService("activity");
    this.mUsageStatsManager =
        (UsageStatsManager) this.mContext.getSystemService(UsageStatsManager.class);
    this.mPackageHideEdgeServiceList =
        CocktailBarConfig.getInstance(context).getPackageHideEdgeServiceList();
    this.mMetaDataHideEdgeService =
        CocktailBarConfig.getInstance(context).getMetaDataHideEdgeService();
    this.mCategoryFilter =
        CocktailProviderInfo.getCategoryIds(
            CocktailBarConfig.getInstance(context).getCategoryFilter());
    HashSet hashSet = this.mPackageHideEdgeServiceList;
    if (hashSet != null && hashSet.size() > 0) {
      this.mLevel |= 1;
    }
    String str = this.mMetaDataHideEdgeService;
    if (str != null && str.length() > 0) {
      this.mLevel |= 2;
    }
    if (this.mLevel > 0) {
      registerUsageStatsWatcher();
    }
  }

  public final void registerUsageStatsWatcher() {
    if (this.mUsageStatsWatcher == null) {
      IUsageStatsWatcher iUsageStatsWatcher =
          new IUsageStatsWatcher
              .Stub() { // from class:
                        // com.android.server.cocktailbar.watcher.CocktailBarUsageStateWatcher.1
            public HashSet mHideEdgeServiceComponentCache = new HashSet();
            public boolean mVisible = true;

            public void noteResumeComponent(
                ComponentName componentName, Intent intent, int i, int i2) {
              boolean z;
              List<ActivityManager.RecentTaskInfo> recentTasks;
              ActivityInfo activityInfo;
              synchronized (CocktailBarUsageStateWatcher.this.mLock) {
                if (componentName == null) {
                  Slog.e(CocktailBarUsageStateWatcher.TAG, "resumeComponentName is null");
                  return;
                }
                String packageName = componentName.getPackageName();
                if (packageName == null) {
                  Slog.e(CocktailBarUsageStateWatcher.TAG, "resumePackageName is null");
                  return;
                }
                String flattenToShortString = componentName.flattenToShortString();
                CocktailBarUsageStateWatcher.this.mListener.onNoteResumeComponent(componentName);
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.addCategory("android.intent.category.HOME");
                PackageManager packageManager =
                    CocktailBarUsageStateWatcher.this.mContext.getPackageManager();
                ResolveInfo resolveActivity = packageManager.resolveActivity(intent2, 65536);
                String str =
                    (resolveActivity == null
                            || (activityInfo = resolveActivity.activityInfo) == null)
                        ? null
                        : activityInfo.packageName;
                boolean z2 = true;
                if ((CocktailBarUsageStateWatcher.this.mLevel & 1) != 0) {
                  if (CocktailBarUsageStateWatcher.this.mPackageHideEdgeServiceList.contains(
                      packageName)) {
                    String str2 = flattenToShortString + XmlUtils.STRING_ARRAY_SEPARATOR + i;
                    if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str2)) {
                      Slog.d(CocktailBarUsageStateWatcher.TAG, "noteResumeComponent: " + str2);
                      CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str2);
                    }
                    z = false;
                  } else {
                    z = true;
                  }
                  if (z
                      && !packageName.equals(str)
                      && (recentTasks =
                              CocktailBarUsageStateWatcher.this.mActivityManager.getRecentTasks(
                                  1, 1))
                          != null
                      && recentTasks.size() > 0) {
                    if (CocktailBarUsageStateWatcher.this.mPackageHideEdgeServiceList.contains(
                        recentTasks.get(0).baseIntent.getComponent().getPackageName())) {
                      String str3 = flattenToShortString + XmlUtils.STRING_ARRAY_SEPARATOR + i;
                      if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str3)) {
                        Slog.d(CocktailBarUsageStateWatcher.TAG, "noteResumeComponent: " + str3);
                        CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str3);
                      }
                      z2 = false;
                    }
                  }
                  z2 = z;
                }
                if (z2 && (CocktailBarUsageStateWatcher.this.mLevel & 2) != 0) {
                  if (this.mHideEdgeServiceComponentCache.contains(componentName.getClassName())) {
                    String str4 = flattenToShortString + XmlUtils.STRING_ARRAY_SEPARATOR + i;
                    if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str4)) {
                      Slog.d(CocktailBarUsageStateWatcher.TAG, "noteResumeComponent: " + str4);
                      CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str4);
                    }
                  } else {
                    try {
                      Bundle bundle = packageManager.getActivityInfo(componentName, 128).metaData;
                      if (bundle != null
                          ? bundle.getBoolean(
                              CocktailBarUsageStateWatcher.this.mMetaDataHideEdgeService, false)
                          : false) {
                        this.mHideEdgeServiceComponentCache.add(componentName.getClassName());
                        String str5 = flattenToShortString + XmlUtils.STRING_ARRAY_SEPARATOR + i;
                        if (!CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(
                            str5)) {
                          Slog.d(CocktailBarUsageStateWatcher.TAG, "noteResumeComponent: " + str5);
                          CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.add(str5);
                        }
                      }
                    } catch (PackageManager.NameNotFoundException e) {
                      Slog.d(
                          CocktailBarUsageStateWatcher.TAG,
                          "noteResumeComponent: getActivityInfo not found. " + componentName);
                      e.printStackTrace();
                    }
                  }
                  z2 = false;
                }
                if (this.mVisible
                    && !z2
                    && !CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.isEmpty()) {
                  CocktailBarUsageStateWatcher.this.mListener.onChangeVisibleEdgeService(false);
                  this.mVisible = false;
                }
              }
            }

            public void notePauseComponent(
                ComponentName componentName, Intent intent, int i, int i2) {
              synchronized (CocktailBarUsageStateWatcher.this.mLock) {
                if (componentName == null) {
                  Slog.e(CocktailBarUsageStateWatcher.TAG, "pauseComponentName is null");
                  return;
                }
                String flattenToShortString = componentName.flattenToShortString();
                if (componentName.getPackageName() == null) {
                  Slog.e(CocktailBarUsageStateWatcher.TAG, "pausePackageName is null");
                  return;
                }
                CocktailBarUsageStateWatcher.this.mListener.onNotePauseComponent(componentName);
                String str = flattenToShortString + XmlUtils.STRING_ARRAY_SEPARATOR + i;
                if (CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str)) {
                  Slog.d(CocktailBarUsageStateWatcher.TAG, "notePauseComponent: " + str);
                  CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.remove(str);
                }
                if (!this.mVisible
                    && CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.isEmpty()) {
                  CocktailBarUsageStateWatcher.this.mListener.onChangeVisibleEdgeService(true);
                  this.mVisible = true;
                }
              }
            }

            public void noteStopComponent(
                ComponentName componentName, Intent intent, int i, int i2) {
              synchronized (CocktailBarUsageStateWatcher.this.mLock) {
                if (componentName == null) {
                  Slog.e(CocktailBarUsageStateWatcher.TAG, "stopComponentName is null");
                  return;
                }
                String flattenToShortString = componentName.flattenToShortString();
                if (componentName.getPackageName() == null) {
                  Slog.e(CocktailBarUsageStateWatcher.TAG, "stopPackageName is null");
                  return;
                }
                String str = flattenToShortString + XmlUtils.STRING_ARRAY_SEPARATOR + i;
                if (CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.contains(str)) {
                  Slog.d(CocktailBarUsageStateWatcher.TAG, "noteStopComponent: " + str);
                  CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.remove(str);
                }
                if (!this.mVisible
                    && CocktailBarUsageStateWatcher.this.mComponentsToHideEdge.isEmpty()) {
                  CocktailBarUsageStateWatcher.this.mListener.onChangeVisibleEdgeService(true);
                  this.mVisible = true;
                }
              }
            }
          };
      this.mUsageStatsWatcher = iUsageStatsWatcher;
      try {
        UsageStatsManager usageStatsManager = this.mUsageStatsManager;
        if (usageStatsManager != null) {
          usageStatsManager.registerUsageStatsWatcher(iUsageStatsWatcher);
        }
      } catch (Exception unused) {
      }
    }
  }

  public String dump() {
    StringBuffer stringBuffer = new StringBuffer("[UsageStateWatcher]: ");
    synchronized (this.mLock) {
      ArrayList arrayList = this.mComponentsToHideEdge;
      if (arrayList != null) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
          stringBuffer.append((String) this.mComponentsToHideEdge.get(i));
          stringBuffer.append(" ");
        }
        stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      } else {
        stringBuffer.append("null\n");
      }
    }
    return stringBuffer.toString();
  }
}
