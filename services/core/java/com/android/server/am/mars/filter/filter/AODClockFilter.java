package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.am.mars.filter.IFilter;

public final class AODClockFilter implements IFilter {
    public boolean isAodTypeCalendar;
    public boolean isUsingAODCalendarWidget;
    public AnonymousClass1 mAODCalendarWidgetObserver;
    public AnonymousClass1 mAODClockTypeObserver;
    public Context mContext;

    public abstract class AODClockFilterHolder {
        public static final AODClockFilter INSTANCE = new AODClockFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        try {
            Context context = this.mContext;
            if (context != null) {
                if (this.mAODClockTypeObserver != null) {
                    context.getContentResolver()
                            .unregisterContentObserver(this.mAODClockTypeObserver);
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
            Slog.e(
                    "MARs:AODClockFilter",
                    "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        return ((this.isUsingAODCalendarWidget || this.isAodTypeCalendar)
                        && "com.samsung.android.calendar".equals(str))
                ? 25
                : 0;
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

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        if (context != null) {
            if (this.mAODClockTypeObserver == null) {
                final int i = 0;
                this.mAODClockTypeObserver =
                        new ContentObserver(
                                this,
                                new Handler()) { // from class:
                                                 // com.android.server.am.mars.filter.filter.AODClockFilter.1
                            public final /* synthetic */ AODClockFilter this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri) {
                                int i2;
                                switch (i) {
                                    case 0:
                                        this.this$0.getAODClockType();
                                        break;
                                    default:
                                        AODClockFilter aODClockFilter = this.this$0;
                                        aODClockFilter.getClass();
                                        try {
                                            i2 =
                                                    Settings.System.getInt(
                                                            aODClockFilter.mContext
                                                                    .getContentResolver(),
                                                            "add_info_today_schedule");
                                        } catch (Exception e) {
                                            Slog.e(
                                                    "MARs:AODClockFilter",
                                                    "calendarWidget " + e.getMessage());
                                            e.printStackTrace();
                                            i2 = 0;
                                        }
                                        aODClockFilter.isUsingAODCalendarWidget = i2 == 1;
                                        break;
                                }
                            }
                        };
            }
            if (this.mAODCalendarWidgetObserver == null) {
                final int i2 = 1;
                this.mAODCalendarWidgetObserver =
                        new ContentObserver(
                                this,
                                new Handler()) { // from class:
                                                 // com.android.server.am.mars.filter.filter.AODClockFilter.1
                            public final /* synthetic */ AODClockFilter this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri) {
                                int i22;
                                switch (i2) {
                                    case 0:
                                        this.this$0.getAODClockType();
                                        break;
                                    default:
                                        AODClockFilter aODClockFilter = this.this$0;
                                        aODClockFilter.getClass();
                                        try {
                                            i22 =
                                                    Settings.System.getInt(
                                                            aODClockFilter.mContext
                                                                    .getContentResolver(),
                                                            "add_info_today_schedule");
                                        } catch (Exception e) {
                                            Slog.e(
                                                    "MARs:AODClockFilter",
                                                    "calendarWidget " + e.getMessage());
                                            e.printStackTrace();
                                            i22 = 0;
                                        }
                                        aODClockFilter.isUsingAODCalendarWidget = i22 == 1;
                                        break;
                                }
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
        getAODClockType();
    }
}
