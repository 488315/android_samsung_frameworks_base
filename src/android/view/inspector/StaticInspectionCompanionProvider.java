package android.view.inspector;

/* loaded from: classes4.dex */
public class StaticInspectionCompanionProvider implements InspectionCompanionProvider {
    private static final String COMPANION_SUFFIX = "$InspectionCompanion";

    @Override // android.view.inspector.InspectionCompanionProvider
    public <T> InspectionCompanion<T> provide(Class<T> cls) {
        try {
            Class<?> loadClass = cls.getClassLoader().loadClass(cls.getName() + COMPANION_SUFFIX);
            if (InspectionCompanion.class.isAssignableFrom(loadClass)) {
                return (InspectionCompanion) loadClass.newInstance();
            }
            return null;
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException(cause);
        }
    }
}
