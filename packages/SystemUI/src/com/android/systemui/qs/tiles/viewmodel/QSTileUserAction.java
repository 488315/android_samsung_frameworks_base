package com.android.systemui.qs.tiles.viewmodel;

import com.android.systemui.animation.Expandable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface QSTileUserAction {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
