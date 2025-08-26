package com.android.wm.shell.apptoweb;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Slog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.window.InputTransferToken;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.R;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import java.util.function.Supplier;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class OpenByDefaultDialog {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ImageView appIconView;
    public final TextView appNameView;
    public final CoroutineScope bgScope;
    public final OpenByDefaultDialogView dialog;
    public AdditionalViewHostViewContainer dialogContainer;
    public final SurfaceControl dialogSurfaceControl;
    public final DomainVerificationManager domainVerificationManager;
    public final DialogLifecycleListener listener;
    public final StandaloneCoroutine loadAppInfoJob;
    public final MainCoroutineDispatcher mainDispatcher;
    public final RadioButton openInAppButton;
    public final RadioButton openInBrowserButton;
    public final String packageName;
    public final Supplier surfaceControlTransactionSupplier;
    public final ActivityManager.RunningTaskInfo taskInfo;
    public final WindowDecorTaskResourceLoader taskResourceLoader;
    public final SurfaceControlViewHost viewHost;

    /* renamed from: com.android.wm.shell.apptoweb.OpenByDefaultDialog$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.wm.shell.apptoweb.OpenByDefaultDialog$1$1, reason: invalid class name and collision with other inner class name */
        final class C06541 extends SuspendLambda implements Function2 {
            final /* synthetic */ Bitmap $icon;
            final /* synthetic */ CharSequence $name;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ OpenByDefaultDialog this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06541(OpenByDefaultDialog openByDefaultDialog, Bitmap bitmap, CharSequence charSequence, Continuation continuation) {
                super(2, continuation);
                this.this$0 = openByDefaultDialog;
                this.$icon = bitmap;
                this.$name = charSequence;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06541 c06541 = new C06541(this.this$0, this.$icon, this.$name, continuation);
                c06541.L$0 = obj;
                return c06541;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06541) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                    return Unit.INSTANCE;
                }
                OpenByDefaultDialog openByDefaultDialog = this.this$0;
                Bitmap bitmap = this.$icon;
                CharSequence charSequence = this.$name;
                ImageView imageView = openByDefaultDialog.appIconView;
                if (imageView == null) {
                    imageView = null;
                }
                imageView.setImageBitmap(bitmap);
                TextView textView = openByDefaultDialog.appNameView;
                (textView != null ? textView : null).setText(charSequence);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = OpenByDefaultDialog.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                    return Unit.INSTANCE;
                }
                OpenByDefaultDialog openByDefaultDialog = OpenByDefaultDialog.this;
                CharSequence name = openByDefaultDialog.taskResourceLoader.getName(openByDefaultDialog.taskInfo);
                OpenByDefaultDialog openByDefaultDialog2 = OpenByDefaultDialog.this;
                Bitmap headerIcon = openByDefaultDialog2.taskResourceLoader.getHeaderIcon(openByDefaultDialog2.taskInfo);
                MainCoroutineDispatcher immediate = OpenByDefaultDialog.this.mainDispatcher.getImmediate();
                C06541 c06541 = new C06541(OpenByDefaultDialog.this, headerIcon, name, null);
                this.label = 1;
                if (BuildersKt.withContext(immediate, c06541, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface DialogLifecycleListener {
    }

    static {
        new Companion(null);
    }

    public OpenByDefaultDialog(Context context, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, Supplier<SurfaceControl.Transaction> supplier, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, DialogLifecycleListener dialogLifecycleListener) throws PackageManager.NameNotFoundException {
        DomainVerificationUserState domainVerificationUserState;
        final int i = 1;
        this.taskInfo = runningTaskInfo;
        this.taskResourceLoader = windowDecorTaskResourceLoader;
        this.surfaceControlTransactionSupplier = supplier;
        this.mainDispatcher = mainCoroutineDispatcher;
        this.bgScope = coroutineScope;
        this.listener = dialogLifecycleListener;
        Object systemService = context.getSystemService((Class<Object>) DomainVerificationManager.class);
        systemService.getClass();
        DomainVerificationManager domainVerificationManager = (DomainVerificationManager) systemService;
        this.domainVerificationManager = domainVerificationManager;
        ComponentName componentName = runningTaskInfo.baseActivity;
        String packageName = componentName != null ? componentName.getPackageName() : null;
        packageName.getClass();
        this.packageName = packageName;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        OpenByDefaultDialogView openByDefaultDialogView = (OpenByDefaultDialogView) LayoutInflater.from(context).inflate(R.layout.open_by_default_settings_dialog, (ViewGroup) null);
        this.dialog = openByDefaultDialogView;
        this.appIconView = (ImageView) (openByDefaultDialogView == null ? null : openByDefaultDialogView).requireViewById(R.id.application_icon);
        OpenByDefaultDialogView openByDefaultDialogView2 = this.dialog;
        this.appNameView = (TextView) (openByDefaultDialogView2 == null ? null : openByDefaultDialogView2).requireViewById(R.id.application_name);
        Display display = displayController.mDisplayManager.getDisplay(runningTaskInfo.displayId);
        SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setName("Open by Default Dialog of Task=" + runningTaskInfo.taskId).setContainerLayer().setParent(surfaceControl).setCallsite("OpenByDefaultDialog#createDialog").build();
        this.dialogSurfaceControl = surfaceControlBuild;
        SurfaceControl.Transaction position = transaction.setPosition(surfaceControlBuild == null ? null : surfaceControlBuild, 0.0f, 0.0f);
        SurfaceControl surfaceControl2 = this.dialogSurfaceControl;
        SurfaceControl.Transaction windowCrop = position.setWindowCrop(surfaceControl2 == null ? null : surfaceControl2, bounds.width(), bounds.height());
        SurfaceControl surfaceControl3 = this.dialogSurfaceControl;
        SurfaceControl.Transaction layer = windowCrop.setLayer(surfaceControl3 == null ? null : surfaceControl3, PluginLockInstancePolicy.DISABLED_BY_MODE);
        SurfaceControl surfaceControl4 = this.dialogSurfaceControl;
        layer.show(surfaceControl4 == null ? null : surfaceControl4);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(bounds.width(), bounds.height(), 1000, 40, -3);
        layoutParams.setTitle("Open by default settings dialog of task=" + runningTaskInfo.taskId);
        layoutParams.setTrustedOverlay();
        Configuration configuration = runningTaskInfo.configuration;
        SurfaceControl surfaceControl5 = this.dialogSurfaceControl;
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, display, new WindowlessWindowManager(configuration, surfaceControl5 == null ? null : surfaceControl5, (InputTransferToken) null), "Dialog");
        OpenByDefaultDialogView openByDefaultDialogView3 = this.dialog;
        surfaceControlViewHost.setView(openByDefaultDialogView3 == null ? null : openByDefaultDialogView3, layoutParams);
        surfaceControlViewHost.getRootSurfaceControl().applyTransactionOnDraw(transaction);
        this.viewHost = surfaceControlViewHost;
        SurfaceControl surfaceControl6 = this.dialogSurfaceControl;
        this.dialogContainer = new AdditionalViewHostViewContainer(surfaceControl6 == null ? null : surfaceControl6, surfaceControlViewHost, supplier);
        OpenByDefaultDialogView openByDefaultDialogView4 = this.dialog;
        openByDefaultDialogView4 = openByDefaultDialogView4 == null ? null : openByDefaultDialogView4;
        final int i2 = 0;
        final Function1 function1 = new Function1(this) { // from class: com.android.wm.shell.apptoweb.OpenByDefaultDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ OpenByDefaultDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                OpenByDefaultDialog openByDefaultDialog = this.f$0;
                switch (i2) {
                    case 0:
                        int i3 = OpenByDefaultDialog.$r8$clinit;
                        StandaloneCoroutine standaloneCoroutine = openByDefaultDialog.loadAppInfoJob;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(null);
                        }
                        AdditionalViewHostViewContainer additionalViewHostViewContainer = openByDefaultDialog.dialogContainer;
                        if (additionalViewHostViewContainer != null) {
                            additionalViewHostViewContainer.releaseView();
                        }
                        openByDefaultDialog.dialogContainer = null;
                        DesktopModeWindowDecoration.this.mOpenByDefaultDialog = null;
                        break;
                    default:
                        int i4 = OpenByDefaultDialog.$r8$clinit;
                        try {
                            DomainVerificationManager domainVerificationManager2 = openByDefaultDialog.domainVerificationManager;
                            String str = openByDefaultDialog.packageName;
                            RadioButton radioButton = openByDefaultDialog.openInAppButton;
                            if (radioButton == null) {
                                radioButton = null;
                            }
                            domainVerificationManager2.setDomainVerificationLinkHandlingAllowed(str, radioButton.isChecked());
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.e("OpenByDefaultDialog", "Failed to change link handling policy due to the package name is not found: " + e);
                        }
                        StandaloneCoroutine standaloneCoroutine2 = openByDefaultDialog.loadAppInfoJob;
                        if (standaloneCoroutine2 != null) {
                            standaloneCoroutine2.cancel(null);
                        }
                        AdditionalViewHostViewContainer additionalViewHostViewContainer2 = openByDefaultDialog.dialogContainer;
                        if (additionalViewHostViewContainer2 != null) {
                            additionalViewHostViewContainer2.releaseView();
                        }
                        openByDefaultDialog.dialogContainer = null;
                        DesktopModeWindowDecoration.this.mOpenByDefaultDialog = null;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        openByDefaultDialogView4.getClass();
        openByDefaultDialogView4.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.apptoweb.OpenByDefaultDialogView$sam$android_view_View_OnClickListener$0
            @Override // android.view.View.OnClickListener
            public final /* synthetic */ void onClick(View view) {
                function1.mo781invoke(view);
            }
        });
        View view = openByDefaultDialogView4.dialogContainer;
        (view == null ? null : view).setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.apptoweb.OpenByDefaultDialogView$setDismissOnClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
            }
        });
        OpenByDefaultDialogView openByDefaultDialogView5 = this.dialog;
        openByDefaultDialogView5 = openByDefaultDialogView5 == null ? null : openByDefaultDialogView5;
        final Function1 function12 = new Function1(this) { // from class: com.android.wm.shell.apptoweb.OpenByDefaultDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ OpenByDefaultDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                OpenByDefaultDialog openByDefaultDialog = this.f$0;
                switch (i) {
                    case 0:
                        int i3 = OpenByDefaultDialog.$r8$clinit;
                        StandaloneCoroutine standaloneCoroutine = openByDefaultDialog.loadAppInfoJob;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(null);
                        }
                        AdditionalViewHostViewContainer additionalViewHostViewContainer = openByDefaultDialog.dialogContainer;
                        if (additionalViewHostViewContainer != null) {
                            additionalViewHostViewContainer.releaseView();
                        }
                        openByDefaultDialog.dialogContainer = null;
                        DesktopModeWindowDecoration.this.mOpenByDefaultDialog = null;
                        break;
                    default:
                        int i4 = OpenByDefaultDialog.$r8$clinit;
                        try {
                            DomainVerificationManager domainVerificationManager2 = openByDefaultDialog.domainVerificationManager;
                            String str = openByDefaultDialog.packageName;
                            RadioButton radioButton = openByDefaultDialog.openInAppButton;
                            if (radioButton == null) {
                                radioButton = null;
                            }
                            domainVerificationManager2.setDomainVerificationLinkHandlingAllowed(str, radioButton.isChecked());
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.e("OpenByDefaultDialog", "Failed to change link handling policy due to the package name is not found: " + e);
                        }
                        StandaloneCoroutine standaloneCoroutine2 = openByDefaultDialog.loadAppInfoJob;
                        if (standaloneCoroutine2 != null) {
                            standaloneCoroutine2.cancel(null);
                        }
                        AdditionalViewHostViewContainer additionalViewHostViewContainer2 = openByDefaultDialog.dialogContainer;
                        if (additionalViewHostViewContainer2 != null) {
                            additionalViewHostViewContainer2.releaseView();
                        }
                        openByDefaultDialog.dialogContainer = null;
                        DesktopModeWindowDecoration.this.mOpenByDefaultDialog = null;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        View view2 = openByDefaultDialogView5.dialogContainer;
        ((Button) (view2 == null ? null : view2).requireViewById(R.id.open_by_default_settings_dialog_confirm_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.apptoweb.OpenByDefaultDialogView$sam$android_view_View_OnClickListener$0
            @Override // android.view.View.OnClickListener
            public final /* synthetic */ void onClick(View view3) {
                function12.mo781invoke(view3);
            }
        });
        DesktopModeWindowDecoration.this.closeHandleMenu();
        OpenByDefaultDialogView openByDefaultDialogView6 = this.dialog;
        this.openInAppButton = (RadioButton) (openByDefaultDialogView6 == null ? null : openByDefaultDialogView6).requireViewById(R.id.open_in_app_button);
        OpenByDefaultDialogView openByDefaultDialogView7 = this.dialog;
        this.openInBrowserButton = (RadioButton) (openByDefaultDialogView7 == null ? null : openByDefaultDialogView7).requireViewById(R.id.open_in_browser_button);
        Intent intent = AppToWebUtils.GenericBrowserIntent;
        try {
            domainVerificationUserState = domainVerificationManager.getDomainVerificationUserState(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
            String message = e.getMessage();
            message.getClass();
            ProtoLog.w(shellProtoLogGroup, "%s: Failed to get domain verification user state: %s", new Object[]{"AppToWebUtils", message});
            domainVerificationUserState = null;
        }
        if (domainVerificationUserState != null) {
            boolean zIsLinkHandlingAllowed = domainVerificationUserState.isLinkHandlingAllowed();
            RadioButton radioButton = this.openInAppButton;
            (radioButton == null ? null : radioButton).setChecked(zIsLinkHandlingAllowed);
            RadioButton radioButton2 = this.openInBrowserButton;
            (radioButton2 == null ? null : radioButton2).setChecked(!zIsLinkHandlingAllowed);
        }
        this.loadAppInfoJob = BuildersKt.launch$default(this.bgScope, null, null, new AnonymousClass1(null), 3);
    }
}
