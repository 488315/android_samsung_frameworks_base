package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import kotlin.Result;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public abstract class CompositionExtKt {
    public static final StaticProvidableCompositionLocal LocalAudioPathInteraction;
    public static final StaticProvidableCompositionLocal LocalBackgroundColor = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$LocalBackgroundColor$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            try {
                int i = Result.$r8$clinit;
                throw new IllegalStateException("No Color provided");
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                if (thM3442exceptionOrNullimpl == null) {
                    return null;
                }
                thM3442exceptionOrNullimpl.printStackTrace();
                return null;
            }
        }
    });
    public static final StaticProvidableCompositionLocal LocalDensityScale;
    public static final StaticProvidableCompositionLocal LocalDismissCallback;
    public static final StaticProvidableCompositionLocal LocalFeature;
    public static final StaticProvidableCompositionLocal LocalLargeScreenHeaderHeight;
    public static final StaticProvidableCompositionLocal LocalMediaInteraction;
    public static final StaticProvidableCompositionLocal LocalMediaOutputState;
    public static final StaticProvidableCompositionLocal LocalRootSize;
    public static final StaticProvidableCompositionLocal LocalSecHapticFeedback;
    public static final StaticProvidableCompositionLocal LocalSnackbarHostState;
    public static final StaticProvidableCompositionLocal LocalTransitionInfo;
    public static final StaticProvidableCompositionLocal LocalViewModelProviderFactory;

    static {
        final int i = 0;
        LocalRootSize = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i2 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i3 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i4 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i5 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i6 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i7 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i2 = 5;
        LocalMediaInteraction = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i3 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i4 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i5 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i6 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i7 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i3 = 6;
        LocalAudioPathInteraction = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i4 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i5 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i6 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i7 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i4 = 7;
        LocalSnackbarHostState = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i5 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i6 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i7 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i5 = 8;
        LocalFeature = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i6 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i7 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i6 = 9;
        LocalMediaOutputState = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i7 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i7 = 10;
        LocalTransitionInfo = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i72 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i8 = 11;
        LocalDismissCallback = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i8) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i72 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i9 = 1;
        LocalViewModelProviderFactory = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i9) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i72 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i10 = 2;
        LocalSecHapticFeedback = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i10) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i72 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i11 = 3;
        LocalDensityScale = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i11) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i72 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
        final int i12 = 4;
        LocalLargeScreenHeaderHeight = new StaticProvidableCompositionLocal(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i12) {
                    case 0:
                        throw new IllegalStateException("No RootSize provided");
                    case 1:
                        throw new IllegalStateException("No ViewModelProvider.Factory provided");
                    case 2:
                        try {
                            int i22 = Result.$r8$clinit;
                            throw new IllegalStateException("No SecPlatformHapticFeedback provided");
                        } catch (Throwable th) {
                            int i32 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th));
                            if (thM3442exceptionOrNullimpl != null) {
                                thM3442exceptionOrNullimpl.printStackTrace();
                            }
                            return null;
                        }
                    case 3:
                        try {
                            int i42 = Result.$r8$clinit;
                            throw new IllegalStateException("No DensityScale provided");
                        } catch (Throwable th2) {
                            int i52 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th2));
                            if (thM3442exceptionOrNullimpl2 != null) {
                                thM3442exceptionOrNullimpl2.printStackTrace();
                            }
                            return Float.valueOf(1.0f);
                        }
                    case 4:
                        try {
                            int i62 = Result.$r8$clinit;
                            throw new IllegalStateException("No HeaderHeight provided");
                        } catch (Throwable th3) {
                            int i72 = Result.$r8$clinit;
                            Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th3));
                            if (thM3442exceptionOrNullimpl3 != null) {
                                thM3442exceptionOrNullimpl3.printStackTrace();
                            }
                            return null;
                        }
                    case 5:
                        throw new IllegalStateException("No MediaInteraction provided");
                    case 6:
                        throw new IllegalStateException("No AudioPathInteraction provided");
                    case 7:
                        throw new IllegalStateException("No SnackbarHostState provided");
                    case 8:
                        throw new IllegalStateException("No Feature provided");
                    case 9:
                        throw new IllegalStateException("No MediaOutputState provided");
                    case 10:
                        throw new IllegalStateException("No TransitionInfo provided");
                    default:
                        throw new IllegalStateException("No DismissCallback provided");
                }
            }
        });
    }
}
