package android.view;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;
import android.util.Log;
import android.util.PathParser;
import java.util.Objects;

/* loaded from: classes4.dex */
public class CutoutSpecification {
    private static final String BIND_LEFT_CUTOUT_MARKER = "@bind_left_cutout";
    private static final String BIND_RIGHT_CUTOUT_MARKER = "@bind_right_cutout";
    private static final String BOTTOM_MARKER = "@bottom";
    private static final String CENTER_VERTICAL_MARKER = "@center_vertical";
    private static final String CUTOUT_MARKER = "@cutout";
    private static final boolean DEBUG = false;
    private static final String DP_MARKER = "@dp";
    private static final String LEFT_MARKER = "@left";
    private static final char MARKER_START_CHAR = '@';
    private static final int MINIMAL_ACCEPTABLE_PATH_LENGTH = 5;
    private static final String RIGHT_MARKER = "@right";
    private static final String TAG = "CutoutSpecification";
    private final Rect mBottomBound;
    private Insets mInsets;
    private final Rect mLeftBound;
    private final Path mPath;
    private final Rect mRightBound;
    private final Rect mTopBound;

    /* JADX INFO: Access modifiers changed from: private */
    public static int decideWhichEdge(boolean z, boolean z2, boolean z3) {
        return z ? z2 ? z3 ? 48 : 80 : z3 ? 3 : 5 : z2 ? z3 ? 3 : 5 : z3 ? 48 : 80;
    }

    private CutoutSpecification(Parser parser) {
        this.mPath = parser.mPath;
        this.mLeftBound = parser.mLeftBound;
        this.mTopBound = parser.mTopBound;
        this.mRightBound = parser.mRightBound;
        this.mBottomBound = parser.mBottomBound;
        this.mInsets = parser.mInsets;
        applyPhysicalPixelDisplaySizeRatio(parser.mPhysicalPixelDisplaySizeRatio);
    }

    private void applyPhysicalPixelDisplaySizeRatio(float f) {
        if (f == 1.0f) {
            return;
        }
        Path path = this.mPath;
        if (path != null && !path.isEmpty()) {
            Matrix matrix = new Matrix();
            matrix.postScale(f, f);
            this.mPath.transform(matrix);
        }
        scaleBounds(this.mLeftBound, f);
        scaleBounds(this.mTopBound, f);
        scaleBounds(this.mRightBound, f);
        scaleBounds(this.mBottomBound, f);
        this.mInsets = scaleInsets(this.mInsets, f);
    }

    private void scaleBounds(Rect rect, float f) {
        if (rect == null || rect.isEmpty()) {
            return;
        }
        rect.scale(f);
    }

    private Insets scaleInsets(Insets insets, float f) {
        return Insets.of((int) ((insets.left * f) + 0.5f), (int) ((insets.top * f) + 0.5f), (int) ((insets.right * f) + 0.5f), (int) ((insets.bottom * f) + 0.5f));
    }

    public Path getPath() {
        return this.mPath;
    }

    public Rect getLeftBound() {
        return this.mLeftBound;
    }

    public Rect getTopBound() {
        return this.mTopBound;
    }

    public Rect getRightBound() {
        return this.mRightBound;
    }

    public Rect getBottomBound() {
        return this.mBottomBound;
    }

    public Rect getSafeInset() {
        return this.mInsets.toRect();
    }

    public static class Parser {
        private boolean mBindBottomCutout;
        private boolean mBindLeftCutout;
        private boolean mBindRightCutout;
        private Rect mBottomBound;
        private boolean mInDp;
        private Insets mInsets;
        private boolean mIsCloserToStartSide;
        private final boolean mIsShortEdgeOnTop;
        private boolean mIsTouchShortEdgeEnd;
        private boolean mIsTouchShortEdgeStart;
        private Rect mLeftBound;
        private final Matrix mMatrix;
        private Path mPath;
        private final int mPhysicalDisplayHeight;
        private final int mPhysicalDisplayWidth;
        private final float mPhysicalPixelDisplaySizeRatio;
        private boolean mPositionFromBottom;
        private boolean mPositionFromCenterVertical;
        private boolean mPositionFromLeft;
        private boolean mPositionFromRight;
        private Rect mRightBound;
        private int mSafeInsetBottom;
        private int mSafeInsetLeft;
        private int mSafeInsetRight;
        private int mSafeInsetTop;
        private final float mStableDensity;
        private final Rect mTmpRect;
        private final RectF mTmpRectF;
        private Rect mTopBound;

        public Parser(float f, int i, int i2) {
            this(f, i, i2, 1.0f);
        }

        Parser(float f, int i, int i2, float f2) {
            this.mTmpRect = new Rect();
            this.mTmpRectF = new RectF();
            this.mPositionFromLeft = false;
            this.mPositionFromRight = false;
            this.mPositionFromBottom = false;
            this.mPositionFromCenterVertical = false;
            this.mBindLeftCutout = false;
            this.mBindRightCutout = false;
            this.mBindBottomCutout = false;
            this.mStableDensity = f;
            this.mPhysicalDisplayWidth = i;
            this.mPhysicalDisplayHeight = i2;
            this.mPhysicalPixelDisplaySizeRatio = f2;
            this.mMatrix = new Matrix();
            this.mIsShortEdgeOnTop = i < i2;
        }

