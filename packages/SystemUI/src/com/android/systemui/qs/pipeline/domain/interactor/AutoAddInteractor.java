package com.android.systemui.qs.pipeline.domain.interactor;

import android.util.IndentingPrintWriter;
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

public final class AutoAddInteractor implements Dumpable {
    public final Set autoAddables;
    public final QSPipelineLogger qsPipelineLogger;
    public final AutoAddRepository repository;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AutoAddInteractor(Set<AutoAddable> set, AutoAddRepository autoAddRepository, DumpManager dumpManager, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope) {
        this.autoAddables = set;
        new AtomicBoolean(false);
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
}
