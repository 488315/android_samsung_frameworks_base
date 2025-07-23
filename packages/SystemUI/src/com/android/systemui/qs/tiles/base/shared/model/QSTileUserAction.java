package com.android.systemui.qs.tiles.base.shared.model;

import com.android.systemui.animation.Expandable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QSTileUserAction {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Click implements QSTileUserAction {
        public final Expandable expandable;

        public Click(Expandable expandable) {
            this.expandable = expandable;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LongClick implements QSTileUserAction {
        public final Expandable expandable;

        public LongClick(Expandable expandable) {
            this.expandable = expandable;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ToggleClick implements QSTileUserAction {
        public ToggleClick(Expandable expandable) {
        }
    }
}