        private void computeBoundsRectAndAddToRegion(Path path, Region region, Rect rect) {
            this.mTmpRectF.setEmpty();
            path.computeBounds(this.mTmpRectF, false);
            this.mTmpRectF.round(rect);
            region.op(rect, Region.Op.UNION);
        }

        private void resetStatus(StringBuilder sb) {
            sb.setLength(0);
            this.mPositionFromBottom = false;
            this.mPositionFromLeft = false;
            this.mPositionFromRight = false;
            this.mPositionFromCenterVertical = false;
            this.mBindLeftCutout = false;
            this.mBindRightCutout = false;
            this.mBindBottomCutout = false;
        }

        private void translateMatrix() {
            float f;
            float f2 = 0.0f;
            if (this.mPositionFromRight) {
                f = this.mPhysicalDisplayWidth;
            } else {
                f = this.mPositionFromLeft ? 0.0f : this.mPhysicalDisplayWidth / 2.0f;
            }
            if (this.mPositionFromBottom) {
                f2 = this.mPhysicalDisplayHeight;
            } else if (this.mPositionFromCenterVertical) {
                f2 = this.mPhysicalDisplayHeight / 2.0f;
            }
            this.mMatrix.reset();
            if (this.mInDp) {
                Matrix matrix = this.mMatrix;
                float f3 = this.mStableDensity;
                matrix.postScale(f3, f3);
            }
            this.mMatrix.postTranslate(f, f2);
        }

        private int computeSafeInsets(int i, Rect rect) {
            if (i == 3 && rect.right > 0 && rect.right < this.mPhysicalDisplayWidth) {
                return rect.right;
            }
            if (i == 48 && rect.bottom > 0 && rect.bottom < this.mPhysicalDisplayHeight) {
                return rect.bottom;
            }
            if (i == 5 && rect.left > 0) {
                int i2 = rect.left;
                int i3 = this.mPhysicalDisplayWidth;
                if (i2 < i3) {
                    return i3 - rect.left;
                }
            }
            if (i != 80 || rect.top <= 0) {
                return 0;
            }
            int i4 = rect.top;
            int i5 = this.mPhysicalDisplayHeight;
            if (i4 < i5) {
                return i5 - rect.top;
            }
            return 0;
        }

        private void setSafeInset(int i, int i2) {
            if (i == 3) {
                this.mSafeInsetLeft = i2;
                return;
            }
            if (i == 48) {
                this.mSafeInsetTop = i2;
            } else if (i == 5) {
                this.mSafeInsetRight = i2;
            } else if (i == 80) {
                this.mSafeInsetBottom = i2;
            }
        }

        private int getSafeInset(int i) {
            if (i == 3) {
                return this.mSafeInsetLeft;
            }
            if (i == 48) {
                return this.mSafeInsetTop;
            }
            if (i == 5) {
                return this.mSafeInsetRight;
            }
            if (i == 80) {
                return this.mSafeInsetBottom;
            }
            return 0;
        }

