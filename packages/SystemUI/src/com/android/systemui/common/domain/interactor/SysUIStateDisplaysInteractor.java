package com.android.systemui.common.domain.interactor;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SysUIStateDisplaysInteractor {
    public final DisplayRepository displayRepository;
    public final PerDisplayRepository sysUIStateRepository;

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

    public SysUIStateDisplaysInteractor(PerDisplayRepository perDisplayRepository, DisplayRepository displayRepository) {
        this.sysUIStateRepository = perDisplayRepository;
        this.displayRepository = displayRepository;
    }
}
