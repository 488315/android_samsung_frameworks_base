package com.android.systemui;

import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt;
import java.util.Iterator;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CompletableDeferredImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KairosCoreStartable$start$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KairosCoreStartable$start$1$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BuildScope buildScope = (BuildScope) obj;
                KairosCoreStartable kairosCoreStartable = (KairosCoreStartable) this.f$0;
                Iterator it = ((Set) kairosCoreStartable.activatables.get()).iterator();
                while (it.hasNext()) {
                    BuildScopeKt.launchScope(buildScope, new KairosCoreStartable$start$1$$ExternalSyntheticLambda0((KairosActivatable) it.next(), 2));
                }
                return BuildScopeKt.effect$default(buildScope, new KairosCoreStartable$start$1$$ExternalSyntheticLambda0(kairosCoreStartable, 1));
            case 1:
                CompletableDeferredImpl completableDeferredImpl = ((KairosCoreStartable) this.f$0).started;
                Unit unit = Unit.INSTANCE;
                completableDeferredImpl.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
                return unit;
            default:
                ((KairosActivatable) this.f$0).activate((BuildScope) obj);
                return Unit.INSTANCE;
        }
    }
}
