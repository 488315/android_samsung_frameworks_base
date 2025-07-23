package com.samsung.vekit.Common.Object;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class AudioSegment {
    private boolean enable;
    private String key;
    private ArrayList<AudioRegion> regionList;

    public AudioSegment(String str) {
        this.enable = true;
        this.regionList = new ArrayList<>();
        this.key = str;
    }

    public AudioSegment(String str, ArrayList<AudioRegion> arrayList) {
        this.enable = true;
        new ArrayList();
        this.key = str;
        this.regionList = arrayList;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AudioSegment m9804clone() {
        AudioSegment audioSegment = new AudioSegment(this.key);
        audioSegment.enable = this.enable;
        Iterator<AudioRegion> it = this.regionList.iterator();
        while (it.hasNext()) {
            audioSegment.addRegion(it.next().m9803clone());
        }
        return audioSegment;
    }

    public ArrayList<AudioRegion> getRegionList() {
        return this.regionList;
    }

    public void setRegionList(ArrayList<AudioRegion> arrayList) {
        this.regionList = arrayList;
    }

    public int getRegionListSize() {
        return this.regionList.size();
    }

    public AudioRegion getRegion(int i) {
        try {
            return this.regionList.get(i);
        } catch (IndexOutOfBoundsException unused) {
            Log.e("AudioSegment", "invalid index.");
            return null;
        }
    }

    public void addRegion(AudioRegion audioRegion) {
        this.regionList.add(audioRegion);
    }

    public void removeRegion(AudioRegion audioRegion) {
        this.regionList.remove(audioRegion);
    }

    public void clearRegion() {
        this.regionList.clear();
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }
}
