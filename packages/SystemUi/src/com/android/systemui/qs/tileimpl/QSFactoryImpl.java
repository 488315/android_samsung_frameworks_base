package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.qp.SubRoomQsTileBaseView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import dagger.Lazy;
import java.util.Map;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSFactoryImpl implements QSFactory {
    public final Provider mCustomTileBuilderProvider;
    public final Lazy mQsHostLazy;
    public final Map mTileMap;

    public QSFactoryImpl(Lazy lazy, Provider provider, Map<String, Provider> map) {
        this.mQsHostLazy = lazy;
        this.mCustomTileBuilderProvider = provider;
        this.mTileMap = map;
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final QSTileView createCoverScreenTileView(Context context, QSTile qSTile, boolean z) {
        return new SubRoomQsTileBaseView(context, qSTile.createTileView(context), z);
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final QSTile createTile(String str) {
        Lazy lazy = this.mQsHostLazy;
        QSTileImpl qSTileImpl = null;
        if (((QSHost) lazy.get()).isUnsupportedTile(str)) {
            MotionLayout$$ExternalSyntheticOutline0.m23m("Unsupported tile spec: ", str, "QSFactory");
        } else {
            Map map = this.mTileMap;
            if (map.containsKey(str) && (!str.equals("dbg:mem") || Build.IS_DEBUGGABLE)) {
                qSTileImpl = (QSTileImpl) ((Provider) map.get(str)).get();
            } else if (str.startsWith("custom(")) {
                CustomTile.Builder builder = (CustomTile.Builder) this.mCustomTileBuilderProvider.get();
                Context userContext = ((QSHost) lazy.get()).getUserContext();
                int i = CustomTile.$r8$clinit;
                builder.mSpec = str;
                builder.mUserContext = userContext;
                qSTileImpl = builder.build();
            } else {
                Log.w("QSFactory", "No stock tile spec: ".concat(str));
            }
        }
        if (qSTileImpl != null) {
            qSTileImpl.initialize();
            qSTileImpl.postStale();
        }
        return qSTileImpl;
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final QSTileView createTileView(Context context, QSTile qSTile, boolean z) {
        QSIconView createTileView = qSTile.createTileView(context);
        if (z) {
            return new SecQSTileBaseView(context, createTileView, z);
        }
        Lazy lazy = this.mQsHostLazy;
        return new SecQSTileView(context, createTileView, false, ((QSHost) lazy.get()).isLargeBarTile(qSTile.getTileSpec()), ((QSHost) lazy.get()).isBrightnessBarTile(qSTile.getTileSpec()), qSTile.getState().isNonBGTile);
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final boolean isSystemTile(String str) {
        return this.mTileMap.containsKey(str);
    }
}
