package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ViewFlipper extends ViewAnimator {
    private static final int DEFAULT_INTERVAL = 3000;
    private static final boolean LOGD = false;
    private static final String TAG = "ViewFlipper";
    private boolean mAutoStart;
    private int mFlipInterval;
    private final Runnable mFlipRunnable;
    private boolean mRunning;
    private boolean mStarted;
    private boolean mVisible;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ViewFlipper> {
        private int mAutoStartId;
        private int mFlipIntervalId;
        private int mFlippingId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAutoStartId = propertyMapper.mapBoolean("autoStart", 16843445);
            this.mFlipIntervalId = propertyMapper.mapInt("flipInterval", 16843129);
            this.mFlippingId = propertyMapper.mapBoolean("flipping", 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ViewFlipper viewFlipper, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAutoStartId, viewFlipper.isAutoStart());
            propertyReader.readInt(this.mFlipIntervalId, viewFlipper.getFlipInterval());
            propertyReader.readBoolean(this.mFlippingId, viewFlipper.isFlipping());
        }
    }

    public ViewFlipper(Context context) {
        super(context);
        this.mFlipInterval = 3000;
        this.mAutoStart = false;
        this.mRunning = false;
        this.mStarted = false;
        this.mVisible = false;
        this.mFlipRunnable = new Runnable() { // from class: android.widget.ViewFlipper.1
            @Override // java.lang.Runnable
            public void run() {
                if (ViewFlipper.this.mRunning) {
                    ViewFlipper.this.showNext();
                    ViewFlipper viewFlipper = ViewFlipper.this;
                    viewFlipper.postDelayed(viewFlipper.mFlipRunnable, ViewFlipper.this.mFlipInterval);
                }
            }
        };
    }

    public ViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFlipInterval = 3000;
        this.mAutoStart = false;
        this.mRunning = false;
        this.mStarted = false;
        this.mVisible = false;
        this.mFlipRunnable = new Runnable() { // from class: android.widget.ViewFlipper.1
            @Override // java.lang.Runnable
            public void run() {
                if (ViewFlipper.this.mRunning) {
                    ViewFlipper.this.showNext();
                    ViewFlipper viewFlipper = ViewFlipper.this;
                    viewFlipper.postDelayed(viewFlipper.mFlipRunnable, ViewFlipper.this.mFlipInterval);
                }
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewFlipper);
        this.mFlipInterval = obtainStyledAttributes.getInt(0, 3000);
        this.mAutoStart = obtainStyledAttributes.getBoolean(1, false);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAutoStart) {
            startFlipping();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        updateRunning();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mVisible = i == 0;
        updateRunning(false);
    }

    @RemotableViewMethod
    public void setFlipInterval(int i) {
        this.mFlipInterval = i;
    }

    public int getFlipInterval() {
        return this.mFlipInterval;
    }

    public void startFlipping() {
        this.mStarted = true;
        updateRunning();
    }

    public void stopFlipping() {
        this.mStarted = false;
        updateRunning();
    }

    @Override // android.widget.ViewAnimator, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ViewFlipper.class.getName();
    }

    private void updateRunning() {
        updateRunning(true);
    }

    private void updateRunning(boolean z) {
        boolean z2 = this.mVisible && this.mStarted;
        if (z2 != this.mRunning) {
            if (z2) {
                showOnly(this.mWhichChild, z);
                postDelayed(this.mFlipRunnable, this.mFlipInterval);
            } else {
                removeCallbacks(this.mFlipRunnable);
            }
            this.mRunning = z2;
        }
    }

    public boolean isFlipping() {
        return this.mStarted;
    }

    public void setAutoStart(boolean z) {
        this.mAutoStart = z;
    }

    public boolean isAutoStart() {
        return this.mAutoStart;
    }
}
