package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Object.PVFrameInfo;
import com.samsung.vekit.Common.Object.PVKeyFrame;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public interface PortraitVideoStatusListener extends NativeInterfaceListener {
    void onPortraitVideoError(int i);

    void onPortraitVideoFrameInfoUpdated(PVFrameInfo pVFrameInfo);

    void onPortraitVideoKeyFrameUpdated(ArrayList<PVKeyFrame> arrayList);
}
