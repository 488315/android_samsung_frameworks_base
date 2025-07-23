package kotlinx.serialization.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PlatformKt {
    /* JADX WARN: Can't wrap try/catch for region: R(15:58|(1:(2:60|(1:63)(1:62))(2:111|112))|(5:106|107|108|(8:80|81|(1:(3:83|(1:101)(1:(1:89)(2:86|87))|88)(2:102|(1:104)))|90|(1:100)(1:94)|95|(1:97)|99)|(1:79)(4:70|(1:78)|76|77))|65|(1:67)|80|81|(2:(0)(0)|88)|90|(1:92)|100|95|(0)|99|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x00e9, code lost:
    
        if (r8 == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00aa, code lost:
    
        if (r7 == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a3, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e2, code lost:
    
        r9 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x015d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00fb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0146 A[Catch: NoSuchFieldException -> 0x0175, TryCatch #2 {NoSuchFieldException -> 0x0175, blocks: (B:81:0x013d, B:83:0x0146, B:92:0x0162, B:94:0x0168, B:95:0x016e, B:97:0x0172, B:88:0x015a), top: B:80:0x013d }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0172 A[Catch: NoSuchFieldException -> 0x0175, TRY_LEAVE, TryCatch #2 {NoSuchFieldException -> 0x0175, blocks: (B:81:0x013d, B:83:0x0146, B:92:0x0162, B:94:0x0168, B:95:0x016e, B:97:0x0172, B:88:0x015a), top: B:80:0x013d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlinx.serialization.KSerializer constructSerializerForGivenTypeArgs(kotlin.reflect.KClass r13, kotlinx.serialization.KSerializer... r14) {
        /*
            Method dump skipped, instructions count: 427
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.serialization.internal.PlatformKt.constructSerializerForGivenTypeArgs(kotlin.reflect.KClass, kotlinx.serialization.KSerializer[]):kotlinx.serialization.KSerializer");
    }

    public static final KSerializer invokeSerializerOnCompanion(Object obj, KSerializer... kSerializerArr) {
        Class[] clsArr;
        try {
            if (kSerializerArr.length == 0) {
                clsArr = new Class[0];
            } else {
                int length = kSerializerArr.length;
                Class[] clsArr2 = new Class[length];
                for (int i = 0; i < length; i++) {
                    clsArr2[i] = KSerializer.class;
                }
                clsArr = clsArr2;
            }
            Object invoke = obj.getClass().getDeclaredMethod("serializer", (Class[]) Arrays.copyOf(clsArr, clsArr.length)).invoke(obj, Arrays.copyOf(kSerializerArr, kSerializerArr.length));
            if (invoke instanceof KSerializer) {
                return (KSerializer) invoke;
            }
            return null;
        } catch (NoSuchMethodException unused) {
            return null;
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                throw e;
            }
            String message = cause.getMessage();
            if (message == null) {
                message = e.getMessage();
            }
            throw new InvocationTargetException(cause, message);
        }
    }
}
