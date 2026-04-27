
package com.srm.curahealth.model;

public class AppointmentData {

    private final String facility;
    private final boolean readmission;
    private final String healthcareProgram;
    private final String visitDate;
    private final String comment;

    public AppointmentData(String facility, boolean readmission, String healthcareProgram, String visitDate,
            String comment) {
        this.facility = facility;
        this.readmission = readmission;
        this.healthcareProgram = healthcareProgram;
        this.visitDate = visitDate;
        this.comment = comment;
    }

    public String getFacility() {
        return facility;
    }

    public boolean isReadmission() {
        return readmission;
    }

    public String getHealthcareProgram() {
        return healthcareProgram;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getComment() {
        return comment;
    }
}
