package com.android.systemui.volume.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewGroupKt$children$1;
import com.android.systemui.R;
import com.android.systemui.volume.view.standard.VolumePanelView;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumePanelViewExt {
    static {
        new VolumePanelViewExt();
    }

    private VolumePanelViewExt() {
    }

    public static final boolean isIconClickWillConsume(VolumePanelView volumePanelView, MotionEvent motionEvent) {
        TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(new ViewGroupKt$children$1((ViewGroup) volumePanelView.findViewById(R.id.volume_panel_row_container)), new Function1() { // from class: com.android.systemui.volume.view.VolumePanelViewExt$isIconClickWillConsume$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return (VolumeRowView) ((View) obj);
            }
        }));
        while (transformingSequence$iterator$1.hasNext()) {
            if (((VolumeRowView) transformingSequence$iterator$1.next()).isIconClicked(motionEvent.getRawX(), motionEvent.getRawY())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isIconClickWillConsume(SubFullLayoutVolumePanelView subFullLayoutVolumePanelView, MotionEvent motionEvent) {
        TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(new ViewGroupKt$children$1((ViewGroup) subFullLayoutVolumePanelView.findViewById(R.id.volume_panel_row_container)), new Function1() { // from class: com.android.systemui.volume.view.VolumePanelViewExt$isIconClickWillConsume$3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return (SubFullLayoutVolumeRowView) ((View) obj);
            }
        }));
        while (transformingSequence$iterator$1.hasNext()) {
            if (((SubFullLayoutVolumeRowView) transformingSequence$iterator$1.next()).isIconClicked(motionEvent.getRawX(), motionEvent.getRawY())) {
                return true;
            }
        }
        return false;
    }
}
