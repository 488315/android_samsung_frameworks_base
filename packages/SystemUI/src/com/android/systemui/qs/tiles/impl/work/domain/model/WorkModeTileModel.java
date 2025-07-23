package com.android.systemui.qs.tiles.impl.work.domain.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface WorkModeTileModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HasActiveProfile implements WorkModeTileModel {
        public final boolean isEnabled;

        public HasActiveProfile(boolean z) {
            this.isEnabled = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof HasActiveProfile) && this.isEnabled == ((HasActiveProfile) obj).isEnabled;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isEnabled);
        }

        public final String toString() {
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("HasActiveProfile(isEnabled="), this.isEnabled, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NoActiveProfile implements WorkModeTileModel {
        public static final NoActiveProfile INSTANCE = new NoActiveProfile();

        private NoActiveProfile() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoActiveProfile);
        }

        public final int hashCode() {
            return -872501935;
        }

        public final String toString() {
            return "NoActiveProfile";
        }
    }
}
