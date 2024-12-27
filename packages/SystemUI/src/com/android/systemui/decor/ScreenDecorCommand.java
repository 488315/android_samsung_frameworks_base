package com.android.systemui.decor;

import android.graphics.Color;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.commandline.ArgParseError;
import com.android.systemui.statusbar.commandline.OptionalSubCommand;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import com.android.systemui.statusbar.commandline.SingleArgParamOptional;
import com.android.systemui.statusbar.commandline.Type;
import com.android.systemui.statusbar.commandline.ValueParser;
import com.android.systemui.statusbar.commandline.ValueParserKt$parseBoolean$1;
import com.android.systemui.statusbar.commandline.ValueParserKt$parseString$1;
import com.samsung.android.knox.zt.config.BuildConfig;
import java.io.PrintWriter;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenDecorCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final Callback callback;
    public final OptionalSubCommand cameraProtection$delegate;
    public final SingleArgParamOptional color$delegate;
    public final SingleArgParamOptional debug$delegate;
    public final SingleArgParamOptional hwcDebugTransparentRegion$delegate;
    public final OptionalSubCommand roundedBottom$delegate;
    public final OptionalSubCommand roundedTop$delegate;

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
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(ScreenDecorCommand.class, BuildConfig.BUILD_TYPE, "getDebug()Ljava/lang/Boolean;", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl2 = new PropertyReference1Impl(ScreenDecorCommand.class, "color", "getColor()Ljava/lang/Integer;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl3 = new PropertyReference1Impl(ScreenDecorCommand.class, "hwcDebugTransparentRegion", "getHwcDebugTransparentRegion()Ljava/lang/Boolean;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl4 = new PropertyReference1Impl(ScreenDecorCommand.class, "roundedTop", "getRoundedTop()Lcom/android/systemui/decor/RoundedCornerSubCommand;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl5 = new PropertyReference1Impl(ScreenDecorCommand.class, "roundedBottom", "getRoundedBottom()Lcom/android/systemui/decor/RoundedCornerSubCommand;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl6 = new PropertyReference1Impl(ScreenDecorCommand.class, "cameraProtection", "getCameraProtection()Lcom/android/systemui/decor/CameraProtectionSubCommand;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl, propertyReference1Impl2, propertyReference1Impl3, propertyReference1Impl4, propertyReference1Impl5, propertyReference1Impl6};
        new Companion(null);
    }

    public ScreenDecorCommand(Callback callback) {
        super("screen-decor", null, 2, null);
        this.callback = callback;
        Type.INSTANCE.getClass();
        ValueParserKt$parseBoolean$1 valueParserKt$parseBoolean$1 = Type.Boolean;
        this.debug$delegate = param(BuildConfig.BUILD_TYPE, null, "Enter or exits debug mode. Effectively makes the corners visible and allows for overriding the path data for the anti-aliasing corner paths and display cutout.", valueParserKt$parseBoolean$1);
        final ValueParserKt$parseString$1 valueParserKt$parseString$1 = Type.String;
        this.color$delegate = param("color", "c", "Set a specific color for the debug assets. See Color#parseString() for accepted inputs.", new ValueParser() { // from class: com.android.systemui.decor.ScreenDecorCommand$special$$inlined$map$1
            @Override // com.android.systemui.statusbar.commandline.ValueParser
            /* renamed from: parseValue-IoAF18A */
            public final Object mo1930parseValueIoAF18A(String str) {
                Result.Failure failure;
                Integer num;
                Object mo1930parseValueIoAF18A = ValueParser.this.mo1930parseValueIoAF18A(str);
                int i = Result.$r8$clinit;
                if (!(mo1930parseValueIoAF18A instanceof Result.Failure)) {
                    ResultKt.throwOnFailure(mo1930parseValueIoAF18A);
                    try {
                        num = Integer.valueOf(Color.parseColor((String) mo1930parseValueIoAF18A));
                    } catch (Exception unused) {
                        num = null;
                    }
                    if (num != null) {
                        int i2 = Result.$r8$clinit;
                        return num;
                    }
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(new ArgParseError("Failed to transform value ".concat(str)));
                } else {
                    Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(mo1930parseValueIoAF18A);
                    Intrinsics.checkNotNull(m2527exceptionOrNullimpl);
                    failure = new Result.Failure(m2527exceptionOrNullimpl);
                }
                return failure;
            }
        });
        this.hwcDebugTransparentRegion$delegate = param("transparent-region", "t", "Draw rectangle on transparent region of HWC layer", valueParserKt$parseBoolean$1);
        this.roundedTop$delegate = subCommand(new RoundedCornerSubCommand("rounded-top"));
        this.roundedBottom$delegate = subCommand(new RoundedCornerSubCommand("rounded-bottom"));
        this.cameraProtection$delegate = subCommand(new CameraProtectionSubCommand("camera-protection"));
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        ((ScreenDecorations$$ExternalSyntheticLambda0) this.callback).onExecute(this);
    }

    public final CameraProtectionSubCommand getCameraProtection() {
        KProperty kProperty = $$delegatedProperties[5];
        OptionalSubCommand optionalSubCommand = this.cameraProtection$delegate;
        return (CameraProtectionSubCommand) (optionalSubCommand.isPresent ? optionalSubCommand.cmd : null);
    }

    public final Integer getColor() {
        KProperty kProperty = $$delegatedProperties[1];
        return (Integer) this.color$delegate.getValue();
    }

    public final String toString() {
        KProperty[] kPropertyArr = $$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        Boolean bool = (Boolean) this.debug$delegate.getValue();
        Integer color = getColor();
        KProperty kProperty2 = kPropertyArr[2];
        Boolean bool2 = (Boolean) this.hwcDebugTransparentRegion$delegate.getValue();
        CameraProtectionSubCommand cameraProtection = getCameraProtection();
        KProperty kProperty3 = kPropertyArr[3];
        OptionalSubCommand optionalSubCommand = this.roundedTop$delegate;
        RoundedCornerSubCommand roundedCornerSubCommand = (RoundedCornerSubCommand) (optionalSubCommand.isPresent ? optionalSubCommand.cmd : null);
        KProperty kProperty4 = kPropertyArr[4];
        OptionalSubCommand optionalSubCommand2 = this.roundedBottom$delegate;
        return "ScreenDecorCommand(debug=" + bool + ", color=" + color + ", transparentRegion=" + bool2 + ", cameraProtection=" + cameraProtection + ", roundedTop=" + roundedCornerSubCommand + ", roundedBottom=" + ((RoundedCornerSubCommand) (optionalSubCommand2.isPresent ? optionalSubCommand2.cmd : null)) + ")";
    }
}
