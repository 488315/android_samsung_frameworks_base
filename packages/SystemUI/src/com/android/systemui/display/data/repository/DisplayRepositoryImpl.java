package com.android.systemui.display.data.repository;

import android.view.Display;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplayRepositoryImpl implements com.android.app.displaylib.DisplayRepository, DisplaysWithDecorationsRepository, DisplayRepository {
    public final com.android.app.displaylib.DisplayRepository displayRepositoryFromLib;
    public final DisplaysWithDecorationsRepository displaysWithDecorationsRepositoryImpl;

    public DisplayRepositoryImpl(com.android.app.displaylib.DisplayRepository displayRepository, DisplaysWithDecorationsRepository displaysWithDecorationsRepository) {
        this.displayRepositoryFromLib = displayRepository;
        this.displaysWithDecorationsRepositoryImpl = displaysWithDecorationsRepository;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDefaultDisplayOff() {
        return this.displayRepositoryFromLib.getDefaultDisplayOff();
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Display getDisplay(int i) {
        return this.displayRepositoryFromLib.getDisplay(i);
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayAdditionEvent() {
        return this.displayRepositoryFromLib.getDisplayAdditionEvent();
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayChangeEvent() {
        return this.displayRepositoryFromLib.getDisplayChangeEvent();
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final StateFlow getDisplayIds() {
        return this.displayRepositoryFromLib.getDisplayIds();
    }

    @Override // com.android.systemui.display.data.repository.DisplaysWithDecorationsRepository
    public final StateFlow getDisplayIdsWithSystemDecorations() {
        return this.displaysWithDecorationsRepositoryImpl.getDisplayIdsWithSystemDecorations();
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayRemovalEvent() {
        return this.displayRepositoryFromLib.getDisplayRemovalEvent();
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final StateFlow getDisplays() {
        return this.displayRepositoryFromLib.getDisplays();
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getPendingDisplay() {
        return this.displayRepositoryFromLib.getPendingDisplay();
    }
}
