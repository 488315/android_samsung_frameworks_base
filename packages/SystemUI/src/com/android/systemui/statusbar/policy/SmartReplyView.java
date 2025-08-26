package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.SystemClock;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.res.R$styleable;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.util.DeviceState;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public class SmartReplyView extends ViewGroup implements PanelScreenShotLogger.LogProvider {
    public final BreakIterator mBreakIterator;
    public PriorityQueue mCandidateButtonQueueForSqueezing;
    public int mCurrentBackgroundColor;
    public int mCurrentRippleColor;
    public int mCurrentTextColor;
    public boolean mDidHideSystemReplies;
    public final int mHeightUpperLimit;
    public long mLastDispatchDrawTime;
    public long mLastDrawChildTime;
    public long mLastMeasureTime;
    public int mMaxNumActions;
    public int mMaxSqueezeRemeasureAttempts;
    public int mMinNumSystemGeneratedReplies;
    public boolean mSmartRepliesGeneratedByAssistant;
    public View mSmartReplyContainer;
    public final int mSpacing;
    public int mTotalSqueezeRemeasureAttempts;
    public static final int MEASURE_SPEC_ANY_LENGTH = View.MeasureSpec.makeMeasureSpec(0, 0);
    public static final SmartReplyView$$ExternalSyntheticLambda0 DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR = new SmartReplyView$$ExternalSyntheticLambda0();

    class LayoutParams extends ViewGroup.LayoutParams {
        public SmartButtonType mButtonType;
        public String mNoShowReason;
        public boolean show;
        public int squeezeStatus;

        public /* synthetic */ LayoutParams(int i, int i2, int i3) {
            this(i, i2);
        }

        public boolean isShown() {
            return this.show;
        }

        public /* synthetic */ LayoutParams(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet);
        }

        private LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.show = false;
            this.squeezeStatus = 0;
            this.mButtonType = SmartButtonType.REPLY;
            this.mNoShowReason = "new";
        }

        private LayoutParams(int i, int i2) {
            super(i, i2);
            this.show = false;
            this.squeezeStatus = 0;
            this.mButtonType = SmartButtonType.REPLY;
            this.mNoShowReason = "new";
        }
    }

    public class SmartActions {
        public final List actions;
        public final boolean fromAssistant;

        public SmartActions(List<Notification.Action> list, boolean z) {
            this.actions = list;
            this.fromAssistant = z;
        }
    }

    enum SmartButtonType {
        REPLY,
        ACTION
    }

    public class SmartReplies {
        public final List choices;
        public final boolean fromAssistant;
        public final PendingIntent pendingIntent;
        public final RemoteInput remoteInput;

        public SmartReplies(List<CharSequence> list, RemoteInput remoteInput, PendingIntent pendingIntent, boolean z) {
            this.choices = list;
            this.remoteInput = remoteInput;
            this.pendingIntent = pendingIntent;
            this.fromAssistant = z;
        }
    }

    public class SmartSuggestionMeasures {
        public int mMaxChildHeight;
        public int mMeasuredWidth;

        public SmartSuggestionMeasures(int i, int i2) {
            this.mMeasuredWidth = i;
            this.mMaxChildHeight = i2;
        }

        public final Object clone() {
            return new SmartSuggestionMeasures(this.mMeasuredWidth, this.mMaxChildHeight);
        }
    }

    public SmartReplyView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mSmartRepliesGeneratedByAssistant = false;
        this.mHeightUpperLimit = NotificationUtils.getFontScaledHeight(R.dimen.smart_reply_button_max_height, ((ViewGroup) this).mContext);
        int color = context.getColor(R.color.smart_reply_button_background);
        this.mCurrentBackgroundColor = color;
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(0, false, true);
        ((ViewGroup) this).mContext.getColor(R.color.smart_reply_button_text_dark_bg);
        Color.argb(Color.alpha(((ViewGroup) this).mContext.getColor(R.color.notification_ripple_untinted_color)), 255, 255, 255);
        ContrastColorUtil.calculateContrast(color, color);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartReplyView, 0, 0);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        int dimensionPixelSize = 0;
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == 1) {
                dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(i, 0);
            } else if (index == 0) {
                typedArrayObtainStyledAttributes.getDimensionPixelSize(i, 0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mSpacing = dimensionPixelSize;
        this.mBreakIterator = BreakIterator.getLineInstance();
        this.mCandidateButtonQueueForSqueezing = new PriorityQueue(Math.max(getChildCount(), 1), DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mLastDispatchDrawTime = SystemClock.elapsedRealtime();
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        if (!((LayoutParams) view.getLayoutParams()).show) {
            return false;
        }
        this.mLastDrawChildTime = SystemClock.elapsedRealtime();
        return super.drawChild(canvas, view, j);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println(this);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("mMaxSqueezeRemeasureAttempts=");
        indentingPrintWriter.println(this.mMaxSqueezeRemeasureAttempts);
        indentingPrintWriter.print("mTotalSqueezeRemeasureAttempts=");
        indentingPrintWriter.println(this.mTotalSqueezeRemeasureAttempts);
        indentingPrintWriter.print("mMaxNumActions=");
        indentingPrintWriter.println(this.mMaxNumActions);
        indentingPrintWriter.print("mSmartRepliesGeneratedByAssistant=");
        indentingPrintWriter.println(this.mSmartRepliesGeneratedByAssistant);
        indentingPrintWriter.print("mMinNumSystemGeneratedReplies=");
        indentingPrintWriter.println(this.mMinNumSystemGeneratedReplies);
        indentingPrintWriter.print("mHeightUpperLimit=");
        indentingPrintWriter.println(this.mHeightUpperLimit);
        indentingPrintWriter.print("mDidHideSystemReplies=");
        indentingPrintWriter.println(this.mDidHideSystemReplies);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.print("lastMeasureAge (s)=");
        indentingPrintWriter.println(this.mLastMeasureTime == 0 ? Float.NaN : (jElapsedRealtime - r2) / 1000.0f);
        indentingPrintWriter.print("lastDrawChildAge (s)=");
        indentingPrintWriter.println(this.mLastDrawChildTime == 0 ? Float.NaN : (jElapsedRealtime - r2) / 1000.0f);
        indentingPrintWriter.print("lastDispatchDrawAge (s)=");
        indentingPrintWriter.println(this.mLastDispatchDrawTime != 0 ? (jElapsedRealtime - r2) / 1000.0f : Float.NaN);
        int childCount = getChildCount();
        indentingPrintWriter.print("children: num=");
        indentingPrintWriter.println(childCount);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            indentingPrintWriter.print("[");
            indentingPrintWriter.print(i);
            indentingPrintWriter.print("] type=");
            indentingPrintWriter.print(layoutParams.mButtonType);
            indentingPrintWriter.print(" squeezeStatus=");
            indentingPrintWriter.print(layoutParams.squeezeStatus);
            indentingPrintWriter.print(" show=");
            indentingPrintWriter.print(layoutParams.show);
            indentingPrintWriter.print(" noShowReason=");
            indentingPrintWriter.print(layoutParams.mNoShowReason);
            indentingPrintWriter.print(" view=");
            indentingPrintWriter.println(childAt);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final List filterActionsOrReplies(SmartButtonType smartButtonType) {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 0 && (childAt instanceof Button) && layoutParams.mButtonType == smartButtonType) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        Integer numValueOf = Integer.valueOf(childCount);
        panelScreenShotLogger.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "childCount", numValueOf);
        for (int i = 0; i < childCount; i++) {
            Button button = (Button) getChildAt(i);
            if (button.getText() != null) {
                PanelScreenShotLogger panelScreenShotLogger2 = PanelScreenShotLogger.INSTANCE;
                CharSequence text = button.getText();
                panelScreenShotLogger2.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "buttonText", text);
                PanelScreenShotLogger.addLogItem(arrayList, "visibility", Integer.valueOf(button.getVisibility()));
                PanelScreenShotLogger.addLogItem(arrayList, "width", Integer.valueOf(button.getWidth()));
                PanelScreenShotLogger.addLogItem(arrayList, "height", Integer.valueOf(button.getHeight()));
                PanelScreenShotLogger.addLogItem(arrayList, "alpha", Float.valueOf(button.getAlpha()));
                arrayList.add("\n");
            }
        }
        PanelScreenShotLogger panelScreenShotLogger3 = PanelScreenShotLogger.INSTANCE;
        String str = String.format("0x%08x", Integer.valueOf(this.mCurrentTextColor));
        panelScreenShotLogger3.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "currentTextColor", str);
        PanelScreenShotLogger.addLogItem(arrayList, "currentTextColor", String.format("0x%08x", Integer.valueOf(this.mCurrentBackgroundColor)));
        return arrayList;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        int i = -2;
        return new LayoutParams(i, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = getLayoutDirection() == 1;
        int i5 = z2 ? (i3 - i) - ((ViewGroup) this).mPaddingRight : ((ViewGroup) this).mPaddingLeft;
        int childCount = getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (((LayoutParams) childAt.getLayoutParams()).show) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = z2 ? i5 - measuredWidth : i5;
                childAt.layout(i7, 0, i7 + measuredWidth, measuredHeight);
                int i8 = measuredWidth + this.mSpacing;
                i5 = z2 ? i5 - i8 : i5 + i8;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x01ed, code lost:
    
        r13 = r28;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        ArrayList arrayList;
        int i6;
        int i7;
        SmartSuggestionMeasures smartSuggestionMeasures;
        int i8;
        int i9;
        int i10;
        int i11;
        int iCeil;
        float f;
        int i12;
        int size = View.MeasureSpec.getMode(i) == 0 ? Integer.MAX_VALUE : View.MeasureSpec.getSize(i);
        int childCount = getChildCount();
        for (int i13 = 0; i13 < childCount; i13++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i13).getLayoutParams();
            layoutParams.show = false;
            layoutParams.squeezeStatus = 0;
            layoutParams.mNoShowReason = UniversalCredentialManager.RESET_APPLET_FORM_FACTOR;
        }
        this.mTotalSqueezeRemeasureAttempts = 0;
        if (!this.mCandidateButtonQueueForSqueezing.isEmpty()) {
            Log.wtf("SmartReplyView", "Single line button queue leaked between onMeasure calls");
            this.mCandidateButtonQueueForSqueezing.clear();
        }
        SmartSuggestionMeasures smartSuggestionMeasures2 = new SmartSuggestionMeasures(((ViewGroup) this).mPaddingLeft + ((ViewGroup) this).mPaddingRight, 0);
        List listFilterActionsOrReplies = filterActionsOrReplies(SmartButtonType.ACTION);
        List listFilterActionsOrReplies2 = filterActionsOrReplies(SmartButtonType.REPLY);
        ArrayList arrayList2 = new ArrayList(listFilterActionsOrReplies);
        arrayList2.addAll(listFilterActionsOrReplies2);
        ArrayList arrayList3 = new ArrayList();
        int i14 = this.mMaxNumActions;
        int size2 = arrayList2.size();
        SmartSuggestionMeasures smartSuggestionMeasures3 = null;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        while (i17 < size2) {
            Object obj = arrayList2.get(i17);
            i17++;
            View view = (View) obj;
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            List list = listFilterActionsOrReplies2;
            if (i14 != -1) {
                arrayList = arrayList2;
                if (layoutParams2.mButtonType == SmartButtonType.ACTION && i15 >= i14) {
                    layoutParams2.mNoShowReason = "max-actions-shown";
                    i6 = i14;
                }
                listFilterActionsOrReplies2 = list;
                arrayList2 = arrayList;
                i14 = i6;
            } else {
                arrayList = arrayList2;
            }
            if (view instanceof TextView) {
                ((TextView) view).nullLayouts();
                view.forceLayout();
            }
            view.measure(MEASURE_SPEC_ANY_LENGTH, i2);
            Button button = (Button) view;
            if (button.getLayout() == null) {
                Log.wtf("SmartReplyView", "Button layout is null after measure.");
            }
            arrayList3.add(view);
            int lineCount = button.getLineCount();
            i6 = i14;
            if (lineCount < 1) {
                layoutParams2.mNoShowReason = "line-count-0";
            } else if (lineCount > 2) {
                layoutParams2.mNoShowReason = "line-count-3+";
            } else {
                if (lineCount == 1) {
                    this.mCandidateButtonQueueForSqueezing.add(button);
                }
                SmartSuggestionMeasures smartSuggestionMeasures4 = new SmartSuggestionMeasures(smartSuggestionMeasures2.mMeasuredWidth, smartSuggestionMeasures2.mMaxChildHeight);
                if (smartSuggestionMeasures3 == null && layoutParams2.mButtonType == SmartButtonType.REPLY) {
                    smartSuggestionMeasures3 = new SmartSuggestionMeasures(smartSuggestionMeasures2.mMeasuredWidth, smartSuggestionMeasures2.mMaxChildHeight);
                }
                int i18 = i16 == 0 ? 0 : this.mSpacing;
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                smartSuggestionMeasures2.mMeasuredWidth = i18 + measuredWidth + smartSuggestionMeasures2.mMeasuredWidth;
                smartSuggestionMeasures2.mMaxChildHeight = Math.max(smartSuggestionMeasures2.mMaxChildHeight, measuredHeight);
                if (smartSuggestionMeasures2.mMeasuredWidth > size) {
                    while (smartSuggestionMeasures2.mMeasuredWidth > size && !this.mCandidateButtonQueueForSqueezing.isEmpty()) {
                        Button button2 = (Button) this.mCandidateButtonQueueForSqueezing.poll();
                        String string = button2.getText().toString();
                        TransformationMethod transformationMethod = button2.getTransformationMethod();
                        if (transformationMethod != null) {
                            string = transformationMethod.getTransformation(string, button2).toString();
                        }
                        int length = string.length();
                        int i19 = size2;
                        this.mBreakIterator.setText(string);
                        SmartSuggestionMeasures smartSuggestionMeasures5 = smartSuggestionMeasures3;
                        if (this.mBreakIterator.preceding(length / 2) == -1 && this.mBreakIterator.next() == -1) {
                            i10 = i15;
                            i11 = i16;
                            iCeil = -1;
                        } else {
                            TextPaint paint = button2.getPaint();
                            int iCurrent = this.mBreakIterator.current();
                            i10 = i15;
                            i11 = i16;
                            float desiredWidth = Layout.getDesiredWidth(string, 0, iCurrent, paint);
                            float desiredWidth2 = Layout.getDesiredWidth(string, iCurrent, length, paint);
                            float fMax = Math.max(desiredWidth, desiredWidth2);
                            if (desiredWidth != desiredWidth2) {
                                boolean z = desiredWidth > desiredWidth2;
                                int i20 = this.mMaxSqueezeRemeasureAttempts;
                                boolean z2 = z;
                                int i21 = 0;
                                while (true) {
                                    if (i21 >= i20) {
                                        f = fMax;
                                        break;
                                    }
                                    int i22 = i21;
                                    this.mTotalSqueezeRemeasureAttempts++;
                                    BreakIterator breakIterator = this.mBreakIterator;
                                    int iPrevious = z2 ? breakIterator.previous() : breakIterator.next();
                                    f = fMax;
                                    if (iPrevious == -1) {
                                        break;
                                    }
                                    int i23 = i20;
                                    float desiredWidth3 = Layout.getDesiredWidth(string, 0, iPrevious, paint);
                                    float desiredWidth4 = Layout.getDesiredWidth(string, iPrevious, length, paint);
                                    fMax = Math.max(desiredWidth3, desiredWidth4);
                                    if (fMax >= f) {
                                        break;
                                    }
                                    if (z2) {
                                        if (desiredWidth3 <= desiredWidth4) {
                                            break;
                                        }
                                        i21 = i22 + 1;
                                        i20 = i23;
                                    } else {
                                        if (desiredWidth3 >= desiredWidth4) {
                                            break;
                                        }
                                        i21 = i22 + 1;
                                        i20 = i23;
                                    }
                                }
                            }
                            iCeil = (int) Math.ceil(fMax);
                        }
                        if (iCeil == -1) {
                            i12 = -1;
                        } else {
                            int measuredWidth2 = button2.getMeasuredWidth();
                            button2.nullLayouts();
                            button2.forceLayout();
                            int paddingEnd = button2.getPaddingEnd() + button2.getPaddingStart() + iCeil;
                            Drawable drawable = button2.getCompoundDrawablesRelative()[0];
                            button2.measure(View.MeasureSpec.makeMeasureSpec(paddingEnd + (drawable == null ? 0 : drawable.getBounds().width() + button2.getCompoundDrawablePadding()), Integer.MIN_VALUE), i2);
                            if (button2.getLayout() == null) {
                                Log.wtf("SmartReplyView", "Button layout is null after measure.");
                            }
                            int measuredWidth3 = button2.getMeasuredWidth();
                            LayoutParams layoutParams3 = (LayoutParams) button2.getLayoutParams();
                            if (button2.getLineCount() > 2 || measuredWidth3 >= measuredWidth2) {
                                layoutParams3.squeezeStatus = 3;
                                i12 = -1;
                            } else {
                                layoutParams3.squeezeStatus = 1;
                                i12 = measuredWidth2 - measuredWidth3;
                            }
                        }
                        if (i12 != -1) {
                            smartSuggestionMeasures2.mMaxChildHeight = Math.max(smartSuggestionMeasures2.mMaxChildHeight, button2.getMeasuredHeight());
                            smartSuggestionMeasures2.mMeasuredWidth -= i12;
                        }
                        size2 = i19;
                        smartSuggestionMeasures3 = smartSuggestionMeasures5;
                        i15 = i10;
                        i16 = i11;
                    }
                    i7 = size2;
                    smartSuggestionMeasures = smartSuggestionMeasures3;
                    i8 = i15;
                    i9 = i16;
                    if (smartSuggestionMeasures2.mMeasuredWidth > size) {
                        int size3 = arrayList3.size();
                        int i24 = 0;
                        while (i24 < size3) {
                            Object obj2 = arrayList3.get(i24);
                            i24++;
                            LayoutParams layoutParams4 = (LayoutParams) ((View) obj2).getLayoutParams();
                            if (layoutParams4.squeezeStatus == 1) {
                                layoutParams4.squeezeStatus = 3;
                            }
                        }
                        layoutParams2.mNoShowReason = "overflow";
                        listFilterActionsOrReplies2 = list;
                        arrayList2 = arrayList;
                        size2 = i7;
                        i14 = i6;
                        smartSuggestionMeasures2 = smartSuggestionMeasures4;
                        smartSuggestionMeasures3 = smartSuggestionMeasures;
                        i15 = i8;
                        i16 = i9;
                    } else {
                        int size4 = arrayList3.size();
                        int i25 = 0;
                        while (i25 < size4) {
                            Object obj3 = arrayList3.get(i25);
                            i25++;
                            LayoutParams layoutParams5 = (LayoutParams) ((View) obj3).getLayoutParams();
                            if (layoutParams5.squeezeStatus == 1) {
                                layoutParams5.squeezeStatus = 2;
                            }
                        }
                    }
                } else {
                    i7 = size2;
                    smartSuggestionMeasures = smartSuggestionMeasures3;
                    i8 = i15;
                    i9 = i16;
                }
                layoutParams2.show = true;
                layoutParams2.mNoShowReason = "n/a";
                i16 = i9 + 1;
                i15 = layoutParams2.mButtonType == SmartButtonType.ACTION ? i8 + 1 : i8;
                listFilterActionsOrReplies2 = list;
                arrayList2 = arrayList;
                size2 = i7;
                i14 = i6;
                smartSuggestionMeasures3 = smartSuggestionMeasures;
            }
            listFilterActionsOrReplies2 = list;
            arrayList2 = arrayList;
            i14 = i6;
        }
        List list2 = listFilterActionsOrReplies2;
        this.mDidHideSystemReplies = false;
        if (!this.mSmartRepliesGeneratedByAssistant) {
            i3 = 1;
            i4 = 0;
        } else if (this.mMinNumSystemGeneratedReplies <= 1) {
            i3 = 1;
            i4 = 0;
        } else {
            ArrayList arrayList4 = (ArrayList) list2;
            int size5 = arrayList4.size();
            int i26 = 0;
            int i27 = 0;
            while (i26 < size5) {
                Object obj4 = arrayList4.get(i26);
                i26++;
                if (((LayoutParams) ((View) obj4).getLayoutParams()).show) {
                    i27++;
                }
            }
            if (i27 != 0 && i27 < this.mMinNumSystemGeneratedReplies) {
                int size6 = arrayList4.size();
                int i28 = 0;
                while (i28 < size6) {
                    Object obj5 = arrayList4.get(i28);
                    i28++;
                    LayoutParams layoutParams6 = (LayoutParams) ((View) obj5).getLayoutParams();
                    layoutParams6.show = false;
                    layoutParams6.mNoShowReason = "not-enough-system-replies";
                }
                i3 = 1;
                i4 = 0;
                this.mDidHideSystemReplies = true;
                smartSuggestionMeasures2 = smartSuggestionMeasures3;
            }
            i3 = 1;
            i4 = 0;
        }
        this.mCandidateButtonQueueForSqueezing.clear();
        int i29 = smartSuggestionMeasures2.mMaxChildHeight;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i29, 1073741824);
        int childCount2 = getChildCount();
        for (int i30 = i4; i30 < childCount2; i30++) {
            View childAt = getChildAt(i30);
            LayoutParams layoutParams7 = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams7.show) {
                int measuredWidth4 = childAt.getMeasuredWidth();
                if (layoutParams7.squeezeStatus == 3) {
                    i5 = i3;
                    measuredWidth4 = Integer.MAX_VALUE;
                } else {
                    i5 = i4;
                }
                if (childAt.getMeasuredHeight() != i29) {
                    i5 = i3;
                }
                if (i5 != 0) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, Integer.MIN_VALUE), iMakeMeasureSpec);
                }
            }
        }
        setMeasuredDimension(ViewGroup.resolveSize(Math.max(getSuggestedMinimumWidth(), smartSuggestionMeasures2.mMeasuredWidth), i), ViewGroup.resolveSize(Math.max(getSuggestedMinimumHeight(), ((ViewGroup) this).mPaddingTop + smartSuggestionMeasures2.mMaxChildHeight + ((ViewGroup) this).mPaddingBottom), i2));
        this.mLastMeasureTime = SystemClock.elapsedRealtime();
    }

    public final void setButtonColors(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof RippleDrawable) {
            Drawable drawableMutate = background.mutate();
            RippleDrawable rippleDrawable = (RippleDrawable) drawableMutate;
            rippleDrawable.setColor(ColorStateList.valueOf(this.mCurrentRippleColor));
            Drawable drawable = rippleDrawable.getDrawable(0);
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 instanceof GradientDrawable) {
                    ((GradientDrawable) drawable2).setColor(this.mCurrentBackgroundColor);
                }
            }
            button.setBackground(drawableMutate);
        }
        button.setTextColor(this.mCurrentTextColor);
    }

    public final void updateButtonColorOnUiModeChanged() {
        this.mCurrentTextColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(0, false, true);
        this.mCurrentRippleColor = ((ViewGroup) this).mContext.getColor(R.color.notification_ripple_untinted_color);
        this.mCurrentBackgroundColor = ((ViewGroup) this).mContext.getColor(R.color.smart_reply_button_background);
        if (DeviceState.isOpenTheme(((ViewGroup) this).mContext)) {
            int color = ((ViewGroup) this).mContext.getColor(R.color.open_theme_notification_title_text_color);
            this.mCurrentTextColor = color;
            this.mCurrentBackgroundColor = Color.argb(127, Color.red(color), Color.green(this.mCurrentTextColor), Color.blue(this.mCurrentTextColor));
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Button button = (Button) getChildAt(i);
            if (button.getCompoundDrawables() != null && button.getCompoundDrawables()[0] != null) {
                if (button.getCompoundDrawables()[0] instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) button.getCompoundDrawables()[0];
                    if (bitmapDrawable.getBitmap().getConfig() == Bitmap.Config.HARDWARE) {
                        bitmapDrawable.setBitmap(bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true));
                        button.setCompoundDrawables(bitmapDrawable, null, null, null);
                    }
                }
                if (ContrastColorUtil.getInstance(getContext()).isGrayscaleIcon(button.getCompoundDrawables()[0])) {
                    button.getCompoundDrawables()[0].setColorFilter(this.mCurrentTextColor, PorterDuff.Mode.SRC_ATOP);
                }
            }
            setButtonColors(button);
        }
    }

    @Override // android.view.ViewGroup
    public final LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(((ViewGroup) this).mContext, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams.width, layoutParams.height, 0);
    }
}
