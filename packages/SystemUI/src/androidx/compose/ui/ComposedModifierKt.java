package androidx.compose.ui;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: classes.dex */
public abstract class ComposedModifierKt {
    public static final Modifier composed(Modifier modifier, Function1 function1, Function3 function3) {
        return modifier.then(new ComposedModifier(function1, function3));
    }

    public static final Modifier materializeImpl(Composer composer, Modifier modifier) {
        if (modifier.all(new Function1() { // from class: androidx.compose.ui.ComposedModifierKt.materializeImpl.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(!(((Modifier.Element) obj) instanceof ComposedModifier));
            }
        })) {
            return modifier;
        }
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(1219399079);
        Modifier modifier2 = (Modifier) modifier.foldIn(Modifier.Companion, new Function2() { // from class: androidx.compose.ui.ComposedModifierKt$materializeImpl$result$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Modifier modifier3 = (Modifier) obj;
                Modifier modifierMaterializeImpl = (Modifier.Element) obj2;
                if (modifierMaterializeImpl instanceof ComposedModifier) {
                    Function3 function3 = ((ComposedModifier) modifierMaterializeImpl).factory;
                    TypeIntrinsics.beforeCheckcastToFunctionOfArity(3, function3);
                    modifierMaterializeImpl = ComposedModifierKt.materializeImpl(composerImpl, (Modifier) function3.invoke(Modifier.Companion, composerImpl, 0));
                }
                return modifier3.then(modifierMaterializeImpl);
            }
        });
        composerImpl.end(false);
        return modifier2;
    }

    public static final Modifier materializeModifier(Composer composer, Modifier modifier) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(439770924);
        Modifier modifierMaterializeImpl = materializeImpl(composerImpl, modifier);
        composerImpl.end(false);
        return modifierMaterializeImpl;
    }
}
