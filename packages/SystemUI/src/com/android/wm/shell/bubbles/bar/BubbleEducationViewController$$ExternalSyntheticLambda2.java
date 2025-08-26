package com.android.wm.shell.bubbles.bar;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;
import com.android.wm.shell.bubbles.BubbleEducationController;
import com.android.wm.shell.taskview.TaskView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleEducationViewController$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleEducationViewController f$0;

    public /* synthetic */ BubbleEducationViewController$$ExternalSyntheticLambda2(BubbleEducationViewController bubbleEducationViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleEducationViewController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        TaskView taskView;
        BubbleBarLayerView$$ExternalSyntheticLambda2 bubbleBarLayerView$$ExternalSyntheticLambda2;
        final BubbleEducationViewController bubbleEducationViewController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = BubbleEducationViewController.$r8$clinit;
                View view = new View(bubbleEducationViewController.context);
                view.setImportantForAccessibility(2);
                view.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.bar.BubbleEducationViewController$scrimView$2$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        BubbleEducationViewController.hideEducation$default(bubbleEducationViewController, true);
                    }
                });
                return view;
            case 1:
                int i2 = BubbleEducationViewController.$r8$clinit;
                return new BubbleEducationController(bubbleEducationViewController.context);
            default:
                int i3 = BubbleEducationViewController.$r8$clinit;
                SharedPreferences.Editor editorEdit = ((BubbleEducationController) bubbleEducationViewController.controller$delegate.getValue()).prefs.edit();
                editorEdit.putBoolean("HasSeenBubblesManageOnboarding", true);
                editorEdit.apply();
                BubbleBarExpandedView bubbleBarExpandedView = ((BubbleBarLayerView$$ExternalSyntheticLambda0) bubbleEducationViewController.listener).f$0.mExpandedView;
                if (bubbleBarExpandedView != null && (taskView = bubbleBarExpandedView.mTaskView) != null && (bubbleBarLayerView$$ExternalSyntheticLambda2 = bubbleBarExpandedView.mLayerBoundsSupplier) != null) {
                    taskView.mObscuredTouchRegion = new Region((Rect) bubbleBarLayerView$$ExternalSyntheticLambda2.get());
                    taskView.invalidate();
                }
                return Unit.INSTANCE;
        }
    }
}
