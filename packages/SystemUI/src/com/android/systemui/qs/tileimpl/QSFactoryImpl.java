package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import dagger.Lazy;
import java.util.Map;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public class QSFactoryImpl implements QSFactory {
    public final Provider mCustomTileFactoryProvider;
    public final Lazy mQsHostLazy;
    public final Map mTileMap;

    public QSFactoryImpl(Lazy lazy, Provider provider, Map<String, Provider> map) {
        this.mQsHostLazy = lazy;
        this.mCustomTileFactoryProvider = provider;
        this.mTileMap = map;
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final QSTile createTile(String str) {
        Lazy lazy = this.mQsHostLazy;
        QSTileImpl qSTileImplCreate = null;
        if (((QSHost) lazy.get()).isUnsupportedTile(str)) {
            MotionLayout$$ExternalSyntheticOutline0.m("Unsupported tile spec: ", str, "QSFactory");
        } else if (this.mTileMap.containsKey(str)) {
            qSTileImplCreate = (QSTileImpl) ((Provider) this.mTileMap.get(str)).get();
        } else if (str.startsWith("custom(")) {
            DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass36 anonymousClass36 = (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass36) this.mCustomTileFactoryProvider.get();
            Context userContext = ((QSHost) lazy.get()).getUserContext();
            if (!str.startsWith("custom(") || !str.endsWith(")")) {
                throw new IllegalArgumentException("Bad custom tile spec: ".concat(str));
            }
            String strSubstring = str.substring(7, str.length() - 1);
            if (strSubstring.isEmpty()) {
                throw new IllegalArgumentException("Empty custom tile spec action");
            }
            qSTileImplCreate = anonymousClass36.create(strSubstring, userContext);
        } else {
            Log.w("QSFactory", "No stock tile spec: ".concat(str));
        }
        if (qSTileImplCreate != null) {
            qSTileImplCreate.initialize();
            qSTileImplCreate.postStale();
            qSTileImplCreate.setTileSpec(str);
        }
        return qSTileImplCreate;
    }
}
