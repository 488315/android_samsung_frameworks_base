package androidx.compose.animation;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContentTransform {
    public final ExitTransition initialContentExit;
    public SizeTransform sizeTransform;
    public final EnterTransition targetContentEnter;
    public final MutableFloatState targetContentZIndex$delegate;

    public ContentTransform(EnterTransition enterTransition, ExitTransition exitTransition, float f, SizeTransform sizeTransform) {
        this.targetContentEnter = enterTransition;
        this.initialContentExit = exitTransition;
        this.targetContentZIndex$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this.sizeTransform = sizeTransform;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ContentTransform(androidx.compose.animation.EnterTransition r1, androidx.compose.animation.ExitTransition r2, float r3, androidx.compose.animation.SizeTransform r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 4
            if (r6 == 0) goto L5
            r3 = 0
        L5:
            r5 = r5 & 8
            if (r5 == 0) goto L14
            int r4 = androidx.compose.animation.AnimatedContentKt.$r8$clinit
            androidx.compose.animation.AnimatedContentKt$SizeTransform$1 r4 = new kotlin.jvm.functions.Function2() { // from class: androidx.compose.animation.AnimatedContentKt$SizeTransform$1
                static {
                    /*
                        androidx.compose.animation.AnimatedContentKt$SizeTransform$1 r0 = new androidx.compose.animation.AnimatedContentKt$SizeTransform$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.animation.AnimatedContentKt$SizeTransform$1) androidx.compose.animation.AnimatedContentKt$SizeTransform$1.INSTANCE androidx.compose.animation.AnimatedContentKt$SizeTransform$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedContentKt$SizeTransform$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 2
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedContentKt$SizeTransform$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object r2, java.lang.Object r3) {
                    /*
                        r1 = this;
                        androidx.compose.ui.unit.IntSize r2 = (androidx.compose.ui.unit.IntSize) r2
                        long r1 = r2.packedValue
                        androidx.compose.ui.unit.IntSize r3 = (androidx.compose.ui.unit.IntSize) r3
                        long r1 = r3.packedValue
                        androidx.compose.ui.unit.IntSize$Companion r1 = androidx.compose.ui.unit.IntSize.Companion
                        long r1 = androidx.compose.animation.core.VisibilityThresholdsKt.getVisibilityThreshold$3()
                        androidx.compose.ui.unit.IntSize r1 = androidx.compose.ui.unit.IntSize.m859boximpl(r1)
                        r2 = 1137180672(0x43c80000, float:400.0)
                        r3 = 1
                        r0 = 0
                        androidx.compose.animation.core.SpringSpec r1 = androidx.compose.animation.core.AnimationSpecKt.spring$default(r0, r2, r1, r3)
                        return r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedContentKt$SizeTransform$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }
            androidx.compose.animation.SizeTransformImpl r5 = new androidx.compose.animation.SizeTransformImpl
            r6 = 1
            r5.<init>(r6, r4)
            r4 = r5
        L14:
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.ContentTransform.<init>(androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, float, androidx.compose.animation.SizeTransform, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
