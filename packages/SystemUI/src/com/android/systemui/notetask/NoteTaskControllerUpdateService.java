package com.android.systemui.notetask;

import androidx.lifecycle.LifecycleService;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoteTaskControllerUpdateService extends LifecycleService {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController controller;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
