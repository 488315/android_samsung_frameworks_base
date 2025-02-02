package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.VirtualLayout;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class Grid extends VirtualLayout {
    private int[] mBoxViewIds;
    private View[] mBoxViews;
    private int mColumns;
    private int mColumnsSet;
    ConstraintLayout mContainer;
    private float mHorizontalGaps;
    private int mNextAvailableIndex;
    private int mOrientation;
    private boolean[][] mPositionMatrix;
    private int mRows;
    private int mRowsSet;
    Set<Integer> mSpanIds;
    private String mStrColumnWeights;
    private String mStrRowWeights;
    private String mStrSkips;
    private String mStrSpans;
    private boolean mValidateInputs;
    private float mVerticalGaps;

    public Grid(Context context) {
        super(context);
        this.mNextAvailableIndex = 0;
        this.mSpanIds = new HashSet();
    }

    private static void clearHParams(View view) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.horizontalWeight = -1.0f;
        layoutParams.leftToRight = -1;
        layoutParams.leftToLeft = -1;
        layoutParams.rightToLeft = -1;
        layoutParams.rightToRight = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = -1;
        view.setLayoutParams(layoutParams);
    }

    private static void clearVParams(View view) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.verticalWeight = -1.0f;
        layoutParams.topToBottom = -1;
        layoutParams.topToTop = -1;
        layoutParams.bottomToTop = -1;
        layoutParams.bottomToBottom = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = -1;
        view.setLayoutParams(layoutParams);
    }

    private void connectView(View view, int i, int i2, int i3, int i4) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        int[] iArr = this.mBoxViewIds;
        layoutParams.leftToLeft = iArr[i2];
        layoutParams.topToTop = iArr[i];
        layoutParams.rightToRight = iArr[(i2 + i4) - 1];
        layoutParams.bottomToBottom = iArr[(i + i3) - 1];
        view.setLayoutParams(layoutParams);
    }

    private void generateGrid(boolean z) {
        int i;
        int i2;
        int[][] parseSpans;
        int[][] parseSpans2;
        if (this.mContainer == null || this.mRows < 1 || this.mColumns < 1) {
            return;
        }
        if (z) {
            for (int i3 = 0; i3 < this.mPositionMatrix.length; i3++) {
                int i4 = 0;
                while (true) {
                    boolean[][] zArr = this.mPositionMatrix;
                    if (i4 < zArr[0].length) {
                        zArr[i3][i4] = true;
                        i4++;
                    }
                }
            }
            ((HashSet) this.mSpanIds).clear();
        }
        this.mNextAvailableIndex = 0;
        int max = Math.max(this.mRows, this.mColumns);
        View[] viewArr = this.mBoxViews;
        if (viewArr == null) {
            this.mBoxViews = new View[max];
            int i5 = 0;
            while (true) {
                View[] viewArr2 = this.mBoxViews;
                if (i5 >= viewArr2.length) {
                    break;
                }
                viewArr2[i5] = makeNewView();
                i5++;
            }
        } else if (max != viewArr.length) {
            View[] viewArr3 = new View[max];
            for (int i6 = 0; i6 < max; i6++) {
                View[] viewArr4 = this.mBoxViews;
                if (i6 < viewArr4.length) {
                    viewArr3[i6] = viewArr4[i6];
                } else {
                    viewArr3[i6] = makeNewView();
                }
            }
            int i7 = max;
            while (true) {
                View[] viewArr5 = this.mBoxViews;
                if (i7 >= viewArr5.length) {
                    break;
                }
                this.mContainer.removeView(viewArr5[i7]);
                i7++;
            }
            this.mBoxViews = viewArr3;
        }
        this.mBoxViewIds = new int[max];
        int i8 = 0;
        while (true) {
            View[] viewArr6 = this.mBoxViews;
            if (i8 >= viewArr6.length) {
                break;
            }
            this.mBoxViewIds[i8] = viewArr6[i8].getId();
            i8++;
        }
        int id = getId();
        int max2 = Math.max(this.mRows, this.mColumns);
        float[] parseWeights = parseWeights(this.mRows, this.mStrRowWeights);
        if (this.mRows == 1) {
            ConstraintLayout.LayoutParams params = params(this.mBoxViews[0]);
            clearVParams(this.mBoxViews[0]);
            params.topToTop = id;
            params.bottomToBottom = id;
            this.mBoxViews[0].setLayoutParams(params);
        } else {
            int i9 = 0;
            while (true) {
                i = this.mRows;
                if (i9 >= i) {
                    break;
                }
                ConstraintLayout.LayoutParams params2 = params(this.mBoxViews[i9]);
                clearVParams(this.mBoxViews[i9]);
                if (parseWeights != null) {
                    params2.verticalWeight = parseWeights[i9];
                }
                if (i9 > 0) {
                    params2.topToBottom = this.mBoxViewIds[i9 - 1];
                } else {
                    params2.topToTop = id;
                }
                if (i9 < this.mRows - 1) {
                    params2.bottomToTop = this.mBoxViewIds[i9 + 1];
                } else {
                    params2.bottomToBottom = id;
                }
                if (i9 > 0) {
                    ((ViewGroup.MarginLayoutParams) params2).topMargin = (int) this.mHorizontalGaps;
                }
                this.mBoxViews[i9].setLayoutParams(params2);
                i9++;
            }
            while (i < max2) {
                ConstraintLayout.LayoutParams params3 = params(this.mBoxViews[i]);
                clearVParams(this.mBoxViews[i]);
                params3.topToTop = id;
                params3.bottomToBottom = id;
                this.mBoxViews[i].setLayoutParams(params3);
                i++;
            }
        }
        int id2 = getId();
        int max3 = Math.max(this.mRows, this.mColumns);
        float[] parseWeights2 = parseWeights(this.mColumns, this.mStrColumnWeights);
        ConstraintLayout.LayoutParams params4 = params(this.mBoxViews[0]);
        if (this.mColumns == 1) {
            clearHParams(this.mBoxViews[0]);
            params4.leftToLeft = id2;
            params4.rightToRight = id2;
            this.mBoxViews[0].setLayoutParams(params4);
        } else {
            int i10 = 0;
            while (true) {
                i2 = this.mColumns;
                if (i10 >= i2) {
                    break;
                }
                ConstraintLayout.LayoutParams params5 = params(this.mBoxViews[i10]);
                clearHParams(this.mBoxViews[i10]);
                if (parseWeights2 != null) {
                    params5.horizontalWeight = parseWeights2[i10];
                }
                if (i10 > 0) {
                    params5.leftToRight = this.mBoxViewIds[i10 - 1];
                } else {
                    params5.leftToLeft = id2;
                }
                if (i10 < this.mColumns - 1) {
                    params5.rightToLeft = this.mBoxViewIds[i10 + 1];
                } else {
                    params5.rightToRight = id2;
                }
                if (i10 > 0) {
                    ((ViewGroup.MarginLayoutParams) params5).leftMargin = (int) this.mHorizontalGaps;
                }
                this.mBoxViews[i10].setLayoutParams(params5);
                i10++;
            }
            while (i2 < max3) {
                ConstraintLayout.LayoutParams params6 = params(this.mBoxViews[i2]);
                clearHParams(this.mBoxViews[i2]);
                params6.leftToLeft = id2;
                params6.rightToRight = id2;
                this.mBoxViews[i2].setLayoutParams(params6);
                i2++;
            }
        }
        String str = this.mStrSkips;
        if (str != null && !str.trim().isEmpty() && (parseSpans2 = parseSpans(this.mStrSkips)) != null) {
            for (int i11 = 0; i11 < parseSpans2.length; i11++) {
                int rowByIndex = getRowByIndex(parseSpans2[i11][0]);
                int colByIndex = getColByIndex(parseSpans2[i11][0]);
                int[] iArr = parseSpans2[i11];
                if (!invalidatePositions(rowByIndex, colByIndex, iArr[1], iArr[2])) {
                    break;
                }
            }
        }
        String str2 = this.mStrSpans;
        if (str2 != null && !str2.trim().isEmpty() && (parseSpans = parseSpans(this.mStrSpans)) != null) {
            int[] iArr2 = this.mIds;
            View[] views = getViews(this.mContainer);
            for (int i12 = 0; i12 < parseSpans.length; i12++) {
                int rowByIndex2 = getRowByIndex(parseSpans[i12][0]);
                int colByIndex2 = getColByIndex(parseSpans[i12][0]);
                int[] iArr3 = parseSpans[i12];
                if (!invalidatePositions(rowByIndex2, colByIndex2, iArr3[1], iArr3[2])) {
                    break;
                }
                View view = views[i12];
                int[] iArr4 = parseSpans[i12];
                connectView(view, rowByIndex2, colByIndex2, iArr4[1], iArr4[2]);
                ((HashSet) this.mSpanIds).add(Integer.valueOf(iArr2[i12]));
            }
        }
        View[] views2 = getViews(this.mContainer);
        for (int i13 = 0; i13 < this.mCount; i13++) {
            if (!((HashSet) this.mSpanIds).contains(Integer.valueOf(this.mIds[i13]))) {
                int nextPosition = getNextPosition();
                int rowByIndex3 = getRowByIndex(nextPosition);
                int colByIndex3 = getColByIndex(nextPosition);
                if (nextPosition == -1) {
                    return;
                } else {
                    connectView(views2[i13], rowByIndex3, colByIndex3, 1, 1);
                }
            }
        }
    }

    private int getColByIndex(int i) {
        return this.mOrientation == 1 ? i / this.mRows : i % this.mColumns;
    }

    private int getNextPosition() {
        boolean z = false;
        int i = 0;
        while (!z) {
            i = this.mNextAvailableIndex;
            if (i >= this.mRows * this.mColumns) {
                return -1;
            }
            int rowByIndex = getRowByIndex(i);
            int colByIndex = getColByIndex(this.mNextAvailableIndex);
            boolean[] zArr = this.mPositionMatrix[rowByIndex];
            if (zArr[colByIndex]) {
                zArr[colByIndex] = false;
                z = true;
            }
            this.mNextAvailableIndex++;
        }
        return i;
    }

    private int getRowByIndex(int i) {
        return this.mOrientation == 1 ? i % this.mRows : i / this.mColumns;
    }

    private void initVariables() {
        boolean[][] zArr = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, this.mRows, this.mColumns);
        this.mPositionMatrix = zArr;
        for (boolean[] zArr2 : zArr) {
            Arrays.fill(zArr2, true);
        }
    }

    private boolean invalidatePositions(int i, int i2, int i3, int i4) {
        for (int i5 = i; i5 < i + i3; i5++) {
            for (int i6 = i2; i6 < i2 + i4; i6++) {
                boolean[][] zArr = this.mPositionMatrix;
                if (i5 < zArr.length && i6 < zArr[0].length) {
                    boolean[] zArr2 = zArr[i5];
                    if (zArr2[i6]) {
                        zArr2[i6] = false;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private View makeNewView() {
        View view = new View(getContext());
        view.setId(View.generateViewId());
        view.setVisibility(4);
        this.mContainer.addView(view, new ConstraintLayout.LayoutParams(0, 0));
        return view;
    }

    private static ConstraintLayout.LayoutParams params(View view) {
        return (ConstraintLayout.LayoutParams) view.getLayoutParams();
    }

    private static int[][] parseSpans(String str) {
        String[] split = str.split(",");
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, split.length, 3);
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].trim().split(":");
            String[] split3 = split2[1].split("x");
            iArr[i][0] = Integer.parseInt(split2[0]);
            iArr[i][1] = Integer.parseInt(split3[0]);
            iArr[i][2] = Integer.parseInt(split3[1]);
        }
        return iArr;
    }

    private static float[] parseWeights(int i, String str) {
        float[] fArr = null;
        if (str != null && !str.trim().isEmpty()) {
            String[] split = str.split(",");
            if (split.length != i) {
                return null;
            }
            fArr = new float[i];
            for (int i2 = 0; i2 < i; i2++) {
                fArr[i2] = Float.parseFloat(split[i2].trim());
            }
        }
        return fArr;
    }

    private void updateActualRowsAndColumns() {
        int i;
        int i2 = this.mRowsSet;
        if (i2 != 0 && (i = this.mColumnsSet) != 0) {
            this.mRows = i2;
            this.mColumns = i;
            return;
        }
        int i3 = this.mColumnsSet;
        if (i3 > 0) {
            this.mColumns = i3;
            this.mRows = ((this.mCount + i3) - 1) / i3;
        } else if (i2 > 0) {
            this.mRows = i2;
            this.mColumns = ((this.mCount + i2) - 1) / i2;
        } else {
            int sqrt = (int) (Math.sqrt(this.mCount) + 1.5d);
            this.mRows = sqrt;
            this.mColumns = ((this.mCount + sqrt) - 1) / sqrt;
        }
    }

    public String getColumnWeights() {
        return this.mStrColumnWeights;
    }

    public int getColumns() {
        return this.mColumnsSet;
    }

    public float getHorizontalGaps() {
        return this.mHorizontalGaps;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public String getRowWeights() {
        return this.mStrRowWeights;
    }

    public int getRows() {
        return this.mRowsSet;
    }

    public String getSkips() {
        return this.mStrSkips;
    }

    public String getSpans() {
        return this.mStrSpans;
    }

    public float getVerticalGaps() {
        return this.mVerticalGaps;
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper
    protected final void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.mUseViewMeasure = true;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.Grid);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 5) {
                    this.mRowsSet = obtainStyledAttributes.getInteger(index, 0);
                } else if (index == 1) {
                    this.mColumnsSet = obtainStyledAttributes.getInteger(index, 0);
                } else if (index == 7) {
                    this.mStrSpans = obtainStyledAttributes.getString(index);
                } else if (index == 6) {
                    this.mStrSkips = obtainStyledAttributes.getString(index);
                } else if (index == 4) {
                    this.mStrRowWeights = obtainStyledAttributes.getString(index);
                } else if (index == 0) {
                    this.mStrColumnWeights = obtainStyledAttributes.getString(index);
                } else if (index == 3) {
                    this.mOrientation = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 2) {
                    this.mHorizontalGaps = obtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == 10) {
                    this.mVerticalGaps = obtainStyledAttributes.getDimension(index, 0.0f);
                } else if (index == 9) {
                    this.mValidateInputs = obtainStyledAttributes.getBoolean(index, false);
                } else if (index == 8) {
                    obtainStyledAttributes.getBoolean(index, false);
                }
            }
            updateActualRowsAndColumns();
            initVariables();
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ConstraintLayout) getParent();
        generateGrid(false);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public final void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            Paint paint = new Paint();
            paint.setColor(-65536);
            paint.setStyle(Paint.Style.STROKE);
            int top = getTop();
            int left = getLeft();
            int bottom = getBottom();
            int right = getRight();
            for (View view : this.mBoxViews) {
                int left2 = view.getLeft() - left;
                int top2 = view.getTop() - top;
                int right2 = view.getRight() - left;
                int bottom2 = view.getBottom() - top;
                canvas.drawRect(left2, 0.0f, right2, bottom - top, paint);
                canvas.drawRect(0.0f, top2, right - left, bottom2, paint);
            }
        }
    }

    public void setColumnWeights(String str) {
        String str2 = this.mStrColumnWeights;
        if (str2 == null || !str2.equals(str)) {
            this.mStrColumnWeights = str;
            generateGrid(true);
            invalidate();
        }
    }

    public void setColumns(int i) {
        if (i <= 50 && this.mColumnsSet != i) {
            this.mColumnsSet = i;
            updateActualRowsAndColumns();
            initVariables();
            generateGrid(false);
            invalidate();
        }
    }

    public void setHorizontalGaps(float f) {
        if (f >= 0.0f && this.mHorizontalGaps != f) {
            this.mHorizontalGaps = f;
            generateGrid(true);
            invalidate();
        }
    }

    public void setOrientation(int i) {
        if ((i == 0 || i == 1) && this.mOrientation != i) {
            this.mOrientation = i;
            generateGrid(true);
            invalidate();
        }
    }

    public void setRowWeights(String str) {
        String str2 = this.mStrRowWeights;
        if (str2 == null || !str2.equals(str)) {
            this.mStrRowWeights = str;
            generateGrid(true);
            invalidate();
        }
    }

    public void setRows(int i) {
        if (i <= 50 && this.mRowsSet != i) {
            this.mRowsSet = i;
            updateActualRowsAndColumns();
            initVariables();
            generateGrid(false);
            invalidate();
        }
    }

    public void setSkips(String str) {
        String str2 = this.mStrSkips;
        if (str2 == null || !str2.equals(str)) {
            this.mStrSkips = str;
            generateGrid(true);
            invalidate();
        }
    }

    public void setSpans(CharSequence charSequence) {
        String str = this.mStrSpans;
        if (str == null || !str.contentEquals(charSequence)) {
            this.mStrSpans = charSequence.toString();
            generateGrid(true);
            invalidate();
        }
    }

    public void setVerticalGaps(float f) {
        if (f >= 0.0f && this.mVerticalGaps != f) {
            this.mVerticalGaps = f;
            generateGrid(true);
            invalidate();
        }
    }

    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNextAvailableIndex = 0;
        this.mSpanIds = new HashSet();
    }

    public Grid(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNextAvailableIndex = 0;
        this.mSpanIds = new HashSet();
    }
}
