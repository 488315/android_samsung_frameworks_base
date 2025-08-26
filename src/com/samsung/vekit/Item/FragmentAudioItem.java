package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.FragmentAudio;
import com.samsung.vekit.Layer.Layer;
import java.util.List;

/* loaded from: classes6.dex */
public class FragmentAudioItem extends Item {
    public static final int TARGET_DURATION_GAP = 2000;
    private int bodyFragmentCount;
    private boolean enableAnimation;
    private boolean enableAutoDuration;
    private boolean enableOutro;
    private int volume;

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.FRAGMENT_AUDIO) {
            throw new Exception("isInvalidElement : please set fragment_audio(content).");
        }
    }

    public FragmentAudioItem(VEContext vEContext, int i, String str) {
        super(vEContext, ItemType.FRAGMENT_AUDIO, i, str);
        this.volume = 100;
        this.enableOutro = true;
        this.enableAnimation = false;
        this.enableAutoDuration = true;
        this.bodyFragmentCount = 0;
    }

    @Override // com.samsung.vekit.Item.Item
    public FragmentAudioItem setParent(Layer layer) {
        return (FragmentAudioItem) super.setParent(layer);
    }

    @Override // com.samsung.vekit.Item.Item
    public FragmentAudioItem setContent(Content content) {
        try {
            checkValidContent(content);
            super.setContent(content);
            if (this.duration != 0) {
                updateOptions();
            }
            return this;
        } catch (Exception e) {
            Log.e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public FragmentAudioItem setPadding(long j) {
        return (FragmentAudioItem) super.setPadding(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public FragmentAudioItem setDuration(long j) {
        super.setDuration(j);
        updateOptions();
        return this;
    }

    public FragmentAudioItem setEnableOutro(boolean z) {
        this.enableOutro = z;
        return this;
    }

    public boolean isEnableOutro() {
        return this.enableOutro;
    }

    public FragmentAudioItem setEnableAnimation(boolean z) {
        this.enableAnimation = z;
        return this;
    }

    public boolean getEnableAnimation() {
        return this.enableAnimation;
    }

    public FragmentAudioItem setEnableAutoDuration(boolean z) {
        this.enableAutoDuration = z;
        return this;
    }

    public boolean isEnableAutoDuration() {
        return this.enableAutoDuration;
    }

    public int getVolume() {
        return this.volume;
    }

    public FragmentAudioItem setVolume(int i) {
        this.volume = i;
        return this;
    }

    private void updateOptions() {
        if (this.content == null) {
            Log.e(this.TAG, "Content is null.");
            return;
        }
        long jCalculateContentDuration = calculateContentDuration();
        long j = this.duration - jCalculateContentDuration;
        if (jCalculateContentDuration <= 0) {
            Log.e(this.TAG, "contentDuration is 0 or negative");
            return;
        }
        if (Math.abs(j) <= 2000) {
            this.enableOutro = true;
            this.enableAnimation = j < 0;
        } else {
            appendBodyCount(jCalculateContentDuration);
            this.enableOutro = false;
            this.enableAnimation = true;
        }
        Log.d(this.TAG, "updateOptions() => duration : " + this.duration + " enableOutro : " + this.enableOutro + " enableAnimation : " + this.enableAnimation);
    }

    private void appendBodyCount(long j) {
        FragmentAudio fragmentAudio = (FragmentAudio) this.content;
        long outroDuration = this.duration - (j - fragmentAudio.getOutroDuration());
        if (outroDuration < 0) {
            return;
        }
        List<Long> bodyDurationList = fragmentAudio.getBodyDurationList();
        int size = bodyDurationList.size();
        while (outroDuration > 0) {
            int i = this.bodyFragmentCount + 1;
            this.bodyFragmentCount = i;
            outroDuration -= bodyDurationList.get(i % size).longValue();
        }
    }

    private long calculateContentDuration() {
        FragmentAudio fragmentAudio = (FragmentAudio) this.content;
        long introDuration = fragmentAudio.getIntroDuration() + fragmentAudio.getOutroDuration();
        List<Long> bodyDurationList = fragmentAudio.getBodyDurationList();
        int size = bodyDurationList.size();
        if (size == 0) {
            return introDuration;
        }
        long jAbs = Math.abs(this.duration - introDuration);
        int i = 0;
        this.bodyFragmentCount = 0;
        while (Math.abs(this.duration - (bodyDurationList.get(i).longValue() + introDuration)) < jAbs) {
            introDuration += bodyDurationList.get(i).longValue();
            jAbs = Math.abs(this.duration - introDuration);
            i = (i + 1) % size;
            this.bodyFragmentCount++;
        }
        Log.d(this.TAG, "contentDuration is " + introDuration);
        return introDuration;
    }
}
