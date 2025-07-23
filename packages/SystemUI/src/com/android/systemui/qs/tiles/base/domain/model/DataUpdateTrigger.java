package com.android.systemui.qs.tiles.base.domain.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DataUpdateTrigger {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ForceUpdate implements DataUpdateTrigger {
        public static final ForceUpdate INSTANCE = new ForceUpdate();

        private ForceUpdate() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ForceUpdate);
        }

        public final int hashCode() {
            return 2037607043;
        }

        public final String toString() {
            return "ForceUpdate";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InitialRequest implements DataUpdateTrigger {
        public static final InitialRequest INSTANCE = new InitialRequest();

        private InitialRequest() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof InitialRequest);
        }

        public final int hashCode() {
            return 645352796;
        }

        public final String toString() {
            return "InitialRequest";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UserInput implements DataUpdateTrigger {
        public final QSTileInput input;

        public UserInput(QSTileInput qSTileInput) {
            this.input = qSTileInput;
        }
    }
}
