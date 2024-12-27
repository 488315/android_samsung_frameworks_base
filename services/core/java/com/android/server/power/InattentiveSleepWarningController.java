package com.android.server.power;

import android.os.Handler;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class InattentiveSleepWarningController {
    public final Handler mHandler = new Handler();
    public boolean mIsShown;
    public IStatusBarService mStatusBarService;
}
