package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import androidx.leanback.widget.SearchOrbView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SpeechOrbView extends SearchOrbView {
    public final SearchOrbView.Colors mListeningOrbColors;
    public final SearchOrbView.Colors mNotListeningOrbColors;

    public SpeechOrbView(Context context) {
        this(context, null);
    }

    @Override // androidx.leanback.widget.SearchOrbView
    public final int getLayoutResourceId() {
        return R.layout.lb_speech_orb;
    }

    public SpeechOrbView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SpeechOrbView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Resources resources = context.getResources();
        resources.getFraction(R.fraction.lb_search_bar_speech_orb_max_level_zoom, 1, 1);
        this.mNotListeningOrbColors = new SearchOrbView.Colors(resources.getColor(R.color.lb_speech_orb_not_recording), resources.getColor(R.color.lb_speech_orb_not_recording_pulsed), resources.getColor(R.color.lb_speech_orb_not_recording_icon));
        this.mListeningOrbColors = new SearchOrbView.Colors(resources.getColor(R.color.lb_speech_orb_recording), resources.getColor(R.color.lb_speech_orb_recording), 0);
        setOrbColors(this.mNotListeningOrbColors);
        this.mIcon.setImageDrawable(getResources().getDrawable(R.drawable.lb_ic_search_mic_out));
        animateOnFocus(hasFocus());
        this.mSearchOrbView.setScaleX(1.0f);
        this.mSearchOrbView.setScaleY(1.0f);
    }
}
