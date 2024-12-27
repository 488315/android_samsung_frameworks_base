package com.android.server.notification;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.util.Slog;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

public final class CalendarTracker {
    public EventConditionProvider.AnonymousClass2 mCallback;
    public final AnonymousClass1 mObserver =
            new ContentObserver() { // from class: com.android.server.notification.CalendarTracker.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    if (CalendarTracker.DEBUG) {
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                "onChange selfChange=", "ConditionProviders.CT", z);
                    }
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    if (CalendarTracker.DEBUG) {
                        Log.d(
                                "ConditionProviders.CT",
                                "onChange selfChange="
                                        + z
                                        + " uri="
                                        + uri
                                        + " u="
                                        + CalendarTracker.this.mUserContext.getUserId());
                    }
                    EventConditionProvider.AnonymousClass2 anonymousClass2 =
                            CalendarTracker.this.mCallback;
                    anonymousClass2.getClass();
                    if (EventConditionProvider.DEBUG) {
                        Slog.d("ConditionProviders.ECP", "mTrackerCallback.onChanged");
                    }
                    EventConditionProvider eventConditionProvider = EventConditionProvider.this;
                    eventConditionProvider.mWorker.removeCallbacks(
                            eventConditionProvider.mEvaluateSubscriptionsW);
                    eventConditionProvider.mWorker.postDelayed(
                            eventConditionProvider.mEvaluateSubscriptionsW, 2000L);
                }
            };
    public boolean mRegistered;
    public final Context mSystemContext;
    public final Context mUserContext;
    public static final boolean DEBUG = Log.isLoggable("ConditionProviders", 3);
    public static final String[] INSTANCE_PROJECTION = {
        "begin",
        "end",
        KnoxCustomManagerService.SHORTCUT_TITLE,
        "visible",
        "event_id",
        "calendar_displayName",
        "ownerAccount",
        "calendar_id",
        "availability"
    };
    public static final String[] ATTENDEE_PROJECTION = {
        "event_id", "attendeeEmail", "attendeeStatus"
    };

    public final class CheckEventResult {
        public boolean inEvent;
        public long recheckAt;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.notification.CalendarTracker$1] */
    public CalendarTracker(Context context, Context context2) {
        this.mSystemContext = context;
        this.mUserContext = context2;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0117  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.notification.CalendarTracker.CheckEventResult checkEvent(
            android.service.notification.ZenModeConfig.EventInfo r33, long r34) {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.CalendarTracker.checkEvent(android.service.notification.ZenModeConfig$EventInfo,"
                    + " long):com.android.server.notification.CalendarTracker$CheckEventResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004b, code lost:

       if (com.android.server.notification.CalendarTracker.DEBUG == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004d, code lost:

       android.util.Log.d("ConditionProviders.CT", "getCalendarsWithAccess took " + (java.lang.System.currentTimeMillis() - r1));
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:

       return r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:

       if (r4 == null) goto L18;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.ArraySet getCalendarsWithAccess() {
        /*
            r11 = this;
            java.lang.String r0 = "ConditionProviders.CT"
            long r1 = java.lang.System.currentTimeMillis()
            android.util.ArraySet r3 = new android.util.ArraySet
            r3.<init>()
            java.lang.String r4 = "_id"
            java.lang.String[] r7 = new java.lang.String[]{r4}
            r4 = 0
            android.content.Context r11 = r11.mUserContext     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            android.content.ContentResolver r5 = r11.getContentResolver()     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            android.net.Uri r6 = android.provider.CalendarContract.Calendars.CONTENT_URI     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            java.lang.String r8 = "calendar_access_level >= 500 AND sync_events = 1"
            r9 = 0
            r10 = 0
            android.database.Cursor r4 = r5.query(r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
        L22:
            if (r4 == 0) goto L3b
            boolean r11 = r4.moveToNext()     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            if (r11 == 0) goto L3b
            r11 = 0
            long r5 = r4.getLong(r11)     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            java.lang.Long r11 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            r3.add(r11)     // Catch: java.lang.Throwable -> L37 android.database.sqlite.SQLiteException -> L39
            goto L22
        L37:
            r11 = move-exception
            goto L64
        L39:
            r11 = move-exception
            goto L41
        L3b:
            if (r4 == 0) goto L49
        L3d:
            r4.close()
            goto L49
        L41:
            java.lang.String r5 = "error querying calendar content provider"
            android.util.Slog.w(r0, r5, r11)     // Catch: java.lang.Throwable -> L37
            if (r4 == 0) goto L49
            goto L3d
        L49:
            boolean r11 = com.android.server.notification.CalendarTracker.DEBUG
            if (r11 == 0) goto L63
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r4 = "getCalendarsWithAccess took "
            r11.<init>(r4)
            long r4 = java.lang.System.currentTimeMillis()
            long r4 = r4 - r1
            r11.append(r4)
            java.lang.String r11 = r11.toString()
            android.util.Log.d(r0, r11)
        L63:
            return r3
        L64:
            if (r4 == 0) goto L69
            r4.close()
        L69:
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.CalendarTracker.getCalendarsWithAccess():android.util.ArraySet");
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00ee, code lost:

       android.util.Log.d("ConditionProviders.CT", "No attendees found");
    */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean meetsAttendee(
            android.service.notification.ZenModeConfig.EventInfo r17,
            int r18,
            java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.CalendarTracker.meetsAttendee(android.service.notification.ZenModeConfig$EventInfo,"
                    + " int, java.lang.String):boolean");
    }

    public final void setCallback(EventConditionProvider.AnonymousClass2 anonymousClass2) {
        if (this.mCallback == anonymousClass2) {
            return;
        }
        this.mCallback = anonymousClass2;
        boolean z = anonymousClass2 != null;
        if (this.mRegistered == z) {
            return;
        }
        ContentResolver contentResolver = this.mSystemContext.getContentResolver();
        int userId = this.mUserContext.getUserId();
        boolean z2 = this.mRegistered;
        AnonymousClass1 anonymousClass1 = this.mObserver;
        boolean z3 = DEBUG;
        if (z2) {
            if (z3) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        userId, "unregister content observer u=", "ConditionProviders.CT");
            }
            contentResolver.unregisterContentObserver(anonymousClass1);
        }
        this.mRegistered = z;
        if (z3) {
            Log.d("ConditionProviders.CT", "mRegistered = " + z + " u=" + userId);
        }
        if (this.mRegistered) {
            if (z3) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        userId, "register content observer u=", "ConditionProviders.CT");
            }
            contentResolver.registerContentObserver(
                    CalendarContract.Instances.CONTENT_URI, true, anonymousClass1, userId);
            contentResolver.registerContentObserver(
                    CalendarContract.Events.CONTENT_URI, true, anonymousClass1, userId);
            contentResolver.registerContentObserver(
                    CalendarContract.Calendars.CONTENT_URI, true, anonymousClass1, userId);
        }
    }
}
