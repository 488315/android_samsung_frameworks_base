package androidx.slice.compat;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CompatPermissionManager {
    public final String[] mAutoGrantPermissions;
    public final Context mContext;
    public final int mMyUid;
    public final String mPrefsName;

    public CompatPermissionManager(Context context, String str, int i, String[] strArr) {
        this.mContext = context;
        this.mPrefsName = str;
        this.mMyUid = i;
        this.mAutoGrantPermissions = strArr;
    }
}
