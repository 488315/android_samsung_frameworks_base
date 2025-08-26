package com.android.app.viewcapture;

import android.content.res.Resources;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.data.FrameData;
import com.android.app.viewcapture.data.ViewNode;
import com.android.app.viewcapture.data.WindowData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda8 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ViewCapture.ViewIdProvider f$0;
    public final /* synthetic */ ArrayList f$1;

    public /* synthetic */ ViewCapture$$ExternalSyntheticLambda8(ViewCapture.ViewIdProvider viewIdProvider, ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = viewIdProvider;
        this.f$1 = arrayList;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) throws Resources.NotFoundException {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                ViewCapture.ViewIdProvider viewIdProvider = this.f$0;
                ArrayList arrayList = this.f$1;
                LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
                return (List) ((List) obj).stream().map(new ViewCapture$$ExternalSyntheticLambda8(viewIdProvider, arrayList, i)).collect(Collectors.toList());
            default:
                ViewCapture.ViewIdProvider viewIdProvider2 = this.f$0;
                ArrayList arrayList2 = this.f$1;
                ViewCapture.WindowListener windowListener = (ViewCapture.WindowListener) obj;
                LooperExecutor looperExecutor2 = ViewCapture.MAIN_EXECUTOR;
                windowListener.getClass();
                WindowData.Builder builderNewBuilder = WindowData.newBuilder();
                String str = windowListener.name;
                builderNewBuilder.copyOnWrite();
                WindowData.access$700((WindowData) builderNewBuilder.instance, str);
                ViewCapture.ViewPropertyRef[] viewPropertyRefArr = windowListener.mNodesBg;
                int i2 = ViewCapture.this.mMemorySize;
                if (viewPropertyRefArr[i2 - 1] == null) {
                    i2 = windowListener.mFrameIndexBg + 1;
                }
                for (int i3 = i2 - 1; i3 >= 0; i3--) {
                    int i4 = ViewCapture.this.mMemorySize;
                    int i5 = ((windowListener.mFrameIndexBg + i4) - i3) % i4;
                    ViewNode.Builder builderNewBuilder2 = ViewNode.newBuilder();
                    windowListener.mNodesBg[i5].toProto(viewIdProvider2, arrayList2, builderNewBuilder2);
                    FrameData.Builder builderNewBuilder3 = FrameData.newBuilder();
                    builderNewBuilder3.copyOnWrite();
                    FrameData.access$300((FrameData) builderNewBuilder3.instance, (ViewNode) builderNewBuilder2.build());
                    long j = windowListener.mFrameTimesNanosBg[i5];
                    builderNewBuilder3.copyOnWrite();
                    FrameData.access$100((FrameData) builderNewBuilder3.instance, j);
                    builderNewBuilder.copyOnWrite();
                    WindowData.access$200((WindowData) builderNewBuilder.instance, (FrameData) builderNewBuilder3.build());
                }
                return (WindowData) builderNewBuilder.build();
        }
    }
}
