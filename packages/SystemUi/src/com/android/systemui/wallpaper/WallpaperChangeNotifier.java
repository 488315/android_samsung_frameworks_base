package com.android.systemui.wallpaper;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperChangeNotifier {
    public final Context mContext;
    public int mDlsState;
    public final Handler mHandler;
    public final ArrayList mListeners = new ArrayList();

    public WallpaperChangeNotifier(Context context, Handler handler) {
        this.mContext = context;
        this.mDlsState = Settings.System.getInt(context.getContentResolver(), "dls_state", 0);
        this.mHandler = handler;
    }

    public final void notify(final int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("notify: which = ", i, "WallpaperChangeNotifier");
        synchronized (this.mListeners) {
            for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(this.mListeners.get(i2));
                this.mHandler.post(new Runnable(i) { // from class: com.android.systemui.wallpaper.WallpaperChangeNotifier$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        throw null;
                    }
                });
            }
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperChangeNotifier$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WallpaperChangeNotifier wallpaperChangeNotifier = WallpaperChangeNotifier.this;
                wallpaperChangeNotifier.mDlsState = Settings.System.getInt(wallpaperChangeNotifier.mContext.getContentResolver(), "dls_state", 0);
            }
        }, 500L);
    }
}
