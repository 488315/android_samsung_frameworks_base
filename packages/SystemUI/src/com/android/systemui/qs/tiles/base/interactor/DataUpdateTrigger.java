package com.android.systemui.qs.tiles.base.interactor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DataUpdateTrigger {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class UserInput implements DataUpdateTrigger {
        public final QSTileInput input;

        public UserInput(QSTileInput qSTileInput) {
            this.input = qSTileInput;
        }
    }
}
