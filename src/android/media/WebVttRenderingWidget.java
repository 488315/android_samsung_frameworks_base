package android.media;

import android.content.Context;
import android.media.SubtitleTrack;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import com.android.internal.widget.SubtitleView;
import java.util.ArrayList;
import java.util.Vector;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class WebVttRenderingWidget extends ViewGroup implements SubtitleTrack.RenderingWidget {
    private static final boolean DEBUG = false;
    private static final int DEBUG_CUE_BACKGROUND = -2130771968;
    private static final int DEBUG_REGION_BACKGROUND = -2147483393;
    private static final CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = CaptioningManager.CaptionStyle.DEFAULT;
    private static final float LINE_HEIGHT_RATIO = 0.0533f;
    private CaptioningManager.CaptionStyle mCaptionStyle;
    private final CaptioningManager.CaptioningChangeListener mCaptioningListener;
    private final ArrayMap<TextTrackCue, CueLayout> mCueBoxes;
    private float mFontSize;
    private boolean mHasChangeListener;
    private SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final CaptioningManager mManager;
    private final ArrayMap<TextTrackRegion, RegionLayout> mRegionBoxes;

    /* JADX INFO: Access modifiers changed from: private */
    public static int resolveCueAlignment(int i, int i2) {
        return i2 != 201 ? i2 != 202 ? i2 : i == 0 ? 204 : 203 : i == 0 ? 203 : 204;
    }

    public WebVttRenderingWidget(Context context) {
        this(context, null);
    }

    public WebVttRenderingWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebVttRenderingWidget(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WebVttRenderingWidget(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRegionBoxes = new ArrayMap<>();
        this.mCueBoxes = new ArrayMap<>();
        this.mCaptioningListener = new CaptioningManager.CaptioningChangeListener() { // from class: android.media.WebVttRenderingWidget.1
            @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
            public void onFontScaleChanged(float f) {
                float height = f * WebVttRenderingWidget.this.getHeight() * WebVttRenderingWidget.LINE_HEIGHT_RATIO;
                WebVttRenderingWidget webVttRenderingWidget = WebVttRenderingWidget.this;
                webVttRenderingWidget.setCaptionStyle(webVttRenderingWidget.mCaptionStyle, height);
            }

            @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
            public void onUserStyleChanged(CaptioningManager.CaptionStyle captionStyle) {
                WebVttRenderingWidget webVttRenderingWidget = WebVttRenderingWidget.this;
                webVttRenderingWidget.setCaptionStyle(captionStyle, webVttRenderingWidget.mFontSize);
            }
        };
        setLayerType(1, null);
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService(Context.CAPTIONING_SERVICE);
        this.mManager = captioningManager;
        this.mCaptionStyle = captioningManager.getUserStyle();
        this.mFontSize = captioningManager.getFontScale() * getHeight() * LINE_HEIGHT_RATIO;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setSize(int i, int i2) {
        measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        layout(0, 0, i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        manageChangeListener();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        manageChangeListener();
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setOnChangedListener(SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener) {
        this.mListener = onChangedListener;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setVisible(boolean z) {
        if (z) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        manageChangeListener();
    }

    private void manageChangeListener() {
        boolean z = isAttachedToWindow() && getVisibility() == 0;
        if (this.mHasChangeListener != z) {
            this.mHasChangeListener = z;
            if (z) {
                this.mManager.addCaptioningChangeListener(this.mCaptioningListener);
                setCaptionStyle(this.mManager.getUserStyle(), this.mManager.getFontScale() * getHeight() * LINE_HEIGHT_RATIO);
            } else {
                this.mManager.removeCaptioningChangeListener(this.mCaptioningListener);
            }
        }
    }

    public void setActiveCues(Vector<SubtitleTrack.Cue> vector) {
        Context context = getContext();
        CaptioningManager.CaptionStyle captionStyle = this.mCaptionStyle;
        float f = this.mFontSize;
        prepForPrune();
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            TextTrackCue textTrackCue = (TextTrackCue) vector.get(i);
            TextTrackRegion textTrackRegion = textTrackCue.mRegion;
            if (textTrackRegion != null) {
                RegionLayout regionLayout = this.mRegionBoxes.get(textTrackRegion);
                if (regionLayout == null) {
                    regionLayout = new RegionLayout(context, textTrackRegion, captionStyle, f);
                    this.mRegionBoxes.put(textTrackRegion, regionLayout);
                    addView(regionLayout, -2, -2);
                }
                regionLayout.put(textTrackCue);
            } else {
                CueLayout cueLayout = this.mCueBoxes.get(textTrackCue);
                if (cueLayout == null) {
                    cueLayout = new CueLayout(context, textTrackCue, captionStyle, f);
                    this.mCueBoxes.put(textTrackCue, cueLayout);
                    addView(cueLayout, -2, -2);
                }
                cueLayout.update();
                cueLayout.setOrder(i);
            }
        }
        prune();
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            setSize(width, height);
        } else {
            Log.w(getClass().getName(), "setActiveCues width : " + width + ", height : " + height);
        }
        SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener = this.mListener;
        if (onChangedListener != null) {
            onChangedListener.onChanged(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
        CaptioningManager.CaptionStyle applyStyle = DEFAULT_CAPTION_STYLE.applyStyle(captionStyle);
        this.mCaptionStyle = applyStyle;
        this.mFontSize = f;
        int size = this.mCueBoxes.size();
        for (int i = 0; i < size; i++) {
            this.mCueBoxes.valueAt(i).setCaptionStyle(applyStyle, f);
        }
        int size2 = this.mRegionBoxes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mRegionBoxes.valueAt(i2).setCaptionStyle(applyStyle, f);
        }
    }

    private void prune() {
        int size = this.mRegionBoxes.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            RegionLayout valueAt = this.mRegionBoxes.valueAt(i2);
            if (valueAt.prune()) {
                removeView(valueAt);
                this.mRegionBoxes.removeAt(i2);
                size--;
                i2--;
            }
            i2++;
        }
        int size2 = this.mCueBoxes.size();
        while (i < size2) {
            CueLayout valueAt2 = this.mCueBoxes.valueAt(i);
            if (!valueAt2.isActive()) {
                removeView(valueAt2);
                this.mCueBoxes.removeAt(i);
                size2--;
                i--;
            }
            i++;
        }
    }

    private void prepForPrune() {
        int size = this.mRegionBoxes.size();
        for (int i = 0; i < size; i++) {
            this.mRegionBoxes.valueAt(i).prepForPrune();
        }
        int size2 = this.mCueBoxes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mCueBoxes.valueAt(i2).prepForPrune();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = this.mRegionBoxes.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mRegionBoxes.valueAt(i3).measureForParent(i, i2);
        }
        int size2 = this.mCueBoxes.size();
        for (int i4 = 0; i4 < size2; i4++) {
            this.mCueBoxes.valueAt(i4).measureForParent(i, i2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        setCaptionStyle(this.mCaptionStyle, this.mManager.getFontScale() * LINE_HEIGHT_RATIO * i6);
        int size = this.mRegionBoxes.size();
        for (int i7 = 0; i7 < size; i7++) {
            layoutRegion(i5, i6, this.mRegionBoxes.valueAt(i7));
        }
        int size2 = this.mCueBoxes.size();
        for (int i8 = 0; i8 < size2; i8++) {
            layoutCue(i5, i6, this.mCueBoxes.valueAt(i8));
        }
    }

    private void layoutRegion(int i, int i2, RegionLayout regionLayout) {
        TextTrackRegion region = regionLayout.getRegion();
        int measuredHeight = regionLayout.getMeasuredHeight();
        int measuredWidth = regionLayout.getMeasuredWidth();
        int i3 = (int) ((region.mViewportAnchorPointX * (i - measuredWidth)) / 100.0f);
        int i4 = (int) ((region.mViewportAnchorPointY * (i2 - measuredHeight)) / 100.0f);
        regionLayout.layout(i3, i4, measuredWidth + i3, measuredHeight + i4);
    }

    private void layoutCue(int i, int i2, CueLayout cueLayout) {
        int i3;
        TextTrackCue cue = cueLayout.getCue();
        int layoutDirection = getLayoutDirection();
        int resolveCueAlignment = resolveCueAlignment(layoutDirection, cue.mAlignment);
        boolean z = cue.mSnapToLines;
        int measuredWidth = (cueLayout.getMeasuredWidth() * 100) / i;
        if (resolveCueAlignment == 203) {
            i3 = cue.mTextPosition;
        } else if (resolveCueAlignment == 204) {
            i3 = cue.mTextPosition - measuredWidth;
        } else {
            i3 = cue.mTextPosition - (measuredWidth / 2);
        }
        if (layoutDirection == 1) {
            i3 = 100 - i3;
        }
        if (z) {
            int paddingLeft = (getPaddingLeft() * 100) / i;
            int paddingRight = (getPaddingRight() * 100) / i;
            if (i3 < paddingLeft && i3 + measuredWidth > paddingLeft) {
                i3 += paddingLeft;
                measuredWidth -= paddingLeft;
            }
            float f = 100 - paddingRight;
            if (i3 < f && i3 + measuredWidth > f) {
                measuredWidth -= paddingRight;
            }
        }
        int i4 = (i3 * i) / 100;
        int i5 = (measuredWidth * i) / 100;
        int calculateLinePosition = calculateLinePosition(cueLayout);
        int measuredHeight = cueLayout.getMeasuredHeight();
        int i6 = calculateLinePosition < 0 ? i2 + (calculateLinePosition * measuredHeight) : (calculateLinePosition * (i2 - measuredHeight)) / 100;
        cueLayout.layout(i4, i6, i5 + i4, measuredHeight + i6);
    }

    private int calculateLinePosition(CueLayout cueLayout) {
        TextTrackCue cue = cueLayout.getCue();
        Integer num = cue.mLinePosition;
        boolean z = cue.mSnapToLines;
        boolean z2 = num == null;
        if (!z && !z2 && (num.intValue() < 0 || num.intValue() > 100)) {
            return 100;
        }
        if (!z2) {
            return num.intValue();
        }
        if (z) {
            return -(cueLayout.mOrder + 1);
        }
        return 100;
    }

    /* compiled from: WebVttRenderer.java */
    private static class RegionLayout extends LinearLayout {
        private CaptioningManager.CaptionStyle mCaptionStyle;
        private float mFontSize;
        private final TextTrackRegion mRegion;
        private final ArrayList<CueLayout> mRegionCueBoxes;

        public RegionLayout(Context context, TextTrackRegion textTrackRegion, CaptioningManager.CaptionStyle captionStyle, float f) {
            super(context);
            this.mRegionCueBoxes = new ArrayList<>();
            this.mRegion = textTrackRegion;
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            setOrientation(1);
            setBackgroundColor(captionStyle.windowColor);
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int size = this.mRegionCueBoxes.size();
            for (int i = 0; i < size; i++) {
                this.mRegionCueBoxes.get(i).setCaptionStyle(captionStyle, f);
            }
            setBackgroundColor(captionStyle.windowColor);
        }

        public void measureForParent(int i, int i2) {
            TextTrackRegion textTrackRegion = this.mRegion;
            measure(View.MeasureSpec.makeMeasureSpec((((int) textTrackRegion.mWidth) * View.MeasureSpec.getSize(i)) / 100, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), Integer.MIN_VALUE));
        }

        public void prepForPrune() {
            int size = this.mRegionCueBoxes.size();
            for (int i = 0; i < size; i++) {
                this.mRegionCueBoxes.get(i).prepForPrune();
            }
        }

        public void put(TextTrackCue textTrackCue) {
            int size = this.mRegionCueBoxes.size();
            for (int i = 0; i < size; i++) {
                CueLayout cueLayout = this.mRegionCueBoxes.get(i);
                if (cueLayout.getCue() == textTrackCue) {
                    cueLayout.update();
                    return;
                }
            }
            CueLayout cueLayout2 = new CueLayout(getContext(), textTrackCue, this.mCaptionStyle, this.mFontSize);
            this.mRegionCueBoxes.add(cueLayout2);
            addView(cueLayout2, -2, -2);
            if (getChildCount() > this.mRegion.mLines) {
                removeViewAt(0);
            }
        }

        public boolean prune() {
            int size = this.mRegionCueBoxes.size();
            int i = 0;
            while (i < size) {
                CueLayout cueLayout = this.mRegionCueBoxes.get(i);
                if (!cueLayout.isActive()) {
                    this.mRegionCueBoxes.remove(i);
                    removeView(cueLayout);
                    size--;
                    i--;
                }
                i++;
            }
            return this.mRegionCueBoxes.isEmpty();
        }

        public TextTrackRegion getRegion() {
            return this.mRegion;
        }
    }

    /* compiled from: WebVttRenderer.java */
    private static class CueLayout extends LinearLayout {
        private boolean mActive;
        private CaptioningManager.CaptionStyle mCaptionStyle;
        public final TextTrackCue mCue;
        private float mFontSize;
        private int mOrder;

        public CueLayout(Context context, TextTrackCue textTrackCue, CaptioningManager.CaptionStyle captionStyle, float f) {
            super(context);
            this.mCue = textTrackCue;
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int i = textTrackCue.mWritingDirection == 100 ? 1 : 0;
            setOrientation(i);
            switch (textTrackCue.mAlignment) {
                case 200:
                    setGravity(i == 0 ? 16 : 1);
                    break;
                case 201:
                    setGravity(Gravity.START);
                    break;
                case 202:
                    setGravity(Gravity.END);
                    break;
                case 203:
                    setGravity(3);
                    break;
                case 204:
                    setGravity(5);
                    break;
            }
            update();
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof SpanLayout) {
                    ((SpanLayout) childAt).setCaptionStyle(captionStyle, f);
                }
            }
        }

        public void prepForPrune() {
            this.mActive = false;
        }

        public void update() {
            Layout.Alignment alignment;
            this.mActive = true;
            removeAllViews();
            int resolveCueAlignment = WebVttRenderingWidget.resolveCueAlignment(getLayoutDirection(), this.mCue.mAlignment);
            if (resolveCueAlignment == 203) {
                alignment = Layout.Alignment.ALIGN_LEFT;
            } else if (resolveCueAlignment == 204) {
                alignment = Layout.Alignment.ALIGN_RIGHT;
            } else {
                alignment = Layout.Alignment.ALIGN_CENTER;
            }
            CaptioningManager.CaptionStyle captionStyle = this.mCaptionStyle;
            float f = this.mFontSize;
            for (TextTrackCueSpan[] textTrackCueSpanArr : this.mCue.mLines) {
                SpanLayout spanLayout = new SpanLayout(getContext(), textTrackCueSpanArr);
                spanLayout.setAlignment(alignment);
                spanLayout.setCaptionStyle(captionStyle, f);
                addView(spanLayout, -2, -2);
            }
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }

        public void measureForParent(int i, int i2) {
            int i3;
            int i4;
            TextTrackCue textTrackCue = this.mCue;
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            int resolveCueAlignment = WebVttRenderingWidget.resolveCueAlignment(getLayoutDirection(), textTrackCue.mAlignment);
            if (resolveCueAlignment == 200) {
                if (textTrackCue.mTextPosition <= 50) {
                    i3 = textTrackCue.mTextPosition;
                } else {
                    i3 = 100 - textTrackCue.mTextPosition;
                }
                i4 = i3 * 2;
            } else if (resolveCueAlignment == 203) {
                i4 = 100 - textTrackCue.mTextPosition;
            } else {
                i4 = resolveCueAlignment != 204 ? 0 : textTrackCue.mTextPosition;
            }
            measure(View.MeasureSpec.makeMeasureSpec((Math.min(textTrackCue.mSize, i4) * size) / 100, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
        }

        public void setOrder(int i) {
            this.mOrder = i;
        }

        public boolean isActive() {
            return this.mActive;
        }

        public TextTrackCue getCue() {
            return this.mCue;
        }
    }

    /* compiled from: WebVttRenderer.java */
    private static class SpanLayout extends SubtitleView {
        private final SpannableStringBuilder mBuilder;
        private final TextTrackCueSpan[] mSpans;

        public SpanLayout(Context context, TextTrackCueSpan[] textTrackCueSpanArr) {
            super(context);
            this.mBuilder = new SpannableStringBuilder();
            this.mSpans = textTrackCueSpanArr;
            update();
        }

        public void update() {
            SpannableStringBuilder spannableStringBuilder = this.mBuilder;
            TextTrackCueSpan[] textTrackCueSpanArr = this.mSpans;
            spannableStringBuilder.clear();
            spannableStringBuilder.clearSpans();
            int length = textTrackCueSpanArr.length;
            for (int i = 0; i < length; i++) {
                if (textTrackCueSpanArr[i].mEnabled) {
                    spannableStringBuilder.append((CharSequence) textTrackCueSpanArr[i].mText);
                }
            }
            setText(spannableStringBuilder);
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
            setBackgroundColor(captionStyle.backgroundColor);
            setForegroundColor(captionStyle.foregroundColor);
            setEdgeColor(captionStyle.edgeColor);
            setEdgeType(captionStyle.edgeType);
            setTypeface(captionStyle.getTypeface());
            setTextSize(f);
        }
    }
}
