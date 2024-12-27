package com.android.systemui;

import com.android.systemui.statusbar.commandline.OptionalSubCommand;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import java.io.PrintWriter;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class StatusBarInsetsCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final OptionalSubCommand bottomMargin$delegate;
    public final Callback callback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(StatusBarInsetsCommand.class, "bottomMargin", "getBottomMargin()Lcom/android/systemui/BottomMarginCommand;", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl};
        new Companion(null);
    }

    public StatusBarInsetsCommand(Callback callback) {
        super("status-bar-insets", null, 2, 0 == true ? 1 : 0);
        this.callback = callback;
        this.bottomMargin$delegate = subCommand(new BottomMarginCommand());
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = ((StatusBarContentInsetsProvider.AnonymousClass1.C02001) this.callback).this$0;
        statusBarContentInsetsProvider.getClass();
        KProperty kProperty = $$delegatedProperties[0];
        OptionalSubCommand optionalSubCommand = this.bottomMargin$delegate;
        BottomMarginCommand bottomMarginCommand = (BottomMarginCommand) (optionalSubCommand.isPresent ? optionalSubCommand.cmd : null);
        if (bottomMarginCommand != null) {
            Map map = BottomMarginCommand.ROTATION_DEGREES_TO_VALUE_MAPPING;
            KProperty[] kPropertyArr = BottomMarginCommand.$$delegatedProperties;
            KProperty kProperty2 = kPropertyArr[0];
            Integer num = (Integer) map.get((Integer) bottomMarginCommand.rotationDegrees$delegate.getValue());
            if (num == null) {
                BottomMarginCommand.Companion.getClass();
                printWriter.println("Rotation should be one of " + BottomMarginCommand.ROTATION_DEGREES_OPTIONS);
                return;
            }
            KProperty kProperty3 = kPropertyArr[1];
            Float f = (Float) bottomMarginCommand.marginBottomDp$delegate.getValue();
            if (f == null) {
                printWriter.println("Margin bottom not set.");
                return;
            }
            float floatValue = f.floatValue();
            statusBarContentInsetsProvider.insetsCache.evictAll();
            statusBarContentInsetsProvider.marginBottomOverrides.put(num, Integer.valueOf((int) (floatValue * statusBarContentInsetsProvider.context.getResources().getDisplayMetrics().density)));
            statusBarContentInsetsProvider.notifyInsetsChanged();
        }
    }
}
