package androidx.compose.ui.contentcapture;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;
import android.view.translation.TranslationRequestValue;
import android.view.translation.TranslationResponseValue;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
import androidx.collection.IntObjectMap;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableScatterMap;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.contentcapture.AndroidContentCaptureManager;
import androidx.compose.ui.contentcapture.ContentCaptureManager;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.SemanticsNodeCopy;
import androidx.compose.ui.platform.SemanticsNodeWithAdjustedBounds;
import androidx.compose.ui.platform.SemanticsUtils_androidKt;
import androidx.compose.ui.platform.coreshims.AutofillIdCompat;
import androidx.compose.ui.platform.coreshims.ContentCaptureSessionCompat;
import androidx.compose.ui.platform.coreshims.ViewStructureCompat;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.util.ListUtilsKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import com.samsung.android.knox.lockscreen.LSOAttrConst;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes.dex */
public final class AndroidContentCaptureManager implements ContentCaptureManager, DefaultLifecycleObserver, View.OnAttachStateChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean checkingForSemanticsChanges;
    public final AndroidContentCaptureManager$$ExternalSyntheticLambda0 contentCaptureChangeChecker;
    public ContentCaptureSessionCompat contentCaptureSession;
    public MutableIntObjectMap currentSemanticsNodes;
    public long currentSemanticsNodesSnapshotTimestampMillis;
    public final Function0 onContentCaptureSession;
    public final MutableIntObjectMap previousSemanticsNodes;
    public SemanticsNodeCopy previousSemanticsRoot;
    public final AndroidComposeView view;
    public final List bufferedEvents = new ArrayList();
    public final long SendRecurringContentCaptureEventsIntervalMillis = 100;
    public TranslateStatus translateStatus = TranslateStatus.SHOW_ORIGINAL;
    public boolean currentSemanticsNodesInvalidated = true;
    public final BufferedChannel boundsUpdateChannel = ChannelKt.Channel$default(1, null, null, 6);
    public final Handler handler = new Handler(Looper.getMainLooper());

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class TranslateStatus {
        public static final /* synthetic */ TranslateStatus[] $VALUES;
        public static final TranslateStatus SHOW_ORIGINAL;
        public static final TranslateStatus SHOW_TRANSLATED;

        static {
            TranslateStatus translateStatus = new TranslateStatus("SHOW_ORIGINAL", 0);
            SHOW_ORIGINAL = translateStatus;
            TranslateStatus translateStatus2 = new TranslateStatus("SHOW_TRANSLATED", 1);
            SHOW_TRANSLATED = translateStatus2;
            TranslateStatus[] translateStatusArr = {translateStatus, translateStatus2};
            $VALUES = translateStatusArr;
            EnumEntriesKt.enumEntries(translateStatusArr);
        }

        private TranslateStatus(String str, int i) {
        }

        public static TranslateStatus valueOf(String str) {
            return (TranslateStatus) Enum.valueOf(TranslateStatus.class, str);
        }

        public static TranslateStatus[] values() {
            return (TranslateStatus[]) $VALUES.clone();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final class ViewTranslationHelperMethods {
        public static final ViewTranslationHelperMethods INSTANCE = new ViewTranslationHelperMethods();

        private ViewTranslationHelperMethods() {
        }

        public static void doTranslation(AndroidContentCaptureManager androidContentCaptureManager, LongSparseArray longSparseArray) {
            TranslationResponseValue value;
            CharSequence text;
            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds;
            SemanticsNode semanticsNode;
            Function1 function1;
            int size = longSparseArray.size();
            for (int i = 0; i < size; i++) {
                long jKeyAt = longSparseArray.keyAt(i);
                ViewTranslationResponse viewTranslationResponse = (ViewTranslationResponse) longSparseArray.get(jKeyAt);
                if (viewTranslationResponse != null && (value = viewTranslationResponse.getValue(LSOAttrConst.ATTR_TEXT)) != null && (text = value.getText()) != null && (semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) androidContentCaptureManager.getCurrentSemanticsNodes$ui_release().get((int) jKeyAt)) != null && (semanticsNode = semanticsNodeWithAdjustedBounds.semanticsNode) != null) {
                    SemanticsActions.INSTANCE.getClass();
                    AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsNode.unmergedConfig, SemanticsActions.SetTextSubstitution);
                    if (accessibilityAction != null && (function1 = (Function1) accessibilityAction.action) != null) {
                    }
                }
            }
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ContentCaptureEventType.values().length];
            try {
                iArr[ContentCaptureEventType.VIEW_APPEAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ContentCaptureEventType.VIEW_DISAPPEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [androidx.compose.ui.contentcapture.AndroidContentCaptureManager$$ExternalSyntheticLambda0] */
    public AndroidContentCaptureManager(AndroidComposeView androidComposeView, Function0 function0) {
        this.view = androidComposeView;
        this.onContentCaptureSession = function0;
        MutableIntObjectMap mutableIntObjectMap = IntObjectMapKt.EmptyIntObjectMap;
        this.currentSemanticsNodes = mutableIntObjectMap;
        this.previousSemanticsNodes = IntObjectMapKt.mutableIntObjectMapOf();
        this.previousSemanticsRoot = new SemanticsNodeCopy(androidComposeView.semanticsOwner.getUnmergedRootSemanticsNode(), mutableIntObjectMap);
        this.contentCaptureChangeChecker = new Runnable() { // from class: androidx.compose.ui.contentcapture.AndroidContentCaptureManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                long j;
                long j2;
                long j3;
                char c;
                long j4;
                IntObjectMap intObjectMap;
                int[] iArr;
                long[] jArr;
                IntObjectMap intObjectMap2;
                int[] iArr2;
                long[] jArr2;
                int i;
                long j5;
                char c2;
                long j6;
                int i2;
                long[] jArr3;
                long[] jArr4;
                long j7;
                IntObjectMap intObjectMap3;
                long j8;
                int i3;
                boolean z;
                boolean z2;
                long j9;
                long j10;
                boolean z3 = true;
                AndroidContentCaptureManager androidContentCaptureManager = this.f$0;
                int i4 = AndroidContentCaptureManager.$r8$clinit;
                if (androidContentCaptureManager.isEnabled$ui_release()) {
                    AndroidComposeView androidComposeView2 = androidContentCaptureManager.view;
                    Owner.Companion companion = Owner.Companion;
                    androidComposeView2.measureAndLayout(true);
                    MutableIntObjectMap mutableIntObjectMap2 = androidContentCaptureManager.previousSemanticsNodes;
                    int[] iArr3 = mutableIntObjectMap2.keys;
                    long[] jArr5 = mutableIntObjectMap2.metadata;
                    int length = jArr5.length - 2;
                    int i5 = 8;
                    long j11 = -9187201950435737472L;
                    char c3 = 7;
                    if (length >= 0) {
                        int i6 = 0;
                        j2 = 128;
                        while (true) {
                            long j12 = jArr5[i6];
                            j3 = 255;
                            if ((((~j12) << 7) & j12 & j11) != j11) {
                                int i7 = 8 - ((~(i6 - length)) >>> 31);
                                int i8 = 0;
                                while (i8 < i7) {
                                    if ((j12 & 255) < 128) {
                                        z2 = z3;
                                        int i9 = iArr3[(i6 << 3) + i8];
                                        j10 = j11;
                                        if (androidContentCaptureManager.getCurrentSemanticsNodes$ui_release().containsKey(i9)) {
                                            j9 = j12;
                                        } else {
                                            j9 = j12;
                                            ((ArrayList) androidContentCaptureManager.bufferedEvents).add(new ContentCaptureEvent(i9, androidContentCaptureManager.currentSemanticsNodesSnapshotTimestampMillis, ContentCaptureEventType.VIEW_DISAPPEAR, null));
                                            androidContentCaptureManager.boundsUpdateChannel.mo3476trySendJP2dKIU(Unit.INSTANCE);
                                        }
                                    } else {
                                        z2 = z3;
                                        j9 = j12;
                                        j10 = j11;
                                    }
                                    j12 = j9 >> 8;
                                    i8++;
                                    z3 = z2;
                                    j11 = j10;
                                }
                                z = z3;
                                j = j11;
                                if (i7 != 8) {
                                    break;
                                }
                            } else {
                                z = z3;
                                j = j11;
                            }
                            if (i6 == length) {
                                break;
                            }
                            i6++;
                            z3 = z;
                            j11 = j;
                        }
                    } else {
                        j = -9187201950435737472L;
                        j2 = 128;
                        j3 = 255;
                    }
                    androidContentCaptureManager.sendContentCaptureAppearEvents(androidContentCaptureManager.view.semanticsOwner.getUnmergedRootSemanticsNode(), androidContentCaptureManager.previousSemanticsRoot);
                    IntObjectMap currentSemanticsNodes$ui_release = androidContentCaptureManager.getCurrentSemanticsNodes$ui_release();
                    int[] iArr4 = currentSemanticsNodes$ui_release.keys;
                    long[] jArr6 = currentSemanticsNodes$ui_release.metadata;
                    int length2 = jArr6.length - 2;
                    if (length2 >= 0) {
                        int i10 = 0;
                        while (true) {
                            long j13 = jArr6[i10];
                            if ((((~j13) << c3) & j13 & j) != j) {
                                int i11 = 8 - ((~(i10 - length2)) >>> 31);
                                int i12 = 0;
                                while (i12 < i11) {
                                    if ((j13 & j3) < j2) {
                                        int i13 = iArr4[(i10 << 3) + i12];
                                        SemanticsNodeCopy semanticsNodeCopy = (SemanticsNodeCopy) androidContentCaptureManager.previousSemanticsNodes.get(i13);
                                        SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) currentSemanticsNodes$ui_release.get(i13);
                                        SemanticsNode semanticsNode = semanticsNodeWithAdjustedBounds != null ? semanticsNodeWithAdjustedBounds.semanticsNode : null;
                                        if (semanticsNode == null) {
                                            throw AndroidAutofill$$ExternalSyntheticOutline0.m("no value for specified key");
                                        }
                                        c2 = c3;
                                        int i14 = semanticsNode.id;
                                        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
                                        if (semanticsNodeCopy == null) {
                                            MutableScatterMap mutableScatterMap = semanticsConfiguration.props;
                                            Object[] objArr = mutableScatterMap.keys;
                                            long[] jArr7 = mutableScatterMap.metadata;
                                            j6 = j2;
                                            int length3 = jArr7.length - 2;
                                            if (length3 >= 0) {
                                                iArr2 = iArr4;
                                                jArr2 = jArr6;
                                                int i15 = i5;
                                                int i16 = 0;
                                                while (true) {
                                                    long j14 = jArr7[i16];
                                                    j5 = j13;
                                                    if ((((~j14) << c2) & j14 & j) != j) {
                                                        int i17 = 8 - ((~(i16 - length3)) >>> 31);
                                                        int i18 = 0;
                                                        while (i18 < i17) {
                                                            if ((j14 & j3) < j6) {
                                                                intObjectMap3 = currentSemanticsNodes$ui_release;
                                                                SemanticsPropertyKey semanticsPropertyKey = (SemanticsPropertyKey) objArr[(i16 << 3) + i18];
                                                                SemanticsProperties.INSTANCE.getClass();
                                                                j8 = j14;
                                                                SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.Text;
                                                                if (Intrinsics.areEqual(semanticsPropertyKey, semanticsPropertyKey2)) {
                                                                    List list = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, semanticsPropertyKey2);
                                                                    String strValueOf = String.valueOf(list != null ? (AnnotatedString) CollectionsKt___CollectionsKt.firstOrNull(list) : null);
                                                                    ContentCaptureSessionCompat contentCaptureSessionCompat = androidContentCaptureManager.contentCaptureSession;
                                                                    if (contentCaptureSessionCompat != null) {
                                                                        i3 = length2;
                                                                        AutofillId autofillIdNewAutofillId = contentCaptureSessionCompat.newAutofillId(i14);
                                                                        if (autofillIdNewAutofillId == null) {
                                                                            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Invalid content capture ID");
                                                                        }
                                                                        ((ContentCaptureSession) contentCaptureSessionCompat.mWrappedObj).notifyViewTextChanged(autofillIdNewAutofillId, strValueOf);
                                                                    }
                                                                }
                                                                j14 = j8 >> i15;
                                                                i18++;
                                                                length2 = i3;
                                                                currentSemanticsNodes$ui_release = intObjectMap3;
                                                            } else {
                                                                intObjectMap3 = currentSemanticsNodes$ui_release;
                                                                j8 = j14;
                                                            }
                                                            i3 = length2;
                                                            j14 = j8 >> i15;
                                                            i18++;
                                                            length2 = i3;
                                                            currentSemanticsNodes$ui_release = intObjectMap3;
                                                        }
                                                        intObjectMap2 = currentSemanticsNodes$ui_release;
                                                        i = length2;
                                                        if (i17 != i15) {
                                                            break;
                                                        }
                                                    } else {
                                                        intObjectMap2 = currentSemanticsNodes$ui_release;
                                                        i = length2;
                                                    }
                                                    if (i16 == length3) {
                                                        break;
                                                    }
                                                    i16++;
                                                    j13 = j5;
                                                    length2 = i;
                                                    currentSemanticsNodes$ui_release = intObjectMap2;
                                                    i15 = 8;
                                                }
                                            } else {
                                                intObjectMap2 = currentSemanticsNodes$ui_release;
                                                iArr2 = iArr4;
                                                jArr2 = jArr6;
                                                i = length2;
                                                j5 = j13;
                                            }
                                        } else {
                                            intObjectMap2 = currentSemanticsNodes$ui_release;
                                            iArr2 = iArr4;
                                            jArr2 = jArr6;
                                            i = length2;
                                            j5 = j13;
                                            j6 = j2;
                                            MutableScatterMap mutableScatterMap2 = semanticsConfiguration.props;
                                            Object[] objArr2 = mutableScatterMap2.keys;
                                            long[] jArr8 = mutableScatterMap2.metadata;
                                            int length4 = jArr8.length - 2;
                                            if (length4 >= 0) {
                                                int i19 = 0;
                                                while (true) {
                                                    long j15 = jArr8[i19];
                                                    if ((((~j15) << c2) & j15 & j) != j) {
                                                        int i20 = 8 - ((~(i19 - length4)) >>> 31);
                                                        int i21 = 0;
                                                        while (i21 < i20) {
                                                            if ((j15 & j3) < j6) {
                                                                SemanticsPropertyKey semanticsPropertyKey3 = (SemanticsPropertyKey) objArr2[(i19 << 3) + i21];
                                                                SemanticsProperties.INSTANCE.getClass();
                                                                jArr4 = jArr8;
                                                                SemanticsPropertyKey semanticsPropertyKey4 = SemanticsProperties.Text;
                                                                if (Intrinsics.areEqual(semanticsPropertyKey3, semanticsPropertyKey4)) {
                                                                    List list2 = (List) SemanticsConfigurationKt.getOrNull(semanticsNodeCopy.unmergedConfig, semanticsPropertyKey4);
                                                                    AnnotatedString annotatedString = list2 != null ? (AnnotatedString) CollectionsKt___CollectionsKt.firstOrNull(list2) : null;
                                                                    List list3 = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, semanticsPropertyKey4);
                                                                    AnnotatedString annotatedString2 = list3 != null ? (AnnotatedString) CollectionsKt___CollectionsKt.firstOrNull(list3) : null;
                                                                    if (!Intrinsics.areEqual(annotatedString, annotatedString2)) {
                                                                        String strValueOf2 = String.valueOf(annotatedString2);
                                                                        ContentCaptureSessionCompat contentCaptureSessionCompat2 = androidContentCaptureManager.contentCaptureSession;
                                                                        if (contentCaptureSessionCompat2 != null) {
                                                                            j7 = j15;
                                                                            AutofillId autofillIdNewAutofillId2 = contentCaptureSessionCompat2.newAutofillId(i14);
                                                                            if (autofillIdNewAutofillId2 == null) {
                                                                                throw AndroidAutofill$$ExternalSyntheticOutline0.m("Invalid content capture ID");
                                                                            }
                                                                            ((ContentCaptureSession) contentCaptureSessionCompat2.mWrappedObj).notifyViewTextChanged(autofillIdNewAutofillId2, strValueOf2);
                                                                        }
                                                                    }
                                                                }
                                                                j15 = j7 >> 8;
                                                                i21++;
                                                                jArr8 = jArr4;
                                                            } else {
                                                                jArr4 = jArr8;
                                                            }
                                                            j7 = j15;
                                                            j15 = j7 >> 8;
                                                            i21++;
                                                            jArr8 = jArr4;
                                                        }
                                                        jArr3 = jArr8;
                                                        if (i20 != 8) {
                                                            break;
                                                        }
                                                    } else {
                                                        jArr3 = jArr8;
                                                    }
                                                    if (i19 == length4) {
                                                        break;
                                                    }
                                                    i19++;
                                                    jArr8 = jArr3;
                                                }
                                            }
                                        }
                                        i2 = 8;
                                    } else {
                                        intObjectMap2 = currentSemanticsNodes$ui_release;
                                        iArr2 = iArr4;
                                        jArr2 = jArr6;
                                        i = length2;
                                        j5 = j13;
                                        c2 = c3;
                                        j6 = j2;
                                        i2 = i5;
                                    }
                                    j13 = j5 >> i2;
                                    i12++;
                                    i5 = i2;
                                    c3 = c2;
                                    j2 = j6;
                                    iArr4 = iArr2;
                                    jArr6 = jArr2;
                                    length2 = i;
                                    currentSemanticsNodes$ui_release = intObjectMap2;
                                }
                                intObjectMap = currentSemanticsNodes$ui_release;
                                iArr = iArr4;
                                jArr = jArr6;
                                int i22 = length2;
                                c = c3;
                                j4 = j2;
                                if (i11 != i5) {
                                    break;
                                } else {
                                    length2 = i22;
                                }
                            } else {
                                intObjectMap = currentSemanticsNodes$ui_release;
                                iArr = iArr4;
                                jArr = jArr6;
                                c = c3;
                                j4 = j2;
                            }
                            if (i10 == length2) {
                                break;
                            }
                            i10++;
                            c3 = c;
                            j2 = j4;
                            iArr4 = iArr;
                            jArr6 = jArr;
                            currentSemanticsNodes$ui_release = intObjectMap;
                            i5 = 8;
                        }
                    } else {
                        c = 7;
                        j4 = j2;
                    }
                    androidContentCaptureManager.previousSemanticsNodes.clear();
                    IntObjectMap currentSemanticsNodes$ui_release2 = androidContentCaptureManager.getCurrentSemanticsNodes$ui_release();
                    int[] iArr5 = currentSemanticsNodes$ui_release2.keys;
                    Object[] objArr3 = currentSemanticsNodes$ui_release2.values;
                    long[] jArr9 = currentSemanticsNodes$ui_release2.metadata;
                    int length5 = jArr9.length - 2;
                    if (length5 >= 0) {
                        int i23 = 0;
                        while (true) {
                            long j16 = jArr9[i23];
                            if ((((~j16) << c) & j16 & j) != j) {
                                int i24 = 8 - ((~(i23 - length5)) >>> 31);
                                for (int i25 = 0; i25 < i24; i25++) {
                                    if ((j16 & j3) < j4) {
                                        int i26 = (i23 << 3) + i25;
                                        androidContentCaptureManager.previousSemanticsNodes.set(iArr5[i26], new SemanticsNodeCopy(((SemanticsNodeWithAdjustedBounds) objArr3[i26]).semanticsNode, androidContentCaptureManager.getCurrentSemanticsNodes$ui_release()));
                                    }
                                    j16 >>= 8;
                                }
                                if (i24 != 8) {
                                    break;
                                }
                            }
                            if (i23 == length5) {
                                break;
                            } else {
                                i23++;
                            }
                        }
                    }
                    androidContentCaptureManager.previousSemanticsRoot = new SemanticsNodeCopy(androidContentCaptureManager.view.semanticsOwner.getUnmergedRootSemanticsNode(), androidContentCaptureManager.getCurrentSemanticsNodes$ui_release());
                    androidContentCaptureManager.checkingForSemanticsChanges = false;
                }
            }
        };
    }

    public static void onVirtualViewTranslationResponses$ui_release(final AndroidContentCaptureManager androidContentCaptureManager, final LongSparseArray longSparseArray) {
        ViewTranslationHelperMethods.INSTANCE.getClass();
        if (Intrinsics.areEqual(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            ViewTranslationHelperMethods.doTranslation(androidContentCaptureManager, longSparseArray);
        } else {
            androidContentCaptureManager.view.post(new Runnable() { // from class: androidx.compose.ui.contentcapture.AndroidContentCaptureManager$ViewTranslationHelperMethods$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AndroidContentCaptureManager androidContentCaptureManager2 = this.f$0;
                    LongSparseArray longSparseArray2 = longSparseArray;
                    AndroidContentCaptureManager.ViewTranslationHelperMethods.INSTANCE.getClass();
                    AndroidContentCaptureManager.ViewTranslationHelperMethods.doTranslation(androidContentCaptureManager2, longSparseArray2);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0093, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r5, r0) == r1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0093 -> B:13:0x0031). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object boundsUpdatesEventLoop$ui_release(ContinuationImpl continuationImpl) throws Throwable {
        AndroidContentCaptureManager$boundsUpdatesEventLoop$1 androidContentCaptureManager$boundsUpdatesEventLoop$1;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator2;
        AndroidContentCaptureManager androidContentCaptureManager;
        if (continuationImpl instanceof AndroidContentCaptureManager$boundsUpdatesEventLoop$1) {
            androidContentCaptureManager$boundsUpdatesEventLoop$1 = (AndroidContentCaptureManager$boundsUpdatesEventLoop$1) continuationImpl;
            int i = androidContentCaptureManager$boundsUpdatesEventLoop$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                androidContentCaptureManager$boundsUpdatesEventLoop$1.label = i - Integer.MIN_VALUE;
            } else {
                androidContentCaptureManager$boundsUpdatesEventLoop$1 = new AndroidContentCaptureManager$boundsUpdatesEventLoop$1(this, continuationImpl);
            }
        }
        Object objHasNext = androidContentCaptureManager$boundsUpdatesEventLoop$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = androidContentCaptureManager$boundsUpdatesEventLoop$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objHasNext);
            BufferedChannel bufferedChannel = this.boundsUpdateChannel;
            bufferedChannel.getClass();
            bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
            androidContentCaptureManager$boundsUpdatesEventLoop$1.L$0 = this;
            androidContentCaptureManager$boundsUpdatesEventLoop$1.L$1 = bufferedChannelIterator;
            androidContentCaptureManager$boundsUpdatesEventLoop$1.label = 1;
            objHasNext = bufferedChannelIterator.hasNext(androidContentCaptureManager$boundsUpdatesEventLoop$1);
            if (objHasNext != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) androidContentCaptureManager$boundsUpdatesEventLoop$1.L$1;
            androidContentCaptureManager = (AndroidContentCaptureManager) androidContentCaptureManager$boundsUpdatesEventLoop$1.L$0;
            ResultKt.throwOnFailure(objHasNext);
            AndroidContentCaptureManager androidContentCaptureManager2 = androidContentCaptureManager;
            bufferedChannelIterator = bufferedChannelIterator2;
            this = androidContentCaptureManager2;
            androidContentCaptureManager$boundsUpdatesEventLoop$1.L$0 = this;
            androidContentCaptureManager$boundsUpdatesEventLoop$1.L$1 = bufferedChannelIterator;
            androidContentCaptureManager$boundsUpdatesEventLoop$1.label = 1;
            objHasNext = bufferedChannelIterator.hasNext(androidContentCaptureManager$boundsUpdatesEventLoop$1);
            if (objHasNext != coroutineSingletons) {
                BufferedChannel.BufferedChannelIterator bufferedChannelIterator3 = bufferedChannelIterator;
                androidContentCaptureManager = this;
                bufferedChannelIterator2 = bufferedChannelIterator3;
                if (((Boolean) objHasNext).booleanValue()) {
                    return Unit.INSTANCE;
                }
                bufferedChannelIterator2.next();
                if (androidContentCaptureManager.isEnabled$ui_release()) {
                    androidContentCaptureManager.notifyContentCaptureChanges();
                }
                if (!androidContentCaptureManager.checkingForSemanticsChanges) {
                    androidContentCaptureManager.checkingForSemanticsChanges = true;
                    androidContentCaptureManager.handler.post(androidContentCaptureManager.contentCaptureChangeChecker);
                }
                long j = androidContentCaptureManager.SendRecurringContentCaptureEventsIntervalMillis;
                androidContentCaptureManager$boundsUpdatesEventLoop$1.L$0 = androidContentCaptureManager;
                androidContentCaptureManager$boundsUpdatesEventLoop$1.L$1 = bufferedChannelIterator2;
                androidContentCaptureManager$boundsUpdatesEventLoop$1.label = 2;
            }
            return coroutineSingletons;
        }
        bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) androidContentCaptureManager$boundsUpdatesEventLoop$1.L$1;
        androidContentCaptureManager = (AndroidContentCaptureManager) androidContentCaptureManager$boundsUpdatesEventLoop$1.L$0;
        ResultKt.throwOnFailure(objHasNext);
        if (((Boolean) objHasNext).booleanValue()) {
        }
    }

    public final void fastForEachReplacedVisibleChildren(SemanticsNode semanticsNode, Function2 function2) {
        semanticsNode.getClass();
        List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
        int size = children$ui_release$default.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = children$ui_release$default.get(i2);
            if (getCurrentSemanticsNodes$ui_release().containsKey(((SemanticsNode) obj).id)) {
                function2.invoke(Integer.valueOf(i), obj);
                i++;
            }
        }
    }

    public final IntObjectMap getCurrentSemanticsNodes$ui_release() {
        if (this.currentSemanticsNodesInvalidated) {
            this.currentSemanticsNodesInvalidated = false;
            this.currentSemanticsNodes = SemanticsUtils_androidKt.getAllUncoveredSemanticsNodesToIntObjectMap(this.view.semanticsOwner);
            this.currentSemanticsNodesSnapshotTimestampMillis = System.currentTimeMillis();
        }
        return this.currentSemanticsNodes;
    }

    public final boolean isEnabled$ui_release() {
        ContentCaptureManager.Companion.getClass();
        return ContentCaptureManager.Companion.isEnabled && this.contentCaptureSession != null;
    }

    public final void notifyContentCaptureChanges() {
        AutofillId autofillIdNewAutofillId;
        ContentCaptureSessionCompat contentCaptureSessionCompat = this.contentCaptureSession;
        if (contentCaptureSessionCompat == null || ((ArrayList) this.bufferedEvents).isEmpty()) {
            return;
        }
        ArrayList arrayList = (ArrayList) this.bufferedEvents;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            Object obj = contentCaptureSessionCompat.mWrappedObj;
            if (i >= size) {
                ((ContentCaptureSession) obj).notifyViewsDisappeared((AutofillId) AutofillIdCompat.toAutofillIdCompat(contentCaptureSessionCompat.mView.getAutofillId()).mWrappedObj, new long[]{Long.MIN_VALUE});
                ((ArrayList) this.bufferedEvents).clear();
                return;
            }
            ContentCaptureEvent contentCaptureEvent = (ContentCaptureEvent) arrayList.get(i);
            int i2 = WhenMappings.$EnumSwitchMapping$0[contentCaptureEvent.type.ordinal()];
            if (i2 == 1) {
                ViewStructureCompat viewStructureCompat = contentCaptureEvent.structureCompat;
                if (viewStructureCompat != null) {
                    ((ContentCaptureSession) obj).notifyViewAppeared((ViewStructure) viewStructureCompat.mWrappedObj);
                }
            } else if (i2 == 2 && (autofillIdNewAutofillId = contentCaptureSessionCompat.newAutofillId(contentCaptureEvent.id)) != null) {
                ((ContentCaptureSession) obj).notifyViewDisappeared(autofillIdNewAutofillId);
            }
            i++;
        }
    }

    public final void onClearTranslation$ui_release() {
        Function0 function0;
        this.translateStatus = TranslateStatus.SHOW_ORIGINAL;
        IntObjectMap currentSemanticsNodes$ui_release = getCurrentSemanticsNodes$ui_release();
        Object[] objArr = currentSemanticsNodes$ui_release.values;
        long[] jArr = currentSemanticsNodes$ui_release.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        SemanticsConfiguration semanticsConfiguration = ((SemanticsNodeWithAdjustedBounds) objArr[(i << 3) + i3]).semanticsNode.unmergedConfig;
                        SemanticsProperties.INSTANCE.getClass();
                        if (SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.IsShowingTextSubstitution) != null) {
                            SemanticsActions.INSTANCE.getClass();
                            AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.ClearTextSubstitution);
                            if (accessibilityAction != null && (function0 = (Function0) accessibilityAction.action) != null) {
                            }
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void onCreateVirtualViewTranslationRequests$ui_release(long[] jArr, Consumer consumer) {
        SemanticsNode semanticsNode;
        String strFastJoinToString$default;
        ViewTranslationHelperMethods.INSTANCE.getClass();
        for (long j : jArr) {
            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) getCurrentSemanticsNodes$ui_release().get((int) j);
            if (semanticsNodeWithAdjustedBounds != null && (semanticsNode = semanticsNodeWithAdjustedBounds.semanticsNode) != null) {
                ViewTranslationRequest.Builder builder = new ViewTranslationRequest.Builder(this.view.getAutofillId(), semanticsNode.id);
                SemanticsProperties.INSTANCE.getClass();
                List list = (List) SemanticsConfigurationKt.getOrNull(semanticsNode.unmergedConfig, SemanticsProperties.Text);
                if (list != null && (strFastJoinToString$default = ListUtilsKt.fastJoinToString$default(list, "\n", null, 62)) != null) {
                    builder.setValue(LSOAttrConst.ATTR_TEXT, TranslationRequestValue.forText(new AnnotatedString(strFastJoinToString$default, null, 2, null)));
                    consumer.accept(builder.build());
                }
            }
        }
    }

    public final void onHideTranslation$ui_release() {
        Function1 function1;
        this.translateStatus = TranslateStatus.SHOW_ORIGINAL;
        IntObjectMap currentSemanticsNodes$ui_release = getCurrentSemanticsNodes$ui_release();
        Object[] objArr = currentSemanticsNodes$ui_release.values;
        long[] jArr = currentSemanticsNodes$ui_release.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        SemanticsConfiguration semanticsConfiguration = ((SemanticsNodeWithAdjustedBounds) objArr[(i << 3) + i3]).semanticsNode.unmergedConfig;
                        SemanticsProperties.INSTANCE.getClass();
                        if (Intrinsics.areEqual(SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.IsShowingTextSubstitution), Boolean.TRUE)) {
                            SemanticsActions.INSTANCE.getClass();
                            AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.ShowTextSubstitution);
                            if (accessibilityAction != null && (function1 = (Function1) accessibilityAction.action) != null) {
                            }
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void onShowTranslation$ui_release() {
        Function1 function1;
        this.translateStatus = TranslateStatus.SHOW_TRANSLATED;
        IntObjectMap currentSemanticsNodes$ui_release = getCurrentSemanticsNodes$ui_release();
        Object[] objArr = currentSemanticsNodes$ui_release.values;
        long[] jArr = currentSemanticsNodes$ui_release.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        SemanticsConfiguration semanticsConfiguration = ((SemanticsNodeWithAdjustedBounds) objArr[(i << 3) + i3]).semanticsNode.unmergedConfig;
                        SemanticsProperties.INSTANCE.getClass();
                        if (Intrinsics.areEqual(SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.IsShowingTextSubstitution), Boolean.FALSE)) {
                            SemanticsActions.INSTANCE.getClass();
                            AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.ShowTextSubstitution);
                            if (accessibilityAction != null && (function1 = (Function1) accessibilityAction.action) != null) {
                            }
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStart$1() {
        this.contentCaptureSession = (ContentCaptureSessionCompat) this.onContentCaptureSession.invoke();
        updateBuffersOnAppeared(-1, this.view.semanticsOwner.getUnmergedRootSemanticsNode());
        notifyContentCaptureChanges();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStop$1() {
        updateBuffersOnDisappeared(this.view.semanticsOwner.getUnmergedRootSemanticsNode());
        notifyContentCaptureChanges();
        this.contentCaptureSession = null;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.handler.removeCallbacks(this.contentCaptureChangeChecker);
        this.contentCaptureSession = null;
    }

    public final void sendContentCaptureAppearEvents(SemanticsNode semanticsNode, final SemanticsNodeCopy semanticsNodeCopy) {
        fastForEachReplacedVisibleChildren(semanticsNode, new Function2() { // from class: androidx.compose.ui.contentcapture.AndroidContentCaptureManager.sendContentCaptureAppearEvents.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int iIntValue = ((Number) obj).intValue();
                SemanticsNode semanticsNode2 = (SemanticsNode) obj2;
                if (!semanticsNodeCopy.children.contains(semanticsNode2.id)) {
                    AndroidContentCaptureManager androidContentCaptureManager = this;
                    int i = AndroidContentCaptureManager.$r8$clinit;
                    androidContentCaptureManager.updateBuffersOnAppeared(iIntValue, semanticsNode2);
                    this.boundsUpdateChannel.mo3476trySendJP2dKIU(Unit.INSTANCE);
                }
                return Unit.INSTANCE;
            }
        });
        List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
        int size = children$ui_release$default.size();
        for (int i = 0; i < size; i++) {
            SemanticsNode semanticsNode2 = (SemanticsNode) children$ui_release$default.get(i);
            if (getCurrentSemanticsNodes$ui_release().containsKey(semanticsNode2.id)) {
                MutableIntObjectMap mutableIntObjectMap = this.previousSemanticsNodes;
                int i2 = semanticsNode2.id;
                if (mutableIntObjectMap.containsKey(i2)) {
                    Object obj = this.previousSemanticsNodes.get(i2);
                    if (obj == null) {
                        throw AndroidAutofill$$ExternalSyntheticOutline0.m("node not present in pruned tree before this change");
                    }
                    sendContentCaptureAppearEvents(semanticsNode2, (SemanticsNodeCopy) obj);
                } else {
                    continue;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x019b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBuffersOnAppeared(int i, SemanticsNode semanticsNode) {
        Function1 function1;
        AutofillId autofillIdNewAutofillId;
        NodeCoordinator nodeCoordinatorFindCoordinatorToGetBounds$ui_release;
        Rect rectLocalBoundingBoxOf;
        ViewStructureCompat viewStructureCompat;
        String strM712toLegacyClassNameV4PA4sw;
        Function1 function12;
        if (isEnabled$ui_release()) {
            SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
            SemanticsProperties.INSTANCE.getClass();
            Boolean bool = (Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.IsShowingTextSubstitution);
            if (this.translateStatus == TranslateStatus.SHOW_ORIGINAL && Intrinsics.areEqual(bool, Boolean.TRUE)) {
                SemanticsActions.INSTANCE.getClass();
                AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.ShowTextSubstitution);
                if (accessibilityAction != null && (function12 = (Function1) accessibilityAction.action) != null) {
                }
            } else if (this.translateStatus == TranslateStatus.SHOW_TRANSLATED && Intrinsics.areEqual(bool, Boolean.FALSE)) {
                SemanticsActions.INSTANCE.getClass();
                AccessibilityAction accessibilityAction2 = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.ShowTextSubstitution);
                if (accessibilityAction2 != null && (function1 = (Function1) accessibilityAction2.action) != null) {
                }
            }
            ContentCaptureSessionCompat contentCaptureSessionCompat = this.contentCaptureSession;
            if (contentCaptureSessionCompat == null) {
                viewStructureCompat = null;
            } else {
                AutofillIdCompat autofillIdCompat = AutofillIdCompat.toAutofillIdCompat(this.view.getAutofillId());
                if (semanticsNode.getParent() != null) {
                    autofillIdNewAutofillId = contentCaptureSessionCompat.newAutofillId(r5.id);
                    if (autofillIdNewAutofillId == null) {
                    }
                } else {
                    autofillIdNewAutofillId = (AutofillId) autofillIdCompat.mWrappedObj;
                }
                int i2 = semanticsNode.id;
                ViewStructureCompat viewStructureCompat2 = ViewStructureCompat.toViewStructureCompat(((ContentCaptureSession) contentCaptureSessionCompat.mWrappedObj).newVirtualViewStructure(autofillIdNewAutofillId, i2));
                SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Password;
                SemanticsConfiguration semanticsConfiguration2 = semanticsNode.unmergedConfig;
                if (!semanticsConfiguration2.props.containsKey(semanticsPropertyKey)) {
                    Object obj = viewStructureCompat2.mWrappedObj;
                    ViewStructure viewStructure = (ViewStructure) obj;
                    Bundle extras = viewStructure.getExtras();
                    if (extras != null) {
                        extras.putLong("android.view.contentcapture.EventTimestamp", this.currentSemanticsNodesSnapshotTimestampMillis);
                        extras.putInt("android.view.ViewStructure.extra.EXTRA_VIEW_NODE_INDEX", i);
                    }
                    String str = (String) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.TestTag);
                    if (str != null) {
                        viewStructure.setId(i2, null, null, str);
                    }
                    if (((Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.IsTraversalGroup)) != null) {
                        ((ViewStructure) obj).setClassName("android.widget.ViewGroup");
                    }
                    List list = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Text);
                    if (list != null) {
                        ((ViewStructure) obj).setClassName("android.widget.TextView");
                        ((ViewStructure) obj).setText(ListUtilsKt.fastJoinToString$default(list, "\n", null, 62));
                    }
                    AnnotatedString annotatedString = (AnnotatedString) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.EditableText);
                    if (annotatedString != null) {
                        ((ViewStructure) obj).setClassName("android.widget.EditText");
                        ((ViewStructure) obj).setText(annotatedString);
                    }
                    List list2 = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.ContentDescription);
                    if (list2 != null) {
                        viewStructure.setContentDescription(ListUtilsKt.fastJoinToString$default(list2, "\n", null, 62));
                    }
                    Role role = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Role);
                    if (role != null && (strM712toLegacyClassNameV4PA4sw = SemanticsUtils_androidKt.m712toLegacyClassNameV4PA4sw(role.value)) != null) {
                        ((ViewStructure) obj).setClassName(strM712toLegacyClassNameV4PA4sw);
                    }
                    TextLayoutResult textLayoutResult = SemanticsUtils_androidKt.getTextLayoutResult(semanticsConfiguration2);
                    if (textLayoutResult != null) {
                        TextLayoutInput textLayoutInput = textLayoutResult.layoutInput;
                        float fM870getValueimpl = TextUnit.m870getValueimpl(textLayoutInput.style.spanStyle.fontSize);
                        Density density = textLayoutInput.density;
                        viewStructure.setTextStyle(density.getFontScale() * density.getDensity() * fM870getValueimpl, 0, 0, 0);
                    }
                    SemanticsNode parent = semanticsNode.getParent();
                    if (parent != null && (nodeCoordinatorFindCoordinatorToGetBounds$ui_release = semanticsNode.findCoordinatorToGetBounds$ui_release()) != null) {
                        NodeCoordinator nodeCoordinator = nodeCoordinatorFindCoordinatorToGetBounds$ui_release.getTail().isAttached ? nodeCoordinatorFindCoordinatorToGetBounds$ui_release : null;
                        if (nodeCoordinator != null) {
                            rectLocalBoundingBoxOf = DelegatableNodeKt.m634requireCoordinator64DMado(parent.outerSemanticsNode, 8).localBoundingBoxOf(nodeCoordinator, true);
                        } else {
                            Rect.Companion.getClass();
                            rectLocalBoundingBoxOf = Rect.Zero;
                        }
                        float f = rectLocalBoundingBoxOf.left;
                        float f2 = rectLocalBoundingBoxOf.top;
                        viewStructure.setDimens((int) f, (int) f2, 0, 0, (int) (rectLocalBoundingBoxOf.right - f), (int) (rectLocalBoundingBoxOf.bottom - f2));
                        viewStructureCompat = viewStructureCompat2;
                    }
                }
            }
            if (viewStructureCompat != null) {
                ((ArrayList) this.bufferedEvents).add(new ContentCaptureEvent(semanticsNode.id, this.currentSemanticsNodesSnapshotTimestampMillis, ContentCaptureEventType.VIEW_APPEAR, viewStructureCompat));
            }
            fastForEachReplacedVisibleChildren(semanticsNode, new Function2() { // from class: androidx.compose.ui.contentcapture.AndroidContentCaptureManager.updateBuffersOnAppeared.1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    AndroidContentCaptureManager androidContentCaptureManager = AndroidContentCaptureManager.this;
                    int i3 = AndroidContentCaptureManager.$r8$clinit;
                    androidContentCaptureManager.updateBuffersOnAppeared(((Number) obj2).intValue(), (SemanticsNode) obj3);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public final void updateBuffersOnDisappeared(SemanticsNode semanticsNode) {
        if (isEnabled$ui_release()) {
            int i = semanticsNode.id;
            ((ArrayList) this.bufferedEvents).add(new ContentCaptureEvent(i, this.currentSemanticsNodesSnapshotTimestampMillis, ContentCaptureEventType.VIEW_DISAPPEAR, null));
            List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
            int size = children$ui_release$default.size();
            for (int i2 = 0; i2 < size; i2++) {
                updateBuffersOnDisappeared((SemanticsNode) children$ui_release$default.get(i2));
            }
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
