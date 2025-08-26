package androidx.navigation;

/* loaded from: classes.dex */
public final class NamedNavArgument {
    public final NavArgument argument;
    public final String name;

    public NamedNavArgument(String str, NavArgument navArgument) {
        this.name = str;
        this.argument = navArgument;
    }
}
