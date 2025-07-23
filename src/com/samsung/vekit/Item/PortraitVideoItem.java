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

    public PortraitVideoItem(VEContext vEContext, int i, String str) {
        super(vEContext, i, str);
        this.itemType = ItemType.PORTRAIT_VIDEO;
        this.keyFrameList = new ArrayList<>();
    }

    public void setPortraitVideoStatusListener(PortraitVideoStatusListener portraitVideoStatusListener) {
        this.portraitVideoStatusListener = portraitVideoStatusListener;
    }

    public PortraitVideoStatusListener getPortraitVideoStatusListener() {
        return this.portraitVideoStatusListener;
    }

    public void changePortraitVideoFocus(PVDetectionInfo pVDetectionInfo) {
        this.context.getNativeInterface().changePortraitVideoFocus(this, pVDetectionInfo);
    }

    public void changePortraitVideoFocus(int i, int i2) {
        this.context.getNativeInterface().changePortraitVideoFocus(this, i, i2);
    }

    public void changePortraitVideoKeyFrame(PVKeyFrame pVKeyFrame) {
        this.context.getNativeInterface().changePortraitVideoKeyFrame(this, pVKeyFrame);
    }

    public void changePortraitVideoKeyFrameList(ArrayList<PVKeyFrame> arrayList) {
        this.context.getNativeInterface().changePortraitVideoKeyFrameList(this, arrayList);
    }

    public PVFrameInfo getFrameInfo() {
        return this.frameInfo;
    }

    public void setFrameInfo(PVFrameInfo pVFrameInfo) {
        this.frameInfo = pVFrameInfo;
    }

    public List<PVKeyFrame> getKeyFrameList() {
        return Collections.unmodifiableList(this.keyFrameList);
    }

    public void setKeyFrameList(ArrayList<PVKeyFrame> arrayList) {
        this.keyFrameList = arrayList;
    }

    public int getBlurStrength() {
        return this.blurStrength;
    }

    public void setBlurStrength(int i) {
        this.blurStrength = i;
    }

    public void deleteKeyFrame(int i) {
        this.context.getNativeInterface().deletePortraitVideoKeyFrame(this, i);
    }

    @Override // com.samsung.vekit.Item.VideoItem, com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.PORTRAIT_VIDEO) {
            throw new Exception("isInvalidElement : please set portraitVideo(content).");
        }
    }

    @Override // com.samsung.vekit.Listener.PortraitVideoStatusListener
    public void onPortraitVideoKeyFrameUpdated(ArrayList<PVKeyFrame> arrayList) {
        Log.i(this.TAG, "onPortraitVideoKeyFrameUpdated -> ItemId : " + this.id);
        PortraitVideoStatusListener portraitVideoStatusListener = this.portraitVideoStatusListener;
        if (portraitVideoStatusListener != null) {
            portraitVideoStatusListener.onPortraitVideoKeyFrameUpdated(arrayList);
        }
    }

    @Override // com.samsung.vekit.Listener.PortraitVideoStatusListener
    public void onPortraitVideoFrameInfoUpdated(PVFrameInfo pVFrameInfo) {
        Log.i(this.TAG, "onPortraitVideoFrameInfoUpdated -> ItemId : " + this.id);
        this.blurStrength = pVFrameInfo.getBlurLevel();
        PortraitVideoStatusListener portraitVideoStatusListener = this.portraitVideoStatusListener;
        if (portraitVideoStatusListener != null) {
            portraitVideoStatusListener.onPortraitVideoFrameInfoUpdated(pVFrameInfo);
        }
    }

    @Override // com.samsung.vekit.Listener.PortraitVideoStatusListener
    public void onPortraitVideoError(int i) {
        Log.i(this.TAG, "onPortraitVideoError -> ItemId : " + this.id + ", requestType : " + i);
        PortraitVideoStatusListener portraitVideoStatusListener = this.portraitVideoStatusListener;
        if (portraitVideoStatusListener != null) {
            portraitVideoStatusListener.onPortraitVideoError(i);
        }
    }
}
