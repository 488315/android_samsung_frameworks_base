package com.android.systemui.decor;

import android.graphics.Path;
import android.util.PathParser;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.commandline.ArgParseError;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import com.android.systemui.statusbar.commandline.SingleArgParam;
import com.android.systemui.statusbar.commandline.SingleArgParamOptional;
import com.android.systemui.statusbar.commandline.Type;
import com.android.systemui.statusbar.commandline.ValueParser;
import com.android.systemui.statusbar.commandline.ValueParserKt$parseFloat$1;
import com.android.systemui.statusbar.commandline.ValueParserKt$parseInt$1;
import com.android.systemui.statusbar.commandline.ValueParserKt$parseString$1;
import java.io.PrintWriter;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public final class RoundedCornerSubCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final SingleArgParam height$delegate;
    public final SingleArgParam pathData$delegate;
    public final SingleArgParamOptional viewportHeight$delegate;
    public final SingleArgParamOptional viewportWidth$delegate;
    public final SingleArgParam width$delegate;

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(RoundedCornerSubCommand.class, "height", "getHeight()I", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl2 = new PropertyReference1Impl(RoundedCornerSubCommand.class, "width", "getWidth()I", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl3 = new PropertyReference1Impl(RoundedCornerSubCommand.class, "pathData", "getPathData()Landroid/graphics/Path;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl4 = new PropertyReference1Impl(RoundedCornerSubCommand.class, "viewportHeight", "getViewportHeight()Ljava/lang/Float;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl5 = new PropertyReference1Impl(RoundedCornerSubCommand.class, "viewportWidth", "getViewportWidth()Ljava/lang/Float;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl, propertyReference1Impl2, propertyReference1Impl3, propertyReference1Impl4, propertyReference1Impl5};
    }

    public RoundedCornerSubCommand(String str) {
        super(str, null, 2, null);
        Type.INSTANCE.getClass();
        ValueParserKt$parseInt$1 valueParserKt$parseInt$1 = Type.Int;
        this.height$delegate = required(param("height", null, "The height of a corner, in pixels.", valueParserKt$parseInt$1));
        this.width$delegate = required(param("width", null, "The width of the corner, in pixels. Likely should be equal to the height.", valueParserKt$parseInt$1));
        final ValueParserKt$parseString$1 valueParserKt$parseString$1 = Type.String;
        this.pathData$delegate = required(param("path-data", "d", "PathParser-compatible path string to be rendered as the corner drawable. This path should be a closed arc oriented as the top-left corner of the device", new ValueParser() { // from class: com.android.systemui.decor.RoundedCornerSubCommand$special$$inlined$map$1
            @Override // com.android.systemui.statusbar.commandline.ValueParser
            /* renamed from: parseValue-IoAF18A, reason: not valid java name */
            public final Object mo2563parseValueIoAF18A(String str2) {
                Path pathCreatePathFromPathData;
                Object objMo2563parseValueIoAF18A = valueParserKt$parseString$1.mo2563parseValueIoAF18A(str2);
                int i = Result.$r8$clinit;
                if (objMo2563parseValueIoAF18A instanceof Result.Failure) {
                    Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(objMo2563parseValueIoAF18A);
                    thM3441exceptionOrNullimpl.getClass();
                    return new Result.Failure(thM3441exceptionOrNullimpl);
                }
                ResultKt.throwOnFailure(objMo2563parseValueIoAF18A);
                try {
                    pathCreatePathFromPathData = PathParser.createPathFromPathData((String) objMo2563parseValueIoAF18A);
                } catch (Exception unused) {
                    pathCreatePathFromPathData = null;
                }
                if (pathCreatePathFromPathData != null) {
                    int i2 = Result.$r8$clinit;
                    return pathCreatePathFromPathData;
                }
                int i3 = Result.$r8$clinit;
                return new Result.Failure(new ArgParseError(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed to transform value ", str2)));
            }
        }));
        ValueParserKt$parseFloat$1 valueParserKt$parseFloat$1 = Type.Float;
        this.viewportHeight$delegate = param("viewport-height", null, "The height of the viewport for the given path string. If null, the corner height will be used.", valueParserKt$parseFloat$1);
        this.viewportWidth$delegate = param("viewport-width", null, "The width of the viewport for the given path string. If null, the corner width will be used.", valueParserKt$parseFloat$1);
    }

    public final DebugRoundedCornerModel toRoundedCornerDebugModel() {
        float fIntValue;
        KProperty[] kPropertyArr = $$delegatedProperties;
        Path path = (Path) this.pathData$delegate.getValue(this, kPropertyArr[2]);
        KProperty kProperty = kPropertyArr[1];
        int iIntValue = ((Number) this.width$delegate.getValue(this, kProperty)).intValue();
        KProperty kProperty2 = kPropertyArr[0];
        int iIntValue2 = ((Number) this.height$delegate.getValue(this, kProperty2)).intValue();
        Float f = (Float) this.viewportWidth$delegate.getValue(this, kPropertyArr[4]);
        float fIntValue2 = 1.0f;
        if (f != null) {
            fIntValue = ((Number) r5.getValue(this, kPropertyArr[1])).intValue() / f.floatValue();
        } else {
            fIntValue = 1.0f;
        }
        Float f2 = (Float) this.viewportHeight$delegate.getValue(this, kPropertyArr[3]);
        if (f2 != null) {
            fIntValue2 = ((Number) r8.getValue(this, kPropertyArr[0])).intValue() / f2.floatValue();
        }
        return new DebugRoundedCornerModel(path, iIntValue, iIntValue2, fIntValue, fIntValue2);
    }

    public final String toString() {
        KProperty[] kPropertyArr = $$delegatedProperties;
        int iIntValue = ((Number) this.height$delegate.getValue(this, kPropertyArr[0])).intValue();
        int iIntValue2 = ((Number) this.width$delegate.getValue(this, kPropertyArr[1])).intValue();
        Path path = (Path) this.pathData$delegate.getValue(this, kPropertyArr[2]);
        Float f = (Float) this.viewportHeight$delegate.getValue(this, kPropertyArr[3]);
        Float f2 = (Float) this.viewportWidth$delegate.getValue(this, kPropertyArr[4]);
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iIntValue, iIntValue2, "RoundedCornerSubCommand(height=", ", width=", ", pathData='");
        sbM.append(path);
        sbM.append("', viewportHeight=");
        sbM.append(f);
        sbM.append(", viewportWidth=");
        sbM.append(f2);
        sbM.append(")");
        return sbM.toString();
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
    }
}
