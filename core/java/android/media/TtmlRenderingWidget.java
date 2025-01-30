package android.media;

import android.content.Context;
import android.graphics.Color;
import android.media.SubtitleTrack;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Vector;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlRenderingWidget extends LinearLayout implements SubtitleTrack.RenderingWidget {
    private static final float LINE_HEIGHT_RATIO = 0.0533f;
    private CaptioningManager mCaptionManager;
    private CaptioningManager.CaptionStyle mCaptionStyle;
    private SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private LinkedList<CustomTextView> mTextViewSet;

    public TtmlRenderingWidget(Context context) {
        this(context, null);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mTextViewSet = new LinkedList<>();
        setLayerType(1, null);
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService(Context.CAPTIONING_SERVICE);
        this.mCaptionManager = captioningManager;
        this.mCaptionStyle = captioningManager.getUserStyle();
        CustomTextView customTextView = new CustomTextView(context);
        customTextView.setGravity(81);
        this.mTextViewSet.addLast(customTextView);
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setOnChangedListener(SubtitleTrack.RenderingWidget.OnChangedListener listener) {
        this.mListener = listener;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setSize(int width, int height) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height, 1073741824);
        measure(widthSpec, heightSpec);
        layout(0, 0, width, height);
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setVisible(boolean visible) {
        if (visible) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public int applyOpacity(int color, int opacity) {
        return Color.argb((opacity * 255) / 100, Color.red(color), Color.green(color), Color.blue(color));
    }

    public void setActiveCues(Vector<SubtitleTrack.Cue> activeCues) {
        removeAllViews();
        int viewCount = this.mTextViewSet.size();
        for (int i = 0; i < viewCount - 1; i++) {
            this.mTextViewSet.removeLast();
        }
        int count = activeCues.size();
        String subtitleText = "";
        for (int i2 = 0; i2 < count; i2++) {
            TtmlCue cue = (TtmlCue) activeCues.get(i2);
            if (i2 > 0) {
                subtitleText = subtitleText + "\n";
            }
            String text = TtmlUtils.applySpacePolicy(cue.mText, true);
            subtitleText = (subtitleText + text) + "\n";
            Spannable textSpan = new SpannableString(subtitleText);
            int color = this.mCaptionStyle.backgroundColor;
            float fontSize = this.mCaptionManager.getFontScale() * getHeight() * LINE_HEIGHT_RATIO;
            textSpan.setSpan(new BackgroundColorSpan(color), 0, subtitleText.length(), 33);
            this.mTextViewSet.get(0).setGravity(81);
            this.mTextViewSet.get(0).setText(textSpan);
            this.mTextViewSet.get(0).setTextSize(fontSize);
            float tmpTextSize = this.mTextViewSet.get(0).getTextSize();
            this.mTextViewSet.get(0).setTextSize((fontSize * fontSize) / tmpTextSize);
            this.mTextViewSet.get(0).setTextColor(this.mCaptionStyle.foregroundColor);
            addView(this.mTextViewSet.get(0), -1, -1);
        }
        int i3 = getWidth();
        setSize(i3, getHeight());
        SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener = this.mListener;
        if (onChangedListener != null) {
            onChangedListener.onChanged(this);
        }
    }

    /* compiled from: TtmlRenderer.java */
    private static class CustomTextView extends TextView {
        public CustomTextView(Context context) {
            super(context);
        }

        public void setSize(int width, int height) {
            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, 1073741824);
            measure(widthSpec, heightSpec);
            layout(0, 0, width, height);
        }
    }
}
