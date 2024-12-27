package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

public final class VideoCallMicModeBar extends BarItemImpl {
    public float fontScale;
    public final Sequence items;
    public final ConfigurationState lastConfigurationState;
    public int orientation;
    public LinearLayout slotButtonGroup;
    public final VideoCallMicModeUtil util;

    public interface VideoCallMicModeBarBase {
        void fini();

        View getButton();

        void inflate(View view);

        void init();

        boolean isEnabled();

        void setClickListener(Function1 function1);

        void updateContents();

        void updateFontScale();

        void updateHeightMargins(boolean z, VideoCallMicModeStates videoCallMicModeStates, VideoCallMicModeResources videoCallMicModeResources);

        void updateVisibilities(VideoCallMicModeStates videoCallMicModeStates);
    }

    public VideoCallMicModeBar(Context context, SettingsHelper settingsHelper, SecQSPanelResourcePicker secQSPanelResourcePicker, PanelInteractor panelInteractor, SecQSDetailController secQSDetailController) {
        super(context);
        VideoCallMicModeUtil videoCallMicModeUtil = new VideoCallMicModeUtil(context, secQSPanelResourcePicker, new Function0() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$util$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return VideoCallMicModeBar.this.mBGColorHelper;
            }
        });
        this.util = videoCallMicModeUtil;
        this.items = SequencesKt__SequencesKt.sequenceOf(new VideoCallEffect(videoCallMicModeUtil, context, panelInteractor, new Runnable() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$items$1
            @Override // java.lang.Runnable
            public final void run() {
                VideoCallMicModeBar.this.updateBarVisibilities$1();
            }
        }), new VoIPTranslator(videoCallMicModeUtil, context, settingsHelper, panelInteractor, new Runnable() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$items$2
            @Override // java.lang.Runnable
            public final void run() {
                VideoCallMicModeBar.this.updateBarVisibilities$1();
            }
        }), new MicMode(videoCallMicModeUtil, context, settingsHelper, new Runnable() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$items$3
            @Override // java.lang.Runnable
            public final void run() {
                VideoCallMicModeBar.this.updateBarVisibilities$1();
            }
        }, new Runnable() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$items$4
            @Override // java.lang.Runnable
            public final void run() {
                Iterator it = VideoCallMicModeBar.this.items.iterator();
                while (it.hasNext()) {
                    ((VideoCallMicModeBar.VideoCallMicModeBarBase) it.next()).updateContents();
                }
            }
        }, secQSDetailController));
        this.lastConfigurationState = new ConfigurationState(CollectionsKt__CollectionsKt.listOf(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        Iterator it = this.items.iterator();
        while (it.hasNext()) {
            ((VideoCallMicModeBarBase) it.next()).fini();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_video_call_mic_mode_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View inflate = this.util.inflate(R.layout.sec_video_call_mic_mode_bar, viewGroup, false);
        LinearLayout linearLayout = null;
        Sequence sequence = this.items;
        if (inflate != null) {
            Iterator it = sequence.iterator();
            while (it.hasNext()) {
                ((VideoCallMicModeBarBase) it.next()).inflate(inflate);
            }
            LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.slot_button_group);
            if (linearLayout2 != null) {
                FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filterNotNull(new TransformingSequence(sequence, new Function1() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$inflateSlotButtonGroup$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((VideoCallMicModeBar.VideoCallMicModeBarBase) obj).getButton();
                    }
                })));
                while (filteringSequence$iterator$1.hasNext()) {
                    linearLayout2.addView((View) filteringSequence$iterator$1.next());
                }
                linearLayout = linearLayout2;
            }
            this.slotButtonGroup = linearLayout;
        } else {
            inflate = null;
        }
        this.mBarRootView = inflate;
        Iterator it2 = sequence.iterator();
        while (it2.hasNext()) {
            ((VideoCallMicModeBarBase) it2.next()).init();
        }
        Iterator it3 = sequence.iterator();
        while (it3.hasNext()) {
            ((VideoCallMicModeBarBase) it3.next()).setClickListener(VideoCallMicModeBar$setClickListeners$1$1.INSTANCE);
        }
        updateBarVisibilities$1();
        Iterator it4 = sequence.iterator();
        while (it4.hasNext()) {
            ((VideoCallMicModeBarBase) it4.next()).updateContents();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        if (this.mBarRootView == null) {
            return;
        }
        int i = this.mContext.getResources().getConfiguration().orientation;
        float f = this.mContext.getResources().getConfiguration().fontScale;
        ConfigurationState configurationState = this.lastConfigurationState;
        if (!configurationState.needToUpdate(configuration) && this.orientation == i && this.fontScale == f) {
            return;
        }
        this.orientation = i;
        this.fontScale = f;
        updateHeightMargins();
        configurationState.update(configuration);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        if (z) {
            updateHeightMargins();
        }
    }

    public final void updateBarVisibilities$1() {
        VideoCallMicModeBar$updateBarVisibilities$1 videoCallMicModeBar$updateBarVisibilities$1 = new Function1() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$updateBarVisibilities$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((VideoCallMicModeBar.VideoCallMicModeBarBase) obj).isEnabled());
            }
        };
        Sequence sequence = this.items;
        List list = SequencesKt___SequencesKt.toList(new TransformingSequence(sequence, videoCallMicModeBar$updateBarVisibilities$1));
        boolean booleanValue = ((Boolean) list.get(0)).booleanValue();
        boolean booleanValue2 = ((Boolean) list.get(1)).booleanValue();
        boolean booleanValue3 = ((Boolean) list.get(2)).booleanValue();
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateBarVisibilities: videoCall: ", " micMode: ", " voIPTranslator: ", booleanValue, booleanValue3);
        m.append(booleanValue2);
        Log.d(this.TAG, m.toString());
        VideoCallMicModeStates videoCallMicModeStates = new VideoCallMicModeStates(booleanValue, booleanValue2, booleanValue3);
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            ((VideoCallMicModeBarBase) it.next()).updateVisibilities(videoCallMicModeStates);
        }
        showBar(booleanValue || booleanValue3 || booleanValue2);
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        if (this.mBarRootView == null) {
            return;
        }
        boolean z = this.orientation == 2 && !QpRune.QUICK_TABLET;
        VideoCallMicModeBar$updateHeightMargins$1 videoCallMicModeBar$updateHeightMargins$1 = new Function1() { // from class: com.android.systemui.qs.bar.VideoCallMicModeBar$updateHeightMargins$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((VideoCallMicModeBar.VideoCallMicModeBarBase) obj).isEnabled());
            }
        };
        Sequence sequence = this.items;
        List list = SequencesKt___SequencesKt.toList(new TransformingSequence(sequence, videoCallMicModeBar$updateHeightMargins$1));
        VideoCallMicModeStates videoCallMicModeStates = new VideoCallMicModeStates(((Boolean) list.get(0)).booleanValue(), ((Boolean) list.get(1)).booleanValue(), ((Boolean) list.get(2)).booleanValue());
        VideoCallMicModeUtil videoCallMicModeUtil = this.util;
        VideoCallMicModeResources resources = videoCallMicModeUtil.getResources();
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            ((VideoCallMicModeBarBase) it.next()).updateHeightMargins(z, videoCallMicModeStates, resources);
        }
        Iterator it2 = sequence.iterator();
        while (it2.hasNext()) {
            ((VideoCallMicModeBarBase) it2.next()).updateFontScale();
        }
        LinearLayout linearLayout = this.slotButtonGroup;
        if (linearLayout == null) {
            return;
        }
        linearLayout.setDividerPadding(videoCallMicModeUtil.getResources().dividerPadding);
    }
}
