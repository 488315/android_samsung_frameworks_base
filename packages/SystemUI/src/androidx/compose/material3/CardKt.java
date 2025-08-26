package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.material3.tokens.FilledCardTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class CardKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:143:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0101  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Card(Modifier modifier, Shape shape, CardColors cardColors, CardElevation cardElevation, BorderStroke borderStroke, final Function3 function3, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        Shape shape2;
        CardColors cardColors2;
        CardElevation cardElevation2;
        BorderStroke borderStroke2;
        Shape value;
        CardColors defaultCardColors$material3_release;
        Modifier modifier3;
        CardElevation cardElevation3;
        Shape shape3;
        BorderStroke borderStroke3;
        Object objRememberedValue;
        ComposerImpl composerImpl;
        final CardColors cardColors3;
        final Shape shape4;
        final BorderStroke borderStroke4;
        final CardElevation cardElevation4;
        final Modifier modifier4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1179621553);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else if ((i & 6) == 0) {
            modifier2 = modifier;
            i3 = (composerImpl2.changed(modifier2) ? 4 : 2) | i;
        } else {
            modifier2 = modifier;
            i3 = i;
        }
        if ((i & 48) == 0) {
            if ((i2 & 2) == 0) {
                shape2 = shape;
                int i5 = composerImpl2.changed(shape2) ? 32 : 16;
                i3 |= i5;
            } else {
                shape2 = shape;
            }
            i3 |= i5;
        } else {
            shape2 = shape;
        }
        if ((i & 384) == 0) {
            if ((i2 & 4) == 0) {
                cardColors2 = cardColors;
                int i6 = composerImpl2.changed(cardColors2) ? 256 : 128;
                i3 |= i6;
            } else {
                cardColors2 = cardColors;
            }
            i3 |= i6;
        } else {
            cardColors2 = cardColors;
        }
        if ((i & 3072) == 0) {
            if ((i2 & 8) == 0) {
                cardElevation2 = cardElevation;
                int i7 = composerImpl2.changed(cardElevation2) ? 2048 : 1024;
                i3 |= i7;
            } else {
                cardElevation2 = cardElevation;
            }
            i3 |= i7;
        } else {
            cardElevation2 = cardElevation;
        }
        int i8 = i2 & 16;
        if (i8 == 0) {
            if ((i & 24576) == 0) {
                borderStroke2 = borderStroke;
                i3 |= composerImpl2.changed(borderStroke2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            if ((i2 & 32) == 0) {
                i3 |= 196608;
            } else if ((i & 196608) == 0) {
                i3 |= composerImpl2.changedInstance(function3) ? 131072 : 65536;
            }
            if ((74899 & i3) == 74898 || !composerImpl2.getSkipping()) {
                composerImpl2.startDefaults();
                if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                    Modifier modifier5 = i4 == 0 ? Modifier.Companion : modifier2;
                    if ((i2 & 2) == 0) {
                        CardDefaults.INSTANCE.getClass();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.CardDefaults.<get-shape> (Card.kt:376)");
                        }
                        FilledCardTokens.INSTANCE.getClass();
                        value = ShapesKt.getValue(FilledCardTokens.ContainerShape, composerImpl2);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        i3 &= -113;
                    } else {
                        value = shape2;
                    }
                    if ((i2 & 4) == 0) {
                        CardDefaults.INSTANCE.getClass();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.CardDefaults.cardColors (Card.kt:478)");
                        }
                        MaterialTheme.INSTANCE.getClass();
                        defaultCardColors$material3_release = CardDefaults.getDefaultCardColors$material3_release(MaterialTheme.getColorScheme(composerImpl2));
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        i3 &= -897;
                    } else {
                        defaultCardColors$material3_release = cardColors2;
                    }
                    if ((i2 & 8) != 0) {
                        CardDefaults.INSTANCE.getClass();
                        FilledCardTokens.INSTANCE.getClass();
                        float f = FilledCardTokens.ContainerElevation;
                        float f2 = FilledCardTokens.PressedContainerElevation;
                        float f3 = FilledCardTokens.FocusContainerElevation;
                        float f4 = FilledCardTokens.HoverContainerElevation;
                        float f5 = FilledCardTokens.DraggedContainerElevation;
                        float f6 = FilledCardTokens.DisabledContainerElevation;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.CardDefaults.cardElevation (Card.kt:406)");
                        }
                        CardElevation cardElevation5 = new CardElevation(f, f2, f3, f4, f5, f6, null);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        i3 &= -7169;
                        cardElevation2 = cardElevation5;
                    }
                    if (i8 == 0) {
                        CardElevation cardElevation6 = cardElevation2;
                        modifier3 = modifier5;
                        cardElevation3 = cardElevation6;
                        shape3 = value;
                        borderStroke3 = null;
                    } else {
                        CardElevation cardElevation7 = cardElevation2;
                        modifier3 = modifier5;
                        cardElevation3 = cardElevation7;
                        shape3 = value;
                        borderStroke3 = borderStroke2;
                    }
                } else {
                    composerImpl2.skipToGroupEnd();
                    if ((i2 & 2) != 0) {
                        i3 &= -113;
                    }
                    if ((i2 & 4) != 0) {
                        i3 &= -897;
                    }
                    if ((i2 & 8) != 0) {
                        i3 &= -7169;
                    }
                    shape3 = shape2;
                    defaultCardColors$material3_release = cardColors2;
                    cardElevation3 = cardElevation2;
                    borderStroke3 = borderStroke2;
                    modifier3 = modifier2;
                }
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.Card (Card.kt:87)");
                }
                long j = defaultCardColors$material3_release.containerColor;
                cardElevation3.getClass();
                composerImpl2.startReplaceGroup(-1763481333);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.CardElevation.shadowElevation (Card.kt:661)");
                }
                composerImpl2.startReplaceGroup(-1304675619);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(Dp.m837boximpl(cardElevation3.defaultElevation));
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                MutableState mutableState = (MutableState) objRememberedValue;
                composerImpl2.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl2.end(false);
                composerImpl = composerImpl2;
                SurfaceKt.m304SurfaceT9BRK9s(modifier3, shape3, j, defaultCardColors$material3_release.contentColor, 0.0f, ((Dp) mutableState.getValue()).value, borderStroke3, ComposableLambdaKt.rememberComposableLambda(664103990, new Function2() { // from class: androidx.compose.material3.CardKt.Card.1
                    {
                        super(2);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.Card.<anonymous> (Card.kt:96)");
                                }
                                Function3 function32 = function3;
                                Modifier.Companion companion = Modifier.Companion;
                                Arrangement.INSTANCE.getClass();
                                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                                Alignment.Companion.getClass();
                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl4.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function0);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                function32.invoke(ColumnScopeInstance.INSTANCE, composer2, 6);
                                composerImpl4.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i3 & 14) | 12582912 | (i3 & 112) | ((i3 << 6) & 3670016), 16);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                cardColors3 = defaultCardColors$material3_release;
                shape4 = shape3;
                borderStroke4 = borderStroke3;
                cardElevation4 = cardElevation3;
                modifier4 = modifier3;
            } else {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
                modifier4 = modifier2;
                shape4 = shape2;
                cardColors3 = cardColors2;
                cardElevation4 = cardElevation2;
                borderStroke4 = borderStroke2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.CardKt.Card.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        CardKt.Card(modifier4, shape4, cardColors3, cardElevation4, borderStroke4, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 24576;
        borderStroke2 = borderStroke;
        if ((i2 & 32) == 0) {
        }
        if ((74899 & i3) == 74898) {
            composerImpl2.startDefaults();
            if ((i & 1) != 0) {
                if (i4 == 0) {
                }
                if ((i2 & 2) == 0) {
                }
                if ((i2 & 4) == 0) {
                }
                if ((i2 & 8) != 0) {
                }
                if (i8 == 0) {
                }
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                long j2 = defaultCardColors$material3_release.containerColor;
                cardElevation3.getClass();
                composerImpl2.startReplaceGroup(-1763481333);
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl2.startReplaceGroup(-1304675619);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                }
                MutableState mutableState2 = (MutableState) objRememberedValue;
                composerImpl2.end(false);
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl2.end(false);
                composerImpl = composerImpl2;
                SurfaceKt.m304SurfaceT9BRK9s(modifier3, shape3, j2, defaultCardColors$material3_release.contentColor, 0.0f, ((Dp) mutableState2.getValue()).value, borderStroke3, ComposableLambdaKt.rememberComposableLambda(664103990, new Function2() { // from class: androidx.compose.material3.CardKt.Card.1
                    {
                        super(2);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.Card.<anonymous> (Card.kt:96)");
                                }
                                Function3 function32 = function3;
                                Modifier.Companion companion = Modifier.Companion;
                                Arrangement.INSTANCE.getClass();
                                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                                Alignment.Companion.getClass();
                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composer2, 0);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl4.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function0);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                function32.invoke(ColumnScopeInstance.INSTANCE, composer2, 6);
                                composerImpl4.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i3 & 14) | 12582912 | (i3 & 112) | ((i3 << 6) & 3670016), 16);
                if (ComposerKt.isTraceInProgress()) {
                }
                cardColors3 = defaultCardColors$material3_release;
                shape4 = shape3;
                borderStroke4 = borderStroke3;
                cardElevation4 = cardElevation3;
                modifier4 = modifier3;
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
