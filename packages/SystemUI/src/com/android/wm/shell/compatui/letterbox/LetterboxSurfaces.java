package com.android.wm.shell.compatui.letterbox;

import android.view.SurfaceControl;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes3.dex */
public final class LetterboxSurfaces implements Iterable, KMappedMarker {
    public final SurfaceControl bottomSurface;
    public final SurfaceControl leftSurface;
    public final SurfaceControl rightSurface;
    public final SurfaceControl topSurface;

    public LetterboxSurfaces() {
        this(null, null, null, null, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LetterboxSurfaces)) {
            return false;
        }
        LetterboxSurfaces letterboxSurfaces = (LetterboxSurfaces) obj;
        return Intrinsics.areEqual(this.leftSurface, letterboxSurfaces.leftSurface) && Intrinsics.areEqual(this.topSurface, letterboxSurfaces.topSurface) && Intrinsics.areEqual(this.rightSurface, letterboxSurfaces.rightSurface) && Intrinsics.areEqual(this.bottomSurface, letterboxSurfaces.bottomSurface);
    }

    public final int hashCode() {
        SurfaceControl surfaceControl = this.leftSurface;
        int iHashCode = (surfaceControl == null ? 0 : surfaceControl.hashCode()) * 31;
        SurfaceControl surfaceControl2 = this.topSurface;
        int iHashCode2 = (iHashCode + (surfaceControl2 == null ? 0 : surfaceControl2.hashCode())) * 31;
        SurfaceControl surfaceControl3 = this.rightSurface;
        int iHashCode3 = (iHashCode2 + (surfaceControl3 == null ? 0 : surfaceControl3.hashCode())) * 31;
        SurfaceControl surfaceControl4 = this.bottomSurface;
        return iHashCode3 + (surfaceControl4 != null ? surfaceControl4.hashCode() : 0);
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return Arrays.asList(this.leftSurface, this.topSurface, this.rightSurface, this.bottomSurface).iterator();
    }

    public final String toString() {
        return "LetterboxSurfaces(leftSurface=" + this.leftSurface + ", topSurface=" + this.topSurface + ", rightSurface=" + this.rightSurface + ", bottomSurface=" + this.bottomSurface + ")";
    }

    public LetterboxSurfaces(SurfaceControl surfaceControl, SurfaceControl surfaceControl2, SurfaceControl surfaceControl3, SurfaceControl surfaceControl4) {
        this.leftSurface = surfaceControl;
        this.topSurface = surfaceControl2;
        this.rightSurface = surfaceControl3;
        this.bottomSurface = surfaceControl4;
    }

    public /* synthetic */ LetterboxSurfaces(SurfaceControl surfaceControl, SurfaceControl surfaceControl2, SurfaceControl surfaceControl3, SurfaceControl surfaceControl4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : surfaceControl, (i & 2) != 0 ? null : surfaceControl2, (i & 4) != 0 ? null : surfaceControl3, (i & 8) != 0 ? null : surfaceControl4);
    }
}
