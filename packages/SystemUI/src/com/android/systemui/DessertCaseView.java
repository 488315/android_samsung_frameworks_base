package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Property;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class DessertCaseView extends FrameLayout {
    public static final float[] ALPHA_MASK;
    public static final float[] MASK;
    public static final int NUM_PASTRIES;
    public static final int[] PASTRIES;
    public static final int[] RARE_PASTRIES;
    public static final int[] XRARE_PASTRIES;
    public static final int[] XXRARE_PASTRIES;
    public final float[] hsv;
    public final int mCellSize;
    public View[] mCells;
    public int mColumns;
    public final SparseArray mDrawables;
    public final Set mFreeList;
    public final Handler mHandler;
    public int mHeight;
    public final AnonymousClass1 mJuggle;
    public int mRows;
    public boolean mStarted;
    public int mWidth;
    public final HashSet tmpSet;

    public final class RescalingContainer extends FrameLayout {
        public DessertCaseView mView;

        public RescalingContainer(Context context) {
            super(context);
            setSystemUiVisibility(5638);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            float f = i3 - i;
            float f2 = i4 - i2;
            int i5 = (int) ((f / 0.25f) / 2.0f);
            int i6 = (int) ((f2 / 0.25f) / 2.0f);
            int i7 = (int) ((f * 0.5f) + i);
            int i8 = (int) ((f2 * 0.5f) + i2);
            this.mView.layout(i7 - i5, i8 - i6, i7 + i5, i8 + i6);
        }
    }

    static {
        int[] iArr = {R.drawable.dessert_kitkat, R.drawable.dessert_android};
        PASTRIES = iArr;
        int[] iArr2 = {R.drawable.dessert_cupcake, R.drawable.dessert_donut, R.drawable.dessert_eclair, R.drawable.dessert_froyo, R.drawable.dessert_gingerbread, R.drawable.dessert_honeycomb, R.drawable.dessert_ics, R.drawable.dessert_jellybean};
        RARE_PASTRIES = iArr2;
        int[] iArr3 = {R.drawable.dessert_petitfour, R.drawable.dessert_donutburger, R.drawable.dessert_flan, R.drawable.dessert_keylimepie};
        XRARE_PASTRIES = iArr3;
        int[] iArr4 = {R.drawable.dessert_zombiegingerbread, R.drawable.dessert_dandroid, R.drawable.dessert_jandycane};
        XXRARE_PASTRIES = iArr4;
        NUM_PASTRIES = iArr.length + iArr2.length + iArr3.length + iArr4.length;
        MASK = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        ALPHA_MASK = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    }

    public DessertCaseView(Context context) {
        this(context, null);
    }

    public static Point[] getOccupied(View view) {
        int intValue = ((Integer) view.getTag(33554434)).intValue();
        Point point = (Point) view.getTag(33554433);
        if (point == null || intValue == 0) {
            return new Point[0];
        }
        Point[] pointArr = new Point[intValue * intValue];
        int i = 0;
        for (int i2 = 0; i2 < intValue; i2++) {
            int i3 = 0;
            while (i3 < intValue) {
                pointArr[i] = new Point(point.x + i2, point.y + i3);
                i3++;
                i++;
            }
        }
        return pointArr;
    }

    public static int irand(int i) {
        float f = 0;
        return (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(i, f, (float) Math.random(), f);
    }

    public final synchronized void fillFreeList(int i) {
        try {
            Context context = getContext();
            int i2 = this.mCellSize;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i2);
            while (!((HashSet) this.mFreeList).isEmpty()) {
                Point point = (Point) ((HashSet) this.mFreeList).iterator().next();
                ((HashSet) this.mFreeList).remove(point);
                if (this.mCells[(point.y * this.mColumns) + point.x] == null) {
                    final ImageView imageView = new ImageView(context);
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.DessertCaseView.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            DessertCaseView dessertCaseView = DessertCaseView.this;
                            ImageView imageView2 = imageView;
                            dessertCaseView.getClass();
                            dessertCaseView.place(imageView2, new Point(DessertCaseView.irand(dessertCaseView.mColumns), DessertCaseView.irand(dessertCaseView.mRows)), true);
                            DessertCaseView.this.postDelayed(new Runnable() { // from class: com.android.systemui.DessertCaseView.2.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DessertCaseView.this.fillFreeList(500);
                                }
                            }, 250L);
                        }
                    });
                    this.hsv[0] = irand(12) * 30.0f;
                    imageView.setBackgroundColor(Color.HSVToColor(this.hsv));
                    float random = (float) Math.random();
                    Drawable drawable = random < 5.0E-4f ? (Drawable) this.mDrawables.get(XXRARE_PASTRIES[(int) (Math.random() * 3)]) : random < 0.005f ? (Drawable) this.mDrawables.get(XRARE_PASTRIES[(int) (Math.random() * 4)]) : random < 0.5f ? (Drawable) this.mDrawables.get(RARE_PASTRIES[(int) (Math.random() * 8)]) : random < 0.7f ? (Drawable) this.mDrawables.get(PASTRIES[(int) (Math.random() * 2)]) : null;
                    if (drawable != null) {
                        imageView.getOverlay().add(drawable);
                    }
                    int i3 = this.mCellSize;
                    layoutParams.height = i3;
                    layoutParams.width = i3;
                    addView(imageView, layoutParams);
                    place(imageView, point, false);
                    if (i > 0) {
                        float intValue = ((Integer) imageView.getTag(33554434)).intValue();
                        float f = 0.5f * intValue;
                        imageView.setScaleX(f);
                        imageView.setScaleY(f);
                        imageView.setAlpha(0.0f);
                        imageView.animate().withLayer().scaleX(intValue).scaleY(intValue).alpha(1.0f).setDuration(i);
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public final synchronized void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mWidth == i && this.mHeight == i2) {
            return;
        }
        boolean z = this.mStarted;
        if (z) {
            this.mStarted = false;
            this.mHandler.removeCallbacks(this.mJuggle);
        }
        this.mWidth = i;
        this.mHeight = i2;
        this.mCells = null;
        removeAllViewsInLayout();
        ((HashSet) this.mFreeList).clear();
        int i5 = this.mHeight;
        int i6 = this.mCellSize;
        int i7 = i5 / i6;
        this.mRows = i7;
        int i8 = this.mWidth / i6;
        this.mColumns = i8;
        this.mCells = new View[i7 * i8];
        setScaleX(0.25f);
        setScaleY(0.25f);
        setTranslationX((this.mWidth - (this.mCellSize * this.mColumns)) * 0.5f * 0.25f);
        setTranslationY((this.mHeight - (this.mCellSize * this.mRows)) * 0.5f * 0.25f);
        for (int i9 = 0; i9 < this.mRows; i9++) {
            for (int i10 = 0; i10 < this.mColumns; i10++) {
                ((HashSet) this.mFreeList).add(new Point(i10, i9));
            }
        }
        if (z) {
            start();
        }
    }

    public final synchronized void place(final View view, Point point, boolean z) {
        int i;
        char c;
        try {
            int i2 = point.x;
            int i3 = point.y;
            float random = (float) Math.random();
            if (view.getTag(33554433) != null) {
                for (Point point2 : getOccupied(view)) {
                    ((HashSet) this.mFreeList).add(point2);
                    this.mCells[(point2.y * this.mColumns) + point2.x] = null;
                }
            }
            if (random < 0.01f) {
                if (i2 < this.mColumns - 3 && i3 < this.mRows - 3) {
                    i = 4;
                }
                i = 1;
            } else if (random < 0.1f) {
                if (i2 < this.mColumns - 2 && i3 < this.mRows - 2) {
                    i = 3;
                }
                i = 1;
            } else {
                if (random < 0.33f && i2 != this.mColumns - 1 && i3 != this.mRows - 1) {
                    i = 2;
                }
                i = 1;
            }
            view.setTag(33554433, point);
            view.setTag(33554434, Integer.valueOf(i));
            this.tmpSet.clear();
            Point[] occupied = getOccupied(view);
            for (Point point3 : occupied) {
                View view2 = this.mCells[(point3.y * this.mColumns) + point3.x];
                if (view2 != null) {
                    this.tmpSet.add(view2);
                }
            }
            Iterator it = this.tmpSet.iterator();
            while (it.hasNext()) {
                final View view3 = (View) it.next();
                for (Point point4 : getOccupied(view3)) {
                    ((HashSet) this.mFreeList).add(point4);
                    this.mCells[(point4.y * this.mColumns) + point4.x] = null;
                }
                if (view3 != view) {
                    c = 1;
                    view3.setTag(33554433, null);
                    if (z) {
                        view3.animate().withLayer().scaleX(0.5f).scaleY(0.5f).alpha(0.0f).setDuration(500L).setInterpolator(new AccelerateInterpolator()).setListener(new Animator.AnimatorListener() { // from class: com.android.systemui.DessertCaseView.4
                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                DessertCaseView.this.removeView(view3);
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationRepeat(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                            }
                        }).start();
                    } else {
                        removeView(view3);
                    }
                } else {
                    c = 1;
                }
            }
            for (Point point5 : occupied) {
                this.mCells[(point5.y * this.mColumns) + point5.x] = view;
                ((HashSet) this.mFreeList).remove(point5);
            }
            float irand = irand(4) * 90.0f;
            if (z) {
                view.bringToFront();
                AnimatorSet animatorSet = new AnimatorSet();
                float f = i;
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, f), ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, f));
                animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
                animatorSet.setDuration(500L);
                AnimatorSet animatorSet2 = new AnimatorSet();
                Property property = View.ROTATION;
                float[] fArr = {irand};
                Property property2 = View.X;
                int i4 = this.mCellSize;
                int i5 = i - 1;
                float[] fArr2 = {(i2 * i4) + ((i4 * i5) / 2)};
                Property property3 = View.Y;
                int i6 = this.mCellSize;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) property, fArr), ObjectAnimator.ofFloat(view, (Property<View, Float>) property2, fArr2), ObjectAnimator.ofFloat(view, (Property<View, Float>) property3, (i3 * i6) + ((i5 * i6) / 2)));
                animatorSet2.setInterpolator(new DecelerateInterpolator());
                animatorSet2.setDuration(500L);
                animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.DessertCaseView.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        view.setLayerType(0, null);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        view.setLayerType(2, null);
                        view.buildLayer();
                    }
                });
                animatorSet.start();
                animatorSet2.start();
            } else {
                int i7 = this.mCellSize;
                int i8 = i - 1;
                view.setX((i2 * i7) + ((i7 * i8) / 2));
                int i9 = this.mCellSize;
                view.setY((i3 * i9) + ((i8 * i9) / 2));
                float f2 = i;
                view.setScaleX(f2);
                view.setScaleY(f2);
                view.setRotation(irand);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void start() {
        if (!this.mStarted) {
            this.mStarted = true;
            fillFreeList(2000);
        }
        this.mHandler.postDelayed(this.mJuggle, 5000L);
    }

    public DessertCaseView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.DessertCaseView$1] */
    public DessertCaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2 = 1;
        this.mDrawables = new SparseArray(NUM_PASTRIES);
        this.mFreeList = new HashSet();
        this.mHandler = new Handler();
        this.mJuggle = new Runnable() { // from class: com.android.systemui.DessertCaseView.1
            @Override // java.lang.Runnable
            public final void run() {
                View childAt = DessertCaseView.this.getChildAt((int) (Math.random() * DessertCaseView.this.getChildCount()));
                DessertCaseView dessertCaseView = DessertCaseView.this;
                dessertCaseView.getClass();
                dessertCaseView.place(childAt, new Point(DessertCaseView.irand(dessertCaseView.mColumns), DessertCaseView.irand(dessertCaseView.mRows)), true);
                DessertCaseView.this.fillFreeList(500);
                DessertCaseView dessertCaseView2 = DessertCaseView.this;
                if (dessertCaseView2.mStarted) {
                    dessertCaseView2.mHandler.postDelayed(dessertCaseView2.mJuggle, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                }
            }
        };
        this.hsv = new float[]{0.0f, 1.0f, 0.85f};
        this.tmpSet = new HashSet();
        Resources resources = getResources();
        int i3 = 0;
        this.mStarted = false;
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.dessert_case_cell_size);
        this.mCellSize = dimensionPixelSize;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (dimensionPixelSize < 512) {
            options.inSampleSize = 2;
        }
        options.inMutable = true;
        int[][] iArr = {PASTRIES, RARE_PASTRIES, XRARE_PASTRIES, XXRARE_PASTRIES};
        Bitmap bitmap = null;
        for (int i4 = 0; i4 < 4; i4 += i2) {
            int[] iArr2 = iArr[i4];
            int length = iArr2.length;
            for (int i5 = i3; i5 < length; i5++) {
                int i6 = iArr2[i5];
                options.inBitmap = bitmap;
                bitmap = BitmapFactory.decodeResource(resources, i6, options);
                Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ALPHA_8);
                Canvas canvas = new Canvas(createBitmap);
                Paint paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(MASK));
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, createBitmap);
                bitmapDrawable.setColorFilter(new ColorMatrixColorFilter(ALPHA_MASK));
                int i7 = this.mCellSize;
                i3 = 0;
                bitmapDrawable.setBounds(0, 0, i7, i7);
                this.mDrawables.append(i6, bitmapDrawable);
                i2 = 1;
            }
        }
    }
}
