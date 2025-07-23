package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.SystemPropsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface Channel extends SendChannel, ReceiveChannel {
    public static final Factory Factory = Factory.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public static final /* synthetic */ Factory $$INSTANCE = new Factory();
        public static final int CHANNEL_DEFAULT_CAPACITY = (int) SystemPropsKt.systemProp("kotlinx.coroutines.channels.defaultBuffer", 64, 1, 2147483646);

        private Factory() {
        }
    }
}
