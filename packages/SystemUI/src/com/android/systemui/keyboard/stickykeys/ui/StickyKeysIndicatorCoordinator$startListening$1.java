package com.android.systemui.keyboard.stickykeys.ui;

import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.activity.ComponentDialog;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.platform.ComposeView;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger$$ExternalSyntheticLambda0;
import com.android.systemui.keyboard.stickykeys.ui.viewmodel.StickyKeysIndicatorViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class StickyKeysIndicatorCoordinator$startListening$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ StickyKeysIndicatorCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyKeysIndicatorCoordinator$startListening$1(StickyKeysIndicatorCoordinator stickyKeysIndicatorCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = stickyKeysIndicatorCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new StickyKeysIndicatorCoordinator$startListening$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StickyKeysIndicatorCoordinator$startListening$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final StickyKeysIndicatorCoordinator stickyKeysIndicatorCoordinator = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = stickyKeysIndicatorCoordinator.viewModel.indicatorContent;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.stickykeys.ui.StickyKeysIndicatorCoordinator$startListening$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Map map = (Map) obj2;
                    StickyKeysIndicatorCoordinator stickyKeysIndicatorCoordinator2 = StickyKeysIndicatorCoordinator.this;
                    StickyKeysLogger stickyKeysLogger = stickyKeysIndicatorCoordinator2.stickyKeysLogger;
                    stickyKeysLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    StickyKeysLogger$$ExternalSyntheticLambda0 stickyKeysLogger$$ExternalSyntheticLambda0 = new StickyKeysLogger$$ExternalSyntheticLambda0(2);
                    LogBuffer logBuffer = stickyKeysLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("stickyKeys", logLevel, stickyKeysLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) obtain).str1 = map.toString();
                    logBuffer.commit(obtain);
                    if (map.isEmpty()) {
                        ComponentDialog componentDialog = stickyKeysIndicatorCoordinator2.dialog;
                        if (componentDialog != null) {
                            componentDialog.dismiss();
                        }
                        stickyKeysIndicatorCoordinator2.dialog = null;
                    } else if (stickyKeysIndicatorCoordinator2.dialog == null) {
                        StickyKeyDialogFactory stickyKeyDialogFactory = stickyKeysIndicatorCoordinator2.stickyKeyDialogFactory;
                        stickyKeyDialogFactory.getClass();
                        ComponentDialog componentDialog2 = new ComponentDialog(stickyKeyDialogFactory.context, R.style.Theme_SystemUI_Dialog_StickyKeys);
                        Window window = componentDialog2.getWindow();
                        if (window != null) {
                            window.requestFeature(1);
                            window.setType(2017);
                            window.addFlags(24);
                            window.clearFlags(2);
                            window.setGravity(8388661);
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                            layoutParams.copyFrom(window.getAttributes());
                            layoutParams.receiveInsetsIgnoringZOrder = true;
                            layoutParams.setFitInsetsTypes(WindowInsets.Type.systemBars());
                            layoutParams.setTitle("StickyKeysIndicator");
                            window.setAttributes(layoutParams);
                        }
                        ComposeView composeView = new ComposeView(componentDialog2.getContext(), null, 0, 6, null);
                        final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel = stickyKeysIndicatorCoordinator2.viewModel;
                        composeView.setContent(new ComposableLambdaImpl(1769291460, true, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$createStickyKeyIndicatorView$1$1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl = (ComposerImpl) composer;
                                    if (composerImpl.getSkipping()) {
                                        composerImpl.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.createStickyKeyIndicatorView.<anonymous>.<anonymous> (StickyKeysIndicator.kt:56)");
                                }
                                final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel2 = StickyKeysIndicatorViewModel.this;
                                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(474739514, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt$createStickyKeyIndicatorView$1$1.1
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj5, Object obj6) {
                                        Composer composer2 = (Composer) obj5;
                                        if ((((Number) obj6).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                            if (composerImpl2.getSkipping()) {
                                                composerImpl2.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.createStickyKeyIndicatorView.<anonymous>.<anonymous>.<anonymous> (StickyKeysIndicator.kt:57)");
                                        }
                                        MaterialTheme.INSTANCE.getClass();
                                        ProvidedValue defaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(MaterialTheme.getColorScheme(composer2).onSurfaceVariant));
                                        final StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel3 = StickyKeysIndicatorViewModel.this;
                                        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(2070702074, new Function2() { // from class: com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt.createStickyKeyIndicatorView.1.1.1.1
                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj7, Object obj8) {
                                                Composer composer3 = (Composer) obj7;
                                                if ((((Number) obj8).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                    if (composerImpl3.getSkipping()) {
                                                        composerImpl3.skipToGroupEnd();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.stickykeys.ui.view.createStickyKeyIndicatorView.<anonymous>.<anonymous>.<anonymous>.<anonymous> (StickyKeysIndicator.kt:59)");
                                                }
                                                StickyKeysIndicatorKt.StickyKeysIndicator(StickyKeysIndicatorViewModel.this, composer3, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composer2), composer2, 56);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer), composer, 48, 1);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }));
                        componentDialog2.setContentView(composeView);
                        Window window2 = componentDialog2.getWindow();
                        if (window2 != null) {
                            window2.setWindowAnimations(0);
                        }
                        stickyKeysIndicatorCoordinator2.dialog = componentDialog2;
                        componentDialog2.show();
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
