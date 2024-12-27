package com.android.systemui.media;

import android.app.KeyguardManager;
import android.app.SemStatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityImpl;
import com.android.systemui.media.mediaoutput.analytics.MediaOutputLogging;
import com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$volumeKeyHandler$1;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
import com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class MediaOutputView extends CustomComposeView {
    public final ColoredBGHelper coloredBGHelper;
    public Feature feature;
    public final Lazy onUnhandledKeyEventListener$delegate;
    public final MediaSdkSupportServiceClient serviceClient;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ MediaOutputView(Context context, AttributeSet attributeSet, ViewModelFactory viewModelFactory, ColoredBGHelper coloredBGHelper, AudioManager audioManager, MediaSdkSupportServiceClient mediaSdkSupportServiceClient, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, viewModelFactory, coloredBGHelper, audioManager, (i & 32) != 0 ? null : mediaSdkSupportServiceClient);
    }

    @Override // com.android.systemui.media.CustomComposeView
    public final void ContentView(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(342218409);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (this.feature == null) {
            this.feature = new Feature.Builder().getFeature();
        }
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
        Density density = (Density) composerImpl.consume(staticProvidableCompositionLocal);
        ProvidedValue defaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(new DensityImpl(density.getDensity(), Math.min(density.getFontScale(), 1.3f)));
        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = CompositionExtKt.LocalFeature;
        Feature feature = this.feature;
        if (feature == null) {
            feature = null;
        }
        ProvidedValue defaultProvidedValue$runtime_release2 = staticProvidableCompositionLocal2.defaultProvidedValue$runtime_release(feature);
        ProvidedValue defaultProvidedValue$runtime_release3 = CompositionExtKt.LocalBackgroundColor.defaultProvidedValue$runtime_release(Color.m383boximpl(ColorKt.Color(this.coloredBGHelper.actualAppliedColor)));
        StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = CompositionExtKt.LocalMediaOutputState;
        Feature feature2 = this.feature;
        if (feature2 == null) {
            feature2 = null;
        }
        ProvidedValue defaultProvidedValue$runtime_release4 = staticProvidableCompositionLocal3.defaultProvidedValue$runtime_release(feature2);
        StaticProvidableCompositionLocal staticProvidableCompositionLocal4 = CompositionExtKt.LocalTransitionInfo;
        Feature feature3 = this.feature;
        if (feature3 == null) {
            feature3 = null;
        }
        ProvidedValue defaultProvidedValue$runtime_release5 = staticProvidableCompositionLocal4.defaultProvidedValue$runtime_release(feature3);
        StaticProvidableCompositionLocal staticProvidableCompositionLocal5 = CompositionExtKt.LocalDismissCallback;
        Feature feature4 = this.feature;
        CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{defaultProvidedValue$runtime_release, defaultProvidedValue$runtime_release2, defaultProvidedValue$runtime_release3, defaultProvidedValue$runtime_release4, defaultProvidedValue$runtime_release5, staticProvidableCompositionLocal5.defaultProvidedValue$runtime_release(feature4 != null ? feature4 : null), CompositionExtKt.LocalSecHapticFeedback.defaultProvidedValue$runtime_release(new SecPlatformHapticFeedback((HapticFeedback) composerImpl.consume(CompositionLocalsKt.LocalHapticFeedback)))}, ComposableLambdaKt.rememberComposableLambda(746142185, composerImpl, new Function2() { // from class: com.android.systemui.media.MediaOutputView$ContentView$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                MediaOutputView mediaOutputView = MediaOutputView.this;
                Feature.Companion companion = Feature.Companion;
                Feature feature5 = mediaOutputView.feature;
                if (feature5 == null) {
                    feature5 = null;
                }
                companion.getClass();
                boolean z = feature5.from == 10;
                boolean isKeyguardLocked = ((KeyguardManager) MediaOutputView.this.keyguardManager$delegate.getValue()).isKeyguardLocked();
                String detail = z ? MediaOutputLogging.Details.LOCKSCREEN.getDetail() : MediaOutputLogging.Details.QUICKPANEL.getDetail();
                String detail2 = isKeyguardLocked ? MediaOutputLogging.Details.LOCK.getDetail() : MediaOutputLogging.Details.UNLOCK.getDetail();
                MediaOutputLogging mediaOutputLogging = MediaOutputLogging.INSTANCE;
                MediaOutputLogging.ScreenId screenId = MediaOutputLogging.ScreenId.MEDIA_OUTPUT;
                MediaOutputLogging.Event event = MediaOutputLogging.Event.LAUNCH_MEDIA_OUTPUT;
                Map mapOf = MapsKt__MapsKt.mapOf(new Pair(MediaOutputLogging.CustomKey.FROM, detail), new Pair(MediaOutputLogging.CustomKey.LOCK, detail2));
                mediaOutputLogging.getClass();
                MediaOutputLogging.sendEventCDLog(screenId, event, mapOf);
                MediaOutputHostKt.MediaOutputHost(null, composer2, 0, 1);
                return Unit.INSTANCE;
            }
        }), composerImpl, 56);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.MediaOutputView$ContentView$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    MediaOutputView.this.ContentView(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    @Override // com.android.systemui.media.CustomComposeView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        Object failure;
        super.onAttachedToWindow();
        addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
        MediaSdkSupportServiceClient mediaSdkSupportServiceClient = this.serviceClient;
        if (mediaSdkSupportServiceClient != null) {
            StateFlowImpl stateFlowImpl = mediaSdkSupportServiceClient._serviceBindStateFlow;
            if (!((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                try {
                    int i = Result.$r8$clinit;
                    DLog.Companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService", "");
                    Intent intent = new Intent("com.samsung.android.oneconnect.mediaoutput.START_MEDIA_OUTPUT_SERVICE");
                    intent.setPackage("com.samsung.android.oneconnect");
                    boolean bindService = mediaSdkSupportServiceClient.context.bindService(intent, mediaSdkSupportServiceClient.serviceConnection, 1);
                    stateFlowImpl.updateState(null, Boolean.valueOf(bindService));
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService", "bindResult: " + bindService + " | bindStateEmitted: true");
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    DLog.Companion companion = DLog.Companion;
                    String th2 = m2527exceptionOrNullimpl.toString();
                    companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService.onFailure", th2);
                }
            }
        }
        Feature feature = this.feature;
        Feature feature2 = feature != null ? feature : null;
        Function1 function1 = new Function1() { // from class: com.android.systemui.media.MediaOutputView$onAttachedToWindow$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                Integer valueOf = Integer.valueOf(intValue);
                if (intValue <= 0) {
                    valueOf = null;
                }
                if (valueOf == null) {
                    return null;
                }
                View findViewById = MediaOutputView.this.getRootView().findViewById(valueOf.intValue());
                if (findViewById == null) {
                    return null;
                }
                Rect rect = new Rect();
                findViewById.getGlobalVisibleRect(rect);
                rect.right = findViewById.getMeasuredWidth() + rect.left;
                rect.bottom = findViewById.getMeasuredHeight() + rect.top;
                Log.d("MediaOutputView", "onAttachedToWindow() - rect = " + rect);
                return rect;
            }
        };
        if (Intrinsics.areEqual(feature2.anchorRectCallback, function1)) {
            return;
        }
        feature2.anchorRectCallback = function1;
        feature2.fromRect = (Rect) function1.invoke(Integer.valueOf(feature2.anchorViewId));
    }

    @Override // com.android.systemui.media.CustomComposeView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        SemStatusBarManager semStatusBarManager;
        Object failure;
        super.onDetachedFromWindow();
        MediaSdkSupportServiceClient mediaSdkSupportServiceClient = this.serviceClient;
        if (mediaSdkSupportServiceClient != null) {
            StateFlowImpl stateFlowImpl = mediaSdkSupportServiceClient._serviceBindStateFlow;
            if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                try {
                    int i = Result.$r8$clinit;
                    mediaSdkSupportServiceClient.context.unbindService(mediaSdkSupportServiceClient.serviceConnection);
                    stateFlowImpl.updateState(null, Boolean.FALSE);
                    mediaSdkSupportServiceClient.emitMediaSdkServiceDisconnectedState();
                    DLog.Companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "unbindMediaSdkSupportService", "unbindStateEmitted: true");
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    DLog.Companion companion = DLog.Companion;
                    String th2 = m2527exceptionOrNullimpl.toString();
                    companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "unbindMediaSdkSupportService.onFailure", th2);
                }
            }
        }
        removeOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
        getContext().sendBroadcast(new Intent("com.samsung.android.systemui.action.MEDIA_PANEL_CLOSE"));
        Feature.Companion companion2 = Feature.Companion;
        Feature feature = this.feature;
        if (feature == null) {
            feature = null;
        }
        companion2.getClass();
        if (feature.from == 30 && (semStatusBarManager = (SemStatusBarManager) getContext().getSystemService(SemStatusBarManager.class)) != null) {
            semStatusBarManager.collapsePanels();
        }
        Feature feature2 = this.feature;
        if (feature2 == null) {
            feature2 = null;
        }
        if (Intrinsics.areEqual(feature2.anchorRectCallback, (Object) null)) {
            return;
        }
        feature2.anchorRectCallback = null;
        feature2.fromRect = null;
    }

    public MediaOutputView(Context context, AttributeSet attributeSet, ViewModelFactory viewModelFactory, ColoredBGHelper coloredBGHelper, final AudioManager audioManager, MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(context, attributeSet, viewModelFactory);
        this.coloredBGHelper = coloredBGHelper;
        this.serviceClient = mediaSdkSupportServiceClient;
        this.onUnhandledKeyEventListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.MediaOutputView$onUnhandledKeyEventListener$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new AudioManagerExtKt$volumeKeyHandler$1(audioManager);
            }
        });
        Log.d("MediaOutputView", "init()");
    }
}
