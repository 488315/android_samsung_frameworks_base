package android.flags;

/* loaded from: classes.dex */
public class BooleanFlag extends BooleanFlagBase {
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

    BooleanFlag(String str, String str2, boolean z) {
        super(str, str2);
        this.mDefault = z;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public Boolean getDefault() {
        return Boolean.valueOf(this.mDefault);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    /* renamed from: defineMetaData, reason: merged with bridge method [inline-methods] */
    public Flag<Boolean> defineMetaData2(String str, String str2, String str3) {
        super.defineMetaData2(str, str2, str3);
        return this;
    }
}
