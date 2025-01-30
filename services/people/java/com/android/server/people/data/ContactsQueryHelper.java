package com.android.server.people.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* loaded from: classes2.dex */
public class ContactsQueryHelper {
    public Uri mContactUri;
    public final Context mContext;
    public boolean mIsStarred;
    public long mLastUpdatedTimestamp;
    public String mPhoneNumber;

    public ContactsQueryHelper(Context context) {
        this.mContext = context;
    }

    public boolean query(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if ("tel".equals(parse.getScheme())) {
            return queryWithPhoneNumber(parse.getSchemeSpecificPart());
        }
        if ("mailto".equals(parse.getScheme())) {
            return queryWithEmail(parse.getSchemeSpecificPart());
        }
        if (str.startsWith(ContactsContract.Contacts.CONTENT_LOOKUP_URI.toString())) {
            return queryWithUri(parse);
        }
        return false;
    }

    public boolean querySince(long j) {
        return queryContact(ContactsContract.Contacts.CONTENT_URI, new String[]{KnoxCustomManagerService.f1773ID, "lookup", "starred", "has_phone_number", "contact_last_updated_timestamp"}, "contact_last_updated_timestamp > ?", new String[]{Long.toString(j)});
    }

    public Uri getContactUri() {
        return this.mContactUri;
    }

    public boolean isStarred() {
        return this.mIsStarred;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public long getLastUpdatedTimestamp() {
        return this.mLastUpdatedTimestamp;
    }

    public final boolean queryWithPhoneNumber(String str) {
        return queryWithUri(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)));
    }

    public final boolean queryWithEmail(String str) {
        return queryWithUri(Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(str)));
    }

    public final boolean queryWithUri(Uri uri) {
        return queryContact(uri, new String[]{KnoxCustomManagerService.f1773ID, "lookup", "starred", "has_phone_number"}, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00b4 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean queryContact(Uri uri, String[] strArr, String str, String[] strArr2) {
        boolean z;
        boolean z2;
        Cursor query;
        String str2 = null;
        boolean z3 = false;
        try {
            query = this.mContext.getContentResolver().query(uri, strArr, str, strArr2, null);
        } catch (SQLiteException e) {
            e = e;
            z = false;
        } catch (IllegalArgumentException e2) {
            e = e2;
            z = false;
        } catch (Exception e3) {
            e = e3;
            z = false;
        }
        if (query == null) {
            try {
                Slog.w("ContactsQueryHelper", "Cursor is null when querying contact.");
                if (query != null) {
                    query.close();
                }
                return false;
            } catch (Throwable th) {
                th = th;
                z = false;
            }
        } else {
            z2 = false;
            z = false;
            while (query.moveToNext()) {
                try {
                    long j = query.getLong(query.getColumnIndex(KnoxCustomManagerService.f1773ID));
                    str2 = query.getString(query.getColumnIndex("lookup"));
                    this.mContactUri = ContactsContract.Contacts.getLookupUri(j, str2);
                    this.mIsStarred = query.getInt(query.getColumnIndex("starred")) != 0;
                    z2 = query.getInt(query.getColumnIndex("has_phone_number")) != 0;
                    int columnIndex = query.getColumnIndex("contact_last_updated_timestamp");
                    if (columnIndex >= 0) {
                        this.mLastUpdatedTimestamp = query.getLong(columnIndex);
                    }
                    z = true;
                } catch (Throwable th2) {
                    z3 = z2;
                    th = th2;
                }
            }
            try {
                query.close();
            } catch (SQLiteException e4) {
                e = e4;
                z3 = z2;
                Slog.w("SQLite exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            } catch (IllegalArgumentException e5) {
                e = e5;
                z3 = z2;
                Slog.w("Illegal Argument exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            } catch (Exception e6) {
                e = e6;
                z3 = z2;
                Slog.w("Exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            }
            return (z || str2 == null || !z2) ? z : queryPhoneNumber(str2);
        }
        if (query == null) {
            throw th;
        }
        try {
            query.close();
            throw th;
        } catch (Throwable th3) {
            try {
                th.addSuppressed(th3);
                throw th;
            } catch (SQLiteException e7) {
                e = e7;
                Slog.w("SQLite exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            } catch (IllegalArgumentException e8) {
                e = e8;
                Slog.w("Illegal Argument exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            } catch (Exception e9) {
                e = e9;
                Slog.w("Exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            }
        }
    }

    public final boolean queryPhoneNumber(String str) {
        try {
            Cursor query = this.mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"data4"}, "lookup = ?", new String[]{str}, null);
            try {
                if (query == null) {
                    Slog.w("ContactsQueryHelper", "Cursor is null when querying contact phone number.");
                    if (query != null) {
                        query.close();
                    }
                    return false;
                }
                while (query.moveToNext()) {
                    int columnIndex = query.getColumnIndex("data4");
                    if (columnIndex >= 0) {
                        this.mPhoneNumber = query.getString(columnIndex);
                    }
                }
                query.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            Slog.w("Exception when querying phone number.", e);
            return false;
        }
    }
}
