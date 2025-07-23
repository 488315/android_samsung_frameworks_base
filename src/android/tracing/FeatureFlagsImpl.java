package android.tracing;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.tracing.FeatureFlags
    public boolean clientSideProtoLogging() {
        return false;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoIme() {
        return true;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoImeTracing() {
        return false;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoProtologTracing() {
        return true;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoTransitionTracing() {
        return true;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoViewCaptureTracing() {
        return true;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoWmDump() {
        return false;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoWmDumpCts() {
        return false;
    }

    @Override // android.tracing.FeatureFlags
    public boolean perfettoWmTracing() {
        return false;
    }

    @Override // android.tracing.FeatureFlags
    public boolean systemServerLargePerfettoShmemBuffer() {
        return true;
    }
}
