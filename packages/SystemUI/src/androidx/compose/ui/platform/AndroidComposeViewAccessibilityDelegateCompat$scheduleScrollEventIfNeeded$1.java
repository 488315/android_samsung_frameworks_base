package androidx.compose.ui.platform;

import androidx.collection.MutableIntList;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.semantics.ScrollAxisRange;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class AndroidComposeViewAccessibilityDelegateCompat$scheduleScrollEventIfNeeded$1 extends Lambda implements Function0 {
    final /* synthetic */ ScrollObservationScope $scrollObservationScope;
    final /* synthetic */ AndroidComposeViewAccessibilityDelegateCompat this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidComposeViewAccessibilityDelegateCompat$scheduleScrollEventIfNeeded$1(ScrollObservationScope scrollObservationScope, AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat) {
        super(0);
        this.$scrollObservationScope = scrollObservationScope;
        this.this$0 = androidComposeViewAccessibilityDelegateCompat;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SemanticsNode semanticsNode;
        LayoutNode layoutNode;
        ScrollObservationScope scrollObservationScope = this.$scrollObservationScope;
        ScrollAxisRange scrollAxisRange = scrollObservationScope.horizontalScrollAxisRange;
        ScrollAxisRange scrollAxisRange2 = scrollObservationScope.verticalScrollAxisRange;
        Float f = scrollObservationScope.oldXValue;
        Float f2 = scrollObservationScope.oldYValue;
        float fFloatValue = (scrollAxisRange == null || f == null) ? 0.0f : ((Number) scrollAxisRange.value.invoke()).floatValue() - f.floatValue();
        float fFloatValue2 = (scrollAxisRange2 == null || f2 == null) ? 0.0f : ((Number) scrollAxisRange2.value.invoke()).floatValue() - f2.floatValue();
        if (fFloatValue != 0.0f || fFloatValue2 != 0.0f) {
            AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.this$0;
            int i = this.$scrollObservationScope.semanticsNodeId;
            MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
            int iSemanticsNodeIdToAccessibilityVirtualNodeId = androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i);
            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) this.this$0.getCurrentSemanticsNodes().get(this.this$0.accessibilityFocusedVirtualViewId);
            if (semanticsNodeWithAdjustedBounds != null) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat2 = this.this$0;
                try {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = androidComposeViewAccessibilityDelegateCompat2.currentlyAccessibilityFocusedANI;
                    if (accessibilityNodeInfoCompat != null) {
                        accessibilityNodeInfoCompat.setBoundsInScreen(androidComposeViewAccessibilityDelegateCompat2.boundsInScreen(semanticsNodeWithAdjustedBounds));
                        Unit unit = Unit.INSTANCE;
                    }
                } catch (IllegalStateException unused) {
                    Unit unit2 = Unit.INSTANCE;
                }
            }
            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds2 = (SemanticsNodeWithAdjustedBounds) this.this$0.getCurrentSemanticsNodes().get(this.this$0.focusedVirtualViewId);
            if (semanticsNodeWithAdjustedBounds2 != null) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat3 = this.this$0;
                try {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = androidComposeViewAccessibilityDelegateCompat3.currentlyFocusedANI;
                    if (accessibilityNodeInfoCompat2 != null) {
                        accessibilityNodeInfoCompat2.setBoundsInScreen(androidComposeViewAccessibilityDelegateCompat3.boundsInScreen(semanticsNodeWithAdjustedBounds2));
                        Unit unit3 = Unit.INSTANCE;
                    }
                } catch (IllegalStateException unused2) {
                    Unit unit4 = Unit.INSTANCE;
                }
            }
            this.this$0.view.invalidate();
            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds3 = (SemanticsNodeWithAdjustedBounds) this.this$0.getCurrentSemanticsNodes().get(iSemanticsNodeIdToAccessibilityVirtualNodeId);
            if (semanticsNodeWithAdjustedBounds3 != null && (semanticsNode = semanticsNodeWithAdjustedBounds3.semanticsNode) != null && (layoutNode = semanticsNode.layoutNode) != null) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat4 = this.this$0;
                if (scrollAxisRange != null) {
                    androidComposeViewAccessibilityDelegateCompat4.pendingHorizontalScrollEvents.set(iSemanticsNodeIdToAccessibilityVirtualNodeId, scrollAxisRange);
                }
                if (scrollAxisRange2 != null) {
                    androidComposeViewAccessibilityDelegateCompat4.pendingVerticalScrollEvents.set(iSemanticsNodeIdToAccessibilityVirtualNodeId, scrollAxisRange2);
                }
                androidComposeViewAccessibilityDelegateCompat4.notifySubtreeAccessibilityStateChangedIfNeeded(layoutNode);
            }
        }
        if (scrollAxisRange != null) {
            this.$scrollObservationScope.oldXValue = (Float) scrollAxisRange.value.invoke();
        }
        if (scrollAxisRange2 != null) {
            this.$scrollObservationScope.oldYValue = (Float) scrollAxisRange2.value.invoke();
        }
        return Unit.INSTANCE;
    }
}
