package kotlin.jvm.internal;

/* loaded from: classes4.dex */
public class ReflectionFactory {
    public static String renderLambdaToString(FunctionBase functionBase) {
        String string = functionBase.getClass().getGenericInterfaces()[0].toString();
        return string.startsWith("kotlin.jvm.functions.") ? string.substring(21) : string;
    }
}
