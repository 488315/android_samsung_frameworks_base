package com.caverock.androidsvg;

/* loaded from: classes3.dex */
public class PreserveAspectRatio {
    public static final PreserveAspectRatio LETTERBOX;
    public static final PreserveAspectRatio STRETCH;
    public final Alignment alignment;
    public final Scale scale;

    public enum Alignment {
        none,
        xMinYMin,
        xMidYMin,
        xMaxYMin,
        xMinYMid,
        xMidYMid,
        xMaxYMid,
        xMinYMax,
        xMidYMax,
        xMaxYMax
    }

    public enum Scale {
        meet,
        slice
    }

    static {
        new PreserveAspectRatio(null, null);
        STRETCH = new PreserveAspectRatio(Alignment.none, null);
        Alignment alignment = Alignment.xMidYMid;
        Scale scale = Scale.meet;
        LETTERBOX = new PreserveAspectRatio(alignment, scale);
        Alignment alignment2 = Alignment.xMinYMin;
        new PreserveAspectRatio(alignment2, scale);
        new PreserveAspectRatio(Alignment.xMaxYMax, scale);
        new PreserveAspectRatio(Alignment.xMidYMin, scale);
        new PreserveAspectRatio(Alignment.xMidYMax, scale);
        Scale scale2 = Scale.slice;
        new PreserveAspectRatio(alignment, scale2);
        new PreserveAspectRatio(alignment2, scale2);
    }

    public PreserveAspectRatio(Alignment alignment, Scale scale) {
        this.alignment = alignment;
        this.scale = scale;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PreserveAspectRatio preserveAspectRatio = (PreserveAspectRatio) obj;
        return this.alignment == preserveAspectRatio.alignment && this.scale == preserveAspectRatio.scale;
    }

    public final String toString() {
        return this.alignment + " " + this.scale;
    }
}
