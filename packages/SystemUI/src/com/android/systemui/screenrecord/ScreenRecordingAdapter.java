package com.android.systemui.screenrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenRecordingAdapter extends ArrayAdapter {
    public final LinearLayout mSelectedInternal;
    public final LinearLayout mSelectedMic;
    public final LinearLayout mSelectedMicAndInternal;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.screenrecord.ScreenRecordingAdapter$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$screenrecord$ScreenRecordingAudioSource;

        static {
            int[] iArr = new int[ScreenRecordingAudioSource.values().length];
            $SwitchMap$com$android$systemui$screenrecord$ScreenRecordingAudioSource = iArr;
            try {
                iArr[ScreenRecordingAudioSource.INTERNAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$screenrecord$ScreenRecordingAudioSource[ScreenRecordingAudioSource.MIC_AND_INTERNAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$screenrecord$ScreenRecordingAudioSource[ScreenRecordingAudioSource.MIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public ScreenRecordingAdapter(Context context, int i, List<ScreenRecordingAudioSource> list) {
        super(context, i, list);
        this.mSelectedInternal = getSelected(R.string.screenrecord_device_audio_label);
        this.mSelectedMic = getSelected(R.string.screenrecord_mic_label);
        this.mSelectedMicAndInternal = getSelected(R.string.screenrecord_device_audio_and_mic_label);
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        int i2 = AnonymousClass1.$SwitchMap$com$android$systemui$screenrecord$ScreenRecordingAudioSource[((ScreenRecordingAudioSource) getItem(i)).ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? super.getDropDownView(i, view, viewGroup) : getOption(R.string.screenrecord_mic_label, 0) : getOption(R.string.screenrecord_device_audio_and_mic_label, 0) : getOption(R.string.screenrecord_device_audio_label, R.string.screenrecord_device_audio_description);
    }

    public final LinearLayout getOption(int i, int i2) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.screen_record_dialog_audio_source, (ViewGroup) null, false);
        ((TextView) linearLayout.findViewById(R.id.screen_recording_dialog_source_text)).setText(i);
        TextView textView = (TextView) linearLayout.findViewById(R.id.screen_recording_dialog_source_description);
        if (i2 != 0) {
            textView.setText(i2);
        } else {
            textView.setVisibility(8);
        }
        return linearLayout;
    }

    public final LinearLayout getSelected(int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.screen_record_dialog_audio_source_selected, (ViewGroup) null, false);
        ((TextView) linearLayout.findViewById(R.id.screen_recording_dialog_source_text)).setText(i);
        return linearLayout;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        int i2 = AnonymousClass1.$SwitchMap$com$android$systemui$screenrecord$ScreenRecordingAudioSource[((ScreenRecordingAudioSource) getItem(i)).ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? super.getView(i, view, viewGroup) : this.mSelectedMic : this.mSelectedMicAndInternal : this.mSelectedInternal;
    }
}
