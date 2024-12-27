package com.samsung.vekit.Item;

import android.util.Log;

import com.samsung.vekit.Common.Object.PVDetectionInfo;
import com.samsung.vekit.Common.Object.PVFrameInfo;
import com.samsung.vekit.Common.Object.PVKeyFrame;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Listener.PortraitVideoStatusListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class PortraitVideoItem extends VideoItem implements PortraitVideoStatusListener {
    int blurStrength;
    PVFrameInfo frameInfo;
    ArrayList<PVKeyFrame> keyFrameList;
    PortraitVideoStatusListener portraitVideoStatusListener;

    public PortraitVideoItem(VEContext context, int id, String name) {
        super(context, id, name);
        this.itemType = ItemType.PORTRAIT_VIDEO;
        this.keyFrameList = new ArrayList<>();
    }

    public void setPortraitVideoStatusListener(PortraitVideoStatusListener listener) {
        this.portraitVideoStatusListener = listener;
    }

    public PortraitVideoStatusListener getPortraitVideoStatusListener() {
        return this.portraitVideoStatusListener;
    }

    public void changePortraitVideoFocus(PVDetectionInfo detectionInfo) {
        this.context.getNativeInterface().changePortraitVideoFocus(this, detectionInfo);
    }

    public void changePortraitVideoFocus(int focusX, int focusY) {
        this.context.getNativeInterface().changePortraitVideoFocus(this, focusX, focusY);
    }

    public void changePortraitVideoKeyFrame(PVKeyFrame keyFrame) {
        this.context.getNativeInterface().changePortraitVideoKeyFrame(this, keyFrame);
    }

    public void changePortraitVideoKeyFrameList(ArrayList<PVKeyFrame> keyFrameList) {
        this.context.getNativeInterface().changePortraitVideoKeyFrameList(this, keyFrameList);
    }

    public PVFrameInfo getFrameInfo() {
        return this.frameInfo;
    }

    public void setFrameInfo(PVFrameInfo frameInfo) {
        this.frameInfo = frameInfo;
    }

    public List<PVKeyFrame> getKeyFrameList() {
        return Collections.unmodifiableList(this.keyFrameList);
    }

    public void setKeyFrameList(ArrayList<PVKeyFrame> keyFrameList) {
        this.keyFrameList = keyFrameList;
    }

    public int getBlurStrength() {
        return this.blurStrength;
    }

    public void setBlurStrength(int blurStrength) {
        this.blurStrength = blurStrength;
    }

    public void deleteKeyFrame(int keyFrameId) {
        this.context.getNativeInterface().deletePortraitVideoKeyFrame(this, keyFrameId);
    }

    @Override // com.samsung.vekit.Item.VideoItem, com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.PORTRAIT_VIDEO) {
            throw new Exception("isInvalidElement : please set portraitVideo(content).");
        }
    }

    @Override // com.samsung.vekit.Listener.PortraitVideoStatusListener
    public void onPortraitVideoKeyFrameUpdated(ArrayList<PVKeyFrame> keyFrameList) {
        Log.i(this.TAG, "onPortraitVideoKeyFrameUpdated -> ItemId : " + this.id);
        if (this.portraitVideoStatusListener != null) {
            this.portraitVideoStatusListener.onPortraitVideoKeyFrameUpdated(keyFrameList);
        }
    }

    @Override // com.samsung.vekit.Listener.PortraitVideoStatusListener
    public void onPortraitVideoFrameInfoUpdated(PVFrameInfo frameInfo) {
        Log.i(this.TAG, "onPortraitVideoFrameInfoUpdated -> ItemId : " + this.id);
        this.blurStrength = frameInfo.getBlurLevel();
        if (this.portraitVideoStatusListener != null) {
            this.portraitVideoStatusListener.onPortraitVideoFrameInfoUpdated(frameInfo);
        }
    }

    @Override // com.samsung.vekit.Listener.PortraitVideoStatusListener
    public void onPortraitVideoError(int requestType) {
        Log.i(
                this.TAG,
                "onPortraitVideoError -> ItemId : " + this.id + ", requestType : " + requestType);
        if (this.portraitVideoStatusListener != null) {
            this.portraitVideoStatusListener.onPortraitVideoError(requestType);
        }
    }
}
