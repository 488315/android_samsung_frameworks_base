package com.android.app.viewcapture;

import com.android.app.viewcapture.data.MotionWindowData;
import com.android.app.viewcapture.data.WindowData;
import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ArrayList f$0;

    public /* synthetic */ ViewCapture$$ExternalSyntheticLambda4(int i, ArrayList arrayList) {
        this.$r8$classId = i;
        this.f$0 = arrayList;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        ArrayList arrayList = this.f$0;
        switch (i) {
            case 0:
                LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
                return ((List) obj).stream().findFirst().map(new ViewCapture$$ExternalSyntheticLambda4(1, arrayList));
            default:
                LooperExecutor looperExecutor2 = ViewCapture.MAIN_EXECUTOR;
                MotionWindowData.Builder builderNewBuilder = MotionWindowData.newBuilder();
                Internal.ProtobufList frameDataList = ((WindowData) obj).getFrameDataList();
                builderNewBuilder.copyOnWrite();
                MotionWindowData.access$400((MotionWindowData) builderNewBuilder.instance, frameDataList);
                List list = (List) arrayList.stream().map(new ViewCapture$$ExternalSyntheticLambda6()).collect(Collectors.toList());
                builderNewBuilder.copyOnWrite();
                MotionWindowData.access$900((MotionWindowData) builderNewBuilder.instance, list);
                return (MotionWindowData) builderNewBuilder.build();
        }
    }
}
