package com.example.startup_project.Models;

public class OrganizationModel {

    private String OrganizationName,OrganizationAddress,OrgImage,OrganizationID;
    private long submittedAt;

    private String image1,image2,image3,image4;

    public OrganizationModel() {
    }

    public OrganizationModel(String organizationName, String organizationAddress,String OrgImage, String organizationID, long submittedAt) {
        OrganizationName = organizationName;
        OrganizationAddress = organizationAddress;
        this.OrgImage = OrgImage;
        OrganizationID = organizationID;
        this.submittedAt = submittedAt;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return OrganizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        OrganizationAddress = organizationAddress;
    }

    public String getOrgImage() {
        return OrgImage;
    }

    public void setOrgImage(String OrgImage) {
        this.OrgImage = OrgImage;
    }

    public long getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(long submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(String organizationID) {
        OrganizationID = organizationID;
    }


    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }
}
