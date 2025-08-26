package androidx.compose.ui.graphics.vector;

import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class VectorComposeKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:171:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0118  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Group(String str, float f, float f2, float f3, float f4, float f5, float f6, float f7, List list, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        float f8;
        int i4;
        float f9;
        int i5;
        float f10;
        int i6;
        float f11;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        final String str2;
        final float f12;
        final float f13;
        final float f14;
        final float f15;
        final float f16;
        final List list2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        float f17;
        float f18;
        float f19;
        List list3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-213417674);
        int i13 = i2 & 1;
        if (i13 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i14 = i2 & 2;
        if (i14 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                f8 = f;
                i3 |= composerImpl.changed(f8) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    f9 = f2;
                    i3 |= composerImpl.changed(f9) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        f10 = f3;
                        i3 |= composerImpl.changed(f10) ? 2048 : 1024;
                    }
                    i6 = i2 & 16;
                    if (i6 == 0) {
                        i3 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            f11 = f4;
                            i3 |= composerImpl.changed(f11) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i7 = i2 & 32;
                        if (i7 != 0) {
                            i3 |= 196608;
                            i8 = i13;
                        } else {
                            i8 = i13;
                            if ((i & 196608) == 0) {
                                i3 |= composerImpl.changed(f5) ? 131072 : 65536;
                            }
                        }
                        i9 = i2 & 64;
                        if (i9 != 0) {
                            i3 |= 1572864;
                        } else if ((i & 1572864) == 0) {
                            i3 |= composerImpl.changed(f6) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        i10 = i2 & 128;
                        if (i10 == 0) {
                            if ((i & 12582912) == 0) {
                                i11 = i10;
                                i3 |= composerImpl.changed(f7) ? 8388608 : 4194304;
                            }
                            if ((i & 100663296) == 0) {
                                i3 |= ((i2 & 256) == 0 && composerImpl.changedInstance(list)) ? 67108864 : 33554432;
                            }
                            if ((i2 & 512) == 0) {
                                i3 |= 805306368;
                            } else if ((i & 805306368) == 0) {
                                i3 |= composerImpl.changedInstance(function2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            }
                            i12 = i3;
                            if (composerImpl.shouldExecute(i12 & 1, (306783379 & i3) == 306783378)) {
                                composerImpl.skipToGroupEnd();
                                str2 = str;
                                f12 = f5;
                                f13 = f7;
                                f14 = f9;
                                f15 = f10;
                                f16 = f6;
                                list2 = list;
                            } else {
                                composerImpl.startDefaults();
                                if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                                    str2 = i8 != 0 ? "" : str;
                                    if (i14 != 0) {
                                        f8 = 0.0f;
                                    }
                                    if (i4 != 0) {
                                        f9 = 0.0f;
                                    }
                                    if (i5 != 0) {
                                        f10 = 0.0f;
                                    }
                                    if (i6 != 0) {
                                        f11 = 1.0f;
                                    }
                                    f17 = i7 == 0 ? f5 : 1.0f;
                                    f18 = i9 != 0 ? 0.0f : f6;
                                    f19 = i11 == 0 ? f7 : 0.0f;
                                    if ((i2 & 256) != 0) {
                                        list3 = VectorKt.EmptyPath;
                                        i12 &= -234881025;
                                    } else {
                                        list3 = list;
                                    }
                                    composerImpl.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.Group (VectorCompose.kt:57)");
                                    }
                                    AnonymousClass1 anonymousClass1 = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt.Group.1
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return new GroupComponent();
                                        }
                                    };
                                    if (!(composerImpl.applier instanceof VectorApplier)) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl.startNode();
                                    if (composerImpl.inserting) {
                                        composerImpl.createNode(anonymousClass1);
                                    } else {
                                        composerImpl.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl, str2, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$1
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.name = (String) obj2;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f8), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$2
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.rotation = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f9), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$3
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.pivotX = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f10), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$4
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.pivotY = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f11), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$5
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.scaleX = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f17), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$6
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.scaleY = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f18), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$7
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.translationX = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, Float.valueOf(f19), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$8
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.translationY = ((Number) obj2).floatValue();
                                            groupComponent.isMatrixDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    Updater.m337setimpl(composerImpl, list3, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Group$2$9
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            GroupComponent groupComponent = (GroupComponent) obj;
                                            groupComponent.clipPathData = (List) obj2;
                                            groupComponent.isClipPathDirty = true;
                                            groupComponent.invalidate();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    function2.invoke(composerImpl, Integer.valueOf((i12 >> 27) & 14));
                                    composerImpl.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    f14 = f9;
                                    f16 = f18;
                                    f12 = f17;
                                    f15 = f10;
                                    list2 = list3;
                                    f13 = f19;
                                } else {
                                    composerImpl.skipToGroupEnd();
                                    if ((i2 & 256) != 0) {
                                        f17 = f5;
                                        f18 = f6;
                                        f19 = f7;
                                        list3 = list;
                                        i12 &= -234881025;
                                        str2 = str;
                                        composerImpl.endDefaults();
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        AnonymousClass1 anonymousClass12 = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt.Group.1
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return new GroupComponent();
                                            }
                                        };
                                        if (!(composerImpl.applier instanceof VectorApplier)) {
                                        }
                                    } else {
                                        str2 = str;
                                        f17 = f5;
                                        f18 = f6;
                                        f19 = f7;
                                        list3 = list;
                                        composerImpl.endDefaults();
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        AnonymousClass1 anonymousClass122 = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt.Group.1
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                return new GroupComponent();
                                            }
                                        };
                                        if (!(composerImpl.applier instanceof VectorApplier)) {
                                        }
                                    }
                                }
                            }
                            final float f20 = f8;
                            final float f21 = f11;
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt.Group.4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    /* JADX WARN: Multi-variable type inference failed */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        VectorComposeKt.Group(str2, f20, f14, f15, f21, f12, f16, f13, list2, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 12582912;
                        i11 = i10;
                        if ((i & 100663296) == 0) {
                        }
                        if ((i2 & 512) == 0) {
                        }
                        i12 = i3;
                        if (composerImpl.shouldExecute(i12 & 1, (306783379 & i3) == 306783378)) {
                        }
                        final float f202 = f8;
                        final float f212 = f11;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    f11 = f4;
                    i7 = i2 & 32;
                    if (i7 != 0) {
                    }
                    i9 = i2 & 64;
                    if (i9 != 0) {
                    }
                    i10 = i2 & 128;
                    if (i10 == 0) {
                    }
                    i11 = i10;
                    if ((i & 100663296) == 0) {
                    }
                    if ((i2 & 512) == 0) {
                    }
                    i12 = i3;
                    if (composerImpl.shouldExecute(i12 & 1, (306783379 & i3) == 306783378)) {
                    }
                    final float f2022 = f8;
                    final float f2122 = f11;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                f10 = f3;
                i6 = i2 & 16;
                if (i6 == 0) {
                }
                f11 = f4;
                i7 = i2 & 32;
                if (i7 != 0) {
                }
                i9 = i2 & 64;
                if (i9 != 0) {
                }
                i10 = i2 & 128;
                if (i10 == 0) {
                }
                i11 = i10;
                if ((i & 100663296) == 0) {
                }
                if ((i2 & 512) == 0) {
                }
                i12 = i3;
                if (composerImpl.shouldExecute(i12 & 1, (306783379 & i3) == 306783378)) {
                }
                final float f20222 = f8;
                final float f21222 = f11;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            f9 = f2;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            f10 = f3;
            i6 = i2 & 16;
            if (i6 == 0) {
            }
            f11 = f4;
            i7 = i2 & 32;
            if (i7 != 0) {
            }
            i9 = i2 & 64;
            if (i9 != 0) {
            }
            i10 = i2 & 128;
            if (i10 == 0) {
            }
            i11 = i10;
            if ((i & 100663296) == 0) {
            }
            if ((i2 & 512) == 0) {
            }
            i12 = i3;
            if (composerImpl.shouldExecute(i12 & 1, (306783379 & i3) == 306783378)) {
            }
            final float f202222 = f8;
            final float f212222 = f11;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        f8 = f;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        f9 = f2;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        f10 = f3;
        i6 = i2 & 16;
        if (i6 == 0) {
        }
        f11 = f4;
        i7 = i2 & 32;
        if (i7 != 0) {
        }
        i9 = i2 & 64;
        if (i9 != 0) {
        }
        i10 = i2 & 128;
        if (i10 == 0) {
        }
        i11 = i10;
        if ((i & 100663296) == 0) {
        }
        if ((i2 & 512) == 0) {
        }
        i12 = i3;
        if (composerImpl.shouldExecute(i12 & 1, (306783379 & i3) == 306783378)) {
        }
        final float f2022222 = f8;
        final float f2122222 = f11;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:220:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0114  */
    /* renamed from: Path-9cdaXJ4, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m568Path9cdaXJ4(final List list, int i, String str, Brush brush, float f, Brush brush2, float f2, float f3, int i2, int i3, float f4, float f5, float f6, float f7, Composer composer, final int i4, final int i5, final int i6) {
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        String str2;
        int i12;
        Brush brush3;
        int i13;
        float f8;
        int i14;
        boolean z;
        Brush brush4;
        int i15;
        int i16;
        float f9;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        final float f10;
        final int i27;
        final String str3;
        ComposerImpl composerImpl;
        final Brush brush5;
        final float f11;
        final int i28;
        final float f12;
        final float f13;
        final float f14;
        final int i29;
        final Brush brush6;
        final float f15;
        final float f16;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1478270750);
        if ((i6 & 1) != 0) {
            i7 = i4 | 6;
        } else if ((i4 & 6) == 0) {
            i7 = (composerImpl2.changedInstance(list) ? 4 : 2) | i4;
        } else {
            i7 = i4;
        }
        int i30 = i6 & 2;
        if (i30 != 0) {
            i7 |= 48;
            i8 = i;
        } else {
            i8 = i;
            if ((i4 & 48) == 0) {
                i9 = 16;
                i7 |= composerImpl2.changed(i8) ? 32 : 16;
            }
            i10 = i6 & 4;
            if (i10 == 0) {
                i7 |= 384;
                str2 = str;
                i11 = 32;
            } else {
                i11 = 32;
                if ((i4 & 384) == 0) {
                    str2 = str;
                    i7 |= composerImpl2.changed(str2) ? 256 : 128;
                } else {
                    str2 = str;
                }
            }
            i12 = i6 & 8;
            if (i12 == 0) {
                i7 |= 3072;
            } else {
                if ((i4 & 3072) == 0) {
                    brush3 = brush;
                    i7 |= composerImpl2.changed(brush3) ? 2048 : 1024;
                }
                i13 = i6 & 16;
                if (i13 != 0) {
                    i7 |= 24576;
                } else {
                    if ((i4 & 24576) == 0) {
                        f8 = f;
                        i7 |= composerImpl2.changed(f8) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i14 = i6 & 32;
                    if (i14 == 0) {
                        i7 |= 196608;
                    } else {
                        if ((i4 & 196608) == 0) {
                            z = true;
                            brush4 = brush2;
                            i7 |= composerImpl2.changed(brush4) ? 131072 : 65536;
                        }
                        i15 = i6 & 64;
                        if (i15 != 0) {
                            i7 |= 1572864;
                        } else if ((i4 & 1572864) == 0) {
                            i7 |= composerImpl2.changed(f2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        i16 = i6 & 128;
                        if (i16 != 0) {
                            i7 |= 12582912;
                            f9 = f3;
                        } else {
                            f9 = f3;
                            if ((i4 & 12582912) == 0) {
                                i7 |= composerImpl2.changed(f9) ? 8388608 : 4194304;
                            }
                        }
                        i17 = i6 & 256;
                        if (i17 != 0) {
                            i7 |= 100663296;
                            i18 = i2;
                        } else {
                            i18 = i2;
                            if ((i4 & 100663296) == 0) {
                                i7 |= composerImpl2.changed(i18) ? 67108864 : 33554432;
                            }
                        }
                        i19 = i6 & 512;
                        if (i19 != 0) {
                            i7 |= 805306368;
                            i20 = i19;
                        } else if ((i4 & 805306368) == 0) {
                            i20 = i19;
                            i7 |= composerImpl2.changed(i3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                        } else {
                            i20 = i19;
                        }
                        i21 = i6 & 1024;
                        if (i21 != 0) {
                            i22 = i5 | 6;
                        } else if ((i5 & 6) == 0) {
                            i22 = i5 | (composerImpl2.changed(f4) ? 4 : 2);
                        } else {
                            i22 = i5;
                        }
                        i23 = i6 & 2048;
                        if (i23 != 0) {
                            i22 |= 48;
                        } else if ((i5 & 48) == 0) {
                            if (composerImpl2.changed(f5)) {
                                i9 = i11;
                            }
                            i22 |= i9;
                        }
                        int i31 = i22;
                        i24 = i6 & 4096;
                        if (i24 != 0) {
                            i25 = i31 | 384;
                        } else {
                            int i32 = i31;
                            if ((i5 & 384) == 0) {
                                i32 |= composerImpl2.changed(f6) ? 256 : 128;
                            }
                            i25 = i32;
                        }
                        i26 = 8192 & i6;
                        if (i26 == 0) {
                            if ((i5 & 3072) == 0) {
                                i25 |= composerImpl2.changed(f7) ? 2048 : 1024;
                            }
                            int i33 = 0;
                            if (composerImpl2.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 || (i25 & 1171) != 1170) ? z : false)) {
                                composerImpl2.skipToGroupEnd();
                                f10 = f5;
                                i27 = i8;
                                str3 = str2;
                                composerImpl = composerImpl2;
                                brush5 = brush3;
                                f11 = f8;
                                i28 = i3;
                                f12 = f6;
                                f13 = f7;
                                f14 = f9;
                                i29 = i18;
                                brush6 = brush4;
                                f15 = f2;
                                f16 = f4;
                            } else {
                                if (i30 != 0) {
                                    EmptyList emptyList = VectorKt.EmptyPath;
                                    i8 = 0;
                                }
                                String str4 = i10 != 0 ? "" : str2;
                                if (i12 != 0) {
                                    brush3 = null;
                                }
                                if (i13 != 0) {
                                    f8 = 1.0f;
                                }
                                if (i14 != 0) {
                                    brush4 = null;
                                }
                                float f17 = i15 != 0 ? 1.0f : f2;
                                if (i16 != 0) {
                                    f9 = 0.0f;
                                }
                                if (i17 != 0) {
                                    EmptyList emptyList2 = VectorKt.EmptyPath;
                                    i18 = 0;
                                }
                                if (i20 != 0) {
                                    EmptyList emptyList3 = VectorKt.EmptyPath;
                                } else {
                                    i33 = i3;
                                }
                                float f18 = i21 != 0 ? 4.0f : f4;
                                float f19 = i23 != 0 ? 0.0f : f5;
                                float f20 = i24 == 0 ? f6 : 1.0f;
                                float f21 = i26 == 0 ? f7 : 0.0f;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.Path (VectorCompose.kt:114)");
                                }
                                VectorComposeKt$Path$1 vectorComposeKt$Path$1 = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$1
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return new PathComponent();
                                    }
                                };
                                if (!(composerImpl2.applier instanceof VectorApplier)) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl2.startNode();
                                if (composerImpl2.inserting) {
                                    composerImpl2.createNode(vectorComposeKt$Path$1);
                                } else {
                                    composerImpl2.useNode();
                                }
                                Updater.m337setimpl(composerImpl2, str4, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$1
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((PathComponent) obj).invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, list, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$2
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.pathData = (List) obj2;
                                        pathComponent.isPathDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, PathFillType.m494boximpl(i8), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$3
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.renderPath.m446setFillTypeoQ8Xj4U(((PathFillType) obj2).value);
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, brush3, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$4
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.fill = (Brush) obj2;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f8), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$5
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.fillAlpha = ((Number) obj2).floatValue();
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, brush4, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$6
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.stroke = (Brush) obj2;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f17), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$7
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.strokeAlpha = ((Number) obj2).floatValue();
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f9), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$8
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.strokeLineWidth = ((Number) obj2).floatValue();
                                        pathComponent.isStrokeDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, StrokeJoin.m501boximpl(i33), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$9
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.strokeLineJoin = ((StrokeJoin) obj2).value;
                                        pathComponent.isStrokeDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, StrokeCap.m500boximpl(i18), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$10
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.strokeLineCap = ((StrokeCap) obj2).value;
                                        pathComponent.isStrokeDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f18), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$11
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.strokeLineMiter = ((Number) obj2).floatValue();
                                        pathComponent.isStrokeDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f19), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$12
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.trimPathStart = ((Number) obj2).floatValue();
                                        pathComponent.isTrimPathDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f20), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$13
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.trimPathEnd = ((Number) obj2).floatValue();
                                        pathComponent.isTrimPathDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                Updater.m337setimpl(composerImpl2, Float.valueOf(f21), new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$2$14
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        PathComponent pathComponent = (PathComponent) obj;
                                        pathComponent.trimPathOffset = ((Number) obj2).floatValue();
                                        pathComponent.isTrimPathDirty = true;
                                        pathComponent.invalidate();
                                        return Unit.INSTANCE;
                                    }
                                });
                                composerImpl2.end(z);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                float f22 = f17;
                                i29 = i18;
                                f15 = f22;
                                i27 = i8;
                                brush5 = brush3;
                                f12 = f20;
                                f14 = f9;
                                brush6 = brush4;
                                str3 = str4;
                                f16 = f18;
                                composerImpl = composerImpl2;
                                f11 = f8;
                                i28 = i33;
                                f13 = f21;
                                f10 = f19;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorComposeKt$Path$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    /* JADX WARN: Multi-variable type inference failed */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        VectorComposeKt.m568Path9cdaXJ4(list, i27, str3, brush5, f11, brush6, f15, f14, i29, i28, f16, f10, f12, f13, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), RecomposeScopeImplKt.updateChangedFlags(i5), i6);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i25 |= 3072;
                        int i332 = 0;
                        if (composerImpl2.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 || (i25 & 1171) != 1170) ? z : false)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    z = true;
                    brush4 = brush2;
                    i15 = i6 & 64;
                    if (i15 != 0) {
                    }
                    i16 = i6 & 128;
                    if (i16 != 0) {
                    }
                    i17 = i6 & 256;
                    if (i17 != 0) {
                    }
                    i19 = i6 & 512;
                    if (i19 != 0) {
                    }
                    i21 = i6 & 1024;
                    if (i21 != 0) {
                    }
                    i23 = i6 & 2048;
                    if (i23 != 0) {
                    }
                    int i312 = i22;
                    i24 = i6 & 4096;
                    if (i24 != 0) {
                    }
                    i26 = 8192 & i6;
                    if (i26 == 0) {
                    }
                    int i3322 = 0;
                    if (composerImpl2.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 || (i25 & 1171) != 1170) ? z : false)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                f8 = f;
                i14 = i6 & 32;
                if (i14 == 0) {
                }
                z = true;
                brush4 = brush2;
                i15 = i6 & 64;
                if (i15 != 0) {
                }
                i16 = i6 & 128;
                if (i16 != 0) {
                }
                i17 = i6 & 256;
                if (i17 != 0) {
                }
                i19 = i6 & 512;
                if (i19 != 0) {
                }
                i21 = i6 & 1024;
                if (i21 != 0) {
                }
                i23 = i6 & 2048;
                if (i23 != 0) {
                }
                int i3122 = i22;
                i24 = i6 & 4096;
                if (i24 != 0) {
                }
                i26 = 8192 & i6;
                if (i26 == 0) {
                }
                int i33222 = 0;
                if (composerImpl2.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 || (i25 & 1171) != 1170) ? z : false)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            brush3 = brush;
            i13 = i6 & 16;
            if (i13 != 0) {
            }
            f8 = f;
            i14 = i6 & 32;
            if (i14 == 0) {
            }
            z = true;
            brush4 = brush2;
            i15 = i6 & 64;
            if (i15 != 0) {
            }
            i16 = i6 & 128;
            if (i16 != 0) {
            }
            i17 = i6 & 256;
            if (i17 != 0) {
            }
            i19 = i6 & 512;
            if (i19 != 0) {
            }
            i21 = i6 & 1024;
            if (i21 != 0) {
            }
            i23 = i6 & 2048;
            if (i23 != 0) {
            }
            int i31222 = i22;
            i24 = i6 & 4096;
            if (i24 != 0) {
            }
            i26 = 8192 & i6;
            if (i26 == 0) {
            }
            int i332222 = 0;
            if (composerImpl2.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 || (i25 & 1171) != 1170) ? z : false)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i9 = 16;
        i10 = i6 & 4;
        if (i10 == 0) {
        }
        i12 = i6 & 8;
        if (i12 == 0) {
        }
        brush3 = brush;
        i13 = i6 & 16;
        if (i13 != 0) {
        }
        f8 = f;
        i14 = i6 & 32;
        if (i14 == 0) {
        }
        z = true;
        brush4 = brush2;
        i15 = i6 & 64;
        if (i15 != 0) {
        }
        i16 = i6 & 128;
        if (i16 != 0) {
        }
        i17 = i6 & 256;
        if (i17 != 0) {
        }
        i19 = i6 & 512;
        if (i19 != 0) {
        }
        i21 = i6 & 1024;
        if (i21 != 0) {
        }
        i23 = i6 & 2048;
        if (i23 != 0) {
        }
        int i312222 = i22;
        i24 = i6 & 4096;
        if (i24 != 0) {
        }
        i26 = 8192 & i6;
        if (i26 == 0) {
        }
        int i3322222 = 0;
        if (composerImpl2.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 || (i25 & 1171) != 1170) ? z : false)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
