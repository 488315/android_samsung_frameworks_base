package com.android.compose.animation.scene;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.Velocity;
import com.android.compose.animation.scene.effect.GestureEffect;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.ui.util.SpaceVectorConverter;
import com.android.compose.ui.util.SpaceVectorConverterKt;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DraggableHandler implements NestedDraggable {
    public DragControllerImpl dragController;
    public final Function1 gestureEffectProvider;
    public final SceneTransitionLayoutImpl layoutImpl;
    public final Orientation orientation;
    public final DelegatingOverscrollEffect overscrollEffect = new DelegatingOverscrollEffect();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DelegatingOverscrollEffect implements OverscrollEffect, SpaceVectorConverter {
        public final /* synthetic */ SpaceVectorConverter $$delegate_0;
        public ContentKey currentContent;
        public GestureEffect currentDelegate;

        public DelegatingOverscrollEffect() {
            this.$$delegate_0 = SpaceVectorConverterKt.SpaceVectorConverter(DraggableHandler.this.orientation);
        }

        @Override // androidx.compose.foundation.OverscrollEffect
        /* renamed from: applyToFling-BMRW4eQ */
        public final Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl) {
            DragControllerImpl dragControllerImpl;
            float mo914toFloatTH1AsA0$1 = this.$$delegate_0.mo914toFloatTH1AsA0$1(j);
            if (mo914toFloatTH1AsA0$1 != 0.0f && (dragControllerImpl = DraggableHandler.this.dragController) != null && dragControllerImpl.isDrivingTransition()) {
                ensureDelegateIsNotNull(mo914toFloatTH1AsA0$1);
            }
            GestureEffect gestureEffect = this.currentDelegate;
            setCurrentDelegate(null);
            this.currentContent = null;
            if (gestureEffect == null || !((Modifier.Node) gestureEffect.delegate.getNode()).node.isAttached) {
                Object invoke = function2.invoke(Velocity.m876boximpl(j), continuationImpl);
                return invoke == CoroutineSingletons.COROUTINE_SUSPENDED ? invoke : Unit.INSTANCE;
            }
            Object mo19applyToFlingBMRW4eQ = gestureEffect.mo19applyToFlingBMRW4eQ(j, function2, continuationImpl);
            return mo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? mo19applyToFlingBMRW4eQ : Unit.INSTANCE;
        }

        @Override // androidx.compose.foundation.OverscrollEffect
        /* renamed from: applyToScroll-Rhakbz0 */
        public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
            float mo915toFloatk4lQ0M$1 = this.$$delegate_0.mo915toFloatk4lQ0M$1(j);
            if (mo915toFloatk4lQ0M$1 == 0.0f) {
                return ((Offset) function1.mo779invoke(Offset.m393boximpl(j))).packedValue;
            }
            ensureDelegateIsNotNull(mo915toFloatk4lQ0M$1);
            GestureEffect gestureEffect = this.currentDelegate;
            if (gestureEffect != null) {
                return ((Modifier.Node) gestureEffect.delegate.getNode()).node.isAttached ? gestureEffect.mo20applyToScrollRhakbz0(i, j, function1) : ((Offset) function1.mo779invoke(Offset.m393boximpl(j))).packedValue;
            }
            throw new IllegalStateException("Required value was null.");
        }

        public final void ensureDelegateIsNotNull(float f) {
            ContentKey key;
            if (f == 0.0f) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            if (isInProgress()) {
                return;
            }
            DraggableHandler draggableHandler = DraggableHandler.this;
            DragControllerImpl dragControllerImpl = draggableHandler.dragController;
            if (dragControllerImpl == null || !dragControllerImpl.isDrivingTransition()) {
                key = draggableHandler.layoutImpl.contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().getKey();
            } else {
                DragControllerImpl dragControllerImpl2 = draggableHandler.dragController;
                if (dragControllerImpl2 == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                SwipeAnimation swipeAnimation = dragControllerImpl2.swipeAnimation;
                swipeAnimation.getClass();
                if (f == 0.0f) {
                    throw new IllegalArgumentException(("Cannot find a content in this direction: " + f).toString());
                }
                boolean z = swipeAnimation.isUpOrLeft;
                key = ((!z || f >= 0.0f) && (z || f <= 0.0f)) ? swipeAnimation.fromContent : swipeAnimation.toContent;
            }
            if (Intrinsics.areEqual(key, this.currentContent)) {
                return;
            }
            this.currentContent = key;
            setCurrentDelegate((GestureEffect) draggableHandler.gestureEffectProvider.mo779invoke(key));
        }

        @Override // androidx.compose.foundation.OverscrollEffect
        public final boolean isInProgress() {
            GestureEffect gestureEffect = this.currentDelegate;
            if (gestureEffect != null) {
                return gestureEffect.delegate.isInProgress();
            }
            return false;
        }

        public final void setCurrentDelegate(GestureEffect gestureEffect) {
            GestureEffect gestureEffect2 = this.currentDelegate;
            if (gestureEffect2 != null && gestureEffect2.delegate.isInProgress()) {
                BuildersKt.launch$default(DraggableHandler.this.layoutImpl.animationScope, null, null, new DraggableHandler$DelegatingOverscrollEffect$currentDelegate$1$1(gestureEffect2, null), 3);
            }
            this.currentDelegate = gestureEffect;
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toFloat-TH1AsA0$1 */
        public final float mo914toFloatTH1AsA0$1(long j) {
            return this.$$delegate_0.mo914toFloatTH1AsA0$1(j);
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toFloat-k-4lQ0M$1 */
        public final float mo915toFloatk4lQ0M$1(long j) {
            return this.$$delegate_0.mo915toFloatk4lQ0M$1(j);
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toIntOffset-Bjo55l4 */
        public final long mo916toIntOffsetBjo55l4(int i) {
            return this.$$delegate_0.mo916toIntOffsetBjo55l4(i);
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toOffset-tuRUvjQ$1 */
        public final long mo917toOffsettuRUvjQ$1(float f) {
            return this.$$delegate_0.mo917toOffsettuRUvjQ$1(f);
        }
    }

    public DraggableHandler(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Orientation orientation, Function1 function1) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.orientation = orientation;
        this.gestureEffectProvider = function1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x005b, code lost:
    
        r3 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0057, code lost:
    
        if (r14 == null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0050, code lost:
    
        if (r11 == null) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0059, code lost:
    
        r3 = r11;
     */
    /* renamed from: onDragStarted-w4f02Oo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.compose.gesture.NestedDraggable.Controller m918onDragStartedw4f02Oo(long r11, float r13, int r14, androidx.compose.ui.input.pointer.PointerType r15) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.DraggableHandler.m918onDragStartedw4f02Oo(long, float, int, androidx.compose.ui.input.pointer.PointerType):com.android.compose.gesture.NestedDraggable$Controller");
    }
}
