package androidx.compose.material3;

import android.content.Context;
import android.view.Window;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AbstractComposeView;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class ModalBottomSheetDialogLayout extends AbstractComposeView {
    public ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 backCallback;
    public final MutableState content$delegate;
    public final Function0 onDismissRequest;
    public final Animatable predictiveBackProgress;
    public final CoroutineScope scope;
    public boolean shouldCreateCompositionOnAttachedToWindow;
    public final boolean shouldDismissOnBackPress;

    final class Api33Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Api33Impl();
        }

        private Api33Impl() {
        }
    }

    final class Api34Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Api34Impl();
        }

        private Api34Impl() {
        }
    }

    public ModalBottomSheetDialogLayout(Context context, Window window, boolean z, Function0 function0, Animatable<Float, AnimationVector1D> animatable, CoroutineScope coroutineScope) {
        super(context, null, 0, 6, null);
        this.shouldDismissOnBackPress = z;
        this.onDismissRequest = function0;
        this.predictiveBackProgress = animatable;
        this.scope = coroutineScope;
        ComposableSingletons$ModalBottomSheet_androidKt.INSTANCE.getClass();
        this.content$delegate = SnapshotStateKt.mutableStateOf$default(ComposableSingletons$ModalBottomSheet_androidKt.f9lambda2);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void Content(ComposerImpl composerImpl) {
        composerImpl.startReplaceGroup(576708319);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetDialogLayout.Content (ModalBottomSheet.android.kt:367)");
        }
        ((Function2) ((SnapshotMutableStateImpl) this.content$delegate).getValue()).invoke(composerImpl, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final boolean getShouldCreateCompositionOnAttachedToWindow() {
        return this.shouldCreateCompositionOnAttachedToWindow;
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [androidx.compose.material3.ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1] */
    @Override // androidx.compose.ui.platform.AbstractComposeView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher;
        super.onAttachedToWindow();
        if (this.shouldDismissOnBackPress) {
            if (this.backCallback == null) {
                final Function0 function0 = this.onDismissRequest;
                final Animatable animatable = this.predictiveBackProgress;
                final CoroutineScope coroutineScope = this.scope;
                int i = Api34Impl.$r8$clinit;
                this.backCallback = new OnBackAnimationCallback() { // from class: androidx.compose.material3.ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1
                    @Override // android.window.OnBackAnimationCallback
                    public final void onBackCancelled() {
                        BuildersKt.launch$default(coroutineScope, null, null, new ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackCancelled$1(animatable, null), 3);
                    }

                    @Override // android.window.OnBackInvokedCallback
                    public final void onBackInvoked() {
                        function0.invoke();
                    }

                    @Override // android.window.OnBackAnimationCallback
                    public final void onBackProgressed(BackEvent backEvent) {
                        BuildersKt.launch$default(coroutineScope, null, null, new ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackProgressed$1(animatable, backEvent, null), 3);
                    }

                    @Override // android.window.OnBackAnimationCallback
                    public final void onBackStarted(BackEvent backEvent) {
                        BuildersKt.launch$default(coroutineScope, null, null, new ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1$onBackStarted$1(animatable, backEvent, null), 3);
                    }
                };
            }
            ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 modalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 = this.backCallback;
            int i2 = Api33Impl.$r8$clinit;
            if (modalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 == null || (onBackInvokedDispatcherFindOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) == null) {
                return;
            }
            onBackInvokedDispatcherFindOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, modalBottomSheetDialogLayout$Api34Impl$createBackCallback$1);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher;
        super.onDetachedFromWindow();
        ModalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 modalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 = this.backCallback;
        int i = Api33Impl.$r8$clinit;
        if (modalBottomSheetDialogLayout$Api34Impl$createBackCallback$1 != null && (onBackInvokedDispatcherFindOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) != null) {
            onBackInvokedDispatcherFindOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(modalBottomSheetDialogLayout$Api34Impl$createBackCallback$1);
        }
        this.backCallback = null;
    }
}
