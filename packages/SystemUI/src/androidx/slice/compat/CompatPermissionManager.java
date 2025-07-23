package androidx.slice.compat;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
