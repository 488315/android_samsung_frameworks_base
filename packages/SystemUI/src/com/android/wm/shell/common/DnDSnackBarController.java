package com.android.wm.shell.common;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DnDSnackBarController {
    public final Context mContext;
    public final SharedPreferences mSnackBarPref;
    public DnDSnackBarWindow mView;
    public boolean mWasShownSnackBar;

    public DnDSnackBarController(Context context) {
        Context createWindowContext = context.createWindowContext(2008, null);
        this.mContext = createWindowContext;
        SharedPreferences sharedPreferences = createWindowContext.getSharedPreferences("snack_bar_pref_name", 0);
        this.mSnackBarPref = sharedPreferences;
        this.mWasShownSnackBar = sharedPreferences.getBoolean("snack_bar_shown", false);
    }
}
