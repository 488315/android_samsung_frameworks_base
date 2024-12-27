package com.android.systemui.statusbar.phone;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarHideIconsForBouncerManager implements Dumpable {
    public boolean bouncerShowing;
    public boolean bouncerWasShowingWhenHidden;
    public final CommandQueue commandQueue;
    public int displayId;
    public boolean hideIconsForBouncer;
    public boolean isOccluded;
    public final DelayableExecutor mainExecutor;
    public final ShadeInteractor shadeInteractor;
    public boolean statusBarWindowHidden;
    public boolean topAppHidesStatusBar;
    public boolean wereIconsJustHidden;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return StatusBarHideIconsForBouncerManager.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow isAnyExpanded = ((ShadeInteractorImpl) StatusBarHideIconsForBouncerManager.this.shadeInteractor).baseShadeInteractor.isAnyExpanded();
                final StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = StatusBarHideIconsForBouncerManager.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        StatusBarHideIconsForBouncerManager.this.updateHideIconsForBouncer(false);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (isAnyExpanded.collect(flowCollector, this) == coroutineSingletons) {
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

    public StatusBarHideIconsForBouncerManager(CoroutineScope coroutineScope, CommandQueue commandQueue, DelayableExecutor delayableExecutor, StatusBarWindowStateController statusBarWindowStateController, ShadeInteractor shadeInteractor, DumpManager dumpManager) {
        this.commandQueue = commandQueue;
        this.mainExecutor = delayableExecutor;
        this.shadeInteractor = shadeInteractor;
        dumpManager.registerDumpable(this);
        statusBarWindowStateController.addListener(new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager.1
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = StatusBarHideIconsForBouncerManager.this;
                statusBarHideIconsForBouncerManager.getClass();
                statusBarHideIconsForBouncerManager.statusBarWindowHidden = i == 2;
                statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(false);
            }
        });
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(null), 3);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("---- State variables set externally ----");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isShadeOrQsExpanded=", ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue(), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isOccluded=", this.isOccluded, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("bouncerShowing=", this.bouncerShowing, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("topAppHideStatusBar=", this.topAppHidesStatusBar, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("statusBarWindowHidden=", this.statusBarWindowHidden, printWriter);
        printWriter.println("displayId=" + this.displayId);
        printWriter.println("---- State variables calculated internally ----");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("hideIconsForBouncer=", this.hideIconsForBouncer, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("bouncerWasShowingWhenHidden=", this.bouncerWasShowingWhenHidden, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("wereIconsJustHidden=", this.wereIconsJustHidden, printWriter);
    }

    public final void updateHideIconsForBouncer(boolean z) {
        boolean z2 = (this.topAppHidesStatusBar && this.isOccluded && (this.statusBarWindowHidden || this.bouncerShowing)) || (!((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue() && !this.isOccluded && this.bouncerShowing);
        if (this.hideIconsForBouncer != z2) {
            this.hideIconsForBouncer = z2;
            if (z2 || !this.bouncerWasShowingWhenHidden) {
                this.commandQueue.recomputeDisableFlags(this.displayId, z);
            } else {
                this.wereIconsJustHidden = true;
                this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager$updateHideIconsForBouncer$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = StatusBarHideIconsForBouncerManager.this;
                        statusBarHideIconsForBouncerManager.wereIconsJustHidden = false;
                        statusBarHideIconsForBouncerManager.commandQueue.recomputeDisableFlags(statusBarHideIconsForBouncerManager.displayId, true);
                    }
                }, 500L);
            }
        }
        if (z2) {
            this.bouncerWasShowingWhenHidden = this.bouncerShowing;
        }
    }
}
