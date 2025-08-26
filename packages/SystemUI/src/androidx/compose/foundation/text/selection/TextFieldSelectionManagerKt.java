package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.Handle;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.LongPressTextDragObserverKt;
import androidx.compose.foundation.text.TextDragObserver;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.foundation.text.selection.SelectionAdjustment;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphKt;
import androidx.compose.ui.text.Paragraph;
import androidx.compose.ui.text.ParagraphInfo;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.android.TextLayout;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import com.samsung.android.knox.EnterpriseContainerCallback;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class TextFieldSelectionManagerKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Handle.values().length];
            try {
                iArr[Handle.Cursor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Handle.SelectionStart.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Handle.SelectionEnd.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TextFieldSelectionHandle(final boolean z, final ResolvedTextDirection resolvedTextDirection, final TextFieldSelectionManager textFieldSelectionManager, Composer composer, final int i) {
        int i2;
        float lineBottom;
        boolean zChangedInstance;
        TextLayoutResultProxy layoutResult;
        TextLayoutResult textLayoutResult;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1344558920);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(resolvedTextDirection) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(textFieldSelectionManager) ? 256 : 128;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 147) != 146)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.TextFieldSelectionHandle (TextFieldSelectionManager.kt:1029)");
            }
            int i3 = i2 & 14;
            boolean zChanged = (i3 == 4) | composerImpl.changed(textFieldSelectionManager);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    textFieldSelectionManager.getClass();
                    objRememberedValue = new TextDragObserver() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$handleDragObserver$1
                        @Override // androidx.compose.foundation.text.TextDragObserver
                        /* renamed from: onDown-k-4lQ0M */
                        public final void mo202onDownk4lQ0M() {
                            TextLayoutResultProxy layoutResult2;
                            boolean z2 = z;
                            Handle handle = z2 ? Handle.SelectionStart : Handle.SelectionEnd;
                            TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.draggingHandle$delegate).setValue(handle);
                            long jM235getAdjustedCoordinatesk4lQ0M = SelectionHandlesKt.m235getAdjustedCoordinatesk4lQ0M(textFieldSelectionManager2.m241getHandlePositiontuRUvjQ$foundation_release(z2));
                            LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager2.state;
                            if (legacyTextFieldState == null || (layoutResult2 = legacyTextFieldState.getLayoutResult()) == null) {
                                return;
                            }
                            long jM212translateInnerToDecorationCoordinatesMKHz9U$foundation_release = layoutResult2.m212translateInnerToDecorationCoordinatesMKHz9U$foundation_release(jM235getAdjustedCoordinatesk4lQ0M);
                            textFieldSelectionManager2.dragBeginPosition = jM212translateInnerToDecorationCoordinatesMKHz9U$foundation_release;
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(Offset.m395boximpl(jM212translateInnerToDecorationCoordinatesMKHz9U$foundation_release));
                            Offset.Companion.getClass();
                            textFieldSelectionManager2.dragTotalDistance = 0L;
                            textFieldSelectionManager2.previousRawDragOffset = -1;
                            LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager2.state;
                            if (legacyTextFieldState2 != null) {
                                ((SnapshotMutableStateImpl) legacyTextFieldState2.isInTouchMode$delegate).setValue(Boolean.TRUE);
                            }
                            textFieldSelectionManager2.updateFloatingToolbar(false);
                        }

                        @Override // androidx.compose.foundation.text.TextDragObserver
                        /* renamed from: onDrag-k-4lQ0M */
                        public final void mo203onDragk4lQ0M(long j) {
                            TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                            long jM403plusMKHz9U = Offset.m403plusMKHz9U(textFieldSelectionManager2.dragTotalDistance, j);
                            textFieldSelectionManager2.dragTotalDistance = jM403plusMKHz9U;
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(Offset.m395boximpl(Offset.m403plusMKHz9U(textFieldSelectionManager2.dragBeginPosition, jM403plusMKHz9U)));
                            TextFieldValue value$foundation_release = textFieldSelectionManager2.getValue$foundation_release();
                            Offset offsetM240getCurrentDragPosition_m7T9E = textFieldSelectionManager2.m240getCurrentDragPosition_m7T9E();
                            offsetM240getCurrentDragPosition_m7T9E.getClass();
                            SelectionAdjustment.Companion.getClass();
                            SelectionAdjustment$Companion$$ExternalSyntheticLambda0 selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.CharacterWithWordAccelerate;
                            TextFieldSelectionManager.m237access$updateSelection8UEBfa8(textFieldSelectionManager2, value$foundation_release, offsetM240getCurrentDragPosition_m7T9E.packedValue, false, z, selectionAdjustment$Companion$$ExternalSyntheticLambda0, true);
                            textFieldSelectionManager2.updateFloatingToolbar(false);
                        }

                        @Override // androidx.compose.foundation.text.TextDragObserver
                        public final void onStop() {
                            TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.draggingHandle$delegate).setValue(null);
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(null);
                            textFieldSelectionManager2.updateFloatingToolbar(true);
                        }

                        @Override // androidx.compose.foundation.text.TextDragObserver
                        public final void onUp() {
                            TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.draggingHandle$delegate).setValue(null);
                            ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(null);
                            textFieldSelectionManager2.updateFloatingToolbar(true);
                        }

                        @Override // androidx.compose.foundation.text.TextDragObserver
                        public final void onCancel() {
                        }

                        @Override // androidx.compose.foundation.text.TextDragObserver
                        /* renamed from: onStart-k-4lQ0M */
                        public final void mo204onStartk4lQ0M(long j) {
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                final TextDragObserver textDragObserver = (TextDragObserver) objRememberedValue;
                boolean zChangedInstance2 = (i3 == 4) | composerImpl.changedInstance(textFieldSelectionManager);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new OffsetProvider() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt$TextFieldSelectionHandle$1$1
                            @Override // androidx.compose.foundation.text.selection.OffsetProvider
                            /* renamed from: provide-F1C5BW0 */
                            public final long mo198provideF1C5BW0() {
                                return textFieldSelectionManager.m241getHandlePositiontuRUvjQ$foundation_release(z);
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    OffsetProvider offsetProvider = (OffsetProvider) objRememberedValue2;
                    boolean zM753getReversedimpl = TextRange.m753getReversedimpl(textFieldSelectionManager.getValue$foundation_release().selection);
                    int i4 = (int) (z ? textFieldSelectionManager.getValue$foundation_release().selection >> 32 : textFieldSelectionManager.getValue$foundation_release().selection & 4294967295L);
                    LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                    if (legacyTextFieldState == null || (layoutResult = legacyTextFieldState.getLayoutResult()) == null || (textLayoutResult = layoutResult.value) == null || i4 < 0 || textLayoutResult.layoutInput.text.text.length() == 0) {
                        lineBottom = 0.0f;
                        Modifier.Companion companion2 = Modifier.Companion;
                        zChangedInstance = composerImpl.changedInstance(textDragObserver);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (zChangedInstance) {
                            companion.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt$TextFieldSelectionHandle$2$1
                                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                        Object objDetectDownAndDragGesturesWithObserver = LongPressTextDragObserverKt.detectDownAndDragGesturesWithObserver(pointerInputScope, textDragObserver, continuation);
                                        return objDetectDownAndDragGesturesWithObserver == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectDownAndDragGesturesWithObserver : Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue3);
                            }
                            AndroidSelectionHandles_androidKt.m233SelectionHandlewLIcFTc(offsetProvider, z, resolvedTextDirection, zM753getReversedimpl, 0L, lineBottom, SuspendingPointerInputFilterKt.pointerInput(companion2, textDragObserver, (PointerInputEventHandler) objRememberedValue3), composerImpl, (i2 << 3) & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS, 16);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    } else {
                        MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
                        int iMin = Math.min(multiParagraph.getLineForOffset(i4), Math.min(multiParagraph.maxLines - 1, multiParagraph.lineCount - 1));
                        if (i4 <= multiParagraph.getLineEnd(iMin, false)) {
                            multiParagraph.requireLineIndexInRange(iMin);
                            ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) multiParagraph.paragraphInfoList).get(MultiParagraphKt.findParagraphByLineIndex(iMin, multiParagraph.paragraphInfoList));
                            Paragraph paragraph = paragraphInfo.paragraph;
                            int i5 = iMin - paragraphInfo.startLineIndex;
                            TextLayout textLayout = ((AndroidParagraph) paragraph).layout;
                            lineBottom = textLayout.getLineBottom(i5) - textLayout.getLineTop(i5);
                        }
                        Modifier.Companion companion22 = Modifier.Companion;
                        zChangedInstance = composerImpl.changedInstance(textDragObserver);
                        Object objRememberedValue32 = composerImpl.rememberedValue();
                        if (zChangedInstance) {
                        }
                    }
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt.TextFieldSelectionHandle.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldSelectionManagerKt.TextFieldSelectionHandle(z, resolvedTextDirection, textFieldSelectionManager, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final boolean isSelectionHandleInVisibleBound(TextFieldSelectionManager textFieldSelectionManager, boolean z) {
        LayoutCoordinates layoutCoordinates;
        LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
        if (legacyTextFieldState == null || (layoutCoordinates = legacyTextFieldState.getLayoutCoordinates()) == null) {
            return false;
        }
        Rect rectVisibleBounds = SelectionManagerKt.visibleBounds(layoutCoordinates);
        long jM241getHandlePositiontuRUvjQ$foundation_release = textFieldSelectionManager.m241getHandlePositiontuRUvjQ$foundation_release(z);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jM241getHandlePositiontuRUvjQ$foundation_release >> 32));
        if (rectVisibleBounds.left > fIntBitsToFloat || fIntBitsToFloat > rectVisibleBounds.right) {
            return false;
        }
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM241getHandlePositiontuRUvjQ$foundation_release & 4294967295L));
        return rectVisibleBounds.top <= fIntBitsToFloat2 && fIntBitsToFloat2 <= rectVisibleBounds.bottom;
    }
}
