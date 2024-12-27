package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class FragmentAudio extends Content {
    private ArrayList<Long> bodyDurationList;
    private ArrayList<String> bodyPathList;
    private long introDuration;
    private String introPath;
    private long outroDuration;
    private String outroPath;

    public FragmentAudio(VEContext context, int id, String name) {
        super(context, ContentType.FRAGMENT_AUDIO, id, name);
        this.bodyPathList = new ArrayList<>();
        this.bodyDurationList = new ArrayList<>();
    }

    public FragmentAudio setPaths(
            String introPath, ArrayList<String> bodyPathList, String outroPath) {
        this.introPath = introPath;
        this.bodyPathList = bodyPathList;
        this.outroPath = outroPath;
        return this;
    }

    public FragmentAudio setDurations(
            long introDuration, ArrayList<Long> bodyDurationList, long outroDuration) {
        this.introDuration = introDuration;
        this.bodyDurationList = bodyDurationList;
        this.outroDuration = outroDuration;
        return this;
    }

    public String getIntroPath() {
        return this.introPath;
    }

    public String getOutroPath() {
        return this.outroPath;
    }

    public List<String> getBodyPathList() {
        return Collections.unmodifiableList(this.bodyPathList);
    }

    public long getIntroDuration() {
        return this.introDuration;
    }

    public long getOutroDuration() {
        return this.outroDuration;
    }

    public List<Long> getBodyDurationList() {
        return Collections.unmodifiableList(this.bodyDurationList);
    }
}
