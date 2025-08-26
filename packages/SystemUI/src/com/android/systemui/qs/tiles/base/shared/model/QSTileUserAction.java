package com.android.systemui.qs.tiles.base.shared.model;

import com.android.systemui.animation.Expandable;

/* loaded from: classes2.dex */
public interface QSTileUserAction {

    public final class Click implements QSTileUserAction {
        public final Expandable expandable;

        public Click(Expandable expandable) {
            this.expandable = expandable;
        }
    }

    public final class LongClick implements QSTileUserAction {
        public final Expandable expandable;

        public LongClick(Expandable expandable) {
            this.expandable = expandable;
        }
    }

    public final class ToggleClick implements QSTileUserAction {
        public ToggleClick(Expandable expandable) {
        }
    }
}
