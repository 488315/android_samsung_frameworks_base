package com.samsung.android.ims;

public interface SemImsRegistrationListener {
    void onDeregistered(
            SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError);

    void onRegistered(SemImsRegistration semImsRegistration);
}
