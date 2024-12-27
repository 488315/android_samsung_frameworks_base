package com.android.systemui.qs.tiles.impl.fontscaling.domain.model;

public final class FontScalingTileModel {
    public static final FontScalingTileModel INSTANCE = new FontScalingTileModel();

    private FontScalingTileModel() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof FontScalingTileModel);
    }

    public final int hashCode() {
        return 1237191140;
    }

    public final String toString() {
        return "FontScalingTileModel";
    }
}
