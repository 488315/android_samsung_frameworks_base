package com.android.systemui.media.mediaoutput.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.foundation.layout.BoxWithConstraintsKt;
import androidx.compose.foundation.layout.BoxWithConstraintsScopeImpl;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityImpl;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$volumeKeyHandler$1;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaOutputActivity extends ComponentActivity {
    public final AudioManager audioManager;
    public final ViewModelFactory factory;
    public final Lazy onUnhandledKeyEventListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity$onUnhandledKeyEventListener$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new AudioManagerExtKt$volumeKeyHandler$1(MediaOutputActivity.this.audioManager);
        }
    });

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

    public MediaOutputActivity(ViewModelFactory viewModelFactory, AudioManager audioManager) {
        this.factory = viewModelFactory;
        this.audioManager = audioManager;
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

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Log.d("MediaOutputActivity", "onCreate()");
        super.onCreate(bundle);
        if (!getIntent().getBooleanExtra("force", false) && getDisplay().getDisplayId() != 2) {
            finish();
        } else {
            getWindow().setDecorFitsSystemWindows(true);
            ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-1487186387, true, new Function2() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity$onCreate$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    long Color;
                    Composer composer = (Composer) obj;
                    if ((((Number) obj2).intValue() & 11) == 2) {
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.getSkipping()) {
                            composerImpl.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey = ComposerKt.invocation;
                    ComposerImpl composerImpl2 = (ComposerImpl) composer;
                    Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                    final DensityImpl densityImpl = new DensityImpl(density.getDensity(), Math.min(density.getFontScale(), 1.3f));
                    Feature.Builder builder = new Feature.Builder();
                    Color.Companion.getClass();
                    Color = ColorKt.Color(Color.m391getRedimpl(r1), Color.m390getGreenimpl(r1), Color.m388getBlueimpl(r1), 0.5f, Color.m389getColorSpaceimpl(Color.Black));
                    int m397toArgb8_81llA = ColorKt.m397toArgb8_81llA(Color);
                    builder.getFeature().backgroundColor = ColorKt.Color(m397toArgb8_81llA);
                    builder.getFeature().showMediaController = true;
                    builder.getFeature().getClass();
                    builder.getFeature().isFullScreen = true;
                    builder.getFeature().defaultScreen = Screen.Phone.INSTANCE;
                    final Feature feature = builder.getFeature();
                    final MediaOutputActivity mediaOutputActivity = MediaOutputActivity.this;
                    BoxWithConstraintsKt.BoxWithConstraints(null, null, false, ComposableLambdaKt.rememberComposableLambda(-302200681, composerImpl2, new Function3() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity$onCreate$1.1
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                            float f;
                            float f2;
                            BoxWithConstraintsScopeImpl boxWithConstraintsScopeImpl = (BoxWithConstraintsScopeImpl) obj3;
                            Composer composer2 = (Composer) obj4;
                            int intValue = ((Number) obj5).intValue();
                            if ((intValue & 14) == 0) {
                                intValue |= ((ComposerImpl) composer2).changed(boxWithConstraintsScopeImpl) ? 4 : 2;
                            }
                            if ((intValue & 91) == 18) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionExtKt.LocalRootSize;
                            long j = boxWithConstraintsScopeImpl.constraints;
                            boolean m720getHasBoundedWidthimpl = Constraints.m720getHasBoundedWidthimpl(j);
                            Density density2 = boxWithConstraintsScopeImpl.density;
                            if (m720getHasBoundedWidthimpl) {
                                f = density2.mo58toDpu2uoSUM(Constraints.m724getMaxWidthimpl(j));
                            } else {
                                Dp.Companion.getClass();
                                f = Dp.Infinity;
                            }
                            long j2 = boxWithConstraintsScopeImpl.constraints;
                            if (Constraints.m719getHasBoundedHeightimpl(j2)) {
                                f2 = density2.mo58toDpu2uoSUM(Constraints.m723getMaxHeightimpl(j2));
                            } else {
                                Dp.Companion.getClass();
                                f2 = Dp.Infinity;
                            }
                            ProvidedValue[] providedValueArr = {staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DpSize.m746boximpl(DpKt.m742DpSizeYgX7TsA(f, f2))), CompositionExtKt.LocalViewModelProviderFactory.defaultProvidedValue$runtime_release(MediaOutputActivity.this.factory), CompositionLocalsKt.LocalDensity.defaultProvidedValue$runtime_release(densityImpl), CompositionExtKt.LocalFeature.defaultProvidedValue$runtime_release(feature), CompositionExtKt.LocalMediaOutputState.defaultProvidedValue$runtime_release(feature), CompositionExtKt.LocalTransitionInfo.defaultProvidedValue$runtime_release(feature), CompositionExtKt.LocalDismissCallback.defaultProvidedValue$runtime_release(feature)};
                            ComposableSingletons$MediaOutputActivityKt.INSTANCE.getClass();
                            CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableSingletons$MediaOutputActivityKt.f48lambda3, composer2, 56);
                            return Unit.INSTANCE;
                        }
                    }), composerImpl2, 3072, 7);
                    return Unit.INSTANCE;
                }
            }));
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getWindow().getDecorView().removeOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
    }
}
