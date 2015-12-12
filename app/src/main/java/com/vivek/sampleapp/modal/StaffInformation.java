
package com.vivek.sampleapp.modal;

public class StaffInformation {

    private String staffName;
    private String staffId;
    private String staffPost;
    private String staffSchedule;
    private String staffDesignation;
    private String staffQualifications;
    private String staffFellowships;
    private String staffSpeciality;
    private String staffExperience;
    private String imageStaff;
    private boolean fromDoc;
    private boolean fromNurse;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getstaffPost() {
        return staffPost;
    }

    public void setstaffPost(String staffPost) {
        this.staffPost = staffPost;
    }

    public String getstaffDesignation() {
        return staffDesignation;
    }

    public void setstaffDesignation(String staffDesignation) {
        this.staffDesignation = staffDesignation;
    }

    public String getstaffQualifications() {
        return staffQualifications;
    }

    public void setstaffQualifications(String staffQualifications) {
        this.staffQualifications = staffQualifications;
    }

    public String getstaffFellowships() {
        return staffFellowships;
    }

    public void setstaffFellowships(String staffFellowships) {
        this.staffFellowships = staffFellowships;
    }

    public String getstaffSpeciality() {
        return staffSpeciality;
    }

    public void setstaffSpeciality(String staffSpeciality) {
        this.staffSpeciality = staffSpeciality;
    }

    public String getstaffExperience() {
        return staffExperience;
    }

    public void setstaffExperience(String staffExperience) {
        this.staffExperience = staffExperience;
    }

    public String getStaffSchedule() {
        return staffSchedule;
    }

    public void setStaffSchedule(String staffSchedule) {
        this.staffSchedule = staffSchedule;
    }

    public String getImageStaff() {
        return imageStaff;
    }

    public void setImageStaff(String imageStaff) {
        this.imageStaff = imageStaff;
    }

    public boolean isFromDoc() {
        return fromDoc;
    }

    public void setFromDoc(boolean fromDoc) {
        this.fromDoc = fromDoc;
    }

    public boolean isFromNurse() {
        return fromNurse;
    }

    public void setFromNurse(boolean fromNurse) {
        this.fromNurse = fromNurse;
    }

}
