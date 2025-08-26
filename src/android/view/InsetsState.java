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
import android.window.DesktopModeFlags;
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
    /* JADX WARN: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b9  */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3, types: [int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public WindowInsets calculateInsets(Rect rect, InsetsState insetsState, boolean z, int i, int i2, int i3, int i4, int i5, SparseIntArray sparseIntArray, boolean z2) {
        boolean z3;
        Insets[] insetsArr;
        Rect rect2;
        Rect[][] rectArr;
        InsetsState insetsState2 = this;
        Insets[] insetsArr2 = new Insets[10];
        Insets[] insetsArr3 = new Insets[10];
        boolean[] zArr = new boolean[10];
        Rect rect3 = new Rect(rect);
        Rect rect4 = new Rect(rect);
        Rect[][] rectArr2 = new Rect[10][];
        int size = insetsState2.mSources.size() - 1;
        boolean z4 = false;
        int i6 = -1;
        Rect[][] rectArr3 = new Rect[10][];
        int i7 = 0;
        boolean z5 = false;
        int i8 = 0;
        while (size >= 0) {
            Rect rect5 = rect3;
            InsetsSource insetsSourceValueAt = insetsState2.mSources.valueAt(size);
            Insets[] insetsArr4 = insetsArr2;
            int type = insetsSourceValueAt.getType();
            int flags = insetsSourceValueAt.getFlags();
            if ((flags & 4) != 0) {
                i7 |= type;
            }
            int i9 = i7;
            boolean z6 = (!DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS.isTrue() || (flags & 16) == 0) ? z5 : true;
            if (!CoreRune.MW_CAPTION_TYPE) {
                z3 = i6;
            } else if ((flags & 32) != 0) {
                z3 = true;
            } else if ((flags & 64) != 0) {
                z3 = z4;
            }
            if ((flags & 1) != 0) {
                i8 |= type;
            }
            int i10 = size;
            InsetsSource insetsSourcePeekSource = insetsSourceValueAt;
            Rect[][] rectArr4 = rectArr3;
            boolean[] zArr2 = zArr;
            insetsState2.processSource(insetsSourcePeekSource, rect5, false, insetsArr4, sparseIntArray, zArr2, rectArr4, z2);
            boolean z7 = z4;
            if (type == WindowInsets.Type.ime()) {
                insetsState2 = this;
                insetsArr = insetsArr3;
                rect2 = rect4;
                rectArr = rectArr2;
            } else {
                if (insetsState != null) {
                    insetsSourcePeekSource = insetsState.peekSource(insetsSourcePeekSource.getId());
                }
                if (insetsSourcePeekSource != null) {
                    insetsState2 = this;
                    Insets[] insetsArr5 = insetsArr3;
                    rect2 = rect4;
                    Rect[][] rectArr5 = rectArr2;
                    insetsState2.processSource(insetsSourcePeekSource, rect2, true, insetsArr5, null, null, rectArr5);
                    insetsArr = insetsArr5;
                    rectArr = rectArr5;
                }
            }
            insetsArr3 = insetsArr;
            rect4 = rect2;
            rectArr2 = rectArr;
            size = i10 - 1;
            rectArr3 = rectArr4;
            insetsArr2 = insetsArr4;
            zArr = zArr2;
            i7 = i9;
            z5 = z6;
            i6 = z3;
            rect3 = rect5;
            z4 = z7;
        }
        Insets[] insetsArr6 = insetsArr2;
        boolean[] zArr3 = zArr;
        Insets[] insetsArr7 = insetsArr3;
        Rect[][] rectArr6 = rectArr2;
        boolean z8 = z4;
        Rect[][] rectArr7 = rectArr3;
        int i11 = i & 240;
        int iSystemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
        if (i11 == 16) {
            iSystemBars |= WindowInsets.Type.ime();
        }
        if ((i2 & 1024) != 0) {
            iSystemBars &= ~WindowInsets.Type.statusBars();
        }
        ?? r15 = clearsCompatInsets(i4, i2, i5, i7) ? z8 : iSystemBars;
        DisplayCutout displayCutoutCalculateRelativeCutout = null;
        DisplayCutout displayCutoutCalculateRelativeCutout2 = (!CoreRune.FW_CAN_DISPATCH_UDC_CUTOUT || !insetsState2.mCanDispatchUdcCutout || i4 == 2040 || i4 == 2000) ? calculateRelativeCutout(rect) : null;
        RoundedCorners roundedCornersCalculateRelativeRoundedCorners = calculateRelativeRoundedCorners(rect);
        PrivacyIndicatorBounds privacyIndicatorBoundsCalculateRelativePrivacyIndicatorBounds = calculateRelativePrivacyIndicatorBounds(rect);
        DisplayShape displayShapeCalculateRelativeDisplayShape = calculateRelativeDisplayShape(rect);
        boolean z9 = (i3 & 256) == 0 ? z8 : true;
        int iWidth = rect.width();
        int i12 = i8;
        int iHeight = rect.height();
        if (CoreRune.FW_CAN_DISPATCH_UDC_CUTOUT && insetsState2.mCanDispatchUdcCutout) {
            displayCutoutCalculateRelativeCutout = calculateRelativeCutout(rect);
        }
        return new WindowInsets(insetsArr6, insetsArr7, zArr3, z, i7, z5, i6, i12, displayCutoutCalculateRelativeCutout2, roundedCornersCalculateRelativeRoundedCorners, privacyIndicatorBoundsCalculateRelativePrivacyIndicatorBounds, displayShapeCalculateRelativeDisplayShape, r15, z9, rectArr7, rectArr6, iWidth, iHeight, displayCutoutCalculateRelativeCutout);
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
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(size);
            if (insetsSourceValueAt.hasFlags(2)) {
                rect2.inset(insetsSourceValueAt.calculateInsets(rect2, false));
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
        Insets insetsMax = Insets.NONE;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(size);
            if ((insetsSourceValueAt.getType() & i) != 0) {
                insetsMax = Insets.max(insetsSourceValueAt.calculateInsets(rect, z), insetsMax);
            }
        }
        return insetsMax;
    }

    public Insets calculateInsets(Rect rect, int i, int i2) {
        Insets insetsMax = Insets.NONE;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(size);
            if ((insetsSourceValueAt.getType() & i & i2) != 0) {
                insetsMax = Insets.max(insetsSourceValueAt.calculateInsets(rect, true), insetsMax);
            }
        }
        return insetsMax;
    }

    public Insets calculateVisibleInsets(Rect rect, int i, int i2, int i3, int i4) {
        int iSystemBars;
        int iDisplayCutout;
        if ((i3 & 240) != 48) {
            iSystemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
            iDisplayCutout = WindowInsets.Type.ime();
        } else {
            iSystemBars = WindowInsets.Type.systemBars();
            iDisplayCutout = WindowInsets.Type.displayCutout();
        }
        int i5 = iSystemBars | iDisplayCutout;
        DisplayCutout displayCutout = this.mDisplayCutout.get();
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT && displayCutout.isCutoutOnLongEdge(this.mDisplayFrame.width(), this.mDisplayFrame.height())) {
            i5 &= ~WindowInsets.Type.displayCutout();
        }
        Insets insetsMax = Insets.NONE;
        int type = 0;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(size);
            if ((insetsSourceValueAt.getType() & i5) != 0) {
                if (insetsSourceValueAt.hasFlags(4)) {
                    type |= insetsSourceValueAt.getType();
                }
                insetsMax = Insets.max(insetsSourceValueAt.calculateVisibleInsets(rect), insetsMax);
            }
        }
        return clearsCompatInsets(i, i4, i2, type) ? Insets.NONE : insetsMax;
    }

    public int calculateUncontrollableInsetsFromFrame(Rect rect) {
        int type = 0;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(size);
            if (!canControlSource(rect, insetsSourceValueAt)) {
                type |= insetsSourceValueAt.getType();
            }
        }
        return type;
    }

    private static boolean canControlSource(Rect rect, InsetsSource insetsSource) {
        Insets insetsCalculateInsets = insetsSource.calculateInsets(rect, true);
        Rect frame = insetsSource.getFrame();
        int iWidth = frame.width();
        int iHeight = frame.height();
        return insetsCalculateInsets.left == iWidth || insetsCalculateInsets.right == iWidth || insetsCalculateInsets.top == iHeight || insetsCalculateInsets.bottom == iHeight;
    }

    private void processSource(InsetsSource insetsSource, Rect rect, boolean z, Insets[] insetsArr, SparseIntArray sparseIntArray, boolean[] zArr, Rect[][] rectArr) {
        processSource(insetsSource, rect, z, insetsArr, sparseIntArray, zArr, rectArr, false);
    }

    private void processSource(InsetsSource insetsSource, Rect rect, boolean z, Insets[] insetsArr, SparseIntArray sparseIntArray, boolean[] zArr, Rect[][] rectArr, boolean z2) {
        Insets insetsCalculateInsets;
        if (CoreRune.MW_EMBED_ACTIVITY && z2) {
            insetsCalculateInsets = insetsSource.calculateInsetsIgnoreIntersection(rect, z);
        } else {
            insetsCalculateInsets = insetsSource.calculateInsets(rect, z);
        }
        Insets insets = insetsCalculateInsets;
        Rect[] rectArrCalculateBoundingRects = insetsSource.calculateBoundingRects(rect, z);
        int type = insetsSource.getType();
        processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, rectArrCalculateBoundingRects, type);
        if (type == 32) {
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, rectArrCalculateBoundingRects, 16);
        }
        if (type == 4) {
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, rectArrCalculateBoundingRects, 16);
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, rectArrCalculateBoundingRects, 32);
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, insets, rectArrCalculateBoundingRects, 64);
        }
    }

    private void processSourceAsPublicType(InsetsSource insetsSource, Insets[] insetsArr, SparseIntArray sparseIntArray, boolean[] zArr, Rect[][] rectArr, Insets insets, Rect[] rectArr2, int i) {
        int insetSide;
        int iIndexOf = WindowInsets.Type.indexOf(i);
        if (!Insets.NONE.equals(insets)) {
            Insets insets2 = insetsArr[iIndexOf];
            if (insets2 == null) {
                insetsArr[iIndexOf] = insets;
            } else {
                insetsArr[iIndexOf] = Insets.max(insets2, insets);
            }
        }
        if (zArr != null) {
            zArr[iIndexOf] = insetsSource.isVisible();
        }
        if (sparseIntArray != null && (insetSide = InsetsSource.getInsetSide(insets)) != 5) {
            sparseIntArray.put(insetsSource.getId(), insetSide);
        }
        if (rectArr == null || rectArr2.length <= 0) {
            return;
        }
        Rect[] rectArr3 = rectArr[iIndexOf];
        if (rectArr3 == null) {
            rectArr[iIndexOf] = rectArr2;
        } else {
            rectArr[iIndexOf] = concatenate(rectArr3, rectArr2);
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
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(size);
            insetsSourceValueAt.getFrame().scale(f);
            Rect visibleFrame = insetsSourceValueAt.getVisibleFrame();
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
            InsetsSource insetsSourceValueAt = insetsState.mSources.valueAt(i);
            SparseArray<InsetsSource> sparseArray = this.mSources;
            int id = insetsSourceValueAt.getId();
            if (z) {
                insetsSourceValueAt = new InsetsSource(insetsSourceValueAt);
            }
            sparseArray.append(id, insetsSourceValueAt);
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
            InsetsSource insetsSourceValueAt = insetsState.mSources.valueAt(size2);
            if ((insetsSourceValueAt.getType() & i) != 0) {
                this.mSources.put(insetsSourceValueAt.getId(), insetsSourceValueAt);
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
        long jStart = protoOutputStream.start(j);
        InsetsSource insetsSource = this.mSources.get(InsetsSource.ID_IME);
        if (insetsSource != null) {
            insetsSource.dumpDebug(protoOutputStream, 2246267895809L);
        }
        this.mDisplayFrame.dumpDebug(protoOutputStream, 1146756268034L);
        this.mDisplayCutout.get().dumpDebug(protoOutputStream, 1146756268035L);
        protoOutputStream.end(jStart);
    }

    public boolean equals(Object obj) {
        return equals(obj, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x0081, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00b6, code lost:
    
        r8 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean equals(Object obj, boolean z, boolean z2) {
        InsetsSource insetsSourceValueAt;
        InsetsSource insetsSourceValueAt2;
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InsetsState insetsState = (InsetsState) obj;
            if (this.mDisplayFrame.equals(insetsState.mDisplayFrame) && this.mDisplayCutout.equals(insetsState.mDisplayCutout) && this.mCanDispatchUdcCutout == insetsState.mCanDispatchUdcCutout && this.mRoundedCorners.equals(insetsState.mRoundedCorners) && this.mRoundedCornerFrame.equals(insetsState.mRoundedCornerFrame) && this.mPrivacyIndicatorBounds.equals(insetsState.mPrivacyIndicatorBounds) && this.mDisplayShape.equals(insetsState.mDisplayShape)) {
                SparseArray<InsetsSource> sparseArray = this.mSources;
                SparseArray<InsetsSource> sparseArray2 = insetsState.mSources;
                if (!z && !z2) {
                    return sparseArray.contentEquals(sparseArray2);
                }
                int size = sparseArray.size();
                int size2 = sparseArray2.size();
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i >= size && i2 >= size2) {
                        return true;
                    }
                    if (i < size) {
                        insetsSourceValueAt = sparseArray.valueAt(i);
                        while (insetsSourceValueAt != null && ((z && insetsSourceValueAt.getType() == WindowInsets.Type.captionBar()) || (z2 && insetsSourceValueAt.getType() == WindowInsets.Type.ime() && !insetsSourceValueAt.isVisible()))) {
                            i++;
                            if (i < size) {
                                insetsSourceValueAt = sparseArray.valueAt(i);
                            }
                        }
                        if (i2 < size2) {
                            insetsSourceValueAt2 = sparseArray2.valueAt(i2);
                            while (insetsSourceValueAt2 != null && ((z && insetsSourceValueAt2.getType() == WindowInsets.Type.captionBar()) || (z2 && insetsSourceValueAt2.getType() == WindowInsets.Type.ime() && !insetsSourceValueAt2.isVisible()))) {
                                i2++;
                                if (i2 < size2) {
                                    insetsSourceValueAt2 = sparseArray2.valueAt(i2);
                                }
                            }
                            if (!Objects.equals(insetsSourceValueAt, insetsSourceValueAt2)) {
                                return false;
                            }
                            i++;
                            i2++;
                        }
                        insetsSourceValueAt2 = null;
                    }
                    insetsSourceValueAt = null;
                }
            }
        }
        return false;
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
        int i = parcel.readInt();
        SparseArray<InsetsSource> sparseArray = this.mSources;
        if (sparseArray == null) {
            sparseArray = new SparseArray<>(i);
        } else {
            sparseArray.clear();
        }
        for (int i2 = 0; i2 < i; i2++) {
            InsetsSource insetsSource = (InsetsSource) parcel.readTypedObject(InsetsSource.CREATOR);
            sparseArray.append(insetsSource.getId(), insetsSource);
        }
        return sparseArray;
    }

    public Rect getLeftSideHintFrame() {
        return getSideHintFrame(1);
    }

    public Rect getRightSideHintFrame() {
        return getSideHintFrame(3);
    }

    private Rect getSideHintFrame(int i) {
        int size = this.mSources.size();
        for (int i2 = 0; i2 < size; i2++) {
            InsetsSource insetsSourceValueAt = this.mSources.valueAt(i2);
            if (insetsSourceValueAt != null && insetsSourceValueAt.getType() == WindowInsets.Type.systemGestures() && insetsSourceValueAt.getSideHint() == i) {
                return insetsSourceValueAt.getFrame();
            }
        }
        return new Rect();
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
        int iSourceSize = insetsState.sourceSize();
        int iSourceSize2 = insetsState2.sourceSize();
        int i = 0;
        int i2 = 0;
        while (i < iSourceSize && i2 < iSourceSize2) {
            int iSourceIdAt = insetsState.sourceIdAt(i);
            int iSourceIdAt2 = insetsState2.sourceIdAt(i2);
            while (iSourceIdAt != iSourceIdAt2) {
                if (iSourceIdAt < iSourceIdAt2) {
                    onTraverseCallbacks.onIdNotFoundInState2(i, insetsState.sourceAt(i));
                    i++;
                    if (i >= iSourceSize) {
                        break;
                    } else {
                        iSourceIdAt = insetsState.sourceIdAt(i);
                    }
                } else {
                    onTraverseCallbacks.onIdNotFoundInState1(i2, insetsState2.sourceAt(i2));
                    i2++;
                    if (i2 >= iSourceSize2) {
                        break;
                    } else {
                        iSourceIdAt2 = insetsState2.sourceIdAt(i2);
                    }
                }
            }
            if (i >= iSourceSize || i2 >= iSourceSize2) {
                break;
            }
            onTraverseCallbacks.onIdMatch(insetsState.sourceAt(i), insetsState2.sourceAt(i2));
            i++;
            i2++;
        }
        while (i2 < iSourceSize2) {
            onTraverseCallbacks.onIdNotFoundInState1(i2, insetsState2.sourceAt(i2));
            i2++;
        }
        while (i < iSourceSize) {
            onTraverseCallbacks.onIdNotFoundInState2(i, insetsState.sourceAt(i));
            i++;
        }
        onTraverseCallbacks.onFinish(insetsState, insetsState2);
    }
}
