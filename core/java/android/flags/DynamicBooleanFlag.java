package android.flags;

public class DynamicBooleanFlag extends BooleanFlagBase implements DynamicFlag<Boolean> {
    private final boolean mDefault;

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ String getCategoryName() {
        return super.getCategoryName();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ String getDescription() {
        return super.getDescription();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ String getLabel() {
        return super.getLabel();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ String getNamespace() {
        return super.getNamespace();
    }

    @Override // android.flags.BooleanFlagBase
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    DynamicBooleanFlag(String namespace, String name, boolean defaultValue) {
        super(namespace, name);
        this.mDefault = defaultValue;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public Boolean getDefault() {
        return Boolean.valueOf(this.mDefault);
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    /* renamed from: defineMetaData, reason: avoid collision after fix types in other method */
    public Flag<Boolean> defineMetaData2(String label, String description, String categoryName) {
        super.defineMetaData2(label, description, categoryName);
        return this;
    }
}
