package com.android.server.job.controllers;

public abstract class RestrictingController extends StateController {
    public abstract void startTrackingRestrictedJobLocked(JobStatus jobStatus);

    public abstract void stopTrackingRestrictedJobLocked(JobStatus jobStatus);
}
