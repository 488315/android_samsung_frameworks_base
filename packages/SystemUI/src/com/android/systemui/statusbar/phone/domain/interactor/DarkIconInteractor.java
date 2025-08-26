package com.android.systemui.statusbar.phone.domain.interactor;

import com.android.systemui.statusbar.phone.data.repository.DarkIconRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DarkIconInteractor {
    public static final Companion Companion = new Companion(null);
    public final DarkIconRepository repository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DarkIconInteractor(DarkIconRepository darkIconRepository) {
        this.repository = darkIconRepository;
    }
}
