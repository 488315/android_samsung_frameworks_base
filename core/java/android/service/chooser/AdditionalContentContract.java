package android.service.chooser;

public interface AdditionalContentContract {

    public interface Columns {
        public static final String URI = "uri";
    }

    public interface CursorExtraKeys {
        public static final String POSITION = "position";
    }

    public interface MethodNames {
        public static final String ON_SELECTION_CHANGED = "onSelectionChanged";
    }
}
