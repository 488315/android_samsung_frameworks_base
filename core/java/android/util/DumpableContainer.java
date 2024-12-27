package android.util;

public interface DumpableContainer {
    boolean addDumpable(Dumpable dumpable);

    boolean removeDumpable(Dumpable dumpable);
}
