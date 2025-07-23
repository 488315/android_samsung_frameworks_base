package android.flags;

/* loaded from: classes.dex */
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

    DynamicBooleanFlag(String str, String str2, boolean z) {
        super(str, str2);
        this.mDefault = z;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public Boolean getDefault() {
        return Boolean.valueOf(this.mDefault);
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    /* renamed from: defineMetaData, reason: avoid collision after fix types in other method */
    public Flag<Boolean> defineMetaData2(String str, String str2, String str3) {
        super.defineMetaData2(str, str2, str3);
        return this;
    }
}
