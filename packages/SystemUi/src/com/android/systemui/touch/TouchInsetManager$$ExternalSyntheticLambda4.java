package com.android.systemui.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.AttachedSurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TouchInsetManager$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HashMap f$0;

    public /* synthetic */ TouchInsetManager$$ExternalSyntheticLambda4(int i, HashMap hashMap) {
        this.$r8$classId = i;
        this.f$0 = hashMap;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((HashMap) obj).entrySet().stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda4(2, this.f$0));
                break;
            case 1:
                HashMap hashMap = this.f$0;
                Map.Entry entry = (Map.Entry) obj;
                AttachedSurfaceControl attachedSurfaceControl = (AttachedSurfaceControl) entry.getKey();
                if (!hashMap.containsKey(attachedSurfaceControl)) {
                    attachedSurfaceControl.setTouchableRegion(null);
                }
                ((Region) entry.getValue()).recycle();
                break;
            case 2:
                HashMap hashMap2 = this.f$0;
                Map.Entry entry2 = (Map.Entry) obj;
                AttachedSurfaceControl attachedSurfaceControl2 = (AttachedSurfaceControl) entry2.getKey();
                if (!hashMap2.containsKey(attachedSurfaceControl2)) {
                    hashMap2.put(attachedSurfaceControl2, Region.obtain());
                }
                ((Region) hashMap2.get(attachedSurfaceControl2)).op((Region) entry2.getValue(), Region.Op.UNION);
                break;
            default:
                HashMap hashMap3 = this.f$0;
                View view = (View) obj;
                AttachedSurfaceControl rootSurfaceControl = view.getRootSurfaceControl();
                if (rootSurfaceControl != null) {
                    if (!hashMap3.containsKey(rootSurfaceControl)) {
                        hashMap3.put(rootSurfaceControl, Region.obtain());
                    }
                    Rect rect = new Rect();
                    view.getDrawingRect(rect);
                    ((ViewGroup) view.getRootView()).offsetDescendantRectToMyCoords(view, rect);
                    ((Region) hashMap3.get(rootSurfaceControl)).op(rect, Region.Op.UNION);
                    break;
                }
                break;
        }
    }
}
