package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
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
import com.android.systemui.R$styleable;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.util.DeviceState;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends ViewGroup.LayoutParams {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmartActions {
        public final List actions;
        public final boolean fromAssistant;

        public SmartActions(List<Notification.Action> list, boolean z) {
            this.actions = list;
            this.fromAssistant = z;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum SmartButtonType {
        REPLY,
        ACTION
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmartReplies {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmartSuggestionMeasures {
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

    public SmartReplyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSmartRepliesGeneratedByAssistant = false;
        this.mHeightUpperLimit = NotificationUtils.getFontScaledHeight(R.dimen.smart_reply_button_max_height, ((ViewGroup) this).mContext);
        int color = context.getColor(R.color.smart_reply_button_background);
        this.mCurrentBackgroundColor = color;
        ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getTextColor(0, false, true);
        ((ViewGroup) this).mContext.getColor(R.color.smart_reply_button_text_dark_bg);
        Color.argb(Color.alpha(((ViewGroup) this).mContext.getColor(R.color.notification_ripple_untinted_color)), 255, 255, 255);
        ContrastColorUtil.calculateContrast(color, color);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartReplyView, 0, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        int i = 0;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            if (index == 1) {
                i = obtainStyledAttributes.getDimensionPixelSize(i2, 0);
            } else if (index == 0) {
                obtainStyledAttributes.getDimensionPixelSize(i2, 0);
            }
        }
        obtainStyledAttributes.recycle();
        this.mSpacing = i;
        this.mBreakIterator = BreakIterator.getLineInstance();
        this.mCandidateButtonQueueForSqueezing = new PriorityQueue(Math.max(getChildCount(), 1), DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
    }

    public static void markButtonsWithPendingSqueezeStatusAs(int i, List list) {
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            LayoutParams layoutParams = (LayoutParams) ((View) it.next()).getLayoutParams();
            if (layoutParams.squeezeStatus == 1) {
                layoutParams.squeezeStatus = i;
            }
        }
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
        long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.print("lastMeasureAge (s)=");
        indentingPrintWriter.println(this.mLastMeasureTime == 0 ? Float.NaN : (elapsedRealtime - r2) / 1000.0f);
        indentingPrintWriter.print("lastDrawChildAge (s)=");
        indentingPrintWriter.println(this.mLastDrawChildTime == 0 ? Float.NaN : (elapsedRealtime - r2) / 1000.0f);
        indentingPrintWriter.print("lastDispatchDrawAge (s)=");
        indentingPrintWriter.println(this.mLastDispatchDrawTime != 0 ? (elapsedRealtime - r2) / 1000.0f : Float.NaN);
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
        Integer valueOf = Integer.valueOf(childCount);
        panelScreenShotLogger.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "childCount", valueOf);
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
        String format = String.format("0x%08x", Integer.valueOf(this.mCurrentTextColor));
        panelScreenShotLogger3.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "currentTextColor", format);
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01d7, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0268 A[SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int childCount;
        int i5;
        int i6;
        boolean z;
        Iterator it;
        int i7;
        List list;
        SmartSuggestionMeasures smartSuggestionMeasures;
        int i8;
        int i9;
        LayoutParams layoutParams;
        List list2;
        int i10;
        int i11;
        LayoutParams layoutParams2;
        int ceil;
        int i12;
        int i13;
        int i14;
        SmartReplyView smartReplyView = this;
        int size = View.MeasureSpec.getMode(i) == 0 ? Integer.MAX_VALUE : View.MeasureSpec.getSize(i);
        int childCount2 = getChildCount();
        boolean z2 = false;
        for (int i15 = 0; i15 < childCount2; i15++) {
            LayoutParams layoutParams3 = (LayoutParams) smartReplyView.getChildAt(i15).getLayoutParams();
            layoutParams3.show = false;
            layoutParams3.squeezeStatus = 0;
            layoutParams3.mNoShowReason = UniversalCredentialManager.RESET_APPLET_FORM_FACTOR;
        }
        smartReplyView.mTotalSqueezeRemeasureAttempts = 0;
        if (!smartReplyView.mCandidateButtonQueueForSqueezing.isEmpty()) {
            Log.wtf("SmartReplyView", "Single line button queue leaked between onMeasure calls");
            smartReplyView.mCandidateButtonQueueForSqueezing.clear();
        }
        SmartSuggestionMeasures smartSuggestionMeasures2 = new SmartSuggestionMeasures(((ViewGroup) smartReplyView).mPaddingLeft + ((ViewGroup) smartReplyView).mPaddingRight, 0);
        List filterActionsOrReplies = smartReplyView.filterActionsOrReplies(SmartButtonType.ACTION);
        List filterActionsOrReplies2 = smartReplyView.filterActionsOrReplies(SmartButtonType.REPLY);
        ArrayList arrayList = new ArrayList(filterActionsOrReplies);
        arrayList.addAll(filterActionsOrReplies2);
        ArrayList arrayList2 = new ArrayList();
        int i16 = smartReplyView.mMaxNumActions;
        Iterator it2 = arrayList.iterator();
        SmartSuggestionMeasures smartSuggestionMeasures3 = null;
        int i17 = 0;
        int i18 = 0;
        while (it2.hasNext()) {
            View view = (View) it2.next();
            LayoutParams layoutParams4 = (LayoutParams) view.getLayoutParams();
            if (i16 == -1 || layoutParams4.mButtonType != SmartButtonType.ACTION || i17 < i16) {
                if (view instanceof TextView) {
                    ((TextView) view).nullLayouts();
                    view.forceLayout();
                }
                view.measure(MEASURE_SPEC_ANY_LENGTH, i2);
                Button button = (Button) view;
                it = it2;
                if (button.getLayout() == null) {
                    Log.wtf("SmartReplyView", "Button layout is null after measure.");
                }
                arrayList2.add(view);
                int lineCount = button.getLineCount();
                i7 = i16;
                if (lineCount < 1) {
                    layoutParams4.mNoShowReason = "line-count-0";
                } else if (lineCount > 2) {
                    layoutParams4.mNoShowReason = "line-count-3+";
                } else {
                    if (lineCount == 1) {
                        smartReplyView.mCandidateButtonQueueForSqueezing.add(button);
                    }
                    SmartSuggestionMeasures smartSuggestionMeasures4 = new SmartSuggestionMeasures(smartSuggestionMeasures2.mMeasuredWidth, smartSuggestionMeasures2.mMaxChildHeight);
                    if (smartSuggestionMeasures3 == null && layoutParams4.mButtonType == SmartButtonType.REPLY) {
                        smartSuggestionMeasures3 = new SmartSuggestionMeasures(smartSuggestionMeasures2.mMeasuredWidth, smartSuggestionMeasures2.mMaxChildHeight);
                    }
                    int i19 = i18 == 0 ? 0 : smartReplyView.mSpacing;
                    int measuredWidth = view.getMeasuredWidth();
                    int measuredHeight = view.getMeasuredHeight();
                    smartSuggestionMeasures2.mMeasuredWidth = i19 + measuredWidth + smartSuggestionMeasures2.mMeasuredWidth;
                    smartSuggestionMeasures2.mMaxChildHeight = Math.max(smartSuggestionMeasures2.mMaxChildHeight, measuredHeight);
                    if (smartSuggestionMeasures2.mMeasuredWidth > size) {
                        while (smartSuggestionMeasures2.mMeasuredWidth > size && !smartReplyView.mCandidateButtonQueueForSqueezing.isEmpty()) {
                            Button button2 = (Button) smartReplyView.mCandidateButtonQueueForSqueezing.poll();
                            String charSequence = button2.getText().toString();
                            TransformationMethod transformationMethod = button2.getTransformationMethod();
                            if (transformationMethod != null) {
                                charSequence = transformationMethod.getTransformation(charSequence, button2).toString();
                            }
                            int length = charSequence.length();
                            smartReplyView.mBreakIterator.setText(charSequence);
                            SmartSuggestionMeasures smartSuggestionMeasures5 = smartSuggestionMeasures3;
                            if (smartReplyView.mBreakIterator.preceding(length / 2) == -1 && smartReplyView.mBreakIterator.next() == -1) {
                                list2 = filterActionsOrReplies2;
                                i10 = i17;
                                i11 = i18;
                                layoutParams2 = layoutParams4;
                                i12 = -1;
                                ceil = -1;
                            } else {
                                TextPaint paint = button2.getPaint();
                                int current = smartReplyView.mBreakIterator.current();
                                list2 = filterActionsOrReplies2;
                                i10 = i17;
                                float desiredWidth = Layout.getDesiredWidth(charSequence, 0, current, paint);
                                float desiredWidth2 = Layout.getDesiredWidth(charSequence, current, length, paint);
                                float max = Math.max(desiredWidth, desiredWidth2);
                                if (desiredWidth != desiredWidth2) {
                                    boolean z3 = desiredWidth > desiredWidth2;
                                    int i20 = smartReplyView.mMaxSqueezeRemeasureAttempts;
                                    i11 = i18;
                                    int i21 = 0;
                                    while (i21 < i20) {
                                        int i22 = i20;
                                        smartReplyView.mTotalSqueezeRemeasureAttempts++;
                                        BreakIterator breakIterator = smartReplyView.mBreakIterator;
                                        int previous = z3 ? breakIterator.previous() : breakIterator.next();
                                        if (previous == -1) {
                                            break;
                                        }
                                        layoutParams2 = layoutParams4;
                                        float desiredWidth3 = Layout.getDesiredWidth(charSequence, 0, previous, paint);
                                        float desiredWidth4 = Layout.getDesiredWidth(charSequence, previous, length, paint);
                                        float max2 = Math.max(desiredWidth3, desiredWidth4);
                                        if (max2 >= max) {
                                            break;
                                        }
                                        boolean z4 = z3 ? false : false;
                                        if (z4) {
                                            max = max2;
                                            break;
                                        }
                                        i21++;
                                        smartReplyView = this;
                                        max = max2;
                                        i20 = i22;
                                        layoutParams4 = layoutParams2;
                                    }
                                } else {
                                    i11 = i18;
                                }
                                layoutParams2 = layoutParams4;
                                ceil = (int) Math.ceil(max);
                                i12 = -1;
                            }
                            if (ceil != i12) {
                                int measuredWidth2 = button2.getMeasuredWidth();
                                button2.nullLayouts();
                                button2.forceLayout();
                                int paddingEnd = button2.getPaddingEnd() + button2.getPaddingStart() + ceil;
                                Drawable drawable = button2.getCompoundDrawablesRelative()[0];
                                button2.measure(View.MeasureSpec.makeMeasureSpec(paddingEnd + (drawable == null ? 0 : drawable.getBounds().width() + button2.getCompoundDrawablePadding()), VideoPlayer.MEDIA_ERROR_SYSTEM), i2);
                                if (button2.getLayout() == null) {
                                    Log.wtf("SmartReplyView", "Button layout is null after measure.");
                                }
                                int measuredWidth3 = button2.getMeasuredWidth();
                                LayoutParams layoutParams5 = (LayoutParams) button2.getLayoutParams();
                                if (button2.getLineCount() > 2 || measuredWidth3 >= measuredWidth2) {
                                    layoutParams5.squeezeStatus = 3;
                                } else {
                                    layoutParams5.squeezeStatus = 1;
                                    i14 = measuredWidth2 - measuredWidth3;
                                    i13 = -1;
                                    if (i14 == i13) {
                                        smartSuggestionMeasures2.mMaxChildHeight = Math.max(smartSuggestionMeasures2.mMaxChildHeight, button2.getMeasuredHeight());
                                        smartSuggestionMeasures2.mMeasuredWidth -= i14;
                                    }
                                    smartReplyView = this;
                                    smartSuggestionMeasures3 = smartSuggestionMeasures5;
                                    filterActionsOrReplies2 = list2;
                                    i17 = i10;
                                    i18 = i11;
                                    layoutParams4 = layoutParams2;
                                }
                            }
                            i13 = -1;
                            i14 = -1;
                            if (i14 == i13) {
                            }
                            smartReplyView = this;
                            smartSuggestionMeasures3 = smartSuggestionMeasures5;
                            filterActionsOrReplies2 = list2;
                            i17 = i10;
                            i18 = i11;
                            layoutParams4 = layoutParams2;
                        }
                        list = filterActionsOrReplies2;
                        smartSuggestionMeasures = smartSuggestionMeasures3;
                        i8 = i17;
                        i9 = i18;
                        LayoutParams layoutParams6 = layoutParams4;
                        if (smartSuggestionMeasures2.mMeasuredWidth > size) {
                            markButtonsWithPendingSqueezeStatusAs(3, arrayList2);
                            layoutParams6.mNoShowReason = "overflow";
                            smartSuggestionMeasures2 = smartSuggestionMeasures4;
                            smartSuggestionMeasures3 = smartSuggestionMeasures;
                            i17 = i8;
                            i18 = i9;
                            z2 = false;
                            smartReplyView = this;
                            it2 = it;
                            i16 = i7;
                            filterActionsOrReplies2 = list;
                        } else {
                            layoutParams = layoutParams6;
                            markButtonsWithPendingSqueezeStatusAs(2, arrayList2);
                        }
                    } else {
                        list = filterActionsOrReplies2;
                        smartSuggestionMeasures = smartSuggestionMeasures3;
                        i8 = i17;
                        i9 = i18;
                        layoutParams = layoutParams4;
                    }
                    layoutParams.show = true;
                    layoutParams.mNoShowReason = "n/a";
                    i18 = i9 + 1;
                    i17 = layoutParams.mButtonType == SmartButtonType.ACTION ? i8 + 1 : i8;
                    smartSuggestionMeasures3 = smartSuggestionMeasures;
                    z2 = false;
                    smartReplyView = this;
                    it2 = it;
                    i16 = i7;
                    filterActionsOrReplies2 = list;
                }
            } else {
                layoutParams4.mNoShowReason = "max-actions-shown";
                it = it2;
                i7 = i16;
            }
            list = filterActionsOrReplies2;
            z2 = false;
            smartReplyView = this;
            it2 = it;
            i16 = i7;
            filterActionsOrReplies2 = list;
        }
        List list3 = filterActionsOrReplies2;
        smartReplyView.mDidHideSystemReplies = z2;
        if (smartReplyView.mSmartRepliesGeneratedByAssistant) {
            if (smartReplyView.mMinNumSystemGeneratedReplies > 1) {
                Iterator it3 = ((ArrayList) list3).iterator();
                int i23 = 0;
                while (it3.hasNext()) {
                    if (((LayoutParams) ((View) it3.next()).getLayoutParams()).show) {
                        i23++;
                    }
                }
                if (i23 != 0 && i23 < smartReplyView.mMinNumSystemGeneratedReplies) {
                    z = false;
                    if (!z) {
                        Iterator it4 = ((ArrayList) list3).iterator();
                        while (it4.hasNext()) {
                            LayoutParams layoutParams7 = (LayoutParams) ((View) it4.next()).getLayoutParams();
                            layoutParams7.show = false;
                            layoutParams7.mNoShowReason = "not-enough-system-replies";
                        }
                        i3 = 1;
                        i4 = 0;
                        smartReplyView.mDidHideSystemReplies = true;
                        smartSuggestionMeasures2 = smartSuggestionMeasures3;
                        smartReplyView.mCandidateButtonQueueForSqueezing.clear();
                        int i24 = smartSuggestionMeasures2.mMaxChildHeight;
                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i24, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                        childCount = getChildCount();
                        for (i5 = i4; i5 < childCount; i5++) {
                            View childAt = smartReplyView.getChildAt(i5);
                            LayoutParams layoutParams8 = (LayoutParams) childAt.getLayoutParams();
                            if (layoutParams8.show) {
                                int measuredWidth4 = childAt.getMeasuredWidth();
                                if (layoutParams8.squeezeStatus == 3) {
                                    i6 = i3;
                                    measuredWidth4 = Integer.MAX_VALUE;
                                } else {
                                    i6 = i4;
                                }
                                if (childAt.getMeasuredHeight() != i24) {
                                    i6 = i3;
                                }
                                if (i6 != 0) {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, VideoPlayer.MEDIA_ERROR_SYSTEM), makeMeasureSpec);
                                }
                            }
                        }
                        smartReplyView.setMeasuredDimension(ViewGroup.resolveSize(Math.max(getSuggestedMinimumWidth(), smartSuggestionMeasures2.mMeasuredWidth), i), ViewGroup.resolveSize(Math.max(getSuggestedMinimumHeight(), ((ViewGroup) smartReplyView).mPaddingTop + smartSuggestionMeasures2.mMaxChildHeight + ((ViewGroup) smartReplyView).mPaddingBottom), i2));
                        smartReplyView.mLastMeasureTime = SystemClock.elapsedRealtime();
                    }
                }
            }
            z = true;
            if (!z) {
            }
        }
        i3 = 1;
        i4 = 0;
        smartReplyView.mCandidateButtonQueueForSqueezing.clear();
        int i242 = smartSuggestionMeasures2.mMaxChildHeight;
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i242, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        childCount = getChildCount();
        while (i5 < childCount) {
        }
        smartReplyView.setMeasuredDimension(ViewGroup.resolveSize(Math.max(getSuggestedMinimumWidth(), smartSuggestionMeasures2.mMeasuredWidth), i), ViewGroup.resolveSize(Math.max(getSuggestedMinimumHeight(), ((ViewGroup) smartReplyView).mPaddingTop + smartSuggestionMeasures2.mMaxChildHeight + ((ViewGroup) smartReplyView).mPaddingBottom), i2));
        smartReplyView.mLastMeasureTime = SystemClock.elapsedRealtime();
    }

    public final void setButtonColors(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof RippleDrawable) {
            Drawable mutate = background.mutate();
            RippleDrawable rippleDrawable = (RippleDrawable) mutate;
            rippleDrawable.setColor(ColorStateList.valueOf(this.mCurrentRippleColor));
            Drawable drawable = rippleDrawable.getDrawable(0);
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 instanceof GradientDrawable) {
                    ((GradientDrawable) drawable2).setColor(this.mCurrentBackgroundColor);
                }
            }
            button.setBackground(mutate);
        }
        button.setTextColor(this.mCurrentTextColor);
    }

    public final void updateButtonColorOnUiModeChanged() {
        this.mCurrentTextColor = ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getTextColor(0, false, true);
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
