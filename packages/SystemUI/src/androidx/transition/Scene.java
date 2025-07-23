package androidx.transition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Scene {
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
