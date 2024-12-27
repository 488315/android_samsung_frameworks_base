package com.android.systemui.wallpaper;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
