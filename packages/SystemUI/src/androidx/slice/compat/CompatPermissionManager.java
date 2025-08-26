package androidx.slice.compat;

import android.content.Context;

/* loaded from: classes.dex */
public class CompatPermissionManager {
    public final String[] mAutoGrantPermissions;
    public final Context mContext;
    public final String mPrefsName;

    public CompatPermissionManager(Context context, String str, int i, String[] strArr) {
        this.mContext = context;
        this.mPrefsName = str;
        this.mAutoGrantPermissions = strArr;
    }
}
