package androidx.compose.animation.graphics.vector;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import java.util.ArrayList;
import java.util.Comparator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class Animator {
    public /* synthetic */ Animator(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x01d1, code lost:
    
        throw new java.lang.IllegalStateException("Unknown propertyName: ".concat(r17));
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Configure(final Transition transition, final StateVectorConfig stateVectorConfig, final int i, Composer composer, final int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1894587123);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(transition) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changedInstance(stateVectorConfig) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl.changed(i) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changed(this) ? 2048 : 1024;
        }
        int i10 = i3;
        if (composerImpl.shouldExecute(i10 & 1, (i10 & 1171) != 1170)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.Animator.Configure (Animator.kt:67)");
            }
            boolean z = (i10 & 896) == 256;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                Object obj = objRememberedValue;
                if (objRememberedValue == Composer.Companion.Empty) {
                    MutableScatterMap mutableScatterMapMutableScatterMapOf = ScatterMapKt.mutableScatterMapOf();
                    collectPropertyValues(mutableScatterMapMutableScatterMapOf, i, 0);
                    composerImpl.updateRememberedValue(mutableScatterMapMutableScatterMapOf);
                    obj = mutableScatterMapMutableScatterMapOf;
                }
                MutableScatterMap mutableScatterMap = (MutableScatterMap) obj;
                Object[] objArr = mutableScatterMap.keys;
                Object[] objArr2 = mutableScatterMap.values;
                long[] jArr = mutableScatterMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i11 = 0;
                    while (true) {
                        long j = jArr[i11];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i12 = 8;
                            int i13 = 8 - ((~(i11 - length)) >>> 31);
                            int i14 = 0;
                            while (i14 < i13) {
                                if ((j & 255) < 128) {
                                    int i15 = (i11 << 3) + i14;
                                    Object obj2 = objArr[i15];
                                    PropertyValues propertyValues = (PropertyValues) objArr2[i15];
                                    String str = (String) obj2;
                                    i6 = i12;
                                    ArrayList arrayList = (ArrayList) propertyValues.timestamps;
                                    if (arrayList.size() > 1) {
                                        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: androidx.compose.animation.graphics.vector.Animator$Configure$lambda$5$$inlined$sortBy$1
                                            @Override // java.util.Comparator
                                            public final int compare(Object obj3, Object obj4) {
                                                return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((Timestamp) obj3).timeMillis), Integer.valueOf(((Timestamp) obj4).timeMillis));
                                            }
                                        });
                                    }
                                    i7 = i11;
                                    i8 = length;
                                    i9 = i14;
                                    State stateCreateState = propertyValues.createState(transition, str, i, composerImpl, i10 & 910);
                                    switch (str.hashCode()) {
                                        case -1721943862:
                                            if (!str.equals("translateX")) {
                                                break;
                                            } else {
                                                stateVectorConfig.translateXState = stateCreateState;
                                                break;
                                            }
                                        case -1721943861:
                                            if (!str.equals("translateY")) {
                                                break;
                                            } else {
                                                stateVectorConfig.translateYState = stateCreateState;
                                                break;
                                            }
                                        case -1143814757:
                                            if (!str.equals("fillAlpha")) {
                                                break;
                                            } else {
                                                stateVectorConfig.fillAlphaState = stateCreateState;
                                                break;
                                            }
                                        case -1141881952:
                                            if (!str.equals("fillColor")) {
                                                break;
                                            } else {
                                                stateVectorConfig.fillColorState = stateCreateState;
                                                break;
                                            }
                                        case -1121758502:
                                            if (!str.equals("trimPathOffset")) {
                                                break;
                                            } else {
                                                stateVectorConfig.trimPathOffsetState = stateCreateState;
                                                break;
                                            }
                                        case -987906986:
                                            if (!str.equals("pivotX")) {
                                                break;
                                            } else {
                                                stateVectorConfig.pivotXState = stateCreateState;
                                                break;
                                            }
                                        case -987906985:
                                            if (!str.equals("pivotY")) {
                                                break;
                                            } else {
                                                stateVectorConfig.pivotYState = stateCreateState;
                                                break;
                                            }
                                        case -908189618:
                                            if (!str.equals("scaleX")) {
                                                break;
                                            } else {
                                                stateVectorConfig.scaleXState = stateCreateState;
                                                break;
                                            }
                                        case -908189617:
                                            if (!str.equals("scaleY")) {
                                                break;
                                            } else {
                                                stateVectorConfig.scaleYState = stateCreateState;
                                                break;
                                            }
                                        case -170626757:
                                            if (!str.equals("trimPathStart")) {
                                                break;
                                            } else {
                                                stateVectorConfig.trimPathStartState = stateCreateState;
                                                break;
                                            }
                                        case -40300674:
                                            if (!str.equals("rotation")) {
                                                break;
                                            } else {
                                                stateVectorConfig.rotationState = stateCreateState;
                                                break;
                                            }
                                        case 1233923439:
                                            if (!str.equals("pathData")) {
                                                break;
                                            } else {
                                                stateVectorConfig.pathDataState = stateCreateState;
                                                break;
                                            }
                                        case 1903848966:
                                            if (!str.equals("strokeAlpha")) {
                                                break;
                                            } else {
                                                stateVectorConfig.strokeAlphaState = stateCreateState;
                                                break;
                                            }
                                        case 1905781771:
                                            if (!str.equals("strokeColor")) {
                                                break;
                                            } else {
                                                stateVectorConfig.strokeColorState = stateCreateState;
                                                break;
                                            }
                                        case 1924065902:
                                            if (!str.equals("strokeWidth")) {
                                                break;
                                            } else {
                                                stateVectorConfig.strokeWidthState = stateCreateState;
                                                break;
                                            }
                                        case 2136119284:
                                            if (!str.equals("trimPathEnd")) {
                                                break;
                                            } else {
                                                stateVectorConfig.trimPathEndState = stateCreateState;
                                                break;
                                            }
                                    }
                                } else {
                                    i6 = i12;
                                    i7 = i11;
                                    i8 = length;
                                    i9 = i14;
                                }
                                j >>= i6;
                                i14 = i9 + 1;
                                length = i8;
                                i11 = i7;
                                i12 = i6;
                            }
                            int i16 = i12;
                            i4 = i11;
                            i5 = length;
                            if (i13 == i16) {
                            }
                        } else {
                            i4 = i11;
                            i5 = length;
                        }
                        if (i4 != i5) {
                            i11 = i4 + 1;
                            length = i5;
                        }
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.graphics.vector.Animator.Configure.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    ((Number) obj4).intValue();
                    Animator.this.Configure(transition, stateVectorConfig, i, (Composer) obj3, RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public abstract void collectPropertyValues(MutableScatterMap mutableScatterMap, int i, int i2);

    public abstract int getTotalDuration();

    private Animator() {
    }
}
