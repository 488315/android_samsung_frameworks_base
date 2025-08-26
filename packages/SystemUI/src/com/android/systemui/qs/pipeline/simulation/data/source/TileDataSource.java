package com.android.systemui.qs.pipeline.simulation.data.source;

/* loaded from: classes2.dex */
public interface TileDataSource {
    public static final Companion Companion = Companion.$$INSTANCE;

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
