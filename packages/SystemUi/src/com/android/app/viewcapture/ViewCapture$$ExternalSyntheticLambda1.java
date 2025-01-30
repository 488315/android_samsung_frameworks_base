package com.android.app.viewcapture;

import com.android.app.viewcapture.data.MotionWindowData;
import com.android.app.viewcapture.data.WindowData;
import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ArrayList f$0;

    public /* synthetic */ ViewCapture$$ExternalSyntheticLambda1(ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = arrayList;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((List) obj).stream().findFirst().map(new ViewCapture$$ExternalSyntheticLambda1(this.f$0, 1));
            default:
                ArrayList arrayList = this.f$0;
                MotionWindowData.Builder newBuilder = MotionWindowData.newBuilder();
                Internal.ProtobufList frameDataList = ((WindowData) obj).getFrameDataList();
                newBuilder.copyOnWrite();
                MotionWindowData.access$400((MotionWindowData) newBuilder.instance, frameDataList);
                List list = arrayList.stream().map(new ViewCapture$$ExternalSyntheticLambda9()).toList();
                newBuilder.copyOnWrite();
                MotionWindowData.access$900((MotionWindowData) newBuilder.instance, list);
                return (MotionWindowData) newBuilder.build();
        }
    }
}
