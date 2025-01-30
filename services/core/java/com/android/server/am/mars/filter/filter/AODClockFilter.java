package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.am.mars.filter.IFilter;

/* loaded from: classes.dex */
public class AODClockFilter implements IFilter {
  public final String TAG;
  public boolean isAodTypeCalendar;
  public boolean isUsingAODCalendarWidget;
  public ContentObserver mAODCalendarWidgetObserver;
  public ContentObserver mAODClockTypeObserver;
  public Context mContext;

  public abstract class AODClockFilterHolder {
    public static final AODClockFilter INSTANCE = new AODClockFilter();
  }

  public AODClockFilter() {
    this.TAG = "MARs:" + AODClockFilter.class.getSimpleName();
  }

  public static AODClockFilter getInstance() {
    return AODClockFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    this.mContext = context;
    registerContentObserver();
    getAODClockType();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    unregisterContentObserver();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    return ((this.isUsingAODCalendarWidget || this.isAodTypeCalendar)
            && "com.samsung.android.calendar".equals(str))
        ? 25
        : 0;
  }

  public final void registerContentObserver() {
    if (this.mContext != null) {
      if (this.mAODClockTypeObserver == null) {
        this.mAODClockTypeObserver =
            new ContentObserver(
                new Handler()) { // from class:
                                 // com.android.server.am.mars.filter.filter.AODClockFilter.1
              @Override // android.database.ContentObserver
              public void onChange(boolean z, Uri uri) {
                AODClockFilter.this.getAODClockType();
              }
            };
      }
      if (this.mAODCalendarWidgetObserver == null) {
        this.mAODCalendarWidgetObserver =
            new ContentObserver(
                new Handler()) { // from class:
                                 // com.android.server.am.mars.filter.filter.AODClockFilter.2
              @Override // android.database.ContentObserver
              public void onChange(boolean z, Uri uri) {
                AODClockFilter.this.getAODCalaendarWidget();
              }
            };
      }
      try {
        this.mContext
            .getContentResolver()
            .registerContentObserver(
                Uri.parse(
                    "content://com.samsung.android.app.aodservice.provider/settings/aod_clock_type"),
                false,
                this.mAODClockTypeObserver);
        this.mContext
            .getContentResolver()
            .registerContentObserver(
                Uri.parse("content://settings/system/add_info_today_schedule"),
                false,
                this.mAODCalendarWidgetObserver);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public final void unregisterContentObserver() {
    try {
      Context context = this.mContext;
      if (context != null) {
        if (this.mAODClockTypeObserver != null) {
          context.getContentResolver().unregisterContentObserver(this.mAODClockTypeObserver);
          this.mAODClockTypeObserver = null;
        }
        if (this.mAODCalendarWidgetObserver != null) {
          this.mContext
              .getContentResolver()
              .unregisterContentObserver(this.mAODCalendarWidgetObserver);
          this.mAODCalendarWidgetObserver = null;
        }
      }
    } catch (IllegalArgumentException unused) {
      Slog.e(this.TAG, "IllegalArgumentException occurred in unregisterContentObserver()");
    }
  }

  public final void getAODClockType() {
    int i = -1;
    try {
      Cursor query =
          this.mContext
              .getContentResolver()
              .query(
                  Uri.parse(
                      "content://com.samsung.android.app.aodservice.provider/settings/aod_clock_type"),
                  null,
                  null,
                  null,
                  null);
      if (query != null) {
        try {
          if (query.getCount() > 0) {
            query.moveToFirst();
            i = query.getInt(0);
          }
        } finally {
        }
      }
      if (query != null) {
        query.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.isAodTypeCalendar = i == 50001;
  }

  public final void getAODCalaendarWidget() {
    int i;
    try {
      i = Settings.System.getInt(this.mContext.getContentResolver(), "add_info_today_schedule");
    } catch (Exception e) {
      Slog.e(this.TAG, "calendarWidget " + e.getMessage());
      e.printStackTrace();
      i = 0;
    }
    this.isUsingAODCalendarWidget = i == 1;
  }
}
