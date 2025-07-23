package com.android.internal.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import java.util.Collection;

/* loaded from: classes5.dex */
public class ForceShowNavBarSettingsObserver extends ContentObserver {
    private Context mContext;
    private Runnable mOnChangeRunnable;

    public ForceShowNavBarSettingsObserver(Handler handler, Context context) {
        super(handler);
        this.mContext = context;
    }

    public void setOnChangeRunnable(Runnable runnable) {
        this.mOnChangeRunnable = runnable;
    }

    public void register() {
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.NAV_BAR_FORCE_VISIBLE), false, this, -1);
    }

    public void unregister() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
        Runnable runnable;
        if (i2 == ActivityManager.getCurrentUser() && (runnable = this.mOnChangeRunnable) != null) {
            runnable.run();
        }
    }

    public boolean isEnabled() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.NAV_BAR_FORCE_VISIBLE, 0, -2) == 1;
    }
}
