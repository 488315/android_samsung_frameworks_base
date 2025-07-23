package androidx.compose.ui.graphics.layer;

import androidx.collection.MutableScatterSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChildLayerDependenciesTracker {
    public MutableScatterSet dependenciesSet;
    public GraphicsLayer dependency;
    public MutableScatterSet oldDependenciesSet;
    public GraphicsLayer oldDependency;
    public boolean trackingInProgress;
}
