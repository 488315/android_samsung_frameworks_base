package com.samsung.android.hardware.secinputdev.motion;

import android.content.Context;

import java.io.PrintWriter;
import java.nio.MappedByteBuffer;

/* loaded from: classes.dex */
public class SemInputMotionStream extends SemInputMotion {
    private static final String TAG = "SemInputMotionStream";

    public SemInputMotionStream(Context context, int channelX, int channelY, int rawLength) {
        super(TAG, "", channelX, channelY, rawLength);
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected boolean prepareTensorflow(MappedByteBuffer mappedByteBuffer) {
        return true;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void prepareSettings() {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void startDelivery() {
        restart();
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected boolean restartDelivery() {
        return true;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void pauseDelivery() {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void stopDelivery() {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void destroyDelivery() {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    protected void delivery(int[] rawdata) {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public void setMotionControl(String type, int control) {}

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public int getMotionControl(String type) {
        return 0;
    }

    @Override // com.samsung.android.hardware.secinputdev.motion.SemInputMotion
    public void dump(PrintWriter pw) {
        pw.println("dumping SemInputMotionStream");
    }
}
