package com.android.server.power;

import android.os.Handler;

import com.android.internal.statusbar.IStatusBarService;

public class InattentiveSleepWarningController {
    public final Handler mHandler = new Handler();
    public boolean mIsShown;
    public IStatusBarService mStatusBarService;
}
