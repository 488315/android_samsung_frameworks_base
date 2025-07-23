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

    public FragmentAudio(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.FRAGMENT_AUDIO, i, str);
        this.bodyPathList = new ArrayList<>();
        this.bodyDurationList = new ArrayList<>();
    }

    public FragmentAudio setPaths(String str, ArrayList<String> arrayList, String str2) {
        this.introPath = str;
        this.bodyPathList = arrayList;
        this.outroPath = str2;
        return this;
    }

    public FragmentAudio setDurations(long j, ArrayList<Long> arrayList, long j2) {
        this.introDuration = j;
        this.bodyDurationList = arrayList;
        this.outroDuration = j2;
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
