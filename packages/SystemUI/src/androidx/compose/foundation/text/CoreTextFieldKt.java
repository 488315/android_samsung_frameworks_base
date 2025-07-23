package androidx.compose.foundation.text;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.text.TextFieldDelegate;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputService;
import androidx.compose.ui.text.input.TextInputSession;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CoreTextFieldKt {
    /* JADX WARN: Code restructure failed: missing block: B:176:0x06d8, code lost:
    
        if (r7 > ((r3 != null ? r3.longValue() : r4) + 5000)) goto L370;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x062a  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0656  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0678 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0696  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x06c8  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x06e9  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x06fa  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x070a  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x077e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x078d  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x07a3  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x07b4  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x07eb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0845 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0877  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0892 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x08c4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:226:0x08de  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x08e7  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x08fb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0926  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0950  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x095f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x097d  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0995  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x09a4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0a03 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0a5b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0a76  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0a7e  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0a92 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0aa8  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0ade  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0b07 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0b34 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0b90  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0bb1  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0bfb  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0c3f  */
    /* JADX WARN: Removed duplicated region for block: B:311:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0aaa  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0a78  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0997  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x097f  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0952  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0934  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x08e9  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x08e0  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0889  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x07ce  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x07a5  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x078f  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0780  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x065f  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0635  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x0c1d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:494:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0267  */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CoreTextField(final androidx.compose.ui.text.input.TextFieldValue r65, final kotlin.jvm.functions.Function1 r66, androidx.compose.ui.Modifier r67, androidx.compose.ui.text.TextStyle r68, androidx.compose.ui.text.input.VisualTransformation r69, kotlin.jvm.functions.Function1 r70, androidx.compose.foundation.interaction.MutableInteractionSource r71, androidx.compose.ui.graphics.Brush r72, boolean r73, int r74, int r75, androidx.compose.ui.text.input.ImeOptions r76, androidx.compose.foundation.text.KeyboardActions r77, boolean r78, boolean r79, kotlin.jvm.functions.Function3 r80, androidx.compose.foundation.text.TextFieldScrollerPosition r81, androidx.compose.runtime.Composer r82, final int r83, final int r84, final int r85) {
        /*
            Method dump skipped, instructions count: 3158
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.CoreTextFieldKt.CoreTextField(androidx.compose.ui.text.input.TextFieldValue, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.input.VisualTransformation, kotlin.jvm.functions.Function1, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.ui.graphics.Brush, boolean, int, int, androidx.compose.ui.text.input.ImeOptions, androidx.compose.foundation.text.KeyboardActions, boolean, boolean, kotlin.jvm.functions.Function3, androidx.compose.foundation.text.TextFieldScrollerPosition, androidx.compose.runtime.Composer, int, int, int):void");
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
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            ContextMenu_androidKt.ContextMenuArea(textFieldSelectionManager, function2, composerImpl, (i2 >> 3) & 126);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextFieldRootBox$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CoreTextFieldKt.CoreTextFieldRootBox(Modifier.this, textFieldSelectionManager, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0077, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ed, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x010f, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x012e, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L54;
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TextFieldCursorHandle(final androidx.compose.foundation.text.selection.TextFieldSelectionManager r10, androidx.compose.runtime.Composer r11, final int r12) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.CoreTextFieldKt.TextFieldCursorHandle(androidx.compose.foundation.text.selection.TextFieldSelectionManager, androidx.compose.runtime.Composer, int):void");
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
                    if (TextRange.m747getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection)) {
                        composerImpl.startReplaceGroup(-1684125606);
                        composerImpl.end(false);
                    } else {
                        composerImpl.startReplaceGroup(-1685176940);
                        int originalToTransformed = textFieldSelectionManager.offsetMapping.originalToTransformed((int) (textFieldSelectionManager.getValue$foundation_release().selection >> 32));
                        int originalToTransformed2 = textFieldSelectionManager.offsetMapping.originalToTransformed((int) (textFieldSelectionManager.getValue$foundation_release().selection & 4294967295L));
                        ResolvedTextDirection bidiRunDirection = textLayoutResult2.getBidiRunDirection(originalToTransformed);
                        ResolvedTextDirection bidiRunDirection2 = textLayoutResult2.getBidiRunDirection(Math.max(originalToTransformed2 - 1, 0));
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
                        boolean areEqual = Intrinsics.areEqual(textFieldSelectionManager.oldValue.annotatedString.text, textFieldSelectionManager.getValue$foundation_release().annotatedString.text);
                        MutableState mutableState = legacyTextFieldState5.showFloatingToolbar$delegate;
                        if (!areEqual) {
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$SelectionToolbarAndHandles$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CoreTextFieldKt.access$SelectionToolbarAndHandles(TextFieldSelectionManager.this, z, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
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
            ((LegacyTextFieldState$onValueChange$1) function1).mo779invoke(TextFieldValue.m778copy3r_uNRQ$default(legacyTextFieldState.processor.mBufferState, null, 0L, 3));
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
        Snapshot makeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
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
            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, makeCurrentNonObservable, readObserver);
        }
    }
}
