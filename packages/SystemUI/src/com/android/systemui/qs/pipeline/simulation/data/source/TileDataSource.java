package com.android.systemui.qs.pipeline.simulation.data.source;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface TileDataSource {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String LOCAL = "local";
        public static final String REMOTE = "remote";

        private Companion() {
        }
    }

    boolean getQqsEdited();

    String getQqsTiles();

    boolean getQsEdited();

    String getRemovedTiles();

    String getSepVersion();

    String getTiles();
}
