package com.android.systemui.dextouchpad.manager;

import android.app.SemStatusBarManager;
import android.content.Context;
import android.widget.RemoteViews;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.dextouchpad.util.Features;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class NavBarIconManager {
    public final Context mContext;
    public boolean mHasNavBarIcon = false;
    public final NavBarIconManager$$ExternalSyntheticLambda0 mRemoteView = new NavBarIconManager$$ExternalSyntheticLambda0();
    public final SemStatusBarManager mSemStatusBarManager;

    public NavBarIconManager(Context context) {
        this.mContext = context;
        this.mSemStatusBarManager = (SemStatusBarManager) context.getSystemService(SemStatusBarManager.class);
    }

    public final void remove() {
        if (Features.DEBUG) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("remove, hasIcon="), this.mHasNavBarIcon, "DexTouchpadNavBarIconManager");
        }
        this.mHasNavBarIcon = false;
        this.mSemStatusBarManager.setNavigationBarShortcut("com.android.systemui.dextouchpad.activity.TouchpadActivity", (RemoteViews) null, 0, 7);
    }
}
