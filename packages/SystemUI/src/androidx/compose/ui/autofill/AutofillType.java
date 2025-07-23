package androidx.compose.ui.autofill;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AutofillType {
    public static final /* synthetic */ AutofillType[] $VALUES;
    public static final AutofillType AddressAuxiliaryDetails;
    public static final AutofillType AddressCountry;
    public static final AutofillType AddressLocality;
    public static final AutofillType AddressRegion;
    public static final AutofillType AddressStreet;
    public static final AutofillType BirthDateDay;
    public static final AutofillType BirthDateFull;
    public static final AutofillType BirthDateMonth;
    public static final AutofillType BirthDateYear;
    public static final AutofillType CreditCardExpirationDate;
    public static final AutofillType CreditCardExpirationDay;
    public static final AutofillType CreditCardExpirationMonth;
    public static final AutofillType CreditCardExpirationYear;
    public static final AutofillType CreditCardNumber;
    public static final AutofillType CreditCardSecurityCode;
    public static final AutofillType EmailAddress;
    public static final AutofillType Gender;
    public static final AutofillType NewPassword;
    public static final AutofillType NewUsername;
    public static final AutofillType Password;
    public static final AutofillType PersonFirstName;
    public static final AutofillType PersonFullName;
    public static final AutofillType PersonLastName;
    public static final AutofillType PersonMiddleInitial;
    public static final AutofillType PersonMiddleName;
    public static final AutofillType PersonNamePrefix;
    public static final AutofillType PersonNameSuffix;
    public static final AutofillType PhoneCountryCode;
    public static final AutofillType PhoneNumber;
    public static final AutofillType PhoneNumberDevice;
    public static final AutofillType PhoneNumberNational;
    public static final AutofillType PostalAddress;
    public static final AutofillType PostalCode;
    public static final AutofillType PostalCodeExtended;
    public static final AutofillType SmsOtpCode;
    public static final AutofillType Username;

    static {
        AutofillType autofillType = new AutofillType("EmailAddress", 0);
        EmailAddress = autofillType;
        AutofillType autofillType2 = new AutofillType("Username", 1);
        Username = autofillType2;
        AutofillType autofillType3 = new AutofillType("Password", 2);
        Password = autofillType3;
        AutofillType autofillType4 = new AutofillType("NewUsername", 3);
        NewUsername = autofillType4;
        AutofillType autofillType5 = new AutofillType("NewPassword", 4);
        NewPassword = autofillType5;
        AutofillType autofillType6 = new AutofillType("PostalAddress", 5);
        PostalAddress = autofillType6;
        AutofillType autofillType7 = new AutofillType("PostalCode", 6);
        PostalCode = autofillType7;
        AutofillType autofillType8 = new AutofillType("CreditCardNumber", 7);
        CreditCardNumber = autofillType8;
        AutofillType autofillType9 = new AutofillType("CreditCardSecurityCode", 8);
        CreditCardSecurityCode = autofillType9;
        AutofillType autofillType10 = new AutofillType("CreditCardExpirationDate", 9);
        CreditCardExpirationDate = autofillType10;
        AutofillType autofillType11 = new AutofillType("CreditCardExpirationMonth", 10);
        CreditCardExpirationMonth = autofillType11;
        AutofillType autofillType12 = new AutofillType("CreditCardExpirationYear", 11);
        CreditCardExpirationYear = autofillType12;
        AutofillType autofillType13 = new AutofillType("CreditCardExpirationDay", 12);
        CreditCardExpirationDay = autofillType13;
        AutofillType autofillType14 = new AutofillType("AddressCountry", 13);
        AddressCountry = autofillType14;
        AutofillType autofillType15 = new AutofillType("AddressRegion", 14);
        AddressRegion = autofillType15;
        AutofillType autofillType16 = new AutofillType("AddressLocality", 15);
        AddressLocality = autofillType16;
        AutofillType autofillType17 = new AutofillType("AddressStreet", 16);
        AddressStreet = autofillType17;
        AutofillType autofillType18 = new AutofillType("AddressAuxiliaryDetails", 17);
        AddressAuxiliaryDetails = autofillType18;
        AutofillType autofillType19 = new AutofillType("PostalCodeExtended", 18);
        PostalCodeExtended = autofillType19;
        AutofillType autofillType20 = new AutofillType("PersonFullName", 19);
        PersonFullName = autofillType20;
        AutofillType autofillType21 = new AutofillType("PersonFirstName", 20);
        PersonFirstName = autofillType21;
        AutofillType autofillType22 = new AutofillType("PersonLastName", 21);
        PersonLastName = autofillType22;
        AutofillType autofillType23 = new AutofillType("PersonMiddleName", 22);
        PersonMiddleName = autofillType23;
        AutofillType autofillType24 = new AutofillType("PersonMiddleInitial", 23);
        PersonMiddleInitial = autofillType24;
        AutofillType autofillType25 = new AutofillType("PersonNamePrefix", 24);
        PersonNamePrefix = autofillType25;
        AutofillType autofillType26 = new AutofillType("PersonNameSuffix", 25);
        PersonNameSuffix = autofillType26;
        AutofillType autofillType27 = new AutofillType("PhoneNumber", 26);
        PhoneNumber = autofillType27;
        AutofillType autofillType28 = new AutofillType("PhoneNumberDevice", 27);
        PhoneNumberDevice = autofillType28;
        AutofillType autofillType29 = new AutofillType("PhoneCountryCode", 28);
        PhoneCountryCode = autofillType29;
        AutofillType autofillType30 = new AutofillType("PhoneNumberNational", 29);
        PhoneNumberNational = autofillType30;
        AutofillType autofillType31 = new AutofillType("Gender", 30);
        Gender = autofillType31;
        AutofillType autofillType32 = new AutofillType("BirthDateFull", 31);
        BirthDateFull = autofillType32;
        AutofillType autofillType33 = new AutofillType("BirthDateDay", 32);
        BirthDateDay = autofillType33;
        AutofillType autofillType34 = new AutofillType("BirthDateMonth", 33);
        BirthDateMonth = autofillType34;
        AutofillType autofillType35 = new AutofillType("BirthDateYear", 34);
        BirthDateYear = autofillType35;
        AutofillType autofillType36 = new AutofillType("SmsOtpCode", 35);
        SmsOtpCode = autofillType36;
        AutofillType[] autofillTypeArr = {autofillType, autofillType2, autofillType3, autofillType4, autofillType5, autofillType6, autofillType7, autofillType8, autofillType9, autofillType10, autofillType11, autofillType12, autofillType13, autofillType14, autofillType15, autofillType16, autofillType17, autofillType18, autofillType19, autofillType20, autofillType21, autofillType22, autofillType23, autofillType24, autofillType25, autofillType26, autofillType27, autofillType28, autofillType29, autofillType30, autofillType31, autofillType32, autofillType33, autofillType34, autofillType35, autofillType36};
        $VALUES = autofillTypeArr;
        EnumEntriesKt.enumEntries(autofillTypeArr);
    }

    private AutofillType(String str, int i) {
    }

    public static AutofillType valueOf(String str) {
        return (AutofillType) Enum.valueOf(AutofillType.class, str);
    }

    public static AutofillType[] values() {
        return (AutofillType[]) $VALUES.clone();
    }
}
