package android.os.instrumentation;

import android.app.slice.SliceItem;
import android.widget.SemRemoteViewsValueAnimation;
import com.android.internal.hidden_from_bootclasspath.com.android.art.flags.Flags;
import java.lang.reflect.Executable;

/* loaded from: classes3.dex */
public final class MethodDescriptorParser {
    /* JADX WARN: Removed duplicated region for block: B:35:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Executable parseMethodDescriptor(ClassLoader classLoader, MethodDescriptor methodDescriptor) throws ClassNotFoundException {
        Class<?> clsLoadClass;
        try {
            Class<?> clsLoadClass2 = classLoader.loadClass(methodDescriptor.fullyQualifiedClassName);
            Class<?>[] clsArr = new Class[methodDescriptor.fullyQualifiedParameters.length];
            for (int i = 0; i < methodDescriptor.fullyQualifiedParameters.length; i++) {
                String strSubstring = methodDescriptor.fullyQualifiedParameters[i];
                boolean zEndsWith = strSubstring.endsWith("[]");
                char c = 2;
                if (zEndsWith) {
                    strSubstring = strSubstring.substring(0, strSubstring.length() - 2);
                }
                switch (strSubstring.hashCode()) {
                    case -1325958191:
                        if (strSubstring.equals("double")) {
                            c = 7;
                            break;
                        } else {
                            c = 65535;
                            break;
                        }
                    case 104431:
                        if (strSubstring.equals("int")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 3039496:
                        if (strSubstring.equals("byte")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 3052374:
                        if (strSubstring.equals("char")) {
                            break;
                        }
                        break;
                    case 3327612:
                        if (strSubstring.equals(SliceItem.FORMAT_LONG)) {
                            c = 5;
                            break;
                        }
                        break;
                    case 64711720:
                        if (strSubstring.equals("boolean")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 97526364:
                        if (strSubstring.equals(SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 109413500:
                        if (strSubstring.equals("short")) {
                            c = 3;
                            break;
                        }
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        clsArr[i] = zEndsWith ? Boolean.TYPE.arrayType() : Boolean.TYPE;
                        break;
                    case 1:
                        clsArr[i] = zEndsWith ? Byte.TYPE.arrayType() : Byte.TYPE;
                        break;
                    case 2:
                        clsArr[i] = zEndsWith ? Character.TYPE.arrayType() : Character.TYPE;
                        break;
                    case 3:
                        clsArr[i] = zEndsWith ? Short.TYPE.arrayType() : Short.TYPE;
                        break;
                    case 4:
                        clsArr[i] = zEndsWith ? Integer.TYPE.arrayType() : Integer.TYPE;
                        break;
                    case 5:
                        clsArr[i] = zEndsWith ? Long.TYPE.arrayType() : Long.TYPE;
                        break;
                    case 6:
                        clsArr[i] = zEndsWith ? Float.TYPE.arrayType() : Float.TYPE;
                        break;
                    case 7:
                        clsArr[i] = zEndsWith ? Double.TYPE.arrayType() : Double.TYPE;
                        break;
                    default:
                        if (zEndsWith) {
                            clsLoadClass = classLoader.loadClass(strSubstring).arrayType();
                        } else {
                            clsLoadClass = classLoader.loadClass(strSubstring);
                        }
                        clsArr[i] = clsLoadClass;
                        break;
                }
            }
            if (Flags.executableMethodFileOffsetsV2() && methodDescriptor.methodName.equals("<init>")) {
                return clsLoadClass2.getDeclaredConstructor(clsArr);
            }
            return clsLoadClass2.getDeclaredMethod(methodDescriptor.methodName, clsArr);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException("The specified method cannot be found. Is this descriptor valid? " + methodDescriptor, e);
        }
    }
}
