package androidx.compose.ui.platform.coreshims;

import android.view.ViewStructure;

/* loaded from: classes.dex */
public class ViewStructureCompat {
    public final Object mWrappedObj;

    private ViewStructureCompat(ViewStructure viewStructure) {
        this.mWrappedObj = viewStructure;
    }

    public static ViewStructureCompat toViewStructureCompat(ViewStructure viewStructure) {
        return new ViewStructureCompat(viewStructure);
    }
}
