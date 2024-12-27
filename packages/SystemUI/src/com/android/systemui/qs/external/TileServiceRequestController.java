package com.android.systemui.qs.external;

import android.app.IUriGrantsManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.TileRequestDialog;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.qs.external.TileServiceRequestController.TileServiceRequestCommand;
import com.android.systemui.qs.tileimpl.DummyTileView;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TileServiceRequestController {
    public final CommandQueue commandQueue;
    public final TileServiceRequestController$commandQueueCallback$1 commandQueueCallback;
    public final CommandRegistry commandRegistry;
    public Function1 dialogCanceller;
    public final Function0 dialogCreator;
    public final TileRequestDialogEventLogger eventLogger;
    public final IUriGrantsManager iUriGrantsManager;
    public final QSHost qsHost;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Builder {
        public final CommandQueue commandQueue;
        public final CommandRegistry commandRegistry;
        public final IUriGrantsManager iUriGrantsManager;

        public Builder(CommandQueue commandQueue, CommandRegistry commandRegistry, IUriGrantsManager iUriGrantsManager) {
            this.commandQueue = commandQueue;
            this.commandRegistry = commandRegistry;
            this.iUriGrantsManager = iUriGrantsManager;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SingleShotConsumer implements Consumer {
        public final Consumer consumer;
        public final AtomicBoolean dispatched = new AtomicBoolean(false);

        public SingleShotConsumer(Consumer<Object> consumer) {
            this.consumer = consumer;
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            if (this.dispatched.compareAndSet(false, true)) {
                this.consumer.accept(obj);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TileServiceRequestCommand implements Command {
        public TileServiceRequestCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString((String) list.get(0));
            if (unflattenFromString == null) {
                Log.w("TileServiceRequestController", "Malformed componentName " + list.get(0));
                return;
            }
            TileServiceRequestController.this.requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0, unflattenFromString, (CharSequence) list.get(1), (CharSequence) list.get(2), null, new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$TileServiceRequestCommand$execute$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(((Number) obj).intValue(), "Response: ", "TileServiceRequestController");
                }
            });
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1] */
    public TileServiceRequestController(QSHost qSHost, CommandQueue commandQueue, CommandRegistry commandRegistry, TileRequestDialogEventLogger tileRequestDialogEventLogger, IUriGrantsManager iUriGrantsManager, Function0 function0) {
        this.qsHost = qSHost;
        this.commandQueue = commandQueue;
        this.commandRegistry = commandRegistry;
        this.eventLogger = tileRequestDialogEventLogger;
        this.iUriGrantsManager = iUriGrantsManager;
        this.dialogCreator = function0;
        this.commandQueueCallback = new CommandQueue.Callbacks() { // from class: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void cancelRequestAddTile(String str) {
                Function1 function1 = TileServiceRequestController.this.dialogCanceller;
                if (function1 != null) {
                    function1.invoke(str);
                }
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, final IAddTileResultCallback iAddTileResultCallback) {
                TileServiceRequestController.this.requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, componentName, charSequence, charSequence2, icon, new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1$requestAddTile$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        try {
                            iAddTileResultCallback.onTileRequest(((Number) obj).intValue());
                        } catch (RemoteException e) {
                            Log.e("TileServiceRequestController", "Couldn't respond to request", e);
                        }
                    }
                });
            }
        };
    }

    public final void init() {
        this.commandRegistry.registerCommand("tile-service-add", new Function0() { // from class: com.android.systemui.qs.external.TileServiceRequestController$init$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TileServiceRequestController.this.new TileServiceRequestCommand();
            }
        });
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallback);
    }

    public final void requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i, final ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, final Consumer<Integer> consumer) {
        InstanceId instanceId;
        QSTile.Icon icon2;
        TileRequestDialogEventLogger tileRequestDialogEventLogger = this.eventLogger;
        final InstanceId newInstanceId = tileRequestDialogEventLogger.instanceIdSequence.newInstanceId();
        final String packageName = componentName.getPackageName();
        if (this.qsHost.indexOf(CustomTile.toSpec(componentName)) != -1) {
            consumer.accept(1);
            tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED, 0, packageName, newInstanceId);
            return;
        }
        final SingleShotConsumer singleShotConsumer = new SingleShotConsumer(new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TileRequestDialogEvent tileRequestDialogEvent;
                int intValue = ((Number) obj).intValue();
                if (intValue == 2) {
                    TileServiceRequestController tileServiceRequestController = TileServiceRequestController.this;
                    tileServiceRequestController.qsHost.addTile(componentName, true);
                }
                TileServiceRequestController tileServiceRequestController2 = TileServiceRequestController.this;
                tileServiceRequestController2.dialogCanceller = null;
                String str = packageName;
                InstanceId instanceId2 = newInstanceId;
                TileRequestDialogEventLogger tileRequestDialogEventLogger2 = tileServiceRequestController2.eventLogger;
                tileRequestDialogEventLogger2.getClass();
                if (intValue == 0) {
                    tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_NOT_ADDED;
                } else if (intValue == 2) {
                    tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ADDED;
                } else {
                    if (intValue != 3) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(intValue, "User response not valid: "));
                    }
                    tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_DISMISSED;
                }
                tileRequestDialogEventLogger2.uiEventLogger.logWithInstanceId(tileRequestDialogEvent, 0, str, instanceId2);
                consumer.accept(Integer.valueOf(intValue));
            }
        });
        TileRequestDialog.TileData tileData = new TileRequestDialog.TileData(i, charSequence, charSequence2, icon, componentName.getPackageName());
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$dialogClickListener$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                if (i2 == -1) {
                    TileServiceRequestController.SingleShotConsumer.this.accept(2);
                } else {
                    TileServiceRequestController.SingleShotConsumer.this.accept(0);
                }
            }
        };
        Object invoke = this.dialogCreator.invoke();
        TileRequestDialog tileRequestDialog = (TileRequestDialog) invoke;
        IUriGrantsManager iUriGrantsManager = this.iUriGrantsManager;
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(tileRequestDialog.getContext()).inflate(R.layout.sec_tile_service_request_dialog, (ViewGroup) null);
        new ContextThemeWrapper(tileRequestDialog.getContext(), R.style.Theme_SystemUI_QuickSettings);
        final DummyTileView dummyTileView = new DummyTileView(tileRequestDialog.getContext(), null, null, 6, null);
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = tileData.label;
        booleanState.handlesLongClick = false;
        Icon icon3 = tileData.icon;
        if (icon3 != null) {
            instanceId = newInstanceId;
            Drawable loadDrawableCheckingUriGrant = icon3.loadDrawableCheckingUriGrant(tileRequestDialog.getContext(), iUriGrantsManager, tileData.callingUid, tileData.packageName);
            if (loadDrawableCheckingUriGrant != null) {
                icon2 = new QSTileImpl.DrawableIcon(loadDrawableCheckingUriGrant);
                booleanState.icon = icon2;
                booleanState.contentDescription = booleanState.label;
                dummyTileView.onStateChanged(booleanState);
                dummyTileView.post(new Runnable() { // from class: com.android.systemui.qs.external.TileRequestDialog$createTileView$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSTileView.this.setStateDescription("");
                        QSTileView.this.setClickable(false);
                        QSTileView.this.setSelected(true);
                    }
                });
                viewGroup.addView(dummyTileView, viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.add_qs_tile_width), -2);
                viewGroup.setSelected(true);
                tileRequestDialog.setView(viewGroup, 0, 0, 0, 0);
                SystemUIDialog.setShowForAllUsers(tileRequestDialog);
                tileRequestDialog.setCanceledOnTouchOutside(true);
                tileRequestDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        TileServiceRequestController.SingleShotConsumer.this.accept(3);
                    }
                });
                tileRequestDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        TileServiceRequestController.SingleShotConsumer.this.accept(3);
                    }
                });
                tileRequestDialog.setTitle(R.string.sec_request_add_tile_title);
                tileRequestDialog.setMessage(tileRequestDialog.getContext().getString(R.string.sec_qs_tile_request_dialog_text, tileData.appName));
                tileRequestDialog.setPositiveButton(R.string.sec_qs_tile_request_dialog_add, onClickListener);
                tileRequestDialog.setNegativeButton(R.string.sec_qs_tile_request_dialog_cancel, onClickListener);
                final SystemUIDialog systemUIDialog = (SystemUIDialog) invoke;
                this.dialogCanceller = new Function1() { // from class: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        if (Intrinsics.areEqual(packageName, (String) obj)) {
                            systemUIDialog.cancel();
                        }
                        this.dialogCanceller = null;
                        return Unit.INSTANCE;
                    }
                };
                systemUIDialog.show();
                tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_SHOWN, 0, packageName, instanceId);
            }
        } else {
            instanceId = newInstanceId;
        }
        icon2 = QSTileImpl.ResourceIcon.get(R.drawable.android);
        booleanState.icon = icon2;
        booleanState.contentDescription = booleanState.label;
        dummyTileView.onStateChanged(booleanState);
        dummyTileView.post(new Runnable() { // from class: com.android.systemui.qs.external.TileRequestDialog$createTileView$1
            @Override // java.lang.Runnable
            public final void run() {
                QSTileView.this.setStateDescription("");
                QSTileView.this.setClickable(false);
                QSTileView.this.setSelected(true);
            }
        });
        viewGroup.addView(dummyTileView, viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.add_qs_tile_width), -2);
        viewGroup.setSelected(true);
        tileRequestDialog.setView(viewGroup, 0, 0, 0, 0);
        SystemUIDialog.setShowForAllUsers(tileRequestDialog);
        tileRequestDialog.setCanceledOnTouchOutside(true);
        tileRequestDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$1
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                TileServiceRequestController.SingleShotConsumer.this.accept(3);
            }
        });
        tileRequestDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                TileServiceRequestController.SingleShotConsumer.this.accept(3);
            }
        });
        tileRequestDialog.setTitle(R.string.sec_request_add_tile_title);
        tileRequestDialog.setMessage(tileRequestDialog.getContext().getString(R.string.sec_qs_tile_request_dialog_text, tileData.appName));
        tileRequestDialog.setPositiveButton(R.string.sec_qs_tile_request_dialog_add, onClickListener);
        tileRequestDialog.setNegativeButton(R.string.sec_qs_tile_request_dialog_cancel, onClickListener);
        final SystemUIDialog systemUIDialog2 = (SystemUIDialog) invoke;
        this.dialogCanceller = new Function1() { // from class: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                if (Intrinsics.areEqual(packageName, (String) obj)) {
                    systemUIDialog2.cancel();
                }
                this.dialogCanceller = null;
                return Unit.INSTANCE;
            }
        };
        systemUIDialog2.show();
        tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_SHOWN, 0, packageName, instanceId);
    }

    public /* synthetic */ TileServiceRequestController(final QSHost qSHost, CommandQueue commandQueue, CommandRegistry commandRegistry, TileRequestDialogEventLogger tileRequestDialogEventLogger, IUriGrantsManager iUriGrantsManager, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(qSHost, commandQueue, commandRegistry, tileRequestDialogEventLogger, iUriGrantsManager, (i & 32) != 0 ? new Function0() { // from class: com.android.systemui.qs.external.TileServiceRequestController.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new TileRequestDialog(QSHost.this.getContext());
            }
        } : function0);
    }
}
