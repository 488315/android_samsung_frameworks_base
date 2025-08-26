package androidx.compose.ui.text.font;

/* loaded from: classes.dex */
public interface PlatformResolveInterceptor {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final PlatformResolveInterceptor$Companion$Default$1 Default = new PlatformResolveInterceptor() { // from class: androidx.compose.ui.text.font.PlatformResolveInterceptor$Companion$Default$1
        };

        private Companion() {
        }
    }

    default FontWeight interceptFontWeight(FontWeight fontWeight) {
        return fontWeight;
    }
}
