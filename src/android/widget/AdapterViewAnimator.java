package android.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes5.dex */
public abstract class AdapterViewAnimator extends AdapterView<Adapter> implements RemoteViewsAdapter.RemoteAdapterConnectionCallback, Advanceable {
    private static final String APPWIDGET_CURRENT_DISPLAYED_POSITION_ACTION = "android.widget.AdapterViewAnimator.APPWIDGET_CURRENT_DISPLAYED_POSITION";
    private static final String APPWIDGET_EXTRA_CURRENT_DISPLAYED_POSITION = "appwidgetCurrentDisplayedPosition";
    private static final int APP_WIDGET_BROADCAST_CURRENT_DISPLAYED_POSITION_TYPE = 1;
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final String TAG = "RemoteViewAnimator";
    static final int TOUCH_MODE_DOWN_IN_CURRENT_VIEW = 1;
    static final int TOUCH_MODE_HANDLED = 2;
    static final int TOUCH_MODE_NONE = 0;
    int mActiveOffset;
    Adapter mAdapter;
    boolean mAnimateFirstTime;
    private String mAppWidgetGetCurrentDisplayedPosition;
    int mCurrentWindowEnd;
    int mCurrentWindowStart;
    int mCurrentWindowStartUnbounded;
    AdapterView<Adapter>.AdapterDataSetObserver mDataSetObserver;
    boolean mDeferNotifyDataSetChanged;
    private boolean mDeferSetDisplayedChild;
    private int mDeferSetDisplayedChildIndex;
    boolean mFirstTime;
    ObjectAnimator mInAnimation;
    private long mInAnimationDuration;
    boolean mLoopViews;
    int mMaxNumActiveViews;
    ObjectAnimator mOutAnimation;
    private long mOutAnimationDuration;
    private Runnable mPendingCheckForTap;
    ArrayList<Integer> mPreviousViews;
    int mReferenceChildHeight;
    int mReferenceChildWidth;
    RemoteViewsAdapter mRemoteViewsAdapter;
    private int mRestoreWhichChild;
    private int mTouchMode;
    HashMap<Integer, ViewAndMetaData> mViewsMap;
    int mWhichChild;

    void applyTransformForChildAtIndex(View view, int i) {
    }

