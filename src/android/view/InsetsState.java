package android.view;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SequenceUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes4.dex */
public class InsetsState implements Parcelable {
    public static final Parcelable.Creator<InsetsState> CREATOR = new Parcelable.Creator<InsetsState>() { // from class: android.view.InsetsState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsState createFromParcel(Parcel parcel) {
            return new InsetsState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsState[] newArray(int i) {
            return new InsetsState[i];
        }
    };
    public boolean mCanDispatchUdcCutout;
    private final DisplayCutout.ParcelableWrapper mDisplayCutout;
    private final Rect mDisplayFrame;
    private DisplayShape mDisplayShape;
    private PrivacyIndicatorBounds mPrivacyIndicatorBounds;
    private final Rect mRoundedCornerFrame;
    private RoundedCorners mRoundedCorners;
    private int mSeq;
    private final SparseArray<InsetsSource> mSources;

    public interface OnTraverseCallbacks {
        default void onFinish(InsetsState insetsState, InsetsState insetsState2) {
        }

        default void onIdMatch(InsetsSource insetsSource, InsetsSource insetsSource2) {
        }

        default void onIdNotFoundInState1(int i, InsetsSource insetsSource) {
        }

        default void onIdNotFoundInState2(int i, InsetsSource insetsSource) {
        }

        default void onStart(InsetsState insetsState, InsetsState insetsState2) {
        }
    }

    public static boolean clearsCompatInsets(int i, int i2, int i3, int i4) {
        if ((i2 & 512) == 0 || i == 2013 || i == 2010) {
            return false;
        }
        return i4 == 0 || i3 != 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InsetsState() {
        this.mDisplayFrame = new Rect();
        this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        this.mCanDispatchUdcCutout = false;
        this.mRoundedCornerFrame = new Rect();
        this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
        this.mDisplayShape = DisplayShape.NONE;
        this.mSeq = SequenceUtils.getInitSeq();
        this.mSources = new SparseArray<>();
    }

    public InsetsState(InsetsState insetsState) {
        this(insetsState, false);
    }

    public InsetsState(InsetsState insetsState, boolean z) {
        this.mDisplayFrame = new Rect();
        this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        this.mCanDispatchUdcCutout = false;
        this.mRoundedCornerFrame = new Rect();
        this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
        this.mDisplayShape = DisplayShape.NONE;
        this.mSeq = SequenceUtils.getInitSeq();
        this.mSources = new SparseArray<>(insetsState.mSources.size());
        set(insetsState, z);
    }

    public WindowInsets calculateInsets(Rect rect, InsetsState insetsState, boolean z, int i, int i2, int i3, int i4, int i5, SparseIntArray sparseIntArray) {
        return calculateInsets(rect, insetsState, z, i, i2, i3, i4, i5, sparseIntArray, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009d  */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3, types: [int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.WindowInsets calculateInsets(android.graphics.Rect r28, android.view.InsetsState r29, boolean r30, int r31, int r32, int r33, int r34, int r35, android.util.SparseIntArray r36, boolean r37) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.InsetsState.calculateInsets(android.graphics.Rect, android.view.InsetsState, boolean, int, int, int, int, int, android.util.SparseIntArray, boolean):android.view.WindowInsets");
    }

    private DisplayCutout calculateRelativeCutout(Rect rect) {
        DisplayCutout displayCutout = this.mDisplayCutout.get();
        if (this.mDisplayFrame.equals(rect)) {
            return displayCutout;
        }
        if (rect == null) {
            return DisplayCutout.NO_CUTOUT;
        }
        int i = rect.left - this.mDisplayFrame.left;
        int i2 = rect.top - this.mDisplayFrame.top;
        int i3 = this.mDisplayFrame.right - rect.right;
        int i4 = this.mDisplayFrame.bottom - rect.bottom;
        if (i >= displayCutout.getSafeInsetLeft() && i2 >= displayCutout.getSafeInsetTop() && i3 >= displayCutout.getSafeInsetRight() && i4 >= displayCutout.getSafeInsetBottom()) {
            return DisplayCutout.NO_CUTOUT;
        }
        return displayCutout.inset(i, i2, i3, i4);
    }

    private RoundedCorners calculateRelativeRoundedCorners(Rect rect) {
        if (rect == null) {
            return RoundedCorners.NO_ROUNDED_CORNERS;
        }
        Rect rect2 = new Rect(this.mRoundedCornerFrame);
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource valueAt = this.mSources.valueAt(size);
            if (valueAt.hasFlags(2)) {
                rect2.inset(valueAt.calculateInsets(rect2, false));
            }
        }
        if (!rect2.isEmpty() && !rect2.equals(this.mDisplayFrame)) {
            return this.mRoundedCorners.insetWithFrame(rect, rect2);
        }
        if (this.mDisplayFrame.equals(rect)) {
            return this.mRoundedCorners;
        }
        return this.mRoundedCorners.inset(rect.left - this.mDisplayFrame.left, rect.top - this.mDisplayFrame.top, this.mDisplayFrame.right - rect.right, this.mDisplayFrame.bottom - rect.bottom);
    }

    private PrivacyIndicatorBounds calculateRelativePrivacyIndicatorBounds(Rect rect) {
        if (this.mDisplayFrame.equals(rect)) {
            return this.mPrivacyIndicatorBounds;
        }
        if (rect == null) {
            return null;
        }
        return this.mPrivacyIndicatorBounds.inset(rect.left - this.mDisplayFrame.left, rect.top - this.mDisplayFrame.top, this.mDisplayFrame.right - rect.right, this.mDisplayFrame.bottom - rect.bottom);
    }

    private DisplayShape calculateRelativeDisplayShape(Rect rect) {
        if (this.mDisplayFrame.equals(rect)) {
            return this.mDisplayShape;
        }
        if (rect == null) {
            return DisplayShape.NONE;
        }
        return this.mDisplayShape.setOffset(-rect.left, -rect.top);
    }

    public Insets calculateInsets(Rect rect, int i, boolean z) {
        Insets insets = Insets.NONE;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource valueAt = this.mSources.valueAt(size);
            if ((valueAt.getType() & i) != 0) {
                insets = Insets.max(valueAt.calculateInsets(rect, z), insets);
            }
        }
        return insets;
    }

    public Insets calculateInsets(Rect rect, int i, int i2) {
        Insets insets = Insets.NONE;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource valueAt = this.mSources.valueAt(size);
            if ((valueAt.getType() & i & i2) != 0) {
                insets = Insets.max(valueAt.calculateInsets(rect, true), insets);
            }
        }
        return insets;
    }

