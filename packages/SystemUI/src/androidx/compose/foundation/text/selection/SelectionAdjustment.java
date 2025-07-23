package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.StringHelpersKt;
import androidx.compose.foundation.text.StringHelpers_androidKt;
import androidx.compose.foundation.text.selection.Selection;
import androidx.compose.foundation.text.selection.SelectionAdjustment;
import androidx.compose.ui.text.TextRangeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SelectionAdjustment {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final SelectionAdjustment$Companion$$ExternalSyntheticLambda0 CharacterWithWordAccelerate;
        public static final SelectionAdjustment$Companion$$ExternalSyntheticLambda0 None;
        public static final SelectionAdjustment$Companion$$ExternalSyntheticLambda0 Paragraph;
        public static final SelectionAdjustment$Companion$$ExternalSyntheticLambda0 Word;

        /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r0v2, types: [androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r0v4, types: [androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0] */
        static {
            final int i = 0;
            None = new SelectionAdjustment() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.foundation.text.selection.SelectionAdjustment
                public final Selection adjust(SelectionLayout selectionLayout) {
                    Selection.AnchorInfo access$updateSelectionBoundary;
                    Selection.AnchorInfo anchorInfo;
                    boolean z;
                    Selection copy$default;
                    switch (i) {
                        case 0:
                            SelectionAdjustment.Companion companion = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo = singleSelectionLayout.info;
                            return new Selection(selectableInfo.anchorForOffset(selectableInfo.rawStartHandleOffset), selectableInfo.anchorForOffset(selectableInfo.rawEndHandleOffset), singleSelectionLayout.getCrossStatus() == CrossStatus.CROSSED);
                        case 1:
                            SelectionAdjustment.Companion companion2 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Word$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i2) {
                                    return selectableInfo2.textLayoutResult.m744getWordBoundaryjx7JFs(i2);
                                }
                            });
                        case 2:
                            SelectionAdjustment.Companion companion3 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Paragraph$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i2) {
                                    String str = selectableInfo2.textLayoutResult.layoutInput.text.text;
                                    return TextRangeKt.TextRange(StringHelpersKt.findParagraphStart(i2, str), StringHelpersKt.findParagraphEnd(i2, str));
                                }
                            });
                        default:
                            SelectionAdjustment.Companion companion4 = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
                            Selection selection = singleSelectionLayout2.previousSelection;
                            if (selection == null) {
                                return SelectionAdjustment.Companion.Word.adjust(selectionLayout);
                            }
                            SelectableInfo selectableInfo2 = singleSelectionLayout2.info;
                            boolean z2 = singleSelectionLayout2.isStartHandle;
                            Selection.AnchorInfo anchorInfo2 = selection.end;
                            Selection.AnchorInfo anchorInfo3 = selection.start;
                            if (z2) {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo3);
                                anchorInfo = anchorInfo2;
                                anchorInfo2 = anchorInfo3;
                                anchorInfo3 = access$updateSelectionBoundary;
                            } else {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo2);
                                anchorInfo = access$updateSelectionBoundary;
                            }
                            if (Intrinsics.areEqual(access$updateSelectionBoundary, anchorInfo2)) {
                                return selection;
                            }
                            Selection selection2 = new Selection(anchorInfo3, anchorInfo, singleSelectionLayout2.getCrossStatus() == CrossStatus.CROSSED || (singleSelectionLayout2.getCrossStatus() == CrossStatus.COLLAPSED && anchorInfo3.offset > anchorInfo.offset));
                            Selection.AnchorInfo anchorInfo4 = selection2.end;
                            Selection.AnchorInfo anchorInfo5 = selection2.start;
                            if (anchorInfo5.selectableId == anchorInfo4.selectableId) {
                                if (anchorInfo5.offset == anchorInfo4.offset) {
                                    z = true;
                                }
                                z = false;
                            } else {
                                boolean z3 = selection2.handlesCrossed;
                                if ((z3 ? anchorInfo5 : anchorInfo4).offset == 0) {
                                    if (((SingleSelectionLayout) selectionLayout).info.textLayoutResult.layoutInput.text.text.length() == (z3 ? anchorInfo4 : anchorInfo5).offset) {
                                        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                                        ref$BooleanRef.element = true;
                                        new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionLayoutKt$isCollapsed$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj) {
                                                if (((SelectableInfo) obj).textLayoutResult.layoutInput.text.text.length() > 0) {
                                                    Ref$BooleanRef.this.element = false;
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        z = ref$BooleanRef.element;
                                    }
                                }
                                z = false;
                            }
                            if (!z) {
                                return selection2;
                            }
                            SingleSelectionLayout singleSelectionLayout3 = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo3 = singleSelectionLayout3.info;
                            String str = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            Selection selection3 = singleSelectionLayout3.previousSelection;
                            if (selection3 == null) {
                                return selection2;
                            }
                            if (str.length() == 0) {
                                return selection2;
                            }
                            String str2 = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            int length = str2.length();
                            boolean z4 = singleSelectionLayout3.isStartHandle;
                            int i2 = selectableInfo3.rawStartHandleOffset;
                            if (i2 == 0) {
                                int findFollowingBreak = StringHelpers_androidKt.findFollowingBreak(0, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findFollowingBreak), null, true, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findFollowingBreak), false, 1);
                            } else if (i2 == length) {
                                int findPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak(length, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak), null, false, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak), true, 1);
                            } else {
                                boolean z5 = selection3.handlesCrossed;
                                int findPrecedingBreak2 = z4 ^ z5 ? StringHelpers_androidKt.findPrecedingBreak(i2, str2) : StringHelpers_androidKt.findFollowingBreak(i2, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak2), null, z5, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak2), z5, 1);
                            }
                            return copy$default;
                    }
                }
            };
            final int i2 = 1;
            Word = new SelectionAdjustment() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.foundation.text.selection.SelectionAdjustment
                public final Selection adjust(SelectionLayout selectionLayout) {
                    Selection.AnchorInfo access$updateSelectionBoundary;
                    Selection.AnchorInfo anchorInfo;
                    boolean z;
                    Selection copy$default;
                    switch (i2) {
                        case 0:
                            SelectionAdjustment.Companion companion = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo = singleSelectionLayout.info;
                            return new Selection(selectableInfo.anchorForOffset(selectableInfo.rawStartHandleOffset), selectableInfo.anchorForOffset(selectableInfo.rawEndHandleOffset), singleSelectionLayout.getCrossStatus() == CrossStatus.CROSSED);
                        case 1:
                            SelectionAdjustment.Companion companion2 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Word$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i22) {
                                    return selectableInfo2.textLayoutResult.m744getWordBoundaryjx7JFs(i22);
                                }
                            });
                        case 2:
                            SelectionAdjustment.Companion companion3 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Paragraph$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i22) {
                                    String str = selectableInfo2.textLayoutResult.layoutInput.text.text;
                                    return TextRangeKt.TextRange(StringHelpersKt.findParagraphStart(i22, str), StringHelpersKt.findParagraphEnd(i22, str));
                                }
                            });
                        default:
                            SelectionAdjustment.Companion companion4 = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
                            Selection selection = singleSelectionLayout2.previousSelection;
                            if (selection == null) {
                                return SelectionAdjustment.Companion.Word.adjust(selectionLayout);
                            }
                            SelectableInfo selectableInfo2 = singleSelectionLayout2.info;
                            boolean z2 = singleSelectionLayout2.isStartHandle;
                            Selection.AnchorInfo anchorInfo2 = selection.end;
                            Selection.AnchorInfo anchorInfo3 = selection.start;
                            if (z2) {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo3);
                                anchorInfo = anchorInfo2;
                                anchorInfo2 = anchorInfo3;
                                anchorInfo3 = access$updateSelectionBoundary;
                            } else {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo2);
                                anchorInfo = access$updateSelectionBoundary;
                            }
                            if (Intrinsics.areEqual(access$updateSelectionBoundary, anchorInfo2)) {
                                return selection;
                            }
                            Selection selection2 = new Selection(anchorInfo3, anchorInfo, singleSelectionLayout2.getCrossStatus() == CrossStatus.CROSSED || (singleSelectionLayout2.getCrossStatus() == CrossStatus.COLLAPSED && anchorInfo3.offset > anchorInfo.offset));
                            Selection.AnchorInfo anchorInfo4 = selection2.end;
                            Selection.AnchorInfo anchorInfo5 = selection2.start;
                            if (anchorInfo5.selectableId == anchorInfo4.selectableId) {
                                if (anchorInfo5.offset == anchorInfo4.offset) {
                                    z = true;
                                }
                                z = false;
                            } else {
                                boolean z3 = selection2.handlesCrossed;
                                if ((z3 ? anchorInfo5 : anchorInfo4).offset == 0) {
                                    if (((SingleSelectionLayout) selectionLayout).info.textLayoutResult.layoutInput.text.text.length() == (z3 ? anchorInfo4 : anchorInfo5).offset) {
                                        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                                        ref$BooleanRef.element = true;
                                        new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionLayoutKt$isCollapsed$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj) {
                                                if (((SelectableInfo) obj).textLayoutResult.layoutInput.text.text.length() > 0) {
                                                    Ref$BooleanRef.this.element = false;
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        z = ref$BooleanRef.element;
                                    }
                                }
                                z = false;
                            }
                            if (!z) {
                                return selection2;
                            }
                            SingleSelectionLayout singleSelectionLayout3 = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo3 = singleSelectionLayout3.info;
                            String str = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            Selection selection3 = singleSelectionLayout3.previousSelection;
                            if (selection3 == null) {
                                return selection2;
                            }
                            if (str.length() == 0) {
                                return selection2;
                            }
                            String str2 = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            int length = str2.length();
                            boolean z4 = singleSelectionLayout3.isStartHandle;
                            int i22 = selectableInfo3.rawStartHandleOffset;
                            if (i22 == 0) {
                                int findFollowingBreak = StringHelpers_androidKt.findFollowingBreak(0, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findFollowingBreak), null, true, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findFollowingBreak), false, 1);
                            } else if (i22 == length) {
                                int findPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak(length, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak), null, false, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak), true, 1);
                            } else {
                                boolean z5 = selection3.handlesCrossed;
                                int findPrecedingBreak2 = z4 ^ z5 ? StringHelpers_androidKt.findPrecedingBreak(i22, str2) : StringHelpers_androidKt.findFollowingBreak(i22, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak2), null, z5, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak2), z5, 1);
                            }
                            return copy$default;
                    }
                }
            };
            final int i3 = 2;
            Paragraph = new SelectionAdjustment() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.foundation.text.selection.SelectionAdjustment
                public final Selection adjust(SelectionLayout selectionLayout) {
                    Selection.AnchorInfo access$updateSelectionBoundary;
                    Selection.AnchorInfo anchorInfo;
                    boolean z;
                    Selection copy$default;
                    switch (i3) {
                        case 0:
                            SelectionAdjustment.Companion companion = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo = singleSelectionLayout.info;
                            return new Selection(selectableInfo.anchorForOffset(selectableInfo.rawStartHandleOffset), selectableInfo.anchorForOffset(selectableInfo.rawEndHandleOffset), singleSelectionLayout.getCrossStatus() == CrossStatus.CROSSED);
                        case 1:
                            SelectionAdjustment.Companion companion2 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Word$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i22) {
                                    return selectableInfo2.textLayoutResult.m744getWordBoundaryjx7JFs(i22);
                                }
                            });
                        case 2:
                            SelectionAdjustment.Companion companion3 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Paragraph$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i22) {
                                    String str = selectableInfo2.textLayoutResult.layoutInput.text.text;
                                    return TextRangeKt.TextRange(StringHelpersKt.findParagraphStart(i22, str), StringHelpersKt.findParagraphEnd(i22, str));
                                }
                            });
                        default:
                            SelectionAdjustment.Companion companion4 = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
                            Selection selection = singleSelectionLayout2.previousSelection;
                            if (selection == null) {
                                return SelectionAdjustment.Companion.Word.adjust(selectionLayout);
                            }
                            SelectableInfo selectableInfo2 = singleSelectionLayout2.info;
                            boolean z2 = singleSelectionLayout2.isStartHandle;
                            Selection.AnchorInfo anchorInfo2 = selection.end;
                            Selection.AnchorInfo anchorInfo3 = selection.start;
                            if (z2) {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo3);
                                anchorInfo = anchorInfo2;
                                anchorInfo2 = anchorInfo3;
                                anchorInfo3 = access$updateSelectionBoundary;
                            } else {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo2);
                                anchorInfo = access$updateSelectionBoundary;
                            }
                            if (Intrinsics.areEqual(access$updateSelectionBoundary, anchorInfo2)) {
                                return selection;
                            }
                            Selection selection2 = new Selection(anchorInfo3, anchorInfo, singleSelectionLayout2.getCrossStatus() == CrossStatus.CROSSED || (singleSelectionLayout2.getCrossStatus() == CrossStatus.COLLAPSED && anchorInfo3.offset > anchorInfo.offset));
                            Selection.AnchorInfo anchorInfo4 = selection2.end;
                            Selection.AnchorInfo anchorInfo5 = selection2.start;
                            if (anchorInfo5.selectableId == anchorInfo4.selectableId) {
                                if (anchorInfo5.offset == anchorInfo4.offset) {
                                    z = true;
                                }
                                z = false;
                            } else {
                                boolean z3 = selection2.handlesCrossed;
                                if ((z3 ? anchorInfo5 : anchorInfo4).offset == 0) {
                                    if (((SingleSelectionLayout) selectionLayout).info.textLayoutResult.layoutInput.text.text.length() == (z3 ? anchorInfo4 : anchorInfo5).offset) {
                                        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                                        ref$BooleanRef.element = true;
                                        new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionLayoutKt$isCollapsed$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj) {
                                                if (((SelectableInfo) obj).textLayoutResult.layoutInput.text.text.length() > 0) {
                                                    Ref$BooleanRef.this.element = false;
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        z = ref$BooleanRef.element;
                                    }
                                }
                                z = false;
                            }
                            if (!z) {
                                return selection2;
                            }
                            SingleSelectionLayout singleSelectionLayout3 = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo3 = singleSelectionLayout3.info;
                            String str = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            Selection selection3 = singleSelectionLayout3.previousSelection;
                            if (selection3 == null) {
                                return selection2;
                            }
                            if (str.length() == 0) {
                                return selection2;
                            }
                            String str2 = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            int length = str2.length();
                            boolean z4 = singleSelectionLayout3.isStartHandle;
                            int i22 = selectableInfo3.rawStartHandleOffset;
                            if (i22 == 0) {
                                int findFollowingBreak = StringHelpers_androidKt.findFollowingBreak(0, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findFollowingBreak), null, true, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findFollowingBreak), false, 1);
                            } else if (i22 == length) {
                                int findPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak(length, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak), null, false, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak), true, 1);
                            } else {
                                boolean z5 = selection3.handlesCrossed;
                                int findPrecedingBreak2 = z4 ^ z5 ? StringHelpers_androidKt.findPrecedingBreak(i22, str2) : StringHelpers_androidKt.findFollowingBreak(i22, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak2), null, z5, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak2), z5, 1);
                            }
                            return copy$default;
                    }
                }
            };
            final int i4 = 3;
            CharacterWithWordAccelerate = new SelectionAdjustment() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.foundation.text.selection.SelectionAdjustment
                public final Selection adjust(SelectionLayout selectionLayout) {
                    Selection.AnchorInfo access$updateSelectionBoundary;
                    Selection.AnchorInfo anchorInfo;
                    boolean z;
                    Selection copy$default;
                    switch (i4) {
                        case 0:
                            SelectionAdjustment.Companion companion = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo = singleSelectionLayout.info;
                            return new Selection(selectableInfo.anchorForOffset(selectableInfo.rawStartHandleOffset), selectableInfo.anchorForOffset(selectableInfo.rawEndHandleOffset), singleSelectionLayout.getCrossStatus() == CrossStatus.CROSSED);
                        case 1:
                            SelectionAdjustment.Companion companion2 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Word$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i22) {
                                    return selectableInfo2.textLayoutResult.m744getWordBoundaryjx7JFs(i22);
                                }
                            });
                        case 2:
                            SelectionAdjustment.Companion companion3 = SelectionAdjustment.Companion.$$INSTANCE;
                            return SelectionAdjustmentKt.access$adjustToBoundaries(selectionLayout, new BoundaryFunction() { // from class: androidx.compose.foundation.text.selection.SelectionAdjustment$Companion$Paragraph$1$1
                                @Override // androidx.compose.foundation.text.selection.BoundaryFunction
                                /* renamed from: getBoundary-fzxv0v0 */
                                public final long mo233getBoundaryfzxv0v0(SelectableInfo selectableInfo2, int i22) {
                                    String str = selectableInfo2.textLayoutResult.layoutInput.text.text;
                                    return TextRangeKt.TextRange(StringHelpersKt.findParagraphStart(i22, str), StringHelpersKt.findParagraphEnd(i22, str));
                                }
                            });
                        default:
                            SelectionAdjustment.Companion companion4 = SelectionAdjustment.Companion.$$INSTANCE;
                            SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
                            Selection selection = singleSelectionLayout2.previousSelection;
                            if (selection == null) {
                                return SelectionAdjustment.Companion.Word.adjust(selectionLayout);
                            }
                            SelectableInfo selectableInfo2 = singleSelectionLayout2.info;
                            boolean z2 = singleSelectionLayout2.isStartHandle;
                            Selection.AnchorInfo anchorInfo2 = selection.end;
                            Selection.AnchorInfo anchorInfo3 = selection.start;
                            if (z2) {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo3);
                                anchorInfo = anchorInfo2;
                                anchorInfo2 = anchorInfo3;
                                anchorInfo3 = access$updateSelectionBoundary;
                            } else {
                                access$updateSelectionBoundary = SelectionAdjustmentKt.access$updateSelectionBoundary(selectionLayout, selectableInfo2, anchorInfo2);
                                anchorInfo = access$updateSelectionBoundary;
                            }
                            if (Intrinsics.areEqual(access$updateSelectionBoundary, anchorInfo2)) {
                                return selection;
                            }
                            Selection selection2 = new Selection(anchorInfo3, anchorInfo, singleSelectionLayout2.getCrossStatus() == CrossStatus.CROSSED || (singleSelectionLayout2.getCrossStatus() == CrossStatus.COLLAPSED && anchorInfo3.offset > anchorInfo.offset));
                            Selection.AnchorInfo anchorInfo4 = selection2.end;
                            Selection.AnchorInfo anchorInfo5 = selection2.start;
                            if (anchorInfo5.selectableId == anchorInfo4.selectableId) {
                                if (anchorInfo5.offset == anchorInfo4.offset) {
                                    z = true;
                                }
                                z = false;
                            } else {
                                boolean z3 = selection2.handlesCrossed;
                                if ((z3 ? anchorInfo5 : anchorInfo4).offset == 0) {
                                    if (((SingleSelectionLayout) selectionLayout).info.textLayoutResult.layoutInput.text.text.length() == (z3 ? anchorInfo4 : anchorInfo5).offset) {
                                        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                                        ref$BooleanRef.element = true;
                                        new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionLayoutKt$isCollapsed$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj) {
                                                if (((SelectableInfo) obj).textLayoutResult.layoutInput.text.text.length() > 0) {
                                                    Ref$BooleanRef.this.element = false;
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        z = ref$BooleanRef.element;
                                    }
                                }
                                z = false;
                            }
                            if (!z) {
                                return selection2;
                            }
                            SingleSelectionLayout singleSelectionLayout3 = (SingleSelectionLayout) selectionLayout;
                            SelectableInfo selectableInfo3 = singleSelectionLayout3.info;
                            String str = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            Selection selection3 = singleSelectionLayout3.previousSelection;
                            if (selection3 == null) {
                                return selection2;
                            }
                            if (str.length() == 0) {
                                return selection2;
                            }
                            String str2 = selectableInfo3.textLayoutResult.layoutInput.text.text;
                            int length = str2.length();
                            boolean z4 = singleSelectionLayout3.isStartHandle;
                            int i22 = selectableInfo3.rawStartHandleOffset;
                            if (i22 == 0) {
                                int findFollowingBreak = StringHelpers_androidKt.findFollowingBreak(0, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findFollowingBreak), null, true, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findFollowingBreak), false, 1);
                            } else if (i22 == length) {
                                int findPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak(length, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak), null, false, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak), true, 1);
                            } else {
                                boolean z5 = selection3.handlesCrossed;
                                int findPrecedingBreak2 = z4 ^ z5 ? StringHelpers_androidKt.findPrecedingBreak(i22, str2) : StringHelpers_androidKt.findFollowingBreak(i22, str2);
                                copy$default = z4 ? Selection.copy$default(selection2, SelectionAdjustmentKt.changeOffset(anchorInfo5, selectableInfo3, findPrecedingBreak2), null, z5, 2) : Selection.copy$default(selection2, null, SelectionAdjustmentKt.changeOffset(anchorInfo4, selectableInfo3, findPrecedingBreak2), z5, 1);
                            }
                            return copy$default;
                    }
                }
            };
        }

        private Companion() {
        }
    }

    Selection adjust(SelectionLayout selectionLayout);
}
