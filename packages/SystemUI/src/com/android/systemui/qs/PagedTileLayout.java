package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Scroller;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.systemui.R;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.logging.QSLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class PagedTileLayout extends ViewPager implements SecQSPanel.QSTileLayout {
    public static final PagedTileLayout$$ExternalSyntheticLambda0 SCROLL_CUBIC = new PagedTileLayout$$ExternalSyntheticLambda0();
    public final AnonymousClass3 mAdapter;
    public boolean mDistributeTiles;
    public int mExcessHeight;
    public int mLastExcessHeight;
    public int mLastMaxHeight;
    public int mLayoutDirection;
    public int mLayoutOrientation;
    public boolean mListening;
    public QSLogger mLogger;
    public final int mMaxColumns;
    public final int mMinRows;
    public final AnonymousClass2 mOnPageChangeListener;
    public int mPageToRestore;
    public final ArrayList mPages;
    Scroller mScroller;
    public final ArrayList mTiles;
    public final UiEventLogger mUiEventLogger;

    /* renamed from: com.android.systemui.qs.PagedTileLayout$3, reason: invalid class name */
    public class AnonymousClass3 extends PagerAdapter {
        public AnonymousClass3() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewPager viewPager, int i, Object obj) {
            PagedTileLayout pagedTileLayout = PagedTileLayout.this;
            pagedTileLayout.mLogger.d(Integer.valueOf(i), "Destantiating page at");
            viewPager.removeView((View) obj);
            pagedTileLayout.updateListening();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return PagedTileLayout.this.mPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewPager viewPager, int i) {
            PagedTileLayout pagedTileLayout = PagedTileLayout.this;
            pagedTileLayout.mLogger.d(Integer.valueOf(i), "Instantiating page at");
            if (pagedTileLayout.isLayoutRtl()) {
                i = (pagedTileLayout.mPages.size() - 1) - i;
            }
            ViewGroup viewGroup = (ViewGroup) pagedTileLayout.mPages.get(i);
            if (viewGroup.getParent() != null) {
                viewPager.removeView(viewGroup);
            }
            viewPager.addView(viewGroup);
            pagedTileLayout.updateListening();
            return viewGroup;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.viewpager.widget.ViewPager$OnPageChangeListener, com.android.systemui.qs.PagedTileLayout$2] */
    public PagedTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTiles = new ArrayList();
        this.mPages = new ArrayList();
        this.mDistributeTiles = false;
        this.mPageToRestore = -1;
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        ActivityManager.isRunningInTestHarness();
        this.mLastMaxHeight = -1;
        ?? r0 = new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.qs.PagedTileLayout.2
            public int mCurrentScrollState = 0;
            public boolean mIsScrollJankTraceBegin = false;

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i) {
                if (i != this.mCurrentScrollState && i == 0) {
                    InteractionJankMonitor.getInstance().end(6);
                    this.mIsScrollJankTraceBegin = false;
                }
                this.mCurrentScrollState = i;
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i) {
                boolean z = this.mIsScrollJankTraceBegin;
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                if (!z && this.mCurrentScrollState == 1) {
                    InteractionJankMonitor.getInstance().begin(pagedTileLayout, 6);
                    this.mIsScrollJankTraceBegin = true;
                }
                PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
                pagedTileLayout.getClass();
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i) {
                PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                pagedTileLayout.setImportantForAccessibility(4);
                if (pagedTileLayout.isLayoutRtl()) {
                    pagedTileLayout.mPages.size();
                }
                for (int i2 = 0; i2 < pagedTileLayout.mPages.size(); i2++) {
                    TileLayout tileLayout = (TileLayout) pagedTileLayout.mPages.get(i2);
                    tileLayout.setSelected(false);
                    if (tileLayout.isSelected()) {
                        for (int i3 = 0; i3 < tileLayout.mRecords.size(); i3++) {
                            QSTile qSTile = ((SecQSPanelControllerBase.TileRecord) tileLayout.mRecords.get(i3)).tile;
                            pagedTileLayout.mUiEventLogger.logWithInstanceId(QSEvent.QS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
                        }
                    }
                }
                pagedTileLayout.setImportantForAccessibility(0);
            }
        };
        this.mOnPageChangeListener = r0;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mAdapter = anonymousClass3;
        this.mScroller = new Scroller(context, SCROLL_CUBIC);
        setAdapter(anonymousClass3);
        super.mOnPageChangeListener = r0;
        setCurrentItem(0, false);
        this.mLayoutOrientation = getResources().getConfiguration().orientation;
        this.mLayoutDirection = getLayoutDirection();
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        this.mLogger.d("adding new tile", "forcing tile redistribution across pages, reason");
        this.mDistributeTiles = true;
        requestLayout();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void computeScroll() throws Resources.NotFoundException {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            if (!this.mFakeDragging && !this.mIsBeingDragged) {
                this.mFakeDragging = true;
                setScrollState(1);
                this.mLastMotionX = 0.0f;
                this.mInitialMotionX = 0.0f;
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, 0.0f, 0.0f, 0);
                this.mVelocityTracker.addMovement(motionEventObtain);
                motionEventObtain.recycle();
                this.mFakeDragBeginTime = jUptimeMillis;
            }
            try {
                super.fakeDragBy(getScrollX() - this.mScroller.getCurrX());
                postInvalidateOnAnimation();
            } catch (NullPointerException e) {
                QSLogger qSLogger = this.mLogger;
                qSLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7("FakeDragBy called before begin");
                LogBuffer logBuffer = qSLogger.buffer;
                logBuffer.commit(logBuffer.obtain("QSLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7, e));
                final int size = this.mPages.size() - 1;
                post(new Runnable() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        PagedTileLayout pagedTileLayout = this.f$0;
                        int i = size;
                        PagedTileLayout$$ExternalSyntheticLambda0 pagedTileLayout$$ExternalSyntheticLambda0 = PagedTileLayout.SCROLL_CUBIC;
                        pagedTileLayout.setCurrentItem(i, true);
                        pagedTileLayout.setOffscreenPageLimit();
                    }
                });
            }
        } else if (this.mFakeDragging) {
            try {
                super.endFakeDrag();
            } catch (NullPointerException e2) {
                QSLogger qSLogger2 = this.mLogger;
                qSLogger2.getClass();
                LogLevel logLevel2 = LogLevel.ERROR;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda72 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7("endFakeDrag called without velocityTracker");
                LogBuffer logBuffer2 = qSLogger2.buffer;
                logBuffer2.commit(logBuffer2.obtain("QSLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda72, e2));
            }
            setOffscreenPageLimit();
        }
        super.computeScroll();
    }

    public final TileLayout createTileLayout() throws Resources.NotFoundException {
        int i = 0;
        TileLayout tileLayout = (TileLayout) LayoutInflater.from(getContext()).inflate(R.layout.qs_paged_page, (ViewGroup) this, false);
        int i2 = this.mMinRows;
        if (tileLayout.mMinRows != i2) {
            tileLayout.mMinRows = i2;
            tileLayout.updateResources();
        }
        int i3 = this.mMaxColumns;
        tileLayout.mMaxColumns = i3;
        tileLayout.mColumns = Math.min(tileLayout.mResourceColumns, i3);
        tileLayout.setSelected(false);
        float f = this.mPages.isEmpty() ? 1.0f : ((TileLayout) this.mPages.get(0)).mSquishinessFraction;
        if (Float.compare(tileLayout.mSquishinessFraction, f) != 0) {
            tileLayout.mSquishinessFraction = f;
            tileLayout.layoutTileRecords(tileLayout.mRecords.size(), false);
            ArrayList arrayList = tileLayout.mRecords;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ViewParent viewParent = ((SecQSPanelControllerBase.TileRecord) obj).tileView;
                if (viewParent instanceof HeightOverrideable) {
                    float f2 = tileLayout.mSquishinessFraction;
                    QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((HeightOverrideable) viewParent);
                    if (qSTileViewImpl.squishinessFraction != f2) {
                        qSTileViewImpl.squishinessFraction = f2;
                        qSTileViewImpl.updateHeight();
                    }
                }
            }
        }
        return tileLayout;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.mPages.get(i);
            if (view.getParent() == null) {
                view.dispatchConfigurationChanged(configuration);
            }
        }
        int i2 = this.mLayoutOrientation;
        int i3 = configuration.orientation;
        if (i2 == i3) {
            this.mLogger.d(configuration, "Orientation didn't change, tiles might be not redistributed, new config");
            return;
        }
        this.mLayoutOrientation = i3;
        this.mLogger.d("orientation changed to " + this.mLayoutOrientation, "forcing tile redistribution across pages, reason");
        this.mDistributeTiles = true;
        setCurrentItem(0, false);
        this.mPageToRestore = 0;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPages.add(createTileLayout());
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f;
        float axisValue;
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            if ((motionEvent.getMetaState() & 1) != 0) {
                axisValue = motionEvent.getAxisValue(9);
                f = 0.0f;
            } else {
                f = -motionEvent.getAxisValue(9);
                axisValue = motionEvent.getAxisValue(10);
            }
            if (axisValue != 0.0f || f != 0.0f) {
                int width = 0;
                boolean z = !isLayoutRtl() ? !(axisValue > 0.0f || f > 0.0f) : !(axisValue < 0.0f || f < 0.0f);
                if (this.mScroller.isFinished()) {
                    if (!z && this.mCurItem != 0) {
                        width = -getWidth();
                    } else if (z && this.mCurItem != this.mPages.size() - 1) {
                        width = getWidth();
                    }
                    int i = width;
                    if (i != 0) {
                        this.mScroller.startScroll(getScrollX(), getScrollY(), i, 0, 300);
                        postInvalidateOnAnimation();
                    }
                }
                return true;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        AnonymousClass3 anonymousClass3 = this.mAdapter;
        if (anonymousClass3 == null || anonymousClass3.getCount() <= 0) {
            return;
        }
        accessibilityEvent.setItemCount(this.mAdapter.getCount());
        boolean zIsLayoutRtl = isLayoutRtl();
        int size = this.mCurItem;
        if (zIsLayoutRtl) {
            size = (this.mPages.size() - 1) - size;
        }
        accessibilityEvent.setFromIndex(size);
        boolean zIsLayoutRtl2 = isLayoutRtl();
        int size2 = this.mCurItem;
        if (zIsLayoutRtl2) {
            size2 = (this.mPages.size() - 1) - size2;
        }
        accessibilityEvent.setToIndex(size2);
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mCurItem != 0) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT);
        }
        if (this.mCurItem != this.mPages.size() - 1) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (((TileLayout) this.mPages.get(0)).getParent() == null) {
            ((TileLayout) this.mPages.get(0)).layout(i, i2, i3, i4);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = this.mTiles.size();
        if (this.mDistributeTiles || this.mLastMaxHeight != View.MeasureSpec.getSize(i2) || this.mLastExcessHeight != this.mExcessHeight) {
            int size2 = View.MeasureSpec.getSize(i2);
            this.mLastMaxHeight = size2;
            int i3 = this.mExcessHeight;
            this.mLastExcessHeight = i3;
            if (((TileLayout) this.mPages.get(0)).updateMaxRows(size2 - i3, size) || this.mDistributeTiles) {
                this.mDistributeTiles = false;
                int size3 = this.mTiles.size();
                TileLayout tileLayout = (TileLayout) this.mPages.get(0);
                int iMax = Math.max(size3 / Math.max(tileLayout.mColumns * tileLayout.mRows, 1), 1);
                TileLayout tileLayout2 = (TileLayout) this.mPages.get(0);
                if (size3 > Math.max(tileLayout2.mColumns * tileLayout2.mRows, 1) * iMax) {
                    iMax++;
                }
                int size4 = this.mPages.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    ((TileLayout) this.mPages.get(i4)).removeAllViews();
                }
                if (size4 != iMax) {
                    while (this.mPages.size() < iMax) {
                        ConstantStringsLoggerImpl constantStringsLoggerImpl = this.mLogger.$$delegate_0;
                        constantStringsLoggerImpl.getClass();
                        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, "Adding new page");
                        this.mPages.add(createTileLayout());
                    }
                    while (this.mPages.size() > iMax) {
                        ConstantStringsLoggerImpl constantStringsLoggerImpl2 = this.mLogger.$$delegate_0;
                        constantStringsLoggerImpl2.getClass();
                        LogBuffer.log$default(constantStringsLoggerImpl2.buffer, constantStringsLoggerImpl2.tag, LogLevel.DEBUG, "Removing page");
                        ArrayList arrayList = this.mPages;
                        arrayList.remove(arrayList.size() - 1);
                    }
                    setAdapter(this.mAdapter);
                    this.mAdapter.notifyDataSetChanged();
                    int i5 = this.mPageToRestore;
                    if (i5 != -1) {
                        setCurrentItem(i5, false);
                        this.mPageToRestore = -1;
                    }
                }
                TileLayout tileLayout3 = (TileLayout) this.mPages.get(0);
                int iMax2 = Math.max(tileLayout3.mColumns * tileLayout3.mRows, 1);
                int size5 = this.mTiles.size();
                QSLogger qSLogger = this.mLogger;
                qSLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                QSLogger$$ExternalSyntheticLambda0 qSLogger$$ExternalSyntheticLambda0 = new QSLogger$$ExternalSyntheticLambda0(8);
                LogBuffer logBuffer = qSLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("QSLog", logLevel, qSLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.int1 = iMax2;
                logMessageImpl.int2 = size5;
                logBuffer.commit(logMessageObtain);
                int i6 = 0;
                for (int i7 = 0; i7 < size5; i7++) {
                    SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) this.mTiles.get(i7);
                    if (((TileLayout) this.mPages.get(i6)).mRecords.size() == iMax2) {
                        i6++;
                    }
                    QSLogger qSLogger2 = this.mLogger;
                    String simpleName = tileRecord.tile.getClass().getSimpleName();
                    qSLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    QSLogger$$ExternalSyntheticLambda0 qSLogger$$ExternalSyntheticLambda02 = new QSLogger$$ExternalSyntheticLambda0(10);
                    LogBuffer logBuffer2 = qSLogger2.buffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("QSLog", logLevel2, qSLogger$$ExternalSyntheticLambda02, null);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                    logMessageImpl2.str1 = simpleName;
                    logMessageImpl2.int1 = i6;
                    logBuffer2.commit(logMessageObtain2);
                    ((TileLayout) this.mPages.get(i6)).addTile(tileRecord);
                }
            }
            int i8 = ((TileLayout) this.mPages.get(0)).mRows;
            for (int i9 = 0; i9 < this.mPages.size(); i9++) {
                ((TileLayout) this.mPages.get(i9)).mRows = i8;
            }
        }
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            int measuredHeight = getChildAt(i11).getMeasuredHeight();
            if (measuredHeight > i10) {
                i10 = measuredHeight;
            }
        }
        if (((TileLayout) this.mPages.get(0)).getParent() == null) {
            ((TileLayout) this.mPages.get(0)).measure(i, i2);
            int measuredHeight2 = ((TileLayout) this.mPages.get(0)).getMeasuredHeight();
            if (measuredHeight2 > i10) {
                i10 = measuredHeight2;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), getPaddingBottom() + i10);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        boolean z = this.mLayoutDirection == 1;
        int size = this.mCurItem;
        if (z) {
            size = (this.mPages.size() - 1) - size;
        }
        super.onRtlPropertiesChanged(i);
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            setAdapter(this.mAdapter);
            setCurrentItem(size, false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001e, code lost:
    
        r5 = 4096;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        int id = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT.getId();
        int id2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT.getId();
        if (i == id || i == id2) {
            i = !isLayoutRtl() ? 8192 : 8192;
        }
        boolean zPerformAccessibilityAction = super.performAccessibilityAction(i, bundle);
        if (zPerformAccessibilityAction && (i == 8192 || i == 4096)) {
            requestAccessibilityFocus();
        }
        return zPerformAccessibilityAction;
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void removeTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        if (this.mTiles.remove(tileRecord)) {
            this.mLogger.d("removing tile", "forcing tile redistribution across pages, reason");
            this.mDistributeTiles = true;
            requestLayout();
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void restoreInstanceState(Bundle bundle) {
        this.mPageToRestore = bundle.getInt("current_page", -1);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void saveInstanceState(Bundle bundle) {
        int size = this.mPageToRestore;
        if (size == -1) {
            boolean zIsLayoutRtl = isLayoutRtl();
            int i = this.mCurItem;
            size = zIsLayoutRtl ? (this.mPages.size() - 1) - i : i;
        }
        bundle.putInt("current_page", size);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        updateListening();
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void setLogger(QSLogger qSLogger) {
        this.mLogger = qSLogger;
    }

    public final void updateListening() {
        ArrayList arrayList = this.mPages;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TileLayout tileLayout = (TileLayout) obj;
            tileLayout.setListening(tileLayout.getParent() != null && this.mListening, null);
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final boolean updateResources() {
        boolean zUpdateResources = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            zUpdateResources |= ((TileLayout) this.mPages.get(i)).updateResources();
        }
        if (zUpdateResources) {
            this.mLogger.d("resources in pages changed", "forcing tile redistribution across pages, reason");
            this.mDistributeTiles = true;
            requestLayout();
            return zUpdateResources;
        }
        ConstantStringsLoggerImpl constantStringsLoggerImpl = this.mLogger.$$delegate_0;
        constantStringsLoggerImpl.getClass();
        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, "resource in pages didn't change, tiles might be not redistributed");
        return zUpdateResources;
    }
}
