package androidx.window.embedding;

import android.os.Bundle;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.window.embedding.EmbeddingBounds;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityEmbeddingOptionsImpl {
    public static final ActivityEmbeddingOptionsImpl INSTANCE = new ActivityEmbeddingOptionsImpl();

    private ActivityEmbeddingOptionsImpl() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public static EmbeddingBounds.Dimension getDimension(Bundle bundle, String str) {
        Bundle bundle2 = bundle.getBundle(str);
        bundle2.getClass();
        String string = bundle2.getString("androidx.window.embedding.EmbeddingBounds.dimension_type");
        if (string != null) {
            switch (string.hashCode()) {
                case -1939100487:
                    if (string.equals("expanded")) {
                        return EmbeddingBounds.Dimension.DIMENSION_EXPANDED;
                    }
                    break;
                case 99283243:
                    if (string.equals("hinge")) {
                        return EmbeddingBounds.Dimension.DIMENSION_HINGE;
                    }
                    break;
                case 106680966:
                    if (string.equals("pixel")) {
                        EmbeddingBounds.Dimension.Companion companion = EmbeddingBounds.Dimension.Companion;
                        int i = bundle2.getInt("androidx.window.embedding.EmbeddingBounds.dimension_value");
                        companion.getClass();
                        return new EmbeddingBounds.Dimension.Pixel(i);
                    }
                    break;
                case 108285963:
                    if (string.equals("ratio")) {
                        EmbeddingBounds.Dimension.Companion companion2 = EmbeddingBounds.Dimension.Companion;
                        float f = bundle2.getFloat("androidx.window.embedding.EmbeddingBounds.dimension_value");
                        companion2.getClass();
                        return new EmbeddingBounds.Dimension.Ratio(f);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Illegal type ", string));
    }
}
