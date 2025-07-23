package com.android.systemui.log.echo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface Outcome {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Failure implements Outcome {
        public final String message;

        public Failure(String str) {
            this.message = str;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Success implements Outcome {
        public final Object value;

        public Success(Object obj) {
            this.value = obj;
        }
    }
}
