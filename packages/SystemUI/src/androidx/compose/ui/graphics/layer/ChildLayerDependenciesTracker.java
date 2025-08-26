package androidx.compose.ui.graphics.layer;

import androidx.collection.MutableScatterSet;

/* loaded from: classes.dex */
public final class ChildLayerDependenciesTracker {
    public MutableScatterSet dependenciesSet;
    public GraphicsLayer dependency;
    public MutableScatterSet oldDependenciesSet;
    public GraphicsLayer oldDependency;
    public boolean trackingInProgress;
}
