package com.android.systemui.wallpaper;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import java.util.ArrayList;

public final class WallpaperChangeNotifier {
    public final Context mContext;
    public final Handler mHandler;
    public final ArrayList mListeners = new ArrayList();

    public WallpaperChangeNotifier(Context context, Handler handler) {
        this.mContext = context;
        Settings.System.getInt(context.getContentResolver(), "dls_state", 0);
        this.mHandler = handler;
    }
}
