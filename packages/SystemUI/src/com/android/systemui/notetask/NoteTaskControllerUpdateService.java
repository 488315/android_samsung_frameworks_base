package com.android.systemui.notetask;

import androidx.lifecycle.LifecycleService;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class NoteTaskControllerUpdateService extends LifecycleService {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController controller;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public NoteTaskControllerUpdateService(NoteTaskController noteTaskController) {
        this.controller = noteTaskController;
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.controller.launchUpdateNoteTaskAsUser(getUser());
        stopSelf();
    }
}
