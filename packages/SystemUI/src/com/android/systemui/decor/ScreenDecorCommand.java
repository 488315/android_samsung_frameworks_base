package com.android.systemui.decor;

import android.graphics.Color;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
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
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenDecorCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final Callback callback;
    public final OptionalSubCommand cameraProtection$delegate;
    public final SingleArgParamOptional color$delegate;
    public final SingleArgParamOptional debug$delegate;
    public final SingleArgParamOptional faceAuthScreen$delegate;
    public final SingleArgParamOptional hwcDebugTransparentRegion$delegate;
    public final OptionalSubCommand roundedBottom$delegate;
    public final OptionalSubCommand roundedTop$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        PropertyReference1Impl propertyReference1Impl6 = new PropertyReference1Impl(ScreenDecorCommand.class, "faceAuthScreen", "getFaceAuthScreen()Ljava/lang/Integer;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl7 = new PropertyReference1Impl(ScreenDecorCommand.class, "cameraProtection", "getCameraProtection()Lcom/android/systemui/decor/CameraProtectionSubCommand;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl, propertyReference1Impl2, propertyReference1Impl3, propertyReference1Impl4, propertyReference1Impl5, propertyReference1Impl6, propertyReference1Impl7};
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
            public final Object mo2548parseValueIoAF18A(String str) {
                Integer num;
                Object mo2548parseValueIoAF18A = ValueParser.this.mo2548parseValueIoAF18A(str);
                int i = Result.$r8$clinit;
                if (mo2548parseValueIoAF18A instanceof Result.Failure) {
                    Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(mo2548parseValueIoAF18A);
                    m3422exceptionOrNullimpl.getClass();
                    return new Result.Failure(m3422exceptionOrNullimpl);
                }
                ResultKt.throwOnFailure(mo2548parseValueIoAF18A);
                try {
                    num = Integer.valueOf(Color.parseColor((String) mo2548parseValueIoAF18A));
                } catch (Exception unused) {
                    num = null;
                }
                if (num != null) {
                    int i2 = Result.$r8$clinit;
                    return num;
                }
                int i3 = Result.$r8$clinit;
                return new Result.Failure(new ArgParseError(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed to transform value ", str)));
            }
        });
        this.hwcDebugTransparentRegion$delegate = param("transparent-region", "t", "Draw rectangle on transparent region of HWC layer", valueParserKt$parseBoolean$1);
        this.roundedTop$delegate = subCommand(new RoundedCornerSubCommand("rounded-top"));
        this.roundedBottom$delegate = subCommand(new RoundedCornerSubCommand("rounded-bottom"));
        this.faceAuthScreen$delegate = param("faceAuthScreen", null, "Specify a screen to show face auth animation. 0:outer(default screen), 1:inner", Type.Int);
        this.cameraProtection$delegate = subCommand(new CameraProtectionSubCommand("camera-protection"));
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        ((ScreenDecorations$$ExternalSyntheticLambda0) this.callback).onExecute(this);
    }

    public final CameraProtectionSubCommand getCameraProtection() {
        KProperty kProperty = $$delegatedProperties[6];
        return (CameraProtectionSubCommand) this.cameraProtection$delegate.getValue();
    }

    public final Integer getColor() {
        return (Integer) this.color$delegate.getValue(this, $$delegatedProperties[1]);
    }

    public final String toString() {
        KProperty[] kPropertyArr = $$delegatedProperties;
        Boolean bool = (Boolean) this.debug$delegate.getValue(this, kPropertyArr[0]);
        Integer color = getColor();
        Integer num = (Integer) this.faceAuthScreen$delegate.getValue(this, kPropertyArr[5]);
        Boolean bool2 = (Boolean) this.hwcDebugTransparentRegion$delegate.getValue(this, kPropertyArr[2]);
        CameraProtectionSubCommand cameraProtection = getCameraProtection();
        KProperty kProperty = kPropertyArr[3];
        RoundedCornerSubCommand roundedCornerSubCommand = (RoundedCornerSubCommand) this.roundedTop$delegate.getValue();
        KProperty kProperty2 = kPropertyArr[4];
        return "ScreenDecorCommand(debug=" + bool + ", color=" + color + ", faceAuthScreen=" + num + ", transparentRegion=" + bool2 + ", cameraProtection=" + cameraProtection + ", roundedTop=" + roundedCornerSubCommand + ", roundedBottom=" + ((RoundedCornerSubCommand) this.roundedBottom$delegate.getValue()) + ")";
    }
}
