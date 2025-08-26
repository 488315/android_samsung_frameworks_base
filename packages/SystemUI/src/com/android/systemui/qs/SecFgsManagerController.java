package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class SecFgsManagerController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Function0 dialog;
    public SecQsUiDisplayModeInteractor.FoldState foldState;
    public TextView noItemTextView;
    public final Lazy qsUiDisplayModeInteractor$delegate;
    public RecyclerView recyclerView;
    public final Function1 updateDialog;

    /* renamed from: com.android.systemui.qs.SecFgsManagerController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SecFgsManagerController.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((SecQsUiDisplayModeInteractor.FoldState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SecQsUiDisplayModeInteractor.FoldState foldState = (SecQsUiDisplayModeInteractor.FoldState) this.L$0;
            Log.d("SecFgsManagerController", "foldStateChange foldState = " + SecFgsManagerController.this.foldState + " > " + foldState);
            SecFgsManagerController secFgsManagerController = SecFgsManagerController.this;
            boolean z = false;
            if (secFgsManagerController.foldState != foldState) {
                secFgsManagerController.foldState = foldState;
                SystemUIDialog systemUIDialog = (SystemUIDialog) secFgsManagerController.dialog.invoke();
                if (systemUIDialog != null && systemUIDialog.isShowing()) {
                    z = true;
                }
            }
            Boolean boolValueOf = Boolean.valueOf(z);
            EmergencyButtonController$$ExternalSyntheticOutline0.m("foldStateChange isDirty = ", "SecFgsManagerController", z);
            return boolValueOf;
        }
    }

    /* renamed from: com.android.systemui.qs.SecFgsManagerController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ int I$0;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            int iIntValue = ((Number) obj2).intValue();
            AnonymousClass2 anonymousClass2 = SecFgsManagerController.this.new AnonymousClass2((Continuation) obj3);
            anonymousClass2.Z$0 = zBooleanValue;
            anonymousClass2.I$0 = iIntValue;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Resources.NotFoundException {
            SystemUIDialog systemUIDialog;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            int i = this.I$0;
            SecQsUiDisplayModeInteractor.FoldState foldState = SecFgsManagerController.this.foldState;
            StringBuilder sb = new StringBuilder("smallestWidthDpChange foldState = ");
            sb.append(foldState);
            sb.append(", isDirty = ");
            sb.append(z);
            sb.append(", swDp = ");
            RecyclerView$$ExternalSyntheticOutline0.m(i, "SecFgsManagerController", sb);
            if (z && (systemUIDialog = (SystemUIDialog) SecFgsManagerController.this.dialog.invoke()) != null) {
                SecFgsManagerController secFgsManagerController = SecFgsManagerController.this;
                Context context = systemUIDialog.getContext();
                secFgsManagerController.getClass();
                Context displayContext = SecFgsManagerController.getDisplayContext(context);
                Window window = systemUIDialog.getWindow();
                if (window != null) {
                    boolean zAreEqual = Intrinsics.areEqual(displayContext, window.getContext());
                    if (QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) secFgsManagerController.qsUiDisplayModeInteractor$delegate.getValue()).isTablet() || !zAreEqual) {
                        window.setGravity(8388659);
                        secFgsManagerController.setMargin(window);
                    } else {
                        window.setGravity(81);
                    }
                    secFgsManagerController.setMargin(window);
                }
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

    static {
        new Companion(null);
    }

    public SecFgsManagerController(Function1 function1, Function0 function0, Function0 function02, ConfigurationInteractor configurationInteractor, CoroutineScope coroutineScope) {
        this.updateDialog = function1;
        this.dialog = function0;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new SecFgsManagerController$$ExternalSyntheticLambda0());
        this.qsUiDisplayModeInteractor$delegate = lazy;
        this.foldState = SecQsUiDisplayModeInteractor.FoldState.UNSET;
        function02.invoke();
        FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(FlowKt.mapLatest(((SecQsUiDisplayModeInteractor) lazy.getValue()).getFoldState(), new AnonymousClass1(null))), ((ConfigurationInteractorImpl) configurationInteractor).smallestWidthDp, new AnonymousClass2(null)), coroutineScope);
    }

    public static Context getDisplayContext(Context context) {
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        int topFocusedDisplayId = windowManagerService != null ? windowManagerService.getTopFocusedDisplayId() : 0;
        if (topFocusedDisplayId != 0) {
            Object systemService = context.getSystemService("display");
            DisplayManager displayManager = systemService instanceof DisplayManager ? (DisplayManager) systemService : null;
            if (displayManager != null) {
                Display display = displayManager.getDisplay(topFocusedDisplayId);
                display.getClass();
                Context contextCreateDisplayContext = (display.getFlags() & 131072) != 0 ? context.createDisplayContext(display) : context;
                if (contextCreateDisplayContext != null) {
                    return contextCreateDisplayContext;
                }
            }
        }
        return context;
    }

    public static void log(String str) {
        Log.d("SecFgsManagerController", str);
    }

    public final void setMargin(Window window) throws Resources.NotFoundException {
        WindowManager.LayoutParams attributes;
        if ((QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) this.qsUiDisplayModeInteractor$delegate.getValue()).isTablet()) && (attributes = window.getAttributes()) != null) {
            int dimensionPixelSize = window.getContext().getResources().getDimensionPixelSize(R.dimen.sec_fgs_side_margin_tablet);
            int dimensionPixelSize2 = window.getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_height);
            attributes.x = dimensionPixelSize;
            attributes.y = dimensionPixelSize + dimensionPixelSize2;
        }
    }
}
