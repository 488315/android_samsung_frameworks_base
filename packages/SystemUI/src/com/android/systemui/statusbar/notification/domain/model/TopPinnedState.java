package com.android.systemui.statusbar.notification.domain.model;

import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface TopPinnedState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NothingPinned implements TopPinnedState {
        public static final NothingPinned INSTANCE = new NothingPinned();

        private NothingPinned() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NothingPinned);
        }

        public final int hashCode() {
            return -1049767245;
        }

        public final String toString() {
            return "NothingPinned";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Pinned implements TopPinnedState {
        public final String key;
        public final PinnedStatus status;

        public Pinned(String str, PinnedStatus pinnedStatus) {
            this.key = str;
            this.status = pinnedStatus;
            if (!pinnedStatus.isPinned()) {
                throw new IllegalStateException("Check failed.");
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Pinned)) {
                return false;
            }
            Pinned pinned = (Pinned) obj;
            return Intrinsics.areEqual(this.key, pinned.key) && this.status == pinned.status;
        }

        public final int hashCode() {
            return this.status.hashCode() + (this.key.hashCode() * 31);
        }

        public final String toString() {
            return "Pinned(key=" + this.key + ", status=" + this.status + ")";
        }
    }
}
