package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import androidx.leanback.widget.SearchOrbView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SpeechOrbView extends SearchOrbView {
    public int mCurrentLevel;
    public boolean mListening;
    public final SearchOrbView.Colors mListeningOrbColors;
    public final SearchOrbView.Colors mNotListeningOrbColors;
    public final float mSoundLevelMaxZoom;

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
        this.mCurrentLevel = 0;
        this.mListening = false;
        Resources resources = context.getResources();
        this.mSoundLevelMaxZoom = resources.getFraction(R.fraction.lb_search_bar_speech_orb_max_level_zoom, 1, 1);
        SearchOrbView.Colors colors = new SearchOrbView.Colors(resources.getColor(R.color.lb_speech_orb_not_recording), resources.getColor(R.color.lb_speech_orb_not_recording_pulsed), resources.getColor(R.color.lb_speech_orb_not_recording_icon));
        this.mNotListeningOrbColors = colors;
        this.mListeningOrbColors = new SearchOrbView.Colors(resources.getColor(R.color.lb_speech_orb_recording), resources.getColor(R.color.lb_speech_orb_recording), 0);
        setOrbColors(colors);
        this.mIcon.setImageDrawable(getResources().getDrawable(R.drawable.lb_ic_search_mic_out));
        animateOnFocus(hasFocus());
        this.mSearchOrbView.setScaleX(1.0f);
        this.mSearchOrbView.setScaleY(1.0f);
        this.mListening = false;
    }
}
