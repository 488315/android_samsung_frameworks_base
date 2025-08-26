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
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.ui.dialog.TileRequestDialogComposeDelegate;
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
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TileServiceRequestController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandQueue commandQueue;
    public final TileServiceRequestController$commandQueueCallback$1 commandQueueCallback;
    public final CommandRegistry commandRegistry;
    public TileServiceRequestController$$ExternalSyntheticLambda1 dialogCanceller;
    public final Function0 dialogCreator;
    public final TileRequestDialogEventLogger eventLogger;
    public final IUriGrantsManager iUriGrantsManager;
    public final QSHost qsHost;

    public final class Builder {
        public final CommandQueue commandQueue;
        public final CommandRegistry commandRegistry;
        public final IUriGrantsManager iUriGrantsManager;
        public final TileRequestDialogComposeDelegate.Factory tileRequestDialogComposeDelegateFactory;

        public Builder(CommandQueue commandQueue, CommandRegistry commandRegistry, IUriGrantsManager iUriGrantsManager, TileRequestDialogComposeDelegate.Factory factory) {
            this.commandQueue = commandQueue;
            this.commandRegistry = commandRegistry;
            this.iUriGrantsManager = iUriGrantsManager;
            this.tileRequestDialogComposeDelegateFactory = factory;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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

    public final class TileServiceRequestCommand implements Command {
        public TileServiceRequestCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString((String) list.get(0));
            if (componentNameUnflattenFromString == null) {
                Log.w("TileServiceRequestController", "Malformed componentName " + list.get(0));
                return;
            }
            TileServiceRequestController.this.requestTileAdd(0, componentNameUnflattenFromString, (CharSequence) list.get(1), (CharSequence) list.get(2), null, new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$TileServiceRequestCommand$execute$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Log.d("TileServiceRequestController", "Response: " + ((Integer) obj));
                }
            });
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1] */
    public TileServiceRequestController(QSHost qSHost, CommandQueue commandQueue, CommandRegistry commandRegistry, TileRequestDialogEventLogger tileRequestDialogEventLogger, IUriGrantsManager iUriGrantsManager, TileRequestDialogComposeDelegate.Factory factory, Function0 function0) {
        this.qsHost = qSHost;
        this.commandQueue = commandQueue;
        this.commandRegistry = commandRegistry;
        this.eventLogger = tileRequestDialogEventLogger;
        this.iUriGrantsManager = iUriGrantsManager;
        this.dialogCreator = function0;
        this.commandQueueCallback = new CommandQueue.Callbacks() { // from class: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void cancelRequestAddTile(String str) {
                TileServiceRequestController$$ExternalSyntheticLambda1 tileServiceRequestController$$ExternalSyntheticLambda1 = this.this$0.dialogCanceller;
                if (tileServiceRequestController$$ExternalSyntheticLambda1 != null) {
                    tileServiceRequestController$$ExternalSyntheticLambda1.mo781invoke(str);
                }
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, final IAddTileResultCallback iAddTileResultCallback) {
                this.this$0.requestTileAdd(i, componentName, charSequence, charSequence2, icon, new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1$requestAddTile$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        try {
                            iAddTileResultCallback.onTileRequest(((Integer) obj).intValue());
                        } catch (RemoteException e) {
                            Log.e("TileServiceRequestController", "Couldn't respond to request", e);
                        }
                    }
                });
            }
        };
    }

    public final SystemUIDialog requestTileAdd(int i, final ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, final Consumer<Integer> consumer) {
        Object obj;
        QSTile.Icon drawableIcon;
        TileRequestDialogEventLogger tileRequestDialogEventLogger = this.eventLogger;
        final InstanceId instanceIdNewInstanceId = tileRequestDialogEventLogger.instanceIdSequence.newInstanceId();
        final String packageName = componentName.getPackageName();
        if (this.qsHost.indexOf(CustomTile.toSpec(componentName)) != -1) {
            consumer.accept(1);
            tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED, 0, packageName, instanceIdNewInstanceId);
            return null;
        }
        final SingleShotConsumer singleShotConsumer = new SingleShotConsumer(new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                TileRequestDialogEvent tileRequestDialogEvent;
                Integer num = (Integer) obj2;
                if (num.intValue() == 2) {
                    TileServiceRequestController tileServiceRequestController = this.this$0;
                    tileServiceRequestController.qsHost.addTile(componentName, true);
                }
                TileServiceRequestController tileServiceRequestController2 = this.this$0;
                tileServiceRequestController2.dialogCanceller = null;
                int iIntValue = num.intValue();
                String str = packageName;
                InstanceId instanceId = instanceIdNewInstanceId;
                TileRequestDialogEventLogger tileRequestDialogEventLogger2 = tileServiceRequestController2.eventLogger;
                tileRequestDialogEventLogger2.getClass();
                if (iIntValue == 0) {
                    tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_NOT_ADDED;
                } else if (iIntValue == 2) {
                    tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ADDED;
                } else {
                    if (iIntValue != 3) {
                        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue, "User response not valid: "));
                    }
                    tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_DISMISSED;
                }
                tileRequestDialogEventLogger2.uiEventLogger.logWithInstanceId(tileRequestDialogEvent, 0, str, instanceId);
                consumer.accept(num);
            }
        });
        TileData tileData = new TileData(i, charSequence, charSequence2, icon, componentName.getPackageName());
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$dialogClickListener$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                if (i2 == -1) {
                    singleShotConsumer.accept(2);
                } else {
                    singleShotConsumer.accept(0);
                }
            }
        };
        Object objInvoke = this.dialogCreator.invoke();
        TileRequestDialog tileRequestDialog = (TileRequestDialog) objInvoke;
        IUriGrantsManager iUriGrantsManager = this.iUriGrantsManager;
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(tileRequestDialog.getContext()).inflate(R.layout.sec_tile_service_request_dialog, (ViewGroup) null);
        new ContextThemeWrapper(tileRequestDialog.getContext(), R.style.Theme_SystemUI_QuickSettings);
        final DummyTileView dummyTileView = new DummyTileView(tileRequestDialog.getContext(), null, null, 6, null);
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = tileData.label;
        booleanState.handlesLongClick = false;
        Icon icon2 = tileData.icon;
        if (icon2 != null) {
            obj = objInvoke;
            Drawable drawableLoadDrawableCheckingUriGrant = icon2.loadDrawableCheckingUriGrant(tileRequestDialog.getContext(), iUriGrantsManager, tileData.callingUid, tileData.packageName);
            if (drawableLoadDrawableCheckingUriGrant != null) {
                drawableIcon = new QSTileImpl.DrawableIcon(drawableLoadDrawableCheckingUriGrant);
            }
            booleanState.icon = drawableIcon;
            booleanState.contentDescription = booleanState.label;
            dummyTileView.onStateChanged(booleanState);
            dummyTileView.post(new Runnable() { // from class: com.android.systemui.qs.external.TileRequestDialog$createTileView$1
                @Override // java.lang.Runnable
                public final void run() {
                    dummyTileView.setStateDescription("");
                    dummyTileView.setClickable(false);
                    dummyTileView.setSelected(true);
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
                    singleShotConsumer.accept(3);
                }
            });
            tileRequestDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$2
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    singleShotConsumer.accept(3);
                }
            });
            tileRequestDialog.setTitle(R.string.sec_request_add_tile_title);
            tileRequestDialog.setMessage(tileRequestDialog.getContext().getString(R.string.sec_qs_tile_request_dialog_text, tileData.appName));
            tileRequestDialog.setPositiveButton(R.string.sec_qs_tile_request_dialog_add, onClickListener);
            tileRequestDialog.setNegativeButton(R.string.sec_qs_tile_request_dialog_cancel, onClickListener);
            SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
            this.dialogCanceller = new TileServiceRequestController$$ExternalSyntheticLambda1(packageName, systemUIDialog, this);
            systemUIDialog.show();
            tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_SHOWN, 0, packageName, instanceIdNewInstanceId);
            return systemUIDialog;
        }
        obj = objInvoke;
        drawableIcon = QSTileImpl.ResourceIcon.get(R.drawable.android);
        booleanState.icon = drawableIcon;
        booleanState.contentDescription = booleanState.label;
        dummyTileView.onStateChanged(booleanState);
        dummyTileView.post(new Runnable() { // from class: com.android.systemui.qs.external.TileRequestDialog$createTileView$1
            @Override // java.lang.Runnable
            public final void run() {
                dummyTileView.setStateDescription("");
                dummyTileView.setClickable(false);
                dummyTileView.setSelected(true);
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
                singleShotConsumer.accept(3);
            }
        });
        tileRequestDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                singleShotConsumer.accept(3);
            }
        });
        tileRequestDialog.setTitle(R.string.sec_request_add_tile_title);
        tileRequestDialog.setMessage(tileRequestDialog.getContext().getString(R.string.sec_qs_tile_request_dialog_text, tileData.appName));
        tileRequestDialog.setPositiveButton(R.string.sec_qs_tile_request_dialog_add, onClickListener);
        tileRequestDialog.setNegativeButton(R.string.sec_qs_tile_request_dialog_cancel, onClickListener);
        SystemUIDialog systemUIDialog2 = (SystemUIDialog) obj;
        this.dialogCanceller = new TileServiceRequestController$$ExternalSyntheticLambda1(packageName, systemUIDialog2, this);
        systemUIDialog2.show();
        tileRequestDialogEventLogger.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_SHOWN, 0, packageName, instanceIdNewInstanceId);
        return systemUIDialog2;
    }

    public /* synthetic */ TileServiceRequestController(QSHost qSHost, CommandQueue commandQueue, CommandRegistry commandRegistry, TileRequestDialogEventLogger tileRequestDialogEventLogger, IUriGrantsManager iUriGrantsManager, TileRequestDialogComposeDelegate.Factory factory, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(qSHost, commandQueue, commandRegistry, tileRequestDialogEventLogger, iUriGrantsManager, factory, (i & 64) != 0 ? new TileServiceRequestController$$ExternalSyntheticLambda0(qSHost, 1) : function0);
    }
}
