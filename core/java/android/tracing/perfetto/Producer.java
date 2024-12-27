package android.tracing.perfetto;

public class Producer {
    private static native void nativePerfettoProducerInit(int i, int i2);

    public static void init(InitArguments args) {
        nativePerfettoProducerInit(args.backends, args.shmemSizeHintKb);
    }
}
