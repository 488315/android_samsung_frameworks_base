package androidx.compose.ui.graphics.vector;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Dp;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ImageVector {
    public static final Companion Companion;
    public static int imageVectorCount;
    public static final Companion lock;
    public final boolean autoMirror;
    public final float defaultHeight;
    public final float defaultWidth;
    public final int genId;
    public final String name;
    public final VectorGroup root;
    public final int tintBlendMode;
    public final long tintColor;
    public final float viewportHeight;
    public final float viewportWidth;

    public final class Builder {
        public final boolean autoMirror;
        public final float defaultHeight;
        public final float defaultWidth;
        public boolean isConsumed;
        public final String name;
        public final ArrayList nodes;
        public final GroupParams root;
        public final int tintBlendMode;
        public final long tintColor;
        public final float viewportHeight;
        public final float viewportWidth;

        final class GroupParams {
            public final List children;
            public final List clipPathData;
            public final String name;
            public final float pivotX;
            public final float pivotY;
            public final float rotate;
            public final float scaleX;
            public final float scaleY;
            public final float translationX;
            public final float translationY;

            public GroupParams() {
                this(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, 1023, null);
            }

            public GroupParams(String str, float f, float f2, float f3, float f4, float f5, float f6, float f7, List<? extends PathNode> list, List<VectorNode> list2) {
                this.name = str;
                this.rotate = f;
                this.pivotX = f2;
                this.pivotY = f3;
                this.scaleX = f4;
                this.scaleY = f5;
                this.translationX = f6;
                this.translationY = f7;
                this.clipPathData = list;
                this.children = list2;
            }

            public GroupParams(String str, float f, float f2, float f3, float f4, float f5, float f6, float f7, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? 0.0f : f, (i & 4) != 0 ? 0.0f : f2, (i & 8) != 0 ? 0.0f : f3, (i & 16) != 0 ? 1.0f : f4, (i & 32) != 0 ? 1.0f : f5, (i & 64) != 0 ? 0.0f : f6, (i & 128) != 0 ? 0.0f : f7, (i & 256) != 0 ? VectorKt.EmptyPath : list, (i & 512) != 0 ? new ArrayList() : list2);
            }
        }

        public /* synthetic */ Builder(String str, float f, float f2, float f3, float f4, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, f, f2, f3, f4, j, i);
        }

        public final void addGroup(String str, float f, float f2, float f3, float f4, float f5, float f6, float f7, List list) {
            if (this.isConsumed) {
                InlineClassHelperKt.throwIllegalStateException("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
            }
            this.nodes.add(new GroupParams(str, f, f2, f3, f4, f5, f6, f7, list, null, 512, null));
        }

        /* renamed from: addPath-oIyEayM, reason: not valid java name */
        public final void m567addPathoIyEayM(String str, List list, int i, Brush brush, float f, Brush brush2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7) {
            if (this.isConsumed) {
                InlineClassHelperKt.throwIllegalStateException("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
            }
            ((GroupParams) AlertController$$ExternalSyntheticOutline0.m(1, this.nodes)).children.add(new VectorPath(str, list, i, brush, f, brush2, f2, f3, i2, i3, f4, f5, f6, f7, null));
        }

        public final ImageVector build() {
            if (this.isConsumed) {
                InlineClassHelperKt.throwIllegalStateException("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
            }
            while (this.nodes.size() > 1) {
                clearGroup();
            }
            GroupParams groupParams = this.root;
            ImageVector imageVector = new ImageVector(this.name, this.defaultWidth, this.defaultHeight, this.viewportWidth, this.viewportHeight, new VectorGroup(groupParams.name, groupParams.rotate, groupParams.pivotX, groupParams.pivotY, groupParams.scaleX, groupParams.scaleY, groupParams.translationX, groupParams.translationY, groupParams.clipPathData, groupParams.children), this.tintColor, this.tintBlendMode, this.autoMirror, 0, 512, null);
            this.isConsumed = true;
            return imageVector;
        }

        public final void clearGroup() {
            if (this.isConsumed) {
                InlineClassHelperKt.throwIllegalStateException("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
            }
            ArrayList arrayList = this.nodes;
            GroupParams groupParams = (GroupParams) arrayList.remove(arrayList.size() - 1);
            ((GroupParams) AlertController$$ExternalSyntheticOutline0.m(1, this.nodes)).children.add(new VectorGroup(groupParams.name, groupParams.rotate, groupParams.pivotX, groupParams.pivotY, groupParams.scaleX, groupParams.scaleY, groupParams.translationX, groupParams.translationY, groupParams.clipPathData, groupParams.children));
        }

        public /* synthetic */ Builder(String str, float f, float f2, float f3, float f4, long j, int i, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, f, f2, f3, f4, j, i, z);
        }

        private Builder(String str, float f, float f2, float f3, float f4, long j, int i, boolean z) {
            this.name = str;
            this.defaultWidth = f;
            this.defaultHeight = f2;
            this.viewportWidth = f3;
            this.viewportHeight = f4;
            this.tintColor = j;
            this.tintBlendMode = i;
            this.autoMirror = z;
            ArrayList arrayList = new ArrayList();
            this.nodes = arrayList;
            GroupParams groupParams = new GroupParams(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, 1023, null);
            this.root = groupParams;
            arrayList.add(groupParams);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Builder(String str, float f, float f2, float f3, float f4, long j, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            long j2;
            int i3;
            String str2 = (i2 & 1) != 0 ? "" : str;
            if ((i2 & 32) != 0) {
                Color.Companion.getClass();
                j2 = Color.Unspecified;
            } else {
                j2 = j;
            }
            if ((i2 & 64) != 0) {
                BlendMode.Companion.getClass();
                i3 = BlendMode.SrcIn;
            } else {
                i3 = i;
            }
            this(str2, f, f2, f3, f4, j2, i3, (i2 & 128) != 0 ? false : z, (DefaultConstructorMarker) null);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Builder(String str, float f, float f2, float f3, float f4, long j, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            long j2;
            int i3;
            String str2 = (i2 & 1) != 0 ? "" : str;
            if ((i2 & 32) != 0) {
                Color.Companion.getClass();
                j2 = Color.Unspecified;
            } else {
                j2 = j;
            }
            if ((i2 & 64) != 0) {
                BlendMode.Companion.getClass();
                i3 = BlendMode.SrcIn;
            } else {
                i3 = i;
            }
            this(str2, f, f2, f3, f4, j2, i3, (DefaultConstructorMarker) null);
        }

        private Builder(String str, float f, float f2, float f3, float f4, long j, int i) {
            this(str, f, f2, f3, f4, j, i, false, (DefaultConstructorMarker) null);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        lock = companion;
    }

    public /* synthetic */ ImageVector(String str, float f, float f2, float f3, float f4, VectorGroup vectorGroup, long j, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, f, f2, f3, f4, vectorGroup, j, i, z, i2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImageVector)) {
            return false;
        }
        ImageVector imageVector = (ImageVector) obj;
        if (!Intrinsics.areEqual(this.name, imageVector.name) || !Dp.m838equalsimpl0(this.defaultWidth, imageVector.defaultWidth) || !Dp.m838equalsimpl0(this.defaultHeight, imageVector.defaultHeight) || this.viewportWidth != imageVector.viewportWidth || this.viewportHeight != imageVector.viewportHeight || !Intrinsics.areEqual(this.root, imageVector.root)) {
            return false;
        }
        long j = imageVector.tintColor;
        Color.Companion companion = Color.Companion;
        if (!ULong.m3446equalsimpl0(this.tintColor, j)) {
            return false;
        }
        int i = imageVector.tintBlendMode;
        BlendMode.Companion companion2 = BlendMode.Companion;
        return this.tintBlendMode == i && this.autoMirror == imageVector.autoMirror;
    }

    public final int hashCode() {
        int iHashCode = this.name.hashCode() * 31;
        Dp.Companion companion = Dp.Companion;
        int iHashCode2 = (this.root.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.viewportHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.viewportWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.defaultHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.defaultWidth, iHashCode, 31), 31), 31), 31)) * 31;
        Color.Companion companion2 = Color.Companion;
        int i = ULong.$r8$clinit;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(iHashCode2, 31, this.tintColor);
        BlendMode.Companion companion3 = BlendMode.Companion;
        return Boolean.hashCode(this.autoMirror) + ReorderTile$$ExternalSyntheticOutline0.m(this.tintBlendMode, iM, 31);
    }

    private ImageVector(String str, float f, float f2, float f3, float f4, VectorGroup vectorGroup, long j, int i, boolean z, int i2) {
        this.name = str;
        this.defaultWidth = f;
        this.defaultHeight = f2;
        this.viewportWidth = f3;
        this.viewportHeight = f4;
        this.root = vectorGroup;
        this.tintColor = j;
        this.tintBlendMode = i;
        this.autoMirror = z;
        this.genId = i2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ImageVector(String str, float f, float f2, float f3, float f4, VectorGroup vectorGroup, long j, int i, boolean z, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        int i4;
        int i5;
        if ((i3 & 512) != 0) {
            Companion.getClass();
            synchronized (lock) {
                i5 = imageVectorCount;
                imageVectorCount = i5 + 1;
            }
            i4 = i5;
        } else {
            i4 = i2;
        }
        this(str, f, f2, f3, f4, vectorGroup, j, i, z, i4, null);
    }
}
