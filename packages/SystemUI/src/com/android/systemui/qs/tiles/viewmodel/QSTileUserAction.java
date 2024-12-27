package com.android.systemui.qs.tiles.viewmodel;

import com.android.systemui.animation.Expandable;

public interface QSTileUserAction {

    public final class Click implements QSTileUserAction {
        public final Expandable expandable;

        public Click(Expandable expandable) {
            this.expandable = expandable;
        }

        @Override // com.android.systemui.qs.tiles.viewmodel.QSTileUserAction
        public final Expandable getExpandable() {
            return this.expandable;
        }
    }

    public final class LongClick implements QSTileUserAction {
        public final Expandable expandable;

        public LongClick(Expandable expandable) {
            this.expandable = expandable;
        }

        @Override // com.android.systemui.qs.tiles.viewmodel.QSTileUserAction
        public final Expandable getExpandable() {
            return this.expandable;
        }
    }

    Expandable getExpandable();
}
