package com.android.wm.shell.desktopmode;

import android.content.Context;
import android.window.DesktopExperienceFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.desktopmode.multidesks.OnDeskRemovedListener;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import java.util.Collections;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopDisplayEventHandler implements DisplayController.OnDisplaysChangedListener, OnDeskRemovedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DesktopDisplayModeController desktopDisplayModeController;
    public final DesktopRepositoryInitializer desktopRepositoryInitializer;
    public final DesktopState desktopState;
    public final DesktopTasksController desktopTasksController;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public final CoroutineScope mainScope;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final ShellController shellController;

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

    public DesktopDisplayEventHandler(ShellInit shellInit, CoroutineScope coroutineScope, ShellController shellController, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesksOrganizer desksOrganizer, DesktopRepositoryInitializer desktopRepositoryInitializer, DesktopUserRepositories desktopUserRepositories, DesktopTasksController desktopTasksController, DesktopDisplayModeController desktopDisplayModeController, DesksTransitionObserver desksTransitionObserver, DesktopState desktopState) {
        this.mainScope = coroutineScope;
        this.shellController = shellController;
        this.displayController = displayController;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.desktopRepositoryInitializer = desktopRepositoryInitializer;
        this.desktopUserRepositories = desktopUserRepositories;
        this.desktopTasksController = desktopTasksController;
        this.desktopDisplayModeController = desktopDisplayModeController;
        this.desktopState = desktopState;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                final DesktopDisplayEventHandler desktopDisplayEventHandler = DesktopDisplayEventHandler.this;
                desktopDisplayEventHandler.displayController.addDisplayWindowListener(desktopDisplayEventHandler, -1);
                if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                    desktopDisplayEventHandler.desktopTasksController.onDeskRemovedListener = desktopDisplayEventHandler;
                    desktopDisplayEventHandler.shellController.addUserChangeListener(new UserChangeListener() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$onInit$1
                        @Override // com.android.wm.shell.sysui.UserChangeListener
                        public final void onUserChanged(int i, Context context) {
                            DesktopDisplayEventHandler desktopDisplayEventHandler2 = DesktopDisplayEventHandler.this;
                            desktopDisplayEventHandler2.createDefaultDesksIfNeeded(ArraysKt___ArraysKt.toSet(desktopDisplayEventHandler2.rootTaskDisplayAreaOrganizer.getDisplayIds()), Integer.valueOf(i));
                        }
                    });
                }
            }
        }, this);
    }

    public static void logV$3(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopDisplayEventHandler", objArr);
        ProtoLog.v(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public final void createDefaultDesksIfNeeded(Set set, Integer num) {
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            logV$3("createDefaultDesksIfNeeded displays=%s", set);
            BuildersKt.launch$default(this.mainScope, null, null, new DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1(this, set, num, null), 3);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDesktopModeEligibleChanged(final int i) {
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT;
        if (desktopExperienceFlags.isTrue() && i != 0) {
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.rootTaskDisplayAreaOrganizer;
            if (rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i) == null) {
                rootTaskDisplayAreaOrganizer.mPendingDesktopModeEligibleChanged.put(i, new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$onDesktopModeEligibleChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopDisplayEventHandler.this.onDesktopModeEligibleChanged(i);
                    }
                });
                logV$3("pending onDesktopModeEligibleChanged until onDisplayAreaAppeared is done.", new Object[0]);
                return;
            }
            DesktopDisplayModeController desktopDisplayModeController = this.desktopDisplayModeController;
            desktopDisplayModeController.getClass();
            if (desktopExperienceFlags.isTrue() && ((DesktopStateImpl) desktopDisplayModeController.desktopState).isDesktopModeSupportedOnDisplay(i)) {
                desktopDisplayModeController.updateDisplayWindowingMode(i, 5);
            }
            desktopDisplayModeController.updateDefaultDisplayWindowingMode();
        }
        if (i != 0) {
            if (((DesktopStateImpl) this.desktopState).isDesktopModeSupportedOnDisplay(i) || i == 0) {
                DesktopRepository.DesktopData desktopData = this.desktopUserRepositories.getCurrent().desktopData;
                DesktopRepository.Desk deskForNewDisplay = desktopData.getDeskForNewDisplay();
                Integer valueOf = deskForNewDisplay != null ? Integer.valueOf(deskForNewDisplay.deskId) : null;
                if (valueOf == null) {
                    ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "No desk to restore on display %d", new Object[]{Integer.valueOf(i)});
                    return;
                }
                int intValue = valueOf.intValue();
                logV$3("onDesktopModeEligibleChanged activate deskToRestore=%d", Integer.valueOf(intValue));
                DesktopTasksController.activateDesk$default(this.desktopTasksController, intValue, null, i, 0, 10);
                if (desktopData.getNumberOfDesks(0) == 1) {
                    createDefaultDesksIfNeeded(Collections.singleton(0), null);
                }
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(final int i) {
        if (i != 0) {
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.rootTaskDisplayAreaOrganizer;
            if (rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i) == null) {
                rootTaskDisplayAreaOrganizer.mPendingDisplayAdded.put(i, new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$onDisplayAdded$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopDisplayEventHandler.this.onDisplayAdded(i);
                    }
                });
                logV$3("pending onDisplayAdded until onDisplayAreaAppeared is done.", new Object[0]);
                return;
            }
            DesktopDisplayModeController desktopDisplayModeController = this.desktopDisplayModeController;
            desktopDisplayModeController.getClass();
            if (DesktopExperienceFlags.ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT.isTrue() && ((DesktopStateImpl) desktopDisplayModeController.desktopState).isDesktopModeSupportedOnDisplay(i)) {
                desktopDisplayModeController.updateDisplayWindowingMode(i, 5);
            }
            desktopDisplayModeController.updateDefaultDisplayWindowingMode();
        }
        DesktopRepository current = this.desktopUserRepositories.getCurrent();
        if (i == 0 || !(((DesktopStateImpl) this.desktopState).isDesktopModeSupportedOnDisplay(i) || i == 0)) {
            createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(i)), null);
            return;
        }
        DesktopRepository.DesktopData desktopData = current.desktopData;
        DesktopRepository.Desk deskForNewDisplay = desktopData.getDeskForNewDisplay();
        Integer valueOf = deskForNewDisplay != null ? Integer.valueOf(deskForNewDisplay.deskId) : null;
        if (valueOf == null) {
            ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "No desk to restore on display %d", new Object[]{Integer.valueOf(i)});
            createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(i)), null);
        } else {
            DesktopTasksController.activateDesk$default(this.desktopTasksController, valueOf.intValue(), null, i, 0, 10);
            if (desktopData.getNumberOfDesks(0) == 1) {
                DesktopTasksController.createDesk$default(this.desktopTasksController, 0, current.userId, false, null, 56);
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        if (i != 0) {
            this.desktopDisplayModeController.updateDefaultDisplayWindowingMode();
        }
    }
}
