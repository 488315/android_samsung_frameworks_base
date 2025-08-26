package com.android.systemui;

import com.android.systemui.statusbar.commandline.OptionalSubCommand;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl$start$1$1;
import java.io.PrintWriter;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public final class StatusBarInsetsCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final OptionalSubCommand bottomMargin$delegate;
    public final Callback callback;

    public interface Callback {
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(StatusBarInsetsCommand.class, "bottomMargin", "getBottomMargin()Lcom/android/systemui/BottomMarginCommand;", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl};
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StatusBarInsetsCommand(Callback callback) {
        super("status-bar-insets", null, 2, 0 == true ? 1 : 0);
        this.callback = callback;
        this.bottomMargin$delegate = subCommand(new BottomMarginCommand());
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = ((StatusBarContentInsetsProviderImpl$start$1$1) this.callback).this$0;
        statusBarContentInsetsProviderImpl.getClass();
        KProperty kProperty = $$delegatedProperties[0];
        BottomMarginCommand bottomMarginCommand = (BottomMarginCommand) this.bottomMargin$delegate.getValue();
        if (bottomMarginCommand != null) {
            Map map = BottomMarginCommand.ROTATION_DEGREES_TO_VALUE_MAPPING;
            KProperty[] kPropertyArr = BottomMarginCommand.$$delegatedProperties;
            Integer num = (Integer) map.get((Integer) bottomMarginCommand.rotationDegrees$delegate.getValue(bottomMarginCommand, kPropertyArr[0]));
            if (num == null) {
                BottomMarginCommand.Companion.getClass();
                printWriter.println("Rotation should be one of " + BottomMarginCommand.ROTATION_DEGREES_OPTIONS);
                return;
            }
            Float f = (Float) bottomMarginCommand.marginBottomDp$delegate.getValue(bottomMarginCommand, kPropertyArr[1]);
            if (f == null) {
                printWriter.println("Margin bottom not set.");
                return;
            }
            float fFloatValue = f.floatValue();
            statusBarContentInsetsProviderImpl.insetsCache.evictAll();
            statusBarContentInsetsProviderImpl.marginBottomOverrides.put(num, Integer.valueOf((int) (fFloatValue * statusBarContentInsetsProviderImpl.context.getResources().getDisplayMetrics().density)));
            statusBarContentInsetsProviderImpl.notifyInsetsChanged();
        }
    }
}
