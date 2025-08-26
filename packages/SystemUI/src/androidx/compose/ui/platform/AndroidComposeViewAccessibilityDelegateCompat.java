package androidx.compose.ui.platform;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.collection.ArraySet;
import androidx.collection.IntListKt;
import androidx.collection.IntObjectMap;
import androidx.collection.IntObjectMapKt;
import androidx.collection.IntSetKt;
import androidx.collection.MutableIntIntMap;
import androidx.collection.MutableIntList;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableIntSet;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.ObjectIntMapKt;
import androidx.collection.ScatterMapKt;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.collection.internal.ContainerHelpersKt;
import androidx.collection.internal.RuntimeHelpersKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.accessibility.CollectionInfo_androidKt;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.CollectionInfo;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.LiveRegionMode;
import androidx.compose.ui.semantics.ProgressBarRangeInfo;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.ScrollAxisRange;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesAndroid;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.state.ToggleableState;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.AndroidAccessibilitySpannableString_androidKt;
import androidx.compose.ui.text.platform.URLSpanCache;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.util.ListUtilsKt;
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;

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

    final class Api24Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Api24Impl();
        }

        private Api24Impl() {
        }
    }

    final class Api29Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Api29Impl();
        }

        private Api29Impl() {
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    final class ComposeAccessibilityNodeProvider extends AccessibilityNodeProviderCompat {
        public ComposeAccessibilityNodeProvider() {
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public final void addExtraDataToAccessibilityNodeInfo(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, String str, Bundle bundle) {
            MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
            AndroidComposeViewAccessibilityDelegateCompat.this.addExtraDataToAccessibilityNodeInfoHelper(i, accessibilityNodeInfoCompat, str, bundle);
        }

        /* JADX WARN: Removed duplicated region for block: B:144:0x033a  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
        /* JADX WARN: Removed duplicated region for block: B:169:0x038d  */
        /* JADX WARN: Removed duplicated region for block: B:247:0x051c  */
        /* JADX WARN: Removed duplicated region for block: B:250:0x0521  */
        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain;
            AndroidViewHolder androidViewHolderSemanticsIdToView;
            int i2;
            MutableObjectIntMap mutableObjectIntMap;
            AccessibilityAction accessibilityAction;
            boolean z;
            boolean zBooleanValue;
            LifecycleOwner lifecycleOwner;
            Lifecycle lifecycle;
            int i3 = 1;
            AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = AndroidComposeViewAccessibilityDelegateCompat.this;
            AndroidComposeView androidComposeView = androidComposeViewAccessibilityDelegateCompat.view;
            AndroidComposeView.ViewTreeOwners viewTreeOwners = androidComposeView.getViewTreeOwners();
            if (((viewTreeOwners == null || (lifecycleOwner = viewTreeOwners.lifecycleOwner) == null || (lifecycle = lifecycleOwner.getLifecycle()) == null) ? null : lifecycle.getCurrentState()) == Lifecycle.State.DESTROYED) {
                accessibilityNodeInfoCompatObtain = !androidComposeViewAccessibilityDelegateCompat.accessibilityManager.isEnabled() ? AccessibilityNodeInfoCompat.obtain() : null;
            } else {
                SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) androidComposeViewAccessibilityDelegateCompat.getCurrentSemanticsNodes().get(i);
                if (semanticsNodeWithAdjustedBounds != null) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain2 = AccessibilityNodeInfoCompat.obtain();
                    SemanticsNode semanticsNode = semanticsNodeWithAdjustedBounds.semanticsNode;
                    if (i == -1) {
                        Object parentForAccessibility = androidComposeView.getParentForAccessibility();
                        View view = parentForAccessibility instanceof View ? (View) parentForAccessibility : null;
                        accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId = -1;
                        accessibilityNodeInfoCompatObtain2.mInfo.setParent(view);
                    } else {
                        SemanticsNode parent = semanticsNode.getParent();
                        Integer numValueOf = parent != null ? Integer.valueOf(parent.id) : null;
                        if (numValueOf == null) {
                            InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("semanticsNode " + i + " has null parent");
                            throw new KotlinNothingValueException();
                        }
                        int iIntValue = numValueOf.intValue();
                        if (iIntValue == androidComposeView.semanticsOwner.getUnmergedRootSemanticsNode().id) {
                            iIntValue = -1;
                        }
                        accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId = iIntValue;
                        accessibilityNodeInfoCompatObtain2.mInfo.setParent(androidComposeView, iIntValue);
                    }
                    accessibilityNodeInfoCompatObtain2.mVirtualDescendantId = i;
                    accessibilityNodeInfoCompatObtain2.mInfo.setSource(androidComposeView, i);
                    accessibilityNodeInfoCompatObtain2.setBoundsInScreen(androidComposeViewAccessibilityDelegateCompat.boundsInScreen(semanticsNodeWithAdjustedBounds));
                    Resources resources = androidComposeView.getContext().getResources();
                    accessibilityNodeInfoCompatObtain2.setClassName("android.view.View");
                    SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
                    SemanticsProperties.INSTANCE.getClass();
                    if (semanticsConfiguration.props.containsKey(SemanticsProperties.EditableText)) {
                        accessibilityNodeInfoCompatObtain2.setClassName("android.widget.EditText");
                    }
                    SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Text;
                    SemanticsConfiguration semanticsConfiguration2 = semanticsNode.unmergedConfig;
                    if (semanticsConfiguration2.props.containsKey(semanticsPropertyKey)) {
                        accessibilityNodeInfoCompatObtain2.setClassName("android.widget.TextView");
                    }
                    Role role = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Role);
                    if (role != null) {
                        if (semanticsNode.isFake || SemanticsNode.getChildren$ui_release$default(4, semanticsNode).isEmpty()) {
                            Role.Companion.getClass();
                            int i4 = Role.Tab;
                            int i5 = role.value;
                            if (i5 == i4) {
                                accessibilityNodeInfoCompatObtain2.setRoleDescription(resources.getString(R.string.tab));
                            } else if (i5 == Role.Switch) {
                                accessibilityNodeInfoCompatObtain2.setRoleDescription(resources.getString(R.string.switch_role));
                            } else {
                                String strM712toLegacyClassNameV4PA4sw = SemanticsUtils_androidKt.m712toLegacyClassNameV4PA4sw(i5);
                                if (i5 != Role.Image || semanticsNode.isUnmergedLeafNode$ui_release() || semanticsConfiguration2.isMergingSemanticsOfDescendants) {
                                    accessibilityNodeInfoCompatObtain2.setClassName(strM712toLegacyClassNameV4PA4sw);
                                }
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setPackageName(androidComposeView.getContext().getPackageName());
                    accessibilityNodeInfoCompatObtain2.mInfo.setImportantForAccessibility(SemanticsUtils_androidKt.isImportantForAccessibility(semanticsNode));
                    List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
                    int size = children$ui_release$default.size();
                    for (int i6 = 0; i6 < size; i6++) {
                        SemanticsNode semanticsNode2 = (SemanticsNode) children$ui_release$default.get(i6);
                        if (androidComposeViewAccessibilityDelegateCompat.getCurrentSemanticsNodes().containsKey(semanticsNode2.id)) {
                            AndroidViewHolder androidViewHolder = (AndroidViewHolder) androidComposeView.getAndroidViewsHandler$ui_release().layoutNodeToHolder.get(semanticsNode2.layoutNode);
                            int i7 = semanticsNode2.id;
                            if (i7 != -1) {
                                if (androidViewHolder != null) {
                                    accessibilityNodeInfoCompatObtain2.mInfo.addChild(androidViewHolder);
                                } else {
                                    accessibilityNodeInfoCompatObtain2.mInfo.addChild(androidComposeView, i7);
                                }
                            }
                        }
                    }
                    if (i == androidComposeViewAccessibilityDelegateCompat.accessibilityFocusedVirtualViewId) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setAccessibilityFocused(true);
                        accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
                    } else {
                        accessibilityNodeInfoCompatObtain2.mInfo.setAccessibilityFocused(false);
                        accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_ACCESSIBILITY_FOCUS);
                    }
                    AnnotatedString infoText = AndroidComposeViewAccessibilityDelegateCompat_androidKt.getInfoText(semanticsNode);
                    accessibilityNodeInfoCompatObtain2.setText(infoText != null ? (SpannableString) AndroidComposeViewAccessibilityDelegateCompat.trimToSize(AndroidAccessibilitySpannableString_androidKt.toAccessibilitySpannableString(infoText, (Density) ((SnapshotMutableStateImpl) androidComposeView.density$delegate).getValue(), (FontFamily.Resolver) ((SnapshotMutableStateImpl) androidComposeView.fontFamilyResolver$delegate).getValue(), androidComposeViewAccessibilityDelegateCompat.urlSpanCache)) : null);
                    SemanticsProperties.INSTANCE.getClass();
                    SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.Error;
                    if (semanticsConfiguration2.props.containsKey(semanticsPropertyKey2)) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setContentInvalid(true);
                        accessibilityNodeInfoCompatObtain2.mInfo.setError((CharSequence) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, semanticsPropertyKey2));
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setStateDescription(AndroidComposeViewAccessibilityDelegateCompat_androidKt.getInfoStateDescriptionOrNull(semanticsNode, resources));
                    accessibilityNodeInfoCompatObtain2.setCheckable(AndroidComposeViewAccessibilityDelegateCompat_androidKt.getInfoIsCheckable(semanticsNode));
                    ToggleableState toggleableState = (ToggleableState) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.ToggleableState);
                    if (toggleableState != null) {
                        if (toggleableState == ToggleableState.On) {
                            accessibilityNodeInfoCompatObtain2.setChecked(true);
                        } else if (toggleableState == ToggleableState.Off) {
                            accessibilityNodeInfoCompatObtain2.setChecked(false);
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                    Boolean bool = (Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Selected);
                    if (bool != null) {
                        boolean zBooleanValue2 = bool.booleanValue();
                        Role.Companion.getClass();
                        int i8 = Role.Tab;
                        if (role != null && role.value == i8) {
                            accessibilityNodeInfoCompatObtain2.mInfo.setSelected(zBooleanValue2);
                        } else {
                            accessibilityNodeInfoCompatObtain2.setChecked(zBooleanValue2);
                        }
                        Unit unit3 = Unit.INSTANCE;
                    }
                    if (!semanticsConfiguration2.isMergingSemanticsOfDescendants || SemanticsNode.getChildren$ui_release$default(4, semanticsNode).isEmpty()) {
                        List list = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.ContentDescription);
                        accessibilityNodeInfoCompatObtain2.setContentDescription(list != null ? (String) CollectionsKt___CollectionsKt.firstOrNull(list) : null);
                    }
                    String str = (String) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.TestTag);
                    if (str != null) {
                        SemanticsNode parent2 = semanticsNode;
                        while (true) {
                            if (parent2 == null) {
                                zBooleanValue = false;
                                break;
                            }
                            SemanticsPropertiesAndroid.INSTANCE.getClass();
                            SemanticsPropertyKey semanticsPropertyKey3 = SemanticsPropertiesAndroid.TestTagsAsResourceId;
                            SemanticsConfiguration semanticsConfiguration3 = parent2.unmergedConfig;
                            if (semanticsConfiguration3.props.containsKey(semanticsPropertyKey3)) {
                                zBooleanValue = ((Boolean) semanticsConfiguration3.get(semanticsPropertyKey3)).booleanValue();
                                break;
                            }
                            parent2 = parent2.getParent();
                        }
                        if (zBooleanValue) {
                            accessibilityNodeInfoCompatObtain2.mInfo.setViewIdResourceName(str);
                        }
                    }
                    SemanticsProperties.INSTANCE.getClass();
                    if (((Unit) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Heading)) != null) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setHeading(true);
                        Unit unit4 = Unit.INSTANCE;
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setPassword(semanticsConfiguration2.props.containsKey(SemanticsProperties.Password));
                    accessibilityNodeInfoCompatObtain2.mInfo.setEditable(semanticsConfiguration2.props.containsKey(SemanticsProperties.IsEditable));
                    Integer num = (Integer) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.MaxTextLength);
                    accessibilityNodeInfoCompatObtain2.mInfo.setMaxTextLength(num != null ? num.intValue() : -1);
                    accessibilityNodeInfoCompatObtain2.mInfo.setEnabled(AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode));
                    SemanticsPropertyKey semanticsPropertyKey4 = SemanticsProperties.Focused;
                    accessibilityNodeInfoCompatObtain2.mInfo.setFocusable(semanticsConfiguration2.props.containsKey(semanticsPropertyKey4));
                    if (accessibilityNodeInfoCompatObtain2.mInfo.isFocusable()) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setFocused(((Boolean) semanticsConfiguration2.get(semanticsPropertyKey4)).booleanValue());
                        if (accessibilityNodeInfoCompatObtain2.mInfo.isFocused()) {
                            accessibilityNodeInfoCompatObtain2.addAction(2);
                            androidComposeViewAccessibilityDelegateCompat.focusedVirtualViewId = i;
                        } else {
                            accessibilityNodeInfoCompatObtain2.addAction(1);
                        }
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setVisibleToUser(!SemanticsUtils_androidKt.isHidden(semanticsNode));
                    LiveRegionMode liveRegionMode = (LiveRegionMode) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.LiveRegion);
                    if (liveRegionMode != null) {
                        LiveRegionMode.Companion.getClass();
                        int i9 = liveRegionMode.value;
                        if (!(i9 == 0)) {
                            int i10 = i9 == LiveRegionMode.Assertive ? 2 : 1;
                            accessibilityNodeInfoCompatObtain2.mInfo.setLiveRegion(i10);
                            Unit unit5 = Unit.INSTANCE;
                        }
                    }
                    accessibilityNodeInfoCompatObtain2.setClickable(false);
                    SemanticsActions.INSTANCE.getClass();
                    AccessibilityAction accessibilityAction2 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.OnClick);
                    if (accessibilityAction2 != null) {
                        boolean zAreEqual = Intrinsics.areEqual(SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Selected), Boolean.TRUE);
                        Role.Companion.getClass();
                        if (!(role != null && role.value == Role.Tab)) {
                            boolean z2 = role != null && role.value == Role.RadioButton;
                            accessibilityNodeInfoCompatObtain2.setClickable(!z2 || (z2 && !zAreEqual));
                            if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode) && accessibilityNodeInfoCompatObtain2.mInfo.isClickable()) {
                                accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, accessibilityAction2.label));
                            }
                            Unit unit6 = Unit.INSTANCE;
                        }
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setLongClickable(false);
                    AccessibilityAction accessibilityAction3 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.OnLongClick);
                    if (accessibilityAction3 != null) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setLongClickable(true);
                        if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(32, accessibilityAction3.label));
                        }
                        Unit unit7 = Unit.INSTANCE;
                    }
                    AccessibilityAction accessibilityAction4 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.CopyText);
                    if (accessibilityAction4 != null) {
                        accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, accessibilityAction4.label));
                        Unit unit8 = Unit.INSTANCE;
                    }
                    if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                        AccessibilityAction accessibilityAction5 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.SetText);
                        if (accessibilityAction5 != null) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(2097152, accessibilityAction5.label));
                            Unit unit9 = Unit.INSTANCE;
                        }
                        AccessibilityAction accessibilityAction6 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.OnImeAction);
                        if (accessibilityAction6 != null) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(android.R.id.accessibilityActionImeEnter, accessibilityAction6.label));
                            Unit unit10 = Unit.INSTANCE;
                        }
                        AccessibilityAction accessibilityAction7 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.CutText);
                        if (accessibilityAction7 != null) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(65536, accessibilityAction7.label));
                            Unit unit11 = Unit.INSTANCE;
                        }
                        AccessibilityAction accessibilityAction8 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.PasteText);
                        if (accessibilityAction8 != null) {
                            if (accessibilityNodeInfoCompatObtain2.mInfo.isFocused()) {
                                ClipDescription primaryClipDescription = androidComposeView.clipboardManager.clipboardManager.getPrimaryClipDescription();
                                if (primaryClipDescription != null ? primaryClipDescription.hasMimeType("text/*") : false) {
                                    accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(NetworkAnalyticsConstants.DataPoints.FLAG_UID, accessibilityAction8.label));
                                }
                            }
                            Unit unit12 = Unit.INSTANCE;
                        }
                    }
                    String iterableTextForAccessibility = AndroidComposeViewAccessibilityDelegateCompat.getIterableTextForAccessibility(semanticsNode);
                    if (!(iterableTextForAccessibility == null || iterableTextForAccessibility.length() == 0)) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setTextSelection(androidComposeViewAccessibilityDelegateCompat.getAccessibilitySelectionStart(semanticsNode), androidComposeViewAccessibilityDelegateCompat.getAccessibilitySelectionEnd(semanticsNode));
                        AccessibilityAction accessibilityAction9 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.SetSelection);
                        accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(131072, accessibilityAction9 != null ? accessibilityAction9.label : null));
                        accessibilityNodeInfoCompatObtain2.addAction(256);
                        accessibilityNodeInfoCompatObtain2.addAction(512);
                        accessibilityNodeInfoCompatObtain2.mInfo.setMovementGranularities(11);
                        List list2 = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.ContentDescription);
                        if (list2 == null || list2.isEmpty()) {
                            if (semanticsConfiguration2.props.containsKey(SemanticsActions.GetTextLayoutResult)) {
                                if (!semanticsConfiguration2.props.containsKey(SemanticsProperties.EditableText) || Intrinsics.areEqual(SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, semanticsPropertyKey4), Boolean.TRUE)) {
                                    LayoutNode layoutNodeFindClosestParentNode = AndroidComposeViewAccessibilityDelegateCompat_androidKt.findClosestParentNode(semanticsNode.layoutNode, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$excludeLineAndPageGranularities$ancestor$1
                                        /* JADX WARN: Removed duplicated region for block: B:9:0x001d  */
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object mo781invoke(Object obj) {
                                            boolean z3;
                                            SemanticsConfiguration semanticsConfiguration4 = ((LayoutNode) obj).getSemanticsConfiguration();
                                            if (semanticsConfiguration4 != null) {
                                                if (semanticsConfiguration4.isMergingSemanticsOfDescendants) {
                                                    SemanticsProperties.INSTANCE.getClass();
                                                    z3 = semanticsConfiguration4.props.containsKey(SemanticsProperties.EditableText);
                                                }
                                            }
                                            return Boolean.valueOf(z3);
                                        }
                                    });
                                    if (layoutNodeFindClosestParentNode != null) {
                                        SemanticsConfiguration semanticsConfiguration4 = layoutNodeFindClosestParentNode.getSemanticsConfiguration();
                                        z = semanticsConfiguration4 != null ? Intrinsics.areEqual(SemanticsConfigurationKt.getOrNull(semanticsConfiguration4, semanticsPropertyKey4), Boolean.TRUE) : false ? false : true;
                                        if (!z) {
                                            accessibilityNodeInfoCompatObtain2.mInfo.setMovementGranularities(accessibilityNodeInfoCompatObtain2.mInfo.getMovementGranularities() | 20);
                                        }
                                    }
                                    if (!z) {
                                    }
                                }
                            }
                        }
                    }
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("androidx.compose.ui.semantics.id");
                    CharSequence text = accessibilityNodeInfoCompatObtain2.getText();
                    if (!(text == null || text.length() == 0)) {
                        if (semanticsConfiguration2.props.containsKey(SemanticsActions.GetTextLayoutResult)) {
                            arrayList.add("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY");
                        }
                    }
                    if (semanticsConfiguration2.props.containsKey(SemanticsProperties.TestTag)) {
                        arrayList.add("androidx.compose.ui.semantics.testTag");
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setAvailableExtraData(arrayList);
                    ProgressBarRangeInfo progressBarRangeInfo = (ProgressBarRangeInfo) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.ProgressBarRangeInfo);
                    if (progressBarRangeInfo != null) {
                        SemanticsPropertyKey semanticsPropertyKey5 = SemanticsActions.SetProgress;
                        if (semanticsConfiguration2.props.containsKey(semanticsPropertyKey5)) {
                            accessibilityNodeInfoCompatObtain2.setClassName("android.widget.SeekBar");
                        } else {
                            accessibilityNodeInfoCompatObtain2.setClassName("android.widget.ProgressBar");
                        }
                        ProgressBarRangeInfo.Companion.getClass();
                        ProgressBarRangeInfo progressBarRangeInfo2 = ProgressBarRangeInfo.Indeterminate;
                        float f = progressBarRangeInfo.current;
                        ClosedFloatingPointRange closedFloatingPointRange = progressBarRangeInfo.range;
                        if (progressBarRangeInfo != progressBarRangeInfo2) {
                            ClosedFloatRange closedFloatRange = (ClosedFloatRange) closedFloatingPointRange;
                            accessibilityNodeInfoCompatObtain2.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo) new AccessibilityNodeInfoCompat.RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(1, closedFloatRange._start, closedFloatRange._endInclusive, f)).mInfo);
                        }
                        if (semanticsConfiguration2.props.containsKey(semanticsPropertyKey5) && AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                            ClosedFloatRange closedFloatRange2 = (ClosedFloatRange) closedFloatingPointRange;
                            float f2 = closedFloatRange2._endInclusive;
                            float f3 = closedFloatRange2._start;
                            if (f2 < f3) {
                                f2 = f3;
                            }
                            if (f < f2) {
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                            }
                            float f4 = closedFloatRange2._endInclusive;
                            if (f3 > f4) {
                                f3 = f4;
                            }
                            if (f > f3) {
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                            }
                        }
                    }
                    int i11 = Api24Impl.$r8$clinit;
                    if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode) && (accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.SetProgress)) != null) {
                        accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(android.R.id.accessibilityActionSetProgress, accessibilityAction.label));
                    }
                    CollectionInfo collectionInfo = (CollectionInfo) SemanticsConfigurationKt.getOrNull(semanticsNode.getConfig(), SemanticsProperties.CollectionInfo);
                    if (collectionInfo != null) {
                        accessibilityNodeInfoCompatObtain2.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(collectionInfo.rowCount, collectionInfo.columnCount, 0));
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        if (SemanticsConfigurationKt.getOrNull(semanticsNode.getConfig(), SemanticsProperties.SelectableGroup) != null) {
                            List children$ui_release$default2 = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
                            int size2 = children$ui_release$default2.size();
                            for (int i12 = 0; i12 < size2; i12++) {
                                SemanticsNode semanticsNode3 = (SemanticsNode) children$ui_release$default2.get(i12);
                                SemanticsConfiguration config = semanticsNode3.getConfig();
                                SemanticsProperties.INSTANCE.getClass();
                                if (config.props.containsKey(SemanticsProperties.Selected)) {
                                    arrayList2.add(semanticsNode3);
                                }
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            boolean zCalculateIfHorizontallyStacked = CollectionInfo_androidKt.calculateIfHorizontallyStacked(arrayList2);
                            accessibilityNodeInfoCompatObtain2.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(zCalculateIfHorizontallyStacked ? 1 : arrayList2.size(), zCalculateIfHorizontallyStacked ? arrayList2.size() : 1, 0));
                        }
                    }
                    CollectionInfo_androidKt.setCollectionItemInfo(semanticsNode, accessibilityNodeInfoCompatObtain2);
                    SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
                    semanticsProperties.getClass();
                    ScrollAxisRange scrollAxisRange = (ScrollAxisRange) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.HorizontalScrollAxisRange);
                    SemanticsActions semanticsActions = SemanticsActions.INSTANCE;
                    semanticsActions.getClass();
                    AccessibilityAction accessibilityAction10 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.ScrollBy);
                    if (scrollAxisRange != null && accessibilityAction10 != null) {
                        if (!CollectionInfo_androidKt.hasCollectionInfo(semanticsNode)) {
                            accessibilityNodeInfoCompatObtain2.setClassName("android.widget.HorizontalScrollView");
                        }
                        if (((Number) scrollAxisRange.maxValue.invoke()).floatValue() > 0.0f) {
                            accessibilityNodeInfoCompatObtain2.setScrollable(true);
                        }
                        if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                            if (AndroidComposeViewAccessibilityDelegateCompat.populateAccessibilityNodeInfoProperties$canScrollForward(scrollAxisRange)) {
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                                accessibilityNodeInfoCompatObtain2.addAction(!AndroidComposeViewAccessibilityDelegateCompat_androidKt.isRtl(semanticsNode) ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT);
                            }
                            if (AndroidComposeViewAccessibilityDelegateCompat.populateAccessibilityNodeInfoProperties$canScrollBackward(scrollAxisRange)) {
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                                accessibilityNodeInfoCompatObtain2.addAction(!AndroidComposeViewAccessibilityDelegateCompat_androidKt.isRtl(semanticsNode) ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT);
                            }
                        }
                    }
                    ScrollAxisRange scrollAxisRange2 = (ScrollAxisRange) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.VerticalScrollAxisRange);
                    if (scrollAxisRange2 != null && accessibilityAction10 != null) {
                        if (!CollectionInfo_androidKt.hasCollectionInfo(semanticsNode)) {
                            accessibilityNodeInfoCompatObtain2.setClassName("android.widget.ScrollView");
                        }
                        if (((Number) scrollAxisRange2.maxValue.invoke()).floatValue() > 0.0f) {
                            accessibilityNodeInfoCompatObtain2.setScrollable(true);
                        }
                        if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                            if (AndroidComposeViewAccessibilityDelegateCompat.populateAccessibilityNodeInfoProperties$canScrollForward(scrollAxisRange2)) {
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
                            }
                            if (AndroidComposeViewAccessibilityDelegateCompat.populateAccessibilityNodeInfoProperties$canScrollBackward(scrollAxisRange2)) {
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                                accessibilityNodeInfoCompatObtain2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
                            }
                        }
                    }
                    int i13 = Api29Impl.$r8$clinit;
                    semanticsProperties.getClass();
                    Role role2 = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Role);
                    if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                        Role.Companion.getClass();
                        int i14 = Role.Carousel;
                        if (role2 == null || role2.value != i14) {
                            semanticsActions.getClass();
                            AccessibilityAction accessibilityAction11 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.PageUp);
                            if (accessibilityAction11 != null) {
                                accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(android.R.id.accessibilityActionPageUp, accessibilityAction11.label));
                            }
                            AccessibilityAction accessibilityAction12 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.PageDown);
                            if (accessibilityAction12 != null) {
                                accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(android.R.id.accessibilityActionPageDown, accessibilityAction12.label));
                            }
                            AccessibilityAction accessibilityAction13 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.PageLeft);
                            if (accessibilityAction13 != null) {
                                accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(android.R.id.accessibilityActionPageLeft, accessibilityAction13.label));
                            }
                            AccessibilityAction accessibilityAction14 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.PageRight);
                            if (accessibilityAction14 != null) {
                                accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(android.R.id.accessibilityActionPageRight, accessibilityAction14.label));
                            }
                        }
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setPaneTitle((CharSequence) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.PaneTitle));
                    if (AndroidComposeViewAccessibilityDelegateCompat_androidKt.access$enabled(semanticsNode)) {
                        AccessibilityAction accessibilityAction15 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.Expand);
                        if (accessibilityAction15 != null) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(262144, accessibilityAction15.label));
                            Unit unit13 = Unit.INSTANCE;
                        }
                        AccessibilityAction accessibilityAction16 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.Collapse);
                        if (accessibilityAction16 != null) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME, accessibilityAction16.label));
                            Unit unit14 = Unit.INSTANCE;
                        }
                        AccessibilityAction accessibilityAction17 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsActions.Dismiss);
                        if (accessibilityAction17 != null) {
                            accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(1048576, accessibilityAction17.label));
                            Unit unit15 = Unit.INSTANCE;
                        }
                        SemanticsPropertyKey semanticsPropertyKey6 = SemanticsActions.CustomActions;
                        if (semanticsConfiguration2.props.containsKey(semanticsPropertyKey6)) {
                            List list3 = (List) semanticsConfiguration2.get(semanticsPropertyKey6);
                            int size3 = list3.size();
                            MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
                            if (size3 >= mutableIntList._size) {
                                throw new IllegalStateException(ReorderTile$$ExternalSyntheticOutline0.m(mutableIntList._size, " custom actions for one widget", new StringBuilder("Can't have more than ")));
                            }
                            SparseArrayCompat sparseArrayCompat = new SparseArrayCompat(0, i3, null);
                            MutableObjectIntMap mutableObjectIntMapMutableObjectIntMapOf = ObjectIntMapKt.mutableObjectIntMapOf();
                            SparseArrayCompat sparseArrayCompat2 = androidComposeViewAccessibilityDelegateCompat.labelToActionId;
                            if (sparseArrayCompat2.garbage) {
                                SparseArrayCompatKt.access$gc(sparseArrayCompat2);
                            }
                            if (ContainerHelpersKt.binarySearch(sparseArrayCompat2.size, i, sparseArrayCompat2.keys) >= 0) {
                                MutableObjectIntMap mutableObjectIntMap2 = (MutableObjectIntMap) sparseArrayCompat2.get(i);
                                MutableIntList mutableIntList2 = new MutableIntList(0, i3, null);
                                int[] iArr = mutableIntList.content;
                                int i15 = mutableIntList._size;
                                for (int i16 = 0; i16 < i15; i16++) {
                                    mutableIntList2.add(iArr[i16]);
                                }
                                ArrayList arrayList3 = new ArrayList();
                                int size4 = list3.size();
                                int i17 = 0;
                                while (i17 < size4) {
                                    CustomAccessibilityAction customAccessibilityAction = (CustomAccessibilityAction) list3.get(i17);
                                    mutableObjectIntMap2.getClass();
                                    int i18 = size4;
                                    if (mutableObjectIntMap2.findKeyIndex(customAccessibilityAction.label) >= 0) {
                                        String str2 = customAccessibilityAction.label;
                                        int i19 = mutableObjectIntMap2.get(str2);
                                        sparseArrayCompat.put(i19, str2);
                                        mutableObjectIntMapMutableObjectIntMapOf.set(i19, str2);
                                        i2 = i17;
                                        int[] iArr2 = mutableIntList2.content;
                                        int i20 = mutableIntList2._size;
                                        mutableObjectIntMap = mutableObjectIntMap2;
                                        int i21 = 0;
                                        while (true) {
                                            if (i21 >= i20) {
                                                i21 = -1;
                                                break;
                                            }
                                            int i22 = i20;
                                            if (i19 == iArr2[i21]) {
                                                break;
                                            }
                                            i21++;
                                            i20 = i22;
                                        }
                                        if (i21 >= 0) {
                                            mutableIntList2.removeAt(i21);
                                        }
                                        accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i19, str2));
                                    } else {
                                        i2 = i17;
                                        mutableObjectIntMap = mutableObjectIntMap2;
                                        arrayList3.add(customAccessibilityAction);
                                    }
                                    i17 = i2 + 1;
                                    size4 = i18;
                                    mutableObjectIntMap2 = mutableObjectIntMap;
                                }
                                int size5 = arrayList3.size();
                                for (int i23 = 0; i23 < size5; i23++) {
                                    CustomAccessibilityAction customAccessibilityAction2 = (CustomAccessibilityAction) arrayList3.get(i23);
                                    int i24 = mutableIntList2.get(i23);
                                    sparseArrayCompat.put(i24, customAccessibilityAction2.label);
                                    String str3 = customAccessibilityAction2.label;
                                    mutableObjectIntMapMutableObjectIntMapOf.set(i24, str3);
                                    accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i24, str3));
                                }
                            } else {
                                int size6 = list3.size();
                                for (int i25 = 0; i25 < size6; i25++) {
                                    CustomAccessibilityAction customAccessibilityAction3 = (CustomAccessibilityAction) list3.get(i25);
                                    int i26 = mutableIntList.get(i25);
                                    sparseArrayCompat.put(i26, customAccessibilityAction3.label);
                                    String str4 = customAccessibilityAction3.label;
                                    mutableObjectIntMapMutableObjectIntMapOf.set(i26, str4);
                                    accessibilityNodeInfoCompatObtain2.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i26, str4));
                                }
                            }
                            androidComposeViewAccessibilityDelegateCompat.actionIdToLabel.put(i, sparseArrayCompat);
                            sparseArrayCompat2.put(i, mutableObjectIntMapMutableObjectIntMapOf);
                        }
                    }
                    accessibilityNodeInfoCompatObtain2.mInfo.setScreenReaderFocusable(AndroidComposeViewAccessibilityDelegateCompat_androidKt.isScreenReaderFocusable(semanticsNode, resources));
                    int orDefault = androidComposeViewAccessibilityDelegateCompat.idToBeforeMap.getOrDefault(i);
                    if (orDefault != -1) {
                        AndroidViewHolder androidViewHolderSemanticsIdToView2 = SemanticsUtils_androidKt.semanticsIdToView(androidComposeView.getAndroidViewsHandler$ui_release(), orDefault);
                        if (androidViewHolderSemanticsIdToView2 != null) {
                            accessibilityNodeInfoCompatObtain2.mInfo.setTraversalBefore(androidViewHolderSemanticsIdToView2);
                        } else {
                            accessibilityNodeInfoCompatObtain2.mInfo.setTraversalBefore(androidComposeView, orDefault);
                        }
                        androidComposeViewAccessibilityDelegateCompat.addExtraDataToAccessibilityNodeInfoHelper(i, accessibilityNodeInfoCompatObtain2, androidComposeViewAccessibilityDelegateCompat.ExtraDataTestTraversalBeforeVal, null);
                    }
                    int orDefault2 = androidComposeViewAccessibilityDelegateCompat.idToAfterMap.getOrDefault(i);
                    if (orDefault2 != -1 && (androidViewHolderSemanticsIdToView = SemanticsUtils_androidKt.semanticsIdToView(androidComposeView.getAndroidViewsHandler$ui_release(), orDefault2)) != null) {
                        accessibilityNodeInfoCompatObtain2.mInfo.setTraversalAfter(androidViewHolderSemanticsIdToView);
                        androidComposeViewAccessibilityDelegateCompat.addExtraDataToAccessibilityNodeInfoHelper(i, accessibilityNodeInfoCompatObtain2, androidComposeViewAccessibilityDelegateCompat.ExtraDataTestTraversalAfterVal, null);
                    }
                    accessibilityNodeInfoCompatObtain = accessibilityNodeInfoCompatObtain2;
                } else if (!androidComposeViewAccessibilityDelegateCompat.accessibilityManager.isEnabled()) {
                    accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain();
                }
            }
            if (androidComposeViewAccessibilityDelegateCompat.sendingFocusAffectingEvent) {
                if (i == androidComposeViewAccessibilityDelegateCompat.accessibilityFocusedVirtualViewId) {
                    androidComposeViewAccessibilityDelegateCompat.currentlyAccessibilityFocusedANI = accessibilityNodeInfoCompatObtain;
                }
                if (i == androidComposeViewAccessibilityDelegateCompat.focusedVirtualViewId) {
                    androidComposeViewAccessibilityDelegateCompat.currentlyFocusedANI = accessibilityNodeInfoCompatObtain;
                }
            }
            return accessibilityNodeInfoCompatObtain;
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

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:87:0x0194
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:124)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
            */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:286:0x04d3  */
        /* JADX WARN: Removed duplicated region for block: B:319:0x0555  */
        /* JADX WARN: Removed duplicated region for block: B:403:0x06ad  */
        /* JADX WARN: Removed duplicated region for block: B:462:0x07a6  */
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
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:86:0x0193 -> B:87:0x0194). Please report as a decompilation issue!!! */
        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean performAction(int r18, int r19, android.os.Bundle r20) {
            /*
                Method dump skipped, instructions count: 2126
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.ComposeAccessibilityNodeProvider.performAction(int, int, android.os.Bundle):boolean");
        }
    }

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
                List<AccessibilityServiceInfo> enabledAccessibilityServiceList;
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.f$0;
                if (z) {
                    enabledAccessibilityServiceList = androidComposeViewAccessibilityDelegateCompat.accessibilityManager.getEnabledAccessibilityServiceList(-1);
                } else {
                    MutableIntList mutableIntList = AndroidComposeViewAccessibilityDelegateCompat.AccessibilityActionsResourceIds;
                    enabledAccessibilityServiceList = EmptyList.INSTANCE;
                }
                androidComposeViewAccessibilityDelegateCompat.enabledServices = enabledAccessibilityServiceList;
            }
        };
        this.touchExplorationStateListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$$ExternalSyntheticLambda1
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.f$0;
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
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.f$0;
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
            public final Object mo781invoke(Object obj) {
                ScrollObservationScope scrollObservationScope = (ScrollObservationScope) obj;
                AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.this$0;
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
        float fFloatValue = ((Number) function0.invoke()).floatValue();
        boolean z = scrollAxisRange.reverseScrolling;
        if (fFloatValue <= 0.0f || z) {
            return ((Number) function0.invoke()).floatValue() < ((Number) scrollAxisRange.maxValue.invoke()).floatValue() && z;
        }
        return true;
    }

    public static final boolean populateAccessibilityNodeInfoProperties$canScrollForward(ScrollAxisRange scrollAxisRange) {
        Function0 function0 = scrollAxisRange.value;
        float fFloatValue = ((Number) function0.invoke()).floatValue();
        float fFloatValue2 = ((Number) scrollAxisRange.maxValue.invoke()).floatValue();
        boolean z = scrollAxisRange.reverseScrolling;
        if (fFloatValue >= fFloatValue2 || z) {
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

    /* JADX WARN: Removed duplicated region for block: B:48:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x014a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addExtraDataToAccessibilityNodeInfoHelper(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, String str, Bundle bundle) {
        SemanticsNode semanticsNode;
        long jPositionInRoot;
        Rect rectM412translatek4lQ0M;
        Rect boundsInRoot;
        int i2;
        int i3;
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this;
        SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) androidComposeViewAccessibilityDelegateCompat.getCurrentSemanticsNodes().get(i);
        if (semanticsNodeWithAdjustedBounds == null || (semanticsNode = semanticsNodeWithAdjustedBounds.semanticsNode) == null) {
            return;
        }
        String iterableTextForAccessibility = getIterableTextForAccessibility(semanticsNode);
        if (Intrinsics.areEqual(str, androidComposeViewAccessibilityDelegateCompat.ExtraDataTestTraversalBeforeVal)) {
            int orDefault = androidComposeViewAccessibilityDelegateCompat.idToBeforeMap.getOrDefault(i);
            if (orDefault != -1) {
                accessibilityNodeInfoCompat.mInfo.getExtras().putInt(str, orDefault);
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(str, androidComposeViewAccessibilityDelegateCompat.ExtraDataTestTraversalAfterVal)) {
            int orDefault2 = androidComposeViewAccessibilityDelegateCompat.idToAfterMap.getOrDefault(i);
            if (orDefault2 != -1) {
                accessibilityNodeInfoCompat.mInfo.getExtras().putInt(str, orDefault2);
                return;
            }
            return;
        }
        SemanticsActions.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsActions.GetTextLayoutResult;
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        if (!semanticsConfiguration.props.containsKey(semanticsPropertyKey) || bundle == null || !Intrinsics.areEqual(str, "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY")) {
            SemanticsProperties.INSTANCE.getClass();
            SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.TestTag;
            if (!semanticsConfiguration.props.containsKey(semanticsPropertyKey2) || bundle == null || !Intrinsics.areEqual(str, "androidx.compose.ui.semantics.testTag")) {
                if (Intrinsics.areEqual(str, "androidx.compose.ui.semantics.id")) {
                    accessibilityNodeInfoCompat.mInfo.getExtras().putInt(str, semanticsNode.id);
                    return;
                }
                return;
            } else {
                String str2 = (String) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, semanticsPropertyKey2);
                if (str2 != null) {
                    accessibilityNodeInfoCompat.mInfo.getExtras().putCharSequence(str, str2);
                    return;
                }
                return;
            }
        }
        int i4 = bundle.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX", -1);
        int i5 = bundle.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH", -1);
        if (i5 > 0 && i4 >= 0) {
            if (i4 < (iterableTextForAccessibility != null ? iterableTextForAccessibility.length() : Integer.MAX_VALUE)) {
                TextLayoutResult textLayoutResult = SemanticsUtils_androidKt.getTextLayoutResult(semanticsConfiguration);
                if (textLayoutResult == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                int i6 = 0;
                while (i6 < i5) {
                    int i7 = i4 + i6;
                    RectF rectF = null;
                    if (i7 >= textLayoutResult.layoutInput.text.text.length()) {
                        arrayList.add(null);
                        i2 = i4;
                        i3 = i6;
                    } else {
                        Rect boundingBox = textLayoutResult.getBoundingBox(i7);
                        NodeCoordinator nodeCoordinatorFindCoordinatorToGetBounds$ui_release = semanticsNode.findCoordinatorToGetBounds$ui_release();
                        if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release == null) {
                            Offset.Companion.getClass();
                            jPositionInRoot = 0;
                            rectM412translatek4lQ0M = boundingBox.m412translatek4lQ0M(jPositionInRoot);
                            boundsInRoot = semanticsNode.getBoundsInRoot();
                            if ((!rectM412translatek4lQ0M.overlaps(boundsInRoot) ? rectM412translatek4lQ0M.intersect(boundsInRoot) : null) == null) {
                                Offset.Companion companion = Offset.Companion;
                                AndroidComposeView androidComposeView = androidComposeViewAccessibilityDelegateCompat.view;
                                long jM695localToScreenMKHz9U = androidComposeView.m695localToScreenMKHz9U((Float.floatToRawIntBits(r10.left) << 32) | (Float.floatToRawIntBits(r10.top) & 4294967295L));
                                i3 = i6;
                                i2 = i4;
                                long jM695localToScreenMKHz9U2 = androidComposeView.m695localToScreenMKHz9U((Float.floatToRawIntBits(r10.bottom) & 4294967295L) | (Float.floatToRawIntBits(r10.right) << 32));
                                rectF = new RectF(Float.intBitsToFloat((int) (jM695localToScreenMKHz9U >> 32)), Float.intBitsToFloat((int) (jM695localToScreenMKHz9U & 4294967295L)), Float.intBitsToFloat((int) (jM695localToScreenMKHz9U2 >> 32)), Float.intBitsToFloat((int) (jM695localToScreenMKHz9U2 & 4294967295L)));
                            } else {
                                i2 = i4;
                                i3 = i6;
                            }
                            arrayList.add(rectF);
                        } else {
                            if (!nodeCoordinatorFindCoordinatorToGetBounds$ui_release.getTail().isAttached) {
                                nodeCoordinatorFindCoordinatorToGetBounds$ui_release = null;
                            }
                            if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release != null) {
                                jPositionInRoot = LayoutCoordinatesKt.positionInRoot(nodeCoordinatorFindCoordinatorToGetBounds$ui_release);
                            }
                            rectM412translatek4lQ0M = boundingBox.m412translatek4lQ0M(jPositionInRoot);
                            boundsInRoot = semanticsNode.getBoundsInRoot();
                            if (!rectM412translatek4lQ0M.overlaps(boundsInRoot)) {
                            }
                            if ((!rectM412translatek4lQ0M.overlaps(boundsInRoot) ? rectM412translatek4lQ0M.intersect(boundsInRoot) : null) == null) {
                            }
                            arrayList.add(rectF);
                        }
                    }
                    i6 = i3 + 1;
                    androidComposeViewAccessibilityDelegateCompat = this;
                    i4 = i2;
                }
                accessibilityNodeInfoCompat.mInfo.getExtras().putParcelableArray(str, (Parcelable[]) arrayList.toArray(new RectF[0]));
                return;
            }
        }
        Log.e("AccessibilityDelegate", "Invalid arguments for accessibility character locations");
    }

    public final android.graphics.Rect boundsInScreen(SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds) {
        android.graphics.Rect rect = semanticsNodeWithAdjustedBounds.adjustedBounds;
        float f = rect.left;
        float f2 = rect.top;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Offset.Companion companion = Offset.Companion;
        AndroidComposeView androidComposeView = this.view;
        long jM695localToScreenMKHz9U = androidComposeView.m695localToScreenMKHz9U(jFloatToRawIntBits);
        float f3 = rect.right;
        float f4 = rect.bottom;
        long jM695localToScreenMKHz9U2 = androidComposeView.m695localToScreenMKHz9U((Float.floatToRawIntBits(f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L));
        return new android.graphics.Rect((int) Math.floor(Float.intBitsToFloat((int) (jM695localToScreenMKHz9U >> 32))), (int) Math.floor(Float.intBitsToFloat((int) (jM695localToScreenMKHz9U & 4294967295L))), (int) Math.ceil(Float.intBitsToFloat((int) (jM695localToScreenMKHz9U2 >> 32))), (int) Math.ceil(Float.intBitsToFloat((int) (jM695localToScreenMKHz9U2 & 4294967295L))));
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x010c, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r6, r2) == r3) goto L47;
     */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x0042: MOVE (r1 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]) (LINE:67), block:B:17:0x0042 */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0092 A[Catch: all -> 0x00b1, TRY_LEAVE, TryCatch #0 {all -> 0x00b1, blocks: (B:25:0x0075, B:29:0x008a, B:31:0x0092, B:34:0x009d, B:36:0x00a2, B:39:0x00b3, B:41:0x00bc, B:42:0x00d8, B:44:0x00e7, B:45:0x00f0, B:24:0x0065), top: B:53:0x0065 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x010f -> B:25:0x0075). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object boundsUpdatesEventLoop$ui_release(ContinuationImpl continuationImpl) throws Throwable {
        AndroidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1 androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1;
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat;
        MutableIntSet mutableIntSet;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
        MutableIntSet mutableIntSet2;
        Object objHasNext;
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat2 = this;
        if (continuationImpl instanceof AndroidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1) {
            androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1 = (AndroidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1) continuationImpl;
            int i = androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.label = i - Integer.MIN_VALUE;
            } else {
                androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1 = new AndroidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1(androidComposeViewAccessibilityDelegateCompat2, continuationImpl);
            }
        }
        Object obj = androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.label;
        int i3 = 1;
        int i4 = 0;
        if (i2 != 0) {
            try {
                if (i2 == 1) {
                    BufferedChannel.BufferedChannelIterator bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$2;
                    mutableIntSet = (MutableIntSet) androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$1;
                    AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat3 = (AndroidComposeViewAccessibilityDelegateCompat) androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    bufferedChannelIterator = bufferedChannelIterator2;
                    androidComposeViewAccessibilityDelegateCompat2 = androidComposeViewAccessibilityDelegateCompat3;
                    if (((Boolean) obj).booleanValue()) {
                    }
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    BufferedChannel.BufferedChannelIterator bufferedChannelIterator3 = (BufferedChannel.BufferedChannelIterator) androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$2;
                    mutableIntSet = (MutableIntSet) androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$1;
                    AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat4 = (AndroidComposeViewAccessibilityDelegateCompat) androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    bufferedChannelIterator = bufferedChannelIterator3;
                    androidComposeViewAccessibilityDelegateCompat2 = androidComposeViewAccessibilityDelegateCompat4;
                    char c = 2;
                    mutableIntSet2 = mutableIntSet;
                    i4 = 0;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$0 = androidComposeViewAccessibilityDelegateCompat2;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$1 = mutableIntSet2;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$2 = bufferedChannelIterator;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.label = 1;
                    objHasNext = bufferedChannelIterator.hasNext(androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1);
                    if (objHasNext != coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    mutableIntSet = mutableIntSet2;
                    obj = objHasNext;
                    if (((Boolean) obj).booleanValue()) {
                        androidComposeViewAccessibilityDelegateCompat2.subtreeChangedLayoutNodes.clear();
                        return Unit.INSTANCE;
                    }
                    bufferedChannelIterator.next();
                    boolean zIsEnabled$ui_release = androidComposeViewAccessibilityDelegateCompat2.isEnabled$ui_release();
                    ArraySet arraySet = androidComposeViewAccessibilityDelegateCompat2.subtreeChangedLayoutNodes;
                    if (zIsEnabled$ui_release) {
                        int i5 = arraySet._size;
                        for (int i6 = i4; i6 < i5; i6++) {
                            LayoutNode layoutNode = (LayoutNode) arraySet.array[i6];
                            androidComposeViewAccessibilityDelegateCompat2.sendSubtreeChangeAccessibilityEvents(layoutNode, mutableIntSet);
                            androidComposeViewAccessibilityDelegateCompat2.sendTypeViewScrolledAccessibilityEvent(layoutNode);
                        }
                        mutableIntSet._size = 0;
                        long[] jArr = mutableIntSet.metadata;
                        if (jArr != ScatterMapKt.EmptyGroup) {
                            Arrays.fill(jArr, 0, jArr.length, -9187201950435737472L);
                            long[] jArr2 = mutableIntSet.metadata;
                            int i7 = mutableIntSet._capacity;
                            int i8 = i7 >> 3;
                            long j = 255 << ((i7 & 7) << 3);
                            jArr2[i8] = ((~j) & jArr2[i8]) | j;
                        }
                        mutableIntSet.growthLimit = ScatterMapKt.loadedCapacity(mutableIntSet._capacity) - mutableIntSet._size;
                        if (!androidComposeViewAccessibilityDelegateCompat2.checkingForSemanticsChanges) {
                            androidComposeViewAccessibilityDelegateCompat2.checkingForSemanticsChanges = true;
                            androidComposeViewAccessibilityDelegateCompat2.handler.post(androidComposeViewAccessibilityDelegateCompat2.semanticsChangeChecker);
                        }
                    }
                    arraySet.clear();
                    androidComposeViewAccessibilityDelegateCompat2.pendingHorizontalScrollEvents.clear();
                    androidComposeViewAccessibilityDelegateCompat2.pendingVerticalScrollEvents.clear();
                    long j2 = androidComposeViewAccessibilityDelegateCompat2.SendRecurringAccessibilityEventsIntervalMillis;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$0 = androidComposeViewAccessibilityDelegateCompat2;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$1 = mutableIntSet;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$2 = bufferedChannelIterator;
                    c = 2;
                    androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.label = 2;
                }
            } catch (Throwable th) {
                th = th;
                androidComposeViewAccessibilityDelegateCompat2 = androidComposeViewAccessibilityDelegateCompat;
                androidComposeViewAccessibilityDelegateCompat2.subtreeChangedLayoutNodes.clear();
                throw th;
            }
        } else {
            ResultKt.throwOnFailure(obj);
            try {
                mutableIntSet2 = new MutableIntSet(i4, i3, null);
                BufferedChannel bufferedChannel = androidComposeViewAccessibilityDelegateCompat2.boundsUpdateChannel;
                bufferedChannel.getClass();
                bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
                androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$0 = androidComposeViewAccessibilityDelegateCompat2;
                androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$1 = mutableIntSet2;
                androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.L$2 = bufferedChannelIterator;
                androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1.label = 1;
                objHasNext = bufferedChannelIterator.hasNext(androidComposeViewAccessibilityDelegateCompat$boundsUpdatesEventLoop$1);
                if (objHasNext != coroutineSingletons) {
                }
            } catch (Throwable th2) {
                th = th2;
                androidComposeViewAccessibilityDelegateCompat2.subtreeChangedLayoutNodes.clear();
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00d3  */
    /* renamed from: canScroll-0AR0LA0$ui_release, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m701canScroll0AR0LA0$ui_release(long j, int i, boolean z) {
        SemanticsPropertyKey semanticsPropertyKey;
        int i2;
        ScrollAxisRange scrollAxisRange;
        if (!Intrinsics.areEqual(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            return false;
        }
        IntObjectMap currentSemanticsNodes = getCurrentSemanticsNodes();
        Offset.Companion.getClass();
        if (Offset.m398equalsimpl0(j, Offset.Unspecified) || (((9223372034707292159L & j) + 36028792732385279L) & (-9223372034707292160L)) != 0) {
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
                        if (RectHelper_androidKt.toComposeRect(semanticsNodeWithAdjustedBounds.adjustedBounds).m407containsk4lQ0M(j) && (scrollAxisRange = (ScrollAxisRange) SemanticsConfigurationKt.getOrNull(semanticsNodeWithAdjustedBounds.semanticsNode.unmergedConfig, semanticsPropertyKey)) != null) {
                            boolean z3 = scrollAxisRange.reverseScrolling;
                            i2 = i4;
                            int i7 = z3 ? -i : i;
                            if (i == 0 && z3) {
                                i7 = -1;
                            }
                            Function0 function0 = scrollAxisRange.value;
                            if (i7 < 0) {
                                if (((Number) function0.invoke()).floatValue() > 0.0f) {
                                    z2 = true;
                                }
                            } else if (((Number) function0.invoke()).floatValue() < ((Number) scrollAxisRange.maxValue.invoke()).floatValue()) {
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
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
        accessibilityEventObtain.setEnabled(true);
        accessibilityEventObtain.setClassName("android.view.View");
        AndroidComposeView androidComposeView = this.view;
        accessibilityEventObtain.setPackageName(androidComposeView.getContext().getPackageName());
        accessibilityEventObtain.setSource(androidComposeView, i);
        if (isEnabled$ui_release() && (semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) getCurrentSemanticsNodes().get(i)) != null) {
            SemanticsConfiguration semanticsConfiguration = semanticsNodeWithAdjustedBounds.semanticsNode.unmergedConfig;
            SemanticsProperties.INSTANCE.getClass();
            accessibilityEventObtain.setPassword(semanticsConfiguration.props.containsKey(SemanticsProperties.Password));
        }
        return accessibilityEventObtain;
    }

    public final AccessibilityEvent createTextSelectionChangedEvent(int i, Integer num, Integer num2, Integer num3, CharSequence charSequence) {
        AccessibilityEvent accessibilityEventCreateEvent = createEvent(i, 8192);
        if (num != null) {
            accessibilityEventCreateEvent.setFromIndex(num.intValue());
        }
        if (num2 != null) {
            accessibilityEventCreateEvent.setToIndex(num2.intValue());
        }
        if (num3 != null) {
            accessibilityEventCreateEvent.setItemCount(num3.intValue());
        }
        if (charSequence != null) {
            accessibilityEventCreateEvent.getText().add(charSequence);
        }
        return accessibilityEventCreateEvent;
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
            this.boundsUpdateChannel.mo3476trySendJP2dKIU(Unit.INSTANCE);
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
            return ((Boolean) ((AndroidComposeViewAccessibilityDelegateCompat$onSendAccessibilityEvent$1) this.onSendAccessibilityEvent).mo781invoke(accessibilityEvent)).booleanValue();
        } finally {
            this.sendingFocusAffectingEvent = false;
        }
    }

    public final boolean sendEventForVirtualView(int i, int i2, Integer num, List list) {
        if (i == Integer.MIN_VALUE || !isEnabled$ui_release()) {
            return false;
        }
        AccessibilityEvent accessibilityEventCreateEvent = createEvent(i, i2);
        if (num != null) {
            accessibilityEventCreateEvent.setContentChangeTypes(num.intValue());
        }
        if (list != null) {
            accessibilityEventCreateEvent.setContentDescription(ListUtilsKt.fastJoinToString$default(list, ",", null, 62));
        }
        return sendEvent(accessibilityEventCreateEvent);
    }

    public final void sendPaneChangeEvents(int i, int i2, String str) {
        AccessibilityEvent accessibilityEventCreateEvent = createEvent(semanticsNodeIdToAccessibilityVirtualNodeId(i), 32);
        accessibilityEventCreateEvent.setContentChangeTypes(i2);
        if (str != null) {
            accessibilityEventCreateEvent.getText().add(str);
        }
        sendEvent(accessibilityEventCreateEvent);
    }

    public final void sendPendingTextTraversedAtGranularityEvent(int i) {
        PendingTextTraversedEvent pendingTextTraversedEvent = this.pendingTextTraversedEvent;
        if (pendingTextTraversedEvent != null) {
            SemanticsNode semanticsNode = pendingTextTraversedEvent.node;
            if (i != semanticsNode.id) {
                return;
            }
            if (SystemClock.uptimeMillis() - pendingTextTraversedEvent.traverseTime <= 1000) {
                AccessibilityEvent accessibilityEventCreateEvent = createEvent(semanticsNodeIdToAccessibilityVirtualNodeId(semanticsNode.id), 131072);
                accessibilityEventCreateEvent.setFromIndex(pendingTextTraversedEvent.fromIndex);
                accessibilityEventCreateEvent.setToIndex(pendingTextTraversedEvent.toIndex);
                accessibilityEventCreateEvent.setAction(pendingTextTraversedEvent.action);
                accessibilityEventCreateEvent.setMovementGranularity(pendingTextTraversedEvent.granularity);
                accessibilityEventCreateEvent.getText().add(getIterableTextForAccessibility(semanticsNode));
                sendEvent(accessibilityEventCreateEvent);
            }
        }
        this.pendingTextTraversedEvent = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:189:0x050c  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0619  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendSemanticsPropertyChangeEvents(IntObjectMap intObjectMap) {
        ArrayList arrayList;
        int[] iArr;
        long[] jArr;
        int i;
        int i2;
        int i3;
        int i4;
        ArrayList arrayList2;
        int[] iArr2;
        long[] jArr2;
        int i5;
        char c;
        int i6;
        SemanticsNode semanticsNode;
        boolean z;
        boolean z2;
        Object[] objArr;
        long[] jArr3;
        int i7;
        int i8;
        Object[] objArr2;
        ArrayList arrayList3;
        SemanticsNode semanticsNode2;
        long[] jArr4;
        int i9;
        int i10;
        int i11;
        ScrollObservationScope scrollObservationScope;
        boolean z3;
        boolean z4;
        SemanticsConfiguration semanticsConfiguration;
        boolean zAreEqual;
        boolean z5;
        boolean z6;
        int i12;
        String str;
        int i13;
        int i14;
        int i15;
        boolean z7;
        AccessibilityEvent accessibilityEventCreateTextSelectionChangedEvent;
        Object obj;
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this;
        IntObjectMap intObjectMap2 = intObjectMap;
        ArrayList arrayList4 = new ArrayList(androidComposeViewAccessibilityDelegateCompat.scrollObservationScopes);
        ((ArrayList) androidComposeViewAccessibilityDelegateCompat.scrollObservationScopes).clear();
        int[] iArr3 = intObjectMap2.keys;
        long[] jArr5 = intObjectMap2.metadata;
        int i16 = 2;
        int length = jArr5.length - 2;
        if (length < 0) {
            return;
        }
        int i17 = 0;
        while (true) {
            long j = jArr5[i17];
            char c2 = 7;
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i18 = 8;
                int i19 = 8 - ((~(i17 - length)) >>> 31);
                long j2 = j;
                int i20 = 0;
                while (i20 < i19) {
                    if ((j2 & 255) < 128) {
                        int i21 = iArr3[(i17 << 3) + i20];
                        SemanticsNodeCopy semanticsNodeCopy = (SemanticsNodeCopy) androidComposeViewAccessibilityDelegateCompat.previousSemanticsNodes.get(i21);
                        if (semanticsNodeCopy == null) {
                            i2 = i20;
                            i3 = i19;
                            i4 = i18;
                            arrayList2 = arrayList4;
                            iArr2 = iArr3;
                            jArr2 = jArr5;
                            i5 = i16;
                            c = c2;
                        } else {
                            i5 = i16;
                            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) intObjectMap2.get(i21);
                            SemanticsNode semanticsNode3 = semanticsNodeWithAdjustedBounds != null ? semanticsNodeWithAdjustedBounds.semanticsNode : null;
                            if (semanticsNode3 == null) {
                                throw AndroidAutofill$$ExternalSyntheticOutline0.m("no value for specified key");
                            }
                            c = c2;
                            SemanticsConfiguration semanticsConfiguration2 = semanticsNode3.unmergedConfig;
                            MutableScatterMap mutableScatterMap = semanticsConfiguration2.props;
                            Object[] objArr3 = mutableScatterMap.keys;
                            int i22 = i18;
                            Object[] objArr4 = mutableScatterMap.values;
                            long[] jArr6 = mutableScatterMap.metadata;
                            i2 = i20;
                            int length2 = jArr6.length - 2;
                            SemanticsConfiguration semanticsConfiguration3 = semanticsNodeCopy.unmergedConfig;
                            if (length2 >= 0) {
                                int i23 = i19;
                                int i24 = 0;
                                boolean z8 = false;
                                while (true) {
                                    long j3 = jArr6[i24];
                                    iArr2 = iArr3;
                                    jArr2 = jArr5;
                                    if ((((~j3) << c) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i25 = 8 - ((~(i24 - length2)) >>> 31);
                                        long j4 = j3;
                                        int i26 = 0;
                                        boolean z9 = z8;
                                        while (i26 < i25) {
                                            if ((j4 & 255) < 128) {
                                                int i27 = (i24 << 3) + i26;
                                                Object obj2 = objArr3[i27];
                                                Object obj3 = objArr4[i27];
                                                int i28 = length2;
                                                SemanticsPropertyKey semanticsPropertyKey = (SemanticsPropertyKey) obj2;
                                                SemanticsProperties.INSTANCE.getClass();
                                                i8 = i26;
                                                SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.HorizontalScrollAxisRange;
                                                objArr2 = objArr3;
                                                if (Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey2) || Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.VerticalScrollAxisRange)) {
                                                    ScrollObservationScope scrollObservationScopeFindById = SemanticsUtils_androidKt.findById(i21, arrayList4);
                                                    if (scrollObservationScopeFindById != null) {
                                                        i10 = i21;
                                                        scrollObservationScope = scrollObservationScopeFindById;
                                                        z3 = false;
                                                    } else {
                                                        int i29 = i21;
                                                        i10 = i29;
                                                        scrollObservationScope = new ScrollObservationScope(i29, androidComposeViewAccessibilityDelegateCompat.scrollObservationScopes, null, null, null, null);
                                                        z3 = true;
                                                    }
                                                    arrayList3 = arrayList4;
                                                    ((ArrayList) androidComposeViewAccessibilityDelegateCompat.scrollObservationScopes).add(scrollObservationScope);
                                                } else {
                                                    i10 = i21;
                                                    arrayList3 = arrayList4;
                                                    z3 = false;
                                                }
                                                if (z3 || !Intrinsics.areEqual(obj3, SemanticsConfigurationKt.getOrNull(semanticsConfiguration3, semanticsPropertyKey))) {
                                                    SemanticsPropertyKey semanticsPropertyKey3 = SemanticsProperties.PaneTitle;
                                                    if (Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey3)) {
                                                        String str2 = (String) obj3;
                                                        if (semanticsConfiguration3.props.containsKey(semanticsPropertyKey3)) {
                                                            androidComposeViewAccessibilityDelegateCompat.sendPaneChangeEvents(i10, i22, str2);
                                                        }
                                                    } else if ((Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.StateDescription) ? true : Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.ToggleableState)) || Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.ProgressBarRangeInfo)) {
                                                        sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, 64, 8);
                                                        sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, 0, 8);
                                                    } else {
                                                        SemanticsPropertyKey semanticsPropertyKey4 = SemanticsProperties.Selected;
                                                        boolean zAreEqual2 = Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey4);
                                                        LayoutNode layoutNode = semanticsNode3.layoutNode;
                                                        z4 = z9;
                                                        if (zAreEqual2) {
                                                            Role role = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Role);
                                                            Role.Companion.getClass();
                                                            if (!(role != null && role.value == Role.Tab)) {
                                                                obj = null;
                                                                sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, 64, 8);
                                                                sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, 0, 8);
                                                            } else if (Intrinsics.areEqual(SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, semanticsPropertyKey4), Boolean.TRUE)) {
                                                                AccessibilityEvent accessibilityEventCreateEvent = androidComposeViewAccessibilityDelegateCompat.createEvent(androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 4);
                                                                SemanticsNode semanticsNode4 = new SemanticsNode(semanticsNode3.outerSemanticsNode, true, layoutNode, semanticsConfiguration2);
                                                                List list = (List) SemanticsConfigurationKt.getOrNull(semanticsNode4.getConfig(), SemanticsProperties.ContentDescription);
                                                                obj = null;
                                                                String strFastJoinToString$default = list != null ? ListUtilsKt.fastJoinToString$default(list, ",", null, 62) : null;
                                                                List list2 = (List) SemanticsConfigurationKt.getOrNull(semanticsNode4.getConfig(), SemanticsProperties.Text);
                                                                String strFastJoinToString$default2 = list2 != null ? ListUtilsKt.fastJoinToString$default(list2, ",", null, 62) : null;
                                                                if (strFastJoinToString$default != null) {
                                                                    accessibilityEventCreateEvent.setContentDescription(strFastJoinToString$default);
                                                                    Unit unit = Unit.INSTANCE;
                                                                }
                                                                if (strFastJoinToString$default2 != null) {
                                                                    accessibilityEventCreateEvent.getText().add(strFastJoinToString$default2);
                                                                }
                                                                androidComposeViewAccessibilityDelegateCompat.sendEvent(accessibilityEventCreateEvent);
                                                            } else {
                                                                obj = null;
                                                                sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, 0, 8);
                                                            }
                                                        } else if (Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.ContentDescription)) {
                                                            androidComposeViewAccessibilityDelegateCompat.sendEventForVirtualView(androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, 4, (List) obj3);
                                                        } else {
                                                            String str3 = "";
                                                            if (Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.EditableText)) {
                                                                SemanticsActions.INSTANCE.getClass();
                                                                if (semanticsConfiguration2.props.containsKey(SemanticsActions.SetText)) {
                                                                    AnnotatedString textForTextField = getTextForTextField(semanticsConfiguration3);
                                                                    if (textForTextField == null) {
                                                                        textForTextField = "";
                                                                    }
                                                                    CharSequence textForTextField2 = getTextForTextField(semanticsConfiguration2);
                                                                    if (textForTextField2 == null) {
                                                                        textForTextField2 = "";
                                                                    }
                                                                    CharSequence charSequenceTrimToSize = trimToSize(textForTextField2);
                                                                    int length3 = textForTextField.length();
                                                                    int length4 = textForTextField2.length();
                                                                    int i30 = length3 > length4 ? length4 : length3;
                                                                    int i31 = 0;
                                                                    while (true) {
                                                                        i13 = length4;
                                                                        if (i31 >= i30) {
                                                                            i14 = i30;
                                                                            break;
                                                                        }
                                                                        i14 = i30;
                                                                        if (textForTextField.charAt(i31) != textForTextField2.charAt(i31)) {
                                                                            break;
                                                                        }
                                                                        i31++;
                                                                        length4 = i13;
                                                                        i30 = i14;
                                                                    }
                                                                    int i32 = 0;
                                                                    while (true) {
                                                                        if (i32 >= i14 - i31) {
                                                                            i15 = i32;
                                                                            break;
                                                                        }
                                                                        i15 = i32;
                                                                        if (textForTextField.charAt((length3 - 1) - i32) != textForTextField2.charAt((i13 - 1) - i15)) {
                                                                            break;
                                                                        } else {
                                                                            i32 = i15 + 1;
                                                                        }
                                                                    }
                                                                    int i33 = (length3 - i15) - i31;
                                                                    int i34 = (i13 - i15) - i31;
                                                                    SemanticsProperties.INSTANCE.getClass();
                                                                    SemanticsPropertyKey semanticsPropertyKey5 = SemanticsProperties.Password;
                                                                    jArr4 = jArr6;
                                                                    boolean zContainsKey = semanticsConfiguration3.props.containsKey(semanticsPropertyKey5);
                                                                    boolean zContainsKey2 = semanticsConfiguration2.props.containsKey(semanticsPropertyKey5);
                                                                    boolean zContainsKey3 = semanticsConfiguration3.props.containsKey(SemanticsProperties.EditableText);
                                                                    boolean z10 = zContainsKey3 && !zContainsKey && zContainsKey2;
                                                                    boolean z11 = zContainsKey3 && zContainsKey && !zContainsKey2;
                                                                    if (z10 || z11) {
                                                                        semanticsConfiguration = semanticsConfiguration3;
                                                                        z7 = z11;
                                                                        accessibilityEventCreateTextSelectionChangedEvent = androidComposeViewAccessibilityDelegateCompat.createTextSelectionChangedEvent(androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 0, 0, Integer.valueOf(i13), charSequenceTrimToSize);
                                                                    } else {
                                                                        semanticsConfiguration = semanticsConfiguration3;
                                                                        z7 = z11;
                                                                        accessibilityEventCreateTextSelectionChangedEvent = androidComposeViewAccessibilityDelegateCompat.createEvent(androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 16);
                                                                        accessibilityEventCreateTextSelectionChangedEvent.setFromIndex(i31);
                                                                        accessibilityEventCreateTextSelectionChangedEvent.setRemovedCount(i33);
                                                                        accessibilityEventCreateTextSelectionChangedEvent.setAddedCount(i34);
                                                                        accessibilityEventCreateTextSelectionChangedEvent.setBeforeText(textForTextField);
                                                                        accessibilityEventCreateTextSelectionChangedEvent.getText().add(charSequenceTrimToSize);
                                                                    }
                                                                    accessibilityEventCreateTextSelectionChangedEvent.setClassName("android.widget.EditText");
                                                                    androidComposeViewAccessibilityDelegateCompat.sendEvent(accessibilityEventCreateTextSelectionChangedEvent);
                                                                    if (z10 || z7) {
                                                                        long j5 = ((TextRange) semanticsConfiguration2.get(SemanticsProperties.TextSelectionRange)).packedValue;
                                                                        accessibilityEventCreateTextSelectionChangedEvent.setFromIndex((int) (j5 >> 32));
                                                                        accessibilityEventCreateTextSelectionChangedEvent.setToIndex((int) (j5 & 4294967295L));
                                                                        androidComposeViewAccessibilityDelegateCompat.sendEvent(accessibilityEventCreateTextSelectionChangedEvent);
                                                                    }
                                                                    semanticsNode2 = semanticsNode3;
                                                                    i9 = i23;
                                                                } else {
                                                                    jArr4 = jArr6;
                                                                    sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10), 2048, Integer.valueOf(i5), 8);
                                                                    semanticsNode2 = semanticsNode3;
                                                                    i9 = i23;
                                                                    i11 = i28;
                                                                    z6 = z4;
                                                                    z9 = z6;
                                                                }
                                                            } else {
                                                                semanticsConfiguration = semanticsConfiguration3;
                                                                jArr4 = jArr6;
                                                                i9 = i23;
                                                                SemanticsPropertyKey semanticsPropertyKey6 = SemanticsProperties.TextSelectionRange;
                                                                boolean zAreEqual3 = Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey6);
                                                                int i35 = semanticsNode3.id;
                                                                if (zAreEqual3) {
                                                                    AnnotatedString textForTextField3 = getTextForTextField(semanticsConfiguration2);
                                                                    if (textForTextField3 != null && (str = textForTextField3.text) != null) {
                                                                        str3 = str;
                                                                    }
                                                                    TextRange textRange = (TextRange) semanticsConfiguration2.get(semanticsPropertyKey6);
                                                                    int iSemanticsNodeIdToAccessibilityVirtualNodeId = androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i10);
                                                                    long j6 = textRange.packedValue;
                                                                    androidComposeViewAccessibilityDelegateCompat = this;
                                                                    androidComposeViewAccessibilityDelegateCompat.sendEvent(androidComposeViewAccessibilityDelegateCompat.createTextSelectionChangedEvent(iSemanticsNodeIdToAccessibilityVirtualNodeId, Integer.valueOf((int) (j6 >> 32)), Integer.valueOf((int) (j6 & 4294967295L)), Integer.valueOf(str3.length()), trimToSize(str3)));
                                                                    androidComposeViewAccessibilityDelegateCompat.sendPendingTextTraversedAtGranularityEvent(i35);
                                                                    semanticsNode2 = semanticsNode3;
                                                                } else {
                                                                    i11 = i28;
                                                                    semanticsConfiguration3 = semanticsConfiguration;
                                                                    if (Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey2)) {
                                                                        semanticsNode2 = semanticsNode3;
                                                                        zAreEqual = true;
                                                                    } else {
                                                                        semanticsNode2 = semanticsNode3;
                                                                        zAreEqual = Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.VerticalScrollAxisRange);
                                                                    }
                                                                    if (zAreEqual) {
                                                                        androidComposeViewAccessibilityDelegateCompat.notifySubtreeAccessibilityStateChangedIfNeeded(layoutNode);
                                                                        ScrollObservationScope scrollObservationScopeFindById2 = SemanticsUtils_androidKt.findById(i10, androidComposeViewAccessibilityDelegateCompat.scrollObservationScopes);
                                                                        scrollObservationScopeFindById2.getClass();
                                                                        scrollObservationScopeFindById2.horizontalScrollAxisRange = (ScrollAxisRange) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, semanticsPropertyKey2);
                                                                        scrollObservationScopeFindById2.verticalScrollAxisRange = (ScrollAxisRange) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.VerticalScrollAxisRange);
                                                                        if (scrollObservationScopeFindById2.allScopes.contains(scrollObservationScopeFindById2)) {
                                                                            androidComposeViewAccessibilityDelegateCompat.view.snapshotObserver.observeReads$ui_release(scrollObservationScopeFindById2, androidComposeViewAccessibilityDelegateCompat.scheduleScrollEventIfNeededLambda, new AndroidComposeViewAccessibilityDelegateCompat$scheduleScrollEventIfNeeded$1(scrollObservationScopeFindById2, androidComposeViewAccessibilityDelegateCompat));
                                                                        }
                                                                    } else if (Intrinsics.areEqual(semanticsPropertyKey, SemanticsProperties.Focused)) {
                                                                        if (((Boolean) obj3).booleanValue()) {
                                                                            i12 = 8;
                                                                            androidComposeViewAccessibilityDelegateCompat.sendEvent(androidComposeViewAccessibilityDelegateCompat.createEvent(androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i35), 8));
                                                                        } else {
                                                                            i12 = 8;
                                                                        }
                                                                        sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i35), 2048, 0, i12);
                                                                    } else {
                                                                        SemanticsActions.INSTANCE.getClass();
                                                                        SemanticsPropertyKey semanticsPropertyKey7 = SemanticsActions.CustomActions;
                                                                        if (Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey7)) {
                                                                            List list3 = (List) semanticsConfiguration2.get(semanticsPropertyKey7);
                                                                            List list4 = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration3, semanticsPropertyKey7);
                                                                            if (list4 != null) {
                                                                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                                                                int size = list3.size();
                                                                                for (int i36 = 0; i36 < size; i36++) {
                                                                                    linkedHashSet.add(((CustomAccessibilityAction) list3.get(i36)).label);
                                                                                }
                                                                                LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                                                                                int size2 = list4.size();
                                                                                for (int i37 = 0; i37 < size2; i37++) {
                                                                                    linkedHashSet2.add(((CustomAccessibilityAction) list4.get(i37)).label);
                                                                                }
                                                                                if (linkedHashSet.containsAll(linkedHashSet2) && linkedHashSet2.containsAll(linkedHashSet)) {
                                                                                    z6 = false;
                                                                                }
                                                                            } else if (!list3.isEmpty()) {
                                                                            }
                                                                            z6 = true;
                                                                        } else {
                                                                            if (obj3 instanceof AccessibilityAction) {
                                                                                AccessibilityAction accessibilityAction = (AccessibilityAction) obj3;
                                                                                Object orNull = SemanticsConfigurationKt.getOrNull(semanticsConfiguration3, semanticsPropertyKey);
                                                                                Comparator[] comparatorArr = AndroidComposeViewAccessibilityDelegateCompat_androidKt.semanticComparators;
                                                                                if (accessibilityAction != orNull) {
                                                                                    if (orNull instanceof AccessibilityAction) {
                                                                                        AccessibilityAction accessibilityAction2 = (AccessibilityAction) orNull;
                                                                                        if (Intrinsics.areEqual(accessibilityAction.label, accessibilityAction2.label)) {
                                                                                            Function function = accessibilityAction2.action;
                                                                                            Function function2 = accessibilityAction.action;
                                                                                            z5 = (function2 != null || function == null) && (function2 == null || function != null);
                                                                                            if (z5) {
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    if (z5) {
                                                                                    }
                                                                                }
                                                                            }
                                                                            z6 = true;
                                                                        }
                                                                        z9 = z6;
                                                                    }
                                                                    z6 = z4;
                                                                    z9 = z6;
                                                                }
                                                            }
                                                            i11 = i28;
                                                            semanticsConfiguration3 = semanticsConfiguration;
                                                            z6 = z4;
                                                            z9 = z6;
                                                        }
                                                        semanticsNode2 = semanticsNode3;
                                                        jArr4 = jArr6;
                                                        i9 = i23;
                                                        i11 = i28;
                                                        z6 = z4;
                                                        z9 = z6;
                                                    }
                                                    z4 = z9;
                                                    semanticsNode2 = semanticsNode3;
                                                    jArr4 = jArr6;
                                                    i9 = i23;
                                                    i11 = i28;
                                                    z6 = z4;
                                                    z9 = z6;
                                                } else {
                                                    semanticsNode2 = semanticsNode3;
                                                    jArr4 = jArr6;
                                                    i9 = i23;
                                                    i11 = i28;
                                                }
                                            } else {
                                                i8 = i26;
                                                objArr2 = objArr3;
                                                arrayList3 = arrayList4;
                                                semanticsNode2 = semanticsNode3;
                                                jArr4 = jArr6;
                                                i9 = i23;
                                                i10 = i21;
                                                i11 = length2;
                                            }
                                            j4 >>= 8;
                                            i22 = 8;
                                            i23 = i9;
                                            arrayList4 = arrayList3;
                                            semanticsNode3 = semanticsNode2;
                                            jArr6 = jArr4;
                                            i26 = i8 + 1;
                                            length2 = i11;
                                            i21 = i10;
                                            objArr3 = objArr2;
                                        }
                                        objArr = objArr3;
                                        arrayList2 = arrayList4;
                                        boolean z12 = z9;
                                        semanticsNode = semanticsNode3;
                                        jArr3 = jArr6;
                                        i3 = i23;
                                        i6 = i21;
                                        i7 = length2;
                                        if (i25 != i22) {
                                            z = z12;
                                            break;
                                        }
                                        z8 = z12;
                                    } else {
                                        objArr = objArr3;
                                        arrayList2 = arrayList4;
                                        semanticsNode = semanticsNode3;
                                        jArr3 = jArr6;
                                        i3 = i23;
                                        i6 = i21;
                                        i7 = length2;
                                    }
                                    if (i24 == i7) {
                                        z = z8;
                                        break;
                                    }
                                    i24++;
                                    length2 = i7;
                                    i21 = i6;
                                    i23 = i3;
                                    arrayList4 = arrayList2;
                                    semanticsNode3 = semanticsNode;
                                    iArr3 = iArr2;
                                    jArr5 = jArr2;
                                    objArr3 = objArr;
                                    jArr6 = jArr3;
                                    i22 = 8;
                                }
                            } else {
                                i6 = i21;
                                i3 = i19;
                                arrayList2 = arrayList4;
                                iArr2 = iArr3;
                                jArr2 = jArr5;
                                semanticsNode = semanticsNode3;
                                z = false;
                            }
                            if (!z) {
                                Comparator[] comparatorArr2 = AndroidComposeViewAccessibilityDelegateCompat_androidKt.semanticComparators;
                                Iterator<Map.Entry<? extends SemanticsPropertyKey<?>, ? extends Object>> it = semanticsConfiguration3.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        z2 = false;
                                        break;
                                    }
                                    if (!semanticsNode.getConfig().props.containsKey(it.next().getKey())) {
                                        z2 = true;
                                        break;
                                    }
                                }
                                z = z2;
                            }
                            if (z) {
                                i4 = 8;
                                sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(i6), 2048, 0, 8);
                            } else {
                                i4 = 8;
                            }
                        }
                    }
                    j2 >>= i4;
                    i20 = i2 + 1;
                    intObjectMap2 = intObjectMap;
                    i18 = i4;
                    i19 = i3;
                    i16 = i5;
                    arrayList4 = arrayList2;
                    c2 = c;
                    iArr3 = iArr2;
                    jArr5 = jArr2;
                }
                arrayList = arrayList4;
                iArr = iArr3;
                jArr = jArr5;
                i = i16;
                if (i19 != i18) {
                    return;
                }
            } else {
                arrayList = arrayList4;
                iArr = iArr3;
                jArr = jArr5;
                i = i16;
            }
            if (i17 == length) {
                return;
            }
            i17++;
            intObjectMap2 = intObjectMap;
            i16 = i;
            arrayList4 = arrayList;
            iArr3 = iArr;
            jArr5 = jArr;
        }
    }

    public final void sendSubtreeChangeAccessibilityEvents(LayoutNode layoutNode, MutableIntSet mutableIntSet) {
        SemanticsConfiguration semanticsConfiguration;
        LayoutNode layoutNodeFindClosestParentNode;
        if (layoutNode.isAttached() && !this.view.getAndroidViewsHandler$ui_release().layoutNodeToHolder.containsKey(layoutNode)) {
            if (!layoutNode.nodes.m665hasH91voCI$ui_release(8)) {
                layoutNode = AndroidComposeViewAccessibilityDelegateCompat_androidKt.findClosestParentNode(layoutNode, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat$sendSubtreeChangeAccessibilityEvents$semanticsNode$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        return Boolean.valueOf(((LayoutNode) obj).nodes.m665hasH91voCI$ui_release(8));
                    }
                });
            }
            if (layoutNode == null || (semanticsConfiguration = layoutNode.getSemanticsConfiguration()) == null) {
                return;
            }
            if (!semanticsConfiguration.isMergingSemanticsOfDescendants && (layoutNodeFindClosestParentNode = AndroidComposeViewAccessibilityDelegateCompat_androidKt.findClosestParentNode(layoutNode, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat.sendSubtreeChangeAccessibilityEvents.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    SemanticsConfiguration semanticsConfiguration2 = ((LayoutNode) obj).getSemanticsConfiguration();
                    boolean z = false;
                    if (semanticsConfiguration2 != null && semanticsConfiguration2.isMergingSemanticsOfDescendants) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            })) != null) {
                layoutNode = layoutNodeFindClosestParentNode;
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
            AccessibilityEvent accessibilityEventCreateEvent = createEvent(i, 4096);
            if (scrollAxisRange != null) {
                accessibilityEventCreateEvent.setScrollX((int) ((Number) scrollAxisRange.value.invoke()).floatValue());
                accessibilityEventCreateEvent.setMaxScrollX((int) ((Number) scrollAxisRange.maxValue.invoke()).floatValue());
            }
            if (scrollAxisRange2 != null) {
                accessibilityEventCreateEvent.setScrollY((int) ((Number) scrollAxisRange2.value.invoke()).floatValue());
                accessibilityEventCreateEvent.setMaxScrollY((int) ((Number) scrollAxisRange2.maxValue.invoke()).floatValue());
            }
            sendEvent(accessibilityEventCreateEvent);
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

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0154, code lost:
    
        r31 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x015e, code lost:
    
        if (((r1 & ((~r1) << 6)) & r24) == 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0160, code lost:
    
        r29 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSemanticsNodesCopyAndPanes() {
        char c;
        long j;
        long j2;
        long j3;
        long[] jArr;
        int[] iArr;
        long[] jArr2;
        int i;
        int[] iArr2;
        long j4;
        int iNumberOfTrailingZeros;
        char c2;
        long j5;
        String str;
        SemanticsConfiguration semanticsConfiguration;
        MutableIntSet mutableIntSet = new MutableIntSet(0, 1, null);
        MutableIntSet mutableIntSet2 = this.paneDisplayed;
        int[] iArr3 = mutableIntSet2.elements;
        long[] jArr3 = mutableIntSet2.metadata;
        int length = jArr3.length - 2;
        MutableIntObjectMap mutableIntObjectMap = this.previousSemanticsNodes;
        char c3 = 7;
        long j6 = -9187201950435737472L;
        int i2 = 8;
        if (length >= 0) {
            int i3 = 0;
            j2 = 128;
            while (true) {
                long j7 = jArr3[i3];
                j3 = 255;
                if ((((~j7) << c3) & j7 & j6) != j6) {
                    int i4 = 8 - ((~(i3 - length)) >>> 31);
                    int i5 = 0;
                    while (i5 < i4) {
                        if ((j7 & 255) < 128) {
                            int i6 = iArr3[(i3 << 3) + i5];
                            c2 = c3;
                            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) getCurrentSemanticsNodes().get(i6);
                            SemanticsNode semanticsNode = semanticsNodeWithAdjustedBounds != null ? semanticsNodeWithAdjustedBounds.semanticsNode : null;
                            if (semanticsNode != null) {
                                SemanticsProperties.INSTANCE.getClass();
                                j5 = j6;
                                if (!semanticsNode.unmergedConfig.props.containsKey(SemanticsProperties.PaneTitle)) {
                                }
                            } else {
                                j5 = j6;
                            }
                            mutableIntSet.add(i6);
                            SemanticsNodeCopy semanticsNodeCopy = (SemanticsNodeCopy) mutableIntObjectMap.get(i6);
                            if (semanticsNodeCopy == null || (semanticsConfiguration = semanticsNodeCopy.unmergedConfig) == null) {
                                str = null;
                            } else {
                                SemanticsProperties.INSTANCE.getClass();
                                str = (String) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.PaneTitle);
                            }
                            sendPaneChangeEvents(i6, 32, str);
                        } else {
                            c2 = c3;
                            j5 = j6;
                        }
                        j7 >>= 8;
                        i5++;
                        c3 = c2;
                        j6 = j5;
                    }
                    c = c3;
                    j = j6;
                    if (i4 != 8) {
                        break;
                    }
                } else {
                    c = c3;
                    j = j6;
                }
                if (i3 == length) {
                    break;
                }
                i3++;
                c3 = c;
                j6 = j;
            }
        } else {
            c = 7;
            j = -9187201950435737472L;
            j2 = 128;
            j3 = 255;
        }
        int[] iArr4 = mutableIntSet.elements;
        long[] jArr4 = mutableIntSet.metadata;
        int length2 = jArr4.length - 2;
        if (length2 >= 0) {
            int i7 = 0;
            while (true) {
                long j8 = jArr4[i7];
                if ((((~j8) << c) & j8 & j) != j) {
                    int i8 = 8 - ((~(i7 - length2)) >>> 31);
                    int i9 = 0;
                    while (i9 < i8) {
                        if ((j8 & j3) < j2) {
                            int i10 = iArr4[(i7 << 3) + i9];
                            int iHashCode = Integer.hashCode(i10) * (-862048943);
                            int i11 = iHashCode ^ (iHashCode << 16);
                            int i12 = i11 & 127;
                            int i13 = mutableIntSet2._capacity;
                            int i14 = (i11 >>> 7) & i13;
                            i = i2;
                            int i15 = 0;
                            while (true) {
                                long[] jArr5 = mutableIntSet2.metadata;
                                int i16 = i14 >> 3;
                                jArr2 = jArr4;
                                int i17 = (i14 & 7) << 3;
                                long j9 = (jArr5[i16] >>> i17) | ((jArr5[i16 + 1] << (64 - i17)) & ((-i17) >> 63));
                                j4 = j8;
                                long j10 = (i12 * 72340172838076673L) ^ j9;
                                long j11 = (j10 - 72340172838076673L) & (~j10) & j;
                                while (true) {
                                    if (j11 == 0) {
                                        break;
                                    }
                                    iNumberOfTrailingZeros = (i14 + (Long.numberOfTrailingZeros(j11) >> 3)) & i13;
                                    iArr2 = iArr4;
                                    if (mutableIntSet2.elements[iNumberOfTrailingZeros] == i10) {
                                        break;
                                    }
                                    j11 &= j11 - 1;
                                    iArr4 = iArr2;
                                }
                                i15 += 8;
                                i14 = (i14 + i15) & i13;
                                j8 = j4;
                                jArr4 = jArr2;
                                iArr4 = iArr2;
                            }
                            if (iNumberOfTrailingZeros >= 0) {
                                mutableIntSet2._size--;
                                long[] jArr6 = mutableIntSet2.metadata;
                                int i18 = mutableIntSet2._capacity;
                                int i19 = iNumberOfTrailingZeros >> 3;
                                int i20 = (iNumberOfTrailingZeros & 7) << 3;
                                long j12 = (254 << i20) | (jArr6[i19] & (~(255 << i20)));
                                jArr6[i19] = j12;
                                jArr6[(((iNumberOfTrailingZeros - 7) & i18) + (i18 & 7)) >> 3] = j12;
                            }
                        } else {
                            jArr2 = jArr4;
                            i = i2;
                            iArr2 = iArr4;
                            j4 = j8;
                        }
                        j8 = j4 >> i;
                        i9++;
                        i2 = i;
                        jArr4 = jArr2;
                        iArr4 = iArr2;
                    }
                    jArr = jArr4;
                    iArr = iArr4;
                    if (i8 != i2) {
                        break;
                    }
                } else {
                    jArr = jArr4;
                    iArr = iArr4;
                }
                if (i7 == length2) {
                    break;
                }
                i7++;
                jArr4 = jArr;
                iArr4 = iArr;
                i2 = 8;
            }
        }
        mutableIntObjectMap.clear();
        IntObjectMap currentSemanticsNodes = getCurrentSemanticsNodes();
        int[] iArr5 = currentSemanticsNodes.keys;
        Object[] objArr = currentSemanticsNodes.values;
        long[] jArr7 = currentSemanticsNodes.metadata;
        int length3 = jArr7.length - 2;
        if (length3 >= 0) {
            int i21 = 0;
            while (true) {
                long j13 = jArr7[i21];
                if ((((~j13) << c) & j13 & j) != j) {
                    int i22 = 8 - ((~(i21 - length3)) >>> 31);
                    for (int i23 = 0; i23 < i22; i23++) {
                        if ((j13 & j3) < j2) {
                            int i24 = (i21 << 3) + i23;
                            int i25 = iArr5[i24];
                            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds2 = (SemanticsNodeWithAdjustedBounds) objArr[i24];
                            SemanticsConfiguration semanticsConfiguration2 = semanticsNodeWithAdjustedBounds2.semanticsNode.unmergedConfig;
                            SemanticsProperties.INSTANCE.getClass();
                            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.PaneTitle;
                            boolean zContainsKey = semanticsConfiguration2.props.containsKey(semanticsPropertyKey);
                            SemanticsNode semanticsNode2 = semanticsNodeWithAdjustedBounds2.semanticsNode;
                            if (zContainsKey && mutableIntSet2.add(i25)) {
                                sendPaneChangeEvents(i25, 16, (String) semanticsNode2.unmergedConfig.get(semanticsPropertyKey));
                            }
                            mutableIntObjectMap.set(i25, new SemanticsNodeCopy(semanticsNode2, getCurrentSemanticsNodes()));
                        }
                        j13 >>= 8;
                    }
                    if (i22 != 8) {
                        break;
                    }
                }
                if (i21 == length3) {
                    break;
                } else {
                    i21++;
                }
            }
        }
        this.previousSemanticsRoot = new SemanticsNodeCopy(this.view.semanticsOwner.getUnmergedRootSemanticsNode(), getCurrentSemanticsNodes());
    }
}
