package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ConstraintSet {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static SparseIntArray sMapToConstant = new SparseIntArray();
    private static SparseIntArray sOverrideMapToConstant = new SparseIntArray();
    public String mIdString;
    public String derivedState = "";
    private String[] mMatchLabels = new String[0];
    public int mRotate = 0;
    private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap<>();
    private boolean mForceId = true;
    private HashMap<Integer, Constraint> mConstraints = new HashMap<>();

    public static class Constraint {
        Delta mDelta;
        String mTargetString;
        int mViewId;
        public final PropertySet propertySet = new PropertySet();
        public final Motion motion = new Motion();
        public final Layout layout = new Layout();
        public final Transform transform = new Transform();
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<>();

        static void access$700(Constraint constraint, ConstraintHelper constraintHelper, int i, Constraints.LayoutParams layoutParams) {
            constraint.fillFromConstraints(i, layoutParams);
            if (constraintHelper instanceof Barrier) {
                Layout layout = constraint.layout;
                layout.mHelperType = 1;
                Barrier barrier = (Barrier) constraintHelper;
                layout.mBarrierDirection = barrier.getType();
                layout.mReferenceIds = barrier.getReferencedIds();
                layout.mBarrierMargin = barrier.getMargin();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFrom(int i, ConstraintLayout.LayoutParams layoutParams) {
            this.mViewId = i;
            int i2 = layoutParams.leftToLeft;
            Layout layout = this.layout;
            layout.leftToLeft = i2;
            layout.leftToRight = layoutParams.leftToRight;
            layout.rightToLeft = layoutParams.rightToLeft;
            layout.rightToRight = layoutParams.rightToRight;
            layout.topToTop = layoutParams.topToTop;
            layout.topToBottom = layoutParams.topToBottom;
            layout.bottomToTop = layoutParams.bottomToTop;
            layout.bottomToBottom = layoutParams.bottomToBottom;
            layout.baselineToBaseline = layoutParams.baselineToBaseline;
            layout.baselineToTop = layoutParams.baselineToTop;
            layout.baselineToBottom = layoutParams.baselineToBottom;
            layout.startToEnd = layoutParams.startToEnd;
            layout.startToStart = layoutParams.startToStart;
            layout.endToStart = layoutParams.endToStart;
            layout.endToEnd = layoutParams.endToEnd;
            layout.horizontalBias = layoutParams.horizontalBias;
            layout.verticalBias = layoutParams.verticalBias;
            layout.dimensionRatio = layoutParams.dimensionRatio;
            layout.circleConstraint = layoutParams.circleConstraint;
            layout.circleRadius = layoutParams.circleRadius;
            layout.circleAngle = layoutParams.circleAngle;
            layout.editorAbsoluteX = layoutParams.editorAbsoluteX;
            layout.editorAbsoluteY = layoutParams.editorAbsoluteY;
            layout.orientation = layoutParams.orientation;
            layout.guidePercent = layoutParams.guidePercent;
            layout.guideBegin = layoutParams.guideBegin;
            layout.guideEnd = layoutParams.guideEnd;
            layout.mWidth = ((ViewGroup.MarginLayoutParams) layoutParams).width;
            layout.mHeight = ((ViewGroup.MarginLayoutParams) layoutParams).height;
            layout.leftMargin = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            layout.rightMargin = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            layout.topMargin = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            layout.bottomMargin = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            layout.baselineMargin = layoutParams.baselineMargin;
            layout.verticalWeight = layoutParams.verticalWeight;
            layout.horizontalWeight = layoutParams.horizontalWeight;
            layout.verticalChainStyle = layoutParams.verticalChainStyle;
            layout.horizontalChainStyle = layoutParams.horizontalChainStyle;
            layout.constrainedWidth = layoutParams.constrainedWidth;
            layout.constrainedHeight = layoutParams.constrainedHeight;
            layout.widthDefault = layoutParams.matchConstraintDefaultWidth;
            layout.heightDefault = layoutParams.matchConstraintDefaultHeight;
            layout.widthMax = layoutParams.matchConstraintMaxWidth;
            layout.heightMax = layoutParams.matchConstraintMaxHeight;
            layout.widthMin = layoutParams.matchConstraintMinWidth;
            layout.heightMin = layoutParams.matchConstraintMinHeight;
            layout.widthPercent = layoutParams.matchConstraintPercentWidth;
            layout.heightPercent = layoutParams.matchConstraintPercentHeight;
            layout.mConstraintTag = layoutParams.constraintTag;
            layout.goneTopMargin = layoutParams.goneTopMargin;
            layout.goneBottomMargin = layoutParams.goneBottomMargin;
            layout.goneLeftMargin = layoutParams.goneLeftMargin;
            layout.goneRightMargin = layoutParams.goneRightMargin;
            layout.goneStartMargin = layoutParams.goneStartMargin;
            layout.goneEndMargin = layoutParams.goneEndMargin;
            layout.goneBaselineMargin = layoutParams.goneBaselineMargin;
            layout.mWrapBehavior = layoutParams.wrapBehaviorInParent;
            layout.endMargin = layoutParams.getMarginEnd();
            layout.startMargin = layoutParams.getMarginStart();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFromConstraints(int i, Constraints.LayoutParams layoutParams) {
            fillFrom(i, layoutParams);
            this.propertySet.alpha = layoutParams.alpha;
            float f = layoutParams.rotation;
            Transform transform = this.transform;
            transform.rotation = f;
            transform.rotationX = layoutParams.rotationX;
            transform.rotationY = layoutParams.rotationY;
            transform.scaleX = layoutParams.scaleX;
            transform.scaleY = layoutParams.scaleY;
            transform.transformPivotX = layoutParams.transformPivotX;
            transform.transformPivotY = layoutParams.transformPivotY;
            transform.translationX = layoutParams.translationX;
            transform.translationY = layoutParams.translationY;
            transform.translationZ = layoutParams.translationZ;
            transform.elevation = layoutParams.elevation;
            transform.applyElevation = layoutParams.applyElevation;
        }

        public final void applyDelta(Constraint constraint) {
            Delta delta = this.mDelta;
            if (delta != null) {
                delta.applyDelta(constraint);
            }
        }

        public final void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            Layout layout = this.layout;
            layoutParams.leftToLeft = layout.leftToLeft;
            layoutParams.leftToRight = layout.leftToRight;
            layoutParams.rightToLeft = layout.rightToLeft;
            layoutParams.rightToRight = layout.rightToRight;
            layoutParams.topToTop = layout.topToTop;
            layoutParams.topToBottom = layout.topToBottom;
            layoutParams.bottomToTop = layout.bottomToTop;
            layoutParams.bottomToBottom = layout.bottomToBottom;
            layoutParams.baselineToBaseline = layout.baselineToBaseline;
            layoutParams.baselineToTop = layout.baselineToTop;
            layoutParams.baselineToBottom = layout.baselineToBottom;
            layoutParams.startToEnd = layout.startToEnd;
            layoutParams.startToStart = layout.startToStart;
            layoutParams.endToStart = layout.endToStart;
            layoutParams.endToEnd = layout.endToEnd;
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = layout.leftMargin;
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = layout.rightMargin;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = layout.topMargin;
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = layout.bottomMargin;
            layoutParams.goneStartMargin = layout.goneStartMargin;
            layoutParams.goneEndMargin = layout.goneEndMargin;
            layoutParams.goneTopMargin = layout.goneTopMargin;
            layoutParams.goneBottomMargin = layout.goneBottomMargin;
            layoutParams.horizontalBias = layout.horizontalBias;
            layoutParams.verticalBias = layout.verticalBias;
            layoutParams.circleConstraint = layout.circleConstraint;
            layoutParams.circleRadius = layout.circleRadius;
            layoutParams.circleAngle = layout.circleAngle;
            layoutParams.dimensionRatio = layout.dimensionRatio;
            layoutParams.editorAbsoluteX = layout.editorAbsoluteX;
            layoutParams.editorAbsoluteY = layout.editorAbsoluteY;
            layoutParams.verticalWeight = layout.verticalWeight;
            layoutParams.horizontalWeight = layout.horizontalWeight;
            layoutParams.verticalChainStyle = layout.verticalChainStyle;
            layoutParams.horizontalChainStyle = layout.horizontalChainStyle;
            layoutParams.constrainedWidth = layout.constrainedWidth;
            layoutParams.constrainedHeight = layout.constrainedHeight;
            layoutParams.matchConstraintDefaultWidth = layout.widthDefault;
            layoutParams.matchConstraintDefaultHeight = layout.heightDefault;
            layoutParams.matchConstraintMaxWidth = layout.widthMax;
            layoutParams.matchConstraintMaxHeight = layout.heightMax;
            layoutParams.matchConstraintMinWidth = layout.widthMin;
            layoutParams.matchConstraintMinHeight = layout.heightMin;
            layoutParams.matchConstraintPercentWidth = layout.widthPercent;
            layoutParams.matchConstraintPercentHeight = layout.heightPercent;
            layoutParams.orientation = layout.orientation;
            layoutParams.guidePercent = layout.guidePercent;
            layoutParams.guideBegin = layout.guideBegin;
            layoutParams.guideEnd = layout.guideEnd;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = layout.mWidth;
            ((ViewGroup.MarginLayoutParams) layoutParams).height = layout.mHeight;
            String str = layout.mConstraintTag;
            if (str != null) {
                layoutParams.constraintTag = str;
            }
            layoutParams.wrapBehaviorInParent = layout.mWrapBehavior;
            layoutParams.setMarginStart(layout.startMargin);
            layoutParams.setMarginEnd(layout.endMargin);
            layoutParams.validate();
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public final Constraint m17clone() {
            Constraint constraint = new Constraint();
            constraint.layout.copyFrom(this.layout);
            constraint.motion.copyFrom(this.motion);
            PropertySet propertySet = constraint.propertySet;
            propertySet.getClass();
            PropertySet propertySet2 = this.propertySet;
            propertySet.mApply = propertySet2.mApply;
            propertySet.visibility = propertySet2.visibility;
            propertySet.alpha = propertySet2.alpha;
            propertySet.mProgress = propertySet2.mProgress;
            propertySet.mVisibilityMode = propertySet2.mVisibilityMode;
            constraint.transform.copyFrom(this.transform);
            constraint.mViewId = this.mViewId;
            constraint.mDelta = this.mDelta;
            return constraint;
        }

        static class Delta {
            int[] mTypeInt = new int[10];
            int[] mValueInt = new int[10];
            int mCountInt = 0;
            int[] mTypeFloat = new int[10];
            float[] mValueFloat = new float[10];
            int mCountFloat = 0;
            int[] mTypeString = new int[5];
            String[] mValueString = new String[5];
            int mCountString = 0;
            int[] mTypeBoolean = new int[4];
            boolean[] mValueBoolean = new boolean[4];
            int mCountBoolean = 0;

            Delta() {
            }

            final void add(int i, int i2) {
                int i3 = this.mCountInt;
                int[] iArr = this.mTypeInt;
                if (i3 >= iArr.length) {
                    this.mTypeInt = Arrays.copyOf(iArr, iArr.length * 2);
                    int[] iArr2 = this.mValueInt;
                    this.mValueInt = Arrays.copyOf(iArr2, iArr2.length * 2);
                }
                int[] iArr3 = this.mTypeInt;
                int i4 = this.mCountInt;
                iArr3[i4] = i;
                int[] iArr4 = this.mValueInt;
                this.mCountInt = i4 + 1;
                iArr4[i4] = i2;
            }

            final void applyDelta(Constraint constraint) {
                for (int i = 0; i < this.mCountInt; i++) {
                    int i2 = this.mTypeInt[i];
                    int i3 = this.mValueInt[i];
                    int i4 = ConstraintSet.$r8$clinit;
                    if (i2 == 6) {
                        constraint.layout.editorAbsoluteX = i3;
                    } else if (i2 == 7) {
                        constraint.layout.editorAbsoluteY = i3;
                    } else if (i2 == 8) {
                        constraint.layout.endMargin = i3;
                    } else if (i2 == 27) {
                        constraint.layout.orientation = i3;
                    } else if (i2 == 28) {
                        constraint.layout.rightMargin = i3;
                    } else if (i2 == 41) {
                        constraint.layout.horizontalChainStyle = i3;
                    } else if (i2 == 42) {
                        constraint.layout.verticalChainStyle = i3;
                    } else if (i2 == 61) {
                        constraint.layout.circleConstraint = i3;
                    } else if (i2 == 62) {
                        constraint.layout.circleRadius = i3;
                    } else if (i2 == 72) {
                        constraint.layout.mBarrierDirection = i3;
                    } else if (i2 == 73) {
                        constraint.layout.mBarrierMargin = i3;
                    } else if (i2 == 2) {
                        constraint.layout.bottomMargin = i3;
                    } else if (i2 == 31) {
                        constraint.layout.startMargin = i3;
                    } else if (i2 == 34) {
                        constraint.layout.topMargin = i3;
                    } else if (i2 == 38) {
                        constraint.mViewId = i3;
                    } else if (i2 == 64) {
                        constraint.motion.mAnimateRelativeTo = i3;
                    } else if (i2 == 66) {
                        constraint.motion.mDrawPath = i3;
                    } else if (i2 == 76) {
                        constraint.motion.mPathMotionArc = i3;
                    } else if (i2 == 78) {
                        constraint.propertySet.mVisibilityMode = i3;
                    } else if (i2 == 97) {
                        constraint.layout.mWrapBehavior = i3;
                    } else if (i2 == 93) {
                        constraint.layout.baselineMargin = i3;
                    } else if (i2 != 94) {
                        switch (i2) {
                            case 11:
                                constraint.layout.goneBottomMargin = i3;
                                break;
                            case 12:
                                constraint.layout.goneEndMargin = i3;
                                break;
                            case 13:
                                constraint.layout.goneLeftMargin = i3;
                                break;
                            case 14:
                                constraint.layout.goneRightMargin = i3;
                                break;
                            case 15:
                                constraint.layout.goneStartMargin = i3;
                                break;
                            case 16:
                                constraint.layout.goneTopMargin = i3;
                                break;
                            case 17:
                                constraint.layout.guideBegin = i3;
                                break;
                            case 18:
                                constraint.layout.guideEnd = i3;
                                break;
                            default:
                                switch (i2) {
                                    case 21:
                                        constraint.layout.mHeight = i3;
                                        break;
                                    case 22:
                                        constraint.propertySet.visibility = i3;
                                        break;
                                    case 23:
                                        constraint.layout.mWidth = i3;
                                        break;
                                    case 24:
                                        constraint.layout.leftMargin = i3;
                                        break;
                                    default:
                                        switch (i2) {
                                            case 54:
                                                constraint.layout.widthDefault = i3;
                                                break;
                                            case 55:
                                                constraint.layout.heightDefault = i3;
                                                break;
                                            case 56:
                                                constraint.layout.widthMax = i3;
                                                break;
                                            case 57:
                                                constraint.layout.heightMax = i3;
                                                break;
                                            case 58:
                                                constraint.layout.widthMin = i3;
                                                break;
                                            case 59:
                                                constraint.layout.heightMin = i3;
                                                break;
                                            default:
                                                switch (i2) {
                                                    case 82:
                                                        constraint.motion.mAnimateCircleAngleTo = i3;
                                                        break;
                                                    case 83:
                                                        constraint.transform.transformPivotTarget = i3;
                                                        break;
                                                    case 84:
                                                        constraint.motion.mQuantizeMotionSteps = i3;
                                                        break;
                                                    default:
                                                        switch (i2) {
                                                            case 87:
                                                                break;
                                                            case 88:
                                                                constraint.motion.mQuantizeInterpolatorType = i3;
                                                                break;
                                                            case 89:
                                                                constraint.motion.mQuantizeInterpolatorID = i3;
                                                                break;
                                                            default:
                                                                Log.w("ConstraintSet", "Unknown attribute 0x");
                                                                break;
                                                        }
                                                }
                                        }
                                }
                        }
                    } else {
                        constraint.layout.goneBaselineMargin = i3;
                    }
                }
                for (int i5 = 0; i5 < this.mCountFloat; i5++) {
                    int i6 = this.mTypeFloat[i5];
                    float f = this.mValueFloat[i5];
                    int i7 = ConstraintSet.$r8$clinit;
                    if (i6 == 19) {
                        constraint.layout.guidePercent = f;
                    } else if (i6 == 20) {
                        constraint.layout.horizontalBias = f;
                    } else if (i6 == 37) {
                        constraint.layout.verticalBias = f;
                    } else if (i6 == 60) {
                        constraint.transform.rotation = f;
                    } else if (i6 == 63) {
                        constraint.layout.circleAngle = f;
                    } else if (i6 == 79) {
                        constraint.motion.mMotionStagger = f;
                    } else if (i6 == 85) {
                        constraint.motion.mQuantizeMotionPhase = f;
                    } else if (i6 != 87) {
                        if (i6 == 39) {
                            constraint.layout.horizontalWeight = f;
                        } else if (i6 != 40) {
                            switch (i6) {
                                case 43:
                                    constraint.propertySet.alpha = f;
                                    break;
                                case 44:
                                    Transform transform = constraint.transform;
                                    transform.elevation = f;
                                    transform.applyElevation = true;
                                    break;
                                case 45:
                                    constraint.transform.rotationX = f;
                                    break;
                                case 46:
                                    constraint.transform.rotationY = f;
                                    break;
                                case 47:
                                    constraint.transform.scaleX = f;
                                    break;
                                case 48:
                                    constraint.transform.scaleY = f;
                                    break;
                                case 49:
                                    constraint.transform.transformPivotX = f;
                                    break;
                                case 50:
                                    constraint.transform.transformPivotY = f;
                                    break;
                                case 51:
                                    constraint.transform.translationX = f;
                                    break;
                                case 52:
                                    constraint.transform.translationY = f;
                                    break;
                                case 53:
                                    constraint.transform.translationZ = f;
                                    break;
                                default:
                                    switch (i6) {
                                        case 67:
                                            constraint.motion.mPathRotate = f;
                                            break;
                                        case 68:
                                            constraint.propertySet.mProgress = f;
                                            break;
                                        case 69:
                                            constraint.layout.widthPercent = f;
                                            break;
                                        case 70:
                                            constraint.layout.heightPercent = f;
                                            break;
                                        default:
                                            Log.w("ConstraintSet", "Unknown attribute 0x");
                                            break;
                                    }
                            }
                        } else {
                            constraint.layout.verticalWeight = f;
                        }
                    }
                }
                for (int i8 = 0; i8 < this.mCountString; i8++) {
                    int i9 = this.mTypeString[i8];
                    String str = this.mValueString[i8];
                    int i10 = ConstraintSet.$r8$clinit;
                    if (i9 == 5) {
                        constraint.layout.dimensionRatio = str;
                    } else if (i9 == 65) {
                        constraint.motion.mTransitionEasing = str;
                    } else if (i9 == 74) {
                        Layout layout = constraint.layout;
                        layout.mReferenceIdString = str;
                        layout.mReferenceIds = null;
                    } else if (i9 == 77) {
                        constraint.layout.mConstraintTag = str;
                    } else if (i9 != 87) {
                        if (i9 != 90) {
                            Log.w("ConstraintSet", "Unknown attribute 0x");
                        } else {
                            constraint.motion.mQuantizeInterpolatorString = str;
                        }
                    }
                }
                for (int i11 = 0; i11 < this.mCountBoolean; i11++) {
                    int i12 = this.mTypeBoolean[i11];
                    boolean z = this.mValueBoolean[i11];
                    int i13 = ConstraintSet.$r8$clinit;
                    if (i12 == 44) {
                        constraint.transform.applyElevation = z;
                    } else if (i12 == 75) {
                        constraint.layout.mBarrierAllowsGoneWidgets = z;
                    } else if (i12 != 87) {
                        if (i12 == 80) {
                            constraint.layout.constrainedWidth = z;
                        } else if (i12 != 81) {
                            Log.w("ConstraintSet", "Unknown attribute 0x");
                        } else {
                            constraint.layout.constrainedHeight = z;
                        }
                    }
                }
            }

            final void add(int i, float f) {
                int i2 = this.mCountFloat;
                int[] iArr = this.mTypeFloat;
                if (i2 >= iArr.length) {
                    this.mTypeFloat = Arrays.copyOf(iArr, iArr.length * 2);
                    float[] fArr = this.mValueFloat;
                    this.mValueFloat = Arrays.copyOf(fArr, fArr.length * 2);
                }
                int[] iArr2 = this.mTypeFloat;
                int i3 = this.mCountFloat;
                iArr2[i3] = i;
                float[] fArr2 = this.mValueFloat;
                this.mCountFloat = i3 + 1;
                fArr2[i3] = f;
            }

            final void add(int i, String str) {
                int i2 = this.mCountString;
                int[] iArr = this.mTypeString;
                if (i2 >= iArr.length) {
                    this.mTypeString = Arrays.copyOf(iArr, iArr.length * 2);
                    String[] strArr = this.mValueString;
                    this.mValueString = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
                }
                int[] iArr2 = this.mTypeString;
                int i3 = this.mCountString;
                iArr2[i3] = i;
                String[] strArr2 = this.mValueString;
                this.mCountString = i3 + 1;
                strArr2[i3] = str;
            }

            final void add(int i, boolean z) {
                int i2 = this.mCountBoolean;
                int[] iArr = this.mTypeBoolean;
                if (i2 >= iArr.length) {
                    this.mTypeBoolean = Arrays.copyOf(iArr, iArr.length * 2);
                    boolean[] zArr = this.mValueBoolean;
                    this.mValueBoolean = Arrays.copyOf(zArr, zArr.length * 2);
                }
                int[] iArr2 = this.mTypeBoolean;
                int i3 = this.mCountBoolean;
                iArr2[i3] = i;
                boolean[] zArr2 = this.mValueBoolean;
                this.mCountBoolean = i3 + 1;
                zArr2[i3] = z;
            }
        }
    }

    public static class Layout {
        private static SparseIntArray sMapToConstant;
        public String mConstraintTag;
        public int mHeight;
        public String mReferenceIdString;
        public int[] mReferenceIds;
        public int mWidth;
        public boolean mIsGuideline = false;
        public boolean mApply = false;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public boolean guidelineUseRtl = true;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int topToTop = -1;
        public int topToBottom = -1;
        public int bottomToTop = -1;
        public int bottomToBottom = -1;
        public int baselineToBaseline = -1;
        public int baselineToTop = -1;
        public int baselineToBottom = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int endToStart = -1;
        public int endToEnd = -1;
        public float horizontalBias = 0.5f;
        public float verticalBias = 0.5f;
        public String dimensionRatio = null;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public float circleAngle = 0.0f;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int orientation = -1;
        public int leftMargin = 0;
        public int rightMargin = 0;
        public int topMargin = 0;
        public int bottomMargin = 0;
        public int endMargin = 0;
        public int startMargin = 0;
        public int baselineMargin = 0;
        public int goneLeftMargin = Integer.MIN_VALUE;
        public int goneTopMargin = Integer.MIN_VALUE;
        public int goneRightMargin = Integer.MIN_VALUE;
        public int goneBottomMargin = Integer.MIN_VALUE;
        public int goneEndMargin = Integer.MIN_VALUE;
        public int goneStartMargin = Integer.MIN_VALUE;
        public int goneBaselineMargin = Integer.MIN_VALUE;
        public float verticalWeight = -1.0f;
        public float horizontalWeight = -1.0f;
        public int horizontalChainStyle = 0;
        public int verticalChainStyle = 0;
        public int widthDefault = 0;
        public int heightDefault = 0;
        public int widthMax = 0;
        public int heightMax = 0;
        public int widthMin = 0;
        public int heightMin = 0;
        public float widthPercent = 1.0f;
        public float heightPercent = 1.0f;
        public int mBarrierDirection = -1;
        public int mBarrierMargin = 0;
        public int mHelperType = -1;
        public boolean constrainedWidth = false;
        public boolean constrainedHeight = false;
        public boolean mBarrierAllowsGoneWidgets = true;
        public int mWrapBehavior = 0;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sMapToConstant = sparseIntArray;
            sparseIntArray.append(43, 24);
            sMapToConstant.append(44, 25);
            sMapToConstant.append(46, 28);
            sMapToConstant.append(47, 29);
            sMapToConstant.append(52, 35);
            sMapToConstant.append(51, 34);
            sMapToConstant.append(24, 4);
            sMapToConstant.append(23, 3);
            sMapToConstant.append(19, 1);
            sMapToConstant.append(61, 6);
            sMapToConstant.append(62, 7);
            sMapToConstant.append(31, 17);
            sMapToConstant.append(32, 18);
            sMapToConstant.append(33, 19);
            sMapToConstant.append(15, 90);
            sMapToConstant.append(0, 26);
            sMapToConstant.append(48, 31);
            sMapToConstant.append(49, 32);
            sMapToConstant.append(30, 10);
            sMapToConstant.append(29, 9);
            sMapToConstant.append(66, 13);
            sMapToConstant.append(69, 16);
            sMapToConstant.append(67, 14);
            sMapToConstant.append(64, 11);
            sMapToConstant.append(68, 15);
            sMapToConstant.append(65, 12);
            sMapToConstant.append(55, 38);
            sMapToConstant.append(41, 37);
            sMapToConstant.append(40, 39);
            sMapToConstant.append(54, 40);
            sMapToConstant.append(39, 20);
            sMapToConstant.append(53, 36);
            sMapToConstant.append(28, 5);
            sMapToConstant.append(42, 91);
            sMapToConstant.append(50, 91);
            sMapToConstant.append(45, 91);
            sMapToConstant.append(22, 91);
            sMapToConstant.append(18, 91);
            sMapToConstant.append(3, 23);
            sMapToConstant.append(5, 27);
            sMapToConstant.append(7, 30);
            sMapToConstant.append(8, 8);
            sMapToConstant.append(4, 33);
            sMapToConstant.append(6, 2);
            sMapToConstant.append(1, 22);
            sMapToConstant.append(2, 21);
            sMapToConstant.append(56, 41);
            sMapToConstant.append(34, 42);
            sMapToConstant.append(17, 41);
            sMapToConstant.append(16, 42);
            sMapToConstant.append(71, 76);
            sMapToConstant.append(25, 61);
            sMapToConstant.append(27, 62);
            sMapToConstant.append(26, 63);
            sMapToConstant.append(60, 69);
            sMapToConstant.append(38, 70);
            sMapToConstant.append(12, 71);
            sMapToConstant.append(10, 72);
            sMapToConstant.append(11, 73);
            sMapToConstant.append(13, 74);
            sMapToConstant.append(9, 75);
            sMapToConstant.append(58, 84);
            sMapToConstant.append(59, 86);
            sMapToConstant.append(58, 83);
            sMapToConstant.append(37, 85);
            sMapToConstant.append(56, 87);
            sMapToConstant.append(34, 88);
            sMapToConstant.append(91, 89);
            sMapToConstant.append(15, 90);
        }

        public final void copyFrom(Layout layout) {
            this.mIsGuideline = layout.mIsGuideline;
            this.mWidth = layout.mWidth;
            this.mApply = layout.mApply;
            this.mHeight = layout.mHeight;
            this.guideBegin = layout.guideBegin;
            this.guideEnd = layout.guideEnd;
            this.guidePercent = layout.guidePercent;
            this.guidelineUseRtl = layout.guidelineUseRtl;
            this.leftToLeft = layout.leftToLeft;
            this.leftToRight = layout.leftToRight;
            this.rightToLeft = layout.rightToLeft;
            this.rightToRight = layout.rightToRight;
            this.topToTop = layout.topToTop;
            this.topToBottom = layout.topToBottom;
            this.bottomToTop = layout.bottomToTop;
            this.bottomToBottom = layout.bottomToBottom;
            this.baselineToBaseline = layout.baselineToBaseline;
            this.baselineToTop = layout.baselineToTop;
            this.baselineToBottom = layout.baselineToBottom;
            this.startToEnd = layout.startToEnd;
            this.startToStart = layout.startToStart;
            this.endToStart = layout.endToStart;
            this.endToEnd = layout.endToEnd;
            this.horizontalBias = layout.horizontalBias;
            this.verticalBias = layout.verticalBias;
            this.dimensionRatio = layout.dimensionRatio;
            this.circleConstraint = layout.circleConstraint;
            this.circleRadius = layout.circleRadius;
            this.circleAngle = layout.circleAngle;
            this.editorAbsoluteX = layout.editorAbsoluteX;
            this.editorAbsoluteY = layout.editorAbsoluteY;
            this.orientation = layout.orientation;
            this.leftMargin = layout.leftMargin;
            this.rightMargin = layout.rightMargin;
            this.topMargin = layout.topMargin;
            this.bottomMargin = layout.bottomMargin;
            this.endMargin = layout.endMargin;
            this.startMargin = layout.startMargin;
            this.baselineMargin = layout.baselineMargin;
            this.goneLeftMargin = layout.goneLeftMargin;
            this.goneTopMargin = layout.goneTopMargin;
            this.goneRightMargin = layout.goneRightMargin;
            this.goneBottomMargin = layout.goneBottomMargin;
            this.goneEndMargin = layout.goneEndMargin;
            this.goneStartMargin = layout.goneStartMargin;
            this.goneBaselineMargin = layout.goneBaselineMargin;
            this.verticalWeight = layout.verticalWeight;
            this.horizontalWeight = layout.horizontalWeight;
            this.horizontalChainStyle = layout.horizontalChainStyle;
            this.verticalChainStyle = layout.verticalChainStyle;
            this.widthDefault = layout.widthDefault;
            this.heightDefault = layout.heightDefault;
            this.widthMax = layout.widthMax;
            this.heightMax = layout.heightMax;
            this.widthMin = layout.widthMin;
            this.heightMin = layout.heightMin;
            this.widthPercent = layout.widthPercent;
            this.heightPercent = layout.heightPercent;
            this.mBarrierDirection = layout.mBarrierDirection;
            this.mBarrierMargin = layout.mBarrierMargin;
            this.mHelperType = layout.mHelperType;
            this.mConstraintTag = layout.mConstraintTag;
            int[] iArr = layout.mReferenceIds;
            if (iArr == null || layout.mReferenceIdString != null) {
                this.mReferenceIds = null;
            } else {
                this.mReferenceIds = Arrays.copyOf(iArr, iArr.length);
            }
            this.mReferenceIdString = layout.mReferenceIdString;
            this.constrainedWidth = layout.constrainedWidth;
            this.constrainedHeight = layout.constrainedHeight;
            this.mBarrierAllowsGoneWidgets = layout.mBarrierAllowsGoneWidgets;
            this.mWrapBehavior = layout.mWrapBehavior;
        }

        final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Layout);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                int i2 = sMapToConstant.get(index);
                switch (i2) {
                    case 1:
                        this.baselineToBaseline = ConstraintSet.lookupID(obtainStyledAttributes, index, this.baselineToBaseline);
                        break;
                    case 2:
                        this.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.bottomMargin);
                        break;
                    case 3:
                        this.bottomToBottom = ConstraintSet.lookupID(obtainStyledAttributes, index, this.bottomToBottom);
                        break;
                    case 4:
                        this.bottomToTop = ConstraintSet.lookupID(obtainStyledAttributes, index, this.bottomToTop);
                        break;
                    case 5:
                        this.dimensionRatio = obtainStyledAttributes.getString(index);
                        break;
                    case 6:
                        this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                        break;
                    case 7:
                        this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                        break;
                    case 8:
                        this.endMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.endMargin);
                        break;
                    case 9:
                        this.endToEnd = ConstraintSet.lookupID(obtainStyledAttributes, index, this.endToEnd);
                        break;
                    case 10:
                        this.endToStart = ConstraintSet.lookupID(obtainStyledAttributes, index, this.endToStart);
                        break;
                    case 11:
                        this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 12:
                        this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 13:
                        this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 14:
                        this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 15:
                        this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 16:
                        this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 17:
                        this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case 18:
                        this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 19:
                        this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 20:
                        this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 21:
                        this.mHeight = obtainStyledAttributes.getLayoutDimension(index, this.mHeight);
                        break;
                    case 22:
                        this.mWidth = obtainStyledAttributes.getLayoutDimension(index, this.mWidth);
                        break;
                    case 23:
                        this.leftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.leftMargin);
                        break;
                    case 24:
                        this.leftToLeft = ConstraintSet.lookupID(obtainStyledAttributes, index, this.leftToLeft);
                        break;
                    case 25:
                        this.leftToRight = ConstraintSet.lookupID(obtainStyledAttributes, index, this.leftToRight);
                        break;
                    case 26:
                        this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 27:
                        this.rightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.rightMargin);
                        break;
                    case 28:
                        this.rightToLeft = ConstraintSet.lookupID(obtainStyledAttributes, index, this.rightToLeft);
                        break;
                    case 29:
                        this.rightToRight = ConstraintSet.lookupID(obtainStyledAttributes, index, this.rightToRight);
                        break;
                    case 30:
                        this.startMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.startMargin);
                        break;
                    case 31:
                        this.startToEnd = ConstraintSet.lookupID(obtainStyledAttributes, index, this.startToEnd);
                        break;
                    case 32:
                        this.startToStart = ConstraintSet.lookupID(obtainStyledAttributes, index, this.startToStart);
                        break;
                    case 33:
                        this.topMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.topMargin);
                        break;
                    case 34:
                        this.topToBottom = ConstraintSet.lookupID(obtainStyledAttributes, index, this.topToBottom);
                        break;
                    case 35:
                        this.topToTop = ConstraintSet.lookupID(obtainStyledAttributes, index, this.topToTop);
                        break;
                    case 36:
                        this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 37:
                        this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                        break;
                    case 38:
                        this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                        break;
                    case 39:
                        this.horizontalChainStyle = obtainStyledAttributes.getInt(index, this.horizontalChainStyle);
                        break;
                    case 40:
                        this.verticalChainStyle = obtainStyledAttributes.getInt(index, this.verticalChainStyle);
                        break;
                    case 41:
                        ConstraintSet.parseDimensionConstraints(this, obtainStyledAttributes, index, 0);
                        break;
                    case 42:
                        ConstraintSet.parseDimensionConstraints(this, obtainStyledAttributes, index, 1);
                        break;
                    default:
                        switch (i2) {
                            case 61:
                                this.circleConstraint = ConstraintSet.lookupID(obtainStyledAttributes, index, this.circleConstraint);
                                break;
                            case 62:
                                this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                                break;
                            case 63:
                                this.circleAngle = obtainStyledAttributes.getFloat(index, this.circleAngle);
                                break;
                            default:
                                switch (i2) {
                                    case 69:
                                        this.widthPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                                        break;
                                    case 70:
                                        this.heightPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                                        break;
                                    case 71:
                                        Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                                        break;
                                    case 72:
                                        this.mBarrierDirection = obtainStyledAttributes.getInt(index, this.mBarrierDirection);
                                        break;
                                    case 73:
                                        this.mBarrierMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.mBarrierMargin);
                                        break;
                                    case 74:
                                        this.mReferenceIdString = obtainStyledAttributes.getString(index);
                                        break;
                                    case 75:
                                        this.mBarrierAllowsGoneWidgets = obtainStyledAttributes.getBoolean(index, this.mBarrierAllowsGoneWidgets);
                                        break;
                                    case 76:
                                        this.mWrapBehavior = obtainStyledAttributes.getInt(index, this.mWrapBehavior);
                                        break;
                                    case 77:
                                        this.baselineToTop = ConstraintSet.lookupID(obtainStyledAttributes, index, this.baselineToTop);
                                        break;
                                    case 78:
                                        this.baselineToBottom = ConstraintSet.lookupID(obtainStyledAttributes, index, this.baselineToBottom);
                                        break;
                                    case 79:
                                        this.goneBaselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBaselineMargin);
                                        break;
                                    case 80:
                                        this.baselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.baselineMargin);
                                        break;
                                    case 81:
                                        this.widthDefault = obtainStyledAttributes.getInt(index, this.widthDefault);
                                        break;
                                    case 82:
                                        this.heightDefault = obtainStyledAttributes.getInt(index, this.heightDefault);
                                        break;
                                    case 83:
                                        this.heightMax = obtainStyledAttributes.getDimensionPixelSize(index, this.heightMax);
                                        break;
                                    case 84:
                                        this.widthMax = obtainStyledAttributes.getDimensionPixelSize(index, this.widthMax);
                                        break;
                                    case 85:
                                        this.heightMin = obtainStyledAttributes.getDimensionPixelSize(index, this.heightMin);
                                        break;
                                    case 86:
                                        this.widthMin = obtainStyledAttributes.getDimensionPixelSize(index, this.widthMin);
                                        break;
                                    case 87:
                                        this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                                        break;
                                    case 88:
                                        this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                                        break;
                                    case 89:
                                        this.mConstraintTag = obtainStyledAttributes.getString(index);
                                        break;
                                    case 90:
                                        this.guidelineUseRtl = obtainStyledAttributes.getBoolean(index, this.guidelineUseRtl);
                                        break;
                                    case 91:
                                        Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + sMapToConstant.get(index));
                                        break;
                                    default:
                                        Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + sMapToConstant.get(index));
                                        break;
                                }
                        }
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public static class Motion {
        private static SparseIntArray sMapToConstant;
        public boolean mApply = false;
        public int mAnimateRelativeTo = -1;
        public int mAnimateCircleAngleTo = 0;
        public String mTransitionEasing = null;
        public int mPathMotionArc = -1;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public float mPathRotate = Float.NaN;
        public float mQuantizeMotionPhase = Float.NaN;
        public int mQuantizeMotionSteps = -1;
        public String mQuantizeInterpolatorString = null;
        public int mQuantizeInterpolatorType = -3;
        public int mQuantizeInterpolatorID = -1;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sMapToConstant = sparseIntArray;
            sparseIntArray.append(3, 1);
            sMapToConstant.append(5, 2);
            sMapToConstant.append(9, 3);
            sMapToConstant.append(2, 4);
            sMapToConstant.append(1, 5);
            sMapToConstant.append(0, 6);
            sMapToConstant.append(4, 7);
            sMapToConstant.append(8, 8);
            sMapToConstant.append(7, 9);
            sMapToConstant.append(6, 10);
        }

        public final void copyFrom(Motion motion) {
            this.mApply = motion.mApply;
            this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
            this.mTransitionEasing = motion.mTransitionEasing;
            this.mPathMotionArc = motion.mPathMotionArc;
            this.mDrawPath = motion.mDrawPath;
            this.mPathRotate = motion.mPathRotate;
            this.mMotionStagger = motion.mMotionStagger;
        }

        final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Motion);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (sMapToConstant.get(index)) {
                    case 1:
                        this.mPathRotate = obtainStyledAttributes.getFloat(index, this.mPathRotate);
                        break;
                    case 2:
                        this.mPathMotionArc = obtainStyledAttributes.getInt(index, this.mPathMotionArc);
                        break;
                    case 3:
                        if (obtainStyledAttributes.peekValue(index).type == 3) {
                            this.mTransitionEasing = obtainStyledAttributes.getString(index);
                            break;
                        } else {
                            this.mTransitionEasing = Easing.NAMED_EASING[obtainStyledAttributes.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        this.mDrawPath = obtainStyledAttributes.getInt(index, 0);
                        break;
                    case 5:
                        this.mAnimateRelativeTo = ConstraintSet.lookupID(obtainStyledAttributes, index, this.mAnimateRelativeTo);
                        break;
                    case 6:
                        this.mAnimateCircleAngleTo = obtainStyledAttributes.getInteger(index, this.mAnimateCircleAngleTo);
                        break;
                    case 7:
                        this.mMotionStagger = obtainStyledAttributes.getFloat(index, this.mMotionStagger);
                        break;
                    case 8:
                        this.mQuantizeMotionSteps = obtainStyledAttributes.getInteger(index, this.mQuantizeMotionSteps);
                        break;
                    case 9:
                        this.mQuantizeMotionPhase = obtainStyledAttributes.getFloat(index, this.mQuantizeMotionPhase);
                        break;
                    case 10:
                        int i2 = obtainStyledAttributes.peekValue(index).type;
                        if (i2 == 1) {
                            int resourceId = obtainStyledAttributes.getResourceId(index, -1);
                            this.mQuantizeInterpolatorID = resourceId;
                            if (resourceId != -1) {
                                this.mQuantizeInterpolatorType = -2;
                                break;
                            } else {
                                break;
                            }
                        } else if (i2 == 3) {
                            String string = obtainStyledAttributes.getString(index);
                            this.mQuantizeInterpolatorString = string;
                            if (string.indexOf("/") > 0) {
                                this.mQuantizeInterpolatorID = obtainStyledAttributes.getResourceId(index, -1);
                                this.mQuantizeInterpolatorType = -2;
                                break;
                            } else {
                                this.mQuantizeInterpolatorType = -1;
                                break;
                            }
                        } else {
                            this.mQuantizeInterpolatorType = obtainStyledAttributes.getInteger(index, this.mQuantizeInterpolatorID);
                            break;
                        }
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public static class PropertySet {
        public boolean mApply = false;
        public int visibility = 0;
        public int mVisibilityMode = 0;
        public float alpha = 1.0f;
        public float mProgress = Float.NaN;

        final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PropertySet);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 1) {
                    this.alpha = obtainStyledAttributes.getFloat(index, this.alpha);
                } else if (index == 0) {
                    this.visibility = obtainStyledAttributes.getInt(index, this.visibility);
                    this.visibility = ConstraintSet.VISIBILITY_FLAGS[this.visibility];
                } else if (index == 4) {
                    this.mVisibilityMode = obtainStyledAttributes.getInt(index, this.mVisibilityMode);
                } else if (index == 3) {
                    this.mProgress = obtainStyledAttributes.getFloat(index, this.mProgress);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public static class Transform {
        private static SparseIntArray sMapToConstant;
        public boolean mApply = false;
        public float rotation = 0.0f;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public float transformPivotX = Float.NaN;
        public float transformPivotY = Float.NaN;
        public int transformPivotTarget = -1;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;
        public boolean applyElevation = false;
        public float elevation = 0.0f;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sMapToConstant = sparseIntArray;
            sparseIntArray.append(6, 1);
            sMapToConstant.append(7, 2);
            sMapToConstant.append(8, 3);
            sMapToConstant.append(4, 4);
            sMapToConstant.append(5, 5);
            sMapToConstant.append(0, 6);
            sMapToConstant.append(1, 7);
            sMapToConstant.append(2, 8);
            sMapToConstant.append(3, 9);
            sMapToConstant.append(9, 10);
            sMapToConstant.append(10, 11);
            sMapToConstant.append(11, 12);
        }

        public final void copyFrom(Transform transform) {
            this.mApply = transform.mApply;
            this.rotation = transform.rotation;
            this.rotationX = transform.rotationX;
            this.rotationY = transform.rotationY;
            this.scaleX = transform.scaleX;
            this.scaleY = transform.scaleY;
            this.transformPivotX = transform.transformPivotX;
            this.transformPivotY = transform.transformPivotY;
            this.transformPivotTarget = transform.transformPivotTarget;
            this.translationX = transform.translationX;
            this.translationY = transform.translationY;
            this.translationZ = transform.translationZ;
            this.applyElevation = transform.applyElevation;
            this.elevation = transform.elevation;
        }

        final void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Transform);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (sMapToConstant.get(index)) {
                    case 1:
                        this.rotation = obtainStyledAttributes.getFloat(index, this.rotation);
                        break;
                    case 2:
                        this.rotationX = obtainStyledAttributes.getFloat(index, this.rotationX);
                        break;
                    case 3:
                        this.rotationY = obtainStyledAttributes.getFloat(index, this.rotationY);
                        break;
                    case 4:
                        this.scaleX = obtainStyledAttributes.getFloat(index, this.scaleX);
                        break;
                    case 5:
                        this.scaleY = obtainStyledAttributes.getFloat(index, this.scaleY);
                        break;
                    case 6:
                        this.transformPivotX = obtainStyledAttributes.getDimension(index, this.transformPivotX);
                        break;
                    case 7:
                        this.transformPivotY = obtainStyledAttributes.getDimension(index, this.transformPivotY);
                        break;
                    case 8:
                        this.translationX = obtainStyledAttributes.getDimension(index, this.translationX);
                        break;
                    case 9:
                        this.translationY = obtainStyledAttributes.getDimension(index, this.translationY);
                        break;
                    case 10:
                        this.translationZ = obtainStyledAttributes.getDimension(index, this.translationZ);
                        break;
                    case 11:
                        this.applyElevation = true;
                        this.elevation = obtainStyledAttributes.getDimension(index, this.elevation);
                        break;
                    case 12:
                        this.transformPivotTarget = ConstraintSet.lookupID(obtainStyledAttributes, index, this.transformPivotTarget);
                        break;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    static {
        sMapToConstant.append(82, 25);
        sMapToConstant.append(83, 26);
        sMapToConstant.append(85, 29);
        sMapToConstant.append(86, 30);
        sMapToConstant.append(92, 36);
        sMapToConstant.append(91, 35);
        sMapToConstant.append(63, 4);
        sMapToConstant.append(62, 3);
        sMapToConstant.append(58, 1);
        sMapToConstant.append(60, 91);
        sMapToConstant.append(59, 92);
        sMapToConstant.append(101, 6);
        sMapToConstant.append(102, 7);
        sMapToConstant.append(70, 17);
        sMapToConstant.append(71, 18);
        sMapToConstant.append(72, 19);
        sMapToConstant.append(54, 99);
        sMapToConstant.append(0, 27);
        sMapToConstant.append(87, 32);
        sMapToConstant.append(88, 33);
        sMapToConstant.append(69, 10);
        sMapToConstant.append(68, 9);
        sMapToConstant.append(106, 13);
        sMapToConstant.append(109, 16);
        sMapToConstant.append(107, 14);
        sMapToConstant.append(104, 11);
        sMapToConstant.append(108, 15);
        sMapToConstant.append(105, 12);
        sMapToConstant.append(95, 40);
        sMapToConstant.append(80, 39);
        sMapToConstant.append(79, 41);
        sMapToConstant.append(94, 42);
        sMapToConstant.append(78, 20);
        sMapToConstant.append(93, 37);
        sMapToConstant.append(67, 5);
        sMapToConstant.append(81, 87);
        sMapToConstant.append(90, 87);
        sMapToConstant.append(84, 87);
        sMapToConstant.append(61, 87);
        sMapToConstant.append(57, 87);
        sMapToConstant.append(5, 24);
        sMapToConstant.append(7, 28);
        sMapToConstant.append(23, 31);
        sMapToConstant.append(24, 8);
        sMapToConstant.append(6, 34);
        sMapToConstant.append(8, 2);
        sMapToConstant.append(3, 23);
        sMapToConstant.append(4, 21);
        sMapToConstant.append(96, 95);
        sMapToConstant.append(73, 96);
        sMapToConstant.append(2, 22);
        sMapToConstant.append(13, 43);
        sMapToConstant.append(26, 44);
        sMapToConstant.append(21, 45);
        sMapToConstant.append(22, 46);
        sMapToConstant.append(20, 60);
        sMapToConstant.append(18, 47);
        sMapToConstant.append(19, 48);
        sMapToConstant.append(14, 49);
        sMapToConstant.append(15, 50);
        sMapToConstant.append(16, 51);
        sMapToConstant.append(17, 52);
        sMapToConstant.append(25, 53);
        sMapToConstant.append(97, 54);
        sMapToConstant.append(74, 55);
        sMapToConstant.append(98, 56);
        sMapToConstant.append(75, 57);
        sMapToConstant.append(99, 58);
        sMapToConstant.append(76, 59);
        sMapToConstant.append(64, 61);
        sMapToConstant.append(66, 62);
        sMapToConstant.append(65, 63);
        sMapToConstant.append(28, 64);
        sMapToConstant.append(121, 65);
        sMapToConstant.append(35, 66);
        sMapToConstant.append(122, 67);
        sMapToConstant.append(113, 79);
        sMapToConstant.append(1, 38);
        sMapToConstant.append(112, 68);
        sMapToConstant.append(100, 69);
        sMapToConstant.append(77, 70);
        sMapToConstant.append(111, 97);
        sMapToConstant.append(32, 71);
        sMapToConstant.append(30, 72);
        sMapToConstant.append(31, 73);
        sMapToConstant.append(33, 74);
        sMapToConstant.append(29, 75);
        sMapToConstant.append(114, 76);
        sMapToConstant.append(89, 77);
        sMapToConstant.append(123, 78);
        sMapToConstant.append(56, 80);
        sMapToConstant.append(55, 81);
        sMapToConstant.append(116, 82);
        sMapToConstant.append(120, 83);
        sMapToConstant.append(119, 84);
        sMapToConstant.append(118, 85);
        sMapToConstant.append(117, 86);
        sOverrideMapToConstant.append(85, 6);
        sOverrideMapToConstant.append(85, 7);
        sOverrideMapToConstant.append(0, 27);
        sOverrideMapToConstant.append(89, 13);
        sOverrideMapToConstant.append(92, 16);
        sOverrideMapToConstant.append(90, 14);
        sOverrideMapToConstant.append(87, 11);
        sOverrideMapToConstant.append(91, 15);
        sOverrideMapToConstant.append(88, 12);
        sOverrideMapToConstant.append(78, 40);
        sOverrideMapToConstant.append(71, 39);
        sOverrideMapToConstant.append(70, 41);
        sOverrideMapToConstant.append(77, 42);
        sOverrideMapToConstant.append(69, 20);
        sOverrideMapToConstant.append(76, 37);
        sOverrideMapToConstant.append(60, 5);
        sOverrideMapToConstant.append(72, 87);
        sOverrideMapToConstant.append(75, 87);
        sOverrideMapToConstant.append(73, 87);
        sOverrideMapToConstant.append(57, 87);
        sOverrideMapToConstant.append(56, 87);
        sOverrideMapToConstant.append(5, 24);
        sOverrideMapToConstant.append(7, 28);
        sOverrideMapToConstant.append(23, 31);
        sOverrideMapToConstant.append(24, 8);
        sOverrideMapToConstant.append(6, 34);
        sOverrideMapToConstant.append(8, 2);
        sOverrideMapToConstant.append(3, 23);
        sOverrideMapToConstant.append(4, 21);
        sOverrideMapToConstant.append(79, 95);
        sOverrideMapToConstant.append(64, 96);
        sOverrideMapToConstant.append(2, 22);
        sOverrideMapToConstant.append(13, 43);
        sOverrideMapToConstant.append(26, 44);
        sOverrideMapToConstant.append(21, 45);
        sOverrideMapToConstant.append(22, 46);
        sOverrideMapToConstant.append(20, 60);
        sOverrideMapToConstant.append(18, 47);
        sOverrideMapToConstant.append(19, 48);
        sOverrideMapToConstant.append(14, 49);
        sOverrideMapToConstant.append(15, 50);
        sOverrideMapToConstant.append(16, 51);
        sOverrideMapToConstant.append(17, 52);
        sOverrideMapToConstant.append(25, 53);
        sOverrideMapToConstant.append(80, 54);
        sOverrideMapToConstant.append(65, 55);
        sOverrideMapToConstant.append(81, 56);
        sOverrideMapToConstant.append(66, 57);
        sOverrideMapToConstant.append(82, 58);
        sOverrideMapToConstant.append(67, 59);
        sOverrideMapToConstant.append(59, 62);
        sOverrideMapToConstant.append(58, 63);
        sOverrideMapToConstant.append(28, 64);
        sOverrideMapToConstant.append(105, 65);
        sOverrideMapToConstant.append(34, 66);
        sOverrideMapToConstant.append(106, 67);
        sOverrideMapToConstant.append(96, 79);
        sOverrideMapToConstant.append(1, 38);
        sOverrideMapToConstant.append(97, 98);
        sOverrideMapToConstant.append(95, 68);
        sOverrideMapToConstant.append(83, 69);
        sOverrideMapToConstant.append(68, 70);
        sOverrideMapToConstant.append(32, 71);
        sOverrideMapToConstant.append(30, 72);
        sOverrideMapToConstant.append(31, 73);
        sOverrideMapToConstant.append(33, 74);
        sOverrideMapToConstant.append(29, 75);
        sOverrideMapToConstant.append(98, 76);
        sOverrideMapToConstant.append(74, 77);
        sOverrideMapToConstant.append(107, 78);
        sOverrideMapToConstant.append(55, 80);
        sOverrideMapToConstant.append(54, 81);
        sOverrideMapToConstant.append(100, 82);
        sOverrideMapToConstant.append(104, 83);
        sOverrideMapToConstant.append(103, 84);
        sOverrideMapToConstant.append(102, 85);
        sOverrideMapToConstant.append(101, 86);
        sOverrideMapToConstant.append(94, 97);
    }

    public static Constraint buildDelta(Context context, XmlPullParser xmlPullParser) {
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        Constraint constraint = new Constraint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(asAttributeSet, R$styleable.ConstraintOverride);
        populateOverride(constraint, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        return constraint;
    }

    private static int[] convertReferenceString(Barrier barrier, String str) {
        int i;
        Object designInformation;
        String[] split = str.split(",");
        Context context = barrier.getContext();
        int[] iArr = new int[split.length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < split.length) {
            String trim = split[i2].trim();
            try {
                i = R$id.class.getField(trim).getInt(null);
            } catch (Exception unused) {
                i = 0;
            }
            if (i == 0) {
                i = context.getResources().getIdentifier(trim, "id", context.getPackageName());
            }
            if (i == 0 && barrier.isInEditMode() && (barrier.getParent() instanceof ConstraintLayout) && (designInformation = ((ConstraintLayout) barrier.getParent()).getDesignInformation(trim)) != null && (designInformation instanceof Integer)) {
                i = ((Integer) designInformation).intValue();
            }
            iArr[i3] = i;
            i2++;
            i3++;
        }
        return i3 != split.length ? Arrays.copyOf(iArr, i3) : iArr;
    }

    private static Constraint fillFromAttributeList(Context context, AttributeSet attributeSet, boolean z) {
        Constraint constraint = new Constraint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, z ? R$styleable.ConstraintOverride : R$styleable.Constraint);
        if (z) {
            populateOverride(constraint, obtainStyledAttributes);
        } else {
            int indexCount = obtainStyledAttributes.getIndexCount();
            int i = 0;
            while (true) {
                Layout layout = constraint.layout;
                if (i < indexCount) {
                    int index = obtainStyledAttributes.getIndex(i);
                    PropertySet propertySet = constraint.propertySet;
                    Transform transform = constraint.transform;
                    Motion motion = constraint.motion;
                    if (index != 1 && 23 != index && 24 != index) {
                        motion.mApply = true;
                        layout.mApply = true;
                        propertySet.mApply = true;
                        transform.mApply = true;
                    }
                    switch (sMapToConstant.get(index)) {
                        case 1:
                            layout.baselineToBaseline = lookupID(obtainStyledAttributes, index, layout.baselineToBaseline);
                            break;
                        case 2:
                            layout.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.bottomMargin);
                            break;
                        case 3:
                            layout.bottomToBottom = lookupID(obtainStyledAttributes, index, layout.bottomToBottom);
                            break;
                        case 4:
                            layout.bottomToTop = lookupID(obtainStyledAttributes, index, layout.bottomToTop);
                            break;
                        case 5:
                            layout.dimensionRatio = obtainStyledAttributes.getString(index);
                            break;
                        case 6:
                            layout.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, layout.editorAbsoluteX);
                            break;
                        case 7:
                            layout.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, layout.editorAbsoluteY);
                            break;
                        case 8:
                            layout.endMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.endMargin);
                            break;
                        case 9:
                            layout.endToEnd = lookupID(obtainStyledAttributes, index, layout.endToEnd);
                            break;
                        case 10:
                            layout.endToStart = lookupID(obtainStyledAttributes, index, layout.endToStart);
                            break;
                        case 11:
                            layout.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneBottomMargin);
                            break;
                        case 12:
                            layout.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneEndMargin);
                            break;
                        case 13:
                            layout.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneLeftMargin);
                            break;
                        case 14:
                            layout.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneRightMargin);
                            break;
                        case 15:
                            layout.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneStartMargin);
                            break;
                        case 16:
                            layout.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneTopMargin);
                            break;
                        case 17:
                            layout.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, layout.guideBegin);
                            break;
                        case 18:
                            layout.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, layout.guideEnd);
                            break;
                        case 19:
                            layout.guidePercent = obtainStyledAttributes.getFloat(index, layout.guidePercent);
                            break;
                        case 20:
                            layout.horizontalBias = obtainStyledAttributes.getFloat(index, layout.horizontalBias);
                            break;
                        case 21:
                            layout.mHeight = obtainStyledAttributes.getLayoutDimension(index, layout.mHeight);
                            break;
                        case 22:
                            propertySet.visibility = VISIBILITY_FLAGS[obtainStyledAttributes.getInt(index, propertySet.visibility)];
                            break;
                        case 23:
                            layout.mWidth = obtainStyledAttributes.getLayoutDimension(index, layout.mWidth);
                            break;
                        case 24:
                            layout.leftMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.leftMargin);
                            break;
                        case 25:
                            layout.leftToLeft = lookupID(obtainStyledAttributes, index, layout.leftToLeft);
                            break;
                        case 26:
                            layout.leftToRight = lookupID(obtainStyledAttributes, index, layout.leftToRight);
                            break;
                        case 27:
                            layout.orientation = obtainStyledAttributes.getInt(index, layout.orientation);
                            break;
                        case 28:
                            layout.rightMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.rightMargin);
                            break;
                        case 29:
                            layout.rightToLeft = lookupID(obtainStyledAttributes, index, layout.rightToLeft);
                            break;
                        case 30:
                            layout.rightToRight = lookupID(obtainStyledAttributes, index, layout.rightToRight);
                            break;
                        case 31:
                            layout.startMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.startMargin);
                            break;
                        case 32:
                            layout.startToEnd = lookupID(obtainStyledAttributes, index, layout.startToEnd);
                            break;
                        case 33:
                            layout.startToStart = lookupID(obtainStyledAttributes, index, layout.startToStart);
                            break;
                        case 34:
                            layout.topMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.topMargin);
                            break;
                        case 35:
                            layout.topToBottom = lookupID(obtainStyledAttributes, index, layout.topToBottom);
                            break;
                        case 36:
                            layout.topToTop = lookupID(obtainStyledAttributes, index, layout.topToTop);
                            break;
                        case 37:
                            layout.verticalBias = obtainStyledAttributes.getFloat(index, layout.verticalBias);
                            break;
                        case 38:
                            constraint.mViewId = obtainStyledAttributes.getResourceId(index, constraint.mViewId);
                            break;
                        case 39:
                            layout.horizontalWeight = obtainStyledAttributes.getFloat(index, layout.horizontalWeight);
                            break;
                        case 40:
                            layout.verticalWeight = obtainStyledAttributes.getFloat(index, layout.verticalWeight);
                            break;
                        case 41:
                            layout.horizontalChainStyle = obtainStyledAttributes.getInt(index, layout.horizontalChainStyle);
                            break;
                        case 42:
                            layout.verticalChainStyle = obtainStyledAttributes.getInt(index, layout.verticalChainStyle);
                            break;
                        case 43:
                            propertySet.alpha = obtainStyledAttributes.getFloat(index, propertySet.alpha);
                            break;
                        case 44:
                            transform.applyElevation = true;
                            transform.elevation = obtainStyledAttributes.getDimension(index, transform.elevation);
                            break;
                        case 45:
                            transform.rotationX = obtainStyledAttributes.getFloat(index, transform.rotationX);
                            break;
                        case 46:
                            transform.rotationY = obtainStyledAttributes.getFloat(index, transform.rotationY);
                            break;
                        case 47:
                            transform.scaleX = obtainStyledAttributes.getFloat(index, transform.scaleX);
                            break;
                        case 48:
                            transform.scaleY = obtainStyledAttributes.getFloat(index, transform.scaleY);
                            break;
                        case 49:
                            transform.transformPivotX = obtainStyledAttributes.getDimension(index, transform.transformPivotX);
                            break;
                        case 50:
                            transform.transformPivotY = obtainStyledAttributes.getDimension(index, transform.transformPivotY);
                            break;
                        case 51:
                            transform.translationX = obtainStyledAttributes.getDimension(index, transform.translationX);
                            break;
                        case 52:
                            transform.translationY = obtainStyledAttributes.getDimension(index, transform.translationY);
                            break;
                        case 53:
                            transform.translationZ = obtainStyledAttributes.getDimension(index, transform.translationZ);
                            break;
                        case 54:
                            layout.widthDefault = obtainStyledAttributes.getInt(index, layout.widthDefault);
                            break;
                        case 55:
                            layout.heightDefault = obtainStyledAttributes.getInt(index, layout.heightDefault);
                            break;
                        case 56:
                            layout.widthMax = obtainStyledAttributes.getDimensionPixelSize(index, layout.widthMax);
                            break;
                        case 57:
                            layout.heightMax = obtainStyledAttributes.getDimensionPixelSize(index, layout.heightMax);
                            break;
                        case 58:
                            layout.widthMin = obtainStyledAttributes.getDimensionPixelSize(index, layout.widthMin);
                            break;
                        case 59:
                            layout.heightMin = obtainStyledAttributes.getDimensionPixelSize(index, layout.heightMin);
                            break;
                        case 60:
                            transform.rotation = obtainStyledAttributes.getFloat(index, transform.rotation);
                            break;
                        case 61:
                            layout.circleConstraint = lookupID(obtainStyledAttributes, index, layout.circleConstraint);
                            break;
                        case 62:
                            layout.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, layout.circleRadius);
                            break;
                        case 63:
                            layout.circleAngle = obtainStyledAttributes.getFloat(index, layout.circleAngle);
                            break;
                        case 64:
                            motion.mAnimateRelativeTo = lookupID(obtainStyledAttributes, index, motion.mAnimateRelativeTo);
                            break;
                        case 65:
                            if (obtainStyledAttributes.peekValue(index).type != 3) {
                                motion.mTransitionEasing = Easing.NAMED_EASING[obtainStyledAttributes.getInteger(index, 0)];
                                break;
                            } else {
                                motion.mTransitionEasing = obtainStyledAttributes.getString(index);
                                break;
                            }
                        case 66:
                            motion.mDrawPath = obtainStyledAttributes.getInt(index, 0);
                            break;
                        case 67:
                            motion.mPathRotate = obtainStyledAttributes.getFloat(index, motion.mPathRotate);
                            break;
                        case 68:
                            propertySet.mProgress = obtainStyledAttributes.getFloat(index, propertySet.mProgress);
                            break;
                        case 69:
                            layout.widthPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                            break;
                        case 70:
                            layout.heightPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                            break;
                        case 71:
                            Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                            break;
                        case 72:
                            layout.mBarrierDirection = obtainStyledAttributes.getInt(index, layout.mBarrierDirection);
                            break;
                        case 73:
                            layout.mBarrierMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.mBarrierMargin);
                            break;
                        case 74:
                            layout.mReferenceIdString = obtainStyledAttributes.getString(index);
                            break;
                        case 75:
                            layout.mBarrierAllowsGoneWidgets = obtainStyledAttributes.getBoolean(index, layout.mBarrierAllowsGoneWidgets);
                            break;
                        case 76:
                            motion.mPathMotionArc = obtainStyledAttributes.getInt(index, motion.mPathMotionArc);
                            break;
                        case 77:
                            layout.mConstraintTag = obtainStyledAttributes.getString(index);
                            break;
                        case 78:
                            propertySet.mVisibilityMode = obtainStyledAttributes.getInt(index, propertySet.mVisibilityMode);
                            break;
                        case 79:
                            motion.mMotionStagger = obtainStyledAttributes.getFloat(index, motion.mMotionStagger);
                            break;
                        case 80:
                            layout.constrainedWidth = obtainStyledAttributes.getBoolean(index, layout.constrainedWidth);
                            break;
                        case 81:
                            layout.constrainedHeight = obtainStyledAttributes.getBoolean(index, layout.constrainedHeight);
                            break;
                        case 82:
                            motion.mAnimateCircleAngleTo = obtainStyledAttributes.getInteger(index, motion.mAnimateCircleAngleTo);
                            break;
                        case 83:
                            transform.transformPivotTarget = lookupID(obtainStyledAttributes, index, transform.transformPivotTarget);
                            break;
                        case 84:
                            motion.mQuantizeMotionSteps = obtainStyledAttributes.getInteger(index, motion.mQuantizeMotionSteps);
                            break;
                        case 85:
                            motion.mQuantizeMotionPhase = obtainStyledAttributes.getFloat(index, motion.mQuantizeMotionPhase);
                            break;
                        case 86:
                            int i2 = obtainStyledAttributes.peekValue(index).type;
                            if (i2 != 1) {
                                if (i2 != 3) {
                                    motion.mQuantizeInterpolatorType = obtainStyledAttributes.getInteger(index, motion.mQuantizeInterpolatorID);
                                    break;
                                } else {
                                    String string = obtainStyledAttributes.getString(index);
                                    motion.mQuantizeInterpolatorString = string;
                                    if (string.indexOf("/") <= 0) {
                                        motion.mQuantizeInterpolatorType = -1;
                                        break;
                                    } else {
                                        motion.mQuantizeInterpolatorID = obtainStyledAttributes.getResourceId(index, -1);
                                        motion.mQuantizeInterpolatorType = -2;
                                        break;
                                    }
                                }
                            } else {
                                int resourceId = obtainStyledAttributes.getResourceId(index, -1);
                                motion.mQuantizeInterpolatorID = resourceId;
                                if (resourceId == -1) {
                                    break;
                                } else {
                                    motion.mQuantizeInterpolatorType = -2;
                                    break;
                                }
                            }
                        case 87:
                            Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + sMapToConstant.get(index));
                            break;
                        case 88:
                        case 89:
                        case 90:
                        default:
                            Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + sMapToConstant.get(index));
                            break;
                        case 91:
                            layout.baselineToTop = lookupID(obtainStyledAttributes, index, layout.baselineToTop);
                            break;
                        case 92:
                            layout.baselineToBottom = lookupID(obtainStyledAttributes, index, layout.baselineToBottom);
                            break;
                        case 93:
                            layout.baselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.baselineMargin);
                            break;
                        case 94:
                            layout.goneBaselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, layout.goneBaselineMargin);
                            break;
                        case 95:
                            parseDimensionConstraints(layout, obtainStyledAttributes, index, 0);
                            break;
                        case 96:
                            parseDimensionConstraints(layout, obtainStyledAttributes, index, 1);
                            break;
                        case 97:
                            layout.mWrapBehavior = obtainStyledAttributes.getInt(index, layout.mWrapBehavior);
                            break;
                    }
                    i++;
                } else if (layout.mReferenceIdString != null) {
                    layout.mReferenceIds = null;
                }
            }
        }
        obtainStyledAttributes.recycle();
        return constraint;
    }

    private Constraint get(int i) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
            this.mConstraints.put(Integer.valueOf(i), new Constraint());
        }
        return this.mConstraints.get(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lookupID(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void parseDimensionConstraints(Object obj, TypedArray typedArray, int i, int i2) {
        int dimensionPixelSize;
        if (obj == null) {
            return;
        }
        int i3 = typedArray.peekValue(i).type;
        boolean z = true;
        int i4 = 0;
        if (i3 == 3) {
            String string = typedArray.getString(i);
            if (string == null) {
                return;
            }
            int indexOf = string.indexOf(61);
            int length = string.length();
            if (indexOf <= 0 || indexOf >= length - 1) {
                return;
            }
            String substring = string.substring(0, indexOf);
            String substring2 = string.substring(indexOf + 1);
            if (substring2.length() > 0) {
                String trim = substring.trim();
                String trim2 = substring2.trim();
                if ("ratio".equalsIgnoreCase(trim)) {
                    if (obj instanceof ConstraintLayout.LayoutParams) {
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) obj;
                        if (i2 == 0) {
                            ((ViewGroup.MarginLayoutParams) layoutParams).width = 0;
                        } else {
                            ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
                        }
                        parseDimensionRatioString(layoutParams, trim2);
                        return;
                    }
                    if (obj instanceof Layout) {
                        ((Layout) obj).dimensionRatio = trim2;
                        return;
                    } else {
                        if (obj instanceof Constraint.Delta) {
                            ((Constraint.Delta) obj).add(5, trim2);
                            return;
                        }
                        return;
                    }
                }
                try {
                    if ("weight".equalsIgnoreCase(trim)) {
                        float parseFloat = Float.parseFloat(trim2);
                        if (obj instanceof ConstraintLayout.LayoutParams) {
                            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) obj;
                            if (i2 == 0) {
                                ((ViewGroup.MarginLayoutParams) layoutParams2).width = 0;
                                layoutParams2.horizontalWeight = parseFloat;
                            } else {
                                ((ViewGroup.MarginLayoutParams) layoutParams2).height = 0;
                                layoutParams2.verticalWeight = parseFloat;
                            }
                        } else if (obj instanceof Layout) {
                            Layout layout = (Layout) obj;
                            if (i2 == 0) {
                                layout.mWidth = 0;
                                layout.horizontalWeight = parseFloat;
                            } else {
                                layout.mHeight = 0;
                                layout.verticalWeight = parseFloat;
                            }
                        } else if (obj instanceof Constraint.Delta) {
                            Constraint.Delta delta = (Constraint.Delta) obj;
                            if (i2 == 0) {
                                delta.add(23, 0);
                                delta.add(39, parseFloat);
                            } else {
                                delta.add(21, 0);
                                delta.add(40, parseFloat);
                            }
                        }
                    } else {
                        if (!"parent".equalsIgnoreCase(trim)) {
                            return;
                        }
                        float max = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(trim2)));
                        if (obj instanceof ConstraintLayout.LayoutParams) {
                            ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) obj;
                            if (i2 == 0) {
                                ((ViewGroup.MarginLayoutParams) layoutParams3).width = 0;
                                layoutParams3.matchConstraintPercentWidth = max;
                                layoutParams3.matchConstraintDefaultWidth = 2;
                            } else {
                                ((ViewGroup.MarginLayoutParams) layoutParams3).height = 0;
                                layoutParams3.matchConstraintPercentHeight = max;
                                layoutParams3.matchConstraintDefaultHeight = 2;
                            }
                        } else if (obj instanceof Layout) {
                            Layout layout2 = (Layout) obj;
                            if (i2 == 0) {
                                layout2.mWidth = 0;
                                layout2.widthPercent = max;
                                layout2.widthDefault = 2;
                            } else {
                                layout2.mHeight = 0;
                                layout2.heightPercent = max;
                                layout2.heightDefault = 2;
                            }
                        } else if (obj instanceof Constraint.Delta) {
                            Constraint.Delta delta2 = (Constraint.Delta) obj;
                            if (i2 == 0) {
                                delta2.add(23, 0);
                                delta2.add(54, 2);
                            } else {
                                delta2.add(21, 0);
                                delta2.add(55, 2);
                            }
                        }
                    }
                    return;
                } catch (NumberFormatException unused) {
                    return;
                }
            }
            return;
        }
        if (i3 != 5) {
            dimensionPixelSize = typedArray.getInt(i, 0);
            if (dimensionPixelSize == -4) {
                i4 = -2;
            } else if (dimensionPixelSize == -3 || (dimensionPixelSize != -2 && dimensionPixelSize != -1)) {
                z = false;
            }
            if (!(obj instanceof ConstraintLayout.LayoutParams)) {
                ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) obj;
                if (i2 == 0) {
                    ((ViewGroup.MarginLayoutParams) layoutParams4).width = i4;
                    layoutParams4.constrainedWidth = z;
                    return;
                } else {
                    ((ViewGroup.MarginLayoutParams) layoutParams4).height = i4;
                    layoutParams4.constrainedHeight = z;
                    return;
                }
            }
            if (obj instanceof Layout) {
                Layout layout3 = (Layout) obj;
                if (i2 == 0) {
                    layout3.mWidth = i4;
                    layout3.constrainedWidth = z;
                    return;
                } else {
                    layout3.mHeight = i4;
                    layout3.constrainedHeight = z;
                    return;
                }
            }
            if (obj instanceof Constraint.Delta) {
                Constraint.Delta delta3 = (Constraint.Delta) obj;
                if (i2 == 0) {
                    delta3.add(23, i4);
                    delta3.add(80, z);
                    return;
                } else {
                    delta3.add(21, i4);
                    delta3.add(81, z);
                    return;
                }
            }
            return;
        }
        dimensionPixelSize = typedArray.getDimensionPixelSize(i, 0);
        z = false;
        i4 = dimensionPixelSize;
        if (!(obj instanceof ConstraintLayout.LayoutParams)) {
        }
    }

    static void parseDimensionRatioString(ConstraintLayout.LayoutParams layoutParams, String str) {
        if (str != null) {
            int length = str.length();
            int indexOf = str.indexOf(44);
            char c = 65535;
            int i = 0;
            if (indexOf > 0 && indexOf < length - 1) {
                String substring = str.substring(0, indexOf);
                if (substring.equalsIgnoreCase("W")) {
                    c = 0;
                } else if (substring.equalsIgnoreCase("H")) {
                    c = 1;
                }
                i = indexOf + 1;
            }
            int indexOf2 = str.indexOf(58);
            try {
                if (indexOf2 < 0 || indexOf2 >= length - 1) {
                    String substring2 = str.substring(i);
                    if (substring2.length() > 0) {
                        Float.parseFloat(substring2);
                    }
                } else {
                    String substring3 = str.substring(i, indexOf2);
                    String substring4 = str.substring(indexOf2 + 1);
                    if (substring3.length() > 0 && substring4.length() > 0) {
                        float parseFloat = Float.parseFloat(substring3);
                        float parseFloat2 = Float.parseFloat(substring4);
                        if (parseFloat > 0.0f && parseFloat2 > 0.0f) {
                            if (c == 1) {
                                Math.abs(parseFloat2 / parseFloat);
                            } else {
                                Math.abs(parseFloat / parseFloat2);
                            }
                        }
                    }
                }
            } catch (NumberFormatException unused) {
            }
        }
        layoutParams.dimensionRatio = str;
    }

    private static void populateOverride(Constraint constraint, TypedArray typedArray) {
        boolean z;
        int indexCount = typedArray.getIndexCount();
        Constraint.Delta delta = new Constraint.Delta();
        constraint.mDelta = delta;
        Motion motion = constraint.motion;
        motion.mApply = false;
        Layout layout = constraint.layout;
        layout.mApply = false;
        PropertySet propertySet = constraint.propertySet;
        propertySet.mApply = false;
        Transform transform = constraint.transform;
        transform.mApply = false;
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (sOverrideMapToConstant.get(index)) {
                case 2:
                    z = false;
                    delta.add(2, typedArray.getDimensionPixelSize(index, layout.bottomMargin));
                    continue;
                case 3:
                case 4:
                case 9:
                case 10:
                case 25:
                case 26:
                case 29:
                case 30:
                case 32:
                case 33:
                case 35:
                case 36:
                case 61:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                default:
                    z = false;
                    Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + sMapToConstant.get(index));
                    continue;
                case 5:
                    z = false;
                    delta.add(5, typedArray.getString(index));
                    continue;
                case 6:
                    z = false;
                    delta.add(6, typedArray.getDimensionPixelOffset(index, layout.editorAbsoluteX));
                    continue;
                case 7:
                    z = false;
                    delta.add(7, typedArray.getDimensionPixelOffset(index, layout.editorAbsoluteY));
                    continue;
                case 8:
                    z = false;
                    delta.add(8, typedArray.getDimensionPixelSize(index, layout.endMargin));
                    continue;
                case 11:
                    z = false;
                    delta.add(11, typedArray.getDimensionPixelSize(index, layout.goneBottomMargin));
                    continue;
                case 12:
                    z = false;
                    delta.add(12, typedArray.getDimensionPixelSize(index, layout.goneEndMargin));
                    continue;
                case 13:
                    z = false;
                    delta.add(13, typedArray.getDimensionPixelSize(index, layout.goneLeftMargin));
                    continue;
                case 14:
                    z = false;
                    delta.add(14, typedArray.getDimensionPixelSize(index, layout.goneRightMargin));
                    continue;
                case 15:
                    z = false;
                    delta.add(15, typedArray.getDimensionPixelSize(index, layout.goneStartMargin));
                    continue;
                case 16:
                    z = false;
                    delta.add(16, typedArray.getDimensionPixelSize(index, layout.goneTopMargin));
                    continue;
                case 17:
                    z = false;
                    delta.add(17, typedArray.getDimensionPixelOffset(index, layout.guideBegin));
                    continue;
                case 18:
                    z = false;
                    delta.add(18, typedArray.getDimensionPixelOffset(index, layout.guideEnd));
                    continue;
                case 19:
                    z = false;
                    delta.add(19, typedArray.getFloat(index, layout.guidePercent));
                    continue;
                case 20:
                    z = false;
                    delta.add(20, typedArray.getFloat(index, layout.horizontalBias));
                    continue;
                case 21:
                    z = false;
                    delta.add(21, typedArray.getLayoutDimension(index, layout.mHeight));
                    continue;
                case 22:
                    z = false;
                    delta.add(22, VISIBILITY_FLAGS[typedArray.getInt(index, propertySet.visibility)]);
                    continue;
                case 23:
                    z = false;
                    delta.add(23, typedArray.getLayoutDimension(index, layout.mWidth));
                    continue;
                case 24:
                    z = false;
                    delta.add(24, typedArray.getDimensionPixelSize(index, layout.leftMargin));
                    continue;
                case 27:
                    z = false;
                    delta.add(27, typedArray.getInt(index, layout.orientation));
                    continue;
                case 28:
                    z = false;
                    delta.add(28, typedArray.getDimensionPixelSize(index, layout.rightMargin));
                    continue;
                case 31:
                    z = false;
                    delta.add(31, typedArray.getDimensionPixelSize(index, layout.startMargin));
                    continue;
                case 34:
                    z = false;
                    delta.add(34, typedArray.getDimensionPixelSize(index, layout.topMargin));
                    continue;
                case 37:
                    z = false;
                    delta.add(37, typedArray.getFloat(index, layout.verticalBias));
                    continue;
                case 38:
                    z = false;
                    int resourceId = typedArray.getResourceId(index, constraint.mViewId);
                    constraint.mViewId = resourceId;
                    delta.add(38, resourceId);
                    continue;
                case 39:
                    z = false;
                    delta.add(39, typedArray.getFloat(index, layout.horizontalWeight));
                    continue;
                case 40:
                    z = false;
                    delta.add(40, typedArray.getFloat(index, layout.verticalWeight));
                    continue;
                case 41:
                    z = false;
                    delta.add(41, typedArray.getInt(index, layout.horizontalChainStyle));
                    continue;
                case 42:
                    z = false;
                    delta.add(42, typedArray.getInt(index, layout.verticalChainStyle));
                    continue;
                case 43:
                    z = false;
                    delta.add(43, typedArray.getFloat(index, propertySet.alpha));
                    continue;
                case 44:
                    z = false;
                    delta.add(44, true);
                    delta.add(44, typedArray.getDimension(index, transform.elevation));
                    continue;
                case 45:
                    z = false;
                    delta.add(45, typedArray.getFloat(index, transform.rotationX));
                    continue;
                case 46:
                    z = false;
                    delta.add(46, typedArray.getFloat(index, transform.rotationY));
                    continue;
                case 47:
                    z = false;
                    delta.add(47, typedArray.getFloat(index, transform.scaleX));
                    continue;
                case 48:
                    z = false;
                    delta.add(48, typedArray.getFloat(index, transform.scaleY));
                    continue;
                case 49:
                    z = false;
                    delta.add(49, typedArray.getDimension(index, transform.transformPivotX));
                    continue;
                case 50:
                    z = false;
                    delta.add(50, typedArray.getDimension(index, transform.transformPivotY));
                    continue;
                case 51:
                    z = false;
                    delta.add(51, typedArray.getDimension(index, transform.translationX));
                    continue;
                case 52:
                    z = false;
                    delta.add(52, typedArray.getDimension(index, transform.translationY));
                    continue;
                case 53:
                    z = false;
                    delta.add(53, typedArray.getDimension(index, transform.translationZ));
                    continue;
                case 54:
                    z = false;
                    delta.add(54, typedArray.getInt(index, layout.widthDefault));
                    continue;
                case 55:
                    z = false;
                    delta.add(55, typedArray.getInt(index, layout.heightDefault));
                    continue;
                case 56:
                    z = false;
                    delta.add(56, typedArray.getDimensionPixelSize(index, layout.widthMax));
                    continue;
                case 57:
                    z = false;
                    delta.add(57, typedArray.getDimensionPixelSize(index, layout.heightMax));
                    continue;
                case 58:
                    z = false;
                    delta.add(58, typedArray.getDimensionPixelSize(index, layout.widthMin));
                    continue;
                case 59:
                    z = false;
                    delta.add(59, typedArray.getDimensionPixelSize(index, layout.heightMin));
                    continue;
                case 60:
                    z = false;
                    delta.add(60, typedArray.getFloat(index, transform.rotation));
                    continue;
                case 62:
                    z = false;
                    delta.add(62, typedArray.getDimensionPixelSize(index, layout.circleRadius));
                    continue;
                case 63:
                    z = false;
                    delta.add(63, typedArray.getFloat(index, layout.circleAngle));
                    continue;
                case 64:
                    z = false;
                    delta.add(64, lookupID(typedArray, index, motion.mAnimateRelativeTo));
                    continue;
                case 65:
                    z = false;
                    if (typedArray.peekValue(index).type != 3) {
                        delta.add(65, Easing.NAMED_EASING[typedArray.getInteger(index, 0)]);
                        break;
                    } else {
                        delta.add(65, typedArray.getString(index));
                        continue;
                    }
                case 66:
                    z = false;
                    delta.add(66, typedArray.getInt(index, 0));
                    continue;
                case 67:
                    delta.add(67, typedArray.getFloat(index, motion.mPathRotate));
                    break;
                case 68:
                    delta.add(68, typedArray.getFloat(index, propertySet.mProgress));
                    break;
                case 69:
                    delta.add(69, typedArray.getFloat(index, 1.0f));
                    break;
                case 70:
                    delta.add(70, typedArray.getFloat(index, 1.0f));
                    break;
                case 71:
                    Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    delta.add(72, typedArray.getInt(index, layout.mBarrierDirection));
                    break;
                case 73:
                    delta.add(73, typedArray.getDimensionPixelSize(index, layout.mBarrierMargin));
                    break;
                case 74:
                    delta.add(74, typedArray.getString(index));
                    break;
                case 75:
                    delta.add(75, typedArray.getBoolean(index, layout.mBarrierAllowsGoneWidgets));
                    break;
                case 76:
                    delta.add(76, typedArray.getInt(index, motion.mPathMotionArc));
                    break;
                case 77:
                    delta.add(77, typedArray.getString(index));
                    break;
                case 78:
                    delta.add(78, typedArray.getInt(index, propertySet.mVisibilityMode));
                    break;
                case 79:
                    delta.add(79, typedArray.getFloat(index, motion.mMotionStagger));
                    break;
                case 80:
                    delta.add(80, typedArray.getBoolean(index, layout.constrainedWidth));
                    break;
                case 81:
                    delta.add(81, typedArray.getBoolean(index, layout.constrainedHeight));
                    break;
                case 82:
                    delta.add(82, typedArray.getInteger(index, motion.mAnimateCircleAngleTo));
                    break;
                case 83:
                    delta.add(83, lookupID(typedArray, index, transform.transformPivotTarget));
                    break;
                case 84:
                    delta.add(84, typedArray.getInteger(index, motion.mQuantizeMotionSteps));
                    break;
                case 85:
                    delta.add(85, typedArray.getFloat(index, motion.mQuantizeMotionPhase));
                    break;
                case 86:
                    int i2 = typedArray.peekValue(index).type;
                    if (i2 == 1) {
                        int resourceId2 = typedArray.getResourceId(index, -1);
                        motion.mQuantizeInterpolatorID = resourceId2;
                        delta.add(89, resourceId2);
                        if (motion.mQuantizeInterpolatorID != -1) {
                            motion.mQuantizeInterpolatorType = -2;
                            delta.add(88, -2);
                            break;
                        }
                    } else if (i2 == 3) {
                        String string = typedArray.getString(index);
                        motion.mQuantizeInterpolatorString = string;
                        delta.add(90, string);
                        if (motion.mQuantizeInterpolatorString.indexOf("/") > 0) {
                            int resourceId3 = typedArray.getResourceId(index, -1);
                            motion.mQuantizeInterpolatorID = resourceId3;
                            delta.add(89, resourceId3);
                            motion.mQuantizeInterpolatorType = -2;
                            delta.add(88, -2);
                            break;
                        } else {
                            motion.mQuantizeInterpolatorType = -1;
                            delta.add(88, -1);
                            break;
                        }
                    } else {
                        int integer = typedArray.getInteger(index, motion.mQuantizeInterpolatorID);
                        motion.mQuantizeInterpolatorType = integer;
                        delta.add(88, integer);
                        break;
                    }
                    break;
                case 87:
                    Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + sMapToConstant.get(index));
                    break;
                case 93:
                    delta.add(93, typedArray.getDimensionPixelSize(index, layout.baselineMargin));
                    break;
                case 94:
                    delta.add(94, typedArray.getDimensionPixelSize(index, layout.goneBaselineMargin));
                    break;
                case 95:
                    parseDimensionConstraints(delta, typedArray, index, 0);
                    z = false;
                    continue;
                case 96:
                    parseDimensionConstraints(delta, typedArray, index, 1);
                    break;
                case 97:
                    delta.add(97, typedArray.getInt(index, layout.mWrapBehavior));
                    break;
                case 98:
                    if (MotionLayout.IS_IN_EDIT_MODE) {
                        int resourceId4 = typedArray.getResourceId(index, constraint.mViewId);
                        constraint.mViewId = resourceId4;
                        if (resourceId4 == -1) {
                            constraint.mTargetString = typedArray.getString(index);
                            break;
                        }
                    } else if (typedArray.peekValue(index).type == 3) {
                        constraint.mTargetString = typedArray.getString(index);
                        break;
                    } else {
                        constraint.mViewId = typedArray.getResourceId(index, constraint.mViewId);
                        break;
                    }
                    break;
                case 99:
                    delta.add(99, typedArray.getBoolean(index, layout.guidelineUseRtl));
                    break;
            }
            z = false;
        }
    }

    public final void applyCustomAttributes(ConstraintLayout constraintLayout) {
        Constraint constraint;
        int childCount = constraintLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                Log.w("ConstraintSet", "id unknown " + Debug.getName(childAt));
            } else {
                if (this.mForceId && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null) {
                    ConstraintAttribute.setAttributes(childAt, constraint.mCustomConstraints);
                }
            }
        }
    }

    public final void applyDeltaFrom(ConstraintSet constraintSet) {
        for (Constraint constraint : constraintSet.mConstraints.values()) {
            if (constraint.mDelta != null) {
                if (constraint.mTargetString == null) {
                    constraint.mDelta.applyDelta(getConstraint(constraint.mViewId));
                } else {
                    Iterator<Integer> it = this.mConstraints.keySet().iterator();
                    while (it.hasNext()) {
                        Constraint constraint2 = getConstraint(it.next().intValue());
                        String str = constraint2.layout.mConstraintTag;
                        if (str != null && constraint.mTargetString.matches(str)) {
                            constraint.mDelta.applyDelta(constraint2);
                            constraint2.mCustomConstraints.putAll((HashMap) constraint.mCustomConstraints.clone());
                        }
                    }
                }
            }
        }
    }

    public final void applyTo(ConstraintLayout constraintLayout) {
        applyToInternal(constraintLayout);
        constraintLayout.setConstraintSet(null);
        constraintLayout.requestLayout();
    }

    public final void applyToHelper(ConstraintHelper constraintHelper, ConstraintWidget constraintWidget, Constraints.LayoutParams layoutParams, SparseArray sparseArray) {
        Constraint constraint;
        int id = constraintHelper.getId();
        if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null && (constraintWidget instanceof HelperWidget)) {
            constraintHelper.loadParameters(constraint, (HelperWidget) constraintWidget, layoutParams, sparseArray);
        }
    }

    final void applyToInternal(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.mConstraints.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                Log.w("ConstraintSet", "id unknown " + Debug.getName(childAt));
            } else {
                if (this.mForceId && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (id != -1) {
                    if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                        hashSet.remove(Integer.valueOf(id));
                        Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                        if (constraint != null) {
                            if (childAt instanceof Barrier) {
                                Layout layout = constraint.layout;
                                layout.mHelperType = 1;
                                Barrier barrier = (Barrier) childAt;
                                barrier.setId(id);
                                barrier.setType(layout.mBarrierDirection);
                                barrier.setMargin(layout.mBarrierMargin);
                                barrier.setAllowsGoneWidget(layout.mBarrierAllowsGoneWidgets);
                                int[] iArr = layout.mReferenceIds;
                                if (iArr != null) {
                                    barrier.setReferencedIds(iArr);
                                } else {
                                    String str = layout.mReferenceIdString;
                                    if (str != null) {
                                        int[] convertReferenceString = convertReferenceString(barrier, str);
                                        layout.mReferenceIds = convertReferenceString;
                                        barrier.setReferencedIds(convertReferenceString);
                                    }
                                }
                            }
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                            layoutParams.validate();
                            constraint.applyTo(layoutParams);
                            ConstraintAttribute.setAttributes(childAt, constraint.mCustomConstraints);
                            childAt.setLayoutParams(layoutParams);
                            PropertySet propertySet = constraint.propertySet;
                            if (propertySet.mVisibilityMode == 0) {
                                childAt.setVisibility(propertySet.visibility);
                            }
                            childAt.setAlpha(propertySet.alpha);
                            Transform transform = constraint.transform;
                            childAt.setRotation(transform.rotation);
                            childAt.setRotationX(transform.rotationX);
                            childAt.setRotationY(transform.rotationY);
                            childAt.setScaleX(transform.scaleX);
                            childAt.setScaleY(transform.scaleY);
                            if (transform.transformPivotTarget != -1) {
                                if (((View) childAt.getParent()).findViewById(transform.transformPivotTarget) != null) {
                                    float bottom = (r6.getBottom() + r6.getTop()) / 2.0f;
                                    float right = (r6.getRight() + r6.getLeft()) / 2.0f;
                                    if (childAt.getRight() - childAt.getLeft() > 0 && childAt.getBottom() - childAt.getTop() > 0) {
                                        childAt.setPivotX(right - childAt.getLeft());
                                        childAt.setPivotY(bottom - childAt.getTop());
                                    }
                                }
                            } else {
                                if (!Float.isNaN(transform.transformPivotX)) {
                                    childAt.setPivotX(transform.transformPivotX);
                                }
                                if (!Float.isNaN(transform.transformPivotY)) {
                                    childAt.setPivotY(transform.transformPivotY);
                                }
                            }
                            childAt.setTranslationX(transform.translationX);
                            childAt.setTranslationY(transform.translationY);
                            childAt.setTranslationZ(transform.translationZ);
                            if (transform.applyElevation) {
                                childAt.setElevation(transform.elevation);
                            }
                        }
                    } else {
                        Log.v("ConstraintSet", "WARNING NO CONSTRAINTS for view " + id);
                    }
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = this.mConstraints.get(num);
            if (constraint2 != null) {
                Layout layout2 = constraint2.layout;
                if (layout2.mHelperType == 1) {
                    Barrier barrier2 = new Barrier(constraintLayout.getContext());
                    barrier2.setId(num.intValue());
                    int[] iArr2 = layout2.mReferenceIds;
                    if (iArr2 != null) {
                        barrier2.setReferencedIds(iArr2);
                    } else {
                        String str2 = layout2.mReferenceIdString;
                        if (str2 != null) {
                            int[] convertReferenceString2 = convertReferenceString(barrier2, str2);
                            layout2.mReferenceIds = convertReferenceString2;
                            barrier2.setReferencedIds(convertReferenceString2);
                        }
                    }
                    barrier2.setType(layout2.mBarrierDirection);
                    barrier2.setMargin(layout2.mBarrierMargin);
                    int i2 = ConstraintLayout.$r8$clinit;
                    ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(-2, -2);
                    barrier2.validateParams();
                    constraint2.applyTo(layoutParams2);
                    constraintLayout.addView(barrier2, layoutParams2);
                }
                if (layout2.mIsGuideline) {
                    View guideline = new Guideline(constraintLayout.getContext());
                    guideline.setId(num.intValue());
                    int i3 = ConstraintLayout.$r8$clinit;
                    ConstraintLayout.LayoutParams layoutParams3 = new ConstraintLayout.LayoutParams(-2, -2);
                    constraint2.applyTo(layoutParams3);
                    constraintLayout.addView(guideline, layoutParams3);
                }
            }
        }
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt2 = constraintLayout.getChildAt(i4);
            if (childAt2 instanceof ConstraintHelper) {
                ((ConstraintHelper) childAt2).applyLayoutFeaturesInConstraintSet(constraintLayout);
            }
        }
    }

    public final void applyToLayoutParams(int i, Constraints.LayoutParams layoutParams) {
        Constraint constraint;
        if (!this.mConstraints.containsKey(Integer.valueOf(i)) || (constraint = this.mConstraints.get(Integer.valueOf(i))) == null) {
            return;
        }
        constraint.applyTo(layoutParams);
    }

    public final void clone(ConstraintSet constraintSet) {
        this.mConstraints.clear();
        for (Integer num : constraintSet.mConstraints.keySet()) {
            Constraint constraint = constraintSet.mConstraints.get(num);
            if (constraint != null) {
                this.mConstraints.put(num, constraint.m17clone());
            }
        }
    }

    public final Constraint getConstraint(int i) {
        if (this.mConstraints.containsKey(Integer.valueOf(i))) {
            return this.mConstraints.get(Integer.valueOf(i));
        }
        return null;
    }

    public final int getHeight(int i) {
        return get(i).layout.mHeight;
    }

    public final int[] getKnownIds() {
        Integer[] numArr = (Integer[]) this.mConstraints.keySet().toArray(new Integer[0]);
        int length = numArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = numArr[i].intValue();
        }
        return iArr;
    }

    public final Constraint getParameters(int i) {
        return get(i);
    }

    public final int getVisibility(int i) {
        return get(i).propertySet.visibility;
    }

    public final int getVisibilityMode(int i) {
        return get(i).propertySet.mVisibilityMode;
    }

    public final int getWidth(int i) {
        return get(i).layout.mWidth;
    }

    public final void load(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 2) {
                    String name = xml.getName();
                    Constraint fillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xml), false);
                    if (name.equalsIgnoreCase("Guideline")) {
                        fillFromAttributeList.layout.mIsGuideline = true;
                    }
                    this.mConstraints.put(Integer.valueOf(fillFromAttributeList.mViewId), fillFromAttributeList);
                }
            }
        } catch (IOException e) {
            Log.e("ConstraintSet", "Error parsing resource: " + i, e);
        } catch (XmlPullParserException e2) {
            Log.e("ConstraintSet", "Error parsing resource: " + i, e2);
        }
    }

    public final void readFallback(ConstraintSet constraintSet) {
        for (Integer num : constraintSet.mConstraints.keySet()) {
            int intValue = num.intValue();
            Constraint constraint = constraintSet.mConstraints.get(num);
            if (!this.mConstraints.containsKey(Integer.valueOf(intValue))) {
                this.mConstraints.put(Integer.valueOf(intValue), new Constraint());
            }
            Constraint constraint2 = this.mConstraints.get(Integer.valueOf(intValue));
            if (constraint2 != null) {
                Layout layout = constraint2.layout;
                if (!layout.mApply) {
                    layout.copyFrom(constraint.layout);
                }
                PropertySet propertySet = constraint2.propertySet;
                if (!propertySet.mApply) {
                    PropertySet propertySet2 = constraint.propertySet;
                    propertySet.mApply = propertySet2.mApply;
                    propertySet.visibility = propertySet2.visibility;
                    propertySet.alpha = propertySet2.alpha;
                    propertySet.mProgress = propertySet2.mProgress;
                    propertySet.mVisibilityMode = propertySet2.mVisibilityMode;
                }
                Transform transform = constraint2.transform;
                if (!transform.mApply) {
                    transform.copyFrom(constraint.transform);
                }
                Motion motion = constraint2.motion;
                if (!motion.mApply) {
                    motion.copyFrom(constraint.motion);
                }
                for (String str : constraint.mCustomConstraints.keySet()) {
                    if (!constraint2.mCustomConstraints.containsKey(str)) {
                        constraint2.mCustomConstraints.put(str, constraint.mCustomConstraints.get(str));
                    }
                }
            }
        }
    }

    public final void setForceId() {
        this.mForceId = false;
    }

    public final void setStateLabels(String str) {
        this.mMatchLabels = str.split(",");
        int i = 0;
        while (true) {
            String[] strArr = this.mMatchLabels;
            if (i >= strArr.length) {
                return;
            }
            strArr[i] = strArr[i].trim();
            i++;
        }
    }

    public final void clone(ConstraintLayout constraintLayout) {
        int i;
        int i2;
        ConstraintSet constraintSet = this;
        int childCount = constraintLayout.getChildCount();
        constraintSet.mConstraints.clear();
        int i3 = 0;
        while (i3 < childCount) {
            View childAt = constraintLayout.getChildAt(i3);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (constraintSet.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!constraintSet.mConstraints.containsKey(Integer.valueOf(id))) {
                constraintSet.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = constraintSet.mConstraints.get(Integer.valueOf(id));
            if (constraint == null) {
                i = childCount;
            } else {
                HashMap<String, ConstraintAttribute> hashMap = constraintSet.mSavedAttributes;
                HashMap<String, ConstraintAttribute> hashMap2 = new HashMap<>();
                Class<?> cls = childAt.getClass();
                for (String str : hashMap.keySet()) {
                    ConstraintAttribute constraintAttribute = hashMap.get(str);
                    try {
                    } catch (IllegalAccessException e) {
                        e = e;
                        i2 = childCount;
                    } catch (NoSuchMethodException e2) {
                        e = e2;
                        i2 = childCount;
                    } catch (InvocationTargetException e3) {
                        e = e3;
                        i2 = childCount;
                    }
                    if (str.equals("BackgroundColor")) {
                        i2 = childCount;
                        try {
                            hashMap2.put(str, new ConstraintAttribute(constraintAttribute, Integer.valueOf(((ColorDrawable) childAt.getBackground()).getColor())));
                        } catch (IllegalAccessException e4) {
                            e = e4;
                            Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName(), e);
                            childCount = i2;
                        } catch (NoSuchMethodException e5) {
                            e = e5;
                            Log.e("TransitionLayout", cls.getName() + " must have a method " + str, e);
                            childCount = i2;
                        } catch (InvocationTargetException e6) {
                            e = e6;
                            Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName(), e);
                            childCount = i2;
                        }
                        childCount = i2;
                    } else {
                        i2 = childCount;
                        try {
                            hashMap2.put(str, new ConstraintAttribute(constraintAttribute, cls.getMethod("getMap" + str, new Class[0]).invoke(childAt, new Object[0])));
                        } catch (IllegalAccessException e7) {
                            e = e7;
                            Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName(), e);
                            childCount = i2;
                        } catch (NoSuchMethodException e8) {
                            e = e8;
                            Log.e("TransitionLayout", cls.getName() + " must have a method " + str, e);
                            childCount = i2;
                        } catch (InvocationTargetException e9) {
                            e = e9;
                            Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName(), e);
                            childCount = i2;
                        }
                        childCount = i2;
                    }
                }
                i = childCount;
                constraint.mCustomConstraints = hashMap2;
                constraint.fillFrom(id, layoutParams);
                int visibility = childAt.getVisibility();
                PropertySet propertySet = constraint.propertySet;
                propertySet.visibility = visibility;
                propertySet.alpha = childAt.getAlpha();
                float rotation = childAt.getRotation();
                Transform transform = constraint.transform;
                transform.rotation = rotation;
                transform.rotationX = childAt.getRotationX();
                transform.rotationY = childAt.getRotationY();
                transform.scaleX = childAt.getScaleX();
                transform.scaleY = childAt.getScaleY();
                float pivotX = childAt.getPivotX();
                float pivotY = childAt.getPivotY();
                if (pivotX != 0.0d || pivotY != 0.0d) {
                    transform.transformPivotX = pivotX;
                    transform.transformPivotY = pivotY;
                }
                transform.translationX = childAt.getTranslationX();
                transform.translationY = childAt.getTranslationY();
                transform.translationZ = childAt.getTranslationZ();
                if (transform.applyElevation) {
                    transform.elevation = childAt.getElevation();
                }
                if (childAt instanceof Barrier) {
                    Barrier barrier = (Barrier) childAt;
                    boolean allowsGoneWidget = barrier.getAllowsGoneWidget();
                    Layout layout = constraint.layout;
                    layout.mBarrierAllowsGoneWidgets = allowsGoneWidget;
                    layout.mReferenceIds = barrier.getReferencedIds();
                    layout.mBarrierDirection = barrier.getType();
                    layout.mBarrierMargin = barrier.getMargin();
                }
            }
            i3++;
            constraintSet = this;
            childCount = i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x01cf, code lost:
    
        continue;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void load(Context context, XmlPullParser xmlPullParser) {
        Constraint fillFromAttributeList;
        try {
            int eventType = xmlPullParser.getEventType();
            Constraint constraint = null;
            while (eventType != 1) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        switch (xmlPullParser.getName()) {
                            case "Constraint":
                                fillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser), false);
                                break;
                            case "ConstraintOverride":
                                fillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser), true);
                                break;
                            case "Guideline":
                                fillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser), false);
                                Layout layout = fillFromAttributeList.layout;
                                layout.mIsGuideline = true;
                                layout.mApply = true;
                                break;
                            case "Barrier":
                                fillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser), false);
                                fillFromAttributeList.layout.mHelperType = 1;
                                break;
                            case "PropertySet":
                                if (constraint != null) {
                                    constraint.propertySet.fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser));
                                    continue;
                                } else {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                }
                            case "Transform":
                                if (constraint != null) {
                                    constraint.transform.fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser));
                                    continue;
                                } else {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                }
                            case "Layout":
                                if (constraint != null) {
                                    constraint.layout.fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser));
                                    continue;
                                } else {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                }
                            case "Motion":
                                if (constraint != null) {
                                    constraint.motion.fillFromAttributeList(context, Xml.asAttributeSet(xmlPullParser));
                                    continue;
                                } else {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                }
                            case "CustomAttribute":
                            case "CustomMethod":
                                if (constraint != null) {
                                    ConstraintAttribute.parse(context, xmlPullParser, constraint.mCustomConstraints);
                                    continue;
                                } else {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                }
                        }
                        constraint = fillFromAttributeList;
                    } else if (eventType == 3) {
                        String lowerCase = xmlPullParser.getName().toLowerCase(Locale.ROOT);
                        switch (lowerCase.hashCode()) {
                            case -2075718416:
                                if (lowerCase.equals("guideline")) {
                                    break;
                                }
                                break;
                            case -190376483:
                                if (lowerCase.equals("constraint")) {
                                    break;
                                }
                                break;
                            case 426575017:
                                if (lowerCase.equals("constraintoverride")) {
                                    break;
                                }
                                break;
                            case 2146106725:
                                if (lowerCase.equals("constraintset")) {
                                    break;
                                }
                                break;
                        }
                        if (r6 == 0) {
                            return;
                        }
                        if (r6 == 1 || r6 == 2 || r6 == 3) {
                            this.mConstraints.put(Integer.valueOf(constraint.mViewId), constraint);
                            constraint = null;
                        }
                    }
                } else {
                    xmlPullParser.getName();
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e) {
            Log.e("ConstraintSet", "Error parsing XML resource", e);
        } catch (XmlPullParserException e2) {
            Log.e("ConstraintSet", "Error parsing XML resource", e2);
        }
    }

    public final void readFallback(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (constraint != null) {
                Layout layout = constraint.layout;
                if (!layout.mApply) {
                    constraint.fillFrom(id, layoutParams);
                    if (childAt instanceof ConstraintHelper) {
                        layout.mReferenceIds = ((ConstraintHelper) childAt).getReferencedIds();
                        if (childAt instanceof Barrier) {
                            Barrier barrier = (Barrier) childAt;
                            layout.mBarrierAllowsGoneWidgets = barrier.getAllowsGoneWidget();
                            layout.mBarrierDirection = barrier.getType();
                            layout.mBarrierMargin = barrier.getMargin();
                        }
                    }
                    layout.mApply = true;
                }
                PropertySet propertySet = constraint.propertySet;
                if (!propertySet.mApply) {
                    propertySet.visibility = childAt.getVisibility();
                    propertySet.alpha = childAt.getAlpha();
                    propertySet.mApply = true;
                }
                Transform transform = constraint.transform;
                if (!transform.mApply) {
                    transform.mApply = true;
                    transform.rotation = childAt.getRotation();
                    transform.rotationX = childAt.getRotationX();
                    transform.rotationY = childAt.getRotationY();
                    transform.scaleX = childAt.getScaleX();
                    transform.scaleY = childAt.getScaleY();
                    float pivotX = childAt.getPivotX();
                    float pivotY = childAt.getPivotY();
                    if (pivotX != 0.0d || pivotY != 0.0d) {
                        transform.transformPivotX = pivotX;
                        transform.transformPivotY = pivotY;
                    }
                    transform.translationX = childAt.getTranslationX();
                    transform.translationY = childAt.getTranslationY();
                    transform.translationZ = childAt.getTranslationZ();
                    if (transform.applyElevation) {
                        transform.elevation = childAt.getElevation();
                    }
                }
            }
        }
    }

    public final void clone(Constraints constraints) {
        int childCount = constraints.getChildCount();
        this.mConstraints.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraints.getChildAt(i);
            Constraints.LayoutParams layoutParams = (Constraints.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (constraint != null) {
                if (childAt instanceof ConstraintHelper) {
                    Constraint.access$700(constraint, (ConstraintHelper) childAt, id, layoutParams);
                }
                constraint.fillFromConstraints(id, layoutParams);
            }
        }
    }
}