    @Override // android.widget.Advanceable
    public void fyiWillBeAdvancedByHostKThx() {
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void onRemoteAdapterDisconnected() {
    }

    public AdapterViewAnimator(Context context) {
        this(context, null);
    }

    public AdapterViewAnimator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterViewAnimator(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AdapterViewAnimator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWhichChild = 0;
        this.mRestoreWhichChild = -1;
        this.mAnimateFirstTime = true;
        this.mActiveOffset = 0;
        this.mMaxNumActiveViews = 1;
        this.mViewsMap = new HashMap<>();
        this.mCurrentWindowStart = 0;
        this.mCurrentWindowEnd = -1;
        this.mCurrentWindowStartUnbounded = 0;
        this.mDeferNotifyDataSetChanged = false;
        this.mFirstTime = true;
        this.mLoopViews = true;
        this.mReferenceChildWidth = -1;
        this.mReferenceChildHeight = -1;
        this.mTouchMode = 0;
        this.mInAnimationDuration = 200L;
        this.mOutAnimationDuration = 200L;
        this.mDeferSetDisplayedChild = false;
        this.mDeferSetDisplayedChildIndex = 0;
        this.mAppWidgetGetCurrentDisplayedPosition = "";
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AdapterViewAnimator, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.AdapterViewAnimator, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInAnimation(context, resourceId);
        } else {
            setInAnimation(getDefaultInAnimation());
        }
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 > 0) {
            setOutAnimation(context, resourceId2);
        } else {
            setOutAnimation(getDefaultOutAnimation());
        }
        setAnimateFirstView(typedArrayObtainStyledAttributes.getBoolean(2, true));
        this.mLoopViews = typedArrayObtainStyledAttributes.getBoolean(3, false);
        typedArrayObtainStyledAttributes.recycle();
        initViewAnimator();
    }

    private void initViewAnimator() {
        this.mPreviousViews = new ArrayList<>();
    }

    class ViewAndMetaData {
        int adapterPosition;
        long itemId;
        int relativeIndex;
        View view;

        ViewAndMetaData(AdapterViewAnimator adapterViewAnimator, View view, int i, int i2, long j) {
            this.view = view;
            this.relativeIndex = i;
            this.adapterPosition = i2;
            this.itemId = j;
        }
    }

    void configureViewAnimator(int i, int i2) {
        this.mMaxNumActiveViews = i;
        this.mActiveOffset = i2;
        this.mPreviousViews.clear();
        this.mViewsMap.clear();
        removeAllViewsInLayout();
        this.mCurrentWindowStart = 0;
        this.mCurrentWindowEnd = -1;
    }

    void transformViewForTransition(int i, int i2, View view, boolean z) {
        if (z) {
            if (this.mInAnimation.getDuration() == 0) {
                this.mInAnimation.setDuration(this.mInAnimationDuration);
            }
            if (this.mOutAnimation.getDuration() == 0) {
                this.mOutAnimation.setDuration(this.mOutAnimationDuration);
            }
        } else {
            if (this.mInAnimation.getDuration() != 0) {
                this.mInAnimationDuration = this.mInAnimation.getDuration();
            }
            if (this.mOutAnimation.getDuration() != 0) {
                this.mOutAnimationDuration = this.mOutAnimation.getDuration();
            }
            this.mInAnimation.setDuration(0L);
            this.mOutAnimation.setDuration(0L);
        }
        if (i == -1) {
            this.mInAnimation.setTarget(view);
            this.mInAnimation.start();
        } else if (i2 == -1) {
            this.mOutAnimation.setTarget(view);
            this.mOutAnimation.start();
        }
    }

    ObjectAnimator getDefaultInAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
        objectAnimatorOfFloat.setDuration(200L);
        return objectAnimatorOfFloat;
    }

    ObjectAnimator getDefaultOutAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(200L);
        return objectAnimatorOfFloat;
    }

    @RemotableViewMethod
    public void setDisplayedChild(int i) throws Resources.NotFoundException {
        if (this.mAdapter == null) {
            this.mDeferSetDisplayedChild = true;
            this.mDeferSetDisplayedChildIndex = i;
        }
        setDisplayedChild(i, true);
    }

    @RemotableViewMethod
    public void semSetDisplayedChild(int i) throws Resources.NotFoundException {
        setDisplayedChild(i, false);
    }

    private void setDisplayedChild(int i, boolean z) throws Resources.NotFoundException {
        if (this.mAdapter != null) {
            this.mWhichChild = i;
            if (i >= getWindowSize()) {
                this.mWhichChild = this.mLoopViews ? 0 : getWindowSize() - 1;
            } else if (i < 0) {
                this.mWhichChild = this.mLoopViews ? getWindowSize() - 1 : 0;
            }
            boolean z2 = getFocusedChild() != null;
            showOnly(this.mWhichChild, z);
            if (z2) {
                requestFocus(2);
            }
        }
        semSendBroadcastPosition(i, 1);
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    public void showNext() throws Resources.NotFoundException {
        setDisplayedChild(this.mWhichChild + 1);
    }

    public void showPrevious() throws Resources.NotFoundException {
        setDisplayedChild(this.mWhichChild - 1);
    }

    int modulo(int i, int i2) {
        if (i2 > 0) {
            return ((i % i2) + i2) % i2;
        }
        return 0;
    }

    View getViewAtRelativeIndex(int i) {
        if (i < 0 || i > getNumActiveViews() - 1 || this.mAdapter == null) {
            return null;
        }
        int iModulo = modulo(this.mCurrentWindowStartUnbounded + i, getWindowSize());
        if (this.mViewsMap.get(Integer.valueOf(iModulo)) != null) {
            return this.mViewsMap.get(Integer.valueOf(iModulo)).view;
        }
        return null;
    }

    int getNumActiveViews() {
        if (this.mAdapter != null) {
            return Math.min(getCount() + 1, this.mMaxNumActiveViews);
        }
        return this.mMaxNumActiveViews;
    }

    int getWindowSize() {
        if (this.mAdapter == null) {
            return 0;
        }
        int count = getCount();
        return (count > getNumActiveViews() || !this.mLoopViews) ? count : count * this.mMaxNumActiveViews;
    }

    private ViewAndMetaData getMetaDataForChild(View view) {
        for (ViewAndMetaData viewAndMetaData : this.mViewsMap.values()) {
            if (viewAndMetaData.view == view) {
                return viewAndMetaData;
            }
        }
        return null;
    }

    ViewGroup.LayoutParams createOrReuseLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams != null ? layoutParams : new ViewGroup.LayoutParams(0, 0);
    }

    void refreshChildren() {
        int count = this.mAdapter == null ? 0 : getCount();
        for (int i = this.mCurrentWindowStart; i <= this.mCurrentWindowEnd; i++) {
            int iModulo = modulo(i, getWindowSize());
            View view = null;
            if (i < count) {
                view = this.mAdapter.getView(modulo(i, count), null, this);
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
            }
            if (this.mViewsMap.containsKey(Integer.valueOf(iModulo))) {
                FrameLayout frameLayout = (FrameLayout) this.mViewsMap.get(Integer.valueOf(iModulo)).view;
                frameLayout.removeAllViewsInLayout();
                if (view != null) {
                    frameLayout.addView(view);
                }
            }
        }
    }

    FrameLayout getFrameForChild() {
        return new FrameLayout(this.mContext);
    }

    void showOnly(int i, boolean z) throws Resources.NotFoundException {
        int count;
        int i2;
        int i3;
        int i4;
        int i5;
        if (this.mAdapter == null || (count = getCount()) == 0) {
            return;
        }
        int i6 = 0;
        while (true) {
            i2 = -1;
            if (i6 >= this.mPreviousViews.size()) {
                break;
            }
            View view = this.mViewsMap.get(this.mPreviousViews.get(i6)).view;
            this.mViewsMap.remove(this.mPreviousViews.get(i6));
            view.clearAnimation();
            if (view instanceof ViewGroup) {
                ((ViewGroup) view).removeAllViewsInLayout();
            }
            applyTransformForChildAtIndex(view, -1);
            removeViewInLayout(view);
            i6++;
        }
        this.mPreviousViews.clear();
        int i7 = i - this.mActiveOffset;
        int numActiveViews = (getNumActiveViews() + i7) - 1;
        int iMax = Math.max(0, i7);
        int iMin = Math.min(count - 1, numActiveViews);
        if (this.mLoopViews) {
            i4 = numActiveViews;
            i3 = i7;
        } else {
            i3 = iMax;
            i4 = iMin;
        }
        int iModulo = modulo(i3, getWindowSize());
        int iModulo2 = modulo(i4, getWindowSize());
        boolean z2 = iModulo > iModulo2;
        for (Integer num : this.mViewsMap.keySet()) {
            if ((!z2 && (num.intValue() < iModulo || num.intValue() > iModulo2)) || (z2 && num.intValue() > iModulo2 && num.intValue() < iModulo)) {
                View view2 = this.mViewsMap.get(num).view;
                int i8 = this.mViewsMap.get(num).relativeIndex;
                this.mPreviousViews.add(num);
                transformViewForTransition(i8, -1, view2, z);
            }
        }
        if (i3 != this.mCurrentWindowStart || i4 != this.mCurrentWindowEnd || i7 != this.mCurrentWindowStartUnbounded) {
            int i9 = i3;
            while (i9 <= i4) {
                int iModulo3 = modulo(i9, getWindowSize());
                int i10 = this.mViewsMap.containsKey(Integer.valueOf(iModulo3)) ? this.mViewsMap.get(Integer.valueOf(iModulo3)).relativeIndex : i2;
                int i11 = i9 - i7;
                if (this.mViewsMap.containsKey(Integer.valueOf(iModulo3)) && !this.mPreviousViews.contains(Integer.valueOf(iModulo3))) {
                    View view3 = this.mViewsMap.get(Integer.valueOf(iModulo3)).view;
                    this.mViewsMap.get(Integer.valueOf(iModulo3)).relativeIndex = i11;
                    applyTransformForChildAtIndex(view3, i11);
                    transformViewForTransition(i10, i11, view3, z);
                    i5 = i2;
                } else {
                    int iModulo4 = modulo(i9, count);
                    View view4 = this.mAdapter.getView(iModulo4, null, this);
                    long itemId = this.mAdapter.getItemId(iModulo4);
                    FrameLayout frameForChild = getFrameForChild();
                    if (view4 != null) {
                        frameForChild.addView(view4);
                    }
                    this.mViewsMap.put(Integer.valueOf(iModulo3), new ViewAndMetaData(this, frameForChild, i11, iModulo4, itemId));
                    addChild(frameForChild);
                    applyTransformForChildAtIndex(frameForChild, i11);
                    i5 = -1;
                    transformViewForTransition(-1, i11, frameForChild, z);
                }
                this.mViewsMap.get(Integer.valueOf(iModulo3)).view.bringToFront();
                i9++;
                i2 = i5;
            }
            this.mCurrentWindowStart = i3;
            this.mCurrentWindowEnd = i4;
            this.mCurrentWindowStartUnbounded = i7;
            if (this.mRemoteViewsAdapter != null) {
                this.mRemoteViewsAdapter.setVisibleRangeHint(modulo(i3, count), modulo(this.mCurrentWindowEnd, count));
            }
        }
        requestLayout();
        invalidate();
    }

    private void addChild(View view) {
        addViewInLayout(view, -1, createOrReuseLayoutParams(view));
        if (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            this.mReferenceChildWidth = view.getMeasuredWidth();
            this.mReferenceChildHeight = view.getMeasuredHeight();
        }
    }

    void showTapFeedback(View view) {
        view.setPressed(true);
    }

    void hideTapFeedback(View view) {
        view.setPressed(false);
    }

    void cancelHandleClick() {
        View currentView = getCurrentView();
        if (currentView != null) {
            hideTapFeedback(currentView);
        }
        this.mTouchMode = 0;
    }

    final class CheckForTap implements Runnable {
        CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AdapterViewAnimator.this.mTouchMode == 1) {
                AdapterViewAnimator.this.showTapFeedback(AdapterViewAnimator.this.getCurrentView());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0054  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        boolean z = true;
        if (action == 0) {
            View currentView = getCurrentView();
            if (currentView != null && isTransformedTouchPointInView(motionEvent.getX(), motionEvent.getY(), currentView, null)) {
                if (this.mPendingCheckForTap == null) {
                    this.mPendingCheckForTap = new CheckForTap();
                }
                this.mTouchMode = 1;
                postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
            }
        } else {
            if (action == 1) {
                if (this.mTouchMode == 1) {
                    final View currentView2 = getCurrentView();
                    final ViewAndMetaData metaDataForChild = getMetaDataForChild(currentView2);
                    if (currentView2 == null || !isTransformedTouchPointInView(motionEvent.getX(), motionEvent.getY(), currentView2, null)) {
                        z = false;
                    } else {
                        Handler handler = getHandler();
                        if (handler != null) {
                            handler.removeCallbacks(this.mPendingCheckForTap);
                        }
                        showTapFeedback(currentView2);
                        postDelayed(new Runnable() { // from class: android.widget.AdapterViewAnimator.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AdapterViewAnimator.this.hideTapFeedback(currentView2);
                                AdapterViewAnimator.this.post(new Runnable() { // from class: android.widget.AdapterViewAnimator.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (metaDataForChild != null) {
                                            AdapterViewAnimator.this.performItemClick(currentView2, metaDataForChild.adapterPosition, metaDataForChild.itemId);
                                        } else {
                                            AdapterViewAnimator.this.performItemClick(currentView2, 0, 0L);
                                        }
                                    }
                                });
                            }
                        }, ViewConfiguration.getPressedStateDuration());
                    }
                }
                this.mTouchMode = 0;
                return z;
            }
            if (action == 3) {
                View currentView3 = getCurrentView();
                if (currentView3 != null) {
                    hideTapFeedback(currentView3);
                }
                this.mTouchMode = 0;
                return false;
            }
        }
        return false;
    }

    private void measureChildren() {
        int childCount = getChildCount();
        int measuredWidth = (getMeasuredWidth() - this.mPaddingLeft) - this.mPaddingRight;
        int measuredHeight = (getMeasuredHeight() - this.mPaddingTop) - this.mPaddingBottom;
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        boolean z = (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) ? false : true;
        if (mode2 == 0) {
            size2 = z ? this.mReferenceChildHeight + this.mPaddingTop + this.mPaddingBottom : 0;
        } else if (mode2 == Integer.MIN_VALUE && z) {
            int i4 = this.mReferenceChildHeight + this.mPaddingTop + this.mPaddingBottom;
            size2 = i4 > size2 ? size2 | 16777216 : i4;
        }
        if (mode == 0) {
            if (z) {
                i3 = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight;
            } else {
                size = 0;
            }
        } else if (mode2 == Integer.MIN_VALUE && z) {
            i3 = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight;
            size = i3 > size ? size | 16777216 : i3;
        }
        setMeasuredDimension(size, size2);
        measureChildren();
    }

    void checkForAndHandleDataChanged() {
        if (this.mDataChanged) {
            post(new Runnable() { // from class: android.widget.AdapterViewAnimator.2
                @Override // java.lang.Runnable
                public void run() throws Resources.NotFoundException {
                    AdapterViewAnimator.this.handleDataChanged();
                    if (AdapterViewAnimator.this.mWhichChild >= AdapterViewAnimator.this.getWindowSize()) {
                        AdapterViewAnimator.this.mWhichChild = 0;
                        AdapterViewAnimator adapterViewAnimator = AdapterViewAnimator.this;
                        adapterViewAnimator.showOnly(adapterViewAnimator.mWhichChild, false);
                    } else if (AdapterViewAnimator.this.mOldItemCount != AdapterViewAnimator.this.getCount()) {
                        AdapterViewAnimator adapterViewAnimator2 = AdapterViewAnimator.this;
                        adapterViewAnimator2.showOnly(adapterViewAnimator2.mWhichChild, false);
                    }
                    AdapterViewAnimator.this.refreshChildren();
                    AdapterViewAnimator.this.requestLayout();
                }
            });
        }
        this.mDataChanged = false;
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        checkForAndHandleDataChanged();
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            childAt.layout(this.mPaddingLeft, this.mPaddingTop, this.mPaddingLeft + childAt.getMeasuredWidth(), this.mPaddingTop + childAt.getMeasuredHeight());
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.AdapterViewAnimator.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int whichChild;

        SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.whichChild = i;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.whichChild = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.whichChild);
        }

        public String toString() {
            return "AdapterViewAnimator.SavedState{ whichChild = " + this.whichChild + " }";
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.saveRemoteViewsCache();
        }
        return new SavedState(parcelableOnSaveInstanceState, this.mWhichChild);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) throws Resources.NotFoundException {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.whichChild;
        this.mWhichChild = i;
        if (this.mRemoteViewsAdapter != null && this.mAdapter == null) {
            this.mRestoreWhichChild = i;
        } else {
            setDisplayedChild(i, false);
        }
    }

    public View getCurrentView() {
        return getViewAtRelativeIndex(this.mActiveOffset);
    }

    public ObjectAnimator getInAnimation() {
        return this.mInAnimation;
    }

    public void setInAnimation(ObjectAnimator objectAnimator) {
        this.mInAnimation = objectAnimator;
    }

    public ObjectAnimator getOutAnimation() {
        return this.mOutAnimation;
    }

    public void setOutAnimation(ObjectAnimator objectAnimator) {
        this.mOutAnimation = objectAnimator;
    }

    public void setInAnimation(Context context, int i) {
        setInAnimation((ObjectAnimator) AnimatorInflater.loadAnimator(context, i));
    }

    public void setOutAnimation(Context context, int i) {
        setOutAnimation((ObjectAnimator) AnimatorInflater.loadAnimator(context, i));
    }

    public void setAnimateFirstView(boolean z) {
        this.mAnimateFirstTime = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        return getCurrentView() != null ? getCurrentView().getBaseline() : super.getBaseline();
    }

    @Override // android.widget.AdapterView
    public Adapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AdapterView
    public void setAdapter(Adapter adapter) throws Resources.NotFoundException {
        AdapterView<Adapter>.AdapterDataSetObserver adapterDataSetObserver;
        Adapter adapter2 = this.mAdapter;
        if (adapter2 != null && (adapterDataSetObserver = this.mDataSetObserver) != null) {
            adapter2.unregisterDataSetObserver(adapterDataSetObserver);
        }
        this.mAdapter = adapter;
        checkFocus();
        if (this.mAdapter != null) {
            AdapterView<Adapter>.AdapterDataSetObserver adapterDataSetObserver2 = new AdapterView.AdapterDataSetObserver();
            this.mDataSetObserver = adapterDataSetObserver2;
            this.mAdapter.registerDataSetObserver(adapterDataSetObserver2);
            this.mItemCount = this.mAdapter.getCount();
        }
        setFocusable(true);
        this.mWhichChild = 0;
        showOnly(0, false);
    }

    @RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(Intent intent) {
        setRemoteViewsAdapter(intent, false);
    }

    public Runnable setRemoteViewsAdapterAsync(Intent intent) {
        return new RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void setRemoteViewsAdapter(Intent intent, boolean z) {
        if (this.mRemoteViewsAdapter == null || !new Intent.FilterComparison(intent).equals(new Intent.FilterComparison(this.mRemoteViewsAdapter.getRemoteViewsServiceIntent()))) {
            this.mDeferNotifyDataSetChanged = false;
            RemoteViewsAdapter remoteViewsAdapter = new RemoteViewsAdapter(getContext(), intent, this, z);
            this.mRemoteViewsAdapter = remoteViewsAdapter;
            if (remoteViewsAdapter.isDataReady()) {
                setAdapter(this.mRemoteViewsAdapter);
            }
        }
    }

    public void setRemoteViewsOnClickHandler(RemoteViews.InteractionHandler interactionHandler) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setRemoteViewsInteractionHandler(interactionHandler);
        }
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) throws Resources.NotFoundException {
        setDisplayedChild(i);
    }

    @Override // android.widget.AdapterView
    public View getSelectedView() {
        return getViewAtRelativeIndex(this.mActiveOffset);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public boolean onRemoteAdapterConnected() throws Resources.NotFoundException {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter == this.mAdapter) {
            if (remoteViewsAdapter == null) {
                return false;
            }
            remoteViewsAdapter.superNotifyDataSetChanged();
            return true;
        }
        setAdapter(remoteViewsAdapter);
        if (this.mDeferNotifyDataSetChanged) {
            this.mRemoteViewsAdapter.notifyDataSetChanged();
            this.mDeferNotifyDataSetChanged = false;
        }
        if (this.mDeferSetDisplayedChild) {
            setDisplayedChild(this.mDeferSetDisplayedChildIndex);
            this.mDeferSetDisplayedChild = false;
        }
        int i = this.mRestoreWhichChild;
        if (i > -1) {
            setDisplayedChild(i, false);
            this.mRestoreWhichChild = -1;
        }
        return false;
    }

    @Override // android.widget.Advanceable
    public void advance() throws Resources.NotFoundException {
        showNext();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AdapterViewAnimator.class.getName();
    }

    private void semSendBroadcastPositionInternal(String str, Intent intent) {
        String str2;
        String str3;
        String[] strArrSplit = str.split("/");
        if (strArrSplit.length <= 1 || (str2 = strArrSplit[0]) == null || strArrSplit[1] == null || str2.isEmpty() || strArrSplit[1].isEmpty()) {
            return;
        }
        intent.setPackage(strArrSplit[0]);
        intent.setComponent(new ComponentName(strArrSplit[0], strArrSplit[1]));
        if (strArrSplit.length == 3 && (str3 = strArrSplit[2]) != null && !str3.isEmpty()) {
            this.mContext.sendBroadcast(intent, strArrSplit[2]);
        } else {
            this.mContext.sendBroadcast(intent);
        }
    }

    private void semSendBroadcastPosition(int i, int i2) {
        if (i >= 0 && i2 == 1 && !this.mAppWidgetGetCurrentDisplayedPosition.isEmpty()) {
            Intent intent = new Intent(APPWIDGET_CURRENT_DISPLAYED_POSITION_ACTION);
            intent.putExtra(APPWIDGET_EXTRA_CURRENT_DISPLAYED_POSITION, i);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.mAppWidgetId);
            semSendBroadcastPositionInternal(this.mAppWidgetGetCurrentDisplayedPosition, intent);
        }
    }

    public void semSetAppWidgetGetCurrentDisplayedPosition(String str) {
        this.mAppWidgetGetCurrentDisplayedPosition = str;
    }

    public void semUsePreloadPositionIndices(boolean z) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.semUsePreloadPositionIndices(z);
        }
    }
}
