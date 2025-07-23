package android.os.instrumentation;

import android.app.slice.SliceItem;
import android.widget.SemRemoteViewsValueAnimation;
import com.android.internal.hidden_from_bootclasspath.com.android.art.flags.Flags;
import java.lang.reflect.Executable;

/* loaded from: classes3.dex */
public final class MethodDescriptorParser {
    public static Executable parseMethodDescriptor(ClassLoader classLoader, MethodDescriptor methodDescriptor) {
        Class<?> loadClass;
        try {
            Class<?> loadClass2 = classLoader.loadClass(methodDescriptor.fullyQualifiedClassName);
            Class<?>[] clsArr = new Class[methodDescriptor.fullyQualifiedParameters.length];
            for (int i = 0; i < methodDescriptor.fullyQualifiedParameters.length; i++) {
                String str = methodDescriptor.fullyQualifiedParameters[i];
                boolean endsWith = str.endsWith("[]");
                char c = 2;
                if (endsWith) {
                    str = str.substring(0, str.length() - 2);
                }
                switch (str.hashCode()) {
                    case -1325958191:
                        if (str.equals("double")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 104431:
                        if (str.equals("int")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3039496:
                        if (str.equals("byte")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3052374:
                        if (str.equals("char")) {
                            break;
                        }
                        c = 65535;
                        break;
                    case 3327612:
                        if (str.equals(SliceItem.FORMAT_LONG)) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 64711720:
                        if (str.equals("boolean")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 97526364:
                        if (str.equals(SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT)) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 109413500:
                        if (str.equals("short")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        clsArr[i] = endsWith ? Boolean.TYPE.arrayType() : Boolean.TYPE;
                        break;
                    case 1:
                        clsArr[i] = endsWith ? Byte.TYPE.arrayType() : Byte.TYPE;
                        break;
                    case 2:
                        clsArr[i] = endsWith ? Character.TYPE.arrayType() : Character.TYPE;
                        break;
                    case 3:
                        clsArr[i] = endsWith ? Short.TYPE.arrayType() : Short.TYPE;
                        break;
                    case 4:
                        clsArr[i] = endsWith ? Integer.TYPE.arrayType() : Integer.TYPE;
                        break;
                    case 5:
                        clsArr[i] = endsWith ? Long.TYPE.arrayType() : Long.TYPE;
                        break;
                    case 6:
                        clsArr[i] = endsWith ? Float.TYPE.arrayType() : Float.TYPE;
                        break;
                    case 7:
                        clsArr[i] = endsWith ? Double.TYPE.arrayType() : Double.TYPE;
                        break;
                    default:
                        if (endsWith) {
                            loadClass = classLoader.loadClass(str).arrayType();
                        } else {
                            loadClass = classLoader.loadClass(str);
                        }
                        clsArr[i] = loadClass;
                        break;
                }
            }
            if (Flags.executableMethodFileOffsetsV2() && methodDescriptor.methodName.equals("<init>")) {
                return loadClass2.getDeclaredConstructor(clsArr);
            }
            return loadClass2.getDeclaredMethod(methodDescriptor.methodName, clsArr);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException("The specified method cannot be found. Is this descriptor valid? " + methodDescriptor, e);
        }
    }
}
