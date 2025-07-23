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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        QSTileImpl qSTileImpl = null;
        if (((QSHost) lazy.get()).isUnsupportedTile(str)) {
            MotionLayout$$ExternalSyntheticOutline0.m("Unsupported tile spec: ", str, "QSFactory");
        } else if (this.mTileMap.containsKey(str)) {
            qSTileImpl = (QSTileImpl) ((Provider) this.mTileMap.get(str)).get();
        } else if (str.startsWith("custom(")) {
            DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass36 anonymousClass36 = (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass36) this.mCustomTileFactoryProvider.get();
            Context userContext = ((QSHost) lazy.get()).getUserContext();
            if (!str.startsWith("custom(") || !str.endsWith(")")) {
                throw new IllegalArgumentException("Bad custom tile spec: ".concat(str));
            }
            String substring = str.substring(7, str.length() - 1);
            if (substring.isEmpty()) {
                throw new IllegalArgumentException("Empty custom tile spec action");
            }
            qSTileImpl = anonymousClass36.create(substring, userContext);
        } else {
            Log.w("QSFactory", "No stock tile spec: ".concat(str));
        }
        if (qSTileImpl != null) {
            qSTileImpl.initialize();
            qSTileImpl.postStale();
            qSTileImpl.setTileSpec(str);
        }
        return qSTileImpl;
    }
}
