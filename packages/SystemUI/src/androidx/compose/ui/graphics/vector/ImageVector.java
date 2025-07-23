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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final void m565addPathoIyEayM(String str, List list, int i, Brush brush, float f, Brush brush2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7) {
            if (this.isConsumed) {
                InlineClassHelperKt.throwIllegalStateException("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
            }
            ((GroupParams) AlertController$$ExternalSyntheticOutline0.m(this.nodes, 1)).children.add(new VectorPath(str, list, i, brush, f, brush2, f2, f3, i2, i3, f4, f5, f6, f7, null));
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
            ((GroupParams) AlertController$$ExternalSyntheticOutline0.m(this.nodes, 1)).children.add(new VectorGroup(groupParams.name, groupParams.rotate, groupParams.pivotX, groupParams.pivotY, groupParams.scaleX, groupParams.scaleY, groupParams.translationX, groupParams.translationY, groupParams.clipPathData, groupParams.children));
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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Builder(java.lang.String r12, float r13, float r14, float r15, float r16, long r17, int r19, boolean r20, int r21, kotlin.jvm.internal.DefaultConstructorMarker r22) {
            /*
                r11 = this;
                r0 = r21
                r1 = r0 & 1
                if (r1 == 0) goto L8
                java.lang.String r12 = ""
            L8:
                r1 = r12
                r12 = r0 & 32
                if (r12 == 0) goto L16
                androidx.compose.ui.graphics.Color$Companion r12 = androidx.compose.ui.graphics.Color.Companion
                r12.getClass()
                long r2 = androidx.compose.ui.graphics.Color.Unspecified
                r6 = r2
                goto L18
            L16:
                r6 = r17
            L18:
                r12 = r0 & 64
                if (r12 == 0) goto L25
                androidx.compose.ui.graphics.BlendMode$Companion r12 = androidx.compose.ui.graphics.BlendMode.Companion
                r12.getClass()
                int r12 = androidx.compose.ui.graphics.BlendMode.SrcIn
                r8 = r12
                goto L27
            L25:
                r8 = r19
            L27:
                r12 = r0 & 128(0x80, float:1.8E-43)
                if (r12 == 0) goto L2e
                r12 = 0
                r9 = r12
                goto L30
            L2e:
                r9 = r20
            L30:
                r10 = 0
                r0 = r11
                r2 = r13
                r3 = r14
                r4 = r15
                r5 = r16
                r0.<init>(r1, r2, r3, r4, r5, r6, r8, r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.ImageVector.Builder.<init>(java.lang.String, float, float, float, float, long, int, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Builder(java.lang.String r11, float r12, float r13, float r14, float r15, long r16, int r18, int r19, kotlin.jvm.internal.DefaultConstructorMarker r20) {
            /*
                r10 = this;
                r0 = r19 & 1
                if (r0 == 0) goto L6
                java.lang.String r11 = ""
            L6:
                r1 = r11
                r11 = r19 & 32
                if (r11 == 0) goto L14
                androidx.compose.ui.graphics.Color$Companion r11 = androidx.compose.ui.graphics.Color.Companion
                r11.getClass()
                long r2 = androidx.compose.ui.graphics.Color.Unspecified
                r6 = r2
                goto L16
            L14:
                r6 = r16
            L16:
                r11 = r19 & 64
                if (r11 == 0) goto L23
                androidx.compose.ui.graphics.BlendMode$Companion r11 = androidx.compose.ui.graphics.BlendMode.Companion
                r11.getClass()
                int r11 = androidx.compose.ui.graphics.BlendMode.SrcIn
                r8 = r11
                goto L25
            L23:
                r8 = r18
            L25:
                r9 = 0
                r0 = r10
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r0.<init>(r1, r2, r3, r4, r5, r6, r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.ImageVector.Builder.<init>(java.lang.String, float, float, float, float, long, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        private Builder(String str, float f, float f2, float f3, float f4, long j, int i) {
            this(str, f, f2, f3, f4, j, i, false, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (!Intrinsics.areEqual(this.name, imageVector.name) || !Dp.m836equalsimpl0(this.defaultWidth, imageVector.defaultWidth) || !Dp.m836equalsimpl0(this.defaultHeight, imageVector.defaultHeight) || this.viewportWidth != imageVector.viewportWidth || this.viewportHeight != imageVector.viewportHeight || !Intrinsics.areEqual(this.root, imageVector.root)) {
            return false;
        }
        long j = imageVector.tintColor;
        Color.Companion companion = Color.Companion;
        if (!ULong.m3427equalsimpl0(this.tintColor, j)) {
            return false;
        }
        int i = imageVector.tintBlendMode;
        BlendMode.Companion companion2 = BlendMode.Companion;
        return this.tintBlendMode == i && this.autoMirror == imageVector.autoMirror;
    }

    public final int hashCode() {
        int hashCode = this.name.hashCode() * 31;
        Dp.Companion companion = Dp.Companion;
        int hashCode2 = (this.root.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.viewportHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.viewportWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.defaultHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.defaultWidth, hashCode, 31), 31), 31), 31)) * 31;
        Color.Companion companion2 = Color.Companion;
        int i = ULong.$r8$clinit;
        int m = MoveResult$$ExternalSyntheticOutline0.m(hashCode2, 31, this.tintColor);
        BlendMode.Companion companion3 = BlendMode.Companion;
        return Boolean.hashCode(this.autoMirror) + ReorderTile$$ExternalSyntheticOutline0.m(this.tintBlendMode, m, 31);
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ImageVector(java.lang.String r16, float r17, float r18, float r19, float r20, androidx.compose.ui.graphics.vector.VectorGroup r21, long r22, int r24, boolean r25, int r26, int r27, kotlin.jvm.internal.DefaultConstructorMarker r28) {
        /*
            r15 = this;
            r0 = r27
            r0 = r0 & 512(0x200, float:7.17E-43)
            if (r0 == 0) goto L1b
            androidx.compose.ui.graphics.vector.ImageVector$Companion r0 = androidx.compose.ui.graphics.vector.ImageVector.Companion
            r0.getClass()
            androidx.compose.ui.graphics.vector.ImageVector$Companion r1 = androidx.compose.ui.graphics.vector.ImageVector.lock
            monitor-enter(r1)
            int r0 = androidx.compose.ui.graphics.vector.ImageVector.imageVectorCount     // Catch: java.lang.Throwable -> L17
            int r2 = r0 + 1
            androidx.compose.ui.graphics.vector.ImageVector.imageVectorCount = r2     // Catch: java.lang.Throwable -> L17
            monitor-exit(r1)
            r13 = r0
            goto L1d
        L17:
            r0 = move-exception
            r15 = r0
            monitor-exit(r1)
            throw r15
        L1b:
            r13 = r26
        L1d:
            r14 = 0
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r7 = r20
            r8 = r21
            r9 = r22
            r11 = r24
            r12 = r25
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r11, r12, r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.ImageVector.<init>(java.lang.String, float, float, float, float, androidx.compose.ui.graphics.vector.VectorGroup, long, int, boolean, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
