package com.android.server.people.data;

import android.content.Context;
import android.net.Uri;

public final class ContactsQueryHelper {
    public Uri mContactUri;
    public final Context mContext;
    public boolean mIsStarred;
    public long mLastUpdatedTimestamp;
    public String mPhoneNumber;

    public ContactsQueryHelper(Context context) {
        this.mContext = context;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean queryContact(
            android.net.Uri r13,
            java.lang.String[] r14,
            java.lang.String r15,
            java.lang.String[] r16) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.people.data.ContactsQueryHelper.queryContact(android.net.Uri,"
                    + " java.lang.String[], java.lang.String, java.lang.String[]):boolean");
    }
}
