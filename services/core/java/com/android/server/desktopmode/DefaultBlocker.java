package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.display.DisplayManager;

import com.samsung.android.desktopmode.IDesktopModeBlocker;

public final class DefaultBlocker {
    public final ActivityManager mActivityManager;
    public final BlockerImpl mBlocker = new BlockerImpl();
    public final Context mContext;
    public final DisplayManager mDisplayManager;

    public final class BlockerImpl extends IDesktopModeBlocker.Stub {
        public int reasonCode = 0;

        public BlockerImpl() {}

        public final String onBlocked() {
            int i = this.reasonCode;
            if (i == 3) {
                return DefaultBlocker.this.mContext.getString(R.string.httpErrorTimeout);
            }
            if (i == 4) {
                return DefaultBlocker.this.mContext.getString(R.string.httpErrorLookup);
            }
            if (i == 6) {
                Context context = DefaultBlocker.this.mContext;
                return context.getString(
                        R.string.hour_picker_description, context.getString(17042662));
            }
            if (i != 7) {
                return DefaultBlocker.this.mContext.getString(R.string.imProtocolAim);
            }
            return null;
        }
    }

    public DefaultBlocker(
            Context context, ActivityManager activityManager, DisplayManager displayManager) {
        this.mContext = context;
        this.mActivityManager = activityManager;
        this.mDisplayManager = displayManager;
    }
}
