package com.android.systemui.log.echo;

public interface Outcome {

    public final class Failure implements Outcome {
        public final String message;

        public Failure(String str) {
            this.message = str;
        }
    }

    public final class Success implements Outcome {
        public final Object value;

        public Success(Object obj) {
            this.value = obj;
        }
    }
}
