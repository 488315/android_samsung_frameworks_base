package androidx.compose.foundation.text;

import android.view.InputDevice;
import android.view.KeyEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.PressGestureScopeImpl;
import androidx.compose.foundation.gestures.ScrollableKt;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.gestures.ScrollableStateKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.relocation.BringIntoViewRequester;
import androidx.compose.foundation.relocation.BringIntoViewRequesterKt;
import androidx.compose.foundation.text.Handle;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.TextFieldDelegate;
import androidx.compose.foundation.text.TextFieldScrollKt;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.foundation.text.handwriting.StylusHandwritingKt;
import androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter;
import androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifier;
import androidx.compose.foundation.text.input.internal.CursorAnimationState;
import androidx.compose.foundation.text.input.internal.LegacyAdaptingPlatformTextInputModifierNodeKt;
import androidx.compose.foundation.text.input.internal.LegacyPlatformTextInputServiceAdapter;
import androidx.compose.foundation.text.input.internal.LegacyPlatformTextInputServiceAdapter_androidKt;
import androidx.compose.foundation.text.selection.OffsetProvider;
import androidx.compose.foundation.text.selection.SelectionGesturesKt;
import androidx.compose.foundation.text.selection.SelectionHandleAnchor;
import androidx.compose.foundation.text.selection.SelectionHandleInfo;
import androidx.compose.foundation.text.selection.SelectionHandlesKt;
import androidx.compose.foundation.text.selection.SelectionManagerKt;
import androidx.compose.foundation.text.selection.SimpleLayoutKt;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt;
import androidx.compose.foundation.text.selection.TextPreparedSelectionState;
import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.foundation.text.selection.TextSelectionColorsKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.focus.FocusChangedModifierKt;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.focus.FocusState;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.FilterQuality;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.pointer.PointerHoverIconModifierElement;
import androidx.compose.ui.input.pointer.PointerIcon;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.AlignmentLineKt;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.Clipboard;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.DelegatingSoftwareKeyboardController;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.platform.SoftwareKeyboardController;
import androidx.compose.ui.platform.TextToolbar;
import androidx.compose.ui.platform.WindowInfo;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextPainter;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontFamilyResolverImpl;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.EditProcessor;
import androidx.compose.ui.text.input.EditingBuffer;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.PlatformTextInputService;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputService;
import androidx.compose.ui.text.input.TextInputSession;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: classes.dex */
public abstract class CoreTextFieldKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x062a  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0635  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0656  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x065f  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0678 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x06c8  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x06da  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x06e9  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x06fa  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x070a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x077e  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0780  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x078d  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x078f  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x07a3  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x07a5  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x07b4  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x07c4 A[PHI: r33
      0x07c4: PHI (r33v6 androidx.compose.ui.text.input.ImeOptions) = (r33v5 androidx.compose.ui.text.input.ImeOptions), (r33v15 androidx.compose.ui.text.input.ImeOptions) binds: [B:393:0x07b2, B:396:0x07bc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x07cc A[PHI: r33 r68
      0x07cc: PHI (r33v12 androidx.compose.ui.text.input.ImeOptions) = (r33v6 androidx.compose.ui.text.input.ImeOptions), (r33v14 androidx.compose.ui.text.input.ImeOptions) binds: [B:399:0x07ca, B:397:0x07bf] A[DONT_GENERATE, DONT_INLINE]
      0x07cc: PHI (r68v13 androidx.compose.foundation.text.LegacyTextFieldState) = 
      (r68v6 androidx.compose.foundation.text.LegacyTextFieldState)
      (r68v14 androidx.compose.foundation.text.LegacyTextFieldState)
     binds: [B:399:0x07ca, B:397:0x07bf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:401:0x07ce  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x07eb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0801  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x084c  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x087d  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0889  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0892 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:429:0x089a  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x08c6  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x08de  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x08e0  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x08e7  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x08e9  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0904  */
    /* JADX WARN: Removed duplicated region for block: B:450:0x0926  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0934  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x0950  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0952  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0965  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x097d  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x097f  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x0995  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0997  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x09ae  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0a46  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0a5b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:492:0x0a5d  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x0a76  */
    /* JADX WARN: Removed duplicated region for block: B:496:0x0a78  */
    /* JADX WARN: Removed duplicated region for block: B:507:0x0a92 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:508:0x0a94  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:511:0x0aa8  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0aaa  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x0ade  */
    /* JADX WARN: Removed duplicated region for block: B:518:0x0ae4  */
    /* JADX WARN: Removed duplicated region for block: B:523:0x0b09  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0b36  */
    /* JADX WARN: Removed duplicated region for block: B:537:0x0bae  */
    /* JADX WARN: Removed duplicated region for block: B:539:0x0bb1  */
    /* JADX WARN: Removed duplicated region for block: B:542:0x0bfb  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0c1d  */
    /* JADX WARN: Removed duplicated region for block: B:547:0x0c3f  */
    /* JADX WARN: Removed duplicated region for block: B:549:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0139  */
    /* JADX WARN: Type inference failed for: r0v13, types: [androidx.compose.ui.Modifier, androidx.compose.ui.Modifier$Companion] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v18, types: [androidx.compose.ui.Modifier] */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r74v0, types: [int] */
    /* JADX WARN: Type inference failed for: r75v0, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CoreTextField(final TextFieldValue textFieldValue, final Function1 function1, Modifier modifier, TextStyle textStyle, VisualTransformation visualTransformation, Function1 function12, MutableInteractionSource mutableInteractionSource, Brush brush, boolean z, int i, int i2, ImeOptions imeOptions, KeyboardActions keyboardActions, boolean z2, boolean z3, Function3 function3, TextFieldScrollerPosition textFieldScrollerPosition, Composer composer, final int i3, final int i4, final int i5) {
        int i6;
        boolean z4;
        int i7;
        TextStyle textStyle2;
        int i8;
        VisualTransformation visualTransformation2;
        int i9;
        Function1 function13;
        int i10;
        MutableInteractionSource mutableInteractionSource2;
        int i11;
        Brush solidColor;
        int i12;
        boolean z5;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        boolean z6;
        int i23;
        int i24;
        int i25;
        final Modifier modifier2;
        final ImeOptions imeOptions2;
        final Function3 function32;
        final TextFieldScrollerPosition textFieldScrollerPosition2;
        ComposerImpl composerImpl;
        final TextStyle textStyle3;
        final VisualTransformation visualTransformation3;
        final Brush brush2;
        final boolean z7;
        final MutableInteractionSource mutableInteractionSource3;
        final int i26;
        final KeyboardActions keyboardActions2;
        final boolean z8;
        final boolean z9;
        final Function1 function14;
        final int i27;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        TextStyle textStyle4;
        Modifier modifier3;
        TextStyle textStyle5;
        ImeOptions imeOptions3;
        KeyboardActions keyboardActions3;
        boolean z10;
        Function3 function33;
        boolean z11;
        MutableInteractionSource mutableInteractionSource4;
        Function3 function34;
        Brush brush3;
        TextStyle textStyle6;
        TextFieldScrollerPosition textFieldScrollerPosition3;
        boolean z12;
        int i28;
        int i29;
        final Function1 function15;
        VisualTransformation visualTransformation4;
        ImeOptions imeOptions4;
        boolean z13;
        Modifier modifier4;
        TextFieldScrollerPosition textFieldScrollerPosition4;
        ComposerImpl composerImpl2;
        int i30;
        int i31;
        TextInputService textInputService;
        FocusManager focusManager;
        TransformedText transformedText;
        Object obj;
        TextStyle textStyle7;
        boolean z14;
        FocusRequester focusRequester;
        TransformedText transformedText2;
        VisualTransformation visualTransformation5;
        TextInputService textInputService2;
        AnnotatedString annotatedString;
        Density density;
        FontFamily.Resolver resolver;
        Object obj2;
        int i32;
        boolean z15;
        boolean zAreEqual;
        boolean z16;
        boolean z17;
        TextRange textRange;
        TextStyle textStyle8;
        long j;
        TextFieldValue textFieldValueM780copy3r_uNRQ$default;
        TextFieldValue textFieldValue2;
        Object objRememberedValue;
        Object obj3;
        final UndoManager undoManager;
        Object objRememberedValue2;
        Object objRememberedValue3;
        Object objRememberedValue4;
        int i33;
        final LegacyTextFieldState legacyTextFieldState;
        boolean zChangedInstance;
        Object objRememberedValue5;
        final LegacyTextFieldState legacyTextFieldState2;
        boolean z18;
        final boolean z19;
        final TextInputService textInputService3;
        ImeOptions imeOptions5;
        BringIntoViewRequester bringIntoViewRequester;
        FocusManager focusManager2;
        VisualTransformation visualTransformation6;
        final OffsetMapping offsetMapping;
        TextFieldSelectionManager textFieldSelectionManager;
        TextFieldSelectionManager textFieldSelectionManager2;
        boolean z20;
        boolean z21;
        Object objRememberedValue6;
        MutableState mutableState;
        TextFieldSelectionManager textFieldSelectionManager3;
        boolean zChangedInstance2;
        Object obj4;
        boolean zChangedInstance3;
        Object objRememberedValue7;
        FocusRequester focusRequester2;
        boolean z22;
        TextFieldSelectionManager textFieldSelectionManager4;
        final boolean z23;
        boolean zChangedInstance4;
        Object obj5;
        final TextFieldSelectionManager textFieldSelectionManager5;
        boolean zChangedInstance5;
        Object objRememberedValue8;
        boolean z24;
        WindowInfo windowInfo;
        TextFieldSelectionManager textFieldSelectionManager6;
        boolean z25;
        CoreTextFieldSemanticsModifier coreTextFieldSemanticsModifier;
        final OffsetMapping offsetMapping2;
        OffsetMapping offsetMapping3;
        CoreTextFieldSemanticsModifier coreTextFieldSemanticsModifier2;
        boolean z26;
        final Brush brush4;
        Modifier modifierComposed;
        boolean zChangedInstance6;
        Object obj6;
        boolean zChangedInstance7;
        Object obj7;
        final boolean z27;
        boolean zChanged;
        Object obj8;
        final long j2;
        boolean zChangedInstance8;
        Object obj9;
        boolean z28;
        int i34 = 16;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-244533042);
        if ((i5 & 1) != 0) {
            i6 = i3 | 6;
        } else if ((i3 & 6) == 0) {
            i6 = i3 | (composerImpl3.changed(textFieldValue) ? 4 : 2);
        } else {
            i6 = i3;
        }
        if ((i5 & 2) != 0) {
            i6 |= 48;
        } else if ((i3 & 48) == 0) {
            i6 |= composerImpl3.changedInstance(function1) ? 32 : 16;
        }
        int i35 = i6;
        int i36 = i5 & 4;
        if (i36 != 0) {
            i35 |= 384;
            z4 = true;
        } else {
            z4 = true;
            if ((i3 & 384) == 0) {
                i35 |= composerImpl3.changed(modifier) ? 256 : 128;
            }
            i7 = i5 & 8;
            if (i7 == 0) {
                i35 |= 3072;
            } else {
                if ((i3 & 3072) == 0) {
                    textStyle2 = textStyle;
                    i35 |= composerImpl3.changed(textStyle2) ? 2048 : 1024;
                }
                i8 = i5 & 16;
                if (i8 != 0) {
                    i35 |= 24576;
                } else {
                    if ((i3 & 24576) == 0) {
                        visualTransformation2 = visualTransformation;
                        i35 |= composerImpl3.changed(visualTransformation2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i9 = i5 & 32;
                    if (i9 == 0) {
                        i35 |= 196608;
                        function13 = function12;
                    } else {
                        function13 = function12;
                        if ((i3 & 196608) == 0) {
                            i35 |= composerImpl3.changedInstance(function13) ? 131072 : 65536;
                        }
                    }
                    i10 = i5 & 64;
                    int i37 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    if (i10 == 0) {
                        i35 |= 1572864;
                        mutableInteractionSource2 = mutableInteractionSource;
                    } else {
                        mutableInteractionSource2 = mutableInteractionSource;
                        if ((i3 & 1572864) == 0) {
                            i35 |= composerImpl3.changed(mutableInteractionSource2) ? 1048576 : 524288;
                        }
                    }
                    i11 = i5 & 128;
                    if (i11 == 0) {
                        i35 |= 12582912;
                        solidColor = brush;
                    } else {
                        solidColor = brush;
                        if ((i3 & 12582912) == 0) {
                            i35 |= composerImpl3.changed(solidColor) ? 8388608 : 4194304;
                        }
                    }
                    i12 = i5 & 256;
                    if (i12 == 0) {
                        i35 |= 100663296;
                        z5 = z;
                    } else {
                        z5 = z;
                        if ((i3 & 100663296) == 0) {
                            i35 |= composerImpl3.changed(z5) ? 67108864 : 33554432;
                        }
                    }
                    i13 = i5 & 512;
                    if (i13 == 0) {
                        i35 |= 805306368;
                        i14 = i13;
                    } else if ((i3 & 805306368) == 0) {
                        i14 = i13;
                        i35 |= composerImpl3.changed((int) i) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    } else {
                        i14 = i13;
                    }
                    i15 = i5 & 1024;
                    if (i15 == 0) {
                        i16 = i4 | 6;
                    } else if ((i4 & 6) == 0) {
                        i16 = i4 | (composerImpl3.changed((int) i2) ? 4 : 2);
                    } else {
                        i16 = i4;
                    }
                    if ((i4 & 48) != 0) {
                        i17 = i15;
                        if ((i5 & 2048) == 0 && composerImpl3.changed(imeOptions)) {
                            i34 = 32;
                        }
                        i16 |= i34;
                    } else {
                        i17 = i15;
                    }
                    int i38 = i16;
                    i18 = i5 & 4096;
                    if (i18 == 0) {
                        i19 = i38 | 384;
                        i20 = i11;
                    } else {
                        int i39 = i38;
                        if ((i4 & 384) == 0) {
                            i39 |= composerImpl3.changed(keyboardActions) ? 256 : 128;
                        }
                        i19 = i39;
                        i20 = i11;
                    }
                    i21 = i5 & 8192;
                    if (i21 == 0) {
                        i19 |= 3072;
                    } else if ((i4 & 3072) == 0) {
                        i19 |= composerImpl3.changed(z2) ? 2048 : 1024;
                    }
                    i22 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    if (i22 != 0) {
                        if ((i4 & 24576) == 0) {
                            z6 = z3;
                            i19 |= composerImpl3.changed(z6) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                        if (i23 != 0) {
                            i24 = i19 | 196608;
                        } else if ((i4 & 196608) == 0) {
                            i24 = i19 | (composerImpl3.changedInstance(function3) ? 131072 : 65536);
                        } else {
                            i24 = i19;
                        }
                        i25 = i5 & 65536;
                        if (i25 != 0) {
                            i24 |= 1572864;
                        } else if ((i4 & 1572864) == 0) {
                            if (composerImpl3.changed(textFieldScrollerPosition)) {
                                i37 = 1048576;
                            }
                            i24 |= i37;
                        }
                        if (composerImpl3.shouldExecute(i35 & 1, ((i35 & 306783379) == 306783378 && (i24 & 599187) == 599186) ? false : z4)) {
                            composerImpl3.startDefaults();
                            if ((i3 & 1) == 0 || composerImpl3.getDefaultsInvalid()) {
                                Modifier modifier5 = i36 != 0 ? Modifier.Companion : modifier;
                                if (i7 != 0) {
                                    TextStyle.Companion.getClass();
                                    textStyle4 = TextStyle.Default;
                                } else {
                                    textStyle4 = textStyle2;
                                }
                                if (i8 != 0) {
                                    VisualTransformation.Companion.getClass();
                                    visualTransformation2 = VisualTransformation.Companion.None;
                                }
                                Function1 function16 = i9 != 0 ? new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj10) {
                                        return Unit.INSTANCE;
                                    }
                                } : function13;
                                if (i10 != 0) {
                                    mutableInteractionSource2 = null;
                                }
                                if (i20 != 0) {
                                    Color.Companion.getClass();
                                    modifier3 = modifier5;
                                    textStyle5 = textStyle4;
                                    solidColor = new SolidColor(Color.Unspecified, null);
                                } else {
                                    modifier3 = modifier5;
                                    textStyle5 = textStyle4;
                                }
                                if (i12 != 0) {
                                    z5 = z4;
                                }
                                ?? r2 = i14 != 0 ? 2147483647 : i;
                                boolean z29 = i17 != 0 ? z4 : i2;
                                if ((i5 & 2048) != 0) {
                                    ImeOptions.Companion.getClass();
                                    imeOptions3 = ImeOptions.Default;
                                    i24 &= -113;
                                } else {
                                    imeOptions3 = imeOptions;
                                }
                                if (i18 != 0) {
                                    KeyboardActions.Companion.getClass();
                                    keyboardActions3 = KeyboardActions.Default;
                                } else {
                                    keyboardActions3 = keyboardActions;
                                }
                                z10 = i21 != 0 ? z4 : z2;
                                if (i22 != 0) {
                                    z6 = false;
                                }
                                if (i23 != 0) {
                                    ComposableSingletons$CoreTextFieldKt.INSTANCE.getClass();
                                    function33 = ComposableSingletons$CoreTextFieldKt.f2lambda1;
                                } else {
                                    function33 = function3;
                                }
                                Brush brush5 = solidColor;
                                z11 = z6;
                                mutableInteractionSource4 = mutableInteractionSource2;
                                function34 = function33;
                                brush3 = brush5;
                                textStyle6 = textStyle5;
                                if (i25 != 0) {
                                    z12 = z29;
                                    i28 = 57344;
                                    i29 = i24;
                                    textFieldScrollerPosition3 = null;
                                } else {
                                    textFieldScrollerPosition3 = textFieldScrollerPosition;
                                    z12 = z29;
                                    i28 = 57344;
                                    i29 = i24;
                                }
                                function15 = function16;
                                visualTransformation4 = visualTransformation2;
                                imeOptions4 = imeOptions3;
                                z13 = r2;
                                modifier4 = modifier3;
                            } else {
                                composerImpl3.skipToGroupEnd();
                                if ((i5 & 2048) != 0) {
                                    i24 &= -113;
                                }
                                modifier4 = modifier;
                                z12 = i2;
                                keyboardActions3 = keyboardActions;
                                z10 = z2;
                                textFieldScrollerPosition3 = textFieldScrollerPosition;
                                brush3 = solidColor;
                                i28 = 57344;
                                i29 = i24;
                                z11 = z6;
                                function15 = function13;
                                mutableInteractionSource4 = mutableInteractionSource2;
                                z13 = i;
                                function34 = function3;
                                textStyle6 = textStyle2;
                                visualTransformation4 = visualTransformation2;
                                imeOptions4 = imeOptions;
                            }
                            composerImpl3.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField (CoreTextField.kt:205)");
                            }
                            boolean z30 = z12;
                            Object objRememberedValue9 = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            int i40 = i35;
                            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                            Object objM = objRememberedValue9;
                            if (objRememberedValue9 == composer$Companion$Empty$1) {
                                objM = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl3);
                            }
                            FocusRequester focusRequester3 = (FocusRequester) objM;
                            final boolean z31 = z11;
                            Object objRememberedValue10 = composerImpl3.rememberedValue();
                            Object obj10 = objRememberedValue10;
                            if (objRememberedValue10 == composer$Companion$Empty$1) {
                                Function1 function17 = LegacyPlatformTextInputServiceAdapter_androidKt.inputMethodManagerFactory;
                                AndroidLegacyPlatformTextInputServiceAdapter androidLegacyPlatformTextInputServiceAdapter = new AndroidLegacyPlatformTextInputServiceAdapter();
                                composerImpl3.updateRememberedValue(androidLegacyPlatformTextInputServiceAdapter);
                                obj10 = androidLegacyPlatformTextInputServiceAdapter;
                            }
                            final LegacyPlatformTextInputServiceAdapter legacyPlatformTextInputServiceAdapter = (LegacyPlatformTextInputServiceAdapter) obj10;
                            boolean z32 = z5;
                            Object objRememberedValue11 = composerImpl3.rememberedValue();
                            Object obj11 = objRememberedValue11;
                            if (objRememberedValue11 == composer$Companion$Empty$1) {
                                TextInputService textInputService4 = new TextInputService(legacyPlatformTextInputServiceAdapter);
                                composerImpl3.updateRememberedValue(textInputService4);
                                obj11 = textInputService4;
                            }
                            TextInputService textInputService5 = (TextInputService) obj11;
                            final Function3 function35 = function34;
                            Density density2 = (Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity);
                            FontFamily.Resolver resolver2 = (FontFamily.Resolver) composerImpl3.consume(CompositionLocalsKt.LocalFontFamilyResolver);
                            final MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource4;
                            Brush brush6 = brush3;
                            long j3 = ((TextSelectionColors) composerImpl3.consume(TextSelectionColorsKt.LocalTextSelectionColors)).backgroundColor;
                            FocusManager focusManager3 = (FocusManager) composerImpl3.consume(CompositionLocalsKt.LocalFocusManager);
                            Modifier modifier6 = modifier4;
                            final WindowInfo windowInfo2 = (WindowInfo) composerImpl3.consume(CompositionLocalsKt.LocalWindowInfo);
                            SoftwareKeyboardController softwareKeyboardController = (SoftwareKeyboardController) composerImpl3.consume(CompositionLocalsKt.LocalSoftwareKeyboardController);
                            final Orientation orientation = (z13 == z4 && !z32 && imeOptions4.singleLine) ? Orientation.Horizontal : Orientation.Vertical;
                            boolean z33 = z13;
                            if (textFieldScrollerPosition3 == null) {
                                composerImpl3.startReplaceGroup(-1705351660);
                                Object[] objArr = {orientation};
                                TextFieldScrollerPosition.Companion.getClass();
                                SaverKt$Saver$1 saverKt$Saver$1 = TextFieldScrollerPosition.Saver;
                                boolean zChanged2 = composerImpl3.changed(orientation);
                                Object objRememberedValue12 = composerImpl3.rememberedValue();
                                Object obj12 = objRememberedValue12;
                                if (zChanged2 || objRememberedValue12 == composer$Companion$Empty$1) {
                                    Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$scrollerPosition$1$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return new TextFieldScrollerPosition(orientation, 0.0f, 2, null);
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(function0);
                                    obj12 = function0;
                                }
                                Object objRememberSaveable = RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$1, null, (Function0) obj12, composerImpl3, 0, 4);
                                ComposerImpl composerImpl4 = composerImpl3;
                                composerImpl4.end(false);
                                textFieldScrollerPosition4 = (TextFieldScrollerPosition) objRememberSaveable;
                                composerImpl2 = composerImpl4;
                            } else {
                                ComposerImpl composerImpl5 = composerImpl3;
                                composerImpl5.startReplaceGroup(-1705352776);
                                composerImpl5.end(false);
                                textFieldScrollerPosition4 = textFieldScrollerPosition3;
                                composerImpl2 = composerImpl5;
                            }
                            ImeOptions imeOptions6 = imeOptions4;
                            if (((Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition4.orientation$delegate).getValue()) != orientation) {
                                throw new IllegalArgumentException("Mismatching scroller orientation; ".concat(orientation == Orientation.Vertical ? "only single-line, non-wrap text fields can scroll horizontally" : "single-line, non-wrap text fields can only scroll horizontally"));
                            }
                            int i41 = i40 & 14;
                            int i42 = i40 & i28;
                            final TextFieldScrollerPosition textFieldScrollerPosition5 = textFieldScrollerPosition4;
                            boolean z34 = (i41 == 4) | (i42 == 16384);
                            Object objRememberedValue13 = composerImpl2.rememberedValue();
                            if (z34 || objRememberedValue13 == composer$Companion$Empty$1) {
                                TransformedText transformedTextFilterWithValidation = ValidatingOffsetMappingKt.filterWithValidation(visualTransformation4, textFieldValue.annotatedString);
                                TextRange textRange2 = textFieldValue.composition;
                                if (textRange2 != null) {
                                    TextFieldDelegate.Companion.getClass();
                                    TextRange.Companion companion = TextRange.Companion;
                                    i30 = i41;
                                    i31 = i29;
                                    long j4 = textRange2.packedValue;
                                    OffsetMapping offsetMapping4 = transformedTextFilterWithValidation.offsetMapping;
                                    int iOriginalToTransformed = offsetMapping4.originalToTransformed((int) (j4 >> 32));
                                    textInputService = textInputService5;
                                    focusManager = focusManager3;
                                    int iOriginalToTransformed2 = offsetMapping4.originalToTransformed((int) (j4 & 4294967295L));
                                    int iMin = Math.min(iOriginalToTransformed, iOriginalToTransformed2);
                                    int iMax = Math.max(iOriginalToTransformed, iOriginalToTransformed2);
                                    AnnotatedString.Builder builder = new AnnotatedString.Builder(transformedTextFilterWithValidation.text);
                                    TextDecoration.Companion.getClass();
                                    builder.addStyle(new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, TextDecoration.Underline, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, 61439, (DefaultConstructorMarker) null), iMin, iMax);
                                    transformedText = new TransformedText(builder.toAnnotatedString(), offsetMapping4);
                                } else {
                                    i30 = i41;
                                    i31 = i29;
                                    textInputService = textInputService5;
                                    focusManager = focusManager3;
                                    transformedText = transformedTextFilterWithValidation;
                                }
                                composerImpl2.updateRememberedValue(transformedText);
                                obj = transformedText;
                            } else {
                                i30 = i41;
                                i31 = i29;
                                obj = objRememberedValue13;
                                textInputService = textInputService5;
                                focusManager = focusManager3;
                            }
                            TransformedText transformedText3 = (TransformedText) obj;
                            AnnotatedString annotatedString2 = transformedText3.text;
                            RecomposeScopeImpl currentRecomposeScope = ComposablesKt.getCurrentRecomposeScope(composerImpl2);
                            boolean zChanged3 = composerImpl2.changed(softwareKeyboardController);
                            Object objRememberedValue14 = composerImpl2.rememberedValue();
                            if (zChanged3 || objRememberedValue14 == composer$Companion$Empty$1) {
                                TextStyle textStyle9 = textStyle6;
                                TextDelegate textDelegate = new TextDelegate(annotatedString2, textStyle9, 0, 0, z32, 0, density2, resolver2, null, 300, null);
                                textStyle7 = textStyle9;
                                z14 = z32;
                                focusRequester = focusRequester3;
                                transformedText2 = transformedText3;
                                visualTransformation5 = visualTransformation4;
                                textInputService2 = textInputService;
                                annotatedString = annotatedString2;
                                density = density2;
                                resolver = resolver2;
                                LegacyTextFieldState legacyTextFieldState3 = new LegacyTextFieldState(textDelegate, currentRecomposeScope, softwareKeyboardController);
                                composerImpl2.updateRememberedValue(legacyTextFieldState3);
                                obj2 = legacyTextFieldState3;
                            } else {
                                z14 = z32;
                                focusRequester = focusRequester3;
                                transformedText2 = transformedText3;
                                visualTransformation5 = visualTransformation4;
                                textInputService2 = textInputService;
                                density = density2;
                                resolver = resolver2;
                                annotatedString = annotatedString2;
                                textStyle7 = textStyle6;
                                obj2 = objRememberedValue14;
                            }
                            LegacyTextFieldState legacyTextFieldState4 = (LegacyTextFieldState) obj2;
                            AnnotatedString annotatedString3 = textFieldValue.annotatedString;
                            legacyTextFieldState4.onValueChangeOriginal = function1;
                            legacyTextFieldState4.selectionBackgroundColor = j3;
                            KeyboardActionRunner keyboardActionRunner = legacyTextFieldState4.keyboardActionRunner;
                            keyboardActionRunner.keyboardActions = keyboardActions3;
                            FocusManager focusManager4 = focusManager;
                            keyboardActionRunner.focusManager = focusManager4;
                            legacyTextFieldState4.untransformedText = annotatedString3;
                            TextDelegate textDelegate2 = legacyTextFieldState4.textDelegate;
                            EmptyList emptyList = EmptyList.INSTANCE;
                            TextOverflow.Companion.getClass();
                            int i43 = TextOverflow.Clip;
                            if (Intrinsics.areEqual(textDelegate2.text, annotatedString) && Intrinsics.areEqual(textDelegate2.style, textStyle7) && textDelegate2.softWrap == z14 && textDelegate2.overflow == i43 && textDelegate2.maxLines == Integer.MAX_VALUE) {
                                i32 = 1;
                                if (textDelegate2.minLines == 1 && Intrinsics.areEqual(textDelegate2.density, density) && Intrinsics.areEqual(textDelegate2.placeholders, emptyList) && textDelegate2.fontFamilyResolver == resolver) {
                                    z15 = z14;
                                }
                                if (legacyTextFieldState4.textDelegate != textDelegate2) {
                                    legacyTextFieldState4.isLayoutResultStale = true;
                                }
                                legacyTextFieldState4.textDelegate = textDelegate2;
                                TextInputSession textInputSession = legacyTextFieldState4.inputSession;
                                EditProcessor editProcessor = legacyTextFieldState4.processor;
                                editProcessor.getClass();
                                boolean zAreEqual2 = Intrinsics.areEqual(textFieldValue.composition, editProcessor.mBuffer.m772getCompositionMzsxiRA$ui_text_release());
                                String str = editProcessor.mBufferState.annotatedString.text;
                                AnnotatedString annotatedString4 = textFieldValue.annotatedString;
                                zAreEqual = Intrinsics.areEqual(str, annotatedString4.text);
                                long j5 = textFieldValue.selection;
                                if (zAreEqual) {
                                    editProcessor.mBuffer = new EditingBuffer(annotatedString4, j5, (DefaultConstructorMarker) null);
                                    z16 = true;
                                } else if (TextRange.m748equalsimpl0(editProcessor.mBufferState.selection, j5)) {
                                    z16 = false;
                                } else {
                                    editProcessor.mBuffer.setSelection$ui_text_release(TextRange.m752getMinimpl(j5), TextRange.m751getMaximpl(j5));
                                    z16 = false;
                                    z17 = true;
                                    textRange = textFieldValue.composition;
                                    if (textRange == null) {
                                        EditingBuffer editingBuffer = editProcessor.mBuffer;
                                        editingBuffer.compositionStart = -1;
                                        editingBuffer.compositionEnd = -1;
                                    } else {
                                        long j6 = textRange.packedValue;
                                        if (!TextRange.m749getCollapsedimpl(j6)) {
                                            textStyle8 = textStyle7;
                                            editProcessor.mBuffer.setComposition$ui_text_release(TextRange.m752getMinimpl(j6), TextRange.m751getMaximpl(j6));
                                        }
                                        if (z16 && (z17 || zAreEqual2)) {
                                            textFieldValueM780copy3r_uNRQ$default = textFieldValue;
                                            j = 0;
                                        } else {
                                            EditingBuffer editingBuffer2 = editProcessor.mBuffer;
                                            editingBuffer2.compositionStart = -1;
                                            editingBuffer2.compositionEnd = -1;
                                            j = 0;
                                            textFieldValueM780copy3r_uNRQ$default = TextFieldValue.m780copy3r_uNRQ$default(textFieldValue, null, 0L, 3);
                                        }
                                        textFieldValue2 = editProcessor.mBufferState;
                                        editProcessor.mBufferState = textFieldValueM780copy3r_uNRQ$default;
                                        if (textInputSession != null && Intrinsics.areEqual((TextInputSession) textInputSession.textInputService._currentInputSession.get(), textInputSession)) {
                                            textInputSession.platformTextInputService.updateState(textFieldValue2, textFieldValueM780copy3r_uNRQ$default);
                                        }
                                        objRememberedValue = composerImpl2.rememberedValue();
                                        if (objRememberedValue != composer$Companion$Empty$1) {
                                            UndoManager undoManager2 = new UndoManager(0, 1, null);
                                            composerImpl2.updateRememberedValue(undoManager2);
                                            obj3 = undoManager2;
                                        } else {
                                            obj3 = objRememberedValue;
                                        }
                                        undoManager = (UndoManager) obj3;
                                        long jCurrentTimeMillis = System.currentTimeMillis();
                                        if (undoManager.forceNextSnapshot) {
                                            undoManager.lastSnapshot = Long.valueOf(jCurrentTimeMillis);
                                            undoManager.makeSnapshot(textFieldValue);
                                            objRememberedValue2 = composerImpl2.rememberedValue();
                                            Object obj13 = objRememberedValue2;
                                            if (objRememberedValue2 == composer$Companion$Empty$1) {
                                                CoroutineScope coroutineScopeCreateCompositionCoroutineScope = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                                                composerImpl2.updateRememberedValue(coroutineScopeCreateCompositionCoroutineScope);
                                                obj13 = coroutineScopeCreateCompositionCoroutineScope;
                                            }
                                            final CoroutineScope coroutineScope = (CoroutineScope) obj13;
                                            objRememberedValue3 = composerImpl2.rememberedValue();
                                            Object obj14 = objRememberedValue3;
                                            if (objRememberedValue3 == composer$Companion$Empty$1) {
                                                BringIntoViewRequester BringIntoViewRequester = BringIntoViewRequesterKt.BringIntoViewRequester();
                                                composerImpl2.updateRememberedValue(BringIntoViewRequester);
                                                obj14 = BringIntoViewRequester;
                                            }
                                            final BringIntoViewRequester bringIntoViewRequester2 = (BringIntoViewRequester) obj14;
                                            objRememberedValue4 = composerImpl2.rememberedValue();
                                            Object obj15 = objRememberedValue4;
                                            if (objRememberedValue4 == composer$Companion$Empty$1) {
                                                TextFieldSelectionManager textFieldSelectionManager7 = new TextFieldSelectionManager(undoManager);
                                                composerImpl2.updateRememberedValue(textFieldSelectionManager7);
                                                obj15 = textFieldSelectionManager7;
                                            }
                                            final TextFieldSelectionManager textFieldSelectionManager8 = (TextFieldSelectionManager) obj15;
                                            TransformedText transformedText4 = transformedText2;
                                            final OffsetMapping offsetMapping5 = transformedText4.offsetMapping;
                                            textFieldSelectionManager8.offsetMapping = offsetMapping5;
                                            VisualTransformation visualTransformation7 = visualTransformation5;
                                            textFieldSelectionManager8.visualTransformation = visualTransformation7;
                                            textFieldSelectionManager8.onValueChange = (Lambda) legacyTextFieldState4.onValueChange;
                                            textFieldSelectionManager8.state = legacyTextFieldState4;
                                            ((SnapshotMutableStateImpl) textFieldSelectionManager8.value$delegate).setValue(textFieldValue);
                                            textFieldSelectionManager8.clipboard = (Clipboard) composerImpl2.consume(CompositionLocalsKt.LocalClipboard);
                                            textFieldSelectionManager8.coroutineScope = coroutineScope;
                                            textFieldSelectionManager8.textToolbar = (TextToolbar) composerImpl2.consume(CompositionLocalsKt.LocalTextToolbar);
                                            textFieldSelectionManager8.hapticFeedBack = (HapticFeedback) composerImpl2.consume(CompositionLocalsKt.LocalHapticFeedback);
                                            final FocusRequester focusRequester4 = focusRequester;
                                            textFieldSelectionManager8.focusRequester = focusRequester4;
                                            final boolean z35 = !z31;
                                            ((SnapshotMutableStateImpl) textFieldSelectionManager8.editable$delegate).setValue(Boolean.valueOf(z35));
                                            ((SnapshotMutableStateImpl) textFieldSelectionManager8.enabled$delegate).setValue(Boolean.valueOf(z10));
                                            ?? TextFieldMagnifier = Modifier.Companion;
                                            int i44 = i31 & 7168;
                                            int i45 = i31 & i28;
                                            final Density density3 = density;
                                            int i46 = i30;
                                            boolean zChangedInstance9 = composerImpl2.changedInstance(legacyTextFieldState4) | (i44 != 2048) | (i45 != 16384) | composerImpl2.changedInstance(textInputService2) | (i46 != 4);
                                            i33 = (i31 & 112) ^ 48;
                                            KeyboardActions keyboardActions4 = keyboardActions3;
                                            if (i33 <= 32) {
                                                legacyTextFieldState = legacyTextFieldState4;
                                                boolean z36 = (i31 & 48) != 32;
                                                zChangedInstance = zChangedInstance9 | z36 | composerImpl2.changedInstance(offsetMapping5) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(bringIntoViewRequester2) | composerImpl2.changedInstance(textFieldSelectionManager8);
                                                objRememberedValue5 = composerImpl2.rememberedValue();
                                                if (zChangedInstance || objRememberedValue5 == composer$Companion$Empty$1) {
                                                    final TextInputService textInputService6 = textInputService2;
                                                    final boolean z37 = z10;
                                                    final ImeOptions imeOptions7 = imeOptions6;
                                                    objRememberedValue5 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$focusModifier$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        /* JADX WARN: Type inference failed for: r3v3, types: [T, androidx.compose.ui.text.input.TextInputSession, java.lang.Object] */
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj16) {
                                                            TextLayoutResultProxy layoutResult;
                                                            FocusStateImpl focusStateImpl = (FocusStateImpl) ((FocusState) obj16);
                                                            if (legacyTextFieldState.getHasFocus() != focusStateImpl.isFocused()) {
                                                                ((SnapshotMutableStateImpl) legacyTextFieldState.hasFocus$delegate).setValue(Boolean.valueOf(focusStateImpl.isFocused()));
                                                                if (legacyTextFieldState.getHasFocus() && z37 && !z31) {
                                                                    TextInputService textInputService7 = textInputService6;
                                                                    LegacyTextFieldState legacyTextFieldState5 = legacyTextFieldState;
                                                                    TextFieldValue textFieldValue3 = textFieldValue;
                                                                    ImeOptions imeOptions8 = imeOptions7;
                                                                    OffsetMapping offsetMapping6 = offsetMapping5;
                                                                    TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                    EditProcessor editProcessor2 = legacyTextFieldState5.processor;
                                                                    Function1 function18 = legacyTextFieldState5.onValueChange;
                                                                    Function1 function19 = legacyTextFieldState5.onImeActionPerformed;
                                                                    companion2.getClass();
                                                                    Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                                                    TextFieldDelegate$Companion$restartInput$1 textFieldDelegate$Companion$restartInput$1 = new TextFieldDelegate$Companion$restartInput$1(editProcessor2, function18, ref$ObjectRef);
                                                                    PlatformTextInputService platformTextInputService = textInputService7.platformTextInputService;
                                                                    platformTextInputService.startInput(textFieldValue3, imeOptions8, textFieldDelegate$Companion$restartInput$1, function19);
                                                                    ?? textInputSession2 = new TextInputSession(textInputService7, platformTextInputService);
                                                                    textInputService7._currentInputSession.set(textInputSession2);
                                                                    ref$ObjectRef.element = textInputSession2;
                                                                    legacyTextFieldState5.inputSession = textInputSession2;
                                                                    CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState5, textFieldValue3, offsetMapping6);
                                                                } else {
                                                                    CoreTextFieldKt.access$endInputSession(legacyTextFieldState);
                                                                }
                                                                if (focusStateImpl.isFocused() && (layoutResult = legacyTextFieldState.getLayoutResult()) != null) {
                                                                    BuildersKt.launch$default(coroutineScope, null, null, new CoreTextFieldKt$CoreTextField$focusModifier$1$1$1$1(bringIntoViewRequester2, textFieldValue, legacyTextFieldState, layoutResult, offsetMapping5, null), 3);
                                                                }
                                                                if (!focusStateImpl.isFocused()) {
                                                                    textFieldSelectionManager8.m239deselect_kEHs6E$foundation_release(null);
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    legacyTextFieldState2 = legacyTextFieldState;
                                                    z18 = z37;
                                                    z19 = z31;
                                                    textInputService3 = textInputService6;
                                                    imeOptions5 = imeOptions7;
                                                    bringIntoViewRequester = bringIntoViewRequester2;
                                                    focusManager2 = focusManager4;
                                                    visualTransformation6 = visualTransformation7;
                                                    offsetMapping = offsetMapping5;
                                                    textFieldSelectionManager = textFieldSelectionManager8;
                                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                                } else {
                                                    focusManager2 = focusManager4;
                                                    visualTransformation6 = visualTransformation7;
                                                    textFieldSelectionManager = textFieldSelectionManager8;
                                                    imeOptions5 = imeOptions6;
                                                    textInputService3 = textInputService2;
                                                    offsetMapping = offsetMapping5;
                                                    bringIntoViewRequester = bringIntoViewRequester2;
                                                    z18 = z10;
                                                    legacyTextFieldState2 = legacyTextFieldState;
                                                    z19 = z31;
                                                }
                                                Modifier modifierFocusable = FocusableKt.focusable(mutableInteractionSource5, FocusChangedModifierKt.onFocusChanged(FocusRequesterModifierKt.focusRequester(TextFieldMagnifier, focusRequester4), (Function1) objRememberedValue5), z18);
                                                final boolean z38 = z18;
                                                MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(Boolean.valueOf((z18 || z19) ? false : true), composerImpl2);
                                                Unit unit = Unit.INSTANCE;
                                                boolean zChanged4 = composerImpl2.changed(mutableStateRememberUpdatedState) | composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changedInstance(textInputService3) | composerImpl2.changedInstance(textFieldSelectionManager);
                                                if (i33 > 32 || !composerImpl2.changed(imeOptions5)) {
                                                    textFieldSelectionManager2 = textFieldSelectionManager;
                                                    if ((i31 & 48) != 32) {
                                                        z20 = false;
                                                    }
                                                    z21 = zChanged4 | z20;
                                                    objRememberedValue6 = composerImpl2.rememberedValue();
                                                    if (z21 || objRememberedValue6 == composer$Companion$Empty$1) {
                                                        TextFieldSelectionManager textFieldSelectionManager9 = textFieldSelectionManager2;
                                                        objRememberedValue6 = new CoreTextFieldKt$CoreTextField$2$1(legacyTextFieldState2, mutableStateRememberUpdatedState, textInputService3, textFieldSelectionManager9, imeOptions5, null);
                                                        mutableState = mutableStateRememberUpdatedState;
                                                        textFieldSelectionManager3 = textFieldSelectionManager9;
                                                        composerImpl2.updateRememberedValue(objRememberedValue6);
                                                    } else {
                                                        mutableState = mutableStateRememberUpdatedState;
                                                        textFieldSelectionManager3 = textFieldSelectionManager2;
                                                    }
                                                    EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue6);
                                                    zChangedInstance2 = composerImpl2.changedInstance(legacyTextFieldState2);
                                                    Object objRememberedValue15 = composerImpl2.rememberedValue();
                                                    obj4 = objRememberedValue15;
                                                    if (!zChangedInstance2 || objRememberedValue15 == composer$Companion$Empty$1) {
                                                        Function1 function18 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$pointerModifier$1$1
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj16) {
                                                                Boolean bool = (Boolean) obj16;
                                                                bool.booleanValue();
                                                                ((SnapshotMutableStateImpl) legacyTextFieldState2.isInTouchMode$delegate).setValue(bool);
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(function18);
                                                        obj4 = function18;
                                                    }
                                                    Modifier modifierUpdateSelectionTouchMode = SelectionGesturesKt.updateSelectionTouchMode(TextFieldMagnifier, (Function1) obj4);
                                                    zChangedInstance3 = composerImpl2.changedInstance(legacyTextFieldState2) | (i45 != 16384) | (i44 != 2048) | composerImpl2.changedInstance(offsetMapping) | composerImpl2.changedInstance(textFieldSelectionManager3);
                                                    objRememberedValue7 = composerImpl2.rememberedValue();
                                                    if (!zChangedInstance3 || objRememberedValue7 == composer$Companion$Empty$1) {
                                                        final TextFieldSelectionManager textFieldSelectionManager10 = textFieldSelectionManager3;
                                                        final OffsetMapping offsetMapping6 = offsetMapping;
                                                        final LegacyTextFieldState legacyTextFieldState5 = legacyTextFieldState2;
                                                        final boolean z39 = z19;
                                                        objRememberedValue7 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$pointerModifier$2$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj16) {
                                                                SoftwareKeyboardController softwareKeyboardController2;
                                                                long j7 = ((Offset) obj16).packedValue;
                                                                LegacyTextFieldState legacyTextFieldState6 = legacyTextFieldState5;
                                                                FocusRequester focusRequester5 = focusRequester4;
                                                                boolean z40 = z39;
                                                                if (!legacyTextFieldState6.getHasFocus()) {
                                                                    FocusRequester.m378requestFocus3ESFkO8$default(focusRequester5);
                                                                } else if (!z40 && (softwareKeyboardController2 = legacyTextFieldState6.keyboardController) != null) {
                                                                    ((DelegatingSoftwareKeyboardController) softwareKeyboardController2).show();
                                                                }
                                                                if (legacyTextFieldState5.getHasFocus() && z38) {
                                                                    if (legacyTextFieldState5.getHandleState() != HandleState.Selection) {
                                                                        TextLayoutResultProxy layoutResult = legacyTextFieldState5.getLayoutResult();
                                                                        if (layoutResult != null) {
                                                                            LegacyTextFieldState legacyTextFieldState7 = legacyTextFieldState5;
                                                                            OffsetMapping offsetMapping7 = offsetMapping6;
                                                                            TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                            EditProcessor editProcessor2 = legacyTextFieldState7.processor;
                                                                            Function1 function19 = legacyTextFieldState7.onValueChange;
                                                                            companion2.getClass();
                                                                            int iTransformedToOriginal = offsetMapping7.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(j7, true));
                                                                            ((LegacyTextFieldState$onValueChange$1) function19).mo781invoke(TextFieldValue.m780copy3r_uNRQ$default(editProcessor2.mBufferState, null, TextRangeKt.TextRange(iTransformedToOriginal, iTransformedToOriginal), 5));
                                                                            if (legacyTextFieldState7.textDelegate.text.text.length() > 0) {
                                                                                ((SnapshotMutableStateImpl) legacyTextFieldState7.handleState$delegate).setValue(HandleState.Cursor);
                                                                            }
                                                                        }
                                                                    } else {
                                                                        textFieldSelectionManager10.m239deselect_kEHs6E$foundation_release(Offset.m395boximpl(j7));
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        focusRequester2 = focusRequester4;
                                                        z22 = z38;
                                                        textFieldSelectionManager4 = textFieldSelectionManager10;
                                                        composerImpl2.updateRememberedValue(objRememberedValue7);
                                                    } else {
                                                        textFieldSelectionManager4 = textFieldSelectionManager3;
                                                        z22 = z38;
                                                        focusRequester2 = focusRequester4;
                                                    }
                                                    final Function1 function19 = (Function1) objRememberedValue7;
                                                    if (z22) {
                                                        z23 = z22;
                                                    } else {
                                                        z23 = z22;
                                                        modifierUpdateSelectionTouchMode = ComposedModifierKt.composed(modifierUpdateSelectionTouchMode, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(3);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function3
                                                            public final Object invoke(Object obj16, Object obj17, Object obj18) {
                                                                ((Number) obj18).intValue();
                                                                ComposerImpl composerImpl6 = (ComposerImpl) ((Composer) obj17);
                                                                composerImpl6.startReplaceGroup(-102778667);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.tapPressTextFieldModifier.<anonymous> (TextFieldPressGestureFilter.kt:40)");
                                                                }
                                                                Object objRememberedValue16 = composerImpl6.rememberedValue();
                                                                Composer.Companion.getClass();
                                                                Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                                                                if (objRememberedValue16 == composer$Companion$Empty$12) {
                                                                    objRememberedValue16 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl6);
                                                                    composerImpl6.updateRememberedValue(objRememberedValue16);
                                                                }
                                                                final CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue16;
                                                                Object objRememberedValue17 = composerImpl6.rememberedValue();
                                                                if (objRememberedValue17 == composer$Companion$Empty$12) {
                                                                    objRememberedValue17 = SnapshotStateKt.mutableStateOf$default(null);
                                                                    composerImpl6.updateRememberedValue(objRememberedValue17);
                                                                }
                                                                final MutableState mutableState2 = (MutableState) objRememberedValue17;
                                                                final MutableState mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(function19, composerImpl6);
                                                                MutableInteractionSource mutableInteractionSource6 = mutableInteractionSource5;
                                                                boolean zChanged5 = composerImpl6.changed(mutableInteractionSource6);
                                                                final MutableInteractionSource mutableInteractionSource7 = mutableInteractionSource5;
                                                                Object objRememberedValue18 = composerImpl6.rememberedValue();
                                                                if (zChanged5 || objRememberedValue18 == composer$Companion$Empty$12) {
                                                                    objRememberedValue18 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$1$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj19) {
                                                                            final MutableState<PressInteraction$Press> mutableState3 = mutableState2;
                                                                            final MutableInteractionSource mutableInteractionSource8 = mutableInteractionSource7;
                                                                            return new DisposableEffectResult() { // from class: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$1$1$invoke$$inlined$onDispose$1
                                                                                @Override // androidx.compose.runtime.DisposableEffectResult
                                                                                public final void dispose() {
                                                                                    MutableState mutableState4 = mutableState3;
                                                                                    PressInteraction$Press pressInteraction$Press = (PressInteraction$Press) mutableState4.getValue();
                                                                                    if (pressInteraction$Press != null) {
                                                                                        PressInteraction$Cancel pressInteraction$Cancel = new PressInteraction$Cancel(pressInteraction$Press);
                                                                                        MutableInteractionSource mutableInteractionSource9 = mutableInteractionSource8;
                                                                                        if (mutableInteractionSource9 != null) {
                                                                                            mutableInteractionSource9.tryEmit(pressInteraction$Cancel);
                                                                                        }
                                                                                        mutableState4.setValue(null);
                                                                                    }
                                                                                }
                                                                            };
                                                                        }
                                                                    };
                                                                    composerImpl6.updateRememberedValue(objRememberedValue18);
                                                                }
                                                                EffectsKt.DisposableEffect(mutableInteractionSource6, (Function1) objRememberedValue18, composerImpl6);
                                                                Modifier.Companion companion2 = Modifier.Companion;
                                                                MutableInteractionSource mutableInteractionSource8 = mutableInteractionSource5;
                                                                boolean zChangedInstance10 = composerImpl6.changedInstance(coroutineScope2) | composerImpl6.changed(mutableInteractionSource5) | composerImpl6.changed(mutableStateRememberUpdatedState2);
                                                                final MutableInteractionSource mutableInteractionSource9 = mutableInteractionSource5;
                                                                Object objRememberedValue19 = composerImpl6.rememberedValue();
                                                                if (zChangedInstance10 || objRememberedValue19 == composer$Companion$Empty$12) {
                                                                    objRememberedValue19 = new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$2$1

                                                                        /* renamed from: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$2$1$1, reason: invalid class name */
                                                                        final class AnonymousClass1 extends SuspendLambda implements Function3 {
                                                                            final /* synthetic */ MutableInteractionSource $interactionSource;
                                                                            final /* synthetic */ MutableState<PressInteraction$Press> $pressedInteraction;
                                                                            final /* synthetic */ CoroutineScope $scope;
                                                                            /* synthetic */ long J$0;
                                                                            private /* synthetic */ Object L$0;
                                                                            int label;

                                                                            /* renamed from: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$2$1$1$1, reason: invalid class name and collision with other inner class name */
                                                                            final class C00201 extends SuspendLambda implements Function2 {
                                                                                final /* synthetic */ MutableInteractionSource $interactionSource;
                                                                                final /* synthetic */ long $it;
                                                                                final /* synthetic */ MutableState<PressInteraction$Press> $pressedInteraction;
                                                                                Object L$0;
                                                                                int label;

                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                public C00201(MutableState<PressInteraction$Press> mutableState, long j, MutableInteractionSource mutableInteractionSource, Continuation continuation) {
                                                                                    super(2, continuation);
                                                                                    this.$pressedInteraction = mutableState;
                                                                                    this.$it = j;
                                                                                    this.$interactionSource = mutableInteractionSource;
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Continuation create(Object obj, Continuation continuation) {
                                                                                    return new C00201(this.$pressedInteraction, this.$it, this.$interactionSource, continuation);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                public final Object invoke(Object obj, Object obj2) {
                                                                                    return ((C00201) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                                }

                                                                                /* JADX WARN: Removed duplicated region for block: B:22:0x0058  */
                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                /*
                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                */
                                                                                public final Object invokeSuspend(Object obj) {
                                                                                    MutableState<PressInteraction$Press> mutableState;
                                                                                    MutableState<PressInteraction$Press> mutableState2;
                                                                                    PressInteraction$Press pressInteraction$Press;
                                                                                    MutableInteractionSource mutableInteractionSource;
                                                                                    PressInteraction$Press pressInteraction$Press2;
                                                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                    int i = this.label;
                                                                                    if (i == 0) {
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                        PressInteraction$Press pressInteraction$Press3 = (PressInteraction$Press) this.$pressedInteraction.getValue();
                                                                                        if (pressInteraction$Press3 == null) {
                                                                                            pressInteraction$Press = new PressInteraction$Press(this.$it, null);
                                                                                            mutableInteractionSource = this.$interactionSource;
                                                                                            if (mutableInteractionSource != null) {
                                                                                            }
                                                                                            this.$pressedInteraction.setValue(pressInteraction$Press);
                                                                                            return Unit.INSTANCE;
                                                                                        }
                                                                                        MutableInteractionSource mutableInteractionSource2 = this.$interactionSource;
                                                                                        mutableState = this.$pressedInteraction;
                                                                                        PressInteraction$Cancel pressInteraction$Cancel = new PressInteraction$Cancel(pressInteraction$Press3);
                                                                                        if (mutableInteractionSource2 != null) {
                                                                                            this.L$0 = mutableState;
                                                                                            this.label = 1;
                                                                                            if (mutableInteractionSource2.emit(pressInteraction$Cancel, this) != coroutineSingletons) {
                                                                                                mutableState2 = mutableState;
                                                                                            }
                                                                                        }
                                                                                        mutableState.setValue(null);
                                                                                        pressInteraction$Press = new PressInteraction$Press(this.$it, null);
                                                                                        mutableInteractionSource = this.$interactionSource;
                                                                                        if (mutableInteractionSource != null) {
                                                                                            this.L$0 = pressInteraction$Press;
                                                                                            this.label = 2;
                                                                                            if (mutableInteractionSource.emit(pressInteraction$Press, this) != coroutineSingletons) {
                                                                                                pressInteraction$Press2 = pressInteraction$Press;
                                                                                                pressInteraction$Press = pressInteraction$Press2;
                                                                                            }
                                                                                        }
                                                                                        this.$pressedInteraction.setValue(pressInteraction$Press);
                                                                                        return Unit.INSTANCE;
                                                                                        return coroutineSingletons;
                                                                                    }
                                                                                    if (i != 1) {
                                                                                        if (i != 2) {
                                                                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                        }
                                                                                        pressInteraction$Press2 = (PressInteraction$Press) this.L$0;
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                        pressInteraction$Press = pressInteraction$Press2;
                                                                                        this.$pressedInteraction.setValue(pressInteraction$Press);
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                    mutableState2 = (MutableState) this.L$0;
                                                                                    ResultKt.throwOnFailure(obj);
                                                                                    mutableState = mutableState2;
                                                                                    mutableState.setValue(null);
                                                                                    pressInteraction$Press = new PressInteraction$Press(this.$it, null);
                                                                                    mutableInteractionSource = this.$interactionSource;
                                                                                    if (mutableInteractionSource != null) {
                                                                                    }
                                                                                    this.$pressedInteraction.setValue(pressInteraction$Press);
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }

                                                                            /* renamed from: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$2$1$1$2, reason: invalid class name */
                                                                            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                                                                                final /* synthetic */ MutableInteractionSource $interactionSource;
                                                                                final /* synthetic */ MutableState<PressInteraction$Press> $pressedInteraction;
                                                                                final /* synthetic */ boolean $success;
                                                                                Object L$0;
                                                                                int label;

                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                public AnonymousClass2(MutableState<PressInteraction$Press> mutableState, boolean z, MutableInteractionSource mutableInteractionSource, Continuation continuation) {
                                                                                    super(2, continuation);
                                                                                    this.$pressedInteraction = mutableState;
                                                                                    this.$success = z;
                                                                                    this.$interactionSource = mutableInteractionSource;
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Continuation create(Object obj, Continuation continuation) {
                                                                                    return new AnonymousClass2(this.$pressedInteraction, this.$success, this.$interactionSource, continuation);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                public final Object invoke(Object obj, Object obj2) {
                                                                                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Object invokeSuspend(Object obj) {
                                                                                    MutableState<PressInteraction$Press> mutableState;
                                                                                    MutableState<PressInteraction$Press> mutableState2;
                                                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                    int i = this.label;
                                                                                    if (i == 0) {
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                        PressInteraction$Press pressInteraction$Press = (PressInteraction$Press) this.$pressedInteraction.getValue();
                                                                                        if (pressInteraction$Press != null) {
                                                                                            boolean z = this.$success;
                                                                                            MutableInteractionSource mutableInteractionSource = this.$interactionSource;
                                                                                            mutableState = this.$pressedInteraction;
                                                                                            Interaction pressInteraction$Release = z ? new PressInteraction$Release(pressInteraction$Press) : new PressInteraction$Cancel(pressInteraction$Press);
                                                                                            if (mutableInteractionSource != null) {
                                                                                                this.L$0 = mutableState;
                                                                                                this.label = 1;
                                                                                                if (mutableInteractionSource.emit(pressInteraction$Release, this) == coroutineSingletons) {
                                                                                                    return coroutineSingletons;
                                                                                                }
                                                                                                mutableState2 = mutableState;
                                                                                            }
                                                                                            mutableState.setValue(null);
                                                                                        }
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                    if (i != 1) {
                                                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                    }
                                                                                    mutableState2 = (MutableState) this.L$0;
                                                                                    ResultKt.throwOnFailure(obj);
                                                                                    mutableState = mutableState2;
                                                                                    mutableState.setValue(null);
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }

                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            public AnonymousClass1(CoroutineScope coroutineScope, MutableState<PressInteraction$Press> mutableState, MutableInteractionSource mutableInteractionSource, Continuation continuation) {
                                                                                super(3, continuation);
                                                                                this.$scope = coroutineScope;
                                                                                this.$pressedInteraction = mutableState;
                                                                                this.$interactionSource = mutableInteractionSource;
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function3
                                                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                                                long j = ((Offset) obj2).packedValue;
                                                                                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$scope, this.$pressedInteraction, this.$interactionSource, (Continuation) obj3);
                                                                                anonymousClass1.L$0 = (PressGestureScope) obj;
                                                                                anonymousClass1.J$0 = j;
                                                                                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                                                                            }

                                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                            public final Object invokeSuspend(Object obj) {
                                                                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                int i = this.label;
                                                                                if (i == 0) {
                                                                                    ResultKt.throwOnFailure(obj);
                                                                                    PressGestureScope pressGestureScope = (PressGestureScope) this.L$0;
                                                                                    BuildersKt.launch$default(this.$scope, null, null, new C00201(this.$pressedInteraction, this.J$0, this.$interactionSource, null), 3);
                                                                                    this.label = 1;
                                                                                    obj = ((PressGestureScopeImpl) pressGestureScope).tryAwaitRelease(this);
                                                                                    if (obj == coroutineSingletons) {
                                                                                        return coroutineSingletons;
                                                                                    }
                                                                                } else {
                                                                                    if (i != 1) {
                                                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                    }
                                                                                    ResultKt.throwOnFailure(obj);
                                                                                }
                                                                                BuildersKt.launch$default(this.$scope, null, null, new AnonymousClass2(this.$pressedInteraction, ((Boolean) obj).booleanValue(), this.$interactionSource, null), 3);
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        }

                                                                        @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                                        public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                                            AnonymousClass1 anonymousClass1 = new AnonymousClass1(coroutineScope2, mutableState2, mutableInteractionSource9, null);
                                                                            final State state = mutableStateRememberUpdatedState2;
                                                                            Object objDetectTapAndPress = TapGestureDetectorKt.detectTapAndPress(pointerInputScope, anonymousClass1, new Function1() { // from class: androidx.compose.foundation.text.TextFieldPressGestureFilterKt$tapPressTextFieldModifier$1$2$1.2
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                /* JADX WARN: Multi-variable type inference failed */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj19) {
                                                                                    ((Function1) state.getValue()).mo781invoke(Offset.m395boximpl(((Offset) obj19).packedValue));
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }, continuation);
                                                                            return objDetectTapAndPress == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapAndPress : Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    composerImpl6.updateRememberedValue(objRememberedValue19);
                                                                }
                                                                Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(companion2, mutableInteractionSource8, (PointerInputEventHandler) objRememberedValue19);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                                composerImpl6.end(false);
                                                                return modifierPointerInput;
                                                            }
                                                        });
                                                    }
                                                    Modifier modifierSelectionGestureInput = SelectionGesturesKt.selectionGestureInput(modifierUpdateSelectionTouchMode, textFieldSelectionManager4.mouseSelectionObserver, textFieldSelectionManager4.touchSelectionObserver);
                                                    PointerIcon.Companion.getClass();
                                                    Modifier modifierThen = modifierSelectionGestureInput.then(new PointerHoverIconModifierElement(PointerIcon.Companion.Text, false));
                                                    zChangedInstance4 = composerImpl2.changedInstance(legacyTextFieldState2) | (i46 != 4) | composerImpl2.changedInstance(offsetMapping);
                                                    Object objRememberedValue16 = composerImpl2.rememberedValue();
                                                    if (!zChangedInstance4 || objRememberedValue16 == composer$Companion$Empty$1) {
                                                        Function1 function110 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$drawModifier$1$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(1);
                                                            }

                                                            /* JADX WARN: Multi-variable type inference failed */
                                                            /* JADX WARN: Removed duplicated region for block: B:30:0x00bf  */
                                                            /* JADX WARN: Type inference failed for: r8v1 */
                                                            /* JADX WARN: Type inference failed for: r8v10 */
                                                            /* JADX WARN: Type inference failed for: r8v12 */
                                                            /* JADX WARN: Type inference failed for: r8v13 */
                                                            /* JADX WARN: Type inference failed for: r8v14 */
                                                            /* JADX WARN: Type inference failed for: r8v15 */
                                                            /* JADX WARN: Type inference failed for: r8v16 */
                                                            /* JADX WARN: Type inference failed for: r8v17 */
                                                            /* JADX WARN: Type inference failed for: r8v2 */
                                                            /* JADX WARN: Type inference failed for: r8v3 */
                                                            /* JADX WARN: Type inference failed for: r8v5 */
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                            */
                                                            public final Object mo781invoke(Object obj16) throws Throwable {
                                                                TextLayoutResult textLayoutResult;
                                                                long j7;
                                                                ?? r8;
                                                                long jMo794getColor0d7_KjU;
                                                                Canvas canvas;
                                                                DrawScope drawScope = (DrawScope) obj16;
                                                                TextLayoutResultProxy layoutResult = legacyTextFieldState2.getLayoutResult();
                                                                if (layoutResult != null) {
                                                                    TextFieldValue textFieldValue3 = textFieldValue;
                                                                    LegacyTextFieldState legacyTextFieldState6 = legacyTextFieldState2;
                                                                    OffsetMapping offsetMapping7 = offsetMapping;
                                                                    Canvas canvas2 = drawScope.getDrawContext().getCanvas();
                                                                    TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                    long j8 = ((TextRange) ((SnapshotMutableStateImpl) legacyTextFieldState6.selectionPreviewHighlightRange$delegate).getValue()).packedValue;
                                                                    long j9 = ((TextRange) ((SnapshotMutableStateImpl) legacyTextFieldState6.deletionPreviewHighlightRange$delegate).getValue()).packedValue;
                                                                    long j10 = legacyTextFieldState6.selectionBackgroundColor;
                                                                    companion2.getClass();
                                                                    boolean zM749getCollapsedimpl = TextRange.m749getCollapsedimpl(j8);
                                                                    AndroidPaint androidPaint = legacyTextFieldState6.highlightPaint;
                                                                    TextLayoutResult textLayoutResult2 = layoutResult.value;
                                                                    if (zM749getCollapsedimpl) {
                                                                        AndroidPaint androidPaint2 = androidPaint;
                                                                        if (TextRange.m749getCollapsedimpl(j9)) {
                                                                            textLayoutResult = textLayoutResult2;
                                                                            r8 = androidPaint2;
                                                                            if (!TextRange.m749getCollapsedimpl(textFieldValue3.selection)) {
                                                                                androidPaint2.m440setColor8_81llA(j10);
                                                                                TextFieldDelegate.Companion.m205drawHighlightLepunE(canvas2, textFieldValue3.selection, offsetMapping7, textLayoutResult, androidPaint2);
                                                                                r8 = androidPaint2;
                                                                            }
                                                                        } else {
                                                                            Color colorM456boximpl = Color.m456boximpl(textLayoutResult2.layoutInput.style.m758getColor0d7_KjU());
                                                                            if (colorM456boximpl.value == 16) {
                                                                                colorM456boximpl = null;
                                                                            }
                                                                            if (colorM456boximpl != null) {
                                                                                j7 = colorM456boximpl.value;
                                                                            } else {
                                                                                Color.Companion.getClass();
                                                                                j7 = Color.Black;
                                                                            }
                                                                            androidPaint2.m440setColor8_81llA(ColorKt.Color(Color.m463getRedimpl(j7), Color.m462getGreenimpl(j7), Color.m460getBlueimpl(j7), Color.m459getAlphaimpl(j7) * 0.2f, Color.m461getColorSpaceimpl(j7)));
                                                                            textLayoutResult = textLayoutResult2;
                                                                            TextFieldDelegate.Companion.m205drawHighlightLepunE(canvas2, j9, offsetMapping7, textLayoutResult, androidPaint2);
                                                                            r8 = androidPaint2;
                                                                        }
                                                                    } else {
                                                                        androidPaint.m440setColor8_81llA(j10);
                                                                        AndroidPaint androidPaint3 = androidPaint;
                                                                        textLayoutResult = textLayoutResult2;
                                                                        TextFieldDelegate.Companion.m205drawHighlightLepunE(canvas2, j8, offsetMapping7, textLayoutResult, androidPaint3);
                                                                        r8 = androidPaint3;
                                                                    }
                                                                    TextPainter.INSTANCE.getClass();
                                                                    boolean z40 = textLayoutResult.getDidOverflowWidth() || textLayoutResult.getDidOverflowHeight();
                                                                    TextLayoutInput textLayoutInput = textLayoutResult.layoutInput;
                                                                    if (z40) {
                                                                        int i47 = textLayoutInput.overflow;
                                                                        TextOverflow.Companion.getClass();
                                                                        boolean z41 = i47 != TextOverflow.Visible;
                                                                        if (z41) {
                                                                            long j11 = textLayoutResult.size;
                                                                            Offset.Companion.getClass();
                                                                            Size.Companion companion3 = Size.Companion;
                                                                            r8 = 0;
                                                                            Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw(0L, (Float.floatToRawIntBits((int) (j11 >> 32)) << 32) | (4294967295L & Float.floatToRawIntBits((int) (j11 & 4294967295L))));
                                                                            canvas2.save();
                                                                            Canvas.m455clipRectmtrdDE$default(canvas2, rectM413Recttz77jQw);
                                                                        }
                                                                        SpanStyle spanStyle = textLayoutInput.style.spanStyle;
                                                                        TextDecoration textDecoration = spanStyle.textDecoration;
                                                                        TextForegroundStyle textForegroundStyle = spanStyle.textForegroundStyle;
                                                                        if (textDecoration == null) {
                                                                            TextDecoration.Companion.getClass();
                                                                            textDecoration = TextDecoration.None;
                                                                        }
                                                                        TextDecoration textDecoration2 = textDecoration;
                                                                        Shadow shadow = spanStyle.shadow;
                                                                        if (shadow == null) {
                                                                            Shadow.Companion.getClass();
                                                                            shadow = Shadow.None;
                                                                        }
                                                                        Shadow shadow2 = shadow;
                                                                        DrawStyle drawStyle = spanStyle.drawStyle;
                                                                        if (drawStyle == null) {
                                                                            drawStyle = Fill.INSTANCE;
                                                                        }
                                                                        DrawStyle drawStyle2 = drawStyle;
                                                                        try {
                                                                            Brush brush7 = textForegroundStyle.getBrush();
                                                                            try {
                                                                                if (brush7 != null) {
                                                                                    Canvas canvas3 = canvas2;
                                                                                    MultiParagraph.m734painthn5TExg$default(textLayoutResult.multiParagraph, canvas3, brush7, textForegroundStyle != TextForegroundStyle.Unspecified.INSTANCE ? textForegroundStyle.getAlpha() : 1.0f, shadow2, textDecoration2, drawStyle2);
                                                                                    canvas = canvas3;
                                                                                    r8 = canvas3;
                                                                                } else {
                                                                                    if (textForegroundStyle != TextForegroundStyle.Unspecified.INSTANCE) {
                                                                                        jMo794getColor0d7_KjU = textForegroundStyle.mo794getColor0d7_KjU();
                                                                                    } else {
                                                                                        Color.Companion.getClass();
                                                                                        jMo794getColor0d7_KjU = Color.Black;
                                                                                    }
                                                                                    Canvas canvas4 = canvas2;
                                                                                    MultiParagraph.m733paintLG529CI$default(textLayoutResult.multiParagraph, canvas4, jMo794getColor0d7_KjU, shadow2, textDecoration2, drawStyle2);
                                                                                    canvas = canvas4;
                                                                                    r8 = canvas4;
                                                                                }
                                                                                if (z41) {
                                                                                    canvas.restore();
                                                                                }
                                                                            } catch (Throwable th) {
                                                                                th = th;
                                                                                canvas2 = r8;
                                                                                Throwable th2 = th;
                                                                                if (!z41) {
                                                                                    throw th2;
                                                                                }
                                                                                canvas2.restore();
                                                                                throw th2;
                                                                            }
                                                                        } catch (Throwable th3) {
                                                                            th = th3;
                                                                        }
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(function110);
                                                        obj5 = function110;
                                                    } else {
                                                        obj5 = objRememberedValue16;
                                                    }
                                                    final Modifier modifierDrawBehind = DrawModifierKt.drawBehind(TextFieldMagnifier, (Function1) obj5);
                                                    textFieldSelectionManager5 = textFieldSelectionManager4;
                                                    zChangedInstance5 = (i46 != 4) | composerImpl2.changedInstance(legacyTextFieldState2) | (i44 != 2048) | composerImpl2.changed(windowInfo2) | composerImpl2.changedInstance(textFieldSelectionManager4) | composerImpl2.changedInstance(offsetMapping);
                                                    objRememberedValue8 = composerImpl2.rememberedValue();
                                                    if (!zChangedInstance5 || objRememberedValue8 == composer$Companion$Empty$1) {
                                                        final OffsetMapping offsetMapping7 = offsetMapping;
                                                        final LegacyTextFieldState legacyTextFieldState6 = legacyTextFieldState2;
                                                        objRememberedValue8 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$onPositionedModifier$1$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj16) {
                                                                LayoutCoordinates layoutCoordinates;
                                                                LayoutCoordinates layoutCoordinates2 = (LayoutCoordinates) obj16;
                                                                LegacyTextFieldState legacyTextFieldState7 = legacyTextFieldState6;
                                                                legacyTextFieldState7._layoutCoordinates = layoutCoordinates2;
                                                                TextLayoutResultProxy layoutResult = legacyTextFieldState7.getLayoutResult();
                                                                if (layoutResult != null) {
                                                                    layoutResult.innerTextFieldCoordinates = layoutCoordinates2;
                                                                }
                                                                if (z23) {
                                                                    if (legacyTextFieldState6.getHandleState() == HandleState.Selection) {
                                                                        if (((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState6.showFloatingToolbar$delegate).getValue()).booleanValue() && windowInfo2.isWindowFocused()) {
                                                                            textFieldSelectionManager5.showSelectionToolbar$foundation_release();
                                                                        } else {
                                                                            textFieldSelectionManager5.hideSelectionToolbar$foundation_release();
                                                                        }
                                                                        ((SnapshotMutableStateImpl) legacyTextFieldState6.showSelectionHandleStart$delegate).setValue(Boolean.valueOf(TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager5, true)));
                                                                        ((SnapshotMutableStateImpl) legacyTextFieldState6.showSelectionHandleEnd$delegate).setValue(Boolean.valueOf(TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager5, false)));
                                                                        ((SnapshotMutableStateImpl) legacyTextFieldState6.showCursorHandle$delegate).setValue(Boolean.valueOf(TextRange.m749getCollapsedimpl(textFieldValue.selection)));
                                                                    } else if (legacyTextFieldState6.getHandleState() == HandleState.Cursor) {
                                                                        ((SnapshotMutableStateImpl) legacyTextFieldState6.showCursorHandle$delegate).setValue(Boolean.valueOf(TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager5, true)));
                                                                    }
                                                                    CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState6, textFieldValue, offsetMapping7);
                                                                    TextLayoutResultProxy layoutResult2 = legacyTextFieldState6.getLayoutResult();
                                                                    if (layoutResult2 != null) {
                                                                        LegacyTextFieldState legacyTextFieldState8 = legacyTextFieldState6;
                                                                        TextFieldValue textFieldValue3 = textFieldValue;
                                                                        OffsetMapping offsetMapping8 = offsetMapping7;
                                                                        TextInputSession textInputSession2 = legacyTextFieldState8.inputSession;
                                                                        if (textInputSession2 != null && legacyTextFieldState8.getHasFocus()) {
                                                                            TextFieldDelegate.Companion.getClass();
                                                                            final LayoutCoordinates layoutCoordinates3 = layoutResult2.innerTextFieldCoordinates;
                                                                            if (layoutCoordinates3 != null && layoutCoordinates3.isAttached() && (layoutCoordinates = layoutResult2.decorationBoxCoordinates) != null) {
                                                                                Function1 function111 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldDelegate$Companion$updateTextLayoutResult$1$1$1
                                                                                    {
                                                                                        super(1);
                                                                                    }

                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                    /* renamed from: invoke */
                                                                                    public final Object mo781invoke(Object obj17) {
                                                                                        float[] fArr = ((Matrix) obj17).values;
                                                                                        if (layoutCoordinates3.isAttached()) {
                                                                                            LayoutCoordinatesKt.findRootCoordinates(layoutCoordinates3).mo619transformFromEL8BTi8(layoutCoordinates3, fArr);
                                                                                        }
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                };
                                                                                Rect rectVisibleBounds = SelectionManagerKt.visibleBounds(layoutCoordinates3);
                                                                                Rect rectLocalBoundingBoxOf = layoutCoordinates3.localBoundingBoxOf(layoutCoordinates, false);
                                                                                if (Intrinsics.areEqual((TextInputSession) textInputSession2.textInputService._currentInputSession.get(), textInputSession2)) {
                                                                                    textInputSession2.platformTextInputService.updateTextLayoutResult(textFieldValue3, offsetMapping8, layoutResult2.value, function111, rectVisibleBounds, rectLocalBoundingBoxOf);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        z24 = z23;
                                                        windowInfo = windowInfo2;
                                                        textFieldSelectionManager6 = textFieldSelectionManager5;
                                                        composerImpl2.updateRememberedValue(objRememberedValue8);
                                                    } else {
                                                        z24 = z23;
                                                        windowInfo = windowInfo2;
                                                        textFieldSelectionManager6 = textFieldSelectionManager5;
                                                    }
                                                    final Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(TextFieldMagnifier, (Function1) objRememberedValue8);
                                                    z25 = z24;
                                                    final TextFieldSelectionManager textFieldSelectionManager11 = textFieldSelectionManager6;
                                                    offsetMapping2 = offsetMapping;
                                                    final ImeOptions imeOptions8 = imeOptions5;
                                                    coreTextFieldSemanticsModifier = new CoreTextFieldSemanticsModifier(transformedText4, textFieldValue, legacyTextFieldState2, z19, z25, visualTransformation6 instanceof PasswordVisualTransformation, offsetMapping2, textFieldSelectionManager11, imeOptions8, focusRequester2);
                                                    if (z25 || z19 || !windowInfo.isWindowFocused()) {
                                                        offsetMapping3 = offsetMapping2;
                                                        coreTextFieldSemanticsModifier2 = coreTextFieldSemanticsModifier;
                                                        z26 = z25;
                                                    } else {
                                                        coreTextFieldSemanticsModifier2 = coreTextFieldSemanticsModifier;
                                                        z26 = z25;
                                                        if (TextRange.m749getCollapsedimpl(((TextRange) ((SnapshotMutableStateImpl) legacyTextFieldState2.selectionPreviewHighlightRange$delegate).getValue()).packedValue) && TextRange.m749getCollapsedimpl(((TextRange) ((SnapshotMutableStateImpl) legacyTextFieldState2.deletionPreviewHighlightRange$delegate).getValue()).packedValue)) {
                                                            brush4 = brush6;
                                                            Function3 function36 = new Function3() { // from class: androidx.compose.foundation.text.TextFieldCursorKt$cursor$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(3);
                                                                }

                                                                /* JADX WARN: Removed duplicated region for block: B:27:0x009d  */
                                                                /* JADX WARN: Removed duplicated region for block: B:32:0x00e0  */
                                                                /* JADX WARN: Removed duplicated region for block: B:9:0x003b  */
                                                                @Override // kotlin.jvm.functions.Function3
                                                                /*
                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                */
                                                                public final Object invoke(Object obj16, Object obj17, Object obj18) {
                                                                    Object objDrawWithContent;
                                                                    Modifier modifier7 = (Modifier) obj16;
                                                                    ((Number) obj18).intValue();
                                                                    ComposerImpl composerImpl6 = (ComposerImpl) ((Composer) obj17);
                                                                    composerImpl6.startReplaceGroup(-84507373);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.foundation.text.cursor.<anonymous> (TextFieldCursor.kt:46)");
                                                                    }
                                                                    boolean zBooleanValue = ((Boolean) composerImpl6.consume(CompositionLocalsKt.LocalCursorBlinkEnabled)).booleanValue();
                                                                    boolean zChanged5 = composerImpl6.changed(zBooleanValue);
                                                                    Object objRememberedValue17 = composerImpl6.rememberedValue();
                                                                    Composer.Companion companion2 = Composer.Companion;
                                                                    if (!zChanged5) {
                                                                        companion2.getClass();
                                                                        if (objRememberedValue17 == Composer.Companion.Empty) {
                                                                            objRememberedValue17 = new CursorAnimationState(zBooleanValue);
                                                                            composerImpl6.updateRememberedValue(objRememberedValue17);
                                                                        }
                                                                    }
                                                                    final CursorAnimationState cursorAnimationState = (CursorAnimationState) objRememberedValue17;
                                                                    Brush brush7 = brush4;
                                                                    boolean z40 = ((brush7 instanceof SolidColor) && ((SolidColor) brush7).value == 16) ? false : true;
                                                                    if (((WindowInfo) composerImpl6.consume(CompositionLocalsKt.LocalWindowInfo)).isWindowFocused() && legacyTextFieldState2.getHasFocus() && TextRange.m749getCollapsedimpl(textFieldValue.selection) && z40) {
                                                                        composerImpl6.startReplaceGroup(808460990);
                                                                        TextFieldValue textFieldValue3 = textFieldValue;
                                                                        AnnotatedString annotatedString5 = textFieldValue3.annotatedString;
                                                                        TextRange textRangeM747boximpl = TextRange.m747boximpl(textFieldValue3.selection);
                                                                        boolean zChangedInstance10 = composerImpl6.changedInstance(cursorAnimationState);
                                                                        Object objRememberedValue18 = composerImpl6.rememberedValue();
                                                                        if (!zChangedInstance10) {
                                                                            companion2.getClass();
                                                                            if (objRememberedValue18 == Composer.Companion.Empty) {
                                                                                objRememberedValue18 = new TextFieldCursorKt$cursor$1$1$1(cursorAnimationState, null);
                                                                                composerImpl6.updateRememberedValue(objRememberedValue18);
                                                                            }
                                                                            EffectsKt.LaunchedEffect(annotatedString5, textRangeM747boximpl, (Function2) objRememberedValue18, composerImpl6);
                                                                            boolean zChangedInstance11 = composerImpl6.changedInstance(cursorAnimationState) | composerImpl6.changedInstance(offsetMapping2) | composerImpl6.changed(textFieldValue) | composerImpl6.changedInstance(legacyTextFieldState2) | composerImpl6.changed(brush4);
                                                                            final OffsetMapping offsetMapping8 = offsetMapping2;
                                                                            final TextFieldValue textFieldValue4 = textFieldValue;
                                                                            final LegacyTextFieldState legacyTextFieldState7 = legacyTextFieldState2;
                                                                            final Brush brush8 = brush4;
                                                                            Object objRememberedValue19 = composerImpl6.rememberedValue();
                                                                            if (!zChangedInstance11) {
                                                                                companion2.getClass();
                                                                                if (objRememberedValue19 == Composer.Companion.Empty) {
                                                                                    Object obj19 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldCursorKt$cursor$1$2$1
                                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                        {
                                                                                            super(1);
                                                                                        }

                                                                                        @Override // kotlin.jvm.functions.Function1
                                                                                        /* renamed from: invoke */
                                                                                        public final Object mo781invoke(Object obj20) {
                                                                                            TextLayoutResult textLayoutResult;
                                                                                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj20);
                                                                                            layoutNodeDrawScope.drawContent();
                                                                                            float floatValue = ((SnapshotMutableFloatStateImpl) cursorAnimationState.cursorAlpha$delegate).getFloatValue();
                                                                                            if (floatValue != 0.0f) {
                                                                                                OffsetMapping offsetMapping9 = offsetMapping8;
                                                                                                long j7 = textFieldValue4.selection;
                                                                                                TextRange.Companion companion3 = TextRange.Companion;
                                                                                                int iOriginalToTransformed3 = offsetMapping9.originalToTransformed((int) (j7 >> 32));
                                                                                                TextLayoutResultProxy layoutResult = legacyTextFieldState7.getLayoutResult();
                                                                                                Rect rect = (layoutResult == null || (textLayoutResult = layoutResult.value) == null) ? new Rect(0.0f, 0.0f, 0.0f, 0.0f) : textLayoutResult.getCursorRect(iOriginalToTransformed3);
                                                                                                float fFloor = (float) Math.floor(layoutNodeDrawScope.mo58toPx0680j_4(TextFieldCursor_androidKt.DefaultCursorThickness));
                                                                                                if (fFloor < 1.0f) {
                                                                                                    fFloor = 1.0f;
                                                                                                }
                                                                                                float f = fFloor / 2;
                                                                                                float f2 = rect.left + f;
                                                                                                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                                                                                                float fIntBitsToFloat = Float.intBitsToFloat((int) (canvasDrawScope.mo547getSizeNHjbRc() >> 32)) - f;
                                                                                                if (f2 > fIntBitsToFloat) {
                                                                                                    f2 = fIntBitsToFloat;
                                                                                                }
                                                                                                if (f2 >= f) {
                                                                                                    f = f2;
                                                                                                }
                                                                                                float fFloor2 = ((int) fFloor) % 2 == 1 ? ((float) Math.floor(f)) + 0.5f : (float) Math.rint(f);
                                                                                                long jFloatToRawIntBits = (Float.floatToRawIntBits(fFloor2) << 32) | (Float.floatToRawIntBits(rect.top) & 4294967295L);
                                                                                                Offset.Companion companion4 = Offset.Companion;
                                                                                                long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fFloor2) << 32) | (Float.floatToRawIntBits(rect.bottom) & 4294967295L);
                                                                                                Brush brush9 = brush8;
                                                                                                Stroke.Companion.getClass();
                                                                                                DrawScope.Companion companion5 = DrawScope.Companion;
                                                                                                companion5.getClass();
                                                                                                int i47 = DrawScope.Companion.DefaultBlendMode;
                                                                                                Canvas canvas = canvasDrawScope.drawParams.canvas;
                                                                                                StrokeJoin.Companion.getClass();
                                                                                                companion5.getClass();
                                                                                                int i48 = DrawScope.Companion.DefaultFilterQuality;
                                                                                                Paint paintObtainStrokePaint = canvasDrawScope.obtainStrokePaint();
                                                                                                if (brush9 != null) {
                                                                                                    brush9.mo451applyToPq9zytI(floatValue, canvasDrawScope.mo547getSizeNHjbRc(), paintObtainStrokePaint);
                                                                                                } else {
                                                                                                    AndroidPaint androidPaint = (AndroidPaint) paintObtainStrokePaint;
                                                                                                    if (androidPaint.internalPaint.getAlpha() / 255.0f != floatValue) {
                                                                                                        androidPaint.setAlpha(floatValue);
                                                                                                    }
                                                                                                }
                                                                                                AndroidPaint androidPaint2 = (AndroidPaint) paintObtainStrokePaint;
                                                                                                if (!Intrinsics.areEqual(androidPaint2.internalColorFilter, (Object) null)) {
                                                                                                    androidPaint2.setColorFilter(null);
                                                                                                }
                                                                                                int i49 = androidPaint2._blendMode;
                                                                                                BlendMode.Companion companion6 = BlendMode.Companion;
                                                                                                if (i49 != i47) {
                                                                                                    androidPaint2.m439setBlendModes9anfk8(i47);
                                                                                                }
                                                                                                if (androidPaint2.internalPaint.getStrokeWidth() != fFloor) {
                                                                                                    androidPaint2.setStrokeWidth(fFloor);
                                                                                                }
                                                                                                if (androidPaint2.internalPaint.getStrokeMiter() != 4.0f) {
                                                                                                    androidPaint2.internalPaint.setStrokeMiter(4.0f);
                                                                                                }
                                                                                                int iM437getStrokeCapKaPHkGw = androidPaint2.m437getStrokeCapKaPHkGw();
                                                                                                StrokeCap.Companion companion7 = StrokeCap.Companion;
                                                                                                if (iM437getStrokeCapKaPHkGw != 0) {
                                                                                                    androidPaint2.m442setStrokeCapBeK7IIE(0);
                                                                                                }
                                                                                                if (androidPaint2.m438getStrokeJoinLxFBmk8() != 0) {
                                                                                                    androidPaint2.m443setStrokeJoinWw9F2mQ(0);
                                                                                                }
                                                                                                if (!Intrinsics.areEqual(androidPaint2.pathEffect, (Object) null)) {
                                                                                                    androidPaint2.setPathEffect(null);
                                                                                                }
                                                                                                int iM436getFilterQualityfv9h1I = androidPaint2.m436getFilterQualityfv9h1I();
                                                                                                FilterQuality.Companion companion8 = FilterQuality.Companion;
                                                                                                if (iM436getFilterQualityfv9h1I != i48) {
                                                                                                    androidPaint2.m441setFilterQualityvDHp3xo(i48);
                                                                                                }
                                                                                                canvas.mo431drawLineWko1d7g(jFloatToRawIntBits, jFloatToRawIntBits2, paintObtainStrokePaint);
                                                                                            }
                                                                                            return Unit.INSTANCE;
                                                                                        }
                                                                                    };
                                                                                    composerImpl6.updateRememberedValue(obj19);
                                                                                    objRememberedValue19 = obj19;
                                                                                }
                                                                                objDrawWithContent = DrawModifierKt.drawWithContent(modifier7, (Function1) objRememberedValue19);
                                                                                composerImpl6.end(false);
                                                                            }
                                                                        }
                                                                    } else {
                                                                        composerImpl6.startReplaceGroup(810474750);
                                                                        composerImpl6.end(false);
                                                                        objDrawWithContent = Modifier.Companion;
                                                                    }
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                    composerImpl6.end(false);
                                                                    return objDrawWithContent;
                                                                }
                                                            };
                                                            offsetMapping3 = offsetMapping2;
                                                            modifierComposed = ComposedModifierKt.composed(TextFieldMagnifier, InspectableValueKt.NoInspectorInfo, function36);
                                                            zChangedInstance6 = composerImpl2.changedInstance(textFieldSelectionManager11);
                                                            final Modifier modifier7 = modifierComposed;
                                                            Object objRememberedValue17 = composerImpl2.rememberedValue();
                                                            obj6 = objRememberedValue17;
                                                            if (zChangedInstance6 || objRememberedValue17 == composer$Companion$Empty$1) {
                                                                Function1 function111 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$3$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj16) {
                                                                        final TextFieldSelectionManager textFieldSelectionManager12 = textFieldSelectionManager11;
                                                                        return new DisposableEffectResult() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$3$1$invoke$$inlined$onDispose$1
                                                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                                                            public final void dispose() {
                                                                                textFieldSelectionManager12.hideSelectionToolbar$foundation_release();
                                                                            }
                                                                        };
                                                                    }
                                                                };
                                                                composerImpl2.updateRememberedValue(function111);
                                                                obj6 = function111;
                                                            }
                                                            EffectsKt.DisposableEffect(textFieldSelectionManager11, (Function1) obj6, composerImpl2);
                                                            zChangedInstance7 = composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changedInstance(textInputService3) | (i46 != 4) | ((i33 <= 32 && composerImpl2.changed(imeOptions8)) || (i31 & 48) == 32);
                                                            Object objRememberedValue18 = composerImpl2.rememberedValue();
                                                            obj7 = objRememberedValue18;
                                                            if (zChangedInstance7 || objRememberedValue18 == composer$Companion$Empty$1) {
                                                                Function1 function112 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$4$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    /* JADX WARN: Type inference failed for: r7v3, types: [T, androidx.compose.ui.text.input.TextInputSession, java.lang.Object] */
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj16) {
                                                                        if (legacyTextFieldState2.getHasFocus()) {
                                                                            LegacyTextFieldState legacyTextFieldState7 = legacyTextFieldState2;
                                                                            TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                            TextInputService textInputService7 = textInputService3;
                                                                            TextFieldValue textFieldValue3 = textFieldValue;
                                                                            EditProcessor editProcessor2 = legacyTextFieldState7.processor;
                                                                            ImeOptions imeOptions9 = imeOptions8;
                                                                            Function1 function113 = legacyTextFieldState7.onValueChange;
                                                                            Function1 function114 = legacyTextFieldState7.onImeActionPerformed;
                                                                            companion2.getClass();
                                                                            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                                                            TextFieldDelegate$Companion$restartInput$1 textFieldDelegate$Companion$restartInput$1 = new TextFieldDelegate$Companion$restartInput$1(editProcessor2, function113, ref$ObjectRef);
                                                                            PlatformTextInputService platformTextInputService = textInputService7.platformTextInputService;
                                                                            platformTextInputService.startInput(textFieldValue3, imeOptions9, textFieldDelegate$Companion$restartInput$1, function114);
                                                                            ?? textInputSession2 = new TextInputSession(textInputService7, platformTextInputService);
                                                                            textInputService7._currentInputSession.set(textInputSession2);
                                                                            ref$ObjectRef.element = textInputSession2;
                                                                            legacyTextFieldState7.inputSession = textInputSession2;
                                                                        }
                                                                        return new DisposableEffectResult() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$4$1$invoke$$inlined$onDispose$1
                                                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                                                            public final void dispose() {
                                                                            }
                                                                        };
                                                                    }
                                                                };
                                                                composerImpl2.updateRememberedValue(function112);
                                                                obj7 = function112;
                                                            }
                                                            EffectsKt.DisposableEffect(imeOptions8, (Function1) obj7, composerImpl2);
                                                            final Function1 function113 = legacyTextFieldState2.onValueChange;
                                                            boolean z40 = !z33;
                                                            final int i47 = imeOptions8.imeAction;
                                                            final OffsetMapping offsetMapping8 = offsetMapping3;
                                                            final boolean z41 = z40;
                                                            final LegacyTextFieldState legacyTextFieldState7 = legacyTextFieldState2;
                                                            Function3 function37 = new Function3() { // from class: androidx.compose.foundation.text.TextFieldKeyInputKt$textFieldKeyInput$2
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(3);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function3
                                                                public final Object invoke(Object obj16, Object obj17, Object obj18) {
                                                                    ((Number) obj18).intValue();
                                                                    ComposerImpl composerImpl6 = (ComposerImpl) ((Composer) obj17);
                                                                    composerImpl6.startReplaceGroup(851809892);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.foundation.text.textFieldKeyInput.<anonymous> (TextFieldKeyInput.kt:252)");
                                                                    }
                                                                    Object objRememberedValue19 = composerImpl6.rememberedValue();
                                                                    Composer.Companion.getClass();
                                                                    Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                                                                    if (objRememberedValue19 == composer$Companion$Empty$12) {
                                                                        objRememberedValue19 = new TextPreparedSelectionState();
                                                                        composerImpl6.updateRememberedValue(objRememberedValue19);
                                                                    }
                                                                    TextPreparedSelectionState textPreparedSelectionState = (TextPreparedSelectionState) objRememberedValue19;
                                                                    Object objRememberedValue20 = composerImpl6.rememberedValue();
                                                                    if (objRememberedValue20 == composer$Companion$Empty$12) {
                                                                        objRememberedValue20 = new DeadKeyCombiner();
                                                                        composerImpl6.updateRememberedValue(objRememberedValue20);
                                                                    }
                                                                    TextFieldKeyInput textFieldKeyInput = new TextFieldKeyInput(legacyTextFieldState7, textFieldSelectionManager11, textFieldValue, z35, z41, textPreparedSelectionState, offsetMapping8, undoManager, (DeadKeyCombiner) objRememberedValue20, null, function113, i47, 512, null);
                                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                                    boolean zChangedInstance10 = composerImpl6.changedInstance(textFieldKeyInput);
                                                                    Object objRememberedValue21 = composerImpl6.rememberedValue();
                                                                    if (zChangedInstance10 || objRememberedValue21 == composer$Companion$Empty$12) {
                                                                        objRememberedValue21 = new TextFieldKeyInputKt$textFieldKeyInput$2$1$1(textFieldKeyInput);
                                                                        composerImpl6.updateRememberedValue(objRememberedValue21);
                                                                    }
                                                                    Modifier modifierOnKeyEvent = KeyInputModifierKt.onKeyEvent(companion2, (Function1) ((KFunction) objRememberedValue21));
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                    composerImpl6.end(false);
                                                                    return modifierOnKeyEvent;
                                                                }
                                                            };
                                                            Function1 function114 = InspectableValueKt.NoInspectorInfo;
                                                            Modifier modifierComposed2 = ComposedModifierKt.composed(TextFieldMagnifier, function114, function37);
                                                            KeyboardType.Companion.getClass();
                                                            if (imeOptions8.keyboardType != KeyboardType.Password) {
                                                                z27 = false;
                                                                boolean zBooleanValue = ((Boolean) mutableState.getValue()).booleanValue();
                                                                zChanged = composerImpl2.changed(z27) | composerImpl2.changedInstance(legacyPlatformTextInputServiceAdapter);
                                                                Object objRememberedValue19 = composerImpl2.rememberedValue();
                                                                obj8 = objRememberedValue19;
                                                                if (!zChanged || objRememberedValue19 == composer$Companion$Empty$1) {
                                                                    Function0 function02 = new Function0() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$stylusHandwritingModifier$1$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(0);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function0
                                                                        public final Object invoke() {
                                                                            if (z27) {
                                                                                legacyPlatformTextInputServiceAdapter.startStylusHandwriting();
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    composerImpl2.updateRememberedValue(function02);
                                                                    obj8 = function02;
                                                                }
                                                                Modifier modifierStylusHandwriting = StylusHandwritingKt.stylusHandwriting(TextFieldMagnifier, zBooleanValue, z27, (Function0) obj8);
                                                                Brush brush7 = brush4;
                                                                j2 = ((Color) composerImpl2.consume(AutofillHighlightKt.LocalAutofillHighlightColor)).value;
                                                                zChangedInstance8 = composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changed(j2);
                                                                Object objRememberedValue20 = composerImpl2.rememberedValue();
                                                                obj9 = objRememberedValue20;
                                                                if (!zChangedInstance8 || objRememberedValue20 == composer$Companion$Empty$1) {
                                                                    Function1 function115 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$drawDecorationModifier$1$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj16) {
                                                                            DrawScope drawScope = (DrawScope) obj16;
                                                                            if (((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState2.autofillHighlightOn$delegate).getValue()).booleanValue() || ((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState2.justAutofilled$delegate).getValue()).booleanValue()) {
                                                                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j2, 0L, 0L, 0.0f, null, null, 0, 126);
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    composerImpl2.updateRememberedValue(function115);
                                                                    obj9 = function115;
                                                                }
                                                                Modifier modifierThen2 = LegacyAdaptingPlatformTextInputModifierNodeKt.legacyTextInputAdapter(modifier6.then(DrawModifierKt.drawBehind(TextFieldMagnifier, (Function1) obj9)), legacyPlatformTextInputServiceAdapter, legacyTextFieldState2, textFieldSelectionManager11).then(modifierStylusHandwriting).then(modifierFocusable);
                                                                final FocusManager focusManager5 = focusManager2;
                                                                final boolean z42 = z26;
                                                                Modifier modifierOnGloballyPositioned2 = OnGloballyPositionedModifierKt.onGloballyPositioned(ComposedModifierKt.composed(KeyInputModifierKt.onPreviewKeyEvent(KeyInputModifierKt.onPreviewKeyEvent(modifierThen2, new Function1() { // from class: androidx.compose.foundation.text.TextFieldFocusModifier_androidKt$interceptDPadAndMoveFocus$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj16) {
                                                                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj16).nativeKeyEvent;
                                                                        InputDevice device = keyEvent.getDevice();
                                                                        boolean zM375moveFocus3ESFkO8 = false;
                                                                        if (device != null && device.supportsSource(513) && !device.isVirtual()) {
                                                                            int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                                                            KeyEventType.Companion.getClass();
                                                                            if (iM581getTypeZmokQxo == KeyEventType.KeyDown && keyEvent.getSource() != 257) {
                                                                                if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(19, keyEvent)) {
                                                                                    FocusManager focusManager6 = focusManager5;
                                                                                    FocusDirection.Companion.getClass();
                                                                                    zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager6).m375moveFocus3ESFkO8(FocusDirection.Up);
                                                                                } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(20, keyEvent)) {
                                                                                    FocusManager focusManager7 = focusManager5;
                                                                                    FocusDirection.Companion.getClass();
                                                                                    zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager7).m375moveFocus3ESFkO8(FocusDirection.Down);
                                                                                } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(21, keyEvent)) {
                                                                                    FocusManager focusManager8 = focusManager5;
                                                                                    FocusDirection.Companion.getClass();
                                                                                    zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager8).m375moveFocus3ESFkO8(FocusDirection.Left);
                                                                                } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(22, keyEvent)) {
                                                                                    FocusManager focusManager9 = focusManager5;
                                                                                    FocusDirection.Companion.getClass();
                                                                                    zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager9).m375moveFocus3ESFkO8(FocusDirection.Right);
                                                                                } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(23, keyEvent)) {
                                                                                    SoftwareKeyboardController softwareKeyboardController2 = legacyTextFieldState2.keyboardController;
                                                                                    if (softwareKeyboardController2 != null) {
                                                                                        ((DelegatingSoftwareKeyboardController) softwareKeyboardController2).show();
                                                                                    }
                                                                                    zM375moveFocus3ESFkO8 = true;
                                                                                }
                                                                            }
                                                                        }
                                                                        return Boolean.valueOf(zM375moveFocus3ESFkO8);
                                                                    }
                                                                }), new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$previewKeyEventToDeselectOnBack$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    /*
                                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                                    */
                                                                    public final Object mo781invoke(Object obj16) {
                                                                        boolean z43;
                                                                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj16).nativeKeyEvent;
                                                                        if (legacyTextFieldState2.getHandleState() == HandleState.Selection && keyEvent.getKeyCode() == 4) {
                                                                            int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                                                            KeyEventType.Companion.getClass();
                                                                            if (iM581getTypeZmokQxo == KeyEventType.KeyUp) {
                                                                                textFieldSelectionManager11.m239deselect_kEHs6E$foundation_release(null);
                                                                                z43 = true;
                                                                            }
                                                                        } else {
                                                                            z43 = false;
                                                                        }
                                                                        return Boolean.valueOf(z43);
                                                                    }
                                                                }).then(modifierComposed2), function114, new Function3() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(3);
                                                                    }

                                                                    /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
                                                                    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
                                                                    @Override // kotlin.jvm.functions.Function3
                                                                    /*
                                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                                    */
                                                                    public final Object invoke(Object obj16, Object obj17, Object obj18) {
                                                                        ((Number) obj18).intValue();
                                                                        ComposerImpl composerImpl6 = (ComposerImpl) ((Composer) obj17);
                                                                        composerImpl6.startReplaceGroup(805428266);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.textFieldScrollable.<anonymous> (TextFieldScroll.kt:71)");
                                                                        }
                                                                        boolean z43 = ((Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition5.orientation$delegate).getValue()) == Orientation.Vertical || !(composerImpl6.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl);
                                                                        boolean zChanged5 = composerImpl6.changed(textFieldScrollerPosition5);
                                                                        final TextFieldScrollerPosition textFieldScrollerPosition6 = textFieldScrollerPosition5;
                                                                        Object objRememberedValue21 = composerImpl6.rememberedValue();
                                                                        Composer.Companion companion2 = Composer.Companion;
                                                                        if (!zChanged5) {
                                                                            companion2.getClass();
                                                                            if (objRememberedValue21 == Composer.Companion.Empty) {
                                                                                objRememberedValue21 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$scrollableState$1$1
                                                                                    {
                                                                                        super(1);
                                                                                    }

                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                    /* renamed from: invoke */
                                                                                    public final Object mo781invoke(Object obj19) {
                                                                                        float fFloatValue = ((Number) obj19).floatValue();
                                                                                        float floatValue = ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.offset$delegate).getFloatValue() + fFloatValue;
                                                                                        if (floatValue > ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.maximum$delegate).getFloatValue()) {
                                                                                            fFloatValue = ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.maximum$delegate).getFloatValue() - ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.offset$delegate).getFloatValue();
                                                                                        } else if (floatValue < 0.0f) {
                                                                                            fFloatValue = -((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.offset$delegate).getFloatValue();
                                                                                        }
                                                                                        TextFieldScrollerPosition textFieldScrollerPosition7 = textFieldScrollerPosition6;
                                                                                        ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).setFloatValue(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).getFloatValue() + fFloatValue);
                                                                                        return Float.valueOf(fFloatValue);
                                                                                    }
                                                                                };
                                                                                composerImpl6.updateRememberedValue(objRememberedValue21);
                                                                            }
                                                                        }
                                                                        final ScrollableState scrollableStateRememberScrollableState = ScrollableStateKt.rememberScrollableState(composerImpl6, (Function1) objRememberedValue21);
                                                                        boolean zChanged6 = composerImpl6.changed(scrollableStateRememberScrollableState) | composerImpl6.changed(textFieldScrollerPosition5);
                                                                        final TextFieldScrollerPosition textFieldScrollerPosition7 = textFieldScrollerPosition5;
                                                                        Object objRememberedValue22 = composerImpl6.rememberedValue();
                                                                        if (!zChanged6) {
                                                                            companion2.getClass();
                                                                            if (objRememberedValue22 == Composer.Companion.Empty) {
                                                                                objRememberedValue22 = new ScrollableState(textFieldScrollerPosition7) { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1
                                                                                    public final State canScrollBackward$delegate;
                                                                                    public final State canScrollForward$delegate;

                                                                                    {
                                                                                        this.canScrollForward$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1$canScrollForward$2
                                                                                            {
                                                                                                super(0);
                                                                                            }

                                                                                            @Override // kotlin.jvm.functions.Function0
                                                                                            public final Object invoke() {
                                                                                                return Boolean.valueOf(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).getFloatValue() < ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.maximum$delegate).getFloatValue());
                                                                                            }
                                                                                        });
                                                                                        this.canScrollBackward$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1$canScrollBackward$2
                                                                                            {
                                                                                                super(0);
                                                                                            }

                                                                                            @Override // kotlin.jvm.functions.Function0
                                                                                            public final Object invoke() {
                                                                                                return Boolean.valueOf(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).getFloatValue() > 0.0f);
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                    public final float dispatchRawDelta(float f) {
                                                                                        return this.$$delegate_0.dispatchRawDelta(f);
                                                                                    }

                                                                                    @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                    public final boolean getCanScrollBackward() {
                                                                                        return ((Boolean) this.canScrollBackward$delegate.getValue()).booleanValue();
                                                                                    }

                                                                                    @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                    public final boolean getCanScrollForward() {
                                                                                        return ((Boolean) this.canScrollForward$delegate.getValue()).booleanValue();
                                                                                    }

                                                                                    @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                    public final boolean isScrollInProgress() {
                                                                                        return this.$$delegate_0.isScrollInProgress();
                                                                                    }

                                                                                    @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                    public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
                                                                                        return this.$$delegate_0.scroll(mutatePriority, function2, continuation);
                                                                                    }
                                                                                };
                                                                                composerImpl6.updateRememberedValue(objRememberedValue22);
                                                                            }
                                                                        }
                                                                        Modifier modifierScrollable$default = ScrollableKt.scrollable$default(Modifier.Companion, (TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1) objRememberedValue22, (Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition5.orientation$delegate).getValue(), z42 && ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition5.maximum$delegate).getFloatValue() != 0.0f, z43, mutableInteractionSource5, 16);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                        composerImpl6.end(false);
                                                                        return modifierScrollable$default;
                                                                    }
                                                                }).then(modifierThen).then(coreTextFieldSemanticsModifier2), new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$decorationBoxModifier$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj16) {
                                                                        LayoutCoordinates layoutCoordinates = (LayoutCoordinates) obj16;
                                                                        TextLayoutResultProxy layoutResult = legacyTextFieldState2.getLayoutResult();
                                                                        if (layoutResult != null) {
                                                                            layoutResult.decorationBoxCoordinates = layoutCoordinates;
                                                                        }
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                });
                                                                z28 = !z42 && legacyTextFieldState2.getHasFocus() && ((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState2.isInTouchMode$delegate).getValue()).booleanValue() && windowInfo.isWindowFocused();
                                                                if (z28) {
                                                                    TextFieldMagnifier = TextFieldSelectionManager_androidKt.textFieldMagnifier(TextFieldMagnifier, textFieldSelectionManager11);
                                                                }
                                                                final Modifier modifier8 = TextFieldMagnifier;
                                                                ComposerImpl composerImpl6 = composerImpl2;
                                                                final LegacyTextFieldState legacyTextFieldState8 = legacyTextFieldState2;
                                                                final BringIntoViewRequester bringIntoViewRequester3 = bringIntoViewRequester;
                                                                final VisualTransformation visualTransformation8 = visualTransformation6;
                                                                final boolean z43 = z28;
                                                                final TextStyle textStyle10 = textStyle8;
                                                                final int i48 = z30 ? 1 : 0;
                                                                final int i49 = z33 ? 1 : 0;
                                                                CoreTextFieldRootBox(modifierOnGloballyPositioned2, textFieldSelectionManager11, ComposableLambdaKt.rememberComposableLambda(-492537660, new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(2);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function2
                                                                    public final Object invoke(Object obj16, Object obj17) {
                                                                        Composer composer2 = (Composer) obj16;
                                                                        int iIntValue = ((Number) obj17).intValue();
                                                                        ComposerImpl composerImpl7 = (ComposerImpl) composer2;
                                                                        if (composerImpl7.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField.<anonymous> (CoreTextField.kt:556)");
                                                                            }
                                                                            Function3 function38 = function35;
                                                                            final LegacyTextFieldState legacyTextFieldState9 = legacyTextFieldState8;
                                                                            final TextStyle textStyle11 = textStyle10;
                                                                            final int i50 = i48;
                                                                            final int i51 = i49;
                                                                            final TextFieldScrollerPosition textFieldScrollerPosition6 = textFieldScrollerPosition5;
                                                                            final TextFieldValue textFieldValue3 = textFieldValue;
                                                                            final VisualTransformation visualTransformation9 = visualTransformation8;
                                                                            final Modifier modifier9 = modifier7;
                                                                            final Modifier modifier10 = modifierDrawBehind;
                                                                            final Modifier modifier11 = modifierOnGloballyPositioned;
                                                                            final Modifier modifier12 = modifier8;
                                                                            final BringIntoViewRequester bringIntoViewRequester4 = bringIntoViewRequester3;
                                                                            final TextFieldSelectionManager textFieldSelectionManager12 = textFieldSelectionManager11;
                                                                            final boolean z44 = z43;
                                                                            final boolean z45 = z19;
                                                                            final Function1 function116 = function15;
                                                                            final OffsetMapping offsetMapping9 = offsetMapping8;
                                                                            final Density density4 = density3;
                                                                            function38.invoke(ComposableLambdaKt.rememberComposableLambda(-1835647873, new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5.1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(2);
                                                                                }

                                                                                /* JADX WARN: Removed duplicated region for block: B:15:0x006e  */
                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                /*
                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                */
                                                                                public final Object invoke(Object obj18, Object obj19) {
                                                                                    Modifier verticalScrollLayoutModifier;
                                                                                    Composer composer3 = (Composer) obj18;
                                                                                    int iIntValue2 = ((Number) obj19).intValue();
                                                                                    ComposerImpl composerImpl8 = (ComposerImpl) composer3;
                                                                                    if (composerImpl8.shouldExecute(iIntValue2 & 1, (iIntValue2 & 3) != 2)) {
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField.<anonymous>.<anonymous> (CoreTextField.kt:559)");
                                                                                        }
                                                                                        Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(Modifier.Companion, ((Dp) ((SnapshotMutableStateImpl) legacyTextFieldState9.minHeightForSingleLineField$delegate).getValue()).value, 0.0f, 2);
                                                                                        final TextStyle textStyle12 = textStyle11;
                                                                                        final int i52 = i50;
                                                                                        final int i53 = i51;
                                                                                        Function1 function117 = InspectableValueKt.NoInspectorInfo;
                                                                                        Modifier modifierComposed3 = ComposedModifierKt.composed(modifierM133heightInVpY3zN4$default, function117, new Function3() { // from class: androidx.compose.foundation.text.HeightInLinesModifierKt$heightInLines$2
                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                            {
                                                                                                super(3);
                                                                                            }

                                                                                            /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
                                                                                            /* JADX WARN: Removed duplicated region for block: B:23:0x009a  */
                                                                                            /* JADX WARN: Removed duplicated region for block: B:41:0x0104  */
                                                                                            /* JADX WARN: Removed duplicated region for block: B:46:0x014a  */
                                                                                            @Override // kotlin.jvm.functions.Function3
                                                                                            /*
                                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                                            */
                                                                                            public final Object invoke(Object obj20, Object obj21, Object obj22) {
                                                                                                int i54;
                                                                                                int i55;
                                                                                                long j7;
                                                                                                float fMo55toDpu2uoSUM;
                                                                                                float fMo55toDpu2uoSUM2;
                                                                                                ((Number) obj22).intValue();
                                                                                                ComposerImpl composerImpl9 = (ComposerImpl) ((Composer) obj21);
                                                                                                composerImpl9.startReplaceGroup(408240218);
                                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.heightInLines.<anonymous> (HeightInLinesModifier.kt:62)");
                                                                                                }
                                                                                                HeightInLinesModifierKt.validateMinMaxLines(i52, i53);
                                                                                                if (i52 == 1 && i53 == Integer.MAX_VALUE) {
                                                                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                                        ComposerKt.traceEventEnd();
                                                                                                    }
                                                                                                    composerImpl9.end(false);
                                                                                                    return companion2;
                                                                                                }
                                                                                                Density density5 = (Density) composerImpl9.consume(CompositionLocalsKt.LocalDensity);
                                                                                                FontFamily.Resolver resolver3 = (FontFamily.Resolver) composerImpl9.consume(CompositionLocalsKt.LocalFontFamilyResolver);
                                                                                                LayoutDirection layoutDirection = (LayoutDirection) composerImpl9.consume(CompositionLocalsKt.LocalLayoutDirection);
                                                                                                boolean zChanged5 = composerImpl9.changed(textStyle12) | composerImpl9.changed(layoutDirection);
                                                                                                TextStyle textStyle13 = textStyle12;
                                                                                                Object objRememberedValue21 = composerImpl9.rememberedValue();
                                                                                                Composer.Companion companion3 = Composer.Companion;
                                                                                                if (!zChanged5) {
                                                                                                    companion3.getClass();
                                                                                                    if (objRememberedValue21 == Composer.Companion.Empty) {
                                                                                                        objRememberedValue21 = TextStyleKt.resolveDefaults(textStyle13, layoutDirection);
                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue21);
                                                                                                    }
                                                                                                }
                                                                                                TextStyle textStyle14 = (TextStyle) objRememberedValue21;
                                                                                                boolean zChanged6 = composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle14);
                                                                                                Object objRememberedValue22 = composerImpl9.rememberedValue();
                                                                                                if (!zChanged6) {
                                                                                                    companion3.getClass();
                                                                                                    if (objRememberedValue22 == Composer.Companion.Empty) {
                                                                                                        SpanStyle spanStyle = textStyle14.spanStyle;
                                                                                                        FontFamily fontFamily = spanStyle.fontFamily;
                                                                                                        FontWeight fontWeight = spanStyle.fontWeight;
                                                                                                        if (fontWeight == null) {
                                                                                                            FontWeight.Companion.getClass();
                                                                                                            fontWeight = FontWeight.Normal;
                                                                                                        }
                                                                                                        SpanStyle spanStyle2 = textStyle14.spanStyle;
                                                                                                        FontStyle fontStyle = spanStyle2.fontStyle;
                                                                                                        if (fontStyle != null) {
                                                                                                            i54 = fontStyle.value;
                                                                                                        } else {
                                                                                                            FontStyle.Companion.getClass();
                                                                                                            i54 = 0;
                                                                                                        }
                                                                                                        FontSynthesis fontSynthesis = spanStyle2.fontSynthesis;
                                                                                                        if (fontSynthesis != null) {
                                                                                                            i55 = fontSynthesis.value;
                                                                                                        } else {
                                                                                                            FontSynthesis.Companion.getClass();
                                                                                                            i55 = FontSynthesis.All;
                                                                                                        }
                                                                                                        objRememberedValue22 = ((FontFamilyResolverImpl) resolver3).m764resolveDPcqOEQ(fontFamily, fontWeight, i54, i55);
                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue22);
                                                                                                    }
                                                                                                }
                                                                                                State state = (State) objRememberedValue22;
                                                                                                boolean zChanged7 = composerImpl9.changed(state.getValue()) | composerImpl9.changed(density5) | composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle12) | composerImpl9.changed(layoutDirection);
                                                                                                Object objRememberedValue23 = composerImpl9.rememberedValue();
                                                                                                if (!zChanged7) {
                                                                                                    companion3.getClass();
                                                                                                    if (objRememberedValue23 == Composer.Companion.Empty) {
                                                                                                        j7 = 4294967295L;
                                                                                                        objRememberedValue23 = Integer.valueOf((int) (TextFieldDelegateKt.computeSizeForDefaultText(textStyle14, density5, resolver3, TextFieldDelegateKt.EmptyTextReplacement, 1) & 4294967295L));
                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue23);
                                                                                                    } else {
                                                                                                        j7 = 4294967295L;
                                                                                                    }
                                                                                                }
                                                                                                int iIntValue3 = ((Number) objRememberedValue23).intValue();
                                                                                                boolean zChanged8 = composerImpl9.changed(layoutDirection) | composerImpl9.changed(density5) | composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle12) | composerImpl9.changed(state.getValue());
                                                                                                Object objRememberedValue24 = composerImpl9.rememberedValue();
                                                                                                if (!zChanged8) {
                                                                                                    companion3.getClass();
                                                                                                    if (objRememberedValue24 == Composer.Companion.Empty) {
                                                                                                        StringBuilder sb = new StringBuilder();
                                                                                                        String str2 = TextFieldDelegateKt.EmptyTextReplacement;
                                                                                                        sb.append(str2);
                                                                                                        sb.append('\n');
                                                                                                        sb.append(str2);
                                                                                                        objRememberedValue24 = Integer.valueOf((int) (TextFieldDelegateKt.computeSizeForDefaultText(textStyle14, density5, resolver3, sb.toString(), 2) & j7));
                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue24);
                                                                                                    }
                                                                                                }
                                                                                                int iIntValue4 = ((Number) objRememberedValue24).intValue() - iIntValue3;
                                                                                                int i56 = i52;
                                                                                                Integer numValueOf = i56 == 1 ? null : Integer.valueOf(((i56 - 1) * iIntValue4) + iIntValue3);
                                                                                                int i57 = i53;
                                                                                                Integer numValueOf2 = i57 != Integer.MAX_VALUE ? Integer.valueOf(((i57 - 1) * iIntValue4) + iIntValue3) : null;
                                                                                                Modifier.Companion companion4 = Modifier.Companion;
                                                                                                if (numValueOf != null) {
                                                                                                    fMo55toDpu2uoSUM = density5.mo55toDpu2uoSUM(numValueOf.intValue());
                                                                                                } else {
                                                                                                    Dp.Companion.getClass();
                                                                                                    fMo55toDpu2uoSUM = Dp.Unspecified;
                                                                                                }
                                                                                                if (numValueOf2 != null) {
                                                                                                    fMo55toDpu2uoSUM2 = density5.mo55toDpu2uoSUM(numValueOf2.intValue());
                                                                                                } else {
                                                                                                    Dp.Companion.getClass();
                                                                                                    fMo55toDpu2uoSUM2 = Dp.Unspecified;
                                                                                                }
                                                                                                Modifier modifierM132heightInVpY3zN4 = SizeKt.m132heightInVpY3zN4(companion4, fMo55toDpu2uoSUM, fMo55toDpu2uoSUM2);
                                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                                    ComposerKt.traceEventEnd();
                                                                                                }
                                                                                                composerImpl9.end(false);
                                                                                                return modifierM132heightInVpY3zN4;
                                                                                            }
                                                                                        });
                                                                                        TextFieldScrollerPosition textFieldScrollerPosition7 = textFieldScrollerPosition6;
                                                                                        TextFieldValue textFieldValue4 = textFieldValue3;
                                                                                        VisualTransformation visualTransformation10 = visualTransformation9;
                                                                                        boolean zChangedInstance10 = composerImpl8.changedInstance(legacyTextFieldState9);
                                                                                        final LegacyTextFieldState legacyTextFieldState10 = legacyTextFieldState9;
                                                                                        Object objRememberedValue21 = composerImpl8.rememberedValue();
                                                                                        if (!zChangedInstance10) {
                                                                                            Composer.Companion.getClass();
                                                                                            if (objRememberedValue21 == Composer.Companion.Empty) {
                                                                                                objRememberedValue21 = new Function0() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$5$1$coreTextFieldModifier$1$1
                                                                                                    {
                                                                                                        super(0);
                                                                                                    }

                                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                                    public final Object invoke() {
                                                                                                        return legacyTextFieldState10.getLayoutResult();
                                                                                                    }
                                                                                                };
                                                                                                composerImpl8.updateRememberedValue(objRememberedValue21);
                                                                                            }
                                                                                            Function0 function03 = (Function0) objRememberedValue21;
                                                                                            Orientation orientation2 = (Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition7.orientation$delegate).getValue();
                                                                                            long j7 = textFieldValue4.selection;
                                                                                            TextRange.Companion companion2 = TextRange.Companion;
                                                                                            int iM752getMinimpl = (int) (j7 >> 32);
                                                                                            long j8 = textFieldScrollerPosition7.previousSelection;
                                                                                            if (iM752getMinimpl == ((int) (j8 >> 32)) && (iM752getMinimpl = (int) (j7 & 4294967295L)) == ((int) (j8 & 4294967295L))) {
                                                                                                iM752getMinimpl = TextRange.m752getMinimpl(j7);
                                                                                            }
                                                                                            textFieldScrollerPosition7.previousSelection = textFieldValue4.selection;
                                                                                            TransformedText transformedTextFilterWithValidation2 = ValidatingOffsetMappingKt.filterWithValidation(visualTransformation10, textFieldValue4.annotatedString);
                                                                                            int i54 = TextFieldScrollKt.WhenMappings.$EnumSwitchMapping$0[orientation2.ordinal()];
                                                                                            if (i54 == 1) {
                                                                                                verticalScrollLayoutModifier = new VerticalScrollLayoutModifier(textFieldScrollerPosition7, iM752getMinimpl, transformedTextFilterWithValidation2, function03);
                                                                                            } else {
                                                                                                if (i54 != 2) {
                                                                                                    throw new NoWhenBranchMatchedException();
                                                                                                }
                                                                                                verticalScrollLayoutModifier = new HorizontalScrollLayoutModifier(textFieldScrollerPosition7, iM752getMinimpl, transformedTextFilterWithValidation2, function03);
                                                                                            }
                                                                                            Modifier modifierThen3 = ClipKt.clipToBounds(modifierComposed3).then(verticalScrollLayoutModifier).then(modifier9).then(modifier10);
                                                                                            final TextStyle textStyle13 = textStyle11;
                                                                                            Modifier modifierBringIntoViewRequester = BringIntoViewRequesterKt.bringIntoViewRequester(ComposedModifierKt.composed(modifierThen3, function117, new Function3() { // from class: androidx.compose.foundation.text.TextFieldSizeKt$textFieldMinSize$1
                                                                                                {
                                                                                                    super(3);
                                                                                                }

                                                                                                /* JADX WARN: Removed duplicated region for block: B:14:0x0074  */
                                                                                                /* JADX WARN: Removed duplicated region for block: B:9:0x0053  */
                                                                                                @Override // kotlin.jvm.functions.Function3
                                                                                                /*
                                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                                */
                                                                                                public final Object invoke(Object obj20, Object obj21, Object obj22) {
                                                                                                    int i55;
                                                                                                    int i56;
                                                                                                    ((Number) obj22).intValue();
                                                                                                    ComposerImpl composerImpl9 = (ComposerImpl) ((Composer) obj21);
                                                                                                    composerImpl9.startReplaceGroup(1582736677);
                                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                                        ComposerKt.traceEventStart("androidx.compose.foundation.text.textFieldMinSize.<anonymous> (TextFieldSize.kt:37)");
                                                                                                    }
                                                                                                    Density density5 = (Density) composerImpl9.consume(CompositionLocalsKt.LocalDensity);
                                                                                                    FontFamily.Resolver resolver3 = (FontFamily.Resolver) composerImpl9.consume(CompositionLocalsKt.LocalFontFamilyResolver);
                                                                                                    LayoutDirection layoutDirection = (LayoutDirection) composerImpl9.consume(CompositionLocalsKt.LocalLayoutDirection);
                                                                                                    boolean zChanged5 = composerImpl9.changed(textStyle13) | composerImpl9.changed(layoutDirection);
                                                                                                    TextStyle textStyle14 = textStyle13;
                                                                                                    Object objRememberedValue22 = composerImpl9.rememberedValue();
                                                                                                    Composer.Companion companion3 = Composer.Companion;
                                                                                                    if (!zChanged5) {
                                                                                                        companion3.getClass();
                                                                                                        if (objRememberedValue22 == Composer.Companion.Empty) {
                                                                                                            objRememberedValue22 = TextStyleKt.resolveDefaults(textStyle14, layoutDirection);
                                                                                                            composerImpl9.updateRememberedValue(objRememberedValue22);
                                                                                                        }
                                                                                                    }
                                                                                                    TextStyle textStyle15 = (TextStyle) objRememberedValue22;
                                                                                                    boolean zChanged6 = composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle15);
                                                                                                    Object objRememberedValue23 = composerImpl9.rememberedValue();
                                                                                                    if (!zChanged6) {
                                                                                                        companion3.getClass();
                                                                                                        if (objRememberedValue23 == Composer.Companion.Empty) {
                                                                                                            SpanStyle spanStyle = textStyle15.spanStyle;
                                                                                                            FontFamily fontFamily = spanStyle.fontFamily;
                                                                                                            FontWeight fontWeight = spanStyle.fontWeight;
                                                                                                            if (fontWeight == null) {
                                                                                                                FontWeight.Companion.getClass();
                                                                                                                fontWeight = FontWeight.Normal;
                                                                                                            }
                                                                                                            SpanStyle spanStyle2 = textStyle15.spanStyle;
                                                                                                            FontStyle fontStyle = spanStyle2.fontStyle;
                                                                                                            if (fontStyle != null) {
                                                                                                                i55 = fontStyle.value;
                                                                                                            } else {
                                                                                                                FontStyle.Companion.getClass();
                                                                                                                i55 = 0;
                                                                                                            }
                                                                                                            FontSynthesis fontSynthesis = spanStyle2.fontSynthesis;
                                                                                                            if (fontSynthesis != null) {
                                                                                                                i56 = fontSynthesis.value;
                                                                                                            } else {
                                                                                                                FontSynthesis.Companion.getClass();
                                                                                                                i56 = FontSynthesis.All;
                                                                                                            }
                                                                                                            objRememberedValue23 = ((FontFamilyResolverImpl) resolver3).m764resolveDPcqOEQ(fontFamily, fontWeight, i55, i56);
                                                                                                            composerImpl9.updateRememberedValue(objRememberedValue23);
                                                                                                        }
                                                                                                    }
                                                                                                    State state = (State) objRememberedValue23;
                                                                                                    TextStyle textStyle16 = textStyle13;
                                                                                                    Object objRememberedValue24 = composerImpl9.rememberedValue();
                                                                                                    companion3.getClass();
                                                                                                    Object obj23 = Composer.Companion.Empty;
                                                                                                    if (objRememberedValue24 == obj23) {
                                                                                                        objRememberedValue24 = new TextFieldSize(layoutDirection, density5, resolver3, textStyle16, state.getValue());
                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue24);
                                                                                                    }
                                                                                                    final TextFieldSize textFieldSize = (TextFieldSize) objRememberedValue24;
                                                                                                    Object value = state.getValue();
                                                                                                    if (layoutDirection != textFieldSize.layoutDirection || !Intrinsics.areEqual(density5, textFieldSize.density) || !Intrinsics.areEqual(resolver3, textFieldSize.fontFamilyResolver) || !Intrinsics.areEqual(textStyle15, textFieldSize.resolvedStyle) || !Intrinsics.areEqual(value, textFieldSize.typeface)) {
                                                                                                        textFieldSize.layoutDirection = layoutDirection;
                                                                                                        textFieldSize.density = density5;
                                                                                                        textFieldSize.fontFamilyResolver = resolver3;
                                                                                                        textFieldSize.resolvedStyle = textStyle15;
                                                                                                        textFieldSize.typeface = value;
                                                                                                        textFieldSize.minSize = TextFieldDelegateKt.computeSizeForDefaultText(textStyle15, density5, resolver3, TextFieldDelegateKt.EmptyTextReplacement, 1);
                                                                                                    }
                                                                                                    Modifier.Companion companion4 = Modifier.Companion;
                                                                                                    boolean zChangedInstance11 = composerImpl9.changedInstance(textFieldSize);
                                                                                                    Object objRememberedValue25 = composerImpl9.rememberedValue();
                                                                                                    if (zChangedInstance11 || objRememberedValue25 == obj23) {
                                                                                                        objRememberedValue25 = new Function3() { // from class: androidx.compose.foundation.text.TextFieldSizeKt$textFieldMinSize$1$1$1
                                                                                                            {
                                                                                                                super(3);
                                                                                                            }

                                                                                                            @Override // kotlin.jvm.functions.Function3
                                                                                                            public final Object invoke(Object obj24, Object obj25, Object obj26) {
                                                                                                                long j9 = ((Constraints) obj26).value;
                                                                                                                long j10 = textFieldSize.minSize;
                                                                                                                final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj25).mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j9, RangesKt___RangesKt.coerceIn((int) (j10 >> 32), Constraints.m825getMinWidthimpl(j9), Constraints.m823getMaxWidthimpl(j9)), 0, RangesKt___RangesKt.coerceIn((int) (j10 & 4294967295L), Constraints.m824getMinHeightimpl(j9), Constraints.m822getMaxHeightimpl(j9)), 0, 10));
                                                                                                                return ((MeasureScope) obj24).layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.text.TextFieldSizeKt$textFieldMinSize$1$1$1.1
                                                                                                                    {
                                                                                                                        super(1);
                                                                                                                    }

                                                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                                                    /* renamed from: invoke */
                                                                                                                    public final Object mo781invoke(Object obj27) {
                                                                                                                        ((Placeable.PlacementScope) obj27).placeRelative(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                                                                                                                        return Unit.INSTANCE;
                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        };
                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue25);
                                                                                                    }
                                                                                                    Modifier modifierLayout = LayoutModifierKt.layout(companion4, (Function3) objRememberedValue25);
                                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                                        ComposerKt.traceEventEnd();
                                                                                                    }
                                                                                                    composerImpl9.end(false);
                                                                                                    return modifierLayout;
                                                                                                }
                                                                                            }).then(modifier11).then(modifier12), bringIntoViewRequester4);
                                                                                            final TextFieldSelectionManager textFieldSelectionManager13 = textFieldSelectionManager12;
                                                                                            final LegacyTextFieldState legacyTextFieldState11 = legacyTextFieldState9;
                                                                                            final boolean z46 = z44;
                                                                                            final boolean z47 = z45;
                                                                                            final Function1 function118 = function116;
                                                                                            final TextFieldValue textFieldValue5 = textFieldValue3;
                                                                                            final OffsetMapping offsetMapping10 = offsetMapping9;
                                                                                            final Density density5 = density4;
                                                                                            final int i55 = i51;
                                                                                            SimpleLayoutKt.SimpleLayout(modifierBringIntoViewRequester, ComposableLambdaKt.rememberComposableLambda(-1172467467, new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5.1.1
                                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                {
                                                                                                    super(2);
                                                                                                }

                                                                                                /* JADX WARN: Removed duplicated region for block: B:31:0x00af  */
                                                                                                /* JADX WARN: Removed duplicated region for block: B:39:0x00d4  */
                                                                                                /* JADX WARN: Removed duplicated region for block: B:42:0x00e3  */
                                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                                /*
                                                                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                                                                */
                                                                                                public final Object invoke(Object obj20, Object obj21) throws Throwable {
                                                                                                    Composer composer4 = (Composer) obj20;
                                                                                                    int iIntValue3 = ((Number) obj21).intValue();
                                                                                                    boolean z48 = true;
                                                                                                    ComposerImpl composerImpl9 = (ComposerImpl) composer4;
                                                                                                    if (composerImpl9.shouldExecute(iIntValue3 & 1, (iIntValue3 & 3) != 2)) {
                                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField.<anonymous>.<anonymous>.<anonymous> (CoreTextField.kt:580)");
                                                                                                        }
                                                                                                        final LegacyTextFieldState legacyTextFieldState12 = legacyTextFieldState11;
                                                                                                        final Function1 function119 = function118;
                                                                                                        final TextFieldValue textFieldValue6 = textFieldValue5;
                                                                                                        final OffsetMapping offsetMapping11 = offsetMapping10;
                                                                                                        final Density density6 = density5;
                                                                                                        final int i56 = i55;
                                                                                                        MeasurePolicy measurePolicy = new MeasurePolicy() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5.1.1.2
                                                                                                            @Override // androidx.compose.ui.layout.MeasurePolicy
                                                                                                            public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i57) {
                                                                                                                LegacyTextFieldState legacyTextFieldState13 = legacyTextFieldState12;
                                                                                                                legacyTextFieldState13.textDelegate.layoutIntrinsics(intrinsicMeasureScope.getLayoutDirection());
                                                                                                                MultiParagraphIntrinsics multiParagraphIntrinsics = legacyTextFieldState13.textDelegate.paragraphIntrinsics;
                                                                                                                if (multiParagraphIntrinsics != null) {
                                                                                                                    return TextDelegateKt.ceilToIntPx(multiParagraphIntrinsics.getMaxIntrinsicWidth());
                                                                                                                }
                                                                                                                throw new IllegalStateException("layoutIntrinsics must be called first");
                                                                                                            }

                                                                                                            /* JADX WARN: Code restructure failed: missing block: B:45:0x00c0, code lost:
                                                                                                            
                                                                                                                if (androidx.compose.ui.unit.Constraints.m822getMaxHeightimpl(r37) == androidx.compose.ui.unit.Constraints.m822getMaxHeightimpl(r6)) goto L46;
                                                                                                             */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:42:0x00ae  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:49:0x0119  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:52:0x0128  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:54:0x0131  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:57:0x013c  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:62:0x014d  */
                                                                                                            @Override // androidx.compose.ui.layout.MeasurePolicy
                                                                                                            /* renamed from: measure-3p2s80s */
                                                                                                            /*
                                                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                                                            */
                                                                                                            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j9) {
                                                                                                                long j10;
                                                                                                                TextLayoutResult textLayoutResult;
                                                                                                                TextLayoutResultProxy textLayoutResultProxy;
                                                                                                                boolean z49;
                                                                                                                TextLayoutResult textLayoutResult2;
                                                                                                                AnonymousClass2 anonymousClass2;
                                                                                                                Snapshot.Companion companion3 = Snapshot.Companion;
                                                                                                                LegacyTextFieldState legacyTextFieldState13 = legacyTextFieldState12;
                                                                                                                companion3.getClass();
                                                                                                                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                                                                                                Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                                                                                                                Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                                                                                                try {
                                                                                                                    TextLayoutResultProxy layoutResult = legacyTextFieldState13.getLayoutResult();
                                                                                                                    TextLayoutResult textLayoutResult3 = layoutResult != null ? layoutResult.value : null;
                                                                                                                    TextFieldDelegate.Companion companion4 = TextFieldDelegate.Companion;
                                                                                                                    TextDelegate textDelegate3 = legacyTextFieldState13.textDelegate;
                                                                                                                    LayoutDirection layoutDirection = measureScope.getLayoutDirection();
                                                                                                                    companion4.getClass();
                                                                                                                    int i57 = textDelegate3.overflow;
                                                                                                                    boolean z50 = textDelegate3.softWrap;
                                                                                                                    int i58 = textDelegate3.maxLines;
                                                                                                                    if (textLayoutResult3 != null) {
                                                                                                                        List list2 = textDelegate3.placeholders;
                                                                                                                        if (textLayoutResult3.multiParagraph.intrinsics.getHasStaleResolvedFonts()) {
                                                                                                                            j10 = j9;
                                                                                                                            textLayoutResult = textLayoutResult3;
                                                                                                                            textLayoutResultProxy = layoutResult;
                                                                                                                            z49 = z50;
                                                                                                                            textDelegate3.layoutIntrinsics(layoutDirection);
                                                                                                                            int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j10);
                                                                                                                            if (z49) {
                                                                                                                                TextOverflow.Companion.getClass();
                                                                                                                                if (i57 == TextOverflow.Ellipsis) {
                                                                                                                                    int iM823getMaxWidthimpl = Constraints.m819getHasBoundedWidthimpl(j10) ? Constraints.m823getMaxWidthimpl(j10) : Integer.MAX_VALUE;
                                                                                                                                    if (!z49) {
                                                                                                                                        TextOverflow.Companion.getClass();
                                                                                                                                        int i59 = i57 == TextOverflow.Ellipsis ? 1 : i58;
                                                                                                                                        if (iM825getMinWidthimpl != iM823getMaxWidthimpl) {
                                                                                                                                            MultiParagraphIntrinsics multiParagraphIntrinsics = textDelegate3.paragraphIntrinsics;
                                                                                                                                            if (multiParagraphIntrinsics == null) {
                                                                                                                                                throw new IllegalStateException("layoutIntrinsics must be called first");
                                                                                                                                            }
                                                                                                                                            iM823getMaxWidthimpl = RangesKt___RangesKt.coerceIn(TextDelegateKt.ceilToIntPx(multiParagraphIntrinsics.getMaxIntrinsicWidth()), iM825getMinWidthimpl, iM823getMaxWidthimpl);
                                                                                                                                        }
                                                                                                                                        MultiParagraphIntrinsics multiParagraphIntrinsics2 = textDelegate3.paragraphIntrinsics;
                                                                                                                                        if (multiParagraphIntrinsics2 == null) {
                                                                                                                                            throw new IllegalStateException("layoutIntrinsics must be called first");
                                                                                                                                        }
                                                                                                                                        Constraints.Companion companion5 = Constraints.Companion;
                                                                                                                                        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j10);
                                                                                                                                        companion5.getClass();
                                                                                                                                        IntSize.Companion companion6 = IntSize.Companion;
                                                                                                                                        textLayoutResult2 = new TextLayoutResult(new TextLayoutInput(textDelegate3.text, textDelegate3.style, textDelegate3.placeholders, textDelegate3.maxLines, textDelegate3.softWrap, textDelegate3.overflow, textDelegate3.density, layoutDirection, textDelegate3.fontFamilyResolver, j10, (DefaultConstructorMarker) null), new MultiParagraph(multiParagraphIntrinsics2, Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(0, iM823getMaxWidthimpl, 0, iM822getMaxHeightimpl), i59, textDelegate3.overflow, (DefaultConstructorMarker) null), ConstraintsKt.m831constrain4WqzIAM(j10, (TextDelegateKt.ceilToIntPx(r26.height) & 4294967295L) | (TextDelegateKt.ceilToIntPx(r26.width) << 32)), null);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            TextLayoutInput textLayoutInput = textLayoutResult3.layoutInput;
                                                                                                                            if (Intrinsics.areEqual(textLayoutInput.text, textDelegate3.text) && textLayoutInput.style.hasSameLayoutAffectingAttributes(textDelegate3.style) && Intrinsics.areEqual(textLayoutInput.placeholders, list2) && textLayoutInput.maxLines == i58 && textLayoutInput.softWrap == z50) {
                                                                                                                                TextOverflow.Companion companion7 = TextOverflow.Companion;
                                                                                                                                if (textLayoutInput.overflow == i57 && Intrinsics.areEqual(textLayoutInput.density, textDelegate3.density) && textLayoutInput.layoutDirection == layoutDirection && Intrinsics.areEqual(textLayoutInput.fontFamilyResolver, textDelegate3.fontFamilyResolver)) {
                                                                                                                                    int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(j9);
                                                                                                                                    z49 = z50;
                                                                                                                                    long j11 = textLayoutInput.constraints;
                                                                                                                                    if (iM825getMinWidthimpl2 == Constraints.m825getMinWidthimpl(j11)) {
                                                                                                                                        if (!z49) {
                                                                                                                                            TextOverflow.Companion.getClass();
                                                                                                                                            if (i57 == TextOverflow.Ellipsis) {
                                                                                                                                                if (Constraints.m823getMaxWidthimpl(j9) == Constraints.m823getMaxWidthimpl(j11)) {
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            textLayoutResult = textLayoutResult3;
                                                                                                                                            textLayoutResultProxy = layoutResult;
                                                                                                                                            IntSize.Companion companion8 = IntSize.Companion;
                                                                                                                                            textLayoutResult2 = new TextLayoutResult(new TextLayoutInput(textLayoutInput.text, textDelegate3.style, textLayoutInput.placeholders, textLayoutInput.maxLines, textLayoutInput.softWrap, textLayoutInput.overflow, textLayoutInput.density, textLayoutInput.layoutDirection, textLayoutInput.fontFamilyResolver, j9, (DefaultConstructorMarker) null), textLayoutResult.multiParagraph, ConstraintsKt.m831constrain4WqzIAM(j9, (TextDelegateKt.ceilToIntPx(r11.width) << 32) | (TextDelegateKt.ceilToIntPx(r11.height) & 4294967295L)), null);
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    j10 = j9;
                                                                                                                                    textLayoutResult = textLayoutResult3;
                                                                                                                                    textLayoutResultProxy = layoutResult;
                                                                                                                                }
                                                                                                                                textDelegate3.layoutIntrinsics(layoutDirection);
                                                                                                                                int iM825getMinWidthimpl3 = Constraints.m825getMinWidthimpl(j10);
                                                                                                                                if (z49) {
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                    TextLayoutResult textLayoutResult4 = textLayoutResult2;
                                                                                                                    long j12 = textLayoutResult4.size;
                                                                                                                    Triple triple = new Triple(Integer.valueOf((int) (j12 >> 32)), Integer.valueOf((int) (j12 & 4294967295L)), textLayoutResult4);
                                                                                                                    int iIntValue4 = ((Number) triple.component1()).intValue();
                                                                                                                    int iIntValue5 = ((Number) triple.component2()).intValue();
                                                                                                                    TextLayoutResult textLayoutResult5 = (TextLayoutResult) triple.component3();
                                                                                                                    if (Intrinsics.areEqual(textLayoutResult, textLayoutResult5)) {
                                                                                                                        anonymousClass2 = this;
                                                                                                                    } else {
                                                                                                                        TextLayoutResultProxy textLayoutResultProxy2 = textLayoutResultProxy;
                                                                                                                        ((SnapshotMutableStateImpl) legacyTextFieldState13.layoutResultState).setValue(new TextLayoutResultProxy(textLayoutResult5, null, textLayoutResultProxy2 != null ? textLayoutResultProxy2.decorationBoxCoordinates : null, 2, null));
                                                                                                                        legacyTextFieldState13.isLayoutResultStale = false;
                                                                                                                        anonymousClass2 = this;
                                                                                                                        function119.mo781invoke(textLayoutResult5);
                                                                                                                        CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState13, textFieldValue6, offsetMapping11);
                                                                                                                    }
                                                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState13.minHeightForSingleLineField$delegate).setValue(Dp.m837boximpl(density6.mo55toDpu2uoSUM(i56 == 1 ? TextDelegateKt.ceilToIntPx(textLayoutResult5.multiParagraph.getLineBottom(0)) : 0)));
                                                                                                                    return measureScope.layout$1(iIntValue4, iIntValue5, MapsKt__MapsKt.mapOf(new Pair(AlignmentLineKt.FirstBaseline, Integer.valueOf(Math.round(textLayoutResult5.firstBaseline))), new Pair(AlignmentLineKt.LastBaseline, Integer.valueOf(Math.round(textLayoutResult5.lastBaseline)))), new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$5$1$1$2$measure$2
                                                                                                                        @Override // kotlin.jvm.functions.Function1
                                                                                                                        /* renamed from: invoke */
                                                                                                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj22) {
                                                                                                                            return Unit.INSTANCE;
                                                                                                                        }
                                                                                                                    });
                                                                                                                } finally {
                                                                                                                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                                                                                }
                                                                                                            }
                                                                                                        };
                                                                                                        Modifier.Companion companion3 = Modifier.Companion;
                                                                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl9);
                                                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl9.currentCompositionLocalScope();
                                                                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl9, companion3);
                                                                                                        ComposeUiNode.Companion.getClass();
                                                                                                        Function0 function04 = ComposeUiNode.Companion.Constructor;
                                                                                                        if (composerImpl9.applier == null) {
                                                                                                            ComposablesKt.invalidApplier();
                                                                                                            throw null;
                                                                                                        }
                                                                                                        composerImpl9.startReusableNode();
                                                                                                        if (composerImpl9.inserting) {
                                                                                                            composerImpl9.createNode(function04);
                                                                                                        } else {
                                                                                                            composerImpl9.useNode();
                                                                                                        }
                                                                                                        Updater.m337setimpl(composerImpl9, measurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                                                        Updater.m337setimpl(composerImpl9, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                                                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                                                        if (composerImpl9.inserting || !Intrinsics.areEqual(composerImpl9.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl9, currentCompositeKeyHash, function2);
                                                                                                        }
                                                                                                        Updater.m337setimpl(composerImpl9, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                                                        composerImpl9.end(true);
                                                                                                        TextFieldSelectionManager textFieldSelectionManager14 = textFieldSelectionManager13;
                                                                                                        if (legacyTextFieldState11.getHandleState() == HandleState.None || legacyTextFieldState11.getLayoutCoordinates() == null) {
                                                                                                            z48 = false;
                                                                                                            CoreTextFieldKt.access$SelectionToolbarAndHandles(textFieldSelectionManager14, z48, composerImpl9, 0);
                                                                                                            if (legacyTextFieldState11.getHandleState() == HandleState.Cursor || z47 || !z46) {
                                                                                                                composerImpl9.startReplaceGroup(-7037410);
                                                                                                                composerImpl9.end(false);
                                                                                                            } else {
                                                                                                                composerImpl9.startReplaceGroup(-7114290);
                                                                                                                CoreTextFieldKt.TextFieldCursorHandle(textFieldSelectionManager13, composerImpl9, 0);
                                                                                                                composerImpl9.end(false);
                                                                                                            }
                                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                                ComposerKt.traceEventEnd();
                                                                                                            }
                                                                                                        } else {
                                                                                                            LayoutCoordinates layoutCoordinates = legacyTextFieldState11.getLayoutCoordinates();
                                                                                                            layoutCoordinates.getClass();
                                                                                                            if (!layoutCoordinates.isAttached() || !z46) {
                                                                                                            }
                                                                                                            CoreTextFieldKt.access$SelectionToolbarAndHandles(textFieldSelectionManager14, z48, composerImpl9, 0);
                                                                                                            if (legacyTextFieldState11.getHandleState() == HandleState.Cursor) {
                                                                                                                composerImpl9.startReplaceGroup(-7037410);
                                                                                                                composerImpl9.end(false);
                                                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        composerImpl9.skipToGroupEnd();
                                                                                                    }
                                                                                                    return Unit.INSTANCE;
                                                                                                }
                                                                                            }, composerImpl8), composerImpl8, 48, 0);
                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                ComposerKt.traceEventEnd();
                                                                                            }
                                                                                        }
                                                                                    } else {
                                                                                        composerImpl8.skipToGroupEnd();
                                                                                    }
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }, composerImpl7), composerImpl7, 6);
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                ComposerKt.traceEventEnd();
                                                                            }
                                                                        } else {
                                                                            composerImpl7.skipToGroupEnd();
                                                                        }
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                }, composerImpl6), composerImpl6, 384);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                                i26 = z30 ? 1 : 0;
                                                                composerImpl = composerImpl6;
                                                                z9 = z19;
                                                                function14 = function15;
                                                                textFieldScrollerPosition2 = textFieldScrollerPosition3;
                                                                keyboardActions2 = keyboardActions4;
                                                                mutableInteractionSource3 = mutableInteractionSource5;
                                                                brush2 = brush7;
                                                                modifier2 = modifier6;
                                                                i27 = z33 ? 1 : 0;
                                                                imeOptions2 = imeOptions8;
                                                                z7 = z15;
                                                                z8 = z42;
                                                                visualTransformation3 = visualTransformation6;
                                                                function32 = function35;
                                                                textStyle3 = textStyle10;
                                                            } else {
                                                                if (imeOptions8.keyboardType != KeyboardType.NumberPassword) {
                                                                    z27 = true;
                                                                }
                                                                boolean zBooleanValue2 = ((Boolean) mutableState.getValue()).booleanValue();
                                                                zChanged = composerImpl2.changed(z27) | composerImpl2.changedInstance(legacyPlatformTextInputServiceAdapter);
                                                                Object objRememberedValue192 = composerImpl2.rememberedValue();
                                                                obj8 = objRememberedValue192;
                                                                if (!zChanged) {
                                                                    Function0 function022 = new Function0() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$stylusHandwritingModifier$1$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(0);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function0
                                                                        public final Object invoke() {
                                                                            if (z27) {
                                                                                legacyPlatformTextInputServiceAdapter.startStylusHandwriting();
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    composerImpl2.updateRememberedValue(function022);
                                                                    obj8 = function022;
                                                                    Modifier modifierStylusHandwriting2 = StylusHandwritingKt.stylusHandwriting(TextFieldMagnifier, zBooleanValue2, z27, (Function0) obj8);
                                                                    Brush brush72 = brush4;
                                                                    j2 = ((Color) composerImpl2.consume(AutofillHighlightKt.LocalAutofillHighlightColor)).value;
                                                                    zChangedInstance8 = composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changed(j2);
                                                                    Object objRememberedValue202 = composerImpl2.rememberedValue();
                                                                    obj9 = objRememberedValue202;
                                                                    if (!zChangedInstance8) {
                                                                        Function1 function1152 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$drawDecorationModifier$1$1
                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj16) {
                                                                                DrawScope drawScope = (DrawScope) obj16;
                                                                                if (((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState2.autofillHighlightOn$delegate).getValue()).booleanValue() || ((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState2.justAutofilled$delegate).getValue()).booleanValue()) {
                                                                                    DrawScope.m541drawRectnJ9OG0$default(drawScope, j2, 0L, 0L, 0.0f, null, null, 0, 126);
                                                                                }
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        };
                                                                        composerImpl2.updateRememberedValue(function1152);
                                                                        obj9 = function1152;
                                                                        Modifier modifierThen22 = LegacyAdaptingPlatformTextInputModifierNodeKt.legacyTextInputAdapter(modifier6.then(DrawModifierKt.drawBehind(TextFieldMagnifier, (Function1) obj9)), legacyPlatformTextInputServiceAdapter, legacyTextFieldState2, textFieldSelectionManager11).then(modifierStylusHandwriting2).then(modifierFocusable);
                                                                        final FocusManager focusManager52 = focusManager2;
                                                                        final boolean z422 = z26;
                                                                        Modifier modifierOnGloballyPositioned22 = OnGloballyPositionedModifierKt.onGloballyPositioned(ComposedModifierKt.composed(KeyInputModifierKt.onPreviewKeyEvent(KeyInputModifierKt.onPreviewKeyEvent(modifierThen22, new Function1() { // from class: androidx.compose.foundation.text.TextFieldFocusModifier_androidKt$interceptDPadAndMoveFocus$1
                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj16) {
                                                                                KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj16).nativeKeyEvent;
                                                                                InputDevice device = keyEvent.getDevice();
                                                                                boolean zM375moveFocus3ESFkO8 = false;
                                                                                if (device != null && device.supportsSource(513) && !device.isVirtual()) {
                                                                                    int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                                                                    KeyEventType.Companion.getClass();
                                                                                    if (iM581getTypeZmokQxo == KeyEventType.KeyDown && keyEvent.getSource() != 257) {
                                                                                        if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(19, keyEvent)) {
                                                                                            FocusManager focusManager6 = focusManager52;
                                                                                            FocusDirection.Companion.getClass();
                                                                                            zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager6).m375moveFocus3ESFkO8(FocusDirection.Up);
                                                                                        } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(20, keyEvent)) {
                                                                                            FocusManager focusManager7 = focusManager52;
                                                                                            FocusDirection.Companion.getClass();
                                                                                            zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager7).m375moveFocus3ESFkO8(FocusDirection.Down);
                                                                                        } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(21, keyEvent)) {
                                                                                            FocusManager focusManager8 = focusManager52;
                                                                                            FocusDirection.Companion.getClass();
                                                                                            zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager8).m375moveFocus3ESFkO8(FocusDirection.Left);
                                                                                        } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(22, keyEvent)) {
                                                                                            FocusManager focusManager9 = focusManager52;
                                                                                            FocusDirection.Companion.getClass();
                                                                                            zM375moveFocus3ESFkO8 = ((FocusOwnerImpl) focusManager9).m375moveFocus3ESFkO8(FocusDirection.Right);
                                                                                        } else if (TextFieldFocusModifier_androidKt.m206access$isKeyCodeYhN2O0w(23, keyEvent)) {
                                                                                            SoftwareKeyboardController softwareKeyboardController2 = legacyTextFieldState2.keyboardController;
                                                                                            if (softwareKeyboardController2 != null) {
                                                                                                ((DelegatingSoftwareKeyboardController) softwareKeyboardController2).show();
                                                                                            }
                                                                                            zM375moveFocus3ESFkO8 = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                return Boolean.valueOf(zM375moveFocus3ESFkO8);
                                                                            }
                                                                        }), new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$previewKeyEventToDeselectOnBack$1
                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            /*
                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                            */
                                                                            public final Object mo781invoke(Object obj16) {
                                                                                boolean z432;
                                                                                KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj16).nativeKeyEvent;
                                                                                if (legacyTextFieldState2.getHandleState() == HandleState.Selection && keyEvent.getKeyCode() == 4) {
                                                                                    int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                                                                    KeyEventType.Companion.getClass();
                                                                                    if (iM581getTypeZmokQxo == KeyEventType.KeyUp) {
                                                                                        textFieldSelectionManager11.m239deselect_kEHs6E$foundation_release(null);
                                                                                        z432 = true;
                                                                                    }
                                                                                } else {
                                                                                    z432 = false;
                                                                                }
                                                                                return Boolean.valueOf(z432);
                                                                            }
                                                                        }).then(modifierComposed2), function114, new Function3() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2
                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            {
                                                                                super(3);
                                                                            }

                                                                            /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
                                                                            /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
                                                                            @Override // kotlin.jvm.functions.Function3
                                                                            /*
                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                            */
                                                                            public final Object invoke(Object obj16, Object obj17, Object obj18) {
                                                                                ((Number) obj18).intValue();
                                                                                ComposerImpl composerImpl62 = (ComposerImpl) ((Composer) obj17);
                                                                                composerImpl62.startReplaceGroup(805428266);
                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.textFieldScrollable.<anonymous> (TextFieldScroll.kt:71)");
                                                                                }
                                                                                boolean z432 = ((Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition5.orientation$delegate).getValue()) == Orientation.Vertical || !(composerImpl62.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl);
                                                                                boolean zChanged5 = composerImpl62.changed(textFieldScrollerPosition5);
                                                                                final TextFieldScrollerPosition textFieldScrollerPosition6 = textFieldScrollerPosition5;
                                                                                Object objRememberedValue21 = composerImpl62.rememberedValue();
                                                                                Composer.Companion companion2 = Composer.Companion;
                                                                                if (!zChanged5) {
                                                                                    companion2.getClass();
                                                                                    if (objRememberedValue21 == Composer.Companion.Empty) {
                                                                                        objRememberedValue21 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$scrollableState$1$1
                                                                                            {
                                                                                                super(1);
                                                                                            }

                                                                                            @Override // kotlin.jvm.functions.Function1
                                                                                            /* renamed from: invoke */
                                                                                            public final Object mo781invoke(Object obj19) {
                                                                                                float fFloatValue = ((Number) obj19).floatValue();
                                                                                                float floatValue = ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.offset$delegate).getFloatValue() + fFloatValue;
                                                                                                if (floatValue > ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.maximum$delegate).getFloatValue()) {
                                                                                                    fFloatValue = ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.maximum$delegate).getFloatValue() - ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.offset$delegate).getFloatValue();
                                                                                                } else if (floatValue < 0.0f) {
                                                                                                    fFloatValue = -((SnapshotMutableFloatStateImpl) textFieldScrollerPosition6.offset$delegate).getFloatValue();
                                                                                                }
                                                                                                TextFieldScrollerPosition textFieldScrollerPosition7 = textFieldScrollerPosition6;
                                                                                                ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).setFloatValue(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).getFloatValue() + fFloatValue);
                                                                                                return Float.valueOf(fFloatValue);
                                                                                            }
                                                                                        };
                                                                                        composerImpl62.updateRememberedValue(objRememberedValue21);
                                                                                    }
                                                                                }
                                                                                final ScrollableState scrollableStateRememberScrollableState = ScrollableStateKt.rememberScrollableState(composerImpl62, (Function1) objRememberedValue21);
                                                                                boolean zChanged6 = composerImpl62.changed(scrollableStateRememberScrollableState) | composerImpl62.changed(textFieldScrollerPosition5);
                                                                                final TextFieldScrollerPosition textFieldScrollerPosition7 = textFieldScrollerPosition5;
                                                                                Object objRememberedValue22 = composerImpl62.rememberedValue();
                                                                                if (!zChanged6) {
                                                                                    companion2.getClass();
                                                                                    if (objRememberedValue22 == Composer.Companion.Empty) {
                                                                                        objRememberedValue22 = new ScrollableState(textFieldScrollerPosition7) { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1
                                                                                            public final State canScrollBackward$delegate;
                                                                                            public final State canScrollForward$delegate;

                                                                                            {
                                                                                                this.canScrollForward$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1$canScrollForward$2
                                                                                                    {
                                                                                                        super(0);
                                                                                                    }

                                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                                    public final Object invoke() {
                                                                                                        return Boolean.valueOf(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).getFloatValue() < ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.maximum$delegate).getFloatValue());
                                                                                                    }
                                                                                                });
                                                                                                this.canScrollBackward$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.text.TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1$canScrollBackward$2
                                                                                                    {
                                                                                                        super(0);
                                                                                                    }

                                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                                    public final Object invoke() {
                                                                                                        return Boolean.valueOf(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition7.offset$delegate).getFloatValue() > 0.0f);
                                                                                                    }
                                                                                                });
                                                                                            }

                                                                                            @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                            public final float dispatchRawDelta(float f) {
                                                                                                return this.$$delegate_0.dispatchRawDelta(f);
                                                                                            }

                                                                                            @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                            public final boolean getCanScrollBackward() {
                                                                                                return ((Boolean) this.canScrollBackward$delegate.getValue()).booleanValue();
                                                                                            }

                                                                                            @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                            public final boolean getCanScrollForward() {
                                                                                                return ((Boolean) this.canScrollForward$delegate.getValue()).booleanValue();
                                                                                            }

                                                                                            @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                            public final boolean isScrollInProgress() {
                                                                                                return this.$$delegate_0.isScrollInProgress();
                                                                                            }

                                                                                            @Override // androidx.compose.foundation.gestures.ScrollableState
                                                                                            public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
                                                                                                return this.$$delegate_0.scroll(mutatePriority, function2, continuation);
                                                                                            }
                                                                                        };
                                                                                        composerImpl62.updateRememberedValue(objRememberedValue22);
                                                                                    }
                                                                                }
                                                                                Modifier modifierScrollable$default = ScrollableKt.scrollable$default(Modifier.Companion, (TextFieldScrollKt$textFieldScrollable$2$wrappedScrollableState$1$1) objRememberedValue22, (Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition5.orientation$delegate).getValue(), z422 && ((SnapshotMutableFloatStateImpl) textFieldScrollerPosition5.maximum$delegate).getFloatValue() != 0.0f, z432, mutableInteractionSource5, 16);
                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                    ComposerKt.traceEventEnd();
                                                                                }
                                                                                composerImpl62.end(false);
                                                                                return modifierScrollable$default;
                                                                            }
                                                                        }).then(modifierThen).then(coreTextFieldSemanticsModifier2), new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$decorationBoxModifier$1
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj16) {
                                                                                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) obj16;
                                                                                TextLayoutResultProxy layoutResult = legacyTextFieldState2.getLayoutResult();
                                                                                if (layoutResult != null) {
                                                                                    layoutResult.decorationBoxCoordinates = layoutCoordinates;
                                                                                }
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        });
                                                                        if (z422) {
                                                                            if (z28) {
                                                                            }
                                                                            final Modifier modifier82 = TextFieldMagnifier;
                                                                            ComposerImpl composerImpl62 = composerImpl2;
                                                                            final LegacyTextFieldState legacyTextFieldState82 = legacyTextFieldState2;
                                                                            final BringIntoViewRequester bringIntoViewRequester32 = bringIntoViewRequester;
                                                                            final VisualTransformation visualTransformation82 = visualTransformation6;
                                                                            final boolean z432 = z28;
                                                                            final TextStyle textStyle102 = textStyle8;
                                                                            final int i482 = z30 ? 1 : 0;
                                                                            final int i492 = z33 ? 1 : 0;
                                                                            CoreTextFieldRootBox(modifierOnGloballyPositioned22, textFieldSelectionManager11, ComposableLambdaKt.rememberComposableLambda(-492537660, new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(2);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                public final Object invoke(Object obj16, Object obj17) {
                                                                                    Composer composer2 = (Composer) obj16;
                                                                                    int iIntValue = ((Number) obj17).intValue();
                                                                                    ComposerImpl composerImpl7 = (ComposerImpl) composer2;
                                                                                    if (composerImpl7.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField.<anonymous> (CoreTextField.kt:556)");
                                                                                        }
                                                                                        Function3 function38 = function35;
                                                                                        final LegacyTextFieldState legacyTextFieldState9 = legacyTextFieldState82;
                                                                                        final TextStyle textStyle11 = textStyle102;
                                                                                        final int i50 = i482;
                                                                                        final int i51 = i492;
                                                                                        final TextFieldScrollerPosition textFieldScrollerPosition6 = textFieldScrollerPosition5;
                                                                                        final TextFieldValue textFieldValue3 = textFieldValue;
                                                                                        final VisualTransformation visualTransformation9 = visualTransformation82;
                                                                                        final Modifier modifier9 = modifier7;
                                                                                        final Modifier modifier10 = modifierDrawBehind;
                                                                                        final Modifier modifier11 = modifierOnGloballyPositioned;
                                                                                        final Modifier modifier12 = modifier82;
                                                                                        final BringIntoViewRequester bringIntoViewRequester4 = bringIntoViewRequester32;
                                                                                        final TextFieldSelectionManager textFieldSelectionManager12 = textFieldSelectionManager11;
                                                                                        final boolean z44 = z432;
                                                                                        final boolean z45 = z19;
                                                                                        final Function1 function116 = function15;
                                                                                        final OffsetMapping offsetMapping9 = offsetMapping8;
                                                                                        final Density density4 = density3;
                                                                                        function38.invoke(ComposableLambdaKt.rememberComposableLambda(-1835647873, new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5.1
                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                            {
                                                                                                super(2);
                                                                                            }

                                                                                            /* JADX WARN: Removed duplicated region for block: B:15:0x006e  */
                                                                                            @Override // kotlin.jvm.functions.Function2
                                                                                            /*
                                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                                            */
                                                                                            public final Object invoke(Object obj18, Object obj19) {
                                                                                                Modifier verticalScrollLayoutModifier;
                                                                                                Composer composer3 = (Composer) obj18;
                                                                                                int iIntValue2 = ((Number) obj19).intValue();
                                                                                                ComposerImpl composerImpl8 = (ComposerImpl) composer3;
                                                                                                if (composerImpl8.shouldExecute(iIntValue2 & 1, (iIntValue2 & 3) != 2)) {
                                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                                        ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField.<anonymous>.<anonymous> (CoreTextField.kt:559)");
                                                                                                    }
                                                                                                    Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(Modifier.Companion, ((Dp) ((SnapshotMutableStateImpl) legacyTextFieldState9.minHeightForSingleLineField$delegate).getValue()).value, 0.0f, 2);
                                                                                                    final TextStyle textStyle12 = textStyle11;
                                                                                                    final int i52 = i50;
                                                                                                    final int i53 = i51;
                                                                                                    Function1 function117 = InspectableValueKt.NoInspectorInfo;
                                                                                                    Modifier modifierComposed3 = ComposedModifierKt.composed(modifierM133heightInVpY3zN4$default, function117, new Function3() { // from class: androidx.compose.foundation.text.HeightInLinesModifierKt$heightInLines$2
                                                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                        {
                                                                                                            super(3);
                                                                                                        }

                                                                                                        /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
                                                                                                        /* JADX WARN: Removed duplicated region for block: B:23:0x009a  */
                                                                                                        /* JADX WARN: Removed duplicated region for block: B:41:0x0104  */
                                                                                                        /* JADX WARN: Removed duplicated region for block: B:46:0x014a  */
                                                                                                        @Override // kotlin.jvm.functions.Function3
                                                                                                        /*
                                                                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                                                                        */
                                                                                                        public final Object invoke(Object obj20, Object obj21, Object obj22) {
                                                                                                            int i54;
                                                                                                            int i55;
                                                                                                            long j7;
                                                                                                            float fMo55toDpu2uoSUM;
                                                                                                            float fMo55toDpu2uoSUM2;
                                                                                                            ((Number) obj22).intValue();
                                                                                                            ComposerImpl composerImpl9 = (ComposerImpl) ((Composer) obj21);
                                                                                                            composerImpl9.startReplaceGroup(408240218);
                                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                                ComposerKt.traceEventStart("androidx.compose.foundation.text.heightInLines.<anonymous> (HeightInLinesModifier.kt:62)");
                                                                                                            }
                                                                                                            HeightInLinesModifierKt.validateMinMaxLines(i52, i53);
                                                                                                            if (i52 == 1 && i53 == Integer.MAX_VALUE) {
                                                                                                                Modifier.Companion companion2 = Modifier.Companion;
                                                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                                                    ComposerKt.traceEventEnd();
                                                                                                                }
                                                                                                                composerImpl9.end(false);
                                                                                                                return companion2;
                                                                                                            }
                                                                                                            Density density5 = (Density) composerImpl9.consume(CompositionLocalsKt.LocalDensity);
                                                                                                            FontFamily.Resolver resolver3 = (FontFamily.Resolver) composerImpl9.consume(CompositionLocalsKt.LocalFontFamilyResolver);
                                                                                                            LayoutDirection layoutDirection = (LayoutDirection) composerImpl9.consume(CompositionLocalsKt.LocalLayoutDirection);
                                                                                                            boolean zChanged5 = composerImpl9.changed(textStyle12) | composerImpl9.changed(layoutDirection);
                                                                                                            TextStyle textStyle13 = textStyle12;
                                                                                                            Object objRememberedValue21 = composerImpl9.rememberedValue();
                                                                                                            Composer.Companion companion3 = Composer.Companion;
                                                                                                            if (!zChanged5) {
                                                                                                                companion3.getClass();
                                                                                                                if (objRememberedValue21 == Composer.Companion.Empty) {
                                                                                                                    objRememberedValue21 = TextStyleKt.resolveDefaults(textStyle13, layoutDirection);
                                                                                                                    composerImpl9.updateRememberedValue(objRememberedValue21);
                                                                                                                }
                                                                                                            }
                                                                                                            TextStyle textStyle14 = (TextStyle) objRememberedValue21;
                                                                                                            boolean zChanged6 = composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle14);
                                                                                                            Object objRememberedValue22 = composerImpl9.rememberedValue();
                                                                                                            if (!zChanged6) {
                                                                                                                companion3.getClass();
                                                                                                                if (objRememberedValue22 == Composer.Companion.Empty) {
                                                                                                                    SpanStyle spanStyle = textStyle14.spanStyle;
                                                                                                                    FontFamily fontFamily = spanStyle.fontFamily;
                                                                                                                    FontWeight fontWeight = spanStyle.fontWeight;
                                                                                                                    if (fontWeight == null) {
                                                                                                                        FontWeight.Companion.getClass();
                                                                                                                        fontWeight = FontWeight.Normal;
                                                                                                                    }
                                                                                                                    SpanStyle spanStyle2 = textStyle14.spanStyle;
                                                                                                                    FontStyle fontStyle = spanStyle2.fontStyle;
                                                                                                                    if (fontStyle != null) {
                                                                                                                        i54 = fontStyle.value;
                                                                                                                    } else {
                                                                                                                        FontStyle.Companion.getClass();
                                                                                                                        i54 = 0;
                                                                                                                    }
                                                                                                                    FontSynthesis fontSynthesis = spanStyle2.fontSynthesis;
                                                                                                                    if (fontSynthesis != null) {
                                                                                                                        i55 = fontSynthesis.value;
                                                                                                                    } else {
                                                                                                                        FontSynthesis.Companion.getClass();
                                                                                                                        i55 = FontSynthesis.All;
                                                                                                                    }
                                                                                                                    objRememberedValue22 = ((FontFamilyResolverImpl) resolver3).m764resolveDPcqOEQ(fontFamily, fontWeight, i54, i55);
                                                                                                                    composerImpl9.updateRememberedValue(objRememberedValue22);
                                                                                                                }
                                                                                                            }
                                                                                                            State state = (State) objRememberedValue22;
                                                                                                            boolean zChanged7 = composerImpl9.changed(state.getValue()) | composerImpl9.changed(density5) | composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle12) | composerImpl9.changed(layoutDirection);
                                                                                                            Object objRememberedValue23 = composerImpl9.rememberedValue();
                                                                                                            if (!zChanged7) {
                                                                                                                companion3.getClass();
                                                                                                                if (objRememberedValue23 == Composer.Companion.Empty) {
                                                                                                                    j7 = 4294967295L;
                                                                                                                    objRememberedValue23 = Integer.valueOf((int) (TextFieldDelegateKt.computeSizeForDefaultText(textStyle14, density5, resolver3, TextFieldDelegateKt.EmptyTextReplacement, 1) & 4294967295L));
                                                                                                                    composerImpl9.updateRememberedValue(objRememberedValue23);
                                                                                                                } else {
                                                                                                                    j7 = 4294967295L;
                                                                                                                }
                                                                                                            }
                                                                                                            int iIntValue3 = ((Number) objRememberedValue23).intValue();
                                                                                                            boolean zChanged8 = composerImpl9.changed(layoutDirection) | composerImpl9.changed(density5) | composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle12) | composerImpl9.changed(state.getValue());
                                                                                                            Object objRememberedValue24 = composerImpl9.rememberedValue();
                                                                                                            if (!zChanged8) {
                                                                                                                companion3.getClass();
                                                                                                                if (objRememberedValue24 == Composer.Companion.Empty) {
                                                                                                                    StringBuilder sb = new StringBuilder();
                                                                                                                    String str2 = TextFieldDelegateKt.EmptyTextReplacement;
                                                                                                                    sb.append(str2);
                                                                                                                    sb.append('\n');
                                                                                                                    sb.append(str2);
                                                                                                                    objRememberedValue24 = Integer.valueOf((int) (TextFieldDelegateKt.computeSizeForDefaultText(textStyle14, density5, resolver3, sb.toString(), 2) & j7));
                                                                                                                    composerImpl9.updateRememberedValue(objRememberedValue24);
                                                                                                                }
                                                                                                            }
                                                                                                            int iIntValue4 = ((Number) objRememberedValue24).intValue() - iIntValue3;
                                                                                                            int i56 = i52;
                                                                                                            Integer numValueOf = i56 == 1 ? null : Integer.valueOf(((i56 - 1) * iIntValue4) + iIntValue3);
                                                                                                            int i57 = i53;
                                                                                                            Integer numValueOf2 = i57 != Integer.MAX_VALUE ? Integer.valueOf(((i57 - 1) * iIntValue4) + iIntValue3) : null;
                                                                                                            Modifier.Companion companion4 = Modifier.Companion;
                                                                                                            if (numValueOf != null) {
                                                                                                                fMo55toDpu2uoSUM = density5.mo55toDpu2uoSUM(numValueOf.intValue());
                                                                                                            } else {
                                                                                                                Dp.Companion.getClass();
                                                                                                                fMo55toDpu2uoSUM = Dp.Unspecified;
                                                                                                            }
                                                                                                            if (numValueOf2 != null) {
                                                                                                                fMo55toDpu2uoSUM2 = density5.mo55toDpu2uoSUM(numValueOf2.intValue());
                                                                                                            } else {
                                                                                                                Dp.Companion.getClass();
                                                                                                                fMo55toDpu2uoSUM2 = Dp.Unspecified;
                                                                                                            }
                                                                                                            Modifier modifierM132heightInVpY3zN4 = SizeKt.m132heightInVpY3zN4(companion4, fMo55toDpu2uoSUM, fMo55toDpu2uoSUM2);
                                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                                ComposerKt.traceEventEnd();
                                                                                                            }
                                                                                                            composerImpl9.end(false);
                                                                                                            return modifierM132heightInVpY3zN4;
                                                                                                        }
                                                                                                    });
                                                                                                    TextFieldScrollerPosition textFieldScrollerPosition7 = textFieldScrollerPosition6;
                                                                                                    TextFieldValue textFieldValue4 = textFieldValue3;
                                                                                                    VisualTransformation visualTransformation10 = visualTransformation9;
                                                                                                    boolean zChangedInstance10 = composerImpl8.changedInstance(legacyTextFieldState9);
                                                                                                    final LegacyTextFieldState legacyTextFieldState10 = legacyTextFieldState9;
                                                                                                    Object objRememberedValue21 = composerImpl8.rememberedValue();
                                                                                                    if (!zChangedInstance10) {
                                                                                                        Composer.Companion.getClass();
                                                                                                        if (objRememberedValue21 == Composer.Companion.Empty) {
                                                                                                            objRememberedValue21 = new Function0() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$5$1$coreTextFieldModifier$1$1
                                                                                                                {
                                                                                                                    super(0);
                                                                                                                }

                                                                                                                @Override // kotlin.jvm.functions.Function0
                                                                                                                public final Object invoke() {
                                                                                                                    return legacyTextFieldState10.getLayoutResult();
                                                                                                                }
                                                                                                            };
                                                                                                            composerImpl8.updateRememberedValue(objRememberedValue21);
                                                                                                        }
                                                                                                        Function0 function03 = (Function0) objRememberedValue21;
                                                                                                        Orientation orientation2 = (Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition7.orientation$delegate).getValue();
                                                                                                        long j7 = textFieldValue4.selection;
                                                                                                        TextRange.Companion companion2 = TextRange.Companion;
                                                                                                        int iM752getMinimpl = (int) (j7 >> 32);
                                                                                                        long j8 = textFieldScrollerPosition7.previousSelection;
                                                                                                        if (iM752getMinimpl == ((int) (j8 >> 32)) && (iM752getMinimpl = (int) (j7 & 4294967295L)) == ((int) (j8 & 4294967295L))) {
                                                                                                            iM752getMinimpl = TextRange.m752getMinimpl(j7);
                                                                                                        }
                                                                                                        textFieldScrollerPosition7.previousSelection = textFieldValue4.selection;
                                                                                                        TransformedText transformedTextFilterWithValidation2 = ValidatingOffsetMappingKt.filterWithValidation(visualTransformation10, textFieldValue4.annotatedString);
                                                                                                        int i54 = TextFieldScrollKt.WhenMappings.$EnumSwitchMapping$0[orientation2.ordinal()];
                                                                                                        if (i54 == 1) {
                                                                                                            verticalScrollLayoutModifier = new VerticalScrollLayoutModifier(textFieldScrollerPosition7, iM752getMinimpl, transformedTextFilterWithValidation2, function03);
                                                                                                        } else {
                                                                                                            if (i54 != 2) {
                                                                                                                throw new NoWhenBranchMatchedException();
                                                                                                            }
                                                                                                            verticalScrollLayoutModifier = new HorizontalScrollLayoutModifier(textFieldScrollerPosition7, iM752getMinimpl, transformedTextFilterWithValidation2, function03);
                                                                                                        }
                                                                                                        Modifier modifierThen3 = ClipKt.clipToBounds(modifierComposed3).then(verticalScrollLayoutModifier).then(modifier9).then(modifier10);
                                                                                                        final TextStyle textStyle13 = textStyle11;
                                                                                                        Modifier modifierBringIntoViewRequester = BringIntoViewRequesterKt.bringIntoViewRequester(ComposedModifierKt.composed(modifierThen3, function117, new Function3() { // from class: androidx.compose.foundation.text.TextFieldSizeKt$textFieldMinSize$1
                                                                                                            {
                                                                                                                super(3);
                                                                                                            }

                                                                                                            /* JADX WARN: Removed duplicated region for block: B:14:0x0074  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:9:0x0053  */
                                                                                                            @Override // kotlin.jvm.functions.Function3
                                                                                                            /*
                                                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                                                            */
                                                                                                            public final Object invoke(Object obj20, Object obj21, Object obj22) {
                                                                                                                int i55;
                                                                                                                int i56;
                                                                                                                ((Number) obj22).intValue();
                                                                                                                ComposerImpl composerImpl9 = (ComposerImpl) ((Composer) obj21);
                                                                                                                composerImpl9.startReplaceGroup(1582736677);
                                                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                                                    ComposerKt.traceEventStart("androidx.compose.foundation.text.textFieldMinSize.<anonymous> (TextFieldSize.kt:37)");
                                                                                                                }
                                                                                                                Density density5 = (Density) composerImpl9.consume(CompositionLocalsKt.LocalDensity);
                                                                                                                FontFamily.Resolver resolver3 = (FontFamily.Resolver) composerImpl9.consume(CompositionLocalsKt.LocalFontFamilyResolver);
                                                                                                                LayoutDirection layoutDirection = (LayoutDirection) composerImpl9.consume(CompositionLocalsKt.LocalLayoutDirection);
                                                                                                                boolean zChanged5 = composerImpl9.changed(textStyle13) | composerImpl9.changed(layoutDirection);
                                                                                                                TextStyle textStyle14 = textStyle13;
                                                                                                                Object objRememberedValue22 = composerImpl9.rememberedValue();
                                                                                                                Composer.Companion companion3 = Composer.Companion;
                                                                                                                if (!zChanged5) {
                                                                                                                    companion3.getClass();
                                                                                                                    if (objRememberedValue22 == Composer.Companion.Empty) {
                                                                                                                        objRememberedValue22 = TextStyleKt.resolveDefaults(textStyle14, layoutDirection);
                                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue22);
                                                                                                                    }
                                                                                                                }
                                                                                                                TextStyle textStyle15 = (TextStyle) objRememberedValue22;
                                                                                                                boolean zChanged6 = composerImpl9.changed(resolver3) | composerImpl9.changed(textStyle15);
                                                                                                                Object objRememberedValue23 = composerImpl9.rememberedValue();
                                                                                                                if (!zChanged6) {
                                                                                                                    companion3.getClass();
                                                                                                                    if (objRememberedValue23 == Composer.Companion.Empty) {
                                                                                                                        SpanStyle spanStyle = textStyle15.spanStyle;
                                                                                                                        FontFamily fontFamily = spanStyle.fontFamily;
                                                                                                                        FontWeight fontWeight = spanStyle.fontWeight;
                                                                                                                        if (fontWeight == null) {
                                                                                                                            FontWeight.Companion.getClass();
                                                                                                                            fontWeight = FontWeight.Normal;
                                                                                                                        }
                                                                                                                        SpanStyle spanStyle2 = textStyle15.spanStyle;
                                                                                                                        FontStyle fontStyle = spanStyle2.fontStyle;
                                                                                                                        if (fontStyle != null) {
                                                                                                                            i55 = fontStyle.value;
                                                                                                                        } else {
                                                                                                                            FontStyle.Companion.getClass();
                                                                                                                            i55 = 0;
                                                                                                                        }
                                                                                                                        FontSynthesis fontSynthesis = spanStyle2.fontSynthesis;
                                                                                                                        if (fontSynthesis != null) {
                                                                                                                            i56 = fontSynthesis.value;
                                                                                                                        } else {
                                                                                                                            FontSynthesis.Companion.getClass();
                                                                                                                            i56 = FontSynthesis.All;
                                                                                                                        }
                                                                                                                        objRememberedValue23 = ((FontFamilyResolverImpl) resolver3).m764resolveDPcqOEQ(fontFamily, fontWeight, i55, i56);
                                                                                                                        composerImpl9.updateRememberedValue(objRememberedValue23);
                                                                                                                    }
                                                                                                                }
                                                                                                                State state = (State) objRememberedValue23;
                                                                                                                TextStyle textStyle16 = textStyle13;
                                                                                                                Object objRememberedValue24 = composerImpl9.rememberedValue();
                                                                                                                companion3.getClass();
                                                                                                                Object obj23 = Composer.Companion.Empty;
                                                                                                                if (objRememberedValue24 == obj23) {
                                                                                                                    objRememberedValue24 = new TextFieldSize(layoutDirection, density5, resolver3, textStyle16, state.getValue());
                                                                                                                    composerImpl9.updateRememberedValue(objRememberedValue24);
                                                                                                                }
                                                                                                                final TextFieldSize textFieldSize = (TextFieldSize) objRememberedValue24;
                                                                                                                Object value = state.getValue();
                                                                                                                if (layoutDirection != textFieldSize.layoutDirection || !Intrinsics.areEqual(density5, textFieldSize.density) || !Intrinsics.areEqual(resolver3, textFieldSize.fontFamilyResolver) || !Intrinsics.areEqual(textStyle15, textFieldSize.resolvedStyle) || !Intrinsics.areEqual(value, textFieldSize.typeface)) {
                                                                                                                    textFieldSize.layoutDirection = layoutDirection;
                                                                                                                    textFieldSize.density = density5;
                                                                                                                    textFieldSize.fontFamilyResolver = resolver3;
                                                                                                                    textFieldSize.resolvedStyle = textStyle15;
                                                                                                                    textFieldSize.typeface = value;
                                                                                                                    textFieldSize.minSize = TextFieldDelegateKt.computeSizeForDefaultText(textStyle15, density5, resolver3, TextFieldDelegateKt.EmptyTextReplacement, 1);
                                                                                                                }
                                                                                                                Modifier.Companion companion4 = Modifier.Companion;
                                                                                                                boolean zChangedInstance11 = composerImpl9.changedInstance(textFieldSize);
                                                                                                                Object objRememberedValue25 = composerImpl9.rememberedValue();
                                                                                                                if (zChangedInstance11 || objRememberedValue25 == obj23) {
                                                                                                                    objRememberedValue25 = new Function3() { // from class: androidx.compose.foundation.text.TextFieldSizeKt$textFieldMinSize$1$1$1
                                                                                                                        {
                                                                                                                            super(3);
                                                                                                                        }

                                                                                                                        @Override // kotlin.jvm.functions.Function3
                                                                                                                        public final Object invoke(Object obj24, Object obj25, Object obj26) {
                                                                                                                            long j9 = ((Constraints) obj26).value;
                                                                                                                            long j10 = textFieldSize.minSize;
                                                                                                                            final Placeable placeableMo610measureBRTryo0 = ((Measurable) obj25).mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j9, RangesKt___RangesKt.coerceIn((int) (j10 >> 32), Constraints.m825getMinWidthimpl(j9), Constraints.m823getMaxWidthimpl(j9)), 0, RangesKt___RangesKt.coerceIn((int) (j10 & 4294967295L), Constraints.m824getMinHeightimpl(j9), Constraints.m822getMaxHeightimpl(j9)), 0, 10));
                                                                                                                            return ((MeasureScope) obj24).layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.text.TextFieldSizeKt$textFieldMinSize$1$1$1.1
                                                                                                                                {
                                                                                                                                    super(1);
                                                                                                                                }

                                                                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                                                                /* renamed from: invoke */
                                                                                                                                public final Object mo781invoke(Object obj27) {
                                                                                                                                    ((Placeable.PlacementScope) obj27).placeRelative(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                                                                                                                                    return Unit.INSTANCE;
                                                                                                                                }
                                                                                                                            });
                                                                                                                        }
                                                                                                                    };
                                                                                                                    composerImpl9.updateRememberedValue(objRememberedValue25);
                                                                                                                }
                                                                                                                Modifier modifierLayout = LayoutModifierKt.layout(companion4, (Function3) objRememberedValue25);
                                                                                                                if (ComposerKt.isTraceInProgress()) {
                                                                                                                    ComposerKt.traceEventEnd();
                                                                                                                }
                                                                                                                composerImpl9.end(false);
                                                                                                                return modifierLayout;
                                                                                                            }
                                                                                                        }).then(modifier11).then(modifier12), bringIntoViewRequester4);
                                                                                                        final TextFieldSelectionManager textFieldSelectionManager13 = textFieldSelectionManager12;
                                                                                                        final LegacyTextFieldState legacyTextFieldState11 = legacyTextFieldState9;
                                                                                                        final boolean z46 = z44;
                                                                                                        final boolean z47 = z45;
                                                                                                        final Function1 function118 = function116;
                                                                                                        final TextFieldValue textFieldValue5 = textFieldValue3;
                                                                                                        final OffsetMapping offsetMapping10 = offsetMapping9;
                                                                                                        final Density density5 = density4;
                                                                                                        final int i55 = i51;
                                                                                                        SimpleLayoutKt.SimpleLayout(modifierBringIntoViewRequester, ComposableLambdaKt.rememberComposableLambda(-1172467467, new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5.1.1
                                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                            {
                                                                                                                super(2);
                                                                                                            }

                                                                                                            /* JADX WARN: Removed duplicated region for block: B:31:0x00af  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:39:0x00d4  */
                                                                                                            /* JADX WARN: Removed duplicated region for block: B:42:0x00e3  */
                                                                                                            @Override // kotlin.jvm.functions.Function2
                                                                                                            /*
                                                                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                                                            */
                                                                                                            public final Object invoke(Object obj20, Object obj21) throws Throwable {
                                                                                                                Composer composer4 = (Composer) obj20;
                                                                                                                int iIntValue3 = ((Number) obj21).intValue();
                                                                                                                boolean z48 = true;
                                                                                                                ComposerImpl composerImpl9 = (ComposerImpl) composer4;
                                                                                                                if (composerImpl9.shouldExecute(iIntValue3 & 1, (iIntValue3 & 3) != 2)) {
                                                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                                                        ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextField.<anonymous>.<anonymous>.<anonymous> (CoreTextField.kt:580)");
                                                                                                                    }
                                                                                                                    final LegacyTextFieldState legacyTextFieldState12 = legacyTextFieldState11;
                                                                                                                    final Function1 function119 = function118;
                                                                                                                    final TextFieldValue textFieldValue6 = textFieldValue5;
                                                                                                                    final OffsetMapping offsetMapping11 = offsetMapping10;
                                                                                                                    final Density density6 = density5;
                                                                                                                    final int i56 = i55;
                                                                                                                    MeasurePolicy measurePolicy = new MeasurePolicy() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.5.1.1.2
                                                                                                                        @Override // androidx.compose.ui.layout.MeasurePolicy
                                                                                                                        public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i57) {
                                                                                                                            LegacyTextFieldState legacyTextFieldState13 = legacyTextFieldState12;
                                                                                                                            legacyTextFieldState13.textDelegate.layoutIntrinsics(intrinsicMeasureScope.getLayoutDirection());
                                                                                                                            MultiParagraphIntrinsics multiParagraphIntrinsics = legacyTextFieldState13.textDelegate.paragraphIntrinsics;
                                                                                                                            if (multiParagraphIntrinsics != null) {
                                                                                                                                return TextDelegateKt.ceilToIntPx(multiParagraphIntrinsics.getMaxIntrinsicWidth());
                                                                                                                            }
                                                                                                                            throw new IllegalStateException("layoutIntrinsics must be called first");
                                                                                                                        }

                                                                                                                        /* JADX WARN: Code restructure failed: missing block: B:45:0x00c0, code lost:
                                                                                                                        
                                                                                                                            if (androidx.compose.ui.unit.Constraints.m822getMaxHeightimpl(r37) == androidx.compose.ui.unit.Constraints.m822getMaxHeightimpl(r6)) goto L46;
                                                                                                                         */
                                                                                                                        /* JADX WARN: Removed duplicated region for block: B:42:0x00ae  */
                                                                                                                        /* JADX WARN: Removed duplicated region for block: B:49:0x0119  */
                                                                                                                        /* JADX WARN: Removed duplicated region for block: B:52:0x0128  */
                                                                                                                        /* JADX WARN: Removed duplicated region for block: B:54:0x0131  */
                                                                                                                        /* JADX WARN: Removed duplicated region for block: B:57:0x013c  */
                                                                                                                        /* JADX WARN: Removed duplicated region for block: B:62:0x014d  */
                                                                                                                        @Override // androidx.compose.ui.layout.MeasurePolicy
                                                                                                                        /* renamed from: measure-3p2s80s */
                                                                                                                        /*
                                                                                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                                                                                        */
                                                                                                                        public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j9) {
                                                                                                                            long j10;
                                                                                                                            TextLayoutResult textLayoutResult;
                                                                                                                            TextLayoutResultProxy textLayoutResultProxy;
                                                                                                                            boolean z49;
                                                                                                                            TextLayoutResult textLayoutResult2;
                                                                                                                            AnonymousClass2 anonymousClass2;
                                                                                                                            Snapshot.Companion companion3 = Snapshot.Companion;
                                                                                                                            LegacyTextFieldState legacyTextFieldState13 = legacyTextFieldState12;
                                                                                                                            companion3.getClass();
                                                                                                                            Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                                                                                                            Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                                                                                                                            Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                                                                                                            try {
                                                                                                                                TextLayoutResultProxy layoutResult = legacyTextFieldState13.getLayoutResult();
                                                                                                                                TextLayoutResult textLayoutResult3 = layoutResult != null ? layoutResult.value : null;
                                                                                                                                TextFieldDelegate.Companion companion4 = TextFieldDelegate.Companion;
                                                                                                                                TextDelegate textDelegate3 = legacyTextFieldState13.textDelegate;
                                                                                                                                LayoutDirection layoutDirection = measureScope.getLayoutDirection();
                                                                                                                                companion4.getClass();
                                                                                                                                int i57 = textDelegate3.overflow;
                                                                                                                                boolean z50 = textDelegate3.softWrap;
                                                                                                                                int i58 = textDelegate3.maxLines;
                                                                                                                                if (textLayoutResult3 != null) {
                                                                                                                                    List list2 = textDelegate3.placeholders;
                                                                                                                                    if (textLayoutResult3.multiParagraph.intrinsics.getHasStaleResolvedFonts()) {
                                                                                                                                        j10 = j9;
                                                                                                                                        textLayoutResult = textLayoutResult3;
                                                                                                                                        textLayoutResultProxy = layoutResult;
                                                                                                                                        z49 = z50;
                                                                                                                                        textDelegate3.layoutIntrinsics(layoutDirection);
                                                                                                                                        int iM825getMinWidthimpl3 = Constraints.m825getMinWidthimpl(j10);
                                                                                                                                        if (z49) {
                                                                                                                                            TextOverflow.Companion.getClass();
                                                                                                                                            if (i57 == TextOverflow.Ellipsis) {
                                                                                                                                                int iM823getMaxWidthimpl = Constraints.m819getHasBoundedWidthimpl(j10) ? Constraints.m823getMaxWidthimpl(j10) : Integer.MAX_VALUE;
                                                                                                                                                if (!z49) {
                                                                                                                                                    TextOverflow.Companion.getClass();
                                                                                                                                                    int i59 = i57 == TextOverflow.Ellipsis ? 1 : i58;
                                                                                                                                                    if (iM825getMinWidthimpl3 != iM823getMaxWidthimpl) {
                                                                                                                                                        MultiParagraphIntrinsics multiParagraphIntrinsics = textDelegate3.paragraphIntrinsics;
                                                                                                                                                        if (multiParagraphIntrinsics == null) {
                                                                                                                                                            throw new IllegalStateException("layoutIntrinsics must be called first");
                                                                                                                                                        }
                                                                                                                                                        iM823getMaxWidthimpl = RangesKt___RangesKt.coerceIn(TextDelegateKt.ceilToIntPx(multiParagraphIntrinsics.getMaxIntrinsicWidth()), iM825getMinWidthimpl3, iM823getMaxWidthimpl);
                                                                                                                                                    }
                                                                                                                                                    MultiParagraphIntrinsics multiParagraphIntrinsics2 = textDelegate3.paragraphIntrinsics;
                                                                                                                                                    if (multiParagraphIntrinsics2 == null) {
                                                                                                                                                        throw new IllegalStateException("layoutIntrinsics must be called first");
                                                                                                                                                    }
                                                                                                                                                    Constraints.Companion companion5 = Constraints.Companion;
                                                                                                                                                    int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j10);
                                                                                                                                                    companion5.getClass();
                                                                                                                                                    IntSize.Companion companion6 = IntSize.Companion;
                                                                                                                                                    textLayoutResult2 = new TextLayoutResult(new TextLayoutInput(textDelegate3.text, textDelegate3.style, textDelegate3.placeholders, textDelegate3.maxLines, textDelegate3.softWrap, textDelegate3.overflow, textDelegate3.density, layoutDirection, textDelegate3.fontFamilyResolver, j10, (DefaultConstructorMarker) null), new MultiParagraph(multiParagraphIntrinsics2, Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(0, iM823getMaxWidthimpl, 0, iM822getMaxHeightimpl), i59, textDelegate3.overflow, (DefaultConstructorMarker) null), ConstraintsKt.m831constrain4WqzIAM(j10, (TextDelegateKt.ceilToIntPx(r26.height) & 4294967295L) | (TextDelegateKt.ceilToIntPx(r26.width) << 32)), null);
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    } else {
                                                                                                                                        TextLayoutInput textLayoutInput = textLayoutResult3.layoutInput;
                                                                                                                                        if (Intrinsics.areEqual(textLayoutInput.text, textDelegate3.text) && textLayoutInput.style.hasSameLayoutAffectingAttributes(textDelegate3.style) && Intrinsics.areEqual(textLayoutInput.placeholders, list2) && textLayoutInput.maxLines == i58 && textLayoutInput.softWrap == z50) {
                                                                                                                                            TextOverflow.Companion companion7 = TextOverflow.Companion;
                                                                                                                                            if (textLayoutInput.overflow == i57 && Intrinsics.areEqual(textLayoutInput.density, textDelegate3.density) && textLayoutInput.layoutDirection == layoutDirection && Intrinsics.areEqual(textLayoutInput.fontFamilyResolver, textDelegate3.fontFamilyResolver)) {
                                                                                                                                                int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(j9);
                                                                                                                                                z49 = z50;
                                                                                                                                                long j11 = textLayoutInput.constraints;
                                                                                                                                                if (iM825getMinWidthimpl2 == Constraints.m825getMinWidthimpl(j11)) {
                                                                                                                                                    if (!z49) {
                                                                                                                                                        TextOverflow.Companion.getClass();
                                                                                                                                                        if (i57 == TextOverflow.Ellipsis) {
                                                                                                                                                            if (Constraints.m823getMaxWidthimpl(j9) == Constraints.m823getMaxWidthimpl(j11)) {
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        textLayoutResult = textLayoutResult3;
                                                                                                                                                        textLayoutResultProxy = layoutResult;
                                                                                                                                                        IntSize.Companion companion8 = IntSize.Companion;
                                                                                                                                                        textLayoutResult2 = new TextLayoutResult(new TextLayoutInput(textLayoutInput.text, textDelegate3.style, textLayoutInput.placeholders, textLayoutInput.maxLines, textLayoutInput.softWrap, textLayoutInput.overflow, textLayoutInput.density, textLayoutInput.layoutDirection, textLayoutInput.fontFamilyResolver, j9, (DefaultConstructorMarker) null), textLayoutResult.multiParagraph, ConstraintsKt.m831constrain4WqzIAM(j9, (TextDelegateKt.ceilToIntPx(r11.width) << 32) | (TextDelegateKt.ceilToIntPx(r11.height) & 4294967295L)), null);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                j10 = j9;
                                                                                                                                                textLayoutResult = textLayoutResult3;
                                                                                                                                                textLayoutResultProxy = layoutResult;
                                                                                                                                            }
                                                                                                                                            textDelegate3.layoutIntrinsics(layoutDirection);
                                                                                                                                            int iM825getMinWidthimpl32 = Constraints.m825getMinWidthimpl(j10);
                                                                                                                                            if (z49) {
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                TextLayoutResult textLayoutResult4 = textLayoutResult2;
                                                                                                                                long j12 = textLayoutResult4.size;
                                                                                                                                Triple triple = new Triple(Integer.valueOf((int) (j12 >> 32)), Integer.valueOf((int) (j12 & 4294967295L)), textLayoutResult4);
                                                                                                                                int iIntValue4 = ((Number) triple.component1()).intValue();
                                                                                                                                int iIntValue5 = ((Number) triple.component2()).intValue();
                                                                                                                                TextLayoutResult textLayoutResult5 = (TextLayoutResult) triple.component3();
                                                                                                                                if (Intrinsics.areEqual(textLayoutResult, textLayoutResult5)) {
                                                                                                                                    anonymousClass2 = this;
                                                                                                                                } else {
                                                                                                                                    TextLayoutResultProxy textLayoutResultProxy2 = textLayoutResultProxy;
                                                                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState13.layoutResultState).setValue(new TextLayoutResultProxy(textLayoutResult5, null, textLayoutResultProxy2 != null ? textLayoutResultProxy2.decorationBoxCoordinates : null, 2, null));
                                                                                                                                    legacyTextFieldState13.isLayoutResultStale = false;
                                                                                                                                    anonymousClass2 = this;
                                                                                                                                    function119.mo781invoke(textLayoutResult5);
                                                                                                                                    CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState13, textFieldValue6, offsetMapping11);
                                                                                                                                }
                                                                                                                                ((SnapshotMutableStateImpl) legacyTextFieldState13.minHeightForSingleLineField$delegate).setValue(Dp.m837boximpl(density6.mo55toDpu2uoSUM(i56 == 1 ? TextDelegateKt.ceilToIntPx(textLayoutResult5.multiParagraph.getLineBottom(0)) : 0)));
                                                                                                                                return measureScope.layout$1(iIntValue4, iIntValue5, MapsKt__MapsKt.mapOf(new Pair(AlignmentLineKt.FirstBaseline, Integer.valueOf(Math.round(textLayoutResult5.firstBaseline))), new Pair(AlignmentLineKt.LastBaseline, Integer.valueOf(Math.round(textLayoutResult5.lastBaseline)))), new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$5$1$1$2$measure$2
                                                                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                                                                    /* renamed from: invoke */
                                                                                                                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj22) {
                                                                                                                                        return Unit.INSTANCE;
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            } finally {
                                                                                                                                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    };
                                                                                                                    Modifier.Companion companion3 = Modifier.Companion;
                                                                                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl9);
                                                                                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl9.currentCompositionLocalScope();
                                                                                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl9, companion3);
                                                                                                                    ComposeUiNode.Companion.getClass();
                                                                                                                    Function0 function04 = ComposeUiNode.Companion.Constructor;
                                                                                                                    if (composerImpl9.applier == null) {
                                                                                                                        ComposablesKt.invalidApplier();
                                                                                                                        throw null;
                                                                                                                    }
                                                                                                                    composerImpl9.startReusableNode();
                                                                                                                    if (composerImpl9.inserting) {
                                                                                                                        composerImpl9.createNode(function04);
                                                                                                                    } else {
                                                                                                                        composerImpl9.useNode();
                                                                                                                    }
                                                                                                                    Updater.m337setimpl(composerImpl9, measurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                                                                    Updater.m337setimpl(composerImpl9, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                                                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                                                                    if (composerImpl9.inserting || !Intrinsics.areEqual(composerImpl9.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl9, currentCompositeKeyHash, function2);
                                                                                                                    }
                                                                                                                    Updater.m337setimpl(composerImpl9, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                                                                    composerImpl9.end(true);
                                                                                                                    TextFieldSelectionManager textFieldSelectionManager14 = textFieldSelectionManager13;
                                                                                                                    if (legacyTextFieldState11.getHandleState() == HandleState.None || legacyTextFieldState11.getLayoutCoordinates() == null) {
                                                                                                                        z48 = false;
                                                                                                                        CoreTextFieldKt.access$SelectionToolbarAndHandles(textFieldSelectionManager14, z48, composerImpl9, 0);
                                                                                                                        if (legacyTextFieldState11.getHandleState() == HandleState.Cursor || z47 || !z46) {
                                                                                                                            composerImpl9.startReplaceGroup(-7037410);
                                                                                                                            composerImpl9.end(false);
                                                                                                                        } else {
                                                                                                                            composerImpl9.startReplaceGroup(-7114290);
                                                                                                                            CoreTextFieldKt.TextFieldCursorHandle(textFieldSelectionManager13, composerImpl9, 0);
                                                                                                                            composerImpl9.end(false);
                                                                                                                        }
                                                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                                                            ComposerKt.traceEventEnd();
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        LayoutCoordinates layoutCoordinates = legacyTextFieldState11.getLayoutCoordinates();
                                                                                                                        layoutCoordinates.getClass();
                                                                                                                        if (!layoutCoordinates.isAttached() || !z46) {
                                                                                                                        }
                                                                                                                        CoreTextFieldKt.access$SelectionToolbarAndHandles(textFieldSelectionManager14, z48, composerImpl9, 0);
                                                                                                                        if (legacyTextFieldState11.getHandleState() == HandleState.Cursor) {
                                                                                                                            composerImpl9.startReplaceGroup(-7037410);
                                                                                                                            composerImpl9.end(false);
                                                                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    composerImpl9.skipToGroupEnd();
                                                                                                                }
                                                                                                                return Unit.INSTANCE;
                                                                                                            }
                                                                                                        }, composerImpl8), composerImpl8, 48, 0);
                                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                                            ComposerKt.traceEventEnd();
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    composerImpl8.skipToGroupEnd();
                                                                                                }
                                                                                                return Unit.INSTANCE;
                                                                                            }
                                                                                        }, composerImpl7), composerImpl7, 6);
                                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                                            ComposerKt.traceEventEnd();
                                                                                        }
                                                                                    } else {
                                                                                        composerImpl7.skipToGroupEnd();
                                                                                    }
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }, composerImpl62), composerImpl62, 384);
                                                                            if (ComposerKt.isTraceInProgress()) {
                                                                            }
                                                                            i26 = z30 ? 1 : 0;
                                                                            composerImpl = composerImpl62;
                                                                            z9 = z19;
                                                                            function14 = function15;
                                                                            textFieldScrollerPosition2 = textFieldScrollerPosition3;
                                                                            keyboardActions2 = keyboardActions4;
                                                                            mutableInteractionSource3 = mutableInteractionSource5;
                                                                            brush2 = brush72;
                                                                            modifier2 = modifier6;
                                                                            i27 = z33 ? 1 : 0;
                                                                            imeOptions2 = imeOptions8;
                                                                            z7 = z15;
                                                                            z8 = z422;
                                                                            visualTransformation3 = visualTransformation6;
                                                                            function32 = function35;
                                                                            textStyle3 = textStyle102;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            offsetMapping3 = offsetMapping2;
                                                        }
                                                    }
                                                    brush4 = brush6;
                                                    modifierComposed = TextFieldMagnifier;
                                                    zChangedInstance6 = composerImpl2.changedInstance(textFieldSelectionManager11);
                                                    final Modifier modifier72 = modifierComposed;
                                                    Object objRememberedValue172 = composerImpl2.rememberedValue();
                                                    obj6 = objRememberedValue172;
                                                    if (zChangedInstance6) {
                                                        Function1 function1112 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$3$1
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj16) {
                                                                final TextFieldSelectionManager textFieldSelectionManager12 = textFieldSelectionManager11;
                                                                return new DisposableEffectResult() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$3$1$invoke$$inlined$onDispose$1
                                                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                                                    public final void dispose() {
                                                                        textFieldSelectionManager12.hideSelectionToolbar$foundation_release();
                                                                    }
                                                                };
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(function1112);
                                                        obj6 = function1112;
                                                        EffectsKt.DisposableEffect(textFieldSelectionManager11, (Function1) obj6, composerImpl2);
                                                        if (i33 <= 32) {
                                                            zChangedInstance7 = composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changedInstance(textInputService3) | (i46 != 4) | ((i33 <= 32 && composerImpl2.changed(imeOptions8)) || (i31 & 48) == 32);
                                                            Object objRememberedValue182 = composerImpl2.rememberedValue();
                                                            obj7 = objRememberedValue182;
                                                            if (zChangedInstance7) {
                                                                Function1 function1122 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$4$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    /* JADX WARN: Type inference failed for: r7v3, types: [T, androidx.compose.ui.text.input.TextInputSession, java.lang.Object] */
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj16) {
                                                                        if (legacyTextFieldState2.getHasFocus()) {
                                                                            LegacyTextFieldState legacyTextFieldState72 = legacyTextFieldState2;
                                                                            TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                            TextInputService textInputService7 = textInputService3;
                                                                            TextFieldValue textFieldValue3 = textFieldValue;
                                                                            EditProcessor editProcessor2 = legacyTextFieldState72.processor;
                                                                            ImeOptions imeOptions9 = imeOptions8;
                                                                            Function1 function1132 = legacyTextFieldState72.onValueChange;
                                                                            Function1 function1142 = legacyTextFieldState72.onImeActionPerformed;
                                                                            companion2.getClass();
                                                                            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                                                            TextFieldDelegate$Companion$restartInput$1 textFieldDelegate$Companion$restartInput$1 = new TextFieldDelegate$Companion$restartInput$1(editProcessor2, function1132, ref$ObjectRef);
                                                                            PlatformTextInputService platformTextInputService = textInputService7.platformTextInputService;
                                                                            platformTextInputService.startInput(textFieldValue3, imeOptions9, textFieldDelegate$Companion$restartInput$1, function1142);
                                                                            ?? textInputSession2 = new TextInputSession(textInputService7, platformTextInputService);
                                                                            textInputService7._currentInputSession.set(textInputSession2);
                                                                            ref$ObjectRef.element = textInputSession2;
                                                                            legacyTextFieldState72.inputSession = textInputSession2;
                                                                        }
                                                                        return new DisposableEffectResult() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$4$1$invoke$$inlined$onDispose$1
                                                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                                                            public final void dispose() {
                                                                            }
                                                                        };
                                                                    }
                                                                };
                                                                composerImpl2.updateRememberedValue(function1122);
                                                                obj7 = function1122;
                                                                EffectsKt.DisposableEffect(imeOptions8, (Function1) obj7, composerImpl2);
                                                                final Function1 function1132 = legacyTextFieldState2.onValueChange;
                                                                if (!z33) {
                                                                }
                                                                final int i472 = imeOptions8.imeAction;
                                                                final OffsetMapping offsetMapping82 = offsetMapping3;
                                                                final boolean z412 = z40;
                                                                final LegacyTextFieldState legacyTextFieldState72 = legacyTextFieldState2;
                                                                Function3 function372 = new Function3() { // from class: androidx.compose.foundation.text.TextFieldKeyInputKt$textFieldKeyInput$2
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(3);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function3
                                                                    public final Object invoke(Object obj16, Object obj17, Object obj18) {
                                                                        ((Number) obj18).intValue();
                                                                        ComposerImpl composerImpl63 = (ComposerImpl) ((Composer) obj17);
                                                                        composerImpl63.startReplaceGroup(851809892);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventStart("androidx.compose.foundation.text.textFieldKeyInput.<anonymous> (TextFieldKeyInput.kt:252)");
                                                                        }
                                                                        Object objRememberedValue193 = composerImpl63.rememberedValue();
                                                                        Composer.Companion.getClass();
                                                                        Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                                                                        if (objRememberedValue193 == composer$Companion$Empty$12) {
                                                                            objRememberedValue193 = new TextPreparedSelectionState();
                                                                            composerImpl63.updateRememberedValue(objRememberedValue193);
                                                                        }
                                                                        TextPreparedSelectionState textPreparedSelectionState = (TextPreparedSelectionState) objRememberedValue193;
                                                                        Object objRememberedValue203 = composerImpl63.rememberedValue();
                                                                        if (objRememberedValue203 == composer$Companion$Empty$12) {
                                                                            objRememberedValue203 = new DeadKeyCombiner();
                                                                            composerImpl63.updateRememberedValue(objRememberedValue203);
                                                                        }
                                                                        TextFieldKeyInput textFieldKeyInput = new TextFieldKeyInput(legacyTextFieldState72, textFieldSelectionManager11, textFieldValue, z35, z412, textPreparedSelectionState, offsetMapping82, undoManager, (DeadKeyCombiner) objRememberedValue203, null, function1132, i472, 512, null);
                                                                        Modifier.Companion companion2 = Modifier.Companion;
                                                                        boolean zChangedInstance10 = composerImpl63.changedInstance(textFieldKeyInput);
                                                                        Object objRememberedValue21 = composerImpl63.rememberedValue();
                                                                        if (zChangedInstance10 || objRememberedValue21 == composer$Companion$Empty$12) {
                                                                            objRememberedValue21 = new TextFieldKeyInputKt$textFieldKeyInput$2$1$1(textFieldKeyInput);
                                                                            composerImpl63.updateRememberedValue(objRememberedValue21);
                                                                        }
                                                                        Modifier modifierOnKeyEvent = KeyInputModifierKt.onKeyEvent(companion2, (Function1) ((KFunction) objRememberedValue21));
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                        composerImpl63.end(false);
                                                                        return modifierOnKeyEvent;
                                                                    }
                                                                };
                                                                Function1 function1142 = InspectableValueKt.NoInspectorInfo;
                                                                Modifier modifierComposed22 = ComposedModifierKt.composed(TextFieldMagnifier, function1142, function372);
                                                                KeyboardType.Companion.getClass();
                                                                if (imeOptions8.keyboardType != KeyboardType.Password) {
                                                                }
                                                            }
                                                        } else {
                                                            zChangedInstance7 = composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changedInstance(textInputService3) | (i46 != 4) | ((i33 <= 32 && composerImpl2.changed(imeOptions8)) || (i31 & 48) == 32);
                                                            Object objRememberedValue1822 = composerImpl2.rememberedValue();
                                                            obj7 = objRememberedValue1822;
                                                            if (zChangedInstance7) {
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    textFieldSelectionManager2 = textFieldSelectionManager;
                                                }
                                                z20 = true;
                                                z21 = zChanged4 | z20;
                                                objRememberedValue6 = composerImpl2.rememberedValue();
                                                if (z21) {
                                                    TextFieldSelectionManager textFieldSelectionManager92 = textFieldSelectionManager2;
                                                    objRememberedValue6 = new CoreTextFieldKt$CoreTextField$2$1(legacyTextFieldState2, mutableStateRememberUpdatedState, textInputService3, textFieldSelectionManager92, imeOptions5, null);
                                                    mutableState = mutableStateRememberUpdatedState;
                                                    textFieldSelectionManager3 = textFieldSelectionManager92;
                                                    composerImpl2.updateRememberedValue(objRememberedValue6);
                                                    EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) objRememberedValue6);
                                                    zChangedInstance2 = composerImpl2.changedInstance(legacyTextFieldState2);
                                                    Object objRememberedValue152 = composerImpl2.rememberedValue();
                                                    obj4 = objRememberedValue152;
                                                    if (!zChangedInstance2) {
                                                        Function1 function182 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$pointerModifier$1$1
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj16) {
                                                                Boolean bool = (Boolean) obj16;
                                                                bool.booleanValue();
                                                                ((SnapshotMutableStateImpl) legacyTextFieldState2.isInTouchMode$delegate).setValue(bool);
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl2.updateRememberedValue(function182);
                                                        obj4 = function182;
                                                        Modifier modifierUpdateSelectionTouchMode2 = SelectionGesturesKt.updateSelectionTouchMode(TextFieldMagnifier, (Function1) obj4);
                                                        zChangedInstance3 = composerImpl2.changedInstance(legacyTextFieldState2) | (i45 != 16384) | (i44 != 2048) | composerImpl2.changedInstance(offsetMapping) | composerImpl2.changedInstance(textFieldSelectionManager3);
                                                        objRememberedValue7 = composerImpl2.rememberedValue();
                                                        if (zChangedInstance3) {
                                                            final TextFieldSelectionManager textFieldSelectionManager102 = textFieldSelectionManager3;
                                                            final OffsetMapping offsetMapping62 = offsetMapping;
                                                            final LegacyTextFieldState legacyTextFieldState52 = legacyTextFieldState2;
                                                            final boolean z392 = z19;
                                                            objRememberedValue7 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$pointerModifier$2$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj16) {
                                                                    SoftwareKeyboardController softwareKeyboardController2;
                                                                    long j7 = ((Offset) obj16).packedValue;
                                                                    LegacyTextFieldState legacyTextFieldState62 = legacyTextFieldState52;
                                                                    FocusRequester focusRequester5 = focusRequester4;
                                                                    boolean z402 = z392;
                                                                    if (!legacyTextFieldState62.getHasFocus()) {
                                                                        FocusRequester.m378requestFocus3ESFkO8$default(focusRequester5);
                                                                    } else if (!z402 && (softwareKeyboardController2 = legacyTextFieldState62.keyboardController) != null) {
                                                                        ((DelegatingSoftwareKeyboardController) softwareKeyboardController2).show();
                                                                    }
                                                                    if (legacyTextFieldState52.getHasFocus() && z38) {
                                                                        if (legacyTextFieldState52.getHandleState() != HandleState.Selection) {
                                                                            TextLayoutResultProxy layoutResult = legacyTextFieldState52.getLayoutResult();
                                                                            if (layoutResult != null) {
                                                                                LegacyTextFieldState legacyTextFieldState73 = legacyTextFieldState52;
                                                                                OffsetMapping offsetMapping72 = offsetMapping62;
                                                                                TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                                EditProcessor editProcessor2 = legacyTextFieldState73.processor;
                                                                                Function1 function192 = legacyTextFieldState73.onValueChange;
                                                                                companion2.getClass();
                                                                                int iTransformedToOriginal = offsetMapping72.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(j7, true));
                                                                                ((LegacyTextFieldState$onValueChange$1) function192).mo781invoke(TextFieldValue.m780copy3r_uNRQ$default(editProcessor2.mBufferState, null, TextRangeKt.TextRange(iTransformedToOriginal, iTransformedToOriginal), 5));
                                                                                if (legacyTextFieldState73.textDelegate.text.text.length() > 0) {
                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState73.handleState$delegate).setValue(HandleState.Cursor);
                                                                                }
                                                                            }
                                                                        } else {
                                                                            textFieldSelectionManager102.m239deselect_kEHs6E$foundation_release(Offset.m395boximpl(j7));
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                            focusRequester2 = focusRequester4;
                                                            z22 = z38;
                                                            textFieldSelectionManager4 = textFieldSelectionManager102;
                                                            composerImpl2.updateRememberedValue(objRememberedValue7);
                                                            final Function1 function192 = (Function1) objRememberedValue7;
                                                            if (z22) {
                                                            }
                                                            Modifier modifierSelectionGestureInput2 = SelectionGesturesKt.selectionGestureInput(modifierUpdateSelectionTouchMode2, textFieldSelectionManager4.mouseSelectionObserver, textFieldSelectionManager4.touchSelectionObserver);
                                                            PointerIcon.Companion.getClass();
                                                            Modifier modifierThen3 = modifierSelectionGestureInput2.then(new PointerHoverIconModifierElement(PointerIcon.Companion.Text, false));
                                                            zChangedInstance4 = composerImpl2.changedInstance(legacyTextFieldState2) | (i46 != 4) | composerImpl2.changedInstance(offsetMapping);
                                                            Object objRememberedValue162 = composerImpl2.rememberedValue();
                                                            if (zChangedInstance4) {
                                                                Function1 function1102 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$drawModifier$1$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    /* JADX WARN: Multi-variable type inference failed */
                                                                    /* JADX WARN: Removed duplicated region for block: B:30:0x00bf  */
                                                                    /* JADX WARN: Type inference failed for: r8v1 */
                                                                    /* JADX WARN: Type inference failed for: r8v10 */
                                                                    /* JADX WARN: Type inference failed for: r8v12 */
                                                                    /* JADX WARN: Type inference failed for: r8v13 */
                                                                    /* JADX WARN: Type inference failed for: r8v14 */
                                                                    /* JADX WARN: Type inference failed for: r8v15 */
                                                                    /* JADX WARN: Type inference failed for: r8v16 */
                                                                    /* JADX WARN: Type inference failed for: r8v17 */
                                                                    /* JADX WARN: Type inference failed for: r8v2 */
                                                                    /* JADX WARN: Type inference failed for: r8v3 */
                                                                    /* JADX WARN: Type inference failed for: r8v5 */
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    /*
                                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                                    */
                                                                    public final Object mo781invoke(Object obj16) throws Throwable {
                                                                        TextLayoutResult textLayoutResult;
                                                                        long j7;
                                                                        ?? r8;
                                                                        long jMo794getColor0d7_KjU;
                                                                        Canvas canvas;
                                                                        DrawScope drawScope = (DrawScope) obj16;
                                                                        TextLayoutResultProxy layoutResult = legacyTextFieldState2.getLayoutResult();
                                                                        if (layoutResult != null) {
                                                                            TextFieldValue textFieldValue3 = textFieldValue;
                                                                            LegacyTextFieldState legacyTextFieldState62 = legacyTextFieldState2;
                                                                            OffsetMapping offsetMapping72 = offsetMapping;
                                                                            Canvas canvas2 = drawScope.getDrawContext().getCanvas();
                                                                            TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                            long j8 = ((TextRange) ((SnapshotMutableStateImpl) legacyTextFieldState62.selectionPreviewHighlightRange$delegate).getValue()).packedValue;
                                                                            long j9 = ((TextRange) ((SnapshotMutableStateImpl) legacyTextFieldState62.deletionPreviewHighlightRange$delegate).getValue()).packedValue;
                                                                            long j10 = legacyTextFieldState62.selectionBackgroundColor;
                                                                            companion2.getClass();
                                                                            boolean zM749getCollapsedimpl = TextRange.m749getCollapsedimpl(j8);
                                                                            AndroidPaint androidPaint = legacyTextFieldState62.highlightPaint;
                                                                            TextLayoutResult textLayoutResult2 = layoutResult.value;
                                                                            if (zM749getCollapsedimpl) {
                                                                                AndroidPaint androidPaint2 = androidPaint;
                                                                                if (TextRange.m749getCollapsedimpl(j9)) {
                                                                                    textLayoutResult = textLayoutResult2;
                                                                                    r8 = androidPaint2;
                                                                                    if (!TextRange.m749getCollapsedimpl(textFieldValue3.selection)) {
                                                                                        androidPaint2.m440setColor8_81llA(j10);
                                                                                        TextFieldDelegate.Companion.m205drawHighlightLepunE(canvas2, textFieldValue3.selection, offsetMapping72, textLayoutResult, androidPaint2);
                                                                                        r8 = androidPaint2;
                                                                                    }
                                                                                } else {
                                                                                    Color colorM456boximpl = Color.m456boximpl(textLayoutResult2.layoutInput.style.m758getColor0d7_KjU());
                                                                                    if (colorM456boximpl.value == 16) {
                                                                                        colorM456boximpl = null;
                                                                                    }
                                                                                    if (colorM456boximpl != null) {
                                                                                        j7 = colorM456boximpl.value;
                                                                                    } else {
                                                                                        Color.Companion.getClass();
                                                                                        j7 = Color.Black;
                                                                                    }
                                                                                    androidPaint2.m440setColor8_81llA(ColorKt.Color(Color.m463getRedimpl(j7), Color.m462getGreenimpl(j7), Color.m460getBlueimpl(j7), Color.m459getAlphaimpl(j7) * 0.2f, Color.m461getColorSpaceimpl(j7)));
                                                                                    textLayoutResult = textLayoutResult2;
                                                                                    TextFieldDelegate.Companion.m205drawHighlightLepunE(canvas2, j9, offsetMapping72, textLayoutResult, androidPaint2);
                                                                                    r8 = androidPaint2;
                                                                                }
                                                                            } else {
                                                                                androidPaint.m440setColor8_81llA(j10);
                                                                                AndroidPaint androidPaint3 = androidPaint;
                                                                                textLayoutResult = textLayoutResult2;
                                                                                TextFieldDelegate.Companion.m205drawHighlightLepunE(canvas2, j8, offsetMapping72, textLayoutResult, androidPaint3);
                                                                                r8 = androidPaint3;
                                                                            }
                                                                            TextPainter.INSTANCE.getClass();
                                                                            boolean z402 = textLayoutResult.getDidOverflowWidth() || textLayoutResult.getDidOverflowHeight();
                                                                            TextLayoutInput textLayoutInput = textLayoutResult.layoutInput;
                                                                            if (z402) {
                                                                                int i473 = textLayoutInput.overflow;
                                                                                TextOverflow.Companion.getClass();
                                                                                boolean z413 = i473 != TextOverflow.Visible;
                                                                                if (z413) {
                                                                                    long j11 = textLayoutResult.size;
                                                                                    Offset.Companion.getClass();
                                                                                    Size.Companion companion3 = Size.Companion;
                                                                                    r8 = 0;
                                                                                    Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw(0L, (Float.floatToRawIntBits((int) (j11 >> 32)) << 32) | (4294967295L & Float.floatToRawIntBits((int) (j11 & 4294967295L))));
                                                                                    canvas2.save();
                                                                                    Canvas.m455clipRectmtrdDE$default(canvas2, rectM413Recttz77jQw);
                                                                                }
                                                                                SpanStyle spanStyle = textLayoutInput.style.spanStyle;
                                                                                TextDecoration textDecoration = spanStyle.textDecoration;
                                                                                TextForegroundStyle textForegroundStyle = spanStyle.textForegroundStyle;
                                                                                if (textDecoration == null) {
                                                                                    TextDecoration.Companion.getClass();
                                                                                    textDecoration = TextDecoration.None;
                                                                                }
                                                                                TextDecoration textDecoration2 = textDecoration;
                                                                                Shadow shadow = spanStyle.shadow;
                                                                                if (shadow == null) {
                                                                                    Shadow.Companion.getClass();
                                                                                    shadow = Shadow.None;
                                                                                }
                                                                                Shadow shadow2 = shadow;
                                                                                DrawStyle drawStyle = spanStyle.drawStyle;
                                                                                if (drawStyle == null) {
                                                                                    drawStyle = Fill.INSTANCE;
                                                                                }
                                                                                DrawStyle drawStyle2 = drawStyle;
                                                                                try {
                                                                                    Brush brush73 = textForegroundStyle.getBrush();
                                                                                    try {
                                                                                        if (brush73 != null) {
                                                                                            Canvas canvas3 = canvas2;
                                                                                            MultiParagraph.m734painthn5TExg$default(textLayoutResult.multiParagraph, canvas3, brush73, textForegroundStyle != TextForegroundStyle.Unspecified.INSTANCE ? textForegroundStyle.getAlpha() : 1.0f, shadow2, textDecoration2, drawStyle2);
                                                                                            canvas = canvas3;
                                                                                            r8 = canvas3;
                                                                                        } else {
                                                                                            if (textForegroundStyle != TextForegroundStyle.Unspecified.INSTANCE) {
                                                                                                jMo794getColor0d7_KjU = textForegroundStyle.mo794getColor0d7_KjU();
                                                                                            } else {
                                                                                                Color.Companion.getClass();
                                                                                                jMo794getColor0d7_KjU = Color.Black;
                                                                                            }
                                                                                            Canvas canvas4 = canvas2;
                                                                                            MultiParagraph.m733paintLG529CI$default(textLayoutResult.multiParagraph, canvas4, jMo794getColor0d7_KjU, shadow2, textDecoration2, drawStyle2);
                                                                                            canvas = canvas4;
                                                                                            r8 = canvas4;
                                                                                        }
                                                                                        if (z413) {
                                                                                            canvas.restore();
                                                                                        }
                                                                                    } catch (Throwable th) {
                                                                                        th = th;
                                                                                        canvas2 = r8;
                                                                                        Throwable th2 = th;
                                                                                        if (!z413) {
                                                                                            throw th2;
                                                                                        }
                                                                                        canvas2.restore();
                                                                                        throw th2;
                                                                                    }
                                                                                } catch (Throwable th3) {
                                                                                    th = th3;
                                                                                }
                                                                            }
                                                                        }
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                };
                                                                composerImpl2.updateRememberedValue(function1102);
                                                                obj5 = function1102;
                                                                final Modifier modifierDrawBehind2 = DrawModifierKt.drawBehind(TextFieldMagnifier, (Function1) obj5);
                                                                textFieldSelectionManager5 = textFieldSelectionManager4;
                                                                zChangedInstance5 = (i46 != 4) | composerImpl2.changedInstance(legacyTextFieldState2) | (i44 != 2048) | composerImpl2.changed(windowInfo2) | composerImpl2.changedInstance(textFieldSelectionManager4) | composerImpl2.changedInstance(offsetMapping);
                                                                objRememberedValue8 = composerImpl2.rememberedValue();
                                                                if (zChangedInstance5) {
                                                                    final OffsetMapping offsetMapping72 = offsetMapping;
                                                                    final LegacyTextFieldState legacyTextFieldState62 = legacyTextFieldState2;
                                                                    objRememberedValue8 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$onPositionedModifier$1$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj16) {
                                                                            LayoutCoordinates layoutCoordinates;
                                                                            LayoutCoordinates layoutCoordinates2 = (LayoutCoordinates) obj16;
                                                                            LegacyTextFieldState legacyTextFieldState73 = legacyTextFieldState62;
                                                                            legacyTextFieldState73._layoutCoordinates = layoutCoordinates2;
                                                                            TextLayoutResultProxy layoutResult = legacyTextFieldState73.getLayoutResult();
                                                                            if (layoutResult != null) {
                                                                                layoutResult.innerTextFieldCoordinates = layoutCoordinates2;
                                                                            }
                                                                            if (z23) {
                                                                                if (legacyTextFieldState62.getHandleState() == HandleState.Selection) {
                                                                                    if (((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState62.showFloatingToolbar$delegate).getValue()).booleanValue() && windowInfo2.isWindowFocused()) {
                                                                                        textFieldSelectionManager5.showSelectionToolbar$foundation_release();
                                                                                    } else {
                                                                                        textFieldSelectionManager5.hideSelectionToolbar$foundation_release();
                                                                                    }
                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState62.showSelectionHandleStart$delegate).setValue(Boolean.valueOf(TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager5, true)));
                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState62.showSelectionHandleEnd$delegate).setValue(Boolean.valueOf(TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager5, false)));
                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState62.showCursorHandle$delegate).setValue(Boolean.valueOf(TextRange.m749getCollapsedimpl(textFieldValue.selection)));
                                                                                } else if (legacyTextFieldState62.getHandleState() == HandleState.Cursor) {
                                                                                    ((SnapshotMutableStateImpl) legacyTextFieldState62.showCursorHandle$delegate).setValue(Boolean.valueOf(TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager5, true)));
                                                                                }
                                                                                CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState62, textFieldValue, offsetMapping72);
                                                                                TextLayoutResultProxy layoutResult2 = legacyTextFieldState62.getLayoutResult();
                                                                                if (layoutResult2 != null) {
                                                                                    LegacyTextFieldState legacyTextFieldState83 = legacyTextFieldState62;
                                                                                    TextFieldValue textFieldValue3 = textFieldValue;
                                                                                    OffsetMapping offsetMapping83 = offsetMapping72;
                                                                                    TextInputSession textInputSession2 = legacyTextFieldState83.inputSession;
                                                                                    if (textInputSession2 != null && legacyTextFieldState83.getHasFocus()) {
                                                                                        TextFieldDelegate.Companion.getClass();
                                                                                        final LayoutCoordinates layoutCoordinates3 = layoutResult2.innerTextFieldCoordinates;
                                                                                        if (layoutCoordinates3 != null && layoutCoordinates3.isAttached() && (layoutCoordinates = layoutResult2.decorationBoxCoordinates) != null) {
                                                                                            Function1 function1113 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldDelegate$Companion$updateTextLayoutResult$1$1$1
                                                                                                {
                                                                                                    super(1);
                                                                                                }

                                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                                /* renamed from: invoke */
                                                                                                public final Object mo781invoke(Object obj17) {
                                                                                                    float[] fArr = ((Matrix) obj17).values;
                                                                                                    if (layoutCoordinates3.isAttached()) {
                                                                                                        LayoutCoordinatesKt.findRootCoordinates(layoutCoordinates3).mo619transformFromEL8BTi8(layoutCoordinates3, fArr);
                                                                                                    }
                                                                                                    return Unit.INSTANCE;
                                                                                                }
                                                                                            };
                                                                                            Rect rectVisibleBounds = SelectionManagerKt.visibleBounds(layoutCoordinates3);
                                                                                            Rect rectLocalBoundingBoxOf = layoutCoordinates3.localBoundingBoxOf(layoutCoordinates, false);
                                                                                            if (Intrinsics.areEqual((TextInputSession) textInputSession2.textInputService._currentInputSession.get(), textInputSession2)) {
                                                                                                textInputSession2.platformTextInputService.updateTextLayoutResult(textFieldValue3, offsetMapping83, layoutResult2.value, function1113, rectVisibleBounds, rectLocalBoundingBoxOf);
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    z24 = z23;
                                                                    windowInfo = windowInfo2;
                                                                    textFieldSelectionManager6 = textFieldSelectionManager5;
                                                                    composerImpl2.updateRememberedValue(objRememberedValue8);
                                                                    final Modifier modifierOnGloballyPositioned3 = OnGloballyPositionedModifierKt.onGloballyPositioned(TextFieldMagnifier, (Function1) objRememberedValue8);
                                                                    z25 = z24;
                                                                    final TextFieldSelectionManager textFieldSelectionManager112 = textFieldSelectionManager6;
                                                                    offsetMapping2 = offsetMapping;
                                                                    final ImeOptions imeOptions82 = imeOptions5;
                                                                    coreTextFieldSemanticsModifier = new CoreTextFieldSemanticsModifier(transformedText4, textFieldValue, legacyTextFieldState2, z19, z25, visualTransformation6 instanceof PasswordVisualTransformation, offsetMapping2, textFieldSelectionManager112, imeOptions82, focusRequester2);
                                                                    if (z25) {
                                                                        offsetMapping3 = offsetMapping2;
                                                                        coreTextFieldSemanticsModifier2 = coreTextFieldSemanticsModifier;
                                                                        z26 = z25;
                                                                        brush4 = brush6;
                                                                        modifierComposed = TextFieldMagnifier;
                                                                        zChangedInstance6 = composerImpl2.changedInstance(textFieldSelectionManager112);
                                                                        final Modifier modifier722 = modifierComposed;
                                                                        Object objRememberedValue1722 = composerImpl2.rememberedValue();
                                                                        obj6 = objRememberedValue1722;
                                                                        if (zChangedInstance6) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (composerImpl2.changed(imeOptions6)) {
                                                imeOptions6 = imeOptions6;
                                                legacyTextFieldState = legacyTextFieldState4;
                                                zChangedInstance = zChangedInstance9 | z36 | composerImpl2.changedInstance(offsetMapping5) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(bringIntoViewRequester2) | composerImpl2.changedInstance(textFieldSelectionManager8);
                                                objRememberedValue5 = composerImpl2.rememberedValue();
                                                if (zChangedInstance) {
                                                }
                                            } else {
                                                imeOptions6 = imeOptions6;
                                                legacyTextFieldState = legacyTextFieldState4;
                                                if ((i31 & 48) != 32) {
                                                }
                                                zChangedInstance = zChangedInstance9 | z36 | composerImpl2.changedInstance(offsetMapping5) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(bringIntoViewRequester2) | composerImpl2.changedInstance(textFieldSelectionManager8);
                                                objRememberedValue5 = composerImpl2.rememberedValue();
                                                if (zChangedInstance) {
                                                    final TextInputService textInputService62 = textInputService2;
                                                    final boolean z372 = z10;
                                                    final ImeOptions imeOptions72 = imeOptions6;
                                                    objRememberedValue5 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$focusModifier$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        /* JADX WARN: Type inference failed for: r3v3, types: [T, androidx.compose.ui.text.input.TextInputSession, java.lang.Object] */
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj16) {
                                                            TextLayoutResultProxy layoutResult;
                                                            FocusStateImpl focusStateImpl = (FocusStateImpl) ((FocusState) obj16);
                                                            if (legacyTextFieldState.getHasFocus() != focusStateImpl.isFocused()) {
                                                                ((SnapshotMutableStateImpl) legacyTextFieldState.hasFocus$delegate).setValue(Boolean.valueOf(focusStateImpl.isFocused()));
                                                                if (legacyTextFieldState.getHasFocus() && z372 && !z31) {
                                                                    TextInputService textInputService7 = textInputService62;
                                                                    LegacyTextFieldState legacyTextFieldState53 = legacyTextFieldState;
                                                                    TextFieldValue textFieldValue3 = textFieldValue;
                                                                    ImeOptions imeOptions83 = imeOptions72;
                                                                    OffsetMapping offsetMapping63 = offsetMapping5;
                                                                    TextFieldDelegate.Companion companion2 = TextFieldDelegate.Companion;
                                                                    EditProcessor editProcessor2 = legacyTextFieldState53.processor;
                                                                    Function1 function183 = legacyTextFieldState53.onValueChange;
                                                                    Function1 function193 = legacyTextFieldState53.onImeActionPerformed;
                                                                    companion2.getClass();
                                                                    Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                                                    TextFieldDelegate$Companion$restartInput$1 textFieldDelegate$Companion$restartInput$1 = new TextFieldDelegate$Companion$restartInput$1(editProcessor2, function183, ref$ObjectRef);
                                                                    PlatformTextInputService platformTextInputService = textInputService7.platformTextInputService;
                                                                    platformTextInputService.startInput(textFieldValue3, imeOptions83, textFieldDelegate$Companion$restartInput$1, function193);
                                                                    ?? textInputSession2 = new TextInputSession(textInputService7, platformTextInputService);
                                                                    textInputService7._currentInputSession.set(textInputSession2);
                                                                    ref$ObjectRef.element = textInputSession2;
                                                                    legacyTextFieldState53.inputSession = textInputSession2;
                                                                    CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState53, textFieldValue3, offsetMapping63);
                                                                } else {
                                                                    CoreTextFieldKt.access$endInputSession(legacyTextFieldState);
                                                                }
                                                                if (focusStateImpl.isFocused() && (layoutResult = legacyTextFieldState.getLayoutResult()) != null) {
                                                                    BuildersKt.launch$default(coroutineScope, null, null, new CoreTextFieldKt$CoreTextField$focusModifier$1$1$1$1(bringIntoViewRequester2, textFieldValue, legacyTextFieldState, layoutResult, offsetMapping5, null), 3);
                                                                }
                                                                if (!focusStateImpl.isFocused()) {
                                                                    textFieldSelectionManager8.m239deselect_kEHs6E$foundation_release(null);
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    legacyTextFieldState2 = legacyTextFieldState;
                                                    z18 = z372;
                                                    z19 = z31;
                                                    textInputService3 = textInputService62;
                                                    imeOptions5 = imeOptions72;
                                                    bringIntoViewRequester = bringIntoViewRequester2;
                                                    focusManager2 = focusManager4;
                                                    visualTransformation6 = visualTransformation7;
                                                    offsetMapping = offsetMapping5;
                                                    textFieldSelectionManager = textFieldSelectionManager8;
                                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                                    Modifier modifierFocusable2 = FocusableKt.focusable(mutableInteractionSource5, FocusChangedModifierKt.onFocusChanged(FocusRequesterModifierKt.focusRequester(TextFieldMagnifier, focusRequester4), (Function1) objRememberedValue5), z18);
                                                    if (z18) {
                                                        final boolean z382 = z18;
                                                        MutableState mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(Boolean.valueOf((z18 || z19) ? false : true), composerImpl2);
                                                        Unit unit2 = Unit.INSTANCE;
                                                        boolean zChanged42 = composerImpl2.changed(mutableStateRememberUpdatedState2) | composerImpl2.changedInstance(legacyTextFieldState2) | composerImpl2.changedInstance(textInputService3) | composerImpl2.changedInstance(textFieldSelectionManager);
                                                        if (i33 > 32) {
                                                            textFieldSelectionManager2 = textFieldSelectionManager;
                                                            if ((i31 & 48) != 32) {
                                                                z20 = true;
                                                            }
                                                            z21 = zChanged42 | z20;
                                                            objRememberedValue6 = composerImpl2.rememberedValue();
                                                            if (z21) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            Long l = undoManager.lastSnapshot;
                                            if (jCurrentTimeMillis > (l != null ? l.longValue() : j) + 5000) {
                                            }
                                            objRememberedValue2 = composerImpl2.rememberedValue();
                                            Object obj132 = objRememberedValue2;
                                            if (objRememberedValue2 == composer$Companion$Empty$1) {
                                            }
                                            final CoroutineScope coroutineScope2 = (CoroutineScope) obj132;
                                            objRememberedValue3 = composerImpl2.rememberedValue();
                                            Object obj142 = objRememberedValue3;
                                            if (objRememberedValue3 == composer$Companion$Empty$1) {
                                            }
                                            final BringIntoViewRequester bringIntoViewRequester22 = (BringIntoViewRequester) obj142;
                                            objRememberedValue4 = composerImpl2.rememberedValue();
                                            Object obj152 = objRememberedValue4;
                                            if (objRememberedValue4 == composer$Companion$Empty$1) {
                                            }
                                            final TextFieldSelectionManager textFieldSelectionManager82 = (TextFieldSelectionManager) obj152;
                                            TransformedText transformedText42 = transformedText2;
                                            final OffsetMapping offsetMapping52 = transformedText42.offsetMapping;
                                            textFieldSelectionManager82.offsetMapping = offsetMapping52;
                                            VisualTransformation visualTransformation72 = visualTransformation5;
                                            textFieldSelectionManager82.visualTransformation = visualTransformation72;
                                            textFieldSelectionManager82.onValueChange = (Lambda) legacyTextFieldState4.onValueChange;
                                            textFieldSelectionManager82.state = legacyTextFieldState4;
                                            ((SnapshotMutableStateImpl) textFieldSelectionManager82.value$delegate).setValue(textFieldValue);
                                            textFieldSelectionManager82.clipboard = (Clipboard) composerImpl2.consume(CompositionLocalsKt.LocalClipboard);
                                            textFieldSelectionManager82.coroutineScope = coroutineScope2;
                                            textFieldSelectionManager82.textToolbar = (TextToolbar) composerImpl2.consume(CompositionLocalsKt.LocalTextToolbar);
                                            textFieldSelectionManager82.hapticFeedBack = (HapticFeedback) composerImpl2.consume(CompositionLocalsKt.LocalHapticFeedback);
                                            final FocusRequester focusRequester42 = focusRequester;
                                            textFieldSelectionManager82.focusRequester = focusRequester42;
                                            final boolean z352 = !z31;
                                            ((SnapshotMutableStateImpl) textFieldSelectionManager82.editable$delegate).setValue(Boolean.valueOf(z352));
                                            ((SnapshotMutableStateImpl) textFieldSelectionManager82.enabled$delegate).setValue(Boolean.valueOf(z10));
                                            ?? TextFieldMagnifier2 = Modifier.Companion;
                                            int i442 = i31 & 7168;
                                            int i452 = i31 & i28;
                                            final Density density32 = density;
                                            int i462 = i30;
                                            boolean zChangedInstance92 = composerImpl2.changedInstance(legacyTextFieldState4) | (i442 != 2048) | (i452 != 16384) | composerImpl2.changedInstance(textInputService2) | (i462 != 4);
                                            i33 = (i31 & 112) ^ 48;
                                            KeyboardActions keyboardActions42 = keyboardActions3;
                                            if (i33 <= 32) {
                                            }
                                        }
                                    }
                                    textStyle8 = textStyle7;
                                    if (z16) {
                                        EditingBuffer editingBuffer22 = editProcessor.mBuffer;
                                        editingBuffer22.compositionStart = -1;
                                        editingBuffer22.compositionEnd = -1;
                                        j = 0;
                                        textFieldValueM780copy3r_uNRQ$default = TextFieldValue.m780copy3r_uNRQ$default(textFieldValue, null, 0L, 3);
                                        textFieldValue2 = editProcessor.mBufferState;
                                        editProcessor.mBufferState = textFieldValueM780copy3r_uNRQ$default;
                                        if (textInputSession != null) {
                                            textInputSession.platformTextInputService.updateState(textFieldValue2, textFieldValueM780copy3r_uNRQ$default);
                                        }
                                        objRememberedValue = composerImpl2.rememberedValue();
                                        if (objRememberedValue != composer$Companion$Empty$1) {
                                        }
                                        undoManager = (UndoManager) obj3;
                                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                                        if (undoManager.forceNextSnapshot) {
                                        }
                                    }
                                }
                                z17 = false;
                                textRange = textFieldValue.composition;
                                if (textRange == null) {
                                }
                                textStyle8 = textStyle7;
                                if (z16) {
                                }
                            } else {
                                i32 = 1;
                            }
                            boolean z44 = z14;
                            textDelegate2 = new TextDelegate(annotatedString, textStyle7, Integer.MAX_VALUE, i32, z44, i43, density, resolver, emptyList, null);
                            z15 = z44;
                            if (legacyTextFieldState4.textDelegate != textDelegate2) {
                            }
                            legacyTextFieldState4.textDelegate = textDelegate2;
                            TextInputSession textInputSession2 = legacyTextFieldState4.inputSession;
                            EditProcessor editProcessor2 = legacyTextFieldState4.processor;
                            editProcessor2.getClass();
                            boolean zAreEqual22 = Intrinsics.areEqual(textFieldValue.composition, editProcessor2.mBuffer.m772getCompositionMzsxiRA$ui_text_release());
                            String str2 = editProcessor2.mBufferState.annotatedString.text;
                            AnnotatedString annotatedString42 = textFieldValue.annotatedString;
                            zAreEqual = Intrinsics.areEqual(str2, annotatedString42.text);
                            long j52 = textFieldValue.selection;
                            if (zAreEqual) {
                            }
                            z17 = false;
                            textRange = textFieldValue.composition;
                            if (textRange == null) {
                            }
                            textStyle8 = textStyle7;
                            if (z16) {
                            }
                        } else {
                            composerImpl3.skipToGroupEnd();
                            modifier2 = modifier;
                            imeOptions2 = imeOptions;
                            function32 = function3;
                            textFieldScrollerPosition2 = textFieldScrollerPosition;
                            composerImpl = composerImpl3;
                            textStyle3 = textStyle2;
                            visualTransformation3 = visualTransformation2;
                            brush2 = solidColor;
                            z7 = z5;
                            mutableInteractionSource3 = mutableInteractionSource2;
                            i26 = i2;
                            keyboardActions2 = keyboardActions;
                            z8 = z2;
                            z9 = z6;
                            function14 = function13;
                            i27 = i;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField.6
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj16, Object obj17) {
                                    ((Number) obj17).intValue();
                                    CoreTextFieldKt.CoreTextField(textFieldValue, function1, modifier2, textStyle3, visualTransformation3, function14, mutableInteractionSource3, brush2, z7, i27, i26, imeOptions2, keyboardActions2, z8, z9, function32, textFieldScrollerPosition2, (Composer) obj16, RecomposeScopeImplKt.updateChangedFlags(i3 | 1), RecomposeScopeImplKt.updateChangedFlags(i4), i5);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i19 |= 24576;
                    z6 = z3;
                    i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    if (i23 != 0) {
                    }
                    i25 = i5 & 65536;
                    if (i25 != 0) {
                    }
                    if (composerImpl3.shouldExecute(i35 & 1, ((i35 & 306783379) == 306783378 && (i24 & 599187) == 599186) ? false : z4)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                visualTransformation2 = visualTransformation;
                i9 = i5 & 32;
                if (i9 == 0) {
                }
                i10 = i5 & 64;
                int i372 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                if (i10 == 0) {
                }
                i11 = i5 & 128;
                if (i11 == 0) {
                }
                i12 = i5 & 256;
                if (i12 == 0) {
                }
                i13 = i5 & 512;
                if (i13 == 0) {
                }
                i15 = i5 & 1024;
                if (i15 == 0) {
                }
                if ((i4 & 48) != 0) {
                }
                int i382 = i16;
                i18 = i5 & 4096;
                if (i18 == 0) {
                }
                i21 = i5 & 8192;
                if (i21 == 0) {
                }
                i22 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if (i22 != 0) {
                }
                z6 = z3;
                i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                if (i23 != 0) {
                }
                i25 = i5 & 65536;
                if (i25 != 0) {
                }
                if (composerImpl3.shouldExecute(i35 & 1, ((i35 & 306783379) == 306783378 && (i24 & 599187) == 599186) ? false : z4)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            textStyle2 = textStyle;
            i8 = i5 & 16;
            if (i8 != 0) {
            }
            visualTransformation2 = visualTransformation;
            i9 = i5 & 32;
            if (i9 == 0) {
            }
            i10 = i5 & 64;
            int i3722 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            if (i10 == 0) {
            }
            i11 = i5 & 128;
            if (i11 == 0) {
            }
            i12 = i5 & 256;
            if (i12 == 0) {
            }
            i13 = i5 & 512;
            if (i13 == 0) {
            }
            i15 = i5 & 1024;
            if (i15 == 0) {
            }
            if ((i4 & 48) != 0) {
            }
            int i3822 = i16;
            i18 = i5 & 4096;
            if (i18 == 0) {
            }
            i21 = i5 & 8192;
            if (i21 == 0) {
            }
            i22 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if (i22 != 0) {
            }
            z6 = z3;
            i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            if (i23 != 0) {
            }
            i25 = i5 & 65536;
            if (i25 != 0) {
            }
            if (composerImpl3.shouldExecute(i35 & 1, ((i35 & 306783379) == 306783378 && (i24 & 599187) == 599186) ? false : z4)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        i7 = i5 & 8;
        if (i7 == 0) {
        }
        textStyle2 = textStyle;
        i8 = i5 & 16;
        if (i8 != 0) {
        }
        visualTransformation2 = visualTransformation;
        i9 = i5 & 32;
        if (i9 == 0) {
        }
        i10 = i5 & 64;
        int i37222 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i10 == 0) {
        }
        i11 = i5 & 128;
        if (i11 == 0) {
        }
        i12 = i5 & 256;
        if (i12 == 0) {
        }
        i13 = i5 & 512;
        if (i13 == 0) {
        }
        i15 = i5 & 1024;
        if (i15 == 0) {
        }
        if ((i4 & 48) != 0) {
        }
        int i38222 = i16;
        i18 = i5 & 4096;
        if (i18 == 0) {
        }
        i21 = i5 & 8192;
        if (i21 == 0) {
        }
        i22 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if (i22 != 0) {
        }
        z6 = z3;
        i23 = i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        if (i23 != 0) {
        }
        i25 = i5 & 65536;
        if (i25 != 0) {
        }
        if (composerImpl3.shouldExecute(i35 & 1, ((i35 & 306783379) == 306783378 && (i24 & 599187) == 599186) ? false : z4)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    public static final void CoreTextFieldRootBox(final Modifier modifier, final TextFieldSelectionManager textFieldSelectionManager, final Function2 function2, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-20551815);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(textFieldSelectionManager) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 147) != 146)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.CoreTextFieldRootBox (CoreTextField.kt:669)");
            }
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            ContextMenu_androidKt.ContextMenuArea(textFieldSelectionManager, function2, composerImpl, (i2 >> 3) & 126);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextFieldRootBox.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CoreTextFieldKt.CoreTextFieldRootBox(modifier, textFieldSelectionManager, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TextFieldCursorHandle(final TextFieldSelectionManager textFieldSelectionManager, Composer composer, final int i) throws Throwable {
        int i2;
        TextDelegate textDelegate;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1436003720);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(textFieldSelectionManager) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 3) != 2)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextFieldCursorHandle (CoreTextField.kt:1066)");
            }
            LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
            if (legacyTextFieldState == null || !((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState.showCursorHandle$delegate).getValue()).booleanValue()) {
                composerImpl.startReplaceGroup(-288579234);
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                AnnotatedString annotatedString = (legacyTextFieldState2 == null || (textDelegate = legacyTextFieldState2.textDelegate) == null) ? null : textDelegate.text;
                if (annotatedString != null && annotatedString.text.length() > 0) {
                    composerImpl.startReplaceGroup(-289887155);
                    boolean zChanged = composerImpl.changed(textFieldSelectionManager);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    Composer.Companion companion = Composer.Companion;
                    if (!zChanged) {
                        companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new TextDragObserver() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$cursorDragObserver$1
                                /* JADX WARN: Type inference failed for: r0v11, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
                                @Override // androidx.compose.foundation.text.TextDragObserver
                                /* renamed from: onDrag-k-4lQ0M */
                                public final void mo203onDragk4lQ0M(long j) {
                                    TextLayoutResultProxy layoutResult;
                                    HapticFeedback hapticFeedback;
                                    TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                                    textFieldSelectionManager2.dragTotalDistance = Offset.m403plusMKHz9U(textFieldSelectionManager2.dragTotalDistance, j);
                                    LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager2.state;
                                    if (legacyTextFieldState3 == null || (layoutResult = legacyTextFieldState3.getLayoutResult()) == null) {
                                        return;
                                    }
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(Offset.m395boximpl(Offset.m403plusMKHz9U(textFieldSelectionManager2.dragBeginPosition, textFieldSelectionManager2.dragTotalDistance)));
                                    OffsetMapping offsetMapping = textFieldSelectionManager2.offsetMapping;
                                    Offset offsetM240getCurrentDragPosition_m7T9E = textFieldSelectionManager2.m240getCurrentDragPosition_m7T9E();
                                    offsetM240getCurrentDragPosition_m7T9E.getClass();
                                    int iTransformedToOriginal = offsetMapping.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(offsetM240getCurrentDragPosition_m7T9E.packedValue, true));
                                    long jTextRange = TextRangeKt.TextRange(iTransformedToOriginal, iTransformedToOriginal);
                                    if (TextRange.m748equalsimpl0(jTextRange, textFieldSelectionManager2.getValue$foundation_release().selection)) {
                                        return;
                                    }
                                    LegacyTextFieldState legacyTextFieldState4 = textFieldSelectionManager2.state;
                                    if ((legacyTextFieldState4 == null || ((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState4.isInTouchMode$delegate).getValue()).booleanValue()) && (hapticFeedback = textFieldSelectionManager2.hapticFeedBack) != null) {
                                        HapticFeedbackType.Companion.getClass();
                                        hapticFeedback.mo572performHapticFeedbackCdsT49E(HapticFeedbackType.Companion.m573getTextHandleMove5zf0vsI());
                                    }
                                    textFieldSelectionManager2.onValueChange.mo781invoke(TextFieldSelectionManager.m238createTextFieldValueFDrldGo(textFieldSelectionManager2.getValue$foundation_release().annotatedString, jTextRange));
                                }

                                @Override // androidx.compose.foundation.text.TextDragObserver
                                /* renamed from: onStart-k-4lQ0M */
                                public final void mo204onStartk4lQ0M(long j) {
                                    TextLayoutResultProxy layoutResult;
                                    TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                                    long jM235getAdjustedCoordinatesk4lQ0M = SelectionHandlesKt.m235getAdjustedCoordinatesk4lQ0M(textFieldSelectionManager2.m241getHandlePositiontuRUvjQ$foundation_release(true));
                                    LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager2.state;
                                    if (legacyTextFieldState3 == null || (layoutResult = legacyTextFieldState3.getLayoutResult()) == null) {
                                        return;
                                    }
                                    long jM212translateInnerToDecorationCoordinatesMKHz9U$foundation_release = layoutResult.m212translateInnerToDecorationCoordinatesMKHz9U$foundation_release(jM235getAdjustedCoordinatesk4lQ0M);
                                    textFieldSelectionManager2.dragBeginPosition = jM212translateInnerToDecorationCoordinatesMKHz9U$foundation_release;
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(Offset.m395boximpl(jM212translateInnerToDecorationCoordinatesMKHz9U$foundation_release));
                                    Offset.Companion.getClass();
                                    textFieldSelectionManager2.dragTotalDistance = 0L;
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.draggingHandle$delegate).setValue(Handle.Cursor);
                                    textFieldSelectionManager2.updateFloatingToolbar(false);
                                }

                                @Override // androidx.compose.foundation.text.TextDragObserver
                                public final void onStop() {
                                    TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.draggingHandle$delegate).setValue(null);
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(null);
                                }

                                @Override // androidx.compose.foundation.text.TextDragObserver
                                public final void onUp() {
                                    TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.draggingHandle$delegate).setValue(null);
                                    ((SnapshotMutableStateImpl) textFieldSelectionManager2.currentDragPosition$delegate).setValue(null);
                                }

                                @Override // androidx.compose.foundation.text.TextDragObserver
                                public final void onCancel() {
                                }

                                @Override // androidx.compose.foundation.text.TextDragObserver
                                /* renamed from: onDown-k-4lQ0M */
                                public final void mo202onDownk4lQ0M() {
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        final TextDragObserver textDragObserver = (TextDragObserver) objRememberedValue;
                        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                        OffsetMapping offsetMapping = textFieldSelectionManager.offsetMapping;
                        long j = textFieldSelectionManager.getValue$foundation_release().selection;
                        TextRange.Companion companion2 = TextRange.Companion;
                        int iOriginalToTransformed = offsetMapping.originalToTransformed((int) (j >> 32));
                        LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager.state;
                        TextLayoutResultProxy layoutResult = legacyTextFieldState3 != null ? legacyTextFieldState3.getLayoutResult() : null;
                        layoutResult.getClass();
                        TextLayoutResult textLayoutResult = layoutResult.value;
                        Rect cursorRect = textLayoutResult.getCursorRect(RangesKt___RangesKt.coerceIn(iOriginalToTransformed, 0, textLayoutResult.layoutInput.text.text.length()));
                        final long jFloatToRawIntBits = (Float.floatToRawIntBits(cursorRect.bottom) & 4294967295L) | (Float.floatToRawIntBits((density.mo58toPx0680j_4(TextFieldCursor_androidKt.DefaultCursorThickness) / 2) + cursorRect.left) << 32);
                        Offset.Companion companion3 = Offset.Companion;
                        boolean zChanged2 = composerImpl.changed(jFloatToRawIntBits);
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (!zChanged2) {
                            companion.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new OffsetProvider() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$TextFieldCursorHandle$1$1
                                    @Override // androidx.compose.foundation.text.selection.OffsetProvider
                                    /* renamed from: provide-F1C5BW0, reason: not valid java name */
                                    public final long mo198provideF1C5BW0() {
                                        return jFloatToRawIntBits;
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            OffsetProvider offsetProvider = (OffsetProvider) objRememberedValue2;
                            Modifier.Companion companion4 = Modifier.Companion;
                            boolean zChangedInstance = composerImpl.changedInstance(textDragObserver) | composerImpl.changedInstance(textFieldSelectionManager);
                            Object objRememberedValue3 = composerImpl.rememberedValue();
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                    objRememberedValue3 = new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$TextFieldCursorHandle$2$1

                                        /* renamed from: androidx.compose.foundation.text.CoreTextFieldKt$TextFieldCursorHandle$2$1$1, reason: invalid class name */
                                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                            final /* synthetic */ TextFieldSelectionManager $manager;
                                            final /* synthetic */ TextDragObserver $observer;
                                            final /* synthetic */ PointerInputScope $this_pointerInput;
                                            private /* synthetic */ Object L$0;
                                            int label;

                                            /* renamed from: androidx.compose.foundation.text.CoreTextFieldKt$TextFieldCursorHandle$2$1$1$1, reason: invalid class name and collision with other inner class name */
                                            final class C00171 extends SuspendLambda implements Function2 {
                                                final /* synthetic */ TextDragObserver $observer;
                                                final /* synthetic */ PointerInputScope $this_pointerInput;
                                                int label;

                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                public C00171(PointerInputScope pointerInputScope, TextDragObserver textDragObserver, Continuation continuation) {
                                                    super(2, continuation);
                                                    this.$this_pointerInput = pointerInputScope;
                                                    this.$observer = textDragObserver;
                                                }

                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                public final Continuation create(Object obj, Continuation continuation) {
                                                    return new C00171(this.$this_pointerInput, this.$observer, continuation);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(Object obj, Object obj2) {
                                                    return ((C00171) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                }

                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                public final Object invokeSuspend(Object obj) {
                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                    int i = this.label;
                                                    if (i == 0) {
                                                        ResultKt.throwOnFailure(obj);
                                                        PointerInputScope pointerInputScope = this.$this_pointerInput;
                                                        TextDragObserver textDragObserver = this.$observer;
                                                        this.label = 1;
                                                        if (LongPressTextDragObserverKt.detectDownAndDragGesturesWithObserver(pointerInputScope, textDragObserver, this) == coroutineSingletons) {
                                                            return coroutineSingletons;
                                                        }
                                                    } else {
                                                        if (i != 1) {
                                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                        }
                                                        ResultKt.throwOnFailure(obj);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }

                                            /* renamed from: androidx.compose.foundation.text.CoreTextFieldKt$TextFieldCursorHandle$2$1$1$2, reason: invalid class name */
                                            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                                                final /* synthetic */ TextFieldSelectionManager $manager;
                                                final /* synthetic */ PointerInputScope $this_pointerInput;
                                                int label;

                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                public AnonymousClass2(PointerInputScope pointerInputScope, TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
                                                    super(2, continuation);
                                                    this.$this_pointerInput = pointerInputScope;
                                                    this.$manager = textFieldSelectionManager;
                                                }

                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                public final Continuation create(Object obj, Continuation continuation) {
                                                    return new AnonymousClass2(this.$this_pointerInput, this.$manager, continuation);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(Object obj, Object obj2) {
                                                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                }

                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                public final Object invokeSuspend(Object obj) {
                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                    int i = this.label;
                                                    if (i == 0) {
                                                        ResultKt.throwOnFailure(obj);
                                                        PointerInputScope pointerInputScope = this.$this_pointerInput;
                                                        final TextFieldSelectionManager textFieldSelectionManager = this.$manager;
                                                        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.TextFieldCursorHandle.2.1.1.2.1
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj2) {
                                                                long j = ((Offset) obj2).packedValue;
                                                                textFieldSelectionManager.showSelectionToolbar$foundation_release();
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        this.label = 1;
                                                        if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, function1, this, 7) == coroutineSingletons) {
                                                            return coroutineSingletons;
                                                        }
                                                    } else {
                                                        if (i != 1) {
                                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                        }
                                                        ResultKt.throwOnFailure(obj);
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }

                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            public AnonymousClass1(PointerInputScope pointerInputScope, TextDragObserver textDragObserver, TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
                                                super(2, continuation);
                                                this.$this_pointerInput = pointerInputScope;
                                                this.$observer = textDragObserver;
                                                this.$manager = textFieldSelectionManager;
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Continuation create(Object obj, Continuation continuation) {
                                                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_pointerInput, this.$observer, this.$manager, continuation);
                                                anonymousClass1.L$0 = obj;
                                                return anonymousClass1;
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj, Object obj2) {
                                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Object invokeSuspend(Object obj) {
                                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                if (this.label != 0) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                                CoroutineStart coroutineStart = CoroutineStart.UNDISPATCHED;
                                                BuildersKt.launch$default(coroutineScope, null, coroutineStart, new C00171(this.$this_pointerInput, this.$observer, null), 1);
                                                BuildersKt.launch$default(coroutineScope, null, coroutineStart, new AnonymousClass2(this.$this_pointerInput, this.$manager, null), 1);
                                                return Unit.INSTANCE;
                                            }
                                        }

                                        @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                        public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                            Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(pointerInputScope, textDragObserver, textFieldSelectionManager, null), continuation);
                                            return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                }
                                Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(companion4, textDragObserver, (PointerInputEventHandler) objRememberedValue3);
                                boolean zChanged3 = composerImpl.changed(jFloatToRawIntBits);
                                Object objRememberedValue4 = composerImpl.rememberedValue();
                                if (!zChanged3) {
                                    companion.getClass();
                                    if (objRememberedValue4 == Composer.Companion.Empty) {
                                        objRememberedValue4 = new Function1() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$TextFieldCursorHandle$3$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj) {
                                                ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj)).set(SelectionHandlesKt.SelectionHandleInfoKey, new SelectionHandleInfo(Handle.Cursor, jFloatToRawIntBits, SelectionHandleAnchor.Middle, true, null));
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue4);
                                    }
                                    AndroidCursorHandle_androidKt.m190CursorHandleUSBMPiE(offsetProvider, SemanticsModifierKt.semantics(modifierPointerInput, false, (Function1) objRememberedValue4), 0L, composerImpl, 0, 4);
                                    composerImpl.end(false);
                                }
                            }
                        }
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt.TextFieldCursorHandle.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Number) obj2).intValue();
                    CoreTextFieldKt.TextFieldCursorHandle(textFieldSelectionManager, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$SelectionToolbarAndHandles(final TextFieldSelectionManager textFieldSelectionManager, final boolean z, Composer composer, final int i) {
        int i2;
        TextLayoutResultProxy layoutResult;
        TextLayoutResult textLayoutResult;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(626339208);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(textFieldSelectionManager) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(z) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 19) != 18)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.SelectionToolbarAndHandles (CoreTextField.kt:1019)");
            }
            if (z) {
                composerImpl.startReplaceGroup(-1290871266);
                LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                TextLayoutResult textLayoutResult2 = null;
                if (legacyTextFieldState != null && (layoutResult = legacyTextFieldState.getLayoutResult()) != null && (textLayoutResult = layoutResult.value) != null) {
                    LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                    if (!(legacyTextFieldState2 != null ? legacyTextFieldState2.isLayoutResultStale : true)) {
                        textLayoutResult2 = textLayoutResult;
                    }
                }
                if (textLayoutResult2 == null) {
                    composerImpl.startReplaceGroup(-1290547720);
                } else {
                    composerImpl.startReplaceGroup(-1290547719);
                    if (TextRange.m749getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection)) {
                        composerImpl.startReplaceGroup(-1684125606);
                        composerImpl.end(false);
                    } else {
                        composerImpl.startReplaceGroup(-1685176940);
                        int iOriginalToTransformed = textFieldSelectionManager.offsetMapping.originalToTransformed((int) (textFieldSelectionManager.getValue$foundation_release().selection >> 32));
                        int iOriginalToTransformed2 = textFieldSelectionManager.offsetMapping.originalToTransformed((int) (textFieldSelectionManager.getValue$foundation_release().selection & 4294967295L));
                        ResolvedTextDirection bidiRunDirection = textLayoutResult2.getBidiRunDirection(iOriginalToTransformed);
                        ResolvedTextDirection bidiRunDirection2 = textLayoutResult2.getBidiRunDirection(Math.max(iOriginalToTransformed2 - 1, 0));
                        LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager.state;
                        if (legacyTextFieldState3 == null || !((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState3.showSelectionHandleStart$delegate).getValue()).booleanValue()) {
                            composerImpl.startReplaceGroup(-1684494630);
                            composerImpl.end(false);
                        } else {
                            composerImpl.startReplaceGroup(-1684758905);
                            TextFieldSelectionManagerKt.TextFieldSelectionHandle(true, bidiRunDirection, textFieldSelectionManager, composerImpl, ((i2 << 6) & 896) | 6);
                            composerImpl.end(false);
                        }
                        LegacyTextFieldState legacyTextFieldState4 = textFieldSelectionManager.state;
                        if (legacyTextFieldState4 == null || !((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState4.showSelectionHandleEnd$delegate).getValue()).booleanValue()) {
                            composerImpl.startReplaceGroup(-1684147430);
                            composerImpl.end(false);
                        } else {
                            composerImpl.startReplaceGroup(-1684410744);
                            TextFieldSelectionManagerKt.TextFieldSelectionHandle(false, bidiRunDirection2, textFieldSelectionManager, composerImpl, ((i2 << 6) & 896) | 6);
                            composerImpl.end(false);
                        }
                        composerImpl.end(false);
                    }
                    LegacyTextFieldState legacyTextFieldState5 = textFieldSelectionManager.state;
                    if (legacyTextFieldState5 != null) {
                        boolean zAreEqual = Intrinsics.areEqual(textFieldSelectionManager.oldValue.annotatedString.text, textFieldSelectionManager.getValue$foundation_release().annotatedString.text);
                        MutableState mutableState = legacyTextFieldState5.showFloatingToolbar$delegate;
                        if (!zAreEqual) {
                            ((SnapshotMutableStateImpl) mutableState).setValue(Boolean.FALSE);
                        }
                        if (legacyTextFieldState5.getHasFocus()) {
                            if (((Boolean) ((SnapshotMutableStateImpl) mutableState).getValue()).booleanValue()) {
                                textFieldSelectionManager.showSelectionToolbar$foundation_release();
                            } else {
                                textFieldSelectionManager.hideSelectionToolbar$foundation_release();
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                }
                composerImpl.end(false);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(651162175);
                composerImpl.end(false);
                textFieldSelectionManager.hideSelectionToolbar$foundation_release();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$SelectionToolbarAndHandles$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CoreTextFieldKt.access$SelectionToolbarAndHandles(textFieldSelectionManager, z, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$endInputSession(LegacyTextFieldState legacyTextFieldState) {
        TextInputSession textInputSession = legacyTextFieldState.inputSession;
        if (textInputSession != null) {
            TextFieldDelegate.Companion companion = TextFieldDelegate.Companion;
            Function1 function1 = legacyTextFieldState.onValueChange;
            companion.getClass();
            ((LegacyTextFieldState$onValueChange$1) function1).mo781invoke(TextFieldValue.m780copy3r_uNRQ$default(legacyTextFieldState.processor.mBufferState, null, 0L, 3));
            TextInputService textInputService = textInputSession.textInputService;
            if (textInputService._currentInputSession.compareAndSet(textInputSession, null)) {
                textInputService.platformTextInputService.stopInput();
            }
        }
        legacyTextFieldState.inputSession = null;
    }

    public static final void notifyFocusedRect(LegacyTextFieldState legacyTextFieldState, TextFieldValue textFieldValue, OffsetMapping offsetMapping) {
        Snapshot.Companion.getClass();
        Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
        Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
        Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
        try {
            TextLayoutResultProxy layoutResult = legacyTextFieldState.getLayoutResult();
            if (layoutResult == null) {
                return;
            }
            TextInputSession textInputSession = legacyTextFieldState.inputSession;
            if (textInputSession == null) {
                return;
            }
            LayoutCoordinates layoutCoordinates = legacyTextFieldState.getLayoutCoordinates();
            if (layoutCoordinates == null) {
                return;
            }
            TextFieldDelegate.Companion companion = TextFieldDelegate.Companion;
            TextDelegate textDelegate = legacyTextFieldState.textDelegate;
            TextLayoutResult textLayoutResult = layoutResult.value;
            boolean hasFocus = legacyTextFieldState.getHasFocus();
            companion.getClass();
            TextFieldDelegate.Companion.notifyFocusedRect$foundation_release(textFieldValue, textDelegate, textLayoutResult, layoutCoordinates, textInputSession, hasFocus, offsetMapping);
            Unit unit = Unit.INSTANCE;
        } finally {
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
        }
    }
}
