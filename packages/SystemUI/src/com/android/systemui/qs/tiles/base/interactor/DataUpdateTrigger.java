package com.android.systemui.qs.tiles.base.interactor;

public interface DataUpdateTrigger {

    public final class ForceUpdate implements DataUpdateTrigger {
        public static final ForceUpdate INSTANCE = new ForceUpdate();

        private ForceUpdate() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ForceUpdate);
        }

        public final int hashCode() {
            return 1800360925;
        }

        public final String toString() {
            return "ForceUpdate";
        }
    }

    public final class InitialRequest implements DataUpdateTrigger {
        public static final InitialRequest INSTANCE = new InitialRequest();

        private InitialRequest() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof InitialRequest);
        }

        public final int hashCode() {
            return -1932546622;
        }

        public final String toString() {
            return "InitialRequest";
        }
    }

    public final class UserInput implements DataUpdateTrigger {
        public final QSTileInput input;

        public UserInput(QSTileInput qSTileInput) {
            this.input = qSTileInput;
        }
    }
}
