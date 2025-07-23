package com.android.wm.shell.compatui.letterbox;

import android.view.SurfaceControl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxSurfaceBuilder {
    public static final int TASK_CHILD_LAYER_LETTERBOX_BACKGROUND;
    public final LetterboxConfiguration letterboxConfiguration;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        SurfaceControl build = builder.setName(str).setHidden(true).setColorLayer().setParent(surfaceControl).setCallsite(str2).build();
        transaction.setLayer(build, TASK_CHILD_LAYER_LETTERBOX_BACKGROUND).setColorSpaceAgnostic(build, true).setColor(build, letterboxSurfaceBuilder.letterboxConfiguration.getLetterboxBackgroundColor().getComponents());
        return build;
    }
}
