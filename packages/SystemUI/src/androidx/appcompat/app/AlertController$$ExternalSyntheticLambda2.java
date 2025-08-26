package androidx.appcompat.app;

import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.core.util.Consumer;
import androidx.core.view.SeslTouchDelegateFactory$$ExternalSyntheticLambda0;
import androidx.core.view.SeslTouchTargetDelegate;
import androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/* loaded from: classes.dex */
public final /* synthetic */ class AlertController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ LinearLayout f$0;

    public /* synthetic */ AlertController$$ExternalSyntheticLambda2(LinearLayout linearLayout) {
        this.f$0 = linearLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final SeslTouchTargetDelegate.Builder builder;
        final SeslTouchTargetDelegate.ExtraInsets extraInsetsOf;
        LinearLayout linearLayout = this.f$0;
        ArrayList arrayList = new ArrayList();
        int childCount = linearLayout.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = linearLayout.getChildAt(i2);
            if ((childAt instanceof Button) && childAt.getVisibility() != 8) {
                arrayList.add(childAt);
            }
        }
        if (arrayList.size() == 0) {
            builder = null;
        } else {
            int height = linearLayout.getHeight();
            int width = linearLayout.getWidth();
            Rect rect = new Rect(0, 0, width, height);
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                arrayList2.add(SeslTouchTargetDelegate.calculateViewBounds(linearLayout, (View) obj));
            }
            SeslTouchDelegateFactory$$ExternalSyntheticLambda0 seslTouchDelegateFactory$$ExternalSyntheticLambda0 = linearLayout.getOrientation() == 0 ? new SeslTouchDelegateFactory$$ExternalSyntheticLambda0(rect, 0) : new SeslTouchDelegateFactory$$ExternalSyntheticLambda0(rect, 1);
            Rect rect2 = (Rect) AlertController$$ExternalSyntheticOutline0.m(1, arrayList2);
            arrayList2.add(new Rect(Math.max(0, width - rect2.right) + width, Math.max(0, height - rect2.bottom) + height, width, height));
            Rect rect3 = new Rect(0, 0, 0, 0);
            SeslTouchTargetDelegate.Builder builder2 = new SeslTouchTargetDelegate.Builder(linearLayout);
            while (i < arrayList.size()) {
                Rect rect4 = (Rect) arrayList2.get(i);
                int i4 = i + 1;
                Rect rect5 = (Rect) arrayList2.get(i4);
                switch (seslTouchDelegateFactory$$ExternalSyntheticLambda0.$r8$classId) {
                    case 0:
                        Rect rect6 = seslTouchDelegateFactory$$ExternalSyntheticLambda0.f$0;
                        extraInsetsOf = SeslTouchTargetDelegate.ExtraInsets.of(rect4.left - rect3.right, rect4.top - rect6.top, Math.max(0, rect5.left - rect4.right) / 2, rect6.bottom - rect4.bottom);
                        break;
                    default:
                        Rect rect7 = seslTouchDelegateFactory$$ExternalSyntheticLambda0.f$0;
                        extraInsetsOf = SeslTouchTargetDelegate.ExtraInsets.of(rect4.left - rect7.left, rect4.top - rect3.bottom, rect7.right - rect4.right, Math.max(0, rect5.top - rect4.bottom) / 2);
                        break;
                }
                final View view = (View) arrayList.get(i);
                ((LinkedList) builder2.mQueue).add(new Consumer() { // from class: androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda2
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj2) {
                        ((SeslTouchTargetDelegate) obj2).addTouchDelegate(view, extraInsetsOf);
                    }
                });
                rect3 = rect4;
                i = i4;
            }
            builder = builder2;
        }
        if (builder != null) {
            View view2 = builder.mAnchorView;
            Objects.requireNonNull(view2);
            final SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0 seslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0 = new SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0(view2);
            builder.mAnchorView.post(new Runnable() { // from class: androidx.core.view.SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SeslTouchTargetDelegate.Builder builder3 = builder;
                    SeslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0 seslTouchTargetDelegate$Builder$$ExternalSyntheticLambda02 = seslTouchTargetDelegate$Builder$$ExternalSyntheticLambda0;
                    SeslTouchTargetDelegate seslTouchTargetDelegate = new SeslTouchTargetDelegate(builder3.mAnchorView);
                    Iterator it = builder3.mQueue.iterator();
                    while (it.hasNext()) {
                        ((Consumer) it.next()).accept(seslTouchTargetDelegate);
                    }
                    seslTouchTargetDelegate$Builder$$ExternalSyntheticLambda02.accept(seslTouchTargetDelegate);
                    int i5 = SeslTouchTargetDelegate.$r8$clinit;
                }
            });
        }
    }
}
