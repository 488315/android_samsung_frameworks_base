package com.android.systemui.qs;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.ScRune;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda4(QSTileHost qSTileHost, String str, int i) {
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
                qSTileHost.changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda11(str, 0));
                SecQQSTileHost secQQSTileHost = qSTileHost.mQQSTileHost;
                Context context = secQQSTileHost.mContext;
                List loadTileSpecs = secQQSTileHost.loadTileSpecs(context, Settings.Secure.getStringForUser(context.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) secQQSTileHost.mUserTracker).getUserId()));
                if (loadTileSpecs.remove(str)) {
                    secQQSTileHost.changeTiles(loadTileSpecs);
                }
                if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
                    SecSubScreenQSTileHost secSubScreenQSTileHost = qSTileHost.mSubScreenTileHost;
                    Context context2 = secSubScreenQSTileHost.mContext;
                    List loadTileSpecs2 = secSubScreenQSTileHost.loadTileSpecs(context2, Settings.Secure.getStringForUser(context2.getContentResolver(), "sysui_sub_qs_tiles", ((UserTrackerImpl) secSubScreenQSTileHost.mUserTracker).getUserId()));
                    if (loadTileSpecs2.remove(str)) {
                        secSubScreenQSTileHost.changeTiles(loadTileSpecs2);
                    }
                }
                SecLockscreenTileHost secLockscreenTileHost = qSTileHost.mLockscreentTileHost;
                secLockscreenTileHost.getClass();
                Log.d(secLockscreenTileHost.TAG, "removeTile  ".concat(str));
                Iterator it = ((ArrayList) secLockscreenTileHost.callbacks).iterator();
                if (it.hasNext()) {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                    throw null;
                }
                return;
            case 1:
                QSTileHost qSTileHost2 = this.f$0;
                qSTileHost2.mQQSTileHost.restoreQQSTileListIfNeeded(this.f$1);
                return;
            default:
                QSTileHost qSTileHost3 = this.f$0;
                String str2 = this.f$1;
                qSTileHost3.getClass();
                qSTileHost3.changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda11(str2, 1));
                return;
        }
    }
}
