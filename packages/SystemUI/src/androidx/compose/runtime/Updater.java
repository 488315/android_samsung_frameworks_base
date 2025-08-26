package androidx.compose.runtime;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Updater<T> {
    public final Composer composer;

    /* renamed from: init-impl, reason: not valid java name */
    public static final void m336initimpl(Composer composer, final Function1 function1) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        if (composerImpl.inserting) {
            composerImpl.apply(Unit.INSTANCE, new Function2() { // from class: androidx.compose.runtime.Updater$init$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    function1.mo781invoke(obj);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    /* renamed from: set-impl, reason: not valid java name */
    public static final void m337setimpl(Composer composer, Object obj, Function2 function2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), obj)) {
            composerImpl.updateRememberedValue(obj);
            composerImpl.apply(obj, function2);
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Updater) {
            return Intrinsics.areEqual(this.composer, ((Updater) obj).composer);
        }
        return false;
    }

    public final int hashCode() {
        return this.composer.hashCode();
    }

    public final String toString() {
        return "Updater(composer=" + this.composer + ')';
    }
}
