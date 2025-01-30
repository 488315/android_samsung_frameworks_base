package com.android.server.notification;

import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class GroupHelper {
  public final int mAutoGroupAtCount;
  public final Callback mCallback;
  public final ArrayMap mUngroupedNotifications = new ArrayMap();

  public interface Callback {
    void addAutoGroup(String str);

    void addAutoGroupSummary(int i, String str, String str2, int i2);

    void removeAutoGroup(String str);

    void removeAutoGroupSummary(int i, String str);

    void updateAutogroupSummary(int i, String str, int i2);
  }

  public final boolean hasAnyFlag(int i, int i2) {
    return (i & i2) != 0;
  }

  public GroupHelper(int i, Callback callback) {
    this.mAutoGroupAtCount = i;
    this.mCallback = callback;
  }

  public final String generatePackageKey(int i, String str) {
    return i + "|" + str;
  }

  public int getAutogroupSummaryFlags(ArrayMap arrayMap) {
    boolean z = arrayMap.size() > 0;
    int i = 0;
    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
      if (!hasAnyFlag(((Integer) arrayMap.valueAt(i2)).intValue(), 16)) {
        z = false;
      }
      if (hasAnyFlag(((Integer) arrayMap.valueAt(i2)).intValue(), 34)) {
        i |= ((Integer) arrayMap.valueAt(i2)).intValue() & 34;
      }
    }
    return (z ? 16 : 0) | 1792 | i;
  }

  public void onNotificationPosted(StatusBarNotification statusBarNotification, boolean z) {
    try {
      if (!statusBarNotification.isAppGroup()) {
        maybeGroup(statusBarNotification, z);
      } else {
        maybeUngroup(statusBarNotification, false, statusBarNotification.getUserId());
      }
    } catch (Exception e) {
      Slog.e("GroupHelper", "Failure processing new notification", e);
    }
  }

  public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    try {
      maybeUngroup(statusBarNotification, true, statusBarNotification.getUserId());
    } catch (Exception e) {
      Slog.e("GroupHelper", "Error processing canceled notification", e);
    }
  }

  public final void maybeGroup(StatusBarNotification statusBarNotification, boolean z) {
    int autogroupSummaryFlags;
    ArrayList arrayList = new ArrayList();
    synchronized (this.mUngroupedNotifications) {
      String generatePackageKey =
          generatePackageKey(
              statusBarNotification.getUserId(), statusBarNotification.getPackageName());
      ArrayMap arrayMap =
          (ArrayMap) this.mUngroupedNotifications.getOrDefault(generatePackageKey, new ArrayMap());
      arrayMap.put(
          statusBarNotification.getKey(),
          Integer.valueOf(statusBarNotification.getNotification().flags));
      this.mUngroupedNotifications.put(generatePackageKey, arrayMap);
      if (arrayMap.size() < this.mAutoGroupAtCount && !z) {
        autogroupSummaryFlags = 0;
      }
      autogroupSummaryFlags = getAutogroupSummaryFlags(arrayMap);
      arrayList.addAll(arrayMap.keySet());
    }
    if (arrayList.size() > 0) {
      if (z) {
        this.mCallback.updateAutogroupSummary(
            statusBarNotification.getUserId(),
            statusBarNotification.getPackageName(),
            autogroupSummaryFlags);
      } else {
        this.mCallback.addAutoGroupSummary(
            statusBarNotification.getUserId(),
            statusBarNotification.getPackageName(),
            statusBarNotification.getKey(),
            autogroupSummaryFlags);
      }
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        this.mCallback.addAutoGroup((String) it.next());
      }
    }
  }

  public final void maybeUngroup(StatusBarNotification statusBarNotification, boolean z, int i) {
    boolean z2;
    int i2;
    boolean z3;
    synchronized (this.mUngroupedNotifications) {
      ArrayMap arrayMap =
          (ArrayMap)
              this.mUngroupedNotifications.getOrDefault(
                  generatePackageKey(
                      statusBarNotification.getUserId(), statusBarNotification.getPackageName()),
                  new ArrayMap());
      if (arrayMap.size() == 0) {
        return;
      }
      boolean z4 = false;
      if (arrayMap.containsKey(statusBarNotification.getKey())) {
        if (hasAnyFlag(
            ((Integer) arrayMap.remove(statusBarNotification.getKey())).intValue(), 34)) {
          i2 = getAutogroupSummaryFlags(arrayMap);
          z3 = true;
        } else {
          i2 = 0;
          z3 = false;
        }
        z2 = (z || statusBarNotification.getOverrideGroupKey() == null) ? false : true;
        if (arrayMap.size() == 0) {
          z4 = true;
        }
      } else {
        z2 = false;
        i2 = 0;
        z3 = false;
      }
      if (z4) {
        this.mCallback.removeAutoGroupSummary(i, statusBarNotification.getPackageName());
      } else if (z3) {
        this.mCallback.updateAutogroupSummary(i, statusBarNotification.getPackageName(), i2);
      }
      if (z2) {
        this.mCallback.removeAutoGroup(statusBarNotification.getKey());
      }
    }
  }

  public int getNotGroupedByAppCount(int i, String str) {
    int size;
    synchronized (this.mUngroupedNotifications) {
      size =
          ((ArrayMap)
                  this.mUngroupedNotifications.getOrDefault(
                      generatePackageKey(i, str), new ArrayMap()))
              .size();
    }
    return size;
  }
}
