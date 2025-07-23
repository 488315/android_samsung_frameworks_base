package com.android.systemui.statusbar.phone.domain.interactor;

import com.android.systemui.statusbar.phone.data.repository.DarkIconRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DarkIconInteractor {
    public static final Companion Companion = new Companion(null);
    public final DarkIconRepository repository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
