package com.samsung.android.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaMetrics;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class SemIndexScrollView extends FrameLayout implements AbsListView.OnScrollListener {
    public static final int GRAVITY_INDEX_BAR_LEFT = 0;
    public static final int GRAVITY_INDEX_BAR_RIGHT = 1;
    private static final float OUT_OF_BOUNDARY = -9999.0f;
    private static final String TAG = "SemIndexScrollView";
    private static final float TRANSPARENCY_VALUE = 0.8f;
    private static final boolean debug = false;
    private Context mContext;
    private String mCurrentIndex;
    private boolean mHasOverlayChild;
    private int mIndexBarGravity;
    IndexScroll mIndexScroll;
    private IndexScrollPreview mIndexScrollPreview;
    private SemAbstractIndexer mIndexer;
    private final IndexerObserver mIndexerObserver;
    private boolean mIsSimpleIndexScroll;
    private int mNumberOfLanguages;
    private OnIndexBarEventListener mOnIndexBarEventListener;
    private final Runnable mPreviewDelayRunnable;
    private boolean mRegisteredDataSetObserver;
    private Typeface mSECRobotoLightRegularFont;
    private long mStartTouchDown;
    private float mTouchY;
    private ViewGroupOverlay mViewGroupOverlay;

    @Deprecated
    public interface OnIndexBarEventListener {
        @Deprecated
        void onIndexChanged(int i);

        @Deprecated
        void onPressed(float f);

        @Deprecated
        void onReleased(float f);
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Deprecated
    public SemIndexScrollView(Context context) {
        super(context);
        this.mIndexBarGravity = 1;
        this.mIndexerObserver = new IndexerObserver();
        this.mIsSimpleIndexScroll = false;
        this.mOnIndexBarEventListener = null;
        this.mRegisteredDataSetObserver = false;
        this.mHasOverlayChild = false;
        this.mTouchY = OUT_OF_BOUNDARY;
        this.mStartTouchDown = 0L;
        this.mPreviewDelayRunnable = new Runnable() { // from class: com.samsung.android.widget.SemIndexScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                if (SemIndexScrollView.this.mIndexScrollPreview != null) {
                    SemIndexScrollView.this.mIndexScrollPreview.fadeOutAnimation();
                }
            }
        };
        this.mContext = context;
        this.mCurrentIndex = null;
        init(context, this.mIndexBarGravity);
    }

    @Deprecated
    public SemIndexScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIndexBarGravity = 1;
        this.mIndexerObserver = new IndexerObserver();
        this.mIsSimpleIndexScroll = false;
        this.mOnIndexBarEventListener = null;
        this.mRegisteredDataSetObserver = false;
        this.mHasOverlayChild = false;
        this.mTouchY = OUT_OF_BOUNDARY;
        this.mStartTouchDown = 0L;
        this.mPreviewDelayRunnable = new Runnable() { // from class: com.samsung.android.widget.SemIndexScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                if (SemIndexScrollView.this.mIndexScrollPreview != null) {
                    SemIndexScrollView.this.mIndexScrollPreview.fadeOutAnimation();
                }
            }
        };
        this.mContext = context;
        this.mIndexBarGravity = 1;
        init(context, 1);
    }

    private void init(Context context, int i) {
        this.mViewGroupOverlay = getOverlay();
        if (this.mIndexScrollPreview == null) {
            IndexScrollPreview indexScrollPreview = new IndexScrollPreview(this.mContext);
            this.mIndexScrollPreview = indexScrollPreview;
            indexScrollPreview.setLayout(0, 0, getWidth(), getHeight());
            this.mViewGroupOverlay.add(this.mIndexScrollPreview);
        }
        this.mHasOverlayChild = true;
        this.mIndexScroll = new IndexScroll(this.mContext, getHeight(), getWidth(), i);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        IndexScrollPreview indexScrollPreview;
        super.dispatchDraw(canvas);
        IndexScroll indexScroll = this.mIndexScroll;
        if (indexScroll == null) {
            return;
        }
        indexScroll.setDimensions(getWidth(), getHeight());
        String str = this.mCurrentIndex;
        if (str != null && str.length() != 0 && (indexScrollPreview = this.mIndexScrollPreview) != null) {
            indexScrollPreview.setLayout(0, 0, getWidth(), getHeight());
            this.mIndexScrollPreview.invalidate();
        }
        IndexScroll indexScroll2 = this.mIndexScroll;
        if (indexScroll2 == null || !indexScroll2.isAlphabetInit()) {
            return;
        }
        this.mIndexScroll.draw(canvas);
    }

    @Deprecated
    public void setIndexer(SemAbstractIndexer semAbstractIndexer) {
        SemAbstractIndexer semAbstractIndexer2 = this.mIndexer;
        if (semAbstractIndexer2 != null && this.mRegisteredDataSetObserver) {
            semAbstractIndexer2.unregisterDataSetObserver(this.mIndexerObserver);
            this.mRegisteredDataSetObserver = false;
        }
        this.mIsSimpleIndexScroll = false;
        this.mIndexer = semAbstractIndexer;
        semAbstractIndexer.registerDataSetObserver(this.mIndexerObserver);
        this.mRegisteredDataSetObserver = true;
        if (this.mIndexScroll.mScrollThumbBgDrawable != null) {
            this.mIndexScroll.mScrollThumbBgDrawable.setColorFilter(this.mIndexScroll.mThumbColor, PorterDuff.Mode.MULTIPLY);
        }
        this.mIndexer.cacheIndexInfo();
        this.mIndexScroll.setAlphabetArray(this.mIndexer.getAlphabetArray(), getFirstAlphabetCharacterIndex(), getLastAlphabetCharacterIndex());
        if (this.mIsSimpleIndexScroll || this.mIndexer.getLangAlphabetArray() == null) {
            return;
        }
        this.mNumberOfLanguages = this.mIndexer.getLangAlphabetArray().length;
    }

    @Deprecated
    public void setSimpleIndexScroll(String[] strArr, int i) {
        this.mIsSimpleIndexScroll = true;
        setSimpleIndexWidth((int) this.mContext.getResources().getDimension(R.dimen.sem_indexbar_simpleindex_width));
        if (i != 0) {
            setSimpleIndexWidth(i);
        }
        if (this.mIndexScroll.mScrollThumbBgDrawable != null) {
            this.mIndexScroll.mScrollThumbBgDrawable.setColorFilter(this.mIndexScroll.mThumbColor, PorterDuff.Mode.MULTIPLY);
        }
        this.mIndexScroll.setAlphabetArray(strArr, -1, -1);
    }

    private void setSimpleIndexWidth(int i) {
        IndexScroll indexScroll = this.mIndexScroll;
        if (indexScroll != null) {
            indexScroll.setSimpleIndexScrollWidth(i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mHasOverlayChild) {
            this.mViewGroupOverlay.remove(this.mIndexScrollPreview);
            this.mHasOverlayChild = false;
        }
        SemAbstractIndexer semAbstractIndexer = this.mIndexer;
        if (semAbstractIndexer != null && this.mRegisteredDataSetObserver) {
            semAbstractIndexer.unregisterDataSetObserver(this.mIndexerObserver);
            this.mRegisteredDataSetObserver = false;
        }
        Runnable runnable = this.mPreviewDelayRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mHasOverlayChild) {
            this.mViewGroupOverlay.add(this.mIndexScrollPreview);
            this.mHasOverlayChild = true;
        }
        SemAbstractIndexer semAbstractIndexer = this.mIndexer;
        if (semAbstractIndexer == null || this.mRegisteredDataSetObserver) {
            return;
        }
        semAbstractIndexer.registerDataSetObserver(this.mIndexerObserver);
        this.mRegisteredDataSetObserver = true;
    }

    @Deprecated
    public void setIndexBarBackgroundDrawable(Drawable drawable) {
        this.mIndexScroll.mBgDrawableDefault = drawable;
    }

    @Deprecated
    public void setIndexBarTextColor(int i) {
        this.mIndexScroll.mTextColorDimmed = i;
    }

    @Deprecated
    public void setIndexBarPressedTextColor(int i) {
        this.mIndexScroll.mScrollThumbBgDrawable.setColorFilter(i, PorterDuff.Mode.MULTIPLY);
        this.mIndexScroll.mThumbColor = i;
    }

    @Deprecated
    public void setEffectTextColor(int i) {
        this.mIndexScrollPreview.setTextColor(i);
    }

    @Deprecated
    public void setEffectBackgroundColor(int i) {
        this.mIndexScrollPreview.setBackgroundColor(this.mIndexScroll.getColorWithAlpha(i, 0.8f));
    }

    @Deprecated
    public void setIndexBarGravity(int i) {
        this.mIndexBarGravity = i;
        this.mIndexScroll.setPosition(i);
    }

    private int getListViewPosition(String str) {
        SemAbstractIndexer semAbstractIndexer;
        if (str == null || (semAbstractIndexer = this.mIndexer) == null) {
            return -1;
        }
        return semAbstractIndexer.getCachingValue(this.mIndexScroll.getSelectedIndex());
    }

    @Deprecated
    public void setIndexScrollMargin(int i, int i2) {
        IndexScroll indexScroll = this.mIndexScroll;
        if (indexScroll != null) {
            indexScroll.setIndexScrollBgMargin(i, i2);
        }
    }

    @Override // android.view.View
    @Deprecated
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return handleMotionEvent(motionEvent);
    }

    private boolean handleMotionEvent(MotionEvent motionEvent) {
        int selectedIndex;
        int selectedIndex2;
        String str;
        int selectedIndex3;
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        float x = motionEvent.getX();
        if (action == 0) {
            this.mCurrentIndex = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
            this.mStartTouchDown = System.currentTimeMillis();
            if (this.mCurrentIndex == null) {
                return false;
            }
            if (this.mIndexScroll.isAlphabetInit() && this.mCurrentIndex.length() != 0) {
                this.mIndexScroll.setEffectText(this.mCurrentIndex);
                this.mIndexScroll.drawEffect(y);
                this.mIndexScrollPreview.setLayout(0, 0, getWidth(), getHeight());
                this.mIndexScrollPreview.invalidate();
                this.mTouchY = y;
            }
            if (!this.mIsSimpleIndexScroll) {
                selectedIndex = getListViewPosition(this.mCurrentIndex);
            } else {
                selectedIndex = this.mIndexScroll.getSelectedIndex();
            }
            if (selectedIndex != -1) {
                notifyIndexChange(selectedIndex);
            }
        } else {
            if (action != 1) {
                if (action == 2) {
                    int i = (int) x;
                    int i2 = (int) y;
                    String indexByPosition = this.mIndexScroll.getIndexByPosition(i, i2);
                    String str2 = this.mCurrentIndex;
                    if (str2 != null && indexByPosition == null && !this.mIsSimpleIndexScroll) {
                        String indexByPosition2 = this.mIndexScroll.getIndexByPosition(i, i2);
                        this.mCurrentIndex = this.mIndexScroll.getIndexByPosition(i, i2);
                        int listViewPosition = getListViewPosition(indexByPosition2);
                        if (listViewPosition != -1) {
                            notifyIndexChange(listViewPosition);
                        }
                    } else if (str2 != null && indexByPosition != null && indexByPosition.length() < this.mCurrentIndex.length()) {
                        String indexByPosition3 = this.mIndexScroll.getIndexByPosition(i, i2);
                        this.mCurrentIndex = indexByPosition3;
                        if (!this.mIsSimpleIndexScroll) {
                            selectedIndex3 = getListViewPosition(indexByPosition3);
                        } else {
                            selectedIndex3 = this.mIndexScroll.getSelectedIndex();
                        }
                        if (selectedIndex3 != -1) {
                            notifyIndexChange(selectedIndex3);
                        }
                    } else {
                        this.mCurrentIndex = this.mIndexScroll.getIndexByPosition(i, i2);
                        if (this.mIndexScroll.isAlphabetInit() && (str = this.mCurrentIndex) != null && str.length() != 0) {
                            this.mIndexScroll.setEffectText(this.mCurrentIndex);
                            this.mIndexScroll.drawEffect(y);
                            this.mTouchY = y;
                        }
                        if (!this.mIsSimpleIndexScroll) {
                            selectedIndex2 = getListViewPosition(this.mCurrentIndex);
                        } else {
                            selectedIndex2 = this.mIndexScroll.getSelectedIndex();
                        }
                        if (selectedIndex2 != -1) {
                            notifyIndexChange(selectedIndex2);
                        }
                    }
                } else if (action != 3) {
                    return false;
                }
            }
            this.mCurrentIndex = null;
            this.mIndexScroll.resetSelectedIndex();
            this.mIndexScrollPreview.close();
            this.mTouchY = OUT_OF_BOUNDARY;
            OnIndexBarEventListener onIndexBarEventListener = this.mOnIndexBarEventListener;
            if (onIndexBarEventListener != null) {
                onIndexBarEventListener.onReleased(y);
            }
        }
        invalidate();
        return true;
    }

    private int getFirstAlphabetCharacterIndex() {
        int currentLang = this.mIndexer.getCurrentLang();
        int length = this.mIndexer.getAlphabetArray().length;
        int i = 0;
        while (i < length && currentLang != this.mIndexer.getLangbyIndex(i)) {
            i++;
        }
        if (i < length) {
            return i;
        }
        return -1;
    }

    private int getLastAlphabetCharacterIndex() {
        SemAbstractIndexer semAbstractIndexer = this.mIndexer;
        if (semAbstractIndexer == null) {
            return -1;
        }
        int currentLang = semAbstractIndexer.getCurrentLang();
        int length = this.mIndexer.getAlphabetArray().length - 1;
        while (length >= 0 && currentLang != this.mIndexer.getLangbyIndex(length)) {
            length--;
        }
        if (length > 0) {
            return (r2 - length) - 1;
        }
        return -1;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    private void notifyIndexChange(int i) {
        OnIndexBarEventListener onIndexBarEventListener = this.mOnIndexBarEventListener;
        if (onIndexBarEventListener != null) {
            onIndexBarEventListener.onIndexChanged(i);
        }
    }

    @Deprecated
    public void setOnIndexBarEventListener(OnIndexBarEventListener onIndexBarEventListener) {
        this.mOnIndexBarEventListener = onIndexBarEventListener;
    }

    class IndexerObserver extends DataSetObserver {
        private final long INDEX_UPDATE_DELAY = 200;
        boolean mDataInvalid = false;
        Runnable mUpdateIndex = new Runnable() { // from class: com.samsung.android.widget.SemIndexScrollView.IndexerObserver.1
            @Override // java.lang.Runnable
            public void run() {
                IndexerObserver.this.mDataInvalid = false;
            }
        };

        IndexerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            notifyDataSetChange();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            notifyDataSetChange();
        }

        public boolean hasIndexerDataValid() {
            return !this.mDataInvalid;
        }

        private void notifyDataSetChange() {
            this.mDataInvalid = true;
            SemIndexScrollView.this.removeCallbacks(this.mUpdateIndex);
            SemIndexScrollView.this.postDelayed(this.mUpdateIndex, 200L);
        }
    }

    class IndexScroll {
        public static final int FIRST_LETTER_NOT_RELEVANT_NOT_MULTI_LANGUAGE = -1;
        public static final int GRAVITY_INDEX_BAR_LEFT = 0;
        public static final int GRAVITY_INDEX_BAR_RIGHT = 1;
        public static final int LAST_LETTER_NOT_RELEVANT_NOT_MULTI_LANGUAGE = -1;
        public static final int NO_SELECTED_INDEX = -1;
        private static final String TAG = "IndexScroll";
        private static final boolean debug = false;
        private int mAdditionalSpace;
        private String[] mAlphabetArray;
        private int mAlphabetArrayFirstLetterIndex;
        private int mAlphabetArrayLastLetterIndex;
        private String[] mAlphabetArrayToDraw;
        private int mAlphabetSize;
        private int mAlphabetToDrawSize;
        private Drawable mBgDrawableDefault;
        private Rect mBgRect;
        private boolean mBgRectParamsSet;
        private int mBgRectWidth;
        private int mBgTintColor;
        private String mBigText;
        private float mContentMinHeight;
        private int mContentPadding;
        private Context mContext;
        private int mDotHeight;
        LangAttributeValues mFirstLang;
        private int mHeight;
        private float mIndexScrollPreviewRadius;
        private boolean mIsAlphabetInit;
        private boolean mIsSetDimensions;
        private float mItemHeight;
        private int mItemWidth;
        private int mItemWidthGap;
        private Paint mPaint;
        private int mPosition;
        private float mPreviewLimitY;
        private int mScreenHeight;
        private int mScrollBottom;
        private int mScrollBottomMargin;
        private int mScrollThumbAdditionalHeight;
        private Drawable mScrollThumbBgDrawable;
        private Rect mScrollThumbBgRect;
        private int mScrollThumbBgRectHeight;
        private int mScrollThumbBgRectPadding;
        private int mScrollTop;
        private int mScrollTopMargin;
        LangAttributeValues mSecondLang;
        private int mSelectedIndex;
        private float mSeparatorHeight;
        private String mSmallText;
        private Rect mTextBounds;
        private int mTextColorDimmed;
        private int mTextSize;
        private int mThumbColor;
        private int mWidth;
        private int mWidthShift;

        class LangAttributeValues {
            String[] alphabetArray;
            int dotCount;
            float height;
            int indexCount;
            float separatorHeight;
            int totalCount;

            public LangAttributeValues(IndexScroll indexScroll, int i, int i2, int i3, float f, float f2) {
                this.indexCount = i;
                this.dotCount = i2;
                this.totalCount = i3;
                this.height = f;
                this.separatorHeight = f2;
            }
        }

        public IndexScroll(Context context, int i, int i2) {
            this.mAlphabetArray = null;
            this.mAlphabetArrayFirstLetterIndex = -1;
            this.mAlphabetArrayLastLetterIndex = -1;
            this.mSelectedIndex = -1;
            this.mPosition = 0;
            this.mBgDrawableDefault = null;
            this.mScrollThumbBgDrawable = null;
            this.mThumbColor = 0;
            this.mIsAlphabetInit = false;
            this.mHeight = i;
            this.mWidth = i2;
            this.mWidthShift = 0;
            this.mScrollTop = 0;
            this.mTextBounds = new Rect();
            this.mBgRectParamsSet = false;
            this.mContext = context;
            init();
        }

        public IndexScroll(Context context, int i, int i2, int i3) {
            this.mAlphabetArray = null;
            this.mAlphabetArrayFirstLetterIndex = -1;
            this.mAlphabetArrayLastLetterIndex = -1;
            this.mSelectedIndex = -1;
            this.mBgDrawableDefault = null;
            this.mScrollThumbBgDrawable = null;
            this.mThumbColor = 0;
            this.mIsAlphabetInit = false;
            this.mHeight = i;
            this.mWidth = i2;
            this.mPosition = i3;
            this.mWidthShift = 0;
            this.mScrollTop = 0;
            this.mTextBounds = new Rect();
            this.mBgRectParamsSet = false;
            this.mContext = context;
            init();
        }

        public boolean isAlphabetInit() {
            return this.mIsAlphabetInit;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public int getSelectedIndex() {
            return this.mSelectedIndex;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public void setSimpleIndexScrollWidth(int i) {
            if (i <= 0) {
                return;
            }
            this.mItemWidth = i;
            this.mBgRectWidth = i;
            allocateBgRectangle();
        }

        public void setIndexScrollBgMargin(int i, int i2) {
            this.mScrollTopMargin = i;
            this.mScrollBottomMargin = i2;
        }

        public void setPosition(int i) {
            this.mPosition = i;
            setBgRectParams();
        }

        public void setDimensions(int i, int i2) {
            if (this.mIsAlphabetInit) {
                if (this.mWidth == i && this.mHeight == i2 && !this.mIsSetDimensions) {
                    return;
                }
                this.mIsSetDimensions = false;
                this.mWidth = i;
                int i3 = i2 - (((this.mScrollTop + this.mScrollBottom) + this.mScrollTopMargin) + this.mScrollBottomMargin);
                this.mHeight = i3;
                this.mScreenHeight = i2;
                float f = i3 / this.mAlphabetSize;
                this.mItemHeight = f;
                this.mSeparatorHeight = Math.max(f, this.mContentMinHeight);
                setBgRectParams();
                LangAttributeValues langAttributeValues = this.mFirstLang;
                if (langAttributeValues == null || this.mSecondLang == null) {
                    return;
                }
                langAttributeValues.separatorHeight = this.mContentMinHeight;
                this.mSecondLang.separatorHeight = this.mContentMinHeight;
                manageIndexScrollHeight();
            }
        }

        private void init() {
            Resources resources = this.mContext.getResources();
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            if (SemIndexScrollView.this.mSECRobotoLightRegularFont == null) {
                SemIndexScrollView.this.mSECRobotoLightRegularFont = Typeface.create("sec-roboto-light", 0);
            }
            this.mPaint.setTypeface(SemIndexScrollView.this.mSECRobotoLightRegularFont);
            this.mScrollTopMargin = 0;
            this.mScrollBottomMargin = 0;
            this.mItemWidth = 1;
            this.mItemWidthGap = 1;
            this.mBgRectWidth = (int) resources.getDimension(R.dimen.sem_indexbar_width);
            this.mTextSize = (int) resources.getDimension(R.dimen.sem_indexbar_textsize);
            this.mScrollTop = (int) resources.getDimension(R.dimen.sem_indexbar_top_margin);
            this.mScrollBottom = (int) resources.getDimension(R.dimen.sem_indexbar_bottom_margin);
            this.mWidthShift = (int) resources.getDimension(R.dimen.sem_indexbar_side_margin);
            this.mContentPadding = (int) resources.getDimension(R.dimen.sem_indexbar_content_padding);
            this.mContentMinHeight = resources.getDimension(R.dimen.sem_indexbar_content_min_height);
            this.mAdditionalSpace = (int) resources.getDimension(R.dimen.sem_indexbar_additional_touch_boundary);
            this.mIndexScrollPreviewRadius = resources.getDimension(R.dimen.sem_index_scroll_preview_radius);
            this.mPreviewLimitY = resources.getDimension(R.dimen.sem_index_scroll_preview_ypos_limit);
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(16843827, typedValue, true);
            int color = typedValue.resourceId != 0 ? resources.getColor(typedValue.resourceId, null) : typedValue.data;
            this.mFirstLang = new LangAttributeValues(this, 0, 0, 0, 0.0f, 0.0f);
            this.mSecondLang = new LangAttributeValues(this, 0, 0, 0, 0.0f, 0.0f);
            this.mScrollThumbBgRectPadding = (int) resources.getDimension(R.dimen.sem_indexbar_thumb_padding);
            this.mScrollThumbAdditionalHeight = (int) resources.getDimension(R.dimen.sem_indexbar_thumb_additional_height);
            this.mDotHeight = (int) resources.getDimension(R.dimen.sem_indexbar_dot_separator_height);
            SemIndexScrollView.this.mIndexScrollPreview.setBackgroundColor(getColorWithAlpha(color, 0.8f));
            Drawable drawable = resources.getDrawable(R.drawable.sem_indexbar_thumb_mtrl_shape);
            this.mScrollThumbBgDrawable = drawable;
            drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            this.mThumbColor = color;
            this.mContext.getTheme().resolveAttribute(16844176, typedValue, true);
            if (typedValue.data != 0) {
                this.mTextColorDimmed = resources.getColor(R.color.sem_indexbar_text_color, null);
                this.mBgTintColor = resources.getColor(R.color.sem_indexbar_bg_tint_color, null);
            } else {
                this.mTextColorDimmed = resources.getColor(R.color.sem_indexbar_text_color_dark, null);
                this.mBgTintColor = resources.getColor(R.color.sem_indexbar_bg_tint_color_dark, null);
            }
            Drawable drawable2 = resources.getDrawable(R.drawable.sem_indexbar_bg_mtrl);
            this.mBgDrawableDefault = drawable2;
            drawable2.setColorFilter(this.mBgTintColor, PorterDuff.Mode.MULTIPLY);
            setBgRectParams();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getColorWithAlpha(int i, float f) {
            return Color.argb(Math.round(Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i));
        }

        public void setAlphabetArray(String[] strArr, int i, int i2) {
            if (strArr == null) {
                return;
            }
            this.mAlphabetArray = strArr;
            int length = strArr.length;
            this.mAlphabetSize = length;
            this.mAlphabetArrayFirstLetterIndex = i;
            this.mAlphabetArrayLastLetterIndex = i2;
            float f = this.mHeight / length;
            this.mItemHeight = f;
            this.mSeparatorHeight = Math.max(f, this.mContentMinHeight);
            this.mIsAlphabetInit = true;
            this.mIsSetDimensions = true;
        }

        private void adjustSeparatorHeight() {
            if (SemIndexScrollView.this.mNumberOfLanguages == 1) {
                this.mFirstLang.separatorHeight = (this.mHeight - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                this.mFirstLang.height = this.mHeight;
                return;
            }
            float f = this.mFirstLang.height;
            int i = this.mHeight;
            if (f > i * 0.6f) {
                this.mFirstLang.separatorHeight = ((i * 0.6f) - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                this.mSecondLang.separatorHeight = ((this.mHeight * 0.4f) - (this.mDotHeight * r0.dotCount)) / this.mSecondLang.indexCount;
                this.mFirstLang.height = this.mHeight * 0.6f;
                this.mSecondLang.height = this.mHeight * 0.4f;
            } else {
                float f2 = this.mFirstLang.height;
                int i2 = this.mHeight;
                if (f2 <= i2 * 0.5f) {
                    this.mFirstLang.separatorHeight = ((i2 * 0.5f) - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                    this.mSecondLang.separatorHeight = ((this.mHeight * 0.5f) - (this.mDotHeight * r0.dotCount)) / this.mSecondLang.indexCount;
                    LangAttributeValues langAttributeValues = this.mFirstLang;
                    float f3 = this.mHeight * 0.5f;
                    this.mSecondLang.height = f3;
                    langAttributeValues.height = f3;
                } else {
                    LangAttributeValues langAttributeValues2 = this.mFirstLang;
                    langAttributeValues2.separatorHeight = (langAttributeValues2.height - (this.mDotHeight * this.mFirstLang.dotCount)) / this.mFirstLang.indexCount;
                    LangAttributeValues langAttributeValues3 = this.mSecondLang;
                    langAttributeValues3.separatorHeight = (langAttributeValues3.height - (this.mDotHeight * this.mSecondLang.dotCount)) / this.mSecondLang.indexCount;
                }
            }
            if (this.mSecondLang.totalCount == 0) {
                this.mFirstLang.separatorHeight = (this.mHeight - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                this.mFirstLang.height = this.mHeight;
                this.mSecondLang.separatorHeight = 0.0f;
                this.mSecondLang.height = 0.0f;
                return;
            }
            if (this.mFirstLang.totalCount == 0) {
                this.mSecondLang.separatorHeight = (this.mHeight - (this.mDotHeight * r0.dotCount)) / this.mSecondLang.indexCount;
                this.mSecondLang.height = this.mHeight;
                this.mFirstLang.separatorHeight = 0.0f;
                this.mFirstLang.height = 0.0f;
            }
        }

        private void manageIndexScrollHeight() {
            if (!this.mIsAlphabetInit || SemIndexScrollView.this.mNumberOfLanguages > 2) {
                return;
            }
            if (this.mAlphabetArrayFirstLetterIndex == -1) {
                this.mAlphabetArrayFirstLetterIndex = 0;
            }
            if (this.mAlphabetArrayLastLetterIndex == -1) {
                this.mAlphabetArrayLastLetterIndex = 0;
            }
            this.mFirstLang.indexCount = this.mAlphabetSize - this.mAlphabetArrayLastLetterIndex;
            LangAttributeValues langAttributeValues = this.mFirstLang;
            langAttributeValues.totalCount = langAttributeValues.indexCount;
            LangAttributeValues langAttributeValues2 = this.mFirstLang;
            langAttributeValues2.alphabetArray = new String[langAttributeValues2.totalCount];
            this.mFirstLang.dotCount = 0;
            this.mSecondLang.indexCount = this.mAlphabetSize - this.mFirstLang.indexCount;
            LangAttributeValues langAttributeValues3 = this.mSecondLang;
            langAttributeValues3.totalCount = langAttributeValues3.indexCount;
            LangAttributeValues langAttributeValues4 = this.mSecondLang;
            langAttributeValues4.alphabetArray = new String[langAttributeValues4.totalCount];
            this.mSecondLang.dotCount = 0;
            this.mFirstLang.height = r0.indexCount * this.mContentMinHeight;
            this.mSecondLang.height = this.mHeight - this.mFirstLang.height;
            this.mAlphabetArrayToDraw = this.mAlphabetArray;
            this.mAlphabetToDrawSize = this.mAlphabetSize;
            adjustSeparatorHeight();
            int i = (this.mAlphabetArrayFirstLetterIndex <= 0 || !SemIndexScrollView.this.mIndexer.isUseDigitIndex()) ? 0 : 1;
            if (SemIndexScrollView.this.mNumberOfLanguages == 1) {
                calcDotPosition(this.mFirstLang, this.mAlphabetArrayFirstLetterIndex, 0, i);
            } else {
                calcDotPosition(this.mFirstLang, this.mAlphabetArrayFirstLetterIndex, 0, 0);
                calcDotPosition(this.mSecondLang, 0, this.mAlphabetSize - this.mAlphabetArrayLastLetterIndex, i);
            }
        }

        private void calcDotPosition(LangAttributeValues langAttributeValues, int i, int i2, int i3) {
            int i4;
            boolean z;
            int i5;
            int i6;
            int i7 = i2;
            int i8 = langAttributeValues.indexCount - i;
            int i9 = (langAttributeValues.totalCount + i7) - i7;
            int i10 = 0;
            System.arraycopy(this.mAlphabetArray, i7, langAttributeValues.alphabetArray, 0, i9);
            int i11 = i;
            int i12 = i3;
            boolean z2 = false;
            int i13 = 0;
            while (langAttributeValues.separatorHeight < this.mContentMinHeight && this.mAlphabetArrayToDraw.length > 0) {
                int i14 = i8 - i12;
                int i15 = (i14 / 2) - 1;
                if (langAttributeValues.dotCount < i15 && !z2) {
                    String[] strArr = new String[langAttributeValues.totalCount];
                    langAttributeValues.dotCount++;
                    langAttributeValues.indexCount--;
                    i13++;
                    int i16 = (i14 / (langAttributeValues.dotCount + 1)) + 1;
                    int i17 = 1;
                    if (langAttributeValues.dotCount == i15) {
                        i16 = 2;
                    }
                    int i18 = langAttributeValues.dotCount;
                    int i19 = i10;
                    while (i18 != 0) {
                        if (i18 != langAttributeValues.dotCount) {
                            i18 = langAttributeValues.dotCount;
                        }
                        int i20 = i8;
                        System.arraycopy(this.mAlphabetArray, i7, strArr, 0, i9);
                        int i21 = i17;
                        while (i21 < langAttributeValues.dotCount + 1) {
                            int i22 = (i16 * i21) - (i19 * i21);
                            if (i11 > i17) {
                                i22 += i11 - 1;
                            }
                            if (i22 <= 0 || i22 >= i14) {
                                if (i22 >= i14 && i18 > 0) {
                                    int i23 = i22 - (i16 / 2);
                                    if (i23 < i14) {
                                        strArr[i23] = MediaMetrics.SEPARATOR;
                                    } else {
                                        i19 = 1;
                                    }
                                }
                                i21++;
                                i17 = 1;
                            } else {
                                strArr[i22] = MediaMetrics.SEPARATOR;
                            }
                            i18--;
                            i21++;
                            i17 = 1;
                        }
                        i7 = i2;
                        i8 = i20;
                        i17 = 1;
                    }
                    i4 = i8;
                    langAttributeValues.alphabetArray = strArr;
                } else {
                    i4 = i8;
                    int i24 = (langAttributeValues.totalCount - i11) - i12;
                    if (i24 != 0) {
                        z = true;
                        if (i24 == 1) {
                            if (i11 != 0 && langAttributeValues.dotCount == 0) {
                                langAttributeValues.indexCount--;
                                langAttributeValues.dotCount++;
                            } else {
                                if (i11 != 0 && langAttributeValues.dotCount == 1) {
                                    langAttributeValues.dotCount--;
                                    langAttributeValues.totalCount--;
                                } else {
                                    langAttributeValues.indexCount--;
                                    langAttributeValues.totalCount--;
                                }
                                z = false;
                            }
                            i13++;
                            if (langAttributeValues.totalCount > 0 || langAttributeValues.dotCount < 0 || langAttributeValues.indexCount < 0) {
                                adjustSeparatorHeight();
                                return;
                            }
                            String[] strArr2 = new String[langAttributeValues.totalCount];
                            if (langAttributeValues.dotCount > 0) {
                                i5 = i13 / langAttributeValues.dotCount;
                                i6 = i13 % langAttributeValues.dotCount;
                            } else {
                                i5 = 0;
                                i6 = 0;
                            }
                            System.arraycopy(this.mAlphabetArray, 0, strArr2, 0, i11);
                            int i25 = langAttributeValues.totalCount - i12;
                            int i26 = i11;
                            int i27 = i26;
                            int i28 = i6;
                            int i29 = 0;
                            while (i26 < i25) {
                                String[] strArr3 = this.mAlphabetArray;
                                boolean z3 = z;
                                if (i27 < strArr3.length - i12) {
                                    if (!z3) {
                                        strArr2[i26] = strArr3[i27 + i2];
                                        i27++;
                                        if (i29 < langAttributeValues.dotCount) {
                                            z = true;
                                        }
                                    } else {
                                        strArr2[i26] = MediaMetrics.SEPARATOR;
                                        i29++;
                                        i27 += i5;
                                        if (i28 > 0) {
                                            i28--;
                                            i27++;
                                        }
                                        z = false;
                                    }
                                    i26++;
                                }
                                z = z3;
                                i26++;
                            }
                            if (i12 > 0) {
                                String[] strArr4 = this.mAlphabetArray;
                                z2 = true;
                                strArr2[i25] = strArr4[strArr4.length - 1];
                            } else {
                                z2 = true;
                            }
                            langAttributeValues.alphabetArray = strArr2;
                        } else if (i24 != 2) {
                            if (i24 == 3) {
                                langAttributeValues.indexCount--;
                                langAttributeValues.totalCount--;
                            } else if (((langAttributeValues.indexCount - langAttributeValues.dotCount) - i11) - i12 == 1) {
                                langAttributeValues.dotCount--;
                                langAttributeValues.totalCount--;
                            } else {
                                langAttributeValues.indexCount--;
                                langAttributeValues.totalCount--;
                            }
                            i13++;
                        } else {
                            langAttributeValues.dotCount--;
                            langAttributeValues.totalCount--;
                        }
                    } else if (i12 > 0) {
                        i12--;
                    } else if (i11 > 0) {
                        i11--;
                    }
                    z = false;
                    if (langAttributeValues.totalCount > 0) {
                    }
                    adjustSeparatorHeight();
                    return;
                }
                adjustSeparatorHeight();
                i7 = i2;
                i8 = i4;
                i10 = 0;
            }
        }

        public String getIndexByPosition(int i, int i2) {
            int i3;
            int i4;
            Rect rect = this.mBgRect;
            if (rect == null || !this.mIsAlphabetInit) {
                return "";
            }
            if ((this.mPosition == 0 && i < rect.left - this.mAdditionalSpace) || (this.mPosition == 1 && i > this.mBgRect.right + this.mAdditionalSpace)) {
                return "";
            }
            if (i >= this.mBgRect.left - this.mAdditionalSpace && i <= this.mBgRect.right + this.mAdditionalSpace) {
                if (isInSelectedIndexRect(i2)) {
                    String[] strArr = this.mAlphabetArrayToDraw;
                    return (strArr == null || (i4 = this.mSelectedIndex) < 0 || i4 >= strArr.length) ? "" : getIndexByY(i2);
                }
                return getIndexByY(i2);
            }
            int i5 = this.mPosition;
            if (i5 == 0 && i >= this.mWidthShift + this.mItemWidth + this.mItemWidthGap) {
                return null;
            }
            if (i5 == 1 && i <= (this.mWidth - this.mWidthShift) - (this.mItemWidth + this.mItemWidthGap)) {
                return null;
            }
            if (isInSelectedIndexRect(i2)) {
                String[] strArr2 = this.mAlphabetArrayToDraw;
                return (strArr2 == null || (i3 = this.mSelectedIndex) < 0 || i3 >= strArr2.length) ? "" : strArr2[i3];
            }
            return getIndexByY(i2);
        }

        private int getIndex(int i) {
            float f;
            float f2 = this.mAlphabetSize - this.mAlphabetArrayLastLetterIndex;
            if (i < this.mScrollTop + this.mScrollTopMargin + this.mFirstLang.height) {
                f = ((i - this.mScrollTop) - this.mScrollTopMargin) / (this.mFirstLang.height / f2);
            } else {
                f = ((int) ((((i - this.mScrollTop) - this.mScrollTopMargin) - this.mFirstLang.height) / (this.mSecondLang.height / this.mAlphabetArrayLastLetterIndex))) + f2;
            }
            int i2 = (int) f;
            if (i2 < 0) {
                return 0;
            }
            int i3 = this.mAlphabetToDrawSize;
            return i2 >= i3 ? i3 - 1 : i2;
        }

        private String getIndexByY(int i) {
            int i2;
            if (i > this.mBgRect.top - this.mAdditionalSpace && i < this.mBgRect.bottom + this.mAdditionalSpace) {
                if (i < this.mBgRect.top) {
                    this.mSelectedIndex = 0;
                } else if (i > this.mBgRect.bottom) {
                    this.mSelectedIndex = this.mAlphabetToDrawSize - 1;
                } else {
                    int index = getIndex(i);
                    this.mSelectedIndex = index;
                    if (index == this.mAlphabetToDrawSize) {
                        this.mSelectedIndex = index - 1;
                    }
                }
                int i3 = this.mSelectedIndex;
                int i4 = this.mAlphabetToDrawSize;
                if (i3 == i4 || i3 == i4 + 1) {
                    this.mSelectedIndex = i4 - 1;
                }
                String[] strArr = this.mAlphabetArrayToDraw;
                if (strArr != null && (i2 = this.mSelectedIndex) > -1 && i2 <= i4) {
                    return strArr[i2];
                }
            }
            return "";
        }

        private boolean isInSelectedIndexRect(int i) {
            int i2 = this.mSelectedIndex;
            if (i2 != -1 && i2 < this.mAlphabetToDrawSize) {
                int i3 = this.mScrollTop;
                int i4 = this.mScrollTopMargin;
                float f = this.mSeparatorHeight;
                if (i >= ((int) (i3 + i4 + (i2 * f))) && i <= ((int) (i3 + i4 + (f * (i2 + 1))))) {
                    return true;
                }
            }
            return false;
        }

        public void resetSelectedIndex() {
            this.mSelectedIndex = -1;
        }

        public void draw(Canvas canvas) {
            if (this.mIsAlphabetInit) {
                drawScroll(canvas);
            }
        }

        public void drawScroll(Canvas canvas) {
            drawBgRectangle(canvas);
            drawAlphabetCharacters(canvas);
            int i = this.mSelectedIndex;
            if (i < 0 || i >= this.mAlphabetSize) {
                if (SemIndexScrollView.this.mIndexScrollPreview != null) {
                    SemIndexScrollView.this.mIndexScrollPreview.close();
                }
                if (SemIndexScrollView.this.mOnIndexBarEventListener != null) {
                    SemIndexScrollView.this.mOnIndexBarEventListener.onReleased(0.0f);
                }
            }
        }

        public void setEffectText(String str) {
            this.mBigText = str;
        }

        public void drawEffect(float f) {
            int i = this.mSelectedIndex;
            if (i != -1) {
                String str = this.mAlphabetArrayToDraw[i];
                this.mSmallText = str;
                this.mPaint.getTextBounds(str, 0, str.length(), this.mTextBounds);
                int i2 = this.mScrollTopMargin;
                float f2 = this.mPreviewLimitY;
                float f3 = this.mIndexScrollPreviewRadius;
                float f4 = i2 + f2 + f3;
                int i3 = this.mScreenHeight;
                int i4 = this.mScrollBottomMargin;
                float f5 = ((i3 - i4) - f2) - f3;
                if (i3 <= (f3 * 2.0f) + f2 + i2 + i4) {
                    f4 = this.mScrollTop + i2 + ((float) (this.mFirstLang.separatorHeight * 0.5d));
                    f5 = ((((this.mScrollTop + this.mScrollTopMargin) - this.mScrollBottomMargin) + this.mFirstLang.height) + this.mSecondLang.height) - ((float) (this.mFirstLang.separatorHeight * 0.5d));
                }
                if (f <= f4 || f >= f5) {
                    f = f <= f4 ? f4 : f >= f5 ? f5 : -9999.0f;
                }
                if (f != SemIndexScrollView.OUT_OF_BOUNDARY) {
                    SemIndexScrollView.this.mIndexScrollPreview.open(f, this.mBigText);
                    if (SemIndexScrollView.this.mOnIndexBarEventListener != null) {
                        SemIndexScrollView.this.mOnIndexBarEventListener.onPressed(f);
                    }
                }
            }
        }

        private void allocateBgRectangle() {
            int i;
            int i2;
            if (this.mPosition == 1) {
                i2 = this.mWidth - this.mWidthShift;
                i = i2 - this.mBgRectWidth;
            } else {
                i = this.mWidthShift;
                i2 = this.mBgRectWidth + i;
            }
            Rect rect = this.mBgRect;
            if (rect == null) {
                int i3 = this.mScrollTop;
                int i4 = this.mScrollTopMargin;
                int i5 = this.mContentPadding;
                this.mBgRect = new Rect(i, (i3 + i4) - i5, i2, this.mHeight + i3 + i4 + i5);
            } else {
                int i6 = this.mScrollTop;
                int i7 = this.mScrollTopMargin;
                int i8 = this.mContentPadding;
                rect.set(i, (i6 + i7) - i8, i2, this.mHeight + i6 + i7 + i8);
            }
            this.mScrollThumbBgRectHeight = ((int) (this.mContentMinHeight * 3.0f)) + this.mScrollThumbAdditionalHeight;
            int i9 = this.mScrollThumbBgRectPadding;
            int i10 = i + i9;
            int i11 = i2 - i9;
            int i12 = (int) (SemIndexScrollView.this.mTouchY - (this.mScrollThumbBgRectHeight / 2));
            int i13 = (int) (SemIndexScrollView.this.mTouchY + (this.mScrollThumbBgRectHeight / 2));
            if ((i12 < this.mBgRect.top + this.mScrollThumbBgRectPadding && i13 > this.mBgRect.bottom - this.mScrollThumbBgRectPadding) || this.mScrollThumbBgRectHeight >= (this.mBgRect.bottom - this.mBgRect.top) - (this.mScrollThumbBgRectPadding * 2)) {
                i12 = this.mBgRect.top + this.mScrollThumbBgRectPadding;
                i13 = this.mBgRect.bottom - this.mScrollThumbBgRectPadding;
            } else if (i12 < this.mBgRect.top + this.mScrollThumbBgRectPadding) {
                i12 = this.mBgRect.top + this.mScrollThumbBgRectPadding;
                i13 = this.mScrollThumbBgRectHeight + i12;
            } else if (i13 > this.mBgRect.bottom - this.mScrollThumbBgRectPadding) {
                i13 = this.mBgRect.bottom - this.mScrollThumbBgRectPadding;
                i12 = i13 - this.mScrollThumbBgRectHeight;
            }
            Rect rect2 = this.mScrollThumbBgRect;
            if (rect2 == null) {
                this.mScrollThumbBgRect = new Rect(i10, i12, i11, i13);
            } else {
                rect2.set(i10, i12, i11, i13);
            }
        }

        private void drawBgRectangle(Canvas canvas) {
            if (!this.mBgRectParamsSet) {
                setBgRectParams();
                this.mBgRectParamsSet = true;
            }
            this.mBgDrawableDefault.draw(canvas);
            if (SemIndexScrollView.this.mTouchY != SemIndexScrollView.OUT_OF_BOUNDARY) {
                this.mScrollThumbBgDrawable.draw(canvas);
            }
        }

        private void setBgRectParams() {
            allocateBgRectangle();
            this.mBgDrawableDefault.setBounds(this.mBgRect);
            this.mScrollThumbBgDrawable.setBounds(this.mScrollThumbBgRect);
        }

        private void drawAlphabetCharacters(Canvas canvas) {
            String str;
            float f;
            float f2;
            this.mPaint.setColor(this.mTextColorDimmed);
            this.mPaint.setTextSize(this.mTextSize);
            if (this.mAlphabetArrayToDraw == null || this.mFirstLang.totalCount == 0) {
                return;
            }
            float f3 = this.mScrollTop + this.mScrollTopMargin;
            int i = this.mFirstLang.totalCount + this.mSecondLang.totalCount;
            for (int i2 = 0; i2 < i; i2++) {
                if (i2 < this.mFirstLang.totalCount) {
                    str = this.mFirstLang.alphabetArray[i2];
                    f = this.mFirstLang.separatorHeight;
                } else {
                    str = this.mSecondLang.alphabetArray[i2 - this.mFirstLang.totalCount];
                    f = this.mSecondLang.separatorHeight;
                }
                this.mPaint.getTextBounds(str, 0, str.length(), this.mTextBounds);
                float centerX = this.mBgRect.centerX() - (this.mPaint.measureText(str) * 0.5f);
                if (MediaMetrics.SEPARATOR.equals(str)) {
                    f2 = ((this.mDotHeight * 0.5f) - (this.mTextBounds.top * 0.5f)) + f3;
                    f3 += this.mDotHeight;
                } else {
                    float f4 = ((f * 0.5f) - (this.mTextBounds.top * 0.5f)) + f3;
                    f3 += f;
                    f2 = f4;
                }
                canvas.drawText(str, centerX, f2, this.mPaint);
            }
        }
    }

    class IndexScrollPreview extends View {
        private static final int FASTSCROLL_VIBRATE_INDEX = 26;
        private boolean mIsOpen;
        private float mPreviewCenterMargin;
        private float mPreviewCenterX;
        private float mPreviewCenterY;
        private float mPreviewRadius;
        private String mPreviewText;
        private Paint mShapePaint;
        private Rect mTextBounds;
        private Paint mTextPaint;
        private int mTextSize;
        private int mTextWidhtLimit;

        public IndexScrollPreview(Context context) {
            super(context);
            this.mIsOpen = false;
            init(context);
        }

        private void init(Context context) {
            Resources resources = context.getResources();
            Paint paint = new Paint();
            this.mShapePaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mShapePaint.setAntiAlias(true);
            this.mTextSize = (int) resources.getDimension(R.dimen.sem_index_scroll_preview_text_size);
            this.mTextWidhtLimit = (int) resources.getDimension(R.dimen.sem_index_scroll_preview_text_width_limit);
            Paint paint2 = new Paint();
            this.mTextPaint = paint2;
            paint2.setAntiAlias(true);
            this.mTextPaint.setTypeface(SemIndexScrollView.this.mSECRobotoLightRegularFont);
            this.mTextPaint.setTextAlign(Paint.Align.CENTER);
            this.mTextPaint.setTextSize(this.mTextSize);
            this.mTextPaint.setColor(resources.getColor(R.color.sem_index_scroll_preview_text_color, null));
            this.mTextBounds = new Rect();
            this.mPreviewRadius = resources.getDimension(R.dimen.sem_index_scroll_preview_radius);
            this.mPreviewCenterMargin = resources.getDimension(R.dimen.sem_index_scroll_preview_center_margin);
            this.mIsOpen = false;
        }

        public void setLayout(int i, int i2, int i3, int i4) {
            layout(i, i2, i3, i4);
            if (SemIndexScrollView.this.mIndexBarGravity == 0) {
                this.mPreviewCenterX = this.mPreviewCenterMargin;
            } else {
                this.mPreviewCenterX = i3 - this.mPreviewCenterMargin;
            }
        }

        @Override // android.view.View
        public void setBackgroundColor(int i) {
            this.mShapePaint.setColor(i);
        }

        public void setTextColor(int i) {
            this.mTextPaint.setColor(i);
        }

        public void open(float f, String str) {
            int i = this.mTextSize;
            this.mPreviewCenterY = f;
            if (!this.mIsOpen || !this.mPreviewText.equals(str)) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(26));
            }
            this.mPreviewText = str;
            this.mTextPaint.setTextSize(i);
            while (this.mTextPaint.measureText(str) > this.mTextWidhtLimit) {
                i--;
                this.mTextPaint.setTextSize(i);
            }
            if (this.mIsOpen) {
                return;
            }
            startAnimation();
            this.mIsOpen = true;
        }

        public void close() {
            long currentTimeMillis = System.currentTimeMillis() - SemIndexScrollView.this.mStartTouchDown;
            removeCallbacks(SemIndexScrollView.this.mPreviewDelayRunnable);
            if (currentTimeMillis <= 100) {
                postDelayed(SemIndexScrollView.this.mPreviewDelayRunnable, 100L);
            } else {
                fadeOutAnimation();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fadeOutAnimation() {
            if (this.mIsOpen) {
                startAnimation();
                this.mIsOpen = false;
            }
        }

        public void startAnimation() {
            ObjectAnimator ofFloat;
            if (!this.mIsOpen) {
                ofFloat = ObjectAnimator.ofFloat(SemIndexScrollView.this.mIndexScrollPreview, "alpha", 0.0f, 1.0f);
            } else {
                ofFloat = ObjectAnimator.ofFloat(SemIndexScrollView.this.mIndexScrollPreview, "alpha", 1.0f, 0.0f);
            }
            ofFloat.setDuration(167L);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(ofFloat);
            animatorSet.start();
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (this.mIsOpen) {
                canvas.drawCircle(this.mPreviewCenterX, this.mPreviewCenterY, this.mPreviewRadius, this.mShapePaint);
                this.mTextPaint.getTextBounds(this.mPreviewText, 0, r1.length() - 1, this.mTextBounds);
                canvas.drawText(this.mPreviewText, this.mPreviewCenterX, this.mPreviewCenterY - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f), this.mTextPaint);
            }
        }
    }
}
