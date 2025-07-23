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
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.analytics.SaScreen;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputView extends CustomComposeView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ColoredBGHelper coloredBGHelper;
    public Feature feature;
    public final LargeScreenHeaderHelper largeScreenHeaderHelper;
    public final Lazy largeScreenShadeHeaderHeight$delegate;
    public final Lazy onUnhandledKeyEventListener$delegate;
    public final PowerManager powerManager;
    public final MediaSdkSupportServiceClient serviceClient;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public /* synthetic */ MediaOutputView(Context context, AttributeSet attributeSet, ViewModelFactory viewModelFactory, ColoredBGHelper coloredBGHelper, AudioManager audioManager, MediaSdkSupportServiceClient mediaSdkSupportServiceClient, PowerManager powerManager, LargeScreenHeaderHelper largeScreenHeaderHelper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, viewModelFactory, coloredBGHelper, audioManager, (i & 32) != 0 ? null : mediaSdkSupportServiceClient, powerManager, largeScreenHeaderHelper);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x00af, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
     */
    @Override // com.android.systemui.media.CustomComposeView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void ContentView(androidx.compose.runtime.Composer r11) {
        /*
            r10 = this;
            androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
            r0 = 342218409(0x1465d6a9, float:1.160388E-26)
            r11.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "com.android.systemui.media.MediaOutputView.ContentView (MediaOutputView.kt:66)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            com.android.systemui.media.mediaoutput.compose.common.Feature r0 = r10.feature
            if (r0 != 0) goto L22
            com.android.systemui.media.mediaoutput.compose.common.Feature$Builder r0 = new com.android.systemui.media.mediaoutput.compose.common.Feature$Builder
            r0.<init>()
            com.android.systemui.media.mediaoutput.compose.common.Feature r0 = r0.getFeature()
            r10.feature = r0
        L22:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.CompositionLocalsKt.LocalDensity
            java.lang.Object r1 = r11.consume(r0)
            androidx.compose.ui.unit.Density r1 = (androidx.compose.ui.unit.Density) r1
            float r2 = r1.getDensity()
            float r1 = r1.getFontScale()
            r3 = 1067869798(0x3fa66666, float:1.3)
            float r1 = java.lang.Math.min(r1, r3)
            androidx.compose.ui.unit.Density r1 = androidx.compose.ui.unit.DensityKt.Density(r2, r1)
            androidx.compose.runtime.ProvidedValue r2 = r0.defaultProvidedValue$runtime_release(r1)
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalFeature
            com.android.systemui.media.mediaoutput.compose.common.Feature r1 = r10.feature
            r3 = 0
            if (r1 == 0) goto L49
            goto L4a
        L49:
            r1 = r3
        L4a:
            androidx.compose.runtime.ProvidedValue r0 = r0.defaultProvidedValue$runtime_release(r1)
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalBackgroundColor
            com.android.systemui.qs.bar.ColoredBGHelper r4 = r10.coloredBGHelper
            int r4 = r4.actualAppliedColor
            long r4 = androidx.compose.ui.graphics.ColorKt.Color(r4)
            androidx.compose.ui.graphics.Color r4 = androidx.compose.ui.graphics.Color.m454boximpl(r4)
            androidx.compose.runtime.ProvidedValue r4 = r1.defaultProvidedValue$runtime_release(r4)
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalMediaOutputState
            com.android.systemui.media.mediaoutput.compose.common.Feature r5 = r10.feature
            if (r5 == 0) goto L67
            goto L68
        L67:
            r5 = r3
        L68:
            androidx.compose.runtime.ProvidedValue r5 = r1.defaultProvidedValue$runtime_release(r5)
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalTransitionInfo
            com.android.systemui.media.mediaoutput.compose.common.Feature r6 = r10.feature
            if (r6 == 0) goto L73
            goto L74
        L73:
            r6 = r3
        L74:
            androidx.compose.runtime.ProvidedValue r6 = r1.defaultProvidedValue$runtime_release(r6)
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalDismissCallback
            com.android.systemui.media.mediaoutput.compose.common.Feature r7 = r10.feature
            if (r7 == 0) goto L7f
            r3 = r7
        L7f:
            androidx.compose.runtime.ProvidedValue r7 = r1.defaultProvidedValue$runtime_release(r3)
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalSecHapticFeedback
            com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback r3 = new com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback
            androidx.compose.runtime.StaticProvidableCompositionLocal r8 = androidx.compose.ui.platform.CompositionLocalsKt.LocalHapticFeedback
            java.lang.Object r8 = r11.consume(r8)
            androidx.compose.ui.hapticfeedback.HapticFeedback r8 = (androidx.compose.ui.hapticfeedback.HapticFeedback) r8
            r3.<init>(r8)
            androidx.compose.runtime.ProvidedValue r8 = r1.defaultProvidedValue$runtime_release(r3)
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalLargeScreenHeaderHeight
            r3 = -1184404079(0xffffffffb9676d91, float:-2.2070693E-4)
            r11.startReplaceGroup(r3)
            boolean r3 = r11.changedInstance(r10)
            java.lang.Object r9 = r11.rememberedValue()
            if (r3 != 0) goto Lb1
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r9 != r3) goto Lba
        Lb1:
            com.android.systemui.media.MediaOutputView$$ExternalSyntheticLambda2 r9 = new com.android.systemui.media.MediaOutputView$$ExternalSyntheticLambda2
            r3 = 1
            r9.<init>(r10, r3)
            r11.updateRememberedValue(r9)
        Lba:
            kotlin.jvm.functions.Function0 r9 = (kotlin.jvm.functions.Function0) r9
            r10 = 0
            r11.end(r10)
            androidx.compose.runtime.ProvidedValue r9 = r1.defaultProvidedValue$runtime_release(r9)
            r3 = r0
            androidx.compose.runtime.ProvidedValue[] r0 = new androidx.compose.runtime.ProvidedValue[]{r2, r3, r4, r5, r6, r7, r8, r9}
            com.android.systemui.media.ComposableSingletons$MediaOutputViewKt r1 = com.android.systemui.media.ComposableSingletons$MediaOutputViewKt.INSTANCE
            r1.getClass()
            androidx.compose.runtime.internal.ComposableLambdaImpl r1 = com.android.systemui.media.ComposableSingletons$MediaOutputViewKt.f50lambda1
            r2 = 56
            androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(r0, r1, r11, r2)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lde
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lde:
            r11.end(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaOutputView.ContentView(androidx.compose.runtime.Composer):void");
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
                    boolean bindService = mediaSdkSupportServiceClient.context.bindService(intent, mediaSdkSupportServiceClient.serviceConnection, 1);
                    stateFlowImpl.updateState(null, Boolean.valueOf(bindService));
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService", "bindResult: " + bindService + " | bindStateEmitted: true");
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    DLog.Companion companion = DLog.Companion;
                    String th2 = m3422exceptionOrNullimpl.toString();
                    companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "bindMediaSdkSupportService.onFailure", th2);
                }
            }
        }
        Feature feature = this.feature;
        if (feature == null) {
            feature = null;
        }
        feature.setAnchorRectCallback(new MediaOutputView$$ExternalSyntheticLambda0(this));
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
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    DLog.Companion companion = DLog.Companion;
                    String th2 = m3422exceptionOrNullimpl.toString();
                    companion.getClass();
                    DLog.Companion.i("MediaSdkSupportServiceClient", "unbindMediaSdkSupportService.onFailure", th2);
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

    public MediaOutputView(Context context, AttributeSet attributeSet, ViewModelFactory viewModelFactory, ColoredBGHelper coloredBGHelper, AudioManager audioManager, MediaSdkSupportServiceClient mediaSdkSupportServiceClient, PowerManager powerManager, LargeScreenHeaderHelper largeScreenHeaderHelper) {
        super(context, attributeSet, viewModelFactory);
        this.coloredBGHelper = coloredBGHelper;
        this.serviceClient = mediaSdkSupportServiceClient;
        this.powerManager = powerManager;
        this.largeScreenHeaderHelper = largeScreenHeaderHelper;
        this.onUnhandledKeyEventListener$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputView$$ExternalSyntheticLambda2(audioManager, 2));
        this.largeScreenShadeHeaderHeight$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputView$$ExternalSyntheticLambda2(this, 0));
        Log.d("MediaOutputView", "init()");
    }
}
