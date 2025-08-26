package com.android.systemui.decor;

import com.android.systemui.statusbar.commandline.ParseableCommand;
import com.android.systemui.statusbar.commandline.SingleArgParamOptional;
import com.android.systemui.statusbar.commandline.Type;
import java.io.PrintWriter;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public final class CameraProtectionSubCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final SingleArgParamOptional enabled$delegate;
    public final SingleArgParamOptional strokeWidth$delegate;

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(CameraProtectionSubCommand.class, "enabled", "getEnabled()Ljava/lang/Boolean;", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl2 = new PropertyReference1Impl(CameraProtectionSubCommand.class, "strokeWidth", "getStrokeWidth()Ljava/lang/Integer;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl, propertyReference1Impl2};
    }

    public CameraProtectionSubCommand(String str) {
        super(str, null, 2, null);
        Type.INSTANCE.getClass();
        this.enabled$delegate = param("enabled", "e", "The circle drawn around camera hole to prevent light leakage from front camera", Type.Boolean);
        this.strokeWidth$delegate = param("stroke-width", "w", "Thickness of camera protection (unit: pixel)", Type.Int);
    }

    public final String toString() {
        KProperty[] kPropertyArr = $$delegatedProperties;
        return "CameraProtectionSubCommand( enabled=" + ((Boolean) this.enabled$delegate.getValue(this, kPropertyArr[0])) + ", strokeWidth=" + ((Integer) this.strokeWidth$delegate.getValue(this, kPropertyArr[1])) + ")";
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
    }
}
