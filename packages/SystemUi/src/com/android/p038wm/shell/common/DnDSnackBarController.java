package com.android.p038wm.shell.common;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.p038wm.shell.common.DnDSnackBarWindow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DnDSnackBarController implements DnDSnackBarWindow.SnackBarCallbacks {
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
