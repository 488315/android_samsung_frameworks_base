package androidx.compose.ui.contentcapture;

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
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.SemanticsNodeCopy;
import androidx.compose.ui.platform.SemanticsNodeWithAdjustedBounds;
import androidx.compose.ui.platform.SemanticsUtils_androidKt;
import androidx.compose.ui.platform.coreshims.AutofillIdCompat;
import androidx.compose.ui.platform.coreshims.ContentCaptureSessionCompat;
import androidx.compose.ui.platform.coreshims.ViewStructureCompat;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.util.ListUtilsKt;
import androidx.lifecycle.DefaultLifecycleObserver;
import com.samsung.android.knox.lockscreen.LSOAttrConst;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                long keyAt = longSparseArray.keyAt(i);
                ViewTranslationResponse viewTranslationResponse = (ViewTranslationResponse) longSparseArray.get(keyAt);
                if (viewTranslationResponse != null && (value = viewTranslationResponse.getValue(LSOAttrConst.ATTR_TEXT)) != null && (text = value.getText()) != null && (semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) androidContentCaptureManager.getCurrentSemanticsNodes$ui_release().get((int) keyAt)) != null && (semanticsNode = semanticsNodeWithAdjustedBounds.semanticsNode) != null) {
                    SemanticsActions.INSTANCE.getClass();
                    AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsNode.unmergedConfig, SemanticsActions.SetTextSubstitution);
                    if (accessibilityAction != null && (function1 = (Function1) accessibilityAction.action) != null) {
                    }
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                AndroidContentCaptureManager androidContentCaptureManager = AndroidContentCaptureManager.this;
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
                                            androidContentCaptureManager.boundsUpdateChannel.mo3456trySendJP2dKIU(Unit.INSTANCE);
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
                                                                    String valueOf = String.valueOf(list != null ? (AnnotatedString) CollectionsKt___CollectionsKt.firstOrNull(list) : null);
                                                                    ContentCaptureSessionCompat contentCaptureSessionCompat = androidContentCaptureManager.contentCaptureSession;
                                                                    if (contentCaptureSessionCompat != null) {
                                                                        i3 = length2;
                                                                        AutofillId newAutofillId = contentCaptureSessionCompat.newAutofillId(i14);
                                                                        if (newAutofillId == null) {
                                                                            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Invalid content capture ID");
                                                                        }
                                                                        ((ContentCaptureSession) contentCaptureSessionCompat.mWrappedObj).notifyViewTextChanged(newAutofillId, valueOf);
                                                                        j14 = j8 >> i15;
                                                                        i18++;
                                                                        length2 = i3;
                                                                        currentSemanticsNodes$ui_release = intObjectMap3;
                                                                    }
                                                                }
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
                                                                        String valueOf2 = String.valueOf(annotatedString2);
                                                                        ContentCaptureSessionCompat contentCaptureSessionCompat2 = androidContentCaptureManager.contentCaptureSession;
                                                                        if (contentCaptureSessionCompat2 != null) {
                                                                            j7 = j15;
                                                                            AutofillId newAutofillId2 = contentCaptureSessionCompat2.newAutofillId(i14);
                                                                            if (newAutofillId2 == null) {
                                                                                throw AndroidAutofill$$ExternalSyntheticOutline0.m("Invalid content capture ID");
                                                                            }
                                                                            ((ContentCaptureSession) contentCaptureSessionCompat2.mWrappedObj).notifyViewTextChanged(newAutofillId2, valueOf2);
                                                                            j15 = j7 >> 8;
                                                                            i21++;
                                                                            jArr8 = jArr4;
                                                                        }
                                                                    }
                                                                }
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
                    AndroidContentCaptureManager androidContentCaptureManager2 = AndroidContentCaptureManager.this;
                    LongSparseArray longSparseArray2 = longSparseArray;
                    AndroidContentCaptureManager.ViewTranslationHelperMethods.INSTANCE.getClass();
                    AndroidContentCaptureManager.ViewTranslationHelperMethods.doTranslation(androidContentCaptureManager2, longSparseArray2);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0093, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r5, r0) == r1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0093 -> B:11:0x0031). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object boundsUpdatesEventLoop$ui_release(kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof androidx.compose.ui.contentcapture.AndroidContentCaptureManager$boundsUpdatesEventLoop$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.ui.contentcapture.AndroidContentCaptureManager$boundsUpdatesEventLoop$1 r0 = (androidx.compose.ui.contentcapture.AndroidContentCaptureManager$boundsUpdatesEventLoop$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.contentcapture.AndroidContentCaptureManager$boundsUpdatesEventLoop$1 r0 = new androidx.compose.ui.contentcapture.AndroidContentCaptureManager$boundsUpdatesEventLoop$1
            r0.<init>(r8, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L49
            if (r2 == r4) goto L3d
            if (r2 != r3) goto L35
            java.lang.Object r8 = r0.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r8 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r8
            java.lang.Object r2 = r0.L$0
            androidx.compose.ui.contentcapture.AndroidContentCaptureManager r2 = (androidx.compose.ui.contentcapture.AndroidContentCaptureManager) r2
            kotlin.ResultKt.throwOnFailure(r9)
        L31:
            r7 = r2
            r2 = r8
            r8 = r7
            goto L56
        L35:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3d:
            java.lang.Object r8 = r0.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r8 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r8
            java.lang.Object r2 = r0.L$0
            androidx.compose.ui.contentcapture.AndroidContentCaptureManager r2 = (androidx.compose.ui.contentcapture.AndroidContentCaptureManager) r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L66
        L49:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.channels.BufferedChannel r9 = r8.boundsUpdateChannel
            r9.getClass()
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r2 = new kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator
            r2.<init>()
        L56:
            r0.L$0 = r8
            r0.L$1 = r2
            r0.label = r4
            java.lang.Object r9 = r2.hasNext(r0)
            if (r9 != r1) goto L63
            goto L95
        L63:
            r7 = r2
            r2 = r8
            r8 = r7
        L66:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L96
            r8.next()
            boolean r9 = r2.isEnabled$ui_release()
            if (r9 == 0) goto L7a
            r2.notifyContentCaptureChanges()
        L7a:
            boolean r9 = r2.checkingForSemanticsChanges
            if (r9 != 0) goto L87
            r2.checkingForSemanticsChanges = r4
            android.os.Handler r9 = r2.handler
            androidx.compose.ui.contentcapture.AndroidContentCaptureManager$$ExternalSyntheticLambda0 r5 = r2.contentCaptureChangeChecker
            r9.post(r5)
        L87:
            long r5 = r2.SendRecurringContentCaptureEventsIntervalMillis
            r0.L$0 = r2
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r5, r0)
            if (r9 != r1) goto L31
        L95:
            return r1
        L96:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.contentcapture.AndroidContentCaptureManager.boundsUpdatesEventLoop$ui_release(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        AutofillId newAutofillId;
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
            } else if (i2 == 2 && (newAutofillId = contentCaptureSessionCompat.newAutofillId(contentCaptureEvent.id)) != null) {
                ((ContentCaptureSession) obj).notifyViewDisappeared(newAutofillId);
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
        String fastJoinToString$default;
        ViewTranslationHelperMethods.INSTANCE.getClass();
        for (long j : jArr) {
            SemanticsNodeWithAdjustedBounds semanticsNodeWithAdjustedBounds = (SemanticsNodeWithAdjustedBounds) getCurrentSemanticsNodes$ui_release().get((int) j);
            if (semanticsNodeWithAdjustedBounds != null && (semanticsNode = semanticsNodeWithAdjustedBounds.semanticsNode) != null) {
                ViewTranslationRequest.Builder builder = new ViewTranslationRequest.Builder(this.view.getAutofillId(), semanticsNode.id);
                SemanticsProperties.INSTANCE.getClass();
                List list = (List) SemanticsConfigurationKt.getOrNull(semanticsNode.unmergedConfig, SemanticsProperties.Text);
                if (list != null && (fastJoinToString$default = ListUtilsKt.fastJoinToString$default(list, "\n", null, 62)) != null) {
                    builder.setValue(LSOAttrConst.ATTR_TEXT, TranslationRequestValue.forText(new AnnotatedString(fastJoinToString$default, null, 2, null)));
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
        fastForEachReplacedVisibleChildren(semanticsNode, new Function2() { // from class: androidx.compose.ui.contentcapture.AndroidContentCaptureManager$sendContentCaptureAppearEvents$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int intValue = ((Number) obj).intValue();
                SemanticsNode semanticsNode2 = (SemanticsNode) obj2;
                if (!SemanticsNodeCopy.this.children.contains(semanticsNode2.id)) {
                    AndroidContentCaptureManager androidContentCaptureManager = this;
                    int i = AndroidContentCaptureManager.$r8$clinit;
                    androidContentCaptureManager.updateBuffersOnAppeared(intValue, semanticsNode2);
                    this.boundsUpdateChannel.mo3456trySendJP2dKIU(Unit.INSTANCE);
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

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r4v24 android.view.autofill.AutofillId, still in use, count: 2, list:
          (r4v24 android.view.autofill.AutofillId) from 0x0091: IF  (r4v24 android.view.autofill.AutofillId) == (null android.view.autofill.AutofillId)  -> B:16:0x0076 A[HIDDEN] (LINE:146)
          (r4v24 android.view.autofill.AutofillId) from 0x0098: PHI (r4v8 android.view.autofill.AutofillId) = (r4v7 android.view.autofill.AutofillId), (r4v24 android.view.autofill.AutofillId) binds: [B:65:0x0094, B:24:0x0091] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:114)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    /* JADX WARN: Removed duplicated region for block: B:18:0x01ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBuffersOnAppeared(int r21, androidx.compose.ui.semantics.SemanticsNode r22) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.contentcapture.AndroidContentCaptureManager.updateBuffersOnAppeared(int, androidx.compose.ui.semantics.SemanticsNode):void");
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
