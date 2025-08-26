package com.android.wm.shell.common;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes3.dex */
public class DnDSnackBarController {
    public final Context mContext;
    public final SharedPreferences mSnackBarPref;
    public DnDSnackBarWindow mView;
    public boolean mWasShownSnackBar;

    public DnDSnackBarController(Context context) {
        Context contextCreateWindowContext = context.createWindowContext(2008, null);
        this.mContext = contextCreateWindowContext;
        SharedPreferences sharedPreferences = contextCreateWindowContext.getSharedPreferences("snack_bar_pref_name", 0);
        this.mSnackBarPref = sharedPreferences;
        this.mWasShownSnackBar = sharedPreferences.getBoolean("snack_bar_shown", false);
    }
}
