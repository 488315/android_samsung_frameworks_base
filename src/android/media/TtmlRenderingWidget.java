package android.media;

import android.content.Context;
import android.graphics.Color;
import android.media.SubtitleTrack;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
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

    public TtmlRenderingWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
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
    public void setOnChangedListener(SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener) {
        this.mListener = onChangedListener;
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setSize(int i, int i2) {
        measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        layout(0, 0, i, i2);
    }

    @Override // android.media.SubtitleTrack.RenderingWidget
    public void setVisible(boolean z) {
        if (z) {
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
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public int applyOpacity(int i, int i2) {
        return Color.argb((i2 * 255) / 100, Color.red(i), Color.green(i), Color.blue(i));
    }

    public void setActiveCues(Vector<SubtitleTrack.Cue> vector) {
        removeAllViews();
        int size = this.mTextViewSet.size();
        for (int i = 0; i < size - 1; i++) {
            this.mTextViewSet.removeLast();
        }
        int size2 = vector.size();
        String str = "";
        for (int i2 = 0; i2 < size2; i2++) {
            TtmlCue ttmlCue = (TtmlCue) vector.get(i2);
            if (i2 > 0) {
                str = str + ShaderAssembler.NEWLINE;
            }
            str = (str + TtmlUtils.applySpacePolicy(ttmlCue.mText, true)) + ShaderAssembler.NEWLINE;
            SpannableString spannableString = new SpannableString(str);
            int i3 = this.mCaptionStyle.backgroundColor;
            float fontScale = this.mCaptionManager.getFontScale() * getHeight() * LINE_HEIGHT_RATIO;
            spannableString.setSpan(new BackgroundColorSpan(i3), 0, str.length(), 33);
            this.mTextViewSet.get(0).setGravity(81);
            this.mTextViewSet.get(0).lambda$setTextAsync$0(spannableString);
            this.mTextViewSet.get(0).setTextSize(fontScale);
            this.mTextViewSet.get(0).setTextSize((fontScale * fontScale) / this.mTextViewSet.get(0).getTextSize());
            this.mTextViewSet.get(0).setTextColor(this.mCaptionStyle.foregroundColor);
            addView(this.mTextViewSet.get(0), -1, -1);
        }
        setSize(getWidth(), getHeight());
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

        public void setSize(int i, int i2) {
            measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
            layout(0, 0, i, i2);
        }
    }
}
