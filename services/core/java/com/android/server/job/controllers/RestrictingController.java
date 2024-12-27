package com.android.server.job.controllers;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RestrictingController extends StateController {
    public abstract void startTrackingRestrictedJobLocked(JobStatus jobStatus);

    public abstract void stopTrackingRestrictedJobLocked(JobStatus jobStatus);
}
