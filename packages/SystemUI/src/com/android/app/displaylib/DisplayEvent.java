package com.android.app.displaylib;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface DisplayEvent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Added implements DisplayEvent {
        public final int displayId;

        public Added(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Added) && this.displayId == ((Added) obj).displayId;
        }

        @Override // com.android.app.displaylib.DisplayEvent
        public final int getDisplayId() {
            return this.displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Added(displayId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Changed implements DisplayEvent {
        public final int displayId;

        public Changed(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Changed) && this.displayId == ((Changed) obj).displayId;
        }

        @Override // com.android.app.displaylib.DisplayEvent
        public final int getDisplayId() {
            return this.displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Changed(displayId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Removed implements DisplayEvent {
        public final int displayId;

        public Removed(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Removed) && this.displayId == ((Removed) obj).displayId;
        }

        @Override // com.android.app.displaylib.DisplayEvent
        public final int getDisplayId() {
            return this.displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Removed(displayId="));
        }
    }

    int getDisplayId();
}
