package com.android.wm.shell.desktopmode;

import android.content.Context;
import android.content.res.Resources;
import android.window.DesktopExperienceFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.desktopmode.multidesks.OnDeskRemovedListener;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1, reason: invalid class name and case insensitive filesystem */
    final class C11971 extends SuspendLambda implements Function2 {
        final /* synthetic */ Set<Integer> $displayIds;
        final /* synthetic */ Integer $userId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11971(Set<Integer> set, Integer num, Continuation continuation) {
            super(2, continuation);
            this.$displayIds = set;
            this.$userId = num;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11971 c11971 = DesktopDisplayEventHandler.this.new C11971(this.$displayIds, this.$userId, continuation);
            c11971.L$0 = obj;
            return c11971;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11971) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                final DesktopDisplayEventHandler desktopDisplayEventHandler = DesktopDisplayEventHandler.this;
                StateFlowImpl stateFlowImpl = ((DesktopRepositoryInitializerImpl) desktopDisplayEventHandler.desktopRepositoryInitializer).isInitialized;
                final Set<Integer> set = this.$displayIds;
                final Integer num = this.$userId;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler.createDefaultDesksIfNeeded.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        DesktopDisplayEventHandler desktopDisplayEventHandler2 = desktopDisplayEventHandler;
                        if (!zBooleanValue) {
                            ((DesktopRepositoryInitializerImpl) desktopDisplayEventHandler2.desktopRepositoryInitializer).addedDisplayIdsBeforeInitialized.addAll(set);
                            return Unit.INSTANCE;
                        }
                        Integer num2 = num;
                        DesktopRepository profile = num2 != null ? desktopDisplayEventHandler2.desktopUserRepositories.getProfile(num2.intValue()) : desktopDisplayEventHandler2.desktopUserRepositories.getCurrent();
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            int iIntValue = ((Number) it.next()).intValue();
                            int i2 = DesktopDisplayEventHandler.$r8$clinit;
                            if (iIntValue == -1) {
                                desktopDisplayEventHandler2.getClass();
                                DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping reason: invalid display", new Object[0]);
                            } else if (!desktopDisplayEventHandler2.supportsDesks(iIntValue)) {
                                DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping displayId=%d reason: desktop ineligible", Integer.valueOf(iIntValue));
                            } else if (profile.desktopData.getNumberOfDesks(iIntValue) > 0) {
                                DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping displayId=%d reason: has desk(s)", Integer.valueOf(iIntValue));
                            } else if (desktopDisplayEventHandler2.displayController.mDisplayManager.getDisplay(iIntValue) == null) {
                                DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping displayId=%d reason: display null", Integer.valueOf(iIntValue));
                            } else if (iIntValue != 0) {
                                DesktopDisplayEventHandler.logV$3("Display %d is desktop-first and needs a default desk", new Integer(iIntValue));
                                DesktopTasksController.createDesk$default(desktopDisplayEventHandler2.desktopTasksController, iIntValue, profile.userId, true, null, 48);
                            } else {
                                DesktopDisplayEventHandler.logV$3("Display %d is touch-first and needs a default desk that is not activated", new Integer(iIntValue));
                                DesktopTasksController.createDesk$default(desktopDisplayEventHandler2.desktopTasksController, iIntValue, profile.userId, false, null, 52);
                            }
                        }
                        CoroutineScopeKt.cancel(coroutineScope, null);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
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
                            Set setSingleton = Collections.singleton(0);
                            Integer numValueOf = Integer.valueOf(i);
                            int i2 = DesktopDisplayEventHandler.$r8$clinit;
                            desktopDisplayEventHandler.createDefaultDesksIfNeeded(setSingleton, numValueOf);
                        }
                    });
                }
            }
        }, this);
    }

    public static void logV$3(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopDisplayEventHandler", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final void createDefaultDesksIfNeeded(Set set, Integer num) {
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            logV$3("createDefaultDesksIfNeeded displays=%s", set);
            BuildersKt.launch$default(this.mainScope, null, null, new C11971(set, num, null), 3);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDesktopModeEligibleChanged(final int i) throws Resources.NotFoundException {
        int i2;
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT;
        if (desktopExperienceFlags.isTrue() && i != 0) {
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.rootTaskDisplayAreaOrganizer;
            if (rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i) == null) {
                rootTaskDisplayAreaOrganizer.mPendingDesktopModeEligibleChanged.put(i, new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler.onDesktopModeEligibleChanged.1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
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
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.desktopExternalDisplayId != -1) {
            return;
        }
        if (i == 0 || !supportsDesks(i)) {
            i2 = i;
        } else {
            DesktopRepository.DesktopData desktopData = this.desktopUserRepositories.getCurrent().desktopData;
            DesktopRepository.Desk deskForNewDisplay = desktopData.getDeskForNewDisplay();
            Integer numValueOf = deskForNewDisplay != null ? Integer.valueOf(deskForNewDisplay.deskId) : null;
            if (numValueOf != null) {
                int iIntValue = numValueOf.intValue();
                logV$3("onDesktopModeEligibleChanged activate deskToRestore=%d", Integer.valueOf(iIntValue));
                i2 = i;
                DesktopTasksController.activateDesk$default(this.desktopTasksController, iIntValue, null, i2, 0, 10);
                if (desktopData.getNumberOfDesks(0) == 1) {
                    createDefaultDesksIfNeeded(Collections.singleton(0), null);
                }
            } else {
                i2 = i;
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "No desk to restore on display %d", new Object[]{Integer.valueOf(i2)});
            }
        }
        if (i2 == 0 || supportsDesks(i2)) {
            return;
        }
        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.desktopTasksController.snapEventHandler;
        DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) (desktopModeWindowDecorViewModel != null ? desktopModeWindowDecorViewModel : null).mDesktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(i2);
        if (desktopTilingWindowDecoration != null) {
            desktopTilingWindowDecoration.resetTilingSession();
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(final int i) throws Resources.NotFoundException {
        Integer numValueOf;
        Integer activeDeskId;
        if (i != 0) {
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.rootTaskDisplayAreaOrganizer;
            if (rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i) == null) {
                rootTaskDisplayAreaOrganizer.mPendingDisplayAdded.put(i, new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler.onDisplayAdded.1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
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
        if (i == 0 || !supportsDesks(i)) {
            createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(i)), null);
            return;
        }
        DesktopStateImpl.Companion.getClass();
        int i2 = DesktopStateImpl.desktopExternalDisplayId;
        if (i2 != -1 && (activeDeskId = current.getActiveDeskId(i2)) != null) {
            DesktopTasksController.activateDesk$default(this.desktopTasksController, activeDeskId.intValue(), null, i, 0, 10);
            return;
        }
        DesktopRepository.DesktopData desktopData = current.desktopData;
        DesktopRepository.Desk deskForNewDisplay = desktopData.getDeskForNewDisplay();
        Integer numValueOf2 = deskForNewDisplay != null ? Integer.valueOf(deskForNewDisplay.deskId) : null;
        DesktopState desktopState = this.desktopState;
        if (numValueOf2 == null) {
            ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "No desk to restore on display %d", new Object[]{Integer.valueOf(i)});
            createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(i)), null);
            if (((DesktopStateImpl) desktopState).isDesktopModeSupportedOnDisplay(0) && current.getActiveDeskId(0) == null) {
                DesktopRepository.Desk deskForDefaultDisplay = desktopData.getDeskForDefaultDisplay(null);
                numValueOf = deskForDefaultDisplay != null ? Integer.valueOf(deskForDefaultDisplay.deskId) : null;
                if (numValueOf != null) {
                    DesktopTasksController.activateDesk$default(this.desktopTasksController, numValueOf.intValue(), null, 0, 0, 10);
                    return;
                }
                return;
            }
            return;
        }
        int iIntValue = numValueOf2.intValue();
        DesktopTasksController.activateDesk$default(this.desktopTasksController, iIntValue, null, i, 0, 10);
        if (desktopData.getNumberOfDesks(0) == 1) {
            DesktopTasksController.createDesk$default(this.desktopTasksController, 0, current.userId, ((DesktopStateImpl) desktopState).isDesktopModeSupportedOnDisplay(0), null, 48);
            return;
        }
        if (((DesktopStateImpl) desktopState).isDesktopModeSupportedOnDisplay(0) && current.getActiveDeskId(0) == null) {
            DesktopRepository.Desk deskForDefaultDisplay2 = desktopData.getDeskForDefaultDisplay(Integer.valueOf(iIntValue));
            numValueOf = deskForDefaultDisplay2 != null ? Integer.valueOf(deskForDefaultDisplay2.deskId) : null;
            if (numValueOf != null) {
                DesktopTasksController.activateDesk$default(this.desktopTasksController, numValueOf.intValue(), null, 0, 0, 10);
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        if (i != 0) {
            this.desktopDisplayModeController.updateDefaultDisplayWindowingMode();
        }
    }

    public final boolean supportsDesks(int i) {
        return ((DesktopStateImpl) this.desktopState).isDesktopModeSupportedOnDisplay(i) || i == 0;
    }
}
