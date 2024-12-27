package com.samsung.vekit.Common.Object;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class AudioSegment {
    private boolean enable;
    private String key;
    private ArrayList<AudioRegion> regionList;

    public AudioSegment(String key) {
        this.enable = true;
        this.regionList = new ArrayList<>();
        this.key = key;
    }

    public AudioSegment(String key, ArrayList<AudioRegion> regionList) {
        this.enable = true;
        this.regionList = new ArrayList<>();
        this.key = key;
        this.regionList = regionList;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AudioSegment m9384clone() {
        AudioSegment clonedAudioSegment = new AudioSegment(this.key);
        clonedAudioSegment.enable = this.enable;
        Iterator<AudioRegion> it = this.regionList.iterator();
        while (it.hasNext()) {
            AudioRegion region = it.next();
            clonedAudioSegment.addRegion(region.m9383clone());
        }
        return clonedAudioSegment;
    }

    public ArrayList<AudioRegion> getRegionList() {
        return this.regionList;
    }

    public void setRegionList(ArrayList<AudioRegion> regionList) {
        this.regionList = regionList;
    }

    public int getRegionListSize() {
        return this.regionList.size();
    }

    public AudioRegion getRegion(int index) {
        try {
            return this.regionList.get(index);
        } catch (IndexOutOfBoundsException e) {
            Log.e("AudioSegment", "invalid index.");
            return null;
        }
    }

    public void addRegion(AudioRegion region) {
        this.regionList.add(region);
    }

    public void removeRegion(AudioRegion region) {
        this.regionList.remove(region);
    }

    public void clearRegion() {
        this.regionList.clear();
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
