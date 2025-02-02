package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.C4337R;
import com.samsung.android.widget.SemPressGestureDetector;

@RemoteViews.RemoteView
@Deprecated
/* loaded from: classes4.dex */
public class AbsoluteLayout extends ViewGroup {

  public static class LayoutParams extends ViewGroup.LayoutParams {

    /* renamed from: x */
    public int f596x;

    /* renamed from: y */
    public int f597y;

    public final class InspectionCompanion
        implements android.view.inspector.InspectionCompanion<LayoutParams> {
      private int mLayout_xId;
      private int mLayout_yId;
      private boolean mPropertiesMapped = false;

      @Override // android.view.inspector.InspectionCompanion
      public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_xId = propertyMapper.mapInt("layout_x", 16843135);
        this.mLayout_yId = propertyMapper.mapInt("layout_y", 16843136);
        this.mPropertiesMapped = true;
      }

      @Override // android.view.inspector.InspectionCompanion
      public void readProperties(LayoutParams node, PropertyReader propertyReader) {
        if (!this.mPropertiesMapped) {
          throw new InspectionCompanion.UninitializedPropertyMapException();
        }
        propertyReader.readInt(this.mLayout_xId, node.f596x);
        propertyReader.readInt(this.mLayout_yId, node.f597y);
      }
    }

    public LayoutParams(int width, int height, int x, int y) {
      super(width, height);
      this.f596x = x;
      this.f597y = y;
    }

    public LayoutParams(Context c, AttributeSet attrs) {
      super(c, attrs);
      TypedArray a = c.obtainStyledAttributes(attrs, C4337R.styleable.AbsoluteLayout_Layout);
      this.f596x = a.getDimensionPixelOffset(0, 0);
      this.f597y = a.getDimensionPixelOffset(1, 0);
      a.recycle();
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }

    @Override // android.view.ViewGroup.LayoutParams
    public String debug(String output) {
      return output
          + "Absolute.LayoutParams={width="
          + sizeToString(this.width)
          + ", height="
          + sizeToString(this.height)
          + " x="
          + this.f596x
          + " y="
          + this.f597y
          + "}";
    }
  }

  public AbsoluteLayout(Context context) {
    this(context, null);
  }

  public AbsoluteLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public AbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override // android.view.View
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int count = getChildCount();
    int maxHeight = 0;
    int maxWidth = 0;
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() != 8) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childRight = lp.f596x + child.getMeasuredWidth();
        int childBottom = lp.f597y + child.getMeasuredHeight();
        maxWidth = Math.max(maxWidth, childRight);
        maxHeight = Math.max(maxHeight, childBottom);
      }
    }
    int i2 = this.mPaddingLeft;
    setMeasuredDimension(
        resolveSizeAndState(
            Math.max(maxWidth + i2 + this.mPaddingRight, getSuggestedMinimumWidth()),
            widthMeasureSpec,
            0),
        resolveSizeAndState(
            Math.max(
                maxHeight + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()),
            heightMeasureSpec,
            0));
  }

  @Override // android.view.ViewGroup
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-2, -2, 0, 0);
  }

  @Override // android.view.ViewGroup, android.view.View
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int count = getChildCount();
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() != 8) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childLeft = this.mPaddingLeft + lp.f596x;
        int childTop = this.mPaddingTop + lp.f597y;
        child.layout(
            childLeft,
            childTop,
            child.getMeasuredWidth() + childLeft,
            child.getMeasuredHeight() + childTop);
      }
    }
  }

  @Override // android.view.ViewGroup
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new LayoutParams(getContext(), attrs);
  }

  @Override // android.view.ViewGroup
  protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    return p instanceof LayoutParams;
  }

  @Override // android.view.ViewGroup
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    return new LayoutParams(p);
  }

  @Override // android.view.ViewGroup
  public boolean shouldDelayChildPressedState() {
    return false;
  }

  @Override // android.view.ViewGroup, android.view.View
  public View semDispatchFindView(float x, float y, boolean findImage) {
    String foundText =
        SemPressGestureDetector.getText(getContext(), getContext().getPackageName(), this);
    if (foundText != null) {
      semSetBixbyTouchFoundText(foundText);
      return this;
    }
    return super.semDispatchFindView(x, y, findImage);
  }
}
