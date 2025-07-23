package com.android.systemui.qs.pipeline.domain.interactor;

import android.util.IndentingPrintWriter;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AutoAddInteractor implements Dumpable {
    public final Set autoAddables;
    public CurrentTilesInteractor currentTilesInteractor;
    public final DumpManager dumpManager;
    public final AtomicBoolean initialized = new AtomicBoolean(false);
    public final QSPipelineLogger qsPipelineLogger;
    public final AutoAddRepository repository;
    public final CoroutineScope scope;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AutoAddInteractor(Set<AutoAddable> set, AutoAddRepository autoAddRepository, DumpManager dumpManager, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope) {
        this.autoAddables = set;
        this.repository = autoAddRepository;
        this.dumpManager = dumpManager;
        this.qsPipelineLogger = qSPipelineLogger;
        this.scope = coroutineScope;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ee, code lost:
    
        if (r11.collect(r2, r0) != r1) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0066, code lost:
    
        if (r12 == r1) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$collectAutoAddSignalsForUser(com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor r9, kotlinx.coroutines.CoroutineScope r10, int r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor.access$collectAutoAddSignalsForUser(com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor, kotlinx.coroutines.CoroutineScope, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("AutoAddables:");
        asIndenting.increaseIndent();
        Iterator it = this.autoAddables.iterator();
        while (it.hasNext()) {
            asIndenting.println(((AutoAddable) it.next()).getDescription());
        }
        asIndenting.decreaseIndent();
    }

    public final void init(CurrentTilesInteractor currentTilesInteractor) {
        if (this.initialized.compareAndSet(false, true)) {
            this.currentTilesInteractor = currentTilesInteractor;
            this.dumpManager.registerNormalDumpable("AutoAddInteractor", this);
            CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AutoAddInteractor$init$1(currentTilesInteractor, this, null), 7);
        }
    }
}
