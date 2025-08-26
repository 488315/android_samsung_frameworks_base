package com.android.systemui.media.mediaoutput.compose.common;

import android.graphics.Rect;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.media.MediaOutputView$$ExternalSyntheticLambda2;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class Feature implements MediaOutputState, TransitionInfo, DismissCallback {
    public static final Companion Companion = new Companion(null);
    public final MutableState _state;
    public MediaOutputView$$ExternalSyntheticLambda2 anchorRectCallback;
    public int anchorViewId;
    public String anchorViewTag;
    public Screen defaultScreen;
    public List deviceIds;
    public Function0 dismissCallback;
    public int from;
    public Rect fromRect;
    public boolean isFullScreen;
    public boolean isRotated;
    public boolean isWindow;
    public String packageName;
    public boolean showMediaController;
    public final State state;

    public final class Builder {
        public final Lazy feature$delegate = LazyKt__LazyJVMKt.lazy(new Feature$Builder$$ExternalSyntheticLambda0());

        public final Feature getFeature() {
            return (Feature) this.feature$delegate.getValue();
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MediaOutputState.StateInfo.values().length];
            try {
                iArr[MediaOutputState.StateInfo.PreShow.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MediaOutputState.StateInfo.Showing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[MediaOutputState.StateInfo.Shown.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[MediaOutputState.StateInfo.Dismissing.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[MediaOutputState.StateInfo.Dismissed.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.compose.common.Feature$animateDismiss$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Feature.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(50L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Feature feature = Feature.this;
            Function0 function0 = feature.dismissCallback;
            if (function0 != null) {
                function0.invoke();
            }
            feature.setState(MediaOutputState.StateInfo.Dismissed);
            return Unit.INSTANCE;
        }
    }

    private Feature(String str, boolean z, int i, boolean z2, boolean z3, Screen screen, boolean z4) {
        this.packageName = str;
        this.showMediaController = z;
        this.from = i;
        this.isFullScreen = z3;
        this.defaultScreen = screen;
        this.isWindow = z4;
        MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(MediaOutputState.StateInfo.PreShow);
        this._state = mutableStateMutableStateOf$default;
        this.state = mutableStateMutableStateOf$default;
        this.anchorViewId = -1;
    }

    public final void animateDismiss() {
        MediaOutputView$$ExternalSyntheticLambda2 mediaOutputView$$ExternalSyntheticLambda2 = this.anchorRectCallback;
        Rect rect = mediaOutputView$$ExternalSyntheticLambda2 != null ? (Rect) mediaOutputView$$ExternalSyntheticLambda2.invoke(Integer.valueOf(this.anchorViewId), this.anchorViewTag) : null;
        this.fromRect = rect;
        if (rect != null) {
            setState(MediaOutputState.StateInfo.Dismissing);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new AnonymousClass1(null), 3);
        } else {
            Function0 function0 = this.dismissCallback;
            if (function0 != null) {
                function0.invoke();
            }
            setState(MediaOutputState.StateInfo.Dismissed);
        }
    }

    public final void setAnchorRectCallback(MediaOutputView$$ExternalSyntheticLambda2 mediaOutputView$$ExternalSyntheticLambda2) {
        if (Intrinsics.areEqual(this.anchorRectCallback, mediaOutputView$$ExternalSyntheticLambda2)) {
            return;
        }
        this.anchorRectCallback = mediaOutputView$$ExternalSyntheticLambda2;
        this.fromRect = mediaOutputView$$ExternalSyntheticLambda2 != null ? (Rect) mediaOutputView$$ExternalSyntheticLambda2.invoke(Integer.valueOf(this.anchorViewId), this.anchorViewTag) : null;
    }

    public final void setState(MediaOutputState.StateInfo stateInfo) {
        MutableState mutableState = this._state;
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i = iArr[stateInfo.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    int i2 = iArr[((MediaOutputState.StateInfo) ((SnapshotMutableStateImpl) mutableState).getValue()).ordinal()];
                    if (i2 != 1 && i2 != 2) {
                        return;
                    }
                } else if (i != 4) {
                    if (i != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                } else if (this.fromRect == null) {
                    stateInfo = MediaOutputState.StateInfo.Dismissed;
                }
            } else if (this.fromRect == null || ((SnapshotMutableStateImpl) mutableState).getValue() != MediaOutputState.StateInfo.PreShow) {
                stateInfo = MediaOutputState.StateInfo.Shown;
            }
        }
        ((SnapshotMutableStateImpl) mutableState).setValue(stateInfo);
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        */
    public /* synthetic */ Feature(java.lang.String r2, boolean r3, int r4, boolean r5, boolean r6, com.android.systemui.media.mediaoutput.compose.Screen r7, boolean r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r1 = this;
            r10 = r9 & 1
            if (r10 == 0) goto L6
            java.lang.String r2 = ""
        L6:
            r10 = r9 & 2
            r0 = 1
            if (r10 == 0) goto Lc
            r3 = r0
        Lc:
            r10 = r9 & 4
            if (r10 == 0) goto L11
            r4 = -1
        L11:
            r10 = r9 & 8
            if (r10 == 0) goto L16
            r5 = r0
        L16:
            r10 = r9 & 16
            r0 = 0
            if (r10 == 0) goto L1c
            r6 = r0
        L1c:
            r10 = r9 & 32
            if (r10 == 0) goto L22
            com.android.systemui.media.mediaoutput.compose.Screen$Phone r7 = com.android.systemui.media.mediaoutput.compose.Screen.Phone.INSTANCE
        L22:
            r9 = r9 & 64
            if (r9 == 0) goto L2f
            r10 = r0
            r8 = r6
            r9 = r7
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
            goto L37
        L2f:
            r10 = r8
            r9 = r7
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
        L37:
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.common.Feature.<init>(java.lang.String, boolean, int, boolean, boolean, com.android.systemui.media.mediaoutput.compose.Screen, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
