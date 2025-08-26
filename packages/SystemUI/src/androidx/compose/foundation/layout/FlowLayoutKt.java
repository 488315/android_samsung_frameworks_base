package androidx.compose.foundation.layout;

import androidx.collection.IntIntPair;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.CrossAxisAlignment;
import androidx.compose.foundation.layout.FlowLayoutBuildingBlocks;
import androidx.compose.foundation.layout.FlowLayoutOverflow;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MultiContentMeasurePolicyImpl;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.ConstraintsKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class FlowLayoutKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        CrossAxisAlignment.Companion companion = CrossAxisAlignment.Companion;
        Alignment.Companion.getClass();
        BiasAlignment.Vertical vertical = Alignment.Companion.Top;
        companion.getClass();
        new CrossAxisAlignment.VerticalCrossAxisAlignment(vertical);
        new CrossAxisAlignment.HorizontalCrossAxisAlignment(Alignment.Companion.Start);
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:240:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void FlowRow(Modifier modifier, Arrangement.Horizontal horizontal, Arrangement.Vertical vertical, Alignment.Vertical vertical2, int i, int i2, FlowRowOverflow flowRowOverflow, final Function3 function3, Composer composer, final int i3, final int i4) {
        Modifier modifier2;
        int i5;
        Arrangement.Horizontal horizontal2;
        int i6;
        Arrangement.Vertical vertical3;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        FlowRowOverflow flowRowOverflow2;
        final Modifier modifier3;
        final int i13;
        final Arrangement.Horizontal horizontal3;
        final int i14;
        final Alignment.Vertical vertical4;
        final FlowRowOverflow flowRowOverflow3;
        final Arrangement.Vertical vertical5;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Alignment.Vertical vertical6;
        int i15;
        int i16;
        Arrangement.Horizontal horizontal4;
        int i17;
        Arrangement.Vertical vertical7;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-218661582);
        int i18 = i4 & 1;
        if (i18 != 0) {
            i5 = i3 | 6;
            modifier2 = modifier;
        } else if ((i3 & 6) == 0) {
            modifier2 = modifier;
            i5 = (composerImpl.changed(modifier2) ? 4 : 2) | i3;
        } else {
            modifier2 = modifier;
            i5 = i3;
        }
        int i19 = i4 & 2;
        if (i19 != 0) {
            i5 |= 48;
        } else {
            if ((i3 & 48) == 0) {
                horizontal2 = horizontal;
                i5 |= composerImpl.changed(horizontal2) ? 32 : 16;
            }
            i6 = i4 & 4;
            if (i6 == 0) {
                i5 |= 384;
            } else {
                if ((i3 & 384) == 0) {
                    vertical3 = vertical;
                    i5 |= composerImpl.changed(vertical3) ? 256 : 128;
                }
                i7 = i4 & 8;
                if (i7 != 0) {
                    i5 |= 3072;
                } else {
                    if ((i3 & 3072) == 0) {
                        i5 |= composerImpl.changed(vertical2) ? 2048 : 1024;
                    }
                    i8 = i4 & 16;
                    if (i8 != 0) {
                        if ((i3 & 24576) == 0) {
                            i9 = i;
                            i5 |= composerImpl.changed(i9) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i10 = i4 & 32;
                        if (i10 != 0) {
                            i5 |= 196608;
                            i11 = i2;
                        } else {
                            i11 = i2;
                            if ((i3 & 196608) == 0) {
                                i5 |= composerImpl.changed(i11) ? 131072 : 65536;
                            }
                        }
                        i12 = i4 & 64;
                        if (i12 != 0) {
                            i5 |= 1572864;
                            flowRowOverflow2 = flowRowOverflow;
                        } else {
                            flowRowOverflow2 = flowRowOverflow;
                            if ((i3 & 1572864) == 0) {
                                i5 |= composerImpl.changed(flowRowOverflow2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                        }
                        if ((i4 & 128) != 0) {
                            i5 |= 12582912;
                        } else if ((i3 & 12582912) == 0) {
                            i5 |= composerImpl.changedInstance(function3) ? 8388608 : 4194304;
                        }
                        if (composerImpl.shouldExecute(i5 & 1, (i5 & 4793491) != 4793490)) {
                            modifier3 = i18 != 0 ? Modifier.Companion : modifier2;
                            if (i19 != 0) {
                                Arrangement.INSTANCE.getClass();
                                horizontal2 = Arrangement.Start;
                            }
                            if (i6 != 0) {
                                Arrangement.INSTANCE.getClass();
                                vertical3 = Arrangement.Top;
                            }
                            if (i7 != 0) {
                                Alignment.Companion.getClass();
                                vertical6 = Alignment.Companion.Top;
                            } else {
                                vertical6 = vertical2;
                            }
                            if (i8 != 0) {
                                i9 = Integer.MAX_VALUE;
                            }
                            if (i10 != 0) {
                                i11 = Integer.MAX_VALUE;
                            }
                            if (i12 != 0) {
                                FlowRowOverflow.Companion.getClass();
                                flowRowOverflow2 = FlowRowOverflow.Clip;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.layout.FlowRow (FlowLayout.kt:96)");
                            }
                            int i20 = 3670016 & i5;
                            boolean z = i20 == 1048576;
                            Object objRememberedValue = composerImpl.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!z) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    i15 = i5;
                                    objRememberedValue = new FlowLayoutOverflowState(flowRowOverflow2.type, flowRowOverflow2.minLinesToShowCollapse, flowRowOverflow2.minCrossAxisSizeToShowCollapse);
                                    composerImpl.updateRememberedValue(objRememberedValue);
                                } else {
                                    i15 = i5;
                                }
                                FlowLayoutOverflowState flowLayoutOverflowState = (FlowLayoutOverflowState) objRememberedValue;
                                int i21 = i15 >> 3;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.layout.rowMeasurementMultiContentHelper (FlowLayout.kt:383)");
                                }
                                boolean zChanged = ((((i21 & 14) ^ 6) > 4 && composerImpl.changed(horizontal2)) || (i21 & 6) == 4) | ((((i21 & 112) ^ 48) > 32 && composerImpl.changed(vertical3)) || (i21 & 48) == 32) | ((((i21 & 896) ^ 384) > 256 && composerImpl.changed(vertical6)) || (i21 & 384) == 256) | ((((i21 & 7168) ^ 3072) > 2048 && composerImpl.changed(i9)) || (i21 & 3072) == 2048) | ((((57344 & i21) ^ 24576) > 16384 && composerImpl.changed(i11)) || (i21 & 24576) == 16384) | composerImpl.changed(flowLayoutOverflowState);
                                Object objRememberedValue2 = composerImpl.rememberedValue();
                                if (!zChanged) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        float fMo95getSpacingD9Ej5fM = horizontal2.mo95getSpacingD9Ej5fM();
                                        CrossAxisAlignment.Companion.getClass();
                                        i16 = i11;
                                        horizontal4 = horizontal2;
                                        i17 = i9;
                                        vertical7 = vertical3;
                                        objRememberedValue2 = new FlowMeasurePolicy(true, horizontal4, vertical7, fMo95getSpacingD9Ej5fM, new CrossAxisAlignment.VerticalCrossAxisAlignment(vertical6), vertical3.mo95getSpacingD9Ej5fM(), i17, i16, flowLayoutOverflowState, null);
                                        composerImpl.updateRememberedValue(objRememberedValue2);
                                    } else {
                                        i16 = i11;
                                        horizontal4 = horizontal2;
                                        i17 = i9;
                                        vertical7 = vertical3;
                                    }
                                    FlowMeasurePolicy flowMeasurePolicy = (FlowMeasurePolicy) objRememberedValue2;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    boolean z2 = (i20 == 1048576) | ((i15 & 29360128) == 8388608) | ((i15 & 458752) == 131072);
                                    Object objRememberedValue3 = composerImpl.rememberedValue();
                                    if (!z2) {
                                        companion.getClass();
                                        Object obj = objRememberedValue3;
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            ArrayList arrayList = new ArrayList();
                                            arrayList.add(new ComposableLambdaImpl(702094978, true, new Function2() { // from class: androidx.compose.foundation.layout.FlowLayoutKt$FlowRow$list$1$1
                                                {
                                                    super(2);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(Object obj2, Object obj3) {
                                                    Composer composer2 = (Composer) obj2;
                                                    int iIntValue = ((Number) obj3).intValue();
                                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                    if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.compose.foundation.layout.FlowRow.<anonymous>.<anonymous> (FlowLayout.kt:110)");
                                                        }
                                                        function3.invoke(FlowRowScopeInstance.INSTANCE, composerImpl2, 6);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    } else {
                                                        composerImpl2.skipToGroupEnd();
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }));
                                            Function1 function1 = flowRowOverflow2.seeMoreGetter;
                                            Function2 function2 = function1 != null ? (Function2) function1.mo781invoke(flowLayoutOverflowState) : null;
                                            Function1 function12 = flowRowOverflow2.collapseGetter;
                                            Function2 function22 = function12 != null ? (Function2) function12.mo781invoke(flowLayoutOverflowState) : null;
                                            int i22 = FlowLayoutOverflow.WhenMappings.$EnumSwitchMapping$0[flowRowOverflow2.type.ordinal()];
                                            if (i22 != 1) {
                                                if (i22 == 2) {
                                                    if (function2 != null) {
                                                        arrayList.add(function2);
                                                    }
                                                    if (function22 != null) {
                                                        arrayList.add(function22);
                                                    }
                                                }
                                            } else if (function2 != null) {
                                                arrayList.add(function2);
                                            }
                                            composerImpl.updateRememberedValue(arrayList);
                                            obj = arrayList;
                                        }
                                        ComposableLambdaImpl composableLambdaImplCombineAsVirtualLayouts = LayoutKt.combineAsVirtualLayouts((List) obj);
                                        boolean zChanged2 = composerImpl.changed(flowMeasurePolicy);
                                        Object objRememberedValue4 = composerImpl.rememberedValue();
                                        if (!zChanged2) {
                                            companion.getClass();
                                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                                objRememberedValue4 = new MultiContentMeasurePolicyImpl(flowMeasurePolicy);
                                                composerImpl.updateRememberedValue(objRememberedValue4);
                                            }
                                            MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue4;
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier3);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                                            if (composerImpl.applier == null) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl.startReusableNode();
                                            if (composerImpl.inserting) {
                                                composerImpl.createNode(function0);
                                            } else {
                                                composerImpl.useNode();
                                            }
                                            Updater.m337setimpl(composerImpl, measurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                                            }
                                            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            composableLambdaImplCombineAsVirtualLayouts.invoke((Object) composerImpl, (Object) 0);
                                            composerImpl.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            vertical4 = vertical6;
                                            flowRowOverflow3 = flowRowOverflow2;
                                            horizontal3 = horizontal4;
                                            vertical5 = vertical7;
                                            i14 = i17;
                                            i13 = i16;
                                        }
                                    }
                                }
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                            modifier3 = modifier2;
                            i13 = i11;
                            horizontal3 = horizontal2;
                            i14 = i9;
                            vertical4 = vertical2;
                            flowRowOverflow3 = flowRowOverflow2;
                            vertical5 = vertical3;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.layout.FlowLayoutKt.FlowRow.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj2, Object obj3) {
                                    ((Number) obj3).intValue();
                                    FlowLayoutKt.FlowRow(modifier3, horizontal3, vertical5, vertical4, i14, i13, flowRowOverflow3, function3, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i3 | 1), i4);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i5 |= 24576;
                    i9 = i;
                    i10 = i4 & 32;
                    if (i10 != 0) {
                    }
                    i12 = i4 & 64;
                    if (i12 != 0) {
                    }
                    if ((i4 & 128) != 0) {
                    }
                    if (composerImpl.shouldExecute(i5 & 1, (i5 & 4793491) != 4793490)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                i8 = i4 & 16;
                if (i8 != 0) {
                }
                i9 = i;
                i10 = i4 & 32;
                if (i10 != 0) {
                }
                i12 = i4 & 64;
                if (i12 != 0) {
                }
                if ((i4 & 128) != 0) {
                }
                if (composerImpl.shouldExecute(i5 & 1, (i5 & 4793491) != 4793490)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            vertical3 = vertical;
            i7 = i4 & 8;
            if (i7 != 0) {
            }
            i8 = i4 & 16;
            if (i8 != 0) {
            }
            i9 = i;
            i10 = i4 & 32;
            if (i10 != 0) {
            }
            i12 = i4 & 64;
            if (i12 != 0) {
            }
            if ((i4 & 128) != 0) {
            }
            if (composerImpl.shouldExecute(i5 & 1, (i5 & 4793491) != 4793490)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        horizontal2 = horizontal;
        i6 = i4 & 4;
        if (i6 == 0) {
        }
        vertical3 = vertical;
        i7 = i4 & 8;
        if (i7 != 0) {
        }
        i8 = i4 & 16;
        if (i8 != 0) {
        }
        i9 = i;
        i10 = i4 & 32;
        if (i10 != 0) {
        }
        i12 = i4 & 64;
        if (i12 != 0) {
        }
        if ((i4 & 128) != 0) {
        }
        if (composerImpl.shouldExecute(i5 & 1, (i5 & 4793491) != 4793490)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    public static final long intrinsicCrossAxisSize(List list, Function3 function3, Function3 function32, int i, int i2, int i3, int i4, int i5, FlowLayoutOverflowState flowLayoutOverflowState) {
        int i6;
        if (list.isEmpty()) {
            return IntIntPair.m1constructorimpl(0, 0);
        }
        FlowLayoutBuildingBlocks flowLayoutBuildingBlocks = new FlowLayoutBuildingBlocks(i4, flowLayoutOverflowState, ConstraintsKt.Constraints(0, i, 0, Integer.MAX_VALUE), i5, i2, i3, null);
        IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) CollectionsKt___CollectionsKt.getOrNull(0, list);
        int iIntValue = intrinsicMeasurable != null ? ((Number) function32.invoke(intrinsicMeasurable, 0, Integer.valueOf(i))).intValue() : 0;
        int iIntValue2 = intrinsicMeasurable != null ? ((Number) function3.invoke(intrinsicMeasurable, 0, Integer.valueOf(iIntValue))).intValue() : 0;
        int i7 = 0;
        int iMax = 0;
        if (flowLayoutBuildingBlocks.m104getWrapInfoOpUlnko(list.size() > 1, 0, IntIntPair.m1constructorimpl(i, Integer.MAX_VALUE), intrinsicMeasurable == null ? null : IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iIntValue2, iIntValue)), 0, 0, 0, false, false).isLastItemInContainer) {
            IntIntPair intIntPairM106ellipsisSizeF35zmw$foundation_layout = flowLayoutOverflowState.m106ellipsisSizeF35zmw$foundation_layout(0, 0, intrinsicMeasurable != null);
            return IntIntPair.m1constructorimpl(intIntPairM106ellipsisSizeF35zmw$foundation_layout != null ? (int) (intIntPairM106ellipsisSizeF35zmw$foundation_layout.packedValue & 4294967295L) : 0, 0);
        }
        int size = list.size();
        int i8 = i;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (true) {
            int i13 = iMax;
            if (i9 >= size) {
                i6 = i10;
                break;
            }
            int i14 = i8 - iIntValue2;
            int i15 = i9 + 1;
            iMax = Math.max(i13, iIntValue);
            IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) CollectionsKt___CollectionsKt.getOrNull(i15, list);
            int iIntValue3 = intrinsicMeasurable2 != null ? ((Number) function32.invoke(intrinsicMeasurable2, Integer.valueOf(i15), Integer.valueOf(i))).intValue() : 0;
            int iIntValue4 = intrinsicMeasurable2 != null ? ((Number) function3.invoke(intrinsicMeasurable2, Integer.valueOf(i15), Integer.valueOf(iIntValue3))).intValue() + i2 : 0;
            int i16 = i15 - i11;
            i6 = i15;
            int i17 = i12;
            FlowLayoutBuildingBlocks.WrapInfo wrapInfoM104getWrapInfoOpUlnko = flowLayoutBuildingBlocks.m104getWrapInfoOpUlnko(i9 + 2 < list.size(), i16, IntIntPair.m1constructorimpl(i14, Integer.MAX_VALUE), intrinsicMeasurable2 == null ? null : IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(iIntValue4, iIntValue3)), i17, i7, iMax, false, false);
            if (wrapInfoM104getWrapInfoOpUlnko.isLastItemInLine) {
                int i18 = iMax + i3 + i7;
                FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo = flowLayoutBuildingBlocks.getWrapEllipsisInfo(wrapInfoM104getWrapInfoOpUlnko, intrinsicMeasurable2 != null, i17, i18, i14, i16);
                iIntValue4 -= i2;
                i12 = i17 + 1;
                if (wrapInfoM104getWrapInfoOpUlnko.isLastItemInContainer) {
                    if (wrapEllipsisInfo != null && !wrapEllipsisInfo.placeEllipsisOnLastContentLine) {
                        i18 = ((int) (wrapEllipsisInfo.ellipsisSize & 4294967295L)) + i3 + i18;
                    }
                    i7 = i18;
                } else {
                    i8 = i;
                    i11 = i6;
                    i7 = i18;
                    iMax = 0;
                }
            } else {
                i8 = i14;
                i12 = i17;
            }
            iIntValue2 = iIntValue4;
            iIntValue = iIntValue3;
            i9 = i6;
            i10 = i9;
        }
        return IntIntPair.m1constructorimpl(i7 - i3, i6);
    }

    /* renamed from: measureAndCache-rqJ1uqs, reason: not valid java name */
    public static final long m105measureAndCacherqJ1uqs(Measurable measurable, FlowLineMeasurePolicy flowLineMeasurePolicy, long j, Function1 function1) {
        FlowLayoutData flowLayoutData;
        if (RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(measurable)) == 0.0f) {
            RowColumnParentData rowColumnParentData = RowColumnImplKt.getRowColumnParentData(measurable);
            if (((rowColumnParentData == null || (flowLayoutData = rowColumnParentData.flowLayoutData) == null) ? null : Float.valueOf(flowLayoutData.fillCrossAxisFraction)) == null) {
                Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                function1.mo781invoke(placeableMo610measureBRTryo0);
                return IntIntPair.m1constructorimpl(flowLineMeasurePolicy.mainAxisSize(placeableMo610measureBRTryo0), flowLineMeasurePolicy.crossAxisSize(placeableMo610measureBRTryo0));
            }
        }
        boolean z = ((FlowMeasurePolicy) flowLineMeasurePolicy).isHorizontal;
        int iMinIntrinsicWidth = z ? measurable.minIntrinsicWidth(Integer.MAX_VALUE) : measurable.minIntrinsicHeight(Integer.MAX_VALUE);
        return IntIntPair.m1constructorimpl(iMinIntrinsicWidth, z ? measurable.minIntrinsicHeight(iMinIntrinsicWidth) : measurable.minIntrinsicWidth(iMinIntrinsicWidth));
    }

    public static final Measurable safeNext(Iterator it, FlowLineInfo flowLineInfo) {
        try {
            if (!(it instanceof ContextualFlowItemIterator)) {
                return (Measurable) it.next();
            }
            flowLineInfo.getClass();
            return ((ContextualFlowItemIterator) it).getNext$foundation_layout(flowLineInfo);
        } catch (IndexOutOfBoundsException unused) {
            return null;
        }
    }
}