        private Rect onSetEdgeCutout(boolean z, boolean z2, Rect rect) {
            int decideWhichEdge;
            if (z2) {
                decideWhichEdge = CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, true, z);
            } else {
                boolean z3 = this.mIsTouchShortEdgeStart;
                if (z3 && this.mIsTouchShortEdgeEnd) {
                    decideWhichEdge = CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, false, z);
                } else if (z3 || this.mIsTouchShortEdgeEnd) {
                    decideWhichEdge = CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, true, this.mIsCloserToStartSide);
                } else {
                    decideWhichEdge = CutoutSpecification.decideWhichEdge(this.mIsShortEdgeOnTop, z2, z);
                }
            }
            int safeInset = getSafeInset(decideWhichEdge);
            int computeSafeInsets = computeSafeInsets(decideWhichEdge, rect);
            if (safeInset < computeSafeInsets) {
                setSafeInset(decideWhichEdge, computeSafeInsets);
            }
            return new Rect(rect);
        }

        private void setEdgeCutout(Path path) {
            boolean z = this.mBindRightCutout;
            if (z && this.mRightBound == null) {
                this.mRightBound = onSetEdgeCutout(false, !this.mIsShortEdgeOnTop, this.mTmpRect);
            } else {
                boolean z2 = this.mBindLeftCutout;
                if (z2 && this.mLeftBound == null) {
                    this.mLeftBound = onSetEdgeCutout(true, !this.mIsShortEdgeOnTop, this.mTmpRect);
                } else {
                    boolean z3 = this.mBindBottomCutout;
                    if (z3 && this.mBottomBound == null) {
                        this.mBottomBound = onSetEdgeCutout(false, this.mIsShortEdgeOnTop, this.mTmpRect);
                    } else if (z3 || z2 || z || this.mTopBound != null) {
                        return;
                    } else {
                        this.mTopBound = onSetEdgeCutout(true, this.mIsShortEdgeOnTop, this.mTmpRect);
                    }
                }
            }
            Path path2 = this.mPath;
            if (path2 != null) {
                path2.addPath(path);
            } else {
                this.mPath = path;
            }
        }

        private void parseSvgPathSpec(Region region, String str) {
            if (TextUtils.length(str) < CutoutSpecification.MINIMAL_ACCEPTABLE_PATH_LENGTH) {
                Log.e(CutoutSpecification.TAG, "According to SVG definition, it shouldn't happen");
                return;
            }
            translateMatrix();
            Path createPathFromPathData = PathParser.createPathFromPathData(str);
            createPathFromPathData.transform(this.mMatrix);
            computeBoundsRectAndAddToRegion(createPathFromPathData, region, this.mTmpRect);
            if (this.mTmpRect.isEmpty()) {
                return;
            }
            if (this.mIsShortEdgeOnTop) {
                this.mIsTouchShortEdgeStart = this.mTmpRect.top <= 0;
                this.mIsTouchShortEdgeEnd = this.mTmpRect.bottom >= this.mPhysicalDisplayHeight;
                this.mIsCloserToStartSide = this.mTmpRect.centerY() < this.mPhysicalDisplayHeight / 2;
            } else {
                this.mIsTouchShortEdgeStart = this.mTmpRect.left <= 0;
                this.mIsTouchShortEdgeEnd = this.mTmpRect.right >= this.mPhysicalDisplayWidth;
                this.mIsCloserToStartSide = this.mTmpRect.centerX() < this.mPhysicalDisplayWidth / 2;
            }
            setEdgeCutout(createPathFromPathData);
        }

        private void parseSpecWithoutDp(String str) {
            int i;
            Region obtain = Region.obtain();
            StringBuilder sb = null;
            int i2 = 0;
            while (true) {
                int indexOf = str.indexOf(64, i2);
                if (indexOf == -1) {
                    break;
                }
                if (sb == null) {
                    sb = new StringBuilder(str.length());
                }
                sb.append((CharSequence) str, i2, indexOf);
                if (str.startsWith(CutoutSpecification.LEFT_MARKER, indexOf)) {
                    if (!this.mPositionFromRight) {
                        this.mPositionFromLeft = true;
                    }
                    i = indexOf + 5;
                } else if (str.startsWith(CutoutSpecification.RIGHT_MARKER, indexOf)) {
                    if (!this.mPositionFromLeft) {
                        this.mPositionFromRight = true;
                    }
                    i = indexOf + 6;
                } else if (str.startsWith(CutoutSpecification.BOTTOM_MARKER, indexOf)) {
                    parseSvgPathSpec(obtain, sb.toString());
                    i = indexOf + 7;
                    resetStatus(sb);
                    this.mBindBottomCutout = true;
                    this.mPositionFromBottom = true;
                } else if (str.startsWith(CutoutSpecification.CENTER_VERTICAL_MARKER, indexOf)) {
                    parseSvgPathSpec(obtain, sb.toString());
                    i = indexOf + 16;
                    resetStatus(sb);
                    this.mPositionFromCenterVertical = true;
                } else if (str.startsWith(CutoutSpecification.CUTOUT_MARKER, indexOf)) {
                    parseSvgPathSpec(obtain, sb.toString());
                    i = indexOf + 7;
                    resetStatus(sb);
                } else if (str.startsWith(CutoutSpecification.BIND_LEFT_CUTOUT_MARKER, indexOf)) {
                    this.mBindBottomCutout = false;
                    this.mBindRightCutout = false;
                    this.mBindLeftCutout = true;
                    i = indexOf + 17;
                } else if (str.startsWith(CutoutSpecification.BIND_RIGHT_CUTOUT_MARKER, indexOf)) {
                    this.mBindBottomCutout = false;
                    this.mBindLeftCutout = false;
                    this.mBindRightCutout = true;
                    i = indexOf + 18;
                } else {
                    i = indexOf + 1;
                }
                i2 = i;
            }
            if (sb == null) {
                parseSvgPathSpec(obtain, str);
            } else {
                sb.append((CharSequence) str, i2, str.length());
                parseSvgPathSpec(obtain, sb.toString());
            }
            obtain.recycle();
        }

        public CutoutSpecification parse(String str) {
            Objects.requireNonNull(str);
            int lastIndexOf = str.lastIndexOf(CutoutSpecification.DP_MARKER);
            this.mInDp = lastIndexOf != -1;
            if (lastIndexOf != -1) {
                str = str.substring(0, lastIndexOf) + str.substring(lastIndexOf + 3);
            }
            parseSpecWithoutDp(str);
            this.mInsets = Insets.of(this.mSafeInsetLeft, this.mSafeInsetTop, this.mSafeInsetRight, this.mSafeInsetBottom);
            return new CutoutSpecification(this);
        }
    }
}
