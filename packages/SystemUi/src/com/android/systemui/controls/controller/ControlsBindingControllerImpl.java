package com.android.systemui.controls.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.util.Log;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl.OnActionResponseRunnable;
import com.android.systemui.controls.controller.PackageUpdateMonitor;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.p005ui.ChallengeDialogs;
import com.android.systemui.controls.p005ui.ControlActionCoordinatorImpl;
import com.android.systemui.controls.p005ui.ControlViewHolder;
import com.android.systemui.controls.p005ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ControlsBindingControllerImpl implements ControlsBindingController, CustomControlsBindingController {
    public static final ControlsBindingControllerImpl$Companion$emptyCallback$1 emptyCallback;
    public final ControlsBindingControllerImpl$actionCallbackService$1 actionCallbackService = new IControlsActionCallback.Stub() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$actionCallbackService$1
        public final void accept(IBinder iBinder, String str, int i) {
            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                Log.d("ControlsBindingControllerImpl", "actionCallback: " + str + ", " + i);
            }
            ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            ((ExecutorImpl) controlsBindingControllerImpl.backgroundExecutor).execute(controlsBindingControllerImpl.new OnActionResponseRunnable(iBinder, str, i));
        }
    };
    public final DelayableExecutor backgroundExecutor;
    public final Context context;
    public final ControlsUtil controlsUtil;
    public ControlsProviderLifecycleManager currentProvider;
    public UserHandle currentUser;
    public final Lazy lazyController;
    public LoadSubscriber loadSubscriber;
    public final PackageUpdateMonitor.Factory packageUpdateMonitorFactory;
    public StatefulControlSubscriber statefulControlSubscriber;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class CallbackRunnable implements Runnable {
        public final ControlsProviderLifecycleManager provider;
        public final IBinder token;

        public CallbackRunnable(IBinder iBinder) {
            this.token = iBinder;
            this.provider = ControlsBindingControllerImpl.this.currentProvider;
        }

        public abstract void doRun();

        @Override // java.lang.Runnable
        public final void run() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager == null) {
                Log.e("ControlsBindingControllerImpl", "No current provider set");
                return;
            }
            if (!Intrinsics.areEqual(controlsProviderLifecycleManager.user, ControlsBindingControllerImpl.this.currentUser)) {
                Log.e("ControlsBindingControllerImpl", "User " + this.provider.user + " is not current user");
                return;
            }
            if (Intrinsics.areEqual(this.token, this.provider.token)) {
                doRun();
                return;
            }
            Log.e("ControlsBindingControllerImpl", "Provider for token:" + this.token + " does not exist anymore");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoadSubscriber extends IControlsSubscriber.Stub {
        public Lambda _loadCancelInternal;
        public ControlsBindingController.LoadCallback callback;
        public final long requestLimit;
        public IControlsSubscription subscription;
        public final ArrayList loadedControls = new ArrayList();
        public final AtomicBoolean isTerminated = new AtomicBoolean(false);

        public LoadSubscriber(ControlsBindingController.LoadCallback loadCallback, long j) {
            this.callback = loadCallback;
            this.requestLimit = j;
        }

        public final void maybeTerminateAndRun(final CallbackRunnable callbackRunnable) {
            if (this.isTerminated.get()) {
                return;
            }
            this._loadCancelInternal = new Function0() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$maybeTerminateAndRun$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
            this.callback = ControlsBindingControllerImpl.emptyCallback;
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsBindingControllerImpl.this.currentProvider;
            if (controlsProviderLifecycleManager != null) {
                ExecutorImpl.ExecutionToken executionToken = controlsProviderLifecycleManager.onLoadCanceller;
                if (executionToken != null) {
                    executionToken.run();
                }
                controlsProviderLifecycleManager.onLoadCanceller = null;
            }
            ((ExecutorImpl) ControlsBindingControllerImpl.this.backgroundExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$maybeTerminateAndRun$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsBindingControllerImpl.LoadSubscriber.this.isTerminated.compareAndSet(false, true);
                    callbackRunnable.run();
                }
            });
        }

        public final void onComplete(IBinder iBinder) {
            maybeTerminateAndRun(new OnLoadRunnable(ControlsBindingControllerImpl.this, iBinder, this.loadedControls, this.callback));
        }

        public final void onError(IBinder iBinder, String str) {
            maybeTerminateAndRun(new OnLoadErrorRunnable(ControlsBindingControllerImpl.this, iBinder, str, this.callback));
        }

        public final void onNext(final IBinder iBinder, final Control control) {
            final ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            ((ExecutorImpl) controlsBindingControllerImpl.backgroundExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onNext$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (ControlsBindingControllerImpl.LoadSubscriber.this.isTerminated.get()) {
                        return;
                    }
                    ControlsBindingControllerImpl.LoadSubscriber.this.loadedControls.add(control);
                    long size = ControlsBindingControllerImpl.LoadSubscriber.this.loadedControls.size();
                    ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = ControlsBindingControllerImpl.LoadSubscriber.this;
                    if (size >= loadSubscriber.requestLimit) {
                        ControlsBindingControllerImpl controlsBindingControllerImpl2 = controlsBindingControllerImpl;
                        IBinder iBinder2 = iBinder;
                        ArrayList arrayList = loadSubscriber.loadedControls;
                        IControlsSubscription iControlsSubscription = loadSubscriber.subscription;
                        if (iControlsSubscription == null) {
                            iControlsSubscription = null;
                        }
                        loadSubscriber.maybeTerminateAndRun(new ControlsBindingControllerImpl.OnCancelAndLoadRunnable(controlsBindingControllerImpl2, iBinder2, arrayList, iControlsSubscription, loadSubscriber.callback));
                    }
                }
            });
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1, kotlin.jvm.internal.Lambda] */
        public final void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) {
            this.subscription = iControlsSubscription;
            final ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            this._loadCancelInternal = new Function0() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsBindingControllerImpl.this.currentProvider;
                    if (controlsProviderLifecycleManager != null) {
                        IControlsSubscription iControlsSubscription2 = this.subscription;
                        if (iControlsSubscription2 == null) {
                            iControlsSubscription2 = null;
                        }
                        controlsProviderLifecycleManager.cancelSubscription(iControlsSubscription2);
                    }
                    return Unit.INSTANCE;
                }
            };
            ControlsBindingControllerImpl controlsBindingControllerImpl2 = ControlsBindingControllerImpl.this;
            ((ExecutorImpl) controlsBindingControllerImpl2.backgroundExecutor).execute(new OnSubscribeRunnable(controlsBindingControllerImpl2, iBinder, iControlsSubscription, this.requestLimit));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnActionResponseRunnable extends CallbackRunnable {
        public final String controlId;
        public final int response;

        public OnActionResponseRunnable(IBinder iBinder, String str, int i) {
            super(iBinder);
            this.controlId = str;
            this.response = i;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public final void doRun() {
            if (this.provider != null) {
                ControlsController controlsController = (ControlsController) ControlsBindingControllerImpl.this.lazyController.get();
                final String str = this.controlId;
                final int i = this.response;
                ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) controlsController;
                if (controlsControllerImpl.confirmAvailability()) {
                    final CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) controlsControllerImpl.uiController;
                    customControlsUiControllerImpl.getClass();
                    ((ExecutorImpl) customControlsUiControllerImpl.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$onActionResponse$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainControlAdapter mainControlAdapter = CustomControlsUiControllerImpl.this.controlAdapter;
                            if (mainControlAdapter != null) {
                                final String str2 = str;
                                final int i2 = i;
                                ((ExecutorImpl) mainControlAdapter.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$actionResponse$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LinkedHashMap linkedHashMap = (LinkedHashMap) MainControlAdapter.controlViewHolders;
                                        Object obj = linkedHashMap.get(str2);
                                        String str3 = str2;
                                        int i3 = i2;
                                        StringBuilder sb = new StringBuilder("actionResponse: ");
                                        sb.append(obj);
                                        sb.append(", controlId=");
                                        sb.append(str3);
                                        sb.append(", response=");
                                        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i3, "MainControlAdapter");
                                        final ControlViewHolder controlViewHolder = (ControlViewHolder) linkedHashMap.get(str2);
                                        if (controlViewHolder != null) {
                                            int i4 = i2;
                                            ((ControlActionCoordinatorImpl) controlViewHolder.controlActionCoordinator).actionsInProgress.remove(controlViewHolder.getCws().f249ci.controlId);
                                            boolean z = controlViewHolder.lastChallengeDialog != null;
                                            final AlertDialog alertDialog = null;
                                            if (i4 == 0) {
                                                controlViewHolder.lastChallengeDialog = null;
                                                controlViewHolder.setErrorStatus();
                                                return;
                                            }
                                            if (i4 == 1) {
                                                controlViewHolder.lastChallengeDialog = null;
                                                return;
                                            }
                                            if (i4 == 2) {
                                                controlViewHolder.lastChallengeDialog = null;
                                                controlViewHolder.setErrorStatus();
                                                return;
                                            }
                                            final Function0 function0 = controlViewHolder.onDialogCancel;
                                            if (i4 != 3) {
                                                if (i4 == 4) {
                                                    ChallengeDialogs.INSTANCE.getClass();
                                                    Dialog createPinDialog = ChallengeDialogs.createPinDialog(controlViewHolder, false, z, function0);
                                                    controlViewHolder.lastChallengeDialog = createPinDialog;
                                                    if (createPinDialog != null) {
                                                        createPinDialog.show();
                                                        return;
                                                    }
                                                    return;
                                                }
                                                if (i4 != 5) {
                                                    return;
                                                }
                                                ChallengeDialogs.INSTANCE.getClass();
                                                Dialog createPinDialog2 = ChallengeDialogs.createPinDialog(controlViewHolder, true, z, function0);
                                                controlViewHolder.lastChallengeDialog = createPinDialog2;
                                                if (createPinDialog2 != null) {
                                                    createPinDialog2.show();
                                                    return;
                                                }
                                                return;
                                            }
                                            ChallengeDialogs.INSTANCE.getClass();
                                            boolean z2 = BasicRune.CONTROLS_SAMSUNG_STYLE;
                                            int i5 = ChallengeDialogs.STYLE;
                                            TextView textView = controlViewHolder.title;
                                            Context context = controlViewHolder.context;
                                            if (z2) {
                                                final ControlAction controlAction = controlViewHolder.lastAction;
                                                if (controlAction == null) {
                                                    Log.e("ControlsUiController", "Confirmation Dialog attempted but no last action is set. Will not show");
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context, i5);
                                                    builder.setTitle(context.getResources().getString(R.string.controls_custom_confirmation_message, textView.getText()));
                                                    builder.setPositiveButton(R.string.controls_custom_dialog_ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomConfirmationDialog$builder$1$1
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(DialogInterface dialogInterface, int i6) {
                                                            ControlViewHolder.this.action(ChallengeDialogs.access$addChallengeValue(ChallengeDialogs.INSTANCE, controlAction, "true"));
                                                            dialogInterface.dismiss();
                                                        }
                                                    });
                                                    builder.setNegativeButton(R.string.controls_custom_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomConfirmationDialog$builder$1$2
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(DialogInterface dialogInterface, int i6) {
                                                            Function0.this.invoke();
                                                            dialogInterface.cancel();
                                                        }
                                                    });
                                                    alertDialog = builder.create();
                                                    alertDialog.getWindow().setType(2020);
                                                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomConfirmationDialog$1$2
                                                        @Override // android.content.DialogInterface.OnShowListener
                                                        public final void onShow(DialogInterface dialogInterface) {
                                                            int color = alertDialog.getContext().getResources().getColor(R.color.basic_interaction_dialog_button, alertDialog.getContext().getTheme());
                                                            AlertDialog alertDialog2 = alertDialog;
                                                            alertDialog2.getButton(-1).setTextColor(color);
                                                            alertDialog2.getButton(-2).setTextColor(color);
                                                        }
                                                    });
                                                }
                                            } else {
                                                final ControlAction controlAction2 = controlViewHolder.lastAction;
                                                if (controlAction2 == null) {
                                                    Log.e("ControlsUiController", "Confirmation Dialog attempted but no last action is set. Will not show");
                                                } else {
                                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context, i5);
                                                    builder2.setTitle(context.getResources().getString(R.string.controls_confirmation_message, textView.getText()));
                                                    builder2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createConfirmationDialog$builder$1$1
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(DialogInterface dialogInterface, int i6) {
                                                            ControlViewHolder.this.action(ChallengeDialogs.access$addChallengeValue(ChallengeDialogs.INSTANCE, controlAction2, "true"));
                                                            dialogInterface.dismiss();
                                                        }
                                                    });
                                                    builder2.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createConfirmationDialog$builder$1$2
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(DialogInterface dialogInterface, int i6) {
                                                            Function0.this.invoke();
                                                            dialogInterface.cancel();
                                                        }
                                                    });
                                                    alertDialog = builder2.create();
                                                    alertDialog.getWindow().setType(2020);
                                                }
                                            }
                                            controlViewHolder.lastChallengeDialog = alertDialog;
                                            if (alertDialog != null) {
                                                alertDialog.show();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnCancelAndLoadRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final List list;
        public final IControlsSubscription subscription;

        public OnCancelAndLoadRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, List<Control> list, IControlsSubscription iControlsSubscription, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            this.list = list;
            this.subscription = iControlsSubscription;
            this.callback = loadCallback;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public final void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Canceling and loading controls");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                controlsProviderLifecycleManager.cancelSubscription(this.subscription);
            }
            this.callback.accept(this.list);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnLoadErrorRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final String error;

        public OnLoadErrorRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, String str, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            this.error = str;
            this.callback = loadCallback;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public final void doRun() {
            this.callback.error(this.error);
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                Log.e("ControlsBindingControllerImpl", "onError receive from '" + controlsProviderLifecycleManager.componentName + "': " + this.error);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnLoadRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final List list;

        public OnLoadRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, List<Control> list, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            this.list = list;
            this.callback = loadCallback;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public final void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Complete and loading controls");
            this.callback.accept(this.list);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnSubscribeRunnable extends CallbackRunnable {
        public final long requestLimit;
        public final IControlsSubscription subscription;

        public OnSubscribeRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, IControlsSubscription iControlsSubscription, long j) {
            super(iBinder);
            this.subscription = iControlsSubscription;
            this.requestLimit = j;
        }

        @Override // com.android.systemui.controls.controller.ControlsBindingControllerImpl.CallbackRunnable
        public final void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Starting subscription");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                controlsProviderLifecycleManager.startSubscription(this.subscription, this.requestLimit);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$Companion$emptyCallback$1] */
    static {
        new Companion(null);
        emptyCallback = new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$Companion$emptyCallback$1
            @Override // java.util.function.Consumer
            public final /* bridge */ /* synthetic */ void accept(Object obj) {
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public final void error(String str) {
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.controls.controller.ControlsBindingControllerImpl$actionCallbackService$1] */
    public ControlsBindingControllerImpl(Context context, DelayableExecutor delayableExecutor, Lazy lazy, PackageUpdateMonitor.Factory factory, UserTracker userTracker, ControlsUtil controlsUtil) {
        this.context = context;
        this.backgroundExecutor = delayableExecutor;
        this.lazyController = lazy;
        this.packageUpdateMonitorFactory = factory;
        this.controlsUtil = controlsUtil;
        this.currentUser = ((UserTrackerImpl) userTracker).getUserHandle();
    }

    /* renamed from: createProviderManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public ControlsProviderLifecycleManager m111xb2527126(ComponentName componentName) {
        return new ControlsProviderLifecycleManager(this.context, this.backgroundExecutor, this.actionCallbackService, this.currentUser, componentName, this.packageUpdateMonitorFactory, this.controlsUtil);
    }

    public final ControlsProviderLifecycleManager retrieveLifecycleManager(ComponentName componentName) {
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            if (!Intrinsics.areEqual(controlsProviderLifecycleManager != null ? controlsProviderLifecycleManager.componentName : null, componentName)) {
                if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                    ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = this.currentProvider;
                    Log.d("ControlsBindingControllerImpl", "currentProvider=" + (controlsProviderLifecycleManager2 != null ? controlsProviderLifecycleManager2.componentName : null) + " component=" + componentName + "}");
                }
                unbind();
            }
        }
        ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = this.currentProvider;
        if (controlsProviderLifecycleManager3 == null) {
            controlsProviderLifecycleManager3 = m111xb2527126(componentName);
        }
        this.currentProvider = controlsProviderLifecycleManager3;
        return controlsProviderLifecycleManager3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("  ControlsBindingController:\n");
        sb.append("    currentUser=" + this.currentUser + "\n");
        sb.append("    StatefulControlSubscriber=" + this.statefulControlSubscriber);
        sb.append("    Providers=" + this.currentProvider + "\n");
        return sb.toString();
    }

    public final void unbind() {
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.d("ControlsBindingControllerImpl", "unbind loadSubscriber=" + this.loadSubscriber + ", currentProvider=" + this.currentProvider);
        }
        unsubscribe();
        LoadSubscriber loadSubscriber = this.loadSubscriber;
        if (loadSubscriber != null) {
            new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber);
        }
        this.loadSubscriber = null;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            controlsProviderLifecycleManager.unbindService();
        }
        this.currentProvider = null;
    }

    public final void unsubscribe() {
        final StatefulControlSubscriber statefulControlSubscriber = this.statefulControlSubscriber;
        if (statefulControlSubscriber != null && statefulControlSubscriber.subscriptionOpen) {
            ((ExecutorImpl) statefulControlSubscriber.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$cancel$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                        StatefulControlSubscriber statefulControlSubscriber2 = StatefulControlSubscriber.this;
                        Log.d("StatefulControlSubscriber", "cancel subscriptionOpen=" + statefulControlSubscriber2.subscriptionOpen + ", subscription=" + statefulControlSubscriber2.subscription);
                    }
                    StatefulControlSubscriber statefulControlSubscriber3 = StatefulControlSubscriber.this;
                    if (statefulControlSubscriber3.subscriptionOpen) {
                        statefulControlSubscriber3.subscriptionOpen = false;
                        IControlsSubscription iControlsSubscription = statefulControlSubscriber3.subscription;
                        if (iControlsSubscription != null) {
                            statefulControlSubscriber3.provider.cancelSubscription(iControlsSubscription);
                        }
                        StatefulControlSubscriber.this.subscription = null;
                    }
                }
            });
            if (BasicRune.CONTROLS_MEMORY_LEAK_BUGFIX) {
                statefulControlSubscriber.controller = null;
            }
        }
        this.statefulControlSubscriber = null;
    }
}
