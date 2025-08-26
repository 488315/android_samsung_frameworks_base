package androidx.compose.foundation.text;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringElement;
import androidx.compose.foundation.text.modifiers.SelectionController;
import androidx.compose.foundation.text.modifiers.TextAnnotatedStringElement;
import androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode;
import androidx.compose.foundation.text.modifiers.TextAnnotatedStringNodeKt;
import androidx.compose.foundation.text.modifiers.TextStringSimpleElement;
import androidx.compose.foundation.text.selection.SelectionRegistrarKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.StringAnnotation;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class BasicTextKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:231:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x012e  */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9, types: [int] */
    /* renamed from: BasicText-CL7eQgs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m192BasicTextCL7eQgs(final AnnotatedString annotatedString, Modifier modifier, TextStyle textStyle, Function1 function1, int i, boolean z, int i2, int i3, Map map, ColorProducer colorProducer, TextAutoSize textAutoSize, Composer composer, final int i4, final int i5, final int i6) {
        int i7;
        Modifier modifier2;
        int i8;
        TextStyle textStyle2;
        int i9;
        Function1 function12;
        int i10;
        boolean z2;
        int i11;
        boolean z3;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        final int i20;
        final int i21;
        final Map map2;
        final ColorProducer colorProducer2;
        ComposerImpl composerImpl;
        final TextStyle textStyle3;
        final Function1 function13;
        final boolean z4;
        final int i22;
        final Modifier modifier3;
        final TextAutoSize textAutoSize2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        TextStyle textStyle4;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        Modifier modifier4;
        boolean z5;
        boolean z6;
        ComposerImpl composerImpl2;
        int i28;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-1343466571);
        if ((i6 & 1) != 0) {
            i7 = i4 | 6;
        } else if ((i4 & 6) == 0) {
            i7 = (composerImpl3.changed(annotatedString) ? 4 : 2) | i4;
        } else {
            i7 = i4;
        }
        int i29 = i6 & 2;
        if (i29 != 0) {
            i7 |= 48;
        } else {
            if ((i4 & 48) == 0) {
                modifier2 = modifier;
                i7 |= composerImpl3.changed(modifier2) ? 32 : 16;
            }
            i8 = i6 & 4;
            if (i8 == 0) {
                i7 |= 384;
                textStyle2 = textStyle;
            } else if ((i4 & 384) == 0) {
                textStyle2 = textStyle;
                i7 |= composerImpl3.changed(textStyle2) ? 256 : 128;
            } else {
                textStyle2 = textStyle;
            }
            i9 = i6 & 8;
            if (i9 == 0) {
                i7 |= 3072;
                function12 = function1;
            } else if ((i4 & 3072) == 0) {
                function12 = function1;
                i7 |= composerImpl3.changedInstance(function12) ? 2048 : 1024;
            } else {
                function12 = function1;
            }
            i10 = i6 & 16;
            if (i10 != 0) {
                z2 = true;
                if ((i4 & 24576) == 0) {
                    i7 |= composerImpl3.changed(i) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                i11 = i6 & 32;
                if (i11 != 0) {
                    i7 |= 196608;
                    z3 = z;
                } else {
                    z3 = z;
                    if ((i4 & 196608) == 0) {
                        i7 |= composerImpl3.changed(z3) ? 131072 : 65536;
                    }
                }
                i12 = i6 & 64;
                if (i12 != 0) {
                    i7 |= 1572864;
                    i13 = i2;
                } else {
                    i13 = i2;
                    if ((i4 & 1572864) == 0) {
                        i7 |= composerImpl3.changed(i13) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                }
                i14 = 128 & i6;
                if (i14 != 0) {
                    i7 |= 12582912;
                } else if ((i4 & 12582912) == 0) {
                    i7 |= composerImpl3.changed(i3) ? 8388608 : 4194304;
                }
                i15 = 256 & i6;
                if (i15 != 0) {
                    i7 |= 100663296;
                } else if ((i4 & 100663296) == 0) {
                    i7 |= composerImpl3.changedInstance(map) ? 67108864 : 33554432;
                }
                i16 = i6 & 512;
                if (i16 != 0) {
                    i7 |= 805306368;
                    i17 = i16;
                } else if ((i4 & 805306368) == 0) {
                    i17 = i16;
                    i7 |= composerImpl3.changedInstance(colorProducer) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                } else {
                    i17 = i16;
                }
                i18 = 1024 & i6;
                if (i18 != 0) {
                    i19 = i5 | 6;
                } else if ((i5 & 6) == 0) {
                    i19 = i5 | ((i5 & 8) == 0 ? composerImpl3.changed(textAutoSize) : composerImpl3.changedInstance(textAutoSize) ? 4 : 2);
                } else {
                    i19 = i5;
                }
                boolean z7 = false;
                if (composerImpl3.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 && (i19 & 3) == 2) ? false : z2)) {
                    Modifier modifier5 = i29 != 0 ? Modifier.Companion : modifier2;
                    if (i8 != 0) {
                        TextStyle.Companion.getClass();
                        textStyle4 = TextStyle.Default;
                    } else {
                        textStyle4 = textStyle2;
                    }
                    Function1 function14 = i9 != 0 ? null : function12;
                    if (i10 != 0) {
                        TextOverflow.Companion.getClass();
                        i23 = TextOverflow.Clip;
                    } else {
                        i23 = i;
                    }
                    boolean z8 = i11 != 0 ? z2 : z3;
                    if (i12 != 0) {
                        i24 = i15;
                        i25 = Integer.MAX_VALUE;
                    } else {
                        i24 = i15;
                        i25 = i13;
                    }
                    if (i14 != 0) {
                        i26 = i24;
                        i27 = z2;
                    } else {
                        i26 = i24;
                        i27 = i3;
                    }
                    Map mapEmptyMap = i26 != 0 ? MapsKt__MapsKt.emptyMap() : map;
                    ColorProducer colorProducer3 = i17 != 0 ? null : colorProducer;
                    TextAutoSize textAutoSize3 = i18 != 0 ? null : textAutoSize;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.text.BasicText (BasicText.kt:194)");
                    }
                    HeightInLinesModifierKt.validateMinMaxLines(i27, i25);
                    if (composerImpl3.consume(SelectionRegistrarKt.LocalSelectionRegistrar) != null) {
                        throw new ClassCastException();
                    }
                    composerImpl3.startReplaceGroup(-1583679630);
                    composerImpl3.end(false);
                    Pair pair = AnnotatedStringResolveInlineContentKt.EmptyInlineContent;
                    int length = annotatedString.text.length();
                    List list = annotatedString.annotations;
                    if (list != null) {
                        int size = list.size();
                        int i30 = 0;
                        while (i30 < size) {
                            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i30);
                            modifier4 = modifier5;
                            if ((range.item instanceof StringAnnotation) && "androidx.compose.foundation.text.inlineContent".equals(range.tag)) {
                                int i31 = range.start;
                                int i32 = range.end;
                                z7 = false;
                                if (AnnotatedStringKt.intersect(0, length, i31, i32)) {
                                    z5 = z2;
                                    break;
                                }
                            } else {
                                z7 = false;
                            }
                            i30++;
                            modifier5 = modifier4;
                        }
                        modifier4 = modifier5;
                        z5 = z7;
                        boolean zHasLinks = TextAnnotatedStringNodeKt.hasLinks(annotatedString);
                        if (!z5 || zHasLinks) {
                            z6 = z2;
                            composerImpl2 = composerImpl3;
                            i28 = i23;
                            composerImpl2.startReplaceGroup(-1582650709);
                            if ((i7 & 14) != 4) {
                                z6 = z7;
                            }
                            Object objRememberedValue = composerImpl2.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (z6) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(annotatedString);
                                    composerImpl2.updateRememberedValue(objRememberedValue);
                                }
                                final MutableState mutableState = (MutableState) objRememberedValue;
                                AnnotatedString annotatedString2 = (AnnotatedString) mutableState.getValue();
                                FontFamily.Resolver resolver = (FontFamily.Resolver) composerImpl2.consume(CompositionLocalsKt.LocalFontFamilyResolver);
                                boolean zChanged = composerImpl2.changed(mutableState);
                                Object objRememberedValue2 = composerImpl2.rememberedValue();
                                if (!zChanged) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new Function1() { // from class: androidx.compose.foundation.text.BasicTextKt$BasicText$2$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                TextAnnotatedStringNode.TextSubstitutionValue textSubstitutionValue = (TextAnnotatedStringNode.TextSubstitutionValue) obj;
                                                mutableState.setValue(textSubstitutionValue.isShowingSubstitution ? textSubstitutionValue.substitution : textSubstitutionValue.original);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl2.updateRememberedValue(objRememberedValue2);
                                    }
                                    int i33 = i7 << 6;
                                    Modifier modifier6 = modifier4;
                                    m196LayoutWithLinksAndInlineContent11Od_4g(modifier6, annotatedString2, function14, z5, mapEmptyMap, textStyle4, i28, z8, i25, i27, resolver, null, colorProducer3, (Function1) objRememberedValue2, textAutoSize3, composerImpl2, ((i7 >> 3) & 910) | ((i7 >> 12) & 57344) | ((i7 << 9) & 458752) | (3670016 & i33) | (29360128 & i33) | (234881024 & i33) | (i33 & 1879048192), ((i7 >> 21) & 896) | ((i19 << 12) & 57344), 0);
                                    modifier4 = modifier6;
                                    composerImpl2.end(z7);
                                }
                            }
                        } else {
                            composerImpl3.startReplaceGroup(-1583500636);
                            boolean z9 = z2;
                            i28 = i23;
                            Modifier modifierM197textModifierCL7eQgs = m197textModifierCL7eQgs(modifier4, annotatedString, textStyle4, function14, i28, z8, i25, i27, (FontFamily.Resolver) composerImpl3.consume(CompositionLocalsKt.LocalFontFamilyResolver), null, null, null, colorProducer3, null, textAutoSize3);
                            EmptyMeasurePolicy emptyMeasurePolicy = EmptyMeasurePolicy.INSTANCE;
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                            composerImpl2 = composerImpl3;
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM197textModifierCL7eQgs);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                            ComposeUiNode.Companion.getClass();
                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                            if (composerImpl2.applier == null) {
                                ComposablesKt.invalidApplier();
                                throw null;
                            }
                            composerImpl2.startReusableNode();
                            if (composerImpl2.inserting) {
                                composerImpl2.createNode(function0);
                            } else {
                                composerImpl2.useNode();
                            }
                            Updater.m337setimpl(composerImpl2, emptyMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                            }
                            composerImpl2.end(z9);
                            composerImpl2.end(false);
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl = composerImpl2;
                        i21 = i27;
                        colorProducer2 = colorProducer3;
                        textAutoSize2 = textAutoSize3;
                        map2 = mapEmptyMap;
                        textStyle3 = textStyle4;
                        i22 = i25;
                        modifier3 = modifier4;
                        z4 = z8;
                        i20 = i28;
                        function13 = function14;
                    } else {
                        modifier4 = modifier5;
                        z5 = z7;
                        boolean zHasLinks2 = TextAnnotatedStringNodeKt.hasLinks(annotatedString);
                        if (z5) {
                            z6 = z2;
                            composerImpl2 = composerImpl3;
                            i28 = i23;
                            composerImpl2.startReplaceGroup(-1582650709);
                            if ((i7 & 14) != 4) {
                            }
                            Object objRememberedValue3 = composerImpl2.rememberedValue();
                            Composer.Companion companion2 = Composer.Companion;
                            if (z6) {
                            }
                        }
                    }
                } else {
                    composerImpl3.skipToGroupEnd();
                    i20 = i;
                    i21 = i3;
                    map2 = map;
                    colorProducer2 = colorProducer;
                    composerImpl = composerImpl3;
                    textStyle3 = textStyle2;
                    function13 = function12;
                    z4 = z3;
                    i22 = i13;
                    modifier3 = modifier2;
                    textAutoSize2 = textAutoSize;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.BasicTextKt$BasicText$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BasicTextKt.m192BasicTextCL7eQgs(annotatedString, modifier3, textStyle3, function13, i20, z4, i22, i21, map2, colorProducer2, textAutoSize2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), RecomposeScopeImplKt.updateChangedFlags(i5), i6);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i7 |= 24576;
            z2 = true;
            i11 = i6 & 32;
            if (i11 != 0) {
            }
            i12 = i6 & 64;
            if (i12 != 0) {
            }
            i14 = 128 & i6;
            if (i14 != 0) {
            }
            i15 = 256 & i6;
            if (i15 != 0) {
            }
            i16 = i6 & 512;
            if (i16 != 0) {
            }
            i18 = 1024 & i6;
            if (i18 != 0) {
            }
            boolean z72 = false;
            if (composerImpl3.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 && (i19 & 3) == 2) ? false : z2)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i8 = i6 & 4;
        if (i8 == 0) {
        }
        i9 = i6 & 8;
        if (i9 == 0) {
        }
        i10 = i6 & 16;
        if (i10 != 0) {
        }
        i11 = i6 & 32;
        if (i11 != 0) {
        }
        i12 = i6 & 64;
        if (i12 != 0) {
        }
        i14 = 128 & i6;
        if (i14 != 0) {
        }
        i15 = 256 & i6;
        if (i15 != 0) {
        }
        i16 = i6 & 512;
        if (i16 != 0) {
        }
        i18 = 1024 & i6;
        if (i18 != 0) {
        }
        boolean z722 = false;
        if (composerImpl3.shouldExecute(i7 & 1, ((i7 & 306783379) == 306783378 && (i19 & 3) == 2) ? false : z2)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x011b A[PHI: r19
      0x011b: PHI (r19v15 int) = (r19v4 int), (r19v10 int), (r19v11 int) binds: [B:100:0x0119, B:110:0x0136, B:109:0x0133] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:181:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0100  */
    /* renamed from: BasicText-RWo7tUw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m194BasicTextRWo7tUw(String str, Modifier modifier, TextStyle textStyle, Function1 function1, int i, boolean z, int i2, int i3, ColorProducer colorProducer, TextAutoSize textAutoSize, Composer composer, final int i4, final int i5) {
        int i6;
        Modifier modifier2;
        int i7;
        TextStyle textStyle2;
        int i8;
        Function1 function12;
        int i9;
        int i10;
        int i11;
        boolean z2;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        final String str2;
        final int i18;
        final ColorProducer colorProducer2;
        Modifier modifier3;
        final TextStyle textStyle3;
        final int i19;
        final int i20;
        final boolean z3;
        final Function1 function13;
        final TextAutoSize textAutoSize2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        TextStyle textStyle4;
        int i21;
        int i22;
        int i23;
        Modifier modifierM197textModifierCL7eQgs;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1040751001);
        if ((i5 & 1) != 0) {
            i6 = i4 | 6;
        } else if ((i4 & 6) == 0) {
            i6 = (composerImpl.changed(str) ? 4 : 2) | i4;
        } else {
            i6 = i4;
        }
        int i24 = i5 & 2;
        if (i24 != 0) {
            i6 |= 48;
        } else {
            if ((i4 & 48) == 0) {
                modifier2 = modifier;
                i6 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i7 = i5 & 4;
            if (i7 == 0) {
                i6 |= 384;
            } else {
                if ((i4 & 384) == 0) {
                    textStyle2 = textStyle;
                    i6 |= composerImpl.changed(textStyle2) ? 256 : 128;
                }
                i8 = i5 & 8;
                if (i8 != 0) {
                    i6 |= 3072;
                } else {
                    if ((i4 & 3072) == 0) {
                        function12 = function1;
                        i6 |= composerImpl.changedInstance(function12) ? 2048 : 1024;
                    }
                    i9 = i5 & 16;
                    if (i9 == 0) {
                        i6 |= 24576;
                    } else {
                        if ((i4 & 24576) == 0) {
                            i10 = i;
                            i6 |= composerImpl.changed(i10) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i11 = i5 & 32;
                        if (i11 != 0) {
                            i6 |= 196608;
                            z2 = z;
                        } else {
                            z2 = z;
                            if ((i4 & 196608) == 0) {
                                i6 |= composerImpl.changed(z2) ? 131072 : 65536;
                            }
                        }
                        i12 = i5 & 64;
                        if (i12 != 0) {
                            i6 |= 1572864;
                        } else if ((i4 & 1572864) == 0) {
                            i6 |= composerImpl.changed(i2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        i13 = i5 & 128;
                        if (i13 != 0) {
                            i6 |= 12582912;
                        } else {
                            if ((i4 & 12582912) == 0) {
                                i14 = i13;
                                i6 |= composerImpl.changed(i3) ? 8388608 : 4194304;
                            }
                            i15 = i5 & 256;
                            if (i15 != 0) {
                                if ((i4 & 100663296) == 0) {
                                    i16 = i15;
                                    i6 |= composerImpl.changedInstance(colorProducer) ? 67108864 : 33554432;
                                }
                                i17 = i5 & 512;
                                int i25 = 805306368;
                                if (i17 != 0) {
                                    i6 |= i25;
                                } else if ((i4 & 805306368) == 0) {
                                    i25 = (i4 & 1073741824) == 0 ? composerImpl.changed(textAutoSize) : composerImpl.changedInstance(textAutoSize) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                                    i6 |= i25;
                                }
                                if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
                                    if (i24 != 0) {
                                        modifier2 = Modifier.Companion;
                                    }
                                    if (i7 != 0) {
                                        TextStyle.Companion.getClass();
                                        textStyle4 = TextStyle.Default;
                                    } else {
                                        textStyle4 = textStyle2;
                                    }
                                    Function1 function14 = i8 != 0 ? null : function12;
                                    if (i9 != 0) {
                                        TextOverflow.Companion.getClass();
                                        i21 = TextOverflow.Clip;
                                    } else {
                                        i21 = i10;
                                    }
                                    boolean z4 = i11 != 0 ? true : z2;
                                    int i26 = i12 != 0 ? Integer.MAX_VALUE : i2;
                                    int i27 = i14 != 0 ? 1 : i3;
                                    ColorProducer colorProducer3 = i16 != 0 ? null : colorProducer;
                                    TextAutoSize textAutoSize3 = i17 != 0 ? null : textAutoSize;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.foundation.text.BasicText (BasicText.kt:101)");
                                    }
                                    HeightInLinesModifierKt.validateMinMaxLines(i27, i26);
                                    if (composerImpl.consume(SelectionRegistrarKt.LocalSelectionRegistrar) != null) {
                                        throw new ClassCastException();
                                    }
                                    composerImpl.startReplaceGroup(-1588311278);
                                    composerImpl.end(false);
                                    if (function14 == null && textAutoSize3 == null) {
                                        composerImpl.startReplaceGroup(-1587511974);
                                        int i28 = i26;
                                        int i29 = i27;
                                        str2 = str;
                                        i22 = i28;
                                        i23 = i29;
                                        modifierM197textModifierCL7eQgs = modifier2.then(new TextStringSimpleElement(str, textStyle4, (FontFamily.Resolver) composerImpl.consume(CompositionLocalsKt.LocalFontFamilyResolver), i21, z4, i28, i29, colorProducer3, null));
                                        composerImpl.end(false);
                                        modifier3 = modifier2;
                                    } else {
                                        i22 = i26;
                                        i23 = i27;
                                        str2 = str;
                                        composerImpl.startReplaceGroup(-1588155131);
                                        modifier3 = modifier2;
                                        modifierM197textModifierCL7eQgs = m197textModifierCL7eQgs(modifier3, new AnnotatedString(str2, null, 2, null), textStyle4, function14, i21, z4, i22, i23, (FontFamily.Resolver) composerImpl.consume(CompositionLocalsKt.LocalFontFamilyResolver), null, null, null, colorProducer3, null, textAutoSize3);
                                        composerImpl.end(false);
                                    }
                                    EmptyMeasurePolicy emptyMeasurePolicy = EmptyMeasurePolicy.INSTANCE;
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM197textModifierCL7eQgs);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
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
                                    Updater.m337setimpl(composerImpl, emptyMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                                    }
                                    composerImpl.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    textStyle3 = textStyle4;
                                    function13 = function14;
                                    i19 = i21;
                                    z3 = z4;
                                    i18 = i22;
                                    i20 = i23;
                                    colorProducer2 = colorProducer3;
                                    textAutoSize2 = textAutoSize3;
                                } else {
                                    str2 = str;
                                    composerImpl.skipToGroupEnd();
                                    i18 = i2;
                                    colorProducer2 = colorProducer;
                                    modifier3 = modifier2;
                                    textStyle3 = textStyle2;
                                    i19 = i10;
                                    i20 = i3;
                                    z3 = z2;
                                    function13 = function12;
                                    textAutoSize2 = textAutoSize;
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                    final Modifier modifier4 = modifier3;
                                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.BasicTextKt$BasicText$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            ((Number) obj2).intValue();
                                            BasicTextKt.m194BasicTextRWo7tUw(str2, modifier4, textStyle3, function13, i19, z3, i18, i20, colorProducer2, textAutoSize2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), i5);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    return;
                                }
                                return;
                            }
                            i6 |= 100663296;
                            i16 = i15;
                            i17 = i5 & 512;
                            int i252 = 805306368;
                            if (i17 != 0) {
                            }
                            if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        i14 = i13;
                        i15 = i5 & 256;
                        if (i15 != 0) {
                        }
                        i16 = i15;
                        i17 = i5 & 512;
                        int i2522 = 805306368;
                        if (i17 != 0) {
                        }
                        if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    i10 = i;
                    i11 = i5 & 32;
                    if (i11 != 0) {
                    }
                    i12 = i5 & 64;
                    if (i12 != 0) {
                    }
                    i13 = i5 & 128;
                    if (i13 != 0) {
                    }
                    i14 = i13;
                    i15 = i5 & 256;
                    if (i15 != 0) {
                    }
                    i16 = i15;
                    i17 = i5 & 512;
                    int i25222 = 805306368;
                    if (i17 != 0) {
                    }
                    if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                function12 = function1;
                i9 = i5 & 16;
                if (i9 == 0) {
                }
                i10 = i;
                i11 = i5 & 32;
                if (i11 != 0) {
                }
                i12 = i5 & 64;
                if (i12 != 0) {
                }
                i13 = i5 & 128;
                if (i13 != 0) {
                }
                i14 = i13;
                i15 = i5 & 256;
                if (i15 != 0) {
                }
                i16 = i15;
                i17 = i5 & 512;
                int i252222 = 805306368;
                if (i17 != 0) {
                }
                if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            textStyle2 = textStyle;
            i8 = i5 & 8;
            if (i8 != 0) {
            }
            function12 = function1;
            i9 = i5 & 16;
            if (i9 == 0) {
            }
            i10 = i;
            i11 = i5 & 32;
            if (i11 != 0) {
            }
            i12 = i5 & 64;
            if (i12 != 0) {
            }
            i13 = i5 & 128;
            if (i13 != 0) {
            }
            i14 = i13;
            i15 = i5 & 256;
            if (i15 != 0) {
            }
            i16 = i15;
            i17 = i5 & 512;
            int i2522222 = 805306368;
            if (i17 != 0) {
            }
            if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i7 = i5 & 4;
        if (i7 == 0) {
        }
        textStyle2 = textStyle;
        i8 = i5 & 8;
        if (i8 != 0) {
        }
        function12 = function1;
        i9 = i5 & 16;
        if (i9 == 0) {
        }
        i10 = i;
        i11 = i5 & 32;
        if (i11 != 0) {
        }
        i12 = i5 & 64;
        if (i12 != 0) {
        }
        i13 = i5 & 128;
        if (i13 != 0) {
        }
        i14 = i13;
        i15 = i5 & 256;
        if (i15 != 0) {
        }
        i16 = i15;
        i17 = i5 & 512;
        int i25222222 = 805306368;
        if (i17 != 0) {
        }
        if (composerImpl.shouldExecute(i6 & 1, (i6 & 306783379) != 306783378)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:144:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0104  */
    /* renamed from: BasicText-VhcvRP8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m195BasicTextVhcvRP8(final String str, Modifier modifier, TextStyle textStyle, Function1 function1, int i, boolean z, int i2, int i3, ColorProducer colorProducer, Composer composer, final int i4, final int i5) {
        String str2;
        int i6;
        Modifier modifier2;
        int i7;
        TextStyle textStyle2;
        int i8;
        Function1 function12;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        ComposerImpl composerImpl;
        final boolean z2;
        final int i18;
        final Modifier modifier3;
        final TextStyle textStyle3;
        final Function1 function13;
        final int i19;
        final int i20;
        final ColorProducer colorProducer2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i21;
        Modifier modifier4;
        TextStyle textStyle4;
        int i22;
        int i23;
        int i24;
        boolean z3;
        int i25;
        int i26;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1186827822);
        if ((i5 & 1) != 0) {
            i6 = i4 | 6;
            str2 = str;
        } else {
            str2 = str;
            if ((i4 & 6) == 0) {
                i6 = (composerImpl2.changed(str2) ? 4 : 2) | i4;
            } else {
                i6 = i4;
            }
        }
        int i27 = i5 & 2;
        if (i27 != 0) {
            i6 |= 48;
        } else {
            if ((i4 & 48) == 0) {
                modifier2 = modifier;
                i6 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i7 = i5 & 4;
            if (i7 == 0) {
                i6 |= 384;
            } else {
                if ((i4 & 384) == 0) {
                    textStyle2 = textStyle;
                    i6 |= composerImpl2.changed(textStyle2) ? 256 : 128;
                }
                i8 = i5 & 8;
                if (i8 != 0) {
                    i6 |= 3072;
                } else {
                    if ((i4 & 3072) == 0) {
                        function12 = function1;
                        i6 |= composerImpl2.changedInstance(function12) ? 2048 : 1024;
                    }
                    i9 = i5 & 16;
                    if (i9 == 0) {
                        i6 |= 24576;
                    } else {
                        if ((i4 & 24576) == 0) {
                            i10 = i;
                            i6 |= composerImpl2.changed(i10) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i11 = i5 & 32;
                        if (i11 != 0) {
                            i6 |= 196608;
                        } else {
                            if ((196608 & i4) == 0) {
                                i6 |= composerImpl2.changed(z) ? 131072 : 65536;
                            }
                            i12 = i5 & 64;
                            if (i12 == 0) {
                                i13 = i6 | 1572864;
                            } else {
                                int i28 = i6;
                                if ((i4 & 1572864) == 0) {
                                    i13 = i28 | (composerImpl2.changed(i2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
                                } else {
                                    i13 = i28;
                                }
                            }
                            i14 = i5 & 128;
                            if (i14 == 0) {
                                i13 |= 12582912;
                            } else {
                                if ((i4 & 12582912) == 0) {
                                    i15 = i14;
                                    i13 |= composerImpl2.changed(i3) ? 8388608 : 4194304;
                                }
                                i16 = i5 & 256;
                                if (i16 == 0) {
                                    if ((i4 & 100663296) == 0) {
                                        i17 = i16;
                                        i13 |= composerImpl2.changedInstance(colorProducer) ? 67108864 : 33554432;
                                    }
                                    if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
                                        composerImpl = composerImpl2;
                                        composerImpl.skipToGroupEnd();
                                        z2 = z;
                                        i18 = i3;
                                        modifier3 = modifier2;
                                        textStyle3 = textStyle2;
                                        function13 = function12;
                                        i19 = i10;
                                        i20 = i2;
                                        colorProducer2 = colorProducer;
                                    } else {
                                        if (i27 != 0) {
                                            modifier4 = Modifier.Companion;
                                            i21 = i11;
                                        } else {
                                            i21 = i11;
                                            modifier4 = modifier2;
                                        }
                                        if (i7 != 0) {
                                            TextStyle.Companion.getClass();
                                            textStyle4 = TextStyle.Default;
                                        } else {
                                            textStyle4 = textStyle2;
                                        }
                                        Function1 function14 = i8 != 0 ? null : function12;
                                        if (i9 != 0) {
                                            TextOverflow.Companion.getClass();
                                            int i29 = i13;
                                            i23 = TextOverflow.Clip;
                                            i22 = i29;
                                        } else {
                                            i22 = i13;
                                            i23 = i10;
                                        }
                                        if (i21 != 0) {
                                            i24 = i15;
                                            z3 = true;
                                        } else {
                                            i24 = i15;
                                            z3 = z;
                                        }
                                        if (i12 != 0) {
                                            int i30 = i17;
                                            i26 = Integer.MAX_VALUE;
                                            i25 = i30;
                                        } else {
                                            i25 = i17;
                                            i26 = i2;
                                        }
                                        int i31 = i24 == 0 ? i3 : 1;
                                        ColorProducer colorProducer3 = i25 != 0 ? null : colorProducer;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.BasicText (BasicText.kt:301)");
                                        }
                                        composerImpl = composerImpl2;
                                        m194BasicTextRWo7tUw(str2, modifier4, textStyle4, function14, i23, z3, i26, i31, colorProducer3, (TextAutoSize) null, composerImpl, i22 & 268435454, 512);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        modifier3 = modifier4;
                                        textStyle3 = textStyle4;
                                        function13 = function14;
                                        i19 = i23;
                                        z2 = z3;
                                        i20 = i26;
                                        i18 = i31;
                                        colorProducer2 = colorProducer3;
                                    }
                                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                    if (recomposeScopeImplEndRestartGroup == null) {
                                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.BasicTextKt$BasicText$4
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj, Object obj2) {
                                                ((Number) obj2).intValue();
                                                BasicTextKt.m195BasicTextVhcvRP8(str, modifier3, textStyle3, function13, i19, z2, i20, i18, colorProducer2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), i5);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        return;
                                    }
                                    return;
                                }
                                i13 |= 100663296;
                                i17 = i16;
                                if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup == null) {
                                }
                            }
                            i15 = i14;
                            i16 = i5 & 256;
                            if (i16 == 0) {
                            }
                            i17 = i16;
                            if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                            }
                        }
                        i12 = i5 & 64;
                        if (i12 == 0) {
                        }
                        i14 = i5 & 128;
                        if (i14 == 0) {
                        }
                        i15 = i14;
                        i16 = i5 & 256;
                        if (i16 == 0) {
                        }
                        i17 = i16;
                        if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    i10 = i;
                    i11 = i5 & 32;
                    if (i11 != 0) {
                    }
                    i12 = i5 & 64;
                    if (i12 == 0) {
                    }
                    i14 = i5 & 128;
                    if (i14 == 0) {
                    }
                    i15 = i14;
                    i16 = i5 & 256;
                    if (i16 == 0) {
                    }
                    i17 = i16;
                    if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                function12 = function1;
                i9 = i5 & 16;
                if (i9 == 0) {
                }
                i10 = i;
                i11 = i5 & 32;
                if (i11 != 0) {
                }
                i12 = i5 & 64;
                if (i12 == 0) {
                }
                i14 = i5 & 128;
                if (i14 == 0) {
                }
                i15 = i14;
                i16 = i5 & 256;
                if (i16 == 0) {
                }
                i17 = i16;
                if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            textStyle2 = textStyle;
            i8 = i5 & 8;
            if (i8 != 0) {
            }
            function12 = function1;
            i9 = i5 & 16;
            if (i9 == 0) {
            }
            i10 = i;
            i11 = i5 & 32;
            if (i11 != 0) {
            }
            i12 = i5 & 64;
            if (i12 == 0) {
            }
            i14 = i5 & 128;
            if (i14 == 0) {
            }
            i15 = i14;
            i16 = i5 & 256;
            if (i16 == 0) {
            }
            i17 = i16;
            if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i7 = i5 & 4;
        if (i7 == 0) {
        }
        textStyle2 = textStyle;
        i8 = i5 & 8;
        if (i8 != 0) {
        }
        function12 = function1;
        i9 = i5 & 16;
        if (i9 == 0) {
        }
        i10 = i;
        i11 = i5 & 32;
        if (i11 != 0) {
        }
        i12 = i5 & 64;
        if (i12 == 0) {
        }
        i14 = i5 & 128;
        if (i14 == 0) {
        }
        i15 = i14;
        i16 = i5 & 256;
        if (i16 == 0) {
        }
        i17 = i16;
        if (composerImpl2.shouldExecute(i13 & 1, (i13 & 38347923) == 38347922)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x04ae  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:331:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0111  */
    /* JADX WARN: Type inference failed for: r14v1, types: [androidx.compose.runtime.ComposerImpl] */
    /* JADX WARN: Type inference failed for: r1v17, types: [androidx.compose.runtime.Composer, androidx.compose.runtime.ComposerImpl] */
    /* JADX WARN: Type inference failed for: r22v8, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22, types: [androidx.compose.foundation.text.TextLinkScope, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r6v34, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r6v35 */
    /* JADX WARN: Type inference failed for: r6v40, types: [java.util.ArrayList] */
    /* renamed from: LayoutWithLinksAndInlineContent-11Od_4g, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m196LayoutWithLinksAndInlineContent11Od_4g(final Modifier modifier, final AnnotatedString annotatedString, final Function1 function1, final boolean z, Map map, final TextStyle textStyle, final int i, final boolean z2, final int i2, final int i3, final FontFamily.Resolver resolver, final SelectionController selectionController, final ColorProducer colorProducer, final Function1 function12, final TextAutoSize textAutoSize, Composer composer, final int i4, final int i5, final int i6) {
        boolean z3;
        Modifier modifier2;
        int i7;
        int i8;
        int i9;
        final Map map2;
        ComposerImpl composerImpl;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final ?? r4;
        Function0 function0;
        Map map3;
        Function0 function02;
        Pair pair;
        Function1 function13;
        final MutableState mutableState;
        Function1 function14;
        Object textMeasurePolicy;
        boolean z4;
        Collection arrayList;
        List list;
        int i10;
        int i11;
        ?? r14 = (ComposerImpl) composer;
        r14.startRestartGroup(-2118572703);
        if ((i6 & 1) != 0) {
            i7 = i4 | 6;
            z3 = true;
            modifier2 = modifier;
        } else {
            z3 = true;
            modifier2 = modifier;
            if ((i4 & 6) == 0) {
                i7 = i4 | (r14.changed(modifier2) ? 4 : 2);
            } else {
                i7 = i4;
            }
        }
        if ((i6 & 2) != 0) {
            i7 |= 48;
        } else if ((i4 & 48) == 0) {
            i7 |= r14.changed(annotatedString) ? 32 : 16;
        }
        int i12 = i7;
        if ((i6 & 4) != 0) {
            i12 |= 384;
        } else if ((i4 & 384) == 0) {
            i12 |= r14.changedInstance(function1) ? 256 : 128;
        }
        if ((i6 & 8) != 0) {
            i12 |= 3072;
        } else if ((i4 & 3072) == 0) {
            i12 |= r14.changed(z) ? 2048 : 1024;
        }
        int i13 = i6 & 16;
        if (i13 != 0) {
            i12 |= 24576;
        } else {
            if ((i4 & 24576) == 0) {
                i12 |= r14.changedInstance(map) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            if ((i6 & 32) == 0) {
                i12 |= 196608;
            } else if ((i4 & 196608) == 0) {
                i12 |= r14.changed(textStyle) ? 131072 : 65536;
            }
            if ((i6 & 64) == 0) {
                i12 |= 1572864;
            } else if ((i4 & 1572864) == 0) {
                i12 |= r14.changed(i) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            }
            if ((i6 & 128) == 0) {
                i12 |= 12582912;
            } else {
                if ((i4 & 12582912) == 0) {
                    i12 |= r14.changed(z2) ? 8388608 : 4194304;
                }
                if ((i6 & 256) != 0) {
                    i12 |= 100663296;
                } else {
                    if ((i4 & 100663296) == 0) {
                        i12 |= r14.changed(i2) ? 67108864 : 33554432;
                    }
                    if ((i6 & 512) == 0) {
                        i12 |= 805306368;
                    } else {
                        if ((i4 & 805306368) == 0) {
                            i12 |= r14.changed(i3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                        }
                        if ((i6 & 1024) != 0) {
                            i8 = i5 | 6;
                        } else if ((i5 & 6) == 0) {
                            i8 = i5 | (r14.changedInstance(resolver) ? 4 : 2);
                        } else {
                            i8 = i5;
                        }
                        if ((i6 & 2048) == 0) {
                            if ((i5 & 48) == 0) {
                                i8 |= r14.changedInstance(selectionController) ? 32 : 16;
                            }
                            i9 = i8;
                            if ((i6 & 4096) == 0) {
                                i9 |= 384;
                            } else if ((i5 & 384) == 0) {
                                i9 |= r14.changedInstance(colorProducer) ? 256 : 128;
                            }
                            if ((i6 & 8192) == 0) {
                                i9 |= 3072;
                            } else if ((i5 & 3072) == 0) {
                                i9 |= r14.changedInstance(function12) ? 2048 : 1024;
                            }
                            if ((i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                                i9 |= 24576;
                            } else if ((i5 & 24576) == 0) {
                                i9 |= (32768 & i5) == 0 ? r14.changed(textAutoSize) : r14.changedInstance(textAutoSize) ? 16384 : 8192;
                            }
                            if (r14.shouldExecute(i12 & 1, ((306783379 & i12) == 306783378 || (i9 & 9363) != 9362) ? z3 : false)) {
                                ComposerImpl composerImpl2 = r14;
                                composerImpl2.skipToGroupEnd();
                                map2 = map;
                                composerImpl = composerImpl2;
                            } else {
                                Map mapEmptyMap = i13 != 0 ? MapsKt__MapsKt.emptyMap() : map;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.LayoutWithLinksAndInlineContent (BasicText.kt:630)");
                                }
                                boolean zHasLinks = TextAnnotatedStringNodeKt.hasLinks(annotatedString);
                                Composer.Companion companion = Composer.Companion;
                                if (zHasLinks) {
                                    r14.startReplaceGroup(-613484007);
                                    boolean z5 = (i12 & 112) == 32 ? z3 : false;
                                    Object objRememberedValue = r14.rememberedValue();
                                    if (!z5) {
                                        companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new TextLinkScope(annotatedString);
                                            r14.updateRememberedValue(objRememberedValue);
                                        }
                                        r14.end(false);
                                        r4 = (TextLinkScope) objRememberedValue;
                                    }
                                } else {
                                    r14.startReplaceGroup(-613418350);
                                    r14.end(false);
                                    r4 = 0;
                                }
                                if (TextAnnotatedStringNodeKt.hasLinks(annotatedString)) {
                                    r14.startReplaceGroup(-613220135);
                                    boolean zChanged = ((i12 & 112) == 32 ? z3 : false) | r14.changed(r4);
                                    Object objRememberedValue2 = r14.rememberedValue();
                                    if (!zChanged) {
                                        companion.getClass();
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            objRememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$styledText$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    AnnotatedString annotatedString2;
                                                    TextLinkScope textLinkScope = r4;
                                                    if (textLinkScope != null) {
                                                        SnapshotStateList snapshotStateList = textLinkScope.annotators;
                                                        if (snapshotStateList.isEmpty()) {
                                                            annotatedString2 = textLinkScope.text;
                                                        } else {
                                                            TextAnnotatorScope textAnnotatorScope = new TextAnnotatorScope(textLinkScope.text);
                                                            int size = snapshotStateList.size();
                                                            for (int i14 = 0; i14 < size; i14++) {
                                                                ((Function1) snapshotStateList.get(i14)).mo781invoke(textAnnotatorScope);
                                                            }
                                                            annotatedString2 = textAnnotatorScope.styledText;
                                                        }
                                                        textLinkScope.text = annotatedString2;
                                                        if (annotatedString2 != null) {
                                                            return annotatedString2;
                                                        }
                                                    }
                                                    return annotatedString;
                                                }
                                            };
                                            r14.updateRememberedValue(objRememberedValue2);
                                        }
                                        function0 = (Function0) objRememberedValue2;
                                        r14.end(false);
                                    }
                                } else {
                                    r14.startReplaceGroup(-613122857);
                                    boolean z6 = (i12 & 112) == 32 ? z3 : false;
                                    Object objRememberedValue3 = r14.rememberedValue();
                                    if (!z6) {
                                        companion.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$styledText$2$1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return annotatedString;
                                                }
                                            };
                                            r14.updateRememberedValue(objRememberedValue3);
                                        }
                                        function0 = (Function0) objRememberedValue3;
                                        r14.end(false);
                                    }
                                }
                                if (!z) {
                                    map3 = mapEmptyMap;
                                    function02 = function0;
                                    pair = new Pair(null, null);
                                } else if (mapEmptyMap != null) {
                                    Pair pair2 = AnnotatedStringResolveInlineContentKt.EmptyInlineContent;
                                    if (mapEmptyMap.isEmpty()) {
                                        map3 = mapEmptyMap;
                                        function02 = function0;
                                        pair = AnnotatedStringResolveInlineContentKt.EmptyInlineContent;
                                    } else {
                                        int length = annotatedString.text.length();
                                        List list2 = annotatedString.annotations;
                                        if (list2 != null) {
                                            function02 = function0;
                                            arrayList = new ArrayList(list2.size());
                                            int size = list2.size();
                                            int i14 = 0;
                                            while (i14 < size) {
                                                List list3 = list2;
                                                AnnotatedString.Range range = (AnnotatedString.Range) list2.get(i14);
                                                int i15 = size;
                                                if (range.item instanceof StringAnnotation) {
                                                    i11 = i14;
                                                    String str = range.tag;
                                                    if ("androidx.compose.foundation.text.inlineContent".equals(str)) {
                                                        int i16 = range.start;
                                                        int i17 = range.end;
                                                        if (AnnotatedStringKt.intersect(0, length, i16, i17)) {
                                                            arrayList.add(new AnnotatedString.Range(((StringAnnotation) range.item).value, i16, i17, str));
                                                        }
                                                    }
                                                } else {
                                                    i11 = i14;
                                                }
                                                i14 = i11 + 1;
                                                size = i15;
                                                list2 = list3;
                                            }
                                        } else {
                                            function02 = function0;
                                            arrayList = EmptyList.INSTANCE;
                                        }
                                        ArrayList arrayList2 = new ArrayList();
                                        ArrayList arrayList3 = new ArrayList();
                                        int size2 = arrayList.size();
                                        int i18 = 0;
                                        List list4 = arrayList;
                                        while (i18 < size2) {
                                            AnnotatedString.Range range2 = (AnnotatedString.Range) list4.get(i18);
                                            InlineTextContent inlineTextContent = (InlineTextContent) mapEmptyMap.get(range2.item);
                                            Map map4 = mapEmptyMap;
                                            if (inlineTextContent != null) {
                                                list = list4;
                                                Placeholder placeholder = inlineTextContent.placeholder;
                                                i10 = size2;
                                                int i19 = range2.start;
                                                int i20 = range2.end;
                                                arrayList2.add(new AnnotatedString.Range(placeholder, i19, i20));
                                                arrayList3.add(new AnnotatedString.Range(inlineTextContent.children, i19, i20));
                                            } else {
                                                list = list4;
                                                i10 = size2;
                                            }
                                            i18++;
                                            list4 = list;
                                            size2 = i10;
                                            mapEmptyMap = map4;
                                        }
                                        map3 = mapEmptyMap;
                                        pair = new Pair(arrayList2, arrayList3);
                                    }
                                }
                                List list5 = (List) pair.component1();
                                List list6 = (List) pair.component2();
                                if (z) {
                                    Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(r14, -612806750, companion);
                                    if (objM == Composer.Companion.Empty) {
                                        function13 = null;
                                        objM = SnapshotStateKt.mutableStateOf$default(null);
                                        r14.updateRememberedValue(objM);
                                    } else {
                                        function13 = null;
                                    }
                                    mutableState = (MutableState) objM;
                                    r14.end(false);
                                } else {
                                    function13 = null;
                                    r14.startReplaceGroup(-612718990);
                                    r14.end(false);
                                    mutableState = null;
                                }
                                if (z) {
                                    r14.startReplaceGroup(-612625741);
                                    boolean zChanged2 = r14.changed(mutableState);
                                    Object objRememberedValue4 = r14.rememberedValue();
                                    if (!zChanged2) {
                                        companion.getClass();
                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$onPlaceholderLayout$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    List list7 = (List) obj;
                                                    MutableState<List<Rect>> mutableState2 = mutableState;
                                                    if (mutableState2 != null) {
                                                        mutableState2.setValue(list7);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            r14.updateRememberedValue(objRememberedValue4);
                                        }
                                        r14.end(false);
                                        function14 = (Function1) objRememberedValue4;
                                    }
                                } else {
                                    r14.startReplaceGroup(-612554318);
                                    r14.end(false);
                                    function14 = function13;
                                }
                                AnnotatedString annotatedString2 = (AnnotatedString) function02.invoke();
                                boolean zChangedInstance = r14.changedInstance(r4) | ((i12 & 896) == 256 ? z3 : false);
                                Object objRememberedValue5 = r14.rememberedValue();
                                if (!zChangedInstance) {
                                    companion.getClass();
                                    if (objRememberedValue5 == Composer.Companion.Empty) {
                                        objRememberedValue5 = new Function1() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$2$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                TextLayoutResult textLayoutResult = (TextLayoutResult) obj;
                                                TextLinkScope textLinkScope = r4;
                                                if (textLinkScope != null) {
                                                    ((SnapshotMutableStateImpl) textLinkScope.textLayoutResult$delegate).setValue(textLayoutResult);
                                                }
                                                Function1 function15 = function1;
                                                if (function15 != null) {
                                                    function15.mo781invoke(textLayoutResult);
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        r14.updateRememberedValue(objRememberedValue5);
                                    }
                                    ?? r1 = r14;
                                    final MutableState mutableState2 = mutableState;
                                    ?? r22 = function13;
                                    int i21 = i12;
                                    Modifier modifierM197textModifierCL7eQgs = m197textModifierCL7eQgs(modifier2, annotatedString2, textStyle, (Function1) objRememberedValue5, i, z2, i2, i3, resolver, list5, function14, selectionController, colorProducer, function12, textAutoSize);
                                    if (z) {
                                        r1.startReplaceGroup(-611365560);
                                        boolean zChangedInstance2 = r1.changedInstance(r4);
                                        Object objRememberedValue6 = r1.rememberedValue();
                                        if (!zChangedInstance2) {
                                            companion.getClass();
                                            if (objRememberedValue6 == Composer.Companion.Empty) {
                                                objRememberedValue6 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$4$1
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        TextLinkScope textLinkScope = r4;
                                                        return Boolean.valueOf(textLinkScope != null ? ((Boolean) new TextLinkScope$shouldMeasureLinks$1(textLinkScope).invoke()).booleanValue() : false);
                                                    }
                                                };
                                                r1.updateRememberedValue(objRememberedValue6);
                                            }
                                            Function0 function03 = (Function0) objRememberedValue6;
                                            boolean zChanged3 = r1.changed(mutableState2);
                                            Object objRememberedValue7 = r1.rememberedValue();
                                            if (!zChanged3) {
                                                companion.getClass();
                                                if (objRememberedValue7 == Composer.Companion.Empty) {
                                                    objRememberedValue7 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$5$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            MutableState<List<Rect>> mutableState3 = mutableState2;
                                                            if (mutableState3 != null) {
                                                                return (List) mutableState3.getValue();
                                                            }
                                                            return null;
                                                        }
                                                    };
                                                    r1.updateRememberedValue(objRememberedValue7);
                                                }
                                                textMeasurePolicy = new TextMeasurePolicy(function03, (Function0) objRememberedValue7);
                                                r1.end(false);
                                            }
                                        }
                                    } else {
                                        r1.startReplaceGroup(-611542291);
                                        boolean zChangedInstance3 = r1.changedInstance(r4);
                                        Object objRememberedValue8 = r1.rememberedValue();
                                        if (!zChangedInstance3) {
                                            companion.getClass();
                                            if (objRememberedValue8 == Composer.Companion.Empty) {
                                                objRememberedValue8 = new Function0() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$3$1
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        TextLinkScope textLinkScope = r4;
                                                        return Boolean.valueOf(textLinkScope != null ? ((Boolean) new TextLinkScope$shouldMeasureLinks$1(textLinkScope).invoke()).booleanValue() : false);
                                                    }
                                                };
                                                r1.updateRememberedValue(objRememberedValue8);
                                            }
                                            textMeasurePolicy = new LinksTextMeasurePolicy((Function0) objRememberedValue8);
                                            r1.end(false);
                                        }
                                    }
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(r1);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = r1.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(r1, modifierM197textModifierCL7eQgs);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function04 = ComposeUiNode.Companion.Constructor;
                                    if (r1.applier == null) {
                                        ComposablesKt.invalidApplier();
                                        throw r22;
                                    }
                                    r1.startReusableNode();
                                    if (r1.inserting) {
                                        r1.createNode(function04);
                                    } else {
                                        r1.useNode();
                                    }
                                    Updater.m337setimpl(r1, textMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(r1, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (r1.inserting || !Intrinsics.areEqual(r1.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, r1, currentCompositeKeyHash, function2);
                                    }
                                    Updater.m337setimpl(r1, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    if (r4 == 0) {
                                        r1.startReplaceGroup(-509592027);
                                        z4 = false;
                                        r1.end(false);
                                    } else {
                                        z4 = false;
                                        r1.startReplaceGroup(537750876);
                                        r4.LinksComposables(0, r1);
                                        r1.end(false);
                                        Unit unit = Unit.INSTANCE;
                                    }
                                    if (list6 == null) {
                                        r1.startReplaceGroup(-509541249);
                                    } else {
                                        r1.startReplaceGroup(-509541248);
                                        AnnotatedStringResolveInlineContentKt.InlineChildren(annotatedString, list6, r1, (i21 >> 3) & 14);
                                        Unit unit2 = Unit.INSTANCE;
                                        z4 = false;
                                    }
                                    r1.end(z4);
                                    r1.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    map2 = map3;
                                    composerImpl = r1;
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.BasicTextKt$LayoutWithLinksAndInlineContent$6
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        BasicTextKt.m196LayoutWithLinksAndInlineContent11Od_4g(modifier, annotatedString, function1, z, map2, textStyle, i, z2, i2, i3, resolver, selectionController, colorProducer, function12, textAutoSize, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), RecomposeScopeImplKt.updateChangedFlags(i5), i6);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i8 |= 48;
                        i9 = i8;
                        if ((i6 & 4096) == 0) {
                        }
                        if ((i6 & 8192) == 0) {
                        }
                        if ((i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                        }
                        if (r14.shouldExecute(i12 & 1, ((306783379 & i12) == 306783378 || (i9 & 9363) != 9362) ? z3 : false)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    if ((i6 & 1024) != 0) {
                    }
                    if ((i6 & 2048) == 0) {
                    }
                    i9 = i8;
                    if ((i6 & 4096) == 0) {
                    }
                    if ((i6 & 8192) == 0) {
                    }
                    if ((i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                    }
                    if (r14.shouldExecute(i12 & 1, ((306783379 & i12) == 306783378 || (i9 & 9363) != 9362) ? z3 : false)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                if ((i6 & 512) == 0) {
                }
                if ((i6 & 1024) != 0) {
                }
                if ((i6 & 2048) == 0) {
                }
                i9 = i8;
                if ((i6 & 4096) == 0) {
                }
                if ((i6 & 8192) == 0) {
                }
                if ((i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                }
                if (r14.shouldExecute(i12 & 1, ((306783379 & i12) == 306783378 || (i9 & 9363) != 9362) ? z3 : false)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            if ((i6 & 256) != 0) {
            }
            if ((i6 & 512) == 0) {
            }
            if ((i6 & 1024) != 0) {
            }
            if ((i6 & 2048) == 0) {
            }
            i9 = i8;
            if ((i6 & 4096) == 0) {
            }
            if ((i6 & 8192) == 0) {
            }
            if ((i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
            }
            if (r14.shouldExecute(i12 & 1, ((306783379 & i12) == 306783378 || (i9 & 9363) != 9362) ? z3 : false)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        if ((i6 & 32) == 0) {
        }
        if ((i6 & 64) == 0) {
        }
        if ((i6 & 128) == 0) {
        }
        if ((i6 & 256) != 0) {
        }
        if ((i6 & 512) == 0) {
        }
        if ((i6 & 1024) != 0) {
        }
        if ((i6 & 2048) == 0) {
        }
        i9 = i8;
        if ((i6 & 4096) == 0) {
        }
        if ((i6 & 8192) == 0) {
        }
        if ((i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
        }
        if (r14.shouldExecute(i12 & 1, ((306783379 & i12) == 306783378 || (i9 & 9363) != 9362) ? z3 : false)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final List access$measureWithTextRangeMeasureConstraints(List list, Function0 function0) {
        TextRangeLayoutMeasureResult textRangeLayoutMeasureResult;
        if (!((Boolean) function0.invoke()).booleanValue()) {
            return null;
        }
        new TextRangeLayoutMeasureScope();
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            TextLinkScope$$ExternalSyntheticLambda0 textLinkScope$$ExternalSyntheticLambda0 = (TextLinkScope$$ExternalSyntheticLambda0) ((TextRangeLayoutModifier) measurable.getParentData()).measurePolicy;
            TextLayoutResult textLayoutResult = (TextLayoutResult) ((SnapshotMutableStateImpl) textLinkScope$$ExternalSyntheticLambda0.f$0.textLayoutResult$delegate).getValue();
            if (textLayoutResult == null) {
                textRangeLayoutMeasureResult = new TextRangeLayoutMeasureResult(0, 0, new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$textRange$1$layoutResult$1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        IntOffset.Companion.getClass();
                        return IntOffset.m849boximpl(0L);
                    }
                });
            } else {
                AnnotatedString.Range rangeCalculateVisibleLinkRange = TextLinkScope.calculateVisibleLinkRange(textLinkScope$$ExternalSyntheticLambda0.f$1, textLayoutResult);
                if (rangeCalculateVisibleLinkRange == null) {
                    textRangeLayoutMeasureResult = new TextRangeLayoutMeasureResult(0, 0, new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$textRange$1$updatedRange$1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            IntOffset.Companion.getClass();
                            return IntOffset.m849boximpl(0L);
                        }
                    });
                } else {
                    final IntRect intRectRoundToIntRect = IntRectKt.roundToIntRect(textLayoutResult.getPathForRange(rangeCalculateVisibleLinkRange.start, rangeCalculateVisibleLinkRange.end).getBounds());
                    textRangeLayoutMeasureResult = new TextRangeLayoutMeasureResult(intRectRoundToIntRect.getWidth(), intRectRoundToIntRect.getHeight(), new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$textRange$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return IntOffset.m849boximpl(intRectRoundToIntRect.m859getTopLeftnOccac());
                        }
                    });
                }
            }
            Constraints.Companion.getClass();
            int i2 = textRangeLayoutMeasureResult.width;
            int i3 = textRangeLayoutMeasureResult.height;
            arrayList.add(new Pair(measurable.mo610measureBRTryo0(Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(i2, i2, i3, i3)), textRangeLayoutMeasureResult.place));
        }
        return arrayList;
    }

    /* renamed from: textModifier-CL7eQgs, reason: not valid java name */
    public static final Modifier m197textModifierCL7eQgs(Modifier modifier, AnnotatedString annotatedString, TextStyle textStyle, Function1 function1, int i, boolean z, int i2, int i3, FontFamily.Resolver resolver, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, Function1 function13, TextAutoSize textAutoSize) {
        if (selectionController == null) {
            return modifier.then(Modifier.Companion).then(new TextAnnotatedStringElement(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, null, colorProducer, textAutoSize, function13, null));
        }
        return modifier.then(selectionController.modifier).then(new SelectableTextAnnotatedStringElement(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:159:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0104  */
    /* renamed from: BasicText-RWo7tUw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m193BasicTextRWo7tUw(final AnnotatedString annotatedString, Modifier modifier, TextStyle textStyle, Function1 function1, int i, boolean z, int i2, int i3, Map map, ColorProducer colorProducer, Composer composer, final int i4, final int i5) {
        AnnotatedString annotatedString2;
        int i6;
        Modifier modifier2;
        int i7;
        TextStyle textStyle2;
        int i8;
        Function1 function12;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        ComposerImpl composerImpl;
        final boolean z2;
        final int i20;
        final ColorProducer colorProducer2;
        final Modifier modifier3;
        final TextStyle textStyle3;
        final Function1 function13;
        final int i21;
        final int i22;
        final Map map2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i23;
        TextStyle textStyle4;
        int i24;
        int i25;
        int i26;
        int i27;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1064305212);
        if ((i5 & 1) != 0) {
            i6 = i4 | 6;
            annotatedString2 = annotatedString;
        } else {
            annotatedString2 = annotatedString;
            if ((i4 & 6) == 0) {
                i6 = (composerImpl2.changed(annotatedString2) ? 4 : 2) | i4;
            } else {
                i6 = i4;
            }
        }
        int i28 = i5 & 2;
        if (i28 != 0) {
            i6 |= 48;
        } else {
            if ((i4 & 48) == 0) {
                modifier2 = modifier;
                i6 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i7 = i5 & 4;
            if (i7 == 0) {
                i6 |= 384;
            } else {
                if ((i4 & 384) == 0) {
                    textStyle2 = textStyle;
                    i6 |= composerImpl2.changed(textStyle2) ? 256 : 128;
                }
                i8 = i5 & 8;
                if (i8 != 0) {
                    i6 |= 3072;
                } else {
                    if ((i4 & 3072) == 0) {
                        function12 = function1;
                        i6 |= composerImpl2.changedInstance(function12) ? 2048 : 1024;
                    }
                    i9 = i5 & 16;
                    if (i9 == 0) {
                        i6 |= 24576;
                    } else {
                        if ((i4 & 24576) == 0) {
                            i10 = i;
                            i6 |= composerImpl2.changed(i10) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i11 = i5 & 32;
                        if (i11 != 0) {
                            i6 |= 196608;
                        } else {
                            if ((196608 & i4) == 0) {
                                i6 |= composerImpl2.changed(z) ? 131072 : 65536;
                            }
                            i12 = i5 & 64;
                            if (i12 == 0) {
                                i13 = i6 | 1572864;
                            } else {
                                int i29 = i6;
                                if ((i4 & 1572864) == 0) {
                                    i13 = i29 | (composerImpl2.changed(i2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
                                } else {
                                    i13 = i29;
                                }
                            }
                            i14 = i5 & 128;
                            if (i14 == 0) {
                                i13 |= 12582912;
                            } else {
                                if ((i4 & 12582912) == 0) {
                                    i15 = i14;
                                    i13 |= composerImpl2.changed(i3) ? 8388608 : 4194304;
                                }
                                i16 = i5 & 256;
                                if (i16 != 0) {
                                    i13 |= 100663296;
                                } else {
                                    if ((i4 & 100663296) == 0) {
                                        i17 = i16;
                                        i13 |= composerImpl2.changedInstance(map) ? 67108864 : 33554432;
                                    }
                                    i18 = i5 & 512;
                                    if (i18 != 0) {
                                        if ((i4 & 805306368) == 0) {
                                            i19 = i18;
                                            i13 |= composerImpl2.changedInstance(colorProducer) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                                        }
                                        if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                                            Modifier modifier4 = i28 != 0 ? Modifier.Companion : modifier2;
                                            if (i7 != 0) {
                                                TextStyle.Companion.getClass();
                                                textStyle4 = TextStyle.Default;
                                                i23 = i12;
                                            } else {
                                                i23 = i12;
                                                textStyle4 = textStyle2;
                                            }
                                            int i30 = i13;
                                            Function1 function14 = i8 != 0 ? null : function12;
                                            if (i9 != 0) {
                                                TextOverflow.Companion.getClass();
                                                int i31 = i15;
                                                i25 = TextOverflow.Clip;
                                                i24 = i31;
                                            } else {
                                                i24 = i15;
                                                i25 = i10;
                                            }
                                            int i32 = i17;
                                            boolean z3 = i11 != 0 ? true : z;
                                            if (i23 != 0) {
                                                int i33 = i19;
                                                i27 = Integer.MAX_VALUE;
                                                i26 = i33;
                                            } else {
                                                i26 = i19;
                                                i27 = i2;
                                            }
                                            int i34 = i24 == 0 ? i3 : 1;
                                            Map mapEmptyMap = i32 != 0 ? MapsKt__MapsKt.emptyMap() : map;
                                            ColorProducer colorProducer3 = i26 != 0 ? null : colorProducer;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.foundation.text.BasicText (BasicText.kt:343)");
                                            }
                                            composerImpl = composerImpl2;
                                            m192BasicTextCL7eQgs(annotatedString2, modifier4, textStyle4, function14, i25, z3, i27, i34, mapEmptyMap, colorProducer3, null, composerImpl, i30 & 2147483646, 0, 1024);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            modifier3 = modifier4;
                                            textStyle3 = textStyle4;
                                            function13 = function14;
                                            i21 = i25;
                                            z2 = z3;
                                            i22 = i27;
                                            i20 = i34;
                                            map2 = mapEmptyMap;
                                            colorProducer2 = colorProducer3;
                                        } else {
                                            composerImpl = composerImpl2;
                                            composerImpl.skipToGroupEnd();
                                            z2 = z;
                                            i20 = i3;
                                            colorProducer2 = colorProducer;
                                            modifier3 = modifier2;
                                            textStyle3 = textStyle2;
                                            function13 = function12;
                                            i21 = i10;
                                            i22 = i2;
                                            map2 = map;
                                        }
                                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                        if (recomposeScopeImplEndRestartGroup != null) {
                                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.BasicTextKt$BasicText$5
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(Object obj, Object obj2) {
                                                    ((Number) obj2).intValue();
                                                    BasicTextKt.m193BasicTextRWo7tUw(annotatedString, modifier3, textStyle3, function13, i21, z2, i22, i20, map2, colorProducer2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i4 | 1), i5);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            return;
                                        }
                                        return;
                                    }
                                    i13 |= 805306368;
                                    i19 = i18;
                                    if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                                    }
                                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                    if (recomposeScopeImplEndRestartGroup != null) {
                                    }
                                }
                                i17 = i16;
                                i18 = i5 & 512;
                                if (i18 != 0) {
                                }
                                i19 = i18;
                                if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                                }
                                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                                if (recomposeScopeImplEndRestartGroup != null) {
                                }
                            }
                            i15 = i14;
                            i16 = i5 & 256;
                            if (i16 != 0) {
                            }
                            i17 = i16;
                            i18 = i5 & 512;
                            if (i18 != 0) {
                            }
                            i19 = i18;
                            if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                            }
                        }
                        i12 = i5 & 64;
                        if (i12 == 0) {
                        }
                        i14 = i5 & 128;
                        if (i14 == 0) {
                        }
                        i15 = i14;
                        i16 = i5 & 256;
                        if (i16 != 0) {
                        }
                        i17 = i16;
                        i18 = i5 & 512;
                        if (i18 != 0) {
                        }
                        i19 = i18;
                        if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                        }
                    }
                    i10 = i;
                    i11 = i5 & 32;
                    if (i11 != 0) {
                    }
                    i12 = i5 & 64;
                    if (i12 == 0) {
                    }
                    i14 = i5 & 128;
                    if (i14 == 0) {
                    }
                    i15 = i14;
                    i16 = i5 & 256;
                    if (i16 != 0) {
                    }
                    i17 = i16;
                    i18 = i5 & 512;
                    if (i18 != 0) {
                    }
                    i19 = i18;
                    if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                function12 = function1;
                i9 = i5 & 16;
                if (i9 == 0) {
                }
                i10 = i;
                i11 = i5 & 32;
                if (i11 != 0) {
                }
                i12 = i5 & 64;
                if (i12 == 0) {
                }
                i14 = i5 & 128;
                if (i14 == 0) {
                }
                i15 = i14;
                i16 = i5 & 256;
                if (i16 != 0) {
                }
                i17 = i16;
                i18 = i5 & 512;
                if (i18 != 0) {
                }
                i19 = i18;
                if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            textStyle2 = textStyle;
            i8 = i5 & 8;
            if (i8 != 0) {
            }
            function12 = function1;
            i9 = i5 & 16;
            if (i9 == 0) {
            }
            i10 = i;
            i11 = i5 & 32;
            if (i11 != 0) {
            }
            i12 = i5 & 64;
            if (i12 == 0) {
            }
            i14 = i5 & 128;
            if (i14 == 0) {
            }
            i15 = i14;
            i16 = i5 & 256;
            if (i16 != 0) {
            }
            i17 = i16;
            i18 = i5 & 512;
            if (i18 != 0) {
            }
            i19 = i18;
            if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i7 = i5 & 4;
        if (i7 == 0) {
        }
        textStyle2 = textStyle;
        i8 = i5 & 8;
        if (i8 != 0) {
        }
        function12 = function1;
        i9 = i5 & 16;
        if (i9 == 0) {
        }
        i10 = i;
        i11 = i5 & 32;
        if (i11 != 0) {
        }
        i12 = i5 & 64;
        if (i12 == 0) {
        }
        i14 = i5 & 128;
        if (i14 == 0) {
        }
        i15 = i14;
        i16 = i5 & 256;
        if (i16 != 0) {
        }
        i17 = i16;
        i18 = i5 & 512;
        if (i18 != 0) {
        }
        i19 = i18;
        if (composerImpl2.shouldExecute(i13 & 1, (i13 & 306783379) != 306783378)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
