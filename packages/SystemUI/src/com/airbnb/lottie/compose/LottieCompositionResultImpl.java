package com.airbnb.lottie.compose;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.airbnb.lottie.LottieComposition;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;

/* loaded from: classes.dex */
public final class LottieCompositionResultImpl implements State {
    public final State isComplete$delegate;
    public final State isSuccess$delegate;
    public final CompletableDeferredImpl compositionDeferred = CompletableDeferredKt.CompletableDeferred$default();
    public final MutableState value$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState error$delegate = SnapshotStateKt.mutableStateOf$default(null);

    public LottieCompositionResultImpl() {
        SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.airbnb.lottie.compose.LottieCompositionResultImpl$isLoading$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((LottieComposition) ((SnapshotMutableStateImpl) this.this$0.value$delegate).getValue()) == null && ((Throwable) ((SnapshotMutableStateImpl) this.this$0.error$delegate).getValue()) == null);
            }
        });
        this.isComplete$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.airbnb.lottie.compose.LottieCompositionResultImpl$isComplete$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf((((LottieComposition) ((SnapshotMutableStateImpl) this.this$0.value$delegate).getValue()) == null && ((Throwable) ((SnapshotMutableStateImpl) this.this$0.error$delegate).getValue()) == null) ? false : true);
            }
        });
        SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.airbnb.lottie.compose.LottieCompositionResultImpl$isFailure$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((Throwable) ((SnapshotMutableStateImpl) this.this$0.error$delegate).getValue()) != null);
            }
        });
        this.isSuccess$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.airbnb.lottie.compose.LottieCompositionResultImpl$isSuccess$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((LottieComposition) ((SnapshotMutableStateImpl) this.this$0.value$delegate).getValue()) != null);
            }
        });
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return (LottieComposition) ((SnapshotMutableStateImpl) this.value$delegate).getValue();
    }
}
