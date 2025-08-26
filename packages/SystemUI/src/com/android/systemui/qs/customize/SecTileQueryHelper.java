package com.android.systemui.qs.customize;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import android.widget.Button;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class SecTileQueryHelper {
    public final Executor mBgExecutor;
    public final Context mContext;
    public boolean mFinished;
    public SecQSCustomizerTileAdapter mListener;
    public final Executor mMainExecutor;
    public boolean mTileQueryFinished;
    public final UserTracker mUserTracker;
    public final ArrayList mTiles = new ArrayList();
    public final ArraySet mSpecs = new ArraySet();
    public final SecQSPanelResourcePicker mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);

    public class TileCollector implements QSTile.Callback {
        public final QSHost mQSHost;
        public final List mQSTileList = new ArrayList();

        public TileCollector(List<QSTile> list, QSHost qSHost, boolean z) {
            Iterator<QSTile> it = list.iterator();
            while (it.hasNext()) {
                ((ArrayList) this.mQSTileList).add(new TilePair(it.next(), 0));
            }
            this.mQSHost = qSHost;
            if (list.isEmpty()) {
                SecTileQueryHelper.this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.customize.SecTileQueryHelper$TileCollector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecTileQueryHelper.TileCollector tileCollector = this.f$0;
                        SecTileQueryHelper secTileQueryHelper = SecTileQueryHelper.this;
                        secTileQueryHelper.getClass();
                        secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda1(secTileQueryHelper, false, new ArrayList(secTileQueryHelper.mTiles)));
                        secTileQueryHelper.mBgExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, tileCollector.mQSHost));
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Callback
        public final void onStateChanged(QSTile.State state) {
            ArrayList arrayList = (ArrayList) this.mQSTileList;
            int size = arrayList.size();
            boolean z = false;
            boolean z2 = true;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                TilePair tilePair = (TilePair) obj;
                if (!tilePair.mReady) {
                    QSTile qSTile = tilePair.mTile;
                    if (qSTile.isTileReady()) {
                        qSTile.removeCallback(this);
                        qSTile.setListening(this, false);
                        tilePair.mReady = true;
                    }
                }
                if (!tilePair.mReady) {
                    z2 = false;
                }
            }
            if (!z2) {
                return;
            }
            ArrayList arrayList2 = (ArrayList) this.mQSTileList;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (true) {
                SecTileQueryHelper secTileQueryHelper = SecTileQueryHelper.this;
                if (i2 >= size2) {
                    secTileQueryHelper.getClass();
                    secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda1(secTileQueryHelper, z, new ArrayList(secTileQueryHelper.mTiles)));
                    secTileQueryHelper.mBgExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, this.mQSHost));
                    return;
                }
                Object obj2 = arrayList2.get(i2);
                i2++;
                QSTile qSTile2 = ((TilePair) obj2).mTile;
                QSTile.State stateCopy = qSTile2.getState().copy();
                stateCopy.label = qSTile2.getTileLabel();
                qSTile2.destroy();
                secTileQueryHelper.addTile(qSTile2.getTileSpec(), null, stateCopy, true);
            }
        }
    }

    public class TileInfo {
        public boolean isActive;
        public String spec;
        public QSTile.State state;

        public TileInfo() {
        }

        public TileInfo(String str, QSTile.State state, boolean z) {
            this.spec = str;
            this.state = state;
        }
    }

    public class TilePair {
        public boolean mReady;
        public final QSTile mTile;

        public /* synthetic */ TilePair(QSTile qSTile, int i) {
            this(qSTile);
        }

        private TilePair(QSTile qSTile) {
            this.mReady = false;
            this.mTile = qSTile;
        }
    }

    public SecTileQueryHelper(Context context, UserTracker userTracker, Executor executor, Executor executor2) {
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mUserTracker = userTracker;
    }

    public final void addTile(String str, CharSequence charSequence, QSTile.State state, boolean z) {
        if (this.mSpecs.contains(str)) {
            return;
        }
        state.dualTarget = false;
        state.expandedAccessibilityClassName = Button.class.getName();
        if (z || TextUtils.equals(state.label, charSequence)) {
            charSequence = null;
        }
        state.secondaryLabel = charSequence;
        this.mTiles.add(new TileInfo(str, state, z));
        this.mSpecs.add(str);
    }
}