    public Insets calculateVisibleInsets(Rect rect, int i, int i2, int i3, int i4) {
        int systemBars;
        int displayCutout;
        if ((i3 & 240) != 48) {
            systemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
            displayCutout = WindowInsets.Type.ime();
        } else {
            systemBars = WindowInsets.Type.systemBars();
            displayCutout = WindowInsets.Type.displayCutout();
        }
        int i5 = systemBars | displayCutout;
        Insets insets = Insets.NONE;
        int i6 = 0;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource valueAt = this.mSources.valueAt(size);
            if ((valueAt.getType() & i5) != 0) {
                if (valueAt.hasFlags(4)) {
                    i6 |= valueAt.getType();
                }
                insets = Insets.max(valueAt.calculateVisibleInsets(rect), insets);
            }
        }
        return clearsCompatInsets(i, i4, i2, i6) ? Insets.NONE : insets;
    }

    public int calculateUncontrollableInsetsFromFrame(Rect rect) {
        int i = 0;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource valueAt = this.mSources.valueAt(size);
            if (!canControlSource(rect, valueAt)) {
                i |= valueAt.getType();
            }
        }
        return i;
    }

    private static boolean canControlSource(Rect rect, InsetsSource insetsSource) {
        Insets calculateInsets = insetsSource.calculateInsets(rect, true);
        Rect frame = insetsSource.getFrame();
        int width = frame.width();
        int height = frame.height();
        return calculateInsets.left == width || calculateInsets.right == width || calculateInsets.top == height || calculateInsets.bottom == height;
    }

    private void processSource(InsetsSource insetsSource, Rect rect, boolean z, Insets[] insetsArr, SparseIntArray sparseIntArray, boolean[] zArr, Rect[][] rectArr) {
        processSource(insetsSource, rect, z, insetsArr, sparseIntArray, zArr, rectArr, false);
    }

    private void processSource(InsetsSource insetsSource, Rect rect, boolean z, Insets[] insetsArr, SparseIntArray sparseIntArray, boolean[] zArr, Rect[][] rectArr, boolean z2) {
        Insets calculateInsets;
        if (CoreRune.MW_EMBED_ACTIVITY && z2) {
            calculateInsets = insetsSource.calculateInsetsIgnoreIntersection(rect, z);
        } else {
            calculateInsets = insetsSource.calculateInsets(rect, z);
        }
        Insets insets = calculateInsets;
        Rect[] calculateBoundingRects = insetsSource.calculateBoundingRects(rect, z);
        int type = insetsSource.getType();
        processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, calculateBoundingRects, type);
        if (type == 32) {
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, calculateBoundingRects, 16);
        }
        if (type == 4) {
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, calculateBoundingRects, 16);
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, calculateBoundingRects, 32);
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, calculateBoundingRects, 64);
        }
    }

    private void processSourceAsPublicType(InsetsSource insetsSource, Insets[] insetsArr, SparseIntArray sparseIntArray, boolean[] zArr, Rect[][] rectArr, Insets insets, Rect[] rectArr2, int i) {
        int insetSide;
        int indexOf = WindowInsets.Type.indexOf(i);
        if (!Insets.NONE.equals(insets)) {
            Insets insets2 = insetsArr[indexOf];
            if (insets2 == null) {
                insetsArr[indexOf] = insets;
            } else {
                insetsArr[indexOf] = Insets.max(insets2, insets);
            }
        }
        if (zArr != null) {
            zArr[indexOf] = insetsSource.isVisible();
        }
        if (sparseIntArray != null && (insetSide = InsetsSource.getInsetSide(insets)) != 5) {
            sparseIntArray.put(insetsSource.getId(), insetSide);
        }
        if (rectArr == null || rectArr2.length <= 0) {
            return;
        }
        Rect[] rectArr3 = rectArr[indexOf];
        if (rectArr3 == null) {
            rectArr[indexOf] = rectArr2;
        } else {
            rectArr[indexOf] = concatenate(rectArr3, rectArr2);
        }
    }

    private static Rect[] concatenate(Rect[] rectArr, Rect[] rectArr2) {
        Rect[] rectArr3 = new Rect[rectArr.length + rectArr2.length];
        System.arraycopy(rectArr, 0, rectArr3, 0, rectArr.length);
        System.arraycopy(rectArr2, 0, rectArr3, rectArr.length, rectArr2.length);
        return rectArr3;
    }

    public InsetsSource getOrCreateSource(int i, int i2) {
        InsetsSource insetsSource = this.mSources.get(i);
        if (insetsSource != null) {
            return insetsSource;
        }
        InsetsSource insetsSource2 = new InsetsSource(i, i2);
        this.mSources.put(i, insetsSource2);
        return insetsSource2;
    }

    public InsetsSource peekSource(int i) {
        return this.mSources.get(i);
    }

    public int sourceIdAt(int i) {
        return this.mSources.keyAt(i);
    }

    public InsetsSource sourceAt(int i) {
        return this.mSources.valueAt(i);
    }

    public int sourceSize() {
        return this.mSources.size();
    }

    public boolean isSourceOrDefaultVisible(int i, int i2) {
        InsetsSource insetsSource = this.mSources.get(i);
        if (insetsSource != null) {
            return insetsSource.isVisible();
        }
        return (WindowInsets.Type.defaultVisible() & i2) != 0;
    }

    public void setDisplayFrame(Rect rect) {
        this.mDisplayFrame.set(rect);
    }

    public Rect getDisplayFrame() {
        return this.mDisplayFrame;
    }

    public void setDisplayCutout(DisplayCutout displayCutout) {
        this.mDisplayCutout.set(displayCutout);
    }

    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout.get();
    }

    public void getDisplayCutoutSafe(Rect rect) {
        rect.set(-100000, -100000, 100000, 100000);
        DisplayCutout displayCutout = this.mDisplayCutout.get();
        Rect rect2 = this.mDisplayFrame;
        if (displayCutout.isEmpty()) {
            return;
        }
        if (CoreRune.FW_CAN_DISPATCH_UDC_CUTOUT && this.mCanDispatchUdcCutout) {
            return;
        }
        if (displayCutout.getSafeInsetLeft() > 0) {
            rect.left = rect2.left + displayCutout.getSafeInsetLeft();
        }
        if (displayCutout.getSafeInsetTop() > 0) {
            rect.top = rect2.top + displayCutout.getSafeInsetTop();
        }
        if (displayCutout.getSafeInsetRight() > 0) {
            rect.right = rect2.right - displayCutout.getSafeInsetRight();
        }
        if (displayCutout.getSafeInsetBottom() > 0) {
            rect.bottom = rect2.bottom - displayCutout.getSafeInsetBottom();
        }
    }

    public void setRoundedCorners(RoundedCorners roundedCorners) {
        this.mRoundedCorners = roundedCorners;
    }

    public RoundedCorners getRoundedCorners() {
        return this.mRoundedCorners;
    }

    public void setRoundedCornerFrame(Rect rect) {
        this.mRoundedCornerFrame.set(rect);
    }

    public void setPrivacyIndicatorBounds(PrivacyIndicatorBounds privacyIndicatorBounds) {
        this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
    }

    public PrivacyIndicatorBounds getPrivacyIndicatorBounds() {
        return this.mPrivacyIndicatorBounds;
    }

    public void setDisplayShape(DisplayShape displayShape) {
        this.mDisplayShape = displayShape;
    }

    public DisplayShape getDisplayShape() {
        return this.mDisplayShape;
    }

    public void removeSource(int i) {
        this.mSources.delete(i);
    }

    public void removeSourceAt(int i) {
        this.mSources.removeAt(i);
    }

    public void setSourceVisible(int i, boolean z) {
        InsetsSource insetsSource = this.mSources.get(i);
        if (insetsSource != null) {
            insetsSource.setVisible(z);
        }
    }

    public void scale(float f) {
        this.mDisplayFrame.scale(f);
        this.mDisplayCutout.scale(f);
        this.mRoundedCorners = this.mRoundedCorners.scale(f);
        this.mRoundedCornerFrame.scale(f);
        this.mPrivacyIndicatorBounds = this.mPrivacyIndicatorBounds.scale(f);
        this.mDisplayShape = this.mDisplayShape.setScale(f);
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource valueAt = this.mSources.valueAt(size);
            valueAt.getFrame().scale(f);
            Rect visibleFrame = valueAt.getVisibleFrame();
            if (visibleFrame != null) {
                visibleFrame.scale(f);
            }
        }
    }

    public int getSeq() {
        return this.mSeq;
    }

    public void setSeq(int i) {
        this.mSeq = i;
    }

    public void set(InsetsState insetsState) {
        set(insetsState, false);
    }

    public void set(InsetsState insetsState, boolean z) {
        this.mDisplayFrame.set(insetsState.mDisplayFrame);
        this.mDisplayCutout.set(insetsState.mDisplayCutout);
        this.mCanDispatchUdcCutout = insetsState.mCanDispatchUdcCutout;
        this.mRoundedCorners = insetsState.getRoundedCorners();
        this.mRoundedCornerFrame.set(insetsState.mRoundedCornerFrame);
        this.mPrivacyIndicatorBounds = insetsState.getPrivacyIndicatorBounds();
        this.mDisplayShape = insetsState.getDisplayShape();
        this.mSeq = insetsState.mSeq;
        this.mSources.clear();
        int size = insetsState.mSources.size();
        for (int i = 0; i < size; i++) {
            InsetsSource valueAt = insetsState.mSources.valueAt(i);
            SparseArray<InsetsSource> sparseArray = this.mSources;
            int id = valueAt.getId();
            if (z) {
                valueAt = new InsetsSource(valueAt);
            }
            sparseArray.append(id, valueAt);
        }
    }

    public void set(InsetsState insetsState, int i) {
        this.mDisplayFrame.set(insetsState.mDisplayFrame);
        this.mDisplayCutout.set(insetsState.mDisplayCutout);
        this.mCanDispatchUdcCutout = insetsState.mCanDispatchUdcCutout;
        this.mRoundedCorners = insetsState.getRoundedCorners();
        this.mRoundedCornerFrame.set(insetsState.mRoundedCornerFrame);
        this.mPrivacyIndicatorBounds = insetsState.getPrivacyIndicatorBounds();
        this.mDisplayShape = insetsState.getDisplayShape();
        this.mSeq = insetsState.mSeq;
        if (i == 0) {
            return;
        }
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            if ((this.mSources.valueAt(size).getType() & i) != 0) {
                this.mSources.removeAt(size);
            }
        }
        for (int size2 = insetsState.mSources.size() - 1; size2 >= 0; size2--) {
            InsetsSource valueAt = insetsState.mSources.valueAt(size2);
            if ((valueAt.getType() & i) != 0) {
                this.mSources.put(valueAt.getId(), valueAt);
            }
        }
    }

    public void addSource(InsetsSource insetsSource) {
        this.mSources.put(insetsSource.getId(), insetsSource);
    }

    public void dump(String str, PrintWriter printWriter) {
        String str2 = str + "  ";
        printWriter.println(str + "InsetsState");
        printWriter.println(str2 + "mDisplayFrame=" + this.mDisplayFrame);
        printWriter.println(str2 + "mDisplayCutout=" + this.mDisplayCutout.get());
        printWriter.println(str2 + "mRoundedCorners=" + this.mRoundedCorners);
        printWriter.println(str2 + "mRoundedCornerFrame=" + this.mRoundedCornerFrame);
        printWriter.println(str2 + "mPrivacyIndicatorBounds=" + this.mPrivacyIndicatorBounds);
        printWriter.println(str2 + "mDisplayShape=" + this.mDisplayShape);
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            this.mSources.valueAt(i).dump(str2 + "  ", printWriter);
        }
    }

    void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        InsetsSource insetsSource = this.mSources.get(InsetsSource.ID_IME);
        if (insetsSource != null) {
            insetsSource.dumpDebug(protoOutputStream, 2246267895809L);
        }
        this.mDisplayFrame.dumpDebug(protoOutputStream, 1146756268034L);
        this.mDisplayCutout.get().dumpDebug(protoOutputStream, 1146756268035L);
        protoOutputStream.end(start);
    }

    public boolean equals(Object obj) {
        return equals(obj, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ae, code lost:
    
        r8 = r12.valueAt(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b6, code lost:
    
        if (r8 == null) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b8, code lost:
    
        if (r13 == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c2, code lost:
    
        if (r8.getType() == android.view.WindowInsets.Type.captionBar()) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d6, code lost:
    
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d8, code lost:
    
        if (r5 >= r3) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00da, code lost:
    
        r8 = r12.valueAt(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00c4, code lost:
    
        if (r14 == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00ce, code lost:
    
        if (r8.getType() != android.view.WindowInsets.Type.ime()) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00d4, code lost:
    
        if (r8.isVisible() != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00e5, code lost:
    
        if (java.util.Objects.equals(r7, r8) != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00e7, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00b6, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0081, code lost:
    
        r7 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r12, boolean r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.InsetsState.equals(java.lang.Object, boolean, boolean):boolean");
    }

    public int hashCode() {
        return Objects.hash(this.mDisplayFrame, this.mDisplayCutout, Integer.valueOf(this.mSources.contentHashCode()), this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mRoundedCornerFrame, this.mDisplayShape);
    }

    public InsetsState(Parcel parcel) {
        this.mDisplayFrame = new Rect();
        this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        this.mCanDispatchUdcCutout = false;
        this.mRoundedCornerFrame = new Rect();
        this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
        this.mDisplayShape = DisplayShape.NONE;
        this.mSeq = SequenceUtils.getInitSeq();
        this.mSources = readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mDisplayFrame.writeToParcel(parcel, i);
        this.mDisplayCutout.writeToParcel(parcel, i);
        parcel.writeByte(this.mCanDispatchUdcCutout ? (byte) 1 : (byte) 0);
        parcel.writeTypedObject(this.mRoundedCorners, i);
        this.mRoundedCornerFrame.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mPrivacyIndicatorBounds, i);
        parcel.writeTypedObject(this.mDisplayShape, i);
        parcel.writeInt(this.mSeq);
        int size = this.mSources.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeTypedObject(this.mSources.valueAt(i2), i);
        }
    }

    public SparseArray<InsetsSource> readFromParcel(Parcel parcel) {
        this.mDisplayFrame.readFromParcel(parcel);
        this.mDisplayCutout.readFromParcel(parcel);
        this.mCanDispatchUdcCutout = parcel.readByte() != 0;
        this.mRoundedCorners = (RoundedCorners) parcel.readTypedObject(RoundedCorners.CREATOR);
        this.mRoundedCornerFrame.readFromParcel(parcel);
        this.mPrivacyIndicatorBounds = (PrivacyIndicatorBounds) parcel.readTypedObject(PrivacyIndicatorBounds.CREATOR);
        this.mDisplayShape = (DisplayShape) parcel.readTypedObject(DisplayShape.CREATOR);
        this.mSeq = parcel.readInt();
        int readInt = parcel.readInt();
        SparseArray<InsetsSource> sparseArray = this.mSources;
        if (sparseArray == null) {
            sparseArray = new SparseArray<>(readInt);
        } else {
            sparseArray.clear();
        }
        for (int i = 0; i < readInt; i++) {
            InsetsSource insetsSource = (InsetsSource) parcel.readTypedObject(InsetsSource.CREATOR);
            sparseArray.append(insetsSource.getId(), insetsSource);
        }
        return sparseArray;
    }

    public String toShortString() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            stringJoiner.add(this.mSources.valueAt(i).toString());
        }
        return "InsetsState: {mSources= { " + stringJoiner + " }";
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            stringJoiner.add(this.mSources.valueAt(i).toString());
        }
        return "InsetsState: {mDisplayFrame=" + this.mDisplayFrame + ", mDisplayCutout=" + this.mDisplayCutout + ", mRoundedCorners=" + this.mRoundedCorners + "  mRoundedCornerFrame=" + this.mRoundedCornerFrame + ", mPrivacyIndicatorBounds=" + this.mPrivacyIndicatorBounds + ", mDisplayShape=" + this.mDisplayShape + ", mSources= { " + stringJoiner + " }";
    }

    public static void traverse(InsetsState insetsState, InsetsState insetsState2, OnTraverseCallbacks onTraverseCallbacks) {
        onTraverseCallbacks.onStart(insetsState, insetsState2);
        int sourceSize = insetsState.sourceSize();
        int sourceSize2 = insetsState2.sourceSize();
        int i = 0;
        int i2 = 0;
        while (i < sourceSize && i2 < sourceSize2) {
            int sourceIdAt = insetsState.sourceIdAt(i);
            int sourceIdAt2 = insetsState2.sourceIdAt(i2);
            while (sourceIdAt != sourceIdAt2) {
                if (sourceIdAt < sourceIdAt2) {
                    onTraverseCallbacks.onIdNotFoundInState2(i, insetsState.sourceAt(i));
                    i++;
                    if (i >= sourceSize) {
                        break;
                    } else {
                        sourceIdAt = insetsState.sourceIdAt(i);
                    }
                } else {
                    onTraverseCallbacks.onIdNotFoundInState1(i2, insetsState2.sourceAt(i2));
                    i2++;
                    if (i2 >= sourceSize2) {
                        break;
                    } else {
                        sourceIdAt2 = insetsState2.sourceIdAt(i2);
                    }
                }
            }
            if (i >= sourceSize || i2 >= sourceSize2) {
                break;
            }
            onTraverseCallbacks.onIdMatch(insetsState.sourceAt(i), insetsState2.sourceAt(i2));
            i++;
            i2++;
        }
        while (i2 < sourceSize2) {
            onTraverseCallbacks.onIdNotFoundInState1(i2, insetsState2.sourceAt(i2));
            i2++;
        }
        while (i < sourceSize) {
            onTraverseCallbacks.onIdNotFoundInState2(i, insetsState.sourceAt(i));
            i++;
        }
        onTraverseCallbacks.onFinish(insetsState, insetsState2);
    }
}
