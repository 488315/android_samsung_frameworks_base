package androidx.compose.runtime.internal;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.RecomposeScope;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: classes.dex */
public final class ComposableLambdaImpl implements ComposableLambda {
    public Object _block;
    public final int key;
    public RecomposeScopeImpl scope;
    public List scopes;
    public final boolean tracked;

    /* renamed from: androidx.compose.runtime.internal.ComposableLambdaImpl$invoke$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends AdaptedFunctionReference implements Function2 {
        public AnonymousClass1(Object obj) {
            super(2, obj, ComposableLambdaImpl.class, "invoke", "invoke(Landroidx/compose/runtime/Composer;I)Ljava/lang/Object;", 8);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            int iIntValue = ((Number) obj2).intValue();
            ((ComposableLambdaImpl) this.receiver).invoke(iIntValue, (Composer) obj);
            return Unit.INSTANCE;
        }
    }

    public ComposableLambdaImpl(int i, boolean z, Object obj) {
        this.key = i;
        this.tracked = z;
        this._block = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Number) obj2).intValue(), (Composer) obj);
    }

    public final void trackRead(Composer composer) {
        ComposerImpl composerImpl;
        RecomposeScopeImpl currentRecomposeScope$runtime_release;
        if (!this.tracked || (currentRecomposeScope$runtime_release = (composerImpl = (ComposerImpl) composer).getCurrentRecomposeScope$runtime_release()) == null) {
            return;
        }
        composerImpl.getClass();
        currentRecomposeScope$runtime_release.flags |= 1;
        if (ComposableLambdaKt.replacableWith(this.scope, currentRecomposeScope$runtime_release)) {
            this.scope = currentRecomposeScope$runtime_release;
            return;
        }
        List list = this.scopes;
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            this.scopes = arrayList;
            arrayList.add(currentRecomposeScope$runtime_release);
            return;
        }
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            if (ComposableLambdaKt.replacableWith((RecomposeScope) arrayList2.get(i), currentRecomposeScope$runtime_release)) {
                arrayList2.set(i, currentRecomposeScope$runtime_release);
                return;
            }
        }
        arrayList2.add(currentRecomposeScope$runtime_release);
    }

    @Override // kotlin.jvm.functions.Function3
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(obj, (Composer) obj2, ((Number) obj3).intValue());
    }

    @Override // kotlin.jvm.functions.Function4
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke(obj, obj2, (Composer) obj3, ((Number) obj4).intValue());
    }

    @Override // kotlin.jvm.functions.Function5
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        return invoke(obj, obj2, obj3, (Composer) obj4, ((Number) obj5).intValue());
    }

    @Override // kotlin.jvm.functions.Function6
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        return invoke(obj, obj2, obj3, obj4, (Composer) obj5, ((Number) obj6).intValue());
    }

    @Override // kotlin.jvm.functions.Function7
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        return invoke(obj, obj2, obj3, obj4, obj5, (Composer) obj6, ((Number) obj7).intValue());
    }

    public final Object invoke(int i, Composer composer) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 0);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 0);
        }
        int i2 = i | iBitsForSlot;
        Object obj = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, obj);
        Object objInvoke = ((Function2) obj).invoke(composerImpl, Integer.valueOf(i2));
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new AnonymousClass1(this);
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 1);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 1);
        }
        Object obj2 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(3, obj2);
        Object objInvoke = ((Function3) obj2).invoke(obj, composerImpl, Integer.valueOf(iBitsForSlot | i));
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    ((Number) obj4).intValue();
                    ComposableLambdaImpl.this.invoke(obj, (Composer) obj3, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 2);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 2);
        }
        Object obj3 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(4, obj3);
        Object objInvoke = ((Function4) obj3).invoke(obj, obj2, composerImpl, Integer.valueOf(iBitsForSlot | i));
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    ((Number) obj5).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, (Composer) obj4, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, final Object obj3, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 3);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 3);
        }
        Object obj4 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(5, obj4);
        Object objInvoke = ((Function5) obj4).invoke(obj, obj2, obj3, composerImpl, Integer.valueOf(iBitsForSlot | i));
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    ((Number) obj6).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, obj3, (Composer) obj5, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, final Object obj3, final Object obj4, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 4);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 4);
        }
        Object obj5 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(6, obj5);
        Object objInvoke = ((Function6) obj5).invoke(obj, obj2, obj3, obj4, composerImpl, Integer.valueOf(iBitsForSlot | i));
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj6, Object obj7) {
                    ((Number) obj7).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, obj3, obj4, (Composer) obj6, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, final Object obj3, final Object obj4, final Object obj5, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 5);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 5);
        }
        Object obj6 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(7, obj6);
        Object objInvoke = ((Function7) obj6).invoke(obj, obj2, obj3, obj4, obj5, composerImpl, Integer.valueOf(i | iBitsForSlot));
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj7, Object obj8) {
                    ((Number) obj8).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, obj3, obj4, obj5, (Composer) obj7, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, final Object obj3, final Object obj4, final Object obj5, final Object obj6, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 6);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 6);
        }
        Object obj7 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(8, obj7);
        Integer numValueOf = Integer.valueOf(i | iBitsForSlot);
        ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) ((Function8) obj7);
        composableLambdaImpl.getClass();
        Object objInvoke = composableLambdaImpl.invoke(obj, obj2, obj3, obj4, obj5, obj6, composerImpl, numValueOf.intValue());
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj8, Object obj9) {
                    ((Number) obj9).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, obj3, obj4, obj5, obj6, (Composer) obj8, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, final Object obj3, final Object obj4, final Object obj5, final Object obj6, final Object obj7, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 7);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 7);
        }
        Object obj8 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(9, obj8);
        Integer numValueOf = Integer.valueOf(i | iBitsForSlot);
        ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) ((Function9) obj8);
        composableLambdaImpl.getClass();
        Object objInvoke = composableLambdaImpl.invoke(obj, obj2, obj3, obj4, obj5, obj6, obj7, composerImpl, numValueOf.intValue());
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.8
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj9, Object obj10) {
                    ((Number) obj10).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, obj3, obj4, obj5, obj6, obj7, (Composer) obj9, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }

    public final Object invoke(final Object obj, final Object obj2, final Object obj3, final Object obj4, final Object obj5, final Object obj6, final Object obj7, final Object obj8, Composer composer, final int i) {
        int iBitsForSlot;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(this.key);
        trackRead(composerImpl);
        if (composerImpl.changed(this)) {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(2, 8);
        } else {
            iBitsForSlot = ComposableLambdaKt.bitsForSlot(1, 8);
        }
        Object obj9 = this._block;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(10, obj9);
        Integer numValueOf = Integer.valueOf(i | iBitsForSlot);
        ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) ((Function10) obj9);
        composableLambdaImpl.getClass();
        Object objInvoke = composableLambdaImpl.invoke(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, composerImpl, numValueOf.intValue());
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.internal.ComposableLambdaImpl.invoke.9
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj10, Object obj11) {
                    ((Number) obj11).intValue();
                    ComposableLambdaImpl.this.invoke(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, (Composer) obj10, RecomposeScopeImplKt.updateChangedFlags(i) | 1);
                    return Unit.INSTANCE;
                }
            };
        }
        return objInvoke;
    }
}
