package com.android.server.notification;

import android.os.IBinder;
import android.os.UserHandle;
import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InlineReplyUriRecord {
    public final String mKey;
    public final String mPackageName;
    public final IBinder mPermissionOwner;
    public final ArraySet mUris = new ArraySet();
    public final UserHandle mUser;

    public InlineReplyUriRecord(IBinder iBinder, UserHandle userHandle, String str, String str2) {
        this.mPermissionOwner = iBinder;
        this.mUser = userHandle;
        this.mPackageName = str;
        this.mKey = str2;
    }
}
