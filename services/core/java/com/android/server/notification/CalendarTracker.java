package com.android.server.notification;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.CalendarContract;
import android.service.notification.ZenModeConfig;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.server.backup.BackupManagerConstants;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Objects;

/* loaded from: classes2.dex */
public class CalendarTracker {
    public Callback mCallback;
    public final ContentObserver mObserver = new ContentObserver(null) { // from class: com.android.server.notification.CalendarTracker.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (CalendarTracker.DEBUG) {
                Log.d("ConditionProviders.CT", "onChange selfChange=" + z + " uri=" + uri + " u=" + CalendarTracker.this.mUserContext.getUserId());
            }
            CalendarTracker.this.mCallback.onChanged();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (CalendarTracker.DEBUG) {
                Log.d("ConditionProviders.CT", "onChange selfChange=" + z);
            }
        }
    };
    public boolean mRegistered;
    public final Context mSystemContext;
    public final Context mUserContext;
    public static final boolean DEBUG = Log.isLoggable("ConditionProviders", 3);
    public static final String[] INSTANCE_PROJECTION = {"begin", "end", KnoxCustomManagerService.SHORTCUT_TITLE, "visible", "event_id", "calendar_displayName", "ownerAccount", "calendar_id", "availability"};
    public static final String[] ATTENDEE_PROJECTION = {"event_id", "attendeeEmail", "attendeeStatus"};

    public interface Callback {
        void onChanged();
    }

    public class CheckEventResult {
        public boolean inEvent;
        public long recheckAt;
    }

    public static boolean meetsReply(int i, int i2) {
        return i != 0 ? i != 1 ? i == 2 && i2 == 1 : i2 == 1 || i2 == 4 : i2 != 2;
    }

    public CalendarTracker(Context context, Context context2) {
        this.mSystemContext = context;
        this.mUserContext = context2;
    }

    public void setCallback(Callback callback) {
        if (this.mCallback == callback) {
            return;
        }
        this.mCallback = callback;
        setRegistered(callback != null);
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mCallback=");
        printWriter.println(this.mCallback);
        printWriter.print(str);
        printWriter.print("mRegistered=");
        printWriter.println(this.mRegistered);
        printWriter.print(str);
        printWriter.print("u=");
        printWriter.println(this.mUserContext.getUserId());
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0049, code lost:
    
        if (com.android.server.notification.CalendarTracker.DEBUG == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004b, code lost:
    
        android.util.Log.d("ConditionProviders.CT", "getCalendarsWithAccess took " + (java.lang.System.currentTimeMillis() - r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0064, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0044, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0042, code lost:
    
        if (r4 == null) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:
    
        if (r4 != null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArraySet getCalendarsWithAccess() {
        long currentTimeMillis = System.currentTimeMillis();
        ArraySet arraySet = new ArraySet();
        Cursor cursor = null;
        try {
            try {
                cursor = this.mUserContext.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, new String[]{KnoxCustomManagerService.f1773ID}, "calendar_access_level >= 500 AND sync_events = 1", null, null);
                while (cursor != null) {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                    arraySet.add(Long.valueOf(cursor.getLong(0)));
                }
            } catch (SQLiteException e) {
                Slog.w("ConditionProviders.CT", "error querying calendar content provider", e);
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CheckEventResult checkEvent(ZenModeConfig.EventInfo eventInfo, long j) {
        Cursor cursor;
        ArraySet arraySet;
        boolean z;
        int i;
        ZenModeConfig.EventInfo eventInfo2;
        boolean z2;
        Uri.Builder buildUpon = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(buildUpon, j);
        long j2 = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS + j;
        ContentUris.appendId(buildUpon, j2);
        Uri build = buildUpon.build();
        CheckEventResult checkEventResult = new CheckEventResult();
        checkEventResult.recheckAt = j2;
        Cursor cursor2 = null;
        try {
            try {
                cursor2 = this.mUserContext.getContentResolver().query(build, INSTANCE_PROJECTION, null, null, "begin ASC");
                try {
                    try {
                        ArraySet calendarsWithAccess = getCalendarsWithAccess();
                        while (cursor2 != null) {
                            if (!cursor2.moveToNext()) {
                                break;
                            }
                            long j3 = cursor2.getLong(0);
                            long j4 = cursor2.getLong(1);
                            String string = cursor2.getString(2);
                            boolean z3 = cursor2.getInt(3) == 1;
                            int i2 = cursor2.getInt(4);
                            CheckEventResult checkEventResult2 = checkEventResult;
                            try {
                                String string2 = cursor2.getString(5);
                                String string3 = cursor2.getString(6);
                                long j5 = cursor2.getLong(7);
                                int i3 = cursor2.getInt(8);
                                boolean contains = calendarsWithAccess.contains(Long.valueOf(j5));
                                boolean z4 = DEBUG;
                                if (z4) {
                                    arraySet = calendarsWithAccess;
                                    cursor = cursor2;
                                    try {
                                        try {
                                            z = false;
                                            Log.d("ConditionProviders.CT", String.format("title=%s time=%s-%s vis=%s availability=%s eventId=%s name=%s owner=%s calId=%s canAccessCal=%s", string, new Date(j3), new Date(j4), Boolean.valueOf(z3), availabilityToString(i3), Integer.valueOf(i2), string2, string3, Long.valueOf(j5), Boolean.valueOf(contains)));
                                        } catch (Exception e) {
                                            e = e;
                                            checkEventResult = checkEventResult2;
                                            cursor2 = cursor;
                                            Slog.w("ConditionProviders.CT", "error reading calendar", e);
                                            if (cursor2 != null) {
                                            }
                                            return checkEventResult;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        cursor2 = cursor;
                                        if (cursor2 != null) {
                                            cursor2.close();
                                        }
                                        throw th;
                                    }
                                } else {
                                    cursor = cursor2;
                                    arraySet = calendarsWithAccess;
                                    z = false;
                                }
                                boolean z5 = (j < j3 || j >= j4) ? z : true;
                                if (z3 && contains) {
                                    i = i3;
                                    eventInfo2 = eventInfo;
                                    if ((eventInfo2.calName == null && eventInfo2.calendarId == null) || Objects.equals(eventInfo2.calendarId, Long.valueOf(j5)) || Objects.equals(eventInfo2.calName, string2)) {
                                        z2 = true;
                                        boolean z6 = i == 1 ? true : z;
                                        if (!z2 && z6) {
                                            if (z4) {
                                                Log.d("ConditionProviders.CT", "  MEETS CALENDAR & AVAILABILITY");
                                            }
                                            if (meetsAttendee(eventInfo2, i2, string3)) {
                                                if (z4) {
                                                    Log.d("ConditionProviders.CT", "    MEETS ATTENDEE");
                                                }
                                                if (z5) {
                                                    if (z4) {
                                                        Log.d("ConditionProviders.CT", "      MEETS TIME");
                                                    }
                                                    checkEventResult = checkEventResult2;
                                                    try {
                                                        checkEventResult.inEvent = true;
                                                    } catch (Exception e2) {
                                                        e = e2;
                                                        cursor2 = cursor;
                                                        Slog.w("ConditionProviders.CT", "error reading calendar", e);
                                                        if (cursor2 != null) {
                                                            cursor2.close();
                                                        }
                                                        return checkEventResult;
                                                    }
                                                } else {
                                                    checkEventResult = checkEventResult2;
                                                }
                                                if (j3 > j && j3 < checkEventResult.recheckAt) {
                                                    checkEventResult.recheckAt = j3;
                                                } else if (j4 > j && j4 < checkEventResult.recheckAt) {
                                                    checkEventResult.recheckAt = j4;
                                                }
                                                calendarsWithAccess = arraySet;
                                                cursor2 = cursor;
                                            }
                                        }
                                        checkEventResult = checkEventResult2;
                                        calendarsWithAccess = arraySet;
                                        cursor2 = cursor;
                                    }
                                } else {
                                    i = i3;
                                    eventInfo2 = eventInfo;
                                }
                                z2 = z;
                                if (i == 1) {
                                }
                                if (!z2) {
                                }
                                checkEventResult = checkEventResult2;
                                calendarsWithAccess = arraySet;
                                cursor2 = cursor;
                            } catch (Exception e3) {
                                e = e3;
                                checkEventResult = checkEventResult2;
                            }
                        }
                        Cursor cursor3 = cursor2;
                        if (cursor3 != null) {
                            cursor3.close();
                        }
                    } catch (Exception e4) {
                        e = e4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e5) {
            e = e5;
        }
        return checkEventResult;
    }

    public final boolean meetsAttendee(ZenModeConfig.EventInfo eventInfo, int i, String str) {
        long currentTimeMillis = System.currentTimeMillis();
        int i2 = 0;
        Cursor cursor = null;
        try {
            try {
                Cursor query = this.mUserContext.getContentResolver().query(CalendarContract.Attendees.CONTENT_URI, ATTENDEE_PROJECTION, "event_id = ? AND attendeeEmail = ?", new String[]{Integer.toString(i), str}, null);
                int i3 = 1;
                if (query != null && query.getCount() != 0) {
                    boolean z = false;
                    while (query.moveToNext()) {
                        long j = query.getLong(i2);
                        String string = query.getString(i3);
                        int i4 = query.getInt(2);
                        boolean meetsReply = meetsReply(eventInfo.reply, i4);
                        if (DEBUG) {
                            Log.d("ConditionProviders.CT", "" + String.format("status=%s, meetsReply=%s", attendeeStatusToString(i4), Boolean.valueOf(meetsReply)));
                        }
                        z |= j == ((long) i) && Objects.equals(string, str) && meetsReply;
                        i3 = 1;
                        i2 = 0;
                    }
                    query.close();
                    if (DEBUG) {
                        Log.d("ConditionProviders.CT", "meetsAttendee took " + (System.currentTimeMillis() - currentTimeMillis));
                    }
                    return z;
                }
                boolean z2 = DEBUG;
                if (z2) {
                    Log.d("ConditionProviders.CT", "No attendees found");
                }
                if (query != null) {
                    query.close();
                }
                if (!z2) {
                    return true;
                }
                Log.d("ConditionProviders.CT", "meetsAttendee took " + (System.currentTimeMillis() - currentTimeMillis));
                return true;
            } catch (SQLiteException e) {
                Slog.w("ConditionProviders.CT", "error querying attendees content provider", e);
                if (0 != 0) {
                    cursor.close();
                }
                if (!DEBUG) {
                    return false;
                }
                Log.d("ConditionProviders.CT", "meetsAttendee took " + (System.currentTimeMillis() - currentTimeMillis));
                return false;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            if (DEBUG) {
                Log.d("ConditionProviders.CT", "meetsAttendee took " + (System.currentTimeMillis() - currentTimeMillis));
            }
            throw th;
        }
    }

    public final void setRegistered(boolean z) {
        if (this.mRegistered == z) {
            return;
        }
        ContentResolver contentResolver = this.mSystemContext.getContentResolver();
        int userId = this.mUserContext.getUserId();
        if (this.mRegistered) {
            if (DEBUG) {
                Log.d("ConditionProviders.CT", "unregister content observer u=" + userId);
            }
            contentResolver.unregisterContentObserver(this.mObserver);
        }
        this.mRegistered = z;
        boolean z2 = DEBUG;
        if (z2) {
            Log.d("ConditionProviders.CT", "mRegistered = " + z + " u=" + userId);
        }
        if (this.mRegistered) {
            if (z2) {
                Log.d("ConditionProviders.CT", "register content observer u=" + userId);
            }
            contentResolver.registerContentObserver(CalendarContract.Instances.CONTENT_URI, true, this.mObserver, userId);
            contentResolver.registerContentObserver(CalendarContract.Events.CONTENT_URI, true, this.mObserver, userId);
            contentResolver.registerContentObserver(CalendarContract.Calendars.CONTENT_URI, true, this.mObserver, userId);
        }
    }

    public static String attendeeStatusToString(int i) {
        if (i == 0) {
            return "ATTENDEE_STATUS_NONE";
        }
        if (i == 1) {
            return "ATTENDEE_STATUS_ACCEPTED";
        }
        if (i == 2) {
            return "ATTENDEE_STATUS_DECLINED";
        }
        if (i == 3) {
            return "ATTENDEE_STATUS_INVITED";
        }
        if (i == 4) {
            return "ATTENDEE_STATUS_TENTATIVE";
        }
        return "ATTENDEE_STATUS_UNKNOWN_" + i;
    }

    public static String availabilityToString(int i) {
        if (i == 0) {
            return "AVAILABILITY_BUSY";
        }
        if (i == 1) {
            return "AVAILABILITY_FREE";
        }
        if (i == 2) {
            return "AVAILABILITY_TENTATIVE";
        }
        return "AVAILABILITY_UNKNOWN_" + i;
    }
}
