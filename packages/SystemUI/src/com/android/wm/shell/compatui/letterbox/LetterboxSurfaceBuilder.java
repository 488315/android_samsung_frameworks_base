package com.android.wm.shell.compatui.letterbox;

import android.view.SurfaceControl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class LetterboxSurfaceBuilder {
    public static final int TASK_CHILD_LAYER_LETTERBOX_BACKGROUND;
    public final LetterboxConfiguration letterboxConfiguration;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TASK_CHILD_LAYER_LETTERBOX_BACKGROUND = -1000;
    }

    public LetterboxSurfaceBuilder(LetterboxConfiguration letterboxConfiguration) {
        this.letterboxConfiguration = letterboxConfiguration;
    }

    public static SurfaceControl createSurface$default(LetterboxSurfaceBuilder letterboxSurfaceBuilder, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, String str, String str2) {
        SurfaceControl.Builder builder = new SurfaceControl.Builder();
        letterboxSurfaceBuilder.getClass();
        SurfaceControl surfaceControlBuild = builder.setName(str).setHidden(true).setColorLayer().setParent(surfaceControl).setCallsite(str2).build();
        transaction.setLayer(surfaceControlBuild, TASK_CHILD_LAYER_LETTERBOX_BACKGROUND).setColorSpaceAgnostic(surfaceControlBuild, true).setColor(surfaceControlBuild, letterboxSurfaceBuilder.letterboxConfiguration.getLetterboxBackgroundColor().getComponents());
        return surfaceControlBuild;
    }
}
