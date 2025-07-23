package androidx.compose.foundation;

import android.view.KeyEvent;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.Role;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ClickableKt {
    /* renamed from: clickable-O2vRcR0, reason: not valid java name */
    public static final Modifier m33clickableO2vRcR0(Modifier modifier, MutableInteractionSource mutableInteractionSource, final Indication indication, final boolean z, final String str, final Role role, final Function0 function0) {
        Modifier composed;
        if (indication instanceof IndicationNodeFactory) {
            composed = new ClickableElement(mutableInteractionSource, (IndicationNodeFactory) indication, z, str, role, function0, null);
        } else if (indication == null) {
            composed = new ClickableElement(mutableInteractionSource, null, z, str, role, function0, null);
        } else if (mutableInteractionSource != null) {
            composed = IndicationKt.indication(Modifier.Companion, mutableInteractionSource, indication).then(new ClickableElement(mutableInteractionSource, null, z, str, role, function0, null));
        } else {
            composed = ComposedModifierKt.composed(Modifier.Companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.ClickableKt$clickable-O2vRcR0$$inlined$clickableWithIndicationIfNeeded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                    composerImpl.startReplaceGroup(-1525724089);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.clickableWithIndicationIfNeeded.<anonymous> (Clickable.kt:473)");
                    }
                    Object rememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(rememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) rememberedValue;
                    Modifier then = IndicationKt.indication(Modifier.Companion, mutableInteractionSource2, Indication.this).then(new ClickableElement(mutableInteractionSource2, null, z, str, role, function0, null));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    return then;
                }
            });
        }
        return modifier.then(composed);
    }

    /* renamed from: clickable-O2vRcR0$default, reason: not valid java name */
    public static /* synthetic */ Modifier m34clickableO2vRcR0$default(Modifier modifier, MutableInteractionSource mutableInteractionSource, Indication indication, boolean z, String str, Role role, Function0 function0, int i) {
        Role role2;
        Modifier modifier2;
        MutableInteractionSource mutableInteractionSource2;
        Indication indication2;
        Function0 function02;
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z2 = z;
        String str2 = (i & 8) != 0 ? null : str;
        if ((i & 16) != 0) {
            role2 = null;
            mutableInteractionSource2 = mutableInteractionSource;
            indication2 = indication;
            function02 = function0;
            modifier2 = modifier;
        } else {
            role2 = role;
            modifier2 = modifier;
            mutableInteractionSource2 = mutableInteractionSource;
            indication2 = indication;
            function02 = function0;
        }
        return m33clickableO2vRcR0(modifier2, mutableInteractionSource2, indication2, z2, str2, role2, function02);
    }

    /* renamed from: clickable-XHw0xAI$default, reason: not valid java name */
    public static Modifier m35clickableXHw0xAI$default(Modifier modifier, final boolean z, final String str, final Function0 function0, int i) {
        if ((i & 1) != 0) {
            z = true;
        }
        final Role role = null;
        if ((i & 2) != 0) {
            str = null;
        }
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.ClickableKt$clickable$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                MutableInteractionSource mutableInteractionSource;
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(-756081143);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.clickable.<anonymous> (Clickable.kt:120)");
                }
                Indication indication = (Indication) composerImpl.consume(IndicationKt.LocalIndication);
                if (indication instanceof IndicationNodeFactory) {
                    composerImpl.startReplaceGroup(617653824);
                    composerImpl.end(false);
                    mutableInteractionSource = null;
                } else {
                    composerImpl.startReplaceGroup(617786442);
                    Object rememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(rememberedValue);
                    }
                    mutableInteractionSource = (MutableInteractionSource) rememberedValue;
                    composerImpl.end(false);
                }
                Modifier m33clickableO2vRcR0 = ClickableKt.m33clickableO2vRcR0(Modifier.Companion, mutableInteractionSource, indication, z, str, role, function0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return m33clickableO2vRcR0;
            }
        });
    }

    /* renamed from: combinedClickable-auXiCPI, reason: not valid java name */
    public static final Modifier m36combinedClickableauXiCPI(Modifier modifier, MutableInteractionSource mutableInteractionSource, final Indication indication, final boolean z, final String str, final Role role, final String str2, final Function0 function0, final Function0 function02, final boolean z2, final Function0 function03) {
        Modifier composed;
        if (indication instanceof IndicationNodeFactory) {
            composed = new CombinedClickableElement(mutableInteractionSource, (IndicationNodeFactory) indication, z, str, role, function03, str2, function0, function02, z2, null);
        } else if (indication == null) {
            composed = new CombinedClickableElement(mutableInteractionSource, null, z, str, role, function03, str2, function0, function02, z2, null);
        } else if (mutableInteractionSource != null) {
            composed = IndicationKt.indication(Modifier.Companion, mutableInteractionSource, indication).then(new CombinedClickableElement(mutableInteractionSource, null, z, str, role, function03, str2, function0, function02, z2, null));
        } else {
            composed = ComposedModifierKt.composed(Modifier.Companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.ClickableKt$combinedClickable-auXiCPI$$inlined$clickableWithIndicationIfNeeded$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                    composerImpl.startReplaceGroup(-1525724089);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.clickableWithIndicationIfNeeded.<anonymous> (Clickable.kt:473)");
                    }
                    Object rememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(rememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) rememberedValue;
                    Modifier then = IndicationKt.indication(Modifier.Companion, mutableInteractionSource2, Indication.this).then(new CombinedClickableElement(mutableInteractionSource2, null, z, str, role, function03, str2, function0, function02, z2, null));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    return then;
                }
            });
        }
        return modifier.then(composed);
    }

    /* renamed from: combinedClickable-auXiCPI$default, reason: not valid java name */
    public static /* synthetic */ Modifier m37combinedClickableauXiCPI$default(Modifier modifier, MutableInteractionSource mutableInteractionSource, Indication indication, Function0 function0, Function0 function02, int i) {
        if ((i & 64) != 0) {
            function0 = null;
        }
        return m36combinedClickableauXiCPI(modifier, mutableInteractionSource, indication, true, null, null, null, function0, null, true, function02);
    }

    /* renamed from: combinedClickable-f5TDLPQ$default, reason: not valid java name */
    public static Modifier m38combinedClickablef5TDLPQ$default(Modifier modifier, String str, String str2, final Function0 function0, final Function0 function02, int i) {
        final String str3 = (i & 2) != 0 ? null : str;
        final String str4 = (i & 8) != 0 ? null : str2;
        final boolean z = true;
        final Role role = null;
        final Function0 function03 = null;
        final boolean z2 = true;
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.ClickableKt$combinedClickable$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                MutableInteractionSource mutableInteractionSource;
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(-1534186401);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.combinedClickable.<anonymous> (Clickable.kt:264)");
                }
                Indication indication = (Indication) composerImpl.consume(IndicationKt.LocalIndication);
                if (indication instanceof IndicationNodeFactory) {
                    composerImpl.startReplaceGroup(-1726068379);
                    composerImpl.end(false);
                    mutableInteractionSource = null;
                } else {
                    composerImpl.startReplaceGroup(-1725935761);
                    Object rememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(rememberedValue);
                    }
                    mutableInteractionSource = (MutableInteractionSource) rememberedValue;
                    composerImpl.end(false);
                }
                Modifier m36combinedClickableauXiCPI = ClickableKt.m36combinedClickableauXiCPI(Modifier.Companion, mutableInteractionSource, indication, z, str3, role, str4, function0, function03, z2, function02);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return m36combinedClickableauXiCPI;
            }
        });
    }

    /* renamed from: isEnter-ZmokQxo, reason: not valid java name */
    public static final boolean m39isEnterZmokQxo(KeyEvent keyEvent) {
        boolean m576equalsimpl0;
        boolean m576equalsimpl02;
        long m578getKeyZmokQxo = KeyEvent_androidKt.m578getKeyZmokQxo(keyEvent);
        Key.Companion companion = Key.Companion;
        companion.getClass();
        if (Key.m576equalsimpl0(m578getKeyZmokQxo, Key.DirectionCenter)) {
            m576equalsimpl0 = true;
        } else {
            companion.getClass();
            m576equalsimpl0 = Key.m576equalsimpl0(m578getKeyZmokQxo, Key.Enter);
        }
        if (m576equalsimpl0) {
            m576equalsimpl02 = true;
        } else {
            companion.getClass();
            m576equalsimpl02 = Key.m576equalsimpl0(m578getKeyZmokQxo, Key.NumPadEnter);
        }
        if (m576equalsimpl02) {
            return true;
        }
        companion.getClass();
        return Key.m576equalsimpl0(m578getKeyZmokQxo, Key.Spacebar);
    }
}
