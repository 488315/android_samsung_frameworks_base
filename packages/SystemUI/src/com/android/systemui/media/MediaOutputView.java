package com.android.systemui.media;

import android.app.KeyguardManager;
import android.app.SemStatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.analytics.SaScreen;
import com.android.systemui.media.mediaoutput.common.ScpmHelper;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
import com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class MediaOutputView extends CustomComposeView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ColoredBGHelper coloredBGHelper;
    public Feature feature;
    public final LargeScreenHeaderHelper largeScreenHeaderHelper;
    public final Lazy largeScreenShadeHeaderHeight$delegate;
    public final Lazy onUnhandledKeyEventListener$delegate;
    public final PowerManager powerManager;
    public final ScpmHelper scpmHelper;
    public final MediaSdkSupportServiceClient serviceClient;

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

    public /* synthetic */ MediaOutputView(Context context, AttributeSet attributeSet, ViewModelFactory viewModelFactory, ColoredBGHelper coloredBGHelper, AudioManager audioManager, MediaSdkSupportServiceClient mediaSdkSupportServiceClient, PowerManager powerManager, LargeScreenHeaderHelper largeScreenHeaderHelper, ScpmHelper scpmHelper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, viewModelFactory, coloredBGHelper, audioManager, (i & 32) != 0 ? null : mediaSdkSupportServiceClient, powerManager, largeScreenHeaderHelper, scpmHelper);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ee  */
    @Override // com.android.systemui.media.CustomComposeView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void ContentView(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(342218409);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.MediaOutputView.ContentView (MediaOutputView.kt:71)");
        }
        if (this.feature == null) {
            this.feature = new Feature.Builder().getFeature();
        }
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
        Density density = (Density) composerImpl.consume(staticProvidableCompositionLocal);
        ProvidedValue providedValueDefaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(density.getDensity(), Math.min(density.getFontScale(), 1.3f)));
        StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = CompositionExtKt.LocalFeature;
        Feature feature = this.feature;
        if (feature == null) {
            feature = null;
        }
        ProvidedValue providedValueDefaultProvidedValue$runtime_release2 = staticProvidableCompositionLocal2.defaultProvidedValue$runtime_release(feature);
        ProvidedValue providedValueDefaultProvidedValue$runtime_release3 = CompositionExtKt.LocalBackgroundColor.defaultProvidedValue$runtime_release(Color.m456boximpl(ColorKt.Color(this.coloredBGHelper.actualAppliedColor)));
        StaticProvidableCompositionLocal staticProvidableCompositionLocal3 = CompositionExtKt.LocalMediaOutputState;
        Feature feature2 = this.feature;
        if (feature2 == null) {
            feature2 = null;
        }
        ProvidedValue providedValueDefaultProvidedValue$runtime_release4 = staticProvidableCompositionLocal3.defaultProvidedValue$runtime_release(feature2);
        StaticProvidableCompositionLocal staticProvidableCompositionLocal4 = CompositionExtKt.LocalTransitionInfo;
        Feature feature3 = this.feature;
        if (feature3 == null) {
            feature3 = null;
        }
        ProvidedValue providedValueDefaultProvidedValue$runtime_release5 = staticProvidableCompositionLocal4.defaultProvidedValue$runtime_release(feature3);
        StaticProvidableCompositionLocal staticProvidableCompositionLocal5 = CompositionExtKt.LocalDismissCallback;
        Feature feature4 = this.feature;
        if (feature4 == null) {
            feature4 = null;
        }
        ProvidedValue providedValueDefaultProvidedValue$runtime_release6 = staticProvidableCompositionLocal5.defaultProvidedValue$runtime_release(feature4);
        ProvidedValue providedValueDefaultProvidedValue$runtime_release7 = CompositionExtKt.LocalSecHapticFeedback.defaultProvidedValue$runtime_release(new SecPlatformHapticFeedback((HapticFeedback) composerImpl.consume(CompositionLocalsKt.LocalHapticFeedback)));
        StaticProvidableCompositionLocal staticProvidableCompositionLocal6 = CompositionExtKt.LocalLargeScreenHeaderHeight;
        composerImpl.startReplaceGroup(-1184396815);
        boolean zChangedInstance = composerImpl.changedInstance(this);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new MediaOutputView$$ExternalSyntheticLambda1(this, 1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        ProvidedValue[] providedValueArr = {providedValueDefaultProvidedValue$runtime_release, providedValueDefaultProvidedValue$runtime_release2, providedValueDefaultProvidedValue$runtime_release3, providedValueDefaultProvidedValue$runtime_release4, providedValueDefaultProvidedValue$runtime_release5, providedValueDefaultProvidedValue$runtime_release6, providedValueDefaultProvidedValue$runtime_release7, staticProvidableCompositionLocal6.defaultProvidedValue$runtime_release((Function0) objRememberedValue)};
        ComposableSingletons$MediaOutputViewKt.INSTANCE.getClass();
        CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$MediaOutputViewKt.f50lambda1, composerImpl, 56);
        Unit unit = Unit.INSTANCE;
        composerImpl.startReplaceGroup(-1184393052);
        boolean zChangedInstance2 = composerImpl.changedInstance(this);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance2) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new MediaOutputView$ContentView$2$1(this, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (CollectionsKt___CollectionsKt.contains(Arrays.asList(0, 1), motionEvent != null ? Integer.valueOf(motionEvent.getAction()) : null)) {
            this.powerManager.userActivity(SystemClock.uptimeMillis(), false);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.media.CustomComposeView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        String str;
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
                    boolean zBindService = mediaSdkSupportServiceClient.context.bindService(intent, mediaSdkSupportServiceClient.serviceConnection, 1);
                    stateFlowImpl.updateState(null, Boolean.valueOf(zBindService));
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService", "bindResult: " + zBindService + " | bindStateEmitted: true");
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    DLog.Companion companion = DLog.Companion;
                    String string = thM3441exceptionOrNullimpl.toString();
                    companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService.onFailure", string);
                }
            }
        }
        Feature feature = this.feature;
        if (feature == null) {
            feature = null;
        }
        feature.setAnchorRectCallback(new MediaOutputView$$ExternalSyntheticLambda2(this));
        Feature feature2 = this.feature;
        if (feature2 == null) {
            feature2 = null;
        }
        Screen screen = feature2.defaultScreen;
        if (!Intrinsics.areEqual(screen, Screen.Phone.INSTANCE)) {
            if (Intrinsics.areEqual(screen, Screen.TV.INSTANCE)) {
                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.LaunchTvCard.INSTANCE);
                return;
            } else {
                if (Intrinsics.areEqual(screen, Screen.Selector.INSTANCE)) {
                    MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.ChooseADevicePage.INSTANCE);
                    return;
                }
                return;
            }
        }
        MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
        SaEvent.LaunchMediaOutput launchMediaOutput = SaEvent.LaunchMediaOutput.INSTANCE;
        SaScreen.MediaOutput mediaOutput = SaScreen.MediaOutput.INSTANCE;
        Feature.Companion companion2 = Feature.Companion;
        Feature feature3 = this.feature;
        if (feature3 == null) {
            feature3 = null;
        }
        companion2.getClass();
        if (feature3.from == 10) {
            str = "Lockscreen";
        } else {
            Feature feature4 = this.feature;
            if ((feature4 != null ? feature4 : null).from == 30) {
                str = "SmartThings";
            } else {
                str = (feature4 != null ? feature4 : null).from == 50 ? "SmartView" : "Quickpanel";
            }
        }
        SaCustom[] saCustomArr = {new SaCustom.From(str), new SaCustom.Lock(((KeyguardManager) this.keyguardManager$delegate.getValue()).isKeyguardLocked() ? "Lock" : "Unlock")};
        moSaLogging.getClass();
        MoSaLogging.send(launchMediaOutput, mediaOutput, saCustomArr);
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
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    DLog.Companion companion = DLog.Companion;
                    String string = thM3441exceptionOrNullimpl.toString();
                    companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "unbindMediaSdkSupportService.onFailure", string);
                }
            }
        }
        removeOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
        Feature feature = this.feature;
        if (feature == null) {
            feature = null;
        }
        if (!feature.isRotated) {
            getContext().sendBroadcast(new Intent("com.samsung.android.systemui.action.MEDIA_PANEL_CLOSE"));
        }
        Feature.Companion companion2 = Feature.Companion;
        Feature feature2 = this.feature;
        if (feature2 == null) {
            feature2 = null;
        }
        companion2.getClass();
        if (feature2.from == 30 && (semStatusBarManager = (SemStatusBarManager) getContext().getSystemService(SemStatusBarManager.class)) != null) {
            semStatusBarManager.collapsePanels();
        }
        Feature feature3 = this.feature;
        if (feature3 == null) {
            feature3 = null;
        }
        feature3.setAnchorRectCallback(null);
    }

    public MediaOutputView(Context context, AttributeSet attributeSet, ViewModelFactory viewModelFactory, ColoredBGHelper coloredBGHelper, AudioManager audioManager, MediaSdkSupportServiceClient mediaSdkSupportServiceClient, PowerManager powerManager, LargeScreenHeaderHelper largeScreenHeaderHelper, ScpmHelper scpmHelper) {
        super(context, attributeSet, viewModelFactory);
        this.coloredBGHelper = coloredBGHelper;
        this.serviceClient = mediaSdkSupportServiceClient;
        this.powerManager = powerManager;
        this.largeScreenHeaderHelper = largeScreenHeaderHelper;
        this.scpmHelper = scpmHelper;
        this.onUnhandledKeyEventListener$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputView$$ExternalSyntheticLambda1(audioManager, 2));
        this.largeScreenShadeHeaderHeight$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputView$$ExternalSyntheticLambda1(this, 0));
        Log.d("MediaOutputView", "init()");
    }
}
