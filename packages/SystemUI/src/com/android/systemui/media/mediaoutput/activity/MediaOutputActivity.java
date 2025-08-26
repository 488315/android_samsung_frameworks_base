package com.android.systemui.media.mediaoutput.activity;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import androidx.compose.foundation.layout.BoxWithConstraintsKt;
import androidx.compose.foundation.layout.BoxWithConstraintsScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.analytics.SaScreen;
import com.android.systemui.media.mediaoutput.compose.CoverScreenKt;
import com.android.systemui.media.mediaoutput.compose.PhoneScreenKt;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
import com.samsung.sesl.compose.theme.ThemeKt;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaOutputActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnonymousClass5 actionScreenReceiver;
    public final AudioManager audioManager;
    public final ViewModelFactory factory;
    public final Lazy onUnhandledKeyEventListener$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputActivity$$ExternalSyntheticLambda0(this, 0));

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

    public MediaOutputActivity(ViewModelFactory viewModelFactory, AudioManager audioManager) {
        this.factory = viewModelFactory;
        this.audioManager = audioManager;
    }

    @Override // androidx.activity.ComponentActivity, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final CreationExtras getDefaultViewModelCreationExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            intent.putExtra("displayId", getDisplay().getDisplayId());
        }
        return super.getDefaultViewModelCreationExtras();
    }

    @Override // androidx.activity.ComponentActivity, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return this.factory;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().getDecorView().addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.activity.MediaOutputActivity$onCreate$5] */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        final boolean z;
        Log.d("MediaOutputActivity", "onCreate()");
        super.onCreate(bundle);
        Intent intent = getIntent();
        final boolean booleanExtra = intent != null ? intent.getBooleanExtra("extra_is_dex", false) : false;
        if (getDisplay().getDisplayId() == 1) {
            z = true;
        } else {
            Intent intent2 = getIntent();
            if (!(intent2 != null ? intent2.getBooleanExtra("extra_is_cover", false) : false)) {
                z = false;
            }
        }
        if (booleanExtra) {
            Display display = getDisplay();
            if (!(display.getDisplayId() == 0)) {
                display = null;
            }
            if (display != null) {
                Intent intent3 = new Intent("com.android.systemui.action.OPEN_MEDIA_OUTPUT");
                intent3.setPackage(getPackageName());
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    intent3.putExtras(extras);
                }
                sendBroadcast(intent3);
                finish();
                return;
            }
            Intent intent4 = getIntent();
            if (intent4 != null) {
                intent4.putExtra("isDex", true);
            }
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.LaunchMediaOutput launchMediaOutput = SaEvent.LaunchMediaOutput.INSTANCE;
            SaScreen.MediaOutput mediaOutput = SaScreen.MediaOutput.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.From("DeX")};
            moSaLogging.getClass();
            MoSaLogging.send(launchMediaOutput, mediaOutput, saCustomArr);
            z = true;
        } else {
            if (!z) {
                finish();
                return;
            }
            getWindow().setBackgroundDrawableResource(R.color.black);
            if (AppCompatDelegate.sDefaultNightMode != 2) {
                AppCompatDelegate.sDefaultNightMode = 2;
                synchronized (AppCompatDelegate.sActivityDelegatesLock) {
                    try {
                        ArraySet arraySet = AppCompatDelegate.sActivityDelegates;
                        arraySet.getClass();
                        ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                        while (elementIterator.hasNext()) {
                            AppCompatDelegate appCompatDelegate = (AppCompatDelegate) ((WeakReference) elementIterator.next()).get();
                            if (appCompatDelegate != null) {
                                appCompatDelegate.applyDayNight();
                            }
                        }
                    } finally {
                    }
                }
            }
            ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.5
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent5) {
                    MediaOutputActivity mediaOutputActivity = MediaOutputActivity.this;
                    int i = MediaOutputActivity.$r8$clinit;
                    AnonymousClass5 anonymousClass5 = mediaOutputActivity.actionScreenReceiver;
                    if (anonymousClass5 != null) {
                        mediaOutputActivity.unregisterReceiver(anonymousClass5);
                        mediaOutputActivity.actionScreenReceiver = null;
                    }
                    MediaOutputActivity.this.finish();
                }
            };
            registerReceiver(r3, new IntentFilter("android.intent.action.SCREEN_OFF"));
            this.actionScreenReceiver = r3;
            Intent intent5 = getIntent();
            if (intent5 != null) {
                intent5.putExtra("isCover", true);
            }
            MoSaLogging moSaLogging2 = MoSaLogging.INSTANCE;
            SaEvent.LaunchMediaOutput launchMediaOutput2 = SaEvent.LaunchMediaOutput.INSTANCE;
            SaScreen.MediaOutput mediaOutput2 = SaScreen.MediaOutput.INSTANCE;
            SaCustom[] saCustomArr2 = {new SaCustom.From("Cover")};
            moSaLogging2.getClass();
            MoSaLogging.send(launchMediaOutput2, mediaOutput2, saCustomArr2);
        }
        if (z) {
            setShowWhenLocked(true);
        }
        getWindow().setDecorFitsSystemWindows(z);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-1487186387, true, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7
            /* JADX WARN: Removed duplicated region for block: B:20:0x0064  */
            /* JADX WARN: Removed duplicated region for block: B:23:0x0078  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x00ba  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                String stringExtra;
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous> (MediaOutputActivity.kt:117)");
                        }
                        ComposerImpl composerImpl2 = (ComposerImpl) composer;
                        Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                        final Density Density = DensityKt.Density(density.getDensity(), Math.min(density.getFontScale(), 1.3f));
                        Feature.Builder builder = new Feature.Builder();
                        final MediaOutputActivity mediaOutputActivity = MediaOutputActivity.this;
                        Intent intent6 = mediaOutputActivity.getIntent();
                        if (intent6 == null || (stringExtra = intent6.getStringExtra("android.intent.extra.PACKAGE_NAME")) == null) {
                            stringExtra = "";
                            builder.getFeature().packageName = stringExtra;
                            builder.getFeature().showMediaController = z;
                            builder.getFeature().from = !booleanExtra ? 40 : 20;
                            builder.getFeature().getClass();
                            builder.getFeature().isFullScreen = true;
                            builder.getFeature().defaultScreen = Screen.Phone.INSTANCE;
                            final Feature feature = builder.getFeature();
                            final boolean z2 = z;
                            BoxWithConstraintsKt.BoxWithConstraints(null, null, false, ComposableLambdaKt.rememberComposableLambda(-302200681, new Function3() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1
                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                @Override // kotlin.jvm.functions.Function3
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                    BoxWithConstraintsScope boxWithConstraintsScope = (BoxWithConstraintsScope) obj3;
                                    Composer composer2 = (Composer) obj4;
                                    int iIntValue = ((Number) obj5).intValue();
                                    if ((iIntValue & 6) == 0) {
                                        iIntValue |= ((ComposerImpl) composer2).changed(boxWithConstraintsScope) ? 4 : 2;
                                    }
                                    if ((iIntValue & 19) == 18) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous> (MediaOutputActivity.kt:128)");
                                            }
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release = CompositionExtKt.LocalRootSize.defaultProvidedValue$runtime_release(DpSize.m844boximpl(DpKt.m840DpSizeYgX7TsA(boxWithConstraintsScope.mo102getMaxWidthD9Ej5fM(), boxWithConstraintsScope.mo101getMaxHeightD9Ej5fM())));
                                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionExtKt.LocalViewModelProviderFactory;
                                            final MediaOutputActivity mediaOutputActivity2 = mediaOutputActivity;
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release2 = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(mediaOutputActivity2.factory);
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release3 = CompositionLocalsKt.LocalDensity.defaultProvidedValue$runtime_release(Density);
                                            StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = CompositionExtKt.LocalFeature;
                                            Feature feature2 = feature;
                                            ProvidedValue[] providedValueArr = {providedValueDefaultProvidedValue$runtime_release, providedValueDefaultProvidedValue$runtime_release2, providedValueDefaultProvidedValue$runtime_release3, staticProvidableCompositionLocal2.defaultProvidedValue$runtime_release(feature2), CompositionExtKt.LocalMediaOutputState.defaultProvidedValue$runtime_release(feature2), CompositionExtKt.LocalTransitionInfo.defaultProvidedValue$runtime_release(feature2), CompositionExtKt.LocalDismissCallback.defaultProvidedValue$runtime_release(feature2)};
                                            final boolean z3 = z2;
                                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(695626711, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1.1
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj6, Object obj7) {
                                                    Composer composer3 = (Composer) obj6;
                                                    if ((((Number) obj7).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous>.<anonymous> (MediaOutputActivity.kt:137)");
                                                            }
                                                            final boolean z4 = z3;
                                                            final MediaOutputActivity mediaOutputActivity3 = mediaOutputActivity2;
                                                            ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(-186312570, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1.1.1
                                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                                @Override // kotlin.jvm.functions.Function2
                                                                /*
                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                */
                                                                public final Object invoke(Object obj8, Object obj9) {
                                                                    Composer composer4 = (Composer) obj8;
                                                                    if ((((Number) obj9).intValue() & 3) == 2) {
                                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                                                        if (composerImpl5.getSkipping()) {
                                                                            composerImpl5.skipToGroupEnd();
                                                                        } else {
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputActivity.kt:138)");
                                                                            }
                                                                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                                                                            Color.Companion.getClass();
                                                                            long j = Color.Transparent;
                                                                            MaterialTheme.INSTANCE.getClass();
                                                                            long j2 = MaterialTheme.getColorScheme(composer4).onSurfaceVariant;
                                                                            final boolean z5 = z4;
                                                                            final MediaOutputActivity mediaOutputActivity4 = mediaOutputActivity3;
                                                                            SurfaceKt.m304SurfaceT9BRK9s(modifierFillMaxWidth, null, j, j2, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-969342623, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1.1.1.1
                                                                                /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
                                                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                /*
                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                */
                                                                                public final Object invoke(Object obj10, Object obj11) {
                                                                                    Composer composer5 = (Composer) obj10;
                                                                                    if ((((Number) obj11).intValue() & 3) == 2) {
                                                                                        ComposerImpl composerImpl6 = (ComposerImpl) composer5;
                                                                                        if (composerImpl6.getSkipping()) {
                                                                                            composerImpl6.skipToGroupEnd();
                                                                                        } else {
                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputActivity.kt:143)");
                                                                                            }
                                                                                            boolean z6 = z5;
                                                                                            Composer.Companion companion = Composer.Companion;
                                                                                            if (z6) {
                                                                                                ComposerImpl composerImpl7 = (ComposerImpl) composer5;
                                                                                                composerImpl7.startReplaceGroup(-2101182047);
                                                                                                composerImpl7.startReplaceGroup(-1037609655);
                                                                                                MediaOutputActivity mediaOutputActivity5 = mediaOutputActivity4;
                                                                                                boolean zChangedInstance = composerImpl7.changedInstance(mediaOutputActivity5);
                                                                                                Object objRememberedValue = composerImpl7.rememberedValue();
                                                                                                if (!zChangedInstance) {
                                                                                                    companion.getClass();
                                                                                                    if (objRememberedValue == Composer.Companion.Empty) {
                                                                                                        objRememberedValue = new MediaOutputActivity$$ExternalSyntheticLambda0(mediaOutputActivity5, 1);
                                                                                                        composerImpl7.updateRememberedValue(objRememberedValue);
                                                                                                    }
                                                                                                    composerImpl7.end(false);
                                                                                                    CoverScreenKt.CoverScreen((Function0) objRememberedValue, null, null, composerImpl7, 0);
                                                                                                    composerImpl7.end(false);
                                                                                                }
                                                                                            } else {
                                                                                                ComposerImpl composerImpl8 = (ComposerImpl) composer5;
                                                                                                composerImpl8.startReplaceGroup(-2101077081);
                                                                                                composerImpl8.startReplaceGroup(-1037606145);
                                                                                                Object objRememberedValue2 = composerImpl8.rememberedValue();
                                                                                                companion.getClass();
                                                                                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                                                                                    objRememberedValue2 = new MediaOutputActivity$onCreate$7$1$1$1$1$$ExternalSyntheticLambda1();
                                                                                                    composerImpl8.updateRememberedValue(objRememberedValue2);
                                                                                                }
                                                                                                composerImpl8.end(false);
                                                                                                PhoneScreenKt.PhoneScreen((Function1) objRememberedValue2, null, null, composerImpl8, 6);
                                                                                                composerImpl8.end(false);
                                                                                            }
                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                ComposerKt.traceEventEnd();
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }, composer4), composer4, 12583302, 114);
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventEnd();
                                                                            }
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }, composer3), composer3, 384, 3);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer2), composer2, 56);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, 3072, 7);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            if (stringExtra.length() <= 0) {
                                stringExtra = null;
                            }
                            if (stringExtra == null) {
                            }
                            builder.getFeature().packageName = stringExtra;
                            builder.getFeature().showMediaController = z;
                            builder.getFeature().from = !booleanExtra ? 40 : 20;
                            builder.getFeature().getClass();
                            builder.getFeature().isFullScreen = true;
                            builder.getFeature().defaultScreen = Screen.Phone.INSTANCE;
                            final Feature feature2 = builder.getFeature();
                            final boolean z22 = z;
                            BoxWithConstraintsKt.BoxWithConstraints(null, null, false, ComposableLambdaKt.rememberComposableLambda(-302200681, new Function3() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1
                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                @Override // kotlin.jvm.functions.Function3
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                    BoxWithConstraintsScope boxWithConstraintsScope = (BoxWithConstraintsScope) obj3;
                                    Composer composer2 = (Composer) obj4;
                                    int iIntValue = ((Number) obj5).intValue();
                                    if ((iIntValue & 6) == 0) {
                                        iIntValue |= ((ComposerImpl) composer2).changed(boxWithConstraintsScope) ? 4 : 2;
                                    }
                                    if ((iIntValue & 19) == 18) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous> (MediaOutputActivity.kt:128)");
                                            }
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release = CompositionExtKt.LocalRootSize.defaultProvidedValue$runtime_release(DpSize.m844boximpl(DpKt.m840DpSizeYgX7TsA(boxWithConstraintsScope.mo102getMaxWidthD9Ej5fM(), boxWithConstraintsScope.mo101getMaxHeightD9Ej5fM())));
                                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionExtKt.LocalViewModelProviderFactory;
                                            final MediaOutputActivity mediaOutputActivity2 = mediaOutputActivity;
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release2 = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(mediaOutputActivity2.factory);
                                            ProvidedValue providedValueDefaultProvidedValue$runtime_release3 = CompositionLocalsKt.LocalDensity.defaultProvidedValue$runtime_release(Density);
                                            StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = CompositionExtKt.LocalFeature;
                                            Feature feature22 = feature2;
                                            ProvidedValue[] providedValueArr = {providedValueDefaultProvidedValue$runtime_release, providedValueDefaultProvidedValue$runtime_release2, providedValueDefaultProvidedValue$runtime_release3, staticProvidableCompositionLocal2.defaultProvidedValue$runtime_release(feature22), CompositionExtKt.LocalMediaOutputState.defaultProvidedValue$runtime_release(feature22), CompositionExtKt.LocalTransitionInfo.defaultProvidedValue$runtime_release(feature22), CompositionExtKt.LocalDismissCallback.defaultProvidedValue$runtime_release(feature22)};
                                            final boolean z3 = z22;
                                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(695626711, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1.1
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj6, Object obj7) {
                                                    Composer composer3 = (Composer) obj6;
                                                    if ((((Number) obj7).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous>.<anonymous> (MediaOutputActivity.kt:137)");
                                                            }
                                                            final boolean z4 = z3;
                                                            final MediaOutputActivity mediaOutputActivity3 = mediaOutputActivity2;
                                                            ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(-186312570, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1.1.1
                                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                                @Override // kotlin.jvm.functions.Function2
                                                                /*
                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                */
                                                                public final Object invoke(Object obj8, Object obj9) {
                                                                    Composer composer4 = (Composer) obj8;
                                                                    if ((((Number) obj9).intValue() & 3) == 2) {
                                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                                                        if (composerImpl5.getSkipping()) {
                                                                            composerImpl5.skipToGroupEnd();
                                                                        } else {
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputActivity.kt:138)");
                                                                            }
                                                                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                                                                            Color.Companion.getClass();
                                                                            long j = Color.Transparent;
                                                                            MaterialTheme.INSTANCE.getClass();
                                                                            long j2 = MaterialTheme.getColorScheme(composer4).onSurfaceVariant;
                                                                            final boolean z5 = z4;
                                                                            final MediaOutputActivity mediaOutputActivity4 = mediaOutputActivity3;
                                                                            SurfaceKt.m304SurfaceT9BRK9s(modifierFillMaxWidth, null, j, j2, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-969342623, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.7.1.1.1.1
                                                                                /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
                                                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                /*
                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                */
                                                                                public final Object invoke(Object obj10, Object obj11) {
                                                                                    Composer composer5 = (Composer) obj10;
                                                                                    if ((((Number) obj11).intValue() & 3) == 2) {
                                                                                        ComposerImpl composerImpl6 = (ComposerImpl) composer5;
                                                                                        if (composerImpl6.getSkipping()) {
                                                                                            composerImpl6.skipToGroupEnd();
                                                                                        } else {
                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaOutputActivity.kt:143)");
                                                                                            }
                                                                                            boolean z6 = z5;
                                                                                            Composer.Companion companion = Composer.Companion;
                                                                                            if (z6) {
                                                                                                ComposerImpl composerImpl7 = (ComposerImpl) composer5;
                                                                                                composerImpl7.startReplaceGroup(-2101182047);
                                                                                                composerImpl7.startReplaceGroup(-1037609655);
                                                                                                MediaOutputActivity mediaOutputActivity5 = mediaOutputActivity4;
                                                                                                boolean zChangedInstance = composerImpl7.changedInstance(mediaOutputActivity5);
                                                                                                Object objRememberedValue = composerImpl7.rememberedValue();
                                                                                                if (!zChangedInstance) {
                                                                                                    companion.getClass();
                                                                                                    if (objRememberedValue == Composer.Companion.Empty) {
                                                                                                        objRememberedValue = new MediaOutputActivity$$ExternalSyntheticLambda0(mediaOutputActivity5, 1);
                                                                                                        composerImpl7.updateRememberedValue(objRememberedValue);
                                                                                                    }
                                                                                                    composerImpl7.end(false);
                                                                                                    CoverScreenKt.CoverScreen((Function0) objRememberedValue, null, null, composerImpl7, 0);
                                                                                                    composerImpl7.end(false);
                                                                                                }
                                                                                            } else {
                                                                                                ComposerImpl composerImpl8 = (ComposerImpl) composer5;
                                                                                                composerImpl8.startReplaceGroup(-2101077081);
                                                                                                composerImpl8.startReplaceGroup(-1037606145);
                                                                                                Object objRememberedValue2 = composerImpl8.rememberedValue();
                                                                                                companion.getClass();
                                                                                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                                                                                    objRememberedValue2 = new MediaOutputActivity$onCreate$7$1$1$1$1$$ExternalSyntheticLambda1();
                                                                                                    composerImpl8.updateRememberedValue(objRememberedValue2);
                                                                                                }
                                                                                                composerImpl8.end(false);
                                                                                                PhoneScreenKt.PhoneScreen((Function1) objRememberedValue2, null, null, composerImpl8, 6);
                                                                                                composerImpl8.end(false);
                                                                                            }
                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                ComposerKt.traceEventEnd();
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }, composer4), composer4, 12583302, 114);
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventEnd();
                                                                            }
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }, composer3), composer3, 384, 3);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer2), composer2, 56);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, 3072, 7);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }));
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("MediaOutputActivity", "onDestroy()");
        super.onDestroy();
        AnonymousClass5 anonymousClass5 = this.actionScreenReceiver;
        if (anonymousClass5 != null) {
            unregisterReceiver(anonymousClass5);
            this.actionScreenReceiver = null;
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getWindow().getDecorView().removeOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
    }
}
