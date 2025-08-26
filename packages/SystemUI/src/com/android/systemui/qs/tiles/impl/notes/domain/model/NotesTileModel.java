package com.android.systemui.qs.tiles.impl.notes.domain.model;

/* loaded from: classes2.dex */
public final class NotesTileModel {
    public static final NotesTileModel INSTANCE = new NotesTileModel();

    private NotesTileModel() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof NotesTileModel);
    }

    public final int hashCode() {
        return 62191396;
    }

    public final String toString() {
        return "NotesTileModel";
    }
}
