package com.android.systemui.wallpaper;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class WallpaperChangeNotifier {
    public final Context mContext;
    public final Handler mHandler;
    public final ArrayList mListeners = new ArrayList();

    public WallpaperChangeNotifier(Context context, Handler handler) {
        this.mContext = context;
        Settings.System.getInt(context.getContentResolver(), "dls_state", 0);
        this.mHandler = handler;
    }
}
