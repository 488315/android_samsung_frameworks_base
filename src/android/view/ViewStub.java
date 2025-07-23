package android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.ref.WeakReference;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public final class ViewStub extends View {
    private OnInflateListener mInflateListener;
    private int mInflatedId;
    private WeakReference<View> mInflatedViewRef;
    private LayoutInflater mInflater;
    private int mLayoutResource;

    public interface OnInflateListener {
        void onInflate(ViewStub viewStub, View view);
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) {
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
    }

    public ViewStub(Context context) {
        this(context, 0);
    }

    public ViewStub(Context context, int i) {
        this(context, (AttributeSet) null);
        this.mLayoutResource = i;
    }

    public ViewStub(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewStub(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ViewStub(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewStub, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ViewStub, attributeSet, obtainStyledAttributes, i, i2);
        this.mInflatedId = obtainStyledAttributes.getResourceId(2, -1);
        this.mLayoutResource = obtainStyledAttributes.getResourceId(1, 0);
        this.mID = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        setVisibility(8);
        setWillNotDraw(true);
    }

    public int getInflatedId() {
        return this.mInflatedId;
    }

    @RemotableViewMethod(asyncImpl = "setInflatedIdAsync")
    public void setInflatedId(int i) {
        this.mInflatedId = i;
    }

    public Runnable setInflatedIdAsync(int i) {
        this.mInflatedId = i;
        return null;
    }

    public int getLayoutResource() {
        return this.mLayoutResource;
    }

    @RemotableViewMethod(asyncImpl = "setLayoutResourceAsync")
    public void setLayoutResource(int i) {
        this.mLayoutResource = i;
    }

    public Runnable setLayoutResourceAsync(int i) {
        this.mLayoutResource = i;
        return null;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.View
    @RemotableViewMethod(asyncImpl = "setVisibilityAsync")
    public void setVisibility(int i) {
        WeakReference<View> weakReference = this.mInflatedViewRef;
        if (weakReference != null) {
            View view = weakReference.get();
            if (view != null) {
                view.setVisibility(i);
                return;
            }
            throw new IllegalStateException("setVisibility called on un-referenced view");
        }
        super.setVisibility(i);
        if (i == 0 || i == 4) {
            inflate();
        }
    }

    public Runnable setVisibilityAsync(int i) {
        if (i == 0 || i == 4) {
            return new ViewReplaceRunnable(inflateViewNoAdd((ViewGroup) getParent()));
        }
        return null;
    }

    private View inflateViewNoAdd(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = this.mInflater;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(this.mContext);
        }
        View inflate = layoutInflater.inflate(this.mLayoutResource, viewGroup, false);
        int i = this.mInflatedId;
        if (i != -1) {
            inflate.setId(i);
        }
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceSelfWithView(View view, ViewGroup viewGroup) {
        int indexOfChild = viewGroup.indexOfChild(this);
        viewGroup.removeViewInLayout(this);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            viewGroup.addView(view, indexOfChild, layoutParams);
        } else {
            viewGroup.addView(view, indexOfChild);
        }
    }

    public View inflate() {
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            if (this.mLayoutResource != 0) {
                ViewGroup viewGroup = (ViewGroup) parent;
                View inflateViewNoAdd = inflateViewNoAdd(viewGroup);
                replaceSelfWithView(inflateViewNoAdd, viewGroup);
                this.mInflatedViewRef = new WeakReference<>(inflateViewNoAdd);
                OnInflateListener onInflateListener = this.mInflateListener;
                if (onInflateListener != null) {
                    onInflateListener.onInflate(this, inflateViewNoAdd);
                }
                return inflateViewNoAdd;
            }
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
        throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }

    public class ViewReplaceRunnable implements Runnable {
        public final View view;

        ViewReplaceRunnable(View view) {
            this.view = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewStub viewStub = ViewStub.this;
            viewStub.replaceSelfWithView(this.view, (ViewGroup) viewStub.getParent());
        }
    }
}
