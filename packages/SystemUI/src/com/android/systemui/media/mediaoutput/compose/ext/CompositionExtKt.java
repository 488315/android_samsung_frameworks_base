package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import kotlin.Result;
import kotlin.jvm.functions.Function0;

public abstract class CompositionExtKt {
    public static final StaticProvidableCompositionLocal LocalRootSize = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalRootSize$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No RootSize provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalMediaInteraction = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalMediaInteraction$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No MediaInteraction provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalAudioPathInteraction = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalAudioPathInteraction$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No AudioPathInteraction provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalSnackbarHostState = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalSnackbarHostState$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No SnackbarHostState provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalFeature = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalFeature$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No Feature provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalMediaOutputState = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalMediaOutputState$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No MediaOutputState provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalTransitionInfo = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalTransitionInfo$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No TransitionInfo provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalDismissCallback = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalDismissCallback$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No DismissCallback provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalViewModelProviderFactory = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalViewModelProviderFactory$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("No ViewModelProvider.Factory provided".toString());
        }
    });
    public static final StaticProvidableCompositionLocal LocalSecHapticFeedback = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalSecHapticFeedback$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            try {
                int i = Result.$r8$clinit;
                throw new IllegalStateException("No SecPlatformHapticFeedback provided".toString());
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
                if (m2527exceptionOrNullimpl == null) {
                    return null;
                }
                m2527exceptionOrNullimpl.printStackTrace();
                return null;
            }
        }
    });
    public static final StaticProvidableCompositionLocal LocalBackgroundColor = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalBackgroundColor$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            try {
                int i = Result.$r8$clinit;
                throw new IllegalStateException("No Color provided".toString());
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
                if (m2527exceptionOrNullimpl == null) {
                    return null;
                }
                m2527exceptionOrNullimpl.printStackTrace();
                return null;
            }
        }
    });
}
