package com.android.systemui.media.mediaoutput.compose.common;

import android.graphics.Rect;
import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

public final class Feature implements MediaOutputState, TransitionInfo, DismissCallback {
    public static final Companion Companion = new Companion(null);
    public final ParcelableSnapshotMutableState _state;
    public Function1 anchorRectCallback;
    public int anchorViewId;
    public long backgroundColor;
    public Screen defaultScreen;
    public List deviceIds;
    public Function0 dismissCallback;
    public int from;
    public Rect fromRect;
    public boolean isFullScreen;
    public String packageName;
    public boolean showMediaController;
    public final ParcelableSnapshotMutableState state;

    public final class Builder {
        public final Lazy feature$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.common.Feature$Builder$feature$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new Feature(null, 0L, false, 0, false, false, null, 127, null);
            }
        });

        public final Feature getFeature() {
            return (Feature) this.feature$delegate.getValue();
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    private Feature(String str, long j, boolean z, int i, boolean z2, boolean z3, Screen screen) {
        this.packageName = str;
        this.backgroundColor = j;
        this.showMediaController = z;
        this.from = i;
        this.isFullScreen = z3;
        this.defaultScreen = screen;
        ParcelableSnapshotMutableState mutableStateOf = SnapshotStateKt.mutableStateOf(MediaOutputState.StateInfo.PreShow, StructuralEqualityPolicy.INSTANCE);
        this._state = mutableStateOf;
        this.state = mutableStateOf;
        this.anchorViewId = -1;
    }

    public final void animateDismiss() {
        Function1 function1 = this.anchorRectCallback;
        Rect rect = function1 != null ? (Rect) function1.invoke(Integer.valueOf(this.anchorViewId)) : null;
        this.fromRect = rect;
        if (rect != null) {
            setState(MediaOutputState.StateInfo.Dismissing);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new Feature$animateDismiss$1(this, null), 3);
        } else {
            Function0 function0 = this.dismissCallback;
            if (function0 != null) {
                function0.invoke();
            }
            setState(MediaOutputState.StateInfo.Dismissed);
        }
    }

    public final void setState(MediaOutputState.StateInfo stateInfo) {
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i = iArr[stateInfo.ordinal()];
        ParcelableSnapshotMutableState parcelableSnapshotMutableState = this._state;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    int i2 = iArr[((MediaOutputState.StateInfo) parcelableSnapshotMutableState.getValue()).ordinal()];
                    if (i2 != 1 && i2 != 2) {
                        return;
                    }
                } else if (i != 4) {
                    if (i != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                } else if (this.anchorViewId <= 0) {
                    stateInfo = MediaOutputState.StateInfo.Dismissed;
                }
            } else if (this.anchorViewId <= 0 || parcelableSnapshotMutableState.getValue() != MediaOutputState.StateInfo.PreShow) {
                stateInfo = MediaOutputState.StateInfo.Shown;
            }
        }
        parcelableSnapshotMutableState.setValue(stateInfo);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Feature(java.lang.String r9, long r10, boolean r12, int r13, boolean r14, boolean r15, com.android.systemui.media.mediaoutput.compose.Screen r16, int r17, kotlin.jvm.internal.DefaultConstructorMarker r18) {
        /*
            r8 = this;
            r0 = r17 & 1
            if (r0 == 0) goto L7
            java.lang.String r0 = ""
            goto L8
        L7:
            r0 = r9
        L8:
            r1 = r17 & 2
            if (r1 == 0) goto L14
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.graphics.Color.Transparent
            goto L15
        L14:
            r1 = r10
        L15:
            r3 = r17 & 4
            r4 = 1
            if (r3 == 0) goto L1c
            r3 = r4
            goto L1d
        L1c:
            r3 = r12
        L1d:
            r5 = r17 & 8
            if (r5 == 0) goto L23
            r5 = -1
            goto L24
        L23:
            r5 = r13
        L24:
            r6 = r17 & 16
            if (r6 == 0) goto L29
            goto L2a
        L29:
            r4 = r14
        L2a:
            r6 = r17 & 32
            if (r6 == 0) goto L30
            r6 = 0
            goto L31
        L30:
            r6 = r15
        L31:
            r7 = r17 & 64
            if (r7 == 0) goto L38
            com.android.systemui.media.mediaoutput.compose.Screen$Phone r7 = com.android.systemui.media.mediaoutput.compose.Screen.Phone.INSTANCE
            goto L3a
        L38:
            r7 = r16
        L3a:
            r9 = r0
            r10 = r1
            r12 = r3
            r13 = r5
            r14 = r4
            r15 = r6
            r16 = r7
            r8.<init>(r9, r10, r12, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.common.Feature.<init>(java.lang.String, long, boolean, int, boolean, boolean, com.android.systemui.media.mediaoutput.compose.Screen, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
