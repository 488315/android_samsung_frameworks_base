package android.sysprop;

import java.util.Locale;

public abstract class SurfaceFlingerProperties {
    public static Boolean tryParseBoolean(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        lowerCase.getClass();
        switch (lowerCase) {
        }
        return null;
    }
}
