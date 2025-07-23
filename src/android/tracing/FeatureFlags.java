package android.tracing;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean clientSideProtoLogging();

    boolean perfettoIme();

    boolean perfettoImeTracing();

    boolean perfettoProtologTracing();

    boolean perfettoTransitionTracing();

    boolean perfettoViewCaptureTracing();

    boolean perfettoWmDump();

    boolean perfettoWmDumpCts();

    boolean perfettoWmTracing();

    boolean systemServerLargePerfettoShmemBuffer();
}
