package com.android.systemui.log.echo;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface Outcome {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Failure implements Outcome {
        public final String message;

        public Failure(String str) {
            this.message = str;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Success implements Outcome {
        public final Object value;

        public Success(Object obj) {
            this.value = obj;
        }
    }
}
