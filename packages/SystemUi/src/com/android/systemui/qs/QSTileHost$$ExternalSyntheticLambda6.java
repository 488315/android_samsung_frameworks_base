package com.android.systemui.qs;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.ScRune;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda6(QSTileHost qSTileHost, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                QSTileHost qSTileHost = this.f$0;
                String str = this.f$1;
                qSTileHost.getClass();
                qSTileHost.changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda14(str, 0));
                qSTileHost.mQQSTileHost.removeTile(str);
                if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
                    SecSubScreenQSTileHost secSubScreenQSTileHost = qSTileHost.mSubScreenTileHost;
                    Context context = secSubScreenQSTileHost.mContext;
                    ContentResolver contentResolver = context.getContentResolver();
                    UserTracker userTracker = secSubScreenQSTileHost.mUserTracker;
                    List loadTileSpecs = secSubScreenQSTileHost.loadTileSpecs(context, Settings.Secure.getStringForUser(contentResolver, "sysui_sub_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
                    if (loadTileSpecs.remove(str)) {
                        Log.d("SecSubScreenQSTileHost", "changeTiles " + loadTileSpecs);
                        Settings.Secure.putStringForUser(secSubScreenQSTileHost.mContext.getContentResolver(), "sysui_sub_qs_tiles", TextUtils.join(",", loadTileSpecs), ((UserTrackerImpl) userTracker).getUserId());
                        break;
                    }
                }
                break;
            default:
                this.f$0.mQQSTileHost.restoreQQSTileListIfNeeded(this.f$1);
                break;
        }
    }
}
