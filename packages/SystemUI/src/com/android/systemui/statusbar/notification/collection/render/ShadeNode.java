package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.Unit;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeNode {
    public final NodeController controller;
    public ShadeNode parent;

    public ShadeNode(NodeController nodeController) {
        this.controller = nodeController;
    }

    public final void addChildAt(ShadeNode shadeNode, int i) {
        NodeController nodeController = shadeNode.controller;
        boolean isEnabled = Trace.isEnabled();
        NodeController nodeController2 = this.controller;
        if (isEnabled) {
            TraceUtilsKt.beginSlice("ShadeNode#" + Reflection.getOrCreateKotlinClass(nodeController2.getClass()).getSimpleName() + "#addChildAt");
        }
        try {
            nodeController2.addChildAt(nodeController, i);
            nodeController.onViewAdded();
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final void moveChildTo(ShadeNode shadeNode, int i) {
        NodeController nodeController = shadeNode.controller;
        boolean isEnabled = Trace.isEnabled();
        NodeController nodeController2 = this.controller;
        if (isEnabled) {
            TraceUtilsKt.beginSlice("ShadeNode#" + Reflection.getOrCreateKotlinClass(nodeController2.getClass()).getSimpleName() + "#moveChildTo");
        }
        try {
            nodeController2.moveChildTo(nodeController, i);
            nodeController.getClass();
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
