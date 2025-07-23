package androidx.compose.ui.platform;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.collection.ArraySet;
import androidx.collection.IntListKt;
import androidx.collection.IntObjectMap;
import androidx.collection.IntObjectMapKt;
import androidx.collection.IntSetKt;
import androidx.collection.MutableIntIntMap;
import androidx.collection.MutableIntList;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableIntSet;
import androidx.collection.SparseArrayCompat;
import androidx.collection.internal.RuntimeHelpersKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.ScrollAxisRange;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.platform.URLSpanCache;
import androidx.compose.ui.util.ListUtilsKt;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidComposeViewAccessibilityDelegateCompat extends AccessibilityDelegateCompat {
    public static final MutableIntList AccessibilityActionsResourceIds;
    public final String ExtraDataTestTraversalAfterVal;
    public final String ExtraDataTestTraversalBeforeVal;
    public final long SendRecurringAccessibilityEventsIntervalMillis;
    public int accessibilityCursorPosition;
    public int accessibilityFocusedVirtualViewId;
    public final android.view.accessibility.AccessibilityManager accessibilityManager;
    public final SparseArrayCompat actionIdToLabel;
    public final BufferedChannel boundsUpdateChannel;
    public boolean checkingForSemanticsChanges;
    public MutableIntObjectMap currentSemanticsNodes;
    public boolean currentSemanticsNodesInvalidated;
    public AccessibilityNodeInfoCompat currentlyAccessibilityFocusedANI;
    public AccessibilityNodeInfoCompat currentlyFocusedANI;
    public List enabledServices;
    public final AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda0 enabledStateListener;
    public int focusedVirtualViewId;
    public final Handler handler;
    public final MutableIntIntMap idToAfterMap;
    public final MutableIntIntMap idToBeforeMap;
    public final SparseArrayCompat labelToActionId;
    public final ComposeAccessibilityNodeProvider nodeProvider;
    public final MutableIntSet paneDisplayed;
    public final MutableIntObjectMap pendingHorizontalScrollEvents;
    public PendingTextTraversedEvent pendingTextTraversedEvent;
    public final MutableIntObjectMap pendingVerticalScrollEvents;
    public final MutableIntObjectMap previousSemanticsNodes;
    public SemanticsNodeCopy previousSemanticsRoot;
    public Integer previousTraversedNode;
    public final Function1 scheduleScrollEventIfNeededLambda;
    public final List scrollObservationScopes;
    public final AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda2 semanticsChangeChecker;
    public boolean sendingFocusAffectingEvent;
    public final ArraySet subtreeChangedLayoutNodes;
    public final AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda1 touchExplorationStateListener;
    public final URLSpanCache urlSpanCache;
    public final AndroidComposeView view;
    public int hoveredVirtualViewId = Integer.MIN_VALUE;
    public final Function1 onSendAccessibilityEvent = new AndroidComposeViewAccessibilityDelegateCompat$onSendAccessibilityEvent$1(this);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Api24Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Api24Impl();
        }

        private Api24Impl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Api29Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Api29Impl();
        }

        private Api29Impl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ComposeAccessibilityNodeProvider extends AccessibilityNodeProviderCompat {
        public ComposeAccessibilityNodeProvider() {
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public final void addExtraDataToAccessibilityNodeInfo(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, String str, Bundle bundle) {
            MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
            AndroidComposeViewAccessibilityDelegateCompat.this.addExtraDataToAccessibilityNodeInfoHelper(i, accessibilityNodeInfoCompat, str, bundle);
        }

        /* JADX WARN: Code restructure failed: missing block: B:143:0x0351, code lost:
        
            if ((r11 == androidx.compose.ui.semantics.LiveRegionMode.Assertive) != false) goto L150;
         */
        /* JADX WARN: Code restructure failed: missing block: B:235:0x0528, code lost:
        
            if ((r9 != null ? kotlin.jvm.internal.Intrinsics.areEqual(androidx.compose.ui.semantics.SemanticsConfigurationKt.getOrNull(r9, r5), java.lang.Boolean.TRUE) : false) == false) goto L247;
         */
        /* JADX WARN: Removed duplicated region for block: B:229:0x052f  */
        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.core.view.accessibility.AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int r24) {
            /*
                Method dump skipped, instructions count: 2527
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.ComposeAccessibilityNodeProvider.createAccessibilityNodeInfo(int):androidx.core.view.accessibility.AccessibilityNodeInfoCompat");
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public final AccessibilityNodeInfoCompat findFocus(int i) {
            AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
            if (i != 1) {
                if (i == 2) {
                    return createAccessibilityNodeInfo(androidComposeViewAccessibilityDelegateCompat.accessibilityFocusedVirtualViewId);
                }
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown focus type: "));
            }
            int i2 = androidComposeViewAccessibilityDelegateCompat.focusedVirtualViewId;
            if (i2 == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(i2);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:385:0x0693, code lost:
        
            if (r0 != 16) goto L435;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:107:0x01fc  */
        /* JADX WARN: Removed duplicated region for block: B:110:0x0239  */
        /* JADX WARN: Removed duplicated region for block: B:119:0x0256  */
        /* JADX WARN: Removed duplicated region for block: B:122:0x026d  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x027d  */
        /* JADX WARN: Removed duplicated region for block: B:136:0x0248  */
        /* JADX WARN: Removed duplicated region for block: B:137:0x01ff  */
        /* JADX WARN: Removed duplicated region for block: B:184:0x0373  */
        /* JADX WARN: Removed duplicated region for block: B:442:0x07ae  */
        /* JADX WARN: Removed duplicated region for block: B:445:0x07b0  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0196 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:95:0x01b5  */
        /* JADX WARN: Type inference failed for: r10v13, types: [androidx.compose.ui.platform.AccessibilityIterators$AbstractTextSegmentIterator, androidx.compose.ui.platform.AccessibilityIterators$WordTextSegmentIterator] */
        /* JADX WARN: Type inference failed for: r4v0, types: [kotlin.jvm.internal.DefaultConstructorMarker] */
        /* JADX WARN: Type inference failed for: r4v1, types: [androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator] */
        /* JADX WARN: Type inference failed for: r4v35 */
        /* JADX WARN: Type inference failed for: r4v38 */
        /* JADX WARN: Type inference failed for: r4v39 */
        /* JADX WARN: Type inference failed for: r4v40 */
        /* JADX WARN: Type inference failed for: r4v45 */
        /* JADX WARN: Type inference failed for: r4v46 */
        /* JADX WARN: Type inference failed for: r4v47 */
        /* JADX WARN: Type inference failed for: r4v8 */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:134:0x0193 -> B:81:0x0194). Please report as a decompilation issue!!! */
        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean performAction(int r18, int r19, android.os.Bundle r20) {
            /*
                Method dump skipped, instructions count: 2126
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.ComposeAccessibilityNodeProvider.performAction(int, int, android.os.Bundle):boolean");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class PendingTextTraversedEvent {
        public final int action;
        public final int fromIndex;
        public final int granularity;
        public final SemanticsNode node;
        public final int toIndex;
        public final long traverseTime;

        public PendingTextTraversedEvent(SemanticsNode semanticsNode, int i, int i2, int i3, int i4, long j) {
            this.node = semanticsNode;
            this.action = i;
            this.granularity = i2;
            this.fromIndex = i3;
            this.toIndex = i4;
            this.traverseTime = j;
        }
    }

    static {
        new Companion(null);
        int[] iArr = {R.id.accessibility_custom_action_0, R.id.accessibility_custom_action_1, R.id.accessibility_custom_action_2, R.id.accessibility_custom_action_3, R.id.accessibility_custom_action_4, R.id.accessibility_custom_action_5, R.id.accessibility_custom_action_6, R.id.accessibility_custom_action_7, R.id.accessibility_custom_action_8, R.id.accessibility_custom_action_9, R.id.accessibility_custom_action_10, R.id.accessibility_custom_action_11, R.id.accessibility_custom_action_12, R.id.accessibility_custom_action_13, R.id.accessibility_custom_action_14, R.id.accessibility_custom_action_15, R.id.accessibility_custom_action_16, R.id.accessibility_custom_action_17, R.id.accessibility_custom_action_18, R.id.accessibility_custom_action_19, R.id.accessibility_custom_action_20, R.id.accessibility_custom_action_21, R.id.accessibility_custom_action_22, R.id.accessibility_custom_action_23, R.id.accessibility_custom_action_24, R.id.accessibility_custom_action_25, R.id.accessibility_custom_action_26, R.id.accessibility_custom_action_27, R.id.accessibility_custom_action_28, R.id.accessibility_custom_action_29, R.id.accessibility_custom_action_30, R.id.accessibility_custom_action_31};
        MutableIntList mutableIntList = IntListKt.EmptyIntList;
        MutableIntList mutableIntList2 = new MutableIntList(32);
        int i = mutableIntList2._size;
        if (i < 0) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
            throw null;
        }
        int i2 = i + 32;
        mutableIntList2.ensureCapacity(i2);
        int[] iArr2 = mutableIntList2.content;
        int i3 = mutableIntList2._size;
        if (i != i3) {
            ArraysKt___ArraysJvmKt.copyInto(i2, i, i3, iArr2, iArr2);
        }
        ArraysKt___ArraysJvmKt.copyInto$default(i, 0, 12, iArr, iArr2);
        mutableIntList2._size += 32;
        AccessibilityActionsResourceIds = mutableIntList2;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v3, types: [androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r6v1, types: [androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda2] */
    public AndroidComposeViewAccessibilityDelegateCompat(AndroidComposeView androidComposeView) {
        this.view = androidComposeView;
        android.view.accessibility.AccessibilityManager accessibilityManager = (android.view.accessibility.AccessibilityManager) androidComposeView.getContext().getSystemService("accessibility");
        this.accessibilityManager = accessibilityManager;
        this.SendRecurringAccessibilityEventsIntervalMillis = 100L;
        this.enabledStateListener = new AccessibilityManager.AccessibilityStateChangeListener() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda0
            @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
            public final void onAccessibilityStateChanged(boolean z) {
                List<AccessibilityServiceInfo> list;
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
                if (z) {
                    list = androidComposeViewAccessibilityDelegateCompat.accessibilityManager.getEnabledAccessibilityServiceList(-1);
                } else {
                    MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
                    list = EmptyList.INSTANCE;
                }
                androidComposeViewAccessibilityDelegateCompat.enabledServices = list;
            }
        };
        this.touchExplorationStateListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda1
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
                androidComposeViewAccessibilityDelegateCompat.enabledServices = androidComposeViewAccessibilityDelegateCompat.accessibilityManager.getEnabledAccessibilityServiceList(-1);
            }
        };
        this.enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(-1);
        this.handler = new Handler(Looper.getMainLooper());
        this.nodeProvider = new ComposeAccessibilityNodeProvider();
        this.accessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.focusedVirtualViewId = Integer.MIN_VALUE;
        int i = 0;
        int i2 = 1;
        DefaultConstructorMarker defaultConstructorMarker = null;
        this.pendingHorizontalScrollEvents = new MutableIntObjectMap(i, i2, defaultConstructorMarker);
        this.pendingVerticalScrollEvents = new MutableIntObjectMap(i, i2, defaultConstructorMarker);
        this.actionIdToLabel = new SparseArrayCompat(i, i2, defaultConstructorMarker);
        this.labelToActionId = new SparseArrayCompat(i, i2, defaultConstructorMarker);
        this.accessibilityCursorPosition = -1;
        this.subtreeChangedLayoutNodes = new ArraySet(i, i2, defaultConstructorMarker);
        this.boundsUpdateChannel = ChannelKt.Channel$default(1, null, null, 6);
        this.currentSemanticsNodesInvalidated = true;
        MutableIntObjectMap mutableIntObjectMap = IntObjectMapKt.EmptyIntObjectMap;
        this.currentSemanticsNodes = mutableIntObjectMap;
        this.paneDisplayed = new MutableIntSet(i, i2, defaultConstructorMarker);
        this.idToBeforeMap = new MutableIntIntMap(i, i2, defaultConstructorMarker);
        this.idToAfterMap = new MutableIntIntMap(i, i2, defaultConstructorMarker);
        this.ExtraDataTestTraversalBeforeVal = "android.view.accessibility.extra.EXTRA_DATA_TEST_TRAVERSALBEFORE_VAL";
        this.ExtraDataTestTraversalAfterVal = "android.view.accessibility.extra.EXTRA_DATA_TEST_TRAVERSALAFTER_VAL";
        this.urlSpanCache = new URLSpanCache();
        this.previousSemanticsNodes = IntObjectMapKt.mutableIntObjectMapOf();
        this.previousSemanticsRoot = new SemanticsNodeCopy(androidComposeView.semanticsOwner.getUnmergedRootSemanticsNode(), mutableIntObjectMap);
        androidComposeView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
                android.view.accessibility.AccessibilityManager accessibilityManager2 = androidComposeViewAccessibilityDelegateCompat.accessibilityManager;
                accessibilityManager2.addAccessibilityStateChangeListener(androidComposeViewAccessibilityDelegateCompat.enabledStateListener);
                accessibilityManager2.addTouchExplorationStateChangeListener(androidComposeViewAccessibilityDelegateCompat.touchExplorationStateListener);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
                androidComposeViewAccessibilityDelegateCompat.handler.removeCallbacks(androidComposeViewAccessibilityDelegateCompat.semanticsChangeChecker);
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat2 = AndroidComposeViewAccessibilityDelegateCompat.this;
                android.view.accessibility.AccessibilityManager accessibilityManager2 = androidComposeViewAccessibilityDelegateCompat2.accessibilityManager;
                accessibilityManager2.removeAccessibilityStateChangeListener(androidComposeViewAccessibilityDelegateCompat2.enabledStateListener);
                accessibilityManager2.removeTouchExplorationStateChangeListener(androidComposeViewAccessibilityDelegateCompat2.touchExplorationStateListener);
            }
        });
        this.semanticsChangeChecker = new Runnable() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
                MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
                Trace.beginSection("measureAndLayout");
                try {
                    AndroidComposeView androidComposeView2 = androidComposeViewAccessibilityDelegateCompat.view;
                    Owner.Companion companion = Owner.Companion;
                    androidComposeView2.measureAndLayout(true);
                    Unit unit = Unit.INSTANCE;
                    Trace.endSection();
                    Trace.beginSection("checkForSemanticsChanges");
                    try {
                        androidComposeViewAccessibilityDelegateCompat.checkForSemanticsChanges();
                        Trace.endSection();
                        androidComposeViewAccessibilityDelegateCompat.checkingForSemanticsChanges = false;
                    } finally {
                    }
                } finally {
                }
            }
        };
        this.scrollObservationScopes = new ArrayList();
        this.scheduleScrollEventIfNeededLambda = new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$scheduleScrollEventIfNeededLambda$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ScrollObservationScope scrollObservationScope = (ScrollObservationScope) obj;
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
                MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
                androidComposeViewAccessibilityDelegateCompat.getClass();
                if (scrollObservationScope.allScopes.contains(scrollObservationScope)) {
                    androidComposeViewAccessibilityDelegateCompat.view.snapshotObserver.observeReads$ui_release(scrollObservationScope, androidComposeViewAccessibilityDelegateCompat.scheduleScrollEventIfNeededLambda, new AndroidComposeViewAccessibilityDelegateCompat$scheduleScrollEventIfNeeded$1(scrollObservationScope, androidComposeViewAccessibilityDelegateCompat));
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static String getIterableTextForAccessibility(SemanticsNode semanticsNode) {
        AnnotatedString annotatedString;
        if (semanticsNode != null) {
            SemanticsProperties.INSTANCE.getClass();
            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.ContentDescription;
            SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
            if (semanticsConfiguration.props.containsKey(semanticsPropertyKey)) {
                return ListUtilsKt.fastJoinToString$default((List) semanticsConfiguration.get(semanticsPropertyKey), ",", null, 62);
            }
            if (semanticsConfiguration.props.containsKey(SemanticsProperties.EditableText)) {
                AnnotatedString textForTextField = getTextForTextField(semanticsConfiguration);
                if (textForTextField != null) {
                    return textForTextField.text;
                }
            } else {
                List list = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.Text);
                if (list != null && (annotatedString = (AnnotatedString) CollectionsKt___CollectionsKt.firstOrNull(list)) != null) {
                    return annotatedString.text;
                }
            }
        }
        return null;
    }

    public static AnnotatedString getTextForTextField(SemanticsConfiguration semanticsConfiguration) {
        SemanticsProperties.INSTANCE.getClass();
        return (AnnotatedString) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.EditableText);
    }

    public static final boolean performActionHelper$canScroll(ScrollAxisRange scrollAxisRange, float f) {
        Function0 function0 = scrollAxisRange.value;
        if (f >= 0.0f || ((Number) function0.invoke()).floatValue() <= 0.0f) {
            return f > 0.0f && ((Number) function0.invoke()).floatValue() < ((Number) scrollAxisRange.maxValue.invoke()).floatValue();
        }
        return true;
    }

    public static final boolean populateAccessibilityNodeInfoProperties$canScrollBackward(ScrollAxisRange scrollAxisRange) {
        Function0 function0 = scrollAxisRange.value;
        float floatValue = ((Number) function0.invoke()).floatValue();
        boolean z = scrollAxisRange.reverseScrolling;
        if (floatValue <= 0.0f || z) {
            return ((Number) function0.invoke()).floatValue() < ((Number) scrollAxisRange.maxValue.invoke()).floatValue() && z;
        }
        return true;
    }

    public static final boolean populateAccessibilityNodeInfoProperties$canScrollForward(ScrollAxisRange scrollAxisRange) {
        Function0 function0 = scrollAxisRange.value;
        float floatValue = ((Number) function0.invoke()).floatValue();
        float floatValue2 = ((Number) scrollAxisRange.maxValue.invoke()).floatValue();
        boolean z = scrollAxisRange.reverseScrolling;
        if (floatValue >= floatValue2 || z) {
            return ((Number) function0.invoke()).floatValue() > 0.0f && z;
        }
        return true;
    }

    public static /* synthetic */ void sendEventForVirtualView$default(AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat, int i, int i2, Integer num, int i3) {
        if ((i3 & 4) != 0) {
            num = null;
        }
        androidComposeViewAccessibilityDelegateCompat.sendEventForVirtualView(i, i2, num, null);
    }

    public static CharSequence trimToSize(CharSequence charSequence) {
        if (charSequence.length() != 0) {
            int i = 100000;
            if (charSequence.length() > 100000) {
                if (Character.isHighSurrogate(charSequence.charAt(99999)) && Character.isLowSurrogate(charSequence.charAt(100000))) {
                    i = 99999;
                }
                return charSequence.subSequence(0, i);
            }
        }
        return charSequence;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addExtraDataToAccessibilityNodeInfoHelper(int r19, androidx.core.view.accessibility.AccessibilityNodeInfoCompat r20, java.lang.String r21, android.os.Bundle r22) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.addExtraDataToAccessibilityNodeInfoHelper(int, androidx.core.view.accessibility.AccessibilityNodeInfoCompat, java.lang.String, android.os.Bundle):void");
    }

    public final Rect boundsInScreen(SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds) {
        Rect rect = semanticsNodeWithAdjustedBounds.adjustedBounds;
        float f = rect.left;
        float f2 = rect.top;
        long floatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Offset.Companion companion = Offset.Companion;
        AndroidComposeView androidComposeView = this.view;
        long m693localToScreenMKHz9U = androidComposeView.m693localToScreenMKHz9U(floatToRawIntBits);
        float f3 = rect.right;
        float f4 = rect.bottom;
        long m693localToScreenMKHz9U2 = androidComposeView.m693localToScreenMKHz9U((Float.floatToRawIntBits(f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L));
        return new Rect((int) Math.floor(Float.intBitsToFloat((int) (m693localToScreenMKHz9U >> 32))), (int) Math.floor(Float.intBitsToFloat((int) (m693localToScreenMKHz9U & 4294967295L))), (int) Math.ceil(Float.intBitsToFloat((int) (m693localToScreenMKHz9U2 >> 32))), (int) Math.ceil(Float.intBitsToFloat((int) (m693localToScreenMKHz9U2 & 4294967295L))));
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x010c, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r6, r2) == r3) goto L47;
     */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x0042: MOVE (r1 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]) (LINE:67), block:B:55:0x0042 */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0092 A[Catch: all -> 0x00b1, TRY_LEAVE, TryCatch #0 {all -> 0x00b1, blocks: (B:11:0x0075, B:17:0x008a, B:19:0x0092, B:22:0x009d, B:24:0x00a2, B:26:0x00b3, B:28:0x00bc, B:29:0x00d8, B:31:0x00e7, B:32:0x00f0, B:10:0x0065), top: B:9:0x0065 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0027 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0062  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x010f -> B:11:0x0075). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object boundsUpdatesEventLoop$ui_release(kotlin.coroutines.jvm.internal.ContinuationImpl r18) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.boundsUpdatesEventLoop$ui_release(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: canScroll-0AR0LA0$ui_release, reason: not valid java name */
    public final boolean m699canScroll0AR0LA0$ui_release(long j, int i, boolean z) {
        SemanticsPropertyKey semanticsPropertyKey;
        int i2;
        ScrollAxisRange scrollAxisRange;
        if (!Intrinsics.areEqual(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            return false;
        }
        IntObjectMap currentSemanticsNodes = getCurrentSemanticsNodes();
        Offset.Companion.getClass();
        if (Offset.m396equalsimpl0(j, Offset.Unspecified) || (((9223372034707292159L & j) + 36028792732385279L) & (-9223372034707292160L)) != 0) {
            return false;
        }
        if (z) {
            SemanticsProperties.INSTANCE.getClass();
            semanticsPropertyKey = SemanticsProperties.VerticalScrollAxisRange;
        } else {
            if (z) {
                throw new NoWhenBranchMatchedException();
            }
            SemanticsProperties.INSTANCE.getClass();
            semanticsPropertyKey = SemanticsProperties.HorizontalScrollAxisRange;
        }
        Object[] objArr = currentSemanticsNodes.values;
        long[] jArr = currentSemanticsNodes.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return false;
        }
        int i3 = 0;
        boolean z2 = false;
        while (true) {
            long j2 = jArr[i3];
            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i4 = 8;
                int i5 = 8 - ((~(i3 - length)) >>> 31);
                int i6 = 0;
                while (i6 < i5) {
                    if ((j2 & 255) < 128) {
                        SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) objArr[(i3 << 3) + i6];
                        if (RectHelper_androidKt.toComposeRect(semanticsNodeWithAdjustedBounds.adjustedBounds).m405containsk4lQ0M(j) && (scrollAxisRange = (ScrollAxisRange) SemanticsConfigurationKt.getOrNull(semanticsNodeWithAdjustedBounds.semanticsNode.unmergedConfig, semanticsPropertyKey)) != null) {
                            boolean z3 = scrollAxisRange.reverseScrolling;
                            i2 = i4;
                            int i7 = z3 ? -i : i;
                            if (i == 0 && z3) {
                                i7 = -1;
                            }
                            Function0 function0 = scrollAxisRange.value;
                            if (i7 < 0) {
                                if (((Number) function0.invoke()).floatValue() <= 0.0f) {
                                }
                                z2 = true;
                            } else {
                                if (((Number) function0.invoke()).floatValue() >= ((Number) scrollAxisRange.maxValue.invoke()).floatValue()) {
                                }
                                z2 = true;
                            }
                        } else {
                            i2 = i4;
                        }
                    } else {
                        i2 = i4;
                    }
                    j2 >>= i2;
                    i6++;
                    i4 = i2;
                }
                if (i5 != i4) {
                    return z2;
                }
            }
            if (i3 == length) {
                return z2;
            }
            i3++;
        }
    }

    public final void checkForSemanticsChanges() {
        Trace.beginSection("sendAccessibilitySemanticsStructureChangeEvents");
        try {
            if (isEnabled$ui_release()) {
                sendAccessibilitySemanticsStructureChangeEvents(this.view.semanticsOwner.getUnmergedRootSemanticsNode(), this.previousSemanticsRoot);
            }
            Unit unit = Unit.INSTANCE;
            Trace.endSection();
            Trace.beginSection("sendSemanticsPropertyChangeEvents");
            try {
                sendSemanticsPropertyChangeEvents(getCurrentSemanticsNodes());
                Trace.endSection();
                Trace.beginSection("updateSemanticsNodesCopyAndPanes");
                try {
                    updateSemanticsNodesCopyAndPanes();
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    public final AccessibilityEvent createEvent(int i, int i2) {
        SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds;
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        obtain.setEnabled(true);
        obtain.setClassName("android.view.View");
        AndroidComposeView androidComposeView = this.view;
        obtain.setPackageName(androidComposeView.getContext().getPackageName());
        obtain.setSource(androidComposeView, i);
        if (isEnabled$ui_release() && (semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) getCurrentSemanticsNodes().get(i)) != null) {
            SemanticsConfiguration semanticsConfiguration = semanticsNodeWithAdjustedBounds.semanticsNode.unmergedConfig;
            SemanticsProperties.INSTANCE.getClass();
            obtain.setPassword(semanticsConfiguration.props.containsKey(SemanticsProperties.Password));
        }
        return obtain;
    }

    public final AccessibilityEvent createTextSelectionChangedEvent(int i, Integer num, Integer num2, Integer num3, CharSequence charSequence) {
        AccessibilityEvent createEvent = createEvent(i, 8192);
        if (num != null) {
            createEvent.setFromIndex(num.intValue());
        }
        if (num2 != null) {
            createEvent.setToIndex(num2.intValue());
        }
        if (num3 != null) {
            createEvent.setItemCount(num3.intValue());
        }
        if (charSequence != null) {
            createEvent.getText().add(charSequence);
        }
        return createEvent;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return this.nodeProvider;
    }

    public final int getAccessibilitySelectionEnd(SemanticsNode semanticsNode) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.ContentDescription;
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        if (!semanticsConfiguration.props.containsKey(semanticsPropertyKey)) {
            SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.TextSelectionRange;
            if (semanticsConfiguration.props.containsKey(semanticsPropertyKey2)) {
                return (int) (((TextRange) semanticsConfiguration.get(semanticsPropertyKey2)).packedValue & 4294967295L);
            }
        }
        return this.accessibilityCursorPosition;
    }

    public final int getAccessibilitySelectionStart(SemanticsNode semanticsNode) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.ContentDescription;
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        if (!semanticsConfiguration.props.containsKey(semanticsPropertyKey)) {
            SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.TextSelectionRange;
            if (semanticsConfiguration.props.containsKey(semanticsPropertyKey2)) {
                return (int) (((TextRange) semanticsConfiguration.get(semanticsPropertyKey2)).packedValue >> 32);
            }
        }
        return this.accessibilityCursorPosition;
    }

    public final IntObjectMap getCurrentSemanticsNodes() {
        if (this.currentSemanticsNodesInvalidated) {
            this.currentSemanticsNodesInvalidated = false;
            AndroidComposeView androidComposeView = this.view;
            this.currentSemanticsNodes = SemanticsUtils_androidKt.getAllUncoveredSemanticsNodesToIntObjectMap(androidComposeView.semanticsOwner);
            if (isEnabled$ui_release()) {
                MutableIntObjectMap mutableIntObjectMap = this.currentSemanticsNodes;
                Resources resources = androidComposeView.getContext().getResources();
                Comparator[] comparatorArr = AndroidComposeViewAccessibilityDelegateCompat_androidKt.semanticComparators;
                MutableIntIntMap mutableIntIntMap = this.idToBeforeMap;
                mutableIntIntMap.clear();
                MutableIntIntMap mutableIntIntMap2 = this.idToAfterMap;
                mutableIntIntMap2.clear();
                SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) mutableIntObjectMap.get(-1);
                SemanticsNode semanticsNode = semanticsNodeWithAdjustedBounds != null ? semanticsNodeWithAdjustedBounds.semanticsNode : null;
                semanticsNode.getClass();
                ArrayList arrayList = (ArrayList) AndroidComposeViewAccessibilityDelegateCompat_androidKt.subtreeSortedByGeometryGrouping(AndroidComposeViewAccessibilityDelegateCompat_androidKt.isRtl(semanticsNode), Collections.singletonList(semanticsNode), mutableIntObjectMap, resources);
                int size = arrayList.size() - 1;
                if (1 <= size) {
                    int i = 1;
                    while (true) {
                        int i2 = ((SemanticsNode) arrayList.get(i - 1)).id;
                        int i3 = ((SemanticsNode) arrayList.get(i)).id;
                        mutableIntIntMap.set(i2, i3);
                        mutableIntIntMap2.set(i3, i2);
                        if (i == size) {
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        return this.currentSemanticsNodes;
    }

    public final boolean isEnabled$ui_release() {
        return this.accessibilityManager.isEnabled() && !this.enabledServices.isEmpty();
    }

    public final void notifySubtreeAccessibilityStateChangedIfNeeded(LayoutNode layoutNode) {
        if (this.subtreeChangedLayoutNodes.add(layoutNode)) {
            this.boundsUpdateChannel.mo3456trySendJP2dKIU(Unit.INSTANCE);
        }
    }

    public final int semanticsNodeIdToAccessibilityVirtualNodeId(int i) {
        if (i == this.view.semanticsOwner.getUnmergedRootSemanticsNode().id) {
            return -1;
        }
        return i;
    }

    public final void sendAccessibilitySemanticsStructureChangeEvents(SemanticsNode semanticsNode, SemanticsNodeCopy semanticsNodeCopy) {
        int i;
        int i2;
        int i3 = 1;
        int[] iArr = IntSetKt.EmptyIntArray;
        int i4 = 0;
        MutableIntSet mutableIntSet = new MutableIntSet(i4, i3, null);
        List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
        int size = children$ui_release$default.size();
        int i5 = 0;
        while (true) {
            LayoutNode layoutNode = semanticsNode.layoutNode;
            if (i5 >= size) {
                MutableIntSet mutableIntSet2 = semanticsNodeCopy.children;
                int[] iArr2 = mutableIntSet2.elements;
                long[] jArr = mutableIntSet2.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i6 = 0;
                    while (true) {
                        long j = jArr[i6];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i7 = 8 - ((~(i6 - length)) >>> 31);
                            int i8 = 0;
                            while (i8 < i7) {
                                if ((j & 255) < 128) {
                                    i2 = i3;
                                    if (!mutableIntSet.contains(iArr2[(i6 << 3) + i8])) {
                                        notifySubtreeAccessibilityStateChangedIfNeeded(layoutNode);
                                        return;
                                    }
                                } else {
                                    i2 = i3;
                                }
                                j >>= 8;
                                i8++;
                                i3 = i2;
                            }
                            i = i3;
                            if (i7 != 8) {
                                break;
                            }
                        } else {
                            i = i3;
                        }
                        if (i6 == length) {
                            break;
                        }
                        i6++;
                        i3 = i;
                    }
                }
                List children$ui_release$default2 = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
                int size2 = children$ui_release$default2.size();
                while (i4 < size2) {
                    SemanticsNode semanticsNode2 = (SemanticsNode) children$ui_release$default2.get(i4);
                    if (getCurrentSemanticsNodes().containsKey(semanticsNode2.id)) {
                        Object obj = this.previousSemanticsNodes.get(semanticsNode2.id);
                        obj.getClass();
                        sendAccessibilitySemanticsStructureChangeEvents(semanticsNode2, (SemanticsNodeCopy) obj);
                    }
                    i4++;
                }
                return;
            }
            SemanticsNode semanticsNode3 = (SemanticsNode) children$ui_release$default.get(i5);
            if (getCurrentSemanticsNodes().containsKey(semanticsNode3.id)) {
                MutableIntSet mutableIntSet3 = semanticsNodeCopy.children;
                int i9 = semanticsNode3.id;
                if (!mutableIntSet3.contains(i9)) {
                    notifySubtreeAccessibilityStateChangedIfNeeded(layoutNode);
                    return;
                }
                mutableIntSet.add(i9);
            }
            i5++;
        }
    }

    public final boolean sendEvent(AccessibilityEvent accessibilityEvent) {
        if (!isEnabled$ui_release()) {
            return false;
        }
        if (accessibilityEvent.getEventType() == 2048 || accessibilityEvent.getEventType() == 32768) {
            this.sendingFocusAffectingEvent = true;
        }
        try {
            return ((Boolean) ((AndroidComposeViewAccessibilityDelegateCompat$onSendAccessibilityEvent$1) this.onSendAccessibilityEvent).mo779invoke(accessibilityEvent)).booleanValue();
        } finally {
            this.sendingFocusAffectingEvent = false;
        }
    }

    public final boolean sendEventForVirtualView(int i, int i2, Integer num, List list) {
        if (i == Integer.MIN_VALUE || !isEnabled$ui_release()) {
            return false;
        }
        AccessibilityEvent createEvent = createEvent(i, i2);
        if (num != null) {
            createEvent.setContentChangeTypes(num.intValue());
        }
        if (list != null) {
            createEvent.setContentDescription(ListUtilsKt.fastJoinToString$default(list, ",", null, 62));
        }
        return sendEvent(createEvent);
    }

    public final void sendPaneChangeEvents(int i, int i2, String str) {
        AccessibilityEvent createEvent = createEvent(semanticsNodeIdToAccessibilityVirtualNodeId(i), 32);
        createEvent.setContentChangeTypes(i2);
        if (str != null) {
            createEvent.getText().add(str);
        }
        sendEvent(createEvent);
    }

    public final void sendPendingTextTraversedAtGranularityEvent(int i) {
        PendingTextTraversedEvent pendingTextTraversedEvent = this.pendingTextTraversedEvent;
        if (pendingTextTraversedEvent != null) {
            SemanticsNode semanticsNode = pendingTextTraversedEvent.node;
            if (i != semanticsNode.id) {
                return;
            }
            if (SystemClock.uptimeMillis() - pendingTextTraversedEvent.traverseTime <= 1000) {
                AccessibilityEvent createEvent = createEvent(semanticsNodeIdToAccessibilityVirtualNodeId(semanticsNode.id), 131072);
                createEvent.setFromIndex(pendingTextTraversedEvent.fromIndex);
                createEvent.setToIndex(pendingTextTraversedEvent.toIndex);
                createEvent.setAction(pendingTextTraversedEvent.action);
                createEvent.setMovementGranularity(pendingTextTraversedEvent.granularity);
                createEvent.getText().add(getIterableTextForAccessibility(semanticsNode));
                sendEvent(createEvent);
            }
        }
        this.pendingTextTraversedEvent = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:179:0x0509, code lost:
    
        if (r1.containsAll(r4) != false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x050c, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0515, code lost:
    
        if (r1.isEmpty() == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0543, code lost:
    
        if (r1 != null) goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x0548, code lost:
    
        if (r1 == null) goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x054e, code lost:
    
        if (r1 != false) goto L189;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendSemanticsPropertyChangeEvents(androidx.collection.IntObjectMap r51) {
        /*
            Method dump skipped, instructions count: 1645
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.sendSemanticsPropertyChangeEvents(androidx.collection.IntObjectMap):void");
    }

    public final void sendSubtreeChangeAccessibilityEvents(LayoutNode layoutNode, MutableIntSet mutableIntSet) {
        SemanticsConfiguration semanticsConfiguration;
        LayoutNode findClosestParentNode;
        if (layoutNode.isAttached() && !this.view.getAndroidViewsHandler$ui_release().layoutNodeToHolder.containsKey(layoutNode)) {
            if (!layoutNode.nodes.m663hasH91voCI$ui_release(8)) {
                layoutNode = AndroidComposeViewAccessibilityDelegateCompat_androidKt.findClosestParentNode(layoutNode, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$sendSubtreeChangeAccessibilityEvents$semanticsNode$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        return Boolean.valueOf(((LayoutNode) obj).nodes.m663hasH91voCI$ui_release(8));
                    }
                });
            }
            if (layoutNode == null || (semanticsConfiguration = layoutNode.getSemanticsConfiguration()) == null) {
                return;
            }
            if (!semanticsConfiguration.isMergingSemanticsOfDescendants && (findClosestParentNode = AndroidComposeViewAccessibilityDelegateCompat_androidKt.findClosestParentNode(layoutNode, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$sendSubtreeChangeAccessibilityEvents$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    SemanticsConfiguration semanticsConfiguration2 = ((LayoutNode) obj).getSemanticsConfiguration();
                    boolean z = false;
                    if (semanticsConfiguration2 != null && semanticsConfiguration2.isMergingSemanticsOfDescendants) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            })) != null) {
                layoutNode = findClosestParentNode;
            }
            int i = layoutNode.semanticsId;
            if (mutableIntSet.add(i)) {
                sendEventForVirtualView$default(this, semanticsNodeIdToAccessibilityVirtualNodeId(i), 2048, 1, 8);
            }
        }
    }

    public final void sendTypeViewScrolledAccessibilityEvent(LayoutNode layoutNode) {
        if (layoutNode.isAttached() && !this.view.getAndroidViewsHandler$ui_release().layoutNodeToHolder.containsKey(layoutNode)) {
            int i = layoutNode.semanticsId;
            ScrollAxisRange scrollAxisRange = (ScrollAxisRange) this.pendingHorizontalScrollEvents.get(i);
            ScrollAxisRange scrollAxisRange2 = (ScrollAxisRange) this.pendingVerticalScrollEvents.get(i);
            if (scrollAxisRange == null && scrollAxisRange2 == null) {
                return;
            }
            AccessibilityEvent createEvent = createEvent(i, 4096);
            if (scrollAxisRange != null) {
                createEvent.setScrollX((int) ((Number) scrollAxisRange.value.invoke()).floatValue());
                createEvent.setMaxScrollX((int) ((Number) scrollAxisRange.maxValue.invoke()).floatValue());
            }
            if (scrollAxisRange2 != null) {
                createEvent.setScrollY((int) ((Number) scrollAxisRange2.value.invoke()).floatValue());
                createEvent.setMaxScrollY((int) ((Number) scrollAxisRange2.maxValue.invoke()).floatValue());
            }
            sendEvent(createEvent);
        }
    }

    public final boolean setAccessibilitySelection(SemanticsNode semanticsNode, int i, int i2, boolean z) {
        String iterableTextForAccessibility;
        SemanticsActions.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsActions.SetSelection;
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        if (semanticsConfiguration.props.containsKey(semanticsPropertyKey) && AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
            Function3 function3 = (Function3) ((AccessibilityAction) semanticsConfiguration.get(semanticsPropertyKey)).action;
            if (function3 != null) {
                return ((Boolean) function3.invoke(Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
            }
        } else if ((i != i2 || i2 != this.accessibilityCursorPosition) && (iterableTextForAccessibility = getIterableTextForAccessibility(semanticsNode)) != null) {
            if (i < 0 || i != i2 || i2 > iterableTextForAccessibility.length()) {
                i = -1;
            }
            this.accessibilityCursorPosition = i;
            boolean z2 = iterableTextForAccessibility.length() > 0;
            int i3 = semanticsNode.id;
            sendEvent(createTextSelectionChangedEvent(semanticsNodeIdToAccessibilityVirtualNodeId(i3), z2 ? Integer.valueOf(this.accessibilityCursorPosition) : null, z2 ? Integer.valueOf(this.accessibilityCursorPosition) : null, z2 ? Integer.valueOf(iterableTextForAccessibility.length()) : null, iterableTextForAccessibility));
            sendPendingTextTraversedAtGranularityEvent(i3);
            return true;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x0154, code lost:
    
        r31 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x015e, code lost:
    
        if (((r1 & ((~r1) << 6)) & r24) == 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0160, code lost:
    
        r29 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSemanticsNodesCopyAndPanes() {
        /*
            Method dump skipped, instructions count: 603
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.updateSemanticsNodesCopyAndPanes():void");
    }
}
