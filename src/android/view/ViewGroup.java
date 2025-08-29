package android.view;

import android.animation.LayoutTransition;
import android.app.jank.AppJankStats;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.TtmlUtils;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.security.keystore.KeyProperties;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.WindowInsetsAnimation;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Transformation;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.Helper;
import android.view.flags.Flags;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.view.translation.TranslationCapability;
import android.view.translation.ViewTranslationRequest;
import android.webkit.WebView;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.R;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.widget.ISemTouchApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public abstract class ViewGroup extends View implements ViewParent, ViewManager {
    private static final int ARRAY_CAPACITY_INCREMENT = 12;
    private static final int ARRAY_INITIAL_CAPACITY = 12;
    private static final int CHILD_LEFT_INDEX = 0;
    private static final int CHILD_TOP_INDEX = 1;
    protected static final int CLIP_TO_PADDING_MASK = 34;
    private static final boolean DBG = false;
    private static final int FLAG_ADD_STATES_FROM_CHILDREN = 8192;

    @Deprecated
    private static final int FLAG_ALWAYS_DRAWN_WITH_CACHE = 16384;

    @Deprecated
    private static final int FLAG_ANIMATION_CACHE = 64;
    static final int FLAG_ANIMATION_DONE = 16;

    @Deprecated
    private static final int FLAG_CHILDREN_DRAWN_WITH_CACHE = 32768;
    static final int FLAG_CLEAR_TRANSFORMATION = 256;
    static final int FLAG_CLIP_CHILDREN = 1;
    private static final int FLAG_CLIP_TO_PADDING = 2;
    protected static final int FLAG_DISALLOW_INTERCEPT = 524288;
    static final int FLAG_INVALIDATE_REQUIRED = 4;
    static final int FLAG_IS_TRANSITION_GROUP = 16777216;
    static final int FLAG_IS_TRANSITION_GROUP_SET = 33554432;
    private static final int FLAG_LAYOUT_MODE_WAS_EXPLICITLY_SET = 8388608;
    private static final int FLAG_MASK_FOCUSABILITY = 393216;
    private static final int FLAG_NOTIFY_ANIMATION_LISTENER = 512;
    private static final int FLAG_NOTIFY_CHILDREN_ON_DRAWABLE_STATE_CHANGE = 65536;
    static final int FLAG_OPTIMIZE_INVALIDATE = 128;
    private static final int FLAG_PADDING_NOT_NULL = 32;
    private static final int FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW = 4194304;
    private static final int FLAG_PROPAGATED_FRAME_RATE = 1073741824;
    private static final int FLAG_RUN_ANIMATION = 8;
    private static final int FLAG_SHOW_CONTEXT_MENU_WITH_COORDS = 536870912;
    private static final int FLAG_SPLIT_MOTION_EVENTS = 2097152;
    private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_NOT_TYPED = 268435456;
    private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_TYPED = 134217728;
    protected static final int FLAG_SUPPORT_STATIC_TRANSFORMATIONS = 2048;
    static final int FLAG_TOUCHSCREEN_BLOCKS_FOCUS = 67108864;
    protected static final int FLAG_USE_CHILD_DRAWING_ORDER = 1024;
    public static final int FOCUS_AFTER_DESCENDANTS = 262144;
    public static final int FOCUS_BEFORE_DESCENDANTS = 131072;
    public static final int FOCUS_BLOCK_DESCENDANTS = 393216;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;
    private static final int LAYOUT_MODE_UNDEFINED = -1;

    @Deprecated
    public static final int PERSISTENT_ALL_CACHES = 3;

    @Deprecated
    public static final int PERSISTENT_ANIMATION_CACHE = 1;

    @Deprecated
    public static final int PERSISTENT_NO_CACHE = 0;

    @Deprecated
    public static final int PERSISTENT_SCROLLING_CACHE = 2;
    private static final String TAG = "ViewGroup";
    private static float[] sDebugLines;
    private Animation.AnimationListener mAnimationListener;
    Paint mCachePaint;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private int mChildCountWithTransientState;
    private Transformation mChildTransformation;
    int mChildUnhandledKeyListeners;
    private View[] mChildren;
    private int mChildrenCount;
    private HashSet<View> mChildrenInterestedInDrag;
    private View mCurrentDragChild;
    private DragEvent mCurrentDragStartEvent;
    private View mDefaultFocus;
    protected ArrayList<View> mDisappearingChildren;
    private HoverTarget mFirstHoverTarget;
    private TouchTarget mFirstTouchTarget;
    private View mFocused;
    View mFocusedInCluster;

    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "CLIP_CHILDREN"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "CLIP_TO_PADDING"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "PADDING_NOT_NULL")}, formatToHexString = true)
    protected int mGroupFlags;
    private boolean mHoveredSelf;
    private int mInsetsAnimationDispatchMode;
    RectF mInvalidateRegion;
    Transformation mInvalidationTransformation;
    private boolean mIsInterestedInDrag;

    @ViewDebug.ExportedProperty(category = Contract.Events.PATH)
    private int mLastTouchDownIndex;

    @ViewDebug.ExportedProperty(category = Contract.Events.PATH)
    private long mLastTouchDownTime;

    @ViewDebug.ExportedProperty(category = Contract.Events.PATH)
    private float mLastTouchDownX;

    @ViewDebug.ExportedProperty(category = Contract.Events.PATH)
    private float mLastTouchDownY;
    private LayoutAnimationController mLayoutAnimationController;
    private boolean mLayoutCalledWhileSuppressed;
    private int mLayoutMode;
    private LayoutTransition.TransitionListener mLayoutTransitionListener;
    private PointF mLocalPoint;
    private int mNestedScrollAxes;
    protected OnHierarchyChangeListener mOnHierarchyChangeListener;
    protected int mPersistentDrawingCache;
    private ArrayList<View> mPreSortedChildren;
    private int mSemHorizontalScrollbarRectRelativePosX;
    private int mSemVerticalScrollbarRectRelativePosY;
    private float mSemX;
    private float mSemY;
    boolean mSuppressLayout;
    private int[] mTempLocation;
    private Point mTempPoint;
    private float[] mTempPosition;
    private Rect mTempRect;
    private View mTooltipHoverTarget;
    private boolean mTooltipHoveredSelf;
    private IntArray mTransientIndices;
    private List<View> mTransientViews;
    private LayoutTransition mTransition;
    private ArrayList<View> mTransitioningViews;
    private ArrayList<View> mVisibilityChangingChildren;
    private static final int[] DESCENDANT_FOCUSABILITY_FLAGS = {131072, 262144, 393216};
    private static boolean sToolkitViewGroupFrameRateApiFlagValue = Flags.toolkitViewgroupSetRequestedFrameRateApi();
    public static int LAYOUT_MODE_DEFAULT = 0;
    private static final ActionMode SENTINEL_ACTION_MODE = new ActionMode() { // from class: android.view.ViewGroup.1
        @Override // android.view.ActionMode
        public void finish() {
        }

        @Override // android.view.ActionMode
        public View getCustomView() {
            return null;
        }

        @Override // android.view.ActionMode
        public Menu getMenu() {
            return null;
        }

        @Override // android.view.ActionMode
        public MenuInflater getMenuInflater() {
            return null;
        }

        @Override // android.view.ActionMode
        public CharSequence getSubtitle() {
            return null;
        }

        @Override // android.view.ActionMode
        public CharSequence getTitle() {
            return null;
        }

        @Override // android.view.ActionMode
        public void invalidate() {
        }

        @Override // android.view.ActionMode
        public void setCustomView(View view) {
        }

        @Override // android.view.ActionMode
        public void setSubtitle(int i) {
        }

        @Override // android.view.ActionMode
        public void setSubtitle(CharSequence charSequence) {
        }

        @Override // android.view.ActionMode
        public void setTitle(int i) {
        }

        @Override // android.view.ActionMode
        public void setTitle(CharSequence charSequence) {
        }
    };

    public interface OnHierarchyChangeListener {
        void onChildViewAdded(View view, View view2);

        void onChildViewRemoved(View view, View view2);
    }

    private static int sign(int i) {
        return i >= 0 ? 1 : -1;
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return layoutParams != null;
    }

    protected LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return layoutParams;
    }

    protected int getChildDrawingOrder(int i, int i2) {
        return i2;
    }

    protected boolean getChildStaticTransformation(View view, Transformation transformation) {
        return false;
    }

    protected boolean isSemUsingAdapterView() {
        return false;
    }

    @Override // android.view.View
    protected abstract void onLayout(boolean z, int i, int i2, int i3, int i4);

    @Override // android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(View view, int i, Bundle bundle) {
        return false;
    }

    public boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return false;
    }

    public void onViewAdded(View view) {
    }

    public void onViewRemoved(View view) {
    }

    @Override // android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return false;
    }

    protected int semGetItemCount() {
        return 0;
    }

    public void semSetSelection(int i) {
    }

    public void semSmoothScrollBy(int i) {
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public static class LayoutParams {

        @Deprecated
        public static final int FILL_PARENT = -1;
        public static final int MATCH_PARENT = -1;
        public static final int WRAP_CONTENT = -2;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"), @ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT")})
        public int height;
        public LayoutAnimationController.AnimationParameters layoutAnimationParameters;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = -1, to = "MATCH_PARENT"), @ViewDebug.IntToString(from = -2, to = "WRAP_CONTENT")})
        public int width;

        public void onDebugDraw(View view, Canvas canvas, Paint paint) {
        }

        public void resolveLayoutDirection(int i) {
        }

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_heightId;
            private int mLayout_widthId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                SparseArray sparseArray = new SparseArray();
                sparseArray.put(-2, "wrap_content");
                sparseArray.put(-1, "match_parent");
                this.mLayout_heightId = propertyMapper.mapIntEnum("layout_height", 16842997, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
                SparseArray sparseArray2 = new SparseArray();
                sparseArray2.put(-2, "wrap_content");
                sparseArray2.put(-1, "match_parent");
                this.mLayout_widthId = propertyMapper.mapIntEnum("layout_width", 16842996, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readIntEnum(this.mLayout_heightId, layoutParams.height);
                propertyReader.readIntEnum(this.mLayout_widthId, layoutParams.width);
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewGroup_Layout);
            setBaseAttributes(typedArrayObtainStyledAttributes, 0, 1);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public LayoutParams(LayoutParams layoutParams) {
            this.width = layoutParams.width;
            this.height = layoutParams.height;
        }

        LayoutParams() {
        }

        protected void setBaseAttributes(TypedArray typedArray, int i, int i2) {
            this.width = typedArray.getLayoutDimension(i, "layout_width");
            this.height = typedArray.getLayoutDimension(i2, "layout_height");
        }

        public String debug(String str) {
            return str + "ViewGroup.LayoutParams={ width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " }";
        }

        protected static String sizeToString(int i) {
            if (i == -2) {
                return "wrap-content";
            }
            if (i == -1) {
                return "match-parent";
            }
            return String.valueOf(i);
        }

        void encode(ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.beginObject(this);
            encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.endObject();
        }

        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
            viewHierarchyEncoder.addProperty("width", this.width);
            viewHierarchyEncoder.addProperty("height", this.height);
        }
    }

    public static class MarginLayoutParams extends LayoutParams {
        public static final int DEFAULT_MARGIN_RELATIVE = Integer.MIN_VALUE;
        private static final int DEFAULT_MARGIN_RESOLVED = 0;
        private static final int LAYOUT_DIRECTION_MASK = 3;
        private static final int LEFT_MARGIN_UNDEFINED_MASK = 4;
        private static final int NEED_RESOLUTION_MASK = 32;
        private static final int RIGHT_MARGIN_UNDEFINED_MASK = 8;
        private static final int RTL_COMPATIBILITY_MODE_MASK = 16;
        private static final int UNDEFINED_MARGIN = Integer.MIN_VALUE;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int bottomMargin;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        private int endMargin;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int leftMargin;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, flagMapping = {@ViewDebug.FlagToString(equals = 3, mask = 3, name = "LAYOUT_DIRECTION"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "LEFT_MARGIN_UNDEFINED_MASK"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "RIGHT_MARGIN_UNDEFINED_MASK"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "RTL_COMPATIBILITY_MODE_MASK"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "NEED_RESOLUTION_MASK")}, formatToHexString = true)
        byte mMarginFlags;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int rightMargin;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        private int startMargin;

        @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
        public int topMargin;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<MarginLayoutParams> {
            private int mLayout_marginBottomId;
            private int mLayout_marginLeftId;
            private int mLayout_marginRightId;
            private int mLayout_marginTopId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_marginBottomId = propertyMapper.mapInt("layout_marginBottom", 16843002);
                this.mLayout_marginLeftId = propertyMapper.mapInt("layout_marginLeft", 16842999);
                this.mLayout_marginRightId = propertyMapper.mapInt("layout_marginRight", 16843001);
                this.mLayout_marginTopId = propertyMapper.mapInt("layout_marginTop", 16843000);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(MarginLayoutParams marginLayoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_marginBottomId, marginLayoutParams.bottomMargin);
                propertyReader.readInt(this.mLayout_marginLeftId, marginLayoutParams.leftMargin);
                propertyReader.readInt(this.mLayout_marginRightId, marginLayoutParams.rightMargin);
                propertyReader.readInt(this.mLayout_marginTopId, marginLayoutParams.topMargin);
            }
        }

        public MarginLayoutParams(Context context, AttributeSet attributeSet) {
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewGroup_MarginLayout);
            setBaseAttributes(typedArrayObtainStyledAttributes, 0, 1);
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
            if (dimensionPixelSize >= 0) {
                this.leftMargin = dimensionPixelSize;
                this.topMargin = dimensionPixelSize;
                this.rightMargin = dimensionPixelSize;
                this.bottomMargin = dimensionPixelSize;
            } else {
                int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, -1);
                int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(10, -1);
                if (dimensionPixelSize2 >= 0) {
                    this.leftMargin = dimensionPixelSize2;
                    this.rightMargin = dimensionPixelSize2;
                } else {
                    int dimensionPixelSize4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, Integer.MIN_VALUE);
                    this.leftMargin = dimensionPixelSize4;
                    if (dimensionPixelSize4 == Integer.MIN_VALUE) {
                        this.mMarginFlags = (byte) (this.mMarginFlags | 4);
                        this.leftMargin = 0;
                    }
                    int dimensionPixelSize5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, Integer.MIN_VALUE);
                    this.rightMargin = dimensionPixelSize5;
                    if (dimensionPixelSize5 == Integer.MIN_VALUE) {
                        this.mMarginFlags = (byte) (this.mMarginFlags | 8);
                        this.rightMargin = 0;
                    }
                }
                this.startMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, Integer.MIN_VALUE);
                this.endMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, Integer.MIN_VALUE);
                if (dimensionPixelSize3 >= 0) {
                    this.topMargin = dimensionPixelSize3;
                    this.bottomMargin = dimensionPixelSize3;
                } else {
                    this.topMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 0);
                    this.bottomMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
                }
                if (isMarginRelative()) {
                    this.mMarginFlags = (byte) (this.mMarginFlags | 32);
                }
            }
            if (!context.getApplicationInfo().hasRtlSupport()) {
                this.mMarginFlags = (byte) (this.mMarginFlags | 16);
            }
            this.mMarginFlags = this.mMarginFlags;
            typedArrayObtainStyledAttributes.recycle();
        }

        public MarginLayoutParams(int i, int i2) {
            super(i, i2);
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            this.mMarginFlags = (byte) (((byte) (((byte) (((byte) (this.mMarginFlags | 4)) | 8)) & (-33))) & (-17));
        }

        public MarginLayoutParams(MarginLayoutParams marginLayoutParams) {
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            this.width = marginLayoutParams.width;
            this.height = marginLayoutParams.height;
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
            this.startMargin = marginLayoutParams.startMargin;
            this.endMargin = marginLayoutParams.endMargin;
            this.mMarginFlags = marginLayoutParams.mMarginFlags;
        }

        public MarginLayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.startMargin = Integer.MIN_VALUE;
            this.endMargin = Integer.MIN_VALUE;
            this.mMarginFlags = (byte) (((byte) (((byte) (((byte) (this.mMarginFlags | 4)) | 8)) & (-33))) & (-17));
        }

        public final void copyMarginsFrom(MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
            this.startMargin = marginLayoutParams.startMargin;
            this.endMargin = marginLayoutParams.endMargin;
            this.mMarginFlags = marginLayoutParams.mMarginFlags;
        }

        public void setMargins(int i, int i2, int i3, int i4) {
            this.leftMargin = i;
            this.topMargin = i2;
            this.rightMargin = i3;
            this.bottomMargin = i4;
            this.mMarginFlags = (byte) (((byte) (this.mMarginFlags & (-5))) & (-9));
            if (isMarginRelative()) {
                this.mMarginFlags = (byte) (this.mMarginFlags | 32);
            } else {
                this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
            }
        }

        public void setMarginsRelative(int i, int i2, int i3, int i4) {
            this.startMargin = i;
            this.topMargin = i2;
            this.endMargin = i3;
            this.bottomMargin = i4;
            this.mMarginFlags = (byte) (this.mMarginFlags | 32);
        }

        public void semSetMarginsRelative(int i, int i2, int i3, int i4) {
            setMarginsRelative(i, i2, i3, i4);
        }

        public void setMarginStart(int i) {
            this.startMargin = i;
            this.mMarginFlags = (byte) (this.mMarginFlags | 32);
        }

        public int getMarginStart() {
            int i = this.startMargin;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            if ((this.mMarginFlags & 32) == 32) {
                doResolveMargins();
            }
            if ((this.mMarginFlags & 3) == 1) {
                return this.rightMargin;
            }
            return this.leftMargin;
        }

        public void setMarginEnd(int i) {
            this.endMargin = i;
            this.mMarginFlags = (byte) (this.mMarginFlags | 32);
        }

        public int getMarginEnd() {
            int i = this.endMargin;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            if ((this.mMarginFlags & 32) == 32) {
                doResolveMargins();
            }
            if ((this.mMarginFlags & 3) == 1) {
                return this.leftMargin;
            }
            return this.rightMargin;
        }

        public boolean isMarginRelative() {
            return (this.startMargin == Integer.MIN_VALUE && this.endMargin == Integer.MIN_VALUE) ? false : true;
        }

        public void setLayoutDirection(int i) {
            if (i == 0 || i == 1) {
                byte b = this.mMarginFlags;
                if (i != (b & 3)) {
                    this.mMarginFlags = (byte) ((i & 3) | ((byte) (b & (-4))));
                    if (isMarginRelative()) {
                        this.mMarginFlags = (byte) (this.mMarginFlags | 32);
                    } else {
                        this.mMarginFlags = (byte) (this.mMarginFlags & (-33));
                    }
                }
            }
        }

        public int getLayoutDirection() {
            return this.mMarginFlags & 3;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public void resolveLayoutDirection(int i) {
            setLayoutDirection(i);
            if (isMarginRelative() && (this.mMarginFlags & 32) == 32) {
                doResolveMargins();
            }
        }

        private void doResolveMargins() {
            int i;
            int i2;
            byte b = this.mMarginFlags;
            if ((b & 16) == 16) {
                if ((b & 4) == 4 && (i2 = this.startMargin) > Integer.MIN_VALUE) {
                    this.leftMargin = i2;
                }
                if ((b & 8) == 8 && (i = this.endMargin) > Integer.MIN_VALUE) {
                    this.rightMargin = i;
                }
            } else {
                if ((b & 3) == 1) {
                    int i3 = this.endMargin;
                    if (i3 <= Integer.MIN_VALUE) {
                        i3 = 0;
                    }
                    this.leftMargin = i3;
                    int i4 = this.startMargin;
                    this.rightMargin = i4 > Integer.MIN_VALUE ? i4 : 0;
                } else {
                    int i5 = this.startMargin;
                    if (i5 <= Integer.MIN_VALUE) {
                        i5 = 0;
                    }
                    this.leftMargin = i5;
                    int i6 = this.endMargin;
                    this.rightMargin = i6 > Integer.MIN_VALUE ? i6 : 0;
                }
            }
            this.mMarginFlags = (byte) (b & (-33));
        }

        public boolean isLayoutRtl() {
            return (this.mMarginFlags & 3) == 1;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public void onDebugDraw(View view, Canvas canvas, Paint paint) {
            Insets opticalInsets = View.isLayoutModeOptical(view.mParent) ? view.getOpticalInsets() : Insets.NONE;
            ViewGroup.fillDifference(canvas, view.getLeft() + opticalInsets.left, view.getTop() + opticalInsets.top, view.getRight() - opticalInsets.right, view.getBottom() - opticalInsets.bottom, this.leftMargin, this.topMargin, this.rightMargin, this.bottomMargin, paint);
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("leftMargin", this.leftMargin);
            viewHierarchyEncoder.addProperty("topMargin", this.topMargin);
            viewHierarchyEncoder.addProperty("rightMargin", this.rightMargin);
            viewHierarchyEncoder.addProperty("bottomMargin", this.bottomMargin);
            viewHierarchyEncoder.addProperty("startMargin", this.startMargin);
            viewHierarchyEncoder.addProperty("endMargin", this.endMargin);
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ViewGroup> {
        private int mAddStatesFromChildrenId;
        private int mAlwaysDrawnWithCacheId;
        private int mAnimationCacheId;
        private int mClipChildrenId;
        private int mClipToPaddingId;
        private int mDescendantFocusabilityId;
        private int mLayoutAnimationId;
        private int mLayoutModeId;
        private int mPersistentDrawingCacheId;
        private boolean mPropertiesMapped = false;
        private int mSplitMotionEventsId;
        private int mTouchscreenBlocksFocusId;
        private int mTransitionGroupId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAddStatesFromChildrenId = propertyMapper.mapBoolean("addStatesFromChildren", 16842992);
            this.mAlwaysDrawnWithCacheId = propertyMapper.mapBoolean("alwaysDrawnWithCache", 16842991);
            this.mAnimationCacheId = propertyMapper.mapBoolean("animationCache", 16842989);
            this.mClipChildrenId = propertyMapper.mapBoolean("clipChildren", 16842986);
            this.mClipToPaddingId = propertyMapper.mapBoolean("clipToPadding", 16842987);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(131072, "beforeDescendants");
            sparseArray.put(262144, "afterDescendants");
            sparseArray.put(393216, "blocksDescendants");
            this.mDescendantFocusabilityId = propertyMapper.mapIntEnum("descendantFocusability", 16842993, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mLayoutAnimationId = propertyMapper.mapObject("layoutAnimation", 16842988);
            SparseArray sparseArray2 = new SparseArray();
            sparseArray2.put(0, "clipBounds");
            sparseArray2.put(1, "opticalBounds");
            this.mLayoutModeId = propertyMapper.mapIntEnum("layoutMode", 16843738, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            SparseArray sparseArray3 = new SparseArray();
            sparseArray3.put(0, "none");
            sparseArray3.put(1, AppJankStats.WIDGET_CATEGORY_ANIMATION);
            sparseArray3.put(2, AppJankStats.WIDGET_STATE_SCROLLING);
            sparseArray3.put(3, "all");
            this.mPersistentDrawingCacheId = propertyMapper.mapIntEnum("persistentDrawingCache", 16842990, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray3));
            this.mSplitMotionEventsId = propertyMapper.mapBoolean("splitMotionEvents", 16843503);
            this.mTouchscreenBlocksFocusId = propertyMapper.mapBoolean("touchscreenBlocksFocus", 16843919);
            this.mTransitionGroupId = propertyMapper.mapBoolean("transitionGroup", 16843777);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ViewGroup viewGroup, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAddStatesFromChildrenId, viewGroup.addStatesFromChildren());
            propertyReader.readBoolean(this.mAlwaysDrawnWithCacheId, viewGroup.isAlwaysDrawnWithCacheEnabled());
            propertyReader.readBoolean(this.mAnimationCacheId, viewGroup.isAnimationCacheEnabled());
            propertyReader.readBoolean(this.mClipChildrenId, viewGroup.getClipChildren());
            propertyReader.readBoolean(this.mClipToPaddingId, viewGroup.getClipToPadding());
            propertyReader.readIntEnum(this.mDescendantFocusabilityId, viewGroup.getDescendantFocusability());
            propertyReader.readObject(this.mLayoutAnimationId, viewGroup.getLayoutAnimation());
            propertyReader.readIntEnum(this.mLayoutModeId, viewGroup.getLayoutMode());
            propertyReader.readIntEnum(this.mPersistentDrawingCacheId, viewGroup.getPersistentDrawingCache());
            propertyReader.readBoolean(this.mSplitMotionEventsId, viewGroup.isMotionEventSplittingEnabled());
            propertyReader.readBoolean(this.mTouchscreenBlocksFocusId, viewGroup.getTouchscreenBlocksFocus());
            propertyReader.readBoolean(this.mTransitionGroupId, viewGroup.isTransitionGroup());
        }
    }

    public ViewGroup(Context context) {
        this(context, null);
    }

    public ViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewGroup(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ViewGroup(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.mLastTouchDownIndex = -1;
        this.mLayoutMode = -1;
        this.mSuppressLayout = false;
        this.mLayoutCalledWhileSuppressed = false;
        this.mChildCountWithTransientState = 0;
        this.mTransientIndices = null;
        this.mTransientViews = null;
        this.mChildUnhandledKeyListeners = 0;
        this.mInsetsAnimationDispatchMode = 1;
        this.mSemHorizontalScrollbarRectRelativePosX = 0;
        this.mSemVerticalScrollbarRectRelativePosY = 0;
        this.mLayoutTransitionListener = new LayoutTransition.TransitionListener() { // from class: android.view.ViewGroup.4
            @Override // android.animation.LayoutTransition.TransitionListener
            public void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i3) {
                if (i3 == 3) {
                    ViewGroup.this.startViewTransition(view);
                }
            }

            @Override // android.animation.LayoutTransition.TransitionListener
            public void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i3) {
                if (ViewGroup.this.mLayoutCalledWhileSuppressed && !layoutTransition.isChangingLayout()) {
                    ViewGroup.this.requestLayout();
                    ViewGroup.this.mLayoutCalledWhileSuppressed = false;
                }
                if (i3 != 3 || ViewGroup.this.mTransitioningViews == null) {
                    return;
                }
                ViewGroup.this.endViewTransition(view);
            }
        };
        initViewGroup();
        initFromAttributes(context, attributeSet, i, i2);
    }

    private void initViewGroup() {
        if (!isShowingLayoutBounds()) {
            setFlags(128, 128);
        }
        this.mGroupFlags |= 2113619;
        setDescendantFocusability(131072);
        this.mChildren = new View[12];
        this.mChildrenCount = 0;
        this.mPersistentDrawingCache = 2;
    }

    private void initFromAttributes(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewGroup, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ViewGroup, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i3);
            switch (index) {
                case 0:
                    setClipChildren(typedArrayObtainStyledAttributes.getBoolean(index, true));
                    break;
                case 1:
                    setClipToPadding(typedArrayObtainStyledAttributes.getBoolean(index, true));
                    break;
                case 2:
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    if (resourceId > 0) {
                        setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this.mContext, resourceId));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    setAnimationCacheEnabled(typedArrayObtainStyledAttributes.getBoolean(index, true));
                    break;
                case 4:
                    setPersistentDrawingCache(typedArrayObtainStyledAttributes.getInt(index, 2));
                    break;
                case 5:
                    setAlwaysDrawnWithCacheEnabled(typedArrayObtainStyledAttributes.getBoolean(index, true));
                    break;
                case 6:
                    setAddStatesFromChildren(typedArrayObtainStyledAttributes.getBoolean(index, false));
                    break;
                case 7:
                    setDescendantFocusability(DESCENDANT_FOCUSABILITY_FLAGS[typedArrayObtainStyledAttributes.getInt(index, 0)]);
                    break;
                case 8:
                    setMotionEventSplittingEnabled(typedArrayObtainStyledAttributes.getBoolean(index, false));
                    break;
                case 9:
                    if (typedArrayObtainStyledAttributes.getBoolean(index, false)) {
                        setLayoutTransition(new LayoutTransition());
                        break;
                    } else {
                        break;
                    }
                case 10:
                    setLayoutMode(typedArrayObtainStyledAttributes.getInt(index, -1));
                    break;
                case 11:
                    setTransitionGroup(typedArrayObtainStyledAttributes.getBoolean(index, false));
                    break;
                case 12:
                    setTouchscreenBlocksFocus(typedArrayObtainStyledAttributes.getBoolean(index, false));
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @ViewDebug.ExportedProperty(category = "focus", mapping = {@ViewDebug.IntToString(from = 131072, to = "FOCUS_BEFORE_DESCENDANTS"), @ViewDebug.IntToString(from = 262144, to = "FOCUS_AFTER_DESCENDANTS"), @ViewDebug.IntToString(from = 393216, to = "FOCUS_BLOCK_DESCENDANTS")})
    public int getDescendantFocusability() {
        return this.mGroupFlags & 393216;
    }

    public void setDescendantFocusability(int i) {
        if (i != 131072 && i != 262144 && i != 393216) {
            throw new IllegalArgumentException("must be one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS");
        }
        this.mGroupFlags = (i & 393216) | (this.mGroupFlags & (-393217));
    }

    @Override // android.view.View
    void handleFocusGainInternal(int i, Rect rect) throws Resources.NotFoundException {
        View view = this.mFocused;
        if (view != null) {
            view.unFocus(this);
            this.mFocused = null;
            this.mFocusedInCluster = null;
        }
        super.handleFocusGainInternal(i, rect);
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        super.unFocus(view2);
        View view3 = this.mFocused;
        if (view3 != view) {
            if (view3 != null) {
                view3.unFocus(view2);
            }
            this.mFocused = view;
        }
        if (this.mParent != null) {
            this.mParent.requestChildFocus(this, view2);
        }
    }

    void setDefaultFocus(View view) {
        View view2 = this.mDefaultFocus;
        if (view2 == null || !view2.isFocusedByDefault()) {
            this.mDefaultFocus = view;
            if (this.mParent instanceof ViewGroup) {
                ((ViewGroup) this.mParent).setDefaultFocus(this);
            }
        }
    }

    void clearDefaultFocus(View view) {
        View view2 = this.mDefaultFocus;
        if (view2 == view || view2 == null || !view2.isFocusedByDefault()) {
            this.mDefaultFocus = null;
            for (int i = 0; i < this.mChildrenCount; i++) {
                View view3 = this.mChildren[i];
                if (view3.isFocusedByDefault()) {
                    this.mDefaultFocus = view3;
                    return;
                }
                if (this.mDefaultFocus == null && view3.hasDefaultFocus()) {
                    this.mDefaultFocus = view3;
                }
            }
            if (this.mParent instanceof ViewGroup) {
                ((ViewGroup) this.mParent).clearDefaultFocus(this);
            }
        }
    }

    @Override // android.view.View
    boolean hasDefaultFocus() {
        return this.mDefaultFocus != null || super.hasDefaultFocus();
    }

    void clearFocusedInCluster(View view) {
        if (this.mFocusedInCluster != view) {
            return;
        }
        clearFocusedInCluster();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.view.ViewParent] */
    void clearFocusedInCluster() {
        View viewFindKeyboardNavigationCluster = findKeyboardNavigationCluster();
        do {
            this.mFocusedInCluster = null;
            if (this == viewFindKeyboardNavigationCluster) {
                return;
            } else {
                this = this.getParent();
            }
        } while (this instanceof ViewGroup);
    }

    @Override // android.view.ViewParent
    public void focusableViewAvailable(View view) {
        if (this.mParent == null || getDescendantFocusability() == 393216 || (this.mViewFlags & 12) != 0) {
            return;
        }
        if (isFocusableInTouchMode() || !shouldBlockFocusForTouchscreen()) {
            if (!isFocused() || getDescendantFocusability() == 262144) {
                this.mParent.focusableViewAvailable(view);
            }
        }
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View view) {
        return (isShowingContextMenuWithCoords() || this.mParent == null || !this.mParent.showContextMenuForChild(view)) ? false : true;
    }

    public final boolean isShowingContextMenuWithCoords() {
        return (this.mGroupFlags & 536870912) != 0;
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View view, float f, float f2) {
        try {
            this.mGroupFlags |= 536870912;
            if (showContextMenuForChild(view)) {
                return true;
            }
            this.mGroupFlags = (-536870913) & this.mGroupFlags;
            return this.mParent != null && this.mParent.showContextMenuForChild(view, f, f2);
        } finally {
            this.mGroupFlags &= -536870913;
        }
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        int i = this.mGroupFlags;
        if ((134217728 & i) == 0) {
            try {
                this.mGroupFlags = i | 268435456;
                return startActionModeForChild(view, callback, 0);
            } finally {
                this.mGroupFlags &= -268435457;
            }
        }
        return SENTINEL_ACTION_MODE;
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        int i2 = this.mGroupFlags;
        if ((268435456 & i2) == 0 && i == 0) {
            try {
                this.mGroupFlags = i2 | 134217728;
                ActionMode actionModeStartActionModeForChild = startActionModeForChild(view, callback);
                this.mGroupFlags = (-134217729) & this.mGroupFlags;
                if (actionModeStartActionModeForChild != SENTINEL_ACTION_MODE) {
                    return actionModeStartActionModeForChild;
                }
            } catch (Throwable th) {
                this.mGroupFlags &= -134217729;
                throw th;
            }
        }
        if (this.mParent == null) {
            return null;
        }
        try {
            return this.mParent.startActionModeForChild(view, callback, i);
        } catch (AbstractMethodError unused) {
            return this.mParent.startActionModeForChild(view, callback);
        }
    }

    @Override // android.view.View
    public boolean dispatchActivityResult(String str, int i, int i2, Intent intent) {
        if (super.dispatchActivityResult(str, i, i2, intent)) {
            return true;
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            if (getChildAt(i3).dispatchActivityResult(str, i, i2, intent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewParent
    public View focusSearch(View view, int i) {
        if (isRootNamespace()) {
            return FocusFinder.getInstance().findNextFocus(this, view, i);
        }
        if (this.mParent != null) {
            return this.mParent.focusSearch(view, i);
        }
        return null;
    }

    @Override // android.view.ViewParent
    public boolean requestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        ViewParent viewParent = this.mParent;
        if (viewParent != null && onRequestSendAccessibilityEvent(view, accessibilityEvent)) {
            return viewParent.requestSendAccessibilityEvent(this, accessibilityEvent);
        }
        return false;
    }

    public boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.onRequestSendAccessibilityEvent(this, view, accessibilityEvent);
        }
        return onRequestSendAccessibilityEventInternal(view, accessibilityEvent);
    }

    @Override // android.view.ViewParent
    public void childHasTransientStateChanged(View view, boolean z) {
        boolean zHasTransientState = hasTransientState();
        if (z) {
            this.mChildCountWithTransientState++;
        } else {
            this.mChildCountWithTransientState--;
        }
        boolean zHasTransientState2 = hasTransientState();
        if (this.mParent == null || zHasTransientState == zHasTransientState2) {
            return;
        }
        try {
            this.mParent.childHasTransientStateChanged(this, zHasTransientState2);
        } catch (AbstractMethodError e) {
            Log.e(TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
        }
    }

    @Override // android.view.View
    public boolean hasTransientState() {
        return this.mChildCountWithTransientState > 0 || super.hasTransientState();
    }

    @Override // android.view.View
    public boolean dispatchUnhandledMove(View view, int i) {
        View view2 = this.mFocused;
        return view2 != null && view2.dispatchUnhandledMove(view, i);
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(View view) {
        this.mFocused = null;
        if (this.mParent != null) {
            this.mParent.clearChildFocus(this);
        }
    }

    @Override // android.view.View
    public void clearFocus() throws Resources.NotFoundException {
        View view = this.mFocused;
        if (view == null) {
            super.clearFocus();
        } else {
            this.mFocused = null;
            view.clearFocus();
        }
    }

    @Override // android.view.View
    void unFocus(View view) throws Resources.NotFoundException {
        View view2 = this.mFocused;
        if (view2 == null) {
            super.unFocus(view);
        } else {
            view2.unFocus(view);
            this.mFocused = null;
        }
    }

    public View getFocusedChild() {
        return this.mFocused;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    View getDeepestFocusedChild() {
        while (this != 0) {
            if (this.isFocused()) {
                return this;
            }
            this = this instanceof ViewGroup ? ((ViewGroup) this).getFocusedChild() : 0;
        }
        return null;
    }

    @Override // android.view.View
    public boolean hasFocus() {
        return ((this.mPrivateFlags & 2) == 0 && this.mFocused == null) ? false : true;
    }

    @Override // android.view.View
    public View findFocus() {
        if (isFocused()) {
            return this;
        }
        View view = this.mFocused;
        if (view != null) {
            return view.findFocus();
        }
        return null;
    }

    @Override // android.view.View
    boolean hasFocusable(boolean z, boolean z2) {
        if ((this.mViewFlags & 12) != 0) {
            return false;
        }
        if ((z || getFocusable() != 16) && isFocusable()) {
            return true;
        }
        if (getDescendantFocusability() != 393216) {
            return hasFocusableChild(z2);
        }
        return false;
    }

    boolean hasFocusableChild(boolean z) {
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if (z && view.hasExplicitFocusable()) {
                return true;
            }
            if (!z && view.hasFocusable()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        boolean zShouldBlockFocusForTouchscreen = shouldBlockFocusForTouchscreen();
        boolean z = isFocusableInTouchMode() || !zShouldBlockFocusForTouchscreen;
        if (descendantFocusability == 393216) {
            if (z) {
                super.addFocusables(arrayList, i, i2);
                return;
            }
            return;
        }
        if (zShouldBlockFocusForTouchscreen) {
            i2 |= 1;
        }
        if (descendantFocusability == 131072 && z) {
            super.addFocusables(arrayList, i, i2);
        }
        View[] viewArr = new View[this.mChildrenCount];
        int i3 = 0;
        for (int i4 = 0; i4 < this.mChildrenCount; i4++) {
            View view = this.mChildren[i4];
            if ((view.mViewFlags & 12) == 0) {
                viewArr[i3] = view;
                i3++;
            }
        }
        FocusFinder.sort(viewArr, 0, i3, this, isLayoutRtl());
        for (int i5 = 0; i5 < i3; i5++) {
            viewArr[i5].addFocusables(arrayList, i, i2);
        }
        if (descendantFocusability == 262144 && z && size == arrayList.size()) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    @Override // android.view.View
    public void addKeyboardNavigationClusters(Collection<View> collection, int i) {
        int size = collection.size();
        if (isKeyboardNavigationCluster()) {
            boolean touchscreenBlocksFocus = getTouchscreenBlocksFocus();
            try {
                setTouchscreenBlocksFocusNoRefocus(false);
                super.addKeyboardNavigationClusters(collection, i);
            } finally {
                setTouchscreenBlocksFocusNoRefocus(touchscreenBlocksFocus);
            }
        } else {
            super.addKeyboardNavigationClusters(collection, i);
        }
        if (size == collection.size() && getDescendantFocusability() != 393216) {
            View[] viewArr = new View[this.mChildrenCount];
            int i2 = 0;
            for (int i3 = 0; i3 < this.mChildrenCount; i3++) {
                View view = this.mChildren[i3];
                if ((view.mViewFlags & 12) == 0) {
                    viewArr[i2] = view;
                    i2++;
                }
            }
            FocusFinder.sort(viewArr, 0, i2, this, isLayoutRtl());
            for (int i4 = 0; i4 < i2; i4++) {
                viewArr[i4].addKeyboardNavigationClusters(collection, i);
            }
        }
    }

    public void setTouchscreenBlocksFocus(boolean z) throws Resources.NotFoundException {
        View viewFocusSearch;
        if (z) {
            this.mGroupFlags |= 67108864;
            if (!hasFocus() || isKeyboardNavigationCluster() || getDeepestFocusedChild().isFocusableInTouchMode() || (viewFocusSearch = focusSearch(2)) == null) {
                return;
            }
            viewFocusSearch.requestFocus();
            return;
        }
        this.mGroupFlags &= -67108865;
    }

    private void setTouchscreenBlocksFocusNoRefocus(boolean z) {
        if (z) {
            this.mGroupFlags |= 67108864;
        } else {
            this.mGroupFlags &= -67108865;
        }
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public boolean getTouchscreenBlocksFocus() {
        return (this.mGroupFlags & 67108864) != 0;
    }

    boolean shouldBlockFocusForTouchscreen() {
        if (!getTouchscreenBlocksFocus() || !this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)) {
            return false;
        }
        if (isKeyboardNavigationCluster()) {
            return !hasFocus() && findKeyboardNavigationCluster() == this;
        }
        return true;
    }

    @Override // android.view.View
    public void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int i) {
        super.findViewsWithText(arrayList, charSequence, i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            View view = viewArr[i3];
            if ((view.mViewFlags & 12) == 0 && (view.mPrivateFlags & 8) == 0) {
                view.findViewsWithText(arrayList, charSequence, i);
            }
        }
    }

    @Override // android.view.View
    public View findViewByAccessibilityIdTraversal(int i) {
        View viewFindViewByAccessibilityIdTraversal = super.findViewByAccessibilityIdTraversal(i);
        if (viewFindViewByAccessibilityIdTraversal != null) {
            return viewFindViewByAccessibilityIdTraversal;
        }
        if (getAccessibilityNodeProvider() != null) {
            return null;
        }
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            View viewFindViewByAccessibilityIdTraversal2 = viewArr[i3].findViewByAccessibilityIdTraversal(i);
            if (viewFindViewByAccessibilityIdTraversal2 != null) {
                return viewFindViewByAccessibilityIdTraversal2;
            }
        }
        return null;
    }

    @Override // android.view.View
    public View findViewByAutofillIdTraversal(int i) {
        View viewFindViewByAutofillIdTraversal = super.findViewByAutofillIdTraversal(i);
        if (viewFindViewByAutofillIdTraversal != null) {
            return viewFindViewByAutofillIdTraversal;
        }
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            View viewFindViewByAutofillIdTraversal2 = viewArr[i3].findViewByAutofillIdTraversal(i);
            if (viewFindViewByAutofillIdTraversal2 != null) {
                return viewFindViewByAutofillIdTraversal2;
            }
        }
        return null;
    }

    @Override // android.view.View
    public void findAutofillableViewsByTraversal(List<View> list) {
        super.findAutofillableViewsByTraversal(list);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].findAutofillableViewsByTraversal(list);
        }
    }

    @Override // android.view.View
    public void dispatchWindowFocusChanged(boolean z) {
        super.dispatchWindowFocusChanged(z);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchWindowFocusChanged(z);
        }
    }

    @Override // android.view.View
    public void addTouchables(ArrayList<View> arrayList) {
        super.addTouchables(arrayList);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if ((view.mViewFlags & 12) == 0) {
                view.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.View
    public void makeOptionalFitsSystemWindows() throws Resources.NotFoundException {
        super.makeOptionalFitsSystemWindows();
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].makeOptionalFitsSystemWindows();
        }
    }

    @Override // android.view.View
    public void makeFrameworkOptionalFitsSystemWindows() {
        super.makeFrameworkOptionalFitsSystemWindows();
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].makeFrameworkOptionalFitsSystemWindows();
        }
    }

    @Override // android.view.View
    public void dispatchDisplayHint(int i) {
        super.dispatchDisplayHint(i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchDisplayHint(i);
        }
    }

    protected void onChildVisibilityChanged(View view, int i, int i2) {
        LayoutTransition layoutTransition = this.mTransition;
        if (layoutTransition != null) {
            if (i2 == 0) {
                layoutTransition.showChild(this, view, i);
            } else {
                layoutTransition.hideChild(this, view, i2);
                ArrayList<View> arrayList = this.mTransitioningViews;
                if (arrayList != null && arrayList.contains(view)) {
                    if (this.mVisibilityChangingChildren == null) {
                        this.mVisibilityChangingChildren = new ArrayList<>();
                    }
                    this.mVisibilityChangingChildren.add(view);
                    addDisappearingView(view);
                }
            }
        }
        if (i2 == 0 && this.mCurrentDragStartEvent != null) {
            if (this.mChildrenInterestedInDrag.contains(view)) {
                return;
            }
            notifyChildOfDragStart(view);
        } else {
            if (i2 != 0 || getParent() == null) {
                return;
            }
            getParent().requestSendStickyDragStartedEvent(this);
        }
    }

    @Override // android.view.View
    protected void dispatchVisibilityChanged(View view, int i) {
        super.dispatchVisibilityChanged(view, i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchVisibilityChanged(view, i);
        }
    }

    @Override // android.view.View
    public void dispatchWindowVisibilityChanged(int i) {
        super.dispatchWindowVisibilityChanged(i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchWindowVisibilityChanged(i);
        }
    }

    @Override // android.view.View
    boolean dispatchVisibilityAggregated(boolean z) {
        boolean zDispatchVisibilityAggregated = super.dispatchVisibilityAggregated(z);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (viewArr[i2].getVisibility() == 0) {
                viewArr[i2].dispatchVisibilityAggregated(zDispatchVisibilityAggregated);
            }
        }
        return zDispatchVisibilityAggregated;
    }

    @Override // android.view.View
    public void dispatchConfigurationChanged(Configuration configuration) {
        super.dispatchConfigurationChanged(configuration);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if (view != null) {
                view.dispatchConfigurationChanged(configuration);
            } else {
                Log.e(TAG, "child of ViewGroup " + this + " was removed during dispatching, index : " + i2 + ", count : " + i);
            }
        }
    }

    @Override // android.view.ViewParent
    public void recomputeViewAttributes(View view) {
        ViewParent viewParent;
        if (this.mAttachInfo == null || this.mAttachInfo.mRecomputeGlobalAttributes || (viewParent = this.mParent) == null) {
            return;
        }
        viewParent.recomputeViewAttributes(this);
    }

    @Override // android.view.View
    void dispatchCollectViewAttributes(View.AttachInfo attachInfo, int i) {
        if ((i & 12) == 0) {
            super.dispatchCollectViewAttributes(attachInfo, i);
            int i2 = this.mChildrenCount;
            View[] viewArr = this.mChildren;
            for (int i3 = 0; i3 < i2; i3++) {
                View view = viewArr[i3];
                view.dispatchCollectViewAttributes(attachInfo, (view.mViewFlags & 12) | i);
            }
        }
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(View view) {
        int iIndexOfChild = indexOfChild(view);
        if (iIndexOfChild >= 0) {
            removeFromArray(iIndexOfChild);
            addInArray(view, this.mChildrenCount);
            view.mParent = this;
            requestLayout();
            invalidate();
        }
    }

    private PointF getLocalPoint() {
        if (this.mLocalPoint == null) {
            this.mLocalPoint = new PointF();
        }
        return this.mLocalPoint;
    }

    @Override // android.view.View
    boolean dispatchDragEnterExitInPreN(DragEvent dragEvent) {
        View view;
        if (dragEvent.mAction == 6 && (view = this.mCurrentDragChild) != null) {
            view.dispatchDragEnterExitInPreN(dragEvent);
            this.mCurrentDragChild = null;
        }
        return this.mIsInterestedInDrag && super.dispatchDragEnterExitInPreN(dragEvent);
    }

    @Override // android.view.View
    public boolean dispatchDragEvent(DragEvent dragEvent) {
        boolean z;
        boolean z2;
        float f = dragEvent.mX;
        float f2 = dragEvent.mY;
        ClipData clipData = dragEvent.mClipData;
        PointF localPoint = getLocalPoint();
        int i = dragEvent.mAction;
        if (i == 1) {
            this.mCurrentDragChild = null;
            this.mCurrentDragStartEvent = DragEvent.obtain(dragEvent);
            HashSet<View> hashSet = this.mChildrenInterestedInDrag;
            if (hashSet == null) {
                this.mChildrenInterestedInDrag = new HashSet<>();
            } else {
                hashSet.clear();
            }
            int i2 = this.mChildrenCount;
            View[] viewArr = this.mChildren;
            boolean z3 = false;
            for (int i3 = 0; i3 < i2; i3++) {
                View view = viewArr[i3];
                if (view != null) {
                    view.mPrivateFlags2 &= -4;
                    if (view.getVisibility() == 0 && notifyChildOfDragStart(viewArr[i3])) {
                        z3 = true;
                    }
                }
            }
            boolean zDispatchDragEvent = super.dispatchDragEvent(dragEvent);
            this.mIsInterestedInDrag = zDispatchDragEvent;
            z = zDispatchDragEvent ? true : z3;
            if (!z) {
                this.mCurrentDragStartEvent.recycle();
                this.mCurrentDragStartEvent = null;
            }
            return z;
        }
        if (i == 2 || i == 3) {
            View viewFindFrontmostDroppableChildAt = findFrontmostDroppableChildAt(dragEvent.mX, dragEvent.mY, localPoint);
            if (sCascadedDragDrop && viewFindFrontmostDroppableChildAt != this.mCurrentDragChild) {
                int i4 = dragEvent.mAction;
                dragEvent.mX = 0.0f;
                dragEvent.mY = 0.0f;
                dragEvent.mClipData = null;
                if (this.mCurrentDragChild != null) {
                    dragEvent.mAction = 6;
                    this.mCurrentDragChild.dispatchDragEnterExitInPreN(dragEvent);
                }
                if (viewFindFrontmostDroppableChildAt != null) {
                    dragEvent.mAction = 5;
                    viewFindFrontmostDroppableChildAt.dispatchDragEnterExitInPreN(dragEvent);
                }
                dragEvent.mAction = i4;
                dragEvent.mX = f;
                dragEvent.mY = f2;
                dragEvent.mClipData = clipData;
                this.mCurrentDragChild = viewFindFrontmostDroppableChildAt;
            }
            if (viewFindFrontmostDroppableChildAt == null && this.mIsInterestedInDrag) {
                viewFindFrontmostDroppableChildAt = this;
            }
            if (viewFindFrontmostDroppableChildAt != null) {
                if (viewFindFrontmostDroppableChildAt != this) {
                    dragEvent.mX = localPoint.x;
                    dragEvent.mY = localPoint.y;
                    boolean zDispatchDragEvent2 = viewFindFrontmostDroppableChildAt.dispatchDragEvent(dragEvent);
                    dragEvent.mX = f;
                    dragEvent.mY = f2;
                    if (this.mIsInterestedInDrag) {
                        if (!(sCascadedDragDrop ? zDispatchDragEvent2 : dragEvent.mEventHandlerWasCalled)) {
                            return super.dispatchDragEvent(dragEvent);
                        }
                    }
                    return zDispatchDragEvent2;
                }
                return super.dispatchDragEvent(dragEvent);
            }
        } else if (i == 4) {
            HashSet<View> hashSet2 = this.mChildrenInterestedInDrag;
            if (hashSet2 != null) {
                Iterator<View> it = hashSet2.iterator();
                z2 = false;
                while (it.hasNext()) {
                    if (it.next().dispatchDragEvent(dragEvent)) {
                        z2 = true;
                    }
                }
                hashSet2.clear();
            } else {
                z2 = false;
            }
            DragEvent dragEvent2 = this.mCurrentDragStartEvent;
            if (dragEvent2 != null) {
                dragEvent2.recycle();
                this.mCurrentDragStartEvent = null;
            }
            if (!this.mIsInterestedInDrag) {
                return z2;
            }
            z = super.dispatchDragEvent(dragEvent) ? true : z2;
            this.mIsInterestedInDrag = false;
            return z;
        }
        return false;
    }

    View findFrontmostDroppableChildAt(float f, float f2, PointF pointF) {
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            View view = viewArr[i2];
            if (view.canAcceptDrag() && isTransformedTouchPointInView(f, f2, view, pointF)) {
                return view;
            }
        }
        return null;
    }

    boolean notifyChildOfDragStart(View view) {
        float f = this.mCurrentDragStartEvent.mX;
        float f2 = this.mCurrentDragStartEvent.mY;
        float[] tempLocationF = getTempLocationF();
        tempLocationF[0] = f;
        tempLocationF[1] = f2;
        transformPointToViewLocal(tempLocationF, view);
        this.mCurrentDragStartEvent.mX = tempLocationF[0];
        this.mCurrentDragStartEvent.mY = tempLocationF[1];
        boolean zDispatchDragEvent = view.dispatchDragEvent(this.mCurrentDragStartEvent);
        this.mCurrentDragStartEvent.mX = f;
        this.mCurrentDragStartEvent.mY = f2;
        this.mCurrentDragStartEvent.mEventHandlerWasCalled = false;
        if (zDispatchDragEvent) {
            this.mChildrenInterestedInDrag.add(view);
            if (!view.canAcceptDrag()) {
                view.mPrivateFlags2 |= 1;
                view.refreshDrawableState();
            }
        }
        return zDispatchDragEvent;
    }

    @Override // android.view.View
    @Deprecated
    public void dispatchWindowSystemUiVisiblityChanged(int i) {
        super.dispatchWindowSystemUiVisiblityChanged(i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchWindowSystemUiVisiblityChanged(i);
        }
    }

    @Override // android.view.View
    @Deprecated
    public void dispatchSystemUiVisibilityChanged(int i) {
        super.dispatchSystemUiVisibilityChanged(i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchSystemUiVisibilityChanged(i);
        }
    }

    @Override // android.view.View
    boolean updateLocalSystemUiVisibility(int i, int i2) {
        boolean zUpdateLocalSystemUiVisibility = super.updateLocalSystemUiVisibility(i, i2);
        int i3 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i4 = 0; i4 < i3; i4++) {
            zUpdateLocalSystemUiVisibility |= viewArr[i4].updateLocalSystemUiVisibility(i, i2);
        }
        return zUpdateLocalSystemUiVisibility;
    }

    @Override // android.view.View
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchKeyEventPreIme(keyEvent);
        }
        View view = this.mFocused;
        if (view == null || (view.mPrivateFlags & 16) != 16) {
            return false;
        }
        return this.mFocused.dispatchKeyEventPreIme(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onKeyEvent(keyEvent, 1);
        }
        if ((this.mPrivateFlags & 18) == 18) {
            if (super.dispatchKeyEvent(keyEvent)) {
                return true;
            }
        } else {
            View view = this.mFocused;
            if (view != null && (view.mPrivateFlags & 16) == 16 && this.mFocused.dispatchKeyEvent(keyEvent)) {
                return true;
            }
        }
        if (this.mInputEventConsistencyVerifier == null) {
            return false;
        }
        this.mInputEventConsistencyVerifier.onUnhandledEvent(keyEvent, 1);
        return false;
    }

    @Override // android.view.View
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchKeyShortcutEvent(keyEvent);
        }
        View view = this.mFocused;
        if (view == null || (view.mPrivateFlags & 16) != 16) {
            return false;
        }
        return this.mFocused.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTrackballEvent(motionEvent, 1);
        }
        if ((this.mPrivateFlags & 18) == 18) {
            if (super.dispatchTrackballEvent(motionEvent)) {
                return true;
            }
        } else {
            View view = this.mFocused;
            if (view != null && (view.mPrivateFlags & 16) == 16 && this.mFocused.dispatchTrackballEvent(motionEvent)) {
                return true;
            }
        }
        if (this.mInputEventConsistencyVerifier == null) {
            return false;
        }
        this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 1);
        return false;
    }

    @Override // android.view.View
    public boolean dispatchCapturedPointerEvent(MotionEvent motionEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchCapturedPointerEvent(motionEvent);
        }
        View view = this.mFocused;
        return view != null && (view.mPrivateFlags & 16) == 16 && this.mFocused.dispatchCapturedPointerEvent(motionEvent);
    }

    @Override // android.view.View
    public void dispatchPointerCaptureChanged(boolean z) {
        exitHoverTargets();
        super.dispatchPointerCaptureChanged(z);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchPointerCaptureChanged(z);
        }
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        PointerIcon pointerIconDispatchResolvePointerIcon;
        float xDispatchLocation = motionEvent.getXDispatchLocation(i);
        float yDispatchLocation = motionEvent.getYDispatchLocation(i);
        if (isOnScrollbarThumb(xDispatchLocation, yDispatchLocation) || isDraggingScrollBar()) {
            return null;
        }
        int i2 = this.mChildrenCount;
        if (i2 != 0) {
            ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
            boolean z = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
            View[] viewArr = this.mChildren;
            for (int i3 = i2 - 1; i3 >= 0; i3--) {
                View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i2, i3, z));
                if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null) && (pointerIconDispatchResolvePointerIcon = dispatchResolvePointerIcon(motionEvent, i, andVerifyPreorderedView)) != null) {
                    if (arrayListBuildOrderedChildList != null) {
                        arrayListBuildOrderedChildList.clear();
                    }
                    return pointerIconDispatchResolvePointerIcon;
                }
            }
            if (arrayListBuildOrderedChildList != null) {
                arrayListBuildOrderedChildList.clear();
            }
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private PointerIcon dispatchResolvePointerIcon(MotionEvent motionEvent, int i, View view) {
        if (!view.hasIdentityMatrix()) {
            MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            PointerIcon pointerIconOnResolvePointerIcon = view.onResolvePointerIcon(transformedMotionEvent, i);
            transformedMotionEvent.recycle();
            return pointerIconOnResolvePointerIcon;
        }
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent.offsetLocation(f, f2);
        PointerIcon pointerIconOnResolvePointerIcon2 = view.onResolvePointerIcon(motionEvent, i);
        motionEvent.offsetLocation(-f, -f2);
        return pointerIconOnResolvePointerIcon2;
    }

    private int getAndVerifyPreorderedIndex(int i, int i2, boolean z) {
        if (!z) {
            return i2;
        }
        int childDrawingOrder = getChildDrawingOrder(i, i2);
        if (childDrawingOrder < i) {
            return childDrawingOrder;
        }
        throw new IndexOutOfBoundsException("getChildDrawingOrder() returned invalid index " + childDrawingOrder + " (child count is " + i + NavigationBarInflaterView.KEY_CODE_END);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00c0 A[EDGE_INSN: B:94:0x00c0->B:47:0x00c0 BREAK  A[LOOP:0: B:14:0x0041->B:46:0x00ba], SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2;
        boolean zDispatchHoverEvent;
        MotionEvent motionEventObtainMotionEventNoHistoryOrSelf;
        boolean z;
        boolean zDispatchTransformedGenericPointerEvent;
        int action = motionEvent.getAction();
        boolean zOnInterceptHoverEvent = onInterceptHoverEvent(motionEvent);
        motionEvent.setAction(action);
        HoverTarget hoverTarget = this.mFirstHoverTarget;
        PointF pointF = null;
        this.mFirstHoverTarget = null;
        if (zOnInterceptHoverEvent || action == 10) {
            motionEvent2 = motionEvent;
            zDispatchHoverEvent = false;
        } else {
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            int i = this.mChildrenCount;
            if (i != 0) {
                ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
                boolean z2 = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
                View[] viewArr = this.mChildren;
                motionEvent2 = motionEvent;
                HoverTarget hoverTarget2 = null;
                zDispatchHoverEvent = false;
                int i2 = i - 1;
                while (i2 >= 0) {
                    View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z2));
                    if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, pointF)) {
                        HoverTarget hoverTargetObtain = hoverTarget;
                        HoverTarget hoverTarget3 = pointF;
                        while (true) {
                            if (hoverTargetObtain == null) {
                                hoverTargetObtain = HoverTarget.obtain(andVerifyPreorderedView);
                                z = false;
                                break;
                            }
                            if (hoverTargetObtain.child == andVerifyPreorderedView) {
                                if (hoverTarget3 != 0) {
                                    hoverTarget3.next = hoverTargetObtain.next;
                                } else {
                                    hoverTarget = hoverTargetObtain.next;
                                }
                                hoverTargetObtain.next = null;
                                z = true;
                            } else {
                                hoverTarget3 = hoverTargetObtain;
                                hoverTargetObtain = hoverTargetObtain.next;
                            }
                        }
                        if (hoverTarget2 != null) {
                            hoverTarget2.next = hoverTargetObtain;
                        } else {
                            this.mFirstHoverTarget = hoverTargetObtain;
                        }
                        if (action == 9) {
                            if (!z) {
                                zDispatchTransformedGenericPointerEvent = zDispatchHoverEvent | dispatchTransformedGenericPointerEvent(motionEvent, andVerifyPreorderedView);
                                zDispatchHoverEvent = zDispatchTransformedGenericPointerEvent;
                            }
                            if (!zDispatchHoverEvent) {
                                break;
                            }
                            hoverTarget2 = hoverTargetObtain;
                        } else {
                            if (action == 7) {
                                if (!z) {
                                    MotionEvent motionEventObtainMotionEventNoHistoryOrSelf2 = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                                    motionEventObtainMotionEventNoHistoryOrSelf2.setAction(9);
                                    boolean zDispatchTransformedGenericPointerEvent2 = zDispatchHoverEvent | dispatchTransformedGenericPointerEvent(motionEventObtainMotionEventNoHistoryOrSelf2, andVerifyPreorderedView);
                                    motionEventObtainMotionEventNoHistoryOrSelf2.setAction(action);
                                    zDispatchTransformedGenericPointerEvent = zDispatchTransformedGenericPointerEvent2 | dispatchTransformedGenericPointerEvent(motionEventObtainMotionEventNoHistoryOrSelf2, andVerifyPreorderedView);
                                    motionEvent2 = motionEventObtainMotionEventNoHistoryOrSelf2;
                                    zDispatchHoverEvent = zDispatchTransformedGenericPointerEvent;
                                } else {
                                    zDispatchHoverEvent |= dispatchTransformedGenericPointerEvent(motionEvent, andVerifyPreorderedView);
                                }
                            }
                            if (!zDispatchHoverEvent) {
                            }
                        }
                    }
                    i2--;
                    pointF = null;
                }
                if (arrayListBuildOrderedChildList != null) {
                    arrayListBuildOrderedChildList.clear();
                }
            }
        }
        while (hoverTarget != null) {
            View view = hoverTarget.child;
            if (action == 10) {
                zDispatchHoverEvent |= dispatchTransformedGenericPointerEvent(motionEvent, view);
            } else {
                if (action == 7) {
                    boolean zIsHoverExitPending = motionEvent.isHoverExitPending();
                    motionEvent.setHoverExitPending(true);
                    dispatchTransformedGenericPointerEvent(motionEvent, view);
                    motionEvent.setHoverExitPending(zIsHoverExitPending);
                }
                MotionEvent motionEventObtainMotionEventNoHistoryOrSelf3 = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                motionEventObtainMotionEventNoHistoryOrSelf3.setAction(10);
                dispatchTransformedGenericPointerEvent(motionEventObtainMotionEventNoHistoryOrSelf3, view);
                motionEventObtainMotionEventNoHistoryOrSelf3.setAction(action);
                motionEvent2 = motionEventObtainMotionEventNoHistoryOrSelf3;
            }
            HoverTarget hoverTarget4 = hoverTarget.next;
            hoverTarget.recycle();
            hoverTarget = hoverTarget4;
        }
        boolean z3 = (zDispatchHoverEvent || action == 10 || motionEvent.isHoverExitPending()) ? false : true;
        boolean z4 = this.mHoveredSelf;
        if (z3 != z4) {
            if (z4) {
                if (action == 10) {
                    zDispatchHoverEvent |= super.dispatchHoverEvent(motionEvent);
                } else {
                    if (action == 7) {
                        super.dispatchHoverEvent(motionEvent);
                    }
                    MotionEvent motionEventObtainMotionEventNoHistoryOrSelf4 = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                    motionEventObtainMotionEventNoHistoryOrSelf4.setAction(10);
                    super.dispatchHoverEvent(motionEventObtainMotionEventNoHistoryOrSelf4);
                    motionEventObtainMotionEventNoHistoryOrSelf4.setAction(action);
                    motionEvent2 = motionEventObtainMotionEventNoHistoryOrSelf4;
                }
                this.mHoveredSelf = false;
            }
            if (z3) {
                if (action == 9) {
                    zDispatchHoverEvent |= super.dispatchHoverEvent(motionEvent);
                    this.mHoveredSelf = true;
                } else if (action == 7) {
                    motionEventObtainMotionEventNoHistoryOrSelf = obtainMotionEventNoHistoryOrSelf(motionEvent2);
                    motionEventObtainMotionEventNoHistoryOrSelf.setAction(9);
                    boolean zDispatchHoverEvent2 = zDispatchHoverEvent | super.dispatchHoverEvent(motionEventObtainMotionEventNoHistoryOrSelf);
                    motionEventObtainMotionEventNoHistoryOrSelf.setAction(action);
                    zDispatchHoverEvent = zDispatchHoverEvent2 | super.dispatchHoverEvent(motionEventObtainMotionEventNoHistoryOrSelf);
                    this.mHoveredSelf = true;
                }
            }
            if (!motionEventObtainMotionEventNoHistoryOrSelf.mRecycled && motionEventObtainMotionEventNoHistoryOrSelf != motionEvent) {
                motionEventObtainMotionEventNoHistoryOrSelf.recycle();
            }
            return zDispatchHoverEvent;
        }
        if (z3) {
            zDispatchHoverEvent |= super.dispatchHoverEvent(motionEvent);
        }
        motionEventObtainMotionEventNoHistoryOrSelf = motionEvent2;
        if (!motionEventObtainMotionEventNoHistoryOrSelf.mRecycled) {
            motionEventObtainMotionEventNoHistoryOrSelf.recycle();
        }
        return zDispatchHoverEvent;
    }

    private void exitHoverTargets() {
        if (this.mHoveredSelf || this.mFirstHoverTarget != null) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 10, 0.0f, 0.0f, 0);
            motionEventObtain.setSource(4098);
            dispatchHoverEvent(motionEventObtain);
            motionEventObtain.recycle();
        }
    }

    private void cancelHoverTarget(View view) {
        HoverTarget hoverTarget = this.mFirstHoverTarget;
        HoverTarget hoverTarget2 = null;
        while (hoverTarget != null) {
            HoverTarget hoverTarget3 = hoverTarget.next;
            if (hoverTarget.child == view) {
                if (hoverTarget2 == null) {
                    this.mFirstHoverTarget = hoverTarget3;
                } else {
                    hoverTarget2.next = hoverTarget3;
                }
                hoverTarget.recycle();
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 10, 0.0f, 0.0f, 0);
                motionEventObtain.setSource(4098);
                view.dispatchHoverEvent(motionEventObtain);
                motionEventObtain.recycle();
                return;
            }
            hoverTarget2 = hoverTarget;
            hoverTarget = hoverTarget3;
        }
    }

    @Override // android.view.View
    boolean dispatchTooltipHoverEvent(MotionEvent motionEvent) {
        return semDispatchTooltipHoverEvent(motionEvent);
    }

    public boolean semDispatchTooltipHoverEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        View view = null;
        if (action != 7) {
            if (action == 10) {
                View view2 = this.mTooltipHoverTarget;
                if (view2 != null) {
                    view2.dispatchTooltipHoverEvent(motionEvent);
                    this.mTooltipHoverTarget = null;
                } else if (this.mTooltipHoveredSelf) {
                    super.dispatchTooltipHoverEvent(motionEvent);
                    this.mTooltipHoveredSelf = false;
                }
            }
            return false;
        }
        int i = this.mChildrenCount;
        if (i != 0) {
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
            boolean z = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
            View[] viewArr = this.mChildren;
            int i2 = i - 1;
            while (true) {
                if (i2 < 0) {
                    break;
                }
                View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
                if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null) && dispatchTooltipHoverEvent(motionEvent, andVerifyPreorderedView)) {
                    view = andVerifyPreorderedView;
                    break;
                }
                i2--;
            }
            if (arrayListBuildOrderedChildList != null) {
                arrayListBuildOrderedChildList.clear();
            }
        }
        View view3 = this.mTooltipHoverTarget;
        if (view3 != view) {
            if (view3 != null) {
                motionEvent.setAction(10);
                this.mTooltipHoverTarget.dispatchTooltipHoverEvent(motionEvent);
                motionEvent.setAction(action);
            }
            this.mTooltipHoverTarget = view;
        }
        if (this.mTooltipHoverTarget != null) {
            if (this.mTooltipHoveredSelf) {
                this.mTooltipHoveredSelf = false;
                motionEvent.setAction(10);
                super.dispatchTooltipHoverEvent(motionEvent);
                motionEvent.setAction(action);
            }
            return true;
        }
        boolean zDispatchTooltipHoverEvent = super.dispatchTooltipHoverEvent(motionEvent);
        this.mTooltipHoveredSelf = zDispatchTooltipHoverEvent;
        return zDispatchTooltipHoverEvent;
    }

    private boolean dispatchTooltipHoverEvent(MotionEvent motionEvent, View view) {
        if (!view.hasIdentityMatrix()) {
            MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            boolean zDispatchTooltipHoverEvent = view.dispatchTooltipHoverEvent(transformedMotionEvent);
            transformedMotionEvent.recycle();
            return zDispatchTooltipHoverEvent;
        }
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent.offsetLocation(f, f2);
        boolean zDispatchTooltipHoverEvent2 = view.dispatchTooltipHoverEvent(motionEvent);
        motionEvent.offsetLocation(-f, -f2);
        return zDispatchTooltipHoverEvent2;
    }

    private void exitTooltipHoverTargets() {
        if (this.mTooltipHoveredSelf || this.mTooltipHoverTarget != null) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 10, 0.0f, 0.0f, 0);
            motionEventObtain.setSource(4098);
            dispatchTooltipHoverEvent(motionEventObtain);
            motionEventObtain.recycle();
        }
    }

    @Override // android.view.View
    protected boolean hasHoveredChild() {
        return this.mFirstHoverTarget != null;
    }

    @Override // android.view.View
    protected boolean pointInHoveredChild(MotionEvent motionEvent) {
        if (this.mFirstHoverTarget != null) {
            return isTransformedTouchPointInView(motionEvent.getXDispatchLocation(0), motionEvent.getYDispatchLocation(0), this.mFirstHoverTarget.child, null);
        }
        return false;
    }

    @Override // android.view.View
    public void addChildrenForAccessibility(ArrayList<View> arrayList) {
        if (getAccessibilityNodeProvider() != null) {
            return;
        }
        ChildListForAccessibility childListForAccessibilityObtain = ChildListForAccessibility.obtain(this, true);
        try {
            int childCount = childListForAccessibilityObtain.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = childListForAccessibilityObtain.getChildAt(i);
                if ((childAt.mViewFlags & 12) == 0) {
                    if (childAt.includeForAccessibility()) {
                        arrayList.add(childAt);
                    } else {
                        childAt.addChildrenForAccessibility(arrayList);
                    }
                }
            }
        } finally {
            childListForAccessibilityObtain.recycle();
        }
    }

    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194)) {
            int action = motionEvent.getAction();
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            if ((action == 7 || action == 9) && isOnScrollbar(xDispatchLocation, yDispatchLocation)) {
                return true;
            }
        }
        return false;
    }

    private static MotionEvent obtainMotionEventNoHistoryOrSelf(MotionEvent motionEvent) {
        return motionEvent.getHistorySize() == 0 ? motionEvent : MotionEvent.obtainNoHistory(motionEvent);
    }

    @Override // android.view.View
    protected boolean dispatchGenericPointerEvent(MotionEvent motionEvent) {
        int i = this.mChildrenCount;
        if (i != 0) {
            boolean z = false;
            float xDispatchLocation = motionEvent.getXDispatchLocation(0);
            float yDispatchLocation = motionEvent.getYDispatchLocation(0);
            ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
            if (arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled()) {
                z = true;
            }
            View[] viewArr = this.mChildren;
            for (int i2 = i - 1; i2 >= 0; i2--) {
                View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
                if (andVerifyPreorderedView.canReceivePointerEvents() && isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null) && dispatchTransformedGenericPointerEvent(motionEvent, andVerifyPreorderedView)) {
                    if (arrayListBuildOrderedChildList != null) {
                        arrayListBuildOrderedChildList.clear();
                    }
                    return true;
                }
            }
            if (arrayListBuildOrderedChildList != null) {
                arrayListBuildOrderedChildList.clear();
            }
        }
        return super.dispatchGenericPointerEvent(motionEvent);
    }

    @Override // android.view.View
    protected boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchGenericFocusedEvent(motionEvent);
        }
        View view = this.mFocused;
        if (view == null || (view.mPrivateFlags & 16) != 16) {
            return false;
        }
        return this.mFocused.dispatchGenericMotionEvent(motionEvent);
    }

    private boolean dispatchTransformedGenericPointerEvent(MotionEvent motionEvent, View view) {
        if (!view.hasIdentityMatrix()) {
            MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            boolean zDispatchGenericMotionEvent = view.dispatchGenericMotionEvent(transformedMotionEvent);
            transformedMotionEvent.recycle();
            return zDispatchGenericMotionEvent;
        }
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent.offsetLocation(f, f2);
        boolean zDispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-f, -f2);
        return zDispatchGenericMotionEvent2;
    }

    private MotionEvent getTransformedMotionEvent(MotionEvent motionEvent, View view) {
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.offsetLocation(f, f2);
        if (!view.hasIdentityMatrix()) {
            motionEventObtain.transform(view.getInverseMatrix());
        }
        return motionEventObtain;
    }

    /* JADX WARN: Removed duplicated region for block: B:193:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0138  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean zOnInterceptTouchEvent;
        boolean z;
        int i;
        boolean z2;
        TouchTarget touchTarget;
        TouchTarget touchTarget2;
        TouchTarget touchTargetAddTouchTarget;
        int i2;
        boolean z3;
        int iComputeHorizontalScrollOffset;
        int iComputeHorizontalScrollExtent;
        int iComputeHorizontalScrollRange;
        float f;
        int height;
        int width;
        if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
            Log.i(TAG, "dispatchTouchEvent " + this);
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(motionEvent, 1);
        }
        boolean zDispatchTransformedTouchEvent = false;
        if (motionEvent.isTargetAccessibilityFocus() && isAccessibilityFocusedViewOrHost()) {
            motionEvent.setTargetAccessibilityFocus(false);
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mSemX = motionEvent.getX();
            this.mSemY = motionEvent.getY();
            if (isVerticalScrollBarEnabled() && semIsShowingScrollbar() && this.mSemVerticalScrollbarRect.contains(((int) this.mSemX) + getScrollX(), ((int) this.mSemY) + getScrollY())) {
                this.mSemScrollingByScrollbar = true;
                this.mSemScrollingVertical = true;
                this.mSemVerticalScrollbarRectRelativePosY = ((int) this.mSemY) - this.mSemVerticalScrollbarRect.top;
                awakenScrollBars();
            }
            if (isHorizontalScrollBarEnabled() && semIsShowingScrollbar() && this.mSemHorizontalScrollbarRect.contains(((int) this.mSemX) + getScrollX(), ((int) this.mSemY) + getScrollY())) {
                this.mSemScrollingByScrollbar = true;
                this.mSemScrollingVertical = false;
                this.mSemHorizontalScrollbarRectRelativePosX = ((int) this.mSemX) - this.mSemHorizontalScrollbarRect.left;
                awakenScrollBars();
            }
            if (this.mSemScrollingByScrollbar) {
                return true;
            }
        } else if (action == 1) {
            if (this.mSemScrollingByScrollbar) {
                this.mSemScrollingByScrollbar = false;
                if (!awakenScrollBars()) {
                    postInvalidateOnAnimation();
                }
                return true;
            }
        } else if (action != 2) {
            if (action != 3) {
                if (this.mSemScrollingByScrollbar) {
                    return true;
                }
            }
        } else if (this.mSemScrollingByScrollbar) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.mSemScrollingVertical) {
                iComputeHorizontalScrollOffset = computeVerticalScrollOffset();
                iComputeHorizontalScrollExtent = computeVerticalScrollExtent();
                iComputeHorizontalScrollRange = computeVerticalScrollRange();
                f = y - this.mSemY;
            } else {
                iComputeHorizontalScrollOffset = computeHorizontalScrollOffset();
                iComputeHorizontalScrollExtent = computeHorizontalScrollExtent();
                iComputeHorizontalScrollRange = computeHorizontalScrollRange();
                f = x - this.mSemX;
            }
            if (isSemUsingAdapterView()) {
                int i3 = iComputeHorizontalScrollExtent == 0 ? 0 : (int) (f * (iComputeHorizontalScrollRange / iComputeHorizontalScrollExtent));
                int iSemGetItemCount = semGetItemCount();
                if (iSemGetItemCount > 1) {
                    if (this.mSemScrollingVertical) {
                        boolean z4 = (this.mAppWidgetScrollBarBottomPadding == 0 && this.mAppWidgetScrollBarTopPadding == 0) ? false : true;
                        float height2 = z4 ? ((this.mAppWidgetScrollBarBottomPadding * y) - ((getHeight() - y) * this.mAppWidgetScrollBarTopPadding)) / getHeight() : 0.0f;
                        float f2 = iSemGetItemCount;
                        if (!z4) {
                            height2 = -this.mSemVerticalScrollbarRectRelativePosY;
                        }
                        width = (int) (f2 * ((height2 + y) / getHeight()));
                    } else {
                        width = (int) (iSemGetItemCount * ((x - this.mSemHorizontalScrollbarRectRelativePosX) / getWidth()));
                    }
                    semSetSelection(Math.max(width, 0));
                } else {
                    semSmoothScrollBy(i3);
                }
            } else {
                if (iComputeHorizontalScrollExtent == 0) {
                    height = 0;
                } else {
                    height = (int) (f * (((this.mSemScrollingVertical ? getHeight() : getWidth()) + iComputeHorizontalScrollRange) / iComputeHorizontalScrollExtent));
                }
                if (height < 0) {
                    if (this.mSemScrollingVertical) {
                        if (height + iComputeHorizontalScrollOffset < 0) {
                            height = -iComputeHorizontalScrollOffset;
                        }
                        scrollBy(0, height);
                    } else {
                        if (height + iComputeHorizontalScrollOffset < 0) {
                            height = -iComputeHorizontalScrollOffset;
                        }
                        scrollBy(height, 0);
                    }
                } else if (height > 0) {
                    if (this.mSemScrollingVertical) {
                        if (height + iComputeHorizontalScrollOffset + iComputeHorizontalScrollExtent > iComputeHorizontalScrollRange) {
                            height = (iComputeHorizontalScrollRange - iComputeHorizontalScrollExtent) - iComputeHorizontalScrollOffset;
                        }
                        scrollBy(0, height);
                    } else {
                        if (height + iComputeHorizontalScrollOffset + iComputeHorizontalScrollExtent > iComputeHorizontalScrollRange) {
                            height = (iComputeHorizontalScrollRange - iComputeHorizontalScrollExtent) - iComputeHorizontalScrollOffset;
                        }
                        scrollBy(height, 0);
                    }
                }
            }
            this.mSemX = x;
            this.mSemY = y;
            return true;
        }
        if (onFilterTouchEventForSecurity(motionEvent)) {
            int action2 = motionEvent.getAction();
            int i4 = action2 & 255;
            if (i4 == 0 || i4 == 211) {
                cancelAndClearTouchTargets(motionEvent);
                resetTouchState();
            }
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (i4 == 0 || i4 == 211 || this.mFirstTouchTarget != null) {
                boolean z5 = (this.mGroupFlags & 524288) != 0;
                boolean z6 = viewRootImpl != null && viewRootImpl.getOnBackInvokedDispatcher().isBackGestureInProgress();
                if (!z5 || z6) {
                    zOnInterceptTouchEvent = onInterceptTouchEvent(motionEvent);
                    motionEvent.setAction(action2);
                } else {
                    zOnInterceptTouchEvent = false;
                }
            } else {
                zOnInterceptTouchEvent = true;
            }
            if (zOnInterceptTouchEvent || this.mFirstTouchTarget != null) {
                motionEvent.setTargetAccessibilityFocus(false);
            }
            boolean z7 = resetCancelNextUpFlag(this) || i4 == 3;
            boolean z8 = ((this.mGroupFlags & 2097152) == 0 || (motionEvent.getSource() == 8194)) ? false : true;
            if (!z7 && !zOnInterceptTouchEvent) {
                View viewFindChildWithAccessibilityFocus = motionEvent.isTargetAccessibilityFocus() ? findChildWithAccessibilityFocus() : null;
                if (i4 == 0 || i4 == 211 || ((z8 && i4 == 5) || i4 == 7)) {
                    int actionIndex = motionEvent.getActionIndex();
                    int pointerId = z8 ? 1 << motionEvent.getPointerId(actionIndex) : -1;
                    removePointersFromTouchTargets(pointerId);
                    int i5 = this.mChildrenCount;
                    if (i5 != 0) {
                        float xDispatchLocation = motionEvent.getXDispatchLocation(actionIndex);
                        float yDispatchLocation = motionEvent.getYDispatchLocation(actionIndex);
                        ArrayList<View> arrayListBuildTouchDispatchChildList = buildTouchDispatchChildList();
                        boolean z9 = arrayListBuildTouchDispatchChildList == null && isChildrenDrawingOrderEnabled();
                        i = -1;
                        View[] viewArr = this.mChildren;
                        int i6 = i5 - 1;
                        touchTargetAddTouchTarget = null;
                        while (true) {
                            if (i6 < 0) {
                                z = z8;
                                zDispatchTransformedTouchEvent = false;
                                z2 = false;
                                break;
                            }
                            int andVerifyPreorderedIndex = getAndVerifyPreorderedIndex(i5, i6, z9);
                            boolean z10 = z9;
                            View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildTouchDispatchChildList, viewArr, andVerifyPreorderedIndex);
                            if (viewFindChildWithAccessibilityFocus == null) {
                                if (andVerifyPreorderedView.canReceivePointerEvents()) {
                                    i2 = i6;
                                } else {
                                    i2 = i6;
                                    if (isTransformedTouchPointInView(xDispatchLocation, yDispatchLocation, andVerifyPreorderedView, null)) {
                                        TouchTarget touchTarget3 = getTouchTarget(andVerifyPreorderedView);
                                        if (touchTarget3 != null) {
                                            touchTarget3.pointerIdBits |= pointerId;
                                            touchTargetAddTouchTarget = touchTarget3;
                                            z = z8;
                                            zDispatchTransformedTouchEvent = false;
                                            z2 = false;
                                            break;
                                        }
                                        resetCancelNextUpFlag(andVerifyPreorderedView);
                                        touchTargetAddTouchTarget = touchTarget3;
                                        if (dispatchTransformedTouchEvent(motionEvent, false, andVerifyPreorderedView, pointerId)) {
                                            z = z8;
                                            this.mLastTouchDownTime = motionEvent.getDownTime();
                                            if (arrayListBuildTouchDispatchChildList != null) {
                                                int i7 = 0;
                                                while (true) {
                                                    if (i7 >= i5) {
                                                        break;
                                                    }
                                                    if (viewArr[andVerifyPreorderedIndex] == this.mChildren[i7]) {
                                                        this.mLastTouchDownIndex = i7;
                                                        break;
                                                    }
                                                    i7++;
                                                }
                                            } else {
                                                this.mLastTouchDownIndex = andVerifyPreorderedIndex;
                                            }
                                            this.mLastTouchDownX = xDispatchLocation;
                                            this.mLastTouchDownY = yDispatchLocation;
                                            touchTargetAddTouchTarget = addTouchTarget(andVerifyPreorderedView, pointerId);
                                            zDispatchTransformedTouchEvent = false;
                                            z2 = true;
                                        } else {
                                            z3 = z8;
                                            motionEvent.setTargetAccessibilityFocus(false);
                                        }
                                    }
                                    i6 = i2 - 1;
                                    z9 = z10;
                                    z8 = z3;
                                }
                                z3 = z8;
                                motionEvent.setTargetAccessibilityFocus(false);
                                i6 = i2 - 1;
                                z9 = z10;
                                z8 = z3;
                            } else if (viewFindChildWithAccessibilityFocus != andVerifyPreorderedView) {
                                i2 = i6;
                                z3 = z8;
                                i6 = i2 - 1;
                                z9 = z10;
                                z8 = z3;
                            } else {
                                i6 = i5;
                                viewFindChildWithAccessibilityFocus = null;
                                if (andVerifyPreorderedView.canReceivePointerEvents()) {
                                }
                                z3 = z8;
                                motionEvent.setTargetAccessibilityFocus(false);
                                i6 = i2 - 1;
                                z9 = z10;
                                z8 = z3;
                            }
                        }
                        if (arrayListBuildTouchDispatchChildList != null) {
                            arrayListBuildTouchDispatchChildList.clear();
                        }
                    } else {
                        z = z8;
                        i = -1;
                        z2 = false;
                        touchTargetAddTouchTarget = null;
                    }
                    if (touchTargetAddTouchTarget != null || (touchTarget = this.mFirstTouchTarget) == null) {
                        touchTarget = touchTargetAddTouchTarget;
                    } else {
                        while (touchTarget.next != null) {
                            touchTarget = touchTarget.next;
                        }
                        touchTarget.pointerIdBits |= pointerId;
                    }
                }
                touchTarget2 = this.mFirstTouchTarget;
                if (touchTarget2 != null) {
                }
                if (z7) {
                    resetTouchState();
                }
            } else {
                z = z8;
                i = -1;
                z2 = false;
                touchTarget = null;
                touchTarget2 = this.mFirstTouchTarget;
                if (touchTarget2 != null) {
                    zDispatchTransformedTouchEvent = dispatchTransformedTouchEvent(motionEvent, z7, null, i);
                } else {
                    TouchTarget touchTarget4 = null;
                    TouchTarget touchTarget5 = touchTarget2;
                    boolean z11 = zDispatchTransformedTouchEvent;
                    while (touchTarget5 != null) {
                        TouchTarget touchTarget6 = touchTarget5.next;
                        if (z2 && touchTarget5 == touchTarget) {
                            z11 = true;
                        } else {
                            boolean z12 = ((touchTarget5.child == null || !resetCancelNextUpFlag(touchTarget5.child)) && !zOnInterceptTouchEvent) ? zDispatchTransformedTouchEvent : true;
                            if (touchTarget5.child != null && dispatchTransformedTouchEvent(motionEvent, z12, touchTarget5.child, touchTarget5.pointerIdBits)) {
                                z11 = true;
                            }
                            if (z12) {
                                if (touchTarget4 == null) {
                                    this.mFirstTouchTarget = touchTarget6;
                                } else {
                                    touchTarget4.next = touchTarget6;
                                }
                                if (!touchTarget5.isRecycled()) {
                                    touchTarget5.recycle();
                                }
                            }
                            touchTarget5 = touchTarget6;
                        }
                        touchTarget4 = touchTarget5;
                        touchTarget5 = touchTarget6;
                    }
                    zDispatchTransformedTouchEvent = z11;
                }
                if (!z7 || i4 == 1 || i4 == 212 || i4 == 7) {
                    resetTouchState();
                } else if (z && i4 == 6) {
                    removePointersFromTouchTargets(1 << motionEvent.getPointerId(motionEvent.getActionIndex()));
                }
            }
        }
        if (!zDispatchTransformedTouchEvent && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 1);
        }
        return zDispatchTransformedTouchEvent;
    }

    public ArrayList<View> buildTouchDispatchChildList() {
        return buildOrderedChildList();
    }

    private View findChildWithAccessibilityFocus() {
        View accessibilityFocusedHost;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null || (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) == null) {
            return null;
        }
        ViewParent parent = accessibilityFocusedHost.getParent();
        while (parent instanceof View) {
            if (parent == this) {
                return accessibilityFocusedHost;
            }
            accessibilityFocusedHost = parent;
            parent = accessibilityFocusedHost.getParent();
        }
        return null;
    }

    private void resetTouchState() {
        clearTouchTargets();
        resetCancelNextUpFlag(this);
        this.mGroupFlags &= -524289;
        this.mNestedScrollAxes = 0;
    }

    private static boolean resetCancelNextUpFlag(View view) {
        if ((view.mPrivateFlags & 67108864) == 0) {
            return false;
        }
        view.mPrivateFlags &= -67108865;
        return true;
    }

    private void clearTouchTargets() {
        TouchTarget touchTarget = this.mFirstTouchTarget;
        if (touchTarget == null) {
            return;
        }
        while (true) {
            TouchTarget touchTarget2 = touchTarget.next;
            touchTarget.recycle();
            if (touchTarget2 == null) {
                this.mFirstTouchTarget = null;
                return;
            }
            touchTarget = touchTarget2;
        }
    }

    private void cancelAndClearTouchTargets(MotionEvent motionEvent) {
        boolean z;
        if (this.mFirstTouchTarget != null) {
            if (motionEvent == null) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                motionEvent = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                motionEvent.setSource(4098);
                z = true;
            } else {
                z = false;
            }
            for (TouchTarget touchTarget = this.mFirstTouchTarget; touchTarget != null; touchTarget = touchTarget.next) {
                if (touchTarget.child != null) {
                    resetCancelNextUpFlag(touchTarget.child);
                }
                dispatchTransformedTouchEvent(motionEvent, true, touchTarget.child, touchTarget.pointerIdBits);
            }
            clearTouchTargets();
            if (!z || motionEvent.mRecycled) {
                return;
            }
            motionEvent.recycle();
        }
    }

    private TouchTarget getTouchTarget(View view) {
        for (TouchTarget touchTarget = this.mFirstTouchTarget; touchTarget != null; touchTarget = touchTarget.next) {
            if (touchTarget.child == view) {
                return touchTarget;
            }
        }
        return null;
    }

    private TouchTarget addTouchTarget(View view, int i) {
        TouchTarget touchTargetObtain = TouchTarget.obtain(view, i);
        touchTargetObtain.next = this.mFirstTouchTarget;
        this.mFirstTouchTarget = touchTargetObtain;
        return touchTargetObtain;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void removePointersFromTouchTargets(int i) {
        TouchTarget touchTarget = this.mFirstTouchTarget;
        TouchTarget touchTarget2 = null;
        while (touchTarget != null) {
            TouchTarget touchTarget3 = touchTarget.next;
            if ((touchTarget.pointerIdBits & i) != 0) {
                touchTarget.pointerIdBits &= ~i;
                if (touchTarget.pointerIdBits == 0) {
                    if (touchTarget2 == null) {
                        this.mFirstTouchTarget = touchTarget3;
                    } else {
                        touchTarget2.next = touchTarget3;
                    }
                    touchTarget.recycle();
                } else {
                    touchTarget2 = touchTarget;
                }
            }
            touchTarget = touchTarget3;
        }
    }

    private void cancelTouchTarget(View view) {
        TouchTarget touchTarget = this.mFirstTouchTarget;
        TouchTarget touchTarget2 = null;
        while (touchTarget != null) {
            TouchTarget touchTarget3 = touchTarget.next;
            if (touchTarget.child == view) {
                if (touchTarget2 == null) {
                    this.mFirstTouchTarget = touchTarget3;
                } else {
                    touchTarget2.next = touchTarget3;
                }
                touchTarget.recycle();
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                motionEventObtain.setSource(4098);
                view.dispatchTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                return;
            }
            touchTarget2 = touchTarget;
            touchTarget = touchTarget3;
        }
    }

    private Rect getTempRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        return this.mTempRect;
    }

    private float[] getTempLocationF() {
        if (this.mTempPosition == null) {
            this.mTempPosition = new float[2];
        }
        return this.mTempPosition;
    }

    private Point getTempPoint() {
        if (this.mTempPoint == null) {
            this.mTempPoint = new Point();
        }
        return this.mTempPoint;
    }

    protected boolean isTransformedTouchPointInView(float f, float f2, View view, PointF pointF) {
        float[] tempLocationF = getTempLocationF();
        tempLocationF[0] = f;
        tempLocationF[1] = f2;
        transformPointToViewLocal(tempLocationF, view);
        boolean zPointInView = view.pointInView(tempLocationF[0], tempLocationF[1]);
        if (zPointInView && pointF != null) {
            pointF.set(tempLocationF[0], tempLocationF[1]);
        }
        return zPointInView;
    }

    public void transformPointToViewLocal(float[] fArr, View view) {
        fArr[0] = fArr[0] + (this.mScrollX - view.mLeft);
        fArr[1] = fArr[1] + (this.mScrollY - view.mTop);
        if (view.hasIdentityMatrix()) {
            return;
        }
        view.getInverseMatrix().mapPoints(fArr);
    }

    private boolean dispatchTransformedTouchEvent(MotionEvent motionEvent, boolean z, View view, int i) {
        MotionEvent motionEventSplit;
        boolean zDispatchTouchEvent;
        boolean zDispatchTouchEvent2;
        int action = motionEvent.getAction();
        if (z) {
            try {
                motionEvent.setAction(3);
            } finally {
                motionEvent.setAction(action);
            }
        }
        int pointerIdBits = motionEvent.getPointerIdBits();
        int i2 = i & pointerIdBits;
        if (i2 == 0) {
            if (motionEvent.getAction() != 3) {
                motionEvent.setAction(action);
                return false;
            }
            i2 = pointerIdBits;
        }
        if (i2 == pointerIdBits) {
            if (view != null && !view.hasIdentityMatrix()) {
                motionEventSplit = MotionEvent.obtain(motionEvent);
            }
            if (view == null) {
                zDispatchTouchEvent2 = super.dispatchTouchEvent(motionEvent);
            } else {
                float f = this.mScrollX - view.mLeft;
                float f2 = this.mScrollY - view.mTop;
                motionEvent.offsetLocation(f, f2);
                boolean zDispatchTouchEvent3 = view.dispatchTouchEvent(motionEvent);
                motionEvent.offsetLocation(-f, -f2);
                zDispatchTouchEvent2 = zDispatchTouchEvent3;
            }
            return zDispatchTouchEvent2;
        }
        motionEventSplit = motionEvent.split(i2);
        if (view == null) {
            zDispatchTouchEvent = super.dispatchTouchEvent(motionEventSplit);
        } else {
            motionEventSplit.offsetLocation(this.mScrollX - view.mLeft, this.mScrollY - view.mTop);
            if (!view.hasIdentityMatrix()) {
                motionEventSplit.transform(view.getInverseMatrix());
            }
            zDispatchTouchEvent = view.dispatchTouchEvent(motionEventSplit);
        }
        motionEventSplit.recycle();
        return zDispatchTouchEvent;
    }

    public void setMotionEventSplittingEnabled(boolean z) {
        if (z) {
            this.mGroupFlags |= 2097152;
        } else {
            this.mGroupFlags &= -2097153;
        }
    }

    public boolean isMotionEventSplittingEnabled() {
        return (this.mGroupFlags & 2097152) == 2097152;
    }

    public boolean isTransitionGroup() {
        int i = this.mGroupFlags;
        if ((33554432 & i) != 0) {
            return (16777216 & i) != 0;
        }
        ViewOutlineProvider outlineProvider = getOutlineProvider();
        return (getBackground() == null && getTransitionName() == null && (outlineProvider == null || outlineProvider == ViewOutlineProvider.BACKGROUND)) ? false : true;
    }

    public void setTransitionGroup(boolean z) {
        int i = this.mGroupFlags;
        int i2 = 33554432 | i;
        this.mGroupFlags = i2;
        if (z) {
            this.mGroupFlags = 50331648 | i;
        } else {
            this.mGroupFlags = (-16777217) & i2;
        }
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        int i = this.mGroupFlags;
        if (z == ((i & 524288) != 0)) {
            return;
        }
        if (z) {
            this.mGroupFlags = i | 524288;
        } else {
            this.mGroupFlags = i & (-524289);
        }
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(z);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return motionEvent.isFromSource(8194) && motionEvent.getAction() == 0 && motionEvent.isButtonPressed(1) && isOnScrollbarThumb(motionEvent.getXDispatchLocation(0), motionEvent.getYDispatchLocation(0));
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002f A[PHI: r0
      0x002f: PHI (r0v2 boolean) = (r0v1 boolean), (r0v3 boolean) binds: [B:16:0x003a, B:12:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestFocus(int i, Rect rect) throws Resources.NotFoundException {
        boolean zRequestFocus;
        boolean zOnRequestFocusInDescendants;
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability == 131072) {
            zRequestFocus = super.requestFocus(i, rect);
            if (!zRequestFocus) {
                zOnRequestFocusInDescendants = onRequestFocusInDescendants(i, rect);
            }
        } else if (descendantFocusability == 262144) {
            zRequestFocus = onRequestFocusInDescendants(i, rect);
            zOnRequestFocusInDescendants = zRequestFocus ? zRequestFocus : super.requestFocus(i, rect);
        } else if (descendantFocusability == 393216) {
            zOnRequestFocusInDescendants = super.requestFocus(i, rect);
        } else {
            throw new IllegalStateException("descendant focusability must be one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS but is " + descendantFocusability);
        }
        if (zOnRequestFocusInDescendants && !isLayoutValid() && (this.mPrivateFlags & 1) == 0) {
            this.mPrivateFlags |= 1;
        }
        return zOnRequestFocusInDescendants;
    }

    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        int i4;
        int i5 = this.mChildrenCount;
        if ((i & 2) != 0) {
            i3 = i5;
            i2 = 0;
            i4 = 1;
        } else {
            i2 = i5 - 1;
            i3 = -1;
            i4 = -1;
        }
        View[] viewArr = this.mChildren;
        while (i2 != i3) {
            View view = viewArr[i2];
            if ((view.mViewFlags & 12) == 0 && view.requestFocus(i, rect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    @Override // android.view.View
    public boolean restoreDefaultFocus() {
        if (this.mDefaultFocus == null || getDescendantFocusability() == 393216 || (this.mDefaultFocus.mViewFlags & 12) != 0 || !this.mDefaultFocus.restoreDefaultFocus()) {
            return super.restoreDefaultFocus();
        }
        return true;
    }

    @Override // android.view.View
    public boolean restoreFocusInCluster(int i) {
        if (isKeyboardNavigationCluster()) {
            boolean touchscreenBlocksFocus = getTouchscreenBlocksFocus();
            try {
                setTouchscreenBlocksFocusNoRefocus(false);
                return restoreFocusInClusterInternal(i);
            } finally {
                setTouchscreenBlocksFocusNoRefocus(touchscreenBlocksFocus);
            }
        }
        return restoreFocusInClusterInternal(i);
    }

    private boolean restoreFocusInClusterInternal(int i) {
        if (this.mFocusedInCluster == null || getDescendantFocusability() == 393216 || (this.mFocusedInCluster.mViewFlags & 12) != 0 || !this.mFocusedInCluster.restoreFocusInCluster(i)) {
            return super.restoreFocusInCluster(i);
        }
        return true;
    }

    @Override // android.view.View
    public boolean restoreFocusNotInCluster() {
        if (this.mFocusedInCluster != null) {
            return restoreFocusInCluster(130);
        }
        if (!isKeyboardNavigationCluster() && (this.mViewFlags & 12) == 0) {
            int descendantFocusability = getDescendantFocusability();
            if (descendantFocusability == 393216) {
                return super.requestFocus(130, null);
            }
            if (descendantFocusability == 131072 && super.requestFocus(130, null)) {
                return true;
            }
            for (int i = 0; i < this.mChildrenCount; i++) {
                View view = this.mChildren[i];
                if (!view.isKeyboardNavigationCluster() && view.restoreFocusNotInCluster()) {
                    return true;
                }
            }
            if (descendantFocusability == 262144 && !hasFocusableChild(false)) {
                return super.requestFocus(130, null);
            }
        }
        return false;
    }

    @Override // android.view.View
    public void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchStartTemporaryDetach();
        }
    }

    @Override // android.view.View
    public void dispatchFinishTemporaryDetach() {
        super.dispatchFinishTemporaryDetach();
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchFinishTemporaryDetach();
        }
    }

    @Override // android.view.View
    void dispatchAttachedToWindow(View.AttachInfo attachInfo, int i) {
        this.mGroupFlags |= 4194304;
        super.dispatchAttachedToWindow(attachInfo, i);
        this.mGroupFlags &= -4194305;
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            View view = viewArr[i3];
            view.dispatchAttachedToWindow(attachInfo, combineVisibility(i, view.getVisibility()));
        }
        IntArray intArray = this.mTransientIndices;
        int size = intArray == null ? 0 : intArray.size();
        for (int i4 = 0; i4 < size; i4++) {
            View view2 = this.mTransientViews.get(i4);
            view2.dispatchAttachedToWindow(attachInfo, combineVisibility(i, view2.getVisibility()));
        }
    }

    @Override // android.view.View
    void dispatchScreenStateChanged(int i) {
        super.dispatchScreenStateChanged(i);
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            viewArr[i3].dispatchScreenStateChanged(i);
        }
    }

    @Override // android.view.View
    void dispatchMovedToDisplay(Display display, Configuration configuration) {
        super.dispatchMovedToDisplay(display, configuration);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchMovedToDisplay(display, configuration);
        }
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        boolean zDispatchPopulateAccessibilityEvent;
        boolean zDispatchPopulateAccessibilityEventInternal;
        if (includeForAccessibility(false) && (zDispatchPopulateAccessibilityEventInternal = super.dispatchPopulateAccessibilityEventInternal(accessibilityEvent))) {
            return zDispatchPopulateAccessibilityEventInternal;
        }
        ChildListForAccessibility childListForAccessibilityObtain = ChildListForAccessibility.obtain(this, true);
        try {
            int childCount = childListForAccessibilityObtain.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = childListForAccessibilityObtain.getChildAt(i);
                if ((childAt.mViewFlags & 12) == 0 && (zDispatchPopulateAccessibilityEvent = childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent))) {
                    return zDispatchPopulateAccessibilityEvent;
                }
            }
            return false;
        } finally {
            childListForAccessibilityObtain.recycle();
        }
    }

    @Override // android.view.View
    public void dispatchProvideStructure(ViewStructure viewStructure) {
        int i;
        super.dispatchProvideStructure(viewStructure);
        if (isAssistBlocked() || viewStructure.getChildCount() != 0 || (i = this.mChildrenCount) <= 0) {
            return;
        }
        if (!isLaidOut()) {
            if (Helper.sVerbose) {
                Log.v("View", "dispatchProvideStructure(): not laid out, ignoring " + i + " children of " + getAccessibilityViewId());
                return;
            }
            return;
        }
        viewStructure.setChildCount(i);
        ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
        ArrayList arrayList = arrayListBuildOrderedChildList != null ? new ArrayList(arrayListBuildOrderedChildList) : null;
        boolean z = arrayList == null && isChildrenDrawingOrderEnabled();
        for (int i2 = 0; i2 < i; i2++) {
            getAndVerifyPreorderedView(arrayList, this.mChildren, getAndVerifyPreorderedIndex(i, i2, z)).dispatchProvideStructure(viewStructure.newChild(i2));
        }
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    @Override // android.view.View
    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        super.dispatchProvideAutofillStructure(viewStructure, i);
        if (viewStructure.getChildCount() != 0) {
            return;
        }
        if (!isLaidOut()) {
            if (Helper.sVerbose) {
                Log.v("View", "dispatchProvideAutofillStructure(): not laid out, ignoring " + this.mChildrenCount + " children of " + getAutofillId());
                return;
            }
            return;
        }
        ChildListForAutoFillOrContentCapture childrenForAutofill = getChildrenForAutofill(i);
        int size = childrenForAutofill.size();
        viewStructure.setChildCount(size);
        for (int i2 = 0; i2 < size; i2++) {
            childrenForAutofill.get(i2).dispatchProvideAutofillStructure(viewStructure.newChild(i2), i);
        }
        childrenForAutofill.recycle();
    }

    @Override // android.view.View
    public void dispatchProvideContentCaptureStructure() {
        super.dispatchProvideContentCaptureStructure();
        if (isLaidOut()) {
            ChildListForAutoFillOrContentCapture childrenForContentCapture = getChildrenForContentCapture();
            int size = childrenForContentCapture.size();
            for (int i = 0; i < size; i++) {
                childrenForContentCapture.get(i).dispatchProvideContentCaptureStructure();
            }
            childrenForContentCapture.recycle();
        }
    }

    private ChildListForAutoFillOrContentCapture getChildrenForAutofill(int i) {
        ChildListForAutoFillOrContentCapture childListForAutoFillOrContentCaptureObtain = ChildListForAutoFillOrContentCapture.obtain();
        populateChildrenForAutofill(childListForAutoFillOrContentCaptureObtain, i);
        return childListForAutoFillOrContentCaptureObtain;
    }

    private AutofillManager getAutofillManager() {
        return (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
    }

    private boolean shouldIncludeAllChildrenViewWithAutofillTypeNotNone(AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldIncludeAllChildrenViewsWithAutofillTypeNotNoneInAssistStructure();
    }

    private boolean shouldIncludeAllChildrenViews(AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldIncludeAllChildrenViewInAssistStructure();
    }

    private boolean shouldAlwaysIncludeWebview(AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldAlwaysIncludeWebviewInAssistStructure();
    }

    private boolean shouldIncludeInvisibleView(AutofillManager autofillManager) {
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.shouldIncludeInvisibleViewInAssistStructure();
    }

    private void populateChildrenForAutofill(ArrayList<View> arrayList, int i) {
        int i2 = this.mChildrenCount;
        if (i2 <= 0) {
            return;
        }
        ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
        boolean z = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        AutofillManager autofillManager = getAutofillManager();
        for (int i3 = 0; i3 < i2; i3++) {
            int andVerifyPreorderedIndex = getAndVerifyPreorderedIndex(i2, i3, z);
            View view = arrayListBuildOrderedChildList == null ? this.mChildren[andVerifyPreorderedIndex] : arrayListBuildOrderedChildList.get(andVerifyPreorderedIndex);
            if ((i & 1) != 0 || view.isImportantForAutofill() || (((view instanceof WebView) && shouldAlwaysIncludeWebview(autofillManager)) || ((view.isMatchingAutofillableHeuristics() && !view.isActivityDeniedForAutofillForUnimportantView()) || ((shouldIncludeAllChildrenViewWithAutofillTypeNotNone(autofillManager) && view.getAutofillType() != 0) || shouldIncludeAllChildrenViews(autofillManager) || (shouldIncludeInvisibleView(autofillManager) && (view instanceof ViewGroup) && view.getVisibility() != 0))))) {
                arrayList.add(view);
            } else if (view instanceof ViewGroup) {
                ((ViewGroup) view).populateChildrenForAutofill(arrayList, i);
            }
        }
    }

    private ChildListForAutoFillOrContentCapture getChildrenForContentCapture() {
        ChildListForAutoFillOrContentCapture childListForAutoFillOrContentCaptureObtain = ChildListForAutoFillOrContentCapture.obtain();
        populateChildrenForContentCapture(childListForAutoFillOrContentCaptureObtain);
        return childListForAutoFillOrContentCaptureObtain;
    }

    private void populateChildrenForContentCapture(ArrayList<View> arrayList) {
        int i = this.mChildrenCount;
        if (i <= 0) {
            return;
        }
        ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
        boolean z = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        for (int i2 = 0; i2 < i; i2++) {
            int andVerifyPreorderedIndex = getAndVerifyPreorderedIndex(i, i2, z);
            View view = arrayListBuildOrderedChildList == null ? this.mChildren[andVerifyPreorderedIndex] : arrayListBuildOrderedChildList.get(andVerifyPreorderedIndex);
            if (view.isImportantForContentCapture()) {
                arrayList.add(view);
            } else if (view instanceof ViewGroup) {
                ((ViewGroup) view).populateChildrenForContentCapture(arrayList);
            }
        }
    }

    private static View getAndVerifyPreorderedView(ArrayList<View> arrayList, View[] viewArr, int i) {
        if (arrayList != null) {
            View view = arrayList.get(i);
            if (view != null) {
                return view;
            }
            throw new RuntimeException("Invalid preorderedList contained null child at index " + i);
        }
        return viewArr[i];
    }

    @Override // android.view.View
    public void resetSubtreeAutofillIds() {
        super.resetSubtreeAutofillIds();
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].resetSubtreeAutofillIds();
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (getAccessibilityNodeProvider() != null) {
            return;
        }
        if (this.mAttachInfo != null) {
            ArrayList<View> arrayList = this.mAttachInfo.mTempArrayList;
            arrayList.clear();
            addChildrenForAccessibility(arrayList);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                try {
                    accessibilityNodeInfo.addChildUnchecked(arrayList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            arrayList.clear();
        }
        accessibilityNodeInfo.setAvailableExtraData(Collections.singletonList(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY));
    }

    @Override // android.view.View
    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
        if (str.equals(AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY)) {
            AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfoObtain = AccessibilityNodeInfo.ExtraRenderingInfo.obtain();
            extraRenderingInfoObtain.setLayoutSize(getLayoutParams().width, getLayoutParams().height);
            accessibilityNodeInfo.setExtraRenderingInfo(extraRenderingInfoObtain);
        }
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return ViewGroup.class.getName();
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(View view, View view2, int i) {
        if (getAccessibilityLiveRegion() != 0) {
            notifyViewAccessibilityStateChangedIfNeeded(1);
            return;
        }
        if (this.mParent != null) {
            try {
                this.mParent.notifySubtreeAccessibilityStateChanged(this, view2, i);
            } catch (AbstractMethodError e) {
                Log.e("View", this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            }
        }
    }

    @Override // android.view.View
    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || this.mAttachInfo == null) {
            return;
        }
        if (getImportantForAccessibility() != 4 && !isImportantForAccessibility() && getChildCount() > 0) {
            Object parentForAccessibility = getParentForAccessibility();
            if (parentForAccessibility instanceof View) {
                ((View) parentForAccessibility).notifySubtreeAccessibilityStateChangedIfNeeded();
                return;
            }
        }
        super.notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    @Override // android.view.View
    void resetSubtreeAccessibilityStateChanged() {
        super.resetSubtreeAccessibilityStateChanged();
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if (view != null) {
                view.resetSubtreeAccessibilityStateChanged();
            }
        }
    }

    int getNumChildrenForAccessibility() {
        int numChildrenForAccessibility = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.includeForAccessibility()) {
                numChildrenForAccessibility++;
            } else if (childAt instanceof ViewGroup) {
                numChildrenForAccessibility += ((ViewGroup) childAt).getNumChildrenForAccessibility();
            }
        }
        return numChildrenForAccessibility;
    }

    @Override // android.view.View
    void calculateAccessibilityDataSensitive() {
        super.calculateAccessibilityDataSensitive();
        for (int i = 0; i < this.mChildrenCount; i++) {
            this.mChildren[i].calculateAccessibilityDataSensitive();
        }
    }

    @Override // android.view.View
    void dispatchDetachedFromWindow() {
        cancelAndClearTouchTargets(null);
        exitHoverTargets();
        exitTooltipHoverTargets();
        this.mLayoutCalledWhileSuppressed = false;
        this.mChildrenInterestedInDrag = null;
        this.mIsInterestedInDrag = false;
        DragEvent dragEvent = this.mCurrentDragStartEvent;
        if (dragEvent != null) {
            dragEvent.recycle();
            this.mCurrentDragStartEvent = null;
        }
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchDetachedFromWindow();
        }
        clearDisappearingChildren();
        int size = this.mTransientViews == null ? 0 : this.mTransientIndices.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mTransientViews.get(i3).dispatchDetachedFromWindow();
        }
        super.dispatchDetachedFromWindow();
    }

    @Override // android.view.View
    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        super.internalSetPadding(i, i2, i3, i4);
        if ((this.mPaddingLeft | this.mPaddingTop | this.mPaddingRight | this.mPaddingBottom) != 0) {
            this.mGroupFlags |= 32;
        } else {
            this.mGroupFlags &= -33;
        }
    }

    @Override // android.view.View
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchSaveInstanceState(sparseArray);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if ((view.mViewFlags & 536870912) != 536870912) {
                view.dispatchSaveInstanceState(sparseArray);
            }
        }
    }

    protected void dispatchFreezeSelfOnly(SparseArray<Parcelable> sparseArray) {
        super.dispatchSaveInstanceState(sparseArray);
    }

    @Override // android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if ((view.mViewFlags & 536870912) != 536870912) {
                view.dispatchRestoreInstanceState(sparseArray);
            }
        }
    }

    protected void dispatchThawSelfOnly(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public void setChildrenDrawingCacheEnabled(boolean z) throws Resources.NotFoundException {
        if (z || (this.mPersistentDrawingCache & 3) != 3) {
            View[] viewArr = this.mChildren;
            int i = this.mChildrenCount;
            for (int i2 = 0; i2 < i; i2++) {
                viewArr[i2].setDrawingCacheEnabled(z);
            }
        }
    }

    @Override // android.view.View
    public Bitmap createSnapshot(ViewDebug.CanvasProvider canvasProvider, boolean z) {
        int[] iArr;
        int i = this.mChildrenCount;
        int i2 = 0;
        if (z) {
            iArr = new int[i];
            for (int i3 = 0; i3 < i; i3++) {
                View childAt = getChildAt(i3);
                int visibility = childAt.getVisibility();
                iArr[i3] = visibility;
                if (visibility == 0) {
                    childAt.mViewFlags = (childAt.mViewFlags & (-13)) | 4;
                }
            }
        } else {
            iArr = null;
        }
        try {
            return super.createSnapshot(canvasProvider, z);
        } finally {
            if (z) {
                while (i2 < i) {
                    View childAt2 = getChildAt(i2);
                    childAt2.mViewFlags = (childAt2.mViewFlags & (-13)) | (iArr[i2] & 12);
                    i2++;
                }
            }
        }
    }

    boolean isLayoutModeOptical() {
        return this.mLayoutMode == 1;
    }

    @Override // android.view.View
    Insets computeOpticalInsets() {
        if (isLayoutModeOptical()) {
            int iMax = 0;
            int iMax2 = 0;
            int iMax3 = 0;
            int iMax4 = 0;
            for (int i = 0; i < this.mChildrenCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    Insets opticalInsets = childAt.getOpticalInsets();
                    iMax = Math.max(iMax, opticalInsets.left);
                    iMax2 = Math.max(iMax2, opticalInsets.top);
                    iMax3 = Math.max(iMax3, opticalInsets.right);
                    iMax4 = Math.max(iMax4, opticalInsets.bottom);
                }
            }
            return Insets.of(iMax, iMax2, iMax3, iMax4);
        }
        return Insets.NONE;
    }

    private static void fillRect(Canvas canvas, Paint paint, int i, int i2, int i3, int i4) {
        if (i == i3 || i2 == i4) {
            return;
        }
        if (i > i3) {
            i3 = i;
            i = i3;
        }
        if (i2 > i4) {
            i4 = i2;
            i2 = i4;
        }
        canvas.drawRect(i, i2, i3, i4, paint);
    }

    private static void drawCorner(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5) {
        fillRect(canvas, paint, i, i2, i + i3, i2 + (sign(i4) * i5));
        fillRect(canvas, paint, i, i2, i + (i5 * sign(i3)), i4 + i2);
    }

    private static void drawRectCorners(Canvas canvas, int i, int i2, int i3, int i4, Paint paint, int i5, int i6) {
        drawCorner(canvas, paint, i, i2, i5, i5, i6);
        int i7 = -i5;
        drawCorner(canvas, paint, i, i4, i5, i7, i6);
        drawCorner(canvas, paint, i3, i2, i7, i5, i6);
        drawCorner(canvas, paint, i3, i4, i7, i7, i6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void fillDifference(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Paint paint) {
        int i9 = i - i5;
        int i10 = i3 + i7;
        fillRect(canvas, paint, i9, i2 - i6, i10, i2);
        fillRect(canvas, paint, i9, i2, i, i4);
        fillRect(canvas, paint, i3, i2, i10, i4);
        fillRect(canvas, paint, i9, i4, i10, i4 + i8);
    }

    protected void onDebugDrawMargins(Canvas canvas, Paint paint) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.getLayoutParams().onDebugDraw(childAt, canvas, paint);
        }
    }

    protected void onDebugDraw(Canvas canvas) {
        Canvas canvas2;
        Paint debugPaint = getDebugPaint();
        debugPaint.setColor(-65536);
        debugPaint.setStyle(Paint.Style.STROKE);
        int i = 0;
        while (i < getChildCount()) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                Insets opticalInsets = childAt.getOpticalInsets();
                int left = childAt.getLeft() + opticalInsets.left;
                int top = childAt.getTop() + opticalInsets.top;
                int right = (childAt.getRight() - opticalInsets.right) - 1;
                int bottom = (childAt.getBottom() - opticalInsets.bottom) - 1;
                canvas2 = canvas;
                drawRect(canvas2, debugPaint, left, top, right, bottom);
            } else {
                canvas2 = canvas;
            }
            i++;
            canvas = canvas2;
        }
        Canvas canvas3 = canvas;
        debugPaint.setColor(Color.argb(63, 255, 0, 255));
        debugPaint.setStyle(Paint.Style.FILL);
        onDebugDrawMargins(canvas3, debugPaint);
        debugPaint.setColor(DEBUG_CORNERS_COLOR);
        debugPaint.setStyle(Paint.Style.FILL);
        int iDipsToPixels = dipsToPixels(8);
        int iDipsToPixels2 = dipsToPixels(1);
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt2 = getChildAt(i2);
            if (childAt2.getVisibility() != 8) {
                Paint paint = debugPaint;
                drawRectCorners(canvas3, childAt2.getLeft(), childAt2.getTop(), childAt2.getRight(), childAt2.getBottom(), paint, iDipsToPixels, iDipsToPixels2);
                debugPaint = paint;
            }
        }
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int iSave;
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        int i2 = this.mGroupFlags;
        if ((i2 & 8) != 0 && canAnimate()) {
            for (int i3 = 0; i3 < i; i3++) {
                View view = viewArr[i3];
                if ((view.mViewFlags & 12) == 0) {
                    attachLayoutAnimationParameters(view, view.getLayoutParams(), i3, i);
                    bindLayoutAnimation(view);
                }
            }
            LayoutAnimationController layoutAnimationController = this.mLayoutAnimationController;
            if (layoutAnimationController.willOverlap()) {
                this.mGroupFlags |= 128;
            }
            layoutAnimationController.start();
            this.mGroupFlags &= -25;
            Animation.AnimationListener animationListener = this.mAnimationListener;
            if (animationListener != null) {
                animationListener.onAnimationStart(layoutAnimationController.getAnimation());
            }
        }
        boolean z = (i2 & 34) == 34;
        if (z) {
            iSave = canvas.save(2);
            canvas.clipRect(this.mScrollX + this.mPaddingLeft, this.mScrollY + this.mPaddingTop, ((this.mScrollX + this.mRight) - this.mLeft) - this.mPaddingRight, ((this.mScrollY + this.mBottom) - this.mTop) - this.mPaddingBottom);
        } else {
            iSave = 0;
        }
        this.mPrivateFlags &= -65;
        this.mGroupFlags &= -5;
        long drawingTime = getDrawingTime();
        canvas.enableZ();
        IntArray intArray = this.mTransientIndices;
        int size = intArray == null ? 0 : intArray.size();
        int i4 = size != 0 ? 0 : -1;
        ArrayList<View> arrayListBuildOrderedChildList = drawsWithRenderNode(canvas) ? null : buildOrderedChildList();
        boolean z2 = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        int i5 = i4;
        boolean zDrawChild = false;
        for (int i6 = 0; i6 < i; i6++) {
            while (i5 >= 0 && this.mTransientIndices.get(i5) == i6) {
                View view2 = this.mTransientViews.get(i5);
                if (view2 != null && ((view2.mViewFlags & 12) == 0 || view2.getAnimation() != null)) {
                    zDrawChild = drawChild(canvas, view2, drawingTime) | zDrawChild;
                }
                i5++;
                if (i5 >= size) {
                    i5 = -1;
                }
            }
            View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i6, z2));
            if (andVerifyPreorderedView != null && ((andVerifyPreorderedView.mViewFlags & 12) == 0 || andVerifyPreorderedView.getAnimation() != null)) {
                zDrawChild = drawChild(canvas, andVerifyPreorderedView, drawingTime) | zDrawChild;
            }
        }
        while (i5 >= 0) {
            View view3 = this.mTransientViews.get(i5);
            if (view3 != null && ((view3.mViewFlags & 12) == 0 || view3.getAnimation() != null)) {
                zDrawChild = drawChild(canvas, view3, drawingTime) | zDrawChild;
            }
            i5++;
            if (i5 >= size) {
                break;
            }
        }
        if (arrayListBuildOrderedChildList != null) {
            arrayListBuildOrderedChildList.clear();
        }
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList != null) {
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                zDrawChild |= drawChild(canvas, arrayList.get(size2), drawingTime);
            }
        }
        canvas.disableZ();
        if (isShowingLayoutBounds()) {
            onDebugDraw(canvas);
        }
        if (z) {
            canvas.restoreToCount(iSave);
        }
        int i7 = this.mGroupFlags;
        if ((i7 & 4) == 4) {
            invalidate(true);
        }
        if ((i7 & 16) == 0 && (i7 & 512) == 0 && this.mLayoutAnimationController.isDone() && !zDrawChild) {
            this.mGroupFlags |= 512;
            post(new Runnable() { // from class: android.view.ViewGroup.2
                @Override // java.lang.Runnable
                public void run() {
                    ViewGroup.this.notifyAnimationListener();
                }
            });
        }
    }

    @Override // android.view.View
    public ViewGroupOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new ViewGroupOverlay(this.mContext, this);
        }
        return (ViewGroupOverlay) this.mOverlay;
    }

    public final int getChildDrawingOrder(int i) {
        return getChildDrawingOrder(getChildCount(), i);
    }

    private boolean hasChildWithZ() {
        for (int i = 0; i < this.mChildrenCount; i++) {
            if (this.mChildren[i].getZ() != 0.0f) {
                return true;
            }
        }
        return false;
    }

    ArrayList<View> buildOrderedChildList() {
        int i = this.mChildrenCount;
        if (i <= 1 || !hasChildWithZ()) {
            return null;
        }
        ArrayList<View> arrayList = this.mPreSortedChildren;
        if (arrayList == null) {
            this.mPreSortedChildren = new ArrayList<>(i);
        } else {
            arrayList.clear();
            this.mPreSortedChildren.ensureCapacity(i);
        }
        boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        for (int i2 = 0; i2 < i; i2++) {
            View view = this.mChildren[getAndVerifyPreorderedIndex(i, i2, zIsChildrenDrawingOrderEnabled)];
            float z = view.getZ();
            int i3 = i2;
            while (i3 > 0 && this.mPreSortedChildren.get(i3 - 1).getZ() > z) {
                i3--;
            }
            this.mPreSortedChildren.add(i3, view);
        }
        return this.mPreSortedChildren;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAnimationListener() {
        this.mGroupFlags = (this.mGroupFlags & (-513)) | 16;
        if (this.mAnimationListener != null) {
            post(new Runnable() { // from class: android.view.ViewGroup.3
                @Override // java.lang.Runnable
                public void run() {
                    ViewGroup.this.mAnimationListener.onAnimationEnd(ViewGroup.this.mLayoutAnimationController.getAnimation());
                }
            });
        }
        invalidate(true);
    }

    @Override // android.view.View
    protected void dispatchGetDisplayList() {
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if (view == null) {
                throw new IllegalStateException(getClass().getSimpleName() + " contains null child at index " + i2 + " when traversal in dispatchGetDisplayList, the view may have been removed.");
            }
            if ((view.mViewFlags & 12) == 0 || view.getAnimation() != null) {
                recreateChildDisplayList(view);
            }
        }
        int size = this.mTransientViews == null ? 0 : this.mTransientIndices.size();
        for (int i3 = 0; i3 < size; i3++) {
            View view2 = this.mTransientViews.get(i3);
            if (view2 != null && ((view2.mViewFlags & 12) == 0 || view2.getAnimation() != null)) {
                recreateChildDisplayList(view2);
            }
        }
        if (this.mOverlay != null) {
            recreateChildDisplayList(this.mOverlay.getOverlayView());
        }
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList != null) {
            int size2 = arrayList.size();
            for (int i4 = 0; i4 < size2; i4++) {
                recreateChildDisplayList(arrayList.get(i4));
            }
        }
    }

    private void recreateChildDisplayList(View view) {
        view.mRecreateDisplayList = (view.mPrivateFlags & Integer.MIN_VALUE) != 0;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        return view.draw(canvas, this, j);
    }

    @Override // android.view.View
    void getScrollIndicatorBounds(Rect rect) {
        super.getScrollIndicatorBounds(rect);
        if ((this.mGroupFlags & 34) == 34) {
            rect.left += this.mPaddingLeft;
            rect.right -= this.mPaddingRight;
            rect.top += this.mPaddingTop;
            rect.bottom -= this.mPaddingBottom;
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean getClipChildren() {
        return (this.mGroupFlags & 1) != 0;
    }

    public void setClipChildren(boolean z) {
        if (z != ((this.mGroupFlags & 1) == 1)) {
            setBooleanFlag(1, z);
            for (int i = 0; i < this.mChildrenCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.mRenderNode != null) {
                    childAt.mRenderNode.setClipToBounds(z);
                }
            }
            invalidate(true);
        }
    }

    public void setClipToPadding(boolean z) {
        if (hasBooleanFlag(2) != z) {
            setBooleanFlag(2, z);
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean getClipToPadding() {
        return hasBooleanFlag(2);
    }

    @Override // android.view.View
    public void dispatchSetSelected(boolean z) {
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].setSelected(z);
        }
    }

    @Override // android.view.View
    public void dispatchSetActivated(boolean z) {
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].setActivated(z);
        }
    }

    @Override // android.view.View
    protected void dispatchSetPressed(boolean z) {
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if (!z || (!view.isClickable() && !view.isLongClickable())) {
                view.setPressed(z);
            }
        }
    }

    @Override // android.view.View
    public void dispatchDrawableHotspotChanged(float f, float f2) {
        int i = this.mChildrenCount;
        if (i == 0) {
            return;
        }
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            boolean z = (view.isClickable() || view.isLongClickable()) ? false : true;
            boolean z2 = (view.mViewFlags & 4194304) != 0;
            if (z || z2) {
                float[] tempLocationF = getTempLocationF();
                tempLocationF[0] = f;
                tempLocationF[1] = f2;
                transformPointToViewLocal(tempLocationF, view);
                view.drawableHotspotChanged(tempLocationF[0], tempLocationF[1]);
            }
        }
    }

    @Override // android.view.View
    void dispatchCancelPendingInputEvents() {
        super.dispatchCancelPendingInputEvents();
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].dispatchCancelPendingInputEvents();
        }
    }

    protected void setStaticTransformationsEnabled(boolean z) {
        setBooleanFlag(2048, z);
    }

    Transformation getChildTransformation() {
        if (this.mChildTransformation == null) {
            this.mChildTransformation = new Transformation();
        }
        return this.mChildTransformation;
    }

    @Override // android.view.View
    protected <T extends View> T findViewTraversal(int i) {
        T t;
        if (i == this.mID) {
            return this;
        }
        View[] viewArr = this.mChildren;
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            View view = viewArr[i3];
            if (view != null && (view.mPrivateFlags & 8) == 0 && (t = (T) view.findViewById(i)) != null) {
                return t;
            }
        }
        return null;
    }

    @Override // android.view.View
    protected <T extends View> T findViewWithTagTraversal(Object obj) {
        T t;
        if (obj != null && obj.equals(this.mTag)) {
            return this;
        }
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            View view = viewArr[i2];
            if ((view.mPrivateFlags & 8) == 0 && (t = (T) view.findViewWithTag(obj)) != null) {
                return t;
            }
        }
        return null;
    }

    @Override // android.view.View
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        T t;
        if (predicate.test(this)) {
            return this;
        }
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            View view2 = viewArr[i2];
            if (view2 != view && (view2.mPrivateFlags & 8) == 0 && (t = (T) view2.findViewByPredicate(predicate)) != null) {
                return t;
            }
        }
        return null;
    }

    public void addTransientView(View view, int i) {
        if (i < 0 || view == null) {
            return;
        }
        if (view.mParent != null) {
            throw new IllegalStateException("The specified view already has a parent " + view.mParent);
        }
        if (this.mTransientIndices == null) {
            this.mTransientIndices = new IntArray();
            this.mTransientViews = new ArrayList();
        }
        int size = this.mTransientIndices.size();
        if (size > 0) {
            int i2 = 0;
            while (i2 < size && i >= this.mTransientIndices.get(i2)) {
                i2++;
            }
            this.mTransientIndices.add(i2, i);
            this.mTransientViews.add(i2, view);
        } else {
            this.mTransientIndices.add(i);
            this.mTransientViews.add(view);
        }
        view.mParent = this;
        if (this.mAttachInfo != null) {
            view.dispatchAttachedToWindow(this.mAttachInfo, this.mViewFlags & 12);
        }
        invalidate(true);
    }

    public void removeTransientView(View view) {
        List<View> list = this.mTransientViews;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (view == this.mTransientViews.get(i)) {
                this.mTransientViews.remove(i);
                this.mTransientIndices.remove(i);
                view.mParent = null;
                if (view.mAttachInfo != null) {
                    view.dispatchDetachedFromWindow();
                }
                invalidate(true);
                return;
            }
        }
    }

    public int getTransientViewCount() {
        IntArray intArray = this.mTransientIndices;
        if (intArray == null) {
            return 0;
        }
        return intArray.size();
    }

    public int getTransientViewIndex(int i) {
        IntArray intArray;
        if (i < 0 || (intArray = this.mTransientIndices) == null || i >= intArray.size()) {
            return -1;
        }
        return this.mTransientIndices.get(i);
    }

    public View getTransientView(int i) {
        List<View> list = this.mTransientViews;
        if (list == null || i >= list.size()) {
            return null;
        }
        return this.mTransientViews.get(i);
    }

    public void addView(View view) {
        addView(view, -1);
    }

    public void addView(View view, int i) {
        if (view == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null && (layoutParams = generateDefaultLayoutParams()) == null) {
            throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null  ");
        }
        addView(view, i, layoutParams);
    }

    public void addView(View view, int i, int i2) {
        LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
        layoutParamsGenerateDefaultLayoutParams.width = i;
        layoutParamsGenerateDefaultLayoutParams.height = i2;
        addView(view, -1, layoutParamsGenerateDefaultLayoutParams);
    }

    public void addView(View view, LayoutParams layoutParams) {
        addView(view, -1, layoutParams);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        if (view == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        requestLayout();
        invalidate(true);
        addViewInner(view, i, layoutParams, false);
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(View view, LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            throw new IllegalArgumentException("Invalid LayoutParams supplied to " + this);
        }
        if (view.mParent != this) {
            throw new IllegalArgumentException("Given view not a child of " + this);
        }
        view.setLayoutParams(layoutParams);
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    void dispatchViewAdded(View view) {
        onViewAdded(view);
        OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewAdded(this, view);
        }
        if (view == null || view.mOnAddRemoveListener == null) {
            return;
        }
        view.mOnAddRemoveListener.onViewAdded(this, view);
    }

    void dispatchViewRemoved(View view) {
        onViewRemoved(view);
        OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewRemoved(this, view);
        }
        if (view == null || view.mOnAddRemoveListener == null) {
            return;
        }
        view.mOnAddRemoveListener.onViewRemoved(this, view);
    }

    private void clearCachedLayoutMode() {
        if (hasBooleanFlag(8388608)) {
            return;
        }
        this.mLayoutMode = -1;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        clearCachedLayoutMode();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearCachedLayoutMode();
    }

    @Override // android.view.View
    protected void destroyHardwareResources() {
        super.destroyHardwareResources();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).destroyHardwareResources();
        }
    }

    protected boolean addViewInLayout(View view, int i, LayoutParams layoutParams) {
        return addViewInLayout(view, i, layoutParams, false);
    }

    protected boolean addViewInLayout(View view, int i, LayoutParams layoutParams, boolean z) {
        if (view == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        view.mParent = null;
        addViewInner(view, i, layoutParams, z);
        view.mPrivateFlags = (view.mPrivateFlags & (-2097153)) | 32;
        return true;
    }

    protected void cleanupLayoutState(View view) {
        view.mPrivateFlags &= -4097;
    }

    private void addViewInner(View view, int i, LayoutParams layoutParams, boolean z) {
        LayoutTransition layoutTransition = this.mTransition;
        if (layoutTransition != null) {
            layoutTransition.cancel(3);
        }
        if (view.getParent() != null) {
            throw new IllegalStateException("The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        LayoutTransition layoutTransition2 = this.mTransition;
        if (layoutTransition2 != null) {
            layoutTransition2.addChild(this, view);
        }
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        if (z) {
            view.mLayoutParams = layoutParams;
        } else {
            view.setLayoutParams(layoutParams);
        }
        if (i < 0) {
            i = this.mChildrenCount;
        }
        addInArray(view, i);
        if (z) {
            view.assignParent(this);
        } else {
            view.mParent = this;
        }
        if (view.hasUnhandledKeyListener()) {
            incrementChildUnhandledKeyListeners();
        }
        if (view.hasFocus()) {
            requestChildFocus(view, view.findFocus());
        }
        View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && (this.mGroupFlags & 4194304) == 0) {
            boolean z2 = attachInfo.mKeepScreenOn;
            attachInfo.mKeepScreenOn = false;
            view.dispatchAttachedToWindow(this.mAttachInfo, this.mViewFlags & 12);
            if (attachInfo.mKeepScreenOn) {
                needGlobalAttributesUpdate(true);
            }
            attachInfo.mKeepScreenOn = z2;
        }
        if (view.isLayoutDirectionInherited()) {
            view.resetRtlProperties();
        }
        dispatchViewAdded(view);
        if ((view.mViewFlags & 4194304) == 4194304) {
            this.mGroupFlags |= 65536;
        }
        if (view.hasTransientState()) {
            childHasTransientStateChanged(view, true);
        }
        if (view.getVisibility() != 8) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        IntArray intArray = this.mTransientIndices;
        if (intArray != null) {
            int size = intArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int i3 = this.mTransientIndices.get(i2);
                if (i <= i3) {
                    this.mTransientIndices.set(i2, i3 + 1);
                }
            }
        }
        if (this.mCurrentDragStartEvent != null && view.getVisibility() == 0) {
            notifyChildOfDragStart(view);
        } else if (view.getVisibility() == 0 && getParent() != null) {
            getParent().requestSendStickyDragStartedEvent(this);
        }
        if (view.hasDefaultFocus()) {
            setDefaultFocus(view);
        }
        touchAccessibilityNodeProviderIfNeeded(view);
        if (!sToolkitViewGroupFrameRateApiFlagValue || Float.isNaN(getRequestedFrameRate()) || (this.mGroupFlags & 1073741824) == 0) {
            return;
        }
        view.overrideFrameRate(getRequestedFrameRate(), getForcedOverrideFrameRateFlag());
    }

    private void touchAccessibilityNodeProviderIfNeeded(View view) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            view.getAccessibilityNodeProvider();
        }
    }

    private void addInArray(View view, int i) {
        View[] viewArr = this.mChildren;
        int i2 = this.mChildrenCount;
        int length = viewArr.length;
        if (i == i2) {
            if (length == i2) {
                View[] viewArr2 = new View[length + 12];
                this.mChildren = viewArr2;
                System.arraycopy(viewArr, 0, viewArr2, 0, length);
                viewArr = this.mChildren;
            }
            int i3 = this.mChildrenCount;
            this.mChildrenCount = i3 + 1;
            viewArr[i3] = view;
            return;
        }
        if (i < i2) {
            if (length == i2) {
                View[] viewArr3 = new View[length + 12];
                this.mChildren = viewArr3;
                System.arraycopy(viewArr, 0, viewArr3, 0, i);
                System.arraycopy(viewArr, i, this.mChildren, i + 1, i2 - i);
                viewArr = this.mChildren;
            } else {
                System.arraycopy(viewArr, i, viewArr, i + 1, i2 - i);
            }
            viewArr[i] = view;
            this.mChildrenCount++;
            int i4 = this.mLastTouchDownIndex;
            if (i4 >= i) {
                this.mLastTouchDownIndex = i4 + 1;
                return;
            }
            return;
        }
        throw new IndexOutOfBoundsException("index=" + i + " count=" + i2);
    }

    private void removeFromArray(int i) {
        View view;
        View[] viewArr = this.mChildren;
        ArrayList<View> arrayList = this.mTransitioningViews;
        if ((arrayList == null || !arrayList.contains(viewArr[i])) && (view = viewArr[i]) != null) {
            view.mParent = null;
        }
        int i2 = this.mChildrenCount;
        if (i == i2 - 1) {
            int i3 = i2 - 1;
            this.mChildrenCount = i3;
            viewArr[i3] = null;
        } else if (i >= 0 && i < i2) {
            System.arraycopy(viewArr, i + 1, viewArr, i, (i2 - i) - 1);
            int i4 = this.mChildrenCount - 1;
            this.mChildrenCount = i4;
            viewArr[i4] = null;
        } else {
            throw new IndexOutOfBoundsException();
        }
        int i5 = this.mLastTouchDownIndex;
        if (i5 == i) {
            this.mLastTouchDownTime = 0L;
            this.mLastTouchDownIndex = -1;
        } else if (i5 > i) {
            this.mLastTouchDownIndex = i5 - 1;
        }
    }

    private void removeFromArray(int i, int i2) {
        View[] viewArr = this.mChildren;
        int i3 = this.mChildrenCount;
        int iMax = Math.max(0, i);
        int iMin = Math.min(i3, i2 + iMax);
        if (iMax == iMin) {
            return;
        }
        if (iMin == i3) {
            for (int i4 = iMax; i4 < iMin; i4++) {
                viewArr[i4].mParent = null;
                viewArr[i4] = null;
            }
        } else {
            for (int i5 = iMax; i5 < iMin; i5++) {
                viewArr[i5].mParent = null;
            }
            System.arraycopy(viewArr, iMin, viewArr, iMax, i3 - iMin);
            for (int i6 = i3 - (iMin - iMax); i6 < i3; i6++) {
                viewArr[i6] = null;
            }
        }
        this.mChildrenCount -= iMin - iMax;
    }

    private void bindLayoutAnimation(View view) {
        view.setAnimation(this.mLayoutAnimationController.getAnimationForView(view));
    }

    protected void attachLayoutAnimationParameters(View view, LayoutParams layoutParams, int i, int i2) {
        LayoutAnimationController.AnimationParameters animationParameters = layoutParams.layoutAnimationParameters;
        if (animationParameters == null) {
            animationParameters = new LayoutAnimationController.AnimationParameters();
            layoutParams.layoutAnimationParameters = animationParameters;
        }
        animationParameters.count = i2;
        animationParameters.index = i;
    }

    @Override // android.view.ViewManager
    public void removeView(View view) {
        if (removeViewInternal(view)) {
            requestLayout();
            invalidate(true);
        }
    }

    public void removeViewInLayout(View view) throws Resources.NotFoundException {
        removeViewInternal(view);
    }

    public void removeViewsInLayout(int i, int i2) throws Resources.NotFoundException {
        removeViewsInternal(i, i2);
    }

    public void removeViewAt(int i) throws Resources.NotFoundException {
        removeViewInternal(i, getChildAt(i));
        requestLayout();
        invalidate(true);
    }

    public void removeViews(int i, int i2) throws Resources.NotFoundException {
        removeViewsInternal(i, i2);
        requestLayout();
        invalidate(true);
    }

    private boolean removeViewInternal(View view) throws Resources.NotFoundException {
        int iIndexOfChild = indexOfChild(view);
        if (iIndexOfChild < 0) {
            return false;
        }
        removeViewInternal(iIndexOfChild, view);
        return true;
    }

    private void removeViewInternal(int i, View view) throws Resources.NotFoundException {
        boolean z;
        ArrayList<View> arrayList;
        LayoutTransition layoutTransition = this.mTransition;
        if (layoutTransition != null) {
            layoutTransition.removeChild(this, view);
        }
        if (view == this.mFocused) {
            view.unFocus(null);
            z = true;
        } else {
            z = false;
        }
        if (view == this.mFocusedInCluster) {
            clearFocusedInCluster(view);
        }
        view.clearAccessibilityFocus();
        cancelTouchTarget(view);
        cancelHoverTarget(view);
        if (view.getAnimation() != null || ((arrayList = this.mTransitioningViews) != null && arrayList.contains(view))) {
            addDisappearingView(view);
        } else if (view.mAttachInfo != null) {
            view.dispatchDetachedFromWindow();
        }
        if (view.hasTransientState()) {
            childHasTransientStateChanged(view, false);
        }
        needGlobalAttributesUpdate(false);
        removeFromArray(i);
        if (view.hasUnhandledKeyListener()) {
            decrementChildUnhandledKeyListeners();
        }
        if (view == this.mDefaultFocus) {
            clearDefaultFocus(view);
        }
        if (z) {
            clearChildFocus(view);
            if (!rootViewRequestFocus()) {
                notifyGlobalFocusCleared(this);
            }
        }
        dispatchViewRemoved(view);
        if (view.getVisibility() != 8) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        IntArray intArray = this.mTransientIndices;
        int size = intArray == null ? 0 : intArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = this.mTransientIndices.get(i2);
            if (i < i3) {
                this.mTransientIndices.set(i2, i3 - 1);
            }
        }
        if (this.mCurrentDragStartEvent != null) {
            this.mChildrenInterestedInDrag.remove(view);
        }
    }

    public void setLayoutTransition(LayoutTransition layoutTransition) {
        LayoutTransition layoutTransition2 = this.mTransition;
        if (layoutTransition2 != null) {
            layoutTransition2.cancel();
            layoutTransition2.removeTransitionListener(this.mLayoutTransitionListener);
        }
        this.mTransition = layoutTransition;
        if (layoutTransition != null) {
            layoutTransition.addTransitionListener(this.mLayoutTransitionListener);
        }
    }

    public LayoutTransition getLayoutTransition() {
        return this.mTransition;
    }

    private void removeViewsInternal(int i, int i2) throws Resources.NotFoundException {
        ArrayList<View> arrayList;
        int i3 = i + i2;
        if (i < 0 || i2 < 0 || i3 > this.mChildrenCount) {
            throw new IndexOutOfBoundsException();
        }
        View view = this.mFocused;
        boolean z = this.mAttachInfo != null;
        View[] viewArr = this.mChildren;
        boolean z2 = false;
        View view2 = null;
        for (int i4 = i; i4 < i3; i4++) {
            View view3 = viewArr[i4];
            LayoutTransition layoutTransition = this.mTransition;
            if (layoutTransition != null) {
                layoutTransition.removeChild(this, view3);
            }
            if (view3 == view) {
                view3.unFocus(null);
                z2 = true;
            }
            if (view3 == this.mDefaultFocus) {
                view2 = view3;
            }
            if (view3 == this.mFocusedInCluster) {
                clearFocusedInCluster(view3);
            }
            view3.clearAccessibilityFocus();
            cancelTouchTarget(view3);
            cancelHoverTarget(view3);
            if (view3.getAnimation() != null || ((arrayList = this.mTransitioningViews) != null && arrayList.contains(view3))) {
                addDisappearingView(view3);
            } else if (z) {
                view3.dispatchDetachedFromWindow();
            }
            if (view3.hasTransientState()) {
                childHasTransientStateChanged(view3, false);
            }
            needGlobalAttributesUpdate(false);
            dispatchViewRemoved(view3);
        }
        removeFromArray(i, i2);
        if (view2 != null) {
            clearDefaultFocus(view2);
        }
        if (z2) {
            clearChildFocus(view);
            if (rootViewRequestFocus()) {
                return;
            }
            notifyGlobalFocusCleared(view);
        }
    }

    public void removeAllViews() throws Resources.NotFoundException {
        removeAllViewsInLayout();
        requestLayout();
        invalidate(true);
    }

    public void removeAllViewsInLayout() throws Resources.NotFoundException {
        ArrayList<View> arrayList;
        int i = this.mChildrenCount;
        if (i <= 0) {
            return;
        }
        View[] viewArr = this.mChildren;
        this.mChildrenCount = 0;
        View view = this.mFocused;
        boolean z = this.mAttachInfo != null;
        needGlobalAttributesUpdate(false);
        boolean z2 = false;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            View view2 = viewArr[i2];
            LayoutTransition layoutTransition = this.mTransition;
            if (layoutTransition != null) {
                layoutTransition.removeChild(this, view2);
            }
            if (view2 == view) {
                view2.unFocus(null);
                z2 = true;
            }
            view2.clearAccessibilityFocus();
            cancelTouchTarget(view2);
            cancelHoverTarget(view2);
            if (view2.getAnimation() != null || ((arrayList = this.mTransitioningViews) != null && arrayList.contains(view2))) {
                addDisappearingView(view2);
            } else if (z) {
                view2.dispatchDetachedFromWindow();
            }
            if (view2.hasTransientState()) {
                childHasTransientStateChanged(view2, false);
            }
            dispatchViewRemoved(view2);
            view2.mParent = null;
            viewArr[i2] = null;
        }
        View view3 = this.mDefaultFocus;
        if (view3 != null) {
            clearDefaultFocus(view3);
        }
        View view4 = this.mFocusedInCluster;
        if (view4 != null) {
            clearFocusedInCluster(view4);
        }
        if (z2) {
            clearChildFocus(view);
            if (rootViewRequestFocus()) {
                return;
            }
            notifyGlobalFocusCleared(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z) {
        ArrayList<View> arrayList;
        LayoutTransition layoutTransition = this.mTransition;
        if (layoutTransition != null) {
            layoutTransition.removeChild(this, view);
        }
        if (view == this.mFocused) {
            view.clearFocus();
        }
        if (view == this.mDefaultFocus) {
            clearDefaultFocus(view);
        }
        if (view == this.mFocusedInCluster) {
            clearFocusedInCluster(view);
        }
        view.clearAccessibilityFocus();
        cancelTouchTarget(view);
        cancelHoverTarget(view);
        if ((z && view.getAnimation() != null) || ((arrayList = this.mTransitioningViews) != null && arrayList.contains(view))) {
            addDisappearingView(view);
        } else if (view.mAttachInfo != null) {
            view.dispatchDetachedFromWindow();
        }
        if (view.hasTransientState()) {
            childHasTransientStateChanged(view, false);
        }
        dispatchViewRemoved(view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void attachViewToParent(View view, int i, LayoutParams layoutParams) {
        view.mLayoutParams = layoutParams;
        if (i < 0) {
            i = this.mChildrenCount;
        }
        addInArray(view, i);
        view.mParent = this;
        view.mPrivateFlags = (view.mPrivateFlags & (-2129921)) | (-2147483616);
        boolean z = false;
        view.setDetached(false);
        this.mPrivateFlags |= Integer.MIN_VALUE;
        if (view.hasFocus()) {
            requestChildFocus(view, view.findFocus());
        }
        if (isAttachedToWindow() && getWindowVisibility() == 0 && isShown()) {
            z = true;
        }
        dispatchVisibilityAggregated(z);
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    protected void detachViewFromParent(View view) {
        view.setDetached(true);
        removeFromArray(indexOfChild(view));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void detachViewFromParent(int i) {
        if (i >= 0 && i < this.mChildrenCount) {
            this.mChildren[i].setDetached(true);
        }
        removeFromArray(i);
    }

    protected void detachViewsFromParent(int i, int i2) {
        int iMax = Math.max(0, i);
        int iMin = Math.min(this.mChildrenCount, iMax + i2);
        for (int i3 = iMax; i3 < iMin; i3++) {
            this.mChildren[i3].setDetached(true);
        }
        removeFromArray(iMax, i2);
    }

    protected void detachAllViewsFromParent() {
        int i = this.mChildrenCount;
        if (i <= 0) {
            return;
        }
        View[] viewArr = this.mChildren;
        this.mChildrenCount = 0;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            viewArr[i2].mParent = null;
            viewArr[i2].setDetached(true);
            viewArr[i2] = null;
        }
    }

    @Override // android.view.ViewParent
    public void onDescendantInvalidated(View view, View view2) {
        this.mPrivateFlags |= view2.mPrivateFlags & 64;
        if ((view2.mPrivateFlags & (-2097153)) != 0) {
            this.mPrivateFlags = (this.mPrivateFlags & (-2097153)) | 2097152;
            this.mPrivateFlags &= -32769;
        }
        if (this.mLayerType == 1) {
            this.mPrivateFlags |= -2145386496;
            view2 = this;
        }
        if (this.mParent != null) {
            this.mParent.onDescendantInvalidated(this, view2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r9v2, types: [android.view.ViewParent] */
    @Override // android.view.ViewParent
    @Deprecated
    public final void invalidateChild(View view, Rect rect) {
        View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mHardwareAccelerated) {
            onDescendantInvalidated(view, view);
            return;
        }
        if (attachInfo != null) {
            boolean z = (view.mPrivateFlags & 64) != 0;
            Matrix matrix = view.getMatrix();
            if (view.mLayerType != 0) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
                this.mPrivateFlags &= -32769;
            }
            int[] iArr = attachInfo.mInvalidateChildLocation;
            iArr[0] = view.mLeft;
            iArr[1] = view.mTop;
            if (!matrix.isIdentity() || (this.mGroupFlags & 2048) != 0) {
                RectF rectF = attachInfo.mTmpTransformRect;
                rectF.set(rect);
                if ((this.mGroupFlags & 2048) != 0) {
                    Transformation transformation = attachInfo.mTmpTransformation;
                    if (getChildStaticTransformation(view, transformation)) {
                        Matrix matrix2 = attachInfo.mTmpMatrix;
                        matrix2.set(transformation.getMatrix());
                        if (!matrix.isIdentity()) {
                            matrix2.preConcat(matrix);
                        }
                        matrix = matrix2;
                    }
                }
                matrix.mapRect(rectF);
                rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
            }
            do {
                View view2 = this instanceof View ? this : null;
                if (z) {
                    if (view2 != null) {
                        view2.mPrivateFlags |= 64;
                    } else if (this instanceof ViewRootImpl) {
                        this.mIsAnimating = true;
                    }
                }
                if (view2 != null && (view2.mPrivateFlags & 2097152) != 2097152) {
                    view2.mPrivateFlags = (view2.mPrivateFlags & (-2097153)) | 2097152;
                }
                this = this.invalidateChildInParent(iArr, rect);
                if (view2 != null) {
                    Matrix matrix3 = view2.getMatrix();
                    if (!matrix3.isIdentity()) {
                        RectF rectF2 = attachInfo.mTmpTransformRect;
                        rectF2.set(rect);
                        matrix3.mapRect(rectF2);
                        rect.set((int) Math.floor(rectF2.left), (int) Math.floor(rectF2.top), (int) Math.ceil(rectF2.right), (int) Math.ceil(rectF2.bottom));
                    }
                }
            } while (this != 0);
        }
    }

    @Override // android.view.ViewParent
    @Deprecated
    public ViewParent invalidateChildInParent(int[] iArr, Rect rect) {
        if ((this.mPrivateFlags & 32800) == 0) {
            return null;
        }
        int i = this.mGroupFlags;
        if ((i & 144) != 128) {
            rect.offset(iArr[0] - this.mScrollX, iArr[1] - this.mScrollY);
            if ((this.mGroupFlags & 1) == 0) {
                rect.union(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            }
            int i2 = this.mLeft;
            int i3 = this.mTop;
            if ((this.mGroupFlags & 1) == 1 && !rect.intersect(0, 0, this.mRight - i2, this.mBottom - i3)) {
                rect.setEmpty();
            }
            iArr[0] = i2;
            iArr[1] = i3;
        } else {
            if ((i & 1) == 1) {
                rect.set(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            } else {
                rect.union(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            }
            iArr[0] = this.mLeft;
            iArr[1] = this.mTop;
            this.mPrivateFlags &= -33;
        }
        this.mPrivateFlags &= -32769;
        if (this.mLayerType != 0) {
            this.mPrivateFlags |= Integer.MIN_VALUE;
        }
        return this.mParent;
    }

    public final void offsetDescendantRectToMyCoords(View view, Rect rect) {
        offsetRectBetweenParentAndChild(view, rect, true, false);
    }

    public final void offsetRectIntoDescendantCoords(View view, Rect rect) {
        offsetRectBetweenParentAndChild(view, rect, false, false);
    }

    void offsetRectBetweenParentAndChild(View view, Rect rect, boolean z, boolean z2) {
        if (view == this) {
            return;
        }
        Object obj = view.mParent;
        View view2 = view;
        while (obj != null && (obj instanceof View) && obj != this) {
            if (z) {
                rect.offset(view2.mLeft - view2.mScrollX, view2.mTop - view2.mScrollY);
                if (z2) {
                    View view3 = (View) obj;
                    if (!rect.intersect(0, 0, view3.mRight - view3.mLeft, view3.mBottom - view3.mTop)) {
                        rect.setEmpty();
                    }
                }
            } else {
                if (z2) {
                    View view4 = (View) obj;
                    if (!rect.intersect(0, 0, view4.mRight - view4.mLeft, view4.mBottom - view4.mTop)) {
                        rect.setEmpty();
                    }
                }
                rect.offset(view2.mScrollX - view2.mLeft, view2.mScrollY - view2.mTop);
            }
            view2 = (View) obj;
            obj = view2.mParent;
        }
        if (obj != this) {
            offsetRectBetweenParentAndChildDebug(view, rect, z, z2);
            throw new IllegalArgumentException("parameter must be a descendant of this view");
        }
        if (z) {
            rect.offset(view2.mLeft - view2.mScrollX, view2.mTop - view2.mScrollY);
        } else {
            rect.offset(view2.mScrollX - view2.mLeft, view2.mScrollY - view2.mTop);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void offsetRectBetweenParentAndChildDebug(View view, Rect rect, boolean z, boolean z2) {
        int i;
        View view2 = view;
        Log.i(TAG, "void offsetRectBetweenParentAndChild() this=" + this);
        Log.i(TAG, "    : descendant=" + view2 + " rect=" + rect);
        Log.i(TAG, "    : offsetFromChildToParent=" + z + " clipToBounds=" + z2);
        if (view2 == this) {
            Log.i(TAG, "    : if (descendant == this) return;");
            return;
        }
        Object obj = view2.mParent;
        Log.i(TAG, "    : ViewParent theParent = descendant.mParent; theParent=" + obj);
        Log.i(TAG, "    : ========================================");
        StringBuilder sb = new StringBuilder("    : while #0 (theParent != null)= ");
        int i2 = 1;
        int i3 = 0;
        sb.append(obj != null);
        sb.append(" && (theParent instanceof View)= ");
        sb.append(obj instanceof View);
        sb.append(" && (theParent != this)= ");
        sb.append(obj != this);
        Log.i(TAG, sb.toString());
        Log.i(TAG, "    :    >>  descendant = " + view2);
        Log.i(TAG, "    :    >>  theParent = " + obj);
        int i4 = 0;
        while ((obj instanceof View) && obj != this) {
            i4 += i2;
            if (z) {
                rect.offset(view2.mLeft - view2.mScrollX, view2.mTop - view2.mScrollY);
                if (z2) {
                    View view3 = (View) obj;
                    if (!rect.intersect(i3, i3, view3.mRight - view3.mLeft, view3.mBottom - view3.mTop)) {
                        rect.setEmpty();
                    }
                }
                i = i3;
            } else {
                if (z2) {
                    View view4 = (View) obj;
                    int i5 = view4.mRight - view4.mLeft;
                    int i6 = view4.mBottom - view4.mTop;
                    i = 0;
                    if (!rect.intersect(0, 0, i5, i6)) {
                        rect.setEmpty();
                    }
                } else {
                    i = i3;
                }
                rect.offset(view2.mScrollX - view2.mLeft, view2.mScrollY - view2.mTop);
            }
            view2 = (View) obj;
            obj = view2.mParent;
            StringBuilder sb2 = new StringBuilder("    : while #");
            sb2.append(i4);
            sb2.append(" (theParent != null)= ");
            sb2.append(obj != null ? 1 : i);
            sb2.append(" && (theParent instanceof View)= ");
            sb2.append(obj instanceof View);
            sb2.append(" && (theParent != this)= ");
            sb2.append(obj != this ? 1 : i);
            Log.i(TAG, sb2.toString());
            Log.i(TAG, "    :    >>  descendant = " + view2);
            Log.i(TAG, "    :    >>  theParent = " + obj);
            i3 = i;
            i2 = 1;
        }
        Log.i(TAG, "    : ========================================");
        if (obj == this) {
            if (z) {
                rect.offset(view2.mLeft - view2.mScrollX, view2.mTop - view2.mScrollY);
                return;
            } else {
                rect.offset(view2.mScrollX - view2.mLeft, view2.mScrollY - view2.mTop);
                return;
            }
        }
        Log.i(TAG, "    : #Last  descendant=" + view2);
        Log.i(TAG, "    : #Last  theParent=" + obj);
        Log.i(TAG, "    : IllegalArgumentException(parameter must be a descendant of this view)");
    }

    public void offsetChildrenTopAndBottom(int i) {
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            View view = viewArr[i3];
            view.mTop += i;
            view.mBottom += i;
            if (view.mRenderNode != null) {
                view.mRenderNode.offsetTopAndBottom(i);
                z = true;
            }
        }
        if (z) {
            invalidateViewProperty(false, false);
        }
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    public void semOffsetChildrenLeftAndRight(int i) {
        int i2 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            View view = viewArr[i3];
            view.mLeft += i;
            view.mRight += i;
            if (view.mRenderNode != null) {
                view.mRenderNode.offsetLeftAndRight(i);
                z = true;
            }
        }
        if (z) {
            invalidateViewProperty(false, false);
        }
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    @Override // android.view.ViewParent
    public boolean getChildVisibleRect(View view, Rect rect, Point point) {
        return getChildVisibleRect(view, rect, point, false);
    }

    public boolean getChildVisibleRect(View view, Rect rect, Point point, boolean z) {
        RectF rectF = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new RectF();
        rectF.set(rect);
        if (!view.hasIdentityMatrix()) {
            view.getMatrix().mapRect(rectF);
        }
        int i = view.mLeft - this.mScrollX;
        int i2 = view.mTop - this.mScrollY;
        rectF.offset(i, i2);
        boolean zIntersect = true;
        if (point != null) {
            if (!view.hasIdentityMatrix()) {
                float[] fArr = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformLocation : new float[2];
                fArr[0] = point.x;
                fArr[1] = point.y;
                view.getMatrix().mapPoints(fArr);
                point.x = Math.round(fArr[0]);
                point.y = Math.round(fArr[1]);
            }
            point.x += i;
            point.y += i2;
        }
        int i3 = this.mRight - this.mLeft;
        int i4 = this.mBottom - this.mTop;
        if (this.mParent == null || ((this.mParent instanceof ViewGroup) && ((ViewGroup) this.mParent).getClipChildren())) {
            zIntersect = rectF.intersect(0.0f, 0.0f, i3, i4);
        }
        if ((z || zIntersect) && (this.mGroupFlags & 34) == 34) {
            zIntersect = rectF.intersect(this.mPaddingLeft, this.mPaddingTop, i3 - this.mPaddingRight, i4 - this.mPaddingBottom);
        }
        if ((z || zIntersect) && this.mClipBounds != null) {
            zIntersect = rectF.intersect(this.mClipBounds.left, this.mClipBounds.top, this.mClipBounds.right, this.mClipBounds.bottom);
        }
        rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
        if ((!z && !zIntersect) || this.mParent == null) {
            return zIntersect;
        }
        if (this.mParent instanceof ViewGroup) {
            return ((ViewGroup) this.mParent).getChildVisibleRect(this, rect, point, z);
        }
        return this.mParent.getChildVisibleRect(this, rect, point);
    }

    @Override // android.view.View
    public final void layout(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        LayoutTransition layoutTransition;
        if (!this.mSuppressLayout && ((layoutTransition = this.mTransition) == null || !layoutTransition.isChangingLayout())) {
            LayoutTransition layoutTransition2 = this.mTransition;
            if (layoutTransition2 != null) {
                layoutTransition2.layoutChange(this);
            }
            super.layout(i, i2, i3, i4);
            return;
        }
        this.mLayoutCalledWhileSuppressed = true;
    }

    protected boolean canAnimate() {
        return this.mLayoutAnimationController != null;
    }

    public void startLayoutAnimation() {
        if (this.mLayoutAnimationController != null) {
            this.mGroupFlags |= 8;
            requestLayout();
        }
    }

    public void scheduleLayoutAnimation() {
        this.mGroupFlags |= 8;
    }

    public void setLayoutAnimation(LayoutAnimationController layoutAnimationController) {
        this.mLayoutAnimationController = layoutAnimationController;
        if (layoutAnimationController != null) {
            this.mGroupFlags |= 8;
        }
    }

    public LayoutAnimationController getLayoutAnimation() {
        return this.mLayoutAnimationController;
    }

    @Deprecated
    public boolean isAnimationCacheEnabled() {
        return (this.mGroupFlags & 64) == 64;
    }

    @Deprecated
    public void setAnimationCacheEnabled(boolean z) {
        setBooleanFlag(64, z);
    }

    @Deprecated
    public boolean isAlwaysDrawnWithCacheEnabled() {
        return (this.mGroupFlags & 16384) == 16384;
    }

    @Deprecated
    public void setAlwaysDrawnWithCacheEnabled(boolean z) {
        setBooleanFlag(16384, z);
    }

    @Deprecated
    protected boolean isChildrenDrawnWithCacheEnabled() {
        return (this.mGroupFlags & 32768) == 32768;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public void setChildrenDrawnWithCacheEnabled(boolean z) {
        setBooleanFlag(32768, z);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    protected boolean isChildrenDrawingOrderEnabled() {
        return (this.mGroupFlags & 1024) == 1024;
    }

    protected void setChildrenDrawingOrderEnabled(boolean z) {
        setBooleanFlag(1024, z);
    }

    private boolean hasBooleanFlag(int i) {
        return (this.mGroupFlags & i) == i;
    }

    private void setBooleanFlag(int i, boolean z) {
        if (z) {
            this.mGroupFlags = i | this.mGroupFlags;
        } else {
            this.mGroupFlags = (~i) & this.mGroupFlags;
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing", mapping = {@ViewDebug.IntToString(from = 0, to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 1, to = "ANIMATION"), @ViewDebug.IntToString(from = 2, to = "SCROLLING"), @ViewDebug.IntToString(from = 3, to = "ALL")})
    @Deprecated
    public int getPersistentDrawingCache() {
        return this.mPersistentDrawingCache;
    }

    @Deprecated
    public void setPersistentDrawingCache(int i) {
        this.mPersistentDrawingCache = i & 3;
    }

    private void setLayoutMode(int i, boolean z) {
        this.mLayoutMode = i;
        setBooleanFlag(8388608, z);
    }

    @Override // android.view.View
    void invalidateInheritedLayoutMode(int i) {
        int i2 = this.mLayoutMode;
        if (i2 == -1 || i2 == i || hasBooleanFlag(8388608)) {
            return;
        }
        setLayoutMode(-1, false);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).invalidateInheritedLayoutMode(i);
        }
    }

    public int getLayoutMode() {
        if (this.mLayoutMode == -1) {
            setLayoutMode(this.mParent instanceof ViewGroup ? ((ViewGroup) this.mParent).getLayoutMode() : LAYOUT_MODE_DEFAULT, false);
        }
        return this.mLayoutMode;
    }

    public void setLayoutMode(int i) {
        if (this.mLayoutMode != i) {
            invalidateInheritedLayoutMode(i);
            setLayoutMode(i, i != -1);
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.View
    protected void debug(int i) {
        super.debug(i);
        if (this.mFocused != null) {
            Log.d("View", debugIndent(i) + "mFocused");
            this.mFocused.debug(i + 1);
        }
        if (this.mDefaultFocus != null) {
            Log.d("View", debugIndent(i) + "mDefaultFocus");
            this.mDefaultFocus.debug(i + 1);
        }
        if (this.mFocusedInCluster != null) {
            Log.d("View", debugIndent(i) + "mFocusedInCluster");
            this.mFocusedInCluster.debug(i + 1);
        }
        if (this.mChildrenCount != 0) {
            Log.d("View", debugIndent(i) + "{");
        }
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            this.mChildren[i3].debug(i + 1);
        }
        if (this.mChildrenCount != 0) {
            Log.d("View", debugIndent(i) + "}");
        }
    }

    public int indexOfChild(View view) {
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (viewArr[i2] == view) {
                return i2;
            }
        }
        return -1;
    }

    public int getChildCount() {
        return this.mChildrenCount;
    }

    public View getChildAt(int i) {
        if (i < 0 || i >= this.mChildrenCount) {
            return null;
        }
        return this.mChildren[i];
    }

    protected void measureChildren(int i, int i2) {
        int i3 = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i4 = 0; i4 < i3; i4++) {
            View view = viewArr[i4];
            if ((view.mViewFlags & 12) != 8) {
                measureChild(view, i, i2);
            }
        }
    }

    protected void measureChild(View view, int i, int i2) {
        LayoutParams layoutParams = view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight, layoutParams.width), getChildMeasureSpec(i2, this.mPaddingTop + this.mPaddingBottom, layoutParams.height));
    }

    protected void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), getChildMeasureSpec(i3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getChildMeasureSpec(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i) - i2;
        int i4 = 0;
        int iMax = Math.max(0, size);
        if (mode == Integer.MIN_VALUE) {
            if (i3 < 0) {
                if (i3 != -1 && i3 != -2) {
                }
            }
            i4 = 1073741824;
        } else if (mode != 0) {
            if (mode == 1073741824) {
                if (i3 < 0) {
                    if (i3 == -1) {
                        i3 = iMax;
                    } else if (i3 == -2) {
                        i3 = iMax;
                        i4 = Integer.MIN_VALUE;
                    }
                }
                i4 = 1073741824;
            }
        } else if (i3 >= 0) {
            i4 = 1073741824;
        } else {
            i3 = (i3 == -1 || i3 == -2) ? iMax : 0;
        }
        return View.MeasureSpec.makeMeasureSpec(i3, i4);
    }

    public void clearDisappearingChildren() {
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                View view = arrayList.get(i);
                if (view.mAttachInfo != null) {
                    view.dispatchDetachedFromWindow();
                }
                view.clearAnimation();
            }
            arrayList.clear();
            invalidate();
        }
    }

    private void addDisappearingView(View view) {
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mDisappearingChildren = arrayList;
        }
        arrayList.add(view);
    }

    void finishAnimatingView(View view, Animation animation) {
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList != null && arrayList.contains(view)) {
            arrayList.remove(view);
            if (view.mAttachInfo != null) {
                view.dispatchDetachedFromWindow();
            }
            view.clearAnimation();
            this.mGroupFlags |= 4;
        }
        if (animation != null && !animation.getFillAfter()) {
            view.clearAnimation();
        }
        if ((view.mPrivateFlags & 65536) == 65536) {
            view.onAnimationEnd();
            view.mPrivateFlags &= -65537;
            this.mGroupFlags |= 4;
        }
    }

    boolean isViewTransitioning(View view) {
        ArrayList<View> arrayList = this.mTransitioningViews;
        return arrayList != null && arrayList.contains(view);
    }

    public void startViewTransition(View view) {
        if (view.mParent == this) {
            if (this.mTransitioningViews == null) {
                this.mTransitioningViews = new ArrayList<>();
            }
            this.mTransitioningViews.add(view);
        }
    }

    public void endViewTransition(View view) {
        ArrayList<View> arrayList = this.mTransitioningViews;
        if (arrayList != null) {
            arrayList.remove(view);
            ArrayList<View> arrayList2 = this.mDisappearingChildren;
            if (arrayList2 == null || !arrayList2.contains(view)) {
                return;
            }
            arrayList2.remove(view);
            ArrayList<View> arrayList3 = this.mVisibilityChangingChildren;
            if (arrayList3 != null && arrayList3.contains(view)) {
                this.mVisibilityChangingChildren.remove(view);
            } else {
                if (view.mAttachInfo != null) {
                    view.dispatchDetachedFromWindow();
                }
                if (view.mParent != null) {
                    view.mParent = null;
                }
            }
            invalidate();
        }
    }

    public void suppressLayout(boolean z) {
        this.mSuppressLayout = z;
        if (z || !this.mLayoutCalledWhileSuppressed) {
            return;
        }
        requestLayout();
        this.mLayoutCalledWhileSuppressed = false;
    }

    public boolean isLayoutSuppressed() {
        return this.mSuppressLayout;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(Region region) {
        boolean z;
        boolean z2 = (this.mPrivateFlags & 512) == 0;
        if (z2 && region == null) {
            return true;
        }
        super.gatherTransparentRegion(region);
        int i = this.mChildrenCount;
        if (i > 0) {
            ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
            boolean z3 = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
            View[] viewArr = this.mChildren;
            z = true;
            for (int i2 = 0; i2 < i; i2++) {
                View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z3));
                if (((andVerifyPreorderedView.mViewFlags & 12) == 0 || andVerifyPreorderedView.getAnimation() != null) && !andVerifyPreorderedView.gatherTransparentRegion(region)) {
                    z = false;
                }
            }
            if (arrayListBuildOrderedChildList != null) {
                arrayListBuildOrderedChildList.clear();
            }
        } else {
            z = true;
        }
        return z2 || z;
    }

    @Override // android.view.ViewParent
    public void requestTransparentRegion(View view) {
        if (view != null) {
            view.mPrivateFlags |= 512;
            if (this.mParent != null) {
                this.mParent.requestTransparentRegion(this);
            }
        }
    }

    @Override // android.view.ViewParent
    public void subtractObscuredTouchableRegion(Region region, View view) {
        int i = this.mChildrenCount;
        ArrayList<View> arrayListBuildTouchDispatchChildList = buildTouchDispatchChildList();
        boolean z = arrayListBuildTouchDispatchChildList == null && isChildrenDrawingOrderEnabled();
        View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildTouchDispatchChildList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
            if (andVerifyPreorderedView == view) {
                break;
            }
            if (andVerifyPreorderedView.canReceivePointerEvents()) {
                applyOpToRegionByBounds(region, andVerifyPreorderedView, Region.Op.DIFFERENCE);
            }
        }
        applyOpToRegionByBounds(region, this, Region.Op.INTERSECT);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.subtractObscuredTouchableRegion(region, this);
        }
    }

    @Override // android.view.ViewParent
    public boolean getChildLocalHitRegion(View view, Region region, Matrix matrix, boolean z) {
        if (!view.hasIdentityMatrix()) {
            matrix.preConcat(view.getInverseMatrix());
        }
        matrix.preTranslate(-(view.mLeft - this.mScrollX), -(view.mTop - this.mScrollY));
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        RectF rectF = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new RectF();
        rectF.set(0.0f, 0.0f, i, i2);
        matrix.mapRect(rectF);
        boolean zOp = region.op(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom), Region.Op.INTERSECT);
        if (z) {
            HoverTarget hoverTarget = this.mFirstHoverTarget;
            while (true) {
                if (hoverTarget != null) {
                    HoverTarget hoverTarget2 = hoverTarget.next;
                    if (hoverTarget.child == view) {
                        break;
                    }
                    hoverTarget = hoverTarget2;
                } else {
                    HoverTarget hoverTarget3 = this.mFirstHoverTarget;
                    if (hoverTarget3 != null) {
                        ArrayList<View> arrayListBuildTouchDispatchChildList = buildTouchDispatchChildList();
                        while (zOp && hoverTarget3 != null) {
                            HoverTarget hoverTarget4 = hoverTarget3.next;
                            if (!isOnTop(view, hoverTarget3.child, arrayListBuildTouchDispatchChildList)) {
                                rectF.set(r0.mLeft, r0.mTop, r0.mRight, r0.mBottom);
                                matrix.mapRect(rectF);
                                zOp = region.op(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom), Region.Op.DIFFERENCE);
                            }
                            hoverTarget3 = hoverTarget4;
                        }
                        if (arrayListBuildTouchDispatchChildList != null) {
                            arrayListBuildTouchDispatchChildList.clear();
                        }
                    }
                }
            }
        } else {
            TouchTarget touchTarget = this.mFirstTouchTarget;
            while (true) {
                if (touchTarget != null) {
                    TouchTarget touchTarget2 = touchTarget.next;
                    if (touchTarget.child == view) {
                        break;
                    }
                    touchTarget = touchTarget2;
                } else {
                    TouchTarget touchTarget3 = this.mFirstTouchTarget;
                    if (touchTarget3 != null) {
                        ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
                        while (zOp && touchTarget3 != null) {
                            TouchTarget touchTarget4 = touchTarget3.next;
                            if (!isOnTop(view, touchTarget3.child, arrayListBuildOrderedChildList)) {
                                rectF.set(r0.mLeft, r0.mTop, r0.mRight, r0.mBottom);
                                matrix.mapRect(rectF);
                                zOp = region.op(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom), Region.Op.DIFFERENCE);
                            }
                            touchTarget3 = touchTarget4;
                        }
                        if (arrayListBuildOrderedChildList != null) {
                            arrayListBuildOrderedChildList.clear();
                        }
                    }
                }
            }
        }
        return (!zOp || this.mParent == null) ? zOp : this.mParent.getChildLocalHitRegion(this, region, matrix, z);
    }

    private boolean isOnTop(View view, View view2, ArrayList<View> arrayList) {
        int i = this.mChildrenCount;
        boolean z = arrayList == null && isChildrenDrawingOrderEnabled();
        View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            View andVerifyPreorderedView = getAndVerifyPreorderedView(arrayList, viewArr, getAndVerifyPreorderedIndex(i, i2, z));
            if (andVerifyPreorderedView == view) {
                return true;
            }
            if (andVerifyPreorderedView == view2) {
                return false;
            }
        }
        return false;
    }

    private static void applyOpToRegionByBounds(Region region, View view, Region.Op op) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        region.op(i, i2, i + view.getWidth(), i2 + view.getHeight(), op);
    }

    @Override // android.view.View
    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets windowInsetsDispatchApplyWindowInsets = super.dispatchApplyWindowInsets(windowInsets);
        if (windowInsetsDispatchApplyWindowInsets.isConsumed()) {
            return windowInsetsDispatchApplyWindowInsets;
        }
        if (View.sBrokenInsetsDispatch) {
            return brokenDispatchApplyWindowInsets(windowInsetsDispatchApplyWindowInsets);
        }
        return newDispatchApplyWindowInsets(windowInsetsDispatchApplyWindowInsets);
    }

    private WindowInsets brokenDispatchApplyWindowInsets(WindowInsets windowInsets) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            windowInsets = getChildAt(i).dispatchApplyWindowInsets(windowInsets);
            if (windowInsets.isConsumed()) {
                return windowInsets;
            }
        }
        return windowInsets;
    }

    private WindowInsets newDispatchApplyWindowInsets(WindowInsets windowInsets) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchApplyWindowInsets(windowInsets);
        }
        return windowInsets;
    }

    @Override // android.view.View
    public void setWindowInsetsAnimationCallback(WindowInsetsAnimation.Callback callback) {
        super.setWindowInsetsAnimationCallback(callback);
        this.mInsetsAnimationDispatchMode = callback != null ? callback.getDispatchMode() : 1;
    }

    @Override // android.view.View
    public boolean hasWindowInsetsAnimationCallback() {
        if (super.hasWindowInsetsAnimationCallback()) {
            return true;
        }
        if (((this.mViewFlags & 2048) != 0 || isFrameworkOptionalFitsSystemWindows()) && this.mAttachInfo != null && this.mAttachInfo.mContentOnApplyWindowInsetsListener != null && (getWindowSystemUiVisibility() & 1536) == 0) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).hasWindowInsetsAnimationCallback()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        super.dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
        if (((this.mViewFlags & 2048) != 0 || isFrameworkOptionalFitsSystemWindows()) && this.mAttachInfo != null && getListenerInfo().mWindowInsetsAnimationCallback == null && this.mAttachInfo.mContentOnApplyWindowInsetsListener != null && (getWindowSystemUiVisibility() & 1536) == 0) {
            this.mInsetsAnimationDispatchMode = 0;
        } else {
            if (this.mInsetsAnimationDispatchMode == 0) {
                return;
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
            }
        }
    }

    @Override // android.view.View
    public WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        WindowInsetsAnimation.Bounds boundsDispatchWindowInsetsAnimationStart = super.dispatchWindowInsetsAnimationStart(windowInsetsAnimation, bounds);
        if (this.mInsetsAnimationDispatchMode != 0) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).dispatchWindowInsetsAnimationStart(windowInsetsAnimation, boundsDispatchWindowInsetsAnimationStart);
            }
        }
        return boundsDispatchWindowInsetsAnimationStart;
    }

    @Override // android.view.View
    public WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
        WindowInsets windowInsetsDispatchWindowInsetsAnimationProgress = super.dispatchWindowInsetsAnimationProgress(windowInsets, list);
        if (this.mInsetsAnimationDispatchMode != 0) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).dispatchWindowInsetsAnimationProgress(windowInsetsDispatchWindowInsetsAnimationProgress, list);
            }
        }
        return windowInsetsDispatchWindowInsetsAnimationProgress;
    }

    @Override // android.view.View
    public void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation windowInsetsAnimation) {
        super.dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
        if (this.mInsetsAnimationDispatchMode == 0) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dispatchScrollCaptureSearch(Rect rect, Point point, Consumer<ScrollCaptureTarget> consumer) {
        ArrayList<View> arrayListBuildOrderedChildList;
        boolean z;
        int i;
        View andVerifyPreorderedView;
        if (getVisibility() != 0) {
            return;
        }
        if (getClipToPadding() && !rect.intersect(this.mPaddingLeft, this.mPaddingTop, (this.mRight - this.mLeft) - this.mPaddingRight, (this.mBottom - this.mTop) - this.mPaddingBottom)) {
            return;
        }
        super.dispatchScrollCaptureSearch(rect, point, consumer);
        int i2 = this.mChildrenCount;
        if (i2 == 0 || (getScrollCaptureHint() & 4) != 0) {
            return;
        }
        Rect tempRect = getTempRect();
        if (Flags.scrollCaptureTargetZOrderFix()) {
            arrayListBuildOrderedChildList = buildOrderedChildList();
            if (arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled()) {
                z = true;
            }
            View[] viewArr = this.mChildren;
            for (i = 0; i < i2; i++) {
                if (Flags.scrollCaptureTargetZOrderFix()) {
                    andVerifyPreorderedView = getAndVerifyPreorderedView(arrayListBuildOrderedChildList, viewArr, getAndVerifyPreorderedIndex(i2, i, z));
                } else {
                    andVerifyPreorderedView = viewArr[i];
                }
                if (andVerifyPreorderedView.getVisibility() == 0) {
                    tempRect.set(rect);
                    Point tempPoint = getTempPoint();
                    tempPoint.set(point.x, point.y);
                    int i3 = andVerifyPreorderedView.mLeft - this.mScrollX;
                    int i4 = andVerifyPreorderedView.mTop - this.mScrollY;
                    tempRect.offset(-i3, -i4);
                    tempPoint.offset(i3, i4);
                    if (getClipChildren() ? tempRect.intersect(0, 0, andVerifyPreorderedView.getWidth(), andVerifyPreorderedView.getHeight()) : true) {
                        andVerifyPreorderedView.dispatchScrollCaptureSearch(tempRect, tempPoint, consumer);
                    }
                }
            }
            if (arrayListBuildOrderedChildList == null) {
                arrayListBuildOrderedChildList.clear();
                return;
            }
            return;
        }
        arrayListBuildOrderedChildList = null;
        z = false;
        View[] viewArr2 = this.mChildren;
        while (i < i2) {
        }
        if (arrayListBuildOrderedChildList == null) {
        }
    }

    public Animation.AnimationListener getLayoutAnimationListener() {
        return this.mAnimationListener;
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int i = this.mGroupFlags;
        if ((65536 & i) != 0) {
            if ((i & 8192) != 0) {
                throw new IllegalStateException("addStateFromChildren cannot be enabled if a child has duplicateParentState set to true");
            }
            View[] viewArr = this.mChildren;
            int i2 = this.mChildrenCount;
            for (int i3 = 0; i3 < i2; i3++) {
                View view = viewArr[i3];
                if ((view.mViewFlags & 4194304) != 0) {
                    view.refreshDrawableState();
                }
            }
        }
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        View[] viewArr = this.mChildren;
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            viewArr[i2].jumpDrawablesToCurrentState();
        }
    }

    @Override // android.view.View
    protected int[] onCreateDrawableState(int i) {
        if ((this.mGroupFlags & 8192) == 0) {
            return super.onCreateDrawableState(i);
        }
        int childCount = getChildCount();
        int length = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            int[] drawableState = getChildAt(i2).getDrawableState();
            if (drawableState != null) {
                length += drawableState.length;
            }
        }
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + length);
        for (int i3 = 0; i3 < childCount; i3++) {
            int[] drawableState2 = getChildAt(i3).getDrawableState();
            if (drawableState2 != null) {
                iArrOnCreateDrawableState = mergeDrawableStates(iArrOnCreateDrawableState, drawableState2);
            }
        }
        return iArrOnCreateDrawableState;
    }

    public void setAddStatesFromChildren(boolean z) {
        if (z) {
            this.mGroupFlags |= 8192;
        } else {
            this.mGroupFlags &= -8193;
        }
        refreshDrawableState();
    }

    public boolean addStatesFromChildren() {
        return (this.mGroupFlags & 8192) != 0;
    }

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(View view) {
        if ((this.mGroupFlags & 8192) != 0) {
            refreshDrawableState();
        }
    }

    public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public void requestTransitionStart(LayoutTransition layoutTransition) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestTransitionStart(layoutTransition);
        }
    }

    @Override // android.view.View
    public boolean resolveRtlPropertiesIfNeeded() {
        boolean zResolveRtlPropertiesIfNeeded = super.resolveRtlPropertiesIfNeeded();
        if (zResolveRtlPropertiesIfNeeded) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.isLayoutDirectionInherited()) {
                    childAt.resolveRtlPropertiesIfNeeded();
                }
            }
        }
        return zResolveRtlPropertiesIfNeeded;
    }

    @Override // android.view.View
    public boolean resolveLayoutDirection() {
        boolean zResolveLayoutDirection = super.resolveLayoutDirection();
        if (zResolveLayoutDirection) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.isLayoutDirectionInherited()) {
                    childAt.resolveLayoutDirection();
                }
            }
        }
        return zResolveLayoutDirection;
    }

    @Override // android.view.View
    public boolean resolveTextDirection() {
        boolean zResolveTextDirection = super.resolveTextDirection();
        if (zResolveTextDirection) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.isTextDirectionInherited()) {
                    childAt.resolveTextDirection();
                }
            }
        }
        return zResolveTextDirection;
    }

    @Override // android.view.View
    public boolean resolveTextAlignment() {
        boolean zResolveTextAlignment = super.resolveTextAlignment();
        if (zResolveTextAlignment) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.isTextAlignmentInherited()) {
                    childAt.resolveTextAlignment();
                }
            }
        }
        return zResolveTextAlignment;
    }

    @Override // android.view.View
    public void resolvePadding() {
        super.resolvePadding();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited() && !childAt.isPaddingResolved()) {
                childAt.resolvePadding();
            }
        }
    }

    @Override // android.view.View
    protected void resolveDrawables() {
        super.resolveDrawables();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited() && !childAt.areDrawablesResolved()) {
                childAt.resolveDrawables();
            }
        }
    }

    @Override // android.view.View
    public void resolveLayoutParams() {
        super.resolveLayoutParams();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).resolveLayoutParams();
        }
    }

    @Override // android.view.View
    public void resetResolvedLayoutDirection() {
        super.resetResolvedLayoutDirection();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited()) {
                childAt.resetResolvedLayoutDirection();
            }
        }
    }

    @Override // android.view.View
    public void resetResolvedTextDirection() {
        super.resetResolvedTextDirection();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isTextDirectionInherited()) {
                childAt.resetResolvedTextDirection();
            }
        }
    }

    @Override // android.view.View
    public void resetResolvedTextAlignment() {
        super.resetResolvedTextAlignment();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isTextAlignmentInherited()) {
                childAt.resetResolvedTextAlignment();
            }
        }
    }

    @Override // android.view.View
    public void resetResolvedPadding() {
        super.resetResolvedPadding();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited()) {
                childAt.resetResolvedPadding();
            }
        }
    }

    @Override // android.view.View
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.isLayoutDirectionInherited()) {
                childAt.resetResolvedDrawables();
            }
        }
    }

    @Override // android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.mNestedScrollAxes = i;
    }

    @Override // android.view.ViewParent
    public void onStopNestedScroll(View view) {
        stopNestedScroll();
        this.mNestedScrollAxes = 0;
    }

    @Override // android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        dispatchNestedScroll(i, i2, i3, i4, null);
    }

    @Override // android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        dispatchNestedPreScroll(i, i2, iArr, null);
    }

    @Override // android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollAxes;
    }

    protected void onSetLayoutParams(View view, LayoutParams layoutParams) {
        requestLayout();
    }

    @Override // android.view.View
    public void captureTransitioningViews(List<View> list) {
        if (getVisibility() != 0) {
            return;
        }
        if (isTransitionGroup()) {
            list.add(this);
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).captureTransitioningViews(list);
        }
    }

    @Override // android.view.View
    public void findNamedViews(Map<String, View> map) {
        if (getVisibility() == 0 || this.mGhostView != null) {
            super.findNamedViews(map);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).findNamedViews(map);
            }
        }
    }

    @Override // android.view.View
    boolean hasUnhandledKeyListener() {
        return this.mChildUnhandledKeyListeners > 0 || super.hasUnhandledKeyListener();
    }

    void incrementChildUnhandledKeyListeners() {
        int i = this.mChildUnhandledKeyListeners + 1;
        this.mChildUnhandledKeyListeners = i;
        if (i == 1 && (this.mParent instanceof ViewGroup)) {
            ((ViewGroup) this.mParent).incrementChildUnhandledKeyListeners();
        }
    }

    void decrementChildUnhandledKeyListeners() {
        int i = this.mChildUnhandledKeyListeners - 1;
        this.mChildUnhandledKeyListeners = i;
        if (i == 0 && (this.mParent instanceof ViewGroup)) {
            ((ViewGroup) this.mParent).decrementChildUnhandledKeyListeners();
        }
    }

    @Override // android.view.View
    View dispatchUnhandledKeyEvent(KeyEvent keyEvent) {
        if (!hasUnhandledKeyListener()) {
            return null;
        }
        ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
        if (arrayListBuildOrderedChildList != null) {
            try {
                for (int size = arrayListBuildOrderedChildList.size() - 1; size >= 0; size--) {
                    View viewDispatchUnhandledKeyEvent = arrayListBuildOrderedChildList.get(size).dispatchUnhandledKeyEvent(keyEvent);
                    if (viewDispatchUnhandledKeyEvent != null) {
                        return viewDispatchUnhandledKeyEvent;
                    }
                }
            } finally {
                arrayListBuildOrderedChildList.clear();
            }
        } else {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                View viewDispatchUnhandledKeyEvent2 = getChildAt(childCount).dispatchUnhandledKeyEvent(keyEvent);
                if (viewDispatchUnhandledKeyEvent2 != null) {
                    return viewDispatchUnhandledKeyEvent2;
                }
            }
        }
        if (onUnhandledKeyEvent(keyEvent)) {
            return this;
        }
        return null;
    }

    private static final class TouchTarget {
        public static final int ALL_POINTER_IDS = -1;
        private static final int MAX_RECYCLED = 32;
        private static TouchTarget sRecycleBin;
        private static final Object sRecycleLock = new Object[0];
        private static int sRecycledCount;
        public View child;
        public TouchTarget next;
        public int pointerIdBits;

        private TouchTarget() {
        }

        public static TouchTarget obtain(View view, int i) {
            TouchTarget touchTarget;
            if (view == null) {
                throw new IllegalArgumentException("child must be non-null");
            }
            synchronized (sRecycleLock) {
                touchTarget = sRecycleBin;
                if (touchTarget == null) {
                    touchTarget = new TouchTarget();
                } else {
                    sRecycleBin = touchTarget.next;
                    sRecycledCount--;
                    touchTarget.next = null;
                }
            }
            touchTarget.child = view;
            touchTarget.pointerIdBits = i;
            return touchTarget;
        }

        public boolean isRecycled() {
            return this.child == null;
        }

        public void recycle() {
            if (this.child == null) {
                throw new IllegalStateException("already recycled once");
            }
            synchronized (sRecycleLock) {
                int i = sRecycledCount;
                if (i < 32) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount = i + 1;
                } else {
                    this.next = null;
                }
                this.child = null;
            }
        }
    }

    private static final class HoverTarget {
        private static final int MAX_RECYCLED = 32;
        private static HoverTarget sRecycleBin;
        private static final Object sRecycleLock = new Object[0];
        private static int sRecycledCount;
        public View child;
        public HoverTarget next;

        private HoverTarget() {
        }

        public static HoverTarget obtain(View view) {
            HoverTarget hoverTarget;
            if (view == null) {
                throw new IllegalArgumentException("child must be non-null");
            }
            synchronized (sRecycleLock) {
                hoverTarget = sRecycleBin;
                if (hoverTarget == null) {
                    hoverTarget = new HoverTarget();
                } else {
                    sRecycleBin = hoverTarget.next;
                    sRecycledCount--;
                    hoverTarget.next = null;
                }
            }
            hoverTarget.child = view;
            return hoverTarget;
        }

        public void recycle() {
            if (this.child == null) {
                throw new IllegalStateException("already recycled once");
            }
            synchronized (sRecycleLock) {
                int i = sRecycledCount;
                if (i < 32) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount = i + 1;
                } else {
                    this.next = null;
                }
                this.child = null;
            }
        }
    }

    private static class ChildListForAutoFillOrContentCapture extends ArrayList<View> {
        private static final int MAX_POOL_SIZE = 32;
        private static final Pools.SimplePool<ChildListForAutoFillOrContentCapture> sPool = new Pools.SimplePool<>(32);

        private ChildListForAutoFillOrContentCapture() {
        }

        public static ChildListForAutoFillOrContentCapture obtain() {
            ChildListForAutoFillOrContentCapture childListForAutoFillOrContentCaptureAcquire = sPool.acquire();
            return childListForAutoFillOrContentCaptureAcquire == null ? new ChildListForAutoFillOrContentCapture() : childListForAutoFillOrContentCaptureAcquire;
        }

        public void recycle() {
            clear();
            sPool.release(this);
        }
    }

    static class ChildListForAccessibility {
        private static final int MAX_POOL_SIZE = 32;
        private static final Pools.SynchronizedPool<ChildListForAccessibility> sPool = new Pools.SynchronizedPool<>(32);
        private final ArrayList<View> mChildren = new ArrayList<>();
        private final ArrayList<ViewLocationHolder> mHolders = new ArrayList<>();

        ChildListForAccessibility() {
        }

        public static ChildListForAccessibility obtain(ViewGroup viewGroup, boolean z) {
            ChildListForAccessibility childListForAccessibilityAcquire = sPool.acquire();
            if (childListForAccessibilityAcquire == null) {
                childListForAccessibilityAcquire = new ChildListForAccessibility();
            }
            childListForAccessibilityAcquire.init(viewGroup, z);
            return childListForAccessibilityAcquire;
        }

        public void recycle() {
            clear();
            sPool.release(this);
        }

        public int getChildCount() {
            return this.mChildren.size();
        }

        public View getChildAt(int i) {
            return this.mChildren.get(i);
        }

        private void init(ViewGroup viewGroup, boolean z) {
            ArrayList<View> arrayList = this.mChildren;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                arrayList.add(viewGroup.getChildAt(i));
            }
            if (z) {
                ArrayList<ViewLocationHolder> arrayList2 = this.mHolders;
                for (int i2 = 0; i2 < childCount; i2++) {
                    arrayList2.add(ViewLocationHolder.obtain(viewGroup, arrayList.get(i2)));
                }
                sort(arrayList2);
                for (int i3 = 0; i3 < childCount; i3++) {
                    ViewLocationHolder viewLocationHolder = arrayList2.get(i3);
                    arrayList.set(i3, viewLocationHolder.mView);
                    viewLocationHolder.recycle();
                }
                arrayList2.clear();
            }
        }

        private void sort(ArrayList<ViewLocationHolder> arrayList) {
            try {
                ViewLocationHolder.setComparisonStrategy(1);
                Collections.sort(arrayList);
            } catch (IllegalArgumentException unused) {
                ViewLocationHolder.setComparisonStrategy(2);
                Collections.sort(arrayList);
            }
        }

        private void clear() {
            this.mChildren.clear();
        }
    }

    static class ViewLocationHolder implements Comparable<ViewLocationHolder> {
        public static final int COMPARISON_STRATEGY_LOCATION = 2;
        public static final int COMPARISON_STRATEGY_STRIPE = 1;
        private static final int MAX_POOL_SIZE = 32;
        private int mLayoutDirection;
        private final Rect mLocation = new Rect();
        private ViewGroup mRoot;
        public View mView;
        private static final Pools.SynchronizedPool<ViewLocationHolder> sPool = new Pools.SynchronizedPool<>(32);
        private static int sComparisonStrategy = 1;

        ViewLocationHolder() {
        }

        public static ViewLocationHolder obtain(ViewGroup viewGroup, View view) {
            ViewLocationHolder viewLocationHolderAcquire = sPool.acquire();
            if (viewLocationHolderAcquire == null) {
                viewLocationHolderAcquire = new ViewLocationHolder();
            }
            viewLocationHolderAcquire.init(viewGroup, view);
            return viewLocationHolderAcquire;
        }

        public static void setComparisonStrategy(int i) {
            sComparisonStrategy = i;
        }

        public void recycle() {
            clear();
            sPool.release(this);
        }

        @Override // java.lang.Comparable
        public int compareTo(ViewLocationHolder viewLocationHolder) {
            if (viewLocationHolder == null) {
                return 1;
            }
            int iCompareBoundsOfTree = compareBoundsOfTree(this, viewLocationHolder);
            return iCompareBoundsOfTree != 0 ? iCompareBoundsOfTree : this.mView.getAccessibilityViewId() - viewLocationHolder.mView.getAccessibilityViewId();
        }

        private static int compareBoundsOfTree(ViewLocationHolder viewLocationHolder, ViewLocationHolder viewLocationHolder2) {
            if (sComparisonStrategy == 1) {
                if (viewLocationHolder.mLocation.bottom - viewLocationHolder2.mLocation.top <= 0) {
                    return -1;
                }
                if (viewLocationHolder.mLocation.top - viewLocationHolder2.mLocation.bottom >= 0) {
                    return 1;
                }
            }
            if (viewLocationHolder.mLayoutDirection == 0) {
                int i = viewLocationHolder.mLocation.left - viewLocationHolder2.mLocation.left;
                if (i != 0) {
                    return i;
                }
            } else {
                int i2 = viewLocationHolder.mLocation.right - viewLocationHolder2.mLocation.right;
                if (i2 != 0) {
                    return -i2;
                }
            }
            int i3 = viewLocationHolder.mLocation.top - viewLocationHolder2.mLocation.top;
            if (i3 != 0) {
                return i3;
            }
            int iHeight = viewLocationHolder.mLocation.height() - viewLocationHolder2.mLocation.height();
            if (iHeight != 0) {
                return -iHeight;
            }
            int iWidth = viewLocationHolder.mLocation.width() - viewLocationHolder2.mLocation.width();
            if (iWidth != 0) {
                return -iWidth;
            }
            final Rect rect = new Rect();
            final Rect rect2 = new Rect();
            final Rect rect3 = new Rect();
            viewLocationHolder.mView.getBoundsOnScreen(rect, true);
            viewLocationHolder2.mView.getBoundsOnScreen(rect2, true);
            View viewFindViewByPredicateTraversal = viewLocationHolder.mView.findViewByPredicateTraversal(new Predicate() { // from class: android.view.ViewGroup$ViewLocationHolder$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ViewGroup.ViewLocationHolder.lambda$compareBoundsOfTree$0(rect3, rect, (View) obj);
                }
            }, null);
            View viewFindViewByPredicateTraversal2 = viewLocationHolder2.mView.findViewByPredicateTraversal(new Predicate() { // from class: android.view.ViewGroup$ViewLocationHolder$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ViewGroup.ViewLocationHolder.lambda$compareBoundsOfTree$1(rect3, rect2, (View) obj);
                }
            }, null);
            if (viewFindViewByPredicateTraversal != null && viewFindViewByPredicateTraversal2 != null) {
                return compareBoundsOfTree(obtain(viewLocationHolder.mRoot, viewFindViewByPredicateTraversal), obtain(viewLocationHolder.mRoot, viewFindViewByPredicateTraversal2));
            }
            if (viewFindViewByPredicateTraversal != null) {
                return 1;
            }
            return viewFindViewByPredicateTraversal2 != null ? -1 : 0;
        }

        static /* synthetic */ boolean lambda$compareBoundsOfTree$0(Rect rect, Rect rect2, View view) {
            view.getBoundsOnScreen(rect, true);
            return !rect.equals(rect2);
        }

        static /* synthetic */ boolean lambda$compareBoundsOfTree$1(Rect rect, Rect rect2, View view) {
            view.getBoundsOnScreen(rect, true);
            return !rect.equals(rect2);
        }

        private void init(ViewGroup viewGroup, View view) {
            Rect rect = this.mLocation;
            view.getDrawingRect(rect);
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            this.mView = view;
            this.mRoot = viewGroup;
            this.mLayoutDirection = viewGroup.getLayoutDirection();
        }

        private void clear() {
            this.mView = null;
            this.mRoot = null;
            this.mLocation.set(0, 0, 0, 0);
        }
    }

    private static void drawRect(Canvas canvas, Paint paint, int i, int i2, int i3, int i4) {
        if (sDebugLines == null) {
            sDebugLines = new float[16];
        }
        float[] fArr = sDebugLines;
        float f = i;
        fArr[0] = f;
        float f2 = i2;
        fArr[1] = f2;
        float f3 = i3;
        fArr[2] = f3;
        fArr[3] = f2;
        fArr[4] = f3;
        fArr[5] = f2;
        fArr[6] = f3;
        float f4 = i4;
        fArr[7] = f4;
        fArr[8] = f3;
        fArr[9] = f4;
        fArr[10] = f;
        fArr[11] = f4;
        fArr[12] = f;
        fArr[13] = f4;
        fArr[14] = f;
        fArr[15] = f2;
        canvas.drawLines(fArr, paint);
    }

    @Override // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("focus:descendantFocusability", getDescendantFocusability());
        viewHierarchyEncoder.addProperty("drawing:clipChildren", getClipChildren());
        viewHierarchyEncoder.addProperty("drawing:clipToPadding", getClipToPadding());
        viewHierarchyEncoder.addProperty("drawing:childrenDrawingOrderEnabled", isChildrenDrawingOrderEnabled());
        viewHierarchyEncoder.addProperty("drawing:persistentDrawingCache", getPersistentDrawingCache());
        int childCount = getChildCount();
        viewHierarchyEncoder.addProperty("meta:__childCount__", (short) childCount);
        for (int i = 0; i < childCount; i++) {
            viewHierarchyEncoder.addPropertyKey("meta:__child__" + i);
            getChildAt(i).encode(viewHierarchyEncoder);
        }
    }

    @Override // android.view.ViewParent
    public final void onDescendantUnbufferedRequested() {
        View view = this.mFocused;
        int i = 0;
        this.mUnbufferedInputSource = view != null ? view.mUnbufferedInputSource & (-3) : 0;
        while (true) {
            if (i >= this.mChildrenCount) {
                break;
            }
            if ((this.mChildren[i].mUnbufferedInputSource & 2) != 0) {
                this.mUnbufferedInputSource |= 2;
                break;
            }
            i++;
        }
        if (this.mParent != null) {
            this.mParent.onDescendantUnbufferedRequested();
        }
    }

    @Override // android.view.View
    public void dispatchCreateViewTranslationRequest(Map<AutofillId, long[]> map, int[] iArr, TranslationCapability translationCapability, List<ViewTranslationRequest> list) {
        super.dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
        }
    }

    @Override // android.view.ViewParent
    public OnBackInvokedDispatcher findOnBackInvokedDispatcherForChild(View view, View view2) {
        ViewParent parent = getParent();
        if (parent != null) {
            return parent.findOnBackInvokedDispatcherForChild(this, view2);
        }
        return null;
    }

    @Override // android.view.View
    public void setRequestedFrameRate(float f) {
        if (!sToolkitViewGroupFrameRateApiFlagValue || getForcedOverrideFrameRateFlag()) {
            return;
        }
        super.setRequestedFrameRate(f);
        setSelfRequestedFrameRateFlag(!Float.isNaN(getRequestedFrameRate()));
        this.mGroupFlags &= -1073741825;
    }

    public void propagateRequestedFrameRate(float f, boolean z) {
        if (!sToolkitViewGroupFrameRateApiFlagValue || getForcedOverrideFrameRateFlag()) {
            return;
        }
        setSelfRequestedFrameRateFlag(false);
        overrideFrameRate(f, z);
        setSelfRequestedFrameRateFlag(true);
    }

    @Override // android.view.View
    void overrideFrameRate(float f, boolean z) {
        if (z || !getSelfRequestedFrameRateFlag()) {
            super.overrideFrameRate(f, z);
            this.mGroupFlags |= 1073741824;
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).overrideFrameRate(f, z);
            }
        }
    }

    @Override // android.view.View
    public boolean dispatchKeyEventTextMultiSelection(KeyEvent keyEvent) {
        if (!ViewRune.WIDGET_PEN_SUPPORTED) {
            return false;
        }
        int i = this.mChildrenCount;
        View[] viewArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (viewArr[i2].dispatchKeyEventTextMultiSelection(keyEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewParent
    public void requestSendStickyDragStartedEvent(View view) {
        DragEvent dragEvent = this.mCurrentDragStartEvent;
        if (dragEvent != null) {
            if (!dragEvent.isStickyEvent() || this.mChildrenInterestedInDrag.contains(view)) {
                return;
            }
            if (CoreRune.IS_DEBUG_LEVEL_MID) {
                Log.i("View", "requestSendStickyDragStartedEvent this=" + this + ", child=" + view);
            }
            notifyChildOfDragStart(view);
            return;
        }
        if (getParent() != null) {
            getParent().requestSendStickyDragStartedEvent(this);
        }
    }

    @Override // android.view.View
    public View semDispatchFindView(PointF pointF, boolean z, ISemTouchApi iSemTouchApi) {
        if (getVisibility() != 0) {
            return null;
        }
        if (iSemTouchApi.getViewContent(this.mContext, this.mContext.getPackageName(), this, pointF, null)) {
            return this;
        }
        int i = this.mChildrenCount;
        if (i == 0) {
            return null;
        }
        ArrayList<View> arrayListBuildOrderedChildList = buildOrderedChildList();
        boolean z2 = arrayListBuildOrderedChildList == null && isChildrenDrawingOrderEnabled();
        View[] viewArr = this.mChildren;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            int childDrawingOrder = z2 ? getChildDrawingOrder(i, i2) : i2;
            View view = arrayListBuildOrderedChildList == null ? viewArr[childDrawingOrder] : arrayListBuildOrderedChildList.get(childDrawingOrder);
            if (view.getVisibility() == 0 && isTransformedTouchPointInView(pointF.x, pointF.y, view, null)) {
                View viewSemDispatchFindView = view.semDispatchFindView(new PointF(pointF.x + ((this.mScrollX - view.mLeft) - view.getTranslationX()), pointF.y + ((this.mScrollY - view.mTop) - view.getTranslationY())), z, iSemTouchApi);
                if (viewSemDispatchFindView != null) {
                    if (arrayListBuildOrderedChildList != null) {
                        arrayListBuildOrderedChildList.clear();
                    }
                    return viewSemDispatchFindView;
                }
            }
        }
        if (arrayListBuildOrderedChildList != null) {
            arrayListBuildOrderedChildList.clear();
        }
        return null;
    }
}
