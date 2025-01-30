package androidx.transition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Scene {
    public final View mLayout;
    public final ViewGroup mSceneRoot;

    public Scene(ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
    }

    private Scene(ViewGroup viewGroup, int i, Context context) {
        this.mSceneRoot = viewGroup;
    }

    public Scene(ViewGroup viewGroup, View view) {
        this.mSceneRoot = viewGroup;
        this.mLayout = view;
    }
}
