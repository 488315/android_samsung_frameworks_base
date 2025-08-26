package androidx.compose.ui.window;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public abstract class AndroidPopup_androidKt {
    public static final DynamicProvidableCompositionLocal LocalPopupTestTag = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$LocalPopupTestTag$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return "DEFAULT_TEST_TAG";
        }
    });

    /* JADX WARN: Removed duplicated region for block: B:105:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:136:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Popup(PopupPositionProvider popupPositionProvider, Function0 function0, PopupProperties popupProperties, final Function2 function2, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        Function0 function02;
        int i4;
        PopupProperties popupProperties2;
        final Function0 function03;
        final PopupProperties popupProperties3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i5;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        boolean z;
        boolean z2;
        String str;
        Composer$Companion$Empty$1 composer$Companion$Empty$12;
        boolean zChangedInstance;
        Object objRememberedValue;
        boolean zChangedInstance2;
        Object objRememberedValue2;
        boolean zChangedInstance3;
        Object objRememberedValue3;
        Throwable th;
        boolean zChangedInstance4;
        Object objRememberedValue4;
        boolean zChangedInstance5;
        Object objRememberedValue5;
        final PopupPositionProvider popupPositionProvider2 = popupPositionProvider;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-830247068);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(popupPositionProvider2) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i6 = i2 & 2;
        if (i6 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                function02 = function0;
                i3 |= composerImpl.changedInstance(function02) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 != 0) {
                if ((i & 384) == 0) {
                    popupProperties2 = popupProperties;
                    i3 |= composerImpl.changed(popupProperties2) ? 256 : 128;
                }
                if ((i2 & 8) != 0) {
                    i3 |= 3072;
                } else if ((i & 3072) == 0) {
                    i3 |= composerImpl.changedInstance(function2) ? 2048 : 1024;
                }
                if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
                    final Function0 function04 = i6 != 0 ? null : function02;
                    final PopupProperties popupProperties4 = i4 != 0 ? new PopupProperties(false, false, false, false, 15, (DefaultConstructorMarker) null) : popupProperties2;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.ui.window.Popup (AndroidPopup.android.kt:297)");
                    }
                    View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
                    Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                    String str2 = (String) composerImpl.consume(LocalPopupTestTag);
                    final LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
                    ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
                    final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerImpl);
                    UUID uuid = (UUID) RememberSaveableKt.rememberSaveable(new Object[0], null, null, new Function0() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$popupId$1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return UUID.randomUUID();
                        }
                    }, composerImpl, 3072, 6);
                    Object objRememberedValue6 = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$13 = Composer.Companion.Empty;
                    if (objRememberedValue6 == composer$Companion$Empty$13) {
                        z2 = false;
                        composer$Companion$Empty$1 = composer$Companion$Empty$13;
                        i5 = i3;
                        z = true;
                        str = str2;
                        final PopupLayout popupLayout = new PopupLayout(function04, popupProperties4, str, view, density, popupPositionProvider2, uuid, null, 128, null);
                        popupPositionProvider2 = popupPositionProvider2;
                        ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(1302892335, true, new Function2() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$popupLayout$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                int iIntValue = ((Number) obj2).intValue();
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.ui.window.Popup.<anonymous>.<anonymous>.<anonymous> (AndroidPopup.android.kt:317)");
                                    }
                                    Modifier modifierSemantics = SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$popupLayout$1$1$1.1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj3) {
                                            KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                            SemanticsProperties.INSTANCE.getClass();
                                            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsPopup;
                                            Unit unit = Unit.INSTANCE;
                                            ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj3)).set(semanticsPropertyKey, unit);
                                            return unit;
                                        }
                                    });
                                    boolean zChangedInstance6 = composerImpl2.changedInstance(popupLayout);
                                    final PopupLayout popupLayout2 = popupLayout;
                                    Object objRememberedValue7 = composerImpl2.rememberedValue();
                                    if (!zChangedInstance6) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue7 == Composer.Companion.Empty) {
                                            objRememberedValue7 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$popupLayout$1$1$1$2$1
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj3) {
                                                    long j = ((IntSize) obj3).packedValue;
                                                    PopupLayout popupLayout3 = popupLayout2;
                                                    ((SnapshotMutableStateImpl) popupLayout3.popupContentSize$delegate).setValue(IntSize.m861boximpl(j));
                                                    popupLayout2.updatePosition();
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue7);
                                        }
                                        Modifier modifierAlpha = AlphaKt.alpha(OnRemeasuredModifierKt.onSizeChanged(modifierSemantics, (Function1) objRememberedValue7), ((Boolean) popupLayout.canCalculatePosition$delegate.getValue()).booleanValue() ? 1.0f : 0.0f);
                                        State<Function2> state = mutableStateRememberUpdatedState;
                                        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidPopup_androidKt.LocalPopupTestTag;
                                        Function2 function22 = (Function2) state.getValue();
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierAlpha);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function05 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl2.applier == null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl2.startReusableNode();
                                        if (composerImpl2.inserting) {
                                            composerImpl2.createNode(function05);
                                        } else {
                                            composerImpl2.useNode();
                                        }
                                        Updater.m337setimpl(composerImpl2, new MeasurePolicy() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$SimpleStack$1
                                            @Override // androidx.compose.ui.layout.MeasurePolicy
                                            /* renamed from: measure-3p2s80s */
                                            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                                                int size = list.size();
                                                if (size == 0) {
                                                    return measureScope.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$SimpleStack$1.1
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj3) {
                                                            return Unit.INSTANCE;
                                                        }
                                                    });
                                                }
                                                if (size == 1) {
                                                    final Placeable placeableMo610measureBRTryo0 = ((Measurable) list.get(0)).mo610measureBRTryo0(j);
                                                    return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$SimpleStack$1.2
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj3) {
                                                            ((Placeable.PlacementScope) obj3).placeRelative(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                                                            return Unit.INSTANCE;
                                                        }
                                                    });
                                                }
                                                final ArrayList arrayList = new ArrayList(list.size());
                                                int size2 = list.size();
                                                int iMax = 0;
                                                int iMax2 = 0;
                                                for (int i7 = 0; i7 < size2; i7++) {
                                                    Placeable placeableMo610measureBRTryo02 = ((Measurable) list.get(i7)).mo610measureBRTryo0(j);
                                                    iMax = Math.max(iMax, placeableMo610measureBRTryo02.width);
                                                    iMax2 = Math.max(iMax2, placeableMo610measureBRTryo02.height);
                                                    arrayList.add(placeableMo610measureBRTryo02);
                                                }
                                                return measureScope.layout$1(iMax, iMax2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$SimpleStack$1.3
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj3) {
                                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj3;
                                                        int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(arrayList);
                                                        if (lastIndex >= 0) {
                                                            int i8 = 0;
                                                            while (true) {
                                                                placementScope.placeRelative(arrayList.get(i8), 0, 0, 0.0f);
                                                                if (i8 == lastIndex) {
                                                                    break;
                                                                }
                                                                i8++;
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                });
                                            }
                                        }, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                                        }
                                        Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        function22.invoke(composerImpl2, 0);
                                        composerImpl2.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        popupLayout.setParentCompositionContext(compositionContextImplRememberCompositionContext);
                        ((SnapshotMutableStateImpl) popupLayout.content$delegate).setValue(composableLambdaImpl);
                        popupLayout.shouldCreateCompositionOnAttachedToWindow = true;
                        composerImpl = composerImpl;
                        composerImpl.updateRememberedValue(popupLayout);
                        objRememberedValue6 = popupLayout;
                    } else {
                        i5 = i3;
                        composer$Companion$Empty$1 = composer$Companion$Empty$13;
                        z = true;
                        z2 = false;
                        str = str2;
                    }
                    final PopupLayout popupLayout2 = (PopupLayout) objRememberedValue6;
                    int i7 = i5;
                    int i8 = i7 & 112;
                    int i9 = i7 & 896;
                    boolean zChangedInstance6 = composerImpl.changedInstance(popupLayout2) | (i8 == 32 ? z : z2) | (i9 == 256 ? z : z2) | composerImpl.changed(str) | composerImpl.changed(layoutDirection);
                    Object objRememberedValue7 = composerImpl.rememberedValue();
                    if (zChangedInstance6) {
                        composer$Companion$Empty$12 = composer$Companion$Empty$1;
                    } else {
                        composer$Companion$Empty$12 = composer$Companion$Empty$1;
                        if (objRememberedValue7 == composer$Companion$Empty$12) {
                        }
                        EffectsKt.DisposableEffect(popupLayout2, (Function1) objRememberedValue7, composerImpl);
                        zChangedInstance = composerImpl.changedInstance(popupLayout2) | (i8 != 32 ? z : z2) | (i9 != 256 ? z : z2) | composerImpl.changed(str) | composerImpl.changed(layoutDirection);
                        objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance || objRememberedValue == composer$Companion$Empty$12) {
                            final String str3 = str;
                            objRememberedValue = new Function0() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$3$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    popupLayout2.updateParameters(function04, popupProperties4, layoutDirection);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        EffectsKt.SideEffect((Function0) objRememberedValue, composerImpl);
                        zChangedInstance2 = composerImpl.changedInstance(popupLayout2) | ((i7 & 14) != 4 ? z : z2);
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (!zChangedInstance2 || objRememberedValue2 == composer$Companion$Empty$12) {
                            objRememberedValue2 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$4$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    PopupLayout popupLayout3 = popupLayout2;
                                    popupLayout3.positionProvider = popupPositionProvider2;
                                    popupLayout3.updatePosition();
                                    return new DisposableEffectResult() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$4$1$invoke$$inlined$onDispose$1
                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                        public final void dispose() {
                                        }
                                    };
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        EffectsKt.DisposableEffect(popupPositionProvider2, (Function1) objRememberedValue2, composerImpl);
                        zChangedInstance3 = composerImpl.changedInstance(popupLayout2);
                        objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChangedInstance3 || objRememberedValue3 == composer$Companion$Empty$12) {
                            th = null;
                            objRememberedValue3 = new AndroidPopup_androidKt$Popup$5$1(popupLayout2, null);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        } else {
                            th = null;
                        }
                        EffectsKt.LaunchedEffect(composerImpl, popupLayout2, (Function2) objRememberedValue3);
                        Modifier.Companion companion = Modifier.Companion;
                        zChangedInstance4 = composerImpl.changedInstance(popupLayout2);
                        objRememberedValue4 = composerImpl.rememberedValue();
                        if (!zChangedInstance4 || objRememberedValue4 == composer$Companion$Empty$12) {
                            objRememberedValue4 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$7$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    LayoutCoordinates parentLayoutCoordinates$1 = ((LayoutCoordinates) obj).getParentLayoutCoordinates$1();
                                    parentLayoutCoordinates$1.getClass();
                                    PopupLayout popupLayout3 = popupLayout2;
                                    ((SnapshotMutableStateImpl) popupLayout3.parentLayoutCoordinates$delegate).setValue(parentLayoutCoordinates$1);
                                    popupLayout3.updateParentBounds$ui_release();
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue4);
                        }
                        Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(companion, (Function1) objRememberedValue4);
                        zChangedInstance5 = composerImpl.changedInstance(popupLayout2) | composerImpl.changed(layoutDirection);
                        objRememberedValue5 = composerImpl.rememberedValue();
                        if (!zChangedInstance5 || objRememberedValue5 == composer$Companion$Empty$12) {
                            objRememberedValue5 = new MeasurePolicy() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$8$1
                                @Override // androidx.compose.ui.layout.MeasurePolicy
                                /* renamed from: measure-3p2s80s */
                                public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                                    popupLayout2.parentLayoutDirection = layoutDirection;
                                    return measureScope.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$8$1.1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                            return Unit.INSTANCE;
                                        }
                                    });
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue5);
                        }
                        MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue5;
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierOnGloballyPositioned);
                        ComposeUiNode.Companion.getClass();
                        Function0 function05 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl.applier != null) {
                            ComposablesKt.invalidApplier();
                            throw th;
                        }
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function05);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, measurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                        composerImpl.end(z);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        function03 = function04;
                        popupProperties3 = popupProperties4;
                    }
                    final String str4 = str;
                    objRememberedValue7 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            PopupLayout popupLayout3 = popupLayout2;
                            popupLayout3.windowManager.addView(popupLayout3, popupLayout3.params);
                            popupLayout2.updateParameters(function04, popupProperties4, layoutDirection);
                            final PopupLayout popupLayout4 = popupLayout2;
                            return new DisposableEffectResult() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$2$1$invoke$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                    PopupLayout popupLayout5 = popupLayout4;
                                    popupLayout5.disposeComposition();
                                    popupLayout5.setTag(R.id.view_tree_lifecycle_owner, null);
                                    popupLayout5.windowManager.removeViewImmediate(popupLayout5);
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue7);
                    EffectsKt.DisposableEffect(popupLayout2, (Function1) objRememberedValue7, composerImpl);
                    zChangedInstance = composerImpl.changedInstance(popupLayout2) | (i8 != 32 ? z : z2) | (i9 != 256 ? z : z2) | composerImpl.changed(str) | composerImpl.changed(layoutDirection);
                    objRememberedValue = composerImpl.rememberedValue();
                    if (!zChangedInstance) {
                        final String str32 = str;
                        objRememberedValue = new Function0() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$3$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                popupLayout2.updateParameters(function04, popupProperties4, layoutDirection);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                        EffectsKt.SideEffect((Function0) objRememberedValue, composerImpl);
                        zChangedInstance2 = composerImpl.changedInstance(popupLayout2) | ((i7 & 14) != 4 ? z : z2);
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (!zChangedInstance2) {
                            objRememberedValue2 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$4$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    PopupLayout popupLayout3 = popupLayout2;
                                    popupLayout3.positionProvider = popupPositionProvider2;
                                    popupLayout3.updatePosition();
                                    return new DisposableEffectResult() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$4$1$invoke$$inlined$onDispose$1
                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                        public final void dispose() {
                                        }
                                    };
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                            EffectsKt.DisposableEffect(popupPositionProvider2, (Function1) objRememberedValue2, composerImpl);
                            zChangedInstance3 = composerImpl.changedInstance(popupLayout2);
                            objRememberedValue3 = composerImpl.rememberedValue();
                            if (zChangedInstance3) {
                                th = null;
                                objRememberedValue3 = new AndroidPopup_androidKt$Popup$5$1(popupLayout2, null);
                                composerImpl.updateRememberedValue(objRememberedValue3);
                                EffectsKt.LaunchedEffect(composerImpl, popupLayout2, (Function2) objRememberedValue3);
                                Modifier.Companion companion2 = Modifier.Companion;
                                zChangedInstance4 = composerImpl.changedInstance(popupLayout2);
                                objRememberedValue4 = composerImpl.rememberedValue();
                                if (!zChangedInstance4) {
                                    objRememberedValue4 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$7$1
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            LayoutCoordinates parentLayoutCoordinates$1 = ((LayoutCoordinates) obj).getParentLayoutCoordinates$1();
                                            parentLayoutCoordinates$1.getClass();
                                            PopupLayout popupLayout3 = popupLayout2;
                                            ((SnapshotMutableStateImpl) popupLayout3.parentLayoutCoordinates$delegate).setValue(parentLayoutCoordinates$1);
                                            popupLayout3.updateParentBounds$ui_release();
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue4);
                                    Modifier modifierOnGloballyPositioned2 = OnGloballyPositionedModifierKt.onGloballyPositioned(companion2, (Function1) objRememberedValue4);
                                    zChangedInstance5 = composerImpl.changedInstance(popupLayout2) | composerImpl.changed(layoutDirection);
                                    objRememberedValue5 = composerImpl.rememberedValue();
                                    if (!zChangedInstance5) {
                                        objRememberedValue5 = new MeasurePolicy() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$8$1
                                            @Override // androidx.compose.ui.layout.MeasurePolicy
                                            /* renamed from: measure-3p2s80s */
                                            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                                                popupLayout2.parentLayoutDirection = layoutDirection;
                                                return measureScope.layout$1(0, 0, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$8$1.1
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                                        return Unit.INSTANCE;
                                                    }
                                                });
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue5);
                                        MeasurePolicy measurePolicy2 = (MeasurePolicy) objRememberedValue5;
                                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierOnGloballyPositioned2);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function052 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl.applier != null) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                    function03 = function02;
                    popupProperties3 = popupProperties2;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt.Popup.9
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) throws Throwable {
                            ((Number) obj2).intValue();
                            AndroidPopup_androidKt.Popup(popupPositionProvider2, function03, popupProperties3, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 384;
            popupProperties2 = popupProperties;
            if ((i2 & 8) != 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        function02 = function0;
        i4 = i2 & 4;
        if (i4 != 0) {
        }
        popupProperties2 = popupProperties;
        if ((i2 & 8) != 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:99:? A[RETURN, SYNTHETIC] */
    /* renamed from: Popup-K5zGePQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m887PopupK5zGePQ(Alignment alignment, long j, Function0 function0, PopupProperties popupProperties, final Function2 function2, Composer composer, final int i, final int i2) throws Throwable {
        Alignment alignment2;
        int i3;
        long j2;
        int i4;
        Function0 function02;
        int i5;
        PopupProperties popupProperties2;
        Function2 function22;
        final Alignment alignment3;
        final long j3;
        final Function0 function03;
        final PopupProperties popupProperties3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Alignment alignment4;
        long j4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(295309329);
        int i6 = i2 & 1;
        if (i6 != 0) {
            i3 = i | 6;
            alignment2 = alignment;
        } else if ((i & 6) == 0) {
            alignment2 = alignment;
            i3 = (composerImpl.changed(alignment2) ? 4 : 2) | i;
        } else {
            alignment2 = alignment;
            i3 = i;
        }
        int i7 = i2 & 2;
        if (i7 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                j2 = j;
                i3 |= composerImpl.changed(j2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function02 = function0;
                    i3 |= composerImpl.changedInstance(function02) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        popupProperties2 = popupProperties;
                        i3 |= composerImpl.changed(popupProperties2) ? 2048 : 1024;
                    }
                    if ((i2 & 16) != 0) {
                        if ((i & 24576) == 0) {
                            function22 = function2;
                            i3 |= composerImpl.changedInstance(function22) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
                            if (i6 != 0) {
                                Alignment.Companion.getClass();
                                alignment4 = Alignment.Companion.TopStart;
                            } else {
                                alignment4 = alignment2;
                            }
                            if (i7 != 0) {
                                long j5 = 0;
                                j4 = (j5 << 32) | (j5 & 4294967295L);
                                IntOffset.Companion companion = IntOffset.Companion;
                            } else {
                                j4 = j2;
                            }
                            if (i4 != 0) {
                                function02 = null;
                            }
                            PopupProperties popupProperties4 = i5 != 0 ? new PopupProperties(false, false, false, false, 15, (DefaultConstructorMarker) null) : popupProperties2;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.ui.window.Popup (AndroidPopup.android.kt:268)");
                            }
                            boolean z = ((i3 & 14) == 4) | ((i3 & 112) == 32);
                            Object objRememberedValue = composerImpl.rememberedValue();
                            if (!z) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new AlignmentOffsetPositionProvider(alignment4, j4, null);
                                    composerImpl.updateRememberedValue(objRememberedValue);
                                }
                                Function2 function23 = function22;
                                Function0 function04 = function02;
                                long j6 = j4;
                                Popup((AlignmentOffsetPositionProvider) objRememberedValue, function04, popupProperties4, function23, composerImpl, (i3 >> 3) & 8176, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                alignment3 = alignment4;
                                j3 = j6;
                                function03 = function04;
                                popupProperties3 = popupProperties4;
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                            alignment3 = alignment2;
                            j3 = j2;
                            function03 = function02;
                            popupProperties3 = popupProperties2;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt.Popup.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) throws Throwable {
                                    ((Number) obj2).intValue();
                                    AndroidPopup_androidKt.m887PopupK5zGePQ(alignment3, j3, function03, popupProperties3, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 24576;
                    function22 = function2;
                    if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                popupProperties2 = popupProperties;
                if ((i2 & 16) != 0) {
                }
                function22 = function2;
                if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function02 = function0;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            popupProperties2 = popupProperties;
            if ((i2 & 16) != 0) {
            }
            function22 = function2;
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        j2 = j;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        function02 = function0;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        popupProperties2 = popupProperties;
        if ((i2 & 16) != 0) {
        }
        function22 = function2;
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 9363) != 9362)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    public static final boolean isFlagSecureEnabled(View view) {
        ViewGroup.LayoutParams layoutParams = view.getRootView().getLayoutParams();
        WindowManager.LayoutParams layoutParams2 = layoutParams instanceof WindowManager.LayoutParams ? (WindowManager.LayoutParams) layoutParams : null;
        return (layoutParams2 == null || (layoutParams2.flags & 8192) == 0) ? false : true;
    }
}
