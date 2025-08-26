package androidx.compose.ui.graphics.vector;

/* loaded from: classes.dex */
public interface VectorConfig {
    default Object getOrDefault(VectorProperty vectorProperty, Object obj) {
        return obj;
    }
}
